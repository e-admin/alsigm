package ieci.tecdoc.sgm.scheduler.job.consolidacion;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.consolidacion.ServicioConsolidacion;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.Trigger;

public class ConsolidacionJob implements Job {

	/**
	 * Constructor.
	 */
	public ConsolidacionJob() {
		super();
	}
	
    /**
     * Método llamado por el <code>{@link Scheduler}</code> cuando se lanza un <code>{@link Trigger}</code>
     * asociado con el <code>Job</code>.
     * 
     * @throws JobExecutionException
     *           si ocurre algún error.
     */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		try {
			
			/*
				Llamada al proceso de consolidación para todas las entidades definidas
				en el sistema.
			*/ 
			ServicioConsolidacion servicio = LocalizadorServicios.getServicioConsolidacion();
			servicio.consolida();
			
		} catch (SigemException e) {
			throw new JobExecutionException("Error en el proceso de consolidación", e);
		}
		
	}

}
