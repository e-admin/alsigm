package es.ieci.tecdoc.isicres.api.compulsa.justificante.business.vo;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Locale;

import es.ieci.tecdoc.isicres.api.business.vo.BaseRegistroVO;


/**
 * Clase de propiedades con los datos para compulsar un documento PDF.
 * @author IECISA
 *
 */
public class ISicresCompulsaJustificanteVO {

	//Locale
	private Locale locale = null;
	//Entidad
	private String entidad = null;
	//Fecha de Compulsa
	private Date fechaCompulsa = null;
	//Numero de Registro
	private String numeroRegistro = null;
	//Path completo a la imagen que se usara de marca de agua.
	private String fondoPath = null;
	//Path completo al fichero de texto con la información a incluir en el PDF.
	private String datosPath = null;
	//CN del certificado usado para firmar el PDF
	private String CNcertificado = null;
	//Stream al PDF Original
	private InputStream pdfOriginalStream = null;
	//Stream al PDF Compulsado
	private OutputStream pdfCompulsaStream = null;
	//Datos del registro
	private BaseRegistroVO registro = null;
	//Localizador
	private String locator = null;



	/**
	 * @param entidad the entidad to set
	 */
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
	/**
	 * @return the entidad
	 */
	public String getEntidad() {
		return entidad;
	}
	/**
	 * @param fechaCompulsa the fechaCompulsa to set
	 */
	public void setFechaCompulsa(Date fechaCompulsa) {
		this.fechaCompulsa = fechaCompulsa;
	}
	/**
	 * @return the fechaCompulsa
	 */
	public Date getFechaCompulsa() {
		return fechaCompulsa;
	}
	/**
	 * @param numeroRegistro the numeroRegistro to set
	 */
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	/**
	 * @return the numeroRegistro
	 */
	public String getNumeroRegistro() {
		return numeroRegistro;
	}
	/**
	 * @param pathFondo the fondoPath to set
	 */
	public void setFondoPath(String fondoPath) {
		this.fondoPath = fondoPath;
	}
	/**
	 * @return the fondoPath
	 */
	public String getFondoPath() {
		return fondoPath;
	}
	/**
	 * @param pathDatos the datosPath to set
	 */
	public void setDatosPath(String datosPath) {
		this.datosPath = datosPath;
	}
	/**
	 * @return the datosPath
	 */
	public String getDatosPath() {
		return datosPath;
	}
	/**
	 * @param cNcertificado the cNcertificado to set
	 */
	public void setCNcertificado(String cNcertificado) {
		CNcertificado = cNcertificado;
	}
	/**
	 * @return the cNcertificado
	 */
	public String getCNcertificado() {
		return CNcertificado;
	}
	/**
	 * @param pdfOriginalStream the pdfOriginalStream to set
	 */
	public void setPdfOriginalStream(InputStream pdfOriginalStream) {
		this.pdfOriginalStream = pdfOriginalStream;
	}
	/**
	 * @return the pdfOriginalStream
	 */
	public InputStream getPdfOriginalStream() {
		return pdfOriginalStream;
	}
	/**
	 * @param pdfCompulsaStream the pdfCompulsaStream to set
	 */
	public void setPdfCompulsaStream(OutputStream pdfCompulsaStream) {
		this.pdfCompulsaStream = pdfCompulsaStream;
	}
	/**
	 * @return the pdfCompulsaStream
	 */
	public OutputStream getPdfCompulsaStream() {
		return pdfCompulsaStream;
	}
	/**
	 * @param locale the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}


	/**
	 * @param locator the locator to set
	 */
	public void setLocator(String locator) {
		this.locator = locator;
	}
	/**
	 * @return the locator
	 */
	public String getLocator() {
		return locator;
	}
	public BaseRegistroVO getRegistro() {
		return registro;
	}
	public void setRegistro(BaseRegistroVO registro) {
		this.registro = registro;
	}
}
