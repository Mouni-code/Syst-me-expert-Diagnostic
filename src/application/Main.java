package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Main extends Application {
	
	private static Stage stg;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage primaryStage) throws Exception{
		
		stg=primaryStage;
		
		primaryStage.setResizable(false);
		
		Parent root = FXMLLoader.load(getClass().getResource("connexion.fxml"));
		Scene scene = new Scene(root,600,400);
		Stage stage = new Stage();
		
		stage.setOnCloseRequest(event -> logout(stage));
		
		Image icon = new Image("assets/rep.png");
		stage.getIcons().add(icon);
		stage.setTitle("Diagnostic de pannes");
		
	
		stage.setScene(scene);
		stage.show();
	}

	public void changeScene(ActionEvent event, String fxml) throws IOException{
		Parent pane = FXMLLoader.load(getClass().getResource(fxml));
		stg=(Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(pane);
		stg.setScene(scene);
		stg.show();
		
	}
	
public void logout(Stage stage) {
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Déconnexion");
    	alert.setHeaderText("T'es entrain de te déconnecter!");
    	alert.setContentText("T'es sûr de vouloir te déconnecter?");
    	
    	if (alert.showAndWait().get()== ButtonType.OK) {
        	System.out.println("You successfully logout!");
        	stage.close();
    	}
    }
}

