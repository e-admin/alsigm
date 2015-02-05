package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.ruleset;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSet;

import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Provincia;

/**
 *
 * @author IECISA
 *
 */
public class ProvinciaRuleSet implements RuleSet {

	public String getNamespaceURI() {
		return null;
	}

	public void addRuleInstances(Digester digester) {
		digester.addObjectCreate("*/Provincia", Provincia.class);
		digester.addBeanPropertySetter("*/Provincia/Id", "id");
		digester.addBeanPropertySetter("*/Provincia/Codigo", "codigo");
		digester.addBeanPropertySetter("*/Provincia/Nombre", "nombre");
	}

}
