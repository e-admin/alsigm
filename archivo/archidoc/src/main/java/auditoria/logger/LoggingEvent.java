package auditoria.logger;

import java.util.ArrayList;
import java.util.List;

import se.usuarios.ServiceClient;

import common.bi.IServiceBase;
import common.vos.BaseVO;

/**
 * Clase que encapsula todos los datos necesarios para el registro de un evento.
 */
public class LoggingEvent extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Modulo al que pertenece el evento que estamos registrando */
	private int module = 0;
	/** Accion que estamos registrando */
	private int action = 0;
	/**
	 * Usuario generador del evento. Contiene: Dirección IP del cliente que está
	 * generando la pista Identificador del usuario que genera la pista
	 * Criticidad para ese usuario.
	 **/
	private ServiceClient user = null;
	/** Codigo de error en caso de que vaya un error asociado al evento */
	private int codError = 0;
	/** Listado de los datos asociados al evento */
	private List data = null;
	/** Indica si el evento es un evento del sistema */
	private boolean systemEvent = false;
	private IServiceBase serviceToAudit = null;

	/**
	 * Constructor por defecto del evento
	 * 
	 * @param module
	 *            Modulo afectado
	 * @param action
	 *            Accion realizada
	 * @param user
	 *            Usuario generador
	 * @param isSystemEvent
	 *            indicador de si el evento es de sistema
	 */
	public LoggingEvent(int module, int action, ServiceClient user,
			boolean isSystemEvent) {
		this.module = module;
		this.action = action;
		this.user = user;
		this.systemEvent = isSystemEvent;
	}

	/**
	 * Constructor por defecto del evento
	 * 
	 * @param module
	 *            Modulo afectado
	 * @param action
	 *            Accion realizada
	 * @param user
	 *            Usuario generador
	 * @param codError
	 *            Codigo error que se genero con la accion
	 * @param isSystemEvent
	 *            indicadoir de si el evento es de sistema
	 */
	public LoggingEvent(int module, int action, ServiceClient user,
			int codError, boolean isSystemEvent) {
		this.module = module;
		this.action = action;
		this.user = user;
		this.codError = codError;
		this.systemEvent = isSystemEvent;
	}

	/**
	 * Constructor por defecto del evento
	 * 
	 * @param module
	 *            Modulo afectado
	 * @param action
	 *            Accion realizada
	 * @param user
	 *            Usuario generador
	 * @param data
	 *            Lista de datos asociados a la traza
	 */
	public LoggingEvent(int module, int action, ServiceClient user,
			boolean isSystemEvent, List data) {
		this.module = module;
		this.action = action;
		this.user = user;
		this.systemEvent = isSystemEvent;
		this.data = data;
	}

	/**
	 * Constructor por defecto del evento
	 * 
	 * @param module
	 *            Modulo afectado
	 * @param action
	 *            Accion realizada
	 * @param user
	 *            Usuario generador
	 * @param codError
	 *            Codigo error que se genero con la accion
	 * @param isSystemEvent
	 *            indicadoir de si el evento es de sistema
	 * @param data
	 *            Lista de datos asociados a la traza
	 */
	public LoggingEvent(int module, int action, ServiceClient user,
			int codError, boolean isSystemEvent, List data) {
		this.module = module;
		this.action = action;
		this.user = user;
		this.codError = codError;
		this.systemEvent = isSystemEvent;
		this.data = data;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public int getCodError() {
		return codError;
	}

	public void setCodError(int codError) {
		this.codError = codError;
	}

	public int getModule() {
		return module;
	}

	public void setModule(int module) {
		this.module = module;
	}

	public ServiceClient getUser() {
		return user;
	}

	public void setUser(ServiceClient user) {
		this.user = user;
	}

	public boolean isSystemEvent() {
		return systemEvent;
	}

	public void setSystemEvent(boolean systemEvent) {
		this.systemEvent = systemEvent;
	}

	public List getData() {
		if (this.data == null)
			data = new ArrayList();

		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	private void addData(DataLoggingEvent data) {
		if (this.data == null)
			this.data = new ArrayList();

		this.data.add(data);
	}

	public DataLoggingEvent getDataLoggingEvent(int object, String idObject) {
		DataLoggingEvent dataLoggingEvent = new DataLoggingEvent(object,
				idObject);
		addData(dataLoggingEvent);
		return dataLoggingEvent;
	}

	/**
	 * @return Returns the serviceToAudit.
	 */
	public IServiceBase getServiceToAudit() {
		return serviceToAudit;
	}

	/**
	 * @param serviceToAudit
	 *            The serviceToAudit to set.
	 */
	public void setServiceToAudit(IServiceBase serviceToAudit) {
		this.serviceToAudit = serviceToAudit;
	}
}
