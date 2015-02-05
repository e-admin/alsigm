/**
 *
 */
package solicitudes.consultas.vos;

import javax.servlet.http.HttpServletRequest;

import salas.vos.UsuarioSalasConsultaVO;
import se.usuarios.AppPermissions;
import se.usuarios.AppUser;
import solicitudes.consultas.ConsultasConstants;
import solicitudes.consultas.forms.ConsultaForm;

import common.ConfigConstants;
import common.Constants;
import common.util.StringUtils;
import common.vos.BaseVO;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class ConsultaViewVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ConsultaViewVO(HttpServletRequest request, ConsultaForm consultaForm) {

		AppUser appUser = (AppUser) request.getSession().getAttribute(
				Constants.USUARIOKEY);

		this.tipoEntidad = consultaForm.getTipoentconsultora();

		if (appUser
				.hasAnyPermission(new String[] { AppPermissions.GESTION_SOLICITUDES_CONSULTAS })) {
			this.mostrarRadioTipoUsuario = true;
		}

		if (isEsInvestigador()) {

			if (StringUtils.isNotEmpty(consultaForm.getIdusrsolicitante())) {
				this.permitidoAddTemas = true;
			}

			this.keySolicitante = ConsultasConstants.LABEL_SOLICITANTE;
			this.mostrarCampoOrgano = true;
			this.mostrarSelectSolicitante = true;

			if (consultaForm.getAddTema() != null
					&& consultaForm.getAddTema().booleanValue()) {
				this.mostrarCampoAddTema = true;
			} else {
				this.mostrarBotonAddTema = true;
			}

			if (appUser.hasPermissionGestionSolicitudesConsultas()) {
				if (StringUtils.isNotEmpty(consultaForm.getIdarchivo())) {
					this.mostrarFiltroUsuarios = true;
				}
				this.selectSolicitanteEditable = true;

				if (ConfigConstants.getInstance().getMostrarSalas()) {
					this.mostrarCheckVerUsuarioSala = true;
				}
			} else {

				UsuarioSalasConsultaVO usuarioSalasConsultaVO = appUser
						.getUsuarioSalasConsultaVO();

				if (ConfigConstants.getInstance().getMostrarSalas()
						&& usuarioSalasConsultaVO != null) {
					this.mostrarCheckUsuariosSala = true;
				}

			}

		} else if (isEsOrgano()) {
			this.keySolicitante = ConsultasConstants.LABEL_REPRESENTANTE;
			this.mostrarCampoOrgano = true;
			this.organoEditable = true;
			this.mostrarCampoSolicitante = true;
			this.selectSolicitanteEditable = true;

		} else if (isEsCiudadano()) {
			this.keySolicitante = ConsultasConstants.LABEL_SOLICITANTE;
			this.selectSolicitanteEditable = true;
			this.mostrarCampoSolicitante = true;
		}

		// Comprobar si hay reserva
		if (consultaForm.getTienereserva() != null
				&& Boolean.TRUE.equals(consultaForm.getTienereserva())) {
			this.mostrarDatosReserva = true;
		} else {
			this.mostrarDatosReserva = false;
		}

		if (StringUtils.isNotEmpty(consultaForm.getId())) {
			this.esEdicion = true;
		}

	}

	public boolean isEsCiudadano() {
		if (tipoEntidad != null
				&& tipoEntidad.intValue() == ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_CIUDADANO_INT) {
			return true;
		}
		return false;
	}

	public boolean isEsOrgano() {
		if (tipoEntidad != null
				&& tipoEntidad.intValue() == ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_ORGANO_EXTERNO_INT) {
			return true;
		}
		return false;
	}

	public boolean isEsInvestigador() {
		if (tipoEntidad != null
				&& tipoEntidad.intValue() == ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_INVESTIGADOR_INT) {
			return true;
		}
		return false;
	}

	private Integer tipoEntidad = null;

	/**
	 * Indicador para mostrar el radio de tipo de usuario - Ciudadano - Órgano -
	 * Investigador
	 */
	private boolean mostrarRadioTipoUsuario = false;

	/**
	 * Indicador para mostar el textbox de solicitante
	 */
	private boolean mostrarCampoSolicitante = false;

	private boolean mostrarSelectSolicitante = false;

	/**
	 * Indicador para mostrar el campo órgano
	 */
	private boolean mostrarCampoOrgano = false;

	/**
	 * Indicador de órgano editable por el usuario
	 */
	private boolean organoEditable = false;

	/**
	 * Indicador de usuario Editable por el usuario
	 */
	private boolean selectSolicitanteEditable = false;

	/**
	 * Indicador de mostrar datos de reserva.
	 */
	private boolean mostrarDatosReserva = false;

	/**
	 * Indicador para mostar el botón de añadir nuevos motivos
	 */
	private boolean mostrarBotonAddTema = false;

	/**
	 * Idicador si se permite añadir nuevos temas
	 */
	private boolean permitidoAddTemas = false;

	/**
	 * Indicador para mostar el campo de añadir motivo
	 */
	private boolean mostrarCampoAddTema = false;

	private String keySolicitante = ConsultasConstants.LABEL_SOLICITANTE;

	private boolean mostrarFiltroUsuarios = false;

	private boolean mostrarCheckUsuariosSala = false;

	private boolean mostrarCheckVerUsuarioSala = false;

	/**
	 * Indica si se esta en modo edición
	 */
	private boolean esEdicion = false;

	public Integer getTipoEntidad() {
		return tipoEntidad;
	}

	public boolean isMostrarRadioTipoUsuario() {
		return mostrarRadioTipoUsuario;
	}

	public boolean isMostrarCampoSolicitante() {
		return mostrarCampoSolicitante;
	}

	public boolean isMostrarSelectSolicitante() {
		return mostrarSelectSolicitante;
	}

	public boolean isMostrarCampoOrgano() {
		return mostrarCampoOrgano;
	}

	public boolean isOrganoEditable() {
		return organoEditable;
	}

	public boolean isSelectSolicitanteEditable() {
		return selectSolicitanteEditable;
	}

	public boolean isMostrarDatosReserva() {
		return mostrarDatosReserva;
	}

	public boolean isMostrarBotonAddTema() {
		return mostrarBotonAddTema;
	}

	public boolean isMostrarCampoAddTema() {
		return mostrarCampoAddTema;
	}

	public String getKeySolicitante() {
		return keySolicitante;
	}

	public boolean isMostrarFiltroUsuarios() {
		return mostrarFiltroUsuarios;
	}

	public boolean isMostrarCheckUsuariosSala() {
		return mostrarCheckUsuariosSala;
	}

	public boolean isPermitidoAddTemas() {
		return permitidoAddTemas;
	}

	public boolean isMostrarCheckVerUsuarioSala() {
		return mostrarCheckVerUsuarioSala;
	}

	public boolean isEsEdicion() {
		return esEdicion;
	}

	// FIN GETTERS

}
