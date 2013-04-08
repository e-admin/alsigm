package ieci.tdw.ispac.ispaccatalog.app;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaccatalog.messages.Messages;
import ieci.tdw.ispac.ispaclib.app.SimpleEntityApp;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.item.CompositeItem;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

public class PubRuleApp extends SimpleEntityApp {

    public PubRuleApp(ClientContext context)
    {
        super(context);
    }

    public IItem processItem(IItem item)
	throws ISPACException
	{
	    CompositeItem composite = new CompositeItem("PUB_REGLAS:ID");
		composite.addItem(item, "PUB_REGLAS:");

		return composite;
	}

	public void initiate() throws ISPACException
    {
		ICatalogAPI catalogAPI = mContext.getAPI().getCatalogAPI();
		
		getAsociatedEntity(catalogAPI, ICatalogAPI.ENTITY_P_PROCEDURE, "SPAC_P_PROCEDIMIENTOS", "PUB_REGLAS:ID_PCD", "form.pubRule.msg.all");
		getAsociatedEntity(catalogAPI, ICatalogAPI.ENTITY_P_STAGE, "SPAC_P_FASES", "PUB_REGLAS:ID_FASE", "form.pubRule.msg.all.a");
		getAsociatedEntity(catalogAPI, ICatalogAPI.ENTITY_P_TASK, "SPAC_P_TRAMITES", "PUB_REGLAS:ID_TRAMITE", "form.pubRule.msg.all");
		getAsociatedEntity(catalogAPI, ICatalogAPI.ENTITY_CT_TYPEDOC, "SPAC_CT_TPDOC", "PUB_REGLAS:TIPO_DOC", "form.pubRule.msg.all");
		getAsociatedEntity(catalogAPI, ICatalogAPI.ENTITY_PUB_ACTIONS, "PUB_ACCIONES", "PUB_REGLAS:ID_ACCION", null);
		getAsociatedEntity(catalogAPI, ICatalogAPI.ENTITY_PUB_CONDITIONS, "PUB_CONDICIONES", "PUB_REGLAS:ID_CONDICION", null);
		getAsociatedEntity(catalogAPI, ICatalogAPI.ENTITY_PUB_APPLICATIONS, "PUB_APLICACIONES_HITOS", "PUB_REGLAS:ID_APLICACION", null);
    }
	
	public boolean validate() throws ISPACException
	{
	    return true;
	}
	
    /**
     * Obtiene la entidad asociada
     *
     * @param entityId identificador de la entidad asociada
     * @param entityName Nombre de la entidad asociada
     * @param propertyId Propiedad del identificador de la entidad asociada en la entidad principal
     */
    protected void getAsociatedEntity(ICatalogAPI catalogAPI,
    								  int ctentityId,
    								  String entityName,
    								  String propertyId,
    								  String msgAllKey) throws ISPACException {
		
		if (getItem().getString(propertyId) != null) {
			
			int id = getItem().getInt(propertyId);
			if (id != 0) {
				
				if (id == -1) {
					
					((CompositeItem)mitem).addItem(catalogAPI.getCTEntity(ctentityId), entityName + ":", false);
					mitem.set(entityName + ":NOMBRE", Messages.getString(mContext.getAppLanguage(), msgAllKey));
				}
				else {
			
		   	 		IItem entity = catalogAPI.getCTEntity(ctentityId, getItem().getInt(propertyId));
		   	 		if (entity != null) {
		   	 			
		   	 			((CompositeItem)mitem).addItem(entity, entityName + ":", false);
		   	 		}
		   	 		else {
		   	 			throw new ISPACException("No existe la entidad " + entityName + " asociada a la regla");
		   	 		}
				}
			}
		}
    }
    
	public void store()
	throws ISPACException {
		
		IItem item = getItem();
		
		if (StringUtils.isEmpty(item.getString("PUB_REGLAS:ID_FASE"))) {
			item.set("PUB_REGLAS:ID_FASE", 0);
		}
		
		if (StringUtils.isEmpty(item.getString("PUB_REGLAS:ID_TRAMITE"))) {
			item.set("PUB_REGLAS:ID_TRAMITE", 0);
		}
		
		if (StringUtils.isEmpty(item.getString("PUB_REGLAS:TIPO_DOC"))) {
			item.set("PUB_REGLAS:TIPO_DOC", 0);
		}
		
		if (item.getInt("PUB_REGLAS:ID_EVENTO") != 8) {
			item.set("PUB_REGLAS:ID_INFO", 0);
		}

		super.store();
		
		if (StringUtils.isEmpty(item.getString("PUB_REGLAS:ORDEN"))) {
			item.set("PUB_REGLAS:ORDEN", item.getKeyInt());
			super.store();
		}
	}

}