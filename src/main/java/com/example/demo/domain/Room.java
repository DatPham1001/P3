package com.example.demo.domain;

import com.example.demo.enums.RoomType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "room")
public class Room extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "room_type")
    private RoomType roomType;
    private String description;
    private Integer width;
    private Integer length;
    private Integer height;
    @Column(name = "is_deleted",columnDefinition = "false")
    private boolean isDeleted;

    public static final class RoomBuilder {
        private String name;
        private RoomType roomType;
        private String description;
        private Integer width;
        private Integer length;
        private Integer height;

        private RoomBuilder() {
        }

        public static RoomBuilder aRoom() {
            return new RoomBuilder();
        }

        public RoomBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public RoomBuilder withRoomType(RoomType roomType) {
            this.roomType = roomType;
            return this;
        }

        public RoomBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public RoomBuilder withWidth(Integer width) {
            this.width = width;
            return this;
        }

        public RoomBuilder withLength(Integer length) {
            this.length = length;
            return this;
        }

        public RoomBuilder withHeight(Integer height) {
            this.height = height;
            return this;
        }

        public Room build() {
            Room room = new Room();
            room.setName(name);
            room.setRoomType(roomType);
            room.setDescription(description);
            room.setWidth(width);
            room.setLength(length);
            room.setHeight(height);
            return room;
        }
    }
}
