package trabs.trab3.anexos.minesweeper.cells;

import trabs.trab3.anexos.minesweeper.Board;
import trabs.trab3.anexos.minesweeper.GameListener;

/**
 * A célula Empty quando é destapada expande
 * a ação às oito células adjacentes.
 * Quando é informada que tem uma mina adjacente, substitui-se por uma célula AdjacentMine.
 */

public class Empty extends DummyCell {

    @Override
    public int uncover(Board board, int line, int column) {
        int count =1;
        board.uncoveredCell(line,column, this);
        for (int i = line-1; i <= line+1; i++) {
            for (int j = column-1; j <= column+1; j++) {
                count+=board.getCell(i, j).uncover(board, i, j);
            }
        }
        return count;
    }

    @Override
    public void notifyEvent(GameListener gameListener, int line, int column) {
        gameListener.cellUncover(line, column);
    }

    @Override
    public void adjacentMine(Board board, int line, int column) {
        board.coveredCell(line, column, new Adjacent());
    }
}
