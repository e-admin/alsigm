package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.factory;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDireccionTelematicaVO;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.TipoDireccion;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.TipoDirecciones;

/**
 *
 * @author IECISA
 *
 */
public class TipoDireccionFactory {

	/**
	 *
	 * @param tipoDirecciones
	 * @return
	 */
	public List<TipoDireccionTelematicaVO> createTiposDireccion(
			TipoDirecciones tipoDirecciones) {

		List<TipoDireccionTelematicaVO> tipos = new ArrayList<TipoDireccionTelematicaVO>();

		for (TipoDireccion tipoDireccion : tipoDirecciones.getTipoDirecciones()) {
			TipoDireccionTelematicaVO tipo = new TipoDireccionTelematicaVO();
			if (!StringUtils.isEmpty(tipoDireccion.getId())) {
				tipo.setId(tipoDireccion.getId());
			}
			if (!StringUtils.isEmpty(tipoDireccion.getCodigo())) {
				tipo.setCodigo(tipoDireccion.getCodigo());
			}
			if (!StringUtils.isEmpty(tipoDireccion.getDescripcion())) {
				tipo.setDescripcion(tipoDireccion.getDescripcion());
			}

			tipos.add(tipo);
		}

		return tipos;
	}
}
