package es.ieci.tecdoc.isicres.api.business.manager.impl.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ieci.tecdoc.common.utils.ScrRegisterInter;

import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;

/**
 * Clase que se encarga de mapear un listado de objetos de tipo
 * <code>ScrRegisterInter</code> en un listado de objetos de tipo
 * <code>InteresadoVO</code>.
 *
 * @author IECISA
 *
 */
public class ListOfScrRegisterInterToListOfInteresadoVO {

	public List map(List listScrRegisterInter) {

		List result = new ArrayList();
		ScrRegisterInter scrRegisterInter = null;
		InteresadoVO interesado = null;

		for (Iterator it = listScrRegisterInter.iterator(); it.hasNext();) {
			scrRegisterInter = (ScrRegisterInter) it.next();
			interesado = (new ScrRegisterInterToInteresadoVOMapper())
					.map(scrRegisterInter);
			result.add(interesado);
		}

		return result;
	}
}
