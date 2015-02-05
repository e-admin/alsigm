package es.ieci.tecdoc.fwktd.dir3.api.manager;

import java.util.List;
/**
 * Interfaz para la generación de ficheros con el proceso de actualización o inicialización.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface GenerateScriptSQLManager {

	/**
	 * Método que genera un fichero SQL con los sentencias de inicialización del sistema
	 * @param fileXMLData - XML con los objetos a insertar en BBDD
	 *
	 */
	public void generateScriptInicializacion(String fileXMLData);

	/**
	 * Método que genera un fichero SQL con las sentencias de actualizacion
	 * @param fileXMLDataUpdate - XML con los objetos a actualizar
	 */
	public void generateScriptActualizacion(String fileXMLDataUpdate);

	/**
	 * Método que genera un array con las sentencias a realizar en un proceso de actualización
	 * @param fileXMLDataUpdate - XML con los objetos a actualizar
	 * @return Listado de sentencias (INSERT, UPDATE o DELETE) segun la operativa del objeto a tratar
	 */
	public List<String> generateSententesSQLUpdate(String fileXMLDataUpdate);
}
