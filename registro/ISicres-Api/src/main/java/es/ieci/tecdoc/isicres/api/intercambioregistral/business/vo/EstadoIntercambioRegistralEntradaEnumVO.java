package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;


/**
 * Enumerado de estado de intercambio de registro de entrada dentro de isicres.
 *
 *
 */

public final class EstadoIntercambioRegistralEntradaEnumVO extends ValuedEnum {
	   /**
	 *
	 */
	private static final long serialVersionUID = -3752212132916841043L;


	   public static final int  PENDIENTE_VALUE  = 0;
	   public static final int  ACEPTADO_VALUE  = 1;
	   public static final int  RECHAZADO_VALUE  = 2;
	   public static final int  REENVIADO_VALUE  = 3;

	   public static final EstadoIntercambioRegistralEntradaEnumVO  ACEPTADO  = new EstadoIntercambioRegistralEntradaEnumVO( "ACEPTADO", ACEPTADO_VALUE );
	   public static final EstadoIntercambioRegistralEntradaEnumVO PENDIENTE = new EstadoIntercambioRegistralEntradaEnumVO("PENDIENTE", PENDIENTE_VALUE);
	   public static final EstadoIntercambioRegistralEntradaEnumVO RECHAZADO = new EstadoIntercambioRegistralEntradaEnumVO("RECHAZADO", RECHAZADO_VALUE);
	   public static final EstadoIntercambioRegistralEntradaEnumVO REENVIADO = new EstadoIntercambioRegistralEntradaEnumVO("REENVIADO", REENVIADO_VALUE);

	   private EstadoIntercambioRegistralEntradaEnumVO(String name, int value) {
	     super( name, value );
	   }

	   public static EstadoIntercambioRegistralEntradaEnumVO getEnum(String value) {
	     return (EstadoIntercambioRegistralEntradaEnumVO) getEnum(EstadoIntercambioRegistralEntradaEnumVO.class, value);
	   }

	   public static EstadoIntercambioRegistralEntradaEnumVO getEnum(int value) {
	     return (EstadoIntercambioRegistralEntradaEnumVO) getEnum(EstadoIntercambioRegistralEntradaEnumVO.class, value);
	   }

	   public static Map getEnumMap() {
	     return getEnumMap(EstadoIntercambioRegistralEntradaEnumVO.class);
	   }

	   public static List getEnumList() {
	     return getEnumList(EstadoIntercambioRegistralEntradaEnumVO.class);
	   }

	   public static Iterator iterator() {
	     return (Iterator) iterator(EstadoIntercambioRegistralEntradaEnumVO.class);
	   }

	 }

