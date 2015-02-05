package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper;

import java.io.StringReader;

import org.apache.commons.digester.Digester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.ruleset.CiudadRuleSet;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Ciudades;

/**
 *
 * @author IECISA
 *
 */
public class CiudadesMapper implements Unmarshaller {

	public Ciudades unmarshall(String xml) {
		try {
			Digester digester = new Digester();
			digester.setValidating(false);

			digester.addObjectCreate("Ciudades", Ciudades.class);
			digester.addRuleSet(new CiudadRuleSet());
			digester.addSetNext("*/Ciudad", "addCiudad");

			return (Ciudades) digester.parse(new StringReader(xml));
		} catch (Exception e) {
			StringBuffer message = new StringBuffer();

			logger.error(message.toString(), e);
		}

		return null;
	}

	protected static final Logger logger = LoggerFactory
			.getLogger(CiudadesMapper.class);

}
