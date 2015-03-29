package com.noriakijr.redesneurais.model;

import com.noriakijr.redesneurais.strategy.FuncaoAtivacaoSigmoidal;
import com.noriakijr.redesneurais.strategy.IStrategy;
import java.util.ArrayList;
import java.util.List;

public class Neuronio {

    private List<Sinapse> sinapses = new ArrayList<Sinapse>();
    private double gradiente;
    private double bias;

    public Neuronio(double bias) {
        this.bias = bias;
    }
    
    public void addSinapse(Neuronio destino, double peso) {
        sinapses.add(new Sinapse(this, destino, peso));
    }

    // removeSinapse...
    
    public double ativar(double somatoria) {
        IStrategy funcaoAtivacao = new FuncaoAtivacaoSigmoidal();
        return funcaoAtivacao.calcular(somatoria);
    }

    public List<Sinapse> getEntrada(Neuronio origem) {
//        List<Sinapse> sinapsesEntrada = new ArrayList<>();
//        for(int i = 0; i < origem.getSinapses().size(); i++) {
//            if(this.equals(origem.getSinapses().get(i).getDestino()))
//                sinapsesEntrada.add(origem.getSinapses().get(i));
//        }
//        return sinapsesEntrada;
        return sinapses;
    }

    public double getSaida() {
        double soma = 0;
        
        for (Sinapse sinapse : sinapses) {
            if(sinapse.getDestino() == null)
                return sinapse.getValor();
            soma += sinapse.getValor() * sinapse.getPeso();
        }        
        return ativar(soma + bias);
    }

    public List<Sinapse> getSinapses() {
        return sinapses;
    }

    public void setSinapses(List<Sinapse> sinapses) {
        this.sinapses = sinapses;
    }

    public double getGradiente() {
        return gradiente;
    }

    public void setGradiente(double gradiente) {
        this.gradiente = gradiente;
    }

    public double getBias() {
        return bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }
    
}
