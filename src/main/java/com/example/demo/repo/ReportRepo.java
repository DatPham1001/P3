package com.example.demo.repo;

import com.example.demo.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepo extends JpaRepository<Report,Integer> {
    List<Report> findAllByDeviceId(Integer deviceId);
    Report findTopByDeviceId(Integer deviceId);
}
