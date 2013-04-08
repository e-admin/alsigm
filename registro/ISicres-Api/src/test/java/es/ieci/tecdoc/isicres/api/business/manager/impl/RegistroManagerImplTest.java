package es.ieci.tecdoc.isicres.api.business.manager.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.JSqlParser;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.AxSfOut;
import com.ieci.tecdoc.common.isicres.AxSfQuery;
import com.ieci.tecdoc.common.isicres.AxSfQueryField;
import com.ieci.tecdoc.common.isicres.AxSfQueryResults;
import com.ieci.tecdoc.common.isicres.Keys;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.idoc.utils.ConfiguratorInvesicres;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.folder.FolderSession;

import es.ieci.tecdoc.isicres.api.business.dao.test.IsicresBaseDatabaseTestCase;
import es.ieci.tecdoc.isicres.api.business.manager.LoginManager;
import es.ieci.tecdoc.isicres.api.business.manager.RegistroManager;
import es.ieci.tecdoc.isicres.api.business.vo.CampoGenericoRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.DocumentoFisicoVO;
import es.ieci.tecdoc.isicres.api.business.vo.DocumentoRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.IdentificadorRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.PaginaDocumentoRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroEntradaVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroOriginalVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroSalidaVO;
import es.ieci.tecdoc.isicres.api.business.vo.TipoAsuntoVO;
import es.ieci.tecdoc.isicres.api.business.vo.UnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.enums.TipoLibroEnum;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class RegistroManagerImplTest extends IsicresBaseDatabaseTestCase {

	protected void setUp() throws Exception {
		super.setUp();

		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				getConfigLocations());

		setRegistroManager((RegistroManager) applicationContext
				.getBean("registroManager"));

		setLoginManager(new LoginLegacyManagerImpl());
	}

	public void testAttachPage() throws Exception {
		// XXX: Esto no tiene sentido. Cierra la session sin saber por qué.
		ConfiguratorInvesicres.getInstance(ISicresKeys.IS_INVESICRES);

		IdentificadorRegistroVO id = new IdentificadorRegistroVO();
		id.setIdLibro("2");
		id.setIdRegistro("1");
		DocumentoRegistroVO documento = new DocumentoRegistroVO();
		documento.setIdRegistro(id);
		documento.setName("foo");
		PaginaDocumentoRegistroVO pagina = new PaginaDocumentoRegistroVO();
		pagina.setIdDocumentoRegistro(id.getIdRegistro());
		pagina.setName("foo");
		pagina.setNumeroPagina(new Integer(1));
		DocumentoFisicoVO df = new DocumentoFisicoVO();
		df.setContent(new String("joljdfajfalskdf99weu").getBytes());
		df.setExtension("foo");
		df.setName("bar.foo");
		pagina.setDocumentoFisico(df);
		documento.setPaginas(Arrays
				.asList(new PaginaDocumentoRegistroVO[] { pagina }));
		UsuarioVO usuario = new UsuarioVO();
		usuario.setLoginName("operador");
		usuario.setPassword("operador");
		usuario.getConfiguracionUsuario().getOficina().setCodigoOficina("001");

		usuario = getLoginManager().login(usuario);

		DocumentoRegistroVO result = getRegistroManager().attachDocument(id,
				documento, usuario);

		Assert.assertNotNull(result);
	}

	public void testUpdateRegistroEntrada() throws Exception {

		UsuarioVO usuario = new UsuarioVO();
		usuario.setLoginName("operador");
		usuario.setPassword("operador");

		IdentificadorRegistroVO id = new IdentificadorRegistroVO("1", "1");
		List camposGenericos = new ArrayList();
		camposGenericos.add(new CampoGenericoRegistroVO("1",
				"Nuevo resumen del asiento"));
		camposGenericos.add(new CampoGenericoRegistroVO("17",
				"Nuevo resumen del asiento [" + new Date().toString() + "]"));

		getRegistroManager()
				.updateRegistroEntrada(usuario, id, camposGenericos);

	}

	public void testOperadorCancelRegister() {
		UsuarioVO usuario = new UsuarioVO();
		usuario.setLoginName("operador");
		usuario.setPassword("operador");
		usuario.getConfiguracionUsuario().setIdEntidad(
				ISicresKeys.IS_INVESICRES);

		usuario = getLoginManager().login(usuario);

		IdentificadorRegistroVO id = new IdentificadorRegistroVO("1", "1");
		getRegistroManager().cancelRegistroEntradaById(usuario, id);

		// EstadoRegistroVO estadoRegistro = getRegistroManager()
		// .getEstadoRegistro(usuario, id);
		//
		// assertNotNull(estadoRegistro);
		// assertEquals("", estadoRegistro.getIdEstado());
	}

	public void testOperadorCancelLockedRegister() throws BookException,
			SessionException, ValidationException {
		UsuarioVO operador = new UsuarioVO();
		operador.setLoginName("operador");
		operador.setPassword("operador");
		operador.getConfiguracionUsuario().setIdEntidad(
				ISicresKeys.IS_INVESICRES);

		operador = getLoginManager().login(operador);

		UsuarioVO sigem = new UsuarioVO();
		sigem.setLoginName("sigem");
		sigem.setPassword("sigem");
		sigem.getConfiguracionUsuario().setIdEntidad(ISicresKeys.IS_INVESICRES);

		sigem = getLoginManager().login(sigem);

		BookSession.openBook(sigem.getConfiguracionUsuario().getSessionID(),
				new Integer(1), sigem.getConfiguracionUsuario().getIdEntidad());

		FolderSession.lockFolder(
				sigem.getConfiguracionUsuario().getSessionID(), new Integer(1),
				new Integer(1).intValue(), sigem.getConfiguracionUsuario()
						.getIdEntidad());

		IdentificadorRegistroVO id = new IdentificadorRegistroVO("1", "1");
		getRegistroManager().cancelRegistroEntradaById(operador, id);
	}

	public void testFindRegistroEntradaById() {
		UsuarioVO usuario = new UsuarioVO();
		usuario.setLoginName("sigem");
		usuario.setPassword("sigem");

		usuario = getLoginManager().login(usuario);

		IdentificadorRegistroVO id = new IdentificadorRegistroVO("2", "9");
		// IdentificadorRegistroVO id = new IdentificadorRegistroVO("3", "9");
		RegistroEntradaVO register = getRegistroManager()
				.findRegistroEntradaById(usuario, id);
		assertNotNull(register);
	}

	public void testFindRegistroSalidaById() {
		UsuarioVO usuario = new UsuarioVO();
		usuario.setLoginName("sigem");
		usuario.setPassword("sigem");

		usuario = getLoginManager().login(usuario);

		// IdentificadorRegistroVO id = new IdentificadorRegistroVO("1", "2");
		IdentificadorRegistroVO id = new IdentificadorRegistroVO("17", "2");
		RegistroSalidaVO result = getRegistroManager().findRegistroSalidaById(
				usuario, id);
		assertNotNull(result);
	}

	public void testFindInputRegisterWithSQLCondition() {
		UsuarioVO usuario = new UsuarioVO();
		usuario.setLoginName("operador");
		usuario.setPassword("operador");
		usuario.getConfiguracionUsuario().setIdEntidad(
				ISicresKeys.IS_INVESICRES);

		usuario = getLoginManager().login(usuario);
		int fromIndex = 4;
		int limit = 15;
		Integer bookID = null/* new Integer(1) */;
		String filter = StringUtils.EMPTY/* "fld1<>'201000100000001'" */;

		// AxSfQueryField field = new AxSfQueryField("fld1", "<>",
		// "201000100000001");
		// axsfQuery.addField(field);

		List results = getRegistroManager().queryForAxSfQueryResults(usuario,
				fromIndex, limit, bookID, TipoLibroEnum.ENTRADA, filter);

		Iterator iterator = results.iterator();
		while (iterator.hasNext()) {
			AxSf reg = (AxSf) iterator.next();
			assertEquals(reg.getClass(), AxSfIn.class);
		}

		assertEquals(7, results.size());
		// assertEquals(limit, results.getCurrentResultsSize());
	}

	public void testFindOutputRegisterWithSQLCondition() {
		UsuarioVO usuario = new UsuarioVO();
		usuario.setLoginName("operador");
		usuario.setPassword("operador");
		usuario.getConfiguracionUsuario().setIdEntidad(
				ISicresKeys.IS_INVESICRES);

		usuario = getLoginManager().login(usuario);

		Integer bookId = new Integer(2);

		AxSfQuery axsfQuery = new AxSfQuery(bookId);

		AxSfQueryField field = new AxSfQueryField("fld1", "<>",
				"201000200000001");
		axsfQuery.addField(field);

		List bookIds = new ArrayList();
		bookIds.add(bookId);
		try {
			BookSession.openBook(usuario.getConfiguracionUsuario()
					.getSessionID(), bookId, usuario.getConfiguracionUsuario()
					.getIdEntidad());

			int registersQuery = FolderSession.openRegistersQuery(usuario
					.getConfiguracionUsuario().getSessionID(), axsfQuery,
					bookIds, /* reportOption -> 1=multiples libros */
					new Integer(1), usuario.getConfiguracionUsuario()
							.getIdEntidad());

			AxSfQueryResults results = null;
			if (registersQuery > 0) {
				results = FolderSession.navigateRegistersQuery(usuario
						.getConfiguracionUsuario().getSessionID(), bookId,
						Keys.QUERY_ALL, usuario.getConfiguracionUsuario()
								.getLocale(), usuario.getConfiguracionUsuario()
								.getIdEntidad(), StringUtils.EMPTY);

				FolderSession.closeRegistersQuery(usuario
						.getConfiguracionUsuario().getSessionID(), bookId);

				Iterator iterator = results.getResults().iterator();
				while (iterator.hasNext()) {
					AxSf reg = (AxSf) iterator.next();
					assertEquals(reg.getClass(), AxSfOut.class);
				}

			}

			assertNull(results);
		} catch (Exception e) {
			fail();
		}
	}

	public void testCreateInputRegister() {
		UsuarioVO usuario = new UsuarioVO();
		usuario.setLoginName("operador");
		usuario.setPassword("operador");
		usuario.getConfiguracionUsuario().setIdEntidad(
				ISicresKeys.IS_INVESICRES);

		usuario = getLoginManager().login(usuario);

		RegistroEntradaVO registro = new RegistroEntradaVO();
		registro.setIdLibro("1");
		RegistroOriginalVO registroOriginal = new RegistroOriginalVO();
		registroOriginal.setNumeroRegistroOriginal("201000100000010");
		registroOriginal.setFechaRegistroOriginal(new Date());
		registroOriginal.setTipoRegistroOriginal("1");
		UnidadAdministrativaVO entidadRegistral = new UnidadAdministrativaVO();
		entidadRegistral.setCodigoUnidad("001");
		registroOriginal.setEntidadRegistral(entidadRegistral);
		registro.setRegistroOriginal(registroOriginal);

		UnidadAdministrativaVO unidadAdministrativaDestino = new UnidadAdministrativaVO();
		unidadAdministrativaDestino.setCodigoUnidad("001");
		registro.setUnidadAdministrativaDestino(unidadAdministrativaDestino);

		UnidadAdministrativaVO unidadAdministrativaOrigen = new UnidadAdministrativaVO();
		unidadAdministrativaOrigen.setCodigoUnidad("002");
		registro.setUnidadAdministrativaOrigen(unidadAdministrativaOrigen);

		TipoAsuntoVO tipoAsunto = new TipoAsuntoVO();
		tipoAsunto.setCodigo("TRQS");
		registro.setTipoAsunto(tipoAsunto);

		List documentos = new ArrayList();
		DocumentoRegistroVO documento = new DocumentoRegistroVO();
		documento.setName("arquitectura");
		List paginas = new ArrayList();
		PaginaDocumentoRegistroVO pagina = new PaginaDocumentoRegistroVO();
		pagina.setName("pagina1");
		pagina.setNumeroPagina(new Integer(1));
		DocumentoFisicoVO df = new DocumentoFisicoVO();
		df.setId("29");
		df.setExtension(".png");
		// df.setLocation("29");
		pagina.setDocumentoFisico(df);
		paginas.add(pagina);
		documento.setPaginas(paginas);
		documentos.add(documento);
		registro.setDocumentos(documentos);

		RegistroEntradaVO registroEntrada = getRegistroManager()
				.createRegistroEntrada(usuario, registro);

		assertNotNull(registroEntrada);
	}

	public void testGetPageByIndex() {
		UsuarioVO usuario = new UsuarioVO();
		usuario.setLoginName("operador");
		usuario.setPassword("operador");
		usuario.getConfiguracionUsuario().setIdEntidad(
				ISicresKeys.IS_INVESICRES);

		usuario = getLoginManager().login(usuario);

		IdentificadorRegistroVO identificadorRegistro = new IdentificadorRegistroVO(
				"9", "1");
		byte[] paginaByIndex = getRegistroManager().getPaginaByIndex(
				identificadorRegistro, 1, 1, usuario);

		assertNotNull(paginaByIndex);
		try {
			IOUtils.write(paginaByIndex, new FileOutputStream(new File(
					"c:\\temp\\tmp.tmp")));
		} catch (Exception e) {
			fail();
		}
	}

	public void testGetPageById() {
		UsuarioVO usuario = new UsuarioVO();
		usuario.setLoginName("operador");
		usuario.setPassword("operador");
		usuario.getConfiguracionUsuario().setIdEntidad(
				ISicresKeys.IS_INVESICRES);

		usuario = getLoginManager().login(usuario);

		IdentificadorRegistroVO identificadorRegistro = new IdentificadorRegistroVO(
				"1", "14");
		byte[] paginaByIndex = getRegistroManager().getPaginaById(
				identificadorRegistro, "1", "1", usuario);

		assertNotNull(paginaByIndex);
		try {
			IOUtils.write(paginaByIndex, new FileOutputStream(new File(
					"c:\\temp\\tmp.tmp")));
		} catch (Exception e) {
			fail();
		}
	}

	public void testParseSql() {
		JSqlParser parser = new CCJSqlParserManager();
		String sql = "SELECT * FROM foo WHERE fld1=12 AND fld3='operador'";
		try {
			Statement parse = parser.parse(new StringReader(sql));
			Select select = (Select) parse;
			PlainSelect ps = (PlainSelect) select.getSelectBody();
			ps.getWhere();

			QueryFilterVisitor visitor = new QueryFilterVisitor();
			visitor.visit(ps);

			assertNotNull(visitor.getFilters());
			assertEquals(2, visitor.getFilters().size());
		} catch (JSQLParserException e) {
			e.printStackTrace();
		}
	}

	public RegistroManager getRegistroManager() {
		return registroManager;
	}

	public void setRegistroManager(RegistroManager registroManager) {
		this.registroManager = registroManager;
	}

	public LoginManager getLoginManager() {
		return loginManager;
	}

	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}

	// Members
	protected RegistroManager registroManager;

	protected LoginManager loginManager;

	protected String[] getConfigLocations() {
		return new String[] { "/beans/datasourceTest-beans.xml",
				"/beans/ISicres-Api/applicationContext.xml",
				"/beans/ISicres-Api/dao-beans.xml",
				"/beans/ISicres-Api/manager-beans.xml",
				"/beans/ISicres-Api/transaction-beans.xml" };
	}
}