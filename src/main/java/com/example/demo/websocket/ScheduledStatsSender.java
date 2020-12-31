package com.example.demo.websocket;

import com.example.demo.repo.ReportRepo;
import com.example.demo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;

public class ScheduledStatsSender {
    private final ReportRepo reportRepo;
    private final ReportService reportService;

    @Autowired
    public ScheduledStatsSender(ReportRepo reportRepo, ReportService reportService) {
        this.reportRepo = reportRepo;
        this.reportService = reportService;
    }
//    @Scheduled(fixedDelay = 2000)
//    @MessageMapping("/user-all")
//    @SendTo("/topic/user")
//    public RoomStats sendToTopic(Integer roomId){
//        var lastReport = reportService.getLastReport(roomId);
//        RoomStats roomStats = new RoomStats();
//        roomStats.setTemp(lastReport.getTemperature());
//        roomStats.setCo(lastReport.getCoConcentration());
//        roomStats.setHumidity(lastReport.getHumidity());
//        return roomStats;
//    }
}
