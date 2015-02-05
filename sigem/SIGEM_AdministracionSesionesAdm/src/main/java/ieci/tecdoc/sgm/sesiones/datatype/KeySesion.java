package ieci.tecdoc.sgm.sesiones.datatype;

public class KeySesion {
	private String key;
	private String entidad;
	
	public KeySesion(String key, String entidad) {
		super();
		this.key = key;
		this.entidad = entidad;
	}
	
	public String getEntidad() {
		return entidad;
	}
	
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
}
