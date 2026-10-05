package pja.edu.Klasa_abstrakcyjna_polimorfizm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FarmAnimal extends Animal{
    private double productionYield;
    private LocalDate lastVetCheckup;
    private double methaneEmission;
    private double waterConsumption;
    private static final List<FarmAnimal> farmAnimalExtent = new ArrayList<>();

    public FarmAnimal(String species, double avgLifespan, double productionYield,
                      LocalDate lastVetCheckup, double methaneEmission, double waterConsumption){
        super(species, avgLifespan);
        setProductionYield(productionYield);
        setLastVetCheckup(lastVetCheckup);
        setMethaneEmission(methaneEmission);
        setWaterConsumption(waterConsumption);
        farmAnimalExtent.add(this);
    }

    public static List<FarmAnimal> getFarmAnimalExtent() {
        return Collections.unmodifiableList(farmAnimalExtent);
    }

    public static void showFarmAnimalExtent() {
        if (farmAnimalExtent.isEmpty()) {
            System.out.println("FarmAnimal extent is empty.");
            return;
        }
        for (FarmAnimal farmAnimal : farmAnimalExtent) {
            System.out.println(farmAnimal);
        }
    }

    public double getProductionYield() {
        return productionYield;
    }
    public LocalDate getLastVetCheckup() {
        return lastVetCheckup;
    }
    public double getMethaneEmission() {
        return methaneEmission;
    }
    public double getWaterConsumption() {
        return waterConsumption;
    }

    public void setProductionYield(double productionYield) {
        if (Double.isNaN(productionYield) || Double.isInfinite(productionYield)) {
            throw new IllegalArgumentException("Production yield must be a finite number");
        }
        if (productionYield < 0) {
            throw new IllegalArgumentException("Production yield cannot be negative");
        }
        this.productionYield = productionYield;
    }
    public void setLastVetCheckup(LocalDate lastVetCheckup) {
        if (lastVetCheckup == null) {
            throw new IllegalArgumentException("Last vet checkup cannot be null");
        }
        if (lastVetCheckup.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Last vet checkup cannot be in the future");
        }
        this.lastVetCheckup = lastVetCheckup;
    }
    public void setMethaneEmission(double methaneEmission) {
        if (Double.isNaN(methaneEmission) || Double.isInfinite(methaneEmission)) {
            throw new IllegalArgumentException("Methane emission must be a finite number");
        }
        if (methaneEmission < 0) {
            throw new IllegalArgumentException("Methane emission cannot be negative");
        }
        this.methaneEmission = methaneEmission;
    }
    public void setWaterConsumption(double waterConsumption) {
        if (Double.isNaN(waterConsumption) || Double.isInfinite(waterConsumption)) {
            throw new IllegalArgumentException("Water consumption must be a finite number");
        }
        if (waterConsumption < 0) {
            throw new IllegalArgumentException("Water consumption cannot be negative");
        }
        this.waterConsumption = waterConsumption;
    }

    @Override
    public double estimateEnvironmentalImpact() {
        return methaneEmission + waterConsumption - (productionYield * 0.2);
    }

    public void moveToPasture() {
        setMethaneEmission(getMethaneEmission() * 0.95);
    }

    public void drinkWater() {
        setWaterConsumption(getWaterConsumption() + 12.0);
    }

    public void produce() { // możesz też zmienić nazwę np. na produceMilk()
        setProductionYield(getProductionYield() + 8.0);
    }

    @Override
    public String toString() {
        return ("%s, productionYield=%.2f, lastVetCheckup=%s, methaneEmission=%.2f, " +
                "waterConsumption=%.2f}").formatted(
                super.toString().replace("}", ""),
                productionYield, lastVetCheckup, methaneEmission, waterConsumption
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FarmAnimal that)) return false;
        if (!super.equals(o)) return false;
        return Double.compare(that.productionYield, productionYield) == 0
                && Double.compare(that.methaneEmission, methaneEmission) == 0
                && Double.compare(that.waterConsumption, waterConsumption) == 0
                && Objects.equals(lastVetCheckup, that.lastVetCheckup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), productionYield, lastVetCheckup, methaneEmission,
                waterConsumption);
    }
}

