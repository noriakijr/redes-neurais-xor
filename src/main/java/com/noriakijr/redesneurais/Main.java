package com.noriakijr.redesneurais;

public class Main {

    private static final double INFORMACOES[][] = {{0, 1, 0, 1},
                                                   {0, 0, 1, 1}};
    private static final double ESPERADO[][] = {{0, 1, 1, 0}};
    
    public static void main(String[] args) {
        Treinamento t = new Treinamento(INFORMACOES, ESPERADO);
        t.treinarRede();
        double a1[][] = {{0},{1}};
        double a2[][] = {{1},{1}};
        double a3[][] = {{1},{0}};
        double a4[][] = {{0},{0}};
        t.classificar(a1);
        t.classificar(a2);
        t.classificar(a3);
        t.classificar(a4);
    }
}