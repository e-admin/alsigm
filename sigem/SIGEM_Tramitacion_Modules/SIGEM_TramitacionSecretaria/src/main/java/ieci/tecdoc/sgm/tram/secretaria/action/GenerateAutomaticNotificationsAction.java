package ieci.tecdoc.sgm.tram.secretaria.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ITemplateAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.dao.cat.TemplateDAO;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispacmgr.action.BaseAction;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;



public abstract class GenerateAutomaticNotificationsAction extends BaseAction {

	/**
	 * Devuelve el id del tipo documental,
	 * en caso de que no tenga el id, sino de que tenga el código deberá obtener el id y devolverlo
	 * @return
	 */
	public abstract String getIdTipoDocumental(IClientContext cct) throws ISPACException;
	/**
	 * Devuelve el id de la plantilla
	 * en caso de que no tenga el id, sino de que tenga el código deberá obtener el id y devolverlo
	 * @return
	 */
	public String getIdPlantilla(IClientContext cct) {
		String varCodPlantilla="";
		try {
			String id="";

			String numexp=cct.getStateContext().getNumexp();
			varCodPlantilla=getVarCodPlantilla(cct,numexp);

			String cod_plantilla= ConfigurationMgr.getVarGlobal(cct,varCodPlantilla );
			ITemplateAPI templateAPI = cct.getAPI().getTemplateAPI();
			TemplateDAO templateDAO=templateAPI.getTemplateByCode(cod_plantilla);

			if(templateDAO!=null){
				id=templateDAO.getKeyInt()+"";
			}
			else{
				if(logger.isDebugEnabled()){
					logger.debug("GenerateAutomaticNotificationsAction:No se ha obtenido la plantilla cuyo código es"+varCodPlantilla);
				}
			}
			return id;
		} catch (ISPACException e) {
			if(logger.isDebugEnabled()){
				logger.debug("Error al obtener el valor de la variable de sistema" +
						varCodPlantilla);

			}
			return "";
		}


	}

	/*Devuelve el nombre de la variable de sistema que contiene el cod de la plantilla*/
	public  abstract String getVarCodPlantilla (IClientContext cct, String numexp)throws ISPACException;



	/**
	 * Devuelve una lista de los participantes para los que hay que generar documentación
	 * @return
	 */
	public abstract String getQuery(String numexp, int taskId);

	/**
	 * Devuelve el nombre del action para que al paginar en el resultado utilice el action correcto
	 * @return
	 */
	public abstract String getAction();//"/generateAutomaticNotifications.do"

	/** Logger de la clase. */
    private static final Logger logger =
    	Logger.getLogger(GenerateAutomaticNotificationsAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

		 ClientContext cct = session.getClientContext();
		 IInvesflowAPI invesFlowAPI = session.getAPI();
		 IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		 IGenDocAPI genDocAPI = invesFlowAPI.getGenDocAPI();
		 IItem entityDocument=null;
		 IItem entityTemplate = null;
		 // Se obtiene el estado de tramitación
		 IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		 IState currentState = managerAPI.currentState(getStateticket(request));
		 int taskPcdId=currentState.getTaskPcdId();
		 int stagePcdId=currentState.getStagePcdId();
		 int pTramite= currentState.getTaskId();
		 int pFase=currentState.getStageId();


		//Obtener la lista de participantes
		IItemCollection itemcol= entitiesAPI.queryEntities(SpacEntities.SPAC_DT_INTERVINIENTES,
								getQuery(cct.getStateContext().getNumexp(), cct.getStateContext().getTaskId()));
		 List registros=CollectionBean.getBeanList(itemcol);
		 List noEnviados=new ArrayList();


		//Generar el documento a través de la plantilla para cada uno de los registros
		Object connectorSession=null;
		connectorSession = genDocAPI.createConnectorSession();


	    ItemBean itemBean = null;
	   Iterator itr = registros.iterator();
	   String id_tp_doc=getIdTipoDocumental(cct);
	   String id_plant=getIdPlantilla(cct);
	   if(StringUtils.isBlank(id_tp_doc) || StringUtils.isBlank(id_plant)){
		    request.setAttribute("error", "true");
	   }
	   else{
	    while(itr.hasNext()){
	    	// Abrir transacción para que no se pueda generar un documento sin fichero
		    cct.beginTX();
			boolean bCommit = false;
			try {
				itemBean = (ItemBean) itr.next();
				if(logger.isDebugEnabled()){
					logger.debug("Se procederá a crear el documento");
				}

				int regId=Integer.parseInt(itemBean.getString("ID"));
				String idExt=itemBean.getString("ID_EXT");
				String destinoTipo= itemBean.getString("DESTINO_TIPO");
				//Si no es validado
				if(StringUtils.isBlank(idExt)){
					idExt="0";
					destinoTipo="N";
				}


				//Crea el registro en la entidad
				entityDocument = genDocAPI.createTaskDocument(currentState.getTaskId(),
															  Integer.parseInt(id_tp_doc),
															  SpacEntities.SPAC_DT_INTERVINIENTES,
														  regId);

				entityDocument.set("ID_FASE", pFase);
				entityDocument.set("ID_TRAMITE", pTramite);
				entityDocument.set("ID_FASE_PCD", stagePcdId);
				entityDocument.set("ID_TRAMITE_PCD", taskPcdId);
				entityDocument.set("DESCRIPCION", "NOT-"+itemBean.getString("NOMBRE"));
				entityDocument.set("DESTINO_ID",idExt );
				entityDocument.set("DESTINO_TIPO" , destinoTipo);
				entityDocument.set("DESTINO" , itemBean.getString("NOMBRE"));

				//Generamos el documento a partir de la plantilla
				entityTemplate = genDocAPI.attachTaskTemplate(connectorSession,
															cct.getStateContext().getTaskId(),
															 entityDocument.getKeyInt("ID"),
															 Integer.parseInt(id_plant),
															 SpacEntities.SPAC_DT_INTERVINIENTES,
															 regId);
				// Referencia al fichero del documento en el gestor documental
				String docref = entityTemplate.getString("INFOPAG");
				String sMimetype = genDocAPI.getMimeType(connectorSession, docref);
				entityTemplate.set("EXTENSION",
						MimetypeMapping.getExtension(sMimetype));
				entityDocument.store(cct);
				entityTemplate.store(cct);

				// Si todo ha sido correcto se hace commit de la transacción
				bCommit = true;
			}catch (Throwable e){

				logger.error("Error al crear el documento para el registro con id  "+itemBean.getString("ID") + e);
				noEnviados.add(itemBean);
				itr.remove();
			}finally{

				cct.endTX(bCommit);

			}
		}
		if (connectorSession != null) {
			genDocAPI.closeConnectorSession(connectorSession);
		}
		if(!registros.isEmpty()){
			request.setAttribute("notificacionesGeneradas", registros);
		}
		if(!noEnviados.isEmpty()){
			request.setAttribute("notificacionesNOgeneradas", noEnviados);
		}
	   }
	   request.setAttribute("ActionDestino", getAction());
		return mapping.findForward("summary");
	}





}