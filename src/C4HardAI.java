import java.util.Random;

/**
 * @author Thomas Werner
 * @date Mar 18, 2014
 * @brief Allows a 'Hard' AI to generate moves in a game of Connect 4
 * @details An AI that generates moves by attempting to extend existing lines of 2 or 3,
 * 	       blocking player lines of 3, or by randomly generating a number if none are available
 * 
 */
public class C4HardAI {

	// Class Constants
	private static final int BOARD_HEIGHT = 7;
	private static final int BOARD_WIDTH = 10;
	private final int PLAYER_ONE = 0;
	private final int PLAYER_TWO = 1;
	private final int EMPTY = 2;
	private final int VALID = 0;
	private final int INVALID = 1;
    private final int INCREMENT_2 = 2;
    private final int INCREMENT_3 = 3;
    private final int DECREMENT_2 = 2;
    private final int DECREMENT_3 = 3;
    private final int BOUNDARY_2 = 2;
    private final int BOUNDARY_3 = 3;
    private final String PLAYER_ONE_PIECE_COLOUR = "Red";
	private final String PLAYER_TWO_PIECE_COLOUR = "Yellow";
		
	// Arrays that hold board state and intermediate calculating states
	private int[][] m_boardState = new int[BOARD_WIDTH][BOARD_HEIGHT];
	private int[] m_possibleMoves3;
	private int[] m_possibleMoves2;
	private int[] m_blockColMoves;
	private int[] m_blockRowMoves;
	
	// booleans to check what length of line has been found
	private boolean m_lineFound3;
	private boolean m_lineFound2;
	private boolean m_lineFoundAny;
	
	// Misc variables
	private Random m_rand;
	private int m_selectedCol;
	private boolean m_validMove;
	private Piece m_currentPiece;
	
	
	/**
	 * Generated a valid move in connect4 by
	 * @param PC allows the current board state to be determined
	 * @return a int representing the selected move
	 */
	public int selectCol(ProgramController PC) {
		
		m_selectedCol = 0;
		m_rand = new Random();
		m_possibleMoves3 = new int[BOARD_WIDTH];
		m_possibleMoves2 = new int[BOARD_WIDTH];
		m_blockColMoves = new int[BOARD_WIDTH];
		m_blockRowMoves = new int[BOARD_WIDTH];
		m_lineFound3 = false;
		m_lineFound2 = false;
		m_lineFoundAny = false;
		m_validMove = false;
		
		
		// Clear the board state and associated array states		
		for(int i = 0; i < BOARD_WIDTH; i++) {
			for(int j = 0; j < BOARD_HEIGHT; j++) {
				m_boardState[i][j] = EMPTY;
			}
		}
		
		for(int x = 0; x < BOARD_WIDTH; x++) {
			m_possibleMoves2[x] = INVALID;
			m_possibleMoves3[x] = INVALID;
			m_blockColMoves[x] = INVALID;
			m_blockRowMoves[x] = INVALID;
		}
		
		// Calculate current board state and place in m_boardState
		// Will be used in deciding better moves for Hard AI		
		for(int i = 0; i < BOARD_WIDTH; i++) {
			for(int j = 0; j < BOARD_HEIGHT; j++) {
				m_currentPiece = PC.getGame().getPiece(i, j);
				if(m_currentPiece.getColour().equals("")) {				
					m_boardState[i][j] = EMPTY;
				} else if (m_currentPiece.getColour().equals(PLAYER_ONE_PIECE_COLOUR)) {
					m_boardState[i][j] = PLAYER_ONE;
				} else if(m_currentPiece.getColour().equals(PLAYER_TWO_PIECE_COLOUR)) {
					m_boardState[i][j] = PLAYER_TWO;
				}
			}
		}
		
		boolean m_blockPlayer = true;
		
		if(m_blockPlayer) {
			if(blockPlayerCols()) {
				while(m_validMove == false) {
					m_selectedCol = m_rand.nextInt(BOARD_WIDTH);
					if(m_blockColMoves[m_selectedCol] == VALID) {
						m_validMove = true;
						return m_selectedCol;
					}
				}
			}
			
			if(blockPlayerRows()) {
				while(m_validMove == false) {
					m_selectedCol = m_rand.nextInt(BOARD_WIDTH);
					if(m_blockRowMoves[m_selectedCol] == VALID) {
						m_validMove = true;
						return m_selectedCol;
					}
				}
			}
		}
	
		boolean examine1 = extendCols();
		boolean examine2 = extendRows();
		boolean examine3 = extendRightDiag();
		boolean examine4 = extendLeftDiag();
		
		if(examine1 || examine2 || examine3 || examine4) {
			m_lineFoundAny = true;
		}
		
		if(m_lineFound3 == true) {
			while(m_validMove == false) {
				m_selectedCol = m_rand.nextInt(BOARD_WIDTH);
				if(m_possibleMoves3[m_selectedCol] == VALID) {
					m_validMove = true;
					return m_selectedCol;
				}
			}
		} else if(m_lineFound2 == true) {
			while(m_validMove == false) {
				m_selectedCol = m_rand.nextInt(BOARD_WIDTH);
				if(m_possibleMoves2[m_selectedCol] == VALID) {
					m_validMove = true;
					return m_selectedCol;
				}
			}
		} else {
			while(m_validMove == false) {
				m_selectedCol = m_rand.nextInt(BOARD_WIDTH);
				m_validMove = PC.getGame().checkValid(m_selectedCol, 0, PC.getGame().getPlayer(PLAYER_TWO));
			}
		}
		
		return m_selectedCol;
		
		
	}
	
	/**
	 * Checks if an AI Player column extends from each position
	 * @return boolean true if any column of 2 or 3 is found
	 */
	private boolean extendCols() {
		
		boolean movesFound = false;
		
		for(int i = 0; i < BOARD_WIDTH; i++) {
			for(int j = 0; j < BOARD_HEIGHT; j++) {
				if(j >= BOUNDARY_3 && m_boardState[i][j] == PLAYER_TWO) {
					if(m_boardState[i][j-1] == PLAYER_TWO &&
					m_boardState[i][j- DECREMENT_2] == PLAYER_TWO &&
					m_boardState[i][j- DECREMENT_3] == EMPTY) {
						
						m_possibleMoves3[i] = VALID;
						m_lineFound3 = true;
						movesFound = true;
					}
					
				}
			}
		}
		
		if(movesFound) { return true; }
		
		for(int i = 0; i < BOARD_WIDTH; i++) {
			for(int j = 0; j < BOARD_HEIGHT; j++) {
				if(j >= BOUNDARY_3 && m_boardState[i][j] == PLAYER_TWO) {
					if(m_boardState[i][j-1] == PLAYER_TWO &&
					m_boardState[i][j- DECREMENT_2] == EMPTY) {
						
						m_possibleMoves2[i] = VALID;
						m_lineFound2 = true;
						movesFound = true;
					}
				}
			}
		}
		
		if(movesFound) { 
			return true; 
		} else { 
			return false; 
		}
	}
	
	/**
	 * Checks if an AI Player row extends from each position
	 * @return boolean true if any row of 2 or 3 is found
	 */
	private boolean extendRows() {
		
		boolean movesFound = false;
		
		for(int i = 0; i < BOARD_WIDTH; i++) {
			for(int j = 0; j < BOARD_HEIGHT; j++) {
				if(i < BOARD_WIDTH - DECREMENT_3 && m_boardState[i][j] == PLAYER_TWO) {
					if(m_boardState[i+1][j] == PLAYER_TWO &&
					m_boardState[i+ INCREMENT_2][j] == PLAYER_TWO &&
					m_boardState[i+ INCREMENT_3][j] == EMPTY) {
						
						m_possibleMoves3[i+ INCREMENT_3] = VALID;
						if(i >=1 && m_boardState[i-1][j] == EMPTY) {
							m_possibleMoves3[i-1] = VALID;
						}
						m_lineFound3 = true;
						movesFound = true;
					}
					
				}
			}
		}
		
		if(movesFound) { return true; }
		
		for(int i = 0; i < BOARD_WIDTH; i++) {
			for(int j = 0; j < BOARD_HEIGHT; j++) {
				if(i < BOARD_WIDTH - DECREMENT_2 && m_boardState[i][j] == PLAYER_TWO) {
					if(m_boardState[i+1][j] == PLAYER_TWO &&
					m_boardState[i+ INCREMENT_2][j] == EMPTY) {
						
						m_possibleMoves2[i+ INCREMENT_2] = VALID;
						if(i >=1 && m_boardState[i-1][j] == EMPTY) {
							m_possibleMoves2[i-1] = VALID;
						}
						m_lineFound2 = true;
						movesFound = true;
					}
				}
			}
		}
		
		if(movesFound) { 
			return true; 
		} else { 
			return false; 
		}
		
	}
	
	/**
	 * Checks if an AI Player RightDiagonal extends from each position
	 * @return boolean true if any RightDiagonal of 2 or 3 is found
	 */
	private boolean extendRightDiag() {
		
		boolean movesFound = false;
		
		for(int i = 0; i < BOARD_WIDTH; i++) {
			for(int j = 0; j < BOARD_HEIGHT; j++) {
				if(i < BOARD_WIDTH - DECREMENT_3 &&
				j >= BOUNDARY_3 &&
				m_boardState[i][j] == PLAYER_TWO) {
					if(m_boardState[i+1][j-1] == PLAYER_TWO &&
					   m_boardState[i+ INCREMENT_2][j- DECREMENT_2] == PLAYER_TWO &&
					   m_boardState[i+ INCREMENT_3][j- DECREMENT_3] == EMPTY) {
						
						m_possibleMoves3[i+ INCREMENT_3] = VALID;
						if(i >=1 && j < BOARD_HEIGHT - 1 &&
					    m_boardState[i-1][j+1] == EMPTY) {
							m_possibleMoves3[i-1] = VALID;
						}
						m_lineFound3 = true;
						movesFound = true;
					}
					
				}
			}
		}
		
		if(movesFound) { return true; }
		
		for(int i = 0; i < BOARD_WIDTH; i++) {
			for(int j = 0; j < BOARD_HEIGHT; j++) {
				if(i < BOARD_WIDTH - DECREMENT_3 &&
				   j >= BOUNDARY_3 &&
				   m_boardState[i][j] == PLAYER_TWO) {
					if(m_boardState[i+1][j-1] == PLAYER_TWO &&
					   m_boardState[i+ INCREMENT_2][j- DECREMENT_2] == EMPTY) {
						
						m_possibleMoves2[i+ INCREMENT_2] = VALID;
						if(i >=1 && j < BOARD_HEIGHT - 1 && m_boardState[i-1][j+1] == EMPTY) {
							m_possibleMoves2[i-1] = VALID;
						}
						m_lineFound2 = true;
						movesFound = true;
					}
					
				}
			}
		}
		
		if(movesFound) { 
			return true; 
		} else { 
			return false; 
		}
		
	}
	
	/**
	 * Checks if an AI Player LeftDiagonal extends from each position
	 * @return boolean true if any LeftDiagonal of 2 or 3 is found
	 */
	private boolean extendLeftDiag() {
		
		boolean movesFound = false;
		
		for(int i = 0; i < BOARD_WIDTH; i++) {
			for(int j = 0; j < BOARD_HEIGHT; j++) {				
				if(i >= BOUNDARY_3 && j >= BOUNDARY_3 && m_boardState[i][j] == PLAYER_TWO) {
					if(m_boardState[i-1][j-1] == PLAYER_TWO &&
					   m_boardState[i- DECREMENT_2][j- DECREMENT_2] == PLAYER_TWO &&
					   m_boardState[i- DECREMENT_3][j- DECREMENT_3] == EMPTY) {
							
							m_possibleMoves3[i- DECREMENT_3] = VALID;
							if(i < (BOARD_WIDTH - 1) && j < BOARD_HEIGHT - 1 && 
							m_boardState[i+1][j+1] == EMPTY) {
								m_possibleMoves3[i+1] = VALID;
							}
							m_lineFound3 = true;
							movesFound = true;
					}					
				}			
			}
		}
		
		if(movesFound) { return true; }
		
		for(int i = 0; i < BOARD_WIDTH; i++) {
			for(int j = 0; j < BOARD_HEIGHT; j++) {				
				if(i >= BOUNDARY_2 && j >= BOUNDARY_2 && m_boardState[i][j] == PLAYER_TWO) {
					if(m_boardState[i-1][j-1] == PLAYER_TWO &&
					   m_boardState[i- DECREMENT_2][j- DECREMENT_2] == EMPTY) {
							
							m_possibleMoves2[i- DECREMENT_2] = VALID;
							if(i < (BOARD_WIDTH - 1) && j > BOARD_HEIGHT - 1 && m_boardState[i+1][j+1] == EMPTY) {
								m_possibleMoves2[i+1] = VALID;
							}
							m_lineFound2 = true;
							movesFound = true;
					}					
				}			
			}
		}
		
		if(movesFound) { 
			return true; 
		} else { 
			return false; 
		}
		
	}
	
	/**
	 * Checks if a Human Player column extends from each position
	 * @return boolean true if any Human Player column of 3 is found
	 */
	private boolean blockPlayerCols() {
		
		boolean playerColsFound = false;
		
		for(int i = 0; i < BOARD_WIDTH; i++) {
			for(int j = 0; j < BOARD_HEIGHT; j++) {
				if(j >= BOUNDARY_3 && m_boardState[i][j] == PLAYER_ONE) {
					if(m_boardState[i][j-1] == PLAYER_ONE &&
					m_boardState[i][j- DECREMENT_2] == PLAYER_ONE &&
					m_boardState[i][j- DECREMENT_3] == EMPTY) {
						
						m_blockColMoves[i] = VALID;
						playerColsFound = true;
					}
					
				}
			}
		}
		return playerColsFound;
	}
	
	/**
	 * Checks if a Human Player row extends from each position
	 * @return boolean true if any Human Player row of 3 is found
	 */
	private boolean blockPlayerRows() {
		
		boolean playerRowsFound = false;
		
		for(int i = 0; i < BOARD_WIDTH; i++) {
			for(int j = 0; j < BOARD_HEIGHT; j++) {
				if(i < BOARD_WIDTH - DECREMENT_3 && m_boardState[i][j] == PLAYER_ONE) {
					if(m_boardState[i+1][j] == PLAYER_ONE &&
					m_boardState[i+ INCREMENT_2][j] == PLAYER_ONE &&
					m_boardState[i+ INCREMENT_3][j] == EMPTY) {
						
						m_blockRowMoves[i+ INCREMENT_3] = VALID;
						
						playerRowsFound = true;
					}
					
				}
				
				if(i < BOARD_WIDTH - DECREMENT_3 && i >= 1 && m_boardState[i][j] == PLAYER_ONE) {
					if(m_boardState[i+1][j] == PLAYER_ONE &&
					m_boardState[i+ INCREMENT_2][j] == PLAYER_ONE &&
					m_boardState[i-1][j] == EMPTY) {
						
						m_blockRowMoves[i-1] = VALID;
						
						playerRowsFound = true;
					}
					
				}
			}
		}
		
		return playerRowsFound;
	}
	
	/**
	 * Main method for class tests on C4HardAI
	 * Takes no arguments
	 */
	public static void main(String[] args) {
		
		final int MAX_COL = BOARD_HEIGHT -2;
		final int INC1 = 1;
		final int INC2 = 2;
		final int INC3 = 3;
		
		
		/*
		 * Test One
		 * Calling C4HardAI.selectCol on an empty C4 Board.
		 */
		ProgramController testPC = new ProgramController();
		C4AndOthelloBoardStore testBoard = new C4AndOthelloBoardStore();
		C4HardAI testAI = new C4HardAI();
		testPC.setIsC4(0);
		testPC.setGame("player1", "player2");
		testBoard.setBoardHeight(BOARD_HEIGHT);
		testBoard.setBoardHeight(BOARD_WIDTH);
		testBoard.setBoard(BOARD_WIDTH, BOARD_HEIGHT);
		int selectedCol = testAI.selectCol(testPC);
		if(selectedCol >= 0 && selectedCol < BOARD_WIDTH) {
			System.out.println("C4HardAI.selectCol Evaluated: Correct");
		}
		else {
			System.out.println("C4HardAI.selectCol Evaluated: Incorrect");
		}
		
		/*
		 * Test Two
		 * Calling C4HardAI.selectCol to extend a line of 3 AI counters.
		 */
		ProgramController testPC2 = new ProgramController();
		C4AndOthelloBoardStore testBoard2 = new C4AndOthelloBoardStore();
		C4HardAI testAI2 = new C4HardAI();
		testPC2.setIsC4(0);
		testPC2.setGame("player1", "player2");
		testBoard2.setBoardHeight(BOARD_HEIGHT);
		testBoard2.setBoardWidth(BOARD_WIDTH);
		testBoard2.setBoard(BOARD_WIDTH, BOARD_HEIGHT);
		testPC2.setBoard(testBoard2);
		
		Piece[][] testPieceLayout = new Piece[BOARD_WIDTH][BOARD_HEIGHT];
		for (int i = 0; i < BOARD_HEIGHT; i++) {
	  		  for (int j = 0; j < BOARD_WIDTH; j++) {
	  			  testPieceLayout[j][i] = new Piece("");
	  		  }
		}
	  		
		testPieceLayout[0][MAX_COL] = new Piece("Yellow");
		testPieceLayout[INC1][MAX_COL] = new Piece("Yellow");
		testPieceLayout[INC2][MAX_COL] = new Piece("Yellow");
		testPieceLayout[INC3][MAX_COL] = new Piece("");
		try {
			// Only applicable arguments are 'testPieceLayout'
			testPC2.ProgramController(0, INC2, "player1", "player2", testPieceLayout, 1, 1);
		} catch (Exception e) {
			System.out.println("Error setting layout in Test 2 OthHardAI");
			e.printStackTrace();
		} finally {
			testPC2.setVisible(false);
		}
		
		
		int selectedCol2 = testAI2.selectCol(testPC2);
		System.out.println(selectedCol2);
		if(selectedCol2 == INC3) {
			System.out.println("C4HardAI.selectCol Evaluated: Correct");
		}
		else {
			System.out.println("C4HardAI.selectCol Evaluated: Incorrect");
		}

	}
	
}


