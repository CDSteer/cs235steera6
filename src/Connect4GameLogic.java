/**
 * @author I.C. Skinner
 * @date 18 Feb '14
 * @see AbstractGameImplementation.java
 * @brief This class controls the Connect4 logic.
 * @details This class controls the 'flow' of the game, specific to Connect4 such as
 *	checking if there are 4 of the same coloured pieces in a vertical,
 *	horizontal or diagonal line and setting the winning player accordingly.
 *
 */

public class Connect4GameLogic extends AbstractGameImplementation{

	/**
	 * connect 4 constructor
	 */
	public Connect4GameLogic(){
		/* Set the board given the symbolic constants */
    this.setBoard(BOARD_WIDTH, BOARD_HEIGHT);
		/* Set player 1 piece colour to "Red" */
    getPlayer(PLAYER_ONE).setColour(PLAYER_ONE_PIECE_COLOUR);
		/* Set player 2 piece colour to "Yellow" */
    getPlayer(PLAYER_TWO).setColour(PLAYER_TWO_PIECE_COLOUR);
  }

	/**
	 *	A 'get' (access) method for the winning move.
	 *	@return	m_Winning_Move	Integer representing a winning connect4 move.
	 */
	public int getWinningMove(){
		return m_Winning_Move;
	}

	/**
	 *	A setter method for the winning move.
	 *	@param	winningMove	Integer representing a winning connect4 move.
	 *	@return null
	 */
	private void setWinningMove(int winningMove){
		m_Winning_Move = winningMove;
	}

	/**
	 *	A 'get' (access) method for the winning column (i) position
	 * 	@return	m_Winning_i	Row position of a winning piece.
	 */
	public int getWinningi(){
		return m_Winning_i;
	}

    /**
	 *	A setter method for the winning column (i) position
	 *  @param i Row position of a winning piece.
	 *	@return null
	 */
	private void setWinningi(int i){
		m_Winning_i = i;
	}

	/**
	 *	A 'get' (access) method for the winning row (j) position
	 *	@return	j Column position of a winning piece.
	 */
	public int getWinningj(){
		return m_Winning_j;
	}

	/**
	 *	A setter method for the winning row (j) position
	 *	@param j Column position of a winning piece.
	 *	@return null
	 */
	private void setWinningj(int j){
		m_Winning_j = j;
	}

	/**
	 *	A 'get' (access) method to get the piece at coordinates (column,row).
	 *	@return	Piece Existing or new Piece if position is not empty or empty.
	 */
	public Piece getPiece(int column, int row) {
		if (getBoard().isEmpty(column,row) == true) {
			return new Piece("");
		} else {
			return getBoard().getBoard()[column][row];
		}
	}

	/**
	 *	A setter method which sets a piece object at a position.
	 *	@param	column Column position on a board.
	 *	@param	row Row Position on a board.
	 *	@param	player Player object which is the player setting a piece.
	 *	@return null
	 */
	public void setPiece(int column, int row, AbstractPlayer player){
        System.out.println("Connect4::setPiece()");
        int rowIndex = BOARD_HEIGHT-1;
        boolean columnNotFull = true;
        while(columnNotFull){

			/* Column is full, don't set piece. (Shouldn't be reachable) */
            if(rowIndex < TOP_ROW){
                columnNotFull = false;

			/* Set the piece at board position [column][rowIndex]*/
            }else if(getBoard().isEmpty(column,rowIndex) == true){
                String playerPiece;
                if(player.getColour().equals(PLAYER_ONE_PIECE_COLOUR)){
                    playerPiece = PLAYER_ONE_PIECE_COLOUR;
				}else{
                    playerPiece = PLAYER_TWO_PIECE_COLOUR;
				}
                (getBoard()).setPiece(new Piece(playerPiece), column, rowIndex);
                columnNotFull = false;

			/* Do nothing which should never be achieved */
            }else{}
            rowIndex--;
        }
    }

	/**
	 *	A setter method which sets the winner games winner.
	 *	@param	p Piece object used by the user.
	 *	@return null
	 */
	public void setWinner(Piece p) {
		if (getPlayer(PLAYER_ONE).getColour().equals(p.getColour())) {
			super.setWinner(PLAYER_ONE_PIECE);
		} else if (getPlayer(PLAYER_TWO).getColour().equals(p.getColour())) {
			super.setWinner(PLAYER_TWO_PIECE);
		}
	}

	/**
	 *	CheckTakeableTurn is a method used primarily for Othello however this
	 *	override is necessary so that the moment the player enters their
	 *	winning piece the game is done and doesn't require an additional click
	 *	to display winner.
	 *	@param	player Player object which is the player to take a turn.
	 *	@return boolean Returns true or false depending on conditions.
	 */
	public boolean checkTakeableTurn(AbstractPlayer player) {
		if(checkWin() == true){
			return false;
		}else{
			return true;
		}
	}


	/**
	 * 	checkValid method checks to see if the players attempted move is valid.
	 *	@param	column Column position of the attempted played piece.
	 *	@param	row	Row position of the attempted played piece.
	 *	@param	player Player object which is the player attempting a move.
	 *	@return	boolean	Returns true of false based depending on conditions.
	 */
	public boolean checkValid(int column, int row, AbstractPlayer player){
        System.out.println("Connect4::checkValid()");

		/* if the top of the column is not empty then the move is invalid */
        if((getBoard()).isEmpty(column,TOP_ROW) == false){
			System.out.println("Invalid move");
            return false;
        }else{

            return true;
        }
    }

	/**
	 * 	checkWin method reads the data on the board and determines if there's a winner
	 *	  in 1 of 4 ways:
	 *		-- Horizontal connect 4
	 *		-- Vertical connect 4
	 *		-- Diagonal right-facing connect 4
	 *		-- Diagonal left-facing connect 4
	 *	@return	boolean	Returns true or false if there is a winner or not.
	 */
    public boolean checkWin(){
        int j;
        int i;

        for(j = 0; j<(BOARD_HEIGHT); j++){
            for(i = 0; i<(BOARD_WIDTH-BOARD_BOUNDARY); i++){ /* horizontal win */
                if(getPiece(i,j).getColour() == getPiece(i+COORDINATE_ONE,j).getColour() &&
                   getPiece(i,j).getColour() == getPiece(i+COORDINATE_TWO,j).getColour() &&
                   getPiece(i,j).getColour() == getPiece(i+COORDINATE_THREE,j).getColour() &&
                   getBoard().isEmpty(i,j) == false){
					setWinner(getPiece(i,j));
					setWinningi(i);
					setWinningj(j);
					setWinningMove(HORIZONTAL_WIN);
					return true;
                }
			}
		}

		for(i = 0; i<(BOARD_WIDTH); i++){ /* vertical win */
			for(j = 0; j<(BOARD_HEIGHT-BOARD_BOUNDARY); j++){
				if(getPiece(i,j).getColour() == getPiece(i,j+COORDINATE_ONE).getColour() &&
                   getPiece(i,j).getColour() == getPiece(i,j+COORDINATE_TWO).getColour() &&
                   getPiece(i,j).getColour() == getPiece(i,j+COORDINATE_THREE).getColour() &&
                   getBoard().isEmpty(i,j) == false){
					setWinner(getPiece(i,j));
					setWinningi(i);
					setWinningj(j);
					setWinningMove(VERTICAL_WIN);
					return true;
				}
			}
		}

		for(j = 0; j<(BOARD_HEIGHT-BOARD_BOUNDARY); j++){ /* diagonal right facing win */
			for(i = 0; i<(BOARD_WIDTH-BOARD_BOUNDARY); i++){
				if(getPiece(i,j).getColour() == getPiece(i+COORDINATE_ONE,j+COORDINATE_ONE).getColour() &&
				   getPiece(i,j).getColour() == getPiece(i+COORDINATE_TWO,j+COORDINATE_TWO).getColour() &&
				   getPiece(i,j).getColour() == getPiece(i+COORDINATE_THREE,j+COORDINATE_THREE).getColour() &&
                   getBoard().isEmpty(i,j) == false){
					setWinner(getPiece(i,j));
					setWinningi(i);
					setWinningj(j);
					setWinningMove(RIGHT_DIAGONAL_WIN);
					return true;
				}
			}
		}

		for(j = 0; j<(BOARD_HEIGHT-BOARD_BOUNDARY); j++){ /* diagonal left facing win */
			for(i = COORDINATE_THREE; i<(BOARD_WIDTH); i++){
				if(getPiece(i,j).getColour() == getPiece(i-COORDINATE_ONE,j+COORDINATE_ONE).getColour() &&
				   getPiece(i,j).getColour() == getPiece(i-COORDINATE_TWO,j+COORDINATE_TWO).getColour() &&
                   getPiece(i,j).getColour() == getPiece(i-COORDINATE_THREE,j+COORDINATE_THREE).getColour() &&
                   getBoard().isEmpty(i,j) == false){
					setWinner(getPiece(i,j));
					setWinningi(i);
					setWinningj(j);
					setWinningMove(LEFT_DIAGONAL_WIN);
					return true;
				}
			}
		}

		boolean boardFull = isBoardFull(BOARD_WIDTH, BOARD_HEIGHT);
		if(boardFull == true){
			super.setWinner(DRAW); /* draw if board is full */
		}else{}
		return boardFull;
	}

    
    public static void main(String[] args){

        final int TOO_BIG = 42;
        final int TOO_SMALL = -42;
        final int ROW_SIX = 6;

		Connect4GameLogic c4gamelogic = new Connect4GameLogic();
		AbstractPlayer testPlayer = new Human();
		testPlayer.setColour("Red");
		
		c4gamelogic.setPiece(0, 0, testPlayer);
		if(c4gamelogic.getBoard().getBoard()[0][ROW_SIX].getColour().equals("Red")){
			System.out.println("Connect4GameLogic::testSetPieceTrue has been successful");
		}else{
			System.out.println("Connect4GameLogic::testSetPieceTrue has not been successful");
		}
		
		testPlayer.setColour("Red");

		Piece testPiece = new Piece("Yellow");
		
		/* 
		 * Set the top piece of the board to yellow. This is a quasi-hack because
		 * now the top piece is occupied whilst everyone below it isn't and that 
		 * wouldn't happen in the running implementation but it suffices here
		 * for test purposes.
		 * 
		 */
		 
		 
		c4gamelogic.getBoard().setPiece(testPiece, 0, 0);
		
		c4gamelogic.setPiece(0, 0, testPlayer);
		
		/* 
		 * If top piece is still yellow then setPiece did nothing which is correct.
		 * Basically our implementation tests if a move is valid before setting a piece
		 * and so won't set a piece if it's not valid anyway but we wanted to include
		 * a test to show this.
		 * 
		 */
		 
		 
		if(c4gamelogic.getBoard().getBoard()[0][0].getColour().equals("Yellow")){
			System.out.println("Connect4GameLogic::testSetPieceFalse has been successful");
		}else{
			System.out.println("Connect4GameLogic::testSetPieceFalse has not been successful");
		}
		
	
		
		try{
		
			c4gamelogic.setPiece(TOO_BIG, TOO_BIG, testPlayer);
		
		}catch(Exception e){
			System.out.println("Connect4GameLogic::testSetPiecesTooBig()");
		}
		
		//try to set pieces that are too small
		try{

			c4gamelogic.setPiece(TOO_SMALL, TOO_SMALL, testPlayer);
			
		} catch (Exception e){
			System.out.println("Connect4GameLogic::testSetPieceTooSmall()");
		}
		
		try{
			
			c4gamelogic.checkValid(0, 0, testPlayer);
		
		} catch(Exception e){
			System.out.println("Connect4GameLogic::testMoveIsValidTrue()");
		}
		
		c4gamelogic.getBoard().setPiece(testPiece, 0, 0);
		
		if((c4gamelogic.checkValid(0, 0, testPlayer)==(false))){
			System.out.println("Connect4GameLogic::testMoveIsValidFalse()");
		}
		
		try{
			c4gamelogic.checkValid(TOO_BIG, TOO_BIG, testPlayer);
		}catch(Exception e){
			System.out.println("Connect4GameLogic::testMoveIsValidTooBig(");
		}
		
		try{
			c4gamelogic.checkValid(TOO_SMALL, TOO_SMALL, testPlayer);
		}catch(Exception e){
			System.out.println("Connect4GameLogic::testMoveIsValidTooSmall()");
		}
		
		
		Piece testpiece = new Piece("Red");
		
		if(c4gamelogic.checkWin()==true){
			c4gamelogic.getBoard().setPiece(testPiece, 0, 6);
			c4gamelogic.getBoard().setPiece(testPiece, 0, 5);
			c4gamelogic.getBoard().setPiece(testPiece, 0, 4);
			c4gamelogic.getBoard().setPiece(testPiece, 0, 3);
			System.out.println("Connect4GameLogic::checkWinVertical() succeeded");
		}else{
			System.out.println("Connect4GameLogic::checkWinVertical() failed");
		}
		
		if(c4gamelogic.checkWin()==true){
			c4gamelogic.getBoard().setPiece(testPiece, 0, 6);
			c4gamelogic.getBoard().setPiece(testPiece, 1, 6);
			c4gamelogic.getBoard().setPiece(testPiece, 2, 6);
			c4gamelogic.getBoard().setPiece(testPiece, 3, 6);
			System.out.println("Connect4GameLogic::checkWinHorizontal() succeeded");
		}else{
			System.out.println("Connect4GameLogic::checkWinHorizontal() failed"); 
		}
		
		if(c4gamelogic.checkWin()==true){
			c4gamelogic.getBoard().setPiece(testPiece, 0, 3);
			c4gamelogic.getBoard().setPiece(testPiece, 1, 4);
			c4gamelogic.getBoard().setPiece(testPiece, 1, 4);
			c4gamelogic.getBoard().setPiece(testPiece, 1, 4);
			System.out.println("Connect4GameLogic::checkWinRightDiagonal succeeded");
		}else{
			System.out.println("Connect4GameLogic::checkWinRightDiagonal failed");
		}
		
		if(c4gamelogic.checkWin()==true){
			c4gamelogic.getBoard().setPiece(testPiece, 9, 3);
			c4gamelogic.getBoard().setPiece(testPiece, 8, 4);
			c4gamelogic.getBoard().setPiece(testPiece, 7, 5);
			c4gamelogic.getBoard().setPiece(testPiece, 6, 6);
			System.out.println("Connect4GameLogic::checkWinLefttDiagonal succeeded");
		}else{
			System.out.println("Connect4GameLogic::checkWinLeftDiagonal failed");
		}
		
		if(c4gamelogic.checkWin()==false){
			System.out.println("C4GameLogic::checkWinFalse() has succeeded");
		}else{
			System.out.println("C4GameLogic::checkWinFalse() has failed");
		}
		
		int x;
		Piece testPiece1 = new Piece("Red");
		Piece testPiece2 = new Piece("Yellow");
		
		for(x = 0; x < c4gamelogic.getBoard().getBoardWidth(); x = x + 4){
			for(int y = 0; y < c4gamelogic.getBoard().getBoardHeight(); y++){
				if((y % 2) == 0){
					c4gamelogic.getBoard().setPiece(testPiece1, x, y);
					c4gamelogic.getBoard().setPiece(testPiece1, x + 1, y);
				}else{
					c4gamelogic.getBoard().setPiece(testPiece2, x, y);
					c4gamelogic.getBoard().setPiece(testPiece2, x + 1, y);
				}
			}	
		}
		
		for(x = 2; x < c4gamelogic.getBoard().getBoardWidth(); x = x + 4){
			for(int y = 0; y < c4gamelogic.getBoard().getBoardHeight(); y++){
				if((y % 2) == 0){
					c4gamelogic.getBoard().setPiece(testPiece2, x, y);
					c4gamelogic.getBoard().setPiece(testPiece2, x + 1, y);
				}else{
					c4gamelogic.getBoard().setPiece(testPiece1, x, y);
					c4gamelogic.getBoard().setPiece(testPiece1, x + 1, y);
				}
			}
		}
		
		if(c4gamelogic.checkWin()==false){
			System.out.println("Connect4GameLogic::checkWinDraw() succeeded");
		}else{
			System.out.println("Connect4GameLogic::checkWinDraw() failed");
		}
		
		
    }
    

	//Member variables to store the winning move and position
	private int m_Winning_Move;
	private int m_Winning_i;
	private int m_Winning_j;

	// Symbolic constants
	private final int DRAW = 3;

	private final int TOP_ROW = 0;

	private final int PLAYER_ONE = 0;
	private final int PLAYER_TWO = 1;

	private final int HORIZONTAL_WIN = 0;
	private final int VERTICAL_WIN = 1;
	private final int RIGHT_DIAGONAL_WIN = 2;
	private final int LEFT_DIAGONAL_WIN = 3;

	private final int PLAYER_ONE_PIECE = 1;
	private final String PLAYER_ONE_PIECE_COLOUR = "Red";
	private final int PLAYER_TWO_PIECE = 2;
	private final String PLAYER_TWO_PIECE_COLOUR = "Yellow";

  private final int BOARD_HEIGHT = 7;
  private final int BOARD_WIDTH = 10;
  private final int BOARD_BOUNDARY = 3;
  private final int COORDINATE_ONE = 1;
  private final int COORDINATE_TWO = 2;
  private final int COORDINATE_THREE = 3;

}
