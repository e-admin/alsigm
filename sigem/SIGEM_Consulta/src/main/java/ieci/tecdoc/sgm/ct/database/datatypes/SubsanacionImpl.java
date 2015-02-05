package ieci.tecdoc.sgm.ct.database.datatypes;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;


/**
 * Bean con las propiedades de la Subsanación.
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 17-may-2007
 */
public class SubsanacionImpl extends SolicitudAportacionDocumentacion implements Subsanacion {

	/**
	 * Constructor de clase
	 */
	public SubsanacionImpl(){

	}

	/**
	 * Recoge los valores de la instancia en una cadena xml
	 * @param header Si se incluye la cabecera
	 * @return los datos en formato xml
	 */
	public String toXML(boolean header) {
		XmlTextBuilder bdr;
		String tagName = "Subsanacion";

		bdr = new XmlTextBuilder();
		if (header)
			bdr.setStandardHeader();

		bdr.addOpeningTag(tagName);

		bdr.addFragment(super.toString());
		
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