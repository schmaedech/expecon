package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

 
/** 
 * @author Diego Schmaedech
 * @version 1.1
 * @since Release Carnaval
 * @link  https://github.com/schmaedech/expecon
 * Created on August 2, 2008, 2:34 AM
 */
public class StockData extends AbstractTableModel  {
  protected Vector data;
  protected Vector columnNames ;  
  protected String datafile;
  private static final Logger logger = Logger.getLogger(StockData.class.getName());
    
  public StockData(String f){
    datafile = f;
    initVectors();  
    }

     
  public void initVectors() {
    String aLine ;
    data = new Vector();    
    columnNames = new Vector();
    
    try {
      FileInputStream fin =  new FileInputStream(datafile);
      BufferedReader br = new BufferedReader(new InputStreamReader(fin));
      // extract column names
      StringTokenizer st1 =  new StringTokenizer(br.readLine(), "|");
      while(st1.hasMoreTokens()) {
                columnNames.addElement(st1.nextToken());
            }
      // extract data
      while ((aLine = br.readLine()) != null) {  
        StringTokenizer st2 =  new StringTokenizer(aLine, "|");
            while(st2.hasMoreTokens()) {
                    data.addElement(st2.nextToken());
                }
        }
        br.close();  
         
    }
    catch (Exception e) {
        logger.log(Level.SEVERE, null, e); 
        e.printStackTrace();
    }
  }

    @Override
  public int getRowCount() {
    return data.size() / getColumnCount();
    }

    @Override
  public int getColumnCount(){
    return columnNames.size();
    }

    @Override
  public String getColumnName(int columnIndex) {
    String colName = "";

    if (columnIndex <= getColumnCount())
       colName = (String)columnNames.elementAt(columnIndex);

    return colName;
    }
    
    @Override
  public Class getColumnClass(int columnIndex){
    return String.class;
    }
    
    @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;
    }
    
    @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    return (String)data.elementAt
        ( (rowIndex * getColumnCount()) + columnIndex);
    }
    
    @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    return;
    }
 
            
}