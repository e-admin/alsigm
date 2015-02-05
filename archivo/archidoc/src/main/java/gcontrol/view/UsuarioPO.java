package gcontrol.view;

import gcontrol.actions.OrganoPO;
import gcontrol.vos.CAOrganoVO;
import gcontrol.vos.UsuarioOrganoVO;
import gcontrol.vos.UsuarioVO;

import java.util.Properties;

import org.apache.log4j.Logger;

import se.NotAvailableException;
import se.instituciones.GestorOrganismos;
import se.instituciones.GestorOrganismosFactory;
import se.instituciones.InfoOrgano;
import se.instituciones.archivo.invesdoc.vo.OrganoVO;
import se.instituciones.exceptions.GestorOrganismosException;
import se.usuarios.ServiceClient;
import se.usuarios.TipoUsuario;
import xml.config.Usuario;

import common.Constants;
import common.MultiEntityConstants;
import common.bi.GestionControlUsuariosBI;
import common.bi.ServiceRepository;
import common.db.DBUtils;
import common.util.StringUtils;
import common.view.POUtils;

/**
 * Datos de presentación de usuario
 */
public class UsuarioPO extends UsuarioVO {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UsuarioPO.class);

	private static final long serialVersionUID = 1L;

	ServiceRepository services = null;

	GestionControlUsuariosBI usuariosService = null;
	Usuario usuarioConfig = null;
	OrganoPO organoPO = null;
	UsuarioOrganoVO usuarioOrganoVO = null;
	GestorOrganismos gestorOrganismos = null;
	InfoOrganoPO organoExterno = null;

	public UsuarioPO(UsuarioVO usuarioVO, ServiceRepository services) {
		POUtils.copyVOProperties(this, usuarioVO);
		this.services = services;
		usuariosService = services.lookupGestionControlUsuariosBI();
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

	public String getNombreCompleto() {
		return new StringBuffer(StringUtils.isBlank(super.getApellidos()) ? ""
				: super.getApellidos())
				.append(StringUtils.isBlank(super.getApellidos()) ? "" : ", ")
				.append(super.getNombre()).toString();
	}

	private Usuario getTipoUsuario() {
		if (usuarioConfig == null) {
			Usuario usuarioXML = this.usuariosService
					.getConfigTipoUsuarioByIdTipo(getTipo());
			if (usuarioXML != null)
				usuarioConfig = usuarioXML;
		}
		return usuarioConfig;
	}

	public boolean isEstaHabilitado() {
		return DBUtils.getBooleanValue(getHabilitado());
	}

	public String getNombreLargoOrgano() {
		try{
		if (getOrganoEnArchivo() != null)
			return getOrganoEnArchivo().getNombreLargo();
		else
			return Constants.STRING_EMPTY;
		}catch (Exception e){
			logger.error(e);
			return Constants.GUION;
		}
	}

	public Boolean getVigenciaOrgano() {
		if (getOrganoEnArchivo() != null)
			return getOrganoEnArchivo().isOrganoVigente();
		else
			return null;
	}

	public OrganoPO getOrganoEnArchivo() {
		if (organoPO == null) {
			CAOrganoVO organoVO = new CAOrganoVO();
			try{
			 organoVO= usuariosService.getOrganoUsuarioEnArchivo(
					getId(), getTipo(), getIdUsrSistOrg());
			}catch(Exception e){
				logger.error("Error al obtener el órgano con id" + getId(), e);
				organoVO.setNombreLargo(e.getMessage());
			}
			organoPO = new OrganoPO(organoVO, services);
		}
		return organoPO;
	}

	private GestorOrganismos getGestorOrganismos() {
		if (gestorOrganismos == null) {
			try {
				// Obtener información de la entidad
				ServiceClient serviceClient = services.getServiceClient();

				gestorOrganismos = GestorOrganismosFactory
						.getConnectorByUserType(getTipo(), getParams());
			} catch (GestorOrganismosException e) {
				logger.error(e);
				return null;
			}
		}
		return gestorOrganismos;
	}

	public InfoOrganoPO getOrganoExterno() {
		if (organoExterno == null) {
			if (!usuariosService
					.isUsuarioDeSistemaOrganizacionInterno(getTipo())
					&& !usuariosService
							.isUsuarioSinSistemaOrganizacion(getTipo())) {
				InfoOrgano infoOrgano = null;
				try {
					if (getIdUsrSistOrg() != null)
						infoOrgano = getGestorOrganismos()
								.recuperarOrganodeUsuario(getIdUsrSistOrg());
				} catch (GestorOrganismosException e) {
					logger.error(e);
					return null;
				} catch (NotAvailableException e) {
					logger.error(e);
					return null;
				}
				if (infoOrgano != null) {
					Usuario infoTipoUsuario = getTipoUsuario();
					OrganoPO organoEnArchivo = getOrganoEnArchivo();
					if (organoEnArchivo != null) {
						// Obtener información de la entidad
						ServiceClient serviceClient = services
								.getServiceClient();

						// Obtener la entidad para el usuario conectado
						Properties params = null;

						if ((serviceClient != null)
								&& (StringUtils.isNotEmpty(serviceClient
										.getEntity()))) {
							params = new Properties();
							params.put(MultiEntityConstants.ENTITY_PARAM,
									serviceClient.getEntity());
						}

						organoExterno = new InfoOrganoPO(infoOrgano,
								infoTipoUsuario.getIdSistGestorOrg(),
								organoEnArchivo.getIdOrg(), usuariosService,
								services);
					}
				}
				return organoExterno;
			}
			return null;

		} else
			return organoExterno;
	}

	public UsuarioOrganoVO getInfoUsuarioEnOrgano() {
		if (usuarioOrganoVO == null) {
			usuarioOrganoVO = usuariosService.getInfoUsuarioEnOrgano(getId());
		}
		return usuarioOrganoVO;
	}

	public boolean isUsuarioConOrganoInterno() {
		if (TipoUsuario.ADMINISTRADOR.equals(getTipo())) {
			return true;
		} else {
			Usuario usuario = getTipoUsuario();
			if (usuario != null) {
				return usuariosService
						.isUsuarioDeSistemaOrganizacionInterno(usuario
								.getTipo());
			}
			return false;
		}
	}

	public boolean isUsuarioSinSistemaOrganizacion() {
		Usuario usuario = getTipoUsuario();
		if (usuario != null) {
			return usuariosService.isUsuarioSinSistemaOrganizacion(usuario
					.getTipo());
		}
		return false;
	}

}
