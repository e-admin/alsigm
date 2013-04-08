package organizacion.view.actions;

import organizacion.model.bi.GestionOrganizacionBI;
import organizacion.model.vo.OrganizacionVO;

import common.bi.ServiceRepository;
import common.view.POUtils;

public class OrganizacionPO extends OrganizacionVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	ServiceRepository services = null;
	GestionOrganizacionBI organizacionService = null;
	String path;

	public OrganizacionPO(OrganizacionVO organizacionVO,
			ServiceRepository services) {
		POUtils.copyVOProperties(this, organizacionVO);
		this.services = services;
		organizacionService = services.lookupGestionOrganizacionBI();
	}
}
