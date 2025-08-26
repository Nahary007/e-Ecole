package guis;

import constants.CommonConstants;
import db.MyJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Classe AddStudentForm - Fenêtre de formulaire pour ajouter un nouvel étudiant
 * Hérite de JFrame pour créer une interface graphique modale
 */
public class AddStudentForm extends JFrame {
    // Référence vers la fenêtre parent pour pouvoir actualiser la liste après ajout
    private StudentDashboard parent;

    // Champs de saisie pour les informations de l'étudiant
    private JTextField nomField, prenomField, emailField, filiereField;

    /**
     * Constructeur - Initialise la fenêtre de formulaire
     * @param parent Référence vers StudentDashboard pour actualiser la liste après ajout
     */
    public AddStudentForm(StudentDashboard parent) {
        // Appel du constructeur parent avec le titre de la fenêtre
        super("Ajouter Étudiant");
        this.parent = parent;

        // Configuration du gestionnaire de mise en page GridBagLayout pour un placement flexible
        setLayout(new GridBagLayout());
        // Application de la couleur de fond définie dans les constantes
        getContentPane().setBackground(CommonConstants.SECONDARY_COLOR);

        // Appel de la méthode pour créer tous les composants graphiques
        addGuiComponents();

        // Configuration de la fenêtre
        setSize(500, 600);                          // Taille de la fenêtre
        setLocationRelativeTo(null);                // Centrer la fenêtre à l'écran
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer seulement cette fenêtre
        setVisible(true);                           // Afficher la fenêtre
    }

    /**
     * Méthode pour créer et positionner tous les composants graphiques du formulaire
     */
    private void addGuiComponents() {
        // Configuration des contraintes GridBag pour le positionnement des composants
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);    // Marges autour de chaque composant
        gbc.gridx = 0;                              // Colonne 0 (tous les composants alignés)
        gbc.fill = GridBagConstraints.HORIZONTAL;   // Étirer horizontalement

        // ====== TITRE DE LA FENÊTRE ======
        JLabel titleLabel = new JLabel("Ajouter un Étudiant", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));        // Police grande et gras
        titleLabel.setForeground(CommonConstants.TEXT_COLOR);        // Couleur du texte
        gbc.gridy = 0;  // Position ligne 0
        add(titleLabel, gbc);

        // ====== CHAMP DE SAISIE NOM ======
        nomField = createStyledTextField("Nom");    // Création avec placeholder "Nom"
        gbc.gridy = 1;  // Position ligne 1
        add(nomField, gbc);

        // ====== CHAMP DE SAISIE PRÉNOM ======
        prenomField = createStyledTextField("Prénom"); // Création avec placeholder "Prénom"
        gbc.gridy = 2;  // Position ligne 2
        add(prenomField, gbc);

        // ====== CHAMP DE SAISIE EMAIL ======
        emailField = createStyledTextField("Email");   // Création avec placeholder "Email"
        gbc.gridy = 3;  // Position ligne 3
        add(emailField, gbc);

        // ====== CHAMP DE SAISIE FILIÈRE ======
        filiereField = createStyledTextField("Filière"); // Création avec placeholder "Filière"
        gbc.gridy = 4;  // Position ligne 4
        add(filiereField, gbc);

        // ====== BOUTON ENREGISTRER ======
        JButton saveButton = new JButton("Enregistrer");
        // Style du bouton
        saveButton.setFont(new Font("Arial", Font.BOLD, 18));        // Police du bouton
        saveButton.setBackground(CommonConstants.TEXT_COLOR);        // Couleur de fond
        saveButton.setForeground(CommonConstants.PRIMARY_COLOR);     // Couleur du texte
        saveButton.setFocusPainted(false);                          // Supprimer le contour de focus
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Curseur main au survol
        saveButton.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20)); // Marges internes

        // Gestionnaire d'événements pour les effets de survol du bouton Enregistrer
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Changement de couleur au survol de la souris
                saveButton.setBackground(Color.WHITE);
                saveButton.setForeground(CommonConstants.SECONDARY_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Retour aux couleurs normales quand la souris quitte le bouton
                saveButton.setBackground(CommonConstants.TEXT_COLOR);
                saveButton.setForeground(CommonConstants.PRIMARY_COLOR);
            }
        });

        // Action à effectuer lors du clic sur Enregistrer
        saveButton.addActionListener(e -> saveStudent());
        gbc.gridy = 5;  // Position ligne 5
        gbc.anchor = GridBagConstraints.CENTER;     // Centrer le bouton
        add(saveButton, gbc);

        // ====== BOUTON ANNULER ======
        JButton cancelButton = new JButton("Annuler");
        // Style du bouton Annuler (couleurs rougeâtres)
        cancelButton.setFont(new Font("Arial", Font.BOLD, 18));
        cancelButton.setBackground(new Color(180, 50, 50));         // Rouge foncé
        cancelButton.setForeground(CommonConstants.TEXT_COLOR);     // Texte blanc
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelButton.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        // Gestionnaire d'événements pour les effets de survol du bouton Annuler
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Rouge plus clair au survol
                cancelButton.setBackground(new Color(200, 70, 70));
                cancelButton.setForeground(CommonConstants.PRIMARY_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Retour au rouge foncé
                cancelButton.setBackground(new Color(180, 50, 50));
                cancelButton.setForeground(CommonConstants.TEXT_COLOR);
            }
        });

        // Action à effectuer lors du clic sur Annuler : fermer la fenêtre
        cancelButton.addActionListener(e -> dispose());
        gbc.gridy = 6;  // Position ligne 6
        add(cancelButton, gbc);
    }

    /**
     * Méthode utilitaire pour créer des champs de texte stylisés avec placeholder
     * @param placeholder Texte d'indication affiché dans le champ vide
     * @return JTextField configuré avec le style et les comportements souhaités
     */
    private JTextField createStyledTextField(String placeholder) {
        // Création du champ avec le placeholder et largeur de 20 colonnes
        JTextField field = new JTextField(placeholder, 20);

        // Configuration du style visuel
        field.setFont(new Font("Arial", Font.PLAIN, 16));           // Police du texte
        field.setBackground(new Color(40, 50, 70));                 // Fond sombre
        field.setForeground(Color.GRAY);                            // Texte gris pour le placeholder

        // Bordure composée : ligne grise + marges internes
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),        // Bordure externe
                BorderFactory.createEmptyBorder(10, 10, 10, 10)             // Marges internes
        ));

        // Gestionnaire d'événements pour la gestion du focus (clic dans/hors du champ)
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Quand l'utilisateur clique dans le champ
                if (field.getText().equals(placeholder)) {
                    field.setText("");  // Effacer le placeholder
                }
                // Changer l'apparence pour indiquer que le champ est actif
                field.setForeground(CommonConstants.TEXT_COLOR);    // Texte blanc
                field.setBorder(BorderFactory.createLineBorder(CommonConstants.TEXT_COLOR, 2, true)); // Bordure colorée
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Quand l'utilisateur clique ailleurs (perd le focus)
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);    // Remettre le placeholder si vide
                    field.setForeground(Color.GRAY); // Texte gris pour le placeholder
                }
                // Retour à la bordure normale
                field.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
            }
        });

        return field;
    }

    /**
     * Méthode pour valider les données saisies et enregistrer l'étudiant en base de données
     */
    private void saveStudent() {
        // Récupération et nettoyage des données saisies (suppression des espaces)
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String email = emailField.getText().trim();
        String filiere = filiereField.getText().trim();

        // Validation : vérifier que tous les champs sont remplis
        // (ne pas accepter les placeholders ou les champs vides)
        if (nom.equals("Nom") || nom.isEmpty() ||
                prenom.equals("Prénom") || prenom.isEmpty() ||
                email.equals("Email") || email.isEmpty() ||
                filiere.equals("Filière") || filiere.isEmpty()) {

            // Afficher un message d'erreur si des champs sont manquants
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return; // Arrêter l'exécution
        }

        // Tentative d'ajout en base de données via la classe MyJDBC
        if (MyJDBC.addStudent(nom, prenom, email, filiere)) {
            // Succès : informer l'utilisateur
            JOptionPane.showMessageDialog(this,
                    "Étudiant ajouté avec succès !",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);

            // Actualiser la liste des étudiants dans la fenêtre parent
            parent.loadStudents();

            // Fermer cette fenêtre de formulaire
            dispose();
        } else {
            // Échec : informer l'utilisateur de l'erreur
            JOptionPane.showMessageDialog(this,
                    "Erreur lors de l'ajout de l'étudiant.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}