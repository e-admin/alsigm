package ieci.tecdoc.sgm.registropresencial.ws.server;

public class ServicioRegistroWebServiceProxy implements ieci.tecdoc.sgm.registropresencial.ws.server.ServicioRegistroWebService {
  private String _endpoint = null;
  private ieci.tecdoc.sgm.registropresencial.ws.server.ServicioRegistroWebService servicioRegistroWebService = null;
  
  public ServicioRegistroWebServiceProxy() {
    _initServicioRegistroWebServiceProxy();
  }
  
  public ServicioRegistroWebServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initServicioRegistroWebServiceProxy();
  }
  
  private void _initServicioRegistroWebServiceProxy() {
    try {
      servicioRegistroWebService = (new ieci.tecdoc.sgm.registropresencial.ws.server.ServicioRegistroWebServiceServiceLocator()).getServicioRegistroWebService();
      if (servicioRegistroWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)servicioRegistroWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)servicioRegistroWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (servicioRegistroWebService != null)
      ((javax.xml.rpc.Stub)servicioRegistroWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ieci.tecdoc.sgm.registropresencial.ws.server.ServicioRegistroWebService getServicioRegistroWebService() {
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService;
  }
  
  public ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo getDistribution(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo distributionInfo, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.getDistribution(user, distributionInfo, entidad);
  }
  
  public ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo createFolder(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[] inter, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.createFolder(user, inter, folder, entidad);
  }
  
  public ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo updateFolder(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[] inter, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.updateFolder(user, inter, folder, entidad);
  }
  
  public ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo importFolder(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[] inter, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.importFolder(user, inter, folder, entidad);
  }
  
  public ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo consolidateFolder(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[] inter, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.consolidateFolder(user, inter, folder, entidad);
  }
  
  public ieci.tecdoc.sgm.registropresencial.ws.server.RegistersInfo findFolder(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.FolderSearchCriteria folderQuery, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.findFolder(user, folderQuery, entidad);
  }
  
  public ieci.tecdoc.sgm.registropresencial.ws.server.Folder getInputFolderForNumber(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.getInputFolderForNumber(user, folder, entidad);
  }
  
  public ieci.tecdoc.sgm.registropresencial.ws.server.FolderWithPersonInfo getInputRegister(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.getInputRegister(user, folder, entidad);
  }
  
  public ieci.tecdoc.sgm.registropresencial.ws.server.Folder getOutputFolderForNumber(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.getOutputFolderForNumber(user, folder, entidad);
  }
  
  public ieci.tecdoc.sgm.registropresencial.ws.server.FolderWithPersonInfo getOutputRegister(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.getOutputRegister(user, folder, entidad);
  }
  
  public ieci.tecdoc.sgm.registropresencial.ws.server.Document getDocumentFolder(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Document document, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.getDocumentFolder(user, document, entidad);
  }
  
  public ieci.tecdoc.sgm.registropresencial.ws.server.DistributionsInfo getInputDistribution(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionSearchCriteria criteria, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.getInputDistribution(user, criteria, entidad);
  }
  
  public ieci.tecdoc.sgm.registropresencial.ws.server.DistributionsInfo getOutputDistribution(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionSearchCriteria criteria, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.getOutputDistribution(user, criteria, entidad);
  }
  
  public ieci.tecdoc.sgm.registropresencial.ws.server.DistributionCountInfo countInputDistribution(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionSearchCriteria criteria, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.countInputDistribution(user, criteria, entidad);
  }
  
  public ieci.tecdoc.sgm.registropresencial.ws.server.DistributionCountInfo countOutputDistribution(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionSearchCriteria criteria, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.countOutputDistribution(user, criteria, entidad);
  }
  
  public ieci.tecdoc.sgm.core.services.dto.RetornoServicio acceptDistribution(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.acceptDistribution(user, folder, entidad);
  }
  
  public ieci.tecdoc.sgm.core.services.dto.RetornoServicio acceptDistributionEx(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo distributionInfo, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.acceptDistributionEx(user, distributionInfo, entidad);
  }
  
  public ieci.tecdoc.sgm.core.services.dto.RetornoServicio archiveDistribution(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.archiveDistribution(user, folder, entidad);
  }
  
  public ieci.tecdoc.sgm.core.services.dto.RetornoServicio archiveDistributionEx(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo distributionInfo, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.archiveDistributionEx(user, distributionInfo, entidad);
  }
  
  public ieci.tecdoc.sgm.core.services.dto.RetornoServicio rejectDistribution(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionOptions options, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.rejectDistribution(user, folder, options, entidad);
  }
  
  public ieci.tecdoc.sgm.core.services.dto.RetornoServicio rejectDistributionEx(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo distributionInfo, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionOptions options, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.rejectDistributionEx(user, distributionInfo, options, entidad);
  }
  
  public ieci.tecdoc.sgm.core.services.dto.RetornoServicio changeInputDistribution(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionOptions options, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.changeInputDistribution(user, folder, options, entidad);
  }
  
  public ieci.tecdoc.sgm.core.services.dto.RetornoServicio changeOutputDistribution(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.registropresencial.ws.server.DistributionOptions options, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.changeOutputDistribution(user, folder, options, entidad);
  }
  
  public ieci.tecdoc.sgm.registropresencial.ws.server.RetornoServicio addDocument(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.addDocument(user, folder, entidad);
  }
  
  public ieci.tecdoc.sgm.registropresencial.ws.server.ListaPersonInfo getInterestedInputRegister(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.getInterestedInputRegister(user, folder, entidad);
  }
  
  public ieci.tecdoc.sgm.registropresencial.ws.server.ListaPersonInfo getInterestedOutputRegister(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.getInterestedOutputRegister(user, folder, entidad);
  }
  
  public ieci.tecdoc.sgm.registropresencial.ws.server.RetornoServicio canCreateRegister(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.SearchCriteria criteria, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.canCreateRegister(user, criteria, entidad);
  }
  
  public ieci.tecdoc.sgm.registropresencial.ws.server.BooksInfo getBooksForType(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.SearchCriteria criteria, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.getBooksForType(user, criteria, entidad);
  }
  
  public ieci.tecdoc.sgm.registropresencial.ws.server.OfficesInfo getOfficeCanCreateRegisterByUser(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, ieci.tecdoc.sgm.registropresencial.ws.server.Folder folder, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.getOfficeCanCreateRegisterByUser(user, folder, entidad);
  }
  
  public ieci.tecdoc.sgm.registropresencial.ws.server.RetornoServicio existMatterTypeInOffice(ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo user, java.lang.String matterTypeCode, java.lang.String officeCode, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws java.rmi.RemoteException{
    if (servicioRegistroWebService == null)
      _initServicioRegistroWebServiceProxy();
    return servicioRegistroWebService.existMatterTypeInOffice(user, matterTypeCode, officeCode, entidad);
  }
  
  
}