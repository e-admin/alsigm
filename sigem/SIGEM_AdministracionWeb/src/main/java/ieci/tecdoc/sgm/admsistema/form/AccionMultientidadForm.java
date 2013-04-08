package ieci.tecdoc.sgm.admsistema.form;

import ieci.tecdoc.sgm.admsistema.vo.OpcionConfiguracionVO;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 *
 * Formulario para las acciones de multientidad
 * @author IECISA
 *
 */
public class AccionMultientidadForm extends ActionForm {

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
	 * @return el fichero
	 */
	public FormFile getFichero() {
		return fichero;
	}
	/**
	 * @param fichero el fichero a fijar
	 */
	public void setFichero(FormFile fichero) {
		this.fichero = fichero;
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
	 * @return el paso
	 */
	public String getPaso() {
		return paso;
	}
	/**
	 * @param paso el paso a fijar
	 */
	public void setPaso(String paso) {
		this.paso = paso;
	}
	
	/**
	 * @return el resumenConfiguracion
	 */
	public String getResumenConfiguracion() {
		return resumenConfiguracion;
	}
	/**
	 * @param resumenConfiguracion el resumenConfiguracion a fijar
	 */
	public void setResumenConfiguracion(String resumenConfiguracion) {
		this.resumenConfiguracion = resumenConfiguracion;
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
	 * @return el opcionesConfiguracion
	 */
	public OpcionConfiguracionVO[] getOpcionesConfiguracion() {
		return opcionesConfiguracion;
	}
	/**
	 * @param opcionesConfiguracion el opcionesConfiguracion a fijar
	 */
	public void setOpcionesConfiguracion(OpcionConfiguracionVO[] opcionesConfiguracion) {
		this.opcionesConfiguracion = opcionesConfiguracion;
	}

	private String idAccion;
	private String nombreAccion;
	private String [] entidades;
	private OpcionConfiguracionVO [] opcionesConfiguracion;
	private String claseConfiguradora;
	private String claseEjecutora;
	private String opcion;
	private FormFile fichero;
	private String ficheroTemporal;
	private String paso;
	private String resumenConfiguracion;
		
	private final static long serialVersionUID = 0;

}