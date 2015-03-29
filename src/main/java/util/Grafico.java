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
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;

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
}
