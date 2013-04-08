package common.bi;

import gcontrol.model.ArchivosException;
import gcontrol.vos.NivelArchivoVO;

import java.util.List;

public interface GestionNivelesArchivoBI {
	/**
	 * Obtencion de NivelArchivoVO por id
	 * 
	 * @param idNivelArchivo
	 * @return
	 */
	public NivelArchivoVO getNivelArchivoXId(String idNivelArchivo);

	/**
	 * Obtencion de lista de niveles archivos a partir de lista de
	 * identificadores
	 * 
	 * @param idArchivo
	 *            Lista de identificadores de archivos
	 * @return Informacion {@link NivelArchivoVO} correspondiente a los archivos
	 *         solicitados
	 */
	public List getNivelesArchivoXId(String[] idNivelesArchivo);

	/**
	 * Obtencion de todos los niveles archivos existentes en la base de datos
	 * 
	 * @return
	 */
	public List getListaNivelesArchivo();

	/**
	 * Inserta el Nivel de Archivo
	 * 
	 * @param nivelArchivoVO
	 *            Datos del Nivel de Archivo
	 * @return NivelArchivoVO nivelArchivoVO insertado.
	 */
	// public NivelArchivoVO insertaNivelArchivo(final NivelArchivoVO
	// nivelArchivoVO);

	/**
	 * Actualiza los datos del nivel de Archivo.
	 * 
	 * @param nivelArchivoVO
	 *            Datos del Nivel de archivo a actualizar
	 */
	// public void actualizaNivelArchivo(final NivelArchivoVO nivelArchivoVO);

	public void actualizarListaNiveles(List listaNivelesArchivo,
			List listaNivelesArchivoEliminados) throws ArchivosException;

	// private void eliminarNivel (NivelArchivoVO nivelArchivoVO) throws
	// ArchivosException;
	/**
	 * Obtiene un {@link NivelArchivoVO} cuyo nombre sea igual al nombre pasado
	 * por parámetro
	 * 
	 * @param String
	 *            nombre del nivelArchivo
	 * @return NivelArchivoVO nivelArchivoVO obtenido;
	 */
	public NivelArchivoVO getNivelArchivoXNombre(String nombre);

	/**
	 * Modifica el campo archivoAsociado de todos los niveles de archivo
	 * {@link NivelArchivoVO} de listaNiveles, asignándole el valor true si
	 * tiene archivo asociado y false en caso contrario
	 * 
	 * @param listaNiveles
	 *            . Lista de niveles
	 * @param listaArchivos
	 *            . Lista de archivos
	 * @return Lista de niveles con el campo archivoAsociado modificado
	 */
	public List setNivelesArchivoAsociado(List listaNiveles, List listaArchivos);
}
