package es.ieci.tecdoc.fwktd.sir.api.manager;

import java.io.File;
import java.util.List;

import es.ieci.tecdoc.fwktd.sir.api.vo.FicheroVO;

/**
 * Interfaz para los managers de compresión de contenidos.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface CompresionManager {

	/**
	 * Comprime un fichero.
	 *
	 * @param fichero
	 *            Información del fichero.
	 * @return Fichero comprimido.
	 */
	public File comprimirFichero(FicheroVO fichero);

	/**
	 * Comprime una lista de ficheros.
	 *
	 * @param ficheros
	 *            Lista ficheros.
	 * @return Fichero comprimido.
	 */
	public File comprimirFicheros(List<FicheroVO> ficheros);

	/**
	 * Descomprimir un fichero en un directorio.
	 *
	 * @param compressedFile
	 *            Fichero comprimido
	 * @param destDir
	 *            Directorio donde se descomprimirá el fichero.
	 */
	public void descomprimirFichero(File compressedFile, File destDir);
}
