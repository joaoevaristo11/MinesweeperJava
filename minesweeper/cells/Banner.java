package trabs.trab3.anexos.minesweeper.cells;

import trabs.trab3.anexos.minesweeper.Board;
import trabs.trab3.anexos.minesweeper.GameListener;

/**
 * A célula Banner tem o comportamento de uma célula
 * destapada exceto no que se refere à operaçăo turnBanner.
 * A operação de voltar a bandeira coloca no tabuleiro
 * a que estava por baixo.
 */
public class Banner extends CoverageCell {

	public Banner(Cell cellDown) {
		super(cellDown);
	}

	@Override
	public int turnBanner(Board board, int line, int column) {
		if (board.getCell(line, column) instanceof Banner) {
			board.setCell(line, column, cellDown); // Remove a bandeira
			return -1;
		} else {
			board.setCell(line, column, new Banner(this)); // Coloca uma bandeira
			return 1;
		}
	}


	@Override
	public void notifyEvent(GameListener gameListener, int line, int column) {
		gameListener.bannerPlaced(line, column);
	}
}



