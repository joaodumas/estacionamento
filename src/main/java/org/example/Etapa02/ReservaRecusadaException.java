package org.example.Etapa02;

/** Lançada quando os dados são válidos, mas uma regra de negócio impede a operação. */
public class ReservaRecusadaException
        extends RuntimeException {

    public ReservaRecusadaException(String mensagem) {
        super(mensagem);
    }
}
