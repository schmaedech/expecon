package model;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Vector;
import javax.swing.JTable;

/** 
 * @author Diego Schmaedech
 * @version 1.1
 * @since Release Carnaval
 * @link  https://github.com/schmaedech/expecon
 * Created on August 2, 2008, 2:34 AM
 */
public class Share extends java.util.Observable {
      
    private Object[][] shares;
    private String[] params;
    private Object[][] avaliableShares;    
    private double purchasePrice;
    private double variation;
    private String shareName;
    private int variationColumm;
    private int purchaseColumn;
    private int shareNameColumn;
    private int currentPeriod;
    private int rowLength;
    private int columnLength;
    private double posVariation; 
    private double posPurchasePrice;
    private JTable shareTable;  
    private int periodColumn;
    private int maxPeriod;    
    private double salePrice;
    private double posSalePrice;
    private boolean isPaused=true;
    private int interval;
    private NumberFormat moneyFormat = NumberFormat.getCurrencyInstance( Locale.US );
    
    private Vector historyHeader = new Vector(1);
    private Vector historyData = new Vector(1);
    
    public Share( JTable shareTable ) {
         
        
        setCurrentPeriod(1); 
        this.purchaseColumn=1;
        this.variationColumm=3;
        this.periodColumn=4;
        this.shareNameColumn=0;        
        setShareTable(shareTable);
        this.rowLength = getShareTable().getModel().getRowCount();
        this.columnLength = getShareTable().getModel().getColumnCount();
        int mp = Integer.parseInt( getShareTable().getModel().getValueAt(rowLength-1, periodColumn).toString() );
        setMaxPeriod(mp); 
         int i = 0;   
        //exclui 3 ultimas colunas 
        setHistoryHeader( "Period" );
        while( i < 6){
             setHistoryHeader( getShareTable().getModel().getValueAt(i, 0).toString() );   
             i++;
        }
        createNewHeader();
         
    }
   
  public void createNewHeader(){
        Vector newRow = new Vector();            
        String tableSeparator = new String();
        tableSeparator = "<font COLOR=#08000> | </font>";
        //newRow.add("<html>"+ "<b>Varia&ccedil;&atilde;o<b>" +tableSeparator+ " Pre&ccedil;o" + "</html>");         
        newRow.add("<html><font COLOR=#08000><b>"+ " " + "<b></font></html>");  
        
        newRow.add("<html>"+ "<b>Var<b>" +tableSeparator+ " Price" + "</html>");  
        newRow.add("<html>"+ "<b>Var<b>" +tableSeparator+ " Price" + "</html>");  
        newRow.add("<html>"+ "<b>Var<b>" +tableSeparator+ " Price" + "</html>");
        newRow.add("<html>"+ "<b>Var<b>" +tableSeparator+ " Price" + "</html>"); 
        newRow.add("<html>"+ "<b>Var<b>" +tableSeparator+ " Price" + "</html>");
        newRow.add("<html>"+ "<b>Var<b>" +tableSeparator+ " Price" + "</html>");  
        
        addHistoryData(newRow);
    }
  public void createNewHistoryRow(){
        Vector newRow = new Vector();  
        //newRow.add(getAccountName());  
        String tableSeparator = new String();
        tableSeparator = "<font COLOR=#08000><b> | <b></font>";
        newRow.add("<html><font COLOR=#08000><b>"+"   "+ getCurrentPeriod() + "<b></font></html>");  
        
        newRow.add("<html>"+"<b>"+getVariation(0)+"<b>"+tableSeparator+moneyFormat.format(getPurchasePrice(0))+ "</html>");  
        newRow.add("<html>"+"<b>"+getVariation(1)+"<b>"+tableSeparator+moneyFormat.format(getPurchasePrice(1))+ "</html>");  
        newRow.add("<html>"+"<b>"+getVariation(2)+"<b>"+tableSeparator+moneyFormat.format(getPurchasePrice(2))+ "</html>");
        newRow.add("<html>"+"<b>"+getVariation(3)+"<b>"+tableSeparator+moneyFormat.format(getPurchasePrice(3))+ "</html>"); 
        newRow.add("<html>"+"<b>"+getVariation(4)+"<b>"+tableSeparator+moneyFormat.format(getPurchasePrice(4))+ "</html>");
        newRow.add("<html>"+"<b>"+getVariation(5)+"<b>"+tableSeparator+moneyFormat.format(getPurchasePrice(5))+ "</html>");  
         
        addHistoryData(newRow);
    }
    public void play() {
         setPaused(false);
          
    }
    
    public void forward( int period ) throws IllegalArgumentException {
        if ( period < 0 ){
            throw new IllegalArgumentException( "gamer over!" );
        }
        createNewHistoryRow();
        setCurrentPeriod(period);
        
    }

    public void stop() throws IllegalArgumentException {
        if ( getCurrentPeriod() < 0 ){
            throw new IllegalArgumentException( "gamer over!" );
        } 
        setPaused(true);
 
    }
    
 
    //purchase price column 1
    private void setPurchasePrice(double purchasePrice){
        this.purchasePrice = purchasePrice;
        setChanged();
        notifyObservers();
    }
   
    public void setPosPurchasePrice(double posPurchasePrice) {
        this.posPurchasePrice = posPurchasePrice;
        setChanged();
        notifyObservers();
    }

    public double getPurchasePrice(int idShare){
                
        int countPeriods = 0;          
        for( int i = 0; i < rowLength; i++ ){          
            if( Integer.parseInt( getShareTable().getModel().getValueAt(i, periodColumn).toString() ) == getCurrentPeriod() ){
                countPeriods++; 
            }
        }       
        int localPointer = (getCurrentPeriod()-1)*countPeriods;  
        int lp =localPointer+idShare;
        double price = Double.parseDouble((String) getShareTable().getModel().getValueAt(lp, purchaseColumn));        
        setPurchasePrice(price);
        return purchasePrice;
    } 
    
    public double getPosPurchasePrice(int idShare) {
              
        int countPeriods = 0;          
        for( int i = 0; i < rowLength; i++ ){          
            if( Integer.parseInt( getShareTable().getModel().getValueAt(i, periodColumn).toString() ) == getCurrentPeriod() ){
                countPeriods++; 
            }
        }       
        int localPointer = (getCurrentPeriod())*countPeriods;  
        int lp =localPointer+idShare;
        double price = Double.parseDouble((String) getShareTable().getModel().getValueAt(lp, purchaseColumn));        
        setPosPurchasePrice(price);
        return posPurchasePrice;
    }

    //purchase price column 1
    private void setSalePrice(double salePrice){
        this.salePrice = salePrice;
        setChanged();
        notifyObservers();
    }
   
    public void setPosSalePrice(double posSalePrice) {
        this.posSalePrice = posSalePrice;
        setChanged();
        notifyObservers();
    }

    public double getSalePrice(int idShare){
                
        int countPeriods = 0;          
        for( int i = 0; i < rowLength; i++ ){          
            if( Integer.parseInt( getShareTable().getModel().getValueAt(i, periodColumn).toString() ) == getCurrentPeriod() ){
                countPeriods++; 
            }
        }       
        int localPointer = (getCurrentPeriod()-1)*countPeriods;  
        int lp =localPointer+idShare;
        double price = Double.parseDouble((String) getShareTable().getModel().getValueAt(lp, purchaseColumn));        
        setSalePrice(price);
        return salePrice;
    } 
    
    public double getPosSalePrice(int idShare) {
              
        int countPeriods = 0;          
        for( int i = 0; i < rowLength; i++ ){          
            if( Integer.parseInt( getShareTable().getModel().getValueAt(i, periodColumn).toString() ) == getCurrentPeriod() ){
                countPeriods++; 
            }
        }       
        int localPointer = (getCurrentPeriod())*countPeriods;  
        int lp =localPointer+idShare;
        double price = Double.parseDouble((String) getShareTable().getModel().getValueAt(lp, purchaseColumn));        
        setPosSalePrice(price);
        return posSalePrice;
    }

    //variatione column 3
    public void setVariation(double variation) {
        this.variation = variation;
        setChanged();
        notifyObservers();
    }
 
    public void setPosVariation(double posVariation) {
        this.posVariation = posVariation;
        setChanged();
        notifyObservers();
    }

    public double getVariation(int idShare) {
               
        int countPeriods = 0;          
        for( int i = 0; i < rowLength; i++ ){          
            if( Integer.parseInt( getShareTable().getModel().getValueAt(i, periodColumn).toString() ) == getCurrentPeriod() ){
                countPeriods++; 
            }
        }       
        int localPointer = (getCurrentPeriod()-1)*countPeriods;  
        int lp =localPointer+idShare;
        double variacione = Double.parseDouble((String) getShareTable().getModel().getValueAt(lp, variationColumm));
        setVariation(variacione);       
        return variation;
    }
    
    public double getPosVariation(int idShare) {
        int countPeriods = 0;          
        for( int i = 0; i < rowLength; i++ ){          
            if( Integer.parseInt( getShareTable().getModel().getValueAt(i, periodColumn).toString() ) == getCurrentPeriod() ){
                countPeriods++; 
            }
        }       
        int posLocalPointer = (getCurrentPeriod())*countPeriods;  
        int lp =posLocalPointer+idShare;
        double variacion = Double.parseDouble((String) getShareTable().getModel().getValueAt(lp, variationColumm));
        setPosVariation(variacion);       
        return posVariation; 
    }
    
    public String getShareName(int idShare) {
         
        int countPeriods = 0;          
        for( int i = 0; i < rowLength; i++ ){          
            if( Integer.parseInt( getShareTable().getModel().getValueAt(i, periodColumn).toString() ) == getCurrentPeriod() ){
                countPeriods++; 
            }
        }       
        int pointer = (getCurrentPeriod()-1)*countPeriods;
        int pointerLocation =pointer+idShare;
        String name =  (String) getShareTable().getModel().getValueAt(pointerLocation, shareNameColumn);
        setShareName(name);       
        return name;
    }
    
    public void setParams(String[] params) {
        this.params = params;
        setChanged();
        notifyObservers();
    }
    
    public void setShares(Object[][] shares) {
        this.shares = shares;
        setChanged();
        notifyObservers();
    }
    
    public void setAvaliableShares(Object[][] avaliableShares) {
        this.avaliableShares = avaliableShares;
        setChanged();
        notifyObservers();
    }
   
    public Object[][] getAvaliableShares() {
        return avaliableShares;
    }
  
    public Object[][] getShares() {
        return shares;
    }

    public String[] getParams() {
        return params;
    }

    public int getCurrentPeriod() {
        return currentPeriod;
        
    }

    public void setCurrentPeriod(int currentPeriod) {
        this.currentPeriod = currentPeriod;
        setChanged();
        notifyObservers();
    }
   
    public JTable getShareTable() {
        return shareTable;
    }

    public void setShareTable(JTable shareTable) {
        this.shareTable = shareTable;
        setChanged();
        notifyObservers();
    }

    private void setShareName(String shareName) {
        this.shareName=shareName;
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

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
        setChanged();
        notifyObservers();
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
        setChanged();
        notifyObservers();
    }
    
    public void setHistoryHeader(String header) {
        this.historyHeader.add(header);
        setChanged();
        notifyObservers();
    }

    public Vector getHistoryData() {
        return historyData;
    }
 
    public void addHistoryData( Vector data ) {
        this.historyData.add( data );
        setChanged();
        notifyObservers();
    }
    public Vector getHistoryHeader() {
        return historyHeader;
    }
}
