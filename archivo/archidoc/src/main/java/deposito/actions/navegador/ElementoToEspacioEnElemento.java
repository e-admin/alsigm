package deposito.actions.navegador;

import org.apache.commons.collections.Transformer;

import common.bi.ServiceRepository;

import deposito.vos.ElementoVO;

/**
 *
 */
public class ElementoToEspacioEnElemento implements Transformer {

	ServiceRepository services = null;
	String formato = null;
	boolean checkHasHuecos = false;
	int huecosNecesarios = -1;

	ElementoToEspacioEnElemento(ServiceRepository services, String formato,
			boolean checkHasHuecos) {
		this.services = services;
		this.formato = formato;
		this.checkHasHuecos = checkHasHuecos;
	}

	public Object transform(Object obj) {
		return new EspacioEnElemento((ElementoVO) obj, services, formato,
				checkHasHuecos);
	}

	public static ElementoToEspacioEnElemento getInstance(
			ServiceRepository services, String formato, boolean checkHasHuecos) {
		return new ElementoToEspacioEnElemento(services, formato,
				checkHasHuecos);
	}
}