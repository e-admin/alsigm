package es.ieci.tecdoc.isicres.api.business.manager.impl.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ieci.tecdoc.common.isicres.AxDoch;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrDocument;

import es.ieci.tecdoc.isicres.api.business.manager.impl.builder.DocumentoRegistroVOBuilder;
import es.ieci.tecdoc.isicres.api.business.vo.DocumentoRegistroVO;

/**
 * Clase que se encarga de transformar una <code>java.util.List</code> de
 * <code>FlushFdrDocument</code> en una <code>java.util.List</code> de
 * <code>DocumentoRegistroVO</code>.
 * 
 * @see FlushFdrDocument
 * @see DocumentoRegistroVO
 * 
 * @author IECISA
 * 
 */
public class ListOfAxDochToListOfDocumentoRegistroVOMapper {

	public ListOfAxDochToListOfDocumentoRegistroVOMapper(String entidad) {
		this.entidad = entidad;
	}

	public List map(List docs) {
		List result = new ArrayList();

		for (Iterator iterator = docs.iterator(); iterator.hasNext();) {
			AxDoch axDoch = (AxDoch) iterator.next();

			DocumentoRegistroVO documento = new DocumentoRegistroVOBuilder()
					.build(axDoch, getEntidad());

			result.add(documento);
		}

		return result;
	}

	protected String getEntidad() {
		return entidad;
	}

	// Members
	protected String entidad;

}
