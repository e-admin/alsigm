package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper;

import java.io.StringReader;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.fwktd.util.velocity.VelocityEngine;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.ruleset.DomicilioRuleSet;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.ruleset.EDireccionRuleSet;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.ruleset.PersonaRuleSet;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Domicilios;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.EDirecciones;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Persona;

/**
 *
 * @author IECISA
 *
 */
public class PersonaMapper implements Marshaller, Unmarshaller {

	public String marshall(Entity entity, Map<String, Object> context) {
		VelocityEngine engine = new VelocityEngine();
		VelocityContext ctx = new VelocityContext(context);
		ctx.put("entity", entity);

		return engine
				.merge("es/ieci/tecdoc/isicres/terceros/web/delegate/impl/legacy/xml/person.vm",
						ctx);
	}

	public Persona unmarshall(String xml) {
		try {
			Digester digester = new Digester();
			digester.setValidating(false);

			digester.addRuleSet(new PersonaRuleSet());

			digester.addObjectCreate("*/Domicilios", Domicilios.class);
			digester.addRuleSet(new DomicilioRuleSet());
			digester.addSetNext("*/Domicilio", "addDomicilio");
			digester.addSetNext("*/Domicilios", "setDomicilios");

			digester.addObjectCreate("*/Telematicas", EDirecciones.class);
			digester.addRuleSet(new EDireccionRuleSet());
			digester.addSetNext("*/Telematica", "addEDireccion");
			digester.addSetNext("*/Telematicas", "setEdirecciones");

			return (Persona) digester.parse(new StringReader(xml));
		} catch (Exception e) {
			StringBuffer message = new StringBuffer();

			logger.error(message.toString(), e);
		}
		return null;
	}

	protected static final Logger logger = LoggerFactory
			.getLogger(PersonaMapper.class);

}
