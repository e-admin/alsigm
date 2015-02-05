package gcontrol.view;

import gcontrol.model.NombreOrganoFormat;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import se.NotAvailableException;
import se.instituciones.GestorOrganismos;
import se.instituciones.GestorOrganismosFactory;
import se.instituciones.InfoOrgano;
import se.instituciones.exceptions.GestorOrganismosException;
import se.usuarios.ServiceClient;

import common.MultiEntityConstants;
import common.bi.GestionControlUsuariosBI;
import common.bi.ServiceRepository;
import common.util.StringUtils;

/**
 * Información para presentación de un órgano.
 */
public class InfoOrganoPO implements InfoOrgano {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Logger de la clase */
	private static final Logger logger = Logger.getLogger(InfoOrganoPO.class);
	InfoOrgano infoOrgano = null;
	String idSistGestor = null;
	String idOrganoInterno = null;
	GestionControlUsuariosBI usuariosService = null;
	ServiceRepository services = null;
	List jerarquiaOrgano = null;

	public InfoOrganoPO(InfoOrgano infoOrgano, String idSistGestorOrg,
			String idOrganoInterno, GestionControlUsuariosBI usuariosService,
			ServiceRepository services) {
		this.infoOrgano = infoOrgano;
		this.usuariosService = usuariosService;
		this.idSistGestor = idSistGestorOrg;
		this.idOrganoInterno = idOrganoInterno;
		this.services = services;

	}

	public String getIdOrganoInterno() {
		return idOrganoInterno;
	}

	public void setIdOrganoInterno(String idOrganoInterno) {
		this.idOrganoInterno = idOrganoInterno;
	}

	public String getId() {
		return infoOrgano.getId();
	}

	public String getNombre() {
		return infoOrgano.getNombre();
	}

	public String getCodigo() {
		return infoOrgano.getCodigo();
	}

	public int getNivel() {
		return infoOrgano.getNivel();
	}

	public String getIdPadre() {
		return infoOrgano.getIdPadre();
	}

	public String getStringOrganization() {
		return usuariosService.getOrganizationInfoString(idSistGestor,
				infoOrgano.getId());
	}

	public List getAncestorOrganizationList() {
		if (jerarquiaOrgano == null)
			try {
				jerarquiaOrgano = usuariosService.getOrganizationInfoInArchivo(
						idSistGestor, infoOrgano.getId());
			} catch (GestorOrganismosException goe) {
				logger.error("Error obteniendo jerarquia del organo", goe);
			} catch (NotAvailableException nae) {
				logger.error("Error obteniendo jerarquia del organo", nae);
			}
		return jerarquiaOrgano;
	}

	public String getNombreLargo() {
		String nombre = null;

		try {
			// Obtener información de la entidad
			ServiceClient serviceClient = services.getServiceClient();

			// Obtener la entidad para el usuario conectado
			Properties params = null;

			if ((serviceClient != null)
					&& (StringUtils.isNotEmpty(serviceClient.getEntity()))) {
				params = new Properties();
				params.put(MultiEntityConstants.ENTITY_PARAM,
						serviceClient.getEntity());
			}

			GestorOrganismos gestor = GestorOrganismosFactory.getConnectorById(
					idSistGestor, params);
			nombre = NombreOrganoFormat.formatearNombreLargo(infoOrgano,
					gestor.recuperarOrganosAntecesores(infoOrgano.getId(), 0));
		} catch (Exception e) {
			logger.warn("Error al obtener el nombre largo", e);
		}

		return nombre;
	}
}