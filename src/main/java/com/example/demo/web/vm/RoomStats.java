package com.example.demo.web.vm;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class RoomStats {
    private List<Integer> temp;
    private List<Integer> humidity;
    private List<Integer> co;
    private List<String> time;
}
