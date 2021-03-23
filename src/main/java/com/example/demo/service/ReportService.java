package com.example.demo.service;

import com.example.demo.domain.Report;
import com.example.demo.enums.DeviceType;
import com.example.demo.repo.DeviceRepo;
import com.example.demo.repo.ReportRepo;
import com.example.demo.repo.RoomRepo;
import com.example.demo.web.vm.ReportIM;
import com.example.demo.web.vm.ReportOM;
import com.example.demo.web.vm.RoomStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ReportService {
    private final ReportRepo reportRepo;
    private final RoomRepo roomRepo;
    private final DeviceRepo deviceRepo;

    @Autowired
    public ReportService(ReportRepo reportRepo, RoomRepo roomRepo, DeviceRepo deviceRepo) {
        this.reportRepo = reportRepo;
        this.roomRepo = roomRepo;
        this.deviceRepo = deviceRepo;
    }

    public Report create(ReportIM reportIM) {
        Report report = new Report();
        report.setDeviceCode("ARDUINO1");
        report.setTemperature(reportIM.getTemp());
        report.setHumidity(reportIM.getHum());
        report.setCoConcentration(reportIM.getCo());
        var reportResult = reportRepo.save(report);
        return reportResult;
    }

    public List<Report> getReportByRoomId(Integer roomId) {
        var device = deviceRepo.findDeviceByRoomIdAndDeletedFalse(roomId);
        if (device.getDeviceType().equals("ADRUINO")) {
            var reports = reportRepo.findAllByDeviceCode(device.getCode());
            return reports;
        }
        return null;
    }

    public List<Report> getReportOfDate(String date) {
       return null;
    }
    public Report getLastReport(Integer roomId){
        var device = deviceRepo.findDeviceByDeviceTypeAndRoomIdAndDeletedFalse(String.valueOf(DeviceType.ADRUINO), roomId);
        var lastReport = reportRepo.findTopByDeviceCode(device.getCode());
        return lastReport;
    }
    public List<ReportOM> getReportForDiagramInMinutes(Integer roomId){
        var device = deviceRepo.findDeviceByRoomIdAndDeletedFalse(roomId);
        var reports = reportRepo.getReportForDiagramInMinutes("2020-11-30",device.getCode());
        return reports;
    }

}
