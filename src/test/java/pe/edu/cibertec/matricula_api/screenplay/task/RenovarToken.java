package pe.edu.cibertec.matricula_api.screenplay.task;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.Map;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RenovarToken implements Task {

    private final String refreshToken;

    public RenovarToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public static Performable con(String refreshToken) {
        return instrumented(RenovarToken.class, refreshToken);
    }

    @Override
    @Step("{0} renueva sus tokens con el refresh token #refreshToken")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Post.to("/api/auth/refresh")
                .with(peticion -> peticion.contentType(ContentType.JSON)
                        .body(Map.of("refreshToken", refreshToken))));

        Response respuesta = SerenityRest.lastResponse();
        if (respuesta.statusCode() == 200) {
            actor.remember("accessTokenNuevo", respuesta.jsonPath().getString("accessToken"));
            actor.remember("refreshTokenNuevo", respuesta.jsonPath().getString("refreshToken"));
        }
    }
}
