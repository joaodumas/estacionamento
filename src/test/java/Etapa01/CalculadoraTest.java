package Etapa01;

import org.example.Etapa01.Calculadora;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculadoraTest {

    Calculadora calculadora = new Calculadora();

    @Test
    public void DeveRetornar2ParaSomar1e1(){

        int numA = 1;
        int numB = 1;
        int resultadoEsperado = 2;

        int soma = calculadora.somar(numA, numB);

        Assertions.assertEquals(
                resultadoEsperado,
                soma);
    }

    @Test
    public void DeveRetornar2ParaSubtracaoDe4e2(){

        int numA = 4;
        int numB = 2;
        int resultadoEsperado = 2;

        int subtracao = calculadora.subtrair(numA, numB);

        Assertions.assertEquals(resultadoEsperado, subtracao);
    }

    @Test
    public void DeveRetornar2ParaSubtracaoDe2e4() {

        int numA = 2;
        int numB = 4;
        int resultadoEsperado = 2;

        int subtracao = calculadora.subtrair(numA, numB);

        Assertions.assertEquals(resultadoEsperado, subtracao);

    }

    @Test
    public void DeveRetornar5ParaDivisaoDe10e2() {

        int divisao = calculadora.dividir(10, 2);

        Assertions.assertEquals(5,divisao);
    }

    @Test
    public void DeveRetornar0ParaDivisaoDe10e0() {

        int divisao = calculadora.dividir(10, 0);

        Assertions.assertEquals(0,divisao);
    }
}
