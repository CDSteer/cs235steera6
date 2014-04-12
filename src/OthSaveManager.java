import lib.opencsv.CSVReader;
import lib.opencsv.CSVWriter;

import java.awt.FileDialog;
import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.io.*;
import javax.swing.*;

/**
* @author Cameron Steer, Chris Jenkins
* @brief Saves and loads Othello games
* @details Reads in a CSV to load game data
* or records game data by writing to a CSV
*
*/

public class OthSaveManager {

	private static boolean test = true;
	private String m_FileName;
	private CSVWriter m_Writer;
	private CSVReader m_CSVReader;
	private List<String[]> m_Data;
	private Piece[][] m_LoadBoard;
	private int m_option;
	private OthelloGameLogic m_othelloGameLogic = new OthelloGameLogic();

	private String m_LoadGameType = "";
	private int m_LoadTime;
	private String m_LoadName1;
	private String m_LoadName2;
	private String m_LoadPlayerType1;
	private String m_LoadPlayerType2;
	private int m_LoadTurn;

	/**
	 * Returns the board that is loaded from the CSV file
	 *
	 * @return m_LoadBoard
	 */
	public OthelloGameLogic getLoadGame(){
	  return m_othelloGameLogic;
	}
	/**
	 * Returns the timer that is loaded from the CSV file
	 *
	 * @return m_LoadTime
	 */
	public int getLoadTime(){
	  return m_LoadTime;
	}
	/**
	 * Returns the player one name that is loaded from the CSV file
	 *
	 * @return m_LoadName1
	 */
	public String getLoadName1(){
	  return m_LoadName1;
	}
	/**
	 * Returns the player two name that is loaded from the CSV file
	 *
	 * @return m_LoadName2
	 */
	public String getLoadName2(){
	  return m_LoadName2;
	}
	/**
	 * Returns the player one type that is loaded from the CSV file
	 *
	 * @return m_LoadPlayerType1
	 */
	public String getLoadPlayerType1(){
	  return m_LoadPlayerType1;
	}
	/**
	 * Returns the player two type that is loaded from the CSV file
	 *
	 * @return m_LoadPlayerType2
	 */
	public String getLoadPlayerType2(){
	  return m_LoadPlayerType2;
	}
	/**
	 * Returns the turn that is loaded from the CSV file
	 *
	 * @return m_LoadTime
	 */
	public int getLoadTurn(){
	  return m_LoadTime;
	}

	/**
	 * Returns the game type that is loaded from the CSV file
	 *
	 * @return m_LoadGameType
	 */
	public String getLoadGameType(){
	  return m_LoadGameType;
	}


	/**
	*@param the current games board state
	*
	*@return boolean
	*/
	public boolean saveData(String gameType, C4AndOthelloBoardStore board, int time, String name1, String name2,
                            String playerType1, String playerType2, int turn) throws IOException {

		System.out.println("Saving....");
		nameFile(SAVE);
		m_FileName = PATH+ m_FileName +FILETYPE;
		m_Writer = new CSVWriter(new FileWriter(m_FileName));
		m_Data = new ArrayList<String[]>();
		m_LoadBoard = board.getBoard();

		for (int i = 0; i < BOARD_ROWS; i++) {
		  for (int j = 0; j < BOARD_COLS; j++) {
		    if (m_LoadBoard[j][i].getColour().equals("Black")){
		      m_Data.add(new String[] {gameType, String.valueOf(j), String.valueOf(i), m_LoadBoard[j][i].getColour(), name1, playerType1,
                                       String.valueOf(turn), String.valueOf(time)});
		    } else if (m_LoadBoard[j][i].getColour().equals("White")){
		      m_Data.add(new String[] {gameType, String.valueOf(j), String.valueOf(i), m_LoadBoard[j][i].getColour(), name2, playerType2,
                                       String.valueOf(turn), String.valueOf(time)});
		    } else {
		      m_Data.add(new String[] {gameType, String.valueOf(j), String.valueOf(i), m_LoadBoard[j][i].getColour(),
                                       "", "", String.valueOf(turn), String.valueOf(time)});
		    }
		  }
		}

		m_Writer.writeAll(m_Data);
		m_Writer.close();
		return true;
	}

	/**
	*
	*/

	public boolean loadData() throws IOException{

		showFileBrowser();
		m_FileName = PATH+ m_FileName;
		if (fileFound()){
			if (readGrid()){
				return true;
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	*
	*/

	private boolean readGrid() throws IOException{

	String[] row = null;
    Piece piece;
    while((row = m_CSVReader.readNext()) != null) {
      if (row[0].equals("Oth")) {
	      if (row[THIRD_ROW].equals("Black")){
	        piece = new Piece("Black");
	        m_LoadName1 = row[FOURTH_ROW];
	        m_LoadPlayerType1 = row[FIFTH_ROW];
	      } else if (row[THIRD_ROW].equals("White")){
	        m_LoadName2 = row[FOURTH_ROW];
	        m_LoadPlayerType2 = row[FIFTH_ROW];
	        piece = new Piece("White");
	      } else {
	        piece = new Piece("");
	      }
	      m_LoadGameType = row[0];
	      m_LoadTime = Integer.parseInt(row[SEVENTH_ROW]);
	      m_LoadTurn = Integer.parseInt(row[SIXTH_ROW]);

	      m_othelloGameLogic.getBoard().setPiece2(piece, Integer.parseInt(row[1]), Integer.parseInt(row[SECOND_ROW]));
	    } else {
	    	m_CSVReader.close();
	    	return false;
	    }
    }
    m_CSVReader.close();
    System.out.println("Load Test Data(Othello):");
    for (int i = 0; i < BOARD_ROWS; i++) {
      for (int j = 0; j < BOARD_COLS; j++) {
      System.out.println(m_LoadGameType +j+", " +i + ", "+ m_othelloGameLogic.getBoard().getBoard()[j][i].getColour()
              + ", "+ m_LoadTime+ ", "+ m_LoadName1+ ", "+ m_LoadName2+
              ", "+ m_LoadPlayerType1+ ", "+ m_LoadPlayerType2 + ", " + m_LoadTurn);
      }
    }
    return true;
	}

	private void showFileBrowser(){

		JFrame frame = new JFrame();
		FileDialog fc = new FileDialog(frame, "Load a Game Save", FileDialog.LOAD);
		//set default directory
		fc.setDirectory(PATH);
		//set file format
		fc.setFile("*.csv");
		//enable the file dialog
		fc.setVisible(true);
		//string to store file name
		m_FileName = fc.getFile();
		//if to catch the cancel exception
		if(m_FileName == null){
			System.out.println("You have cancelled the choice");
		}else{
			System.out.println("You have chosen " + m_FileName);
		}

	}

	/**
	*@ return boolean
	*/
		private boolean fileFound(){
		try{
		  m_CSVReader = new CSVReader(new FileReader(m_FileName));
		  } catch (FileNotFoundException e){
				System.out.println("Input file not found.");
				return false;
			}
			return true;
		}

		/**
		*@return boolean
		*/
		private boolean nameFile(String op) throws IOException{
			try{
			 m_FileName = JOptionPane.showInputDialog("Enter "+ op +" Name");
			} catch (Exception e){
			  e.printStackTrace();
			}
			return true;
		}

		public static void main(String[] args) throws IOException{

            final int TIME = 60;
            final int I_VALUE = 5;

			Piece[][] newBoard = new Piece[BOARD_ROWS][BOARD_COLS];
			OthelloGameLogic othelloGameLogic = new OthelloGameLogic();
			OthSaveManager othSaveManager = new OthSaveManager();
			C4AndOthelloBoardStore board = new C4AndOthelloBoardStore();
			//ProgramController programController = new ProgramController();

			String name1 = "Dave";
			String name2 = "Hal-2000";
			String playerType1 = "Human";
			String playerType2 = "Hard";
			int turn = 1;

			/** If the Game is Othello then load Othello Names*/

			for (int i = 0; i < BOARD_ROWS; i++) {
			  for (int j = 0; j < BOARD_COLS; j++) {
			    othelloGameLogic.setPiece(j, i, othelloGameLogic.getPlayer(1), true);
			    if (i == I_VALUE){
			    	othelloGameLogic.setPiece(j, i, othelloGameLogic.getPlayer(0), true);
			    }
			    System.out.println("Setting up testing: "+othelloGameLogic.getBoard().getBoard()[j][i].getColour());
			  }
			}

			othSaveManager.saveData("Oth", othelloGameLogic.getBoard(), TIME, name1, name2, playerType1, playerType2, turn);
			if(othSaveManager.loadData()){
				othelloGameLogic = othSaveManager.getLoadGame();
				if(test){
					board = othelloGameLogic.getBoard();
					newBoard = board.getBoard();
					for(int i = 0; i < BOARD_ROWS; i++) {
						for (int j = 0; j < BOARD_COLS; j++) {
							System.out.println(newBoard[j][i].getColour());
						}
					}
				}
			}else{
				System.out.println("Returning to menu......");
			}
		}

private static final String PATH ="../SAVEDATA/OTH/";
private static final String FILETYPE = ".csv";
private static final int BOARD_ROWS = 8;
private static final int BOARD_COLS = 8;
private static final String SAVE = "save";
private static final int SECOND_ROW = 2;
private static final int THIRD_ROW = 3;
private static final int FOURTH_ROW = 4;
private static final int FIFTH_ROW = 5;
private static final int SIXTH_ROW = 6;
private static final int SEVENTH_ROW = 7;



}