package docelectronicos.actions;

import org.apache.commons.collections.Transformer;

import se.usuarios.ServiceClient;

import common.bi.ServiceRepository;

import docelectronicos.vos.DocClasificadorVO;
import docelectronicos.vos.DocTCapturaVO;

public class ClasificadoresPOTranformer implements Transformer {

	ServiceRepository rep = null;
	ServiceClient client = null;
	DocTCapturaVO tarea = null;

	public ClasificadoresPOTranformer(ServiceRepository rep,
			ServiceClient client, DocTCapturaVO tarea) {
		this.rep = rep;
		this.client = client;
		this.tarea = tarea;
	}

	public Object transform(Object arg0) {
		return new DocClasificadorPO(rep, client, (DocClasificadorVO) arg0,
				tarea);
	}

}
