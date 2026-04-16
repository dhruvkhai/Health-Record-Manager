import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    public static void savePatients(Path filePath, List<Patient> patients) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Patient patient : patients) {
            lines.add(patient.toCsvLine());
        }
        Files.write(filePath, lines, StandardCharsets.UTF_8);
    }

    public static List<Patient> loadPatients(Path filePath) throws IOException {
        List<Patient> patients = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return patients;
        }
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }
            Patient patient = Patient.fromCsvLine(line);
            if (patient != null) {
                patients.add(patient);
            }
        }
        return patients;
    }
}
