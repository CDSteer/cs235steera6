/**
 * @author Cameron Steer
 * @date 18 Feb '14
 * @see AbstractGameImplementation.java
 * @brief This class controls the TicTacToe^2 logic.
 * @details This class controls the 'flow' of the game, specific to TicTacToe^2 such as
 *  checking if there are 4 of the same pieces in a vertical,
 *  horizontal or diagonal line and setting the winning player accordingly.
 *
 */

public class TicTacToeLogic extends AbstractGameImplementation {

  //Member variables to store the winning move and position
  private int m_Winning_Move;
  private int m_Winning_i;
  private int m_Winning_j;

  /** Constructor for TicTacToe^2 */
  public TicTacToeLogic() {
    this.setBoard(BOARD_WIDTH, BOARD_HEIGHT);
    getPlayer(0).setColour(O_PIECE);
    getPlayer(1).setColour(X_PIECE);
  }

  /**
   *  A setter method which sets the winner games winner.
   *  @param  p Piece object used by the user.
   *  @return null
   */
  public void setWinner(Piece p) {
    if (getPlayer(PLAYER_ONE).getColour().equals(p.getColour())) {
      super.setWinner(PLAYER_ONE_PIECE);
    } else if (getPlayer(PLAYER_TWO).getColour().equals(p.getColour())) {
      super.setWinner(PLAYER_TWO_PIECE);
    }
  }

  /**
   *  checkValid method checks to see if the players attempted move is valid.
   *  @param  column Column position of the attempted played piece.
   *  @param  row Row position of the attempted played piece.
   *  @param  player Player object which is the player attempting a move.
   *  @return boolean Returns true of false based depending on conditions.
   */
  public boolean checkValid(int column, int row, AbstractPlayer player){
    System.out.println("TicTacToe^2::checkValid()");
    /* if the top of the column is not empty then the move is invalid */
    if(getBoard().isEmpty(column,row) == true){
      System.out.println("Invalid move");
      return true;
    }else{
      return false;
    }
  }

  /**
   *  A 'get' (access) method for the winning move.
   *  @return m_Winning_Move  Integer representing a winning connect4 move.
   */
  public int getWinningMove(){
    return m_Winning_Move;
  }

  /**
   *  A setter method for the winning move.
   *  @param  winningMove Integer representing a winning connect4 move.
   *  @return null
   */
  private void setWinningMove(int winningMove){
    m_Winning_Move = winningMove;
  }

  /**
   *  A 'get' (access) method for the winning column (i) position
   *  @return m_Winning_i Row position of a winning piece.
   */
  public int getWinningi(){
    return m_Winning_i;
  }

    /**
   *  A setter method for the winning column (i) position
   *  @param i Row position of a winning piece.
   *  @return null
   */
  private void setWinningi(int i){
    m_Winning_i = i;
  }

  /**
   *  A 'get' (access) method for the winning row (j) position
   *  @return j Column position of a winning piece.
   */
  public int getWinningj(){
    return m_Winning_j;
  }

  /**
   *  A setter method for the winning row (j) position
   *  @param j Column position of a winning piece.
   *  @return null
   */
  private void setWinningj(int j){
    m_Winning_j = j;
  }

  public void setPiece(int x, int y, AbstractPlayer player) {
    System.out.println("TicTacToeLogic::setPiece()");
    String playerColour = player.getColour();
    getBoard().setPiece(new Piece(playerColour), x, y);
  }

  /**
   *  A 'get' (access) method to get the piece at coordinates (column,row).
   *  @return Piece Existing or new Piece if position is not empty or empty.
   */
  public Piece getPiece(int column, int row) {
    if (getBoard().isEmpty(column,row) == true) {
      return new Piece("");
    } else {
      return getBoard().getBoard()[column][row];
    }
  }

  /**
   *  checkWin method reads the data on the board and determines if there's a winner
   *    in 1 of 4 ways:
   *    -- Horizontal connect 4
   *    -- Vertical connect 4
   *    -- Diagonal right-facing connect 4
   *    -- Diagonal left-facing connect 4
   *  @return boolean Returns true or false if there is a winner or not.
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

    TicTacToeLogic ticTacToeLogic = new TicTacToeLogic();
    AbstractPlayer testPlayer = new Human();
    testPlayer.setColour("O");

    ticTacToeLogic.setPiece(0, 0, testPlayer);
    if(ticTacToeLogic.getBoard().getBoard()[0][ROW_SIX].getColour().equals("O")){
      System.out.println("TicTacToeLogic::testSetPieceTrue has been successful");
    }else{
      System.out.println("TicTacToeLogic::testSetPieceTrue has not been successful");
    }

    testPlayer.setColour("O");

    Piece testPiece = new Piece("X");

    /*
     * Set the top piece of the board to X. This is a quasi-hack because
     * now the top piece is occupied whilst everyone below it isn't and that
     * wouldn't happen in the running implementation but it suffices here
     * for test purposes.
     *
     */


    ticTacToeLogic.getBoard().setPiece(testPiece, 0, 0);

    ticTacToeLogic.setPiece(0, 0, testPlayer);

    /*
     * If top piece is still X then setPiece did nothing which is correct.
     * Basically our implementation tests if a move is valid before setting a piece
     * and so won't set a piece if it's not valid anyway but we wanted to include
     * a test to show this.
     *
     */


    if(ticTacToeLogic.getBoard().getBoard()[0][0].getColour().equals("X")){
      System.out.println("TicTacToeLogic::testSetPieceFalse has been successful");
    }else{
      System.out.println("TicTacToeLogic::testSetPieceFalse has not been successful");
    }



    try{

      ticTacToeLogic.setPiece(TOO_BIG, TOO_BIG, testPlayer);

    }catch(Exception e){
      System.out.println("TicTacToeLogic::testSetPiecesTooBig()");
    }

    //try to set pieces that are too small
    try{

      ticTacToeLogic.setPiece(TOO_SMALL, TOO_SMALL, testPlayer);

    } catch (Exception e){
      System.out.println("TicTacToeLogic::testSetPieceTooSmall()");
    }

    try{

      ticTacToeLogic.checkValid(0, 0, testPlayer);

    } catch(Exception e){
      System.out.println("TicTacToeLogic::testMoveIsValidTrue()");
    }

    ticTacToeLogic.getBoard().setPiece(testPiece, 0, 0);

    if((ticTacToeLogic.checkValid(0, 0, testPlayer)==(false))){
      System.out.println("TicTacToeLogic::testMoveIsValidFalse()");
    }

    try{
      ticTacToeLogic.checkValid(TOO_BIG, TOO_BIG, testPlayer);
    }catch(Exception e){
      System.out.println("TicTacToeLogic::testMoveIsValidTooBig(");
    }

    try{
      ticTacToeLogic.checkValid(TOO_SMALL, TOO_SMALL, testPlayer);
    }catch(Exception e){
      System.out.println("TicTacToeLogic::testMoveIsValidTooSmall()");
    }


    Piece testpiece = new Piece("O");

    if(ticTacToeLogic.checkWin()==true){
      ticTacToeLogic.getBoard().setPiece(testPiece, 0, 6);
      ticTacToeLogic.getBoard().setPiece(testPiece, 0, 5);
      ticTacToeLogic.getBoard().setPiece(testPiece, 0, 4);
      ticTacToeLogic.getBoard().setPiece(testPiece, 0, 3);
      System.out.println("TicTacToeLogic::checkWinVertical() succeeded");
    }else{
      System.out.println("TicTacToeLogic::checkWinVertical() failed");
    }

    if(ticTacToeLogic.checkWin()==true){
      ticTacToeLogic.getBoard().setPiece(testPiece, 0, 6);
      ticTacToeLogic.getBoard().setPiece(testPiece, 1, 6);
      ticTacToeLogic.getBoard().setPiece(testPiece, 2, 6);
      ticTacToeLogic.getBoard().setPiece(testPiece, 3, 6);
      System.out.println("TicTacToeLogic::checkWinHorizontal() succeeded");
    }else{
      System.out.println("TicTacToeLogic::checkWinHorizontal() failed");
    }

    if(ticTacToeLogic.checkWin()==true){
      ticTacToeLogic.getBoard().setPiece(testPiece, 0, 3);
      ticTacToeLogic.getBoard().setPiece(testPiece, 1, 4);
      ticTacToeLogic.getBoard().setPiece(testPiece, 1, 4);
      ticTacToeLogic.getBoard().setPiece(testPiece, 1, 4);
      System.out.println("TicTacToeLogic::checkWinRightDiagonal succeeded");
    }else{
      System.out.println("TicTacToeLogic::checkWinRightDiagonal failed");
    }

    if(ticTacToeLogic.checkWin()==true){
      ticTacToeLogic.getBoard().setPiece(testPiece, 9, 3);
      ticTacToeLogic.getBoard().setPiece(testPiece, 8, 4);
      ticTacToeLogic.getBoard().setPiece(testPiece, 7, 5);
      ticTacToeLogic.getBoard().setPiece(testPiece, 6, 6);
      System.out.println("TicTacToeLogic::checkWinLefttDiagonal succeeded");
    }else{
      System.out.println("TicTacToeLogic::checkWinLeftDiagonal failed");
    }

    if(ticTacToeLogic.checkWin()==false){
      System.out.println("ticTacToeLogic::checkWinFalse() has succeeded");
    }else{
      System.out.println("ticTacToeLogic::checkWinFalse() has failed");
    }

    int x;
    Piece testPiece1 = new Piece("O");
    Piece testPiece2 = new Piece("X");

    for(x = 0; x < ticTacToeLogic.getBoard().getBoardWidth(); x = x + 4){
      for(int y = 0; y < ticTacToeLogic.getBoard().getBoardHeight(); y++){
        if((y % 2) == 0){
          ticTacToeLogic.getBoard().setPiece(testPiece1, x, y);
          ticTacToeLogic.getBoard().setPiece(testPiece1, x + 1, y);
        }else{
          ticTacToeLogic.getBoard().setPiece(testPiece2, x, y);
          ticTacToeLogic.getBoard().setPiece(testPiece2, x + 1, y);
        }
      }
    }

    for(x = 2; x < ticTacToeLogic.getBoard().getBoardWidth(); x = x + 4){
      for(int y = 0; y < ticTacToeLogic.getBoard().getBoardHeight(); y++){
        if((y % 2) == 0){
          ticTacToeLogic.getBoard().setPiece(testPiece2, x, y);
          ticTacToeLogic.getBoard().setPiece(testPiece2, x + 1, y);
        }else{
          ticTacToeLogic.getBoard().setPiece(testPiece1, x, y);
          ticTacToeLogic.getBoard().setPiece(testPiece1, x + 1, y);
        }
      }
    }

    if(ticTacToeLogic.checkWin()==false){
      System.out.println("TicTacToeLogic::checkWinDraw() succeeded");
    }else{
      System.out.println("TicTacToeLogic::checkWinDraw() failed");
    }
  }

  private final int PLAYER_ONE = 0;
  private final int PLAYER_TWO = 1;

  private final int PLAYER_ONE_PIECE = 1;
  private final String PLAYER_ONE_PIECE_COLOUR = "O";
  private final int PLAYER_TWO_PIECE = 2;
  private final String PLAYER_TWO_PIECE_COLOUR = "X";

  private final int PLAYER_ONE_WIN = 1;
  private final int PLAYER_TWO_WIN = 2;
  private final int CONTINUE_GAME = -1;
  private final int DRAW = 3;

  private final int HORIZONTAL_WIN = 0;
  private final int VERTICAL_WIN = 1;
  private final int RIGHT_DIAGONAL_WIN = 2;
  private final int LEFT_DIAGONAL_WIN = 3;

  private final int NUMBER_OF_PLAYERS = 2;
  private final int DIVIDED_BY_TWO = 2;
  private final String O_PIECE = "O";
  private final String X_PIECE = "X";

  private final int BOARD_HEIGHT = 8;
  private final int BOARD_WIDTH = 8;
  private final int BOARD_BOUNDARY = 3;
  private final int COORDINATE_ONE = 1;
  private final int COORDINATE_TWO = 2;
  private final int COORDINATE_THREE = 3;
}