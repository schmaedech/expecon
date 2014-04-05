

package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import model.Share;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
  /** 
 * @author Diego Schmaedech
 * @version 1.1
 * @since Release Carnaval
 * @link  https://github.com/schmaedech/expecon
 * Created on August 2, 2008, 2:34 AM
 */
public class StatisticChartView extends AbstractShareView {

     
    private Share share;
         
    private int interval; 
    private XYSeries series1;
    private double incrementSerie1;
    private XYSeries series2;
    private double incrementSerie2;
    private XYSeries series3;
    private double incrementSerie3;
    private XYSeries series4;
    private double incrementSerie4;
    private XYSeries series5;
    private double incrementSerie5;
    private XYSeries series6; 
    private double incrementSerie6;
    private Timer timer;
    private String timestamp;
        
             
    public StatisticChartView( Share controlledShare ) {
        super( controlledShare );

         
        //setAccount(controlledAccount);
        setShare(controlledShare);
        //setTime(controlledTime);
        //setInterval(getShare().getInterval()); 
        final XYSeriesCollection dataset = new XYSeriesCollection();
           
        setSeries1( new XYSeries(getShare().getShareName(0)));
        setSeries2( new XYSeries(getShare().getShareName(1)));
        setSeries3( new XYSeries(getShare().getShareName(2)));
        setSeries4( new XYSeries(getShare().getShareName(3)));
        setSeries5( new XYSeries(getShare().getShareName(4)));
        setSeries6( new XYSeries(getShare().getShareName(5)));
       
          //final TimeSeriesCollection dataset = new TimeSeriesCollection();
         
        dataset.addSeries(getSeries1());
        dataset.addSeries(getSeries2());
        dataset.addSeries(getSeries3());
        dataset.addSeries(getSeries4());
        dataset.addSeries(getSeries5());
        dataset.addSeries(getSeries6());
         
        final JFreeChart chart = createChart(dataset);
        chart.setAntiAlias(true);
        
        final ChartPanel chartPanel = new ChartPanel( chart );
        
       
         
        setLayout( new FlowLayout() );           
        chartPanel.setPreferredSize( new java.awt.Dimension(600, 500) );         
        add(chartPanel);         
        setBackground(Color.white);
         
        startJokerTimer(); 
         
    }

    public void startJokerTimer() {
       
        incrementSerie1 = getShare().getVariation(0); 
        incrementSerie2 = getShare().getVariation(1); 
        incrementSerie3 = getShare().getVariation(2); 
        incrementSerie4 = getShare().getVariation(3); 
        incrementSerie5 = getShare().getVariation(4); 
        incrementSerie6 = getShare().getVariation(5); 
        timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                               
               updatePlotSeries();
               incrementSerie1 = (getShare().getVariation(0)  ); 
               incrementSerie2 = (getShare().getVariation(1)  );
               incrementSerie3 = (getShare().getVariation(2)  );
               incrementSerie4 = (getShare().getVariation(3)  );
               incrementSerie5 = (getShare().getVariation(4)  );
               incrementSerie6 = (getShare().getVariation(5)  );
                        
            }
                
        });
        
         
        timer.start(); 
    }
      
    public void updatePlotSeries( ){
                
        
        incrementSerie1 = getShare().getVariation(0); 
        incrementSerie2 = getShare().getVariation(1); 
        incrementSerie3 = getShare().getVariation(2); 
        incrementSerie4 = getShare().getVariation(3); 
        incrementSerie5 = getShare().getVariation(4); 
        incrementSerie6 = getShare().getVariation(5);
         
        
        getSeries1().add(getShare().getCurrentPeriod(), incrementSerie1 );
        getSeries2().add(getShare().getCurrentPeriod(), incrementSerie2 );
        getSeries3().add(getShare().getCurrentPeriod(), incrementSerie3  );
        getSeries4().add(getShare().getCurrentPeriod(), incrementSerie4 );
        getSeries5().add(getShare().getCurrentPeriod(), incrementSerie5 );
        getSeries6().add(getShare().getCurrentPeriod(), incrementSerie6 );
        
    }
    
      
    private JFreeChart createChart(final XYDataset dataset) {
        JFreeChart result = ChartFactory.createXYLineChart(
            "Variação vs Periodo",
            "Periodo",
            "Variação",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            false,
            false
        );
        final XYPlot plot = result.getXYPlot();
         XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        //renderer.setSeriesLinesVisible(0, true);
        //renderer.setSeriesShapesVisible(0, false);
          
        renderer.setSeriesStroke(0, new BasicStroke(1.0f));
        renderer.setSeriesStroke(1, new BasicStroke(1.0f));
        renderer.setSeriesStroke(2, new BasicStroke(1.0f));
        renderer.setSeriesStroke(3, new BasicStroke(1.0f));
        renderer.setSeriesStroke(4, new BasicStroke(1.0f));
        renderer.setSeriesStroke(5, new BasicStroke(1.0f));
         
        
        plot.setRenderer(renderer);
        ValueAxis axis = plot.getDomainAxis();
        
         axis.setAutoRange(true);
         //axis.setRange(1, getTime().getMaxPeriod());
         axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        //axis.setFixedAutoRange( 10);  // 60 seconds
        axis = plot.getRangeAxis();
        //axis.setFixedAutoRange(10); 
        axis.setAutoRange(true);
        return result;
    }
    
     
   

    public void setShare(Share share) {
        this.share = share;
    }
 
    public Share getShare() {
        return share;
    }
  
    public XYSeries getSeries1() {
        return series1;
    }

    public void setSeries1(XYSeries series1) {
        this.series1 = series1;
    }

    public XYSeries getSeries2() {
        return series2;
    }

    public void setSeries2(XYSeries series2) {
        this.series2 = series2;
    }

    public XYSeries getSeries3() {
        return series3;
    }

    public void setSeries3(XYSeries series3) {
        this.series3 = series3;
    }

    public XYSeries getSeries4() {
        return series4;
    }

    public void setSeries4(XYSeries series4) {
        this.series4 = series4;
    }

    public XYSeries getSeries5() {
        return series5;
    }

    public void setSeries5(XYSeries series5) {
        this.series5 = series5;
    }

    public XYSeries getSeries6() {
        return series6;
    }

    public void setSeries6(XYSeries series6) {
        this.series6 = series6;
    }


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    @Override
    protected void updateDisplay() {
         
    }
}