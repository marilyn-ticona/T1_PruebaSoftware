package pe.edu.cibertec.matricula_api.screenplay.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Question;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ElHistorial {

    public static Question<Integer> creditosAprobados() {
        return Question.about("los créditos aprobados del historial")
                .answeredBy(actor -> SerenityRest.lastResponse().jsonPath().getInt("creditosAprobados"));
    }

    public static Question<Double> promedioPonderado() {
        return Question.about("el promedio ponderado del historial")
                .answeredBy(actor -> SerenityRest.lastResponse().jsonPath().getDouble("promedioPonderado"));
    }

    public static Question<String> condicionDelCurso(String codigoCurso) {
        return Question.about("la condición de " + codigoCurso + " en el historial")
                .answeredBy(actor -> {
                    List<Map<String, Object>> cursos = SerenityRest.lastResponse().jsonPath().getList("cursos");
                    return cursos.stream()
                            .filter(curso -> Objects.equals(String.valueOf(curso.get("codigoCurso")), codigoCurso))
                            .map(curso -> String.valueOf(curso.get("condicion")))
                            .findFirst()
                            .orElse(null);
                });
    }
}
