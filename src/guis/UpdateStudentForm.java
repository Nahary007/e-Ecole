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
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void addGuiComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        // Titre
        JLabel titleLabel = new JLabel("Modifier un Étudiant");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(CommonConstants.TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // Champ Nom
        JLabel nomLabel = new JLabel("Nom :");
        nomLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        nomLabel.setForeground(CommonConstants.TEXT_COLOR);
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(nomLabel, gbc);

        nomField = new JTextField(15);
        nomField.setFont(new Font("Arial", Font.PLAIN, 14));
        nomField.setBackground(new Color(60, 60, 60));
        nomField.setForeground(CommonConstants.TEXT_COLOR);
        nomField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        nomField.setToolTipText("Nom de l'étudiant");
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(nomField, gbc);

        // Champ Prénom
        JLabel prenomLabel = new JLabel("Prénom :");
        prenomLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        prenomLabel.setForeground(CommonConstants.TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        add(prenomLabel, gbc);

        prenomField = new JTextField(15);
        prenomField.setFont(new Font("Arial", Font.PLAIN, 14));
        prenomField.setBackground(new Color(60, 60, 60));
        prenomField.setForeground(CommonConstants.TEXT_COLOR);
        prenomField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        prenomField.setToolTipText("Prénom de l'étudiant");
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(prenomField, gbc);

        // Champ Email
        JLabel emailLabel = new JLabel("Email :");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        emailLabel.setForeground(CommonConstants.TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        add(emailLabel, gbc);

        emailField = new JTextField(15);
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setBackground(new Color(60, 60, 60));
        emailField.setForeground(CommonConstants.TEXT_COLOR);
        emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        emailField.setToolTipText("Email de l'étudiant");
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(emailField, gbc);

        // Champ Filière
        JLabel filiereLabel = new JLabel("Filière :");
        filiereLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        filiereLabel.setForeground(CommonConstants.TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        add(filiereLabel, gbc);

        filiereField = new JTextField(15);
        filiereField.setFont(new Font("Arial", Font.PLAIN, 14));
        filiereField.setBackground(new Color(60, 60, 60));
        filiereField.setForeground(CommonConstants.TEXT_COLOR);
        filiereField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        filiereField.setToolTipText("Filière de l'étudiant");
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(filiereField, gbc);

        // Bouton Enregistrer
        JButton saveButton = new JButton("Enregistrer");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setBackground(CommonConstants.PRIMARY_COLOR);
        saveButton.setForeground(CommonConstants.TEXT_COLOR);
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        saveButton.setBorder(BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 1, true));
        saveButton.setFocusPainted(false);
        saveButton.setToolTipText("Enregistrer les modifications");
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                saveButton.setBackground(CommonConstants.TEXT_COLOR);
                saveButton.setForeground(CommonConstants.PRIMARY_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                saveButton.setBackground(CommonConstants.PRIMARY_COLOR);
                saveButton.setForeground(CommonConstants.TEXT_COLOR);
            }
        });
        saveButton.addActionListener(e -> saveStudent());
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(saveButton, gbc);

        // Bouton Annuler
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(new Color(180, 50, 50));
        cancelButton.setForeground(CommonConstants.TEXT_COLOR);
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelButton.setBorder(BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 1, true));
        cancelButton.setFocusPainted(false);
        cancelButton.setToolTipText("Fermer sans enregistrer");
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
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(cancelButton, gbc);
    }

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

    private void saveStudent() {
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String email = emailField.getText().trim();
        String filiere = filiereField.getText().trim();

        // Validation des entrées
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || filiere.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mettre à jour l'étudiant via MyJDBC
        if (MyJDBC.updateStudent(studentId, nom, prenom, email, filiere)) {
            JOptionPane.showMessageDialog(this, "Étudiant modifié avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            parent.loadStudents();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}