/**
 * 
 */
package ieci.tdw.ispac.audit.business.vo.enums;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * Enumerados de los tipos de manejadores de eventos de auditoria.
 * 
 * @author IECISA
 * @version $Revision$
 * 
 */
public class IspacEventAuditHandlerTypeEnum extends ValuedEnum {

	public static String TRAZA_BUILDER_NAME = "Tipo de handler creador de Trazas";
	public static int TRAZA_BUILDER_VALUE = 1;

	public static IspacEventAuditHandlerTypeEnum TRAZA_BUILDER = new IspacEventAuditHandlerTypeEnum(
			TRAZA_BUILDER_NAME, TRAZA_BUILDER_VALUE);

	/**
	 * @param name
	 * @param value
	 */
	protected IspacEventAuditHandlerTypeEnum(String name, int value) {
		super(name, value);
	}

	public static IspacEventAuditHandlerTypeEnum getEnum(String name) {
		return (IspacEventAuditHandlerTypeEnum) getEnum(IspacEventAuditHandlerTypeEnum.class, name);
	}

	public static IspacEventAuditHandlerTypeEnum getEnum(int value) {
		return (IspacEventAuditHandlerTypeEnum) getEnum(IspacEventAuditHandlerTypeEnum.class, value);
	}

	public static Map getEnumMap() {
		return getEnumMap(IspacEventAuditHandlerTypeEnum.class);
	}

	public static List<IspacEventAuditHandlerTypeEnum> getEnumList() {
		return getEnumList(IspacEventAuditHandlerTypeEnum.class);
	}

	public static Iterator<IspacEventAuditHandlerTypeEnum> iterator() {
		return iterator(IspacEventAuditHandlerTypeEnum.class);
	}

	@Override
	public String toString() {
		return this.getName();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4477354018045420496L;

}
