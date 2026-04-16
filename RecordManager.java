import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecordManager {
    private final List<Patient> patients;
    private final Path dataFile;

    public RecordManager(Path dataFile) {
        this.dataFile = dataFile;
        this.patients = new ArrayList<>();
    }

    public void loadRecords() {
        try {
            patients.clear();
            patients.addAll(FileHandler.loadPatients(dataFile));
        } catch (IOException e) {
            System.out.println("Warning: Could not load records from file. Starting with an empty list.");
        }
    }

    public void saveRecords() {
        try {
            FileHandler.savePatients(dataFile, patients);
        } catch (IOException e) {
            System.out.println("Error: Unable to save records to file.");
        }
    }

    public int nextId() {
        int maxId = 0;
        for (Patient patient : patients) {
            maxId = Math.max(maxId, patient.getId());
        }
        return maxId + 1;
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
        saveRecords();
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients);
    }

    public Optional<Patient> getPatientById(int id) {
        return patients.stream().filter(p -> p.getId() == id).findFirst();
    }

    public boolean updatePatient(int id, String name, Integer age, String bloodGroup, String history) {
        Optional<Patient> optional = getPatientById(id);
        if (optional.isEmpty()) {
            return false;
        }
        Patient patient = optional.get();
        if (name != null && !name.isBlank()) {
            patient.setName(name);
        }
        if (age != null) {
            patient.setAge(age);
        }
        if (bloodGroup != null && !bloodGroup.isBlank()) {
            patient.setBloodGroup(bloodGroup);
        }
        if (history != null && !history.isBlank()) {
            patient.setHistory(history);
        }
        saveRecords();
        return true;
    }

    public boolean deletePatient(int id) {
        Optional<Patient> optional = getPatientById(id);
        if (optional.isEmpty()) {
            return false;
        }
        patients.remove(optional.get());
        saveRecords();
        return true;
    }

    public List<Patient> searchByName(String name) {
        List<Patient> matches = new ArrayList<>();
        if (name == null || name.isBlank()) {
            return matches;
        }
        String lowerName = name.toLowerCase();
        for (Patient patient : patients) {
            if (patient.getName().toLowerCase().contains(lowerName)) {
                matches.add(patient);
            }
        }
        return matches;
    }
}
