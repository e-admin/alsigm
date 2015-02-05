package common.view;

/**
 * Claves para los errores que se pueden producir en la gestión de Fondos
 * Documentales
 */
public class ErrorKeys {
	public static final String NECESARIOS_CRITERIOS_BUSQUEDA = "errors.noSearchToken";

	public static final String SELECCIONAR_TIPO_PROCEDIMIENTO = "errors.previsiones.elegirTipoProcedimiento";
	public static final String NECESARIO_SELECCIONAR_LINEA_DETALLE = "errors.previsiones.seleccionarLineaDetalle";
	public static final String NECESARIO_SELECCIONAR_SERIE = "errors.previsiones.seleccionarSerie";
	public static final String RELACION_NECESARIO_SELECCIONAR_SERIE = "errors.previsiones.seleccionarSerie";

	/**
	 * Clave del fichero de propiedades que contiene el texto de asunto<br>
	 * <b>Valor: </b>errors.relaciones.seleccionarProductor<br>
	 * <b>Asunto: </b>Es necesario seleccionar el productor de la relaci\u00f3n
	 */
	public static final String RELACION_NECESARIO_SELECCIONAR_PRODUCTOR = "errors.relaciones.seleccionarProductor";
	public static final String EL_TITULO_DE_LA_SERIE_ES_NECESARIO = "errors.fondos.identificacion.tituloEsNecesario";
	public static final String SELECCIONAR_NUEVO_PADRE_PARA_EL_FONDO = "errors.fondos.noSeHaSeleccionadoNuevoPadreParaElFondo";
	public static final String SELECCIONAR_NUEVO_PADRE_PARA_LA_SERIE = "errors.fondos.noSeHaSeleccionadoNuevoPadreParaLaSerie";
	public static final String ESTADO_HUECO_ALTERADO = "errors.deposito.estadoHuecoAlterado";
	public static final String NECESARIO_NOMBRE_ROL = "error.gcontrol.roles.NecesarioNombreParaElRol";
	public static final String RELACION_NECESARIO_SELECCIONAR_NIVEL_DOCUMENTAL = "errors.relaciones.seleccionarNivelDocumental";
	public static final String DESCRIPCION_BLOQUEO_VALIDACION_RELACION = "errors.descripcion.bloqueo.validacion.relacion";
	public static final String RELACION_FICHA_TITULO_MAL_DEFINIDO = "errors.relaciones.ficha.titulo.malDefinido";
	public static final String RELACION_FICHA_FECHA_INICIAL_MAL_DEFINIDA = "errors.relaciones.ficha.fechaInicial.malDefinido";
	public static final String RELACION_FICHA_FECHA_FINAL_MAL_DEFINIDA = "errors.relaciones.ficha.fechaFinal.malDefinida";

}