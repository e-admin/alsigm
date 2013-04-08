package solicitudes.prestamos.decorators;

import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.time.FastDateFormat;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.utils.PrestamosUtils;
import solicitudes.prestamos.vos.PrestamoVO;

import common.util.DateUtils;

/**
 * Decorador para formatear las columna de manera adecuada en función de la
 * columna que se desea mostrar.
 */
public class PrestamoDecorator implements DisplaytagColumnDecorator {

	/**
	 * FastDateFormat usado para formatear el objeto fecha.
	 */
	private FastDateFormat dateFormat = FastDateFormat
			.getInstance("dd/MM/yyyy");

	/**
	 * Transforma el objeto dado en su representación String. Comprueba la fecha
	 * inicial de reserva para en caso positivo colorear de rojo la columna.
	 * 
	 * @param columnValue
	 *            Object Objeto con el valor de la columna
	 * @return String Representacion textual del objeto
	 */
	public Object decorate(Object columnValue, PageContext pageContext,
			MediaTypeEnum arg2) throws DecoratorException {

		StringBuffer column = new StringBuffer();
		PrestamoVO prestamo = (PrestamoVO) columnValue;

		ServletRequest request = pageContext.getRequest();

		boolean verificada = verificaFechaInicialReserva(prestamo);

		if (verificada)
			column.append("<FONT COLOR='#FF0000'>");
		column.append(getPrestamoColumn(request.getLocale(), prestamo));
		if (verificada)
			column.append("</FONT>");

		return column.toString();
	}

	/**
	 * Obtiene el valor de la columna adecuada del prestamo.
	 * 
	 * @param prestamo
	 *            Objeto préstamo del que obtener los datos.
	 * @return Valor de la columna
	 */
	private String getPrestamoColumn(Locale locale, PrestamoVO prestamo) {
		String result = null;

		switch (prestamo.getComparatorType()) {
		case PrestamoVO.COMPARABLE_TYPE_FINICIAL_RESERVA:
			if (prestamo.getFinicialreserva() != null)
				result = dateFormat.format(prestamo.getFinicialreserva());
			else
				result = "";
			break;
		case PrestamoVO.COMPARABLE_TYPE_FFINAL_RESERVA:
			if (prestamo.getFfinalreserva() != null)
				result = dateFormat.format(prestamo.getFfinalreserva());
			else
				result = "";
			break;
		case PrestamoVO.COMPARABLE_TYPE_FENTREGA:
			if (prestamo.getFentrega() != null)
				result = dateFormat.format(prestamo.getFentrega());
			else
				result = "";
			break;
		case PrestamoVO.COMPARABLE_TYPE_FMAXFINPRESTAMO:
			if (prestamo.getFmaxfinprestamo() != null)
				result = dateFormat.format(prestamo.getFmaxfinprestamo());
			else
				result = "";
			break;
		case PrestamoVO.COMPARABLE_TYPE_FESTADO:
			if (prestamo.getFestado() != null)
				result = dateFormat.format(prestamo.getFestado());
			else
				result = "";
			break;
		case PrestamoVO.COMPARABLE_TYPE_FRECLAMACION1:
			if (prestamo.getFreclamacion1() != null)
				result = dateFormat.format(prestamo.getFreclamacion1());
			else
				result = "";
			break;
		case PrestamoVO.COMPARABLE_TYPE_FRECLAMACION2:
			if (prestamo.getFreclamacion2() != null)
				result = dateFormat.format(prestamo.getFreclamacion2());
			else
				result = "";
			break;
		case PrestamoVO.COMPARABLE_TYPE_ANO:
			result = prestamo.getAno();
			break;
		case PrestamoVO.COMPARABLE_TYPE_ESTADO:
			result = this.getEstado(locale, prestamo);
			break;
		case PrestamoVO.COMPARABLE_TYPE_IDARCHIVO:
			if (prestamo.getIdarchivo() != null)
				result = prestamo.getIdarchivo();
			else
				result = "";
			break;
		case PrestamoVO.COMPARABLE_TYPE_IDUSRGESTOR:
			if (prestamo.getIdusrgestor() != null)
				result = prestamo.getIdusrgestor();
			else
				result = "";
			break;
		case PrestamoVO.COMPARABLE_TYPE_IDUSRSOLICITANTE:
			if (prestamo.getIdusrgestor() != null)
				result = prestamo.getIdusrgestor();
			else
				result = "";
			break;
		case PrestamoVO.COMPARABLE_TYPE_NOMORGSOLICITANTE:
			if (prestamo.getNorgsolicitante() != null)
				result = prestamo.getNorgsolicitante();
			else
				result = "";
			break;
		case PrestamoVO.COMPARABLE_TYPE_NOMUSRSOLICITANTE:
			if (prestamo.getNusrsolicitante() != null)
				result = prestamo.getNusrsolicitante();
			else
				result = "";
			break;
		case PrestamoVO.COMPARABLE_TYPE_ORDEN:
			result = "" + prestamo.getOrden();
			break;
		}

		return result;
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
				// if ( (prestamo.getFinicialreserva().getTime() - (new
				// Date().getTime())) <= 0 )
				if (!DateUtils.getFechaSinHora(prestamo.getFinicialreserva())
						.after(DateUtils.getFechaActualSinHora()))
					result = true;
			}
		}

		return result;
	}

	/**
	 * Genera el texto asociado al estado del préstamo.
	 * 
	 * @return String con el texto del estado del prestamo
	 */
	public String getEstado(Locale locale, PrestamoVO prestamo) {

		return PrestamosUtils.getEstado(locale, prestamo.getEstado());

	}
}