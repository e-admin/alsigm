package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper;

import java.io.StringReader;

import org.apache.commons.digester.Digester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.ruleset.ProvinciaRuleSet;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Provincias;

/**
 *
 * @author IECISA
 *
 */
public class ProvinciasMapper implements Unmarshaller {

	public Provincias unmarshall(String xml) {
		try {
			Digester digester = new Digester();
			digester.setValidating(false);

			digester.addObjectCreate("Provincias", Provincias.class);
			digester.addRuleSet(new ProvinciaRuleSet());
			digester.addSetNext("*/Provincia", "addProvincia");

			return (Provincias) digester.parse(new StringReader(xml));
		} catch (Exception e) {
			StringBuffer message = new StringBuffer();

			logger.error(message.toString(), e);
		}
		return null;
	}

	protected static final Logger logger = LoggerFactory
			.getLogger(ProvinciasMapper.class);

}
