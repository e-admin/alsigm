package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author IECISA
 *
 */
public class Ciudades {

	public List<Ciudad> getCiudades() {
		return ciudades;
	}

	public void setCiudades(List<Ciudad> ciudades) {
		this.ciudades = ciudades;
	}

	public void addCiudad(Ciudad ciudad) {
		if (null == getCiudades()) {
			setCiudades(new ArrayList<Ciudad>());
		}

		getCiudades().add(ciudad);
	}

	protected List<Ciudad> ciudades;
}
