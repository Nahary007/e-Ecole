package guis;

import constants.CommonConstants;

import javax.swing.*;

/**
 * Fenêtre principale du formulaire, héritée de JFrame.
 */
public class Form extends JFrame {
    /**
     * Constructeur qui initialise la fenêtre avec les paramètres de base.
     * @param title titre affiché dans la barre de titre
     */
    public Form(String title) {

        // Définit le titre de la fenêtre
        super(title);

        // Définit la taille de la fenêtre
        setSize(520, 680);

        // Ferme l'application lorsque la fenêtre est fermée
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Utilise un layout nul pour placer les composants manuellement
        setLayout(null);

        // Centre la fenêtre sur l'écran
        setLocationRelativeTo(null);

        // Empêche le redimensionnement de la fenêtre
        setResizable(false);

        // Définit la couleur de fond de la fenêtre
        getContentPane().setBackground(CommonConstants.PRIMARY_COLOR);
    }
}