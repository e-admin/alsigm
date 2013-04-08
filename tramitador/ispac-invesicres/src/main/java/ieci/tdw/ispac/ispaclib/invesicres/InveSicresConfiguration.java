package ieci.tdw.ispac.ispaclib.invesicres;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.util.PropertiesConfiguration;

/**
 * Clase que gestiona la configuración de SICRES.
 *
 */
public class InveSicresConfiguration extends PropertiesConfiguration {

	private static InveSicresConfiguration mInstance = null;
	
	private static final String DEFAULT_CONFIG_FILENAME = "invesicres.properties";

	public final static String USER_MANAGER = "USER_MANAGER";

    public final static String POOLNAME_PATTERN = "POOLNAME_PATTERN";
    public final static String POOL_NAME = "POOL_NAME";

    //public final static String SICRES_OFFICE_CODE = "SICRES_OFFICE_CODE";
    //public final static String SICRES_UNIT_CODE = "SICRES_UNIT_CODE";

	//-------------------------------------------------
	//     TABLAS SICRES
	//-------------------------------------------------
	public final static String DATE_TIME_FORMAT = "DATE_TIME_FORMAT";
	public final static String DATE_FORMAT = "DATE_FORMAT";

	
	//-------------------------------------------------
	//     TABLAS SICRES
	//-------------------------------------------------

	//Personas físicas
	public final static String TBL_PFIS = "TBL_PFIS";
	//Personas jurídicas
	public final static String TBL_PJUR = "TBL_PJUR";
	//Interesados de un registro
	public final static String TBL_REGINT = "TBL_REGINT";
	//Unidades Administrativas
	public final static String TBL_ORGS = "TBL_ORGS";
	//Oficinas de Registro
	public final static String TBL_OFIC = "TBL_OFIC";
	//Tipo de documentos para personas jurídicas
	public final static String TBL_TYPEDOC = "TBL_TYPEDOC";
	//Tipos de direcciones telemáticas
	public final static String TBL_TYPEADDRESS = "TBL_TYPEADDRESS";
	//Direcciones de una persona
	public final static String TBL_ADDRESS = "TBL_ADDRESS";
	//Direcciones Postales
	public final static String TBL_DOM = "TBL_DOM";
	//Direcciones Telemáticas
	public final static String TBL_ADDRTEL = "TBL_ADDRTEL";
	//Contadores
	public final static String TBL_CONTADOR = "TBL_CONTADOR";
	//Nombre de la secuencia para los identificadores
	public final static String SEQCNT = "SEQCNT";
	//Contadores de registro por oficina
	public final static String TBL_CNTOFICINA = "TBL_CNTOFICINA"; 
	//Contadores de registro central
	public final static String TBL_CNTCENTRAL = "TBL_CNTCENTRAL";
	//Tipos de Asunto
	public final static String TBL_CA = "TBL_CA";
	//tipos de transportes
	public final static String TBL_TT = "TBL_TT";
	//Libros de registro
	public final static String TBL_BOOKS = "TBL_BOOKS";
	//Relación oficinas con libros de registro
	public final static String TBL_BOOKOFIC = "TBL_BOOKOFIC";

	//-------------------------------------------------
	// REGISTRO DE ENTRADA (CAMPOS ARCHIVADOR)
	//-------------------------------------------------

	//Identificador del Archivador de Registro de Entrada
	public final static String ENT_ID_ARCH = "ENT_ID_ARCH";
	//Número de Registro
	public final static String ENT_NUMERO_REGISTRO = "ENT_NUMERO_REGISTRO";
	//Fecha de Registro
	public final static String ENT_FECHA_REGISTRO = "ENT_FECHA_REGISTRO";
	//Usuario que crea el registro
	public final static String ENT_USUARIO = "ENT_USUARIO"; 
	//Fecha de Trabajo
	public final static String ENT_FECHA_TRABAJO = "ENT_FECHA_TRABAJO";
	//Oficina de Registro
	public final static String ENT_OFICINA_REGISTRO = "ENT_OFICINA_REGISTRO";
	//Estado del Registro
	public final static String ENT_ESTADO = "ENT_ESTADO";
	//Origen del Registro
	public final static String ENT_ORIGEN = "ENT_ORIGEN";
	//Destino del Registro
	public final static String ENT_DESTINO = "ENT_DESTINO";
	//Nombres del Primer Remitente
	public final static String ENT_REMITENTE = "ENT_REMITENTE";
	//Numero de Registro Original
	public final static String ENT_NUMERO_REGISTRO_ORIGINAL = "ENT_NUMERO_REGISTRO_ORIGINAL";
	//Tipo del Registro Original
	public final static String ENT_TIPO_REGISTRO_ORIGINAL = "ENT_TIPO_REGISTRO_ORIGINAL";
	//Fecha del Registro Original
	public final static String ENT_FECHA_REGISTRO_ORIGINAL = "ENT_FECHA_REGISTRO_ORIGINAL";
	//Entidad Registral Original
	public final static String ENT_REGISTRO_ORIGINAL = "ENT_REGISTRO_ORIGINAL";
	//Tipo de Transporte
	public final static String ENT_TIPO_TRANSPORTE = "ENT_TIPO_TRANSPORTE";
	//Numero de Transporte
	public final static String ENT_NUMERO_TRANSPORTE = "ENT_NUMERO_TRANSPORTE";
	//Tipo de Asunto
	public final static String ENT_TIPO_ASUNTO = "ENT_TIPO_ASUNTO";
	//Resumen del documento
	public final static String ENT_RESUMEN = "ENT_RESUMEN";

	//-------------------------------------------------
	// REGISTRO DE SALIDA (CAMPOS ARCHIVADOR)
	//-------------------------------------------------

	//Identificador del Archivador de Registro de Salida
	public final static String SAL_ID_ARCH = "SAL_ID_ARCH";
	//Numero de Registro
	public final static String SAL_NUMERO_REGISTRO = "SAL_NUMERO_REGISTRO";
	//Fecha de Registro
	public final static String SAL_FECHA_REGISTRO = "SAL_FECHA_REGISTRO";
	//Usuario creador del registro
	public final static String SAL_USUARIO = "SAL_USUARIO";
	//Fecha de Trabajo
	public final static String SAL_FECHA_TRABAJO = "SAL_FECHA_TRABAJO";
	//Oficina de Registro
	public final static String SAL_OFICINA_REGISTRO = "SAL_OFICINA_REGISTRO";
	//Estado del Registro
	public final static String SAL_ESTADO = "SAL_ESTADO";
	//Origen del Registro
	public final static String SAL_ORIGEN = "SAL_ORIGEN";
	//Destino del Registro
	public final static String SAL_DESTINO = "SAL_DESTINO";
	//Nombres del Destinatario
	public final static String SAL_DESTINATARIO = "SAL_DESTINATARIO";
	//Tipo de Transporte
	public final static String SAL_TIPO_TRANSPORTE = "SAL_TIPO_TRANSPORTE";
	//Numero de Transporte
	public final static String SAL_NUMERO_TRANSPORTE = "SAL_NUMERO_TRANSPORTE";
	//Tipo de Asunto
	public final static String SAL_TIPO_ASUNTO = "SAL_TIPO_ASUNTO";
	//Resumen del documento
	public final static String SAL_RESUMEN = "SAL_RESUMEN";
	//Comentarioa del documento
	public final static String SAL_COMENTARIO = "SAL_COMENTARIO";
	
	public final static String TBL_REGSTATE = "TBL_REGSTATE";

	/**
	 * Constructor.
	 */
	private InveSicresConfiguration() {
		super();
	}

	/**
	 * Obtiene una instancia de la clase.
	 * @return Instancia de la clase.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static synchronized InveSicresConfiguration getInstance()
			throws ISPACException {
		
		if (mInstance == null) {
			mInstance = new InveSicresConfiguration();
			mInstance.initiate(DEFAULT_CONFIG_FILENAME);
		}
		return mInstance;
	}

}
