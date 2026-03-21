package hu.telemetry.telemetry_backend.model;

public class LapData {

    private int lapNumber;
    private double lapDuration;
    private double s1;
    private double s2;
    private double s3;
    private double maxSpeed;
    private double i1Speed;
    private double i2Speed;

    public LapData(int lapNumber, double lapDuration, double s1, double s2, double s3,
                   double maxSpeed, double i1Speed, double i2Speed) {
        this.lapNumber = lapNumber;
        this.lapDuration = lapDuration;
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        this.maxSpeed = maxSpeed;
        this.i1Speed = i1Speed;
        this.i2Speed = i2Speed;
    }

    // 🔥 Getterek hozzáadva
    public double getS1() { return s1; }
    public double getS2() { return s2; }
    public double getS3() { return s3; }
    public double getMaxSpeed() { return maxSpeed; }

    public int getLapNumber() { return lapNumber; }
    public double getLapDuration() { return lapDuration; }
    public double getI1Speed() { return i1Speed; }
    public double getI2Speed() { return i2Speed; }
}