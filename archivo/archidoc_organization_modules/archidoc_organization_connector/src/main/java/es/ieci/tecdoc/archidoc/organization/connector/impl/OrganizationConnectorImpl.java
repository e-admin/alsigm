package es.ieci.tecdoc.archidoc.organization.connector.impl;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbConnectionConfig;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import es.ieci.tecdoc.archidoc.organization.connector.OrganizationConnector;
import es.ieci.tecdoc.archidoc.organization.constants.OrganizationConstants;
import es.ieci.tecdoc.archidoc.organization.constants.OrganizationTipoAtributo;
import es.ieci.tecdoc.archidoc.organization.exception.OrganizationException;
import es.ieci.tecdoc.archidoc.organization.messages.OrganizationErrorCodes;
import es.ieci.tecdoc.archidoc.organization.messages.OrganizationMessages;
import es.ieci.tecdoc.archidoc.organization.util.OrganizationUtil;
import es.ieci.tecdoc.archidoc.organization.vo.OrganizationOrgano;
import es.ieci.tecdoc.archidoc.organization.vo.impl.OrganizationOrganoVOImpl;

/**
 * Conector con el Sistema Gestor de Organizacion desde una base de datos.
 * @author Iecisa
 * @version $Revision: 6591 $
 */
public class OrganizationConnectorImpl implements OrganizationConnector {

    /** Logger de la clase. */
    protected static final Logger LOGGER = Logger.getLogger(OrganizationConnectorImpl.class);

    private static final String COL_ID = "ID";
    private static final String COL_CODIGO = "CODIGO";
    private static final String COL_NOMBRE = "NOMBRE";
    private static final String COL_TIPO = "TIPO";
    private static final String COL_NIVEL = "NIVEL";
    private static final String COL_ID_ORG_PADRE = "IDORGPADRE";

    private static final String [] ALL_ORGANO_COLUMS = new String [] {COL_ID, COL_CODIGO, COL_NOMBRE, COL_NIVEL, COL_ID_ORG_PADRE};

    private static final String COL_ID_USUARIO = "IDUSUARIO";
    private static final String COL_ID_ORGANO = "IDORGANO";

    private static final int INSTITUCION = 1;

    public static final int IDENTIFICADOR_ORGANO 	= 1;
    public static final int CODIGO_ORGANO 		= 2;

    private static final String QUERY_SELECT = " SELECT ";
    private static final String QUERY_FROM = " FROM ";
    private static final String QUERY_WHERE = " WHERE ";
    private static final String QUERY_AND = " AND ";
    private static final String QUERY_ORDER_BY = " ORDER BY ";
    private static final String QUERY_EQUAL = " = ";
    private static final String QUERY_COLUMN_SEPARATOR = ", ";
    private static final String QUERY_INNER_JOIN = " INNER JOIN ";
    private static final String QUERY_IN = " IN ";
    private static final String QUERY_ON = " ON ";
    private static final String QUERY_VALUE_DELIMITER = "'";
    private static final String QUERY_UPPER = "UPPER";
    private static final String QUERY_START_PARENTESIS = "(";
    private static final String QUERY_END_PARENTESIS = ")";
    private static final String QUERY_LIKE = " LIKE ";
    private static final String QUERY_LIKE_WILDCARD = "%";

    /** Nombre de la tabla de organizaciones */
    private static final String TABLE_ORGANIZACION = "AOESTRORG";

    /** Nombre de la tabla de usuario-organo */
    private static final String TABLE_USER_ORGANO = "AOUSRORGV";

    /** Objeto de conexion */
    private DbConnectionConfig dbConnectionCfg = null;

    /** Objeto para almacenar la conexion */
    private Connection connection = null;

    /**
     * Devuelve el objeto de configuracion de conexion
     * @return objeto de configuracion de conexion
     */
    public DbConnectionConfig getDbConnectionConfig() {
        return dbConnectionCfg;
    }

    /**
     * Establece el objeto de configuracion de conexion
     * @param connectionCfg objeto de configuracion de conexion
     */
    public void setDbConnectionConfig(final DbConnectionConfig connectionCfg) {
        this.dbConnectionCfg = connectionCfg;
    }

    /**
     * Devuelve la conexion
     * @return conexion
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Establece la conexion
     * @param connection conexion
     */
    public void setConnection(final Connection connection) {
        this.connection = connection;
    }

    /**
     * Permite obtener un mensaje formateado con sus argumentos
     * @param errCode Codigo de error
     * @param args Argumentos del error
     * @return mensaje formateado con sus argumentos
     */
    private String getMessage(final String errCode, final Object [] args){
        return OrganizationMessages.formatMessage(OrganizationErrorCodes.BUNDLE, errCode, args);
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.archidoc.organization.connector.OrganizationConnector#initialize(java.util.Properties)
     */
    public void initialize(final Properties params) throws OrganizationException {

        String dataSourceName = params.getProperty(OrganizationConstants.PARAM_DATASOURCE);
        String dbDrvClassName = params.getProperty(OrganizationConstants.PARAM_DB_DRIVER_CLASS_NAME);
        if (StringUtils.isNotEmpty(dataSourceName)){
            dataSourceName = OrganizationUtil.composeDsName(dataSourceName, (String)params.get(OrganizationConstants.PARAM_ENTITY));
            dbConnectionCfg = new DbConnectionConfig(dataSourceName, null, null);
        } else if (StringUtils.isNotEmpty(dbDrvClassName)) {
        	String dbUrl = params.getProperty(OrganizationConstants.PARAM_DB_URL);
        	String dbUser = params.getProperty(OrganizationConstants.PARAM_DB_USER);
        	String dbPassword = params.getProperty(OrganizationConstants.PARAM_DB_PASSWORD);
            dbConnectionCfg = new DbConnectionConfig(dbDrvClassName, dbUrl, dbUser, dbPassword);
        } else {
        	String msg = getMessage(OrganizationErrorCodes.ERR_20001,null);
            LOGGER.error(msg);
            throw new OrganizationException(OrganizationErrorCodes.ERR_20001, msg);
        }
    }

    /**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnector#recuperarHijosDeOrgano(java.lang.String)
	 */
    public List recuperarHijosDeOrgano(final String idOrgPadre) throws OrganizationException {
        List organos = new ArrayList();
        ResultSet resultSet = null;
        try {
            if (StringUtils.isNotEmpty(idOrgPadre)){
                   connection = DbConnection.open(dbConnectionCfg);
                if (LOGGER.isDebugEnabled()){
                    LOGGER.debug("Llamada a recuperarHijosDeOrgano(" + idOrgPadre + ")");
                }
                try {
                    StringBuffer query = new StringBuffer()
                    .append(QUERY_SELECT).append(join(ALL_ORGANO_COLUMS,QUERY_COLUMN_SEPARATOR))
                    .append(QUERY_FROM).append(TABLE_ORGANIZACION)
                    .append(QUERY_WHERE).append(generateEqual(COL_ID_ORG_PADRE, idOrgPadre, QUERY_VALUE_DELIMITER));

                    if (LOGGER.isDebugEnabled()){
                        LOGGER.debug("Query recuperarHijosDeOrgano: " + query.toString());
                    }

                    Statement statement = connection.createStatement();
                    resultSet = statement.executeQuery(query.toString());
                    organos = dbToOrganoVOList(resultSet);
                } finally {
                    closeResultSet(resultSet);
                }
                DbConnection.close();
            }
        } catch (Exception e) {
            try {
                DbConnection.ensureClose(e);
            } catch (Exception e1) {}
            String msg = getMessage(OrganizationErrorCodes.ERR_20002,new String [] {idOrgPadre});
            LOGGER.error(msg,e);
            throw new OrganizationException(OrganizationErrorCodes.ERR_20002, msg, e);
        }

        if(organos.isEmpty()){
        	organos = null;
        }

        return organos;
    }

    /**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnector#recuperarInstitucionesProductoras()
	 */
    public List recuperarInstitucionesProductoras()	throws OrganizationException {
        List instituciones = new ArrayList();
        ResultSet resultSet = null;
        try {
            connection = DbConnection.open(dbConnectionCfg);
            if (LOGGER.isDebugEnabled()){
                LOGGER.debug("Llamada a recuperarInstitucionesProductoras()");
            }

            try {
                StringBuffer query = new StringBuffer()
                .append(QUERY_SELECT).append(join(ALL_ORGANO_COLUMS,QUERY_COLUMN_SEPARATOR))
                .append(QUERY_FROM).append(TABLE_ORGANIZACION)
                .append(QUERY_WHERE).append(generateEqual(COL_TIPO, String.valueOf(INSTITUCION), null))
                .append(QUERY_ORDER_BY).append(COL_NOMBRE);

                if (LOGGER.isDebugEnabled()){
                    LOGGER.debug("Query recuperarInstitucionesProductoras: " + query.toString());
                }

                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery(query.toString());
                instituciones = dbToOrganoVOList(resultSet);
            } finally {
                closeResultSet(resultSet);
            }
            DbConnection.close();
        } catch (Exception e) {
            try {
                DbConnection.ensureClose(e);
            } catch (Exception e1) {}
            String msg = getMessage(OrganizationErrorCodes.ERR_20003,null);
            LOGGER.error(msg,e);
            throw new OrganizationException(OrganizationErrorCodes.ERR_20003, msg, e);
        }

        if(instituciones.isEmpty()){
        	instituciones = null;
        }

        return instituciones;
    }

    /**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnector#recuperarOrgano(int, java.lang.String)
	 */
    public OrganizationOrgano recuperarOrgano(final int tipoAtrib, final String valorAtrib) throws OrganizationException {
    	OrganizationOrganoVOImpl organo = null;
    	ResultSet resultSet = null;
    	try {
    		if (StringUtils.isNotEmpty(valorAtrib)){
    			connection = DbConnection.open(dbConnectionCfg);
    			if (LOGGER.isDebugEnabled()) {
    				LOGGER.debug("Llamada a recuperarOrgano(" + tipoAtrib + ", " + valorAtrib + ")");
    			}
    			try {
    				String [] colsNames = new String [] {COL_ID, COL_CODIGO, COL_NOMBRE, COL_NIVEL, COL_ID_ORG_PADRE};

    				StringBuffer query = new StringBuffer()
    				.append(QUERY_SELECT).append(join(colsNames,QUERY_COLUMN_SEPARATOR))
    				.append(QUERY_FROM).append(TABLE_ORGANIZACION);

    				if (tipoAtrib == IDENTIFICADOR_ORGANO) {
    					query.append(QUERY_WHERE).append(generateEqual(COL_ID, String.valueOf(valorAtrib), QUERY_VALUE_DELIMITER));
    				} else if (tipoAtrib == CODIGO_ORGANO) {
    					query.append(QUERY_WHERE).append(generateEqual(COL_CODIGO, String.valueOf(valorAtrib), QUERY_VALUE_DELIMITER));
    				} else {
    					throw new IllegalArgumentException("Tipo de atributo [" + tipoAtrib + "] invalido ");
    				}

    				if (LOGGER.isDebugEnabled()) {
    					LOGGER.debug("Query recuperarOrgano: " + query.toString());
    				}

    				Statement statement = connection.createStatement();
    				resultSet = statement.executeQuery(query.toString());

    				List listaOrganos = dbToOrganoVOList(resultSet);

    				if ((listaOrganos!=null)&&(!listaOrganos.isEmpty())){
    					organo = (OrganizationOrganoVOImpl) listaOrganos.get(0);
    				}
    			}
    			finally {
    				closeResultSet(resultSet);
    			}
    			DbConnection.close();
    		}
    	} catch (Exception e) {
    		try {
    			DbConnection.ensureClose(e);
    		} catch (Exception e1) {}
    		String msg = getMessage(OrganizationErrorCodes.ERR_20004, new String [] {String.valueOf(tipoAtrib), valorAtrib});
    		LOGGER.error(msg,e);
    		throw new OrganizationException(OrganizationErrorCodes.ERR_20004, msg, e);
    	}
    	return organo;
    }

    /**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnector#recuperarOrganodeUsuario(java.lang.String)
	 */
    public OrganizationOrgano recuperarOrganodeUsuario(final String idUsr) throws OrganizationException {
        ResultSet resultSet = null;
        OrganizationOrganoVOImpl organo = null;
        try {
            if (StringUtils.isNotEmpty(idUsr)) {
	            connection = DbConnection.open(dbConnectionCfg);
	            try {
	            	String [] colsNames = new String [] {COL_ID, COL_CODIGO, COL_NOMBRE, COL_NIVEL, COL_ID_ORG_PADRE};
	                StringBuffer query = new StringBuffer()
	                .append(QUERY_SELECT).append(join(colsNames,QUERY_COLUMN_SEPARATOR))
    				.append(QUERY_FROM).append(TABLE_ORGANIZACION)
	                .append(QUERY_INNER_JOIN).append(TABLE_USER_ORGANO)
	                .append(QUERY_ON)
	                .append(generateEqual(COL_ID, COL_ID_ORGANO, null))
	                .append(QUERY_AND)
	                .append(generateEqual(COL_ID_USUARIO, idUsr, QUERY_VALUE_DELIMITER));

	                if (LOGGER.isDebugEnabled()) {
	                    LOGGER.debug("Query recuperarOrganodeUsuario: " + query.toString());
	                }

	                Statement statement = connection.createStatement();
	                resultSet = statement.executeQuery(query.toString());
	                List listaOrganos = dbToOrganoVOList(resultSet);

	                if ((listaOrganos != null) && (!listaOrganos.isEmpty())){
	                    organo = (OrganizationOrganoVOImpl) listaOrganos.get(0);
	                }
	            } finally {
	                closeResultSet(resultSet);
	            }
	            DbConnection.close();
            }
        } catch (Exception e) {
            try {
                DbConnection.ensureClose(e);
            } catch (Exception e1) {}
            String msg = getMessage(OrganizationErrorCodes.ERR_20005, new String [] {idUsr});
            LOGGER.error(msg,e);
            throw new OrganizationException(OrganizationErrorCodes.ERR_20005, msg, e);
        }
        return organo;
    }

    /**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnector#recuperarOrganos(java.lang.String)
	 */
    public List recuperarOrganos(final String param) throws OrganizationException {
        List organos = new ArrayList();
        ResultSet resultSet = null;
        try {
            connection = DbConnection.open(dbConnectionCfg);
            if (LOGGER.isDebugEnabled()){
                LOGGER.debug("Llamada a recuperarOrganos(" + param + ")");
            }

            String paramValue = param;
            if (paramValue == null){
                paramValue = "";
            }

            try {
            	String [] colsNames = new String [] {COL_ID, COL_CODIGO, COL_NOMBRE, COL_NIVEL, COL_ID_ORG_PADRE};
                StringBuffer query = new StringBuffer()
                .append(QUERY_SELECT).append(join(colsNames,QUERY_COLUMN_SEPARATOR))
				.append(QUERY_FROM).append(TABLE_ORGANIZACION)
                .append(QUERY_WHERE)
                .append(generateLikeUppercase(COL_NOMBRE, paramValue));

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Query recuperarOrganos: " + query.toString());
                }

                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery(query.toString());
                organos = dbToOrganoVOList(resultSet);
            } finally {
                closeResultSet(resultSet);
            }
            DbConnection.close();
        } catch (Exception e) {
            try {
                DbConnection.ensureClose(e);
            } catch (Exception e1) {}
            String msg = getMessage(OrganizationErrorCodes.ERR_20006, new String [] {param});
            LOGGER.error(msg,e);
            throw new OrganizationException(OrganizationErrorCodes.ERR_20006, msg, e);
        }

        if(organos.isEmpty()){
        	organos = null;
        }

        return organos;
    }

    /**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnector#recuperarOrganosAntecesores(java.lang.String, int)
	 */
    public List recuperarOrganosAntecesores(final String idOrg, final int numNiveles) throws OrganizationException {
        List organos = new ArrayList();
        ResultSet resultSet = null;
        try {
            if (StringUtils.isNotEmpty(idOrg)) {
	            connection = DbConnection.open(dbConnectionCfg);
	            if (LOGGER.isDebugEnabled()) {
	                LOGGER.debug("Llamada a recuperarOrganosAntecesores(" + idOrg + ", " + numNiveles + ")");
	            }

	            try {
	                organos = getAntecesores(idOrg, numNiveles);
	            } finally {
	                closeResultSet(resultSet);
	            }
	            DbConnection.close();
            }
        } catch (Exception e) {
            try {
                DbConnection.ensureClose(e);
            } catch (Exception e1) {}
            String msg = getMessage(OrganizationErrorCodes.ERR_20007, new String [] {idOrg});
            LOGGER.error(msg,e);
            throw new OrganizationException(OrganizationErrorCodes.ERR_20007, msg, e);
        }

        if(organos.isEmpty()){
        	organos = null;
        }

        return organos;
    }

    /**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnector#recuperarOrganosDependientes(java.lang.String, int)
	 */
    public List recuperarOrganosDependientes(final String idOrg, final int numNiveles) throws OrganizationException {
        List organos = new ArrayList();
        ResultSet resultSet = null;
        try {
        	 if (StringUtils.isNotEmpty(idOrg)) {
	            int nNiveles = numNiveles;
	            if (nNiveles == 0) {
	                nNiveles = Integer.MAX_VALUE;
	            }

	            connection = DbConnection.open(dbConnectionCfg);
	            if (LOGGER.isDebugEnabled()) {
	                LOGGER.debug("Llamada a recuperarOrganosDependientes(" + idOrg + ", " + nNiveles + ")");
	            }

	            try {
	                organos = getDescendientes(idOrg, nNiveles);
	            } finally {
	                closeResultSet(resultSet);
	            }
	            DbConnection.close();
        	 }
        } catch (Exception e) {
            try {
                DbConnection.ensureClose(e);
            } catch (Exception e1) {}
            String msg = getMessage(OrganizationErrorCodes.ERR_20008, new String [] {idOrg});
            LOGGER.error(msg,e);
            throw new OrganizationException(OrganizationErrorCodes.ERR_20008, msg, e);
        }

        if(organos.isEmpty()){
        	organos = null;
        }

        return organos;
    }

    /**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnector#recuperarUsuariosDeOrganos(java.util.List)
	 */
    public List recuperarUsuariosDeOrganos(final List idOrgs) throws OrganizationException {
        List usuarios = new ArrayList();
        String idUsuario = null;
        ResultSet resultSet = null;
        try {
            if ((idOrgs!=null)&&(!idOrgs.isEmpty())){
	            String [] ids = (String [])idOrgs.toArray(new String[idOrgs.size()]);

	            connection = DbConnection.open(dbConnectionCfg);
	            if (LOGGER.isDebugEnabled()) {
	                LOGGER.debug("Llamada a recuperarUsuariosDeOrganos("+ join(ids, ",") + ")");
	            }

	            try {
	                StringBuffer query = new StringBuffer()
	                .append(QUERY_SELECT).append(COL_ID_USUARIO).append(QUERY_FROM).append(TABLE_USER_ORGANO)
	                .append(QUERY_WHERE).append(COL_ID_ORGANO)
	                .append(QUERY_IN).append(QUERY_START_PARENTESIS)
	                .append(join(ids, ",", "'"))
	                .append(QUERY_END_PARENTESIS);

	                if (LOGGER.isDebugEnabled()) {
	                    LOGGER.debug("Query recuperarUsuariosDeOrganos: " + query.toString());
	                }

	                Statement statement = connection.createStatement();
	                resultSet = statement.executeQuery(query.toString());

	                while (resultSet.next()) {
	                    idUsuario = resultSet.getString(COL_ID_USUARIO);
	                    usuarios.add(idUsuario);
	                }
	            } finally {
	                closeResultSet(resultSet);
	            }
	            DbConnection.close();
            }
        } catch (Exception e) {
            try {
                DbConnection.ensureClose(e);
            } catch (Exception e1) {}
            String msg = getMessage(OrganizationErrorCodes.ERR_20009, new String [] {join(idOrgs, ",")});
            LOGGER.error(msg,e);
            throw new OrganizationException(OrganizationErrorCodes.ERR_20009, msg, e);
        }

        if(usuarios.isEmpty()){
        	usuarios = null;
        }

        return usuarios;
    }

    /*******************************************/
    /******	METODOS PROPIOS DEL CONNECTOR ******/
    /*******************************************/

    /**
     * Obtiene los organos antecesores de un determinado organo padre pasado como parametro.
     * @param idOrg Identificador del organo
     * @param numNiveles Numero de niveles que se desea obtener
     * @return Lista de organos antecesores
     * 		<p>Los objetos de la lista implementan el interface {@link OrganizationOrgano}.</p>
     * @throws OrganizationException
     */
    private List getAntecesores (final String idOrg, final int numNiveles) throws OrganizationException {

    	List antecesores = new ArrayList();

    	int nNiveles = numNiveles;

    	if (nNiveles == 0) {
    		nNiveles = Integer.MAX_VALUE;
    	}

    	OrganizationOrgano organo = recuperarOrgano(OrganizationTipoAtributo.IDENTIFICADOR_ORGANO, idOrg);
    	while ( (organo != null)
    			&& StringUtils.isNotEmpty(organo.getIdPadre())
    			&& (nNiveles > 0) )
    	{
    		organo = recuperarOrgano(OrganizationTipoAtributo.IDENTIFICADOR_ORGANO, organo.getIdPadre());
    		if (organo != null){
    			antecesores.add(0, organo);
    		}
    		nNiveles--;
    	}

    	return antecesores;
    }

    /**
     * Obtiene los organos descendientes de un determinado organo padre pasado como parametro de forma recursiva.
     * @param idOrg Identificador del organo
     * @param numNiveles Numero de niveles que se desea obtener
     * @return Lista de organos descendientes
     * 		<p>Los objetos de la lista implementan el interface {@link OrganizationOrgano}.</p>
     * @throws OrganizationException
     */
    private List getDescendientes(final String idOrg, final int numNiveles) throws OrganizationException{
        List dependientes = new ArrayList();
        if (numNiveles > 0) {
            List hijos = recuperarHijosDeOrgano(idOrg);
            OrganizationOrgano organo;
            if (hijos!=null){
	            for (int i = 0; i < hijos.size(); i++) {
	                organo = (OrganizationOrgano) hijos.get(i);
	                dependientes.add(organo);
	                dependientes.addAll(getDescendientes(organo.getId(), numNiveles - 1));
	            }
            }
        }
        return dependientes;
    }

    /**
     * Transforma el resultado de una consulta en una lista de organos
     * @param resultSet Resulset
     * @return Lista de Organos.
     * 		<p>Los objetos de la lista implementan el interface {@link OrganizationOrgano}.</p>
     * @throws SQLException
     */
    private List dbToOrganoVOList (final ResultSet resultSet) throws SQLException {
        List ltOrganos = new ArrayList();
        OrganizationOrganoVOImpl organo = null;

        if (resultSet != null) {
            while (resultSet.next()) {
                organo = new OrganizationOrganoVOImpl();
                organo.setId(resultSet.getString(COL_ID));
                organo.setCodigo(resultSet.getString(COL_CODIGO));
                organo.setNombre(resultSet.getString(COL_NOMBRE));
                organo.setNivel(resultSet.getInt(COL_NIVEL));
                organo.setIdPadre(resultSet.getString(COL_ID_ORG_PADRE));
                ltOrganos.add(organo);
            }
        }
        return ltOrganos;
    }

    /**
     * Cierra el conjunto de resultados {@link java.sql.ResultSet} de una consulta realizada.
     * @param resulSet {@link java.sql.ResultSet} que deseamos cerrar
     * @throws SQLException
     */
    private void closeResultSet(final ResultSet resulSet) throws SQLException {
        if (resulSet != null) {
            Statement stmt = resulSet.getStatement();
            resulSet.close();
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * Construye una cadena resultado de la concatenacion de los elementos de
     * un array separados por una determinada cadena de caracteres.
     */
    private static String join(final Object[] array, final String token) {
    	return join (array, token, null);
    }

    /**
     * Construye una cadena resultado de la concatenacion de los elementos de
     * un array separados por una determinada cadena de caracteres.
     */
    private static String join(final Object[] array, final String token, final String delimiter) {
    	StringBuffer buffer = new StringBuffer();
        if ((array != null) && (array.length > 0)) {
            for (int i = 0; i < array.length; i++) {
                if (i > 0){
                    buffer.append(token);
                }

                if (StringUtils.isNotEmpty(delimiter)) {
                	buffer.append(delimiter).append(array[i].toString()).append(delimiter);
                } else {
                	buffer.append(array[i].toString());
                }
            }
        }
        return buffer.toString();
    }

    /**
     * Construye una cadena resultado de la concatenacion de los elementos de
     * una lista separados por una determinada cadena de caracteres.
     */
    private static String join(final List elements, final String token) {
        if ((elements != null) && (!elements.isEmpty())) {
            StringBuffer buffer = new StringBuffer();

            Iterator itElements = elements.iterator();
            while (itElements.hasNext()){
                if (buffer.length()>0) {
                    buffer.append(token);
                }
                buffer.append(itElements.next().toString());
            }
        }
        return null;
    }

    /**
     * Genera una igualdad en una consulta columna = valor
     * @param column Columna de la que se quiere generar la igualdad
     * @param value Valor de la igualdad
     * @param valueDelimiter Delimitador a utilizar en el valor
     * @return
     */
    private static String generateEqual(String column, String value, String valueDelimiter){
    	StringBuffer buffer = new StringBuffer();

    	String columnValue = value;
    	if (columnValue == null){
    		columnValue = "";
    	}

    	if (StringUtils.isNotEmpty(column)){
    		buffer.append(column).append(QUERY_EQUAL);

	    	if (StringUtils.isNotEmpty(valueDelimiter)){
	    		buffer.append(valueDelimiter).append(columnValue).append(valueDelimiter);
	    	} else {
	    		buffer.append(columnValue);
	    	}
    	}
    	return buffer.toString();
    }

    /**
     * Genera una igualdad en una consulta columna = valor
     * @param column Columna de la que se quiere generar la igualdad
     * @param value Valor de la igualdad
     * @param valueDelimiter Delimitador a utilizar en el valor
     * @return
     */
    private static String generateLikeUppercase(String column, String value){
    	StringBuffer buffer = new StringBuffer();

    	String columnValue = value;
    	if (columnValue == null){
    		columnValue = "";
    	}

		if (StringUtils.isNotEmpty(column)) {
			buffer.append(QUERY_UPPER).append(QUERY_START_PARENTESIS).append(
					column).append(QUERY_END_PARENTESIS).append(QUERY_LIKE)
					.append(QUERY_VALUE_DELIMITER).append(QUERY_LIKE_WILDCARD)
					.append(columnValue.toUpperCase()).append(QUERY_LIKE_WILDCARD)
					.append(QUERY_VALUE_DELIMITER);
		}

    	return buffer.toString();
    }
}