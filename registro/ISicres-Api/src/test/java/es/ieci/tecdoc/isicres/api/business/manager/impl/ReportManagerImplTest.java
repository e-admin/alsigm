package es.ieci.tecdoc.isicres.api.business.manager.impl;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ieci.tecdoc.common.keys.ISicresKeys;

import es.ieci.tecdoc.isicres.api.business.dao.test.IsicresBaseDatabaseTestCase;
import es.ieci.tecdoc.isicres.api.business.manager.LoginManager;
import es.ieci.tecdoc.isicres.api.business.manager.ReportManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.IdentificadorRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.PlantillaInformeVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.enums.TipoPlantillaInformeEnum;

public class ReportManagerImplTest extends IsicresBaseDatabaseTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				getConfigLocations());

		setReportManager((ReportManager) applicationContext
				.getBean("reportManager"));
		setLoginManager(new LoginLegacyManagerImpl());
	}

	/**
	 * Verifica la recuperación de los certificados del usuario <i>sigem</i>.
	 */
	public void testGetSigemCertificates() {

		UsuarioVO usuario = new UsuarioVO();
		usuario.setLoginName("sigem");
		usuario.setPassword("sigem");
		usuario.getConfiguracionUsuario().setIdEntidad(
				ISicresKeys.IS_INVESICRES);

		usuario = getLoginManager().login(usuario);

		BaseLibroVO libro = new BaseLibroVO("1");
		List certificates = getReportManager().getCertificates(usuario, libro);

		checkCertificates(usuario, libro, certificates);
	}

	/**
	 * Verifica la recuperación de los certificados del usuario <i>operador</i>.
	 */
	public void testGetOperadorCertificates() {

		UsuarioVO usuario = new UsuarioVO();
		usuario.setLoginName("operador");
		usuario.setPassword("operador");
		usuario.getConfiguracionUsuario().setIdEntidad(
				ISicresKeys.IS_INVESICRES);

		usuario = getLoginManager().login(usuario);

		BaseLibroVO libro = new BaseLibroVO("1");
		List certificates = getReportManager().getCertificates(usuario, libro);

		checkCertificates(usuario, libro, certificates);
	}

	/**
	 * Verifica las condiciones que deben cumplir los certificados
	 * <code>certificates</code> del usuario <code>usuario</code> para el libro
	 * <code>libro</code>.
	 * 
	 * @param usuario
	 * @param libro
	 * @param certificates
	 */
	private void checkCertificates(UsuarioVO usuario, BaseLibroVO libro,
			List certificates) {
		assertNotNull(certificates);
		assertEquals(1, certificates.size());
		PlantillaInformeVO pi = (PlantillaInformeVO) certificates.get(0);
		assertEquals("4", pi.getId());
		assertEquals("120.zip", pi.getNombre());
		assertEquals(TipoPlantillaInformeEnum.CERTIFICADO_REGISTRO, pi
				.getTipo());

		libro.setId("2");
		certificates = getReportManager().getCertificates(usuario, libro);

		assertNotNull(certificates);
		assertEquals(1, certificates.size());
		pi = (PlantillaInformeVO) certificates.get(0);
		assertEquals("8", pi.getId());
		assertEquals("220.zip", pi.getNombre());
		assertEquals(TipoPlantillaInformeEnum.CERTIFICADO_REGISTRO, pi
				.getTipo());
	}

	/**
	 * Verifica la correcta generación de un certificado de entrada para un
	 * registro de un libro de entrada.
	 */
	public void testSigemInputCertificate() {
		UsuarioVO usuario = new UsuarioVO();
		usuario.setLoginName("sigem");
		usuario.setPassword("sigem");
		usuario.getConfiguracionUsuario().setIdEntidad(
				ISicresKeys.IS_INVESICRES);

		usuario = getLoginManager().login(usuario);

		IdentificadorRegistroVO identificadorRegistro = new IdentificadorRegistroVO(
				"1", "1");
		byte[] inputCertificate = getReportManager().createInputCertificate(
				usuario, identificadorRegistro, "4");

		assertNotNull(inputCertificate);
	}

	/**
	 * Verifica que la solicitud de generación de un certificado de entrada con
	 * un identificador de registro que no existe genera una
	 * <code>java.lang.IllegalArgumentException</code>.
	 */
	public void testSigemInputCertificateWithUnknownRegister() {
		UsuarioVO usuario = new UsuarioVO();
		usuario.setLoginName("sigem");
		usuario.setPassword("sigem");
		usuario.getConfiguracionUsuario().setIdEntidad(
				ISicresKeys.IS_INVESICRES);

		usuario = getLoginManager().login(usuario);

		IdentificadorRegistroVO identificadorRegistro = new IdentificadorRegistroVO(
				"9", "1");
		byte[] inputCertificate = null;
		try {
			inputCertificate = getReportManager().createInputCertificate(
					usuario, identificadorRegistro, "4");
		} catch (IllegalArgumentException iae) {
			assertNull(inputCertificate);
			return;
		}
		fail();

	}

	/**
	 * Verifica que la solicitud de generación de un certificado de entrada con
	 * un identificador de libro de salida genera una
	 * <code>java.lang.IllegalArgumentException</code>.
	 */
	public void testSigemInputCertificateWithWrongBookType() {
		UsuarioVO usuario = new UsuarioVO();
		usuario.setLoginName("sigem");
		usuario.setPassword("sigem");
		usuario.getConfiguracionUsuario().setIdEntidad(
				ISicresKeys.IS_INVESICRES);

		usuario = getLoginManager().login(usuario);

		IdentificadorRegistroVO identificadorRegistro = new IdentificadorRegistroVO(
				"1", "2");
		byte[] inputCertificate = null;
		try {
			inputCertificate = getReportManager().createInputCertificate(
					usuario, identificadorRegistro, "4");
		} catch (IllegalArgumentException iae) {
			assertNull(inputCertificate);
			return;
		}
		fail();

	}

	/**
	 * Verifica que la solicitud de generación de un certificado de entrada con
	 * un identificador de informe que no existe genera una
	 * <code>java.lang.IllegalArgumentException</code>.
	 */
	public void testSigemInputCertificateWithUnknownReportTemplate() {
		UsuarioVO usuario = new UsuarioVO();
		usuario.setLoginName("sigem");
		usuario.setPassword("sigem");
		usuario.getConfiguracionUsuario().setIdEntidad(
				ISicresKeys.IS_INVESICRES);

		usuario = getLoginManager().login(usuario);

		IdentificadorRegistroVO identificadorRegistro = new IdentificadorRegistroVO(
				"1", "1");
		byte[] inputCertificate = null;
		try {
			inputCertificate = getReportManager().createInputCertificate(
					usuario, identificadorRegistro, "14");
		} catch (IllegalArgumentException iae) {
			assertNull(inputCertificate);
			return;
		}
		fail();

	}

	/**
	 * Verifica que la solicitud de generación de un certificado de entrada con
	 * un identificador de informe de un tipo distinto a certificado genera una
	 * <code>java.lang.IllegalArgumentException</code>.
	 */
	public void testSigemInputCertificateWithWrongReportTemplateType() {
		UsuarioVO usuario = new UsuarioVO();
		usuario.setLoginName("sigem");
		usuario.setPassword("sigem");
		usuario.getConfiguracionUsuario().setIdEntidad(
				ISicresKeys.IS_INVESICRES);

		usuario = getLoginManager().login(usuario);

		IdentificadorRegistroVO identificadorRegistro = new IdentificadorRegistroVO(
				"1", "1");
		byte[] inputCertificate = null;
		try {
			inputCertificate = getReportManager().createInputCertificate(
					usuario, identificadorRegistro, "1");
		} catch (IllegalArgumentException iae) {
			assertNull(inputCertificate);
			return;
		}
		fail();

	}

	/**
	 * Verifica la generación de un certificado de salida para el usuario
	 * <i>operador</i>.
	 */
	public void testOperadorOutputCertificate() {
		UsuarioVO usuario = new UsuarioVO();
		usuario.setLoginName("operador");
		usuario.setPassword("operador");
		usuario.getConfiguracionUsuario().setIdEntidad(
				ISicresKeys.IS_INVESICRES);

		usuario = getLoginManager().login(usuario);

		IdentificadorRegistroVO identificadorRegistro = new IdentificadorRegistroVO(
				"1", "2");
		byte[] outputCertificate = null;
		outputCertificate = getReportManager().createOutputCertificate(usuario,
				identificadorRegistro, "8");
		assertNotNull(outputCertificate);
	}

	public ReportManager getReportManager() {
		return reportManager;
	}

	public void setReportManager(ReportManager reportManager) {
		this.reportManager = reportManager;
	}

	public LoginManager getLoginManager() {
		return loginManager;
	}

	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}

	protected String[] getConfigLocations() {
		return new String[] { "/beans/datasourceTest-beans.xml",
				"/beans/ISicres-Api/applicationContext.xml",
				"/beans/ISicres-Api/dao-beans.xml",
				"/beans/ISicres-Api/manager-beans.xml",
				"/beans/ISicres-Api/transaction-beans.xml" };
	}

	protected ReportManager reportManager;

	protected LoginManager loginManager;
}
