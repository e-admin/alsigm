package ieci.tecdoc.sgm.admsistema.proceso.managers;

import java.io.File;
import java.util.Map;

import org.apache.log4j.Logger;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */

public interface IRepoExportManager {

	/**
	 * Importa el repositorio de documentos.
	 * 
	 * @param logger
	 *            Logger
	 * @param repoDir
	 *            Directorio con los datos del repositorio.
	 * @param options
	 *            Opciones de importación del repositorio de documentos.
	 * @return true si el repositorio de documentos se ha importado
	 *         correctamente.
	 */
	public abstract boolean importRepository(Logger logger, File repoDir,
			Map options);

	/**
	 * Exporta el repositorio de documentos.
	 * 
	 * @param logger
	 *            Logger
	 * @param repository
	 *            Nombre del repositorio.
	 * @param exportDir
	 *            Directorio donde se exportará el repositorio.
	 * @param options
	 *            Opciones de exportación del repositorio de documentos.
	 * @return true si el repositorio de documentos se ha exportado
	 *         correctamente.
	 */
	public abstract boolean exportRepository(Logger logger, String repository,
			File exportDir, Map options);

	/**
	 * Elimina los repositorios documentales.
	 * 
	 * @param logger
	 *            Logger.
	 * @param repositories
	 *            Directorios de los repositorios documentales.
	 * @param options
	 *            Opciones de la eliminación de repositorios.
	 * @return true si los repositorios se han eliminado correctamente.
	 */
	public abstract boolean deleteRepositories(Logger logger,
			String[] repositories, Map options);

	/**
	 * Copia y limpia el repositorio de documentos de una exportación.
	 * 
	 * @param logger
	 *            Logger.
	 * @param srcDir
	 *            Directorio de origen
	 * @param destDir
	 *            Directorio de destino
	 */
	public abstract void cleanRepository(Logger logger, File srcDir,
			File destDir);
}
