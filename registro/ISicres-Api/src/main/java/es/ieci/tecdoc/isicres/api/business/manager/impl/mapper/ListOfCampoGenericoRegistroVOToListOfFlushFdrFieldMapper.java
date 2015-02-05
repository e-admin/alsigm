package es.ieci.tecdoc.isicres.api.business.manager.impl.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ieci.tecdoc.idoc.flushfdr.FlushFdrField;

import es.ieci.tecdoc.isicres.api.business.vo.CampoGenericoRegistroVO;

/**
 * Clase que se encarga de transformar una <code>java.util.List</code> de
 * <code>CampoGenericoRegistroVO</code> en una <code>java.util.List</code> de
 * <code>FlushFdrField</code>.
 * 
 * @see CampoGenericoRegistroVO
 * @see FlushFdrField
 * 
 * @author IECISA
 * 
 */
public class ListOfCampoGenericoRegistroVOToListOfFlushFdrFieldMapper {

	public List map(List camposGenericos) {
		List result = new ArrayList();

		Iterator iterator = camposGenericos.iterator();
		while (iterator.hasNext()) {
			CampoGenericoRegistroVO cgr = (CampoGenericoRegistroVO) iterator
					.next();

			FlushFdrField field = new FlushFdrField();
			field.setFldid(Integer.valueOf(cgr.getName()).intValue());
			field.setValue(cgr.getValue());

			result.add(field);
		}

		return result;
	}

}
