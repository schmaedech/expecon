package view;


import model.Share;
 
import java.util.*;
import java.awt.*;
import javax.swing.JPanel;
 
/** 
 * @author Diego Schmaedech
 * @version 1.1
 * @since Release Carnaval
 * @link  https://github.com/schmaedech/expecon
 * Created on August 2, 2008, 2:34 AM
 */
public abstract class AbstractShareView extends JPanel implements Observer {
   
    private Share share;
    
    public AbstractShareView( Share observableShare ) throws NullPointerException{
        
        if ( observableShare == null ){
         throw new NullPointerException();
        }
        share = observableShare;
        share.addObserver( this );
        setBackground( Color.white );
         
    }
     
    public Share getShare(){
        return share;
    }
    
    protected abstract void updateDisplay();
    
    public void update( Observable observable, Object object ){
        updateDisplay();
    }
}
