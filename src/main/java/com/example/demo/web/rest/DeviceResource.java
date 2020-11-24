package com.example.demo.web.rest;


import com.example.demo.service.DeviceService;
import com.example.demo.web.vm.DeviceFilterRequest;
import com.example.demo.web.vm.DeviceRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/device")
public class DeviceResource {
    private final DeviceService deviceService;

    public DeviceResource(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public ResponseEntity<?> filterDevice(@Valid DeviceFilterRequest deviceRequest,
                                          Pageable pageable) {
        var devices = deviceService.filterDevice(deviceRequest, pageable);
        return ResponseEntity.ok().body(devices);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody DeviceRequest deviceRequest) {
        var device = deviceService.create(deviceRequest);
        return ResponseEntity.ok().body(device);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody DeviceRequest deviceRequest) {
        var device = deviceService.update(id, deviceRequest);
        return ResponseEntity.ok().body(device);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        var device = deviceService.getById(id);
        return ResponseEntity.ok().body(device);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        deviceService.delete(id);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
