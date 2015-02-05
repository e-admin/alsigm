package xml.config;

import org.apache.commons.lang.StringUtils;

import xml.XMLObject;

/**
 * Clase que almacena la información de volumen de formato
 */
public class InfoVolumenFormato extends XMLObject
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Id de formato. */
	private String idFmt = null;

    /** Nombre del formato. */
	private String nombreFmt = null;

    /** numUI. */
	private String numUI = null;

	/**
	 * Constructor.
	 */
	public InfoVolumenFormato()
	{
		super();
	}

	/**
	 * @return el idFmt
	 */
	public String getIdFmt() {
		return idFmt;
	}

	/**
	 * @param idFmt el idFmt a establecer
	 */
	public void setIdFmt(String idFmt) {
		this.idFmt = idFmt;
	}

	/**
	 * @return el numUI
	 */
	public String getNumUI() {
		return numUI;
	}

	/**
	 * @param numUI el numUI a establecer
	 */
	public void setNumUI(String numUI) {
		this.numUI = numUI;
	}

	/**
	 * @return el nombreFmt
	 */
	public String getNombreFmt() {
		return nombreFmt;
	}

	/**
	 * @param nombreFmt el nombreFmt a establecer
	 */
	public void setNombreFmt(String nombreFmt) {
		this.nombreFmt = nombreFmt;
	}

	/**
	 * Obtiene una representación XML del objeto.
	 * @param indent Número de espacios de tabulación.
	 * @return Representación del objeto.
	 */
	public String toXML(int indent)
	{
		final StringBuffer xml = new StringBuffer();
		final String tabs = StringUtils.repeat(" ", indent);

		// Formato
		xml.append(tabs + "<formato");
		xml.append(idFmt != null ? " idFmt=\"" + idFmt + "\"": "");
		xml.append(nombreFmt != null ? " nombreFmt=\"" + nombreFmt + "\"": "");
		xml.append(numUI != null ? " numUI=\"" + numUI + "\"": "");
		xml.append("/>");

		return xml.toString();
	}

}
