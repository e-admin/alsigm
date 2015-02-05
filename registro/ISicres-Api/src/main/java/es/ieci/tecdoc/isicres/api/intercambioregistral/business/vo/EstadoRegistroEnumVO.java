package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;


/**
 * Enumerado de estado del registro
 *
 *
 */

public final class EstadoRegistroEnumVO extends ValuedEnum {
	   /**
	 * 
	 */
	private static final long serialVersionUID = -3752212132916841043L;

	   public static final int  COMPLETO_VALUE  = 0;
	   public static final int  INCOMPLETO_VALUE  = 1;
	   public static final int  RESERVADO_VALUE  = 2;
	   public static final int  ANULADO_VALUE  = 4;
	   public static final int  CERRADO_VALUE  = 5;
	   
	   public static final EstadoRegistroEnumVO  COMPLETO  = new EstadoRegistroEnumVO( "COMPLETO", COMPLETO_VALUE );
	   public static final EstadoRegistroEnumVO INCOMPLETO = new EstadoRegistroEnumVO("INCOMPLETO", INCOMPLETO_VALUE);
	   public static final EstadoRegistroEnumVO RESERVADO = new EstadoRegistroEnumVO("RESERVADO", RESERVADO_VALUE);
	   public static final EstadoRegistroEnumVO ANULADO = new EstadoRegistroEnumVO("ANULADO", ANULADO_VALUE);
	   public static final EstadoRegistroEnumVO CERRADO = new EstadoRegistroEnumVO("CERRADO", CERRADO_VALUE);
	   
	   private EstadoRegistroEnumVO(String name, int value) {
	     super( name, value );
	   }
	 
	   public static EstadoRegistroEnumVO getEnum(String value) {
	     return (EstadoRegistroEnumVO) getEnum(EstadoRegistroEnumVO.class, value);
	   }
	 
	   public static EstadoRegistroEnumVO getEnum(int value) {
	     return (EstadoRegistroEnumVO) getEnum(EstadoRegistroEnumVO.class, value);
	   }
	 
	   public static Map getEnumMap() {
	     return getEnumMap(EstadoRegistroEnumVO.class);
	   }
	 
	   public static List getEnumList() {
	     return getEnumList(EstadoRegistroEnumVO.class);
	   }
	 
	   public static Iterator iterator() {
	     return (Iterator) iterator(EstadoRegistroEnumVO.class);
	   }

	 }

