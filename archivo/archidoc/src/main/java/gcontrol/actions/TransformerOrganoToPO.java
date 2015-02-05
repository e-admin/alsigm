package gcontrol.actions;

import gcontrol.vos.CAOrganoVO;

import org.apache.commons.collections.Transformer;

import common.bi.ServiceRepository;

class TransformerOrganoToPO implements Transformer {
	ServiceRepository services = null;

	TransformerOrganoToPO(ServiceRepository services) {
		this.services = services;
	}

	public Object transform(Object vo) {
		return new OrganoPO((CAOrganoVO) vo, services);
	}

	public static TransformerOrganoToPO getInstance(ServiceRepository services) {
		return new TransformerOrganoToPO(services);
	}
}
