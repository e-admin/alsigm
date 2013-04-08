package ieci.tecdoc.sgm.catalogo_tramites.delegate.orgdestinatarios;

import ieci.tecdoc.sgm.core.services.catalogo.CatalogoTramitesExcepcion;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.rpadmin.RPAdminException;

/**
 *
 * Interface para los Organos destinatarios
 * @author IECISA
 *
 */
public interface OrganosDestinatariosDelegate {

	/**
	 * Permite crear y actualizar los órganos destinatarios a partir de una unidad administrativa de registro
	 * @param code Codigo de la unidad administrativa
	 * @param createUpdateSelectedOrg indica si se crea o actualiza el nodo seleccionado
	 * @param entidad entidad en la que se quieren crear/actualizar los organos destinatarios
	 */
	public void createUpdateOrganosDestinatariosForUnidadAdministrativa(String code, boolean createUpdateSelectedOrg, Entidad entidad)
			throws RPAdminException, CatalogoTramitesExcepcion;
}
