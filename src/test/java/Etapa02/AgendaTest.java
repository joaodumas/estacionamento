package Etapa02;
import org.example.Etapa02.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotificadorDummy implements Notificador {
    @Override public void reservaConfirmada(Reserva reserva) {}
    @Override public void reservaCancelada(Reserva reserva) {}
}

// Tornar o cenário determinístico
class SalasRepositoryFake extends SalasRepository {

    List<Sala> salas = new ArrayList<>();

    @Override
    public List<Sala> getAll() {
        return List.of();
    }

    @Override
    public Sala getByCode(String codigo) {
        return new Sala(codigo, 6);
    }
}


public class AgendaTest {

    private Agenda agenda;
    private final String MATRICULA_VALIDA = "12345678";
    private final Sala SALA = new Sala("SL101", 6);

    @BeforeEach
    void criarAgenda() {
        agenda = new Agenda(7,
                new NotificadorDummy(),
                new SalasRepositoryFake());
    }

    @Test void deveRealizarAReservaComOsDadosCorretos() {
        Reserva reserva = agenda.reservar(
                SALA, "12345678", 8, 2, 5);

        assertTrue(reserva.isAtiva());
        assertEquals(10, reserva.getHoraFim());
        assertEquals(1, agenda.reservasDaSala(SALA).size());
//        assertEquals(1, notificadorEmMemoria.mensagensConfirmacao);
    }

    @Test void deveLancarIAExceptionComHorarioForaDaDisponibilidade() {
        int horaInicio = 23;

        var exception = assertThrows(
                IllegalArgumentException.class,
                () -> agenda.reservar(
                    SALA, "12345678", horaInicio, 2, 5)
        );

        assertEquals(
                "Fora do horário de funcionamento",
                exception.getMessage());
    }

    @Test void deveLancarReservaRecusadaExceptionComConflitoDeHorario() {
        int horaInicio = 12;
        agenda.reservar(
                SALA, MATRICULA_VALIDA, horaInicio,
                2, 6);

        var exception = assertThrows(
                ReservaRecusadaException.class,
                () -> agenda.reservar(
                        SALA, MATRICULA_VALIDA, horaInicio,
                        2, 6)
        );
        assertEquals(
                "Sala já reservada no horário solicitado",
                exception.getMessage());

    }

    @Test void deveLancarIllegalArgumentComReservaSalaNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> agenda.reservar(
                        null, "12345678",
                        8, 2, 5)

        );
        assertEquals("A sala é obrigatória", exception.getMessage());
    }

    @Test void deveLancarIllegalArgumentComReservaMatriculaNullOuDiferenteTamanho() {
        IllegalArgumentException exceptionNull = assertThrows(
                IllegalArgumentException.class,
                () -> agenda.reservar(
                        SALA, null,
                        8, 2, 5)
        );
        IllegalArgumentException exceptionSize = assertThrows(
                IllegalArgumentException.class,
                () -> agenda.reservar(
                        SALA, "1234567",
                        8, 2, 5)
        );
        assertEquals("Matrícula inválida: null", exceptionNull.getMessage());
        assertEquals("Matrícula inválida: 1234567", exceptionSize.getMessage());
    }

    @Test void deveCancelarUmaReservaPassivelDeCancelamento() {
        // A
        Reserva reserva = agenda.reservar(SALA,
                "12345678", 8, 2, 6);

        // A
        agenda.cancelar(reserva.getId());

        // A
        assertFalse(reserva.isAtiva());
        assertEquals(0, agenda.reservasDaSala(SALA).size());
//        assertEquals(1, notificadorEmMemoria.mensagensCancelamento);
    }

    // Teste quando não tem reserva
    // Verificar diretamente se há reserva para o aluno
    @Test void deveRetornarZeroParaQuandoNaoHaReservasParaAluno() {
        int resultado = agenda.contarAtivasDoAluno(MATRICULA_VALIDA);

        assertEquals(0, resultado);
    }

    // Teste quando tem n reservas
    // Realizar N reservas
    // Verificar se há N reservas para o aluno
    @Test void deveRetornar1ParaQuandoHouver1ReservaParaAluno() {
        agenda.reservar(SALA, MATRICULA_VALIDA, 12, 2, 6);

        int result = agenda.contarAtivasDoAluno(MATRICULA_VALIDA);

        assertEquals(1, result);
    }

    // Quando há reservas canceladas do aluno
    // Realizar N reservas
    // Cancelar M reservas
    // Verificar se há N - M reservas para o Aluno
    // deveRetonar3Menos1QuandoHouver3ReservasE1Cancelamento
    @Test void deveRetonar2QuandoHouver3ReservasE1Cancelamento() {
        agenda.reservar(SALA, MATRICULA_VALIDA, 8, 1, 6);
        agenda.reservar(SALA, MATRICULA_VALIDA, 10, 1, 6);
        var reserva = agenda.reservar(SALA, MATRICULA_VALIDA, 12, 1, 6);

        int result = agenda.contarAtivasDoAluno(MATRICULA_VALIDA);

        assertEquals(3, result);

        agenda.cancelar(reserva.getId());

        result = agenda.contarAtivasDoAluno(MATRICULA_VALIDA);
        assertEquals(2, result);

    }


}


















