package ieci.tdw.ispac.ispaclib.bpm;

import java.io.Serializable;

/**
 * Clase para almacenar la información de un proceso.
 *
 */
public class Process implements Serializable {

	public static final int TYPE_PROCESS = 1;
	public static final int TYPE_SUBPROCESS = 2;
	
	/** Identificador del proceso. */
	private String id = null;
	
	/** Identificador del proceso en SPAC. */
	private int spacProcessId = -1; 
	
	/** Nombre del proceso. */
	private String name = null;
	
	/** Identificador del responsable. */
	private String respId = null;
	
	/** Indica si es un proceso o un subproceso*/
	protected int processType = TYPE_PROCESS;
	
	/**
	 * Constructor.
	 *
	 */
	public Process() {
		super();
	}
	

	/**
	 * Obtiene el identificador del proceso.
	 * @return Identificador del proceso.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establece el identificador del proceso.
	 * @param id Identificador del proceso.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del proceso.
	 * @return Nombre del proceso.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Establece el nombre del proceso.
	 * @param name Nombre del proceso.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Obtiene el identificador el responsable.
	 * @return Identificador el responsable.
	 */
	public String getRespId() {
		return respId;
	}

	/**
	 * Establece el identificador el responsable.
	 * @param respId Identificador el responsable.
	 */
	public void setRespId(String respId) {
		this.respId = respId;
	}

	/**
	 * Obtiene el identificador del proceso en SPAC.
	 * @return Identificador del proceso en SPAC.
	 */
	public int getSpacProcessId() {
		return spacProcessId;
	}

	/**
	 * Establece el identificador del proceso en SPAC.
	 * @param spacPcdId Identificador del proceso en SPAC.
	 */
	public void setSpacProcessId(int spacPcdId) {
		this.spacProcessId = spacPcdId;
	}

	
	
	
	/**
	 * Obtiene el tipo de proceso (Proceso/Subproceso)
	 * @return Tipo de proceso
	 */
	public int getProcessType() {
		return processType;
	}


	/**
	 * Establece el tipo de proceso (Proceso/Subproceso)
	 * @param processType Tipo de proceso
	 */
	public void setProcessType(int processType) {
		this.processType = processType;
	}


	public String toString() {
		return new StringBuffer()
			.append("id=[").append(id).append("], ")
			.append("spacProcessId=[").append(spacProcessId).append("], ")
			.append("name=[").append(name).append("], ")
			.append("respId=[").append(respId).append("]")
			.toString();
	}
}
