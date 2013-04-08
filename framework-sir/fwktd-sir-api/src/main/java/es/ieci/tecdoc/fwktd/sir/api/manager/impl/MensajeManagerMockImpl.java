package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.sir.api.vo.MensajeVO;
import es.ieci.tecdoc.fwktd.sir.core.util.ToStringLoggerHelper;

/** 
 * Implementación MOCK del manager de envío de ficheros de datos de intercambio
 * en formato SICRES 3.0 generado por la aplicación de registro.

 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class MensajeManagerMockImpl extends MensajeManagerImpl {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory.getLogger(MensajeManagerMockImpl.class);

	/**
	 * Constructor.
	 */
	public MensajeManagerMockImpl() {
		super();
	}

	/**
	 * Envía el fichero de datos de control al nodo distribuido asociado.
	 *
	 * @param mensaje
	 *            Información de mensaje SICRES 3.0.
	 */
	protected void enviar(MensajeVO mensaje) {

		logger.warn("Se está utilizando la implementación MOCK de MensajeManager !!!");
		
		if (logger.isInfoEnabled()){
			logger.info("Llamada a enviar: [{}]", ToStringLoggerApiHelper.toStringLogger(mensaje));
		}

		// Componer el XML del mensaje en formato SICRES 3.0
		logger.debug("XML mensaje: {}",
				getSicresXMLManager().createXMLMensaje(mensaje));
	}

}
