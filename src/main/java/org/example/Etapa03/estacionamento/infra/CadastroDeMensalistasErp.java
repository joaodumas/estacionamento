package br.edu.infnet.estacionamento.infra;

import br.edu.infnet.estacionamento.CadastroDeMensalistas;
import br.edu.infnet.estacionamento.Mensalista;

/** Implementação de produção (simulada): consulta o ERP da administradora pela rede interna. */
public class CadastroDeMensalistasErp implements CadastroDeMensalistas {

    @Override
    public Mensalista buscarPorPlaca(String placa) {
        try {
            Thread.sleep(3000);   // a VPN da administradora é lenta
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        throw new IllegalStateException("ERP indisponível fora da rede do shopping");
    }
}
