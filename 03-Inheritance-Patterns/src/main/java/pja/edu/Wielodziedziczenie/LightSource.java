package pja.edu.Wielodziedziczenie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class LightSource extends SmartDevice {
    private double brightnessLevel; // [0..100]

    private static final List<LightSource> extent = new ArrayList<>();

    public LightSource(String serialNumber, double brightnessLevel) {
        super(serialNumber);
        setBrightnessLevel(brightnessLevel);
        extent.add(this);
    }

    public double getBrightnessLevel() {
        return brightnessLevel;
    }

    public final void setBrightnessLevel(double brightnessLevel) {
        if (Double.isNaN(brightnessLevel) || Double.isInfinite(brightnessLevel)) {
            throw new IllegalArgumentException("brightnessLevel must be finite");
        }
        if (brightnessLevel < 0.0 || brightnessLevel > 100.0) {
            throw new IllegalArgumentException("brightnessLevel must be in [0..100]");
        }
        this.brightnessLevel = brightnessLevel;
    }

    public void addBrightnessLevel() {
        setBrightnessLevel(Math.min(100.0, brightnessLevel + 1.0));
    }

    public void reduceBrightnessLevel() {
        setBrightnessLevel(Math.max(0.0, brightnessLevel - 1.0));
    }

    @Override
    public double getPowerConsumption() {
        return 1.0 + (brightnessLevel / 100.0) * 9.0;
    }

    public static List<LightSource> getLightSourceExtent() {
        return Collections.unmodifiableList(extent);
    }

    public static void showExtent() {
        if (extent.isEmpty()) {
            System.out.println("LightSource extent is empty.");
            return;
        }
        System.out.println("LightSource extent:");
        for (LightSource lightSource : extent) {
            System.out.println(" - " + lightSource);
        }
    }

    @Override
    public String toString() {
        return "LightSource{" +
                "serialNumber='" + getSerialNumber() + '\'' +
                ", brightnessLevel=" + brightnessLevel +
                ", powerConsumption=" + getPowerConsumption() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LightSource that = (LightSource) o;
        return Double.compare(that.brightnessLevel, brightnessLevel) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), brightnessLevel);
    }
}
