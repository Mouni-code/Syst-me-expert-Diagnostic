package application;

import java.io.*;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class InscriptionController {
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Button registerButton;
    @FXML
    private Label wrongLabel;

    int id = 1;

    public void registration(ActionEvent event) {
        if (username.getText().isEmpty() || password.getText().isEmpty() || email.getText().isEmpty()) {
            wrongLabel.setText("Please enter your data.");
        } else {
            try {
                File f = new File("database.txt");
                if (!f.exists()) {
                    f.createNewFile();
                }

                // Read the last ID from the file
                Scanner scanner = new Scanner(f);
                String line;
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    if (line.startsWith(",")) {
                        String[] parts = line.split(",");
                        int lastId = Integer.parseInt(parts[1]);
                        if (lastId >= id) {
                            id = lastId + 1;
                        }
                    }
                }
                scanner.close();

                PrintWriter pw = new PrintWriter(new FileWriter(f, true));
                pw.append("\n," + id + "," + username.getText() + "," + email.getText() + "," + password.getText());
                pw.close();

                Main m = new Main();
                m.changeScene(event, "connexion.fxml");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void goBack(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene(event, "connexion.fxml");
    }
}