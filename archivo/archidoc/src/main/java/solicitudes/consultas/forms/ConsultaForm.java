package solicitudes.consultas.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import solicitudes.SolicitudesConstants;
import solicitudes.consultas.ConsultasConstants;
import solicitudes.consultas.vos.ConsultaVO;
import solicitudes.forms.SolicitudesBaseForm;

import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.util.StringUtils;

/**
 * Formulario para las consultas
 */
public class ConsultaForm extends SolicitudesBaseForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final Logger logger = Logger.getLogger(ConsultaForm.class);

	private String id = null;
	private String ano = null;
	private Integer orden = null;
	private String idTema = null;
	private String tema = null;
	private Integer tipoentconsultora = new Integer(
			ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_CIUDADANO_INT);
	private String norgconsultor = null;
	private String nusrconsultor = null;
	private String idusrsolicitante = null;
	private Boolean tienereserva = null;
	private String temaNuevo = null;
	private String usuarioInvestigador = null;
	// private String iduser = null;
	private String finicialreserva = null;
	private String ffinalreserva = null;
	private Date fentrega = null;
	private Date fmaxfinprestamo = null;
	private Integer estado = null;
	private Date festado = null;
	private String motivo = null;
	private String idarchivo = null;
	private String informacion = null;
	private String consultasseleccionadas[] = null;
	private String telefonosolicitante = null;
	private String faxsolicitante = null;
	private String emailsolicitante = null;
	private String datosautorizado = null;
	private String tipoentrega = null;
	private String observaciones = null;
	private String idMotivo = null;
	private String filtroUsuario = null;
	// private Boolean usuarioEnSala = Boolean.FALSE;
	// private Boolean verUsuariosSala = Boolean.FALSE;

	private Boolean consultaEnSala = Boolean.FALSE;

	private Boolean formBusqueda = Boolean.FALSE;

	private Boolean addTema = Boolean.FALSE;

	// /**
	// * @return Returns the idusr.
	// */
	// public String getIduser() {
	// return iduser;
	// }

	// /**
	// * @param idusr
	// * The idusr to set.
	// */
	// public void setIduser(String idusr) {
	// this.iduser = idusr;
	// }

	/**
	 * @return Returns the ano.
	 */
	public String getAno() {
		return ano;
	}

	/**
	 * @param ano
	 *            The ano to set.
	 */
	public void setAno(String ano) {
		this.ano = ano;
	}

	/**
	 * @return Returns the estado.
	 */
	public Integer getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            The estado to set.
	 */
	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	/**
	 * @return Returns the fentrega.
	 */
	public Date getFentrega() {
		return fentrega;
	}

	/**
	 * @param fentrega
	 *            The fentrega to set.
	 */
	public void setFentrega(Date fentrega) {
		this.fentrega = fentrega;
	}

	/**
	 * @return Returns the festado.
	 */
	public Date getFestado() {
		return festado;
	}

	/**
	 * @param festado
	 *            The festado to set.
	 */
	public void setFestado(Date festado) {
		this.festado = festado;
	}

	/**
	 * @return Returns the ffinalreserva.
	 */
	public String getFfinalreserva() {
		return ffinalreserva;
	}

	/**
	 * @param ffinalreserva
	 *            The ffinalreserva to set.
	 */
	public void setFfinalreserva(String ffinalreserva) {
		this.ffinalreserva = ffinalreserva;
	}

	/**
	 * @return Returns the finicialreserva.
	 */
	public String getFinicialreserva() {
		return finicialreserva;
	}

	/**
	 * @param finicialreserva
	 *            The finicialreserva to set.
	 */
	public void setFinicialreserva(String finicialreserva) {
		this.finicialreserva = finicialreserva;
	}

	/**
	 * @return Returns the fmaxfinprestamo.
	 */
	public Date getFmaxfinprestamo() {
		return fmaxfinprestamo;
	}

	/**
	 * @param fmaxfinprestamo
	 *            The fmaxfinprestamo to set.
	 */
	public void setFmaxfinprestamo(Date fmaxfinprestamo) {
		this.fmaxfinprestamo = fmaxfinprestamo;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the idarchivo.
	 */
	public String getIdarchivo() {
		return idarchivo;
	}

	/**
	 * @param idarchivo
	 *            The idarchivo to set.
	 */
	public void setIdarchivo(String idarchivo) {
		this.idarchivo = idarchivo;
	}

	/**
	 * @return Returns the idusrsolicitante.
	 */
	public String getIdusrsolicitante() {
		return idusrsolicitante;
	}

	/**
	 * @param idusrsolicitante
	 *            The idusrsolicitante to set.
	 */
	public void setIdusrsolicitante(String idusrsolicitante) {
		this.idusrsolicitante = idusrsolicitante;
	}

	/**
	 * @return Returns the informacion.
	 */
	public String getInformacion() {
		return informacion;
	}

	/**
	 * @param informacion
	 *            The informacion to set.
	 */
	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}

	/**
	 * @return Returns the motivo.
	 */
	public String getMotivo() {
		return motivo;
	}

	/**
	 * @param motivo
	 *            The motivo to set.
	 */
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	/**
	 * @return Returns the norgconsultor.
	 */
	public String getNorgconsultor() {
		return norgconsultor;
	}

	/**
	 * @param norgconsultor
	 *            The norgconsultor to set.
	 */
	public void setNorgconsultor(String norgconsultor) {
		this.norgconsultor = norgconsultor;
	}

	/**
	 * @return Returns the nusrconsultor.
	 */
	public String getNusrconsultor() {
		return nusrconsultor;
	}

	/**
	 * @param nusrconsultor
	 *            The nusrconsultor to set.
	 */
	public void setNusrconsultor(String nusrconsultor) {
		this.nusrconsultor = nusrconsultor;
	}

	/**
	 * @return Returns the orden.
	 */
	public Integer getOrden() {
		return orden;
	}

	/**
	 * @param orden
	 *            The orden to set.
	 */
	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	/**
	 * @return Returns the tema.
	 */
	public String getIdTema() {
		return idTema;
	}

	/**
	 * @param tema
	 *            The tema to set.
	 */
	public void setIdTema(String idTema) {
		this.idTema = idTema;
	}

	/**
	 * @return Returns the tipoentconsultora.
	 */
	public Integer getTipoentconsultora() {
		return tipoentconsultora;
	}

	/**
	 * @param tipoentconsultora
	 *            The tipoentconsultora to set.
	 */
	public void setTipoentconsultora(Integer tipoentconsultora) {
		this.tipoentconsultora = tipoentconsultora;
	}

	/**
	 * Realiza la validación de los datos del formulario
	 * 
	 * @param aceptar
	 *            Si se debe realizar la validación o no
	 * @return {@link ActionErrors} con los errores detectados durante el
	 *         proceso de validacion
	 */
	public ActionErrors validate(HttpServletRequest request, boolean aceptar) {
		ActionErrors errors = new ActionErrors();

		if (aceptar) {
			try {
				if (finicialreserva != null
						&& finicialreserva.trim().length() > 0)
					errors = validateFechasReserva(request, finicialreserva,
							SolicitudesConstants.TIPO_SOLICITUD_CONSULTA_INT);
			} catch (Exception e) {
				logger.error("Se ha producido un error validando la consulta",
						e);
			}

			if (GenericValidator.isBlankOrNull(nusrconsultor)) {
				errors.add(
						common.Constants.ERROR_GENERAL_MESSAGE,
						new ActionError(
								common.Constants.ERROR_GENERAL_MESSAGE,
								Messages.getString(
										ConsultasConstants.ERRORS_SOLICITUDES_CONSULTA_FECHA_NUM_USER_CONSULTOR,
										request.getLocale())));
			}

			if (("" + this.getTipoentconsultora())
					.equals(ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_ORGANO_EXTERNO)) {
				if (GenericValidator.isBlankOrNull(norgconsultor)) {
					errors.add(
							common.Constants.ERROR_GENERAL_MESSAGE,
							new ActionError(
									common.Constants.ERROR_GENERAL_MESSAGE,
									Messages.getString(
											ConsultasConstants.ERRORS_SOLICITUDES_CONSULTA_FECHA_NUM_OGR_CONSULTOR,
											request.getLocale())));
				}
			}

			/*
			 * if ( GenericValidator.isBlankOrNull(this.getIdTema()) ) {
			 * errors.add( common.Constants.ERROR_GENERAL_MESSAGE, new
			 * ActionError( common.Constants.ERROR_GENERAL_MESSAGE,
			 * Messages.getString
			 * (ConsultasConstants.ERRORS_SOLICITUDES_CONSULTA_TEMA_NUEVO))); }
			 */

			if (GenericValidator.isBlankOrNull(idMotivo)
					&& !ConfigConstants.getInstance()
							.getMotivoSolicitudOpcional()) {
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_REQUIRED,
								Messages.getString(
										SolicitudesConstants.ETIQUETA_ID_MOTIVO,
										request.getLocale())));
			}
		}

		return errors;
	}

	/**
	 * Realiza la validación de los datos del formulario
	 * 
	 * @param aceptar
	 *            Si se debe realizar la validación o no
	 * @return {@link ActionErrors} con los errores detectados durante el
	 *         proceso de validacion
	 */
	public ActionErrors validateNuevoTema(HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		if (GenericValidator.isBlankOrNull(idusrsolicitante)) {
			errors.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ConsultasConstants.ERRORS_SOLICITUDES_CONSULTA_FECHA_NUM_USER_CONSULTOR,
									request.getLocale())));
		}

		if (GenericValidator.isBlankOrNull(this.getTemaNuevo())) {
			errors.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(ConsultasConstants.ERRORS_SOLICITUDES_CONSULTA_TEMA_NUEVO)));
		}

		return errors;
	}

	/**
	 * @return Returns the consultasseleccionadas.
	 */
	public String[] getConsultasseleccionadas() {
		return consultasseleccionadas;
	}

	/**
	 * @param consultasseleccionadas
	 *            The consultasseleccionadas to set.
	 */
	public void setConsultasseleccionadas(String[] consultasseleccionadas) {
		this.consultasseleccionadas = consultasseleccionadas;
	}

	/**
	 * 
	 * @return
	 */
	public String getTema() {
		return tema;
	}

	/**
	 * 
	 * @param tema
	 */
	public void setTema(String tema) {
		this.tema = tema;
	}

	/**
	 * @return Returns the tienereserva.
	 */
	public Boolean getTienereserva() {
		return tienereserva;
	}

	/**
	 * @param tienereserva
	 *            The tienereserva to set.
	 */
	public void setTienereserva(Boolean tienereserva) {
		this.tienereserva = tienereserva;
	}

	public String getTemaNuevo() {
		return temaNuevo;
	}

	public void setTemaNuevo(String temaNuevo) {
		this.temaNuevo = temaNuevo;
	}

	public String getUsuarioInvestigador() {
		return usuarioInvestigador;
	}

	public void setUsuarioInvestigador(String usuarioInvestigador) {
		this.usuarioInvestigador = usuarioInvestigador;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.id = null;
		this.ano = null;
		this.orden = null;
		this.idTema = null;
		this.tema = null;
		this.tipoentconsultora = new Integer(
				ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_CIUDADANO_INT);
		this.norgconsultor = null;
		this.nusrconsultor = null;
		this.idusrsolicitante = null;
		this.tienereserva = null;
		this.temaNuevo = null;
		this.usuarioInvestigador = null;
		// this.iduser = null;
		this.finicialreserva = null;
		this.ffinalreserva = null;
		this.fentrega = null;
		this.fmaxfinprestamo = null;
		this.estado = null;
		this.festado = null;
		this.motivo = null;
		this.idarchivo = null;
		this.informacion = null;
		this.consultasseleccionadas = null;
		this.telefonosolicitante = null;
		this.faxsolicitante = null;
		this.emailsolicitante = null;
		this.datosautorizado = null;
		this.idMotivo = null;
		this.filtroUsuario = null;
		// this.usuarioEnSala = Boolean.FALSE;
		// this.verUsuariosSala = Boolean.FALSE;
		this.consultaEnSala = Boolean.FALSE;
		this.observaciones = null;

	}

	public String getDatosautorizado() {
		return datosautorizado;
	}

	public void setDatosautorizado(String datosautorizado) {
		this.datosautorizado = datosautorizado;
	}

	public String getEmailsolicitante() {
		return emailsolicitante;
	}

	public void setEmailsolicitante(String emailsolicitante) {
		this.emailsolicitante = emailsolicitante;
	}

	public String getFaxsolicitante() {
		return faxsolicitante;
	}

	public void setFaxsolicitante(String faxsolicitante) {
		this.faxsolicitante = faxsolicitante;
	}

	public String getTelefonosolicitante() {
		return telefonosolicitante;
	}

	public void setTelefonosolicitante(String telefonosolicitante) {
		this.telefonosolicitante = telefonosolicitante;
	}

	public String getTipoentrega() {
		return tipoentrega;
	}

	public void setTipoentrega(String tipoentrega) {
		this.tipoentrega = tipoentrega;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getIdMotivo() {
		return idMotivo;
	}

	public void setIdMotivo(String idMotivo) {
		this.idMotivo = idMotivo;
	}

	public String getFiltroUsuario() {
		return filtroUsuario;
	}

	public void setFiltroUsuario(String filtroUsuario) {
		this.filtroUsuario = filtroUsuario;
	}

	public void setFormBusqueda(Boolean formBusqueda) {
		this.formBusqueda = formBusqueda;
	}

	public Boolean getFormBusqueda() {
		return formBusqueda;
	}

	// public Boolean getUsuarioEnSala() {
	// return usuarioEnSala;
	// }
	//
	// public void setUsuarioEnSala(Boolean usuarioEnSala) {
	// this.usuarioEnSala = usuarioEnSala;
	// }
	//
	// public Boolean getVerUsuariosSala() {
	// return verUsuariosSala;
	// }
	//
	// public void setVerUsuariosSala(Boolean verUsuariosSala) {
	// this.verUsuariosSala = verUsuariosSala;
	// }

	public void setAddTema(Boolean addTema) {
		this.addTema = addTema;
	}

	public Boolean getAddTema() {
		return addTema;
	}

	public void setConsultaEnSala(Boolean consultaEnSala) {
		this.consultaEnSala = consultaEnSala;
	}

	public Boolean getConsultaEnSala() {
		return consultaEnSala;
	}

	public boolean isCheckedConsultaEnSala() {
		if (this.consultaEnSala != null
				&& this.consultaEnSala.equals(Boolean.TRUE)) {
			return true;
		}
		return false;
	}

	public boolean isCheckedReserva() {
		if (this.tienereserva != null && this.tienereserva.equals(Boolean.TRUE)) {
			return true;
		}
		return false;
	}

	public boolean isTipoEntidadInvestigador() {
		if (tipoentconsultora != null
				&& tipoentconsultora.intValue() == ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_INVESTIGADOR_INT) {
			return true;
		}

		return false;
	}

	public boolean isTipoEntidadCiudadano() {
		if (tipoentconsultora != null
				&& tipoentconsultora.intValue() == ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_CIUDADANO_INT) {
			return true;
		}
		return false;
	}

	public boolean isTipoEntidadOrgano() {
		if (tipoentconsultora != null
				&& tipoentconsultora.intValue() == ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_ORGANO_EXTERNO_INT) {
			return true;
		}

		return false;
	}

	public void resetDatosSolicitante() {
		norgconsultor = null;
		nusrconsultor = null;
	}

	public ConsultaVO populate(String idUsuarioConectado, ConsultaVO consultaVO) {

		if (consultaVO == null) {
			consultaVO = new ConsultaVO();
		}

		consultaVO.setId(getId());
		consultaVO.setTipoentconsultora(getTipoentconsultora());
		consultaVO.setIdarchivo(getIdarchivo());
		consultaVO.setNorgconsultor(getNorgconsultor());
		consultaVO.setNusrconsultor(getNusrconsultor());
		consultaVO.setDatossolicitante(getTelefonosolicitante(),
				getFaxsolicitante(), getEmailsolicitante());
		consultaVO.setDatosautorizado(getDatosautorizado());
		consultaVO.setTipoentrega(getTipoentrega());
		consultaVO.setIdMotivo(getIdMotivo());
		consultaVO.setTema(getIdTema());
		consultaVO.setObservaciones(getObservaciones());

		if (isCheckedConsultaEnSala()) {
			consultaVO.setIdusrcsala(getIdusrsolicitante());
		} else {
			consultaVO.setIdusrsolicitante(idUsuarioConectado);
			consultaVO.setIdusrcsala(null);
		}

		// Establecemos el tipo de consulta
		if (idUsuarioConectado.equalsIgnoreCase(getIdusrsolicitante()))
			consultaVO.setTipo(ConsultasConstants.TIPO_CONSULTA_DIRECTA);
		else
			consultaVO.setTipo(ConsultasConstants.TIPO_CONSULTA_INDIRECTA);

		if (isCheckedReserva()) {
			consultaVO.setFinicialreserva(SolicitudesBaseForm
					.parseDate(getFinicialreserva()));
		} else {
			consultaVO.setFinicialreserva(null);
		}

		return consultaVO;
	}

	public void set(ConsultaVO consultaVO) {

		setId(consultaVO.getId());
		setTipoentconsultora(consultaVO.getTipoentconsultora());
		setIdarchivo(consultaVO.getIdarchivo());

		setNorgconsultor(consultaVO.getNorgconsultor());
		setNusrconsultor(consultaVO.getNusrconsultor());
		setTelefonosolicitante(consultaVO.getTelefonosolicitante());
		setFaxsolicitante(consultaVO.getFaxsolicitante());
		setEmailsolicitante(consultaVO.getEmailsolicitante());
		setDatosautorizado(consultaVO.getDatosautorizado());

		setTipoentrega(consultaVO.getTipoentrega());
		setObservaciones(consultaVO.getObservaciones());
		setIdTema(consultaVO.getTema());
		setIdMotivo(consultaVO.getIdMotivo());

		if (StringUtils.isNotEmpty(consultaVO.getIdusrcsala())) {
			setConsultaEnSala(Boolean.TRUE);
			setIdusrsolicitante(consultaVO.getIdusrcsala());
		} else {
			setConsultaEnSala(Boolean.FALSE);
			setIdusrsolicitante(consultaVO.getIdusrsolicitante());
		}
	}
}