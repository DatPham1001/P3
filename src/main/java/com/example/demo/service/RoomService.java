package com.example.demo.service;


import com.example.demo.domain.Room;
import com.example.demo.enums.DeviceType;
import com.example.demo.repo.DeviceRepo;
import com.example.demo.repo.ReportRepo;
import com.example.demo.repo.RoomRepo;
import com.example.demo.web.vm.ReportOM;
import com.example.demo.web.vm.RoomDetailResult;
import com.example.demo.web.vm.RoomRequest;
import com.example.demo.web.vm.RoomStatsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {
    private final RoomRepo roomRepo;
    private final DeviceRepo deviceRepo;
    private final ReportRepo reportRepo;

    @Autowired
    public RoomService(RoomRepo roomRepo, DeviceRepo deviceRepo, ReportRepo reportRepo) {
        this.roomRepo = roomRepo;
        this.deviceRepo = deviceRepo;
        this.reportRepo = reportRepo;
    }

    public Room create(RoomRequest roomRequest) {

        Room room = Room.RoomBuilder
                .aRoom()
                .withName(roomRequest.getName())
                .withDescription(roomRequest.getDescription())
                .withRoomType(roomRequest.getRoomType())
                .withWidth(roomRequest.getWidth())
                .withHeight(roomRequest.getHeight())
                .withLength(roomRequest.getLength())
                .withUrl(roomUrlGenerator(roomRequest))
                .build();
        var roomResult = roomRepo.save(room);
        return roomResult;
    }

    public List<RoomStatsResult> getRooms(Pageable pageable) {
        Page<Room> rooms = roomRepo.findAllByAndIsDeletedFalse(pageable);
        List<RoomStatsResult> roomStatsResults = new ArrayList<>();
        for (Room room : rooms) {
            RoomStatsResult roomStatsResult = new RoomStatsResult();
            roomStatsResult.setId(room.getId());
            roomStatsResult.setDescription(room.getDescription());
            roomStatsResult.setHeight(room.getHeight());
            roomStatsResult.setLength(room.getLength());
            roomStatsResult.setUrl(room.getUrl());
            roomStatsResult.setName(room.getName());
            roomStatsResult.setWidth(room.getWidth());
            roomStatsResult.setRoomType(room.getRoomType());
            roomStatsResult.setRoomStats(buildRoomStats(room.getId()));
            roomStatsResults.add(roomStatsResult);
        }
        return roomStatsResults;
    }

    private RoomStatsResult.RoomStats buildRoomStats(Integer roomId) {
        var stats = new RoomStatsResult.RoomStats();
        var device = deviceRepo.findDeviceByDeviceTypeAndRoomIdAndDeletedFalse(String.valueOf(DeviceType.ADRUINO), roomId);
        if (device != null) {
            var report = reportRepo.findTopByDeviceId(device.getId());
            if (report != null) {
                stats.setTemp(report.getTemperature());
                stats.setHumidity(report.getHumidity());
                stats.setCo(report.getCoConcentration());
            } else {
                stats.setTemp(0);
                stats.setHumidity(0);
                stats.setCo(0);
            }
        }
        return stats;
    }

    public RoomDetailResult getById(Integer id) {
        var device = deviceRepo.findDeviceByRoomIdAndDeletedFalse(id);
        LocalDateTime datetime = LocalDateTime.now();
        String time = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(datetime);
        var reports = reportRepo.getReportOfDate(time);
        var room = roomRepo.findById(id).get();
        var result = new RoomDetailResult();
        result.setName(room.getName());
        if (!room.getDescription().isBlank()) {
            result.setDescription(room.getDescription());
        } else result.setDescription("Không có mô tả");
        result.setWidth(room.getWidth());
        result.setHeight(room.getHeight());
        result.setLength(room.getLength());
        result.setUrl(room.getUrl());
        result.setRoomType(room.getRoomType());
        result.setRoomStats(buildRoomDetailStats(room.getId()));
        result.setDevice(device);
        List<RoomDetailResult.RoomStats> stats = new ArrayList<>();
        for (ReportOM report : reports) {
            RoomDetailResult.RoomStats stat = new RoomDetailResult.RoomStats();
            stat.setTemp(report.getTemperature());
            stat.setHour(report.getHour());
            stat.setHumidity(report.getHumidity());
            stat.setCo(report.getCo());
            stats.add(stat);
        }
        result.setRoomStatsList(stats);
        return result;
    }

    private RoomDetailResult.RoomStats buildRoomDetailStats(Integer roomId) {
        var stats = new RoomDetailResult.RoomStats();
        var device = deviceRepo.findDeviceByDeviceTypeAndRoomIdAndDeletedFalse(String.valueOf(DeviceType.ADRUINO), roomId);
        if (device != null) {
            var report = reportRepo.findTopByDeviceId(device.getId());
            if (report != null) {
                stats.setTemp(report.getTemperature());
                stats.setHumidity(report.getHumidity());
                stats.setCo(report.getCoConcentration());
            } else {
                stats.setTemp(0);
                stats.setHumidity(0);
                stats.setCo(0);
            }
        }
        return stats;
    }

    public Room update(Integer id, RoomRequest roomRequest) {
        var existed = roomRepo.findById(id).get();
        existed.setName(roomRequest.getName());
        existed.setRoomType(roomRequest.getRoomType());
        existed.setHeight(roomRequest.getHeight());
        existed.setWidth(roomRequest.getWidth());
        existed.setLength(roomRequest.getLength());
        existed.setDescription(roomRequest.getDescription());
        var room = roomRepo.save(existed);
        return room;
    }

    public Room delete(Integer id) {
        var existed = roomRepo.findById(id).get();
        existed.setDeleted(true);
        return existed;
    }

    private String roomUrlGenerator(RoomRequest roomRequest) {
        String url = null;
        if (roomRequest.getUrl().isBlank()) {
            switch (roomRequest.getRoomType()) {
                case LABORATORY:
                    url = "https://static.turbosquid.com/Preview/2020/01/28__07_36_19/01.jpgC0FA9FA1-6EAB-414B-BE8D-AE10F1CF8D93Large.jpg";
                    break;
                case OFFICE:
                    url = "https://vrender.com/wp-content/uploads/2019/05/Office-OPEN-AREAS-Renderings.jpg";
                    break;
                case ACCOMMODATION:
                    url = "https://www.bypeople.com/wp-content/uploads/2018/10/livingroom-3d-scene-featured.png";
                    break;
            }
            return url;
        }
        return roomRequest.getUrl();
    }
}
