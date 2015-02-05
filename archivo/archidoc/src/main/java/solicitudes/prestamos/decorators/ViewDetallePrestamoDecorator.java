package solicitudes.prestamos.decorators;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.vos.DetallePrestamoVO;

import common.Constants;
import common.Messages;

/**
 * Decorador de detalles de un prestamos. Devuelve la salida adecuada para
 * ciertas columnas.
 */
public class ViewDetallePrestamoDecorator extends PrestamosBaseDecorator {

	/**
	 * Obtiene el estado en el que se encuentra el detalle a partir del
	 * identificador del estado
	 * 
	 * @return Estado del detalle
	 */
	public String getEstado() {
		String estado = null;
		DetallePrestamoVO detallePrestamo = (DetallePrestamoVO) getCurrentRowObject();
		ServletRequest request = getPageContext().getRequest();

		switch (detallePrestamo.getEstado()) {
		case PrestamosConstants.ESTADO_DETALLE_PENDIENTE:
			estado = Messages.getString(
					Constants.ETIQUETA_ESTADO_DETALLE_PRESTAMOS + "1",
					request.getLocale());
			break;// "Pendiente"; break;
		case PrestamosConstants.ESTADO_DETALLE_RESERVADA:
			estado = Messages.getString(
					Constants.ETIQUETA_ESTADO_DETALLE_PRESTAMOS + "2",
					request.getLocale());
			break;// estado = "Reservada"; break;
		case PrestamosConstants.ESTADO_DETALLE_AUTORIZADA:
			estado = Messages.getString(
					Constants.ETIQUETA_ESTADO_DETALLE_PRESTAMOS + "3",
					request.getLocale());
			break;// estado = "Autorizada"; break;
		case PrestamosConstants.ESTADO_DETALLE_DENEGADA:
			estado = Messages.getString(
					Constants.ETIQUETA_ESTADO_DETALLE_PRESTAMOS + "4",
					request.getLocale());
			break;// estado = "Denegada"; break;
		case PrestamosConstants.ESTADO_DETALLE_ENTREGADA:
			estado = Messages.getString(
					Constants.ETIQUETA_ESTADO_DETALLE_PRESTAMOS + "5",
					request.getLocale());
			break;// estado = "Entregada"; break;
		case PrestamosConstants.ESTADO_DETALLE_DEVUELTA:
			estado = Messages.getString(
					Constants.ETIQUETA_ESTADO_DETALLE_PRESTAMOS + "6",
					request.getLocale());
			break;// estado = "Devuelta"; break;
		default:
			break;
		}

		return estado;
	}

	/**
	 * Genera la imagen asociad a la disponibilidad del detalle del préstamo.
	 * 
	 * @return String de etiqueta image asociada
	 */
	public String getDisponibilidad() {
		DetallePrestamoVO detallePrestamo = (DetallePrestamoVO) getCurrentRowObject();
		String image = null;

		if (detallePrestamo.isDisponibilidad())
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

	/**
	 * Devuelve el objeto detalle prestamo {@link DetallePrestamoVO} ante la
	 * solicitud del campo información.
	 * 
	 * @return Objeto DetallePrestamoVO {@link DetallePrestamoVO}
	 */
	public DetallePrestamoVO getInformacion() {
		DetallePrestamoVO detallePrestamo = (DetallePrestamoVO) getCurrentRowObject();

		return detallePrestamo;
	}
}
