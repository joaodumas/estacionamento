package org.example.Etapa02;

public class NotificadorPorEmail implements Notificador {
    @Override
    public void reservaConfirmada(Reserva reserva) {
        // Consumir um serviço SMTP
    }

    @Override
    public void reservaCancelada(Reserva reserva) {

    }
}
