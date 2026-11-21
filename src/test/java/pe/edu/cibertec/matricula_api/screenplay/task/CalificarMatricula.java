package pe.edu.cibertec.matricula_api.screenplay.task;

import io.restassured.http.ContentType;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Patch;

import java.util.Map;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CalificarMatricula implements Task {

    private final long idMatricula;
    private final double nota;
    private final String tokenDocente;

    public CalificarMatricula(long idMatricula, double nota, String tokenDocente) {
        this.idMatricula = idMatricula;
        this.nota = nota;
        this.tokenDocente = tokenDocente;
    }

    public static Performable con(long idMatricula, double nota, String tokenDocente) {
        return instrumented(CalificarMatricula.class, idMatricula, nota, tokenDocente);
    }

    @Override
    @Step("{0} califica la matrícula #idMatricula con la nota #nota")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Patch.to("/api/matriculas/{id}/nota")
                .with(peticion -> peticion.pathParam("id", idMatricula)
                        .contentType(ContentType.JSON)
                        .auth().oauth2(tokenDocente)
                        .body(Map.of("nota", nota))));
    }
}
