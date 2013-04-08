package organizacion.view.actions;

import organizacion.model.bi.GestionOrganizacionBI;
import organizacion.model.vo.OrganizacionVO;
import organizacion.model.vo.UserOrganoVO;

import common.bi.ServiceRepository;
import common.util.StringUtils;
import common.view.POUtils;

public class UserOrganoPO extends UserOrganoVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	ServiceRepository services = null;
	GestionOrganizacionBI organosService = null;
	String nombreOrgano;

	public UserOrganoPO(UserOrganoVO userOrganoVO, ServiceRepository services) {
		POUtils.copyVOProperties(this, userOrganoVO);
		this.services = services;
		organosService = services.lookupGestionOrganizacionBI();
	}

	public String getNombreOrgano() {
		if (StringUtils.isEmpty(nombreOrgano)) {
			OrganizacionVO organizacionVO = organosService
					.getOrganizacionById(this.getIdOrgano());
			if (organizacionVO != null)
				this.nombreOrgano = organizacionVO.getNombre();
		}
		return nombreOrgano;
	}
}
