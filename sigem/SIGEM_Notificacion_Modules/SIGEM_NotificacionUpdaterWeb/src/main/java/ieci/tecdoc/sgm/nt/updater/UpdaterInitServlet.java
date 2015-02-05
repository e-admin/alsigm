package ieci.tecdoc.sgm.nt.updater;
/*
 * $Id: SysNotUpdaterInitServlet.java,v 1.3.2.2 2008/02/14 16:10:24 jconca Exp $
 */
import java.util.Timer;

import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class for Servlet: SysNotUpdaterInitServlet
 * 
 */
public class UpdaterInitServlet extends javax.servlet.http.HttpServlet
		implements javax.servlet.Servlet {

	private static Logger logger = Logger
			.getLogger(UpdaterInitServlet.class);

	private static final String EXECUTION_TIMEOUT = "timeout";
	private static final String EXECUTION_LAPSO = "lapso";

	Updater oUpdater = null;

	Timer temporizador = null;

	int lapso=10;
	public void setLapso(int lapso){ this.lapso=lapso; }
	public int getLapso(){ return lapso; }
	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public UpdaterInitServlet() {
		super();
	}

	public void init() throws ServletException {
		int lapso = 60000;
		int timeout = 5000;
		if (logger.isDebugEnabled()) {
			logger.debug("NOTIFICACIONES_UPDATER_INIT_SERVLET: Arrancando...");
		}
		
		lapso = obtenerLapso(); 

		try {
			timeout = new Integer(this.getInitParameter(EXECUTION_TIMEOUT))
					.intValue();
		} catch (Exception e) {
			logger.error("Error obteniendo timeout de la configuración");
			throw new ServletException("Timeout de configuración no definido", e);
		}

		oUpdater = new Updater(timeout);
		temporizador = new Timer();
		temporizador.schedule(oUpdater, lapso, lapso);
	}

	public void destroy() {
		if (logger.isDebugEnabled()) {
			logger.debug("Parando NOTIFICACIONES Updater");
		}
		oUpdater.cancel();
		if (logger.isDebugEnabled()) {
			logger.debug("Parando Timer de NOTIFICACIONES Updater");
		}
		temporizador.cancel();
		if (logger.isDebugEnabled()) {
			logger.debug("Hilos de NOTIFICACIONES Updater detenidos");
		}
		super.destroy();
	}
	
	public int obtenerLapso() throws ServletException{
		String strLapso=null;
		int lapso;
		strLapso=UpdaterConfig.getLapso();
		if(StringUtils.isEmpty(strLapso)){
			strLapso= this.getInitParameter(EXECUTION_LAPSO);
			if(StringUtils.isEmpty(strLapso))
				strLapso=""+this.lapso;
		}
			
		try {
			lapso = new Integer(strLapso).intValue();
		} catch (Exception e) {
			logger.error("Error obteniendo lapso de ejecución de la configuración");
			throw new ServletException("Lapso de configuración no definido", e);			
		}
		return lapso;
	}
}