package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.factory;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import es.ieci.tecdoc.isicres.terceros.business.vo.ProvinciaVO;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Provincia;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Provincias;

/**
 *
 * @author IECISA
 *
 */
public class ProvinciaFactory {

	/**
	 *
	 * @param provincias
	 * @return
	 */
	public List<ProvinciaVO> createProvincias(Provincias provincias) {
		List<ProvinciaVO> result = new ArrayList<ProvinciaVO>();

		for (Provincia provincia : provincias.getProvincias()) {
			ProvinciaVO provinciaVO = new ProvinciaVO();

			if (!StringUtils.isEmpty(provincia.getId())) {
				provinciaVO.setId(provincia.getId());
			}
			if (!StringUtils.isEmpty(provincia.getCodigo())) {
				provinciaVO.setCodigo(provincia.getCodigo());
			}
			if (!StringUtils.isEmpty(provincia.getNombre())) {
				provinciaVO.setNombre(provincia.getNombre());
			}

			result.add(provinciaVO);
		}

		return result;
	}
}
