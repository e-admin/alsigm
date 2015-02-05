package es.ieci.tecdoc.fwktd.sir.ws.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.sir.core.types.ErroresEnum;
import es.ieci.tecdoc.fwktd.sir.ws.exception.ServiceException;
import es.ieci.tecdoc.fwktd.sir.ws.manager.EnvioManager;
import es.ieci.tecdoc.fwktd.sir.ws.service.wssir9.RespuestaWS;
import es.ieci.tecdoc.fwktd.sir.ws.service.wssir9.WS_SIR9_PortType;

/**
 * Implementación del servicio de envío de mensaje de datos de control (WS_SIR9)
 * en formato SICRES 3.0 a la aplicación destino, desde su nodo distribuido
 * asociado.
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class WSSIR9ServiceImpl implements WS_SIR9_PortType {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory.getLogger(WSSIR9ServiceImpl.class);

	/**
	 * Gestor de envíos de ficheros de intercambio y mensajes.
	 */
	protected EnvioManager envioManager = null;

	/**
	 * Constructor.
	 */
	public WSSIR9ServiceImpl() {
		super();
	}

	public EnvioManager getEnvioManager() {
		return envioManager;
	}

	public void setEnvioManager(EnvioManager envioManager) {
		this.envioManager = envioManager;
	}

	/**
	 * Envía un mensaje de datos de control a la aplicación.
	 * 
	 * @param mensaje
	 *            Fichero de mensaje SICRES 3.0 en formato String.
	 * @param firma
	 *            Firma del contenido del mensaje.
	 * @return Código y descripción del error producido durante la ejecución. En
	 *         caso de que todo vaya correcto, devolverá "00" y "Exito".
	 */
	public RespuestaWS envioMensajeDatosControlAAplicacion(String mensaje,
			String firma) {

		RespuestaWS respuesta = null;

		logger.info("Llamada al servicio envioMensajeDatosControlAAplicacion");

		try {

			// Enviar el mensaje de datos de control a la aplicación
			getEnvioManager().envioMensaje(mensaje, firma);

			respuesta = createRespuestaWS(ErroresEnum.OK);

		} catch (ServiceException e) {
			logger.error("Error en el envío del mensaje de datos de control a la aplicación", e);
			respuesta = createRespuestaWS(e.getError());
		} catch (Throwable e) {
			logger.error("Error en el envío del mensaje de datos de control a la aplicación", e);
			respuesta = createRespuestaWS(ErroresEnum.ERROR_INESPERADO);
		}

		logger.info(
				"Respuesta de envioFicherosAAplicacion: codigo=[{}], descripcion=[{}]",
				respuesta.getCodigo(), respuesta.getDescripcion());

		return respuesta;
	}

	/**
	 * Crea la respuesta de retorno del servicio.
	 *
	 * @param error
	 *            Información del error.
	 * @return Información de respuesta.
	 */
	protected static RespuestaWS createRespuestaWS(ErroresEnum error) {
		RespuestaWS respuesta = new RespuestaWS();
		respuesta.setCodigo(error.getValue());
		respuesta.setDescripcion(error.getName());
		return respuesta;
	}
}
