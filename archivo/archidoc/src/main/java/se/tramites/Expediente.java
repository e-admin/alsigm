package se.tramites;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Interfaz para la información de un expediente.
 */
public interface Expediente extends Serializable {

	/**
	 * Devuelve la información básica de un expediente.
	 * 
	 * @return Información básica de un expediente.
	 */
	public InfoBExpediente getInformacionBasica();

	/**
	 * Devuelve la fecha de inicio del expediente.
	 * 
	 * @return Fecha de inicio del expediente.
	 */
	public Date getFechaInicio();

	/**
	 * Devuelve la fecha de finalización del expediente.
	 * 
	 * @return Fecha de finalización del expediente.
	 */
	public Date getFechaFinalizacion();

	/**
	 * Devuelve el identificador del órgano productor.
	 * 
	 * @return Identificador del órgano productor.
	 */
	public String getIdOrgProductor();

	/**
	 * Devuelve el nombre del órgano productor.
	 * 
	 * @return Nombre del órgano productor.
	 */
	public String getNombreOrgProductor();

	/**
	 * Devuelve el asunto del expediente.
	 * 
	 * @return Asunto del expediente.
	 */
	public String getAsunto();

	/**
	 * Devuelve la lista de documentos físicos del expediente.
	 * 
	 * @return Lista de documentos físicos.
	 *         <p>
	 *         Los objetos de la lista tienen que implementar el interface
	 *         {@link DocFisico}.
	 *         </p>
	 */
	public List getDocumentosFisicos();

	/**
	 * Devuelve la lista de documentos electrónicos del expediente.
	 * 
	 * @return Lista de documentos electrónicos.
	 *         <p>
	 *         Los objetos de la lista tienen que implementar el interface
	 *         {@link DocElectronico}.
	 *         </p>
	 */
	public List getDocumentosElectronicos();

	/**
	 * Devuelve la lista de interesados asociados al expediente.
	 * 
	 * @return Lista de interesados.
	 *         <p>
	 *         Los objetos de la lista tienen que implementar el interface
	 *         {@link Interesado}.
	 *         </p>
	 */
	public List getInteresados();

	/**
	 * Devuelve la lista de emplazamientos del expediente.
	 * 
	 * @return Lista de emplazamientos.
	 *         <p>
	 *         Los objetos de la lista tienen que implementar el interface
	 *         {@link Emplazamiento}.
	 *         </p>
	 */
	public List getEmplazamientos();

}
