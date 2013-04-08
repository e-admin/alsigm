package fondos.db;

import java.util.List;

import common.db.IDBEntity;

import fondos.vos.OrganoProductorVO;

/**
 * Entidad: <b>ASGFORGPROD</b>
 * 
 * @author IECISA
 * 
 */
public interface IOrganoProductorDbEntity extends IDBEntity {
	public abstract List getOrgProductorXIdEntidadProductora(
			String idEntidadProductora);

	public abstract OrganoProductorVO getOrgProductorXIdDescr(String idDescr);

	public OrganoProductorVO getOrgProductorXIdOrgano(String idOrgano);

	/**
	 * Recupera un órgano productor a pardir de la información del sistema
	 * gestor de organización del que fue importado
	 * 
	 * @param sistGestor
	 *            Identificador del sistema gestor de organización
	 * @param idEnSistGestor
	 *            Identificador del órgano en el sistema externo gestor de
	 *            organización
	 * @return Datos del productor
	 */
	public OrganoProductorVO getOrgProductorXSistemaGestor(String sistGestor,
			String idEnSistGestor);

	public abstract OrganoProductorVO insertOrgProductorVO(
			final OrganoProductorVO organismoProductorVO);

	public abstract void updateOrgProductor(
			final OrganoProductorVO orgProductorVO);

	/**
	 * Obtiene el número de referencias a un descriptor.
	 * 
	 * @param idDescriptor
	 *            Identificador del descriptor.
	 * @return Número de referencias.
	 */
	public int countReferencesDescriptor(String idDescriptor);

	/**
	 * Obtiene el número de referencias a descriptores de una lista descriptora.
	 * 
	 * @param idListaDescriptora
	 *            Identificador de la lista descriptora.
	 * @return Número de referencias.
	 */
	public int countReferencesDescriptorInList(String idListaDescriptora);

	/**
	 * Obtiene los órganos productores de una serie documental. Aquellos
	 * productores de la serie que son de otra naturaleza y no órganos, caso de
	 * haberlos no son devueltos
	 * 
	 * @param idSerie
	 *            Identificador de serie documental
	 * @return Lista de órganos productores de la serie
	 *         {@link OrganoProductorVO}
	 */
	public List getOrganosProductoresSerie(String idSerie);

	public void deleteOrganosProductores(String[] organosProductores);

	/**
	 * Unifica el campo <b>ASGFORGPROD.IDDESCR<b> con el idDescriptor de todos
	 * los idsAReemplazar
	 * 
	 * @param idDescriptor
	 *            Identificador del descriptor
	 * @param idsAReemplazar
	 *            Identificadores de los descriptores a reemplazar
	 */
	public void unificarDescriptor(String idDescriptor, String[] idsAReemplazar);

}