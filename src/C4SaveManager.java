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
 * @author Cameron Steer
 * @brief Saves and loads a connect 4 game
 * @details Reads in a CSV file in order to load a game state,
 * or writes to a CSV file in order to save the games state.
 *
 */
public class C4SaveManager {

  private static boolean test = true;
  private String m_FileName;
  private CSVWriter m_Writer;
  private CSVReader m_CSVReader;
  private List<String[]> m_Data;
  private Piece[][] m_LoadBoard;
  private int m_option;
  private Connect4GameLogic m_Connect4GameLogic = new Connect4GameLogic();

  private String m_LoadGameType = "";
  private int m_LoadTime;
  private String m_LoadName1;
  private String m_LoadName2;
  private String m_LoadPlayerType1;
  private String m_LoadPlayerType2;
  private int m_LoadTurn;

  /**
   * Returns the board thats loaded from the CSV file
   *
   * @return m_LoadBoard
   */
  public Connect4GameLogic getLoadGame(){
    return m_Connect4GameLogic;
  }
  /**
   * Returns the timer thats loaded from the CSV file
   *
   * @return m_LoadTime
   */
  public int getLoadTime(){
    return m_LoadTime;
  }
  /**
   * Returns the player one name thats loaded from the CSV file
   *
   * @return m_LoadName1
   */
  public String getLoadName1(){
    return m_LoadName1;
  }
  /**
   * Returns the player two name thats loaded from the CSV file
   *
   * @return m_LoadName2
   */
  public String getLoadName2(){
    return m_LoadName2;
  }
  /**
   * Returns the player one type thats loaded from the CSV file
   *
   * @return m_LoadPlayerType1
   */
  public String getLoadPlayerType1(){
    return m_LoadPlayerType1;
  }
  /**
   * Returns the player two type thats loaded from the CSV file
   *
   * @return m_LoadPlayerType2
   */
  public String getLoadPlayerType2(){
    return m_LoadPlayerType2;
  }
  /**
   * Returns the turn thats loaded from the CSV file
   *
   * @return m_LoadTime
   */
  public int getLoadTurn(){
    return m_LoadTime;
  }
  /**
   * Returns the game type thats loaded from the CSV file
   *
   * @return m_LoadGameType
   */
  public String getLoadGameType(){
    return m_LoadGameType;
  }

  /**
   * Writes the current sate of the board to a new CSV file, named by the user
   *
   * @param the current games board state
   * @return boolean
   */
  public boolean saveData(String gameType, C4AndOthelloBoardStore board, int time, String name1, String name2,
                          String playerType1, String playerType2, int turn) throws IOException{
    System.out.println("Saving....");
    nameFile(SAVE);
    m_FileName = PATH+ m_FileName +FILETYPE;
    m_Writer = new CSVWriter(new FileWriter(m_FileName));
    m_Data = new ArrayList<String[]>();
    m_LoadBoard = board.getBoard();

    for (int i = 0; i < BOARD_ROWS; i++) {
      for (int j = 0; j < BOARD_COLS; j++) {
        if (m_LoadBoard[j][i].getColour().equals("Red")){
          m_Data.add(new String[] {gameType, String.valueOf(j), String.valueOf(i), m_LoadBoard[j][i].getColour(),
                     name1, playerType1, String.valueOf(turn), String.valueOf(time)});

        } else if (m_LoadBoard[j][i].getColour().equals("Yellow")){
          m_Data.add(new String[] {gameType, String.valueOf(j), String.valueOf(i), m_LoadBoard[j][i].getColour(),
                     name2, playerType2, String.valueOf(turn), String.valueOf(time)});

        } else {
          m_Data.add(new String[] {gameType, String.valueOf(j), String.valueOf(i),
                     m_LoadBoard[j][i].getColour(), "", "", String.valueOf(turn), String.valueOf(time)});
        }
      }
    }
    m_Writer.writeAll(m_Data);
    m_Writer.close();
    return true;
  }

  /**
   * Calls the save file selected by the user, if successful the data is loaded else false is returned
   *
   * @return boolean
   */
  public boolean loadData() throws IOException{
    showFileBrowser();
    m_FileName = PATH+ m_FileName;
    if (fileFound()){
      readGrid();
    } else {
      return false;
    }
    return true;
  }

  /**
   * Reads the data from the selected file and adds it to the temp 2d array for board
   *
   * @return boolean
   */
  private boolean readGrid() throws IOException{

    String[] row = null;
    Piece piece;

    while((row = m_CSVReader.readNext()) != null) {
      if (row[0].equals("C4")) {
        if (row[THIRD_ROW].equals("Red")){
          piece = new Piece("Red");
          m_LoadName1 = row[FOURTH_ROW];
          m_LoadPlayerType1 = row[FIFTH_ROW];
        } else if (row[THIRD_ROW].equals("Yellow")){
          m_LoadName2 = row[FOURTH_ROW];
          m_LoadPlayerType2 = row[FIFTH_ROW];
          piece = new Piece("Yellow");
        } else {
          piece = new Piece("");
        }
        m_LoadGameType = row[0];
        m_LoadTime = Integer.parseInt(row[SEVENTH_ROW]);
        m_LoadTurn = Integer.parseInt(row[SIXTH_ROW]);
        m_Connect4GameLogic.getBoard().setPiece2(piece, Integer.parseInt(row[1]), Integer.parseInt(row[2]));
      } else {
        JOptionPane.showMessageDialog(null, "Incorrect File");
        return false;
      }
    }
    System.out.println("Load Test Data (C4):");
    m_CSVReader.close();

    for (int i = 0; i < BOARD_ROWS; i++) {
      for (int j = 0; j < BOARD_COLS; j++) {
      System.out.println(m_LoadGameType + j+", " +i + ", "+ m_Connect4GameLogic.getBoard().getBoard()[j][i].getColour()
                         + ", "+ m_LoadTime+ ", "+ m_LoadName1+ ", "+ m_LoadName2+ ", "+ m_LoadPlayerType1+
                         ", "+ m_LoadPlayerType2 + ", " + m_LoadTurn);
      }
    }
    return true;
  }

  // private boolean fileChooser(){
  private void showFileBrowser() {
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
    if (m_FileName == null)
      System.out.println("You cancelled the choice");
    else
      System.out.println("You chose " + m_FileName);
  }

  /**
   * Checks if the selected file exists
   *
   * @return boolean
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
   * Uses a input box to get a name of a file from the user
   *
   * @return boolean
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

    Piece[][] newBoard = new Piece[BOARD_ROWS][BOARD_COLS];
    Connect4GameLogic connect4GameLogic = new Connect4GameLogic();
    C4SaveManager c4SaveManager = new C4SaveManager();
    C4AndOthelloBoardStore board = new C4AndOthelloBoardStore();

    String gameType = "C4";
    int time = 60;
    String name1 = "Dave";
    String name2 = "Hal-2000";
    String playerType1 = "Human";
    String playerType2 = "Hard";
    int turn = 1;

    for (int i = 0; i < BOARD_ROWS; i++) {
      for (int j = 0; j < BOARD_COLS; j++) {
        if (i == FIFTH_ROW) {
          connect4GameLogic.setPiece(j, i, connect4GameLogic.getPlayer(0));
        }
        connect4GameLogic.setPiece(j, i, connect4GameLogic.getPlayer(1));
        System.out.println("Setting up testing: "+connect4GameLogic.getBoard().getBoard()[j][i].getColour());
      }
    }

    c4SaveManager.saveData(gameType, connect4GameLogic.getBoard(), time, name1, name2, playerType1, playerType2, turn);
    if (c4SaveManager.loadData()) {
      connect4GameLogic = c4SaveManager.getLoadGame();
      if (test){
        board = connect4GameLogic.getBoard();
        newBoard = board.getBoard();
        for (int i = 0; i < BOARD_ROWS; i++) {
          for (int j = 0; j < BOARD_COLS; j++) {
            System.out.println(newBoard[j][i].getColour());
            System.out.println( j+", " +i + ", "+ connect4GameLogic.getBoard().getBoard()[j][i].getColour());
          }
        }
      }
    } else {
      System.out.println("Returning to menu......");
    }
  }
  private static final String PATH = "../SAVEDATA/C4/";
  private static final String FILETYPE = ".csv";
  private static final int BOARD_ROWS = 7;
  private static final int BOARD_COLS = 10;
  private static final String SAVE = "save";
  private static final int SEVENTH_ROW = 7;
  private static final int FIFTH_ROW = 5;
  private static final int SIXTH_ROW = 6;
  private static final int FOURTH_ROW = 4;
  private static final int THIRD_ROW = 3;


}
