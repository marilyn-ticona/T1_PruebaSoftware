package pe.edu.cibertec.matricula_api.screenplay.task;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ConsultarHistorial implements Task {

    private final String alumno;
    private final String token;

    public ConsultarHistorial(String alumno, String token) {
        this.alumno = alumno;
        this.token = token;
    }

    public static Performable de(String alumno, String token) {
        return instrumented(ConsultarHistorial.class, alumno, token);
    }

    @Override
    @Step("{0} consulta el historial de #alumno")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Get.resource("/api/alumnos/{codigo}/historial")
                .with(peticion -> peticion.pathParam("codigo", alumno)
                        .header("Authorization", "Bearer " + token)));
    }
}
