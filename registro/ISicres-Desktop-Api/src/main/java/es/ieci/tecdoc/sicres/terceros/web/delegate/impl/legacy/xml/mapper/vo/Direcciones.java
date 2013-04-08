package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author IECISA
 *
 */
public class Direcciones {

	public List<Direccion> getDirecciones() {
		return direcciones;
	}

	public void setDirecciones(List<Direccion> direcciones) {
		this.direcciones = direcciones;
	}

	public void addDireccion(Direccion direccion) {
		if (null == getDirecciones()) {
			setDirecciones(new ArrayList<Direccion>());
		}
	}

	protected List<Direccion> direcciones;
}
