package model;

import javax.swing.JTable;

/** 
 * @author Diego Schmaedech
 * @version 1.1
 * @since Release Carnaval
 * @link  https://github.com/schmaedech/expecon
 * Created on August 2, 2008, 2:34 AM
 */
public class Time extends java.util.Observable {
     
    private int interval; 
    private int period;
    private int periodsForward;
    private String timestamp;
    private int timerCounter;
    private String status;
    private JTable timeTable;  
    private boolean isPaused=true;
     
    private int maxPeriod;
    public Time( JTable timeTable ) {
        setTimeTable(timeTable);
        int txtInterval = Integer.parseInt((String) getTimeTable().getModel().getValueAt(0, 0));        
        int txtPeriod = Integer.parseInt((String) getTimeTable().getModel().getValueAt(0, 1));        
        String txtTimestamp =  (String) getTimeTable().getModel().getValueAt(0, 2); 
        int txtMaxPeriod = Integer.parseInt((String) getTimeTable().getModel().getValueAt(0, 3)); 
        
        setInterval( txtInterval );
        setPeriodsForward(txtPeriod);
        setPeriod( 1 );
        setTimestamp( txtTimestamp );
        setMaxPeriod(txtMaxPeriod);
         
    }
     
    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
        setChanged();
        notifyObservers();
    }

    public int getMaxPeriod() {
        return maxPeriod;
    }

    public void setMaxPeriod(int maxPeriod) {
        this.maxPeriod = maxPeriod;
        setChanged();
        notifyObservers();
    }
    
    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
        setChanged();
        notifyObservers();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        setChanged();
        notifyObservers();
    }
 
    public void play( ) throws IllegalArgumentException  {
        if ( period < 0 ){
            throw new IllegalArgumentException( "Game Over" );
        }
        //setTimestamp( "00:00:00");
        //setPeriod( 1 );
        setPaused(false);
    }
    
    public void stop() throws IllegalArgumentException {
        if ( period < 0 ){
            throw new IllegalArgumentException( "Cannot deposit negative amount" );
        }
        
        timerCounter(0);
        setPaused(true);
    }
    
    public void forward( int period, String timestamp ) throws IllegalArgumentException {
        if ( period < 0 ){
            throw new IllegalArgumentException( "gamer over!" );
        }
         
        setTimestamp( timestamp );
        setPeriod( ( period + 1 ) );
    }
    
    public void timerCounter( int counter ) throws IllegalArgumentException {
        if ( counter < 0 ){
            throw new IllegalArgumentException( "gamer over!" );
        }
          
        setTimerCounter(  counter  );
    }

    public int getTimerCounter() {
        return timerCounter;
    }

    public void setTimerCounter(int timerCounter) {
        this.timerCounter = timerCounter;
        setChanged();
        notifyObservers();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        setChanged();
        notifyObservers();
    }

    public JTable getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(JTable timeTable) {
        this.timeTable = timeTable;
        setChanged();
        notifyObservers();
    }

    public boolean isPaused() {
        return isPaused;
    }
  
    private void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
        setChanged();
        notifyObservers();
    }

    public int getPeriodsForward() {
        return periodsForward;
    }

    public void setPeriodsForward(int periodsForward) {        
        this.periodsForward = periodsForward;
        setChanged();
        notifyObservers();
    }
   
    
}
