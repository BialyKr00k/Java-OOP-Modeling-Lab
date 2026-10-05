import java.io.*;
import java.time.LocalDate;
import java.util.Set;

public class Main {
    public static void main(String[] args){
        Patient p1 = new Patient(
                "Adam",
                "Andrzej",
                "Adamowski",
                LocalDate.of(2000, 1, 1),
                "00000000000",
                Set.of("123456789", "321456978"),
                "Długa",
                "345A",
                "00-001",
                "Warszawa"
        );

        Patient p2 = new Patient(
                "Zbigniew",
                "Zawadzki",
                LocalDate.of(2000, 2, 2),
                "11111111111",
                Set.of("321654987", "101010101"),
                "Krótka",
                "1",
                "00-001",
                "Warszawa"
        );

        System.out.println("Numery telefonu do pacjenta " + p2.getFirstName() + " " + p2.getSurname() + " to:");
        for(String number : p2.getPhoneNumbers()){
            System.out.println(number);
        }

        System.out.println("================================================");
        System.out.println("Po wywołaniu metody removePhoneNumber():");
        System.out.println("================================================");

        try {
            p2.removePhoneNumber("101010101");
        } catch (IllegalArgumentException iae) {
            System.out.println(iae);
        }
        System.out.println("Numery telefonu do pacjenta " + p2.getFirstName() + " " + p2.getSurname() + " to:");
        for(String number : p2.getPhoneNumbers()){
            System.out.println(number);
        }
        System.out.println("================================================");

        System.out.println("Liczba pacjentów: " + Patient.getNumberOfPatients());

        System.out.println("================================================");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("patients.bin"))) {
            Patient.saveExtension(oos);
            System.out.println("Ekstensja zapisana do pliku!");
        } catch (IOException e) {
            System.out.println("Błąd zapisu: " + e.getMessage());
        }

        System.out.println("================================================");

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("patients.bin"))) {
            Patient.readExtension(ois);
            System.out.println("Ekstensja odczytana z pliku!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Błąd odczytu: " + e.getMessage());
        }

        System.out.println("================================================");

        System.out.println("Pacjenci odczytani z pliku:");
        for (Patient p : Patient.getExtension()) {
            System.out.println(p);
        }
    }
}