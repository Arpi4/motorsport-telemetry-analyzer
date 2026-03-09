package hu.telemetry.telemetry_backend.controller;

import hu.telemetry.telemetry_backend.model.TelemetryPoint;
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

    public TelemetryController(TelemetryService telemetryService) {
        this.telemetryService = telemetryService;
    }

    @GetMapping("/test")
    public String test() {
        return "Telemetry backend working";
    }

    @PostMapping("/upload")
    public ResponseEntity<List<TelemetryPoint>> uploadCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            List<TelemetryPoint> points = telemetryService.processCsv(file);
            return ResponseEntity.ok(points);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}