package guis;

import constants.CommonConstants;
import db.MyJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterForm extends Form {
    private JTextField usernameField;
    private JPasswordField passwordField, rePasswordField;

    // Constructeur : initialise la fenêtre d'inscription, définit la mise en page et les couleurs
    public RegisterForm() {
        super("Inscription");

        // Utilisation de GridBagLayout pour remplir tout l’écran
        setLayout(new GridBagLayout());
        getContentPane().setBackground(CommonConstants.PRIMARY_COLOR);

        // Ajout des composants graphiques
        addGuiComponents();
    }

    // Ajoute et configure tous les composants graphiques (logo, champs, boutons, liens, etc.)
    private void addGuiComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ====== Logo / titre ======
        JLabel logoLabel = new JLabel("e-Ecole", JLabel.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 40));
        logoLabel.setForeground(CommonConstants.TEXT_COLOR);
        gbc.gridy = 0;
        add(logoLabel, gbc);

        // ====== Champ Username ======
        usernameField = createStyledTextField("Nom d'utilisateur");
        gbc.gridy = 2;
        add(usernameField, gbc);

        // ====== Champ Password ======
        passwordField = createStyledPasswordField("Mot de passe");
        gbc.gridy = 3;
        add(passwordField, gbc);

        // ====== Champ Confirmation Password ======
        rePasswordField = createStyledPasswordField("Confirmer le mot de passe");
        gbc.gridy = 4;
        add(rePasswordField, gbc);

        // ====== Bouton Inscription ======
        JButton registerButton = new JButton("S'inscrire");
        registerButton.setFont(new Font("Arial", Font.BOLD, 18));
        registerButton.setBackground(CommonConstants.TEXT_COLOR);
        registerButton.setForeground(CommonConstants.PRIMARY_COLOR);
        registerButton.setFocusPainted(false);
        registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerButton.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        // Effet hover sur le bouton
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                registerButton.setBackground(Color.WHITE);
                registerButton.setForeground(CommonConstants.SECONDARY_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerButton.setBackground(CommonConstants.TEXT_COLOR);
                registerButton.setForeground(CommonConstants.PRIMARY_COLOR);
            }
        });

        // Action d'inscription quand on clique sur le bouton
        registerButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String rePassword = new String(rePasswordField.getPassword()).trim();

            // Validation des entrées utilisateur
            if (!validateUserInput(username, password, rePassword)) return;

            // Enregistrement via MyJDBC
            if (MyJDBC.register(username, password)) {
                RegisterForm.this.dispose();
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
                JOptionPane.showMessageDialog(loginForm,
                        "Compte créé avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(RegisterForm.this,
                        "Erreur : le nom d'utilisateur est déjà pris.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        add(registerButton, gbc);

        // ====== Lien vers Login ======
        JLabel loginLabel = new JLabel("<html><u>Déjà un compte ? Connectez-vous</u></html>", JLabel.CENTER);
        loginLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        loginLabel.setForeground(Color.LIGHT_GRAY);
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Action : ouvrir la fenêtre Login au clic
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegisterForm.this.dispose();
                new LoginForm().setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                loginLabel.setForeground(CommonConstants.TEXT_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginLabel.setForeground(Color.LIGHT_GRAY);
            }
        });

        gbc.gridy = 6;
        add(loginLabel, gbc);

        // ====== Configuration de la fenêtre ======
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Crée un champ de texte stylisé avec placeholder et gestion du focus
    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField(placeholder, 20);
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setBackground(new Color(40, 50, 70));
        field.setForeground(Color.GRAY);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Gestion du placeholder (effacement quand focus, remise si vide au blur)
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

    // Crée un champ mot de passe stylisé avec placeholder et gestion du focus
    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField(placeholder, 20);
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setBackground(new Color(40, 50, 70));
        field.setForeground(Color.GRAY);
        field.setEchoChar((char) 0); // pas de masquage tant que placeholder
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Gestion du placeholder et masquage des caractères
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                String pass = new String(field.getPassword());
                if (pass.equals(placeholder)) {
                    field.setText("");
                    field.setEchoChar('•');
                }
                field.setForeground(CommonConstants.TEXT_COLOR);
                field.setBorder(BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 2, true));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(field.getPassword()).isEmpty()) {
                    field.setEchoChar((char) 0);
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
                field.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
            }
        });

        return field;
    }

    // Vérifie la validité des entrées utilisateur (champs remplis, longueur, correspondance des mots de passe)
    private boolean validateUserInput(String username, String password, String rePassword) {
        if (username.equals("Nom d'utilisateur") ||
                password.equals("Mot de passe") ||
                rePassword.equals("Confirmer le mot de passe")) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (username.length() < 6) {
            JOptionPane.showMessageDialog(this, "Le nom d'utilisateur doit contenir au moins 6 caractères.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!password.equals(rePassword)) {
            JOptionPane.showMessageDialog(this, "Les mots de passe ne correspondent pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (password.length() < 8) {
            JOptionPane.showMessageDialog(this, "Le mot de passe doit contenir au moins 8 caractères.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
