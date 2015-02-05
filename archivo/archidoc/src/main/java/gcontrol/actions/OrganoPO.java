package gcontrol.actions;

import gcontrol.vos.ArchivoVO;
import gcontrol.vos.CAOrganoVO;
import gcontrol.vos.UsuarioVO;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import se.NotAvailableException;
import se.instituciones.GestorOrganismos;
import se.instituciones.GestorOrganismosFactory;
import se.instituciones.InfoOrgano;
import se.instituciones.TipoAtributo;
import se.instituciones.exceptions.GestorOrganismosException;
import se.usuarios.ServiceClient;

import common.Constants;
import common.MultiEntityConstants;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.util.StringUtils;
import common.view.POUtils;

public class OrganoPO extends CAOrganoVO {

	private static final Logger logger = Logger.getLogger(OrganoPO.class);
	private static final long serialVersionUID = 1L;
	ServiceRepository services = null;
	transient GestionSistemaBI sistemaBI = null;
	// transient GestionOrganizacionBI organismosBI = null;
	transient GestionControlUsuariosBI controlUsuariosBI = null;
	InfoOrgano organizacionVO = null;
	InfoOrgano organizacionCodigoVO = null;

	transient GestorOrganismos gestorOrganismosSE = null;

	ArchivoVO archivoReceptor = null;

	public OrganoPO(CAOrganoVO caOrgano, ServiceRepository services) {
		POUtils.copyVOProperties(this, caOrgano);
		this.services = services;
	}

	public ArchivoVO getArchivoReceptor() {
		if (archivoReceptor == null)
			archivoReceptor = getSistemaBI().getArchivo(getIdArchivoReceptor());
		return archivoReceptor;
	}

	private GestionSistemaBI getSistemaBI() {
		if (sistemaBI == null)
			sistemaBI = services.lookupGestionSistemaBI();
		return sistemaBI;
	}

	private GestionControlUsuariosBI getGestionControlUsuariosBI() {
		if (controlUsuariosBI == null) {
			controlUsuariosBI = services.lookupGestionControlUsuariosBI();
		}
		return controlUsuariosBI;
	}

	private Properties getParams() {

		Properties params = null;
		// Obtener la entidad para el usuario conectado
		ServiceClient serviceClient = services.getServiceClient();

		if ((serviceClient != null)
				&& (StringUtils.isNotEmpty(serviceClient.getEntity()))) {
			params = new Properties();
			params.put(MultiEntityConstants.ENTITY_PARAM,
					serviceClient.getEntity());
		}
		return params;
	}

	private GestorOrganismos getGestorOrganismosSE()
			throws GestorOrganismosException {
		if (gestorOrganismosSE == null) {
			Properties params = null;

			try {
				gestorOrganismosSE = GestorOrganismosFactory.getConnectorById(
						getSistExtGestor(), getParams());
			} catch (GestorOrganismosException e) {
				logger.error("Error al obtener el gestor de organismos "
						+ getSistExtGestor(), e);
				// throw e;
			}
		}
		return gestorOrganismosSE;
	}

	/**
	 * Obtiene el órgano de Organización asociado a este órgano en archivo.
	 * 
	 * @return
	 * @throws NotAvailableException
	 * @throws GestorOrganismosException
	 */
	public InfoOrgano getOrganizacion() {
		if (organizacionVO == null) {
			try {
				organizacionVO = getGestorOrganismosSE()
						.recuperarOrgano(TipoAtributo.IDENTIFICADOR_ORGANO,
								getIdOrgSExtGestor());
			} catch (GestorOrganismosException e) {
				logger.error(e);
			} catch (NotAvailableException e) {
				logger.error(e);
			}
		}
		return organizacionVO;
	}

	/**
	 * Obtiene el órgano de Organización asociado a este órgano en archivo.
	 * 
	 * @return
	 */
	public InfoOrgano getOrganizacionCodigo() {
		if (organizacionCodigoVO == null) {
			try {
				organizacionCodigoVO = getGestorOrganismosSE().recuperarOrgano(
						TipoAtributo.CODIGO_ORGANO, getCodigo());
			} catch (GestorOrganismosException e) {
				logger.error(e);
			} catch (NotAvailableException e) {
				logger.error(e);
			}
		}
		return organizacionCodigoVO;
	}

	public String getTextoOrganizacion() {
		if (getOrganizacion() != null) {
			return organizacionVO.getCodigo() + Constants.GUION
					+ organizacionVO.getNombre();
		} else {
			return null;
		}
	}

	public String getTextoOrganizacionCodigo() {
		if (getOrganizacionCodigo() != null) {
			return organizacionCodigoVO.getCodigo() + Constants.STRING_SPACE
					+ organizacionCodigoVO.getNombre();
		} else {
			return null;
		}
	}

	public boolean isVinculable() {
		if (isOrganoVigente() && getOrganizacion() == null
				&& getOrganizacionCodigo() != null) {
			return true;
		}
		return false;
	}

	public List<UsuarioVO> getUsuariosAsociados() {
		return getGestionControlUsuariosBI()
				.getUsuariosVigentesAsociadosAOrgano(this.getIdOrg());
	}
}
