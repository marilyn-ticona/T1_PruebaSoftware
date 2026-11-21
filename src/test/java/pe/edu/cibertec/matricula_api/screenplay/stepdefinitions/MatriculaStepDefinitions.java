package pe.edu.cibertec.matricula_api.screenplay.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.model.environment.SystemEnvironmentVariables;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;

public class MatriculaStepDefinitions {

    private final String baseUrl = Objects.requireNonNullElse(
            EnvironmentSpecificConfiguration
                    .from(SystemEnvironmentVariables.currentEnvironmentVariables())
                    .getProperty("restapi.baseurl"),
            "http://localhost:8080");

    private String token;
    private String alumno;
    private Long idMatricula;

    @Given("que el alumno {string} inicia sesión con la clave {string} y obtiene un token Bearer")
    public void iniciarSesion(String alumno, String clave) {
        this.alumno = alumno;
        Response respuesta = SerenityRest.given().baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(Map.of("username", alumno, "password", clave))
                .post("/api/auth/login");
        respuesta.then().statusCode(200);
        token = respuesta.jsonPath().getString("accessToken");
        assertThat(token, notNullValue());
    }

    @When("cotizo el curso {string} para el alumno {string}")
    public void cotizarCurso(String curso, String alumno) {
        SerenityRest.given().baseUri(baseUrl).header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(Map.of("codigoAlumno", alumno, "cursos", List.of(curso)))
                .post("/api/matriculas/cotizacion");
    }

    @Then("la respuesta de cotización tiene el estado {int}")
    public void estadoDeLaCotizacion(int estado) {
        SerenityRest.lastResponse().then().statusCode(estado);
    }

    @Then("la cotización tiene {int} créditos, subtotal {string}, descuento {int} y total {string}")
    public void verificarCotizacion(int creditos, String subtotal, int descuento, String total) {
        Response respuesta = SerenityRest.lastResponse();
        assertThat(respuesta.jsonPath().getInt("creditos"), equalTo(creditos));
        verificarDecimal("subtotal", respuesta, subtotal);
        assertThat(respuesta.jsonPath().getInt("descuento"), equalTo(descuento));
        verificarDecimal("total", respuesta, total);
    }

    @When("matrico al alumno {string} en el curso {string}")
    public void matricular(String alumno, String curso) {
        Response respuesta = SerenityRest.given().baseUri(baseUrl).header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(Map.of("codigoAlumno", alumno, "codigoCurso", curso))
                .post("/api/matriculas");
        idMatricula = respuesta.jsonPath().getLong("id");
        assertThat(idMatricula, notNullValue());
    }

    @Then("la respuesta de matrícula tiene el estado {int}")
    public void estadoDeLaMatricula(int estado) {
        SerenityRest.lastResponse().then().statusCode(estado);
    }

    @Then("la matrícula tiene estado {string} y monto {string}")
    public void verificarMatricula(String estado, String monto) {
        Response respuesta = SerenityRest.lastResponse();
        assertThat(respuesta.jsonPath().getString("estado"), equalTo(estado));
        verificarDecimal("monto", respuesta, monto);
    }

    @When("pago la matrícula creada con el método {string}")
    public void pagar(String metodo) {
        RequestSpecification peticion = SerenityRest.given().baseUri(baseUrl).header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON);
        if ("Sin cuerpo en la petición".equalsIgnoreCase(metodo)) {
            peticion.patch("/api/matriculas/{id}/pagar", idMatricula);
        } else {
            peticion.body(Map.of("metodo", metodo))
                    .patch("/api/matriculas/{id}/pagar", idMatricula);
        }
    }

    @Then("la respuesta de pago tiene el estado {int}")
    public void estadoDelPago(int estado) {
        SerenityRest.lastResponse().then().statusCode(estado);
    }

    @Then("la matrícula tiene estado {string} y fechaPago con valor")
    public void verificarPago(String estado) {
        Response respuesta = SerenityRest.lastResponse();
        assertThat(respuesta.jsonPath().getString("estado"), equalTo(estado));
        assertThat(respuesta.jsonPath().getString("fechaPago"), notNullValue());
    }

    @When("consulto los pagos del alumno {string}")
    public void consultarPagos(String alumno) {
        SerenityRest.given().baseUri(baseUrl).header("Authorization", "Bearer " + token)
                .queryParam("alumno", alumno)
                .get("/api/pagos");
    }

    @Then("existe un pago para la matrícula creada con método {string} y número de operación con formato {string}")
    public void verificarPagoEnListado(String metodo, String formato) {
        String metodoEsperado = "Sin cuerpo en la petición".equalsIgnoreCase(metodo) ? "EFECTIVO" : metodo;
        List<Map<String, Object>> pagos = SerenityRest.lastResponse().jsonPath().getList("$");
        Map<String, Object> pago = pagos.stream()
                .filter(p -> Objects.equals(String.valueOf(p.get("idMatricula")), String.valueOf(idMatricula)))
                .findFirst()
                .orElse(null);
        assertThat(pago, notNullValue());
        assertThat(String.valueOf(pago.get("metodo")), equalTo(metodoEsperado));
        assertThat(String.valueOf(pago.get("numeroOperacion")),
                matchesPattern(formato.replaceAll("0", "\\\\d")));
    }

    @When("cotizo los cursos que deben fallar:")
    public void cotizarCursosQueDebenFallar(DataTable cursos) {
        SerenityRest.given().baseUri(baseUrl).header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(Map.of("codigoAlumno", alumno, "cursos", cursos.asList(String.class)))
                .post("/api/matriculas/cotizacion");
    }

    @Then("la respuesta de error tiene el estado {int}")
    public void estadoDelError(int estado) {
        SerenityRest.lastResponse().then().statusCode(estado);
    }

    @Then("el error tiene el codigo {string}")
    public void codigoDelError(String codigo) {
        assertThat(SerenityRest.lastResponse().jsonPath().getString("codigo"), equalTo(codigo));
    }

    @Then("la respuesta JSON cumple el esquema {string}")
    public void cumpleElEsquema(String esquema) {
        SerenityRest.lastResponse().then().body(matchesJsonSchemaInClasspath("schemas/" + esquema));
    }

    private void verificarDecimal(String campo, Response respuesta, String esperado) {
        double valor = Double.parseDouble(esperado.replace(',', '.'));
        assertThat(respuesta.jsonPath().getDouble(campo), closeTo(valor, 0.001));
    }
}
