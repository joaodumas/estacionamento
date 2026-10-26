package org.example.Etapa02;

import java.util.ArrayList;

public class Agenda {

    public static final int TAMANHO_MATRICULA = 8;
    public static final int HORA_ABERTURA = 7;
    public static final int HORA_FECHAMENTO = 22;
    public static final int DURACAO_MINIMA = 1;
    public static final int DURACAO_MAXIMA = 4;
    public static final int LIMITE_RESERVAS_ATIVAS_POR_ALUNO = 3;

    private final ArrayList<Reserva> reservas = new ArrayList<>();
    private final Notificador notificador;
    private final SalasRepository salasRepository;
    private int horaAtual;
    private int proximoId = 1;

    public Agenda(int horaAtual, Notificador notificador, SalasRepository salasRepository) {
        this.horaAtual = horaAtual;
        this.notificador = notificador;
        this.salasRepository = salasRepository;
    }

    public void setHoraAtual(int horaAtual) {
        this.horaAtual = horaAtual;
    }

    public Reserva reservar(Sala sala, String matricula, int horaInicio, int duracao, int participantes) {
        if (sala == null) {
            throw new IllegalArgumentException("A sala é obrigatória");
        }
        if (matricula == null || matricula.length() != TAMANHO_MATRICULA) {
            throw new IllegalArgumentException("Matrícula inválida: " + matricula);
        }
        if (duracao < DURACAO_MINIMA || duracao > DURACAO_MAXIMA) {
            throw new IllegalArgumentException("Duração fora do intervalo permitido: " + duracao);
        }
        int horaFim = horaInicio + duracao;
        if (horaInicio < HORA_ABERTURA || horaFim > HORA_FECHAMENTO) {
            throw new IllegalArgumentException("Fora do horário de funcionamento");
        }
        if (participantes < 1 || participantes > sala.getCapacidade()) {
            throw new IllegalArgumentException("Número de participantes inválido: " + participantes);
        }
        if (horaInicio < horaAtual) {
            throw new ReservaRecusadaException("Não é possível reservar um horário que já passou");
        }
        if (existeConflito(sala, horaInicio, horaFim)) {
            throw new ReservaRecusadaException("Sala já reservada no horário solicitado");
        }
        if (contarAtivasDoAluno(matricula) >= LIMITE_RESERVAS_ATIVAS_POR_ALUNO) {
            throw new ReservaRecusadaException("Limite de reservas ativas atingido");
        }

        Sala salaDoRepo = salasRepository.getByCode(sala.getCodigo());
        Reserva reserva = new Reserva(proximoId, salaDoRepo, matricula, horaInicio, horaFim, participantes);
        proximoId++;
        reservas.add(reserva);
        if (notificador != null) notificador.reservaConfirmada(reserva);
        return reserva;
    }

    public void cancelar(int id) {
        Reserva reserva = buscar(id);
        if (reserva == null) {
            throw new IllegalArgumentException("Reserva inexistente: " + id);
        }
        if (!reserva.isAtiva()) {
            throw new IllegalStateException("Reserva já cancelada: " + id);
        }
        if (horaAtual >= reserva.getHoraInicio()) {
            throw new ReservaRecusadaException("Reserva já iniciada não pode ser cancelada");
        }
        reserva.cancelar();
        notificador.reservaCancelada(reserva);
    }

    /** Retorna a reserva com o id informado, ou null se não existir. */
    public Reserva buscar(int id) {
        for (Reserva reserva : reservas) {
            if (reserva.getId() == id) {
                return reserva;
            }
        }
        return null;
    }

    /** Reservas ativas da sala, em ordem crescente de horário de início. */
    public ArrayList<Reserva> reservasDaSala(Sala sala) {
        ArrayList<Reserva> resultado = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.isAtiva() && reserva.ehDaSala(sala)) {
                // procura a posição que mantém a lista em ordem de horário
                int posicao = 0;
                while (posicao < resultado.size()
                        && resultado.get(posicao).getHoraInicio() < reserva.getHoraInicio()) {
                    posicao++;
                }
                resultado.add(posicao, reserva);
            }
        }
        return resultado;
    }

    public int contarAtivasDoAluno(String matricula) {
        int total = 0;
        for (Reserva reserva : reservas) {
            if (reserva.isAtiva() && reserva.getMatricula().equals(matricula)) {
                total++;
            }
        }
        return total;
    }

    private boolean existeConflito(Sala sala, int horaInicio, int horaFim) {
        for (Reserva reserva : reservas) {
            if (reserva.isAtiva() && reserva.ehDaSala(sala) && reserva.conflitaCom(horaInicio, horaFim)) {
                return true;
            }
        }
        return false;
    }
}
