package GUI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import trajectory.Point;
import trajectory.Trajectory;

import javax.swing.*;
import java.util.List;
import java.util.SortedMap;

public class Graph {
    private final XYSeries VxSeries = new XYSeries("Vx");
    private final XYSeries VySeries = new XYSeries("Vy");
    private final XYSeries VzSeries = new XYSeries("Vz");
    private final XYSeries XSeries = new XYSeries("X");
    private final XYSeries YSeries = new XYSeries("Y");
    private final XYSeries ZSeries = new XYSeries("Z");

    private final XYSeriesCollection SpeedDataset = new XYSeriesCollection();
    private final XYSeriesCollection CorDataset = new XYSeriesCollection();
    private final JFreeChart SpeedChart = ChartFactory.createXYLineChart("График", "Время", "Скорость", SpeedDataset);
    private final JFreeChart CorChart = ChartFactory.createXYLineChart("График", "Время", "Координаты", CorDataset);
    private ChartPanel chartPanel;
    private JCheckBox XCheckBox;
    private JCheckBox YCheckBox;
    private JCheckBox ZCheckBox;
    private JCheckBox VzCheckBox;
    private JCheckBox VyCheckBox;
    private JCheckBox VxCheckBox;

    private List<Double> times;
    private SortedMap<Double, Point> points;

    public Graph(JCheckBox XCheckBox, JCheckBox YCheckBox, JCheckBox ZCheckBox, JCheckBox VxCheckBox, JCheckBox VyCheckBox, JCheckBox VzCheckBox){
        SpeedDataset.addSeries(VxSeries);
        SpeedDataset.addSeries(VySeries);
        SpeedDataset.addSeries(VzSeries);
        CorDataset.addSeries(XSeries);
        CorDataset.addSeries(YSeries);
        CorDataset.addSeries(ZSeries);


        XYPlot SpeedPlot = SpeedChart.getXYPlot();
        XYPlot CorPlot = CorChart.getXYPlot();
        SpeedPlot.setDomainAxis(new NumberAxis("Время"));
        SpeedPlot.setRangeAxis(new NumberAxis("Скорость"));
        CorPlot.setDomainAxis(new NumberAxis("Время"));
        CorPlot.setRangeAxis(new NumberAxis("Координаты"));

        this.XCheckBox = XCheckBox;
        this.YCheckBox = YCheckBox;
        this.ZCheckBox = ZCheckBox;
        this.VxCheckBox = VxCheckBox;
        this.VyCheckBox = VyCheckBox;
        this.VzCheckBox = VzCheckBox;
        addListeners();
        chartPanel = new ChartPanel(SpeedChart);
    }

    public ChartPanel getChartPanel(){
        return chartPanel;
    }

    public void updateGraph(Trajectory trajectory){
        XSeries.clear();
        YSeries.clear();
        ZSeries.clear();
        VxSeries.clear();
        VySeries.clear();
        VzSeries.clear();
        times = trajectory.getTimes();
        points = trajectory.getPoints();

        showX();
        showY();
        showZ();
        showVx();
        showVy();
        showVz();
    }

    private void showX(){
        if(XCheckBox.isSelected() && points != null){
            chartPanel.setChart(CorChart);
            for(Double time : times){
                Point point = points.get(time);
                XSeries.add(time, point.getX());
            }
        }
    }

    private void showY(){
        if(YCheckBox.isSelected() && points != null){
            chartPanel.setChart(CorChart);
            for(Double time : times){
                Point point = points.get(time);
                YSeries.add(time, point.getY());
            }
        }
    }

    private void showZ(){
        if(ZCheckBox.isSelected() && points != null){
            chartPanel.setChart(CorChart);
            for(Double time : times){
                Point point = points.get(time);
                ZSeries.add(time, point.getZ());
            }
        }
    }

    private void showVx(){
        if(VxCheckBox.isSelected() && points != null){
            chartPanel.setChart(SpeedChart);
            for(Double time : times){
                Point point = points.get(time);
                VxSeries.add(time, point.getVx());
            }
        }
    }

    private void showVy(){
        if(VyCheckBox.isSelected() && points != null){
            chartPanel.setChart(SpeedChart);
            for(Double time : times){
                Point point = points.get(time);
                VySeries.add(time, point.getVy());
            }
        }
    }

    private void showVz(){
        if(VzCheckBox.isSelected() && points != null){
            chartPanel.setChart(SpeedChart);
            for(Double time : times){
                Point point = points.get(time);
                VzSeries.add(time, point.getVz());
            }
        }
    }

    private void addListeners(){
        XCheckBox.addItemListener(e -> {
            if (XCheckBox.isSelected()) {
                clearSpeedSeries();
                showX();
            } else {
                XSeries.clear();
            }

    });

        YCheckBox.addItemListener(e -> {
            if (YCheckBox.isSelected()) {
                clearSpeedSeries();
                showY();
            } else {
                YSeries.clear();
            }

        });

        ZCheckBox.addItemListener(e -> {
            if (ZCheckBox.isSelected()) {
                clearSpeedSeries();
                showZ();
            } else {
                ZSeries.clear();
            }
        });

        VxCheckBox.addItemListener(e -> {
            if (VxCheckBox.isSelected()) {
                clearCorSeries();
                showVx();

            } else {
                VxSeries.clear();
            }
        });

        VyCheckBox.addItemListener(e -> {
            if (VyCheckBox.isSelected()) {
                clearCorSeries();
                showVy();
            } else {
                VySeries.clear();
            }
        });

        VzCheckBox.addItemListener(e -> {
            if (VzCheckBox.isSelected()) {
                clearCorSeries();
                showVz();
            } else {
                VzSeries.clear();
            }
        });
    }

    private void clearCorSeries(){
        XCheckBox.setSelected(false);
        YCheckBox.setSelected(false);
        ZCheckBox.setSelected(false);
        XSeries.clear();
        YSeries.clear();
        ZSeries.clear();
    }

    private void clearSpeedSeries(){
        VxCheckBox.setSelected(false);
        VyCheckBox.setSelected(false);
        VzCheckBox.setSelected(false);
        VxSeries.clear();
        VySeries.clear();
        VzSeries.clear();
    }

}
