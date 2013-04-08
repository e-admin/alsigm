package xml.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import se.autenticacion.idoc.InvesDocConnector;

/* Clase de prueba para cargar xml */
public class TestLoadXml {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TestLoadXml.class);

	public static void main(String[] args) {

		try {
			// Fichero de configuración de la búsqueda
			InputStream file = InvesDocConnector.class.getClassLoader()
					.getResourceAsStream("test/bandeja/bandeja_simple.xml");

			// URL del fichero de reglas
			URL rulesXmlUrl = InvesDocConnector.class.getClassLoader()
					.getResource("rules_busqueda.xml");

			// Cargar la configuración
			Digester digester = DigesterLoader.createDigester(rulesXmlUrl);
			Busqueda busqueda = (Busqueda) digester.parse(file);
			logger.debug(busqueda);
		} catch (FileNotFoundException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} catch (SAXException e) {
			logger.error(e);
			;
		}
	}
}
