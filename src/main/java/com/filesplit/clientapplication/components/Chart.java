package com.filesplit.clientapplication.components;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategorySeriesLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class Chart extends ApplicationFrame {
    static long existing,propose;
    static String title;
    public Chart(String paramString){
        super(paramString);

        JPanel localJPanel = createDemoPanel();
        localJPanel.setPreferredSize(new Dimension(800, 370));
        JScrollPane jsp = new JScrollPane(localJPanel);
        setContentPane(localJPanel);
    }
    private static CategoryDataset createDataset(){
        DefaultCategoryDataset localDefaultCategoryDataset = new DefaultCategoryDataset();
        localDefaultCategoryDataset.addValue(ClientScreen.existing,"Greedy Time","Greedy Time");
        localDefaultCategoryDataset.addValue(ClientScreen.propose,"Drops Save Time","Drop Save Time");
        return localDefaultCategoryDataset;
    }
    public void windowClosing(WindowEvent we){
        this.setVisible(false);
    }
    private static JFreeChart createChart(CategoryDataset paramCategoryDataset){
        JFreeChart localJFreeChart = ChartFactory.createBarChart(title,"Technique Name (No Of Fragments : 10)", "Replication Time Save (Milli.Sec)", paramCategoryDataset, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot localCategoryPlot = (CategoryPlot)localJFreeChart.getPlot();
        localCategoryPlot.setDomainGridlinesVisible(true);
        localCategoryPlot.setRangeCrosshairVisible(true);
        localCategoryPlot.setRangeCrosshairPaint(Color.blue);
        NumberAxis localNumberAxis = (NumberAxis)localCategoryPlot.getRangeAxis();
        localNumberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer localBarRenderer = (BarRenderer)localCategoryPlot.getRenderer();
        localBarRenderer.setDrawBarOutline(false);
        GradientPaint localGradientPaint1 = new GradientPaint(0.0F, 0.0F, Color.blue, 0.0F, 0.0F, new Color(0, 0, 64));
        GradientPaint localGradientPaint2 = new GradientPaint(0.0F, 0.0F, Color.green, 0.0F, 0.0F, new Color(0, 64, 0));
        GradientPaint localGradientPaint3 = new GradientPaint(0.0F, 0.0F, Color.red, 0.0F, 0.0F, new Color(64, 0, 0));
        localBarRenderer.setSeriesPaint(0, localGradientPaint1);
        localBarRenderer.setSeriesPaint(1, localGradientPaint2);
        localBarRenderer.setSeriesPaint(2, localGradientPaint3);
        localBarRenderer.setLegendItemToolTipGenerator(new StandardCategorySeriesLabelGenerator("Tooltip: {0}"));
        CategoryAxis localCategoryAxis = localCategoryPlot.getDomainAxis();
        localCategoryAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.5235987755982988D));
        return localJFreeChart;
    }
    public static JPanel createDemoPanel(){
        JFreeChart localJFreeChart = createChart(createDataset());
        return new ChartPanel(localJFreeChart);
    }
}
