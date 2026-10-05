package pja.edu;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class Patient {
    private String firstName;
    private String secondName;
    private String surname;
    private LocalDate dateOfBirth;
    private String pesel;
    private List<Adress> adresses = new ArrayList<>();
    public static final int MAX_AGE_YEARS = 150;
    private static List<Patient> extend = new ArrayList<>();
    private Set<Clinic> clinics = new HashSet<>();
    private List<MedicalHistory> medicalHistories = new ArrayList<>();

    public Patient(String firstName, String surname, LocalDate dateOfBirth, String pesel, String streetName,
                   String homeNumber, String postalCode, String cityName){
        init(firstName, surname, dateOfBirth, pesel, streetName, homeNumber, postalCode, cityName);
        extend.add(this);
    }
    public Patient(String firstName, String secondName, String surname, LocalDate dateOfBirth, String pesel,
                   String streetName, String homeNumber, String postalCode, String cityName){
        init(firstName, surname, dateOfBirth, pesel, streetName, homeNumber, postalCode, cityName);
        setSecondName(secondName);
        extend.add(this);
    }
    private void init(String firstName, String surname, LocalDate dateOfBirth, String pesel, String streetName,
                      String homeNumber, String postalCode, String cityName){
        setFirstName(firstName);
        setSurname(surname);
        setDateOfBirth(dateOfBirth);
        setPesel(pesel);
        addAdress(streetName, homeNumber, postalCode, cityName);
    }

    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName){
        if(firstName == null || firstName.trim().isEmpty()){
            throw new IllegalArgumentException("First name must not be null or empty!");
        }
        firstName = firstName.trim();
        if(!firstName.matches("\\p{L}+")){
            throw new IllegalArgumentException("First name must contain only letters!");
        }
        if(Character.isLowerCase(firstName.charAt(0))){
            throw new IllegalArgumentException("First name must start with capital letter!");
        }
        if(!firstName.substring(1).equals(firstName.substring(1).toLowerCase())){
            throw new IllegalArgumentException("Only the first letter must be capitalized!");
        }
        this.firstName = firstName;
    }
    public String getSurname(){
        return surname;
    }
    public void setSurname(String surname){
        if(surname == null || surname.trim().isEmpty()){
            throw new IllegalArgumentException("Surname must not be null or empty!");
        }
        surname = surname.trim();
        if(!surname.matches("\\p{L}+")){
            throw new IllegalArgumentException("Surname must contain only letters!");
        }
        if(Character.isLowerCase(surname.charAt(0))){
            throw new IllegalArgumentException("Surname must start with capital letter!");
        }
        if(!surname.substring(1).equals(surname.substring(1).toLowerCase())){
            throw new IllegalArgumentException("Only the first letter must be capitalized!");
        }
        this.surname = surname;
    }
    public LocalDate getDateOfBirth(){
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth){
        if(dateOfBirth == null){
            throw new IllegalArgumentException("Birth date must not be null!");
        }
        if(dateOfBirth.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Birth date cannot be from the future!");
        }
        if(Period.between(dateOfBirth, LocalDate.now()).getYears() > MAX_AGE_YEARS){
            throw new IllegalArgumentException("Patient cannot be older than " + MAX_AGE_YEARS + " years!");
        }
        this.dateOfBirth = dateOfBirth;
    }
    public String getPesel(){
        return pesel;
    }
    public void setPesel(String pesel){
        if(pesel == null || !pesel.matches("\\d{11}")){
            throw new IllegalArgumentException("PESEL must contain 11 digits!");
        }
        String oldPesel = this.pesel;
        this.pesel = pesel;

        if(oldPesel != null && !oldPesel.equals(pesel)){
            for(Clinic c : clinics){
                c.updatePatientPesel(oldPesel, this);
            }
        }
    }
    public String getSecondName(){
        return secondName;
    }
    public void setSecondName(String secondName){
        if(secondName == null || secondName.trim().isEmpty()){
            throw new IllegalArgumentException("Second name must not be null or empty!");
        }
        secondName = secondName.trim();
        if(!secondName.matches("\\p{L}+")){
            throw new IllegalArgumentException("Second name must contain only letters!");
        }
        if(Character.isLowerCase(secondName.charAt(0))){
            throw new IllegalArgumentException("Second name must start with capital letter!");
        }
        if(!secondName.substring(1).equals(secondName.substring(1).toLowerCase())){
            throw new IllegalArgumentException("Only the first letter must be capitalized!");
        }
        this.secondName = secondName;
    }

    public void addAdress(String streetName, String homeNumber, String postalCode, String cityName){
        adresses.add(new Adress(streetName, homeNumber, postalCode, cityName));
    }
    public boolean removeAdress(Adress adress){
        if(adress == null){
            throw new IllegalArgumentException("Address must not be null!");
        }
        if(adresses.size() <= 1){
            throw new IllegalStateException("Patient must have at least one address!");
        }
        return adresses.remove(adress);
    }
    public List<Adress> getAdresses(){
        List<Adress> copyAdresses = new ArrayList<>();
        for(Adress adress : adresses){
            copyAdresses.add(new Adress(adress));
        }
        return Collections.unmodifiableList(copyAdresses);
    }

    public static int getNumberOfPatients(){
        return extend.size();
    }

    public int getAge(){
        LocalDate today = LocalDate.now();
        return Period.between(this.dateOfBirth, today).getYears();
    }
    public static List<Patient> getExtend(){
        return Collections.unmodifiableList(extend);
    }

    public void addClinicQualif(Clinic newClinic){
        if(newClinic == null){
            throw new IllegalArgumentException("Clinic must not be null!");
        }
        if(!clinics.contains(newClinic)){
            clinics.add(newClinic);
            newClinic.addPatientQualif(this);
        }
    }

    public void removeClinicQualif(Clinic clinic){
        if(clinic == null){
            throw new IllegalArgumentException("Clinic must not be null!");
        }
        if(clinics.contains(clinic)){
            clinics.remove(clinic);
            clinic.removePatientQualif(this);
        }
    }
    public Set<Clinic> getClinics(){
        return Collections.unmodifiableSet(clinics);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Patient: ").append(firstName).append(" ");
        if(secondName != null){
            sb.append(secondName).append(" ");
        }
        sb.append(surname)
                .append(", PESEL: ").append(pesel)
                .append(", date of birth: ").append(dateOfBirth)
                .append(" (age: ").append(getAge()).append(")");
        sb.append(", addresses: ");
        for(int i = 0; i < adresses.size(); i++){
            if(i > 0) sb.append("; ");
            sb.append(adresses.get(i));
        }
        return sb.toString();
    }

    //oparte o pesel skoro mamy kwalifikator na podstawie peselu:
    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }
        Patient patient = (Patient) obj;
        return Objects.equals(pesel, patient.pesel);
    }
    @Override
    public int hashCode() {
        return Objects.hash(pesel);
    }

    //metoda fabrykująca - jedyny sposób zeby utworzyc medical history
    public MedicalHistory createMedicalHistory(String title, String description){
        MedicalHistory medicalHistory = new MedicalHistory(title, description);
        medicalHistories.add(medicalHistory);
        return medicalHistory;
    }

    //usuwanie części przy usunięciu całości -> zał. kompozycji
    public boolean removeMedicalHistory(MedicalHistory medicalHistory){
        return medicalHistories.remove(medicalHistory);
    }

    public List<MedicalHistory> getMedicalHistories(){
        return Collections.unmodifiableList(medicalHistories);
    }

    public class MedicalHistory{
        private String title;
        private String description;
        private final LocalDate createAt = LocalDate.now();

        private MedicalHistory(String title, String description){
            setTitle(title);
            setDescription(description);
        }

        public String getTitle(){
            return title;
        }
        public void setTitle(String title){
            if(title == null || title.trim().isEmpty()){
                throw new IllegalArgumentException("Title must not be null or empty!");
            }
            title = title.trim();
            this.title = title;
        }
        public String getDescription(){
            return description;
        }
        public void setDescription(String description){
            if(description == null || description.trim().isEmpty()){
                throw new IllegalArgumentException("Description must not be null or empty!");
            }
            description = description.trim();
            this.description = description;
        }
        public LocalDate getCreateAt(){
            return createAt;
        }

        public Patient getPatient(){
            return Patient.this;
        }

        @Override
        public String toString(){
            return "MedicalHistory \"" + title + "\" of "
                    + Patient.this.firstName + " " + Patient.this.surname
                    + " (created: " + createAt + ")\n"
                    + "  Description: " + description;
        }

        @Override
        public boolean equals(Object obj){
            if(this == obj){
                return true;
            }
            if(obj == null || getClass() != obj.getClass()){
                return false;
            }
            MedicalHistory that = (MedicalHistory) obj;
            return Objects.equals(title, that.title)
                    && Objects.equals(description, that.description)
                    && Objects.equals(createAt, that.createAt)
                    && Objects.equals(Patient.this, that.getPatient());
        }

        @Override
        public int hashCode(){
            return Objects.hash(title, description, createAt, Patient.this.pesel);
        }
    }
}
