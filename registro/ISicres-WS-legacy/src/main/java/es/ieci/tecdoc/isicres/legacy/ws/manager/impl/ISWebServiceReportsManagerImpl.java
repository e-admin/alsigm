package es.ieci.tecdoc.isicres.legacy.ws.manager.impl;

import java.util.List;

import es.ieci.tecdoc.isicres.api.business.manager.LoginManager;
import es.ieci.tecdoc.isicres.api.business.manager.ReportManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.IdentificadorRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.PlantillaInformeVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceReportsManager;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.ListOfPlantillaInformeVOToArrayOfWSReportMapper;
import es.ieci.tecdoc.isicres.legacy.ws.service.impl.adapter.UsuarioVOBuilder;
import es.ieci.tecdoc.isicres.ws.legacy.service.reports.ArrayOfWSReport;
import es.ieci.tecdoc.isicres.ws.legacy.service.reports.Security;

/**
 *
 * @author IECISA
 *
 */
public class ISWebServiceReportsManagerImpl implements
		ISWebServiceReportsManager {

	/**
	 * {@inheritDoc}
	 */
	public byte[] createInputRegisterCertificate(int bookIdentification,
			int registerIdentification, int reportIdentification,
			Security security) {

		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		IdentificadorRegistroVO ir = new IdentificadorRegistroVO(String
				.valueOf(registerIdentification), String
				.valueOf(bookIdentification));

		byte[] result = getReportManager().createInputCertificate(usuario, ir,
				String.valueOf(reportIdentification));

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public byte[] createOutputRegisterCertificate(int bookIdentification,
			int registerIdentification, int reportIdentification,
			Security security) {

		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		IdentificadorRegistroVO ir = new IdentificadorRegistroVO(String
				.valueOf(registerIdentification), String
				.valueOf(bookIdentification));

		byte[] result = getReportManager().createOutputCertificate(usuario, ir,
				String.valueOf(reportIdentification));

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public ArrayOfWSReport loadCertificates(int bookIdentification,
			Security security) {

		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		BaseLibroVO libro = new BaseLibroVO(String.valueOf(bookIdentification));

		List<PlantillaInformeVO> certificates = getReportManager()
				.getCertificates(usuario, libro);

		ArrayOfWSReport result = (ArrayOfWSReport) new ListOfPlantillaInformeVOToArrayOfWSReportMapper()
				.map(certificates);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	public LoginManager getLoginManager() {
		return loginManager;
	}

	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}

	public UsuarioVOBuilder getUsuarioBuilder() {
		return usuarioBuilder;
	}

	public void setUsuarioBuilder(UsuarioVOBuilder usuarioAdapter) {
		this.usuarioBuilder = usuarioAdapter;
	}

	public ReportManager getReportManager() {
		return reportManager;
	}

	public void setReportManager(ReportManager reportManager) {
		this.reportManager = reportManager;
	}

	// Members
	protected LoginManager loginManager;

	protected UsuarioVOBuilder usuarioBuilder;

	protected ReportManager reportManager;
}
