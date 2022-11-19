/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.com.datec.pos.dto;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author iquispe
 */
@Setter
@Getter
@ToString
public class SaleDto {
    private String codAutorizacion;
    private BigDecimal montoCompra;
    private String montoCompraTransaccion;
    private String numeroRecibo;
    private String rrn;
    private String terminalId;
    private String fechaTransaccion;
    private String horaTransaccion;
    private Date fechaLocal;
    private String codigoRespuesta;
    private String tipoCuenta;
    private String numeroCuotasTransaccion;
    private String tipoCuentaTransaccion;
    private int ultimosDigitos;
    private String ultimosDigitosTransasccion;
    private String mensajeError;
    private String binTarjetaTransaccion;
    private int binTarjeta;
    private String tarjeta;
}
