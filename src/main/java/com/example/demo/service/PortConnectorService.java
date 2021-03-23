package com.example.demo.service;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *  Nguyen Hai Anh aka HAD all right reserved.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.example.demo.web.vm.ReportIM;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Enumeration;

@Service
public class PortConnectorService implements SerialPortEventListener  {

    SerialPort serialPort;
    /**
     * The port we're normally going to use.
     */
    private static final String PORT_NAMES[] = {"COM5",
            "COM7", // Windows
    };
    private ReportService reportService;
    private BufferedReader input;
    private OutputStream output;
    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 9600;


    public void initialize() {
        System.out.println("init");
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        //First, Find an instance of serial port as set in PORT_NAMES.
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
                }
            }
        }
        if (portId == null) {
            System.out.println("Could not find COM port to load data.");
            return;
        }

        try {
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                    TIME_OUT);
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            // open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
            System.out.println("Started");
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            ReportIM reportIM = new ReportIM();
            try {
                String inputLine = null;
                if (input.ready()) {
                    inputLine = input.readLine();
                    String[] chunks = inputLine.split(",");
                    System.out.println(inputLine);
                    System.out.println(chunks[0] + "\t" + chunks[1] + "\t" + chunks[2] + "\t");
                    reportIM.setTemp(Integer.valueOf(chunks[0]));
                    reportIM.setHum(Integer.valueOf(chunks[1]));
                    reportIM.setCo(Integer.valueOf(chunks[2]));
                    reportService.create(reportIM);
                }
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
        // Ignore all the other eventTypes, but you should consider the other ones.
    }
//    @Scheduled(fixedRate = 1000)
    public void StartConnection(){
        PortConnectorService main = new PortConnectorService();
        System.out.println("Starting");
        main.initialize();
        Thread t = new Thread() {
            public void run() {
                //the following line will keep this app alive for 1000    seconds,
                //waiting for events to occur and responding to them    (printing incoming messages to console).
                try {
                    Thread.sleep(1000000);
                } catch (InterruptedException ie) {
                }
            }
        };
        t.start();

    }
}

