package es.ieci.tecdoc.isicres.api.audit.business.vo.enums;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * Enumerados de los tipos de manejadores de eventos de auditoria.
 * 
 * @author IECISA
 *
 */
public class IsicresEventAuditHandlerTypeEnum extends ValuedEnum {
	
	public static String TRAZA_BUILDER_NAME = "Tipo de handler creador de Tradas";
	public static int TRAZA_BUILDER_VALUE = 1;
	
	
	public static IsicresEventAuditHandlerTypeEnum TRAZA_BUILDER = new IsicresEventAuditHandlerTypeEnum(TRAZA_BUILDER_NAME,TRAZA_BUILDER_VALUE);


	protected IsicresEventAuditHandlerTypeEnum(String name, int value) {
		super(name, value);
	}
	
	public static IsicresEventAuditHandlerTypeEnum getEnum(String name) {
		return (IsicresEventAuditHandlerTypeEnum) getEnum(IsicresEventAuditHandlerTypeEnum.class, name);
	}

	public static IsicresEventAuditHandlerTypeEnum getEnum(int value) {
		return (IsicresEventAuditHandlerTypeEnum) getEnum(IsicresEventAuditHandlerTypeEnum.class, value);
	}

	public static Map getEnumMap() {
		return getEnumMap(IsicresEventAuditHandlerTypeEnum.class);
	}

	public static List<IsicresEventAuditHandlerTypeEnum> getEnumList() {
		return getEnumList(IsicresEventAuditHandlerTypeEnum.class);
	}

	public static Iterator<IsicresEventAuditHandlerTypeEnum> iterator() {
		return iterator(IsicresEventAuditHandlerTypeEnum.class);
	}
	
	@Override
	public String toString() {
		return this.getName();
	}

	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3344631267285195402L;

}
