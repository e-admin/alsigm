package common;

import java.util.HashMap;
import java.util.Map;

import xml.config.ConfiguracionSistemaArchivoFactory;

import common.util.ArrayUtils;
import common.util.StringUtils;

/**
 * Clase con parámetros de configuración de sistemas externos en descripción
 */
public class ConfigConstantsDescripcionSistemasExternos {

	private static ConfigConstantsDescripcionSistemasExternos configConstants = null;

	private final String POINT_SEPARATOR = ".";
	private final char CAMPOS_SEPARATOR = ',';
	private final String ID_SEPARATOR = ":";
	private final char LIST_SEPARATOR = ';';

	/* Sistemas externos */
	public static final String SISTEMA_INTERESADOS = "Interesados";
	public static final String SISTEMA_GEOGRAFICOS = "Geograficos";

	/* Campos de Interesados */
	public static final String CAMPO_INTERESADOS_IDENTIDAD = "Identidad";
	public static final String CAMPO_INTERESADOS_NUM_IDENTIDAD = "NumIdentidad";
	public static final String CAMPO_INTERESADOS_ROL = "Rol";
	public static final String CAMPO_INTERESADOS_VALIDADO = "Validado";
	public static final String CAMPO_INTERESADOS_VALIDADO_INDICE_NO = "Validado.No";
	public static final String CAMPO_INTERESADOS_VALIDADO_INDICE_SI = "Validado.Si";
	public static final String CAMPO_INTERESADOS_ID_TERCERO = "IdTercero";

	/* Campos de Geograficos */
	public static final String CAMPO_GEOGRAFICOS_PAIS = "Pais";
	public static final String CAMPO_GEOGRAFICOS_PROVINCIA = "Provincia";
	public static final String CAMPO_GEOGRAFICOS_MUNICIPIO = "Municipio";
	public static final String CAMPO_GEOGRAFICOS_POBLACION = "Poblacion";
	public static final String CAMPO_GEOGRAFICOS_LOCALIZACION = "Localizacion";
	public static final String CAMPO_GEOGRAFICOS_VALIDADO = "Validado";
	public static final String CAMPO_GEOGRAFICOS_VALIDADO_INDICE_NO = "Validado.No";
	public static final String CAMPO_GEOGRAFICOS_VALIDADO_INDICE_SI = "Validado.Si";

	/* Valores de campos */
	public static final String VALOR_ID = "Id";
	public static final String VALOR_INDICE = "Indice";
	public static final String VALOR_REF = "Ref";
	public static final String VALOR_REFTYPE = "RefType";
	public static final String VALOR_LISTAS = "Listas";

	private static final Map valoresDefecto = new HashMap();

	/**
	 * Constructor por defecto
	 */
	private ConfigConstantsDescripcionSistemasExternos() {
		valoresDefecto.put("Interesados.Identidad.Id", "9");
		valoresDefecto.put("Interesados.NumIdentidad.Id", "10");
		valoresDefecto.put("Interesados.Rol.Id", "11");
		valoresDefecto.put("Interesados.Validado.Id", "12");
		valoresDefecto.put("Interesados.Validado.No.Indice", "1");
		valoresDefecto.put("Interesados.Validado.Si.Indice", "2");
		valoresDefecto.put("Interesados.IdTercero.Id", "51");
		valoresDefecto.put("Geograficos.Pais.Id", "2");
		valoresDefecto.put("Geograficos.Provincia.Id", "39");
		valoresDefecto.put("Geograficos.Municipio.Id", "40");
		valoresDefecto.put("Geograficos.Poblacion.Id", "41");
		valoresDefecto.put("Geograficos.Localizacion.Id", "42");
		valoresDefecto.put("Geograficos.Validado.Id", "212");
		valoresDefecto.put("Geograficos.Validado.No.Indice", "1");
		valoresDefecto.put("Geograficos.Validado.Si.Indice", "2");
	}

	/**
	 * Permite obtener una instancia de la clase
	 * 
	 * @return ConfigConstants Instancia de la clase
	 */
	public static ConfigConstantsDescripcionSistemasExternos getInstance() {
		if (configConstants == null)
			configConstants = new ConfigConstantsDescripcionSistemasExternos();
		return configConstants;
	}

	/**
	 * Permite obtener un parámetro String
	 * 
	 * @param parameterName
	 *            Parámetro a obtener
	 * @param defaultValue
	 *            Valor por defecto
	 * @return Valor del parámetro
	 */
	private String getStringParameter(String parameterName) {
		try {
			return ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo()
					.getConfiguracionParametrosSE().getValor(parameterName);
		} catch (NullPointerException n) {
			return (String) valoresDefecto.get(parameterName);
		}
	}

	/**
	 * Permite obtener si existe un campo
	 * 
	 * @param sistema
	 *            Nombre del sistema
	 * @param nombreCampo
	 *            Nombre del campo
	 * @return Valor del campo si existe
	 */
	public String getValorCampo(String sistema, String nombreCampo) {
		return getStringParameter(sistema + POINT_SEPARATOR + nombreCampo
				+ POINT_SEPARATOR + VALOR_ID);
	}

	/**
	 * Permite obtener el valor de un indice
	 * 
	 * @param sistema
	 *            Nombre del sistema
	 * @param nombreCampo
	 *            Nombre del campo
	 * @return Valor del indice si existe
	 */
	public String getValorIndice(String sistema, String nombreCampo) {
		return getStringParameter(sistema + POINT_SEPARATOR + nombreCampo
				+ POINT_SEPARATOR + VALOR_INDICE);
	}

	/**
	 * Permite procesar una línea de campos, referencias y listas
	 * 
	 * @param refLine
	 *            Línea a procesar
	 * @return Map con los campos, en cada elemento está guardado otro map con
	 *         la referencia y las listas
	 */
	public Map processRefLine(String refLine) {
		Map ret = new HashMap();

		if (StringUtils.isNotEmpty(refLine)) {
			String[] campos = refLine.split("" + CAMPOS_SEPARATOR);
			if (!ArrayUtils.isEmpty(campos)) {
				for (int i = 0; i < campos.length; i++) {
					String campo = campos[i];
					String[] idRefListsCampo = campo.split(ID_SEPARATOR);
					if (!ArrayUtils.isEmpty(idRefListsCampo)) {
						String id = null;
						String ref = null;
						String lists = null;
						String refType = null;
						if (idRefListsCampo.length == 2) {
							id = idRefListsCampo[0];
							ref = idRefListsCampo[1];
						} else if (idRefListsCampo.length == 3) {
							id = idRefListsCampo[0];
							ref = idRefListsCampo[1];
							refType = idRefListsCampo[2];
						} else if (idRefListsCampo.length == 4) {
							id = idRefListsCampo[0];
							ref = idRefListsCampo[1];
							refType = idRefListsCampo[2];
							lists = idRefListsCampo[3];
						}

						if (StringUtils.isNotEmpty(lists)) {
							lists = lists.replace(LIST_SEPARATOR,
									CAMPOS_SEPARATOR);
						}

						if (StringUtils.isNotEmpty(id)
								&& (StringUtils.isNotBlank(ref))) {
							Map map = new HashMap();
							map.put(VALOR_REF, ref);
							map.put(VALOR_REFTYPE, refType);
							map.put(VALOR_LISTAS, lists);
							ret.put(id, map);
						}
					}
				}
			}
		}

		return ret;
	}

}
