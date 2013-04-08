package ieci.tecdoc.sgm.ct.database.datatypes;


/**
 * Bean con las propiedades de un Fichero de un Hito.
 * 
 * @author Javier Septién Arceredillo
 *
 * Fecha de Creación: 17-may-2007
 */
public class FicheroHitoImpl implements FicheroHito {

	protected String guidHito;

	protected String guid;

	protected String titulo;

	/**
	 * Constructor de clase
	 */
	public FicheroHitoImpl(){
		
		guidHito = null;
		guid = null;
		titulo = null;
	}
	
	/**
	 * Devuelve el Guid del Hito.
	 * @return String Guid del Hito.
	 */
	public String getGuidHito(){
		
		return guidHito;
	}


	/**
	 * Devuelve el Guid del Fichero.
	 * @return String Guid del Fichero.
	 */   
	public String getGuid(){
		return guid;
	}

	/**
	 * Devuelve el titulo. Para presentacion.
	 * @return String Presentacion.
	 */   
	public String getTitulo(){
		return titulo;
	}
	

	/**
	 * Establece un Guid de un Hito para el Fichero.
	 * @param guidHito Guid de un Hito. 
	 */	
	public void setGuidHito(String guidHito){
		this.guidHito = guidHito;
	}

	
	/**
	 * Establece el guid de un Fichero.
	 * @param guid Guid de un Fichero.
	 */   
	public void setGuid(String guid){
		this.guid = guid;
	}

		
	/**
	 * Establece el titulo de un fichero para presentacion.
	 * @param titulo Titulo de un fichero.
	 */   
	public void setTitulo(String titulo){
		this.titulo = titulo;
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