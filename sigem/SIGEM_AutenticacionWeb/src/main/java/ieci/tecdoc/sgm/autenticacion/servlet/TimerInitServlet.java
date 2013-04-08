package ieci.tecdoc.sgm.autenticacion.servlet;

/*
 * $Id: TimerInitServlet.java,v 1.1.2.3 2008/02/15 12:17:52 jconca Exp $
 */
import ieci.tecdoc.sgm.autenticacion.utils.EliminarSesionesCaducadas;

import java.util.Timer;

import javax.servlet.http.HttpServlet;

public class TimerInitServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	EliminarSesionesCaducadas tarea;
	Timer temporizador;
	
	public void init() {
		int periodo = new Integer(this.getInitParameter("timer-minutes-period")).intValue();
		int delay = new Integer(this.getInitParameter("timer-minutes-delay")).intValue();
		temporizador = new Timer();
	    tarea  = new EliminarSesionesCaducadas(periodo);
	    temporizador.schedule(tarea, new Long(delay * 1000 * 60).longValue(), new Long(delay * 1000 * 60).longValue());
	}
	
	public void destroy() {
		tarea.cancel();
		temporizador.cancel();
		super.destroy();
	}
}
