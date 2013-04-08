package es.ieci.tecdoc.isicres.api.business.manager.impl.builder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ieci.tecdoc.common.isicres.AxDoch;
import com.ieci.tecdoc.common.isicres.AxPageh;

import es.ieci.tecdoc.isicres.api.business.vo.PaginaDocumentoRegistroVO;

public class ListPaginaDocumentoRegistroVOBuilder {

	/**
	 * Metodo que obtiene un listado de {@link PaginaDocumentoRegistroVO} a partir de {@link AxDoch} y la entidad
	 * @param axDoch
	 * @param entidad
	 * @return listado de objetos {@link PaginaDocumentoRegistroVO}
	 */
	//TODO falta por saber donde colocar este metodo
	public List build(AxDoch axDoch, String entidad){
		List result = new ArrayList();
		PaginaDocumentoRegistroVO  paginaDocumentoRegistroVO = null;
		for(Iterator it = axDoch.getPages().iterator(); it.hasNext();){
			AxPageh axPageh = (AxPageh) it.next();
			//adaptamos la informacion a un objeto paginaDocumentoRegistroVO
			paginaDocumentoRegistroVO = new PaginaDocumentoRegistroVOBuilder()
					.build(axDoch, axPageh, entidad);
			result.add(paginaDocumentoRegistroVO);
		}
		return result;
	}
}
