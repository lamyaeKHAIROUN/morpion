package controller;


import ai.Board;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.Part;
import vue.BoardView;
import vue.ConfigurationView;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OnePlayerPanel {

    @FXML
    protected Button returnMenu;


    @FXML
    private AnchorPane background;


    @FXML
    Pane pane;


    @FXML
    protected Pane paneMenu;




    @FXML
    Button valider;

    @FXML
    Button save;


    @FXML
    public static Pane paneTurn;

    @FXML
    ComboBox<String> comboBoxFirstTurn;


    public static boolean turnX = true;
    private boolean playable = true;
    private String [] symboles = {"X" , "AI"};

    // dimention du plateau
    protected int dimention = 3;

    // matrice de stokage de mouvement des joueurs
    protected String[][] matrix = new String[this.dimention][this.dimention];

    //pour s'avoir si le joueur reprend le jeu
    public static boolean gameResumes = false;

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
    public void returnMenu() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/res/layouts/Menu.fxml"));


        Scene scene = returnMenu.getScene();

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
    @FXML
    public void save(){
        System.out.println("partie sauvegarder");

        // appel du modèle (classe Part)
        Part part = new Part(this.dimention , matrix , turnX);
        part.newPart();
    }

    private JPanel boardAi, configuration;


    public void initialize(URL location, ResourceBundle resources) {

        //String[] colors = {"Rouge", "Blanc", "Vert", "Aqua" , "Gris"};
        // comboBoxColorSquare.getItems().setAll(colors);
        //comboBoxColorBackground.getItems().setAll(colors);

        // initialiser la comboBox des tours (X ou O)
        comboBoxFirstTurn.getItems().setAll(this.symboles);



        Board b = new Board();
        this.boardAi = new BoardView(b);
        this.configuration = new ConfigurationView(this.boardAi);


        final SwingNode NodeS = new SwingNode();
        createAndSetSwingContent(NodeS);
        pane.getChildren().add(NodeS);

        final SwingNode swingNode1 = new SwingNode();
        createAndSetSwing(swingNode1);
        paneMenu.getChildren().add(swingNode1);

    }


    private void createAndSetSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JPanel panel = new JPanel();
                panel.setSize(510, 510);
                panel.add(boardAi);
                swingNode.setContent(panel);
            }
        });
    }

    private void createAndSetSwing(final SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JPanel panel = new JPanel();
                panel.setSize(510, 510);
                panel.add(configuration);
                swingNode.setContent(panel);
            }
        });
    }

}
