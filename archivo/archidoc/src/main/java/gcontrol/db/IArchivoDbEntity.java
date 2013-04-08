package gcontrol.db;

import gcontrol.vos.ArchivoVO;

import java.util.List;

import common.db.IDBEntity;

/**
 * Entidad: <b>AGARCHIVO</b>
 * 
 * @author IECISA
 * 
 */
public interface IArchivoDbEntity extends IDBEntity {
	/**
	 * Obtencion de archivoVO por idArchivo
	 * 
	 * @param idArchivo
	 * @return
	 */
	public abstract ArchivoVO getArchivoXId(String idArchivo);

	/**
	 * Obtencion de lista de archivos a partir de lista de identificadores
	 * 
	 * @param idArchivo
	 *            Lista de identificadores de archivos
	 * @return Informacion {@link ArchivoVO} correspondiente a los archivos
	 *         solicitados
	 */
	public List getArchivosXId(Object[] idsArchivo);

	/**
	 * Obtencion de ArchivoVO por codigoArchivo
	 * 
	 * @param codArchivo
	 * @return
	 */
	public abstract ArchivoVO getArchivoXCodArchivo(String codArchivo);

	/**
	 * Obtencion de todos los archivos existentes en la base de datos
	 * 
	 * @return
	 */
	public abstract List getAll();

	public abstract void update(ArchivoVO archivoVO);

	public abstract ArchivoVO insert(ArchivoVO archivoVO);

	public abstract List getArchivosConOrdenNivelMayor(String orden);

	public abstract void delete(ArchivoVO archivoVO);

	public abstract List getArchivosXNivel(String idNivel);

	/**
	 * Obtiene los archivos que tienen como receptor por defecto el idArchivo
	 * 
	 * @param idArchivo
	 *            Identificador del Archivo
	 * @return Lista de los Archivos que cumplen las condiciones.
	 */
	public abstract List getArchivosXIdReceptorDefecto(String idArchivo);

	/**
	 * Obtiene los archivos receptores posibles para un archivo remitente
	 * 
	 * @param orden
	 *            Orden seleccionado.
	 * @return Lista de Archivos que cumplen las condiciones
	 */
	public List getArchivosReceptores(String idArchivoRemitente);

	/**
	 * Devuelve todos los archivos que tiene el tipo de signaturacion y el
	 * idArchivo pasados como parametros siempre y cuando este id sea distinto
	 * de null.
	 * 
	 * @param tipoSignaturacion
	 * @param idArchivo
	 * @return {List ArchivoVO}
	 */
	public List getArchivosXTipoSignaturacion(String tipoSignaturacion,
			String idArchivo);

}