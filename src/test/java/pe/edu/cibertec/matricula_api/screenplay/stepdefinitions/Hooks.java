package pe.edu.cibertec.matricula_api.screenplay.stepdefinitions;

import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Hooks {

    private static final String NOMBRE = "Marilyn Ticona Salinas";
    private static final String CODIGO = "I202506711";
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Before
    public void beforeScenario() {
        OnStage.setTheStage(new OnlineCast());
        System.out.println("============================================================");
        System.out.printf("Estudiante: %s | Codigo: %s | Fecha y hora: %s%n",
                NOMBRE, CODIGO, LocalDateTime.now().format(FORMATO));
        System.out.println("============================================================");
    }
}
