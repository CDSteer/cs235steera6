import java.io.*;

/**
 * @author G.D. Damabel *
 * @date 24 Feb '14 *
 * @brief Gets and returns Player move.
 * @details The Human class is intended to create two players, checks if player move is valid and then
 * 		   pass the information to the program controller class to set the piece on the game board,
 *         update the game board and checks if that is a winning move.
 */

public class Human extends AbstractPlayer{
	
	/**
	*  Method move is used to make a move by a player at position (x,y).
	*  \param  	x	Column position on a Board to move Piece.
	*  \param 	y	Row position on a Board to move Piece.
	*  \param  	PC  ProgramController used to get required information.
	*  \return Boolean
	*/
    public boolean move(int x, int y, ProgramController PC){
        Boolean validMove = false;

		/* checks if move is valid. if not valid return and do nothing. */
        validMove = (PC.getGame()).checkValid(x, y, this);
        if (validMove == false) {
            return validMove;
        }
        /* Set piece if move is valid */
        PC.getGame().setPiece(x, y, this);
        try {
			/*
				Move is valid call update with with type of game board and colour
				of piece.
		   */
             PC.update(((PC.getGame().getBoard())),
                        PC.getGame().getPlayer(PLAYER_ONE).getColour(),
                        PC.getGame().getPlayer(PLAYER_TWO).getColour());
        } catch (IOException ex) {}
        /* Return true if the player move is valid. */
        return validMove;
    }
    
    /**
	 * Main method for class tests on SplashScreen
	 * Takes no arguments
	 */
    public static void main(String[] args) {
    	
    	final int IS_C4 = 0;
    	final int NOT_C4 = 1;
    	final int C4_WIDTH = 10;
    	final int C4_HEIGHT = 7;
    	final int OTH_WIDTH = 8;
    	final int OTH_HEIGHT = 8;
        final int ROW_3 = 3;
        final int ROW_4 = 4;
        final int COLUMN_3 = 3;
        final int COLUMN_4 = 4;
        final int INCREMENT_TWO = 2;
        final int INCREMENT_FOUR = 4;
    	
    	/*
    	 * Test One
    	 * Calling Human.move on a C4 board
    	 */
    	ProgramController testPC1 = new ProgramController();
    	Human testHuman1 = new Human();
    	testPC1.setIsC4(IS_C4);
    	testHuman1.setColour("Red");
   	
    	Piece[][] testC4PieceLayout = new Piece[C4_WIDTH][C4_HEIGHT];
		for (int i = 0; i < C4_HEIGHT; i++) {
	  		  for (int j = 0; j < C4_WIDTH; j++) {
	  			  testC4PieceLayout[j][i] = new Piece("");
	  		  }
		}
    	
    	try {
			// Only applicable arguments are 'testPieceLayout'
			testPC1.ProgramController(0, INCREMENT_TWO, "player1", "player2", testC4PieceLayout, 1, 1);
		} catch (Exception e) {
			System.out.println("Error setting layout in Test 1 Human");
			e.printStackTrace();
		} finally {
			testPC1.setVisible(false);
		}
		
    	
    	if(testHuman1.move(0, 0, testPC1) == true) {
    		System.out.println("Human.move Evaluated: Correct");
    	} else {
    		System.out.println("Human.move Evaluated: Incorrect");
    	}
    	
    	/*
    	 * Test Two
    	 * Calling Human.move on an Othello board
    	 */
    	ProgramController testPC2 = new ProgramController();
    	Human testHuman2 = new Human();
    	testPC2.setIsC4(NOT_C4);
    	testHuman2.setColour("Black");
   	
    	Piece[][] testOthPieceLayout = new Piece[OTH_WIDTH][OTH_HEIGHT];
		for (int i = 0; i < OTH_HEIGHT; i++) {
	  		  for (int j = 0; j < OTH_WIDTH; j++) {
	  			  testOthPieceLayout[j][i] = new Piece("");
	  		  }
		}
		testOthPieceLayout[COLUMN_3][ROW_3] = new Piece("White");
		testOthPieceLayout[COLUMN_3][ROW_4] = new Piece("Black");
		testOthPieceLayout[COLUMN_4][ROW_3] = new Piece("Black");
		testOthPieceLayout[COLUMN_4][ROW_4] = new Piece("White");
		
    	
    	try {
			// Only applicable arguments are 'testPieceLayout'
			testPC2.ProgramController(1, 0, "player1", "player2", testOthPieceLayout, 1, 1);
		} catch (Exception e) {
			System.out.println("Error setting layout in Test 2 Human");
			e.printStackTrace();
		} finally {
			testPC2.setVisible(false);
		}
		
    	
    	if(testHuman2.move(INCREMENT_FOUR, INCREMENT_TWO, testPC2) == true) {
    		System.out.println("Human.move Evaluated: Correct");
    	} else {
    		System.out.println("Human.move Evaluated: Incorrect");
    	}
    	  	
    	 
    }
    	

	/* Symbolic constants */

	/* First player at array position one. */
	private final int PLAYER_ONE = 0;
	/* Second player at array position two. */
	private final int PLAYER_TWO = 1;
}