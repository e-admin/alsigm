package ieci.tecdoc.sgm.admsistema.temporal;

public class Provincia {
	private String idProvincia;
	private String descripcion;
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public Provincia(String idProvincia, String descripcion) {
		this.idProvincia = idProvincia;
		this.descripcion = descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getIdProvincia() {
		return idProvincia;
	}
	
	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}
}
