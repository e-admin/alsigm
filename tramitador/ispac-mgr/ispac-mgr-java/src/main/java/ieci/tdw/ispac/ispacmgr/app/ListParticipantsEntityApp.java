package ieci.tdw.ispac.ispacmgr.app;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.app.ListEntityApp;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

import java.util.List;

/**
 * @author Ildefonso Noreña
 *
 */

// TODO Eliminar ya que se utiliza la genérica con list-order en los parámetros
public class ListParticipantsEntityApp extends ListEntityApp {
	
    public ListParticipantsEntityApp(ClientContext context) {
        super(context);
    }
   
	protected List getItemBeanList(int entityId) throws ISPACException {
		
		IEntitiesAPI entitiesAPI = mContext.getAPI().getEntitiesAPI();
		
		IItemCollection participants = entitiesAPI.getParticipants(msExpedient, null, "NDOC");
   	 	List list = CollectionBean.getBeanList(participants);
   	 	
   	 	return list;
	}
	
}