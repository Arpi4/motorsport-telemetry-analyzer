package hu.telemetry.telemetry_backend.parser;

import hu.telemetry.telemetry_backend.model.LapData;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@Component
public class LapCsvParser {

    public List<LapData> parse(MultipartFile file) throws Exception {
        List<LapData> laps = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            String headerLine = br.readLine();
            String[] headers = headerLine.split("[,;]");
            Map<String, Integer> columnIndex = new HashMap<>();

            for (int i = 0; i < headers.length; i++) {
                columnIndex.put(clean(headers[i]), i);
            }

            // 🔥 DEBUG: oszlopok
            System.out.println("Columns: " + columnIndex);

            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("[,;]");

                try {
                    int lapNumber = getInt(tokens, columnIndex, "lap_number");
                    double lapDuration = getDouble(tokens, columnIndex, "lap_duration");

                    double s1 = getDouble(tokens, columnIndex, "duration_sector_1");
                    double s2 = getDouble(tokens, columnIndex, "duration_sector_2");
                    double s3 = getDouble(tokens, columnIndex, "duration_sector_3");

                    double maxSpeed = getDouble(tokens, columnIndex, "st_speed");
                    double i1Speed = getDouble(tokens, columnIndex, "i1_speed");
                    double i2Speed = getDouble(tokens, columnIndex, "i2_speed");

                    // 🔥 DEBUG: tokenok ellenőrzése
                    System.out.println("RAW st_speed token: [" + tokens[columnIndex.get("st_speed")] + "]");
                    System.out.println("Parsed maxSpeed: " + maxSpeed);

                    LapData lap = new LapData(
                            lapNumber,
                            lapDuration,
                            s1,
                            s2,
                            s3,
                            maxSpeed,
                            i1Speed,
                            i2Speed
                    );

                    laps.add(lap);

                } catch (Exception e) {
                    System.out.println("Skipping row due to parsing error: " + line);
                }
            }
        }

        // 🔥 DEBUG: minden lap értékek ellenőrzése
        System.out.println("DEBUG:");
        System.out.println("S1: " + getSectorList(laps, "s1"));
        System.out.println("S2: " + getSectorList(laps, "s2"));
        System.out.println("S3: " + getSectorList(laps, "s3"));
        System.out.println("MaxSpeed: " + getSectorList(laps, "maxSpeed"));

        return laps;
    }

    private List<Double> getSectorList(List<LapData> laps, String field) {
        List<Double> list = new ArrayList<>();
        for (LapData lap : laps) {
            switch (field) {
                case "s1": list.add(lap.getS1()); break;
                case "s2": list.add(lap.getS2()); break;
                case "s3": list.add(lap.getS3()); break;
                case "maxSpeed": list.add(lap.getMaxSpeed()); break;
            }
        }
        return list;
    }

    private String clean(String header) {
        return header.replace("\uFEFF", "").trim().toLowerCase();
    }

    private double getDouble(String[] tokens, Map<String, Integer> map, String column) {
        Integer index = map.get(column.toLowerCase());
        if (index == null || index >= tokens.length) return 0;

        try {
            String value = tokens[index].replace("\"", "")           // idézőjelek eltávolítása
                    .replaceAll("[^0-9\\.\\-]", "") // minden nem szám karakter eltávolítása
                    .trim();
            if (value.isEmpty()) return 0;
            return Double.parseDouble(value);
        } catch (Exception e) {
            System.out.println("Failed parsing double for column " + column + ": " + tokens[index]);
            return 0;
        }
    }

    private int getInt(String[] tokens, Map<String, Integer> map, String column) {
        Integer index = map.get(column.toLowerCase());
        if (index == null || index >= tokens.length) return 0;
        try {
            return Integer.parseInt(tokens[index].replaceAll("[^0-9]", "").trim());
        } catch (Exception e) {
            System.out.println("Failed parsing int for column " + column + ": " + tokens[index]);
            return 0;
        }
    }
}