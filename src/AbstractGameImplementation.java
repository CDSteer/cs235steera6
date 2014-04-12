/**
 * @author I.C. Skinner
 * @date 18 Feb '14
 * @brief This class is inherited by Connect4 and Othello.
 * @details This class controls the "flow" of the game such as creating the
 * board, instantiating the players and creating the methods to be
 * inherited by both games.
 *
 */

abstract public class AbstractGameImplementation {

	/**
	 *	A 'get' (access) method which is used in Connect 4
	 *	@return	0	Return value is changed by subclass.
	 */
	public int getWinningMove(){
		return 0;
	}

    /**
	 *	A 'get' (access) method which is used in Connect 4
	 *	@return	0	Return value is changed by subclass.
	 */
	public int getWinningi(){
		return 0;
	}

	/**
	 *	A 'get' (access) method which is used in Connect 4
	 *	@return	0	Return value is changed by subclass.
	 */
	public int getWinningj(){
		return 0;
	}

	/**
	 *	A 'get' (access) method which is used by both subclasses.
	 *	@return	m_Winner	Integer representation of the games winner.
	 */
	public int getWinner() {
		return m_Winner;
    }

	/**
	 *	A setter method which is used in Connect 4.
	 *	@param	playerNumber	Integer representation of a player.
	 *	@return m_Winner		Returns variable m_Winner to be the winner.
	 */
	public int setWinner(int playerNumber) {
		m_Winner = playerNumber;
		return m_Winner;
	}

	/** A setter method inherited and changed by both subclasses.
	 *	@param	x		Column value.
	 *	@param	y		Row value.
	 *	@param	player	Player which wants to set the Piece.
	 *	@return null
	 */
    public void setPiece(int x, int y, AbstractPlayer player){}

    public abstract Piece getPiece(int y, int x);

	/** Method to get the board object
	*	@return m_Board		Return the current board object
	*
	*/
	public C4AndOthelloBoardStore getBoard(){
        return m_Board;
    }

	/**
	 *	A setter method to instantiate the board object, this method is called
	 *	in both games constructor.
	 *	@param	boardWidth	The Board objects width.
	 *	@param	boardHeight	The Board objects height.
	 *	@return null
	 */
    public void setBoard(int boardWidth, int boardHeight) {
        System.out.println("GameImplementation::setBoard()");
        m_Board = new C4AndOthelloBoardStore();
        m_Board.setBoard(boardWidth, boardHeight);

    }
    public void setBoard(C4AndOthelloBoardStore board){
        m_Board = board;
    }

	/**
	 *	A 'get' (access) method to get player 1 or player 2.
	 *	@param	playerNumber	Integer representation of a Player.
	 *	@return	Player			Returns the Player object from the array at
	 *							index playerNumber.
	 */
	public AbstractPlayer getPlayer(int playerNumber){
        return m_Player[playerNumber];
    }

	/**
	 *	A setter method to set the players, this method is called in the
	 *	GameImplementation constructor.
	 *	@param	playerNumber	Integer representation of a Player.
	 *	@param	player			Player object.
	 *	@return null
	 */
	private void setPlayer(int playerNumber, AbstractPlayer player) {
		m_Player[playerNumber] = player;
	}

	/**
	 *	GameImplementation constructor. Initially calls setPlayer to
	 *	instantiate the games players.
	 */
	public AbstractGameImplementation() {
		setPlayer(PLAYER_ONE, new Human());
		setPlayer(PLAYER_TWO, new Human());
	}

	/**
	 *	Method to check if either games board is full.
	 *	@param	boardWidth	The Board width.
	 *	@param	boardHeight	The Board height.
	 *	@return	boolean		Returns true or false if board is full or not.
	 */
    public boolean isBoardFull(int boardWidth, int boardHeight) {
        boolean boardFull = true;
            for (int i = 0; i < boardWidth; i++) {
                for (int j = 0; j < boardHeight; j++) {
                    if (m_Board.isEmpty(i,j) == true) {
                        boardFull = false;
                    }
				}
            }
        return boardFull;
    }

	/**
	 * 	Method to check is a turn is 'take-able', necessary for Othello.
	 *	@param	player	A Player object whose turn is to be taken.
	 *	@return	boolean	Return value is changed by subclass.
	 */
	public boolean checkTakeableTurn(AbstractPlayer player) {
		return true;
	}

    /**
	 *	Method to check if a players move is valid.
	 *	@param	column	Column value of the Player move.
	 *	@param	row		Row value of the Player move.
	 *	@return	boolean	Return value is changed by subclass.
	 */
	public boolean checkValid(int column, int row, AbstractPlayer player){
        return false;
    }

	/**
	 *	Method to check to see if the game has been won by a Player.
	 *	@return	boolean	Return value is changed by subclass.
	 */
    public boolean checkWin(){
        return false;
    }

	/* Symbolic constants*/
	public final int EMPTY_SQUARE = 0;
	private final int PLAYER_ONE = 0;
	private final int PLAYER_TWO = 1;
	private final int NUMBER_OF_PLAYERS = 2;

	/*
		Member variables to store an array of the players, a board object
		and the winner.
	*/
    private final AbstractPlayer[] m_Player  = new AbstractPlayer[NUMBER_OF_PLAYERS];
    private C4AndOthelloBoardStore m_Board = new C4AndOthelloBoardStore();
    private int m_Winner;
}
