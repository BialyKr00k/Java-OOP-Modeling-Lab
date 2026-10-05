package pja.edu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Specialization {
    private String name;
    private List<Doctor> doctors = new ArrayList<>();
    private static List<Specialization> extend = new ArrayList<>();

    public Specialization(String name){
        setName(name);
        extend.add(this);
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Specialization name must not be null or empty!");
        }
        name = name.trim();
        this.name = name;
    }

    public void addDoctor(Doctor doctor){
        if(doctor == null){
            throw new IllegalArgumentException("Doctor must not be null!");
        }
        if(!doctors.contains(doctor)){
            doctors.add(doctor);
            doctor.addSpecialization(this);
        }
    }

    public void removeDoctor(Doctor doctor){
        if(doctor == null){
            throw new IllegalArgumentException("Doctor must not be null!");
        }
        if(doctors.contains(doctor)){
            doctors.remove(doctor);
            doctor.removeSpecialization(this);
        }
    }

    public List<Doctor> getDoctors(){
        return Collections.unmodifiableList(doctors);
    }

    public static List<Specialization> getExtend(){
        return Collections.unmodifiableList(extend);
    }

    public static int getNumberOfSpecializations(){
        return extend.size();
    }

    @Override
    public String toString(){
        return "Specialization: " + name + " (doctors: " + doctors.size() + ")";
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Specialization s = (Specialization) obj;
        return Objects.equals(name, s.name);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name);
    }
}
