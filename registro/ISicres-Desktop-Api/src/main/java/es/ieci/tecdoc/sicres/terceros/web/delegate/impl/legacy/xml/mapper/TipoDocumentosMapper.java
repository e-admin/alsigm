package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper;

import java.io.StringReader;

import org.apache.commons.digester.Digester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.ruleset.TipoDocumentoRuleSet;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.TipoDocumentos;

/**
 *
 * @author IECISA
 *
 */
public class TipoDocumentosMapper implements Unmarshaller {

	public TipoDocumentos unmarshall(String xml) {
		try {
			Digester digester = new Digester();
			digester.setValidating(false);

			digester.addObjectCreate("TipoDocumentos", TipoDocumentos.class);
			digester.addRuleSet(new TipoDocumentoRuleSet());
			digester.addSetNext("*/TipoDocumento", "addTipoDocumento");

			return (TipoDocumentos) digester.parse(new StringReader(xml));
		} catch (Exception e) {
			StringBuffer message = new StringBuffer();

			logger.error(message.toString(), e);
		}
		return null;
	}

	protected static final Logger logger = LoggerFactory
			.getLogger(TipoDocumentosMapper.class);

}
