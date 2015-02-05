package ieci.tecdoc.sgm.core.services.permisos_backoffice;

public class CriterioBusqueda {
	
	private String nombreUsuario;
	private String apellidos;
	private String nombreReal;
	
	public CriterioBusqueda(){
		this.nombreUsuario=null;
		this.apellidos=null;
		this.nombreReal=null;
	}	
	
	public CriterioBusqueda(String nombreUsuario,String nombreReal, String apellidos){
		this.nombreUsuario=nombreUsuario;
		this.apellidos=apellidos;
		this.nombreReal=nombreReal;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombreReal() {
		return nombreReal;
	}

	public void setNombreReal(String nombreReal) {
		this.nombreReal = nombreReal;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	
}
