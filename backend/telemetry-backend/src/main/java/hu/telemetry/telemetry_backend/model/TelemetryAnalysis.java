package hu.telemetry.telemetry_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class TelemetryAnalysis {

    private double maxSpeed;
    private double avgSpeed;
    private double avgThrottle;
    private double avgBrake;
    private int gearShifts;
    private int brakingZones;

    private List<Double> time;
    private List<Double> speed;
    private List<Double> throttle;
    private List<Double> brake;
    private List<Double> acceleration;
}