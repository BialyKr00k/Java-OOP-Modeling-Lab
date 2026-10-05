package pja.edu.Wielodziedziczenie;

import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        Speaker speaker1 = new Speaker("SPK-001", 30);
        LightSource light1 = new LightSource("LGT-001", 50);

        SmartAlarm alarm1 = new SmartAlarm(
                "ALR-001",
                70,
                LocalTime.of(7, 30),
                speaker1
        );

        System.out.println("=== Stan początkowy ===");
        System.out.println(speaker1);
        System.out.println(light1);
        System.out.println(alarm1);

        alarm1.addVolumeLevel();
        alarm1.reduceVolumeLevel();
        alarm1.addBrightnessLevel();
        alarm1.addAlarmTime();

        System.out.println("\n=== Po operacjach na alarmie ===");
        System.out.println("Speaker po delegacji: " + speaker1);
        System.out.println("Alarm: " + alarm1);

        System.out.println("\n=== Ekstensje ===");
        SmartDevice.showExtent();
        LightSource.showExtent();
        Speaker.showExtent();
        SmartAlarm.showExtent();

        System.out.println("\n=== Rozmiary ekstensji ===");
        System.out.println("SmartDevice: " + SmartDevice.getSmartDeviceExtent().size());
        System.out.println("LightSource: " + LightSource.getLightSourceExtent().size());
        System.out.println("Speaker: " + Speaker.getSpeakerExtent().size());
        System.out.println("SmartAlarm: " + SmartAlarm.getSmartAlarmExtent().size());
    }
}
