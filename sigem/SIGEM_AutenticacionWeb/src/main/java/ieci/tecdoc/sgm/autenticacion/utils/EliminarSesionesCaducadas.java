package ieci.tecdoc.sgm.autenticacion.utils;

import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.core.admin.web.AdministracionHelper;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.sesion.ServicioSesionUsuario;
import ieci.tecdoc.sgm.core.services.sesion.SesionUsuarioException;

public class EliminarSesionesCaducadas extends TimerTask {
	private static final Logger logger = Logger.getLogger(EliminarSesionesCaducadas.class);
	private int periodo = 0;

	public EliminarSesionesCaducadas(int periodo){
		this.periodo = periodo;
		logger.debug("EliminarSesionesCaducadas <-- Periodo de comprobación: " + periodo + " minutos");
	}

	public void run() {
		try{
			logger.debug("EliminarSesionesCaducadas <-- Borrado de sesiones caducadas");
			List oLista = AdministracionHelper.obtenerListaEntidades();
			if( (oLista != null) && (oLista.size() > 0) ){
				ServicioSesionUsuario oServicio = LocalizadorServicios.getServicioSesionUsuario();
				Iterator oIterator = oLista.iterator();
				Entidad oEntidad = null;
				while(oIterator.hasNext()){
					oEntidad = (Entidad)oIterator.next();
					logger.debug("EliminarSesionesCaducadas <-- Borrado de sesiones caducadas de la entidad " + oEntidad.getIdentificador());

					try {
						oServicio.limpiarSesiones(periodo, oEntidad);
					} catch (SesionUsuarioException sue) {
						logger.debug("EliminarSesionesCaducadas: Se ha producido un error eliminando sesiones --> " + sue.getMessage() + " en la entidad --> " + oEntidad.getIdentificador());
					}
				}
			}
		}catch(Exception e){
			logger.debug("EliminarSesionesCaducadas: Se ha producido un error eliminando sesiones --> " + e.toString());
		}
	}
}
