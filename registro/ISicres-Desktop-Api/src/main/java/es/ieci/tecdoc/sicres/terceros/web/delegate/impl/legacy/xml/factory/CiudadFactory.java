package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.factory;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import es.ieci.tecdoc.isicres.terceros.business.vo.CiudadVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.ProvinciaVO;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Ciudad;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Ciudades;

/**
 *
 * @author IECISA
 *
 */
public class CiudadFactory {

	/**
	 *
	 * @param ciudades
	 * @return
	 */
	public List<CiudadVO> createCiudades(Ciudades ciudades) {
		List<CiudadVO> result = new ArrayList<CiudadVO>();

		for (Ciudad ciudad : ciudades.getCiudades()) {
			CiudadVO ciudadVO = new CiudadVO();
			if (!StringUtils.isEmpty(ciudad.getId())) {
				ciudadVO.setId(ciudad.getId());
			}
			if (!StringUtils.isEmpty(ciudad.getCodigo())) {
				ciudadVO.setCodigo(ciudad.getCodigo());
			}
			if (!StringUtils.isEmpty(ciudad.getNombre())) {
				ciudadVO.setNombre(ciudad.getNombre());
			}
			if (!StringUtils.isEmpty(ciudad.getIdProvincia())) {
				ProvinciaVO provincia = new ProvinciaVO();
				provincia.setId(ciudad.getIdProvincia());

				ciudadVO.setProvincia(provincia);
			}

			result.add(ciudadVO);
		}

		return result;
	}
}
