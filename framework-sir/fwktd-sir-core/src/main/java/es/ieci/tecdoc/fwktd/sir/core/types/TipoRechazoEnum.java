package es.ieci.tecdoc.fwktd.sir.core.types;

import org.apache.commons.lang.enums.EnumUtils;
import org.apache.commons.lang.enums.ValuedEnum;

/**
 * Enumerados para las constantes de tipos de rechazo.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class TipoRechazoEnum extends ValuedEnum {

	private static final long serialVersionUID = -7551983248599173392L;

	private static final int RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN_VALUE = 0;
	private static final int RECHAZO_A_ENTIDAD_REGISTRAL_INICIAL_VALUE = 1;

	private static final String RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN_STRING = "Rechazo a la entidad registral origen";
	private static final String RECHAZO_A_ENTIDAD_REGISTRAL_INICIAL_STRING = "Rechazo a la entidad registral inicial";

	/**
	 * Rechazo a la entidad registral origen.
	 */
	public static final TipoRechazoEnum RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN = new TipoRechazoEnum(
			RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN_STRING, RECHAZO_A_ENTIDAD_REGISTRAL_ORIGEN_VALUE);

	/**
	 * Rechazo a la entidad registral inicial.
	 */
	public static final TipoRechazoEnum RECHAZO_A_ENTIDAD_REGISTRAL_INICIAL = new TipoRechazoEnum(
			RECHAZO_A_ENTIDAD_REGISTRAL_INICIAL_STRING, RECHAZO_A_ENTIDAD_REGISTRAL_INICIAL_VALUE);

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected TipoRechazoEnum(String name, int value) {
		super(name, value);
	}

	/**
	 * Obtiene la constante asociada al valor.
	 * @param value Valor de la constante
	 * @return Constante.
	 */
	public static TipoRechazoEnum getTipoRechazo(int value) {
		return (TipoRechazoEnum) EnumUtils.getEnum(TipoRechazoEnum.class, value);
	}
}
