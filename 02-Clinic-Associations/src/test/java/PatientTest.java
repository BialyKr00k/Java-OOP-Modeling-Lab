import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pja.edu.*;

import java.time.LocalDate;
import java.time.Period;

public class PatientTest {
    Patient p1;

    @BeforeEach
    void setup() {
        p1 = new Patient("Michał",
                "Piotr",
                "Sebastian",
                LocalDate.of(2003, 2, 18),
                "10101010101",
                "Krzywa",
                "16",
                "34-470",
                "Czarny Dunajec"
        );
    }

    @Test
    void testConstructorWithSecondName() {
        assertEquals("Michał", p1.getFirstName());
        assertEquals("Piotr", p1.getSecondName());
        assertEquals("Sebastian", p1.getSurname());
        assertEquals(LocalDate.of(2003, 2, 18), p1.getDateOfBirth());
        assertEquals("10101010101", p1.getPesel());
        assertEquals(1, p1.getAdresses().size());
    }

    @Test
    void testConstructorWithoutSecondName() {
        Patient p2 = new Patient("Jan", "Kowalski", LocalDate.of(1990, 1, 1),
                "90010112345", "Testowa", "1", "00-000", "Warszawa");

        assertEquals("Jan", p2.getFirstName());
        assertNull(p2.getSecondName());
        assertEquals("Kowalski", p2.getSurname());
    }

    @Test
    void testFirstName() {
        assertEquals("Michał", p1.getFirstName());
        p1.setFirstName("Jakub");
        assertEquals("Jakub", p1.getFirstName());

        assertThrows(IllegalArgumentException.class, () -> p1.setFirstName(null));
        assertThrows(IllegalArgumentException.class, () -> p1.setFirstName(""));
        assertThrows(IllegalArgumentException.class, () -> p1.setFirstName("   "));
        assertThrows(IllegalArgumentException.class, () -> p1.setFirstName("michał"));
        assertThrows(IllegalArgumentException.class, () -> p1.setFirstName("MICHAŁ"));
        assertThrows(IllegalArgumentException.class, () -> p1.setFirstName("Michał123"));
        assertThrows(IllegalArgumentException.class, () -> p1.setFirstName("MiChAł"));
    }

    @Test
    void testSurname() {
        assertEquals("Sebastian", p1.getSurname());
        p1.setSurname("Adamski");
        assertEquals("Adamski", p1.getSurname());

        assertThrows(IllegalArgumentException.class, () -> p1.setSurname(null));
        assertThrows(IllegalArgumentException.class, () -> p1.setSurname(""));
        assertThrows(IllegalArgumentException.class, () -> p1.setSurname("   "));
        assertThrows(IllegalArgumentException.class, () -> p1.setSurname("sebastian"));
        assertThrows(IllegalArgumentException.class, () -> p1.setSurname("SEBASTIAN"));
        assertThrows(IllegalArgumentException.class, () -> p1.setSurname("Sebastian123"));
    }

    @Test
    void testSecondName() {
        assertEquals("Piotr", p1.getSecondName());
        p1.setSecondName("Paweł");
        assertEquals("Paweł", p1.getSecondName());

        assertThrows(IllegalArgumentException.class, () -> p1.setSecondName(null));
        assertThrows(IllegalArgumentException.class, () -> p1.setSecondName(""));
        assertThrows(IllegalArgumentException.class, () -> p1.setSecondName("paweł"));
        assertThrows(IllegalArgumentException.class, () -> p1.setSecondName("PAWEŁ"));
        assertThrows(IllegalArgumentException.class, () -> p1.setSecondName("Paweł43"));
    }

    @Test
    void testDateOfBirth() {
        assertEquals(LocalDate.of(2003, 2, 18), p1.getDateOfBirth());
        p1.setDateOfBirth(LocalDate.of(2000, 8, 5));
        assertEquals(LocalDate.of(2000, 8, 5), p1.getDateOfBirth());

        assertThrows(IllegalArgumentException.class, () -> p1.setDateOfBirth(null));
        assertThrows(IllegalArgumentException.class,
                () -> p1.setDateOfBirth(LocalDate.now().plusYears(1)));
        assertThrows(IllegalArgumentException.class,
                () -> p1.setDateOfBirth(LocalDate.of(1800, 1, 1)));
    }

    @Test
    void testPesel() {
        assertEquals("10101010101", p1.getPesel());
        p1.setPesel("85050567890");
        assertEquals("85050567890", p1.getPesel());

        assertThrows(IllegalArgumentException.class, () -> p1.setPesel(null));
        assertThrows(IllegalArgumentException.class, () -> p1.setPesel(""));
        assertThrows(IllegalArgumentException.class, () -> p1.setPesel("   "));
        assertThrows(IllegalArgumentException.class, () -> p1.setPesel("1234567890"));
        assertThrows(IllegalArgumentException.class, () -> p1.setPesel("123456789012"));
        assertThrows(IllegalArgumentException.class, () -> p1.setPesel("abcdefghijk"));
        assertThrows(IllegalArgumentException.class, () -> p1.setPesel("1234567890a"));
    }

    @Test
    void testSetPeselUpdatesQualifierInClinics() throws Exception {
        Clinic c1 = new Clinic("Medicover", "Krzywa", "16", "34-470", "Czarny Dunajec");
        Clinic c2 = new Clinic("LuxMed", "Długa", "12B", "00-001", "Warszawa");

        c1.addPatientQualif(p1);
        c2.addPatientQualif(p1);

        // zmiana PESEL pacjenta - mapy w obu klinikach powinny się zaktualizować
        p1.setPesel("85050567890");

        assertEquals(p1, c1.findPatientQualif("85050567890"));
        assertEquals(p1, c2.findPatientQualif("85050567890"));

        // stary PESEL nie działa
        assertThrows(Exception.class, () -> c1.findPatientQualif("10101010101"));
        assertThrows(Exception.class, () -> c2.findPatientQualif("10101010101"));
    }

    @Test
    void testGetAge() {
        int expectedAge = Period.between(LocalDate.of(2003, 2, 18), LocalDate.now()).getYears();
        assertEquals(expectedAge, p1.getAge());
    }

    @Test
    void testAddAdress() {
        assertEquals(1, p1.getAdresses().size());

        p1.addAdress("Marszalkowska", "10", "00-001", "Warszawa");
        assertEquals(2, p1.getAdresses().size());
    }

    @Test
    void testRemoveAdress() {
        p1.addAdress("Marszalkowska", "10", "00-001", "Warszawa");
        Adress toRemove = p1.getAdresses().get(1);

        assertTrue(p1.removeAdress(toRemove));
        assertEquals(1, p1.getAdresses().size());

        // nie można usunąć ostatniego adresu
        Adress last = p1.getAdresses().get(0);
        assertThrows(IllegalStateException.class, () -> p1.removeAdress(last));

        assertThrows(IllegalArgumentException.class, () -> p1.removeAdress(null));
    }

    @Test
    void testGetAdressesAsCopy() {
        Adress first = p1.getAdresses().get(0);
        first.setCityName("Krakow");

        // kopia - oryginał się nie zmienił
        assertNotEquals("Krakow", p1.getAdresses().get(0).getCityName());
        assertEquals("Czarny Dunajec", p1.getAdresses().get(0).getCityName());
    }

    @Test
    void testAdressesUnmodifiable() {
        assertThrows(UnsupportedOperationException.class,
                () -> p1.getAdresses().add(new Adress("Testowa", "1", "00-000", "Warszawa")));
    }

    @Test
    void testAddClinicQualif() {
        Clinic c1 = new Clinic("Medicover", "Krzywa", "16", "34-470", "Czarny Dunajec");

        p1.addClinicQualif(c1);

        assertEquals(1, p1.getClinics().size());
        assertTrue(p1.getClinics().contains(c1));
        // połączenie zwrotne
        assertTrue(c1.getPatients().contains(p1));
    }

    @Test
    void testAddClinicQualifDuplicate() {
        Clinic c1 = new Clinic("Medicover", "Krzywa", "16", "34-470", "Czarny Dunajec");

        p1.addClinicQualif(c1);
        p1.addClinicQualif(c1);

        assertEquals(1, p1.getClinics().size());
    }

    @Test
    void testAddClinicQualifNull() {
        assertThrows(IllegalArgumentException.class, () -> p1.addClinicQualif(null));
    }

    @Test
    void testRemoveClinicQualif() {
        Clinic c1 = new Clinic("Medicover", "Krzywa", "16", "34-470", "Czarny Dunajec");
        p1.addClinicQualif(c1);

        p1.removeClinicQualif(c1);

        assertEquals(0, p1.getClinics().size());
        assertFalse(c1.getPatients().contains(p1));
    }

    @Test
    void testRemoveClinicQualifNotExisting() {
        Clinic c1 = new Clinic("Medicover", "Krzywa", "16", "34-470", "Czarny Dunajec");

        // usuwanie kliniki której nie ma - nic się nie dzieje
        p1.removeClinicQualif(c1);
        assertEquals(0, p1.getClinics().size());

        assertThrows(IllegalArgumentException.class, () -> p1.removeClinicQualif(null));
    }

    @Test
    void testClinicsUnmodifiable() {
        assertThrows(UnsupportedOperationException.class, () -> p1.getClinics().clear());
    }

    @Test
    void testExtensionAndCount() {
        int countBefore = Patient.getNumberOfPatients();

        Patient p2 = new Patient("Jan", "Kowalski", LocalDate.of(1990, 1, 1),
                "90010112345", "Testowa", "1", "00-000", "Warszawa");

        assertEquals(countBefore + 1, Patient.getNumberOfPatients());
        assertTrue(Patient.getExtend().contains(p2));
    }

    @Test
    void testExtensionUnmodifiable() {
        assertThrows(UnsupportedOperationException.class, () -> Patient.getExtend().add(p1));
    }

    @Test
    void testEqualsAndHashCode() {
        // equals oparty na PESEL
        Patient same = new Patient("Inne", "Imie", LocalDate.of(1990, 1, 1),
                "10101010101", "Testowa", "1", "00-000", "Warszawa");
        Patient different = new Patient("Michał", "Sebastian", LocalDate.of(2003, 2, 18),
                "99999999999", "Krzywa", "16", "34-470", "Czarny Dunajec");

        assertEquals(p1, same);
        assertEquals(p1.hashCode(), same.hashCode());
        assertNotEquals(p1, different);
    }

    @Test
    void testEqualsEdgeCases() {
        assertEquals(p1, p1);
        assertNotEquals(p1, null);
        assertNotEquals(p1, "not a Patient");
    }

    @Test
    void testToString() {
        String result = p1.toString();

        assertTrue(result.contains("Michał"));
        assertTrue(result.contains("Piotr"));
        assertTrue(result.contains("Sebastian"));
        assertTrue(result.contains("10101010101"));
        assertTrue(result.contains("2003-02-18"));
    }

    // MedicalHistory
    @Test
    void testCreateMedicalHistory() {
        Patient.MedicalHistory mh = p1.createMedicalHistory("Grypa", "Objawy: kaszel, gorączka");

        assertNotNull(mh);
        assertEquals("Grypa", mh.getTitle());
        assertEquals("Objawy: kaszel, gorączka", mh.getDescription());
        assertEquals(LocalDate.now(), mh.getCreateAt());
        // referencja do całości
        assertEquals(p1, mh.getPatient());

        assertEquals(1, p1.getMedicalHistories().size());
        assertTrue(p1.getMedicalHistories().contains(mh));
    }

    @Test
    void testCreateMultipleMedicalHistories() {
        p1.createMedicalHistory("Grypa", "Objawy sezonowe");
        p1.createMedicalHistory("Złamanie", "Ręka lewa");
        p1.createMedicalHistory("Przeziębienie", "Standardowe");

        assertEquals(3, p1.getMedicalHistories().size());
    }

    @Test
    void testMedicalHistoryInvalidTitle() {
        assertThrows(IllegalArgumentException.class,
                () -> p1.createMedicalHistory(null, "opis"));
        assertThrows(IllegalArgumentException.class,
                () -> p1.createMedicalHistory("", "opis"));
        assertThrows(IllegalArgumentException.class,
                () -> p1.createMedicalHistory("   ", "opis"));
    }

    @Test
    void testMedicalHistoryInvalidDescription() {
        assertThrows(IllegalArgumentException.class,
                () -> p1.createMedicalHistory("Tytul", null));
        assertThrows(IllegalArgumentException.class,
                () -> p1.createMedicalHistory("Tytul", ""));
        assertThrows(IllegalArgumentException.class,
                () -> p1.createMedicalHistory("Tytul", "   "));
    }

    @Test
    void testMedicalHistorySetTitle() {
        Patient.MedicalHistory mh = p1.createMedicalHistory("Grypa", "Opis");

        mh.setTitle("Ostra grypa");
        assertEquals("Ostra grypa", mh.getTitle());

        assertThrows(IllegalArgumentException.class, () -> mh.setTitle(null));
        assertThrows(IllegalArgumentException.class, () -> mh.setTitle(""));
        assertThrows(IllegalArgumentException.class, () -> mh.setTitle("   "));
    }

    @Test
    void testMedicalHistorySetDescription() {
        Patient.MedicalHistory mh = p1.createMedicalHistory("Grypa", "Opis");

        mh.setDescription("Nowy opis");
        assertEquals("Nowy opis", mh.getDescription());

        assertThrows(IllegalArgumentException.class, () -> mh.setDescription(null));
        assertThrows(IllegalArgumentException.class, () -> mh.setDescription(""));
        assertThrows(IllegalArgumentException.class, () -> mh.setDescription("   "));
    }

    @Test
    void testRemoveMedicalHistory() {
        Patient.MedicalHistory mh = p1.createMedicalHistory("Grypa", "Opis");

        assertTrue(p1.removeMedicalHistory(mh));
        assertEquals(0, p1.getMedicalHistories().size());
        assertFalse(p1.getMedicalHistories().contains(mh));
    }

    @Test
    void testRemoveMedicalHistoryNotExisting() {
        Patient p2 = new Patient("Jan", "Kowalski", LocalDate.of(1990, 1, 1),
                "90010112345", "Testowa", "1", "00-000", "Warszawa");
        Patient.MedicalHistory mhOfOther = p2.createMedicalHistory("Obca", "Opis");

        // historia innego pacjenta - remove zwraca false
        assertFalse(p1.removeMedicalHistory(mhOfOther));
    }

    @Test
    void testMedicalHistoriesUnmodifiable() {
        assertThrows(UnsupportedOperationException.class,
                () -> p1.getMedicalHistories().clear());
    }

    @Test
    void testMedicalHistoryOwnerImmutable() {
        Patient.MedicalHistory mh = p1.createMedicalHistory("Grypa", "Opis");

        // klasa wewnętrzna - Patient.this jest niezmienialne
        assertEquals(p1, mh.getPatient());

        // nawet po zmianie danych pacjenta, referencja jest ta sama
        p1.setFirstName("Adam");
        assertEquals(p1, mh.getPatient());
        assertEquals("Adam", mh.getPatient().getFirstName());
    }

    @Test
    void testMedicalHistoryCannotBeSharedBetweenPatients() {
        //  niemożliwe - konstruktor MedicalHistory jest prywatny, a Patient.this jest ustawiane raz
        // Nie ma żadnej metody publicznej która zmieni właściciela.
        Patient p2 = new Patient("Jan", "Kowalski", LocalDate.of(1990, 1, 1),
                "90010112345", "Testowa", "1", "00-000", "Warszawa");

        Patient.MedicalHistory mh1 = p1.createMedicalHistory("Grypa", "Opis");
        Patient.MedicalHistory mh2 = p2.createMedicalHistory("Grypa", "Opis");

        assertEquals(p1, mh1.getPatient());
        assertEquals(p2, mh2.getPatient());
        assertNotEquals(mh1.getPatient(), mh2.getPatient());
    }

    @Test
    void testMedicalHistoryEquals() {
        Patient.MedicalHistory mh1 = p1.createMedicalHistory("Grypa", "Opis");
        Patient.MedicalHistory mh2 = p1.createMedicalHistory("Grypa", "Opis");

        // ten sam pacjent, ten sam tytuł, opis, data utworzenia - są równe
        assertEquals(mh1, mh2);
        assertEquals(mh1.hashCode(), mh2.hashCode());

        assertEquals(mh1, mh1);
        assertNotEquals(mh1, null);
        assertNotEquals(mh1, "not a MedicalHistory");
    }

    @Test
    void testMedicalHistoryEqualsDifferentPatients() {
        Patient p2 = new Patient("Jan", "Kowalski", LocalDate.of(1990, 1, 1),
                "90010112345", "Testowa", "1", "00-000", "Warszawa");

        Patient.MedicalHistory mh1 = p1.createMedicalHistory("Grypa", "Opis");
        Patient.MedicalHistory mh2 = p2.createMedicalHistory("Grypa", "Opis");

        // różni pacjenci - mimo takich samych danych, nie są równe
        assertNotEquals(mh1, mh2);
    }

    @Test
    void testMedicalHistoryToString() {
        Patient.MedicalHistory mh = p1.createMedicalHistory("Grypa", "Kaszel i gorączka");
        String result = mh.toString();

        assertTrue(result.contains("Grypa"));
        assertTrue(result.contains("Michał"));
        assertTrue(result.contains("Sebastian"));
        assertTrue(result.contains("Kaszel i gorączka"));
    }
}
