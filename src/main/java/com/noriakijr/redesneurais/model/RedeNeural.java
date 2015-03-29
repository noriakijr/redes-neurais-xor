package com.noriakijr.redesneurais.model;

import com.noriakijr.redesneurais.model.Neuronio;
import com.noriakijr.redesneurais.model.Sinapse;
import java.util.ArrayList;
import java.util.List;

public class RedeNeural {

    private List<Neuronio> camadaEntrada = new ArrayList<>();
    private List<Neuronio> camadaOculta = new ArrayList<>();
    private List<Neuronio> camadaSaida = new ArrayList<>();

    public void inicializar(int entradaSize, int ocultaSize, int saidaSize) {
        // prencher a camada de entrada com bias 0
        for (int i = 0; i < entradaSize; i++) { camadaEntrada.add(new Neuronio(0)); }
        // preenchendo as camadas ocultas e de saida com bias aleatorios
        for (int i = 0; i < ocultaSize; i++) { camadaOculta.add(new Neuronio(Math.random())); }
        for (int i = 0; i < saidaSize; i++) { camadaSaida.add(new Neuronio(Math.random())); }
        // ligar todos os neuronios e inicializar os pesos
        criarSinapses(camadaEntrada, camadaOculta, camadaSaida);
    }

    private void criarSinapses(List<Neuronio> camadaEntrada, List<Neuronio> camadaOculta, List<Neuronio> camadaSaida) {
        // loops para ligacao dos neuronios em todas as camadas com pesos aleatorios
        for (Neuronio neuronioSaida : camadaSaida) {
            for (Neuronio neuronioOculto : camadaOculta) {
                neuronioSaida.addSinapse(neuronioOculto, Math.random());
            }
        }
        for (Neuronio neuronioOculto : camadaOculta) {
            for (Neuronio neuronioEntrada : camadaEntrada) {
                neuronioOculto.addSinapse(neuronioEntrada, Math.random());
            }
        }
        // criando "sinapses" para a camada de entrada com peso 1 para que nao haja alteracao na entrada de valores
        for (Neuronio neuronioEntrada : camadaEntrada) { neuronioEntrada.addSinapse(null, 1); }
    }

    // zerando valores nas sinapses que não serão mais utilizados e que impediria a execucao da recursividade
    public void zeraValorSinapse(List<Neuronio> camadaEntrada, List<Neuronio> camadaOculta, List<Neuronio> camadaSaida) {
        for (Neuronio neuronioSaida : camadaSaida) {
            for (Sinapse s : neuronioSaida.getSinapses()) {
                s.setValor(null);
            }
        }
        for (Neuronio neuronioOculto : camadaOculta) {
            for (Sinapse s : neuronioOculto.getSinapses()) {
                s.setValor(null);
            }
        }
    }

    public List<Neuronio> getCamadaEntrada() {
        return camadaEntrada;
    }

    public void setCamadaEntrada(List<Neuronio> camadaEntrada) {
        this.camadaEntrada = camadaEntrada;
    }

    public List<Neuronio> getCamadaOculta() {
        return camadaOculta;
    }

    public void setCamadaOculta(List<Neuronio> camadaOculta) {
        this.camadaOculta = camadaOculta;
    }

    public List<Neuronio> getCamadaSaida() {
        return camadaSaida;
    }

    public void setCamadaSaida(List<Neuronio> camadaSaida) {
        this.camadaSaida = camadaSaida;
    }

}
