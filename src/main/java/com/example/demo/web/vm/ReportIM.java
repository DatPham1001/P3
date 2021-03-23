package com.example.demo.web.vm;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ReportIM {
    private Integer temp;
    private Integer hum;
    private Integer co;
}
