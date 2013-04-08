/*
 * Created on 16-jun-2004
 *
 */
package ieci.tdw.ispac.ispactx;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.deadline.Deadline;
import ieci.tdw.ispac.deadline.DeadlineManager;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PNumExpDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PProcedimientoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PSincNodoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PTramiteDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXSincNodoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramiteDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author   juanin
 */
public class TXDAOGen
{

	private final ClientContext mccxt;
	private final EventManager meventmgr;
	private final DeadlineManager mdeadlinemgr;
	protected Logger logger = Logger.getLogger(TXDAOGen.class);
	
	private final String VARIABLE_DELIMIT = "$";
	private final String COUNTER_PREFIX = "{";
	private final String COUNTER_SUFIX = "}";
	private final String VARIABLE_PROCEDURE = "PR";
	private final String VARIABLE_YEAR = "YR"; 
	private final String VARIABLE_NUM_EXPEDIENT = "NM";
		
	
	public TXDAOGen(ClientContext ccxt,EventManager eventmgr)
	{
		mccxt=ccxt;
		meventmgr=eventmgr;
		mdeadlinemgr=new DeadlineManager(mccxt,meventmgr);
	}

	public EventManager getEventManager()
	{
		return meventmgr;
	}

	public void setDefaultResp(ObjectDAO obj,String sRespId)
	throws ISPACException
	{
		String respid=obj.getString("ID_RESP");
		if (respid == null || respid.length()==0 )
		{
			obj.set("ID_RESP",sRespId);
		}
	}

	public void instance(PProcedimientoDAO mproc, TXProcesoDAO process, String codProcedure, String numexpuser, int type)
	throws ISPACException
	{
		process.set("ID_PCD",mproc.getKeyInt());
		process.set("ESTADO",TXConstants.STATUS_OPEN);
		process.set("FECHA_INICIO",new Date());

		process.set("FECHA_CIERRE",(Date)null);
//		process.set("ID_RESP",mproc.getString("ID_RESP"));
//		process.set("ID_RESP_SEC",mproc.getString("ID_RESP_SEC"));

		process.set("TIPO", type);
		calculateNumExp(mproc, process, codProcedure, numexpuser);


//EL CALCULO DEL RESPONSABLE SE HARA PREVIO A LA INSTANCIACION DEL PROCESO
//Responsable por defecto del expediente
//		meventmgr.newContext();
//		meventmgr.getRuleContextBuilder().addContext(process);
//		List resplist=meventmgr.processEvents(EventsDefines.EVENT_OBJ_PROCEDURE,
//		        							mproc.getKeyInt(),
//											EventsDefines.EVENT_EXEC_RESPCALC);
//		if (resplist!=null && resplist.size()>0)
//		{
//		    process.set("ID_RESP",(String)resplist.get(0));
//		}
//		setDefaultResp(process,mccxt.getRespId());

		// Se construye el contexto de ejecución de scripts por si el gestor de
		// fechas
		// necesita ejecutar una regla para obtener la fecha base.
		meventmgr.newContext();
		meventmgr.getRuleContextBuilder().addContext(process);
		Deadline deadline = mdeadlinemgr.calcDeadline(process.getString("NUMEXP"),
				DeadlineManager.OBJ_PROCEDURE, mproc.getKeyInt());
		Date datedeadline = null;
		if (deadline != null) {
			datedeadline = deadline.getFinalDate();
			process.set("FECHA_LIMITE", datedeadline);
			//estado del plazo activo
			process.set("ESTADO_PLAZO", 1);
			Date datebasedate = deadline.getBaseDate();
			process.set("FECHA_INICIO_PLAZO", datebasedate);
			process.set("ID_CALENDARIO", deadline.getIdCalendario());

			// numero de dias naturales del plazo
			if (datedeadline != null)
				process.set("NUM_DIAS_PLAZO", calculateDays(datebasedate, datedeadline));
		} else {
			//exped.set("FECHA_LIMITE", (Object) null);
			//establecemos el calendario del proceso para que lo pueda utilizar la fase y el tramite
			process.set("ID_CALENDARIO", ConfigurationMgr.getVarGlobal(mccxt, ConfigurationMgr.DEFAULT_CALENDAR_ID));
		
		}

		

		// Necesario para poder acceder al objeto recien creado por la
		// transacción
		// desde las reglas
		process.store(mccxt);
	}
	
	
	private void calculateNumExp(PProcedimientoDAO mproc,TXProcesoDAO process, String codProcedure, String numexpuser) throws ISPACException{
		if (numexpuser!=null && numexpuser.length()>0)
		{
		    //Se ha proporcionado número de expediente
		    process.set("NUMEXP",numexpuser);
		}
		else
		{
			DbCnt cnt = mccxt.getConnection();
			
			//exped.set("NUMEXP","EXP/"+exped.getKeyInt());
			String numexp = null;
			try{
				int year = DateUtil.getCurrentYear();
				int inumexp = 1;
				String defaultFormat = ISPACConfiguration.getInstance().get(ISPACConfiguration.FORMAT_NUM_EXP);
				
				PNumExpDAO pnumexpDAO = null;
				
				// Tipo de contador de expedientes
				String typeCounter = ISPACConfiguration.getInstance().get(ISPACConfiguration.EXPEDIENT_COUNTER_TYPE);
				if (StringUtils.equalsIgnoreCase(typeCounter, ISPACConfiguration.EXPEDIENT_COUNTER_TYPE_PROCEDURE)) {
					
					// Contador por procedimiento, luego en la máscara es obligatorio $PR$
					if (defaultFormat.indexOf(VARIABLE_DELIMIT + VARIABLE_PROCEDURE + VARIABLE_DELIMIT) == -1) {
						throw new ISPACInfo("exception.expedients.counterProcedure.noPR");
					}
						
					//Trabajamos con el id_grupo en lugar del id_pcd para poder compartir el contador para distintas versiones del mismo procedimiento.
					//Las distintas versiones de un procedimiento tienen en comun el id_group que es el identificador del procedimiento con version 1.
					pnumexpDAO = (PNumExpDAO)PNumExpDAO.getCounterByPcd(cnt, mproc.getInt("ID_GROUP"));
					if (pnumexpDAO == null){
						pnumexpDAO = new PNumExpDAO(cnt);
						pnumexpDAO.createNew(cnt);
						pnumexpDAO.set("ANIO", year);
						pnumexpDAO.set("FORMATO", defaultFormat);
						pnumexpDAO.set("ID_PCD",mproc.getInt("ID_GROUP"));
						pnumexpDAO.set("CONTADOR",1);
					}
					numexp = pnumexpDAO.getFormato();
					
				}else{
					// Obtener el siguiente número de expediente para el organismo tramitador
					pnumexpDAO = PNumExpDAO.getDefaultCounter(cnt);
					
					// Formato para el número de expediente
					numexp = pnumexpDAO.getFormato();
					if (StringUtils.isEmpty(numexp)) {
						numexp = defaultFormat;
					}
				}
				
				// Año y número de expediente
				if (year == Integer.parseInt(pnumexpDAO.getAnio())) {
					
					inumexp = pnumexpDAO.getContador();
				}
				else {
					// Actualizar el año
					pnumexpDAO.set("ANIO", year);
					
					// Primer expediente
					inumexp = 1;
				}
				
				// Actualizar el siguiente número de expediente
				pnumexpDAO.set("CONTADOR", inumexp + 1);
				
				pnumexpDAO.store(cnt);
				
			
				numexp = StringUtils.replace(numexp, VARIABLE_DELIMIT + VARIABLE_PROCEDURE + VARIABLE_DELIMIT , codProcedure);
				numexp = StringUtils.replace(numexp, VARIABLE_DELIMIT + VARIABLE_YEAR + VARIABLE_DELIMIT, String.valueOf(year));
				numexp = replaceVariable(numexp, VARIABLE_NUM_EXPEDIENT, inumexp);
				
			}
			catch(ISPACInfo e) {
				throw e;
			}
			catch(ISPACException e) {
				logger.warn("Fallo al generar el Nº de Expediente", e);
			}
			
			if (numexp == null) {
				numexp = "EXP/" + process.getKeyInt();
			}
			
			// Establecer el número de expediente
			process.set("NUMEXP", numexp);
			
			meventmgr.newContext();
			meventmgr.getRuleContextBuilder().addContext(process);
			List numexplist=meventmgr.processEvents(EventsDefines.EVENT_OBJ_PROCEDURE,
			        								mproc.getKeyInt(),
			        								EventsDefines.EVENT_EXEC_NUMEXPCALC);

			if (numexplist!=null && numexplist.size()>0)
			{
			    numexp = (String)numexplist.get(0);
			    if (numexp != null) {
			        process.set("NUMEXP", numexp);
			    }
			}
		}		
		
	}
	
	private String replaceVariable (String numexp, String variable, int substitute){
		int posIni = StringUtils.indexOf(numexp, VARIABLE_DELIMIT +variable);
		int posFin = StringUtils.indexOf(numexp, VARIABLE_DELIMIT, posIni +1); 
		if ((posFin - posIni -  ((VARIABLE_DELIMIT+variable).length()) )> 0 ){
			posIni = StringUtils.indexOf(numexp, COUNTER_PREFIX);
			posFin = StringUtils.indexOf(numexp, COUNTER_SUFIX);
			String repeat = StringUtils.substring(numexp, posIni+1, posFin);
			String format = "";
			for (int i = 0; i< Integer.parseInt(repeat); i++)
				format += "0";
			NumberFormat formatter = new DecimalFormat(format);
			return StringUtils.replace(numexp, VARIABLE_DELIMIT + variable + COUNTER_PREFIX + repeat + COUNTER_SUFIX + VARIABLE_DELIMIT, formatter.format(substitute));
		}
		return StringUtils.replace(numexp, VARIABLE_DELIMIT + variable + VARIABLE_DELIMIT, String.valueOf(substitute));
	}
	

	private int calculateDays(Date initialDate, Date finalDate){
		long diffMillis = finalDate.getTime() - initialDate.getTime();
		long diffDays = diffMillis / (1000 * 60 * 60 * 24);
		return new Long(diffDays).intValue();
	}
	
	public void instance(PFaseDAO mpStage,TXFaseDAO stage,TXProcesoDAO process,String nodeRespId)
	throws ISPACException
	{
		stage.set("ID_EXP",process.getKeyInt());
		stage.set("ID_PCD",process.getInt("ID_PCD"));
		stage.set("ID_FASE",mpStage.getKeyInt());
		stage.set("NUMEXP",process.getString("NUMEXP"));
		stage.set("FECHA_INICIO",new Date());
		stage.set("ID_RESP", nodeRespId);
		stage.set("ESTADO",TXConstants.STATUS_OPEN);

		int type = IStage.STAGE_TYPE;
		int eventObjectType = EventsDefines.EVENT_OBJ_STAGE;
		if (process.isSubProcess()) {
			type = IStage.ACTIVITY_TYPE;
			eventObjectType = EventsDefines.EVENT_OBJ_ACTIVITY;
		}
		stage.set("TIPO", type);
			
		
//		stage.set("ID_RESP",mpStage.getString("ID_RESP"));
//		stage.set("ID_RESP_SEC",mpStage.getString("ID_RESP_SEC"));
//		Calcular responsable de la fase.
//		meventmgr.newContext();
//		meventmgr.getRuleContextBuilder().addContext(stage);
//		List resplist=meventmgr.processEvents(eventObjectType,
//											mpStage.getKeyInt(),
//											EventsDefines.EVENT_EXEC_RESPCALC);
//
//		if (resplist!=null && resplist.size()>0)
//		{
//			stage.set("ID_RESP",(String)resplist.get(0));
//		}
//		setDefaultResp(stage,process.getString("ID_RESP"));
//		setDefaultResp(stage,mccxt.getRespId());

		

		//Calcular fecha base.
		Deadline deadline = mdeadlinemgr.calcDeadline(process.getString("NUMEXP"),
				DeadlineManager.OBJ_STAGE,mpStage.getKeyInt());
		Date datedeadline = null;
		if (deadline != null) {
			datedeadline = deadline.getFinalDate();
			stage.set("FECHA_LIMITE", datedeadline);
			//estado del plazo activo
			stage.set("ESTADO_PLAZO", 1);
			Date datebasedate = deadline.getBaseDate();
			stage.set("FECHA_INICIO_PLAZO", datebasedate);

			// numero de dias naturales del plazo
			if (datedeadline != null)
				stage.set("NUM_DIAS_PLAZO", calculateDays(datebasedate, datedeadline));
			
			
//			Date datebasedate = deadline.getBaseDate();
//			stage.set("ESTADO_PLAZO", 1);
//			stage.set("FECHA_INICIO_PLAZO", datebasedate);
//			datedeadline = deadline.getFinalDate();
//			stage.set("FECHA_LIMITE", datedeadline);
		};

		
        //Necesario para poder acceder al objeto recien creado por la transacción
        //desde las reglas
        stage.store(mccxt);
		
        //Se construye el contexto de ejecución de eventos.
        meventmgr.getRuleContextBuilder().addContext(stage);

		//Ejecutar eventos de sistema al iniciar fase.
		meventmgr.processSystemEvents(eventObjectType,
								EventsDefines.EVENT_EXEC_START);

		//Ejecutar evento al iniciar fase.
		meventmgr.processEvents(eventObjectType,
								mpStage.getKeyInt(),
								EventsDefines.EVENT_EXEC_START);
	}

	public void instance(PSincNodoDAO mpSyncNd,TXSincNodoDAO syncnode,TXProcesoDAO exped)
	throws ISPACException
	{
		//TODO Evaluar el tipo de nodo de sincronización y generar el XML adecuado.

		syncnode.set("ID_EXP",exped.getKeyInt());
		syncnode.set("ID_PCD",exped.getInt("ID_PCD"));
		syncnode.set("ID_NODO",mpSyncNd.getKeyInt());
		syncnode.set("TIPO",mpSyncNd.getInt("TIPO"));
		syncnode.set("ESTADO",(String)null);
	}


	public void instance(PTramiteDAO mpTask,TXTramiteDAO task,TXFaseDAO stage,TXProcesoDAO exped)
	throws ISPACException
	{
		task.set("ID_EXP",exped.getInt("ID"));
		task.set("ID_PCD",mpTask.getInt("ID_PCD"));
		task.set("ID_FASE_EXP",stage.getKeyInt());
		task.set("ID_FASE_PCD",mpTask.getInt("ID_FASE"));
		task.set("ID_TRAMITE",mpTask.getKeyInt());
		task.set("ID_CTTRAMITE",mpTask.getInt("ID_CTTRAMITE"));
		task.set("NUMEXP",exped.getString("NUMEXP"));
		task.set("NOMBRE",mpTask.getString("NOMBRE"));
		task.set("FECHA_INICIO",new Date());


		task.set("ESTADO",TXConstants.STATUS_OPEN);
//		task.set("ID_RESP",mpTask.getString("ID_RESP"));
//		task.set("ID_RESP_SEC",mpTask.getString("ID_RESP_SEC"));

//		// Se construye el contexto de ejecución de scripts.
//		meventmgr.getRuleContextBuilder().addContext(exped);
//		meventmgr.getRuleContextBuilder().addContext(task);
//
//		//Calcular responsable del trámite.
//		List resplist=meventmgr.processEvents(EventsDefines.EVENT_OBJ_TASK,
//								mpTask.getKeyInt(),
//								EventsDefines.EVENT_EXEC_RESPCALC);
//
//		if (resplist!=null && resplist.size()>0)
//		{
//			task.set("ID_RESP",(String)resplist.get(0));
//		}
//
//		setDefaultResp(task,stage.getString("ID_RESP"));
//		setDefaultResp(task,exped.getString("ID_RESP"));
//		setDefaultResp(task,mccxt.getRespId());

		Date datedeadline = null;
		//Calcular fecha base.
		Deadline deadline = mdeadlinemgr.calcDeadline(exped.getString("NUMEXP"),
				DeadlineManager.OBJ_TASK,mpTask.getKeyInt());
		if (deadline!=null){
			datedeadline = deadline.getFinalDate();
			task.set("FECHA_LIMITE", datedeadline);
			//estado del plazo activo
			task.set("ESTADO_PLAZO", 1);
			Date datebasedate = deadline.getBaseDate();
			task.set("FECHA_INICIO_PLAZO", datebasedate);

			// numero de dias naturales del plazo
			if (datedeadline != null)
				task.set("NUM_DIAS_PLAZO", calculateDays(datebasedate, datedeadline));
			
//			Date datebasedate = deadline.getBaseDate();
//			task.set("FECHA_INICIO_PLAZO", datebasedate);
//			Date datedeadline = deadline.getFinalDate();
//			task.set("FECHA_LIMITE",datedeadline);
		}

        //Necesario para poder acceder al objeto recien creado por la transacción
        //desde las reglas
        task.store(mccxt);

		//Ejecutar evento al iniciar trámite.
/*		meventmgr.processEvents(EventsDefines.EVENT_OBJ_TASK,
								mpTask.getKeyInt(),
								EventsDefines.EVENT_EXEC_START);
*/
	}
}
