import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Graphical user Interface
 */
public class Gui extends JPanel implements ActionListener {

    private JLabel scoreLabel;
    private JLabel levelLabel;
    private JButton butQuit;
    private JButton newGameButton;
    private JComboBox<Level> difficultyComboBox;
    private App app;
    private Champ champ;
    private JPanel panelMines = new JPanel();
    private JMenuBar menuBar;
    private JMenu menuPartie;
    private Case[][] tabCase;
    
    Gui(Champ champ, App app) {

        // Création de la barre de menu

        createMenuBar();

        // Création des labels pour le score et le niveau
        scoreLabel = new JLabel("Score: 0");
        levelLabel = new JLabel("Niveau: Easy");
        this.app = app;
        this.champ = champ;

        // Utilisation de l'enum pour la JComboBox
        difficultyComboBox = new JComboBox<>(Level.values());
        difficultyComboBox.setSelectedIndex(0);
        difficultyComboBox.addActionListener(this);

        // Création des boutons
        newGameButton = new JButton("Nouvelle partie");
        newGameButton.addActionListener(this);

        butQuit = new JButton("Quit");
        butQuit.addActionListener(this);

        // Création du panel pour le score et le niveau (en haut)
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(1, 3));
        infoPanel.add(scoreLabel);
        infoPanel.add(levelLabel);
        infoPanel.add(difficultyComboBox);

        // Création du panel pour les boutons (en bas)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(newGameButton);
        buttonPanel.add(butQuit);

        // Création du panel pour les mines (au centre)
        majPanelMines();

        // Ajout des composants au panel principal
        setLayout(new BorderLayout());
        add(infoPanel, BorderLayout.NORTH);
        add(panelMines, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Création de la barre de menu avec les options "Nouvelle partie" et "Quitter"
    private void createMenuBar() {
        menuBar = new JMenuBar();

        // Création du menu "File"
        menuPartie = new JMenu("Menu");

        // Création de l'option "New Game"
        JMenuItem newGameMenuItem = new JMenuItem("Nouvelle partie");
        newGameMenuItem.addActionListener(e -> {
            System.out.println("Nouvelle partie lancée via le menu !");
            app.newGame(difficultyComboBox.getSelectedIndex());
        });

        // Création de l'option "Quit"
        JMenuItem quitMenuItem = new JMenuItem("Quitter");
        quitMenuItem.addActionListener(e -> {
            System.out.println("Quitter le jeu via le menu !");
            app.quit();
        });

        // Ajout des items au menu "File"
        menuPartie.add(newGameMenuItem);
        menuPartie.addSeparator(); // Séparateur visuel entre les options
        menuPartie.add(quitMenuItem);

        // Ajout du menu à la barre de menu
        menuBar.add(menuPartie);

        // Ajout de la barre de menu à l'interface principale
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this); // Récupérer le JFrame parent
        if (frame != null) {
            frame.setJMenuBar(menuBar); // Ajouter la barre de menu au frame
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            System.out.println("Nouvelle partie lancée !");
            levelLabel.setText("Niveau: " + difficultyComboBox.getSelectedIndex());
            app.newGame(difficultyComboBox.getSelectedIndex());
        } else if (e.getSource() == butQuit) {
            app.quit();
            System.out.println("Quitter le jeu");
        }
    }

    public void newGame(int level) {
        scoreLabel.setText("Score: 0");
        panelMines.removeAll(); // Efface les anciennes cases
        majPanelMines(); // Réinitialise la grille
        app.pack(); // Ajuste la taille de la fenêtre
    }

    public void majPanelMines() {
        champ.init(1, 0); // Réinitialisation de la grille de mines
        panelMines.setLayout(new GridLayout(champ.getWidth(), champ.getHight()));
        tabCase = new Case[champ.getWidth()][champ.getHight()];

        for (int i = 0; i < champ.getWidth(); i++) {
            for (int j = 0; j < champ.getHight(); j++) {
                // Création de chaque case avec les coordonnées (i, j)
                tabCase[i][j] = new Case(champ.isMines(i, j), champ.nbMinesAround(i, j), i, j, this);
                panelMines.add(tabCase[i][j]);
            }
        }
    }

    public void propagation(int x, int y) {
        // Logique pour propager la révélation des cases adjacentes à (x, y)
        // Vérifier les cases autour de la case (x, y)
        for (int i = Math.max(0, x - 1); i <= Math.min(x + 1, champ.getWidth() - 1); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(y + 1, champ.getHight() - 1); j++) {
                // Si la case n'est pas encore révélée et qu'il n'y a pas de mine autour, révéler et propager
                Case c = (Case) panelMines.getComponent(i * champ.getHight() + j);
                if (!c.isRevealed() && champ.nbMinesAround(i, j) == 0) {
                    c.reveal();
                    propagation(i, j); // Appel récursif pour révéler les voisins
                }
                else if(!c.isRevealed() && champ.nbMinesAround(i, j) > 0) {
                    c.reveal();
                }
            }
        }
    }
}
