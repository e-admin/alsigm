package deposito.actions.hueco;

import org.apache.commons.collections.Transformer;

import common.bi.ServiceRepository;

import deposito.vos.HuecoVO;

public class HuecoToPO implements Transformer {

	ServiceRepository services = null;

	HuecoToPO(ServiceRepository services) {
		super();
		this.services = services;
	}

	public Object transform(Object obj) {
		HuecoPO po = new HuecoPO((HuecoVO) obj, services);

		return po;
	}

	public static HuecoToPO getInstance(ServiceRepository services) {
		return new HuecoToPO(services);
	}

}
