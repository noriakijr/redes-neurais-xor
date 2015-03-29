package com.noriakijr.redesneurais.model;

public class Sinapse {

    private Neuronio origem;
    private Neuronio destino;
    private double peso;
    private Double valor;

    public Double getValor() {
        if (valor != null) {
            return valor;
        } else {
            valor = destino.getSaida();
            return valor;
        }
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Sinapse(Neuronio origem, Neuronio destino, double peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    public Neuronio getOrigem() {
        return origem;
    }

    public void setOrigem(Neuronio origem) {
        this.origem = origem;
    }

    public Neuronio getDestino() {
        return destino;
    }

    public void setDestino(Neuronio destino) {
        this.destino = destino;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public Sinapse(Neuronio origem, Neuronio destino, double peso, double valor) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
        this.valor = valor;
    }
}
