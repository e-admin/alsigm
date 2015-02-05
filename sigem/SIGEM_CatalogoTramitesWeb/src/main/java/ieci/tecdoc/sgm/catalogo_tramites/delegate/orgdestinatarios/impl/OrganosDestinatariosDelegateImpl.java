package ieci.tecdoc.sgm.catalogo_tramites.delegate.orgdestinatarios.impl;

import ieci.tecdoc.sgm.catalogo_tramites.delegate.orgdestinatarios.OrganosDestinatariosDelegate;
import ieci.tecdoc.sgm.core.services.catalogo.CatalogoTramitesExcepcion;
import ieci.tecdoc.sgm.core.services.catalogo.OrganoDestinatario;
import ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.rpadmin.Organizacion;
import ieci.tecdoc.sgm.core.services.rpadmin.OrganizacionBean;
import ieci.tecdoc.sgm.core.services.rpadmin.Organizaciones;
import ieci.tecdoc.sgm.core.services.rpadmin.RPAdminException;
import ieci.tecdoc.sgm.core.services.rpadmin.ServicioRPAdmin;

/**
 *
 * Implementacion del delegate de organos destinatarios
 * @author IECISA
 *
 */
public class OrganosDestinatariosDelegateImpl implements OrganosDestinatariosDelegate {

	/**
	 * {@inheritDoc}
	 * @see ieci.tecdoc.sgm.catalogo_tramites.delegate.orgdestinatarios.OrganosDestinatariosDelegate#createUpdateOrganosDestinatariosForUnidadAdministrativa(java.lang.String, boolean, ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public void createUpdateOrganosDestinatariosForUnidadAdministrativa(String codigo, boolean createUpdateSelectedOrg, Entidad entidad) throws RPAdminException, CatalogoTramitesExcepcion {
		if (!CODIGO_NODO_RAIZ.equals(codigo)){
			OrganizacionBean organizacionBean = servicioRPAdmin.obtenerOrganizacionByCode(codigo, entidad);
			if (createUpdateSelectedOrg){
				OrganoDestinatario organoDestinatario = null;
				try {
					organoDestinatario = servicioCatalogoTramites.getAddressee(codigo, entidad);
				} catch (CatalogoTramitesExcepcion e) {
				}
				if (organoDestinatario==null){
					organoDestinatario = new OrganoDestinatario(codigo, organizacionBean.getNombre());
					servicioCatalogoTramites.addAddressee(organoDestinatario, entidad);
				} else {
					organoDestinatario.setDescription(organizacionBean.getNombre());
					servicioCatalogoTramites.updateAddressee(organoDestinatario, entidad);
				}
			} 
			
			if (organizacionBean!=null){
				Organizaciones organizaciones = servicioRPAdmin.obtenerHijosOrganizacion(organizacionBean.getId(), entidad);
				if ((organizaciones!=null)&&(organizaciones.count()>0)){
					Organizacion objOrg = null;
				    String codigoHijo = null;
					for (int i = 0; i < organizaciones.count(); i++ ) {
				    	objOrg = (Organizacion)organizaciones.get(i);
				        codigoHijo = objOrg.getUid();
				        createUpdateOrganosDestinatariosForUnidadAdministrativa(codigoHijo, entidad);
				    }
				}
			}
		} else {
			Organizaciones organizaciones = servicioRPAdmin.obtenerHijosOrganizacion(UnidadesAdministrativasTreeConstants.UNIDADES_ADMINISTRATIVAS_ROOT_NODE, entidad);
			if ((organizaciones!=null)&&(organizaciones.count()>0)){
				Organizacion objOrg = null;
			    String codigoHijo = null;
				for (int i = 0; i < organizaciones.count(); i++ ) {
			    	objOrg = (Organizacion)organizaciones.get(i);
			        codigoHijo = objOrg.getUid();
			        createUpdateOrganosDestinatariosForUnidadAdministrativa(codigoHijo, entidad);
			    }
			}
		}
	}

	/**
	 * Permite crear y actualizar los órganos destinatarios a partir de una unidad administrativa de registro
	 * @param codigo Codigo de la unidad administrativa
	 * @param entidad entidad en la que se quieren crear/actualizar los organos destinatarios
	 */
	private void createUpdateOrganosDestinatariosForUnidadAdministrativa(String codigo, Entidad entidad) throws RPAdminException, CatalogoTramitesExcepcion {
		OrganoDestinatario organoDestinatario = null;
		try {
			organoDestinatario = servicioCatalogoTramites.getAddressee(codigo, entidad);
		} catch (CatalogoTramitesExcepcion e) {
		}
		
		OrganizacionBean organizacionBean = servicioRPAdmin.obtenerOrganizacionByCode(codigo, entidad);
		if (organoDestinatario==null){
			organoDestinatario = new OrganoDestinatario(codigo, organizacionBean.getNombre());
			servicioCatalogoTramites.addAddressee(organoDestinatario, entidad);
		} else {
			organoDestinatario.setDescription(organizacionBean.getNombre());
			servicioCatalogoTramites.updateAddressee(organoDestinatario, entidad);
		}
		
		Organizaciones organizaciones = servicioRPAdmin.obtenerHijosOrganizacion(organizacionBean.getId(), entidad);
		if ((organizaciones!=null)&&(organizaciones.count()>0)){
			Organizacion objOrg = null;
            String codigoHijo = null;
			for (int i = 0; i < organizaciones.count(); i++ ) {
            	objOrg = (Organizacion)organizaciones.get(i);
                codigoHijo = objOrg.getUid();
                createUpdateOrganosDestinatariosForUnidadAdministrativa(codigoHijo, entidad);
            }
		}
	}
	
	/**
	 * @param servicioCatalogoTramites Servicio de catalogo
	 * @param servicioRPAdmin Servicio de administracion de registro presencial
	 */
	public OrganosDestinatariosDelegateImpl(
			ServicioCatalogoTramites servicioCatalogoTramites,
			ServicioRPAdmin servicioRPAdmin) {
		super();
		this.servicioCatalogoTramites = servicioCatalogoTramites;
		this.servicioRPAdmin = servicioRPAdmin;
	}

	public ServicioCatalogoTramites getServicioCatalogoTramites() {
		return servicioCatalogoTramites;
	}

	public void setServicioCatalogoTramites(
			ServicioCatalogoTramites servicioCatalogoTramites) {
		this.servicioCatalogoTramites = servicioCatalogoTramites;
	}

	public ServicioRPAdmin getServicioRPAdmin() {
		return servicioRPAdmin;
	}

	public void setServicioRPAdmin(ServicioRPAdmin servicioRPAdmin) {
		this.servicioRPAdmin = servicioRPAdmin;
	}

	private ServicioCatalogoTramites servicioCatalogoTramites = null;
	private ServicioRPAdmin servicioRPAdmin = null;
	
	private static final String CODIGO_NODO_RAIZ = "0";

}
