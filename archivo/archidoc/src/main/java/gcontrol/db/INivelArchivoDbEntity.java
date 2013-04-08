package gcontrol.db;

import gcontrol.vos.NivelArchivoVO;

import java.util.List;

import common.db.IDBEntity;

/**
 * Interfaz para gestionar los niveles de archivo <br>
 * Entidad: <b>AGNIVELARCHIVO</b>
 * 
 * @author IECISA
 * 
 */
public interface INivelArchivoDbEntity extends IDBEntity {
	/**
	 * Obtencion de NivelArchivoVO por id
	 * 
	 * @param idNivelArchivo
	 * @return
	 */
	public abstract NivelArchivoVO getNivelArchivoXId(String idNivelArchivo);

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
	public abstract List getAll();

	/**
	 * Inserta el Nivel de Archivo
	 * 
	 * @param nivelArchivoVO
	 *            Datos del Nivel de Archivo
	 * @return NivelArchivoVO nivelArchivoVO insertado.
	 */
	public abstract NivelArchivoVO insert(final NivelArchivoVO nivelArchivoVO);

	/**
	 * Actualiza los datos del nivel de Archivo.
	 * 
	 * @param nivelArchivoVO
	 *            Datos del Nivel de archivo a actualizar
	 */
	public abstract void update(final NivelArchivoVO nivelArchivoVO);

	/**
	 * Elimina el Nivel de Archivo
	 * 
	 * @param nivelArchivoVO
	 *            Datos del Nivel de archivo a eliminar
	 */
	public abstract void delete(final NivelArchivoVO nivelArchivoVO);

	/**
	 * Obtiene un {@link NivelArchivoVO} cuyo nombre sea igual al nombre pasado
	 * por parámetro
	 * 
	 * @param String
	 *            nombre del nivelArchivo
	 * @return NivelArchivoVO nivelArchivoVO obtenido;
	 */
	public NivelArchivoVO getNivelArchivoXNombre(String nombre);

}
