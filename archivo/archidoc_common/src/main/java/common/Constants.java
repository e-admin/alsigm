package common;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import solicitudes.prestamos.PrestamosConstants;

import common.util.StringUtils;

/**
 * Cosntantes globales de la aplicacion
 *
 */
public class Constants {

	/*-----------------------------------------------------------------------------------------------
	 * CONSTANTES
	 * ----------------------------------------------------------------------------------------------*/
	public static final String MODULE = "module";
	public static final String ACCION_ = "accion-";
	public static final String ACCIONES = "acciones";

	public static String FALSE_STRING = "N";
	public static String TRUE_STRING = "S";
	public static String TRUE_FULL_STRING = "Si";
	public static String FALSE_FULL_STRING = "No";
	public static String CHECKED_STRING = "on";
	public static final String BOOLEAN_TRUE_STRING = "true";
	public static String UNCHECKED_STRING = "off";
	public static String XML_HEADER = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>";
	public static String XML_DEFINITION_OPEN = "<Definicion_Ficha Version=\"01.00\">";
	public static String XML_DEFINITION_CLOSE = "</Definicion_Ficha>";
	public static String XML_INFORMACION_CAMPOS_OPEN = "<Informacion_Campos>";
	public static String XML_INFORMACION_CAMPOS_CLOSE = "</Informacion_Campos>";
	public static String ENCODING_ISO_8859_1 = "ISO-8859-1";

	public static String XML_DEFINITION_FMTFICHAS_OPEN = "<Definicion_FmtFicha Version=\"01.00\">";
	public static String XML_DEFINITION_FMTFICHAS_CLOSE = "</Definicion_FmtFicha>";
	public static String XML_ELEMENTOS_OPEN = "<Elementos>";
	public static String XML_ELEMENTOS_CLOSE = "</Elementos>";

	public static String CODIGO_DE_REFERENCIA = "Código de Referencia";
	public static String NUMERO_DE_EXPEDIENTE = "Número de expediente";
	public static String TITULO = "Título";
	public static String NIVEL_DE_DESCRIPCION = "Nivel de descripción";
	public static String ID = "id";
	public static String ID_FORMATO_FICHA = "idformato";
	public static String ID_NUEVO_FORMATO_FICHA = "idnuevoformato";
	public static String TIPO_ACCESO_FICHA = "tipoacceso";
	public static String COMMA = ",";
	public static String DOS_PUNTOS = ": ";
	public static String ARROBA = "@";
	public static String SLASH = "/";
	public static String SEPARADOR_SPLIT = "\\|";
	public static String SEPARADOR_ICONOS_DEPOSITO = ";";
	public static String CARACTERES_TRUNCADO = "...";
	public static final String ITEM = "item";
	public static final String GUION = " - ";

	public static final String FORMATO_UI_TRANSFERENCIA_ELECTRONICA = "DOCUMENTOS_ELECTRONICOS";

	public static final String SALTO_LINEA_HTML = "<br/>";

	public static String BLANK = "";
	public static String UNDERSCORE = "_";
	public static String EQUAL="=";
	public static String LISTA_USO_OBJETO = "listaUsoObjeto";
	public final static String FORMATO_FECHA_DDMMAAAA = "DDMMAAAA";
	public final static String FORMATO_FECHA = "dd/MM/yyyy";
	public final static String FORMATO_HORA = "HH:mm:ss";
	public final static String FORMATO_FECHA_DETALLADA = "dd/MM/yyyy HH:mm:ss";
	public final static String FORMATO_FECHA_EXTENDIDA = "EEEEEEEEEEEE, dd 'de' MMMMMMMMMM 'de' yyyy - hh:mm:ss aa";
	public final static String DELIMITER_PARAMETER_FORMBEAN = "#";
	public final static String ABRIR_PARENTESIS = "(";
	public final static String CERRAR_PARENTESIS = ")";
	public final static String MAYOR = ">";
	public final static String MAYOR_IGUAL = ">=";
	public final static String MENOR = "<";
	public final static String MENOR_IGUAL = "<=";
	public final static String RANGO = "[..]";
	public final static String STRING_EQUAL = " igual a ";
	public final static String STRING_MAYOR = " mayor que ";
	public final static String STRING_MAYOR_IGUAL = " mayor o igual que ";
	public final static String STRING_MENOR = " menor que ";
	public final static String STRING_MENOR_IGUAL = " menor o igual que ";
	public final static String STRING_RANGO_INI = " desde ";
	public final static String STRING_RANGO_FIN = " hasta ";
	public final static String STRING_IGUAL = "igual";
	public final static String STRING_CONTIENE = " contiene ";


	public final static String DELIMITER_IDS = ":";
	public final static String STRING_EMPTY = "";
	public final static String STRING_SPACE = " ";
	public final static String STRING_NEW_LINE = "\n";
	public final static String STRING_TEXT = "text";
	public final static String STRING_CERO = "0";
	public final static char CARACTER_COMILLA_DOBLE = '"';
	public final static char CARACTER_ESPACIO_BLANCO = ' ';
	public static final String NEWLINE = System.getProperty("line.separator");
	public final static String BANDEJA_KEY = "BANDEJA_KEY";
	public final static String LISTA_ELEMENTOS_ERROR_KEY = "LISTA_ELEMENTOS_ERROR_KEY";
	public final static String LISTA_ELEMENTOS_MENSAJE_KEY = "LISTA_ELEMENTOS_MENSAJE_KEY";

	public final static String METHOD_KEY = "METHOD_KEY";
	public final static String ID_SYSSUPERUSER = "1";
	public final static String FILA = "Fila";
	public final static String NOMBRE_FICHERO_FICHA = "defFicha";
	public static String LAST_ORDER="lastOrder";
	public static String LAST_ORDER_DIRECTION="lastOrderDirection";
	public static String PAGE_NUMBER="pageNumber";
	public static String SEPARADOR_IDENTIFICADOR_UNIDAD_INSTALACION = ".";
	public static final String SEPARADOR_ANTECESORES_ORGANO = "/";
	public static final String COMODIN_BUSQUEDAS = "%";
	public static final String SEPARADOR_NUM_HUECOS = "-";

	//TABLAS TEMPORALES
	   public final static Integer TABLA_TEMPORAL_LIBRE_STATE = new Integer(0);

	   public final static Integer TABLA_TEMPORAL_OCUPADO_STATE = new Integer(1);

	   public final static String TABLA_TEMPORAL_TIPO_UDOC	= "D";

	   public final static String TABLA_TEMPORAL_TIPO_UI = "I";
	/**
	 * Caracter Separador de las partes de una unidad de instalacion 00000000/1<br>
	 * <b>Valor:</b> /
	 */
	public static String SEPARADOR_PARTES_UNIDAD_INSTALACION = "/";

	/**
	 * Caracter Separador de las signatura de una unidad documental dentro de una caja 00000000/1<br>
	 * <b>Valor:</b> /
	 */
	public static String SEPARADOR_SIGNATURA_UNIDAD_DOCUMENTAL= "/";

	/**
	 * Caracter Separador del Path de los elementos del deposito
	 * <b>Valor:</b> /
	 */
	public static String SEPARADOR_PATH_ELEMENTOS_DEPOSITO= "/";

	/**
	 * Caracter Separador de la identificación de unidad documental 00000000/1-1<br>
	 * <b>Valor:</b> -
	 */
	public static String SEPARADOR_IDENTIFICADOR_UNIDAD_DOCUMENTAL = "-";

	/**
	 * Caracter Separador de los valores que identifican a un elemento unívocamente, por ejemplo, a una unidad documental en depósito
	 * idelemento@@signaturaUdoc
	 * <b>Valor:</b> -
	 */
	public static String SEPARADOR_IDENTIFICADOR_BUSQUEDAS = "@@";

	/**
	 * Tipo de Documento NIF
	 */
	public static int TIPO_DOCUMENTO_NIF = 0;

	/**
	 * Tipo de Documento CIF
	 */
	public static int TIPO_DOCUMENTO_CIF = 1;

	/**
	 * Otro tipo de Documento
	 */
	public static int TIPO_DOCUMENTO_OTRO = 2;

	/**
	 * Tipo de Interesado No validado
	 */
	public static int TIPO_INTERESADO_NO_VALIDADO = 0;

	/** Tipo de Interesado Validado */
	public static int TIPO_INTERESADO_VALIDADO = 1;
	public static String ESTADO_VIGENTE = "S";
	public static String ESTADO_NO_VIGENTE = "N";
	public static String TIPO_SOLICITANTE_INTERNO = "interno";
	public static String NUM_MAX_INTENTOS="numMaxIntentos";
	public static String TIPO_APLICACION_PDF = "application/pdf";
	public static final String FORMATO_SIGNATURA = "000000000";
	public static final String FORMATO_COD_UDOC = "000000000";
	public static final int ELEMENTOCF_VIGENTE=2;
	public static final String FORMATO_SIGNATURA_CAJA = "0000000000000000";
	public static final String DEFAULT_MAC ="000000000000";

	/**
	 * Formato del Código de Orden.
	 */
	public static final String FORMATO_COD_ORDEN = "0000";

	/**
	 * Separador del Código de Orden Ej. D0001_E0001
	 */
	public static final String SEPARADOR_COD_ORDEN = "_";

	/**
	 * Letra del Hueco para la creación del Código de Orden.
	 */
	public static final String LETRA_HUECO = "H";

	/**
	 * Valor para indicar que la valoración no tiene un dictamen definido en bbdd
	 * porque posiblemente Archidoc permitiese dejar este campo vacío
	 */
	public static final int VALOR_DICTAMEN_NO_DEFINIDO = 0;

	/**
	 * Separador entre el código de órgano y la fecha de inicio de la unidad documental de la que se quiere obtener
	 * su órgano para los casos en los que no tenemos el nombre del órgano
	 */
	public static final String SEPARADOR_COD_FECHA = "_";

	public static final String SEPARADOR_COD_REGISTRO ="_";

	/**
	 * String indicador de nodo raiz del árbol que representa el cuadro de clasificación
	 */
	public static final String TREE_VIEW_ROOT_NODE = "/";

	/**
	 * Indicador de recarga del árbol
	 */
	public static final String TREE_VIEW_RELOAD = "treeViewReload";


	/**
	 * String separador de rangos en la lista que se establece en el campo de número de expediente
	 */
	public static final String DELIMITADOR_RANGOS = ",";

	/**
	 * String separador de rango inicial y rango final en la lista que se establece en el campo de número de expediente
	 */
	public static final String DELIMITADOR_RANGO_INICIAL_FINAL = " .. ";

	/**
	 * String Posicion que ocupa la unidad documental electrónica. Se utiliza
	 * para generar la signatura de la unidad docuemental.
	 */
	public static final String POSICION_SIGNATURA_UDOCS_ELECTRONICAS = "1";

	/**
	 * Estados de los detalles de los préstamos para los cuales no se pueden realizar las sigientes operaciones
	 * 	- Organizar Caja
	 *  - Compactar Unidad Documental
	 *  - Añadir Unidad Documental a la Relación entre Archivos.
	 */
	public static final int[] ESTADOS_DETALLES_EN_PRESTAMOS = new int[]{
		  PrestamosConstants.ESTADO_DETALLE_PENDIENTE, //1
		  PrestamosConstants.ESTADO_DETALLE_RESERVADA, //2
		  PrestamosConstants.ESTADO_DETALLE_AUTORIZADA, //3
		  PrestamosConstants.ESTADO_DETALLE_ENTREGADA}; //5


	/*-----------------------------------------------------------------------------------------------
	 * ERRORES DE LA APLICACION
	 * ----------------------------------------------------------------------------------------------*/

	public final static String ERROR_CONFIGURACION_FILE_NOT_FOUND = "errors.configuracion.fichero.no.encontrado";

	public final static String ERROR_CONFIGURACION_FILE = "errors.configuracion.generico.fichero";


	/**
	 * Clave de la etiqueta que contiene el texto de <br>
	 * <b>Valor: </b>errors.general<br>
	 * <b>Texto: </b>{0}.<br>
	 * @param Texto Texto a mostrar<br>
	 */
	public final static String ERROR_GENERAL_MESSAGE = "errors.general";
	public final static String ERROR_CAMPO_NO_NUMERICO="archigest.error.general.campo.numerico";
	public final static String ERROR_CAMPOS_OPERADOR_RANGO_OBLIGATORIOS = "archigest.error.general.campo.fila.rango";

	/**
	 * Clave de la etiqueta que contiene el texto de Requerido<br>
	 * <b>Valor: </b>errors.required<br>
	 * <b>Texto: </b>{0} es obligatorio.<br>
	 * @param Texto Etiqueta del campo Requerido<br>
	 */
	public final static String ERROR_REQUIRED = "errors.required";

	public final static String ERROR_OBLIGATORIA = "errors.obligatoria";



	/**
	 * Clave de la etiqueta que contiene el texto de Inválido<br>
	 * <b>Valor: </b>errors.invalid<br>
	 * <b>Texto: </b>{0} no es válido.<br>
	 * @param Texto Etiqueta del campo no válido<br>
	 */
	public final static String ERROR_INVALID = "errors.invalid";
	public final static String ERROR_REFERENCE="errors.reference";

	/**
	 * Clave de la etiqueta que contiene el texto Campo debe ser entero<br>
	 * <b>Valor: </b>errors.integer<br>
	 * <b>Texto: </b>{0} debe ser un entero.<br>
	 * @param Texto Etiqueta del campo entero<br>
	 */
	public final static String ERROR_INT = "errors.integer";
	public final static String ERROR_DOUBLE = "errors.double";
	public final static String ERROR_MAX_RANGE = "errors.maxRange";
	public final static String ERROR_MIN_RANGE = "errors.minRange";

	/**
	 * Clave de la etiqueta que contiene el texto campo Mayor que cero<br>
	 * <b>Valor: </b>errors.ceroNumber<br>
	 * <b>Texto: </b>{0} no puede ser cero.<br>
	 * @param Texto Etiqueta del campo Mayor que cero<br>
	 */
	public final static String ERROR_INT_MAYOR_CERO = "errors.ceroNumber";
	public final static String ERROR_DATE = "errors.date";
	public final static String ERROR_DATE_AFTER = "errors.date.after";
	public final static String ERROR_DATE_NO_AFTER = "errors.date.no.after";
	public static final String ERROR_BUSQUEDA_FILA_VALOR ="archigest.error.general.campo.fila.numerico";
	public final static String ERROR_DATE_BEFORE = "errors.date.before";
	public final static String ERROR_DATE_NO_BEFORE = "errors.date.no.before";
	public final static String ERROR_INITDATE_AFTER_ENDDATE = "error.init-end_dates";
	public final static String ERROR_NOT_SELECTION = "errors.item_no_selected";
	public static final String ERROR_SELECTION_NOT_PERFORMED = "errors.item_selection";
	public static final String ERROR_SELECTION_NOT_PERFORMED2 = "errors.item_selection2";
	public final static String ERROR_NOT_EMPTY_ELEMENT = "error.edicion.elementoNoVacio";
	public final static String ERROR_ELEMENTO_NO_VIGENTE = "errors.item_no_vigente";


	public final static String ERROR_NOT_AVAILABLE_SPACE = "error.reubicacion.espacioInsuficiente";
	public static final String ERROR_GESTOR_ORGANIZACION = "error.ext_systems.gestor_organizacion";
	public static final String ERROR_GESTOR_ORGANIZACION_ORGANO_INEXISTENTE = "error.organo.externo.no.existe";

	public static final String ERROR_GESTOR_CATALOGO = "error.ext_systems.gestor_catalogo";
	public static final String ERROR_GESTOR_GEOGRAFICOS = "error.ext_systems.gestor_geograficos";
	public static final String ERROR_SISTEMA_TRAMITADOR = "error.ext_systems.sistema_tramitador";
	public static final String ERROR_GESTOR_TERCEROS = "error.ext_systems.gestor_terceros";
	public static final String ERROR_FUNCIONALIDAD_NO_DISPONIBLE = "error.ext_systems.funcionalidad_no_disponible";
	public static final String ERROR_BUSQUEDA_TERECEROS_NO_DISPONIBLE = "error.ext_systems.busqueda_terceros.no_disponible";
	public static final String ERROR_TOO_MANY_RESULTS = "error.too_many_results";

	public static final String ERROR_ORGANO_EXTERNO_SIN_CODIGO = "error.organo.externo.sin.codigo";
	public static final String ERROR_ORGANO_NO_ENCONTRADO = "archigest.archivo.series.organo.no.encontrado";

	public static final String ERROR_NO_ENCONTRADAS_LISTAS_PRODUCTORAS = "error.no.encontradas.listas.productoras";
	public static final String ERROR_ACCION_NO_PERMITIDA = "errors.notAllowedException";

	public static final String ERROR_CREAR_UBICACION_USUARIO_SIN_ARCHIVOS = "error.crear.ubicacion.usuario.sin.archivos";
	public static final String ERROR_OPERACION_NO_PERMITIDA_USUARIO_SIN_ARCHIVOS = "error.operacion.no.permitida.usuario.sin.archivos";

	public static final String ERROR_NUM_IDENTIFICACION_NO_VALIDO = "errors.numIdentificacion";
	public static final String ERROR_CONFIRMACION_CLAVE = "error.confirmacion.clave";
	public static final String ERROR_NECESARIO_SELECCIONAR_UN_ASIGNABLE = "error.deposito.necesarioSeleccionarAsignable";
	public static final String ERROR_COLUMNA_NO_INDEXADA = "error.columnNotIndexedException";
	public static final String ERROR_PALABRA_OMITIDA = "error.wordOmittedException";
	public static final String ERROR_SINTAXIS_INCORRECTA = "error.incorrectSintax";
	public static final String ERROR_SIN_PERMISOS = "archigest.archivo.error.sinPermisos";
	public final static String ERROR_AMBITO_CONTENIDO = "errors.ambito.contenido";
	public static final String ERROR_GENERICO_BUSQUEDAS = "error.generico.busquedas";
	public static final String ERROR_GENERICO_REEMPLAZO = "archigest.archivo.reemplazo.error.generico";

	public final static String ERROR_AMBITO_REPETIDO = "errors.ambito.repetido";
	public static final String NUEVA_FECHA_ANTERIOR_A_FECHA_ANTERIOR = "errors.relaciones.lasNuevaFechaHaDeSerSuperiorALaActual";
	public static final String ERROR_FECHA_INICIAL_MAYOR_O_IGUAL_FECHA_FINAL = "errors.archigest.general.fechaFinal.menorIgual.fechaInicial";
	public static final String NUEVA_FECHA_ANTERIOR_A_HOY = "errors.relaciones.laNuevaFechaHaDeSuperiorOIgualAlDiaDeHoy";
	public static final String FECHA_ANTERIOR_A_HOY = "errors.relaciones.FechaHaDeSuperiorOIgualAlDiaDeHoy";
	public static final String ERROR_DATE_AFTER_TODAY = "error.date-after-today";
	public static final String ERROR_DATE_BEFORE_TODAY = "error.date-before-today";
	public static final String ERROR_DATE_INVALID = "errors.date";
	public static final String ERROR_UDOCEND_TOO_EARLY = "error.udocend_too_early";
	public static final String ERROR_UDOCEND_TOO_LATE = "error.udocend_too_late";
	public static final String UDOCS_WITH_ERRORS = "error.udoc_with_errors";
	public static final String EXPEDIENTE_DUPLICADO = "error.expediente_duplicado";
	public static final String FECHA_DEVOLUCION_NO_VALIDA = "errors.solicitudes.fechaNoValidaXReservadasOAutorizadas";

	public final static String EXCEPCION_KEY = "EXCEPCION_KEY";
	public final static String JAVAX_SERVLET_ERROR_EXCEPCION = "javax.servlet.error.exception";
	public final static String USUARIOKEY = "USUARIOKEY";
	public final static String USER_AVAILABLE_MENUS = "USER_AVAILABLE_MENUS";
	public final static String MENU_DOC_VITALES = "MenuDocumentosVitales";
	public final static String MENU_ADM_DOC_VITALES = "MenuAdministracionDocVitales";
	public final static String MENU_BUSQUEDA_AVANZADA = "MenuBusquedaAvanzada";
	public final static String MENU_CAMBIO_GESTOR_INGRESO_DIRECTO = "MenuCambioGestorIngresoDirecto";
	public final static String MENU_DESCRIPCION_ARCHIVISTICA = "MenuDescripcionFondo";
	public final static String MENU_FONDOS_INGRESO_DIRECTO = "MenuFondosIngresoDirecto";
	public final static String MENU_ADMINISTRACION_ORGANIZACION = "MenuOrganizacion";

	public final static String MENU_ARCHIVO = "MenuArchivo";
	public static final String ERROR_NEGATIVE_NUMBER = "errors.negativeNumber";
	public static final String ERROR_CODIGO_SERIE_DUPLICADO = "errors.codigoSerieDuplicado";
	public static final String ERROR_CODIGO_CLFSERIE_DUPLICADO = "errors.codigoClasificadorSerieDuplicado";
	public static final String ERROR_CLF_SERIES_NO_VACIO = "errors.clasificadorSeriesNoVacio";
	public static final String ERROR_CLF_SERIES_HAS_ACTIVE_CHILDS = "errors.clasificadorSeriesConHijosVigentes";
	public static final String ERROR_VALOR_DEBE_SER_MAYOR_QUE_VALOR= "archigest.archivo.valorDebeSerMayorIgualQueValor";
	public static final String ERROR_PREVISION_FUERA_PLAZO = "errors.relaciones.previsionFueraPlazo";
	public static final String ERROR_RELACIONES_ENTRE_ARCHIVOS_UNIDADES_BLOQUEADAS = "errors.relaciones.entre.archivos.unidades.bloqueadas";
	public static final String ERROR_CAMPO_DEMASIADO_LARGO="errors.campo.demasiadoLargo";
	public static final String ERROR_RELACION_CON_UNIDADES_INCOMPLETAS = "archigest.archivo.transferencias.no.enviar.udocs.incompletas";
	// public static final String ERROR_FONDO_HAS_UDOCS =
	// "errors.fondoContieneUnidadesDocumentales";

	public static final String ERROR_RELACION_UNIDADES_A_ELIMINAR = "archigest.archivo.transferencias.no.enviar.udocs.a.eliminar";
	public static final String UDOCS_NO_ASIGNADAS = "errors.relaciones.ImposibleEnviarRelacionUdocSinCaja";
	public static final String ERROR_NO_SEARCH_TOKEN = "errors.noSearchToken";
	public static final String ESPACIO_EN_UBICACION_INSUFICIENTE = "error.espacioInsuficiente";
	public final static String INVOCATION_STACK_KEY = "sigia.view.INVOCATION_STACK";
	public final static String HOME_URL = "/action/homepage?method=verBandeja";
	public final static String CODIGO_ES_NECESARIO = "errors.fondos.codigoEsNecesario";
	public final static String TITULO_ES_NECESARIO = "errors.fondos.tituloEsNecesario";

	/**
	 * Clave del fichero de propiedades que contiene el texto de de Denominación
	 * es Necesario<br>
	 * <b>Valor: </b>errors.fondos.denominacionEsNecesario<br>
	 * <b>Texto: </b> Es necesario introducir una Denominación</b>
	 */
	public final static String DENOMINACION_ES_NECESARIO = "errors.fondos.denominacionEsNecesario";
	public final static String ENTIDAD_PRODUCTORA_NECESARIA = "errors.fondos.nombreEsNecesario";
	public final static String TIPO_CLASIFICADOR_ES_NECESARIO = "errors.fondos.tipoClasificadorEsNecesario";
	public static final String NO_EXISTEN_PRODUCTORES = "archigest.archivo.series.noExistenProductores";
	public static final String NO_EXISTEN_PRODUCTORES_VIGENTES = "archigest.archivo.series.noExistenProductoresVigentes";
	public static final String PRODUCTOR_EXISTE_EN_HISTORICOS = "archigest.archivo.series.productorExisteEnHistoricos";
	public static final String NO_EXISTEN_ORGANOS = "archigest.archivo.series.noExistenOrganos";
	public static final String NO_EXISTE_PROCEDIMIENTO = "archigest.archivo.series.noExisteProcedimiento";
	public static final String MAS_DE_UN_PRODUCTOR = "archigest.archivo.series.masDeUnProductor";

	public static final String PROCEDIMIENTO_SIN_PRODUCTORES = "archigest.archivo.series.procedimientoSinProductores";
	public static final String MISMO_PRODUCTOR_ACTUAL = "archigest.archivo.series.mismoProductorActual";
	public static final String ERROR_NIF_CIF_INCORRECTO = "errors.nif.cif.incorrecto.letra";

	/**
	 * Clave del fichero de propiedades que contiene el texto de que el tipo de Previsión es Obligatorio<br>
	 * <b>Valor: </b>archigest.archivo.transferencias.selTipoPrevision<br>
	 * <b>Texto: </b>Seleccione el Tipo de Previsión a crear
	 */
	public static final String ERROR_TIPO_PREVISION_OBLIGATORIO = "archigest.archivo.transferencias.selTipoPrevision";


	/**
	 * Clave del fichero de propiedades que contiene el texto de caracteres inválidos<br>
	 * <b>Valor: </b>archigest.archivo.transferencias.selTipoPrevision<br>
	 * <b>Texto: </b>{0} contiene caracteres no permitidos {1}
	 * @param Nombre del Campo
	 */
	public static final String ERROR_INVALID_CHARACTERS = "errors.invalid.characters";

	/**
	 * Clave del fichero de propiedades que contiene el texto que no hay huecos desde la ubicacion seleciconada<br>
	 * <b>Valor: </b>{@value}<br>
	 * <b>Texto: </b>No existen {0} huecos desde ubicación seleccionada. Solo hay {1} huecos disponibles. Pruebe a seleccionar una ubicación anterior.
	 * @param Número de Huecos Necesarios
	 * @param Numero de Huecos Disponibles
	 */
	public static final String ERROR_NO_HUECOS_DISPONIBLES_DESDE_POSICION = "archigest.archivo.deposito.noHuecos.desde.posicion";

	/***
	 * Clave en el fichero de propiedades que contiene el texto del error que indica que se ha intentado añadir números de expediente duplicados
	 * a la fracción de serie
	 */
	public static final String ERROR_EXPEDIENTE_DUPLICADO_EN_FRACCION_SERIE = 	"error.expediente_duplicado.enFraccionSerie";
	public static final String ERROR_INITDATE_AFTER_ENDDATE_FRACCION_SERIE =  	"error.init-end_datesUDocFraccionSerie";
	public static final String ERROR_DATE_AFTER_TODAY_FRACCION_SERIE = 			"error.date-after-todayUDocFraccionSerie";
	public static final String ERROR_NO_SE_PUEDE_ELIMINAR_EL_ARCHIVO_NAME	=	"error.archivos.eliminacion.no.permitida";
	public static final String ERROR_NIVEL_REQUERIDO = "errors.divisionfs.necesarioSeleccionarNivel";

	/**
	 * Clave del fichero de propiedades que contiene el texto de que no se puede eliminar un fichero<br>
	 * <b>Valor: </b>error.niveles.archivos.eliminacion.no.permitida<br>
	 * <b>Texto: </b>No se puede eliminar el nivel de archivo seleccionado. Hay archivos que tienen este nivel
	 */
	public static final String ERROR_NO_SE_PUEDE_ELIMINAR_NIVEL				=	"error.niveles.archivos.eliminacion.no.permitida";

	/**
	 * Clave del fichero de propiedades que contiene el texto de que se encuentran unidades documentales para las condiciones de la transferencia entre archivo<br>
	 * <b>Valor: </b>archigest.archivo.transferencias.no.ui.entre.archivos<br>
	 * <b>Texto: </b>No hay unidades de instalación que cumplan las condiciones indicadas.
	 */
	public static final String ERROR_NO_UI_TRANSFERENCIAS_ENTRE_ARCHIVOS	=	"archigest.archivo.transferencias.no.ui.entre.archivos";

	/**
	 * Clave del fichero de propiedades que contiene el texto de que un campo determinado de escritura no puede contener un caracter determinado<br>
	 * <b>Valor: </b>errors.archivo.creacion.caracterEnCampoNoPermitido<br>
	 * <b>Texto: </b>El campo {0} no puede contener el caracter {1}
	 * @param Nombre del campo
	 * @param Carácter no permitido
	 */
	public static final String ERROR_CARACTER_EN_CAMPO_NO_PERMITIDO			=	"errors.archivo.creacion.caracterEnCampoNoPermitido";

	/**
	 * Clave del fichero de propiedades que contiene el texto de que ya existe un código determinado asociado a un archivo
	 * <b>Valor: </b>errors.codigoArchivoDuplicado<br>
	 * <b>Texto: </b>El c\u00f3digo ya ha sido asignado a otro archivo
	 */
	public static final String ERROR_CODIGO_ARCHIVO_DUPLICADO				=   "errors.codigoArchivoDuplicado";

	/**
	 * Clave del fichero de propiedades que contiene el texto de que ya existe un nombre determinado asociado a un nivel de archivo
	 * <b>Valor: </b>errors.nombreNivelArchivoDuplicado<br>
	 * <b>Texto: </b>El nombre ya ha sido asignado a otro nivel de archivo
	 */
	public static final String ERROR_NOMBRE_NIVEL_ARCHIVO_DUPLICADO				=   "errors.nombreNivelArchivoDuplicado";

	/**
	 * Si tenemos SIGNATURACION_POR_ARCHIVO ='N'. Indica que no podemos insertar archivos cuyo tipo
	 * de signaturación sea distinta a los archivos ya existentes.
	 */
	public static final String ERROR_TIPO_SIGNATURACION_ARCHIVO_NO_PERMITIDO	=   "errors.tipoSignaturacionArchivoNoPermitido";

	/**
	 * Clave del fichero de propiedades que contiene el texto de que un registro está duplicado
	 * @param 0 Nombre del producto duplicado
	 * <b>Valor: </b>errors.registro.duplicado<br>
	 * <b>Texto: </b>{0} Duplicado
	 */
	public static final String ERROR_REGISTRO_DUPLICADO							 = "errors.registro.duplicado";

	/**
	 * Clave del fichero de propiedades que contiene el texto de que no se pueden añadir cajas que tienen unidades en préstamos
	 * <b>Valor: </b>archigest.archivo.no.permitir.cajas.con.udocs.prestadas<br>
	 * <b>Texto: </b>No se han podido añadir las cajas que contienen unidades documentales que están prestadas. A continuación se detallan las unidades:
	 */
	public static final String ERROR_NO_PERMITIR_ADD_UDOCS_PRESTADAS			 = "archigest.archivo.no.permitir.cajas.con.udocs.en.prestamos";

	/**
	 * Clave del fichero de propiedades que contiene el texto de que la caja tiene unidades en préstamos
	 * @param Signatura Unidad de Instalación
	 * @param Siganturas de Unidades Documentales.
	 * <b>Valor: </b>archigest.archivo.no.permitir.cajas.con.udocs.prestadas<br>
	 * <b>Texto: </b>&nbsp;&nbsp;-{0} tiene las unidades documentales {1} en Pr\u00e9stamos.
	 */
	public static final String ERROR_CAJA_CON_UDOCS_PRESTADAS					= "archigest.archivo.caja.con.udocs.en.prestamos";

	/**
	 * Clave del fichero de propiedades que contiene el texto de que la unidad documental en préstamos
	 * @param Signatura Unidad de la Unidad Documental
	 * @param Estado de la Unidad de Instalación
	 * <b>Valor: </b>archigest.archivo.unidad.documental.en.prestamos<br>
	 * <b>Texto: </b>&nbsp;&nbsp;&nbsp;&nbsp;-{0}{1}
	 */
	public static final String ERROR_UDOC_EN_PRESTAMOS							= "archigest.archivo.unidad.documental.en.prestamos";

	public static final String ERROR_PRESTAMO_SIN_PRORROGAS_EN_ESTADO 		= "archigest.archivo.prestamo.sin.prorrogas.solicitadas.error";

	public static final String ERROR_SERIE_CODIGO_REPETIDO_ENFONDO				= "archigest.error.serie.codigoRepetidoEnFondo";
	public static final String ERROR_CREAR_ELEMENTO_YA_EXISTE					= "errors.crear.elemento.ya.existe";
	public static final String LOGIN_USER_INVALIDATED							="login.user.invalidated";
	public static final String LOGIN_APPLICATION_NUM_MAX_INTENTOS				="login.application.numMaxIntentos";
	public static final String LOGIN_APPLICATION_ERROR_COD_UNICO_SIG_ARCHIVOS	="login.application.errorCodigoUnicoNSignaturaXArchivos";
	public static final String LOGIN_APPLICATION_ERROR_FICHERO_MAP_FS_UDOC		="login.application.errorFicheroMapFSUDoc";
	public static final String LOGIN_ERROR_USER_INTERNO_SIN_ORGANO				="archigest.login.error.usuarioInterno.sinOrganoValido";

	public static final String LOGIN_USER_CONNECTED								="login.user.connected";
	public static final String LOGIN_APPLICATION_NOT_FOUND						="login.application.notfound";
	public static final String LOGIN_USER_TYPE_NOT_FOUND						="login.user.type.notfound";
	public static final String LOGIN_APPLICATION_NOT_ACTIVATED					="login.application.notactivated";
	public static final String LOGIN_APPLICATION_EXPIRED						="login.application.expired";
	public static final String LOGIN_APPLICATION_NO_PERMISSIONS					="login.application.nopermissions";
	public static final String LOGIN_APPLICATION_ORGANIZATION_ERROR				="login.application.organizationerror";
	public static final String LOGIN_APPLICATION_USER_LOCKED					="login.application.user.locked";

	public static final String LOGIN_MULTIENTITY_APPLICATION_SESSION_ERROR		="archigest.multientity.error.session";
	public static final String LOGIN_APPLICATION_DATABASE_ERROR					="archigest.login.error.database";
	public static final String LOGIN_APPLICATION_ADMINISTRATOR_TYPE_ERROR		="archigest.login.error.user.type.administrator";
	public static final String GLOBAL_ARCHIGEST_EXCEPTION						="global.archigestexception";
	public static final String DOCVITALES_DOCVITAL_ERROR_EXISTENTE_MSG			="archigest.archivo.docvitales.docVital.error.existente.msg";
	public static final String ERRORS_FONDOS_USUARIO_NO_GESTOR_SERIE			="errors.fondos.usuarioNoGestorSerie";
	public static final String ERRORS_FONDOS_PRODUCTOR_NO_ORGANIZACION			="errors.fondos.productorNoOrganizacion";
	public static final String ERRORS_FONDOS_CS_NO_POSIBLE_ELIMINAR_X_HIJOS_VIGENTES	="errors.fondos.CSnoPosibleEliminarXHijosVigentes";
	public static final String ERRORS_FONDOS_CF_NO_POSIBLE_ELIMINAR_X_HIJOS		="errors.fondos.CFnoPosibleEliminarXHijos";
	public static final String ERRORS_FONDOS_CS_NO_POSIBLE_ELIMINAR_X_TENER_SERIES		="errors.fondos.CSnoPosibleEliminarXtenerSeries";
	public static final String ERRORS_FONDOS_CS_NO_PUEDE_TENER_FONDOS			="errors.fondos.CSnoPuedeTenerFondos";

	public static final String ERRORS_FONDOS_CODIGO_ENTIDAD_YA_EXISTE			="errors.fondos.codigoEntidadYaExiste";
	public static final String ERRORS_FONDOS_NOMBRE_ENTIDAD_YA_EXISTE			="errors.fondos.NombreEntidadYaExiste";
	public static final String ERRORS_FONDOS_CODSERIE_YA_EXISTE					="errors.fondos.CodSerieYaExiste";
	public static final String ERRORS_FONDOS_CODELEMENTO_YA_EXISTE				="errors.fondos.CodElementoYaExiste";
	public static final String ERRORS_FONDOS_ELEMENTO_NO_VACIO					="errors.fondos.elementoNoVacio";
	public static final String ERRORS_FONDOS_ELIMINACION_X_ORGANOS_ASOCIADOS	="errors.fondos.FnoSePuedeEliminarXOrganosAsociados";
	public static final String ERRORS_FONDOS_ELIMINACION_X_CL_SERIES_VIGENTES	="";
	public static final String ERRORS_FONDOS_ELIMINACION_X_SERIES_DESCENDIENTES	="errors.fondos.FnoSePuedeEliminarXSeriesDescendientes";
	public static final String ERRORS_FONDOS_MOVER_X_TENER_UDOCS				="errors.fondos.FnoSePuedeMoverXTenerUDocs";
	public static final String ERRORS_FONDOS_OPERACION_NOPERMITIDA_X_ESTADO		="errors.fondos.operacionNoPermitidaXestado";

	public static final String ERRORS_FONDOS_OPERACION_NOPERMITIDA_X_VALORACION_DICTAMINADA		="errors.fondos.operacionNoPermitidaXValoracionDictaminada";
	public static final String ERRORS_FONDOS_CAMBIAR_ESTADO_X_HIJOS_VIGENTES	="errors.fondos.imposibleCambiarEstadoXHijosVigentes";
	public static final String ERRORS_FONDOS_CAMBIAR_ESTADO_X_SERIES_VIGENTES	="errors.fondos.imposibleCambiarEstadoXSeriesVigentes";
	public static final String ERRORS_FONDOS_MOVER_X_MISMO_COD_EN_DESTINO		="errors.fondos.imposibleMoverXmismoCodEnDestino";
	public static final String ERRORS_FONDOS_MOVER_X_SERIES_DESCENDIENTES		="errors.fondos.imposibleMoverXSeriesDescendientes";
	public static final String ERRORS_FONDOS_MOVER_X_DESTINO_NO_VIGENTE			="errors.fondos.imposibleMoverXDestinoNoVigente";
	public static final String ERRORS_FONDOS_CAMBIO_X_NO_SER_GESTOR				="errors.fondos.imposibleCambiarXNoSerGestor";
	public static final String ERRORS_FONDOS_CAMBIAR_ESTADO_X_PADRE_NO_VIGENTE	="errors.fondos.imposibleCambiarEstadoXPadreNoVigente";
	public static final String ERRORS_FONDOS_CAMBIAR_ESTADO_SERIE_X_FALTAR_TITULO		="errors.fondos.imposibleCambiarEstadoSerieXFaltarTitulo";
	public static final String ERRORS_FONDOS_CAMBIAR_ESTADO_SERIE_X_FALTAR_F_INICIAL	="errors.fondos.imposibleCambiarEstadoSerieXFaltarFIni";

	public static final String ERRORS_FONDOS_CAMBIAR_ESTADO_SERIE_X_FALTAR_DEFINICION	="errors.fondos.imposibleCambiarEstadoSerieXFaltarDefinicion";
	public static final String ERRORS_FONDOS_CAMBIAR_ESTADO_SERIE_X_FALTAR_TRAMITES		="errors.fondos.imposibleCambiarEstadoSerieXFaltarTramites";
	public static final String ERRORS_FONDOS_CAMBIAR_ESTADO_SERIE_X_FALTAR_NORMATIVA	="errors.fondos.imposibleCambiarEstadoSerieXFaltarNormativa";
	public static final String ERRORS_FONDOS_CAMBIAR_ESTADO_SERIE_X_NO_IDENTIFICADA		="errors.fondos.imposibleCambiarEstadoSerieXNoIdentificada";
	public static final String ERRORS_FONDOS_CAMBIAR_ESTADO_SERIE_X_NO_PRODUCTORES		="errors.fondos.imposibleCambiarEstadoSerieXNoProductores";
	public static final String ERRORS_FONDOS_ASOCIAR_PROCEDIMIENTO_SERIE_X_NO_PRODUCTORES		="errors.fondos.imposibleAsociarProcedimientoSerieXNoProductores";
	public static final String ERRORS_FONDOS_CAMBIAR_ESTADO_CLASIF_X_DESCENDIENTES		="errors.fondos.imposibleCambiarEstadoClasifXDescendientes";
	public static final String ERRORS_FONDOS_PASO_A_VIGENTE_SIN_DESCRIBIR		="errors.fondos.pasoAVigente.sinDescribir";
	public static final String ERRORS_FONDOS_CREAR_X_EXISTE_OTRA_VALORACION		="errors.fondos.imposibleCrearXExisteOtraValoracion";
	public static final String ERRORS_FONDOS_BORRAR_X_CLASIFS_SERIES_VIGENTES	="errors.fondos.FnoSePuedeEliminarXclasifSeriesVigentes";
	public static final String ERRORS_FONDOS_FRACCION_SERIE_NO_DIVISIBLE_XPRESTAMO_CONSULTA = "errors.fondos.FraccionSerieNoDivisibleXPrestamoConsulta";

	public static final String ERRORS_TRANSF_NO_POSIBLE_TRANSFERIR				="errors.transferencias.noPosibleTransferir";
	public static final String ERRORS_TRANSF_NO_POSIBLE_TRANSFERIR_ARCHIVO		="errors.transferencias.noPosibleTransferirArchivo";
	public static final String ERRORS_TRANSF_NOT_ALLOWED_IN_ORDINARIAS			="errors.transferencias.noPermitidoEnOrdinarias";
	public static final String ERRORS_RELAC_ENVIAR_X_UDOCS_FISICOS_FUERA_CAJA	="errors.relaciones.ImposibleEnviarXRelacionConUdocFisicosFueraDeCaja";
	public static final String ERRORS_PREVISIONES_NO_SELECTED					="errors.previsiones.noSelected";
	public static final String ERRORS_RELACIONES_NO_SELECTED					="errors.relaciones.noSelected";
	public static final String ERRORS_TRANSF_GESTOR_NO_ENCONTRADO				="errors.transferencias.gestorNoEncontrado";
	public static final String ERRORS_PREVISIONES_GESTOR_CON_OTRA_PREVISION		="errors.previsiones.gestorConOtraPrevision";
	public static final String ERRORS_PREVISIONES_ORGANO_GESTOR_OTRA_PREVISION		="errors.previsiones.organoGestorOtraPrevision";

	public static final String ERRORS_PREVISIONES_USUARIO_GESTOR_OTRA_PREVISION_ENTRE_ARCH		="errors.previsiones.usuarioGestorOtraPrevisionEntreArchivos";
	public static final String ERRORS_PREVISIONES_OTRO_DETALLE_MISMO_PROCEDIMIENTO	="errors.previsiones.otroDetalleMismoProcedimiento";
	public static final String ERRORS_PREVISIONES_OTRO_DETALLE_MISMA_SERIE		="errors.previsiones.otroDetalleMismaSerie";
	public static final String ERRORS_RELACIONES_SUBIR_FIRST_UDOC				="errors.relaciones.noPosibleSubirFirstUDoc";
	public static final String ERRORS_RELACIONES_BAJAR_LAST_UDOC				="errors.relaciones.noPosibleBajarLastUDoc";
	public static final String ERRORS_RELACIONES_MOVER_UDOCS_NO_CONSECUTIVAS	="errors.relaciones.noPosibleMoverUDocsNoConsecutivas";
	public static final String ERRORS_RELACIONES_DIVIDIR_FORMATO				="errors.relaciones.noPosibleDividorFormato";
	public static final String ERRORS_RELACIONES_SOLO_ELIMINAR_LAST_PARTE		="errors.relaciones.soloEliminarLastParte";
	public static final String ERRORS_RELACIONES_SOLO_DIVIDIR_LAST_UDOC			="errors.relaciones.soloDividirLastUdoc";
	public static final String ERRORS_RELACIONES_SOLO_DIVIDIR_LAST_PARTE		="errors.relaciones.soloDividirLastParte";
	public static final String ERRORS_RELACIONES_SACAR_SOLO_UDOCS_ERRONEAS		="errors.relaciones.sacarSoloUDocsErroneas";
	public static final String ERRORS_RELACIONES_METER_UDOCS_CAJA_CON_PARTE		="errors.relaciones.imposibleMeterUdocsCajaConParte";
	public static final String ERRORS_RELACIONES_SOLO_EXTRAER_UDOCS_COMPLETAS	="errors.relaciones.soloExtraerUdocsCompletas";

	public static final String ERRORS_RELACIONES_SOLO_ELIMINAR_CAJAS_VACIAS		="error.relaciones.soloEliminarCajasVacias";
	public static final String ERRORS_RELACIONES_SIGNATURA_REPETIDA				="errors.relaciones.signaturaRepetida";
	public static final String ERRORS_RELACIONES_HUECO_ASOCIADO_SIGNATURA_NO_EXISTE_EN_ARCHIVO				="errors.relaciones.hueco.asociado.signatura.no.existe.en.archivo";
	public static final String ERRORS_RELACIONES_LA_CAJA_A_ASIGNAR_CONTIENE_UNIDADES_CON_DISTINTO_FONDO = "errors.relaciones.la.caja.a.asignar.contiene.unidades.con.distinto.fondo";
	public static final String ERRORS_RELACIONES_NO_ES_POSIBLE_ASIGNAR_CAJA_POR_ESTAR_BLOQUEADA = "errors.relaciones.no.es.posible.asignar.caja.por.estar.bloqueada";
	public static final String ERRORS_RELACIONES_HUECO_ASOCIADO_A_LA_SIGNATURA_DEL_ARCHIVO_ESTA_UTILIZADO		="errors.relaciones.hueco.asociado.signatura.del.archivo.esta.utilizado";
	public static final String ERROR_RELACIONES_HUECO_ASOCIADO_A_LA_SIGNATURA_TIENE_DISTINTO_FORMATO_RELACION ="errors.relaciones.hueco.asociado.signatura.tiene.distinto.formato.relacion";
	public static final String ERROR_RELACIONES_HUECO_ASOCIADO_A_LA_SIGNATURA_TIENE_DISTINTA_UBICACION_RELACION ="errors.relaciones.hueco.asociado.signatura.tiene.distinta.ubicacion.relacion";
	public static final String ERROR_RELACIONES_SIGNATURA_ASOCIADA_HUECO_UTILIZADA_EN_RELACION="errors.relaciones.signatura.asociada.hueco.utilizada.en.relacion";
	public static final String ERROR_RELACIONES_HUECOS_ASOCIADOS_RELACION_YA_UTILIZADOS = "errors.relaciones.huecos.asociados.relacion.ya.utilizados";
	public static final String ERRORS_RELACIONES_SOLO_ELIMINAR_UDOCS_ERRONEAS	="errors.relaciones.soloEliminarUDocsErroneas";
	public static final String ERRORS_RELACIONES_NO_PERMITIR_BLOQUEAR_UDOCS_BLOQUEADAS	="errors.relaciones.noPermitirBloquearUdocBloqueadas";
	public static final String ERRORS_RELACIONES_NO_PERMITIR_DESBLOQUEAR_UDOCS_DESBLOQUEADAS	="errors.relaciones.noPermitirDesbloquearUdocsDesbloqueadas";
	public static final String ERRORS_RELACIONES_FALTAN_SIGNATURAS_CAJAS		="errors.relaciones.fatanSignaturasCajas";
	public static final String ERRORS_RELACIONES_FINALIZAR_CORRECCION_FALTAN_SIGNATURAS_CAJAS ="errors.relaciones.correccion.faltanSignaturasCajas";
	public static final String ERRORS_RELACIONES_ONLY_ALLOW_TO_GESTOR			="errors.relaciones.soloPermitidoAlGestor";
	public static final String ERRORS_RELACIONES_ESTADO_INCORRECTO				="errors.relaciones.relacionEstadoIncorrecto";
	public static final String ERRORS_RELACIONES_ONLY_REMOVE_WRONG_UDOCS_PARTS	="errors.relaciones.soloEliminarPartesUDocsErroneas";
	public static final String ERRORS_RELACIONES_FIN_COTEJO_SIN_DEVOLUCION_FISICA	="errors.relaciones.noPosibleFinCotejoTOCajasSinDevolucionFisica";
	public static final String ERRORS_RELACIONES_FIN_COTEJO_X_UDOCS_PENDIENTES 	="errors.relaciones.noPosibleFinCotejoXUDocsPendientes";
	public static final String ERRORS_RELACIONES_NOT_ALLOWED_X_OTRA_REL_SAME_PREV_Y_SERIE 	="errors.relaciones.operacionNoPermitidaXOtraRelacionMismaPrevisionMismaSerie";
	public static final String ERRORS_RELACIONES_PARTE_CON_ERRORES_TODAS_CON_ERRORES = "archigest.archivo.error.ordinarias.con.partes.con.errores.incompletas";
	public static final String X_EL_USUARIO_NO_TIENE_PERMISOS_REQUERIDOS_VALIDACION	= "errors.relaciones.operacionNoPermitidaXUsuarioNoTienePermisosValidacion";
	public static final String ERRORS_TRANSFERENCIAS_SIN_ARCHIVO="errors.transferencias.sinArchivo.noPosibleTransferir";
	public static final String ERRORS_RELACIONES_UI_NO_MULTIDOCUMENTO				="errors.relaciones.uisinmultidocumento";
	public static final String ERRORS_RELACIONES_SIGNATURAUI_NO_EXISTE				="errors.relaciones.signaturauiNoExiste";
	public static final String ERRORS_RELACIONES_DIVIDIR_SIGNATURAUI_NO_PERMITIDA	="errors.relaciones.dividir.signaturaUINoPermitida";
	public static final String ERRORS_FORMATO_NO_VALIDO								="errors.relaciones.formatoNoValido";
	public static final String ERRORS_RELAC_NO_MULTIDOC_CON_UIS_VARIOS_UDOCS		="errors.relaciones.RelacionNoMultidocConUIsConVariosUdocs";
	public static final String ERRORS_RELAC_NO_MULTIDOC_CON_UIS_VARIAS_FS			="errors.relaciones.RelacionNoMultidocConUIsConVariasFS";
	public static final String ERRORS_RELAC_NO_FINALIZA_SIN_UDOCS					="errors.relaciones.RelacionNoFinalizaSinUdocs";

	public static final String ERRORS_GCONTROL_NUEVO_USUARIO_YA_EXISTE 				="errors.gcontrol.nuevoUsuario.yaExiste";
	public static final String ERRORS_GCONTROL_NUEVO_USUARIO_ORGANO_NO_EN_SISTEMA	="errors.gcontrol.nuevoUsuario.organoNoEnSistema";
	public static final String ERRORS_GCONTROL_LISTADO_ACCESO_ELIMINAR_X_ELEMENTOS_CF_ASOCIADOS	="errors.gcontrol.listaAcceso.imposibleEliminarElementosCFAsociados";
	public static final String ERRORS_GCONTROL_LISTADO_ACCESO_ELIMINAR_X_DESCRIPTORES_ASOCIADOS	="errors.gcontrol.listaAcceso.imposibleEliminarDescriptoresAsociados";
	public static final String ERRORS_GCONTROL_LISTADO_ACCESO_ELIMINAR_X_FORMS_FICHA_ASOCIADOS	="errors.gcontrol.listaAcceso.imposibleEliminarFormsFichaAsociados";
	public static final String ERRORS_GCONTROL_LISTADO_ACCESO_USER_WITH_PREVS_EN_ELABORACION	="errors.gcontrol.listaAcceso.usuarioConPrevisionesEnElaboracion";
	public static final String ERRORS_GCONTROL_LISTADO_ACCESO_USER_WITH_RELS_EN_ELABORACION	="errors.gcontrol.listaAcceso.usuarioConRelacionesEnElaboracion";
	public static final String ERRORS_GCONTROL_LISTADO_ACCESO_USER_WITH_RELS ="errors.gcontrol.listaAcceso.usuarioConRelaciones";
	public static final String ERRORS_GCONTROL_LISTADO_ACCESO_USER_ES_GESTOR_SERIES		="errors.gcontrol.listaAcceso.usuarioEsGestorSeries";
	public static final String ERRORS_GCONTROL_LISTADO_ACCESO_USER_CON_PRESTAMOS_EN_CURSO	="errors.gcontrol.listaAcceso.usuarioConPrestamosEnCurso";
	public static final String ERRORS_GCONTROL_LISTADO_ACCESO_USER_CON_CONSULTAS_EN_CURSO	="errors.gcontrol.listaAcceso.usuarioConConsultasEnCurso";
	public static final String ERRORS_GCONTROL_NEW_PREV_ENTRE_ARCH_USER_NOT_BELONG_TO_ANY_ARCH	="errors.gcontrol.nuevaPrevisionEntreArchivos.usuarioNoPerteneceANingunArchivo";
	public static final String ERRORS_GCONTROL_USUARIO_TIENE_ASOCIADO_USUARIO_SALA_CONSULTA = "errors.gcontrol.usuario.asociado.a.usuario.consulta";

	public static final String ERRORS_EDOCS_CREAR_TAREA							="errors.documentosElectronicos.imposibleCrearTarea";
	public static final String ERRORS_EDOCS_CREAR_TAREA_DESCRIPTOR				="errors.documentosElectronicos.imposibleCrearTareaDescriptor";
	public static final String ERRORS_EDOCS_CREAR_TAREA_NIVEL_CUADRO			="errors.documentosElectronicos.imposibleCrearTareaNivelCuadro";
	public static final String ERRORS_EDOCS_ONLY_END_TAREAS_PENDIENTES_O_CON_ERRORES	="errors.documentosElectronicos.soloFinalizarTareasPendienteOConErrores";
	public static final String ERRORS_EDOCS_END_TAREA_ERRONEA_X_CL_NO_VALIDO	="errors.documentosElectronicos.ImposibleFinalizarTareaErronaXClasifNoValido";
	public static final String ERRORS_EDOCS_END_TAREA_ERRONEA_X_DOC_NO_VALIDO	="errors.documentosElectronicos.ImposibleFinalizarTareaErronaXDocNoValido";
	public static final String ERRORS_EDOCS_NO_PERMISOS_EN_TAREA				="errors.documentosElectronicos.noPermisosEnTarea";
	public static final String ERRORS_EDOCS_ELEMENTOS_SIN_VALIDAR				="errors.documentosElectronicos.elementosSinValidar";
	public static final String ERRORS_EDOCS_SOLO_ELIMINAR_TAREAS_PENDIENTES		="errors.documentosElectronicos.soloEliminarTareasPendientes";

	public static final String ERRORS_EDOCS_CAMBIAR_ESTADO_A_PUBLICADOS			="errors.documentosElectronicos.imposibleCambiarEstadoAPublicados";
	public static final String ERRORS_EDOCS_SOLO_VALIDAR_TAREA_FINALIZADA		="errors.documentosElectronicos.soloValidarTareaFinalizada";
	public static final String ERRORS_EDOCS_SOLO_END_VALIDACION_TAREA_FINALIZADA="errors.documentosElectronicos.soloFinalizarValidacionTareaFinalizada";
	public static final String ERRORS_EDOCS_NO_MODIFICAR_TAREA_FINALIZADA		="errors.documentosElectronicos.noModificarTareaFinalizada";
	public static final String ERRORS_EDOCS_ELEMENTO_CON_CAPTURA_NO_VALIDADA	="errors.documentosElectronicos.elementoConCapturaNoValidada";
	public static final String ERRORS_EDOCS_USUARIOS_SIN_PERMISOS_OBJETO_TAREA	="errors.documentosElectronicos.usuarioSinPermisosObjetoTarea";
	public static final String ERRORS_ARCHS_ELIMINACION_NOT_ALLOWED_ESTA_EN_FONDOS	="error.archivos.eliminacion.no.permitida.esta.en.fondos";
	public static final String ERRORS_ARCHS_ELIMINACION_NOT_ALLOWED_ESTA_EN_UBICACIONES	="error.archivos.eliminacion.no.permitida.esta.en.ubicaciones";
	public static final String ERRORS_ARCHS_ELIMINACION_NOT_ALLOWED_ESTA_EN_EDIFICIOS_SC	="error.archivos.eliminacion.no.permitida.esta.en.edificios.sc";
	public static final String ERRORS_ARCHS_ELIMINACION_NOT_ALLOWED_ESTA_EN_CONSULTAS	="error.archivos.eliminacion.no.permitida.esta.en.consultas";
	public static final String ERRORS_NO_MULTIVALOR 							= "errors.no_multivalor";

	public static final String ERROR_BUSQUEDA_AVANZADA_VALOR_OBLIGATORIO		="archigest.archivo.busqueda.form.valor.obligatorio.condiciones.avanzadas";
	public static final String ERROR_BUSQUEDA_AVANZADA_VALOR_OBLIGATORIO_SOLOUNO="archigest.archivo.busqueda.form.valor.obligatorio.condicion.avanzada";
	public static final String ERROR_NO_EXISTEN_ARCHIVOS						="archigest.archivo.noExistenArchivos";
	public static final String ERROR_REEEMPLAZO_VALOR_NUEVO_OBLIGATORIO			="archigest.descripcion.reemplazo.nuevoValor.obligatorio";
	public static final String ERROR_REEEMPLAZO_VALOR_NUEVO						="archigest.descripcion.reemplazo.valor.nuevo";
	public static final String ERROR_REEEMPLAZO_VALOR_NUMERICO_FUERA_RANGO		="errors.archivo.numero.fuera.rango";
	public static final String ERROR_RELACION_SOLO_DOCS_ELECTRONICOS			="archigest.archivo.transferencias.relacion.solo.docselectronicos";

	public static final String ERROR_FICHA_PERMISOS_INSERCION_DESCRIPTOR		="archigest.archivo.error.ficha.permisos.descriptor";
	public static final String ERROR_EXPORTAR_BUSQUEDA							="archigest.error.exportar.busqueda";
	public static final String ERROR_MOVER_NIVELES_VALIDACIONES_CONFLICTIVAS	="archigest.niveles.mover.error.valoraciones.conflictivas";
	public static final String ERROR_ELIMINAR_NIVELES_VALIDACIONES_CONFLICTIVAS	="archigest.niveles.eliminar.error.valoraciones.conflictivas";
	public static final String ERRORS_MOVER_UDOCS_CAJA_DISTINTOS_FONDOS			="errors.fondos.moverUdocs.cajasDepositos.distintosFondos";

	public static final String ERRORS_ESTADO_UBICACION_INCORRECTO				="errors.relacion.estado.ubicacion.incorrecto";
	public static final String ERRORS_ESTADO_VALIDACION_INCORRECTO				="errors.relacion.estado.validacion.incorrecto";

	public static final String ERRORS_IDENTIFICACION_PRODUCTORES				= "archigest.archivo.series.identificacion.productores";


	/**
	 * Clave de la etiqueta que contiene el texto de la etiqueta <b>Registro existente</b><br>
	 * <b>Valor: </b>errors.registro.existente<br>
	 * <b>Texto: </b>Existe un elemento '{0}' con '{1}'='{2}'<br>
	 * @param 0 - Tipo de elemento
	 * @param 1 - Clave
	 * @param 2 - Valor
	 */
	public static final String EXISTE_ELEMENTO_DUPLICADO = "errors.elemento.existente";

	/*-----------------------------------------------------------------------------------------------
	 * ETIQUETAS DE LA APLICACION
	 * ----------------------------------------------------------------------------------------------*/

	/**
	 * Clave del fichero de propiedades que contiene el texto de de ASUNTO<br>
	 * <b>Valor: </b>archigest.archivo.asunto<br>
	 * <b>Texto: </b>Asunto
	 */
	public final static String ETIQUETA_ASUNTO = "archigest.archivo.asunto";
	public final static String ETIQUETA_FECHA = "archigest.archivo.fecha";
	public final static String ETIQUETA_FECHA2 = "archigest.archivo.busqueda.form.fecha";

	/**
	 * Clave del fichero de propiedades que contiene el texto de de FECHA INICIO<br>
	 * <b>Valor: </b>archigest.archivo.eliminacion.fechaInicio<br>
	 * <b>Texto: </b>Fecha Inicio
	 */
	public final static String ETIQUETA_FECHA_INICIAL="archigest.archivo.fechaInicial";
	public final static String ETIQUETA_FECHA_INICIO = "archigest.archivo.eliminacion.fechaInicio";
	public final static String ETIQUETA_TRANSFERENCIAS_FECHA_INICIO="archigest.archivo.transferencias.fIniTransf";
	public final static String ETIQUETA_TRANSFERENCIAS_FECHA_FIN="archigest.archivo.transferencias.fFinTransf";

	/**
	 * Clave del fichero de propiedades que contiene el texto de F.Inicio<br>
	 * <b>Valor: </b>archigest.archivo.fInicio<br>
	 * <b>Texto: </b>F.Inicio
	 */
	public final static String ETIQUETA_FECHA_INICIO_CORTA = "archigest.archivo.fInicio";


	public final static String ETIQUETA_TODOS = "archigest.archivo.todos";

	/**
	 * Clave del fichero de propiedades que contiene el texto de de FECHA FIN<br>
	 * <b>Valor: </b>archigest.archivo.eliminacion.fechaFin<br>
	 * <b>Texto: </b>Fecha Fin
	 */
	public final static String ETIQUETA_FECHA_FIN = "archigest.archivo.eliminacion.fechaFin";
	public final static String ETIQUETA_FECHA_FINAL = "archigest.archivo.fechaFinal";

	/**
	 * Clave del fichero de propiedades que contiene el texto de F.Fin<br>
	 * <b>Valor: </b>archigest.archivo.fFin<br>
	 * <b>Texto: </b>F.Fin
	 */
	public final static String ETIQUETA_FECHA_FIN_CORTA = "archigest.archivo.fFin";

	/**
	 * Clave del fichero de propiedades que contiene el texto de de LONGITUD<br>
	 * <b>Valor: </b>archigest.archivo.medida.longitud<br>
	 * <b>Texto: </b>Longitud
	 */
	public final static String ETIQUETA_LONGITUD = "archigest.archivo.medida.longitud";

	/**
	 * Clave del fichero de propiedades que contiene el texto de 'Motivo Rechazo'<br>
	 * <b>Valor: </b>archigest.archivo.transferencias.motivoRechazo<br>
	 * <b>Texto: </b>Motivo Rechazo
	 */
	public static final String ETIQUETA_MOTIVO_RECHAZO = "archigest.archivo.transferencias.motivoRechazo";

	/**
	 * Clave del fichero de propiedades que contiene el texto de de MUNICIPIO<br>
	 * <b>Valor: </b>archigest.archivo.transferencias.municipio<br>
	 * <b>Texto: </b>Municipio
	 */
	public static final String ETIQUETA_MUNICIPIO = "archigest.archivo.transferencias.municipio";

	/**
	 * Clave de la etiqueta que contiene el texto de la etiqueta NOMBRE<br>
	 * <b>Valor: </b>archigest.archivo.nombre<br>
	 * <b>Texto: </b>Nombre<br>
	 */
	public static final String ETIQUETA_NOMBRE = "archigest.archivo.nombre";

	/**
	 * Clave de la etiqueta que contiene el texto de la etiqueta ETIQUETA <br>
	 * <b>Valor: </b>archigest.archivo.descripcion.camposDato.form.etiquetaXml<br>
	 * <b>Texto: </b>Etiqueta<br>
	 */
	public static final String ETIQUETA_ETIQUETAXML = "archigest.archivo.descripcion.camposDato.form.etiquetaXml";


	/**
	 * Clave de la etiqueta que contiene el texto de la etiqueta ETIQUETA <br>
	 * <b>Valor: </b>archigest.archivo.descripcion.camposDato.form.etiquetaXml<br>
	 * <b>Texto: </b>Etiqueta<br>
	 */
	public static final String ETIQUETA_ETIQUETAXMLFILA = "archigest.archivo.descripcion.camposTabla.form.etiqXmlFila";


	/**
	 * Clave del fichero de propiedades que contiene el texto de GUID<br>
	 * <b>Valor: </b>archigest.archivo.identificador<br>
	 * <b>Texto: </b>GUID
	 */
	public static final String ETIQUETA_GUID = "archigest.archivo.identificador";

	/**
	 * Clave de la etiqueta que contiene el texto de la etiqueta Nombre Corto<br>
	 * <b>Valor: </b>archigest.archivo.nombre.corto<br>
	 * <b>Texto: </b>Nombre Corto<br>
	 */
	public static final String ETIQUETA_NOMBRE_CORTO = "archigest.archivo.nombre.corto";

	/**
	 * Clave de la etiqueta que contiene el texto de la etiqueta NIVEL<br>
	 * <b>Valor: </b>archigest.archivo.nivel<br>
	 * <b>Texto: </b>Nivel<br>
	 */
	public static final String ETIQUETA_NIVEL = "archigest.archivo.nivel";

	/**
	 * Clave de la etiqueta que contiene el texto de la etiqueta EXPEDIENTE<br>
	 * <b>Valor: </b>archigest.archivo.transferencias.expediente<br>
	 * <b>Texto: </b>Expediente<br>
	 */
	public static final String ETIQUETA_EXPEDIENTE = "archigest.archivo.transferencias.expediente";
	public static String ETIQUETA_NOMBREAPELLIDOS = "archigest.archivo.transferencias.nombreApellidos";

	/**
	 * Clave de la etiqueta que contiene el texto de la etiqueta Notas<br>
	 * <b>Valor: </b>archigest.archivo.transferencias.notas<br>
	 * <b>Texto: </b>Notas<br>
	 */
	public static String ETIQUETA_NOTAS			= "archigest.archivo.transferencias.notas";
	public static String ETIQUETA_NUMIDENTIDAD = "archigest.archivo.transferencias.numIdentidad";

	/**
	 * Clave del fichero de propiedades que contiene el texto Nº Unidades Instalación<br>
	 * <b>Valor: </b>archigest.archivo.serie.numUInstalacion<br>
	 * <b>Texto: </b>Nº Unidades Instalación
	 */
	public static final String ETIQUETA_NUM_UNIDADES_INSTALACION = "archigest.archivo.serie.numUInstalacion";

	/**
	 * Clave del fichero de propiedades que contiene la etiqueta Serie<br>
	 * <b>Valor: </b>archigest.archivo.cf.serie<br>
	 * <b>Texto: </b>Serie
	 */
	public static final String ETIQUETA_SERIE = "archigest.archivo.cf.serie";

	/**
	 * Clave del fichero de propiedades que contiene la etiqueta Serie Origen<br>
	 * <b>Valor: </b>archigest.archivo.transferencias.serieOrigen<br>
	 * <b>Texto: </b>Serie
	 */
	public static final String ETIQUETA_SERIE_ORIGEN = "archigest.archivo.transferencias.serieOrigen";

	/**
	 * Clave del fichero de propiedades que contiene la etiqueta Serie Destino<br>
	 * <b>Valor: </b>archigest.archivo.transferencias.serieDestino<br>
	 * <b>Texto: </b>Serie
	 */
	public static final String ETIQUETA_SERIE_DESTINO = "archigest.archivo.transferencias.serieDestino";

	/**
	 * Clave del fichero de propiedades que contiene el texto de de 'Volumen'<br>
	 * <b>Valor: </b>archigest.archivo.transferencias.volumen<br>
	 * <b>Texto: </b>Volumen
	 */
	public static final String ETIQUETA_VOLUMEN = "archigest.archivo.transferencias.volumen";


	/**
	 * Clave del fichero de propiedades que contiene el texto de de 'Año de Inicio'<br>
	 * <b>Valor: </b>auditoria.detalles.TRANSFERENCIAS_ANO_INICIO_DETALLE_PREVISION<br>
	 * <b>Texto: </b>Año de Inicio
	 */
	public static final String ETIQUETA_ANIO_INICIAL = "auditoria.detalles.TRANSFERENCIAS_ANO_INICIO_DETALLE_PREVISION";

	/**
	 * Clave del fichero de propiedades que contiene el texto de de 'Año de Fin'<br>
	 * <b>Valor: </b>auditoria.detalles.TRANSFERENCIAS_ANO_FIN_DETALLE_PREVISION<br>
	 * <b>Texto: </b>Año de Fin
	 */
	public static final String ETIQUETA_ANIO_FINAL = "auditoria.detalles.TRANSFERENCIAS_ANO_FIN_DETALLE_PREVISION";

	/**
	 * Clave del fichero de propiedades que contiene el texto de de 'Ubicacion'<br>
	 * <b>Valor: </b>archigest.archivo.ubicacion<br>
	 * <b>Texto: </b>Ubicacion
	 */
	public static final String ETIQUETA_UBICACION = "archigest.archivo.ubicacion";

	/**
	 * Clave del fichero de propiedades que contiene el texto de de 'Estado'<br>
	 * <b>Valor: </b>archigest.archivo.estado<br>
	 * <b>Texto: </b>Estado
	 */
	public static final String ETIQUETA_ESTADO = "archigest.archivo.estado";

	/**
	 * Clave del fichero de propiedades que contiene el texto de de 'Relacion'<br>
	 * <b>Valor: </b>archigest.archivo.relacion<br>
	 * <b>Texto: </b>Relacion
	 */
	public static final String ETIQUETA_RANGO = "archigest.archivo.cf.rango";
	public static final String ETIQUETA_RELACION = "archigest.archivo.relacion";

	/**
	 * Clave del fichero de propiedades que contiene el texto de de 'F.Estado'<br>
	 * <b>Valor: </b>archigest.archivo.fEstado<br>
	 * <b>Texto: </b>F.Estado
	 */
	public static final String ETIQUETA_F_ESTADO = "archigest.archivo.fEstado";


	/**
	 * Clave del fichero de propiedades que contiene el texto de de 'Gestor'<br>
	 * <b>Valor: </b>archigest.archivo.gestor<br>
	 * <b>Texto: </b>Gestor
	 */
	public static final String ETIQUETA_GESTOR = "archigest.archivo.gestor";

	/**
	 * Clave del fichero de propiedades que contiene el texto de de 'Caja'<br>
	 * <b>Valor: </b>archigest.archivo.transferencias.caja<br>
	 * <b>Texto: </b>Caja
	 */
	public static final String ETIQUETA_CAJA = "archigest.archivo.transferencias.caja";

	/**
	 * Clave del fichero de propiedades que contiene el texto de de 'Signatura'<br>
	 * <b>Valor: </b>archigest.archivo.solicitudes.signaturaudoc<br>
	 * <b>Texto: </b>Signatura
	 */
	public static final String ETIQUETA_SIGNATURA = "archigest.archivo.solicitudes.signaturaudoc";

	/**
	 * Clave del fichero de propiedades que contiene el texto de de 'Caja Devuelta'<br>
	 * <b>Valor: </b>archigest.archivo.transferencias.devolver<br>
	 * <b>Texto: </b>Caja devuelta
	 */
	public static final String ETIQUETA_CAJA_DEVUELTA = "archigest.archivo.transferencias.devolver";

	/**
	 * Clave del fichero de propiedades que contiene el texto de de 'Observaciones'<br>
	 * <b>Valor: </b>archigest.archivo.transferencias.observaciones<br>
	 * <b>Texto: </b>Observaciones
	 */
	public static final String ETIQUETA_OBSERVACIONES = "archigest.archivo.transferencias.observaciones";
	public static final String ETIQUETA_PRODUCTOR = "archigest.archivo.cf.productor";

	/**
	 * Clave del fichero de propiedades que contiene el texto de de 'Si'<br>
	 * <b>Valor: </b>archigest.archivo.si<br>
	 * <b>Texto: </b>Si
	 */
	public static final String ETIQUETA_SI = "archigest.archivo.si";

	/**
	 * Clave del fichero de propiedades que contiene el texto de de 'No'<br>
	 * <b>Valor: </b>archigest.archivo.no<br>
	 * <b>Texto: </b>No
	 */
	public static final String ETIQUETA_NO = "archigest.archivo.no";

	/**
	 * Clave del fichero de propiedades que contiene el texto de de 'Contenido'<br>
	 * <b>Valor: </b>archigest.archivo.contenido<br>
	 * <b>Texto: </b>Conetenido
	 */
	public static final String ETIQUETA_CONTENIDO = "archigest.archivo.contenido";

	/**
	 * Clave del fichero de propiedades que contiene el texto de de 'Unidad Documental'<br>
	 * <b>Valor: </b>archigest.archivo.unidadDoc<br>
	 * <b>Texto: </b>Unidad Documental
	 */
	public static final String ETIQUETA_UNIDAD_DOCUMENTAL = "archigest.archivo.unidadDoc";
	public static final String ETIQUETA_TITULO = "archigest.archivo.cf.titulo";
	public static final String ETIQUETA_TITULO_UDOC_FRACCION_SERIE = "archigest.archivo.tituloUDocFraccionSerieRequerido";
	public static final String ETIQUETA_FECHA_INICIO_UDOC_FRACCION_SERIE = "archigest.archivo.fechaInicioUDocFraccionSerieRequerido";
	public static final String ETIQUETA_FECHA_FIN_UDOC_FRACCION_SERIE = "archigest.archivo.fechaFinUDocFraccionSerieRequerido";
	public static final String ETIQUETA_NUM_EXPEDIENTE="archigest.archivo.cf.numExpediente";
	public static final String ETIQUETA_NUM_EXP_FRACCION_SERIE = "archigest.archivo.numExpFraccionSerieRequerido";
	public static final String ETIQUETA_NOF_UDOCS_EN_FRACCION_SERIE = "archigest.archivo.cf.numeroUDocsFraccionSerieNoRellenado";
	public static final String ETIQUETA_FECHA_DEVOLUCION_SOLICITUD = "archigest.archivo.busqueda.form.fecha.devolucion";
	/**
	 * Clave del fichero de propiedades que contiene el texto de de 'Código'<br>
	 * <b>Valor: </b>archigest.archivo.codigo<br>
	 * <b>Texto: </b>Código
	 */
	public static final String ETIQUETA_CODIGO = "archigest.archivo.codigo";
	public static final String ETIQUETA_CODIGO_REFERENCIA= "archigest.archivo.cf.codReferencia";

	/**
	 * Clave del fichero de propiedades que contiene el texto de de 'SIN ARCHIVO RECEPTOR POR DEFECTO'<br>
	 * <b>Valor: </b>archigest.archivo.sin.archivo.receptor.por.defecto<br>
	 * <b>Texto: </b>SIN ARCHIVO RECEPTOR POR DEFECTO
	 */
	public static final String ETIQUETA_SIN_ARCHIVO_RECEPTOR_DEFECTO = "archigest.archivo.sin.archivo.receptor.por.defecto";

	/**
	 * Clave del fichero de propiedades que contiene el texto de 'Archivo Remitente'<br>
	 * <b>Valor: </b>archigest.archivo.archivoRemitente<br>
	 * <b>Texto: </b>Archivo Remitente
	 */
	public static final String ETIQUETA_ARCHIVO_REMITENTE = "archigest.archivo.archivoRemitente";

	/**
	 * Clave del fichero de propiedades que contiene el texto de 'Archivo Receptor'<br>
	 * <b>Valor: </b>archigest.archivo.archivoReceptor<br>
	 * <b>Texto: </b>Archivo Receptor
	 */
	public static final String ETIQUETA_ARCHIVO_RECEPTOR = "archigest.archivo.archivoReceptor";

	/**
	 * Clave del fichero de propiedades que contiene el texto de 'Fondo Destino'<br>
	 * <b>Valor: </b> archigest.archivo.cf.fondoDestino<br>
	 * <b>Texto: </b>Fondo Destino
	 */
	public static final String ETIQUETA_FONDO_DESTINO = "archigest.archivo.cf.fondoDestino";

	/**
	 * Clave del fichero de propiedades que contiene el texto de 'Fondo'<br>
	 * <b>Valor: </b> archigest.archivo.transferencias.fondo<br>
	 * <b>Texto: </b>Fondo
	 */
	public static final String ETIQUETA_FONDO = "archigest.archivo.transferencias.fondo";
	public static final String ETIQUETA_FONDO_JSP = "archigest.archivo.cf.fondo";
	public static final String ETIQUETA_FORMATO = "archigest.archivo.formato";
	public static final String ETIQUETA_CALIFICADOR = "archivo.calificador";
	public static final String ETIQUETA_FORMATO_REENCAJADO = "archigest.archivo.transferencias.reencajado.formato";

	/**
	 * Clave del fichero de propiedades que contiene el texto de 'Signatura Unidad Documental'<br>
	 * @param Signatura
	 * <b>Valor: </b>archigest.archivo.transferencias.udoc.incompleta<br>
	 * <b>Texto: </b>Signatura Unidad Documental: {0}
	 */
	public static final String ETIQUETA_SIGNATURA_UNIDAD_DOCUMENTAL = "archigest.archivo.transferencias.udoc.incompleta";
	public static final String ETIQUETA_ESTADO_DETALLE_PRESTAMOS 	="archigest.archivo.solicitudes.detalle.estado.";
	public static final String ETIQUETA_CALENDAR_PREVISIONES	 	="archigest.archivo.calendar.previsiones";
	public static final String ETIQUETA_CALENDAR_UNIDADES		 	="archigest.archivo.calendar.unidades";
	public static final String ETIQUETA_CALENDAR_DIAS_POSIBLES	 	="archigest.archivo.calendar.dias.posibles";
	public static final String ETIQUETA_BUSQUEDA_FORM_NUMERO="archigest.archivo.busqueda.form.numero";
	public static final String ETIQUETA_ESTADO_ELEMENTO_CF="archigest.archivo.cf.estadoElementoCF";
	public static final String ETIQUETA_BUSQUEDA_FECHA_INICIO = "archigest.archivo.busqueda.form.fecha.inicial";
	public static final String ETIQUETA_BUSQUEDA_FECHA_FINAL = "archigest.archivo.busqueda.form.fecha.final";
	public static final String ETIQUETA_NIVEL_ACCESO="archigest.archivo.nivelAcceso";
	public static final String ETIQUETA_LISTA_CONTROL_ACCESO="archigest.archivo.listaControlAcceso";
	public static final String ETIQUETA_CONDICIONES_AVANZADAS="archigest.archivo.busqueda.form.fecha.condiciones.avanzadas";
	public static final String ETIQUETA_MOTIVO_DE_RECHAZO="archigest.archivo.solicitudes.motivorechazo";
	public static final String ETIQUETA_CODIGO_ORDENACION="archigest.archivo.cf.codigoOrdenacion";

	/*-----------------------------------------------------------------------------------------------
	 * INFORMES
	 * ----------------------------------------------------------------------------------------------*/
	/**
	 * Clave del fichero de propiedades que contiene el texto de de 'Informe de Cotejo'<br>
	 * <b>Valor: </b>archigest.archivo.transferencias.informeCotejo<br>
	 * <b>Texto: </b>Informe de Cotejo
	 */
	public static final String TITULO_INFORME_COTEJO = "archigest.archivo.transferencias.informeCotejo";
	public static final String TITULO_INFORME = "archigest.archivo.informe.titulo";
	public static final String INFORME_SUBTITULO_1="archigest.archivo.informe.subtitulo1";
	public static final String INFORME_SUBTITULO_2="archigest.archivo.informe.subtitulo2";
	public static final String INFORME_SUBTITULO_3="archigest.archivo.informe.subtitulo3";
	public static final String INFORME_DIRECCION="archigest.archivo.informe.direccion";
	public static final String ETIQUETA_DOCS_ELECTRONICOS = "archigest.archivo.documentos.caption";
	public static final String ETIQUETA_UNIDADES_ELECTRONICAS = "archigest.archivo.transferencias.unidadesElectronicas";
	public static final String ETIQUETA_UNIDADES_DOCUMENTALES = "archigest.archivo.unidadesDocumentales";

	//informe resultado busqueda deposito
	public static final String TITULO_INFORME_BUSQUEDA_DEPOSITO = "archigest.archivo.informe.informeResultadoBusquedaDeposito.titulo";
	public static final String TITULO_INFORME_BUSQUEDA_FONDOS = "archigest.archivo.informe.informeResultadoBusquedaFondos.titulo";
	public static final String NUM_RESULTADOS_INFORME_BUSQUEDA_DEPOSITO = "archigest.archivo.informeResultadoBusquedaDeposito.rownum";

	/*-----------------------------------------------------------------------------------------------
	 * EXPRESIONES REGULARES
	 * ----------------------------------------------------------------------------------------------*/

	/**
	 * Expresión Regular para comprobar la fecha. DD/MM/AAAA
	 */
	public static final String REGEXP_FORMATO_FECHA = "(0[1-9]|[12][0-9]|3[01])([/])(0[1-9]|1[012])([/])[0-9]{4}$";

	/**
	 * Expresión Regular de Formato de CIF
	 */
	public static String FORMATO_CIF = "^([A-Z][0-9]{8,8})$";

	public static String REGEXP_LETRAS = "^([A-Z,a-z]*)";

	/**
	 * Expresión Regular de Formato de NIF;
	 */
	public static String FORMATO_NIF = "^([0-9]{1,8}[TRWAGMYFPDXBNJZSQVHLCKET])$";

	/**
	 * Expresión Regular de Formato de Cif/Nif
	 */
	public static String FORMATO_NUMERO_NIF = "^([0-9]{1,8})$";

	public static String FORMATO_NUMERO_INT_MAYOR_CERO = "^([1-9]{1,1}[0-9]{0,4})$";

	/**
	 * Expresión regualar con el siguiente formato: - Numero de 1 a 8 posiciones +
	 * 1 letra Ej. 123A 1234A 12345A 12345678A - Número de 1 a 8 posiciones. Ej.
	 * 1 12 123 1234 12345 1234567 12345678
	 */
	public static String FORMATO_NUMERO_Y_LETRA_CIF_NIF = "^(([0-9]{1,8})|([0-9]{1,8}[A-Z]{1}))$";


	/**
	 * Solo permite letras, números y el espacio.
	 */
	//public static String FORMATO_CARACTERES_VALIDOS = "^[a-zA-Z0-9 _]{0,}$";


	public static String INICIO_FORMATO_CARACTERES_PROHIBIDOS = "^[^";

	public static String FIN_FORMATO_CARACTERES_PROHIBIDOS = "]{0,}$";


	/**
	 * Separador entre campos de la fecha del calendario laboral
	 */
	public static final String SEPARATOR_CALENDAR_CODE = "-";

	public static final String SEPARADOR_FORMATOS_REENCAJADO = " =>(R) ";

    /**
     * Configuración del calendario.
     */
    public static final String CALENDAR_MONTH_NAMES = "archigest.archivo.calendar.month.names";

    /**
     * Lista de calendarios.
     */
    public static final String CALENDAR_DAY_NAMES = "archigest.archivo.calendar.day.names";


    public static final String VALORACION_DOCUMENTOSRECOPILATORIOS="archigest.archivo.valoracion.documentosRecopilatorios";
    public static final String VALORACION_JUSTIFICACION="archigest.archivo.valoracion.justificacion";
    public static final String VALORACION_PERIODOVIGENCIA_ADM="archigest.archivo.valoracion.periodoVigenciaAdm";
    public static final String VALORACION_PERIODOVIGENCIA_LEGAL="archigest.archivo.valoracion.periodoVigenciaLegal";
    public static final String VALORACION_VALORESINFORMATIVOS="archigest.archivo.valoracion.valoresInformativos";
    public static final String VALORACION_VALORCIENTIFICO="archigest.archivo.valoracion.valorCientifico";
    public static final String VALORACION_VALORTECNOLOGICO="archigest.archivo.valoracion.valorTecnologico";
    public static final String VALORACION_VALORCULTURAL="archigest.archivo.valoracion.valorCultural";
    public static final String VALORACION_REGIMENACCESO="archigest.archivo.valoracion.regimenAcceso";

    /**
     *  Constantes para Forwards
     */
    public static final String FORWARD_LISTADO_ACCIONES="listado_acciones";
    public static final String FORWARD_DETALLE_PISTA="detalle_pista";

	/*-----------------------------------------------------------------------------------------------
	 * FUNCIONES
	 * ----------------------------------------------------------------------------------------------*/

	/**
	 * A los campos que son de tipo texto, pueden llevar caracteres especiales &,
	 * etc con esto el XML lo trata como texto y no como caracter especial XML.
	 *
	 * @param cadena
	 *            Cadena de texto a la que hay que añadir el CDATA
	 * @return cadena con el CDATA
	 */
	public static String addCData(String cadena) {
		if(StringUtils.isBlank(cadena)) return "";
		if (cadena.indexOf("<![CDATA[") == -1) {
			cadena = "<![CDATA[" + cadena + "]]>";
		}
		return cadena;
	}

	/**
	 * Obtiene el Identificador de una unidad de instalación.
	 * @param String codigoFondo Código de Fondo
	 * @param String signatura Signatura de la Unidad de Instalación.
	 * @return
	 */
	public static String getIdentificadorUnidadDeInstalacion(String codigoFondo, String signatura){
		if(codigoFondo == null || codigoFondo.equals(BLANK) || signatura == null || signatura.equals(BLANK)) return null;
		return codigoFondo + SEPARADOR_IDENTIFICADOR_UNIDAD_INSTALACION + signatura;
	}

	/**
	 * Obtiene el Identificador de una unidad Documental.
	 * @param String identificacion Identificaición de la Unidad Docuemtal
	 * @param String signatura Signatura de la Unidad de Documental.
	 * @return
	 */
	public static String getIdentificadorUnidadDocumental(String codigoFondo, String signatura){
		if(codigoFondo == null || codigoFondo.equals(BLANK) || signatura == null || signatura.equals(BLANK)) return null;
		return codigoFondo + SEPARADOR_IDENTIFICADOR_UNIDAD_DOCUMENTAL + signatura;
	}

	/**
	 * Concatena la signatura de la unidad documental
	 * @param signaturaCaja Signatura de la Caja que contiene la unidad documental
	 * @param posicion Posicion que ocupa la unidad documental dentro de la caja.
	 * @return Signatura de la unidad documental en una caja.
	 * @Author Lucas Alvarez
	 */
	public static String getSignaturaUnidadDocumental(String signaturaCaja, String posicion) {
		if(StringUtils.isBlank(signaturaCaja) || StringUtils.isBlank(posicion)) return null;
		return signaturaCaja + SEPARADOR_SIGNATURA_UNIDAD_DOCUMENTAL + posicion;
	}

	/**
	 * Obtiene el Código de Orden
	 * @param String ordenElemento Orden del Elemento.
	 * @param String letraElemento Identificaición de la Unidad Docuemtal
	 * @param String codigoOrdenPadre Código de Orden del Elemento Padre.
	 * @return Código de Orden del Elemento Ej. D0001_E0001_C0001_B0001
	 */
	public static final String getCodigoOrden( int ordenElemento, String letraElemento,String codigoOrdenPadre){
		DecimalFormat CODORDEN_FORMATER = new DecimalFormat(FORMATO_COD_ORDEN);
		String codigoOrden = letraElemento + CODORDEN_FORMATER.format(ordenElemento);

		if(!StringUtils.isBlank(codigoOrdenPadre)){
			codigoOrden = codigoOrdenPadre + SEPARADOR_COD_ORDEN + codigoOrden;
		}

		return codigoOrden;
	}


	/**
	 * Obtiene el Código de Orden del Hueco.
	 * @param String ordenElemento Orden del Elemento.
	 * @param String letraElemento Identificaición de la Unidad Docuemtal
	 * @param String codigoOrdenPadre Código de Orden del Elemento Padre.
	 * @param boolean isOrdenacionPorDefecto
	 * @return Código de Orden del Elemento Ej. D0001_E0001_C0001_B0001
	 */
	public static final String getCodigoOrdenPorAnchura(int ordenElemento, String letraElemento,String codigoOrdenPadre){
		DecimalFormat CODORDEN_FORMATER = new DecimalFormat(FORMATO_COD_ORDEN);
		String codigoOrden = letraElemento + CODORDEN_FORMATER.format(ordenElemento);

		if(!StringUtils.isBlank(codigoOrdenPadre)){
			String[] partes = codigoOrdenPadre.split(SEPARADOR_COD_ORDEN);

			int numElementos = partes.length;
			if(numElementos >2){
				codigoOrdenPadre = null;
				for(int pos=0;pos<numElementos-2;pos++){
					if(StringUtils.isBlank(codigoOrdenPadre)){
						codigoOrdenPadre = partes[pos];
					}
					else{
						codigoOrdenPadre += SEPARADOR_COD_ORDEN + partes[pos];
					}
				}

				codigoOrdenPadre += SEPARADOR_COD_ORDEN + partes[numElementos-1];
				codigoOrdenPadre += SEPARADOR_COD_ORDEN + partes[numElementos-2];
			}

			codigoOrden = codigoOrdenPadre + SEPARADOR_COD_ORDEN + codigoOrden;
		}

		return codigoOrden;
	}


	/**
	 * Comprueba si la cadena especificado, contiene caracteres prohibidos, ya que
	 * se están utilizando para hacer separaciones Ej. ".", "-" ,"/" etc.
	 * @param cadena
	 * @return String Caracteres Prohibidos
	 */
	public static boolean hasForbidenChars(String cadena, String caracteresProhibidos){

		String expresionRegular = INICIO_FORMATO_CARACTERES_PROHIBIDOS + caracteresProhibidos + FIN_FORMATO_CARACTERES_PROHIBIDOS;

		Pattern mask = Pattern.compile(expresionRegular);
		Matcher matcher = mask.matcher(cadena);

		if (!matcher.matches()) {
			return true;
		}
		return false;
	}


	public static final String ERROR_UINSTALACION_BLOQUEADA_NO_ORGANIZAR = "archigest.archivo.deposito.uInstalacionBloqueadaNoOrganizar";
	public static final String ERROR_LAS_PARTES_NO_SON_IGUALES = "errors.deposito.partes.no.son.iguales";
	public static final String ERROR_DIVIDIR_SOLO_UNA_UNIDAD	= "archigest.archivo.solo.seleccionar.una.unidad.dividir";
	public static final String ERROR_DEBE_SELECCIONAR_VARIAS_UDOCS = "errors.deposito.seleccionar.unidades.a.unir";
	public static final String ERROR_NO_PERMITIR_UNIR_NO_CONSECUTIVAS = "archigest.archivo.error.unidades.no.unir.consecutivas";
	public static final String ERROR_UDOCS_EN_PRESTAMOS ="errors.deposito.no.organizar.udocs.en.prestamos";
	public static final String ERROR_NIVELES_ARCHIVO_NINGUN_ELEMENTO_SELECCIONADO="errors.nivelesArchivo.ningunElementoSeleccionado";
	public static final String ERROR_NIVELES_ARCHIVO_SIN_ARCHIVO_NO_SELECCIONADO="errors.nivelesArchivo.elementoSinArchivoNoSeleccionado";
	public static final String ERROR_NIVELES_ARCHIVO_PRIMER_ELEMENTO_SELECCIONADO="errors.nivelesArchivo.primerElementoSeleccionado";
	public static final String ERROR_NIVELES_ARCHIVO_ULTIMO_ELEMENTO_SELECCIONADO="errors.nivelesArchivo.ultimoElementoSeleccionado";
	public static final String ERROR_ANIO="error.anio";
	public static final String ERROR_CONFIGURACION_ARCHIVO_MAP_PAISES = "errors.archivo.map.paises";
	public static final String ERROR_CONFIGURACION_MAP_PAISES_INCORRECTO = "errors.archivo.map.paises.incorrecto";
	public static final String NIVEL_FICHAS_UDOC_REP_ECM_ALGUN_DATO_NECESARIO = "errors.archivo.serie.nivelesUDocRepEcm.algunDatoNecesario";
	public static final String NIVEL_FICHAS_UDOC_REP_ECM_NIVEL_NECESARIO = "errors.archivo.serie.nivelesUDocRepEcm.nivelNecesario";
	public static final String NIVEL_FICHAS_UDOC_REP_ECM_NO_PUEDE_REPETIR_NIVEL = "errors.archivo.serie.nivelesUDocRepEcm.noPuedeRepetirNivel";

	public static final String ERRORS_FONDOS_DESTINO_NO_DEFINIDOS = "errors.transferencias.noPosibleTransferir.fondosDestinoNoDefinidos";
	public static final String ERRORS_FONDOS_DESTINO_NO_DEFINIDOS_ALTA = "errors.transferencias.noPosibleTransferir.fondosDestinoNoDefinidosAlta";
	public static final String ERRORS_ARCHIVOS_DESTINO_NO_DEFINIDOS_ALTA = "errors.transferencias.noPosibleTransferir.archivosDestinoNoDefinidosAlta";
	public static final String ERRORS_ARCHIVOS_DESTINO_NO_DEFINIDOS = "errors.transferencias.noPosibleTransferir.archivosDestinoNoDefinidos";
	public static final String ERRORS_EDOCS_NO_SE_PUEDE_REALIZAR_OPERACION_FALTA_REPOSITORIO_ECM_DESCRIPTOR = "errors.documentosElectronicos.noPosibleRealizarOperacion.faltaRepositorioEcmDescriptor";
	public static final String ERRORS_EDOCS_NO_SE_PUEDE_REALIZAR_OPERACION_FALTA_REPOSITORIO_ECM_ELEMENTO_CUADRO = "errors.documentosElectronicos.noPosibleRealizarOperacion.faltaRepositorioEcmElementoCuadro";
	public static final String ERRORS_EDOCS_NO_SE_PUEDE_ELIMINAR_CLASIFICADOR_XTIENE_DESCENCIENTES = "errors.documentosElectronicos.noPosibleBorrarClasificadorXTieneDescendientes";
	public static final String ERRORS_EDOCS_NO_SE_PUEDE_ELIMINAR_DOCUMENTO_FALTA_REPOSITORIO_ECM_DESCRIPTOR = "errors.documentosElectronicos.noPosibleBorrarDocumento.faltaRepositorioEcmDescriptor";
	public static final String ERRORS_EDOCS_NO_SE_PUEDE_ELIMINAR_DOCUMENTO_FALTA_REPOSITORIO_ECM_ELEMENTO_CUADRO = "errors.documentosElectronicos.noPosibleBorrarDocumento.faltaRepositorioEcmElementoCuadro";
	public static final String ERRORS_EDOCS_NO_EXISTEN_DOCUMENTOS_ELECTRONICOS_ASOCIADOS_DESCRIPTOR = "errors.documentosElectronicos.noExistenDocumentosElectronicosDescriptor";
	public static final String ERRORS_EDOCS_NO_EXISTEN_DOCUMENTOS_ELECTRONICOS_ASOCIADOS_ELEMENTO_CUADRO = "errors.documentosElectronicos.noExistenDocumentosElectronicosElementoCuadro";
	public static final String ERRORS_FONDOS_ELEMENTO_TIENE_DOCUMENTOS_ASOCIADOS = "errors.fondos.noPosibleEditarRepositorioEcm.ElementoTieneDocumentosAsociados";
	public static final String XNO_PERMITIDO_PARA_UNIDADES_SIN_VALIDAR = "errors.documentosElectronicos.accionNoPermitidaParaUDocsEnRelacion";
	public static final String XDIVISION_FS_ESTADO_INCORRECTO_BORRADO = "errors.fondos.noPosibleBorrarDivisionFS.estadoDivisionIncorrecto";

	public static final String ERRORS_FONDOS_EXPORTAR_EXCEL = "errors.fondos.exportar.excel";
	public static final String ETIQUETA_CUADRO_CLASIFICACION ="archigest.archivo.cf.cfFondosDocumentales";
	public static final String ERROR_NOMBRE_CAMPO_DATO_REPETIDO = "errors.nombre.campo.dato.repetido";
	public static final String ERROR_NOMBRE_CAMPO_TABLA_REPETIDO = "errors.nombre.campo.tabla.repetido";
	public static final String ERROR_NOMBRE_AREA_REPETIDO = "errors.nombre.area.repetido";
	public static final String ERRORS_FONDOS_ELEMENTO_NO_EXISTE = "errors.fondos.elemento.noExiste";
	public static final String ERROR_UNIFICAR_DESCRIPTORES = "error.unificarDescriptores";
	public static final String ERRORS_EXPORTAR_FICHA_PDF = "errors.exportar.ficha.pdf";
	public static final String ETIQUETA_FICHA ="archigest.archivo.cf.ficha";
	public static final String ERRORS_NO_FOUND_XSL_FICHA_PDF = "errors.no.found.xsl.ficha.pdf";
	public static final String ERRORS_NO_DECLARED_XSL_FICHA_PDF = "errors.no.declared.xsl.ficha.pdf";

	public static final String ERROR_NO_ES_POSIBLE_REUBICAR_VARIAS_UDOCS_EN_FORMATO_NO_MULTIDOCUMENTO = "errors.deposito.reubicarUDocs.noEsPosibleReubicarXFormatoNoMultidocumento";

	public static final String ERROR_NO_SE_PUEDE_MOVER_UDOCS_EN_ELIMINACION_NO_FINALIZADA ="errors.fondos.imposibleMoverXUdocsEnEliminacionSinFinalizar";

	public static final String INFORME_IDENTIFICACION_SERIE_TITULO	= "archigest.archivo.series.informe.identificacion.titulo";
	public static final String LOCALEKEY = "LOCALEKEY";
	public static final String LOCALE_LANGUAGE_COUNTRY_SEPARATOR = "-";
	public static final String DEFAULT_LOCALE_FOLDER = "default";
	public static final String HELP_FOLDER = "help";
	public static final String FILE_SEPARATOR_PROPERTY = "file.separator";
	public static final String FILE_SEPARATOR = System.getProperty(FILE_SEPARATOR_PROPERTY);
	public static final String DESCRIPTOR ="DESCRIPTOR";
	public static final String PROGRESSBAR_PERCENT_KEY="PROGRESSBAR_PERCENT";
	public static final String CANCEL_PROGRESSBAR_KEY="CANCEL_PROGRESSBAR";
	public static final String FORWARD_GENERAR_INFORME_LISTADO="FORWARD_GENERAR_INFORME_LISTADO";
	public static final String FORMATO_INFORME_LISTADO_KEY="formatoListado";
	public static final String FORMATO_PDF="PDF";
	public static final String FORMATO_TXT="TXT";
	public static final String FORMATO_XLS="XLS";
	public static final String ELEMENTO ="ELEMENTO";
	public static final String ID_FICHA = "idficha";
	public static final String ID_UDOC_FICHA = "idudocficha";
	public static final String CURRENT_INVOCATION = "currentInvocation";
	public static final String UDOCRE = "udocre";
	public static final String FSRE = "fsre"; // Fracción de serie
	public static final String UDOCFS = "udocfs";
	public static final String FSFS = "fsfs"; // Fracción de serie

	public static final String VER_GENERAR_INFORME="VER_GENERAR_INFORME";

	//expresion regular para validar las cadenas con el siguiente formato:  1,2, 5-7,8,  9, 12-3, ...
	public static final String EXPRESION_REGULAR_VAL_SEL_CARTELAS="((\\d)+(-(\\d)+)?,(\\s)*)*((\\d)+(-(\\d)+)?)";
	//expresion regular para validar caracteres alfanumericos de las signaturas de los elementos no asignables del deposito
	public static final String EXPRESION_REGULAR_VAL_SEL_CARTELAS_NO_ASIGNABLE="((\\w)+(-(\\w)+)?,(\\s)*)*((\\w)+(-(\\w)+)?)";

	public static final String SHOW_INFORME_BUSQUEDA_BUTTON="showInformeBusquedaButton";
	public static final String PREFIX_R="R";
	public static final String ERROR_NUMERACION_ALFANUMERICA="archigest.archivo.error.aplicacion.signaturas.no.alfanumericas";

	public static final String TIPO_SIGNATURACION_KEY = "archigest.archivo.tipoSignaturacion";

	public static final String ACCION_KEY = "archigest.archivo.auditoria.busqueda.form.accion";
	public static final String ERROR_NECESARIO_SELECCIONAR_UN_ELEMENTO = "errors.gcontrol.listasacceso.necesarioSeleccionarAlMenosUnElemento";
	public static final String ERROR_SOLO_SELECCION_UN_ELEMENTO = "archigest.archivo.msg.seleccion.simple";
	public static final String ERROR_ELEMENTOS_DISTINTO_TIPO = "error.busquedas.accion.elementos.distinto.tipo";
	public static final String ERROR_ELEMENTOS_DISTINTO_PADRE = "error.busquedas.accion.elementos.distinto.padre";
	public static final String ERROR_ELEMENTOS_DISTINTO_ARCHIVO = "error.busquedas.accion.elementos.distinto.archivo";
	public static final String ERROR_ACCION_SIN_PERMISOS = "error.busquedas.accion.elementos.sin.permisos";
	public static final String ERROR_ACCION_BUSQUEDA_NO_ENCONTRADA = "error.busquedas.accion.noEncontrada";
	public static final String ERROR_TIPO_ACCION_BUSQUEDA_NO_ENCONTRADA = "error.busquedas.tipo.accion.noEncontrada";

	public static final String ETIQUETA_FECHA_INICIO_VIGENCIA_PRODUCTOR = "archigest.archivo.fecha.inicio.vigencia.productor";
	public static final String ETIQUETA_FECHA_FIN_VIGENCIA_PRODUCTOR = "archigest.archivo.fecha.fin.vigencia.productor";

	public static final String TIPO_REVDOC_KEY = "archigest.archivo.tipoRevDoc";
	public static final String ID_REV_DOC = "ID_REV_DOC";

	public static final String LINEAS_MENSAJE_MULTILINEA="LINEAS_MENSAJE_MULTILINEA";
	public static final String LABEL_MENSAJE_MULTILINEA="LABEL_MENSAJE_MULTILINEA";
	public static final String MULTILINEA_ERROR_UIs_AFECTADAS="label.unidadesInstalacion.afectadas";
	public static final String ERRORS_GCONTROL_LISTADO_ACCESO_ELIMINAR_X_PERTENECER_A_PRODUCTOR = "errors.gcontrol.listaAcceso.pertenece.a.productor.serie";

	public static final String ETIQUETA_TIPO_NIVEL = "archigest.archivo.tipo";

	public static final String ERROR_NIVEL_CUADRO_REQUERIDO = "errors.fondos.cf.nivel.requerido";
	public static final String ERROR_NIVEL_HIJO_ELIMINAR_REQUERIDO = "errors.fondos.cf.nivel.hijo.eliminar.requerido";
	public static final String ERROR_ELIMINAR_NIVEL_CON_HIJOS = "errors.fondos.cf.nivel.eliminar.hijos";
	public static final String ERROR_ELIMINAR_NIVEL_HIJO = "errors.fondos.cf.nivel.eliminar.hijo";
	public static final String ERROR_ELIMINAR_NIVEL_REFERENCIADO = "errors.fondos.cf.nivel.eliminar.referenciado";

	public static final String ETIQUETA_ROWNUM = "archigest.archivo.informeDeposito.rownum";

	public static final String ETIQUETA_DESCRIPCION = "archigest.archivo.descripcion";
	public static final String ETIQUETA_TIPO_ENTIDAD = "archigest.archivo.cf.TipoEntidad";
	public static final String ETIQUETA_TIPO_CONSULTA = "archigest.archivo.consultas.tipoConsulta";
	public static final String ETIQUETA_VISIBILIDAD = "archigest.archivo.solicitudes.visibilidad";
	public static final String ERROR_TIPO_ENTIDAD_INVALIDO = "archigest.archivo.consultas.tipoEntidad.noValido";
	public static final String ERROR_MOTIVO_YA_EXISTE = "archigest.archivo.solicitudes.motivo.yaExiste";
	public static final String ERROR_ELIMINAR_MOTIVO = "errors.solicitudes.motivo.eliminar";
	public static final String ETIQUETA_TIPO_USUARIO = "archigest.archivo.tipoUsuario";
	public static final String ETIQUETA_TIPO_PRESTAMO = "archigest.archivo.prestamos.tipoPrestamo";
	public static final String ERROR_COMPACTAR_GENERICO = "archigest.archivo.compactar.error.generico";
	public static final String ETIQUETA_TIPO_SOLICITUD = "archigest.archivo.solicitudes.tipoSolicitud";
	public static final String ERROR_AL_CREAR_ASIGNABLE = "archigest.archivo.deposito.error.crear.asignable";

	public static final String LITERAL_PATH_HUECO = "Hueco";

	public static final String ERROR_SERIE_SIN_ENTIDAD_PRODUCTORA = "archigest.archivo.series.sinEntidadProductora";

	public static final String ETIQUETA_FECHA_CREACION = "archigest.archivo.documentos.clasificador.form.fechaCreacion";
	public static final String ERROR_FECHA_ENTREGA_Y_DEVOLUCION = "archigest.archivo.solicitudes.error.fechas.registro.entrega";

	public static final String ETIQUETA_DESC_UI = "archigest.archivo.transferencias.descripcionUI";

	public static final String SEPARADOR_DEFECTO_FECHAS_RELACION = "/";
	public static final String FORMATO_FECHA_DIA = "dd";
	public static final String FORMATO_FECHA_MES = "MM";
	public static final String FORMATO_FECHA_ANIO = "yyyy";
	public static final String IS_BACK_INVOCATION_KEY = "IS_BACK_INVOCATION_KEY";
	public static final String LISTA_ACCIONES_BUSQUEDA_KEY = "LISTA_ACCIONES_BUSQUEDA_KEY";
	public static DateFormat dateFormater = new SimpleDateFormat(FORMATO_FECHA);

	public static final String ERROR_AL_OBTENER_ARCHIVO_ELEMENTOS = "archigest.archivo.error.al.obtener.archivo.elementos";

	public static final String ERROR_SIN_PRESTAMOS_PARA_ANIADIR_UDOCS = "archigest.archivo.busquedas.operacion.accion.ANIADIR_A_PRESTAMO.sin.prestamos";
	public static final String ERROR_SIN_CONSULTAS_PARA_ANIADIR_UDOCS = "archigest.archivo.busquedas.operacion.accion.ANIADIR_A_CONSULTA.sin.consultas";

	public static final String SERIAL_ID = "serialVersionUID";
	public static final String PARAM_METHOD = "method";

	public final static String MENU_SALAS_CONSULTA = "MenuSalasConsulta";
	public final static String MENU_SERVICIO_SALAS = "MenuServicioSalas";

	public static final String OPCION_TODOS = "0";
	public static final String LABEL_TODOS_LOS_CAMPOS = "archigest.descripcion.reemplazo.fichas.todosLosCampos";

	public static final String ERROR_VALIDACION_SIGNATURAS = "archigest.archivo.deposito.signaturas.error.expresion";

	public static final String LABEL_DIA = "archigest.archivo.dia";
	public static final String LABEL_MES = "archigest.archivo.mes";
	public static final String LABEL_ANIO = "archigest.archivo.anio";
	public static final String LABEL_SIGLO = "archigest.archivo.formato.S";

	public static final String LABEL_DESDE = "archigest.archivo.desde";
	public static final String LABEL_HASTA = "archigest.archivo.hasta";
	public static final String COL_MAX = "MAX";
	public static final String COL_MIN = "MIN";

	public static final String ERROR_DESRCIPCION_SOLO_UNA_UNIDAD	= "archigest.archivo.transferencias.selUnExpediente";
	public static final String ERROR_RECORD_NOT_FOUND = "archigest.archivo.record.not.found";

	public static final String ERROR_DESCRIPTOR_DUPLICADO			 = "archigest.archivo.errors.descriptor.duplicado";
	public static final String ERROR_LISTA_DESCRIPTORES_CERRADA		 = "archigest.archivo.errors.lista.descriptores.cerrada";
	public static final String LABEL_SOLICITAR						 = "archigest.archivo.solicitar";

	public static final String SEPARADOR_EXTENSION_FICHERO			 = ".";

	public static final String ERROR_ALMACENAR_DOCUMENTO			= "errors.documentosElectronicos.error.almacenar.fichero";

	public static final String PROPERTY_ID_LISTA_VOLUMENES			= "ID_LISTA_VOLUMENES";
	public static final String PROPERTY_JDBC_DATASOURCE				= "JDBC_DATASOURCE";
	public static final String PROPERTY_MOTOR_DOCUMENTAL_PLATAFORMA = "MOTOR_DOCUMENTAL_PLATAFORMA";
	public static final String PROPERTY_MOTOR_DOCUMENTAL_RUTA_PLATAFORMA = "MOTOR_DOCUMENTAL_RUTA_PLATAFORMA";
	public static final String PROPERTY_MOTOR_DOCUMENTAL_EXTENSIONES = "MOTOR_DOCUMENTAL_EXTENSIONES";

	public static final String LABEL_CAMPO_DATO						= "archigest.archivo.campo.dato";
	public static final String LABEL_CAMPO_TABLA					= "archigest.archivo.campo.tabla";
	public static final String ERROR_ELEMENTOS_DISTINTOS			= "errors.elemento.no.igual.a.otro";

}

