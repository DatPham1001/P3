package com.example.demo.web.rest;


import com.example.demo.service.RoomService;
import com.example.demo.web.vm.RoomRequest;
import com.example.demo.web.vm.RoomStatsResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/room")
public class RoomResource {
    private final RoomService roomService;

    public RoomResource(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody RoomRequest roomRequest) {
        var room = roomService.create(roomRequest);
        return ResponseEntity.ok().body(room);
    }

    @GetMapping
    public ResponseEntity<?> getRooms(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit
    ) {
        List<RoomStatsResult> rooms = roomService.getRooms(PageRequest.of(page.orElse(0), limit.orElse(10)));
        return ResponseEntity.ok().body(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(roomService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody RoomRequest roomRequest) {
        var room = roomService.update(id, roomRequest);
        return ResponseEntity.ok().body(room);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        roomService.delete(id);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
