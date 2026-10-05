package pja.edu.Wielodziedziczenie;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SmartAlarm extends LightSource implements ISpeaker {
    private LocalTime alarmTime;
    private ISpeaker speakerDelegate; // delegacja do interfejsu

    private static final List<SmartAlarm> smartAlarmExtent = new ArrayList<>();

    public SmartAlarm(String serialNumber,
                      double brightnessLevel,
                      LocalTime alarmTime,
                      ISpeaker speakerDelegate) {
        super(serialNumber, brightnessLevel);
        setAlarmTime(alarmTime);
        setSpeakerDelegate(speakerDelegate);
        smartAlarmExtent.add(this);
    }

    public LocalTime getAlarmTime() {
        return alarmTime;
    }

    public final void setAlarmTime(LocalTime alarmTime) {
        if (alarmTime == null) {
            throw new IllegalArgumentException("alarmTime cannot be null");
        }
        this.alarmTime = alarmTime;
    }

    public ISpeaker getSpeakerDelegate() {
        return speakerDelegate;
    }

    public final void setSpeakerDelegate(ISpeaker speakerDelegate) {
        if (speakerDelegate == null) {
            throw new IllegalArgumentException("speakerDelegate cannot be null");
        }
        this.speakerDelegate = speakerDelegate;
    }

    public void addAlarmTime() {
        setAlarmTime(alarmTime.plusMinutes(1));
    }

    @Override
    public void addVolumeLevel() {
        speakerDelegate.addVolumeLevel();
    }

    @Override
    public void reduceVolumeLevel() {
        speakerDelegate.reduceVolumeLevel();
    }

    @Override
    public double getPowerConsumption() {
        return super.getPowerConsumption() + speakerDelegate.getPowerConsumption();
    }

    public static List<SmartAlarm> getSmartAlarmExtent() {
        return Collections.unmodifiableList(smartAlarmExtent);
    }

    public static void showExtent() {
        if (smartAlarmExtent.isEmpty()) {
            System.out.println("SmartAlarm extent is empty.");
            return;
        }
        System.out.println("SmartAlarm extent:");
        for (SmartAlarm smartAlarm : smartAlarmExtent) {
            System.out.println(" - " + smartAlarm);
        }
    }

    @Override
    public String toString() {
        return "SmartAlarm{" +
                "serialNumber='" + getSerialNumber() + '\'' +
                ", brightnessLevel=" + getBrightnessLevel() +
                ", alarmTime=" + alarmTime +
                ", speakerDelegate=" + speakerDelegate +
                ", powerConsumption=" + getPowerConsumption() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SmartAlarm that = (SmartAlarm) o;
        return Objects.equals(alarmTime, that.alarmTime) &&
                Objects.equals(speakerDelegate, that.speakerDelegate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), alarmTime, speakerDelegate);
    }
}
