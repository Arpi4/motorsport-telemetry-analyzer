package hu.telemetry.telemetry_backend.service;

import hu.telemetry.telemetry_backend.model.TelemetryPoint;
import hu.telemetry.telemetry_backend.parser.CsvParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class TelemetryService {

    private final CsvParser parser;

    public TelemetryService(CsvParser parser) {
        this.parser = parser;
    }

    public List<TelemetryPoint> processCsv(MultipartFile file) {
        try {
            return parser.parse(file);
        } catch (Exception e) {
            throw new RuntimeException("CSV parsing failed");
        }
    }
}