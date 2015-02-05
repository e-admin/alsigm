package deposito.actions;

/**
 * Claves de errores que pueden darse en la gestión de depósito
 */
public class ErrorKeys {
	public static final String NOMBRE_ES_OBLIGATORIO = "errors.deposito.nombreUbicacionObligatorio";
	public static final String NUMERO_ELEMENTOS_INCORRECTO = "errors.deposito.numeroElementosIncorrecto";
	public static final String LONGITUD_ASIGNABLE_INCORRECTA = "errors.deposito.longitudAsignableIncorrecta";
	public static final String NUMERO_HUECOS_INCORRECTO = "errors.deposito.numeroHuecosIncorrecto";
	public static final String ESPACIO_INSUFICIENTE = "errors.deposito.espacioInsuficiente";
	public static final String NO_HAY_HUECOS_A_SELECCIONAR = "errors.deposito.noHayHuecosASeleccionar";
	public static final String NOMBRE_UBICACION_REPETIDO = "errors.deposito.nombreUbicacionRepetido";
	public static final String DEPOSITO_ELECTRONICO_CREATE_ERROR_EXIST = "archigest.archivo.deposito.electronico.create.error.existente";
	public static final String DEPOSITO_ELECTRONICO_DELETE_ERROR_ENUSO = "archigest.archivo.deposito.electronico.delete.error.enuso";
	public static final String ERROR_UDOCS_NO_DISPONIBLES_AL_REUBICAR = "errors.deposito.uDocsNoDisponiblesAlReubicar";
	public static final String ERROR_SOLO_ES_POSIBLE_SELECCIONAR_ULTIMAS_UDOCS = "error.deposito.soloEsPosibleSeleccionarUltimasUdocs";
	public static final String ERROR_UINSTALACION_BLOQUEADA_NO_COMPACTAR = "archigest.archivo.deposito.uInstalacionBloqueadaNoCompactar";
	public static final String ERROR_SELECCIONES_AL_MENOS_UNO = "errors.deposito.formato.seleccioneAlMenosUno";
	// public final static String
	// ES_NECESARIO_SELECCIONAR_UNA_UBICACION_PARA_LA_RESERVA_MESSAGE_KEY =
	// "errors.deposito.reserva.EsNecesarioSeleccionarUnaUbicacionParaLaReserva";
	public final static String ES_NECESARIO_SELECCIONAR_UNA_UBICACION_PARA_LOS_HUECOS_SIN_RESERVA_MESSAGE_KEY = "errors.deposito.reserva.EsNecesarioSeleccionarUnaUbicacionParaLosHuecosSinReserva";
	public static String ERROR_USUARIO_SIN_ARCHIVOS = "archigest.archivo.sin.archivos";
	public static final String NOMBRE_ELEMENTO_NO_ASIGNABLE_REPETIDO = "errors.deposito.nombreElementoNoAsignableRepetido";
}