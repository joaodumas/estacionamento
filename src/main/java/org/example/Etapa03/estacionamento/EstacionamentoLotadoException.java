package org.example.Etapa03.estacionamento;

/** Não há vagas livres. */
public class EstacionamentoLotadoException extends RuntimeException {

    public EstacionamentoLotadoException(String mensagem) {
        super(mensagem);
    }
}
