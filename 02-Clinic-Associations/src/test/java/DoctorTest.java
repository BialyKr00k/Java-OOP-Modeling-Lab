import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pja.edu.*;

import java.time.LocalDate;

public class DoctorTest {
    Doctor d1;

    @BeforeEach
    void setup() {
        d1 = new Doctor("Jan", "Kowalski", "90010112345", "1234567");
    }

    @Test
    void testConstructor() {
        assertEquals("Jan", d1.getFirstName());
        assertEquals("Kowalski", d1.getSurname());
        assertEquals("90010112345", d1.getPesel());
        assertEquals("1234567", d1.getPwz());
    }

    @Test
    void testFirstName() {
        assertEquals("Jan", d1.getFirstName());
        d1.setFirstName("Piotr");
        assertEquals("Piotr", d1.getFirstName());

        assertThrows(IllegalArgumentException.class, () -> d1.setFirstName(null));
        assertThrows(IllegalArgumentException.class, () -> d1.setFirstName(""));
        assertThrows(IllegalArgumentException.class, () -> d1.setFirstName("   "));
        assertThrows(IllegalArgumentException.class, () -> d1.setFirstName("jan"));
        assertThrows(IllegalArgumentException.class, () -> d1.setFirstName("JAN"));
        assertThrows(IllegalArgumentException.class, () -> d1.setFirstName("Jan123"));
        assertThrows(IllegalArgumentException.class, () -> d1.setFirstName("JaN"));
    }

    @Test
    void testSurname() {
        assertEquals("Kowalski", d1.getSurname());
        d1.setSurname("Nowak");
        assertEquals("Nowak", d1.getSurname());

        assertThrows(IllegalArgumentException.class, () -> d1.setSurname(null));
        assertThrows(IllegalArgumentException.class, () -> d1.setSurname(""));
        assertThrows(IllegalArgumentException.class, () -> d1.setSurname("   "));
        assertThrows(IllegalArgumentException.class, () -> d1.setSurname("kowalski"));
        assertThrows(IllegalArgumentException.class, () -> d1.setSurname("KOWALSKI"));
        assertThrows(IllegalArgumentException.class, () -> d1.setSurname("Kowalski123"));
    }

    @Test
    void testPesel() {
        assertEquals("90010112345", d1.getPesel());
        d1.setPesel("85050567890");
        assertEquals("85050567890", d1.getPesel());

        assertThrows(IllegalArgumentException.class, () -> d1.setPesel(null));
        assertThrows(IllegalArgumentException.class, () -> d1.setPesel(""));
        assertThrows(IllegalArgumentException.class, () -> d1.setPesel("   "));
        assertThrows(IllegalArgumentException.class, () -> d1.setPesel("1234567890"));
        assertThrows(IllegalArgumentException.class, () -> d1.setPesel("123456789012"));
        assertThrows(IllegalArgumentException.class, () -> d1.setPesel("abcdefghijk"));
        assertThrows(IllegalArgumentException.class, () -> d1.setPesel("1234567890a"));
    }

    @Test
    void testPwz() {
        assertEquals("1234567", d1.getPwz());
        d1.setPwz("7654321");
        assertEquals("7654321", d1.getPwz());

        assertThrows(IllegalArgumentException.class, () -> d1.setPwz(null));
        assertThrows(IllegalArgumentException.class, () -> d1.setPwz(""));
        assertThrows(IllegalArgumentException.class, () -> d1.setPwz("   "));
        assertThrows(IllegalArgumentException.class, () -> d1.setPwz("123456"));
        assertThrows(IllegalArgumentException.class, () -> d1.setPwz("12345678"));
        assertThrows(IllegalArgumentException.class, () -> d1.setPwz("abcdefg"));
        assertThrows(IllegalArgumentException.class, () -> d1.setPwz("123456a"));
    }

    @Test
    void testConstructorInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> new Doctor(null, "Kowalski", "90010112345", "1234567"));
        assertThrows(IllegalArgumentException.class,
                () -> new Doctor("Jan", null, "90010112345", "1234567"));
        assertThrows(IllegalArgumentException.class,
                () -> new Doctor("Jan", "Kowalski", null, "1234567"));
        assertThrows(IllegalArgumentException.class,
                () -> new Doctor("Jan", "Kowalski", "90010112345", null));
        assertThrows(IllegalArgumentException.class,
                () -> new Doctor("jan", "Kowalski", "90010112345", "1234567"));
        assertThrows(IllegalArgumentException.class,
                () -> new Doctor("Jan", "Kowalski", "123", "1234567"));
        assertThrows(IllegalArgumentException.class,
                () -> new Doctor("Jan", "Kowalski", "90010112345", "123"));
    }

    @Test
    void testAddContract() {
        Clinic c1 = new Clinic("Medicover", "Krzywa", "16", "34-470", "Czarny Dunajec");
        Contract contract = new Contract(d1, c1, LocalDate.of(2024, 1, 1), 5000.0);

        assertEquals(1, d1.getContracts().size());
        assertTrue(d1.getContracts().contains(contract));
        // połączenie zwrotne
        assertTrue(c1.getContracts().contains(contract));
    }

    @Test
    void testGetClinicsViaContracts() {
        Clinic c1 = new Clinic("Medicover", "Krzywa", "16", "34-470", "Czarny Dunajec");
        Clinic c2 = new Clinic("LuxMed", "Długa", "12B", "00-001", "Warszawa");

        new Contract(d1, c1, LocalDate.of(2024, 1, 1), 5000.0);
        new Contract(d1, c2, LocalDate.of(2024, 2, 1), 6000.0);

        assertEquals(2, d1.getClinics().size());
        assertTrue(d1.getClinics().contains(c1));
        assertTrue(d1.getClinics().contains(c2));
    }

    @Test
    void testContractsUnmodifiable() {
        assertThrows(UnsupportedOperationException.class, () -> d1.getContracts().clear());
    }

    @Test
    void testClinicsUnmodifiable() {
        assertThrows(UnsupportedOperationException.class, () -> d1.getClinics().clear());
    }

    @Test
    void testTerminateContractRemovesFromDoctor() {
        Clinic c1 = new Clinic("Medicover", "Krzywa", "16", "34-470", "Czarny Dunajec");
        Contract contract = new Contract(d1, c1, LocalDate.of(2024, 1, 1), 5000.0);

        contract.terminate();

        assertEquals(0, d1.getContracts().size());
        assertEquals(0, d1.getClinics().size());
    }

    @Test
    void testAddSpecialization() {
        Specialization s1 = new Specialization("Kardiologia");

        d1.addSpecialization(s1);

        assertEquals(1, d1.getSpecializations().size());
        assertTrue(d1.getSpecializations().contains(s1));
        // połączenie zwrotne
        assertTrue(s1.getDoctors().contains(d1));
    }

    @Test
    void testAddSpecializationDuplicate() {
        Specialization s1 = new Specialization("Kardiologia");

        d1.addSpecialization(s1);
        d1.addSpecialization(s1);

        assertEquals(1, d1.getSpecializations().size());
        assertEquals(1, s1.getDoctors().size());
    }

    @Test
    void testAddSpecializationNull() {
        assertThrows(IllegalArgumentException.class, () -> d1.addSpecialization(null));
    }

    @Test
    void testRemoveSpecialization() {
        Specialization s1 = new Specialization("Kardiologia");
        d1.addSpecialization(s1);

        d1.removeSpecialization(s1);

        assertEquals(0, d1.getSpecializations().size());
        assertFalse(d1.getSpecializations().contains(s1));
        // połączenie zwrotne usunięte
        assertFalse(s1.getDoctors().contains(d1));
    }

    @Test
    void testRemoveSpecializationNotExisting() {
        Specialization s1 = new Specialization("Kardiologia");

        // usuwanie specjalizacji której nie ma - nic się nie dzieje
        d1.removeSpecialization(s1);
        assertEquals(0, d1.getSpecializations().size());
    }

    @Test
    void testRemoveSpecializationNull() {
        assertThrows(IllegalArgumentException.class, () -> d1.removeSpecialization(null));
    }

    @Test
    void testMultipleSpecializations() {
        Specialization s1 = new Specialization("Kardiologia");
        Specialization s2 = new Specialization("Neurologia");
        Specialization s3 = new Specialization("Pediatria");

        d1.addSpecialization(s1);
        d1.addSpecialization(s2);
        d1.addSpecialization(s3);

        assertEquals(3, d1.getSpecializations().size());
    }

    @Test
    void testSpecializationsUnmodifiable() {
        assertThrows(UnsupportedOperationException.class,
                () -> d1.getSpecializations().add(new Specialization("Test")));
    }

    @Test
    void testExtensionAndCount() {
        int countBefore = Doctor.getNumberOfDoctors();

        Doctor d2 = new Doctor("Anna", "Nowak", "85050567890", "7654321");

        assertEquals(countBefore + 1, Doctor.getNumberOfDoctors());
        assertTrue(Doctor.getExtend().contains(d2));
    }

    @Test
    void testExtensionUnmodifiable() {
        assertThrows(UnsupportedOperationException.class, () -> Doctor.getExtend().add(d1));
    }

    @Test
    void testEqualsAndHashCode() {
        // equals oparty na PWZ
        Doctor same = new Doctor("Piotr", "Wiśniewski", "75030587654", "1234567");
        Doctor different = new Doctor("Jan", "Kowalski", "90010112345", "9999999");

        assertEquals(d1, same);
        assertEquals(d1.hashCode(), same.hashCode());
        assertNotEquals(d1, different);
    }

    @Test
    void testEqualsEdgeCases() {
        assertEquals(d1, d1);
        assertNotEquals(d1, null);
        assertNotEquals(d1, "not a Doctor");
    }

    @Test
    void testToString() {
        String result = d1.toString();

        assertTrue(result.contains("Jan"));
        assertTrue(result.contains("Kowalski"));
        assertTrue(result.contains("90010112345"));
        assertTrue(result.contains("1234567"));
    }
}
