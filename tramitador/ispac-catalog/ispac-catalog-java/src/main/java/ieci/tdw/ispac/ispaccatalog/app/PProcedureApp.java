package ieci.tdw.ispac.ispaccatalog.app;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.item.CompositeItem;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Date;

/**
 * EntityApp para la información de un procedimiento
 */
public class PProcedureApp extends DeadlineApp {

    public PProcedureApp(ClientContext context) {
        super(context);
    }

    public IItem processItem(IItem item) throws ISPACException {
	    CompositeItem composite = new CompositeItem("SPAC_P_PROCEDIMIENTOS:ID");
		composite.addItem(item, "SPAC_P_PROCEDIMIENTOS:");

		return composite;
	}

	public void initiate() throws ISPACException {
		
			//Obtener el nombre del responsable
            addPropName("SPAC_P_PROCEDIMIENTOS:ID_RESP",
            		"SPAC_P_PROCEDIMIENTOS:RESP_NOMBRE", "");
            
//			//Obtener la lista de fases
//            setProperty("listStagesPcd",getListStagesPcd());
            
			//Obtener el plazo
			super.initiate();
    }

	public boolean validate() throws ISPACException {
	    return super.validate();
	}

//    public List getListStagesPcd() throws ISPACException {
//    	
//        ICatalogAPI catalogAPI = mContext.getAPI().getCatalogAPI();
//
//        IItemCollection itemcol;
//        try {
//            itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_P_STAGE, 
//            		"WHERE ID_PCD = " + getItem().getString("SPAC_P_PROCEDIMIENTOS:ID"));
//            return CollectionBean.getBeanList(itemcol);
//
//        } catch (ISPACException e) {
//            throw new ISPACException(" PProcedureApp: getListStagePcd()", e);
//        }
//    }

    private void addPropName(String propId,String propName,String defaultName) 
    		throws ISPACException {
    	
        String uid = getItem().getString(propId);

        if (uid == null||uid.length()==0) {
            getItem().set(propId, "");
            setProperty(propName, defaultName);
            return;
        }

        try {
            IResponsible responsible = mContext.getAPI().getRespManagerAPI().getResp(uid);
            setProperty(propName, responsible.get("RESPNAME"));
        } catch (Exception e) {
            setProperty(propName, defaultName);
        }

    }

	public void store()	throws ISPACException {
		
		try {
			
			mitem.set("SPAC_P_PROCEDIMIENTOS:ID_UPD", mContext.getUser().getUID());
			mitem.set("SPAC_P_PROCEDIMIENTOS:NOMBRE_UPD", mContext.getUser().getName());
			mitem.set("SPAC_P_PROCEDIMIENTOS:TS_UPD", new Date());
			((CompositeItem) mitem).getItemWithPrefix("SPAC_P_PROCEDIMIENTOS:").store(mContext);
			
			super.store();
			
			// Actualizar la información del modelo de procesos en el BPM
			String bpmPcdId = mitem.getString("SPAC_P_PROCEDIMIENTOS:ID_PCD_BPM");
			if (StringUtils.isNotBlank(bpmPcdId)) {
				mContext.getAPI().getBPMAPI().updateProcessResponsible(
						bpmPcdId, getItem().getString("SPAC_P_PROCEDIMIENTOS:ID_RESP"));
			}
			
		} catch (Exception ie) {
			throw new ISPACException("Error en EntityBean:store()", ie);
		}
	}
}
