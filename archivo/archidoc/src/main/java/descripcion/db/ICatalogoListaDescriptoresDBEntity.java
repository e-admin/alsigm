package descripcion.db;

import java.util.List;

import common.db.IDBEntity;

import descripcion.vos.ListaDescrVO;

/**
 * Interfaz de comportamiento para la entidad de acceso al Catálogo de Listas de
 * Descriptores. Entidad: <b>ADCTLGLISTAD</b>
 */
public interface ICatalogoListaDescriptoresDBEntity extends IDBEntity {

	/**
	 * Obtiene la lista de listas descriptoras.
	 * 
	 * @return Listas descriptoras.
	 */
	public List getListasDescriptoras();

	/**
	 * Obtiene la lista de listas descriptoras.
	 * 
	 * @param ids
	 *            Array de identificadores de listas descriptoras.
	 * @return Listas descriptoras.
	 */
	public List getListasDescriptoras(String[] ids);

	/**
	 * Obtiene la lista de listas descriptoras con información extendida.
	 * 
	 * @return Listas descriptoras.
	 */
	public List getListasDescriptorasExt();

	/**
	 * Obtiene la lista descriptora.
	 * 
	 * @param id
	 *            Identificador de la lista descriptora.
	 * @return Lista descriptora.
	 */
	public ListaDescrVO getListaDescriptora(String id);

	/**
	 * Obtiene la lista descriptora con información extendida.
	 * 
	 * @param id
	 *            Identificador de la lista descriptora.
	 * @return Lista descriptora.
	 */
	public ListaDescrVO getListaDescriptoraExt(String id);

	/**
	 * Obtiene las listas descriptoras en función del tipo de descriptores.
	 * 
	 * @param tipoDescriptor
	 *            Tipo de descriptores ({@link descripcion.model.TipoDescriptor}
	 *            ).
	 * @return Listas descriptoras.
	 */
	public List getListasDescriptorasByTipoDescriptor(int tipoDescriptor);

	/**
	 * Obtiene las listas descriptoras en función del tipo de descriptores y la
	 * ficha.
	 * 
	 * @param tipoDescriptor
	 * @param idFicha
	 * @return Listas descriptoras.
	 */
	public List getListasDescrByTipoDescrOFicha(int tipoDescriptor,
			String idFicha);

	/**
	 * Crea una lista descriptora.
	 * 
	 * @param listaDescriptora
	 *            lista descriptora.
	 * @return Lista descriptora insertada.
	 */
	public ListaDescrVO insert(final ListaDescrVO listaDescriptora);

	/**
	 * Modifica la lista descriptora.
	 * 
	 * @param listaDescriptora
	 *            Lista descriptora.
	 */
	public void update(ListaDescrVO listaDescriptora);

	/**
	 * Elimina las listas descriptoras.
	 * 
	 * @param listaIds
	 *            Lista de identificadores de listas descriptoras.
	 */
	public void delete(String[] listaIds);

	/**
	 * Elimina la lista descriptora.
	 * 
	 * @param id
	 *            Identificador de la lista descriptora.
	 */
	public void delete(String id);

	/**
	 * Obtiene las listas descriptoras por tipo (ABIERTAS, CERRADAS, etc)
	 * 
	 * @param tipos
	 *            Tipos de listas
	 * @return Objetos de tipo {@link ListaDescrVO}
	 */
	public List getListasDescriptorasByTipos(int[] tipos);

}