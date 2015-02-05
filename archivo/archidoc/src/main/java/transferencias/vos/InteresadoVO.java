/*
 * Created on 22-feb-2005
 *
 */
package transferencias.vos;

/**
 * Value Object con la informacion referente a cada uno de los interesados
 * asociados a una unidad documental
 * 
 */
public class InteresadoVO {
	String nombre = null;
	String numeroIdentificacion = null;
	boolean validado = false;
	String rol = null;
	boolean principal;
	String idEnTerceros = null;

	public InteresadoVO() {
	}

	public InteresadoVO(String nombre, String numeroIdentificacion,
			boolean validado) {
		this.nombre = nombre;
		this.numeroIdentificacion = numeroIdentificacion;
		this.validado = validado;
	}

	public InteresadoVO(String nombre, String numeroIdentificacion,
			String idEnTerceros, String rol) {
		this.nombre = nombre;
		this.numeroIdentificacion = numeroIdentificacion;
		this.validado = idEnTerceros != null;
		this.idEnTerceros = idEnTerceros;
		this.rol = rol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public boolean isValidado() {
		return validado;
	}

	public void setValidado(boolean validado) {
		this.validado = validado;
	}

	// TODO tratar de recoger directamente el booleano en la lectura del xml
	public void setValidado(String validado) {
		this.validado = "S".equals(validado);
	}

	public void setPrincipal(String principal) {
		this.principal = "S".equals(principal);
	}

	public String getIdEnTerceros() {
		return idEnTerceros;
	}

	public void setIdEnTerceros(String idEnTerceros) {
		this.idEnTerceros = idEnTerceros;
	}

	public boolean isPrincipal() {
		return principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

}
