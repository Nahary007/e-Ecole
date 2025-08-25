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
        super("Login");
        setLayout(new GridBagLayout());
        getContentPane().setBackground(CommonConstants.SECONDARY_COLOR);
        addGuiComponents();
    }

    private void addGuiComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Logo ou icône (optionnel, ici un texte stylisé pour simuler un logo)
        JLabel logoLabel = new JLabel("e-Ecole");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 36));
        logoLabel.setForeground(CommonConstants.TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(logoLabel, gbc);

        // Titre
        JLabel loginLabel = new JLabel("Connexion");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 28));
        loginLabel.setForeground(CommonConstants.TEXT_COLOR);
        gbc.gridy = 1;
        add(loginLabel, gbc);

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
        usernameField.setBackground(new Color(50, 50, 50)); // Fond légèrement plus sombre
        usernameField.setForeground(CommonConstants.TEXT_COLOR);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        usernameField.setText("Entrez votre nom d'utilisateur");
        usernameField.setForeground(Color.GRAY);
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
        passwordField.setForeground(CommonConstants.TEXT_COLOR);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        passwordField.setEchoChar((char) 0);
        passwordField.setText("Entrez votre mot de passe");
        passwordField.setForeground(Color.GRAY);
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

        // Bouton Login
        JButton loginButton = new JButton("Se connecter");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(CommonConstants.PRIMARY_COLOR);
        loginButton.setForeground(CommonConstants.TEXT_COLOR);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setBorder(BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 1, true));
        loginButton.setFocusPainted(false);
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(CommonConstants.TEXT_COLOR);
                loginButton.setForeground(CommonConstants.PRIMARY_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(CommonConstants.PRIMARY_COLOR);
                loginButton.setForeground(CommonConstants.TEXT_COLOR);
            }
        });
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            // Ignorer les placeholders
            if (username.equals("Entrez votre nom d'utilisateur") || password.equals("Entrez votre mot de passe")) {
                JOptionPane.showMessageDialog(LoginForm.this, "Veuillez entrer un nom d'utilisateur et un mot de passe valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (MyJDBC.validateLogin(username, password)) {
                LoginForm.this.dispose();
                new StudentDashboard().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(LoginForm.this, "Échec de la connexion. Vérifiez vos identifiants.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

        // Lien Register
        JLabel registerLabel = new JLabel("<html><u>Pas de compte ? Inscrivez-vous ici</u></html>");
        registerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        registerLabel.setForeground(CommonConstants.TEXT_COLOR);
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginForm.this.dispose();
                new RegisterForm().setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                registerLabel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerLabel.setForeground(CommonConstants.TEXT_COLOR);
            }
        });
        gbc.gridy = 7;
        add(registerLabel, gbc);

        // Configurer la fenêtre
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}