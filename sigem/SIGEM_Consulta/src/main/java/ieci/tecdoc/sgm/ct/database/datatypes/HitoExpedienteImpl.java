package ieci.tecdoc.sgm.ct.database.datatypes;

import java.util.Date;

/**
 * Bean con las propiedades del Hito de un Expediente.
 * 
 * @author Javier Septién Arceredillo
 *
 * Fecha de Creación: 14-may-2007
 */

public class HitoExpedienteImpl implements HitoExpediente{
	
	protected String numeroExpediente;

	protected String guid;

	protected String codigo;

	protected Date fecha;

	protected String descripcion;
	
	protected String informacionAuxiliar;

	/**
	 * Constructor de clase
	 */
	public HitoExpedienteImpl(){
		
		numeroExpediente = null;
		guid = null;
		codigo = null;
		fecha = null;
		descripcion = null;
		informacionAuxiliar = null;
	}
	
	/**
	 * Devuelve el numero del expediente.
	 * @return String Numero del expediente.
	 */
	public String getNumeroExpediente(){
		return numeroExpediente;
	}


	/**
	 * Devuelve el Guid del hito.
	 * @return String Guid del hito.
	 */   
	public String getGuid(){
		return guid;
	}


	/**
	 * Devuelve el codigo del hito.
	 * @return String Codigo del hito.
	 */   
	public String getCodigo(){
		return codigo;
	}
	
	/**
	 * Devuelve la fecha del hito.
	 * @return Date Fecha hito.
	 */   
	public Date getFecha(){
		return fecha;
	}

	
	/**
	 * Devuelve la descripcion del hito
	 * @return String Descripcion del hito.
	 */   
	public String getDescripcion(){
		return descripcion;
	}


	/**
	 * Devuelve informacion auxiliar del hito
	 * como documento XML
	 * @return String Informacion auxiliar del hito.
	 */   
	public String getInformacionAuxiliar(){
		return informacionAuxiliar;
	}



	/**
	 * Establece un numero de expediente.
	 * @param numeroExpediente Numero de expediente. 
	 */	
	public void setNumeroExpediente(String numeroExpediente){
		this.numeroExpediente = numeroExpediente;
	}

	
	/**
	 * Establece el Guid del hito.
	 * @param guid Guid del hito.
	 */   
	public void setGuid(String guid){
		this.guid = guid;
	}

	/**
	 * Establece el codigo del hito.
	 * @return codigo Codigo del hito.
	 */   
	public void setCodigo(String codigo){
		this.codigo = codigo;
	}
	
	/**
	 * Establece la fecha y hora del hito.
	 * @param fecha Fecha y hora del hito.
	 */   
	public void setFecha(Date fecha){
		this.fecha = fecha;
	}

	
	/**
	 * Establece la descripcion del hito.
	 * @param descripcion descripcion del hito.
	 */   
	public void setDescripcion(String descripcion){
		this.descripcion = descripcion;
	}

	
	/**
	 * Establece le informacion auxiliar del expediente
	 * que vendra en formato XML
	 * @param informacionAuxiliar Informacion auxiliar del expediente. 
	 */   
	public void setInformacionAuxiliar(String informacionAuxiliar){
		this.informacionAuxiliar = informacionAuxiliar;
	}
	
	
	/**
	 * Recoge los valores de la instancia en una cadena xml
	 * @param header Si se incluye la cabecera
	 * @return los datos en formato xml
	 */
	public String toXML(boolean header) {
		/*XmlTextBuilder bdr;
		String tagName = "Document";

		bdr = new XmlTextBuilder();
		if (header)
			bdr.setStandardHeader();

		bdr.addOpeningTag(tagName);

		bdr.addSimpleElement("Guid", guid);
		bdr.addSimpleElement("Content", content.toString());
		bdr.addSimpleElement("Hash", hash);
		bdr.addSimpleElement("Extension", extension);
		bdr.addSimpleElement("Timestamp", timestamp.toString());

		bdr.addClosingTag(tagName);

		return bdr.getText();*/
		return null;
	}

	/**
	 * Devuelve los valores de la instancia en una cadena de caracteres.
	 */
	public String toString() {
		return toXML(false);
	}


}