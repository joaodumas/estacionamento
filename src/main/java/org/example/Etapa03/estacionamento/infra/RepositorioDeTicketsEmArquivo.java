package br.edu.infnet.estacionamento.infra;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import br.edu.infnet.estacionamento.RepositorioDeTickets;
import br.edu.infnet.estacionamento.Ticket;

/**
 * Implementação de produção: grava os tickets em um arquivo CSV.
 * Formato de cada linha: numero;placa;entrada;saida;valorEmCentavos (saida vazia = aberto).
 */
public class RepositorioDeTicketsEmArquivo implements RepositorioDeTickets {

    private final Path arquivo;

    public RepositorioDeTicketsEmArquivo(Path arquivo) {
        this.arquivo = arquivo;
    }

    @Override
    public void salvar(Ticket ticket) {
        List<Ticket> todos = lerTodos();
        todos.removeIf(t -> t.getNumero() == ticket.getNumero());
        todos.add(ticket);
        List<String> linhas = new ArrayList<>();
        for (Ticket t : todos) {
            linhas.add(t.getNumero() + ";" + t.getPlaca() + ";" + t.getEntrada() + ";"
                    + (t.getSaida() == null ? "" : t.getSaida()) + ";" + t.getValorEmCentavos());
        }
        try {
            Files.write(arquivo, linhas);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Ticket buscarAbertoPorPlaca(String placa) {
        for (Ticket t : lerTodos()) {
            if (t.isAberto() && t.getPlaca().equals(placa)) {
                return t;
            }
        }
        return null;
    }

    @Override
    public int contarAbertos() {
        int total = 0;
        for (Ticket t : lerTodos()) {
            if (t.isAberto()) {
                total++;
            }
        }
        return total;
    }

    private List<Ticket> lerTodos() {
        List<Ticket> todos = new ArrayList<>();
        if (!Files.exists(arquivo)) {
            return todos;
        }
        try {
            for (String linha : Files.readAllLines(arquivo)) {
                String[] c = linha.split(";", -1);
                Instant saida = c[3].isEmpty() ? null : Instant.parse(c[3]);
                todos.add(new Ticket(Integer.parseInt(c[0]), c[1], Instant.parse(c[2]), saida,
                        Integer.parseInt(c[4])));
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return todos;
    }
}
