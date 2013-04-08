/**
 * @author Javier Septien Arceredillo
 * 
 * Fecha de Creación: 29-may-2007
 */

package ieci.tecdoc.sgm.ct.database.exception;

/**
 * Códigos de error de excepciones del Sistema de Archivo Digital.
 */

public final class DbErrorCodes {

   private DbErrorCodes() {

   }
   
   public static final long EC_PREFIX = 20004000;
   public static final long CT_OBTENER_TODOS_LOS_VALORES = EC_PREFIX + 1;
   public static final long CT_INSERTAR_TODOS_LOS_VALORES = EC_PREFIX + 2;
   public static final long CT_ANIADIR_VALOR = EC_PREFIX + 3;
   public static final long CT_BORRAR_TODOS_LOS_VALORES = EC_PREFIX + 4;   
   public static final long CT_CERRAR_CONEXION = EC_PREFIX + 5;
   public static final long CT_GUID_INCORRECTO = EC_PREFIX + 6;
   public static final long CT_CARGAR_EXPEDIENTE = EC_PREFIX + 7;
   public static final long CT_CARGAR_EXPEDIENTES_INTERESADO = EC_PREFIX + 8;
}