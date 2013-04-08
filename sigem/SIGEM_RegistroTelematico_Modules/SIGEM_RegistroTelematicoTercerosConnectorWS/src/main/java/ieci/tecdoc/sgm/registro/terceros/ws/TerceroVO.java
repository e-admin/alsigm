package ieci.tecdoc.sgm.registro.terceros.ws;

public class TerceroVO {

	protected String identificador;
	protected String nombre;
	protected String primerApellido;
	protected String segundoApellido;
	protected String terceroId;

	protected DireccionTerceroVO[] direcciones;
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public DireccionTerceroVO[] getDirecciones() {
		return direcciones;
	}
	public void setDirecciones(DireccionTerceroVO[] direcciones) {
		this.direcciones = direcciones;
	}
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	public String getTerceroId() {
		return terceroId;
	}
	public void setTerceroId(String terceroId) {
		this.terceroId = terceroId;
	}

}
