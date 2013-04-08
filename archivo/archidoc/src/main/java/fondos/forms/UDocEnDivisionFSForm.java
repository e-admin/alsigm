package fondos.forms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.validator.GenericValidator;

import common.Constants;

import es.archigest.framework.web.action.ArchigestActionForm;
import fondos.vos.UDocEnFraccionSerieVO;

/**
 * Formulario para la recogida de datos referentes a unidad documental
 * 
 */
public class UDocEnDivisionFSForm extends ArchigestActionForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			Constants.FORMATO_FECHA);

	String idUDoc;
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
	int mantenerInformacion = 0;
	String[] rangoInicial = null;
	String[] rangoFinal = null;
	String[] selectedUdoc = null;

	// String idNivelDocumental = null;
	// String idFicha = null;

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
	 * @param udocEnFS
	 */
	public void populateForEdition(UDocEnFraccionSerieVO udocEnFS) {
		if (udocEnFS != null) {
			setAsunto(udocEnFS.getAsunto());
			setNumeroExpediente(udocEnFS.getNumExp());
			/*
			 * ProductorUnidadDocumentalVO productor = udoc.getProductor(); if
			 * (productor != null) { setIdProductor(productor.getId());
			 * setNombreProductor(productor.getNombre()); }
			 */
			setFechaInicioAsDate(udocEnFS.getFechaExtIni());
			setFechaFinAsDate(udocEnFS.getFechaExtFin());
		}

	}

	public void nuevaUnidadDocumental() {
		serie = null;
		expediente = null;
		numeroExpediente = null;
		fechaInicio = null;
		fechaFin = null;
		asunto = null;
		signaturaUDoc = null;
		signaturaUI = null;
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

	/*
	 * public String getIdNivelDocumental() { return idNivelDocumental; } public
	 * void setIdNivelDocumental(String idNivelDocumental) {
	 * this.idNivelDocumental = idNivelDocumental; } public String getIdFicha()
	 * { return idFicha; } public void setIdFicha(String idFicha) { this.idFicha
	 * = idFicha; }
	 */
}