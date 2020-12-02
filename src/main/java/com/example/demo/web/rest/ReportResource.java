package com.example.demo.web.rest;


import com.example.demo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
@CrossOrigin("*")
public class ReportResource {
    private final ReportService reportService;

    @Autowired
    public ReportResource(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<?> getReports(@PathVariable Integer roomId) {
        var report = reportService.getReportByRoomId(roomId);
        return ResponseEntity.ok().body(report);
    }
}
