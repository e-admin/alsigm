package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.ICustomAPI;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

/**
 * API para personalización.
 *
 */
public class CustomAPI implements ICustomAPI {
	
	/**
	 * Contexto de cliente.
	 */
    private ClientContext mcontext;

    /**
     * Constructor.
     * @param context Contexto de cliente
     */
    public CustomAPI(ClientContext context) {
        mcontext = context;
    }

	public ClientContext getClientContext() {
		return mcontext;
	}
}