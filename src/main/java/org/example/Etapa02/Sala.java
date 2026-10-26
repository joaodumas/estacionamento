package org.example.Etapa02;

/**
 * Sala de estudo disponível para reserva.
 * Exemplo de código válido: "SL101" (prefixo "SL" seguido de três caracteres).
 */
public class Sala {

    public static final String PREFIXO_CODIGO = "SL";
    public static final int TAMANHO_CODIGO = 5;
    public static final int CAPACIDADE_MINIMA = 1;
    public static final int CAPACIDADE_MAXIMA = 40;

    private final String codigo;
    private final int capacidade;

    public Sala(String codigo, int capacidade) {
        if (codigo == null || codigo.length() != TAMANHO_CODIGO || !codigo.startsWith(PREFIXO_CODIGO)) {
            throw new IllegalArgumentException("Código de sala inválido: " + codigo);
        }
        if (capacidade < CAPACIDADE_MINIMA || capacidade > CAPACIDADE_MAXIMA) {
            throw new IllegalArgumentException("Capacidade fora do intervalo permitido: " + capacidade);
        }
        this.codigo = codigo;
        this.capacidade = capacidade;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getCapacidade() {
        return capacidade;
    }
}
