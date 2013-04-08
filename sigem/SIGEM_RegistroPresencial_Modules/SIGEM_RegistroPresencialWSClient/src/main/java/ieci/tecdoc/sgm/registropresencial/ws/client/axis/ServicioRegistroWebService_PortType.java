/**
 * ServicioRegistroWebService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registropresencial.ws.client.axis;

public interface ServicioRegistroWebService_PortType extends java.rmi.Remote {
	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo getDistribution(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo distributionInfo,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo createFolder(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo[] inter,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo updateFolder(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo[] inter,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo importFolder(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo[] inter,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo consolidateFolder(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo[] inter,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegistersInfo findFolder(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderSearchCriteria folderQuery,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder getInputFolderForNumber(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderWithPersonInfo getInputRegister(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder getOutputFolderForNumber(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderWithPersonInfo getOutputRegister(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document getDocumentFolder(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document document,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionsInfo getInputDistribution(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria criteria,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionsInfo getOutputDistribution(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria criteria,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionCountInfo countInputDistribution(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria criteria,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionCountInfo countOutputDistribution(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria criteria,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio acceptDistribution(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio acceptDistributionEx(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo distributionInfo,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio archiveDistribution(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio archiveDistributionEx(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo distributionInfo,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio rejectDistribution(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionOptions options,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio rejectDistributionEx(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo distributionInfo,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionOptions options,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio changeInputDistribution(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionOptions options,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio changeOutputDistribution(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionOptions options,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio addDocument(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio getInterestedInputRegister(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio getInterestedOutputRegister(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio canCreateRegister(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.SearchCriteria criteria,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.BooksInfo getBooksForType(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.SearchCriteria criteria,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.OfficesInfo getOfficeCanCreateRegisterByUser(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;

	public ieci.tecdoc.sgm.registropresencial.ws.client.axis.RetornoServicio existMatterTypeInOffice(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo user,
			java.lang.String matterTypeCode, java.lang.String officeCode,
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad entidad)
			throws java.rmi.RemoteException;
}
