package org.example.Etapa03.estacionamento;

/** A placa já tem um ticket aberto. */
public class VeiculoJaEstacionadoException extends RuntimeException {

    public VeiculoJaEstacionadoException(String mensagem) {
        super(mensagem);
    }
}
