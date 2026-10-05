package pja.edu.Klasa_abstrakcyjna_polimorfizm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class Animal {
    private String species;
    private double avgLifespan;
    private static final List<Animal> extent = new ArrayList<>();

    public Animal(String species, double avgLifespan){
        setSpecies(species);
        setAvgLifespan(avgLifespan);
        extent.add(this);
    }

    public static List<Animal> getExtent() {
        return Collections.unmodifiableList(extent);
    }

    public static void showExtent() {
        if (extent.isEmpty()) {
            System.out.println("Extent is empty.");
            return;
        }
        for (Animal animal : extent) {
            System.out.println(animal);
        }
    }

    public String getSpecies(){
        return species;
    }
    public double getAvgLifespan(){
        return avgLifespan;
    }
    public void setSpecies(String species) {
        if (species == null || species.trim().isEmpty()) {
            throw new IllegalArgumentException("Species cannot be null or blank");
        }
        species = species.trim();
        if (!species.matches("^[\\p{L} -]+$")) {
            throw new IllegalArgumentException("Species can contain only letters, spaces and hyphen");
        }
        this.species = species;
    }
    public void setAvgLifespan(double avgLifespan) {
        if (Double.isNaN(avgLifespan) || Double.isInfinite(avgLifespan)) {
            throw new IllegalArgumentException("Avg lifespan must be a finite number");
        }
        if (avgLifespan <= 0) {
            throw new IllegalArgumentException("Avg lifespan must be greater than 0");
        }
        this.avgLifespan = avgLifespan;
    }

    public abstract double estimateEnvironmentalImpact();

    @Override
    public String toString() {
        return "%s{species='%s', avgLifespan=%.2f}".formatted(
                getClass().getSimpleName(), species, avgLifespan
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Double.compare(animal.avgLifespan, avgLifespan) == 0
                && Objects.equals(species, animal.species);
    }

    @Override
    public int hashCode() {
        return Objects.hash(species, avgLifespan);
    }
}
