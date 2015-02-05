package ieci.tdw.ispac.services.mgr;

import ieci.tdw.ispac.api.impl.InvesflowAPI;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

public class ServiceManager {

	/** Contexto de cliente. */
	private ClientContext context = null;
	
	
	/**
	 * Constructor.
	 *
	 */
	public ServiceManager() {
		this.context = new ClientContext();
		this.context.setAPI(new InvesflowAPI(context));
	}
	
	/**
	 * Obtiene el contexto de cliente. 
	 * @return Contexto de cliente.
	 */
	public ClientContext getContext() {
		return context;
	}
}
