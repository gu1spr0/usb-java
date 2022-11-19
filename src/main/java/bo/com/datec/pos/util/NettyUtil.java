/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.com.datec.pos.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iquispe
 */
public class NettyUtil {
    public static String bytesToHex(byte[] bytes) {
        char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        System.err.println("[TAREA]: Conversion BYTES a HEX = " + new String(hexChars));
        return new String(hexChars);
    }
    public static String hex2a(String hexx) {
        String result = "";
        char[] charArray = hexx.toCharArray();
        for (int i = 0; i < charArray.length; i = i + 2) {
            String st = "" + charArray[i] + "" + charArray[i + 1];
            char ch = (char) Integer.parseInt(st, 16);
            result = result + ch;
        }
        System.err.println("[TAREA]: Conversion HEXA a CADENA = " + result);
        return result;
    }
    public static String asciiToHex(String str) {
        char[] ch = str.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char c : ch) {
            builder.append(Integer.toHexString((int) c).toUpperCase());
        }
        System.err.println("[TAREA]: Conversion ASCII a HEXA = " + builder.toString());
        return builder.toString();

    }
    public static String xor(String str) {
        byte[] data = str.getBytes(StandardCharsets.UTF_8);
        byte x = 0;
        int tam = data.length;
        for (byte datum : data) {
            x ^= datum;
        }
        String hex = "0" + Integer.toHexString(x);
        String xo = hex.substring(hex.length() - 2, hex.length());
        System.err.println("[TAREA]: Valor xor = " + xo);
        return xo;
    }
    public static boolean isNaN(BigDecimal v) {
        return (v != v);
    }
    public static String validarMonto(BigDecimal monto) {
        String montos = String.valueOf(monto.setScale(2, RoundingMode.HALF_UP));
        System.err.println("MONTO REDONDEADO=" + montos);
        String[] n = montos.split("\\.");
        System.err.println("[0]=" + n[0]);
        System.err.println("[1]=" + n[1]);
        String num = n[0] + n[1];
        System.err.println("NUMERO DESCOMPUESTO:" + num);
        num = "000000000000" + num;
        final String substring = num.substring(num.length() - 12);
        System.err.println("[TAREA]: Validando monto = " + substring);
        return substring;

    }
    public static String validarRef(int pId) {
        String recibo = String.valueOf(pId);
        recibo = "000000" + recibo;
        final String substring = recibo.substring(recibo.length() - 6);
        System.err.println("[TAREA]: Validando referencia para anulacion = " + substring);
        return substring;
    }
    public static String formatearFecha(String pFecha) {
        String mes = pFecha.substring(0, 2);
        String dia = pFecha.substring(2, 4);
        return dia.concat("/").concat(mes);
    }
    public static String formatearHora(String pHora) {
        String hora = pHora.substring(0, 2);
        String minutos = pHora.substring(2, 4);
        return hora.concat(":").concat(minutos);
    }
    public static String cleanData(String pParameter) {
        String data = pParameter.trim();
        return (data == null || data.isEmpty()) ? null : data;
    }
    public static Date getGlobalDate(String fecha, String hora) {
        Calendar c = Calendar.getInstance();
        String[] fechas = fecha.split("/");
        String[] horas = hora.split(":");
        c.set(c.get(Calendar.YEAR), Integer.parseInt(fechas[1]), Integer.parseInt(fechas[0]), Integer.parseInt(horas[0]), Integer.parseInt(horas[1]));
        return c.getTime();
    }
    public static String getMasked(int pBinTarjeta, int pUltimosDigitos) {
        String vBinTarjeta = String.valueOf(pBinTarjeta);
        String vUltimosDigitos = String.valueOf(pUltimosDigitos);
        return vBinTarjeta+"XXXXXX"+vUltimosDigitos;
    }
}
