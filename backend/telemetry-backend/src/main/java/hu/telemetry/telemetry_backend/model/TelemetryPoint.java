package hu.telemetry.telemetry_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelemetryPoint {

    private double time;
    private double speed;
    private double throttle;
    private double brake;
    private int gear;
    private int rpm;
    private double lat;
    private double lon;
}