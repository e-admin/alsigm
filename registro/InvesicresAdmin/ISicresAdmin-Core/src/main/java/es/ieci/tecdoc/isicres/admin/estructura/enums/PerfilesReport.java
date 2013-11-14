package es.ieci.tecdoc.isicres.admin.estructura.enums;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * Enumerado para representar los diferentes perfiles de usuario que son posibles asociar a un informe (SCR_REPORTPREF). Se trata de
 * una especialización de <code>org.apache.commons.lang.enums.Enum</code>.
 *
 * Los diferentes valores que se pueden tomar:
 *
 * <ul>
 * <li>1 - Operador</li>
 * <li>4 - Superusuario</li>
 * </ul>
 *
 * @see ValuedEnum
 *
 * @author IECISA
 *
 */
public class PerfilesReport extends ValuedEnum{

	/**
	 *
	 */
	private static final long serialVersionUID = 4764243529443650940L;

	public static final PerfilesReport OPERADOR = new PerfilesReport(
			"operador", 1);

	/**
	 * Los perfiles comentados se han creado para dejar constancia de que existen
	 * para dicha tabla, pero ISicresAdmin no los tiene en cuenta, por tanto, no
	 * se deben trabajar con ellos
	 */
//	public static final PerfilesReport ADMINISTRADOR_LIBRO = new PerfilesReport(
//			"administrador de libro", 2);
//
//	public static final PerfilesReport OPERADOR_MAS_ADMLIBRO = new PerfilesReport(
//			"operador de registro + Administrador de libro", 3);

	public static final PerfilesReport SUPERUSUARIO = new PerfilesReport(
			"superusuario", 4);

//	public static final PerfilesReport OPERADOR_MAS_SUPERUSUARIO = new PerfilesReport(
//			"superusuario", 5);
//
//	public static final PerfilesReport ADMLIBRO_SUPERUSUARIO = new PerfilesReport(
//			"superusuario", 6);

	protected PerfilesReport(String name, int value) {
		super(name, value);
	}

	public static PerfilesReport getEnum(int valor) {
		return (PerfilesReport) getEnum(PerfilesReport.class,
				valor);
	}

	public static Map getEnumMap() {
		return getEnumMap(PerfilesReport.class);
	}

	public static List getEnumList() {
		return getEnumList(PerfilesReport.class);
	}

	public static Iterator iterator() {
		return iterator(PerfilesReport.class);
	}

}
