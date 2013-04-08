package solicitudes.consultas.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import solicitudes.consultas.ConsultasConstants;
import solicitudes.consultas.vos.BusquedaVO;
import auditoria.ArchivoDetails;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;
import common.util.CustomDate;
import common.util.CustomDateFormat;
import common.util.CustomDateRange;

/**
 * Formulario para la busqueda de consultas
 */
public class BusquedaForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String codigo = null;
	private String organo = null;
	private String solicitante = null;
	private String[] estados = new String[0];
	// Fechas para la entrega
	private String fechaCompEntrega = null;
	private String fechaFormatoEntrega = null;
	private String fechaAEntrega = null;
	private String fechaMEntrega = null;
	private String fechaDEntrega = null;
	private String fechaSEntrega = null;
	private String fechaIniFormatoEntrega = null;
	private String fechaIniAEntrega = null;
	private String fechaIniMEntrega = null;
	private String fechaIniDEntrega = null;
	private String fechaIniSEntrega = null;
	private String fechaFinFormatoEntrega = null;
	private String fechaFinAEntrega = null;
	private String fechaFinMEntrega = null;
	private String fechaFinDEntrega = null;
	private String fechaFinSEntrega = null;

	private String datosautorizado = null;
	private String tipoentrega = null;
	private String expedienteudoc = null;

	/**
	 * Inicia el formulario.
	 * 
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		estados = new String[0];
	}

	/**
	 * Rellena el formulario con el contenido de otro.
	 * 
	 * @param busqForm
	 *            Formulario de búsqueda.
	 */
	public void populate(BusquedaForm busqForm) {
		this.setEstados(busqForm.getEstados());
		this.setSolicitante(busqForm.getSolicitante());
		this.setOrgano(busqForm.getOrgano());
	}

	/**
	 * Obtiene el solicitante del préstamo
	 * 
	 * @return solicitante del préstamo
	 */
	public String getSolicitante() {
		return solicitante;
	}

	/**
	 * Establece el solicitante del prestamo.
	 * 
	 * @param solicitante
	 *            solicitante del prestamo.
	 */
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	/**
	 * Obtiene los estados del prestamo
	 * 
	 * @return Listado de identificadores de estaod
	 */
	public String[] getEstados() {
		return estados;
	}

	/**
	 * Establece los estados del prestamos
	 * 
	 * @param estados
	 *            Listado de los identificadores de los estados
	 */
	public void setEstados(String[] estados) {
		this.estados = estados;
	}

	/**
	 * Construye un VO con la información del formulario.
	 * 
	 * @return Objeto VO.
	 */
	public BusquedaVO getBusquedaVO() {
		BusquedaVO vo = new BusquedaVO();

		vo.setCodigo(this.codigo);
		vo.setEstados(this.estados);
		vo.setSolicitante(this.solicitante);
		vo.setOrgano(this.organo);

		// Obtener el rango de fechas de entrega para la búsqueda
		CustomDateRange range = CustomDateFormat.getDateRange(
				this.fechaCompEntrega, new CustomDate(this.fechaFormatoEntrega,
						this.fechaAEntrega, this.fechaMEntrega,
						this.fechaDEntrega, this.fechaSEntrega),
				new CustomDate(this.fechaIniFormatoEntrega,
						this.fechaIniAEntrega, this.fechaIniMEntrega,
						this.fechaIniDEntrega, this.fechaIniSEntrega),
				new CustomDate(this.fechaFinFormatoEntrega,
						this.fechaFinAEntrega, this.fechaFinMEntrega,
						this.fechaFinDEntrega, this.fechaFinSEntrega));

		vo.setFechaInicioEntrega(range.getInitialDate());
		vo.setFechaFinEntrega(range.getFinalDate());

		vo.setDatosautorizado(getDatosautorizado());
		vo.setTipoentrega(getTipoentrega());
		vo.setExpedienteudoc(getExpedienteudoc());

		return vo;
	}

	public String getFechaAEntrega() {
		return fechaAEntrega;
	}

	public void setFechaAEntrega(String fechaAEntrega) {
		this.fechaAEntrega = fechaAEntrega;
	}

	public String getFechaCompEntrega() {
		return fechaCompEntrega;
	}

	public void setFechaCompEntrega(String fechaCompEntrega) {
		this.fechaCompEntrega = fechaCompEntrega;
	}

	public String getFechaDEntrega() {
		return fechaDEntrega;
	}

	public void setFechaDEntrega(String fechaDEntrega) {
		this.fechaDEntrega = fechaDEntrega;
	}

	public String getFechaFinAEntrega() {
		return fechaFinAEntrega;
	}

	public void setFechaFinAEntrega(String fechaFinAEntrega) {
		this.fechaFinAEntrega = fechaFinAEntrega;
	}

	public String getFechaFinDEntrega() {
		return fechaFinDEntrega;
	}

	public void setFechaFinDEntrega(String fechaFinDEntrega) {
		this.fechaFinDEntrega = fechaFinDEntrega;
	}

	public String getFechaFinFormatoEntrega() {
		return fechaFinFormatoEntrega;
	}

	public void setFechaFinFormatoEntrega(String fechaFinFormatoEntrega) {
		this.fechaFinFormatoEntrega = fechaFinFormatoEntrega;
	}

	public String getFechaFinMEntrega() {
		return fechaFinMEntrega;
	}

	public void setFechaFinMEntrega(String fechaFinMEntrega) {
		this.fechaFinMEntrega = fechaFinMEntrega;
	}

	public String getFechaFinSEntrega() {
		return fechaFinSEntrega;
	}

	public void setFechaFinSEntrega(String fechaFinSEntrega) {
		this.fechaFinSEntrega = fechaFinSEntrega;
	}

	public String getFechaFormatoEntrega() {
		return fechaFormatoEntrega;
	}

	public void setFechaFormatoEntrega(String fechaFormatoEntrega) {
		this.fechaFormatoEntrega = fechaFormatoEntrega;
	}

	public String getFechaIniAEntrega() {
		return fechaIniAEntrega;
	}

	public void setFechaIniAEntrega(String fechaIniAEntrega) {
		this.fechaIniAEntrega = fechaIniAEntrega;
	}

	public String getFechaIniDEntrega() {
		return fechaIniDEntrega;
	}

	public void setFechaIniDEntrega(String fechaIniDEntrega) {
		this.fechaIniDEntrega = fechaIniDEntrega;
	}

	public String getFechaIniFormatoEntrega() {
		return fechaIniFormatoEntrega;
	}

	public void setFechaIniFormatoEntrega(String fechaIniFormatoEntrega) {
		this.fechaIniFormatoEntrega = fechaIniFormatoEntrega;
	}

	public String getFechaIniMEntrega() {
		return fechaIniMEntrega;
	}

	public void setFechaIniMEntrega(String fechaIniMEntrega) {
		this.fechaIniMEntrega = fechaIniMEntrega;
	}

	public String getFechaIniSEntrega() {
		return fechaIniSEntrega;
	}

	public void setFechaIniSEntrega(String fechaIniSEntrega) {
		this.fechaIniSEntrega = fechaIniSEntrega;
	}

	public String getFechaMEntrega() {
		return fechaMEntrega;
	}

	public void setFechaMEntrega(String fechaMEntrega) {
		this.fechaMEntrega = fechaMEntrega;
	}

	public String getFechaSEntrega() {
		return fechaSEntrega;
	}

	public void setFechaSEntrega(String fechaSEntrega) {
		this.fechaSEntrega = fechaSEntrega;
	}

	/**
	 * @return Returns the codigo.
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            The codigo to set.
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getOrgano() {
		return organo;
	}

	public void setOrgano(String organo) {
		this.organo = organo;
	}

	/**
	 * Valida el formulario
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		// Estados
		if (estados.length == 0)
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									ArchivoDetails.SOLICITUDES_ESTADOS_CONSULTA,
									request.getLocale())));

		// Fechas
		if (!new CustomDate(this.fechaFinFormatoEntrega, this.fechaAEntrega,
				this.fechaMEntrega, this.fechaDEntrega, this.fechaSEntrega)
				.validate()
				|| !new CustomDate(this.fechaIniFormatoEntrega,
						this.fechaIniAEntrega, this.fechaIniMEntrega,
						this.fechaIniDEntrega, this.fechaIniSEntrega)
						.validate()
				|| !new CustomDate(this.fechaFinFormatoEntrega,
						this.fechaFinAEntrega, this.fechaFinMEntrega,
						this.fechaFinDEntrega, this.fechaFinSEntrega)
						.validate()) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_DATE,
							Messages.getString(
									ConsultasConstants.BUSQUEDA_CONSULTAS_FECHA_ENTREGA,
									request.getLocale())));
		}

		return errors;
	}

	public String getDatosautorizado() {
		return datosautorizado;
	}

	public void setDatosautorizado(String datosautorizado) {
		this.datosautorizado = datosautorizado;
	}

	public String getTipoentrega() {
		return tipoentrega;
	}

	public void setTipoentrega(String tipoentrega) {
		this.tipoentrega = tipoentrega;
	}

	public String getExpedienteudoc() {
		return expedienteudoc;
	}

	public void setExpedienteudoc(String expedienteudoc) {
		this.expedienteudoc = expedienteudoc;
	}

}
