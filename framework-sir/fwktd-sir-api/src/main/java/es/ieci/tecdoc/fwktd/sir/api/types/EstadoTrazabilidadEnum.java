package es.ieci.tecdoc.fwktd.sir.api.types;

import es.ieci.tecdoc.fwktd.core.enums.StringValuedEnum;

/**
 * Enumerados para las constantes de estados de trazabilidad.
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class EstadoTrazabilidadEnum extends StringValuedEnum {

	private static final long serialVersionUID = -585507961217664513L;

	/**
	 * Estado que identifica si un registro ha sido enviado al CIR origen y va a
	 * ser procesado por el mismo.
	 */
	public static final EstadoTrazabilidadEnum REGISTRO_PENDIENTE = new EstadoTrazabilidadEnum(
			"REGISTRO PENDIENTE", "01");

	/**
	 * Estado que indica si el registro es un envío. Este estado se inserta
	 * cuando el registro sea enviado desde el nodo distribuido origen al nodo
	 * distribuido destino.
	 */
	public static final EstadoTrazabilidadEnum ENVIO = new EstadoTrazabilidadEnum(
			"ENVIO", "02");

	/**
	 * Estado de la plataforma SIR que indica que el registro es un reenvío.
	 * Este estado se inserta cuando el registro sea enviado desde el nodo
	 * distribuido origen al nodo distribuido destino.
	 */
	public static final EstadoTrazabilidadEnum REENVIO = new EstadoTrazabilidadEnum(
			"REENVIO", "03");

	/**
	 * Estado de la plataforma SIR que indica que el registro es un rechazo.
	 * Este estado se inserta cuando el registro sea enviado desde el nodo
	 * distribuido origen al nodo distribuido destino.
	 */
	public static final EstadoTrazabilidadEnum RECHAZO = new EstadoTrazabilidadEnum(
			"RECHAZO", "04");

	/**
	 * Estado de la plataforma SIR que identifica que un registro ha sido
	 * enviado al CIR destino y va a ser procesado por el mismo.
	 */
	public static final EstadoTrazabilidadEnum REGISTRO_RECIBIDO_ENVIO = new EstadoTrazabilidadEnum(
			"REGISTRO RECIBIDO-ENVIO", "05");

	/**
	 * Estado final de la plataforma SIR que indica que el envío del registro ha
	 * sido satisfactorio. Este estado se insertará cuando el nodo distribuido
	 * destino invoca de manera satisfactoria al servicio web de envío de
	 * ficheros a aplicación de la aplicación destino.
	 */
	public static final EstadoTrazabilidadEnum REGISTRO_ENVIADO = new EstadoTrazabilidadEnum(
			"REGISTRO ENVIADO", "06");

	/**
	 * Estado final de la plataforma SIR que indica que el reenvío del registro
	 * ha sido satisfactorio. Este estado se insertará cuando el registro sea
	 * enviado desde el nodo distribuido origen al nodo distribuido destino.
	 */
	public static final EstadoTrazabilidadEnum REGISTRO_REENVIADO = new EstadoTrazabilidadEnum(
			"REGISTRO REENVIADO", "07");

	/**
	 * Estado final de la plataforma SIR que indica que el rechazo del registro
	 * ha sido satisfactorio. Este estado se insertará cuando el registro sea
	 * enviado desde el nodo distribuido origen al nodo distribuido destino.
	 */
	public static final EstadoTrazabilidadEnum REGISTRO_RECHAZADO = new EstadoTrazabilidadEnum(
			"REGISTRO RECHAZADO", "08");

	/**
	 * Estado final de la plataforma SIR que indica que la confirmación del
	 * registro ha sido satisfactoria.
	 */
	public static final EstadoTrazabilidadEnum REGISTRO_CONFIRMADO = new EstadoTrazabilidadEnum(
			"REGISTRO CONFIRMADO", "09");

	/**
	 * Estado de la plataforma SIR que identifica que un fichero de mensaje ha
	 * sido enviado al CIR origen y va a ser procesado por el mismo.
	 */
	public static final EstadoTrazabilidadEnum MENSAJE_PENDIENTE = new EstadoTrazabilidadEnum(
			"MENSAJE PENDIENTE", "11");

	/**
	 * Estado de la plataforma SIR que indica que el fichero de mensaje ha sido
	 * enviado al nodo distribuido destino.
	 */
	public static final EstadoTrazabilidadEnum MENSAJE_ENVIADO = new EstadoTrazabilidadEnum(
			"MENSAJE ENVIADO", "12");

	/**
	 * Estado de la plataforma SIR que identifica que un fichero de mensaje ha
	 * sido enviado al CIR destino y va a ser procesado por el mismo.
	 */
	public static final EstadoTrazabilidadEnum MENSAJE_RECIBIDO_ENVIO = new EstadoTrazabilidadEnum(
			"MENSAJE RECIBIDO-ENVIO", "13");

	/**
	 * Estado final de la plataforma SIR que indica que el envío del fichero de
	 * mensaje ha sido satisfactorio. Este estado se insertará cuando el nodo
	 * distribuido destino invoca de manera satisfactoria al servicio web de
	 * envío de mensaje de datos de control a aplicación de la aplicación
	 * destino.
	 */
	public static final EstadoTrazabilidadEnum MENSAJE_PROCESADO = new EstadoTrazabilidadEnum(
			"MENSAJE PROCESADO", "14");

	/**
	 * Estado final de la plataforma SIR que indica que la recepción del
	 * registro en el destino ha sido satisfactoria.
	 */
	public static final EstadoTrazabilidadEnum RECEPCIÓN_CORRECTA = new EstadoTrazabilidadEnum(
			"RECEPCIÓN CORRECTA", "15");

	/**
	 * Estado final de SIR que indica que se ha producido algún error en el
	 * proceso de intercambio.
	 */
	public static final EstadoTrazabilidadEnum ERROR = new EstadoTrazabilidadEnum(
			"ERROR", "99");

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected EstadoTrazabilidadEnum(String name, String value) {
		super(name, value);
	}

	/**
	 * Obtiene la constante asociada al valor.
	 * 
	 * @param value
	 *            Valor de la constante
	 * @return Constante.
	 */
	public static EstadoTrazabilidadEnum getEstadoTrazabilidad(String value) {
		return (EstadoTrazabilidadEnum) StringValuedEnum.getEnum(
				EstadoTrazabilidadEnum.class, value);
	}
}
