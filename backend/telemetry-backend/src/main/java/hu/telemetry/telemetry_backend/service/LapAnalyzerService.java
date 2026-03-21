package hu.telemetry.telemetry_backend.service;

import hu.telemetry.telemetry_backend.model.LapAnalysis;
import hu.telemetry.telemetry_backend.model.LapData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LapAnalyzerService {

    public LapAnalysis analyze(List<LapData> laps) {

        double fastestLap = Double.MAX_VALUE;
        double sum = 0;

        double bestS1 = Double.MAX_VALUE;
        double bestS2 = Double.MAX_VALUE;
        double bestS3 = Double.MAX_VALUE;

        List<Integer> lapNumbers = new ArrayList<>();
        List<Double> lapTimes = new ArrayList<>();

        List<Double> s1List = new ArrayList<>();
        List<Double> s2List = new ArrayList<>();
        List<Double> s3List = new ArrayList<>();

        List<Double> maxSpeeds = new ArrayList<>();
        List<Double> i1Speeds = new ArrayList<>();
        List<Double> i2Speeds = new ArrayList<>();

        for (LapData lap : laps) {

            double time = lap.getLapDuration();

            fastestLap = Math.min(fastestLap, time);
            sum += time;

            bestS1 = Math.min(bestS1, lap.getS1());
            bestS2 = Math.min(bestS2, lap.getS2());
            bestS3 = Math.min(bestS3, lap.getS3());

            lapNumbers.add(lap.getLapNumber());
            lapTimes.add(time);

            s1List.add(lap.getS1());
            s2List.add(lap.getS2());
            s3List.add(lap.getS3());

            maxSpeeds.add(lap.getMaxSpeed());
            i1Speeds.add(lap.getI1Speed());
            i2Speeds.add(lap.getI2Speed());
        }
        System.out.println("DEBUG:");
        System.out.println("S1: " + s1List);
        System.out.println("MaxSpeed: " + maxSpeeds);

        double avg = sum / laps.size();

        return new LapAnalysis(
                fastestLap,
                avg,
                bestS1,
                bestS2,
                bestS3,
                lapNumbers,
                lapTimes,
                s1List,
                s2List,
                s3List,
                maxSpeeds,
                i1Speeds,
                i2Speeds
        );
    }
}