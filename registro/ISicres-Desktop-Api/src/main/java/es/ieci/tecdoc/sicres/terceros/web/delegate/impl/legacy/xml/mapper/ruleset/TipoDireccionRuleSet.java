package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.ruleset;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSet;

import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.TipoDireccion;

/**
 *
 * @author IECISA
 *
 */
public class TipoDireccionRuleSet implements RuleSet {

	public String getNamespaceURI() {
		return null;
	}

	public void addRuleInstances(Digester digester) {
		digester.addObjectCreate("*/TipoDireccion", TipoDireccion.class);
		digester.addBeanPropertySetter("*/TipoDireccion/IdTel", "id");
		digester.addBeanPropertySetter("*/TipoDireccion/Descripcion",
				"descripcion");
		digester.addBeanPropertySetter("*/TipoDireccion/Codigo", "codigo");
	}

}
