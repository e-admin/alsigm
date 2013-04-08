package ieci.tecdoc.isicres.rpadmin.struts.beans;

public class Distribucion {
	
	private String id;
	private String idOrganizacion;
	private String tipoDestino;
	private String idDestino;
	private String nombre;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdDestino() {
		return idDestino;
	}
	public void setIdDestino(String idDestino) {
		this.idDestino = idDestino;
	}
	public String getIdOrganizacion() {
		return idOrganizacion;
	}
	public void setIdOrganizacion(String idOrganizacion) {
		this.idOrganizacion = idOrganizacion;
	}
	public String getTipoDestino() {
		return tipoDestino;
	}
	public void setTipoDestino(String tipoDestino) {
		this.tipoDestino = tipoDestino;
	}
}
