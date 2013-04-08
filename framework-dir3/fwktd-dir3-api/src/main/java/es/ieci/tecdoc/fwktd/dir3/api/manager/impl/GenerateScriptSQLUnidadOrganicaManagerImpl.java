package es.ieci.tecdoc.fwktd.dir3.api.manager.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.dir3.api.helper.ScriptFilesHelper;
import es.ieci.tecdoc.fwktd.dir3.api.helper.XmlDcoToObject;
import es.ieci.tecdoc.fwktd.dir3.api.manager.DatosBasicosUnidadOrganicaManager;
import es.ieci.tecdoc.fwktd.dir3.api.manager.GenerateScriptSQLManager;
import es.ieci.tecdoc.fwktd.dir3.api.vo.DatosBasicosUnidadOrganicaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.unidad.OrganismoVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.unidad.OrganismosVO;

/**
 * Interfaz para la generación de ficheros con el proceso de actualización o
 * inicialización de unidades orgánicas del DCO.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class GenerateScriptSQLUnidadOrganicaManagerImpl implements
		GenerateScriptSQLManager {


	private static final Logger logger = LoggerFactory
			.getLogger(GenerateScriptSQLUnidadOrganicaManagerImpl.class);

	protected DatosBasicosUnidadOrganicaManager datosBasicosUnidadOrganicaManager;
	//Variable que indica el directorio para el fichero que va contener las sentencias SQL
	protected String scriptsFilesDir;

	private static final String VIGENTE = "V";
	private static final String DOBLE_ESCAPE_COMILLA_SIMPLE = "\'\'";
	private static final String ESCAPE_COMILLA_SIMPLE = "\'";

	//SQL Unidades Organicas
	private String ORGANISMO_INSERT = "INSERT INTO DIR_UNIDAD_ORGANICA(CODIGO_UNIDAD_ORGANICA, NOMBRE_UNIDAD_ORGANICA, CODIGO_UNIDAD_SUP_JERARQUICA, DENOM_UNIDAD_SUP_JERARQUICA, CODIGO_EXTERNO_FUENTE) VALUES ('%1$s','%2$s','%3$s','%4$s','%5$s');\n";
	private String ORGANISMO_UPDATE = "UPDATE INTO DIR_UNIDAD_ORGANICA SET NOMBRE_UNIDAD_ORGANICA = '%2$s', CODIGO_UNIDAD_SUP_JERARQUICA = '%3$s', DENOM_UNIDAD_SUP_JERARQUICA = '%4$s', CODIGO_EXTERNO_FUENTE = '%5$s'WHERE CODIGO_UNIDAD_ORGANICA = '%1$s';\n";
	private String ORGANISMO_DELETE = "DELETE FROM DIR_UNIDAD_ORGANICA WHERE CODIGO_UNIDAD_ORGANICA = '%1$s';\n";

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.dir3.api.manager.GenerateScriptSQLManager#generateScriptInicializacion(java.lang.String)
	 */
	public void generateScriptInicializacion(String fileXMLData) {
		//Obtenemos los organismos a partir del fichero XML
		OrganismosVO organismosDCO = XmlDcoToObject.getInstance().getOrganismosFromXmlFile(fileXMLData);

		//Creamos el fichero SQL de Salida
		String outputFileUnidades = ScriptFilesHelper.getScriptFileName(
				getScriptsFilesDir(), ScriptFilesHelper.UNIDADES,
				ScriptFilesHelper.INIT);

		try {
			//Escribimos las sentencias de inicializacion en el fichero de salida
			writeSentencesInFileInit(organismosDCO, outputFileUnidades);
		} catch (IOException e) {
			logger.error(
					"Error al generar el script de inicialización de las Unidades Orgánicas del DCO", e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.dir3.api.manager.GenerateScriptSQLManager#generateScriptActualizacion(java.lang.String)
	 */
	public void generateScriptActualizacion(String fileXMLDataUpdate) {
		//Obtenemos los organismos a partir del fichero XML
		OrganismosVO organismosDCO = XmlDcoToObject.getInstance().getOrganismosFromXmlFile(fileXMLDataUpdate);

		//Creamos el fichero SQL de Salida
		String outputFileUnidades = ScriptFilesHelper.getScriptFileName(
				getScriptsFilesDir(), ScriptFilesHelper.UNIDADES,
				ScriptFilesHelper.INIT);

		try{
			//Escribimos las sentencias SQL correspondientes en el fichero de salida
			writeSentencesInFileUpdate(organismosDCO, outputFileUnidades);
		} catch (IOException e) {
			logger.error("Error al generar el script de las Unidades Orgánicas del DCO",e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.dir3.api.manager.GenerateScriptSQLManager#generateSententesSQLUpdate(java.lang.String)
	 */
	public List<String> generateSententesSQLUpdate(String fileXMLDataUpdate) {
		//Obtenemos los organismos a partir del fichero XML
		OrganismosVO organismosDCO = XmlDcoToObject.getInstance().getOrganismosFromXmlFile(fileXMLDataUpdate);

		//Creamos el fichero SQL de Salida
		Iterator<OrganismoVO> iterator = organismosDCO.getOrganismos().iterator();

		List<String> stmtList = new ArrayList<String>();
		while (iterator.hasNext()) {
			OrganismoVO organismo = (OrganismoVO) iterator.next();
			//Escribimos en el array las sentencias SQL correspondientes
			stmtList.add(getSentenceSQL(organismo));
		}

		// Devolver la lista de sentencias
		return stmtList;
	}

	/**
	 * Metodo que genera la sentencia SQL segun el objeto pasado como parametro
	 * @param organismo - VO con los datos del organismo
	 * @return String - Sentencia SQL, INSERT, UPDATE o DELETE.
	 */
	private String getSentenceSQL(OrganismoVO organismo) {
		String result;
		// Si el organismo es vigente realizar modificacion o insercción
		if (VIGENTE.equals(organismo.getDatosVigencia().getEstado())) {
			//Buscamos datos sobre la unidad organica
			DatosBasicosUnidadOrganicaVO datosBasicosUnidadOrganica = getDatosBasicosUnidadOrganicaManager()
					.getDatosBasicosUnidadOrganicaByCode(
							organismo.getDatosIdentificativos()
									.getCodigoUnidadOrganica());

			// Si el organismo ya existe actualizar
			if (datosBasicosUnidadOrganica != null) {
				result = getOrganismoStmt(organismo, ORGANISMO_UPDATE);
			} else {
				// Realizar insercción de un nuevo organismo
				result = getOrganismoStmt(organismo, ORGANISMO_INSERT);
			}
		} else {
			// Borrar Organismo
			result = getOrganismoStmt(organismo, ORGANISMO_DELETE);
		}

		return result;
	}


	/**
	 * Metodo que escribe en el fichero pasado como parametro las sentencias de inicializacion del sistema para los organismos
	 * @param organismosDCO - Listado de organismo a insertar en el sistema siempre que esten en estado VIGENTE
	 * @param outputFileUnidades - Fichero de salida en el que se escribiran las sentencias SQL
	 * @throws IOException
	 */
	private void writeSentencesInFileInit(OrganismosVO organismosDCO,
			String outputFileUnidades) throws IOException {
		Iterator<OrganismoVO> iterator = organismosDCO.getOrganismos()
				.iterator();

		BufferedWriter writer = new BufferedWriter(new FileWriter(
				outputFileUnidades));

		try {
			OrganismoVO organismo;
			while (iterator.hasNext()) {
				organismo = (OrganismoVO) iterator.next();
				// Solo escribir el INSERT si el organismo esta en estado
				// Vigente
				if (VIGENTE.equals(organismo.getDatosVigencia().getEstado())) {
					writer.write(getOrganismoStmt(organismo, ORGANISMO_INSERT));
				}
			}
		} finally {
			writer.close();
		}
	}

	/**
	 * Método que escribe en el fichero pasado como parametro las sentencias SQL correspondientes a {@link OrganismosVO}
	 * @param organismosDCO - Listado de organismos
	 * @param outputFileUnidades - Fichero de salida con las sentencias SQL
	 * @throws IOException
	 */
	private void writeSentencesInFileUpdate(OrganismosVO organismosDCO,
			String outputFileUnidades) throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileUnidades));
		try{
			OrganismoVO organismo;
			Iterator<OrganismoVO> iterator = organismosDCO.getOrganismos().iterator();
			while (iterator.hasNext()) {
				organismo = (OrganismoVO) iterator.next();
				//Escribimos cada sentencia en el fichero SQL
				writer.write(getSentenceSQL(organismo));
			}
		}finally{
			writer.close();
		}
	}

	/**
	 * Devuelve una sentencia SQL generada a partir de una cadena origen y los datos del organismo
	 * @param organismo Datos del organismo
	 * @param stmtSource Cadena origen con la sentencia SQL a generar
	 * @return Una sentencia SQL a partir de los datos de un Organismo
	 */
	private String getOrganismoStmt(OrganismoVO organismo, String stmtSource)
	{
		return String.format(stmtSource,
				organismo.getDatosIdentificativos().getCodigoUnidadOrganica(),
				organismo.getDatosIdentificativos().getNombreUnidadOrganica().replace(ESCAPE_COMILLA_SIMPLE,DOBLE_ESCAPE_COMILLA_SIMPLE),
				organismo.getDatosDependencia().getCodigoUnidadSuperiorJerarquica(),
				organismo.getDatosDependencia().getDenominacionUnidadSuperiorJerarquica().replace(ESCAPE_COMILLA_SIMPLE,DOBLE_ESCAPE_COMILLA_SIMPLE),
				organismo.getDatosIdentificativos().getCodigoExterno());
	}



	public String getScriptsFilesDir() {
		return scriptsFilesDir;
	}

	public void setScriptsFilesDir(String scriptsFilesDir) {
		this.scriptsFilesDir = scriptsFilesDir;
	}

	public DatosBasicosUnidadOrganicaManager getDatosBasicosUnidadOrganicaManager() {
		return datosBasicosUnidadOrganicaManager;
	}

	public void setDatosBasicosUnidadOrganicaManager(
			DatosBasicosUnidadOrganicaManager datosBasicosUnidadOrganicaManager) {
		this.datosBasicosUnidadOrganicaManager = datosBasicosUnidadOrganicaManager;
	}
}
