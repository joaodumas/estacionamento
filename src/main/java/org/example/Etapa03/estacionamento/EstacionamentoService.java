package org.example.Etapa03.estacionamento;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

/** Controle de entrada, saída e cobrança do estacionamento do shopping. */
public class EstacionamentoService {

    public static final int TOLERANCIA_EM_MINUTOS = 15;
    public static final int PRIMEIRA_HORA_EM_CENTAVOS = 10_00;
    public static final int HORA_ADICIONAL_EM_CENTAVOS = 5_00;
    public static final int TETO_EM_CENTAVOS = 50_00;

    private final RepositorioDeTickets tickets;
    private final CadastroDeMensalistas mensalistas;
    private final Clock relogio;
    private final int capacidade;
    private int proximoNumero = 1;

    public EstacionamentoService(RepositorioDeTickets tickets, CadastroDeMensalistas mensalistas,
                                 Clock relogio, int capacidade) {
        if (capacidade < 1) {
            throw new IllegalArgumentException("A capacidade deve ser de pelo menos 1 vaga");
        }
        this.tickets = tickets;
        this.mensalistas = mensalistas;
        this.relogio = relogio;
        this.capacidade = capacidade;
    }

    public Ticket registrarEntrada(String placaInformada) {
        String placa = normalizar(placaInformada);
        if (tickets.buscarAbertoPorPlaca(placa) != null) {
            throw new VeiculoJaEstacionadoException("O veículo " + placa + " já está no estacionamento");
        }
        if (tickets.contarAbertos() >= capacidade) {
            throw new EstacionamentoLotadoException("Estacionamento lotado");
        }
        Ticket ticket = new Ticket(proximoNumero, placa, Instant.now(relogio));
        proximoNumero++;
        tickets.salvar(ticket);
        return ticket;
    }

    public Ticket registrarSaida(String placaInformada) {
        String placa = normalizar(placaInformada);
        Ticket ticket = tickets.buscarAbertoPorPlaca(placa);
        if (ticket == null) {
            throw new TicketNaoEncontradoException("Não há ticket aberto para " + placa);
        }
        Instant agora = Instant.now(relogio);
        long minutos = Duration.between(ticket.getEntrada(), agora).toMinutes();

        Mensalista mensalista = mensalistas.buscarPorPlaca(placa);
        boolean isento = mensalista != null && mensalista.emDiaEm(LocalDate.now(relogio));
        int valor = isento ? 0 : calcularValor(minutos);

        ticket.fechar(agora, valor);
        tickets.salvar(ticket);
        return ticket;
    }

    public int vagasLivres() {
        return capacidade - tickets.contarAbertos();
    }

    /** Aceita "abc-1d23" e devolve "ABC1D23". Formatos aceitos: ABC1234 (antigo) e ABC1D23 (Mercosul). */
    private String normalizar(String placa) {
        if (placa == null) {
            throw new IllegalArgumentException("A placa é obrigatória");
        }
        String normalizada = placa.replace("-", "").trim().toUpperCase();
        if (!normalizada.matches("[A-Z]{3}[0-9][A-Z0-9][0-9]{2}")) {
            throw new IllegalArgumentException("Placa inválida: " + placa);
        }
        return normalizada;
    }

    private int calcularValor(long minutos) {
        if (minutos <= TOLERANCIA_EM_MINUTOS) {
            return 0;
        }
        if (minutos <= 60) {
            return PRIMEIRA_HORA_EM_CENTAVOS;
        }
        long horasAdicionais = (minutos - 60) / 60;
        long valor = PRIMEIRA_HORA_EM_CENTAVOS + horasAdicionais * HORA_ADICIONAL_EM_CENTAVOS;
        return (int) Math.min(valor, TETO_EM_CENTAVOS);
    }
}
