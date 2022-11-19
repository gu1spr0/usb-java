/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo.com.datec.pos.util;

/**
 *
 * @author iquispe
 */
public class Constants {
        // ESTADOS
    public static final String STATE_ACTIVE = "ACTIVO";
    public static final String STATE_INACTIVE = "INACTIVO";
    public static final String STATE_BLOCKED = "BLOQUEADO";
    public static final String STATE_DELETED = "ELIMINADO";
    public static final String STATE_SUCCESS = "OK";
    public static final String STATE_DENIED = "RECHAZADO";
    public static final String STATE_OTHER = "ERROR";

    //ESTADOS MENSAJES
    public static final String STATE_EXCEPCION = "0";
    public static final String STATE_CARGANDO = "1";
    public static final String STATE_TARJETA = "2";
    public static final String STATE_PIN = "3";
    public static final String STATE_PAY = "4";
    public static final String STATE_REALIZADO = "5";

    //FLUJO POS LITERAL
    public static final String FLOW_NONE="NONE";
    public static final String FLOW_CHIP="CHIP";
    public static final String FLOW_CHIP_MULTI="MULTICHIP";
    public static final String FLOW_CTL="CTL";
    public static final String FLOW_CTL_MULTI="MULTICTL";
    public static final String FLOW_DELETED="DELETED";
    public static final String FLOW_DELETED_MULTI="MULTIDELETED";
    public static final String FLOW_CLOSE="CLOSE";
    public static final String FLOW_CLOSE_MULTI="MULTICLOSE";
    public static final String FLOW_INIT="INIT";
    public static final String FLOW_RESET = "RESET";

    //FLUJO POS NUMERAL
    public static final int NUMBER_FLOW_NONE=0;
    public static final int NUMBER_FLOW_INIT=1;
    public static final int NUMBER_FLOW_CHIP=2;
    public static final int NUMBER_FLOW_CHIP_MULTI=3;
    public static final int NUMBER_FLOW_CTL=4;
    public static final int NUMBER_FLOW_CTL_MULTI=5;
    public static final int NUMBER_FLOW_DELETED=6;
    public static final int NUMBER_FLOW_DELETED_MULTI=7;
    public static final int NUMBER_FLOW_CLOSE=8;
    public static final int NUMBER_FLOW_CLOSE_MULTI=9;
    public static final int NUMBER_FLOW_RESET = 10;

    //DATA RESPUESTA VENTA
    public static final int BEGIN_COD_AUTORIZACION = 50;
    public static final int BEGIN_MONTO_COMPRA = 72;
    public static final int BEGIN_NUM_RECIBO = 106;
    public static final int BEGIN_RRN = 128;
    public static final int BEGIN_TERMINAL_ID = 162;
    public static final int BEGIN_FECHA_TRANSAC = 188;
    public static final int BEGIN_HORA_TRANSAC = 206;
    public static final int BEGIN_COD_RESPUESTA = 224;
    public static final int BEGIN_TIPO_CUENTA = 238;
    public static final int BEGIN_NUM_CUOTAS = 252;
    public static final int BEGIN_ULT_DIGITOS = 266;
    public static final int BEGIN_MSG_ERROR = 284;
    public static final int BEGIN_BIN_TARJETA = 432;

    public static final int TAM_COD_AUTORIZACION = 12;
    public static final int TAM_MONTO_COMPRA = 24;
    public static final int TAM_NUM_RECIBO = 12;
    public static final int TAM_RRN = 24;
    public static final int TAM_TERMINAL_ID = 16;
    public static final int TAM_FECHA_TRANSAC = 8;
    public static final int TAM_HORA_TRANSAC = 8;
    public static final int TAM_COD_RESPUESTA = 4;
    public static final int TAM_TIPO_CUENTA = 4;
    public static final int TAM_NUM_CUOTAS= 4;
    public static final int TAM_ULT_DIGITOS = 8;
    public static final int TAM_MSG_ERROR = 138;
    public static final int TAM_BIN_TARJETA = 12;

    //DATA RESPUESTA ANULACION
    public static final int AN_IAUTORIZACION = 52;
    public static final int AN_ICOMPRA = 74;
    public static final int AN_IRECIBO = 108;
    public static final int AN_IRRN = 130;
    public static final int AN_ITERMINAL = 164;
    public static final int AN_IFECHA = 190;
    public static final int AN_IHORA = 208;
    public static final int AN_IRESPUESTA = 226;
    public static final int AN_IDIGITOS = 240;
    public static final int AN_IERROR = 258;
    public static final int AN_IBIN = 406;

    public static final int AN_LAUTORIZACION = 12;
    public static final int AN_LCOMPRA = 24;
    public static final int AN_LRECIBO = 12;
    public static final int AN_LRRN = 24;
    public static final int AN_LTERMINAL = 16;
    public static final int AN_LFECHA = 8;
    public static final int AN_LHORA = 8;
    public static final int AN_LRESPUESTA = 4;
    public static final int AN_LDIGITOS = 8;
    public static final int AN_LERROR = 138;
    public static final int AN_LBIN = 12;

    //DATA RESPUESTA CIERRE CON TRANSACCION
    public static final int CT_IAUTORIZACION = 50;
    public static final int CT_IRESPUESTA = 72;

    public static final int CT_LAUTORIZACION = 12;
    public static final int CT_LRESPUESTA = 4;

    //DATA RESPUESTA INICIALIZACION
    public static final int IN_IRESPUESTA = 50;

    public static final int IN_LRESPUESTA = 4;

    // ENTITYS
    public static final String USUARIO = "Usuario";
    public static final String ROL = "Rol";
    public static final String RECURSO = "Recurso";
    public static final String RESOURCEGROUP = "Grupo de recursos";

    //SECRET KEY
    public static final String SECRET_KEY = "#Datec2022$Pos@#";
    public static final long TIME_KEY_USER = 3600000L;
    public static final long TIME_KEY_KIOSK = 1200000L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    // SECURITY
    public static final String DOM_SECURITY = "SECURITY";
    public static final String VAL_AES_KEY = "AES_KEY";
    //MESSAGE
    public static final String MSG_USER ="Bienvenido %s, iniciaste sesión!";
    public static final String MSG_CHIP = "Inserte/Deslice tarjeta porfavor!";
    public static final String MSG_CTL = "Acerque su tarjeta porfavor!";
    public static final String MSG_PIN = "Ingrese pin porfavor!";
    public static final String MSG_SUCCESS = "Transacción completada";
    public static final String MSG_INTENT = "Intento de conexión";
    public static final String INTENT_SEND = "Intento de envío de mensaje";
    public static final String ERROR_TRANS = "No se realizó, intente nuevamente!";
    public static final String ERROR_IN_CHIP = "Tarjeta no insertada o deslizada";
    public static final String ERROR_IN_CTL = "Sin Tarjeta";
    public static final String ERROR_IN_PIN = "Pin no ingresado";
    public static final String ERROR_USER = "Usuario no encontrado";
    public static final String ERROR_NODIS = "Terminal pos no encontrado";
    public static final String ERROR_NOTOP = "Operación no válida";
    public static final String ERROR_IN_AMOUNT = "Monto debe ser mayor a cero";
    public static final String ERROR_IN_NROTRA = "El numero de transacción debe ser un número mayor a cero";
    public static final String ERROR_IN_CONFIRM = "El valor de confirmación no válido";
    public static final String ERROR_IN_INIT = "Inicialización no confirmada";
    public static final String ERROR_ENTRADA = "Datos de entrada no válido";
    public static final String MSG_INI_INIT = "Iniciando inicialización";
    public static final String MSG_INI_CHIP = "Iniciando pago con chip";
    public static final String MSG_INI_CTL = "Iniciando pago contactless";
    public static final String MSG_INI_CANCEL = "Iniciando cancelación de transacción";
    public static final String MSG_INI_CLOSE = "Iniciando cierre de caja";
    public static final String MSG_PAY = "Procesando su pago";
    //ROLE
    public static final String ROLE_KIOSCO = "ROLE_KIOSCO";
    //TYPE DEVICE
    public static final String DEVICE_SINGLE = "SINGLE";
    public static final String DEVICE_MULTI = "MULTI";

    //ENDPOINTS WEBSOCKET
    public static final String ENDPOINT_USER = "/user";
    public static final String ENDPOINT_TOPIC = "/topic";
    public static final String ENDPOINT_QUEUE = "/queue";
    public static final String ENDPOINT_APP = "/app";
    public static final String ENDPOINT_POS = "/pos";
    public static final String ENDPOINT_INIT = "/init";
    public static final String ENDPOINT_CHIP = "/chip";
    public static final String ENDPOINT_CONTACTLESS = "/contactless";
    public static final String ENDPOINT_CLOSE = "/close";
    public static final String ENDPOINT_CANCEL = "/cancel";
    public static final String ENDPOINT_RESET = "/reset";
    public static final String ENDPOINT_NOTIFY = "/notify";
    public static final String ENDPOINT_MSG = "/msg";
}
