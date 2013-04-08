/*
 * Created on 15-mar-2005
 *
 */
package ieci.tdw.ispac.ispacweb.api;

import java.io.InputStream;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;

/**
 * @author juanin
 *
 */
public interface IWorklist
{
    /**
     * Devuelve la lista de procedimientos en los cuales existen expedientes en curso
     * que son responsabilidad del usuario conectado
     *
     * @param state  el estado actual de tramitaci&oacute;n
     * @return lista de procedimientos
     * @throws ISPACException
     */
    public IItemCollection getProcedures(IState state)
            throws ISPACException;
    
	/**
	 * Devuelve la lista de procedimientos en los cuales existen expedientes en curso
	 * que son responsabilidad del usuario conectado
	 *
	 * @param IState state  el estado actual de tramitaci&oacute;n
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return lista de procedimientos
	 */
	public IItemCollection getProcedures(IState state, String resp) 
			throws ISPACException;

	/**
	 * Devuelve la lista de subprocesos en los cuales existen expedientes en curso
	 * que son responsabilidad del usuario conectado
	 *
	 * @param IState state  el estado actual de tramitaci&oacute;n
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return lista de procedimientos
	 */
	public IItemCollection getSubProcedures(IState state, String resp) 
	throws ISPACException;	
	
    /**
     * Devuelve la lista de fases para el estado actual en las cuales existe algún
     * expediente en curso responsabilidad del usuario conectado
     *
     * @param IState state  el estado actual de tramitaci&oacute;n
     * @return lista de fases
     * @throws ISPACException
     */
    public IItemCollection getStages(IState state)
            throws ISPACException;
    
    /**
     * Devuelve la lista de fases para el estado actual en las cuales existe algún
     * expediente en curso responsabilidad del usuario conectado
     *
     * @param IState state  el estado actual de tramitaci&oacute;n
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
     * @return lista de fases
     * @throws ISPACException
     */
    public IItemCollection getStages(IState state, String resp)
            throws ISPACException;

    /**
     * Devuelve la lista de expedientes para el estado actual responsabilidad
     * del usuario conectado
     *
     * @param state  el estado actual de tramitaci&oacute;n
     * @return lista de expedientes
     */
    public IItemCollection getProcesses(IState state)
            throws ISPACException;


    /**
     * Devuelve la lista de expedientes para el estado actual responsabilidad
     * del usuario conectado. La lista se confecciona según la definición
     *
     * @param state  el estado actual de tramitaci&oacute;n
     * @param istream stream de entrada con el xml de definición de la lista.
     * @return lista de expedientes
     * @throws ISPACException
     */
    public IItemCollection getProcesses(IState state,InputStream istream)
            throws ISPACException;

    /**
     * Devuelve la lista de subprocesos para el estado actual responsabilidad
     * del usuario conectado. La lista se confecciona según la definición
     *
     * @param state  el estado actual de tramitaci&oacute;n
     * @param istream stream de entrada con el xml de definición de la lista.
     * @return lista de subprocesos
     * @throws ISPACException
     */
    
    public IItemCollection getSubProcesses(IState state, InputStream istream)
    throws ISPACException;
    
    /**
     * Devuelve la lista de expedientes para el estado actual responsabilidad
     * del usuario conectado
     *
     * @param state  el estado actual de tramitaci&oacute;n
     * @param resourcepath Ruta completa al fichero con el xml de definición de la lista
     * @return lista de expedientes
     * @throws ISPACException
     */
    public IItemCollection getProcesses(IState state,String resourcepath)
            throws ISPACException;

    /**
     * Lista de trámites de procedimiento para el estado actual responsabilidad del
     * usuario conectado
     *
     * @param state  el estado actual de tramitaci&oacute;n
     * @return lista de trámites
     * @throws ISPACException
     */ 
    public IItemCollection getProcedureTasks(IState state)
            throws ISPACException;
    
	/**
	 * Lista de trámites de procedimiento para el estado actual responsabilidad del
	 * usuario conectado
	 *
	 * @param IState state  el estado actual de tramitaci&oacute;n
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return lista de trámites
	 */
	public IItemCollection getProcedureTasks(IState state, String resp)
			throws ISPACException;

    /**
     * Lista de trámites para el estado actual responsabilidad del
     * usuario conectado
     *
     * @param state  el estado actual de tramitaci&oacute;n
     * @return lista de trámites
     * @throws ISPACException
     */
    public IItemCollection getTasks(IState state)
            throws ISPACException;
    
    /**
     * Lista de tramitaciones agrupadas por procedimiento
     * @param state
     * @return
     * @throws ISPACException
     */
    public IItemCollection getBatchTasks()
    	throws ISPACException;
    
	/**
	 * Obtener las responsabilidades del usuario conectado (supervisar y sustituir).
	 * 
	 * @return Cadena con todos los UIDs separados por comas
	 * @throws ISPACException
	 */
	public String getRespString(IState state) 
		throws ISPACException;
    
}