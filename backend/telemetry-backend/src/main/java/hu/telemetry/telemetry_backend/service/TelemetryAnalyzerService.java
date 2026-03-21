package hu.telemetry.telemetry_backend.service;

import hu.telemetry.telemetry_backend.model.TelemetryAnalysis;
import hu.telemetry.telemetry_backend.model.TelemetryPoint;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TelemetryAnalyzerService {

    public TelemetryAnalysis analyze(List<TelemetryPoint> points) {

        double maxSpeed = 0;
        double sumSpeed = 0;
        double sumThrottle = 0;
        double sumBrake = 0;
        int gearShifts = 0;

        List<Double> time = new ArrayList<>();
        List<Double> speed = new ArrayList<>();
        List<Double> throttle = new ArrayList<>();
        List<Double> brake = new ArrayList<>();
        List<Double> acceleration = new ArrayList<>();

        int brakingZones = 0;
        boolean wasBraking = false;

        for (int i = 0; i < points.size(); i++) {

            TelemetryPoint p = points.get(i);

            time.add(p.getTime());
            speed.add(p.getSpeed());
            throttle.add(p.getThrottle());
            brake.add(p.getBrake());

            maxSpeed = Math.max(maxSpeed, p.getSpeed());
            sumSpeed += p.getSpeed();
            sumThrottle += p.getThrottle();
            sumBrake += p.getBrake();

            // Gear shift
            if (i > 0 && p.getGear() != points.get(i - 1).getGear()) {
                gearShifts++;
            }

            // Acceleration
            if (i > 0) {
                double dv = p.getSpeed() - points.get(i - 1).getSpeed();
                double dt = p.getTime() - points.get(i - 1).getTime();

                acceleration.add(dt > 0 ? dv / dt : 0.0);
            } else {
                acceleration.add(0.0);
            }

            // Braking zones
            if (p.getBrake() > 20) {
                if (!wasBraking) {
                    brakingZones++;
                    wasBraking = true;
                }
            } else {
                wasBraking = false;
            }
        }

        double avgSpeed = sumSpeed / points.size();
        double avgThrottle = sumThrottle / points.size();
        double avgBrake = sumBrake / points.size();

        return new TelemetryAnalysis(
                maxSpeed,
                avgSpeed,
                avgThrottle,
                avgBrake,
                gearShifts,
                brakingZones,
                time,
                speed,
                throttle,
                brake,
                acceleration
        );
    }
}