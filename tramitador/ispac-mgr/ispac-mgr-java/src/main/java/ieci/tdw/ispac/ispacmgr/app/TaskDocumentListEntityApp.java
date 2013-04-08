package ieci.tdw.ispac.ispacmgr.app;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.app.GenericMasterDetailEntityApp;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

public class TaskDocumentListEntityApp extends GenericMasterDetailEntityApp {
	
	public TaskDocumentListEntityApp(ClientContext context)
	{
		super(context);
	}
	
	public void initiate() throws ISPACException {
		
		super.initiate();
		
	    // Establecer el nombre del responsable para mostrarlo en el formulario
	    if (getProperty("SPAC_DT_TRAMITES:FECHA_CIERRE") == null) {
	    	setProperty("ID_RESP_NAME", TaskHelper.getNameResp(mContext));
	    	if ( StringUtils.isEmpty(mitem.getString("SPAC_DT_TRAMITES:UND_RESP")) ){
	    		setProperty("SPAC_DT_TRAMITES:UND_RESP", TaskHelper.getDptoResp(mContext));
	    	}
	    } 
	}
	
    public void store() throws ISPACException {
    	
        // Se establece la fecha de alarma si procede
        TaskHelper.setLimitDate(mContext, getItem());
        
	    super.store();
    }
    
}