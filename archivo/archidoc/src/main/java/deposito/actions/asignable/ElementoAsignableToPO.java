package deposito.actions.asignable;

import org.apache.commons.collections.Transformer;

import common.bi.ServiceRepository;

import deposito.vos.ElementoAsignableVO;

public class ElementoAsignableToPO implements Transformer {

	ServiceRepository services = null;

	ElementoAsignableToPO(ServiceRepository services) {
		super();
		this.services = services;
	}

	public Object transform(Object obj) {
		ElementoAsignablePO po = new ElementoAsignablePO(
				(ElementoAsignableVO) obj, services);
		return po;
	}

	public static ElementoAsignableToPO getInstance(ServiceRepository services) {
		return new ElementoAsignableToPO(services);
	}
}
