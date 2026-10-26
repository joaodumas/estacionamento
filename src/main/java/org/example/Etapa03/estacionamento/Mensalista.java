package org.example.Etapa03.estacionamento;

import java.time.LocalDate;

/** Cliente com plano mensal. A mensalidade cobre até o dia "pagoAte", inclusive. */
public record Mensalista(String placa, String nome, LocalDate pagoAte) {

    public boolean emDiaEm(LocalDate data) {
        return !data.isAfter(pagoAte);
    }
}
