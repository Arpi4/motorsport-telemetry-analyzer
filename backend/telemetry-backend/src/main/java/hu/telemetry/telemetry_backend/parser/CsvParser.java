package hu.telemetry.telemetry_backend.parser;

import hu.telemetry.telemetry_backend.model.TelemetryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvParser {

    public List<TelemetryPoint> parse(MultipartFile file) throws Exception {
        List<TelemetryPoint> points = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            br.readLine(); // fejléc átugrása
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                try {
                    double time = tryParseDouble(tokens[0]);
                    double speed = tryParseDouble(tokens[1]);
                    double throttle = tryParseDouble(tokens[2]);
                    double brake = tryParseDouble(tokens[3]);
                    int gear = tryParseInt(tokens[4]);
                    int rpm = tryParseInt(tokens[5]);
                    double lat = tryParseDouble(tokens[6]);
                    double lon = tryParseDouble(tokens[7]);

                    TelemetryPoint point = new TelemetryPoint(time, speed, throttle, brake, gear, rpm, lat, lon);
                    points.add(point);
                } catch (NumberFormatException ex) {
                    System.out.println("Skipping line due to parse error: " + line);
                }
            }
        }
        return points;
    }

    private double tryParseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return 0.0; // vagy más default érték
        }
    }

    private int tryParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}