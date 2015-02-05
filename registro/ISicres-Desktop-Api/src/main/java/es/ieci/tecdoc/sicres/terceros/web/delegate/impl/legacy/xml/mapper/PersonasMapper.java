package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper;

import java.io.StringReader;

import org.apache.commons.digester.Digester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.ruleset.PersonaRuleSet;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Personas;

/**
 *
 * @author IECISA
 *
 */
public class PersonasMapper implements Unmarshaller {

	public Personas unmarshall(String xml) {
		try {
			Digester digester = new Digester();
			digester.setValidating(false);

			digester.addObjectCreate("Personas", Personas.class);
			digester.addSetProperties("Personas");

			digester.addRuleSet(new PersonaRuleSet());

			digester.addSetNext("*/Persona", "addPersona");

			return (Personas) digester.parse(new StringReader(xml));
		} catch (Exception e) {
			StringBuffer message = new StringBuffer();

			logger.error(message.toString(), e);
		}
		return null;
	}

	protected static final Logger logger = LoggerFactory
			.getLogger(PersonasMapper.class);
}
