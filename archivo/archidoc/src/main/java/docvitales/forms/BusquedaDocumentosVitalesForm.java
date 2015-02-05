package docvitales.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;
import common.pagination.PageInfo;
import common.util.ArrayUtils;
import common.util.CustomDate;
import common.util.CustomDateFormat;
import common.util.CustomDateRange;

import docvitales.DocumentosVitalesConstants;
import docvitales.vos.BusquedaDocumentosVitalesVO;

/**
 * Formulario para la información de un tipo de documento vital.
 */
public class BusquedaDocumentosVitalesForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Número de identidad del ciudadano. */
	private String numIdentidad = null;

	/** Identidad del ciudadano. */
	private String identidad = null;

	/** Tipos de documentos vitales. */
	private String[] tiposDocumentos = new String[0];

	/** Estado del documento vital. */
	private String[] estados = new String[0];

	private String fechaCaducidadComp = null;
	private String fechaCaducidadFormato = null;
	private String fechaCaducidadA = null;
	private String fechaCaducidadM = null;
	private String fechaCaducidadD = null;
	private String fechaCaducidadIniFormato = null;
	private String fechaCaducidadIniA = null;
	private String fechaCaducidadIniM = null;
	private String fechaCaducidadIniD = null;
	private String fechaCaducidadFinFormato = null;
	private String fechaCaducidadFinA = null;
	private String fechaCaducidadFinM = null;
	private String fechaCaducidadFinD = null;

	/** Comparador de número de accesos. */
	private String numAccesosComp = null;

	/** Número de accesos. */
	private String numAccesos = null;

	private String fechaUltimoAccesoComp = null;
	private String fechaUltimoAccesoFormato = null;
	private String fechaUltimoAccesoA = null;
	private String fechaUltimoAccesoM = null;
	private String fechaUltimoAccesoD = null;
	private String fechaUltimoAccesoIniFormato = null;
	private String fechaUltimoAccesoIniA = null;
	private String fechaUltimoAccesoIniM = null;
	private String fechaUltimoAccesoIniD = null;
	private String fechaUltimoAccesoFinFormato = null;
	private String fechaUltimoAccesoFinA = null;
	private String fechaUltimoAccesoFinM = null;
	private String fechaUltimoAccesoFinD = null;

	/** Información de la paginación. */
	private PageInfo pageInfo = null;

	/**
	 * Constructor.
	 */
	public BusquedaDocumentosVitalesForm() {
		super();
	}

	/**
	 * Inicia el formulario.
	 * 
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		tiposDocumentos = new String[0];
		estados = new String[0];
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
	 * @return Returns the identidad.
	 */
	public String getIdentidad() {
		return identidad;
	}

	/**
	 * @param identidad
	 *            The identidad to set.
	 */
	public void setIdentidad(String identidad) {
		this.identidad = identidad;
	}

	/**
	 * @return Returns the numIdentidad.
	 */
	public String getNumIdentidad() {
		return numIdentidad;
	}

	/**
	 * @param numIdentidad
	 *            The numIdentidad to set.
	 */
	public void setNumIdentidad(String numIdentidad) {
		this.numIdentidad = numIdentidad;
	}

	/**
	 * @return Returns the numAccesos.
	 */
	public String getNumAccesos() {
		return numAccesos;
	}

	/**
	 * @param numAccesos
	 *            The numAccesos to set.
	 */
	public void setNumAccesos(String numAccesos) {
		this.numAccesos = numAccesos;
	}

	/**
	 * @return Returns the numAccesosComp.
	 */
	public String getNumAccesosComp() {
		return numAccesosComp;
	}

	/**
	 * @param numAccesosComp
	 *            The numAccesosComp to set.
	 */
	public void setNumAccesosComp(String numAccesosComp) {
		this.numAccesosComp = numAccesosComp;
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
	 * @return Returns the tiposDocumentos.
	 */
	public String[] getTiposDocumentos() {
		return tiposDocumentos;
	}

	/**
	 * @param tiposDocumentos
	 *            The tiposDocumentos to set.
	 */
	public void setTiposDocumentos(String[] tiposDocumentos) {
		this.tiposDocumentos = tiposDocumentos;
	}

	/**
	 * @return Returns the fechaCaducidadA.
	 */
	public String getFechaCaducidadA() {
		return fechaCaducidadA;
	}

	/**
	 * @param fechaCaducidadA
	 *            The fechaCaducidadA to set.
	 */
	public void setFechaCaducidadA(String fechaCaducidadA) {
		this.fechaCaducidadA = fechaCaducidadA;
	}

	/**
	 * @return Returns the fechaCaducidadComp.
	 */
	public String getFechaCaducidadComp() {
		return fechaCaducidadComp;
	}

	/**
	 * @param fechaCaducidadComp
	 *            The fechaCaducidadComp to set.
	 */
	public void setFechaCaducidadComp(String fechaCaducidadComp) {
		this.fechaCaducidadComp = fechaCaducidadComp;
	}

	/**
	 * @return Returns the fechaCaducidadD.
	 */
	public String getFechaCaducidadD() {
		return fechaCaducidadD;
	}

	/**
	 * @param fechaCaducidadD
	 *            The fechaCaducidadD to set.
	 */
	public void setFechaCaducidadD(String fechaCaducidadD) {
		this.fechaCaducidadD = fechaCaducidadD;
	}

	/**
	 * @return Returns the fechaCaducidadFinA.
	 */
	public String getFechaCaducidadFinA() {
		return fechaCaducidadFinA;
	}

	/**
	 * @param fechaCaducidadFinA
	 *            The fechaCaducidadFinA to set.
	 */
	public void setFechaCaducidadFinA(String fechaCaducidadFinA) {
		this.fechaCaducidadFinA = fechaCaducidadFinA;
	}

	/**
	 * @return Returns the fechaCaducidadFinD.
	 */
	public String getFechaCaducidadFinD() {
		return fechaCaducidadFinD;
	}

	/**
	 * @param fechaCaducidadFinD
	 *            The fechaCaducidadFinD to set.
	 */
	public void setFechaCaducidadFinD(String fechaCaducidadFinD) {
		this.fechaCaducidadFinD = fechaCaducidadFinD;
	}

	/**
	 * @return Returns the fechaCaducidadFinFormato.
	 */
	public String getFechaCaducidadFinFormato() {
		return fechaCaducidadFinFormato;
	}

	/**
	 * @param fechaCaducidadFinFormato
	 *            The fechaCaducidadFinFormato to set.
	 */
	public void setFechaCaducidadFinFormato(String fechaCaducidadFinFormato) {
		this.fechaCaducidadFinFormato = fechaCaducidadFinFormato;
	}

	/**
	 * @return Returns the fechaCaducidadFinM.
	 */
	public String getFechaCaducidadFinM() {
		return fechaCaducidadFinM;
	}

	/**
	 * @param fechaCaducidadFinM
	 *            The fechaCaducidadFinM to set.
	 */
	public void setFechaCaducidadFinM(String fechaCaducidadFinM) {
		this.fechaCaducidadFinM = fechaCaducidadFinM;
	}

	/**
	 * @return Returns the fechaCaducidadFormato.
	 */
	public String getFechaCaducidadFormato() {
		return fechaCaducidadFormato;
	}

	/**
	 * @param fechaCaducidadFormato
	 *            The fechaCaducidadFormato to set.
	 */
	public void setFechaCaducidadFormato(String fechaCaducidadFormato) {
		this.fechaCaducidadFormato = fechaCaducidadFormato;
	}

	/**
	 * @return Returns the fechaCaducidadIniA.
	 */
	public String getFechaCaducidadIniA() {
		return fechaCaducidadIniA;
	}

	/**
	 * @param fechaCaducidadIniA
	 *            The fechaCaducidadIniA to set.
	 */
	public void setFechaCaducidadIniA(String fechaCaducidadIniA) {
		this.fechaCaducidadIniA = fechaCaducidadIniA;
	}

	/**
	 * @return Returns the fechaCaducidadIniD.
	 */
	public String getFechaCaducidadIniD() {
		return fechaCaducidadIniD;
	}

	/**
	 * @param fechaCaducidadIniD
	 *            The fechaCaducidadIniD to set.
	 */
	public void setFechaCaducidadIniD(String fechaCaducidadIniD) {
		this.fechaCaducidadIniD = fechaCaducidadIniD;
	}

	/**
	 * @return Returns the fechaCaducidadIniFormato.
	 */
	public String getFechaCaducidadIniFormato() {
		return fechaCaducidadIniFormato;
	}

	/**
	 * @param fechaCaducidadIniFormato
	 *            The fechaCaducidadIniFormato to set.
	 */
	public void setFechaCaducidadIniFormato(String fechaCaducidadIniFormato) {
		this.fechaCaducidadIniFormato = fechaCaducidadIniFormato;
	}

	/**
	 * @return Returns the fechaCaducidadIniM.
	 */
	public String getFechaCaducidadIniM() {
		return fechaCaducidadIniM;
	}

	/**
	 * @param fechaCaducidadIniM
	 *            The fechaCaducidadIniM to set.
	 */
	public void setFechaCaducidadIniM(String fechaCaducidadIniM) {
		this.fechaCaducidadIniM = fechaCaducidadIniM;
	}

	/**
	 * @return Returns the fechaCaducidadM.
	 */
	public String getFechaCaducidadM() {
		return fechaCaducidadM;
	}

	/**
	 * @param fechaCaducidadM
	 *            The fechaCaducidadM to set.
	 */
	public void setFechaCaducidadM(String fechaCaducidadM) {
		this.fechaCaducidadM = fechaCaducidadM;
	}

	/**
	 * @return Returns the fechaUltimoAccesoA.
	 */
	public String getFechaUltimoAccesoA() {
		return fechaUltimoAccesoA;
	}

	/**
	 * @param fechaUltimoAccesoA
	 *            The fechaUltimoAccesoA to set.
	 */
	public void setFechaUltimoAccesoA(String fechaUltimoAccesoA) {
		this.fechaUltimoAccesoA = fechaUltimoAccesoA;
	}

	/**
	 * @return Returns the fechaUltimoAccesoComp.
	 */
	public String getFechaUltimoAccesoComp() {
		return fechaUltimoAccesoComp;
	}

	/**
	 * @param fechaUltimoAccesoComp
	 *            The fechaUltimoAccesoComp to set.
	 */
	public void setFechaUltimoAccesoComp(String fechaUltimoAccesoComp) {
		this.fechaUltimoAccesoComp = fechaUltimoAccesoComp;
	}

	/**
	 * @return Returns the fechaUltimoAccesoD.
	 */
	public String getFechaUltimoAccesoD() {
		return fechaUltimoAccesoD;
	}

	/**
	 * @param fechaUltimoAccesoD
	 *            The fechaUltimoAccesoD to set.
	 */
	public void setFechaUltimoAccesoD(String fechaUltimoAccesoD) {
		this.fechaUltimoAccesoD = fechaUltimoAccesoD;
	}

	/**
	 * @return Returns the fechaUltimoAccesoFinA.
	 */
	public String getFechaUltimoAccesoFinA() {
		return fechaUltimoAccesoFinA;
	}

	/**
	 * @param fechaUltimoAccesoFinA
	 *            The fechaUltimoAccesoFinA to set.
	 */
	public void setFechaUltimoAccesoFinA(String fechaUltimoAccesoFinA) {
		this.fechaUltimoAccesoFinA = fechaUltimoAccesoFinA;
	}

	/**
	 * @return Returns the fechaUltimoAccesoFinD.
	 */
	public String getFechaUltimoAccesoFinD() {
		return fechaUltimoAccesoFinD;
	}

	/**
	 * @param fechaUltimoAccesoFinD
	 *            The fechaUltimoAccesoFinD to set.
	 */
	public void setFechaUltimoAccesoFinD(String fechaUltimoAccesoFinD) {
		this.fechaUltimoAccesoFinD = fechaUltimoAccesoFinD;
	}

	/**
	 * @return Returns the fechaUltimoAccesoFinFormato.
	 */
	public String getFechaUltimoAccesoFinFormato() {
		return fechaUltimoAccesoFinFormato;
	}

	/**
	 * @param fechaUltimoAccesoFinFormato
	 *            The fechaUltimoAccesoFinFormato to set.
	 */
	public void setFechaUltimoAccesoFinFormato(
			String fechaUltimoAccesoFinFormato) {
		this.fechaUltimoAccesoFinFormato = fechaUltimoAccesoFinFormato;
	}

	/**
	 * @return Returns the fechaUltimoAccesoFinM.
	 */
	public String getFechaUltimoAccesoFinM() {
		return fechaUltimoAccesoFinM;
	}

	/**
	 * @param fechaUltimoAccesoFinM
	 *            The fechaUltimoAccesoFinM to set.
	 */
	public void setFechaUltimoAccesoFinM(String fechaUltimoAccesoFinM) {
		this.fechaUltimoAccesoFinM = fechaUltimoAccesoFinM;
	}

	/**
	 * @return Returns the fechaUltimoAccesoFormato.
	 */
	public String getFechaUltimoAccesoFormato() {
		return fechaUltimoAccesoFormato;
	}

	/**
	 * @param fechaUltimoAccesoFormato
	 *            The fechaUltimoAccesoFormato to set.
	 */
	public void setFechaUltimoAccesoFormato(String fechaUltimoAccesoFormato) {
		this.fechaUltimoAccesoFormato = fechaUltimoAccesoFormato;
	}

	/**
	 * @return Returns the fechaUltimoAccesoIniA.
	 */
	public String getFechaUltimoAccesoIniA() {
		return fechaUltimoAccesoIniA;
	}

	/**
	 * @param fechaUltimoAccesoIniA
	 *            The fechaUltimoAccesoIniA to set.
	 */
	public void setFechaUltimoAccesoIniA(String fechaUltimoAccesoIniA) {
		this.fechaUltimoAccesoIniA = fechaUltimoAccesoIniA;
	}

	/**
	 * @return Returns the fechaUltimoAccesoIniD.
	 */
	public String getFechaUltimoAccesoIniD() {
		return fechaUltimoAccesoIniD;
	}

	/**
	 * @param fechaUltimoAccesoIniD
	 *            The fechaUltimoAccesoIniD to set.
	 */
	public void setFechaUltimoAccesoIniD(String fechaUltimoAccesoIniD) {
		this.fechaUltimoAccesoIniD = fechaUltimoAccesoIniD;
	}

	/**
	 * @return Returns the fechaUltimoAccesoIniFormato.
	 */
	public String getFechaUltimoAccesoIniFormato() {
		return fechaUltimoAccesoIniFormato;
	}

	/**
	 * @param fechaUltimoAccesoIniFormato
	 *            The fechaUltimoAccesoIniFormato to set.
	 */
	public void setFechaUltimoAccesoIniFormato(
			String fechaUltimoAccesoIniFormato) {
		this.fechaUltimoAccesoIniFormato = fechaUltimoAccesoIniFormato;
	}

	/**
	 * @return Returns the fechaUltimoAccesoIniM.
	 */
	public String getFechaUltimoAccesoIniM() {
		return fechaUltimoAccesoIniM;
	}

	/**
	 * @param fechaUltimoAccesoIniM
	 *            The fechaUltimoAccesoIniM to set.
	 */
	public void setFechaUltimoAccesoIniM(String fechaUltimoAccesoIniM) {
		this.fechaUltimoAccesoIniM = fechaUltimoAccesoIniM;
	}

	/**
	 * @return Returns the fechaUltimoAccesoM.
	 */
	public String getFechaUltimoAccesoM() {
		return fechaUltimoAccesoM;
	}

	/**
	 * @param fechaUltimoAccesoM
	 *            The fechaUltimoAccesoM to set.
	 */
	public void setFechaUltimoAccesoM(String fechaUltimoAccesoM) {
		this.fechaUltimoAccesoM = fechaUltimoAccesoM;
	}

	/**
	 * Construye un VO con la información del formulario.
	 * 
	 * @return Value Object.
	 */
	public BusquedaDocumentosVitalesVO getBusquedaVO() {
		BusquedaDocumentosVitalesVO vo = new BusquedaDocumentosVitalesVO();
		vo.setNumIdentidad(this.numIdentidad);
		vo.setIdentidad(this.identidad);
		vo.setEstados(this.estados);
		vo.setTiposDocumentos(this.tiposDocumentos);

		// Obtener el rango de fechas de caducidad
		CustomDateRange range = CustomDateFormat
				.getDateRange(this.fechaCaducidadComp, new CustomDate(
						this.fechaCaducidadFormato, this.fechaCaducidadA,
						this.fechaCaducidadM, this.fechaCaducidadD, null),
						new CustomDate(this.fechaCaducidadIniFormato,
								this.fechaCaducidadIniA,
								this.fechaCaducidadIniM,
								this.fechaCaducidadIniD, null), new CustomDate(
								this.fechaCaducidadFinFormato,
								this.fechaCaducidadFinA,
								this.fechaCaducidadFinM,
								this.fechaCaducidadFinD, null));

		vo.setFechaCaducidadIni(range.getInitialDate());
		vo.setFechaCaducidadFin(range.getFinalDate());

		vo.setNumAccesosComp(this.numAccesosComp);
		vo.setNumAccesos(this.numAccesos);

		// Obtener el rango de fechas de último acceso
		range = CustomDateFormat.getDateRange(this.fechaUltimoAccesoComp,
				new CustomDate(this.fechaUltimoAccesoFormato,
						this.fechaUltimoAccesoA, this.fechaUltimoAccesoM,
						this.fechaUltimoAccesoD, null), new CustomDate(
						this.fechaUltimoAccesoIniFormato,
						this.fechaUltimoAccesoIniA, this.fechaUltimoAccesoIniM,
						this.fechaUltimoAccesoIniD, null), new CustomDate(
						this.fechaUltimoAccesoFinFormato,
						this.fechaUltimoAccesoFinA, this.fechaUltimoAccesoFinM,
						this.fechaUltimoAccesoFinD, null));

		vo.setFechaUltimoAccesoIni(range.getInitialDate());
		vo.setFechaUltimoAccesoFin(range.getFinalDate());

		vo.setPageInfo(this.pageInfo);
		return vo;
	}

	/**
	 * Valida el formulario.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		List tiposDocumentosVitales = (List) request
				.getAttribute(DocumentosVitalesConstants.TIPOS_DOCUMENTOS_VITALES_KEY);

		// Tipos de Documentos
		if (ArrayUtils.isEmpty(tiposDocumentos)
				&& tiposDocumentosVitales != null
				&& tiposDocumentosVitales.size() > 0) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									DocumentosVitalesConstants.LABEL_DOCVITALES_DOCVITAL_TIPO_DOCUMENTO,
									request.getLocale())));
		}

		// Estados
		if (ArrayUtils.isEmpty(estados)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									DocumentosVitalesConstants.LABEL_DOCVITALES_DOCVITAL_ESTADO,
									request.getLocale())));
		}

		// Fechas de caducidad
		if (!new CustomDate(this.fechaCaducidadFormato, this.fechaCaducidadA,
				this.fechaCaducidadM, this.fechaCaducidadD, null).validate()
				|| !new CustomDate(this.fechaCaducidadIniFormato,
						this.fechaCaducidadIniA, this.fechaCaducidadIniM,
						this.fechaCaducidadIniD, null).validate()
				|| !new CustomDate(this.fechaCaducidadFinFormato,
						this.fechaCaducidadFinA, this.fechaCaducidadFinM,
						this.fechaCaducidadFinD, null).validate()) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_DATE,
							Messages.getString(
									DocumentosVitalesConstants.LABEL_DOCVITALES_DOCVITAL_FECHA_CADUCIDAD,
									request.getLocale())));
		}

		// Fechas del último acceso
		if (!new CustomDate(this.fechaUltimoAccesoFormato,
				this.fechaUltimoAccesoA, this.fechaUltimoAccesoM,
				this.fechaUltimoAccesoD, null).validate()
				|| !new CustomDate(this.fechaUltimoAccesoIniFormato,
						this.fechaUltimoAccesoIniA, this.fechaUltimoAccesoIniM,
						this.fechaUltimoAccesoIniD, null).validate()
				|| !new CustomDate(this.fechaUltimoAccesoFinFormato,
						this.fechaUltimoAccesoFinA, this.fechaUltimoAccesoFinM,
						this.fechaUltimoAccesoFinD, null).validate()) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_DATE,
							Messages.getString(
									DocumentosVitalesConstants.LABEL_DOCVITALES_DOCVITAL_FECHA_ULTIMO_ACCESO,
									request.getLocale())));
		}

		return errors;
	}

}
