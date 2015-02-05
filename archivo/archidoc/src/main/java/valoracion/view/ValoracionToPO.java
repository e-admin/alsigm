package valoracion.view;

import org.apache.commons.collections.Transformer;

import common.bi.ServiceRepository;
import common.view.POUtils;

/**
 * Crea objetos para presentacion de valoraciones de serie a partir de value
 * objects con la informacion básica de dichas valoraciones.
 */
public class ValoracionToPO implements Transformer {

	ServiceRepository services = null;

	public ValoracionToPO(ServiceRepository services) {
		this.services = services;
	}

	public Object transform(Object vo) {
		ValoracionSeriePO po = new ValoracionSeriePO(services);
		POUtils.copyVOProperties(po, vo);
		return po;
	}

	public static ValoracionToPO getInstance(ServiceRepository services) {
		return new ValoracionToPO(services);
	}
}
