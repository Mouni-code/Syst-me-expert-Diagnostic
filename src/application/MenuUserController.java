package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuUserController {

    private static final String FILE_PATH = "C:\\Users\\Mouna\\eclipse-workspace\\SysExpert\\src\\application\\BDC.txt";

    @FXML
    private ChoiceBox<String> choixFaits;
    
    @FXML
    private TextField autre;

    private List<Diagnostic> diagnostics;

    @FXML
    public void initialize() {
        initializeDiagnostics();
        remplirChoiceBox();
    }

    private void initializeDiagnostics() {
        try {
            diagnostics = DiagnosticFileReader.readDiagnosticsFromFile(FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void remplirChoiceBox() {
        ObservableList<String> faitsList = FXCollections.observableArrayList();
        for (Diagnostic diagnostic : diagnostics) {
            faitsList.add(diagnostic.getProblem());
        }
        choixFaits.setItems(faitsList);
    }
    
    @FXML
    public void onClick(ActionEvent event) throws IOException {
        String selectedProblem = choixFaits.getValue();
        if (selectedProblem != null && !selectedProblem.isEmpty()) {
            List<String> selectedCausesList = new ArrayList<>();
            for (Diagnostic diagnostic : diagnostics) {
                if (diagnostic.getProblem().equals(selectedProblem)) {
                    List<String> causes = diagnostic.getCauses();
                    selectedCausesList.addAll(causes);
                }
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("DiagnosticResult.fxml"));
            Parent root = loader.load();
            DiagnosticResultController diagresult = loader.getController();
            diagresult.afficherCauses(selectedCausesList);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            // Gérer le cas où aucun problème n'est sélectionné dans la ChoiceBox
            System.out.println("Aucun problème sélectionné.");
        }
    }

    @FXML
    public void OtherClick(ActionEvent event) {
        String newProblem = autre.getText().trim();
        if (!newProblem.isEmpty()) {
            Diagnostic newDiagnostic = new Diagnostic(newProblem, new ArrayList<>());
            diagnostics.add(newDiagnostic);
            remplirChoiceBox(); // Mettre à jour la ChoiceBox avec le nouveau fait
            System.out.println("Nouveau problème ajouté : " + newProblem);

            try {
                DiagnosticFileWriter.writeDiagnosticToFile(newDiagnostic);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
