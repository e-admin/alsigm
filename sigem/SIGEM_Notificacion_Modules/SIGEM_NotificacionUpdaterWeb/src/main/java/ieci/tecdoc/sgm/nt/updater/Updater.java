package ieci.tecdoc.sgm.nt.updater;
/*
 * $Id: SysNotUpdater.java,v 1.4.2.2 2008/02/15 12:20:19 jconca Exp $
 */
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

import ieci.tecdoc.sgm.core.admin.web.AdministracionHelper;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.nt.GestorNotificaciones;
import ieci.tecdoc.sgm.nt.exception.ServicioWebExcepcion;

import org.apache.log4j.Logger;

public class Updater extends TimerTask {
	
	private static Logger logger = Logger.getLogger(Updater.class);
	int timeout;
	public Updater(int pTimeout)
	{
		this.timeout = pTimeout;
	}
	
	public void run() {
		if(logger.isDebugEnabled()){
			logger.debug("Ejecutando run de SysNotUpdater");
		}
		Date date = new Date();
		logger.debug("Actualizacion de estados de notificaciones.");       
        try{
        	Entidad oEntidad = null;
			List oLista = AdministracionHelper.obtenerListaEntidades();
        	Iterator oIterator = oLista.iterator();
			if( (oLista != null) && (oLista.size() > 0) ){
				while(oIterator.hasNext()){
					oEntidad = (Entidad)oIterator.next();
					if(logger.isInfoEnabled()) {
						logger.info("Actualizando notificaciones de la entidad: " + oEntidad.getIdentificador());
					}
					GestorNotificaciones.actualizaEstados(null, oEntidad.getIdentificador(), timeout);
				}
			}
        } catch (ServicioWebExcepcion e){
            logger.debug("Fallo en la comunicacion con el servicio :" + e);     
            System.out.println ("Fallo:"+e);
        } catch (Exception ex){
            logger.error("Fallo inesperado al actualzar los estados de las notificaciones:" +ex);       
             System.out.println ("Fallo:"+ex);
        }
		Date date2 = new Date();
		if(logger.isDebugEnabled()){
			StringBuffer sb  = new StringBuffer("Salgo en ");
			logger.debug(sb.append(date2.getTime()-date.getTime()).append(" milisegundos").toString());
		}
	}
}
