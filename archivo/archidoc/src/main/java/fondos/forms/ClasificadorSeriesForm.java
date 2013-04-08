package fondos.forms;

import es.archigest.framework.web.action.ArchigestActionForm;

/**
 * Formulario para recogida de datos en la gestion dentro del cuadro de
 * clasificacion de elementos del tipo 'Clasificador de Series'
 */
public class ClasificadorSeriesForm extends ArchigestActionForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String id = null;
	String tipoClasificador = null;
	String codigo = null;
	String denominacion = null;
	String idPadre = null;
	String nivelPadre = null;
	String fondoSerie = null;
	String tituloSerie = null;
	String codOrdenacion = null;
	String codigoOld = null;
	String idLCA;
	int nivelAcceso;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getTipoClasificador() {
		return tipoClasificador;
	}

	public void setTipoClasificador(String tipoClasificador) {
		this.tipoClasificador = tipoClasificador;
	}

	public String getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}

	public String getNivelPadre() {
		return nivelPadre;
	}

	public void setNivelPadre(String nivelPadre) {
		this.nivelPadre = nivelPadre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFondoSerie() {
		return fondoSerie;
	}

	public void setFondoSerie(String fondoSerie) {
		this.fondoSerie = fondoSerie;
	}

	public String getTituloSerie() {
		return tituloSerie;
	}

	public void setTituloSerie(String tituloSerie) {
		this.tituloSerie = tituloSerie;
	}

	public String getCodOrdenacion() {
		return codOrdenacion;
	}

	/**
	 * @param tienePadreFijo
	 *            The tienePadreFijo to set.
	 */
	public void setCodOrdenacion(String posOrdenacion) {
		this.codOrdenacion = posOrdenacion;
	}

	public String getCodigoOld() {
		return codigoOld;
	}

	public void setCodigoOld(String codigoOld) {
		this.codigoOld = codigoOld;
	}

	public String getIdLCA() {
		return idLCA;
	}

	public void setIdLCA(String idLCA) {
		this.idLCA = idLCA;
	}

	public int getNivelAcceso() {
		return nivelAcceso;
	}

	public void setNivelAcceso(int nivelAcceso) {
		this.nivelAcceso = nivelAcceso;
	}
}
