package gcontrol.db;

import gcontrol.vos.DestinatarioPermisosListaVO;
import gcontrol.vos.PermisosListaVO;

import java.util.List;

import common.db.IDBEntity;

/**
 * Entidad: <b>ASCAPERMLISTCA</b>
 * 
 * @author IECISA
 * 
 */
public interface IPermisosListaDbEntity extends IDBEntity {
	public abstract PermisosListaVO insertPermisosListaVO(
			final PermisosListaVO permisosListaVO);

	/**
	 * Borra todos los permisos de la lista
	 * 
	 * @param id
	 */
	public abstract void deletePermisosListaVOXIdListaCA(String id);

	/**
	 * borra los permisos en la lista que existen para los elementos pasados por
	 * parametro
	 * 
	 * @param idLista
	 * @param idDest
	 */
	public abstract void deletePermisosLista(String idLista,
			int tipoDestinatarios, String[] idDestinatarios);

	/**
	 * Obtiene las listas de acceso para un grupo de destinatarios.
	 * 
	 * @param destinatariosPermisosLista
	 *            Lista de destinatarios ({@link DestinatarioPermisosListaVO}).
	 * @return Listas de acceso ({@link PermisosListaVO});
	 */
	public List getListasAcceso(List destinatariosPermisosLista);

	/**
	 * 
	 * @param idLista
	 * @param tipoDestino
	 *            @link gcontrol.model.TipoDestinatario
	 * @return
	 */
	public List getPermisosXIdListaAccesoYTipo(String idLista, int tipoDestino);

	public PermisosListaVO getPermisosListaVO(PermisosListaVO permisosListaVO);

	public List getPermisosXIdListaIdDestino(String idLista, String idDestino);

	public List getPermisosXIdLista(String idLista);

	List getPermisosListaAcceso(String idListaAcceso,
			List destinatariosPermisosLista);

	public int getCountPermisosXIdLista(String idLista);
}