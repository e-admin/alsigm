package ieci.tdw.ispac.ispaclib.app;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

import java.util.List;

public class GenericEntityListEntityApp extends ListEntityApp {
	
	private static final long serialVersionUID = -4388852799326434685L;

	public GenericEntityListEntityApp(ClientContext context) {
		super(context);
	}

	protected List getItemBeanList(int entityId) throws ISPACException {
		
		IEntitiesAPI entitiesAPI = mContext.getAPI().getEntitiesAPI();
		
		IItemCollection collection = entitiesAPI.getEntitiesWithOrder(entityId, msExpedient, null, getListOrder());
   	 	List list = CollectionBean.getBeanList(collection);
   	 	
   	 	return list;
	}
	
}