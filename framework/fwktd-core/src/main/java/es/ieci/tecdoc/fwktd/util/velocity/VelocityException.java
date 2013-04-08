package es.ieci.tecdoc.fwktd.util.velocity;

import es.ieci.tecdoc.fwktd.core.exception.FrameworkException;

/**
 * Excepción que se lanza durante la generación de contenido usando el motor de
 * plantillas Apache Velocity con el soporte del framework.
 * 
 * @author IECISA
 * 
 */
public class VelocityException extends FrameworkException {

	public VelocityException(String msg, Exception e) {
		super(msg, e);
	}

	public VelocityException(String msg) {
		super(msg);
	}

	// Members
	private static final long serialVersionUID = 2716004992086165125L;

}
