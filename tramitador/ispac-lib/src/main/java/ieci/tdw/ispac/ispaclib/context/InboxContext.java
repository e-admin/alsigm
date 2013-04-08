/*
 * invesflow Java - ISPAC
 *
 * $Source: /TEST/SIGEM/ispaclib/src/ieci/tdw/ispac/ispaclib/context/InboxContext.java,v $
 * $Revision: 1.2 $
 * $Date: 2007/05/10 09:02:26 $
 * $Author: luismimg $
 */
package ieci.tdw.ispac.ispaclib.context;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.rule.IRuleContextParams;
import ieci.tdw.ispac.api.rule.RuleProperties;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

import java.util.HashMap;
import java.util.Map;

/**
 * InboxContext
 *
 *
 * @version $Revision: 1.2 $ $Date: 2007/05/10 09:02:26 $
 */
public class InboxContext implements IRuleContextParams
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

	HashMap mcontextparams;

	String msRegister;

	public InboxContext(IClientContext context, String register)
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

		mcontext=context;
		mcontextparams=new HashMap();

		msRegister = register;
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
	}

	public void setProcess(int nIdProc)  throws ISPACException
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
			throw new ISPACException(e);
		}
		finally
		{
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
	public String getRegister()
	{
		return msRegister;
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

        parammap.put(RuleProperties.RCTX_REGISTERID,getRegister());

        parammap.putAll(mcontextparams);

        return parammap;
    }

}
