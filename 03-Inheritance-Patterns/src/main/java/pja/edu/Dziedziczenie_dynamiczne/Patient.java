package pja.edu.Dziedziczenie_dynamiczne;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Patient extends Person {
    private String medicalHistory;

    private static final Set<Patient> extent = new HashSet<>();

    public Patient(String pesel, String name, LocalDate birthDate, String medicalHistory) {
        super(pesel, name, birthDate);
        setMedicalHistory(medicalHistory);
        extent.add(this);
    }

    public Patient(Person prevPerson, String medicalHistory) {
        super(prevPerson);
        setMedicalHistory(medicalHistory);
        extent.add(this);

        Person.removeFromExtent(prevPerson);
    }

    @Override
    public int calculateAge() {
        return ageFromBirthDate();
    }

    public void makeAppointment() {
        System.out.println("Patient " + getName() + " made an appointment.");
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        if (medicalHistory == null || medicalHistory.isBlank()) {
            throw new IllegalArgumentException("Historia medyczna nie może być pusta");
        }
        this.medicalHistory = medicalHistory;
    }

    public static Set<Patient> getPatientExtent() {
        return Collections.unmodifiableSet(extent);
    }

    public static void showExtent() {
        System.out.println("=== Patient extent ===");
        extent.forEach(System.out::println);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "pesel='" + getPesel() + '\'' +
                ", name='" + getName() + '\'' +
                ", birthDate=" + getBirthDate() +
                ", age=" + calculateAge() +
                ", medicalHistory='" + medicalHistory + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient patient)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(medicalHistory, patient.medicalHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), medicalHistory);
    }
}
