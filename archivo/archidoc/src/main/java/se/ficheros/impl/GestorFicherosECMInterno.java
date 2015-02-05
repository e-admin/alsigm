package se.ficheros.impl;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbDataType;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.core.db.DbEngine;
import ieci.tecdoc.sbo.fss.core.FssBnoFileEx;
import ieci.tecdoc.sbo.fss.core.FssDaoFileTbl;
import ieci.tecdoc.sbo.fss.core.FssDaoFtsTbl;
import ieci.tecdoc.sbo.fss.core.FssDaoVolVolTbl;
import ieci.tecdoc.sbo.fss.core.FssFileInfo;
import ieci.tecdoc.sbo.fss.core.FssFtsInfo;
import ieci.tecdoc.sbo.fss.core.FssMdoUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import se.ficheros.IGestorFicheros;
import se.ficheros.exceptions.GestorFicherosException;
import se.repositoriosECM.vo.MotorDocumental;
import se.repositoriosECM.vo.RepositorioEcmInternoVO;
import util.MultiEntityUtil;

import common.Constants;
import common.MultiEntityConstants;
import common.db.DBUtils;
import common.db.TableDef;
import common.util.ArrayUtils;
import common.util.NumberUtils;
import common.util.StringUtils;
import common.util.TypeConverter;

import docelectronicos.vos.DocDocumentoExtVO;
import docelectronicos.vos.IRepositorioEcmVO;

public class GestorFicherosECMInterno implements IGestorFicheros {

	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(GestorFicherosECMInterno.class);

	/** Configuración de la conexión con InvesDoc. */
	protected DbConnectionConfig dbConnectionConfig = null;

	/** Conexión con la bbdd de InvesDoc. */
	protected Connection connection = null;

	/** Tipo de base de datos de InvesDoc **/
	protected int dbEngine = -1;

	/** Definición de la columna identificador de documento **/
	protected DbColumnDef ID = new DbColumnDef("ID", DbDataType.LONG_TEXT,
			false);

	protected RepositorioEcmInternoVO listaVolumenesVO = null;

	public GestorFicherosECMInterno(IRepositorioEcmVO repositorioEcm) {
		this.listaVolumenesVO = (RepositorioEcmInternoVO) repositorioEcm;
	}

	public void initialize(Properties params) {
		try {

			String entity = null;
			// Buscar la propiedad entidad
			if ((params != null)
					&& (params.containsKey(MultiEntityConstants.ENTITY_PARAM))) {
				entity = (String) params.get(MultiEntityConstants.ENTITY_PARAM);
			}

			// Obtener el dataSource de almacenamiento
			String dataSource = listaVolumenesVO.getNombreDataSource();
			if (StringUtils.isNotEmpty(entity))
				dataSource = MultiEntityUtil.composeDsName(dataSource, entity);
			dbConnectionConfig = new DbConnectionConfig(dataSource, null, null);

		} catch (Exception e) {
			logger.error("Error leyendo el nombre del dataSource", e);
			throw new GestorFicherosException(e, this.getClass().getName(),
					"Error leyendo el nombre del dataSource");
		}
	}

	public byte[] retrieveFile(String id) throws Exception {
		return FssBnoFileEx.retrieveFile(dbConnectionConfig, getId(id));
	}

	public String storeFile(DocDocumentoExtVO docDocumentoExtVO)
			throws Exception {

		String ext = docDocumentoExtVO.getExtFich();
		String idListaVolumenes = listaVolumenesVO.getIdReal();

		// Obtener la configuración de almacenamiento
		// ConfiguracionAlmacenamiento configAlmacenamiento =
		// ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo().getConfiguracionAlmacenamiento();

		FssFtsInfo ftsInfo = null;
		FssFileInfo fileInfo = new FssFileInfo();

		fileInfo.m_ext = ext;

		MotorDocumental motorDocumental = listaVolumenesVO.getMotorDocumental();

		// Comprobar si hay que hacer indexado por el motor documental
		if ((motorDocumental != null)
				&& (StringUtils.isNotEmpty(motorDocumental
						.getPlataformaMotorDocumental()))) {

			// Comprobar si la extensión del fichero está entre las extensiones
			// que requieren indexado
			// Si no hay ninguna extensión definida por defecto no se indexa.
			String[] extensiones = motorDocumental
					.getExtensionesArrayMotorDocumental();

			if (ArrayUtils.isNotEmpty(extensiones)
					&& ArrayUtils.contains(extensiones, ext.toUpperCase())) {
				fileInfo.m_flags = FssMdoUtil.FILE_FLAG_FTS;

				ftsInfo = new FssFtsInfo();
				ftsInfo.m_ftsPlatform = motorDocumental
						.getPlataformaIntMotorDocumental();
				ftsInfo.m_ftsRoot = motorDocumental
						.getRutaPlataformaMotorDocumental();
			}

		}

		int idDocumento = FssBnoFileEx.storeFileInList(dbConnectionConfig,
				getId(idListaVolumenes), fileInfo, ftsInfo,
				docDocumentoExtVO.getContenido());
		return "" + idDocumento;
	}

	public void deleteFile(String idFichero) throws Exception {
		int iFileId = TypeConverter.toInt(idFichero, -1);
		if (iFileId > 0) {
			FssBnoFileEx.deleteFile(dbConnectionConfig, iFileId);
		}
	}

	public List<String> findFileByContent(String searchString, List docsIds)
			throws Exception {

		List<String> filesIds = new ArrayList<String>();
		int idEngine = 0;
		ResultSet rs = null;
		String aliasSubquery = "IDS";

		try {
			connection = DbConnection.open(dbConnectionConfig);

			// Obtener el DbEngine
			String databaseProductName = connection.getMetaData()
					.getDatabaseProductName();
			if (databaseProductName.toUpperCase().startsWith(
					(DbEngine.ORACLE_STR.toUpperCase()))
					|| databaseProductName.toUpperCase().equalsIgnoreCase(
							DbEngine.ORACLE_VERSION_8.toUpperCase())) {
				idEngine = DbEngine.ORACLE;
			} else if (databaseProductName.toUpperCase().startsWith(
					DbEngine.SQLSERVER_STR.toUpperCase())) {
				idEngine = DbEngine.SQLSERVER;
			} else if (databaseProductName.toUpperCase().startsWith(
					DbEngine.POSTGRESQL_STR.toUpperCase())) {
				idEngine = DbEngine.POSTGRESQL;
			}

			StringBuffer query = new StringBuffer();

			query.append(" SELECT ").append(FssDaoFtsTbl.getIdColName(false));
			query.append(" FROM ").append(
					getSqlCondition(idEngine, searchString, aliasSubquery));
			query.append(" WHERE ").append(
					DBUtils.generateInTokenField(new DbColumnDef(new TableDef(
							"IDS"), ID), listaVolumenesVO.getIdReal()));

			if (logger.isDebugEnabled())
				logger.debug("Query: " + query.toString());

			Statement statement = connection.createStatement();
			rs = statement.executeQuery(query.toString());

			if (rs != null) {
				while (rs.next()) {
					String id = rs.getString("ID");
					filesIds.add(id);
				}
			}
		} catch (Exception e) {
			logger.error("Error al buscar en el contenido de ficheros la cadena: "
					+ searchString);
			throw new GestorFicherosException(
					"Error al buscar en el contenido de ficheros la cadena: "
							+ searchString);
		}

		return filesIds;
	}

	private int getId(String id) {
		if (NumberUtils.isInteger(id)) {
			return Integer.parseInt(id);
		}
		return -1;
	}

	/**
	 * Devuelve la condición sql que representa la condición de búsqueda para un
	 * determinado campo
	 *
	 * @param dbEngine
	 *            tipo de base de datos
	 * @param searchString
	 *            cadena de búsqueda
	 * @param aliasSubquery
	 *            cadena que representa el alias que es necesario usar para la
	 *            union compuesta
	 * @return condición sql que representa la condición de búsqueda para un
	 *         determinado campo
	 * @throws Exception
	 *             si se intenta hace búsqueda documental en un gestor
	 *             documental que no sea OTEXT (Oracle Text)
	 */
	private String getSqlCondition(int dbEngine, String searchString,
			String aliasSubquery) throws Exception {
		String cond = "";

		if (dbEngine == DbEngine.ORACLE)
			cond = getOracleSqlConditionByFTS(searchString);
		else if (dbEngine == DbEngine.SQLSERVER)
			cond = getSqlServerConditionByFTS(searchString);
		else {
			logger.error("El motor de base de datos es incorrecto.");
			throw new GestorFicherosException(
					" El motor de base de datos es incorrecto.");
		}

		cond = cond + Constants.STRING_SPACE + aliasSubquery;

		return cond;
	}

	private String getOracleSqlConditionByFTS(String searchString)
			throws Exception {
		StringBuffer cond = new StringBuffer();

		String ftsTblName = FssDaoFtsTbl.getTblName();
		String ftsArchIdColName = FssDaoFtsTbl.getExtId1ColName(true);
		String ftsPathColName = FssDaoFtsTbl.getPathColName(true);
		String ftsIdColName = FssDaoFtsTbl.getIdColName(true);
		String hdrTblName = FssDaoFileTbl.TN;
		String volVolTblName = FssDaoVolVolTbl.TN;
		String hdrIdColName = FssDaoFileTbl.CD_ID.getName();
		String locColName = hdrTblName + "." + FssDaoFileTbl.CD_LOC.getName();
		String locIdColName = volVolTblName + "."
				+ FssDaoVolVolTbl.CD_LOCID.getName();

		cond.append('(');

		// Obtenemos primero los ficheros en la tabla IVOLFILEFTS
		cond.append("SELECT ").append(ftsIdColName).append(" ID");
		cond.append(" FROM ").append(ftsTblName).append(" WHERE ");
		cond.append(" CONTAINS (");
		cond.append(ftsPathColName).append(", '" + searchString + "',1)>0 ");
		cond.append(" AND ").append(ftsArchIdColName).append(" = 0 ");

		// Y después los de la join de las tablas IVOLFILEHDR e IVOLVOLTBL
		cond.append(" UNION ");

		hdrIdColName = hdrTblName + '.' + FssDaoFileTbl.CD_ID.getName();
		String ftsFileValColName = volVolTblName + '.'
				+ FssDaoVolVolTbl.CD_FILEVAL.getName();

		cond.append("SELECT ").append(hdrIdColName).append(" ID");
		cond.append(" FROM ").append(hdrTblName).append(", ")
				.append(volVolTblName);
		cond.append(" WHERE ");
		cond.append(" CONTAINS (");
		cond.append(ftsFileValColName).append(", '" + searchString + "',1)>0 ");
		cond.append(" AND ");
		cond.append(locColName).append("=").append(locIdColName);

		cond.append(')');

		return cond.toString();
	}

	private String getSqlServerConditionByFTS(String searchString)
			throws Exception {
		StringBuffer cond = new StringBuffer();
		String ftsTblName = FssDaoFtsTbl.getTblName();
		String ftsPathColName = FssDaoFtsTbl.getPathColName(false);
		String ftsIdColName = FssDaoFtsTbl.getIdColName(false);
		String hdrTblName = FssDaoFileTbl.TN;
		String volVolTblName = FssDaoVolVolTbl.TN;
		String hdrIdColName = FssDaoFileTbl.CD_ID.getName();
		String locColName = hdrTblName + "." + FssDaoFileTbl.CD_LOC.getName();
		String locIdColName = volVolTblName + "."
				+ FssDaoVolVolTbl.CD_LOCID.getName();

		cond.append('(');

		// Obtenemos primero los ficheros en la tabla IVOLFILEFTS
		cond.append("SELECT W.").append(ftsIdColName).append(" ID ");
		cond.append(" FROM OPENQUERY(FILESYSTEM,'SELECT PATH FROM SCOPE() WHERE CONTAINS (CONTENTS,''");
		cond.append(searchString);
		cond.append("'')') as Q, ").append(ftsTblName)
				.append(" as W WHERE Q.PATH = W.").append(ftsPathColName);

		// Y después los de la tabla IVOLVOLTBL
		cond.append(" UNION ");

		hdrIdColName = hdrTblName + '.' + FssDaoFileTbl.CD_ID.getName();
		String ftsFileValColName = volVolTblName + '.'
				+ FssDaoVolVolTbl.CD_FILEVAL.getName();

		cond.append("SELECT ").append(hdrIdColName).append(" ID");
		cond.append(" FROM ").append(hdrTblName).append(", ")
				.append(volVolTblName);
		cond.append(" WHERE CONTAINS(").append(ftsFileValColName).append(",'");
		cond.append(searchString);
		cond.append("')");
		cond.append(" AND ");
		cond.append(locColName).append("=").append(locIdColName);

		cond.append(')');

		return cond.toString();
	}

	protected int getIdDocumento(String idDocumento) {
		if (NumberUtils.isInteger(idDocumento)) {
			return Integer.parseInt(idDocumento);
		}
		return -1;
	}
}
