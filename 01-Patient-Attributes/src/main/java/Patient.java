import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class Patient implements Serializable {
    private String firstName;
    private String secondName; //Atrybut opcjonalny
    private String surname;
    private LocalDate dateOfBirth;
    private String pesel;
    private Adres adres; //Atrybut zlozony
    private Set<String> phoneNumbers; //Atrybut powtarzalny
    private static int nextId = 1; //Atrybut klasowy
    private static final int MAX_AGE_YEARS = 200;
    private final int patientId;
    private static List<Patient> extension = new ArrayList<>(); //Ekstensja

    //Konstruktor dla pacjenta z drugim imieniem:
    public Patient(String firstName, String secondName, String surname, LocalDate dateOfBirth, String pesel,
                   Set<String> phoneNumbers, String streetName, String homeNumber, String postalCode, String cityName){
        Adres adres1 = new Adres(streetName, homeNumber, postalCode, cityName);
        setFirstName(firstName);
        setSecondName(secondName);
        setSurname(surname);
        setDateOfBirth(dateOfBirth);
        setPesel(pesel);
        this.phoneNumbers = (phoneNumbers == null) ? new HashSet<>() : new HashSet<>(phoneNumbers);
        this.adres = adres1;
        this.patientId = nextId++;
        extension.add(this);
    }

    //Konstruktor dla pacjenta bez drugiego imienia:
    public Patient(String firstName, String surname, LocalDate dateOfBirth, String pesel, Set<String> phoneNumbers,
                   String streetName, String homeNumber, String postalCode, String cityName){
        this(firstName, null, surname, dateOfBirth, pesel, phoneNumbers,
                streetName, homeNumber, postalCode, cityName);
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        if (firstName == null || firstName.trim().isEmpty()){
            throw new IllegalArgumentException("First name must not be null or empty!");
        }
        firstName = firstName.trim();
        if (!firstName.matches("\\p{L}+")){
            throw new IllegalArgumentException("First name must contain only letters!");
        }
        if (Character.isLowerCase(firstName.charAt(0))){
            throw new IllegalArgumentException("First name must start with capital letter!");
        }
        if (!firstName.substring(1).equals(firstName.substring(1).toLowerCase())){
            throw new IllegalArgumentException("Only the first letter must be capitalized!");
        }
        this.firstName = firstName;
    }

    public String getSurname(){
        return surname;
    }

    public void setSurname(String surname){
        if (surname == null || surname.trim().isEmpty()){
            throw new IllegalArgumentException("Surname must not be null or empty!");
        }
        surname = surname.trim();
        if (!surname.matches("\\p{L}+")){
            throw new IllegalArgumentException("Surname must contain only letters!");
        }
        if (Character.isLowerCase(surname.charAt(0))){
            throw new IllegalArgumentException("Surname must start with capital letter!");
        }
        if (!surname.substring(1).equals(surname.substring(1).toLowerCase())){
            throw new IllegalArgumentException("Only the first letter must be capitalized!");
        }
        this.surname = surname;
    }

    public LocalDate getDateOfBirth(){
        return dateOfBirth;
    }

    //YYYY-MM-DD
    public void setDateOfBirth(LocalDate dateOfBirth){
        if (dateOfBirth == null){
            throw new IllegalArgumentException("Birth date must not be null!");
        }
        if (dateOfBirth.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Birth date cannot be from the future!");
        }
        if (Period.between(dateOfBirth, LocalDate.now()).getYears() > MAX_AGE_YEARS) {
            throw new IllegalArgumentException("Patient cannot be older than " + MAX_AGE_YEARS + " years!");
        }
        this.dateOfBirth = dateOfBirth;
    }

    public String getPesel(){
        return pesel;
    }

    public void setPesel(String pesel){
        if (pesel == null || !pesel.matches("\\d{11}")){
            throw new IllegalArgumentException("PESEL must contain 11 digits!");
        }
        this.pesel = pesel;
    }

    public String getSecondName(){
        return (secondName != null) ? secondName : "";
    }

    public void setSecondName(String secondName){
        if (secondName == null) {
            this.secondName = null;
            return;
        }

        secondName = secondName.trim();

        if (secondName.isEmpty()) {
            this.secondName = null;
            return;
        }

        if (!secondName.matches("\\p{L}+")){
            throw new IllegalArgumentException("Second name must contain only letters!");
        }
        if (Character.isLowerCase(secondName.charAt(0))){
            throw new IllegalArgumentException("Second name must start with capital letter!");
        }
        if (!secondName.substring(1).equals(secondName.substring(1).toLowerCase())){
            throw new IllegalArgumentException("Only the first letter must be capitalized!");
        }
        this.secondName = secondName;
    }

    public Set<String> getPhoneNumbers(){
        return Collections.unmodifiableSet(phoneNumbers);
    }

    public void setPhoneNumbers(Set<String> phoneNumbers){
        if (phoneNumbers == null) {
            throw new IllegalArgumentException("Phone numbers set must not be null!");
        }
        for (String number : phoneNumbers) {
            if (number == null || !number.matches("\\d{9,13}")) {
                throw new IllegalArgumentException("Invalid phone number: " + number);
            }
        }
        this.phoneNumbers = new HashSet<>(phoneNumbers);
    }

    public void addPhoneNumber(String phoneNumber){
        if (phoneNumber == null || !phoneNumber.matches("\\d{9,13}")) {
            throw new IllegalArgumentException("Illegal format of phone number!");
        }
        this.phoneNumbers.add(phoneNumber);
    }

    public void removePhoneNumber(String phoneNumber){
        if (phoneNumber == null) {
            throw new IllegalArgumentException("You cannot remove number which is null!");
        }
        if (this.phoneNumbers.size() == 1 && this.phoneNumbers.contains(phoneNumber)) {
           throw new IllegalArgumentException("You cannot remove last phone number!");
        }
        boolean removedNumber = this.phoneNumbers.remove(phoneNumber);

        if (!removedNumber) {
            throw new IllegalArgumentException("This number (" + phoneNumber + ") does not exist in database!");
        }
    }

    public static int getNumberOfPatients(){
        return extension.size();
    }

    public int getPatientId(){
        return patientId;
    }

    //Ze wzgledu na hermetyzajce zwracamy kopie:
    public Adres getAdres(){
        return new Adres(adres.getStreetName(), adres.getHomeNumber(),
                adres.getPostalCode(), adres.getCityName());
    }

    public void setAdres(String streetName, String homeNumber, String postalCode, String cityName){
        this.adres = new Adres(streetName, homeNumber, postalCode, cityName);
    }

    public static List<Patient> getExtension(){
        return Collections.unmodifiableList(extension);
    }

    //Trwalosc ekstensji (zapis do pliku):
    public static void saveExtension(ObjectOutputStream oos) throws IOException {
        oos.writeObject(extension);
    }

    public static void readExtension(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        List<Patient> loadedExtension = (List<Patient>) ois.readObject();
        extension.clear();
        extension.addAll(loadedExtension);
        int maxId = 0;
        for (Patient p : extension) {
            if (p.patientId > maxId) {
                maxId = p.patientId;
            }
        }
        nextId = maxId + 1;
    }

    //Atrybut pochodny:
    public int getAge() {
        LocalDate today = LocalDate.now();
        return Period.between(this.dateOfBirth, today).getYears();
    }

    //Przesloniecie:
    @Override
    public String toString() {
        return "Patient #" + patientId + ": " + firstName + " " + surname
                + ", PESEL: " + pesel
                + ", Birth date: " + dateOfBirth
                + ", Age: " + getAge();
    }

    //Przeciazenie metody:
    public String toString(boolean showSecondName) {
        if (showSecondName && secondName != null) {
            return "Patient #" + patientId + ": " + firstName + " " + secondName + " " + surname
                    + ", PESEL: " + pesel
                    + ", Birth date: " + dateOfBirth
                    + ", Age: " + getAge();
        }
        return toString();
    }
}

