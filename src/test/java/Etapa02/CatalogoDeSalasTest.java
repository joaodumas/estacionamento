package Etapa02;

import org.example.Etapa02.CatalogoDeSalas;
import org.example.Etapa02.Sala;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

class SalaDummy extends Sala {
    public SalaDummy() {
        super("SL101", 6);
    }
}

public class CatalogoDeSalasTest {

    private final String CODIGO_SALA = "SL101";
    private final int CAPACIDADE = 6;

//    @BeforeEach
//    @BeforeAll
//    @AfterAll
    @AfterEach
    void limpar() {
        CatalogoDeSalas.limpar();
    }

    @Test void cadastrar() {
        Sala sala = new SalaDummy();

        CatalogoDeSalas.cadastrar(sala);

        assertEquals(1, CatalogoDeSalas.quantidade());
        assertSame(sala, CatalogoDeSalas.buscar(CODIGO_SALA));
    }

    @Test void cadastrarCodigoDuplicado() {
        CatalogoDeSalas.cadastrar(new SalaDummy());

        assertThrows(IllegalArgumentException.class,
                () -> CatalogoDeSalas
                        .cadastrar(new SalaDummy())
        );
        assertEquals(1, CatalogoDeSalas.quantidade());
    }
}
