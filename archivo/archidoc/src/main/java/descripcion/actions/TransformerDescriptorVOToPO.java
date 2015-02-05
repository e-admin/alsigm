package descripcion.actions;

import org.apache.commons.collections.Transformer;

import common.bi.ServiceRepository;

import descripcion.vos.DescriptorVO;

public class TransformerDescriptorVOToPO implements Transformer {

	public Object transform(Object vo) {
		return new DescriptorPO((DescriptorVO) vo, services);
	}

	ServiceRepository services = null;

	TransformerDescriptorVOToPO(ServiceRepository services) {
		this.services = services;
	}

	public static TransformerDescriptorVOToPO getInstance(
			ServiceRepository services) {
		return new TransformerDescriptorVOToPO(services);
	}

}
