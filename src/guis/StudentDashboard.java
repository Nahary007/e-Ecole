package guis;

import constants.CommonConstants;
import db.MyJDBC;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class StudentDashboard extends Form {
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public StudentDashboard() {
        super("Student Management");
        addGuiComponents();
        loadStudents();
    }

    private void addGuiComponents() {
        JLabel titleLabel = new JLabel("Student Management");
        titleLabel.setBounds(0, 20, 520, 40);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 28));
        titleLabel.setForeground(CommonConstants.TEXT_COLOR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);

        searchField = new JTextField();
        searchField.setBounds(20, 80, 300, 30);
        add(searchField);

        JButton insertButton = new JButton("Ajouter Étudiant");
        insertButton.setBounds(340, 80, 150, 30);
        insertButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        insertButton.addActionListener(e -> openInsertForm());
        add(insertButton);

        String[] columnNames = {"ID", "Nom", "Prénom", "Email","Filiere", "Opérations"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBounds(20, 130, 480, 500);
        add(scrollPane);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        studentTable.setRowSorter(sorter);
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            private void filter() {
                String text = searchField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        studentTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = studentTable.getSelectedRow();
                if (row >= 0 && e.getClickCount() == 2) {
                    int id = (int) tableModel.getValueAt(row, 0);
                    openStudentDetails(id);
                }
            }
        });

        studentTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int row = studentTable.rowAtPoint(e.getPoint());
                int col = studentTable.columnAtPoint(e.getPoint());
                if (row >= 0 && col == 4) {
                    int id = (int) tableModel.getValueAt(row, 0);
                    String[] options = {"Modifier", "Supprimer"};
                    int choice = JOptionPane.showOptionDialog(null,
                            "Que voulez-vous faire ?",
                            "Opérations Étudiant",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null, options, options[0]);
                    if (choice == 0) openUpdateForm(id);
                    if (choice == 1) deleteStudent(id);
                }
            }
        });
    }

    private void loadStudents() {
        tableModel.setRowCount(0);
        List<String[]> students = MyJDBC.getAllStudents();
        for (String[] s : students) {
            tableModel.addRow(new Object[]{Integer.parseInt(s[0]), s[1], s[2], s[3], "⚙"});
        }
    }

    private void openInsertForm() {
        JOptionPane.showMessageDialog(this, "Formulaire d'insertion étudiant (à implémenter)");
    }

    private void openUpdateForm(int id) {
        JOptionPane.showMessageDialog(this, "Formulaire de modification étudiant ID: " + id);
    }

    private void deleteStudent(int id) {
        if (MyJDBC.deleteStudent(id)) {
            JOptionPane.showMessageDialog(this, "Étudiant supprimé !");
            loadStudents();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur suppression !");
        }
    }

    private void openStudentDetails(int id) {
        JOptionPane.showMessageDialog(this, "Détails de l'étudiant ID: " + id);
    }
}
