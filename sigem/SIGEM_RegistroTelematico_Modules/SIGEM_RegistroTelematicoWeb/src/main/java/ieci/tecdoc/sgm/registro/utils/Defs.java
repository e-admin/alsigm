package ieci.tecdoc.sgm.registro.utils;

public class Defs {
	public static final String XML_DATA = "XML_DATA";
	public static final String TRAMITE_ID = "tramiteId";
	public static final String NUMERO_EXPEDIENTE = "numExpediente";
	public static final String NUMERO_REGISTRO = "numRegistro";
	public static final String CERTIFICADO_ID = "certificadoId";
	public static final String OFICINA = "OFICINA";
	public static final String CERTIFICADO_DESC = "certificadoDesc";
	public static final String SESION_ID = "SESION_ID";
	public static final String FORMULARIO = "FORMULARIO";
	public static final String FECHA = "FECHA";
	public static final String SOLICITUD_REGISTRO = "SOLICITUD_REGISTRO";
	public static final String JUSTIFICANTE_REGISTRO = "JUSTIFICANTE_REGISTRO";
	public static final String INFORMACION_REGISTRO = "INFORMACION_REGISTRO";
	public static final String REQUEST = "REQUEST";
	public static final String XML_REQUEST_FILE = "XML_REQUEST_FILE";
	public static final String DATOS_A_FIRMAR = "DATOS_A_FIRMAR";
	public static final String DATOS_ESPECIFICOS = "DATOS_ESPECIFICOS";
	public static final String FIRMA = "FIRMA";
	public static final String CERTIFICADO = "CERTIFICADO";
	public static final String FIRMAR_SOLICITUD = "FIRMAR_SOLICITUD";
	public static final String DOCUMENTOS_REQUEST = "DOCUMENTOS_REQUEST";
	public static final String ID_ORGANO = "idOrgano";
	public static final String DESC_ORGANO = "descOrgano";
	public static final String HAY_VIRUS = "hayVirus";
	public static final String HAY_VIRUS_DOCUMENTOS = "hayVirusDocumentos";

	public static final String USUARIO = "username";
	public static final String PASSWORD = "password";

	public static final String SENDER_NAME = "NOMBRE_SOLICITANTE";
	public static final String SENDER_SURNAME = "APELLIDOS_SOLICITANTE";
	public static final String SENDER_COMPANY_NAME = "RAZON_SOCIAL";
	public static final String SENDER_NIF = "CIF";
	public static final String SENDER_MAIL = "CORREO_ELECTRONICO_SOLICITANTE";
	public static final String CERTIFICATE_ISSUER = "EMISOR_CERTIFICADO_SOLICITANTE";
	public static final String CERTIFICATE_SERIALNUMBER = "SOLICITANTE_CERTIFICADO_SN";
	public static final String AUTH_TYPE_CHOOSEN = "SOLICITANTE_INQUALITY";

	public static final String CERTIFICADOS = "CERTIFICADOS";
	public static final String CERTIFICADO_SELECCIONADO = "CERTIFICADO_SELECCIONADO";

	public static final String TRAMITES = "TRAMITES";
	public static final String TRAMITE_SELECCIONADO = "TRAMITE_SELECCIONADO";

	public static final String TIPO_ACCESO = "TIPO_ACCESO";
	public static final String ACCESO_SEL = "ACCESO_SEL";

	public static final String MENSAJE_LOGIN = "MENSAJE_LOGIN";
	public static final String VALIDO_CERT_SIN_TRAMITE = "VALIDO_CERT_SIN_TRAMITE";
	public static final String VALIDO_CERT_CON_TRAMITE = "VALIDO_CERT_CON_TRAMITE";
	public static final String VALIDO_USER_SIN_TRAMITE = "VALIDO_USER_SIN_TRAMITE";
	public static final String VALIDO_USER_CON_TRAMITE = "VALIDO_USER_CON_TRAMITE";
	public static final String CERT_REVOCADO = "CERT_REVOCADO";
	public static final String CERT_YA_VALIDADO = "CERT_YA_VALIDADO";
	public static final String CERT_NO_VALIDO = "CERTIFICADO_NO_VALIDO";
	public static final String METODO_AUTH_NO_ACEPTADA = "METODO_AUTH_NO_ACEPTADA";

	public static final String PLUGIN_ORGANISMO = "organismo";
	public static final String PLUGIN_HTTPS_PORT = "httpsPort";
	public static final String PLUGIN_HTTPS_USER_PORT = "httpsUserPort";
	public static final String PLUGIN_TMP_PATH_UPLOAD = "tmpUploadPath";
	public static final String PLUGIN_TMP_PATH_XML = "tmpXmlPath";
	public static final String PLUGIN_TMP_PATH_TRAMITES = "tramitesPath";
	public static final String PLUGIN_AFIRMA = "aFirma";
	public static final String PLUGIN_PLANTILLA = "plantilla";
	public static final String PLUGIN_CERTIFICADO = "certificado";
	public static final String PLUGIN_REDIRAUTENTICACION = "redirAutenticacion";
	public static final String PLUGIN_INICIAREXPEDIENTECONVIRUS = "iniciarExpedienteConVirus";
	public static final String PLUGIN_REGISTRARCONVIRUS = "registrarConVirus";
	public static final String PLUGIN_INICIAREXPEDIENTE = "iniciarExpediente";
	public static final String PLUGIN_INICIAREXPEDIENTEERRORREGISTROESTADOERROR = "iniciarExpedienteErrorRegistroEstadoError";
	public static final String PLUGIN_SUBSANACIONSINNUMEROREGISTROINICIAL_CODIGO_OFICINA = "subsanacionSinNumeroRegistroInicial.codigoOficina";
	public static final String PLUGIN_SUBSANACIONSINNUMEROREGISTROINICIAL_FIRMAR_SOLICITUD = "subsanacionSinNumeroRegistroInicial.firmarSolicitud";

	//Configuración envío de correos al fallar el inicio de un expediente
	public static final String PLUGIN_INICIAREXPEDIENTEERROR_ENVIAR_EMAIL_ORIGEN = "iniciarExpendienteError.enviarEmail.origen";
	public static final String PLUGIN_INICIAREXPEDIENTEERROR_ENVIAR_EMAIL_DESTINO = "iniciarExpendienteError.enviarEmail.destino";
	public static final String PLUGIN_INICIAREXPEDIENTEERROR_ENVIAR_EMAIL_ASUNTO = "iniciarExpendienteError.enviarEmail.asunto";

	public static final String TAG_ED_ENTIDAD_REPRESENTADA = "Entidad_Representada";
	public static final String TAG_ED_DOMICILIO_NOTIFICACION = "Domicilio_Notificación";
	public static final String TAG_ED_LOCALIDAD = "Localidad";
	public static final String TAG_ED_PROVINCIA = "Provincia";
	public static final String TAG_ED_CODIGO_POSTAL = "Código_Postal";
	public static final String TAG_ED_TIPO = "Tipo";
	public static final String TAG_ED_ACTO_IMPUGNADO = "Acto_Impugnado";
	public static final String TAG_ED_EXPONE = "Expone";
	public static final String TAG_ED_SUPLICA = "Suplica";
	public static final String TAG_ED_ORGANO_DESTINATARIO = "Órgano_Destinatario";
	public static final String TAG_ED_LUGAR_PRESENTACIÓN = "Lugar_Presentación";
	public static final String TAG_DATOS_REGISTRO = "Datos_Registro";

	public static final String TAG_ED_DOCUMENTOS = "Documentos";
	public static final String TAG_ED_DOCUMENTO = "Documento";
	public static final String TAG_ED_SIZE = "Size";
	public static final String TAG_ED_NUMERO_DOCUMENTO = "Numero_Documento";
	public static final String TAG_ED_EXTENSION = "Extension";
	public static final String TAG_ED_NOMBRE = "Nombre";
	public static final String TAG_ED_DESCRIPCION = "Descripcion";
	public static final String TAG_ED_EMAIL_SOLICITANTE = "Email_Solicitante";
	public static final String TAG_ED_DOCUMENTOS_ANEXADOS = "DocumentosAnexados";
	public static final String TAG_ED_INFORMACION_DOCUMENTOS = "Informacion_Documentos";
	public static final String TAG_ED_TRAMITE = "Tramite";
	public static final String TAG_ED_CORREO_ELECTRONICO = "Correo_Electronico";
	public static final String TAG_ED_REMITENTE = "Remitente";

	public static final String MENSAJE_INFORMATIVO = "MENSAJE_INFORMATIVO";
	public static final String MENSAJE_ERROR = "MENSAJE_ERROR";
	public static final String MENSAJE_ERROR_DETALLE = "MENSAJE_ERROR_DETALLE";
	public static final String MENSAJE_INFORMATIVO_DESCONEXION = "mensaje_informativo.desconexion";
	public static final String MENSAJE_ERROR_DESCONEXION = "mensaje_error.desconexion";
	public static final String MENSAJE_ERROR_ENVIO_SOLICITUD = "mensaje_error.envio_solicitud";
	public static final String MENSAJE_ERROR_ENVIO_SOLICITUD_DOCUMENTOS_DUPLICADOS = "mensaje_error.envio_solicitud.documentos_duplicados";
	public static final String MENSAJE_ERROR_LISTA_CERTIFICADOS = "mensaje_error.lista_certificados";
	public static final String MENSAJE_ERROR_VALIDAR_USUARIO = "mensaje_error.validar_usuario";
	public static final String MENSAJE_ERROR_OBTENER_JUSTIFICANTE = "mensaje_error.obtener_justificante";
	public static final String MENSAJE_ERROR_OBTENER_TIPO_ACCESO = "mensaje_error.obtener_tipo_acceso";
	public static final String MENSAJE_ERROR_PREPARAR_SOLICITUD = "mensaje_error.preparar_solicitud";
	public static final String MENSAJE_ERROR_REALIZAR_SOLICITUD = "mensaje_error.realizar_solicitud";
	public static final String MENSAJE_ERROR_REGISTRAR_SOLICITUD = "mensaje_error.registrar_solicitud";
	public static final String MENSAJE_ERROR_CERTIFICADO_FIRMA = "mensaje_error.certificado_firma";
	public static final String MENSAJE_ERROR_SELECCIONAR_CERTIFICADO = "mensaje_error.seleccionar_certificado";
	public static final String MENSAJE_ERROR_SOLICITUDES_REGISTRO = "mensaje_error.solicitudes_registro";
	public static final String MENSAJE_ERROR_INICIO_EXPEDIENTE = "mensaje_error.inicio_expediente";
	public static final String MENSAJE_ERROR_NO_EXISTE = "mensaje_error.no_tramite";
	public static final String MENSAJE_ERROR_SUBSANACION_SIN_OFICINA_REGISTRO = "mensaje_error.envio_solicitud.subsanacion_sin_oficina_registro";

	public static final String IDIOMAS_DISPONIBLES = "IdiomasDisponibles";
	public static final String LANG = "LANG";
	public static final String COUNTRY = "COUNTRY";
	public static final String REFRESCO = "refresco";
	public static final String EMPTY_STRING = "";
	public static final String MENSAJE_ERROR_DOCUMENT_MAX_LENGTH = "mensaje_error.envio_solicitud.document_max_length";
}
