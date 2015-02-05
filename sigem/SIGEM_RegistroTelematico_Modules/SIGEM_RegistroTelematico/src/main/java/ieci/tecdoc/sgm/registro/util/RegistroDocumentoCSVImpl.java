/**
 * 
 */
package ieci.tecdoc.sgm.registro.util;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

/**
 * @author IECISA
 * 
 *         Implementación de una asociación entre el código GUID de un documento
 *         y el código CSV
 * 
 */
public class RegistroDocumentoCSVImpl implements RegistroDocumentoCSV {

	protected String guid;

	protected String csv;

	/**
	 * @return el guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * @param guid
	 *            el guid a fijar
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * @return el csv
	 */
	public String getCsv() {
		return csv;
	}

	/**
	 * @param csv
	 *            el csv a fijar
	 */
	public void setCsv(String csv) {
		this.csv = csv;
	}

	/**
	 * Recoge los valores de la instancia en una cadena xml
	 * 
	 * @param header
	 *            Si se incluye la cabecera
	 * @return los datos en formato xml
	 */
	public String toXML(boolean header) {
		XmlTextBuilder bdr;
		String tagName = "RegistroDocumentoCSV";

		bdr = new XmlTextBuilder();
		if (header)
			bdr.setStandardHeader();

		bdr.addOpeningTag(tagName);

		bdr.addSimpleElement("CSV", csv);
		bdr.addSimpleElement("GUID", guid);

		bdr.addClosingTag(tagName);

		return bdr.getText();
	}

	/**
	 * Devuelve los valores de la instancia en una cadena de caracteres.
	 */
	public String toString() {
		return toXML(false);
	}

}
