package auditoria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;

import auditoria.vos.ArchivoError;

import common.definitions.ArchivoModules;

/**
 * Clase que encapsula los errores existentes en la aplicacion
 */
public class ArchivoErrorCodes {
	public static final int ERROR_DESCONOCIDO = 0;

	/** Errores TRANSFERENCIAS_MODULE - 1xxx */
	// Previsión no en estado 'Abierta'
	public static final int PREVISION_NO_ABIERTA = 1001;
	// Prevision detallada sin ningun detalle asociado
	public static final int PREVISION_DETALLADA_SIN_DETALLES = 1002;
	// Un organo remitente no puede tener abiertas mas de una prevision del
	// mismo tipo
	public static final int PREVISION_EN_CURSO_EN_ORGANO_REMITENTE = 1003;
	// Detalle de prevision cerrado con lo que ya no admite mas relaciones
	public static final int DETALLE_PREVISION_CERRADO = 1004;
	// No pueden existir dos relaciones del mismo tipo y con igual formato sobre
	// un mismo detalle de prevision
	public static final int RELACION_DUPLICADA = 1005;
	// Formato no vigente
	public static final int FORMATO_NO_VIGENTE = 1006;
	// Prevision caducada
	public static final int PREVISION_CADUCADA = 1007;
	// Prevision no aceptada
	public static final int PREVISION_NO_ACEPTADA = 1008;
	// La creacion de prevision no es posible por alguna causa no identificada
	public static final int CREACION_PREVISION_NO_PERMITIDA = 1009;
	// No es posible la modificacion de fondo contra el que se hace la
	// transferencia
	public static final int MODIFICACION_FONDO_NO_PERMITIDA = 1010;
	// No se puede resolver el fondo destino para las transferencias de un
	// organo
	public static final int FONDO_DESTINO_NO_DEFINIDO = 1011;
	// No se puede resolver el archivo destino para las transferencias de un
	// organo
	public static final int ARCHIVO_DESTINO_NO_DEFINIDO = 1038;
	// Solo el gestor puede realizar la operacion seleccionada
	public static final int SOLO_PERMITIDO_A_GESTOR = 1012;

	// Relación no en estado 'Abierta'
	public static final int RELACION_NO_ABIERTA = 1013;
	public static final int RELACION_SIN_UDOC_ASOCIADAS = 1014;
	public static final int RELACION_CON_DOC_FISICOS_SIN_DOCUMENTOS = 1015;
	public static final int RELACION_UDOC_CON_DOC_FISICOS_SIN_CAJA = 1016;
	public static final int RELACION_NO_TIENE_ESPACIO_EN_DEPOSITO = 1017;
	public static final int RELACION_TIENE_UDOCS_EN_ESTADO_PENDIENTE = 1018;
	public static final int RELACION_TIENE_UDOCS_CON_ERRORES = 1019;
	public static final int RELACION_EN_ESTADO_INCORRECTO = 1020;
	public static final int RELACION_TIENE_SOLICITUD_RESERVA_SIN_RESPONDER = 1021;

	public static final int PREVISION_NO_EN_ESTADO_ADECUADO = 1022;
	public static final int DETALLE_SIN_PROCEDIMIENTO_Y_SERIE = 1023;
	public static final int EXISTE_LINEA_DETALLE_MISMO_PROCEDIMIENTO = 1024;
	public static final int EXISTE_LINEA_DETALLE_MISMA_SERIE = 1025;

	public static final int NO_PERMITIDO_EN_TRANSFERENCIAS_ORDINARIAS = 1026;
	public static final int USER_CAN_NOT_VIEW_DATA = 1027;
	public static final int ERROR_SISTEMA_TRAMITADOR_NO_RECONOCIDO = 1028;

	public static final int ERROR_NO_EXISTE_GESTOR_ESPECIFICADO = 1029;
	public static final int ERROR_NO_HAY_PREVISIONES_SELECCIONADAS = 1030;
	public static final int ERROR_GESTOR_ESTANDAR_CON_PREVISION_A_GESTIONAR = 1031;
	public static final int ERROR_NO_HAY_RELACIONES_SELECCIONADAS = 1032;
	public static final int EXISTE_RELACION_MISMA_SERIE = 1033;
	public static final int EN_ORDINARIA_NO_PUEDE_FINALIZARSE_COTEJO_SI_EXISTE_ERRORES_EN_CAJAS_NO_DEVUELTAS = 1034;
	// public static final int
	// EN_ORDINARIA_NO_SE_PUEDEN_MODIFICAR_DATOS_DE_NINGUN_EXPEDIENTE = 1035;
	public static final int NO_SE_PUEDEN_MODIFICAR_DATOS_DE_NINGUN_EXPEDIENTE_CON_PARTES_VALIDADAS = 1036;
	public static final int EN_ORDINARIAS_SI_PARTE_CON_ERRORES_TODAS_CON_ERRORES = 1037;

	// Un usuario gestor no puede tener abiertas mas de una prevision entre
	// archivos
	public static final int PREVISION_ENTRE_ARCHIVOS_EN_CURSO_EN_USUARIO_REMITENTE = 1038;

	// Error para los casos en los que se intenta validar una relación de
	// entrega sin tener permiso de alta en el cuadro de clasificación
	public static final int ERROR_NO_TIENE_PERMISO_ALTA_ELEMENTO = 1039;

	// Error para los casos en los que se intenta crear una previsión pero no
	// existe ningún fondo al que se pueda transferir
	public static final int FONDOS_DESTINO_NO_DEFINIDOS = 1040;

	// Error para los casos en los que se intenta dar de alta una unidad
	// documental pero no existe ningún fondo en el que se pueda hacer
	public static final int FONDOS_DESTINO_NO_DEFINIDOS_ALTA = 1041;

	// No existen posibles archivos destino del alta
	public static final int ARCHIVOS_DESTINO_NO_DEFINIDOS_ALTA = 1042;

	// No existen posibles archivos destino de la previsión entre archivos
	public static final int ARCHIVOS_DESTINO_NO_DEFINIDOS = 1043;

	// El usuario no pertenece a ningún organo
	public static final int USUARIO_SIN_ORGANO = 1044;

	// La relacion de entrega contiene unidades de instalacion con más de una
	// unidad documental
	public static final int REEA_NO_MULTIDOC_CON_UIS_CON_VARIAS_UDOCS = 1045;
	public static final int REEA_NO_MULTIDOC_CON_UIS_CON_VARIAS_FS = 1046;
	public static final int REEA_REENCAJADO_NO_FINALIZA_SIN_UDOCS = 1047;

	/** Errores DEPOSITOS_MODULE - 2xxx */
	public static final int DEPOSITO_MODULE_ERROR = 2;

	public static final int ERROR_ELEMENT_NOT_EMPTY = 2001;
	public static final int ERROR_NOT_LAST_CHILD = 2002;
	public static final int ERROR_LONGITUD_MENOR_Q_LONGITUD_FORMATO = 2003;
	public static final int ERROR_CONTIENE_HUECOS_OCUPADOS_O_RESERVADOS = 2004;
	public static final int ERROR_ELEMENT_DELETED = 2005;
	public static final int EL_HUECO_CONTIENE_UNIDADES_DOCS_DE_OTRO_FONDO = 2006;
	public static final int NO_SE_PUEDE_REUBICAR_EN_LA_MISMA_UNIDADINSTALACION = 2007;
	public static final int NO_SE_PUEDE_ELIMINAR_EL_FORMATO = 2008;
	public static final int NO_SE_PUEDE_CREAR_IDENTIFICADOR_UNICO_HUECO = 2009;
	public static final int NO_SE_PUEDE_REUBICAR_EN_UI_NO_MULTIDOC = 2010;
	public static final int NUMERACION_HUECO_EN_USO = 2011;
	public static final int NO_SE_PUEDE_RENUMERAR_HUECOS_ALFANUMERICOS = 2012;
	public static final int NO_SE_PUEDE_RENUMERAR_A_HUECOS_OCUPADOS = 2013;
	public static final int NO_SE_PUEDE_RENUMERAR_A_HUECOS_CREADOS = 2014;
	public static final int NO_SE_PUEDE_RENUMERAR_MAXIMO_NUMERACION_EXCEDIDA = 2015;
	public static final int NO_SE_PUEDE_UBICAR_A_HUECOS_NO_LIBRES = 2016;
	public static final int NO_SE_PUEDE_RESERVAR_A_HUECOS_NO_LIBRES = 2017;
	public static final int NO_SE_PUEDE_REUBICAR_A_HUECOS_NO_LIBRES = 2018;
	public static final int NO_SE_PUEDE_RESERVAR_A_HUECOS_NO_LIBRES_SIGNATURA_ASOCIADA_A_HUECO = 2019;
	public static final int COMPACTAR_HUECO_OCUPADO_PROCESO_COMPACTACION = 2020;
	public static final int NO_SE_PUEDE_CAMBIAR_ESTADO_HUECO_MODIFICACION = 2021;
	public static final int SIGNATURA_HUECO_OCUPADA = 2022;

	/** Errores PRESTAMOS_MODULE - 3xxx */
	public static final int ERROR_ESTADO_NO_VALIDO = 3001;
	public static final int ERROR_DEVOLUCION_Y_NO_ENTREGADA = 3002;
	public static final int ERROR_ENVIO_SIN_DETALLES = 3003;
	public static final int ERROR_AUTORIZACION_ESTADO_NO_VALIDO = 3004;
	public static final int ERROR_AUTORIZACION_SIN_DETALLES = 3005;
	public static final int ERROR_ENTREGA_ESTADO_NO_VALIDO = 3006;
	public static final int ERROR_ENTREGA_SIN_DETALLES = 3007;
	public static final int ERROR_DEVOLVER_ESTADO_NO_VALIDO = 3008;
	public static final int ERROR_RECLAMAR_ESTADO_NO_VALIDO = 3009;
	public static final int ERROR_CREACION_XOTRA = 3010;
	public static final int ERROR_EDICION_NOEDITABLE = 3011;
	public static final int ERROR_ENVIO_FECHA_NO_VALIDA = 3012;
	public static final int ERROR_ENVIO_ESTADO_NO_VALIDO = 3013;
	public static final int ERROR_PRORROGA_TRATADA = 3014;
	public static final int ERROR_ENTREGA_DETALLE_ESTADO_NO_VALIDO = 3015;
	public static final int ERROR_PRESTAMO_NO_BORRABLE = 3016;
	public static final int ERROR_AUTORIZAR_ESTADO_NO_VALIDO = 3017;
	public static final int ERROR_AUTORIZAR_ARCHIVO_NO_VALIDO = 3018;
	public static final int ERROR_FINALIZARAUTORIZACION_ESTADO_NO_VALIDO = 3019;
	public static final int ERROR_PRESTAMO_NO_RESERVADO = 3020;
	public static final int ERROR_PRESTAMO_NO_ENTREGABLE_X_FECHA = 3021;
	public static final int ERROR_PRESTAMO_NO_ENTREGABLE_X_USUARIO = 3022;
	public static final int ERROR_PRESTAMO_NO_AUTORIZADO = 3023;
	public static final int ERROR_ENTREGA_ARCHIVO_NO_VALIDO = 3024;
	public static final int ERROR_PRESTAMO_NO_ENTREGADO = 3025;
	public static final int ERROR_RECLAMAR_EN_FECHA_VALIDA = 3026;
	public static final int ERROR_RECLAMAR_X_PRORROGA = 3027;

	public static final int ERROR_CREACIONCONSULTA_XOTRA = 3028;
	public static final int ERROR_CONSULTA_NO_BORRABLE = 3029;
	public static final int ERROR_ENVIO_CONSULTA_FECHA_NO_VALIDA = 3030;
	public static final int ERROR_ENVIO_CONSULTA_ESTADO_NO_VALIDO = 3031;
	public static final int ERROR_EDICION_CONSULTA_NOEDITABLE = 3032;
	public static final int ERROR_CONSULTA_NO_RESERVADA = 3033;
	public static final int ERROR_CONSULTA_NO_ENTREGABLE_X_FECHA = 3034;
	public static final int ERROR_CONSULTA_NO_ENTREGABLE_X_USUARIO = 3035;
	public static final int ERROR_CONSULTA_NO_AUTORIZADA = 3036;
	public static final int ERROR_MODIFICACION_XOTRA = 3037;
	public static final int ERROR_AUTORIZACION_DETALLE_NO_DISPONIBLE = 3038;
	public static final int ERROR_CESION_PRESTAMO_NO_ORGANIZACION = 3039;
	public static final int ERROR_ENTREGA_FECHA_MAXIMA_FIN_SUPERADA = 3040;

	/** Nombres Errores DESCRIPCION_MODULE - 4xxx */
	public static final int ERROR_DEFINICION_FICHA_NO_BIEN_FORMADA = 4001;
	public static final int ERROR_CAMPO_FORMULARIO_VACIO = 4002;

	/** Errores CLASIFICACION_MODULE - 5xxx */
	public static final int ERROR_FRACCION_SERIE_NO_DIVISIBLE_XPRESTAMO_CONSULTA = 5001;

	/** Errores SERIES_MODULE - 6xxx */
	public static final int ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XESTADO = 6001;
	public static final int ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XUSUARIO = 6002;
	public static final int ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XVALORADM = 6003;
	public static final int ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XVALORLEGAL = 6004;
	public static final int ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XREGACCESO = 6005;
	public static final int ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XVALORDICTAMEN = 6006;
	public static final int ERROR_VALORACION_NOAUTORIZABLE_XESTADO = 6007;
	public static final int ERROR_VALORACION_NORECHAZABLE_XESTADO = 6008;
	public static final int ERROR_VALORACION_NO_BORRABLE = 6009;
	public static final int ERROR_VALORACION_NO_CREABLE_XESTADO = 6010;
	public static final int ERROR_VALORACION_NO_CREABLE_XESTADO_VALORACION = 6011;
	public static final int ERROR_VALORACION_NO_MODIFICABLE_XESTADO = 6012;
	public static final int ERROR_VALORACION_NOEVALUABLE_XESTADO = 6013;
	public static final int ERROR_VALORACION_NODICTAMINABLE_XESTADO = 6014;
	public static final int ERROR_VALORACION_NO_CREABLE_XUSUARIO = 6015;
	public static final int ERROR_VALORACION_SOLO_PERMITIDO_A_GESTOR_VALORACIONES = 6016;

	public static final int ERROR_ELIMINACION_NO_CREABLE_XESTADO = 6500;
	public static final int ERROR_ELIMINACION_NO_CREABLE_XESTADO_ELIMINACION = 6501;
	public static final int ERROR_SOLICITUD_APROBACION_NO_CREABLE_XESTADO = 6502;
	public static final int ERROR_APROBACION_ELIMINACION_NO_CREABLE_XESTADO = 6503;
	public static final int ERROR_RECHAZO_ELIMINACION_NO_CREABLE_XESTADO = 6504;
	public static final int ERROR_ELIMINACION_NO_BORRABLE = 6505;
	public static final int ERROR_ELIMINACION_NO_EJECUTABLE = 6506;
	public static final int ERROR_ELIMINACION_NO_DESTRUIBLE = 6507;
	public static final int ERROR_ELIMINACION_NO_FINALIZABLE = 6508;
	public static final int ERROR_VALORACION_SOLO_PERMITIDO_A_GESTOR = 6509;
	public static final int ERROR_ELIMINACION_NO_EDITABLE_XESTADO = 6510;
	public static final int ERROR_APROBACION_DIRECTA_ELIMINACION_NO_CREABLE_XUSUARIO = 6511;
	public static final int ERROR_ELIMINACION_DESTRUCCION_NO_REALIZADA = 6512;
	public static final int ERROR_ELIMINACION_THREAD_EJECUTANDOSE = 6513;
	public static final int ERROR_ELIMINACION_NO_EJECUTABLE_SIN_PERMISOS_DOCUMENTOS = 6514;
	public static final int ERROR_ELIMINACION_NO_EJECUTABLE_SIN_PERMISOS_ELEMENTOS = 6515;

	/** Errores USUARIOS_MODULE - 7xxx */

	/** Errores DOSSIER_MODULE - 8xxx */

	/** Errores EXPLOTACION_MODULE - 9xxx */

	/** Errores DIGITALIZACION_MODULE - 10xxx */

	/** Errores AUDITORIA_MODULE - 11xxx */

	/** Errores LOGIN_MODULE - 12xxx */
	public static final int ERROR_LOGIN_NO_VALIDO = 12001;
	public static final int ERROR_ACCION_NO_PERMITIDA = 12002;

	/** Errores ARCHIVOS_MODULE - 13xxx */
	public static final int ERROR_NO_SE_PUEDE_ELIMINAR_EL_ARCHIVO = 13001;

	/** Errores SALAS_MODULE - 14xxx */
	public static final int ERROR_CONTIENE_MESAS_OCUPADAS = 14001;

	/** Errores ORGANIZACION_MODULE 15xxx */

	/*****************************
	 * NOMBRES
	 ****************************/
	// TODO 0.Internacionalizar
	/** Nombres Errores TRANSFERENCIAS_MODULE - 1xxx */
	public static final String ERROR_NO_HAY_PREVISIONES_SELECCIONADAS_NAME = "No hay previsiones seleccionadas";
	public static final String ERROR_GESTOR_ESTANDAR_CON_PREVISION_A_GESTIONAR_NAME = "El gestor ya tiene una previsión asignada";
	public static final String ERROR_NO_HAY_RELACIONES_SELECCIONADAS_NAME = "No hay relaciones de entrega seleccionadas";
	public static final String DETALLE_SIN_PROCEDIMIENTO_Y_SERIE_NAME = "Detalle sin procedimiento y serie";
	public static final String EXISTE_LINEA_DETALLE_MISMO_PROCEDIMIENTO_NAME = "Existe otro detalle con mismo procedimiento y serie";
	// public static final String ERROR_NO_TIENE_PERMISO_ALTA_ELEMENTO_NAME =
	// "No es posible realizar la validación de las unidades documentales de la relación, los perfiles asignados a su usuario no contienen el permiso de Alta en el Cuadro de Clasificación";

	/** Nombres Errores DEPOSITOS_MODULE - 2xxx */
	public static final String ERROR_ELEMENT_NOT_EMPTY_NAME = "Elemento no vacio";
	public static final String ERROR_NOT_LAST_CHILD_NAME = "No es el ultimo hijo";
	public static final String ERROR_LONGITUD_MENOR_Q_LONGITUD_FORMATO_NAME = "Longitud menor que longitud del formato";
	public static final String ERROR_CONTIENE_HUECOS_OCUPADOS_O_RESERVADOS_NAME = "Contiene huecos ocupados o reservados";
	public static final String ERROR_ELEMENT_DELETED_NAME = "Elemento eliminado";
	public static final String EL_HUECO_CONTIENE_UNIDADES_DOCS_DE_OTRO_FONDO_NAME = "El hueco contiene unidades documentales de otro fondo";
	public static final String NO_SE_PUEDE_REUBICAR_EN_LA_MISMA_UNIDADINSTALACION_NAME = "No se puede reubicar en la misma Unidad de Unidad";
	public static final String NO_SE_PUEDE_ELIMINAR_EL_FORMATO_NAME = "No es posible eliminar porque existen relaciones o huecos que ya tienen este formato";
	public static final String NO_SE_PUEDE_CREAR_IDENTIFICADOR_UNICO_HUECO_NAME = "No se ha podido crear la caja en el hueco. Error al crear el identificador único";

	/** Nombres Errores PRESTAMOS_MODULE - 3xxx */
	public static final String ERROR_ESTADO_NO_VALIDO_NAME = "Préstamos en estado no válido";
	public static final String ERROR_DEVOLUCION_Y_NO_ENTREGADA_NAME = "Devolución de un préstamo que no ha sido entregado";
	public static final String ERROR_ENVIO_SIN_DETALLES_NAME = "Envío de un préstamo sin detalles";
	public static final String ERROR_AUTORIZACION_ESTADO_NO_VALIDO_NAME = "Autorización de un préstamo en un estado no adecuado(solo solicitado o reservado)";
	public static final String ERROR_AUTORIZACION_SIN_DETALLES_NAME = "Autorización de un préstamo sin detalles";
	public static final String ERROR_ENTREGA_ESTADO_NO_VALIDO_NAME = "Entrega de un préstamo en un estado no adecuado(solo autorizado o reservado)";
	public static final String ERROR_ENTREGA_SIN_DETALLES_NAME = "Entrega de un préstamo sin detalles";
	public static final String ERROR_DEVOLVER_ESTADO_NO_VALIDO_NAME = "Devolución de un préstamos en estado no válido(solo entregado o devuelto incompleto)";
	public static final String ERROR_RECLAMAR_ESTADO_NO_VALIDO_NAME = "Reclamación de un préstamos en estado no válido(solo entregado)";
	public static final String ERROR_CREACION_XOTRA_NAME = "Creación de un préstamo con otro abierto";
	public static final String ERROR_MODIFICACION_XOTRA_NAME = "Modificación de un préstamo con otro abierto";
	public static final String ERROR_EDICION_NOEDITABLE_NAME = "Edición de un préstamo no editable";
	public static final String ERROR_ENVIO_FECHA_NO_VALIDA_NAME = "Envío de préstamo en fecha no válida";
	public static final String ERROR_ENVIO_ESTADO_NO_VALIDO_NAME = "Envío de préstamo en estado no válido(abierto)";
	public static final String ERROR_PRORROGA_TRATADA_NAME = "Prórroga ya tratada";
	public static final String ERROR_ENTREGA_DETALLE_ESTADO_NO_VALIDO_NAME = "Entrega de detalle en estado no válido(solo autorizado o reservado)";
	public static final String ERROR_PRESTAMO_NO_BORRABLE_NAME = "Préstamos no borrable por su estado(solo abierto)";
	public static final String ERROR_AUTORIZAR_ESTADO_NO_VALIDO_NAME = "Préstamos no autorizable por su estado(solo solicitado)";
	public static final String ERROR_ENVIO_ARCHIVO_NO_VALIDO_NAME = "Préstamo no autorizable por los archivos del usuario";
	public static final String ERROR_FINALIZARAUTORIZACION_ESTADO_NO_VALIDO_NAME = "Préstamo no autorizable por su estado(sólo solicitado)";
	public static final String ERROR_PRESTAMO_NO_RESERVADO_NAME = "Préstamo no entregable por su estado(no reservado)";
	public static final String ERROR_PRESTAMO_NO_ENTREGABLE_X_FECHA_NAME = "La fecha de reserva del préstamo para su entrega no es válida";
	public static final String ERROR_PRESTAMO_NO_ENTREGABLE_X_USUARIO_NAME = "El usuario no puede solicitar la entrega";
	public static final String ERROR_PRESTAMO_NO_AUTORIZADO_NAME = "Préstamo no puede ser entregado porque no ha sido autorizado";
	public static final String ERROR_ENTREGA_ARCHIVO_NO_VALIDO_NAME = "Préstamo no entregable por los archivos del usuario";;
	public static final String ERROR_PRESTAMO_NO_ENTREGADO_NAME = "Préstamo no prorrogable por no estar entregado";
	public static final String ERROR_ACCION_NO_PERMITIDA_NAME = "Acción no permitida al usuario";
	public static final String ERROR_RECLAMAR_EN_FECHA_VALIDA_NAME = "Préstamo no reclamable(está en fecha válida)";
	public static final String ERROR_RECLAMAR_X_PRORROGA_NAME = "Préstamo no reclamable(tiene prórroga solicitada)";

	public static final String ERROR_CREACIONCONSULTA_XOTRA_NAME = "Creación de una consulta con otra abierta";
	public static final String ERROR_CONSULTA_NO_BORRABLE_NAME = "Consulta no borrable por su estado(solo abierto)";
	public static final String ERROR_ENVIO_CONSULTA_FECHA_NO_VALIDA_NAME = "Envío de consulta en fecha no válida";
	public static final String ERROR_ENVIO_CONSULTA_ESTADO_NO_VALIDO_NAME = "Envío de consulta en estado no válido(abierto)";
	public static final String ERROR_EDICION_CONSULTA_NOEDITABLE_NAME = "Edición de una consulta no editable";
	public static final String ERROR_CONSULTA_NO_RESERVADA_NAME = "Consulta no entregable por su estado(no reservada)";
	public static final String ERROR_CONSULTA_NO_ENTREGABLE_X_FECHA_NAME = "La fecha de reserva de la consulta para su entrega no es válida";
	public static final String ERROR_CONSULTA_NO_ENTREGABLE_X_USUARIO_NAME = "El usuario no puede solicitar la entrega";
	public static final String ERROR_CONSULTA_NO_AUTORIZADA_NAME = "Consulta no puede ser entregada porque no ha sido autorizada";
	public static final String ERROR_CESION_PRESTAMO_NO_ORGANIZACION_NAME = "El usuario gestor no está asignado a ningún órgano";

	/** Nombres Errores DESCRIPCION_MODULE - 4xxx */
	public static final String ERROR_DEFINICION_FICHA_NO_BIEN_FORMADA_NAME = "La definición del ficha no está bien formada";
	public static final String ERROR_CAMPO_FORMULARIO_VACIO_NAME = "Campo del formulario vacío";

	/** Nombres Errores CLASIFICACION_MODULE - 5xxx */

	/** Nombres Errores SERIES_MODULE - 6xxx */
	public static final String ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XESTADO_NAME = "Valoración no solicitable para validación por estado";
	public static final String ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XUSUARIO_NAME = "Valoración no solicitable para validación por usuario";
	public static final String ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XVALORADM_NAME = "Valoración no solicitable para validación por valor administrativo";
	public static final String ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XVALORLEGAL_NAME = "Valoración no solicitable para validación por valor legal";
	public static final String ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XREGACCESO_NAME = "Valoración no solicitable para validación por por régimen de acceso";
	public static final String ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XVALORDICTAMEN_NAME = "Valoración no solicitable para validación por valor dictamen";
	public static final String ERROR_VALORACION_NOAUTORIZABLE_XESTADO_NAME = "Valoración no autorizable por estado";
	public static final String ERROR_VALORACION_NORECHAZABLE_XESTADO_NAME = "Valoración no rechazable por estado";
	public static final String ERROR_VALORACION_NO_BORRABLE_NAME = "Valoración no borrable por estado";
	public static final String ERROR_VALORACION_NO_CREABLE_XESTADO_NAME = "Valoración no creable por estado serie";
	public static final String ERROR_VALORACION_NO_CREABLE_XESTADO_VALORACION_NAME = "Valoración no creable por serie tiene otra valoración en estado";
	public static final String ERROR_VALORACION_NO_MODIFICABLE_XESTADO_NAME = "Valoración no modificable en el estado actual";
	public static final String ERROR_VALORACION_NOEVALUABLE_XESTADO_NAME = "Valoración no evaluable en el estado actual";
	public static final String ERROR_VALORACION_NODICTAMINABLE_XESTADO_NAME = "Valoración no dictaminable en el estado actual";
	public static final String ERROR_VALORACION_NO_CREABLE_XUSUARIO_NAME = "Valoración no creable por el usuario";
	public static final String ERROR_ELIMINACION_NO_CREABLE_XESTADO_NAME = "Eliminación no creable por estado actual";
	public static final String ERROR_ELIMINACION_NO_CREABLE_XESTADO_ELIMINACION_NAME = "Eliminacion no creable por existir otra en estado";
	public static final String ERROR_SOLICITUD_APROBACION_NO_CREABLE_XESTADO_NAME = "Solicitud aprobación no posible en el estado actual";
	public static final String ERROR_APROBACION_ELIMINACION_NO_CREABLE_XESTADO_NAME = "Aprobación de eliminación no posible en el estado actual";
	public static final String ERROR_RECHAZO_ELIMINACION_NO_CREABLE_XESTADO_NAME = "Rechazo de eliminación no posible en el estado actual";
	public static final String ERROR_ELIMINACION_NO_BORRABLE_NAME = "Eliminación no borrable en el estado actual";
	public static final String ERROR_ELIMINACION_NO_EJECUTABLE_NAME = "No se puede realizar la eliminación física";
	public static final String ERROR_ELIMINACION_NO_DESTRUIBLE_NAME = "No se puede realizar la destrucción física en el estado actual";
	public static final String ERROR_ELIMINACION_NO_FINALIZABLE_NAME = "No se puede finalizar la eliminación en el estado actual";
	public static final String ERROR_ELIMINACION_NO_EDITABLE_XESTADO_NAME = "No se puede editar la eliminación en el estado actual";
	public static final String ERROR_APROBACION_DIRECTA_ELIMINACION_NO_CREABLE_XUSUARIO_NAME = "El usuario no puede aprobar directamente la eliminación";
	public static final String ERROR_VALORACION_SOLO_PERMITIDO_A_GESTOR_NAME = "El usuario no es el gestor de la serie";
	public static final String ERROR_VALORACION_SOLO_PERMITIDO_A_GESTOR_VALORACIONES_NAME = "El usuario no tiene permisos suficientes";
	public static final String ERROR_NO_EXISTE_GESTOR_ESPECIFICADO_NAME = "No existe el gestor especificado";

	/** Nombres Errores USUARIOS_MODULE - 7xxx */

	/** Nombres Errores DOSSIER_MODULE - 8xxx */

	/** Nombres Errores EXPLOTACION_MODULE - 9xxx */

	/** Nombres Errores DIGITALIZACION_MODULE - 10xxx */

	/** Nombres Errores AUDITORIA_MODULE - 11xxx */

	/** Nombres Errores LOGIN_MODULE - 12xxx */
	public static final String ERROR_LOGIN_NO_VALIDO_NAME = "Login no válido";

	/** Errores ARCHIVOS_MODULE - 13xxx */
	public static final String ERROR_NO_SE_PUEDE_ELIMINAR_EL_ARCHIVO_NAME = "No se Puede eliminar el archivo";

	/** Errores ORGANIZACION_MODULE - 15xxx */

	/** Asociacion error/nombre */
	private static HashMap errorNames;

	private static HashMap mapCodeErrorModules;

	static {

		mapCodeErrorModules = new HashMap();
		mapCodeErrorModules.put(new Integer(ArchivoModules.DEPOSITOS_MODULE),
				new Integer(DEPOSITO_MODULE_ERROR));

		errorNames = new HashMap();

		/** TRANSFERENCIAS_MODULE */
		errorNames.put(new Integer(ERROR_NO_HAY_PREVISIONES_SELECCIONADAS),
				ERROR_NO_HAY_PREVISIONES_SELECCIONADAS_NAME);
		errorNames.put(new Integer(
				ERROR_GESTOR_ESTANDAR_CON_PREVISION_A_GESTIONAR),
				ERROR_GESTOR_ESTANDAR_CON_PREVISION_A_GESTIONAR_NAME);
		errorNames.put(new Integer(ERROR_NO_HAY_RELACIONES_SELECCIONADAS),
				ERROR_NO_HAY_RELACIONES_SELECCIONADAS_NAME);
		errorNames.put(new Integer(DETALLE_SIN_PROCEDIMIENTO_Y_SERIE),
				DETALLE_SIN_PROCEDIMIENTO_Y_SERIE_NAME);
		errorNames.put(new Integer(EXISTE_LINEA_DETALLE_MISMO_PROCEDIMIENTO),
				EXISTE_LINEA_DETALLE_MISMO_PROCEDIMIENTO_NAME);
		// errorNames.put(new Integer(ERROR_NO_TIENE_PERMISO_ALTA_ELEMENTO),
		// ERROR_NO_TIENE_PERMISO_ALTA_ELEMENTO_NAME);

		/** DEPOSITOS_MODULE */
		/**
		 * Meto las dependencias solo en esta clase, en lugar de hacer que las
		 * excepciones dependan de esta clase lo q supondria mayor numero de
		 * dependencias
		 */
		errorNames.put(new Integer(ERROR_ELEMENT_NOT_EMPTY),
				ERROR_ELEMENT_NOT_EMPTY_NAME);
		errorNames.put(new Integer(ERROR_NOT_LAST_CHILD),
				ERROR_NOT_LAST_CHILD_NAME);
		errorNames.put(new Integer(ERROR_LONGITUD_MENOR_Q_LONGITUD_FORMATO),
				ERROR_LONGITUD_MENOR_Q_LONGITUD_FORMATO_NAME);
		errorNames.put(new Integer(ERROR_LONGITUD_MENOR_Q_LONGITUD_FORMATO),
				ERROR_LONGITUD_MENOR_Q_LONGITUD_FORMATO_NAME);
		errorNames.put(
				new Integer(ERROR_CONTIENE_HUECOS_OCUPADOS_O_RESERVADOS),
				ERROR_CONTIENE_HUECOS_OCUPADOS_O_RESERVADOS_NAME);
		errorNames.put(new Integer(ERROR_ELEMENT_DELETED),
				ERROR_ELEMENT_DELETED_NAME);
		errorNames.put(new Integer(
				EL_HUECO_CONTIENE_UNIDADES_DOCS_DE_OTRO_FONDO),
				EL_HUECO_CONTIENE_UNIDADES_DOCS_DE_OTRO_FONDO_NAME);
		errorNames.put(new Integer(
				NO_SE_PUEDE_REUBICAR_EN_LA_MISMA_UNIDADINSTALACION),
				NO_SE_PUEDE_REUBICAR_EN_LA_MISMA_UNIDADINSTALACION_NAME);
		errorNames.put(new Integer(NO_SE_PUEDE_ELIMINAR_EL_FORMATO),
				NO_SE_PUEDE_ELIMINAR_EL_FORMATO_NAME);
		errorNames.put(
				new Integer(NO_SE_PUEDE_CREAR_IDENTIFICADOR_UNICO_HUECO),
				NO_SE_PUEDE_CREAR_IDENTIFICADOR_UNICO_HUECO_NAME);

		/** PRESTAMOS_MODULE */
		errorNames.put(new Integer(ERROR_ESTADO_NO_VALIDO),
				ERROR_ESTADO_NO_VALIDO_NAME);
		errorNames.put(new Integer(ERROR_DEVOLUCION_Y_NO_ENTREGADA),
				ERROR_DEVOLUCION_Y_NO_ENTREGADA_NAME);
		errorNames.put(new Integer(ERROR_ENVIO_SIN_DETALLES),
				ERROR_ENVIO_SIN_DETALLES_NAME);
		errorNames.put(new Integer(ERROR_AUTORIZACION_ESTADO_NO_VALIDO),
				ERROR_AUTORIZACION_ESTADO_NO_VALIDO_NAME);
		errorNames.put(new Integer(ERROR_AUTORIZACION_SIN_DETALLES),
				ERROR_AUTORIZACION_SIN_DETALLES_NAME);
		errorNames.put(new Integer(ERROR_ENTREGA_ESTADO_NO_VALIDO),
				ERROR_ENTREGA_ESTADO_NO_VALIDO_NAME);
		errorNames.put(new Integer(ERROR_ENTREGA_SIN_DETALLES),
				ERROR_ENTREGA_SIN_DETALLES_NAME);
		errorNames.put(new Integer(ERROR_DEVOLVER_ESTADO_NO_VALIDO),
				ERROR_DEVOLVER_ESTADO_NO_VALIDO_NAME);
		errorNames.put(new Integer(ERROR_RECLAMAR_ESTADO_NO_VALIDO),
				ERROR_RECLAMAR_ESTADO_NO_VALIDO_NAME);
		errorNames.put(new Integer(ERROR_CREACION_XOTRA),
				ERROR_CREACION_XOTRA_NAME);
		errorNames.put(new Integer(ERROR_MODIFICACION_XOTRA),
				ERROR_MODIFICACION_XOTRA_NAME);
		errorNames.put(new Integer(ERROR_EDICION_NOEDITABLE),
				ERROR_EDICION_NOEDITABLE_NAME);
		errorNames.put(new Integer(ERROR_ENVIO_FECHA_NO_VALIDA),
				ERROR_ENVIO_FECHA_NO_VALIDA_NAME);
		errorNames.put(new Integer(ERROR_ENVIO_ESTADO_NO_VALIDO),
				ERROR_ENVIO_ESTADO_NO_VALIDO_NAME);
		errorNames.put(new Integer(ERROR_PRORROGA_TRATADA),
				ERROR_PRORROGA_TRATADA_NAME);
		errorNames.put(new Integer(ERROR_ENTREGA_DETALLE_ESTADO_NO_VALIDO),
				ERROR_ENTREGA_DETALLE_ESTADO_NO_VALIDO_NAME);
		errorNames.put(new Integer(ERROR_PRESTAMO_NO_BORRABLE),
				ERROR_PRESTAMO_NO_BORRABLE_NAME);
		errorNames.put(new Integer(ERROR_AUTORIZAR_ARCHIVO_NO_VALIDO),
				ERROR_ENVIO_ARCHIVO_NO_VALIDO_NAME);
		errorNames.put(
				new Integer(ERROR_FINALIZARAUTORIZACION_ESTADO_NO_VALIDO),
				ERROR_FINALIZARAUTORIZACION_ESTADO_NO_VALIDO_NAME);
		errorNames.put(new Integer(ERROR_PRESTAMO_NO_RESERVADO),
				ERROR_PRESTAMO_NO_RESERVADO_NAME);
		errorNames.put(new Integer(ERROR_PRESTAMO_NO_ENTREGABLE_X_FECHA),
				ERROR_PRESTAMO_NO_ENTREGABLE_X_FECHA_NAME);
		errorNames.put(new Integer(ERROR_PRESTAMO_NO_ENTREGABLE_X_USUARIO),
				ERROR_PRESTAMO_NO_ENTREGABLE_X_USUARIO_NAME);
		errorNames.put(new Integer(ERROR_PRESTAMO_NO_AUTORIZADO),
				ERROR_PRESTAMO_NO_AUTORIZADO_NAME);
		errorNames.put(new Integer(ERROR_ENTREGA_ARCHIVO_NO_VALIDO),
				ERROR_ENTREGA_ARCHIVO_NO_VALIDO_NAME);
		errorNames.put(new Integer(ERROR_PRESTAMO_NO_ENTREGADO),
				ERROR_PRESTAMO_NO_ENTREGADO_NAME);
		errorNames.put(new Integer(ERROR_ACCION_NO_PERMITIDA),
				ERROR_ACCION_NO_PERMITIDA_NAME);
		errorNames.put(new Integer(ERROR_RECLAMAR_EN_FECHA_VALIDA),
				ERROR_RECLAMAR_EN_FECHA_VALIDA_NAME);
		errorNames.put(new Integer(ERROR_RECLAMAR_X_PRORROGA),
				ERROR_RECLAMAR_X_PRORROGA_NAME);
		errorNames.put(new Integer(ERROR_CREACIONCONSULTA_XOTRA),
				ERROR_CREACIONCONSULTA_XOTRA_NAME);
		errorNames.put(new Integer(ERROR_CONSULTA_NO_BORRABLE),
				ERROR_CONSULTA_NO_BORRABLE_NAME);
		errorNames.put(new Integer(ERROR_ENVIO_CONSULTA_FECHA_NO_VALIDA),
				ERROR_ENVIO_CONSULTA_FECHA_NO_VALIDA_NAME);
		errorNames.put(new Integer(ERROR_ENVIO_CONSULTA_ESTADO_NO_VALIDO),
				ERROR_ENVIO_CONSULTA_ESTADO_NO_VALIDO_NAME);
		errorNames.put(new Integer(ERROR_EDICION_CONSULTA_NOEDITABLE),
				ERROR_EDICION_CONSULTA_NOEDITABLE_NAME);
		errorNames.put(new Integer(ERROR_CONSULTA_NO_RESERVADA),
				ERROR_CONSULTA_NO_RESERVADA_NAME);
		errorNames.put(new Integer(ERROR_CONSULTA_NO_ENTREGABLE_X_FECHA),
				ERROR_CONSULTA_NO_ENTREGABLE_X_FECHA_NAME);
		errorNames.put(new Integer(ERROR_CONSULTA_NO_ENTREGABLE_X_USUARIO),
				ERROR_CONSULTA_NO_ENTREGABLE_X_USUARIO_NAME);
		errorNames.put(new Integer(ERROR_CONSULTA_NO_AUTORIZADA),
				ERROR_CONSULTA_NO_AUTORIZADA_NAME);
		errorNames.put(new Integer(ERROR_CESION_PRESTAMO_NO_ORGANIZACION),
				ERROR_CESION_PRESTAMO_NO_ORGANIZACION_NAME);

		/** DESCRIPCION_MODULE */
		errorNames.put(new Integer(ERROR_DEFINICION_FICHA_NO_BIEN_FORMADA),
				ERROR_DEFINICION_FICHA_NO_BIEN_FORMADA_NAME);
		errorNames.put(new Integer(ERROR_CAMPO_FORMULARIO_VACIO),
				ERROR_CAMPO_FORMULARIO_VACIO_NAME);

		/** CLASIFICACION_MODULE */

		/** SERIES_MODULE */
		errorNames.put(new Integer(
				ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XESTADO),
				ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XESTADO_NAME);
		errorNames.put(new Integer(
				ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XUSUARIO),
				ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XUSUARIO_NAME);
		errorNames.put(new Integer(
				ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XVALORADM),
				ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XVALORADM_NAME);
		errorNames.put(new Integer(
				ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XVALORLEGAL),
				ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XVALORLEGAL_NAME);
		errorNames.put(new Integer(
				ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XREGACCESO),
				ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XREGACCESO_NAME);
		errorNames.put(new Integer(
				ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XVALORDICTAMEN),
				ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XVALORDICTAMEN_NAME);
		errorNames.put(new Integer(ERROR_VALORACION_NOAUTORIZABLE_XESTADO),
				ERROR_VALORACION_NOAUTORIZABLE_XESTADO_NAME);
		errorNames.put(new Integer(ERROR_VALORACION_NORECHAZABLE_XESTADO),
				ERROR_VALORACION_NORECHAZABLE_XESTADO_NAME);
		errorNames.put(new Integer(ERROR_VALORACION_NO_BORRABLE),
				ERROR_VALORACION_NO_BORRABLE_NAME);
		errorNames.put(new Integer(ERROR_VALORACION_NO_CREABLE_XESTADO),
				ERROR_VALORACION_NO_CREABLE_XESTADO_NAME);
		errorNames.put(new Integer(ERROR_VALORACION_NO_CREABLE_XESTADO),
				ERROR_VALORACION_NO_CREABLE_XESTADO_VALORACION_NAME);
		errorNames.put(new Integer(ERROR_VALORACION_NO_MODIFICABLE_XESTADO),
				ERROR_VALORACION_NO_MODIFICABLE_XESTADO_NAME);
		errorNames.put(new Integer(ERROR_VALORACION_NOEVALUABLE_XESTADO),
				ERROR_VALORACION_NOEVALUABLE_XESTADO_NAME);
		errorNames.put(new Integer(ERROR_VALORACION_NODICTAMINABLE_XESTADO),
				ERROR_VALORACION_NODICTAMINABLE_XESTADO_NAME);
		errorNames.put(new Integer(ERROR_VALORACION_NO_CREABLE_XUSUARIO),
				ERROR_VALORACION_NO_CREABLE_XUSUARIO_NAME);
		errorNames.put(new Integer(ERROR_ELIMINACION_NO_CREABLE_XESTADO),
				ERROR_ELIMINACION_NO_CREABLE_XESTADO_NAME);
		errorNames.put(new Integer(
				ERROR_ELIMINACION_NO_CREABLE_XESTADO_ELIMINACION),
				ERROR_ELIMINACION_NO_CREABLE_XESTADO_ELIMINACION_NAME);
		errorNames.put(new Integer(
				ERROR_SOLICITUD_APROBACION_NO_CREABLE_XESTADO),
				ERROR_SOLICITUD_APROBACION_NO_CREABLE_XESTADO_NAME);
		errorNames.put(new Integer(
				ERROR_APROBACION_ELIMINACION_NO_CREABLE_XESTADO),
				ERROR_APROBACION_ELIMINACION_NO_CREABLE_XESTADO_NAME);
		errorNames.put(
				new Integer(ERROR_RECHAZO_ELIMINACION_NO_CREABLE_XESTADO),
				ERROR_RECHAZO_ELIMINACION_NO_CREABLE_XESTADO_NAME);
		errorNames.put(new Integer(ERROR_ELIMINACION_NO_BORRABLE),
				ERROR_ELIMINACION_NO_BORRABLE_NAME);
		errorNames.put(new Integer(ERROR_ELIMINACION_NO_EJECUTABLE),
				ERROR_ELIMINACION_NO_EJECUTABLE_NAME);
		errorNames.put(new Integer(ERROR_ELIMINACION_NO_DESTRUIBLE),
				ERROR_ELIMINACION_NO_DESTRUIBLE_NAME);
		errorNames.put(new Integer(ERROR_ELIMINACION_NO_FINALIZABLE),
				ERROR_ELIMINACION_NO_FINALIZABLE_NAME);
		errorNames.put(new Integer(ERROR_ELIMINACION_NO_EDITABLE_XESTADO),
				ERROR_ELIMINACION_NO_EDITABLE_XESTADO_NAME);
		errorNames.put(new Integer(
				ERROR_APROBACION_DIRECTA_ELIMINACION_NO_CREABLE_XUSUARIO),
				ERROR_APROBACION_DIRECTA_ELIMINACION_NO_CREABLE_XUSUARIO_NAME);
		errorNames.put(new Integer(ERROR_VALORACION_SOLO_PERMITIDO_A_GESTOR),
				ERROR_VALORACION_SOLO_PERMITIDO_A_GESTOR_NAME);
		errorNames.put(new Integer(
				ERROR_VALORACION_SOLO_PERMITIDO_A_GESTOR_VALORACIONES),
				ERROR_VALORACION_SOLO_PERMITIDO_A_GESTOR_VALORACIONES_NAME);
		errorNames.put(new Integer(ERROR_NO_EXISTE_GESTOR_ESPECIFICADO),
				ERROR_NO_EXISTE_GESTOR_ESPECIFICADO_NAME);

		/** USUARIOS_MODULE */

		/** DOSSIER_MODULE */

		/** EXPLOTACION_MODULE */

		/** DIGITALIZACION_MODULE */

		/** AUDITORIA_MODULE */

		/** LOGIN_MODULE */
		errorNames.put(new Integer(ERROR_LOGIN_NO_VALIDO),
				ERROR_LOGIN_NO_VALIDO_NAME);

		/** ARCHIVOS_MODULE */
		errorNames.put(new Integer(ERROR_NO_SE_PUEDE_ELIMINAR_EL_ARCHIVO),
				ERROR_NO_SE_PUEDE_ELIMINAR_EL_ARCHIVO_NAME);

	}

	/**
	 * Devuelve el nombre del error a partir de la cte de identificación del
	 * mismo.
	 * 
	 * @param error
	 *            Cte de identificacion del error
	 * @return Nombre del error asociado a la cte de identificacion
	 */
	public static String getErrorName(int error) {
		return (String) errorNames.get(new Integer(error));
	}

	/**
	 * Devuelve un listado de los errores existentes en la aplicacion con su cte
	 * de identificación, y su nombre.
	 * 
	 * @return Listado de los errores{@link ArchivoError} de la aplicación.
	 */
	public static Collection getErrors() {
		Integer i = null;
		String name = null;
		ArchivoError error = null;
		ArrayList result = new ArrayList();
		Iterator it = errorNames.keySet().iterator();

		while (it.hasNext()) {
			i = (Integer) it.next();

			name = (String) errorNames.get(i);

			error = new ArchivoError();
			error.setId(i.intValue());
			error.setName(name);

			result.add(error);
		}

		return result;
	}

	/**
	 * De momento hago un mapeo aqui de archivo modules a codigos de error. Lo
	 * correcto seria que no fuese necesario,hacer que los codigos de error
	 * comienzen por el numero del modulo de pegarle un repaso mas adelante
	 * 
	 * @param archivoModule
	 * @param codeError
	 * @return
	 */
	public static Integer composeCodeError(int archivoModule, int codeError) {
		Integer codeErrorModule = (Integer) mapCodeErrorModules
				.get(new Integer(archivoModule));
		if (codeErrorModule != null) {
			StringBuffer buff = new StringBuffer().append(
					String.valueOf(codeErrorModule)).append(
					StringUtils.leftPad(String.valueOf(codeError), 3, '0'));
			return new Integer(buff.toString());
		}
		return new Integer(ERROR_DESCONOCIDO);
	}

	public static int composeCodeErrorInt(int archivoModule, int codeError) {
		return composeCodeError(archivoModule, codeError).intValue();
	}

}
