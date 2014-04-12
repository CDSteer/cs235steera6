import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

import java.awt.event.*;

/**
 * @author Jamie I Davies, Martin Hui, Cameron Steer, I.C. Skinner, J. Bailey, S. Jones
 * @date Feb 25, 2014
 * @brief class that builds the board for either C4 or Othello 
 * @details ProgramController has all setup variables such and the game state and player names called into it
 *
*/
public class ProgramController extends JFrame implements MouseListener, ActionListener{

	/** Method to get the GameImplementation member variable m_Game
	 * @return m_game -game being played
	 */
	public AbstractGameImplementation getGame(){
       return m_Game;
   }

	public ProgramController getThis() {
		return this;
	}

	public boolean getWaiting() {
		return m_waiting;
	}

	public void setWaiting(boolean b) {
		m_waiting = b;
	}


	/** Getter method to get the variable m_Time
	 * @return m_Time -int that stores time elapsed
	 */
	public int getTime() {
		return m_Time;
	}

	/** Getter method to get the variable m_Turn
	 * @return m_Turn -int that stores who's turn it is
	 */
	public int getTurn() {
		return m_Turn;
	}

	/** Getter method to get the object m_Board
	 * @return m_Board -object from board class
	 */
	public C4AndOthelloBoardStore getBoard(){
		return m_Board;
	}

	/** Method to get the boolean m_IsC4.
	 * @return m_isC4 -boolean variable that stores which game's being played
	 */
	private boolean getIsC4(){
		return m_IsC4;
	}

	/**
	*	Sets connect4 option to be true
	*	@param userOption -int value given by dialog frame at start of the program
	*	@return null
	*/
	public void setIsC4(int userOption){
		if(userOption == 0){
			m_IsC4 = true;
		}else if(userOption == 1){
			m_IsC4 = false;
    }else{
      System.out.println("Program exited");
			System.exit(1);
    }
	}


	/**
	*	Method to set the GameImplementation member variable m_Game that also
	*	calls setBGImage.
	*	@param player1 -stores name of player 1
	*	@param player2 -stores name of player 2
	* 	@return null
	*/
	public void setGame(String player1, String player2){
		File background_File;
		if(getIsC4() == true){
			m_Game = new Connect4GameLogic();
			background_File = new File("../Images/Connect4Background.png");
		}else{
			m_Game = new OthelloGameLogic();
			background_File = new File("../Images/OthelloBackground.png");
		}
		try{
			setBGImage(background_File);
		}catch(IOException e){
			System.out.println("IOException error @ ProgramController::setGame()");
		}
		getGame().getPlayer(PLAYER_ONE).setName(player1);
		getGame().getPlayer(PLAYER_TWO).setName(player2);

	}


	/**
	 *	Create New game button
	 *	@return null
	 */
	private void setNewGameButton(){
		m_NewGameButton = new JButton("New Game");
		m_Constraints.gridy = getBoard().getBoardHeight() + ADD_TWO;
		m_Constraints.gridx = getBoard().getBoardWidth() / DIVIDE_BY_TWO - 1;
		m_Constraints.gridwidth = NEW_GAME_GRID_WIDTH;
		m_Container.add(m_NewGameButton, m_Constraints);
		m_NewGameButton.addActionListener(this);
	}
	ActionListener actListner2;
	/**
	 *	Create Save Game button and actionlistener
	 *	@return null
	 */
	private void setSaveButton(){
		m_SaveButton = new JButton("Save Game");
		m_Constraints.gridy = saveButtonGridY;
		m_Constraints.gridx = saveButtonGridX;
		m_Constraints.gridwidth = NEW_GAME_GRID_WIDTH;
		m_Container.add(m_SaveButton, m_Constraints);
		actListner2 = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println("Test save button");
				try{
					if (m_GameType.equals("C4")){
						if (c4SaveManager.saveData(m_GameType, m_Board, m_Time, m_Game.getPlayer(PLAYER_ONE).getName(),
                                m_Game.getPlayer(PLAYER_TWO).getName(), m_Player1Type, m_Player2Type, m_Turn));
						        JOptionPane.showMessageDialog(null, "Save Success");

					} else {
						othSaveManager.saveData(m_GameType, m_Board, m_Time, m_Game.getPlayer(PLAYER_ONE).getName(),
                                m_Game.getPlayer(PLAYER_TWO).getName(), m_Player1Type, m_Player2Type, m_Turn);
					}
				} catch (IOException e){
					System.out.println("Can't Save Data");
				  e.printStackTrace();
				}
			}
		};
		// action listener for the save button
    m_SaveButton.addActionListener(actListner2);
	}
	
	ActionListener actListner3;
	/**
	 *	Create Save Game button and actionlistener
	 *	@return null
	 */
	private void setMainMenuButton(){
		m_MainMenuButton = new JButton("Main Menu");
		m_Constraints.gridy = mainMenuButtonGridY;
		m_Constraints.gridx = mainMenuButtonGridX;
		m_Constraints.gridwidth = NEW_GAME_GRID_WIDTH;
		m_Container.add(m_MainMenuButton, m_Constraints);
		//m_SaveButton.addActionListener(m_Handler);
		actListner3 = new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				System.out.println("Back to main menu...");
				setVisible(false);
				System.out.println("Start back up splash screen...");
						
				m_Timer.stop(); //stop timer
				m_TimerLabel = new JLabel("Time elapsed: 0s"); //reset timer label
				setTime(0);//set time to zero
				
				setTurn(0); //Reset turn to zero
				getTurnNumberLabel().setText("Turn: 1"); //reset turn count
				
				SplashScreen splash = new SplashScreen();
				splash.initSplash();
				
			}
		};
		// action listener for the save button
		m_MainMenuButton.addActionListener(actListner3);
	}


	/**
	 *	Create label to display name of current player
	 *	@return null
	 */
	private void setTurnLabel(){
		m_TurnLabel = new JLabel(m_Game.getPlayer(PLAYER_ONE).getName() + "'s turn");
		System.out.println(m_Game.getPlayer(PLAYER_ONE).getName());
		m_Constraints.gridy = turnLabelGridY;
		m_Constraints.gridx = turnLabelGridX;
		m_Constraints.gridwidth = TURN_GRID_WIDTH;
		m_Container.add(m_TurnLabel, m_Constraints);
	}

	/**
	 *	Create label to display time elapsed
	 *	@return null
	 */
	private void setTimerLabel(){
		m_TimerLabel = new JLabel("Time elapsed: 0s");
		m_Constraints.gridy = getBoard().getBoardHeight() + ADD_TWO;
		m_Constraints.gridx = getBoard().getBoardWidth() / DIVIDE_BY_TWO + 1;
		m_Constraints.gridwidth = TIMER_GRID_WIDTH;
		m_Container.add(m_TimerLabel, m_Constraints);
	}

	/**
	 *	Create label to display number of turns taken
	 *	@return null
	 */
	private void setTurnNumberLabel(String labelString){

		m_TurnNumberLabel = new JLabel(labelString);
		m_Constraints.gridy = getBoard().getBoardHeight() + ADD_TWO;
		m_Constraints.gridx = getBoard().getBoardWidth() / DIVIDE_BY_TWO - SUBTRACT_FOUR;
		m_Constraints.gridwidth = INCREMENT_TWO;
		m_Container.add(m_TurnNumberLabel, m_Constraints);
	}
	/**
	 *	Create container to hold UI elements
	 *	@return null
	 */
	private void setContainer(){
		m_Container = getContentPane();
    m_Container.setLayout(new GridBagLayout());
		m_Constraints = new GridBagConstraints();
		this.setMinimumSize(new Dimension(CONTAINER_64 * getBoard().getBoardWidth(),
                CONTAINER_64 * getBoard().getBoardHeight() + CONTAINER_22));
	}

	/** Getter method to return timer label value
	 * 	@return m_TimerLabel -JLabel object
	 */
	private JLabel getTimerLabel(){
		return m_TimerLabel;
	}

	/** Getter method to return turn label value
	 * 	@return m_TurnLabel -JLabel object
	 */
	private JLabel getTurnLabel(){
		return m_TurnLabel;
	}

	/**
	 *	Setter method to set current time elapsed value
	 * 	@param time -sets time variable to value given by timer
	 *	@return null
	 */
	private void setTime(int time) {
		m_Time = time;
	}

	/** Setter method to set current who's turn it currently is
	 * 	@param turnNumber -int that stores number of taken turns
	 *	@return null
	 */
	private void setTurn(int turnNumber) {
		m_Turn = turnNumber;
	}

	/** Getter method that returns label object
	 *  @return m_TimerLabel -returns value for getTunNumberLabel
	 */
	private JLabel getTurnNumberLabel(){
		return m_TurnNumberLabel;
	}

	/** Setter method that sets the board using the board and game classes
	*	@return null
	*/
	private void setBoard(){
		m_Board = getGame().getBoard();
	}
	public void setBoard(C4AndOthelloBoardStore board){
		m_Board = board;
	}

	/** Getter method that returns the background image of the board
	 *  @return m_Background_Image -file extension of background image
	 */
	private BufferedImage getBGImage(){
		return m_Background_Image;
	}

	/** Setter method that sets a image file as the background of the board
	 *  @param background_File -file extension of image file
	 *	@return null
	 */
	private void setBGImage(File background_File) throws IOException{
		try{
            m_Background_Image = ImageIO.read(background_File);
        }catch(IOException e){
            System.out.println("IOException Error @ ProgramController::setBGImage()");
        }
	}

	/** setter method that draws the labels that are used as the squares on the board
	 * 	@param x -coordinates of label grid that user has clicked on
	 * 	@param y
	 *	@return null
	 */
	private void setLabels(int x, int y) {

		m_Image_Labels = new JLabel[x][y];
		for(int i = 0; i < y; i++){
			for(int j = 0; j<x; j++){
				m_Image_Labels[j][i] = new JLabel();
		       	m_Image_Labels[j][i].addMouseListener(this);
		    }
		}
	}

	/** Getter method that returns value of square user has clicked on
	 * 	@param x -coordinates of label grid that user has clicked on
	 * 	@param y
	 *  @return m_Image_Labels -specific label from the 2D array of labels
	 */
	private JLabel getLabel(int x, int y) {
		return m_Image_Labels[x][y];
	}

	/** Setter method that sets image where user has clicked
	 *  @param x -coordinates of label grid that user has clicked on
	 *  @param y
	 *  @param image -image to be used as background of label
	 *	@return null
	 */
	private void setImage(int x, int y, ImageIcon image) {
		m_Image_Labels[x][y].setIcon(image);
	}

	/** Setter method that sets an image for every square on the board
	*	@return null
	*/
	private void setImages(){
		setLabels(getBoard().getBoardWidth(),getBoard().getBoardHeight());

		for(int y = 0; y<getBoard().getBoardHeight(); y++){
			for(int x = 0; x<getBoard().getBoardWidth(); x++){
				m_Constraints.gridx = x;
				m_Constraints.gridy = y;
				m_Constraints.gridwidth = 1;
				m_Container.add(getLabel(x,y), m_Constraints);
		    }
		}
	}


	/** Setter method that gets label from array
	 *  @return m_Image_Labels -an array of JLabels
	 */
	public JLabel[][] getLabels(){
		return m_Image_Labels;
	}

	/**
	 *	Method that is performed when a user has taken their turn so the board's UI elements
	 *	are updated to whoever's turn it is
	 *	@param event - action performed event
	 *	@return null
	*/
	public void actionPerformed(ActionEvent event) {
			String colour1 = getGame().getPlayer(PLAYER_ONE).getColour();
			String colour2 = getGame().getPlayer(PLAYER_TWO).getColour();
			getTurnLabel().setText(getGame().getPlayer(PLAYER_ONE).getName() + "'s turn");
			setGame(getGame().getPlayer(PLAYER_ONE).getName(), getGame().getPlayer(PLAYER_TWO).getName());

			setTime(0);

			try {
				update(getGame().getBoard(), colour1, colour2);
			} catch (IOException e) {
				System.out.println("IOException error @ ProgramController::actionPerformed()");
			}

			setTurn(0); //Reset turn to 0.
			getTurnNumberLabel().setText("Turn: 1");

	}

	/**
	 *	Mouse event handler method that checks where user has clicked on board,
	 *	then runs the attemptMove method with the coordinates of the move
	 *	@param e - mouse event
	 *	@return null
	*/
  public void mouseClicked(MouseEvent e) {

	  if(getWaiting() == true) {
		  System.out.println("Waiting for AI Move...");
		  return;
	  }

	  System.out.println("Clicked");
  	if(getGame().checkWin() == false){
      for(int y = 0; y<getBoard().getBoardHeight(); y++){
        for(int x = 0; x<getBoard().getBoardWidth(); x++){
          if(getIsC4() == false){
						if(e.getSource()==getLabel(x,y)){
							attemptMove(x,y);
						}
					}else{
						if(e.getSource()==getLabel(x,0)){
							attemptMove(x,0); //y value doesn't matter
							y = getBoard().getBoardHeight();
						}
					}
				}
			}
        }else{
			try{
				displayWinner();
			}catch(IOException e2){
				System.out.println("IOException error @ ProgramController::mouseClicked()");
			}

    }

  }
  /**
	 *	Mouse event handler method that checks where user has enter into particular coordinate on Connect 4 board,
	 *	then runs the arrowPointer method with the coordinates of the move
	 *	@param e - mouse event
	 *	@return null
  */
  public void mouseEntered(MouseEvent e) {
	if(getIsC4() == true){
		try{
			for(int y = 0; y < getBoard().getBoardHeight(); y++){
				for(int x = 0; x < getBoard().getBoardWidth(); x++){
					if(getIsC4() == true){
						if(e.getSource()==getLabel(x,y)){
							arrowPointer(x,0);          // 6 as always on top
						}
					}
				}
			}
		}catch(IOException e2){
			System.out.println("IOException error @ ProgramController::mouseEntered()");
		}
	}
}

	/**
	* Animation of mouse pointing at row in Connect 4
	*	@param  x	x coordinate of mouse clicked position
	*	@param	y   y coordinate of mouse clicked position
	*	@throws	IOException
	*	@return null
	*/
	public void arrowPointer(int x, int y) throws IOException{
		//final BufferedImage arrow_Image = ImageIO.read(new File("../Images/Connect4arrow.png"));
		//final BufferedImage blank_Image = ImageIO.read(new File("../Images/Connect4Background.png"));

		BufferedImage arrow_Image;

		BufferedImage blank_Image = ImageIO.read(new File("../Images/Connect4Background.png"));

		if(getTurn() % REMAINDER_2 == PLAYER_ONE) {
			arrow_Image = ImageIO.read(new File("../Images/Connect4arrow.png"));
		} else {
			arrow_Image = ImageIO.read(new File("../Images/Connect4arrowYellow.png"));
		}

		int hidden;
		for(int i = 0; i < getBoard().getBoardWidth(); i++){
			hidden = getLabel(i,0).getDisplayedMnemonic();
			if(hidden == IMAGE_SIZE_100){
				setImage(i,0,(new ImageIcon(blank_Image)));
				getLabel(i,0).setDisplayedMnemonic(IMAGE_SIZE_100);
			}
		}
		hidden = getLabel(x,y).getDisplayedMnemonic();
		if(hidden == IMAGE_SIZE_100){
			setImage(x,y,(new ImageIcon(arrow_Image)));
		}
	}

	/**
	* Animation of falling piece
	*	@param  x	x coordinate of mouse clicked position
	*	@param	y   y coordinate of mouse clicked position
	*	@param	piece_Image      pieces image
	*	@throws	IOException
	*	@return null
	*/
	public void dropPiece(int x, int y, BufferedImage piece_Image) throws IOException{

		if(getIsC4() == false){
			setImage(x,y,(new ImageIcon(piece_Image)));
			return;
		}
		final int boardHeight = C4_BOARD_HEIGHT - 1;
		final int width = x;
		final int height = y;
		final int hidden = getLabel(x,y).getDisplayedMnemonic();
		final BufferedImage piece_Image1 = piece_Image;
		final BufferedImage blank_Image = ImageIO.read(new File("../Images/Connect4Background.png"));
		if(getIsC4() == true){
			new Thread(
				new Runnable(){
					public void run(){
						if(hidden == IMAGE_SIZE_100){
							try{
								for(int i = 0; i < height; i++){
									setImage(width,i,(new ImageIcon(piece_Image1)));
									Thread.sleep(DROP_DELAY);
									setImage(width,i,(new ImageIcon(blank_Image)));
								}
								setImage(width,height,(new ImageIcon(piece_Image1)));
							}catch(InterruptedException e){
							}
						}
					}
				}
			).start();
		}

	}
  /**
   *	Method that updates the board with new moves that have been taken
   *	@param board
   *	@param colour1
   *	@param colour2
   *	@throws	IOException
   *	@return null
  */
  public void update(C4AndOthelloBoardStore board, String colour1, String colour2) throws IOException{
    System.out.println("ProgramController::update()");
    int boardHeight = board.getBoard()[0].length;
    int boardWidth = board.getBoard().length;

    for(int y = 0; y<boardHeight; y++){
      for(int x = 0; x<boardWidth; x++){
			int hidden = getLabel(x,y).getDisplayedMnemonic();
				/*Set Label as empty block, if board(x,y) is Empty*/
				if(board.isEmpty(x,y) == true) {
					setImage(x,y,(new ImageIcon(getBGImage())));
					getLabel(x,y).setDisplayedMnemonic(IMAGE_SIZE_100);
				} else if (board.getBoard()[x][y].getColour().equals(colour1)) {
					/*Check if it is not player2 piece*/
					if (hidden == IMAGE_SIZE_300 || hidden == IMAGE_SIZE_500) {
						if(getIsC4() == false) {
							setImage(x,y,(new OthelloPieceColourChanger().flip(board.getBoard()[x][y])));
						}
						getLabel(x,y).setDisplayedMnemonic(IMAGE_SIZE_400);
					} else {
						BufferedImage piece_Image = ImageIO.read(new File("../Images/" + colour1 + "Piece.png"));
						if (m_Loading){
							setImage(x, y, (new ImageIcon (piece_Image)));
						} else {
							dropPiece(x,y,piece_Image);
						}
						getLabel(x,y).setDisplayedMnemonic(IMAGE_SIZE_200);
					}
				} else if (board.getBoard()[x][y].getColour().equals(colour2)) {
					/*Check if it is not player1 piece*/
					if (hidden == IMAGE_SIZE_200 || hidden == IMAGE_SIZE_400) {
						if(getIsC4() == false) {
							setImage(x,y,(new OthelloPieceColourChanger().flip(board.getBoard()[x][y])));
						}

						getLabel(x,y).setDisplayedMnemonic(IMAGE_SIZE_500);
					} else {
						BufferedImage piece_Image = ImageIO.read(new File("../Images/" + colour2 + "Piece.png"));
						if (m_Loading){
							setImage(x, y, (new ImageIcon (piece_Image)));
						} else {
							dropPiece(x,y,piece_Image);
						}
						getLabel(x,y).setDisplayedMnemonic(IMAGE_SIZE_300);
					}
				}else{

				}
  		}
	}
  }

   /**
	 *	Method that checks if the square user has clicked on is a valid move, if there are no
	 *	more possible turns then the game is over and displayWinner method is called.
	 *	@param x
	 *	@param y
	 *	@return null
	 */
	private void attemptMove(int x, int y) {

		m_AIC4Col = 0;
		m_players = new AbstractPlayer[PLAYER_2];
		m_players[PLAYER_ONE] = getGame().getPlayer(PLAYER_ONE);
		m_players[PLAYER_TWO] = getGame().getPlayer(PLAYER_TWO);

		if(m_playerSelection == HUMAN) {
			boolean checkMoveIsValid = false;
			checkMoveIsValid = m_players[getTurn() % PLAYER_2].move(x, y, this);
		  if(checkMoveIsValid == true) {
				if (getGame().checkTakeableTurn(m_players[(getTurn() + 1) % REMAINDER_2]) == true) {
					/** If it's 0 (player 1) turn this will change it  to 1 (player 2) turn*/
					setTurn(getTurn() + 1);
					getTurnNumberLabel().setText("Turn: " + (getTurn() + 1));
					if((getTurn() % PLAYER_2)== 0){
						System.out.println(getGame().getPlayer(PLAYER_ONE).getName());
						getTurnLabel().setText(getGame().getPlayer(PLAYER_ONE).getName() + "'s turn");
					} else {
						System.out.println(getGame().getPlayer(PLAYER_TWO).getName());
						getTurnLabel().setText(getGame().getPlayer(PLAYER_TWO).getName() + "'s turn");
					}

				} else if (getGame().checkTakeableTurn(m_players[0]) == false && getGame().checkTakeableTurn(m_players[1]) == false) {
					try{
						displayWinner();
					}catch(IOException e){
						System.out.println("IOException error @ ProgramController::attemptMove()");
					}
				}
			}
		} else if (m_playerSelection == EASY_AI) {
			boolean checkAIMove = false;
			boolean checkMoveIsValid = false;
			if(getIsC4() == true) {
				checkMoveIsValid = m_players[0].move(x, y, this);
				if(checkMoveableAI(m_players)) {
					checkWinner(m_players);
					return;
				}
				new Thread(
						new Runnable(){
							public void run(){
								try {
									setWaiting(true);
									System.out.println("DELAY FOR AI MOVE START");
									Thread.sleep(WAIT_TIME);
									System.out.println("DELAY FOR AI MOVE END");
									m_AIC4Col = m_c4EasyAI.selectCol(getThis());
									m_checkAIMove = m_players[1].move(m_AIC4Col, C4_BOARD_HEIGHT, getThis());
									checkWinner(m_players);
									setWaiting(false);
								} catch (Exception e) {
									Thread.currentThread().interrupt();
								}
							}
						}).start();

			} else {
				checkMoveIsValid = m_players[0].move(x, y, this);
				if(checkMoveableAI(m_players)) {
					checkWinner(m_players);
					return;
				}
				if(checkMoveIsValid == true) {
					new Thread(
							new Runnable(){
								public void run(){
									try {
										setWaiting(true);
										System.out.println("DELAY FOR AI MOVE START");
										Thread.sleep(WAIT_TIME);
										System.out.println("DELAY FOR AI MOVE END");
										m_AIOthMoves = m_othEasyAI.selectMove(getThis());
										m_AIOthRow = m_AIOthMoves[0];
										m_AIOthCol = m_AIOthMoves[1];
										m_checkAIMove = m_players[1].move(m_AIOthRow, m_AIOthCol, getThis());
										checkWinner(m_players);
										setWaiting(false);
									} catch (Exception e) {
										Thread.currentThread().interrupt();
									}
								}
							}).start();

				}
			}
		} else if (m_playerSelection == HARD_AI) {
			boolean checkAIMove = false;
			boolean checkMoveIsValid = false;
			if(getIsC4() == true) {
				checkMoveIsValid = m_players[0].move(x, y, this);
				if(checkMoveableAI(m_players)) {
					checkWinner(m_players);
					return;
				}
				new Thread(
						new Runnable(){
							public void run(){
								try {
									setWaiting(true);
									System.out.println("DELAY FOR AI MOVE START");
									Thread.sleep(WAIT_TIME);
									System.out.println("DELAY FOR AI MOVE END");
									m_AIC4Col = m_c4HardAI.selectCol(getThis());
									m_checkAIMove = m_players[1].move(m_AIC4Col, C4_BOARD_HEIGHT, getThis());
									checkWinner(m_players);
									setWaiting(false);
								} catch (Exception e) {
									Thread.currentThread().interrupt();
								}
							}
						}).start();

			} else {
				checkMoveIsValid = m_players[0].move(x, y, this);
				if(checkMoveableAI(m_players)) {
					checkWinner(m_players);
					return;
				}
				if(checkMoveIsValid == true) {
					new Thread(
							new Runnable(){
								public void run(){
									try {
										setWaiting(true);
										System.out.println("DELAY FOR AI MOVE START");
										Thread.sleep(WAIT_TIME);
										System.out.println("DELAY FOR AI MOVE END");
										m_AIOthMoves = m_othHardAI.selectMove(getThis());
										m_AIOthRow = m_AIOthMoves[0];
										m_AIOthCol = m_AIOthMoves[1];
										m_checkAIMove = m_players[1].move(m_AIOthRow, m_AIOthCol, getThis());
										checkWinner(m_players);
										setWaiting(false);
									} catch(Exception e) {}
								}
							}).start();

				}
			}
		}
	}

	/**
	*	Method that check who has won the game
	*	@param AbstractPlayer 	take a player object into method
	*	@return null
	*/
	private void checkWinner(AbstractPlayer[] players) {

		if (getGame().checkTakeableTurn(players[0]) == false && getGame().checkTakeableTurn(players[1]) == false) {
			try{
				displayWinner();
			}catch(IOException e){
				System.out.println("IOException error @ ProgramController::attemptMove()");
			}
		}
	}

	/**
	 *	Method that checks if a Human player has won in a VS AI game
	 *	@param AbstractPlayer 	take a player object into method
	 *	@return null
	 */
	private boolean checkMoveableAI(AbstractPlayer[] players) {

		if (getGame().checkTakeableTurn(players[1]) == false) {
			return true;
		}

		return false;
	}


	/**
	 *	Method that checks who has won the game, displays the winner's name to a label
	 *	Also highlights winning combination of pieces if playing connect4
	 *	@return null
	 *	@throws	IOException
	 */
	private void displayWinner() throws IOException{
		if(getGame().getWinner() == PLAYER_1 || getGame().getWinner() == PLAYER_2){
			System.out.println("Winner: Player " + (getGame().getWinner()) );
			if(getGame().getWinner() == PLAYER_1){
				getTurnLabel().setText(m_Game.getPlayer(PLAYER_ONE).getName() + " is the winner!");
			}else{
				getTurnLabel().setText(m_Game.getPlayer(PLAYER_TWO).getName() + " is the winner!");
			}

		}else if(getGame().getWinner() == DRAW){
			System.out.println("Draw!");
			getTurnLabel().setText("It's a draw!");
		}else{}

		Connect4WinStateHighlighter highlight = new Connect4WinStateHighlighter();

		if(getIsC4() == true){
			highlight.Connect4WinStateHighlighter(getGame().getWinningi(), getGame().getWinningj(), getLabels(), getGame());
		}else{}
	}

  public void mousePressed(MouseEvent e) {}
  public void mouseReleased(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}

  /**
   *	Timer method that starts a timer in intervals of 1 second, and is run whenever the game is
   *	declared to be running. current time elapsed is then set to a label and updated every second.
   *	@return null
   */
  private void startTimer() {
		ActionListener actListner = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(getGame().checkWin() == false) { //checks whether game is still running. if so timer can start
					setTime(getTime() + 1);
					getTimerLabel().setText("Time elapsed: " + getTime() + "s");
				}
			}
		};
		m_Timer = new Timer(ONE_SECOND_INTERVAL, actListner); //sets timer to 1 second intervals
		System.out.println("Timer started");
		if(getGame().checkWin() == true) {
			m_Timer.stop();
		} else {
			m_Timer.start(); //timer starts
		}
	}

  	/**
	 * Method that is called when program is first run, it starts all the methods
	 *  that create the UI elements, it also accepts input from the user such as their
	 *  name and what game they'd like to play
	 * @param gameState
	 * @param playerState
	 * @param player1Name
	 * @param player2Name
	 * @throws IOException
	 * @return null
	 */
	void ProgramController(int gameState, int playerState, String player1Name, String player2Name) throws IOException{
		//if (!load){
		player1 = player1Name;
		player2 = player2Name;

		setIsC4(gameState);
		if(this.getIsC4() == true){
			m_GameType = "C4";
		} else {
			m_GameType = "Oth";
		}
		setGame(player1, player2);
		setBoard();
		setContainer();
		setImages();
        setTurnLabel();
		setNewGameButton();
		setSaveButton();
		setMainMenuButton();
		setTurnNumberLabel("Turn: 1");
		setTimerLabel();
		startTimer();

  	if (playerState == 0) {
  		m_Player1Type = "Human";
  		m_Player2Type = "Human";
  		m_playerSelection = HUMAN;
  	} else if (playerState == 1) {
  		m_playerSelection = EASY_AI;
  	} else if (playerState == HARD_CHOICE) {
  			m_playerSelection = HARD_AI;
  	}

	//AI stuff
		if(m_playerSelection == EASY_AI) {
			if(this.getIsC4() == true) {
				m_Player2Type = "Easy";
				m_c4EasyAI = new C4EasyAI();
			} else {
				m_Player2Type = "Easy";
				m_othEasyAI = new OthEasyAI();
			}
		} else if(m_playerSelection == HARD_AI) {
			if(this.getIsC4() == true) {
				m_Player2Type = "Hard";
				m_c4HardAI = new C4HardAI();
			} else {
				m_Player2Type = "Hard";
				m_othHardAI = new OthHardAI();
			}
		}
		update(getGame().getBoard(), "Black", "White");

		//no idea
		pack();
	    setLocationRelativeTo(null);
		setVisible(true);
	// }
  }


  void ProgramController(int gameState, int playerState, String player1Name, String player2Name, Piece[][] board,
                         int turn, int time) throws IOException{

		m_Loading = true;
		setIsC4(gameState);
		System.out.println(playerState);
		if(this.getIsC4() == true){
			m_GameType = "C4";
		} else {
			m_GameType = "Oth";
		}
		player1 = player1Name;
  	    player2 = player2Name;

  	if (playerState == 0) {
  		m_Player1Type = "Human";
  		m_Player2Type = "Human";
  		m_playerSelection = HUMAN;
  	} else if (playerState == 1) {
  		m_playerSelection = EASY_AI;
  	} else if (playerState == HARD_CHOICE) {
  		m_playerSelection = HARD_AI;
  	}

  	//AI stuff
		if(m_playerSelection == EASY_AI) {
			if(this.getIsC4() == true) {
				m_Player2Type = "Easy";
				System.out.println("Set Easy C4 AI");
				m_c4EasyAI = new C4EasyAI();
			} else {
				m_Player2Type = "Easy";
				m_othEasyAI = new OthEasyAI();
			}
		} else if(m_playerSelection == HARD_AI) {
			if(this.getIsC4() == true) {
				m_Player2Type = "Hard";
				System.out.println("Set Hard C4 AI");
				m_c4HardAI = new C4HardAI();
			} else {
				m_Player2Type = "Hard";
				m_othHardAI = new OthHardAI();
			}
		}

		setGame(player1, player2);
		setBoard();
		setContainer();
		setImages();
	    setTurnLabel();
		setNewGameButton();
		setSaveButton();
		setMainMenuButton();
		setTurnNumberLabel("Turn: " + turn );
		setTimerLabel();
		startTimer();
		System.out.println(turn);
		m_Time = time;

  	if (m_GameType.equals("C4")){
  		for (int i = 0; i < LOOP_MAX_C4_ONE; i++) {
  		  for (int j = 0; j < LOOP_MAX_C4_TWO; j++) {
  		  	if (board[j][i].getColour() == "Red"){
  		  		Piece piece = new Piece("Red");
  		  		getGame().getBoard().setPiece2(piece ,j,i);
  		  	}else if (board[j][i].getColour() == "Yellow"){
  		  		Piece piece = new Piece("Yellow");
						getGame().getBoard().setPiece2(piece, j, i);
					}
  		    System.out.println( j+", " +i + ", "+ getGame().getBoard().getBoard()[j][i].getColour());
  		  }
  		}
  	}else if (m_GameType.equals("Oth")) {
  		for (int i = 0; i < LOOP_MAX; i++) {
  		  for (int j = 0; j < LOOP_MAX; j++) {
  		  	if (board[j][i].getColour() == "Black"){
  		  		Piece piece = new Piece("Black");
  		  		getGame().getBoard().setPiece2(piece, j, i);
  		  	}else if (board[j][i].getColour() == "White"){
  		  		Piece piece = new Piece("White");
						getGame().getBoard().setPiece2(piece, j, i);
					}
  		    System.out.println( j+", " +i + ", "+ getGame().getBoard().getBoard()[j][i].getColour());
  		  }
  		}
  	}

  	if (playerState == 0) {
  		m_Player1Type = "Human";
  		m_Player2Type = "Human";
  		m_playerSelection = HUMAN;
  	} else if (playerState == 1) {
  		m_Player1Type = "Human";
  		m_Player2Type = "Easy";
  		m_playerSelection = EASY_AI;
  	} else if (playerState == HUMAN_AND_HARD_CHOICE) {
  		m_Player1Type = "Human";
  		m_Player2Type = "Hard";
  		m_playerSelection = HARD_AI;
  	}

	//AI stuff
		if(m_playerSelection == EASY_AI) {
			if(this.getIsC4() == true) {
				m_Player2Type = "Easy";
				System.out.println("Set Easy C4 AI");
				m_c4EasyAI = new C4EasyAI();
			} else {
				m_Player2Type = "Easy";
				m_othEasyAI = new OthEasyAI();
			}
		} else if(m_playerSelection == HARD_AI) {
			if(this.getIsC4() == true) {
				m_Player2Type = "Hard";
				System.out.println("Set Hard C4 AI");
				m_c4HardAI = new C4HardAI();
			} else {
				m_Player2Type = "Hard";
				m_othHardAI = new OthHardAI();
			}
		}

		if (m_GameType.equals("C4")){
			update(getGame().getBoard(), "Red", "Yellow");
		} else {
			update(getGame().getBoard(), "Black", "White");
		}
		//no idea
		pack();
	    setLocationRelativeTo(null);
		setVisible(true);
		m_Loading = false;
  }
  
  /**
   * Main method for testing ProgramController class
   * void ProgramController method tests all other methods of the ProgramController class
   */
  public static void main(String[] args) {
	  
	  final int C4_HEIGHT = 7;
	  final int C4_WIDTH = 10;
	  final int OTH_WIDTH = 8;
	  final int OTH_HEIGHT = 8;
	  final int TEST_VALUE = 1;
	  final int SET_C4 = 0;
	  final int SET_NOT_C4 = 1;
	  
	  /*
	   * Test One
	   * Creating an instance of ProgramController for a C4 Game
	   */
	  ProgramController testPC1 = new ProgramController();
	  testPC1.setIsC4(0);
	  testPC1.setGame("player1", "player2");
		
	  Piece[][] testPieceLayout = new Piece[C4_WIDTH][C4_HEIGHT];
	  for (int i = 0; i < C4_HEIGHT; i++) {
		  for (int j = 0; j < C4_WIDTH; j++) {
			  testPieceLayout[j][i] = new Piece("");
	  	  }
	  }
	  		
	  try {
			
		  testPC1.ProgramController(SET_C4, 0, "player1", "player2", testPieceLayout, TEST_VALUE, TEST_VALUE);
		  System.out.println("ProgramController C4 Test Evaluated: Correct");
	  } catch (Exception e) {
		  System.out.println("ProgramController C4 Test Evaluated: Incorrect");
	  } finally {
		  testPC1.setVisible(false);
	  }
		
	  /*
	   * Test Two
	   * Creating an instance of ProgramController for an Othello Game
	   */
	  ProgramController testPC2 = new ProgramController();
	  testPC2.setIsC4(1);
	  testPC2.setGame("player1", "player2");
			
	  Piece[][] testPieceLayout2 = new Piece[OTH_WIDTH][OTH_HEIGHT];
	  for (int i = 0; i < OTH_HEIGHT; i++) {
		  for (int j = 0; j < OTH_WIDTH; j++) {
			  testPieceLayout2[j][i] = new Piece("");
		  }
	  }
		  		
	  try {
				
		  testPC2.ProgramController(SET_NOT_C4, 0, "player1", "player2", testPieceLayout2, TEST_VALUE, TEST_VALUE);
		  System.out.println("ProgramController Othello Test Evaluated: Correct");
	  } catch (Exception e) {
		  System.out.println("ProgramController Othello Test Evaluated: Incorrect");
	  } finally {
		  testPC2.setVisible(false);
	  }
  }

    // Class Constants
    // AI and Player Number Constants
    private final int PLAYER_ONE = 0;
	private final int PLAYER_TWO = 1;
	private final int HUMAN = 0;
	private final int EASY_AI = 1;
	private final int HARD_AI = 2;
	private final int DRAW = 3;
	private final int PLAYER_1 = 1;
	private final int PLAYER_2 = 2;
	private final int EASY_CHOICE = 1;
	private final int HARD_CHOICE = 2;
	private final int HUMAN_AND_HARD_CHOICE = 2;
    private final int LOOP_MAX = 8;
    private final int LOOP_MAX_C4_ONE = 7;
    private final int LOOP_MAX_C4_TWO = 10;
    private final int INCREMENT_TWO = 2;

	// Size and Math Constants
	private final int TIMER_GRID_WIDTH = 10;
    private final int TURN_GRID_WIDTH = 8;
	private final int NEW_GAME_GRID_WIDTH = 2;
	private final int DIVIDE_BY_TWO = 2;
	private final int CONTAINER_64 = 64;
	private final int CONTAINER_22 = 22;
	private final int ADD_TWO = 2;
	private final int SUBTRACT_FOUR = 4;;
	private final int turnLabelGridY = 8;
	private final int turnLabelGridX = 1;
	private final int saveButtonGridY = 23;
	private final int saveButtonGridX = 3;
	private final int mainMenuButtonGridY = 23;
	private final int mainMenuButtonGridX = 5;
	private final int C4_BOARD_HEIGHT = 7;
	private final int REMAINDER_2 = 2;

	// Time Related Constants
	private final int WAIT_TIME = 1500;
	private final int DROP_DELAY = 100;
	private final int ONE_SECOND_INTERVAL = 1000;

	// Image Size Constants
	private final int IMAGE_SIZE_100 = 100;
	private final int IMAGE_SIZE_200 = 200;
	private final int IMAGE_SIZE_300 = 300;
	private final int IMAGE_SIZE_400 = 400;
	private final int IMAGE_SIZE_500 = 500;

	// Variables storing player information
	private int m_Turn = 0;
	private String m_Player1, m_Player2;
	private String m_Player1Type;
	private String m_Player2Type;
	private String player1;
	private String player2;
	private int m_playerSelection;

	// Timer initialisation and member variables
	private Timer m_Timer;
	private static int m_Time;

	// Member variable that stores which game is being played, if false then othello is being played
  private static boolean m_IsC4;

	/* Member variable that stores game being played */
  private static AbstractGameImplementation m_Game;
  private String m_GameType;
  private C4SaveManager c4SaveManager =  new C4SaveManager();
  private OthSaveManager othSaveManager = new OthSaveManager();
  private boolean m_Loading = false;

	/** Initialisation of UI elements */
	private static BufferedImage m_Background_Image;
    private C4AndOthelloBoardStore m_Board;
    private JLabel[][] m_Image_Labels;
	private JButton m_NewGameButton;
	private JButton m_SaveButton;
	private JButton m_MainMenuButton;
	private JLabel m_TurnLabel;
	private JLabel m_TimerLabel;
	private JLabel m_TurnNumberLabel;
    private Container m_Container;
	private GridBagConstraints m_Constraints;


	// Variables holding AI Objects
	private C4EasyAI m_c4EasyAI;
	private C4HardAI m_c4HardAI;
	private OthEasyAI m_othEasyAI;
	private OthHardAI m_othHardAI;

	// Variables used in making AI moves
	private int m_AIC4Col;
	private int[] m_AIOthMoves;
	private int m_AIOthRow;
	private int m_AIOthCol;
	private boolean m_checkAIMove;
	private AbstractPlayer[] m_players;
	private boolean m_waiting;


}
