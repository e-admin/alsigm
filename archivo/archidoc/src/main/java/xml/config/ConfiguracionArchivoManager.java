package xml.config;

import gcontrol.model.TipoAcceso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import se.autenticacion.DefaultAuthenticationConnectorImpl;
import se.autenticacion.ldap.LdapConnector;
import se.geograficos.DefaultGestorGeograficosImpl;
import se.instituciones.DefaultGestorOrganismoImpl;
import se.procedimientos.DefaultGestorCatalogoImpl;
import se.terceros.DefaultGestorTercerosImpl;
import se.tramites.DefaultSistemaTramitadorImpl;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;
import common.BaseConfiguration;
import common.Constants;
import common.Globals;
import common.exceptions.ConfigException;
import common.util.FileHelper;
import common.util.ListUtils;
import common.util.XmlFacade;

import deposito.DepositoConstants;
import deposito.vos.IconoDeposito;

/**
 * Clase para gestionar los ficheros xml de configuración de la aplicación
 *
 *
 */
public class ConfiguracionArchivoManager {

	protected static final Logger logger = Logger
			.getLogger(ConfiguracionArchivoManager.class);

	public static final String TEXTO_FICHERO_NO_ENCONTRADO = "Error de Configuración. Fichero no Encontrado: ";
	public static final String TEXTO_DIRECTORIO_NO_ENCONTRADO = "Error de Configuración. Directorio no Encontrado: ";
	public static final String TEXTO_ERROR_PARSEAR_FICHERO = "Error de Configuración. Fichero Incorrecto: ";
	public static final String TEXTO_ERROR_GENERICO = "Error en la Configuración. ";
	public static final String TEXTO_ERROR_CONFIGURACION = "Error en los Ficheros de Configuración de la Aplicación. Para más información Revise los logs.";

	public static final String BUSQUEDAS_ARCHIVO_KEY = "_ARCHIVO";

	public static final String EXTENSION_INFORMES = ".jasper";

	private String pathConfiguracion = null;

	private boolean errores = false;

	private static final String ARCHIVO_CFG = "ARCHIVO_CFG_KEY";

	private static final String LOG4J = "LOG4J_KEY";

	private static final String FWKTD_DM = "FKWTD_DM";

	private static final String ORGANIZATION_RESOURCES = "ORGANIZATION_RESOURCES_KEY";

	private static final String ICONOS_DEPOSITO_RESOURCES = "ICONOS_DEPOSITO_RESOURCES_KEY";

	// SISTEMAS GESTORES
	private static final String GESTOR_GEOGRAFICOS = "GESTOR_GEOGRAFICOS_KEY";

	private static final String GESTOR_ORGANIZACION = "GESTOR_ORGANIZACION_KEY";

	private static final String GESTOR_PROCEDIMIENTOS = "GESTOR_PROCEDIMIENTOS_KEY";
	private static final String GESTOR_TERCEROS = "GESTOR_TERCEROS_KEY";

	private static final String GESTOR_TRAMITES = "GESTOR_TRAMITES_KEY";
	private static final String GESTOR_USUARIO = "GESTOR_USUARIO_KEY";
	private static final String LDAP_USER_ATTRIBUTES = "LDAP_USER_ATTRIBUTES_KEY";
	// TRANSFERENCIAS
	private static final String MAP_FS_UDOC = "MAP_FS_UDOC_KEY";

	//MAPEOS
	private static final String MAPEO_UDOC = "MAPEO_UDOC";

	private static final String MAPEO_FS = "MAPEO_FS";


	// ESQUEMAS XML
	public static final String XSD_FICHAS = "XSD_FICHAS_KEY";
	public static final String XSD_FORMATOS_FICHAS = "XSD_FORMATOS_FICHAS_KEY";
	public static final String XSD_MAPEO_DESCRICRIPCION = "XSD_MAPEO_DESCRIPCION_KEY";

	// XSL
	public static final String XSL_CONSULTA = "XSL_CONSULTA_KEY";

	public static final String XSL_EDICION = "XSL_EDICION_KEY";

	public static final String XSL_EXPORTACION = "XSL_EXPORTACION_KEY";
	public static final String XSL_AUDITORIA_LOG = "XSL_AUDITORIA_LOG_KEY";
	public static final String XSL_WS_TRANSFERENCIAS = "XSL_WS_TRANSFERENCIAS_KEY";

	// Directorio de INFORMES
	private static final String INFORMES = "INFORMES_KEY";
	/**
	 * Definción de la Imagen En Blanco. Para mostrar en caso de que no se
	 * encuentren las imagenes de los informes.
	 */
	private static final String BLANK_IMAGE = "BLANK_IMAGE_KEY";
	// Definición de Informes de la Aplicación

	//INFORMES

	// INFORMES > GENERAL
	/**
	 * Subinforme cabecera de todos los informes excepto las cartelas
	 */
	public static final String CABECERA = "cabecera";

	/**
	 * Subinforme que contiene el pie de todos los informes, excepto las cartelas
	 */
	public static final String PIE = "pie";

	//INFORMES > TRANSFERENCIAS

	/**
	 * Informe de las cartelas de las unidades de instalaci&oacute;n en la oficina.<br/>
	 */
	public static final String CARTELAS_OFICINA = "cartelasOficina";

	/**
	 * Informe de la unidades de instalación de la relación de entrega
	 */
	public static final String INFORME_RELACION = "informeRelacion";

	/**
	 * Informe de las unidades documentales de la relación de entrega
	 */
	public static final String INFORME_DETALLES_RELACION = "informeDetallesRelacion";

	/**
	 * Subinforme que muestra los datos de las unidades documentales de la relación de entrega.
	 */
	public static final String INFORME_DETALLES_RELACION_SUBINFORME = "informeDetallesRelacionSubinforme";

	/**
	 * Informe de Reservas de espacio en el depósito
	 */
	public static final String INFORME_RESERVAS = "informeReservas";

	/**
	 * Informe de cotejo de las unidades documentales de la relación de entrega
	 */
	public static final String INFORME_COTEJO = "informeCotejo";

	/**
	 * Informe de las cartelas de las unidades de instalación cuando la transferencia está en el archivo.
	 */
	public static final String CARTELAS_ARCHIVO = "cartelasArchivo";

	/**
	 * Informe de las cartelas de las unidades documentales cuando la transferencia está en el archivo
	 */
	public static final String CARTELAS_ARCHIVO_UDOCS = "cartelasArchivoUDocs";


	/**
	 * Informe de las cartelas de las unidades de instalación en el depósito
	 */
	public static final String CARTELAS_DEPOSITO = "cartelasDeposito";

	/**
	 * Informe de las cartelas de las unidades documentales en el depósito
	 */
	public static final String CARTELAS_DEPOSITO_UDOCS = "cartelasDepositoUDocs";

	/**
	 * Informe de ubicación de las unidades documentales de la transferencia o alta de unidades
	 */
	public static final String INFORME_UBICACION = "informeUbicacion";

	/**
	 * Informe de Alta de unidades documentales
	 */
	public static final String JUSTIFICANTE_ALTA_UDOCS = "justificanteAltaUDocs";


	//DEPÓSITO
	/**
	 * Informe de reubicación de unidades de instalación en el depósito
	 */
	public static final String INFORME_REUBICACION_UINST = "informeReubicacionUinst";

	/**
	 * Informe de reubicaión de unidades documentales (compactar) en el depósito
	 */
	public static final String INFORME_REUBICACION = "informeReubicacion";


	//PRESTAMOS
	/**
	 * Informe de Entrega del Préstamo
	 */
	public static final String PAPELETA_ENTREGA_PRESTAMO = "papeletaEntregaPrestamo";

	/**
	 * Informe de Entrega de los detalles del préstamo.
	 */
	public static final String PAPELETA_TESTIGO_PRESTAMO = "papeletaTestigoPrestamo";

	/**
	 * Informe de devoluciones para que el operario de depósito pueda ubicarlas en su correspondiente unidad de instalación.
	 */
	public static final String INFORME_DEVOLUCION_FECHAS = "informeDevolucionFechas";


	/**
	 * Informe de la reclamación de un préstamo
	 */
	public static final String PAPELETA_RECLAMACION_PRESTAMO = "papeletaReclamacionPrestamo";

	/**
	 * Etiquetas de las unidades documentales del préstamo.
	 * Para que se muestre este informe debe estar el parámetro IMPRIMIR_ETIQUETAS_PRESTAMO = S
	 */
	public static final String PAPELETA_ETIQUETA_PRESTAMO = "papeletaEtiquetaPrestamo";

	/**
	 * Justificante de la devolución del préstamo.
	 */
	public static final String JUSTIFICANTE_DEVOLUCION = "justificanteDevolucion";

	/**
	 * Subinforme que contiene las unidades documentales del justificante del préstamo.
	 */
	public static final String JUSTIFICANTE_DEVOLUCION_SUBINFORME = "justificanteDevolucionSubinforme";


	//CONSULTAS
	/**
	 * Informe de entrega de consulta
	 */
	public static final String PAPELETA_ENTREGA_CONSULTA = "papeletaEntregaConsulta";



	public static final String INFORME_ELIMINACION = "informeEliminacion";
	public static final String INFORME_MOVER_ELEMENTO = "informeMoverElemento";
	public static final String INFORME_MOVER_UDOCS = "informeMoverUdocs";



	public static final String INFORME_SELECCION = "informeSeleccion";



	public static final String INFORME_UNIFICAR_DESCRIPTORES = "informeUnificarDescriptores";




	public static final String INFORME_USUARIOS_CONSULTA = "informeUsuariosConsulta";
	public static final String INFORME_USUARIOS_ASISTENCIA = "informeUsuariosAsistencia";
	public static final String INFORME_EXPEDIENTES_USUARIO = "informeExpedientesUsuario";
	public static final String INFORME_RESUMEN_SERVICIOS = "informeResumenServicios";
	public static final String INFORME_TEMAS_INVESTIGACION = "informeTemasInvestigacion";
	public static final String INFORME_UNIDADES_CONSULTADAS = "informeUnidadesConsultadas";
	public static final String INFORME_UNIDADES_SERIE = "informeUDocsSerie";

	// BUSQUEDAS
	private static final String ACCIONES_BUSQUEDA = "ACCIONES_BUSQUEDA_KEY";
	private static final String BUSQUEDAS_CFG = "BUSQUEDAS_CFG_KEY";
	// BANDEJA
	public static final String BANDEJA_AVANZADA = "BANDEJA_AVANZADA_KEY";
	private static final String BANDEJA_AVANZADA_ARCHIVO = "BANDEJA_AVANZADA_KEY_ARCHIVO";

	public static final String BANDEJA_SIMPLE = "BANDEJA_SIMPLE_KEY";
	private static final String BANDEJA_SIMPLE_ARCHIVO = "BANDEJA_SIMPLE_KEY_ARCHIVO";

	// DESCRIPCION
	public static final String REEMPLAZO_SIMPLE = "REEMPLAZO_SIMPLE_KEY";
	private static final String REEMPLAZO_SIMPLE_ARCHIVO = "REEMPLAZO_SIMPLE_KEY_ARCHIVO";
	// ELEMENTOS
	public static final String ELEMENTOS = "ELEMENTOS_KEY";
	private static final String ELEMENTOS_ARCHIVO = "ELEMENTOS_KEY_ARCHIVO";

	// ELIMINACION
	public static final String ELIMINACION = "ELIMINACION_KEY";
	private static final String ELIMINACION_ARCHIVO = "ELIMINACION_KEY_ARCHIVO";

	// FONDOS
	public static final String FONDOS_AVANZADA = "FONDOS_AVANZADA_KEY";
	private static final String FONDOS_AVANZADA_ARCHIVO = "FONDOS_AVANZADA_KEY_ARCHIVO";

	public static final String FONDOS_RAPIDA = "FONDOS_RAPIDA_KEY";
	private static final String FONDOS_RAPIDA_ARCHIVO = "FONDOS_RAPIDA_KEY_ARCHIVO";

	// SERIE
	public static final String UDOCS_SERIE = "UDOCS_SERIE_KEY";

	// PRESTAMOS
	public static final String PRESTAMOS = "PRESTAMOS_KEY";
	private static final String PRESTAMOS_ARCHIVO = "PRESTAMOS_KEY_ARCHIVO";
	// DEFINCIÓN DE DIRECTORIOS
	private static final String BUSQUEDAS_DIR = "busquedas";
	private static final String BUSQUEDAS_BANDEJA_DIR = FileHelper
			.getNormalizedToSystemFilePath(BUSQUEDAS_DIR, "bandeja");

	private static final String BUSQUEDAS_DESCRIPCION_DIR = FileHelper
			.getNormalizedToSystemFilePath(BUSQUEDAS_DIR, "descripcion");
	private static final String BUSQUEDAS_ELEMENTOS_DIR = FileHelper
			.getNormalizedToSystemFilePath(BUSQUEDAS_DIR, "elementos");

	private static final String BUSQUEDAS_ELIMINACION_DIR = FileHelper
			.getNormalizedToSystemFilePath(BUSQUEDAS_DIR, "eliminacion");
	private static final String BUSQUEDAS_FONDOS_DIR = FileHelper
			.getNormalizedToSystemFilePath(BUSQUEDAS_DIR, "fondos");
	private static final String BUSQUEDAS_PRESTAMOS_DIR = FileHelper
			.getNormalizedToSystemFilePath(BUSQUEDAS_DIR, "prestamos");
	private static final String GESTORES_DIR = "gestores";


	private static final String FWKTD_DM_DIR = "fwktd-dm";


	private static final String TRANSFERENCIAS_DIR = "transferencias";

	private static final String[] KEYS_TRANSFERENCIAS = new String[]{
		MAP_FS_UDOC,
		MAPEO_UDOC,
		MAPEO_FS
	};


	private static final String XSD_DIR = "xsd";
	private static final String XSL_DIR = "xsl";

	private static final String INFORMES_DIR = "reports";
	private static final String IMAGES_INFORMES_DIR = "images";
	private static ConfiguracionArchivoManager configuracionArchivoManager;
	private static HashMap configMap = new LinkedHashMap();
	private static LinkedHashMap iconosDepositoMap = null;

	private static final String[] KEYS_BUSQUEDAS = new String[] {
			BANDEJA_AVANZADA, BANDEJA_AVANZADA_ARCHIVO, BANDEJA_SIMPLE,
			BANDEJA_SIMPLE_ARCHIVO, REEMPLAZO_SIMPLE, REEMPLAZO_SIMPLE_ARCHIVO,
			ELEMENTOS, ELEMENTOS_ARCHIVO, ELIMINACION, ELIMINACION_ARCHIVO,
			FONDOS_AVANZADA, FONDOS_AVANZADA_ARCHIVO, FONDOS_RAPIDA,
			FONDOS_RAPIDA_ARCHIVO, UDOCS_SERIE, PRESTAMOS, PRESTAMOS_ARCHIVO };
	private static final String[] KEYS_INFORMES = new String[] { CABECERA,
			CARTELAS_ARCHIVO, CARTELAS_ARCHIVO_UDOCS, CARTELAS_DEPOSITO,
			CARTELAS_DEPOSITO_UDOCS, CARTELAS_OFICINA, /*INFORME_COTEJO,*/
			INFORME_DETALLES_RELACION, INFORME_DETALLES_RELACION_SUBINFORME,
			INFORME_DEVOLUCION_FECHAS, INFORME_ELIMINACION,
			INFORME_MOVER_ELEMENTO, INFORME_MOVER_UDOCS, INFORME_RELACION,
			INFORME_RESERVAS, INFORME_REUBICACION, INFORME_REUBICACION_UINST,
			INFORME_SELECCION, INFORME_UBICACION,
			INFORME_UNIFICAR_DESCRIPTORES, JUSTIFICANTE_DEVOLUCION,
			JUSTIFICANTE_DEVOLUCION_SUBINFORME, PAPELETA_ENTREGA_CONSULTA,
			PAPELETA_ENTREGA_PRESTAMO, PAPELETA_RECLAMACION_PRESTAMO,
			PAPELETA_TESTIGO_PRESTAMO, PIE, JUSTIFICANTE_ALTA_UDOCS,
			INFORME_UNIDADES_SERIE };

	private static final String[] KEYS_XSD = new String[] {
			XSD_FORMATOS_FICHAS, XSD_FICHAS, XSD_MAPEO_DESCRICRIPCION };

	private static final String[] KEYS_XSL = new String[] { XSL_CONSULTA,
			XSL_EDICION, XSL_EXPORTACION, XSL_AUDITORIA_LOG,
			XSL_WS_TRANSFERENCIAS };

	public static synchronized ConfiguracionArchivoManager getInstance()
			throws ConfigException {

		try {
			if (configuracionArchivoManager == null) {
				configuracionArchivoManager = new ConfiguracionArchivoManager();
			}
		} catch (ConfigException e) {
			configuracionArchivoManager = null;
			throw e;
		}

		return configuracionArchivoManager;
	}

	private static void addToConfigMap(ConfigKeyValue configKeyValue) {
		configMap.put(configKeyValue.getKey(), configKeyValue);
	}

	private static Image getImage(String path) throws BadElementException,
			MalformedURLException, IOException {
		return Image.getInstance(path);
	}

	private static URL getUrlFromClasspath(String path) {
		return ConfiguracionArchivoManager.class.getResource(path);
		// return new ClassPathResource(path).getURL();
		// return
		// ConfiguracionArchivoManager.class.getClassLoader().getResource(FileHelper.SEPARADOR_DIRECTORIOS
		// + path);
	}

	private ConfiguracionArchivoManager() throws ConfigException {

		try {
			BaseConfiguration config = BaseConfiguration.getInstance();

			this.pathConfiguracion = FileHelper
					.getNormalizedToSystemPath((String) config
							.getConfigurationProperty(BaseConfiguration.ARCHIDOC_BASE_CONFIG_DIR));

			if (this.pathConfiguracion == null) {
				throw new Exception();
			}

			ConfigKeyValue archivo_cfg_key = getKey(ARCHIVO_CFG,
					getFinalPath(null, "archivo-cfg.xml"),
					Globals.RULES_ARCHIVO_CFG_FILE, true);
			ConfigKeyValue log4j_key = getKey(LOG4J,
					getFinalPath(null, "log4j.xml"), true);

			ConfigKeyValue fwktd_dm_config = getKey(FWKTD_DM,
					getFinalPath(FWKTD_DM_DIR, "fwktd-dm-config"));

			ConfigKeyValue organization_resources_key = getKey(
					ORGANIZATION_RESOURCES,
					getFinalPath(null, "OrganizationResources.properties"),
					true);
			ConfigKeyValue deposito_resources_key = getKey(
					ICONOS_DEPOSITO_RESOURCES,
					getFinalPath(null, "IconosDepositoResources.properties"),
					true);

			// SISTEMAS GESTORES
			ConfigKeyValue gestor_geograficos_key = getKey(
					GESTOR_GEOGRAFICOS,
					getFinalPath(GESTORES_DIR, "sistema_gestor_geograficos.xml"),
					true);
			ConfigKeyValue gestor_organizacion_key = getKey(
					GESTOR_ORGANIZACION,
					getFinalPath(GESTORES_DIR,
							"sistema_gestor_organizacion.xml"));
			ConfigKeyValue gestor_procedimientos_key = getKey(
					GESTOR_PROCEDIMIENTOS,
					getFinalPath(GESTORES_DIR,
							"sistema_gestor_procedimientos.xml"));
			ConfigKeyValue gestor_terceros_key = getKey(GESTOR_TERCEROS,
					getFinalPath(GESTORES_DIR, "sistema_gestor_terceros.xml"));
			ConfigKeyValue gestor_tramites_key = getKey(GESTOR_TRAMITES,
					getFinalPath(GESTORES_DIR, "sistema_gestor_tramites.xml"));
			ConfigKeyValue gestor_usuario_key = getKey(GESTOR_USUARIO,
					getFinalPath(GESTORES_DIR, "sistema_gestor_usuarios.xml"));
			ConfigKeyValue ldap_user_attributes_key = getKey(
					LDAP_USER_ATTRIBUTES,
					getFinalPath(GESTORES_DIR, "ldapUserAttributes.xml"));

			// TRANSFERENCIAS
			ConfigKeyValue map_fs_udoc_key = getKey(MAP_FS_UDOC,
					getFinalPath(TRANSFERENCIAS_DIR, "map_fs_udoc.xml"),
					Globals.RULES_MAP_FS_UDOC_FILE);

			ConfigKeyValue mapeo_udoc = getKey(
					MAPEO_UDOC,
					getFinalPath(TRANSFERENCIAS_DIR, "mapeo_udoc_transferencia.xml"));

			ConfigKeyValue mapeo_fs = getKey(
					MAPEO_FS,
					getFinalPath(TRANSFERENCIAS_DIR, "mapeo_fs_transferencia.xml"));


			// ESQUEMAS XML
			ConfigKeyValue xsd_fichas_key = getKey(XSD_FICHAS,
					getFinalPath(XSD_DIR, "fichas.xsd"));
			ConfigKeyValue xsd_formatos_fichas_key = getKey(
					XSD_FORMATOS_FICHAS,
					getFinalPath(XSD_DIR, "formatos_fichas.xsd"));

			ConfigKeyValue xsd_mapeo_descripcion_key = getKey(XSD_MAPEO_DESCRICRIPCION
					,getFinalPath(XSD_DIR, "mapeo_descripcion.xsd")
			);


			// XSL
			ConfigKeyValue xsl_consulta_key = getKey(XSL_CONSULTA,
					getFinalPath(XSL_DIR, "templateConsultaFicha.xsl"));
			ConfigKeyValue xsl_edicion_key = getKey(XSL_EDICION,
					getFinalPath(XSL_DIR, "templateEdicionFicha.xsl"));
			ConfigKeyValue xsl_exportacion_key = getKey(XSL_EXPORTACION,
					getFinalPath(XSL_DIR, "templateExportacionFicha.xsl"));
			ConfigKeyValue xsl_auditoria_log_key = getKey(XSL_AUDITORIA_LOG,
					getFinalPath(XSL_DIR, "transformLogTemplate.xsl"));
			ConfigKeyValue xsl_ws_transferencias_key = getKey(
					XSL_WS_TRANSFERENCIAS,
					getFinalPath(XSL_DIR, "templateResultWsTransferencias.xsl"));

			// Director de INFORMES
			ConfigKeyValue informes_key = getKey(INFORMES,
					getFinalPath(null, INFORMES_DIR));

			ConfigKeyValue blank_image_key = getKey(
					BLANK_IMAGE,
					getFinalPath(INFORMES_DIR
							+ FileHelper.SEPARADOR_DIRECTORIOS
							+ IMAGES_INFORMES_DIR, "blank.gif"));

			// BUSQUEDAS
			ConfigKeyValue acciones_busqueda_key = getKey(ACCIONES_BUSQUEDA,
					getFinalPath(BUSQUEDAS_DIR, "acciones_busquedas.xml"),
					Globals.RULES_ACCIONES_BUSQUEDA, true);
			ConfigKeyValue busquedas_cfg_key = getKey(BUSQUEDAS_CFG,
					getFinalPath(BUSQUEDAS_DIR, "busquedas-cfg.xml"),
					Globals.RULES_BUSQUEDAS_CFG_FILE, true);

			// BANDEJA
			ConfigKeyValue bandeja_avanzada_key = getKey(
					BANDEJA_AVANZADA,
					getFinalPath(BUSQUEDAS_BANDEJA_DIR, "bandeja_avanzada.xml"),
					Globals.RULES_BUSQUEDAS, true);
			ConfigKeyValue bandeja_avanzada_archivo_key = getKey(
					BANDEJA_AVANZADA_ARCHIVO,
					getFinalPath(BUSQUEDAS_BANDEJA_DIR,
							"bandeja_avanzada_archivo.xml"),
					Globals.RULES_BUSQUEDAS, true);
			ConfigKeyValue bandeja_simple_key = getKey(BANDEJA_SIMPLE,
					getFinalPath(BUSQUEDAS_BANDEJA_DIR, "bandeja_simple.xml"),
					Globals.RULES_BUSQUEDAS, true);
			ConfigKeyValue bandeja_simple_archivo_key = getKey(
					BANDEJA_SIMPLE_ARCHIVO,
					getFinalPath(BUSQUEDAS_BANDEJA_DIR,
							"bandeja_simple_archivo.xml"),
					Globals.RULES_BUSQUEDAS, true);

			// DESCRIPCION
			ConfigKeyValue reemplazo_simple_key = getKey(
					REEMPLAZO_SIMPLE,
					getFinalPath(BUSQUEDAS_DESCRIPCION_DIR,
							"reemplazo_simple.xml"), Globals.RULES_BUSQUEDAS,
					true);
			ConfigKeyValue reemplazo_simple_archivo_key = getKey(
					REEMPLAZO_SIMPLE_ARCHIVO,
					getFinalPath(BUSQUEDAS_DESCRIPCION_DIR,
							"reemplazo_simple_archivo.xml"),
					Globals.RULES_BUSQUEDAS, true);

			// ELEMENTOS
			ConfigKeyValue elementos_key = getKey(ELEMENTOS,
					getFinalPath(BUSQUEDAS_ELEMENTOS_DIR, "elementos.xml"),
					Globals.RULES_BUSQUEDAS, true);
			ConfigKeyValue elementos_archivo_key = getKey(
					ELEMENTOS_ARCHIVO,
					getFinalPath(BUSQUEDAS_ELEMENTOS_DIR,
							"elementos_archivo.xml"), Globals.RULES_BUSQUEDAS,
					true);

			// ELIMINACION
			ConfigKeyValue eliminacion_key = getKey(
					ELIMINACION,
					getFinalPath(BUSQUEDAS_ELIMINACION_DIR,
							"eliminacion_sel_udocs.xml"),
					Globals.RULES_BUSQUEDAS, true);
			ConfigKeyValue eliminacion_archivo_key = getKey(
					ELIMINACION_ARCHIVO,
					getFinalPath(BUSQUEDAS_ELIMINACION_DIR,
							"eliminacion_sel_udocs_archivo.xml"),
					Globals.RULES_BUSQUEDAS, true);

			// FONDOS
			ConfigKeyValue fondos_avanzada_key = getKey(FONDOS_AVANZADA,
					getFinalPath(BUSQUEDAS_FONDOS_DIR, "fondos_avanzada.xml"),
					Globals.RULES_BUSQUEDAS, true);
			ConfigKeyValue fondos_avanzada_archivo_key = getKey(
					FONDOS_AVANZADA_ARCHIVO,
					getFinalPath(BUSQUEDAS_FONDOS_DIR,
							"fondos_avanzada_archivo.xml"),
					Globals.RULES_BUSQUEDAS, true);
			ConfigKeyValue fondos_rapida_key = getKey(FONDOS_RAPIDA,
					getFinalPath(BUSQUEDAS_FONDOS_DIR, "fondos_rapida.xml"),
					Globals.RULES_BUSQUEDAS, true);
			ConfigKeyValue fondos_rapida_archivo_key = getKey(
					FONDOS_RAPIDA_ARCHIVO,
					getFinalPath(BUSQUEDAS_FONDOS_DIR,
							"fondos_rapida_archivo.xml"),
					Globals.RULES_BUSQUEDAS, true);

			ConfigKeyValue udocs_serie_key = getKey(UDOCS_SERIE,
					getFinalPath(BUSQUEDAS_FONDOS_DIR, "udocs_serie.xml"),
					Globals.RULES_BUSQUEDAS, true);
			// PRESTAMOS
			ConfigKeyValue prestamos_key = getKey(PRESTAMOS,
					getFinalPath(BUSQUEDAS_PRESTAMOS_DIR, "prestamos.xml"),
					Globals.RULES_BUSQUEDAS, true);
			ConfigKeyValue prestamos_archivo_key = getKey(
					PRESTAMOS_ARCHIVO,
					getFinalPath(BUSQUEDAS_PRESTAMOS_DIR,
							"prestamos_archivo.xml"), Globals.RULES_BUSQUEDAS,
					true);

			// Añadir los elementos al Map.
			addToConfigMap(archivo_cfg_key);
			addToConfigMap(log4j_key);


			addToConfigMap(organization_resources_key);
			addToConfigMap(deposito_resources_key);

			//FWKTD_DM
			addToConfigMap(fwktd_dm_config);

			// SISTEMAS GESTORES
			addToConfigMap(gestor_geograficos_key);
			addToConfigMap(gestor_organizacion_key);
			addToConfigMap(gestor_procedimientos_key);
			addToConfigMap(gestor_terceros_key);
			addToConfigMap(gestor_tramites_key);
			addToConfigMap(gestor_usuario_key);
			addToConfigMap(ldap_user_attributes_key);

			// TRANSFERENCIAS
			addToConfigMap(map_fs_udoc_key);
			addToConfigMap(mapeo_fs);
			addToConfigMap(mapeo_udoc);

			// ESQUEMAS XML
			addToConfigMap(xsd_fichas_key);
			addToConfigMap(xsd_formatos_fichas_key);
			addToConfigMap(xsd_mapeo_descripcion_key);

			// XSL
			addToConfigMap(xsl_consulta_key);
			addToConfigMap(xsl_edicion_key);
			addToConfigMap(xsl_exportacion_key);
			addToConfigMap(xsl_auditoria_log_key);
			addToConfigMap(xsl_ws_transferencias_key);

			// Directorio de INFORMES
			addToConfigMap(informes_key);

			// Directorio de Imagenes de INFORMES
			addToConfigMap(blank_image_key);

			// BUSQUEDAS
			addToConfigMap(acciones_busqueda_key);
			addToConfigMap(busquedas_cfg_key);

			// BANDEJA
			addToConfigMap(bandeja_avanzada_key);
			addToConfigMap(bandeja_avanzada_archivo_key);
			addToConfigMap(bandeja_simple_key);
			addToConfigMap(bandeja_simple_archivo_key);

			// DESCRIPCION
			addToConfigMap(reemplazo_simple_key);
			addToConfigMap(reemplazo_simple_archivo_key);

			// ELEMENTOS
			addToConfigMap(elementos_key);
			addToConfigMap(elementos_archivo_key);

			// ELIMINACION
			addToConfigMap(eliminacion_key);
			addToConfigMap(eliminacion_archivo_key);

			// FONDOS
			addToConfigMap(fondos_avanzada_key);
			addToConfigMap(fondos_avanzada_archivo_key);
			addToConfigMap(fondos_rapida_key);
			addToConfigMap(fondos_rapida_archivo_key);
			addToConfigMap(udocs_serie_key);

			// PRESTAMOS
			addToConfigMap(prestamos_key);
			addToConfigMap(prestamos_archivo_key);

			errores = false;

			testConfiguracion();
			if (errores) {
				throw new ConfigException(TEXTO_ERROR_CONFIGURACION);
			}

		} catch (Exception e) {
			throw new ConfigException(e, "Configuración Archivo",
					Constants.BLANK);
		}

	}

	public synchronized AccionBusqueda getAccionBusqueda(String accion) {
		return getAccionesBusqueda().getAccionBusqueda(accion);
	}

	public synchronized Properties getOrganizationProperties() {

		ConfigKeyValue configKeyValue = (ConfigKeyValue) configMap
				.get(ORGANIZATION_RESOURCES);

		Properties organizationProperties = new Properties();

		if (configKeyValue != null) {
			Object value = configKeyValue.getValue();

			if (value != null) {
				if (value instanceof Properties)
					organizationProperties = (Properties) value;
			} else {
				InputStream file;
				try {

					file = getFichero(configKeyValue.getPath());
					organizationProperties.load(file);

					if (configKeyValue.isSingleton()) {
						configKeyValue.setValue(organizationProperties);
					}

					if (logger.isDebugEnabled())
						logger.debug(configKeyValue.getKey() + ":"
								+ configKeyValue.getPath());

				} catch (Exception e) {
					throw getConfigException(e, configKeyValue.getPath());
				}
			}
		}

		return organizationProperties;

	}

	public synchronized Properties getIconosDepositoProperties() {

		ConfigKeyValue configKeyValue = (ConfigKeyValue) configMap
				.get(ICONOS_DEPOSITO_RESOURCES);

		Properties depositoProperties = new Properties();

		if (configKeyValue != null) {
			Object value = configKeyValue.getValue();

			if (value != null) {
				if (value instanceof Properties)
					depositoProperties = (Properties) value;
			} else {
				InputStream file;
				try {

					file = getFichero(configKeyValue.getPath());
					depositoProperties.load(file);

					if (configKeyValue.isSingleton()) {
						configKeyValue.setValue(depositoProperties);
					}

					if (logger.isDebugEnabled())
						logger.debug(configKeyValue.getKey() + ":"
								+ configKeyValue.getPath());
				} catch (FileNotFoundException fne) {
					return null;
				} catch (Exception e) {
					throw getConfigException(e, configKeyValue.getPath());
				}
			}
		}

		return depositoProperties;

	}

	public synchronized ConfiguracionSistemaArchivo getArchivoCfg(
			boolean recargar) throws ConfigException {

		ConfigKeyValue configKeyValue = (ConfigKeyValue) configMap
				.get(ARCHIVO_CFG);

		ConfiguracionSistemaArchivo csa = null;

		if (configKeyValue != null) {
			Object value = configKeyValue.getValue();

			if (value != null && recargar == false) {
				if (value instanceof ConfiguracionSistemaArchivo)
					csa = (ConfiguracionSistemaArchivo) value;
			} else {
				InputStream file;
				try {
					file = getFichero(configKeyValue.getPath());
					URL rulesXmlUrl = getUrlFromClasspath(configKeyValue
							.getPathRules());

					Digester digester = DigesterLoader
							.createDigester(rulesXmlUrl);
					csa = (ConfiguracionSistemaArchivo) digester.parse(file);

					if (configKeyValue.isSingleton()) {
						configKeyValue.setValue(csa);
					}

					if (logger.isDebugEnabled())
						logger.debug(configKeyValue.getKey() + ":"
								+ configKeyValue.getPath());

				} catch (Exception e) {
					throw getConfigException(e, configKeyValue.getPath());
				}
			}
		}

		return csa;
	}

	public synchronized Image getBlankImage() throws ConfigException {
		ConfigKeyValue configKeyValue = (ConfigKeyValue) configMap
				.get(BLANK_IMAGE);
		try {
			return getImage(configKeyValue.getPath());

		} catch (Exception e) {
			throw getConfigException(e, configKeyValue.getPath());
		}
	}

	/**
	 * Obtiene el Objeto de Configuración de Búsqueda.
	 *
	 * @param key
	 *            Clave del Objeto (Obtenerla de
	 *            ConfiguracionArchivoManager.ELEMENTO.getValue()
	 * @return Objeto de Busqueda.
	 * @throws Exception
	 */
	public synchronized Busqueda getCfgBusqueda(String key)
			throws ConfigException {

		ConfigKeyValue configKeyValue = (ConfigKeyValue) configMap.get(key);

		Busqueda busqueda = null;

		if (configKeyValue != null) {
			Object value = configKeyValue.getValue();

			if (value != null) {
				if (value instanceof Busqueda)
					busqueda = (Busqueda) value;
			} else {
				try {
					InputStream file = getFichero(configKeyValue.getPath());
					URL rulesXmlUrl = getUrlFromClasspath(configKeyValue
							.getPathRules());

					Digester digester = DigesterLoader
							.createDigester(rulesXmlUrl);
					busqueda = (Busqueda) digester.parse(file);

					if (configKeyValue.isSingleton()) {
						configKeyValue.setValue(busqueda);
					}
				} catch (Exception e) {
					throw getConfigException(e, configKeyValue.getPath());
				}
			}
		}

		return busqueda;
	}

	public ConfigException getConfigException(Exception e, String path) {
		ConfigException configException = null;

		if (e instanceof FileNotFoundException) {
			configException = new ConfigException(e.getLocalizedMessage());
		} else if (e instanceof DocumentException) {
			configException = new ConfigException(TEXTO_ERROR_PARSEAR_FICHERO
					+ path);
		} else {
			configException = new ConfigException(TEXTO_ERROR_GENERICO
					+ e.getMessage());
		}

		return configException;
	}

	public synchronized ConfiguracionBusquedas getConfiguracionBusquedas()
			throws ConfigException {
		ConfigKeyValue configKeyValue = (ConfigKeyValue) configMap
				.get(BUSQUEDAS_CFG);

		ConfiguracionBusquedas configuracionBusquedas = null;

		if (configKeyValue != null) {
			Object value = configKeyValue.getValue();

			if (value != null) {
				if (value instanceof ConfiguracionBusquedas)
					configuracionBusquedas = (ConfiguracionBusquedas) value;
			} else {
				try {
					InputStream file = getFichero(configKeyValue.getPath());
					URL rulesXmlUrl = getUrlFromClasspath(configKeyValue
							.getPathRules());

					Digester digester = DigesterLoader
							.createDigester(rulesXmlUrl);
					configuracionBusquedas = (ConfiguracionBusquedas) digester
							.parse(file);

					if (configKeyValue.isSingleton()) {
						configKeyValue.setValue(configuracionBusquedas);
					}

					if (logger.isDebugEnabled())
						logger.debug(configKeyValue.getKey() + ":"
								+ configKeyValue.getPath());

				} catch (Exception e) {
					throw getConfigException(e, configKeyValue.getPath());
				}
			}
		}

		return configuracionBusquedas;
	}

	public synchronized ConfiguracionMapeoFSUDoc getConfiguracionMapeoFSUDoc()
			throws Exception {
		ConfigKeyValue configKeyValue = (ConfigKeyValue) configMap
				.get(MAP_FS_UDOC);

		ConfiguracionMapeoFSUDoc configuracionBusquedas = null;

		if (configKeyValue != null) {
			Object value = configKeyValue.getValue();

			if (value != null) {
				if (value instanceof ConfiguracionMapeoFSUDoc)
					configuracionBusquedas = (ConfiguracionMapeoFSUDoc) value;
			} else {
				try {
					InputStream file = getFichero(configKeyValue.getPath());
					URL rulesXmlUrl = getUrlFromClasspath(configKeyValue
							.getPathRules());

					Digester digester = DigesterLoader
							.createDigester(rulesXmlUrl);
					configuracionBusquedas = (ConfiguracionMapeoFSUDoc) digester
							.parse(file);

					if (configKeyValue.isSingleton()) {
						configKeyValue.setValue(configuracionBusquedas);

					}
					if (logger.isDebugEnabled())
						logger.debug(configKeyValue.getKey() + ":"
								+ configKeyValue.getPath());

				} catch (Exception e) {
					throw getConfigException(e, configKeyValue.getPath());
				}
			}
		}

		return configuracionBusquedas;
	}

	public String getMapeoUnidadDocumental() throws IOException{
		return getContenidoFicheroAsString(MAPEO_UDOC);
	}

	public String getMapeoFraccionSerie() throws IOException{
		return getContenidoFicheroAsString(MAPEO_FS);
	}


	/**
	 * Obtiene el XML del Sistema Gestor de Autenticación
	 *
	 * @return {@link Document} del Sistema Gestor de Autenticacion.
	 */
	public synchronized Document getDefaultSistemaGestorAutenticacion()
			throws ConfigException {
		return getDocument(GESTOR_USUARIO);
	}

	/**
	 * Obtiene el XML del Sistema Gestor de Catalogo
	 *
	 * @return {@link Document} del Sistema Gestor de Geograficos.
	 */
	public synchronized Document getDefaultSistemaGestorCatalogo()
			throws ConfigException {
		return getDocument(GESTOR_PROCEDIMIENTOS);
	}

	/**
	 * Obtiene el XML del Sistema Gestor de Geográficos
	 *
	 * @return {@link XmlFacade} del Sistema Gestor de Geograficos.
	 * @throws Exception
	 */
	public synchronized XmlFacade getDefaultSistemaGestorGeograficos()
			throws ConfigException {
		return getXmlFacade(GESTOR_GEOGRAFICOS);
	}

	/**
	 * Obtiene el XML del Sistema Gestor de Organización
	 *
	 * @return {@link Document} del Sistema Gestor de Geograficos.
	 */
	public synchronized Document getDefaultSistemaGestorOrganismo()
			throws ConfigException {
		return getDocument(GESTOR_ORGANIZACION);
	}

	/**
	 * Obtiene el XML del Sistema Gestor de Terceros
	 *
	 * @return {@link XmlFacade} del Sistema Gestor de Terceros.
	 */
	public synchronized XmlFacade getDefaultSistemaGestorTerceros()
			throws ConfigException {
		return getXmlFacade(GESTOR_TERCEROS);
	}

	/**
	 * Obtiene el XML del Sistema Gestor de Trámites
	 *
	 * @return {@link Document} del Sistema Gestor de Trámites
	 */
	public synchronized Document getDefaultSistemaGestorTramitador()
			throws ConfigException {
		return getDocument(GESTOR_TRAMITES);
	}

	public InputStream getFichero(String path) throws FileNotFoundException {
		return new FileInputStream(new File(path));
	}

	public void checkPath(String path, String modulo)
			throws FileNotFoundException {
		File directorio = new File(path);
		if (directorio == null || directorio.exists() == false) {
			StringBuffer str = new StringBuffer(TEXTO_DIRECTORIO_NO_ENCONTRADO)
					.append(modulo).append(Constants.STRING_SPACE).append(path);
			throw new FileNotFoundException(str.toString());
		}
	}

	public synchronized XmlFacade getLdapUserAttibutes() throws Exception {
		return getXmlFacade(LDAP_USER_ATTRIBUTES);
	}

	public String getMessageLog(Exception e) {
		return e.getLocalizedMessage();
	}

	/**
	 * Devuelve la Ruta de los ficheros de configuración.
	 *
	 * @return
	 */
	public String getPathConfiguracion() {
		return pathConfiguracion;
	}

	public synchronized String getPathImagenInformes(String nombreImagen,
			String entity) {
		return FileHelper.getNormalizedToSystemFilePath(
				getPathImagenesInformes(entity), nombreImagen);
	}

	public synchronized String getPathInforme(String nombreInforme,
			String entity) {
		return FileHelper.getNormalizedToSystemFilePath(
				getPathInformes(entity), nombreInforme + EXTENSION_INFORMES);
	}

	public synchronized String getPathInformes(String entity) {
		String ruta = null;
		if (entity != null) {
			ruta = FileHelper.getNormalizedToSystemFilePath(
					getPathProperty(INFORMES), entity
							+ FileHelper.SEPARADOR_DIRECTORIOS);
			File rutaFile = new File(ruta);
			if (rutaFile.exists()) {
				return ruta;
			}
		}

		ruta = FileHelper.getNormalizedToSystemFilePath(
				getPathProperty(INFORMES), FileHelper.SEPARADOR_DIRECTORIOS);

		return ruta;
	}

	public synchronized String getPathImagenesInformes(String entity) {
		String ruta = null;
		if (entity != null) {
			ruta = FileHelper.getNormalizedToSystemFilePath(
					getPathProperty(INFORMES), entity);
			ruta = FileHelper.getNormalizedToSystemFilePath(ruta,
					IMAGES_INFORMES_DIR + FileHelper.SEPARADOR_DIRECTORIOS);
			File rutaFile = new File(ruta);
			if (rutaFile.exists()) {
				return ruta;
			}
		}

		ruta = FileHelper.getNormalizedToSystemFilePath(
				getPathProperty(INFORMES), IMAGES_INFORMES_DIR
						+ FileHelper.SEPARADOR_DIRECTORIOS);

		return ruta;
	}

	public synchronized String getPathXSD(String key) {
		return getPathProperty(key);
	}

	public synchronized String getRutaPlantillaXSL(int tipoAcceso) {
		String key = null;
		switch (tipoAcceso) {
		case TipoAcceso.CONSULTA:
			key = XSL_CONSULTA;
			break;
		case TipoAcceso.EDICION:
			key = XSL_EDICION;
			break;
		case TipoAcceso.EXPORTACION:
			key = XSL_EXPORTACION;
			break;
		case TipoAcceso.AUDITORIA:
			key = XSL_AUDITORIA_LOG;
			break;
		case TipoAcceso.IMPORTACION_WS:
			key = XSL_WS_TRANSFERENCIAS;
			break;
		default:
			return null;
		}

		return getPathProperty(key);
	}

	/**
	 * @return the errores
	 */
	public boolean isErrores() {
		return errores;
	}

	/**
	 * @param errores
	 *            the errores to set
	 */
	public void setErrores(boolean errores) {
		this.errores = errores;
	}

	/**
	 * @param pathConfiguracion
	 *            the pathConfiguracion to set
	 */
	public void setPathConfiguracion(String pathConfiguracion) {
		this.pathConfiguracion = pathConfiguracion;
	}

	private boolean comprobarClase(String nombreClaseConfig, String nombreClase) {
		if (nombreClaseConfig != null && nombreClaseConfig.equals(nombreClase)) {
			return true;
		}

		return false;
	}

	// public synchronized String getPathImagesInformes(){
	// return getPathProperty(IN) + SEPARADOR_DIRECTORIOS;
	// }

	private boolean comprobarSistemaEnLista(List listaSistemas,
			String nombreClase) {
		if (ListUtils.isNotEmpty(listaSistemas)) {

			for (Iterator iterator = listaSistemas.iterator(); iterator
					.hasNext();) {
				Sistema sistema = (Sistema) iterator.next();

				if (comprobarClase(sistema.getClase(), nombreClase)) {
					return true;
				}
			}
		}
		return false;
	}

	private synchronized AccionesBusqueda getAccionesBusqueda()
			throws ConfigException {

		ConfigKeyValue configKeyValue = (ConfigKeyValue) configMap
				.get(ACCIONES_BUSQUEDA);

		AccionesBusqueda accionesBusqueda = null;

		if (configKeyValue != null) {
			Object value = configKeyValue.getValue();

			if (value != null) {
				if (value instanceof AccionesBusqueda)
					accionesBusqueda = (AccionesBusqueda) value;
			} else {
				try {
					InputStream file = getFichero(configKeyValue.getPath());
					URL rulesXmlUrl = getUrlFromClasspath(configKeyValue
							.getPathRules());

					Digester digester = DigesterLoader
							.createDigester(rulesXmlUrl);
					accionesBusqueda = (AccionesBusqueda) digester.parse(file);

					if (configKeyValue.isSingleton()) {
						configKeyValue.setValue(accionesBusqueda);
					}

					if (logger.isDebugEnabled())
						logger.debug(configKeyValue.getKey() + ":"
								+ configKeyValue.getPath());

				} catch (Exception e) {
					throw getConfigException(e, configKeyValue.getPath());
				}
			}
		}

		return accionesBusqueda;
	}

	/**
	 * Obtiene el Objeto de Configuración de Búsqueda.
	 *
	 * @param key
	 *            Clave del Objeto (Obtenerla de
	 *            ConfiguracionArchivoManager.ELEMENTO.getValue()
	 * @return Objeto de Busqueda.
	 * @throws Exception
	 */
	private synchronized Document getDocument(String key)
			throws ConfigException {

		ConfigKeyValue configKeyValue = (ConfigKeyValue) configMap.get(key);

		org.dom4j.Document document = null;

		if (configKeyValue != null) {
			Object value = configKeyValue.getValue();

			if (value != null) {
				if (value instanceof Document)
					document = (Document) value;
			} else {
				InputStream is;
				try {
					is = getFichero(configKeyValue.getPath());
					SAXReader saxReader = new SAXReader();
					document = saxReader.read(is);

					if (configKeyValue.isSingleton()) {
						configKeyValue.setValue(document);
					}

				} catch (Exception e) {
					throw getConfigException(e, configKeyValue.getPath());
				}
			}
		}

		return document;
	}

	private String getFinalPath(String directorio, String fichero) {
		if (directorio == null) {
			directorio = getPathConfiguracion();
		} else {
			directorio = FileHelper.getNormalizedToSystemFilePath(
					getPathConfiguracion(), directorio);
		}
		return FileHelper.getNormalizedToSystemFilePath(directorio, fichero);
	}

	private ConfigKeyValue getKey(String key, String path) {
		return new ConfigKeyValue(key, path, null, false);
	}

	private ConfigKeyValue getKey(String key, String path, boolean singleton) {
		return new ConfigKeyValue(key, path, null, singleton);
	}

	private ConfigKeyValue getKey(String key, String path, String pathRules) {
		return new ConfigKeyValue(key, path, pathRules, false);
	}

	private ConfigKeyValue getKey(String key, String path, String pathRules,
			boolean singleton) {
		return new ConfigKeyValue(key, path, pathRules, singleton);
	}

	private String getPathProperty(String key) {
		ConfigKeyValue configKeyValue = (ConfigKeyValue) configMap.get(key);

		String ruta = null;

		if (configKeyValue != null) {
			ruta = configKeyValue.getPath();
		}
		return ruta;
	}

	/**
	 * Obtiene el Objeto de Configuración de Búsqueda.
	 *
	 * @param key
	 *            Clave del Objeto (Obtenerla de
	 *            ConfiguracionArchivoManager.ELEMENTO.getValue()
	 * @return Objeto de Busqueda.
	 * @throws Exception
	 */
	private synchronized XmlFacade getXmlFacade(String key)
			throws ConfigException {

		ConfigKeyValue configKeyValue = (ConfigKeyValue) configMap.get(key);

		XmlFacade xmlFacade = null;

		if (configKeyValue != null) {
			Object value = configKeyValue.getValue();

			if (value != null) {
				if (value instanceof XmlFacade)
					xmlFacade = (XmlFacade) value;
			} else {
				InputStream is;
				try {
					is = getFichero(configKeyValue.getPath());
					xmlFacade = new XmlFacade(is);

					if (configKeyValue.isSingleton()) {
						configKeyValue.setValue(xmlFacade);
					}

				} catch (Exception e) {
					throw getConfigException(e, configKeyValue.getPath());
				}
			}
		}

		return xmlFacade;
	}

	// private void testTransferencias(ConfiguracionSistemaArchivo csa) {
	// //Comprobar si es necesario el fichero de mapeos de unidades documentales
	//
	// //METER COMPROBACION
	// if(false){
	// try {
	// getConfiguracionMapeoFSUDoc();
	// } catch (Exception e) {
	// logger.error(getMessageLog(e));
	// errores = true;
	// }
	// }
	// }

	private void testBusquedas() {

		try {
			getConfiguracionBusquedas();
		} catch (Exception e) {
			logger.error(getMessageLog(e));
			errores = true;
		}

		for (int i = 0; i < KEYS_BUSQUEDAS.length; i++) {
			try {
				getCfgBusqueda(KEYS_BUSQUEDAS[i]);
			} catch (Exception e) {
				logger.error(getMessageLog(e));
				errores = true;
			}
		}
	}

	private void testConfiguracion() {
		try {
			ConfiguracionSistemaArchivo csa = getArchivoCfg(false);
			testGestores(csa);
			testDirectorioLogEliminacion(csa);
			// Comprobar transferencias
			// testTransferencias(csa);
		} catch (Exception e) {
			logger.error(getMessageLog(e));
			errores = true;
		}

		// Comprobar log4j
		testLog4j();

		// Comprobar Busquedas
		testBusquedas();

		// Comprobar los Informes
		testInformes();

		// Comprobar XSD
		testXSD();

		// Comprobar XSL
		testXLS();

		testImagenes();


		testTransferencias();

	}

	private void testTransferencias() {
		for (int i = 0; i < KEYS_TRANSFERENCIAS .length; i++) {
			try {
				ConfigKeyValue cfg = (ConfigKeyValue) configMap
				.get(KEYS_TRANSFERENCIAS[i]);
				getFichero(cfg.getPath());
			} catch (Exception e) {
				logger.error(getMessageLog(e));
				errores = true;
			}
		}
	}

	private void testGestores(ConfiguracionSistemaArchivo csa) {
		// Comprobar si es necesario el fichero de geograficos
		if (comprobarClase(csa.getSistemaGestorGeograficos().getClase(),
				DefaultGestorGeograficosImpl.class.getName())) {
			try {
				getDefaultSistemaGestorGeograficos();
			} catch (Exception e) {
				logger.error(getMessageLog(e));
				errores = true;
			}
		}

		// Comprobar gestor de organizacion
		List listaSistemas = csa.getSistemasGestoresOrganismos();
		if (comprobarSistemaEnLista(listaSistemas,
				DefaultGestorOrganismoImpl.class.getName())) {
			try {
				getDefaultSistemaGestorOrganismo();
			} catch (Exception e) {
				logger.error(getMessageLog(e));
				errores = true;
			}
		}

		// Comprobar procedimientos
		if (comprobarClase(csa.getSistemaGestorCatalogo().getClase(),
				DefaultGestorCatalogoImpl.class.getName())) {
			try {
				getDefaultSistemaGestorCatalogo();
			} catch (Exception e) {
				logger.error(getMessageLog(e));
				errores = true;
			}
		}

		// Comprobar terceros
		if (comprobarClase(csa.getSistemaGestorTerceros().getClase(),
				DefaultGestorTercerosImpl.class.getName())) {
			try {
				getDefaultSistemaGestorTerceros();
			} catch (Exception e) {
				logger.error(getMessageLog(e));
				errores = true;
			}
		}

		// Comprobar gestor de organizacion
		listaSistemas = csa.getSistemasTramitadores();
		if (comprobarSistemaEnLista(listaSistemas,
				DefaultSistemaTramitadorImpl.class.getName())) {
			try {
				getDefaultSistemaGestorTramitador();
			} catch (Exception e) {
				logger.error(getMessageLog(e));
				errores = true;
			}
		}

		// Comprobar Sistema Gestor de Usuarios
		listaSistemas = csa.getSistemasGestoresUsuarios();
		if (comprobarSistemaEnLista(listaSistemas,
				DefaultAuthenticationConnectorImpl.class.getName())) {
			try {
				getDefaultSistemaGestorAutenticacion();
			} catch (Exception e) {
				logger.error(getMessageLog(e));
				errores = true;
			}
		}

		// Comprobar si LdapUserAttributes
		listaSistemas = csa.getSistemasGestoresUsuarios();
		if (comprobarSistemaEnLista(listaSistemas,
				LdapConnector.class.getName())) {
			try {
				getLdapUserAttibutes();
			} catch (Exception e) {
				logger.error(getMessageLog(e));
				errores = true;
			}
		}
	}

	private void testImagenes() {
		try {
			getBlankImage();
		} catch (Exception e) {
			logger.error(getMessageLog(e));
			errores = true;
		}
	}

	private void testInformes() {
		for (int i = 0; i < KEYS_INFORMES.length; i++) {
			try {
				getFichero(getPathInforme(KEYS_INFORMES[i], null));
			} catch (Exception e) {
				logger.error(getMessageLog(e));
				errores = true;
			}
		}
	}

	private void testLog4j() {
		try {
			ConfigKeyValue cfg = (ConfigKeyValue) configMap.get(LOG4J);
			getFichero(cfg.getPath());
		} catch (Exception e) {
			logger.error(getMessageLog(e));
			errores = true;
		}
	}

	/**
	 * Comprueba que el directorio de Logs de eliminación está bien configurado
	 *
	 * @param csa
	 */
	private void testDirectorioLogEliminacion(ConfiguracionSistemaArchivo csa) {
		try {
			checkPath(csa.getConfiguracionFondos()
					.getDirectorioLogsEliminacion(),
					"Directorio de Logs de Eliminación");
		} catch (FileNotFoundException e) {
			logger.error(getMessageLog(e));
			errores = true;
		}
	}

	private void testXLS() {
		for (int i = 0; i < KEYS_XSL.length; i++) {
			try {
				ConfigKeyValue cfg = (ConfigKeyValue) configMap
						.get(KEYS_XSL[i]);
				getFichero(cfg.getPath());
			} catch (Exception e) {
				logger.error(getMessageLog(e));
				errores = true;
			}
		}
	}

	private void testXSD() {
		for (int i = 0; i < KEYS_XSD.length; i++) {
			try {
				ConfigKeyValue cfg = (ConfigKeyValue) configMap
						.get(KEYS_XSD[i]);
				getFichero(cfg.getPath());
			} catch (Exception e) {
				logger.error(getMessageLog(e));
				errores = true;
			}
		}
	}

	public LinkedHashMap getIconosDeposito() {

		if (iconosDepositoMap == null || iconosDepositoMap.isEmpty()) {
			iconosDepositoMap = getIconosDepositoDefault();

			// Sobreescribir con los del fichero si existe
			Properties properties = getIconosDepositoProperties();
			if (properties != null) {
				for (Iterator iterator = properties.keySet().iterator(); iterator
						.hasNext();) {
					String key = (String) iterator.next();
					String value = (String) properties.get(key);

					if (value != null) {
						String[] valores = value
								.split(Constants.SEPARADOR_ICONOS_DEPOSITO);

						if (valores != null && valores.length == 6) {
							IconoDeposito icono = new IconoDeposito(key,
									valores[0], valores[1], valores[2],
									valores[3], valores[4], valores[5]);
							iconosDepositoMap.put(key, icono);
						}
					}
				}
			}
		}

		return iconosDepositoMap;
	}

	public IconoDeposito getIconoDeposito(String idTipoElemento) {
		IconoDeposito iconoDeposito = (IconoDeposito) getIconosDeposito().get(
				idTipoElemento);

		if (iconoDeposito == null) {
			return getDefaultIconoDeposito();
		}
		return iconoDeposito;
	}

	private IconoDeposito getDefaultIconoDeposito() {
		return new IconoDeposito("", "icono_raiz.gif", "icono_raiz.gif",
				"icono_raiz.gif", "icono_raiz.gif", "icono_raiz.gif",
				"icono_raiz.gif");
	}

	private LinkedHashMap getIconosDepositoDefault() {
		if (iconosDepositoMap == null) {
			iconosDepositoMap = new LinkedHashMap();

			IconoDeposito ubicacion = new IconoDeposito(
					DepositoConstants.ID_TIPO_ELEMENTO_UBICACION,
					"deposito0.gif", "deposito1.gif", "deposito2.gif",
					"deposito3.gif", "deposito4.gif", "deposito5.gif");

			IconoDeposito deposito = new IconoDeposito(
					DepositoConstants.ID_TIPO_ELEMENTO_DEPOSITO, "puerta0.gif",
					"puerta1.gif", "puerta2.gif", "puerta3.gif", "puerta4.gif",
					"puerta5.gif");

			IconoDeposito modulo = new IconoDeposito(
					DepositoConstants.ID_TIPO_ELEMENTO_MODULO, "armario0.gif",
					"armario1.gif", "armario2.gif", "armario3.gif",
					"armario4.gif", "armario5.gif");

			IconoDeposito balda = new IconoDeposito(
					DepositoConstants.ID_TIPO_ELEMENTO_BALDA, "balda0.gif",
					"balda1.gif", "balda0.gif", "balda1.gif", "balda0.gif",
					"balda1.gif");
			IconoDeposito planero = new IconoDeposito(
					DepositoConstants.ID_TIPO_PLANERO, "planero0.gif",
					"planero1.gif", "planero2.gif", "planero3.gif",
					"planero4.gif", "planero5.gif");
			IconoDeposito cajonPlanero = new IconoDeposito(
					DepositoConstants.ID_TIPO_CAJON_PLANERO,
					"cajonPlanero0.gif", "cajonPlanero1.gif",
					"cajonPlanero0.gif", "cajonPlanero1.gif",
					"cajonPlanero0.gif", "cajonPlanero1.gif");
			IconoDeposito cuerpo = new IconoDeposito(
					DepositoConstants.ID_TIPO_ELEMENTO_CUERPO, "cuerpo0.gif",
					"cuerpo1.gif", "cuerpo2.gif", "cuerpo3.gif", "cuerpo4.gif",
					"cuerpo5.gif");

			iconosDepositoMap.put(ubicacion.getId(), ubicacion);
			iconosDepositoMap.put(deposito.getId(), deposito);
			iconosDepositoMap.put(modulo.getId(), modulo);
			iconosDepositoMap.put(balda.getId(), balda);
			iconosDepositoMap.put(planero.getId(), planero);
			iconosDepositoMap.put(cajonPlanero.getId(), cajonPlanero);
			iconosDepositoMap.put(cuerpo.getId(), cuerpo);

		}

		return iconosDepositoMap;
	}

	public String getIconosJavascript() {

		StringBuffer javascript = new StringBuffer();
		LinkedHashMap iconos = getIconosDeposito();

		for (Iterator iterator = iconos.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			IconoDeposito icono = (IconoDeposito) iconos.get(key);

			if (icono != null) {
				if (StringUtils.isNotBlank(javascript.toString())) {
					javascript.append(",");
				}

				javascript.append(icono.getJavascript());
			}
		}

		return javascript.toString();
	}

	/**
	 * Obtiene el contenido de un fichero de configuracion
	 * @param key Cadena que contiene la clave del fichero a obtener {@link ConfiguracionArchivoManager}
	 * @return
	 * @throws IOException
	 */
	public String getContenidoFicheroAsString(String key) throws IOException{
		ConfigKeyValue configKeyValue = (ConfigKeyValue) configMap.get(key);
		return FileHelper.getTextFileContent(configKeyValue.getPath());
	}
}
