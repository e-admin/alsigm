package fondos.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;
import common.pagination.PageInfo;
import common.util.CustomDate;
import common.util.CustomDateFormat;
import common.util.CustomDateRange;

import fondos.FondosConstants;
import fondos.vos.BusquedaSolicitudesSerieVO;

public class BusquedaSolicitudSerieForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public String[] getEstados() {
		return estados;
	}

	public void setEstados(String[] estados) {
		this.estados = estados;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	String idUsuarioGestor = null;
	String idUsuarioSolitante = null;
	String nombreUsuarioGestor = null;
	String nombreUsuarioSolicitante = null;
	String etiqueta = null;

	// String titulo;
	// String referencia;

	/** Estado del documento vital. */
	private String[] estados = new String[0];

	String fecha;
	String fechaComp;
	String fechaFormato;
	String fechaA;
	String fechaM;
	String fechaD;
	String fechaS;

	String fechaIniFormato;
	String fechaIniA;
	String fechaIniM;
	String fechaIniD;
	String fechaIniS;

	String fechaFinFormato;
	String fechaFinA;
	String fechaFinM;
	String fechaFinD;
	String fechaFinS;

	/** Información de la paginación. */
	private PageInfo pageInfo = null;

	public String getFechaA() {
		return fechaA;
	}

	public void setFechaA(String fechaAEstado) {
		this.fechaA = fechaAEstado;
	}

	public String getFechaComp() {
		return fechaComp;
	}

	public void setFechaComp(String fechaCompEstado) {
		this.fechaComp = fechaCompEstado;
	}

	public String getFechaD() {
		return fechaD;
	}

	public void setFechaD(String fechaDEstado) {
		this.fechaD = fechaDEstado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fechaEstado) {
		this.fecha = fechaEstado;
	}

	public String getFechaFinA() {
		return fechaFinA;
	}

	public void setFechaFinA(String fechaFinAEstado) {
		this.fechaFinA = fechaFinAEstado;
	}

	public String getFechaFinD() {
		return fechaFinD;
	}

	public void setFechaFinD(String fechaFinDEstado) {
		this.fechaFinD = fechaFinDEstado;
	}

	public String getFechaFinFormato() {
		return fechaFinFormato;
	}

	public void setFechaFinFormato(String fechaFinFormatoEstado) {
		this.fechaFinFormato = fechaFinFormatoEstado;
	}

	public String getFechaFinM() {
		return fechaFinM;
	}

	public void setFechaFinM(String fechaFinMEstado) {
		this.fechaFinM = fechaFinMEstado;
	}

	public String getFechaFinS() {
		return fechaFinS;
	}

	public void setFechaFinS(String fechaFinSEstado) {
		this.fechaFinS = fechaFinSEstado;
	}

	public String getFechaFormato() {
		return fechaFormato;
	}

	public void setFechaFormato(String fechaFormatoEstado) {
		this.fechaFormato = fechaFormatoEstado;
	}

	public String getFechaIniA() {
		return fechaIniA;
	}

	public void setFechaIniA(String fechaIniAEstado) {
		this.fechaIniA = fechaIniAEstado;
	}

	public String getFechaIniD() {
		return fechaIniD;
	}

	public void setFechaIniD(String fechaIniDEstado) {
		this.fechaIniD = fechaIniDEstado;
	}

	public String getFechaIniFormato() {
		return fechaIniFormato;
	}

	public void setFechaIniFormato(String fechaIniFormatoEstado) {
		this.fechaIniFormato = fechaIniFormatoEstado;
	}

	public String getFechaIniM() {
		return fechaIniM;
	}

	public void setFechaIniM(String fechaIniMEstado) {
		this.fechaIniM = fechaIniMEstado;
	}

	public String getFechaIniS() {
		return fechaIniS;
	}

	public void setFechaIniS(String fechaIniSEstado) {
		this.fechaIniS = fechaIniSEstado;
	}

	public String getFechaM() {
		return fechaM;
	}

	public void setFechaM(String fechaMEstado) {
		this.fechaM = fechaMEstado;
	}

	public String getFechaS() {
		return fechaS;
	}

	public void setFechaS(String fechaSEstado) {
		this.fechaS = fechaSEstado;
	}

	// public String getReferencia() {
	// return referencia;
	// }
	// public void setReferencia(String referencia) {
	// this.referencia = referencia;
	// }
	// public String getTitulo() {
	// return titulo;
	// }
	// public void setTitulo(String titulo) {
	// this.titulo = titulo;
	// }
	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public String getIdUsuarioGestor() {
		return idUsuarioGestor;
	}

	public void setIdUsuarioGestor(String idUsuarioGestor) {
		this.idUsuarioGestor = idUsuarioGestor;
	}

	public String getIdUsuarioSolitante() {
		return idUsuarioSolitante;
	}

	public void setIdUsuarioSolitante(String idUsuarioSolitante) {
		this.idUsuarioSolitante = idUsuarioSolitante;
	}

	public String getNombreUsuarioGestor() {
		return nombreUsuarioGestor;
	}

	public void setNombreUsuarioGestor(String nombreUsuarioGestor) {
		this.nombreUsuarioGestor = nombreUsuarioGestor;
	}

	public String getNombreUsuarioSolicitante() {
		return nombreUsuarioSolicitante;
	}

	public void setNombreUsuarioSolicitante(String nombreUsuarioSolicitante) {
		this.nombreUsuarioSolicitante = nombreUsuarioSolicitante;
	}

	public BusquedaSolicitudesSerieVO getBusquedaVO() {
		BusquedaSolicitudesSerieVO vo = new BusquedaSolicitudesSerieVO();
		// vo.setTitulo(this.titulo);
		// vo.setReferencia(this.referencia);
		vo.setIdUsuarioGestor(this.getIdUsuarioGestor());
		vo.setIdUsuarioSolicitante(this.getIdUsuarioSolitante());
		vo.setNombreUsuarioGestor(this.getNombreUsuarioGestor());
		vo.setNombreUsuarioSolicitante(this.getNombreUsuarioSolicitante());
		vo.setEtiqueta(this.getEtiqueta());
		vo.setEstados(this.estados);
		vo.setFechaEstado(this.fecha);

		// Obtener el rango de fechas de entrega para la búsqueda
		CustomDateRange range = CustomDateFormat.getDateTimeRange(
				this.fechaComp, new CustomDate(this.fechaFormato, this.fechaA,
						this.fechaM, this.fechaD, this.fechaS), new CustomDate(
						this.fechaIniFormato, this.fechaIniA, this.fechaIniM,
						this.fechaIniD, this.fechaIniS), new CustomDate(
						this.fechaFinFormato, this.fechaFinA, this.fechaFinM,
						this.fechaFinD, this.fechaFinS));

		vo.setFechaInicioEstado(range.getInitialDate());
		vo.setFechaFinEstado(range.getFinalDate());

		vo.setPageInfo(this.pageInfo);

		return vo;
	}

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
									FondosConstants.UDOCS_ARCHIVO_CF_ESTADO_SOLICITUD,
									request.getLocale())));

		// Fechas
		if (!new CustomDate(this.fechaFinFormato, this.fechaA, this.fechaM,
				this.fechaD, this.fechaS).validate()
				|| !new CustomDate(this.fechaIniFormato, this.fechaIniA,
						this.fechaIniM, this.fechaIniD, this.fechaIniS)
						.validate()
				|| !new CustomDate(this.fechaFinFormato, this.fechaFinA,
						this.fechaFinM, this.fechaFinD, this.fechaFinS)
						.validate()) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_DATE, Messages.getString(
							FondosConstants.LABEL_CF_BUSQUEDA_FECHA,
							request.getLocale())));
		}

		return errors;
	}

}
