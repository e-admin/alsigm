package es.ieci.tecdoc.isicres.api.audit.business.vo.enums;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * Enumerado con los tipos de eventos a auditar por la aplicación de registro
 *
 * @author IECISA
 *
 */
public class IsicresEventAuditTypeEnum extends ValuedEnum {

	private static final long serialVersionUID = -6594408742409786836L;

	public static int ACCESO_APLICACION_VALUE = 1;
	public static String ACCESO_APLICACION_NAME = "EVENTO DE ACCESO A LA APLICACIÓN";

	public static int BUSQUEDA_REGISTRO_VALUE = 2;
	public static String BUSQUEDA_REGISTRO_NAME = "EVENTO DE BÚSQUEDA DE REGISTROS";

	public static int ACCESO_REGISTRO_VALUE = 3;
	public static String ACCESO_REGISTRO_NAME = "EVENTO DE ACCESO A UN REGISTRO";

	public static int CREACION_REGISTRO_VALUE = 4;
	public static String CREACION_REGISTRO_NAME = "EVENTO DE CREACIÓN A UN REGISTRO";

	public static int CREACION_CAMPO_REGISTRO_VALUE = 5;
	public static String CREACION_CAMPO_REGISTRO_NAME = "EVENTO DE CREACIÓN CAMPO EN UN REGISTRO";

	public static int MODIFICACION_REGISTRO_VALUE = 6;
	public static String MODIFICACION_REGISTRO_NAME = "EVENTO DE MODIFICACIÓN  EN UN REGISTRO";

	public static int MODIFICACION_CAMPO_REGISTRO_VALUE = 7;
	public static String MODIFICACION_CAMPO_REGISTRO_NAME = "EVENTO DE MODIFICACIÓN CAMPO EN UN REGISTRO";

	public static int ACCESO_DOCUMENTO_REGISTRO_VALUE = 8;
	public static String ACCESO_DOCUMENTO_REGISTRO_NAME = "EVENTO DE ACCESO DOCUMENTO DE UN REGISTRO";

	public static int ACCESO_INFORMES_VALUE = 9;
	public static String ACCESO_INFORMES_REGISTRO_NAME = "EVENTO DE ACCESO A INFORMES";

	public static IsicresEventAuditTypeEnum ACCESO_APLICACION = new IsicresEventAuditTypeEnum(ACCESO_APLICACION_NAME, ACCESO_APLICACION_VALUE);
	public static IsicresEventAuditTypeEnum BUSQUEDA_REGISTRO = new IsicresEventAuditTypeEnum(BUSQUEDA_REGISTRO_NAME,BUSQUEDA_REGISTRO_VALUE);
	public static IsicresEventAuditTypeEnum ACCESO_REGISTRO = new IsicresEventAuditTypeEnum(ACCESO_REGISTRO_NAME,ACCESO_REGISTRO_VALUE);
	public static IsicresEventAuditTypeEnum CREACION_REGISTRO = new IsicresEventAuditTypeEnum(CREACION_REGISTRO_NAME,CREACION_REGISTRO_VALUE);
	public static IsicresEventAuditTypeEnum CREACION_CAMPO_REGISTRO = new IsicresEventAuditTypeEnum(CREACION_CAMPO_REGISTRO_NAME,CREACION_CAMPO_REGISTRO_VALUE);
	public static IsicresEventAuditTypeEnum MODIFICACION_REGISTRO = new IsicresEventAuditTypeEnum(MODIFICACION_REGISTRO_NAME,MODIFICACION_REGISTRO_VALUE);
	public static IsicresEventAuditTypeEnum MODIFICACION_CAMPO_REGISTRO = new IsicresEventAuditTypeEnum(MODIFICACION_CAMPO_REGISTRO_NAME,MODIFICACION_CAMPO_REGISTRO_VALUE);
	public static IsicresEventAuditTypeEnum ACCESO_DOCUMENTO_REGISTRO = new IsicresEventAuditTypeEnum(ACCESO_DOCUMENTO_REGISTRO_NAME,ACCESO_DOCUMENTO_REGISTRO_VALUE);
	public static IsicresEventAuditTypeEnum ACCESO_INFORMES = new IsicresEventAuditTypeEnum(ACCESO_INFORMES_REGISTRO_NAME, ACCESO_INFORMES_VALUE);

	protected IsicresEventAuditTypeEnum(String name, int value) {
		super(name, value);
	}

	public static IsicresEventAuditTypeEnum getEnum(String name) {
		return (IsicresEventAuditTypeEnum) getEnum(IsicresEventAuditTypeEnum.class, name);
	}

	public static IsicresEventAuditTypeEnum getEnum(int value) {
		return (IsicresEventAuditTypeEnum) getEnum(IsicresEventAuditTypeEnum.class, value);
	}

	public static Map getEnumMap() {
		return getEnumMap(IsicresEventAuditTypeEnum.class);
	}

	public static List<IsicresEventAuditTypeEnum> getEnumList() {
		return getEnumList(IsicresEventAuditTypeEnum.class);
	}

	public static Iterator<IsicresEventAuditTypeEnum> iterator() {
		return iterator(IsicresEventAuditTypeEnum.class);
	}

	@Override
	public String toString() {
		return this.getName();
	}



}
