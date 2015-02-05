package ieci.tecdoc.sgm.ct.database.datatypes;


/**
 * Bean con las propiedades de un Interesado.
 * 
 * @author Javier Septién Arceredillo
 *
 * Fecha de Creación: 14-may-2007
 */
public class InteresadoImpl implements Interesado {

	protected String numeroExpediente;

	protected String NIF;

	protected String nombre;

	protected String principal;

	protected String informacionAuxiliar;
	
	protected Expedientes expedientes;

	/**
	 * Constructor de clase
	 */
	public InteresadoImpl(){
		
		numeroExpediente = null;
		NIF = null;
		nombre = null;
		principal = null;
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
	 * Devuelve el NIF del interesado.
	 * @return String NIF del interesado.
	 */   
	public String getNIF(){
		return NIF;
	}


	/**
	 * Devuelve el nombre del interesado.
	 * @return String Nombre del interesado.
	 */   
	public String getNombre(){
		return nombre;
	}


	/**
	 * Devuelve si el interesado es el Interesado Principal
	 * del expediente. Puede tomar dos valores: S/N.
	 * @return String Si el interesado es el Interesado Principal o no (S/N).
	 */   
	public String getPrincipal(){
		return principal;
	}


	/**
	 * Devuelve informacion auxiliar del interesado
	 * como documento XML
	 * @return String Informacion auxiliar del interesado.
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
	 * Devuelve todos los expedientes del interesado
	 * @return Expedientes Coleccion de expedientes de un interesado.
	 */   
	public Expedientes getExpedientes(){
		return expedientes;
	}

	
	/**
	 * Establece el NIF del interesado.
	 * @param NIF NIF del interesado.
	 */   
	public void setNIF(String NIF){
		this.NIF = NIF;
	}

	
	/**
	 * Establece el nombre del interesado.
	 * @param nombre Nombre del interesado.
	 */   
	public void setNombre(String nombre){
		this.nombre = nombre;
	}

	/**
	 * Establece si el interesado es el Interesado Principal
	 * del expediente. Puede tomar dos valores: S/N.
	 * @param principal Si el interesado es el Interesado Principal o no (S/N).
	 */   
	public void setPrincipal(String principal){
		this.principal = principal;
	}



	/**
	 * Establece le informacion auxiliar del interesado
	 * que vendra en formato XML
	 * @param informacionAuxiliar Informacion auxiliar del interesado. 
	 */   
	public void setInformacionAuxiliar(String informacionAuxiliar){
		this.informacionAuxiliar = informacionAuxiliar;
	}
	
	/**
	 * Establece los expedientes de un interesado
	 * @param expedientes Coleccion de expedientes de un interesado. 
	 */   
	public void setExpedientes(Expedientes expedientes){
		this.expedientes = expedientes;
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