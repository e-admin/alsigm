package salas;

/**
 * Clase de constantes para la gestión de salas
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class SalasConsultaConstants {

	private static final StringBuffer buff = new StringBuffer();

	private final static String getKey(String innerKey) {
		buff.setLength(0);
		return buff.append("SalasConsultaConstants_").append(innerKey).toString();
	}

	// Keys
	public static final String LISTA_EDIFICIOS_KEY = getKey("LISTA_EDIFICIOS_KEY");
	public static final String LISTA_SALAS_KEY = getKey("LISTA_SALAS_KEY");
	public static final String LISTA_MESAS_KEY = getKey("LISTA_MESAS_KEY");
	public static final String LISTA_PATHS_MESAS_KEY = getKey("LISTA_PATHS_MESAS_KEY");
	public static final String LISTA_IDS_MESA_KEY = getKey("LISTA_IDS_MESA_KEY");
	public static final String EDIFICIO_KEY = getKey("EDIFICIO_KEY");
	public static final String SALA_KEY = getKey("SALA_KEY");
	public static final String SALAS_CONSULTA_VIEW_NAME = getKey("VISTA_ESTRUCTURA_SALAS_CONSULTA_KEY");
	public static final String REFRESH_VIEW_KEY = getKey("REFRESH_VIEW_KEY");
	public static final String USUARIO_ARCHIVO_KEY = getKey("USUARIO_ARCHIVO_KEY");
	public static final String PERMITIR_MODIFICAR_MESA_KEY = getKey("PERMITIR_MODIFICAR_MESA_KEY");
	public static final String ACTION_ELIMINAR_MESAS_KEY = getKey("ACTION_ELIMINAR_MESAS_KEY");
	public static final String ACTION_MODIFICAR_MESAS_KEY = getKey("ACTION_MODIFICAR_MESAS_KEY");
	public static final String LISTA_USUARIOS_KEY = getKey("LISTA_USUARIOS_KEY");
	public static final String LISTA_USUARIOS_CONSULTA_KEY = getKey("LISTA_USUARIOS_CONSULTA_KEY");
	public static final String LISTA_TIPOS_DOC_IDENTIFICATIVO_KEY = getKey("LISTA_TIPOS_DOC_IDENTIFICATIVO_KEY");
	public static final String USUARIO_CONSULTA_KEY = getKey("USUARIO_CONSULTA_KEY");
	public static final String LISTA_ARCHIVOS_KEY = getKey("LISTA_ARCHIVOS_KEY");
	public static final String LISTA_ARCHIVOS_SELECCIONAR_KEY = getKey("LISTA_ARCHIVOS_SELECCIONAR_KEY");
	public static final String LISTA_USUARIOS_EXTERNOS_KEY = getKey("LISTA_USUARIOS_EXTERNOS_KEY");
	public static final String LISTA_VACIO_SI_NO		  = getKey("LISTA_VACIO_SI_NO");
	public static final String LISTA_DESCENDIENTES_KEY = getKey("LISTA_DESCENDIENTES_KEY");
	public static final String MOSTRAR_NAVEGACION_REGISTRO_KEY = getKey("MOSTRAR_NAVEGACION_REGISTRO_KEY");
	public static final String IS_LAST_LEVEL_KEY = getKey("IS_LAST_LEVEL_KEY");
	public static final String PATH_PADRE_KEY = getKey("PATH_PADRE_KEY");
	public static final String NOMBRE_PADRE_KEY = getKey("NOMBRE_PADRE_KEY");
	public static final String REGISTRO_CONSULTA_KEY = getKey("REGISTRO_CONSULTA_KEY");
	public static final String ARCHIVO_KEY = getKey("ARCHIVO_KEY");
	public static final String MESA_KEY = getKey("MESA_KEY");
	public static final String REGISTRO_KEY = getKey("REGISTRO_KEY");
	public static final String LISTA_CONSULTAS_USUARIO_KEY = getKey("LISTA_CONSULTAS_USUARIO_KEY");
	public static final String LISTA_USUARIOS_REGISTRADOS_KEY = getKey("LISTA_USUARIOS_REGISTRADOS_KEY");
	public static final String USUARIO_SALA_KEY = getKey("USUARIO_SALA_KEY");
	public static final String VER_USUARIOS_SALA_KEY = getKey("VER_USUARIOS_SALA_KEY");
	public static final String INFORME_USUARIOS_KEY = getKey("INFORME_USUARIOS_KEY");
	public static final String BUSQUEDA_USUARIOS_KEY = getKey("BUSQUEDA_USUARIOS_KEY");
	public static final String FECHA_COMP_KEY = getKey("FECHA_COMP_KEY");
	public static final String FECHA_COMP_ENTRADA_KEY = getKey("FECHA_COMP_ENTRADA_KEY");
	public static final String FECHA_COMP_SALIDA_KEY = getKey("FECHA_COMP_SALIDA_KEY");
	public static final String NUM_DOC_LIKE_KEY = getKey("NUM_DOC_LIKE_KEY");
	public static final String BUSQUEDA_REGISTROS_KEY = getKey("BUSQUEDA_REGISTROS_KEY");
	public static final String LISTA_EXPEDIENTES_KEY = getKey("LISTA_EXPEDIENTES_KEY");
	public static final String BUSCADOR_USUARIOS_KEY = getKey("BUSCADOR_USUARIOS_KEY");
	public static final String RESUMEN_SERVICIOS_KEY = getKey("RESUMEN_SERVICIOS_KEY");
	public static final String LISTA_UNIDADES_CONSULTADAS_KEY = getKey("LISTA_UNIDADES_CONSULTADAS_KEY");
	public static final String LISTA_TEMAS_KEY = getKey("LISTA_TEMAS_KEY");
	public static final String PERMITIR_MODIFICAR_USUARIO_KEY = getKey("PERMITIR_MODIFICAR_USUARIO_KEY");

	// Parametros
	public static final String PARAM_ID_EDIFICIO = "idEdificio";
	public static final String PARAM_ID_SALA = "idSala";
	public static final String PARAM_ID_MESA = "idMesa";
	public static final String PARAM_ID_USUARIO = "idUsuario";
	public static final String PARAM_NODE = "node";
	public static final String PARAM_REFRESHVIEW = "refreshView";
	public static final String PARAM_NUM_MESAS = "numeroMesas";
	public static final String PARAM_ID_ARCHIVO = "idArchivo";
	public static final String PARAM_EQUIPO_INFORMATICO = "equipoInformatico";
	public static final String PARAM_CONSULTA = "idConsulta";
	public static final String PARAM_REGISTRO_CONSULTA = "idRegistroConsulta";
	public static final String PARAM_DATOS_REGISTRO = "datosRegistro";

	// Etiquetas
	public static final String ETIQUETA_OBJETO_EDIFICIO = "archigest.archivo.salas.edificio.objeto.nombre";
	public static final String ETIQUETA_NOMBRE_EDIFICIO = "archigest.archivo.salas.edificio.nombre.label";
	public static final String ETIQUETA_OBJETO_USUARIO	= "archigest.archivo.salas.usuario.sala.objeto.nombre";
	public static final String ETIQUETA_ARCHIVO_EDIFICIO = "archigest.archivo.salas.edificio.archivo.label";
	public static final String ETIQUETA_UBICACION_ARCHIVO = "archigest.archivo.salas.edificio.ubicacion.label";
	public static final String ETIQUETA_OBJETO_SALA = "archigest.archivo.salas.sala.objeto.nombre";
	public static final String ETIQUETA_ARBOL_NAME = "archigest.archivo.salas.arbol.nombre";
	public static final String ETIQUETA_NUMERO_MESAS = "archigest.archivo.salas.numero.mesas";
	public static final String ETIQUETA_NUM_DOCUMENTO = "archigest.archivo.numdoc";
	public static final String ETIQUETA_TIPO_DOCUMENTO = "archigest.archivo.tipodoc";
	public static final String ETIQUETA_NOMBRE = "archigest.archivo.nombre";
	public static final String ETIQUETA_APELLIDOS="archigest.archivo.apellidos";
	public static final String ETIQUETA_RAIZ_NAVEGADOR = "archigest.archivo.salas.arbol.nombre";
	public static final String ETIQUETA_ARCHIVOS_ASOCIADOS = "archigest.archivo.archivos.asociados";

	public static final String LABEL_MESA_ASIGNADA = "archigest.archivo.salas.mesa.asignada";
	public static final String LABEL_INFORME_USUARIOSCONSULTA_TITULO = "archigest.archivo.salas.informeUsuariosConsulta.titulo";
	public static final String LABEL_DOC_IDENTIFICATIVO = "archigest.archivo.salas.usuario.doc.identificacion";
	public static final String LABEL_NOMBRE = "archigest.archivo.nombre";
	public static final String LABEL_APELLIDOS = "archigest.archivo.apellidos";
	public static final String LABEL_VIGENCIA = "archigest.archivo.salas.usuario.vigente";
	public static final String LABEL_NACIONALIDAD = "archigest.archivo.nacionalidad";
	public static final String LABEL_FECHA_ALTA = "archigest.archivo.fecha.alta";

	public static final String LABEL_ARCHIVO = "archigest.archivo.archivo";
	public static final String LABEL_TIPO_DOC = "archigest.archivo.tipodoc";
	public static final String LABEL_NUM_DOC = "archigest.archivo.numdoc";

	public static final String LABEL_INFORME_USUARIOSASISTENCIA_TITULO = "archigest.archivo.salas.informeUsuariosAsistencia.titulo";
	public static final String LABEL_NOMBRE_APELLIDOS = "archigest.archivo.salas.usuario.nombreApellidos";
	public static final String LABEL_FECHA_REGISTRO = "archigest.archivo.salas.registro.fechaRegistro";
	public static final String LABEL_HORA_ENTRADA_SALIDA = "archigest.archivo.salas.horasRegistro";
	public static final String LABEL_REGISTRO_CERRADO = "archigest.archivo.salas.registro.cerrado";
	public static final String LABEL_R_CERRADO = "archigest.archivo.salas.registroCerrado";
	public static final String LABEL_FECHA_ENTRADA = "archigest.archivo.salas.registro.fechaEntrada";
	public static final String LABEL_FECHA_SALIDA = "archigest.archivo.salas.registro.fechaSalida";

	public static final String LABEL_INFORME_EXPEDIENTESUSUARIO_TITULO = "archigest.archivo.salas.informeExpedientesUsuario.titulo";
	public static final String LABEL_DATOS_USUARIO = "archigest.archivo.salas.datos.usuario";
	public static final String LABEL_DOCUMENTO_IDENTIFICATIVO = "archigest.archivo.doc.identificativo";
	public static final String LABEL_DIRECCION = "archigest.archivo.direccion";
	public static final String LABEL_FECHA = "archigest.archivo.fecha";
	public static final String LABEL_SIGNATURA = "archigest.archivo.signatura";
	public static final String LABEL_MOTIVO = "archigest.archivo.motivo";
	public static final String LABEL_NOMBRE_USUARIO = "archigest.archivo.salas.consulta.usuario";
	public static final String LABEL_FECHA_ESTADO_EXP = "archigest.archivo.sala.fecha.estado.exp";

	public static final String LABEL_INFORME_RESUMENSERVICIOS_TITULO = "archigest.archivo.salas.informeResumenServicios.titulo";
	public static final String LABEL_INFORME_TEMASINVESTIGACION_TITULO = "archigest.archivo.salas.informeTemasInvestigacion.titulo";
	public static final String LABEL_INFORME_UNIDADES_CONSULTADAS_TITULO = "archigest.archivo.salas.informeUnidadesConsultadas.titulo";
	public static final String LABEL_TEMA = "archigest.archivo.consultas.tema";
	public static final String LABEL_NUM_USUARIOS = "archigest.archivo.salas.num.usuarios";
	public static final String LABEL_NUM_REGISTROS = "archigest.archivo.salas.num.registros";
	public static final String LABEL_NUM_REGISTROS_ORDENADOR = "archigest.archivo.salas.num.registros.ordenador";
	public static final String LABEL_NUM_UNIDADES = "archigest.archivo.salas.num.unidades";
	public static final String LABEL_CRITERIOS_BUSQUEDA = "archigest.archivo.salas.criterios.busqueda";
	public static final String LABEL_NUM_VECES = "archigest.archivo.salas.label.num.veces";
	public static final String LABEL_TITULO = "archigest.archivo.titulo";
	public static final String LABEL_TOTAL_UNIDADES = "archigest.archivo.salas.total.unidades";
	public static final String LABEL_TOTAL_VECES = "archigest.archivo.salas.total.veces.consultadas";
	public static final String PARAM_NUM_VECES = "archigest.archivo.salas.num.veces.mayor";

	// Atributos
	public static final String ATTRIBUTE_VIEW_ACTION = "viewAction";
	public static final String ATTRIBUTE_VIEW_ACTION_OBTENER_VISTA = "obtenerVista";
	public static final String ATTRIBUTE_VIEW_NAME = "viewName";

	// Para las Fechas de Entrada y Salida de Registro de consulta en sala
	public static final String ATTR_FECHA_COMP_ENTRADA = "fechaCompEntrada";
	public static final String ATTR_FECHA_FORMATO_ENTRADA = "fechaFormatoEntrada";
	public static final String ATTR_FECHA_D_ENTRADA = "fechaDEntrada";
	public static final String ATTR_FECHA_M_ENTRADA = "fechaMEntrada";
	public static final String ATTR_FECHA_A_ENTRADA = "fechaAEntrada";
	public static final String ATTR_FECHA_S_ENTRADA = "fechaSEntrada";
	public static final String ATTR_FECHA_INI_FORMATO_ENTRADA = "fechaIniFormatoEntrada";
	public static final String ATTR_FECHA_INI_D_ENTRADA = "fechaIniDEntrada";
	public static final String ATTR_FECHA_INI_M_ENTRADA = "fechaIniMEntrada";
	public static final String ATTR_FECHA_INI_A_ENTRADA = "fechaIniAEntrada";
	public static final String ATTR_FECHA_INI_S_ENTRADA = "fechaIniSEntrada";
	public static final String ATTR_FECHA_FIN_FORMATO_ENTRADA = "fechaFinFormatoEntrada";
	public static final String ATTR_FECHA_FIN_D_ENTRADA = "fechaFinDEntrada";
	public static final String ATTR_FECHA_FIN_M_ENTRADA = "fechaFinMEntrada";
	public static final String ATTR_FECHA_FIN_A_ENTRADA = "fechaFinAEntrada";
	public static final String ATTR_FECHA_FIN_S_ENTRADA = "fechaFinSEntrada";

	public static final String ATTR_FECHA_COMP_SALIDA = "fechaCompSalida";
	public static final String ATTR_FECHA_FORMATO_SALIDA = "fechaFormatoSalida";
	public static final String ATTR_FECHA_D_SALIDA = "fechaDSalida";
	public static final String ATTR_FECHA_M_SALIDA = "fechaMSalida";
	public static final String ATTR_FECHA_A_SALIDA = "fechaASalida";
	public static final String ATTR_FECHA_S_SALIDA = "fechaSSalida";
	public static final String ATTR_FECHA_INI_FORMATO_SALIDA = "fechaIniFormatoSalida";
	public static final String ATTR_FECHA_INI_D_SALIDA = "fechaIniDSalida";
	public static final String ATTR_FECHA_INI_M_SALIDA = "fechaIniMSalida";
	public static final String ATTR_FECHA_INI_A_SALIDA = "fechaIniASalida";
	public static final String ATTR_FECHA_INI_S_SALIDA = "fechaIniSSalida";
	public static final String ATTR_FECHA_FIN_FORMATO_SALIDA = "fechaFinFormatoSalida";
	public static final String ATTR_FECHA_FIN_D_SALIDA = "fechaFinDSalida";
	public static final String ATTR_FECHA_FIN_M_SALIDA = "fechaFinMSalida";
	public static final String ATTR_FECHA_FIN_A_SALIDA = "fechaFinASalida";
	public static final String ATTR_FECHA_FIN_S_SALIDA = "fechaFinSSalida";

	// Para la Fecha de Alta de usuario de consulta en sala
	public static final String ATTR_FECHA_COMP_ALTA = "fechaCompAlta";
	public static final String ATTR_FECHA_FORMATO_ALTA = "fechaFormatoAlta";
	public static final String ATTR_FECHA_D_ALTA = "fechaDAlta";
	public static final String ATTR_FECHA_M_ALTA = "fechaMAlta";
	public static final String ATTR_FECHA_A_ALTA = "fechaAAlta";
	public static final String ATTR_FECHA_S_ALTA = "fechaSAlta";
	public static final String ATTR_FECHA_INI_FORMATO_ALTA = "fechaIniFormatoAlta";
	public static final String ATTR_FECHA_INI_D_ALTA = "fechaIniDAlta";
	public static final String ATTR_FECHA_INI_M_ALTA = "fechaIniMAlta";
	public static final String ATTR_FECHA_INI_A_ALTA = "fechaIniAAlta";
	public static final String ATTR_FECHA_INI_S_ALTA = "fechaIniSAlta";
	public static final String ATTR_FECHA_FIN_FORMATO_ALTA = "fechaFinFormatoAlta";
	public static final String ATTR_FECHA_FIN_D_ALTA = "fechaFinDAlta";
	public static final String ATTR_FECHA_FIN_M_ALTA = "fechaFinMAlta";
	public static final String ATTR_FECHA_FIN_A_ALTA = "fechaFinAAlta";
	public static final String ATTR_FECHA_FIN_S_ALTA = "fechaFinSAlta";

	// Para la Fecha de Estado de los expedientes de usuario de consulta en sala
	public static final String ATTR_FECHA_COMP_EXP = "fechaCompExp";
	public static final String ATTR_FECHA_FORMATO_EXP = "fechaFormatoExp";
	public static final String ATTR_FECHA_D_EXP = "fechaDExp";
	public static final String ATTR_FECHA_M_EXP = "fechaMExp";
	public static final String ATTR_FECHA_A_EXP = "fechaAExp";
	public static final String ATTR_FECHA_S_EXP = "fechaSExp";
	public static final String ATTR_FECHA_INI_FORMATO_EXP = "fechaIniFormatoExp";
	public static final String ATTR_FECHA_INI_D_EXP = "fechaIniDExp";
	public static final String ATTR_FECHA_INI_M_EXP = "fechaIniMExp";
	public static final String ATTR_FECHA_INI_A_EXP = "fechaIniAExp";
	public static final String ATTR_FECHA_INI_S_EXP = "fechaIniSExp";
	public static final String ATTR_FECHA_FIN_FORMATO_EXP = "fechaFinFormatoExp";
	public static final String ATTR_FECHA_FIN_D_EXP = "fechaFinDExp";
	public static final String ATTR_FECHA_FIN_M_EXP = "fechaFinMExp";
	public static final String ATTR_FECHA_FIN_A_EXP = "fechaFinAExp";
	public static final String ATTR_FECHA_FIN_S_EXP = "fechaFinSExp";

	// Errores
	public static final String ERROR_DATOS_ELEMENTA_NO_ENCONTRADOS = "archigest.archivo.datos.elementa.no.encontrados";
	public static final String ERROR_DATOS_ELEMENTO_NO_ENCONTRADOS = "archigest.archivo.datos.elemento.no.encontrados";
	public static final String ERROR_NOMBRE_EDIFICIO_DUPLICADO = "archigest.archivo.salas.nombre.edificio.duplicado";
	public static final String ERROR_NO_ELIMINAR_EDIFICIO_CON_SALAS = "archigest.archivo.salas.no.eliminar.edificio.con.salas";
	public static final String ERROR_NODO_NO_ENCONTRADO = "archigest.archivo.salas.arbol.nodo.no.encontrado";
	public static final String ERROR_EDIFICIO_CON_MESAS_OCUPADAS = "archigest.archivo.salas.edificio.con.mesas.ocupadas";
	public static final String ERROR_NO_ELIMINAR_SALA_CON_MESAS = "archigest.archivo.salas.no.eliminar.sala.con.mesas";
	public static final String ERROR_CODIGO_MESA_VACIO = "archigest.archivo.salas.codigo.mesa.vacio";
	public static final String ERROR_CODIGO_MESA_DUPLICADO = "archigest.archivo.salas.codigo.mesa.duplicado";
	public static final String ERROR_NOMBRE_SALA_DUPLICADO = "archigest.archivo.salas.nombre.sala.duplicado";
	public static final String ERROR_NUMERO_MESAS_INCORRECTO = "archigest.archivo.salas.numero.mesas.incorrecto";
	public static final String ERROR_NO_HAY_MESAS_A_SELECCIONAR = "archigest.archivo.salas.noHayMesasASeleccionar";
	public static final String ERROR_NO_HAY_MESAS_A_MODIFICAR = "archigest.archivo.salas.noHayMesasAModificar";
	public static final String ERROR_NO_HAY_ARCHIVOS_PARA_ANIADIR = "archigest.archivo.salas.usuario.sinArchivos.aniadir";
	public static final String ERROR_NO_ELIMINAR_USUARIO_CON_CONSULTAS = "archigest.archivo.salas.usuario.no.eliminar.con.consultas";
	public static final String ERROR_NO_ELIMINAR_USUARIO_EN_SALA = "archigest.archivo.salas.usuario.no.eliminar.en.sala";
	public static final String ERROR_NO_MODIFICAR_USUARIO_EN_SALA = "archigest.archivo.salas.usuario.no.eliminar.en.sala";
	public static final String ERROR_NO_MESAS_LIBRES_A_SELECCIONAR = "archigest.archivo.salas.noMesasLibres";
	public static final String ERROR_REGISTRO_DE_MESA_OCUPADA = "archigest.archivo.salas.registroMesaOcupada";
	public static final String ERROR_NO_ARCHIVOS_REGISTRO = "archigest.archivo.salas.registroSinArchivos";
	public static final String ERROR_USUARIO_REGISTRADO = "archigest.archivo.salas.usuarioRegistrado";
	public static final String ERROR_ARCHIVOS_OBLIGATORIOS = "archigest.archivo.salas.usuario.archivo.obligatorio";
	public static final String ERROR_USUARIO_CON_UDOCS_PENDIENTES = "archigest.archivo.salas.registro.usuario.udocs.pendientes";
	public static final String ERROR_USUARIO_CONSULTA_SALAS_USUARIO_APLICACION_YA_CREADO = "archigest.archivo.salas.usuario.aplicacion.ya.creado";
	public static final String ERROR_ARCHIVO_USUARIO_CONSULTA_SALA_CON_MESA_OCUPADA = "archigest.archivo.salas.archivo.usuario.mesa.ocupada";
	public static final String ERROR_BUSQUEDA_USUARIOS_CONSULTA_VACIA = "archigest.archivo.salas.busqueda.usuarios.vacia";
    public static final String ERROR_AL_MOSTRAR_ELEMENTO_KEY = "archigest.archivo.salas.error.mostrar.elemento";
    public static final String ERROR_ELEMENTO_NO_ENCONTRADO_KEY = "archigest.archivo.salas.error.elemento.no.encontrado";



	// Forwards
	public static final String GLOBAL_FORWARD_VER_EDIFICIO = "verEdificio";
	public static final String GLOBAL_FORWARD_VER_SALA = "verSala";
	public static final String GLOBAL_FORWARD_LISTADO_EDIFICIOS = "listado_edificios";
	public static final String GLOBAL_FORWARD_VER_USUARIO_SALA_CONSULTA = "ver_usuario_sala_consulta";

	public static final String FORWARD_VISTA_SC_HOME_SALAS_CONSULTA = "home_salas_consulta";
	public static final String FORWARD_VISTA_SC_DONE ="done";
	//public static final String FORWARD_VISTA_SC_LISTA_EDIFICIOS = "lista_edificios";
	public static final String FORWARD_VISTA_SC_VER_SALAS_CONSULTA = "ver_salas_consulta";


	public static final String FORWARD_INIT_EDIFICIOS = "initEdificios";
	public static final String FORWARD_LISTADO_EDIFICIOS = "listadoEdificios";
	public static final String FORWARD_DATOS_EDIFICIO = "datosEdificio";
	public static final String FORWARD_EDICION_EDIFICIO = "edicionEdificio";
	//public static final String FORWARD_HOME = "homeSalasConsulta";
	public static final String FORWARD_DONE = "done";
	public static final String FORWARD_LISTADO = "listado";
	public static final String FORWARD_DATOS = "datos";
	public static final String FORWARD_EDICION = "edicion";
	public static final String FORWARD_CREAR_MESAS = "crearMesas";
	public static final String FORWARD_NUMERO_MESAS_CREAR = "numMesasCrear";
	public static final String FORWARD_SELECCION_ESTADO_MESAS = "seleccionEstadoMesas";
	public static final String FORWARD_SELECCION_MESAS = "seleccionMesas";
	public static final String FORWARD_REGISTRO_ENTRADA = "registroEntrada";
	public static final String FORWARD_CREAR_REGISTRO = "crearRegistro";
	public static final String FORWARD_ADD_USUARIO_EXTERNO = "add_usuario_externo";
	public static final String FORWARD_BUSCADOR			   = "buscador";
	public static final String FORWARD_CONFIRMAR_REGISTRO = "confirmarRegistro";
	public static final String FORWARD_REGISTRO_SALIDA = "registroSalida";
	public static final String FORWARD_DATOS_REGISTRO_ENTRADA = "datosRegistroEntrada";
	public static final String FORWARD_REDIRECT_DATOS_REGISTRO_ENTRADA = "redirectDatosRegistroEntrada";
	public static final String FORWARD_CONSULTAS_USUARIO_SALA = "consultasUsuario";
	public static final String FORWARD_USUARIOS_REGISTRADOS = "usuariosRegistrados";
	public static final String FORWARD_BUSCAR_USUARIOS = "buscarUsuarios";
	public static final String FORWARD_INFORME_USUARIOS = "informeUsuarios";
	public static final String FORWARD_BUSCAR_INVESTIGADORES = "buscarInvestigadoresAsistencia";
	public static final String FORWARD_INFORME_ASISTENCIA = "informeAsistencia";
	public static final String FORWARD_BUSCAR_EXPEDIENTES = "buscarExpedientes";
	public static final String FORWARD_BUSCADOR_USUARIOS = "buscadorUsuarios";
	public static final String FORWARD_RESUMEN_SERVICIOS = "resumenServicios";
	public static final String FORWARD_TEMAS_INVESTIGACION = "temasInvestigacion";
	public static final String FORWARD_LISTADO_USUARIOS = "listadoUsuarios";
	public static final String FORWARD_INFORME_EXPEDIENTES = "informeExpedientes";
	public static final String FORWARD_INFORME_SERVICIOS = "informeServicios";
	public static final String FORWARD_INFORME_TEMAS = "informeTemas";
	public static final String FORWARD_UNIDADES_CONSULTADAS = "unidadesConsultadas";
	public static final String FORWARD_INFORME_UNIDADES = "informeUnidades";

	// Paths
	public static final String PATH_ACTION_VER_NODO = "/action/gestionVistaSalasConsulta?actionToPerform=verNodo";
	public static final String PATH_ACTION_HOME = "/action/gestionVistaSalasConsulta?actionToPerform=homeSalasConsulta";

	// Constantes
	public static final String TIPO_ELEMENTO_EDIFICIO = "E";
	public static final String TIPO_ELEMENTO_SALA = "S";
	public static final String TIPO_ELEMENTO_MESA = "M";

	public static final String PATH_NAME_UBICACION_MESA = "Mesa";

	public static final String ETIQUETA_IGUAL = "igual";
	public static final String ETIQUETA_CONTIENE = "contiene";
}