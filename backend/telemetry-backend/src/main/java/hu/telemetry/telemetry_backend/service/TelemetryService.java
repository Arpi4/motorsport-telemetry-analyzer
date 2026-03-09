package hu.telemetry.telemetry_backend.service;

import hu.telemetry.telemetry_backend.model.TelemetryPoint;
import hu.telemetry.telemetry_backend.parser.CsvParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class TelemetryService {

    private final CsvParser csvParser;

    public TelemetryService(CsvParser csvParser) {
        this.csvParser = csvParser;
    }

    public List<TelemetryPoint> processCsv(MultipartFile file) {
        try {
            return csvParser.parse(file);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}