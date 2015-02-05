package ieci.tdw.ispac.ispaccatalog.app;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.messages.Messages;
import ieci.tdw.ispac.ispaclib.app.SimpleEntityApp;
import ieci.tdw.ispac.ispaclib.bean.ValidationError;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTFrmBusquedaOrgDAO;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

public class CTFrmBusquedaApp extends SimpleEntityApp {

	public CTFrmBusquedaApp(ClientContext context) {
		super(context);
	}

	public void initiate() throws ISPACException {
		super.initiate();		
	}

	public boolean validate() throws ISPACException {
		
		boolean ret = super.validate();
		
		if (ret) {
		
			ValidationError error = null;
	
			String descripcion = (String)getProperty("DESCRIPCION");
			if(StringUtils.isBlank(descripcion)){
				
				error = new ValidationError("property(DESCRIPCION)", "errors.required", new String[]{Messages.getString(mContext.getAppLanguage(),"form.search.propertyLabel.description")});
				addError(error);
				return false;
			}
			else {
				IInvesflowAPI invesFlowAPI = mContext.getAPI();
				ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
				
				// Bloqueo de la tabla
				catalogAPI.setCTEntitiesForUpdate(ICatalogAPI.ENTITY_CT_SEARCHFORM, "");
				
				// Nombre único de calendario
				IItemCollection itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_SEARCHFORM, " WHERE DESCRIPCION = '" + DBUtil.replaceQuotes(descripcion) + "' AND ID != " + getString("ID"));
				if (itemcol.next()) {
					
					addError(new ValidationError("property(DESCRIPCION)", "error.searchform.nameDuplicated", new String[] {descripcion}));
					return false;
				}
			}
			
			return true;
		}
		else {
			return false;
		}	
	}

	public void store() throws ISPACException {
		
		if ( StringUtils.equals(mitem.getString("TIPO"),Integer.toString(CTFrmBusquedaOrgDAO.GENERIC_TYPE)) ){
			CollectionDAO collection = new CollectionDAO(CTFrmBusquedaOrgDAO.class);
			collection.delete(mContext.getConnection(), "WHERE ID_SEARCHFRM = " + mitem.getKeyInt());
		}
			
		super.store();
	}
	
}