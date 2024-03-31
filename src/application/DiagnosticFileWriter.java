package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DiagnosticFileWriter {
    private static final String FILE_PATH = "C:\\Users\\Mouna\\eclipse-workspace\\SysExpert\\src\\application\\BDC.txt";

    public static void writeDiagnosticToFile(Diagnostic diagnostic) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(diagnostic.getProblem() + ":");
            /*for (String cause : diagnostic.getCauses()) {
                writer.write(cause + ",");
            }*/
            writer.newLine();
        }
}
}