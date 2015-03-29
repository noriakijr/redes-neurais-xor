package com.noriakijr.redesneurais.model;

import java.io.Serializable;

public class ModeloGraficoItem implements Serializable {

    private String epoca;
    private String erro;
    private double quantidade;

    public ModeloGraficoItem(String epoca, String erro, double quantidade) {
        this.epoca = epoca;
        this.erro = erro;
        this.quantidade = quantidade;
    }

    public ModeloGraficoItem() {

    }

    public String getEpoca() {
        return epoca;
    }

    public void setEpoca(String epoca) {
        this.epoca = epoca;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }
}
