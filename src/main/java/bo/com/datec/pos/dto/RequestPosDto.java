/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo.com.datec.pos.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author iquispe
 */
@Setter
@Getter
public class RequestPosDto {
    private Long idCommerce;
    private Long idBranch;
    private Long idKiosk;
    private Long idDevice;
    private String ip;
    private int numberCommerce;
    private int flujo;
    private String strFlujo;
    private boolean confirm;
    private String montoTransaccion;
    private int numberTransaction;
    private BigDecimal monto;
    private int paso;
    private int tamaño;
    private String referenciaAnulacion;
    private String username;
}
