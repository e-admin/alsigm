package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.ruleset;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSet;

import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Domicilio;

/**
 *
 * @author IECISA
 *
 */
public class DomicilioRuleSet implements RuleSet {

	public String getNamespaceURI() {
		return null;
	}

	public void addRuleInstances(Digester digester) {
		digester.addObjectCreate("*/Domicilio", Domicilio.class);
		digester.addBeanPropertySetter("*/Domicilio/Id", "id");
		digester.addBeanPropertySetter("*/Domicilio/Direccion", "direccion");
		digester.addBeanPropertySetter("*/Domicilio/Poblacion", "poblacion");
		digester.addBeanPropertySetter("*/Domicilio/CodPostal", "codigoPostal");
		digester.addBeanPropertySetter("*/Domicilio/Provincia", "provincia");
		digester.addBeanPropertySetter("*/Domicilio/Preferencia", "preferencia");
	}

}
