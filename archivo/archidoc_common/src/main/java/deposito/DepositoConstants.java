package deposito;

import java.util.Map;

import common.SigiaUtilConstants;



public class DepositoConstants {
//    public static final Map depositoVO = SigiaUtilConstants.getConstantsMap(DepositoVO.class);

    public static final String DEPOSITO_VIEW_NAME = "VISTA_ESTRUCTURA_DEPOSITO_KEY";
    public static final String DEPOSITO_SEL_VIEW_NAME = "VISTA_ESTRUCTURA_DEPOSITO_SELECCION_KEY";
    public static final String DEPOSITO_SUBVIEW_NAME = "VISTA_SUBESTRUCTURA_DEPOSITO_KEY";
    public static final String DEPOSITO_SEL_SUBVIEW_NAME = "VISTA_SUBESTRUCTURA_DEPOSITO_SELECCION_KEY";

    /**
     * Identificador del ArchivoVO por defecto
     */
    public static final String ARCHIVO_DEFAULT = "ARCHIVO_DEFAULT";


    public static final String AMBITO_DEPOSITO_VIEW_NAME = "VISTA_ESTRUCTURA_AMBITO_DEPOSITO_KEY";
    public static final String AMBITO_DEPOSITO_SEL_VIEW_NAME = "VISTA_ESTRUCTURA_AMBITO_DEPOSITO_SELECCION_KEY";
    public static final String AMBITO_DEPOSITO_SUBVIEW_NAME = "VISTA_SUBESTRUCTURA_AMBITO_DEPOSITO_KEY";
    public static final String AMBITO_DEPOSITO_SEL_SUBVIEW_NAME = "VISTA_SUBESTRUCTURA_AMBITO_DEPOSITO_SELECCION_KEY";


    public static final String EDIFICIO_KEY = "EDIFICIO_KEY";
    public static final String ID_ARCHIVO_KEY = "ID_ARCHIVO_KEY";
    public static final String LISTA_UBICACIONES_KEY = "LISTA_UBICACIONES_KEY";
    public static final String DEPOSITO_KEY = "DEPOSITO_KEY";
    public static final String ELEMENTO_ASIGNABLE_KEY = "ELEMENTO_ASIGNABLE_KEY";
    public static final String ELEMENTO_ASIGNABLE_DESTINO_KEY = "ELEMENTO_ASIGNABLE_DESTINO_KEY";
    public static final String ELEMENTO_NO_ASIGNABLE_KEY = "ELEMENTO_NO_ASIGNABLE_KEY";
    public static final String ELEMENTO_DEPOSITO_KEY = "ELEMENTO_DEPOSITO_KEY";
    public static final String ELEMENTO_PADRE = "ELEMENTO_PADRE";
    public static final String LISTA_EDIFICIOS_KEY	= "LISTA_EDIFICIOS_KEY";

    public static final String LISTA_DESCENDIENTES_KEY = "LISTA_DESCENDIENTES_KEY";
    public static final String LISTA_TIPO_ELEMENTO_KEY = "LISTA_TIPO_ELEMENTO_KEY";
    public static final String LISTA_FORMATOS_REGULARES_KEY = "LISTA_FORMATOS_REGULARES_KEY";
    public static final String LISTA_FORMATOS_IRREGULARES_KEY = "LISTA_FORMATOS_IRREGULARES_KEY";
    public static final String DEPOSITOS_EN_UBICACION = "DEPOSITOS_EN_UBICACION";
    public static final String POSIBLE_UBICAR_RELACION = "POSIBLE_UBICAR_RELACION";
    public static final String LISTA_HUECOS_KEY = "LISTA_HUECOS_KEY";
    public static final String HUECO_DESTINO_SELECCIONADO_KEY = "HUECO_DESTINO_SELECCIONADO_KEY";
    public static final String HAY_HUECOS_OCUPADOS_KEY = "HAY_HUECOS_OCUPADOS_KEY";
    public static final String NUMERO_HUECOS_NO_LIBRES_KEY="NUMERO_HUECOS_NO_LIBRES_KEY";
    public static final String REUBICACION_FINALIZADA = "REUBICACION_FINALIZADA";
    public static final String ID_ELEMENTO_SIN_HUECOS_DISPONIBLES = "ID_ELEMENTO_SIN_HUECOS_DISPONIBLES";

    public static final String LISTA_HUECOS_UBICACION_KEY = "LISTA_HUECOS_UBICACION_KEY";
    public static final String LISTA_HUECOS_RESERVADOS_KEY = "LISTA_HUECOS_RESERVADOS_KEY";
    public static final String NUM_HUECOS_RESERVADOS_KEY = "NUM_HUECOS_RESERVADOS_KEY";
    public static final String NUM_HUECOS_OCUPADOS_KEY = "NUM_HUECOS_OCUPADOS_KEY";
    public static final String LISTA_HUECOS_A_LIBERAR_KEY = "LISTA_HUECOS_A_LIBERAR_KEY";
    public static final String LISTA_HUECOS_A_MOVER = "LISTA_HUECOS_A_MOVER";
    public static final String LISTA_NUEVOS_HUECOS_A_OCUPAR_KEY = "LISTA_NUEVOS_HUECOS_A_OCUPAR_KEY";
    public static final String LISTA_UDOCS_KEY = "LISTA_UDOCS_KEY";
    public static final String LISTA_UDOCS_POSIBLES_A_REUBICAR_KEY = "LISTA_UDOCS_POSIBLES_A_REUBICAR_KEY";
    public static final String TIPO_ELEMENTO_PADRE_HUECO="TIPO_ELEMENTO_PADRE_HUECO";
    public static final String HUECO_ORIGEN_COMPACTAR_KEY = "HUECO_ORIGEN_COMPACTAR_KEY";


    public static final String LISTA_REUBICACION_KEY= "LISTA_REUBICACION_KEY";


    /**
     * Se utiliza para reorganizar las unidades documentales de una unidad de instalación.
     * Se modifican los valores en sesión y posteriormente se graban.
     */
    public static final String LISTA_UDOCS_EN_CAJA = "LISTA_UDOCS_EN_CAJA";

    /**
     * Se utiliza para reorganizar las unidades documentales de una unidad de instalación.
     * Se modifican los valores en sesión y posteriormente se graban.
     */
    public static final String LISTA_UDOCS_EN_CAJA_ELIMINADAS = "LISTA_UDOCS_EN_CAJA_ELIMINADAS";
    public static final String HUECO_KEY = "HUECO_KEY";
    public static final String IS_CONTENIDO_VALIDADO_KEY = "IS_CONTENIDO_VALIDADO_KEY";
    public static final String UNIDAD_INSTALACION_DEPOSITO_KEY = "UNIDAD_INSTALACION_DEPOSITO_KEY";
    public static final String ELEMENTO_UBICACION_KEY = "ELEMENTO_UBICACION_KEY";
    public static final String RELACION_KEY = "RELACION_KEY";
    public static final String LISTA_ELEMENTOS_BANDEJA_KEY = "LISTA_ELEMENTOS_BANDEJA_KEY";
    public static final String REFRESH_VIEW_KEY = "REFRESH_VIEW_KEY";
    public static final String RESUMEN_OCUPACION = "RESUMEN_OCUPACION";
    public static final String EDITABLE_KEY = "EDITABLE_KEY";
    public static final String EDITANDO_KEY = "EDITANDO_KEY";
    public static final String EDITABLE_NUMERACION_KEY = "EDITABLE_NUMERACION_KEY";
    public static final String MOSTRAR_RENUMERACION_KEY = "MOSTRAR_RENUMERACION_KEY";
    public static final String LISTA_UIS_ELIMINADAS_KEY = "LISTA_UIS_ELIMINADAS_KEY";
    public static final String LISTA_UIS_ANIO_SERIE_KEY = "LISTA_UIS_ANIO_SERIE_KEY";

    public static final String LISTA_DEPOSITOS_KEY = "LISTA_DEPOSITOS_KEY";
    public static final String LISTA_CARTELAS_KEY = "LISTA_CARTELAS_KEY";
    public static final String LISTA_UDOCS_CARTELAS_KEY = "LISTA_UDOCS_CARTELAS_KEY";
    public static final String LISTADO_UNIDS_INST_BUSQUEDA_KEY = "LISTADO_UNIDS_INST_BUSQUEDA_KEY";
    public static final String LISTADO_FONDOS_BUSQUEDA = "LISTADO_FONDOS_BUSQUEDA";
    public static final String DEPOSITO_UDOCS_A_REUBICAR = "DEPOSITO_UDOCS_A_REUBICAR";
    public static final String LISTA_FORMATOS = "LISTA_FORMATOS";
    public static final String LISTA_ARCHIVOS_KEY = "LISTA_ARCHIVOS_KEY";
    public static final String INFO_FORMATO = "INFO_FORMATO";
    public static final String LISTA_IDS_ELEMENTOS="LISTA_IDS_ELEMENTOS";
    public static final String VO_BUSQUEDA_DEPOSITO="VO_BUSQUEDA_DEPOSITO";
    public static final String LIMITAR_RESULTADOS_BUSQUEDA="LIMITAR_RESULTADOS_BUSQUEDA";
    public static final String DEPOSITO_UDOCS_REUBICADAS = "DEPOSITO_UDOCS_REUBICADAS";

    public static final String LISTA_ARCHIVOS_USUARIO_KEY = "LISTA_ARCHIVOS_USUARIO_KEY";
    /**
     * Indica si existen cambios sin guardar, para mostar un mensaje al usuario.
     */
    public static final String HAY_CAMBIOS_SIN_GUARDAR = "HAY_CAMBIOS_SIN_GUARDAR";

    /**
     * Variable que indica si se permite cambia el elemento de destino de la ubicación
     * de una relación sin reserva. Si esta variables es true, se mostrará el botón.
     */
    public static final String PERMITIR_SELECCIONAR_ELEMENTO_UBICACION = "PERMITIR_SELECCIONAR_ELEMENTO_UBICACION";

    /**
     * Variable que indica si se muestra el navegador para el cambio de ubicación.
     */
    public static final String MOSTRAR_NAVEGADOR_CAMBIO_DESTINO_UBICACION = "MOSTRAR_NAVEGADOR_CAMBIO_DESTINO_UBICACION";

    /**
     * Lista de formatos regulares que coinciden con el de la balda de la que se intenta modificar el formato
     */
    public static final String LISTA_FMT_REG_IGUAL_LONG = "LISTA_FMT_REG_IGUAL_LONG";

    public static final String ID_TIPO_ELEMENTO_UBICACION="00000000000000000000000000000001";
    public static final String ID_TIPO_ELEMENTO_DEPOSITO="00000000000000000000000000000002";
    public static final String ID_TIPO_ELEMENTO_MODULO = "00000000000000000000000000000003";
    public static final String ID_TIPO_ELEMENTO_BALDA="00000000000000000000000000000004";
    public static final String ID_TIPO_PLANERO ="00000000000000000000000000000005";
    public static final String ID_TIPO_CAJON_PLANERO = "00000000000000000000000000000006";
    public static final String ID_TIPO_ELEMENTO_CUERPO="00000000000000000000000000000007";

    //Tipo elemento padre de Ubicaciones
    public static final String ID_TIPO_ELEMENTO_ROOT_UBICACION="00000000000000000000000000000000";
    //public static final String ID_TIPO_ELEMENTO_CUERPO="00000000000000000000000000000007";
    //public static final String ID_TIPO_ELEMENTO_BALDA="00000000000000000000000000000004";
    public static final String LISTA_HUECOS="listaHuecos";
    public static final String NUM_HUECOS_A_BUSCAR="numHuecosABuscar";
    public static final String CONTINUAR_EN_SUBARBOL="continuarEnSubarbol";
    public static final String SENTIDO_DESCENDENTE="sentidoDescendente";
    public static final int LENGTH_CODIGO_ORDEN_ELEMENTO=4;
    public static final Integer ORDENACION_ANCHURA = new Integer(2);
    public static final Integer ORDENACION_PROFUNDIDAD = new Integer(1);
    public static final String ORDENACION="ordenacion";

    public static Map claseAcceso = SigiaUtilConstants.getConstantsMap(ClaseAccesoDepositoElectronico.class);

    public static final String INFORME_CARTELAS_DEPOSITO_PA="archigest.archivo.informe.cartelas.deposito.pa";
    public static final String DEPOSITO_ELECTRONICO_IDEXT="archigest.archivo.deposito.electronico.idExt";
    public static final String DEPOSITO_ELECTRONICO_USO_FIRMA="archigest.archivo.deposito.electronico.usoFirma";
    public static final String DEPOSITO_ELECTRONICO_WS_WSDL="archigest.archivo.deposito.electronico.webservice.wsdl";
    public static final String CLAVE_CONFIRMACION="archigest.archivo.clave.confirmacion";
    public static final String USUARIO="archigest.archivo.usuario";
    public static final String DEPOSITO_ESTADO_RESERVA_RESERVADA="archigest.archivo.transferencias.estadoReservaReservada";
    public static final String DEPOSITO_ESTADO_RESERVA_NO_SE_HA_PODIDO="archigest.archivo.transferencias.estadoReservaNoSeHaPodido";
    public static final String DEPOSITO_OCUPADO="archigest.archivo.deposito.ocupado";
    public static final String DEPOSITO_RESERVADO="archigest.archivo.deposito.reservado";
    public static final String DEPOSITO_INUTILIZADO="archigest.archivo.deposito.inutilizado";
    public static final String DEPOSITO_LIBRE="archigest.archivo.deposito.libre";
    public static final String UBICACION="archigest.archivo.deposito.ubicacion";
    public static final String AMBITO="archigest.archivo.cf.ambito";
    public static final String NO_UNIDADES_DOCS_BUSQUEDA_NAME="archigest.error.busq.unidInst.deposito";

	public static final String LABEL_INFORMES_REUBICACION_TITULO="archigest.archivo.deposito.reubicacionDeUDocumentales";
	public static final String LABEL_INFORMES_REUBICACION_UINST_TITULO="archigest.archivo.deposito.informe.reubicacion.uinst";

    public static final String TEXTO_HUECOS="archigest.archivo.deposito.seleccionarHuecos.selAlgunosHuecos";

    public static final String ERROR_CONSULTA_UINST_DEPOSITO_DEMASIDOS_REGISTROS="archigest.error.consulta.uInstDeposito.demasiados.registros";
    public static final String ERROR_EDITAR_NUMERACION_SIN_SELECCION="archigest.error.deposito.editarnumeracion.sinSeleccion";
    public static final String ERROR_EDITAR_NUMERACION_VALOR_VACIO="archigest.error.deposito.editarnumeracion.vacio";
    public static final String ERROR_DEPOSITO_NUMERACION_IRREGULAR_FILA_VACIA="archigest.archivo.error.deposito.numeracion.irregular.fila.vacia";
    public static final String ERROR_DEPOSITO_NUMERACION_IRREGULAR_FILA_REPETIDA="archigest.archivo.error.deposito.numeracion.irregular.fila.repetida";
    public static final String ERROR_DEPOSITO_NUMERACION_REGULAR_FILA_REPETIDA="archigest.archivo.error.deposito.numeracion.regular.repetida";
    public static final String ERROR_DEPOSITO_NUMERACION_BD_REPETIDA="archigest.archivo.error.deposito.numeracion.repetida";
    //public static final String ERROR_NUMERACION_NO_NUMERICA="archigest.archivo.error.aplicacion.signaturas.no.alfanumericas";
    public static final String ERROR_NUMERACION_IRREGULAR_FILA_ALFANUMERICA="archigest.archivo.error.aplicacion.signaturas.no.alfanumericas.fila";
    public static final String ERROR_NUMERACION_REGULAR_NO_MAYOR_CERO="archigest.archivo.error.deposito.numeracion.regular.nomayorcero";
    public static final String ERROR_NUMERACION_IRREGULAR_FILA_NO_MAYOR_CERO="archigest.archivo.error.deposito.numeracion.irregular.fila.nomayorcero";
    public static final String ERROR_NUMERACION_RECALCULO_EXCEDE_MAXIMO="archigest.archivo.error.deposito.numeracion.recalculo.excede.maximo";
    public static final String ERROR_NUMERACION_ASIGNABLE_SIN_HUECOS="archigest.archivo.error.deposito.creacion.asignable.sinHueco";
    public static final String ERROR_EDITAR_NUMERACION_VACIA="archigest.archivo.deposito.editarnumeracion.vacia";
    public static final String ERROR_NUMERACION_HUECOS_DEMASIADO_ALTO="archigest.archivo.deposito.numeracion.huecos.demasiado.alto.fila";

    public static final String RECALCULAR_NUMERACION="R";
    public static final String NO_RECALCULAR_NUMERACION="C";
	public static final String PATH_KEY = "PATH_KEY";
	public static final String SHOW_PARENT = "SHOW_PARENT";
	public static final  String ALLOW_ALL_FORMATS = "ALLOW_ALL_FORMATS";

    public static final String MAP_NUMERACION_KEY="MAP_NUMERACION_KEY";
    public static final String FORMATO_KEY="FORMATO_KEY";
    public static final String LISTA_PATHS_HUECOS_KEY="LISTA_PATHS_HUECOS_KEY";
    public static final String LISTA_HUECOS_ASIGNABLE_KEY = "LISTA_HUECOS_ASIGNABLE_KEY";
    public static final String ID_FORMATO_INICIAL = "ID_FORMATO_INICIAL";
    public static final long MAX_NUMERACION_ENTERA_HUECOS=9999999999999999L;

    public static final String ERROR_HUECO_LISTAR_UNIDADES = "errors.deposito.hueco.listar.unidades.documentales";

    public static final String LABEL_SERIE = "archigest.archivo.serie";
    public static final String LABEL_NUM_EXP = "archigest.archivo.num.exp";
    public static final String LABEL_ASUNTO = "archigest.archivo.asunto";
    public static final String LABEL_FECHA = "archigest.archivo.fecha";

    public static final String LABEL_SERIE_CARTELAS = "archigest.archivo.serie.cartelas";
    public static final String LABEL_NUM_EXP_CARTELAS = "archigest.archivo.num.exp.cartelas";
    public static final String LABEL_ASUNTO_CARTELAS = "archigest.archivo.asunto.cartelas";
    public static final String LABEL_FECHA_CARTELAS = "archigest.archivo.fecha.cartelas";

    public static final String ERROR_NO_HAY_HUECOS_PARA_LAS_CONDICIONES = "archigest.archivo.deposito.error.noHuecosCondicion";

    public static final String LISTA_NIVELES_KEY = "LISTA_NIVELES_KEY";
    public static final String NIVEL_DEPOSITO_KEY = "NIVEL_DEPOSITO_KEY";
    public static final String NIVEL_PADRE_DEPOSITO_KEY = "NIVEL_PADRE_DEPOSITO_KEY";
    public static final String LISTA_NIVELES_DEPOSITO_HIJO = "LISTA_NIVELES_DEPOSITO_HIJO";
    public static final String LISTA_ELEMENTOS_DEFECTO = "LISTA_ELEMENTOS_DEFECTO";

	//NIVELES DEPOSITO
	public static final String ERROR_ELIMINAR_NIVEL_HIJO = "archigest.archivo.depositos.niveles.eliminar.hijos";
	public static final String ERROR_ELIMINAR_NIVEL_REFERENCIADO = "archigest.archivo.depositos.niveles.eliminar.referenciado";
	public static final String ERROR_HERMANO_DUPLICADO = "archigest.archivo.depositos.niveles.hermano.duplicado";
	public static final String ERROR_NIVEL_DUPLICADO = "archigest.archivo.depositos.niveles.duplicado";
	public static final String ERROR_TIPO_ELEMENTO_DEPOSITO_NO_EDITABLE = "archigest.archivo.depositos.niveles.no.editable";
	public static final String ERROR_ELEMENTO_DEPOSITO_SIN_DESCENDIENTES = "archigest.archivo.depositos.elementos.sin.descendientes";
	public static final String ERROR_PADRE_DUPLICADO = "archigest.archivo.depositos.niveles.caracter.ordenacion.padre.duplicado";
	public static final String ERROR_CARACTER_INCORRECTO = "archigest.archivo.depositos.niveles.caracter.ordenacion.incorrecto";

	public static final String ERROR_CARACTER_ORDENACION_NO_EDITABLE	= "archigest.archivo.depositos.niveles.caracter.ordenacion.no.editable";


	public static final String LABEL_TIPO_ELEMENTO_NOMBRE = "archigest.archivo.depositos.niveles.nombre";
	public static final String LABEL_TIPO_ELEMENTO_NOMBRE_ABREVIADO = "archigest.archivo.depositos.niveles.nombre.abreviado";
	public static final String LABEL_TIPO_ELEMENTO_CARACTER_ORDENACION = "archigest.archivo.depositos.niveles.caracter.ordenacion";
	public static final String LABEL_TIPO_ELEMENTO_ASIGNABLE = "archigest.archivo.depositos.niveles.asignable";
	public static final String LABEL_TIPO_ELEMENTO_TIPO_ORDENACION = "archigest.archivo.depositos.niveles.tipo.ordenacion";
}

