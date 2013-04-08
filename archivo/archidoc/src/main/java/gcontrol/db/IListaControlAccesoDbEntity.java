package gcontrol.db;

import gcontrol.model.TipoListaControlAcceso;
import gcontrol.vos.ListaAccesoVO;

import java.util.List;

import common.db.IDBEntity;

/**
 * Entidad: <b>ASCALISTCA</b>
 * 
 * @author IECISA
 * 
 */
public interface IListaControlAccesoDbEntity extends IDBEntity {
	public abstract ListaAccesoVO insertListaAccesoVO(
			final ListaAccesoVO listaAccesoVO);

	public abstract void deleteListaAccesoVO(String id);

	public abstract ListaAccesoVO getListaAccesoVOXId(final String idLista);

	/**
	 * Obtiene las listas de control de acceso.
	 * 
	 * @return Listas de control de acceso.
	 */
	public List getListasControlAcceso();

	List getListadoListasControlAccesoByNombre(String nombre);

	/**
	 * Obtiene las listas de acceso filtradas por nombre y tipos
	 * 
	 * @param nombre
	 *            Filtro por nombre
	 * @param tipos
	 *            Filtro por tipos
	 * @return
	 */
	List getListadoListasControlAccesoByNombreYTipos(String nombre, int[] tipos);

	/**
	 * Obtiene las listas de control de acceso por tipo.
	 * 
	 * @param tipo
	 *            Tipo de la lista de control de acceso (
	 *            {@link TipoListaControlAcceso}).
	 * @return Listas de control de acceso.
	 */
	public List getListasControlAccesoByTipo(int tipo);

	ListaAccesoVO getListaControlAccesoByNombreYTipo(String nombre, int tipo);

	/**
	 * 
	 * @return Listas de control de acceso por ids
	 */
	public List getListasControlAcceso(String[] ids);

	/**
	 * @param listaAcceso
	 */
	public void updateListaAccesoVO(ListaAccesoVO listaAcceso);

	/**
	 * Elimina de la base de datos las listas de acceso indicadas
	 * 
	 * @param listasAcceso
	 *            Identificadores de lista de acceso
	 */
	public void eliminarListasAcceso(String[] listasAcceso);

	public ListaAccesoVO getListaControlAccesoByNombre(String nombre);

	/**
	 * Obtiene las listas de acceso de los productores de una serie.
	 * 
	 * @param idSerie
	 *            Identificador de la Serie
	 * @return Lista de {@link ListaAccesoVO}
	 */
	public List getListasControlAccesoProductoresSerie(String idSerie);
}