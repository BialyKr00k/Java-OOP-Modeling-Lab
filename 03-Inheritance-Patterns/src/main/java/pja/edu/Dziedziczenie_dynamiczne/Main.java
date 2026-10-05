package pja.edu.Dziedziczenie_dynamiczne;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Person p = new Patient(
                "01234567890",
                "Jan Kowalski",
                LocalDate.of(2000, 5, 10),
                "Brak chorób przewlekłych"
        );

        System.out.println("Po utworzeniu Patient:");
        Person.showExtent();
        Patient.showExtent();
        Doctor.showExtent();

        p = new Doctor(p, "PWZ-1234567");

        System.out.println("\nPo zmianie roli na Doctor:");
        Person.showExtent();
        Patient.showExtent();
        Doctor.showExtent();

        ((Doctor) p).issuePrescription();
    }
}
