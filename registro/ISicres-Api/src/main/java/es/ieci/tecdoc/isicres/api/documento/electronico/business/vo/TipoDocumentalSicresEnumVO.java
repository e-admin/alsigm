package es.ieci.tecdoc.isicres.api.documento.electronico.business.vo;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * Indica el tipo documental del documento: Los valores se almacenan en la tabla SCR_PAGETYPE
	- '0' = Sin tipo.
	- '1' = Documento a compulsar
	- '2' = Firma documento a compulsar
	- '3' = Documento localizador del fichero a compulsar
 */
public class TipoDocumentalSicresEnumVO extends ValuedEnum{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7734048885145167334L;
	
	protected TipoDocumentalSicresEnumVO(String name, int value) {
		super(name, value);
	}	
	
	 public static final int  SIN_TIPO_VALUE  = 0;
	 public static final int  DOCUMENTO_A_COMPULSAR_VALUE  = 1;
	 public static final int  FIRMA_DOCUMENTO_A_COMPULSAR_VALUE  = 2;
	 public static final int  DOCUMENTO_LOCALIZADOR_DEL_FICHERO_A_COMPULSAR_VALUE  = 3;
	 
	 public static final String  SIN_TIPO_NAME  = "Sin tipo";
	 public static final String  DOCUMENTO_A_COMPULSAR_NAME  = "Documento a compulsar";
	 public static final String  FIRMA_DOCUMENTO_A_COMPULSAR_NAME  = "Firma documento a compulsar";
	 public static final String  DOCUMENTO_LOCALIZADOR_DEL_FICHERO_A_COMPULSAR_NAME  = "Documento localizador de fichero a compulsar";
	 	 
	 public static final TipoDocumentalSicresEnumVO  SIN_TIPO  = new TipoDocumentalSicresEnumVO(SIN_TIPO_NAME,SIN_TIPO_VALUE);
	 public static final TipoDocumentalSicresEnumVO  DOCUMENTO_A_COMPULSAR  = new TipoDocumentalSicresEnumVO(DOCUMENTO_A_COMPULSAR_NAME,DOCUMENTO_A_COMPULSAR_VALUE);
	 public static final TipoDocumentalSicresEnumVO  FIRMA_DOCUMENTO_A_COMPULSAR = new TipoDocumentalSicresEnumVO(FIRMA_DOCUMENTO_A_COMPULSAR_NAME,FIRMA_DOCUMENTO_A_COMPULSAR_VALUE);
	 public static final TipoDocumentalSicresEnumVO  DOCUMENTO_LOCALIZADOR_DEL_FICHERO_A_COMPULSAR = new TipoDocumentalSicresEnumVO(DOCUMENTO_LOCALIZADOR_DEL_FICHERO_A_COMPULSAR_NAME,DOCUMENTO_LOCALIZADOR_DEL_FICHERO_A_COMPULSAR_VALUE);
}
