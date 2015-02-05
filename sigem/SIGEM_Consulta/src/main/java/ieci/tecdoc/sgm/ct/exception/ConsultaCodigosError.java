/**
 * @author Javier Septién Arceredillo
 * 
 * Fecha de Creación: 30-may-2007
 */

package ieci.tecdoc.sgm.ct.exception;


/**
 * Códigos de error de excepciones del Sistema de Archivo Digital.
 */

public final class ConsultaCodigosError {


   public static final long EC_PREFIX = 20005000;

   public static final long EC_NIF_INCORRECTO = EC_PREFIX + 1;
   public static final long EC_NUMERO_EXPEDIENTE_INCORRECTO = EC_PREFIX + 2;
   public static final long EC_CARGAR_EXPEDIENTE = EC_PREFIX + 3;
   public static final long EC_GUID_HITO_INCORRECTO = EC_PREFIX + 4;
   public static final long EC_OBTENER_HITOS_EXPEDIENTE = EC_PREFIX + 5;
   public static final long EC_CARGAR_INTERESADO_EXPEDIENTE = EC_PREFIX + 6;
   public static final long EC_CARGAR_EXPEDIENTES = EC_PREFIX + 7;
   public static final long EC_CERTIFICADO_NO_ENCONTRADO = EC_PREFIX + 8;
   public static final long EC_CARGAR_FICHEROS_HITO = EC_PREFIX + 9;
   public static final long EC_FORMATO_FECHA_INCORRECTO = EC_PREFIX + 10;
   public static final long EC_OBTENER_HITO_ESTADO_EXPEDIENTE = EC_PREFIX + 11;
   public static final long EC_INSERTAR_FICHERO = EC_PREFIX + 12;
   public static final long EC_ELIMINAR_FICHERO = EC_PREFIX + 13;
   public static final long EC_INSERTAR_EXPEDIENTE = EC_PREFIX + 14;
   public static final long EC_ELIMINAR_EXPEDIENTE = EC_PREFIX + 15;
   public static final long EC_INSERTAR_HITO_ESTADO = EC_PREFIX + 16;
   public static final long EC_ELIMINAR_HITO_ESTADO = EC_PREFIX + 17;
   public static final long EC_INSERTAR_HITO_HISTORICO = EC_PREFIX + 18;
   public static final long EC_ELIMINAR_HITO_HISTORICO = EC_PREFIX + 19;
   public static final long EC_INSERTAR_INTERESADO = EC_PREFIX + 20;
   public static final long EC_ELIMINAR_INTERESADO = EC_PREFIX + 21;
   public static final long EC_INSERTAR_NOTIFICACION = EC_PREFIX + 22;
   public static final long EC_ELIMINAR_NOTIFICACION = EC_PREFIX + 23;
   public static final long EC_INSERTAR_PAGO = EC_PREFIX + 24;
   public static final long EC_ELIMINAR_PAGO = EC_PREFIX + 25;
   public static final long EC_INSERTAR_SUBSANACION = EC_PREFIX + 26;
   public static final long EC_ELIMINAR_SUBSANACION = EC_PREFIX + 27;
   public static final long EC_GET_NOTIFICACIONES = EC_PREFIX + 28;
   public static final long EC_GET_PAGOS = EC_PREFIX + 29;
   public static final long EC_GET_SUBSANACIONES = EC_PREFIX + 30;
   public static final long EC_OBTENER_URL_APORTACION = EC_PREFIX + 31;
   public static final long EC_OBTENER_URL_NOTIFICACION = EC_PREFIX + 32;
   public static final long EC_OBTENER_URL_PAGO = EC_PREFIX + 33;
   public static final long EC_ANEXAR_DOCUMENTO_HITO_ESTADO = EC_PREFIX + 34;
}