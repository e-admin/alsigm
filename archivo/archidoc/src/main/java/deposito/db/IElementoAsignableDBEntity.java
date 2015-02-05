package deposito.db;

import ieci.core.exception.IeciTdException;

import java.util.List;

import common.db.IDBEntity;
import common.exceptions.TooManyResultsException;

import deposito.vos.ElementoAsignableVO;
import deposito.vos.ElementoVO;

/**
 * Entidad: <b>ASGDELEMASIG</b>
 * 
 * @author IECISA
 * 
 */
public interface IElementoAsignableDBEntity extends IDBEntity {
	/**
	 * Recupera los elementos asignables que tienen por padre el elemento no
	 * asignable cuyo identificador se suministra
	 * 
	 * @param idElemento
	 *            Identificador de elemento no asignable
	 * @return Lista de elementos asignables cuyo padre es el elemento no
	 *         asignable que se indica
	 */
	public List loadAsignablesElements(String idElementoPadre);

	public List loadAsignablesElementsMayorQueOrden(String id, int numOrden);

	public List loadAsignablesElementsMayorIgualQueOrden(String id, int numOrden);

	public ElementoAsignableVO loadAsignableElement(String idElementoPadre,
			int numOrden);

	// public List getAllElementosAsignables();

	public void insertElemento(ElementoAsignableVO asignableVO);

	public ElementoAsignableVO loadElementoAsignable(String id);

	public void updateAsignable(ElementoAsignableVO asignableVO);

	public void delete(String id);

	public void delete(String[] ids);

	public String[] getIdsElementosAsignables(String[] idsPadres);

	public ElementoAsignableVO getElementoByCodOrden(String codOrden,
			String idDeposito);

	public List getElementosBetweenByCodOrden(ElementoVO elementoOrigenVO,
			ElementoVO elementoDestinoVO, String idFormato);

	public ElementoAsignableVO getElementoByMinimoCodOrden(String idDeposito,
			String codOrden, String idFormato);

	public List getLongitudElementosXRelacionEntrega(String idRelacion);

	public List getElementosAsignablesByAmbitosPadre(String[] idsAmbitoDeposito)
			throws IeciTdException, TooManyResultsException;

	/**
	 * Permite comprobar si un número de orden está en uso
	 * 
	 * @param idElemPadre
	 *            Identificador del elemento padre
	 * @param numOrden
	 *            Numero de orden
	 * @return si el numero de orden esta usado o no
	 */
	public boolean isNumOrdenEnUso(String idElemPadre, int numOrden);

	/**
	 * Obtiene el número de Tipo de Elemento
	 * 
	 * @param idTipoElemento
	 *            Identificador del tipo de Elemento
	 * @return
	 */
	public int getCountByTipoElemento(String idTipoElemento);

}