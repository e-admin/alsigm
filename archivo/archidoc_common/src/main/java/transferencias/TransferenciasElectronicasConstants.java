package transferencias;

import java.util.HashMap;


public class TransferenciasElectronicasConstants {

	//Sistemas Externos
	public static final String SE_ORGANIZACION = "ORGANIZACION";
	public static final String SE_TERCEROS = "TERCEROS";
	public static final String SE_GEOGRAFICOS = "GEOGRAFICOS";

	//Campos Sistema Externo
	public static final String TIPO_ATRIBUTO_SE_CODIGO = "CODIGO";
	public static final String TIPO_ATRIBUTO_SE_ID = "ID";


	//Tipos de Ubicación
	public static final String BINARIO = "BINARIO";
	public static final String REPOSITORIO = "REPOSITORIO";

	//Tipos de Repositorios
	public static final String ALFRESCO = "ALFRESCO";
	public static final String INVESDOC = "INVESDOC";
	public static final String RDE = "RDE";

	//ETIQUETAS XML
	public static final String CAMPO_TEXTO ="CAMPO_TEXTO";
	public static final String CAMPO_FECHA ="CAMPO_FECHA";
	public static final String CAMPO_NUMERICO = "CAMPO_NUMERICO";
	public static final String CAMPO_DESCRIPTOR = "CAMPO_DESCRIPTOR";
	public static final String CAMPO_SISTEMA_EXTERNO = "CAMPO_SISTEMA_EXTERNO";
	public static final String CAMPO_TABLA = "CAMPO_TABLA";
	public static final String CAMPO_FILA = "CAMPO_FILA";
	public static final String CAMPO_DOCUMENTO_ELECTRONICO = "CAMPO_DOCUMENTO_ELECTRONICO";

	public static final String PRODUCTOR = "PRODUCTOR";
	public static final String FECHA = "FECHA";

	//Autenticación del Usuario
	public static final String ERROR_INESPERADO_MSG = "Se ha producido un error no controlado";

	public static final String ERROR_EXCEPTION = "0000";
	private static final String ERROR_EXCEPTION_MSG = "Error no controlado al realizar la operación";

	/**
	 * Error en la autenticación del usuario
	 */
	public static final String ERROR_AUTENTICACION_USUARIO = "0001";
	private static final String ERROR_AUTENTICACION_USUARIO_MSG = "Error al realizar la autenticación del usuario";

	public static final String ERROR_SIN_PERMISOS = "0002";
	private static final String ERROR_SIN_PERMISOS_MSG = "El usuario no tiene permisos para ejecutar la operación";


	public static final String ERROR_ELEMENTO_PADRE_SERIE_INCORRECTO = "0003";
	private static final String ERROR_ELEMENTO_PADRE_SERIE_INCORRECTO_MSG = "Tipo de elemento padre es incorrecto";

	public static final String ERROR_ELEMENTO_PADRE_NO_VIGENTE = "0004";
	private static final String ERROR_ELEMENTO_PADRE_NO_VIGENTE_MSG = "El elemento padre de la serie no está vigente";

	public static final String ERROR_ELEMENTO_PADRE_INEXSTENTE = "0005";
	private static final String ERROR_ELEMENTO_PADRE_INEXSTENTE_MSG = "El elemento padre de la serie no existe";



	public static final String ERROR_GESTOR_ESTRUCTURA_ORGANIZATIVA = "0006";
	private static final String ERROR_GESTOR_ESTRUCTURA_ORGANIZATIVA_MSG = "Error con el sistema de estructura organizativa";

	public static final String ERROR_OPERACION_NO_DISPONIBLE = "0007";
	private static final String ERROR_OPERACION_NO_DISPONIBLE_MSG = "La operación no está disponible";


	//Errores de Parámetros
	public static final String ERROR_PROCESAR_CONTENIDO_XML = "0008";
	private static final String ERROR_PROCESAR_CONTENIDO_XML_MSG  ="Error al procesar el fichero de contenido xml";


	public static final String ERROR_INFORMACION_REQUERIDA = "0009";
	private static final String ERROR_INFORMACION_REQUERIDA_MSG = "Falta información obligatoria para ejecutar el servicio web";

	public static final String ERROR_LONGITUD_INCORRECTA = "0010";
	private static final String ERROR_LONGITUD_INCORRECTA_MSG  ="Longitud del campo incorrecta";

	public static final String ERROR_NO_EXISTE_PRODUCTOR_UNIDAD_DOCUMENTAL = "0011";
	private static final String ERROR_NO_EXISTE_PRODUCTOR_UNIDAD_DOCUMENTAL_MSG = "No existe el productor en el sistema de organización";

	public static final String ERROR_CAMPO_DATO_INEXISTENTE = "0012";
	private static final String ERROR_CAMPO_DATO_INEXISTENTE_MSG = "El campo dato no existe";

	public static final String ERROR_CAMPO_TABLA_INEXISTENTE = "0013";
	private static final String ERROR_CAMPO_TABLA_INEXISTENTE_MSG = "El campo tabla no existe";

	public static final String ERROR_CAMPO_FILA_TABLA_INEXISTENTE = "0014";
	private static final String ERROR_CAMPO_FILA_TABLA_INEXISTENTE_MSG = "El campo fila no existe";

	public static final String ERROR_FECHA_INCORRECTA = "0015";
	private static final String ERROR_FECHA_INCORRECTA_MSG ="Fecha incorrecta";

	public static final String ERROR_CAMPO_DATO_SIN_ETIQUETA = "0016";
	private static final String ERROR_CAMPO_DATO_SIN_ETIQUETA_MSG = "Existe un campo sin valor en el atributo etiqueta";

	public static final String ERROR_AL_CREAR_CAMPO_DESCRIPTOR = "0017";
	private static final String ERROR_AL_CREAR_CAMPO_DESCRIPTOR_MSG =  "Error al crear el descriptor";

	public static final String ERROR_DOCUMENTOS_ELECTRONICOS_BINARIO_INEXISTENTE = "0018";
	private static final String ERROR_DOCUMENTOS_ELECTRONICOS_BINARIO_INEXISTENTE_MSG = "El campo binario del documento electrónico no está definido";

	public static final String ERROR_DOCUMENTO_BINARIO_INVALIDO = "0019";
	private static final String ERROR_DOCUMENTO_BINARIO_INVALIDO_MSG = "Error al procesar el fichero binario del documento electrónico";

	public static final String ERROR_UBICACION_INCORRECTA_DOCUMENTO_ELECTRONICO = "0020";
	private static final String ERROR_UBICACION_INCORRECTA_DOCUMENTO_ELECTRONICO_MSG = "Si el origen de almacenamiento del documento, no es un repositorio, es necesaria la información para almacenarlo en uno.";

	public static final String ERROR_ALMACENAR_FICHERO_REPOSITORIO = "0021";
	private static final String ERROR_ALMACENAR_FICHERO_REPOSITORIO_MSG = "Error al almacenar el fichero en el repositorio";

	public static final String ERROR_ALMACENAR_FICHERO_INTERNO = "0022";
	private static final String ERROR_ALMACENAR_FICHERO_INTERNO_MSG = "Error al almacenar el fichero en el repositorio";

	public static final String ERROR_CONFIGURACION_SISTEMA_ARCHIVO = "0023";
	private static final String ERROR_CONFIGURACION_SISTEMA_ARCHIVO_MSG = "Error en la configuración general. Para más información revisar los ficheros de logs de la aplicación";

	public static final String ERROR_OBTENER_IDENTIFICADOR_INTERNO_FICHERO = "0024";
	private static final String ERROR_OBTENER_IDENTIFICADOR_INTERNO_FICHERO_MSG = "Error al obtener el identificador documento interno";

	public static final String ERROR_REPOSITORIO_NO_CONFIGURADO = "0025";
	private static final String ERROR_REPOSITORIO_NO_CONFIGURADO_MSG = "El repositorio documental no está configurado";

	public static final String ERROR_DEPOSITO_ELECTRONICO_INEXISTENTE = "0026";
	private static final String ERROR_DEPOSITO_ELECTRONICO_INEXISTENTE_MSG = "No existe el repositorio documental electrónico";

	public static final String ERROR_REPOSITORIO_DESTINO_NO_ES_ECM = "0027";
	private static final String ERROR_REPOSITORIO_DESTINO_NO_ES_ECM_MSG = "El repositorio de almacenamiento del documento no es un ECM válido";

	public static final String ERROR_SIN_REPOSITORIO_ALMACENAMIENTO_DEFECTO = "0028";
	private static final String ERROR_SIN_REPOSITORIO_ALMACENAMIENTO_DEFECTO_MSG ="No se ha definido el identificador del repositorio donde se almacenará el documento";

	public static final String ERROR_VALIDANDO_RELACION_ENTREGA = "0029";
	private static final String ERROR_VALIDANDO_RELACION_ENTREGA_MSG = "Error al validar la unidad documental en el cuadro";

	public static final String ERROR_TIPO_SISTEMA_EXTERNO_NO_SOPORTADO = "0030";
	private static final String ERROR_TIPO_SISTEMA_EXTERNO_NO_SOPORTADO_MSG = "Tipo de Sistema Externo no soportado";

	public static final String ERROR_NO_EXISTE_ORGANO_EN_SISTEMA_EXTERNO_CON_CODIGO = "0031";
	private static final String ERROR_NO_EXISTE_ORGANO_EN_SISTEMA_EXTERNO_CON_CODIGO_MSG = "No existe ningún órgano externo con ese código";

	public static final String ERROR_NO_EXISTE_ORGANO_EN_SISTEMA_EXTERNO_CON_ID = "0032";
	private static final String ERROR_NO_EXISTE_ORGANO_EN_SISTEMA_EXTERNO_CON_ID_MSG = "No existe ningún órgano externo con ese identificador";

	public static final String ERROR_UNIDAD_DOCUMENTAL_ESTA_DUPLICADA = "0033";
	private static final String ERROR_UNIDAD_DOCUMENTAL_ESTA_DUPLICADA_MSG = "Unidad Documental Duplicada en el Sistema";


	public static final String ERROR_AL_OBTENER_EL_DOCUMENTO_ORIGEN = "0034";
	private static final String ERROR_AL_OBTENER_EL_DOCUMENTO_ORIGEN_MSG = "Error al obtener el documento origen";

	public static final String ERROR_VALOR_INCORRECTO = "0035";
	private static final String ERROR_VALOR_INCORRECTO_MSG = "Valor Incorrecto";


	//ERRORES
	private static final HashMap<String, String> errores = new HashMap<String, String>();

	static{
		//PARAMETROS
		errores.put(ERROR_PROCESAR_CONTENIDO_XML, ERROR_PROCESAR_CONTENIDO_XML_MSG);

		//AUTH
		errores.put(ERROR_AUTENTICACION_USUARIO, ERROR_AUTENTICACION_USUARIO_MSG);
		errores.put(ERROR_SIN_PERMISOS,ERROR_SIN_PERMISOS_MSG);

		//CF
		errores.put(ERROR_ELEMENTO_PADRE_SERIE_INCORRECTO, ERROR_ELEMENTO_PADRE_SERIE_INCORRECTO_MSG);
		errores.put(ERROR_ELEMENTO_PADRE_NO_VIGENTE, ERROR_ELEMENTO_PADRE_NO_VIGENTE_MSG);
		errores.put(ERROR_ELEMENTO_PADRE_INEXSTENTE, ERROR_ELEMENTO_PADRE_INEXSTENTE_MSG);

		//OBLIGATORIO
		errores.put(ERROR_INFORMACION_REQUERIDA, ERROR_INFORMACION_REQUERIDA_MSG);

		//GENERIC
		errores.put(ERROR_EXCEPTION, ERROR_EXCEPTION_MSG);

		//Sistemas Externos
		errores.put(ERROR_GESTOR_ESTRUCTURA_ORGANIZATIVA, ERROR_GESTOR_ESTRUCTURA_ORGANIZATIVA_MSG);

		errores.put(ERROR_LONGITUD_INCORRECTA, ERROR_LONGITUD_INCORRECTA_MSG);

		errores.put(ERROR_NO_EXISTE_PRODUCTOR_UNIDAD_DOCUMENTAL, ERROR_NO_EXISTE_PRODUCTOR_UNIDAD_DOCUMENTAL_MSG);

		errores.put(ERROR_CAMPO_DATO_INEXISTENTE, ERROR_CAMPO_DATO_INEXISTENTE_MSG);

		errores.put(ERROR_CAMPO_TABLA_INEXISTENTE, ERROR_CAMPO_TABLA_INEXISTENTE_MSG);

		errores.put(ERROR_OPERACION_NO_DISPONIBLE, ERROR_OPERACION_NO_DISPONIBLE_MSG);

		errores.put(ERROR_CAMPO_FILA_TABLA_INEXISTENTE, ERROR_CAMPO_FILA_TABLA_INEXISTENTE_MSG);

		errores.put(ERROR_FECHA_INCORRECTA, ERROR_FECHA_INCORRECTA_MSG);

		errores.put(ERROR_CAMPO_DATO_SIN_ETIQUETA, ERROR_CAMPO_DATO_SIN_ETIQUETA_MSG);

		errores.put(ERROR_AL_CREAR_CAMPO_DESCRIPTOR, ERROR_AL_CREAR_CAMPO_DESCRIPTOR_MSG);

		errores.put(ERROR_DOCUMENTOS_ELECTRONICOS_BINARIO_INEXISTENTE, ERROR_DOCUMENTOS_ELECTRONICOS_BINARIO_INEXISTENTE_MSG);

		errores.put(ERROR_DOCUMENTO_BINARIO_INVALIDO, ERROR_DOCUMENTO_BINARIO_INVALIDO_MSG);

		errores.put(ERROR_UBICACION_INCORRECTA_DOCUMENTO_ELECTRONICO, ERROR_UBICACION_INCORRECTA_DOCUMENTO_ELECTRONICO_MSG);

		errores.put(ERROR_ALMACENAR_FICHERO_REPOSITORIO, ERROR_ALMACENAR_FICHERO_REPOSITORIO_MSG);

		errores.put(ERROR_ALMACENAR_FICHERO_INTERNO, ERROR_ALMACENAR_FICHERO_INTERNO_MSG);

		errores.put(ERROR_CONFIGURACION_SISTEMA_ARCHIVO, ERROR_CONFIGURACION_SISTEMA_ARCHIVO_MSG);

		errores.put(ERROR_OBTENER_IDENTIFICADOR_INTERNO_FICHERO, ERROR_OBTENER_IDENTIFICADOR_INTERNO_FICHERO_MSG);

		errores.put(ERROR_REPOSITORIO_NO_CONFIGURADO, ERROR_REPOSITORIO_NO_CONFIGURADO_MSG);

		errores.put(ERROR_DEPOSITO_ELECTRONICO_INEXISTENTE, ERROR_DEPOSITO_ELECTRONICO_INEXISTENTE_MSG);

		errores.put(ERROR_REPOSITORIO_DESTINO_NO_ES_ECM, ERROR_REPOSITORIO_DESTINO_NO_ES_ECM_MSG);

		errores.put(ERROR_SIN_REPOSITORIO_ALMACENAMIENTO_DEFECTO,ERROR_SIN_REPOSITORIO_ALMACENAMIENTO_DEFECTO_MSG);

		errores.put(ERROR_VALIDANDO_RELACION_ENTREGA,ERROR_VALIDANDO_RELACION_ENTREGA_MSG);

		errores.put(ERROR_TIPO_SISTEMA_EXTERNO_NO_SOPORTADO,ERROR_TIPO_SISTEMA_EXTERNO_NO_SOPORTADO_MSG);

		errores.put(ERROR_NO_EXISTE_ORGANO_EN_SISTEMA_EXTERNO_CON_CODIGO,ERROR_NO_EXISTE_ORGANO_EN_SISTEMA_EXTERNO_CON_CODIGO_MSG);

		errores.put(ERROR_NO_EXISTE_ORGANO_EN_SISTEMA_EXTERNO_CON_ID, ERROR_NO_EXISTE_ORGANO_EN_SISTEMA_EXTERNO_CON_ID_MSG);

		errores.put(ERROR_UNIDAD_DOCUMENTAL_ESTA_DUPLICADA, ERROR_UNIDAD_DOCUMENTAL_ESTA_DUPLICADA_MSG);

		errores.put(ERROR_AL_OBTENER_EL_DOCUMENTO_ORIGEN, ERROR_AL_OBTENER_EL_DOCUMENTO_ORIGEN_MSG);

		errores.put(ERROR_VALOR_INCORRECTO, ERROR_VALOR_INCORRECTO_MSG);

	}

	//Parámetros

	private static final HashMap<String, String> parametros = new HashMap<String, String>();

	public static final String PARAMETRO_OBJETO_INFO_TRANSFERENCIA = "P-001";
	private static final String PARAMETRO_OBJETO_INFO_TRANSFERENCIA_MSG = "Información de la Transferencia";

	public static final String PARAMETRO_CREAR_SERIE = "P-002";
	private static final String PARAMETRO_CREAR_SERIE_MSG = "Creación de Serie";

	public static final String PARAMETRO_CREAR_PREVISION = "P-003";
	private static final String PARAMETRO_CREAR_PREVISION_MSG = "Creación de Previsión";

	public static final String PARAMETRO_CREAR_DETALLE_PREVISION = "P-004";
	private static final String PARAMETRO_CREAR_DETALLE_PREVISION_MSG = "Creación de Detalle Previsión";

	public static final String PARAMETRO_CREAR_RELACION_ENTREGA = "P-005";
	private static final String PARAMETRO_CREAR_RELACION_ENTREGA_MSG = "Creación de Relación de Entrega";

	public static final String PARAMETRO_OBJETO_INFO_SERIE = "P-006";
	private static final String PARAMETRO_OBJETO_INFO_SERIE_MSG = "Información de la serie";

	public static final String PARAMETRO_ANIO_TRANSFERENCIA = "P-007";
	private static final String PARAMETRO_ANIO_TRANSFERENCIA_MSG = "Año de la transferencia";

	public static final String PARAMETRO_CODIGO_SISTEMA_TRAMITADOR =  "P-008";
	private static final String PARAMETRO_CODIGO_SISTEMA_TRAMITADOR_MSG = "Código del sistema tramitador";

	public static final String PARAMETRO_NOMBRE_SISTEMA_TRAMITADOR =  "P-009";
	private static final String PARAMETRO_NOMBRE_SISTEMA_TRAMITADOR_MSG = "Nombre del sistema tramitador";

	public static final String PARAMETRO_SISTEMA_TRAMITADOR =  "P-010";
	private static final String PARAMETRO_SISTEMA_TRAMITADOR_MSG = "Sistema tramitador";

	public static final String PARAMETRO_CODIGO_PROCEDIMIENTO =  "P-011";
	private static final String PARAMETRO_CODIGO_PROCEDIMIENTO_MSG = "Código de procedimiento";

	public static final String PARAMETRO_INFO_UDOC = "P-012";
	private static final String PARAMETRO_INFO_UDOC_MSG = "Información de unidad documental";

	public static final String PARAMETRO_CONTENIDO_XML = "P-013";
	private static final String PARAMETRO_CONTENIDO_XML_MSG = "Información de la unidad documental";

	public static final String PARAMETRO_IDENTIFICACION_UDOC = "P-014";
	private static final String PARAMETRO_IDENTIFICACION_UDOC_MSG = "Identificación de la unidad documental";

	public static final String PARAMETRO_CREAR_UNIDAD_DOCUMENTAL = "P-015";
	private static final String PARAMETRO_CREAR_UNIDAD_DOCUMENTAL_MSG = "Añadir unidad documental a relación de entrega";

	public static final String PARAMETRO_FECHA_INICIAL = "P-016";
	private static final String PARAMETRO_FECHA_INICIAL_MSG = "Fecha Inicial";

	public static final String PARAMETRO_FECHA_FINAL = "P-017";
	private static final String PARAMETRO_FECHA_FINAL_MSG = "Fecha Final";

	public static final String PARAMETRO_REPOSITORIO_UBICACION = "P-018";
	private static final String PARAMETRO_REPOSITORIO_UBICACION_MSG = "Repositorio Ubicación";

	public static final String PARAMETRO_REPOSITORIO_ALMACENAMIENTO = "P-019";
	private static final String PARAMETRO_REPOSITORIO_ALMACENAMIENTO_MSG = "Repositorio Almacenamiento";

	public static final String PARAMETRO_VALIDAR_RELACION = "P-020";
	private static final String PARAMETRO_VALIDAR_RELACION_MSG = "Validar Relación de Entrega";

	public static final String PARAMETRO_VALOR = "P-21";
	private static final String PARAMETRO_VALOR_MSG = "valor";

	public static final String PARAMETRO_SEPARADOR_FECHA = "P-22";
	private static final String PARAMETRO_SEPARADOR_FECHA_MSG = "SEP";

	public static final String PARAMETRO_FORMATO_FECHA = "P-23";
	private static final String PARAMETRO_FORMATO_FECHA_MSG = "FMT";

	public static final String PARAMETRO_ID_LISTA = "P-24";
	private static final String PARAMETRO_ID_LISTA_MSG = "idLista";

	public static final String PARAMETRO_TIPO_ATRIBUTO = "P-25";
	private static final String PARAMETRO_TIPO_ATRIBUTO_MSG = "tipoAtributo";

	public static final String PARAMETRO_TITULO= "P-26";
	private static final String PARAMETRO_TITULO_MSG = "Titulo";

	public static final String PARAMETRO_RELACION_ENTREGA = "P-27";
	private static final String PARAMETRO_RELACION_ENTREGA_MSG = "Relacion de Entrega";

	public static final String PARAMETRO_NUMERO_EXPEDIENTE = "P-28";
	private static final String PARAMETRO_NUMERO_EXPEDIENTE_MSG = "Numero de Expediente";



	static{
		parametros.put(PARAMETRO_OBJETO_INFO_TRANSFERENCIA, PARAMETRO_OBJETO_INFO_TRANSFERENCIA_MSG);
		parametros.put(PARAMETRO_CREAR_SERIE, PARAMETRO_CREAR_SERIE_MSG);
		parametros.put(PARAMETRO_CREAR_PREVISION, PARAMETRO_CREAR_PREVISION_MSG);
		parametros.put(PARAMETRO_CREAR_DETALLE_PREVISION, PARAMETRO_CREAR_DETALLE_PREVISION_MSG);
		parametros.put(PARAMETRO_CREAR_RELACION_ENTREGA, PARAMETRO_CREAR_RELACION_ENTREGA_MSG);
		parametros.put(PARAMETRO_OBJETO_INFO_SERIE, PARAMETRO_OBJETO_INFO_SERIE_MSG);
		parametros.put(PARAMETRO_ANIO_TRANSFERENCIA, PARAMETRO_ANIO_TRANSFERENCIA_MSG);
		parametros.put(PARAMETRO_CODIGO_SISTEMA_TRAMITADOR, PARAMETRO_CODIGO_SISTEMA_TRAMITADOR_MSG);
		parametros.put(PARAMETRO_NOMBRE_SISTEMA_TRAMITADOR, PARAMETRO_NOMBRE_SISTEMA_TRAMITADOR_MSG);
		parametros.put(PARAMETRO_SISTEMA_TRAMITADOR, PARAMETRO_SISTEMA_TRAMITADOR_MSG);
		parametros.put(PARAMETRO_CODIGO_PROCEDIMIENTO, PARAMETRO_CODIGO_PROCEDIMIENTO_MSG);
		parametros.put(PARAMETRO_INFO_UDOC, PARAMETRO_INFO_UDOC_MSG);
		parametros.put(PARAMETRO_CONTENIDO_XML, PARAMETRO_CONTENIDO_XML_MSG);
		parametros.put(PARAMETRO_IDENTIFICACION_UDOC, PARAMETRO_IDENTIFICACION_UDOC_MSG);
		parametros.put(PARAMETRO_CREAR_UNIDAD_DOCUMENTAL, PARAMETRO_CREAR_UNIDAD_DOCUMENTAL_MSG);
		parametros.put(PARAMETRO_FECHA_INICIAL, PARAMETRO_FECHA_INICIAL_MSG);
		parametros.put(PARAMETRO_FECHA_FINAL, PARAMETRO_FECHA_FINAL_MSG);
		parametros.put(PARAMETRO_REPOSITORIO_UBICACION, PARAMETRO_REPOSITORIO_UBICACION_MSG);
		parametros.put(PARAMETRO_REPOSITORIO_ALMACENAMIENTO, PARAMETRO_REPOSITORIO_ALMACENAMIENTO_MSG);
		parametros.put(PARAMETRO_VALIDAR_RELACION, PARAMETRO_VALIDAR_RELACION_MSG);
		parametros.put(PARAMETRO_VALOR, PARAMETRO_VALOR_MSG);
		parametros.put(PARAMETRO_SEPARADOR_FECHA, PARAMETRO_SEPARADOR_FECHA_MSG);
		parametros.put(PARAMETRO_FORMATO_FECHA, PARAMETRO_FORMATO_FECHA_MSG);
		parametros.put(PARAMETRO_ID_LISTA,PARAMETRO_ID_LISTA_MSG);
		parametros.put(PARAMETRO_TIPO_ATRIBUTO, PARAMETRO_TIPO_ATRIBUTO_MSG);
		parametros.put(PARAMETRO_RELACION_ENTREGA, PARAMETRO_RELACION_ENTREGA_MSG);
		parametros.put(PARAMETRO_TITULO, PARAMETRO_TITULO_MSG);
		parametros.put(PARAMETRO_NUMERO_EXPEDIENTE, PARAMETRO_NUMERO_EXPEDIENTE_MSG);
	}


	public static final String getMensajeError(String codigoError){

		String mensaje = errores.get(codigoError);

		if(mensaje == null){
			mensaje = ERROR_INESPERADO_MSG;
		}
		return mensaje;
	}


	public static final String getParametro(String codigoParametro){
		String mensaje = parametros.get(codigoParametro);

		return mensaje;
	}
}
