import javax.swing.*;
import java.io.IOException;

/**
 * @author Cameron Steer, Jamie I Davies
 * @date March 21, 2014
 * @brief Main class to run the Splash Screen
 */

public class Main extends JFrame{
  public static void main(String[] args) throws IOException{
    /**ProgramController controller = new ProgramController();
    controller.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    controller.ProgramController();*/
	
	  SplashScreen splash = new SplashScreen();
	  splash.initSplash();
	  
  }
}