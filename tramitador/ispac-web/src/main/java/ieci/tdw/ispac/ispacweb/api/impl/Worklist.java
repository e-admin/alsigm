/*
 * Created on 14-mar-2005
 *
 */
package ieci.tdw.ispac.ispacweb.api.impl;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IWorklistAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.IWorklist;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author juanin
 *
 */
public class Worklist implements IWorklist
{
    ClientContext mcct;

    public Worklist(ClientContext cct)
	{
		this.mcct = cct;
	}

	/**
	 * Devuelve la lista de procedimientos en los cuales existen expedientes en curso
	 * que son responsabilidad del usuario conectado
	 *
	 * @param IState state  el estado actual de tramitaci&oacute;n
	 * @return lista de procedimientos
	 */
	public IItemCollection getProcedures(IState state) throws ISPACException
	{
	    IInvesflowAPI invesflowAPI = mcct.getAPI();
		IWorklistAPI wl = invesflowAPI.getWorkListAPI();

		return wl.getProcedures();
	}
	
	/**
	 * Devuelve la lista de procedimientos en los cuales existen expedientes en curso
	 * que son responsabilidad del usuario conectado
	 *
	 * @param IState state  el estado actual de tramitaci&oacute;n
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return lista de procedimientos
	 */
	public IItemCollection getProcedures(IState state, String resp) throws ISPACException
	{
	    IInvesflowAPI invesflowAPI = mcct.getAPI();
		IWorklistAPI wl = invesflowAPI.getWorkListAPI();

		return wl.getProcedures(resp);
	}

	
	public IItemCollection getSubProcedures(IState state, String resp) throws ISPACException
	{
	    IInvesflowAPI invesflowAPI = mcct.getAPI();
		IWorklistAPI wl = invesflowAPI.getWorkListAPI();

		return wl.getSubProcedures(resp);
	}	
	
	/**
	 * Devuelve la lista de fases para el estado actual en las cuales existe algún
	 * expediente en curso responsabilidad del usuario conectado
	 *
	 * @param IState state  el estado actual de tramitaci&oacute;n
	 * @return lista de fases
	 */
	public IItemCollection getStages(IState state) throws ISPACException
	{
	    int procedureid=state.getPcdId();

	    //No hay procedimiento. Se devuelve una colección vacía.
	    if (procedureid==0)
	        return new ListCollection(new ArrayList());

	    IInvesflowAPI invesflowAPI = mcct.getAPI();
		IWorklistAPI wl = invesflowAPI.getWorkListAPI();

		return wl.getStages(procedureid);
	}
	
    /**
     * Devuelve la lista de fases para el estado actual en las cuales existe algún
     * expediente en curso responsabilidad del usuario conectado
     *
     * @param IState state  el estado actual de tramitaci&oacute;n
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
     * @return lista de fases
     * @throws ISPACException
     */
    public IItemCollection getStages(IState state, String resp) throws ISPACException {
    	
	    int procedureid=state.getPcdId();

	    //No hay procedimiento. Se devuelve una colección vacía.
	    if (procedureid==0)
	        return new ListCollection(new ArrayList());

	    IInvesflowAPI invesflowAPI = mcct.getAPI();
		IWorklistAPI wl = invesflowAPI.getWorkListAPI();

		return wl.getStages(procedureid, resp);
    }

	/**
	 * Devuelve la lista de expedientes para el estado actual responsabilidad
	 * del usuario conectado
	 *
	 * @param IState state  el estado actual de tramitaci&oacute;n
	 * @return lista de expedientes
	 */
	public IItemCollection getProcesses(IState state) throws ISPACException
	{
	    int idstagePCD=state.getStagePcdId();

	    //No hay fase del procedimiento. Se devuelve una colección vacía.
	    if (idstagePCD==0)
	        return new ListCollection(new ArrayList());

	    IInvesflowAPI invesflowAPI = mcct.getAPI();
		IWorklistAPI wl = invesflowAPI.getWorkListAPI();
		return wl.getProcesses(idstagePCD);
	}

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispacweb.api.IWorklist#getProcesses(ieci.tdw.ispac.ispacweb.api.IState, java.io.InputStream)
     */
    public IItemCollection getProcesses(IState state, InputStream istream) throws ISPACException
    {
	    int idstagePCD=state.getStagePcdId();

	    //No hay fase del procedimiento. Se devuelve una colección vacía.
	    if (idstagePCD==0)
	        return new ListCollection(new ArrayList());

	    IInvesflowAPI invesflowAPI = mcct.getAPI();
		IWorklistAPI wl = invesflowAPI.getWorkListAPI();
		return wl.getProcesses(idstagePCD,istream);
    }


	public IItemCollection getSubProcesses(IState state, InputStream istream) throws ISPACException {
	    int idActivityPCD=state.getActivityPcdId();

	    //No hay fase del procedimiento. Se devuelve una colección vacía.
	    if (idActivityPCD==0)
	        return new ListCollection(new ArrayList());

	    IInvesflowAPI invesflowAPI = mcct.getAPI();
		IWorklistAPI wl = invesflowAPI.getWorkListAPI();
		
		int pcdId = state.getPcdId();
		if (pcdId == 0) {
			return wl.getSubProcesses(idActivityPCD,istream);
		} else {
			return wl.getSubProcesses(pcdId, idActivityPCD,istream);
		}
	}    
    
    
    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispacweb.api.IWorklist#getProcesses(ieci.tdw.ispac.ispacweb.api.IState, java.lang.String)
     */
    public IItemCollection getProcesses(IState state, String resourcepath) throws ISPACException
    {
	    int idstagePCD=state.getStagePcdId();

	    //No hay fase del procedimiento. Se devuelve una colección vacía.
	    if (idstagePCD==0)
	        return new ListCollection(new ArrayList());

	    IInvesflowAPI invesflowAPI = mcct.getAPI();
		IWorklistAPI wl = invesflowAPI.getWorkListAPI();
		return wl.getProcesses(idstagePCD,resourcepath);
	}

	/**
	 * Lista de trámites de procedimiento para el estado actual responsabilidad del
	 * usuario conectado
	 *
	 * @param IState state  el estado actual de tramitaci&oacute;n
	 * @return lista de trámites
	 */
	public IItemCollection getProcedureTasks(IState state) throws ISPACException
	{
	    IInvesflowAPI invesflowAPI = mcct.getAPI();
		IWorklistAPI wl = invesflowAPI.getWorkListAPI();

		int idstagePCD=state.getStagePcdId();
		if (idstagePCD==0)
		    return wl.getProcedureTasks();

		return wl.getProcedureStageTasks(idstagePCD);
	}
	
	/**
	 * Lista de trámites de procedimiento para el estado actual responsabilidad del
	 * usuario conectado
	 *
	 * @param IState state  el estado actual de tramitaci&oacute;n
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return lista de trámites
	 */
	public IItemCollection getProcedureTasks(IState state, String resp) throws ISPACException
	{
	    IInvesflowAPI invesflowAPI = mcct.getAPI();
		IWorklistAPI wl = invesflowAPI.getWorkListAPI();

		int idstagePCD=state.getStagePcdId();
		if (idstagePCD==0)
		    return wl.getProcedureTasks(resp);

		return wl.getProcedureStageTasks(idstagePCD);
	}
	
	/*
	 * (non-Javadoc)
	 * @see ieci.tdw.ispac.ispacweb.api.IWorklist#getProcedureGroupTasks(ieci.tdw.ispac.ispacweb.api.IState)
	 */
	public IItemCollection getBatchTasks() throws ISPACException
	{
	    IInvesflowAPI invesflowAPI = mcct.getAPI();
		IWorklistAPI wl = invesflowAPI.getWorkListAPI();
	    return wl.getBatchTasks();
	}
	
	/**
	 * Lista de trámites para el estado actual responsabilidad del
	 * usuario conectado.
	 *
	 * @param IState state  el estado actual de tramitaci&oacute;n
	 * @return lista de trámites
	 */
	public IItemCollection getTasks(IState state) throws ISPACException
	{
	    IInvesflowAPI invesflowAPI = mcct.getAPI();
		IWorklistAPI wl = invesflowAPI.getWorkListAPI();

		int procid=state.getProcessId();
		if (procid!=0)
		    return wl.findActiveTasks(procid);

		int taskCtlId = state.getTaskCtlId();

		if (taskCtlId == 0)
		    return new ListCollection(new ArrayList());
		
		return wl.getTasksCTL(taskCtlId);
	}
	
	/**
	 * Obtener las responsabilidades del usuario conectado (supervisar y sustituir).
	 * 
	 * @return Cadena con todos los UIDs separados por comas
	 * @throws ISPACException
	 */
	public String getRespString(IState state) throws ISPACException
	{
	    IInvesflowAPI invesflowAPI = mcct.getAPI();
		IWorklistAPI wl = invesflowAPI.getWorkListAPI();

		return wl.getRespString();
	}

	
}