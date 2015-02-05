package es.ieci.tecdoc.sicres.terceros.web.view.decorator;

import org.apache.commons.lang.StringUtils;
import org.displaytag.decorator.TableDecorator;

import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.RepresentanteInteresadoVO;

/**
 *
 * @author IECISA
 *
 */
public class InteresadosTableDecorator extends TableDecorator {

	/**
	 *
	 * @return
	 */
	public String getInteresado() {
		InteresadoVO interesado = (InteresadoVO) getCurrentRowObject();

		return interesado.getTercero().getDescripcion();
	}

	/**
	 *
	 * @return
	 */
	public String getDireccionNotificacionInteresado() {
		InteresadoVO interesado = (InteresadoVO) getCurrentRowObject();
		BaseDireccionVO direccionNotificacion = interesado
				.getDireccionNotificacion();

		return null != direccionNotificacion ? direccionNotificacion.toString()
				: StringUtils.EMPTY;
	}

	/**
	 *
	 * @return
	 */
	public String getRepresentante() {
		InteresadoVO interesado = (InteresadoVO) getCurrentRowObject();
		RepresentanteInteresadoVO representante = interesado.getRepresentante();
		if (null != representante && null != representante.getRepresentante()) {
			return representante.getRepresentante().getDescripcion();
		} else
			return StringUtils.EMPTY;
	}

	/**
	 *
	 * @return
	 */
	public String getDireccionNotificacionRepresentante() {
		InteresadoVO interesado = (InteresadoVO) getCurrentRowObject();

		RepresentanteInteresadoVO representante = interesado.getRepresentante();

		if (null != representante) {

			BaseDireccionVO direccionNotificacion = representante
					.getDireccionNotificacion();

			return (null != direccionNotificacion) ? direccionNotificacion
					.toString() : StringUtils.EMPTY;
		} else {
			return StringUtils.EMPTY;
		}
	}

}
