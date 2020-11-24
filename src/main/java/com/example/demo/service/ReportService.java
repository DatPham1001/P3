package com.example.demo.service;

import com.example.demo.domain.Device;
import com.example.demo.domain.Report;
import com.example.demo.enums.DeviceType;
import com.example.demo.repo.DeviceRepo;
import com.example.demo.repo.ReportRepo;
import com.example.demo.repo.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Report create(Report report) {
        var reportResult = reportRepo.save(report);
        return reportResult;
    }
    public List<Report> getReportByRoomId(Integer roomId){
        var devices = deviceRepo.findAllByRoomIdAndDeletedFalse(roomId);
        for(Device device : devices){
            if(device.getDeviceType().equals("ADRUINO")){
                var reports = reportRepo.findAllByDeviceId(device.getId());
                return reports;
            }
        }
        return null;
    }
}
