package pja.edu;

import java.time.LocalDate;

public class Main {
    public static void main() {
        System.out.println("===================================================================================");
        // Kliniki:
        Clinic medicover = new Clinic("Medicover", "Marszalkowska", "1", "00-001", "Warszawa");
        Clinic luxmed = new Clinic("LuxMed", "Krzywa", "16", "34-470", "Czarny Dunajec");
        System.out.println(medicover);
        System.out.println(luxmed);
        System.out.println("===================================================================================");

        // Lekarze:
        Doctor jan = new Doctor("Jan", "Kowalski", "80010112345", "1234567");
        Doctor anna = new Doctor("Anna", "Nowak", "75050567890", "7654321");
        System.out.println(jan);
        System.out.println(anna);
        System.out.println("===================================================================================");

        // Specjalizacje:
        Specialization kardiologia = new Specialization("Kardiologia");
        Specialization pediatria = new Specialization("Pediatria");
        System.out.println(kardiologia);
        System.out.println(pediatria);
        System.out.println("===================================================================================");

        // Pacjenci:
        Patient michal = new Patient("Michał", "Piotr", "Sebastian",
                LocalDate.of(2003, 2, 18), "03021812345",
                "Kwiatowa", "5", "00-002", "Warszawa");
        Patient adam = new Patient("Adam", "Testowy",
                LocalDate.of(1990, 6, 15), "90061554321",
                "Długa", "12B", "00-003", "Warszawa");
        System.out.println(michal);
        System.out.println(adam);
        System.out.println("===================================================================================");

        jan.addSpecialization(kardiologia);
        jan.addSpecialization(pediatria);
        anna.addSpecialization(pediatria);

        System.out.println("Specjalizacje Jana: " + jan.getSpecializations());
        System.out.println("Lekarze z kardiologii: " + kardiologia.getDoctors());
        System.out.println("Lekarze z pediatrii: " + pediatria.getDoctors());
        System.out.println("===================================================================================");

        Contract c1 = new Contract(jan, medicover, LocalDate.of(2024, 1, 1), 8000.0);
        Contract c2 = new Contract(anna, luxmed, LocalDate.of(2023, 6, 1), 7500.0);
        Contract c3 = new Contract(jan, luxmed, LocalDate.of(2020, 1, 1),
                LocalDate.of(2022, 12, 31), 6000.0); // zakończony

        System.out.println(c1);
        System.out.println(c2);
        System.out.println(c3);
        System.out.println("===================================================================================");

        System.out.println("\nKontrakty Jana: " + jan.getContracts().size());
        System.out.println("Kliniki Jana: " + jan.getClinics());
        System.out.println("Lekarze w Medicover: " + medicover.getDoctors());

        medicover.addPatientQualif(michal);
        medicover.addPatientQualif(adam);
        luxmed.addPatientQualif(michal);

        System.out.println("Pacjenci Medicover: " + medicover.getNumberOfPatients());
        System.out.println("Kliniki Michała: " + michal.getClinics().size());
        System.out.println("===================================================================================");

        try {
            Patient found = medicover.findPatientQualif("03021812345");
            System.out.println("Znaleziono pacjenta po PESEL: " + found.getFirstName() + " " + found.getSurname());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("===================================================================================");

        System.out.println("\nZmiana PESEL-u Michała:");
        michal.setPesel("03021899999");

        try {
            Patient found = medicover.findPatientQualif("03021899999");
            System.out.println("Znaleziono pod nowym PESEL w Medicover: " + found.getFirstName());
            Patient found2 = luxmed.findPatientQualif("03021899999");
            System.out.println("Znaleziono pod nowym PESEL w LuxMed:    " + found2.getFirstName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("===================================================================================");

        Patient.MedicalHistory mh1 = michal.createMedicalHistory(
                "Grypa", "Sezonowe przeziębienie, kaszel, gorączka 38.5°C");
        Patient.MedicalHistory mh2 = michal.createMedicalHistory(
                "Złamanie ręki", "Złamanie kości promieniowej lewej ręki, gips 6 tygodni");

        System.out.println(mh1);
        System.out.println(mh2);
        System.out.println("\nHistorie choroby Michała: " + michal.getMedicalHistories().size());
        System.out.println("Pacjent historii mh1: " + mh1.getPatient().getFirstName());

        System.out.println("Przed zmianą lekarza w kontrakcie c1:");
        System.out.println("  Jan ma kontraktów: " + jan.getContracts().size());
        System.out.println("  Anna ma kontraktów: " + anna.getContracts().size());

        c1.setDoctor(anna);

        System.out.println("===================================================================================");
        System.out.println("\nPo zmianie c1.doctor = Anna:");
        System.out.println("  Jan ma kontraktów: " + jan.getContracts().size());
        System.out.println("  Anna ma kontraktów: " + anna.getContracts().size());
        System.out.println("  " + c1);

        System.out.println("Przed terminacją c2:");
        System.out.println("  LuxMed contracts: " + luxmed.getContracts().size());
        System.out.println("  Anna contracts:   " + anna.getContracts().size());

        c2.terminate();

        System.out.println("===================================================================================");

        System.out.println("\nPo terminacji c2:");
        System.out.println("  " + c2);
        System.out.println("  LuxMed contracts: " + luxmed.getContracts().size());
        System.out.println("  Anna contracts:   " + anna.getContracts().size());

        System.out.println("===================================================================================");

        System.out.println("Liczba klinik:        " + Clinic.getNumberOfClinics());
        System.out.println("Liczba lekarzy:       " + Doctor.getNumberOfDoctors());
        System.out.println("Liczba pacjentów:     " + Patient.getNumberOfPatients());
        System.out.println("Liczba specjalizacji: " + Specialization.getNumberOfSpecializations());
    }
}
