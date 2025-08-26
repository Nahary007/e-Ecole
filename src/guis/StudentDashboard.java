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
    private JLabel statsLabel;

    // Couleurs améliorées
    private static final Color PRIMARY_COLOR = new Color(34, 40, 49);
    private static final Color SECONDARY_COLOR = new Color(57, 62, 70);
    private static final Color ACCENT_COLOR = new Color(0, 173, 181);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);
    private static final Color TEXT_PRIMARY = new Color(236, 240, 241);
    private static final Color TEXT_SECONDARY = new Color(149, 165, 166);
    private static final Color CARD_BG = new Color(44, 62, 80);

    public StudentDashboard() {
        super("Gestion des Étudiants");
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(CommonConstants.PRIMARY_COLOR);
        addGuiComponents();
        loadStudents();

    }

    private void addGuiComponents() {
        // 🔹 Header avec design moderne
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // 🔹 Sidebar modernisée
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        // 🔹 Contenu principal
        JPanel mainContent = createMainContent();
        add(mainContent, BorderLayout.CENTER);

        // Configuration de la fenêtre
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();
                GradientPaint gradient = new GradientPaint(
                        0, 0, SECONDARY_COLOR,
                        w, 0, CARD_BG
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, w, h);

                // Ligne décorative
                g2d.setColor(ACCENT_COLOR);
                g2d.fillRect(0, h-3, w, 3);
            }
        };
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        headerPanel.setPreferredSize(new Dimension(0, 85));

        // Titre avec icône
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setOpaque(false);

        JLabel iconLabel = new JLabel("🎓");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        titlePanel.add(iconLabel);

        JLabel titleLabel = new JLabel("  Gestion des Étudiants");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_PRIMARY);
        titlePanel.add(titleLabel);

        headerPanel.add(titlePanel, BorderLayout.WEST);

        // Panel de recherche et stats
        JPanel rightPanel = new JPanel(new BorderLayout(10, 0));
        rightPanel.setOpaque(false);

        // Statistiques
        statsLabel = new JLabel();
        statsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statsLabel.setForeground(TEXT_SECONDARY);
        rightPanel.add(statsLabel, BorderLayout.NORTH);

        // Champ de recherche amélioré
        searchField = createSearchField();
        rightPanel.add(searchField, BorderLayout.SOUTH);

        headerPanel.add(rightPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JTextField createSearchField() {
        JTextField field = new JTextField("🔍 Rechercher un étudiant...") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                super.paintComponent(g);
            }
        };

        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(CommonConstants.BLACK_COLOR);
        field.setBackground(CommonConstants.WHITE_COLOR);
        field.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        field.setPreferredSize(new Dimension(300, 40));
        field.setOpaque(false);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals("🔍 Rechercher un étudiant...")) {
                    field.setText("");
                    field.setForeground(TEXT_PRIMARY);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText("🔍 Rechercher un étudiant...");
                    field.setForeground(TEXT_SECONDARY);
                }
            }
        });

        return field;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(SECONDARY_COLOR);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));

        // Logo/Avatar section
        JPanel logoPanel = createLogoPanel();
        sidebar.add(logoPanel);
        sidebar.add(Box.createVerticalStrut(30));

        // Boutons d'action
        JButton addButton = createModernButton("➕  Ajouter un étudiant", SUCCESS_COLOR);
        addButton.addActionListener(e -> openInsertForm());
        sidebar.add(addButton);
        sidebar.add(Box.createVerticalStrut(15));

        JButton refreshButton = createModernButton("🔄  Actualiser", ACCENT_COLOR);
        refreshButton.addActionListener(e -> {
            loadStudents();

        });
        sidebar.add(refreshButton);

        // Spacer
        sidebar.add(Box.createVerticalGlue());

        // Bouton de déconnexion
        JButton logoutButton = createModernButton("🚪  Déconnexion", DANGER_COLOR);
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Êtes-vous sûr de vouloir vous déconnecter ?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                StudentDashboard.this.dispose();
                new LoginForm().setVisible(true);
            }
        });
        sidebar.add(logoutButton);

        return sidebar;
    }

    private JPanel createLogoPanel() {
        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Avatar circulaire
        JLabel avatar = new JLabel("👤") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(ACCENT_COLOR);
                g2d.fillOval(10, 10, 60, 60);
                super.paintComponent(g);
            }
        };

        return logoPanel;
    }

    private JPanel createMainContent() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(PRIMARY_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Tableau dans une card moderne
        JPanel tableCard = createTableCard();
        mainPanel.add(tableCard, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createTableCard() {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(CARD_BG);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // En-tête de la table
        JLabel tableTitle = new JLabel("📋 Liste des Étudiants");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        tableTitle.setForeground(TEXT_PRIMARY);
        tableTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        card.add(tableTitle, BorderLayout.NORTH);

        // Tableau stylisé
        createStyledTable();
        JScrollPane scrollPane = new JScrollPane(studentTable) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(SECONDARY_COLOR);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            }
        };
        scrollPane.setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setOpaque(false);
        card.add(scrollPane, BorderLayout.CENTER);

        return card;
    }

    private void createStyledTable() {
        String[] columnNames = {"ID", "Nom", "Prénom", "Email", "Filière", "Actions"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };

        studentTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (!isRowSelected(row)) {
                    if (row % 2 == 0) {
                        c.setBackground(SECONDARY_COLOR);
                    } else {
                        c.setBackground(new Color(52, 58, 64));
                    }
                    c.setForeground(TEXT_PRIMARY);
                } else {
                    c.setBackground(ACCENT_COLOR);
                    c.setForeground(Color.WHITE);
                }

                // Style spécial pour la colonne Actions
                if (column == 5) {
                    c.setForeground(WARNING_COLOR);
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                }

                return c;
            }
        };

        // Configuration du tableau
        studentTable.setRowHeight(35);
        studentTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        studentTable.setGridColor(new Color(70, 70, 70));
        studentTable.setShowVerticalLines(true);
        studentTable.setShowHorizontalLines(true);
        studentTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // En-têtes stylisés
        studentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        studentTable.getTableHeader().setBackground(ACCENT_COLOR);
        studentTable.getTableHeader().setForeground(Color.WHITE);
        studentTable.getTableHeader().setPreferredSize(new Dimension(0, 40));

        // Centrer le contenu
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < studentTable.getColumnCount(); i++) {
            studentTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Ajuster les largeurs de colonnes
        studentTable.getColumnModel().getColumn(0).setMaxWidth(60);
        studentTable.getColumnModel().getColumn(5).setMaxWidth(100);

        setupTableInteractions();
    }

    private void setupTableInteractions() {
        // Filtre de recherche
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        studentTable.setRowSorter(sorter);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filter(); }
            public void removeUpdate(DocumentEvent e) { filter(); }
            public void changedUpdate(DocumentEvent e) { filter(); }

            private void filter() {
                String text = searchField.getText().trim();
                if (text.equals("🔍 Rechercher un étudiant...")) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        // Interactions souris
        studentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = studentTable.getSelectedRow();
                    if (row >= 0) {
                        int modelRow = studentTable.convertRowIndexToModel(row);
                        int id = (int) tableModel.getValueAt(modelRow, 0);
                        openStudentDetails(id);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int row = studentTable.rowAtPoint(e.getPoint());
                int col = studentTable.columnAtPoint(e.getPoint());

                if (row >= 0 && col == 5) {
                    int modelRow = studentTable.convertRowIndexToModel(row);
                    int id = (int) tableModel.getValueAt(modelRow, 0);
                    showActionMenu(e, id);
                }
            }
        });
    }

    private void showActionMenu(MouseEvent e, int studentId) {
        JPopupMenu popup = new JPopupMenu();
        popup.setBackground(CARD_BG);
        popup.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 1));

        JMenuItem editItem = new JMenuItem("✏️ Modifier");
        editItem.setBackground(CARD_BG);
        editItem.setForeground(TEXT_PRIMARY);
        editItem.addActionListener(ev -> openUpdateForm(studentId));

        JMenuItem deleteItem = new JMenuItem("🗑️ Supprimer");
        deleteItem.setBackground(CARD_BG);
        deleteItem.setForeground(DANGER_COLOR);
        deleteItem.addActionListener(ev -> deleteStudent(studentId));

        JMenuItem viewItem = new JMenuItem("👁️ Voir détails");
        viewItem.setBackground(CARD_BG);
        viewItem.setForeground(TEXT_PRIMARY);
        viewItem.addActionListener(ev -> openStudentDetails(studentId));

        popup.add(viewItem);
        popup.add(editItem);
        popup.addSeparator();
        popup.add(deleteItem);

        popup.show(e.getComponent(), e.getX(), e.getY());
    }

    private JButton createModernButton(String text, Color color) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2d.setColor(color.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(color.brighter());
                } else {
                    g2d.setColor(color);
                }

                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                FontMetrics fm = g2d.getFontMetrics();
                Rectangle stringBounds = fm.getStringBounds(getText(), g2d).getBounds();
                int textX = (getWidth() - stringBounds.width) / 2;
                int textY = (getHeight() - stringBounds.height) / 2 + fm.getAscent();

                g2d.setColor(Color.WHITE);
                g2d.drawString(getText(), textX, textY);
            }
        };

        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(180, 45));
        button.setMaximumSize(new Dimension(180, 45));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return button;
    }


    private void exportData() {
        JOptionPane.showMessageDialog(this,
                "Fonctionnalité d'export en cours de développement...",
                "Export", JOptionPane.INFORMATION_MESSAGE);
    }

    // Méthodes existantes conservées
    public void loadStudents() {
        tableModel.setRowCount(0);
        List<String[]> students = MyJDBC.getAllStudents();
        for (String[] s : students) {
            tableModel.addRow(new Object[]{
                    Integer.parseInt(s[0]), s[1], s[2], s[3], s[4], "•••"
            });
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
                "Voulez-vous vraiment supprimer cet étudiant ?\nCette action est irréversible.",
                "⚠️ Confirmer la suppression",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (MyJDBC.deleteStudent(id)) {
                JOptionPane.showMessageDialog(this,
                        "✅ Étudiant supprimé avec succès !",
                        "Succès", JOptionPane.INFORMATION_MESSAGE);
                loadStudents();
            } else {
                JOptionPane.showMessageDialog(this,
                        "❌ Erreur lors de la suppression.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openStudentDetails(int id) {
        String[] studentData = MyJDBC.getStudentById(id);
        if (studentData != null) {
            String details = String.format(
                    "<html><div style='font-family: Segoe UI; padding: 20px;'>" +
                            "<h2 style='color: #00ADB5; margin-bottom: 15px;'>👨‍🎓 Détails de l'étudiant</h2>" +
                            "<table style='border-spacing: 10px;'>" +
                            "<tr><td><b>ID:</b></td><td>%d</td></tr>" +
                            "<tr><td><b>Nom:</b></td><td>%s</td></tr>" +
                            "<tr><td><b>Prénom:</b></td><td>%s</td></tr>" +
                            "<tr><td><b>Email:</b></td><td>%s</td></tr>" +
                            "<tr><td><b>Filière:</b></td><td>%s</td></tr>" +
                            "</table></div></html>",
                    id, studentData[0], studentData[1], studentData[2], studentData[3]);

            JOptionPane.showMessageDialog(this, details,
                    "Détails Étudiant", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "❌ Erreur lors de la récupération des détails.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}