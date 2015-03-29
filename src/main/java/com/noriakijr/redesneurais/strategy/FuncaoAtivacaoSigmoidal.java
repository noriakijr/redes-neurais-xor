package com.noriakijr.redesneurais.strategy;

public class FuncaoAtivacaoSigmoidal implements IStrategy{

    public double calcular(double valor) {
        return 1/(1 + Math.exp(-(valor)));
    }
}
