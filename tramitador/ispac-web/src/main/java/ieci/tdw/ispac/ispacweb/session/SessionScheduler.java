package ieci.tdw.ispac.ispacweb.session;

import org.apache.log4j.Logger;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.session.SessionMgr;
import ieci.tdw.ispac.ispacweb.scheduler.SchedulerTask;

public class SessionScheduler extends SchedulerTask{
	
	/** Logger de la clase. */
    private static Logger logger = Logger.getLogger(SessionScheduler.class);

    
    /**
     * Ejecuta la tarea.
     */
	public void run() {
		try {
			SessionMgr mgr = new SessionMgr();
			mgr.deleteExpiredSessions();
		} catch (ISPACException e) {
			logger.error("Error en SessionScheduler::run: " + e);
		}
	}


}