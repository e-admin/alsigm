package es.ieci.tecdoc.fwktd.sir.core.types;

import es.ieci.tecdoc.fwktd.core.enums.StringValuedEnum;

/**
 * Enumerados para los errores producidos en el servicio web.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ErroresEnum extends StringValuedEnum {

	private static final long serialVersionUID = -8630400050851973320L;

	public static final ErroresEnum OK = new ErroresEnum("Exito", "00");
	public static final ErroresEnum ERROR_INESPERADO = new ErroresEnum("Error inesperado", "-1");

	/*
	 * Errores definidos en la versión 1 del documento de integración 
	 *
	
//	public static final ErroresEnum ERROR_0001 = new ErroresEnum("Error en la validación del registro en el envío", "0001");
//	public static final ErroresEnum ERROR_0002 = new ErroresEnum("Error en la consulta de la url de destino", "0002");
//	public static final ErroresEnum ERROR_0003 = new ErroresEnum("Error en la inserción en trazabilidad en el envío", "0003");
//	public static final ErroresEnum ERROR_0004 = new ErroresEnum("Error al firmar el registro", "0004");
//	public static final ErroresEnum ERROR_0005 = new ErroresEnum("Error al enviar el registro", "0005");
//	public static final ErroresEnum ERROR_0006 = new ErroresEnum("Error al consultar la clave pública", "0006");
//	public static final ErroresEnum ERROR_0007 = new ErroresEnum("Error al validar la firma", "0007");
	public static final ErroresEnum ERROR_0008 = new ErroresEnum("Error en la validación del registro en la recepción", "0008");
//	public static final ErroresEnum ERROR_0009 = new ErroresEnum("Error en la inserción en trazabilidad en la recepción", "0009");

	public static final ErroresEnum ERROR_0011 = new ErroresEnum("Error al almacenar el registro", "0011");
	public static final ErroresEnum ERROR_0012 = new ErroresEnum("Error al descomprimir el registro", "0012");
//	public static final ErroresEnum ERROR_0013 = new ErroresEnum("Error al comprimir el registro", "0013");
	public static final ErroresEnum ERROR_0014 = new ErroresEnum("Error al almacenar los documentos en destino", "0014");
//	public static final ErroresEnum ERROR_0015 = new ErroresEnum("Error, registro no firmado en destino", "0015");
//	public static final ErroresEnum ERROR_0016 = new ErroresEnum("Error en validar firma, recuperada más de una clave pública", "0016");
//	public static final ErroresEnum ERROR_0017 = new ErroresEnum("Error al recuperar varias URLs destino para un destino", "0017");
	public static final ErroresEnum ERROR_0018 = new ErroresEnum("Error, no coinciden los documentos anexos con los informados en el registro", "0019");
	public static final ErroresEnum ERROR_0019 = new ErroresEnum("Error en la validación del fichero de registro", "0019");
	public static final ErroresEnum ERROR_0020 = new ErroresEnum("Error en la validación del fichero de mensaje", "0020");
	public static final ErroresEnum ERROR_0021 = new ErroresEnum("Error, nombre del fichero de registro incorrecto", "0021");
	public static final ErroresEnum ERROR_0022 = new ErroresEnum("Error, nombre del fichero de mensaje incorrecto", "0022");
	public static final ErroresEnum ERROR_0023 = new ErroresEnum("Error, nombre del zip incorrecto", "0023");
	public static final ErroresEnum ERROR_0024 = new ErroresEnum("Error, nombre de documento adjunto incorrecto", "0024");
	public static final ErroresEnum ERROR_0025 = new ErroresEnum("Error, tipo de registro incorrecto", "0025");
	public static final ErroresEnum ERROR_0026 = new ErroresEnum("Error, oficina de registro origen incorrecta", "0026");
	public static final ErroresEnum ERROR_0027 = new ErroresEnum("Error, organismo origen incorrecto", "0027");
	public static final ErroresEnum ERROR_0028 = new ErroresEnum("Error, oficina de registro destino incorrecta", "0028");
	public static final ErroresEnum ERROR_0029 = new ErroresEnum("Error, organismo destino incorrecto", "0029");
	public static final ErroresEnum ERROR_0030 = new ErroresEnum("Error, tipo de mensaje incorrecto", "0030");

//	public static final ErroresEnum ERROR_0100 = new ErroresEnum("Error de conexión con destino", "0100");
//	public static final ErroresEnum ERROR_0101 = new ErroresEnum("Error, interfaz servicio web destino errónea", "0101");
//	public static final ErroresEnum ERROR_0114 = new ErroresEnum("Error de conexión con destino", "0114");
//	public static final ErroresEnum ERROR_0200 = new ErroresEnum("Error en la consulta de trazabilidad", "0200");
//	public static final ErroresEnum ERROR_0201 = new ErroresEnum("Error en la consulta de trazabilidad, código de intercambio no encontrado", "0201");
//	public static final ErroresEnum ERROR_0202 = new ErroresEnum("Error al acceder a base de datos en la consulta de trazabilidad", "0202");
//	public static final ErroresEnum ERROR_0203 = new ErroresEnum("Error en la inserción de error en trazabilidad", "0203");
//	public static final ErroresEnum ERROR_0204 = new ErroresEnum("Error al acceder a base de datos en la inserción de trazabilidad", "0204");
	public static final ErroresEnum ERROR_0205 = new ErroresEnum("Error al recibir en la aplicación un registro duplicado", "0205");
	public static final ErroresEnum ERROR_0206 = new ErroresEnum("Error al recibir en la aplicación un mensaje duplicado", "0206");

	*/

	/*
	 * Errores definidos en la versión 7 del documento de integración 
	 */
	
//	public static final ErroresEnum ERROR_0036 = new ErroresEnum("ERROR_PROCESO_FIRMA_MENSAJE", "0036"); // Error durante la firma del mensaje.
	public static final ErroresEnum ERROR_0037 = new ErroresEnum("ERROR_VALIDACION_FORMATO_XML", "0037"); // Error al validar el contenido del XML de mensaje o registro.
	public static final ErroresEnum ERROR_0038 = new ErroresEnum("ERROR_ALMACENAR_TRAZABILIDAD_WS", "0038"); // Error al insertar la trazabilidad en el nodo central de la plataforma SIR.
//	public static final ErroresEnum ERROR_0039 = new ErroresEnum("ERROR_NO_SE_PUEDE_ALCANZAR_DESTINO", "0039"); // Error al consultar la IP del destino.
//	public static final ErroresEnum ERROR_0040 = new ErroresEnum("ERROR_VALIDACION_FIRMA", "0040"); // Error al validar la firma del registro o del mensaje.
	public static final ErroresEnum ERROR_0041 = new ErroresEnum("ERROR_CAMPO_TIPO_REGISTRO", "0041"); // Error al validar el tipo de anotación de la trazabilidad.
//	public static final ErroresEnum ERROR_0042 = new ErroresEnum("ERROR_COMPRIMIR_FICHERO", "0042"); // Error al comprimir el registro.
	public static final ErroresEnum ERROR_0043 = new ErroresEnum("ERROR_CONSULTAR_CERTIFICADO", "0043"); // Error al consultar el certificado.
	public static final ErroresEnum ERROR_0044 = new ErroresEnum("ERROR_AL_RECIBIR_MENSAJE", "0044"); // Error al construir el mensaje.
	public static final ErroresEnum ERROR_0045 = new ErroresEnum("ERROR_FICHEROS_ADJUNTOS", "0045"); // Error en los ficheros adjuntos.
//	public static final ErroresEnum ERROR_0046 = new ErroresEnum("ERROR_OFICINA_SIN_URL", "0046"); // Error al consultar la URL de la aplicación destino.
	public static final ErroresEnum ERROR_0047 = new ErroresEnum("ERROR_NOMBRE_ZIP", "0047"); // Error al validar el nombre del archivo comprimido.
	public static final ErroresEnum ERROR_0048 = new ErroresEnum("ERROR_DESCOMPRIMIR_FICHERO", "0048"); // Error al descomprimir el archivo del registro.
	public static final ErroresEnum ERROR_0049 = new ErroresEnum("ERROR_DESCOMPRIMIR_MENSAJE", "0049"); // Error al descomprimir el archivo del mensaje.
//	public static final ErroresEnum ERROR_0050 = new ErroresEnum("ERROR_COMPRIMIR_MENSAJE", "0050"); // Error al comprimir el mensaje.
//	public static final ErroresEnum ERROR_0051 = new ErroresEnum("ERROR_PROCESO_FIRMA_REGISTRO", "0051"); // Error al generar la firma del asiento.
//	public static final ErroresEnum ERROR_0053 = new ErroresEnum("ERROR_PROCESO_VALIDACIÓN_FIRMA_ASIENTO", "0053"); // Error al validar la firma del asiento.
	public static final ErroresEnum ERROR_0055 = new ErroresEnum("ERROR_GESTIONAR_REINTENTOS_REGISTRO", "0055"); // Error al gestionar los reintentos del asiento.
	public static final ErroresEnum ERROR_0056 = new ErroresEnum("ERROR_GESTIONAR_REINTENTOS_MENSAJE", "0056"); // Error al gestionar los reintentos del mensaje.
	public static final ErroresEnum ERROR_0057 = new ErroresEnum("ERROR_ALMACENAR_TRAZABILIDAD_REGISTRO", "0057"); // Error al insertar la trazabilidad del asiento.
//	public static final ErroresEnum ERROR_0058 = new ErroresEnum("ERROR_COMPROBAR_INTEGRACION_APLICACION", "0058"); // Error al obtener la modalidad de integración (FS, WS) de la aplicación destino.
	public static final ErroresEnum ERROR_0059 = new ErroresEnum("ERROR_GESTION_MENSAJES_ERROR", "0059"); // Error al tratar los mensajes de error.
//	public static final ErroresEnum ERROR_0060 = new ErroresEnum("ERROR_OBTENCION_VALOR_XML", "0060"); // Error al obtener un valor del XML.
//	public static final ErroresEnum ERROR_0061 = new ErroresEnum("ERROR_GENERAR_NOMBRE_MENSAJE", "0061"); // Error al generar un nombre para el mensaje.
//	public static final ErroresEnum ERROR_0062 = new ErroresEnum("ERROR_GENERAR_NOMBRE_ASIENTO", "0062"); // Error al generar un nombre para el registro.
	public static final ErroresEnum ERROR_0063 = new ErroresEnum("ERROR_AL_RECIBIR_ASIENTO", "0063"); // Error al obtener el registro.
	public static final ErroresEnum ERROR_0064 = new ErroresEnum("ERROR_EN_EL_MENSAJE", "0064"); // Error en el mensaje.
	public static final ErroresEnum ERROR_0065 = new ErroresEnum("ERROR_EN_EL_ASIENTO", "0065"); // Error en el asiento.
	public static final ErroresEnum ERROR_0066 = new ErroresEnum("ERROR_AL_EXTRAER", "0066"); // Error al descomprimir el asiento.
	
	public static final ErroresEnum ERROR_0205 = new ErroresEnum("ERROR, DUPLICIDAD DE FICHERO DE INTERCAMBIO EN DESTINO", "0205"); // Error al recibir en la aplicación un registro duplicado.
	public static final ErroresEnum ERROR_0206 = new ErroresEnum("ERROR, DUPLICIDAD DE FICHERO DE MENSAJES EN DESTINO", "0206"); // Error al recibir en la aplicación un mensaje duplicado.


	/**
	 * Constructor.
	 *
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected ErroresEnum(String name, String value) {
		super(name, value);
	}
}
