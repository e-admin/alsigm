package deposito.db;

import java.util.List;

import common.db.IDBEntity;

import deposito.vos.ElementoNoAsignableVO;

/**
 * Entidad: <b>ASGDELEMNOASIG</b>
 * 
 * @author IECISA
 * 
 */
public interface IElementoNoAsignableDBEntity extends IDBEntity {
	public ElementoNoAsignableVO loadElementoNoAsignable(String id);

	public List getIdsDescendientes(String idElemento);

	public void insertElemento(ElementoNoAsignableVO noAsignableVO);

	public void updateNoAsignable(ElementoNoAsignableVO noAsignableVO);

	public void delete(String id);

	public void delete(String[] ids);

	/**
	 * Recupera los elementos del dep�sito f�sico que son descendientes del
	 * suministrado
	 * 
	 * @param idPadre
	 *            Identificador de elemento no asignable
	 * @param idUbicacion
	 *            Identificador de ubicaci�n
	 * @return Lista de elementos del dep�sito f�sico que pertenecen a la
	 *         ubicaci�n que se indica y tienen como padre el elemento cuyo
	 *         identificador se suministra
	 */
	public List getByIdPadre(String idPadre, String idUbicacion);

	/**
	 * Recupera los elementos del dep�sito f�sico que son descendientes del
	 * suministrado
	 * 
	 * @param idPadre
	 *            Identificador de elemento no asignable
	 * @param idUbicacion
	 *            Identificador de ubicaci�n
	 * @param idTipoElemento
	 *            Identificador del tipo de elemento
	 * @return Lista de elementos del dep�sito f�sico que pertenecen a la
	 *         ubicaci�n que se indica y tienen como padre el elemento cuyo
	 *         identificador se suministra
	 */
	public List getByIdPadreTipo(String idPadre, String idUbicacion,
			String idTipoElemento);

	/**
	 * Recupera el n�mero de elementos no asignables que contiene una
	 * ubicaci�n
	 * 
	 * @param idUbicacion
	 *            Identificador de ubicaci�n
	 * @return N�mero de elementos no asignables que forman parte de la
	 *         estructura en la que que se organiza la ubicaci�n
	 */
	public int getNumeroElementosUbicacion(String idUbicacion);

	public List getByIdPadreMayorQueOrden(String idPadre, String idUbicacion,
			int numOrden);

	public List getByIdPadreMayorIgualQueOrden(String idPadre,
			String idUbicacion, int numOrden);

	public ElementoNoAsignableVO getByIdPadre(String idPadre,
			String idUbicacion, int orden);

	public ElementoNoAsignableVO getElementoByCodOrden(String codOrden,
			String idDeposito);

	/**
	 * Permite comprobar si un n�mero de orden est� en uso
	 * 
	 * @param padreIsUbicacion
	 *            Indica si el elemento padre es una ubicacion
	 * @param idDeposito
	 *            Identificador del deposito
	 * @param idElemPadre
	 *            Identificador del elemento padre
	 * @param numOrden
	 *            Numero de orden
	 * @return si el numero de orden esta usado o no
	 */
	public boolean isNumOrdenEnUso(boolean padreIsUbicacion, String idDeposito,
			String idElemPadre, String idTipoElemento, int numOrden);

	/**
	 * Obtiene el elemento No Asignable por su identificador
	 * 
	 * @param idNoAsignable
	 * @return
	 */
	public ElementoNoAsignableVO getById(String idNoAsignable);

	/**
	 * Obtiene el n�mero de no asignables del tipo tipo seleccionado.
	 * 
	 * @param idTipoElemento
	 *            Identificador del Tipo de Elemento
	 */
	public int getCountByTipoElemento(String idTipoElemento);

}