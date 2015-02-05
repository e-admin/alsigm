package common.bi;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import se.usuarios.AppPermissions;
import se.usuarios.AppUserRI;
import se.usuarios.AppUserRIFactory;
import se.usuarios.ServiceClient;
import util.CollectionUtils;
import auditoria.logger.IArchivoLogger;

import common.db.ITransactionManager;
import common.db.TransactionManagerFactory;
import common.exceptions.SecurityException;
import common.security.ActionObject;
import common.security.SecurityManagerBase;

import deposito.model.GestorEstructuraDepositoBI;

public abstract class ServiceBase implements IServiceBase {

	private final static Logger logger = Logger.getLogger(ServiceBase.class);

	private ITransactionManager txManager = null;
	private ServiceClient serviceClient = null;
	private ServiceSession serviceSession = null;
	private SecurityManagerBase securityManager = null;

	/** Logger de auditoría */
	private IArchivoLogger sLogger = null;

	protected ServiceBase() {
	}

	protected ServiceBase(ServiceClient serviceClient) {
		this.serviceClient = serviceClient;
		txManager = TransactionManagerFactory.getTransactionManager();
	}

	protected ServiceBase(ServiceClient serviceClient,
			ITransactionManager txManager) {
		this.serviceClient = serviceClient;
		this.txManager = txManager;
	}

	protected ServiceBase(ServiceClient serviceClient,
			ITransactionManager txManager, SecurityManagerBase smb) {
		this.serviceClient = serviceClient;
		this.txManager = txManager;
		this.securityManager = smb;
	}

	protected void setTransactionManager(ITransactionManager txManager) {
		this.txManager = txManager;
	}

	public ITransactionManager getTransactionManager() {
		return this.txManager;
	}

	protected void setServiceClient(ServiceClient serviceClient) {
		this.serviceClient = serviceClient;
	}

	public ServiceClient getServiceClient() {
		return serviceClient;
	}

	public ServiceSession getServiceSession() {
		return serviceSession;
	}

	public ServiceRepository getServiceRepository() {
		return ServiceRepository.getInstance(serviceSession);
	}

	public void attachToSession(ServiceSession serviceSession) {
		this.serviceSession = serviceSession;
		this.txManager = serviceSession.getTransactionManager();
		this.serviceClient = serviceSession.getSessionOwner();
	}

	protected GestionCuadroClasificacionBI getGestionCuadroClasificacionBI() {
		return ServiceRepository.getInstance(getServiceSession())
				.lookupGestionCuadroClasificacionBI();
	}

	public GestionSeriesBI getGestionSeriesBI() {
		return ServiceRepository.getInstance(getServiceSession())
				.lookupGestionSeriesBI();
	}

	public GestionSolicitudesBI getGestionSolicitudesBI() {
		return ServiceRepository.getInstance(getServiceSession())
				.lookupGestionSolicitudesBI();
	}

	public GestionFondosBI getGestionFondosBI() {
		return ServiceRepository.getInstance(getServiceSession())
				.lookupGestionFondosBI();
	}

	public GestionAuditoriaBI getGestionAuditoriaBI() {
		return ServiceRepository.getInstance(getServiceSession())
				.lookupGestionAuditoriaBI();
	}

	public GestionPrevisionesBI getGestionPrevisionesBI() {
		return ServiceRepository.getInstance(getServiceSession())
				.lookupGestionPrevisionesBI();
	}

	public GestionRelacionesEntregaBI getGestionRelacionesBI() {
		return ServiceRepository.getInstance(getServiceSession())
				.lookupGestionRelacionesBI();
	}

	public GestionRelacionesEACRBI getGestionRelacionEACRBI() {
		return ServiceRepository.getInstance(getServiceSession())
				.lookupGestionRelacionesEACRBI();
	}

	public GestionControlUsuariosBI getGestionControlUsusarios() {
		return ServiceRepository.getInstance(getServiceSession())
				.lookupGestionControlUsuariosBI();
	}

	public AppUserRI getAppUserRI(HttpServletRequest request) {
		return AppUserRIFactory.createAppUserRI();
	}

	public GestorEstructuraDepositoBI getGestorEstructuraDepositoBI() {
		return ServiceRepository.getInstance(getServiceSession())
				.lookupGestorEstructuraDepositoBI();
	}

	public GestionUnidadDocumentalBI getGestionUnidadDocumentalBI() {
		return ServiceRepository.getInstance(getServiceSession())
				.lookupGestionUnidadDocumentalBI();
	}

	public GestionPrestamosBI getGestionPrestamosBI() {
		return ServiceRepository.getInstance(getServiceSession())
				.lookupGestionPrestamosBI();
	}

	public GestionConsultasBI getGestionConsultasBI() {
		return ServiceRepository.getInstance(getServiceSession())
				.lookupGestionConsultasBI();
	}

	public GestionDocumentosElectronicosBI getGestionDocumentosElectronicosBI() {
		return ServiceRepository.getInstance(getServiceSession())
				.lookupGestionDocumentosElectronicosBI();
	}

	public GestionDocumentosVitalesBI getGestionDocumentosVitalesBI() {
		return ServiceRepository.getInstance(getServiceSession())
				.lookupGestionDocumentosVitalesBI();
	}

	public GestionValoracionBI getGestionValoracionBI() {
		return ServiceRepository.getInstance(getServiceSession())
				.lookupGestionValoracionBI();
	}

	public GestionEliminacionBI getGestionEliminacionBI() {
		return ServiceRepository.getInstance(getServiceSession())
				.lookupGestionEliminacionBI();
	}

	public GestionDescripcionBI getGestionDescripcionBI() {
		return ServiceRepository.getInstance(getServiceSession())
				.lookupGestionDescripcionBI();
	}

	public GestionTransferenciasElectronicasBI getGestionTransferenciasElectronicasBI(){
		return ServiceRepository.getInstance(getServiceSession()).lookupGestionTransferenciasElectronicasBI();
	}


	/*****************/

	protected void iniciarTransaccion() {
		txManager.iniciarTransaccion();
	}

	protected void commit() {
		try {
			txManager.commit();
		} catch (Exception e) {
			try {
				rollback();
			} catch (Exception re) {
			}
		}
	}

	protected void rollback() {
		try {
			txManager.rollback();
		} catch (Exception e) {
			logger.error(e);
		}
	}

	protected void liberarConexion() {
		try {
			txManager.liberarConexion();
		} catch (Exception e) {
			logger.error(e);

		}
	}

	public IArchivoLogger getLogger() {
		return sLogger;
	}

	public void setLogger(IArchivoLogger logger) {
		sLogger = logger;
	}

	public void setSecurityManager(SecurityManagerBase securityManager) {
		this.securityManager = securityManager;
	}

	public SecurityManagerBase getSecurityManager() {
		if (securityManager == null)
			logger.error("Objeto security manager no establecido en el servicio");
		return securityManager;
	}

	public void checkPermission(ActionObject action) {
		if (securityManager != null)
			securityManager.check(action, serviceClient);
		else
			throw new SecurityException(
					"Objeto security manager no establecido en el servicio");
	}

	public void checkPermission(String permission) {
		if (serviceClient != null) {
			if (!serviceClient.hasPermission(permission))
				throw new SecurityException(
						"Se ha intentando realizar una operación no permitida");
		} else
			throw new SecurityException(
					"La operación no puede ser invocada en un servicio de forma anónima");

	}

	public void checkSpecificPermission(String acl, String permission) {
		if (serviceClient != null) {
			if (!serviceClient
					.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)) {
				List permisosEspecificos = (List) serviceClient.getAcls().get(
						acl);
				if (CollectionUtils.isEmpty(permisosEspecificos)
						|| !permisosEspecificos.contains(permission))
					throw new SecurityException(
							"Se ha intentando realizar una operación no permitida");
			}
		} else
			throw new SecurityException(
					"La operación no puede ser invocada en un servicio de forma anónima");

	}

	public Locale getLocale() {
		if (getServiceClient() != null) {
			return getServiceClient().getLocale();
		}
		return null;
	}

}