package gui.dialogs;

import node.Node;
import node.users_management.Role;

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
        escChiusuraFinestra();
        // Crea la tabella con dati personalizzati
        ArrayListTableModel tableModel = new ArrayListTableModel(target);
        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(3).setCellRenderer(new RolesButtonRenderer());
        table.getColumnModel().getColumn(3).setCellEditor(new RolesButtonEditor(new JCheckBox(),tableModel,target));

        // Aggiungi la tabella in un JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Aggiungi il bottone in fondo
        JButton addButton = new JButton("Aggiungi ruolo");
        addButton.addActionListener(e -> new AddRolesDialog(parent, target, tableModel).setVisible(true));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Impostazioni del dialog
        setSize(500, 300);
        setLocationRelativeTo(parent);
    }

    private void escChiusuraFinestra() {
        // Crea un KeyStroke per il tasto ESC
        KeyStroke escKeyStroke = KeyStroke.getKeyStroke("ESCAPE");

        // Ottieni l'InputMap per la finestra in modalità focus
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        // Mappa il tasto ESC a una azione chiamata "closeDialog"
        inputMap.put(escKeyStroke, "closeDialog");

        // Ottieni l'ActionMap per la finestra
        ActionMap actionMap = rootPane.getActionMap();

        // Definisci l'azione da eseguire quando viene premuto ESC
        actionMap.put("closeDialog", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageRolesDialog.this.dispose();
            }
        });
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            ManageRolesDialog dialog = new ManageRolesDialog(null,null);
//            dialog.setVisible(true);
//        });
//    }
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
                row[2] = "";
                row[3] = "Rimuovi";
                tableModel.addRow(row);
                target.addRole(new Role(roleName,inheritRole));
            }
            dispose();
        });

        // Imposta il pulsante di conferma come pulsante predefinito
        getRootPane().setDefaultButton(confirmButton);

        pack();
        setLocationRelativeTo(parent);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gestione Ruoli");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Lista dei ruoli esistenti
        DefaultListModel<String> roleListModel = new DefaultListModel<>();
        JList<String> roleList = new JList<>(roleListModel);
        JScrollPane roleScrollPane = new JScrollPane(roleList);

        // Pulsante per aggiungere un nuovo ruolo
        JButton addRoleButton = new JButton("Aggiungi Ruolo");
        addRoleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageEmployeeDialog dialog = new ManageEmployeeDialog(frame,null);
                dialog.setVisible(true);

                // Esempio di utilizzo dei dati ottenuti dal dialogo
//                String roleName = dialog.getRoleName();
//                if (roleName != null && !roleName.isEmpty()) {
//                    boolean inheritRole = dialog.isInheritRole();
//                    String roleInfo = roleName + (inheritRole ? " (ereditato)" : "");
//                    roleListModel.addElement(roleInfo);
//                }
            }
        });

        // Layout del frame principale
        frame.setLayout(new BorderLayout());
        frame.add(new JLabel("Ruoli Esistenti:"), BorderLayout.NORTH);
        frame.add(roleScrollPane, BorderLayout.CENTER);
        frame.add(addRoleButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}

/*class CustomTableModel extends AbstractTableModel {
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
*/
class ArrayListTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Ruolo", "Ereditato", "Padre", "Rimuovi"};
    private final ArrayList<Object[]> data = new ArrayList<>();


    public ArrayListTableModel(Node target) {
        //TODO sistemare con oggetto ruolo invece di oggetto Object
        //Ruoli del nodo stesso
        for(Role role : target.getRoles()) {
            Object[] row = new Object[4];
            row[0] = role.getRole();
            row[1] = Boolean.FALSE;
            row[2] = "";
            row[3] = "Rimuovi";
            data.add(row);
        }

        Node parent = target.getParent();
        while(parent!=null){
            for(Role role : parent.getRoles())
                if (role.isExtend()) {
                    Object[] row = new Object[4];
                    row[0] = role.getRole();
                    row[1] = Boolean.TRUE;
                    row[2] = parent.getGraphic().getName();
                    row[3] = null;
                    data.add(row);
                }
            parent = parent.getParent();
        }
        //si potrebbero inglobare in un unico do-while volendo
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

    public Role getRow(int currentRow) {
        return new Role((String) getValueAt(currentRow,0),(boolean) getValueAt(currentRow,1));
    }
}

class RolesButtonRenderer extends JButton implements TableCellRenderer {
    private final JLabel emptyLabel = new JLabel();
    public RolesButtonRenderer() {
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

class RolesButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
    private final JLabel emptyLabel = new JLabel();
    private final ArrayListTableModel tableModel;
    private int currentRow;
    private final Node target;

    public RolesButtonEditor(JCheckBox checkBox, ArrayListTableModel tableModel, Node target) {
        super(checkBox);
        this.tableModel = tableModel;
        this.target = target;
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

            Role role = tableModel.getRow(currentRow);
            target.removeRole(role);
//            String nomeRuolo = (String) tableModel.getValueAt(currentRow,0);
//            System.out.println("nome ruolo rimosso: "+nomeRuolo);

            SwingUtilities.invokeLater(() -> tableModel.removeRow(currentRow));
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
