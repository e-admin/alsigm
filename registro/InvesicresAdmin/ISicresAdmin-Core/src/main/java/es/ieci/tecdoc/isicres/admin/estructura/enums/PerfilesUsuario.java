package es.ieci.tecdoc.isicres.admin.estructura.enums;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * Enumerado para representar los diferentes perfiles de usuario (IUSERUSERTYPE). Se trata de
 * una especialización de <code>org.apache.commons.lang.enums.Enum</code>.
 *
 * Los diferentes valores que se pueden tomar:
 *
 * <ul>
 * <li>1 - Operador</li>
 * <li>3 - Superusuario</li>
 * </ul>
 *
 * @see ValuedEnum
 *
 * @author IECISA
 *
 */
public class PerfilesUsuario extends ValuedEnum {

	private static final long serialVersionUID = 1100856037647964072L;

	public static final PerfilesUsuario OPERADOR = new PerfilesUsuario(
			"operador", 1);

	public static final PerfilesUsuario SUPERUSUARIO = new PerfilesUsuario(
			"superusuario", 3);

	protected PerfilesUsuario(String name, int value) {
		super(name, value);
	}

	public static PerfilesUsuario getEnum(int valor) {
		return (PerfilesUsuario) getEnum(PerfilesUsuario.class,
				valor);
	}

	public static Map getEnumMap() {
		return getEnumMap(PerfilesUsuario.class);
	}

	public static List getEnumList() {
		return getEnumList(PerfilesUsuario.class);
	}

	public static Iterator iterator() {
		return iterator(PerfilesUsuario.class);
	}

}
