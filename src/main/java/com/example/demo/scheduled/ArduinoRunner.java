package com.example.demo.scheduled;

import com.example.demo.service.PortConnectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArduinoRunner extends Thread {
    private PortConnectorService portConnectorService;

    public void run() {
        System.out.println(this.getName() + ": New Thread is running...");
        try {
            //Wait for one sec so it doesn't print too fast
            portConnectorService.initialize();
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
