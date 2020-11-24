package com.example.demo.web.vm;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.time.Instant;

@Component
@Getter
@Setter
public class DeviceRespondModel {
    private Integer id;
    private Integer roomId;
    private String roomName;
    private String name;
    private String code;
    private Instant createdDate;
    private Instant lastModifiedDate;
}
