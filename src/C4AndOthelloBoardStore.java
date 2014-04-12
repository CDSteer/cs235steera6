/**
 * @author X. Chen, Tested by Curtis Lewis
 * @date 22 Feb '14
 * @brief Stores information about a Piece object.
 * @details Stores a piece object in a specific board position by having 2 identical
 * 2D arrays where one stores an integer which corresponds to the other having
 * a piece at the same index location.
 *
 */
public class C4AndOthelloBoardStore {

    /** Method to get the 2D array (board) of pieces */
    public Piece[][] getBoard() {
        return m_Pieces;
    }

    public Piece getPiece(int i, int j){
        return m_Pieces[i][j];
    }

    /**
     * Returns the heiht of the board
     * @return the m_BoardHeight
     */
    public int getBoardHeight(){
        return m_BoardHeight;
    }

    /** Method to set the boards height */
    public void setBoardHeight(int boardHeight){
        m_BoardHeight = boardHeight;
    }

    /**
     * Returns the width of the board
     * @return the m_BoardWidth
     */
    public int getBoardWidth(){
        return m_BoardWidth;
    }

    /** Method to set the boards width */
    public void setBoardWidth(int boardWidth){
        m_BoardWidth = boardWidth;
    }


    /**
     *  Method to "setBoard" which instantiate both 2D arrays
     *  Initializes board for given board size values
     *  @param set the the Width of the board
     *  @param set the the height of the board
     */
    public void setBoard(int boardWidth, int boardHeight) {
        setBoardHeight(boardHeight);
        setBoardWidth(boardWidth);
        m_Board = new int[boardWidth][boardHeight];
        m_Pieces = new Piece[boardWidth][boardHeight];
        for(int j = 0; j < boardHeight; j++){
            for(int i = 0; i < boardWidth; i++){
                m_Pieces[i][j] = new Piece(" ");
            }
        }
    }

    /**
     * Method to set a piece in both arrays
     * initializes piece for given position values and colour
     * @param set the the colour of the piece
     * @param set the the row number of the piece
     * @param set the the column number of the piece
     */
    public void setPiece(Piece piece, int column, int row) {
        System.out.println("Board::setPiece()");
        if(m_Board[column][row] != HAS_PIECE_OBJECT){
            m_Board[column][row] = HAS_PIECE_OBJECT;
            m_Pieces[column][row] = piece;
        }
    }

    public void setPiece2(Piece piece, int column, int row) {
        System.out.println("Board::setPiece()");
        m_Board[column][row] = HAS_PIECE_OBJECT;
        m_Pieces[column][row] = piece;
    }
    public void foobar(int i, int j){
        m_Board[i][j] = 1;
    }

    /**
     *  Method to check if a board has a piece object in it i.e. is not empty
     */
    public boolean isEmpty(int column, int row) {
        return m_Board[column][row] != HAS_PIECE_OBJECT;
    }

    public static void main(String[] args) {

            m_BoardStore = new C4AndOthelloBoardStore();
            m_BoardStore.setBoard(NORMAL_BOARD_SIZE, NORMAL_BOARD_SIZE);
            // get the right size values.

        if(m_BoardStore.getBoardWidth() == NORMAL_BOARD_SIZE && m_BoardStore.getBoardHeight() == NORMAL_BOARD_SIZE) {
            System.out.println("C4AndOthelloBoardStore Test One NormalSize Evaluated: Correct");
        } else {
            System.out.println("C4AndOthelloBoardStore Test One NormalSize Evaluated: Incorrect");
        }

        // Set board with no size.

            m_BoardStore = new C4AndOthelloBoardStore();
            m_BoardStore.setBoard(0, 0);
            // get the right size values.

        if(m_BoardStore.getBoardWidth() == 0 && m_BoardStore.getBoardHeight() == 0) {
            System.out.println("C4AndOthelloBoardStore Test Two NoSize Evaluated: Correct");
        } else {
            System.out.println("C4AndOthelloBoardStore Test Two NoSize Evaluated: Incorrect");
        }


        // Set board with sizes very big.

            m_BoardStore = new C4AndOthelloBoardStore();
            m_BoardStore.setBoard(LARGE_BOARD_SIZE, LARGE_BOARD_SIZE);
            // get the right size values.

        if(m_BoardStore.getBoardWidth() == LARGE_BOARD_SIZE && m_BoardStore.getBoardHeight() == LARGE_BOARD_SIZE) {
            System.out.println("C4AndOthelloBoardStore Test Three BigSize Evaluated: Correct");
        } else {
            System.out.println("C4AndOthelloBoardStore Test Three BigSize Evaluated: Incorrect");
        }

        // Set board with negative sizes which is impossible.
        // so there is no board here.
        try {

            m_BoardStore = new C4AndOthelloBoardStore();
            m_BoardStore.setBoard(NEGATIVE_BOARD_SIZE, NEGATIVE_BOARD_SIZE);

            if(m_BoardStore.getBoardWidth() == NEGATIVE_BOARD_SIZE && m_BoardStore.getBoardHeight() == NEGATIVE_BOARD_SIZE) {

                System.out.println("C4AndOthelloBoardStore Test Four MinusSize Evaluated: Correct");
            }

        } catch (Exception e) {
            System.out.println("C4AndOthelloBoardStore Test Four MinusSize Evaluated: Incorrect");
        }

        try {

            // create a board and try to set piece outside the board.

            m_BoardStore = new C4AndOthelloBoardStore();
            m_BoardStore.setBoard(NORMAL_BOARD_SIZE, NORMAL_BOARD_SIZE);
            m_Piece = new Piece("black");
            m_BoardStore.setPiece(m_Piece, OUTSIDE_BOARD, OUTSIDE_BOARD);

            // maybe it just stores the data. no change on the board.

        if(m_BoardStore.getBoard()[OUTSIDE_BOARD][OUTSIDE_BOARD].getColour().equals ("black")) {

            System.out.println("C4AndOthelloBoardStore Test Five OutsideBoard Evaluated: Correct");
        }

        } catch (Exception e) {

            System.out.println("C4AndOthelloBoardStore Test Five OutsideBoard Evaluated: Incorrect");

        }

        try {

            // set piece in a negative position which is not exist.

            m_BoardStore = new C4AndOthelloBoardStore();
            m_BoardStore.setBoard(NORMAL_BOARD_SIZE, NORMAL_BOARD_SIZE);
            m_Piece = new Piece("black");
            m_BoardStore.setPiece(m_Piece, NEGATIVE_BOARD_SIZE, NEGATIVE_BOARD_SIZE);

            // maybe it just stores the data. no change on the board.

        if(m_BoardStore.getBoard()[-1][-1].getColour().equals ("black")) {
            System.out.println("C4AndOthelloBoardStore Test Six NegativeBoard Evaluated: Correct");
        }

        } catch (Exception e) {

            System.out.println("C4AndOthelloBoardStore Test Six NegativeBoard Evaluated: Incorrect");

        }

        // set a black piece on the edge of the board

            m_BoardStore = new C4AndOthelloBoardStore();
            m_BoardStore.setBoard(NORMAL_BOARD_SIZE, NORMAL_BOARD_SIZE);
            m_Piece = new Piece("black");
            m_BoardStore.setPiece(m_Piece, 0, ROW_SEVEN);
            // yeah! there is a black piece!

        if(m_BoardStore.getBoard()[0][ROW_SEVEN].getColour().equals ("black")) {
            System.out.println("C4AndOthelloBoardStore Test Seven BlackOnSide Evaluated: Correct");
        } else {
            System.out.println("C4AndOthelloBoardStore Test Seven BlackOnSide Evaluated: Incorrect");
        }


        /** set a black piece on the board
         *  then set a white piece at the same position
         *  please do not override it !
         */

            m_BoardStore = new C4AndOthelloBoardStore();
            m_BoardStore.setBoard(NORMAL_BOARD_SIZE, NORMAL_BOARD_SIZE);
            // set a black piece on column 0, row 7.
            m_Piece = new Piece("black");
            m_BoardStore.setPiece(m_Piece, 0, ROW_SEVEN);
            // set a white piece again on column 0, row 7.
            m_Piece2 = new Piece("white");
            m_BoardStore.setPiece(m_Piece2, 0, ROW_SEVEN);
            // you cannot put the white piece here !!!


        if(m_BoardStore.getBoard()[0][ROW_SEVEN].getColour().equals ("black")) {
            System.out.println("C4AndOthelloBoardStore Test Eight Override Evaluated: Correct");
        } else {
            System.out.println("C4AndOthelloBoardStore Test Eight Override Evaluated: Incorrect");
        }

    }

    /**
     * Member variables to store both 2D arrays and the boards height and width
     */
    private int[][] m_Board;
    private Piece[][] m_Pieces;
    private int m_BoardWidth;
    private int m_BoardHeight;
    private static C4AndOthelloBoardStore m_BoardStore;
    private static Piece m_Piece;
    private static Piece m_Piece2;
    private static final int ROW_SEVEN = 7;
    private static final int NORMAL_BOARD_SIZE = 8;
    private static final int OUTSIDE_BOARD = 10;
    private static final int LARGE_BOARD_SIZE = 800;
    private static final int NEGATIVE_BOARD_SIZE = -1;

    /** Symbolic constant */
    private final int HAS_PIECE_OBJECT = 1;
}
