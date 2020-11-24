package com.example.demo.domain;


import javax.persistence.*;

@Entity
@Table(name = "configuration")
public class Configuration extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column( name = "room_id")
    private Integer roomId;
    @Column(name ="temp_limit")
    private Integer tempLimit;
    @Column(name ="hum_limit")
    private Integer humLimit;
    @Column(name = "co_limit")
    private Integer coLimit;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getTempLimit() {
        return tempLimit;
    }

    public void setTempLimit(Integer tempLimit) {
        this.tempLimit = tempLimit;
    }

    public Integer getHumLimit() {
        return humLimit;
    }

    public void setHumLimit(Integer humLimit) {
        this.humLimit = humLimit;
    }

    public Integer getCoLimit() {
        return coLimit;
    }

    public void setCoLimit(Integer coLimit) {
        this.coLimit = coLimit;
    }
}
