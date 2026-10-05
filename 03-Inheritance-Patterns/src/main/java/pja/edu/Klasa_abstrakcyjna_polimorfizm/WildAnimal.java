package pja.edu.Klasa_abstrakcyjna_polimorfizm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class WildAnimal extends Animal {
    private double territorySize;
    private int dangerLevel;
    private double biodiversityContribution;
    private static final List<WildAnimal> wildAnimalExtent = new ArrayList<>();

    public WildAnimal(String species, double avgLifespan,
                      double territorySize, int dangerLevel,
                      double biodiversityContribution) {
        super(species, avgLifespan);
        setTerritorySize(territorySize);
        setDangerLevel(dangerLevel);
        setBiodiversityContribution(biodiversityContribution);
        wildAnimalExtent.add(this);
    }

    public static List<WildAnimal> getWildAnimalExtent() {
        return Collections.unmodifiableList(wildAnimalExtent);
    }

    public static void showWildAnimalExtent() {
        if (wildAnimalExtent.isEmpty()) {
            System.out.println("WildAnimal extent is empty.");
            return;
        }
        for (WildAnimal wildAnimal : wildAnimalExtent) {
            System.out.println(wildAnimal);
        }
    }

    public double getTerritorySize() {
        return territorySize;
    }
    public int getDangerLevel() {
        return dangerLevel;
    }
    public double getBiodiversityContribution() {
        return biodiversityContribution;
    }
    public void setTerritorySize(double territorySize) {
        if (Double.isNaN(territorySize) || Double.isInfinite(territorySize)) {
            throw new IllegalArgumentException("Territory size must be a finite number");
        }
        if (territorySize <= 0) {
            throw new IllegalArgumentException("Territory size must be greater than 0");
        }
        this.territorySize = territorySize;
    }
    public void setDangerLevel(int dangerLevel) {
        if (dangerLevel < 1 || dangerLevel > 10) {
            throw new IllegalArgumentException("Danger level must be in range 1..10");
        }
        this.dangerLevel = dangerLevel;
    }
    public void setBiodiversityContribution(double biodiversityContribution) {
        if (Double.isNaN(biodiversityContribution) || Double.isInfinite(biodiversityContribution)) {
            throw new IllegalArgumentException("Biodiversity contribution must be a finite number");
        }
        if (biodiversityContribution < 0) {
            throw new IllegalArgumentException("Biodiversity contribution cannot be negative");
        }
        this.biodiversityContribution = biodiversityContribution;
    }

    @Override
    public double estimateEnvironmentalImpact() {
        return (territorySize * 0.05) + dangerLevel - biodiversityContribution;
    }

    public void hideFromPredator() {
        setTerritorySize(getTerritorySize() * 0.98);
    }

    public void spreadSeeds() {
        setBiodiversityContribution(getBiodiversityContribution() + 1.5);
    }

    @Override
    public String toString() {
        return "%s, territorySize=%.2f, dangerLevel=%d, biodiversityContribution=%.2f}".formatted(
                super.toString().replace("}", ""),
                territorySize, dangerLevel, biodiversityContribution
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WildAnimal that)) return false;
        if (!super.equals(o)) return false;
        return Double.compare(that.territorySize, territorySize) == 0
                && dangerLevel == that.dangerLevel
                && Double.compare(that.biodiversityContribution, biodiversityContribution) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), territorySize, dangerLevel, biodiversityContribution);
    }
}
