
package ieci.tecdoc.sgm.tram.secretaria.helper;


public final class SecretariaConstants {


		private SecretariaConstants(){}





		//--------------------------------------------------------------------------------------------------
	    // Entidad diligencia informativa y campos
	    //--------------------------------------------------------------------------------------------------

		public static final String ENTITY_DILIGENCIAS_INFORMATIVAS			= "SEC_DILIGENCIA_DECRETO";
	    public static final String FIELD_DILIGENCIAS_INFORMATIVAS_FECHA		= "FECHA";
	    public static final String FIELD_DILIGENCIAS_NUM_DEC_INIC			= "NUM_DEC_INIC";
	    public static final String FIELD_DILIGENCIAS_NUM_DEC_FIN			= "NUM_DEC_FIN";
	    public static final String FIELD_DILIGENCIAS_ID_TRAMITE				= "ID_TR_INFO_MENSUAL";

	    //--------------------------------------------------------------------------------------------------
	    // Entidad decreto y campos
	    //--------------------------------------------------------------------------------------------------
	    public static final String ENTITY_DECRETO							= "SEC_DECRETO";
	    public static final String FIELD_DECRETO_FECHA_FIRMA				= "FECHA_FIRMA";
	    public static final String FIELD_DECRETO_FECHA_FIRMA_SECRETARIA		= "FECHA_FIRMA_SECRETARIA";
	    public static final String FIELD_DECRETO_NUM_DECRETO				= "NUM_DECRETO";
	    public static final String FIELD_DECRETO_NOMBRE						= "NOMBRE";
	    public static final String FIELD_DECRETO_NUM_HOJAS					= "NUM_HOJAS";
	    public static final String FIELD_DECRETO_ALCALDIA					= "ALCALDIA";
	    public static final String FIELD_DECRETO_SECRETARIA					= "SECRETARIA";


	    //--------------------------------------------------------------------------------------------------
	    // Entidad libro decreto y campos
	    //--------------------------------------------------------------------------------------------------
	    public static final String ENTITY_LIBRO							= "SEC_LIBRO";
	    public static final String FIELD_LIBRO_ANIO						= "ANO";
	    public static final String FIELD_LIBRO_NUM_HOJA_INI				= "NUM_HOJA_INI";
	    public static final String FIELD_LIBRO_NUM_HOJA_FIN				= "NUM_HOJA_FIN";

	    //--------------------------------------------------------------------------------------------------
	    // Entidad conjunto decretos
	    //--------------------------------------------------------------------------------------------------
	    public static final String ENTITY_CONJUNTO_CONTENIDOS			="SEC_CONJUNTO_DECRETOS";
	    public static final String FIELD_CONJUNTO_CONTENIDOS_NOMBRE		= "NOMBRE";
	    public static final String FIELD_CONJUNTO_CONTENIDOS_FECHA		= "FECHA";
	    public static final String FIELD_CONJUNTO_CONTENIDOS_NUM 		= "NUM_DECRETO";

	    //--------------------------------------------------------------------------------------------------
	    // Entidad adicional del participantes en el pcd de Gestion integrantes
	    //--------------------------------------------------------------------------------------------------
	    public static final String ENTITY_INTEGRANTE								="SEC_INFO_INTEGRANTE";
	    public static final String FIELD_INTEGRANTE_ORGANO_GOBIERNO				= "ORGANO_CARGO";
	    public static final String TABLE_INTEGRANTE_MULTIVALUE_FIELDS_TYPE_STRING	="SEC_INFO_INTEGRANTE_S";

	    //--------------------------------------------------------------------------------------------------
	    // Entidad propuesta
	    //--------------------------------------------------------------------------------------------------
	    public static final String ENTITY_PROPUESTA								="SEC_PROPUESTA";
	    public static final String FIELD_PROPUESTA_TITULO						="TITULO";
	    public static final String FIELD_PROPUESTA_TIPO_PROPUESTA				="TIPO_PROPUESTA";
	    public static final String FIELD_PROPUESTA_EXTRACTO						="EXTRACTO";
	    public static final String FIELD_PROPUESTA_CLASIFICACION				="CLF_PROPUESTA";
	    public static final String FIELD_PROPUESTA_ORG_COMPETENTE				="ORG_COMPETENTE";
	    public static final String FIELD_PROPUESTA_ENVIO_SESION					="ENVIO_SESION";




	    //--------------------------------------------------------------------------------------------------
	    // Entidad sesiones plenarias y  propuestas y participantes
	    //--------------------------------------------------------------------------------------------------
	    public static final String ENTITY_SESIONES_PLENARIAS							= "SEC_SESION_PLENARIA";
	    public static final String FIELD_SESIONES_PLENARIAS_NUM_SESION					= "NUM_SESION";
	    public static final String FIELD_SESIONES_PLENARIAS_NUM_HOJAS_ACTA				= "NUM_HOJAS";
	    public static final String FIELD_SESIONES_PLENARIAS_FECHA_CELEBRACION			="FECHA_CELEBRACION";
	    public static final String ENTITY_PROPUESTA_SESION								="SEC_PROPUESTA_SESION";
	    public static final String FIELD_PROPUESTA_SESION_ORDEN							="ORDEN";
	    public static final String FIELD_PROPUESTA_SESION_ID_PROPUESTA					="ID_PROPUESTA";
	    public static final String FIELD_PROPUESTA_SESION_VOTOS_SI						="NUM_VOTOS_SI";
	    public static final String FIELD_PROPUESTA_SESION_VOTOS_NO						="NUM_VOTOS_NO";
	    public static final String FIELD_PROPUESTA_SESION_ABSTENCIONES					="ABSTENCIONES";
	    public static final String FIELD_PROPUESTA_SESION_ACUERDOS						="ACUERDOS";
	    public static final String FIELD_PROPUESTA_SESION_DEBATES						="DEBATE";
	    public static final String FIELD_PROPUESTA_SESION_RESULTADO						="RESULTADO";
	    public static final String FIELD_PROPUESTA_SESION_EXPOSICION					="EXPOSICION";
	    public static final String FIELD_PROPUESTA_SESION_OBSERVACIONES					="OBSERVACIONES";
	    public static final String FIELD_PROPUESTA_SESION_ESTADO						="ESTADO";
	    public static final String TBL_NAME_ESTADO_PROPUESTA_SESION						="SEC_VLDTBL_ESTADO_PROPUESTA";
	    public static final String ENTITY_PARTICIPANTES_SESION							="SEC_PARTIC_SESION_PLENARIA";
	    public static final String FIELD_PARTICIPANTES_SESION_ID_PROPUESTA				="ID_PROPUESTA";
	    public static final String FIELD_PARTICIPANTES_SESION_ID_PARTICIPANTE			="ID_PARTICIPANTE";
	    public static final String FIELD_PARTICIPANTES_SESIONES_ID_PARTICIPANTE_PROPUESTA="ID_PARTICIPANTE_PROPUESTA";
	    public static final String FIELD_PARTICIPANTES_SESION_ASISTE					="ASISTE";
	    public static final String TBL_NAME_RESULTADO_VOTACION_PROPUESTA_SESION			="SEC_VLDTBL_RES_VOTACION";
	    public static final String TBL_NAME_RECURSOS									="SPAC_VLDTBL_RECURSOS";
	    public static final String ENTITY_PARTICIPANTES_AUX								="SEC_PARTICIPANTES";
	    public static final String FIELD_PARTICIPANTES_AUX_ID_PARTICIPANTE				="ID_PARTICIPANTE";
	    public static final String FIELD_PARTICIPANTES_AUX_RECURSOS						="RECURSOS";

	    //--------------------------------------------------------------------------------------------------
	    // Entidad correción error
	    //--------------------------------------------------------------------------------------------------

	    public static final String ENTITY_DILIGENCIA_C_ERROR					= "SEC_DILIGENCIA_C_ERROR";
	    public static final String FIELD_DILIGENCIA_C_ERROR_NUM_HOJAS			= "NUM_HOJAS";

	    //--------------------------------------------------------------------------------------------------
	    // Entidad Notificaciones acuerdos de propuesta en la sesión plenaria
	    //--------------------------------------------------------------------------------------------------
	    public static final String ENTITY_NOT_ACUERDOS_PROPUESTA				="SEC_NOT_ACUERDOS_SP";
	    public static final String FIELD_NOT_ACUERDOS_PROPUESTA_ID_TRAMITE		="ID_TRAMITE";
	    public static final String FIELD_NOT_ACUERDOS_PROPUESTA_ID_PROPUESTA	="ID_PROPUESTA";





	    //--------------------------------------------------------------------------------------------------
	    // Roles destinatario e-mail.
	    //--------------------------------------------------------------------------------------------------
		public static final String ROL_TRASLADO						="TLD";
		public static final String ROL_TRAMITADOR					="TRA";
		public static final String ROL_NOTIFICADO					="NOT";
		public static final String ROL_TRAMITADOR_PROPUESTA			="TRP";
		public static final String ROL_RECEPTOR_COMUNICACION		="REC";
		public static final String ROL_LICITADOR					="LIC";
		public static final String ROL_ADJUDICATARIO				="ADJ";

	    //--------------------------------------------------------------------------------------------------
	    // Roles padron
	    //--------------------------------------------------------------------------------------------------
		public static final String ROL_EMPADRONADO						="EMPR";




	    //--------------------------------------------------------------------------------------------------
	    // Variables del sistema
	    //--------------------------------------------------------------------------------------------------
		public static final String EMAIL_FROM_VAR_NAME 						= "AVISO_DOCFIRMADO_EMAIL_FROM";
		public static final String EMAIL_SUBJECT_VAR_NAME					= "AVISO_DOCFIRMADO_EMAIL_SUBJECT";
		public static final String EMAIL_CONTENT_VAR_NAME 					= "AVISO_DOCFIRMADO_EMAIL_CONTENT";
		public static final String COD_TPDOC_MODELO_DECRETO					= "COD_TPDOC_DEC";
		//public static final String COD_PLANTILLA_NOTIFICACION_DECRETO 		= "COD_PLANTILLA_NOTIFICACION_DECRETO";
		public static final String EMAIL_FROM_VAR_NAME_NOTIFICACIONES 		= "AVISO_NOTIFICACIONES_FIRMADAS_EMAIL_FROM";
		public static final String EMAIL_SUBJECT_VAR_NAME_NOTIFICACIONES	= "AVISO_NOTIFICACIONES_FIRMADAS_SUBJECT";
		public static final String EMAIL_CONTENT_VAR_NAME_NOTIFICACIONES 	= "AVISO_NOTIFICACIONES_FIRMADAS_EMAIL_CONTENT";
		public static final String COD_TPDOC_NOTIFICACION_DECRETO			= "COD_TPDOC_NOT_DEC";
		public static final String COD_TPDOC_DILIGENCIA_APERTURA			= "COD_TPDOC_DILIGENCIA_APERTURA";
		public static final String COD_TPDOC_DILIGENCIA_CIERRE				= "COD_TPDOC_DILIGENCIA_CIERRE";
		public static final String COD_TPDOC_LIBRO_DECRETOS					= "COD_TPDOC_LIBRO_DECRETOS";
		public static final String COD_TPDOC_DICTAMEN_CI					= "COD_TPDOC_DICTAMEN_CI";

		public static final String UID_ALCALDE								= "UID_ALCALDE";
		public static final String TRAMITADORES								= "TRAMITADORES";
		public static final String EMAIL_FROM_VAR_NAME_CNV_SP 				= "AVISO_CNV_SP_EMAIL_FROM";
		public static final String EMAIL_SUBJECT_VAR_NAME_CNV_SP			= "AVISO_CNV_SP_EMAIL_SUBJECT";
		public static final String EMAIL_CONTENT_VAR_NAME_CNV_SP 			= "AVISO_CNV_SP_EMAIL_CONTENT";
		public static final String EMAIL_FROM_VAR_NAME_CNV_JG 				= "AVISO_CNV_JG_EMAIL_FROM";
		public static final String EMAIL_SUBJECT_VAR_NAME_CNV_JG			= "AVISO_CNV_JG_EMAIL_SUBJECT";
		public static final String EMAIL_CONTENT_VAR_NAME_CNV_JG 			= "AVISO_CNV_JG_EMAIL_CONTENT";
		public static final String EMAIL_FROM_VAR_NAME_CAP_SP 				= "AVISO_CAP_SP_EMAIL_FROM";
		public static final String EMAIL_SUBJECT_VAR_NAME_CAP_SP			= "AVISO_CAP_SP_EMAIL_SUBJECT";
		public static final String EMAIL_CONTENT_VAR_NAME_CAP_SP 			= "AVISO_CAP_SP_EMAIL_CONTENT";
		public static final String EMAIL_FROM_VAR_NAME_CAP_JG 				= "AVISO_CAP_JG_EMAIL_FROM";
		public static final String EMAIL_SUBJECT_VAR_NAME_CAP_JG			= "AVISO_CAP_JG_EMAIL_SUBJECT";
		public static final String EMAIL_CONTENT_VAR_NAME_CAP_JG 			= "AVISO_CAP_JG_EMAIL_CONTENT";

		public static final String EMAIL_FROM_VAR_NAME_CNV_CI 				= "AVISO_CNV_CI_EMAIL_FROM";
		public static final String EMAIL_SUBJECT_VAR_NAME_CNV_CI			= "AVISO_CNV_CI_EMAIL_SUBJECT";
		public static final String EMAIL_CONTENT_VAR_NAME_CNV_CI			= "AVISO_CNV_CI_EMAIL_CONTENT";

		public static final String EMAIL_CONTENT_VAR_NAME_CI				= "AVISO_CI_EMAIL_CONTENT";
		public static final String EMAIL_SUBJECT_VAR_NAME_CI				= "AVISO_CI_EMAIL_SUBJECT";



		//Variables de sistema para envio de emails de las reservar de espacio y de plazas en las actividades
		public static final String EMAIL_CONTENT_VAR_NAME_LE_RE				= "AVISO_RE_LE_EMAIL_CONTENT";
		public static final String EMAIL_FROM_VAR_NAME_LE_RE                = "AVISO_RE_LE_EMAIL_FROM";
		public static final String EMAIL_SUBJECT_VAR_NAME_LE_RE				= "AVISO_RE_LE_EMAIL_SUBJECT";

		public static final String EMAIL_CONTENT_VAR_NAME_PAGO_RE			= "AVISO_RE_PAGO_EMAIL_CONTENT";
		public static final String EMAIL_FROM_VAR_NAME_PAGO_RE              = "AVISO_RE_PAGO_EMAIL_FROM";
		public static final String EMAIL_SUBJECT_VAR_NAME_PAGO_RE			= "AVISO_RE_PAGO_EMAIL_SUBJECT";

		public static final String EMAIL_CONTENT_VAR_NAME_RC_RE				= "AVISO_RE_RC_EMAIL_CONTENT";
		public static final String EMAIL_FROM_VAR_NAME_RC_RE             	= "AVISO_RE_RC_EMAIL_FROM";
		public static final String EMAIL_SUBJECT_VAR_NAME_RC_RE				= "AVISO_RE_RC_EMAIL_SUBJECT";

		public static final String COD_TPDOC_NOT_CNV_SP						= "COD_TPDOC_NOT_CNV_SP";
		public static final String COD_TPDOC_NOT_CNV_JG						= "COD_TPDOC_NOT_CNV_JG";
		public static final String COD_TPDOC_NOT_CNV_CI						= "COD_TPDOC_NOT_CNV_CI";


		public static final String COD_TPDOC_CERT_A_SP						= "COD_TPDOC_CERT_A_SP";
		public static final String COD_TPDOC_NOT_A_SP						= "COD_TPDOC_NOT_A_SP";
		public static final String COD_TPDOC_NOT_SE_URG						="COD_TPDOC_NOT_SE_URG";
		public static final String COD_PLANTDOC_NOT_A_SP					= "COD_PLANTDOC_NOT_A_SP";
		public static final String COD_PLANTDOC_NOT_A_JG				    = "COD_PLANTDOC_NOT_A_JG";
		public static final String COD_PLANTDOC_NOT_SE_SP_URG				= "COD_PLANTDOC_NOT_SE_SP_URG";
		public static final String COD_PLANTDOC_NOT_SE_JG_URG				= "COD_PLANTDOC_NOT_SE_JG_URG";
		public static final String COD_TPDOC_ACTA_SP						= "COD_TPDOC_ACTA_SP";
		public static final String COD_TPDOC_C_ERROR_SP						= "COD_TPDOC_C_ERROR_SP";
		public static final String COD_TPDOC_C_ERROR_DEC					= "COD_TPDOC_C_ERROR_DEC";
		public static final String COD_TPDOC_LIBRO_ACTAS_SP					= "COD_TPDOC_LIBRO_ACTAS_SP";
		public static final String COD_TPDOC_LIBRO_ACTAS_JG					= "COD_TPDOC_LIBRO_ACTAS_JG";
		public static final String COD_TPDOC_JUSTIFICANTE_PAGO				= "COD_TPDOC_JUSTIFICANTE_PAGO";
		public static final String COD_TPDOC_DECRETO						= "COD_TPDOC_DEC";
		public static final String COD_TPDOC_PROPUESTA_DECRETO				= "COD_TPDOC_PROP_DEC";
		//public static final String COD_TPDOC_CERTIFICACION_DECRETO			= "COD_TPDOC_CERT_DEC";
		public static final String COD_TPDOC_ACUERDO_JG						= "COD_TPDOC_ACUERDO_JG";






		//Variables de sistema con los codigos de los procedimientos
		public static final String COD_PCD_TRAMITACION_DECRETOS			="COD_PCD_TRAMITACION_DECRETOS";
		public static final String COD_PCD_SESIONES_PLENARIAS			="COD_PCD_SESIONES_PLENARIAS";
		public static final String COD_PCD_JUNTA_GOBIERNO				="COD_PCD_JUNTA_GOBIERNO";
		public static final String COD_PCD_GESTION_INTEGRANTES			="COD_PCD_GESTION_INTEGRANTES";
		public static final String COD_PCD_LIBRO_ACTAS_JG				="COD_PCD_LIBRO_ACTAS_JG";

	/*	public static final String COD_PCD_COMISION_INFORMATIVA			="COD_PCD_COMISION_INFORMATIVA";
		public static final String COD_PCD_GESTION_PROPUESTAS			="COD_PCD_GESTION_PROPUESTAS";*/


		//Variables con los codigos de los trámites y tipos documental

		public static final String COD_TRAMITE_CREACION_DECRETO			="CRE_DEC";
		public static final String COD_TRAMITE_RESOLUCION_POR_DECRETO	="RES";
		public static final String COD_TRAMITE_CERTIFICACION_DECRETO	="CER_DEC";
		public static final String COD_TRAMITE_NOTIFICACION_DECRETO			="NOT_DEC";
		public static final String COD_TRAMITE_CONTENIDO_PROPUESTA			="CON_PRO";
		public static final String COD_TRAMITE_NOTIFICACION_ACUERDOS		="NOT_ACU";
		public static final String COD_TRAMITE_CERTIFICACION_ACUERDO		="CERT_ACU";


		//Integración con decretos
		public static final String VALUE_RELACION_APROBACION_POR_DECRETO	="VALUE_RELACION_APROBACION_POR_DECRETO";

		//Padrón
		public static final String COD_AYTO						="COD_AYTO";
		public static final String URL_WS_PADRON				="URL_WS_PADRON";
		public static final String COD_TPDOC_VOL_EMP			="COD_TPDOC_VOL_EMP";
		public static final String COD_OFIC_TEL					="COD_OFIC_TEL";


	    //Actualización Referencia Expediente
		public static final String REGISTRO_USER						="REGISTRO_USER";

		//--------------------------------------------------------------------------------------------------
		// Estados Administrativos
	    //--------------------------------------------------------------------------------------------------
		  public static final String VALUE_DECRETO_FIRMADO_TRASLADADO 		= "DFT";
		  public static final String VALUE_DECRETO_NOTIFICACO 				= "DNT";
		  public static final String VALUE_ESPERANDO_NOTIFICACIONES 		= "ENS";
		  public static final String VALUE_CONVOCADA 						= "CNV";


		//--------------------------------------------------------------------------------------------------
		// Generación libro
		//--------------------------------------------------------------------------------------------------
		  public static final String MARGIN_BAND_LIBRO				="MARGIN_BAND_LIBRO";


		//--------------------------------------------------------------------------------------------------
		// Órgano Competente
		//--------------------------------------------------------------------------------------------------
		  public static final String VALUE_SESION_JUNTA_GOBIERNO   		= "JUN";
		  public static final String VALUE_SESION_PLENO					= "PLE";
		  public static final String VALUE_SESION_CI					="CIN";

		  //Valores de tablas nuevas exclusivas de secretaria , han de tener el 01 y el 02
		  public static final String VALUE_ORGANO_GOBIERNO_PLENO		= "01";
		  public static final String VALUE_ORGANO_GOBIERNO_JUNTA		= "02";


		//--------------------------------------------------------------------------------------------------
		// Tipo Relación expedientes
		//--------------------------------------------------------------------------------------------------
		  public static final String VALUE_RELACION_SESION_PROPUESTA	= "VALUE_RELACION_ENVIO_SESION";
		  public static final String VALUE_RELACION_JUNTA_PROPUESTA 	= "VALUE_RELACION_ENVIO_JUNTA";


		//--------------------------------------------------------------------------------------------------
		// Tipo de Propuesta
		//--------------------------------------------------------------------------------------------------
		 public static final String VALUE_TIPO_PROPUESTA_ORDINARIA	= "01";
		 public static final String VALUE_TIPO_PROPUESTA_URGENTE	= "02";


		//--------------------------------------------------------------------------------------------------
		// Clasificación de la propuesta
		//--------------------------------------------------------------------------------------------------
		 public static final String ENTITY_TBL_CLF_PROPUESTA		="SEC_VLDTBL_CLF_PROPUESTA";
		 public static final String VALUE_CLF_PROPUESTA_URGENTE		= "00";



		//--------------------------------------------------------------------------------------------------
		// Tipos de contadores
		//--------------------------------------------------------------------------------------------------
		 public static final String  TBL_CONTADOR						="SEC_CONTADORES";
		 public static final String SEQ_TBL_CONTADOR					="SEC_SQ_ID_CONTADORES";
		 public static final int TIPO_CONTADOR_DECRETO					= 1;
		 public static final int TIPO_CONTADOR_SESIONES_PLENARIAS		= 2;
		 public static final int TIPO_CONTADOR_JUNTA_GOBIERNO			= 3;
		 public static final int TIPO_CONTADOR_COMISION_INFORMATIVA		= 4;

		//--------------------------------------------------------------------------------------------------
		// Variables permitidas para los formatos de los contadores
		//--------------------------------------------------------------------------------------------------
		public static final String VARIABLE_DELIMIT = "$";
		public static  final String VARIABLE_YEAR = "YR";
		public static  final String VARIABLE_NUM_CONTADOR = "NM";


}
