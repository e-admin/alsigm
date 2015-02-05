/**
 * 
 */
package ieci.tdw.ispac.audit.business.vo.enums;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * 
 * Enumerado con los tipos de objetos de negocio que se auditan
 * 
 * @author IECISA
 * @version $Revision$
 * 
 */
public class IspacObjectAuditTypeEnum extends ValuedEnum {

	public static int USUARIO_VALUE = 1;
	public static String USUARIO_NAME = "USUARIO";

	public static int EXPEDIENTE_VALUE = 2;
	public static String EXPEDIENTE_NAME = "EXPEDIENTE";

	public static int AVISO_VALUE = 3;
	public static String AVISO_NAME = "AVISO";
	
	public static int REGISTRO_DISTRIBUIDO_VALUE = 4;
	public static String REGISTRO_DISTRIBUIDO_NAME = "REGISTRO DISTRIBUIDO";
	
	public static int FICHERO_VALUE = 5;
	public static String FICHERO_NAME = "FICHERO";

	public static IspacObjectAuditTypeEnum USUARIO = new IspacObjectAuditTypeEnum(USUARIO_NAME,
			USUARIO_VALUE);

	public static IspacObjectAuditTypeEnum EXPEDIENTE = new IspacObjectAuditTypeEnum(
			EXPEDIENTE_NAME, EXPEDIENTE_VALUE);

	public static IspacObjectAuditTypeEnum AVISO = new IspacObjectAuditTypeEnum(AVISO_NAME,
			AVISO_VALUE);
	
	public static IspacObjectAuditTypeEnum REGISTRO_DISTRIBUIDO = new IspacObjectAuditTypeEnum(REGISTRO_DISTRIBUIDO_NAME,
			REGISTRO_DISTRIBUIDO_VALUE);
	
	public static IspacObjectAuditTypeEnum FICHERO = new IspacObjectAuditTypeEnum(FICHERO_NAME,
			FICHERO_VALUE);

	/**
	 * @param name
	 * @param value
	 */
	protected IspacObjectAuditTypeEnum(String name, int value) {
		super(name, value);
	}

	public static IspacObjectAuditTypeEnum getEnum(String name) {
		return (IspacObjectAuditTypeEnum) getEnum(IspacObjectAuditTypeEnum.class, name);
	}

	public static IspacObjectAuditTypeEnum getEnum(int value) {
		return (IspacObjectAuditTypeEnum) getEnum(IspacObjectAuditTypeEnum.class, value);
	}

	public static Map getEnumMap() {
		return getEnumMap(IspacObjectAuditTypeEnum.class);
	}

	public static List<IspacObjectAuditTypeEnum> getEnumList() {
		return getEnumList(IspacObjectAuditTypeEnum.class);
	}

	public static Iterator<IspacObjectAuditTypeEnum> iterator() {
		return iterator(IspacObjectAuditTypeEnum.class);
	}

	@Override
	public String toString() {
		return this.getName();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5267244646923075112L;

}
