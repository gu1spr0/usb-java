
import bo.com.datec.pos.dto.RequestPosDto;
import bo.com.datec.pos.dto.ResponsePosDto;
import bo.com.datec.pos.service.PayService;
import static bo.com.datec.pos.service.ServerCommunication.vRequestPosDto;
import static bo.com.datec.pos.service.ServerCommunication.vSerialPort;
import bo.com.datec.pos.util.Constants;
import bo.com.datec.pos.util.NettyUtil;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author iquispe
 */
public class Principal {

    public static void main(String[] args) throws InterruptedException {
        //Preparar request
        System.out.println("#####Creando solicitud#####");
        RequestPosDto vRequestPosDto = new RequestPosDto();
        vRequestPosDto.setMonto(new BigDecimal(20.50));
        vRequestPosDto.setUsername("ISRAEL");
        //Buscar  usb
        System.out.println("#####Buscando dispositivo#####");
        String vCom = "COM9";
        SerialPort[] vSerialPortList = SerialPort.getCommPorts();
        SerialPort vSerialPort = null;
        for (SerialPort vSerialPortRow : vSerialPortList) {
            if (vSerialPortRow.getSystemPortName().equals(vCom)) {
                vSerialPort = vSerialPortRow;
                break;
            }
        }
        if(vSerialPort == null) {
            throw new RuntimeException("No se encontro puerto com9!");
        }
        vSerialPort.setBaudRate(115200);
        vSerialPort.setParity(0);
        vSerialPort.setNumDataBits(8);
        vSerialPort.setNumStopBits(1);
        vSerialPort.setRTS();
        vSerialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        vSerialPort.openPort();
        if(!vSerialPort.isOpen()) {
            throw new RuntimeException("Puerto cerrado!");
        }
        //Crear hilo de lectura
        Thread vThread = new Thread(new ReadListener(vSerialPort));
        vThread.start();
        //Enviar ACK
        String msg = "06";
        System.out.println("Enviando: "+msg);
        if (vSerialPort.isOpen()) {
            try {
                OutputStream vOutputStream = vSerialPort.getOutputStream();
                vOutputStream.write(msg.getBytes());
            } catch (IOException ioe) {
                throw new RuntimeException("No se pudo enviar: "+ioe.getMessage());
            }
        }
        Thread.sleep(5000);
        
        //Enviar verificar existencia
        msg = "02001736303030303030303030313030303030300323";
        System.out.println("Enviando: "+msg);
        if (vSerialPort.isOpen()) {
            try {
                OutputStream vOutputStream = vSerialPort.getOutputStream();
                vOutputStream.write(msg.getBytes());
            } catch (IOException ioe) {
                throw new RuntimeException("No se pudo enviar: "+ioe.getMessage());
            }
        }
        //Solicitar conexion
        //Comenzar proceso
    }
}
