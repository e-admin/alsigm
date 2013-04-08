package solicitudes.prestamos.decorators;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import solicitudes.db.DetalleDBEntity;
import solicitudes.vos.BusquedaDetalleVO;

/**
 * Decorador de detalles en los listado de devolucion. Devuelve la salida
 * adecuada para ciertas columnas.
 */
public class ViewDetalleBusquedaDecorator extends PrestamosBaseDecorator {
	/**
	 * Genera la imagen asociad al tipo al que va asociado una unidad
	 * documental, es decir 1-prestamo o 2-consulta.
	 * 
	 * @return String de etiqueta image asociada
	 */
	public String getTipo() {
		BusquedaDetalleVO bdp = (BusquedaDetalleVO) getCurrentRowObject();
		String image = null;

		if (bdp.getTiposolicitud() == Integer
				.parseInt(DetalleDBEntity.TIPO_DETALLE_PRESTAMO))
			image = generateImage(bdp, "prestamo.gif", "Préstamo");
		else
			image = generateImage(bdp, "consulta.gif", "Consulta");

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
	private String generateImage(BusquedaDetalleVO bdp, String image, String alt) {
		StringBuffer document = new StringBuffer();
		PageContext context = getPageContext();

		// Generamos el enlace a la imagen en funcion del tipo
		document.append("<a href=\"").append("javascript:verSolicitud('")
				.append(bdp.getIdSolicitud()).append("','")
				.append(bdp.getTiposolicitud()).append("')").append("\">");

		// Generamos la imagen
		document.append("<image src=\"")
				.append(((HttpServletRequest) context.getRequest())
						.getContextPath())
				.append("/pages/images/")
				.append(image)
				.append("\" border=\"0\" alt=\"" + alt + "\" title=\"" + alt
						+ "\" class=\"imgTextTop\"/>");

		document.append("</a>");

		return document.toString();
	}
}
