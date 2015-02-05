package ieci.tecdoc.sgm.admsistema.proceso.managers;

import java.io.File;
import java.util.Map;

import org.apache.log4j.Logger;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */

public interface IDbExportManager {

	/**
	 * Crea la base de datos.
	 * 
	 * @param logger
	 *            Logger
	 * @param dbName
	 *            Nombre de la base de datos.
	 * @param options
	 *            Opciones de creación de la base de datos.
	 * @return true si la base de datos se ha creado correctamente.
	 */
	public abstract boolean createDatabase(Logger logger, String dbName,
			Map options);

	/**
	 * Importa la base de datos.
	 * 
	 * @param logger
	 *            Logger
	 * @param dbName
	 *            Nombre de la base de datos.
	 * @param importFile
	 *            Fichero para la importación de datos.
	 * @param options
	 *            Opciones de importación de la base de datos.
	 * @return true si la base de datos se ha importado correctamente.
	 */
	public abstract boolean importDatabase(Logger logger, String dbName,
			File importFile, Map options);

	/**
	 * Limpia la base de datos.
	 * 
	 * @param logger
	 *            Logger
	 * @param dbName
	 *            Nombre de la base de datos.
	 * @param options
	 *            Opciones de limpieza de la base de datos.
	 * @return true si la base de datos se ha limpiado correctamente.
	 */
	public abstract boolean cleanDatabase(Logger logger, String dbName,
			Map options);

	/**
	 * Exporta la base de datos.
	 * 
	 * @param logger
	 *            Logger
	 * @param dbName
	 *            Nombre de la base de datos.
	 * @param exportFile
	 *            Fichero para la exportación de datos.
	 * @param options
	 *            Opciones de exportación de la base de datos.
	 * @return true si la base de datos se ha exportado correctamente.
	 */
	public abstract boolean exportDatabase(Logger logger, String dbName,
			File exportFile, Map options);

	/**
	 * Actualiza la información del repositorio documental en la base de datos.
	 * 
	 * @param logger
	 *            Logger.
	 * @param dbName
	 *            Nombre de la base de datos que contiene la información del
	 *            repositorio.
	 * @param options
	 *            Opciones de actualización de la base de datos
	 * @return true si la base de datos se ha actualizado correctamente.
	 */
	public abstract boolean updateRepoInfo(Logger logger, String dbName, Map options);
	

	/**
	 * Elimina la base de datos.
	 * 
	 * @param logger
	 *            Logger
	 * @param dbName
	 *            Nombre de la base de datos.
	 * @param options
	 *            Opciones de eliminación de la base de datos.
	 * @return true si la base de datos se ha eliminado correctamente.
	 */
	public abstract boolean dropDatabase(Logger logger, String dbName,
			Map options);

}