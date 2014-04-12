import java.util.Random;

/**
 * @author Thomas Werner
 * @date Mar 18, 2014
 * @brief Allows an 'Easy' AI to generate moves in a game of Connect 4
 * @details An AI that generates moves by generating a number at random and checking
 * 		   if it is a valid move
 *
 */
public class C4EasyAI {

	// Class Constants
	private static final int BOARD_HEIGHT = 7;
	private static final int BOARD_WIDTH = 10;
	private final int PLAYER_ONE = 0;
	private final int PLAYER_TWO = 1;

	// Misc Variables
	private Random m_rand;
	private int m_selectedCol;
	private boolean m_validMove;

	public C4EasyAI() {}


	/**
	 * Randomly selects a column using an object of the Random class until a valid move is selected
	 *
	 * @param PC Allows the current game to be accessed
	 * @return Int representing the selected column
	 */
	public int selectCol(ProgramController PC) {

		m_rand = new Random();
		m_validMove = false;
		while(m_validMove == false) {
			m_selectedCol = 0;
			m_selectedCol = m_rand.nextInt(BOARD_WIDTH);
			m_validMove = PC.getGame().checkValid(m_selectedCol, 0, PC.getGame().getPlayer(PLAYER_TWO));
		}

		return m_selectedCol;
	}
	
	/**
	 * Main method for class tests on C4EasyAI
	 * Takes no arguments
	 */
	public static void main(String[] args) {
		
		final int TEST_CONSTANT_2 = 2;
		
		/*
		 * Test One
		 * Calling C4EasyAI.selectCol on an empty C4 Board.
		 */
		ProgramController testPC = new ProgramController();
		C4AndOthelloBoardStore testBoard = new C4AndOthelloBoardStore();
		C4EasyAI testAI = new C4EasyAI();
		testPC.setIsC4(0);
		testPC.setGame("player1", "player2");
		testBoard.setBoardHeight(BOARD_HEIGHT);
		testBoard.setBoardWidth(BOARD_WIDTH);
		testBoard.setBoard(BOARD_WIDTH, BOARD_HEIGHT);
		testPC.setBoard(testBoard);
		int selectedCol = testAI.selectCol(testPC);
		if(selectedCol >= 0 && selectedCol < BOARD_WIDTH) {
			System.out.println("C4EasyAI.selectCol Evaluated: Correct");
		}
		else {
			System.out.println("C4EasyAI.selectCol Evaluated: Incorrect");
		}
		
		/*
		 * Test Two
		 * Calling C4EasyAI.selectCol on a partially filled C4 Board.
		 */
		ProgramController testPC2 = new ProgramController();
		C4AndOthelloBoardStore testBoard2 = new C4AndOthelloBoardStore();
		C4EasyAI testAI2 = new C4EasyAI();
		testPC2.setIsC4(0);
		testPC2.setGame("player1", "player2");
		testBoard2.setBoardHeight(BOARD_HEIGHT);
		testBoard2.setBoardHeight(BOARD_WIDTH);
		testBoard2.setBoard(BOARD_WIDTH, BOARD_HEIGHT);	
		for(int i = 0; i < BOARD_WIDTH; i++) {
			testBoard2.setPiece(new Piece("Red"), i, BOARD_HEIGHT - TEST_CONSTANT_2);
		}
		testPC2.setBoard(testBoard2);
		
		int selectedCol2 = testAI2.selectCol(testPC2);
		if(selectedCol2 >= 0 && selectedCol2 < BOARD_WIDTH) {
			System.out.println("C4EasyAI.selectCol Evaluated: Correct");
		}
		else {
			System.out.println("C4EasyAI.selectCol Evaluated: Incorrect");
		}
		
	}

}
