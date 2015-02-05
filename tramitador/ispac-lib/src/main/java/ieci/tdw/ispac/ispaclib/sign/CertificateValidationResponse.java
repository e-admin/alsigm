package ieci.tdw.ispac.ispaclib.sign;

import org.apache.log4j.Logger;

 /**
 * Clase que encapsula los datos del certificado digital.
 */
public class CertificateValidationResponse {
	private static final long serialVersionUID = 1L;

	public static final Logger logger = Logger
			.getLogger(CertificateValidationResponse.class);

	private String nombre;

	private String apellido1;

	private String apellido2;

	private String apellidos;

	/**
	 * NIF/NIE/Pasaporte
	 */
	private String nif;

	private String email;

	public String getNombre() {
		return nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public String getApellidos() {
		return apellidos;
	}

	public String getNif() {
		return nif;
	}

	public String getEmail() {
		return email;
	}

	/**
	 * Cualquier otro campo no estandar recuperado del certificado.
	 * 
	 * @param field
	 * @return
	 */
	public String getField(String field) {
		return null;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
