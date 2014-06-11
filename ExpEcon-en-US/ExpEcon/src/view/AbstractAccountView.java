package view;


import model.Account;
 
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
public abstract class AbstractAccountView extends JPanel implements Observer {
   
    private Account account;
    
    public AbstractAccountView( Account observableAccount ) throws NullPointerException{
        
        if ( observableAccount == null ){
         throw new NullPointerException();
        }
        account = observableAccount;
        account.addObserver( this );
        setBackground( Color.white );
    }
     
    public Account getAccount(){
        return account;
    }
    
    protected abstract void updateDisplay();
    
    public void update( Observable observable, Object object ){
        updateDisplay();
    }
}
