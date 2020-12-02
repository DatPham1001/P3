package com.example.demo.repo;

import com.example.demo.domain.Report;
import com.example.demo.web.vm.ReportOM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepo extends JpaRepository<Report, Integer> {
    List<Report> findAllByDeviceId(Integer deviceId);

    Report findTopByDeviceId(Integer deviceId);

    @Query(value = "SELECT * FROM report WHERE device_id = :device_id AND created_date >= :date", nativeQuery = true)
    List<Report> getReports(@Param("device_id") Integer deviceId, @Param("date") String date);

    @Query(value = "select temperature,humidity,co_concentration co,HOUR(r.created_date) hour from report r\n" +
            "where DATE(created_date) = :date \n" +
            "group by hour\n" +
            "order by hour ASC\n", nativeQuery = true)
    List<ReportOM> getReportOfDate(@Param("date") String date);
//    '2020-11-26'
}
