package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author IECISA
 *
 */
public class TipoDirecciones {

	public List<TipoDireccion> getTipoDirecciones() {
		return tipoDirecciones;
	}

	public void setTipoDirecciones(List<TipoDireccion> tipoDirecciones) {
		this.tipoDirecciones = tipoDirecciones;
	}

	public void addTipoDireccion(TipoDireccion tipoDireccion) {
		if (null == getTipoDirecciones()) {
			setTipoDirecciones(new ArrayList<TipoDireccion>());
		}

		getTipoDirecciones().add(tipoDireccion);
	}

	protected List<TipoDireccion> tipoDirecciones;

}
