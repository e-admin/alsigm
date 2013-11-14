package es.ieci.tecdoc.fwktd.sir.core.types;

import org.apache.commons.lang.enums.EnumUtils;
import org.apache.commons.lang.enums.ValuedEnum;

/**
 * Enumerados para las constantes de estados de un asiento registral.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class EstadoAsientoRegistralEnum extends ValuedEnum {


	private static final long serialVersionUID = -7551983248599173392L;

/*
	private static final int PENDIENTE_ENVIO_VALUE = 0;
	private static final int ENVIADO_VALUE = 1;
	private static final int DEVUELTO_VALUE = 2;
	private static final int ACEPTADO_VALUE = 3;
	private static final int PENDIENTE_REENVIO_VALUE = 4;
	private static final int REENVIADO_VALUE = 5;
	private static final int ANULADO_VALUE = 6;
	private static final int RECIBIDO_VALUE = 7;
	private static final int RECHAZADO_VALUE = 8;
	private static final int VALIDADO_VALUE = 9;

 */
	private static final int PENDIENTE_ENVIO_VALUE = 0;
	private static final int ENVIADO_VALUE = 1;
	private static final int ENVIADO_Y_ACK_VALUE = 2;
	private static final int ENVIADO_Y_ERROR_VALUE = 3;
	private static final int DEVUELTO_VALUE = 4;
	private static final int ACEPTADO_VALUE = 5;
	private static final int REENVIADO_VALUE = 6;
	private static final int REENVIADO_Y_ACK_VALUE = 7;
	private static final int REENVIADO_Y_ERROR_VALUE = 8;
	private static final int ANULADO_VALUE = 9;
	private static final int RECIBIDO_VALUE = 10;
	private static final int RECHAZADO_VALUE = 11;
	private static final int RECHAZADO_Y_ACK_VALUE = 12;
	private static final int RECHAZADO_Y_ERROR_VALUE = 13;
	private static final int VALIDADO_VALUE = 14;
	private static final int REINTENTAR_VALIDACION_VALUE = 15;


	private static final String PENDIENTE_ENVIO_STRING = "Pendiente de envío";
	private static final String ENVIADO_STRING = "Enviado";
	private static final String ENVIADO_Y_ACK_STRING = "Enviado y ACK";
	private static final String ENVIADO_Y_ERROR_STRING = "Enviado y ERROR";
	private static final String DEVUELTO_STRING = "Devuelto";
	private static final String ACEPTADO_STRING = "Aceptado";
	private static final String REENVIADO_STRING = "Reenviado";
	private static final String REENVIADO_Y_ACK_STRING = "Reenviado y ACK";
	private static final String REENVIADO_Y_ERROR_STRING = "Reenviado y ERROR";
	private static final String ANULADO_STRING = "Anulado";
	private static final String RECIBIDO_STRING = "Recibido";
	private static final String RECHAZADO_STRING = "Rechazado";
	private static final String RECHAZADO_Y_ACK_STRING = "Rechazado y ACK";
	private static final String RECHAZADO_Y_ERROR_STRING = "Rechazado y ERROR";
	private static final String VALIDADO_STRING = "Validado";
	private static final String REINTENTAR_VALIDACION_STRING = "Reintentar validación";


	/**
	 * PENDIENTE DE ENVÍO - El asiento está pendiente de envío.
	 */
	public static final EstadoAsientoRegistralEnum PENDIENTE_ENVIO = new EstadoAsientoRegistralEnum(
			PENDIENTE_ENVIO_STRING, PENDIENTE_ENVIO_VALUE);

	/**
	 * ENVIADO - El asiento está enviado pero a la espera del ACK.
	 */
	public static final EstadoAsientoRegistralEnum ENVIADO = new EstadoAsientoRegistralEnum(
			ENVIADO_STRING, ENVIADO_VALUE);

	/**
	 * ENVIADO_Y_ACK - Asiento enviado y recibido el ACK.
	 */
	public static final EstadoAsientoRegistralEnum ENVIADO_Y_ACK = new EstadoAsientoRegistralEnum(
			ENVIADO_Y_ACK_STRING, ENVIADO_Y_ACK_VALUE);

	/**
	 * ENVIADO_Y_ERROR - Asiento enviado y recibido un mensaje de ERROR.
	 */
	public static final EstadoAsientoRegistralEnum ENVIADO_Y_ERROR = new EstadoAsientoRegistralEnum(
			ENVIADO_Y_ERROR_STRING, ENVIADO_Y_ERROR_VALUE);

	/**
	 * DEVUELTO - El asiento enviado ha sido devuelto por el destino.
	 */
	public static final EstadoAsientoRegistralEnum DEVUELTO = new EstadoAsientoRegistralEnum(
			DEVUELTO_STRING, DEVUELTO_VALUE);

	/**
	 * ACEPTADO - El asiento enviado ha sido aceptado por el destino
	 */
	public static final EstadoAsientoRegistralEnum ACEPTADO = new EstadoAsientoRegistralEnum(
			ACEPTADO_STRING, ACEPTADO_VALUE);

	/**
	 * REENVIADO - El asiento recibido o devuelto ha sido reenviado pero a la espera del ACK.
	 */
	public static final EstadoAsientoRegistralEnum REENVIADO = new EstadoAsientoRegistralEnum(
			REENVIADO_STRING, REENVIADO_VALUE);

	/**
	 * REENVIADO_Y_ACK - El asiento recibido o devuelto ha sido reenviado y recibido el ACK.
	 */
	public static final EstadoAsientoRegistralEnum REENVIADO_Y_ACK = new EstadoAsientoRegistralEnum(
			REENVIADO_Y_ACK_STRING, REENVIADO_Y_ACK_VALUE);

	/**
	 * REENVIADO_Y_ERROR - El asiento recibido o devuelto ha sido reenviado y recibido mensaje de error.
	 */
	public static final EstadoAsientoRegistralEnum REENVIADO_Y_ERROR = new EstadoAsientoRegistralEnum(
			REENVIADO_Y_ERROR_STRING, REENVIADO_Y_ERROR_VALUE);

	/**
	 * ANULADO - El asiento devuelto ha sido anulado.
	 */
	public static final EstadoAsientoRegistralEnum ANULADO = new EstadoAsientoRegistralEnum(
			ANULADO_STRING, ANULADO_VALUE);

	/**
	 * RECIBIDO - El asiento ha sido recibido.
	 */
	public static final EstadoAsientoRegistralEnum RECIBIDO = new EstadoAsientoRegistralEnum(
			RECIBIDO_STRING, RECIBIDO_VALUE);

	/**
	 * RECHAZADO - El asiento recibido ha sido rechazado pero a la espera del ACK.
	 */
	public static final EstadoAsientoRegistralEnum RECHAZADO = new EstadoAsientoRegistralEnum(
			RECHAZADO_STRING, RECHAZADO_VALUE);

	/**
	 * RECHAZADO_Y_ACK - El asiento recibido ha sido rechazado y recibido el ACK.
	 */
	public static final EstadoAsientoRegistralEnum RECHAZADO_Y_ACK = new EstadoAsientoRegistralEnum(
			RECHAZADO_Y_ACK_STRING, RECHAZADO_Y_ACK_VALUE);

	/**
	 * RECHAZADO_Y_ERROR - El asiento recibido ha sido rechazado y recibido mensaje de ERROR.
	 */
	public static final EstadoAsientoRegistralEnum RECHAZADO_Y_ERROR = new EstadoAsientoRegistralEnum(
			RECHAZADO_Y_ERROR_STRING, RECHAZADO_Y_ERROR_VALUE);

	/**
	 * VALIDADO - El asiento recibido ha sido validado.
	 */
	public static final EstadoAsientoRegistralEnum VALIDADO = new EstadoAsientoRegistralEnum(
			VALIDADO_STRING, VALIDADO_VALUE);
	/**
	 * REINTENTAR_VALIDACION - El asiento no ha podido validarse por un fallo en la comunicación con el CIR
	 */
	public static final EstadoAsientoRegistralEnum REINTENTAR_VALIDACION = new EstadoAsientoRegistralEnum(REINTENTAR_VALIDACION_STRING, REINTENTAR_VALIDACION_VALUE);



	/**
	 * Constructor.
	 *
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected EstadoAsientoRegistralEnum(String name, int value) {
		super(name, value);
	}

	/**
	 * Obtiene la constante asociada al valor.
	 * @param value Valor de la constante
	 * @return Constante.
	 */
	public static EstadoAsientoRegistralEnum getEstadoAsientoRegistral(int value) {
		return (EstadoAsientoRegistralEnum) EnumUtils.getEnum(EstadoAsientoRegistralEnum.class, value);
	}
}
