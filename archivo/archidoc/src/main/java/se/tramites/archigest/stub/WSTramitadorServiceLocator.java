package se.tramites.archigest.stub;

import java.util.Collection;

import javax.xml.namespace.QName;

import org.apache.axis.client.Service;
import org.apache.axis.wsdl.gen.Parser;
import org.apache.log4j.Logger;

public class WSTramitadorServiceLocator {

	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(WSTramitadorServiceLocator.class);

	/**
	 * Obtiene el servicio.
	 * 
	 * @return Servicio.
	 */
	public static WSTramitador getTramitadorService(String wsdlLocation)
			throws Exception {
		if (logger.isDebugEnabled())
			logger.debug("WSDL Location: " + wsdlLocation);

		return new WSTramitadorStub(getService(wsdlLocation));
	}

	/**
	 * Crear el servicio.
	 * 
	 * @param wsdlLocation
	 *            Localización del WSDL.
	 * @return Servicio.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	protected static Service getService(String wsdlLocation) throws Exception {
		// Parser del WSDL
		Parser parser = getParser(wsdlLocation);

		// Crear el servicio
		return new Service(parser, getServiceQName(parser));
	}

	/**
	 * Obtiene el parser del WSDL.
	 * 
	 * @param wsdlLocation
	 *            URL del WSDL.
	 * @return Parser del WSDL
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	protected static Parser getParser(String wsdlLocation) throws Exception {
		Parser parser = new Parser();
		parser.run(wsdlLocation);

		if (logger.isDebugEnabled())
			logger.debug("Parser cargado: " + parser.getWSDLURI());

		return parser;
	}

	/**
	 * Obtiene el nombre cualificado del servicio.
	 * 
	 * @param parser
	 *            Parser del WSDL.
	 * @return Nombre cualificado del servicio.
	 */
	protected static QName getServiceQName(Parser parser) {
		// Espacio de nombres del servicio web
		String targetNamespace = parser.getCurrentDefinition()
				.getTargetNamespace();
		if (logger.isDebugEnabled())
			logger.debug("TargetNamespace: " + targetNamespace);

		Collection serviceCollection = parser.getCurrentDefinition()
				.getServices().values();
		javax.wsdl.Service service = (javax.wsdl.Service) serviceCollection
				.iterator().next();
		if (logger.isDebugEnabled())
			logger.debug("Servicio: " + service.getQName().toString());

		return service.getQName();
	}
}