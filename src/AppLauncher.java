import guis.LoginForm;
import guis.RegisterForm;
import db.MyJDBC;

import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {
        // MyJDBC.testConnection(); // Tester la connexion
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }
}