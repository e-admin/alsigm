package ieci.tecdoc.sgm.admin.interfaces;

/**
 * Interfaz de perfiles
 * @author Ana
 */
public interface Perfil {

	public abstract String getIdAplicacion();
	public abstract void setIdAplicacion(String idAplicacion);
	
	public abstract String getIdEntidad();	
	public abstract void setIdEntidad(String idEntidad);
	
	public abstract String getIdUsuario();
	public abstract void setIdUsuario(String idUsuario);
	
	public abstract String toXML(boolean header);
	
	public abstract String toString();
	
}
