package ieci.tecdoc.sgm.admin.interfaces;

/**
 * Interfaz de aplicaciones
 * @author Ana
 *
 */
public interface Aplicacion {
	
	public abstract String getContextoApp();
	public abstract void setContextoApp(String contextoApp);
	
	public abstract String getDefinicion();
	public abstract void setDefinicion(String definicion);
	
	public abstract String getIdentificador();
	public abstract void setIdentificador(String identificador);
	
	public abstract String getProtocolo();
	public abstract void setProtocolo(String protocolo);
	
	public abstract String getServidor();
	public abstract void setServidor(String servidor);
	
	public abstract String getPuertoApp();
	public abstract void setPuertoApp(String puertoApp);
	
	public abstract String toXML(boolean header);
	
	public abstract String toString();


}
