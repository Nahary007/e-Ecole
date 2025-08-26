package guis;

import constants.CommonConstants;
import db.MyJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Fenêtre permettant de modifier les informations d'un étudiant.
 */
public class UpdateStudentForm extends JFrame {
    // Référence au tableau parent pour rafraîchir la liste après modification
    private StudentDashboard parent;
    // Identifiant de l'étudiant à modifier
    private int studentId;
    // Champs de saisie pour les informations de l'étudiant
    private JTextField nomField, prenomField, emailField, filiereField;

    /**
     * Constructeur : initialise la fenêtre et charge les données de l'étudiant.
     * @param parent tableau parent pour le rafraîchissement
     * @param studentId identifiant de l'étudiant à modifier
     */
    public UpdateStudentForm(StudentDashboard parent, int studentId) {
        super("Modifier Étudiant");
        this.parent = parent;
        this.studentId = studentId;

        // Utilise un layout en grille pour organiser les composants
        setLayout(new GridBagLayout());
        // Définit la couleur de fond
        getContentPane().setBackground(CommonConstants.SECONDARY_COLOR);

        // Ajoute les composants graphiques
        addGuiComponents();
        // Pré-remplit les champs avec les données de l'étudiant
        loadStudentData();

        // Paramètres de la fenêtre
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Ajoute et configure les composants graphiques du formulaire.
     */
    private void addGuiComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titre du formulaire
        JLabel titleLabel = new JLabel("Modifier un Étudiant", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(CommonConstants.TEXT_COLOR);
        gbc.gridy = 0;
        add(titleLabel, gbc);

        // Champs de saisie stylisés
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

        // Bouton pour enregistrer les modifications
        JButton saveButton = new JButton("Enregistrer");
        saveButton.setFont(new Font("Arial", Font.BOLD, 18));
        saveButton.setBackground(CommonConstants.TEXT_COLOR);
        saveButton.setForeground(CommonConstants.PRIMARY_COLOR);
        saveButton.setFocusPainted(false);
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        saveButton.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        // Effets visuels au survol du bouton
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

        // Action : enregistrer l'étudiant
        saveButton.addActionListener(e -> saveStudent());
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        add(saveButton, gbc);

        // Bouton pour annuler et fermer la fenêtre
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 18));
        cancelButton.setBackground(new Color(180, 50, 50));
        cancelButton.setForeground(CommonConstants.TEXT_COLOR);
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelButton.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        // Effets visuels au survol du bouton annuler
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

        // Action : fermer la fenêtre
        cancelButton.addActionListener(e -> dispose());
        gbc.gridy = 6;
        add(cancelButton, gbc);
    }

    /**
     * Crée un champ de saisie stylisé avec un placeholder.
     * @param placeholder texte d'exemple affiché dans le champ
     * @return champ de saisie stylisé
     */
    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField(placeholder, 20);
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setBackground(new Color(40, 50, 70));
        field.setForeground(Color.GRAY);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Gestion du focus pour afficher/masquer le placeholder
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

    /**
     * Charge les données de l'étudiant et pré-remplit les champs.
     */
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

    /**
     * Valide les champs et met à jour l'étudiant dans la base de données.
     */
    private void saveStudent() {
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String email = emailField.getText().trim();
        String filiere = filiereField.getText().trim();

        // Vérifie que tous les champs sont remplis et ne contiennent pas le placeholder
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || filiere.isEmpty()
                || nom.equals("Nom") || prenom.equals("Prénom") || email.equals("Email") || filiere.equals("Filière")) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Met à jour l'étudiant et affiche le résultat
        if (MyJDBC.updateStudent(studentId, nom, prenom, email, filiere)) {
            JOptionPane.showMessageDialog(this, "Étudiant modifié avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            parent.loadStudents();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}