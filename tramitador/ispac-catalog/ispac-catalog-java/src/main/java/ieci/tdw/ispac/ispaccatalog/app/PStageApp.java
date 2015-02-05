package ieci.tdw.ispac.ispaccatalog.app;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;
import ieci.tdw.ispac.ispaclib.item.CompositeItem;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.List;

public class PStageApp extends DeadlineApp {

	public PStageApp(ClientContext context) {
		super(context);
	}

	public IItem processItem(IItem item) throws ISPACException {
		
	    CompositeItem composite = new CompositeItem("SPAC_P_FASES:ID");
		composite.addItem(item, "SPAC_P_FASES:");

		return composite;
	}

	public boolean validate() throws ISPACException {
	    return super.validate();
	}

	public void initiate() throws ISPACException {
		
		// Obtenermos el procedimiento asociado
		IItem item = getAsociatedEntity(ICatalogAPI.ENTITY_P_PROCEDURE);
		((CompositeItem) mitem).addItem(item, "SPAC_P_PCD:");

		//Obtener el nombre del responsable del LDAP
		setNameResp();

		//Obtener el plazo
		super.initiate();
    }

    /**
     * Obtiene la entidad asociada
     *
     * @param entityId identificador de la entidad asociada
     * @return EntityDAO
     */
    protected IItem getAsociatedEntity(int ctentityId)
    throws ISPACException
    {
        ICatalogAPI catalogAPI = mContext.getAPI().getCatalogAPI();

        // Si no tiene asociada aplicación se devuelve un Item vacío.
        if (getItem().get("SPAC_P_FASES:ID_PCD") == null)
            return catalogAPI.getCTEntity(ctentityId);

        int pcdid = getItem().getInt("SPAC_P_FASES:ID_PCD");

        try
        {
            IItemCollection collection =
            catalogAPI.queryCTEntities(ctentityId, "WHERE ID = " + pcdid);

			if (!collection.next())
			    throw new ISPACException(" PStageApp: No existe la aplicación");

			return (EntityDAO)collection.value();
        }
        catch (ISPACException e)
        {
            throw new ISPACException(" PStageApp: getAsociatedEntity()", e);
        }
    }

    public void setNameResp()
    throws ISPACException
    {
        String uid = getItem().getString("SPAC_P_FASES:ID_RESP");

		if (uid == null||uid.length()==0)
		{
		    getItem().set("SPAC_P_FASES:ID_RESP", "");
	    	setProperty("PRESP_NAME:NOMBRE", "");
		}
		else
		{
		    IResponsible responsible = mContext.getAPI().getRespManagerAPI().getResp(uid);
		    setProperty("PRESP_NAME:NOMBRE", responsible.get("RESPNAME"));
		}
    }

    public List getListTasksStage() throws ISPACException
    {
        ICatalogAPI catalogAPI = mContext.getAPI().getCatalogAPI();

        IItemCollection itemcol;
        try {
            itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_P_TASK, "WHERE ID_FASE = " + getItem().getString("SPAC_P_FASES:ID"));
            return CollectionBean.getBeanList(itemcol);

        } catch (ISPACException e) {
            throw new ISPACException(" PProcedureApp: getListTasksStage()", e);
        }
    }

	public void store() throws ISPACException {

		try {
			
			IItem stage = ((CompositeItem) mitem).getItemWithPrefix("SPAC_P_FASES:");
			stage.store(mContext);
			
			super.store();
			
			// Actualizar la información de la fase en el BPM
	        IItem node = mContext.getAPI().getProcedureNode(getItem().getKeyInt());
			String bpmNodeId = node.getString("ID_NODO_BPM");
			if (StringUtils.isNotBlank(bpmNodeId)) {
				mContext.getAPI().getBPMAPI().updateNodeResponsible(bpmNodeId, 
						getItem().getString("SPAC_P_FASES:ID_RESP"));
			}
			
		} catch (Exception ie) {
			throw new ISPACException("Error en EntityBean:store()", ie);
		}
	}
}
