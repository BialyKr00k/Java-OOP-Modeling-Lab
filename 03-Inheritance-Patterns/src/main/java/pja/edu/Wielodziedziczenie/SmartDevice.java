package pja.edu.Wielodziedziczenie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class SmartDevice {
    private String serialNumber;

    private static final List<SmartDevice> smartDeviceExtent = new ArrayList<>();

    protected SmartDevice(String serialNumber) {
        setSerialNumber(serialNumber);
        smartDeviceExtent.add(this);
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public final void setSerialNumber(String serialNumber) {
        if (serialNumber == null || serialNumber.isBlank()) {
            throw new IllegalArgumentException("serialNumber cannot be null or blank");
        }
        this.serialNumber = serialNumber.trim();
    }

    public abstract double getPowerConsumption();

    public static List<SmartDevice> getSmartDeviceExtent() {
        return Collections.unmodifiableList(smartDeviceExtent);
    }

    public static void showExtent() {
        if (smartDeviceExtent.isEmpty()) {
            System.out.println("SmartDevice extent is empty.");
            return;
        }
        System.out.println("SmartDevice extent:");
        for (SmartDevice device : smartDeviceExtent) {
            System.out.println(" - " + device);
        }
    }

    @Override
    public String toString() {
        return "SmartDevice{serialNumber='" + serialNumber + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartDevice that = (SmartDevice) o;
        return Objects.equals(serialNumber, that.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber);
    }
}
