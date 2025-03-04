package trabs.trab3.anexos.minesweeper;


public class Mineswepper {
    public static void main(String[] args ) {
        new MinesweeperFrame(new GameMinesweeper(9, 9, 2)).setVisible(true);
    }
}
