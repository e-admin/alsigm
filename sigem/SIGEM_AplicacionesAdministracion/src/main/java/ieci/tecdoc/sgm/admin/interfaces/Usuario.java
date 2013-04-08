package ieci.tecdoc.sgm.admin.interfaces;

import java.util.Date;

public interface Usuario {

	public abstract String getUsuario();

	public abstract void setUsuario(String usuario);

	public abstract String getPassword();

	public abstract void setPassword(String password);

	public abstract String getNombre();

	public abstract void setNombre(String nombre);
	
	public abstract String getApellidos();

	public abstract void setApellidos(String apellidos);
	
	public abstract Date getFechaAlta();

	public abstract void setFechaAlta(Date fechaAlta);


	/**
	 * Recoge los valores de la instancia en una cadena xml
	 * @param header Si se incluye la cabecera
	 * @return los datos en formato xml
	 */
	public abstract String toXML(boolean header);

	/**
	 * Devuelve los valores de la instancia en una cadena de caracteres.
	 */
	public abstract String toString();

}