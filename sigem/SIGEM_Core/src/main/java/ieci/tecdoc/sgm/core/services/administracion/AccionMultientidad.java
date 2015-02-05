package ieci.tecdoc.sgm.core.services.administracion;


public class AccionMultientidad {

	private String identificador;// PK
	private String nombre;
	private String claseConfiguradora; 
	private String claseEjecutora;
	private String infoAdicional;
	
	/**
	 * @return el identificador
	 */
	public String getIdentificador() {
		return identificador;
	}
	/**
	 * @param identificador el identificador a fijar
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	
	/**
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre el nombre a fijar
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * @return el claseConfiguradora
	 */
	public String getClaseConfiguradora() {
		return claseConfiguradora;
	}
	/**
	 * @param claseConfiguradora el claseConfiguradora a fijar
	 */
	public void setClaseConfiguradora(String claseConfiguradora) {
		this.claseConfiguradora = claseConfiguradora;
	}
	
	/**
	 * @return el claseEjecutora
	 */
	public String getClaseEjecutora() {
		return claseEjecutora;
	}
	/**
	 * @param claseEjecutora el claseEjecutora a fijar
	 */
	public void setClaseEjecutora(String claseEjecutora) {
		this.claseEjecutora = claseEjecutora;
	}
	
	/**
	 * @return el infoAdicional
	 */
	public String getInfoAdicional() {
		return infoAdicional;
	}
	/**
	 * @param infoAdicional el infoAdicional a fijar
	 */
	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
	}
	
}
