package transferencias.actions;

import org.apache.commons.collections.Transformer;

import common.bi.ServiceRepository;
import common.view.POUtils;

/**
 * @author LUISANVE
 * 
 */
public class UnidadInstalacionToPO2 implements Transformer {

	ServiceRepository services = null;

	private UnidadInstalacionToPO2(ServiceRepository services) {
		this.services = services;
	}

	public Object transform(Object vo) {
		UnidadInstalacionPO2 po = new UnidadInstalacionPO2(services);
		POUtils.copyVOProperties(po, vo);
		return po;
	}

	public static UnidadInstalacionToPO2 getInstance(ServiceRepository services) {
		return new UnidadInstalacionToPO2(services);
	}
}