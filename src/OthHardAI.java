

import java.util.Random;

/**
 * @author Thomas Werner
 * @date Mar 24, 2014
 * @brief Allows a 'Hard' AI to generate moves in a game of Othello
 * @details An AI that generates moves by selecting moves that flip the most amount of counters,
 * 		   and randomly selecting a move if there is no highest.
 * 
 */
public class OthHardAI {

	// Class constants
	private static final int BOARD_WIDTH = 8;
	private static final int BOARD_HEIGHT = 8;
	private static final int PLAYER_ONE = 0;
	private static final int PLAYER_TWO = 1;
	private final int EMPTY = 2;
	private final int INVALID_MOVE = 0;
    private final int INCREMENT_2 = 2;
    private final int DECREMENT_2 = 2;
    private final int SELECTED_MOVES_2 = 2;

	
	private final String PLAYER_ONE_PIECE_COLOUR = "Black";
	private final String PLAYER_TWO_PIECE_COLOUR = "White";
	
	// variables holding class objects
	private AbstractPlayer humanPlayer;
	private AbstractPlayer AIPlayer;
	private OthelloGameLogic othello;
			
	// Arrays holding current board state and intermediate calculation states
	private int[][] m_boardState;
	private int[][] m_possibleMoves;
	private int[][] m_moveLength;
	private int[] m_selectedMoves;
	
	// Misc Variables
	private Random m_rand;
	private int m_maxLineLength;
	private int m_selectedRow;
	private int m_selectedCol;
	private Piece m_currentPiece;
	private boolean m_validMove;
	
	public int[] selectMove(ProgramController PC) {
		
		calculateMoves(PC);
		
		m_maxLineLength = 0;
		for(int i = 0; i < BOARD_WIDTH; i++) {
			for(int j = 0; j < BOARD_HEIGHT; j++) {
				
				if(m_moveLength[i][j] > m_maxLineLength) {
					m_maxLineLength = m_moveLength[i][j];
				}
			}
		}
		
		m_selectedMoves = new int[SELECTED_MOVES_2];
		m_rand = new Random();
		m_validMove = false;
		
		if(othello.checkTakeableTurn(AIPlayer) == false) {
			return m_selectedMoves;
		}
		
		while(m_validMove == false ||
				m_moveLength[m_selectedRow][m_selectedCol] != m_maxLineLength) {
			m_validMove = false;
			m_selectedRow = m_rand.nextInt(BOARD_WIDTH);
			m_selectedCol = m_rand.nextInt(BOARD_HEIGHT);
			if(PC.getGame().checkValid(m_selectedRow, m_selectedCol, PC.getGame().getPlayer(PLAYER_TWO)) == true) {
				m_validMove = true;
			}
		}
		
		System.out.println("OthHardAI Generated move: " + m_selectedRow + " " + m_selectedCol);
		
		m_selectedMoves[0] = m_selectedRow;
		m_selectedMoves[1] = m_selectedCol;
		
		return m_selectedMoves;
		
	}
	
	private void calculateMoves(ProgramController PC) {

		humanPlayer = PC.getGame().getPlayer(PLAYER_ONE);
		AIPlayer = PC.getGame().getPlayer(PLAYER_TWO);
		m_boardState = new int[BOARD_WIDTH][BOARD_HEIGHT];
		m_possibleMoves = new int[BOARD_WIDTH][BOARD_HEIGHT];
		m_moveLength = new int[BOARD_WIDTH][BOARD_HEIGHT];
		othello = new OthelloGameLogic();
		
		// Clears m_boardState		
		for(int i = 0; i < BOARD_WIDTH; i++) {
			for(int j = 0; j < BOARD_HEIGHT; j++) {
				m_boardState[i][j] = EMPTY;
				m_possibleMoves[i][j] = INVALID_MOVE;
			}
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
		
		// Check each position for the move that flips the most counters
		for(int i = 0; i < BOARD_WIDTH; i++) {
			for(int j = 0; j < BOARD_HEIGHT; j++) {
				if(m_boardState[i][j] == PLAYER_TWO || m_boardState[i][j] == PLAYER_ONE) {
					m_moveLength[i][j] = INVALID_MOVE;
				} else if(m_boardState[i][j] == EMPTY) {
					if(PC.getGame().checkValid(i, j, AIPlayer) == true) {
												
						m_maxLineLength = 0;
		
						checkVerticalDown(i, j, AIPlayer);
						checkVerticalUp(i, j, AIPlayer);
						checkRowsRight(i, j, AIPlayer);
						checkRowsLeft(i, j, AIPlayer);
						checkDiagUpRight(i, j, AIPlayer);
						checkDiagUpLeft(i, j, AIPlayer);
						checkDiagDownRight(i, j, AIPlayer);
						checkDiagDownLeft(i, j, AIPlayer);
						
						m_moveLength[i][j] = m_maxLineLength;
						
					}
				}
			}
				
		}
	}
	
	private void checkVerticalDown(int row, int col, AbstractPlayer player) {
		
		if(col == (BOARD_HEIGHT - 1)) { return; }
		
		if(m_boardState[row][col+1] == PLAYER_TWO || m_boardState[row][col+1] == EMPTY) {
			return;
		}
		
		int lineLength = 1;
		if(col < BOARD_HEIGHT - 1) {			
			for (int j = col + INCREMENT_2; j < BOARD_HEIGHT; j++) {
				if(m_boardState[row][j] == PLAYER_ONE) {
					lineLength++;		
				} else if(m_boardState[row][j] == PLAYER_TWO) {
					if(lineLength > m_maxLineLength) {
						m_maxLineLength = lineLength;
					}
				} else if(m_boardState[row][j] == EMPTY) {
					return;
				}
			}
		}	
	}
	
	private void checkVerticalUp(int row, int col, AbstractPlayer player) {
		
		if(col == 0) { return; }
		
		if(m_boardState[row][col-1] == PLAYER_TWO || m_boardState[row][col-1] == EMPTY) {
			return;
		}
				
		int lineLength = 1;
		
		if( col > 1) {
			for (int j = col - DECREMENT_2; j >= 0; j--) {
				if(m_boardState[row][j] == PLAYER_ONE) {
					lineLength++;		
				} else if(m_boardState[row][j] == PLAYER_TWO) {
					if(lineLength > m_maxLineLength) {
						m_maxLineLength = lineLength;
					}
				} else if(m_boardState[row][j] == EMPTY) {
					return;
				}
			}
		}
		
	}
	
	private void checkRowsRight(int row, int col, AbstractPlayer player) {
		
		if(row == (BOARD_WIDTH - 1)) { return; }
		
		if(m_boardState[row+1][col] == PLAYER_TWO || m_boardState[row+1][col] == EMPTY) {
			return;
		}
		
		int lineLength = 1;
		
		if(row < BOARD_WIDTH - 1) {
			for (int i = row + INCREMENT_2; i < BOARD_WIDTH; i++) {
				if(m_boardState[i][col] == PLAYER_ONE) {
					lineLength++;		
				} else if(m_boardState[i][col] == PLAYER_TWO) {
					if(lineLength > m_maxLineLength) {
						m_maxLineLength = lineLength;
					}
				} else if(m_boardState[i][col] == EMPTY) {
					return;
				}
			}
		}
	}
	
	private void checkRowsLeft(int row, int col, AbstractPlayer player) {
		
		if(row == 0) { return; }
		
		if(m_boardState[row-1][col] == PLAYER_TWO || m_boardState[row-1][col] == EMPTY) {
			return;
		}
			
		int lineLength = 1;
		
		if(row > 1) {
			for (int i = row - DECREMENT_2; i >= 0; i--) {
				if(m_boardState[i][col] == PLAYER_ONE) {
					lineLength++;		
				} else if(m_boardState[i][col] == PLAYER_TWO) {
					if(lineLength > m_maxLineLength) {
						m_maxLineLength = lineLength;
					}
				} else if(m_boardState[i][col] == EMPTY) {
					return;
				}
			}
		}
	}
	
	private void checkDiagUpRight(int row, int col, AbstractPlayer player) {
		
		if(row == (BOARD_WIDTH - 1)|| col == 0) { return; }
		
		if(m_boardState[row+1][col-1] == PLAYER_TWO || m_boardState[row+1][col-1] == EMPTY) {
			return;
		}
		
		int lineLength = 1;
		
		if(row < BOARD_WIDTH - 1 && col > 1) {
			for(int i = row + INCREMENT_2, j = col - DECREMENT_2; i < BOARD_WIDTH && j >= 0; i++, j--) {
				if(m_boardState[i][j] == PLAYER_ONE) {
					lineLength++;		
				} else if(m_boardState[i][j] == PLAYER_TWO) {
					if(lineLength > m_maxLineLength) {
						m_maxLineLength = lineLength;
					}
				} else if(m_boardState[i][j] == EMPTY) {
					return;
				}
			}
		}
	}
	
	private void checkDiagUpLeft(int row, int col, AbstractPlayer player) {
		
		if(row == 0 || col == 0) { return; }
		
		if(m_boardState[row-1][col-1] == PLAYER_TWO || m_boardState[row-1][col-1] == EMPTY) {
			return;
		}
		
		int lineLength = 1;
		
		if(row > 1 && col > 1) {
			for(int i = row - DECREMENT_2, j = col - DECREMENT_2; i >= 0 && j >= 0; i--, j--) {
				if(m_boardState[i][j] == PLAYER_ONE) {
					lineLength++;		
				} else if(m_boardState[i][j] == PLAYER_TWO) {
					if(lineLength > m_maxLineLength) {
						m_maxLineLength = lineLength;
					}
				} else if(m_boardState[i][j] == EMPTY) {
					return;
				}
			}
		}
	}
	
	private void checkDiagDownRight(int row, int col, AbstractPlayer player) {
		
		if(row == (BOARD_WIDTH - 1)|| col == (BOARD_HEIGHT - 1)) { return; }
		
		if(m_boardState[row+1][col+1] == PLAYER_TWO || m_boardState[row+1][col+1] == EMPTY) {
			return;
		}
		
		int lineLength = 1;
		
		if(row < BOARD_WIDTH - 1 && col < BOARD_HEIGHT - 1) {
			for(int i = row + INCREMENT_2, j = col + INCREMENT_2; i < BOARD_WIDTH && j < BOARD_HEIGHT; i++, j++) {
				if(m_boardState[i][j] == PLAYER_ONE) {
					lineLength++;		
				} else if(m_boardState[i][j] == PLAYER_TWO) {
					if(lineLength > m_maxLineLength) {
						m_maxLineLength = lineLength;
					}
				} else if(m_boardState[i][j] == EMPTY) {
					return;
				}
			}
		}
	}
	
	private void checkDiagDownLeft(int row, int col, AbstractPlayer player) {
		
		if(row == 0 || col == (BOARD_HEIGHT - 1)) { return; }
		
		if(m_boardState[row-1][col+1] == PLAYER_TWO || m_boardState[row-1][col+1] == EMPTY) {
			return;
		}
		
		int lineLength = 1;
		
		if(row > 1 && col < BOARD_HEIGHT - 1) {
			for(int i = row - DECREMENT_2, j = col + INCREMENT_2; i >= 0 && j < BOARD_HEIGHT; i--, j++) {
				if(m_boardState[i][j] == PLAYER_ONE) {
					lineLength++;		
				} else if(m_boardState[i][j] == PLAYER_TWO) {
					if(lineLength > m_maxLineLength) {
						m_maxLineLength = lineLength;
					}
				} else if(m_boardState[i][j] == EMPTY) {
					return;
				}
			}
		}
	}
	
	/**
	 * Main method for class tests on OthHardAI
	 * Takes no arguments
	 */
	public static void main(String[] args) {

        final int TEST_POSITION_FIVE = 5;
        final int TEST_POSITION_FOUR = 4;
        final int SELECTED_MOVES_TWO = 2;
		
		/*
		 * Test One
		 * Calling OthHardAI.selectMove on an Oth Board with default starting state.
		 */
		ProgramController testPC = new ProgramController();
		C4AndOthelloBoardStore testBoard = new C4AndOthelloBoardStore();
		OthHardAI testAI = new OthHardAI();
		testPC.setIsC4(1);
		testPC.setGame("player1", "player2");
		testBoard.setBoardWidth(BOARD_WIDTH);
		testBoard.setBoardHeight(BOARD_HEIGHT);
		testBoard.setBoard(BOARD_WIDTH, BOARD_HEIGHT);
		testPC.setBoard(testBoard);

		int[] selectedMoves = new int[SELECTED_MOVES_TWO];
		selectedMoves = testAI.selectMove(testPC);

		int testRow = selectedMoves[0];
		int testCol = selectedMoves[1];

		if(testPC.getGame().checkValid(testRow, testCol, testPC.getGame().getPlayer(PLAYER_TWO)) == true) {
			System.out.println("OthHardAI.selectMove Evaluated: Correct");
		}
		else {
			System.out.println("OthHardAI.selectMove Evaluated: Incorrect");
		}
		
		/*
		 * Test Two
		 * Calling OthHardAI.selectMove on an Oth Board with non-default starting state.
		 */
		ProgramController testPC2 = new ProgramController();
		C4AndOthelloBoardStore testBoard2 = new C4AndOthelloBoardStore();
		OthHardAI testAI2 = new OthHardAI();
		testPC2.setIsC4(1);
		testPC2.setGame("player1", "player2");
		testBoard2.setBoardWidth(BOARD_WIDTH);
		testBoard2.setBoardHeight(BOARD_HEIGHT);
		testBoard2.setBoard(BOARD_WIDTH, BOARD_HEIGHT);
		testBoard2.setPiece2(new Piece("Black"), TEST_POSITION_FIVE, TEST_POSITION_FOUR);
		testBoard2.setPiece2(new Piece("White"), TEST_POSITION_FIVE, TEST_POSITION_FIVE);
		testPC2.setBoard(testBoard2);

		int[] selectedMoves2 = new int[SELECTED_MOVES_TWO];
		selectedMoves2 = testAI2.selectMove(testPC2);

		int testRow2 = selectedMoves2[0];
		int testCol2 = selectedMoves2[1];

		if(testPC.getGame().checkValid(testRow2, testCol2, testPC2.getGame().getPlayer(PLAYER_TWO)) == true) {
			System.out.println("OthHardAI.selectMove Evaluated: Correct");
		}
		else {
			System.out.println("OthHardAI.selectMove Evaluated: Incorrect");
		}
	}

}

