import java.util.Objects;

public class Patient {
    private int id;
    private String name;
    private int age;
    private String bloodGroup;
    private String history;

    public Patient(int id, String name, int age, String bloodGroup, String history) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.history = history;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return "Patient ID: " + id + "\n"
                + "Name: " + name + "\n"
                + "Age: " + age + "\n"
                + "Blood Group: " + bloodGroup + "\n"
                + "Medical History: " + history + "\n";
    }

    public String toCsvLine() {
        return id + "," + escapeField(name) + "," + age + "," + escapeField(bloodGroup) + "," + escapeField(history);
    }

    public static Patient fromCsvLine(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length < 5) {
            return null;
        }
        int id;
        try {
            id = Integer.parseInt(parts[0]);
        } catch (NumberFormatException e) {
            return null;
        }
        String name = unescapeField(parts[1]);
        int age;
        try {
            age = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            age = 0;
        }
        String bloodGroup = unescapeField(parts[3]);
        String history = unescapeField(parts[4]);
        return new Patient(id, name, age, bloodGroup, history);
    }

    private static String escapeField(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\").replace(",", "\\,").replace("\n", "\\n");
    }

    private static String unescapeField(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\n", "\n").replace("\\,", ",").replace("\\\\", "\\");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        Patient patient = (Patient) o;
        return id == patient.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
