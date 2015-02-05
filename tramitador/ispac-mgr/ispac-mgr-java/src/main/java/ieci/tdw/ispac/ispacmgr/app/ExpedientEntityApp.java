package ieci.tdw.ispac.ispacmgr.app;

import ieci.tdw.ispac.api.IEntitiesAPI;

import ieci.tdw.ispac.api.IRegisterAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterInfo;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterType;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.sicres.RegisterHelper;

public class ExpedientEntityApp extends GenericSecondaryEntityApp {

	public ExpedientEntityApp(ClientContext context) {
		super(context);
	}

	private static final long serialVersionUID = -8646747207464586513L;

	@Override
	public void store() throws ISPACException {
		//Se comprueban si han habido cambios sobre el numero de registro para realizar las actuaciones oportunas 
        checkNumRegChange();
		
		super.store();
	}

	private void checkNumRegChange() throws ISPACException {
		IEntitiesAPI entitiesAPI = mContext.getAPI().getEntitiesAPI();
        IRegisterAPI registerAPI = mContext.getAPI().getRegisterAPI();

		//Comprobamos si la entidad SPAC_REGISTROS_ES esta vinculada al procedimiento
        boolean associatedRegESEntity = RegisterHelper.isAssocitedRegistrosESEntity(mContext, msExpedient); 
        
		IItem itemStored = entitiesAPI.getExpedient(msExpedient);
		IItem itemRegisterES = null;
		String registerNumber = mitem.getString("SPAC_EXPEDIENTES:NREG");
		//Si el numero de registro anterior y el nuevo son iguales no se hace nada 
		if (StringUtils.equalsNullEmpty(itemStored.getString("NREG"), registerNumber)){
			return;
		}
		if (associatedRegESEntity && StringUtils.isNotEmpty(itemStored.getString("NREG"))){
			//Si se ha producido un cambio en el numero de registro y no hay ningun documento asociado a este apunte de registro, se borra el apunte del registro anterior si es que lo hubiera
			if (entitiesAPI.countEntities(SpacEntities.SPAC_DT_DOCUMENTOS, "WHERE NUMEXP = '" + DBUtil.replaceQuotes(msExpedient) + "' AND NREG = '" + itemStored.getString("NREG") + "' AND TP_REG = '" + RegisterType.ENTRADA + "'") == 0){
				entitiesAPI.deleteEntities(SpacEntities.SPAC_REGISTROS_ES_NAME, "WHERE NUMEXP = '" + DBUtil.replaceQuotes(msExpedient)+ "' AND ORIGINO_EXPEDIENTE = 'SI'");
				//Se desvincula el expediente del apunte de registro
				registerAPI.unlinkExpedient(new RegisterInfo(null, itemStored.getString("NREG"), null, RegisterType.ENTRADA), msExpedient);				
			}
		}
		//Si el nuevo numero de registro esta vacio no se hace nada mas
		if (StringUtils.isEmpty(registerNumber)){ 
			return;
		}
		
		if (associatedRegESEntity){
			//Si no esta asociado ya el apunte de registro con el expediente, se realiza la asociacion
			IItemCollection itemcol = entitiesAPI.queryEntities(SpacEntities.SPAC_REGISTROS_ES_NAME, "WHERE NREG = '" + registerNumber + "' AND NUMEXP = '"+DBUtil.replaceQuotes(msExpedient)+"' AND TP_REG = '" + RegisterType.ENTRADA + "'");
			if (!itemcol.next()){
				itemRegisterES = entitiesAPI.createEntity(SpacEntities.SPAC_REGISTROS_ES_NAME, msExpedient);
				itemRegisterES.set("NREG", registerNumber);
				itemRegisterES.set("FREG", mitem.get("SPAC_EXPEDIENTES:FREG"));
				itemRegisterES.set("ASUNTO", mitem.get("SPAC_EXPEDIENTES:ASUNTO"));
				itemRegisterES.set("ID_INTERESADO", mitem.get("SPAC_EXPEDIENTES:IDTITULAR"));
				itemRegisterES.set("INTERESADO", mitem.get("SPAC_EXPEDIENTES:IDENTIDADTITULAR"));
				itemRegisterES.set("TP_REG", RegisterType.ENTRADA);
				itemRegisterES.set("ORIGINO_EXPEDIENTE", "SI");
				itemRegisterES.store(mContext);
	
				//Se vincula el expediente al apunte de registro
				registerAPI.linkExpedient(new RegisterInfo(null, itemStored.getString("NREG"), null, RegisterType.ENTRADA), msExpedient);
			}
		}
		
		//Se da de alta los interesados como participantes en el expediente menos el primero que se asocia como interesado principal
		RegisterHelper.insertParticipants(mContext, registerNumber,RegisterType.ENTRADA, msExpedient, false);	
		
		//Se incorporan a nivel de fase los documentos anexos del Registro de Entrada 
		RegisterHelper.attachAnnexes(mContext, registerNumber, RegisterType.ENTRADA, mitem.getDate("SPAC_EXPEDIENTES:FREG"), mitem.getString("SPAC_EXPEDIENTES:IDTITULAR"), mitem.getString("SPAC_EXPEDIENTES:IDENTIDADTITULAR"), mContext.getStateContext().getStageId(),0);
		
	}
}