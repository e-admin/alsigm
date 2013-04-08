package docvitales;

import java.util.Map;

import common.SigiaUtilConstants;



public class DocumentosVitalesConstants 
{
   
	private static final StringBuffer buff = new StringBuffer();
	private static final String getKey(String innerKey)
	{
		buff.setLength(0);
		return buff.append("DocumentosVitalesConstants_").append(innerKey).toString();
	}

	public static final String DOCUMENTOS_VITALES_KEY = getKey("DOCUMENTOS_VITALES_KEY");
	public static final String DOCUMENTO_VITAL_KEY = getKey("DOCUMENTO_VITAL_KEY");
	public static final String DOCUMENTO_VITAL_VIGENTE_KEY = getKey("DOCUMENTO_VITAL_VIGENTE_KEY");
	public static final String ESTADOS_DOCUMENTOS_VITALES_KEY = getKey("ESTADOS_DOCUMENTOS_VITALES_KEY");
	public static final String TIPOS_DOCUMENTOS_VITALES_KEY = getKey("TIPOS_DOCUMENTOS_VITALES_KEY");
	public static final String TIPO_DOCUMENTO_VITAL_KEY = getKey("TIPO_DOCUMENTO_VITAL_KEY");
	public static final String RESULTADOS_BUSQUEDA_TERCEROS = getKey("RESULTADOS_BUSQUEDA_TERCEROS");
	public static final String TERCERO_DE_DOCUMENTO_VITAL = getKey("TERCERO_DE_DOCUMENTO_VITAL");
	
	public static Map estadoDocumento = SigiaUtilConstants.getConstantsMap(EstadoDocumentoVital.class);
	
	public static final String ERRORS_DOCVITALES_SEL_TERCERO = "errors.documentosVitales.seleccioneUnTercero";
	public static final String ERRORS_DOCVITALES_DOS_TIPOS_IGUALES="archigest.archivo.docvitales.docVital.error.dosTiposIguales.msg";
	public static final String ERRORS_DOCVITALES_DOC_NO_ENCONTRADO="errors.documentosVitales.documento_no_encontrado";
	public static final String ERRORS_GESTIONTIPOSDOCUMENTOS_NOMBRE_VACIO = "errors.gestiontiposdocumentos.nombre.vacio";
	public static final String ERRORS_GESTIONTIPOSDOCUMENTOS_DESCRIPCION_VACIO = "errors.gestiontiposdocumentos.descripcion.vacio";
	public static final String ERRORS_DOCVITALES_DELETE_TIPODOCVITAL_EN_USO="archigest.archivo.docvitales.tipoDocumentoVital.delete.error.enuso";

	
	public static final String LABEL_DOCVITALES_TIPO_DOCUMENTO="archigest.archivo.docvitales.form.tipoDocumento";
	public static final String LABEL_DOCVITALES_NUEVO_DOCUMENTO="archigest.archivo.docvitales.form.nuevoDocumento";
	public static final String LABEL_DOCVITALES_DOCVITAL_TIPO_DOCUMENTO="archigest.archivo.docvitales.docVital.tipoDocumento";
	public static final String LABEL_DOCVITALES_DOCVITAL_ESTADO="archigest.archivo.docvitales.docVital.estado";
	public static final String LABEL_DOCVITALES_DOCVITAL_FECHA_CADUCIDAD="archigest.archivo.docvitales.docVital.fechaCaducidad";
	public static final String LABEL_DOCVITALES_DOCVITAL_FECHA_ULTIMO_ACCESO="archigest.archivo.docvitales.docVital.fechaUltimoAcceso";
	public static final String LABEL_DOCVITALES_DOCVITAL_TIPODOCVITAL_NOMBRE="archigest.archivo.docvitales.tipoDocumentoVital.nombre";
	public static final String LABEL_DOCVITALES_ESTADO="archigest.archivo.docvitales.estado";
}
