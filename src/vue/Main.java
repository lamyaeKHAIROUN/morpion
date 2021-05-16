package vue;


import controller.MenuController;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application 
{	
	
	public static void main(String[] args) 
	{
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage){	
		
		// en �xecute l'action MenuControllerAction du MenuController pour faire appel � la vue du menu controller
		new MenuController().MenuControllerAction(primaryStage);
		
	}
	
	
	

}
