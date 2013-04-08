package deposito.forms;

import common.forms.CustomForm;

/**
 * Formulario para la recogida de datos en la gestión de los elementos de tipo
 * no asignable que integran el depósito físico gestionado por el sistema
 */
public class ElementoNoAsignableForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String id = null;
	String tipoElemento = null;
	String idPadre = null;
	String idUbicacion = null;
	String numACrear = null;
	String idFormato = null;
	String selHuecos = null;
	String textSignaturas = null;
	String pathPadre = null;
	String nombre = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTipoElemento() {
		return tipoElemento;
	}

	public void setTipoElemento(String tipoElemento) {
		this.tipoElemento = tipoElemento;
	}

	public String getNumACrear() {
		return numACrear;
	}

	public void setNumACrear(String numACrear) {
		this.numACrear = numACrear;
	}

	public String getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}

	public String getIdUbicacion() {
		return idUbicacion;
	}

	public void setIdUbicacion(String idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	public String getIdFormato() {
		return idFormato;
	}

	public void setIdFormato(String idFormato) {
		this.idFormato = idFormato;
	}

	public String getSelHuecos() {
		return selHuecos;
	}

	public void setSelHuecos(String selHuecos) {
		this.selHuecos = selHuecos;
	}

	public String getTextSignaturas() {
		return textSignaturas;
	}

	public void setTextSignaturas(String textSignaturas) {
		this.textSignaturas = textSignaturas;
	}

	public String getPathPadre() {
		return pathPadre;
	}

	public void setPathPadre(String pathPadre) {
		this.pathPadre = pathPadre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}