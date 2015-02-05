package ieci.tdw.ispac.services.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Información de un expediente.
 */
public class Expediente implements Serializable {

	private static final long serialVersionUID = -965709721950821274L;

	/** Información básica del expediente. */
	private InfoBExpediente informacionBasica = new InfoBExpediente();

	/** Fecha de inicio del expediente. */
	private Date fechaInicio = null;

	/** Fecha de finalización del expediente. */
	private Date fechaFinalizacion = null;

	/** Identificador del órgano productor del expediente. */
	private String idOrgProductor = null;

	/** Nombre del órgano productor del expediente. */
	private String nombreOrgProductor = null;

	/** Asunto del expediente. */
	private String asunto = null;

	/** Documentos físicos del expediente. */
	private DocFisico[] documentosFisicos = new DocFisico[0];

	/** Documentos electrónicos del expediente. */
	private DocElectronico[] documentosElectronicos = new DocElectronico[0];

	/** Interesados del expediente. */
	private Interesado[] interesados = new Interesado[0];

	/** Emplazamientos del expediente. */
	private Emplazamiento[] emplazamientos = new Emplazamiento[0];

	/**
	 * Constructor.
	 */
	public Expediente() {
		super();
	}

	/**
	 * Obtiene el asunto del expediente.
	 * @return Asunto del expediente.
	 */
	public String getAsunto() {
		return asunto;
	}

	/**
	 * Establece el asunto del expediente.
	 * @param asunto Asunto del expediente.
	 */
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	/**
	 * Obtiene los documentos físicos del expediente.
	 * @return Documentos físicos del expediente.
	 */
	public DocFisico[] getDocumentosFisicos() {
		return documentosFisicos;
	}

	/**
	 * Establece los documentos físicos del expediente.
	 * @param documentosFisicos Documentos físicos del expediente.
	 */
	public void setDocumentosFisicos(DocFisico[] documentosFisicos) {
		this.documentosFisicos = documentosFisicos;
	}

	/**
	 * Obtiene los documentos electrónicos del expediente.
	 * @return Documentos electrónicos del expediente.
	 */
	public DocElectronico[] getDocumentosElectronicos() {
		return documentosElectronicos;
	}

	/**
	 * Establece los documentos electrónicos del expediente.
	 * @param documentosElectronicos Documentos electrónicos del expediente.
	 */
	public void setDocumentosElectronicos(
			DocElectronico[] documentosElectronicos) {
		this.documentosElectronicos = documentosElectronicos;
	}

	/**
	 * Obtiene los Emplazamientos del expediente.
	 * @return Emplazamientos del expediente.
	 */
	public Emplazamiento[] getEmplazamientos() {
		return emplazamientos;
	}

	/**
	 * Establece los emplazamientos del expediente.
	 * @param emplazamientos Emplazamientos del expediente.
	 */
	public void setEmplazamientos(Emplazamiento[] emplazamientos) {
		this.emplazamientos = emplazamientos;
	}

	/**
	 * Obtiene la fecha de finalización del expediente.
	 * @return Fecha de finalización del expediente.
	 */
	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}

	/**
	 * Establece la decha de finalización del expediente.
	 * @param fechaFinalizacion Fecha de finalización del expediente.
	 */
	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}

	/**
	 * Obtiene la fecha de inicio del expediente.
	 * @return Fecha de inicio del expediente.
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Establece la fecha de inicio del expediente.
	 * @param fechaInicio Fecha de inicio del expediente.
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * Obtiene el identificador del órgano productor.
	 * @return Identificador del órgano productor.
	 */
	public String getIdOrgProductor() {
		return idOrgProductor;
	}

	/**
	 * Establece el identificador del órgano productor.
	 * @param idOrgProductor Identificador del órgano productor.
	 */
	public void setIdOrgProductor(String idOrgProductor) {
		this.idOrgProductor = idOrgProductor;
	}

	/**
	 * Obtiene la información básica del expediente.
	 * @return Información básica del expediente.
	 */
	public InfoBExpediente getInformacionBasica() {
		return informacionBasica;
	}

	/**
	 * Establece la información básica del expediente.
	 * @param informacionBasica Información básica del expediente.
	 */
	public void setInformacionBasica(InfoBExpediente informacionBasica) {
		this.informacionBasica = informacionBasica;
	}

	/**
	 * Obtiene los interesados del expediente.
	 * @return Interesados del expediente.
	 */
	public Interesado[] getInteresados() {
		return interesados;
	}

	/**
	 * Establece los interesados del expediente.
	 * @param interesados Interesados del expediente.
	 */
	public void setInteresados(Interesado[] interesados) {
		this.interesados = interesados;
	}

	/**
	 * Obtiene el nombre del órgano productor.
	 * @return Nombre del órgano productor.
	 */
	public String getNombreOrgProductor() {
		return nombreOrgProductor;
	}

	/**
	 * Establece el nombre del órgano productor.
	 * @param nombreOrgProductor Nombre del órgano productor.
	 */
	public void setNombreOrgProductor(String nombreOrgProductor) {
		this.nombreOrgProductor = nombreOrgProductor;
	}

}
