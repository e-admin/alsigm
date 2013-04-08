package ieci.tecdoc.sgm.rde.ws.server;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.xml.soap.SOAPException;

import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.core.base64.Base64;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.core.services.repositorio.DocumentoHashInfo;
import ieci.tecdoc.sgm.core.services.repositorio.DocumentoInfo;
import ieci.tecdoc.sgm.core.services.repositorio.DocumentoTemporal;
import ieci.tecdoc.sgm.core.services.repositorio.DocumentosTemporales;
import ieci.tecdoc.sgm.core.services.repositorio.GuidIncorrectoRdeExcepcion;
import ieci.tecdoc.sgm.core.services.repositorio.RepositorioDocumentosRdeExcepcion;
import ieci.tecdoc.sgm.core.services.repositorio.ServicioRepositorioDocumentosTramitacion;
import ieci.tecdoc.sgm.core.ws.axis.UtilAxis;

import org.apache.log4j.Logger;

public class RepositorioDocumentosWebService {

	private static final Logger logger = Logger.getLogger(RepositorioDocumentosWebService.class);
	
	private static final String SERVICE_NAME = ConstantesServicios.SERVICE_REPOSITORY;

	private static final String TEMP_FILE_PREFIX = "RDEWS_";
	
	public InfoDocumento recuperarDocumento(InfoDocumento poDocId, Entidad entidad){
		  
		try {
			InfoDocumento oDoc = getInfoDocumentoWS(
						getServicioRepositorioDocumentosTramitacion().retrieveDocument(null, poDocId.getGuid(), entidad)
						);
			return (InfoDocumento)ServiciosUtils.completeReturnOK((RetornoServicio) oDoc);
		} catch (GuidIncorrectoRdeExcepcion e) {
			logger.error("Error recuperando documento.", e);
			return (InfoDocumento)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new InfoDocumento()),
								e.getErrorCode());
		} catch (RepositorioDocumentosRdeExcepcion e) {
			logger.error("Error recuperando documento.", e);
			return (InfoDocumento)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new InfoDocumento()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error recuperando documento.", e);
			return (InfoDocumento)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new InfoDocumento()),
								e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error inesperado recuperando documento.", e);
			return (InfoDocumento)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new InfoDocumento()));					
		}
	  }
	  	  
	  public InfoDocumento guardarDocumento(InfoDocumento poDoc, Entidad entidad){
			try {
				InfoDocumento oDoc = new InfoDocumento();
				oDoc.setGuid(
							getServicioRepositorioDocumentosTramitacion()
								.storeDocument(	null, 
												Base64.decode(poDoc.getContenidoB64()), 
												poDoc.getExtension(),
												entidad)
							);
				return (InfoDocumento)ServiciosUtils.completeReturnOK((RetornoServicio) oDoc);
			} catch (GuidIncorrectoRdeExcepcion e) {
				logger.error("Error guardando documento.", e);
				return (InfoDocumento)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new InfoDocumento()),
									e.getErrorCode());
			} catch (RepositorioDocumentosRdeExcepcion e) {
				logger.error("Error guardando documento.", e);
				return (InfoDocumento)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new InfoDocumento()),
									e.getErrorCode());
			} catch (SigemException e) {
				logger.error("Error guardando documento.", e);
				return (InfoDocumento)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new InfoDocumento()),
									e.getErrorCode());
			} catch (Throwable e){
				logger.error("Error inesperado guardando documento.", e);
				return (InfoDocumento)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new InfoDocumento()));					
			}
	  }
	  
	  public InfoDocumento guardarDocumentoGuid(InfoDocumento poDoc, Entidad entidad){
			try {
				InfoDocumento oDoc = new InfoDocumento();
				oDoc.setGuid(
							getServicioRepositorioDocumentosTramitacion()
								.storeDocumentGuid(	null,
												poDoc.getGuid(),
												Base64.decode(poDoc.getContenidoB64()), 
												poDoc.getExtension(),
												entidad)
							);
				return (InfoDocumento)ServiciosUtils.completeReturnOK((RetornoServicio) oDoc);
			} catch (GuidIncorrectoRdeExcepcion e) {
				logger.error("Error guardando documento.", e);
				return (InfoDocumento)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new InfoDocumento()),
									e.getErrorCode());
			} catch (RepositorioDocumentosRdeExcepcion e) {
				logger.error("Error guardando documento.", e);
				return (InfoDocumento)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new InfoDocumento()),
									e.getErrorCode());
			} catch (SigemException e) {
				logger.error("Error guardando documento.", e);
				return (InfoDocumento)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new InfoDocumento()),
									e.getErrorCode());
			} catch (Throwable e){
				logger.error("Error inesperado guardando documento.", e);
				return (InfoDocumento)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new InfoDocumento()));					
			}
	  }
	  
	  public RetornoServicio eliminarDocumento(InfoDocumento poDoc, Entidad entidad){
			try {
				getServicioRepositorioDocumentosTramitacion()
					.deleteDocument(null, poDoc.getGuid(), entidad);
				return ServiciosUtils.createReturnOK();
			} catch (GuidIncorrectoRdeExcepcion e) {
				logger.error("Error borrando documento.", e);
				return ServiciosUtils.createReturnError(e.getErrorCode());
			} catch (RepositorioDocumentosRdeExcepcion e) {
				logger.error("Error borrando documento.", e);
				return ServiciosUtils.createReturnError(e.getErrorCode());
			} catch (SigemException e) {
				logger.error("Error borrando documento.", e);
				return ServiciosUtils.createReturnError(e.getErrorCode());
			} catch (Throwable e){
				logger.error("Error inesperado borrando documento.", e);
				return ServiciosUtils.createReturnError();			
			}
	  }
	  
	  public InfoDocumento obtenerHash(InfoDocumento poDoc, Entidad entidad){
			try {
				InfoDocumento oDoc = new InfoDocumento();
				oDoc.setHash(
							getServicioRepositorioDocumentosTramitacion()
								.getHash(null, poDoc.getGuid(), entidad)
							);
				return (InfoDocumento)ServiciosUtils.completeReturnOK((RetornoServicio) oDoc);
			} catch (GuidIncorrectoRdeExcepcion e) {
				logger.error("Error obteniendo hash.", e);
				return (InfoDocumento)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new InfoDocumento()),
									e.getErrorCode());
			} catch (RepositorioDocumentosRdeExcepcion e) {
				logger.error("Error obteniendo hash.", e);
				return (InfoDocumento)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new InfoDocumento()),
									e.getErrorCode());
			} catch (SigemException e) {
				logger.error("Error obteniendo hash.", e);
				return (InfoDocumento)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new InfoDocumento()),
									e.getErrorCode());
			} catch (Throwable e){
				logger.error("Error inesperado obteniendo hash.", e);
				return (InfoDocumento)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new InfoDocumento()));					
			}
	  }
	  

	  public RetornoServicio almacenarDocumentos(InfoDocumentos poDocs, Entidad entidad){
		  Vector oVector = null;
		  try {
				oVector = getVectorHashInfo(poDocs);
				getServicioRepositorioDocumentosTramitacion()
					.storeDocumentsAndGetHashes(null, oVector, entidad);
				return ServiciosUtils.createReturnOK();
			} catch (GuidIncorrectoRdeExcepcion e) {
				logger.error("Error almacenando documento temporal.", e);
				return ServiciosUtils.createReturnError(e.getErrorCode());
			} catch (RepositorioDocumentosRdeExcepcion e) {
				logger.error("Error almacenando documento temporal.", e);
				return ServiciosUtils.createReturnError(e.getErrorCode());
			} catch (SigemException e) {
				logger.error("Error almacenando documento temporal.", e);
				return ServiciosUtils.createReturnError(e.getErrorCode());
			} catch (Throwable e){
				logger.error("Error inesperado almacenando documento temporal.", e);
				return ServiciosUtils.createReturnError();			
			} finally {
				if(oVector != null){
					eliminarArchivosTemporales(oVector);
				}
			}
	  }
	  
	  public RetornoServicio almacenarDocumentoTemporal(InfoDocumentoTemporal poDoc, Entidad entidad){
			try {
				getServicioRepositorioDocumentosTramitacion()
					.storeDocumentTmp(poDoc.getSessionId(), poDoc.getGuid(), entidad);
				return ServiciosUtils.createReturnOK();
			} catch (GuidIncorrectoRdeExcepcion e) {
				logger.error("Error almacenando documento temporal.", e);
				return ServiciosUtils.createReturnError(e.getErrorCode());
			} catch (RepositorioDocumentosRdeExcepcion e) {
				logger.error("Error almacenando documento temporal.", e);
				return ServiciosUtils.createReturnError(e.getErrorCode());
			} catch (SigemException e) {
				logger.error("Error almacenando documento temporal.", e);
				return ServiciosUtils.createReturnError(e.getErrorCode());
			} catch (Throwable e){
				logger.error("Error inesperado almacenando documento temporal.", e);
				return ServiciosUtils.createReturnError();			
			}		  
	  }
	  
	  public RetornoServicio eliminarDocumentoTemporal(InfoDocumentoTemporal poDoc, Entidad entidad){
			try {
				getServicioRepositorioDocumentosTramitacion()
					.deleteDocumentTmp(poDoc.getSessionId(), entidad);
				return ServiciosUtils.createReturnOK();
			} catch (GuidIncorrectoRdeExcepcion e) {
				logger.error("Error almacenando documento temporal.", e);
				return ServiciosUtils.createReturnError(e.getErrorCode());
			} catch (RepositorioDocumentosRdeExcepcion e) {
				logger.error("Error almacenando documento temporal.", e);
				return ServiciosUtils.createReturnError(e.getErrorCode());
			} catch (SigemException e) {
				logger.error("Error almacenando documento temporal.", e);
				return ServiciosUtils.createReturnError(e.getErrorCode());
			} catch (Throwable e){
				logger.error("Error inesperado almacenando documento temporal.", e);
				return ServiciosUtils.createReturnError();			
			}		  
	  }
	  
	  public InfoDocumentosTemporales obtenerDocumentosTemporales(InfoDocumentoTemporal poDoc, Entidad entidad){
			try {
				InfoDocumentosTemporales oDocs = 
					getDocumentosTemporalesWS(
							getServicioRepositorioDocumentosTramitacion()
							.getDocumentTmp(poDoc.getSessionId(), entidad)
					);
				return (InfoDocumentosTemporales)ServiciosUtils.completeReturnOK((RetornoServicio) oDocs);
			} catch (GuidIncorrectoRdeExcepcion e) {
				logger.error("Error obteniendo documentos temporales.", e);
				return (InfoDocumentosTemporales)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new InfoDocumentosTemporales()),
									e.getErrorCode());
			} catch (RepositorioDocumentosRdeExcepcion e) {
				logger.error("Error obteniendo documentos temporales.", e);
				return (InfoDocumentosTemporales)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new InfoDocumentosTemporales()),
									e.getErrorCode());
			} catch (SigemException e) {
				logger.error("Error obteniendo documentos temporales.", e);
				return (InfoDocumentosTemporales)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new InfoDocumentosTemporales()),
									e.getErrorCode());
			} catch (Throwable e){
				logger.error("Error inesperado obteniendo documentos temporales.", e);
				return (InfoDocumentosTemporales)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new InfoDocumentosTemporales()));					
			}
	  }
	  

	  public InfoDocumentosTemporales obtenerDocumentosTemporalesCaducados(CriterioBusquedaDocs poDocs, Entidad entidad){
			try {
				InfoDocumentosTemporales oDocs = 
					getDocumentosTemporalesWS(
							getServicioRepositorioDocumentosTramitacion()
							.getDocumentTmp(Integer.valueOf(poDocs.getTimeout()).intValue(), entidad)
					);
				return (InfoDocumentosTemporales)ServiciosUtils.completeReturnOK((RetornoServicio) oDocs);
			} catch (GuidIncorrectoRdeExcepcion e) {
				logger.error("Error obteniendo documentos temporales.", e);
				return (InfoDocumentosTemporales)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new InfoDocumentosTemporales()),
									e.getErrorCode());
			} catch (RepositorioDocumentosRdeExcepcion e) {
				logger.error("Error obteniendo documentos temporales.", e);
				return (InfoDocumentosTemporales)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new InfoDocumentosTemporales()),
									e.getErrorCode());
			} catch (SigemException e) {
				logger.error("Error obteniendo documentos temporales.", e);
				return (InfoDocumentosTemporales)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new InfoDocumentosTemporales()),
									e.getErrorCode());
			} catch (Throwable e){
				logger.error("Error inesperado obteniendo documentos temporales.", e);
				return (InfoDocumentosTemporales)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new InfoDocumentosTemporales()));					
			}
	  }

	  public ContenedorDocumento retrieveDocumentInfo(InfoDocumento poInfo, Entidad entidad){
			try {
				ContenedorDocumento oDoc = getContenedorDocumentoWS(
					getServicioRepositorioDocumentosTramitacion().retrieveDocumentInfo(null, poInfo.getGuid(), entidad)
					);
				return (ContenedorDocumento)ServiciosUtils.completeReturnOK((RetornoServicio) oDoc);
			} catch (GuidIncorrectoRdeExcepcion e) {
				logger.error("Error obteniendo documentos temporales.", e);
				return (ContenedorDocumento)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new ContenedorDocumento()),
									e.getErrorCode());
			} catch (RepositorioDocumentosRdeExcepcion e) {
				logger.error("Error obteniendo documentos temporales.", e);
				return (ContenedorDocumento)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new ContenedorDocumento()),
									e.getErrorCode());
			} catch (SigemException e) {
				logger.error("Error obteniendo documentos temporales.", e);
				return (ContenedorDocumento)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new ContenedorDocumento()),
									e.getErrorCode());
			} catch (Throwable e){
				logger.error("Error inesperado obteniendo documentos temporales.", e);
				return (ContenedorDocumento)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new ContenedorDocumento()));					
			}
	  }

	
	private ServicioRepositorioDocumentosTramitacion getServicioRepositorioDocumentosTramitacion() throws SOAPException, SigemException{
		String cImpl = UtilAxis.getImplementacion(org.apache.axis.MessageContext.getCurrentContext());
		if((cImpl == null) ||("".equals(cImpl))){
			return LocalizadorServicios.getServicioRepositorioDocumentosTramitacion();
		}else{
			StringBuffer sbImpl = new StringBuffer(SERVICE_NAME).append(".").append(cImpl);
			return LocalizadorServicios.getServicioRepositorioDocumentosTramitacion(sbImpl.toString());
		}
	}
	
	private InfoDocumento getInfoDocumentoWS(DocumentoInfo poDoc){
		if(poDoc == null){
			return null;
		}
		InfoDocumento oDoc = new InfoDocumento();
		oDoc.setExtension(poDoc.getExtension());
		oDoc.setMimeType(poDoc.getMimeType());
		oDoc.setContenidoB64(getContenidoB64(poDoc.getContent()));
		return oDoc;
	}
	
	private String getContenidoB64(byte[] poContenido){
		return Base64.encodeBytes(poContenido);
	}
	
	private InfoDocumentosTemporales getDocumentosTemporalesWS(DocumentosTemporales poDocs){
		if(poDocs == null){
			return null;
		}
		InfoDocumentosTemporales oDocs = new InfoDocumentosTemporales();
		InfoDocumentoTemporal[] lDocs = new InfoDocumentoTemporal[poDocs.count()];			
		for(int i = 0; i < poDocs.count(); i++){
			lDocs[i] = getDocumentoTemporal(poDocs.get(i));
		}
		oDocs.setDocumentos(lDocs);
		return oDocs;
	}
	
	private InfoDocumentoTemporal getDocumentoTemporal(DocumentoTemporal poDoc){
		if(poDoc == null){
			return null;
		}
		InfoDocumentoTemporal oDoc = new InfoDocumentoTemporal();
		oDoc.setGuid(poDoc.getGuid());
		oDoc.setSessionId(poDoc.getSessionId());
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTimeInMillis(poDoc.getTimestamp().getTime());
		oDoc.setTimestamp(DateTimeUtil.getDateTime(cal.getTime(), ConstantesServicios.DATE_PATTERN));
		return oDoc;
	}
	
	private Vector getVectorHashInfo(InfoDocumentos poDocs) throws IOException{
		if(poDocs == null){
			return null;
		}
		Vector ovector = new Vector();
		if( (poDocs.getDocumentos() != null) && (poDocs.getDocumentos().length > 0)){
			for(int i=0; i < poDocs.getDocumentos().length; i++){
				ovector.add(i, getDocumentoHashInfo(poDocs.getDocumentos()[i]));
			}
		}
		return ovector;
	}
	
	private DocumentoHashInfo getDocumentoHashInfo(InfoDocumento poDoc) throws IOException{
		if(poDoc == null){
			return null;
		}
		DocumentoHashInfo oDoc = new DocumentoHashInfo();
		File ofile = File.createTempFile(TEMP_FILE_PREFIX, poDoc.getExtension());
		Base64.decodeToFile(poDoc.getContenidoB64(), ofile.getAbsolutePath());
		oDoc.setPath(ofile.getPath());
		oDoc.setExtension(poDoc.getExtension());
		oDoc.setGuid(poDoc.getGuid());
		oDoc.setHash(poDoc.getHash());
		return oDoc;
	}
	
	private void eliminarArchivosTemporales(Vector poVector){
		if(poVector != null){
			DocumentoHashInfo oDoc = null;
			File oFile = null;
			for(int i=0; i < poVector.size(); i++){
				oDoc = (DocumentoHashInfo)poVector.get(i);
				if(	(oDoc != null) && (oDoc.getPath() != null) ){
					oFile = new File(oDoc.getPath());
					if( (oFile.exists()) && (!oFile.isDirectory()) && (oFile.delete()) ){
						StringBuffer sbError = new StringBuffer("Archivo temporal eliminado: ");
						sbError.append(oFile.getAbsolutePath());
						logger.debug(sbError.toString());
					}else{
						StringBuffer sbError = new StringBuffer("Error eliminando archivo temporal: ");
						sbError.append(oFile.getAbsolutePath());
						logger.error(sbError.toString());						
					}
				}
			}
		}
	}
	
	private ContenedorDocumento getContenedorDocumentoWS(ieci.tecdoc.sgm.core.services.repositorio.ContenedorDocumento poDoc){
		if(poDoc == null){
			return null;
		}
		ContenedorDocumento oContenedor = new ContenedorDocumento();
		if(poDoc.getContent() != null){
			oContenedor.setContentB64(Base64.encodeBytes(poDoc.getContent()));
		}
		oContenedor.setContentSize(String.valueOf(poDoc.getContentSize()));
		oContenedor.setExtension(poDoc.getExtension());
		oContenedor.setGuid(poDoc.getGuid());
		oContenedor.setHash(poDoc.getHash());
		if(poDoc.getTimestamp() != null){
			Calendar cal = GregorianCalendar.getInstance();
			cal.setTimeInMillis(poDoc.getTimestamp().getTime());
			oContenedor.setTimestamp(DateTimeUtil.getDateTime(cal.getTime(), ConstantesServicios.DATE_PATTERN));
		}
		return oContenedor;
	}
}

