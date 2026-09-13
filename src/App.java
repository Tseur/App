import javax.swing.*;

public class App extends JFrame {

    private Champ champ;
    private Gui gui;

    App() {
        // Initialisation du champ (grille)
        champ = new Champ();

        // Création de la GUI
        gui = new Gui(champ, this);

        // Affectation du JPanel dans le JFrame
        setContentPane(gui);

        // Paramètres de la fenêtre
        setTitle("Jeu de Démineur");
        setSize(500, 500);  // Taille de la fenêtre
        setLocationRelativeTo(null);  // Centrer la fenêtre
        setDefaultCloseOperation(EXIT_ON_CLOSE);  // Ferme l'application à la fermeture de la fenêtre
        setVisible(true);  // Rendre la fenêtre visible
    }

    public static void main(String[] args) {
        // Exécution du programme
        new App();  
    }

    /**
     * Quitter l'application
     */
    public void quit() {
        System.exit(0);
    }

    public void newGame(int level) {
        champ.newGame(level);  // Initialise une nouvelle partie dans Champ
        gui.newGame(level);  // Met à jour l'interface graphique
    }
}
