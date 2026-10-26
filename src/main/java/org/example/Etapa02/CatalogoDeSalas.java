package org.example.Etapa02;

import java.util.ArrayList;

/**
 * Catálogo único de salas da biblioteca.
 * Por ser "static", o conteúdo é compartilhado por todo o programa
 * e permanece guardado até que alguém chame limpar().
 */
public class CatalogoDeSalas {

    private static final ArrayList<Sala> salas = new ArrayList<>();

    public static void cadastrar(Sala sala) {
        if (sala == null) {
            throw new IllegalArgumentException("A sala é obrigatória");
        }
        if (buscar(sala.getCodigo()) != null) {
            throw new IllegalArgumentException("Sala já cadastrada: " + sala.getCodigo());
        }
        salas.add(sala);
    }

    /** Retorna a sala com o código informado, ou null se não existir. */
    public static Sala buscar(String codigo) {
        for (Sala sala : salas) {
            if (sala.getCodigo().equals(codigo)) {
                return sala;
            }
        }
        return null;
    }

    public static int quantidade() {
        return salas.size();
    }

    public static void limpar() {
        salas.clear();
    }
}
