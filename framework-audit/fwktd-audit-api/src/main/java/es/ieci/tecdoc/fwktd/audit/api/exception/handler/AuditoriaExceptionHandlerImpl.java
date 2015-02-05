package es.ieci.tecdoc.fwktd.audit.api.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.audit.api.service.impl.ServicioAuditoriaImpl;
import es.ieci.tecdoc.fwktd.audit.core.exception.AuditoriaException;
import es.ieci.tecdoc.fwktd.audit.core.exception.handler.AuditoriaExceptionHandler;

public class AuditoriaExceptionHandlerImpl implements AuditoriaExceptionHandler {

	private Logger logger = LoggerFactory
			.getLogger(ServicioAuditoriaImpl.class);

	public void handleException(Exception e, String message)
			throws AuditoriaException {

		logger.error(message, e);
		throw new AuditoriaException(message, e);

	}

}
