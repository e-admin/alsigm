package ieci.tdw.ispac.designer.server;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.ISessionAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.graph.GInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.impl.SessionAPIFactory;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.designer.client.exceptions.DesignerException;
import ieci.tdw.ispac.designer.client.helper.Html;
import ieci.tdw.ispac.designer.client.helper.Util;
import ieci.tdw.ispac.designer.client.helper.varDefs;
import ieci.tdw.ispac.designer.client.objetos.CampoEntidad;
import ieci.tdw.ispac.designer.client.objetos.Condition;
import ieci.tdw.ispac.designer.client.objetos.ConditionSimple;
import ieci.tdw.ispac.designer.client.objetos.DrawFlow;
import ieci.tdw.ispac.designer.client.objetos.DrawObject;
import ieci.tdw.ispac.designer.client.objetos.Entidad;
import ieci.tdw.ispac.designer.client.objetos.Evento;
import ieci.tdw.ispac.designer.client.objetos.Instancia;
import ieci.tdw.ispac.designer.client.objetos.Operando;
import ieci.tdw.ispac.designer.client.objetos.Procedimiento;
import ieci.tdw.ispac.designer.client.objetos.Regla;
import ieci.tdw.ispac.designer.client.objetos.TramiteFase;
import ieci.tdw.ispac.designer.client.service.GwtIService;
import ieci.tdw.ispac.designer.server.utils.EventoId;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.entity.EntityResources;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;
import ieci.tdw.ispac.ispaclib.events.DescEventsBean;
import ieci.tdw.ispac.ispaclib.events.DescriptionsPEvents;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.LocaleHelper;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Implementación del servicio.
 *
 */
public class ImplGwtIService extends RemoteServiceServlet implements
		GwtIService {

	/**
	 * Serial version UID. 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(ImplGwtIService.class);
	
	
	public Procedimiento getProcedureBynIdProc(int nIdProc) throws DesignerException
	{
		
		try {
			ISessionAPI sessionAPI = getSessionAPI();
			IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
			IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();
		
			// Información del expediente
			IProcess proc= invesFlowAPI.getProcess(nIdProc);
			
		     String numexp=proc.getString("NUMEXP");
			
		    int pcdId= proc.getInt("ID_PCD");

			String pcdXml = procedureAPI.getProcedureXml(pcdId);
			Procedimiento procedimiento = createProcedimiento(pcdId, pcdXml);
			procedimiento.setIdProc(pcdId);

		    return getHitosByNumExp(numexp, procedimiento);
	
		}  catch (Throwable t) {
			logger.error("Error al obtener el procedimiento by numExp", t);
			throw new DesignerException(t.getLocalizedMessage());
		}
		
	}
	
	public Procedimiento getHitosByNumExp(String numExp , Procedimiento procedimiento) throws ISPACException
	{
		
		ISessionAPI sessionAPI= getSessionAPI();
		IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
		// Obtener los hitos del expediente
		IItemCollection hitos = invesFlowAPI.getMilestones(numExp);

		int idFase, idTramite, codHito;
		while (hitos.next()) {
			IItem hito = hitos.value();

			codHito = hito.getInt("HITO");
			if (hitoTratado(codHito)) {
				idFase = hito.getInt("ID_FASE");
				if (idFase != 0) {

					//Obtengo la fase que estamos tratando

					idTramite = hito.getInt("ID_TRAMITE");
					if (idTramite == 0) {
						//Si no se nos ha informado del tramite estamos ante un hito de comienzo de fase, cierre de fase, o retroceso de fase
						if (codHito == varDefs.MILESTONE_STAGE_END)
							procedimiento.setEstadoFase(idFase,
									varDefs.CERRADO);
						else if (codHito == varDefs.MILESTONE_STAGE_END_RELOCATED)
							procedimiento.setEstadoFase(idFase,
									varDefs.NO_INICIADO);
						else
							procedimiento.setEstadoFase(idFase,
									varDefs.INICIADO);
					} else {

						//Si esta informado tanto el tramite con el la fase estamos antes los estados de
						//Tramite iniciado, cerrado o borrado
						if (codHito == varDefs.MILESTONE_TASK_END) {
							procedimiento.setEstadoTramiteFase(idFase,
									idTramite, varDefs.CERRADO);

						}

						//Solo se puede producir este estado si es un tramite abierto
						else if (codHito == varDefs.MILESTONE_TASK_DELETE) {
							procedimiento.setEstadoTramiteFase(idFase,
									idTramite, varDefs.tramiteCancelado);

						}
						
						else {
							procedimiento.setEstadoTramiteFase(idFase,
									idTramite, varDefs.INICIADO);

						}

					}
				}
				else if(codHito==varDefs.MILESTONE_EXPED_END)
				{
					 procedimiento.setProcedimientoTerminado(true);
					
				}

			}

		}

		Iterator iterator = procedimiento.getTramitesFases().values()
				.iterator();
		Iterator iteratorKeys = procedimiento.getTramitesFases().keySet()
				.iterator();
		IEntitiesAPI entityAPI = invesFlowAPI.getEntitiesAPI();
		while (iterator.hasNext()) {
			int idPhase = Integer.parseInt(iteratorKeys.next().toString());
			List tramites = (List) iterator.next();

			for (int i = 0; i < tramites.size(); i++) {
				//Obtengo las instancias de cada tramite, siempre y cuando hubiese instancias
				TramiteFase tramiteFase = ((TramiteFase) tramites.get(i));
				if (tramiteFase.getEstado() != varDefs.NO_INICIADO) {
					IItemCollection instancias = entityAPI
							.getTasksExpInPhase(numExp, idPhase,
									tramiteFase.getPTaskId());

					int numOpen, numClose;
					numOpen = numClose = 0;
					List instanciasExistentes = new ArrayList();
					
					if (!instancias.next()) {
						tramiteFase.setEstado(varDefs.NO_INICIADO);
					}

					while (instancias.next()) {
						//

						IItem instancia = instancias.value();
						if (instancia.getInt("SPAC_DT_TRAMITES:ESTADO") != varDefs.tramiteCancelado) {
							if (((TramiteFase) tramites.get(i)).getEstado() == varDefs.tramiteCancelado) {
								if (instancia
										.getInt("SPAC_DT_TRAMITES:ESTADO") == varDefs.tramiteAbierto)
									((TramiteFase) tramites.get(i))
											.setEstado(varDefs.INICIADO);
								else
									((TramiteFase) tramites.get(i))
											.setEstado(varDefs.CERRADO);
							}
							Date date = instancia
									.getDate("SPAC_DT_TRAMITES:FECHA_INICIO");
							SimpleDateFormat formateador = new SimpleDateFormat(
									"dd/MM/yyyy HH:mm");
							String fecha = formateador.format(date);

							Object idpcdSub = instancia
									.get("SPAC_DT_TRAMITES:ID_SUBPROCESO");

							int sub = -1;
							if (idpcdSub != null)
								sub = Integer.parseInt(idpcdSub.toString());
							Instancia inst = new Instancia(tramiteFase
									.getCtTaskId(), tramiteFase
									.getPTaskId(), fecha, instancia
									.getInt("SPAC_DT_TRAMITES:ESTADO"),
									tramiteFase.getSubTaskId(), sub);
							instanciasExistentes.add(inst);
							//Obtenemos el estado
							int estado = instancia
									.getInt("SPAC_DT_TRAMITES:ESTADO");
							if (estado == varDefs.tramiteAbierto) {
								((TramiteFase) tramites.get(i))
										.setEstado(varDefs.INICIADO);
								numOpen += 1;
							} else if (estado == varDefs.tramiteCerrado)
								numClose += 1;
						}

					}
					((TramiteFase) tramites.get(i))
							.setNumInstancesOpen(numOpen);
					((TramiteFase) tramites.get(i))
							.setNumInstancesOpen(numClose);
					((TramiteFase) tramites.get(i))
							.setInstancias(instanciasExistentes);
					((TramiteFase) tramites.get(i))
							.setNumInstancesClose(numClose);
					((TramiteFase) tramites.get(i))
							.setNumInstancesOpen(numOpen);

				}

			}

			procedimiento.getTramitesFases().put(new Integer(idPhase),
					tramites);

		}
		return procedimiento;
	}
	
	/**
	 * 
	 * @param pcdId , Identificador del subproceso
	 * @return Procedimiento informado
	 * @throws DesignerException
	 */
	public Procedimiento getSubProcByPcdId(int pcdId, int instance)
			throws DesignerException {
		try {

			ISessionAPI sessionAPI = getSessionAPI();
			IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
			Procedimiento procedimiento = getProcedure(pcdId);

			IItemCollection hitos = invesFlowAPI.getMilestones(instance);

			int idFase, codHito;
			while (hitos.next()) {
				IItem hito = hitos.value();

				codHito = hito.getInt("HITO");
				if (hitoTratadoActivity(codHito)) {
					idFase = hito.getInt("ID_FASE");
					if (idFase != 0) {

						//Obtengo la fase que estamos tratando

						if (codHito == varDefs.MILESTONE_ACTIVITY_END)
							procedimiento
									.setEstadoFase(idFase, varDefs.CERRADO);
						else if (codHito == varDefs.MILESTONE_ACTIVITY_END_RELOCATED)
							procedimiento.setEstadoFase(idFase,
									varDefs.NO_INICIADO);
						else if (codHito == varDefs.MILESTONE_ACTIVITY_START)
							procedimiento.setEstadoFase(idFase,
									varDefs.INICIADO);
					}

				}

			}

			

		/*	String xmlCondicion = "<condition version='1.00'><id>1</id><name>Condition1</name><conditions><simpleCondition id='1'><type></type><parenthesisOpen>0</parenthesisOpen><op1><idEntidad>1</idEntidad><idCampo>48</idCampo><nombreEntidad>SPAC_EXPEDIENTES</nombreEntidad><nombreCampo>direcciontelematica</nombreCampo><valor></valor></op1><operator>LIKE</operator><op2><idEntidad></idEntidad><idCampo></idCampo><nombreEntidad></nombreEntidad><nombreCampo></nombreCampo><valor><![CDATA[%@ieci.es]]></valor></op2><op3></op3><parenthesisClose>0</parenthesisClose></simpleCondition><simpleCondition id='2'><type>AND</type><parenthesisOpen>1</parenthesisOpen><op1><idEntidad>3</idEntidad><idCampo>13</idCampo><nombreEntidad>SPAC_DT_INTERVINIENTES</nombreEntidad><nombreCampo>tfno_movil</nombreCampo><valor></valor></op1><operator>NOT NULL</operator><op2></op2><op3></op3><parenthesisClose>0</parenthesisClose></simpleCondition><simpleCondition id='2'><type>OR</type><parenthesisOpen>0</parenthesisOpen><op1><idEntidad>3</idEntidad><idCampo>12</idCampo><nombreEntidad>SPAC_DT_INTERVINIENTES</nombreEntidad><nombreCampo>tfno_fijo</nombreCampo><valor></valor></op1><operator>NOT NULL</operator><op2></op2><op3></op3><parenthesisClose>1</parenthesisClose></simpleCondition><simpleCondition id='2'><type>AND</type><parenthesisOpen>0</parenthesisOpen><op1><idEntidad>7</idEntidad><idCampo>12</idCampo><nombreEntidad>SPAC_DT_TRAMITES</nombreEntidad><nombreCampo>fecha_cierre</nombreCampo><valor></valor></op1><operator>BETWEEN</operator><op2><idEntidad></idEntidad><idCampo></idCampo><nombreEntidad></nombreEntidad><nombreCampo></nombreCampo><valor><![CDATA[10/12/2008]]></valor></op2><op3><idEntidad></idEntidad><idCampo></idCampo><nombreEntidad></nombreEntidad><nombreCampo></nombreCampo><valor><![CDATA[21/12/2008]]></valor></op3><parenthesisClose>0</parenthesisClose></simpleCondition></conditions><sql>"
					+ "<![CDATA[ SELECT COUNT(*)FROM SPAC_TRAMITES , SPAC_DT_TRAMITES , SPAC_DT_INTERVINIENTES WHERE SPAC_EXPEDIENTE.direcciontelematica LIKE %@ieci.es AND ( SPAC_DT_INTERVINIENTES.tfno_movil NOT NULL OR SPAC_DT_INTERVINIENTES.tfno_fijo NOT NULL) AND SPAC_DT_TRAMITES.fecha_cierre BETWEEN '10/12/2008' AND '21/12/2008' ]]></sql></condition>";

			//parseo al objeto
			List flujos = procedimiento.getFlujos();
			if (flujos != null && flujos.size() >= 1)
				((DrawFlow) procedimiento.getFlujos().get(0))
						.setCondition(parserConditions(xmlCondicion));*/
			return procedimiento;
		} catch (Throwable t) {
			logger.error("Error al obtener el subproceso", t);
			throw new DesignerException(t.getLocalizedMessage());
		}

	}

	/**
	 * Se obtiene la informacion del expediente y se informa al objeto procedimiento
	 * de los estados tanto de las fases como de los tramites
	 * @param stageId Identificador de la fase activa del expediente 
	 */
	public Procedimiento getProcedureByStageId(int stageId)
			throws DesignerException {

		try {
			ISessionAPI sessionAPI = getSessionAPI();
			IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
			IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

			// Información del expediente
			IStage stage = invesFlowAPI.getStage(stageId);
			String numExp = stage.getString("NUMEXP");

			// Obtener la información del procedimiento del expediente
			int pcdId = stage.getInt("ID_PCD");

			String pcdXml = procedureAPI.getProcedureXml(pcdId);
			Procedimiento procedimiento = createProcedimiento(pcdId, pcdXml);
			procedimiento.setIdProc(pcdId);

		    return getHitosByNumExp(numExp, procedimiento);
			


			/***********************************************************
			//PARA PROBAR SI TIENEN O NO FLUJOS ASOCIADOS
			
			
			String xmlCondicion = "<condition version='1.00'><id>1</id><name>Condition1</name><conditions><simpleCondition id='1'><type></type><parenthesisOpen>0</parenthesisOpen><op1><idEntidad>1</idEntidad><idCampo>48</idCampo><nombreEntidad>SPAC_EXPEDIENTES</nombreEntidad><nombreCampo>direcciontelematica</nombreCampo><valor></valor></op1><operator>LIKE</operator><op2><idEntidad></idEntidad><idCampo></idCampo><nombreEntidad></nombreEntidad><nombreCampo></nombreCampo><valor><![CDATA[%@ieci.es]]></valor></op2><op3></op3><parenthesisClose>0</parenthesisClose></simpleCondition><simpleCondition id='2'><type>AND</type><parenthesisOpen>1</parenthesisOpen><op1><idEntidad>3</idEntidad><idCampo>13</idCampo><nombreEntidad>SPAC_DT_INTERVINIENTES</nombreEntidad><nombreCampo>tfno_movil</nombreCampo><valor></valor></op1><operator>NOT NULL</operator><op2></op2><op3></op3><parenthesisClose>0</parenthesisClose></simpleCondition><simpleCondition id='2'><type>OR</type><parenthesisOpen>0</parenthesisOpen><op1><idEntidad>3</idEntidad><idCampo>12</idCampo><nombreEntidad>SPAC_DT_INTERVINIENTES</nombreEntidad><nombreCampo>tfno_fijo</nombreCampo><valor></valor></op1><operator>NOT NULL</operator><op2></op2><op3></op3><parenthesisClose>1</parenthesisClose></simpleCondition><simpleCondition id='2'><type>AND</type><parenthesisOpen>0</parenthesisOpen><op1><idEntidad>7</idEntidad><idCampo>12</idCampo><nombreEntidad>SPAC_DT_TRAMITES</nombreEntidad><nombreCampo>fecha_cierre</nombreCampo><valor></valor></op1><operator>BETWEEN</operator><op2><idEntidad></idEntidad><idCampo></idCampo><nombreEntidad></nombreEntidad><nombreCampo></nombreCampo><valor><![CDATA[10/12/2008]]></valor></op2><op3><idEntidad></idEntidad><idCampo></idCampo><nombreEntidad></nombreEntidad><nombreCampo></nombreCampo><valor><![CDATA[21/12/2008]]></valor></op3><parenthesisClose>0</parenthesisClose></simpleCondition></conditions><sql>"
					+ "<![CDATA[ SELECT COUNT(*)FROM SPAC_TRAMITES , SPAC_DT_TRAMITES , SPAC_DT_INTERVINIENTES WHERE SPAC_EXPEDIENTE.direcciontelematica LIKE %@ieci.es AND ( SPAC_DT_INTERVINIENTES.tfno_movil NOT NULL OR SPAC_DT_INTERVINIENTES.tfno_fijo NOT NULL) AND SPAC_DT_TRAMITES.fecha_cierre BETWEEN '10/12/2008' AND '21/12/2008' ]]></sql></condition>";

			//parseo al objeto
			List flujos = procedimiento.getFlujos();
			if (flujos != null && flujos.size() >= 1)
				((DrawFlow) procedimiento.getFlujos().get(0))
						.setCondition(parserConditions(xmlCondicion));*/



		} catch (Throwable t) {
			logger.error("Error al obtener el procedimiento", t);
			throw new DesignerException(t.getLocalizedMessage());
		}
	}

	/**
	 * 
	 * @param idHito, identificador del hito que se ha registrado
	 * @return true si el hito influye en el estado de las actividades 
	 */
	protected boolean hitoTratadoActivity(int idHito) {
		return (idHito == varDefs.MILESTONE_ACTIVITY_END
				|| idHito == varDefs.MILESTONE_ACTIVITY_END_RELOCATED || idHito == varDefs.MILESTONE_ACTIVITY_START);
	}

	
	
	
	/**
	 * 
	 * @param idHito, identificador del hito que se ha registrado
	 * @return true si el hito influye en el estado bien de la fase o de los tramites y falso en caso contrario
	 */
	protected boolean hitoTratado(int idHito) {

		return (idHito == varDefs.MILESTONE_STAGE_END
				|| idHito == varDefs.MILESTONE_STAGE_END_RELOCATED
				|| idHito == varDefs.MILESTONE_STAGE_START
				|| idHito == varDefs.MILESTONE_TASK_DELETE
				|| idHito == varDefs.MILESTONE_TASK_END || idHito == varDefs.MILESTONE_TASK_START ||idHito== varDefs.MILESTONE_EXPED_END);

	}

	/**
	 * Obtiene la información del procedimiento.
	 * @param pcdId Identificador del procedimiento.
	 * @return Información del procedimiento.
	 * @throws DesignerException si ocurre algún error.
	 */
	public Procedimiento getProcedure(int pcdId) throws DesignerException {

		try {
			ISessionAPI sessionAPI = getSessionAPI();
			IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
			IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

			String pcdXml = procedureAPI.getProcedureXml(pcdId);

			return createProcedimiento(pcdId, pcdXml);

		} catch (Throwable t) {
			logger.error("Error al obtener el procedimiento", t);
			throw new DesignerException(t.getLocalizedMessage());
		}
	}

	protected static Procedimiento createProcedimiento(int pcdId, String pcdXml) {
		Procedimiento proc = new Procedimiento();
		proc.setIdProc(pcdId);

		if ((pcdXml != null) && (pcdXml.trim().length() > 0)) {
			XmlFacade xml = new XmlFacade(pcdXml);
			proc.setNombreProc(xml.get("/procedure/name"));
			proc.setEstado(new Integer(xml.get("/procedure/state")).intValue());
			int tipoProc = new Integer(xml.get("/procedure/tipo")).intValue();
			if (isProcedure(tipoProc))
				proc.setTipoProcedimiento(true);
			else
				proc.setTipoProcedimiento(false);
			if (isPcdStateOld(proc.getEstado()))
				proc.setEstado(varDefs.historico);
			else if (isPcdStateCurrent(proc.getEstado()))
				proc.setEstado(varDefs.vigente);
			else
				proc.setEstado(varDefs.borrador);

			// Fases
			NodeIterator stagesNodeIt = xml
					.getNodeIterator("/procedure/stages/stage");
			for (Node stageNode = stagesNodeIt.nextNode(); stageNode != null; stageNode = stagesNodeIt
					.nextNode()) {
				if(stageNode!=null){
				int stageId = Integer.parseInt(XmlFacade.getAttributeValue(
						stageNode, "id"));
				proc.addFase(new DrawObject(stageId, XmlFacade.get(stageNode,
						"name"), TypeConverter.parseInt(XmlFacade.get(
						stageNode, "ginfo/position/@x"), 0), TypeConverter
						.parseInt(
								XmlFacade.get(stageNode, "ginfo/position/@y"),
								0), TypeConverter.parseInt(XmlFacade.get(
						stageNode, "idctstage"), -1)));

				// Trámites de la fase
				List stageTasks = new ArrayList();
				NodeIterator tasksNodeIt = XmlFacade.getNodeIterator(stageNode,
						"tasks/task");

				for (Node taskNode = tasksNodeIt.nextNode(); taskNode != null; taskNode = tasksNodeIt
						.nextNode()) {
					String idSub = XmlFacade.get(taskNode, "idpcdsub");
					int idSubP = -1;
					if (idSub.length() > 0)
						idSubP = Integer.parseInt(idSub);

					String id = XmlFacade.getAttributeValue(taskNode, "id");
					stageTasks.add(new TramiteFase(XmlFacade.get(taskNode,
							"name"), idSubP, Integer.parseInt(id)));

				}
				proc.addTramitesFases(stageId, stageTasks);
				}
			}

			// Nodos de sincronización
			NodeIterator syncNodesNodeIt = xml
					.getNodeIterator("/procedure/syncnodes/syncnode");
			for (Node syncNodeNode = syncNodesNodeIt.nextNode(); syncNodeNode != null; syncNodeNode = syncNodesNodeIt
					.nextNode()) {
				proc.addNodo(new DrawObject(Integer.parseInt(XmlFacade
						.getAttributeValue(syncNodeNode, "id")), XmlFacade.get(
						syncNodeNode, "name"), TypeConverter.parseInt(XmlFacade
						.get(syncNodeNode, "ginfo/position/@x"), 0),
						TypeConverter.parseInt(XmlFacade.get(syncNodeNode,
								"ginfo/position/@y"), 0), TypeConverter
								.parseInt(XmlFacade.get(syncNodeNode, "type"),
										-1)));
			}

			// Transiciones
			NodeIterator flowsNodeIt = xml
					.getNodeIterator("/procedure/flows/flow");
			for (Node flowNode = flowsNodeIt.nextNode(); flowNode != null; flowNode = flowsNodeIt
					.nextNode()) {
				proc.addFlow(new DrawFlow(Integer.parseInt(XmlFacade
						.getAttributeValue(flowNode, "id")),
						Integer.parseInt(XmlFacade.getAttributeValue(flowNode,
								"orig")), Integer.parseInt(XmlFacade
								.getAttributeValue(flowNode, "dest"))
				,parserEvents(XmlFacade.getNodeIterator(flowNode, "events/event"))));
			}
		}

	
		return proc;
	}
	
	/**
	 * 
	 * @param condicionXml String con el xml de la condicion
	 * @return Objeto condición
	 */
	protected static Condition parserCondition(String condicionXml)
	{
		Condition condicion = null;
		
		if (StringUtils.isNotBlank(condicionXml)) {
			condicionXml=StringUtils.unescapeXml(condicionXml);
			XmlFacade xml = new XmlFacade(condicionXml);
			condicion=new Condition();
			String nombre = xml.get("/condition/name");
		
			condicion.setNombre(nombre);
			
			List condiciones = new ArrayList();
			ConditionSimple condSimple = new ConditionSimple();
			// Condiciones
			NodeIterator conditionsNodeIt = xml
			.getNodeIterator("/condition/conditions/simpleCondition");
			for (Node condSimpleNode = conditionsNodeIt.nextNode(); condSimpleNode != null; condSimpleNode = conditionsNodeIt
			.nextNode()) {
				condSimple = new ConditionSimple();
				condSimple.setAndOr(XmlFacade.get(condSimpleNode, "type"));
			
				condSimple.setParentesisOpen(Integer.parseInt(XmlFacade.get(
				condSimpleNode, "parenthesisOpen")));
				condSimple.setParentesisClose(Integer.parseInt(XmlFacade.get(
				condSimpleNode, "parenthesisClose")));
				String operador=XmlFacade.get(condSimpleNode, "operator"); 
				if(Html.contains(operador, "NOT NULL") || Html.contains(operador, "not null"))
					condSimple.setOperador("NOT NULL");
				else if(Html.contains(operador, "NULL") || Html.contains(operador, "null"))
					condSimple.setOperador("NULL");
				else
					condSimple.setOperador(operador);
				
				//Operador1
				condSimple.setOp1(new Operando( XmlFacade.get(condSimpleNode,
						"op1/valor"), XmlFacade.get(condSimpleNode,
						"op1/nombreEntidad"), XmlFacade.get(condSimpleNode,
						"op1/nombreCampo")));
				//Operador2
				condSimple.setOp2(new Operando( XmlFacade.get(condSimpleNode,
						"op2/valor"), XmlFacade.get(condSimpleNode,
						"op2/nombreEntidad"), XmlFacade.get(condSimpleNode,
						"op2/nombreCampo")));
				//Operador3
				condSimple.setOp3(new Operando( XmlFacade.get(condSimpleNode,
						"op3/valor"), XmlFacade.get(condSimpleNode,
						"op3/nombreEntidad"), XmlFacade.get(condSimpleNode,
						"op3/nombreCampo")));
	
				condiciones.add(condSimple);
	
			}
	
			condicion.setCondicionesSimples(condiciones);
		}
		
		return condicion;
	}
	
	protected static List parserEvents(NodeIterator eventsNodeIt) {
		
		List listadoEventos = new ArrayList();
		
		for (Node eventNode = eventsNodeIt.nextNode(); eventNode != null; eventNode = eventsNodeIt
		.nextNode()) {
			Evento evento=new Evento();
			
			evento.setCode(Integer.parseInt(XmlFacade.get(eventNode, "code")));
			evento.setOrder(Integer.parseInt(XmlFacade.get(eventNode, "order")));
			evento.setRuleId((TypeConverter.parseInt(XmlFacade
					.get(eventNode, "ruleId"), -1)));
			if(evento.getRuleId()==-1){
			
				Condition condicion= parserCondition(XmlFacade.get(eventNode, "condition"));
				
				
		
		evento.setCondition(condicion);
			}
		listadoEventos.add( evento);
		}	
	
		return listadoEventos;
		
	}

	public List getPStages(int pcdId) throws DesignerException {

		try {

			ISessionAPI sessionAPI = getSessionAPI();
			IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
			IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();
			IItemCollection pStages = procedureAPI.getStages(pcdId);

			List stages = new ArrayList();
			while (pStages.next()) {
				IItem pStage = pStages.value();

				// Identificador de la fase en el catálogo
				stages.add(pStage.getString("NOMBRE"));

			}

			return stages;
		} catch (Throwable t) {
			logger.error("Error al obtener las fases/actividades del procedimiento/subproceso", t);
			throw new DesignerException(t.getLocalizedMessage());
		}
	}

	/**
	 * Obtiene la lista de fases del catálogo que no pertenecen al procedimiento.
	 * @param pcdId Identificador del procedimiento.
	 * @return Lista de fases del catálogo.
	 * @throws DesignerException si ocurre algún error.
	 */
	public List getCtStages(int pcdId) throws DesignerException {

		try {

			ISessionAPI sessionAPI = getSessionAPI();
			IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
			ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
			IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

			// Lista de fases del catálogo
			IItemCollection ctStages = catalogAPI.getCTStages();

			// Lista de fases del procedimiento
			IItemCollection pcdStages = procedureAPI.getStages(pcdId);
			Map pcdStagesMap = pcdStages.toMapStringKey("ID_CTFASE");

			// Listado de fases del catálogo que se van a mostrar
			List stages = new ArrayList();
			while (ctStages.next()) {

				IItem ctStage = ctStages.value();

				// Identificador de la fase en el catálogo
				int ctStageId = ctStage.getKeyInt();

				// Añadir la fase del catálogo a la lista
				if (pcdStagesMap.get(String.valueOf(ctStageId)) == null) {
					stages.add(new DrawObject(ctStageId, ctStage
							.getString("NOMBRE"), ctStage
							.getString("DESCRIPCION")));
				}
			}

			return stages;

		} catch (Throwable t) {
			logger.error("Error al obtener las fases del catálogo", t);
			throw new DesignerException(t.getLocalizedMessage());
		}
	}

	/**
	 * Añade una fase del catálogo al procedimiento.
	 * @param pcdId Identificador de la fase.
	 * @param ctStageId Identificador de la fase en el catálogo.
	 * @param x Coordenada x de la fase.
	 * @param y Coordenada y de la fase.
	 * @return Identificador de la fase creada.
	 * @throws DesignerException si ocurre algún error.
	 */
	public int addStage(int pcdId, int ctStageId, int x, int y)
			throws DesignerException {

		try {

			ISessionAPI sessionAPI = getSessionAPI();
			IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
			IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

			// Nombre de la fase
			String stageName = null;

			// Obtener el nombre de la fase si es del catálogo
			ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
			IItem ctstage = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_CT_STAGE,
					ctStageId);
			if (ctstage != null) {
				stageName = ctstage.getString("NOMBRE");
			}

			// Crear la fase
			return procedureAPI.addStage(pcdId, ctStageId, stageName,
					new GInfo(x, y));

		} catch (Throwable t) {
			logger.error("Error al añadir la fase/actividad al procedimiento/subproceso", t);
			throw new DesignerException(t.getLocalizedMessage());
		}
	}

	/**
	 * Añade una fase específica al procedimiento.
	 * @param pcdId Identificador de la fase.
	 * @param stageName Nombre de la fase.
	 * @param x Coordenada x de la fase.
	 * @param y Coordenada y de la fase.
	 * @return Identificador de la fase creada.
	 * @throws DesignerException si ocurre algún error.
	 */
	public int addStage(int pcdId, String stageName, int x, int y)
			throws DesignerException {

		try {
			ISessionAPI sessionAPI = getSessionAPI();
			IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
			IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

			// Crear la fase
			return procedureAPI.addStage(pcdId,
					ISPACEntities.ENTITY_NULLREGKEYID, stageName, new GInfo(x,
							y));
		} catch (Throwable t) {
			logger.error("Error al añadir la fase/actividad al procedimiento/subproceso", t);
			throw new DesignerException(t.getLocalizedMessage());
		}
	}

	/**
	 * Elimina una fase del procedimiento.
	 * @param pcdId Identificador del procedimiento.
	 * @param stageId Identificador de la fase en el procedimiento.
	 * @return Información del procedimiento
	 * @throws DesignerException si ocurre algún error.
	 */
	public Procedimiento removeStage(int pcdId, int stageId) throws DesignerException {

		try {

			ISessionAPI sessionAPI = getSessionAPI();
			IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
			IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

			// Eliminar la fase
			procedureAPI.removeStage(pcdId, stageId);
			return getProcedure(pcdId);

		} catch (Throwable t) {
			logger.error("Error al eliminar la fase/actividad del procedimiento/subproceso", t);
			throw new DesignerException(t.getLocalizedMessage());
		}
	}

	/**
	 * Añade un nodo de sincronización al procedimiento.
	 * @param pcdId Identificador del procedimiento.
	 * @param type Tipo de nodo de sincronización ({@link }}).
	 * @param x Coordenada x del nodo de sincronización.
	 * @param y Coordenada y del nodo de sincronización.
	 * @return Identificador del nodo de sincronización creado.
	 * @throws DesignerException si ocurre algún error.
	 */
	public int addSyncNode(int pcdId, int type, int x, int y)
			throws DesignerException {

		try {

			ISessionAPI sessionAPI = getSessionAPI();
			IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
			IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

			// Crear el nodo de sincronización
			return procedureAPI.addSyncNode(pcdId, type, new GInfo(x, y));

		} catch (Throwable t) {
			logger.error("Error al añadir un nodo de sincronización al procedimiento/subproceso", t);
			throw new DesignerException(t.getLocalizedMessage());
		}
	}

	/**
	 * Elimina el nodo de sincronización del procedimiento.
	 * @param pcdId Identificador del procedimiento.
	 * @param syncNodeId Identificador del nodo de sincronización en el procedimiento.
	 * @return Información del procedimiento 
	 * @throws DesignerException si ocurre algún error.
	 */
	public Procedimiento removeSyncNode(int pcdId, int syncNodeId)
			throws DesignerException {

		try {

			ISessionAPI sessionAPI = getSessionAPI();
			IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
			IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

			// Eliminar el nodo de sincronización
			procedureAPI.removeSyncNode(pcdId, syncNodeId);
			return getProcedure(pcdId);

		} catch (Throwable t) {
			logger.error("Error al eliminar un nodo de sincronización del procedimiento/subproceso", t);
			throw new DesignerException(t.getLocalizedMessage());
		}
	}

	/**
	 * Añade una transición al procedimiento.
	 * @param pcdId Identificador del procedimiento.
	 * @param startNodeId Identificador del nodo origen de la transición.
	 * @param endNodeId Identificador del nodo destino de la transición.
	 * @return Identificador de la transición creada.
	 * @throws DesignerException si ocurre algún error.
	 */
	public int addFlow(int pcdId, int startNodeId, int endNodeId)
			throws DesignerException {

		try {

			ISessionAPI sessionAPI = getSessionAPI();
			IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
			IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

			// Crear el flujo
			return procedureAPI.createFlow(pcdId, startNodeId, endNodeId);

		} catch (Throwable t) {
			logger.error("Error al añadir un flujo al procedimiento/subproceso", t);
			throw new DesignerException(t.getLocalizedMessage());
		}
	}

	/**
	 * Elimina la transición del procedimiento.
	 * @param pcdId Identificador del procedimiento.
	 * @param flowId Identificador de la transición en el procedimiento.
	 * @throws DesignerException si ocurre algún error.
	 */
	public void removeFlow(int pcdId, int flowId) throws DesignerException {

		try {

			ISessionAPI sessionAPI = getSessionAPI();
			IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
			IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

			// Eliminar el flujo
			procedureAPI.removeFlow(pcdId, flowId);

		} catch (Throwable t) {
			logger.error("Error al eliminar un flujo del procedimiento/subproceso", t);
			throw new DesignerException(t.getLocalizedMessage());
		}
	}

	/**
	 * Obtiene los trámites asociados a la fase.
	 * @param stageId Identificador de la fase en el procedimiento.
	 * @return Lista de trámites.
	 */
	public List getTasks(int stageId) throws DesignerException {

		try {

			ISessionAPI sessionAPI = getSessionAPI();
			IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
			ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
			IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

		
			
			// Información de la fase en el catálogo
			IItem pcdStage = invesFlowAPI.getProcedureStage(stageId);
			int ctStageId = pcdStage.getInt("ID_CTFASE");

			// Lista de trámites de la fase del catálogo
			IItemCollection ctStageTasks = catalogAPI
					.getCTStageTasks(ctStageId);

			// Lista de trámites asociados a la fase
			IItemCollection stageTasks = procedureAPI.getStageTasks(stageId);
			Map stageTasksMap = stageTasks.toMapStringKey("ID_CTTRAMITE");

			// Lista de trámites
			List tasks = new ArrayList();
			List tasksAsociadas = new ArrayList();
			while (ctStageTasks.next()) {

				// Trámite en el catálogo
				IItem ctTask = ctStageTasks.value();

				// Identificador del trámite en el catálogo
				int ctTaskId = ctTask.getInt("SPAC_CT_TRAMITES:ID");

				// Identificador del trámite asociado
				int pTaskId = -1;

				// Trámite en el procedimiento
				IItem pTask = (IItem) stageTasksMap.get(String
						.valueOf(ctTaskId));

				if (pTask != null)
					pTaskId = pTask.getInt("ID");

				//Obtengo el idSubproceso, si es que existe
				Object idOSubproceso = ctTask
						.get("SPAC_CT_TRAMITES:ID_SUBPROCESO");
				int idSubProceso = -1;
				if (idOSubproceso != null
						&& idOSubproceso.toString().compareTo("") != 0)
					idSubProceso = Integer.parseInt(idOSubproceso.toString());
				// Añadir el trámite del catálogo a la lista
				if (pTask == null)
					tasks.add(new TramiteFase(ctTaskId, pTaskId, ctTask
							.getString("SPAC_CT_TRAMITES:NOMBRE"),
							(pTask != null), idSubProceso));
				else {

					tasksAsociadas.add(new TramiteFase(ctTaskId, pTaskId,
							ctTask.getString("SPAC_CT_TRAMITES:NOMBRE"),
							(pTask != null), idSubProceso));
				}
			}

			Iterator itr = stageTasksMap.values().iterator();
			int cont = 0;
			while (itr.hasNext()) {

				IItem pTask = (IItem) itr.next();
				int pTaskId = pTask.getInt("ID");
				boolean encontrado = false;
				for (int i = 0; i < tasksAsociadas.size() && !encontrado; i++) {
					if (((TramiteFase) tasksAsociadas.get(i)).getPTaskId() == pTaskId) {
						encontrado = true;
						tasks.add(cont, tasksAsociadas.get(i));
					}

				}
				cont++;
			}

			return tasks;

		} catch (Throwable t) {
			logger.error("Error al obtener los trámites de una fase", t);
			throw new DesignerException(t.getLocalizedMessage());
		}
	}

	/**
	 * Añade un trámite a la fase del procedimiento.
	 * @param stageId Identificador de la fase en el procedimiento.
	 * @param ctTaskId Identificador del trámite en el catálogo de trámites.
	 * @return Identificador del trámite del procedimiento creado.
	 * @throws DesignerException si ocurre algún error.
	 */
	public int addTask(int stageId, int ctTaskId) throws DesignerException {

		try {

			ISessionAPI sessionAPI = getSessionAPI();
			IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
			IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

			// Crear el trámite
			return procedureAPI.addTask(stageId, ctTaskId);

		} catch (Throwable t) {
			logger.error("Error al añadir un trámite a una fase", t);
			throw new DesignerException(t.getLocalizedMessage());
		}
	}

	/**
	 * Elimina el trámite del procedimiento.
	 * @param taskId Identificador del trámite en el procedimiento.
	 * @throws DesignerException si ocurre algún error.
	 */
	public void removeTask(int taskId) throws DesignerException {

		try {

			ISessionAPI sessionAPI = getSessionAPI();
			IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
			IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

			// Eliminar el trámite
			procedureAPI.removeTask(taskId);

		} catch (Throwable t) {
			logger.error("Error al eliminar un trámite", t);
			throw new DesignerException(t.getLocalizedMessage());
		}
	}

	/**
	 * Actualiza las posiciones de las fases y nodos.
	 * @param drawObjects Lista de objetos ({@link DrawObject}).
	 * @throws DesignerException si ocurre algún error.
	 */
	public void updatePosition(DrawObject drawObject) throws DesignerException {
		try {

			
			ISessionAPI sessionAPI = getSessionAPI();

			if (drawObject != null) {

				IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
				IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

				// Actualizar la posición
				procedureAPI.updateGInfoNode(new GInfo(drawObject.getLeft(),
						drawObject.getTop()), new Integer(drawObject.getId()));

			}
		} catch (Throwable t) {
			logger.error("Error al modificar la posición de un nodo", t);
			throw new DesignerException(t.getLocalizedMessage());
		}

	}

	/**
	 * Actualiza las posiciones de las fases y nodos.
	 * @param drawObjects Lista de objetos ({@link DrawObject}).
	 * @throws DesignerException si ocurre algún error.
	 */
	/*
		public void updatePositions(List drawObjects) throws DesignerException {
			
			try {
				
				ISessionAPI sessionAPI = getSessionAPI();
				
				if (drawObjects != null) {
					
					Map gInfoMap = new HashMap(); 

					for (int i = 0; i < drawObjects.size(); i++) {
						DrawObject drawObject = (DrawObject) drawObjects.get(i);
						
						gInfoMap.put(new Integer(drawObject.getId()), 
								new GInfo(drawObject.getLeft(), drawObject.getTop()));
					}
					
					if (!gInfoMap.isEmpty()) {
						
						IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
						IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();
						
						// Actualizar la posición
					
						procedureAPI.updateGInfo(gInfoMap);
					}
				}
			} catch (Throwable t) {
				throw new DesignerException(t.getLocalizedMessage());
			}
		}
	 */
	/**
	 * Obtiene el API de sesión de ISPAC.
	 * @return API de sesión de ISPAC.
	 * @throws ISPACException si ocurre algún error.
	 */
	protected ISessionAPI getSessionAPI() throws ISPACException {

		HttpServletRequest request = getThreadLocalRequest();
		HttpServletResponse response = getThreadLocalResponse();

		SessionAPI sessionAPI = SessionAPIFactory.getSessionAPI(request);

		String ticket = null;

		Cookie[] allCookies = request.getCookies();
		if (allCookies != null) {
			for (int i = 0; i < allCookies.length; i++) {
				if (allCookies[i].getName().equals("user")) {
					ticket = allCookies[i].getValue();
					response.addCookie(allCookies[i]);
				}
			}
		}

		if (StringUtils.isNotBlank(ticket)) {
			sessionAPI.init(ticket, LocaleHelper.getLocale(request));
		} else {

			String user = "sigem";
			String password = "sigem";
			String app = "DESIGNER";
			String remoteHost = request.getRemoteHost();

			if ((remoteHost == null) || (remoteHost == ""))
				remoteHost = request.getRemoteAddr();

			sessionAPI.login(remoteHost, user, password, app, LocaleHelper.getLocale(request));

			ticket = sessionAPI.getTicket();
			Cookie cookieUser = new Cookie("user", ticket);
			response.addCookie(cookieUser);
		}
		
		return sessionAPI;
	}

	/**
	 * 
	 * @param state estado del procedimiento
	 * @return cierto si el estado es vigente
	 */
	protected static boolean isPcdStateCurrent(int state) {

		return IProcedure.PCD_STATE_CURRENT == state;
	}

	/**
	 * 
	 * @param state estado del procedimiento
	 * @return cierto si el estado es borrador
	 */
	protected static boolean isPcdStateDraft(int state) {

		return IProcedure.PCD_STATE_DRAFT == state;
	}

	/**
	 * 
	 * @param state estado del procedimiento
	 * @return cierto si es historico
	 */
	protected static boolean isPcdStateOld(int state) {

		return IProcedure.PCD_STATE_OLD == state;
	}

	/**
	 * 
	 * @param tipo del procedimiento que vamos a tratar
	 * @return cierto si es un procedimiento
	 */
	protected static boolean isProcedure(int tipo) {

		return (IProcess.PROCESS_TYPE == tipo);
	}

	/**
	 * 
	 * @param tipo del procedimiento que vamos a tratar
	 * @return cierto si es un procedimiento
	 */
	protected static boolean isSubProceso(int tipo) {

		return (IProcess.SUBPROCESS_TYPE == tipo);
	}

	/**
	 * return 1 entorno catalogo 2 entorno tramitador
	 */
	public int getEntorno() throws DesignerException {

		return 1;

	}

	/**
	 * 
	 * @param orden de los tramites asociados a la fase
	 * @throws DesignerException
	 */
	public void reorderTasks(int[] orden) throws DesignerException {
		try {

			ISessionAPI sessionAPI = getSessionAPI();
			IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
			IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

			procedureAPI.reorderTasks(orden);

		} catch (Throwable t) {
			logger.error("Error al reordenar los trámites", t);
			throw new DesignerException(t.getLocalizedMessage());
		}

	}

	/**
	 * Obtiene la lista de entidades relacionadas con el procedimiento.
	 * @param pcdId Identificador del procedimiento
	 * @return Mapa de entidades con sus campos.
	 * @throws DesignerException si ocurre algún error.
	 */
	public Map getEntities(int pcdId) throws DesignerException {

		Map entities = new HashMap();
		
		try {
			
			ISessionAPI sessionAPI = getSessionAPI();
			IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
			ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
			IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
			
			// Información de las entidades del procedimiento
			IItemCollection ctentities = catalogAPI.getProcedureEntities(pcdId);
			while (ctentities.next()) {
				IItem ctentity = ctentities.value();
				EntityResources resources = entitiesAPI.getEntityResources(ctentity.getInt("CTENTITY:ID"));
				entities.put(ctentity.get("CTENTITY:NOMBRE"), getEntidad(ctentity, resources));
			}

		} catch (Throwable t) {
			logger.error("Error al obtener las entidades de un procedimiento", t);
			throw new DesignerException(t.getLocalizedMessage());
		}

		return entities;
	}

	/**
	 * Obtiene la información de la entidad.
	 * @param ctentity Información de la entidad en el catálogo.
	 * @param resources Recursos de la entidad.
	 * @return Información de la entidad.
	 * @throws ISPACException si ocurre algún error.
	 */
	private Entidad getEntidad(IItem ctentity, EntityResources resources) 
			throws ISPACException {

		Entidad entidad = new Entidad();
	//	entidad.setId(Integer.parseInt(ctentity.get("CTENTITY:ID").toString()));
		entidad.setNombre(ctentity.get("CTENTITY:NOMBRE").toString());
		entidad.setDescripcion(resources.getResources(entidad.getNombre()));
		entidad.setCampos(getCamposEntidad(ctentity, resources));

		return entidad;
	}
	
	/**
	 * Obtiene los campos de una entidad.
	 * @param ctentity Información de la entidad en el catálogo.
	 * @param resources Recursos de la entidad.
	 * @return Mapa de campos de la entidad.
	 * @throws ISPACException si ocurre algún error.
	 */
	private Map getCamposEntidad(IItem ctentity, EntityResources resources) 
			throws ISPACException {

		Map campos = new HashMap();
		
		String definition = ctentity.getString("CTENTITY:DEFINICION");
		if (StringUtils.isNotBlank(definition)) {
			EntityDef entityDef = EntityDef.parseEntityDef(definition);
			if (entityDef != null) {
				List fields = entityDef.getFields();
				for (int i = 0; i < fields.size(); i++) {
					EntityField field = (EntityField) fields.get(i);
					if (field != null) {
						CampoEntidad campo = new CampoEntidad();
						campo.setId(field.getId());
						campo.setNombre(field.getPhysicalName());
						campo.setTipo(field.getType().getId());
						campo.setDescripcion(resources.getResources(campo.getNombre()));
						
						campos.put(String.valueOf(campo.getNombre()), campo);
					}
				}
			}
		}
		
		return campos;
	}
	
	/**
	 * Obtiene los eventos asignables a un tipo de objeto.
	 * @param objType Tipo de objeto.
	 * @return Mapa de eventos.
	 * @throws DesignerException si ocurre algún error.
	 */
	public Map getEvents(int objType) throws DesignerException {
		Map events = new HashMap();
		
		try {
			List eventsList = DescriptionsPEvents.getDescEventsList(objType);
			if (eventsList != null) {
				for (int i = 0; i < eventsList.size(); i++) {
					DescEventsBean bean = (DescEventsBean) eventsList.get(i);
					events.put(bean.getProperty("ID"), bean.getProperty("DESCRIPCION"));
				}
			}
		} catch (Throwable t) {
			logger.error("Error al obtener los eventos", t);
			throw new DesignerException(t.getLocalizedMessage());
		}
		
		return events;
	}
	
	/**
	 * Obtiene la lista de reglas del catálogo.
	 * @return Lista de reglas del catálogo.
	 * @throws DesignerException si ocurre algún error.
	 */
	public Map getCTRules() throws DesignerException {
		Map ctRulesMap = new HashMap();
		
		try {
			List ctrules = getCTRules(getSessionAPI());
			for (int i = 0; i < ctrules.size(); i++) {
				Regla regla = (Regla) ctrules.get(i);
				ctRulesMap.put(String.valueOf(regla.getId()), regla);
			}
		} catch (Throwable t) {
			logger.error("Error al obtener las reglas del catálogo", t);
			throw new DesignerException(t.getLocalizedMessage());
		}
		
		return ctRulesMap;
	}

	/**
	 * Obtiene las reglas del catálogo.
	 * @param sessionAPI API de sesión de iSPAC.
	 * @return Lista de reglas del catálogo.
	 * @throws ISPACException si ocurre algún error.
	 */
	protected List getCTRules(ISessionAPI sessionAPI) throws ISPACException {

		List rules = new ArrayList();
		
		ICatalogAPI catalogAPI = sessionAPI.getAPI().getCatalogAPI();

		// Lista de reglas del catálogo
		IItemCollection ctrules = catalogAPI.getCTRules();
		while (ctrules.next()) {
			IItem ctrule = ctrules.value();
			rules.add(new Regla(
					ctrule.getKeyInt(),
					ctrule.getString("NOMBRE"),
					ctrule.getString("DESCRIPCION"),
					ctrule.getString("CLASE"),
					ctrule.getInt("TIPO")));
		}
			
		return rules;
	}

	/**
	 * Obtiene las reglas del catálogo que no estén asociadas al evento del objeto.
	 * @param objType Tipo de objeto.
	 * @param objId Identificador del objeto.
	 * @param eventCode Código de evento.
	 * @return Lista de reglas del catálogo.
	 * @throws DesignerException si ocurre algún error.
	 */
	public List getAvailableCTRules(int objType, int objId, int eventCode) 	
			throws DesignerException {
		
		List rules = new ArrayList();
		
		try {
			
			ISessionAPI sessionAPI = getSessionAPI();
			ICatalogAPI catalogAPI = sessionAPI.getAPI().getCatalogAPI();
	
			// Lista de reglas del catálogo
			List ctrules = getCTRules(sessionAPI);
	
			// Reglas asociadas al objeto
	        IItemCollection relatedRules = catalogAPI.getPRulesEvent(objType, objId, eventCode);
	        Map relatedRulesIdsMap = relatedRules.toMapStringKey("ID_REGLA");
	       
	        // Eliminar de la lista las reglas asociadas al objeto
	        for (Iterator it = ctrules.iterator(); it.hasNext(); ) {
	        	Regla regla = (Regla) it.next();
	        	if (!relatedRulesIdsMap.containsKey(String.valueOf(regla.getId()))) {
	        		rules.add(regla);
	        	}
	        }
	        
		} catch (Throwable t) {
			logger.error("Error al obtener las reglas del catálogo para el flujo", t);
			throw new DesignerException(t.getLocalizedMessage());
		}
        
		return rules;
	}

	public void removeEventRules(int objType, int objId) throws DesignerException
	{
		try {
			
			ISessionAPI sessionAPI = getSessionAPI();
			IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
			ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
			
		
			// Información de los eventos
			IItemCollection pevents = catalogAPI.getPEvents(objType, objId);
			while (pevents.next()) {
				IItem pevent = pevents.value();
				
			
				EventoId eventoId = EventoId.getInstance(EventoId.getInstance(pevent).getEventRuleId());
			catalogAPI.delPRuleEvent(eventoId.getObjType(), eventoId.getObjId(), 
					eventoId.getEventCode(), eventoId.getRuleId(), eventoId.getOrder());
			}
			
		} catch (Throwable t) {
			logger.error("Error al eliminar las reglas ", t);
			throw new DesignerException(t.getLocalizedMessage());
		}

		
		
	}
	
	/**
	 * Obtiene la lista de reglas de un objeto.
	 * @param objType Tipo de objeto.
	 * @param objId Identificador del objeto.
	 * @return Lista de reglas
	 * @throws DesignerException si ocurre algún error.
	 */
	public List getEventRules(int objType, int objId) throws DesignerException {
		
		List eventRules = new ArrayList();

		try {
			
			ISessionAPI sessionAPI = getSessionAPI();
			IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
			ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
			
			// Reglas del catálogo
			Map ctrules = getCTRules();

			// Información de los eventos
			IItemCollection pevents = catalogAPI.getPEvents(objType, objId);
			while (pevents.next()) {
				IItem pevent = pevents.value();
				Evento event = new Evento();
				event.setId(EventoId.getInstance(pevent).getEventRuleId());
				event.setCode(pevent.getInt("EVENTO"));
				event.setCodeDescription(DescriptionsPEvents.getDescripcionEvents(event.getCode()));
				event.setRuleId(pevent.getInt("ID_REGLA"));
				event.setOrder(pevent.getInt("ORDEN"));
				
				if (event.getRuleId() > 0) {
					event.setRegla((Regla)ctrules.get(String.valueOf(event.getRuleId())));
				} else if(event.getRuleId()==-1) {
					event.setCondition(parserCondition(pevent.getString("CONDICION")));
				}
				
				eventRules.add(event);
			}
			
		} catch (Throwable t) {
			logger.error("Error al obtener las reglas de los eventos", t);
			throw new DesignerException(t.getLocalizedMessage());
		}

		return eventRules;
	}
	
	/**
	 * Añade una regla asociada al evento de un objeto.
	 * @param objType Tipo de objeto.
	 * @param objId Identificador del objeto.
	 * @param eventCode Código de evento.
	 * @param ruleId Identificador de la regla.
	 * @param condition Información de la condición.
	 * @throws DesignerException si ocurre algún error.
	 */
	
	
	public void addEventRule(int objType, int objId, int eventCode,List rulesId, Condition condition) 
			throws DesignerException {
		
		try {
			
			
				ISessionAPI sessionAPI = getSessionAPI();
				IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
				ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
	
				if (rulesId!=null) {
					for(int i=0; i<rulesId.size(); i++){
						catalogAPI.addPRuleEvent(objType, objId, eventCode, Integer.parseInt(rulesId.get(i).toString()));
					}
				} else if (condition != null) {
					catalogAPI.addPConditionEvent(objType, objId, eventCode, toXml(condition));
				}
			
		} catch (Throwable t) {
			logger.error("Error al añadir un evento", t);
			throw new DesignerException(t.getLocalizedMessage());
		}
	}

	/**
	 * Modifica la condición asociada al evento de un objeto.
	 * @param eventRuleId Identificador de la regla del evento.
	 * @param condition Condición.
	 * @throws DesignerException si ocurre algún error.
	 */
	public void updateEventRuleCondition(String eventRuleId, Condition condition) 
			throws DesignerException {
		
		try {
			
			if (condition != null) {
				ISessionAPI sessionAPI = getSessionAPI();
				IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
				ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
				
				
				EventoId eventoId = EventoId.getInstance(eventRuleId); 
				IItem event = catalogAPI.getPRuleEvent(eventoId.getObjType(), eventoId.getObjId(), 
						eventoId.getEventCode(), eventoId.getRuleId(), eventoId.getOrder());
				event.set("CONDICION", toXml(condition));
				event.store(sessionAPI.getClientContext());
			}
		} catch (Throwable t) {
			logger.error("Error al modificar la condición de un evento", t);
			throw new DesignerException(t.getLocalizedMessage());
		}
	}

	/**
	 * Elimina un listado de reglas , cada una asociado a un evento
	 * @param eventRuleId Lista de  Identificadores de la regla del evento.
	 * @throws DesignerException si ocurre algún error.
	 */
	public void removeEventsRules(List eventRuleIds) throws DesignerException {
		
		try {
			
			ISessionAPI sessionAPI = getSessionAPI();
			IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
			ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
            for(int i=0; i<eventRuleIds.size(); i++){
			EventoId eventoId = EventoId.getInstance(eventRuleIds.get(i).toString());
			catalogAPI.delPRuleEvent(eventoId.getObjType(), eventoId.getObjId(), 
					eventoId.getEventCode(), eventoId.getRuleId(), eventoId.getOrder());
            }

		} catch (Throwable t) {
			logger.error("Error al eliminar la regla del evento", t);
			throw new DesignerException(t.getLocalizedMessage());
		}
	}

	/**
	 * Incrementa el orden de las reglas asociadas a eventos de un objeto.
	 * @param eventRuleIds Identificadores de los eventos.
	 * @throws DesignerException si ocurre algún error.
	 */
	public void incOrderEventRules(String[] eventRuleIds) throws DesignerException {
		try {

			if (!ArrayUtils.isEmpty(eventRuleIds)) {
				
				ISessionAPI sessionAPI = getSessionAPI();
				IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
				ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
	
				for (int i = 0; i < eventRuleIds.length; i++) {
					EventoId eventoId = EventoId.getInstance(eventRuleIds[i]);
					catalogAPI.incOrderPEvents(eventoId.getObjType(), eventoId.getObjId(), 
							eventoId.getEventCode(), eventoId.getRuleId(), eventoId.getOrder());
				}
			}

		} catch (Throwable t) {
			logger.error("Error al incrementar el orden de las reglas", t);
			throw new DesignerException(t.getLocalizedMessage());
		}

	}

	/**
	 * Decrementa el orden de las reglas asociadas a eventos de un objeto.
	 * @param eventRuleIds Identificadores de los eventos.
	 * @throws DesignerException si ocurre algún error.
	 */
	public void decOrderEventRules(String[] eventRuleIds) throws DesignerException {
		try {

			if (!ArrayUtils.isEmpty(eventRuleIds)) {

				ISessionAPI sessionAPI = getSessionAPI();
				IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
				ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

				for (int i = eventRuleIds.length - 1; i >= 0; i--) {
					EventoId eventoId = EventoId.getInstance(eventRuleIds[i]);
					catalogAPI.decOrderPEvents(eventoId.getObjType(), eventoId
							.getObjId(), eventoId.getEventCode(), eventoId
							.getRuleId(), eventoId.getOrder());
				}
			}

		} catch (Throwable t) {
			logger.error("Error al decrementar el orden de las reglas", t);
			throw new DesignerException(t.getLocalizedMessage());
		}

	}
	
	/**
	 * 
	 * @param date
	 * @return
	 * @throws DesignerException
	 */
	public  String getSQLDate(String date) throws DesignerException {
		ClientContext context = null;
		DbCnt cnt = null;
		
		try {
			context = getSessionAPI().getClientContext();
			cnt = context.getConnection();
			return DBUtil.getToDateByBD(cnt, date);
		} catch (Throwable t) {
			logger.error("Error al obtener la fecha para la SQL", t);
			throw new DesignerException(t.getLocalizedMessage());
		} finally {
			if (context != null) {
				context.releaseConnection(cnt);
			}
		}
	}
	
	public int loadTypeOper(String nombreEntidad, String nombreCampo) throws DesignerException{
		
		try{
			ISessionAPI sessionAPI = getSessionAPI();
			IInvesflowAPI invesFlowAPI = sessionAPI.getAPI();
			IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
			
			Property property=entitiesAPI.getEntityFieldProperty(nombreEntidad, nombreCampo.toUpperCase());
			return property.getType();
		} catch (Throwable t) {
			logger.error("Error al obtener el campo "+nombreCampo+" de la entidad "+nombreEntidad, t);
			throw new DesignerException(t.getLocalizedMessage());
		}
	}
	
	
	public  String toXml(Condition cond) throws ISPACException, DesignerException {
	    	
	        String sXml = null;
	        StringBuffer buffer = new StringBuffer();
	        StringBuffer bufferConditions = new StringBuffer();
	        StringBuffer select, from , where;
	        
	        select= new StringBuffer();
	        from=   new StringBuffer();
	        where=  new StringBuffer();
	        
	   
	        
	        
	        select.append("SELECT COUNT(*) ");
	        from.append("FROM ");
	        where.append(" WHERE (");
	        List entidades= new ArrayList();
	        buffer.append(XmlTag.newTag("name",cond.getNombre()));
		      
	        if(cond.getCondicionesSimples()!=null)
	        {
	        	for(int i=0; i<cond.getCondicionesSimples().size(); i++){
	        		
	        		ConditionSimple conds= (ConditionSimple) cond.getCondicionesSimples().get(i);
	        		
	        		int tipoOperando1=loadTypeOper(conds.getOp1().getNombreEntidad(), conds.getOp1().getNombreCampo());
	        		StringBuffer bufferCondSimple = new StringBuffer();
	        		bufferCondSimple.append(XmlTag.newTag("type", conds.getAndOr()));
	        		where.append(" ");
	        		where.append(conds.getAndOr());
	        		if(conds.getParentesisOpen()==1)
	        		where.append("(");
	        		bufferCondSimple.append(XmlTag.newTag("parenthesisOpen", conds.getParentesisOpen()));
	        		bufferCondSimple.append(XmlTag.newTag("parenthesisClose", conds.getParentesisClose()));
	        		if(Html.contains(conds.getOperador(), "NULL") ||Html.contains(conds.getOperador(), "null") )
	        			bufferCondSimple.append(XmlTag.newTag("operator", XmlTag.newCDATA(conds.getOperador())));
	        		bufferCondSimple.append(XmlTag.newTag("operator",XmlTag.newCDATA(conds.getOperador())));
	        	
	        		
	        	   StringBuffer bufferOperando1=new StringBuffer();
	        	   StringBuffer bufferContenidoOP=new StringBuffer();
	        	
	        	  // bufferContenidoOP.append(XmlTag.newTag("idEntidad", conds.getOp1().getIdEntidad()));
	        	   bufferContenidoOP.append(XmlTag.newTag("nombreEntidad", conds.getOp1().getNombreEntidad()));
	        	   bufferContenidoOP.append(XmlTag.newTag("nombreCampo", conds.getOp1().getNombreCampo()));
	        	   where.append(" ");
	        	   if(i!=0 && !Html.contains(from.substring(0), conds.getOp1().getNombreEntidad())){
	        		  entidades.add(conds.getOp1().getNombreEntidad());
	        	     from.append(" ,");
	        	     from.append(conds.getOp1().getNombreEntidad());
	        	    
	        	   }
	        	   else  if(!Html.contains(from.substring(0), conds.getOp1().getNombreEntidad())){
	        		   entidades.add(conds.getOp1().getNombreEntidad());
	        		   from.append(conds.getOp1().getNombreEntidad());
	        	   }
	        	   where.append(conds.getOp1().getNombreEntidad());
	        	   where.append(".");
	        	   where.append(conds.getOp1().getNombreCampo());
	        	   where.append(" ");
	        		if(Html.contains(conds.getOperador(), "NULL") ||Html.contains(conds.getOperador(), "null") )
	        			where.append(" is ");
	        	   where.append(conds.getOperador());

	        	   bufferOperando1.append(XmlTag.newTag("op1", bufferContenidoOP));
	        	   
	        	   bufferContenidoOP=new StringBuffer();
	        	   StringBuffer bufferOperando2=new StringBuffer();
	        	   
	        	   if(conds.getOp2()!=null && conds.getOp2().getNombreEntidad()!=null && conds.getOp2().getNombreEntidad().compareTo("")!=0){
	        	   //bufferContenidoOP.append(XmlTag.newTag("idEntidad", conds.getOp2().getIdEntidad()));
	        	  
	        	   if(!Html.contains(from.substring(0), conds.getOp2().getNombreEntidad())){
	 	        		  entidades.add(conds.getOp2().getNombreEntidad());
	 	        	     from.append(" ,"+conds.getOp2().getNombreEntidad());
	 	        	    
	 	        	}	   
	        		   
	        	   bufferContenidoOP.append(XmlTag.newTag("nombreEntidad", conds.getOp2().getNombreEntidad()));
	        	   bufferContenidoOP.append(XmlTag.newTag("nombreCampo", conds.getOp2().getNombreCampo()));
	        	   where.append(" ");
	        	   where.append(conds.getOp2().getNombreEntidad());
	        	   where.append(".");
	        	   where.append(conds.getOp2().getNombreCampo());
	        	  
	        	 
	        	   
	        	   }
	        	   else if (conds.getOp2()!=null && conds.getOp2().getValor()!=null &&  conds.getOp2().getValor().compareTo("")!=0){
	        	   bufferContenidoOP.append(XmlTag.newTag("valor", XmlTag.newCDATA(conds.getOp2().getValor()))); 
	        	   where.append(" ");
	        	  Object valorFormateado=formatToSql(conds.getOp2().getValor(),tipoOperando1);
	        	  if(valorFormateado!=null)
	        	   {
						where.append(valorFormateado);
	        	   }
	        	   else
	        	   where.append(conds.getOp2().getValor());
	        		   
	        	   }

	        	   bufferOperando2.append(XmlTag.newTag("op2", bufferContenidoOP));
	        	   
	        	   bufferContenidoOP=new StringBuffer();
	        	   StringBuffer bufferOperando3=new StringBuffer();
	        	   
	        	   if(conds.getOp3()!=null && conds.getOp3().getNombreEntidad()!=null && conds.getOp3().getNombreEntidad().compareTo("")!=0){
		        	
	        		   if(!Html.contains(from.substring(0), conds.getOp3().getNombreEntidad())){
		 	        		  entidades.add(conds.getOp3().getNombreEntidad());
		 	        		  from.append(" ,"+conds.getOp3().getNombreEntidad());
		 	        	    
		 	        	}	
		        	   bufferContenidoOP.append(XmlTag.newTag("nombreEntidad", conds.getOp3().getNombreEntidad()));
		        	   bufferContenidoOP.append(XmlTag.newTag("nombreCampo", conds.getOp3().getNombreCampo()));
		        	   where.append(" AND ");
		        	   where.append(conds.getOp3().getNombreEntidad());
		        	   where.append(".");
		        	   where.append(conds.getOp3().getNombreCampo());
		        	   }
		        	   else if (conds.getOp3()!=null &&  conds.getOp3().getValor()!=null &&  conds.getOp3().getValor().compareTo("")!=0){
		        	   bufferContenidoOP.append(XmlTag.newTag("valor", XmlTag.newCDATA(conds.getOp3().getValor()))); 
		        	   where.append(" AND ");
		        	   Object valorFormateado=formatToSql(conds.getOp3().getValor(),tipoOperando1);
			        	  if(valorFormateado!=null)
			        	   {
								where.append(valorFormateado);
			        	   }
		        	  else
		        		  where.append(conds.getOp2().getValor());
		        		   
		        	   
		        	   }
	        	   bufferOperando2.append(XmlTag.newTag("op3", bufferContenidoOP));
	        	
	        	bufferConditions.append(XmlTag.newTag("simpleCondition", 
	        			(((bufferCondSimple.append(bufferOperando1)).append(bufferOperando2)).append(bufferOperando3))));
	        	if(conds.getParentesisClose()==1)
	        		where.append(")");
	        
	        	}
	        	
	        }
	        buffer.append(XmlTag.newTag("conditions", bufferConditions));
        	
	      
	        
	        //A la sql tengo que forzar los inner join y que se igualen a un parametro numexp , ya que este parametro
	        //no se conoce en el catalogo.
	        if(entidades.size()>0){
	        String primera=(String) entidades.get(0);
	        where.append(")");
	       for(int i=1; i< entidades.size(); i++)
	       {
	    	   where.append(" and " + entidades.get(i) + ".numExp="+primera+".numExp");
	       }
	       where.append(" and "+ primera+".numExp='${NUMEXP}'");
	        }
	      
	        
	         buffer.append(XmlTag.newTag("sql",XmlTag.newCDATA(((select.append(from)).append(where)).toString())));	
	        sXml=XmlTag.getXmlInstruction("UTF-8")+ XmlTag.newTag("condition",buffer.toString());
	        return sXml;
	    }
		

	
	public  Object formatToSql(String valor, int tipoOperando1) throws DesignerException {
			
     Object res=null;

		try{
		if (Util.isDate(valor))
			return  getSQLDate(valor);
	
		if(StringUtils.contains(valor, "'"))
			valor=DBUtil.replaceQuotes(valor);
		if(StringUtils.isAlpha(valor) || StringUtils.isAlphanumericSpace(valor)|| tipoOperando1==varDefs.SHORTTEXT || tipoOperando1==varDefs.LONGTEXT){
			return "'"+ valor+ "'";
		}
		else if(StringUtils.isNumeric(valor))
			return valor;
		else if(StringUtils.containsOnly(valor , "0123456789.,"))
			{ 
				
		
			Locale locale = new  Locale("es","","");
			DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(locale);
			df.setDecimalSeparatorAlwaysShown(true);
			df.setMaximumIntegerDigits(100);
		
			try {
				return  df.parse(valor);
			
			} catch (ParseException e) {
				// TODO Auto-generated catch block
			return null;
			}
			
			//}
			}
		}  catch (DesignerException e) {
			// TODO Auto-generated catch block
			logger.error("Error al formatear el dato", e);
			throw new DesignerException(e.getLocalizedMessage());
		}	
			
		return res;
		
	}
}
