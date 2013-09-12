package common.bi;

import java.lang.reflect.Proxy;
import java.util.Properties;

import organizacion.model.bi.GestionOrganizacionBI;
import organizacion.model.bi.GestionOrganizacionBIImpl;

import salas.model.GestionSalasConsultaBI;
import salas.model.GestionSalasConsultaBIImpl;
import se.procedimientos.GestorCatalogo;
import se.procedimientos.GestorCatalogoFactory;
import se.procedimientos.exceptions.GestorCatalogoException;
import se.tramites.SistemaTramitador;
import se.tramites.SistemaTramitadorFactory;
import se.tramites.exceptions.SistemaTramitadorException;
import se.usuarios.ServiceClient;
import solicitudes.consultas.model.GestionConsultasBIImpl;
import solicitudes.model.ConsultaUnidadesDocumentalesBI;
import solicitudes.model.ConsultaUnidadesDocumentalesBIImpl;
import solicitudes.model.GestionMotivosRechazoBImpl;
import solicitudes.model.GestionSolicitudesBIImpl;
import solicitudes.prestamos.model.GestionPrestamosBIImpl;
import system.model.GestionSistemaBImpl;
import transferencias.model.GestionPrevisionesBIImpl;
import transferencias.model.GestionRelacionesEACRBIImpl;
import transferencias.model.GestionRelacionesEntregaBIImpl;
import transferencias.model.GestionTransferenciasElectronicasBIImpl;
import valoracion.model.GestionEliminacionImpl;
import valoracion.model.GestionValoracionImpl;
import auditoria.logger.ArchivoLogger;
import auditoria.model.GestionAuditoriaBImpl;

import common.MultiEntityConstants;
import common.db.DBEntityFactory;
import common.db.DbDataSource;
import common.db.DbDataSourceMultiEntidadImpl;
import common.db.IDBEntityFactory;
import common.db.ITransactionManager;
import common.db.TransactionManagerImpl;
import common.security.FondosSecurityManager;
import common.security.SecurityManagerLocator;
import common.session.model.GestionSessionImpl;
import common.util.StringUtils;

import configuracion.bi.GestionInfoSistemaBI;
import configuracion.model.GestionInfoSistemaBIImpl;
import deposito.model.GestorEstructuraDeposito;
import deposito.model.GestorEstructuraDepositoBI;
import descripcion.model.GestionDescripcionBIImpl;
import docelectronicos.model.GestionDocumentosElectronicosBIImpl;
import docvitales.model.GestionDocumentosVitalesBIImpl;
import fondos.model.CuadroClasificacionBIImpl;
import fondos.model.GestionFondosBIImpl;
import fondos.model.GestionFraccionSerieBIImpl;
import fondos.model.GestionSeriesBIImpl;
import fondos.model.GestionUnidadDocumentalBIImpl;
import gcontrol.model.GestionArchivosBIImpl;
import gcontrol.model.GestionControlUsuariosBIImpl;
import gcontrol.model.GestionNivelesArchivoBIImpl;

/**
 * Repositorio que permite obtener cada uno de los servicios en los que se
 * implementan las funcionalidades ofrecidas por el sistema.
 *
 * @see common.bi.ServiceBase
 */
public class ServiceRepository {

	private ServiceSession serviceSession = null;
	// private static final DbDataSource DEFAULT_DATA_SOURCE = new
	// DbDataSourceImpl();
	private ServiceClient serviceClient = null;

	private ServiceRepository(ServiceSession serviceSession) {
		this.serviceSession = serviceSession;

		if (serviceSession != null)
			serviceClient = serviceSession.getSessionOwner();
	}

	public static ServiceRepository getInstance(ServiceClient serviceClient) {
		DbDataSource dataSource = new DbDataSourceMultiEntidadImpl(
				serviceClient.getEngine());
		ITransactionManager txManager = new TransactionManagerImpl(dataSource);
		ServiceSession session = new DefaultServiceSession(serviceClient,
				txManager);
		return new ServiceRepository(session);
	}

	public static ServiceRepository getInstance(ServiceClient serviceClient,
			ITransactionManager txManager) {
		ServiceSession session = new DefaultServiceSession(serviceClient,
				txManager);
		return new ServiceRepository(session);
	}

	// TODO NACHO Comprobar donde se está usando esto y probar a crearlo con el
	// serviceClient
	public static ServiceRepository getInstance(ServiceSession serviceSession) {
		return new ServiceRepository(serviceSession);
	}

	/**
	 * @return el serviceClient
	 */
	public ServiceClient getServiceClient() {
		return serviceClient;
	}

	/**
	 * @param serviceClient
	 *            el serviceClient a establecer
	 */
	public void setServiceClient(ServiceClient serviceClient) {
		this.serviceClient = serviceClient;
	}

	private IServiceBase configureService(Class serviceBI, IServiceBase service) {
		DbDataSource dataSource = serviceSession.getTransactionManager();
		service.setLogger(ArchivoLogger.getLogger(service.getClass(),
				dataSource));
		return (IServiceBase) Proxy.newProxyInstance(
				serviceBI.getClassLoader(), new Class[] { serviceBI,
						IServiceBase.class }, new ServiceWrapper(service));
	}

	public GestorEstructuraDepositoBI lookupGestorEstructuraDepositoBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		IServiceBase service = new GestorEstructuraDeposito(
				dbEntityFactory.getDepositoDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getElementoAsignableDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getElmentoNoAsignableDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getRelacionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getOcupacionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getHuecoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getUnidadInstalacionDepositoDbEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getUnidadInstalacionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUnidadInstalacionReeaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUdocEnUIDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUDocEnUiDepositoDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getTipoElementoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getFormatoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getOrdenElementoDepositoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDepositoElectronicoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDocDocumentoCFDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNSecUIDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUnidadDocumentalDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDetalleDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getElementoCuadroClasificacionDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getUDocElectronicaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getUnidadDocumentalElectronicaDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getArchivoDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNsecSigNumHuecoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getHistUInstalacionDepositoDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getUiReeaCRDBEntity(serviceSession
						.getTransactionManager())

		);

		service.attachToSession(this.serviceSession);
		service.setSecurityManager(SecurityManagerLocator.loockupDepositoSM());

		return (GestorEstructuraDepositoBI) configureService(
				GestorEstructuraDepositoBI.class, service);
	}

	/**
	 * Obtiene un servicio que nos da acceso a las funcionalidades de negocio a
	 * realizar sobre previsiones de transferencia
	 *
	 * @return {@link GestionPrevisionesBI}
	 */
	public GestionPrevisionesBI lookupGestionPrevisionesBI() {
		// Creacion de la implementacion del servicio. En caso de tratarse de
		// servicios que emplean
		// DBEntities se crearian estas primero y se pasarian al servicio a
		// traves de su constructor
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		IServiceBase service = new GestionPrevisionesBIImpl(
				dbEntityFactory.getPrevisionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getArchivoDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNSecDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDetallePrevisionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getRelacionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getProductorSerieDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getOrganoProductorDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getSerieDBEntity(serviceSession
						.getTransactionManager()));
		// Se asocia el servicio a la que esta vinculada la instancia del
		// repositorio de servicios
		// Para tener disponible el metodo attachToSession solo es necesario que
		// los servicios extiendan de ServiceBase
		service.attachToSession(this.serviceSession);
		service.setSecurityManager(SecurityManagerLocator
				.loockupTransferenciasSM());
		return (GestionPrevisionesBI) configureService(
				GestionPrevisionesBI.class, service);
	}

	public GestionRelacionesEntregaBI lookupGestionRelacionesBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		IServiceBase service = new GestionRelacionesEntregaBIImpl(
				dbEntityFactory.getRelacionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNSecDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getPrevisionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDetallePrevisionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getFormatoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getTransferenciasUnidadDocumentalDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getUdocEnUIDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUnidadInstalacionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNSecUIDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDescriptorDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getValidacionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getProductorSerieDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getOrganoProductorDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUDocEnUiDepositoDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUnidadInstalacionReeaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getUnidadInstalacionDepositoDbEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getHuecoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNSecUDocDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getPermisosListaDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getElementoCuadroClasificacionDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getMapDescUDocDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUDocElectronicaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getUnidadDocumentalElectronicaDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getTextoCortoUDocREDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getTextoLargoUDocREDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNumeroUDocREDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getFechaUDocREDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getReferenciaUDocREDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUDocEnDivisionFSDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDivisionFSDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getArchivoDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNivelCFDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getRevisionDocumentacionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getReferenciaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getHistUInstalacionDepositoDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getUnidadDocumentalDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUiReeaCRDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUDocEnUIReeaCRDBEntity(serviceSession
						.getTransactionManager()));

		service.attachToSession(this.serviceSession);
		service.setSecurityManager(SecurityManagerLocator
				.loockupTransferenciasSM());
		return (GestionRelacionesEntregaBI) configureService(
				GestionRelacionesEntregaBI.class, service);
	}

	public GestionRelacionesEACRBI lookupGestionRelacionesEACRBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());

		IServiceBase service = new GestionRelacionesEACRBIImpl(
				dbEntityFactory.getRelacionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getFormatoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getHuecoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUnidadInstalacionReeaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUDocEnUiDepositoDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUiReeaCRDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUDocEnUIReeaCRDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUDocReeaCRDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getUnidadInstalacionDepositoDbEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getNivelCFDBEntity(serviceSession
						.getTransactionManager()));

		service.attachToSession(this.serviceSession);
		service.setSecurityManager(SecurityManagerLocator
				.loockupTransferenciasSM());
		return (GestionRelacionesEACRBI) configureService(
				GestionRelacionesEACRBI.class, service);
	}

	public GestionFondosBI lookupGestionFondosBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		IServiceBase service = new GestionFondosBIImpl(
				dbEntityFactory.getFondoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getEntidadProductoraDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getElementoCuadroClasificacionDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getSerieDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDescriptorDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getOrganoProductorDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getCatalogoListaDescriptoresDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getNivelCFDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getFondosUnidadDocumentalDBEntity(serviceSession
								.getTransactionManager()));
		service.attachToSession(this.serviceSession);

		// establecer el security manager
		service.setSecurityManager(FondosSecurityManager.getInstance());

		// devolver un wrapper del servicio de fondos
		return (GestionFondosBI) configureService(GestionFondosBI.class,
				service);

	}

	public GestionCuadroClasificacionBI lookupGestionCuadroClasificacionBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		IServiceBase service = new CuadroClasificacionBIImpl(
				dbEntityFactory.getElementoCuadroClasificacionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNivelCFDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getTextoCortoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getTextoLargoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNumeroDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getFechaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getReferenciaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUsuarioDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getProductorSerieDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUnidadDocumentalDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUDocEnUiDepositoDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getSerieDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getArchivoDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getUnidadInstalacionDepositoDbEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory
						.getCatalogoTablasTemporalesDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getTablaTemporalDBEntity(serviceSession
						.getTransactionManager()));

		service.attachToSession(this.serviceSession);
		service.setLogger(ArchivoLogger
				.getLogger(CuadroClasificacionBIImpl.class));
		service.setSecurityManager(SecurityManagerLocator
				.lookupFondosSecurityManager());
		return (GestionCuadroClasificacionBI) configureService(
				GestionCuadroClasificacionBI.class, service);
	}

	public GestionPrestamosBI lookupGestionPrestamosBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		IServiceBase service = new GestionPrestamosBIImpl(
				dbEntityFactory.getMotivoRechazoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getProrrogaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNSecDBEntityForSolicitudes(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDetalleDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUDocEnUiDepositoDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getPrestamoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getConsultaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getUnidadInstalacionDepositoDbEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getRevisionDocumentacionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getReferenciaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUdocEnUIDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getMotivoPrestamoDBEntity(serviceSession
						.getTransactionManager()));

		service.attachToSession(this.serviceSession);
		service.setLogger(ArchivoLogger.getLogger(GestionPrestamosBIImpl.class));
		service.setSecurityManager(SecurityManagerLocator.loockupServiciosSM());
		return (GestionPrestamosBI) configureService(GestionPrestamosBI.class,
				service);
	}

	public GestionConsultasBI lookupGestionConsultasBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		IServiceBase service = new GestionConsultasBIImpl(
				dbEntityFactory.getTemaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getMotivoRechazoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getMotivoConsultaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNSecDBEntityForSolicitudes(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDetalleDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUDocEnUiDepositoDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getConsultaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getUnidadInstalacionDepositoDbEntity(serviceSession
								.getTransactionManager()));

		service.attachToSession(this.serviceSession);
		service.setLogger(ArchivoLogger.getLogger(GestionConsultasBIImpl.class));
		service.setSecurityManager(SecurityManagerLocator.loockupServiciosSM());
		return (GestionConsultasBI) configureService(GestionConsultasBI.class,
				service);
	}

	public ConsultaUnidadesDocumentalesBI lookupConsultaUnidadesDocumentalesBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		IServiceBase service = new ConsultaUnidadesDocumentalesBIImpl(
				dbEntityFactory.getDetalleDBEntity(serviceSession
						.getTransactionManager()));
		service.attachToSession(this.serviceSession);
		service.setLogger(ArchivoLogger
				.getLogger(ConsultaUnidadesDocumentalesBIImpl.class));
		service.setSecurityManager(SecurityManagerLocator.loockupServiciosSM());
		return (ConsultaUnidadesDocumentalesBI) configureService(
				ConsultaUnidadesDocumentalesBI.class, service);
	}

	public GestionSolicitudesBI lookupGestionSolicitudesBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		IServiceBase service = new GestionSolicitudesBIImpl(
				dbEntityFactory.getDetalleDBEntity(serviceSession
						.getTransactionManager()));

		service.attachToSession(this.serviceSession);
		service.setLogger(ArchivoLogger
				.getLogger(GestionSolicitudesBIImpl.class));
		service.setSecurityManager(SecurityManagerLocator.loockupServiciosSM());
		return (GestionSolicitudesBI) configureService(
				GestionSolicitudesBI.class, service);
	}

	public GestionSeriesBI lookupGestionSeriesBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		IServiceBase service = new GestionSeriesBIImpl(
				dbEntityFactory.getElementoCuadroClasificacionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNivelCFDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getSolicitudSerieDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getSerieDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getEntidadProductoraDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getFondoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getOrganoProductorDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getProductorSerieDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDescriptorDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getCAOrganoDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getListaControlAccesoDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getPermisosListaDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getValoracionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getVolumenSerieDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getSolicitudSerieBusquedasDbEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getDocDocumentoCFDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getReferenciaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getFechaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUnidadDocumentalDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUDocEnUiDepositoDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getElementoClasificacionVistaDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getUnidadDocumentalVistaDBEntity(serviceSession
						.getTransactionManager()),

				dbEntityFactory.getDetallePrevisionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getRelacionDBEntity(serviceSession
						.getTransactionManager())
		);

		service.setSecurityManager(SecurityManagerLocator
				.lookupFondosSecurityManager());

		service.attachToSession(this.serviceSession);

		return (GestionSeriesBI) configureService(GestionSeriesBI.class,
				service);
	}

	public GestionUnidadDocumentalBI lookupGestionUnidadDocumentalBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		IServiceBase service = new GestionUnidadDocumentalBIImpl(
				dbEntityFactory.getElementoCuadroClasificacionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getFondosUnidadDocumentalDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getNivelCFDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUDocEnUiDepositoDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getReferenciaDBEntity(serviceSession
						.getTransactionManager()),
				// dbEntityFactory.getUDocElectronicaDBEntity(serviceSession.getTransactionManager()),
				dbEntityFactory
						.getUnidadDocumentalElectronicaDBEntity(serviceSession
								.getTransactionManager()));
		service.attachToSession(this.serviceSession);
		service.setSecurityManager(SecurityManagerLocator
				.lookupFondosSecurityManager());
		return (GestionUnidadDocumentalBI) configureService(
				GestionUnidadDocumentalBI.class, service);
	}

	public GestionDescripcionBI lookupGestionDescripcionBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		IServiceBase service = new GestionDescripcionBIImpl(
				dbEntityFactory.getFichaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getTextoCortoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getTextoLargoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getFechaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNumeroDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getReferenciaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getValidacionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getCatalogoListaDescriptoresDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getDescriptorDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getFmtPrefDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getFmtFichaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getFechaDescrDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNumeroDescrDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getReferenciaDescrDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getTextoCortoDescrDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getTextoLargoDescrDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getTablaValidacionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getElementoCuadroClasificacionDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getFondoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getOrganoProductorDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getCampoDatoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getCampoTablaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUsoObjetoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getAreaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUnidadDocumentalDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getTextoCortoUDocREDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getTextoLargoUDocREDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNumeroUDocREDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getFechaUDocREDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getReferenciaUDocREDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getTransferenciasUnidadDocumentalDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getUDocEnDivisionFSDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getRelacionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getEntidadProductoraDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDocDocumentoDescrDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDocClasifDescrDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDivisionFSDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getListaControlAccesoDbEntity(serviceSession
						.getTransactionManager()),

				dbEntityFactory.getMapDescUDocDBEntity(serviceSession
						.getTransactionManager())

		);
		service.attachToSession(this.serviceSession);
		service.setSecurityManager(SecurityManagerLocator
				.loockupDescripcionSM());
		return (GestionDescripcionBI) configureService(
				GestionDescripcionBI.class, service);
	}

	public GestionDocumentosElectronicosBI lookupGestionDocumentosElectronicosBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		IServiceBase service = new GestionDocumentosElectronicosBIImpl(
				dbEntityFactory.getDocFichaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDocClasifCFDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDocClasifDescrDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDocDocumentoCFDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDocDocumentoDescrDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDocTCapturaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNivelCFDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getElementoCuadroClasificacionDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getDescriptorDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getFondosUnidadDocumentalDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getSerieDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getCatalogoListaDescriptoresDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getCampoDatoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getCampoTablaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNumeroDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getTextoCortoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getTextoLargoDBEntity(serviceSession
						.getTransactionManager()));

		service.attachToSession(this.serviceSession);
		service.setSecurityManager(SecurityManagerLocator
				.loockupDocumentosElectronicosSM());
		return (GestionDocumentosElectronicosBI) configureService(
				GestionDocumentosElectronicosBI.class, service);
	}

	public GestionDocumentosVitalesBI lookupGestionDocumentosVitalesBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		IServiceBase service = new GestionDocumentosVitalesBIImpl(
				dbEntityFactory.getDocumentoVitalDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getTipoDocumentoVitalDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getTipoDocVitProcedimientoDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getUsoDocumentoVitalDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getFondosUnidadDocumentalDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getSerieDBEntity(serviceSession
						.getTransactionManager()));
		service.attachToSession(this.serviceSession);
		service.setSecurityManager(SecurityManagerLocator
				.lookupDocumentosVitalesSM());
		return (GestionDocumentosVitalesBI) configureService(
				GestionDocumentosVitalesBI.class, service);
	}

	public GestionControlUsuariosBI lookupGestionControlUsuariosBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		IServiceBase service = new GestionControlUsuariosBIImpl(
				dbEntityFactory.getRolDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getArchivoDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getCAOrganoDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUsuarioDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getRolUsuarioDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getPermisoRolDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getGrupoUsuarioDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getPermisosListaDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getOrganoUsuarioDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getGrupoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getListaControlAccesoDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getProductorSerieDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDescriptorDBEntity(serviceSession
						.getTransactionManager()));
		service.attachToSession(this.serviceSession);
		service.setSecurityManager(SecurityManagerLocator.loockupUsuariosSM());

		return (GestionControlUsuariosBI) configureService(
				GestionControlUsuariosBI.class, service);
	}

	public GestionSistemaBI lookupGestionSistemaBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		DbDataSource dataSource = serviceSession.getTransactionManager();

		IServiceBase service = new GestionSistemaBImpl(
				dbEntityFactory.getArchivoDbEntity(dataSource),
				dbEntityFactory.getValidacionDBEntity(dataSource));
		service.attachToSession(this.serviceSession);

		return (GestionSistemaBI) configureService(GestionSistemaBI.class,
				service);
	}

	public GestionAuditoriaBI lookupGestionAuditoriaBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		DbDataSource dataSource = serviceSession.getTransactionManager();

		IServiceBase service = new GestionAuditoriaBImpl(
				dbEntityFactory.getTrazaDBEntity(dataSource),
				dbEntityFactory.getDatosTrazaDBEntity(dataSource),
				dbEntityFactory.getCritAccionesDBEntity(dataSource),
				dbEntityFactory.getCritUsuariosDBEntity(dataSource));

		service.attachToSession(this.serviceSession);
		service.setSecurityManager(SecurityManagerLocator.loockupAuditoriaSM());
		return (GestionAuditoriaBI) configureService(GestionAuditoriaBI.class,
				service);
	}

	public GestionValoracionBI lookupGestionValoracionBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());

		IServiceBase service = new GestionValoracionImpl(
				dbEntityFactory.getNSecValoracionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getValoracionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNVersionValoracionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getPlazosValoracionDBEntity(serviceSession
						.getTransactionManager()));

		service.attachToSession(this.serviceSession);
		service.setLogger(ArchivoLogger.getLogger(GestionValoracionImpl.class));
		service.setSecurityManager(SecurityManagerLocator
				.lookupFondosSecurityManager());

		return (GestionValoracionBI) configureService(
				GestionValoracionBI.class, service);
	}

	public GestionEliminacionBI lookupGestionEliminacionBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());

		IServiceBase service = new GestionEliminacionImpl(
				dbEntityFactory.getEliminacionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDetalleDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getElementoCuadroClasificacionDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getHistoricoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNVersionSeleccionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getEliminacionUDocConservadaDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getUDocEnUiDepositoDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getEliminacionUDocEliminadaDBEntity(serviceSession
								.getTransactionManager()));

		service.attachToSession(this.serviceSession);
		service.setLogger(ArchivoLogger.getLogger(GestionEliminacionImpl.class));
		service.setSecurityManager(SecurityManagerLocator
				.lookupFondosSecurityManager());

		return (GestionEliminacionBI) configureService(
				GestionEliminacionBI.class, service);
	}

	public GestorCatalogo lookupGestorCatalogo() throws GestorCatalogoException {
		// Obtener información de la entidad
		ServiceClient serviceClient = getServiceClient();

		// Obtener la entidad para el usuario conectado
		Properties params = null;

		if ((serviceClient != null)
				&& (StringUtils.isNotEmpty(serviceClient.getEntity()))) {
			params = new Properties();
			params.put(MultiEntityConstants.ENTITY_PARAM,
					serviceClient.getEntity());
		}
		return GestorCatalogoFactory.getConnector(params);
	}

	public SistemaTramitador lookupSistemaTramitacion(String idSistema)
			throws SistemaTramitadorException {

		// Obtener información de la entidad
		ServiceClient serviceClient = getServiceClient();

		// Obtener la entidad para el usuario conectado
		Properties params = null;

		if ((serviceClient != null)
				&& (StringUtils.isNotEmpty(serviceClient.getEntity()))) {
			params = new Properties();
			params.put(MultiEntityConstants.ENTITY_PARAM,
					serviceClient.getEntity());
		}

		return SistemaTramitadorFactory.getConnector(idSistema, params);
	}

	public GestionSessionBI lookupGestionSessionBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		IServiceBase service = new GestionSessionImpl(
				dbEntityFactory.getSessionDBEntity(serviceSession
						.getTransactionManager()));
		service.attachToSession(this.serviceSession);

		return (GestionSessionBI) configureService(GestionSessionBI.class,
				service);
	}

	public GestionFraccionSerieBI lookupGestionFraccionSerieBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		IServiceBase service = new GestionFraccionSerieBIImpl(
				dbEntityFactory.getUnidadDocumentalDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUDocEnUiDepositoDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getUnidadInstalacionDepositoDbEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getFormatoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getElementoCuadroClasificacionDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getDescriptorDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getReferenciaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getFechaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getTextoCortoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getTextoLargoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNumeroDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getOrganoProductorDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getMapDescUDocDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNSecUDocDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDivisionFSDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUDocEnDivisionFSDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getReferenciaUDocREDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getFechaUDocREDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getTextoCortoUDocREDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getTextoLargoUDocREDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNumeroUDocREDBEntity(serviceSession
						.getTransactionManager()));

		service.attachToSession(this.serviceSession);
		service.setSecurityManager(SecurityManagerLocator
				.lookupFondosSecurityManager());
		return (GestionFraccionSerieBI) configureService(
				GestionFraccionSerieBI.class, service);
	}

	public GestionNivelesArchivoBI lookupGestionNivelesArchivoBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		IServiceBase service = new GestionNivelesArchivoBIImpl(
				dbEntityFactory.getNivelArchivoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getArchivoDbEntity(serviceSession
						.getTransactionManager()));

		service.attachToSession(this.serviceSession);

		return (GestionNivelesArchivoBI) configureService(
				GestionNivelesArchivoBI.class, service);
	}

	public GestionArchivosBI lookupGestionArchivosBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		IServiceBase service = new GestionArchivosBIImpl(
				dbEntityFactory.getArchivoDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getFondoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getElementoCuadroClasificacionDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getDepositoDbEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getEdificioDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getConsultaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getUsuarioArchivoSalasConsultaDBEntity(serviceSession
								.getTransactionManager()));
		service.attachToSession(this.serviceSession);

		return (GestionArchivosBI) configureService(GestionArchivosBI.class,
				service);
	}

	public GestionInfoSistemaBI lookupInfoSistemaBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());

		IServiceBase service = new GestionInfoSistemaBIImpl(
				dbEntityFactory.getInfoSistemaDBEntity(serviceSession
						.getTransactionManager()));
		service.attachToSession(this.serviceSession);

		return (GestionInfoSistemaBI) configureService(
				GestionInfoSistemaBI.class, service);
	}

	public GestionOrganizacionBI lookupGestionOrganizacionBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		IServiceBase service = new GestionOrganizacionBIImpl(
				dbEntityFactory.getOrganizacionDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUserOrganoDBEntity(serviceSession
						.getTransactionManager()));

		service.attachToSession(this.serviceSession);

		return (GestionOrganizacionBI) configureService(
				GestionOrganizacionBI.class, service);
	}

	public GestionRechazosBI lookupGestionMotivosRechazoBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		IServiceBase service = new GestionMotivosRechazoBImpl(
				dbEntityFactory.getMotivoRechazoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDetalleDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getProrrogaDBEntity(serviceSession
						.getTransactionManager()));
		service.attachToSession(this.serviceSession);

		return (GestionRechazosBI) configureService(GestionRechazosBI.class,
				service);
	}

	public GestionSalasConsultaBI lookupGestionSalasBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());
		IServiceBase service = new GestionSalasConsultaBIImpl(
				dbEntityFactory.getEdificioDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getSalaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getMesaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUsuarioSalaConsultaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory
						.getUsuarioArchivoSalasConsultaDBEntity(serviceSession
								.getTransactionManager()),
				dbEntityFactory.getRegistroConsultaSalaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getUsuarioDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getConsultaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDetalleDBEntity(serviceSession
						.getTransactionManager()));
		service.attachToSession(this.serviceSession);
		service.setSecurityManager(SecurityManagerLocator
				.lookupSalasSecurityManager());
		return (GestionSalasConsultaBI) configureService(
				GestionSalasConsultaBI.class, service);

	}

	/**
	 * Servicio para transferencia electrónicas.
	 *
	 * @return
	 */
	public GestionTransferenciasElectronicasBI lookupGestionTransferenciasElectronicasBI() {
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(serviceClient.getDBFactoryClass());

		IServiceBase service = new GestionTransferenciasElectronicasBIImpl(
				dbEntityFactory.getTextoCortoUDocREDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getTextoLargoUDocREDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getFechaUDocREDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNumeroUDocREDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getReferenciaUDocREDBEntity(serviceSession
						.getTransactionManager()),

				dbEntityFactory.getTextoCortoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getTextoLargoDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getFechaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getNumeroDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getReferenciaDBEntity(serviceSession
						.getTransactionManager()),
				dbEntityFactory.getDocDocumentoCFDBEntity(serviceSession.getTransactionManager()),

				dbEntityFactory.getTransferenciasUnidadDocumentalDBEntity(serviceSession.getTransactionManager())

		);

		service.attachToSession(this.serviceSession);
		service.setSecurityManager(SecurityManagerLocator
				.lookupSalasSecurityManager());
		return (GestionTransferenciasElectronicasBI) configureService(
				GestionTransferenciasElectronicasBI.class, service);
	}
}
