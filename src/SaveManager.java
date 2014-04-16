import java.io.IOException;

abstract public class SaveManager{

  public abstract boolean loadData() throws IOException;
  public abstract boolean saveData(String gameType, Board board, int time, String name1, String name2,
                          String playerType1, String playerType2, int turn) throws IOException;
  public abstract boolean readGrid() throws IOException;
  public abstract void showFileBrowser();
  public abstract boolean fileFound();
  public abstract boolean nameFile(String op) throws IOException;

}