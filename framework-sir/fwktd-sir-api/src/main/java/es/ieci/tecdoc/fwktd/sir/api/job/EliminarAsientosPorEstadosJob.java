package es.ieci.tecdoc.fwktd.sir.api.job;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral;
import es.ieci.tecdoc.fwktd.sir.core.types.CriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.OperadorCriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriterioVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO;

/**
 * Job para eliminar asientos registrales por su estado.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class EliminarAsientosPorEstadosJob extends QuartzJobBean {

	private static Logger logger = LoggerFactory
			.getLogger(EliminarAsientosPorEstadosJob.class);

	/**
	 * Servicio de intercambio registral.
	 */
	private ServicioIntercambioRegistral servicioIntercambioRegistral = null;

	/**
	 * Códigos de los estados de asientos registrales.
	 */
	private int[] codigosEstado = null;

	/**
	 * Constructor.
	 */
	public EliminarAsientosPorEstadosJob() {
		super();
	}

	public ServicioIntercambioRegistral getServicioIntercambioRegistral() {
		return servicioIntercambioRegistral;
	}

	public void setServicioIntercambioRegistral(
			ServicioIntercambioRegistral servicioIntercambioRegistral) {
		this.servicioIntercambioRegistral = servicioIntercambioRegistral;
	}

	public int[] getCodigosEstado() {
		return codigosEstado;
	}

	public void setCodigosEstado(int[] codigosEstado) {
		this.codigosEstado = codigosEstado;
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {

		logger.info("Inicio del job para eliminar asientos registrales por estados");

		try {

			// Obtener los estados de los asientos a eliminar
			EstadoAsientoRegistralEnum[] estados = getEstados();
			logger.info("Se eliminarán los asientos registrales con estados: {}",
					StringUtils.join(estados, ", "));

			// Obtener los asientos registrales por estado
			List<AsientoRegistralVO> asientos = getServicioIntercambioRegistral()
					.findAsientosRegistrales(new CriteriosVO()
						.addCriterioVO(new CriterioVO(
			        			CriterioEnum.ASIENTO_ESTADO,
								OperadorCriterioEnum.IN,
								estados)));

			if (CollectionUtils.isNotEmpty(asientos)) {

				logger.info("Eliminando {} asiento/s registral/es", asientos.size());

				for (AsientoRegistralVO asiento : asientos) {

					logger.info("Eliminando el asiento con identificador [{}]", asiento.getId());

					// Eliminar el asiento registral
					getServicioIntercambioRegistral().deleteAsientoRegistral(asiento.getId());

					logger.info("Asiento con identificador [{}] eliminado", asiento.getId());
				}
			}

			context.setResult("Ok");

		} catch (Throwable e) {
			logger.error("Error al eliminar asientos registrales por estados",
					e);
			context.setResult("Error al eliminar asientos registrales por estados: "
					+ e.toString());
			throw new JobExecutionException(
					"Error al eliminar asientos registrales por estados", e);
		} finally {
			logger.info("Fin del job para la recepción de ficheros de intercambio mediante el sistema de ficheros");
		}
	}

	protected EstadoAsientoRegistralEnum[] getEstados() {

		List<EstadoAsientoRegistralEnum> estados = new ArrayList<EstadoAsientoRegistralEnum>();

		Assert.isTrue(ArrayUtils.isNotEmpty(getCodigosEstado()), "'codigosEstados' must not be empty");

		for (int codigoEstado : getCodigosEstado()) {
			EstadoAsientoRegistralEnum estado = EstadoAsientoRegistralEnum.getEstadoAsientoRegistral(codigoEstado);
			if (estado != null) {
				estados.add(estado);
			}
		}

		return estados.toArray(new EstadoAsientoRegistralEnum[estados.size()]);
	}

}
