package ieci.tecdoc.sgm.registropresencial.ws.client;

import java.rmi.RemoteException;

import ieci.tecdoc.sgm.registropresencial.ws.client.axis.BooksInfo;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionCountInfo;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionOptions;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionsInfo;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderSearchCriteria;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderWithPersonInfo;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.OfficesInfo;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegistersInfo;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.SearchCriteria;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo;

public class ServicioRegistroWebServiceProxy
		implements
		ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType {
	private String _endpoint = null;
	private ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType servicioRegistroWebService = null;

	public ServicioRegistroWebServiceProxy() {
		_initServicioRegistroWebServiceProxy();
	}

	private void _initServicioRegistroWebServiceProxy() {
		try {
			servicioRegistroWebService = (new ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebServiceServiceLocator())
					.getServicioRegistroWebService();
			if (servicioRegistroWebService != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) servicioRegistroWebService)
							._setProperty(
									"javax.xml.rpc.service.endpoint.address",
									_endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) servicioRegistroWebService)
							._getProperty("javax.xml.rpc.service.endpoint.address");
			}

		} catch (javax.xml.rpc.ServiceException serviceException) {
		}
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
		if (servicioRegistroWebService != null)
			((javax.xml.rpc.Stub) servicioRegistroWebService)._setProperty(
					"javax.xml.rpc.service.endpoint.address", _endpoint);

	}

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType getServicioRegistroWebService() {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#acceptDistribution(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public RetornoServicio acceptDistribution(UserInfo user, Folder folder,
			Entidad entidad) throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.acceptDistribution(user, folder,
				entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#acceptDistributionEx(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public RetornoServicio acceptDistributionEx(UserInfo user,
			DistributionInfo distributionInfo, Entidad entidad)
			throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.acceptDistributionEx(user,
				distributionInfo, entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#addDocument(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public RetornoServicio addDocument(UserInfo user, Folder folder,
			Entidad entidad) throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.addDocument(user, folder, entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#archiveDistribution(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public RetornoServicio archiveDistribution(UserInfo user, Folder folder,
			Entidad entidad) throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.archiveDistribution(user, folder,
				entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#archiveDistributionEx(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public RetornoServicio archiveDistributionEx(UserInfo user,
			DistributionInfo distributionInfo, Entidad entidad)
			throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.acceptDistributionEx(user,
				distributionInfo, entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#canCreateRegister(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.SearchCriteria,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public RetornoServicio canCreateRegister(UserInfo user,
			SearchCriteria criteria, Entidad entidad) throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.canCreateRegister(user, criteria,
				entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#changeInputDistribution(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionOptions,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public RetornoServicio changeInputDistribution(UserInfo user,
			Folder folder, DistributionOptions options, Entidad entidad)
			throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.changeInputDistribution(user, folder,
				options, entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#changeOutputDistribution(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionOptions,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public RetornoServicio changeOutputDistribution(UserInfo user,
			Folder folder, DistributionOptions options, Entidad entidad)
			throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.changeOutputDistribution(user,
				folder, options, entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#consolidateFolder(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo[],
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public RegisterInfo consolidateFolder(UserInfo user, PersonInfo[] inter,
			Folder folder, Entidad entidad) throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.consolidateFolder(user, inter,
				folder, entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#countInputDistribution(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public DistributionCountInfo countInputDistribution(UserInfo user,
			DistributionSearchCriteria criteria, Entidad entidad)
			throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.countInputDistribution(user,
				criteria, entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#countOutputDistribution(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public DistributionCountInfo countOutputDistribution(UserInfo user,
			DistributionSearchCriteria criteria, Entidad entidad)
			throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.countOutputDistribution(user,
				criteria, entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#createFolder(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo[],
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public RegisterInfo createFolder(UserInfo user, PersonInfo[] inter,
			Folder folder, Entidad entidad) throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.createFolder(user, inter, folder,
				entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#findFolder(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderSearchCriteria,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public RegistersInfo findFolder(UserInfo user,
			FolderSearchCriteria folderQuery, Entidad entidad)
			throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService
				.findFolder(user, folderQuery, entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#getBooksForType(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.SearchCriteria,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public BooksInfo getBooksForType(UserInfo user, SearchCriteria criteria,
			Entidad entidad) throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.getBooksForType(user, criteria,
				entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#getDistribution(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public DistributionInfo getDistribution(UserInfo user,
			DistributionInfo distributionInfo, Entidad entidad)
			throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.getDistribution(user,
				distributionInfo, entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#getDocumentFolder(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public Document getDocumentFolder(UserInfo user, Document document,
			Entidad entidad) throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.getDocumentFolder(user, document,
				entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#getInputDistribution(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public DistributionsInfo getInputDistribution(UserInfo user,
			DistributionSearchCriteria criteria, Entidad entidad)
			throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.getInputDistribution(user, criteria,
				entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#getInputFolderForNumber(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public Folder getInputFolderForNumber(UserInfo user, Folder folder,
			Entidad entidad) throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.getInputFolderForNumber(user, folder,
				entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#getInputRegister(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public FolderWithPersonInfo getInputRegister(UserInfo user, Folder folder,
			Entidad entidad) throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.getInputRegister(user, folder,
				entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#getInterestedInputRegister(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public RetornoServicio getInterestedInputRegister(UserInfo user,
			Folder folder, Entidad entidad) throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.getInterestedInputRegister(user,
				folder, entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#getInterestedOutputRegister(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public RetornoServicio getInterestedOutputRegister(UserInfo user,
			Folder folder, Entidad entidad) throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.getInterestedOutputRegister(user,
				folder, entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#getOfficeCanCreateRegisterByUser(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public OfficesInfo getOfficeCanCreateRegisterByUser(UserInfo user,
			Folder folder, Entidad entidad) throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.getOfficeCanCreateRegisterByUser(
				user, folder, entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#getOutputDistribution(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public DistributionsInfo getOutputDistribution(UserInfo user,
			DistributionSearchCriteria criteria, Entidad entidad)
			throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.getOutputDistribution(user, criteria,
				entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#getOutputFolderForNumber(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public Folder getOutputFolderForNumber(UserInfo user, Folder folder,
			Entidad entidad) throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.getOutputFolderForNumber(user,
				folder, entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#getOutputRegister(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public FolderWithPersonInfo getOutputRegister(UserInfo user, Folder folder,
			Entidad entidad) throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.getOutputRegister(user, folder,
				entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#importFolder(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo[],
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public RegisterInfo importFolder(UserInfo user, PersonInfo[] inter,
			Folder folder, Entidad entidad) throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.importFolder(user, inter, folder,
				entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#rejectDistribution(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionOptions,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public RetornoServicio rejectDistribution(UserInfo user, Folder folder,
			DistributionOptions options, Entidad entidad)
			throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.rejectDistribution(user, folder,
				options, entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#rejectDistributionEx(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionOptions,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public RetornoServicio rejectDistributionEx(UserInfo user,
			DistributionInfo distributionInfo, DistributionOptions options,
			Entidad entidad) throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.rejectDistributionEx(user,
				distributionInfo, options, entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ieci.tecdoc.sgm.registropresencial.ws.client.axis.ServicioRegistroWebService_PortType#updateFolder(ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo[],
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder,
	 *      ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad)
	 */
	public RegisterInfo updateFolder(UserInfo user, PersonInfo[] inter,
			Folder folder, Entidad entidad) throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.updateFolder(user, inter, folder,
				entidad);
	}


	public RetornoServicio existMatterTypeInOffice(UserInfo user,
			String matterTypeCode, String officeCode, Entidad entidad)
			throws RemoteException {
		if (servicioRegistroWebService == null)
			_initServicioRegistroWebServiceProxy();
		return servicioRegistroWebService.existMatterTypeInOffice(user, matterTypeCode, officeCode, entidad);
	}

}