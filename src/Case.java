import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Case extends JPanel implements MouseListener {
    private boolean isRevealed = false; // Indique si la case est révélée
    private boolean hasMine; // Indique si la case contient une mine
    private int surroundingMines; // Nombre de mines autour de la case
    private int x, y; // Coordonnées de la case dans la grille
    private Color hiddenColor = Color.LIGHT_GRAY; // Couleur quand la case est cachée
    private Color revealedColor = Color.WHITE; // Couleur quand la case est révélée
    private final static int DIM = 50; // Taille de la case
    private Gui gui; // Référence à la classe Gui pour la propagation

    // Constructeur
    public Case(boolean hasMine, int surroundingMines, int x, int y, Gui gui) {
        this.hasMine = hasMine;
        this.surroundingMines = surroundingMines;
        this.x = x;
        this.y = y; 
        this.gui = gui; // Enregistrer la référence à Gui
        setPreferredSize(new Dimension(DIM, DIM));
        addMouseListener(this);
        setBackground(hiddenColor);
        setBorder(new LineBorder(Color.BLACK)); // Bordure pour visualiser la grille
    }

    // Méthode pour révéler la case
    public void reveal() {
        if (!isRevealed) {
            isRevealed = true;
            setBackground(revealedColor);
            repaint();
        }
    }

    // Méthode pour vérifier si la case est révélée
    public boolean isRevealed() {
        return isRevealed;
    }

    @Override
    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        if (isRevealed) {
            setBackground(revealedColor);
            gc.setColor(Color.BLACK);

            if (hasMine) {
                gc.drawString("X", getWidth() / 2 - 5, getHeight() / 2 + 5); // Affiche un "X" si c'est une mine
            } else if (surroundingMines > 0) {
                gc.drawString(String.valueOf(surroundingMines), getWidth() / 2 - 5, getHeight() / 2 + 5); // Affiche le nombre de mines autour
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!isRevealed) {
            reveal(); // Révéler la case cliquée

            // Si la case n'a aucune mine autour, démarrer la propagation
            if (surroundingMines == 0) {
                gui.propagation(x, y); // Appel à la propagation avec les coordonnées
            }
        }
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
