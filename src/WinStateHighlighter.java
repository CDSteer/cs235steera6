import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

/**
 * @author I.C. Skinner
 * @date 18 Feb '14
 * @brief This highlights the winning pieces in Connect4
 * This class will highlight the winning pieces after a game of
 * Connect4 has been played through to completion.
 *
 */

public class WinStateHighlighter{
	int m_Winner;
	/**
	 * Method for highlighting the winning combination in connect 4.
	 * @param column				The column which contains the Piece
	 *	which begins the chain of winning pieces in Connect 4.
	 * @param row					The row which contains the Piece
	 *  which begins the chain of winning pieces in Connect 4.
	 * @param ProgramController		The program controller, so that the
	 *  class can access various methods that it uses.
	 *	@return null
	 */
	public void c4WinStateHighlighter(int column, int row, JLabel[][] board, GameImplementation game) throws IOException{
		BufferedImage highlight_Piece = null;
		int winningMove = game.getWinningMove();

		if(game.getWinner() == PLAYER_ONE){
			String directory = "../Images/RedPieceHighlighted.png";
			highlight_Piece = ImageIO.read(new File(directory));
		}else if(game.getWinner() == PLAYER_TWO){
			String directory = "../Images/YellowPieceHighlighted.png";
			highlight_Piece = ImageIO.read(new File(directory));
		}else{
			System.out.println("No Win highlighting image found");
		}
		try{
			if(winningMove == HORIZONTAL_WIN){
				board[column][row].setIcon(new ImageIcon(highlight_Piece));
				board[column+ COORDINATE_ONE][row].setIcon(new ImageIcon(highlight_Piece));
				board[column+ COORDINATE_TWO][row].setIcon(new ImageIcon(highlight_Piece));
				board[column+ COORDINATE_THREE][row].setIcon(new ImageIcon(highlight_Piece));
			}else if(winningMove == VERTICAL_WIN){
				board[column][row].setIcon(new ImageIcon(highlight_Piece));
				board[column][row+ COORDINATE_ONE].setIcon(new ImageIcon(highlight_Piece));
				board[column][row+ COORDINATE_TWO].setIcon(new ImageIcon(highlight_Piece));
				board[column][row+ COORDINATE_THREE].setIcon(new ImageIcon(highlight_Piece));
			}else if(winningMove == RIGHT_DIAGONAL_WIN){
				board[column][row].setIcon(new ImageIcon(highlight_Piece));
				board[column+ COORDINATE_ONE][row+ COORDINATE_ONE].setIcon(new ImageIcon(highlight_Piece));
				board[column+ COORDINATE_TWO][row+ COORDINATE_TWO].setIcon(new ImageIcon(highlight_Piece));
				board[column+ COORDINATE_THREE][row+ COORDINATE_THREE].setIcon(new ImageIcon(highlight_Piece));
			}else if(winningMove == LEFT_DIAGONAL_WIN){
				board[column][row].setIcon(new ImageIcon(highlight_Piece));
				board[column- COORDINATE_ONE][row+ COORDINATE_ONE].setIcon(new ImageIcon(highlight_Piece));
				board[column- COORDINATE_TWO][row+ COORDINATE_TWO].setIcon(new ImageIcon(highlight_Piece));
				board[column- COORDINATE_THREE][row+ COORDINATE_THREE].setIcon(new ImageIcon(highlight_Piece));
			}else{}
		}catch(NullPointerException e){
			System.out.println("Must have been a draw!");
		}
	}

	/**
	 * Method for highlighting the winning combination in connect 4.
	 * @param column				The column which contains the Piece
	 *	which begins the chain of winning pieces in Connect 4.
	 * @param row					The row which contains the Piece
	 *  which begins the chain of winning pieces in Connect 4.
	 * @param ProgramController		The program controller, so that the
	 *  class can access various methods that it uses.
	 *	@return null
	 */
	public void tttWinStateHighlighter(int column, int row, JLabel[][] board, GameImplementation game) throws IOException{
		BufferedImage highlight_Piece = null;
		int winningMove = game.getWinningMove();

		if(game.getWinner() == PLAYER_ONE){
			String directory = "../Images/OPieceHighlighted.png";
			highlight_Piece = ImageIO.read(new File(directory));
		}else if(game.getWinner() == PLAYER_TWO){
			String directory = "../Images/XPieceHighlighted.png";
			highlight_Piece = ImageIO.read(new File(directory));
		}else{
			System.out.println("No Win highlighting image found");
		}
		try{
			if(winningMove == HORIZONTAL_WIN){
				board[column][row].setIcon(new ImageIcon(highlight_Piece));
				board[column+ COORDINATE_ONE][row].setIcon(new ImageIcon(highlight_Piece));
				board[column+ COORDINATE_TWO][row].setIcon(new ImageIcon(highlight_Piece));
				board[column+ COORDINATE_THREE][row].setIcon(new ImageIcon(highlight_Piece));
			}else if(winningMove == VERTICAL_WIN){
				board[column][row].setIcon(new ImageIcon(highlight_Piece));
				board[column][row+ COORDINATE_ONE].setIcon(new ImageIcon(highlight_Piece));
				board[column][row+ COORDINATE_TWO].setIcon(new ImageIcon(highlight_Piece));
				board[column][row+ COORDINATE_THREE].setIcon(new ImageIcon(highlight_Piece));
			}else if(winningMove == RIGHT_DIAGONAL_WIN){
				board[column][row].setIcon(new ImageIcon(highlight_Piece));
				board[column+ COORDINATE_ONE][row+ COORDINATE_ONE].setIcon(new ImageIcon(highlight_Piece));
				board[column+ COORDINATE_TWO][row+ COORDINATE_TWO].setIcon(new ImageIcon(highlight_Piece));
				board[column+ COORDINATE_THREE][row+ COORDINATE_THREE].setIcon(new ImageIcon(highlight_Piece));
			}else if(winningMove == LEFT_DIAGONAL_WIN){
				board[column][row].setIcon(new ImageIcon(highlight_Piece));
				board[column- COORDINATE_ONE][row+ COORDINATE_ONE].setIcon(new ImageIcon(highlight_Piece));
				board[column- COORDINATE_TWO][row+ COORDINATE_TWO].setIcon(new ImageIcon(highlight_Piece));
				board[column- COORDINATE_THREE][row+ COORDINATE_THREE].setIcon(new ImageIcon(highlight_Piece));
			}else{}
		}catch(NullPointerException e){
			System.out.println("Must have been a draw!");
		}
	}

	/**
	 * Method for highlighting the winning combination in connect 4.
	 * @param column				The column which contains the Piece
	 *	which begins the chain of winning pieces in Connect 4.
	 * @param row					The row which contains the Piece
	 *  which begins the chain of winning pieces in Connect 4.
	 * @param ProgramController		The program controller, so that the
	 *  class can access various methods that it uses.
	 *	@return null
	 */
	public void othWinStateHighlighter(int column, int row, JLabel[][] board, GameImplementation game) throws IOException{
		BufferedImage highlight_Piece = null;
		int winningMove = game.getWinningMove();

		if(game.getWinner() == PLAYER_ONE){
			m_Winner = 0;
			String directory = "../Images/BlackPieceHighlighted.png";
			highlight_Piece = ImageIO.read(new File(directory));
		}else if(game.getWinner() == PLAYER_TWO){
			m_Winner = 1;
			String directory = "../Images/WhitePieceHighlighted.png";
			highlight_Piece = ImageIO.read(new File(directory));
		}else{
			System.out.println("No Win highlighting image found");
		}
		try{
	    for(int y = 0; y<HEIGHT; y++){
	      for(int x = 0; x<WIDTH; x++){
	      	if (game.getBoard().getPiece(y,x).getColour().equals(game.getPlayer(m_Winner).getColour())){
	      		board[y][x].setIcon(new ImageIcon(highlight_Piece));
	      	}
	      }
	    }

		}catch(NullPointerException e){
			System.out.println("Must have been a draw!");
		}
	}

	public static void main(String[] args){
		// Testing
		GameImplementation game = new Connect4GameLogic();
		WinStateHighlighter testHighlight = new WinStateHighlighter();
		JLabel[][] board = new JLabel[game.getBoard().getBoardWidth()][game.getBoard().getBoardHeight()];

		for(int y = 0; y < game.getBoard().getBoardHeight(); y++){
			for(int x = 0; x < game.getBoard().getBoardWidth(); x++){
				board[x][y] = new JLabel();
			}
		}

		/* Player 1 has won the game */

		game.setWinner(1);

		try {
			testHighlight.c4WinStateHighlighter(0, 0, board, game);
		} catch (IOException e) {
			System.out.println("WinStateHighlighter()::setWinner(1) - Exception found");
		}

		/* Player 2 has won the game */

		game.setWinner(2);

		try{
			testHighlight.c4WinStateHighlighter(0, 0, board, game);
		}catch(IOException e){
			System.out.println("WinStateHighLighter()::setWinner(2) - Exception found");
		}

	}

	/** Symbolic constants */
	private final int HORIZONTAL_WIN = 0;
	private final int VERTICAL_WIN = 1;
	private final int RIGHT_DIAGONAL_WIN = 2;
	private final int LEFT_DIAGONAL_WIN = 3;

	private final int HEIGHT = 8;
	private final int WIDTH = 8;

	private final int PLAYER_ONE = 1;
	private final int PLAYER_TWO = 2;
  private final int COORDINATE_ONE = 1;
  private final int COORDINATE_TWO = 2;
  private final int COORDINATE_THREE = 3;
}
