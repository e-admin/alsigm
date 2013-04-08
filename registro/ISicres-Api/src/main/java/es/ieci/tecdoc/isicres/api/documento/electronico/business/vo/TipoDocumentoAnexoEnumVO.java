package es.ieci.tecdoc.isicres.api.documento.electronico.business.vo;
import org.apache.commons.lang.enums.ValuedEnum;

/**
* Campo OBLIGATORIO
	 * Tipo del documento:
	 * 
	 * Indica el tipo de documento:
		- ‘01’ = Formulario (el documento adjunto es un formulario con campos rellenos por el ciudadano remitente).
		- ‘02’ = Documento adjunto al formulario (además del formulario, otro documento es adjuntado, acompañando al formulario).
		- ‘03’ = Fichero técnico interno (el documento adjunto es un fichero interno. Por lo general, estos ficheros pueden resultar útiles para la Entidad Registral de Destino, pero no son ficheros para
	 * 	

 * @author IECISA
 *
 */
public class TipoDocumentoAnexoEnumVO extends ValuedEnum {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5651813651338584528L;
	
	protected TipoDocumentoAnexoEnumVO(String name, int value) {
		super(name, value);
	}	
	
	 public static final int  FORMULARIO_VALUE  = 1;
	 public static final int  DOCUMENTO_ADJUNTO_FORMULARIO_VALUE  = 2;
	 public static final int  FICHERO_TECNICO_VALUE  = 3;
	 
	 public static final String  FORMULARIO_NAME= "01";
	 public static final String DOCUMENTO_ADJUNTO_FORMULARIO_NAME= "02";
	 public static final String FICHERO_TECNICO_NAME = "03";
	 
	 public static final TipoDocumentoAnexoEnumVO  FORMULARIO  = new TipoDocumentoAnexoEnumVO(FORMULARIO_NAME,FORMULARIO_VALUE);
	 public static final TipoDocumentoAnexoEnumVO  DOCUMENTO_ADJUNTO_FORMULARIO  = new TipoDocumentoAnexoEnumVO(DOCUMENTO_ADJUNTO_FORMULARIO_NAME,DOCUMENTO_ADJUNTO_FORMULARIO_VALUE);
	 public static final TipoDocumentoAnexoEnumVO  FICHERO_TECNICO = new TipoDocumentoAnexoEnumVO(FICHERO_TECNICO_NAME,FICHERO_TECNICO_VALUE);
	 
	 

}
