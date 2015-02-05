package es.ieci.tecdoc.isicres.api.business.vo.enums;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;

import com.ieci.tecdoc.common.isicres.Keys;

/**
 * Enumerado que modelado los diferentes tipos de plantillas de informes. Estos
 * son :
 * 
 * <ul>
 * <li>0: Listado genérico</li>
 * <li>1: Relación diaria por destino</li>
 * <li>2: Certificado de registro</li>
 * <li>3: Relación diaria por origen</li>
 * </ul>
 * 
 * @author IECISA
 * 
 */
public class TipoPlantillaInformeEnum extends ValuedEnum {

	public static final TipoPlantillaInformeEnum LISTADO_GENERICO = new TipoPlantillaInformeEnum(
			"LISTADO_GENERICO", Keys.REPORT_TYPE_LM);
	public static final TipoPlantillaInformeEnum RELACION_DIARIA_POR_DESTINO = new TipoPlantillaInformeEnum(
			"RELACION_DIARIA_POR_DESTINO", Keys.REPORT_TYPE_RMD);
	public static final TipoPlantillaInformeEnum RELACION_DIARIA_POR_ORIGEN = new TipoPlantillaInformeEnum(
			"RELACION_DIARIA_POR_ORIGEN", Keys.REPORT_TYPE_RMO);
	public static final TipoPlantillaInformeEnum CERTIFICADO_REGISTRO = new TipoPlantillaInformeEnum(
			"CERTIFICADO_REGISTRO", Keys.REPORT_TYPE_CM);

	protected TipoPlantillaInformeEnum(String name, int value) {
		super(name, value);
	}

	public static TipoPlantillaInformeEnum getEnum(int valor) {
		return (TipoPlantillaInformeEnum) getEnum(
				TipoPlantillaInformeEnum.class, valor);
	}

	public static Map getEnumMap() {
		return getEnumMap(TipoPlantillaInformeEnum.class);
	}

	public static List getEnumList() {
		return getEnumList(TipoPlantillaInformeEnum.class);
	}

	public static Iterator iterator() {
		return iterator(TipoPlantillaInformeEnum.class);
	}

	// Members
	private static final long serialVersionUID = -1936553667500511923L;

}
