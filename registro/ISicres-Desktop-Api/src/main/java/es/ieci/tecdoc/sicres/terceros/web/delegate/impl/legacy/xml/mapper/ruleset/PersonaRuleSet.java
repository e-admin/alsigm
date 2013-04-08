package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.ruleset;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSet;

import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Persona;

/**
 *
 * @author IECISA
 *
 */
public class PersonaRuleSet implements RuleSet {

	public String getNamespaceURI() {
		return null;
	}

	public void addRuleInstances(Digester digester) {
		digester.addObjectCreate("*/Persona", Persona.class);
		digester.addSetProperties("*/Persona");
		digester.addBeanPropertySetter("*/Persona/Id", "id");
		digester.addBeanPropertySetter("*/Persona/Tipo", "tipo");
		digester.addBeanPropertySetter("*/Persona/Nombre", "nombre");
		digester.addBeanPropertySetter("*/Persona/Apellido1", "apellido1");
		digester.addBeanPropertySetter("*/Persona/Apellido2", "apellido2");
		digester.addBeanPropertySetter("*/Persona/TipoDoc", "tipoDoc");
		digester.addBeanPropertySetter("*/Persona/NIF", "nif");
	}

}
