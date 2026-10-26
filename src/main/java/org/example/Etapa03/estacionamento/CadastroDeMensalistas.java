package org.example.Etapa03.estacionamento;

/** Consulta ao ERP da administradora do shopping, onde ficam os planos mensais. */
public interface CadastroDeMensalistas {

    /** Retorna o mensalista da placa, ou null se a placa não tiver plano. */
    Mensalista buscarPorPlaca(String placa);
}
