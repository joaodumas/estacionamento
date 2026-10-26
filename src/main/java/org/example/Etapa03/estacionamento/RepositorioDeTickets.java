package org.example.Etapa03.estacionamento;

/** Banco de dados dos tickets. */
public interface RepositorioDeTickets {

    /** Grava um ticket novo ou atualiza um existente (mesmo número). */
    void salvar(Ticket ticket);

    /** Retorna o ticket ABERTO da placa, ou null se o veículo não estiver no estacionamento. */
    Ticket buscarAbertoPorPlaca(String placa);

    /** Quantidade de tickets abertos (veículos dentro do estacionamento). */
    int contarAbertos();
}
