package ieci.tdw.ispac.ispaclib.session;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.errors.ISPACSessionException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.session.LockDAO;
import ieci.tdw.ispac.ispaclib.dao.session.SessionDAO;
import ieci.tdw.ispac.ispaclib.dao.session.SsVariableDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.directory.DirectoryConnectorFactory;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryConnector;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryEntry;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.sql.Timestamp;

public class SessionMgr
{

	// Variables para control de estadisiticas
	private int m_hits;
	private long m_startTime;
	private long DEFAULT_TIMEOUT = 180000;
	private long m_timeoutSession;

	/** Creates a new instance of session */

	public SessionMgr()
	{
		String sPeriod = null;
	  	try
			{
	  		ISPACConfiguration config = ISPACConfiguration.getInstance();
	  		sPeriod = config.get(ISPACConfiguration.SESSION_TIMEOUT);
	  		m_timeoutSession = Long.parseLong(sPeriod);
	  	}
	  	catch (NumberFormatException e)
			{
	  		m_timeoutSession = DEFAULT_TIMEOUT;
	  	}
	  	catch (ISPACException e)
			{
	  		m_timeoutSession = DEFAULT_TIMEOUT;
	  	}
	}

	/**
	 * Valida un usuario y obtiene su ticket de sesión
	 *
	 * @return un string que representa la sesión, o <code>null</code> si se
	 *         produce algun error.
	 * @deprecated
	 */

	public Session login(String remoteHost, String user, String password) throws ISPACException
	{
		return login(remoteHost, user, password, null);
	}

	public Session login(String remoteHost, String user, String password, String aplicacion) throws ISPACException
	{
		IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
		IDirectoryEntry userEntry = directory.login(user, password);

		return login(remoteHost, user, userEntry, aplicacion);
	}

	/**
	 * Obtiene el ticket de sesión para un usuario validado.
	 *
	 * @return un string que representa la sesión, o <code>null</code> si se
	 *         produce algun error.
	 */
	public Session login(String remoteHost, String user, IDirectoryEntry userEntry, String aplicacion) throws ISPACException
	{
		Session session = null;
		DbCnt cnt = new DbCnt();

		try
		{
			// Insertamos registro de sesion
			cnt.getConnection();
			SessionDAO sessionDAO = new SessionDAO(cnt);
			sessionDAO.createNew(cnt);
			sessionDAO.set("USUARIO", user);
			sessionDAO.set("FECHA", new java.util.Date(System
					.currentTimeMillis()));
			sessionDAO.set("TIEMPO_SS", (int)System.currentTimeMillis());
			sessionDAO.set("TIEMPO_OP", 0);
			sessionDAO.set("HITS", 1);
			String ticket = (String) sessionDAO.get("ID");
			session = new Session(remoteHost, ticket, user, userEntry);
			sessionDAO.set("DATOS", session.toXmlString());
			sessionDAO.set("HOST", remoteHost);
			sessionDAO.set("KEEP_ALIVE", new java.util.Date(System
					.currentTimeMillis()));
			sessionDAO.set("APLICACION", aplicacion);
			sessionDAO.store(cnt);
		}
		catch (ISPACException e)
		{
			throw new ISPACException("Error en SessionMgr::login (" + user + "): " + e.toString(), e);
		}
		finally
		{
			cnt.closeConnection();
		}

		return session;
	}

	/**
	 * Valida un usuario en el sistema.
	 * @param user usuario
	 * @param password clave
	 * @return el usuario validado
	 * @throws ISPACException
	 */
	public IDirectoryEntry validate(String user, String password) throws ISPACException {

		IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();

		return directory.login(user, password);
	}

	/**
	 * Inicializa una sesión, y valida el ticket de conexión. Debe ser llamada
	 * por un cliente ya conectado.
	 *
	 * @param session objeto con la informacion de la sesion
	 * @return <code>true</code> si el ticket de sesión es válido, y <code>false</code>
	 *         en cualquier otro caso
	 */
	public Session init(String ticket) throws ISPACException
	{
		Session session = null;
		DbCnt cnt = new DbCnt();

		try
		{
			cnt.getConnection();

			SessionDAO sessionDAO = new SessionDAO(cnt, ticket);
			m_startTime = System.currentTimeMillis();
			m_hits = sessionDAO.getInt("HITS") + 1;
			session = new Session();
			session.loadObject((String) sessionDAO.get("DATOS"));

			sessionDAO.set("KEEP_ALIVE", new java.util.Date(System.currentTimeMillis()));
			sessionDAO.store(cnt);
		}
		catch (ISPACException e)
		{
			throw new ISPACSessionException(
					"SessionMgr::init (Session session), sesión caducada o no existe",
					e);
		}
		finally
		{
			cnt.closeConnection();
		}
		return session;
	}

	/**
	 * Almacena en base de datos el objeto session
	 */
	public void release(Session session) throws ISPACException
	{
		long opTime;
		DbCnt cnt = new DbCnt();

		try
		{
			// Liberar todas las conexiones internas del contexto
			session.getClientContext().releaseAllConnections();

			cnt.getConnection();

			// Se carga la session para actualizarla en BD
			// tras de finalizar la ejecución de la petición al servidor
			SessionDAO sessionDAO = new SessionDAO(cnt, session.getTicket());

			sessionDAO.set("FECHA", new java.util.Date(System
					.currentTimeMillis()));
			opTime = sessionDAO.getLong("TIEMPO_OP")
					+ System.currentTimeMillis() - m_startTime;
			sessionDAO.set("TIEMPO_OP", opTime);
			sessionDAO.set("HITS", m_hits);
			sessionDAO.set("DATOS", session.toXmlString());
			sessionDAO.set("KEEP_ALIVE", new java.util.Date(System
					.currentTimeMillis()));

			sessionDAO.store(cnt);
		}
		catch (ISPACNullObject ino)
		{
			// No se encuentra el ticket
		}
		catch (ISPACException e)
		{
			throw new ISPACException(
					"Error en SessionMgr::release(Session session)", e);
		}
		finally
		{
			cnt.closeConnection();
		}
	}

	/**
	 * Elimina una sesión, y libera todos los recursos que pueda tener
	 * reservados.
	 */
	public void logout(Session session) throws ISPACException
	{
		deleteSession(session.getTicket());
	}

	/**
	 * Mantiene viva la sesion actualizando el campo keep-alive
	 * @param ticket
	 * @throws ISPACException
	 */
	public void keepAlive(String ticket) throws ISPACException
	{
		DbCnt cnt = new DbCnt();

		try
		{
			cnt.getConnection();

		    SessionDAO sessionDAO = new SessionDAO(cnt,ticket);
			sessionDAO.set("KEEP_ALIVE", new java.util.Date(
					System.currentTimeMillis()));
			sessionDAO.store(cnt);
		}
		catch (ISPACNullObject ino)
		{
			// No se encuentra el ticket
		}
		catch (ISPACException e)
		{
			throw new ISPACException(
					"error en SessionMgr::keepAlive (" + ticket + "): ",e);
		}
		finally
		{
			cnt.closeConnection();
		}
	}

	/**
	 * Borra un sesion y todos sus recursos
    *
	 * @param ticket
	 * @throws ISPACException
	 */
	public void deleteSession(String ticket)
	throws ISPACException
	{
		DbCnt cnt = new DbCnt();

		try
		{
			cnt.getConnection();

			SessionDAO sessionDAO = new SessionDAO(cnt, ticket);

			//CollectionDAO locks = sessionDAO.getBloqueos(cnt);
            CollectionDAO locks = new CollectionDAO(LockDAO.class);
			locks.delete(cnt, "WHERE ID = '" + ticket + "'") ;

			//CollectionDAO vars = sessionDAO.getVariables(cnt);
            CollectionDAO vars = new CollectionDAO(SsVariableDAO.class);
			vars.delete(cnt, "WHERE ID_SES = '" + ticket + "'");

			sessionDAO.delete(cnt);
		}
		catch (ISPACNullObject ino)
		{
			// No se encuentra el ticket
		}
		catch (ISPACException e)
		{
			throw new ISPACException(
					"Error en SessionMgr:deleteSession ("+ ticket +"): ", e);
		}
		finally
		{
			cnt.closeConnection();
		}
	}

	/**
	 *  Borra un sesion y todos sus recursos
    *
	 * @param cnt
	 * @param ticket
	 * @throws ISPACException
	 */
	public void deleteSession(DbCnt cnt, String ticket)
	throws ISPACException
	{
		try
		{
			SessionDAO sessionDAO = new SessionDAO(cnt, ticket);

			//CollectionDAO locks = sessionDAO.getBloqueos(cnt);
            CollectionDAO locks = new CollectionDAO(LockDAO.class);
			locks.delete(cnt, "WHERE ID = '" + ticket + "'") ;

			//CollectionDAO vars = sessionDAO.getVariables(cnt);
			CollectionDAO vars = new CollectionDAO(SsVariableDAO.class);
			vars.delete(cnt, "WHERE ID_SES = '" + ticket + "'");

			sessionDAO.delete(cnt);
		}
		catch (ISPACNullObject ino)
		{
			// No se encuentra el ticket
		}
		catch (ISPACException e)
		{
			throw new ISPACException(
					"Error en SessionMgr:deleteSession ("+ ticket +"): ", e);
		}
	}

	/**
	 * Borra un sesion y todos sus recursos
    *
	 * @param cnt
	 * @param session
	 * @throws ISPACException
	 */
	public void deleteSession(DbCnt cnt, SessionDAO session)
	throws ISPACException
	{
		String ticket = (String) session.getKey();
		deleteSession(cnt, ticket);
	}

	/**
	 * Borra un sesion y todos sus recursos
	 * @param ticket
	 * @throws ISPACException
	 */
	public void deleteExpiredSessions ()
	throws ISPACException
	{
		DbCnt cnt = new DbCnt();

		try
		{
			cnt.getConnection();

			long timeout = System.currentTimeMillis() - m_timeoutSession;
            Timestamp ts = new Timestamp(timeout);

            // Borrar sessiones expiradas
            String sQuery = new StringBuffer()
            	.append("WHERE KEEP_ALIVE < ")
            	.append(DBUtil.getToTimestampByBD(cnt, ts))
            	.toString();

			IItemCollection sessions = SessionDAO.getSesiones(cnt, sQuery).disconnect();
			while (sessions.next())
			{
				IItem session = sessions.value();
				deleteSession(cnt, (SessionDAO) session);
			}

			// Borrar posibles bloqueos perdidos de sesiones ya expiradas
            CollectionDAO locks = new CollectionDAO(LockDAO.class);
			locks.delete(cnt, "WHERE ID NOT IN (SELECT ID FROM SPAC_S_SESIONES)");

			// Borrar posibles variables de sesiones ya expiradas
			CollectionDAO vars = new CollectionDAO(SsVariableDAO.class);
			vars.delete(cnt, "WHERE ID_SES NOT IN (SELECT ID FROM SPAC_S_SESIONES)");
		}
		catch (ISPACException e)
		{
			throw new ISPACException("Error en SessionMgr::deleteExpiredSessions", e);
		}
		finally
		{
			cnt.closeConnection();
		}
	}

	/**
	 * Devuelve las estadísticas de una sesion
	 *
	 * @param session sesion de la cual recuperar estadisticas
	 * @return objeto de tipo SessionStatistics
	 * @throws ISAPCException si se produce error
	 */
	public SessionStatistics getStatistics(Session session)
			throws ISPACException
	{
		SessionStatistics ss = null;
		DbCnt cnt = new DbCnt();

		try
		{
			cnt.getConnection();

			SessionDAO sessionDAO = new SessionDAO(cnt, session.getTicket());
			ss = new SessionStatistics( sessionDAO.getLong("TIEMPO_OP"),
					                    sessionDAO.getInt("HITS"),
                                        sessionDAO.getLong("TIEMPO_SS"));
		}
		catch (ISPACException e)
		{
			throw new ISPACException(
					"error en SessionMgr::getStatistics, no se han podido recuperar las estadísticas de sesión",
					e);
		}
		finally
		{
			cnt.closeConnection();
		}

		return ss;
	}

	public IItemCollection getActiveSessions(String user, String aplicacion)
		throws ISPACException
	{
		DbCnt cnt = new DbCnt ();

		try
		{
			cnt.getConnection();

            String sQuery = new StringBuffer()
            	.append("WHERE USUARIO = '")
            	.append(DBUtil.replaceQuotes(user))
            	.append("' AND APLICACION = '" + DBUtil.replaceQuotes(aplicacion) + "'").toString();

			IItemCollection sessions = SessionDAO.getSesiones(cnt, sQuery).disconnect();
			return sessions;
		}
		catch (ISPACException e)
		{
			throw new ISPACException("Error en SessionMgr::deleteExpiredSessions", e);
		}
		finally
		{
			cnt.closeConnection();
		}
	}
}