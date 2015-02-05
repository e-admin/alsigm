package docelectronicos;

import java.util.Map;

import common.SigiaUtilConstants;



public class DocumentosConstants
{

	private static final StringBuffer buff = new StringBuffer();
	private static final String getKey(String innerKey)
	{
		buff.setLength(0);
		return buff.append("DocumentosConstants_").append(innerKey).toString();
	}

	public static final String DOCUMENT_TREE_KEY = getKey("DOCUMENT_TREE_KEY");
	public static final String REFRESH_VIEW_KEY = getKey("REFRESH_VIEW_KEY");
	public static final String OBJECT_ID_KEY = getKey("OBJECT_ID_KEY");
	public static final String OBJECT_TYPE_KEY = getKey("OBJECT_TYPE_KEY");
	public static final String DESCRIPTOR_KEY = getKey("DESCRIPTOR_KEY");
	public static final String UNIDAD_DOCUMENTAL_KEY = getKey("UNIDAD_DOCUMENTAL_KEY");
	public static final String SERIE_KEY = getKey("SERIE_KEY");
	public static final String FOLDER_CONTENT_KEY = getKey("FOLDER_CONTENT_KEY");
	public static final String FOLDER_KEY = getKey("FOLDER_KEY");
	public static final String FILE_INFO_KEY = getKey("FILE_INFO_KEY");
	public static final String NAVEGADOR_DOCUMENTOS_KEY = getKey("NAVEGADOR_DOCUMENTOS_KEY");


	public static final String ACCESO_DOCUMENTOS_IDELEMENTO_KEY = getKey("ACCESO_DOCUMENTOS_IDDOCUMENTO_KEY");
	public static final String ACCESO_DOCUMENTOS_IDTIPODOCUMENTO_KEY = getKey("ACCESO_DOCUMENTOS_IDTIPODOCUMENTO_KEY");

	//digitalizacion
	public static final String LISTA_TAREAS_A_GESTIONAR_KEY = getKey("LISTA_TAREAS_A_GESTIONAR_KEY");
	public static final String LISTA_TAREAS_PENDIENTES_KEY = getKey("LISTA_TAREAS_PENDIENTES_KEY");
	public static final String LISTA_FONDOS_KEY = getKey("LISTA_FONDOS_KEY");
	public static final String LISTA_LISTAS_DESCRIPTORAS_KEY = getKey("LISTA_LISTAS_DESCRIPTORAS_KEY");
	public static final String TAREA_KEY = getKey("TAREA_KEY");
	public static final String BUSQUEDA_X_DESCRIPTOR = getKey("BUSQUEDA_X_DESCRIPTOR");
	public static final String BUSQUEDA_X_EL_CUADRO = getKey("BUSQUEDA_X_EL_CUADRO");

	public static final String RESPUESTA_BUSQUEDA_CUADRO = getKey("RESPUESTA_BUSQUEDA_CUADRO");
	public static final String RESPUESTA_BUSQUEDA_ENTIDADES = getKey("RESPUESTA_BUSQUEDA_ENTIDADES");
	public static final String DESCRIPTOR_PARA_CREAR_TAREA_KEY = getKey("DESCRIPTOR_PARA_CREAR_TAREA_KEY");
	public static final String ELEMENTO_PARA_CREAR_TAREA_KEY = getKey("ELEMENTO_PARA_CREAR_TAREA_KEY");
	public static final String USUARIOS_CAPTURADORES_KEY = getKey("USUARIOS_CAPTURADORES_KEY");
	public static final String ESTADOS_TAREAS_KEY = getKey("ESTADOS_TAREAS_KEY");
	public static final String TAREAS_KEY = getKey("TAREAS_KEY");
	public static final String LISTA_TAREAS_CEDIBLES_KEY = getKey("LISTA_TAREAS_CEDIBLES_KEY");
	public static final String LISTA_TAREAS_KEY = getKey("LISTA_TAREAS_KEY");
	public static final String LISTA_TAREAS_ELIMINADAS_KEY = getKey("LISTA_TAREAS_ELIMINADAS_KEY");
	public static final String RESULTADO_BUSQUEDA_TERCEROS =  getKey("RESULTADO_BUSQUEDA_TERCEROS");

    public static final int TIPO_BUSQUEDA_POR_TAREA = 1;
    public static final int TIPO_BUSQUEDA_POR_USUARIO_CAPTURA = 2;

    public static final String CAPTURADOR_KEY = getKey("CAPTURADOR_KEY");

    public static Map estadoDocumento = SigiaUtilConstants.getConstantsMap(EstadoDocumento.class);

    public static Map estadoClasificador = SigiaUtilConstants.getConstantsMap(EstadoClasificador.class);

	public static final String [] IMAGE_EXTENSIONS = {
	        "GIF",
	        "JPE", "JPEG", "JPG",
	        "TIF", "TIFF"};

	public static final String LABEL_DOCUMENTOS_SEL_ELEMENTO="archigest.archivo.documentos.selElemento";
	public static final String LABEL_DOCUMENTOS_DIGITALIZACION_ESTADO="archigest.archivo.documentos.digitalizacion.estado";
	public static final String LABEL_DOCUMENTOS_DIGITALIZACION_TIPO_OBJ="archigest.archivo.documentos.digitalizacion.tipoObj";
	public static final String LABEL_DIGITALIZACION_BUSQUEDA_ESTADO="archigest.archivo.documentos.digitalizacion.busqueda.estado";
    public static final String LABEL_DIGITALIZACION_BUSQUEDA_FECHA="archigest.archivo.documentos.digitalizacion.busqueda.fecha";
    public static final String LABEL_DOCUMENTOS_CLASIFICADOR_NOMBRE="archigest.archivo.documentos.clasificador.form.nombre";
    public static final String LABEL_DOCUMENTOS_DOCUMENTO_NOMBRE="archigest.archivo.documentos.documento.form.nombre";
    public static final String LABEL_DOCUMENTOS_DOCUMENTO_NUEVO_DOC="archigest.archivo.documentos.documento.form.nuevoDocumento";

	public static final String ERROR_DIGITALIZACION_NECESARIO_USER_CAPTURADOR="archigest.archivo.documentos.digitalizacion.necesarioUserCapturador";
	public static final String ERROR_DIGITALIZACION_NECESARIO_SEL_TAREAS_A_CEDER="archigest.archivo.documentos.digitalizacion.necesarioSeleccionarTareasACeder";
	public static final String ERROR_DOC_ELECTRONICOS_DOCUMENTO_NO_ENCONTRADO="errors.documentosElectronicos.documento_no_encontrado";
	public static final String ERRORS_DIGITALIZACION_SELECCIONEUNELEMENTO = "errors.digitalizacion.seleccioneUnElemento";
    public static final String ERRORS_DIGITALIZACION_SELECCIONEUSUCAPTURA = "errors.digitalizacion.seleccioneUnUsuarioDeCaptura";

    public static final String ERROR_DOC_ELECTRONICOS_DOCUMENTO_EXTERNO_NO_ENCONTRADO="errors.documentosElectronicos.documento_externo_no_encontrado";

    public static final String ERROR_NOMBRE_FICHERO_INVALIDO = "error.ficheros.nombre.invalido";
    public static final String ERROR_EXTENSION_FICHERO_INVALIDA = "error.ficheros.extension.invalida";
    public static final String ERROR_ECM_NO_CONFIGURADOS = "error.repositorios.ecm.no.configurados";

    /**
     * Longitud máxima para la extensiónd de un fichero
     */
    public static final int LONGITUD_MAX_EXTENSION = 16;

}
