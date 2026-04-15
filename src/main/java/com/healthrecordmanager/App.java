package com.healthrecordmanager;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.healthrecordmanager.model.MedicalRecord;
import com.healthrecordmanager.model.Patient;
import com.healthrecordmanager.service.HealthRecordService;

public class App {
    private static final String STORAGE_FILE = "patients.dat";

    public static void main(String[] args) {
        HealthRecordService service = new HealthRecordService(STORAGE_FILE);
        service.loadPatients();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                printMenu();
                String option = scanner.nextLine().trim();
                switch (option) {
                    case "1" -> registerPatient(scanner, service);
                    case "2" -> searchPatient(scanner, service);
                    case "3" -> listPatients(service);
                    case "4" -> updatePatient(scanner, service);
                    case "5" -> addMedicalRecord(scanner, service);
                    case "6" -> updateMedicalRecord(scanner, service);
                    case "7" -> deletePatient(scanner, service);
                    case "8" -> deleteMedicalRecord(scanner, service);
                    case "9" -> saveAndExit(service);
                    default -> System.out.println("Invalid option. Please choose 1-9.");
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("=== Health Record Manager ===");
        System.out.println("1. Register new patient");
        System.out.println("2. Search patient by ID or name");
        System.out.println("3. List all patients");
        System.out.println("4. Update patient details");
        System.out.println("5. Add medical history entry");
        System.out.println("6. Update medical history entry");
        System.out.println("7. Delete patient");
        System.out.println("8. Delete medical history entry");
        System.out.println("9. Save and exit");
        System.out.print("Choose an option: ");
    }

    private static void registerPatient(Scanner scanner, HealthRecordService service) {
        System.out.print("Full name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Date of birth (YYYY-MM-DD): ");
        String dobText = scanner.nextLine().trim();
        System.out.print("Gender: ");
        String gender = scanner.nextLine().trim();

        try {
            LocalDate dob = LocalDate.parse(dobText);
            Patient patient = service.createPatient(name, dob, gender);
            System.out.println("Patient registered: " + patient.getSummary());
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use YYYY-MM-DD.");
        }
    }

    private static void searchPatient(Scanner scanner, HealthRecordService service) {
        System.out.print("Search by ID or full/partial name: ");
        String query = scanner.nextLine().trim();
        List<Patient> results = service.searchPatients(query);
        if (results.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        results.forEach(patient -> {
            System.out.println(patient.getSummary());
            patient.getMedicalHistory().forEach(record -> System.out.println("  - " + record.getSummary()));
        });
    }

    private static void listPatients(HealthRecordService service) {
        List<Patient> patients = service.getAllPatients();
        if (patients.isEmpty()) {
            System.out.println("No patients registered yet.");
            return;
        }
        patients.forEach(patient -> System.out.println(patient.getSummary()));
    }

    private static void updatePatient(Scanner scanner, HealthRecordService service) {
        System.out.print("Patient ID to update: ");
        String id = scanner.nextLine().trim();
        Patient patient = service.getPatientById(id);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.print("New full name (leave blank to keep): ");
        String name = scanner.nextLine().trim();
        System.out.print("New gender (leave blank to keep): ");
        String gender = scanner.nextLine().trim();
        System.out.print("New date of birth (YYYY-MM-DD, leave blank to keep): ");
        String dobText = scanner.nextLine().trim();

        try {
            LocalDate dob = dobText.isEmpty() ? null : LocalDate.parse(dobText);
            service.updatePatient(id, name.isEmpty() ? null : name, gender.isEmpty() ? null : gender, dob);
            System.out.println("Patient updated: " + service.getPatientById(id).getSummary());
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use YYYY-MM-DD.");
        }
    }

    private static void addMedicalRecord(Scanner scanner, HealthRecordService service) {
        System.out.print("Patient ID: ");
        String patientId = scanner.nextLine().trim();
        Patient patient = service.getPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.print("Visit date (YYYY-MM-DD): ");
        String visitDate = scanner.nextLine().trim();
        System.out.print("Doctor: ");
        String doctor = scanner.nextLine().trim();
        System.out.print("Diagnosis: ");
        String diagnosis = scanner.nextLine().trim();
        System.out.print("Notes: ");
        String notes = scanner.nextLine().trim();

        MedicalRecord record = service.addMedicalRecord(patientId, visitDate, doctor, diagnosis, notes);
        System.out.println("Added medical record: " + record.getSummary());
    }

    private static void updateMedicalRecord(Scanner scanner, HealthRecordService service) {
        System.out.print("Patient ID: ");
        String patientId = scanner.nextLine().trim();
        Patient patient = service.getPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.println("Medical entries:");
        patient.getMedicalHistory().forEach(record -> System.out.println(record.getRecordId() + ": " + record.getSummary()));
        System.out.print("Record ID to update: ");
        String recordId = scanner.nextLine().trim();

        System.out.print("New visit date (YYYY-MM-DD, leave blank to keep): ");
        String visitDate = scanner.nextLine().trim();
        System.out.print("New doctor (leave blank to keep): ");
        String doctor = scanner.nextLine().trim();
        System.out.print("New diagnosis (leave blank to keep): ");
        String diagnosis = scanner.nextLine().trim();
        System.out.print("New notes (leave blank to keep): ");
        String notes = scanner.nextLine().trim();

        MedicalRecord updated = service.updateMedicalRecord(patientId, recordId,
                visitDate.isEmpty() ? null : visitDate,
                doctor.isEmpty() ? null : doctor,
                diagnosis.isEmpty() ? null : diagnosis,
                notes.isEmpty() ? null : notes);

        if (updated != null) {
            System.out.println("Medical record updated: " + updated.getSummary());
        } else {
            System.out.println("Record not found.");
        }
    }

    private static void deletePatient(Scanner scanner, HealthRecordService service) {
        System.out.print("Patient ID to delete: ");
        String id = scanner.nextLine().trim();
        if (service.deletePatient(id)) {
            System.out.println("Patient deleted.");
        } else {
            System.out.println("Patient not found.");
        }
    }

    private static void deleteMedicalRecord(Scanner scanner, HealthRecordService service) {
        System.out.print("Patient ID: ");
        String patientId = scanner.nextLine().trim();
        System.out.print("Record ID to delete: ");
        String recordId = scanner.nextLine().trim();
        if (service.deleteMedicalRecord(patientId, recordId)) {
            System.out.println("Medical record deleted.");
        } else {
            System.out.println("Patient or record not found.");
        }
    }

    private static void saveAndExit(HealthRecordService service) {
        service.savePatients();
        System.out.println("Data saved. Exiting application.");
        System.exit(0);
    }
}
