package view;

import model.Time;

/**
 * TimeView.java
 * @author Diego Schmaedech
 * @version 1.0
 * @since Release Beta
 * @link  http://code.google.com/p/economylabs
 * Created on August 2, 2008, 2:34 AM
 */
public class TimeView extends AbstractTimeView {
 
    /** Creates new form TimeView
     * @param time 
     */
    public TimeView( Time time ) {
        super( time );         
        initComponents();
        updateDisplay();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlTimer = new javax.swing.JLabel();
        jtfTimer = new javax.swing.JTextField();
        jlPeriod = new javax.swing.JLabel();
        jlInterval = new javax.swing.JLabel();
        jtfInterval = new javax.swing.JTextField();
        jtfPeriod = new javax.swing.JTextField();
        jpbTimer = new javax.swing.JProgressBar();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tempo Corrente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("DejaVu Sans 13", 1, 12), new java.awt.Color(113, 196, 243))); // NOI18N
        setPreferredSize(new java.awt.Dimension(400, 100));

        jlTimer.setFont(jlTimer.getFont().deriveFont(jlTimer.getFont().getStyle() | java.awt.Font.BOLD, jlTimer.getFont().getSize()+1));
        jlTimer.setText("Timer:"); // NOI18N

        jtfTimer.setEditable(false);
        jtfTimer.setColumns(20);
        jtfTimer.setFont(jtfTimer.getFont());
        jtfTimer.setText(getTime().getTimestamp());

        jlPeriod.setFont(jlPeriod.getFont().deriveFont(jlPeriod.getFont().getStyle() | java.awt.Font.BOLD, jlPeriod.getFont().getSize()+1));
        jlPeriod.setText("Periodo:"); // NOI18N

        jlInterval.setFont(jlInterval.getFont().deriveFont(jlInterval.getFont().getStyle() | java.awt.Font.BOLD, jlInterval.getFont().getSize()+1));
        jlInterval.setText("Intervalo:"); // NOI18N

        jtfInterval.setEditable(false);
        jtfInterval.setColumns(20);
        jtfInterval.setFont(jtfInterval.getFont());
        jtfInterval.setText(String.valueOf(getTime().getInterval()));

        jtfPeriod.setEditable(false);
        jtfPeriod.setColumns(20);
        jtfPeriod.setFont(jtfPeriod.getFont());
        jtfPeriod.setText(String.valueOf(getTime().getPeriod()));

        jpbTimer.setBackground(new java.awt.Color(254, 210, 156));
        jpbTimer.setFont(jpbTimer.getFont());
        jpbTimer.setMaximum(getTime().getInterval());
        jpbTimer.setValue(getTime().getTimerCounter());
        jpbTimer.setStringPainted(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlPeriod)
                    .addComponent(jlInterval))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jtfInterval, javax.swing.GroupLayout.Alignment.LEADING, 0, 1, Short.MAX_VALUE)
                    .addComponent(jtfPeriod, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlTimer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jpbTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(167, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlPeriod)
                    .addComponent(jtfPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlTimer)
                    .addComponent(jtfTimer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlInterval)
                    .addComponent(jtfInterval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpbTimer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(72, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jlInterval;
    private javax.swing.JLabel jlPeriod;
    private javax.swing.JLabel jlTimer;
    private javax.swing.JProgressBar jpbTimer;
    private javax.swing.JTextField jtfInterval;
    private javax.swing.JTextField jtfPeriod;
    private javax.swing.JTextField jtfTimer;
    // End of variables declaration//GEN-END:variables
  
    @Override
    public void updateDisplay() {
     // set text in balanceTextField to formatted balance
     jtfInterval.setText( String.valueOf( getTime().getInterval() ) );
     jtfPeriod.setText( String.valueOf( getTime().getPeriod() ) );
     jtfTimer.setText( String.valueOf( getTime().getTimestamp() ) ); 
     jpbTimer.setValue( getTime().getTimerCounter() );
  }
}
