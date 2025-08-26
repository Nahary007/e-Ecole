package guis;

import constants.CommonConstants;
import db.MyJDBC;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class StudentDashboard extends Form {
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public StudentDashboard() {
        super("Gestion des Étudiants");
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(CommonConstants.SECONDARY_COLOR);
        addGuiComponents();
        loadStudents();
    }

    private void addGuiComponents() {
        // Panneau supérieur (titre et recherche)
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(CommonConstants.SECONDARY_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Titre
        JLabel titleLabel = new JLabel("Gestion des Étudiants");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(CommonConstants.TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        topPanel.add(titleLabel, gbc);

        // Champ de recherche
        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBackground(new Color(60, 60, 60));
        searchField.setForeground(Color.GRAY);
        searchField.setText("Rechercher un étudiant...");
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        searchField.setToolTipText("Rechercher par nom, prénom, email ou filière");
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Rechercher un étudiant...")) {
                    searchField.setText("");
                    searchField.setForeground(CommonConstants.TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Rechercher un étudiant...");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        topPanel.add(searchField, gbc);

        add(topPanel, BorderLayout.NORTH);

        // Barre latérale pour les actions
        JPanel sidebar = new JPanel(new GridBagLayout());
        sidebar.setBackground(new Color(30, 30, 30));
        sidebar.setPreferredSize(new Dimension(150, 0));
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Bouton Ajouter
        JButton insertButton = new JButton("Ajouter");
        insertButton.setFont(new Font("Arial", Font.BOLD, 14));
        insertButton.setBackground(CommonConstants.PRIMARY_COLOR);
        insertButton.setForeground(CommonConstants.TEXT_COLOR);
        insertButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        insertButton.setBorder(BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 1, true));
        insertButton.setFocusPainted(false);
        insertButton.setToolTipText("Ajouter un nouvel étudiant");
        insertButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                insertButton.setBackground(CommonConstants.TEXT_COLOR);
                insertButton.setForeground(CommonConstants.PRIMARY_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                insertButton.setBackground(CommonConstants.PRIMARY_COLOR);
                insertButton.setForeground(CommonConstants.TEXT_COLOR);
            }
        });
        insertButton.addActionListener(e -> openInsertForm());
        gbc.gridx = 0;
        gbc.gridy = 0;
        sidebar.add(insertButton, gbc);

        // Bouton Déconnexion
        JButton logoutButton = new JButton("Déconnexion");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setBackground(new Color(180, 50, 50));
        logoutButton.setForeground(CommonConstants.TEXT_COLOR);
        logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutButton.setBorder(BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 1, true));
        logoutButton.setFocusPainted(false);
        logoutButton.setToolTipText("Retour à l'écran de connexion");
        logoutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                logoutButton.setBackground(new Color(200, 70, 70));
                logoutButton.setForeground(CommonConstants.PRIMARY_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logoutButton.setBackground(new Color(180, 50, 50));
                logoutButton.setForeground(CommonConstants.TEXT_COLOR);
            }
        });
        logoutButton.addActionListener(e -> {
            StudentDashboard.this.dispose();
            new LoginForm().setVisible(true);
        });
        gbc.gridy = 1;
        sidebar.add(logoutButton, gbc);

        add(sidebar, BorderLayout.WEST);

        // Tableau des étudiants
        String[] columnNames = {"ID", "Nom", "Prénom", "Email", "Filière", "Actions"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };
        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(25);
        studentTable.setFont(new Font("Arial", Font.PLAIN, 12));
        studentTable.setBackground(new Color(50, 50, 50));
        studentTable.setForeground(CommonConstants.TEXT_COLOR);
        studentTable.setGridColor(CommonConstants.TEXT_COLOR);
        studentTable.setSelectionBackground(new Color(80, 80, 80));
        studentTable.setSelectionForeground(CommonConstants.TEXT_COLOR);

        // Centrer les cellules et alternance des couleurs
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? new Color(50, 50, 50) : new Color(60, 60, 60));
                return c;
            }
        };
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < studentTable.getColumnCount(); i++) {
            studentTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Style de la colonne Actions
        studentTable.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setFont(new Font("Arial", Font.BOLD, 12));
                label.setForeground(CommonConstants.TEXT_COLOR);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });

        // Ajuster la largeur des colonnes
        studentTable.getColumnModel().getColumn(0).setPreferredWidth(50); // ID
        studentTable.getColumnModel().getColumn(5).setPreferredWidth(50); // Actions

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 1, true));
        add(scrollPane, BorderLayout.CENTER);

        // Filtre de recherche
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        studentTable.setRowSorter(sorter);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filter(); }
            private void filter() {
                String text = searchField.getText().trim();
                if (text.equals("Rechercher un étudiant...")) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        // Double-clic pour voir les détails
        studentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = studentTable.getSelectedRow();
                    if (row >= 0) {
                        int id = (int) tableModel.getValueAt(row, 0);
                        openStudentDetails(id);
                    }
                }
            }
        });

        // Clic sur la colonne Actions
        studentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = studentTable.rowAtPoint(e.getPoint());
                int col = studentTable.columnAtPoint(e.getPoint());
                if (row >= 0 && col == 5) {
                    int id = (int) tableModel.getValueAt(row, 0);
                    String[] options = {"Modifier", "Supprimer"};
                    int choice = JOptionPane.showOptionDialog(null,
                            "Que voulez-vous faire ?",
                            "Actions Étudiant",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null, options, options[0]);
                    if (choice == 0) openUpdateForm(id);
                    if (choice == 1) deleteStudent(id);
                }
            }
        });

        // Configurer la fenêtre en plein écran
        setExtendedState(JFrame.MAXIMIZED_BOTH); // ✅ plein écran uniquement pour StudentDashboard
        setResizable(true); // autorise le redimensionnement
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void loadStudents() {
        tableModel.setRowCount(0);
        List<String[]> students = MyJDBC.getAllStudents();
        for (String[] s : students) {
            tableModel.addRow(new Object[]{Integer.parseInt(s[0]), s[1], s[2], s[3], s[4], "⚙"});
        }
    }

    private void openInsertForm() {
        new AddStudentForm(this);
    }

    private void openUpdateForm(int id) {
        new UpdateStudentForm(this, id);
    }

    private void deleteStudent(int id) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Voulez-vous vraiment supprimer cet étudiant ?",
                "Confirmer la suppression",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (MyJDBC.deleteStudent(id)) {
                JOptionPane.showMessageDialog(this, "Étudiant supprimé avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                loadStudents();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openStudentDetails(int id) {
        String[] studentData = MyJDBC.getStudentById(id);
        if (studentData != null) {
            String details = String.format(
                    "<html><b>Détails de l'étudiant</b><br>" +
                            "ID: %d<br>Nom: %s<br>Prénom: %s<br>Email: %s<br>Filière: %s</html>",
                    id, studentData[0], studentData[1], studentData[2], studentData[3]);
            JOptionPane.showMessageDialog(this, details, "Détails Étudiant", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la récupération des détails.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
