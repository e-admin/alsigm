package solicitudes.prestamos.decorators;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;

import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.vos.PrestamoPO;
import solicitudes.prestamos.vos.PrestamoVO;

import common.Constants;
import common.Messages;

/**
 * Decorador de un prestamos. Devuelve el prestamo con el tipo de comparador
 * adecuado para el campo solicitado(fecha entrega,fecha reserva...) o la salida
 * adecuada para ciertas columnas.
 */
public class ViewPrestamoDecorator extends PrestamosBaseDecorator {
	private final static Logger logger = Logger
			.getLogger(ViewPrestamoDecorator.class);

	/**
	 * Devuelve el objeto prestamos {@link PrestamoVO} ordenable por fecha
	 * estado
	 * 
	 * @return Objeto prestamos {@link PrestamoVO}
	 */
	public PrestamoVO getFestado() {
		PrestamoVO prestamo = (solicitudes.prestamos.vos.PrestamoVO) getCurrentRowObject();

		prestamo.setComparatorType(PrestamoVO.COMPARABLE_TYPE_FESTADO);
		return prestamo;
	}

	/**
	 * Devuelve el objeto prestamos {@link PrestamoVO} ordenable por fecha
	 * inicial de reserva
	 * 
	 * @return Objeto prestamos {@link PrestamoVO}
	 */
	public PrestamoVO getFinicialreserva() {
		PrestamoVO prestamo = (solicitudes.prestamos.vos.PrestamoVO) getCurrentRowObject();

		prestamo.setComparatorType(PrestamoVO.COMPARABLE_TYPE_FINICIAL_RESERVA);
		return prestamo;
	}

	/**
	 * Devuelve el objeto prestamos {@link PrestamoVO} ordenable por fecha
	 * maxima para fin de prestamo
	 * 
	 * @return Objeto prestamos {@link PrestamoVO}
	 */
	public PrestamoVO getFmaxfinprestamo() {
		PrestamoVO prestamo = (solicitudes.prestamos.vos.PrestamoVO) getCurrentRowObject();

		prestamo.setComparatorType(PrestamoVO.COMPARABLE_TYPE_FMAXFINPRESTAMO);
		return prestamo;
	}

	/**
	 * Devuelve el objeto prestamos {@link PrestamoVO} ordenable por fecha de
	 * final de reserva
	 * 
	 * @return Objeto prestamos {@link PrestamoVO}
	 */
	public PrestamoVO getFfinalreserva() {
		PrestamoVO prestamo = (solicitudes.prestamos.vos.PrestamoVO) getCurrentRowObject();

		prestamo.setComparatorType(PrestamoVO.COMPARABLE_TYPE_FFINAL_RESERVA);
		return prestamo;
	}

	/**
	 * Devuelve el objeto prestamos {@link PrestamoVO} ordenable por fecha de
	 * reclamacion 1
	 * 
	 * @return Objeto prestamos {@link PrestamoVO}
	 */
	public PrestamoVO getFreclamacion1() {
		PrestamoVO prestamo = (solicitudes.prestamos.vos.PrestamoVO) getCurrentRowObject();

		prestamo.setComparatorType(PrestamoVO.COMPARABLE_TYPE_FRECLAMACION1);
		return prestamo;
	}

	/**
	 * Devuelve el objeto prestamos {@link PrestamoVO} ordenable por fecha de
	 * reclamacion 2
	 * 
	 * @return Objeto prestamos {@link PrestamoVO}
	 */
	public PrestamoVO getFreclamacion2() {
		PrestamoVO prestamo = (solicitudes.prestamos.vos.PrestamoVO) getCurrentRowObject();

		prestamo.setComparatorType(PrestamoVO.COMPARABLE_TYPE_FRECLAMACION2);
		return prestamo;
	}

	/**
	 * Devuelve el objeto prestamos {@link PrestamoVO} ordenable por año
	 * 
	 * @return Objeto prestamos {@link PrestamoVO}
	 */
	public PrestamoVO getAno() {
		PrestamoVO prestamo = (solicitudes.prestamos.vos.PrestamoVO) getCurrentRowObject();

		prestamo.setComparatorType(PrestamoVO.COMPARABLE_TYPE_ANO);
		return prestamo;
	}

	/**
	 * Devuelve el objeto prestamos {@link PrestamoVO} ordenable por estado
	 * 
	 * @return Objeto prestamos {@link PrestamoVO}
	 */
	public PrestamoVO getEstado() {
		PrestamoVO prestamo = (solicitudes.prestamos.vos.PrestamoVO) getCurrentRowObject();

		prestamo.setComparatorType(PrestamoVO.COMPARABLE_TYPE_ESTADO);
		return prestamo;
	}

	/**
	 * Genera la imagen asociad al estado del préstamo.
	 * 
	 * @return String de etiqueta image asociada a las notas del préstamos
	 */
	public String getNotas() {
		String images = "";
		try {
			PrestamoPO prestamo = (solicitudes.prestamos.vos.PrestamoPO) getCurrentRowObject();

			String notas = prestamo.getNotas();
			if (notas != null) {
				PageContext context = getPageContext();
				Locale locale = ((HttpServletRequest) context.getRequest())
						.getLocale();
				if (notas.indexOf(PrestamosConstants.SITUACION_CADUCADO) >= 0) {
					images += generateImage(
							"caducado.gif",
							Messages.getString(
									PrestamosConstants.LABEL_PRESTAMOS_SITUACION_CADUCADO,
									locale));
				}
				if (notas
						.indexOf(PrestamosConstants.SITUACION_RECLAMADO_UNA_VEZ) >= 0) {
					images += generateImage(
							"recl_1.gif",
							Messages.getString(
									PrestamosConstants.LABEL_PRESTAMOS_SITUACION_RECLAMADO_1,
									locale));
				}
				if (notas
						.indexOf(PrestamosConstants.SITUACION_RECLAMADO_DOS_VECES) >= 0) {
					images += generateImage(
							"recl_2.gif",
							Messages.getString(
									PrestamosConstants.LABEL_PRESTAMOS_SITUACION_RECLAMADO_2,
									locale));
				}
				if (notas
						.indexOf(PrestamosConstants.SITUACION_PRORROGA_SOLICITADA) >= 0) {
					images += generateImage(
							"pro_sol.gif",
							Messages.getString(
									PrestamosConstants.LABEL_PRESTAMOS_SITUACION_PRORROGA_SOLICITADA,
									locale));
				} else if (prestamo.isProrrogado()) {
					images += generateImage(
							"pro_acep.gif",
							Messages.getString(
									PrestamosConstants.LABEL_PRESTAMOS_PRORROGA_AUTORIZADA,
									locale));

				} else if (notas
						.indexOf(PrestamosConstants.SITUACION_PRORROGA_DENEGADA) >= 0) {
					images += generateImage(
							"pro_den.gif",
							Messages.getString(
									PrestamosConstants.LABEL_PRESTAMOS_SITUACION_PRORROGA_DENEGADA,
									locale));
				}

				// Comprobar si el estado es reservado, y se ha pasado la fecha
				// de inicio de reserva
				// ---> : mostar icono 'SOLICITAR' indicando que estan en rango
				// de peticion
				if ((prestamo.getEstado() == PrestamosConstants.ESTADO_PRESTAMO_RESERVADO)
						&& (verificaFechaInicialReserva(prestamo))) {
					images += generateImage("clock.gif", Messages.getString(
							Constants.LABEL_SOLICITAR, locale));
				}
			}
		} catch (Exception e) {
			logger.error("Error al obtener el campo notas del prestamo con id"
					+ getIdentificadorCompleto(), e);

			images = "--";
		}

		return images;
	}

	/**
	 * Genera la imagen asociad al estado del préstamo.
	 * 
	 * @return String de etiqueta image asociada a las notas del préstamos
	 */
	public String getNotasExportar() {
		String images = "";
		try {
			PrestamoPO prestamo = (solicitudes.prestamos.vos.PrestamoPO) getCurrentRowObject();

			String notas = prestamo.getNotas();
			if (notas != null) {
				PageContext context = getPageContext();
				Locale locale = ((HttpServletRequest) context.getRequest())
						.getLocale();

				if (notas.indexOf(PrestamosConstants.SITUACION_CADUCADO) >= 0) {
					images += generateTexto(Messages
							.getString(
									PrestamosConstants.LABEL_PRESTAMOS_SITUACION_CADUCADO,
									locale));
				}
				if (notas
						.indexOf(PrestamosConstants.SITUACION_RECLAMADO_UNA_VEZ) >= 0) {
					images += generateTexto(Messages
							.getString(PrestamosConstants.LABEL_PRESTAMOS_SITUACION_RECLAMADO_1));
				}
				if (notas
						.indexOf(PrestamosConstants.SITUACION_RECLAMADO_DOS_VECES) >= 0) {
					images += generateTexto(Messages
							.getString(PrestamosConstants.LABEL_PRESTAMOS_SITUACION_RECLAMADO_2));
				}

				if (notas
						.indexOf(PrestamosConstants.SITUACION_PRORROGA_SOLICITADA) >= 0) {
					images += generateTexto(Messages
							.getString(PrestamosConstants.LABEL_PRESTAMOS_SITUACION_PRORROGA_SOLICITADA));
				} else if (prestamo.isProrrogado()) {
					images += generateTexto(Messages
							.getString(
									PrestamosConstants.LABEL_PRESTAMOS_PRORROGA_AUTORIZADA,
									locale));
				} else if (notas
						.indexOf(PrestamosConstants.SITUACION_PRORROGA_DENEGADA) >= 0) {
					images += generateTexto(Messages
							.getString(PrestamosConstants.LABEL_PRESTAMOS_SITUACION_PRORROGA_DENEGADA));
				}

				// Comprobar si el estado es reservado, y se ha pasado la fecha
				// de inicio de reserva
				// ---> : mostar icono 'SOLICITAR' indicando que estan en rango
				// de peticion
				if ((prestamo.getEstado() == PrestamosConstants.ESTADO_PRESTAMO_RESERVADO)
						&& (verificaFechaInicialReserva(prestamo))) {
					images += generateTexto(Messages
							.getString(Constants.LABEL_SOLICITAR));
				}
			}

		} catch (Exception e) {
			logger.error("Error al obtener el campo notas del prestamo con id"
					+ getIdentificadorCompleto(), e);
			images = "--";
		}

		return images;
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
	private String generateTexto(String alt) {
		return alt + Constants.STRING_SPACE;
	}

	/**
	 * Comprueba si la fecha inicial de reserva de un préstamo es menor que la
	 * actual.
	 * 
	 * @param prestamo
	 *            Prestamo del que se desea comprobar la fecha de reserva.
	 * @return Verdaderi si la fecha de reserva es menor que la actual o false
	 *         en caso contrario
	 */
	private boolean verificaFechaInicialReserva(PrestamoVO prestamo) {
		boolean result = false;

		if (prestamo.getEstado() == PrestamosConstants.ESTADO_PRESTAMO_RESERVADO) {
			if (prestamo.getFinicialreserva() != null) {
				if ((prestamo.getFinicialreserva().getTime() - (new Date()
						.getTime())) <= 0)
					result = true;
			}
		}

		return result;
	}

	/**
	 * Devuelve el objeto prestamos {@link PrestamoVO} ordenable por fecha de
	 * entrega
	 * 
	 * @return Objeto prestamos {@link PrestamoVO}
	 */
	public PrestamoVO getFentrega() {
		PrestamoVO prestamo = (solicitudes.prestamos.vos.PrestamoVO) getCurrentRowObject();

		prestamo.setComparatorType(PrestamoVO.COMPARABLE_TYPE_FENTREGA);
		return prestamo;
	}

	/**
	 * Devuelve el objeto prestamos {@link PrestamoVO} ordenable por orden
	 * 
	 * @return Objeto prestamos {@link PrestamoVO}
	 */
	public PrestamoVO getOrden() {
		PrestamoVO prestamo = (solicitudes.prestamos.vos.PrestamoVO) getCurrentRowObject();

		prestamo.setComparatorType(PrestamoVO.COMPARABLE_TYPE_ORDEN);
		return prestamo;
	}

	/**
	 * Devuelve el objeto prestamos {@link PrestamoVO} ordenable por nombre de
	 * usuario solitante
	 * 
	 * @return Objeto prestamos {@link PrestamoVO}
	 */
	public PrestamoVO getNusrsolicitantePrestamo() {
		PrestamoVO prestamo = (solicitudes.prestamos.vos.PrestamoVO) getCurrentRowObject();

		prestamo.setComparatorType(PrestamoVO.COMPARABLE_TYPE_NOMUSRSOLICITANTE);
		return prestamo;
	}

	/**
	 * Devuelve el objeto prestamos {@link PrestamoVO} ordenable por nombre de
	 * organizacion solicitante
	 * 
	 * @return Objeto prestamos {@link PrestamoVO}
	 */
	public PrestamoVO getNorgsolicitantePrestamo() {
		PrestamoVO prestamo = (solicitudes.prestamos.vos.PrestamoVO) getCurrentRowObject();

		prestamo.setComparatorType(PrestamoVO.COMPARABLE_TYPE_NOMORGSOLICITANTE);
		return prestamo;
	}
}
