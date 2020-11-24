package com.example.demo.service;


import com.example.demo.domain.Device;
import com.example.demo.repo.DeviceRepo;
import com.example.demo.repo.RoomRepo;
import com.example.demo.web.exception.BadRequestAlertException;
import com.example.demo.web.vm.DeviceFilterRequest;
import com.example.demo.web.vm.DeviceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {
    private final DeviceRepo deviceRepo;
    private final RoomRepo roomRepo;

    @Autowired
    public DeviceService(DeviceRepo deviceRepo, RoomRepo roomRepo) {
        this.deviceRepo = deviceRepo;
        this.roomRepo = roomRepo;
    }

    public Device create(DeviceRequest deviceRequest) {
        var existedRoom = roomRepo.findById(deviceRequest.getRoomId());
        if (existedRoom.isPresent()) {
            var device = Device.DeviceBuilder.aDevice()
                    .withRoomId(deviceRequest.getRoomId())
                    .withCode(deviceRequest.getCode())
                    .withName(deviceRequest.getName())
                    .withDeviceType(deviceRequest.getDeviceType())
                    .build();
            var result = deviceRepo.save(device);
            return result;
        } else throw new BadRequestAlertException("No room exist", "Device", "400");
    }

    public Device getById(Integer id) {
        return deviceRepo.findById(id).get();
    }

    public Device update(Integer id, DeviceRequest deviceRequest) {
        var existed = deviceRepo.getOne(id);
        var existedRoom = roomRepo.getOne(id);
        if (existed != null && existedRoom != null) {
            if (!deviceRequest.getName().isBlank()) {
                existed.setName(deviceRequest.getName());
            }
            if (!deviceRequest.getCode().isBlank()) {
                existed.setCode(deviceRequest.getCode());
            }
            existed.setRoomId(deviceRequest.getRoomId());
            deviceRepo.save(existed);
            return existed;
        } else throw new BadRequestAlertException("No room exist", "Device", "400");

    }

    public Page<?> filterDevice(DeviceFilterRequest deviceFilterRequest, Pageable pageable) {
        var devices = deviceRepo.filterDevice(deviceFilterRequest.getRoomId(), deviceFilterRequest.getCode(),
                deviceFilterRequest.getName(), pageable);
        return devices;
    }

    public void delete(Integer id) {
        var device = deviceRepo.getOne(id);
        device.setDeleted(true);
        deviceRepo.save(device);
    }
}
