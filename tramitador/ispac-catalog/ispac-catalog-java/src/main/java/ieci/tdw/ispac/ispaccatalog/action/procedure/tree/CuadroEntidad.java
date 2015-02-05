package ieci.tdw.ispac.ispaccatalog.action.procedure.tree;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISessionAPI;
import ieci.tdw.ispac.api.ITemplateAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.messages.Messages;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.bean.TreeModel;
import ieci.tdw.ispac.ispaclib.bean.TreeModelItem;
import ieci.tdw.ispac.ispaclib.bean.TreeModelListener;
import ieci.tdw.ispac.ispaclib.dao.procedure.PNodoDAO;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;


public class CuadroEntidad implements TreeModel  {

	private final static Logger logger = Logger.getLogger(CuadroEntidad.class); 
	
	int entityId = -1;
	int regId = -1;
	ISessionAPI sessionApi = null;
	boolean showExtendedInfo = false;
	String language = null;
	
	public CuadroEntidad (int entityId, int regId, ISessionAPI sessionApi, String language){
		this.entityId = entityId;
		this.regId = regId;
		this.sessionApi = sessionApi;
		this.language = language;
	}
	
	public Collection getRootNodes() {
		List ret = null;
		try {
			IItem item = sessionApi.getAPI().getCatalogAPI().
				getCTEntity(entityId, regId);
			ret = new ArrayList();
			if (item!=null){
				if (entityId == ICatalogAPI.ENTITY_P_SUBPROCEDURE) {
					ret.add(new ElementoCuadro(ElementoCuadro.TYPE_SUBPROCEDURE, new ItemBean(item), language));
				} else {
					ret.add(new ElementoCuadro(ElementoCuadro.TYPE_PROCEDURE, new ItemBean(item), language));
				}
			}
		} catch (ISPACException e) {
			logger.error("Error al obtener el procedimiento", e);
		}
		//solo hay un root node=> el procedimiento del cuadro
		return ret;
	}

	public TreeModelItem getParentItem(TreeModelItem item) {
		
		ElementoCuadro parent = null;
		
		if (item != null) {
			parent = ((ElementoCuadro)item).getParentElement();
		}
		
		return parent;
	}
    
	public List getListStagesPcd(int idProcedimiento) throws ISPACException
    {
        IItemCollection itemcol;
        try {
            itemcol = sessionApi.getAPI().getCatalogAPI().queryCTEntities(ICatalogAPI.ENTITY_P_STAGE, "WHERE ID_PCD = " + idProcedimiento + " ORDER BY ID " );
            return CollectionBean.getBeanList(itemcol);

        } catch (ISPACException e) {
           throw new ISPACException(" CuadroEntidad: getListStagePcd()", e);
        }
    }

	public List getSincNodes(int pcdId) throws ISPACException {
		try {
        	IProcedureAPI procedureAPI = sessionApi.getAPI().getProcedureAPI();
        	IItemCollection itemcol = procedureAPI.getSyncNodes(pcdId);
            return CollectionBean.getBeanList(itemcol);

		} catch (ISPACException e) {
			throw new ISPACException(" CuadroEntidad: getSincNodes()", e);
		}
	}

	public List getNodeInputFlows(int nodeId) throws ISPACException {
        IItemCollection itemcol;
        try {
        	IProcedureAPI procedureAPI = sessionApi.getAPI().getProcedureAPI();
            itemcol = procedureAPI.getNodeInputFlows(nodeId);
            return CollectionBean.getBeanList(itemcol);

        } catch (ISPACException e) {
           throw new ISPACException(" CuadroEntidad: getNodeInputFlows()", e);
        }
	}
	
	public List getNodeOutputFlows(int nodeId) throws ISPACException {
        IItemCollection itemcol;
        try {
        	IProcedureAPI procedureAPI = sessionApi.getAPI().getProcedureAPI();
            itemcol = procedureAPI.getNodeOutputFlows(nodeId);
            return CollectionBean.getBeanList(itemcol);

        } catch (ISPACException e) {
           throw new ISPACException(" CuadroEntidad: getNodeOutputFlows()", e);
        }
	}
	
	public List getListTramitesStage(int idFase) throws ISPACException
    {
        IItemCollection itemcol;
        try {
        	itemcol = sessionApi.getAPI().getCatalogAPI().queryCTEntities(ICatalogAPI.ENTITY_P_TASK, "WHERE ID_FASE = " + idFase +" ORDER BY ORDEN");
            return CollectionBean.getBeanList(itemcol);

        } catch (ISPACException e) {
           throw new ISPACException(" CuadroEntidad: getListTramitesStagePcd()", e);
        }
    }
	
	public List getListTiposDocumentalesTask(int idTramite) throws ISPACException{
		IInvesflowAPI invesFlowAPI = sessionApi.getAPI();
		IItemCollection tiposDocumentales =  invesFlowAPI.getProcedureAPI().
			getTaskTpDoc(idTramite);
		return CollectionBean.getBeanList(tiposDocumentales);
	}

	public List getListTiposDocumentalesStage(int pcdIdStage) throws ISPACException{
		IInvesflowAPI invesFlowAPI = sessionApi.getAPI();
		IItemCollection tiposDocumentales =  invesFlowAPI.getProcedureAPI().
			getStageTpDoc(pcdIdStage);
		return CollectionBean.getBeanList(tiposDocumentales);
	}

	public List getListTemplateTpDoc(int idTipoDocumental, int proc) throws ISPACException{
		IInvesflowAPI invesFlowAPI = sessionApi.getAPI();
		IItemCollection templatesTpDoc = invesFlowAPI.getProcedureAPI().
			getProcTemplates(idTipoDocumental, proc);
		
		ITemplateAPI templateAPI= invesFlowAPI.getTemplateAPI();
		List listaPlantillas = CollectionBean.getBeanList(templatesTpDoc);

		// Se pone la propiedad isGeneric para saber si la plantilla es genérica o específica
		for (Iterator iter = listaPlantillas.iterator(); iter.hasNext();) {
			 ItemBean element = (ItemBean) iter.next();
			 int id = element.getItem().getInt("ID");
			 element.setProperty("isEspecific", new Boolean(templateAPI.isProcedureTemplate(id)));	 
		}
		return listaPlantillas;
	}

	public Collection getChilds(String language, TreeModelItem item) {
		//en funcion del tipo de elemento devolver sus hijos
		//procedimiento => fases
		//fases => Tramites
		//tramites => tipos documentales
		//tipos documentales => plantillas
		
        List children = new ArrayList();
        
        try{
	        ElementoCuadro modelItem = (ElementoCuadro)item;
	        
	        if (modelItem.isEntityProcedimiento()) {
	        	
	        	// Fases
	        	List objsHijos = getListStagesPcd(Integer.parseInt(
	        			item.getItemId().toString()));
	        	addElementosCuadro(children, modelItem, objsHijos, 
	        			ElementoCuadro.TYPE_STAGE, language);

	        	// Nodos de sincronización
	        	if (isShowExtendedInfo()) {
		        	objsHijos = getSincNodes(Integer.parseInt(
		        			item.getItemId().toString()));
		        	addElementosCuadro(children, modelItem, objsHijos, 
		        			ElementoCuadro.TYPE_SINC_NODE, language);
	        	}

	        } else if (modelItem.isEntitySubProceso()) {
	        	
	        	// Actividades
	        	List objsHijos = getListStagesPcd(Integer.parseInt(
	        			item.getItemId().toString()));
	        	addElementosCuadro(children, modelItem, objsHijos, 
	        			ElementoCuadro.TYPE_ACTIVITY, language);

	        	// Nodos de sincronización
	        	if (isShowExtendedInfo()) {
		        	objsHijos = getSincNodes(Integer.parseInt(
		        			item.getItemId().toString()));
		        	addElementosCuadro(children, modelItem, objsHijos, 
		        			ElementoCuadro.TYPE_SINC_NODE, language);
	        	}

	        } else if (modelItem.isEntityFaseProcedimiento()
	        		|| modelItem.isEntityActividad()) {
	        	
	        	if (isShowExtendedInfo()) {
	        		
		        	// Flujo de entrada
		            children.add(createElementoCuadro(
		            		ElementoCuadro.TYPE_STAGE_INPUT_FLOWS, -1,
		            		Messages.getString(language, "procedure.tree.inputflows"), 
		            		modelItem, language));
	
		        	// Flujo de salida
		            children.add(createElementoCuadro(
		            		ElementoCuadro.TYPE_STAGE_OUTPUT_FLOWS, -2,
		            		Messages.getString(language, "procedure.tree.outputflows"), 
		            		modelItem, language));
	        	}

	        	//las fase tiene hijos tramite simples
	        	List objsHijos = getListTramitesStage(Integer.parseInt(
	        			item.getItemId().toString()));
            	for (int i = 0; i < objsHijos.size(); i++) {
            		ItemBean task = (ItemBean) objsHijos.get(i);
                    children.add(createElementoCuadro(
                    		StringUtils.isBlank(task.getString("ID_PCD_SUB")) 
                    			? ElementoCuadro.TYPE_TASK 
                    			: ElementoCuadro.TYPE_TASK_COMPLEX, 
                    		(ItemBean) objsHijos.get(i), 
                    		modelItem, language));
                }
	        	
	        	//las fase tiene hijos tipo de documento
        		objsHijos = getListTiposDocumentalesStage(Integer.parseInt(
	        			item.getItemId().toString()));
	        	addElementosCuadro(children, modelItem, objsHijos, 
	        			ElementoCuadro.TYPE_STAGEDOCTYPE, language);
	        	
	        	
	        } else if (modelItem.isEntityFlujosEntradaFase()) {
	        	
	        	int stageId = Integer.parseInt(modelItem.getParentElement()
	        			.getItemId().toString());
	        	addFlujos(children, modelItem, getNodeInputFlows(stageId), language);
	        	
	        } else if (modelItem.isEntityFlujosSalidaFase()) {
	        	
	        	int stageId = Integer.parseInt(modelItem.getParentElement()
	        			.getItemId().toString());
	        	addFlujos(children, modelItem, getNodeOutputFlows(stageId), language);
	        	
        	} else if (modelItem.isEntityTramiteFase() || modelItem.isEntityTramiteComplejo()){
        		
        		//el tramite tiene los tipos documentales
        		List objsHijos = getListTiposDocumentalesTask(Integer.parseInt(
        				modelItem.getProperty("ID_CTTRAMITE")));
        		addElementosCuadro(children, modelItem, objsHijos, 
        				ElementoCuadro.TYPE_DOCTYPE, language);
        		
//        		// Subproceso
//        		int subPcdId = modelItem.getItem().getItem().getInt("ID_PCD_SUB");
//        		if (subPcdId > 0) {
//        			children.add(createElementoCuadro(
//        					ElementoCuadro.TYPE_SUBPROCEDURE, 
//        					new ItemBean(sessionApi.getAPI().getProcedure(subPcdId)), 
//                    		modelItem));
//        		}
         	
        	}else if (modelItem.isEntityTipoDocFase()){

        		Collection rootNodes = getRootNodes();
        		ElementoCuadro proc = (ElementoCuadro)rootNodes.iterator().next();
        		String sidProc =proc.getRegId();
        		int idProc = Integer.parseInt(sidProc);
        		List objsHijos = getListTemplateTpDoc(Integer.parseInt(
        				item.getItemId().toString()),idProc);
        		addElementosCuadro(children, modelItem, objsHijos,
        				ElementoCuadro.TYPE_TEMPLATESTAGETYPEDOC, language);
        	
        	} 
        	else if (modelItem.isEntityTipoDocTramite()){

        		Collection rootNodes = getRootNodes();
        		ElementoCuadro proc = (ElementoCuadro)rootNodes.iterator().next();
        		String sidProc =proc.getRegId();
        		int idProc = Integer.parseInt(sidProc);
        		List objsHijos = getListTemplateTpDoc(Integer.parseInt(
        				item.getItemId().toString()),idProc);
        		addElementosCuadro(children, modelItem, objsHijos,
        				ElementoCuadro.TYPE_TEMPLATE, language);
        	
        	} else if (modelItem.isEntityNodoSincronizacion()) {
        	
	        	// Flujo de entrada
	            children.add(createElementoCuadro(
	            		ElementoCuadro.TYPE_SINC_NODE_INPUT_FLOWS, -1,
	            		Messages.getString(language, "procedure.tree.inputflows"), 
	            		modelItem, language));

	        	// Flujo de salida
	            children.add(createElementoCuadro(
	            		ElementoCuadro.TYPE_SINC_NODE_OUTPUT_FLOWS, -2,
	            		Messages.getString(language, "procedure.tree.outputflows"), 
	            		modelItem, language));

	        } else if (modelItem.isEntityFlujosEntradaNodoSinc()) {
	        	
	        	int sincNodeId = Integer.parseInt(modelItem.getParentElement()
	        			.getItemId().toString());
	        	addFlujos(children, modelItem, getNodeInputFlows(sincNodeId), language);
	        	
	        } else if (modelItem.isEntityFlujosSalidaNodoSinc()) {
	        	
	        	int sincNodeId = Integer.parseInt(modelItem.getParentElement()
	        			.getItemId().toString());
	        	addFlujos(children, modelItem, getNodeOutputFlows(sincNodeId), language);
	        }
	        
        }catch (Exception e) {
			logger.error(e);
		}
        
        return children;
	}

	public void addTreeModelListener(TreeModelListener listener) {}

	public void removeTreeModelListener(TreeModelListener listener) {}
	
	private static void addElementosCuadro(List children, ElementoCuadro parent, 
			List beans, int type, String language) {
    	for (int i = 0; i < beans.size(); i++) {
            children.add(createElementoCuadro(type, (ItemBean) beans.get(i), 
            		parent, language));
        }
	}

	private static void addFlujos(List children, ElementoCuadro parent, 
			List beans, String language) throws ISPACException {
		
		ItemBean bean;
		int nodeType;
		int elemType;
		
    	for (int i = 0; i < beans.size(); i++) {
    		
    		elemType = 0;
    		
    		// Información del flujo
    		bean = (ItemBean) beans.get(i);
    		
    		// Tipo de nodo relacionado
    		nodeType = bean.getItem().getInt("NODE:TIPO");
    		
    		if (nodeType == PNodoDAO.NODE_OBJ_STAGE) { // Fase
    			
    			if ( (parent.getType() == ElementoCuadro.TYPE_SINC_NODE_INPUT_FLOWS)
    					|| (parent.getType() == ElementoCuadro.TYPE_STAGE_INPUT_FLOWS)) {
    				elemType = ElementoCuadro.TYPE_STAGE_INPUT_FLOW;
    			} else if ( (parent.getType() == ElementoCuadro.TYPE_SINC_NODE_OUTPUT_FLOWS)
    					|| (parent.getType() == ElementoCuadro.TYPE_STAGE_OUTPUT_FLOWS)) {
    				elemType = ElementoCuadro.TYPE_STAGE_OUTPUT_FLOW;
    			}
    			
    		} else if (nodeType == PNodoDAO.NODE_OBJ_SYNCNODE) { // Nodo de sincronización
    		
    			if ( (parent.getType() == ElementoCuadro.TYPE_SINC_NODE_INPUT_FLOWS)
    					|| (parent.getType() == ElementoCuadro.TYPE_STAGE_INPUT_FLOWS)) {
    				elemType = ElementoCuadro.TYPE_SINC_NODE_INPUT_FLOW;
    			} else if ( (parent.getType() == ElementoCuadro.TYPE_SINC_NODE_OUTPUT_FLOWS)
    					|| (parent.getType() == ElementoCuadro.TYPE_STAGE_OUTPUT_FLOWS)) {
    				elemType = ElementoCuadro.TYPE_SINC_NODE_OUTPUT_FLOW;
    			}
    		}
    		
            children.add(createElementoCuadro(elemType, bean, parent, language));
        }
	}

	private static ElementoCuadro createElementoCuadro(int type, int id, 
			String name, ElementoCuadro parent, String language) throws ISPACException {
		
		// Crear el ItemBean
    	ItemBean bean = new ItemBean();
    	bean.setProperty("ID", String.valueOf(id));
    	bean.setProperty("NOMBRE", name);
    	
    	// Crear el elemento del cuadro
    	ElementoCuadro elemCuadro = new ElementoCuadro(type, bean, language);
    	elemCuadro.setParentElement(parent);
    	
        return elemCuadro;
	}

	private static ElementoCuadro createElementoCuadro(int type, ItemBean bean, 
			ElementoCuadro parent, String language) {
		
    	// Crear el elemento del cuadro
    	ElementoCuadro elemCuadro = new ElementoCuadro(type, bean, language);
    	elemCuadro.setParentElement(parent);
    	
        return elemCuadro;
	}

	public boolean isShowExtendedInfo() {
		return showExtendedInfo;
	}

	public void setShowExtendedInfo(boolean showExtendedInfo) {
		this.showExtendedInfo = showExtendedInfo;
	}

}
