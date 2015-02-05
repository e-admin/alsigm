package es.ieci.tecdoc.isicres.api.audit.business.vo.enums;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * Enumerado con lso tipos de objetos de negocio que se auditan
 * @author IECISA
 *
 */
public class IsicresObjectAuditTypeEnum extends ValuedEnum {
	
	public static int REGISTRO_VALUE = 1;
	public static String REGISTRO_NAME = "TIPO DE OBJETO A AUDITAR REGISTRO";
	
	public static int REGISTRO_FIELD_VALUE = 2;
	public static String REGISTRO_FIELD_NAME = "TIPO DE OBJETO A AUDITAR CAMPO REGISTRO";
	
	public static int USUARIO_VALUE = 3;
	public static String USUARIO_NAME = "TIPO DE OBJETO A AUDITAR USUARIO";
	
	
	public static IsicresObjectAuditTypeEnum REGISTRO = new IsicresObjectAuditTypeEnum(REGISTRO_NAME, REGISTRO_VALUE);
	public static IsicresObjectAuditTypeEnum REGISTRO_FIELD = new IsicresObjectAuditTypeEnum(REGISTRO_FIELD_NAME, REGISTRO_FIELD_VALUE);
	
	public static IsicresObjectAuditTypeEnum USUARIO = new IsicresObjectAuditTypeEnum(USUARIO_NAME, USUARIO_VALUE);
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7820659414388341117L;

	protected IsicresObjectAuditTypeEnum(String name, int value) {
		super(name, value);
	}

	public static IsicresObjectAuditTypeEnum getEnum(String name) {
		return (IsicresObjectAuditTypeEnum) getEnum(
				IsicresObjectAuditTypeEnum.class, name);
	}

	public static IsicresObjectAuditTypeEnum getEnum(int value) {
		return (IsicresObjectAuditTypeEnum) getEnum(
				IsicresObjectAuditTypeEnum.class, value);
	}

	public static Map getEnumMap() {
		return getEnumMap(IsicresObjectAuditTypeEnum.class);
	}

	public static List<IsicresObjectAuditTypeEnum> getEnumList() {
		return getEnumList(IsicresObjectAuditTypeEnum.class);
	}

	public static Iterator<IsicresObjectAuditTypeEnum> iterator() {
		return iterator(IsicresObjectAuditTypeEnum.class);
	}

	@Override
	public String toString() {
		return this.getName();
	}

}
