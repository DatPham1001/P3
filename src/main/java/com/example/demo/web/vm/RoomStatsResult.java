package com.example.demo.web.vm;

import com.example.demo.enums.RoomType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class RoomStatsResult {
    private Integer id;
    private String name;
    private RoomType roomType;
    private String description;
    private Integer width;
    private Integer length;
    private Integer height;
    private RoomStats roomStats;


    @Getter
    @Setter
    public static class RoomStats {
        private Integer temp;
        private Integer humidity;
        private Integer co;
    }
}
