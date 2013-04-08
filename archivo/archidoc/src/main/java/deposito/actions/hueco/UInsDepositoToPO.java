package deposito.actions.hueco;

import org.apache.commons.collections.Transformer;

import common.bi.ServiceRepository;
import common.view.POUtils;

public class UInsDepositoToPO implements Transformer {

	ServiceRepository services = null;

	UInsDepositoToPO(ServiceRepository services) {
		super();
		this.services = services;
	}

	public Object transform(Object obj) {
		UInsDepositoPO po = new UInsDepositoPO(services);
		POUtils.copyVOProperties(po, obj);
		return po;
	}

	public static UInsDepositoToPO getInstance(ServiceRepository services) {
		return new UInsDepositoToPO(services);
	}

}
