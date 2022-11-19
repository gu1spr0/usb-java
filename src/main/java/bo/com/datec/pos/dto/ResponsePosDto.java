/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.com.datec.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author iquispe
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class ResponsePosDto {
    private String code;
    private String message;
    private boolean success;
    private Object data;

    public ResponsePosDto(String pCode, String pMessage, boolean pSuccess, Object pData) {
        this.code = pCode;
        this.message = pMessage;
        this.success = pSuccess;
        this.data = pData;
    }
    public ResponsePosDto(String pMessage, boolean pSuccess, Object pData) {
        this.message = pMessage;
        this.success = pSuccess;
        this.data = pData;
    }
}
