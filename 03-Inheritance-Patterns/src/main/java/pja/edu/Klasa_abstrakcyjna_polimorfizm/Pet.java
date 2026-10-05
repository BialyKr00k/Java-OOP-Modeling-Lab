package pja.edu.Klasa_abstrakcyjna_polimorfizm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Pet extends Animal{
    private String ownerName;
    private String petName;
    private double levelOfHappiness;
    private double wasteProducedDaily;
    private static final List<Pet> petExtent = new ArrayList<>();

    public Pet(String species, double avgLifespan, String ownerName, String petName,
               double levelOfHappiness, double wasteProducedDaily){
        super(species, avgLifespan);
        setOwnerName(ownerName);
        setPetName(petName);
        setLevelOfHappiness(levelOfHappiness);
        setWasteProducedDaily(wasteProducedDaily);
        petExtent.add(this);
    }

    public static List<Pet> getPetExtent() {
        return Collections.unmodifiableList(petExtent);
    }

    public static void showPetExtent() {
        if (petExtent.isEmpty()) {
            System.out.println("Pet extent is empty.");
            return;
        }
        for (Pet pet : petExtent) {
            System.out.println(pet);
        }
    }

    public String getOwnerName(){
        return ownerName;
    }
    public String getPetName(){
        return petName;
    }
    public double getLevelOfHappiness(){
        return levelOfHappiness;
    }
    public double getWasteProducedDaily(){
        return wasteProducedDaily;
    }
    public void setOwnerName(String ownerName){
        if (ownerName == null || ownerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner name cannot be null or blank");
        }
        ownerName = ownerName.trim();
        if (!ownerName.matches("^[\\p{L}][\\p{L} '\\-]*$")) {
            throw new IllegalArgumentException("Owner name contains invalid characters");
        }
        this.ownerName = ownerName;
    }
    public void setPetName(String petName){
        if (petName == null || petName.trim().isEmpty()) {
            throw new IllegalArgumentException("Pet name cannot be null or blank");
        }
        petName = petName.trim();
        if (!petName.matches("^[\\p{L}][\\p{L} '\\-]*$")) {
            throw new IllegalArgumentException("Pet name contains invalid characters");
        }
        this.petName = petName;
    }
    public void setLevelOfHappiness(double levelOfHappiness){
        if (Double.isNaN(levelOfHappiness) || Double.isInfinite(levelOfHappiness)) {
            throw new IllegalArgumentException("Level of happiness must be a finite number");
        }
        if (levelOfHappiness < 0 || levelOfHappiness > 100) {
            throw new IllegalArgumentException("Level of happiness must be in range 0..100");
        }
        this.levelOfHappiness = levelOfHappiness;
    }
    public void setWasteProducedDaily(double wasteProducedDaily){
        if (Double.isNaN(wasteProducedDaily) || Double.isInfinite(wasteProducedDaily)) {
            throw new IllegalArgumentException("Waste produced daily must be a finite number");
        }
        if (wasteProducedDaily < 0) {
            throw new IllegalArgumentException("Waste produced daily cannot be negative");
        }
        this.wasteProducedDaily = wasteProducedDaily;
    }

    @Override
    public double estimateEnvironmentalImpact() {
        return wasteProducedDaily - (levelOfHappiness * 0.1);
    }

    public void playWithHuman() {
        setLevelOfHappiness(Math.min(100.0, getLevelOfHappiness() + 5.0));
    }

    public void eatTreats() {
        setWasteProducedDaily(getWasteProducedDaily() + 0.30);
    }

    @Override
    public String toString() {
        return ("%s, ownerName='%s', petName='%s', levelOfHappiness=%.2f, " +
                "wasteProducedDaily=%.2f}").formatted(
                super.toString().replace("}", ""),
                ownerName, petName, levelOfHappiness, wasteProducedDaily
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pet pet)) return false;
        if (!super.equals(o)) return false;
        return Double.compare(pet.levelOfHappiness, levelOfHappiness) == 0
                && Double.compare(pet.wasteProducedDaily, wasteProducedDaily) == 0
                && Objects.equals(ownerName, pet.ownerName)
                && Objects.equals(petName, pet.petName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ownerName, petName, levelOfHappiness, wasteProducedDaily);
    }
}
