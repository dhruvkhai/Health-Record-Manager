package com.healthrecordmanager.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Patient extends Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private String gender;
    private final List<MedicalRecord> medicalHistory;

    public Patient(String fullName, LocalDate dateOfBirth, String gender) {
        super(fullName, dateOfBirth);
        this.id = UUID.randomUUID().toString();
        this.gender = gender;
        this.medicalHistory = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }

    public List<MedicalRecord> getMedicalHistory() {
        return Collections.unmodifiableList(medicalHistory);
    }

    public void addMedicalRecord(MedicalRecord record) {
        medicalHistory.add(record);
    }

    public boolean removeMedicalRecord(String recordId) {
        return medicalHistory.removeIf(record -> record.getRecordId().equals(recordId));
    }

    public MedicalRecord findMedicalRecord(String recordId) {
        return medicalHistory.stream()
                .filter(record -> record.getRecordId().equals(recordId))
                .findFirst()
                .orElse(null);
    }

    public void updateMedicalRecord(String recordId, String visitDate, String doctor, String diagnosis, String notes) {
        MedicalRecord record = findMedicalRecord(recordId);
        if (record != null) {
            record.update(visitDate, doctor, diagnosis, notes);
        }
    }

    public void updateDetails(String fullName, LocalDate dateOfBirth, String gender) {
        if (fullName != null && !fullName.isBlank()) {
            setFullName(fullName);
        }
        if (dateOfBirth != null) {
            setDateOfBirth(dateOfBirth);
        }
        if (gender != null && !gender.isBlank()) {
            this.gender = gender;
        }
    }

    public String getSummary() {
        return String.format("%s | ID: %s | DOB: %s | Gender: %s | Records: %d",
                getFullName(), id, getDateOfBirth(), gender, medicalHistory.size());
    }
}
