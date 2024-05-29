package gui.dialogs;

import gui.GraphicNode;
import gui.UserInterfacePanel;
import node.CompositeNode;
import node.Node;
import node.Employee;
import node.Role;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class ManageEmployeeDialog extends JDialog {

    public ManageEmployeeDialog(final Frame parent, final Node target) {
        super(parent, "Gestione Impiegati", true);

        escChiusuraFinestra();

        // Crea la tabella con dati personalizzati
        EmployeeTableModel tableModel = new EmployeeTableModel(target);
        JTable table = new JTable(tableModel);
        //imposto renderer e editor per la colonna relativa ai bottoni di rimozione
        table.getColumnModel().getColumn(3).setCellRenderer(new EmployeeButtonRenderer());
        table.getColumnModel().getColumn(3).setCellEditor(new EmployeeButtonEditor(new JCheckBox(),tableModel, target));

        // Aggiungi la tabella in un JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Aggiungi il bottone in fondo
        JButton addButton = new JButton("Aggiungi impiegato");
        addButton.addActionListener(e -> new AddEmployeeDialog(parent, target, tableModel).setVisible(true));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setSize(500, 300);
        setLocationRelativeTo(parent);
    }

    private void escChiusuraFinestra() {
        //codice per implementare la chiusura della finestra con tasto escape
        KeyStroke escKeyStroke = KeyStroke.getKeyStroke("ESCAPE");
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(escKeyStroke, "closeDialog");
        ActionMap actionMap = rootPane.getActionMap();
        actionMap.put("closeDialog", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageEmployeeDialog.this.dispose();
            }
        });
    }

    public static void main(String[] args) {
        //main di test
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setVisible(true);

        JButton openDialogButton = new JButton("Aggiungi Dipendente");
        openDialogButton.addActionListener(e -> {
                Node example = new CompositeNode(0,new GraphicNode("prova"));
                example.addRole(new Role("Dirigente",true));
                example.addRole(new Role("Impiegato semplice",false));
                ManageEmployeeDialog dialog = new ManageEmployeeDialog(frame,example);
                dialog.setVisible(true);
        });

        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(openDialogButton);
    }
}

class AddEmployeeDialog extends JDialog {
    public AddEmployeeDialog(final Frame parent, final Node target, final EmployeeTableModel tableModel) {
        super(parent, "Aggiungi Dipendente", true);
        JTextField firstNameField = new JTextField(20);
        JTextField lastNameField = new JTextField(20);

        List<Role> allRoles = new ArrayList<>();
        allRoles.addAll(target.getInheritedRoles());
        allRoles.addAll(target.getRoles());
        JComboBox<String> roleComboBox = new JComboBox<>(allRoles
                .stream()
                .map(Role::getRole)
                .toArray(String[]::new));

        JButton cancelButton = new JButton("Annulla");
        JButton confirmButton = new JButton("Conferma");

        //gestione layout e aggiunta componenti
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Add elements to dialog
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Cognome:"), gbc);

        gbc.gridx = 1;
        add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Ruolo:"), gbc);

        gbc.gridx = 1;
        add(roleComboBox, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        if(allRoles.isEmpty()) JOptionPane.showMessageDialog(this,
                "Attenzione, i ruoli per il nodo selezionato non sono stati ancora inseriti, si prega di aggiungerne qualcuno prima di aggiungere impiegati.",
                "Warning", JOptionPane.WARNING_MESSAGE);

        cancelButton.addActionListener(e -> dispose());

        confirmButton.addActionListener(ev -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String role = (String) roleComboBox.getSelectedItem();
            if(firstName.isBlank()) {
                JOptionPane.showMessageDialog(this, "Il campo nome non può essere vuoto", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if(lastName.isBlank()) {
                JOptionPane.showMessageDialog(this, "Il campo cognome non può essere vuoto", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if(role==null) {
                JOptionPane.showMessageDialog(this, "Il campo ruolo non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }


            Employee e = new Employee(firstName,lastName,new Role(role,false));

            tableModel.addRow(e);
            target.addEmployee(e);
            ((UserInterfacePanel) target.getGraphic().getParent()).setModificato(true);
            dispose();
        });

        // Set default button
        getRootPane().setDefaultButton(confirmButton);

        pack();
        setLocationRelativeTo(parent);
    }
}

class EmployeeTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Nome", "Cognome", "Ruolo", "Rimuovi"};
    protected final ArrayList<Employee> data = new ArrayList<>();

    public EmployeeTableModel(Node target) {
        data.addAll(target.getEmployees());
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
        return switch (col) {
            case 0 -> data.get(row).getName();
            case 1 -> data.get(row).getSurname();
            case 2 -> data.get(row).getRole();
            case 3 -> "Rimuovi";
            default -> throw new IndexOutOfBoundsException();
        };
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
//        data.get(row)[col] = value;
//        fireTableCellUpdated(row, col);
//        throw new UnsupportedOperationException("Employee not mutable.");
    }
    public void addRow(Employee e) {
        //data.add(new Employee((String) rowData[0],(String) rowData[1],(Role) rowData[2]));
        data.add(e);
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }
    public void removeRow(int row){
        data.remove(row);
        fireTableRowsDeleted(row, row);
    }
    public Employee getRowData(int row) {
        return data.get(row);
    }
}

class EmployeeButtonRenderer extends JButton implements TableCellRenderer {
    public EmployeeButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

class EmployeeButtonEditor extends DefaultCellEditor {
    protected JButton button;
    protected String label;
    protected boolean isPushed;
    protected final EmployeeTableModel tableModel;
    protected int currentRow;
    protected final Node target;

    public EmployeeButtonEditor(JCheckBox checkBox, EmployeeTableModel tableModel, Node target) {
        super(checkBox);
        this.target = target;
        this.tableModel = tableModel;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            Employee e = tableModel.getRowData(currentRow);
            SwingUtilities.invokeLater(() -> tableModel.removeRow(currentRow));
            target.removeEmployee(e);
            ((UserInterfacePanel) target.getGraphic().getParent()).setModificato(true);
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
        if (tableModel.getRowCount() > 0) {
            super.fireEditingStopped();
        }
    }
}
