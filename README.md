# Health Record Manager

A console-based Java application for managing patient health records with Create, Read, Update, and Delete operations.

## Features

- Patient registration with unique ID, full name, gender, and date of birth
- Medical history tracking using OOP model objects
- Search patient records by ID or name
- Add, update, and remove medical history entries
- File-based persistence so data is saved between sessions

## Requirements

- Java 17+
- Maven

## Run

1. Build the project:
   ```bash
   mvn package
   ```
2. Run the application:
   ```bash
   mvn exec:java
   ```

## Persistent Storage

Patient records are saved to `patients.dat` in the project root using file I/O serialization.
