package pe.edu.cibertec.matricula_api.screenplay.task;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CerrarSesion implements Task {

    private final String accessToken;

    public CerrarSesion(String accessToken) {
        this.accessToken = accessToken;
    }

    public static Performable con(String accessToken) {
        return instrumented(CerrarSesion.class, accessToken);
    }

    @Override
    @Step("{0} cierra sesión con su accessToken")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Post.to("/api/auth/logout")
                .with(peticion -> peticion.header("Authorization", "Bearer " + accessToken)));
    }
}
