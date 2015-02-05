/**
 * 
 */
package es.ieci.tecdoc.fwktd.server.dao;

import java.io.Serializable;

/**
 * Interfaz genérico para acceso a datos.
 * 
 * @author IECISA
 * 
 * @param <Entity>
 *            tipo de entidad a gestionar
 * @param <Id>
 *            tipo de la clave primaria de la entidad
 */
public interface BaseDao<Entity, Id extends Serializable> extends
		BaseReadOnlyDao<Entity, Id> {

	/**
	 * Método genérico para guardar un objeto. Maneja tanto la inserción como la
	 * actualización.
	 * 
	 * @param entity
	 */
	Entity save(Entity anEntity);

	/**
	 * Método genérico para eliminar un objeto de tipo T e identificador id.
	 * 
	 * @param id
	 *            identificador del objeto a eliminar
	 */
	void delete(Id anId);

	/**
	 * Elimina todas los objetos de la clase gestionada por el presente DAO.
	 */
	void deleteAll();

	/**
	 * Método genérico para actualizar un objeto de la clase dada.
	 * 
	 * @param entity
	 *            objeto a actualizar
	 */
	Entity update(Entity anEntity);
}