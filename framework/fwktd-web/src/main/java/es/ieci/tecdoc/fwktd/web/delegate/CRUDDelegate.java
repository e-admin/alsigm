package es.ieci.tecdoc.fwktd.web.delegate;

import es.ieci.tecdoc.fwktd.core.model.Entity;

/**
 * Interface para objetos delegate que dan servicio a operaciones CRUD (Create,
 * Retrieve, Update, Delete) sobre entidades de tipo <code>Entity</code>.
 * 
 * @author IECISA
 * 
 * @see Entity
 * @param <T>
 */
public interface CRUDDelegate<T extends Entity> {

	/**
	 * Crea una nueva entidad.
	 * 
	 * @param entity
	 *            la nueva entidad a insertar
	 * @return la entidad creada
	 */
	abstract T create(T entity);

	/**
	 * Elimina una entidad del sistema. Generalmente, de una base de datos o
	 * similar.
	 * 
	 * @param entity
	 *            la entidad a eliminar
	 */
	abstract void delete(String id);

	/**
	 * Actualiza la entidad que se especifica como parámetro
	 * 
	 * @param entity
	 *            la entidad a actualizar
	 * @return la entidad actualizada
	 */
	abstract T update(T entity);

	/**
	 * Recupera la entidad cuyo <code>id</code> se especifica como parámetro
	 * 
	 * @param id
	 *            el identificador de la entidad a recuperar
	 * @return la entidad recuperada
	 */
	abstract T retrieve(String id);
}
