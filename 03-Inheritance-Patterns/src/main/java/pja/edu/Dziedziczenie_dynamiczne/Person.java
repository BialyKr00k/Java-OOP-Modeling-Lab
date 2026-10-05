package pja.edu.Dziedziczenie_dynamiczne;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Person {
    private String pesel;
    private String name;
    private LocalDate birthDate;

    protected static final Set<Person> extent = new HashSet<>();

    public Person(String pesel, String name, LocalDate birthDate) {
        setPesel(pesel);
        setName(name);
        setBirthDate(birthDate);
        extent.add(this);
    }

    public Person(Person prevPerson) {
        this(prevPerson.getPesel(), prevPerson.getName(), prevPerson.getBirthDate());
    }

    public abstract int calculateAge();

    protected int ageFromBirthDate() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        if (pesel == null || pesel.isBlank()) {
            throw new IllegalArgumentException("PESEL nie może być pusty");
        }
        this.pesel = pesel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Imię i nazwisko nie może być puste");
        }
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("Data urodzenia nie może być null");
        }
        this.birthDate = birthDate;
    }

    public static Set<Person> getPersonExtent() {
        return Collections.unmodifiableSet(extent);
    }

    public static void showExtent() {
        System.out.println("=== Person extent ===");
        extent.forEach(System.out::println);
    }

    protected static void removeFromExtent(Person person) {
        extent.remove(person);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                "{pesel='" + pesel + '\'' +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", age=" + calculateAge() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return Objects.equals(pesel, person.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pesel);
    }
}
