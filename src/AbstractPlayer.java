import java.awt.event.*;

/**
 * @author A.Alakeel
 * @date 25 Feb '14
 * @brief Store the name of the Player and their colour.
 * @details This class describes that whether a Player can make a move or not. It can also tell
 *  if it is his turn. The Player can change his name, set the colour of a piece.
 *  
 */

abstract public class AbstractPlayer {

	/** name of the player  */
	private String m_Name;
	/** the colour of piece */
	private String m_Colour;
	/** determines if player's turn or not */
	private boolean m_CurrentTurn;

	/**
	 * This is the constructor for the Player class.
	 */
	public AbstractPlayer(){}

	/**
	 * This method sets the colour of a piece
	 * and assigns it to attribute m_Colour
	 *
	 * @param colour	Assign this colour to m_Colour of the piece
	 * @return null
	 */
	public void setColour(String colour) {
		this.m_Colour = colour;
	}

	/**
	 * This method sets the name of a player
	 * and assigns it to the attribute m_Name
	 *
	 * @param name	Sets the name of the player
	 * @return null
	 */
	public void setName(String name) {
		this.m_Name = name;
	}

	/**
	 * This method simply returns the
	 * name of the player
	 *
	 * @return this.m_Name	Returns the name of the player
	 */
    public String getName() {
		return this.m_Name;
	}

	/**
	 * This method simply returns the colour of piece
	 *
	 * @return this.m_Colour	Returns the colour of type String
	 */
	public String getColour() {
		return this.m_Colour;
	}

	/**
	 * The purpose of this method is check if it is turn of the player or not.
	 *
	 * @return boolean	Returns true if it is the player's turn
	 * 					and false otherwise.
	 */
	public boolean isTurn() {
		return this.m_CurrentTurn;
	}

	public void switchTurn() {
		if (m_CurrentTurn == true){
			this.m_CurrentTurn = false;
		} else {
			m_CurrentTurn = true;
		}
	}


	/**
	 * This method is what the subclasses will use to control the move that
	 *	the player will make.
	 *	@param	x
	 *	@param	y
	 *	@param ProgramController
	 *	@return false
	 */
	public boolean move(int x, int y, ProgramController PC) {
    return false;
	}
}
