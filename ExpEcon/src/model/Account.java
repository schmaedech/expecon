package model;
 
import java.util.Vector;
import javax.swing.JTable;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
 
/** 
 * @author Diego Schmaedech
 * @version 1.1
 * @since Release Carnaval
 * @link  https://github.com/schmaedech/expecon
 * Created on August 2, 2008, 2:34 AM
 */ 
public class Account extends java.util.Observable {
      
    private JTable accountTable; 
    private Vector extractHeader = new Vector(1);
    private Vector extractData = new Vector(1);
    private Vector fileData = new Vector(1);
    private Vector vTempRefactory = new Vector(1); 
    private Vector vTotalRefactory = new Vector(1); 
    
    private int dataIndex = 0;    
    private String accountName;
    private String shareName;
    
    private String transaction;
    private String totalTransaction = "";
    
    private int amount;
    private int totalAmount;
    
    private double purchasePrice;
    private double totalPurchasePrice = 0;
    
    private double moneyReturn;
    private double totalMoneyReturn = 0;
    
    private double openingMoney;
    private double totalOpeningMoney=0;
    
    private double shareBalance;
    private double totalShareBalance = 0;
    
    private boolean buyFlag = false;
    private boolean autoSeller = false;
    
    private int currentPeriod; 
    private boolean isPaused = true; 
    private String output = "";
    private String operationTable = "";
    private String investimentTable = "";
    
    private double salePrice;
    private double txtOpeningMoney;
    private String txtAccountName; 
    private double moneyBalance;
   
    private static Logger logger = Logger.getLogger("Account");
    private static List strHolder = new ArrayList();
    
 
    public Account( JTable accountTable ) {
        setAccountTable(accountTable);
        this.txtOpeningMoney = Double.parseDouble((String) getAccountTable().getModel().getValueAt(0, 7));        
        this.txtAccountName =  (String) getAccountTable().getModel().getValueAt(0, 8);
        String strAutoSeller = (String) getAccountTable().getModel().getValueAt(0, 9);
        if(strAutoSeller.equals("true") ){
        this.autoSeller =  true;
        }
        setOpeningMoney(txtOpeningMoney);
        totalOpeningMoney += getOpeningMoney();
        setAccountName(txtAccountName);
        int i = 0;   
        //exclui 3 ultimas colunas 
        while( i < getAccountTable().getModel().getColumnCount()-4){
             setExtractHeader(getAccountTable().getColumnName(i));   
             i++;
        }
        
        loadTableModel();
        vTotalRefactory = (Vector) getExtractData().elementAt(0);
        
        //notas do jocker-----        
        String ans;
        ans = JOptionPane.showInputDialog(null, "<html><br />Digite seu nome:<br /></html>","Vamos Começar!",JOptionPane.INFORMATION_MESSAGE);
        if(ans == null){
           System.exit(0);
        }
        if(ans.equals("") ){
          String ansJocker = JOptionPane.showInputDialog(null, "<html>Você não digitou um nome!<br />" +
                   "Para tal, <i>click</i> sobre o quadradinho branco logo abaixo desta mensagem:<br />" +
                   "<b>desta mensagem</b></html>","Mensagem ;)",JOptionPane.WARNING_MESSAGE);
                   if(ansJocker==null || ansJocker.equals("")){
                      System.exit(0);  
                   }
           
        }
        setAccountName(ans);
        //fim do jocker------- 
        
        //logger
        logger.addHandler(new Handler() {
                @Override
              public void publish(LogRecord logRecord) {
                //strHolder.add(logRecord.getLevel() + ":");
                //strHolder.add(logRecord.getSourceClassName() + ":");
                //strHolder.add(logRecord.getSourceMethodName() + ":");
                strHolder.add("" + logRecord.getMessage() + "");
                strHolder.add("\n");
              }

            @Override
              public void flush() {
              }

            @Override
              public void close() {
              }
        });
        //.................fim do logger
    }
    
    public void loadTableModel(){
               
        String txtShareName =  (String) getAccountTable().getModel().getValueAt(0, 0);        
        String txtTransaction =  (String) getAccountTable().getModel().getValueAt(0, 1);         
        int txtAmount = Integer.parseInt((String) getAccountTable().getModel().getValueAt(0, 2));        
        double txtPurchasePrice = Double.parseDouble((String) getAccountTable().getModel().getValueAt(0, 3));        
        double txtMoneyReturn = Double.parseDouble((String) getAccountTable().getModel().getValueAt(0, 4));        
        double txtShareBalance = Double.parseDouble((String) getAccountTable().getModel().getValueAt(0, 5));           
        int txtPeriod = Integer.parseInt((String) getAccountTable().getModel().getValueAt(0, 6));        
     
        setShareName(txtShareName);
        setTransaction(txtTransaction);
        setAmount(txtAmount);
        setPurchasePrice(txtPurchasePrice);
        setMoneyReturn(txtMoneyReturn);
        setShareBalance(txtShareBalance);
        setCurrentPeriod(txtPeriod);
        createNewStractRow();
        
    }
     
    public void stop() throws IllegalArgumentException {
        if ( getCurrentPeriod() < 0 ){
            throw new IllegalArgumentException( "gamer over!" );
        }     
         setPaused(true);
         logger.info("Parou: "+new Date() );
    }
    
    public void play() { 
        setPaused(false);              
        operationTable += "Dados do "+ getAccountName()+"\n";
        operationTable += "TABELA DE OPERAÇÕES\n";
        operationTable += "PERIODO\tOPERACAO\tATIVO\tQUANTIDADE\tPREÇO ATUAL\n";
        investimentTable += "TABELA DA CARTEIRA DE INVESTIMENTOS\n";
        investimentTable += "TEMPO\tLIVRE PARA OPERAR\n";
        investimentTable += "PERIODO\tATIVO\tCUSTO DO LOTE\tVALOR ATUAL\n";
        investimentTable += "Começou---->\t"+new Date() +"\t"+ getOpeningMoney() +"\n";   
        logger.info("Começou: "+new Date() );
        
        
    }
  
    public void replay() { 
        setPaused(false);    
        logger.info("Re-Começou: "+new Date() );
    }
    
    public void buyShare( Vector vShare ) throws IllegalArgumentException  {
        if ( vShare == null ){
            throw new IllegalArgumentException( "Game Over" );
        } 
        addFileData(vShare);
        this.buyFlag = false;//inicializa a flag
        logger.info( "C"+"      "+ new Date() + "     "+ vShare.toString());
        //verifica se a acao jah existe no extrato  
        Vector vNameCompare = new Vector(1);
        for( int i=0; i < getExtractData().size(); i++ ){
            vNameCompare = (Vector) getExtractData().elementAt(i);        
            if( vNameCompare.elementAt(0).toString().equals(vShare.elementAt(0).toString()) == true ){
                this.buyFlag = true;//avisa que chegou
                this.dataIndex = i;//captura o indice 
            } 
        }    
        
        vTempRefactory = (Vector) getExtractData().elementAt(dataIndex);         
        String tempStractTransaction =  vTempRefactory.elementAt(1).toString();                 
        int tempStractAmount = Integer.parseInt( vTempRefactory.elementAt(2).toString()); 
        double tempStractPrice = Double.parseDouble( vTempRefactory.elementAt(3).toString() );            
        double tempStractReturn = Double.parseDouble( vTempRefactory.elementAt(4).toString() ); 
        double tempStractShareBalance = Double.parseDouble( vTempRefactory.elementAt(5).toString());
        
        String buyShareName = vShare.elementAt(0).toString();
        int tempBuyAmount = Integer.parseInt( vShare.elementAt(1).toString() );
        double tempPurchasePrice = Double.parseDouble( vShare.elementAt(2).toString() );
        double tempPosPurchasePrice = Double.parseDouble( vShare.elementAt(3).toString() );        
        double tempCurrentVariation = Double.parseDouble( vShare.elementAt(4).toString() ); 
        double tempPosVariation = Double.parseDouble( vShare.elementAt(5).toString() );         
        int tempPeriod = Integer.parseInt(vShare.elementAt(6).toString()); 
        
        if( (getOpeningMoney()-tempBuyAmount*tempPurchasePrice) > 0.0 ){
            operationTable += tempPeriod + "\t"+ "C" + "\t" +buyShareName+"\t"+tempBuyAmount+ "\t"+ tempPurchasePrice + "\n";   
            totalOpeningMoney -=tempBuyAmount*tempPurchasePrice;
             //setShareName(shareName);
            //se jah nao foi comprado uma vez entra aqui
            if(buyFlag==false){
                setShareName( buyShareName );
                setTransaction( "C" );
                setAmount( tempBuyAmount );
                setPurchasePrice( tempPurchasePrice*tempBuyAmount );
                
                setMoneyReturn( 0.0 );                
                setShareBalance( tempPurchasePrice*tempBuyAmount  );
                setCurrentPeriod( tempPeriod );
                //setMoneyBalance(getMoneyBalance());
                setOpeningMoney(getOpeningMoney()-tempPurchasePrice*tempBuyAmount);                
                createNewStractRow();               

            } else {   
                   //coluna transaction
                setTransaction( "C" + tempStractTransaction );//1
                setAmount( tempStractAmount + tempBuyAmount );//2          
                setPurchasePrice( (tempPurchasePrice*tempBuyAmount) + tempStractPrice ); //3  
                
                setMoneyReturn( tempStractReturn ); //4
                setShareBalance( tempStractShareBalance + (tempPurchasePrice*tempBuyAmount));//5
                setCurrentPeriod( tempPeriod );//6
                //setOpeningMoney( getOpeningMoney()-tempBuyAmount*tempPurchasePrice );
                setOpeningMoney(totalOpeningMoney);
                //setMoneyBalance( getMoneyBalance()  );
                updateStractRow();
                
           } //end if/else
       }else{
         JOptionPane.showMessageDialog(accountTable, "<html>Você não possui " +
                        "condições financeiras de comprar esse lote! </html>");

       }   

    }
       
    public void sellShare( Vector vShare ) throws IllegalArgumentException {
        if ( vShare == null ){
            throw new IllegalArgumentException( "Cannot withdraw negative amount" );
        }       
        addFileData(vShare);
        this.buyFlag = false;//inicializa a flag
        logger.info("V"+"      "+ new Date() + "     "+ vShare.toString());
        //verifica se a acao jah existe no extrato 
        Vector vNameCompare = new Vector(1);
        String sellShareName = vShare.elementAt(0).toString();
        for( int i=0; i < getExtractData().size(); i++ ){
            vNameCompare = (Vector) getExtractData().elementAt(i); 
              
            if( vNameCompare.elementAt(0).toString().equals(sellShareName) == true ){
                this.buyFlag = true;//avisa que chegou
                this.dataIndex = i;//captura o indice
                
            }
        }        
          
        vTempRefactory = (Vector) getExtractData().elementAt(dataIndex); 
        String tempStractTransaction =  vTempRefactory.elementAt(1).toString();                 
        int tempStractAmount = Integer.parseInt(vTempRefactory.elementAt(2).toString()); 
        double tempStractPrice = Double.parseDouble( vTempRefactory.elementAt(3).toString() );            
        double tempStractReturn = Double.parseDouble( vTempRefactory.elementAt(4).toString() ); 
        double tempStractShareBalance = Double.parseDouble(vTempRefactory.elementAt(5).toString());
        
        int tempSellAmount = Integer.parseInt(vShare.elementAt(1).toString());
        double tempPurchasePrice = Double.parseDouble( vShare.elementAt(2).toString() );
        double tempPosPurchasePrice = Double.parseDouble( vShare.elementAt(3).toString() );        
        double tempCurrentVariation = Double.parseDouble( vShare.elementAt(4).toString() ); 
        double tempPosVariation = Double.parseDouble( vShare.elementAt(5).toString() );         
        int tempPeriod = Integer.parseInt(vShare.elementAt(6).toString()); 
        if(tempStractAmount - tempSellAmount >= 0){
            operationTable += tempPeriod + "\t"+ "V" + "\t" +sellShareName+"\t"+tempSellAmount+ "\t"+ tempPurchasePrice + "\n";
            totalOpeningMoney +=tempSellAmount*tempPurchasePrice;
        
             //setShareName(shareName);
            //se jah foi comprado uma vez entra aqui
            if(buyFlag==false){
                
              //  JOptionPane.showMessageDialog(accountTable, "<html>Você não possui o ativo escolhido!</html>");
            } else {   
                   //coluna transaction
                setTransaction( "V" + tempStractTransaction );//1
                setAmount( tempStractAmount - tempSellAmount );//2          
                setPurchasePrice(  tempStractPrice - tempSellAmount * (tempStractPrice/tempStractAmount) ); //3 
                
                setMoneyReturn( tempStractReturn ); //4
                setShareBalance( tempStractShareBalance - tempPurchasePrice*tempSellAmount );//5
                setCurrentPeriod( tempPeriod );//6
                //setOpeningMoney( tempSellAmount*tempStractShareBalance );
                setOpeningMoney(totalOpeningMoney);
                //setMoneyBalance( getMoneyBalance() );
                updateStractRow();
                 
           } //end if/else
        }else{
            //JOptionPane.showMessageDialog(accountTable, "<html>Você não possui " +
              //              "condições financeiras de vender esse lote! </html>");

        }  

    }

    public void upShare( int period, String strShare, double upVariation, double upPrice) throws IllegalArgumentException  {
      
        if ( period < 0 ){
            throw new IllegalArgumentException( "Game Over" );
        }
         
        this.buyFlag = false;//inicializa a flag
        //int period = Integer.parseInt(vShare.elementAt(6).toString()); 
        setCurrentPeriod(period);    
        //verifica se a acao jah existe no extrato 
        Vector vNameCompare = new Vector(1); 
        for( int i=0; i < getExtractData().size(); i++ ){
            vNameCompare = (Vector) getExtractData().elementAt(i);  
            if( vNameCompare.elementAt(0).toString().equals(strShare) == true ){
                this.buyFlag = true;//avisa que chegou
                this.dataIndex = i;//captura o indice 
                 
            }
        }        
                
        vTempRefactory = (Vector) getExtractData().elementAt(dataIndex);   
        String tempStractTransaction =  vTempRefactory.elementAt(1).toString();      
        int tempStractAmount = Integer.parseInt(vTempRefactory.elementAt(2).toString());   
        double tempStractPrice = Double.parseDouble( vTempRefactory.elementAt(3).toString() );                   
        double tempStractReturn = Double.parseDouble(vTempRefactory.elementAt(4).toString());         
        double tempStractShareBalance = Double.parseDouble(vTempRefactory.elementAt(5).toString());      
                      
        //setShareName(shareName);
        if(buyFlag==true){ 
             
            setTransaction( tempStractTransaction );
            setAmount( tempStractAmount );                                
            setPurchasePrice( tempStractPrice );            
            setShareBalance( tempStractAmount * upPrice );
            setMoneyReturn( ((getShareBalance()- getPurchasePrice())/getPurchasePrice()) *100 );  
            totalShareBalance += getShareBalance();  
            
            totalAmount += getAmount();
            totalTransaction += getTransaction();
            totalPurchasePrice += getPurchasePrice();
            updateStractRow();
            
            investimentTable += (period-1)+"\t"+strShare +"\t"+tempStractPrice +"\t"+ tempStractShareBalance+"\n";
       
            if(autoSeller == true){
                Vector vSellShare = new Vector(1);        
                vSellShare.add( strShare );//0
                vSellShare.add( getAmount() );//1
                vSellShare.add( upPrice );//2
                vSellShare.add( upPrice);//3        
                vSellShare.add( upVariation );//4
                vSellShare.add(  upVariation  );//5
                vSellShare.add( period);//6
                vSellShare.add(  getOpeningMoney() );//7
                //System.out.print(vSellShare.toString());    
                sellShare(vSellShare);
            }
           
         }  
         
    }
    
    public void saveAs(){
       
        PrintWriter pw = null;
        String op="";
        DecimalFormat col1 = new DecimalFormat("#0");

        try {
            pw = new PrintWriter(new FileWriter("resources/outputTable.txt"));
        } catch (IOException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
        //pw.println("Date: "+ new Date());
        //pw.println("<table>" + "<tr>" +
                  // "<th>ativo <th> quantidade <th> pre&ccedil;o do lote <th> p seguinte <th> varia&ccedil;&atilde;o <th> v seguinte <th> saldo <th> periodo </th>");
        
        op += "Data: "+ new Date();
        op += "<table>" +
                "<tr>" +
                "<th>ativo <th> quantidade <th> pre&ccedil;o do lote <th> pre&ccedil;o seguinte <th> varia&ccedil;&atilde;o <th> varia&ccedil;&atilde;o seguinte <th> saldo <th> periodo </th>";
        op += "\n";
        for (int f = 0; f < getFileData().size(); f++) {
            String line = getFileData().get(f).toString();
            String linef1 = line.replace("[", "");
            String linef2 = linef1.replace("]", "");
            String strLine = linef2.replace(",", "<td>");
            //pw.println("<tr><td>" +  strLine );
            op += "<tr><td>" +  strLine + "\n";
        }

        //pw.println("</tr>"+"</table>");
        pw.print(operationTable);
        pw.println("#-----------------------------------------------------------");
        pw.print(investimentTable);
        op += "</tr>"+"</table>" + "\n"; 
         
        pw.close();  
        setOutput(strHolder.toString()); 
        JOptionPane.showMessageDialog(accountTable, getAccountName() + ", acabaram os periodos para esta simulação! ");
        System.exit(0);
        /*
        JFileChooser fc = new JFileChooser();

        String command = "Save a File...";
        int retVal;
        boolean exists;

        try{
            File f = new File(new File("output.html").getCanonicalPath());

            fc.setSelectedFile(f);
        }catch (IOException ioE){
            JOptionPane.showMessageDialog(null, ioE.getMessage());
        }
        if (command.equals("Save a File...")){ 

            retVal = fc.showSaveDialog(null);    

            if (retVal == JFileChooser.APPROVE_OPTION) {            
                File thefile = fc.getSelectedFile(); 
                String nameOfFile = "";
                nameOfFile = thefile.getPath();          
                exists = (new File(nameOfFile)).exists();
                if(!exists){
                    try {
                        BufferedWriter out = new BufferedWriter(new FileWriter(nameOfFile));
                        out.write(getOutput());
                        out.close();
                    } catch (IOException ioE) {
                    }		  			
                }else{
                    try {
                        BufferedWriter out = new BufferedWriter(new FileWriter(nameOfFile, true));
                        out.write(operationTable);
                        out.close();
                        } catch (IOException ioE) {
                        }
                }

            if (thefile != null) {
                if (thefile.isDirectory()) {
                    JOptionPane.showMessageDialog(null,"Diretório: " + thefile.getPath());
                } else {
                    JOptionPane.showMessageDialog(null,"Arquivo: " + thefile.getPath());

                    //out = new FileOutputStream(theFile, true);
                }
            }        
            }else if (retVal == JFileChooser.CANCEL_OPTION) {
                JOptionPane.showMessageDialog(null, "User cancelled operation. No file was chosen.");
            }else if (retVal == JFileChooser.ERROR_OPTION) {
                JOptionPane.showMessageDialog(null, "An error occured. No file was chosen.");
            }else {
                JOptionPane.showMessageDialog(null, "Unknown operation occured.");
            }
            }
         */
    }
    
    public void forward( int period, JTable shareData ) throws IllegalArgumentException {
        if ( period < 0 ){
            throw new IllegalArgumentException( "gamer over!" );
        }
        setCurrentPeriod(period);
        investimentTable += "Periodo --> "+ (period-1) +"\t"+new Date() +"\t"+ getOpeningMoney() +"\n";   
         
        Vector vUserDataFile = new Vector(1);
        vUserDataFile.add(1);
        vUserDataFile.add(1);
        vUserDataFile.add(1);
        vUserDataFile.add(1);
        vUserDataFile.add(1);
        addFileData(vUserDataFile);
        
        int rowCount =  shareData.getRowCount();
        String lastPeriod = shareData.getModel().getValueAt(rowCount-1, 4).toString();
        int intLP = Integer.parseInt(lastPeriod);
        int countPeriods = shareData.getRowCount()/intLP;
        totalShareBalance = 0; 
        for( int i=0; i < countPeriods; i++ ){
            String strShareName = shareData.getModel().getValueAt(i, 0).toString();
            int localPointer = (getCurrentPeriod()-1)*countPeriods;  
            int lp =localPointer+i;
            double upVariation = Double.parseDouble((String) shareData.getModel().getValueAt(lp, 3));
            double upPrice = Double.parseDouble((String) shareData.getModel().getValueAt(lp, 1));
            upShare(period, strShareName, upVariation, upPrice);
            //----------------------------------------------edit code
           
            
            //----------------------------------------------
        }//end loop for
        if( totalShareBalance != 0 ){
            
            if(autoSeller==false){
                setMoneyBalance( totalShareBalance + getOpeningMoney()); 
                 
            }else{
                setMoneyBalance(  getOpeningMoney()); 
            }
            
        }else{
            setMoneyBalance( getMoneyBalance()); 
        }
        totalMoneyReturn = ((totalShareBalance - totalPurchasePrice)/totalPurchasePrice) *100;
        updateTotalStractRow();
         
        totalMoneyReturn = 0;
        totalAmount = 0;
        totalTransaction = "";
        totalPurchasePrice = 0;
        
        logger.info("Passou periodo: "+new Date() );
    }
 
    public void createNewStractRow(){
        Vector newRow = new Vector();  
        //newRow.add(getAccountName());  
        newRow.add(getShareName());  
        newRow.add(getTransaction());  
        newRow.add(getAmount());
        newRow.add(getPurchasePrice()); 
        newRow.add(getMoneyReturn());
        newRow.add(getShareBalance());  
        newRow.add(getCurrentPeriod());  
        
        addExtractData(newRow);
    }
    
    public void updateStractRow(){
        vTempRefactory.removeElementAt(1); 
        vTempRefactory.add(1, getTransaction()); 
        vTempRefactory.removeElementAt(2);                   
        vTempRefactory.add(2, getAmount());
        vTempRefactory.removeElementAt(3);                
        vTempRefactory.add(3, getPurchasePrice());
        vTempRefactory.removeElementAt(4);            
        vTempRefactory.add(4, getMoneyReturn()); 
        vTempRefactory.removeElementAt(5);     
        vTempRefactory.add(5, getShareBalance()); 
        //vRefactory.removeElementAt(6);                
        //vRefactory.add(6, getCurrentPeriod());
    }
    
    public void updateTotalStractRow(){
        
        vTotalRefactory = (Vector) getExtractData().elementAt(0); 
        vTotalRefactory.removeElementAt(1); 
        vTotalRefactory.add(1, totalTransaction); 
        vTotalRefactory.removeElementAt(2);                   
        vTotalRefactory.add(2, totalAmount);
        vTotalRefactory.removeElementAt(3);                
        vTotalRefactory.add(3, totalPurchasePrice);
        vTotalRefactory.removeElementAt(4);            
        vTotalRefactory.add(4, totalMoneyReturn); 
        vTotalRefactory.removeElementAt(5);     
        vTotalRefactory.add(5, totalShareBalance); 
        //vRefactory.removeElementAt(6);                
        //vRefactory.add(6, getCurrentPeriod());
    }
    
    private void setMoneyBalance( double moneyBalance ) {
        this.moneyBalance = moneyBalance;
        setChanged();
        notifyObservers();
    }
  
    public double getMoneyBalance() {
        return moneyBalance;
    }
   
    public void setAccountName(String accountName) {
        this.accountName = accountName;
        setChanged();
        notifyObservers();
    }
    
    public String getAccountName() {
        return accountName;
    }

    public void setOutput(String output) {
        this.output = output;
        setChanged();
        notifyObservers();
    }
    
    public String getOutput() {
        return output;
    }
 
    public JTable getAccountTable() {
        return accountTable;
    }

    public void setAccountTable(JTable accountTable) {
        this.accountTable = accountTable;
        setChanged();
        notifyObservers();
    }

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
        setChanged();
        notifyObservers();
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
        setChanged();
        notifyObservers();
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        setChanged();
        notifyObservers();
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
        setChanged();
        notifyObservers();
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
        setChanged();
        notifyObservers();
    }

    public double getMoneyReturn() {
        return moneyReturn;
    }

    public void setMoneyReturn(double moneyReturn) {
        this.moneyReturn = moneyReturn;
        setChanged();
        notifyObservers();
    }
   
    public Vector getExtractHeader() {
        return extractHeader;
    }

    public void setExtractHeader(String header) {
        this.extractHeader.add(header);
        setChanged();
        notifyObservers();
    }

    public Vector getExtractData() {
        return extractData;
    }
 
    public void addExtractData( Vector data ) {
        this.extractData.add( data );
        setChanged();
        notifyObservers();
    }
    
    public void addFileData( Vector linedata ) {
        this.fileData.add( linedata );
        setChanged();
        notifyObservers();
    }
    
    public Vector getFileData() {
        return fileData;
    }
    public void remExtractDataAt(int index) {
        this.extractData.removeElementAt(index);
        setChanged();
        notifyObservers();
    }
    
    public void removeExtractData() {
        this.extractData.removeAllElements();
        setChanged();
        notifyObservers();
    }
   
    public int getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(int currentPeriod) {
        this.currentPeriod = currentPeriod;
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

    public double getShareBalance() {
        return shareBalance;
    }

    public void setShareBalance(double shareBalance) {        
        this.shareBalance = shareBalance;
        setChanged();
        notifyObservers();
    }

    public double getOpeningMoney() {
        return openingMoney;
    }

    private void setOpeningMoney(double openingMoney) {
        this.openingMoney = openingMoney;
        setChanged();
        notifyObservers();
    }
    

}
