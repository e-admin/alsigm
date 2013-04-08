package es.ieci.tecdoc.isicres.api.business.manager.impl;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ieci.tecdoc.common.keys.ISicresKeys;

import es.ieci.tecdoc.isicres.api.business.dao.test.IsicresBaseDatabaseTestCase;
import es.ieci.tecdoc.isicres.api.business.exception.DistributionException;
import es.ieci.tecdoc.isicres.api.business.exception.UnknownDistributionException;
import es.ieci.tecdoc.isicres.api.business.manager.DistribucionManager;
import es.ieci.tecdoc.isicres.api.business.manager.LoginManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.BaseRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaDistribucionVO;
import es.ieci.tecdoc.isicres.api.business.vo.DistribucionVO;
import es.ieci.tecdoc.isicres.api.business.vo.ResultadoBusquedaDistribucionVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.enums.EstadoDistribucionEnum;
import es.ieci.tecdoc.isicres.api.business.vo.enums.EstadoRegistroEnum;

public class DistribucionManagerImplTest extends IsicresBaseDatabaseTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		// setRegistroManager(new RegistroManagerImpl());
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				getConfigLocations());

		setDistribucionManager((DistribucionManager) applicationContext
				.getBean("distribucionManager"));
		setLoginManager(new LoginLegacyManagerImpl());
	}

	public void testGetInputDistributions() {

		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO();
		criterio.setLimit(new Long(1000));
		criterio.setOffset(new Long(0));
		criterio.setEstado(EstadoDistribucionEnum.RECHAZADO);

		ResultadoBusquedaDistribucionVO inputDistributions = getDistribucionManager()
				.getInputDistributions(usuario, criterio);

		Assert.assertNotNull(inputDistributions.getDistributions());
		Assert.assertEquals(1, inputDistributions.getDistributions().size());

		usuario.setLoginName("operador");
		usuario.setPassword("operador");
		usuario = getLoginManager().login(usuario);

		inputDistributions = getDistribucionManager().getInputDistributions(
				usuario, criterio);

		Assert.assertNotNull(inputDistributions.getDistributions());
		Assert.assertEquals(2, inputDistributions.getDistributions().size());
	}

	public void testGetInputDistributionsByUserId() {

		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO();
		criterio.setLimit(new Long(1000));
		criterio.setOffset(new Long(0));
		criterio.setEstado(EstadoDistribucionEnum.RECHAZADO);
		criterio.setUser("1");

		ResultadoBusquedaDistribucionVO inputDistributions = getDistribucionManager()
				.getInputDistributions(usuario, criterio);

		Assert.assertNotNull(inputDistributions.getDistributions());
		Assert.assertEquals(2, inputDistributions.getDistributions().size());

	}

	public void testGetOutputDistributionsByUserId() {

		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO();
		criterio.setLimit(new Long(1000));
		criterio.setOffset(new Long(0));
		criterio.setEstado(EstadoDistribucionEnum.RECHAZADO);
		criterio.setUser("1");

		ResultadoBusquedaDistribucionVO outputDistributions = getDistribucionManager()
				.getOutputDistributions(usuario, criterio);

		Assert.assertNotNull(outputDistributions.getDistributions());
		Assert.assertEquals(3, outputDistributions.getDistributions().size());

	}

	public void testGetDistributionByRegNumber() {
		UsuarioVO usuario = new UsuarioVO();
		usuario.setLoginName("operador");
		usuario.setPassword("operador");
		usuario.getConfiguracionUsuario().setIdEntidad(
				ISicresKeys.IS_INVESICRES);
		usuario = getLoginManager().login(usuario);

		BaseRegistroVO registro = new BaseRegistroVO();
		registro.setNumeroRegistro("201000100000004");

		DistribucionVO distribution = ((DistribucionManagerImpl) getDistribucionManager())
				.getDistributionFromInputBox(
						usuario,
						registro,
						new EstadoDistribucionEnum[] { EstadoDistribucionEnum.PENDIENTE });

		Assert.assertNotNull(distribution);
		Assert.assertEquals("AHI TE VA", distribution.getMensajeDistribucion());
		assertEquals(new DateTime(2010, 6, 4, 17, 22, 06, 32).toDate(),
				distribution.getFechaDistribucion());
		assertEquals(1, distribution.getEstado().getIdEstado().getValue());
		assertEquals(new DateTime(2010, 6, 4, 17, 22, 06, 32).toDate(),
				distribution.getEstado().getFechaEstado());
		assertEquals("2", distribution.getOrigenDistribucion().getId());
		assertEquals("2", distribution.getOrigenDistribucion().getTipo());
		assertEquals("1", distribution.getDestinoDistribucion().getId());
		assertEquals("1", distribution.getDestinoDistribucion().getTipo());

		Assert.assertEquals("201000100000004", distribution.getRegistro()
				.getNumeroRegistro());
		Assert.assertEquals(EstadoRegistroEnum.COMPLETO, distribution
				.getRegistro().getEstado());
		assertEquals(new DateTime(2010, 6, 3, 9, 26, 28, 0).toDate(),
				distribution.getRegistro().getFechaRegistro());
		assertEquals(new DateTime(2010, 6, 3, 0, 0, 0, 0).toDate(),
				distribution.getRegistro().getFechaAlta());
		assertEquals("2929", distribution.getRegistro()
				.getUnidadAdministrativaOrigen().getId());
		assertEquals("4887", distribution.getRegistro()
				.getUnidadAdministrativaDestino().getId());
		assertEquals("1", distribution.getRegistro().getOficinaRegistro()
				.getId());

	}

	public void testSigemAcceptDistribution() {
		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		DistribucionVO distribucion = new DistribucionVO();
		BaseRegistroVO registro = new BaseRegistroVO();
		registro.setNumeroRegistro("201000200000001");
		distribucion.setRegistro(registro);
		getDistribucionManager().acceptDistribution(usuario, distribucion);

		CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO();
		criterio.setEstado(EstadoDistribucionEnum.ACEPTADO);
		ResultadoBusquedaDistribucionVO inputDistributions = getDistribucionManager()
				.getInputDistributions(usuario, criterio);

		assertNotNull(inputDistributions.getDistributions());
		assertEquals(1, inputDistributions.getDistributions().size());
		assertEquals(EstadoDistribucionEnum.ACEPTADO,
				((DistribucionVO) inputDistributions.getDistributions().get(0))
						.getEstado());
	}

	public void testSigemAcceptDistributionOnBook() {
		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		DistribucionVO distribucion = new DistribucionVO();
		BaseRegistroVO registro = new BaseRegistroVO();
		registro.setNumeroRegistro("201000200000001");
		distribucion.setRegistro(registro);

		BaseLibroVO libro = new BaseLibroVO();
		libro.setId("2");

		getDistribucionManager().acceptDistribution(usuario, distribucion,
				libro);

		CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO();
		criterio.setEstado(EstadoDistribucionEnum.ACEPTADO);
		ResultadoBusquedaDistribucionVO inputDistributions = getDistribucionManager()
				.getInputDistributions(usuario, criterio);

		assertNotNull(inputDistributions.getDistributions());
		assertEquals(1, inputDistributions.getDistributions().size());
		assertEquals(EstadoDistribucionEnum.ACEPTADO,
				((DistribucionVO) inputDistributions.getDistributions().get(0))
						.getEstado());
	}

	public void testSigemArchiveDistribution() {
		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		DistribucionVO distribucion = new DistribucionVO();
		BaseRegistroVO registro = new BaseRegistroVO();
		registro.setNumeroRegistro("201000200000001");
		distribucion.setRegistro(registro);
		getDistribucionManager().archiveDistribution(usuario, distribucion);

		CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO();
		criterio.setEstado(EstadoDistribucionEnum.ACEPTADO);
		ResultadoBusquedaDistribucionVO inputDistributions = getDistribucionManager()
				.getInputDistributions(usuario, criterio);

		assertNotNull(inputDistributions.getDistributions());
		assertEquals(0, inputDistributions.getDistributions().size());

		criterio.setEstado(EstadoDistribucionEnum.ARCHIVADO);
		inputDistributions = getDistribucionManager().getInputDistributions(
				usuario, criterio);
		assertNotNull(inputDistributions);
		assertEquals(1, inputDistributions.getDistributions().size());
		assertEquals(EstadoDistribucionEnum.ARCHIVADO,
				((DistribucionVO) inputDistributions.getDistributions().get(0))
						.getEstado().getIdEstado());
	}

	private UsuarioVO buildSigemUser() {
		UsuarioVO usuario = new UsuarioVO();
		usuario.setLoginName("sigem");
		usuario.setPassword("sigem");
		usuario.getConfiguracionUsuario().setIdEntidad(
				ISicresKeys.IS_INVESICRES);
		return usuario;
	}

	public void testSigemRejectDistribution() {
		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		DistribucionVO distribucion = new DistribucionVO();
		BaseRegistroVO registro = new BaseRegistroVO();
		registro.setNumeroRegistro("201000200000003");
		distribucion.setRegistro(registro);
		distribucion.setMensajeDistribucion("Esto no es para mí");

		getDistribucionManager().rejectDistribution(usuario, distribucion);

		CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO();
		criterio.setEstado(EstadoDistribucionEnum.RECHAZADO);
		ResultadoBusquedaDistribucionVO inputDistributions = getDistribucionManager()
				.getInputDistributions(usuario, criterio);

		assertNotNull(inputDistributions.getDistributions());
		assertEquals(1, inputDistributions.getDistributions().size());

	}

	public void testSigemRedistributeRejectedDistribution() {
		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		DistribucionVO distribucion = new DistribucionVO();
		distribucion.getRegistro().setNumeroRegistro("201000200000001");
		distribucion.getDestinoDistribucion().setId("002");

		getDistribucionManager().redistributeOutputDistribution(usuario,
				distribucion);

	}

	public void testSigemRedistributeInputPendingDistribution() {
		UsuarioVO usuario = buildOperadorUser();

		usuario = getLoginManager().login(usuario);

		DistribucionVO distribucion = new DistribucionVO();
		distribucion.getRegistro().setNumeroRegistro("201000200000001");
		distribucion.getDestinoDistribucion().setId("001");

		getDistribucionManager().redistributeInputDistribution(usuario,
				distribucion);

	}

	private UsuarioVO buildOperadorUser() {
		UsuarioVO usuario = new UsuarioVO();
		usuario.setLoginName("operador");
		usuario.setPassword("operador");
		usuario.getConfiguracionUsuario().setIdEntidad(
				ISicresKeys.IS_INVESICRES);

		return usuario;
	}

	public void testAcceptInvalidState() {

		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		DistribucionVO distribucion = new DistribucionVO();
		distribucion.getRegistro().setNumeroRegistro("201000200000005");

		try {
			getDistribucionManager().acceptDistribution(usuario, distribucion);
		} catch (DistributionException de) {
			assertTrue(de.getCause() instanceof UnknownDistributionException);
		}

	}

	public void testSigemInputDistributionsInPendingState() {
		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO();
		criterio.setEstado(EstadoDistribucionEnum.RECHAZADO);

		ResultadoBusquedaDistribucionVO inputDistributions = getDistribucionManager()
				.getInputDistributions(usuario, criterio);

		assertNotNull(inputDistributions.getDistributions());
		assertEquals(1, inputDistributions.getDistributions().size());
	}

	public void testSigemOutputDistributionsInPendingState() {
		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO();
		criterio.setEstado(EstadoDistribucionEnum.PENDIENTE);

		ResultadoBusquedaDistribucionVO outputDistributions = getDistribucionManager()
				.getOutputDistributions(usuario, criterio);

		assertNotNull(outputDistributions.getDistributions());
		assertEquals(1, outputDistributions.getDistributions().size());
	}

	public void testSigemOutputDistributionsInRejectedState() {
		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		CriterioBusquedaDistribucionVO criterio = new CriterioBusquedaDistribucionVO();
		criterio.setEstado(EstadoDistribucionEnum.RECHAZADO);

		ResultadoBusquedaDistribucionVO outputDistributions = getDistribucionManager()
				.getOutputDistributions(usuario, criterio);

		assertNotNull(outputDistributions.getDistributions());
		assertEquals(3, outputDistributions.getDistributions().size());
	}

	public DistribucionManager getDistribucionManager() {
		return distribucionManager;
	}

	public void setDistribucionManager(DistribucionManager distribucionManager) {
		this.distribucionManager = distribucionManager;
	}

	public LoginManager getLoginManager() {
		return loginManager;
	}

	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}

	// Members
	protected DistribucionManager distribucionManager;

	protected LoginManager loginManager;

	protected String[] getConfigLocations() {
		return new String[] { "/beans/datasourceTest-beans.xml",
				"/beans/ISicres-Api/applicationContext.xml",
				"/beans/ISicres-Api/dao-beans.xml",
				"/beans/ISicres-Api/manager-beans.xml",
				"/beans/ISicres-Api/transaction-beans.xml" };
	}
}
