package solicitudes.consultas.decorators;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import solicitudes.consultas.vos.DetalleConsultaVO;

import common.Constants;

/**
 * Decorador de detalles de consulta. Devuelve el detalle para la columna
 * informacion.
 */
public class ViewDetalleConsultaDecorator extends ConsultasBaseDecorator {
	/**
	 * Devuelve el objeto detalle consulta {@link DetalleConsultaVO} ante la
	 * solicitud del campo información.
	 * 
	 * @return Objeto DetalleConsultaVO {@link DetalleConsultaVO}
	 */
	public DetalleConsultaVO getInformacion() {
		DetalleConsultaVO detalleConsulta = (DetalleConsultaVO) getCurrentRowObject();

		return detalleConsulta;
	}

	/**
	 * Devuelve el objeto detalle consulta {@link DetalleConsultaVO} ante la
	 * solicitud del campo información.
	 * 
	 * @return Objeto DetalleConsultaVO {@link DetalleConsultaVO}
	 */
	public String getObservaciones() {
		String observaciones = Constants.STRING_EMPTY;

		DetalleConsultaVO detalleConsulta = (DetalleConsultaVO) getCurrentRowObject();

		if (detalleConsulta != null)
			observaciones = detalleConsulta.getObservaciones();

		return observaciones;
	}

	/**
	 * Genera la imagen asociad a la disponibilidad del detalle de la consulta.
	 * 
	 * @return String de etiqueta image asociada
	 */
	public String getDisponibilidad() {
		DetalleConsultaVO detalleConsulta = (DetalleConsultaVO) getCurrentRowObject();
		String image = null;

		if (detalleConsulta.isDisponibilidad())
			image = generateImage("right.gif", "Disponible");
		else
			image = generateImage("wrong.gif", "No Disponible");

		return image;
	}

	/**
	 * Genera el código HTML para la imagen pasada (que debe estar ubicada en el
	 * directorio /pages/images) y con el texto alternativo pasado
	 * 
	 * @param image
	 *            Nombre y optativamente directorio donde se encuentra la imagen
	 *            en el directorio /pages/images para generar la imagen
	 * @param alt
	 *            Texto alternativo para la imagen
	 * @return Etiqueta generada.
	 */
	private String generateImage(String image, String alt) {
		StringBuffer document = new StringBuffer();
		PageContext context = getPageContext();

		document.append("<image src=\"");
		document.append(((HttpServletRequest) context.getRequest())
				.getContextPath());
		document.append("/pages/images/");
		document.append(image);
		document.append("\" border=\"0\" alt=\"" + alt + "\" title=\"" + alt
				+ "\" class=\"imgTextTop\"/>");

		return document.toString();
	}
}
