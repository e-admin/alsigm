package es.ieci.tecdoc.fwktd.sir.ws.manager.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.sir.core.exception.ValidacionException;
import es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral;
import es.ieci.tecdoc.fwktd.sir.core.types.ErroresEnum;
import es.ieci.tecdoc.fwktd.sir.ws.exception.ServiceException;
import es.ieci.tecdoc.fwktd.sir.ws.manager.EnvioManager;

/**
 * Implementación del gestor de envíos de ficheros de intercambio y mensajes de
 * datos de control a la aplicación.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class EnvioManagerImpl implements EnvioManager {

    /**
     * Logger de la clase.
     */
    private static final Logger logger = LoggerFactory.getLogger(EnvioManagerImpl.class);

    /**
     * Servicio de intercambio registral.
     */
    private ServicioIntercambioRegistral servicioIntercambioRegistral = null;

    /**
     * Constructor.
     */
    public EnvioManagerImpl() {
    	super();
    }

    public ServicioIntercambioRegistral getServicioIntercambioRegistral() {
		return servicioIntercambioRegistral;
	}

	public void setServicioIntercambioRegistral(
			ServicioIntercambioRegistral servicioIntercambioRegistral) {
		this.servicioIntercambioRegistral = servicioIntercambioRegistral;
	}

	/**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.sir.ws.manager.EnvioManager#envioFichero(java.lang.String, java.lang.String, java.util.List)
     */
    public void envioFichero(String ficheroIntercambio,
            String firmaFicheroIntercambio, List<byte[]> documentos) {

        logger.info("Llamada al método envioFichero");
		logger.debug("envioFichero: ficheroIntercambio=[{}], documentos=[]",
				ficheroIntercambio,
				(documentos != null ? documentos.size() : 0));

		try {

			// Recibir asiento registral
			getServicioIntercambioRegistral().recibirFicheroIntercambio(ficheroIntercambio, documentos);

		} catch (IllegalArgumentException e) {
			logger.error("Error en la recepción del fichero de datos de intercambio", e);
			throw new ServiceException(ErroresEnum.ERROR_0065, e);
		} catch (ValidacionException e) {
			logger.error("Error en la recepción del fichero de datos de intercambio", e);
			throw new ServiceException(e.getErrorValidacion(), e.getErrorException());
		}
    }

    /**
     * {@inheritDoc}
     *
     * @see es.ieci.tecdoc.fwktd.sir.ws.manager.EnvioManager#envioMensaje(java.lang.String,
     *      java.lang.String)
     */
    public void envioMensaje(String mensaje, String firma) {

        logger.info("Llamada al método envioMensaje");
		logger.debug("envioMensaje: mensaje=[{}]", mensaje);

		try {

			// Recibir el mensaje
			getServicioIntercambioRegistral().recibirMensaje(mensaje);

		} catch (IllegalArgumentException e) {
			logger.error("Error en la recepción del fichero de datos de intercambio", e);
			throw new ServiceException(ErroresEnum.ERROR_0064, e);
		} catch (ValidacionException e) {
			logger.error("Error en la recepción del fichero de datos de intercambio", e);
			throw new ServiceException(e.getErrorValidacion(), e.getErrorException());
		}
    }

}
