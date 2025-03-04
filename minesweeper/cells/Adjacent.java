package trabs.trab3.anexos.minesweeper.cells;

import trabs.trab3.anexos.minesweeper.Board;
import trabs.trab3.anexos.minesweeper.GameListener;

/**
 * A célula Adjacent memoriza o número de minas adjacentes
 * e sempre que é informada que tem mais uma mina adjacente
 * incrementa esse número.
 * Quando é destapada ou expandida informa o tabuleiro para
 * a destapar (uncoveredCell) e retorna 1 porque a operação
 * destapou uma célula.
 */
public class Adjacent extends DummyCell {
    private int adjacentMines = 1;

    @Override
    public void adjacentMine(Board board, int line, int column) {
        adjacentMines++;
    }

    @Override
    public int uncover(Board board, int line, int column) {
        board.uncoveredCell(line, column, this);
        return 1;
    }

    @Override
    public void notifyEvent(GameListener gameListener, int line, int column) {
        gameListener.cellUncover(line, column, adjacentMines);
    }
}
