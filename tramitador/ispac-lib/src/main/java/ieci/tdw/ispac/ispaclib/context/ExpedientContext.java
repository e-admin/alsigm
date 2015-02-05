package ieci.tdw.ispac.ispaclib.context;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.api.rule.IRuleContextParams;
import ieci.tdw.ispac.api.rule.RuleProperties;
import ieci.tdw.ispac.ispaclib.dao.procedure.PTramiteDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramiteDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

import java.util.HashMap;
import java.util.Map;

/**
 * Contexto de expediente.
 *
 */
public class ExpedientContext implements IRuleContextParams
{
	IClientContext mcontext;

	int midprocedure;
	int midstagePCD;
	int midtaskPCD;

	int midproc;
	int midstage;
	int midtask;
	String mnumexp;
	String msProcedureName;
	String msStageName;
	String msTaskName;
	String msFileTemplate = null;

	int mIdEntity;	// Entidad relacionada
	int mIdKey;		// Identificador del registro de la entidad

	HashMap mcontextparams;

	public ExpedientContext(IClientContext context)
	{
		midprocedure=0;
		midstagePCD=0;
		midtaskPCD=0;

		midproc=0;
		midstage=0;
		midtask=0;
		mnumexp="";
		msStageName="";
		msTaskName="";

		mIdEntity = 0;
		mIdKey = 0;

		mcontext=context;
		mcontextparams=new HashMap();
	}

	public ExpedientContext(IClientContext context,String numexp)
	{
		midprocedure=0;
		midstagePCD=0;
		midtaskPCD=0;

		midproc=0;
		midstage=0;
		midtask=0;
		mnumexp=numexp;

		mIdEntity = 0;
		mIdKey = 0;

		mcontext=context;
		mcontextparams=new HashMap();
	}

	public void setExpedient(TXProcesoDAO proc)  throws ISPACException
	{
		midprocedure=proc.getInt("ID_PCD");
		midproc=proc.getInt("ID");
		mnumexp=proc.getString("NUMEXP");
		midstagePCD=0;
		midtaskPCD=0;
		midstage=0;
		midtask=0;

		mcontextparams.put(RuleProperties.RCTX_PROCESS,Integer.toString(midproc));
		mcontextparams.put(RuleProperties.RCTX_PROCEDURE,Integer.toString(midprocedure));
	}
/*
	public void setExpedient(String numexp)  throws ISPACException
	{
		DbCnt cnt = null;
		try
		{
			cnt = mcontext.getConnection();
			TXProcesoDAO proc= new TXProcesoDAO(mcontext.getConnection(),numexp);

			setExpedient(proc);
		}
		catch (ISPACException e)
		{
			throw new ISPACException("Error en ExpedientContext:setExpedient("+ numexp + ")", e);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}
*/

	public void setProc(int nIdProc)  throws ISPACException
	{
		DbCnt cnt = null;
		try
		{
			cnt = mcontext.getConnection();
			TXProcesoDAO proc= new TXProcesoDAO(cnt,nIdProc);

			setExpedient(proc);
		}
		catch (ISPACException e)
		{
			throw new ISPACException("Error en ExpedientContext:setProc("+ nIdProc + ")", e);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}
	public void setStage(TXFaseDAO xstage)  throws ISPACException
	{
		midprocedure=xstage.getInt("ID_PCD");
		midproc=xstage.getInt("ID_EXP");
		mnumexp=xstage.getString("NUMEXP");
		midstagePCD=xstage.getInt("ID_FASE");
		midtaskPCD=0;
		midstage=xstage.getInt("ID");
		midtask=0;
		IProcedure procedure = mcontext.getAPI().getProcedure(midprocedure);
		msProcedureName = procedure.getString("NOMBRE");
		IItem pstage = procedure.getStage(midstagePCD);
		msStageName = pstage.getString("NOMBRE");
		
		mcontextparams.put(RuleProperties.RCTX_PROCESS,Integer.toString(midproc));
		mcontextparams.put(RuleProperties.RCTX_PROCEDURE,Integer.toString(midprocedure));
		mcontextparams.put(RuleProperties.RCTX_NUMEXP,mnumexp);
		mcontextparams.put(RuleProperties.RCTX_STAGE,Integer.toString(midstage));
		mcontextparams.put(RuleProperties.RCTX_STAGEPCD,Integer.toString(midstagePCD));
		mcontextparams.put(RuleProperties.RCTX_TASK,Integer.toString(midtask));
		mcontextparams.put(RuleProperties.RCTX_TASKPCD,Integer.toString(midtaskPCD));
	}

	public void setStage(int nIdStage)  throws ISPACException
	{
		DbCnt cnt = null;
		try
		{
			cnt = mcontext.getConnection();
			TXFaseDAO xstage=new TXFaseDAO(cnt,nIdStage);
			setStage(xstage);
		}
		catch (ISPACException e)
		{
			throw new ISPACException("Error en ExpedientContext:setStage("+ nIdStage + ")", e);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}
	
	public void setActivity(TXFaseDAO xstage, int nIdTask, int nIdTaskPCD)  throws ISPACException
	{
		midprocedure=xstage.getInt("ID_PCD");
		midproc=xstage.getInt("ID_EXP");
		mnumexp=xstage.getString("NUMEXP");
		midstagePCD=xstage.getInt("ID_FASE");
		midtaskPCD=nIdTaskPCD;
		midstage=xstage.getInt("ID");
		midtask=nIdTask;
		IProcedure procedure = mcontext.getAPI().getProcedure(midprocedure);
		msProcedureName = procedure.getString("NOMBRE");
		IItem pstage = procedure.getStage(midstagePCD);
		msStageName = pstage.getString("NOMBRE");
		
		mcontextparams.put(RuleProperties.RCTX_PROCESS,Integer.toString(midproc));
		mcontextparams.put(RuleProperties.RCTX_PROCEDURE,Integer.toString(midprocedure));
		mcontextparams.put(RuleProperties.RCTX_NUMEXP,mnumexp);
		mcontextparams.put(RuleProperties.RCTX_STAGE,Integer.toString(midstage));
		mcontextparams.put(RuleProperties.RCTX_STAGEPCD,Integer.toString(midstagePCD));
		mcontextparams.put(RuleProperties.RCTX_TASK,Integer.toString(midtask));
		mcontextparams.put(RuleProperties.RCTX_TASKPCD,Integer.toString(midtaskPCD));
	}
	
	public void setActivity(int nIdActivity, int nIdTask, int nIdTaskPCD)  throws ISPACException
	{
		DbCnt cnt = null;
		try
		{
			cnt = mcontext.getConnection();
			TXFaseDAO xstage=new TXFaseDAO(cnt,nIdActivity);
			setActivity(xstage, nIdTask, nIdTaskPCD);
		}
		catch (ISPACException e)
		{
			throw new ISPACException("Error en ExpedientContext:setActivity("+ nIdActivity + ", " + nIdTask + ", " + nIdTaskPCD + ")", e);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

	public void setTask(TXTramiteDAO xtask) throws ISPACException
	{
		midprocedure=xtask.getInt("ID_PCD");
		midproc=xtask.getInt("ID_EXP");
		mnumexp=xtask.getString("NUMEXP");
		midstagePCD=xtask.getInt("ID_FASE_PCD");
		midtaskPCD=xtask.getInt("ID_TRAMITE");
		midstage=xtask.getInt("ID_FASE_EXP");
		midtask=xtask.getInt("ID");

		IProcedure procedure = mcontext.getAPI().getProcedure(midprocedure);
		msProcedureName = procedure.getString("NOMBRE");
		IItem ptask = procedure.getTask(midtaskPCD);
		msTaskName = ptask.getString("NOMBRE");
		
		IItem pstage = procedure.getStage(midstagePCD);
		msStageName = pstage.getString("NOMBRE");
	
		mcontextparams.put(RuleProperties.RCTX_PROCESS,Integer.toString(midproc));
		mcontextparams.put(RuleProperties.RCTX_PROCEDURE,Integer.toString(midprocedure));
		mcontextparams.put(RuleProperties.RCTX_NUMEXP,mnumexp);
		mcontextparams.put(RuleProperties.RCTX_STAGE,Integer.toString(midstage));
		mcontextparams.put(RuleProperties.RCTX_STAGEPCD,Integer.toString(midstagePCD));
		mcontextparams.put(RuleProperties.RCTX_TASK,Integer.toString(midtask));
		mcontextparams.put(RuleProperties.RCTX_TASKPCD,Integer.toString(midtaskPCD));
	}

	public void setTask(int nIdTask) throws ISPACException
	{
		DbCnt cnt = null;
		try
		{
			cnt = mcontext.getConnection();
			TXTramiteDAO xtask=new TXTramiteDAO(cnt,nIdTask);

			setTask(xtask);
		}
		catch (ISPACException e)
		{
			throw new ISPACException("Error en ExpedientContext:setTask("+ nIdTask + ")", e);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

	public void setPTask(PTramiteDAO ptask) throws ISPACException {
		
		midprocedure = ptask.getInt("ID_PCD");
		midstagePCD = ptask.getInt("ID_FASE");
		midtaskPCD = ptask.getInt("ID");

		IProcedure procedure = mcontext.getAPI().getProcedure(midprocedure);
		msProcedureName = procedure.getString("NOMBRE");
		msTaskName = ptask.getString("NOMBRE");

		IItem pstage = procedure.getStage(midstagePCD);
		msStageName = pstage.getString("NOMBRE");

		mcontextparams.put(RuleProperties.RCTX_PROCESS, Integer.toString(midproc));
		mcontextparams.put(RuleProperties.RCTX_PROCEDURE, Integer.toString(midprocedure));
		mcontextparams.put(RuleProperties.RCTX_NUMEXP, mnumexp);
		mcontextparams.put(RuleProperties.RCTX_STAGE, Integer.toString(midstage));
		mcontextparams.put(RuleProperties.RCTX_STAGEPCD, Integer.toString(midstagePCD));
		mcontextparams.put(RuleProperties.RCTX_TASK, Integer.toString(midtask));
		mcontextparams.put(RuleProperties.RCTX_TASKPCD, Integer.toString(midtaskPCD));
	}

	public void setPTask(int nIdTaskPCD) throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = mcontext.getConnection();
			PTramiteDAO ptask = new PTramiteDAO(cnt, nIdTaskPCD);
			setPTask(ptask);
		} catch (ISPACException e) {
			throw new ISPACException("Error en ExpedientContext:setTaskPCD("
					+ nIdTaskPCD + ")", e);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	public int getProcedure()
	{
		return midprocedure;
	}

	public int getStagePCD()
	{
		return midstagePCD;
	}
	public int getTaskPCD()
	{
		return midtaskPCD;
	}

	public int getProcess()
	{
		return midproc;
	}
	public int getStage()
	{
		return midstage;
	}
	public int getTask()
	{
		return midtask;
	}
	public String getNumExp()
	{
		return mnumexp;
	}
	public String getProcedureName()
	{
		return msProcedureName;
	}
	public String getStageName()
	{
		return msStageName;
	}
	public String getTaskName()
	{
		return msTaskName;
	}
	public String getFileTemplate()
	{
		return msFileTemplate;
	}
	public void setFileTemplate( String file)
	{
		msFileTemplate = file;
	}
	public int getEntity()
	{
		return mIdEntity;
	}
	public void setEntity(int entity)
	{
		mIdEntity = entity;
	}
	public int getKey()
	{
		return mIdKey;
	}
	public void setKey(int key)
	{
		mIdKey = key;
	}
	public void addContextParam(String name,String value)
	{
	    mcontextparams.put(name,value);
	}

	public void addContextParams(Map params)
	{
	    mcontextparams.putAll(params);
	}


	// Implementación de IRuleContextParams

    public int getRuleProcedureId()
    {
        return getProcedure();
    }

    public int getRuleProcId()
    {
        return getProcess();
    }

    public String getRuleNumexp()
    {
        return getNumExp();
    }

    public int getRuleStagePCDId()
    {
        return getStagePCD();
    }

    public int getRuleStageId()
    {
        return getStage();
    }

    public int getRuleTaskPCDId()
    {
        return getTaskPCD();
    }

    public int getRuleTaskId()
    {
        return getTask();
    }

    public Map getRuleParameters()
    {
        Map parammap=new HashMap();

        parammap.put(RuleProperties.RCTX_PROCEDURENAME,getProcedureName());
        parammap.put(RuleProperties.RCTX_STAGENAME,getStageName());
        parammap.put(RuleProperties.RCTX_TASKNAME,getTaskName());

        parammap.putAll(mcontextparams);

        return parammap;
    }

}
