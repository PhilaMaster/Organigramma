package gui.dialogs;

import node.Node;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

public class ManageEmployeeDialog extends JDialog {

//    public ManageEmployeeDialog(Frame parent, Node target) {
//        super(parent, "Gestione Ruoli", true);
//
//        // Crea la tabella con dati personalizzati
//        EmployeeTableModel tableModel = new EmployeeTableModel();
//        JTable table = new JTable(tableModel);
//        //imposto renderer e editor per la colonna relativa ai bottoni di rimozione
//        table.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
//        table.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox(),tableModel, target));
//
//        // Aggiungi la tabella in un JScrollPane
//        JScrollPane scrollPane = new JScrollPane(table);
//        getContentPane().add(scrollPane, BorderLayout.CENTER);
//
//        // Aggiungi il bottone in fondo
//        JButton addButton = new JButton("Aggiungi impiegato");
//        addButton.addActionListener(e -> new AddEmployeeDialog(parent, target, tableModel).setVisible(true));
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.add(addButton);
//        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
//    }
//
//
//    public static void main(String[] args) {
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(300, 200);
//        frame.setVisible(true);
//
//        JButton openDialogButton = new JButton("Aggiungi Dipendente");
//        openDialogButton.addActionListener(e -> {
////                ManageEmployeeDialog dialog = new ManageEmployeeDialog(parent);
////                dialog.setVisible(true);
//        });
//
//        frame.getContentPane().setLayout(new FlowLayout());
//        frame.getContentPane().add(openDialogButton);
//    }
}

//class AddEmployeeDialog extends JDialog {
//    public AddEmployeeDialog(Frame parent, Node target, EmployeeTableModel tableModel) {
//
//        super(parent, "Aggiungi Dipendente", true);
//        // Create form elements
//        JTextField firstNameField = new JTextField(20);
//        JTextField lastNameField = new JTextField(20);
//        JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"Manager", "Sviluppatore", "Analista", "Testatore"});
//        JButton cancelButton = new JButton("Annulla");
//        JButton confirmButton = new JButton("Conferma");
//
//        // Set layout
//        setLayout(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10, 10, 10, 10);
//        gbc.anchor = GridBagConstraints.WEST;
//
//        // Add elements to dialog
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        add(new JLabel("Nome:"), gbc);
//
//        gbc.gridx = 1;
//        add(firstNameField, gbc);
//
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        add(new JLabel("Cognome:"), gbc);
//
//        gbc.gridx = 1;
//        add(lastNameField, gbc);
//
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        add(new JLabel("Ruolo:"), gbc);
//
//        gbc.gridx = 1;
//        add(roleComboBox, gbc);
//
//        // Add buttons
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.add(confirmButton);
//        buttonPanel.add(cancelButton);
//
//        gbc.gridx = 0;
//        gbc.gridy = 3;
//        gbc.gridwidth = 2;
//        gbc.anchor = GridBagConstraints.CENTER;
//        add(buttonPanel, gbc);
//
//        // Add button listeners
//        cancelButton.addActionListener(e -> dispose());
//
//        confirmButton.addActionListener(e -> {
//            String firstName = firstNameField.getText();
//            String lastName = lastNameField.getText();
//            String role = (String) roleComboBox.getSelectedItem();
//
//            // Logic to handle the new employee data
//            System.out.println("Nome: " + firstName);
//            System.out.println("Cognome: " + lastName);
//            System.out.println("Ruolo: " + role);
//
//            dispose();
//        });
//
//        // Set default button
//        getRootPane().setDefaultButton(confirmButton);
//
//        pack();
//        setLocationRelativeTo(parent);
//    }
//}

//class EmployeeTableModel extends AbstractTableModel {
//    private final String[] columnNames = {"Nome", "Cognome", "Ruolo", "Rimuovi"};
//    protected final ArrayList<Object[]> data = new ArrayList<>();
//
//    //TODO solo debug per vedere se va, poi qui aggiungo i ruoli con for (per i ruoli non ereditati)
//    //e while(getParent()) per quelli ereditati
////    public EmployeeTableModel() {
////        // Aggiungi alcune righe iniziali
////        data.add(new Object[]{"Pasquale", "Papalia", "Direttore", "Rimuovi"});
////        data.add(new Object[]{"Andrea", "Nuara", "Capocessi", "Rimuovi"});
////        data.add(new Object[]{"Giuseppe Giulio", "Misitano", "Addetto cessi", "Rimuovi"});
////        data.add(new Object[]{"Giuseppe", "Zappia", "Laureato", "Rimuovi"});
////    }
//    @Override
//    public int getColumnCount() {
//        return columnNames.length;
//    }
//
//    @Override
//    public int getRowCount() {
//        return data.size();
//    }
//
//    @Override
//    public Object getValueAt(int row, int col) {
//        return data.get(row)[col];
//    }
//
//    @Override
//    public String getColumnName(int col) {
//        return columnNames[col];
//    }
//
//    @Override
//    public Class<?> getColumnClass(int col) {
//        return getValueAt(0, col).getClass();
//    }
//
//    @Override
//    public boolean isCellEditable(int row, int col) {
//        return col == 3;  // Solo la colonna del bottone è editabile
//    }
//
//    @Override
//    public void setValueAt(Object value, int row, int col) {
//        data.get(row)[col] = value;
//        fireTableCellUpdated(row, col);
//    }
//    public void addRow(Object[] rowData) {
//        data.add(rowData);
//        fireTableRowsInserted(data.size() - 1, data.size() - 1);
//    }
//    public void removeRow(int row){
//        data.remove(row);
//        fireTableRowsDeleted(row, row);//TODO row row?
//    }
//}
//
//class ButtonRenderer extends JButton implements TableCellRenderer {
//    public ButtonRenderer() {
//        setOpaque(true);
//    }
//
//    @Override
//    public Component getTableCellRendererComponent(JTable table, Object value,
//                                                   boolean isSelected, boolean hasFocus, int row, int column) {
//        setText((value == null) ? "" : value.toString());
//        return this;
//    }
//}
//
//class ButtonEditor extends DefaultCellEditor {
//    protected JButton button;
//    protected String label;
//    protected boolean isPushed;
//    protected final EmployeeTableModel tableModel;
//    protected int currentRow;
//    protected final Node target;
//
//    public ButtonEditor(JCheckBox checkBox, EmployeeTableModel tableModel, Node target) {
//        super(checkBox);
//        this.target = target;
//        this.tableModel = tableModel;
//        button = new JButton();
//        button.setOpaque(true);
//        button.addActionListener(e -> fireEditingStopped());
//    }
//
//    @Override
//    public Component getTableCellEditorComponent(JTable table, Object value,
//                                                 boolean isSelected, int row, int column) {
//        label = (value == null) ? "" : value.toString();
//        button.setText(label);
//        isPushed = true;
//        return button;
//    }
//
//    @Override
//    public Object getCellEditorValue() {
//        if (isPushed) {
//            // Azione da eseguire quando il bottone è premuto TODO rimuovere ruolo
//            //new RemoveRoleCommand(target,nomeRuolo);
//            SwingUtilities.invokeLater(() -> tableModel.removeRow(currentRow));
//            String nomeRuolo = (String) tableModel.getValueAt(currentRow,0);
//            System.out.println("nome ruolo rimosso: "+nomeRuolo);//debug
//        }
//        isPushed = false;
//        return label;
//    }
//
//    @Override
//    public boolean stopCellEditing() {
//        isPushed = false;
//        return super.stopCellEditing();
//    }
//
//    @Override
//    protected void fireEditingStopped() {
//        if (tableModel.getRowCount() > 0) {
//            super.fireEditingStopped();
//        }
//    }
//}
