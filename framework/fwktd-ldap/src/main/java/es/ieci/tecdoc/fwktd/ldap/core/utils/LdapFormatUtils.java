package es.ieci.tecdoc.fwktd.ldap.core.utils;

import es.ieci.tecdoc.fwktd.ldap.core.constants.LdapEngineConstants;

/**
 * Clase de utilidad para formateo de atributos
 * @author Iecisa
 * @version $Revision: 115 $
 */
public final class LdapFormatUtils {

    /**
     * Modo de log debug
     */
    private static final int BITWISE_AND = 0xff;

    /**
     * Constructor privado
     */
    private LdapFormatUtils(){

    }

    /**
     * Permite formatear un GUID binario
     * @param engine Tipo de Ldap
     * @param guidObj Guid a formatear
     * @return Guid formateado
     */
    public static String formatGuid(final String engine, final Object guidObj)
    {
    	String guidStr = null;
    	if (LdapEngineConstants.ENGINE_ACTIVE_DIRECTORY.equals(engine)) {
    		guidStr = formatAdGuid((byte[]) guidObj);
    	} else {
    		guidStr = guidObj.toString();
    	}

    	return guidStr;

    }

    /**
     * Permite formatear un GUID binario
     * @param guidObj Guid a formatear
     * @return Guid formateado
     */
    private static String formatAdGuid(final byte[] guidObj) {
        StringBuffer guidStr = new StringBuffer();
        int len;
        String byteStr;

        len = guidObj.length;

        for (int i = 0; i < len; i++) {
            byteStr = Integer.toHexString(guidObj[i] & BITWISE_AND);
            if (byteStr.length() == 1) {
                byteStr = "0" + byteStr;
            }

            guidStr.append(byteStr);
        }

        return guidStr.toString();
    }

}
