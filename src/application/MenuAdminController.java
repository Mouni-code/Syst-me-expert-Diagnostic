package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuAdminController {

	private static final String filePath = "C:\\Users\\Mouna\\eclipse-workspace\\SysExpert\\src\\application\\BDC.txt";
	
    @FXML
    private TextField textFieldProblemeAjout;

    @FXML
    private TextField textFieldCauseAjout;

    @FXML
    private Button boutonAjout;

    @FXML
    private ChoiceBox<String> choixProblemeSuppression;

    @FXML
    private Button boutonSuppression;

    @FXML
    private ChoiceBox<String> choixProblemeModification;

    @FXML
    private TextField textFieldNouveauProbleme;

    @FXML
    private ChoiceBox<String> choixCauseModification;

    @FXML
    private TextField textFieldNouvelleCause;

    @FXML
    private Button boutonModification;
    
    @FXML
    private Button menuUserGo;

    private List<Diagnostic> diagnostics;

    
    @FXML
    public void initialize() throws IOException {
        
    	initializeDiagnostics();
    	remplirChoixProblemes();
        choixProblemeModification.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                remplirChoixCauses(newValue);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void initializeDiagnostics() {
        try {
            diagnostics = DiagnosticFileReader.readDiagnosticsFromFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void add(ActionEvent event) throws IOException {
        String newProblem = textFieldProblemeAjout.getText().trim();
        String newCause = textFieldCauseAjout.getText().trim();

        if (!newProblem.isEmpty() && !newCause.isEmpty()) {
            Diagnostic newDiagnostic = new Diagnostic(newProblem, new ArrayList<>());
            newDiagnostic.getCauses().add(newCause);//prq ça marche pas???
            diagnostics.add(newDiagnostic);
            System.out.println("Nouveau problème ajouté : " + newProblem);
            try {
                DiagnosticFileWriter.writeDiagnosticToFile(newDiagnostic);
            } catch (IOException e) {
                e.printStackTrace();
            }
            remplirChoixProblemes();   
        }
    }

    

    public void remplirChoixProblemes() throws IOException {
        List<String> problems = DiagnosticFileReader.readProblemsFromFile(filePath);
        choixProblemeSuppression.getItems().clear();
        choixProblemeModification.getItems().clear();

        System.out.println(problems);

        choixProblemeSuppression.getItems().addAll(problems);
        choixProblemeModification.getItems().addAll(problems);
    }

    public void remplirChoixCauses(String selectedProblem) throws IOException {
        choixCauseModification.getItems().clear();

        List<String> causes = DiagnosticFileReader.readCausesForProblemFromFile(filePath, selectedProblem);

        choixCauseModification.getItems().addAll(causes);
    }

    @FXML
    public void delete(ActionEvent event) throws IOException {
        String selectedProblem = choixProblemeSuppression.getValue();
        if (selectedProblem != null) {
            removeLineFromFile(selectedProblem);
            remplirChoixProblemes();
        }
    }

    @FXML
    public void edit(ActionEvent event) throws IOException {
        String selectedProblem = choixProblemeModification.getValue();
        String newProblem = textFieldNouveauProbleme.getText().trim();
        String selectedCause = choixCauseModification.getValue();
        String newCause = textFieldNouvelleCause.getText().trim();

        if (selectedProblem != null && selectedCause != null && !newProblem.isEmpty() && !newCause.isEmpty()) {
            modifyProblemInFile(selectedProblem, newProblem, selectedCause, newCause);
            remplirChoixProblemes();
        }
    }

    @FXML
    private void removeLineFromFile(String lineToRemove) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            List<String> lines = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(lineToRemove)) {
                    lines.add(line);
                }
            }

            reader.close();

            FileWriter writer = new FileWriter(filePath);
            for (String newLine : lines) {
                writer.write(newLine + "\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void modifyProblemInFile(String oldProblem, String newProblem, String oldCause, String newCause) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            List<String> lines = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith(oldProblem + ":")) {
                    // Si la ligne correspond au problème à modifier
                    String[] parts = line.split(":");
                    if (parts.length >= 2) { // Vérifier s'il y a au moins deux éléments dans le tableau
                        String[] causeArray = parts[1].split(",");
                        for (int i = 0; i < causeArray.length; i++) {
                            if (causeArray[i].trim().equals(oldCause)) {
                                // Si la cause correspond à celle à modifier
                                causeArray[i] = newCause; // Modifier la cause
                                parts[1] = String.join(",", causeArray); // Rejoindre les causes modifiées
                                break;
                            }
                        }
                    }
                    line = newProblem + ": " + parts[1]; // Mettre à jour la ligne avec le nouveau problème et les causes
                }
                lines.add(line);
            }

            reader.close();

            FileWriter writer = new FileWriter(filePath);
            for (String newLine : lines) {
                writer.write(newLine + "\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void goToMenuUser(ActionEvent event)throws IOException{
    	Main m = new Main();
    	m.changeScene(event, "MenuUser.fxml");
    }
    

}
