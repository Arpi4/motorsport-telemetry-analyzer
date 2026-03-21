package hu.telemetry.telemetry_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LapAnalysis {

    private double fastestLap;
    private double averageLap;

    private double bestSector1;
    private double bestSector2;
    private double bestSector3;

    private List<Integer> lapNumbers;
    private List<Double> lapTimes;

    private List<Double> sector1;
    private List<Double> sector2;
    private List<Double> sector3;

    private List<Double> maxSpeeds;
    private List<Double> i1Speeds;
    private List<Double> i2Speeds;
}