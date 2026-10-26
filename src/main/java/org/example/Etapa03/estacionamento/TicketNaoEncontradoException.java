package org.example.Etapa03.estacionamento;

/** Não existe ticket aberto para a placa. */
public class TicketNaoEncontradoException extends RuntimeException {

    public TicketNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
