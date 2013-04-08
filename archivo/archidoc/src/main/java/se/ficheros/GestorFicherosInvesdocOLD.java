package se.ficheros;

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

import se.ficheros.exceptions.GestorFicherosException;
import util.MultiEntityUtil;
import xml.config.ConfiguracionAlmacenamiento;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.MultiEntityConstants;
import common.db.DBUtils;
import common.db.TableDef;
import common.util.ArrayUtils;
import common.util.StringUtils;
import common.util.TypeConverter;

/**
 * Contiene los métodos de tratamiento de ficheros almacenados en InvesDoc
 * 
 */

public class GestorFicherosInvesdocOLD {

	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(GestorFicherosInvesdocOLD.class);

	/** Configuración de la conexión con InvesDoc. */
	protected DbConnectionConfig dbConnectionConfig = null;

	/** Conexión con la bbdd de InvesDoc. */
	protected Connection connection = null;

	/** Tipo de base de datos de InvesDoc **/
	protected int dbEngine = -1;

	/** Definición de la columna identificador de documento **/
	protected DbColumnDef ID = new DbColumnDef("ID", DbDataType.LONG_TEXT,
			false);

	public GestorFicherosInvesdocOLD() {
	}

	/**
	 * Inicializa con los parámetros de configuración.
	 * 
	 * @param params
	 *            Parámetros de configuración.
	 * @throws GestorFicherosException
	 *             si ocurre algún error.
	 */
	public void initialize(Properties params) {
		try {

			String entity = null;
			// Buscar la propiedad entidad
			if ((params != null)
					&& (params.containsKey(MultiEntityConstants.ENTITY_PARAM))) {
				entity = (String) params.get(MultiEntityConstants.ENTITY_PARAM);
			}

			// Obtener el dataSource de almacenamiento
			String dataSource = ""; // ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo().getConfiguracionAlmacenamiento().getDataSourceNameAlmacenamiento();
			if (StringUtils.isNotEmpty(entity))
				dataSource = MultiEntityUtil.composeDsName(dataSource, entity);
			dbConnectionConfig = new DbConnectionConfig(dataSource, null, null);

		} catch (Exception e) {
			logger.error("Error leyendo el nombre del dataSource", e);
			throw new GestorFicherosException(e, this.getClass().getName(),
					"Error leyendo el nombre del dataSource");
		}
	}

	/**
	 * Permite obtener repositorios ECM
	 * 
	 * @return Lista de {@link RepositorioEcmVO}
	 * @throws Exception
	 */
	// public List getRepositoriosEcm() throws Exception {
	//
	// List<IRepositorioEcmVO> listas = new LinkedList<IRepositorioEcmVO>();
	//
	// VolumeListsImpl volLists = new VolumeListsImpl();
	//
	// volLists.setConnectionConfig(dbConnectionConfig);
	//
	// try
	// {
	// // Cargar las listas de volúmenes
	// volLists.load();
	//
	// if (logger.isDebugEnabled())
	// logger.debug("Repositorios ECM:"
	// + Constants.NEWLINE
	// + volLists.toString());
	//
	// // Crear una lista de objetos
	// for (int i = 0; i < volLists.count(); i++)
	// {
	// VolumeList volList = volLists.get(i);
	// listas.add(
	// new RepositorioEcmVO(
	// volList.getId(),
	// volList.getName(),
	// IRepositorioEcmVO.REPOSITORIO_ECM_INVESDOC));
	// }
	// }
	// catch (Exception e)
	// {
	// logger.error("Error al leer los Repositorios ECM", e);
	// }
	//
	// return listas;
	// }

	// public RepositorioEcmVO getListaVolumenes(String idRepEcm) throws
	// Exception {
	//
	// List listas = this.getRepositoriosEcm();
	//
	// RepositorioEcmVO repositorioEcmVO = null;
	//
	// if (listas != null && listas.size() > 0) {
	// Iterator it = listas.iterator();
	// while (it.hasNext()) {
	// RepositorioEcmVO vo = (RepositorioEcmVO)it.next();
	// if (vo.getId().equalsIgnoreCase(idRepEcm))
	// return vo;
	// }
	// }
	//
	// return repositorioEcmVO;
	// }

	/**
	 * Permite obtener un fichero de Invesdoc
	 * 
	 * @param id
	 *            Id del fichero
	 * @return array de bytes con el contenido
	 * @throws Exception
	 *             Si se produce algún error
	 */
	public byte[] retrieveFile(int id) throws Exception {
		return FssBnoFileEx.retrieveFile(dbConnectionConfig, id);
	}

	/**
	 * Permite almacenar un fichero en Invesdoc
	 * 
	 * @param idLista
	 *            Id de la lista en la que se quiere almacenar el fichero
	 * @param ext
	 *            Extensión del fichero
	 * @param contenido
	 *            Contenido del fichero
	 * @return Id del fichero
	 * @throws Exception
	 *             Si se produce algún error
	 */
	public int storeFileInList(int idLista, String ext, byte[] contenido)
			throws Exception {

		// Obtener la configuración de almacenamiento
		ConfiguracionAlmacenamiento configAlmacenamiento = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo()
				.getConfiguracionAlmacenamiento();

		FssFtsInfo ftsInfo = null;
		FssFileInfo fileInfo = new FssFileInfo();
		fileInfo.m_ext = ext;

		// Comprobar si hay que hacer indexado por el motor documental
		if ((configAlmacenamiento != null)
				&& (StringUtils.isNotEmpty(configAlmacenamiento
						.getPlataformaMotorDocumental()))) {

			// Comprobar si la extensión del fichero está entre las extensiones
			// que requieren indexado
			// Si no hay ninguna extensión definida por defecto no se indexa.
			String[] extensiones = configAlmacenamiento
					.getExtensionesArrayMotorDocumental();

			if (ArrayUtils.isNotEmpty(extensiones)
					&& ArrayUtils.contains(extensiones, ext.toUpperCase())) {
				fileInfo.m_flags = FssMdoUtil.FILE_FLAG_FTS;

				ftsInfo = new FssFtsInfo();
				ftsInfo.m_ftsPlatform = configAlmacenamiento
						.getPlataformaIntMotorDocumental();
				ftsInfo.m_ftsRoot = configAlmacenamiento
						.getRutaPlataformaMotorDocumental();
			}

		}

		return FssBnoFileEx.storeFileInList(dbConnectionConfig, idLista,
				fileInfo, ftsInfo, contenido);
	}

	/**
	 * Permite eliminar un fichero de Invesdoc
	 * 
	 * @param idFichero
	 *            Id del fichero
	 * @throws Exception
	 *             Si se produce algún error
	 */
	public void deleteFile(String idFichero) throws Exception {

		int iFileId = TypeConverter.toInt(idFichero, -1);
		if (iFileId > 0)
			FssBnoFileEx.deleteFile(dbConnectionConfig, iFileId);
		DbConnection.close();

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

	public List findFileByContent(String[] idsRepsEcm, String searchString,
			List idsList) throws Exception {

		List filesIds = new ArrayList();
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
							"IDS"), ID), idsRepsEcm));

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
}