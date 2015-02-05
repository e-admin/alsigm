package es.ieci.tecdoc.fwktd.ldap.core.utils;

import org.slf4j.Logger;

import es.ieci.tecdoc.fwktd.ldap.core.messages.LdapErrorCodes;
import es.ieci.tecdoc.fwktd.ldap.core.messages.LdapMessages;

/**
 * Clase de utilidad para generar log de sentencias de debug
 * @author Iecisa
 * @version $Revision: 112 $
 */
public final class LdapLogDebugUtils {
    /**
     * Constructor privado
     */
    private LdapLogDebugUtils(){

    }

    /**
     * Escribe en el log pasado como parametro la informacion de debug
     * @param errCode Codigo de error
     * @param logger Logger para escribir la informacion de debug
     */
    public static void generateDebug(final String errCode, final Logger logger){
        generateDebug(errCode, null, logger, null);
    }

    /**
     * Escribe en el log pasado como parametro la informacion de debug con sus parametros
     * @param errCode Codigo de error
     * @param errParams Parametros del error
     * @param logger Logger para escribir la informacion de debug
     */
    public static void generateDebug(final String errCode, final Object [] errParams, final Logger logger){
        generateDebug(errCode, errParams, logger, null);
    }

    /**
     * Escribe en el log pasado como parametro la informacion de debug
     * @param errCode Codigo de error
     * @param logger Logger para escribir la informacion de debug
     * @param exception Excepcion original
     */
    public static void generateDebug(final String errCode, final Logger logger, final Exception exception){
        generateDebug(errCode, null, logger, exception);
    }

    /**
     * Escribe en el log pasado como parametro la informacion de debug con sus parametros
     * @param errCode Codigo de error
     * @param errParams Parametros del error
     * @param logger Logger para escribir la informacion de debug
     * @param exception Excepcion original
     */
    public static void generateDebug(final String errCode, final Object [] errParams, final Logger logger, final Exception exception){
        String msg = LdapMessages.formatMessage(LdapErrorCodes.BUNDLE, errCode, errParams);
    	if (exception!=null) {
    		logger.debug(msg, exception);
    	} else {
    		logger.debug(msg);
    	}
    }

}
