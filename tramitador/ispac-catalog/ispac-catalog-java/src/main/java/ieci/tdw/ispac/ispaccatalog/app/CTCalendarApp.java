package ieci.tdw.ispac.ispaccatalog.app;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.deadline.CalendarDef;
import ieci.tdw.ispac.ispaccatalog.messages.Messages;
import ieci.tdw.ispac.ispaclib.app.SimpleEntityApp;
import ieci.tdw.ispac.ispaclib.bean.ValidationError;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

public class CTCalendarApp extends SimpleEntityApp {

	public CTCalendarApp(ClientContext context) {
		super(context);
	}

	public void initiate() throws ISPACException {
		super.initiate();
		if (getItem().getKeyInt()<0) {
			getItem().set("CALENDARIO", new CalendarDef().getXmlValues());
		}
		
	}

	public boolean validate() throws ISPACException {
		
		boolean ret = super.validate();
		
		if (ret) {
		
			ValidationError error = null;
	
			String nombre = (String)getProperty("NOMBRE");
			if(StringUtils.isBlank(nombre)) {
				
				error = new ValidationError("property(NOMBRE)", "errors.required", new String[]{Messages.getString(mContext.getAppLanguage(), "form.calendar.propertyLabel.name")});
				addError(error);
				return false;
			}
			else {
				IInvesflowAPI invesFlowAPI = mContext.getAPI();
				ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
				
				// Bloqueo de la tabla
				catalogAPI.setCTEntitiesForUpdate(ICatalogAPI.ENTITY_SPAC_CALENDARIOS, "");
				
				// Nombre único de calendario
				IItemCollection itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_SPAC_CALENDARIOS, " WHERE NOMBRE = '" + DBUtil.replaceQuotes(nombre) + "' AND ID !=" + getString("ID"));
				if (itemcol.next()) {	
					
					addError(new ValidationError("property(NOMBRE)", "error.calendar.nameDuplicated", new String[]{nombre}));	
					return false;
				}
			}
			
			return true;
		}
		else {
			return false;
		}	
	}

}