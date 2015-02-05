package ieci.tdw.ispac.ispaccatalog.app;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IPublisherAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaccatalog.messages.Messages;
import ieci.tdw.ispac.ispaclib.app.SimpleEntityApp;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.item.CompositeItem;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import java.util.Date;

public class PubErrorMilestoneApp extends SimpleEntityApp {

	private static final long serialVersionUID = 1L;
	
    public PubErrorMilestoneApp(ClientContext context) {
		super(context);
	}

	public IItem processItem(IItem item) throws ISPACException {
		CompositeItem composite = new CompositeItem("PUB_HITOS_ACTIVOS:ID_HITO");
		composite.addItem(item, "PUB_HITOS_ACTIVOS:");

		return composite;
	}

	public void initiate() throws ISPACException {
		
		ICatalogAPI catalogAPI = mContext.getAPI().getCatalogAPI();
		IPublisherAPI publisherAPI = mContext.getAPI().getPublisherAPI();
		
		// Obtener el error
		int milestoneId = getItem().getInt("PUB_HITOS_ACTIVOS:ID_HITO");
		int applicationId = getItem().getInt("PUB_HITOS_ACTIVOS:ID_APLICACION");
		String systemId = getItem().getString("PUB_HITOS_ACTIVOS:ID_SISTEMA");
//		String objectId = getItem().getString("PUB_HITOS_ACTIVOS:ID_OBJETO");
		
//		IItemCollection errorcol = publisherAPI.getErrors(milestoneId, applicationId, systemId, objectId);
//		if (errorcol.next()) {
//			IItem error = errorcol.value();
//			((CompositeItem)mitem).addItem(error, "PUB_ERRORES:", false);
//		}

		IItem error = publisherAPI.getError(milestoneId, applicationId, systemId);
		if (error != null) {
			((CompositeItem)mitem).addItem(error, "PUB_ERRORES:", false);
		}
		
		getAsociatedEntity(catalogAPI, ICatalogAPI.ENTITY_P_PROCEDURE, "SPAC_P_PROCEDIMIENTOS", "PUB_HITOS_ACTIVOS:ID_PCD", "form.pubRule.msg.all");
		getAsociatedEntity(catalogAPI, ICatalogAPI.ENTITY_P_STAGE, "SPAC_P_FASES", "PUB_HITOS_ACTIVOS:ID_FASE", "form.pubRule.msg.all.a");
		getAsociatedEntity(catalogAPI, ICatalogAPI.ENTITY_P_TASK, "SPAC_P_TRAMITES", "PUB_HITOS_ACTIVOS:ID_TRAMITE", "form.pubRule.msg.all");
		getAsociatedEntity(catalogAPI, ICatalogAPI.ENTITY_CT_TYPEDOC, "SPAC_CT_TPDOC", "PUB_HITOS_ACTIVOS:TIPO_DOC", "form.pubRule.msg.all");
		getAsociatedEntity(catalogAPI, ICatalogAPI.ENTITY_PUB_APPLICATIONS, "PUB_APLICACIONES_HITOS", "PUB_HITOS_ACTIVOS:ID_APLICACION", null);
		
		Date timestamp = getItem().getDate("PUB_HITOS_ACTIVOS:FECHA");
		setProperty("FECHA", TypeConverter.toString(timestamp, TypeConverter.DATETIMEFORMAT));
		
		int eventId = getItem().getInt("PUB_HITOS_ACTIVOS:ID_EVENTO");
		setProperty("EVENT_NAME", Messages.getString(mContext.getAppLanguage(), "event.milestone.type." + eventId));
		if (eventId == 8) {
			setProperty("INFO_NAME", Messages.getString(mContext.getAppLanguage(), "id.info.type." + getItem().getInt("PUB_HITOS_ACTIVOS:ID_INFO")));
		}
		
		setURL("/forms/publisher/puberrormilestonesform.jsp");
    }
	
	public boolean validate() throws ISPACException {
		return true;
	}
	
    /**
     * Obtiene la entidad asociada
     *
     * @param entityId Identificador de la entidad asociada
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
		   	 			throw new ISPACException("No existe la entidad " + entityName + " asociada al hito");
		   	 		}
				}
			}
		}
    }

}