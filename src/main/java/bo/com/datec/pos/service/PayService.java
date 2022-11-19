/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.com.datec.pos.service;

import bo.com.datec.pos.dto.RequestPosDto;
import bo.com.datec.pos.dto.ResponsePosDto;
import bo.com.datec.pos.dto.SaleDto;
import static bo.com.datec.pos.service.ServerCommunication.ack;
import static bo.com.datec.pos.service.ServerCommunication.isAck1;
import static bo.com.datec.pos.service.ServerCommunication.isAck2;
import static bo.com.datec.pos.service.ServerCommunication.isAck3;
import static bo.com.datec.pos.service.ServerCommunication.isAck4;
import static bo.com.datec.pos.service.ServerCommunication.nack;
import static bo.com.datec.pos.service.ServerCommunication.vRequestPosDto;
import static bo.com.datec.pos.service.ServerCommunication.vResponsePosDto;
import static bo.com.datec.pos.service.ServerCommunication.vSerialPort;
import bo.com.datec.pos.util.Constants;
import bo.com.datec.pos.util.NettyUtil;
import bo.com.datec.pos.util.ResponseUtil;
import com.fazecast.jSerialComm.SerialPort;

/**
 *
 * @author iquispe
 */
public class PayService {

    public boolean isAck(String pStr) {
        return pStr.equals(ack);
    }

    public boolean isNack(String pStr) {
        return pStr.equals(nack);
    }
    
    public void payChipSingleCommerce(RequestPosDto pRequestPosDto, SerialPort pSerialPort) {
        if (NettyUtil.isNaN(pRequestPosDto.getMonto())) {
            ResponsePosDto vResponsePosDto = new ResponsePosDto();
            vResponsePosDto.setCode(Constants.STATE_EXCEPCION);
            vResponsePosDto.setSuccess(false);
            vResponsePosDto.setMessage(Constants.ERROR_IN_AMOUNT);
            vResponsePosDto.setData(pRequestPosDto.getMonto());
            System.out.println(vResponsePosDto.toString());
        } else {
            String montoBoB = NettyUtil.validarMonto(pRequestPosDto.getMonto());
            System.out.println("MONTO VALIDADO:" + montoBoB);
            pRequestPosDto.setFlujo(Constants.NUMBER_FLOW_CHIP);
            pRequestPosDto.setStrFlujo(Constants.FLOW_CHIP);
            pRequestPosDto.setMontoTransaccion(montoBoB);
            pRequestPosDto.setPaso(1);
            pRequestPosDto.setTamaño(0);
            if (vSerialPort != null) {
                ServerCommunication.vSerialPort = pSerialPort;
                selectProcess(pRequestPosDto);
            }
        }
    }
    
    public void selectProcess(RequestPosDto pRequestPosDto) {
        if (!vSerialPort.isOpen()) {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("XXXXX NINGÚN DISPOSITIVO ENCONTRADO XXXXX");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            vResponsePosDto = new ResponsePosDto();
            vResponsePosDto.setCode(Constants.STATE_EXCEPCION);
            vResponsePosDto.setSuccess(false);
            vResponsePosDto.setMessage(Constants.ERROR_NODIS);
            vResponsePosDto.setData(null);
            System.out.println(vResponsePosDto.toString());
        } else {
            System.out.println("*******************************");
            System.out.println("********GENERANDO FLUJO********");
            System.out.println("*******************************");
            System.out.println("FLUJO STR:" + pRequestPosDto.getStrFlujo());
            System.out.println("FLUJO NUM:" + pRequestPosDto.getFlujo());
            vRequestPosDto = pRequestPosDto;
            switch (pRequestPosDto.getFlujo()) {
                case Constants.NUMBER_FLOW_CHIP:
                    ServerCommunication.sendConnectionChip();
                    break;
                case Constants.NUMBER_FLOW_CTL:
                    ServerCommunication.sendConnectionCtl();
                    break;
                default:
                    System.out.println("[FLUJO]: Flujo no definido!");
                    break;
            }
        }
    }

    public void flujoChip(String pStrReply) {
        switch (vRequestPosDto.getPaso()) {
            case 1: {
                if (isAck1 && vRequestPosDto.getTamaño() == 40) {
                    ServerCommunication.sendAck();
                    ServerCommunication.sendTransRevNo();
                    vRequestPosDto.setPaso(2);
                    vRequestPosDto.setTamaño(0);
                    break;
                } else if (isAck(pStrReply)) {
                    isAck1 = true;
                    vRequestPosDto.setTamaño(0);
                    break;
                }
            }
            case 2: {
                if (isAck2 && vRequestPosDto.getTamaño() == 36) {
                    ServerCommunication.sendAck();
                    vRequestPosDto.setPaso(3);
                    vRequestPosDto.setTamaño(0);
                    vResponsePosDto = new ResponsePosDto();
                    vResponsePosDto.setCode(Constants.STATE_TARJETA);
                    vResponsePosDto.setSuccess(true);
                    vResponsePosDto.setMessage(Constants.MSG_CHIP);
                    vResponsePosDto.setData(null);
                    System.out.println(vResponsePosDto.toString());
                    break;
                } else if (isAck(pStrReply)) {
                    isAck2 = true;
                    vRequestPosDto.setTamaño(0);
                    break;
                }
            }
            case 3: {
                if (vRequestPosDto.getTamaño() == 29) {
                    ServerCommunication.sendAck();
                    ServerCommunication.sendDataToPos(vRequestPosDto.getMontoTransaccion());
                    vRequestPosDto.setPaso(4);
                    vRequestPosDto.setTamaño(0);
                    break;
                } else if (isNack(pStrReply)) {
                    vRequestPosDto.setPaso(-1);
                    vRequestPosDto.setTamaño(0);
                    vRequestPosDto.setFlujo(Constants.NUMBER_FLOW_NONE);
                    vRequestPosDto.setStrFlujo(Constants.FLOW_NONE);
                    vResponsePosDto = new ResponsePosDto();
                    vResponsePosDto.setCode(Constants.STATE_EXCEPCION);
                    vResponsePosDto.setSuccess(false);
                    vResponsePosDto.setMessage(Constants.ERROR_IN_CHIP);
                    vResponsePosDto.setData(null);
                    System.out.println(vResponsePosDto.toString());
                    break;
                } else {
                    break;
                }
            }
            case 4: {
                if (isAck4 && vRequestPosDto.getTamaño() == 36) {
                    ServerCommunication.sendAck();
                    vRequestPosDto.setPaso(5);
                    vRequestPosDto.setTamaño(0);
                    vResponsePosDto = new ResponsePosDto();
                    vResponsePosDto.setCode(Constants.STATE_PIN);
                    vResponsePosDto.setSuccess(true);
                    vResponsePosDto.setMessage(Constants.MSG_PIN);
                    vResponsePosDto.setData(null);
                    System.out.println(vRequestPosDto.toString());
                    break;
                } else if (isAck(pStrReply)) {
                    isAck4 = true;
                    vRequestPosDto.setTamaño(0);
                    break;
                } else if (isNack(pStrReply)) {
                    vRequestPosDto.setPaso(-1);
                    vRequestPosDto.setTamaño(0);
                    vRequestPosDto.setFlujo(Constants.NUMBER_FLOW_NONE);
                    vRequestPosDto.setStrFlujo(Constants.FLOW_NONE);
                    break;
                } else {
                    break;
                }
            }
            case 5: {
                System.out.println(vResponsePosDto.toString());
                if (vRequestPosDto.getTamaño() >= 223) {
                    vResponsePosDto = new ResponsePosDto();
                    vResponsePosDto.setCode(Constants.STATE_PAY);
                    vResponsePosDto.setSuccess(true);
                    vResponsePosDto.setMessage(Constants.MSG_PAY);
                    vResponsePosDto.setData(null);
                    ServerCommunication.sendAck();
                    SaleDto vSaleDto = ResponseUtil.getRespuestaHostVenta(pStrReply);
                    vSaleDto.setFechaLocal(NettyUtil.getGlobalDate(vSaleDto.getFechaTransaccion(), vSaleDto.getHoraTransaccion()));
                    vSaleDto.setMontoCompra(vRequestPosDto.getMonto());
                    vRequestPosDto.setPaso(-1);
                    vRequestPosDto.setTamaño(0);
                    isAck1 = false;
                    isAck2 = false;
                    isAck3 = false;
                    isAck4 = false;
                    ServerCommunication.vResponsePosDto = new ResponsePosDto();
                    if (vSaleDto.getCodAutorizacion() != null && vSaleDto.getCodigoRespuesta().equals("00")) {
                        vResponsePosDto.setCode(Constants.STATE_REALIZADO);
                        vResponsePosDto.setSuccess(true);
                        vResponsePosDto.setMessage(Constants.MSG_SUCCESS);
                        vResponsePosDto.setData(null);
                    } else {
                        vResponsePosDto.setCode(Constants.STATE_EXCEPCION);
                        vResponsePosDto.setSuccess(false);
                        vResponsePosDto.setMessage(Constants.ERROR_TRANS);
                        vResponsePosDto.setData(null);
                    }
                    vRequestPosDto.setFlujo(Constants.NUMBER_FLOW_NONE);
                    vRequestPosDto.setStrFlujo(Constants.FLOW_NONE);
                    System.out.println(vRequestPosDto.toString());
                    break;
                } else if (isAck(pStrReply)) {
                    break;
                } else if (isNack(pStrReply)) {
                    vRequestPosDto.setPaso(-1);
                    vRequestPosDto.setTamaño(0);
                    vRequestPosDto.setFlujo(Constants.NUMBER_FLOW_NONE);
                    vRequestPosDto.setStrFlujo(Constants.FLOW_NONE);
                    vResponsePosDto = new ResponsePosDto();
                    vResponsePosDto.setCode(Constants.STATE_EXCEPCION);
                    vResponsePosDto.setSuccess(false);
                    vResponsePosDto.setMessage(Constants.ERROR_IN_PIN);
                    vResponsePosDto.setData(null);
                    System.out.println(vRequestPosDto.toString());
                    break;
                } else {
                    ServerCommunication.vResponsePosDto = new ResponsePosDto();
                    vResponsePosDto.setCode(Constants.STATE_EXCEPCION);
                    vResponsePosDto.setSuccess(false);
                    vResponsePosDto.setMessage(Constants.ERROR_TRANS);
                    vResponsePosDto.setData(null);
                    System.out.println(vRequestPosDto.toString());
                    break;
                }
            }
            default:
                break;
        }
    }

    public void flujoCtl(String pStrReply) {
        switch (vRequestPosDto.getPaso()) {
            case 1: {
                if (isAck1 && vRequestPosDto.getTamaño() == 40) {
                    ServerCommunication.sendAck();
                    ServerCommunication.sendTransRevNo();
                    vRequestPosDto.setPaso(2);
                    vRequestPosDto.setTamaño(0);
                    break;
                } else if (isAck(pStrReply)) {
                    isAck1 = true;
                    vRequestPosDto.setTamaño(0);
                    break;
                }
            }
            case 2: {
                if (isAck2 && vRequestPosDto.getTamaño() == 29) {
                    ServerCommunication.sendAck();
                    ServerCommunication.sendDataToPos(vRequestPosDto.getMontoTransaccion());
                    vRequestPosDto.setPaso(3);
                    vRequestPosDto.setTamaño(0);
                    break;
                } else if (isAck(pStrReply)) {
                    isAck2 = true;
                    vRequestPosDto.setTamaño(0);
                    break;
                }
            }
            case 3: {
                if (isAck3 && vRequestPosDto.getTamaño() == 36) {
                    ServerCommunication.sendAck();
                    ServerCommunication.sendTipoTarjetaCtl();
                    vResponsePosDto = new ResponsePosDto();
                    vResponsePosDto.setCode(Constants.STATE_TARJETA);
                    vResponsePosDto.setSuccess(true);
                    vResponsePosDto.setMessage(Constants.MSG_CTL);
                    vResponsePosDto.setData(null);
                    System.out.println(vRequestPosDto.toString());
                    vRequestPosDto.setPaso(4);
                    vRequestPosDto.setTamaño(0);
                    break;
                } else if (isAck(pStrReply)) {
                    isAck3 = true;
                    vRequestPosDto.setTamaño(0);
                    break;
                }
            }

            case 4: {
                if (isAck4 && vRequestPosDto.getTamaño() == 36) {
                    ServerCommunication.sendAck();
                    vRequestPosDto.setPaso(5);
                    vRequestPosDto.setTamaño(0);
                    break;
                } else if (isAck(pStrReply)) {
                    isAck4 = true;
                    vRequestPosDto.setTamaño(0);
                    break;
                } else if (isNack(pStrReply)) {
                    vResponsePosDto = new ResponsePosDto();
                    vResponsePosDto.setCode(Constants.STATE_EXCEPCION);
                    vResponsePosDto.setSuccess(false);
                    vResponsePosDto.setMessage(Constants.ERROR_IN_CTL);
                    vResponsePosDto.setData(null);
                    vRequestPosDto.setPaso(-1);
                    vRequestPosDto.setTamaño(0);
                    vRequestPosDto.setFlujo(Constants.NUMBER_FLOW_NONE);
                    vRequestPosDto.setStrFlujo(Constants.FLOW_NONE);
                    System.out.println(vResponsePosDto.toString());
                    break;
                }
            }
            case 5: {
                if (vRequestPosDto.getTamaño() >= 222) {
                    vResponsePosDto = new ResponsePosDto();
                    vResponsePosDto.setCode(Constants.STATE_PAY);
                    vResponsePosDto.setSuccess(true);
                    vResponsePosDto.setMessage(Constants.MSG_PAY);
                    vResponsePosDto.setData(pStrReply);
                    System.out.println(vResponsePosDto.toString());
                    ServerCommunication.sendAck();
                    SaleDto vSaleDto = ResponseUtil.getRespuestaHostVenta(pStrReply);
                    vSaleDto.setFechaLocal(NettyUtil.getGlobalDate(vSaleDto.getFechaTransaccion(), vSaleDto.getHoraTransaccion()));
                    vSaleDto.setMontoCompra(vRequestPosDto.getMonto());
                    vRequestPosDto.setPaso(-1);
                    vRequestPosDto.setTamaño(0);
                    isAck1 = false;
                    isAck2 = false;
                    isAck3 = false;
                    isAck4 = false;

                    ServerCommunication.vResponsePosDto = new ResponsePosDto();

                    if (vSaleDto.getCodAutorizacion() != null && vSaleDto.getCodigoRespuesta().equals("00")) {
                        vResponsePosDto.setCode(Constants.STATE_REALIZADO);
                        vResponsePosDto.setSuccess(true);
                        vResponsePosDto.setMessage(Constants.MSG_SUCCESS);
                        vResponsePosDto.setData(null);
                    } else {
                        vResponsePosDto.setCode(Constants.STATE_EXCEPCION);
                        vResponsePosDto.setSuccess(false);
                        vResponsePosDto.setMessage(Constants.ERROR_TRANS);
                        vResponsePosDto.setData(null);
                    }
                    vRequestPosDto.setFlujo(Constants.NUMBER_FLOW_NONE);
                    vRequestPosDto.setStrFlujo(Constants.FLOW_NONE);
                    System.out.println(vResponsePosDto.toString());
                    break;
                } else if (isAck(pStrReply)) {
                    break;
                } else if (isNack(pStrReply)) {
                    vRequestPosDto.setPaso(-1);
                    vRequestPosDto.setTamaño(0);
                    vRequestPosDto.setStrFlujo(Constants.FLOW_NONE);
                    vRequestPosDto.setFlujo(Constants.NUMBER_FLOW_NONE);

                    vResponsePosDto = new ResponsePosDto();
                    vResponsePosDto.setCode(Constants.STATE_EXCEPCION);
                    vResponsePosDto.setSuccess(false);
                    vResponsePosDto.setMessage(Constants.ERROR_IN_CTL);
                    vResponsePosDto.setData(null);
                    System.out.println(vResponsePosDto.toString());
                    break;
                } else {
                    vResponsePosDto = new ResponsePosDto();
                    vResponsePosDto.setCode(Constants.STATE_EXCEPCION);
                    vResponsePosDto.setSuccess(false);
                    vResponsePosDto.setMessage(Constants.ERROR_TRANS);
                    vResponsePosDto.setData(pStrReply);
                    System.out.println(vResponsePosDto.toString());
                    break;
                }

            }
        }
    }
}
