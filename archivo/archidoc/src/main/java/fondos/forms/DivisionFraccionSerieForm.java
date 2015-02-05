package fondos.forms;

import es.archigest.framework.web.action.ArchigestActionForm;

/**
 * Formulario para la recogida de datos en la modificación de la información de
 * una división de fracción de serie.
 */
public class DivisionFraccionSerieForm extends ArchigestActionForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String nofUDocs;
	protected String numExp = null;
	protected String asunto = null;
	// protected String signatura = null;
	protected String fechaInicio = null;
	protected String fechaFin = null;
	protected String idNivelDocumental = null;
	protected String idFicha = null;
	protected String divisionesseleccionadas[];
	protected String iddivisionfsseleccionada = null;

	/**
	 * Constructor.
	 */
	public DivisionFraccionSerieForm() {
		super();
		nofUDocs = new String();
	}

	public String getNofUDocs() {
		return nofUDocs;
	}

	public void setNofUDocs(String nofUDocs) {
		this.nofUDocs = nofUDocs;
	}

	public String getNumExp() {
		return numExp;
	}

	public void setNumExp(String numExp) {
		this.numExp = numExp;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getIdFicha() {
		return idFicha;
	}

	public void setIdFicha(String idFicha) {
		this.idFicha = idFicha;
	}

	public String getIdNivelDocumental() {
		return idNivelDocumental;
	}

	public void setIdNivelDocumental(String idNivelDocumental) {
		this.idNivelDocumental = idNivelDocumental;
	}

	public String[] getDivisionesseleccionadas() {
		return divisionesseleccionadas;
	}

	public void setDivisionesseleccionadas(String[] divisionesseleccionadas) {
		this.divisionesseleccionadas = divisionesseleccionadas;
	}

	public String getIddivisionfsseleccionada() {
		return iddivisionfsseleccionada;
	}

	public void setIddivisionfsseleccionada(String iddivisionfsseleccionada) {
		this.iddivisionfsseleccionada = iddivisionfsseleccionada;
	}

}
