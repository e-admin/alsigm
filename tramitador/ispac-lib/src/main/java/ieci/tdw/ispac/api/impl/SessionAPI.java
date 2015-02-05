package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISessionAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.audit.IspacAuditConstants;
import ieci.tdw.ispac.audit.business.IspacAuditoriaManager;
import ieci.tdw.ispac.audit.business.manager.impl.IspacAuditoriaManagerImpl;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventAccesoAplicacionVO;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryEntry;
import ieci.tdw.ispac.ispaclib.session.Session;
import ieci.tdw.ispac.ispaclib.session.SessionMgr;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.lock.LockManager;

import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

/**
 * Clase del API de gestión de sesiones.
 * 
 */
public class SessionAPI implements ISessionAPI {
	/**
	 * 
	 */
	private static final String APP_DESCRIPTION = "ISPAC";

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SessionAPI.class);

	private final SessionMgr mgr;
	private Session sesion;
	private IspacAuditoriaManager auditoriaManager = new IspacAuditoriaManagerImpl();

	public SessionAPI() {
		mgr = new SessionMgr();
	}

	public IDirectoryEntry validate(String user, String password) throws ISPACException {
		return mgr.validate(user, password);
	}

	public void login(String remoteHost, String user, String password, String aplicacion,
			Locale locale) throws ISPACException {
		sesion = mgr.login(remoteHost, user, password, aplicacion);
		init(locale);
		auditLogin(user);
	}

	/**
	 * @param user
	 */
	private void auditLogin(String user) {
		IspacAuditEventAccesoAplicacionVO eventoAcceso = new IspacAuditEventAccesoAplicacionVO();
		
		logger.info("Se procede a auditar el acceso a la aplicacion del usuario: " + user);
		
		AuditContext auditContext = AuditContextHolder.getAuditContext();
		eventoAcceso.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		eventoAcceso.setAppId(IspacAuditConstants.getAppId());
		eventoAcceso.setFecha(new Date());		
		eventoAcceso.setUser(user);
		eventoAcceso.setIdUser("");
		eventoAcceso.setUserHostName("");
		eventoAcceso.setUserIp("");
		
		if (auditContext != null){
		eventoAcceso.setIdUser(auditContext.getUserId());
		eventoAcceso.setUserHostName(auditContext.getUserHost());
		eventoAcceso.setUserIp(auditContext.getUserIP());
		}else{
			logger.error("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría en el thread local. Faltan los siguientes valores por auditar: userId, user, userHost y userIp");
		}
		auditoriaManager.audit(eventoAcceso);
	}

	public void login(String remoteHost, String user, IDirectoryEntry userEntry, String aplicacion,
			Locale locale) throws ISPACException {
		sesion = mgr.login(remoteHost, user, userEntry, aplicacion);
		init(locale);
		
		auditLogin(user);
	}

	public void init(String ticket, Locale locale) throws ISPACException {
		sesion = mgr.init(ticket);
		init(locale);
	}

	public void init(Locale locale) throws ISPACException {
		if (sesion != null) {
			ClientContext clientctx = sesion.getClientContext();
			clientctx.setAPI(new InvesflowAPI(clientctx));
			clientctx.setLocale(locale);
			
		}
	}

	public void release() throws ISPACException {
		if (sesion != null) {
			mgr.release(sesion);
		}
	}

	public void keepAlive(String ticket) throws ISPACException {
		mgr.keepAlive(ticket);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ieci.tdw.ispac.api.impl.ISessionAPI#logout()
	 */
	public void logout() throws ISPACException {
		if (sesion != null)
			mgr.logout(sesion);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ieci.tdw.ispac.api.impl.ISessionAPI#testLogged()
	 */
	public boolean testLogged() {
		return sesion != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ieci.tdw.ispac.api.impl.ISessionAPI#getTicket()
	 */
	public String getTicket() {
		if (sesion == null)
			return null;
		return sesion.getTicket();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ieci.tdw.ispac.api.impl.ISessionAPI#getUserName()
	 */
	public String getUserName() {
		if (sesion == null)
			return null;
		return sesion.getUserName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ieci.tdw.ispac.api.impl.ISessionAPI#getRespName()
	 */
	public String getRespName() {
		if (sesion == null)
			return null;
		return sesion.getRespName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ieci.tdw.ispac.api.impl.ISessionAPI#getSession()
	 */
	public Session getSession() {
		return sesion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ieci.tdw.ispac.api.impl.ISessionAPI#getClientContext()
	 */
	public ClientContext getClientContext() {
		if (sesion == null)
			return null;
		return sesion.getClientContext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ieci.tdw.ispac.api.impl.ISessionAPI#getAPI()
	 */
	public IInvesflowAPI getAPI() {
		if (sesion == null)
			return null;
		return sesion.getClientContext().getAPI();
	}

	public IItemCollection getActiveSessions(String user, String aplicacion) throws ISPACException {
		return mgr.getActiveSessions(user, aplicacion);
	}

	public void deleteSession(String ticket) throws ISPACException {
		mgr.deleteSession(ticket);
	}

	public void lockObj(int tpObj, int idObj) throws ISPACException {
		LockManager lockManager = new LockManager(sesion.getClientContext());
		lockManager.lockObj(tpObj, idObj, StringUtils.EMPTY);
	}

	public void unlockObj(int tpObj, int idObj) throws ISPACException {
		LockManager lockManager = new LockManager(sesion.getClientContext());
		lockManager.unlockObj(tpObj, idObj, StringUtils.EMPTY);
	}

	public void lockProcedure(int pcdId) throws ISPACException {
		LockManager lockManager = new LockManager(sesion.getClientContext());
		lockManager.lockProcedure(pcdId);
	}

	public void unlockProcedure(int pcdId) throws ISPACException {
		LockManager lockManager = new LockManager(sesion.getClientContext());
		lockManager.unlockProcedure(pcdId);
	}

	public void lockDocument(int docId) throws ISPACException {
		LockManager lockManager = new LockManager(sesion.getClientContext());
		lockManager.lockDocument(docId);
	}

	public void unlockDocument(int docId) throws ISPACException {
		LockManager lockManager = new LockManager(sesion.getClientContext());
		lockManager.unlockDocument(docId);
	}

	public void lockTemplate(int templateId) throws ISPACException {
		LockManager lockManager = new LockManager(sesion.getClientContext());
		lockManager.lockTemplate(templateId);
	}

	public void unlockTemplate(int templateId) throws ISPACException {
		LockManager lockManager = new LockManager(sesion.getClientContext());
		lockManager.unlockTemplate(templateId);
	}

	public void lockStagesCT() throws ISPACException {
		LockManager lockManager = new LockManager(sesion.getClientContext());
		lockManager.lockStagesCT();
	}

	public void unlockStagesCT() throws ISPACException {
		LockManager lockManager = new LockManager(sesion.getClientContext());
		lockManager.unlockStagesCT();
	}

	public void lockActCT() throws ISPACException {
		LockManager lockManager = new LockManager(sesion.getClientContext());
		lockManager.lockActCT();
	}

	public void unlockActCT() throws ISPACException {
		LockManager lockManager = new LockManager(sesion.getClientContext());
		lockManager.unlockActCT();
	}

	public void lockDocTypesCT() throws ISPACException {
		LockManager lockManager = new LockManager(sesion.getClientContext());
		lockManager.lockDocTypesCT();
	}

	public void unlockDocTypesCT() throws ISPACException {
		LockManager lockManager = new LockManager(sesion.getClientContext());
		lockManager.unlockDocTypesCT();
	}

	public void lockSheets() throws ISPACException {
		LockManager lockManager = new LockManager(sesion.getClientContext());
		lockManager.lockSheets();
	}

	public void unlockSheets() throws ISPACException {
		LockManager lockManager = new LockManager(sesion.getClientContext());
		lockManager.unlockSheets();
	}

	public void lockEntities() throws ISPACException {
		LockManager lockManager = new LockManager(sesion.getClientContext());
		lockManager.lockEntities();
	}

	public void unlockEntities() throws ISPACException {
		LockManager lockManager = new LockManager(sesion.getClientContext());
		lockManager.unlockEntities();
	}

	public void lockProcessCreation() throws ISPACException {
		LockManager lockManager = new LockManager(sesion.getClientContext());
		lockManager.lockProcessCreation();
	}

	public void unlockProcessCreation() throws ISPACException {
		LockManager lockManager = new LockManager(sesion.getClientContext());
		lockManager.unlockProcessCreation();
	}

	public void lockRules() throws ISPACException {
		LockManager lockManager = new LockManager(sesion.getClientContext());
		lockManager.lockRules();
	}

	public void unlockRules() throws ISPACException {
		LockManager lockManager = new LockManager(sesion.getClientContext());
		lockManager.unlockRules();
	}

}