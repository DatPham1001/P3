package com.example.demo.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "report")
public class Report extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "device_code")
    private String deviceCode;
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
