package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;

import es.ieci.tecdoc.fwktd.sir.core.types.TipoRegistroEnum;


/**
 * Enumerado de Tipo de registros (entrada / Salida)
 *
 *
 */

public final class TipoRegistroEnumVO extends ValuedEnum {
	   /**
	 * 
	 */
	private static final long serialVersionUID = -3752212132916841043L;

	  
	   public static final int  ENTRADA_VALUE  = 1;
	   public static final int  SALIDA_VALUE  = 2;
	 
	   
	   public static final TipoRegistroEnumVO ENTRADA  = new TipoRegistroEnumVO( "ENTRADA", ENTRADA_VALUE );
	   public static final TipoRegistroEnumVO SALIDA = new TipoRegistroEnumVO("SALIDA", SALIDA_VALUE);


	/**
	 * se realiza la correspondendia de estados entre el sir y los tipos de libro en sicres
	 * @param tipoRegistroSicres
	 * @return
	 */
	public static TipoRegistroEnum getTipoRegistroSIR(Integer tipoRegistroSicres) {

		TipoRegistroEnum result = null;

		if (tipoRegistroSicres.intValue() == ENTRADA_VALUE) {
			result = TipoRegistroEnum.ENTRADA;
		}

		if (tipoRegistroSicres.intValue() == SALIDA_VALUE) {
			result = TipoRegistroEnum.SALIDA;
		}

		return result;
	}

	/**
	 * se realiza la correspondendia de estados entre el sir y los tipos de libro en sicres
	 * @param tipoRegistroSIR
	 * @return
	 */
	public static TipoRegistroEnumVO getTipoRegistroFromSIR(TipoRegistroEnum tipoRegistroSIR) {
		TipoRegistroEnumVO result = null;
		
		if (TipoRegistroEnum.ENTRADA == tipoRegistroSIR) {
			result = TipoRegistroEnumVO.ENTRADA;
		}

		if (TipoRegistroEnum.SALIDA == tipoRegistroSIR) {
			result = TipoRegistroEnumVO.SALIDA;
		}
		
		return result;

	}
	   
	   private TipoRegistroEnumVO(String name, int value) {
	     super( name, value );
	   }
	 
	   public static TipoRegistroEnumVO getEnum(String value) {
	     return (TipoRegistroEnumVO) getEnum(TipoRegistroEnumVO.class, value);
	   }
	 
	   public static TipoRegistroEnumVO getEnum(int value) {
	     return (TipoRegistroEnumVO) getEnum(TipoRegistroEnumVO.class, value);
	   }
	 
	   public static Map getEnumMap() {
	     return getEnumMap(TipoRegistroEnumVO.class);
	   }
	 
	   public static List getEnumList() {
	     return getEnumList(TipoRegistroEnumVO.class);
	   }
	 
	   public static Iterator iterator() {
	     return (Iterator) iterator(TipoRegistroEnumVO.class);
	   }

	 }

