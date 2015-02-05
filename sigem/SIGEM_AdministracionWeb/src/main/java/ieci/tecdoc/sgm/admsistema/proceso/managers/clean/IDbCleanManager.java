package ieci.tecdoc.sgm.admsistema.proceso.managers.clean;

import java.util.Map;

import org.apache.log4j.Logger;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public interface IDbCleanManager {

	/**
	 * Limpia la base de datos
	 * @param logger Logger
	 * @param options Opciones para la limpieza de la base de datos.
	 * @return true si la base de datos se ha limpiado correctamente.
	 */
	public abstract boolean clean(Logger logger, Map options);
	
}
