package controller;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import model.Part;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class TwoPlayersGameController implements Initializable
{

	@FXML
	protected Button returnMenu;


	@FXML
	protected Pane pane;
	

	@FXML
	protected Pane paneMenu;

	@FXML
	ImageView image;

	@FXML
	Button valider;
	
	@FXML
	Button save;
	
	@FXML
	Text tour;
	
	@FXML
	Pane paneTurn;

	@FXML
	ComboBox<String> comboBoxFirstTurn;
	@FXML
	Label scor1;
	@FXML
	Label scor2;
	 int i=0,j=0;
	

	
	public static boolean turnX = true;
	private boolean playable = true;
	private List<Combo> combos = new ArrayList<Combo>();
	private Tile[][] board ;
	private String [] symboles = {"X" , "O"};
	
	// dimention du plateau
		protected int dimention = 3;
		
		// matrice de stokage de mouvement des joueurs
		protected String[][] matrix = new String[this.dimention][this.dimention];
	
	//pour s'avoir si le joueur reprend le jeu
	public static boolean gameResumes = false;
	
	
	@SuppressWarnings("unused")
	private void StoringProfitPositions(){
		   // horizontal
		   for(int y=0 ; y<this.dimention ; y++){
			   if(this.dimention == 3)
			       combos.add(new Combo(board[0][y] ,board[1][y] , board[2][y]));
			   
		   }
		   
		   // vertical
		   for(int x=0 ; x<this.dimention ; x++){
			   combos.add(new Combo(board[x][0] ,board[x][1] , board[x][2]));

			   
			   
		   }
		   
		   // diagonals
		combos.add(new Combo(board[0][0] ,board[1][1] , board[2][2]));
		combos.add(new Combo(board[2][0] ,board[1][1] , board[0][2]));


	}


	private void drawGameSquare(int dimention , double cote){
		  
		  this.dimention = dimention;
		  this.board = new Tile[dimention][dimention];
		  pane.setPrefSize(510, 510);
		  pane.getChildren().clear();
		
		   for(int i=0 ; i<dimention ; i++){
	    	   for(int j=0 ; j<dimention ; j++){
	    		   Tile tile = new Tile(cote);
	    		   tile.tilePositionY = i;
	    		   tile.tilePositionX = j;
	    		   tile.setTranslateX(j*cote);
	    		   tile.setTranslateY(i*cote);
	    		   pane.getChildren().add(tile);
	    		   
	    		   board[j][i] = tile;
	    	   }
	       }
		   
		   StoringProfitPositions();
		   
	}
	
	
	@SuppressWarnings("unused")
	private void drawResumeGameSquare(int dimention , double cote , String[][] matrix){
		
		
		  this.dimention = dimention;
		  this.board = new Tile[dimention][dimention];
		  pane.setPrefSize(510, 510);
		  pane.getChildren().clear();
		
		   for(int i=0 ; i<dimention ; i++){
	    	   for(int j=0 ; j<dimention ; j++){
	    		   Tile tile = new Tile(cote);
	    		   tile.tilePositionY = i;
	    		   tile.tilePositionX = j;
	    		   tile.setTranslateX(j*cote);
	    		   tile.setTranslateY(i*cote);
	    		   if(matrix[i][j].equals("X") || matrix[i][j].equals("O")){   
	    			   tile.text.setText(matrix[i][j]);
	    			   tile.tileClicked = true;
	    			   this.matrix[i][j] = matrix[i][j];
	    			   
	    			   
	    			   if(matrix[i][j].equals("X")) 
	    				   tile.text.setFill(Color.WHITE);
	    			   else 
	    				   tile.text.setFill(Color.BROWN);
	    		   }
	    		   
	    		   pane.getChildren().add(tile);
	    		   
	    		   board[j][i] = tile;
	    	   }
	       }
		   
		   StoringProfitPositions();
	}
	
	
	@SuppressWarnings("unused")
	private void changeGameSquareDimention(){
		this.dimention = 3;
		this.matrix = new String[this.dimention][this.dimention];
		drawGameSquare(3 , 170);

	}
	
	private void drawResumeGameSquareAccordingToDimention(int dimention , String[][] jeux){
		this.dimention = 3;
		this.matrix = new String[this.dimention][this.dimention];
		drawResumeGameSquare(3 , 170 , jeux);


	}
	


	@FXML
	public void changeFirstTurn(){
		int position = comboBoxFirstTurn.getSelectionModel().getSelectedIndex();
		if(position == 0){
			// X tour 
			this.turnX = true;
			comboBoxFirstTurn.getItems().clear();
		}else if(position == 1){
			// O tour
		    this.turnX = false;
		    comboBoxFirstTurn.getItems().clear();
		}
	}
	

	
	@FXML
	public void returnMenu() throws IOException{
       Parent root = FXMLLoader.load(getClass().getResource("/res/layouts/Menu.fxml"));
		
       
		Scene scene = returnMenu.getScene();
		
		root.translateYProperty().set(scene.getHeight());
		
		StackPane parentContainer = (StackPane)scene.getRoot();
		parentContainer.getChildren().add(root);
		
		Timeline timeLine = new Timeline();
		KeyValue kv = new KeyValue(root.translateYProperty(), 0 , Interpolator.DISCRETE);
		KeyFrame kf = new KeyFrame(Duration.seconds(0.4), kv);
		timeLine.getKeyFrames().add(kf);

		timeLine.play();
	}
	
	
	// boutton de validation de toutes les options final
	
	@FXML
	public void valider(){
		// execute toutes les actions du menu paraméter + initialisation des variables
		this.board = new Tile[3][3];
		turnX = true;
		playable = true;
		combos.clear();
		changeGameSquareDimention();
		tour.setText("Tour : X");
		tour.setStyle("-fx-translate-x : 0 ; -fx-font-size : 25");
		comboBoxFirstTurn.getItems().setAll(this.symboles);
	}
	
	
	@FXML
	public void save(){

	    // appel du modèle (classe Part)
		Part part = new Part(this.dimention , matrix , turnX);
		part.newPart();
	}
	
	
	
	
	private void checkState(){
		
		int trueNumbers = 0;
		
		for (Combo combo : combos) {
			if(combo.isCompleate(this.dimention)){
				playable = false;	
				playWinAnimation(combo);
				break;
			}
			
			if(combo.checkMatchNull()){
				trueNumbers++;
			}			
	    }
		
		if(trueNumbers == (this.dimention * 2)+2){
			playable = false;
			tour.setText("Match Null !");
			tour.setStyle("-fx-translate-x : -30 ; -fx-font-size : 25");
		}
		
	}
	
	private void playWinAnimation(Combo combo){

		
		// ligne de check check
		Line line = new Line();
		line.setFill(null);
		line.setStrokeWidth(4);
		line.setStroke(Color.BLACK);
		
		line.setStartX(combo.tiles[0].getCenterX());
		line.setStartY(combo.tiles[0].getCenterY());
		
		line.setEndX(combo.tiles[0].getCenterX());
		line.setEndY(combo.tiles[0].getCenterY());
		
		
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), new KeyValue(line.endXProperty(), combo.tiles[this.dimention -1].getCenterX()),
				new KeyValue(line.endYProperty(), combo.tiles[this.dimention - 1].getCenterY())));
		timeline.play();
		
		pane.getChildren().add(line);
		
		

		// changement du texte par le symbole du gagnan
		tour.setText("Congratulations :  " + combo.tiles[0].getText() + " is the winner !");
		tour.setStyle("-fx-translate-x : -50 ; -fx-font-size : 25");
		if (combo.tiles[0].getText()=="X"){
			i++;
		}
		else if (combo.tiles[0].getText()=="O"){
			j++;
		}
		scor1.setText(String.valueOf(i));
		
		scor2.setText(String.valueOf(j));
		// animation de l'image
		TranslateTransition t = new TranslateTransition();
		t.setDuration(Duration.seconds(0.7));
		t.setAutoReverse(true);
		t.setCycleCount(2);
		t.setToX(300);
		t.setNode(image);
		t.play();
		
		
			

	}
	
	
	private class Combo{
		private Tile[] tiles;
		
		public Combo(Tile...tiles ) {
			this.tiles = tiles;
		}
		public boolean isCompleate(int dimention){
			
			if(tiles[0].getText().isEmpty())
				return false;

	    	else if(dimention == 3)
	    		
			    return tiles[0].getText().equals(tiles[1].getText()) && tiles[0].getText().equals(tiles[2].getText());

	    		return tiles[0].getText().equals(tiles[1].getText()) && tiles[0].getText().equals(tiles[2].getText()) 
	    				&& tiles[0].getText().equals(tiles[3].getText()) && tiles[0].getText().equals(tiles[4].getText())
	    				&& tiles[0].getText().equals(tiles[5].getText());
			
		}
		
		public boolean checkMatchNull(){
			int counter = 0;
			for (int i = 0; i < tiles.length; i++) {
					if (tiles[i].getText().equals("X") || tiles[i].getText().equals("O")) 
						++counter;	
			}
			
			if(counter == tiles.length){
				return true;
			}
			
			return false;		
			
		}
		
	}
	
	private class Tile extends StackPane {

		int tilePositionX;
		int tilePositionY;
		Text text = new Text();
		double cote;
		boolean tileClicked = false;

		public Tile(double cote) {
			this.cote = cote;
			Rectangle border = new Rectangle(cote, cote);
			border.setFill(null);
			border.setStrokeWidth(2);
			border.setStroke(javafx.scene.paint.Color.WHITE);

			text.setFont(Font.font(72));

			setAlignment(Pos.CENTER);
			getChildren().addAll(border, text);

			setOnMouseClicked(event -> {
				if (!playable) {
					return;
				}


				if (event.getButton() == MouseButton.PRIMARY) {
					if (tileClicked == false) {
						if (!turnX)
							return;
						drawX();
						tour.setText("Tour : O");
						turnX = false;
						tileClicked = true;
						checkState();

						// stocage dans le tableau matrix
						matrix[tilePositionX][tilePositionY] = this.getText();
					}

					System.out.println(tilePositionY + " - " + tilePositionX);


				} else if (event.getButton() == MouseButton.SECONDARY) {
					if (tileClicked == false) {
						if (turnX)
							return;
						turnX = true;
						drawO();
						tour.setText("Tour : X");
						tileClicked = true;
						checkState();

						// stocage dans le tableau matrix
						matrix[tilePositionX][tilePositionY] = this.getText();
					}
					System.out.println(tilePositionY + " - " + tilePositionX);

				}
			});

		}

		public String getText() {
			return text.getText();
		}

		private void drawX() {
			text.setText("X");
			text.setFill(Color.WHITE);
		}

		private void drawO() {
			text.setText("O");
			text.setFill(Color.BROWN);
		}

		private double getCenterX() {
			return getTranslateX() + (cote / 2);
		}

		private double getCenterY() {
			return getTranslateY() + (cote / 2);
		}
	}
	
	
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		  // initialiser la comboBox des tours (X ou O)
		   comboBoxFirstTurn.getItems().setAll(this.symboles);
		   
		
		
	  if(gameResumes){
		  
		  Part part = new Part();
		  // répuration de la dimention de laprtie
		  part.readDimention();
		  
		  String[][] jeux = new String[part.getDimention()][part.getDimention()];
		  
		  // récupération de la partie (stockage de X O depuis le un fichier text dans un tableau jeux)
		  part.getPart(jeux);
	  

		  if(part.getTurnX()){
			  turnX = true;
			  tour.setText("Tour : X");
		  }else{
			  turnX = false;
			  tour.setText("Tour : O");
		  }
		  	  
		  comboBoxFirstTurn.getItems().clear();
		  drawResumeGameSquareAccordingToDimention(part.getDimention() , jeux);
		  
	  }else{
	    drawGameSquare(3, 170);  
	  }
	   
	  gameResumes = false;
		   
	}
	
	

}