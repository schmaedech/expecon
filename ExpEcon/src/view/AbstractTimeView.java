package view;


import model.Time;
 
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
public abstract class AbstractTimeView extends JPanel implements Observer {
   
    private Time time;
    
    public AbstractTimeView( Time observableTime ) throws NullPointerException{
        
        if ( observableTime == null ){
         throw new NullPointerException();
        }
        time = observableTime;
        time.addObserver( this );
        setBackground( Color.white );
         
    }
     
    public Time getTime(){
        return time;
    }
    
    protected abstract void updateDisplay();
    
    public void update( Observable observable, Object object ){
        updateDisplay();
    }
}
