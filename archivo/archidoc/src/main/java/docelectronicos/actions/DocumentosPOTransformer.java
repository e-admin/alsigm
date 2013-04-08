package docelectronicos.actions;

import org.apache.commons.collections.Transformer;

import se.usuarios.ServiceClient;

import common.bi.ServiceRepository;

import docelectronicos.vos.DocDocumentoVO;
import docelectronicos.vos.DocTCapturaVO;

public class DocumentosPOTransformer implements Transformer {
	ServiceRepository rep = null;
	ServiceClient client = null;
	DocTCapturaVO tarea = null;

	public DocumentosPOTransformer(ServiceRepository rep, ServiceClient client,
			DocTCapturaVO tarea) {
		this.rep = rep;
		this.client = client;
		this.tarea = tarea;
	}

	public Object transform(Object arg0) {
		return new DocDocumentoPO(rep, client, (DocDocumentoVO) arg0, tarea);
	}

}
