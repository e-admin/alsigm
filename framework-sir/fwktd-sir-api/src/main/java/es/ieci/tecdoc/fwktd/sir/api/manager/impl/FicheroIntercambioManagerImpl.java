package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.sir.api.manager.AuditoriaFicheroIntercambioManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.CodificacionFicherosManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.FicheroIntercambioManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.SicresXMLManager;
import es.ieci.tecdoc.fwktd.sir.api.types.BandejaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoAnotacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.util.ToStringLoggerHelper;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;

/**
 * Implementación abstracta del manager de envío de ficheros de datos de
 * intercambio en formato SICRES 3.0 generado por la aplicación de registro.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public abstract class FicheroIntercambioManagerImpl implements
		FicheroIntercambioManager {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(FicheroIntercambioManagerImpl.class);

	/**
	 * Gestor de XML de SICRES.
	 */
	private SicresXMLManager sicresXMLManager = null;

    /**
     * Gestor de auditoría de ficheros de datos de intercambio.
     */
    private AuditoriaFicheroIntercambioManager auditoriaFicheroIntercambioManager = null;

	/**
	 * Gestor de codificación de ficheros.
	 */
	private CodificacionFicherosManager codificacionFicherosManager = null;

    /**
     * Constructor.
     */
    public FicheroIntercambioManagerImpl() {
    	super();
    }

	public SicresXMLManager getSicresXMLManager() {
		return sicresXMLManager;
	}

	public void setSicresXMLManager(SicresXMLManager sicresXMLManager) {
		this.sicresXMLManager = sicresXMLManager;
	}

	public CodificacionFicherosManager getCodificacionFicherosManager() {
		return codificacionFicherosManager;
	}

	public AuditoriaFicheroIntercambioManager getAuditoriaFicheroIntercambioManager() {
		return auditoriaFicheroIntercambioManager;
	}

	public void setAuditoriaFicheroIntercambioManager(
			AuditoriaFicheroIntercambioManager auditoriaFicheroIntercambioManager) {
		this.auditoriaFicheroIntercambioManager = auditoriaFicheroIntercambioManager;
	}

	public void setCodificacionFicherosManager(
			CodificacionFicherosManager codificacionFicherosManager) {
		this.codificacionFicherosManager = codificacionFicherosManager;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.FicheroIntercambioManager#enviarFicheroIntercambio(es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO)
	 */
	public void enviarFicheroIntercambio(AsientoRegistralVO asiento) {

		if (logger.isInfoEnabled()){
			logger.info("Llamada a enviarFicheroIntercambio: [{}]", ToStringLoggerHelper.toStringLogger(asiento));
		}

		// Comprobar el identificador de intercambio
		if (StringUtils.isBlank(asiento.getIdentificadorIntercambio())) {
			asiento.setIdentificadorIntercambio(getCodificacionFicherosManager().getIdentificadorIntercambio(asiento));
		}
		logger.info("Identificador de intercambio [{}]", asiento.getIdentificadorIntercambio());
		
		// Establecer el tipo de anotación
		asiento.setTipoAnotacion(TipoAnotacionEnum.ENVIO);

		// Validar la información del asiento registral
		getSicresXMLManager().validarAsientoRegistral(asiento);

		enviar(asiento);

        // Audita el fichero de datos de intercambio
    	getAuditoriaFicheroIntercambioManager().audita(asiento, BandejaEnum.ENVIADOS);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.FicheroIntercambioManager#reenviarFicheroIntercambio(es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO)
	 */
	public void reenviarFicheroIntercambio(AsientoRegistralVO asiento) {

		if (logger.isInfoEnabled()){
			logger.info("Llamada a reenviarFicheroIntercambio: [{}]", ToStringLoggerHelper.toStringLogger(asiento));
		}

		// Establecer el tipo de anotación
		asiento.setTipoAnotacion(TipoAnotacionEnum.REENVIO);

		// Validar la información del asiento registral
		getSicresXMLManager().validarAsientoRegistral(asiento);

		enviar(asiento);

        // Audita el fichero de datos de intercambio
    	getAuditoriaFicheroIntercambioManager().audita(asiento, BandejaEnum.ENVIADOS);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.FicheroIntercambioManager#rechazarFicheroIntercambio(es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO)
	 */
	public void rechazarFicheroIntercambio(AsientoRegistralVO asiento) {

		if (logger.isInfoEnabled()){
			logger.info("Llamada a rechazarFicheroIntercambio: [{}]", ToStringLoggerHelper.toStringLogger(asiento));
		}

		// Establecer el tipo de anotación
		asiento.setTipoAnotacion(TipoAnotacionEnum.RECHAZO);

		// Validar la información del asiento registral
		getSicresXMLManager().validarAsientoRegistral(asiento);

		enviar(asiento);

        // Audita el fichero de datos de intercambio
    	getAuditoriaFicheroIntercambioManager().audita(asiento, BandejaEnum.ENVIADOS);
	}

	/**
	 * Envía un fichero de intercambio.
	 *
	 * @param asiento
	 *            Información del asiento registral.
	 */
	protected abstract void enviar(AsientoRegistralVO asiento);
}
