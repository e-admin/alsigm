package ieci.tecdoc.sgm.registropresencial.job;

import java.util.List;
import java.util.ListIterator;

import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.entidades.Entidad;
import ieci.tecdoc.sgm.core.services.entidades.ServicioEntidades;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.Trigger;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralActualizadorEstadosManager;


public class UpdateStatesSIRJobSigem implements Job {


	/**
	 * Constructor.
	 */
	public UpdateStatesSIRJobSigem() {
		super();
	}
	
    /**
     * M�todo llamado por el <code>{@link Scheduler}</code> cuando se lanza un <code>{@link Trigger}</code>
     * asociado con el <code>Job</code>.
     * 
     * @throws JobExecutionException
     *           si ocurre alg�n error.
     */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		try {

			ServicioEntidades servicio = LocalizadorServicios.getServicioEntidades();
			List<Entidad> listaEntidades = servicio.obtenerEntidades();
			ListIterator<Entidad> itr = listaEntidades.listIterator();
			while(itr.hasNext())
			{
				
				Entidad entidad = itr.next();
				MultiEntityContextHolder.setEntity(entidad.getIdentificador());
				IntercambioRegistralActualizadorEstadosManager manager = IsicresManagerProvider.getInstance().getIntercambioRegistralActualizadorEstadosManager();
				manager.actualizarEstadoEnviados();
			}
	
		} catch (Exception e) {
			throw new JobExecutionException("Error en el proceso de UpdateStatesSIRJobSigem", e);
		}
		
	}

}
