import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pja.edu.*;

public class SpecializationTest {
    Specialization s1;

    @BeforeEach
    void setup() {
        s1 = new Specialization("Kardiologia");
    }

    @Test
    void testConstructor() {
        assertEquals("Kardiologia", s1.getName());
    }

    @Test
    void testName() {
        assertEquals("Kardiologia", s1.getName());
        s1.setName("Neurologia");
        assertEquals("Neurologia", s1.getName());

        assertThrows(IllegalArgumentException.class, () -> s1.setName(null));
        assertThrows(IllegalArgumentException.class, () -> s1.setName(""));
        assertThrows(IllegalArgumentException.class, () -> s1.setName("   "));
    }

    @Test
    void testConstructorInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new Specialization(null));
        assertThrows(IllegalArgumentException.class, () -> new Specialization(""));
        assertThrows(IllegalArgumentException.class, () -> new Specialization("   "));
    }

    @Test
    void testAddDoctor() {
        Doctor d1 = new Doctor("Jan", "Kowalski", "90010112345", "1234567");

        s1.addDoctor(d1);

        assertEquals(1, s1.getDoctors().size());
        assertTrue(s1.getDoctors().contains(d1));
        // połączenie zwrotne
        assertTrue(d1.getSpecializations().contains(s1));
    }

    @Test
    void testAddDoctorDuplicate() {
        Doctor d1 = new Doctor("Jan", "Kowalski", "90010112345", "1234567");

        s1.addDoctor(d1);
        s1.addDoctor(d1);

        assertEquals(1, s1.getDoctors().size());
        assertEquals(1, d1.getSpecializations().size());
    }

    @Test
    void testAddDoctorNull() {
        assertThrows(IllegalArgumentException.class, () -> s1.addDoctor(null));
    }

    @Test
    void testRemoveDoctor() {
        Doctor d1 = new Doctor("Jan", "Kowalski", "90010112345", "1234567");
        s1.addDoctor(d1);

        s1.removeDoctor(d1);

        assertEquals(0, s1.getDoctors().size());
        assertFalse(s1.getDoctors().contains(d1));
        // połączenie zwrotne usunięte
        assertFalse(d1.getSpecializations().contains(s1));
    }

    @Test
    void testRemoveDoctorNotExisting() {
        Doctor d1 = new Doctor("Jan", "Kowalski", "90010112345", "1234567");

        // usuwanie lekarza którego nie ma - nic się nie dzieje
        s1.removeDoctor(d1);
        assertEquals(0, s1.getDoctors().size());
    }

    @Test
    void testRemoveDoctorNull() {
        assertThrows(IllegalArgumentException.class, () -> s1.removeDoctor(null));
    }

    @Test
    void testMultipleDoctors() {
        Doctor d1 = new Doctor("Jan", "Kowalski", "90010112345", "1234567");
        Doctor d2 = new Doctor("Anna", "Nowak", "85050567890", "7654321");
        Doctor d3 = new Doctor("Piotr", "Wiśniewski", "75030587654", "9999999");

        s1.addDoctor(d1);
        s1.addDoctor(d2);
        s1.addDoctor(d3);

        assertEquals(3, s1.getDoctors().size());
        assertTrue(s1.getDoctors().contains(d1));
        assertTrue(s1.getDoctors().contains(d2));
        assertTrue(s1.getDoctors().contains(d3));
    }

    @Test
    void testBidirectionalFromDoctorSide() {
        // dodanie przez Doctor.addSpecialization powinno zaktualizować obie strony
        Doctor d1 = new Doctor("Jan", "Kowalski", "90010112345", "1234567");

        d1.addSpecialization(s1);

        assertTrue(s1.getDoctors().contains(d1));
        assertTrue(d1.getSpecializations().contains(s1));
    }

    @Test
    void testBidirectionalRemoveFromDoctorSide() {
        Doctor d1 = new Doctor("Jan", "Kowalski", "90010112345", "1234567");
        d1.addSpecialization(s1);

        d1.removeSpecialization(s1);

        assertFalse(s1.getDoctors().contains(d1));
        assertFalse(d1.getSpecializations().contains(s1));
    }

    @Test
    void testDoctorsUnmodifiable() {
        assertThrows(UnsupportedOperationException.class,
                () -> s1.getDoctors().add(new Doctor("Jan", "Kowalski", "90010112345", "1234567")));
    }

    @Test
    void testExtensionAndCount() {
        int countBefore = Specialization.getNumberOfSpecializations();

        Specialization s2 = new Specialization("Pediatria");

        assertEquals(countBefore + 1, Specialization.getNumberOfSpecializations());
        assertTrue(Specialization.getExtend().contains(s2));
    }

    @Test
    void testExtensionUnmodifiable() {
        assertThrows(UnsupportedOperationException.class,
                () -> Specialization.getExtend().add(s1));
    }

    @Test
    void testEqualsAndHashCode() {
        Specialization same = new Specialization("Kardiologia");
        Specialization different = new Specialization("Neurologia");

        // equals oparty na nazwie
        assertEquals(s1, same);
        assertEquals(s1.hashCode(), same.hashCode());
        assertNotEquals(s1, different);
    }

    @Test
    void testEqualsEdgeCases() {
        assertEquals(s1, s1);
        assertNotEquals(s1, null);
        assertNotEquals(s1, "not a Specialization");
    }

    @Test
    void testToString() {
        String result = s1.toString();

        assertTrue(result.contains("Kardiologia"));
        assertTrue(result.contains("0"));

        Doctor d1 = new Doctor("Jan", "Kowalski", "90010112345", "1234567");
        s1.addDoctor(d1);
        String result2 = s1.toString();

        assertTrue(result2.contains("Kardiologia"));
        assertTrue(result2.contains("1"));
    }
}
