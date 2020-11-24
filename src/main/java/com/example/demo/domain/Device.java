package com.example.demo.domain;


import javax.persistence.*;

@Entity
@Table(name = "device")
public class Device extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "room_id")
    private Integer roomId;
    @Column(name = "device_type")
    private String deviceType;
    private String name;
    private String code;
    @Column(name = "is_deleted")
    private boolean deleted;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getId() {
        return id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static final class DeviceBuilder {
        private Integer roomId;
        private String deviceType;
        private String name;
        private String code;

        private DeviceBuilder() {
        }

        public static DeviceBuilder aDevice() {
            return new DeviceBuilder();
        }

        public DeviceBuilder withRoomId(Integer roomId) {
            this.roomId = roomId;
            return this;
        }

        public DeviceBuilder withDeviceType(String deviceType) {
            this.deviceType = deviceType;
            return this;
        }

        public DeviceBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public DeviceBuilder withCode(String code) {
            this.code = code;
            return this;
        }

        public Device build() {
            Device device = new Device();
            device.setRoomId(roomId);
            device.setDeviceType(deviceType);
            device.setName(name);
            device.setCode(code);
            return device;
        }
    }
}
