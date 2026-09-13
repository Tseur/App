import java.util.Random;

public class Champ {

    Random random = new Random();

    final static int DEF_LENGTH = 8;
    final static int DEF_WIDTH = 8;
    private final static int DEF_NBMINES = 10; 
    private boolean[][] champ = new boolean[DEF_WIDTH][DEF_LENGTH];
    private int [] tabSize = {5, 10, 15};
    private int [] tabDMines = {3, 7, 9};

    boolean isMines(int x, int y){

        return champ[x][y];
    }

    public void init(int startX, int startY) { 

        for (int n = DEF_NBMINES; n!= 0;) {
            int x = random.nextInt(champ.length);
            int y = random.nextInt(champ[0].length);
            if (!champ[y][x] || (x == startX && y == startY)) {
                champ[y][x] = true;
                n--;
            }
        }
    }


    public void display() {
        for (int i = 0; i < champ.length; i++) {
            System.out.println(" ");
            for (int j = 0; j < champ[0].length; j++) {
                if (champ[i][j]) {
                    System.out.print("x "); 
                } else {
                    int minesAround = nbMinesAround(i, j);
                    if (minesAround > 0) {
                        System.out.print(minesAround + " "); 
                    } else {
                        System.out.print("o "); 
                    }
                }
            }
        }
        System.out.println();
    }


    public int nbMinesAround(int x, int y){
        int n = 0;
        for (int i = Math.max(0, x - 1); i <= Math.min(x + 1, champ.length - 1); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(y + 1, champ[0].length - 1); j++) {
                if (champ[i][j])
                    n++;
            }
        }
        return n;
    }
    public int getWidth(){
        return champ.length;
    }
    
    public int getHight(){
        return champ.length;
    }

    public void newGame(int level) {
        champ = new boolean[tabSize[level]][tabSize[level]];

    }
}
