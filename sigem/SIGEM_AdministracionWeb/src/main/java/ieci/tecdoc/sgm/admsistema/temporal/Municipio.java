package ieci.tecdoc.sgm.admsistema.temporal;

public class Municipio {
	private String idProvincia;
	private String idMunicipio;
	private String descripcion;
	public String getDescripcion() {
		return descripcion;
	}
	
	public Municipio(String idProvincia, String idMunicipio, String descripcion) {
		this.idProvincia = idProvincia;
		this.idMunicipio = idMunicipio;
		this.descripcion = descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getIdMunicipio() {
		return idMunicipio;
	}
	
	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}
	
	public String getIdProvincia() {
		return idProvincia;
	}
	
	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}
}
