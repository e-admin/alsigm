package ieci.tecdoc.sgm.autenticacion.utils;

public class Defs {
	public static final String USUARIO = "username";
	public static final String PASSWORD = "password";
	
	public static final String PLUGIN_REDIRCONSULTA = "redirConsulta";
	public static final String PLUGIN_REDIRPAGOELECTRONICO = "redirPagoElectronico";
	public static final String PLUGIN_REDIRNOTIFICACION = "redirNotificacion";
	public static final String PLUGIN_REDIRREGISTROTELEMATICO = "redirRegistroTelematico";
	public static final String PLUGIN_REDIRCERTIFICACION = "redirCertificacion";
	public static final String PLUGIN_REDIRCONSULTAREGISTROTELEMATICO = "redirConsultaRegistroTelematico";
	
	public static final String ACCESO_SEL = "ACCESO_SEL";
	public static final String TIPO_ACCESO = "TIPO_ACCESO";
	
	public static final String XML_DATA = "XML_DATA";
	public static final String SESION_ID = "SESION_ID";
	public static final String REDIRECCION = "REDIRECCION";
	public static final String URL_REDIRECCION = "URL_REDIRECCION";
	public static final String URL_PUERTO = "URL_PUERTO";
	public static final String TRAMITE_ID = "tramiteId";
	public static final String ENTIDAD_ID = "ENTIDAD_ID";
	public static final String LANG = "LANG";
	public static final String COUNTRY = "COUNTRY";
	
	public static final String CERTIFICADO_ID = "certificadoId";
	
	public static final String MENSAJE_ERROR = "MENSAJE_ERROR";
	public static final String MENSAJE_INFORMATIVO = "MENSAJE_INFORMATIVO";
	public static final String MENSAJE_ERROR_SELECCIONAR_CERTIFICADO = "mensaje_error.seleccionar_certificado";
	public static final String MENSAJE_ERROR_DETALLE = "MENSAJE_ERROR_DETALLE";
	public static final String MENSAJE_ERROR_LISTA_CERTIFICADOS = "mensaje_error.lista_certificados";
	public static final String MENSAJE_ERROR_OBTENER_TIPO_ACCESO = "mensaje_error.obtener_tipo_acceso";
	public static final String MENSAJE_ERROR_DESCONEXION = "mensaje_error.desconexion";
	public static final String MENSAJE_INFORMATIVO_DESCONEXION = "mensaje_informativo.desconexion";
	
	public static final String CERTIFICADO_SELECCIONADO = "CERTIFICADO_SELECCIONADO";
	public static final String MENSAJE_LOGIN = "MENSAJE_LOGIN";
	public static final String VALIDO_CERT_SIN_TRAMITE = "VALIDO_CERT_SIN_TRAMITE";
	public static final String VALIDO_CERT_CON_TRAMITE = "VALIDO_CERT_CON_TRAMITE";
	public static final String VALIDO_USER_SIN_TRAMITE = "VALIDO_USER_SIN_TRAMITE";
	public static final String VALIDO_USER_CON_TRAMITE = "VALIDO_USER_CON_TRAMITE";
	public static final String CERT_REVOCADO = "CERT_REVOCADO";
	public static final String CERT_YA_VALIDADO = "CERT_YA_VALIDADO";
	public static final String CERT_NO_VALIDO = "CERTIFICADO_NO_VALIDO";
	public static final String METODO_AUTH_NO_ACEPTADA = "METODO_AUTH_NO_ACEPTADA";
	
	public static final String CONFIG_NOMBRE_REGISTRO = "RegistroTelematico.nombre";
	public static final String CONFIG_ACCESO_PAGOELECTRONICO = "PagoElectronico.acceso";
	public static final String CONFIG_ACCESO_CONSULTA = "Consulta.acceso";
	public static final String CONFIG_ACCESO_NOTIFICACION = "Notificacion.acceso";
	
	//public static final String DEFAULT_SERVER_PORT = "defaultServerPort";
	public static final String IDIOMAS_DISPONIBLES = "IdiomasDisponibles";
	
	public static boolean isNuloOVacio(Object cadena) {
		if((cadena == null) || ("".equals(cadena)) || ("null".equals(cadena))) {
			return true;
		}
		return false;
	}
}
