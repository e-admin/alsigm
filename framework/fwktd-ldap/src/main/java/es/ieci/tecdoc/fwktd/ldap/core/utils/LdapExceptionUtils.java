package es.ieci.tecdoc.fwktd.ldap.core.utils;

import org.slf4j.Logger;

import es.ieci.tecdoc.fwktd.ldap.core.exception.LdapException;
import es.ieci.tecdoc.fwktd.ldap.core.messages.LdapErrorCodes;
import es.ieci.tecdoc.fwktd.ldap.core.messages.LdapMessages;

/**
 * Clase de utilidad para generar excepciones de error
 * @author Iecisa
 * @version $Revision: 78 $
 */
public final class LdapExceptionUtils {
    /**
     * Constructor privado
     */
    private LdapExceptionUtils(){

    }

    /**
     * Lanza una excepcion LDAP a partir del codigo de error
     * @param errCode Codigo de error
     * @param logger Logger para escribir el error y la excepcion
     * @throws LdapException
     */
    public static void generateErrorException(final String errCode, final Logger logger){
    	generateErrorException(errCode, null, logger, null);
    }

    /**
     * Lanza una excepcion LDAP a partir del codigo de error y sus parametros
     * @param errCode Codigo de error
     * @param errParams Parametros del error
     * @param logger Logger para escribir el error y la excepcion
     * @throws LdapException
     */
    public static void generateErrorException(final String errCode, final Object [] errParams, final Logger logger){
    	generateErrorException(errCode, null, logger, null);
    }

    /**
     * Lanza una excepcion LDAP a partir del codigo de error
     * @param errCode Codigo de error
     * @param logger Logger para escribir el error y la excepcion
     * @param exception Excepcion original
     * @throws LdapException
     */
    public static void generateErrorException(final String errCode, final Logger logger, final Exception exception){
    	generateErrorException(errCode, null, logger, exception);
    }

    /**
     * Lanza una excepcion LDAP a partir del codigo de error
     * @param errCode Codigo de error
     * @param errParams Parametros del error
     * @param logger Logger para escribir el error y la excepcion
     * @param exception Excepcion original
     * @throws LdapException
     */
    public static void generateErrorException(final String errCode, final Object [] errParams, final Logger logger, final Exception exception){
    	String msg = LdapMessages.formatMessage(LdapErrorCodes.BUNDLE, errCode, errParams);
    	if (exception!=null) {
    		logger.error(msg, exception);
    		throw new LdapException(errCode, msg, exception);
    	} else {
    		logger.error(msg);
    		throw new LdapException(errCode, msg);
    	}
    }

}
