package ieci.tdw.ispac.ispaccatalog.app;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.app.SimpleEntityApp;
import ieci.tdw.ispac.ispaclib.bean.ValidationError;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

public class CTApplicationApp extends SimpleEntityApp {

	public CTApplicationApp(ClientContext context)
	{
		super(context);
	}
	
	/**
	 * Valida la información del formulario.
	 * @throws ISPACException si ocurre algún error.
	 */
	public boolean validate() throws ISPACException {
		
		boolean ret = super.validate();
		
		if (ret) {
			
			IInvesflowAPI invesFlowAPI = mContext.getAPI();
			ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
			
			// Bloqueo de la tabla
			catalogAPI.setCTEntitiesForUpdate(ICatalogAPI.ENTITY_CT_APP, "");
			
			// Nombre único de formulario
			String nombre = getString("NOMBRE");
			IItemCollection itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_APP, " WHERE NOMBRE = '" + DBUtil.replaceQuotes(nombre) + "' AND ID != " + getString("ID"));
			if (itemcol.next()) {
				
				addError(new ValidationError("property(NOMBRE)", "error.form.nameDuplicated", new String[] {nombre}));
				return false;
			}
			
			return true;
		}
		else {
			return false;
		}
	}

	public void store() throws ISPACException {
		
		IItem item = getItem();
		
		if (StringUtils.isEmpty(item.getString("PAGINA"))) {
			
			String page = "/forms/"
						+ item.getString("ENT_PRINCIPAL_NOMBRE")
						+ "/" + item.getString("NOMBRE").replace(' ', '_')
						+ ".jsp";
			
			item.set("PAGINA", page);
		}
		
		super.store();		
	}
	
}