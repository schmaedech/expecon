 
package control;
import model.Share;
import model.Time;
import model.Account; 
import java.text.NumberFormat; 
import java.util.Locale;
import java.util.Vector;
import javax.swing.JOptionPane;
import view.AbstractAccountView;
/** 
 * @author Diego Schmaedech
 * @version 1.1
 * @since Release Carnaval
 * @link  https://github.com/schmaedech/expecon
 * Created on August 2, 2008, 2:34 AM
 */
public class AccountController extends AbstractAccountView {
 
    private Account account;
    private Share share;
    private Time time;
     
    private int idSelectedShare;    
    private Vector comboShares = new Vector(6);
    private NumberFormat moneyFormat = NumberFormat.getCurrencyInstance( Locale.US );
    
    /** Creates new form AccountController */
    public AccountController( Account controlledAccount, Share controlledShare, Time controlledTime ) {
        super(controlledAccount);
        setAccount(controlledAccount);
        setShare(controlledShare);
        setTime(controlledTime);
        getShare().setInterval(getTime().getInterval());
        initComponents();
        loadAvaliableShares();
        updateDisplay();
         
        
    }
    
    private void loadAvaliableShares(){
        for( int i = 0; i < 6; i++ ){
            setComboShares( i, getShare().getShareName(i).toString() );
        }
        jcbShares.setModel(new javax.swing.DefaultComboBoxModel( getComboShares()));
            
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jlShare = new javax.swing.JLabel();
        jlAmount = new javax.swing.JLabel();
        jtfAmountOfShare = new javax.swing.JTextField();
        jcbShares = new javax.swing.JComboBox();
        jlTotal = new javax.swing.JLabel();
        jtfPurchasePrice = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jbBuyShare = new javax.swing.JButton();
        jbSellShare = new javax.swing.JButton();

        setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(AccountController.class, "AccountController.jPanel1.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("DejaVu Sans 13", 1, 12), new java.awt.Color(113, 196, 243))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 150));

        jlShare.setFont(new java.awt.Font("DejaVu Sans 13", 1, 12)); // NOI18N
        jlShare.setText(org.openide.util.NbBundle.getMessage(AccountController.class, "AccountController.jlShare.text")); // NOI18N

        jlAmount.setFont(new java.awt.Font("DejaVu Sans 13", 1, 12)); // NOI18N
        jlAmount.setText(org.openide.util.NbBundle.getMessage(AccountController.class, "AccountController.jlAmount.text")); // NOI18N

        jtfAmountOfShare.setColumns(7);
        jtfAmountOfShare.setText(org.openide.util.NbBundle.getMessage(AccountController.class, "AccountController.jtfAmountOfShare.text")); // NOI18N
        jtfAmountOfShare.setToolTipText(org.openide.util.NbBundle.getMessage(AccountController.class, "AccountController.jtfAmountOfShare.toolTipText")); // NOI18N

        jcbShares.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "", "", "", "", "" }));
        jcbShares.setToolTipText(org.openide.util.NbBundle.getMessage(AccountController.class, "AccountController.jcbShares.toolTipText")); // NOI18N
        jcbShares.setAutoscrolls(true);
        jcbShares.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbShareItemChange(evt);
            }
        });

        jlTotal.setFont(new java.awt.Font("DejaVu Sans 13", 1, 12)); // NOI18N
        jlTotal.setText(org.openide.util.NbBundle.getMessage(AccountController.class, "AccountController.jlTotal.text")); // NOI18N

        jtfPurchasePrice.setEditable(false);
        jtfPurchasePrice.setColumns(10);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlAmount)
                    .addComponent(jlShare)
                    .addComponent(jlTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtfPurchasePrice, 0, 1, Short.MAX_VALUE)
                    .addComponent(jcbShares, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtfAmountOfShare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(137, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbShares, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlShare, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfAmountOfShare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfPurchasePrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        add(jPanel1);

        jPanel2.setLayout(new java.awt.GridLayout(2, 0));

        jbBuyShare.setBackground(new java.awt.Color(254, 254, 254));
        jbBuyShare.setFont(new java.awt.Font("DejaVu Sans 13", 1, 12)); // NOI18N
        jbBuyShare.setIcon(new javax.swing.ImageIcon(getClass().getResource("/control/resources/tango/format-indent-less.png"))); // NOI18N
        jbBuyShare.setText(org.openide.util.NbBundle.getMessage(AccountController.class, "AccountController.jbBuyShare.text")); // NOI18N
        jbBuyShare.setToolTipText(org.openide.util.NbBundle.getMessage(AccountController.class, "AccountController.jbBuyShare.toolTipText")); // NOI18N
        jbBuyShare.setBorder(null);
        jbBuyShare.setPreferredSize(new java.awt.Dimension(150, 40));
        jbBuyShare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuyShareActionPerformed(evt);
            }
        });
        jPanel2.add(jbBuyShare);

        jbSellShare.setBackground(new java.awt.Color(254, 254, 254));
        jbSellShare.setFont(new java.awt.Font("DejaVu Sans 13", 1, 12)); // NOI18N
        jbSellShare.setIcon(new javax.swing.ImageIcon(getClass().getResource("/control/resources/tango/format-indent-more.png"))); // NOI18N
        jbSellShare.setText(org.openide.util.NbBundle.getMessage(AccountController.class, "AccountController.jbSellShare.text")); // NOI18N
        jbSellShare.setToolTipText(org.openide.util.NbBundle.getMessage(AccountController.class, "AccountController.jbSellShare.toolTipText")); // NOI18N
        jbSellShare.setBorder(null);
        jbSellShare.setPreferredSize(new java.awt.Dimension(150, 40));
        jbSellShare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSellShareActionPerformed(evt);
            }
        });
        jPanel2.add(jbSellShare);

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents
                        
                          

    private void jbBuyShareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuyShareActionPerformed
       if( getAccount().isPaused() == false ){ 
        
        Vector vShare = new Vector(1);        
        vShare.add( getShare().getShareName( getIdSelectedShare() ) );//0
        vShare.add( Integer.parseInt( jtfAmountOfShare.getText() ) );//1
        vShare.add( getShare().getPurchasePrice( getIdSelectedShare() ) );//2
        vShare.add( getShare().getPosPurchasePrice( getIdSelectedShare() ) );//3        
        vShare.add( getShare().getVariation( getIdSelectedShare() ) );//4
        vShare.add( getShare().getPosVariation( getIdSelectedShare() ) );//5
        vShare.add( getShare().getCurrentPeriod() );//6
        vShare.add( getAccount().getOpeningMoney() );//7
                
        //efetiva a compra 
        getAccount().buyShare( vShare );
         
    }else{
        JOptionPane.showMessageDialog(this, "<html> <br />Acione <i> Começar </i> para iniciar a simulação" + 
                "<br />" +
                "</html>");
    }
    }//GEN-LAST:event_jbBuyShareActionPerformed

    private void jbSellShareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSellShareActionPerformed
          if(getAccount().isPaused() == false){ 
         
        Vector vShare = new Vector(1);
        vShare.add( getShare().getShareName( getIdSelectedShare() ) );//0
        vShare.add( Integer.parseInt( jtfAmountOfShare.getText() ) );//1
        vShare.add( getShare().getPurchasePrice( getIdSelectedShare() ) );//2
        vShare.add( getShare().getPosPurchasePrice( getIdSelectedShare() ) );//3        
        vShare.add( getShare().getVariation( getIdSelectedShare() ) );//4
        vShare.add( getShare().getPosVariation( getIdSelectedShare() ) );//5
        vShare.add( getShare().getCurrentPeriod() );//6
        vShare.add( getAccount().getOpeningMoney() );//7
         
        getAccount().sellShare( vShare ); 
        
    }else{
        JOptionPane.showMessageDialog(this, "<html>  <br />Acione <i>Começar</i> para iniciar a simulação" +                
                "<br/>" +
                "</html>");
    }
    }//GEN-LAST:event_jbSellShareActionPerformed

    private void jcbShareItemChange(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbShareItemChange
         setIdSelectedShare(jcbShares.getSelectedIndex());
        jtfPurchasePrice.setText( moneyFormat.format( getShare().getPurchasePrice( getIdSelectedShare() ) ) );  
        
    }//GEN-LAST:event_jcbShareItemChange

           @SuppressWarnings("unchecked")
private void buySelectedShare(java.awt.event.ActionEvent evt) {                                  
    
    if( getAccount().isPaused() == false ){ 
        
        Vector vShare = new Vector(1);        
        vShare.add( getShare().getShareName( getIdSelectedShare() ) );//0
        vShare.add( Integer.parseInt( jtfAmountOfShare.getText() ) );//1
        vShare.add( getShare().getPurchasePrice( getIdSelectedShare() ) );//2
        vShare.add( getShare().getPosPurchasePrice( getIdSelectedShare() ) );//3        
        vShare.add( getShare().getVariation( getIdSelectedShare() ) );//4
        vShare.add( getShare().getPosVariation( getIdSelectedShare() ) );//5
        vShare.add( getShare().getCurrentPeriod() );//6
        vShare.add( getAccount().getOpeningMoney() );//7
                
        //efetiva a compra 
        getAccount().buyShare( vShare );
         
    }else{
        JOptionPane.showMessageDialog(this, "<html> <br />Acione <i> Começar </i> para iniciar a simulação" + 
                "<br />" +
                "</html>");
    }
    
    
}                                 

private void sellSelectedShare(java.awt.event.ActionEvent evt) {                                   
    if(getAccount().isPaused() == false){ 
         
        Vector vShare = new Vector(1);
        vShare.add( getShare().getShareName( getIdSelectedShare() ) );//0
        vShare.add( Integer.parseInt( jtfAmountOfShare.getText() ) );//1
        vShare.add( getShare().getPurchasePrice( getIdSelectedShare() ) );//2
        vShare.add( getShare().getPosPurchasePrice( getIdSelectedShare() ) );//3        
        vShare.add( getShare().getVariation( getIdSelectedShare() ) );//4
        vShare.add( getShare().getPosVariation( getIdSelectedShare() ) );//5
        vShare.add( getShare().getCurrentPeriod() );//6
        vShare.add( getAccount().getOpeningMoney() );//7
         
        getAccount().sellShare( vShare ); 
        
    }else{
        JOptionPane.showMessageDialog(this, "<html>  <br />Acione <i>Começar</i> para iniciar a simulação" +                
                "<br/>" +
                "</html>");
    }
}                                  
                          

    @Override
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
    public Share getShare() {
        return share;
    }
    
    public void setShare(Share share) {
        this.share = share;
    }
  
    public Time getTime() {
        return time;
    }
    
    private void setTime(Time time) {
        this.time = time;
    }
     
    public int getIdSelectedShare() {
        return idSelectedShare;
    }

    public void setIdSelectedShare(int idSelectedShare) {
        this.idSelectedShare = idSelectedShare;
    }

    public Vector getComboShares() {
        return comboShares;
    }

    public void setComboShares(int index, String avaliableShare) {
        this.comboShares.add(index, avaliableShare);
    }

    @Override
    public void updateDisplay() {
      jtfPurchasePrice.setText( moneyFormat.format( getShare().getPurchasePrice( getIdSelectedShare() ) ));
  }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton jbBuyShare;
    private javax.swing.JButton jbSellShare;
    private javax.swing.JComboBox jcbShares;
    private javax.swing.JLabel jlAmount;
    private javax.swing.JLabel jlShare;
    private javax.swing.JLabel jlTotal;
    private javax.swing.JTextField jtfAmountOfShare;
    private javax.swing.JTextField jtfPurchasePrice;
    // End of variables declaration//GEN-END:variables
}
