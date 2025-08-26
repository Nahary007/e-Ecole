package guis;

import constants.CommonConstants;
import db.MyJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginForm extends Form {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginForm() {
        super("Connexion");

        setLayout(new GridBagLayout());
        getContentPane().setBackground(CommonConstants.SECONDARY_COLOR);

        addGuiComponents();
    }

    private void addGuiComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ====== Logo / Titre ======
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

        // ====== Bouton Connexion ======
        JButton loginButton = new JButton("Se connecter");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBackground(CommonConstants.TEXT_COLOR);
        loginButton.setForeground(CommonConstants.PRIMARY_COLOR);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        // Effet hover
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(Color.WHITE);
                loginButton.setForeground(CommonConstants.SECONDARY_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(CommonConstants.TEXT_COLOR);
                loginButton.setForeground(CommonConstants.PRIMARY_COLOR);
            }
        });

        // Action login
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.equals("Nom d'utilisateur") || password.equals("Mot de passe")) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez entrer un nom d'utilisateur et un mot de passe valides.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (MyJDBC.validateLogin(username, password)) {
                LoginForm.this.dispose();
                new StudentDashboard().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Échec de la connexion. Vérifiez vos identifiants.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

        // ====== Lien Inscription ======
        JLabel registerLabel = new JLabel("<html><u>Pas de compte ? Inscrivez-vous ici</u></html>", JLabel.CENTER);
        registerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        registerLabel.setForeground(Color.LIGHT_GRAY);
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginForm.this.dispose();
                new RegisterForm().setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                registerLabel.setForeground(CommonConstants.TEXT_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerLabel.setForeground(Color.LIGHT_GRAY);
            }
        });

        gbc.gridy = 5;
        add(registerLabel, gbc);

        // ====== Config fenêtre ======
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // ====== Champs stylisés ======
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

    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField(placeholder, 20);
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setBackground(new Color(40, 50, 70));
        field.setForeground(Color.GRAY);
        field.setEchoChar((char) 0);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

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
}
