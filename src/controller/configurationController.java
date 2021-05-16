package controller;

import ai.Coup;
import ai.MultiLayerPerceptron;
import ai.SigmoidalTransferFunction;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

public class configurationController implements Initializable{

	@FXML
	private AnchorPane background;
	@FXML
	private Button cancel;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
	}

	private void redirect(String nameView , Button clicked) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("/res/layouts/" + nameView + ".fxml"));


		Scene scene = clicked.getScene();

		root.translateYProperty().set(scene.getHeight());
		background.getChildren().add(root);

		Timeline timeLine = new Timeline();
		KeyValue kv = new KeyValue(root.translateYProperty(), 0 , Interpolator.DISCRETE);
		KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
		timeLine.getKeyFrames().add(kf);

		timeLine.play();
	}

	public void back(ActionEvent event) throws IOException {
		redirect("Menu" , cancel);

	}
}
