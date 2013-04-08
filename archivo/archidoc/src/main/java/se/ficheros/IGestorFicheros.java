package se.ficheros;

import java.util.List;
import java.util.Properties;

import se.ficheros.exceptions.GestorFicherosException;
import docelectronicos.vos.DocDocumentoExtVO;

/**
 * Interface para el sistema gestor de ficheros
 */
public interface IGestorFicheros {

	/**
	 * Inicializa con los parámetros de configuración.
	 * 
	 * @param params
	 *            Parámetros de configuración.
	 * @throws GestorFicherosException
	 *             si ocurre algún error.
	 */
	public void initialize(Properties params);

	/**
	 * Permite obtener un fichero
	 * 
	 * @param id
	 *            Id del fichero
	 * @return array de bytes con el contenido
	 * @throws Exception
	 *             Si se produce algún error
	 */
	public byte[] retrieveFile(String id) throws Exception;

	/**
	 * Permite almacenar un fichero
	 * 
	 * @param idLista
	 *            Id de la lista en la que se quiere almacenar el fichero
	 * @param ext
	 *            Extensión del fichero
	 * @param contenido
	 *            Contenido del fichero
	 * @return Id del fichero
	 * @throws Exception
	 *             Si se produce algún error
	 */
	public String storeFile(DocDocumentoExtVO documentoExtVO) throws Exception;

	/**
	 * Permite eliminar un fichero
	 * 
	 * @param idFichero
	 *            Id del fichero
	 * @throws Exception
	 *             Si se produce algún error
	 */
	public void deleteFile(String idFichero) throws Exception;

	/**
	 * Obtiene la lista de identificadores de fichero en el sistema de
	 * almacenamiento que contienen la cadena pasada como parámetro y cuyo id
	 * está en la lista de identificadores de documentos pasados como parámetro
	 * 
	 * @return Lista de Ids de fichero
	 * @throws Exception
	 */
	public List<String> findFileByContent(String searchString, List docsIds)
			throws Exception;
}
