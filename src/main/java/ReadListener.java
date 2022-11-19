
import bo.com.datec.pos.dto.ResponsePosDto;
import static bo.com.datec.pos.service.ServerCommunication.vRequestPosDto;
import bo.com.datec.pos.util.Constants;
import bo.com.datec.pos.util.NettyUtil;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author iquispe
 */
public class ReadListener implements Runnable {

    private SerialPort serialPort;

    public SerialPort getSerialPort() {
        return this.serialPort;
    }

    public void setSerialPort(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public ReadListener(SerialPort serialPort) {
        super();
        this.serialPort = serialPort;
    }

    @Override
    public void run() {
        System.out.println("####Escuchando dispositivo...####");
        this.serialPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {

                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_RECEIVED) {
                    return;
                }
                try {
                    String vData;
                    int vSizeData = event.getReceivedData().length;
                    if (vSizeData <= 0) {
                        System.err.println("No existen datos para leer");
                        return;
                    }
                    System.out.println("####Desde el Hilo de lectura...####");
                    vData = NettyUtil.bytesToHex(event.getReceivedData());
                    System.out.println("[POS] " + vData);
                    if (vData.equals("06")) {
                        System.out.println("POS:OK");
                    } else if (vData.equals("15")) {
                        ResponsePosDto vResponsePosDto = new ResponsePosDto();
                        vResponsePosDto.setCode(Constants.STATE_EXCEPCION);
                        vResponsePosDto.setSuccess(false);
                        vResponsePosDto.setData(null);
                        if ((vRequestPosDto.getFlujo() == Constants.NUMBER_FLOW_CHIP || vRequestPosDto.getFlujo() == Constants.NUMBER_FLOW_CHIP_MULTI) && vRequestPosDto.getPaso() == 5) {
                            vResponsePosDto.setMessage(Constants.ERROR_IN_PIN);
                        } else if ((vRequestPosDto.getFlujo() == Constants.NUMBER_FLOW_CTL || vRequestPosDto.getFlujo() == Constants.NUMBER_FLOW_CTL_MULTI) && vRequestPosDto.getPaso() == 4) {
                            vResponsePosDto.setMessage(Constants.ERROR_IN_CTL);
                        } else {
                            vResponsePosDto.setMessage(Constants.ERROR_TRANS);
                        }
                    } else {
                        System.err.println("Valor no permitido!");
                    }
                    /*switch (vRequestPosDto.getFlujo()) {
                     case Constants.NUMBER_FLOW_CHIP:
                     flujoChip(vData);
                     break;
                     case Constants.NUMBER_FLOW_CTL:
                     flujoCtl(vData);
                     break;
                     default:
                     ResponsePosDto vResponsePosDto = new ResponsePosDto();
                     vResponsePosDto.setCode(Constants.STATE_EXCEPCION);
                     vResponsePosDto.setSuccess(false);
                     vResponsePosDto.setMessage(Constants.ERROR_NOTOP);
                     vResponsePosDto.setData(vRequestPosDto.getFlujo());
                     System.out.println(vResponsePosDto.toString());
                     break;
                     }*/
                } catch (Exception ex) {
                    System.err.println("[ERROR] " + ex.getMessage());
                }
            }
        });
    }
}
