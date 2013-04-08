package ieci.tecdoc.sgm.registro.terceros.connector.vo;

import java.util.List;


public class TerceroVO {

	protected String identificador;
	protected String nombre;
	protected String primerApellido;
	protected String segundoApellido;
	protected List<DireccionTerceroVO> direcciones;


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

	public List<DireccionTerceroVO> getDirecciones() {
		return direcciones;
	}
	public void setDirecciones(List<DireccionTerceroVO> direcciones) {
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


}
