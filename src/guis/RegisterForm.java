package guis;

import constants.CommonConstants;
import db.MyJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterForm extends Form {
    public RegisterForm() {
        super("Register");
        addGuiComponents();
    }

    private void addGuiComponents() {
        JLabel registerLabel = new JLabel("Register");
        registerLabel.setBounds(0, 25, 520, 100);
        registerLabel.setForeground(CommonConstants.TEXT_COLOR);
        registerLabel.setFont(new Font("Dialog", Font.BOLD, 40));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(registerLabel);

        JLabel usernameLabel = new JLabel("Username : ");
        usernameLabel.setBounds(30, 150, 400, 25);
        usernameLabel.setForeground(CommonConstants.TEXT_COLOR);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 18));

        JTextField usernameField = new JTextField();
        usernameField.setBounds(30, 175, 450, 55);
        usernameField.setBackground(CommonConstants.SECONDARY_COLOR);
        usernameField.setForeground(CommonConstants.TEXT_COLOR);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(usernameLabel);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password : ");
        passwordLabel.setBounds(30, 260, 400, 25);
        passwordLabel.setForeground(CommonConstants.TEXT_COLOR);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 18));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(30, 285, 450, 55);
        passwordField.setBackground(CommonConstants.SECONDARY_COLOR);
        passwordField.setForeground(CommonConstants.TEXT_COLOR);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(passwordLabel);
        add(passwordField);

        JLabel rePasswordLabel = new JLabel("Re-enter Password : ");
        rePasswordLabel.setBounds(30, 370, 400, 25);
        rePasswordLabel.setForeground(CommonConstants.TEXT_COLOR);
        rePasswordLabel.setFont(new Font("Dialog", Font.PLAIN, 18));

        JPasswordField rePasswordField = new JPasswordField();
        rePasswordField.setBounds(30, 395, 450, 55);
        rePasswordField.setBackground(CommonConstants.SECONDARY_COLOR);
        rePasswordField.setForeground(CommonConstants.TEXT_COLOR);
        rePasswordField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(rePasswordLabel);
        add(rePasswordField);

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Dialog", Font.BOLD, 18));
        registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerButton.setBackground(CommonConstants.TEXT_COLOR);
        registerButton.setBounds(125, 520, 250, 50);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String rePassword = new String(rePasswordField.getPassword());

                if (validateUserInput(username, password, rePassword)) {
                    if (MyJDBC.register(username, password)) {
                        RegisterForm.this.dispose();
                        LoginForm loginForm = new LoginForm();
                        loginForm.setVisible(true);
                        JOptionPane.showMessageDialog(loginForm, "Registered account successfully");
                    } else {
                        JOptionPane.showMessageDialog(RegisterForm.this, "Username already taken");
                    }
                } else {
                    JOptionPane.showMessageDialog(RegisterForm.this, "Error: Username must be at least 6 characters and passwords must match");
                }
            }
        });

        add(registerButton);

        JLabel loginLabel = new JLabel("Have account? Login Here");
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginLabel.setForeground(CommonConstants.TEXT_COLOR);

        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegisterForm.this.dispose();
                new LoginForm().setVisible(true);
            }
        });

        loginLabel.setBounds(125, 600, 250, 30);
        add(loginLabel);
    }

    private boolean validateUserInput(String username, String password, String rePassword) {
        if (username.isEmpty() || password.isEmpty() || rePassword.isEmpty()) return false;
        if (username.length() < 6) return false;
        if (!password.equals(rePassword)) return false;
        return true;
    }
}
