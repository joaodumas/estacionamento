package org.example.Etapa03.estacionamento;

import java.time.Instant;

/** Ticket emitido na entrada. Valores em centavos para evitar erros de arredondamento. */
public class Ticket {

    private final int numero;
    private final String placa;
    private final Instant entrada;
    private Instant saida;
    private int valorEmCentavos;

    public Ticket(int numero, String placa, Instant entrada) {
        this(numero, placa, entrada, null, 0);
    }

    /** Usado para reconstruir um ticket já gravado. */
    public Ticket(int numero, String placa, Instant entrada, Instant saida, int valorEmCentavos) {
        this.numero = numero;
        this.placa = placa;
        this.entrada = entrada;
        this.saida = saida;
        this.valorEmCentavos = valorEmCentavos;
    }

    void fechar(Instant saida, int valorEmCentavos) {
        this.saida = saida;
        this.valorEmCentavos = valorEmCentavos;
    }

    public boolean isAberto() {
        return saida == null;
    }

    public int getNumero() {
        return numero;
    }

    public String getPlaca() {
        return placa;
    }

    public Instant getEntrada() {
        return entrada;
    }

    public Instant getSaida() {
        return saida;
    }

    public int getValorEmCentavos() {
        return valorEmCentavos;
    }
}
