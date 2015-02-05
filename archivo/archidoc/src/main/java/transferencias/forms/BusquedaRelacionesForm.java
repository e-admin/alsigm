package transferencias.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import transferencias.TipoTransferencia;
import transferencias.TransferenciasConstants;
import transferencias.vos.BusquedaRelacionesVO;
import auditoria.ArchivoDetails;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;
import common.pagination.PageInfo;
import common.util.CustomDate;
import common.util.CustomDateFormat;
import common.util.CustomDateRange;
import common.util.StringUtils;

/**
 * Formulario de búsqueda de relaciones.
 */
public class BusquedaRelacionesForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String codigo = null;
	private String organo = null;
	private String gestorOficina = null;
	private String gestorArchivo = null;
	private String[] tipos = new String[0];
	private String[] estados = new String[0];
	private String anio = null;
	private String serie = null;
	private String codigoSerie = null;
	private String procedimiento = null;
	private String idArchivo = null;
	private String idFormato = null;
	private String nombreArchivo = null;
	private String observaciones = null;
	private String[] idsProductores = null;
	private String[] nombreProductores = null;

	private String productoresSeleccionados = null;

	// Fecha de recepción
	private String fechaComp = null;
	private String fechaFormato = null;
	private String fechaA = null;
	private String fechaM = null;
	private String fechaD = null;
	private String fechaS = null;
	private String fechaIniFormato = null;
	private String fechaIniA = null;
	private String fechaIniM = null;
	private String fechaIniD = null;
	private String fechaIniS = null;
	private String fechaFinFormato = null;
	private String fechaFinA = null;
	private String fechaFinM = null;
	private String fechaFinD = null;
	private String fechaFinS = null;

	private PageInfo pageInfo = null;

	private Boolean ingresoDirecto = null;

	/**
	 * Constructor.
	 */
	public BusquedaRelacionesForm() {
	}

	/**
	 * Inicia el formulario.
	 * 
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		tipos = new String[0];
		estados = new String[0];
	}

	/**
	 * @return Returns the anio.
	 */
	public String getAnio() {
		return anio;
	}

	/**
	 * @param anio
	 *            The anio to set.
	 */
	public void setAnio(String anio) {
		this.anio = anio;
	}

	/**
	 * @return Returns the estados.
	 */
	public String[] getEstados() {
		return estados;
	}

	/**
	 * @param estados
	 *            The estados to set.
	 */
	public void setEstados(String[] estados) {
		this.estados = estados;
	}

	/**
	 * @return Returns the fechaA.
	 */
	public String getFechaA() {
		return fechaA;
	}

	/**
	 * @param fechaA
	 *            The fechaA to set.
	 */
	public void setFechaA(String fechaA) {
		this.fechaA = fechaA;
	}

	/**
	 * @return Returns the fechaComp.
	 */
	public String getFechaComp() {
		return fechaComp;
	}

	/**
	 * @param fechaComp
	 *            The fechaComp to set.
	 */
	public void setFechaComp(String fechaComp) {
		this.fechaComp = fechaComp;
	}

	/**
	 * @return Returns the fechaD.
	 */
	public String getFechaD() {
		return fechaD;
	}

	/**
	 * @param fechaD
	 *            The fechaD to set.
	 */
	public void setFechaD(String fechaD) {
		this.fechaD = fechaD;
	}

	/**
	 * @return Returns the fechaFinA.
	 */
	public String getFechaFinA() {
		return fechaFinA;
	}

	/**
	 * @param fechaFinA
	 *            The fechaFinA to set.
	 */
	public void setFechaFinA(String fechaFinA) {
		this.fechaFinA = fechaFinA;
	}

	/**
	 * @return Returns the fechaFinD.
	 */
	public String getFechaFinD() {
		return fechaFinD;
	}

	/**
	 * @param fechaFinD
	 *            The fechaFinD to set.
	 */
	public void setFechaFinD(String fechaFinD) {
		this.fechaFinD = fechaFinD;
	}

	/**
	 * @return Returns the fechaFinFormato.
	 */
	public String getFechaFinFormato() {
		return fechaFinFormato;
	}

	/**
	 * @param fechaFinFormato
	 *            The fechaFinFormato to set.
	 */
	public void setFechaFinFormato(String fechaFinFormato) {
		this.fechaFinFormato = fechaFinFormato;
	}

	/**
	 * @return Returns the fechaFinM.
	 */
	public String getFechaFinM() {
		return fechaFinM;
	}

	/**
	 * @param fechaFinM
	 *            The fechaFinM to set.
	 */
	public void setFechaFinM(String fechaFinM) {
		this.fechaFinM = fechaFinM;
	}

	/**
	 * @return Returns the fechaFinS.
	 */
	public String getFechaFinS() {
		return fechaFinS;
	}

	/**
	 * @param fechaFinS
	 *            The fechaFinS to set.
	 */
	public void setFechaFinS(String fechaFinS) {
		this.fechaFinS = fechaFinS;
	}

	/**
	 * @return Returns the fechaFormato.
	 */
	public String getFechaFormato() {
		return fechaFormato;
	}

	/**
	 * @param fechaFormato
	 *            The fechaFormato to set.
	 */
	public void setFechaFormato(String fechaFormato) {
		this.fechaFormato = fechaFormato;
	}

	/**
	 * @return Returns the fechaIniA.
	 */
	public String getFechaIniA() {
		return fechaIniA;
	}

	/**
	 * @param fechaIniA
	 *            The fechaIniA to set.
	 */
	public void setFechaIniA(String fechaIniA) {
		this.fechaIniA = fechaIniA;
	}

	/**
	 * @return Returns the fechaIniD.
	 */
	public String getFechaIniD() {
		return fechaIniD;
	}

	/**
	 * @param fechaIniD
	 *            The fechaIniD to set.
	 */
	public void setFechaIniD(String fechaIniD) {
		this.fechaIniD = fechaIniD;
	}

	/**
	 * @return Returns the fechaIniFormato.
	 */
	public String getFechaIniFormato() {
		return fechaIniFormato;
	}

	/**
	 * @param fechaIniFormato
	 *            The fechaIniFormato to set.
	 */
	public void setFechaIniFormato(String fechaIniFormato) {
		this.fechaIniFormato = fechaIniFormato;
	}

	/**
	 * @return Returns the fechaIniM.
	 */
	public String getFechaIniM() {
		return fechaIniM;
	}

	/**
	 * @param fechaIniM
	 *            The fechaIniM to set.
	 */
	public void setFechaIniM(String fechaIniM) {
		this.fechaIniM = fechaIniM;
	}

	/**
	 * @return Returns the fechaIniS.
	 */
	public String getFechaIniS() {
		return fechaIniS;
	}

	/**
	 * @param fechaIniS
	 *            The fechaIniS to set.
	 */
	public void setFechaIniS(String fechaIniS) {
		this.fechaIniS = fechaIniS;
	}

	/**
	 * @return Returns the fechaM.
	 */
	public String getFechaM() {
		return fechaM;
	}

	/**
	 * @param fechaM
	 *            The fechaM to set.
	 */
	public void setFechaM(String fechaM) {
		this.fechaM = fechaM;
	}

	/**
	 * @return Returns the fechaS.
	 */
	public String getFechaS() {
		return fechaS;
	}

	/**
	 * @param fechaS
	 *            The fechaS to set.
	 */
	public void setFechaS(String fechaS) {
		this.fechaS = fechaS;
	}

	/**
	 * @return Returns the organo.
	 */
	public String getOrgano() {
		return organo;
	}

	/**
	 * @param organo
	 *            The organo to set.
	 */
	public void setOrgano(String organo) {
		this.organo = organo;
	}

	/**
	 * @return Returns the gestorArchivo.
	 */
	public String getGestorArchivo() {
		return gestorArchivo;
	}

	/**
	 * @param gestorArchivo
	 *            The gestorArchivo to set.
	 */
	public void setGestorArchivo(String gestorArchivo) {
		this.gestorArchivo = gestorArchivo;
	}

	/**
	 * @return Returns the gestorOficina.
	 */
	public String getGestorOficina() {
		return gestorOficina;
	}

	/**
	 * @param gestorOficina
	 *            The gestorOficina to set.
	 */
	public void setGestorOficina(String gestorOficina) {
		this.gestorOficina = gestorOficina;
	}

	/**
	 * @return Returns the procedimiento.
	 */
	public String getProcedimiento() {
		return procedimiento;
	}

	/**
	 * @param procedimiento
	 *            The procedimiento to set.
	 */
	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}

	/**
	 * @return Returns the serie.
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param serie
	 *            The serie to set.
	 */
	public void setSerie(String serie) {
		this.serie = serie;
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

	/**
	 * @return Returns the tipos.
	 */
	public String[] getTipos() {
		return tipos;
	}

	/**
	 * @param tipos
	 *            The tipos to set.
	 */
	public void setTipos(String[] tipos) {
		this.tipos = tipos;
	}

	/**
	 * @return Returns the codigoSerie.
	 */
	public String getCodigoSerie() {
		return codigoSerie;
	}

	/**
	 * @param codigoSerie
	 *            The codigoSerie to set.
	 */
	public void setCodigoSerie(String codigoSerie) {
		this.codigoSerie = codigoSerie;
	}

	/**
	 * @return Returns the pageInfo.
	 */
	public PageInfo getPageInfo() {
		return pageInfo;
	}

	/**
	 * @param pageInfo
	 *            The pageInfo to set.
	 */
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
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

		String keyFecha = TransferenciasConstants.LABEL_TRANSFERENCIAS_RELACIONES_BUSQUEDA_FECHA;

		// Tipos
		if (ingresoDirecto != null) {
			if (ingresoDirecto.booleanValue()) {
				keyFecha = TransferenciasConstants.LABEL_TRANSFERENCIAS_RELACIONES_BUSQUEDA_FECHA_ESTADO;
			} else if (tipos.length == 0) {
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_REQUIRED,
								Messages.getString(
										ArchivoDetails.TRANSFERENCIAS_TIPO_BUSQUEDA_RELACION,
										request.getLocale())));
			}
		}

		// Estados
		if (estados.length == 0)
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									ArchivoDetails.TRANSFERENCIAS_ESTADOS_BUSQUEDA_RELACION,
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
							keyFecha, request.getLocale())));
		}

		return errors;
	}

	public BusquedaRelacionesVO getBusquedaRelacionesVO() {
		BusquedaRelacionesVO vo = new BusquedaRelacionesVO();

		vo.setCodigo(this.codigo);
		vo.setOrgano(this.organo);
		vo.setGestorOficina(this.gestorOficina);
		vo.setGestorArchivo(this.gestorArchivo);
		vo.setTipos(this.tipos);
		vo.setEstados(this.estados);
		vo.setAnio(this.anio);
		vo.setSerie(this.serie);
		vo.setCodigoSerie(this.codigoSerie);
		vo.setProcedimiento(this.procedimiento);
		vo.setIdFormato(this.idFormato);
		vo.setObservaciones(this.observaciones);
		vo.setIdsProductores(idsProductores);
		// Obtener el rango de fechas para la búsqueda
		CustomDateRange range = CustomDateFormat.getDateRange(this.fechaComp,
				new CustomDate(this.fechaFormato, this.fechaA, this.fechaM,
						this.fechaD, this.fechaS), new CustomDate(
						this.fechaIniFormato, this.fechaIniA, this.fechaIniM,
						this.fechaIniD, this.fechaIniS), new CustomDate(
						this.fechaFinFormato, this.fechaFinA, this.fechaFinM,
						this.fechaFinD, this.fechaFinS));

		vo.setPageInfo(this.pageInfo);

		if (ingresoDirecto != null && ingresoDirecto.booleanValue()) {
			vo.setIngresoDirecto(true);
			vo.setTipos(new String[] { new Integer(
					TipoTransferencia.INGRESO_DIRECTO.getIdentificador())
					.toString() });
			vo.setFechaEstadoInicio(range.getInitialDate());
			vo.setFechaEstadoFin(range.getFinalDate());
		} else {
			vo.setFechaInicio(range.getInitialDate());
			vo.setFechaFin(range.getFinalDate());
		}

		if (StringUtils.isNotBlank(idArchivo)) {
			vo.setArchivosReceptores(new String[] { idArchivo });
		}

		return vo;
	}

	public void setIngresoDirecto(Boolean ingresoDirecto) {
		this.ingresoDirecto = ingresoDirecto;
	}

	public Boolean getIngresoDirecto() {
		return ingresoDirecto;
	}

	public boolean esIngresoDirecto() {
		if (ingresoDirecto != null && ingresoDirecto.booleanValue()) {
			return true;
		}
		return false;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public String getIdArchivo() {
		return idArchivo;
	}

	public void setIdFormato(String idFormato) {
		this.idFormato = idFormato;
	}

	public String getIdFormato() {
		return idFormato;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public void setProductoresSeleccionados(String productoresSeleccionados) {
		this.productoresSeleccionados = productoresSeleccionados;
		if (StringUtils.isNotEmpty(productoresSeleccionados)) {
			String[] productores = productoresSeleccionados
					.split(Constants.DELIMITER_PARAMETER_FORMBEAN);

			if (productores != null) {
				this.idsProductores = new String[productores.length];
				this.nombreProductores = new String[productores.length];

				for (int i = 0; i < productores.length; i++) {
					String[] productor = productores[i]
							.split(Constants.DELIMITER_IDS);
					if (productor != null && productor.length == 2) {
						this.idsProductores[i] = productor[0];
						this.nombreProductores[i] = productor[1];
					}
				}
			}
		} else {
			this.idsProductores = null;
			this.nombreProductores = null;
		}
	}

	public String getProductoresSeleccionados() {
		return productoresSeleccionados;
	}

	public void setIdsProductores(String[] idsProductores) {
		this.idsProductores = idsProductores;
	}

	public String[] getIdsProductores() {
		return idsProductores;
	}

	public void setNombreProductores(String[] nombreProductores) {
		this.nombreProductores = nombreProductores;
	}

	public String[] getNombreProductores() {
		return nombreProductores;
	}

}
