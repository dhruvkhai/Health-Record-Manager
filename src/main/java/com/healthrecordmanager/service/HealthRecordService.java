package com.healthrecordmanager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.healthrecordmanager.model.MedicalRecord;
import com.healthrecordmanager.model.Patient;
import com.healthrecordmanager.repository.PatientRepository;

public class HealthRecordService {
    private final PatientRepository repository;
    private final String storageFile;

    public HealthRecordService(String storageFile) {
        this.storageFile = storageFile;
        this.repository = PatientRepository.loadFromFile(storageFile);
    }

    public void loadPatients() {
        // Already loaded in constructor.
    }

    public void savePatients() {
        repository.saveToFile(storageFile);
    }

    public Patient createPatient(String fullName, LocalDate dateOfBirth, String gender) {
        Patient patient = new Patient(fullName, dateOfBirth, gender);
        repository.save(patient);
        return patient;
    }

    public Patient getPatientById(String patientId) {
        return repository.findById(patientId);
    }

    public List<Patient> getAllPatients() {
        return repository.findAll().stream().collect(Collectors.toList());
    }

    public boolean updatePatient(String patientId, String fullName, String gender, LocalDate dateOfBirth) {
        Patient patient = repository.findById(patientId);
        if (patient == null) {
            return false;
        }
        patient.updateDetails(fullName, dateOfBirth, gender);
        repository.save(patient);
        return true;
    }

    public MedicalRecord addMedicalRecord(String patientId, String visitDate, String doctor, String diagnosis, String notes) {
        Patient patient = repository.findById(patientId);
        if (patient == null) {
            throw new IllegalArgumentException("Patient not found.");
        }
        MedicalRecord record = new MedicalRecord(visitDate, doctor, diagnosis, notes);
        patient.addMedicalRecord(record);
        repository.save(patient);
        return record;
    }

    public MedicalRecord updateMedicalRecord(String patientId, String recordId, String visitDate, String doctor, String diagnosis, String notes) {
        Patient patient = repository.findById(patientId);
        if (patient == null) {
            return null;
        }
        MedicalRecord record = patient.findMedicalRecord(recordId);
        if (record == null) {
            return null;
        }
        record.update(visitDate, doctor, diagnosis, notes);
        repository.save(patient);
        return record;
    }

    public boolean deletePatient(String patientId) {
        return repository.deleteById(patientId);
    }

    public boolean deleteMedicalRecord(String patientId, String recordId) {
        Patient patient = repository.findById(patientId);
        boolean removed = patient != null && patient.removeMedicalRecord(recordId);
        if (removed) {
            repository.save(patient);
        }
        return removed;
    }

    public List<Patient> searchPatients(String query) {
        String lowerQuery = query.toLowerCase();
        return repository.findAll().stream()
                .filter(patient -> patient.getId().equalsIgnoreCase(query)
                        || patient.getFullName().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }
}
