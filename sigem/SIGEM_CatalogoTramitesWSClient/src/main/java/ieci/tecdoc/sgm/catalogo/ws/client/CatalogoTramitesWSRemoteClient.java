package ieci.tecdoc.sgm.catalogo.ws.client;
/*
 * $Id: CatalogoTramitesWSRemoteClient.java,v 1.2.2.4 2008/10/13 08:51:13 jnogales Exp $
 */
import java.rmi.RemoteException;

import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites;
import ieci.tecdoc.sgm.core.services.catalogo.CatalogoTramitesExcepcion;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;

public class CatalogoTramitesWSRemoteClient implements ServicioCatalogoTramites {

	private CatalogoTramitesWebService service;
	
	
	public CatalogoTramitesWebService getService() {
		return service;
	}

	public void setService(CatalogoTramitesWebService service) {
		this.service = service;
	}
	
	public void addProcedure(ieci.tecdoc.sgm.core.services.catalogo.Tramite procedure, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			RetornoServicio oRetorno =  getService().addProcedure(getTramiteWS(procedure), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.catalogo.Tramite getProcedure(String procedureId, boolean loadDocuments, Entidad entidad)  throws CatalogoTramitesExcepcion {
		try{
			Tramite oRetorno =  getService().getProcedure(procedureId, getRetornoLogicoWS(loadDocuments), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getTramiteServicio(oRetorno);
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.catalogo.Tramites query(ieci.tecdoc.sgm.core.services.catalogo.TramiteConsulta query, Entidad entidad)  throws CatalogoTramitesExcepcion {
		try{
			Tramites oRetorno =  getService().query(getTramiteConsultaWS(query), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getTramitesServicio(oRetorno);
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public void deleteProcedure(String procedureId, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			RetornoServicio oRetorno =  getService().deleteProcedure(procedureId, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public void updateProcedure(ieci.tecdoc.sgm.core.services.catalogo.Tramite procedure, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			RetornoServicio oRetorno =  getService().updateProcedure(getTramiteWS(procedure), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public boolean isDocumentReferenced(String documentId, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			RetornoLogico oRetorno =  getService().isDocumentReferenced(documentId, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getRetornoLogicoServicio(oRetorno);
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public void addProcedureDocument(ieci.tecdoc.sgm.core.services.catalogo.DocumentoTramite procedureDocument, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			RetornoServicio oRetorno =  getService().addProcedureDocument(getDocumentoTramiteWS(procedureDocument), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public void deleteProcedureDocument(ieci.tecdoc.sgm.core.services.catalogo.DocumentoTramite procedureDocument, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			RetornoServicio oRetorno =  getService().deleteProcedureDocument(getDocumentoTramiteWS(procedureDocument), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.catalogo.Documento getDocument(String documentId, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			Documento oRetorno =  getService().getDocument(documentId, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getDocumentoServicio(oRetorno);
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public void addDocument(ieci.tecdoc.sgm.core.services.catalogo.Documento document, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			RetornoServicio oRetorno =  getService().addDocument(getDocumentoWS(document), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public void deleteDocument(String documentId, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			RetornoServicio oRetorno =  getService().deleteDocument(documentId, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public void updateDocument(ieci.tecdoc.sgm.core.services.catalogo.Documento document, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			RetornoServicio oRetorno =  getService().updateDocument(getDocumentoWS(document), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.catalogo.Documentos getDocuments(Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			Documentos oRetorno =  getService().getDocuments(getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getDocumentosServicio(oRetorno);
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.catalogo.Documentos getProcedureDocuments(String procedureId, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			Documentos oRetorno =  getService().getProcedureDocuments(procedureId, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getDocumentosServicio(oRetorno);
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.catalogo.DocumentoTramite getProcedureDocument(String procedureId, String documentId, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			DocumentoTramite oRetorno =  getService().getProcedureDocument(procedureId, documentId, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getDocumentoTramiteServicio(oRetorno);
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.catalogo.DocumentoTramite getProcedureDocument(String procedureId, String documentId, String code, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			DocumentoTramite oRetorno =  getService().getProcedureDocumentByCode(procedureId, documentId, code, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getDocumentoTramiteServicio(oRetorno);
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public void updateProcedureDocument(ieci.tecdoc.sgm.core.services.catalogo.DocumentoTramite procedure, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			RetornoServicio oRetorno =  getService().updateProcedureDocument(getDocumentoTramiteWS(procedure), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.catalogo.Tramites getProcedures(Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			Tramites oRetorno =  getService().getProcedures(getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getTramitesServicio(oRetorno);
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.catalogo.Documento getDocumentfromCode (String code, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			Documento oRetorno =  getService().getDocumentfromCode(code, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getDocumentoServicio(oRetorno);
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.catalogo.OrganoDestinatario getAddressee(String addresseeId, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			OrganoDestinatario oRetorno =  getService().getAddressee(addresseeId, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getOrganoDestinatarioServicio(oRetorno);
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public void addAddressee(ieci.tecdoc.sgm.core.services.catalogo.OrganoDestinatario addressee, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			RetornoServicio oRetorno =  getService().addAddressee(getOrganoDestinatarioWS(addressee), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public void deleteAddressee(String addresseeId, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			RetornoServicio oRetorno =  getService().deleteAddressee(addresseeId, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public void updateAddressee(ieci.tecdoc.sgm.core.services.catalogo.OrganoDestinatario addressee, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			RetornoServicio oRetorno =  getService().updateAddressee(getOrganoDestinatarioWS(addressee), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.catalogo.OrganosDestinatarios getAddressees(Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			OrganosDestinatarios oRetorno =  getService().getAddressees(getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getOrganosDestinatariosServicio(oRetorno);
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.catalogo.Conector getHook(String hookId, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			Conector oRetorno =  getService().getHook(hookId, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getConectorServicio(oRetorno);
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public void addHook(ieci.tecdoc.sgm.core.services.catalogo.Conector hook, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			RetornoServicio oRetorno =  getService().addHook(getConectorWS(hook), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public void deleteHook(String hookId, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			RetornoServicio oRetorno =  getService().deleteHook(hookId, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public void updateHook(ieci.tecdoc.sgm.core.services.catalogo.Conector hook, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			RetornoServicio oRetorno =  getService().updateHook(getConectorWS(hook), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.catalogo.Conectores getHooks(Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			Conectores oRetorno =  getService().getHooks(getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getConectoresServicio(oRetorno);
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.catalogo.Conectores getHooksByType(int hookType, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			Conectores oRetorno =  getService().getHooksByType(""+hookType, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getConectoresServicio(oRetorno);
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.catalogo.TipoConector getHookType(int typeId, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			TipoConector oRetorno =  getService().getHookType(""+typeId, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getTipoConectorServicio(oRetorno);
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public void addHookType(ieci.tecdoc.sgm.core.services.catalogo.TipoConector hookType, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			RetornoServicio oRetorno =  getService().addHookType(getTipoConectorWS(hookType), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public void deleteHookType(int typeId, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			RetornoServicio oRetorno =  getService().deleteHookType(""+typeId, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public void updateHookType(ieci.tecdoc.sgm.core.services.catalogo.TipoConector hookType, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			RetornoServicio oRetorno =  getService().updateHookType(getTipoConectorWS(hookType), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.catalogo.TiposConectores getHookTypes(Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			TiposConectores oRetorno =  getService().getHookTypes(getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getTiposConectoresServicio(oRetorno);
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.catalogo.ConectoresAutenticacion getAuthHooks (String tramiteId, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			ConectoresAutenticacion oRetorno =  getService().getAuthHooks(tramiteId, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getConectoresAutenticacionServicio(oRetorno);
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	} 
	
	public void addAuthHooks(ieci.tecdoc.sgm.core.services.catalogo.ConectorAutenticacion conectorAutenticacion, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			RetornoServicio oRetorno =  getService().addAuthHooks(getConectorAutenticacionWS(conectorAutenticacion), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public void deleteAuthHooks(String tramiteId, String conectorId, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			RetornoServicio oRetorno =  getService().deleteAuthHooks(tramiteId, conectorId, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public void updateAuthHooks(ieci.tecdoc.sgm.core.services.catalogo.ConectorAutenticacion conectorAutenticacion, String oldHookId, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			RetornoServicio oRetorno =  getService().updateAuthHooks(getConectorAutenticacionWS(conectorAutenticacion), oldHookId, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.catalogo.ConectorAutenticacion getAuthHook (String tramiteId, String conectorId, Entidad entidad) throws CatalogoTramitesExcepcion {
		try{
			ConectorAutenticacion oRetorno =  getService().getAuthHook(tramiteId, conectorId, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getConectorAutenticacionServicio(oRetorno);
			}else{
				throw getCatalogoTramitesException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CatalogoTramitesExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	} 
	
	private CatalogoTramitesExcepcion getCatalogoTramitesException(IRetornoServicio oReturn){
		return new CatalogoTramitesExcepcion(Long.valueOf(oReturn.getErrorCode()).longValue());
	}
	
	private Tramite getTramiteWS(ieci.tecdoc.sgm.core.services.catalogo.Tramite poTramite){
		if (poTramite == null)
			return null;
		
		Tramite oTramite = new Tramite();
		oTramite.setAddressee(poTramite.getAddressee());
		oTramite.setDescription(poTramite.getDescription());
		oTramite.setDocuments(getDocumentosWS(poTramite.getDocuments()));
		oTramite.setFirma(""+poTramite.getFirma());
		oTramite.setId(poTramite.getId());
		oTramite.setLimitDocs(""+poTramite.getLimitDocs());
		oTramite.setOficina(poTramite.getOficina());
		oTramite.setTopic(poTramite.getTopic());
		oTramite.setIdProcedimiento(poTramite.getIdProcedimiento());
		
		return oTramite;
	}
	
	private ieci.tecdoc.sgm.core.services.catalogo.Tramite getTramiteServicio(Tramite poTramite){
		if (poTramite == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catalogo.Tramite oTramite = new ieci.tecdoc.sgm.core.services.catalogo.Tramite();
		oTramite.setAddressee(poTramite.getAddressee());
		oTramite.setDescription(poTramite.getDescription());
		oTramite.setDocuments(getDocumentosServicio(poTramite.getDocuments()));
		oTramite.setFirma(new Boolean(poTramite.getFirma()).booleanValue());
		oTramite.setId(poTramite.getId());
		oTramite.setLimitDocs(new Boolean(poTramite.getLimitDocs()).booleanValue());
		oTramite.setOficina(poTramite.getOficina());
		oTramite.setTopic(poTramite.getTopic());
		oTramite.setIdProcedimiento(poTramite.getIdProcedimiento());
		
		return oTramite;
	}
	
	private ieci.tecdoc.sgm.core.services.catalogo.Tramites getTramitesServicio(Tramites poTramites){
		if (poTramites == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catalogo.Tramites oTramites = new ieci.tecdoc.sgm.core.services.catalogo.Tramites();
		Tramite[] poTramitesArray = poTramites.getTramites();
		for (int i=0; i<poTramitesArray.length; i++)
			oTramites.add(getTramiteServicio(poTramitesArray[i]));
		
		return oTramites;
	}
	
	private Documentos getDocumentosWS(ieci.tecdoc.sgm.core.services.catalogo.Documentos poDocumentos){
		if (poDocumentos == null)
			return null;
		
		Documentos oDocumentos = new Documentos();
		DocumentoExtendido[] poDocumentosArray = new DocumentoExtendido[poDocumentos.count()];
		for (int i=0; i<poDocumentos.count(); i++)
			poDocumentosArray[i] = getDocumentoExtendidoWS((ieci.tecdoc.sgm.core.services.catalogo.DocumentoExtendido)poDocumentos.get(i));
		oDocumentos.setDocumentos(poDocumentosArray);
		return oDocumentos;
	}
	
	private ieci.tecdoc.sgm.core.services.catalogo.Documentos getDocumentosServicio(Documentos poDocumentos){
		if (poDocumentos == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catalogo.Documentos oDocumentos = new ieci.tecdoc.sgm.core.services.catalogo.Documentos();
		DocumentoExtendido[] poDocumentosArray = poDocumentos.getDocumentos();
		for (int i=0; i<poDocumentosArray.length; i++)
			oDocumentos.add(getDocumentoExtendidoServicio(poDocumentosArray[i]));
		
		return oDocumentos;
	}
	
	private DocumentoExtendido getDocumentoExtendidoWS(ieci.tecdoc.sgm.core.services.catalogo.DocumentoExtendido poDocumento){
		if (poDocumento == null)
			return null;
		
		DocumentoExtendido oDocumento = new DocumentoExtendido();
		oDocumento.setDescription(poDocumento.getDescription());
		oDocumento.setExtension(poDocumento.getExtension());
		oDocumento.setId(poDocumento.getId());
		oDocumento.setSignatureHook(poDocumento.getSignatureHook());
		oDocumento.setValidationHook(poDocumento.getValidationHook());
		oDocumento.setCode(poDocumento.getCode());
		oDocumento.setMandatory(""+poDocumento.isMandatory());
		
		return oDocumento;
	}
	
	private ieci.tecdoc.sgm.core.services.catalogo.DocumentoExtendido getDocumentoExtendidoServicio(DocumentoExtendido poDocumento){
		if (poDocumento == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catalogo.DocumentoExtendido oDocumento = new ieci.tecdoc.sgm.core.services.catalogo.DocumentoExtendido();
		oDocumento.setDescription(poDocumento.getDescription());
		oDocumento.setExtension(poDocumento.getExtension());
		oDocumento.setId(poDocumento.getId());
		oDocumento.setSignatureHook(poDocumento.getSignatureHook());
		oDocumento.setValidationHook(poDocumento.getValidationHook());
		oDocumento.setCode(poDocumento.getCode());
		oDocumento.setMandatory(new Boolean(poDocumento.getMandatory()).booleanValue());
		
		return oDocumento;
	}
	
	private RetornoLogico getRetornoLogicoWS(boolean poRetornoLogico){
		RetornoLogico oRetornoLogico = new RetornoLogico ();
		if (poRetornoLogico)
			oRetornoLogico.setValor(ConstantesServicios.LABEL_TRUE);
		else oRetornoLogico.setValor(ConstantesServicios.LABEL_FALSE);
		return oRetornoLogico;
	}
	
	private boolean getRetornoLogicoServicio(RetornoLogico poRetornoLogico){
		if (poRetornoLogico != null && ConstantesServicios.LABEL_TRUE.equals(poRetornoLogico))
			return true;
		return false;
	}
	
	private TramiteConsulta getTramiteConsultaWS(ieci.tecdoc.sgm.core.services.catalogo.TramiteConsulta poTramiteConsulta){
		if (poTramiteConsulta == null)
			return null;
		
		TramiteConsulta oTramiteConsulta = new TramiteConsulta();
		oTramiteConsulta.setAddressee(poTramiteConsulta.getAddressee());
		oTramiteConsulta.setId(poTramiteConsulta.getId());
		oTramiteConsulta.setSubject(poTramiteConsulta.getSubject());
		oTramiteConsulta.setSubtype(poTramiteConsulta.getSubtype());
		oTramiteConsulta.setTopic(poTramiteConsulta.getTopic());
		oTramiteConsulta.setType(poTramiteConsulta.getType());
		
		return oTramiteConsulta;
	}
	
	private ieci.tecdoc.sgm.core.services.catalogo.TramiteConsulta getTramiteConsultaServicio(TramiteConsulta poTramiteConsulta){
		if (poTramiteConsulta == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catalogo.TramiteConsulta oTramiteConsulta = new ieci.tecdoc.sgm.core.services.catalogo.TramiteConsulta();
		oTramiteConsulta.setAddressee(poTramiteConsulta.getAddressee());
		oTramiteConsulta.setId(poTramiteConsulta.getId());
		oTramiteConsulta.setSubject(poTramiteConsulta.getSubject());
		oTramiteConsulta.setSubtype(poTramiteConsulta.getSubtype());
		oTramiteConsulta.setTopic(poTramiteConsulta.getTopic());
		oTramiteConsulta.setType(poTramiteConsulta.getType());
		
		return oTramiteConsulta;
	}
	
	private ieci.tecdoc.sgm.core.services.catalogo.DocumentoTramite getDocumentoTramiteServicio(DocumentoTramite poDocumentoTramite){
		if (poDocumentoTramite == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catalogo.DocumentoTramite oDocumentoTramite = new ieci.tecdoc.sgm.core.services.catalogo.DocumentoTramite();
		oDocumentoTramite.setCode(poDocumentoTramite.getCode());
		oDocumentoTramite.setDocumentId(poDocumentoTramite.getDocumentId());
		oDocumentoTramite.setMandatory(new Boolean(poDocumentoTramite.getMandatory()).booleanValue());
		oDocumentoTramite.setProcedureId(poDocumentoTramite.getProcedureId());
		
		return oDocumentoTramite;
	}
	
	private DocumentoTramite getDocumentoTramiteWS(ieci.tecdoc.sgm.core.services.catalogo.DocumentoTramite poDocumentoTramite){
		if (poDocumentoTramite == null)
			return null;
		
		DocumentoTramite oDocumentoTramite = new DocumentoTramite();
		oDocumentoTramite.setCode(poDocumentoTramite.getCode());
		oDocumentoTramite.setDocumentId(poDocumentoTramite.getDocumentId());
		oDocumentoTramite.setMandatory(""+poDocumentoTramite.isMandatory());
		oDocumentoTramite.setProcedureId(poDocumentoTramite.getProcedureId());
		
		return oDocumentoTramite;
	}
	
	private ieci.tecdoc.sgm.core.services.catalogo.Documento getDocumentoServicio(Documento poDocumento){
		if (poDocumento == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catalogo.Documento oDocumento = new ieci.tecdoc.sgm.core.services.catalogo.Documento();
		oDocumento.setDescription(poDocumento.getDescription());
		oDocumento.setExtension(poDocumento.getExtension());
		oDocumento.setId(poDocumento.getId());
		oDocumento.setSignatureHook(poDocumento.getSignatureHook());
		oDocumento.setValidationHook(poDocumento.getValidationHook());
		
		return oDocumento;
	}
	
	private Documento getDocumentoWS(ieci.tecdoc.sgm.core.services.catalogo.Documento poDocumento){
		if (poDocumento == null)
			return null;
		
		Documento oDocumento = new Documento();
		oDocumento.setDescription(poDocumento.getDescription());
		oDocumento.setExtension(poDocumento.getExtension());
		oDocumento.setId(poDocumento.getId());
		oDocumento.setSignatureHook(poDocumento.getSignatureHook());
		oDocumento.setValidationHook(poDocumento.getValidationHook());
		
		return oDocumento;
	}
	
	private ieci.tecdoc.sgm.core.services.catalogo.OrganoDestinatario getOrganoDestinatarioServicio(OrganoDestinatario poOrganoDestinatario){
		if (poOrganoDestinatario == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catalogo.OrganoDestinatario oOrganoDestinatario = new ieci.tecdoc.sgm.core.services.catalogo.OrganoDestinatario();
		oOrganoDestinatario.setDescription(poOrganoDestinatario.getDescription());
		oOrganoDestinatario.setId(poOrganoDestinatario.getId());
		
		return oOrganoDestinatario;
	}
	
	private OrganoDestinatario getOrganoDestinatarioWS(ieci.tecdoc.sgm.core.services.catalogo.OrganoDestinatario poOrganoDestinatario){
		if (poOrganoDestinatario == null)
			return null;
		
		OrganoDestinatario oOrganoDestinatario = new OrganoDestinatario();
		oOrganoDestinatario.setDescription(poOrganoDestinatario.getDescription());
		oOrganoDestinatario.setId(poOrganoDestinatario.getId());
		
		return oOrganoDestinatario;
	}
	
	private ieci.tecdoc.sgm.core.services.catalogo.OrganosDestinatarios getOrganosDestinatariosServicio(OrganosDestinatarios poOrganosDestinatarios){
		if (poOrganosDestinatarios == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catalogo.OrganosDestinatarios oOrganosDestinatarios = new ieci.tecdoc.sgm.core.services.catalogo.OrganosDestinatarios();
		OrganoDestinatario[] poOrganosDestinatariosArray = poOrganosDestinatarios.getOrganosDestinatarios();
		for (int i=0; i<poOrganosDestinatariosArray.length; i++)
			oOrganosDestinatarios.add(getOrganoDestinatarioServicio(poOrganosDestinatariosArray[i]));
		
		return oOrganosDestinatarios;
	}
	
	private Conector getConectorWS(ieci.tecdoc.sgm.core.services.catalogo.Conector poConector){
		if (poConector == null)
			return null;
		
		Conector oConector = new Conector();
		oConector.setDescription(poConector.getDescription());
		oConector.setId(poConector.getId());
		oConector.setType(""+poConector.getType());
		
		return oConector;
	}
	
	private ieci.tecdoc.sgm.core.services.catalogo.Conector getConectorServicio(Conector poConector){
		if (poConector == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catalogo.Conector oConector = new ieci.tecdoc.sgm.core.services.catalogo.Conector();
		poConector.setDescription(poConector.getDescription());
		poConector.setId(poConector.getId());
		
		return oConector;
	}
	
	private ieci.tecdoc.sgm.core.services.catalogo.Conectores getConectoresServicio(Conectores poConectores){
		if (poConectores == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catalogo.Conectores oConectores = new ieci.tecdoc.sgm.core.services.catalogo.Conectores();
		Conector[] poConectoresArray = poConectores.getConectores();
		for (int i=0; i<poConectoresArray.length; i++)
			oConectores.add(getConectorServicio(poConectoresArray[i]));
		
		return oConectores;
	}
	
	private ieci.tecdoc.sgm.core.services.catalogo.TipoConector getTipoConectorServicio(TipoConector poTipoConector){
		if (poTipoConector == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catalogo.TipoConector oTipoConector = new ieci.tecdoc.sgm.core.services.catalogo.TipoConector();
		oTipoConector.setDescription(poTipoConector.getDescription());
		oTipoConector.setId(new Integer(poTipoConector.getId()).intValue());
		
		return oTipoConector;
	}
	
	private TipoConector getTipoConectorWS(ieci.tecdoc.sgm.core.services.catalogo.TipoConector poTipoConector){
		if (poTipoConector == null)
			return null;
		
		TipoConector oTipoConector = new TipoConector();
		oTipoConector.setDescription(poTipoConector.getDescription());
		oTipoConector.setId(""+poTipoConector.getId());
		
		return oTipoConector;
	}
	
	private ieci.tecdoc.sgm.core.services.catalogo.TiposConectores getTiposConectoresServicio(TiposConectores poTiposConectores){
		if (poTiposConectores == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catalogo.TiposConectores oTiposConectores = new ieci.tecdoc.sgm.core.services.catalogo.TiposConectores();
		TipoConector[] poTiposConectoresArray = poTiposConectores.getTiposConectores();
		for (int i=0; i<poTiposConectoresArray.length; i++)
			oTiposConectores.add(getTipoConectorServicio(poTiposConectoresArray[i]));
		
		return oTiposConectores;
	}
	
	private ConectorAutenticacion getConectorAutenticacionWS(ieci.tecdoc.sgm.core.services.catalogo.ConectorAutenticacion poConectorAutenticacion){
		if (poConectorAutenticacion == null)
			return null;
		
		ConectorAutenticacion oConectorAutenticacion = new ConectorAutenticacion();
		oConectorAutenticacion.setConectorId(poConectorAutenticacion.getConectorId());
		oConectorAutenticacion.setTramiteId(poConectorAutenticacion.getTramiteId());
		
		return oConectorAutenticacion;
	}
	
	private ieci.tecdoc.sgm.core.services.catalogo.ConectorAutenticacion getConectorAutenticacionServicio(ConectorAutenticacion poConectorAutenticacion){
		if (poConectorAutenticacion == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catalogo.ConectorAutenticacion oConectorAutenticacion = new ieci.tecdoc.sgm.core.services.catalogo.ConectorAutenticacion();
		oConectorAutenticacion.setConectorId(poConectorAutenticacion.getConectorId());
		oConectorAutenticacion.setTramiteId(poConectorAutenticacion.getTramiteId());
		
		return oConectorAutenticacion;
	}
	
	private ieci.tecdoc.sgm.core.services.catalogo.ConectoresAutenticacion getConectoresAutenticacionServicio(ConectoresAutenticacion poConectoresAutenticacion){
		if (poConectoresAutenticacion == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catalogo.ConectoresAutenticacion oConectoresAutenticacion = new ieci.tecdoc.sgm.core.services.catalogo.ConectoresAutenticacion();
		ConectorAutenticacion[] poConectoresAutenticacionArray = poConectoresAutenticacion.getConectoresAutenticacion();
		for (int i=0; i<poConectoresAutenticacionArray.length; i++)
			oConectoresAutenticacion.add(getConectorAutenticacionServicio(poConectoresAutenticacionArray[i]));
		
		return oConectoresAutenticacion;
	}
	
	private ieci.tecdoc.sgm.catalogo.ws.client.Entidad getEntidadWS(Entidad poEntidad){
		if(poEntidad ==  null){
			return null;
		}
		ieci.tecdoc.sgm.catalogo.ws.client.Entidad oEntidad = new ieci.tecdoc.sgm.catalogo.ws.client.Entidad();
		oEntidad.setIdentificador(poEntidad.getIdentificador());
		oEntidad.setNombre(poEntidad.getNombre());
		return oEntidad;
	}
}
