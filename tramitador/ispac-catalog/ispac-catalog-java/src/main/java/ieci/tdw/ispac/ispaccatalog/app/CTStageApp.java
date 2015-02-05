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
import ieci.tdw.ispac.ispaclib.utils.DateUtil;

import java.util.Iterator;

/**
 * EntityApp para la información de una fase del catálogo de fases.
 *
 */
public class CTStageApp extends SimpleEntityApp {

	private static final long serialVersionUID = 1L;
	
	public CTStageApp(ClientContext context) {
		super(context);
	}
	
	public void initiate() throws ISPACException {

		super.initiate();
		
		if (getString("ID")==null){
			setProperty("AUTOR", mContext.getUser().getName());
			setProperty("FALTA", DateUtil.getToday());
		}
	}
	
	public void store() throws ISPACException {
		
		IInvesflowAPI invesFlowAPI = mContext.getAPI(); 
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		if (getString("ORDEN") == null) {

			// Bloqueo de las fases para obtener el último orden
			//IItemCollection itemcol = catalogAPI.queryCTEntitiesForUpdate(ICatalogAPI.ENTITY_CT_STAGE, " ORDER BY ORDEN DESC ");
			IItemCollection itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_STAGE, " ORDER BY ORDEN DESC ");
			
			int nextOrden = 1;
			if (itemcol != null) {
				
				for (Iterator iter = itemcol.iterator(); iter.hasNext();) {
					
					IItem element = (IItem) iter.next();
					String orden = element.getString("ORDEN");
					if (orden != null) {
						
						nextOrden = Integer.parseInt(element.getString("ORDEN"))+1;
						break;
					}
				}
			}
			
			setProperty("ORDEN", new Integer(nextOrden));
		}
		
		super.store();
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
			catalogAPI.setCTEntitiesForUpdate(ICatalogAPI.ENTITY_CT_STAGE, "");
			
			// Nombre único de fase
			String nombre = getString("NOMBRE");
			IItemCollection itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_STAGE, " WHERE NOMBRE = '" + DBUtil.replaceQuotes(nombre) + "' AND ID != " + getString("ID"));
			if (itemcol.next()) {
				
				addError(new ValidationError("property(NOMBRE)", "error.stage.nameDuplicated", new String[] {nombre}));
				return false;
			}
			
			return true;
		} 
		else {
			return false;
		}
	}

}