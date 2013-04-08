package es.ieci.tecdoc.fwktd.sir.api.manager;

import es.ieci.tecdoc.fwktd.dm.business.vo.InfoDocumentoVO;
import es.ieci.tecdoc.fwktd.server.manager.BaseManager;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;

/**
 * Interfaz para los managers de anexos de asientos registrales.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface AnexoManager extends BaseManager<AnexoVO, String> {

	/**
	 * Crea un anexo.
	 * @param idAsientoRegistral Identificador del asiento registral.
	 * @param anexoForm Información del anexo.
	 * @return Información del anexo creado.
	 */
	public AnexoVO saveAnexo(String idAsientoRegistral, AnexoFormVO anexoForm);

	/**
	 * Obtiene el contenido de un anexo.
	 *
	 * @param id
	 *            Identificador del anexo.
	 * @return Contenido del anexo.
	 */
	public byte[] getContenidoAnexo(String id);

	/**
	 * Obtiene la información del contenido de un anexo.
	 * 
	 * @param id
	 *            Identificador del anexo.
	 * @return Información del contenido del anexo.
	 */
	public InfoDocumentoVO getInfoContenidoAnexo(String id);
	
	/**
	 * Establece el contenido de un anexo
	 *
	 * @param id
	 *            Identificador del anexo.
	 * @param contenido
	 *            Contenido del anexo.
	 */
	public void setContenidoAnexo(String id, byte[] contenido);
	
	/**
	 * Establece el contenido de un anexo
	 *
	 * @param anexo
	 *            Información del anexo.
	 * @param contenido
	 *            Contenido del anexo.
	 */
	public void setContenidoAnexo(AnexoVO anexo, byte[] contenido);

	/**
	 * Elimina los anexos de un asiento registral.
	 *
	 * @param idAsientoRegistral
	 *            Identificador del asiento registral.
	 */
	public void deleteByIdAsientoRegistral(String idAsientoRegistral);
}
