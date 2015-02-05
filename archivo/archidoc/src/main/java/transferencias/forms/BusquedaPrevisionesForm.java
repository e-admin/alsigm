package transferencias.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import transferencias.TransferenciasConstants;
import transferencias.vos.BusquedaPrevisionesVO;
import auditoria.ArchivoDetails;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;
import common.pagination.PageInfo;
import common.util.CustomDate;
import common.util.CustomDateFormat;
import common.util.CustomDateRange;

/**
 * Formulario de búsqueda de previsiones.
 */
public class BusquedaPrevisionesForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String codigo = null;
	private String organo = null;
	private String usuario = null;
	private String fondo = null;
	private String[] tipos = new String[0];
	private String anio = null;
	private String[] estados = new String[0];

	// Fecha de transferencia
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

	/**
	 * Constructor.
	 */
	public BusquedaPrevisionesForm() {
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
	 * @return Returns the fondo.
	 */
	public String getFondo() {
		return fondo;
	}

	/**
	 * @param fondo
	 *            The fondo to set.
	 */
	public void setFondo(String fondo) {
		this.fondo = fondo;
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
	 * @return Returns the tipo.
	 */
	public String[] getTipos() {
		return tipos;
	}

	/**
	 * @param tipo
	 *            The tipo to set.
	 */
	public void setTipos(String[] tipos) {
		this.tipos = tipos;
	}

	/**
	 * @return Returns the usuario.
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            The usuario to set.
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
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

		// Tipos
		if (tipos.length == 0)
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									ArchivoDetails.TRANSFERENCIAS_TIPO_BUSQUEDA_PREVISION,
									request.getLocale())));

		// Estados
		if (estados.length == 0)
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									ArchivoDetails.TRANSFERENCIAS_ESTADOS_BUSQUEDA_PREVISION,
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
					new ActionError(
							Constants.ERROR_DATE,
							Messages.getString(
									TransferenciasConstants.LABEL_TRANSFERENCIAS_PREVISIONES_BUSQUEDA_FECHA,
									request.getLocale())));
		}

		return errors;
	}

	public BusquedaPrevisionesVO getBusquedaPrevisionesVO() {
		BusquedaPrevisionesVO vo = new BusquedaPrevisionesVO();

		vo.setCodigo(this.codigo);
		vo.setOrgano(this.organo);
		vo.setUsuario(this.usuario);
		vo.setFondo(this.fondo);
		vo.setTipos(this.tipos);
		vo.setEstados(this.estados);
		vo.setAnio(this.anio);

		// Obtener el rango de fechas para la búsqueda
		CustomDateRange range = CustomDateFormat.getDateRange(this.fechaComp,
				new CustomDate(this.fechaFormato, this.fechaA, this.fechaM,
						this.fechaD, this.fechaS), new CustomDate(
						this.fechaIniFormato, this.fechaIniA, this.fechaIniM,
						this.fechaIniD, this.fechaIniS), new CustomDate(
						this.fechaFinFormato, this.fechaFinA, this.fechaFinM,
						this.fechaFinD, this.fechaFinS));

		vo.setFechaInicio(range.getInitialDate());
		vo.setFechaFin(range.getFinalDate());

		vo.setPageInfo(this.pageInfo);

		return vo;
	}
}
