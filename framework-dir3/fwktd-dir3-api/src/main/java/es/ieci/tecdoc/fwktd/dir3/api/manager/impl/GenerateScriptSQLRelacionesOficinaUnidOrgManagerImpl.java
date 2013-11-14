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
import es.ieci.tecdoc.fwktd.dir3.api.manager.DatosBasicosRelacionUnidOrgOficinaManager;
import es.ieci.tecdoc.fwktd.dir3.api.manager.GenerateScriptSQLManager;
import es.ieci.tecdoc.fwktd.dir3.api.vo.DatosBasicosRelacionUnidOrgOficinaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.relacion.RelacionUnidOrgOficinaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.relacion.RelacionesUnidOrgOficinaVO;

public class GenerateScriptSQLRelacionesOficinaUnidOrgManagerImpl implements
		GenerateScriptSQLManager {

	private static final Logger logger = LoggerFactory
			.getLogger(GenerateScriptSQLRelacionesOficinaUnidOrgManagerImpl.class);

	protected DatosBasicosRelacionUnidOrgOficinaManager datosBasicosRelacionUnidOrgOficinaManager;

	//Variable que indica el directorio para el fichero que va contener las sentencias SQL
	protected String scriptsFilesDir;

	private static final String VIGENTE = "V";
	private static final String DOBLE_ESCAPE_COMILLA_SIMPLE = "\'\'";
	private static final String ESCAPE_COMILLA_SIMPLE = "\'";

	//SQL
	//SQL Relaciones
	private String RELACION_INSERT = "INSERT INTO DIR_RELAC_UNID_ORG_OFIC (CODIGO_OFICINA, DENOMINACION_OFICINA, CODIGO_UNIDAD_ORGANICA, DENOM_UNIDAD_ORGANICA) VALUES('%1$s','%3$s','%2$s','%4$s');\n";
	private String RELACION_UPDATE = "UPDATE DIR_RELAC_UNID_ORG_OFIC  SET DENOMINACION_OFICINA = '%3$s', DENOM_UNIDAD_ORGANICA = '%4$s' WHERE CODIGO_OFICINA = '%1$s'; AND CODIGO_UNIDAD_ORGANICA = '%2$s'; \n";
	private String RELACION_DELETE = "DELETE FROM DIR_RELAC_UNID_ORG_OFIC WHERE CODIGO_OFICINA = '%1$s'; AND CODIGO_UNIDAD_ORGANICA = '%2$s'; \n";



	public void generateScriptInicializacion(String fileXMLData) {
		//Obtenemos listado de objetos a partir del XML


		RelacionesUnidOrgOficinaVO relacionesDCO = XmlDcoToObject.getInstance().getRelacionesUnidOrgOficinaFromXmlFile(fileXMLData);

		//Creamos el fichero de salida
		String outputFileRelacionesUnidOrgOficina = ScriptFilesHelper.getScriptFileName(
				getScriptsFilesDir(), ScriptFilesHelper.RELACIONES_UNID_ORG_OFIC,
				ScriptFilesHelper.INIT);

		try {
			writeSentencesInFileInit(relacionesDCO, outputFileRelacionesUnidOrgOficina);
		} catch (IOException e) {
			logger.error(
					"Error al generar el script de inicialización de las relaciones entre las unid. orgánicas y las oficinas del DCO", e);
		}
	}

	public void generateScriptActualizacion(String fileXMLDataUpdate) {
		//Obtenemos listado de objetos a partir del XML
		RelacionesUnidOrgOficinaVO relacionesDCO = XmlDcoToObject.getInstance().getRelacionesUnidOrgOficinaFromXmlFile(fileXMLDataUpdate);

		// Creamos el fichero SQL de salida
		String outputFileRelacionesUnidOrgOficina = ScriptFilesHelper.getScriptFileName(
				getScriptsFilesDir(), ScriptFilesHelper.RELACIONES_UNID_ORG_OFIC,
				ScriptFilesHelper.UPDATE);

		try {
			// Escribimos las sentencias en el fichero SQL de SALIDA
			writeSentencesInFileUpdate(relacionesDCO, outputFileRelacionesUnidOrgOficina);
		} catch (IOException e) {
			logger.error(
					"Error al generar el script de actualizacion de Oficinas del DCO", e);
		}
	}

	public List<String> generateSententesSQLUpdate(String fileXMLDataUpdate) {
		//Obtenemos listado de objetos a partir del XML
		RelacionesUnidOrgOficinaVO relacionesDCO = XmlDcoToObject.getInstance().getRelacionesUnidOrgOficinaFromXmlFile(fileXMLDataUpdate);

		Iterator<RelacionUnidOrgOficinaVO> iterator = relacionesDCO.getRelacionesUnidOrgOficinaVO().iterator();

		List<String> stmtList = new ArrayList<String>();
		RelacionUnidOrgOficinaVO relacion;
		while (iterator.hasNext()) {
			relacion = (RelacionUnidOrgOficinaVO) iterator.next();
			//obtenemos y añadimos al listado la sentencia SQL correspondiente para la oficina a tratar
			stmtList.add(getSentenceSQL(relacion));
		}

		return stmtList;
	}

	private void writeSentencesInFileUpdate(RelacionesUnidOrgOficinaVO relacionesDCO,
			String outputFileRelacionesUnidOrgOficina) throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(
				outputFileRelacionesUnidOrgOficina));
		try {
			Iterator<RelacionUnidOrgOficinaVO> iterator = relacionesDCO.getRelacionesUnidOrgOficinaVO().iterator();
			RelacionUnidOrgOficinaVO relacion;
			//recorremos las relaciones
			while (iterator.hasNext()) {
				relacion = (RelacionUnidOrgOficinaVO) iterator.next();
				//escribimos en el fichero la sentencia SQL correspondiente a cada OficinaVO
				writer.write(getSentenceSQL(relacion));
			}
		} finally {
			writer.close();
		}
	}

	private String getSentenceSQL(RelacionUnidOrgOficinaVO relacion) {
		String result;
		// Si la oficina esta en estado VIGENTE realizar modificacion o insercción
		if (VIGENTE.equals(relacion.getEstadoRelacion())) {
			// Buscamos datos sobre la oficina
			DatosBasicosRelacionUnidOrgOficinaVO datosBasicosRelacion = getDatosBasicosRelacionUnidOrgOficinaManager()
					.getRelacionesByOficinaAndUnidad(
							relacion.getCodigoOficina(),
							relacion.getCodigoUnidadOrganica());

			// Si la Oficina ya existe actualizar
			if (datosBasicosRelacion != null) {
				result = getRelacionStmt(relacion, RELACION_UPDATE);
			} else {
				// Realizar insercción de nueva oficina
				result = getRelacionStmt(relacion, RELACION_INSERT);
			}
		} else {
			// Borrar Oficina
			result = getRelacionStmt(relacion,RELACION_DELETE);
		}

		return result;
	}


	private void writeSentencesInFileInit(RelacionesUnidOrgOficinaVO relacionesDCO,
			String outputFileRelacionesUnidOrgOficina) throws IOException {
		Iterator<RelacionUnidOrgOficinaVO> iterator = relacionesDCO.getRelacionesUnidOrgOficinaVO().iterator();

		BufferedWriter writer = new BufferedWriter(new FileWriter(
				outputFileRelacionesUnidOrgOficina));

		try {
			while (iterator.hasNext()) {
				RelacionUnidOrgOficinaVO relacion = iterator.next();
				// Solo escribir el INSERT si la oficina esta en estado Vigente
				if (VIGENTE.equals(relacion.getEstadoRelacion())) {
					writer.write(getRelacionStmt(relacion, RELACION_INSERT));
				}
			}
		} finally {
			writer.close();
		}
	}

	private String getRelacionStmt(RelacionUnidOrgOficinaVO relacion, String stmtSource)
	{
		return String.format(stmtSource,
					relacion.getCodigoOficina(),
					relacion.getCodigoUnidadOrganica(),
					relacion.getDenominacionOficina().replace(ESCAPE_COMILLA_SIMPLE,DOBLE_ESCAPE_COMILLA_SIMPLE),
					relacion.getDenominacionUnidadOrganica().replace(ESCAPE_COMILLA_SIMPLE,DOBLE_ESCAPE_COMILLA_SIMPLE));
	}

	public String getScriptsFilesDir() {
		return scriptsFilesDir;
	}

	public void setScriptsFilesDir(String scriptsFilesDir) {
		this.scriptsFilesDir = scriptsFilesDir;
	}

	public DatosBasicosRelacionUnidOrgOficinaManager getDatosBasicosRelacionUnidOrgOficinaManager() {
		return datosBasicosRelacionUnidOrgOficinaManager;
	}

	public void setDatosBasicosRelacionUnidOrgOficinaManager(
			DatosBasicosRelacionUnidOrgOficinaManager datosBasicosRelacionUnidOrgOficinaManager) {
		this.datosBasicosRelacionUnidOrgOficinaManager = datosBasicosRelacionUnidOrgOficinaManager;
	}



}
