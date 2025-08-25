package guis;

import constants.CommonConstants;
import db.MyJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterForm extends Form {
    private JTextField usernameField;
    private JPasswordField passwordField, rePasswordField;

    public RegisterForm() {
        super("Inscription");
        setLayout(new GridBagLayout());
        getContentPane().setBackground(CommonConstants.SECONDARY_COLOR);
        addGuiComponents();
    }

    private void addGuiComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Logo ou titre stylisé
        JLabel logoLabel = new JLabel("e-Ecole");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 36));
        logoLabel.setForeground(CommonConstants.TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(logoLabel, gbc);

        // Titre
        JLabel registerLabel = new JLabel("Inscription");
        registerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        registerLabel.setForeground(CommonConstants.TEXT_COLOR);
        gbc.gridy = 1;
        add(registerLabel, gbc);

        // Champ Username
        JLabel usernameLabel = new JLabel("Nom d'utilisateur :");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameLabel.setForeground(CommonConstants.TEXT_COLOR);
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setBackground(new Color(50, 50, 50));
        usernameField.setForeground(Color.GRAY);
        usernameField.setText("Entrez votre nom d'utilisateur");
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        usernameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("Entrez votre nom d'utilisateur")) {
                    usernameField.setText("");
                    usernameField.setForeground(CommonConstants.TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setText("Entrez votre nom d'utilisateur");
                    usernameField.setForeground(Color.GRAY);
                }
            }
        });
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(usernameField, gbc);

        // Champ Password
        JLabel passwordLabel = new JLabel("Mot de passe :");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setForeground(CommonConstants.TEXT_COLOR);
        gbc.gridy = 4;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBackground(new Color(50, 50, 50));
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0);
        passwordField.setText("Entrez votre mot de passe");
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(passwordField.getPassword()).equals("Entrez votre mot de passe")) {
                    passwordField.setText("");
                    passwordField.setEchoChar('•');
                    passwordField.setForeground(CommonConstants.TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setEchoChar((char) 0);
                    passwordField.setText("Entrez votre mot de passe");
                    passwordField.setForeground(Color.GRAY);
                }
            }
        });
        gbc.gridy = 5;
        add(passwordField, gbc);

        // Champ Re-enter Password
        JLabel rePasswordLabel = new JLabel("Confirmer mot de passe :");
        rePasswordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        rePasswordLabel.setForeground(CommonConstants.TEXT_COLOR);
        gbc.gridy = 6;
        add(rePasswordLabel, gbc);

        rePasswordField = new JPasswordField(20);
        rePasswordField.setFont(new Font("Arial", Font.PLAIN, 16));
        rePasswordField.setBackground(new Color(50, 50, 50));
        rePasswordField.setForeground(Color.GRAY);
        rePasswordField.setEchoChar((char) 0);
        rePasswordField.setText("Confirmez votre mot de passe");
        rePasswordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        rePasswordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(rePasswordField.getPassword()).equals("Confirmez votre mot de passe")) {
                    rePasswordField.setText("");
                    rePasswordField.setEchoChar('•');
                    rePasswordField.setForeground(CommonConstants.TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(rePasswordField.getPassword()).isEmpty()) {
                    rePasswordField.setEchoChar((char) 0);
                    rePasswordField.setText("Confirmez votre mot de passe");
                    rePasswordField.setForeground(Color.GRAY);
                }
            }
        });
        gbc.gridy = 7;
        add(rePasswordField, gbc);

        // Bouton Register
        JButton registerButton = new JButton("S'inscrire");
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setBackground(CommonConstants.PRIMARY_COLOR);
        registerButton.setForeground(CommonConstants.TEXT_COLOR);
        registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerButton.setBorder(BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 1, true));
        registerButton.setFocusPainted(false);
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                registerButton.setBackground(CommonConstants.TEXT_COLOR);
                registerButton.setForeground(CommonConstants.PRIMARY_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerButton.setBackground(CommonConstants.PRIMARY_COLOR);
                registerButton.setForeground(CommonConstants.TEXT_COLOR);
            }
        });
        registerButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String rePassword = new String(rePasswordField.getPassword()).trim();

            if (!validateUserInput(username, password, rePassword)) {
                return;
            }

            if (MyJDBC.register(username, password)) {
                RegisterForm.this.dispose();
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
                JOptionPane.showMessageDialog(loginForm, "Compte créé avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(RegisterForm.this, "Erreur : le nom d'utilisateur est déjà pris.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(registerButton, gbc);

        // Lien Login
        JLabel loginLabel = new JLabel("<html><u>Déjà un compte ? Connectez-vous ici</u></html>");
        loginLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        loginLabel.setForeground(CommonConstants.TEXT_COLOR);
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegisterForm.this.dispose();
                new LoginForm().setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                loginLabel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginLabel.setForeground(CommonConstants.TEXT_COLOR);
            }
        });
        gbc.gridy = 9;
        add(loginLabel, gbc);

        // Configurer la fenêtre
        setSize(500, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private boolean validateUserInput(String username, String password, String rePassword) {
        // Ignorer les placeholders
        if (username.equals("Entrez votre nom d'utilisateur") || 
            password.equals("Entrez votre mot de passe") || 
            rePassword.equals("Confirmez votre mot de passe")) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Vérification de la longueur du nom d'utilisateur
        if (username.length() < 6) {
            JOptionPane.showMessageDialog(this, "Le nom d'utilisateur doit contenir au moins 6 caractères.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Vérification de la correspondance des mots de passe
        if (!password.equals(rePassword)) {
            JOptionPane.showMessageDialog(this, "Les mots de passe ne correspondent pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Vérification de la longueur du mot de passe
        if (password.length() < 8) {
            JOptionPane.showMessageDialog(this, "Le mot de passe doit contenir au moins 8 caractères.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}