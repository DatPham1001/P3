package com.example.demo.web.vm;


import com.example.demo.enums.RoomType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomRequest {
    private String name;
    private Integer width;
    private Integer length;
    private Integer height;
    private String description;
    private RoomType roomType;
    private String url;
}
