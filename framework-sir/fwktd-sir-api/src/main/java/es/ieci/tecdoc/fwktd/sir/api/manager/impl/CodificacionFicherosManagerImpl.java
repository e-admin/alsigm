package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import com.ibm.icu.util.Calendar;

import es.ieci.tecdoc.fwktd.sir.api.manager.CodificacionFicherosManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.ContadorManager;
import es.ieci.tecdoc.fwktd.sir.api.types.TipoContadorEnum;
import es.ieci.tecdoc.fwktd.sir.api.vo.MensajeVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;

/**
 * Implementación del manager de codificación de ficheros.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CodificacionFicherosManagerImpl implements
		CodificacionFicherosManager {

	/**
	 * Gestor de contadores.
	 */
	private ContadorManager contadorManager = null;

	/**
	 * Constructor.
	 */
	public CodificacionFicherosManagerImpl() {
		super();
	}

	public ContadorManager getContadorManager() {
		return contadorManager;
	}

	public void setContadorManager(ContadorManager contadorManager) {
		this.contadorManager = contadorManager;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.CodificacionFicherosManager#getIdentificadorIntercambio(es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO)
	 */
	public String getIdentificadorIntercambio(AsientoRegistralVO asiento) {
		return asiento.getCodigoEntidadRegistralOrigen() + "_"
				+ getCurrentYear() + "_"
				+ getContadorManager().updateContador(asiento.getCodigoEntidadRegistralOrigen(), TipoContadorEnum.IDENTIFICADOR_INTERCAMBIO);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.CodificacionFicherosManager#getCodificacionMensaje(es.ieci.tecdoc.fwktd.sir.api.vo.MensajeVO)
	 */
	public String getCodificacionMensaje(MensajeVO mensaje) {
		String codificacion = mensaje.getCodigoEntidadRegistralOrigen() + "_"
				+ getCurrentYear() + "_"
				+ getContadorManager().updateContador(mensaje.getCodigoEntidadRegistralOrigen(), TipoContadorEnum.MENSAJE);

		return codificacion;
	}

	private static String getCurrentYear() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		return String.valueOf(year).substring(2);
	}

}
