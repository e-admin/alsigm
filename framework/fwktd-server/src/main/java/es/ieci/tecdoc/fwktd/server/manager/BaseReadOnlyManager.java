/*
 * 
 */
package es.ieci.tecdoc.fwktd.server.manager;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author IECISA
 *
 * @param <Entity>
 * @param <Id>
 */
public interface BaseReadOnlyManager<Entity, Id extends Serializable> {

	/**
	 * Método genérico para recuperar un objeto basándonos en su clase e
	 * identificador. En caso de que no exista ningún objeto para el
	 * identificador dado se lanzará una ObjectRetrievalFailureException.
	 * 
	 * @param id
	 *            identificador del objeto a recuperar
	 * @return el objeto recuperado
	 * @see org.springframework.orm.ObjectRetrievalFailureException
	 */
	public Entity get(Id anId);

	/**
	 * Método genérico para recuperar todas las entidades de un mismo tipo.
	 * Viene a ser lo mismo que recuperar todas las filas de una tabla de base
	 * de datos.
	 * 
	 * @return la lista de objetos recuperados
	 */
	public List<Entity> getAll();

	/**
	 * Método genérico para recuperar todas las entidades de un mismo tipo
	 * filtrando duplicados.
	 * <p>
	 * Para poder usar este método, debemos asegurarnos que las clases de
	 * nuestro modelo implementen correctamente los métodos equals()/hashCode()
	 * </p>
	 * 
	 * @return la lista de objetos recuperados
	 */
	public List<Entity> getAllDistinct();

	/**
	 * Devuelve el número de entidades de tipo Entity existentes. Para obtener el resultado delega en el dao asociado.
	 * 
	 * @return
	 */
	public int count();

	/**
	 * Comprueba la existencia de un objeto de tipo Entity con el identificador dado.
	 * 
	 * @param anId
	 *            identificador del objeto
	 * @return <code>true</code> si el objeto existe, <code>false</code> en caso
	 *         contrario
	 */
	public boolean exists(Id anId);

}