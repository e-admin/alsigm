package descripcion.db;

import java.util.List;

import common.db.IDBEntity;

import descripcion.vos.TablaValidacionVO;

/**
 * Entidad: <b>ADCTLGTBLVLD</b>
 * 
 * @author IECISA
 * 
 */
public interface ITablaValidacionDBEntity extends IDBEntity {

	/**
	 * Obtiene la lista de tablas de validación.
	 * 
	 * @return Lista de tablas de validación.
	 */
	public abstract List getTablasValidacion();

	/**
	 * Obtiene la tabla de validación.
	 * 
	 * @param id
	 *            Identificador de la tabla de validación.
	 * @return Tabla de validación.
	 */
	public TablaValidacionVO getTablaValidacion(String id);

	TablaValidacionVO getTablaValidacionByNombre(String nombre);

	/**
	 * Crea una tabla de validación.
	 * 
	 * @param tablaValidacion
	 *            Tabla de validación.
	 * @return Tabla de validación insertada.
	 */
	public TablaValidacionVO insert(TablaValidacionVO tablaValidacion);

	/**
	 * Modifica la tabla de validación.
	 * 
	 * @param tablaValidacion
	 *            Tabla de validación.
	 */
	public void update(TablaValidacionVO tablaValidacion);

	/**
	 * Elimina las tablas de validación.
	 * 
	 * @param listaIds
	 *            Lista de identificadores de tablas de validación.
	 */
	public void delete(String[] listaIds);

	/**
	 * Elimina la tabla de validación.
	 * 
	 * @param id
	 *            Identificador de la tabla de validación.
	 */
	public void delete(String id);

}