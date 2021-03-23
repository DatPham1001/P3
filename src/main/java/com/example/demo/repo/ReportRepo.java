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
    List<Report> findAllByDeviceCode(String deviceCode);

    Report findTopByDeviceCode(String deviceCode);

    @Query(value = "SELECT * FROM report WHERE device_id = :device_id AND created_date >= :date", nativeQuery = true)
    List<Report> getReports(@Param("device_id") Integer deviceId, @Param("date") String date);

    @Query(value = "select temperature,humidity,co_concentration co,HOUR(r.created_date) hour from report r\n" +
            "where DATE(created_date) = :date \n" +
            "group by hour\n" +
            "order by hour ASC\n", nativeQuery = true)
    List<ReportOM> getReportOfDate(@Param("date") String date);
//    '2020-11-30'
    @Query(value = "select temperature,humidity,co_concentration co,minute(r.created_date) minute,hour(r.created_date) hour\n" +
            "    from report r \n" +
            "    where DATE(created_date) = :date \n" +
            "    and device_code LIKE :deviceCode \n" +
            "    group by minute \n" +
            "    order by minute ASC \n" +
            "    limit 5 ",nativeQuery = true)
    List<ReportOM> getReportForDiagramInMinutes(@Param("date") String date,@Param("deviceCode") String deviceCode);

}
