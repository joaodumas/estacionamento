package Canary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CanaryTest {

//    @Test
    public void DeveValidarTrueQuantoComparadoComTrue() {
        // se true == true
        Assertions.assertTrue(true);
    }

//    @Test
    public void DeveInvalidarTrueQuandoComparadoComFalse() {
        Assertions.assertFalse(true);
    }
}
