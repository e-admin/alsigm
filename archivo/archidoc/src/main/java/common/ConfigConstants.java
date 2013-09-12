package common;

import java.util.ArrayList;
import java.util.MissingResourceException;

import org.apache.log4j.Logger;

import xml.config.ConfiguracionSistemaArchivoFactory;

import common.util.ArrayUtils;
import common.util.StringUtils;

/**
 * Clase con parámetros de configuración de la aplicación
 */
public class ConfigConstants extends ConfiguracionParametrosConstants {

	/** Logger de la clase. */
	private final static Logger logger = Logger
			.getLogger(ConfigConstants.class);
	private static ConfigConstants configConstants = null;

	/**
	 * Constructor por defecto
	 */
	private ConfigConstants() {
		// RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	}

	/**
	 * Permite obtener una instancia de la clase
	 *
	 * @return ConfigConstants Instancia de la clase
	 */
	public static ConfigConstants getInstance() {
		if (configConstants == null) {
			configConstants = new ConfigConstants();
			if (logger.isDebugEnabled())
				logger.debug(configConstants.toString());
		}
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
	private String getStringParameter(String parameterName, String defaultValue) {
		try {
			String valor = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo()
					.getConfiguracionParametros().getValor(parameterName);

			if (valor == null) {
				return defaultValue;
			} else {
				return valor;
			}
		} catch (MissingResourceException e) {
			return defaultValue;
		} catch (NullPointerException n) {
			return defaultValue;
		}
	}

	/**
	 * Permite obtener el valor booleano de un parámetro
	 *
	 * @param parameterName
	 *            Nombre del parámetro
	 * @return valor true/false del parámetro
	 */
	private boolean getBooleanParameter(String parameterName) {
		String valor = getStringParameter(parameterName, Constants.STRING_EMPTY);

		if (Constants.TRUE_STRING.equals(valor)) {
			return true;
		}
		return false;
	}

	/**
	 * Permite obtener el valor booleano de un parámetro
	 *
	 * @param parameterName
	 *            Nombre del parámetro
	 * @param defaultValue
	 *            Valor por defecto para el parámetro
	 * @return valor true/false del parámetro
	 */
	private boolean getBooleanParameter(String parameterName,
			boolean defaultValue) {
		String valor = getStringParameter(parameterName, Constants.STRING_EMPTY);

		if (Constants.STRING_EMPTY.equals(valor)) {
			return defaultValue;
		} else {
			if (Constants.TRUE_STRING.equals(valor))
				return true;
			else
				return false;
		}
	}

	/**
	 * Permite obtener el valor int de un parámetro.
	 *
	 * @param parameterName
	 *            Nombre del parámetro
	 * @param defaultValue
	 *            Valor por defecto del parámetro
	 * @return Valor int de un parámetro
	 */
	private int getIntParameter(String parameterName, int defaultValue) {
		String valor = getStringParameter(parameterName, Constants.STRING_EMPTY);
		try {
			int valorInt = Integer.parseInt(valor);
			return valorInt;
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * Permite obtener el multivalor String de un parámetro.
	 *
	 * @param parameterName
	 *            Nombre del parámetro
	 * @param defaultValue
	 *            Valor por defecto del parámetro
	 * @return Valor int de un parámetro
	 */
	private String[] getMultivalueStringParameter(String parameterName,
			String[] defaultValue) {
		String valor = getStringParameter(parameterName, Constants.STRING_EMPTY);
		try {
			String[] valores = valor.split(getMultivalueSeparator());
			return valores;
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * Permite obtener el valor del parámetro MOSTRAR_DOC_VITALES
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean getMostrarDocVitales() {
		return getBooleanParameter(MOSTRAR_DOC_VITALES);
	}

	/**
	 * Permite obtener el valor del parámetro MOSTRAR_AYUDA
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean getMostrarAyuda() {
		return getBooleanParameter(MOSTRAR_AYUDA);
	}

	/**
	 * Permite obtener el valor del parámetro USAR_VISOR_IMG_OCX
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean getUsarVisorOcx() {
		return getBooleanParameter(USAR_VISOR_IMG_OCX);
	}

	/**
	 * Permite obtener el valor del parámetro MOSTRAR_CHECK_SUPERUSUARIO
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean getMostrarCheckSuperusuario() {
		return getBooleanParameter(MOSTRAR_CHECK_SUPERUSUARIO);
	}

	/**
	 * Permite obtener el valor del parámetro MOSTRAR_BUSQUEDA_AVANZADA
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean getMostrarBusquedaAvanzada() {
		return getBooleanParameter(MOSTRAR_BUSQUEDA_AVANZADA);
	}

	/**
	 * Permite obtener el valor del parámetro MOSTRAR_CALENDARIO_PREVISIONES
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean getMostrarCalendarioPrevisiones() {
		return getBooleanParameter(MOSTRAR_CALENDARIO_PREVISIONES);
	}

	private String MULTIVALUE_SEPARATOR_DEFECTO = ";";

	/**
	 * Permite obtener el valor del parámetro SEPARADOR_VALORES_MULTIPLES
	 *
	 * @return Valor del parámetro SEPARADOR_VALORES_MULTIPLES
	 */
	public String getMultivalueSeparator() {
		return getStringParameter(SEPARADOR_VALORES_MULTIPLES,
				MULTIVALUE_SEPARATOR_DEFECTO);
	}

	/**
	 * Permite obtener el valor del parámetro
	 * PERMITIR_TRANSFERENCIAS_ENTRE_ARCHIVOS
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean getPermitirTransferenciasEntreArchivos() {
		return getBooleanParameter(PERMITIR_TRANSFERENCIAS_ENTRE_ARCHIVOS);
	}

	/**
	 * Permite obtener el valor del parámetro
	 * PERMITIR_ALTA_DIRECTA_UNIDADES_DOCUMENTALES
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean getPermitirAltaDirectaUnidadesDocumentales() {
		return getBooleanParameter(PERMITIR_ALTA_DIRECTA_UNIDADES_DOCUMENTALES);
	}

	/**
	 * Permite obtener el valor del parámetro SIGNATURACION_POR_ARCHIVO
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean getSignaturacionPorArchivo() {
		return getBooleanParameter(SIGNATURACION_POR_ARCHIVO);
	}

	/**
	 * Permite obtener el valor del parámetro PERMITIR_SIGNATURA_ALFANUMERICA
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean getPermitirSignaturaAlfanumerica() {
		return getBooleanParameter(PERMITIR_SIGNATURA_ALFANUMERICA);
	}

	/**
	 * En la reserva de huecos, si el nivel especificado, está configurado en el
	 * fichero de configuración, no se permitrá selecciona otro nivel inferior.
	 *
	 * @return Cadena de últimos niveles separados por el valor de separación de
	 *         valores
	 */
	public String getLastLevelsUbicacionReserva() {
		return getStringParameter(LAST_LEVEL_UBICACION_RESERVA,
				Constants.STRING_EMPTY);
	}

	/**
	 * Permite obtener el valor del parámetro
	 * PERMITIR_SELECCIONAR_ELEMENTO_UBICACION
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean mostrarBotonSeleccionDestinoUbicacion() {
		return getBooleanParameter(PERMITIR_SELECCIONAR_ELEMENTO_UBICACION);
	}

	private final String[] COLORES_CALENDARIO_PREVISIONES_DEFECTO = new String[] {
			"50ff50", "b0acfa", "fff721", "ffa0a0" };

	/**
	 * Colores a usar en el calendario de previsiones.
	 *
	 * @return Array con los colores a mostrar
	 */
	public String[] getColoresCalendarioPrevisiones() {
		return getMultivalueStringParameter(COLORES_CALENDARIO_PREVISIONES,
				COLORES_CALENDARIO_PREVISIONES_DEFECTO);
	}

	private final int INTERVAL_CALENDARIO_PREVISIONES_DEFECTO = 20;

	/**
	 * Intervalo a usar en el calendario de previsiones.
	 *
	 * @return Intervalo a usar en el calendario de previsiones
	 */
	public int getIntervalCalendarioPrevisiones() {
		return getIntParameter(INTERVAL_CALENDARIO_PREVISIONES,
				INTERVAL_CALENDARIO_PREVISIONES_DEFECTO);
	}

	/**
	 * Separador de código de transferencia por defecto
	 */
	private char SEPARADOR_CODIGO_TRANSFERENCIA_DEFECTO = '/';

	public char getSeparadorCodigoTransferencia() {

		String valor = getStringParameter(SEPARADOR_CODIGO_TRANSFERENCIA,
				Constants.STRING_EMPTY);
		if (StringUtils.isEmpty(valor))
			return SEPARADOR_CODIGO_TRANSFERENCIA_DEFECTO;

		return valor.charAt(0);
	}

	/**
	 * Permite obtener el valor del parámetro CODIGO_UDOC_UNICO
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean getCodigoUdocUnico() {
		return getBooleanParameter(CODIGO_UDOC_UNICO);
	}

	/**
	 * Permite obtener el valor del parámetro FORMATEAR_SIGNATURA_NUMERICA
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean getFormatearSignaturaNumerica() {
		return getBooleanParameter(FORMATEAR_SIGNATURA_NUMERICA);
	}

	/**
	 * Permite obtener el valor del parámetro MOSTRAR_INFORMACION_EXTENDIDA
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean getMostrarInformacionIdentificacionExtendia() {
		return getBooleanParameter(MOSTRAR_INFORMACION_IDENTIFICACION_EXTENDIDA);
	}

	/**
	 * Permite obtener el valor del parámetro ENTIDAD_REQUERIDA
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean getEntidadRequerida() {
		return getBooleanParameter(ENTIDAD_REQUERIDA);
	}

	/**
	 * Permite obtener el valor del parámetro DEBUG
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean getDebug() {
		return getBooleanParameter(DEBUG);
	}

	/**
	 * Permite obtener el valor del parámetro DISTINGUIR_MAYUSCULAS_MINUSCULAS
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean getDistinguirMayusculasMinusculas() {
		return getBooleanParameter(DISTINGUIR_MAYUSCULAS_MINUSCULAS);
	}

	/**
	 * Permite obtener el valor del parámetro LOCALE_DEFAULT
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public String getLocaleDefault() {
		return getStringParameter(LOCALE_DEFAULT,
				Constants.DEFAULT_LOCALE_FOLDER);
	}

	/**
	 * Permite obtener el valor del parámetro MOSTRAR_TODAS_UBICACIONES, que
	 * indica si se muestran todas las ubicaciones (S) o sólo las del usuario
	 * (N)
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean getMostrarTodasUbicaciones() {
		return getBooleanParameter(MOSTRAR_TODAS_UBICACIONES);
	}

	/**
	 * Permite obtener el valor del parámetro PERMITIR_FICHA_ALTA_RELACION, que
	 * indica si se permite elegir una ficha a la hora de dar de alta unidades
	 * documentales o a la hora de crear relaciones de entrega (S) o no (N)
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean getPermitirFichaAltaRelacion() {
		return getBooleanParameter(PERMITIR_FICHA_ALTA_RELACION);
	}

	public boolean getPermitirCodigoClasificadorNulo() {
		return getBooleanParameter(PERMITIR_CODIGO_CLASIFICADOR_NULO);
	}

	/**
	 * Permite obtener el valor del parámetro PERMITIR_SELECCION_SIGNATURACION,
	 * que indica si se permite elegir el tipo de signaturación asociada a cada
	 * archivo
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean getPermitirSeleccionSignaturacion() {
		return getBooleanParameter(PERMITIR_SELECCION_SIGNATURACION);
	}

	/**
	 * Permite obtener el valor del parámetro
	 * PERMITIR_ENVIAR_SOLICITUD_NO_DISPONIBLE, que indica si se permiten enviar
	 * al archivo solicitudes de préstamo o consulta que tienen algún detalle no
	 * disponible
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean getPermitirEnviarSolicitudNoDisponible() {
		return getBooleanParameter(PERMITIR_ENVIAR_SOLICITUD_NO_DISPONIBLE,
				true);
	}

	/**
	 * Permite obtener el valor del parámetro UDOCS_SOLICITUDES_HOJAS_SEPARADAS,
	 * que indica si en las papeletas de solicitud de préstamos y consultas debe
	 * aparecer una unidad documental por hoja o todas en una hoja
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean getUDocsSolicitudesHojasSeparadas() {
		return getBooleanParameter(UDOCS_SOLICITUDES_HOJAS_SEPARADAS, false);
	}

	public boolean getMostrarCampoOrdenacionCuadro() {
		return getBooleanParameter(MOSTRAR_CAMPO_ORDENACION_CUADRO, false);
	}

	/**
	 * Permite obtener el valor del parámetro
	 * MOSTRAR_ADMINISTRACION_ORGANIZACION, que indica si se muestra o no la
	 * administración de organización
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean getMostrarAdministracionOrganizacion() {
		return getBooleanParameter(MOSTRAR_ADMINISTRACION_ORGANIZACION, false);
	}

	/**
	 * Permite obtener el valor del parámetro
	 * NIVELES_DEPOSITO_VISIBLES_CARTELAS, que indica los niveles de depósito
	 * que van a ser visibles en las cartelas de cajas en archivo y depósito
	 * debe aparecer una unidad documental por hoja o todas en una hoja
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public String getNivelesDepositoVisiblesCartelas() {
		return getStringParameter(NIVELES_DEPOSITO_VISIBLES_CARTELAS,
				Constants.STRING_EMPTY);
	}

	public ArrayList getNivelesDepositoVisiblesCartelasAsList() {
		ArrayList nivelesDepositoList = new ArrayList();
		String nivelesDeposito = getNivelesDepositoVisiblesCartelas();
		String separadorValores = getMultivalueSeparator();

		if (!StringUtils.isBlank(nivelesDeposito)) {
			String[] niveles = nivelesDeposito.split(separadorValores);
			if (!ArrayUtils.isEmpty(niveles)) {
				for (int i = 0; i < niveles.length; i++) {
					nivelesDepositoList.add(niveles[i]);
				}
			}
		}

		return nivelesDepositoList;
	}

	/**
	 * Permite obtener el valor del parámetro HEREDAR_CONDICIONES_ACCESO, que
	 * indica si al validar una unidad documental o fracción de serie, deben
	 * heredarse los valores del campo Condiciones de Acceso.
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor no es S
	 */
	public boolean getHeredarCondicionesAcceso() {
		return getBooleanParameter(HEREDAR_CONDICIONES_ACCESO);
	}

	/**
	 * Permite obtener el valor del parámetro
	 * PERMITIR_CARGA_XML_RELACION_EXTRAORDINARIA_SIN_SIGNATURA, que indica si
	 * se permite desde relaciones extraordinarias sin signatura cargar un xml
	 * con información de unidades documentales
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor no es S
	 */
	public boolean getPermitirCargaXmlRelacionExtraordinariaSinSignatura() {
		return getBooleanParameter(PERMITIR_CARGA_XML_RELACION_EXTRAORDINARIA_SIN_SIGNATURA);
	}

	/**
	 * Permite obtener el valor del parámetro
	 * PERMITIR_CARGA_XML_RELACION_EXTRAORDINARIA_CON_SIGNATURA, que indica si
	 * se permite desde relaciones extraordinarias con signatura cargar un xml
	 * con información de unidades documentales
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor no es S
	 */
	public boolean getPermitirCargaXmlRelacionExtraordinariaConSignatura() {
		return getBooleanParameter(PERMITIR_CARGA_XML_RELACION_EXTRAORDINARIA_CON_SIGNATURA);
	}

	/**
	 * Permite obtener el valor del parámetro AGREGAR_DESCRIPCION_DOCUMENTOS.
	 * Indica si se añade la descripcion de los documentos cuando agregamos
	 * nuevos documentos a la unidad documental o fracción de serie.
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor no es S
	 */
	public boolean getAgregarDescripcionDocumentos() {
		return getBooleanParameter(AGREGAR_DESCRIPCION_DOCUMENTOS);
	}

	/**
	 * Permite obtener el valor del parámetro
	 * PERMITIR_CARGA_XML_ALTA_UNIDADES_DOCUMENTALES, que indica si se permite
	 * desde alta de unidades documentales cargar un xml con información de
	 * unidades documentales
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor no es S
	 */
	public boolean getPermitirCargaXmlAltaUnidadesDocumentales() {
		return getBooleanParameter(PERMITIR_CARGA_XML_ALTA_UNIDADES_DOCUMENTALES);
	}

	public String getSeparadorDefectoFechasRelacion() {
		return getStringParameter(SEPARADOR_DEFECTO_FECHAS_RELACION_KEY,
				Constants.SEPARADOR_DEFECTO_FECHAS_RELACION);
	}

	public boolean getMantenerUInstalacionAlCompactar() {
		return getBooleanParameter(MANTENER_CAJA_COMPACTAR);
	}

	public boolean getMotivoSolicitudOpcional() {
		return getBooleanParameter(MOTIVO_SOLICITUD_OPCIONAL);
	}

	public boolean getMarcarArchivoOrganoSolicitud() {
		return getBooleanParameter(MARCAR_ARCHIVO_ORGANO_SOLICITUDES);
	}

	/**
	 * Permite obtener el valor del parámetro MOSTRAR_SALAS
	 *
	 * @return <b>true</b>Si el valor del parámetro es S<br>
	 *         <b>false</b>Si el valor del parámetro es N
	 */
	public boolean getMostrarSalas() {
		return getBooleanParameter(MOSTRAR_SALAS);
	}

	public boolean getMostrarEtiquetasPrestamo() {
		return getBooleanParameter(IMPRIMIR_ETIQUETAS_PRESTAMO);
	}

	public boolean getPermitirReencajado() {
		return getBooleanParameter(PERMITIR_REENCAJADO);
	}

	public boolean getOcultarClasificadoresFondosUsuariosOficina() {
		return getBooleanParameter(OCULTAR_CLASIFICADORES_FONDOS_USUARIOS_OFICINA);
	}

	public boolean isEcmExterno() {
		return getBooleanParameter(ECM_EXTERNO);
	}

	public boolean isInvesdoc8() {
		return getBooleanParameter(INVESDOC_V8);
	}

	public boolean isPermitidoCompactarUdocsNoConsecutivas(){
		return getBooleanParameter(PERMITIR_COMPACTAR_UDOCS_NO_CONSECUTIVAS);
	}

	public String toString() {
		StringBuffer str = new StringBuffer()
				.append(Constants.NEWLINE)
				.append("***********************************************************************************")
				.append(Constants.NEWLINE)
				.append("PARÁMETROS DE LA APLICACIÓN")
				.append(Constants.NEWLINE)
				.append("***********************************************************************************")
				.append(Constants.NEWLINE)
				.append("  - CODIGO_UDOC_UNICO: ")
				.append(getCodigoUdocUnico())
				.append(Constants.NEWLINE)
				.append("  - DISTINGUIR_MAYUSCULAS_MINUSCULAS: ")
				.append(getDistinguirMayusculasMinusculas())
				.append(Constants.NEWLINE)
				.append("  - ENTIDAD_REQUERIDA: ")
				.append(getEntidadRequerida())
				.append(Constants.NEWLINE)
				.append("  - FORMATEAR_SIGNATURA_NUMERICA: ")
				.append(getFormatearSignaturaNumerica())
				.append(Constants.NEWLINE)
				.append("  - MOSTRAR_AYUDA: ")
				.append(getMostrarAyuda())
				.append(Constants.NEWLINE)
				.append("  - MOSTRAR_BUSQUEDA_AVANZADA: ")
				.append(getMostrarBusquedaAvanzada())
				.append(Constants.NEWLINE)
				.append("  - MOSTRAR_CALENDARIO_PREVISIONES: ")
				.append(getMostrarCalendarioPrevisiones())
				.append(Constants.NEWLINE)
				.append("  - MOSTRAR_CHECK_SUPERUSUARIO: ")
				.append(getMostrarCheckSuperusuario())
				.append(Constants.NEWLINE)
				.append("  - MOSTRAR_DOC_VITALES: ")
				.append(getMostrarDocVitales())
				.append(Constants.NEWLINE)
				.append("  - MOSTRAR_INFORMACION_IDENTIFICACION_EXTENDIDA: ")
				.append(getMostrarInformacionIdentificacionExtendia())
				.append(Constants.NEWLINE)
				.append("  - NIVELES_DEPOSITO_VISIBLES_CARTELAS: ")
				.append(getNivelesDepositoVisiblesCartelas())
				.append(Constants.NEWLINE)
				.append("  - PERMITIR_ALTA_DIRECTA_UNIDADES_DOCUMENTALES: ")
				.append(getPermitirAltaDirectaUnidadesDocumentales())
				.append(Constants.NEWLINE)
				.append("  - PERMITIR_SELECCIONAR_ELEMENTO_UBICACION: ")
				.append(mostrarBotonSeleccionDestinoUbicacion())
				.append(Constants.NEWLINE)
				.append("  - PERMITIR_SIGNATURA_ALFANUMERICA: ")
				.append(getPermitirSignaturaAlfanumerica())
				.append(Constants.NEWLINE)
				.append("  - PERMITIR_TRANSFERENCIAS_ENTRE_ARCHIVOS: ")
				.append(getPermitirTransferenciasEntreArchivos())
				.append(Constants.NEWLINE)
				.append("  - SIGNATURACION_POR_ARCHIVO: ")
				.append(getSignaturacionPorArchivo())
				.append(Constants.NEWLINE)
				.append("  - UDOCS_SOLICITUDES_HOJAS_SEPARADAS: ")
				.append(getUDocsSolicitudesHojasSeparadas())
				.append(Constants.NEWLINE)
				.append("  - USAR_VISOR_IMG_OCX: ")
				.append(getUsarVisorOcx())
				.append(Constants.NEWLINE)
				.append("  - DEBUG: ")
				.append(getDebug())
				.append(Constants.NEWLINE)
				.append("  - COLORES_CALENDARIO_PREVISIONES: ")
				.append(getColoresCalendarioPrevisiones())
				.append(Constants.NEWLINE)
				.append("  - INTERVAL_CALENDARIO_PREVISIONES: ")
				.append(getIntervalCalendarioPrevisiones())
				.append(Constants.NEWLINE)
				.append("  - LAST_LEVEL_UBICACION_RESERVA: ")
				.append(getLastLevelsUbicacionReserva())
				.append(Constants.NEWLINE)
				.append("  - LOCALE_DEFAULT: ")
				.append(getLocaleDefault())
				.append(Constants.NEWLINE)
				.append("  - SEPARADOR_CODIGO_TRANSFERENCIA: ")
				.append(getSeparadorCodigoTransferencia())
				.append(Constants.NEWLINE)
				.append("  - SEPARADOR_VALORES_MULTIPLES: ")
				.append(getMultivalueSeparator())
				.append(Constants.NEWLINE)
				.append("  - MOSTRAR_TODAS_UBICACIONES: ")
				.append(getMostrarTodasUbicaciones())
				.append(Constants.NEWLINE)
				.append("  - PERMITIR_FICHA_ALTA_RELACION: ")
				.append(getPermitirFichaAltaRelacion())
				.append(Constants.NEWLINE)
				.append("  - PERMITIR_CODIGO_CLASIFICADOR_NULO: ")
				.append(getPermitirCodigoClasificadorNulo())
				.append(Constants.NEWLINE)
				.append("  - PERMITIR_SELECCION_SIGNATURACION: ")
				.append(getPermitirSeleccionSignaturacion())
				.append(Constants.NEWLINE)
				.append("  - MOSTRAR_CAMPO_ORDENACION_CUADRO: ")
				.append(getMostrarCampoOrdenacionCuadro())
				.append(Constants.NEWLINE)
				.append("  - MOSTRAR_ADMINISTRACION_ORGANIZACION: ")
				.append(getMostrarAdministracionOrganizacion())
				.append(Constants.NEWLINE)
				.append("  - PERMITIR_CARGA_XML_RELACION_EXTRAORDINARIA_SIN_SIGNATURA: ")
				.append(getPermitirCargaXmlRelacionExtraordinariaSinSignatura())
				.append(Constants.NEWLINE)
				.append("  - PERMITIR_CARGA_XML_RELACION_EXTRAORDINARIA_CON_SIGNATURA: ")
				.append(getPermitirCargaXmlRelacionExtraordinariaConSignatura())
				.append(Constants.NEWLINE)
				.append("  - PERMITIR_CARGA_XML_ALTA_UNIDADES_DOCUMENTALES: ")
				.append(getPermitirCargaXmlAltaUnidadesDocumentales())
				.append(Constants.NEWLINE)
				.append("  - AGREGAR_DESCRIPCION_DOCUMENTOS: ")
				.append(getAgregarDescripcionDocumentos())
				.append(Constants.NEWLINE)
				.append("  - SEPARADOR_DEFECTO_FECHAS_RELACION: ")
				.append(getSeparadorDefectoFechasRelacion())
				.append(Constants.NEWLINE)
				.append("  - MANTENER_CAJA_COMPACTAR: ")
				.append(getMantenerUInstalacionAlCompactar())
				.append(Constants.NEWLINE)
				.append("  - MOTIVO SOLICITUD OPCIONAL: ")
				.append(getMotivoSolicitudOpcional())
				.append(Constants.NEWLINE)
				.append("  - MARCAR ARCHIVO ORGANO SOLICITUDES: ")
				.append(getMarcarArchivoOrganoSolicitud())
				// .append(Constants.NEWLINE).append("  - MOSTRAR_SALAS: ").append(getMostrarSalas())
				.append(Constants.NEWLINE)
				.append("  - IMPRIMIR_ETIQUETAS_PRESTAMO: ")
				.append(getMostrarEtiquetasPrestamo())
				.append(Constants.NEWLINE)
				.append("  - PERMITIR_REENCAJADO: ")
				.append(getPermitirReencajado())
				.append(Constants.NEWLINE)
				.append("  - OCULTAR_CLASIFICADORES_FONDOS_USUARIOS_OFICINA: ")
				.append(getOcultarClasificadoresFondosUsuariosOficina())
				.append(Constants.NEWLINE)
				.append("  - ECM_EXTERNO: ")
				.append(isEcmExterno())
				.append(Constants.NEWLINE)
				.append("  - PERMITIR_COMPACTAR_UDOCS_NO_CONSECUTIVAS: ")
				.append(isPermitidoCompactarUdocsNoConsecutivas())
				.append(Constants.NEWLINE)


				.append("***********************************************************************************");
		return str.toString();
	}

}
