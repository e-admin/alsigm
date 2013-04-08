package ieci.tecdoc.sgm.usuario.datatypes;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

/**
 * Bean con las propiedades de un elemento mime.
 * 
 * @author IECISA
 *
 */
public class UsuarioImpl implements Usuario 
{
	public UsuarioImpl() { }
   
	/**
	 * Devuelve el usuario de acceso.
	 * @return String Usuario.
	 */
	public String getUsuario(){
		return usuario;
	}
	
	/**
	 * Devuelve la contraseña de acceso.
	 * @return String Contraseña.
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * Devuelve el correo electrónicodel usuario.
	 * @return String Correo electrónico.
	 */
	public String getEmail(){
		return email;
	}
	
	/**
	 * Devuelve el documento de identidad del usuario.
	 * @return String Documento de identidad.
	 */
	public String getId(){
		return id;
	}
	
	/**
	 * Devuelve el nombre del usuario.
	 * @return String Nombre del usuario.
	 */
	public String getNombre(){
		return nombre;
	}
	
	/**
	 * Devuelve los apellidos del usuario.
	 * @return String Apellidos del usuario.
	 */
	public String getApellidos(){
		return apellidos;
	}
	
	/**
	 * Estable el usuario de acceso.
	 * @param usuario Usuario.
	 */
	public void setUsuario(String usuario){
		this.usuario = usuario;
	}
	
	/**
	 * Estable la contraseña de acceso.
	 * @param password Contraseña.
	 */
	public void setPassword(String password){
		this.password = password;
	}
	
	/**
	 * Estable el correo electrónico del usuario.
	 * @param email Correo electrónico.
	 */
	public void setEmail(String email){
		this.email = email;
	}
	
	/**
	 * Estable el documento de identidad.
	 * @param id Documento de identidad.
	 */
	public void setId(String id){
		this.id = id;
	}
	
	/**
	 * Estable el nombre del usuario.
	 * @param nombre Nombre del usuario.
	 */
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	/**
	 * Estable los apellidos del usuario.
	 * @param apellidos Apellidos del usuario.
	 */
	public void setApellidos(String apellidos){
		this.apellidos = apellidos;
	}

   /**
    * Recoge los valores de la instancia en una cadena xml
    * @param header Si se incluye la cabecera
    * @return los datos en formato xml
    */   
   public String toXML(boolean header) {
      XmlTextBuilder bdr;
      String tagName = "Usuario";

      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      bdr.addSimpleElement("Nombre", nombre);
      bdr.addSimpleElement("Apellidos", apellidos);
      bdr.addSimpleElement("NIF", id);
      bdr.addSimpleElement("Email", email);
      bdr.addSimpleElement("Usuario", usuario);
      bdr.addSimpleElement("Contraseña", password);

      bdr.addClosingTag(tagName);

      return bdr.getText();
   }

   /**
    * Devuelve los valores de la instancia en una cadena de caracteres.
    */
   public String toString() {
      return toXML(false);
   }

   protected String usuario;
   protected String password;
   protected String email;
   protected String id;
   protected String nombre;
   protected String apellidos;
   
}