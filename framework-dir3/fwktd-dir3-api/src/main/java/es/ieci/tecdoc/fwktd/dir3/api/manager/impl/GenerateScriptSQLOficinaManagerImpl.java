package es.ieci.tecdoc.fwktd.dir3.api.manager.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.dir3.api.helper.OficinaHelper;
import es.ieci.tecdoc.fwktd.dir3.api.helper.ScriptFilesHelper;
import es.ieci.tecdoc.fwktd.dir3.api.helper.XmlDcoToObject;
import es.ieci.tecdoc.fwktd.dir3.api.manager.DatosBasicosOficinaManager;
import es.ieci.tecdoc.fwktd.dir3.api.manager.GenerateScriptSQLManager;
import es.ieci.tecdoc.fwktd.dir3.api.vo.DatosBasicosOficinaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinasVO;
/**
 * Interfaz para la generación de ficheros con el proceso de actualización o inicialización de Oficinas del DCO.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class GenerateScriptSQLOficinaManagerImpl implements
		GenerateScriptSQLManager {

	private static final Logger logger = LoggerFactory
			.getLogger(GenerateScriptSQLOficinaManagerImpl.class);

	protected DatosBasicosOficinaManager datosBasicosOficinaManager;
	//Variable que indica el directorio para el fichero que va contener las sentencias SQL
	protected String scriptsFilesDir;

	private static final String VIGENTE = "V";
	private static final String DOBLE_ESCAPE_COMILLA_SIMPLE = "\'\'";
	private static final String ESCAPE_COMILLA_SIMPLE = "\'";

	//SQL Oficinas
	private String OFICINA_INSERT = "INSERT INTO DIR_OFICINA(CODIGO_OFICINA, DENOMINACION_OFICINA, CODIGO_UNIDAD_RESPONSABLE, EXTERNO_FUENTE, DIRECCION, LOCALIDAD, PROVINCIA) VALUES('%1$s','%2$s','%3$s','%4$s','%5$s','%6$s','%7$s');\n";
	private String OFICINA_UPDATE = "UPDATE DIR_OFICINA SET DENOMINACION_OFICINA = '%2$s', CODIGO_UNIDAD_RESPONSABLE = '%3$s', EXTERNO_FUENTE = '%4$s', DIRECCION = '%5$s', LOCALIDAD = '%6$s', PROVINCIA = '%7$s' WHERE CODIGO_OFICINA = '%1$s';\n";
	private String OFICINA_DELETE = "DELETE FROM DIR_OFICINA WHERE CODIGO_OFICINA = '%1$s';\n";

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.dir3.api.manager.GenerateScriptSQLManager#generateScriptInicializacion(java.lang.String)
	 */
	public void generateScriptInicializacion(String fileXMLData) {
		//Obtenemos listado de objetos a partir del XML
		OficinasVO oficinasDCO = XmlDcoToObject.getInstance()
				.getOficinasFromXmlFile(fileXMLData);

		//Creamos el fichero de salida
		String outputFileOficinas = ScriptFilesHelper.getScriptFileName(
				getScriptsFilesDir(), ScriptFilesHelper.OFICINAS,
				ScriptFilesHelper.INIT);

		try {
			writeSentencesInFileInit(oficinasDCO, outputFileOficinas);
		} catch (IOException e) {
			logger.error(
					"Error al generar el script de inicialización de Oficinas del DCO", e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.dir3.api.manager.GenerateScriptSQLManager#generateScriptActualizacion(java.lang.String)
	 */
	public void generateScriptActualizacion(String fileXMLDataUpdate) {
		//Obtenemos listado de objetos a partir del XML
		OficinasVO oficinasDCO = XmlDcoToObject.getInstance()
				.getOficinasFromXmlFile(fileXMLDataUpdate);

		// Creamos el fichero SQL de salida
		String outputFileOficinas = ScriptFilesHelper.getScriptFileName(
				getScriptsFilesDir(), ScriptFilesHelper.OFICINAS,
				ScriptFilesHelper.UPDATE);

		try {
			// Escribimos las sentencias en el fichero SQL de SALIDA
			writeSentencesInFileUpdate(oficinasDCO, outputFileOficinas);
		} catch (IOException e) {
			logger.error(
					"Error al generar el script de actualizacion de Oficinas del DCO", e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.dir3.api.manager.GenerateScriptSQLManager#generateSententesSQLUpdate(java.lang.String)
	 */
	public List<String> generateSententesSQLUpdate(String fileXMLDataUpdate) {
		//Obtenemos listado de objetos a partir del XML
		OficinasVO oficinasDCO = XmlDcoToObject.getInstance()
				.getOficinasFromXmlFile(fileXMLDataUpdate);

		Iterator<OficinaVO> iterator = oficinasDCO.getOficinas().iterator();

		List<String> stmtList = new ArrayList<String>();
		OficinaVO oficina;
		while (iterator.hasNext()) {
			oficina = (OficinaVO) iterator.next();
			//obtenemos y añadimos al listado la sentencia SQL correspondiente para la oficina a tratar
			stmtList.add(getSentenceSQL(oficina));
		}

		return stmtList;
	}

	/**
	 * Metodo que escribe en el fichero pasado como parametro las sentencias de actualizacion segun el array {@link OficinasVO}
	 *
	 * @param oficinasDCO - Listado de objetos a actualizar
	 * @param outputFileOficinas - Fichero de salida con las sentencias SQL
	 * @throws IOException
	 */
	private void writeSentencesInFileUpdate(OficinasVO oficinasDCO,
			String outputFileOficinas) throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(
				outputFileOficinas));
		try {
			Iterator<OficinaVO> iterator = oficinasDCO.getOficinas().iterator();
			OficinaVO oficina;
			//recorremos las oficinas
			while (iterator.hasNext()) {
				oficina = (OficinaVO) iterator.next();
				//escribimos en el fichero la sentencia SQL correspondiente a cada OficinaVO
				writer.write(getSentenceSQL(oficina));
			}
		} finally {
			writer.close();
		}
	}

	/**
	 * Metodo que obtiene la sentencia a realizar segun el objeto que recibe
	 * @param oficina - VO de Oficina
	 * @return String - Cadena con la sentencia INSERT, UPDATE o DELETE segun el objeto
	 */
	private String getSentenceSQL(OficinaVO oficina) {
		String result;
		// Si la oficina esta en estado VIGENTE realizar modificacion o insercción
		if (VIGENTE.equals(oficina.getDatosVigencia().getEstadoOficina())) {
			// Buscamos datos sobre la oficina
			DatosBasicosOficinaVO datosBasicosOficina = getDatosBasicosOficinaManager()
					.getDatosBasicosOficinaByCode(
							oficina.getDatosIdentificativos()
									.getCodigoOficina());

			// Si la Oficina ya existe actualizar
			if (datosBasicosOficina != null) {
				result = getOficinaStmt(oficina, OFICINA_UPDATE);
			} else {
				// Realizar insercción de nueva oficina
				result = getOficinaStmt(oficina, OFICINA_INSERT);
			}
		} else {
			// Borrar Oficina
			result = getOficinaStmt(oficina, OFICINA_DELETE);
		}

		return result;
	}

	/**
	 * Método que escribe en el fichero SQL de salida las sentencias INSERT para inicializar el sistema
	 * @param oficinasDCO - Listado de objetos que se insertaran en el sistema, siempre que esten VIGENTES
	 * @param outputFileOficinas - Fichero de Salida con las sentencias SQL
	 * @throws IOException
	 */
	private void writeSentencesInFileInit(OficinasVO oficinasDCO,
			String outputFileOficinas) throws IOException {
		Iterator<OficinaVO> iterator = oficinasDCO.getOficinas().iterator();

		BufferedWriter writer = new BufferedWriter(new FileWriter(
				outputFileOficinas));

		try {
			while (iterator.hasNext()) {
				OficinaVO oficina = (OficinaVO) iterator.next();
				// Solo escribir el INSERT si la oficina esta en estado Vigente
				if (VIGENTE.equals(oficina.getDatosVigencia()
						.getEstadoOficina())) {
					writer.write(getOficinaStmt(oficina, OFICINA_INSERT));
				}
			}
		} finally {
			writer.close();
		}
	}

	/**
	 * Devuelve una sentencia SQL generada a partir de una cadena origen y los datos de la oficina
	 * @param oficina Datos de la oficina
	 * @param stmtSource Cadena origen con la sentencia SQL a generar
	 * @return Una sentencia SQL a partir de los datos de una oficina
	 */
	private String getOficinaStmt(OficinaVO oficina, String stmtSource)
	{
		return String.format(stmtSource,
					oficina.getDatosIdentificativos().getCodigoOficina(),
					oficina.getDatosIdentificativos().getDenominacionOficina().replace(ESCAPE_COMILLA_SIMPLE,DOBLE_ESCAPE_COMILLA_SIMPLE),
					oficina.getDatosDependenciaJerarquica().getCodigoUnidadResponsable(),
					oficina.getDatosIdentificativos().getExternoFuente(),
					OficinaHelper.getOficinaAddress(oficina.getDatosLocalizacion()).replace(ESCAPE_COMILLA_SIMPLE,DOBLE_ESCAPE_COMILLA_SIMPLE),
					oficina.getDatosLocalizacion().getLocalidad().replace(ESCAPE_COMILLA_SIMPLE,DOBLE_ESCAPE_COMILLA_SIMPLE),
					oficina.getDatosLocalizacion().getProvincia().replace(ESCAPE_COMILLA_SIMPLE,DOBLE_ESCAPE_COMILLA_SIMPLE));
	}

	public DatosBasicosOficinaManager getDatosBasicosOficinaManager() {
		return datosBasicosOficinaManager;
	}

	public void setDatosBasicosOficinaManager(
			DatosBasicosOficinaManager datosBasicosOficinaManager) {
		this.datosBasicosOficinaManager = datosBasicosOficinaManager;
	}

	public String getScriptsFilesDir() {
		return scriptsFilesDir;
	}

	public void setScriptsFilesDir(String scriptsFilesDir) {
		this.scriptsFilesDir = scriptsFilesDir;
	}
}
