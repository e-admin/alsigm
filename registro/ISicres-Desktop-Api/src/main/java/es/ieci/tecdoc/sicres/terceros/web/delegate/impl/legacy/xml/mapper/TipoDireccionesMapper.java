package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper;

import java.io.StringReader;

import org.apache.commons.digester.Digester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.ruleset.TipoDireccionRuleSet;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.TipoDirecciones;

/**
 *
 * @author IECISA
 *
 */
public class TipoDireccionesMapper implements Unmarshaller {

	public TipoDirecciones unmarshall(String xml) {
		try {
			Digester digester = new Digester();
			digester.setValidating(false);

			digester.addObjectCreate("TipoDirecciones", TipoDirecciones.class);
			digester.addRuleSet(new TipoDireccionRuleSet());
			digester.addSetNext("*/TipoDireccion", "addTipoDireccion");

			return (TipoDirecciones) digester.parse(new StringReader(xml));
		} catch (Exception e) {
			StringBuffer message = new StringBuffer();

			logger.error(message.toString(), e);
		}
		return null;
	}

	protected static final Logger logger = LoggerFactory
			.getLogger(TipoDireccionesMapper.class);
}
