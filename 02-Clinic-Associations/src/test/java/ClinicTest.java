import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pja.edu.*;

import java.time.LocalDate;

public class ClinicTest {
    Clinic c1;

    @BeforeEach
    void setup() {
        c1 = new Clinic(
                "Tatra Medica",
                "Kantora",
                "6",
                "34-470",
                "Czarny Dunajec"
        );
    }

    @Test
    void testName(){
        assertEquals("Tatra Medica", c1.getName());
        c1.setName("Medicover");
        assertEquals("Medicover", c1.getName());

        assertThrows(IllegalArgumentException.class, () -> c1.setName(null));
        assertThrows(IllegalArgumentException.class, () -> c1.setName(""));
        assertThrows(IllegalArgumentException.class, () -> c1.setName("      "));
    }

    @Test
    void testConstructorInvalidName(){
        assertThrows(IllegalArgumentException.class, () -> new Clinic(null,"Kantora", "6", "34-470", "Czarny Dunajec"));
        assertThrows(IllegalArgumentException.class, () -> new Clinic("","Kantora", "6", "34-470", "Czarny Dunajec"));
        assertThrows(IllegalArgumentException.class, () -> new Clinic("     ","Kantora", "6", "34-470", "Czarny Dunajec"));
    }

    @Test
    void testConstructorInvalidAdress(){
        //streetName
        assertThrows(IllegalArgumentException.class, () -> new Clinic("Tatra Medica",null, "6", "34-470", "Czarny Dunajec"));
        assertThrows(IllegalArgumentException.class, () -> new Clinic("Tatra Medica", "", "6", "34-470", "Czarny Dunajec"));
        assertThrows(IllegalArgumentException.class, () -> new Clinic("Tatra Medica","   ", "6", "34-470", "Czarny Dunajec"));
        //homeNumber
        assertThrows(IllegalArgumentException.class, () -> new Clinic("Tatra Medica","Kantora", null, "34-470", "Czarny Dunajec"));
        assertThrows(IllegalArgumentException.class, () -> new Clinic("Tatra Medica","Kantora", "", "34-470", "Czarny Dunajec"));
        //postalCode
        assertThrows(IllegalArgumentException.class, () -> new Clinic("Tatra Medica","Kantora", "6", "34470", "Czarny Dunajec"));
        assertThrows(IllegalArgumentException.class, () -> new Clinic("Tatra Medica","Kantora", "6", "aa-abc", "Czarny Dunajec"));
        assertThrows(IllegalArgumentException.class, () -> new Clinic("Tatra Medica","Kantora", "6", null, "Czarny Dunajec"));
        //cityName
        assertThrows(IllegalArgumentException.class, () -> new Clinic("Tatra Medica","Kantora", "6", "34-470", null));
        assertThrows(IllegalArgumentException.class, () -> new Clinic("Tatra Medica","Kantora", "6", "34-470", ""));
        assertThrows(IllegalArgumentException.class, () -> new Clinic("Tatra Medica","Kantora", "6", "34-470", "czarny dunajec"));
    }

    @Test
    void testAddAdress(){
        assertEquals(1, c1.getAdresses().size());

        c1.addAdress("Marszalkowska", "10", "00-001", "Warszawa");
        assertEquals(2, c1.getAdresses().size());

        assertEquals("Marszalkowska", c1.getAdresses().get(1).getStreetName());
        assertEquals("10", c1.getAdresses().get(1).getHomeNumber());
        assertEquals("00-001", c1.getAdresses().get(1).getPostalCode());
        assertEquals("Warszawa", c1.getAdresses().get(1).getCityName());
    }

    @Test
    void testRemoveAdress() {
        c1.addAdress("Marszalkowska", "10", "00-001", "Warszawa");
        Adress toRemove = c1.getAdresses().get(1);

        assertTrue(c1.removeAdress(toRemove));
        assertEquals(1, c1.getAdresses().size());

        // nie można usunąć ostatniego adresu
        Adress last = c1.getAdresses().get(0);
        assertThrows(IllegalStateException.class, () -> c1.removeAdress(last));

        // null
        assertThrows(IllegalArgumentException.class, () -> c1.removeAdress(null));
    }

    @Test
    void testGetAdressesAsCopy() {
        Adress first = c1.getAdresses().get(0);
        first.setCityName("Krakow");

        // oryginał w klinice się nie zmienił (kopia)
        assertNotEquals("Krakow", c1.getAdresses().get(0).getCityName());
        assertEquals("Czarny Dunajec", c1.getAdresses().get(0).getCityName());
    }

    @Test
    void testAdressesUnmodifiable() {
        assertThrows(UnsupportedOperationException.class,
                () -> c1.getAdresses().add(new Adress("Testowa", "1", "00-000", "Warszawa")));
    }

    @Test
    void testAddPatientQualif() {
        Patient p1 = new Patient("Jan", "Kowalski", LocalDate.of(1990, 1, 1),
                "90010112345", "Testowa", "1", "00-000", "Warszawa");

        c1.addPatientQualif(p1);

        assertEquals(1, c1.getNumberOfPatients());
        assertTrue(c1.getPatients().contains(p1));
        // połączenie zwrotne
        assertTrue(p1.getClinics().contains(c1));
    }

    @Test
    void testAddPatientQualifDuplicate() {
        Patient p1 = new Patient("Jan", "Kowalski", LocalDate.of(1990, 1, 1),
                "90010112345", "Testowa", "1", "00-000", "Warszawa");

        c1.addPatientQualif(p1);
        c1.addPatientQualif(p1); // nie dodaje drugi raz

        assertEquals(1, c1.getNumberOfPatients());
    }

    @Test
    void testAddPatientQualifNull() {
        assertThrows(IllegalArgumentException.class, () -> c1.addPatientQualif(null));
    }

    @Test
    void testRemovePatientQualif() {
        Patient p1 = new Patient("Jan", "Kowalski", LocalDate.of(1990, 1, 1),
                "90010112345", "Testowa", "1", "00-000", "Warszawa");

        c1.addPatientQualif(p1);
        c1.removePatientQualif(p1);

        assertEquals(0, c1.getNumberOfPatients());
        assertFalse(c1.getPatients().contains(p1));
        // połączenie zwrotne też zniknęło
        assertFalse(p1.getClinics().contains(c1));
    }

    @Test
    void testRemovePatientQualifNotExisting() {
        Patient p1 = new Patient("Jan", "Kowalski", LocalDate.of(1990, 1, 1),
                "90010112345", "Testowa", "1", "00-000", "Warszawa");

        // usuwanie pacjenta którego nie ma - nic się nie dzieje
        c1.removePatientQualif(p1);
        assertEquals(0, c1.getNumberOfPatients());

        assertThrows(IllegalArgumentException.class, () -> c1.removePatientQualif(null));
    }

    @Test
    void testFindPatientQualif() throws Exception {
        Patient p1 = new Patient("Jan", "Kowalski", LocalDate.of(1990, 1, 1),
                "90010112345", "Testowa", "1", "00-000", "Warszawa");
        c1.addPatientQualif(p1);

        Patient found = c1.findPatientQualif("90010112345");
        assertEquals(p1, found);
    }

    @Test
    void testFindPatientQualifNotExisting() {
        assertThrows(Exception.class, () -> c1.findPatientQualif("99999999999"));
    }

    @Test
    void testUpdatePatientPeselViaSetPesel() throws Exception {
        Patient p1 = new Patient("Jan", "Kowalski", LocalDate.of(1990, 1, 1),
                "90010112345", "Testowa", "1", "00-000", "Warszawa");

        c1.addPatientQualif(p1);

        // zmiana PESEL-u w pacjencie aktualizuje klucz w mapie kliniki
        p1.setPesel("85050567890");

        assertEquals(p1, c1.findPatientQualif("85050567890"));
        assertThrows(Exception.class, () -> c1.findPatientQualif("90010112345"));
    }

    @Test
    void testPatientsUnmodifiable() {
        Patient p1 = new Patient("Jan", "Kowalski", LocalDate.of(1990, 1, 1),
                "90010112345", "Testowa", "1", "00-000", "Warszawa");
        c1.addPatientQualif(p1);

        assertThrows(UnsupportedOperationException.class, () -> c1.getPatients().clear());
    }

    @Test
    void testAddContract() {
        Doctor d1 = new Doctor("Jan", "Kowalski", "90010112345", "1234567");

        Contract contract = new Contract(d1, c1, LocalDate.of(2024, 1, 1), 5000.0);

        assertEquals(1, c1.getContracts().size());
        assertTrue(c1.getContracts().contains(contract));
        // połączenie zwrotne
        assertTrue(d1.getContracts().contains(contract));
    }

    @Test
    void testGetDoctorsViaContracts() {
        Doctor d1 = new Doctor("Jan", "Kowalski", "90010112345", "1234567");
        Doctor d2 = new Doctor("Anna", "Nowak", "85050567890", "7654321");

        new Contract(d1, c1, LocalDate.of(2024, 1, 1), 5000.0);
        new Contract(d2, c1, LocalDate.of(2024, 2, 1), 6000.0);

        assertEquals(2, c1.getDoctors().size());
        assertTrue(c1.getDoctors().contains(d1));
        assertTrue(c1.getDoctors().contains(d2));
    }

    @Test
    void testContractsUnmodifiable() {
        assertThrows(UnsupportedOperationException.class, () -> c1.getContracts().clear());
    }

    @Test
    void testDoctorsUnmodifiable() {
        assertThrows(UnsupportedOperationException.class, () -> c1.getDoctors().clear());
    }

    @Test
    void testTerminateContractRemovesFromClinic() {
        Doctor d1 = new Doctor("Jan", "Kowalski", "90010112345", "1234567");
        Contract contract = new Contract(d1, c1, LocalDate.of(2024, 1, 1), 5000.0);

        contract.terminate();

        assertEquals(0, c1.getContracts().size());
        assertEquals(0, d1.getContracts().size());
    }

    @Test
    void testExtensionAndCount() {
        int countBefore = Clinic.getNumberOfClinics();

        Clinic c2 = new Clinic("LuxMed", "Długa", "12B", "00-001", "Warszawa");

        assertEquals(countBefore + 1, Clinic.getNumberOfClinics());
        assertTrue(Clinic.getExtend().contains(c2));
    }

    @Test
    void testExtensionUnmodifiable() {
        assertThrows(UnsupportedOperationException.class, () -> Clinic.getExtend().add(c1));
    }

    @Test
    void testEqualsAndHashCode() {
        Clinic same = new Clinic("Tatra Medica", "Kantora", "6", "34-470", "Czarny Dunajec");
        Clinic different = new Clinic("LuxMed", "Krzywa", "16", "34-470", "Czarny Dunajec");

        // equals oparty na nazwie
        assertEquals(c1, same);
        assertNotEquals(c1, different);
        assertEquals(c1.hashCode(), same.hashCode());
    }

    @Test
    void testToString() {
        String result = c1.toString();

        assertTrue(result.contains("Tatra Medica"));
        assertTrue(result.contains("Kantora"));
        assertTrue(result.contains("Czarny Dunajec"));
        assertTrue(result.contains("34-470"));
    }
}
