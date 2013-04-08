/**
 * @author José Antonio Nogales
 * 
 * Fecha de Creación: 30-may-2007
 */

package ieci.tecdoc.sgm.geolocalizacion.exception;


/**
 * Códigos de error de excepciones del Sistema de Archivo Digital.
 */

public final class GeoLocalizacionCodigosError {


   public static final long EC_PREFIX = 200002000;

   public static final long EC_OBTENER_PLANO_COORDENADAS = EC_PREFIX + 1;
   public static final long EC_OBTENER_PLANO_REFERENCIA_CATASTRAL = EC_PREFIX + 2;
   public static final long EC_OBTENER_PLANO_ID_VIA = EC_PREFIX + 3;
   public static final long EC_OBTENER_PLANO_ID_NUMERO_POLICIA = EC_PREFIX + 4;
   public static final long EC_OBTENER_PLANOS_PUBLICADOS = EC_PREFIX + 5;
   public static final long EC_VALIDAR_VIA = EC_PREFIX + 6;
   public static final long EC_VALIDAR_NUMERO = EC_PREFIX + 7;
   public static final long EC_VALIDAR_DIRECCION_POSTAL = EC_PREFIX + 8;
   public static final long EC_NOMBRE_VIA_INVALIDO = EC_PREFIX + 9;
   public static final long EC_VIAS_NO_ENCONTRADAS = EC_PREFIX + 10;
   public static final long EC_OBTENER_PROVINCIAS = EC_PREFIX + 11;
   public static final long EC_OBTENER_MUNICIPIOS = EC_PREFIX + 12;
   public static final long EC_OBTENER_TIPOS_VIA = EC_PREFIX + 13;
}