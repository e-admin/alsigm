package fondos.forms;

import java.util.HashMap;
import java.util.Map;

import es.archigest.framework.web.action.ArchigestActionForm;
import fondos.model.IdentificacionSerie;

/**
 * Formulario para recogida de datos durante la edición de la identificación de
 * una serie
 */
public class IdentificacionSerieForm extends ArchigestActionForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	String nombreProcedimientoABuscar;

	String codigoProcedimientoABuscar;

	String idserie;

	String procedimiento;

	String denominacion;

	String definicion;

	String tramites;

	String normativa;

	String documentosbasicos;

	String fechainicial;

	String fechafinal;

	// int apartirde;

	String[] guidsProductor;

	private String guidProductor;
	private String guidProductorHistorico;

	HashMap archivoXProductor = new HashMap();

	String[] idsSoportesSeleccionado;

	String[] idsSoportesSeleccionadoAEliminar;

	boolean changed = false;// usado para saber sihan cambiado los datos de la
							// identificacion

	boolean warningSinProductor = false; // usado como flag para mostrar un
											// mensaje de advertencia de que el
											// procedimiento no tiene
											// productores

	String procedimientoSeleccionado;
	String idProcedimientoSeleccionado;

	boolean guardado = false; // usado como flag para indicar cuando se ha
								// pulsado el boton de guardar

	String[] productoresAntiguosPrimerNodo;
	String productoresAntiguos;
	String productoresSeleccionados;

	String idSerie;

	String archivoReceptor;

	String[] nombreProductor;

	String[] descripcionProductor;

	String[] archivoPorProductor;

	// Información Extendida
	String tipoDocumentacion = "";

	String volumenPrevisionAnual = "";

	String soportePrevisionAnual = "";

	String fechaFinVigenciaProductor = "";

	String fechaInicioVigenciaProductor = "";

	private final Map values = new HashMap();

	public void setValue(String key, Object value) {
		values.put(key, value);
	}

	public Object getValue(String key) {
		return values.get(key);
	}

	private Map skills = new HashMap();

	public Object getSkill(String key) {
		return skills.get(key);
	}

	public void setSkill(String key, Object value) {
		skills.put(key, value);
	}

	public Map getSkills() {
		return skills;
	}

	public String getNombreProcedimientoABuscar() {
		return nombreProcedimientoABuscar;
	}

	public void setNombreProcedimientoABuscar(String nombreProcedimientoABuscar) {
		this.nombreProcedimientoABuscar = nombreProcedimientoABuscar;
	}

	/**
	 * @return Returns the idsSoportesSeleccionadoAEliminar.
	 */
	public String[] getIdsSoportesSeleccionadoAEliminar() {
		return idsSoportesSeleccionadoAEliminar;
	}

	/**
	 * @param idsSoportesSeleccionadoAEliminar
	 *            The idsSoportesSeleccionadoAEliminar to set.
	 */
	public void setIdsSoportesSeleccionadoAEliminar(
			String[] idsSoportesSeleccionadoAEliminar) {
		this.idsSoportesSeleccionadoAEliminar = idsSoportesSeleccionadoAEliminar;
	}

	/**
	 * @return Returns the idsSoportesSeleccionado.
	 */
	public String[] getIdsSoportesSeleccionado() {
		return idsSoportesSeleccionado;
	}

	/**
	 * @param idsSoportesSeleccionado
	 *            The idsSoportesSeleccionado to set.
	 */
	public void setIdsSoportesSeleccionado(String[] idsSoportesSeleccionado) {
		this.idsSoportesSeleccionado = idsSoportesSeleccionado;
	}

	public boolean isChanged() {
		return this.changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public String getFechafinal() {
		return this.fechafinal;
	}

	public void setFechafinal(String fechafinal) {
		this.fechafinal = fechafinal;
	}

	public String[] getGuidsProductor() {
		return this.guidsProductor;
	}

	public void setGuidsProductor(String[] guidsProductor) {
		this.guidsProductor = guidsProductor;
	}

	// public int getApartirde() {
	// return this.apartirde;
	// }
	// public void setApartirde(int apartirde) {
	// this.apartirde = apartirde;
	// }

	public String getDefinicion() {
		return this.definicion;
	}

	public void setDefinicion(String definicion) {
		this.definicion = definicion;
	}

	public String getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getDocumentosbasicos() {
		return this.documentosbasicos;
	}

	public void setDocumentosbasicos(String documentosbasicos) {
		this.documentosbasicos = documentosbasicos;
	}

	public String getFechainicial() {
		return this.fechainicial;
	}

	public void setFechainicial(String fechainicial) {
		this.fechainicial = fechainicial;
	}

	public String getProcedimiento() {
		return this.procedimiento;
	}

	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}

	public String getIdserie() {
		return this.idserie;
	}

	public void setIdserie(String idserie) {
		this.idserie = idserie;
	}

	public String getNormativa() {
		return this.normativa;
	}

	public void setNormativa(String normativa) {
		this.normativa = normativa;
	}

	public String getTramites() {
		return this.tramites;
	}

	public void setTramites(String tramites) {
		this.tramites = tramites;
	}

	// public void setApartideprocedimiento(){
	// this.apartirde = 1;
	// }
	// public void setApartideentidadproductora(){
	// this.apartirde = 2;
	// }
	// public void setApartideorgdep(){
	// this.apartirde = 3;
	// }
	// public boolean isApartideprocedimiento(){
	// return this.apartirde == 1;
	// }
	// public boolean isApartideentidadproductora(){
	// return this.apartirde == 2;
	// }
	// public boolean isApartideorgdep(){
	// return this.apartirde == 3;
	// }

	public void setArchivoXProductor(String idProductor, String idArchivo) {
		archivoXProductor.put(idProductor, idArchivo);
	}

	public String getArchivoXProductor(String idProductor) {
		return (String) archivoXProductor.get(idProductor);
	}

	public void clear() {
		// idserie = null;=>este no, es un parametro!
		procedimiento = null;
		denominacion = null;
		definicion = null;
		tramites = null;
		normativa = null;
		documentosbasicos = null;
		fechainicial = null;
		fechafinal = null;
		changed = false;
		guidsProductor = null;
		setGuidProductor(null);
		setGuidProductorHistorico(null);
		idsSoportesSeleccionado = null;
		idsSoportesSeleccionadoAEliminar = null;
		descripcionProductor = null;
		nombreProductor = null;
		fechaFinVigenciaProductor = null;
		guardado = false;
	}

	public void fill(IdentificacionSerie identificacion) {
		// IdentificacionSerie identificacion = serie.getIdentificacionEntity();
		definicion = identificacion.getDefinicion();
		tramites = identificacion.getTramites();
		normativa = identificacion.getNormativa();
		documentosbasicos = identificacion.getDocsBasicosUnidadDocumental();
		// fechainicial = serie.getFextremainicial();
		// fechafinal = serie.getFextremafinal();
		denominacion = identificacion.getDenominacion();

	}

	public boolean isWarningSinProductor() {
		return warningSinProductor;
	}

	public void setWarningSinProductor(boolean warningSinProductor) {
		this.warningSinProductor = warningSinProductor;
	}

	public String getProcedimientoSeleccionado() {
		return procedimientoSeleccionado;
	}

	public void setProcedimientoSeleccionado(String procedimientoSeleccionado) {
		this.procedimientoSeleccionado = procedimientoSeleccionado;
	}

	public boolean isGuardado() {
		return guardado;
	}

	public void setGuardado(boolean guardado) {
		this.guardado = guardado;
	}

	public String getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(String idSerie) {
		this.idSerie = idSerie;
	}

	public String getIdProcedimientoSeleccionado() {
		return idProcedimientoSeleccionado;
	}

	public void setIdProcedimientoSeleccionado(
			String idProcedimientoSeleccionado) {
		this.idProcedimientoSeleccionado = idProcedimientoSeleccionado;
	}

	public String getProductoresSeleccionados() {
		return productoresSeleccionados;
	}

	public void setProductoresSeleccionados(String productoresSeleccionados) {
		this.productoresSeleccionados = productoresSeleccionados;
	}

	public String getArchivoReceptor() {
		return archivoReceptor;
	}

	public void setArchivoReceptor(String archivoReceptor) {
		this.archivoReceptor = archivoReceptor;
	}

	public String[] getDescripcionProductor() {
		return descripcionProductor;
	}

	public void setDescripcionProductor(String[] descripcionProductor) {
		this.descripcionProductor = descripcionProductor;
	}

	public String[] getNombreProductor() {
		return nombreProductor;
	}

	public void setNombreProductor(String[] nombreProductor) {
		this.nombreProductor = nombreProductor;
	}

	public String[] getArchivoPorProductor() {
		return archivoPorProductor;
	}

	public void setArchivoPorProductor(String[] archivoPorProductor) {
		this.archivoPorProductor = archivoPorProductor;
	}

	public String[] getProductoresAntiguosPrimerNodo() {
		return productoresAntiguosPrimerNodo;
	}

	public void setProductoresAntiguosPrimerNodo(
			String[] productoresAntiguosPrimerNodo) {
		this.productoresAntiguosPrimerNodo = productoresAntiguosPrimerNodo;
	}

	public String getProductoresAntiguos() {
		return productoresAntiguos;
	}

	public void setProductoresAntiguos(String productoresAntiguos) {
		this.productoresAntiguos = productoresAntiguos;
	}

	public String getSoportePrevisionAnual() {
		return soportePrevisionAnual;
	}

	public void setSoportePrevisionAnual(String soportePrevisionAnual) {
		this.soportePrevisionAnual = soportePrevisionAnual;
	}

	public String getTipoDocumentacion() {
		return tipoDocumentacion;
	}

	public void setTipoDocumentacion(String tipoDocumentacion) {
		this.tipoDocumentacion = tipoDocumentacion;
	}

	public String getVolumenPrevisionAnual() {
		return volumenPrevisionAnual;
	}

	public void setVolumenPrevisionAnual(String volumenPrevisionAnual) {
		this.volumenPrevisionAnual = volumenPrevisionAnual;
	}

	/**
	 * @return the fechaFinVigenciaProductor
	 */
	public String getFechaFinVigenciaProductor() {
		return fechaFinVigenciaProductor;
	}

	/**
	 * @param fechaFinVigenciaProductor
	 *            the fechaFinVigenciaProductor to set
	 */
	public void setFechaFinVigenciaProductor(String fechaFinVigenciaProductor) {
		this.fechaFinVigenciaProductor = fechaFinVigenciaProductor;
	}

	public void setFechaInicioVigenciaProductor(
			String fechaInicioVigenciaProductor) {
		this.fechaInicioVigenciaProductor = fechaInicioVigenciaProductor;
	}

	public String getFechaInicioVigenciaProductor() {
		return fechaInicioVigenciaProductor;
	}

	public void setGuidProductor(String guidProductor) {
		this.guidProductor = guidProductor;
	}

	public String getGuidProductor() {
		return guidProductor;
	}

	public void setGuidProductorHistorico(String guidProductorHistorico) {
		this.guidProductorHistorico = guidProductorHistorico;
	}

	public String getGuidProductorHistorico() {
		return guidProductorHistorico;
	}

}