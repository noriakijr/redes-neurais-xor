package util;

import com.noriakijr.redesneurais.model.ModeloGraficoItem;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Grafico {

    public static BufferedImage gerarGraficoLinha(String tituloGrafico, String tituloEixoX, String tituloEixoY, ArrayList arrayValores) throws Exception {
        BufferedImage buf = null;
        try {
            DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
            Iterator iterator = arrayValores.iterator();
            while (iterator.hasNext()) {
                ModeloGraficoItem modelo = (ModeloGraficoItem) iterator.next();
                defaultCategoryDataset.addValue(modelo.getQuantidade(), modelo.getErro(), modelo.getEpoca());
            }
            JFreeChart chart = ChartFactory.createLineChart(tituloGrafico, tituloEixoX, tituloEixoY, defaultCategoryDataset, PlotOrientation.VERTICAL, true, false, false);
            chart.setBorderVisible(true);
            chart.setBorderPaint(Color.black);
            buf = chart.createBufferedImage(700, 250);

        } catch (Exception e) {
            throw new Exception(e);
        }
        return buf;
    }

    public static BufferedImage gerarGraficoLinha3D(String tituloGrafico, String tituloEixoX, String tituloEixoY, ArrayList arrayValores) throws Exception {

        BufferedImage buf = null;
        try {
            DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
            Iterator iterator = arrayValores.iterator();
            while (iterator.hasNext()) {
                ModeloGraficoItem modelo = (ModeloGraficoItem) iterator.next();
                defaultCategoryDataset.addValue(modelo.getQuantidade(), modelo.getErro(), modelo.getEpoca());
            }
            JFreeChart chart = ChartFactory.createLineChart3D(tituloGrafico, tituloEixoX, tituloEixoY, defaultCategoryDataset, PlotOrientation.VERTICAL, true, false, false);
            chart.setBorderVisible(true);
            chart.setBorderPaint(Color.black);
            buf = chart.createBufferedImage(400, 250);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return buf;
    }
    
    public static void gerarImagem(String tituloGrafico, String tituloEixoX, String tituloEixoY, ArrayList arrayValores, String extensao) {
        try {
            ImageIcon grafico = new ImageIcon(Grafico.gerarGraficoLinha(tituloGrafico, tituloEixoX, tituloEixoY, arrayValores));
            Image img = grafico.getImage();
            BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);

            Graphics2D g2 = bi.createGraphics();
            g2.drawImage(img, 0, 0, null);
            g2.dispose();
            ImageIO.write(bi, extensao, new File("img." + extensao));
        } catch (Exception e) {
        }
    }
}
