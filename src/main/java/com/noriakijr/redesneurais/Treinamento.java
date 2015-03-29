package com.noriakijr.redesneurais;

import com.noriakijr.redesneurais.model.RedeNeural;
import com.noriakijr.redesneurais.model.ModeloGraficoItem;
import com.noriakijr.redesneurais.model.Neuronio;
import java.util.ArrayList;
import java.util.List;
import util.Grafico;

public class Treinamento {

    private static double INFORMACOES[][];
    private static double DESEJADO[][];
    private static final double ERRO_MINIMO = 0.05;
    private static final double TAXA_APRENDIZAGEM = 0.4;
    private int epocas;
    private RedeNeural rede = new RedeNeural();

    public Treinamento(double informacoes[][], double desejado[][]) {
        INFORMACOES = informacoes;
        DESEJADO = desejado;
        // preencher a camada de entrada e inicializar as outras camadas
        rede.inicializar(INFORMACOES.length, 2, DESEJADO.length);
    }

    // colocando valores de entradas em seus respectivos neuronios
    private void inicializarEntrada(List<Neuronio> camadaEntrada, double informacoes[][], int i) {
        for(int j = 0; j < camadaEntrada.size(); j++)
            camadaEntrada.get(j).getSinapses().get(0).setValor(informacoes[j][i]);
    }
    
    public void treinarRede() {
        double erroSaida = 1;
        double peso;
        ArrayList<ModeloGraficoItem> list = new ArrayList<>();
        
        System.out.println("Treinando rede...");
        for(epocas = 0; Math.abs(erroSaida) > ERRO_MINIMO && epocas < 20000; epocas++) {
            for (int i = 0; i < INFORMACOES[0].length; i++) {
                // inicializar as entradas, colocando valores de entrada para 1 saida logica nos 2 neuronios de entrada
                inicializarEntrada(rede.getCamadaEntrada(), INFORMACOES, i);
                
                for (int j = 0; j < rede.getCamadaSaida().size(); j++) {
                    // fase de feedforward, gerando a saida atraves da recursividade, posteriormente calculando o erro
                    erroSaida = calcularErroSaida(DESEJADO[j][i], rede.getCamadaSaida().get(j).getSaida());
                    // calculando o gradiente para a camada de saida que sera utilizada no Backpropagation
                    rede.getCamadaSaida().get(j).setGradiente(calcularGradienteSaida(rede.getCamadaSaida().get(j).getSaida()) * erroSaida);
                }
                
                // zerando valores nas sinapses que não serão mais utilizados e que impediria a execucao da recursividade
                rede.zeraValorSinapse(rede.getCamadaEntrada(), rede.getCamadaOculta(), rede.getCamadaSaida());
                
                for (int j = 0; j < rede.getCamadaSaida().size(); j++) {
                    // fase de feedforward, gerando a saida atraves da recursividade, posteriormente calculando o erro
                    erroSaida = calcularErroSaida(DESEJADO[j][i], rede.getCamadaSaida().get(j).getSaida());
                    // calculando o gradiente para a camada de saida que sera utilizada no Backpropagation
                    rede.getCamadaSaida().get(j).setGradiente(calcularGradienteSaida(rede.getCamadaSaida().get(j).getSaida()) * erroSaida);
                    
                    for (int k = 0; k < rede.getCamadaSaida().get(j).getSinapses().size(); k++) {
                        // calculando o gradiente dos neuronios da(s) camada(s) oculta(s)
                        rede.getCamadaSaida().get(j).getSinapses().get(k).getDestino().setGradiente(
                                calcularGradienteOculta(rede.getCamadaSaida().get(j).getSinapses().get(k).getDestino().getSaida(), 
                                rede.getCamadaSaida().get(j).getGradiente(), rede.getCamadaSaida().get(j).getSinapses().get(k).getPeso()));
                    }
                }

                // fazendo o ajuste dos pesos das sinapses de acordo com os gradientes calculados anteriormente
                for (int j = 0; j < rede.getCamadaSaida().size(); j++) {
                    // recalculando os bias da camada de saida
                    rede.getCamadaSaida().get(j).setBias(rede.getCamadaSaida().get(j).getBias() 
                            + TAXA_APRENDIZAGEM * rede.getCamadaSaida().get(j).getGradiente());
                    for (int k = 0; k < rede.getCamadaSaida().get(j).getSinapses().size(); k++) {
                        peso = rede.getCamadaSaida().get(j).getSinapses().get(k).getPeso()
                                + TAXA_APRENDIZAGEM * rede.getCamadaSaida().get(j).getGradiente()
                                * rede.getCamadaSaida().get(j).getSinapses().get(k).getValor();
                        rede.getCamadaSaida().get(j).getSinapses().get(k).setPeso(peso);
                    }
                }

                // fazendo o ajuste dos pesos das sinapses de acordo com os gradientes calculados anteriormente
                for (int j = 0; j < rede.getCamadaOculta().size(); j++) {
                    // recalculando os bias da camada oculta
                    rede.getCamadaOculta().get(j).setBias(rede.getCamadaOculta().get(j).getBias() + TAXA_APRENDIZAGEM * rede.getCamadaOculta().get(j).getGradiente());
                    for (int k = 0; k < rede.getCamadaOculta().get(j).getSinapses().size(); k++) {
                        peso = rede.getCamadaOculta().get(j).getSinapses().get(k).getPeso()
                                + TAXA_APRENDIZAGEM * rede.getCamadaOculta().get(j).getGradiente()
                                * rede.getCamadaOculta().get(j).getSinapses().get(k).getDestino().getSinapses().get(0).getValor();
                        rede.getCamadaOculta().get(j).getSinapses().get(k).setPeso(peso);
                    }
                }
            }
            // list para armazenar o erro e montar o grafico
            list.add(new ModeloGraficoItem(Integer.toString(epocas), "Erro", Math.abs(erroSaida)));
        }
        // gerar imagem do grafico
        Grafico.gerarImagem("Valor do erro", "Epoca", "Erro", list, "jpg");
    }
    
    public void classificar(double informacoes[][]) {
        System.out.println("\nClassificando XOR...");
        for (int i = 0; i < informacoes[0].length; i++) {
            inicializarEntrada(rede.getCamadaEntrada(), informacoes, i);
            rede.zeraValorSinapse(rede.getCamadaEntrada(), rede.getCamadaOculta(), rede.getCamadaSaida());
            for(int j = 0; j < rede.getCamadaSaida().size(); j++) {
                System.out.println("Saída para " + informacoes[0][i] + " e " + informacoes[1][i] + ": " + Math.round(rede.getCamadaSaida().get(j).getSaida()));
            }
        }
    }
    
    private double calcularDerivadaSigmoidal(double valor) {
        return valor * (1 - valor);
    }

    private double calcularErroSaida(double valorEsperado, double valorSaida) {
        return (valorEsperado - valorSaida);
    }

    private double calcularSigmoidal(double valor) {
        return Math.pow((1 - valor), 2);
    }
    
    private double calcularGradienteSaida(double valorSaida) {
        return valorSaida * calcularSigmoidal(valorSaida);
    }
    
    private double calcularGradienteOculta(double valorSaida, double gradiente, double peso) {
        return calcularDerivadaSigmoidal(valorSaida) * gradiente * peso;
    }
}
