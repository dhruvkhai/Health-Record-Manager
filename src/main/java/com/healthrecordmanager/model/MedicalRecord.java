package com.healthrecordmanager.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class MedicalRecord extends Record {
    private static final long serialVersionUID = 1L;

    private String visitDate;
    private String doctor;
    private String diagnosis;
    private String notes;

    public MedicalRecord(String visitDate, String doctor, String diagnosis, String notes) {
        super(UUID.randomUUID().toString());
        this.visitDate = normalizeDate(visitDate);
        this.doctor = doctor;
        this.diagnosis = diagnosis;
        this.notes = notes;
    }

    private String normalizeDate(String visitDate) {
        try {
            return LocalDate.parse(visitDate).toString();
        } catch (DateTimeParseException e) {
            return visitDate;
        }
    }

    public String getVisitDate() {
        return visitDate;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getNotes() {
        return notes;
    }

    public void update(String visitDate, String doctor, String diagnosis, String notes) {
        if (visitDate != null && !visitDate.isBlank()) {
            this.visitDate = normalizeDate(visitDate);
        }
        if (doctor != null && !doctor.isBlank()) {
            this.doctor = doctor;
        }
        if (diagnosis != null && !diagnosis.isBlank()) {
            this.diagnosis = diagnosis;
        }
        if (notes != null && !notes.isBlank()) {
            this.notes = notes;
        }
    }

    @Override
    public String getSummary() {
        return String.format("%s | Doctor: %s | Diagnosis: %s | Notes: %s",
                visitDate, doctor, diagnosis, notes);
    }
}
