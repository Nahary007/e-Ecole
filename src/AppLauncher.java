import guis.LoginForm;
import guis.RegisterForm;
import db.MyJDBC;

import javax.swing.*;

/**
 * Classe principale pour lancer l'application.
 */
public class AppLauncher {
    public static void main(String[] args) {
        // (Optionnel) Tester la connexion à la base de données
        // MyJDBC.testConnection(); // Tester la connexion

        // Lance l'interface graphique dans le thread dédié à Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Affiche le formulaire de connexion
                new LoginForm().setVisible(true);
            }
        });
    }
}