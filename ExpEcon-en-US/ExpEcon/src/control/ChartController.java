/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ChartController.java
 *
 * Created on Feb 10, 2012, 12:03:00 AM
 */
package control;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import view.AbstractShareView;
import model.Account;
import model.Share;
import model.Time;
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
public class ChartController extends AbstractShareView {

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
    private String timestamp;
    private int interval;
    private Timer timerControl;
    private int countForward = 0;

    public javax.swing.JPanel jpControlButtons;
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
    public javax.swing.JPanel jpAux;

    private Timer timerClock;
    private int chronometer;
    private Timer timerForward;
    private int timerController = 0;
    private boolean simPaused = false;
    private Account account;
    private Share share;
    private Time time;

    public ChartController(Share controlledShare, Account controlledAccount, Time controlledTime) {
        super(controlledShare);

        setAccount(controlledAccount);
        setShare(controlledShare);
        setTime(controlledTime);
        initComponents();
        setInterval(getShare().getInterval());
        this.countForward = 0;

        final XYSeriesCollection dataset = new XYSeriesCollection();

        setSeries1(new XYSeries(getShare().getShareName(0)));
        setSeries2(new XYSeries(getShare().getShareName(1)));
        setSeries3(new XYSeries(getShare().getShareName(2)));
        setSeries4(new XYSeries(getShare().getShareName(3)));
        setSeries5(new XYSeries(getShare().getShareName(4)));
        setSeries6(new XYSeries(getShare().getShareName(5)));
        dataset.addSeries(getSeries1());
        dataset.addSeries(getSeries2());
        dataset.addSeries(getSeries3());
        dataset.addSeries(getSeries4());
        dataset.addSeries(getSeries5());
        dataset.addSeries(getSeries6());

        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
          //chartPanel.add(jpControlButtons);

        //chartPanel.setLayout( new FlowLayout() );
        chartPanel.setPreferredSize(new Dimension(600, 320));
        //chartPanel.setBackground(new  Color(0, 254, 254));
        // jpAux.setLayout( new FlowLayout() );
        // jpAux.setPreferredSize( new Dimension(565, 465) );
        jpChart.setLayout(new GridLayout(0, 1));
        jpChart.add(chartPanel);

    }

    private JFreeChart createChart(final XYDataset dataset) {
        JFreeChart result = ChartFactory.createXYLineChart(
                "Period X Price",
                "Period",
                "Price",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );

        final XYPlot plot = result.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));
        renderer.setSeriesStroke(3, new BasicStroke(2.0f));
        renderer.setSeriesStroke(4, new BasicStroke(2.0f));
        renderer.setSeriesStroke(5, new BasicStroke(2.0f));
        plot.setRenderer(renderer);
        ValueAxis axis = plot.getDomainAxis();
        axis.setRange(1, getShare().getMaxPeriod());
        axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        axis = plot.getRangeAxis();
        axis.setAutoRange(true);
        return result;
    }

    public void play() {
        jbForward.setEnabled(true);
        jbPause.setEnabled(true);

        if (simPaused == false) {
            jbPlay.setEnabled(false);
            getTime().play();
            getAccount().play();
            getShare().play();
            startClock();
            updatePlotSeries();

            if (getTime().getPeriodsForward() != 0) {
                timerController = getTime().getPeriodsForward();
                timerForward = new Timer(0, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        timerController--;
                        forward();
                        if (timerController == 0) {
                            timerForward.stop();
                        }
                    }

                });
                timerForward.start();
            }
        } else {
            getTime().play();
            getAccount().replay();
            getShare().play();
            jbPlay.setText("<html>Começar</html>");
            timerClock.start();
            updatePlotSeries();
            jbPlay.setEnabled(false);
        }

    }

    public void forward() {

        this.countForward++;
        if (countForward == getTime().getMaxPeriod()) {
            pauseClock();

            jbPlay.setEnabled(false);
            jbPause.setEnabled(false);
            jbForward.setEnabled(false);

            getTime().stop();
            getShare().stop();
            getAccount().stop();
             //notas do jocker-----        
            //String ans;
            //ans = JOptionPane.showInputDialog(null, "<html>Acabaram os periodos para esta simulação.<br />" +
            // " Agradeçemos sua colaboração em nossos estudos.<br />" +
            // "Para salvar os dados da sua participação digite <i>sim</i> no campo abaixo e acione OK</html>","Mensagem!",JOptionPane.INFORMATION_MESSAGE);
            //if(ans == null || ans.equals("")){
            // System.exit(0);
            //}
            //if(ans.equals("sim")){
            getAccount().saveAs();
            //}

        //fim do jocker------- 
        } else {

            getTime().forward(getTime().getPeriod(), getTime().getTimestamp());
            getShare().forward(getTime().getPeriod());
            getAccount().forward(getTime().getPeriod(), getShare().getShareTable());

            updatePlotSeries();
            restartClock();
        }
    }

    public void pause() {
        jbPlay.setEnabled(true);
        jbPlay.setText("<html>Return</html>");
        jbPause.setEnabled(false);
        jbForward.setEnabled(false);

        getTime().stop();
        getShare().stop();
        getAccount().stop();

        pauseClock();

        JOptionPane.showMessageDialog(null, "<html>To continue the trading simulation press the button <i>Return</i></html>");

    }

    private void restartClock() {
        chronometer = 0;
        timerClock.start();
    }

    private void pauseClock() {
        //chronometer = 0;
        simPaused = true;
        getTime().timerCounter(chronometer);
        timerClock.stop();

    }

    private void startClock() {

        timerClock = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (chronometer == getTime().getInterval()) {

                    forward();
                    restartClock();

                }

                chronometer++;
                getTime().setTimestamp(getStringClock());
                getTime().timerCounter(chronometer);

            }
        });

        chronometer = 0;
        timerClock.start();

    }

    //Metodos auxiliares, nao modifique
    private String getStringClock() {

        calendar.setTimeInMillis(System.currentTimeMillis());
        String strClock = dateFormat.format(calendar.getTime());
        return strClock;
    }

    public void startJokerTimer() {

        incrementSerie1 = getShare().getVariation(0);
        incrementSerie2 = getShare().getVariation(1);
        incrementSerie3 = getShare().getVariation(2);
        incrementSerie4 = getShare().getVariation(3);
        incrementSerie5 = getShare().getVariation(4);
        incrementSerie6 = getShare().getVariation(5);
        timerControl = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                updatePlotSeries();
                incrementSerie1 = (getShare().getVariation(0));
                incrementSerie2 = (getShare().getVariation(1));
                incrementSerie3 = (getShare().getVariation(2));
                incrementSerie4 = (getShare().getVariation(3));
                incrementSerie5 = (getShare().getVariation(4));
                incrementSerie6 = (getShare().getVariation(5));

            }

        });

        timerControl.start();
        timerControl.stop();
    }

    private void updatePlotSeries() {

        incrementSerie1 = getShare().getPurchasePrice(0);
        incrementSerie2 = getShare().getPurchasePrice(1);
        incrementSerie3 = getShare().getPurchasePrice(2);
        incrementSerie4 = getShare().getPurchasePrice(3);
        incrementSerie5 = getShare().getPurchasePrice(4);
        incrementSerie6 = getShare().getPurchasePrice(5);

        getSeries1().add(getShare().getCurrentPeriod(), incrementSerie1);
        getSeries2().add(getShare().getCurrentPeriod(), incrementSerie2);
        getSeries3().add(getShare().getCurrentPeriod(), incrementSerie3);
        getSeries4().add(getShare().getCurrentPeriod(), incrementSerie4);
        getSeries5().add(getShare().getCurrentPeriod(), incrementSerie5);
        getSeries6().add(getShare().getCurrentPeriod(), incrementSerie6);

    }

    @Override
    protected void updateDisplay() {

    }

    //do not modify this
    public void setTime(Time time) {
        this.time = time;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setShare(Share share) {
        this.share = share;
    }

    @Override
    public Share getShare() {
        return share;
    }

    public Account getAccount() {
        return account;
    }

    public Time getTime() {
        return time;
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpChart = new javax.swing.JPanel();
        jpButtons = new javax.swing.JPanel();
        jbPlay = new javax.swing.JButton();
        jbForward = new javax.swing.JButton();
        jbPause = new javax.swing.JButton();

        jpChart.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout jpChartLayout = new javax.swing.GroupLayout(jpChart);
        jpChart.setLayout(jpChartLayout);
        jpChartLayout.setHorizontalGroup(
            jpChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 532, Short.MAX_VALUE)
        );
        jpChartLayout.setVerticalGroup(
            jpChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 214, Short.MAX_VALUE)
        );

        jpButtons.setBackground(new java.awt.Color(255, 255, 255));
        jpButtons.setLayout(new java.awt.GridLayout(1, 3));

        jbPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/control/resources/tango/stock_media-play.png"))); // NOI18N
        jbPlay.setText(org.openide.util.NbBundle.getMessage(ChartController.class, "ChartController.jbPlay.text")); // NOI18N
        jbPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPlayActionPerformed(evt);
            }
        });
        jpButtons.add(jbPlay);

        jbForward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/control/resources/tango/stock_media-next.png"))); // NOI18N
        jbForward.setText(org.openide.util.NbBundle.getMessage(ChartController.class, "ChartController.jbForward.text")); // NOI18N
        jbForward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbForwardActionPerformed(evt);
            }
        });
        jpButtons.add(jbForward);

        jbPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/control/resources/tango/stock_media-pause.png"))); // NOI18N
        jbPause.setText(org.openide.util.NbBundle.getMessage(ChartController.class, "ChartController.jbPause.text")); // NOI18N
        jbPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPauseActionPerformed(evt);
            }
        });
        jpButtons.add(jbPause);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpButtons, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPlayActionPerformed
        play();
    }//GEN-LAST:event_jbPlayActionPerformed

    private void jbForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbForwardActionPerformed
       forward();
    }//GEN-LAST:event_jbForwardActionPerformed

    private void jbPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPauseActionPerformed
       pause();
    }//GEN-LAST:event_jbPauseActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbForward;
    private javax.swing.JButton jbPause;
    private javax.swing.JButton jbPlay;
    private javax.swing.JPanel jpButtons;
    private javax.swing.JPanel jpChart;
    // End of variables declaration//GEN-END:variables
}
