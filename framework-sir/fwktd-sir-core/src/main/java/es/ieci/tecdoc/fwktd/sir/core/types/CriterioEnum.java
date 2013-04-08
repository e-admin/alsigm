package es.ieci.tecdoc.fwktd.sir.core.types;

import es.ieci.tecdoc.fwktd.core.enums.StringValuedEnum;

/**
 * Enumerados para las constantes de los nombres de los criterios
 * de las búsquedas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CriterioEnum extends StringValuedEnum {

	private static final long serialVersionUID = -5596439714016691121L;

	public static final String TABLE_ASIENTOS_REGISTRALES = "AREG";
	public static final String TABLE_INTERESADOS = "INT";
	public static final String TABLE_ANEXOS = "ANE";

	/*
	 * Constantes para la información de los asientos registrales
	 */

	//public static final CriterioEnum ASIENTO_ID = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Identificador del asiento", "id");
	public static final CriterioEnum ASIENTO_CODIGO_ENTIDAD_REGISTRAL = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Código de la entidad registral", "cd_ent_reg");
	public static final CriterioEnum ASIENTO_IDENTIFICADOR_INTERCAMBIO = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Identificador de intercambio", "cd_intercambio");
	//public static final CriterioEnum ASIENTO_TIPO_INTERCAMBIO = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Tipo de intercambio", "cd_tipo_intercambio");
	public static final CriterioEnum ASIENTO_ESTADO = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Estado", "cd_estado");
	public static final CriterioEnum ASIENTO_FECHA_ESTADO = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Fecha estado", "fe_estado");
	public static final CriterioEnum ASIENTO_FECHA_ENVIO = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Fecha envío", "fe_envio");
	public static final CriterioEnum ASIENTO_FECHA_RECEPCION = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Fecha recepción", "fe_recepcion");
	public static final CriterioEnum ASIENTO_NUMERO_REGISTRO = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Número de registro", "num_registro");
	public static final CriterioEnum ASIENTO_FECHA_REGISTRO = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Fecha de registro", "fe_registro");
	public static final CriterioEnum ASIENTO_NUMERO_REGISTRO_INICIAL = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Número de registro inicial", "num_registro_inicial");
	public static final CriterioEnum ASIENTO_FECHA_REGISTRO_INICIAL = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Fecha de registro inicial", "fe_registro_inicial");
	//public static final CriterioEnum ASIENTO_TIMESTAMP_ENTRADA = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Timestamp de entrada", "ts_entrada");
	public static final CriterioEnum ASIENTO_NUMERO_EXPEDIENTE = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Número de expediente", "num_exp");
	public static final CriterioEnum ASIENTO_REFERENCIA_EXTERNA = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Referencia externa", "referencia_externa");
	public static final CriterioEnum ASIENTO_CODIGO_ENTIDAD_REGISTRAL_ORIGEN = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Código de la entidad registral de origen", "cd_ent_reg_origen");
	public static final CriterioEnum ASIENTO_DESCRIPCION_ENTIDAD_REGISTRAL_ORIGEN = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Descripción  de la entidad registral de origen", "ds_ent_reg_origen");
	public static final CriterioEnum ASIENTO_CODIGO_UNIDAD_TRAMITACION_ORIGEN = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Código de la unidad de tramitación de origen", "cd_org_origen");
	public static final CriterioEnum ASIENTO_DESCRIPCION_UNIDAD_TRAMITACION_ORIGEN = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Descripción de la unidad de tramitación de origen", "ds_org_origen");
	public static final CriterioEnum ASIENTO_CODIGO_ENTIDAD_REGISTRAL_DESTINO = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Código de la entidad registral de destino", "cd_ent_reg_destino");
	public static final CriterioEnum ASIENTO_DESCRIPCION_ENTIDAD_REGISTRAL_DESTINO = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Descripción de la entidad registral de destino", "ds_ent_reg_destino");
	public static final CriterioEnum ASIENTO_CODIGO_UNIDAD_TRAMITACION_DESTINO = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Código de la unidad de tramitación de destino", "cd_org_destino");
	public static final CriterioEnum ASIENTO_DESCRIPCION_UNIDAD_TRAMITACION_DESTINO = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Descripción de la unidad de tramitación de destino", "ds_org_destino");
	public static final CriterioEnum ASIENTO_CODIGO_ENTIDAD_REGISTRAL_INICIO = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Código de la entidad registral de inicio", "cd_ent_reg_inicio");
	public static final CriterioEnum ASIENTO_DESCRIPCION_ENTIDAD_REGISTRAL_INICIO = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Descripción de la entidad registral de inicio", "ds_ent_reg_inicio");
	public static final CriterioEnum ASIENTO_RESUMEN = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Resumen", "ds_resumen");
	public static final CriterioEnum ASIENTO_CODIGO_ASUNTO = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Asunto", "cd_asunto_destino");
	public static final CriterioEnum ASIENTO_TIPO_TRANSPORTE = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Tipo de transporte", "cd_tipo_transporte");
	public static final CriterioEnum ASIENTO_NUMERO_TRANSPORTE = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Número de transporte", "ds_num_transporte");
	public static final CriterioEnum ASIENTO_NOMBRE_USUARIO = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Nombre del usuario", "ds_nombre_usuario");
	public static final CriterioEnum ASIENTO_CONTACTO_USUARIO = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Contacto del usuario", "ds_contacto_usuario");
	public static final CriterioEnum ASIENTO_APLICACION = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Aplicación emisora", "cd_app_emisora");
	public static final CriterioEnum ASIENTO_TIPO_ANOTACION = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Código del tipo de anotación", "cd_tipo_anotacion");
	public static final CriterioEnum ASIENTO_DESCRIPCION_TIPO_ANOTACION = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Descripción del tipo de anotación", "ds_tipo_anotacion");
	public static final CriterioEnum ASIENTO_TIPO_REGISTRO = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Tipo de registro", "cd_tipo_registro");
	public static final CriterioEnum ASIENTO_DOCUMENTACION_FISICA = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Documentación física", "cd_doc_fisica");
	public static final CriterioEnum ASIENTO_OBSERVACIONES_APUNTE = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Observaciones del asiento", "ds_observaciones");
	public static final CriterioEnum ASIENTO_INDICADOR_PRUEBA = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Indicador de prueba", "cd_prueba");
	public static final CriterioEnum ASIENTO_EXPONE = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Exposición de los hechos", "ds_expone");
	public static final CriterioEnum ASIENTO_SOLICITA = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Solicitud", "ds_solicita");
	public static final CriterioEnum ASIENTO_CODIGO_ERROR = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Código del error", "cd_error");
	public static final CriterioEnum ASIENTO_DESCRIPCION_ERROR = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Descripción del error", "ds_error");
	public static final CriterioEnum ASIENTO_NUMERO_REINTENTOS = new CriterioEnum(TABLE_ASIENTOS_REGISTRALES, "Número de reintentos de envío", "num_reintentos");


	/*
	 * Constantes para la información de los interesados
	 */

	//public static final CriterioEnum INTERESADO_ID = new CriterioEnum(TABLE_INTERESADOS, "", "id");
	//public static final CriterioEnum INTERESADO_ID_ASIENTO_REGISTRAL = new CriterioEnum(TABLE_INTERESADOS, "", "id_asiento_registral");
	public static final CriterioEnum INTERESADO_TIPO_DOCUMENTO_IDENTIFICACION_INTERESADO = new CriterioEnum(TABLE_INTERESADOS, "Tipo de documento de identificación del interesado", "cd_tipo_identificador");
	public static final CriterioEnum INTERESADO_DOCUMENTO_IDENTIFICACION_INTERESADO = new CriterioEnum(TABLE_INTERESADOS, "Documento de identificación del interesado", "ds_identificador");
	public static final CriterioEnum INTERESADO_RAZON_SOCIAL_INTERESADO = new CriterioEnum(TABLE_INTERESADOS, "Razón social del interesado", "ds_razon_social");
	public static final CriterioEnum INTERESADO_NOMBRE_INTERESADO = new CriterioEnum(TABLE_INTERESADOS, "Nombre del interesado", "ds_nombre");
	public static final CriterioEnum INTERESADO_PRIMER_APELLIDO_INTERESADO = new CriterioEnum(TABLE_INTERESADOS, "Primer apellido del interesado", "ds_apellido1");
	public static final CriterioEnum INTERESADO_SEGUNDO_APELLIDO_INTERESADO = new CriterioEnum(TABLE_INTERESADOS, "Segundo apellido del interesado", "ds_apellido2");
	public static final CriterioEnum INTERESADO_CODIGO_PAIS_INTERESADO = new CriterioEnum(TABLE_INTERESADOS, "País del interesado", "cd_pais");
	public static final CriterioEnum INTERESADO_CODIGO_PROVINCIA_INTERESADO = new CriterioEnum(TABLE_INTERESADOS, "Provincia del interesado", "cd_provincia");
	public static final CriterioEnum INTERESADO_CODIGO_MUNICIPIO_INTERESADO = new CriterioEnum(TABLE_INTERESADOS, "Municipio del interesado", "cd_municipio");
	public static final CriterioEnum INTERESADO_DIRECCION_INTERESADO = new CriterioEnum(TABLE_INTERESADOS, "Dirección postal del interesado", "ds_direccion");
	public static final CriterioEnum INTERESADO_CODIGO_POSTAL_INTERESADO = new CriterioEnum(TABLE_INTERESADOS, "Código postal del interesado", "cd_postal");
	public static final CriterioEnum INTERESADO_CORREO_ELECTRONICO_INTERESADO = new CriterioEnum(TABLE_INTERESADOS, "Correo electrónico del interesado", "ds_email");
	public static final CriterioEnum INTERESADO_TELEFONO_INTERESADO = new CriterioEnum(TABLE_INTERESADOS, "Teléfono del interesado", "ds_telefono");
	public static final CriterioEnum INTERESADO_DIRECCION_ELECTRONICA_INTERESADO = new CriterioEnum(TABLE_INTERESADOS, "Dirección electrónica del interesado", "ds_deu");
	public static final CriterioEnum INTERESADO_CANAL_PREFERENTE_INTERESADO = new CriterioEnum(TABLE_INTERESADOS, "Canal preferente de notificación del interesado", "cd_canal_pref");
	public static final CriterioEnum INTERESADO_TIPO_DOCUMENTO_IDENTIFICACION_REPRESENTANTE = new CriterioEnum(TABLE_INTERESADOS, "Tipo de documento de identificación del representante", "cd_tipo_identificador_rep");
	public static final CriterioEnum INTERESADO_DOCUMENTO_IDENTIFICACION_REPRESENTANTE = new CriterioEnum(TABLE_INTERESADOS, "Documento de identificación del representante", "ds_identificador_rep");
	public static final CriterioEnum INTERESADO_RAZON_SOCIAL_REPRESENTANTE = new CriterioEnum(TABLE_INTERESADOS, "Razón social del representante", "ds_razon_social_rep");
	public static final CriterioEnum INTERESADO_NOMBRE_REPRESENTANTE = new CriterioEnum(TABLE_INTERESADOS, "Nombre del representante", "ds_nombre_rep");
	public static final CriterioEnum INTERESADO_PRIMER_APELLIDO_REPRESENTANTE = new CriterioEnum(TABLE_INTERESADOS, "Primer apellido del representante", "ds_apellido1_rep");
	public static final CriterioEnum INTERESADO_SEGUNDO_APELLIDO_REPRESENTANTE = new CriterioEnum(TABLE_INTERESADOS, "Segundo apellido del representante", "ds_apellido2_rep");
	public static final CriterioEnum INTERESADO_CODIGO_PAIS_REPRESENTANTE = new CriterioEnum(TABLE_INTERESADOS, "País del representante", "cd_pais_rep");
	public static final CriterioEnum INTERESADO_CODIGO_PROVINCIA_REPRESENTANTE = new CriterioEnum(TABLE_INTERESADOS, "Provincia del representante", "cd_provincia_rep");
	public static final CriterioEnum INTERESADO_CODIGO_MUNICIPIO_REPRESENTANTE = new CriterioEnum(TABLE_INTERESADOS, "Municipio del representante", "cd_municipio_rep");
	public static final CriterioEnum INTERESADO_DIRECCION_REPRESENTANTE = new CriterioEnum(TABLE_INTERESADOS, "Dirección postal del representante", "ds_direccion_rep");
	public static final CriterioEnum INTERESADO_CODIGO_POSTAL_REPRESENTANTE = new CriterioEnum(TABLE_INTERESADOS, "Código postal del representante", "cd_postal_rep");
	public static final CriterioEnum INTERESADO_CORREO_ELECTRONICO_REPRESENTANTE = new CriterioEnum(TABLE_INTERESADOS, "Correo electrónico del representante", "ds_email_rep");
	public static final CriterioEnum INTERESADO_TELEFONO_REPRESENTANTE = new CriterioEnum(TABLE_INTERESADOS, "Teléfono del representante", "ds_telefono_rep");
	public static final CriterioEnum INTERESADO_DIRECCION_ELECTRONICA_REPRESENTANTE = new CriterioEnum(TABLE_INTERESADOS, "Dirección electrónica del representante", "ds_deu_rep");
	public static final CriterioEnum INTERESADO_CANAL_PREFERENTE_REPRESENTANTE = new CriterioEnum(TABLE_INTERESADOS, "Canal preferente de notificación del representante", "cd_canal_pref_rep");
	public static final CriterioEnum INTERESADO_OBSERVACIONES = new CriterioEnum(TABLE_INTERESADOS, "Observaciones del interesado", "ds_observaciones_rep");


	/*
	 * Constantes para la información de los anexos
	 */

	//public static final CriterioEnum ANEXO_ID = new CriterioEnum(TABLE_ANEXOS, "Identificador del anexo", "id");
	//public static final CriterioEnum ANEXO_ID_ASIENTO_REGISTRAL = new CriterioEnum(TABLE_ANEXOS, "Identificador del asiento registral", "id_asiento_registral");
	public static final CriterioEnum ANEXO_IDENTIFICADOR_FICHERO = new CriterioEnum(TABLE_ANEXOS, "Identificador del fichero", "cd_fichero");
	public static final CriterioEnum ANEXO_NOMBRE_FICHERO = new CriterioEnum(TABLE_ANEXOS, "Nombre del fichero", "ds_nombre_fichero");
	public static final CriterioEnum ANEXO_VALIDEZ_DOCUMENTO = new CriterioEnum(TABLE_ANEXOS, "Validez del documento", "cd_validez");
	public static final CriterioEnum ANEXO_TIPO_DOCUMENTO = new CriterioEnum(TABLE_ANEXOS, "Tipo de documento", "cd_tipo_documento");
	//public static final CriterioEnum ANEXO_CERTIFICADO = new CriterioEnum(TABLE_ANEXOS, "Certificado", "certificado");
	//public static final CriterioEnum ANEXO_VALIDACION_OCSP_CERTIFICADO = new CriterioEnum(TABLE_ANEXOS, "Validación OCSP del certificado", "validacion_ocsp");
	//public static final CriterioEnum ANEXO_FIRMA = new CriterioEnum(TABLE_ANEXOS, "Firma", "firma");
	//public static final CriterioEnum ANEXO_TIMESTAMP = new CriterioEnum(TABLE_ANEXOS, "Timestamp del fichero", "ts_fichero");
	//public static final CriterioEnum ANEXO_HASH = new CriterioEnum(TABLE_ANEXOS, "Código hash del fichero", "hash");
	public static final CriterioEnum ANEXO_TIPO_MIME = new CriterioEnum(TABLE_ANEXOS, "Tipo MIME", "tipo_mime");
	//public static final CriterioEnum ANEXO_ANEXO = new CriterioEnum(TABLE_ANEXOS, "Contenido del anexo", "anexo");
	//public static final CriterioEnum ANEXO_IDENTIFICADOR_FICHERO_FIRMADO = new CriterioEnum(TABLE_ANEXOS, "Identificador del fichero firmado", "cd_fichero_firmado");
	public static final CriterioEnum ANEXO_OBSERVACIONES = new CriterioEnum(TABLE_ANEXOS, "Observaciones del anexo", "ds_observaciones");


	/**
	 * Nombre de la tabla.
	 */
	private String table = null;

	/**
	 * Constructor.
	 *
	 * @param table
	 *            Nombre de la tabla.
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected CriterioEnum(String table, String name, String value) {
		super(name, value);
		setTable(table);
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	/**
	 * Obtiene la constante asociada al valor.
	 * @param value Valor de la constante
	 * @return Constante.
	 */
	public static CriterioEnum getCriterio(String value) {
		return (CriterioEnum) StringValuedEnum.getEnum(CriterioEnum.class, value);
	}
}
