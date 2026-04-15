package com.healthrecordmanager.repository;

import com.healthrecordmanager.model.Patient;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PatientRepository implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Map<String, Patient> patientMap = new HashMap<>();

    public void save(Patient patient) {
        patientMap.put(patient.getId(), patient);
    }

    public Patient findById(String patientId) {
        return patientMap.get(patientId);
    }

    public boolean deleteById(String patientId) {
        return patientMap.remove(patientId) != null;
    }

    public Collection<Patient> findAll() {
        return patientMap.values();
    }

    public boolean existsById(String patientId) {
        return patientMap.containsKey(patientId);
    }

    public void saveToFile(String fileName) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(this);
        } catch (IOException e) {
            System.err.println("Unable to save patients: " + e.getMessage());
        }
    }

    public static PatientRepository loadFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return new PatientRepository();
        }

        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName))) {
            return (PatientRepository) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Unable to load patients: " + e.getMessage());
            return new PatientRepository();
        }
    }
}
package com.healthrecordmanager.repository;

import com.healthrecordmanager.model.Patient;

import java.util.HashMap;
import java.util.Map;

public class PatientRepository {
    private final Map<String, Patient> patients = new HashMap<>();

    public void save(Patient patient) {
        patients.put(patient.getId(), patient);
    }

    public Patient findById(String id) {
        return patients.get(id);
    }

    public boolean delete(String id) {
        return patients.remove(id) != null;
    }

    public Map<String, Patient> findAll() {
        return patients;
    }
}
