package es.ieci.tecdoc.fwktd.sir.api.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import es.ieci.tecdoc.fwktd.sir.api.manager.AsientoRegistralManager;

/**
 * Job para reintentar validar los asientos registrales que están en el estado
 * REINTENTAR_VALIDACION
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ReintentoValidacionAsientosJob extends QuartzJobBean {

	private static Logger logger = LoggerFactory
			.getLogger(ReintentoValidacionAsientosJob.class);

	private AsientoRegistralManager asientoRegistralManager;


	/**
	 * Constructor.
	 */
	public ReintentoValidacionAsientosJob() {
		super();
	}


	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {

		logger.info("Inicio del job para reintentar la validacion de asientos registrales");

		try {
			getAsientoRegistralManager().reintentarValidarAsientosRegistrales();
			context.setResult("Ok");
		} catch (Throwable e) {
			logger.error(
					"Error al reintentar la validacion asientos registrales", e);
			context.setResult("Error al reintentar la validacion de asientos registrales: "
					+ e.toString());
			throw new JobExecutionException(
					"Error al reintentar la validacion asientos registrales",
					e);
		} finally {
			logger.info("Fin del job para reintentar la validacion de asientos registrales");
		}
	}




	public AsientoRegistralManager getAsientoRegistralManager() {
		return asientoRegistralManager;
	}




	public void setAsientoRegistralManager(
			AsientoRegistralManager asientoRegistralManager) {
		this.asientoRegistralManager = asientoRegistralManager;
	}


}
