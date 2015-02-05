package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper;

import java.io.StringReader;

import org.apache.commons.digester.Digester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.ruleset.DomicilioRuleSet;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Domicilios;

/**
 *
 * @author IECISA
 *
 */
public class DomiciliosMapper implements Unmarshaller {

	public Domicilios unmarshall(String xml) {
		try {
			Digester digester = new Digester();
			digester.setValidating(false);

			digester.addObjectCreate("Domicilios", Domicilios.class);
			digester.addRuleSet(new DomicilioRuleSet());
			digester.addSetNext("*/Domicilio", "addDomicilio");

			return (Domicilios) digester.parse(new StringReader(xml));
		} catch (Exception e) {
			StringBuffer message = new StringBuffer();

			logger.error(message.toString(), e);
		}
		return null;
	}

	protected static final Logger logger = LoggerFactory
			.getLogger(DomiciliosMapper.class);

}
