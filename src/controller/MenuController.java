package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuController implements Initializable
{
	
	@FXML
    private Button twoPlayers;
	@FXML
    private Button soloGame;
	@FXML
    private Button aide;
	
	@FXML
	private AnchorPane background;
	
	@FXML
	private StackPane parentContainer;
	
	@FXML
	private Button config;
    public void MenuControllerAction(Stage primaryStage) {
    	try 
		{
	
	    Parent root = FXMLLoader.load(getClass().getResource("/res/layouts/Menu.fxml"));
	    primaryStage.setScene(new Scene(root));
	    primaryStage.setTitle("Tic Tac Toe");
	    primaryStage.setWidth(900);
	    primaryStage.setResizable(false);
	    primaryStage.show();
			
		   
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	     
		 
		 twoPlayers.setVisible(false);
		 soloGame.setVisible(false);
		
	}
	
	// private methods pour la classe elle m�me
	
	  private void showButton(Button b){
		  b.setVisible(true);

	  }
	
	
	@SuppressWarnings("unused")
	private void redirect(String nameView , Button clicked) throws IOException{
		ActionEvent event;
		Parent root = FXMLLoader.load(getClass().getResource("/res/layouts/" + nameView + ".fxml"));
		
		
		Scene scene = clicked.getScene();
		
		root.translateYProperty().set(scene.getHeight());
		parentContainer.getChildren().add(root);
		
		Timeline timeLine = new Timeline();
		KeyValue kv = new KeyValue(root.translateYProperty(), 0 , Interpolator.DISCRETE);
		KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
		timeLine.getKeyFrames().add(kf);

		timeLine.play();
	}

	
	// controllers -------------------------------
	
	
	@FXML
	public void pastToTwoPlayersScene(ActionEvent event) throws IOException{

		redirect("TwoPlayersPanel" , twoPlayers);

	}
	
	  @FXML
	  
	  public void pastToOnePlayerScene()throws IOException{
		  redirect("OnePlayerPanel" , soloGame);
	  }
	  
	  
	 @FXML
	 public void aideButton() throws IOException{
		 redirect("Instru" , aide);
	 }
	  
	  
	  public void newPart(){
		    aide.setVisible(false);
            showButton(twoPlayers);
            showButton(soloGame);
		  
	  }
	  
	  
	  @FXML  
	  public void restartPart() throws IOException{
		  System.out.println("restart Part :)");
		  
		  TwoPlayersGameController.gameResumes = true;
		  redirect("TwoPlayersPanel" , twoPlayers);
		  
		  
	  }


	public void configurer(ActionEvent event) throws IOException {
		redirect("configuration" , config);
	}
}