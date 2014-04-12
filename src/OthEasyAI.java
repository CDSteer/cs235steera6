import java.util.Random;

/**
 * @author Thomas Werner
 * @date Mar 18, 2014
 * @brief Allows an 'Easy' AI to generate moves in a game of Othello
 * @details An AI that generates moves by generating 2 numbers at random and checking
 * 		   if it is a valid move
 *
 */
public class OthEasyAI {

	// Class Constants
	private static final int BOARD_ROWS = 8;
	private static final int BOARD_COLS = 8;
	private static final int PLAYER_TWO = 1;
    private static final int MOVE_SELECT_VALUE = 2;

	// Misc Variables
	private Random m_rand;
	private int[] m_selectedMoves;
	private int m_selectedRow;
	private int m_selectedCol;
	private boolean m_validMove;

	/**
	 * Randomly selects a column and row using an object of the Random class until a valid move is selected
	 *
	 * @param PC Allows the current game to be accessed
	 * @return int[] array containing 2 indexes representing the selected x,y values
	 */
	public int[] selectMove(ProgramController PC) {

		m_selectedMoves = new int[MOVE_SELECT_VALUE];
		m_rand = new Random();
		m_validMove = false;

		while(m_validMove == false) {
			m_selectedRow = m_rand.nextInt(BOARD_ROWS);
			m_selectedCol = m_rand.nextInt(BOARD_COLS);
			if(PC.getGame().checkValid(m_selectedRow, m_selectedCol, PC.getGame().getPlayer(PLAYER_TWO)) == true) {
				m_validMove = true;
			}
		}

		m_selectedMoves[0] = m_selectedRow;
		m_selectedMoves[1] = m_selectedCol;

		return m_selectedMoves;

	}
	
	/**
	 * Main method for class tests on OthEasyAI
	 * Takes no arguments
	 */
	public static void main(String[] args) {
		
		/*
		 * Test One
		 * Calling OthEasyAI.selectCol on an Oth Board with default starting state.
		 */
		ProgramController testPC = new ProgramController();
		C4AndOthelloBoardStore testBoard = new C4AndOthelloBoardStore();
		OthEasyAI testAI = new OthEasyAI();
		testPC.setIsC4(1);
		testPC.setGame("player1", "player2");
		testBoard.setBoardWidth(BOARD_ROWS);
		testBoard.setBoardHeight(BOARD_COLS);
		testBoard.setBoard(BOARD_ROWS, BOARD_COLS);
		testPC.setBoard(testBoard);
		int[] selectedMoves = new int[2];
		selectedMoves = testAI.selectMove(testPC);
		int testRow = selectedMoves[0];
		int testCol = selectedMoves[1];
		if(testPC.getGame().checkValid(testRow, testCol, testPC.getGame().getPlayer(PLAYER_TWO)) == true) {
			System.out.println("C4EasyAI.selectCol Evaluated: Correct");
		}
		else {
			System.out.println("C4EasyAI.selectCol Evaluated: Incorrect");
		}
		
	}
}
