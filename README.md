# Health Record Manager

A simple Java console application for managing patient health records with file-based persistence.

## Project Structure

- `Main.java` — user menu and command loop
- `Patient.java` — patient model with fields and CSV serialization
- `RecordManager.java` — in-memory patient list and CRUD operations
- `FileHandler.java` — save/load patient records from `patients.csv`

## Features

- Add new patients
- View all patient records
- Update existing records by ID
- Delete records by ID
- Search records by ID or name
- Persistent storage using a CSV file (`patients.csv`)

## Run

1. Open a terminal in the project root folder (`a:\health java`).
2. Compile the project:

```bash
javac *.java
```

3. Run the application:

```bash
java -cp . Main
```

Records are saved automatically to `patients.csv` in the same folder where you run the application.

## Quick start

If you prefer one command, run `run.bat` from the project root.
