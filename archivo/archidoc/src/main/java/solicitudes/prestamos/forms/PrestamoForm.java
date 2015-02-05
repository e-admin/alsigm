package solicitudes.prestamos.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import solicitudes.SolicitudesConstants;
import solicitudes.forms.SolicitudesBaseForm;
import solicitudes.prestamos.PrestamosConstants;

import common.ConfigConstants;
import common.Constants;
import common.Messages;

/**
 * @author x44906ga
 * 
 */
public class PrestamoForm extends SolicitudesBaseForm {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final Logger logger = Logger.getLogger(PrestamoForm.class);

	private String id = null;
	private String ano = null;
	private Integer orden = null;
	private String norgsolicitante = null;
	private String nusrsolicitante = null;
	private String idusrsolicitante = null;
	private String finicialreserva = null;
	private String ffinalreserva = null;
	private Date fentrega = null;
	private Date fmaxfinprestamo = null;
	private Integer estado = null;
	private Date festado = null;
	private String motivorechazo = null;
	private String idarchivo = null;
	private String idusrgestor = null;
	private Integer numreclamaciones = null;
	private Date freclamacion1 = null;
	private Date freclamacion2 = null;
	private String udocsadevolver = null;
	private String tipoSolicitante = Constants.TIPO_SOLICITANTE_INTERNO;
	private String iduser = null;
	private String idprestamo = null;
	private String idorg = null;
	private String prestamosseleccionados[];
	private String usuarioaceder = null;
	private Boolean tienereserva = null;
	private String telefonosolicitante = null;
	private String faxsolicitante = null;
	private String emailsolicitante = null;
	private String datosautorizado = null;
	private String tipoentrega = null;
	private String observaciones = null;
	private String idmotivo = null;
	private String filtroUsuario;

	private Boolean fromBusqueda = Boolean.FALSE;

	private String[] idsUdocs;

	public String getIdorg() {
		return idorg;
	}

	public void setIdorg(String idorg) {
		this.idorg = idorg;
	}

	public String getIduser() {
		return iduser;
	}

	public void setIduser(String idusr) {
		this.iduser = idusr;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Date getFentrega() {
		return fentrega;
	}

	public void setFentrega(Date fentrega) {
		this.fentrega = fentrega;
	}

	public Date getFestado() {
		return festado;
	}

	public void setFestado(Date festado) {
		this.festado = festado;
	}

	public String getFfinalreserva() {
		return ffinalreserva;
	}

	public void setFfinalreserva(String ffinalreserva) {
		this.ffinalreserva = ffinalreserva;
	}

	public String getFinicialreserva() {
		return finicialreserva;
	}

	public void setFinicialreserva(String finicialreserva) {
		this.finicialreserva = finicialreserva;
	}

	public Date getFmaxfinprestamo() {
		return fmaxfinprestamo;
	}

	public void setFmaxfinprestamo(Date fmaxfinprestamo) {
		this.fmaxfinprestamo = fmaxfinprestamo;
	}

	public Date getFreclamacion1() {
		return freclamacion1;
	}

	public void setFreclamacion1(Date freclamacion1) {
		this.freclamacion1 = freclamacion1;
	}

	public Date getFreclamacion2() {
		return freclamacion2;
	}

	public void setFreclamacion2(Date freclamacion2) {
		this.freclamacion2 = freclamacion2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdarchivo() {
		return idarchivo;
	}

	public void setIdarchivo(String idarchivo) {
		this.idarchivo = idarchivo;
	}

	public String getIdusrgestor() {
		return idusrgestor;
	}

	public void setIdusrgestor(String idusrgestor) {
		this.idusrgestor = idusrgestor;
	}

	public String getIdusrsolicitante() {
		return idusrsolicitante;
	}

	public void setIdusrsolicitante(String idusrsolicitante) {
		this.idusrsolicitante = idusrsolicitante;
	}

	public String getMotivorechazo() {
		return motivorechazo;
	}

	public void setMotivorechazo(String motivorechazo) {
		this.motivorechazo = motivorechazo;
	}

	public String getNorgsolicitante() {
		return norgsolicitante;
	}

	public void setNorgsolicitante(String norgsolicitante) {
		this.norgsolicitante = norgsolicitante;
	}

	public Integer getNumreclamaciones() {
		return numreclamaciones;
	}

	public void setNumreclamaciones(Integer numreclamaciones) {
		this.numreclamaciones = numreclamaciones;
	}

	public String getNusrsolicitante() {
		return nusrsolicitante;
	}

	public void setNusrsolicitante(String nusrsolicitante) {
		this.nusrsolicitante = nusrsolicitante;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public String[] getPrestamosseleccionados() {
		return prestamosseleccionados;
	}

	public void setPrestamosseleccionados(String[] prestamosseleccionados) {
		this.prestamosseleccionados = prestamosseleccionados;
	}

	public String getUsuarioaceder() {
		return usuarioaceder;
	}

	public void setUsuarioaceder(String usuarioaceder) {
		this.usuarioaceder = usuarioaceder;
	}

	public String getUdocsadevolver() {
		return udocsadevolver;
	}

	public void setUdocsadevolver(String udocsadevolver) {
		this.udocsadevolver = udocsadevolver;
	}

	public String getTipoSolicitante() {
		return tipoSolicitante;
	}

	public void setTipoSolicitante(String tipoSolicitante) {
		this.tipoSolicitante = tipoSolicitante;
	}

	public Boolean getTienereserva() {
		return tienereserva;
	}

	public void setTienereserva(Boolean tienereserva) {
		this.tienereserva = tienereserva;
	}

	/**
	 * @return el idprestamo
	 */
	public String getIdprestamo() {
		return idprestamo;
	}

	/**
	 * @param idprestamo
	 *            el idprestamo a establecer
	 */
	public void setIdprestamo(String idprestamo) {
		this.idprestamo = idprestamo;
	}

	/**
	 * Valida los datos del prestamos asociado al form.Se valida:</br> - La
	 * fecha inicial de reserva.</br>
	 * 
	 * @param aceptar
	 *            Si se debe validar o no los datos.
	 * @return {@link ActionErrors} con los datos de la validación.
	 */
	public ActionErrors validate(HttpServletRequest request, boolean aceptar) {
		ActionErrors errors = new ActionErrors();

		if (aceptar) {
			try {
				if (finicialreserva != null
						&& finicialreserva.trim().length() > 0)
					errors = validateFechasReserva(request, finicialreserva,
							SolicitudesConstants.TIPO_SOLICITUD_PRESTAMO_INT);
			} catch (Exception e) {
				logger.error("Se ha producido un error validando el préstamo",
						e);
			}

			if (GenericValidator.isBlankOrNull(norgsolicitante)) {
				errors.add(
						common.Constants.ERROR_GENERAL_MESSAGE,
						new ActionError(
								common.Constants.ERROR_GENERAL_MESSAGE,
								Messages.getString(
										PrestamosConstants.ERROR_PRESTAMOS_NORG_SOLICITANTE,
										request.getLocale())));
			}

			if (GenericValidator.isBlankOrNull(nusrsolicitante)) {
				errors.add(
						common.Constants.ERROR_GENERAL_MESSAGE,
						new ActionError(
								common.Constants.ERROR_GENERAL_MESSAGE,
								Messages.getString(
										PrestamosConstants.ERROR_PRESTAMOS_NUSR_SOLICITANTE,
										request.getLocale())));
			}

			if (GenericValidator.isBlankOrNull(idmotivo)
					&& !ConfigConstants.getInstance()
							.getMotivoSolicitudOpcional()) {
				errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								Constants.ERROR_REQUIRED,
								Messages.getString(
										SolicitudesConstants.ETIQUETA_ID_MOTIVO,
										request.getLocale())));
			}
		}

		return errors;
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

	public String getDatosautorizado() {
		return datosautorizado;
	}

	public void setDatosautorizado(String datosautorizado) {
		this.datosautorizado = datosautorizado;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getIdmotivo() {
		return idmotivo;
	}

	public void setIdmotivo(String idmotivo) {
		this.idmotivo = idmotivo;
	}

	public String getFiltroUsuario() {
		return filtroUsuario;
	}

	public void setFiltroUsuario(String filtroUsuario) {
		this.filtroUsuario = filtroUsuario;
	}

	public void setFromBusqueda(Boolean fromBusqueda) {
		this.fromBusqueda = fromBusqueda;
	}

	public Boolean getFromBusqueda() {
		return fromBusqueda;
	}

	public void setIdsUdocs(String[] idsUdocs) {
		this.idsUdocs = idsUdocs;
	}

	public String[] getIdsUdocs() {
		return idsUdocs;
	}

}