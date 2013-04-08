package valoracion;


/**
 * Ctes de la tabla de Valoración
 */
public class ValoracionConstants {
    /** Operadores busqueda fechas */
    public final static String OPERATOR_IGUAL = "=";
    public final static String OPERATOR_MAYOR_IGUAL = ">=";
    public final static String OPERATOR_MAYOR = ">";
    public final static String OPERATOR_MENOR = "<";
    public final static String OPERATOR_MENOR_IGUAL = "<=";
    public final static String OPERATOR_RANGE = "[..]";

    /** Seleccion de Unidades Documentales*/
    public final static String SELECCION_UDOC_NO = "N";
    public final static String SELECCION_UDOC_SI = "S";

    /** Tipos de serie */
    public final static int TIPO_SERIE_PRECEDENTES = 1;
//    public final static int TIPO_SERIE_DESCENDIENTES = 2;
    public final static int TIPO_SERIE_RELACIONADAS = 3;

   /** Estados Eliminación*/
   public final static int ESTADO_ELIMINACION_ABIERTA = 1;
   public final static int ESTADO_ELIMINACION_PENDIENTE_DE_APROBAR = 2;
   public final static int ESTADO_ELIMINACION_RECHAZADA = 3;
   public final static int ESTADO_ELIMINACION_APROBADA = 4;
   public final static int ESTADO_ELIMINACION_PENDIENTE_DESTRUCCION = 5;
   public final static int ESTADO_ELIMINACION_DESTRUCCION_REALIZADA = 6;
   public final static int ESTADO_ELIMINACION_PENDIENTE_FINALIZACION = 7;
   public final static int ESTADO_ELIMINACION_FINALIZADA = 8;

   /** Tipos eliminación*/
   public final static int TIPO_ELIMINACION_PARCIAL = 1;
   public final static int TIPO_ELIMINACION_TOTAL = 2;

   /** Niveles de ordenación de series*/
   public final static int NIVEL_SERIE_PRIMERO = 1;
   public final static int NIVEL_SERIE_SEGUNDO = 2;

   /** Tipos de Valores de Valoracion*/
//   public final static int VALOR_ADMINISTRATIVO = 1;
//   public final static int VALOR_LEGAL = 2;
//   public final static int VALOR_INFORMATIVO = 3;
//   public final static int VALOR_HISTORICO = 4;

   /** Tipos de validación de una Valoración*/
   public final static int VALIDACION_NODIRECTA = 1;
   public final static int VALIDACION_DIRECTA = 2;

   public final static String ACTUALIZACION_DATOS = "1";
   /** KEYS */
   public final static String LISTA_FONDOS_KEY = "LISTA_FONDOS_KEY";
   public final static String FONDO_KEY = "FONDO_KEY";
   public final static String LISTA_ORDENACIONES_PRIMER_NIVEL_KEY = "LISTA_ORDENACIONES_PRIMER_NIVEL_KEY";
   public final static String LISTA_ORDENACIONES_SEGUNDO_NIVEL_KEY = "LISTA_ORDENACIONES_SEGUNDO_NIVEL_KEY";
   public final static String LISTA_VALORES_ADMINISTRATIVOS_KEY = "LISTA_VALORES_ADMINISTRATIVOS_KEY";
   public final static String LISTA_VALORES_LEGALES_KEY = "LISTA_VALORES_LEGALES_KEY";
   public final static String LISTA_VALORES_INFORMATIVOS_KEY = "LISTA_VALORES_INFORMATIVOS_KEY";
   public final static String LISTA_VALORES_HISTORICOS_KEY = "LISTA_VALORES_HISTORICOS_KEY";
   public final static String LISTA_TECNICAS_MUESTREO_KEY = "LISTA_TECNICAS_MUESTREO_KEY";
   public final static String LISTA_VALORES_DICTAMEN_KEY = "LISTA_VALORES_DICTAMEN_KEY";
   public final static String LISTA_SERIES_KEY = "LISTA_SERIES_KEY";
   public final static String SERIE_KEY = "SERIE_KEY";
   public final static String VALORACION_KEY = "VALORACION_KEY";
   public final static String VALORACION_ACTUAL_KEY = "VALORACION_ACTUAL_KEY";
   public static final String TIPO_SERIES_EN_EDICION_KEY = "TIPO_SERIES_EN_EDICION_KEY";
//   public static final String LISTA_SERIES_EN_EDICION_KEY = "LISTA_SERIES_EN_EDICION_KEY";
//   public final static String LISTA_SERIES_PRECEDENTES_KEY = "LISTA_SERIES_PRECEDENTES_KEY";
//   public final static String LISTA_SERIES_RELACIONADAS_KEY = "LISTA_SERIES_RELACIONADAS_KEY";
//   public final static String LISTA_SERIES_DESCENDIENTES_KEY = "LISTA_SERIES_DESCENDIENTES_KEY";
   public static final String LISTA_VALORACIONES_EN_ELABORACION_KEY = "LISTA_VALORACIONES_EN_ELABORACION_KEY";
   public static final String LISTA_VALORACIONES_A_GESTIONAR_KEY = "LISTA_VALORACIONES_A_GESTIONAR_KEY";
   public final static String LISTA_VALORACIONES_KEY = "LISTA_VALORACIONES_KEY";
   public final static String LISTA_ELIMINACIONES_KEY = "LISTA_ELIMINACIONES_KEY";
   public final static String LISTA_CRITERIOS_KEY = "LISTA_CRITERIOS_KEY";
   public final static String ELIMINACION_KEY = "ELIMINACION_KEY";
   public final static String ELIMINACION_OCULTAR_FINALIZAR_ELIMINACION_KEY = "ELIMINACION_OCULTAR_FINALIZAR_ELIMINACION_KEY";
   public final static String ELIMINACION_NUM_UDOCS_CONSERVAR_KEY = "ELIMINACION_NUM_UDOCS_CONSERVAR_KEY";
   public final static String ELIMINACION_UDOCS_CONSERVAR_KEY = "ELIMINACION_UDOCS_CONSERVAR_KEY";
   public final static String LISTA_UDOCS_KEY = "LISTA_UDOCS_KEY";
   public final static String VER_BOTON_INICIAR_VALORACION = "VER_BOTON_INICIAR_VALORACION";
   public final static String VER_BOTON_INICIAR_ELIMINACION = "VER_BOTON_INICIAR_ELIMINACION";
   public final static String VER_BOTON_VER_ELIMINACION = "VER_BOTON_VER_ELIMINACION";
   public final static String VER_BOTON_VER_VALORACION = "VER_BOTON_VER_VALORACION";
   public final static String LISTA_BOLETINES_OFICIALES_KEY = "LISTA_BOLETINES_OFICIALES_KEY";
   public final static String CRITERIOS_MODIFICADOS_KEY = "CRITERIOS_MODIFICADOS_KEY";
   public final static String IDS_ELIMINACION_KEY = "IDS_ELIMINACION_KEY";
   public final static String ELIMINACION_CON_UI_PARCIALES_KEY = "ELIMINACION_CON_UI_PARCIALES_KEY";
   public final static String VER_UI_PARCIALES_KEY = "VER_UI_PARCIALES_KEY";


   /** KEYS con los mensajes de error en resources*/
   public final static String ERROR_VALORACION_NO_CREABLE_XESTADO_NAME = "errors.valoracion.valoracionNoCreable.xestado";
   public final static String ERROR_VALORACION_NO_CREABLE_XESTADO_VALORACION_NAME = "errors.valoracion.valoracionNoCreable.xestadoValoracion";
   public final static String ERROR_VALORACION_NO_CREABLE_XUSUARIO_NAME = "errors.valoracion.valoracionNoCreable.xUsuario";
   public final static String ERROR_VALORACION_SOLO_PERMITIDO_A_GESTOR_NAME = "errors.valoracion.valoracionNoModificable.xUsuario";

   public final static String ERROR_ELIMINACION_NO_CREABLE_XESTADO_NAME = "errors.eliminacion.eliminacionNoCreable.xestado";
   public final static String ERROR_ELIMINACION_NO_CREABLE_XESTADO_ELIMINACION_NAME = "errors.eliminacion.eliminacionNoCreable.xestadoEliminacion";
   public final static String ERROR_SOLICITUD_APROBACION_NO_CREABLE_XESTADO_NAME = "errors.eliminacion.solicitudAprobacion.NoCreable.xestado";
   public final static String ERROR_APROBACION_ELIMINACION_NO_CREABLE_XESTADO_NAME = "errors.eliminacion.aprobacion.NoCreable.xestado";
   public final static String ERROR_RECHAZO_ELIMINACION_NO_CREABLE_XESTADO_NAME = "errors.eliminacion.rechazo.NoCreable.xestado";
   public final static String ERROR_ELIMINACION_NO_EJECUTABLE_NAME = "errors.eliminacion.NoEjcutable";
   public final static String ERROR_ELIMINACION_NO_DESTRUIBLE_NAME = "errors.eliminacion.NoDestruible";
   public final static String ERROR_ELIMINACION_NO_FINALIZABLE_NAME = "errors.eliminacion.NoFinalizable";
   public final static String ERROR_VALORACION_FECHA_EJECUCION_NO_VALIDA="errors.eliminacion.fechaEjecucion.noValida";
   public static final String ERROR_USUARIO_SIN_ARCHIVO="archigest.archivo.valoraciones.usuarioSinArchivo";
   public final static String ERROR_VALORACION_VALORDICTAMEN_NO_VALIDO="errors.valoracion.valorDictamen.noValido";
   public final static String ERROR_VALORACION_VALORINFORMATIVO_SI="error.valoracion.valor_informativo_si";
   public final static String ERROR_VALORACION_VALORDICTAMEN_NO_VALIDO_2="errors.valoracion.valorDictamen.noValido2";
   public final static String ERROR_ELIMINACION_NO_DESTRUCCION_NAME = "errors.eliminacion.destruccion.no.realizada";
   public final static String ERROR_ELIMINACION_THREAD_EJECUTANDOSE_NAME = "errors.eliminacion.thread.ejecutandose";

   public final static String ERROR_ELIMINACION_NO_EJECUTABLE_SIN_PERMISOS_NAME = "archigest.archivo.eliminacion.NoEjecutable.sin.permisos";

   public final static String ERROR_VALORACION_RESTO_CAMPOSFILA_OBLIGATORIOS="archigest.valoraciones.plazos.error.restoCamposFilaObligatorios";
   public final static String ERROR_VALORACION_PLAZOS_CAMPOVACIO="archigest.valoraciones.plazos.error.campoVacioEnFila";
   public final static String ERROR_VALORACION_PLAZOS_PLAZOANIOS_NONUMERICO="archigest.valoraciones.plazos.plazoAniosNoNumerico";
   public final static String ERROR_VALORACION_ORDENORIGEN_MAYORIGUAL_ORDENDESTINO="archigest.valoraciones.plazos.error.ordenNivelOrigenMayorIgualNivelDestino";
   public final static String ERROR_VALORACION_ORDENORIGENANTERIOR_MAYORIGUAL_ORDENORIGEN="archigest.valoraciones.plazos.error.ordenNivelOrigenAnteriorMayorIgualNivelOrigen";
   public final static String ERROR_GENERAL_FILA_NO_ENTERO_CORTO="archigest.error.fila.noEnteroCorto";
   public final static String ERROR_GENERAL_FILA_NO_ENTERO_POSITIVO="archigest.error.fila.noEnteroPositivo";

   public final static String ERROR_SELECCION_NO_UNIDADES_A_RESTRINGIR="archigest.archivo.seleccion.noUnidadesARestringir";

   //Labels
   public final static String LABEL_VALORACION_PLAZOS_TITULO="archigest.valoraciones.plazos.transferencias";
   public final static String LABEL_VALORACION_PLAZOS_NIVELORIGEN="archigest.valoraciones.plazos.nivelArchivoOrigen";
   public final static String LABEL_VALORACION_PLAZOS_NIVELDESTINO="archigest.valoraciones.plazos.nivelArchivoDestino";
   public final static String LABEL_VALORACION_PLAZOS_PLAZOANIOS="archigest.valoraciones.plazo.años";
   public final static String LABEL_VALORACIONES_ELIMINACION_FECHA_EJECUCION="archigest.archivo.eliminacion.fechaEjecucion";
   public final static String LABEL_VALORACIONES_ELIMINACION_UNIDADES_SIN_FECHAS="archigest.archivo.eliminacion.unidades.sin.fechas";
   public final static String LABEL_VALORACIONES_SELECCION_NUMERO_MINIMO_UNIDADES="archigest.archivo.seleccion.numeroMinimoUnidades";
   public final static String LABEL_VALORACIONES_FECHA_EVALUACION="archigest.archivo.valoracion.fechaEvaluacion";


   //Labels informes
   public final static String LABEL_INFORMES_INFORMEELIMINACION_TITULO="archigest.archivo.informe.informeEliminacion.titulo";
   public final static String LABEL_INFORMES_INFORMEELIMINACION_NUMERO="archigest.archivo.informe.informeEliminacion.numero";
   public final static String LABEL_INFORMES_INFORMEELIMINACION_SIGNATURA="archigest.archivo.informe.informeEliminacion.signatura";
   public final static String LABEL_INFORMES_INFORMEELIMINACION_EXPEDIENTE="archigest.archivo.informe.informeEliminacion.expediente";
   public final static String LABEL_INFORMES_INFORMEELIMINACION_TITULO_SEL="archigest.archivo.informe.informeEliminacion.tituloSel";
   public final static String LABEL_INFORMES_INFORMEELIMINACION_FECHA_INICIAL="archigest.archivo.informe.informeEliminacion.fecha.inicial";
   public final static String LABEL_INFORMES_INFORMEELIMINACION_FECHA_FINAL="archigest.archivo.informe.informeEliminacion.fecha.final";
   public final static String LABEL_INFORMES_INFORMESELECCION_TITULO="archigest.archivo.informe.informeSeleccion.titulo";
   public final static String LABEL_INFORMES_INFORMESELECCION_NUMERO="archigest.archivo.informe.informeSeleccion.numero";
   public final static String LABEL_INFORMES_INFORMESELECCION_SIGNATURA="archigest.archivo.informe.informeSeleccion.signatura";
   public final static String LABEL_INFORMES_INFORMESELECCION_CODIGO="archigest.archivo.informe.informeSeleccion.codigo";
   public final static String LABEL_INFORMES_INFORMESELECCION_EXPEDIENTE="archigest.archivo.informe.informeSeleccion.expediente";
   public final static String LABEL_INFORMES_INFORMESELECCION_TITULO_SEL="archigest.archivo.informe.informeSeleccion.tituloSel";
   public final static String LABEL_INFORMES_INFORMESELECCION_UBICACION="archigest.archivo.informe.informeSeleccion.ubicacion";
   public final static String LABEL_INFORMES_INFORMESELECCION_FECHA_INICIAL="archigest.archivo.informe.informeSeleccion.fecha.inicial";
   public final static String LABEL_INFORMES_INFORMESELECCION_FECHA_FINAL="archigest.archivo.informe.informeSeleccion.fecha.final";
   public final static String LABEL_INFORMES_INFORMEVALORACION_PREFIJO_TITULO="archigest.archivo.informeValoracion.prefijoTitulo";
   public final static String LABEL_INFORMES_INFORMEVALORACION_ASUNTO="archigest.archivo.informeValoracion.asunto";
   public final static String LABEL_INFORMES_INFORMEVALORACION_TITULO="archigest.archivo.informeValoracion.titulo";
   public final static String LABEL_INFORMES_INFORMEVALORACION_DATOS_SERIE="archigest.archivo.informeValoracion.datosSerie";
   public final static String LABEL_INFORMES_INFORMEVALORACION_CONTEXTO="archigest.archivo.informeValoracion.contexto";
   public final static String LABEL_INFORMES_INFORMEVALORACION_SERIE="archigest.archivo.informeValoracion.serie";
   public final static String LABEL_INFORMES_INFORMEVALORACION_DATOS_PROCEDIMIENTO="archigest.archivo.informeValoracion.datosProcedimiento";
   public final static String LABEL_INFORMES_INFORMEVALORACION_CODIGO_PROCEDIMIENTO="archigest.archivo.informeValoracion.codigoProcedimiento";
   public final static String LABEL_INFORMES_INFORMEVALORACION_TITULO_PROCEDIMIENTO="archigest.archivo.informeValoracion.tituloProcedimiento";
   public final static String LABEL_INFORMES_INFORMEVALORACION_CONTENIDO_SERIE="archigest.archivo.informeValoracion.contenidoSerie";
   public final static String LABEL_INFORMES_INFORMEVALORACION_DEFINICION_SERIE="archigest.archivo.identificacion.definicion";
   public final static String LABEL_INFORMES_INFORMEVALORACION_TRAMITES_SERIE="archigest.archivo.identificacion.tramites";
   public final static String LABEL_INFORMES_INFORMEVALORACION_NORMATIVA_SERIE="archigest.archivo.identificacion.normativa";
   public final static String LABEL_INFORMES_INFORMEVALORACION_DOCUMENTOS_BASICOS_SERIE=  "archigest.archivo.informeValoracion.documentosBasicosSerie";
   public final static String LABEL_INFORMES_INFORMEVALORACION_DESCRIPCION_FISICA="archigest.archivo.informeValoracion.descripcionFisica";
   public final static String LABEL_INFORMES_INFORMEVALORACION_NUM_UDOCS="archigest.archivo.informeValoracion.nudocs";
   public final static String LABEL_INFORMES_INFORMEVALORACION_NUM_UINST="archigest.archivo.informeValoracion.nuinst";
   public final static String LABEL_INFORMES_INFORMEVALORACION_VOLUMEN="archigest.archivo.informeValoracion.volumen";
   public final static String LABEL_INFORMES_INFORMEVALORACION_FECHAS_EXTREMAS="archigest.archivo.informeValoracion.fechasExtremas";
   public final static String LABEL_INFORMES_INFORMEVALORACION_FECHA_INICIAL="archigest.archivo.informeValoracion.fechaInicial";
   public final static String LABEL_INFORMES_INFORMEVALORACION_FECHA_FINAL="archigest.archivo.informeValoracion.fechaFinal";
   public final static String LABEL_INFORMES_INFORMEVALORACION_PRODUCTORES_VIGENTES="archigest.archivo.informeValoracion.productoresVigentes";
   public final static String LABEL_INFORMES_INFORMEVALORACION_NOMBRE_PRODUCTOR="archigest.archivo.informeValoracion.nombreProductor";
   public final static String LABEL_INFORMES_INFORMEVALORACION_TIPO_PRODUCTOR="archigest.archivo.informeValoracion.tipoProductor";
   public final static String LABEL_INFORMES_INFORMEVALORACION_TIPO_PRODUCTOR_E="archigest.archivo.informeValoracion.tipoProductorE";
   public final static String LABEL_INFORMES_INFORMEVALORACION_TIPO_PRODUCTOR_O="archigest.archivo.informeValoracion.tipoProductorO";
   public final static String LABEL_INFORMES_INFORMEVALORACION_NO_HAY_PRODUCTORES_V="archigest.archivo.informeValoracion.noHayProductoresV";
   public final static String LABEL_INFORMES_INFORMEVALORACION_PRODUCTORES_HISTORICOS="archigest.archivo.informeValoracion.productoresHistoricos";
   public final static String LABEL_INFORMES_INFORMEVALORACION_NO_HAY_PRODUCTORES_H="archigest.archivo.informeValoracion.noHayProductoresH";
   public final static String LABEL_INFORMES_INFORMEVALORACION_DATOS_VALORACION="archigest.archivo.informeValoracion.datosValoracion";
   public final static String LABEL_INFORMES_INFORMEVALORACION_TITULO_VALORACION="archigest.archivo.informeValoracion.tituloValoracion";
   public final static String LABEL_INFORMES_INFORMEVALORACION_ESTADO_VALORACION="archigest.archivo.informeValoracion.estadoValoracion";
   public final static String LABEL_INFORMES_INFORMEVALORACION_MOTIVO_RECHAZO="archigest.archivo.informeValoracion.motivoRechazo";
   public final static String LABEL_INFORMES_INFORMEVALORACION_PERIODO_VIGENCIA_ANIOS="archigest.archivo.informeValoracion.periodoVigenciaAnios";
   public final static String LABEL_INFORMES_INFORMEVALORACION_CODIGO_SERIE="archigest.archivo.informeValoracion.codigoSerie";
   public final static String LABEL_INFORMES_INFORMEVALORACION_TITULO_SERIE="archigest.archivo.informeValoracion.tituloSerie";
   public final static String LABEL_INFORMES_INFORMEVALORACION_VISTO_BUENO="archigest.archivo.informeValoracion.vistoBueno";

   public static final String PREFIX_KEY_VALORES_ADMINISTRATIVOS = "archigest.archivo.valoracion.valorAdministrativo.";
   public static final String PREFIX_KEY_VALORES_LEGALES = "archigest.archivo.valoracion.valorLegal.";
   public static final String PREFIX_KEY_VALORES_DICTAMEN = "archigest.archivo.valoracion.valorDictamen";
   public static final String PREFIX_KEY_VALORACIONES= "archigest.archivo.valoracion.estado";

   public final static String LABEL_VALORACION_ORDENACION_PRIMER_NIVEL="archigest.archivo.valoracion.ordenacionPrimerNivel";
   public final static String LABEL_VALORACION_ORDENACION_SEGUNDO_NIVEL="archigest.archivo.valoracion.ordenacionSegundoNivel";
   public final static String LABEL_VALORACION_ORDENACION="archigest.archivo.valoracion.ordenacion";
   public final static String LABEL_VALORACION_VALOR_ADMINISTRTIVO="archigest.archivo.valoracion.valorAdministrativo";
   public final static String LABEL_VALORACION_JUSTIFICACION="archigest.archivo.valoracion.justificacion";
   public final static String LABEL_VALORACION_VALOR_LEGAL="archigest.archivo.valoracion.valorLegal";
   public final static String LABEL_VALORACION_VALOR_INFORMATIVO="archigest.archivo.valoracion.valorInformativo";
   public final static String LABEL_VALORACION_PERIODO_REGIMEN_ACCESO="archigest.archivo.valoracion.periodoRegimenAcceso";
   public final static String LABEL_VALORACION_VALORES_DICTAMEN="archigest.archivo.valoracion.valoresDictamen";
   public final static String LABEL_VALORACION_TECNICA_MUESTREO="archigest.archivo.valoracion.tecnicaMuestreo";
   public final static String LABEL_VALORACION_SERIES_PRECEDENTES="archigest.archivo.valoracion.seriesPrecedentes";
   public final static String LABEL_VALORACION_MSG_NO_SERIES_PRECEDENTES="archigest.archivo.valoracion.msgNoSeriesPrecedentes";
   public final static String LABEL_VALORACION_SERIES_RELACIONADAS="archigest.archivo.valoracion.seriesRelacionadas";
   public final static String LABEL_VALORACION_MSG_NO_SERIES_RELACIONADAS="archigest.archivo.valoracion.msgNoSeriesRelacionadas";
   public final static String LABEL_VALORACION_TITULO="archigest.archivo.valoracion.titulo";
   public final static String LABEL_VALORACION_FECHA_VALIDACION="archigest.archivo.valoracion.fechaValidacion";
   public final static String LABEL_VALORACION_FECHA_DICTAMEN="archigest.archivo.valoracion.fechaDictamen";
   public final static String LABEL_VALORACION_FECHA_RESOLUCION_DICTAMEN="archigest.archivo.valoracion.fechaResolucionDictamen";
   public final static String LABEL_VALORACION_BOLETIN_DICTAMEN="archigest.archivo.valoracion.boletinDictamen";
   public final static String LABEL_VALORACION_FECHA_BOLETIN_DICTAMEN="archigest.archivo.valoracion.fechaBoletinDictamen";
   public final static String LABEL_VALORACION_MOTIVO_RECHAZO="archigest.archivo.valoracion.motivoRechazo";

   public final static String LABEL_VALORACION_REGIMEN_ACCESO_TEMPORAL_PARCIAL="archigest.archivo.valoracion.regimenAcceso.temporalParcial";
   public final static String LABEL_VALORACION_REGIMEN_ACCESO_TEMPORAL_TOTAL="archigest.archivo.valoracion.regimenAcceso.temporalTotal";

   public final static String LABEL_VALORACION_ELIMINACION_BUSCAR_ESTADO="archigest.archivo.eliminacion.buscar.estado";
   public final static String LABEL_VALORACION_ELIMINACION_TITULO="archigest.archivo.eliminacion.titulo";
   public final static String LABEL_VALORACION_ELIMINACION_ESTADO="archigest.archivo.eliminacion.estado";

   public static final String LABEL_INFORME_SELECCION_FIRMA1 = "archigest.archivo.informe.seleccion.firma1";
   public static final String LABEL_INFORME_SELECCION_FIRMA2 = "archigest.archivo.informe.seleccion.firma2";

   public static final String LABEL_INFORME_SELECCION_COMPLETAS = "archigest.archivo.seleccion.ui.completas";
   public static final String LABEL_INFORME_SELECCION_PARCIALES = "archigest.archivo.seleccion.ui.parciales";
   public static final String LABEL_INFORME_SELECCION_UINSTALACION 	= "archigest.archivo.seleccion.ui.afectadas";
   public static final String LABEL_INFORME_SELECCION_NUMUDOCS 		= "archigest.archivo.seleccion.udoc.a.eliminar";


   public static final String LABEL_FINALIZACION_ELIMINACION = "archigest.archivo.seleccion.lanzada.eliminacion";
   public static final String ERROR_FINALIZACION_ELIMINACION = "archigest.archivo.error.seleccion.eliminacion";
   public static final String LABEL_FINALIZACION_ELIMINACION_EN_PROCESO = "archigest.archivo.seleccion.eliminacion.en.proceso";

   public static final String PERMISO_ELIMINACION_CUADRO_CLASIFICACION = "archigest.archivo.permiso.507";
   public static final String PERMISO_EDICION_DOCUMENTOS_ELECTRONICOS	= "archigest.archivo.permiso.901";
}
