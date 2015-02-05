/*
 * Created on 16-feb-2005
 *
 */
package ieci.tdw.ispac.ispacweb.api;

import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacweb.api.impl.ManagerAPI;

/**
 * @author juanin
 *
 *
 */
public final class ManagerAPIFactory
{
	static ManagerAPIFactory s_instance=null;

	private ManagerAPIFactory()
	{

	}

	static public synchronized ManagerAPIFactory getInstance()
	{
		if (s_instance==null)
			s_instance=new ManagerAPIFactory();
		return s_instance;
	}

	public IManagerAPI getManagerAPI(ClientContext cctx)
	{
		return new ManagerAPI(cctx);
	}
}
