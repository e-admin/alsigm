package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.ruleset;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSet;

import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Ciudad;

/**
 *
 * @author IECISA
 *
 */
public class CiudadRuleSet implements RuleSet {

	public String getNamespaceURI() {
		return null;
	}

	public void addRuleInstances(Digester digester) {
		digester.addObjectCreate("*/Ciudad", Ciudad.class);
		digester.addBeanPropertySetter("*/Ciudad/Id", "id");
		digester.addBeanPropertySetter("*/Ciudad/Codigo", "codigo");
		digester.addBeanPropertySetter("*/Ciudad/Nombre", "nombre");
		digester.addBeanPropertySetter("*/Ciudad/IdProvincia", "idProvincia");
	}

}
