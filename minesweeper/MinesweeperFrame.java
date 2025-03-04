package trabs.trab3.anexos.minesweeper;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import java.util.function.BiConsumer;

import trabs.trab3.anexos.minesweeper.gui.*;
import static trabs.trab3.anexos.minesweeper.gui.Utils.*;

/**
 * <p> Implementa um visualizador em modo grafico.</p>
 * <p> O tabuleiro do jogo é implementado com um JPanel contendo
 *     componentes graficas do tipo Square. O layout é um GridLayout
 *     com o numero de linhas e de colunas do objecto Game ao qual é
 *     associado na construcao, e no inicio de cada jogo.
 * </p>
 * <p> Quando  é informado que algo mudou no jogo (vazia, mina,
 *     adjacent a mina, ou bandeira) altera o caracter da matriz de
 *     acordo com o tipo de pedido e o aspecto visual da quadricula.
 * </p>
 * <p> Quando é informado do inicio do jogo, caso a dimensao do tabuleiro
 *     seja alterada cria um novo tabuleiro.
 * </p>
 * <p> Quando o jogo termina quer seja porque o jogador tenha ganho, ou
 *     porque tenha perdido informa o jogador do fim do jogo.</p>
 *
 * <p> Alem de ser um visualizador, com esta interface grafica tambem é
 *     possivel desencadear as operações de descobrir quadriculas, colocar e
 *     retirar bandeiras, ou iniciar o jogo.
 * </p>
 * <p> Fazendo um click com o botão esquerdo do mouse sobre uma quadricula,
 *     é possivel enviar a ordem ao jogo para descobrir a quadricula. </p>
 * <p> Fazendo um click com o botão direito do mouse sobre uma quadricula,
 *     é possivel enviar a ordem ao jogo para virar a bandeira. </p>
 * <p> Usando as opções do menu bar é possivel enviar a ordem ao jogo de
 *     inicio de jogo, com ou sem mudança de dimensões.
 *
 */
public class MinesweeperFrame extends JFrame implements GameListener {
	private static final Border INSIDE_BORDER =
			new CompoundBorder(new BevelBorder(BevelBorder.LOWERED),
					new EmptyBorder(2, 2, 2, 2));
	private static final Border OUTSIDE_BORDER = new CompoundBorder(
			new BevelBorder(BevelBorder.RAISED),
			new CompoundBorder(new EmptyBorder(1, 1, 1, 1),
					new BevelBorder(BevelBorder.LOWERED)));

	private static final ImageIcon SMILE_ICON = getImageIcon("smile.jpg", "smile");
	private static final ImageIcon MOAN_ICON = getImageIcon("moan.jpg", "moan");

	private final BiConsumer<Integer, Integer> showMine = showAction("mine.jpg", "M");
	private final BiConsumer<Integer, Integer> showBanner = showAction("banner.jpg", "B");

	private boolean isGameOver = false; // Controla o estado do jogo
	private JComponent board;
	private final Game game;
	private Timer timer;
	private long initialTime;

	public MinesweeperFrame(Game g) {
		super("Minesweeper");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		game = g;

		Container cp = getContentPane();
		((JComponent) cp).setBorder(OUTSIDE_BORDER);
		cp.add(board = makeBoardComponent(), BorderLayout.CENTER);
		cp.add(makeInfoComponent(), BorderLayout.NORTH);
		setJMenuBar(makeMenuOption(game));

		game.addGameListener(this);

		setResizable(false);
		setLocationRelativeTo(null);
		pack();
	}

	private Display timerDisplay;
	private Display bannerDisplay;
	private String currentPlayerName = "";

	private JComponent makeInfoComponent() {
		JPanel infoPanel = new JPanel(new BorderLayout());

		// Número de bandeiras
		JPanel bannerPanel = new JPanel();
		bannerPanel.setBackground(Color.black);
		bannerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		bannerDisplay = new Display(3, 0);
		infoPanel.add(bannerDisplay, BorderLayout.WEST);

		// Botão de iniciar jogo
		JButton startButton = new JButton(SMILE_ICON);
		startButton.addActionListener(e -> {
			// Solicita o nome do jogador
			currentPlayerName = JOptionPane.showInputDialog(this, "Enter your name:", "Player");
			if (currentPlayerName == null || currentPlayerName.isBlank()) {
				currentPlayerName = "Player";
			}

			// Inicia o jogo
			game.start(game.getNumberOfLines(), game.getNumberOfColumns(), game.getNumberOfMines());
			startTimer();
		});
		infoPanel.add(startButton, BorderLayout.CENTER);

		JPanel timerPanel = new JPanel();
		timerPanel.setBackground(Color.BLACK);
		timerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		timerDisplay = new Display(3, 0);
		timerPanel.add(timerDisplay);
		infoPanel.add(timerPanel, BorderLayout.EAST);

		return infoPanel;
	}

	private void startTimer() {
		if (timer != null) {
			timer.stop();
		}
		initialTime = System.currentTimeMillis();
		timer = new Timer(1000, e -> {long elapsedSeconds = (System.currentTimeMillis() - initialTime) / 1000;
		timerDisplay.setValue((int) elapsedSeconds);});
		timer.start();
	}


	protected JMenuBar makeMenuOption(Game game) {
		JMenuBar menuBar = new JMenuBar(); // Cria o JMenuBar
		JMenu optionsMenu = new JMenu("Options"); // Cria o JMenu chamado "Options"

		// Adiciona os itens do menu
		JMenuItem beginner = new JMenuItem("Beginner");
		beginner.addActionListener(e -> {game.start(9, 9, 10);startTimer();});
		optionsMenu.add(beginner);

		JMenuItem intermediate = new JMenuItem("Intermediate");
		intermediate.addActionListener(e -> {game.start(16, 16, 40);startTimer();});
		optionsMenu.add(intermediate);

		JMenuItem advanced = new JMenuItem("Advanced");
		advanced.addActionListener(e -> {game.start(16, 30, 99);startTimer();});
		optionsMenu.add(advanced);

		menuBar.add(optionsMenu);
		return menuBar;
	}

	private Square makeSquare(int l, int c) {
		Square square = new Square(l, c);
		square.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isGameOver){return;} // Bloqueia ações se o jogo acabou
				if (e.getButton() == MouseEvent.BUTTON1) {
					game.uncover(l, c);
				} else {
					game.turnBanner(l, c);
				}
			}
		});
		return square;
	}

	private JComponent makeBoardComponent() {
		int rows = game.getNumberOfLines();
		int cols = game.getNumberOfColumns();

		JPanel boardPanel = new JPanel(new GridLayout(rows, cols));
		boardPanel.setBorder(INSIDE_BORDER);

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				boardPanel.add(makeSquare(r, c));
			}
		}

		return boardPanel;
	}

	private BiConsumer<Integer, Integer> showAction(String filename, String text) {
		ImageIcon img = getImageIcon(filename, text);
		return img == null
				? (row, col) -> getSquare(row, col).setText(text)
				: (row, col) -> getSquare(row, col).show(img);
	}

	private Square getSquare(int line, int column) {
		return (Square) board.getComponent(line * game.getNumberOfColumns() + column);
	}

	@Override
	public void gameStart(Game game) {
		isGameOver = false; // Reinicia o estado do jogo
		startTimer();
	}

	@Override
	public void dimensionsChanged(Game game) {
		getContentPane().remove(board);
		board = makeBoardComponent();
		getContentPane().add(board, BorderLayout.CENTER);
		pack();
	}

	@Override
	public void cellCovered(int line, int column) {
		getSquare(line, column).hide();
	}

	@Override
	public void cellUncover(int line, int column) {
		getSquare(line, column).show(" ");
	}

	@Override
	public void cellUncover(int line, int column, int value) {
		getSquare(line, column).show(Integer.toString(value));
	}

	@Override
	public void mineUncover(int line, int column) {
		showMine.accept(line, column);
	}

	@Override
	public void bannerPlaced(int line, int column) {
		showBanner.accept(line, column);
	}

	@Override
	public void numberOfBannersChanged(int number) {
		// Atualiza o Display de bandeiras
		bannerDisplay.setValue(number);
	}

	private String determineLevel() {
		int rows = game.getNumberOfLines();
		int cols = game.getNumberOfColumns();
		int mines = game.getNumberOfMines();

		if (rows == 9 && cols == 9 && mines == 10) return "Beginner";
		if (rows == 16 && cols == 16 && mines == 40) return "Intermediate";
		if (rows == 16 && cols == 30 && mines == 99) return "Advanced";
		return "CustomMode";
	}

	@Override
	public void playerWin() {
		if (timer != null) timer.stop();
		long elapsedSeconds = (System.currentTimeMillis() - initialTime) / 1000;

		// Determina o nível atual do jogo
		String level = determineLevel();

		// Atualiza o leaderboard
		LeaderBoard.updateRecord(level, currentPlayerName, (int) elapsedSeconds);

		// Obtém o melhor tempo do jogador
		String bestRecord = LeaderBoard.getPlayerRecord(level, currentPlayerName);

		// Exibe a mensagem com o tempo do jogador, o seu melhor tempo e os melhores tempos
		String message = "You Won!\n" + "Your time: " + elapsedSeconds / 60 + " min " + elapsedSeconds % 60 + " s\n\n" + bestRecord + "\n\n" + "Top Records:\n" + LeaderBoard.getTopRecords(level);
		JOptionPane.showMessageDialog(this, message, "Victory", JOptionPane.INFORMATION_MESSAGE);

		isGameOver = true; // Define que o jogo terminou
	}

	@Override
	public void playerLose() {
		if (timer != null) {
			timer.stop();
		}

		// Determina o nível atual do jogo (begginer, etc...)
		String level = determineLevel();

		// Obtém o melhor tempo do jogador
		String bestRecord = LeaderBoard.getPlayerRecord(level, currentPlayerName);

		// Exibe a mensagem com o tempo do jogador e os melhores tempos
		String message = "You Lost!\n\n" + bestRecord + "\n\n" + "Top Records:\n" + LeaderBoard.getTopRecords(level);
		JOptionPane.showMessageDialog(this, message, "Defeat", JOptionPane.ERROR_MESSAGE);

		isGameOver = true; // Define que o jogo terminou
	}

}

