package docelectronicos.actions;

import org.apache.commons.collections.Transformer;

import common.bi.ServiceRepository;

import docelectronicos.vos.DocTCapturaVO;

public class DocTCapturaPOTransformer implements Transformer {

	ServiceRepository rep = null;

	public DocTCapturaPOTransformer(ServiceRepository rep) {
		this.rep = rep;

	}

	public Object transform(Object arg0) {
		return new DocTCapturaPO(rep, (DocTCapturaVO) arg0);
	}

}