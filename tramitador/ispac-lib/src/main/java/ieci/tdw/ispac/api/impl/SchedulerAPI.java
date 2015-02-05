package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.ISchedulerAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.ExpedientContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramiteDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

public class SchedulerAPI implements ISchedulerAPI
{
    /** Log4j */
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
            .getLogger(SchedulerAPI.class);

    IClientContext m_clientCtx;

	public SchedulerAPI(ClientContext context)
	{
		this.m_clientCtx = context;
	}

	public void notifyOutdatedProcess()
	throws ISPACException
	{
		DbCnt cnt = null;
		try
		{
			cnt = m_clientCtx.getConnection();

			CollectionDAO col=TXProcesoDAO.getOutdatedProcess(cnt);
			while(col.next())
			{
				ExpedientContext expctx=new ExpedientContext(m_clientCtx);
				expctx.setExpedient((TXProcesoDAO)col.value());
				executeOutdatedEvent(expctx);
			}
		}
		catch (ISPACException e)
		{
			throw new ISPACException("Error en searchOutdatedProcess()", e);
		}
		finally
		{
			m_clientCtx.releaseConnection(cnt);
		}
	}

	public void notifyOutdatedStages()
	throws ISPACException
	{
		DbCnt cnt = null;
		try
		{
			cnt = m_clientCtx.getConnection();

			CollectionDAO col=TXFaseDAO.getOutdatedStages(cnt);
			while(col.next())
			{
				ExpedientContext expctx=new ExpedientContext(m_clientCtx);
				expctx.setStage((TXFaseDAO)col.value());
				executeOutdatedEvent(expctx);
			}
		}
		catch (ISPACException e)
		{
			throw new ISPACException("Error en searchOutdatedProcess()", e);
		}
		finally
		{
			m_clientCtx.releaseConnection(cnt);
		}
	}

	public void notifyOutdatedTask()
	throws ISPACException
	{
		DbCnt cnt = null;
		try
		{
			cnt = m_clientCtx.getConnection();

			CollectionDAO col=TXTramiteDAO.getOutdatedTasks(cnt);
			while(col.next())
			{
				ExpedientContext expctx=new ExpedientContext(m_clientCtx);
				expctx.setTask((TXTramiteDAO)col.value());
				executeOutdatedEvent(expctx);
			}
		}
		catch (ISPACException e)
		{
			throw new ISPACException("Error en searchOutdatedProcess()", e);
		}
		finally
		{
			m_clientCtx.releaseConnection(cnt);
		}
	}

	public void executeOutdatedEvent(ExpedientContext expctx)
	throws ISPACException
	{
	    try
	    {
			ITXTransaction txapi=m_clientCtx.getAPI().getTransactionAPI();
			if (expctx.getTaskPCD()!=0)
			{
			    txapi.executeEvents(EventsDefines.EVENT_OBJ_TASK,expctx.getTaskPCD(),
			            EventsDefines.EVENT_EXEC_OUTDATED,expctx);
			}
			else if (expctx.getStagePCD()!=0)
			{
			    txapi.executeEvents(EventsDefines.EVENT_OBJ_STAGE,expctx.getStagePCD(),
			            EventsDefines.EVENT_EXEC_OUTDATED,expctx);
			}
			else if (expctx.getProcedure()!=0)
			{
			    txapi.executeEvents(EventsDefines.EVENT_OBJ_PROCEDURE,expctx.getProcedure(),
			            EventsDefines.EVENT_EXEC_OUTDATED,expctx);
			}
	    }
	    catch(ISPACRuleException e)
	    {
	        //No se lanza la excepción para permitir que se evaluen el resto de
	        //procesos, trámites o fases fuera de fecha.
	        logger.error("ISPACScheduler: "+e.getMessage());
	    }
	    catch(ISPACException e)
	    {
	        //No se lanza la excepción para permitir que se evaluen el resto de
	        //procesos, trámites o fases fuera de fecha.
	        logger.error("ISPACScheduler: "+e.getMessage());
	    }
	}
}