package com.example.demo.service;


import com.example.demo.domain.Device;
import com.example.demo.domain.Report;
import com.example.demo.domain.Room;
import com.example.demo.enums.DeviceType;
import com.example.demo.repo.DeviceRepo;
import com.example.demo.repo.ReportRepo;
import com.example.demo.repo.RoomRepo;
import com.example.demo.web.vm.RoomDetailResult;
import com.example.demo.web.vm.RoomRequest;
import com.example.demo.web.vm.RoomStatsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        List<Device> devices = deviceRepo.findAllByRoomIdAndDeletedFalse(id);
        var room = roomRepo.getOne(id);
        var result = RoomDetailResult.RoomDetailResultBuilder.aRoomDetailResult()
                .withName(room.getName())
                .withDescription(room.getDescription())
                .withHeight(room.getHeight())
                .withLength(room.getLength())
                .withRoomType(room.getRoomType())
                .withWidth(room.getWidth())
                .build();
        result.setDevices(devices);
        return result;
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
}
