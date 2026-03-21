package hu.telemetry.telemetry_backend.controller;

import hu.telemetry.telemetry_backend.model.LapAnalysis;
import hu.telemetry.telemetry_backend.model.LapData;
import hu.telemetry.telemetry_backend.parser.LapCsvParser;
import hu.telemetry.telemetry_backend.service.LapAnalyzerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/laps")
public class LapController {

    private final LapCsvParser parser;
    private final LapAnalyzerService analyzer;

    public LapController(LapCsvParser parser, LapAnalyzerService analyzer) {
        this.parser = parser;
        this.analyzer = analyzer;
    }

    @PostMapping("/analyze")
    public LapAnalysis analyze(@RequestParam("file") MultipartFile file) throws Exception {

        List<LapData> laps = parser.parse(file);
        return analyzer.analyze(laps);
    }
}