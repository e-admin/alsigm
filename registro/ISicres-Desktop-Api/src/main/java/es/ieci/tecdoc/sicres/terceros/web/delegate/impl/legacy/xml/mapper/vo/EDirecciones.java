package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo;

import java.util.ArrayList;

public class EDirecciones {

	public java.util.List<EDireccion> getEdirecciones() {
		return edirecciones;
	}

	public void setEdirecciones(java.util.List<EDireccion> edirecciones) {
		this.edirecciones = edirecciones;
	}

	public void addEDireccion(EDireccion edireccion) {
		if (null == this.getEdirecciones()) {
			this.setEdirecciones(new ArrayList<EDireccion>());
		}

		this.getEdirecciones().add(edireccion);
	}

	protected java.util.List<EDireccion> edirecciones;
}
