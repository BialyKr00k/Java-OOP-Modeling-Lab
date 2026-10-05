import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pja.edu.Adress;

public class AdressTest {
    Adress a1;

    @BeforeEach
    void setup() {
        a1 = new Adress("Krzywa", "16", "34-470", "Czarny Dunajec");
    }

    @Test
    void testConstructor() {
        assertEquals("Krzywa", a1.getStreetName());
        assertEquals("16", a1.getHomeNumber());
        assertEquals("34-470", a1.getPostalCode());
        assertEquals("Czarny Dunajec", a1.getCityName());
    }

    @Test
    void testCopyConstructor() {
        Adress copy = new Adress(a1);

        assertEquals(a1.getStreetName(), copy.getStreetName());
        assertEquals(a1.getHomeNumber(), copy.getHomeNumber());
        assertEquals(a1.getPostalCode(), copy.getPostalCode());
        assertEquals(a1.getCityName(), copy.getCityName());

        // to są różne instancje - modyfikacja kopii nie zmienia oryginału
        copy.setCityName("Krakow");
        assertNotEquals(copy.getCityName(), a1.getCityName());
        assertEquals("Czarny Dunajec", a1.getCityName());
    }

    @Test
    void testStreetName() {
        assertEquals("Krzywa", a1.getStreetName());
        a1.setStreetName("Marszalkowska");
        assertEquals("Marszalkowska", a1.getStreetName());

        assertThrows(IllegalArgumentException.class, () -> a1.setStreetName(null));
        assertThrows(IllegalArgumentException.class, () -> a1.setStreetName(""));
        assertThrows(IllegalArgumentException.class, () -> a1.setStreetName("    "));
        assertThrows(IllegalArgumentException.class, () -> a1.setStreetName("krzywa"));
    }

    @Test
    void testHomeNumber() {
        assertEquals("16", a1.getHomeNumber());
        a1.setHomeNumber("12B");
        assertEquals("12B", a1.getHomeNumber());

        assertThrows(IllegalArgumentException.class, () -> a1.setHomeNumber(null));
        assertThrows(IllegalArgumentException.class, () -> a1.setHomeNumber(""));
        assertThrows(IllegalArgumentException.class, () -> a1.setHomeNumber("   "));
    }

    @Test
    void testPostalCode() {
        assertEquals("34-470", a1.getPostalCode());
        a1.setPostalCode("00-001");
        assertEquals("00-001", a1.getPostalCode());

        assertThrows(IllegalArgumentException.class, () -> a1.setPostalCode(null));
        assertThrows(IllegalArgumentException.class, () -> a1.setPostalCode(""));
        assertThrows(IllegalArgumentException.class, () -> a1.setPostalCode("   "));
        assertThrows(IllegalArgumentException.class, () -> a1.setPostalCode("34470"));
        assertThrows(IllegalArgumentException.class, () -> a1.setPostalCode("aa-bbb"));
        assertThrows(IllegalArgumentException.class, () -> a1.setPostalCode("344-70"));
        assertThrows(IllegalArgumentException.class, () -> a1.setPostalCode("3-4470"));
        assertThrows(IllegalArgumentException.class, () -> a1.setPostalCode("34-4700"));
    }

    @Test
    void testCityName() {
        assertEquals("Czarny Dunajec", a1.getCityName());
        a1.setCityName("Warszawa");
        assertEquals("Warszawa", a1.getCityName());

        assertThrows(IllegalArgumentException.class, () -> a1.setCityName(null));
        assertThrows(IllegalArgumentException.class, () -> a1.setCityName(""));
        assertThrows(IllegalArgumentException.class, () -> a1.setCityName("   "));
        assertThrows(IllegalArgumentException.class, () -> a1.setCityName("warszawa"));
    }

    @Test
    void testConstructorInvalidStreet() {
        assertThrows(IllegalArgumentException.class,
                () -> new Adress(null, "16", "34-470", "Czarny Dunajec"));
        assertThrows(IllegalArgumentException.class,
                () -> new Adress("", "16", "34-470", "Czarny Dunajec"));
        assertThrows(IllegalArgumentException.class,
                () -> new Adress("krzywa", "16", "34-470", "Czarny Dunajec"));
    }

    @Test
    void testConstructorInvalidHomeNumber() {
        assertThrows(IllegalArgumentException.class,
                () -> new Adress("Krzywa", null, "34-470", "Czarny Dunajec"));
        assertThrows(IllegalArgumentException.class,
                () -> new Adress("Krzywa", "", "34-470", "Czarny Dunajec"));
    }

    @Test
    void testConstructorInvalidPostalCode() {
        assertThrows(IllegalArgumentException.class,
                () -> new Adress("Krzywa", "16", null, "Czarny Dunajec"));
        assertThrows(IllegalArgumentException.class,
                () -> new Adress("Krzywa", "16", "34470", "Czarny Dunajec"));
        assertThrows(IllegalArgumentException.class,
                () -> new Adress("Krzywa", "16", "aa-bbb", "Czarny Dunajec"));
    }

    @Test
    void testConstructorInvalidCity() {
        assertThrows(IllegalArgumentException.class,
                () -> new Adress("Krzywa", "16", "34-470", null));
        assertThrows(IllegalArgumentException.class,
                () -> new Adress("Krzywa", "16", "34-470", ""));
        assertThrows(IllegalArgumentException.class,
                () -> new Adress("Krzywa", "16", "34-470", "czarny dunajec"));
    }

    @Test
    void testTrimming() {
        // białe znaki powinny być zdjęte przez setter
        a1.setStreetName("  Marszalkowska  ");
        assertEquals("Marszalkowska", a1.getStreetName());

        a1.setHomeNumber("  12B  ");
        assertEquals("12B", a1.getHomeNumber());

        a1.setCityName("  Warszawa  ");
        assertEquals("Warszawa", a1.getCityName());
    }

    @Test
    void testToString() {
        String result = a1.toString();

        assertTrue(result.contains("Krzywa"));
        assertTrue(result.contains("16"));
        assertTrue(result.contains("34-470"));
        assertTrue(result.contains("Czarny Dunajec"));
    }

    @Test
    void testEqualsAndHashCode() {
        Adress same = new Adress("Krzywa", "16", "34-470", "Czarny Dunajec");
        Adress diffStreet = new Adress("Inna", "16", "34-470", "Czarny Dunajec");
        Adress diffHome = new Adress("Krzywa", "99", "34-470", "Czarny Dunajec");
        Adress diffPostal = new Adress("Krzywa", "16", "00-001", "Czarny Dunajec");
        Adress diffCity = new Adress("Krzywa", "16", "34-470", "Warszawa");

        assertEquals(a1, same);
        assertEquals(a1.hashCode(), same.hashCode());

        assertNotEquals(a1, diffStreet);
        assertNotEquals(a1, diffHome);
        assertNotEquals(a1, diffPostal);
        assertNotEquals(a1, diffCity);
    }

    @Test
    void testEqualsEdgeCases() {
        assertEquals(a1, a1);                    // this == obj
        assertNotEquals(a1, null);               // null check
        assertNotEquals(a1, "not an Adress");    // different class
    }
}
