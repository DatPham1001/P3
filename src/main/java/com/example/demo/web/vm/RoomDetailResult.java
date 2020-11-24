package com.example.demo.web.vm;

import com.example.demo.domain.Device;
import com.example.demo.enums.RoomType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Getter
@Setter
public class RoomDetailResult {
    private String name;
    private RoomType roomType;
    private String description;
    private Integer width;
    private Integer length;
    private Integer height;
    private List<Device> devices;
    private RoomStats roomStats;

    @Getter
    @Setter
    public static class RoomStats {
        private Integer temp;
        private Integer humidity;
        private Integer co;
    }

    public static final class RoomDetailResultBuilder {
        private String name;
        private RoomType roomType;
        private String description;
        private Integer width;
        private Integer length;
        private Integer height;

        private RoomDetailResultBuilder() {
        }

        public static RoomDetailResultBuilder aRoomDetailResult() {
            return new RoomDetailResultBuilder();
        }

        public RoomDetailResultBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public RoomDetailResultBuilder withRoomType(RoomType roomType) {
            this.roomType = roomType;
            return this;
        }

        public RoomDetailResultBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public RoomDetailResultBuilder withWidth(Integer width) {
            this.width = width;
            return this;
        }

        public RoomDetailResultBuilder withLength(Integer length) {
            this.length = length;
            return this;
        }

        public RoomDetailResultBuilder withHeight(Integer height) {
            this.height = height;
            return this;
        }

        public RoomDetailResult build() {
            RoomDetailResult roomDetailResult = new RoomDetailResult();
            roomDetailResult.setName(name);
            roomDetailResult.setRoomType(roomType);
            roomDetailResult.setDescription(description);
            roomDetailResult.setWidth(width);
            roomDetailResult.setLength(length);
            roomDetailResult.setHeight(height);
            return roomDetailResult;
        }
    }
}
