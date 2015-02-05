package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.ruleset;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSet;

import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.TipoDocumento;

/**
 *
 * @author IECISA
 *
 */
public class TipoDocumentoRuleSet implements RuleSet {

	public String getNamespaceURI() {
		return null;
	}

	public void addRuleInstances(Digester digester) {
		digester.addObjectCreate("*/TipoDocumento", TipoDocumento.class);
		digester.addBeanPropertySetter("*/TipoDocumento/Id", "id");
		digester.addBeanPropertySetter("*/TipoDocumento/Descripcion",
				"descripcion");
		digester.addBeanPropertySetter("*/TipoDocumento/TipoPersona",
				"tipoPersona");
		digester.addBeanPropertySetter("*/TipoDocumento/Codigo", "codigo");
	}

}
