package trabs.trab3.anexos.minesweeper.cells;

import trabs.trab3.anexos.minesweeper.Board;
import trabs.trab3.anexos.minesweeper.GameListener;

/**
 * A célula Mine implementa as operações correspondentes à mina.
 * Quando destapada informa o tabuleiro que foi destapada.
 * É passiva quanto à expansão ou à informação de que existe
 * uma mina adjacente.
 */
public class Mine extends DummyCell {

    @Override
    public int uncover(Board board, int line, int column) {
        board.uncoveredMine(line, column, this);
        return 0;
    }

    @Override
    public void notifyEvent(GameListener gameListener, int line, int column) {
        gameListener.mineUncover(line, column);
    }
}
