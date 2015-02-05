package ieci.tecdoc.sgm.ct.database.datatypes;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;


/**
 * Bean con las propiedades del Pago.
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 17-may-2007
 */
public class PagoImpl extends SolicitudAportacionDocumentacion implements Pago  {
	
	protected String entidadEmisoraId;

	protected String autoliquidacionId;
	
	protected String importe;

	/**
	 * Constructor de clase
	 */
	public PagoImpl(){
		entidadEmisoraId = null;
		autoliquidacionId = null;
		importe = null;
	}

	public String getAutoliquidacionId() {
		return autoliquidacionId;
	}

	public void setAutoliquidacionId(String autoliquidacionId) {
		this.autoliquidacionId = autoliquidacionId;
	}

	public String getEntidadEmisoraId() {
		return entidadEmisoraId;
	}

	public void setEntidadEmisoraId(String entidadEmisoraId) {
		this.entidadEmisoraId = entidadEmisoraId;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	
	/**
	 * Recoge los valores de la instancia en una cadena xml
	 * @param header Si se incluye la cabecera
	 * @return los datos en formato xml
	 */
	public String toXML(boolean header) {
		XmlTextBuilder bdr;
		String tagName = "Pago";

		bdr = new XmlTextBuilder();
		if (header)
			bdr.setStandardHeader();

		bdr.addOpeningTag(tagName);

		bdr.addFragment(super.toString());
		bdr.addSimpleElement("Entidad_Emisora_ID", entidadEmisoraId);
		bdr.addSimpleElement("Autoliquidacion_ID", autoliquidacionId);
		bdr.addSimpleElement("Importe", importe);
		
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