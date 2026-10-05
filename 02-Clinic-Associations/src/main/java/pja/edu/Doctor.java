package pja.edu;

import java.util.*;

public class Doctor {
    private String firstName;
    private String surname;
    private String pesel;
    private String pwz;
    private List<Contract> contracts = new ArrayList<>();
    private List<Specialization> specializations = new ArrayList<>();
    private static List<Doctor> extend = new ArrayList<>();

    public Doctor(String firstName, String surname, String pesel, String pwz){
        setFirstName(firstName);
        setSurname(surname);
        setPesel(pesel);
        setPwz(pwz);
        extend.add(this);
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
    public String getPesel(){
        return pesel;
    }
    public void setPesel(String pesel){
        if(pesel == null || !pesel.matches("\\d{11}")){
            throw new IllegalArgumentException("PESEL must contain 11 digits!");
        }
        this.pesel = pesel;
    }
    public String getPwz(){
        return pwz;
    }
    public void setPwz(String pwz){
        if(pwz == null || !pwz.matches("\\d{7}")){
            throw new IllegalArgumentException("PWZ number must contain 7 digits!");
        }
        this.pwz = pwz;
    }

    void addContract(Contract contract){
        if(!contracts.contains(contract)){
            contracts.add(contract);
        }
    }

    void removeContract(Contract contract){
        contracts.remove(contract);
    }

    public List<Contract> getContracts(){
        return Collections.unmodifiableList(contracts);
    }

    public Set<Clinic> getClinics(){
        Set<Clinic> res = new HashSet<>();
        for(Contract contract : contracts){
            res.add(contract.getClinic());
        }
        return Collections.unmodifiableSet(res);
    }

    public void addSpecialization(Specialization specialization){
        if(specialization == null){
            throw new IllegalArgumentException("Specialization must not be null!");
        }
        if(!specializations.contains(specialization)){
            specializations.add(specialization);
            specialization.addDoctor(this);
        }
    }

    public void removeSpecialization(Specialization specialization){
        if(specialization == null){
            throw new IllegalArgumentException("Specialization must not be null!");
        }
        if(specializations.contains(specialization)){
            specializations.remove(specialization);
            specialization.removeDoctor(this);
        }
    }

    public List<Specialization> getSpecializations(){
        return Collections.unmodifiableList(specializations);
    }

    public static List<Doctor> getExtend(){
        return Collections.unmodifiableList(extend);
    }

    public static int getNumberOfDoctors(){
        return extend.size();
    }

    @Override
    public String toString() {
        return "Doctor: " + firstName + " " + surname + ", PESEL: " + pesel + ", PWZ: " + pwz
                + ", number of contracts: " + contracts.size();
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Doctor l = (Doctor) obj;
        return Objects.equals(pwz, l.pwz);
    }

    @Override
    public int hashCode(){
        return Objects.hash(pwz);
    }
}
