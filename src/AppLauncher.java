import guis.LoginForm;
import guis.RegisterForm;
import db.MyJDBC;

import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // instantiate a loginForm obj and make it visible
                new LoginForm().setVisible(true);
            }
        });
    }
}
