package ieci.tdw.ispac.ispaccatalog.app;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.messages.Messages;
import ieci.tdw.ispac.ispaclib.app.SimpleEntityApp;
import ieci.tdw.ispac.ispaclib.bean.ValidationError;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

public class CTHelpApp extends SimpleEntityApp {

	
	static int ID_SEPARADOR_CATALOGO=30;
	static int ID_SEPARADOR_TRAMITADOR=20;
	public CTHelpApp(ClientContext context) {
		super(context);
	}

	public void initiate() throws ISPACException {
		super.initiate();		
	}

	public boolean validate() throws ISPACException {
		
		boolean ret = super.validate();
		
		if (ret) {
		
			ValidationError error = null;
	
			String nombre = (String)getProperty("NOMBRE");
			if(StringUtils.isBlank(nombre)){
				
				error = new ValidationError("property(NOMBRE)", "errors.required", new String[]{Messages.getString(mContext.getAppLanguage(),"form.help.propertyLabel.name")});
				addError(error);
				return false;
			}
			String contenido = (String)getProperty("CONTENIDO");
			if(StringUtils.isBlank(contenido)){
				
				error = new ValidationError("property(CONTENIDO)", "errors.required", new String[]{Messages.getString(mContext.getAppLanguage(),"form.help.propertyLabel.contenido")});
				addError(error);
				return false;
			}
			int tipo_obj=((Integer)getProperty("TIPO_OBJ")).intValue();
			if(tipo_obj==ID_SEPARADOR_TRAMITADOR || tipo_obj==ID_SEPARADOR_CATALOGO){
				error = new ValidationError("property(TIPO_OBJ)", "errors.invalid", new String[]{Messages.getString(mContext.getAppLanguage(),"form.help.propertyLabel.tipo")});
				addError(error);
				return false;
			}
			IInvesflowAPI invesFlowAPI = mContext.getAPI();
			ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
			
			int id_obj=-1;
			if(StringUtils.isNotEmpty(mitem.getString("ID_OBJ"))){
				id_obj=((Integer)getProperty("ID_OBJ")).intValue();
			}
			String idioma=(String) getProperty("IDIOMA");
			String sql="WHERE IDIOMA ";
			if(idioma==null){
				sql+=" IS NULL ";
			}
			else{
				sql+=" = '" +DBUtil.replaceQuotes(idioma) +"'";
			}
			//Comprobamos que para un mismo id_Obj tp_Obj e idioma no exista ya una entrada
			int count = catalogAPI.countCTEntities(ICatalogAPI.ENTITY_CT_HELPS, sql+"  AND TIPO_OBJ="+tipo_obj+" AND ID_OBJ="+id_obj+ " AND ID != " + getString("ID"));
			if (count > 0) {
				error = new ValidationError("property(NOMBRE)", "errors.help.duplicate");
				addError(error);
				return false;
			}
			else {
				
				
				// Bloqueo de la tabla
				catalogAPI.setCTEntitiesForUpdate(ICatalogAPI.ENTITY_CT_HELPS, "");
				
				// Nombre único de calendario
				IItemCollection itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_HELPS, " WHERE NOMBRE= '" + DBUtil.replaceQuotes(nombre) + "' AND ID != " + getString("ID"));
				if (itemcol.next()) {
					
					addError(new ValidationError("property(NOMBRE)", "error.help.nameDuplicated", new String[] {nombre}));
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
		
		
		if(StringUtils.isEmpty(mitem.getString("ID_OBJ"))){
			mitem.set("ID_OBJ", "-1");
		}
		
		valuesExtra.clear();
		super.store();
		
	}
	  
	
	
}