package common.bi;

import gcontrol.model.ArchivosException;
import gcontrol.vos.ArchivoVO;

import java.util.List;

public interface GestionArchivosBI {
	/**
	 * Obtencion de ArchivoVO por id
	 * 
	 * @param idNivelArchivo
	 * @return
	 */
	public ArchivoVO getArchivoXId(String idArchivo);

	/**
	 * Obtencion de lista de archivos a partir de lista de identificadores
	 * 
	 * @param idArchivo
	 *            Lista de identificadores de archivos
	 * @return Informacion {@link ArchivoVO} correspondiente a los archivos
	 *         solicitados
	 */
	public List getArchivosXId(Object[] idArchivo);

	/**
	 * Obtencion de lista de archivos a partir del codigo de Archivo
	 * 
	 * @param idArchivo
	 *            Lista de identificadores de archivos
	 * @return Informacion {@link ArchivoVO} correspondiente a los archivos
	 *         solicitados
	 */
	public ArchivoVO getArchivosXCodArchivo(String codArchivo);

	/**
	 * Obtencion de todos los archivos existentes en la base de datos
	 * 
	 * @return
	 */
	public List getListaArchivos();

	/**
	 * Inserta el de Archivo
	 * 
	 * @param ArchivoVO
	 *            Datos del de Archivo
	 * @return ArchivoVO ArchivoVO insertado.
	 */
	public ArchivoVO insertarArchivo(final ArchivoVO archivoVO);

	/**
	 * Actualiza los datos del de Archivo.
	 * 
	 * @param ArchivoVO
	 *            Datos del de archivo a actualizar
	 */
	public void actualizarArchivo(final ArchivoVO archivoVO);

	/**
	 * Obtiene la lista de Archivos que pueden se receptores (Los que tienen
	 * orden mayor)
	 * 
	 * @param orden
	 *            Orden del archivo actual
	 * @return Lista de Archivos cuyo orden de nivel es mayor
	 */
	public List getPosiblesArchivosReceptores(String orden);

	/**
	 * Obtiene la lista de Archivos que pueden se receptores a partir de una
	 * lista de remitentes
	 * 
	 * @param ltArchivosRemitentes
	 *            Lista de archivos remitentes
	 * @return Lista de Archivos receptores
	 */
	public List getArchivosReceptores(List ltArchivosRemitentes);

	/**
	 * Obtiene la lista de Archivos que pueden se receptores a partir de uno
	 * remitente
	 * 
	 * @param idArchivoRemitente
	 *            Id del archivo remitente
	 * @return Lista de Archivos cuyo orden de nivel es mayor
	 */
	public List getArchivosReceptores(String idArchivoRemitente);

	/**
	 * Obtiene la lista de Archivos que pueden ser receptores en una
	 * extraordinaria
	 * 
	 * @param idArchivoReceptorDefecto
	 *            Id del archivo receptor por defecto
	 * @return Lista de posibles archivos receptores
	 */
	public List getArchivosReceptoresExtraordinaria(
			String idArchivoReceptorDefecto);

	/**
	 * Elimina el Archivo Seleccionado
	 * 
	 * @param archivoVO
	 *            ArchivoVO a eliminar
	 */
	public void eliminarArchivo(ArchivoVO archivoVO) throws ArchivosException;

	public List getArchivosXNivel(String idNivel);

	public List getArchivosXIdReceptorDefecto(String idArchivo);

	/**
	 * Devuelve todos los archivos que tengan el tipo de signaturacion pasada
	 * como parametro
	 * 
	 * @param tipoSignaturacion
	 * @param idArchivo
	 * @return {List ArchivoVO}
	 */
	public List getArchivosXTipoSignaturacion(String tipoSignaturacion,
			String idArchivo);
}
