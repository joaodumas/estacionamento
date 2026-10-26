package org.example.Etapa01;

public class Calculadora {

    public int somar(int a, int b) {
        return a + b;
    }

    public int subtrair(int a, int b) {
        if (b > a) return b - a;
        return a - b;
    }

    public int multiplicar(int a, int b) {
        return a * b;
    }

    public int dividir(int a, int b) {
        if (b == 0) return 0;
        return a / b;
    }

}
