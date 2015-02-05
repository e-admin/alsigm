package es.ieci.tecdoc.isicres.api.intercambioregistral.scheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.Trigger;

import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralActualizadorEstadosManager;

public class UpdateStatesSIRJob implements Job {


	/**
	 * Constructor.
	 */
	public UpdateStatesSIRJob() {
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

			IntercambioRegistralActualizadorEstadosManager manager = IsicresManagerProvider.getInstance().getIntercambioRegistralActualizadorEstadosManager();
			manager.actualizarEstadoEnviados();
			
			
		} catch (Exception e) {
			throw new JobExecutionException("Error en el proceso de ACTUALIZACION DE ESTADOS DE INTERCAMBIO REGISTRAL", e);
		}
		
	}

}
