/**
 *
 */
package es.ieci.tecdoc.archidoc.organization.connector;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbConnectionConfig;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnectorImpl;
import es.ieci.tecdoc.archidoc.organization.constants.OrganizationConstants;
import es.ieci.tecdoc.archidoc.organization.constants.OrganizationTipoAtributo;
import es.ieci.tecdoc.archidoc.organization.exception.OrganizationException;
import es.ieci.tecdoc.archidoc.organization.messages.OrganizationErrorCodes;
import es.ieci.tecdoc.archidoc.organization.vo.OrganizationOrgano;

/**
 * Test para el conector de organizacion
 * @author Iecisa
 * @version $Revision: 6586 $
 *
 */
public class OrganizationConnectorTest extends TestCase {

	 /** Logger de la clase. */
    protected static final Logger LOGGER = Logger.getLogger(OrganizationConnectorTest.class);

	/**
	 * Conector de organizacion
	 */
	private OrganizationConnector connector;

	/**
	 * Clase para el driver de base de datos
	 */
	private static final String DB_DRIVER_CLASS_NAME = "org.postgresql.Driver";

	/**
	 * Url de la base de datos
	 */
	private static final String DB_URL = "jdbc:postgresql://10.228.20.178/pruebas";

	/**
	 * Usuario de la base de datos
	 */
	private static final String DB_USER = "postgres";

	/**
	 * Password de la base de datos
	 */
	private static final String DB_PASSWORD = "postgres";

	/**
	 * Configuracion de conexion por defecto
	 */
	private DbConnectionConfig dbConnectionCfg = new DbConnectionConfig(DB_DRIVER_CLASS_NAME, DB_URL, DB_USER, DB_PASSWORD);

	/**
	 * Conexion a base de datos
	 */
	private Connection connection;

	/**
	 * Variable para indicar si se crean las tablas
	 */
	private static boolean createTables = true;

	/**
	 * Variable para indicar si se eliminan las tablas
	 */
	private static boolean dropTables = false;

	/**
	 * Metodo para crear las tablas necesarias con sus datos
	 */
	private void createTablesInsertData() {

		try {
			connection = DbConnection.open(dbConnectionCfg);

			Statement statement = connection.createStatement();

			String query = "CREATE TABLE AOESTRORG(ID VARCHAR(32) NOT NULL,CODIGO VARCHAR(64) NOT NULL,NOMBRE VARCHAR(254) NOT NULL,TIPO INTEGER NOT NULL,NIVEL INTEGER NOT NULL,IDORGPADRE VARCHAR(32),ESTADO INTEGER NOT NULL,FINICIOVIGENCIA TIMESTAMP,FFINVIGENCIA TIMESTAMP,DESCRIPCION VARCHAR(254))";
			statement.executeUpdate(query);
			query = "CREATE TABLE AOUSR(ID VARCHAR(64) NOT NULL, DIRECCION VARCHAR(254), EMAIL VARCHAR(254), NOMBRE VARCHAR(64), APELLIDOS VARCHAR(128), NOMBREUSUARIO VARCHAR(64) NOT NULL, PASSWORD VARCHAR(64) NOT NULL)";
			statement.executeUpdate(query);
			query = "CREATE TABLE AOUSRORGV( IDUSUARIO VARCHAR(64) NOT NULL, NOMBREUSUARIO VARCHAR(254) NOT NULL, IDORGANO VARCHAR(32) NOT NULL)";
			statement.executeUpdate(query);
			query = "INSERT INTO AOESTRORG (ID,CODIGO,NOMBRE,TIPO,NIVEL,IDORGPADRE,ESTADO,FINICIOVIGENCIA,FFINVIGENCIA,DESCRIPCION) VALUES ('INST1','INST1','Institución 1',1,0,NULL,2,now(),NULL,'Institución 1')";
			statement.executeUpdate(query);
			query = "INSERT INTO AOESTRORG (ID,CODIGO,NOMBRE,TIPO,NIVEL,IDORGPADRE,ESTADO,FINICIOVIGENCIA,FFINVIGENCIA,DESCRIPCION) VALUES ('ORG1','ORG1','Órgano 1',2,1,'INST1',2,now(),NULL,'Órgano 1')";
			statement.executeUpdate(query);
			query = "INSERT INTO AOESTRORG (ID,CODIGO,NOMBRE,TIPO,NIVEL,IDORGPADRE,ESTADO,FINICIOVIGENCIA,FFINVIGENCIA,DESCRIPCION) VALUES ('ORG11','ORG11','Órgano 1-1',2,2,'ORG1',2,now(),NULL,'Órgano 1-1')";
			statement.executeUpdate(query);
			query = "INSERT INTO AOESTRORG (ID,CODIGO,NOMBRE,TIPO,NIVEL,IDORGPADRE,ESTADO,FINICIOVIGENCIA,FFINVIGENCIA,DESCRIPCION) VALUES ('ORG111','ORG111','Órgano 1-1-1',2,3,'ORG11',2,now(),NULL,'Órgano 1-1-1')";
			statement.executeUpdate(query);
			query = "INSERT INTO AOESTRORG (ID,CODIGO,NOMBRE,TIPO,NIVEL,IDORGPADRE,ESTADO,FINICIOVIGENCIA,FFINVIGENCIA,DESCRIPCION) VALUES ('ORG1111','ORG1111','Órgano 1-1-1-1',2,4,'ORG111',2,now(),NULL,'Órgano 1-1-1-1')";
			statement.executeUpdate(query);
			query = "INSERT INTO AOESTRORG (ID,CODIGO,NOMBRE,TIPO,NIVEL,IDORGPADRE,ESTADO,FINICIOVIGENCIA,FFINVIGENCIA,DESCRIPCION) VALUES ('INST2','INST2','Institución 2',1,0,NULL,2,now(),NULL,'Institución 2')";
			statement.executeUpdate(query);
			query = "INSERT INTO AOESTRORG (ID,CODIGO,NOMBRE,TIPO,NIVEL,IDORGPADRE,ESTADO,FINICIOVIGENCIA,FFINVIGENCIA,DESCRIPCION) VALUES ('ORG2','ORG2','Órgano 2',2,1,'INST2',2,now(),NULL,'Órgano 2')";
			statement.executeUpdate(query);
			query = "INSERT INTO AOUSRORGV (IDUSUARIO,NOMBREUSUARIO,IDORGANO) VALUES ('USR1','Usuario1','ORG1')";
			statement.executeUpdate(query);
			query = "INSERT INTO AOUSRORGV (IDUSUARIO,NOMBREUSUARIO,IDORGANO) VALUES ('USR2','Usuario2','ORG2')";
			statement.executeUpdate(query);

			statement.close();
			DbConnection.close();
		} catch (Exception e) {
			try {
				DbConnection.ensureClose(e);
			} catch (Exception e1) {
				LOGGER.error(e1);
			}
		}
	}

	/**
	 * Metodo para eliminar las tablas necesarias
	 */
	private void dropTables() {

		try {
			connection = DbConnection.open(dbConnectionCfg);

			Statement statement = connection.createStatement();

			String query = "DROP TABLE AOESTRORG";
			statement.executeUpdate(query);
			query = "DROP TABLE AOUSR";
			statement.executeUpdate(query);
			query = "DROP TABLE AOUSRORGV;";
			statement.executeUpdate(query);

			statement.close();
			DbConnection.close();
		} catch (Exception e) {
			try {
				DbConnection.ensureClose(e);
			} catch (Exception e1) {
				LOGGER.error(e1);
			}
		}
	}

	/**
	 * Inicializa cada test
	 * @throws java.lang.Exception
	 */
	protected void setUp() throws Exception {

		Properties params = new Properties();
		params.put(OrganizationConstants.PARAM_DB_DRIVER_CLASS_NAME, DB_DRIVER_CLASS_NAME);
		params.put(OrganizationConstants.PARAM_DB_URL, DB_URL);
		params.put(OrganizationConstants.PARAM_DB_USER, DB_USER);
		params.put(OrganizationConstants.PARAM_DB_PASSWORD, DB_PASSWORD);

		connector = new OrganizationConnectorImpl();
		connector.initialize(params);

		if (createTables){
			try {
				dropTables();
			} catch (Exception e) {
			}
			createTablesInsertData();
			createTables = false;
		}
	}

	/**
	 * Libera recursos en cada test
	 * @throws java.lang.Exception
	 */
	protected void tearDown() throws Exception {

		connector = null;

		if (dropTables){
			dropTables();
			dropTables = false;
		}
	}

	/**
	 * Metodo de test para {@link es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnectorImpl#recuperarHijosDeOrgano(java.lang.String)}.
	 */
	public void testRecuperarHijosDeInstitucion() {
		List hijos = connector.recuperarHijosDeOrgano("INST1");
		assertEquals(1,hijos.size());
		assertEquals("ORG1",((OrganizationOrgano)hijos.get(0)).getId());
	}

	/**
	 * Metodo de test para {@link es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnectorImpl#recuperarHijosDeOrgano(java.lang.String)}.
	 */
	public void testRecuperarHijosDeOrgano() {
		List hijos = connector.recuperarHijosDeOrgano("ORG11");
		assertEquals(1,hijos.size());
		assertEquals("ORG111",((OrganizationOrgano)hijos.get(0)).getId());
	}

	/**
	 * Metodo de test para {@link es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnectorImpl#recuperarInstitucionesProductoras()}.
	 */
	public void testRecuperarInstitucionesProductoras() {
		List instituciones = connector.recuperarInstitucionesProductoras();
		assertEquals(2,instituciones.size());
		assertEquals("INST1",((OrganizationOrgano)instituciones.get(0)).getId());
		assertEquals("INST2",((OrganizationOrgano)instituciones.get(1)).getId());
	}

	/**
	 * Metodo de test para {@link es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnectorImpl#recuperarOrgano(int, java.lang.String)}.
	 */
	public void testRecuperarOrganoByCodigo() {
		OrganizationOrgano organizationOrgano = connector.recuperarOrgano(OrganizationTipoAtributo.CODIGO_ORGANO, "ORG1");
		assertEquals("ORG1",organizationOrgano.getId());
	}

	/**
	 * Metodo de test para {@link es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnectorImpl#recuperarOrgano(int, java.lang.String)}.
	 */
	public void testRecuperarOrganoInexistenteByCodigo() {
		OrganizationOrgano organizationOrgano = connector.recuperarOrgano(OrganizationTipoAtributo.CODIGO_ORGANO, "ORG0");
		assertNull(organizationOrgano);
	}

	/**
	 * Metodo de test para {@link es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnectorImpl#recuperarOrgano(int, java.lang.String)}.
	 */
	public void testRecuperarOrganoByIdentificador() {
		OrganizationOrgano organizationOrgano = connector.recuperarOrgano(OrganizationTipoAtributo.IDENTIFICADOR_ORGANO, "ORG1");
		assertEquals("ORG1",organizationOrgano.getId());
	}

	/**
	 * Metodo de test para {@link es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnectorImpl#recuperarOrgano(int, java.lang.String)}.
	 */
	public void testRecuperarOrganoInexistenteByIdentificador() {
		OrganizationOrgano organizationOrgano = connector.recuperarOrgano(OrganizationTipoAtributo.IDENTIFICADOR_ORGANO, "ORG0");
		assertNull(organizationOrgano);
	}

	/**
	 * Metodo de test para {@link es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnectorImpl#recuperarOrgano(int, java.lang.String)}.
	 */
	public void testRecuperarOrganoByAtributoNoValido() {
		OrganizationOrgano organizationOrgano = null;
		try {
			organizationOrgano = connector.recuperarOrgano(3, "ORG1");
		} catch (OrganizationException e) {
			assertEquals(OrganizationErrorCodes.ERR_20004,e.getErrCode());
		}
		assertNull(organizationOrgano);
	}

	/**
	 * Metodo de test para {@link es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnectorImpl#recuperarOrganodeUsuario(java.lang.String)}.
	 */
	public void testRecuperarOrganodeUsuario() {
		OrganizationOrgano organizationOrgano = connector.recuperarOrganodeUsuario("USR1");
		assertEquals("ORG1",organizationOrgano.getId());
	}

	/**
	 * Metodo de test para {@link es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnectorImpl#recuperarOrganodeUsuario(java.lang.String)}.
	 */
	public void testRecuperarOrganodeUsuarioInexistente() {
		OrganizationOrgano organizationOrgano = connector.recuperarOrganodeUsuario("USR0");
		assertNull(organizationOrgano);
	}


	/**
	 * Metodo de test para {@link es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnectorImpl#recuperarOrganos(java.lang.String)}.
	 */
	public void testRecuperarOrganos() {
		List organos = connector.recuperarOrganos("Institución");
		assertEquals("INST1",((OrganizationOrgano)organos.get(0)).getId());
		assertEquals("INST2",((OrganizationOrgano)organos.get(1)).getId());
	}

	/**
	 * Metodo de test para {@link es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnectorImpl#recuperarOrganosAntecesores(java.lang.String, int)}.
	 */
	public void testRecuperarOrganosAntecesoresSinLimite() {
		List organos = connector.recuperarOrganosAntecesores("ORG1111", 0);
		assertEquals(4, organos.size());
		assertEquals("INST1",((OrganizationOrgano)organos.get(0)).getId());
		assertEquals("ORG1",((OrganizationOrgano)organos.get(1)).getId());
		assertEquals("ORG11",((OrganizationOrgano)organos.get(2)).getId());
		assertEquals("ORG111",((OrganizationOrgano)organos.get(3)).getId());
	}

	/**
	 * Metodo de test para {@link es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnectorImpl#recuperarOrganosAntecesores(java.lang.String, int)}.
	 */
	public void testRecuperarOrganosAntecesoresConLimite() {
		List organos = connector.recuperarOrganosAntecesores("ORG1111", 1);
		assertEquals(1, organos.size());
		assertEquals("ORG111",((OrganizationOrgano)organos.get(0)).getId());
	}

	/**
	 * Metodo de test para {@link es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnectorImpl#recuperarOrganosDependientes(java.lang.String, int)}.
	 */
	public void testRecuperarOrganosAntecesoresVacio() {
		List organos = connector.recuperarOrganosAntecesores("INST1", 0);
		assertNull(organos);
	}

	/**
	 * Metodo de test para {@link es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnectorImpl#recuperarOrganosDependientes(java.lang.String, int)}.
	 */
	public void testRecuperarOrganosDependientesSinLimite() {
		List organos = connector.recuperarOrganosDependientes("ORG11", 0);
		assertEquals(2, organos.size());
		assertEquals("ORG111",((OrganizationOrgano)organos.get(0)).getId());
		assertEquals("ORG1111",((OrganizationOrgano)organos.get(1)).getId());
	}

	/**
	 * Metodo de test para {@link es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnectorImpl#recuperarOrganosDependientes(java.lang.String, int)}.
	 */
	public void testRecuperarOrganosDependientesConLimite() {
		List organos = connector.recuperarOrganosDependientes("ORG11", 1);
		assertEquals(1, organos.size());
		assertEquals("ORG111",((OrganizationOrgano)organos.get(0)).getId());
	}

	/**
	 * Metodo de test para {@link es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnectorImpl#recuperarOrganosDependientes(java.lang.String, int)}.
	 */
	public void testRecuperarOrganosDependientesVacio() {
		List organos = connector.recuperarOrganosDependientes("ORG1111", 0);
		assertNull(organos);
	}

	/**
	 * Metodo de test para {@link es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnectorImpl#recuperarUsuariosDeOrganos(java.util.List)}.
	 */
	public void testRecuperarUsuariosDeOrganos() {
		dropTables = true;
		List idOrgs = new ArrayList();
		idOrgs.add("ORG1");
		idOrgs.add("ORG2");
		List usuarios = connector.recuperarUsuariosDeOrganos(idOrgs);
		assertEquals(2, usuarios.size());
		assertEquals("USR1", usuarios.get(0));
		assertEquals("USR2", usuarios.get(1));
	}

}
