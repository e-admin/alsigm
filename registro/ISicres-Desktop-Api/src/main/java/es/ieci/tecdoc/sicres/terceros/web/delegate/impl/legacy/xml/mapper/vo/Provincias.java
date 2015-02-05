package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author IECISA
 *
 */
public class Provincias {

	public List<Provincia> getProvincias() {
		return provincias;
	}

	public void setProvincias(List<Provincia> provincias) {
		this.provincias = provincias;
	}

	public void addProvincia(Provincia provincia) {
		if (null == getProvincias()) {
			setProvincias(new ArrayList<Provincia>());
		}

		getProvincias().add(provincia);
	}

	protected List<Provincia> provincias;
}
