package es.ieci.tecdoc.fwktd.sir.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.axis.attachments.OctetStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.sir.core.types.ErroresEnum;
import es.ieci.tecdoc.fwktd.sir.ws.exception.ServiceException;
import es.ieci.tecdoc.fwktd.sir.ws.manager.EnvioManager;
import es.ieci.tecdoc.fwktd.sir.ws.service.wssir8a.RespuestaWS;
import es.ieci.tecdoc.fwktd.sir.ws.service.wssir8a.WS_SIR8_A_PortType;

/**
 * Implementación del servicio de envío de fichero de datos de intercambio
 * (WS_SIR8_A) en formatos SICRES 3.0 a la aplicación destino, desde su nodo
 * distribuido asociado.
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class WSSIR8AServiceImpl implements WS_SIR8_A_PortType {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory.getLogger(WSSIR8AServiceImpl.class);

	/**
	 * Gestor de envíos de ficheros de intercambio y mensajes.
	 */
	protected EnvioManager envioManager = null;

	/**
	 * Constructor.
	 */
	public WSSIR8AServiceImpl() {
		super();
	}

	public EnvioManager getEnvioManager() {
		return envioManager;
	}

	public void setEnvioManager(EnvioManager envioManager) {
		this.envioManager = envioManager;
	}

	/**
	 * Envía un fichero de intercambio a la aplicación.
	 * 
	 * @param ficheroIntercambio
	 *            Fichero de intercambio SICRES 3.0 en formato String.
	 * @param firmaFicheroIntercambio
	 *            Firma del contenido del registro.
	 * @param documentos
	 *            Documentos anexos al registro.
	 * @return Código y descripción del error producido durante la ejecución. En
	 *         caso de que todo vaya correcto, devolverá "00" y "Exito".
	 */
	public RespuestaWS envioFicherosAAplicacion(String ficheroIntercambio,
			String firmaFicheroIntercambio, OctetStream[] documentos) {

		RespuestaWS respuesta = null;

		logger.info("Llamada al servicio envioFicherosAAplicacion");

		try {

			// Enviar el fichero de intercambio a la aplicación
			getEnvioManager().envioFichero(ficheroIntercambio,
					firmaFicheroIntercambio, getDocumentos(documentos));

			respuesta = createRespuestaWS(ErroresEnum.OK);

		} catch (ServiceException e) {
			logger.error("Error en el envío del fichero de intercambio a la aplicación", e);
			respuesta = createRespuestaWS(e.getError());
		} catch (Throwable e) {
			logger.error("Error en el envío del fichero de intercambio a la aplicación", e);
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

	protected static List<byte[]> getDocumentos(OctetStream[] documentos) {

		List<byte[]> listaDocumentos = new ArrayList<byte[]>();

		if (documentos != null) {
			for (OctetStream documento : documentos) {
				listaDocumentos.add(documento.getBytes());
			}
		}

		return listaDocumentos;
	}

}
