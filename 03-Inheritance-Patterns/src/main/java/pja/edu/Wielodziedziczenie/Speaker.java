package pja.edu.Wielodziedziczenie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Speaker extends SmartDevice implements ISpeaker {
    private double volumeLevel; // [0..100]

    private static final List<Speaker> speakerExtent = new ArrayList<>();

    public Speaker(String serialNumber, double volumeLevel) {
        super(serialNumber);
        setVolumeLevel(volumeLevel);
        speakerExtent.add(this);
    }

    public double getVolumeLevel() {
        return volumeLevel;
    }

    public final void setVolumeLevel(double volumeLevel) {
        if (Double.isNaN(volumeLevel) || Double.isInfinite(volumeLevel)) {
            throw new IllegalArgumentException("volumeLevel must be finite");
        }
        if (volumeLevel < 0.0 || volumeLevel > 100.0) {
            throw new IllegalArgumentException("volumeLevel must be in [0..100]");
        }
        this.volumeLevel = volumeLevel;
    }

    @Override
    public void addVolumeLevel() {
        setVolumeLevel(Math.min(100.0, volumeLevel + 1.0));
    }

    @Override
    public void reduceVolumeLevel() {
        setVolumeLevel(Math.max(0.0, volumeLevel - 1.0));
    }

    @Override
    public double getPowerConsumption() {
        return 0.5 + (volumeLevel / 100.0) * 4.5;
    }

    public static List<Speaker> getSpeakerExtent() {
        return Collections.unmodifiableList(speakerExtent);
    }

    public static void showExtent() {
        if (speakerExtent.isEmpty()) {
            System.out.println("Speaker extent is empty.");
            return;
        }
        System.out.println("Speaker extent:");
        for (Speaker speaker : speakerExtent) {
            System.out.println(" - " + speaker);
        }
    }

    @Override
    public String toString() {
        return "Speaker{" +
                "serialNumber='" + getSerialNumber() + '\'' +
                ", volumeLevel=" + volumeLevel +
                ", powerConsumption=" + getPowerConsumption() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Speaker speaker = (Speaker) o;
        return Double.compare(speaker.volumeLevel, volumeLevel) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), volumeLevel);
    }
}