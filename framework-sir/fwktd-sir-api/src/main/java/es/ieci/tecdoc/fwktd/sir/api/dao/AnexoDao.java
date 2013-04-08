package es.ieci.tecdoc.fwktd.sir.api.dao;

import java.util.List;

import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;

/**
 * Interfaz de los DAOs de anexos de asientos registrales.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface AnexoDao extends BaseDao<AnexoVO, String> {

//	/**
//	 * Obtiene el contenido de un anexo.
//	 *
//	 * @param id
//	 *            Identificador del anexo.
//	 * @return Contenido del anexo.
//	 */
//	public byte[] getContenidoAnexo(String id);
//
//	/**
//	 * Establece el contenido de un anexo
//	 *
//	 * @param id
//	 *            Identificador del anexo.
//	 * @param contenido
//	 *            Contenido del anexo.
//	 * @param hash
//	 *            Código hash del contenido.
//	 */
//	public void setContenidoAnexo(String id, byte[] contenido, byte[] hash);

	/**
	 * Obtiene el UID del anexo en el gestor documental.
	 *
	 * @param id
	 *            Identificador del anexo.
	 * @return UID del anexo en el gestor documental
	 */
	public String getUIDGestorDocumental(String id);

	/**
	 * Actualiza el UID del anexo en el gestor documental.
	 *
	 * @param id
	 *            Identificador del anexo.
	 * @param uidGestorDocumental
	 *            UID del anexo en el gestor documental.
	 */
	public void updateUIDGestorDocumental(String id, String uidGestorDocumental);

//	/**
//	 * Elimina los anexos de un asiento registral.
//	 *
//	 * @param idAsientoRegistral
//	 *            Identificador del asiento registral.
//	 */
//	public void deleteByIdAsientoRegistral(String idAsientoRegistral);

	/**
	 * Obtiene los identificadores de los anexos de un asiento registral.
	 *
	 * @param idAsientoRegistral
	 *            Identificador del asiento registral.
	 */
	public List<String> getIdsByIdAsientoRegistral(String idAsientoRegistral);

}
