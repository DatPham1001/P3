package com.example.demo.repo;

import com.example.demo.domain.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface DeviceRepo extends JpaRepository<Device, Integer> {
    @Query(value = "select * from device " +
            "where is_deleted = false" +
            " or room_id = :roomId " +
            "or upper(code) like concat('%',upper(:code),'%') " +
            "or upper(name) like concat('%',upper(:name),'%') "
            , nativeQuery = true)
    Page<Device> filterDevice(@Param("roomId") Integer roomId,
                              @Param("code") String code,
                              @Param("name") String name, Pageable pageable);
    Device findDeviceByRoomIdAndDeletedFalse(Integer roomId);
    Device findDeviceByDeviceTypeAndRoomIdAndDeletedFalse(String deviceType,Integer roomId);
}
