package es.ieci.tecdoc.sicres.terceros.web.view.decorator;

import org.apache.commons.lang.StringUtils;
import org.displaytag.decorator.TableDecorator;

import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionFisicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionTelematicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;

/**
 *
 * @author IECISA
 *
 */
public class TercerosTableDecorator extends TableDecorator {

	public String getDireccionFisicaPrincipal() {
		TerceroValidadoVO tercero = (TerceroValidadoVO) getCurrentRowObject();
		DireccionFisicaVO direccionFisicaPrincipal = tercero
				.getDireccionFisicaPrincipal();

		return null != direccionFisicaPrincipal ? direccionFisicaPrincipal
				.toString() : StringUtils.EMPTY;
	}

	public String getDireccionTelematicaPrincipal() {
		TerceroValidadoVO tercero = (TerceroValidadoVO) getCurrentRowObject();

		DireccionTelematicaVO direccionTelematicaPrincipal = tercero
				.getDireccionTelematicaPrincipal();

		return null != direccionTelematicaPrincipal ? direccionTelematicaPrincipal
				.toString() : StringUtils.EMPTY;
	}
}
