package ieci.tecdoc.sgm.migration.utils;

public class Constantes {

	public static final String EJECUTION                       				= "EJECUTAR_CRON";
	public static final String TRACE_SYSTEM                       			= "TRACE_SYSTEM"; 
	public static final String ENTORNO_LOCAL                       			= "ENTORNO_LOCAL";
	
	public static final String OPERATOR_NOT_CONSOLIDATED                    = "!=";
	public static final String ENT_POINT_SW_REGISTRO_ORIGEN       			= "endPoint.sw.registro.presencial.origen";
	public static final String ENT_POINT_SW_REGISTRO_DESTINO      			= "endPoint.sw.registro.presencial.destino";
	public static final String ID_FLD_CONSOLIDATED                			= "axsf.fld.id";
	public static final String ENTITY_ORIGEN                      			= "entity.origen.code";
	public static final String ENTITY_DESTINO                     			= "entity.destino.code";
	public static final String ORIGEN_USER_REGISTER               			= "origen.user.acceso.registro.presencial";
	public static final String ORIGEN_PASSWORD_REGISTER           			= "origen.password.acceso.registro.presencial";
	public static final String ORIGEN_IDIOMA_USER                 			= "origen.idioma.locale";
	public static final String DESTINO_USER_REGISTER              			= "destino.user.acceso.registro.presencial";
	public static final String DESTINO_PASSWORD_REGISTER          			= "destino.password.acceso.registro.presencial";
	public static final String DESTINO_IDIOMA_USER                			= "destino.idioma.locale";
	public static final String ORIGEN_ID_BOOK_ENTRADA             			= "origen.id.book.entrada";
	public static final String ORIGEN_ID_BOOK_SALIDA              			= "origen.id.book.salida";
	public static final String DESTINO_ID_BOOK_ENTRADA            			= "destino.id.book.entrada";
	public static final String DESTINO_ID_BOOK_SALIDA             	    	= "destino.id.book.salida";
	public static final String DESTINO_ID_TIPO_TRANSPORTE_ENTRADA 			= "destino.fldId.tipo.transporte.entrada";
	public static final String DESTINO_ID_TIPO_TRANSPORTE_SALIDA			= "destino.fldId.tipo.transporte.salida";
	public static final String DESTINO_ID_BBDD_SRC_TT_ELECTRONICO			= "destino.fldId.id.tipo.transporte.electronico";
	public static final String DESTINO_ID_TIPO_REGISTRO_ENTRADA             = "destino.fldId.tipo.registro.entrada";
	public static final String DESTINO_FIELDID_FECHA_REGISTRO_ORIG_ENTRADA  = "destino.fldId.fecha.registro.original.entrada";
	public static final String DESTINO_FIELDID_FECHA_TRABAJO_ENTRADA		= "destino.fldId.fecha.trabajo.registro.entrada";
	public static final String DESTINO_FIELDID_FECHA_TRABAJO_SALIDA		    = "destino.fldId.fecha.trabajo.registro.salida";
	
	
	public static final String FORMAT_DATE									= "dd-MM-yyyy";
	public static final String FORMAT_DATE_SECONDS							= "dd/MM/yyyy HH:mm:ss";//dd/MM/yyyy hh:mm:ss.SSS
	public static final String FORMAT_DATE_MOUNTH							= "dd 'de' MMMM 'de' yyyy";
	
	// RESPONSE WS
	public static final String RETURN_CODE_ERROR                  			= "ERROR";
	public static final String RETURN_CODE_OK                     			= "OK";
	public static final int COD_ERROR_EMPTY_REGISTERS             			= 2100010040;
	
	
	// FIELDS REGISTER
	public static final String FLD 							      			= "Fld";
	public static final String FIELDS_FORMAT 				      			= "5;7;8;16;12";
	public static final String VALUE_SEPARATOR 				      			= " - ";
	public static final String FLG_CONSOLIDATED 			      			= "1";
	public static final String FLG_NOT_CONSOLIDATED 		      			= "0";
	public static final String FIELD_NOT_INCLUDED_CONSOLIDATION   		    = "6";
	public static final String FLD_VALUE_TIPO_TRANSPORTE                    = "ELECTRONICO";
	public static final String FLD_BORRADO_DOCUMENTOS 		      			= "2001";
	
	
	// PARÁMETROS DE CONFIGURACIÓN DEL CORREO ELECTRÓNICO
	public static final String EMAIL_ORIGEN 								= "direccion.correo.electronico.origen";
	public static final String EMAIL_DESTINO 								= "direccion.correo.electronico.destino";
	public static final String EMAIL_USER_SMTP 								= "mail.smtp.user";
	public static final String EMAIL_PWD_SMTP								= "mail.smtp.password";
	public static final String EMAIL_HOST_SMTP								= "mail.smtp.host";
	public static final String EMAIL_POSRT_SMTP 							= "mail.smtp.port";
	
	
	
	
}

