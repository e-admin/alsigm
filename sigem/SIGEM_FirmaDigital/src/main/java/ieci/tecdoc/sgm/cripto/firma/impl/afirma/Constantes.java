package ieci.tecdoc.sgm.cripto.firma.impl.afirma;

/*
  $Id: Constantes.java,v 1.1 2007/09/11 10:39:16 cnavas Exp $
*/

final public class Constantes {

    // final public static 

    // valores de etiquetas
    final public static String TRUE="true";
    final public static String FALSE="false";

    final public static String MENOS_UNO="-1";
    final public static String CERO="0";
    final public static String UNO="1";


    /* los archivos xml que usamos cumplen esta especificacion */
    final public static String CABECERA_XML="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    /*
     * valores por defecto
     */
    final public static String PROVIDER="BC";


    /*
     * valores por defecto de firmar
     */
    final public static String KEY_STORE_TYPE="JKS";
    final public static String KEY_STORE_PROVIDER="SUN";
    //    final public static String PROVIDER2="";

    // XXX esto en produccion sobra
    final public static String KEY_STORE="keystore";
    final public static String PASSWORD="caracola";
    final public static String ALIAS="mykey";

    /*
     * valores por defecto de validacion
     */
    final public static String ALGORITHM="SHA1";

    /*
     * servicios
     * las entradas deben coincidir con las entidades (ENTITY) en el xml que utiliza spring
     */

    final public static String FIRMAR="firmar";
    final public static String VALIDACION="validacion";
    final public static String CIFRADO="cifrado";
    final public static String CONSOLIDACION="consolidacion";
    final public static String GENERAR_PDF="generarPdf";

}
