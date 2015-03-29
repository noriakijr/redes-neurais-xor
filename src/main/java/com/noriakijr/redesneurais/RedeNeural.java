package com.noriakijr.redesneurais;

import com.noriakijr.redesneurais.model.Neuronio;
import com.noriakijr.redesneurais.model.Sinapse;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import util.Grafico;
import com.noriakijr.redesneurais.model.ModeloGraficoItem;

public class RedeNeural {

    private static final double INFORMACOES[][] = {{0, 1, 0, 1},
    {0, 0, 1, 1}};
    private static final double ESPERADO[] = {0, 1, 1, 0};
    private static final double TAXA_APRENDIZAGEM = 0.4;
    private List<Neuronio> camadaEntrada = new ArrayList<>();

    private List<Neuronio> camadaSaida = new ArrayList<>();

    private void inicializar(List<Neuronio> camadaEntrada, List<Neuronio> camadaOculta, List<Neuronio> camadaSaida, double informacoes[][]) {
        // prencher a camada de entrada com bias 0
        for (int j = 0; j < informacoes.length; j++) {
            camadaEntrada.add(new Neuronio(0));
        }
        // preenchendo as camadas ocultas e de saida com bias aleatorios
        for (int j = 0; j < 2; j++) {
            camadaOculta.add(new Neuronio(Math.random()));
        }
        for (int j = 0; j < 1; j++) {
            camadaSaida.add(new Neuronio(Math.random()));
        }
    }

    public void criarSinapses(List<Neuronio> camadaEntrada, List<Neuronio> camadaOculta, List<Neuronio> camadaSaida) {
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
        for (Neuronio neuronioEntrada : camadaEntrada) {
            neuronioEntrada.addSinapse(null, 1);
        }
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

    // colocando valores de entradas em seus respectivos neuronios
    public void inicializarEntrada(List<Neuronio> camadaEntrada, double informacoes[][], int i) {
        camadaEntrada.get(0).getSinapses().get(0).setValor(informacoes[0][i]);
        camadaEntrada.get(1).getSinapses().get(0).setValor(informacoes[1][i]);
    }

    public void treinarRede() {
        List<Neuronio> camadaOculta = new ArrayList<>();
        double erroSaida = 9999;
        double peso;
        int epocas = 0;
        ArrayList<ModeloGraficoItem> list = new ArrayList<>();

        // preencher a camada de entrada e inicializar as outras camadas
        inicializar(camadaEntrada, camadaOculta, camadaSaida, INFORMACOES);
        // montar a estrutura
        criarSinapses(camadaEntrada, camadaOculta, camadaSaida);
        System.out.println("Treinando rede...");
        while (Math.abs(erroSaida) > 0.05 && epocas < 500000) {
            for (int i = 0; i < INFORMACOES[0].length; i++) {
                // inicializar as entradas, colocando valores de entrada para 1 saida logica nos 2 neuronios de entrada
                inicializarEntrada(camadaEntrada, INFORMACOES, i);
                // zerando valores nas sinapses que não serão mais utilizados e que impediria a execucao da recursividade
                zeraValorSinapse(camadaEntrada, camadaOculta, camadaSaida);
                // fase de feedforward, gerando a saida atraves da recursividade, posteriormente calculando o erro
                erroSaida = calcularErroSaida(ESPERADO[i], camadaSaida.get(0).getSaida());
                // calculando o gradiente para a camada de saida que sera utilizada no Backpropagation
                camadaSaida.get(0).setGradiente(calcularGradienteSaida(camadaSaida.get(0).getSaida()) * erroSaida);

                // calculando o gradiente dos neuronios da(s) camada(s) oculta(s)
                for (int k = 0; k < camadaSaida.size(); k++) {
                    for (int l = 0; l < camadaSaida.get(k).getSinapses().size(); l++) {
                        camadaSaida.get(k).getSinapses().get(l).getDestino().setGradiente(calcularDerivadaSigmoidal(camadaSaida.get(k).getSinapses().get(l).getDestino().getSaida()) * camadaSaida.get(k).getGradiente() * camadaSaida.get(k).getSinapses().get(l).getPeso());
                    }
                }

                // fazendo o ajuste dos pesos das sinapses de acordo com os gradientes calculados anteriormente
                for (int l = 0; l < camadaSaida.size(); l++) {
                    // recalculando os bias da camada de saida
                    camadaSaida.get(l).setBias(camadaSaida.get(l).getBias() + TAXA_APRENDIZAGEM * camadaSaida.get(l).getGradiente());
                    for (int m = 0; m < camadaSaida.get(l).getSinapses().size(); m++) {
                        peso = camadaSaida.get(l).getSinapses().get(m).getPeso()
                                + TAXA_APRENDIZAGEM * camadaSaida.get(l).getGradiente()
                                * camadaSaida.get(l).getSinapses().get(m).getValor();
                        camadaSaida.get(l).getSinapses().get(m).setPeso(peso);
                    }
                }

                // fazendo o ajuste dos pesos das sinapses de acordo com os gradientes calculados anteriormente
                for (int j = 0; j < camadaOculta.size(); j++) {
                    // recalculando os bias da camada oculta
                    camadaOculta.get(j).setBias(camadaOculta.get(j).getBias() + TAXA_APRENDIZAGEM * camadaOculta.get(j).getGradiente());
                    for (int l = 0; l < camadaOculta.get(j).getSinapses().size(); l++) {
                        peso = camadaOculta.get(j).getSinapses().get(l).getPeso()
                                + TAXA_APRENDIZAGEM * camadaOculta.get(j).getGradiente()
                                * camadaOculta.get(j).getSinapses().get(l).getDestino().getSinapses().get(0).getValor();
                        camadaOculta.get(j).getSinapses().get(l).setPeso(peso);
                    }
                }
            }
            list.add(new ModeloGraficoItem(Integer.toString(epocas), "Erro", Math.abs(erroSaida)));
            epocas++;
        }
        try {
            ImageIcon grafico = new ImageIcon(Grafico.gerarGraficoLinha("Valor do erro", "Epoca", "Erro", list));
            Image img = grafico.getImage();
            BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);

            Graphics2D g2 = bi.createGraphics();
            g2.drawImage(img, 0, 0, null);
            g2.dispose();
            ImageIO.write(bi, "jpg", new File("img.jpg"));
        } catch (Exception e) {
        }
        System.out.println("\nClassificando XOR...");
        for (int i = 0; i < INFORMACOES[0].length; i++) {
            inicializarEntrada(camadaEntrada, INFORMACOES, i);
            zeraValorSinapse(camadaEntrada, camadaOculta, camadaSaida);
            System.out.println("Saída para " + INFORMACOES[0][i] + " e " + INFORMACOES[1][i] + ": " + Math.round(camadaSaida.get(0).getSaida()));
        }
    }

    public List<Neuronio> getCamadaEntrada() {
        return camadaEntrada;
    }

    public void setCamadaEntrada(List<Neuronio> camadaEntrada) {
        this.camadaEntrada = camadaEntrada;
    }

    public List<Neuronio> getCamadaSaida() {
        return camadaSaida;
    }

    public void setCamadaSaida(List<Neuronio> camadaSaida) {
        this.camadaSaida = camadaSaida;
    }

    private double calcularDerivadaSigmoidal(double valor) {
        return valor * (1 - valor);
    }

    private double calcularErroSaida(double valorEsperado, double valorSaida) {
        return (valorEsperado - valorSaida);
    }

    private double calcularGradienteSaida(double valorSaida) {
        return valorSaida * Math.pow((1 - valorSaida), 2);
    }
}
