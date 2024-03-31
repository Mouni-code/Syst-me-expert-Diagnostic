package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DiagnosticResultController {

    @FXML
    private Label labelDiagnostic;

    @FXML
    private Label labelErreur;

    @FXML
    private Button logout;

    @FXML
    private Button back;

    @FXML
    private AnchorPane scenePane;

    private Stage stage;

    @FXML
    private TextArea textAreaCauses;

    private MenuUserController menuUserController;

    public void setMenuUserController(MenuUserController menuUserController) {
        this.menuUserController = menuUserController;
    }

    // Méthode pour afficher les causes dans textAreaCauses
    public void afficherCauses(List<String> causes) {
        StringBuilder causesText = new StringBuilder();
        for (String cause : causes) {
            causesText.append(cause).append("\n");
        }
        textAreaCauses.setText(causesText.toString());
    }


    public void goBack(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene(event, "MenuUser.fxml");
    }

    public void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText("T'es entrain de te déconnecter!");
        alert.setContentText("T'es sûr de vouloir te déconnecter?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("You successfully logout!");
            stage.close();
        }
    }
}

