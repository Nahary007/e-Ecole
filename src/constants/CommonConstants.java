package constants;

import java.awt.*;

/**
 * Classe contenant les constantes communes utilisées dans l'application.
 */
public class CommonConstants {
    // Couleur principale de l'interface (ex : arrière-plan)
    public static final Color PRIMARY_COLOR = Color.decode("#191E29");
    // Couleur secondaire de l'interface (ex : barres latérales)
    public static final Color SECONDARY_COLOR = Color.decode("#132D46");
    // Couleur du texte affiché dans l'interface
    public static final Color TEXT_COLOR = Color.decode("#01C38D");
    // Couleur blanche, utilisée pour les fonds ou textes
    public static final Color WHITE_COLOR = Color.decode("#FFFFFF");
    // Couleur noire, utilisée pour les fonds ou textes
    public static final Color BLACK_COLOR = Color.decode("#000000");

    // URL de connexion à la base de données MySQL
    public static final String DB_URL = "jdbc:mysql://localhost:3306/school_db";
    // Nom d'utilisateur pour la connexion à la base de données
    public static final String DB_USERNAME = "root";
    // Mot de passe pour la connexion à la base de données
    public static final String DB_PASSWORD = "";
    // Nom de la table des utilisateurs dans la base de données
    public static final String DB_USER_TABLE_NAME = "users";
}