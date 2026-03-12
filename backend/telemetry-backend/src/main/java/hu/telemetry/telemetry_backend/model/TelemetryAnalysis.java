package hu.telemetry.telemetry_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TelemetryAnalysis {

    private double maxSpeed;
    private double avgSpeed;
    private double avgThrottle;
    private double avgBrake;
    private int gearShifts;
}