import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Path dataFile = Paths.get("patients.csv");
        RecordManager recordManager = new RecordManager(dataFile);
        recordManager.loadRecords();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> addPatient(recordManager);
                case 2 -> viewAllPatients(recordManager);
                case 3 -> updatePatient(recordManager);
                case 4 -> deletePatient(recordManager);
                case 5 -> searchPatients(recordManager);
                case 6 -> {
                    running = false;
                    System.out.println("Exiting. Goodbye!");
                }
                default -> System.out.println("Invalid option. Please choose 1-6.");
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("=== Health Record Manager ===");
        System.out.println("1. Add patient");
        System.out.println("2. View all patients");
        System.out.println("3. Update patient");
        System.out.println("4. Delete patient");
        System.out.println("5. Search patient");
        System.out.println("6. Exit");
    }

    private static void addPatient(RecordManager recordManager) {
        int id = recordManager.nextId();
        System.out.println("Enter patient details:");
        String name = readString("Name: ");
        int age = readInt("Age: ");
        String bloodGroup = readString("Blood group: ");
        String history = readString("Medical history: ");

        Patient patient = new Patient(id, name, age, bloodGroup, history);
        recordManager.addPatient(patient);
        System.out.println("Patient added with ID " + id + ".");
    }

    private static void viewAllPatients(RecordManager recordManager) {
        List<Patient> patients = recordManager.getAllPatients();
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        for (Patient patient : patients) {
            System.out.println(patient);
            System.out.println("-------------------------");
        }
    }

    private static void updatePatient(RecordManager recordManager) {
        int id = readInt("Enter patient ID to update: ");
        Optional<Patient> optional = recordManager.getPatientById(id);
        if (optional.isEmpty()) {
            System.out.println("Patient not found.");
            return;
        }
        Patient existing = optional.get();
        System.out.println("Current record:");
        System.out.println(existing);
        System.out.println("Leave blank to keep the current value.");

        String name = readOptionalString("New name: ");
        Integer age = readOptionalInt("New age: ");
        String bloodGroup = readOptionalString("New blood group: ");
        String history = readOptionalString("New medical history: ");

        boolean updated = recordManager.updatePatient(id, name, age, bloodGroup, history);
        if (updated) {
            System.out.println("Patient updated successfully.");
        } else {
            System.out.println("Failed to update patient.");
        }
    }

    private static void deletePatient(RecordManager recordManager) {
        int id = readInt("Enter patient ID to delete: ");
        boolean deleted = recordManager.deletePatient(id);
        if (deleted) {
            System.out.println("Patient deleted successfully.");
        } else {
            System.out.println("Patient not found.");
        }
    }

    private static void searchPatients(RecordManager recordManager) {
        System.out.println("Search by:");
        System.out.println("1. ID");
        System.out.println("2. Name");
        int option = readInt("Choose search type: ");
        if (option == 1) {
            int id = readInt("Enter patient ID: ");
            Optional<Patient> optional = recordManager.getPatientById(id);
            if (optional.isPresent()) {
                System.out.println(optional.get());
            } else {
                System.out.println("Patient not found.");
            }
        } else if (option == 2) {
            String name = readString("Enter name or partial name: ");
            List<Patient> matches = recordManager.searchByName(name);
            if (matches.isEmpty()) {
                System.out.println("No matching patients found.");
            } else {
                for (Patient patient : matches) {
                    System.out.println(patient);
                    System.out.println("-------------------------");
                }
            }
        } else {
            System.out.println("Invalid search option.");
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static String readOptionalString(String prompt) {
        System.out.print(prompt);
        String value = scanner.nextLine().trim();
        return value.isEmpty() ? null : value;
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static Integer readOptionalInt(String prompt) {
        System.out.print(prompt);
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Keeping current value.");
            return null;
        }
    }
}
