package es.ieci.tecdoc.fwktd.csv.api.xml.connectionConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.core.model.BaseValueObject;

/**
 * Información de configuracion de conexión a aplicaciones externas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ConfiguracionConexion extends BaseValueObject {

	private static final long serialVersionUID = -3356945563240982687L;

	/**
     * Nombre del conector con la aplicación externa.
     */
	private String conector = null;

    /**
     * Parámetros de configuración.
     */
    private Map<String, String> parametros = new HashMap<String, String>();

    /**
     * Constructor.
     */
    public ConfiguracionConexion() {
        super();
    }

    public String getConector() {
		return conector;
	}

	public void setConector(String conector) {
		this.conector = conector;
	}

	public Map<String, String> getParametros() {
		return parametros;
	}

    public String asXML() {

        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("UTF-8");

        // connection-config
        Element rootNode = doc.addElement("connection-config");

        // connector
        Element connectorElement = rootNode.addElement("connector");
        connectorElement.addText(getConector());

        if (!getParametros().isEmpty()) {

        	// parameters
        	Element parametersElement = rootNode.addElement("parameters");

        	for (Entry<String, String> parameter : getParametros().entrySet()) {

        		// parameter
        		Element parameterElement = parametersElement.addElement("parameter");
        		parameterElement.addAttribute("name", parameter.getKey());
        		parameterElement.addCDATA(parameter.getValue());
        	}
        }

        return doc.asXML();
    }

    public static ConfiguracionConexion parseText(String text) throws DocumentException {

    	Assert.hasText(text, "'text' must not be empty");

		Document doc = DocumentHelper.parseText(text);

		ConfiguracionConexion configuracionConexion = new ConfiguracionConexion();
		configuracionConexion.setConector(doc.valueOf("/connection-config/connector"));

		@SuppressWarnings("unchecked")
		List<Node> nodes = doc.selectNodes("/connection-config/parameters/parameter");
		if (nodes != null) {
			for (Node node : nodes) {
				String name = node.valueOf("@name");
				if (StringUtils.isNotBlank(name)) {
					configuracionConexion.getParametros().put(name, node.getText());
				}
			}
		}

		return configuracionConexion;
    }

}
