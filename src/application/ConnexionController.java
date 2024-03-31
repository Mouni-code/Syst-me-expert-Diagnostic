package application;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ConnexionController {
	
	public ConnexionController() {
		
	}
	
	@FXML
	private Button login_button;
	@FXML
	private Label wrongLogin;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Button registerButton;
	
	public void userLogin(ActionEvent event) throws IOException{
		checkLogin(event);
	}
	
	public void registerButton(ActionEvent event) throws IOException {
			Main m = new Main();
	        m.changeScene(event, "inscription.fxml");
	    }
	    
	private void checkLogin(ActionEvent event) throws IOException {
	    Main m = new Main();
	    File f = new File("database.txt");
	    if (f.exists()) {
	        Scanner scanner = new Scanner(f);
	        String line;
	        while (scanner.hasNextLine()) {
	            line = scanner.nextLine();
	            if (line.startsWith(",")) {
	                String[] parts = line.split(",");
	                String user = parts[2];
	                String pass = parts[4];
	                if (username.getText().equals(user) && password.getText().equals(pass)) {
	                    //wrongLogin.setText("Success!");
	                    m.changeScene(event, "MenuUser.fxml");
	                    return;
	                }
	            }
	        }
	        scanner.close();
	    }

	    if (username.getText().isEmpty() || password.getText().isEmpty()) {
	        wrongLogin.setText("Please enter your data.");
	    } else if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
	        m.changeScene(event, "MenuAdmin.fxml");
	    }
	    else {
	        wrongLogin.setText("Wrong username or password!");
	    }
	}
	

}
