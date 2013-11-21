package es.ieci.tecdoc.isicres.api.intercambioregistral.business.exception;

public class IntercambioRegistralExceptionCodes {

	private IntercambioRegistralExceptionCodes() {
	}

	public static final Integer DEFAULT_CODE = 0;

	public static final Integer ERROR_CODE_OFICINA_NO_MAPEADA = 1;
	public static final Integer ERROR_CODE_DESTINO_YA_ENVIADO = 2;
	public static final Integer ERROR_CODE_PROVINCIA_LOCALIDAD_NO_MAPEADAS = 3;


	public static final Integer ERROR_CODE_VALIDACION_ASUNTO_RESUMEN = 4;
	public static final Integer ERROR_CODE_VALIDACION_INTERESADOS_U_ORIGEN = 5;
	public static final Integer ERROR_CODE_VALIDACION_MAX_NUM_FICHEROS = 6;
	public static final Integer ERROR_CODE_VALIDACION_MAX_SIZE = 7;

	public static final Integer ERROR_CODE_LEER_ADJUNTOS = 8;
	public static final Integer ERROR_CODE_USUARIO_SIGEM = 9;

	public static final Integer ERROR_CODE_UNIDAD_TRAMITACION_NO_MAPEADA = 10;
	public static final Integer ERROR_NOT_SEND_INTERCAMBIO_REGISTRAL = 11;

	public static final Integer ERROR_CODE_VALIDACION_INTERESADOS_DIRECCION = 14;

	public static final Integer ERROR_CODE_VALIDACION_REPRESENTANTES_DIRECCION = 15;
	public static final Integer ERROR_CODE_PROVINCIA_LOCALIDAD_REPRESENTANTES_NO_MAPEADAS = 16;

	public static final Integer ERROR_CODE_VALIDACION_LENGTH_NUMERO_TRANSPORTE = 17;
	public static final Integer ERROR_CODE_VALIDACION_TIPO_TRANSPORTE = 18;

	public static final Integer ERROR_CODE_VALIDACION_LENGTH_COMENTARIOS = 19;
	public static final Integer ERROR_CODE_VALIDACION_INTERESADOS_DIRECCION_LENGTH = 20;
	public static final Integer ERROR_CODE_VALIDACION_EXPONE_LENGTH = 21;
	public static final Integer ERROR_CODE_VALIDACION_SOLICITA_LENGTH = 22;

	public static final Integer ERROR_CODE_VALIDACION_EXTENSION_FILES = 23;

	public static final Integer ERROR_CODE_VALIDACION_MINIMOS_INTERESADOS=24;

	public static final Integer ERROR_CODE_VALIDACION_UNID_TRAMITA_ENTIDAD_REG=25;

	public static final Integer ERROR_CODE_RECHAZO_ACEPTACION_ORIGEN=26;

	public static final Integer ERROR_CODE_VALIDACION_MAX_SIZE_SET_FILES = 27;

	public static final Integer ERROR_VALIDACION_CHANNEL_NOTIFICATION_DEFAULT_BY_ADDRESS = 28;

}

