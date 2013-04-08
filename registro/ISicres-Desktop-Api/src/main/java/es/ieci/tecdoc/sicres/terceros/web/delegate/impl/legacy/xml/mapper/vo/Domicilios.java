package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo;

import java.util.ArrayList;

/**
 *
 * @author IECISA
 *
 */
public class Domicilios {

	public java.util.List<Domicilio> getDomicilios() {
		return domicilios;
	}

	public void setDomicilios(java.util.List<Domicilio> domicilios) {
		this.domicilios = domicilios;
	}

	public void addDomicilio(Domicilio domicilio) {
		if (null == getDomicilios()) {
			this.setDomicilios(new ArrayList<Domicilio>());
		}

		this.getDomicilios().add(domicilio);
	}

	protected java.util.List<Domicilio> domicilios;

}
