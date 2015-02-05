/**
 *
 */
package salas.db;

import java.util.List;

import salas.vos.UsuarioArchivoSalasConsultaVO;

import common.db.IDBEntity;

/**
 * Entidad: <b>ASGSUSRCSARCH</b>
 * 
 * @author IECISA
 * 
 */
public interface IUsuarioArchivoSalasConsultaDBEntity extends IDBEntity {
	/**
	 * Inserta el registro
	 * 
	 * @param idUsuario
	 *            Cadena que define el Identificador del usuario
	 * @param idArchivo
	 *            Cadena que define el Identificador del Archivo
	 */
	public void insertar(
			UsuarioArchivoSalasConsultaVO usuarioArchivoSalasConsultaVO);

	/**
	 * Elimina el registro por idUsuario y idArchivo
	 * 
	 * @param idUsuario
	 *            Cadena que define el Identificador del usuario
	 * @param idArchivo
	 *            Cadena que define el Identificador del Archivo
	 */
	public void deleteById(String idUsuario, String idArchivo);

	/**
	 * Elimina los registros por idUsuario
	 * 
	 * @param idUsuario
	 *            Cadena que define el Identificador del usuario
	 */
	public void deleteByIdUsuario(String idUsuario);

	/**
	 * Elimina el registro para un usuario y los identificador del archivo
	 * seleccionados.
	 * 
	 * @param idUsuario
	 *            Cadena que define el Identificador del usuario
	 * @param idArchivo
	 *            Cadena que define el Identificador del Archivo
	 */
	public void deleteByIdUsuarioYArchivos(String idUsuario, String[] idsArchivo);

	/**
	 * Elimina todos los registros que coincidan con el identificador del
	 * archivo
	 * 
	 * @param idArchivo
	 *            Identificador del archivo
	 */
	public void deleteByIdArchivo(String idArchivo);

	/**
	 * Obtiene la lista de {@link UsuarioArchivoSalasConsultaVO} a los que
	 * pertenece un usuario.
	 * 
	 * @param idUsuario
	 *            Cadena que define el identificador del usuario
	 * @return Lista de {@link UsuarioArchivoSalasConsultaVO} a los que pertence
	 *         el usuario especificado.
	 */
	public List getArchivosByIdUsuarioSalaConsulta(String idUsuario);

	/**
	 * Obtiene la lista de {@link UsuarioArchivoSalasConsultaVO} a los que
	 * pertenece un usuario y están dentro de los ids de archivos.
	 * 
	 * @param idUsuario
	 *            Cadena que define el identificador del usuario
	 * @param Array
	 *            de cadenas que defiene los identificadores de los archivos a
	 *            filtrar.
	 * @return Lista de {@link UsuarioArchivoSalasConsultaVO} a los que pertence
	 *         el usuario especificado.
	 */
	public List getArchivosByIdUsuarioSalaConsultaInArchivos(String idUsuario,
			String[] idsArchivo);

	/**
	 * Comprueba si existe un registro para ese usuario e idArchivo
	 * 
	 * @param idUsuario
	 * @param idArchivo
	 * @return
	 */
	public boolean existe(String idUsuario, String idArchivo);

}
