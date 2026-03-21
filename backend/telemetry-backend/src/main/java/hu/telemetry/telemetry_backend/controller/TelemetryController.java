package hu.telemetry.telemetry_backend.controller;

import hu.telemetry.telemetry_backend.model.TelemetryAnalysis;
import hu.telemetry.telemetry_backend.model.TelemetryPoint;
import hu.telemetry.telemetry_backend.service.TelemetryAnalyzerService;
import hu.telemetry.telemetry_backend.service.TelemetryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/telemetry")
public class TelemetryController {

    private final TelemetryService telemetryService;
    private final TelemetryAnalyzerService analyzerService;

    public TelemetryController(TelemetryService telemetryService,
                               TelemetryAnalyzerService analyzerService) {
        this.telemetryService = telemetryService;
        this.analyzerService = analyzerService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<TelemetryAnalysis> analyze(@RequestParam("file") MultipartFile file) {

        List<TelemetryPoint> points = telemetryService.processCsv(file);
        TelemetryAnalysis analysis = analyzerService.analyze(points);

        return ResponseEntity.ok(analysis);
    }
}