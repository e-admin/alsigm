package ieci.tecdoc.sgm.admsistema.vo;


/**
 * Clase Value Object para almacenar la informacion de la accion multientidad
 * @author IECISA
 *
 */
public class AccionMultientidadVO {


	/**
	 * @return el idAccion
	 */
	public String getIdAccion() {
		return idAccion;
	}
	/**
	 * @param idAccion el idAccion a fijar
	 */
	public void setIdAccion(String idAccion) {
		this.idAccion = idAccion;
	}
	
	/**
	 * @return el nombreAccion
	 */
	public String getNombreAccion() {
		return nombreAccion;
	}
	/**
	 * @param nombreAccion el nombreAccion a fijar
	 */
	public void setNombreAccion(String nombreAccion) {
		this.nombreAccion = nombreAccion;
	}
	
	/**
	 * @return el entidades
	 */
	public String[] getEntidades() {
		return entidades;
	}
	/**
	 * @param entidades el entidades a fijar
	 */
	public void setEntidades(String[] entidades) {
		this.entidades = entidades;
	}
	
	/**
	 * @return el entidadesOrigen
	 */
	public String[] getEntidadesOrigen() {
		return entidadesOrigen;
	}
	/**
	 * @param entidadesOrigen el entidadesOrigen a fijar
	 */
	public void setEntidadesOrigen(String[] entidadesOrigen) {
		this.entidadesOrigen = entidadesOrigen;
	}
	
	/**
	 * @return el entidadesDestino
	 */
	public String[] getEntidadesDestino() {
		return entidadesDestino;
	}
	/**
	 * @param entidadesDestino el entidadesDestino a fijar
	 */
	public void setEntidadesDestino(String[] entidadesDestino) {
		this.entidadesDestino = entidadesDestino;
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
	 * @return el opcion
	 */
	public String getOpcion() {
		return opcion;
	}
	/**
	 * @param opcion el opcion a fijar
	 */
	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}
	
	/**
	 * @return el ficheroTemporal
	 */
	public String getFicheroTemporal() {
		return ficheroTemporal;
	}
	/**
	 * @param ficheroTemporal el ficheroTemporal a fijar
	 */
	public void setFicheroTemporal(String ficheroTemporal) {
		this.ficheroTemporal = ficheroTemporal;
	}
	
	/**
	 * @return el nombreFicheroTemporal
	 */
	public String getNombreFicheroTemporal() {
		return nombreFicheroTemporal;
	}
	/**
	 * @param nombreFicheroTemporal el nombreFicheroTemporal a fijar
	 */
	public void setNombreFicheroTemporal(String nombreFicheroTemporal) {
		this.nombreFicheroTemporal = nombreFicheroTemporal;
	}

	/**
	 * @return el opcionesConfiguracion
	 */
	public OpcionConfiguracionVO[] getOpcionesConfiguracion() {
		return opcionesConfiguracion;
	}
	/**
	 * @param opcionesConfiguracion el opcionesConfiguracion a fijar
	 */
	public void setOpcionesConfiguracion(
			OpcionConfiguracionVO[] opcionesConfiguracion) {
		this.opcionesConfiguracion = opcionesConfiguracion;
	}

	private String idAccion;
	private String nombreAccion;
	private String [] entidades;
	private String [] entidadesOrigen;
	private String [] entidadesDestino;
	private String claseConfiguradora;
	private String claseEjecutora;
	private OpcionConfiguracionVO [] opcionesConfiguracion;
	private String opcion;
	private String ficheroTemporal;
	private String nombreFicheroTemporal;
	
}
