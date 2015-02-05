package ieci.tdw.ispac.ispaccatalog.app;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.app.ExtendedEntityApp;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.procedure.PNodoDAO;
import ieci.tdw.ispac.ispaclib.item.CompositeItem;

public class PFlowApp extends ExtendedEntityApp {

	public PFlowApp(ClientContext context) {
		super(context);
	}

	public IItem processItem(IItem item) throws ISPACException {
		CompositeItem composite = new CompositeItem("SPAC_P_FLUJOS:ID");
		composite.addItem(item, "SPAC_P_FLUJOS:");

		return composite;
	}

	public boolean validate() throws ISPACException {
		return super.validate();
	}

	public void initiate() throws ISPACException {

		// API de invesFlow
		IInvesflowAPI invesflowAPI = mContext.getAPI();

		// Información del nodo origen
		int origNodeId = getItem().getInt("SPAC_P_FLUJOS:ID_ORIGEN");
		IItem node = invesflowAPI.getProcedureNode(origNodeId);
		((CompositeItem) mitem).addItem(node, "ONODE:");
		
		// Si el nodo origen es de tipo fase, añadir su información 
		if (node.getInt("TIPO") == PNodoDAO.NODE_OBJ_STAGE) {
			IItem item = invesflowAPI.getProcedureStage(origNodeId);
			((CompositeItem) mitem).addItem(item, "OSTAGE:");
		}

		// Información del nodo destino
		int destNodeId = getItem().getInt("SPAC_P_FLUJOS:ID_DESTINO");
		node = invesflowAPI.getProcedureNode(destNodeId);
		((CompositeItem) mitem).addItem(node, "DNODE:");

		// Si el nodo destino es de tipo fase, añadir su información 
		if (node.getInt("TIPO") == PNodoDAO.NODE_OBJ_STAGE) {
			IItem item = invesflowAPI.getProcedureStage(destNodeId);
			((CompositeItem) mitem).addItem(item, "DSTAGE:");
		}

		super.initiate();
	}

	protected IItem getNode(int nodeId) throws ISPACException {
		
		// API de invesFlow
		IInvesflowAPI invesflowAPI = mContext.getAPI();

		// Información del nodo
		IItem node = invesflowAPI.getProcedureNode(nodeId);
		
		// Si el nodo es de tipo fase, obtener su información 
		if (node.getInt("TIPO") == PNodoDAO.NODE_OBJ_STAGE) {
			return invesflowAPI.getProcedureStage(nodeId);
		}
		
		return null;
	}
	
	public void store() throws ISPACException {

		try {
			((CompositeItem) mitem).getItemWithPrefix("SPAC_P_FLUJOS:").
				store(mContext);
			super.store();
		} catch (Exception ie) {
			throw new ISPACException("Error en EntityBean:store()", ie);
		}
	}
}
