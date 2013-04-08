package ieci.tdw.ispac.deadline;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.ispaclib.XMLUtil.XMLDocUtil;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PPlazoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PRelPlazoDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

import java.util.Date;

import org.w3c.dom.Document;


public class DeadlineManager
{
	public static final int OBJ_PROCEDURE 		= 1;
	public static final int OBJ_STAGE		 	= 2;
	public static final int OBJ_TASK		 	= 3;
	
	ClientContext mcctx;
	EventManager meventmgr;

	
	public DeadlineManager(ClientContext cctx,EventManager eventmgr)
	{
		super();
		mcctx=cctx;
		meventmgr=eventmgr;
	}
	
	
	/**
	 * Recalcula el deadline pero en lugar de utilizar el basedate y el idCalendario definido
	 * en el XML de plazos coge los valores de los campos FECHA_INICIO_PLAZO, ID_CALENDARIO para la fecha base y el calendario
	 * @param typeObj
	 * @param nIdObj: id del objeto activo : SPAC_PROCESOS, SPAC_FASE, SPAC_TRAMITES
	 * @return
	 */
	public Deadline recalcDeadline(int typeObj, int nIdObj) throws ISPACException {

		//obtenemos el calendario y la fecha base 
		Date basedate = null;
		int idCalendario=-1;
		String deadlineXML = null;
		DbCnt cnt = null;
		try{
		
			cnt = mcctx.getConnection();
			if (typeObj == PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE){
				IProcess process = mcctx.getAPI().getProcess(nIdObj);
				idCalendario = process.getInt("ID_CALENDARIO");
				basedate = process.getDate("FECHA_INICIO_PLAZO");
				deadlineXML = process.getDeadlineXML(cnt);
				
			}else if (typeObj == PRelPlazoDAO.DEADLINE_OBJ_STAGE){
				 IStage stage = mcctx.getAPI().getStage(nIdObj);
				 idCalendario = getIdCalendario(stage, typeObj, nIdObj);
				 basedate = stage.getDate("FECHA_INICIO_PLAZO");
				 deadlineXML = stage.getDeadlineXML(cnt);
				 
			}else if (typeObj == PRelPlazoDAO.DEADLINE_OBJ_TASK){
				 ITask task = mcctx.getAPI().getTask(nIdObj);
				 idCalendario = getIdCalendario(task, typeObj, nIdObj);
				 basedate = task.getDate("FECHA_INICIO_PLAZO");
				 deadlineXML = task.getDeadlineXML(cnt);
			}
			
			if (idCalendario<=0){
				throw new ISPACException("El objeto no tiene establecido un calendario");
			}
			if (basedate == null) {
				throw new ISPACException("El objeto no tiene establecido FECHA_INICIO_PLAZO");
			}
			if (deadlineXML==null){
				throw new ISPACException("El objeto no tiene establecido un XML DE PLAZO");
			}
		    // obtener una factoria de plazos sobre el calendario obtenido
		    DeadlineFactory deadlinefactory=new DeadlineFactory(mcctx, idCalendario);
		    Deadline deadline = deadlinefactory.createDeadline(XMLDocUtil.newDocument(deadlineXML), basedate);
			return deadline;
		
		}
		finally{
			mcctx.releaseConnection(cnt);
		}
	}
	
	/**
	 * Devuelve el idCalendario del objeto, en caso de no tener retorna el del proceso del expediente
	 * @param taskOrStageObject
	 * @param tipoObjeto
	 * @param idObjeto
	 * @return
	 * @throws ISPACException
	 */
	int getIdCalendario(IItem taskOrStageObject, int tipoObjeto, int idObjeto) throws ISPACException{
		int idCalendario = taskOrStageObject.getInt("ID_CALENDARIO");
		if (idCalendario <= 0) {
			String numExp = taskOrStageObject.getString("NUMEXP");
			IProcess process = mcctx.getAPI().getProcess(numExp);
			idCalendario = process.getInt("ID_CALENDARIO");
		}
		return idCalendario;
	}
	
//	String getDeadlineXML(DbCnt cnt, String deadlineXMLObject, IItem taskOrStageObject) throws ISPACException{
//		if (deadlineXMLObject==null || deadlineXMLObject.length()==0){
//			String numExp = taskOrStageObject.getString("NUMEXP");
//			IProcess process = mcctx.getAPI().getProcess(numExp);
//			return process.getDeadlineXML(cnt);
//		}
//		return deadlineXMLObject;
//	}
	
	/**
	 * @param numexp
	 * @param typeObj
	 * @param nIdObjPCD: id del objeto asignado al procedimiento dentro del catalogo: SPAC_P_FASES,SPAC_P_TRAMITES
	 * 
	 * @return
	 * @throws ISPACException
	 */
	public Deadline calcDeadline(String numexp, int typeObj, int p_IdObj) throws ISPACException {
		DbCnt cnt = null;
		try{
			cnt = mcctx.getConnection();
		CollectionDAO deadlines = PPlazoDAO.getDeadline(cnt, typeObj, p_IdObj);

		if (!deadlines.next())
			return null;

		PPlazoDAO deadline = (PPlazoDAO) deadlines.value();
		return calcDeadline(deadline.getString("PLAZO"), numexp, typeObj, p_IdObj);
		}
		finally{
			mcctx.releaseConnection(cnt);
		}
	}

	private Deadline calcDeadline(String deadlineXML, String numexp, int tipoObjeto, int nIdObjPCD) throws ISPACException {
		return calcDeadline(XMLDocUtil.newDocument(deadlineXML), numexp, tipoObjeto, nIdObjPCD);
	}
	
	private Deadline calcDeadline(Document deadlineXML, String numexp, int tipoObjeto, int nIdObjPCD) throws ISPACException {
		Integer idCalendario = null;

		if (tipoObjeto==PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE){
			DeadlineCalendar calendardeadline = new DeadlineCalendar(mcctx, meventmgr, deadlineXML,
					numexp);
			idCalendario = calendardeadline.getCalendarId();

		}else{
			//cogemos el calendario q tenga establecido el procedimiento que en la definicion
			//XML del plazo para fases y tramites no tiene el calendario
			
			//if (tipoObjeto==PRelPlazoDAO.DEADLINE_OBJ_STAGE){
			//	object = mcctx.getAPI().getProcedureStage(nIdObjPCD);
			//}else if (tipoObjeto==PRelPlazoDAO.DEADLINE_OBJ_TASK){
			//	object = mcctx.getAPI().getProcedureTaskPCD(nIdObjPCD);
			//}
			//idCalendario = new Integer(object.getInt("ID_CALENDARIO"));
			//if (idCalendario == null || idCalendario.intValue() <= 0) {
				//cogemos el calendario del proceso al que pertenece la p_fase o el p_tramite
				IProcess process = mcctx.getAPI().getProcess(numexp);//TODO REVISAR SI SE METEN SUBPROCESOS
				idCalendario = new Integer(process.getInt("ID_CALENDARIO"));
			//}
		}
		
		//si no hay calendario (puede estar definido en el XML de plazo del proced) se establece el calendario por defecto
		if (idCalendario == null) {
			//si no se coge el calendario por defecto
			String stringIdCalendario = ConfigurationMgr.getVarGlobal(mcctx,
					ConfigurationMgr.DEFAULT_CALENDAR_ID);
			if (stringIdCalendario != null)
				idCalendario = new Integer(stringIdCalendario);
		}

		//creacion de deadline
		DeadlineFactory deadlinefactory = new DeadlineFactory(mcctx, idCalendario.intValue());
		Deadline deadline = deadlinefactory.createDeadline(mcctx, meventmgr, deadlineXML, numexp);

		return deadline;
	}

}
