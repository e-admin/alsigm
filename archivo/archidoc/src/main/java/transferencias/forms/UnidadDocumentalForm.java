package transferencias.forms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import transferencias.TransferenciasConstants;
import transferencias.vos.ParteUnidadDocumentalVO;
import transferencias.vos.ProductorUnidadDocumentalVO;
import transferencias.vos.UnidadDocumentalVO;

import common.Constants;
import common.Messages;
import common.util.StringUtils;

import es.archigest.framework.web.action.ArchigestActionForm;

/**
 * Formulario para la recogida de datos referentes a unidad documental
 * 
 */
public class UnidadDocumentalForm extends ArchigestActionForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			Constants.FORMATO_FECHA);

	int id;
	String procedimiento = null;
	String serie = null;
	String[] expediente = null;
	String numeroExpediente = null;
	String idProductor = null;
	String nombreProductor = null;
	String fechaInicio = null;
	String fechaFin = null;
	String asunto = null;
	String signaturaUDoc = null;
	String signaturaUI = null;
	String tituloDocumento = null;
	String descripcionDocumento = null;
	String repositorio = null;
	String localizador = null;
	int mantenerInformacion = 0;
	String[] rangoInicial = null;
	String[] rangoFinal = null;
	String[] seleccionDocumento = null;
	String[] selectedUdoc = null;

	/** Fichero del documento. */
	FormFile fichero = null;

	public String getProcedimiento() {
		return procedimiento;
	}

	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String[] getExpediente() {
		return expediente;
	}

	public void setExpediente(String[] expediente) {
		this.expediente = expediente;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaInicioAsDate() throws ParseException {
		return GenericValidator.isBlankOrNull(this.fechaInicio) ? null
				: dateFormat.parse(this.fechaInicio);
	}

	public void setFechaInicioAsDate(Date fechaInicio) {
		this.fechaInicio = fechaInicio != null ? dateFormat.format(fechaInicio)
				: null;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaFinAsDate() throws ParseException {
		return GenericValidator.isBlankOrNull(this.fechaFin) ? null
				: dateFormat.parse(fechaFin);
	}

	public void setFechaFinAsDate(Date fechaFin) {
		this.fechaFin = fechaFin != null ? dateFormat.format(fechaFin) : null;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public String getNombreProductor() {
		return nombreProductor;
	}

	public void setNombreProductor(String nombreProductor) {
		this.nombreProductor = nombreProductor;
	}

	/*
	 * public String getFormato() { return formato; } public void
	 * setFormato(String formato) { this.formato = formato; }
	 */

	/*
	 * public int getId() { return id; } public void setId(int id) { this.id =
	 * id; }
	 */
	public String getDescripcionDocumento() {
		return descripcionDocumento;
	}

	public void setDescripcionDocumento(String descripcionDocumento) {
		this.descripcionDocumento = descripcionDocumento;
	}

	/*
	 * public String getLocalizador() { return localizador; } public void
	 * setLocalizador(String localizador) { this.localizador = localizador; }
	 * public String getRepositorio() { return repositorio; } public void
	 * setRepositorio(String repositorio) { this.repositorio = repositorio; }
	 */
	/*
	 * public String getTipoDocumento() { return tipoDocumento; } public void
	 * setTipoDocumento(String tipoDocumento) { this.tipoDocumento =
	 * tipoDocumento; }
	 */
	public String getTituloDocumento() {
		return GenericValidator.isBlankOrNull(tituloDocumento) ? null
				: tituloDocumento;
	}

	public void setTituloDocumento(String tituloDocumento) {
		this.tituloDocumento = tituloDocumento;
	}

	public String[] getSeleccionDocumento() {
		return seleccionDocumento;
	}

	public void setSeleccionDocumento(String[] seleccionDocumento) {
		this.seleccionDocumento = seleccionDocumento;
	}

	public String getIdProductor() {
		return idProductor;
	}

	public void setIdProductor(String idProductor) {
		this.idProductor = idProductor;
	}

	public String[] getSelectedUdoc() {
		return selectedUdoc;
	}

	public void setSelectedUdoc(String[] selectedUdoc) {
		this.selectedUdoc = selectedUdoc;
	}

	public String getSignaturaUDoc() {
		return GenericValidator.isBlankOrNull(signaturaUDoc) ? null
				: signaturaUDoc;
	}

	public void setSignaturaUDoc(String signaturaUDoc) {
		this.signaturaUDoc = signaturaUDoc;
	}

	public String getSignaturaUI() {
		return GenericValidator.isBlankOrNull(signaturaUI) ? null : signaturaUI;
	}

	public void setSignaturaUI(String signaturaUI) {
		this.signaturaUI = signaturaUI;
	}

	/**
	 * @param udoc
	 */
	public void populateForEdition(UnidadDocumentalVO udoc, List partesUdoc) {
		if (udoc != null) {
			setAsunto(udoc.getAsunto());
			setNumeroExpediente(udoc.getNumeroExpediente());
			ProductorUnidadDocumentalVO productor = udoc.getProductor();
			if (productor != null) {
				setIdProductor(productor.getId());
				setNombreProductor(productor.getNombre());
			}

			setFechaInicioAsDate(udoc.getFechaInicio());
			setFechaFinAsDate(udoc.getFechaFin());
			// setRangoInicial(udoc.getExtraInfo().getRangosIni());
			// setRangoFinal(udoc.getExtraInfo().getRangosIni());

			// TODO Revisar por qué se hizo esto: Esto está bien??
			// establecer signaturas
			for (Iterator itPartes = partesUdoc.iterator(); itPartes.hasNext();) {
				ParteUnidadDocumentalVO aPart = (ParteUnidadDocumentalVO) itPartes
						.next();
				setSignaturaUDoc(aPart.getSignaturaUdoc());
				setSignaturaUI(aPart.getSignaturaUI());
			}
		}

	}

	public void nuevaUnidadDocumental() {
		id = -1;
		procedimiento = null;
		serie = null;
		expediente = null;

		numeroExpediente = null;
		fechaInicio = null;
		fechaFin = null;
		asunto = null;
		signaturaUDoc = null;
		signaturaUI = null;
		tituloDocumento = null;
		descripcionDocumento = null;
		repositorio = null;
		localizador = null;

		seleccionDocumento = null;
		selectedUdoc = null;
	}

	public String getMantenerInformacion() {
		return String.valueOf(mantenerInformacion);
	}

	public int getIntMantenerInformacion() {
		return mantenerInformacion;
	}

	public void setMantenerInformacion(String mantenerInformacion) {
		try {
			this.mantenerInformacion = Integer.parseInt(mantenerInformacion);
		} catch (Exception e) {
			this.mantenerInformacion = 0;
		}
	}

	public void setMantenerInformacion(int mantenerInformacion) {
		this.mantenerInformacion = mantenerInformacion;
	}

	public String[] getRangoFinal() {
		return rangoFinal;
	}

	public void setRangoFinal(String[] rangoFinal) {
		this.rangoFinal = rangoFinal;
	}

	public String[] getRangoInicial() {
		return rangoInicial;
	}

	public void setRangoInicial(String[] rangoInicial) {
		this.rangoInicial = rangoInicial;
	}

	/**
	 * @return Returns the fichero.
	 */
	public FormFile getFichero() {
		return fichero;
	}

	/**
	 * @param fichero
	 *            The fichero to set.
	 */
	public void setFichero(FormFile fichero) {
		this.fichero = fichero;
	}

	/**
	 * Valida el formulario
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	public ActionErrors validateFile(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		if ((fichero == null) || StringUtils.isBlank(fichero.getFileName())) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									TransferenciasConstants.LABEL_TRANSFERENCIAS_IMPORT_FILE,
									request.getLocale())));
		}

		return errors;
	}
}