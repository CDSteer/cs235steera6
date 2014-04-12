
import java.io.*;
import javax.swing.*;

/**
 * @author A. Keskin
 * @date 25 Feb '14
 * @brief Animates the Othello board pieces.
 * @details When passed a piece object, this class returns the appropriate flipping ImageIcon
 */

public class OthelloPieceColourChanger extends JFrame {

	public OthelloPieceColourChanger(){}

	/**
	 *	Calls either blackToWhiteFlip() method or whiteToBlackFlip() method depending on the
	 *	input from the getColour() method parsed in from the Piece class.
	 *	@param p			Parses in the Piece class so that method can get colour of piece.
	 *	@return	ImageIcon	This returns the gif image which represents
	 *  the piece flipping when a colour is changed in the Othello game.
	 */
	public ImageIcon flip(Piece p){
		if (p.getColour().equals("Black")){
			return whiteToBlackFlip();
		}
		else if (p.getColour().equals("White")){
			return blackToWhiteFlip();
		}
		else {
			return new ImageIcon();
		}
	}

	/**
	 *	Fetches the gif which animates the piece flipping from black to white and returns it.
	 *	@return	blackToWhite	The animated Gif which flips a piece from black to white.
	 */
	private ImageIcon blackToWhiteFlip(){
		ImageIcon blackToWhite = new ImageIcon("../Images/BlackToWhitePiece.gif");
		blackToWhite.getImage().flush();
		return blackToWhite;
	}

	/**
	 *	Fetches the gif which animates the piece flipping from white to black and returns it.
	 *	@return	whiteToBlack	The animated Gif which flips a piece from white to black.
	 */
	private ImageIcon whiteToBlackFlip(){
		ImageIcon whiteToBlack = new ImageIcon("../Images/WhiteToBlackPiece.gif");
		whiteToBlack.getImage().flush();
		return whiteToBlack;
	}

	/**
	 * Main method for class tests on OthelloPieceColourChanger
	 */
	public static void main(String[] args) throws IOException {

		final String blackToWhiteGif = "../Images/BlackToWhitePiece.gif";
		final String whiteToBlackGif = "../Images/WhiteToBlackPiece.gif";
		
        //	Tests whether the black to white gif animation from file equals the gif animation parsed
        //	out from the ColourChange class.

		OthelloPieceColourChanger testColourChanger1 = new OthelloPieceColourChanger();
         ImageIcon testImage1 = testColourChanger1.flip(new Piece("White"));
         System.out.println(testImage1.toString());

        if(testImage1.toString().equals(blackToWhiteGif)) {
            System.out.println("OthelloPieceColourChanger Test One Evaluated: Correct");
        } else {
            System.out.println("OthelloPieceColourChanger Test One Evaluated: Incorrect");
        }


        //	Tests whether the white to black gif animation from file equals the gif animation parsed
        //	out from the ColourChange class.

        
        
        OthelloPieceColourChanger testColourChanger2 = new OthelloPieceColourChanger();
        ImageIcon testImage2 = testColourChanger2.flip(new Piece("Black"));   

        if(testImage2.toString().equals(whiteToBlackGif)) {
            System.out.println("OthelloPieceColourChanger Test Two Evaluated: Correct");
        } else {
            System.out.println("OthelloPieceColourChanger Test Two Evaluated: Incorrect");
        }


        //	Tests if an incorrect string is inputted so that it doesn't output one of the GIFs and outputs
        //	a blank ImageIcon.

        OthelloPieceColourChanger testColourChanger3 = new OthelloPieceColourChanger();
        ImageIcon testImage3 = testColourChanger3.flip(new Piece("Blue")); 
        
        try {
         if(testImage3.toString().equals(blackToWhiteGif) || testImage3.toString().equals(whiteToBlackGif)) {
            System.out.println("OthelloPieceColourChanger Test Three Evaluated: Incorrect");
        } else {
        	System.out.println("OthelloPieceColourChanger Test Three Evaluated: Correct");
        }

        } catch (Exception e) {

            System.out.println("OthelloPieceColourChanger Test Three Could not accept incorrect Piece input (intended)");
            System.out.println("OthelloPieceColourChanger Test Three BlankIcon Evaluated: Correct");

        }

	}
}