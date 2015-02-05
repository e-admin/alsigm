package descripcion.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import auditoria.ArchivoDetails;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;
import common.util.CustomDate;
import common.util.CustomDateFormat;
import common.util.CustomDateRange;
import common.util.StringUtils;

import descripcion.DescripcionConstants;
import descripcion.vos.BusquedaGeneralAutVO;

/**
 * Formulario para la búsqueda general de autoridades.
 */
public class BusquedaGeneralAutForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String nombre = null;
	private String texto = null;
	private String descripcion = null;
	private String numeroComp = null;
	private String numero = null;
	private String tipoMedida = null;
	private String unidadMedida = null;
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
	private String calificador = null;
	private String[] listasDescriptoras = new String[0];

	/**
	 * Constructor.
	 */
	public BusquedaGeneralAutForm() {
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
	 * @return Returns the texto.
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * @param texto
	 *            The texto to set.
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}

	/**
	 * @return Returns the calificador.
	 */
	public String getCalificador() {
		return calificador;
	}

	/**
	 * @param calificador
	 *            The calificador to set.
	 */
	public void setCalificador(String calificador) {
		this.calificador = calificador;
	}

	/**
	 * @return Returns the listasDescriptoras.
	 */
	public String[] getListasDescriptoras() {
		return listasDescriptoras;
	}

	/**
	 * @param listasDescriptoras
	 *            The listasDescriptoras to set.
	 */
	public void setListasDescriptoras(String[] listasDescriptoras) {
		this.listasDescriptoras = listasDescriptoras;
	}

	/**
	 * @return Returns the nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return Returns the numero.
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero
	 *            The numero to set.
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return Returns the numeroComp.
	 */
	public String getNumeroComp() {
		return numeroComp;
	}

	/**
	 * @param numeroComp
	 *            The numeroComp to set.
	 */
	public void setNumeroComp(String numeroComp) {
		this.numeroComp = numeroComp;
	}

	/**
	 * @return Returns the tipoMedida.
	 */
	public String getTipoMedida() {
		return tipoMedida;
	}

	/**
	 * @param tipoMedida
	 *            The tipoMedida to set.
	 */
	public void setTipoMedida(String tipoMedida) {
		this.tipoMedida = tipoMedida;
	}

	/**
	 * @return Returns the unidadesMedida.
	 */
	public String getUnidadMedida() {
		return unidadMedida;
	}

	/**
	 * @param unidadesMedida
	 *            The unidadesMedida to set.
	 */
	public void setUnidadMedida(String unidadesMedida) {
		this.unidadMedida = unidadesMedida;
	}

	/**
	 * @return Returns the descripcion.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            The descripcion to set.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Construye un VO con la información del formulario.
	 * 
	 * @param vo
	 *            Value Object.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public void populate(BusquedaGeneralAutVO vo) {
		vo.setNombre(this.nombre);
		vo.setTexto(this.texto);
		vo.setDescripcion(this.descripcion);
		vo.setNumeroComp(this.numeroComp);
		vo.setNumero(this.numero);
		vo.setTipoMedida(this.tipoMedida);
		vo.setUnidadMedida(this.unidadMedida);
		vo.setListasDescriptoras(this.listasDescriptoras);

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
		vo.setCalificador(this.getCalificador());
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
		listasDescriptoras = new String[0];
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
					new ActionError(
							Constants.ERROR_DATE,
							Messages.getString(
									DescripcionConstants.DESCRIPCION_BUSQUEDA_AUT_FECHA,
									request.getLocale())));
		}

		// Número
		if (StringUtils.isNotBlank(numero) && !NumberUtils.isNumber(numero)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_INVALID,
							Messages.getString(
									ArchivoDetails.DESCRIPCION_BUSQUEDA_DATO_NUMERICO,
									request.getLocale())));
		}

		// Listas Descriptoras
		if (listasDescriptoras.length == 0) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									ArchivoDetails.DESCRIPCION_BUSQUEDA_LISTAS_DESCRIPTORAS,
									request.getLocale())));
		}

		return errors;
	}

}
