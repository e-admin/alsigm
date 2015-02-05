package se.tramites;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.keyvalue.DefaultMapEntry;

public class ExpedienteImpl implements Expediente {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	InfoBExpediente infoExpediente = null;
	Date fechaInicio = null;
	Date fechaFin = null;
	Map.Entry organoProductor = null;
	String asunto = null;
	List documentosFisicos = new ArrayList();
	List documentosElectronicos = new ArrayList();
	List interesados = new ArrayList();
	List emplazamientos = new ArrayList();

	public ExpedienteImpl() {
		super();
	}

	public ExpedienteImpl(InfoBExpediente infoExpediente, String asunto) {
		super();
		this.infoExpediente = infoExpediente;
		this.asunto = asunto;
	}

	/**
	 * Devuelve la información básica de un expediente.
	 * 
	 * @return Información básica de un expediente.
	 */
	public InfoBExpediente getInformacionBasica() {
		return infoExpediente;
	}

	/**
	 * Establece la información básica de un expediente.
	 * 
	 * @param info
	 *            Información básica de un expediente.
	 */
	public void setInformacionBasica(InfoBExpediente info) {
		this.infoExpediente = info;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public void setDocumentosElectronicos(List documentosElectronicos) {
		this.documentosElectronicos = documentosElectronicos;
	}

	public void setDocumentosFisicos(List documentosFisicos) {
		this.documentosFisicos = documentosFisicos;
	}

	public void setEmplazamientos(List emplazamientos) {
		this.emplazamientos = emplazamientos;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public void setInfoExpediente(InfoBExpediente infoExpediente) {
		this.infoExpediente = infoExpediente;
	}

	public void setInteresados(List interesados) {
		this.interesados = interesados;
	}

	/**
	 * Devuelve la fecha de inicio del expediente.
	 * 
	 * @return Fecha de inicio del expediente.
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Devuelve la fecha de finalización del expediente.
	 * 
	 * @return Fecha de finalización del expediente.
	 */
	public Date getFechaFinalizacion() {
		return fechaFin;
	}

	/**
	 * Devuelve el asunto del expediente.
	 * 
	 * @return Asunto del expediente.
	 */
	public String getAsunto() {
		return asunto;
	}

	/**
	 * Devuelve la lista de documentos físicos del expediente.
	 * 
	 * @return Lista de documentos físicos.
	 *         <p>
	 *         Los objetos de la lista tienen que implementar el interface
	 *         {@link DocFisico}.
	 *         </p>
	 */
	public List getDocumentosFisicos() {
		return documentosFisicos;
	}

	/**
	 * Devuelve la lista de documentos electrónicos del expediente.
	 * 
	 * @return Lista de documentos electrónicos.
	 *         <p>
	 *         Los objetos de la lista tienen que implementar el interface
	 *         {@link DocElectronico}.
	 *         </p>
	 */
	public List getDocumentosElectronicos() {
		return documentosElectronicos;
	}

	/**
	 * Devuelve la lista de interesados asociados al expediente.
	 * 
	 * @return Lista de interesados.
	 *         <p>
	 *         Los objetos de la lista tienen que implementar el interface
	 *         {@link Interesado}.
	 *         </p>
	 */
	public List getInteresados() {
		return interesados;
	}

	/**
	 * Devuelve la lista de emplazamientos del expediente.
	 * 
	 * @return Lista de emplazamientos.
	 *         <p>
	 *         Los objetos de la lista tienen que implementar el interface
	 *         {@link Emplazamiento}.
	 *         </p>
	 */
	public List getEmplazamientos() {
		return emplazamientos;
	}

	/**
	 * Devuelve el identificador del órgano productor.
	 * 
	 * @return Identificador del órgano productor.
	 */
	public String getIdOrgProductor() {
		return (String) organoProductor.getKey();
	}

	/**
	 * Devuelve el nombre del órgano productor.
	 * 
	 * @return Nombre del órgano productor.
	 */
	public String getNombreOrgProductor() {
		return (String) organoProductor.getValue();
	}

	public void addDocumentoFisico(String tipoDocumento, String asunto) {
		documentosFisicos.add(new DocFisicoImpl(tipoDocumento, asunto));
	}

	public void addDocumentoElectronico(DocElectronico docElectronico) {
		documentosElectronicos.add(docElectronico);
	}

	public void setOrganoProductor(String idOrgano, String nombreOrgano) {
		this.organoProductor = new DefaultMapEntry(idOrgano, nombreOrgano);
	}

	public void addEmplazamiento(String pais, String comunidad, String concejo,
			String poblacion, String localizacion, String validado) {
		this.emplazamientos.add(new EmplazamientoImpl(pais, comunidad, concejo,
				poblacion, localizacion, validado));
	}
}
