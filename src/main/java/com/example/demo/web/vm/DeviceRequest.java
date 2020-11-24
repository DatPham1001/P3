package com.example.demo.web.vm;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class DeviceRequest {
    private Integer roomId;
    private String name;
    private String code;
    private String deviceType;
}
