package se.instituciones.archivo.invesdoc.idoc;

import ieci.core.db.DbEngine;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbConnectionConfig;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import se.autenticacion.AuthenticationConnectorException;
import se.autenticacion.idoc.InvesDocConnector;
import se.instituciones.InfoOrgano;
import se.instituciones.archivo.invesdoc.vo.OrganoVO;
import util.MultiEntityUtil;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.MultiEntityConstants;
import common.exceptions.DBException;
import common.exceptions.UncheckedArchivoException;
import common.util.StringUtils;

/**
 * Implementación del interfaz InvesDocOrganizationConnector para la obtención
 * de organizaciones contra InvesDoc.
 */
public class InvesDocOrganizationConnector {

	public static final String TIPO_DEPARTAMENTO = "2";
	public static final String SEPARADOR_TIPOS = "-";

	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(InvesDocOrganizationConnector.class);

	/** Configuración de la conexión con InvesDoc. */
	DbConnectionConfig dbConnectionConfig = null;

	/** Conexión con la bbdd de InvesDoc. */
	Connection connection = null;

	protected static String ORG_SF_COL_ID = "id";
	protected static String ORG_SF_COL_NOMBRE = "name";
	protected static String ORG_SF_COL_ID_PADRE = "parentid";
	protected static String ORG_SF_COL_DESCRIPCION = "remarks";

	protected static String USU_COL_ID = "id";

	public static final short IDENTIFICADOR_ORGANO = 1;
	public static final short CODIGO_ORGANO = 2;

	public static final String TAG_ORGANO = "<ORGANO>";
	public static final String TAG_FIN_ORGANO = "</ORGANO>";

	public static final String TAG_INFO = "<INFO>";

	/**
	 * Constructor.
	 */
	public InvesDocOrganizationConnector() {
	}

	/**
	 * Inicializa con los parámetros de configuración.
	 *
	 * @param params
	 *            Parámetros de configuración.
	 * @throws AuthenticationConnectorException
	 *             si ocurre algún error.
	 */
	public void initialize(Properties params) // throws
												// AuthenticationConnectorException
	{
		try {
			String entity = null;
			// Buscar la propiedad entidad
			if ((params != null)
					&& (params.containsKey(MultiEntityConstants.ENTITY_PARAM))) {
				entity = (String) params.get(MultiEntityConstants.ENTITY_PARAM);
			}

			// Obtener el dataSource de autenticación
			String dataSource = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo().getConfiguracionGeneral()
					.getDataSourceNameAuth();
			if (entity != null)
				dataSource = MultiEntityUtil.composeDsName(dataSource, entity);
			dbConnectionConfig = new DbConnectionConfig(dataSource, null, null);

		} catch (Exception e) {
			logger.error("Error leyendo el nombre del dataSource", e);
			throw new OrganizationConnectorException(e, this.getClass()
					.getName(), "Error leyendo el nombre del dataSource");
		}
	}

	/**
	 * Obtiene la informacion acerca del usuario.
	 *
	 * @param idUser
	 *            Identificador del usuario.
	 * @return Información del usuario.
	 */
	public InfoOrgano getOrganoDeUsuario(String idUser) {
		ResultSet rs = null;
		OrganoVO organo = null;

		try {

			connection = DbConnection.open(dbConnectionConfig);

			try {
				StringBuffer query = new StringBuffer()
						.append("SELECT D.id, D.name, D.parentid, D.remarks from IUSERUSERHDR U INNER JOIN IUSERDEPTHDR D ON U.deptid = D.id AND U.id = ")
						.append(idUser);

				if (logger.isDebugEnabled())
					logger.debug("Query: " + query.toString());

				Statement statement = connection.createStatement();
				rs = statement.executeQuery(query.toString());

				ArrayList listaOrganos = dbToOrganoVOList(rs);

				if ((listaOrganos != null) && (!listaOrganos.isEmpty()))
					organo = (OrganoVO) listaOrganos.get(0);

			} catch (Exception e) {
				logger.error("Error al leer el \u00F3rgano del usuario con id "
						+ idUser, e);
				throw new DBException(e.toString());
			} finally {
				closeResultSet(rs);
			}
			DbConnection.close();

		} catch (Exception e) {

			try {
				DbConnection.ensureClose(e);
			} catch (Exception e1) {

			}

		}
		return organo;
	}

	private ArrayList dbToOrganoVOList(ResultSet rs) throws Exception {
		ArrayList ret = new ArrayList();
		if (rs != null) {
			while (rs.next()) {
				ret.add(getOrganoVO(rs.getString(ORG_SF_COL_ID),
						rs.getString(ORG_SF_COL_NOMBRE),
						rs.getString(ORG_SF_COL_ID_PADRE),
						rs.getString(ORG_SF_COL_DESCRIPCION)));
			}
		}

		return ret;
	}

	private ArrayList dbToOrganoVOList(ResultSet rs, int numNiveles,
			boolean addFirst, boolean addRootNode) throws Exception {
		ArrayList ret = new ArrayList();
		OrganoVO organo = null;

		if (rs != null) {
			int i = 0;
			while (rs.next()) {

				organo = getOrganoVO(rs.getString(ORG_SF_COL_ID),
						rs.getString(ORG_SF_COL_NOMBRE),
						rs.getString(ORG_SF_COL_ID_PADRE),
						rs.getString(ORG_SF_COL_DESCRIPCION));

				if (organo.getNivel() <= numNiveles || numNiveles == 0) {
					if ((i != 0) || (i == 0 && addRootNode)) {
						if (addFirst)
							ret.add(0, organo);
						else
							ret.add(organo);
					}
				}

				i++;
			}
		}

		return ret;
	}

	public List getHijosDeOrgano(String idOrgPadre) {

		List organos = new ArrayList();
		ResultSet rs = null;

		try {

			connection = DbConnection.open(dbConnectionConfig);

			if (logger.isDebugEnabled())
				logger.debug("Llamada a recuperarHijosDeOrgano(" + idOrgPadre
						+ ")");

			try {
				StringBuffer query = new StringBuffer()
						.append("select D.ID, D.name, D.parentid, D.remarks from IUSERDEPTHDR D WHERE D.parentid = ")
						.append(idOrgPadre != null ? idOrgPadre : "0");

				if (logger.isDebugEnabled())
					logger.debug("Query: " + query.toString());

				Statement statement = connection.createStatement();
				rs = statement.executeQuery(query.toString());

				organos = dbToOrganoVOList(rs);
			} catch (Exception e) {
				logger.error("Error al leer los hijos del \u00F3rgano con id "
						+ idOrgPadre, e);
				throw new DBException(e.toString());
			} finally {
				closeResultSet(rs);
			}

			DbConnection.close();

		} catch (Exception e) {

			try {
				DbConnection.ensureClose(e);
			} catch (Exception e1) {

			}

		}

		return organos;

	}

	public List getInstitucionesProductoras() {
		List instituciones = new ArrayList();
		// OrganoVO institucion = null;
		ResultSet rs = null;

		try {
			connection = DbConnection.open(dbConnectionConfig);

			if (logger.isDebugEnabled())
				logger.debug("Llamada a recuperarInstitucionesProductoras()");

			try {
				StringBuffer query = new StringBuffer()
						.append("select D.ID, D.name, D.parentid, D.remarks from IUSERDEPTHDR D WHERE D.remarks like '%<INSTITUCION>S</INSTITUCION>%' ORDER BY D.name");

				if (logger.isDebugEnabled())
					logger.debug("Query: " + query.toString());

				Statement statement = connection.createStatement();
				rs = statement.executeQuery(query.toString());

				instituciones = dbToOrganoVOList(rs);

			} catch (Exception e) {
				logger.error("Error al leer las instituciones productoras", e);
				throw new DBException(e.toString());
			} finally {
				closeResultSet(rs);
			}
			DbConnection.close();

		} catch (Exception e) {

			try {
				DbConnection.ensureClose(e);
			} catch (Exception e1) {

			}

		}

		return instituciones;
	}

	public OrganoVO recuperarOrgano(short tipoAtrib, String valorAtrib) {
		OrganoVO organo = null;
		ResultSet rs = null;
		try {

			connection = DbConnection.open(dbConnectionConfig);

			if (logger.isDebugEnabled())
				logger.debug("Llamada a recuperarOrgano(" + tipoAtrib + ", "
						+ valorAtrib + ")");

			try {
				StringBuffer query = new StringBuffer()
						.append("select D.ID, D.name, D.parentid, D.remarks from IUSERDEPTHDR D WHERE ");

				if (tipoAtrib == IDENTIFICADOR_ORGANO) {
					query.append("D.").append(ORG_SF_COL_ID).append("=")
							.append(getId(valorAtrib)).append("");
				} else if (tipoAtrib == CODIGO_ORGANO) {
					query.append("D.").append(ORG_SF_COL_DESCRIPCION)
							.append(" LIKE '%<CODIGO>").append(valorAtrib)
							.append("</CODIGO>%'");
				} else {
					throw new IllegalArgumentException("Tipo de atributo ["
							+ tipoAtrib + "] inv\u00E1lido");
				}

				if (logger.isDebugEnabled()) {
					logger.debug("Query: " + query.toString());
				}

				Statement statement = connection.createStatement();
				rs = statement.executeQuery(query.toString());

				ArrayList listaOrganos = dbToOrganoVOList(rs);
				organo = (OrganoVO) listaOrganos.get(0);

			} catch (Exception e) {
				logger.error("Error al leer el \u00F3rgano: " + tipoAtrib + "="
						+ valorAtrib, e);
				throw new DBException(e.toString());
			} finally {
				closeResultSet(rs);
			}
			DbConnection.close();

		} catch (Exception e) {

			try {
				DbConnection.ensureClose(e);
			} catch (Exception e1) {

			}

		}
		return organo;

	}

	public static int getId(String valorAtrib){
		int id= -1;

		try{
			id = Integer.parseInt(valorAtrib,10);
		}catch (NumberFormatException e) {
			String[] valores = valorAtrib.split(SEPARADOR_TIPOS);

			if (valores.length == 2 && TIPO_DEPARTAMENTO.equals(valores[0])) {
				try {
					id = Integer.parseInt(valores[1]);
				} catch (NumberFormatException nfe) {
					logger.error("No se ha podido convertir el id de órgano a numérico: "
							+ valores[1]);
				}
			}

		}

		return id;
	}


	public List recuperarOrganos(String param) {
		List organos = new ArrayList();
		ResultSet rs = null;

		try {
			connection = DbConnection.open(dbConnectionConfig);

			if (logger.isDebugEnabled())
				logger.debug("Llamada a recuperarOrganos(" + param + ")");

			if (param == null)
				param = "";

			try {
				StringBuffer query = new StringBuffer()
						.append("select D.ID, D.name, D.parentid, D.remarks from IUSERDEPTHDR D WHERE UPPER(D.name) like UPPER('%")
						.append(param).append("%')");

				if (logger.isDebugEnabled())
					logger.debug("Query: " + query.toString());

				Statement statement = connection.createStatement();
				rs = statement.executeQuery(query.toString());

				organos = dbToOrganoVOList(rs);
			} catch (Exception e) {
				logger.error("Error al leer los \u00F3rganos que contengan: "
						+ param, e);
				throw new DBException(e.toString());
			} finally {
				closeResultSet(rs);
			}
			DbConnection.close();

		} catch (Exception e) {

			try {
				DbConnection.ensureClose(e);
			} catch (Exception e1) {

			}

		}
		return organos;
	}

	public List recuperarOrganosAntecesores(String idOrg, int numNiveles) {
		List organos = new ArrayList();
		ResultSet rs = null;

		try {
			connection = DbConnection.open(dbConnectionConfig);

			if (logger.isDebugEnabled())
				logger.debug("Llamada a recuperarOrganosAntecesores(" + idOrg
						+ ", " + numNiveles + ")");

			if (idOrg == null)
				return organos;

			// Variable para indicar si se añaden organos al principio o al
			// final de la lista
			boolean addFirst = true;

			// Variable para indicar si se añade el primer registro a la lista o
			// no
			boolean addRootNode = true;
			try {

				String dbVendor = connection.getMetaData()
						.getDatabaseProductName();
				StringBuffer query = new StringBuffer();

				if (dbVendor.equalsIgnoreCase(DbEngine.db2DatabaseName)
						|| dbVendor
								.equalsIgnoreCase(DbEngine.sqlserverDatabaseName)) {

					query.append("WITH n(id,name, parentid, remarks) AS ")
							.append("(SELECT id,name, parentid, remarks ")
							.append("FROM IUSERDEPTHDR ")
							.append("WHERE ID = ")
							.append(idOrg)
							.append(" UNION ALL ")
							.append("SELECT nplus1.id,nplus1.name, nplus1.parentid, nplus1.remarks ")
							.append("FROM IUSERDEPTHDR as nplus1, n ")
							.append("WHERE n.parentid = nplus1.id) ")
							.append("SELECT id,name, parentid, remarks FROM n WHERE ID<>")
							.append(idOrg);
				} else if (dbVendor
						.equalsIgnoreCase(DbEngine.oracleDatabaseName)) {
					// En Oracle no hay que añadir el nodo raíz ya que la
					// consulta lo devuelve
					addRootNode = false;
					query = new StringBuffer()
							.append("select id, name, parentid, remarks from IUSERDEPTHDR ")
							.append("connect by PRIOR parentid = id ")
							.append("START WITH id=" + idOrg)
							.append(" order by level");
				} else {
					query = new StringBuffer()
							.append("select id, name, parentid, remarks from connectby('IUSERDEPTHDR', 'parentid', 'id', '")
							.append(idOrg)
							.append("', ")
							.append(numNiveles)
							.append(") AS t(keyid integer, levelparentid integer, level int) left join IUSERDEPTHDR on id=keyid ")
							.append("where id <> ").append(idOrg)
							.append(" order by level");
				}

				if (logger.isDebugEnabled())
					logger.debug("Query: " + query.toString());

				Statement statement = connection.createStatement();
				rs = statement.executeQuery(query.toString());

				organos = dbToOrganoVOList(rs, numNiveles, addFirst,
						addRootNode);

			} catch (Exception e) {
				logger.error(
						"Error al leer los \u00F3rganos antecesores del \u00F3rgano con id "
								+ idOrg, e);
				throw new DBException(e.toString());
			} finally {
				closeResultSet(rs);
			}
			DbConnection.close();

		} catch (Exception e) {

			try {
				DbConnection.ensureClose(e);
			} catch (Exception e1) {

			}

		}
		return organos;
	}

	public List recuperarOrganosDependientes(String idOrg, int numNiveles) {
		List organos = new ArrayList();
		ResultSet rs = null;

		try {
			connection = DbConnection.open(dbConnectionConfig);

			if (logger.isDebugEnabled())
				logger.debug("Llamada a recuperarOrganosAntecesores(" + idOrg
						+ ", " + numNiveles + ")");

			if (idOrg == null)
				return organos;

			try {

				String dbVendor = connection.getMetaData()
						.getDatabaseProductName();
				StringBuffer query = new StringBuffer();

				// Variable para indicar si se añaden organos al principio o al
				// final de la lista
				boolean addFirst = false;

				// Variable para indicar si se añade el primer registro a la
				// lista o no
				boolean addRootNode = true;

				if (dbVendor.equalsIgnoreCase(DbEngine.db2DatabaseName)
						|| dbVendor
								.equalsIgnoreCase(DbEngine.sqlserverDatabaseName)) {

					query.append("WITH n(id,name, parentid, remarks) AS ")
							.append("(SELECT id,name, parentid, remarks ")
							.append("FROM IUSERDEPTHDR ")
							.append("WHERE ID = ")
							.append(idOrg)
							.append(" UNION ALL ")
							.append("SELECT nplus1.id,nplus1.name, nplus1.parentid, nplus1.remarks ")
							.append("FROM IUSERDEPTHDR as nplus1, n ")
							.append("WHERE n.id = nplus1.parentid) ")
							.append("SELECT id,name, parentid, remarks FROM n WHERE ID<>")
							.append(idOrg);
				} else if (dbVendor
						.equalsIgnoreCase(DbEngine.oracleDatabaseName)) {
					// En Oracle no hay que añadir el nodo raíz ya que la
					// consulta lo devuelve
					addRootNode = false;
					query = new StringBuffer()
							.append("select id, name, parentid, remarks from IUSERDEPTHDR ")
							.append("connect by PRIOR id = parentid ")
							.append("START WITH id=" + idOrg)
							.append(" order by level");
				} else {
					query.append(
							"select id, name, parentid, remarks from connectby('IUSERDEPTHDR', 'id', 'parentid', '")
							.append(idOrg)
							.append("', ")
							.append(numNiveles)
							.append(") AS t(keyid integer, levelparentid integer, level int) left join IUSERDEPTHDR on id=keyid ")
							.append("where id <> ").append(idOrg)
							.append(" order by level");
				}

				if (logger.isDebugEnabled())
					logger.debug("Query: " + query.toString());

				Statement statement = connection.createStatement();
				rs = statement.executeQuery(query.toString());

				organos = dbToOrganoVOList(rs, numNiveles, addFirst,
						addRootNode);

			} catch (Exception e) {
				logger.error(
						"Error al leer los \u00F3rganos antecesores del \u00F3rgano con id "
								+ idOrg, e);
				throw new DBException(e.toString());
			} finally {
				closeResultSet(rs);
			}
			DbConnection.close();

		} catch (Exception e) {

			try {
				DbConnection.ensureClose(e);
			} catch (Exception e1) {

			}

		}

		return organos;
	}

	public List recuperarUsuariosDeOrganos(String[] idOrgs) {
		List usuarios = new ArrayList();
		String idUsuario;
		ResultSet rs = null;

		try {
			connection = DbConnection.open(dbConnectionConfig);

			if (logger.isDebugEnabled())
				logger.debug("Llamada a recuperarUsuariosDeOrganos("
						+ join(idOrgs, ",") + ")");

			if (isEmpty(idOrgs))
				return usuarios;

			try {
				StringBuffer query = new StringBuffer()
						.append("select U.ID from IUSERDEPTHDR D INNER JOIN IUSERUSERHDR U ON U.deptid = D.id where D.id in (")
						.append(join(idOrgs, ",")).append(")");

				if (logger.isDebugEnabled())
					logger.debug("Query: " + query.toString());

				Statement statement = connection.createStatement();
				rs = statement.executeQuery(query.toString());

				while (rs.next()) {
					idUsuario = rs.getString(USU_COL_ID);
					usuarios.add(idUsuario);
				}
			} catch (Exception e) {
				logger.error("Error al leer los usuarios de los \u00F3rganos: "
						+ join(idOrgs, ","), e);
				throw new DBException(e.toString());
			} finally {
				closeResultSet(rs);
			}
			DbConnection.close();

		} catch (Exception e) {

			try {
				DbConnection.ensureClose(e);
			} catch (Exception e1) {

			}

		}
		return usuarios;

	}

	/**
	 * Cierra el conjunto de resultados {@link java.sql.ResultSet} de una
	 * consulta realizada.
	 *
	 * @param rs
	 *            {@link java.sql.ResultSet} que deseamos cerrar
	 */
	private void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				Statement stmt = rs.getStatement();

				// Cerrar el ResultSet
				rs.close();

				// Cerrar el Statement
				if (stmt != null)
					stmt.close();
			}
		} catch (SQLException e) {
			logger.error("Error al cerrar el ResultSet", e);
		}
	}

	/**
	 * Indica si el array está vacío.
	 *
	 * @param array
	 *            Array.
	 * @return true si el array está vacío.
	 */
	private static boolean isEmpty(Object[] array) {
		return (array == null) || (array.length == 0);
	}

	/**
	 * Construye una cadena resultado de la concatenacion de los elementos de un
	 * array separados por una determinada cadena de caracteres.
	 */
	private static String join(Object[] array, String token) {
		if ((array != null) && (array.length > 0)) {
			StringBuffer buffer = new StringBuffer();

			for (int i = 0; i < array.length; i++) {
				if (i > 0)
					buffer.append(token);

				buffer.append(array[i].toString());
			}

			return buffer.toString();
		}

		return null;
	}

	public static OrganoVO getOrganoVO(String id, String nombre,
			String idPadre, String remarks) {

		// Cualquer texto
		// <ORGANO><CODIGO>12345</CODIGO><NIVEL>3</NIVEL><INSTITUCION>N</INSTITUCION></ORGANO></INFO>
		// Más texto
		// ó
		// <ORGANO><CODIGO>12345</CODIGO><NIVEL>3</NIVEL><INSTITUCION>N</INSTITUCION></ORGANO>

		URL rulesXmlUrl = InvesDocConnector.class.getClassLoader().getResource(
				"rules_invesdoc_organization.xml");

		OrganoVO organoVO = null;
		// Cargar la configuración
		Digester digester = DigesterLoader.createDigester(rulesXmlUrl);
		try {
			if (StringUtils.isNotEmpty(remarks)
					&& remarks.toUpperCase().contains(TAG_ORGANO)) {

				String xml = remarks.substring(
						remarks.toUpperCase().indexOf(TAG_ORGANO), remarks
								.toUpperCase().indexOf(TAG_FIN_ORGANO)
								+ (TAG_FIN_ORGANO.length()));

				organoVO = (OrganoVO) digester.parse(new StringReader(
						StringUtils.trim(xml)));
			}

			if (organoVO == null) {
				organoVO = new OrganoVO();
			}
			organoVO.setId(id);
			organoVO.setNombre(nombre);
			organoVO.setIdPadre(idPadre);

			return organoVO;
		} catch (IOException e) {
			logger.error(e);
			throw new UncheckedArchivoException(
					"Error leyendo xml de organizacion", e);
		} catch (SAXException e) {
			logger.warn("El xml de organización no tiene formato valido: "
					+ remarks);
			return null;
		}
	}

}
