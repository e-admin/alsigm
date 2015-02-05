package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper;

import java.io.StringReader;

import org.apache.commons.digester.Digester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.ruleset.DomicilioRuleSet;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.ruleset.EDireccionRuleSet;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Direcciones;

/**
 *
 * @author IECISA
 *
 */
public class DireccionesMapper implements Unmarshaller {

	public Direcciones unmarshall(String xml) {

		try {
			Digester digester = new Digester();
			digester.setValidating(false);

			digester.addObjectCreate("*/Domicilios", Direcciones.class);
			digester.addObjectCreate("*/Telematicas", Direcciones.class);
			digester.addRuleSet(new DomicilioRuleSet());
			digester.addRuleSet(new EDireccionRuleSet());
			digester.addSetNext("*/Domicilios/Domicilio", "addDireccion");
			digester.addSetNext("*/Telematicas/Domicilio", "addDireccion");

			return (Direcciones) digester.parse(new StringReader(xml));
		} catch (Exception e) {
			StringBuffer message = new StringBuffer();

			logger.error(message.toString(), e);
		}
		return null;
	}

	protected static final Logger logger = LoggerFactory
			.getLogger(DireccionesMapper.class);
}
