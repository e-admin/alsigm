package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.ruleset;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSet;

import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.EDireccion;

/**
 *
 * @author IECISA
 *
 */
public class EDireccionRuleSet implements RuleSet {

	public String getNamespaceURI() {
		return null;
	}

	public void addRuleInstances(Digester digester) {
		digester.addObjectCreate("*/Telematica", EDireccion.class);
		digester.addSetProperties("*/Telematica", "eliminarTel", "eliminar");
		digester.addBeanPropertySetter("*/Telematica/IdTel", "id");
		digester.addBeanPropertySetter("*/Telematica/DireccionTel", "direccion");
		digester.addBeanPropertySetter("*/Telematica/TipoTel", "tipo");
		digester.addBeanPropertySetter("*/Telematica/PreferenciaTel",
				"preferencia");
	}

}
