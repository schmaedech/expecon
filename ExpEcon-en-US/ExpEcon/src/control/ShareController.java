package control;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.TableRowSorter;
import java.awt.Dimension;
import javax.swing.RowFilter;
import javax.swing.table.TableCellRenderer;
import model.Account;
import model.Share;
import model.StockData;
import model.Time;
import util.ColorRenderer;
import util.SpringUtilities;
import view.AbstractShareView;

/**
 * @author Diego Schmaedech
 * @version 1.1
 * @since Release Carnaval
 * @link  https://github.com/schmaedech/expecon
 * Created on August 2, 2008, 2:34 AM
 */
public class ShareController extends AbstractShareView {

    private boolean DEBUG = false;
    private JTable jtShare;
    private JTextField filterText;
    private JTextField statusText;
    private TableRowSorter<StockData> sorter;

    private Account account;
    private Share share;
    private Time time;
    private StockData model;

    public ShareController(Account controlledAccount, Share controlledShare, Time controlledTime, StockData modelShare) {
        super(controlledShare);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAccount(controlledAccount);
        setShare(controlledShare);
        setTime(controlledTime);
        setModel(modelShare);
        //Create a table with a sorter.
        
        sorter = new TableRowSorter<StockData>(getModel());

        jtShare = new JTable(model) {
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if ((row == 0) && (column == 2)) {
                    return new ColorRenderer(DEBUG);
                }
                if ((row == 1) && (column == 2)) {
                    return new ColorRenderer(DEBUG);
                }
                if ((row == 2) && (column == 2)) {
                    return new ColorRenderer(DEBUG);
                }
                if ((row == 3) && (column == 2)) {
                    return new ColorRenderer(DEBUG);
                }
                if ((row == 4) && (column == 2)) {
                    return new ColorRenderer(DEBUG);
                }
                if ((row == 5) && (column == 2)) {
                    return new ColorRenderer(DEBUG);
                }
                // else...
                return super.getCellRenderer(row, column);
            }
        };
        jtShare.setCellSelectionEnabled(true);
        jtShare.setGridColor(new java.awt.Color(113, 196, 243));

        jtShare.setRowSorter(sorter);
        jtShare.setPreferredScrollableViewportSize(new Dimension(500, 100));
        jtShare.setFillsViewportHeight(true);
        jtShare.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jtShare.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent event) {

                        int viewRow = jtShare.getSelectedRow();
                        if (viewRow < 0) {
                            //Selection got filtered away.
                            statusText.setText("");
                        } else {
                            int modelRow = jtShare.convertRowIndexToModel(viewRow);
                            statusText.setText(
                                    String.format("Selected Row in view: %d. "
                                            + "Selected Row in model: %d.",
                                            viewRow, modelRow));

                        }
                    }
                }
        );

        JScrollPane scrollPane = new JScrollPane(jtShare);
        setBackground(new java.awt.Color(251, 252, 170));
        setPreferredSize(new java.awt.Dimension(500, 150));
        add(scrollPane);

        //Create a separate form for filterText and statusText
        JPanel form = new JPanel(new SpringLayout());
        JLabel l1 = new JLabel("Filter Share:", SwingConstants.TRAILING);
        form.add(l1);
        filterText = new JTextField();
        filterText.setText(String.valueOf(getTime().getPeriod()));
        periodFilter();
        //Whenever filterText changes, invoke newFilter.
        filterText.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        periodFilter();
                    }

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        periodFilter();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        periodFilter();
                    }
                });
        l1.setLabelFor(filterText);
        form.add(filterText);
        JLabel l2 = new JLabel("Status:", SwingConstants.TRAILING);
        form.add(l2);
        statusText = new JTextField();
        l2.setLabelFor(statusText);
        form.add(statusText);
        SpringUtilities.makeCompactGrid(form, 2, 2, 6, 6, 6, 6);
        //add(form);
    }

    private void periodFilter() {
        RowFilter<StockData, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter("^" + filterText.getText() + "$", 4);

        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }

    public void setModel(StockData model) {
        this.model = model;
    }

    public StockData getModel() {
        return model;
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

    @Override
    public void updateDisplay() {
        filterText.setText(String.valueOf(getTime().getPeriod()));
        periodFilter();
    }
}
