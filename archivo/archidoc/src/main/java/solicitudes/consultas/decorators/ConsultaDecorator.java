package solicitudes.consultas.decorators;

import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.time.FastDateFormat;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import solicitudes.consultas.ConsultasConstants;
import solicitudes.consultas.vos.ConsultaVO;

import common.Messages;
import common.util.DateUtils;

/**
 * Decorador para formatear las columna de manera adecuada en función de la
 * columna que se desea mostrar.
 */
public class ConsultaDecorator implements DisplaytagColumnDecorator {

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

		ServletRequest request = pageContext.getRequest();

		StringBuffer column = new StringBuffer();
		ConsultaVO consulta = (ConsultaVO) columnValue;

		boolean verificada = verificaFechaInicialReserva(consulta);

		if (verificada)
			column.append("<FONT COLOR='#FF0000'>");
		column.append(getConsultaColumn(consulta, request.getLocale()));
		if (verificada)
			column.append("</FONT>");

		return column.toString();
	}

	/**
	 * Obtiene el valor de la columna adecuada de la consulta.
	 * 
	 * @param consulta
	 *            Objeto préstamo del que obtener los datos.
	 * @return Valor de la columna
	 */
	private String getConsultaColumn(ConsultaVO consulta, Locale locale) {
		String result = null;
		switch (consulta.getComparatorType()) {
		case ConsultaVO.COMPARABLE_TYPE_FINICIAL_RESERVA:
			if (consulta.getFinicialreserva() != null)
				result = dateFormat.format(consulta.getFinicialreserva());
			else
				result = "";
			break;
		case ConsultaVO.COMPARABLE_TYPE_FENTREGA:
			if (consulta.getFentrega() != null)
				result = dateFormat.format(consulta.getFentrega());
			else
				result = "";
			break;
		case ConsultaVO.COMPARABLE_TYPE_FMAXFINCONSULTA:
			if (consulta.getFmaxfinconsulta() != null)
				result = dateFormat.format(consulta.getFmaxfinconsulta());
			else
				result = "";
			break;
		case ConsultaVO.COMPARABLE_TYPE_FESTADO:
			if (consulta.getFestado() != null)
				result = dateFormat.format(consulta.getFestado());
			else
				result = "";
			break;
		case ConsultaVO.COMPARABLE_TYPE_ANO:
			result = consulta.getAno();
			break;
		case ConsultaVO.COMPARABLE_TYPE_ESTADO:
			result = this.getEstado(consulta, locale);
			break;
		case ConsultaVO.COMPARABLE_TYPE_IDARCHIVO:
			if (consulta.getIdarchivo() != null)
				result = consulta.getIdarchivo();
			else
				result = "";
			break;
		case ConsultaVO.COMPARABLE_TYPE_IDUSRSOLICITANTE:
			if (consulta.getIdusrsolicitante() != null)
				result = consulta.getIdusrsolicitante();
			else
				result = "";
			break;
		case ConsultaVO.COMPARABLE_TYPE_NOMORGCONSULTOR:
			if (consulta.getNorgconsultor() != null)
				result = consulta.getNorgconsultor();
			else
				result = "";
			break;
		case ConsultaVO.COMPARABLE_TYPE_NOMUSRCONSULTOR:
			if (consulta.getNusrconsultor() != null)
				result = consulta.getNusrconsultor();
			else
				result = "";
			break;
		case ConsultaVO.COMPARABLE_TYPE_ORDEN:
			result = "" + consulta.getOrden();
			break;
		}

		return result;
	}

	/**
	 * Comprueba si la fecha inicial de reserva de una consulta es menor que la
	 * actual.
	 * 
	 * @param consulta
	 *            Consulta del que se desea comprobar la fecha de reserva.
	 * @return Verdadero si la fecha de reserva es menor que la actual o false
	 *         en caso contrario
	 */
	private boolean verificaFechaInicialReserva(ConsultaVO consulta) {
		boolean result = false;

		if (consulta.getEstado() == ConsultasConstants.ESTADO_CONSULTA_RESERVADA) {
			if (consulta.getFinicialreserva() != null) {
				// if ( (consulta.getFinicialreserva().getTime() - (new
				// Date().getTime())) >= 0 )
				// //if
				// (consulta.getFinicialreserva().compareTo(DateUtils.getFechaActual()
				// ) == 0)
				// result = true;

				if (DateUtils.esHoy(consulta.getFinicialreserva()))
					result = true;
			}
		}
		return result;
	}

	/**
	 * Genera el texto asociado al estado de la consulta.
	 * 
	 * @return String con el texto del estado de la consulta
	 */
	public String getEstado(ConsultaVO consulta, Locale locale) {
		String estado = null;

		switch (consulta.getEstado()) {
		case ConsultasConstants.ESTADO_CONSULTA_ABIERTA:
			estado = Messages.getString(
					ConsultasConstants.SOLICITUDES_CONSULTA_ESTADO + ".1",
					locale);
			break;// estado = "Abierto"; break;
		case ConsultasConstants.ESTADO_CONSULTA_SOLICITADA:
			estado = Messages.getString(
					ConsultasConstants.SOLICITUDES_CONSULTA_ESTADO + ".2",
					locale);
			break;// estado = "Solicitado"; break;
		case ConsultasConstants.ESTADO_CONSULTA_RESERVADA:
			estado = Messages.getString(
					ConsultasConstants.SOLICITUDES_CONSULTA_ESTADO + ".3",
					locale);
			break;// estado = "Reservado"; break;
		case ConsultasConstants.ESTADO_CONSULTA_AUTORIZADA:
			estado = Messages.getString(
					ConsultasConstants.SOLICITUDES_CONSULTA_ESTADO + ".4",
					locale);
			break;// estado = "Autorizado"; break;
		case ConsultasConstants.ESTADO_CONSULTA_DENEGADA:
			estado = Messages.getString(
					ConsultasConstants.SOLICITUDES_CONSULTA_ESTADO + ".5",
					locale);
			break;// estado = "Denegado"; break;
		case ConsultasConstants.ESTADO_CONSULTA_ENTREGADA:
			estado = Messages.getString(
					ConsultasConstants.SOLICITUDES_CONSULTA_ESTADO + ".6",
					locale);
			break;// estado = "Entregado"; break;
		case ConsultasConstants.ESTADO_CONSULTA_DEVUELTA_INCOMPLETA:
			estado = Messages.getString(
					ConsultasConstants.SOLICITUDES_CONSULTA_ESTADO + ".7",
					locale);
			break;// estado = "Devuelto Incompleto"; break;
		case ConsultasConstants.ESTADO_CONSULTA_DEVUELTA:
			estado = Messages.getString(
					ConsultasConstants.SOLICITUDES_CONSULTA_ESTADO + ".8",
					locale);
			break;// estado = "Devuelto"; break;
		default:
			estado = "-";
			break;
		}

		return estado;
	}
}