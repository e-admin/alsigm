package deposito.db;

import java.util.List;

import common.db.IDBEntity;

import deposito.vos.TipoElementoVO;

/**
 * Entidad: <b>ASGDTIPOELEMENTO</b>
 * 
 * @author IECISA
 * 
 */
public interface ITipoElementoDBEntity extends IDBEntity {

	/**
	 * Elimina el Tipo de Elemento
	 * 
	 * @param idTipoElemento
	 *            Identificador del Tipo de Elemento
	 */
	public void deleteTipoElemento(String idTipoElemento);

	/**
	 * Inserta el Tipo de Element
	 * 
	 * @param tipoElemento
	 */
	public void insertTipoElemento(final TipoElementoVO tipoElemento);

	/**
	 * Actualiza los datos del Tipo de Elemento
	 * 
	 * @param tipoElemento
	 */
	public void updateTipoElemento(TipoElementoVO tipoElemento);

	/**
	 * Obtiene la lista de tipos de elmento
	 * 
	 * @param idTipoElementoPadre
	 * @param exclude
	 *            Identificadores de los elementos a excluir
	 * @return Lista de {@link TipoElementoVO}
	 */
	public List getTiposElemento(String idTipoElementoPadre, String[] exclude);

	/**
	 * Obtiene el Tipo de Elemento
	 * 
	 * @param idTipoElemento
	 * @return
	 */
	public TipoElementoVO getTipoElemento(String idTipoElemento);

	/**
	 * Comprueba si el elemento tiene hijos asociados.
	 * 
	 * @param idTipoElemento
	 * @return
	 */
	public boolean hasChildsAsignables(String idTipoElemento);

	/**
	 * Comprueba si el elemento tiene hijos asociados.
	 * 
	 * @param idTipoElemento
	 * @return
	 */
	public boolean hasChilds(String idTipoElemento);

	/**
	 * @param tipoElementoVO
	 * @return
	 */
	public TipoElementoVO getTipoElemento(TipoElementoVO tipoElementoVO);
}