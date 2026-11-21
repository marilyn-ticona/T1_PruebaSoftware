package pe.edu.cibertec.matricula_api.screenplay.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.rest.interactions.Get;
import pe.edu.cibertec.matricula_api.screenplay.questions.ElHistorial;
import pe.edu.cibertec.matricula_api.screenplay.questions.LaRespuesta;
import pe.edu.cibertec.matricula_api.screenplay.task.CalificarMatricula;
import pe.edu.cibertec.matricula_api.screenplay.task.ConsultarHistorial;
import pe.edu.cibertec.matricula_api.screenplay.task.IniciarSesion;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CalificacionStepDefinitions {

    @Given("que inicio sesión como la alumna {string} con la clave {string} y guardo su token")
    public void iniciarSesionComoAlumna(String alumna, String clave) {
        theActorInTheSpotlight().attemptsTo(IniciarSesion.con(alumna, clave));
        theActorInTheSpotlight().remember("tokenAlumna",
                theActorInTheSpotlight().<String>recall("accessToken"));
    }

    @When("obtengo el id de la matrícula del curso {string} para {string} desde GET \\/api\\/matriculas con el token de la alumna")
    public void obtenerIdDeLaMatricula(String curso, String alumna) {
        String token = theActorInTheSpotlight().recall("tokenAlumna");
        theActorInTheSpotlight().attemptsTo(Get.resource("/api/matriculas")
                .with(peticion -> peticion.queryParam("alumno", alumna)
                        .header("Authorization", "Bearer " + token)));

        List<Map<String, Object>> matriculas = SerenityRest.lastResponse().jsonPath().getList("$");
        Map<String, Object> matricula = matriculas.stream()
                .filter(item -> Objects.equals(String.valueOf(item.get("codigoCurso")), curso))
                .findFirst()
                .orElse(null);
        assertThat(matricula, notNullValue());
        theActorInTheSpotlight().remember("idMatricula", ((Number) matricula.get("id")).longValue());
    }

    @When("el docente {string} inicia sesión con la clave {string} y guarda su token")
    public void iniciarSesionComoDocente(String docente, String clave) {
        theActorInTheSpotlight().attemptsTo(IniciarSesion.con(docente, clave));
        theActorInTheSpotlight().remember("tokenDocente",
                theActorInTheSpotlight().<String>recall("accessToken"));
    }

    @When("CalificarMatricula registra la nota {string} en la matrícula obtenida")
    public void calificarMatricula(String nota) {
        long idMatricula = theActorInTheSpotlight().recall("idMatricula");
        String tokenDocente = theActorInTheSpotlight().recall("tokenDocente");
        theActorInTheSpotlight().attemptsTo(CalificarMatricula.con(
                idMatricula, Double.parseDouble(nota.replace(',', '.')), tokenDocente));
    }

    @Then("la condición de la matrícula es {string}")
    public void condicionDeLaMatricula(String condicion) {
        theActorInTheSpotlight().should(seeThat(LaRespuesta.campo("condicion"), equalTo(condicion)));
    }

    @When("ConsultarHistorial consulta el historial de {string}")
    public void consultarHistorial(String alumna) {
        String token = theActorInTheSpotlight().recall("tokenAlumna");
        theActorInTheSpotlight().attemptsTo(ConsultarHistorial.de(alumna, token));
    }

    @Then("el historial tiene {int} créditos aprobados y promedio ponderado {string}")
    public void verificarHistorial(int creditos, String promedio) {
        theActorInTheSpotlight().should(seeThat(ElHistorial.creditosAprobados(), equalTo(creditos)));
        theActorInTheSpotlight().should(seeThat(ElHistorial.promedioPonderado(),
                closeTo(Double.parseDouble(promedio.replace(',', '.')), 0.001)));
    }
}
