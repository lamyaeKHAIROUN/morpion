package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class AideController implements Initializable {

	@FXML
	BorderPane background;
	
	@FXML
	Button retour;
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		

	}
	
	public void returnMenu() throws IOException{
	       Parent root = FXMLLoader.load(getClass().getResource("/res/layouts/Menu.fxml"));
			
	       
			Scene scene = retour.getScene();
			
			root.translateYProperty().set(scene.getHeight());
			
			StackPane parentContainer = (StackPane)scene.getRoot();
			parentContainer.getChildren().add(root);
			
			Timeline timeLine = new Timeline();
			KeyValue kv = new KeyValue(root.translateYProperty(), 0 , Interpolator.EASE_IN);
			KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
			timeLine.getKeyFrames().add(kf);
			timeLine.setOnFinished(event1 -> {
				 parentContainer.getChildren().remove(background);
			});
			timeLine.play();
	}

}
