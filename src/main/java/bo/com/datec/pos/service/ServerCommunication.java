/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.com.datec.pos.service;

import bo.com.datec.pos.dto.RequestPosDto;
import bo.com.datec.pos.dto.ResponsePosDto;
import bo.com.datec.pos.dto.SaleDto;
import bo.com.datec.pos.util.Constants;
import bo.com.datec.pos.util.NettyUtil;
import bo.com.datec.pos.util.ResponseUtil;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 *
 * @author iquispe
 */
public class ServerCommunication {

    public static boolean isAck1 = false;
    public static boolean isAck2 = false;
    public static boolean isAck3 = false;
    public static boolean isAck4 = false;
    public static SerialPort vSerialPort;
    public static String ack = "06";
    public static String nack = "15";
    public static RequestPosDto vRequestPosDto;
    public static ResponsePosDto vResponsePosDto;    

    public static String sendAck() {
        System.out.println("*********************");
        System.out.println("*****ENVIANDO ACK****");
        System.out.println("*********************");
        String msg = "06";
        sendMessageToPOS(msg);
        return msg;
    }

    public static String sendNoAck() {
        System.out.println("*********************");
        System.out.println("*****ENVIANDO NACK****");
        System.out.println("*********************");
        String msg = "20";
        sendMessageToPOS(msg);
        return msg;
    }

    public static String sendConnectionChip() {
        System.out.println("*******************");
        System.out.println("***ENVIANDO CHIP***");
        System.out.println("*******************");
        String msg = "02001736303030303030303030313030303030300323";
        sendMessageToPOS(msg);
        return msg;
    }

    public static String sendConnectionCtl() {
        System.out.println("**************************");
        System.out.println("***ENVIANDO CONTACTLESS***");
        System.out.println("**************************");
        String msg = "02001736303030303030303030313030363030300325";
        sendMessageToPOS(msg);
        return msg;
    }

    public static String sendTransRevNo() {
        System.out.println("***************************");
        System.out.println("***ENVIANDO TRANS.REV.NO***");
        System.out.println("***************************");
        String msg = "02002436303030303030303030313030303030311C3438000258580303";
        sendMessageToPOS(msg);
        return msg;
    }

    public static String sendTipoTarjetaCtl() {
        System.out.println("**************************");
        System.out.println("***ENVIANDO CONTACTLESS***");
        System.out.println("**************************");
        String msg = "02001736303030303030303030313030363030310324";
        sendMessageToPOS(msg);
        return msg;
    }

    public static String sendSolicitudCierre() {
        System.out.println("*******************************");
        System.out.println("***ENVIANDO SOLICITUD CIERRE***");
        System.out.println("*******************************");
        String msg = "02001736303030303030303030313030313030300322";
        sendMessageToPOS(msg);
        return msg;
    }

    public static String sendSolicitudAnulacion() {
        System.out.println("**********************************");
        System.out.println("***ENVIANDO SOLICITUD ANULACION***");
        System.out.println("**********************************");
        String msg = "02001736303030303030303030313030353030300326";
        sendMessageToPOS(msg);
        return msg;
    }

    public static String sendConfirmarAnulacion() {
        System.out.println("**********************************");
        System.out.println("***ENVIANDO SOLICITUD ANULACION***");
        System.out.println("**********************************");
        String msg = "02002436303030303030303030313030353030321C3438000230300305";
        sendMessageToPOS(msg);
        return msg;
    }

    public static String sendSolicitudInicializar() {
        System.out.println("**************************");
        System.out.println("***ENVIANDO INICIALIZAR***");
        System.out.println("**************************");
        String msg = "02001736303030303030303030313030323030300321";
        sendMessageToPOS(msg);
        return msg;
    }

    public static String sendDataToPos(String pMontoBob) {
        System.out.println("[KIOSCO]:Enviando datos de venta");
        String inicio = "007736303030303030303030313030303030321C";
        System.out.println("El monto para enviar es: " + pMontoBob);
        String monto = "3430000C" + NettyUtil.asciiToHex(pMontoBob) + "1C";
        System.out.println("El monto codificado de ascii es :" + monto);
        String mpkh = "720" + "          ";
        mpkh = mpkh.substring(0, 10);
        String numCaja = "3432000A" + NettyUtil.asciiToHex(mpkh) + "1C";
        String codresp = "34380002" + "2020" + "1C";
        String pnrh = "028510" + "          ";
        pnrh = pnrh.substring(0, 10);
        String numTransac = "3533000A" + NettyUtil.asciiToHex(pnrh) + "1C";
        String tipoCuenta = "38380001" + "31" + "03";
        String trama = inicio + monto + numCaja + codresp + numTransac + tipoCuenta;
        String xo = NettyUtil.xor(NettyUtil.hex2a(trama));
        String tramaFinal = "02" + trama + xo;
        sendMessageToPOS(tramaFinal);
        return tramaFinal;
    }

    public static String sendAnulacionToPos(String pId) {
        System.out.println("*****************************");
        System.out.println("***ENVIANDO DATA ANULACION***");
        System.out.println("*****************************");
        String inicio = "003536303030303030303030313030353030311C";
        String recibo = "34330006" + NettyUtil.asciiToHex(pId) + "1C";
        String codresp = "34380002" + "2020" + "03";
        String trama = inicio + recibo + codresp;
        String xo = NettyUtil.xor(NettyUtil.hex2a(trama));
        String tramaFinal = "02" + trama + xo;
        sendMessageToPOS(tramaFinal);
        return tramaFinal;
    }

    public static void sendMessageToPOS(String msg) {
        System.out.println("[TAREA] Enviando: " + msg);
        if (vSerialPort.isOpen()) {
            try {
                byte[] byteArray = msg.getBytes(Charset.forName("UTF-8"));
                OutputStream vOutputStream = vSerialPort.getOutputStream();
                vOutputStream.write(byteArray);
            } catch (IOException ioe) {
                System.err.println("[ERROR] " + ioe.getMessage());
            }
        } else {
            System.err.println("[ERROR] Puerto cerrado");
        }
    }

    
}
