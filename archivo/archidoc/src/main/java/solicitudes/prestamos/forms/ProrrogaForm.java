package solicitudes.prestamos.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.vos.ProrrogaVO;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;
import common.util.DateUtils;

/**
 * Formulario de cesión de préstamos.
 */
public class ProrrogaForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private Integer estado;
	private String motivoRechazo;
	private String idMotivo;
	private String idPrestamo;
	private String fechaFinProrroga;
	private String motivoProrroga;

	private Boolean autorizar;

	/**
	 * Inicia el formulario.
	 * 
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
	}

	/**
	 * Valida el formulario de busqueda.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	public ActionErrors validateFormulario(ActionMapping mapping,
			HttpServletRequest request, Date fechaFinProrrogaActual) {
		ActionErrors errors = new ActionErrors();

		String labelFechaFinProrroga = Messages.getString(
				PrestamosConstants.LABEL_PRESTAMOS_FIN_PRORROGA,
				request.getLocale());

		if (GenericValidator.isBlankOrNull(getFechaFinProrroga())) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
					Constants.ERROR_REQUIRED, labelFechaFinProrroga));
		} else {
			if (!DateUtils.isDate(getFechaFinProrroga())) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
						Constants.ERROR_DATE, labelFechaFinProrroga));

			} else {

				// Comprobar que la fecha no sea anterior a la fecha actual
				if (DateUtils.isFechaMenor(
						DateUtils.getDate(getFechaFinProrroga()),
						DateUtils.getFechaActual())) {
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
							Constants.ERROR_DATE_BEFORE_TODAY,
							labelFechaFinProrroga));
				} else {
					// Comprobar que la fecha no sea anterior a la fecha fin de
					// prorroga
					if (fechaFinProrrogaActual != null
							&& DateUtils.isFechaMenor(
									DateUtils.getDate(getFechaFinProrroga()),
									fechaFinProrrogaActual)) {
						String labelFechaFinProrrogaActual = Messages
								.getString(
										PrestamosConstants.LABEL_FECHA_FIN_PRORROGA_VIGENTE,
										request.getLocale())
								+ " ("
								+ DateUtils.formatDate(fechaFinProrrogaActual)
								+ ")";
						errors.add(ActionErrors.GLOBAL_MESSAGE,
								new ActionError(Constants.ERROR_DATE_NO_BEFORE,
										labelFechaFinProrroga,
										labelFechaFinProrrogaActual));
					}
				}
			}
		}

		return errors;
	}

	public void populate(ProrrogaVO prorrogaVO) {

		prorrogaVO.setId(this.id);
		prorrogaVO.setIdPrestamo(this.idPrestamo);
		if (estado != null) {
			prorrogaVO.setEstado(this.estado.intValue());
		}
		prorrogaVO.setMotivoRechazo(this.motivoRechazo);
		prorrogaVO.setIdMotivo(this.idMotivo);
		prorrogaVO.setMotivoProrroga(this.motivoProrroga);

		prorrogaVO
				.setFechaFinProrroga(DateUtils.getDate(this.fechaFinProrroga));
	}

	public void set(ProrrogaVO prorrogaVO) {

		this.id = prorrogaVO.getId();
		this.idPrestamo = prorrogaVO.getIdPrestamo();
		this.estado = new Integer(prorrogaVO.getEstado());
		this.motivoRechazo = prorrogaVO.getMotivoRechazo();
		this.idMotivo = prorrogaVO.getIdMotivo();
		this.motivoProrroga = prorrogaVO.getMotivoProrroga();
		this.fechaFinProrroga = DateUtils.formatDate(prorrogaVO
				.getFechaFinProrroga());
	}

	public String getId() {
		return id;
	}

	public Integer getEstado() {
		return estado;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public String getIdMotivo() {
		return idMotivo;
	}

	public String getIdPrestamo() {
		return idPrestamo;
	}

	public String getFechaFinProrroga() {
		return fechaFinProrroga;
	}

	public String getMotivoProrroga() {
		return motivoProrroga;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	public void setIdMotivo(String idMotivo) {
		this.idMotivo = idMotivo;
	}

	public void setIdPrestamo(String idPrestamo) {
		this.idPrestamo = idPrestamo;
	}

	public void setFechaFinProrroga(String fechaFinProrroga) {
		this.fechaFinProrroga = fechaFinProrroga;
	}

	public void setMotivoProrroga(String motivoProrroga) {
		this.motivoProrroga = motivoProrroga;
	}

	public void setAutorizar(Boolean autorizar) {
		this.autorizar = autorizar;
	}

	public Boolean getAutorizar() {
		return autorizar;
	}

	public boolean isDenegada() {
		if (estado != null) {
			return PrestamosConstants.ESTADO_PRORROGA_DENEGADA == estado
					.intValue();
		}
		return false;
	}

	public boolean isAutorizada() {
		if (estado != null) {
			return PrestamosConstants.ESTADO_PRORROGA_AUTORIZADA == estado
					.intValue();
		}
		return false;

	}

	public boolean isSolicitada() {
		if (estado != null) {
			return PrestamosConstants.ESTADO_PRORROGA_SOLICITADA == estado
					.intValue();
		}
		return false;
	}
}
