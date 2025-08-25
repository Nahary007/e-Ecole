package guis;

import constants.CommonConstants;
import db.MyJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddStudentForm extends JFrame {
    private StudentDashboard parent;
    private JTextField nomField, prenomField, emailField, filiereField;

    public AddStudentForm(StudentDashboard parent) {
        super("Ajouter Étudiant");
        this.parent = parent;
        setLayout(new GridBagLayout());
        getContentPane().setBackground(CommonConstants.SECONDARY_COLOR);
        addGuiComponents();
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void addGuiComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        // Titre
        JLabel titleLabel = new JLabel("Ajouter un Étudiant");
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
        nomField.setForeground(Color.GRAY);
        nomField.setText("Entrez le nom...");
        nomField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        nomField.setToolTipText("Nom de l'étudiant");
        nomField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nomField.getText().equals("Entrez le nom...")) {
                    nomField.setText("");
                    nomField.setForeground(CommonConstants.TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nomField.getText().isEmpty()) {
                    nomField.setText("Entrez le nom...");
                    nomField.setForeground(Color.GRAY);
                }
            }
        });
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
        prenomField.setForeground(Color.GRAY);
        prenomField.setText("Entrez le prénom...");
        prenomField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        prenomField.setToolTipText("Prénom de l'étudiant");
        prenomField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (prenomField.getText().equals("Entrez le prénom...")) {
                    prenomField.setText("");
                    prenomField.setForeground(CommonConstants.TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (prenomField.getText().isEmpty()) {
                    prenomField.setText("Entrez le prénom...");
                    prenomField.setForeground(Color.GRAY);
                }
            }
        });
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
        emailField.setForeground(Color.GRAY);
        emailField.setText("Entrez l'email...");
        emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        emailField.setToolTipText("Email de l'étudiant");
        emailField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (emailField.getText().equals("Entrez l'email...")) {
                    emailField.setText("");
                    emailField.setForeground(CommonConstants.TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setText("Entrez l'email...");
                    emailField.setForeground(Color.GRAY);
                }
            }
        });
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
        filiereField.setForeground(Color.GRAY);
        filiereField.setText("Entrez la filière...");
        filiereField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        filiereField.setToolTipText("Filière de l'étudiant");
        filiereField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (filiereField.getText().equals("Entrez la filière...")) {
                    filiereField.setText("");
                    filiereField.setForeground(CommonConstants.TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (filiereField.getText().isEmpty()) {
                    filiereField.setText("Entrez la filière...");
                    filiereField.setForeground(Color.GRAY);
                }
            }
        });
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
        saveButton.setToolTipText("Enregistrer l'étudiant");
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

    private void saveStudent() {
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String email = emailField.getText().trim();
        String filiere = filiereField.getText().trim();

        // Validation des entrées
        if (nom.equals("Entrez le nom...") || nom.isEmpty() ||
            prenom.equals("Entrez le prénom...") || prenom.isEmpty() ||
            email.equals("Entrez l'email...") || email.isEmpty() ||
            filiere.equals("Entrez la filière...") || filiere.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ajouter l'étudiant via MyJDBC
        if (MyJDBC.addStudent(nom, prenom, email, filiere)) {
            JOptionPane.showMessageDialog(this, "Étudiant ajouté avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            parent.loadStudents();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'étudiant.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}