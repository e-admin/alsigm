/*
 * Created on 29-nov-2005
 *
 */
package ieci.tdw.ispac.ispacpublicador.business.exceptions;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Clase que se encarga de formatear errores al producirse una excepción.
 * @author Ildefonso Noreña
 *
 */
public class RenderException {

    public static void show(Logger logger, Throwable e, Level level){
        show(logger, e, null, level);
    }

    public static void show(Logger logger, Throwable e, String message, Level level){
        String error = e.getMessage();
        
        if (message != null)
            error = message+ " " + e.getMessage();
        
        if (level == Level.ERROR)
            logger.error(error, e);
        else 
            logger.warn(error, e);
    }

    
    public static void show(Logger logger, Throwable e){
        show(logger, e, null, Level.ERROR);
    }

    public static void show(Logger logger, Throwable e, String message){
        show(logger, e, message, Level.ERROR);
    }
}
