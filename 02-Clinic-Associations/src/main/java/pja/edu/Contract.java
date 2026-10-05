package pja.edu;

import java.time.LocalDate;
import java.util.Objects;

public class Contract {
    private Doctor doctor;
    private Clinic clinic;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private double amountOfMoney;

    //kontrakt nadal trwa:
    public Contract(Doctor doctor, Clinic clinic, LocalDate dateFrom, LocalDate dateTo, double amountOfMoney){
        if(doctor == null){
            throw new IllegalArgumentException("Doctor must not be null!");
        }
        if(clinic == null){
            throw new IllegalArgumentException("Clinic must not be null!");
        }

        for(Contract contract : doctor.getContracts()){
            if(contract.getClinic().equals(clinic) && contract.isActive()){
                throw new IllegalStateException("Active contract already exists for this doctor in this clinic");
            }
        }
        this.doctor = doctor;
        this.clinic = clinic;
        setDateFrom(dateFrom);
        setAmountOfMoney(amountOfMoney);

        if(dateTo != null){
            setDateTo(dateTo);
        }

        doctor.addContract(this);
        clinic.addContract(this);
    }

    //kontrakt zakończony:
    public Contract(Doctor doctor, Clinic clinic, LocalDate dateFrom, double amountOfMoney){
        this(doctor, clinic, dateFrom, null, amountOfMoney);
    }

    public boolean isActive(){
        return dateTo == null || !dateTo.isBefore(LocalDate.now());
    }

    public Doctor getDoctor(){
        return doctor;
    }
    public Clinic getClinic(){
        return clinic;
    }
    public void setDoctor(Doctor newDoctor){
        if(newDoctor == null){
            throw new IllegalArgumentException("Doctor must not be null!");
        }
        if(this.doctor == newDoctor){
            return;
        }
        for(Contract c : newDoctor.getContracts()){
            if(c.getClinic().equals(this.clinic) && c.isActive() && c != this){
                throw new IllegalStateException(
                        "Active contract already exists for this doctor in this clinic!");
            }
        }
        if(this.doctor != null){
            this.doctor.removeContract(this);
        }
        this.doctor = newDoctor;
        newDoctor.addContract(this);
    }

    public void setClinic(Clinic newClinic){
        if(newClinic == null){
            throw new IllegalArgumentException("Clinic must not be null!");
        }
        if(this.clinic == newClinic){
            return;
        }
        for(Contract c : newClinic.getContracts()){
            if(c.getDoctor().equals(this.doctor) && c.isActive() && c != this){
                throw new IllegalStateException(
                        "Active contract already exists for this doctor in this clinic!");
            }
        }
        if(this.clinic != null){
            this.clinic.removeContract(this);
        }
        this.clinic = newClinic;
        newClinic.addContract(this);
    }

    public LocalDate getDateFrom(){
        return dateFrom;
    }
    public void setDateFrom(LocalDate dateFrom){
        if(dateFrom == null){
            throw new IllegalArgumentException("Date from must not be null!");
        }
        if(dateFrom.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Date from cannot be from the future!");
        }
        this.dateFrom = dateFrom;
    }
    public LocalDate getDateTo(){
        return dateTo;
    }
    public void setDateTo(LocalDate dateTo){
        if(dateTo != null && dateFrom != null && dateTo.isBefore(dateFrom)){
            throw new IllegalArgumentException("Date to cannot be before data from!");
        }
        this.dateTo = dateTo;
    }
    public double getAmountOfMoney(){
        return amountOfMoney;
    }
    public void setAmountOfMoney(double amountOfMoney){
        if(amountOfMoney <= 0){
            throw new IllegalArgumentException("Amount of money must be positive!");
        }
        this.amountOfMoney = amountOfMoney;
    }

    public void terminate(){
        if(doctor != null){
            doctor.removeContract(this);
            doctor = null;
        }
        if(clinic != null){
            clinic.removeContract(this);
            clinic = null;
        }
    }

    @Override
    public String toString(){
        if(doctor == null || clinic == null){
            return "Contract terminated!";
        }
        return "Contract: " + doctor.getFirstName() + " " + doctor.getSurname()
                + " <-> " + clinic.getName()
                + " (from " + dateFrom
                + (dateTo != null ? " to " + dateTo : " — still active")
                + ", amount of money: $" + amountOfMoney + ")";
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Contract contract = (Contract) obj;
        return Objects.equals(doctor, contract.doctor)
                && Objects.equals(clinic, contract.clinic)
                && Objects.equals(dateFrom, contract.dateFrom);
    }

    @Override
    public int hashCode(){
        return Objects.hash(doctor, clinic, dateFrom);
    }
}
