package ieci.tdw.ispac.ispacpublicador.business.action.mensajesCortos.vo;

public class DestinatarioVO {
	
	
	/**
	 * Móvil del destinatario
	 */
	private String movil;
	
	/**
	 * Mail del destinatarios
	 */
	private String mail;

	/**
	 * Nombre del destinatarios, inicializado por defecto con *
	 */
	private String nombre="****";
	
	/**
	 * Nif del destinatario inicializado por defecto con *
	 */
	private String  nif="*****";
	
	/**
	 * Asunto
	 */
	private String asunto;
	
	/**
	 * Texto a enviar
	 */
	private String texto;
	
	/**
	 * Indica si es un destinatario externo a los destinatarios relacionados con el expediente.
	 */
	boolean destinatarioExterno=false;

	
	/**
	 * Constructor
	 */
	public DestinatarioVO() {
		super();
	}

	/**
	 * Constructor
	 * @param movil
	 */
	public DestinatarioVO(String movil) {
		super();
		this.movil = movil;
		
		
	}
	
	/**
	 * Obtiene el movil
	 * @return
	 */
	public String getMovil() {
		return movil;
	}

	/**
	 * Modifica el movil
	 * @param movil
	 */
	public void setMovil(String movil) {
		this.movil = movil;
	}

	/**
	 * Obtiene el mail
	 * @return
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Modifica el mail
	 * @param mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * Cierto si el destinatario es un destinatario externo
	 * @return
	 */
	public boolean isDestinatarioExterno() {
		return destinatarioExterno;
	}

	/**
	 * Modifica el flag de destinatario externo
	 * @param isDestinatarioExterno
	 */
	public void setDestinatarioExterno(boolean isDestinatarioExterno) {
		this.destinatarioExterno = isDestinatarioExterno;
	}

	
	/**
	 * Devuelve el nombre
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	
	/**
	 * Modifica el nombre
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve el nif
	 * @return
	 */
	public String getNif() {
		return nif;
	}
	
	/**
	 * Modifica el nif
	 * @param nif
	 */

	public void setNif(String nif) {
		this.nif = nif;
	}

	/**
	 * Devuelve el texto
	 * @return
	 */
	public String getTexto() {
		return texto;
	}
	
	/**
	 * Modifica el texto
	 * @param texto
	 */

	public void setTexto(String texto) {
		this.texto = texto;
	}

	/**
	 * Sobrecarga del métdo =
	 * Dos destinatarios se considerarán el mismo si tiene el mismo nif
	 */
	public boolean equals(Object obj) {
		
		DestinatarioVO dest= (DestinatarioVO) obj;
		if(this.nif.equalsIgnoreCase(dest.getNif())){
			return true;
		}

		return false;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}


	
	
	
	
	

}
