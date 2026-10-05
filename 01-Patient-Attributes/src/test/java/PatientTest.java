import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class PatientTest {

    Patient p1;

    @BeforeEach
    void setup() {
        p1 = new Patient("Michał",
                "Piotr",
                "Sebastian",
                LocalDate.of(2003, 2, 18),
                "10101010101",
                Set.of("987654321", "123456789"),
                "Krzywa",
                "16",
                "34-470",
                "Czarny Dunajec"
        );
    }

    @Test
    void testFirstName() {
        assertEquals("Michał", p1.getFirstName());
        p1.setFirstName("Jakub");
        assertEquals("Jakub", p1.getFirstName());

        assertThrows(IllegalArgumentException.class, () -> p1.setFirstName(null));
        assertThrows(IllegalArgumentException.class, () -> p1.setFirstName(""));
        assertThrows(IllegalArgumentException.class, () -> p1.setFirstName("michał"));
        assertThrows(IllegalArgumentException.class, () -> p1.setFirstName("MICHAŁ"));
        assertThrows(IllegalArgumentException.class, () -> p1.setFirstName("    "));
        assertThrows(IllegalArgumentException.class, () -> p1.setFirstName("Michał123"));
    }

    @Test
    void testSurname() {
        assertEquals("Sebastian", p1.getSurname());
        p1.setSurname("Adamski");
        assertEquals("Adamski", p1.getSurname());

        assertThrows(IllegalArgumentException.class, () -> p1.setSurname(null));
        assertThrows(IllegalArgumentException.class, () -> p1.setSurname(""));
        assertThrows(IllegalArgumentException.class, () -> p1.setSurname("sebastian"));
        assertThrows(IllegalArgumentException.class, () -> p1.setSurname("SEBASTIAN"));
        assertThrows(IllegalArgumentException.class, () -> p1.setSurname("     "));
        assertThrows(IllegalArgumentException.class, () -> p1.setSurname("Sebastian123"));
    }

    @Test
    void testDateOfBirth() {
        assertEquals(LocalDate.of(2003, 2, 18), p1.getDateOfBirth());
        p1.setDateOfBirth(LocalDate.of(2000, 8, 5));
        assertEquals(LocalDate.of(2000, 8, 5), p1.getDateOfBirth());

        assertThrows(IllegalArgumentException.class, () -> p1.setDateOfBirth(null));
        assertThrows(IllegalArgumentException.class, () -> p1.setDateOfBirth(LocalDate.of(1111, 1, 1)));
        assertThrows(IllegalArgumentException.class, () -> p1.setDateOfBirth(LocalDate.of(2027, 9,9)));
    }

    @Test
    void testPesel() {
        assertEquals("10101010101", p1.getPesel());
        p1.setPesel("12345678901");
        assertEquals("12345678901", p1.getPesel());

        assertThrows(IllegalArgumentException.class, () -> p1.setPesel(null));
        assertThrows(IllegalArgumentException.class, () -> p1.setPesel("123456789"));
        assertThrows(IllegalArgumentException.class, () -> p1.setPesel("123456789012"));
        assertThrows(IllegalArgumentException.class, () -> p1.setPesel(""));
        assertThrows(IllegalArgumentException.class, () -> p1.setPesel("sWARGE  "));
        assertThrows(IllegalArgumentException.class, () -> p1.setPesel("    "));
    }

    @Test
    void testSecondName() {
        assertEquals("Piotr", p1.getSecondName());
        p1.setSecondName("");
        assertEquals("", p1.getSecondName());
        p1.setSecondName("Paweł");
        assertEquals("Paweł", p1.getSecondName());

        assertThrows(IllegalArgumentException.class, () -> p1.setSecondName("Paweł43"));
        assertThrows(IllegalArgumentException.class, () -> p1.setSecondName("paweł"));
        assertThrows(IllegalArgumentException.class, () -> p1.setSecondName("PAWEŁ"));
    }

    @Test
    void testPhoneNumbers() {
        assertEquals(2, p1.getPhoneNumbers().size());
        assertTrue(p1.getPhoneNumbers().contains("987654321"));

        p1.setPhoneNumbers(Set.of("48123456789", "987654321"));
        assertEquals(2, p1.getPhoneNumbers().size());
        assertTrue(p1.getPhoneNumbers().contains("48123456789"));
        assertTrue(p1.getPhoneNumbers().contains("987654321"));

        assertThrows(IllegalArgumentException.class, () -> p1.setPhoneNumbers(null));
        Set<String> nullSet = new HashSet<>();
        nullSet.add(null);
        assertThrows(IllegalArgumentException.class, () -> p1.setPhoneNumbers(nullSet));

        assertThrows(IllegalArgumentException.class, () -> p1.setPhoneNumbers(Set.of("123456")));
        assertThrows(IllegalArgumentException.class, () -> p1.setPhoneNumbers(Set.of("123456789000000")));
        assertThrows(IllegalArgumentException.class, () -> p1.setPhoneNumbers(Set.of("12345abc")));
        assertThrows(IllegalArgumentException.class, () -> p1.setPhoneNumbers(Set.of("")));
        assertThrows(IllegalArgumentException.class, () -> p1.setPhoneNumbers(Set.of("   ")));
    }

    @Test
    void testAddPhoneNumber() {
        p1.addPhoneNumber("48789456123");
        assertTrue(p1.getPhoneNumbers().contains("48789456123"));

        assertThrows(IllegalArgumentException.class, () -> p1.addPhoneNumber(null));
        assertThrows(IllegalArgumentException.class, () -> p1.addPhoneNumber("123"));
        assertThrows(IllegalArgumentException.class, () -> p1.addPhoneNumber("123456789123456"));
        assertThrows(IllegalArgumentException.class, () -> p1.addPhoneNumber("123456789av"));
        assertThrows(IllegalArgumentException.class, () -> p1.addPhoneNumber(""));
        assertThrows(IllegalArgumentException.class, () -> p1.addPhoneNumber("  "));
    }

    @Test
    void testRemovePhoneNumber() {
        p1.removePhoneNumber("987654321");
        assertFalse(p1.getPhoneNumbers().contains("987654321"));

        assertThrows(IllegalArgumentException.class, () -> p1.removePhoneNumber("123456789"));
        assertTrue(p1.getPhoneNumbers().contains("123456789"));

        assertThrows(IllegalArgumentException.class, () -> p1.removePhoneNumber(null));
        assertThrows(IllegalArgumentException.class, () -> p1.removePhoneNumber("000000000"));
    }

    @Test
    void testExtensionAndCount() {
        int countBefore = Patient.getNumberOfPatients();

        Patient p2 = new Patient("Adam",
                "Testowy",
                LocalDate.of(2000, 2, 2),
                "00220233333",
                Set.of("123456789"),
                "Długa",
                "12B",
                "00-001",
                "Warszawa"
        );

        assertEquals(countBefore + 1, Patient.getNumberOfPatients());
        assertTrue(Patient.getExtension().contains(p2));
    }

    @Test
    void testExtensionUnmodifiable() {
        assertThrows(UnsupportedOperationException.class, () -> Patient.getExtension().add(p1));
    }

    @Test
    void testPatientId() {
        Patient p2 = new Patient("Adam",
                "Testowy",
                LocalDate.of(2000, 2, 2),
                "00220233333",
                Set.of("123456789"),
                "Długa",
                "12B",
                "00-001",
                "Warszawa"
        );

        assertTrue(p2.getPatientId() > p1.getPatientId());
    }

    @Test
    void testGetAdressAsCopy() {
        Adres adres1 = p1.getAdres();

        adres1.setCityName("Krakow");
        assertNotEquals("Krakow", p1.getAdres().getCityName());
    }

    @Test
    void testAdress() {
        p1.setAdres("Nowa", "5A", "31-100", "Krakow");
        assertEquals("Nowa", p1.getAdres().getStreetName());
        assertEquals("5A", p1.getAdres().getHomeNumber());
        assertEquals("31-100", p1.getAdres().getPostalCode());
        assertEquals("Krakow", p1.getAdres().getCityName());

        assertThrows(IllegalArgumentException.class, () -> p1.setAdres(null, "5", "00-001", "Warszawa"));
        assertThrows(IllegalArgumentException.class, () -> p1.setAdres("", "5", "00-001", "Warszawa"));
        assertThrows(IllegalArgumentException.class, () -> p1.setAdres("     ", "5", "00-001", "Warszawa"));
        assertThrows(IllegalArgumentException.class, () -> p1.setAdres("nowa", "5", "00-001", "Warszawa"));

        assertThrows(IllegalArgumentException.class, () -> p1.setAdres("Nowa", null, "00-001", "Warszawa"));
        assertThrows(IllegalArgumentException.class, () -> p1.setAdres("Nowa", "", "00-001", "Warszawa"));
        assertThrows(IllegalArgumentException.class, () -> p1.setAdres("Nowa", "    ", "00-001", "Warszawa"));

        assertThrows(IllegalArgumentException.class, () -> p1.setAdres("Nowa", "5", null, "Warszawa"));
        assertThrows(IllegalArgumentException.class, () -> p1.setAdres("Nowa", "5", "", "Warszawa"));
        assertThrows(IllegalArgumentException.class, () -> p1.setAdres("Nowa", "5", "   ", "Warszawa"));
        assertThrows(IllegalArgumentException.class, () -> p1.setAdres("Nowa", "5", "aa-bbb", "Warszawa"));
        assertThrows(IllegalArgumentException.class, () -> p1.setAdres("Nowa", "5", "aabbb", "Warszawa"));
        assertThrows(IllegalArgumentException.class, () -> p1.setAdres("Nowa", "5", "34450", "Warszawa"));

        assertThrows(IllegalArgumentException.class, () -> p1.setAdres("Nowa", "5", "34450", null));
        assertThrows(IllegalArgumentException.class, () -> p1.setAdres("Nowa", "5", "34450", ""));
        assertThrows(IllegalArgumentException.class, () -> p1.setAdres("Nowa", "5", "34450", "  "));
        assertThrows(IllegalArgumentException.class, () -> p1.setAdres("Nowa", "5", "34450", "warszawa"));
    }

    @Test
    void testAdrestoString() {
        p1.setAdres("Marszalkowska", "10", "00-001", "Warszawa");
        assertEquals("Marszalkowska 10, 00-001 Warszawa", p1.getAdres().toString());
    }

    @Test
    void testSaveAndReadExtension() throws Exception {
        String file = "test_extension.dat";

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            Patient.saveExtension(oos);
        }

        int countBefore = Patient.getNumberOfPatients();
        String nameBefore = p1.getFirstName();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Patient.readExtension(ois);
        }

        assertEquals(countBefore, Patient.getNumberOfPatients());
        assertEquals(nameBefore, Patient.getExtension().get(0).getFirstName());

        new java.io.File(file).delete();
    }

    @Test
    void testGetAge() {
        assertEquals(23, p1.getAge());
    }

    @Test
    void testToString() {
        String result = p1.toString();

        assertTrue(result.contains("Michał"));
        assertTrue(result.contains("Sebastian"));
        assertTrue(result.contains("10101010101"));
        assertTrue(result.contains("2003-02-18"));
    }

    @Test
    void testToStringWithSecondName() {
        String result1 = p1.toString(true);
        assertTrue(result1.contains("Piotr"));

        String result2 = p1.toString(false);
        assertFalse(result2.contains("Piotr"));
    }
}
