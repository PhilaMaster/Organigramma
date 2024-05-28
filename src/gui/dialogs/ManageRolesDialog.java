package gui.dialogs;

import node.Node;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;


public class ManageRolesDialog extends JDialog {
    public ManageRolesDialog(Frame parent, Node target) {
        super(parent, "Gestione Ruoli", true);

        // Crea la tabella con dati personalizzati
        ArrayListTableModel tableModel = new ArrayListTableModel();
        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox(),tableModel));

        // Aggiungi la tabella in un JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Aggiungi il bottone in fondo
        JButton closeButton = new JButton("Aggiungi ruolo");
        closeButton.addActionListener(e -> new AddRolesDialog(parent, target, tableModel).setVisible(true));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Impostazioni del dialog
        setSize(500, 300);
        setLocationRelativeTo(parent);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ManageRolesDialog dialog = new ManageRolesDialog(null,null);
            dialog.setVisible(true);
        });
    }
}

class AddRolesDialog extends JDialog {
    private final Node target;
    private final ArrayListTableModel tableModel;

    public AddRolesDialog(Frame parent, Node target, ArrayListTableModel tableModel) {
        super(parent, "Gestisci ruoli", true);
        this.target = target;
        this.tableModel = tableModel;
        // Crea gli elementi del form
        JTextField roleNameField = new JTextField(20);
        JCheckBox inheritCheckBox = new JCheckBox();

        JButton cancelButton = new JButton("Annulla");
        JButton confirmButton = new JButton("Conferma");

        // Imposta il layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Aggiungi gli elementi al dialogo
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Nome del Ruolo:"), gbc);

        gbc.gridx = 2;
        add(roleNameField, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Eredita ai nodi figli?"), gbc);

        gbc.gridx = 1;
        JButton help = new JButton("?");
        help.addActionListener(e -> JOptionPane.showMessageDialog(parent,
                "Se abilitato, permette l'aggiunta del ruolo anche ai nodi figli","Inheritance",JOptionPane.INFORMATION_MESSAGE));
        add(help,gbc);

        gbc.gridx = 2;
        add(inheritCheckBox, gbc);


        // Aggiungi i bottoni
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        // Listener per i bottoni
        cancelButton.addActionListener(e -> dispose());

        confirmButton.addActionListener(e -> {
            String roleName = roleNameField.getText().trim();
            boolean inheritRole = inheritCheckBox.isSelected();

            if(!roleName.isBlank()) {
                // Logica per gestire i dati del nuovo ruolo
                System.out.println("Nome del Ruolo: " + roleName);
                System.out.println("Eredita ai figli: " + inheritRole);
                //TODO aggiungi ruolo al nodo (magari con un command)
                Object[] row = new Object[4];
                row[0] = roleName;
                row[1] = Boolean.FALSE;
                row[2] = null;
                row[3] = "Rimuovi";
                tableModel.addRow(row);
            }
            dispose();
        });

        // Imposta il pulsante di conferma come pulsante predefinito
        getRootPane().setDefaultButton(confirmButton);

        pack();
        setLocationRelativeTo(parent);
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Gestione Ruoli");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(400, 300);
//
//        // Lista dei ruoli esistenti
//        DefaultListModel<String> roleListModel = new DefaultListModel<>();
//        JList<String> roleList = new JList<>(roleListModel);
//        JScrollPane roleScrollPane = new JScrollPane(roleList);
//
//        // Pulsante per aggiungere un nuovo ruolo
//        JButton addRoleButton = new JButton("Aggiungi Ruolo");
//        addRoleButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ManageEmployeeDialog dialog = new ManageEmployeeDialog(frame);
//                dialog.setVisible(true);
//
//                // Esempio di utilizzo dei dati ottenuti dal dialogo
////                String roleName = dialog.getRoleName();
////                if (roleName != null && !roleName.isEmpty()) {
////                    boolean inheritRole = dialog.isInheritRole();
////                    String roleInfo = roleName + (inheritRole ? " (ereditato)" : "");
////                    roleListModel.addElement(roleInfo);
////                }
//            }
//        });
//
//        // Layout del frame principale
//        frame.setLayout(new BorderLayout());
//        frame.add(new JLabel("Ruoli Esistenti:"), BorderLayout.NORTH);
//        frame.add(roleScrollPane, BorderLayout.CENTER);
//        frame.add(addRoleButton, BorderLayout.SOUTH);
//
//        frame.setVisible(true);
//    }
}

class CustomTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Ruolo", "Ereditato", "Padre", "Rimuovi"};
    private final Object[][] data = {
            {"Hello", Boolean.TRUE, "Bar", null},
            {"Foo", Boolean.FALSE, null, "Rimuovi"},
            {"Java", Boolean.FALSE, null, "Rimuovi"},
            {"Example", Boolean.TRUE, "Swing", null}
    };

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Class<?> getColumnClass(int col) {
        return getValueAt(0, col).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == 3;  // Solo la colonna del bottone è editabile
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}

class ArrayListTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Ruolo", "Ereditato", "Padre", "Rimuovi"};
    private final ArrayList<Object[]> data = new ArrayList<>();

    //TODO solo debug per vedere se va, poi qui aggiungo i ruoli con for (per i ruoli non ereditati)
    //e while(getParent()) per quelli ereditati
    public ArrayListTableModel() {
        // Aggiungi alcune righe iniziali
        data.add(new Object[]{"Hello", Boolean.TRUE, "Bar", null});
        data.add(new Object[]{"Foo", Boolean.FALSE, null, "Rimuovi"});
        data.add(new Object[]{"Java", Boolean.FALSE, null, "Rimuovi"});
        data.add(new Object[]{"Example", Boolean.TRUE, "Swing", null});
    }
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data.get(row)[col];
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Class<?> getColumnClass(int col) {
        return getValueAt(0, col).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == 3;  // Solo la colonna del bottone è editabile
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        data.get(row)[col] = value;
        fireTableCellUpdated(row, col);
    }
    public void addRow(Object[] rowData) {
        data.add(rowData);
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }
    public void removeRow(int row){
        data.remove(row);
        fireTableRowsDeleted(row, row);//TODO row row?
    }
}

class ButtonRenderer extends JButton implements TableCellRenderer {
    private final JLabel emptyLabel = new JLabel();
    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if(value==null)//Se value==null allora non devo renderizzare il bottone
            return emptyLabel;
        setText(value.toString());
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
    private final JLabel emptyLabel = new JLabel();
    private final ArrayListTableModel tableModel;
    private int currentRow;
    //private final Node target;

    public ButtonEditor(JCheckBox checkBox, ArrayListTableModel tableModel) {
        super(checkBox);
        this.tableModel = tableModel;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        if(value==null) {
            label = null;
            return emptyLabel; //Se value==null allora non devo creare il bottone
        }
        label = value.toString();
        button.setText(label);
        isPushed = true;
        currentRow = row;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            // Azione da eseguire quando il bottone è premuto TODO rimuovere ruolo
//            JOptionPane.showMessageDialog(button, label + ": Button clicked!");
            SwingUtilities.invokeLater(() -> tableModel.removeRow(currentRow));
            String nomeRuolo = (String) tableModel.getValueAt(currentRow,0);
            System.out.println("nome ruolo rimosso: "+nomeRuolo);
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}

