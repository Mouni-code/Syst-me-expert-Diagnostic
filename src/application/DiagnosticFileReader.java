package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticFileReader {

    public static List<Diagnostic> readDiagnosticsFromFile(String filePath) throws IOException {
        List<Diagnostic> diagnostics = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String currentProblem = null;
            List<String> currentCauses = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    if (line.endsWith(":")) {
                        // Nouveau problème
                        if (currentProblem != null) {
                            // Ajouter le diagnostic actuel à la liste
                            diagnostics.add(new Diagnostic(currentProblem, currentCauses));
                            currentCauses = new ArrayList<>(); // Réinitialiser la liste des causes
                        }
                        currentProblem = line.substring(0, line.length() - 1).trim();
                    } else {
                        // Cause du problème actuel
                        currentCauses.add(line.trim());
                    }
                }
            }
            // Ajouter le dernier diagnostic après la boucle
            if (currentProblem != null) {
                diagnostics.add(new Diagnostic(currentProblem, currentCauses));
            }
        }
        return diagnostics;
    }
    
    public static List<String> readProblemsFromFile(String filePath) throws IOException {
        List<String> problems = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty() && line.endsWith(":")) {
                // Séparer le problème de la ligne
                String problem = line.substring(0, line.length() - 1).trim();
                problems.add(problem);
            }
        }

        reader.close();
        return problems;
    }

    public static List<String> readCausesForProblemFromFile(String filePath, String problem) throws IOException {
        List<String> causes = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        boolean readingCauses = false;

        while ((line = reader.readLine()) != null) {
            // Si la ligne commence par le problème suivi de ":"
            if (line.trim().startsWith(problem + ":")) {
                // Indiquer que nous commençons à lire les causes pour le problème spécifié
                readingCauses = true;
                // Extraire les causes après le ":"
                String[] parts = line.split(":");
                if (parts.length >= 2) {
                    String[] causeArray = parts[1].split(",");
                    for (String cause : causeArray) {
                        causes.add(cause.trim());
                    }
                }
            } else if (readingCauses && !line.trim().isEmpty()) {
                // Si nous sommes déjà en train de lire les causes et que la ligne n'est pas vide
                // Ajouter la cause à la liste
                causes.add(line.trim());
            } else if (readingCauses && line.trim().isEmpty()) {
                // Si nous avons fini de lire les causes pour le problème spécifié
                break;
            }
        }

        reader.close();
        return causes;
    }




}
