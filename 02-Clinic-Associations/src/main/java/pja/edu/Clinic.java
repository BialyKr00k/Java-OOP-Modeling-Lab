package pja.edu;

import java.util.*;

public class Clinic {
    private String name;
    private List<Adress> adresses = new ArrayList<>();
    private Map<String, Patient> patientsQualif = new TreeMap<>();
    private static List<Clinic> extend = new ArrayList<>();
    private List<Contract> contracts = new ArrayList<>();

    public Clinic(String name, String streetName, String homeNumber, String postalCode, String cityName){
        setName(name);
        addAdress(streetName, homeNumber, postalCode, cityName);
        extend.add(this);
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Clinic name must not be null or empty!");
        }
        name = name.trim();
        this.name = name;
    }

    public void addAdress(String streetName, String homeNumber, String postalCode, String cityName){
        adresses.add(new Adress(streetName, homeNumber, postalCode, cityName));
    }
    public boolean removeAdress(Adress adress){
        if(adress == null){
            throw new IllegalArgumentException("Address must not be null!");
        }
        if(adresses.size() <= 1){
            throw new IllegalStateException("Clinic must have at least one address!");
        }
        return adresses.remove(adress);
    }
    public List<Adress> getAdresses(){
        List<Adress> copyAdress = new ArrayList<>();
        for(Adress adress : adresses){
            copyAdress.add(new Adress(adress));
        }
        return Collections.unmodifiableList(copyAdress);
    }

    public void addPatientQualif(Patient newPatient){
        if(newPatient == null){
            throw new IllegalArgumentException("Patient must not be null!");
        }
        if(!patientsQualif.containsKey(newPatient.getPesel())){
            patientsQualif.put(newPatient.getPesel(), newPatient);
            newPatient.addClinicQualif(this);
        }
    }

    public void removePatientQualif(Patient patient){
        if(patient == null){
            throw new IllegalArgumentException("Patient must not be null!");
        }
        if(patientsQualif.containsKey(patient.getPesel())){
            patientsQualif.remove(patient.getPesel());
            patient.removeClinicQualif(this);
        }
    }

    public Patient findPatientQualif(String pesel) throws Exception {
        if(!patientsQualif.containsKey(pesel)){
            throw new Exception("Unable to find a patient with this PESEL: " + pesel);
        }
        return patientsQualif.get(pesel);
    }

    public Collection<Patient> getPatients(){
        return Collections.unmodifiableCollection(patientsQualif.values());
    }

    public int getNumberOfPatients(){
        return patientsQualif.size();
    }

    public static List<Clinic> getExtend(){
        return Collections.unmodifiableList(extend);
    }

    public static int getNumberOfClinics(){
        return extend.size();
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

    public Set<Doctor> getDoctors(){
        Set<Doctor> res = new HashSet<>();
        for(Contract contract : contracts){
            res.add(contract.getDoctor());
        }
        return Collections.unmodifiableSet(res);
    }

    void updatePatientPesel(String oldPesel, Patient patient){
        if(patientsQualif.containsKey(oldPesel)){
            patientsQualif.remove(oldPesel);
            patientsQualif.put(patient.getPesel(), patient);
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Clinic: ").append(name)
                .append(", patients: ").append(patientsQualif.size())
                .append(", addresses: ");
        for(int i = 0; i < adresses.size(); i++){
            if(i > 0) sb.append("; ");
            sb.append(adresses.get(i));
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Clinic clinic = (Clinic) obj;
        return Objects.equals(name, clinic.name);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name);
    }
}
