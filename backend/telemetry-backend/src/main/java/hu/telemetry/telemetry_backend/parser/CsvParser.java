package hu.telemetry.telemetry_backend.parser;

import hu.telemetry.telemetry_backend.model.TelemetryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Component
public class CsvParser {

    public List<TelemetryPoint> parse(MultipartFile file) throws Exception {

        List<TelemetryPoint> points = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            String headerLine = br.readLine();
            String[] headers = headerLine.split(",");

            Map<String, Integer> columnIndex = new HashMap<>();

            for (int i = 0; i < headers.length; i++) {
                columnIndex.put(headers[i].trim(), i);
            }

            String line;

            Instant startTime = null;

            while ((line = br.readLine()) != null) {

                String[] tokens = line.split(",");

                try {

                    double speed = getDouble(tokens, columnIndex, "speed");

                    // 0-255 -> %
                    double throttle = getDouble(tokens, columnIndex, "throttle") / 255.0 * 100.0;
                    double brake = getDouble(tokens, columnIndex, "brake") / 255.0 * 100.0;

                    int gear = getInt(tokens, columnIndex, "n_gear");
                    int rpm = getInt(tokens, columnIndex, "rpm");

                    String dateStr = tokens[columnIndex.get("date")].replace(" ", "T");

                    Instant currentTime = Instant.parse(dateStr);

                    if (startTime == null) {
                        startTime = currentTime;
                    }

                    double time = Duration.between(startTime, currentTime).toMillis() / 1000.0;

                    TelemetryPoint point = new TelemetryPoint(
                            time,
                            speed,
                            throttle,
                            brake,
                            gear,
                            rpm,
                            0,
                            0
                    );

                    points.add(point);

                } catch (Exception e) {
                    System.out.println("Skipping line: " + line);
                }
            }
        }

        return points;
    }

    private double getDouble(String[] tokens, Map<String, Integer> map, String column) {

        Integer index = map.get(column);

        if (index == null || index >= tokens.length) return 0;

        try {
            return Double.parseDouble(tokens[index]);
        } catch (Exception e) {
            return 0;
        }
    }

    private int getInt(String[] tokens, Map<String, Integer> map, String column) {

        Integer index = map.get(column);

        if (index == null || index >= tokens.length) return 0;

        try {
            return Integer.parseInt(tokens[index]);
        } catch (Exception e) {
            return 0;
        }
    }
}