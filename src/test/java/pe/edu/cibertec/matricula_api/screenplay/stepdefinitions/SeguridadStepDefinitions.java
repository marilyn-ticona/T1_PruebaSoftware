package pe.edu.cibertec.matricula_api.screenplay.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.rest.interactions.Get;
import pe.edu.cibertec.matricula_api.screenplay.questions.ElCodigoDeError;
import pe.edu.cibertec.matricula_api.screenplay.questions.LaRespuesta;
import pe.edu.cibertec.matricula_api.screenplay.task.CerrarSesion;
import pe.edu.cibertec.matricula_api.screenplay.task.IniciarSesion;
import pe.edu.cibertec.matricula_api.screenplay.task.RenovarToken;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

public class SeguridadStepDefinitions {

    private String usuarioBloqueo;

    @When("IniciarSesion con el usuario {string} y la clave {string}")
    public void iniciarSesion(String usuario, String clave) {
        theActorInTheSpotlight().attemptsTo(IniciarSesion.con(usuario, clave));
    }

    @Then("los tokens quedaron en la memoria del actor")
    public void tokensEnLaMemoriaDelActor() {
        assertThat(theActorInTheSpotlight().<String>recall("accessToken"), notNullValue());
        assertThat(theActorInTheSpotlight().<String>recall("refreshToken"), notNullValue());
    }

    @When("RenovarToken con el refresh token guardado")
    public void renovarToken() {
        String refreshToken = theActorInTheSpotlight().recall("refreshToken");
        theActorInTheSpotlight().attemptsTo(RenovarToken.con(refreshToken));
    }

    @Then("los tokens nuevos son diferentes a los originales")
    public void tokensNuevosDiferentes() {
        String accessOriginal = theActorInTheSpotlight().recall("accessToken");
        String refreshOriginal = theActorInTheSpotlight().recall("refreshToken");
        String accessNuevo = theActorInTheSpotlight().recall("accessTokenNuevo");
        String refreshNuevo = theActorInTheSpotlight().recall("refreshTokenNuevo");

        assertThat(accessNuevo, notNullValue());
        assertThat(refreshNuevo, notNullValue());
        assertThat(accessNuevo, not(equalTo(accessOriginal)));
        assertThat(refreshNuevo, not(equalTo(refreshOriginal)));
    }

    @When("reutilizo el refresh token original")
    public void reutilizarRefreshToken() {
        String refreshToken = theActorInTheSpotlight().recall("refreshToken");
        theActorInTheSpotlight().attemptsTo(RenovarToken.con(refreshToken));
    }

    @When("consulto el perfil con el accessToken nuevo")
    public void perfilConAccessTokenNuevo() {
        String token = theActorInTheSpotlight().recall("accessTokenNuevo");
        theActorInTheSpotlight().attemptsTo(Get.resource("/api/auth/perfil")
                .with(peticion -> peticion.header("Authorization", "Bearer " + token)));
    }

    @When("CerrarSesion con el accessToken guardado")
    public void cerrarSesion() {
        String token = theActorInTheSpotlight().recall("accessToken");
        theActorInTheSpotlight().attemptsTo(CerrarSesion.con(token));
    }

    @When("consulto el perfil con el accessToken guardado")
    public void perfilConAccessTokenGuardado() {
        String token = theActorInTheSpotlight().recall("accessToken");
        theActorInTheSpotlight().attemptsTo(Get.resource("/api/auth/perfil")
                .with(peticion -> peticion.header("Authorization", "Bearer " + token)));
    }

    @When("intento iniciar sesión con el usuario {string} según la tabla:")
    public void intentosDeInicioDeSesion(String usuario, DataTable tabla) {
        this.usuarioBloqueo = usuario;
        for (Map<String, String> fila : tabla.asMaps(String.class, String.class)) {
            theActorInTheSpotlight().attemptsTo(IniciarSesion.con(usuario, fila.get("clave")));
            theActorInTheSpotlight().should(seeThat(LaRespuesta.codigoDeEstado(),
                    equalTo(Integer.parseInt(fila.get("estado")))));
            theActorInTheSpotlight().should(seeThat(ElCodigoDeError.actual(),
                    equalTo(fila.get("codigo"))));
            SerenityRest.lastResponse().then().body(matchesJsonSchemaInClasspath("schemas/error.json"));
        }
    }

    @Then("intento iniciar sesión con la clave correcta {string}")
    public void intentoConClaveCorrecta(String clave) {
        theActorInTheSpotlight().attemptsTo(IniciarSesion.con(usuarioBloqueo, clave));
    }

    @When("listo todos los alumnos con el accessToken guardado")
    public void listarAlumnos() {
        String token = theActorInTheSpotlight().recall("accessToken");
        theActorInTheSpotlight().attemptsTo(Get.resource("/api/alumnos")
                .with(peticion -> peticion.header("Authorization", "Bearer " + token)));
    }

    @Then("el codigo del error de seguridad es {string}")
    public void codigoDelError(String codigo) {
        theActorInTheSpotlight().should(seeThat(ElCodigoDeError.actual(), equalTo(codigo)));
    }
}
