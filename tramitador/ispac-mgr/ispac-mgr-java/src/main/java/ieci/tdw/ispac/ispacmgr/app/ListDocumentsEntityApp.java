package ieci.tdw.ispac.ispacmgr.app;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.app.GenericEntityListEntityApp;
import ieci.tdw.ispac.ispaclib.common.constants.DocumentLockStates;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.item.CompositeItem;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.sql.Types;
import java.util.Iterator;


public class ListDocumentsEntityApp extends GenericEntityListEntityApp {
	
	private static final long serialVersionUID = 3362260960093987130L;

	public ListDocumentsEntityApp(ClientContext context) {
        super(context);
    }
    
	public void initiate() throws ISPACException {
		
		super.initiate();
		
	    if (StringUtils.equals(getString("SPAC_DT_DOCUMENTOS:BLOQUEO"), DocumentLockStates.TOTAL_LOCK)) {
	    	setProperty("BLOQUEO", Boolean.TRUE);
	    }
	    
	    IEntitiesAPI entitiesAPI = mContext.getAPI().getEntitiesAPI();
	    IItem itemCatDoc = entitiesAPI.getCatalogEntity(getMainEntity());
	    String idExt = mitem.getString(itemCatDoc.getString("NOMBRE") +":DESTINO_ID"); 
	    
	    if (idExt != null) {
	    	
		    IItem itemExp = entitiesAPI.getExpedient(mitem.getString(itemCatDoc.getString("NOMBRE") + ":" + itemCatDoc.getString("CAMPO_NUMEXP")));
		    IItemCollection participantes=entitiesAPI.getParticipants(itemExp.getString("NUMEXP"), "", "");
	
		    IItem itemThird = null;

	    	if (StringUtils.equals(idExt, itemExp.getString("IDTITULAR"))){
	    		itemThird = composeThirdParty(itemExp.getString("IDENTIDADTITULAR"), itemExp.getString("IDTITULAR"), itemExp.getString("TIPODIRECCIONINTERESADO"));
	    	}else{
	    		IItemCollection itemcol = entitiesAPI.queryEntities(SpacEntities.SPAC_DT_INTERVINIENTES, "WHERE NUMEXP = '" + DBUtil.replaceQuotes(mitem.getString(itemCatDoc.getString("NOMBRE") + ":" + itemCatDoc.getString("CAMPO_NUMEXP"))) + "' AND ID_EXT = '" + idExt+ "'");
	    		if (itemcol.next()){
	    			IItem item = itemcol.value();
	    			itemThird = composeThirdParty(item.getString("NOMBRE"), item.getString("ID_EXT"), item.getString("TIPO_DIRECCION"));
	    		}
	    	}
	    	if (itemThird != null) {
	    		((CompositeItem)mitem).addItem(itemThird, "THIRD_PARTY:", false);
	    	}
	    	
		    ((CompositeItem)mitem).addItem(getTipoDireccionTelematica(itemExp.getString("TIPODIRECCIONINTERESADO"), participantes), "SPAC_DT_INTERVINIENTES:");
	    }
	}
	
    public IItem getTipoDireccionTelematica(String direccion, IItemCollection participantes ) throws ISPACException{
    	Properties properties = new Properties();
    	int ordinal = 0;
    	boolean asignado=false;
    	Object destino=this.getProperty("SPAC_DT_DOCUMENTOS:DESTINO");
    	properties.add(new Property(ordinal++,"TIPODIRECCIONINTERESADO",Types.VARCHAR));
    	properties.add(new Property(ordinal++,"ID",Types.VARCHAR));
    	IItem item = new GenericItem(properties, "TIPODIRECCIONINTERESADO");
    	//Es el principal
    	if(participantes==null || !participantes.iterator().hasNext() || destino==null){
    		item.set("TIPODIRECCIONINTERESADO", direccion);
    		
    		asignado=true;
    	}
    	else{
    		
    		Iterator itr= participantes.iterator();
    		while(itr.hasNext() && !asignado){
    			IItem item1= (IItem) itr.next();
    			String nombrePart=(String) item1.get("NOMBRE");
    			
    			if (StringUtils.isNotBlank(nombrePart) 
    					&& nombrePart.equalsIgnoreCase(destino.toString())) {
    				asignado=true;
    				item.set("TIPODIRECCIONINTERESADO", item1.get("TIPO_DIRECCION"));
    				item.set("ID",item1.get("ID"));
    			}
    		}
    		
    	}
    	//Es destino es el interesado principal
    	if(!asignado){
    		item.set("TIPODIRECCIONINTERESADO", direccion);
    	}
    	return item;
    }

    public IItem composeThirdParty (String nombre, String idExt, String tipoDireccionTelematica) throws ISPACException{
    	Properties properties = new Properties();
    	int ordinal = 0;
    	properties.add(new Property(ordinal++,"IDTITULAR",Types.VARCHAR));
    	properties.add(new Property(ordinal++,"IDENTIDADTITULAR",Types.VARCHAR));
    	properties.add(new Property(ordinal++,"TIPO_DIRECCION_TELEMATICA_VALOR",Types.VARCHAR));    	
    	properties.add(new Property(ordinal++,"TIPO_DIRECCION_TELEMATICA_SUSTITUTO",Types.VARCHAR));    	
    	
    	IItem item = new GenericItem(properties, "IDTITULAR");
    	item.set("IDENTIDADTITULAR", nombre);
    	item.set("IDTITULAR", idExt);
    	item.set("TIPO_DIRECCION_TELEMATICA_VALOR", tipoDireccionTelematica);

    	IEntitiesAPI entitiesAPI = mContext.getAPI().getEntitiesAPI();
    	IItemCollection itemcol =  entitiesAPI.queryEntities(SpacEntities.SPAC_TBL_005, "WHERE VALOR = '" + DBUtil.replaceQuotes(tipoDireccionTelematica) + "'");
    	
    	if (itemcol.next()) {
    		tipoDireccionTelematica = itemcol.value().getString("SUSTITUTO");
    	} else {
    		tipoDireccionTelematica = "";
    	}
    	
    	item.set("TIPO_DIRECCION_TELEMATICA_SUSTITUTO", tipoDireccionTelematica);
    	
    	return item;
    }
    
	
}