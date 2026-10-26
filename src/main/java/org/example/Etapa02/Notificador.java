package org.example.Etapa02;

/** Componente externo (e-mail, mensagem, etc.) avisado a cada confirmação ou cancelamento. */
public interface Notificador {

    void reservaConfirmada(Reserva reserva);

    void reservaCancelada(Reserva reserva);
}
