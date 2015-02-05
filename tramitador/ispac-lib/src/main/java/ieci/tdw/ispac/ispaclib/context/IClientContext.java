package ieci.tdw.ispac.ispaclib.context;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

import java.util.Locale;

/**
 * Interfaz para el contexto de cliente.
 *
 */
public interface IClientContext
{
	public IInvesflowAPI getAPI();

	public DbCnt getConnection() throws ISPACException;

	public void releaseConnection(DbCnt cnt);

	public void releaseAllConnections();

	public DbCnt beginTX() throws ISPACException;

	public void endTX(boolean bCommit) throws ISPACException;

	public void releaseTX() throws ISPACException;

	public boolean ongoingTX() throws ISPACException;

	public TransactionContainer getTXContainer();

	/**
	 * Devuelve el id de responsable del usuario conectado
	 * @return
	 */
	public String getRespId();

	/**
	 * Devuelve el ticket
	 * @return
	 */
	public String getTicket();
	
	/**
	 * Almacena el ticket del Cliente
	 * @param ticket
	 */
	public void setTicket(String ticket);

	/**
	 * Establece la ip del usuario conectado
	 * @param remoteHost
	 */
	public void setRemoteHost(String remoteHost);

	
	public void setUserName(String userName);

    /**
     * Devuelve el locale
     * @return
     */
	public Locale getLocale();
    
    /**
     * Establece el locale
     * @param locale
     */
    public void setLocale(Locale locale);

	/**
	 * Consultar una variable de session
	 * @param name
	 * @return
	 * @throws ISPACException
	 */
    public String getSsVariable(String name) throws ISPACException;

	/**
	 * Establecer una variable de session
	 * @param name
	 * @param value
	 * @throws ISPACException
	 */
	public void setSsVariable(String name, String value) throws ISPACException;

	/**
	 * Borrado de variables de session
	 * @param name
	 * @throws ISPACException
	 */
	public void deleteSsVariable(String name) throws ISPACException;

	public String toXmlString();
	
	/**
	 * 
	 * @return Informacion sobre las responsabilidad del usuario: Departamento, tipo :usuario o grupo,
	 * Usuario de los que es reponsable
	 * 
	 */
	public IResponsible getResponsible();
	
	/**
	 * USO INTERNO!!!. Inicializa el objeto a partir de un XML
	 * @param xml
	 * @throws ISPACException
	 */
	public void loadObject(String xml) throws ISPACException;
	
	/**
	 * @return El contexto del estado actual de tramitación
	 */
	public StateContext getStateContext();


}