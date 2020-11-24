package com.example.demo.repo;


import com.example.demo.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepo extends JpaRepository<Room,Integer> {
    Page<Room> findAllByAndIsDeletedFalse(Pageable pageable);
}
