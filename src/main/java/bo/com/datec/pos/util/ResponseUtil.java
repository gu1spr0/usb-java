/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.com.datec.pos.util;

import bo.com.datec.pos.dto.SaleDto;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author iquispe
 */
@Setter
@Getter
public class ResponseUtil {
    public static SaleDto getRespuestaHostVenta(String pRespuestaHost) {
        SaleDto vSaleDto = new SaleDto();
        String vCodigoAutorizacion = pRespuestaHost.substring(Constants.BEGIN_COD_AUTORIZACION, Constants.BEGIN_COD_AUTORIZACION + Constants.TAM_COD_AUTORIZACION);
        String vMontoCompra = pRespuestaHost.substring(Constants.BEGIN_MONTO_COMPRA, Constants.BEGIN_MONTO_COMPRA + Constants.TAM_MONTO_COMPRA);
        String vNumeroRecibo = pRespuestaHost.substring(Constants.BEGIN_NUM_RECIBO, Constants.BEGIN_NUM_RECIBO + Constants.TAM_NUM_RECIBO);
        String vRrn = pRespuestaHost.substring(Constants.BEGIN_RRN, Constants.BEGIN_RRN + Constants.TAM_RRN);
        String vTerminalId = pRespuestaHost.substring(Constants.BEGIN_TERMINAL_ID, Constants.BEGIN_TERMINAL_ID + Constants.TAM_TERMINAL_ID);
        String vFechaTransaccion = pRespuestaHost.substring(Constants.BEGIN_FECHA_TRANSAC, Constants.BEGIN_FECHA_TRANSAC + Constants.TAM_FECHA_TRANSAC);
        String vHoraTransaccion = pRespuestaHost.substring(Constants.BEGIN_HORA_TRANSAC, Constants.BEGIN_HORA_TRANSAC + Constants.TAM_HORA_TRANSAC);
        String vCodigoRespuesta = pRespuestaHost.substring(Constants.BEGIN_COD_RESPUESTA, Constants.BEGIN_COD_RESPUESTA + Constants.TAM_COD_RESPUESTA);
        String vTipoCuenta = pRespuestaHost.substring(Constants.BEGIN_TIPO_CUENTA, Constants.BEGIN_TIPO_CUENTA + Constants.TAM_TIPO_CUENTA);
        String vNumeroCuotas = pRespuestaHost.substring(Constants.BEGIN_NUM_CUOTAS, Constants.BEGIN_NUM_CUOTAS + Constants.TAM_NUM_CUOTAS);
        String vUltimosDigitos = pRespuestaHost.substring(Constants.BEGIN_ULT_DIGITOS, Constants.BEGIN_ULT_DIGITOS + Constants.TAM_ULT_DIGITOS);
        String vMensajeError = pRespuestaHost.substring(Constants.BEGIN_MSG_ERROR, Constants.BEGIN_MSG_ERROR + Constants.TAM_MSG_ERROR);
        String vBinTarjeta = pRespuestaHost.substring(Constants.BEGIN_BIN_TARJETA, Constants.BEGIN_BIN_TARJETA + Constants.TAM_BIN_TARJETA);

        vSaleDto.setCodAutorizacion(NettyUtil.hex2a(vCodigoAutorizacion));
        vSaleDto.setMontoCompraTransaccion(NettyUtil.hex2a(vMontoCompra));
        vSaleDto.setNumeroRecibo(NettyUtil.hex2a(vNumeroRecibo));
        vSaleDto.setRrn(NettyUtil.hex2a(vRrn));
        vSaleDto.setTerminalId(NettyUtil.hex2a(vTerminalId));
        vSaleDto.setFechaTransaccion(NettyUtil.formatearFecha(NettyUtil.hex2a(vFechaTransaccion)));
        vSaleDto.setHoraTransaccion(NettyUtil.formatearHora(NettyUtil.hex2a(vHoraTransaccion)));
        //TODO: Cargar fecha actual sistema (VERIFICAR FECHA INEXACTA)
        vSaleDto.setCodigoRespuesta(NettyUtil.hex2a(vCodigoRespuesta));
        vSaleDto.setTipoCuenta(NettyUtil.hex2a(vTipoCuenta));
        vSaleDto.setUltimosDigitos(Integer.parseInt(NettyUtil.hex2a(vUltimosDigitos)));
        vSaleDto.setNumeroCuotasTransaccion(NettyUtil.hex2a(vNumeroCuotas));
        vSaleDto.setUltimosDigitosTransasccion(NettyUtil.hex2a(vUltimosDigitos));
        vSaleDto.setMensajeError(NettyUtil.cleanData(NettyUtil.hex2a(vMensajeError)));
        vSaleDto.setBinTarjeta(Integer.parseInt(NettyUtil.hex2a(vBinTarjeta)));
        vSaleDto.setBinTarjetaTransaccion(NettyUtil.hex2a(vBinTarjeta));
        vSaleDto.setTarjeta(NettyUtil.getMasked(Integer.parseInt(NettyUtil.hex2a(vBinTarjeta)), Integer.parseInt(NettyUtil.hex2a(vUltimosDigitos))));
        return vSaleDto;
    }
}
