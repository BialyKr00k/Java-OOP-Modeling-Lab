import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pja.edu.*;

import java.time.LocalDate;

public class ContractTest {
    Doctor d1;
    Clinic c1;
    Contract contract;

    @BeforeEach
    void setup() {
        d1 = new Doctor("Jan", "Kowalski", "90010112345", "1234567");
        c1 = new Clinic("Medicover", "Krzywa", "16", "34-470", "Czarny Dunajec");
        contract = new Contract(d1, c1, LocalDate.of(2024, 1, 1), 5000.0);
    }

    @Test
    void testConstructorActive() {
        assertEquals(d1, contract.getDoctor());
        assertEquals(c1, contract.getClinic());
        assertEquals(LocalDate.of(2024, 1, 1), contract.getDateFrom());
        assertNull(contract.getDateTo());
        assertEquals(5000.0, contract.getAmountOfMoney());
        assertTrue(contract.isActive());
    }

    @Test
    void testConstructorWithDateTo() {
        Doctor d2 = new Doctor("Anna", "Nowak", "85050567890", "7654321");
        Clinic c2 = new Clinic("LuxMed", "Długa", "12B", "00-001", "Warszawa");

        Contract c = new Contract(d2, c2, LocalDate.of(2020, 1, 1),
                LocalDate.of(2022, 12, 31), 6000.0);

        assertEquals(LocalDate.of(2022, 12, 31), c.getDateTo());
        assertFalse(c.isActive()); // data końcowa minęła
    }

    @Test
    void testConstructorAutomaticallyConnectsBothSides() {
        // połączenia zwrotne utworzone przez konstruktor
        assertTrue(d1.getContracts().contains(contract));
        assertTrue(c1.getContracts().contains(contract));
    }

    @Test
    void testConstructorNullDoctor() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contract(null, c1, LocalDate.of(2024, 1, 1), 5000.0));
    }

    @Test
    void testConstructorNullClinic() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contract(d1, null, LocalDate.of(2024, 1, 1), 5000.0));
    }

    @Test
    void testConstructorInvalidDateFrom() {
        Doctor d2 = new Doctor("Anna", "Nowak", "85050567890", "7654321");
        Clinic c2 = new Clinic("LuxMed", "Długa", "12B", "00-001", "Warszawa");

        assertThrows(IllegalArgumentException.class,
                () -> new Contract(d2, c2, LocalDate.now().plusYears(1), 5000.0));

        Doctor d3 = new Doctor("Piotr", "Wiśniewski", "75030587654", "9999999");
        assertThrows(IllegalArgumentException.class,
                () -> new Contract(d3, c2, null, 5000.0));
    }

    @Test
    void testConstructorInvalidDateTo() {
        Doctor d2 = new Doctor("Anna", "Nowak", "85050567890", "7654321");

        assertThrows(IllegalArgumentException.class,
                () -> new Contract(d2, c1, LocalDate.of(2024, 6, 1),
                        LocalDate.of(2024, 1, 1), 5000.0));
    }

    @Test
    void testConstructorInvalidAmount() {
        Doctor d2 = new Doctor("Anna", "Nowak", "85050567890", "7654321");

        assertThrows(IllegalArgumentException.class,
                () -> new Contract(d2, c1, LocalDate.of(2024, 1, 1), 0));
        assertThrows(IllegalArgumentException.class,
                () -> new Contract(d2, c1, LocalDate.of(2024, 1, 1), -100.0));
    }

    @Test
    void testUniquenessOfActivePair() {
        // nie można zawrzeć drugiego AKTYWNEGO kontraktu dla tej samej pary
        assertThrows(IllegalStateException.class,
                () -> new Contract(d1, c1, LocalDate.of(2024, 6, 1), 6000.0));
    }

    @Test
    void testUniquenessAfterTerminate() {
        // po terminacji starego, nowy kontrakt dla tej samej pary jest OK
        contract.terminate();
        Contract newContract = new Contract(d1, c1, LocalDate.of(2024, 6, 1), 6000.0);

        assertNotNull(newContract);
        assertTrue(d1.getContracts().contains(newContract));
        assertTrue(c1.getContracts().contains(newContract));
    }

    @Test
    void testUniquenessAfterDateToInPast() {
        // kontrakt zakończony (dateTo w przeszłości) nie blokuje nowego
        Doctor d2 = new Doctor("Anna", "Nowak", "85050567890", "7654321");
        new Contract(d2, c1, LocalDate.of(2020, 1, 1), LocalDate.of(2022, 12, 31), 5000.0);

        // nowy AKTYWNY kontrakt dla tej samej pary - OK bo poprzedni nieaktywny
        Contract newContract = new Contract(d2, c1, LocalDate.of(2024, 1, 1), 6000.0);
        assertTrue(newContract.isActive());
    }

    @Test
    void testSetDateFrom() {
        contract.setDateFrom(LocalDate.of(2023, 6, 1));
        assertEquals(LocalDate.of(2023, 6, 1), contract.getDateFrom());

        assertThrows(IllegalArgumentException.class, () -> contract.setDateFrom(null));
        assertThrows(IllegalArgumentException.class,
                () -> contract.setDateFrom(LocalDate.now().plusYears(1)));
    }

    @Test
    void testSetDateTo() {
        contract.setDateTo(LocalDate.of(2025, 12, 31));
        assertEquals(LocalDate.of(2025, 12, 31), contract.getDateTo());

        assertThrows(IllegalArgumentException.class,
                () -> contract.setDateTo(LocalDate.of(2023, 1, 1)));

        contract.setDateTo(null);
        assertNull(contract.getDateTo());
    }

    @Test
    void testSetAmountOfMoney() {
        contract.setAmountOfMoney(10000.0);
        assertEquals(10000.0, contract.getAmountOfMoney());

        assertThrows(IllegalArgumentException.class, () -> contract.setAmountOfMoney(0));
        assertThrows(IllegalArgumentException.class, () -> contract.setAmountOfMoney(-100));
    }

    @Test
    void testIsActive() {
        assertTrue(contract.isActive());

        contract.setDateTo(LocalDate.now().plusYears(1));
        assertTrue(contract.isActive());

        contract.setDateTo(LocalDate.now().minusDays(1));
        assertFalse(contract.isActive());
    }

    @Test
    void testSetDoctor() {
        Doctor newDoc = new Doctor("Anna", "Nowak", "85050567890", "7654321");

        contract.setDoctor(newDoc);

        assertEquals(newDoc, contract.getDoctor());
        // stara referencja zwrotna usunięta
        assertFalse(d1.getContracts().contains(contract));
        // nowa referencja zwrotna dopięta
        assertTrue(newDoc.getContracts().contains(contract));
        // klinika nie zmieniona
        assertTrue(c1.getContracts().contains(contract));
    }

    @Test
    void testSetDoctorSameDoctor() {
        // ustawienie tego samego lekarza - nic się nie dzieje
        contract.setDoctor(d1);
        assertEquals(d1, contract.getDoctor());
        assertEquals(1, d1.getContracts().size());
    }

    @Test
    void testSetDoctorNull() {
        assertThrows(IllegalArgumentException.class, () -> contract.setDoctor(null));
    }

    @Test
    void testSetDoctorViolatesUniqueness() {
        Doctor d2 = new Doctor("Anna", "Nowak", "85050567890", "7654321");
        // d2 ma już aktywny kontrakt z c1
        new Contract(d2, c1, LocalDate.of(2024, 2, 1), 6000.0);

        // próba zmiany lekarza w 'contract' na d2 - konflikt aktywnej pary (d2,c1)
        assertThrows(IllegalStateException.class, () -> contract.setDoctor(d2));
    }

    @Test
    void testSetClinic() {
        Clinic newClinic = new Clinic("LuxMed", "Długa", "12B", "00-001", "Warszawa");

        contract.setClinic(newClinic);

        assertEquals(newClinic, contract.getClinic());
        // stara klinika już nie ma kontraktu
        assertFalse(c1.getContracts().contains(contract));
        // nowa klinika ma kontrakt
        assertTrue(newClinic.getContracts().contains(contract));
        // lekarz nie zmieniony
        assertTrue(d1.getContracts().contains(contract));
    }

    @Test
    void testSetClinicSameClinic() {
        contract.setClinic(c1);
        assertEquals(c1, contract.getClinic());
        assertEquals(1, c1.getContracts().size());
    }

    @Test
    void testSetClinicNull() {
        assertThrows(IllegalArgumentException.class, () -> contract.setClinic(null));
    }

    @Test
    void testSetClinicViolatesUniqueness() {
        Clinic c2 = new Clinic("LuxMed", "Długa", "12B", "00-001", "Warszawa");
        // d1 ma już aktywny kontrakt z c2
        new Contract(d1, c2, LocalDate.of(2024, 2, 1), 6000.0);

        // próba zmiany kliniki w 'contract' na c2 - konflikt aktywnej pary (d1,c2)
        assertThrows(IllegalStateException.class, () -> contract.setClinic(c2));
    }

    @Test
    void testTerminate() {
        contract.terminate();

        // wszystkie 4 referencje wyczyszczone
        assertNull(contract.getDoctor());
        assertNull(contract.getClinic());
        assertFalse(d1.getContracts().contains(contract));
        assertFalse(c1.getContracts().contains(contract));
    }

    @Test
    void testTerminateIsIdempotent() {
        contract.terminate();
        // drugie wywołanie nic nie robi, nie rzuca
        assertDoesNotThrow(() -> contract.terminate());
    }

    @Test
    void testToString() {
        String result = contract.toString();

        assertTrue(result.contains("Jan"));
        assertTrue(result.contains("Kowalski"));
        assertTrue(result.contains("Medicover"));
        assertTrue(result.contains("2024-01-01"));
        assertTrue(result.contains("5000"));
    }

    @Test
    void testToStringAfterTerminate() {
        contract.terminate();
        assertEquals("Contract terminated!", contract.toString());
    }

    @Test
    void testToStringWithDateTo() {
        contract.setDateTo(LocalDate.of(2025, 12, 31));
        String result = contract.toString();

        assertTrue(result.contains("2025-12-31"));
        assertTrue(result.contains("to"));
    }

    @Test
    void testEqualsAndHashCode() {
        // equals oparty na (doctor, clinic, dateFrom)
        Doctor d2 = new Doctor("Anna", "Nowak", "85050567890", "7654321");
        Clinic c2 = new Clinic("LuxMed", "Długa", "12B", "00-001", "Warszawa");

        Contract c_diffDoctor = new Contract(d2, c1, LocalDate.of(2024, 1, 1), 5000.0);
        Contract c_diffClinic = new Contract(d1, c2, LocalDate.of(2024, 1, 1), 5000.0);

        assertNotEquals(contract, c_diffDoctor);
        assertNotEquals(contract, c_diffClinic);
    }

    @Test
    void testEqualsEdgeCases() {
        assertEquals(contract, contract);
        assertNotEquals(contract, null);
        assertNotEquals(contract, "not a Contract");
    }
}
