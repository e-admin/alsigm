package es.ieci.tecdoc.isicres.api.business.manager.impl.builder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ieci.tecdoc.common.isicres.AxDoch;

import es.ieci.tecdoc.isicres.api.business.vo.DocumentoRegistroVO;

public class ListDocumentoRegistroVOBuilder {

	/**
	 * Metodo que adapta un listado de {@link AxDoch} a un listado de {@link DocumentoRegistroVO}
	 * @param docs listado de {@link AxDoch}
	 * @return listado de {@link DocumentoRegistroVO}
	 */
	public List build(List docs, String entidad){
		List result = new ArrayList();
		
		DocumentoRegistroVO documentoRegistro = null;
		for (Iterator it = docs.iterator();it.hasNext();){
			AxDoch axDoch = (AxDoch) it.next();
			documentoRegistro =  new DocumentoRegistroVOBuilder().build(axDoch, entidad);
			result.add(documentoRegistro);
		}
		
		return result;
	}
}
