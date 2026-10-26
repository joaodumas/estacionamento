package org.example.Etapa02;

/**
 * Reserva de uma sala por um aluno.
 * Os horários são horas cheias do dia (0 a 23): uma reserva das 10 às 12
 * tem horaInicio = 10 e horaFim = 12.
 */
public class Reserva {

    private final int id;
    private final Sala sala;
    private final String matricula;
    private final int horaInicio;
    private final int horaFim;
    private final int participantes;
    private boolean ativa = true;

    Reserva(int id, Sala sala, String matricula, int horaInicio, int horaFim, int participantes) {
        this.id = id;
        this.sala = sala;
        this.matricula = matricula;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.participantes = participantes;
    }

    /** Dois horários se sobrepõem quando um começa antes do outro terminar. */
    boolean conflitaCom(int outroInicio, int outroFim) {
        return horaInicio < outroFim && outroInicio < horaFim;
    }

    boolean ehDaSala(Sala outraSala) {
        return sala.getCodigo().equals(outraSala.getCodigo());
    }

    void cancelar() {
        ativa = false;
    }

    public int getId() {
        return id;
    }

    public Sala getSala() {
        return sala;
    }

    public String getMatricula() {
        return matricula;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public int getHoraFim() {
        return horaFim;
    }

    public int getParticipantes() {
        return participantes;
    }

    public boolean isAtiva() {
        return ativa;
    }
}
