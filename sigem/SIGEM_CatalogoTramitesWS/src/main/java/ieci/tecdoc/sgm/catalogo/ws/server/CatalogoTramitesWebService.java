package ieci.tecdoc.sgm.catalogo.ws.server;
/*
 * $Id: CatalogoTramitesWebService.java,v 1.2.2.3 2008/10/13 08:51:13 jnogales Exp $
 */
import javax.xml.soap.SOAPException;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.catalogo.CatalogoTramitesExcepcion;
import ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.core.ws.axis.UtilAxis;

import org.apache.log4j.Logger;

public class CatalogoTramitesWebService {

	private static final Logger logger = Logger.getLogger(CatalogoTramitesWebService.class);
	
	private static final String SERVICE_NAME = ConstantesServicios.SERVICE_PROCEDURES;

	private ServicioCatalogoTramites getServicioCatalogoTramites() throws SOAPException, SigemException{
		String cImpl = UtilAxis.getImplementacion(org.apache.axis.MessageContext.getCurrentContext());
		if((cImpl == null) ||("".equals(cImpl))){
			return LocalizadorServicios.getServicioCatalogoTramites();
		}else{
			StringBuffer sbImpl = new StringBuffer(SERVICE_NAME).append(".").append(cImpl);
			return LocalizadorServicios.getServicioCatalogoTramites(sbImpl.toString());
		}
	}
	
	public RetornoServicio addProcedure(Tramite procedure, Entidad entidad) {
		try {	
			getServicioCatalogoTramites().addProcedure(getTramiteServicio(procedure), entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al insertar tramite.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al insertar tramite.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al insertar tramite.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public Tramite getProcedure(String procedureId, RetornoLogico loadDocuments, Entidad entidad) {
		try {	
			Tramite tramite = getTramiteWS(
					getServicioCatalogoTramites().getProcedure(procedureId, getRetornoLogicoServicio(loadDocuments), entidad)
				);
			return (Tramite)ServiciosUtils.completeReturnOK(tramite);
		} catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener un tramite.", e);
			return (Tramite)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Tramite()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener un tramite.", e);
			return (Tramite)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Tramite()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener un tramite.", e);
			return (Tramite)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Tramite()));			
		}
   }
	
	public Tramites query(TramiteConsulta query, Entidad entidad) {
		try {	
			Tramites tramites = getTramitesWS(
					getServicioCatalogoTramites().query(getTramiteConsultaServicio(query), entidad)
				);
			return (Tramites)ServiciosUtils.completeReturnOK(tramites);
		} catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener tramites.", e);
			return (Tramites)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Tramites()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener tramites.", e);
			return (Tramites)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Tramites()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener tramite.", e);
			return (Tramites)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Tramites()));			
		}
	}
	
	public RetornoServicio deleteProcedure(String procedureId, Entidad entidad) {
		try {	
			getServicioCatalogoTramites().deleteProcedure(procedureId, entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al eliminar  tramite.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al eliminar tramite.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al eliminar tramite.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public RetornoServicio updateProcedure(Tramite procedure, Entidad entidad) {
		try {	
			getServicioCatalogoTramites().updateProcedure(getTramiteServicio(procedure), entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al actualizar tramite.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al actualizar tramite.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al actualizar tramite.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public RetornoLogico isDocumentReferenced(String documentId, Entidad entidad) {
		try {	
			RetornoLogico oRetorno = getRetornoLogicoWS(
					getServicioCatalogoTramites().isDocumentReferenced(documentId, entidad)
				);
			return (RetornoLogico)ServiciosUtils.completeReturnOK(oRetorno);
		} catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener tramites.", e);
			return (RetornoLogico)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoLogico()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener tramites.", e);
			return (RetornoLogico)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoLogico()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener tramite.", e);
			return (RetornoLogico)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new RetornoLogico()));			
		}
	}
	
	public RetornoServicio addProcedureDocument(DocumentoTramite procedureDocument, Entidad entidad) {
		try {	
			getServicioCatalogoTramites().addProcedureDocument(getDocumentoTramiteServicio(procedureDocument), entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al asociar un documento a tramite.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al asociar un documento a tramite.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al asociar un documento a tramite.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public RetornoServicio deleteProcedureDocument(DocumentoTramite procedureDocument, Entidad entidad) {
		try {	
			getServicioCatalogoTramites().deleteProcedureDocument(getDocumentoTramiteServicio(procedureDocument), entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al eliminar la asociacion de un documento a tramite.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al eliminar la asociacion de un documento a tramite.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al eliminar la asociacion de un documento a tramite.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public Documento getDocument(String documentId, Entidad entidad) {
		try {	
			Documento documento = getDocumentoWS(
					getServicioCatalogoTramites().getDocument(documentId, entidad)
				);
			return (Documento)ServiciosUtils.completeReturnOK(documento);
		} catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener documento.", e);
			return (Documento)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Documento()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener documento.", e);
			return (Documento)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Documento()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener documento.", e);
			return (Documento)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Documento()));			
		}
	}
	
	public RetornoServicio addDocument(Documento document, Entidad entidad) {
		try {	
			getServicioCatalogoTramites().addDocument(getDocumentoServicio(document), entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al insertar documento.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al insertar documento.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al insertar documento.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public RetornoServicio deleteDocument(String documentId, Entidad entidad) {
		try {	
			getServicioCatalogoTramites().deleteDocument(documentId, entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al eliminar documento.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al eliminar documento.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al eliminar documento.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public RetornoServicio updateDocument(Documento document, Entidad entidad) {
		try {	
			getServicioCatalogoTramites().updateDocument(getDocumentoServicio(document), entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al actualizar documento.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al actualizar documento.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al actualizar documento.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public Documentos getDocuments(Entidad entidad) {
		try {	
			Documentos documentos = getDocumentosWS(
					getServicioCatalogoTramites().getDocuments(entidad)
				);
			return (Documentos)ServiciosUtils.completeReturnOK(documentos);
		} catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener documentos.", e);
			return (Documentos)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Documentos()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener documentos.", e);
			return (Documentos)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Documentos()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener documentos.", e);
			return (Documentos)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Documentos()));			
		}
	}
	
	public Documentos getProcedureDocuments(String procedureId, Entidad entidad) {
		try {	
			Documentos documentos = getDocumentosWS(
					getServicioCatalogoTramites().getProcedureDocuments(procedureId, entidad)
				);
			return (Documentos)ServiciosUtils.completeReturnOK(documentos);
		} catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener documentos de tramite.", e);
			return (Documentos)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Documentos()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener documentos de tramite.", e);
			return (Documentos)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Documentos()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener documentos de tramite.", e);
			return (Documentos)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Documentos()));			
		}
	}
	
	public DocumentoTramite getProcedureDocument(String procedureId, String documentId, Entidad entidad) {
		try {	
			DocumentoTramite documentoTramite = getDocumentoTramiteWS(
					getServicioCatalogoTramites().getProcedureDocument(procedureId, documentId, entidad)
				);
			return (DocumentoTramite)ServiciosUtils.completeReturnOK(documentoTramite);
		} catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener documentos de tramite.", e);
			return (DocumentoTramite)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new DocumentoTramite()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener documentos de tramite.", e);
			return (DocumentoTramite)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new DocumentoTramite()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener documentos de tramite.", e);
			return (DocumentoTramite)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new DocumentoTramite()));			
		}
	}
	
	public DocumentoTramite getProcedureDocumentByCode(String procedureId, String documentId, String code, Entidad entidad) {
		try {	
			DocumentoTramite documentoTramite = getDocumentoTramiteWS(
					getServicioCatalogoTramites().getProcedureDocument(procedureId, documentId, code, entidad)
				);
			return (DocumentoTramite)ServiciosUtils.completeReturnOK(documentoTramite);
		} catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener documentos de tramite.", e);
			return (DocumentoTramite)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new DocumentoTramite()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener documentos de tramite.", e);
			return (DocumentoTramite)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new DocumentoTramite()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener documentos de tramite.", e);
			return (DocumentoTramite)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new DocumentoTramite()));			
		}
	}
	
	public RetornoServicio updateProcedureDocument(DocumentoTramite procedureDocument, Entidad entidad) {
		try {	
			getServicioCatalogoTramites().updateProcedureDocument(getDocumentoTramiteServicio(procedureDocument), entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al actualizar documento de tramite.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al actualizar documento de tramite.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al actualizar documento de tramite.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public Tramites getProcedures(Entidad entidad) {
		try {	
			Tramites documentos = getTramitesWS(
					getServicioCatalogoTramites().getProcedures(entidad)
				);
			return (Tramites)ServiciosUtils.completeReturnOK(documentos);
		} catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener tramites.", e);
			return (Tramites)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Tramites()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener tramites.", e);
			return (Tramites)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Tramites()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener tramites.", e);
			return (Tramites)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Tramites()));			
		}
	}
	
	public Documento getDocumentfromCode (String code, Entidad entidad) {
		try {	
			Documento documento = getDocumentoWS(
					getServicioCatalogoTramites().getDocumentfromCode(code, entidad)
				);
			return (Documento)ServiciosUtils.completeReturnOK(documento);
		} catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener documento por codigo.", e);
			return (Documento)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Documento()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener documento por codigo.", e);
			return (Documento)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Documento()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener documento por codigo.", e);
			return (Documento)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Documento()));			
		}
	}
	
	public OrganoDestinatario getAddressee(String addresseeId, Entidad entidad) {
		try {	
			OrganoDestinatario organoDestinatario = getOrganoDestinatarioWS(
					getServicioCatalogoTramites().getAddressee(addresseeId, entidad)
				);
			return (OrganoDestinatario)ServiciosUtils.completeReturnOK(organoDestinatario);
		} catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener organo destinatario.", e);
			return (OrganoDestinatario)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new OrganoDestinatario()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener organo destinatario.", e);
			return (OrganoDestinatario)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new OrganoDestinatario()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener organo destinatario.", e);
			return (OrganoDestinatario)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new OrganoDestinatario()));			
		}
	}
	
	public RetornoServicio addAddressee(OrganoDestinatario addressee, Entidad entidad) {
		try {	
			getServicioCatalogoTramites().addAddressee(getOrganoDestinatarioServicio(addressee), entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al insertar organo destinatario.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al insertar organo destinatario.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al insertar organo destinatario.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public RetornoServicio deleteAddressee(String addresseeId, Entidad entidad) {
		try {	
			getServicioCatalogoTramites().deleteAddressee(addresseeId, entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al eliminar organo destinatario.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al eliminar organo destinatario.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al eliminar organo destinatario.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public RetornoServicio updateAddressee(OrganoDestinatario addressee, Entidad entidad) {
		try {	
			getServicioCatalogoTramites().updateAddressee(getOrganoDestinatarioServicio(addressee), entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al actualizar organo destinatario.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al actualizar organo destinatario.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al actualizar organo destinatario.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public OrganosDestinatarios getAddressees(Entidad entidad) {
		try {	
			OrganosDestinatarios organosDestinatarios = getOrganosDestinatariosWS(
					getServicioCatalogoTramites().getAddressees(entidad)
				);
			return (OrganosDestinatarios)ServiciosUtils.completeReturnOK(organosDestinatarios);
		} catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener organos destinatarios.", e);
			return (OrganosDestinatarios)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new OrganosDestinatarios()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener organos destinatarios.", e);
			return (OrganosDestinatarios)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new OrganosDestinatarios()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener organos destinatarios.", e);
			return (OrganosDestinatarios)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new OrganosDestinatarios()));			
		}
	}
	
	public Conector getHook(String hookId, Entidad entidad) {
		try {	
			Conector conector = getConectorWS(
					getServicioCatalogoTramites().getHook(hookId, entidad)
				);
			return (Conector)ServiciosUtils.completeReturnOK(conector);
		} catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener conector.", e);
			return (Conector)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Conector()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener conector.", e);
			return (Conector)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Conector()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener conector.", e);
			return (Conector)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Conector()));			
		}
	}
	
	public RetornoServicio addHook(Conector hook, Entidad entidad) {
		try {	
			getServicioCatalogoTramites().addHook(getConectorServicio(hook), entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al insertar conector.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al insertar conector.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al insertar conector.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public RetornoServicio deleteHook(String hookId, Entidad entidad) {
		try {	
			getServicioCatalogoTramites().deleteHook(hookId, entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al eliminar conector.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al eliminar conector.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al eliminar conector.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public RetornoServicio updateHook(Conector hook, Entidad entidad) {
		try {	
			getServicioCatalogoTramites().updateHook(getConectorServicio(hook), entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al actualizar conector.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al actualizar conector.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al actualizar conector.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public Conectores getHooks(Entidad entidad) {
		try {	
			Conectores conectores = getConectoresWS(
					getServicioCatalogoTramites().getHooks(entidad)
				);
			return (Conectores)ServiciosUtils.completeReturnOK(conectores);
		} catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener conectores.", e);
			return (Conectores)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Conectores()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener conectores.", e);
			return (Conectores)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Conectores()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener conectores.", e);
			return (Conectores)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Conectores()));			
		}
	}
	
	public Conectores getHooksByType(String hookType, Entidad entidad) {
		try {	
			Conectores conectores = getConectoresWS(
					getServicioCatalogoTramites().getHooksByType(new Integer(hookType).intValue(), entidad)
				);
			return (Conectores)ServiciosUtils.completeReturnOK(conectores);
		} catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener conectores.", e);
			return (Conectores)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Conectores()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener conectores.", e);
			return (Conectores)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Conectores()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener conectores.", e);
			return (Conectores)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Conectores()));			
		}
	}
	
	public TipoConector getHookType(String typeId, Entidad entidad) {
		try {	
			TipoConector tipoConector = getTipoConectorWS(
					getServicioCatalogoTramites().getHookType(new Integer(typeId).intValue(), entidad)
				);
			return (TipoConector)ServiciosUtils.completeReturnOK(tipoConector);
		} catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener tipo de conector.", e);
			return (TipoConector)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new TipoConector()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener tipo de conector.", e);
			return (TipoConector)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new TipoConector()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener tipo de conector.", e);
			return (TipoConector)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new TipoConector()));			
		}
	}
	
	public RetornoServicio addHookType(TipoConector hookType, Entidad entidad) {
		try {	
			getServicioCatalogoTramites().addHookType(getTipoConectorServicio(hookType), entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al insertar tipo de conector.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al insertar tipo de conector.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al insertar tipo de conector.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public RetornoServicio deleteHookType(String typeId, Entidad entidad) {
		try {	
			getServicioCatalogoTramites().deleteHookType(new Integer(typeId).intValue(), entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al eliminar tipo de conector.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al eliminar tipo de conector.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al eliminar tipo de conector.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public RetornoServicio updateHookType(TipoConector hookType, Entidad entidad) {
		try {	
			getServicioCatalogoTramites().updateHookType(getTipoConectorServicio(hookType), entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al actualizar tipo de conector.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al actualizar tipo de conector.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al actualizar tipo de conector.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public TiposConectores getHookTypes(Entidad entidad) {
		try {	
			TiposConectores tiposConectores = getTiposConectoresWS(
					getServicioCatalogoTramites().getHookTypes(entidad)
				);
			return (TiposConectores)ServiciosUtils.completeReturnOK(tiposConectores);
		} catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener los tipos de conectores.", e);
			return (TiposConectores)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new TiposConectores()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener los tipos de conectores.", e);
			return (TiposConectores)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new TiposConectores()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener los tipos de conectores.", e);
			return (TiposConectores)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new TiposConectores()));			
		}
	}
	
	public ConectoresAutenticacion getAuthHooks(String tramiteId, Entidad entidad) {
		try {	
			ConectoresAutenticacion conectoresAutenticacion = getConectoresAutenticacionWS(
					getServicioCatalogoTramites().getAuthHooks(tramiteId, entidad)
				);
			return (ConectoresAutenticacion)ServiciosUtils.completeReturnOK(conectoresAutenticacion);
		} catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener los conectores de autenticacion.", e);
			return (ConectoresAutenticacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ConectoresAutenticacion()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener los conectores de autenticacion.", e);
			return (ConectoresAutenticacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ConectoresAutenticacion()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener los conectores de autenticacion.", e);
			return (ConectoresAutenticacion)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new ConectoresAutenticacion()));			
		}
	}
	
	public RetornoServicio addAuthHooks(ConectorAutenticacion authHook, Entidad entidad) {
		try {	
			getServicioCatalogoTramites().addAuthHooks(getConectorAutenticacionServicio(authHook), entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al insertar conector de autenticacion.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al insertar conector de autenticacion.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al insertar conector de autenticacion.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public RetornoServicio deleteAuthHooks(String tramiteId, String conectorId, Entidad entidad) {
		try {	
			getServicioCatalogoTramites().deleteAuthHooks(tramiteId, conectorId, entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al eliminar conector de autenticacion.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al eliminar conector de autenticacion.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al eliminar conector de autenticacion.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public RetornoServicio updateAuthHooks(ConectorAutenticacion conectorAutenticacion, String oldHookId, Entidad entidad) {
		try {	
			getServicioCatalogoTramites().updateAuthHooks(getConectorAutenticacionServicio(conectorAutenticacion), oldHookId, entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al actualizar conector de autenticacion.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al actualizar conector de autenticacion.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al actualizar conector de autenticacion.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public ConectorAutenticacion getAuthHook(String tramiteId, String conectorId, Entidad entidad) {
		try {	
			ConectorAutenticacion conectorAutenticacion = getConectorAutenticacionWS(
					getServicioCatalogoTramites().getAuthHook(tramiteId, conectorId, entidad)
				);
			return (ConectorAutenticacion)ServiciosUtils.completeReturnOK(conectorAutenticacion);
		} catch (CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener los conectores de autenticacion.", e);
			return (ConectorAutenticacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ConectorAutenticacion()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener los conectores de autenticacion.", e);
			return (ConectorAutenticacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ConectorAutenticacion()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener los conectores de autenticacion.", e);
			return (ConectorAutenticacion)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new ConectorAutenticacion()));			
		}
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
		oDocumento.setMandatory(new Boolean(poDocumento.isMandatory()).booleanValue());
		
		return oDocumento;
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
	
	private ieci.tecdoc.sgm.core.services.catalogo.Documentos getDocumentosServicio(Documentos poDocumentos){
		if (poDocumentos == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catalogo.Documentos oDocumentos = new ieci.tecdoc.sgm.core.services.catalogo.Documentos();
		DocumentoExtendido[] poDocumentosArray = poDocumentos.getDocumentos();
		for (int i=0; i<poDocumentosArray.length; i++)
			oDocumentos.add(getDocumentoExtendidoServicio(poDocumentosArray[i]));
		
		return oDocumentos;
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
	
	private Tramites getTramitesWS(ieci.tecdoc.sgm.core.services.catalogo.Tramites poTramites){
		if (poTramites == null)
			return null;
		
		Tramites oTramites = new Tramites();
		Tramite[] poTramitesArray = new Tramite[poTramites.count()];
		for (int i=0; i<poTramites.count(); i++)
			poTramitesArray[i] = getTramiteWS((ieci.tecdoc.sgm.core.services.catalogo.Tramite)poTramites.get(i));
		oTramites.setTramites(poTramitesArray);
		return oTramites;
	}
	
	private ieci.tecdoc.sgm.core.services.catalogo.DocumentoTramite getDocumentoTramiteServicio(DocumentoTramite poDocumentoTramite){
		if (poDocumentoTramite == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catalogo.DocumentoTramite oDocumentoTramite = new ieci.tecdoc.sgm.core.services.catalogo.DocumentoTramite();
		oDocumentoTramite.setCode(poDocumentoTramite.getCode());
		oDocumentoTramite.setDocumentId(poDocumentoTramite.getDocumentId());
		oDocumentoTramite.setMandatory(new Boolean(poDocumentoTramite.isMandatory()).booleanValue());
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
	
	private OrganoDestinatario getOrganoDestinatarioWS(ieci.tecdoc.sgm.core.services.catalogo.OrganoDestinatario poOrganoDestinatario){
		if (poOrganoDestinatario == null)
			return null;
		
		OrganoDestinatario oOrganoDestinatario = new OrganoDestinatario();
		oOrganoDestinatario.setDescription(poOrganoDestinatario.getDescription());
		oOrganoDestinatario.setId(poOrganoDestinatario.getId());
		
		return oOrganoDestinatario;
	}
	
	private ieci.tecdoc.sgm.core.services.catalogo.OrganoDestinatario getOrganoDestinatarioServicio(OrganoDestinatario poOrganoDestinatario){
		if (poOrganoDestinatario == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catalogo.OrganoDestinatario oOrganoDestinatario = new ieci.tecdoc.sgm.core.services.catalogo.OrganoDestinatario();
		oOrganoDestinatario.setDescription(poOrganoDestinatario.getDescription());
		oOrganoDestinatario.setId(poOrganoDestinatario.getId());
		
		return oOrganoDestinatario;
	}
	
	private OrganosDestinatarios getOrganosDestinatariosWS(ieci.tecdoc.sgm.core.services.catalogo.OrganosDestinatarios poOrganosDestinatarios){
		if (poOrganosDestinatarios == null)
			return null;
		
		OrganosDestinatarios oOrganosDestinatarios = new OrganosDestinatarios();
		OrganoDestinatario[] poOrganosDestinatariosArray = new OrganoDestinatario[poOrganosDestinatarios.count()];
		for (int i=0; i<poOrganosDestinatarios.count(); i++)
			poOrganosDestinatariosArray[i] = getOrganoDestinatarioWS((ieci.tecdoc.sgm.core.services.catalogo.OrganoDestinatario)poOrganosDestinatarios.get(i));
		oOrganosDestinatarios.setOrganosDestinatarios(poOrganosDestinatariosArray);
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
	
	private Conectores getConectoresWS(ieci.tecdoc.sgm.core.services.catalogo.Conectores poConectores){
		if (poConectores == null)
			return null;
		
		Conectores oConectores = new Conectores();
		Conector[] poConectoresArray = new Conector[poConectores.count()];
		for (int i=0; i<poConectores.count(); i++)
			poConectoresArray[i] = getConectorWS((ieci.tecdoc.sgm.core.services.catalogo.Conector)poConectores.get(i));
		oConectores.setConectores(poConectoresArray);
		return oConectores;
	}
	
	private TipoConector getTipoConectorWS(ieci.tecdoc.sgm.core.services.catalogo.TipoConector poTipoConector){
		if (poTipoConector == null)
			return null;
		
		TipoConector oTipoConector = new TipoConector();
		oTipoConector.setDescription(poTipoConector.getDescription());
		oTipoConector.setId(""+poTipoConector.getId());
		
		return oTipoConector;
	}
	
	private ieci.tecdoc.sgm.core.services.catalogo.TipoConector getTipoConectorServicio(TipoConector poTipoConector){
		if (poTipoConector == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catalogo.TipoConector oTipoConector = new ieci.tecdoc.sgm.core.services.catalogo.TipoConector();
		oTipoConector.setDescription(poTipoConector.getDescription());
		oTipoConector.setId(new Integer(poTipoConector.getId()).intValue());
		
		return oTipoConector;
	}
	
	private TiposConectores getTiposConectoresWS(ieci.tecdoc.sgm.core.services.catalogo.TiposConectores poTiposConectores){
		if (poTiposConectores == null)
			return null;
		
		TiposConectores oTiposConectores = new TiposConectores();
		TipoConector[] poTiposConectoresArray = new TipoConector[poTiposConectores.count()];
		for (int i=0; i<poTiposConectores.count(); i++)
			poTiposConectoresArray[i] = getTipoConectorWS((ieci.tecdoc.sgm.core.services.catalogo.TipoConector)poTiposConectores.get(i));
		oTiposConectores.setTiposConectores(poTiposConectoresArray);
		return oTiposConectores;
	}
	
	private ConectoresAutenticacion getConectoresAutenticacionWS(ieci.tecdoc.sgm.core.services.catalogo.ConectoresAutenticacion poConectoresAutenticacion){
		if (poConectoresAutenticacion == null)
			return null;
		
		ConectoresAutenticacion oConectoresAutenticacion = new ConectoresAutenticacion();
		ConectorAutenticacion[] poConectoresAutenticacionArray = new ConectorAutenticacion[poConectoresAutenticacion.count()];
		for (int i=0; i<poConectoresAutenticacion.count(); i++)
			poConectoresAutenticacionArray[i] = getConectorAutenticacionWS((ieci.tecdoc.sgm.core.services.catalogo.ConectorAutenticacion)poConectoresAutenticacion.get(i));
		oConectoresAutenticacion.setConectoresAutenticacion(poConectoresAutenticacionArray);
		return oConectoresAutenticacion;
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
}

