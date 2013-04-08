package ieci.tdw.ispac.ispacmgr.app;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IRegisterAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.app.GenericEntityListEntityApp;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.item.CompositeItem;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterInfo;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.sicres.RegisterHelper;

public class RegistroESEntityApp extends GenericEntityListEntityApp {

	private static final long serialVersionUID = 6885735345454554762L;


	public RegistroESEntityApp(ClientContext context) {
		super(context);
	}
	
	public void initiate() throws ISPACException {
		super.initiate();
		//Añadimos como propiedad extra el identificador del tramite instanciado para enlazarlo
		String idTramite = getString(mPrefixMainEntity+":ID_TRAMITE"); 
		if (StringUtils.isNotEmpty(idTramite)){
			IEntitiesAPI entitiesAPI = mContext.getAPI().getEntitiesAPI();
			IItemCollection itemcol = entitiesAPI.queryEntities(SpacEntities.SPAC_DT_TRAMITES, "WHERE ID_TRAM_EXP = "+Integer.parseInt(idTramite));
			if (itemcol.next()){
				setProperty("KEY", ""+itemcol.value().getKey());
			}
		}
	}


	public void store() throws ISPACException {
		IItem itemRegES = ((CompositeItem)mitem).getItemWithPrefix(mPrefixMainEntity); 
		if (StringUtils.isNotEmpty(itemRegES.getString("ID_TRAMITE"))){  
			String registerNumber = itemRegES.getString("NREG");
			String registerType = itemRegES.getString("TP_REG");
			int taskId = itemRegES.getInt("ID_TRAMITE");
			
			//Si el apunte de registro no tiene documenots no se vincula con el tramite
			int anexos = RegisterHelper.attachAnnexes(mContext, registerNumber, registerType, itemRegES.getDate("FREG"),itemRegES.getString("ID_INTERESADO"),itemRegES.getString("INTERESADO"),0, taskId);
			if (anexos == 0){
				itemRegES.set("ID_TRAMITE", (Object)null);
			}
			RegisterHelper.insertParticipants(mContext,registerNumber, registerType, msExpedient, true);
		}
		
		IRegisterAPI registerAPI = mContext.getAPI().getRegisterAPI();
		//Se vincula el expediente al apunte de registro
		registerAPI.linkExpedient(new RegisterInfo(null, this.getString(mPrefixMainEntity+":NREG"), null, getString(mPrefixMainEntity+":TP_REG")), msExpedient);

		super.store();
	}
}