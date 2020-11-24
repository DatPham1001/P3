package com.example.demo.domain;


import javax.persistence.*;

@Entity
@Table(name = "report")
public class Report extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "device_id")
    private Integer deviceId;
    private Integer humidity;
    private Integer temperature;
    private Integer coConcentration;

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getCoConcentration() {
        return coConcentration;
    }

    public void setCoConcentration(Integer coConcentration) {
        this.coConcentration = coConcentration;
    }
}
