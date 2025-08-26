package guis;

import constants.CommonConstants;
import db.MyJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UpdateStudentForm extends JFrame {
    private StudentDashboard parent;
    private int studentId;
    private JTextField nomField, prenomField, emailField, filiereField;

    public UpdateStudentForm(StudentDashboard parent, int studentId) {
        super("Modifier Étudiant");
        this.parent = parent;
        this.studentId = studentId;

        setLayout(new GridBagLayout());
        getContentPane().setBackground(CommonConstants.SECONDARY_COLOR);

        addGuiComponents();
        loadStudentData();

        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void addGuiComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ====== Titre ======
        JLabel titleLabel = new JLabel("Modifier un Étudiant", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(CommonConstants.TEXT_COLOR);
        gbc.gridy = 0;
        add(titleLabel, gbc);

        // ====== Champs stylisés ======
        nomField = createStyledTextField("Nom");
        gbc.gridy = 1;
        add(nomField, gbc);

        prenomField = createStyledTextField("Prénom");
        gbc.gridy = 2;
        add(prenomField, gbc);

        emailField = createStyledTextField("Email");
        gbc.gridy = 3;
        add(emailField, gbc);

        filiereField = createStyledTextField("Filière");
        gbc.gridy = 4;
        add(filiereField, gbc);

        // ====== Bouton Enregistrer ======
        JButton saveButton = new JButton("Enregistrer");
        saveButton.setFont(new Font("Arial", Font.BOLD, 18));
        saveButton.setBackground(CommonConstants.TEXT_COLOR);
        saveButton.setForeground(CommonConstants.PRIMARY_COLOR);
        saveButton.setFocusPainted(false);
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        saveButton.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                saveButton.setBackground(Color.WHITE);
                saveButton.setForeground(CommonConstants.SECONDARY_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                saveButton.setBackground(CommonConstants.TEXT_COLOR);
                saveButton.setForeground(CommonConstants.PRIMARY_COLOR);
            }
        });

        saveButton.addActionListener(e -> saveStudent());
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        add(saveButton, gbc);

        // ====== Bouton Annuler ======
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 18));
        cancelButton.setBackground(new Color(180, 50, 50));
        cancelButton.setForeground(CommonConstants.TEXT_COLOR);
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelButton.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cancelButton.setBackground(new Color(200, 70, 70));
                cancelButton.setForeground(CommonConstants.PRIMARY_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cancelButton.setBackground(new Color(180, 50, 50));
                cancelButton.setForeground(CommonConstants.TEXT_COLOR);
            }
        });

        cancelButton.addActionListener(e -> dispose());
        gbc.gridy = 6;
        add(cancelButton, gbc);
    }

    // ====== Champs stylisés avec placeholder ======
    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField(placeholder, 20);
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setBackground(new Color(40, 50, 70));
        field.setForeground(Color.GRAY);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                }
                field.setForeground(CommonConstants.TEXT_COLOR);
                field.setBorder(BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 2, true));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
                field.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
            }
        });

        return field;
    }

    // ====== Pré-remplissage des champs ======
    private void loadStudentData() {
        String[] studentData = MyJDBC.getStudentById(studentId);
        if (studentData != null) {
            nomField.setText(studentData[0]);
            prenomField.setText(studentData[1]);
            emailField.setText(studentData[2]);
            filiereField.setText(studentData[3]);
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données.", "Erreur", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    // ====== Validation et mise à jour ======
    private void saveStudent() {
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String email = emailField.getText().trim();
        String filiere = filiereField.getText().trim();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || filiere.isEmpty()
                || nom.equals("Nom") || prenom.equals("Prénom") || email.equals("Email") || filiere.equals("Filière")) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (MyJDBC.updateStudent(studentId, nom, prenom, email, filiere)) {
            JOptionPane.showMessageDialog(this, "Étudiant modifié avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            parent.loadStudents();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
