package pja.edu.Dziedziczenie_dynamiczne;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Doctor extends Person {
    private String medicalLicenseNumber;

    private static final Set<Doctor> extent = new HashSet<>();

    public Doctor(String pesel, String name, LocalDate birthDate, String medicalLicenseNumber) {
        super(pesel, name, birthDate);
        setMedicalLicenseNumber(medicalLicenseNumber);
        extent.add(this);
    }

    public Doctor(Person prevPerson, String medicalLicenseNumber) {
        super(prevPerson);
        setMedicalLicenseNumber(medicalLicenseNumber);
        extent.add(this);

        Person.removeFromExtent(prevPerson);
    }

    @Override
    public int calculateAge() {
        return ageFromBirthDate();
    }

    public void issuePrescription() {
        System.out.println("Doctor " + getName() + " issued a prescription.");
    }

    public String getMedicalLicenseNumber() {
        return medicalLicenseNumber;
    }

    public void setMedicalLicenseNumber(String medicalLicenseNumber) {
        if (medicalLicenseNumber == null || medicalLicenseNumber.isBlank()) {
            throw new IllegalArgumentException("Numer PWZ nie może być pusty");
        }
        this.medicalLicenseNumber = medicalLicenseNumber;
    }

    public static Set<Doctor> getDoctorExtent() {
        return Collections.unmodifiableSet(extent);
    }

    public static void showExtent() {
        System.out.println("=== Doctor extent ===");
        extent.forEach(System.out::println);
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "pesel='" + getPesel() + '\'' +
                ", name='" + getName() + '\'' +
                ", birthDate=" + getBirthDate() +
                ", age=" + calculateAge() +
                ", medicalLicenseNumber='" + medicalLicenseNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor doctor)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(medicalLicenseNumber, doctor.medicalLicenseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), medicalLicenseNumber);
    }
}
