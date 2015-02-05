package auditoria.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import auditoria.vos.BusquedaPistasVO;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;
import common.util.CustomDate;
import common.util.CustomDateFormat;
import common.util.CustomDateRange;

/**
 * Formulario para la búsqueda de pistas de auditoría.
 */
public class BusquedaPistasForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Modulo al que pertenece el evento */
	private int modulo = 0;
	/** Accion */
	private int accion = 0;
	/** Grupo de usuarios */
	private String grupo = null;
	/** Direccion IP desde la que se realizo el evento */
	private String ip = null;
	/** Campos para la fecha del evento */
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

	/**
	 * Constructor.
	 */
	public BusquedaPistasForm() {
		super();
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
	 * Construye un VO con la información del formulario.
	 * 
	 * @param vo
	 *            Value Object.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public void populate(BusquedaPistasVO vo) {
		vo.setAccion(this.accion);
		vo.setGrupo(this.grupo);
		vo.setIp(this.ip);
		vo.setModulo(this.modulo);

		// Obtener el rango de fechas para la búsqueda
		CustomDateRange range = CustomDateFormat.getDateRange(this.fechaComp,
				new CustomDate(this.fechaFormato, this.fechaA, this.fechaM,
						this.fechaD, this.fechaS), new CustomDate(
						this.fechaIniFormato, this.fechaIniA, this.fechaIniM,
						this.fechaIniD, this.fechaIniS), new CustomDate(
						this.fechaFinFormato, this.fechaFinA, this.fechaFinM,
						this.fechaFinD, this.fechaFinS));

		vo.setFechaIni(range.getInitialDate());
		vo.setFechaFin(range.getFinalDate());
	}

	/**
	 * Inicia el formulario.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
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

		// Fechas
		if (!new CustomDate(this.fechaFormato, this.fechaA, this.fechaM,
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
							Constants.ETIQUETA_FECHA2, request.getLocale())));
		}

		return errors;
	}

	public int getAccion() {
		return accion;
	}

	public void setAccion(int accion) {
		this.accion = accion;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getModulo() {
		return modulo;
	}

	public void setModulo(int modulo) {
		this.modulo = modulo;
	}
}
