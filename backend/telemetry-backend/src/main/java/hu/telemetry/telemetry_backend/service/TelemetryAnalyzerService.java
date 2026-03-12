package hu.telemetry.telemetry_backend.service;

import hu.telemetry.telemetry_backend.model.TelemetryAnalysis;
import hu.telemetry.telemetry_backend.model.TelemetryPoint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelemetryAnalyzerService {

    public TelemetryAnalysis analyze(List<TelemetryPoint> points) {

        double maxSpeed = 0;
        double speedSum = 0;

        double throttleSum = 0;
        double brakeSum = 0;

        int gearShifts = 0;

        int prevGear = -1;

        for (TelemetryPoint p : points) {

            if (p.getSpeed() > maxSpeed) {
                maxSpeed = p.getSpeed();
            }

            speedSum += p.getSpeed();
            throttleSum += p.getThrottle();
            brakeSum += p.getBrake();

            if (prevGear != -1 && prevGear != p.getGear()) {
                gearShifts++;
            }

            prevGear = p.getGear();
        }

        double avgSpeed = speedSum / points.size();
        double avgThrottle = throttleSum / points.size();
        double avgBrake = brakeSum / points.size();

        return new TelemetryAnalysis(
                maxSpeed,
                avgSpeed,
                avgThrottle,
                avgBrake,
                gearShifts
        );
    }
}