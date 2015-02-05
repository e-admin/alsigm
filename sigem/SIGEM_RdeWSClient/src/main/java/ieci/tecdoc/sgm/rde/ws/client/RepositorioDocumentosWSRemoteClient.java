package ieci.tecdoc.sgm.rde.ws.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.core.base64.Base64;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;
import ieci.tecdoc.sgm.core.services.repositorio.DocumentoHashInfo;
import ieci.tecdoc.sgm.core.services.repositorio.DocumentoInfo;
import ieci.tecdoc.sgm.core.services.repositorio.DocumentoTemporal;
import ieci.tecdoc.sgm.core.services.repositorio.DocumentosTemporales;
import ieci.tecdoc.sgm.core.services.repositorio.GuidIncorrectoRdeExcepcion;
import ieci.tecdoc.sgm.core.services.repositorio.RepositorioDocumentosRdeExcepcion;
import ieci.tecdoc.sgm.core.services.repositorio.ServicioRepositorioDocumentosTramitacion;

public class RepositorioDocumentosWSRemoteClient implements ServicioRepositorioDocumentosTramitacion{
	
	private static final String TEMP_FILE_PREFIX 	=	"RDETEMP_";
	
	private RepositorioDocumentosWebService service;
	
	public void deleteDocument(String sessionId, String guid, Entidad entidad) throws GuidIncorrectoRdeExcepcion, RepositorioDocumentosRdeExcepcion {
		try{
			InfoDocumento odoc = new InfoDocumento();
			odoc.setGuid(guid);
			RetornoServicio oRetorno = 
				getService().eliminarDocumento(odoc, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				String sError = ((IRetornoServicio)oRetorno).getErrorCode();
				if(GuidIncorrectoRdeExcepcion.GUID_INCORRECTO
						.equals( sError )  ){
					throw new GuidIncorrectoRdeExcepcion(Long.valueOf(sError).longValue());
				} else {
					throw new RepositorioDocumentosRdeExcepcion(Long.valueOf(sError).longValue());
				}
			}
		} catch (RemoteException e) {
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosRdeExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void deleteDocumentTmp(String sessionId, Entidad entidad) throws GuidIncorrectoRdeExcepcion, RepositorioDocumentosRdeExcepcion {
		try{
			InfoDocumentoTemporal oDoc = new InfoDocumentoTemporal();
			oDoc.setSessionId(sessionId);
			RetornoServicio oRetorno = 
				getService().eliminarDocumentoTemporal(oDoc, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				String sError = ((IRetornoServicio)oRetorno).getErrorCode();
				if(GuidIncorrectoRdeExcepcion.GUID_INCORRECTO
						.equals( sError )  ){
					throw new GuidIncorrectoRdeExcepcion(Long.valueOf(sError).longValue());
				} else {
					throw new RepositorioDocumentosRdeExcepcion(Long.valueOf(sError).longValue());
				}
			}
		} catch (RemoteException e) {
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosRdeExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public DocumentosTemporales getDocumentTmp(String sessionId, Entidad entidad) throws RepositorioDocumentosRdeExcepcion {
		try{
			InfoDocumentoTemporal oDoc = new InfoDocumentoTemporal();
			oDoc.setSessionId(sessionId);
			InfoDocumentosTemporales oDocs = getService().obtenerDocumentosTemporales(oDoc, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oDocs)){
				try {
					return getDocumentosTemporalesServicio(oDocs);
				} catch (Exception e) {
					throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosRdeExcepcion.EXC_GENERIC_EXCEPCION, e);
				}
			}else{
				throw getRepositorioDocumentosRdeExcepcion((IRetornoServicio)oDocs);
			}
		} catch (RemoteException e) {
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosRdeExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public DocumentosTemporales getDocumentTmp(int timeout, Entidad entidad) throws RepositorioDocumentosRdeExcepcion {
		try{
			CriterioBusquedaDocs oCriterio = new CriterioBusquedaDocs();
			oCriterio.setTimeout(String.valueOf(timeout));
			InfoDocumentosTemporales oDocs = getService().obtenerDocumentosTemporalesCaducados(oCriterio, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oDocs)){
				try {
					return getDocumentosTemporalesServicio(oDocs);
				} catch (Exception e) {
					throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosRdeExcepcion.EXC_GENERIC_EXCEPCION, e);
				}
			}else{
				throw getRepositorioDocumentosRdeExcepcion((IRetornoServicio)oDocs);
			}
		} catch (RemoteException e) {
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosRdeExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public String getHash(String sessionId, String guid, Entidad entidad) throws GuidIncorrectoRdeExcepcion, RepositorioDocumentosRdeExcepcion {
		try{
			InfoDocumento oDoc = new InfoDocumento();
			oDoc.setGuid(guid);
			InfoDocumento oInfo = getService().obtenerHash(oDoc, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oInfo)){
				return oInfo.getHash();
			}else{
				String sError = ((IRetornoServicio)oInfo).getErrorCode();
				if(GuidIncorrectoRdeExcepcion.GUID_INCORRECTO
						.equals( sError )  ){
					throw new GuidIncorrectoRdeExcepcion(Long.valueOf(sError).longValue());
				} else {
					throw new RepositorioDocumentosRdeExcepcion(Long.valueOf(sError).longValue());
				}
			}
		} catch (RemoteException e) {
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosRdeExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public String retrieveDocument(String sessionId, String guid, String path, Entidad entidad) throws GuidIncorrectoRdeExcepcion, RepositorioDocumentosRdeExcepcion {
		try{
			InfoDocumento oDoc = new InfoDocumento();
			oDoc.setGuid(guid);
			oDoc = getService().recuperarDocumento(oDoc, getEntidadWS(entidad));			
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oDoc)){
				File oDir = new File(path);
				if( (!oDir.exists()) || (!oDir.isDirectory()) ){
					throw new RepositorioDocumentosRdeExcepcion(Long.valueOf(RepositorioDocumentosRdeExcepcion.FILE_ERROR).longValue());
				}
				File ofile;
				try {
					ofile = File.createTempFile(TEMP_FILE_PREFIX, oDoc.getExtension(), oDir);
				} catch (IOException e) {
					throw new RepositorioDocumentosRdeExcepcion(Long.valueOf(RepositorioDocumentosRdeExcepcion.FILE_ERROR).longValue());
				}
				Base64.decodeToFile(oDoc.getContenidoB64(), ofile.getAbsolutePath());				
				return ofile.getAbsolutePath();
			}else{
				String sError = ((IRetornoServicio)oDoc).getErrorCode();
				if(GuidIncorrectoRdeExcepcion.GUID_INCORRECTO
						.equals( sError )  ){
					throw new GuidIncorrectoRdeExcepcion(Long.valueOf(sError).longValue());
				} else {
					throw new RepositorioDocumentosRdeExcepcion(Long.valueOf(sError).longValue());
				}
			}
		} catch (RemoteException e) {
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosRdeExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public DocumentoInfo retrieveDocument(String sessionId, String guid, Entidad entidad) throws GuidIncorrectoRdeExcepcion, RepositorioDocumentosRdeExcepcion {
		try{
			InfoDocumento oDoc = new InfoDocumento();
			oDoc.setGuid(guid);
			oDoc = getService().recuperarDocumento(oDoc, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oDoc)){
				return getDocumentoInfoServicio(oDoc);
			}else{
				String sError = ((IRetornoServicio)oDoc).getErrorCode();
				if(GuidIncorrectoRdeExcepcion.GUID_INCORRECTO
						.equals( sError )  ){
					throw new GuidIncorrectoRdeExcepcion(Long.valueOf(sError).longValue());
				} else {
					throw new RepositorioDocumentosRdeExcepcion(Long.valueOf(sError).longValue());
				}
			}
		} catch (RemoteException e) {
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosRdeExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public String storeDocument(String sessionId, String path, String extension, Entidad entidad) throws RepositorioDocumentosRdeExcepcion {
		try{
			InfoDocumento oDoc = new InfoDocumento();
			oDoc.setExtension(extension);
			File oFile = new File(path);
			if( (!oFile.exists()) || (oFile.isDirectory())){
				throw new RepositorioDocumentosRdeExcepcion(Long.valueOf(RepositorioDocumentosRdeExcepcion.FILE_ERROR).longValue());
			}
			oDoc.setContenidoB64(Base64.encodeFromFile(oFile.getAbsolutePath()));
			InfoDocumento oDocRetorno = getService().guardarDocumento(oDoc, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oDocRetorno)){
				return oDocRetorno.getGuid();
			}else{
				throw getRepositorioDocumentosRdeExcepcion((IRetornoServicio)oDocRetorno);
			}
		} catch (RemoteException e) {
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosRdeExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public String storeDocument(String sessionId, InputStream document, String extension, Entidad entidad) throws RepositorioDocumentosRdeExcepcion {
		/**
		 * TODO
		 */
		return null;
	}

	public String storeDocument(String sessionId, byte[] document, String extension, Entidad entidad) throws RepositorioDocumentosRdeExcepcion {
		try{
			InfoDocumento oDoc = new InfoDocumento();
			oDoc.setExtension(extension);
			oDoc.setContenidoB64(Base64.encodeBytes(document));
			InfoDocumento oDocRetorno = getService().guardarDocumento(oDoc, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oDocRetorno)){
				return oDocRetorno.getGuid();
			}else{
				throw getRepositorioDocumentosRdeExcepcion((IRetornoServicio)oDocRetorno);
			}
		} catch (RemoteException e) {
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosRdeExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public String storeDocumentGuid(String sessionId, String guid, byte[] document, String extension, Entidad entidad) throws RepositorioDocumentosRdeExcepcion {
		try{
			InfoDocumento oDoc = new InfoDocumento();
			oDoc.setExtension(extension);
			oDoc.setGuid(guid);
			oDoc.setContenidoB64(Base64.encodeBytes(document));
			InfoDocumento oDocRetorno = getService().guardarDocumentoGuid(oDoc, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oDocRetorno)){
				return oDocRetorno.getGuid();
			}else{
				throw getRepositorioDocumentosRdeExcepcion((IRetornoServicio)oDocRetorno);
			}
		} catch (RemoteException e) {
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosRdeExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void storeDocumentTmp(String sessionId, String guid, Entidad entidad) throws RepositorioDocumentosRdeExcepcion {
		try{
			InfoDocumentoTemporal oDoc = new InfoDocumentoTemporal();
			oDoc.setSessionId(sessionId);
			oDoc.setGuid(guid);
			RetornoServicio oRetorno = 
				getService().almacenarDocumentoTemporal(oDoc, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getRepositorioDocumentosRdeExcepcion((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosRdeExcepcion.EXC_GENERIC_EXCEPCION, e);
		}		
	}

	public void storeDocumentsAndGetHashes(String sessionId, Vector docsHashInfo, Entidad entidad) throws RepositorioDocumentosRdeExcepcion {
		try{
			InfoDocumentos oDocs = null;
			try{
				oDocs = getInfoDocumentos(docsHashInfo);				
			} catch (IOException e){
				throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosRdeExcepcion.EXC_GENERIC_EXCEPCION, e);
			}

			RetornoServicio oRetorno = getService().almacenarDocumentos(oDocs, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return ;
			}else{
				throw getRepositorioDocumentosRdeExcepcion((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosRdeExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.repositorio.ContenedorDocumento retrieveDocumentInfo(String sessionId, String guid, Entidad entidad) throws GuidIncorrectoRdeExcepcion, RepositorioDocumentosRdeExcepcion {
		try{
			InfoDocumento oInfo = new InfoDocumento();
			oInfo.setGuid(guid);
			ContenedorDocumento oContenedor = getService().retrieveDocumentInfo(oInfo, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oContenedor)){
				return getContenedorServicio(oContenedor);
			}else{
				throw getRepositorioDocumentosRdeExcepcion((IRetornoServicio)oContenedor);
			}
		} catch (RemoteException e) {
			throw new RepositorioDocumentosRdeExcepcion(RepositorioDocumentosRdeExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}	

	public RepositorioDocumentosWebService getService() {
		return service;
	}

	public void setService(RepositorioDocumentosWebService service) {
		this.service = service;
	}

	private RepositorioDocumentosRdeExcepcion getRepositorioDocumentosRdeExcepcion(IRetornoServicio oReturn){
		return new RepositorioDocumentosRdeExcepcion(Long.valueOf(oReturn.getErrorCode()).longValue());
	}

	private DocumentoInfo getDocumentoInfoServicio(InfoDocumento poInfo){
		if(poInfo == null){
			return null;
		}
		DocumentoInfo oDoc = new DocumentoInfo();
		if(poInfo.getContenidoB64() != null){
			oDoc.setContent(Base64.decode(poInfo.getContenidoB64()));
		}
		oDoc.setExtension(poInfo.getExtension());
		oDoc.setGuid(poInfo.getGuid());
		oDoc.setMimeType(poInfo.getMimeType());
		return oDoc;
	}
	
	public String convertInputStreamtoByteArray(InputStream in) throws IOException
	{
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;){
			out.append(new String(b, 0, n));
		}
		return Base64.encodeBytes(out.toString().getBytes());
	}
	
	private DocumentosTemporales getDocumentosTemporalesServicio(InfoDocumentosTemporales poDocs) throws Exception{
		if(poDocs == null){
			return null;
		}
		DocumentosTemporales oDocs = new DocumentosTemporales();
		if(poDocs.getDocumentos() != null){
			for(int i=0; i<poDocs.getDocumentos().length; i++){
				oDocs.add(getDocumentoTemporalServicio(poDocs.getDocumentos()[i]));
			}
		}
		return oDocs;
	}
	
	private DocumentoTemporal getDocumentoTemporalServicio(InfoDocumentoTemporal poDoc) throws Exception{
		if(poDoc == null){
			return null;
		}
		DocumentoTemporal oDoc = new DocumentoTemporal();
		oDoc.setGuid(poDoc.getGuid());
		oDoc.setSessionId(poDoc.getSessionId());
		Date oDate = DateTimeUtil.getDate(poDoc.getTimestamp(), ConstantesServicios.DATE_PATTERN);
		Timestamp oTime = new Timestamp(oDate.getTime());
		oDoc.setTimestamp(oTime);
		return oDoc;
	}
	
	private InfoDocumentos getInfoDocumentos(Vector poInfos) throws IOException{
		if(poInfos == null){
			return null;
		}
		InfoDocumentos oDocs = new InfoDocumentos();
		Iterator oIterador = poInfos.iterator();
		InfoDocumento[] lInfos = new InfoDocumento[poInfos.size()];
		DocumentoHashInfo oHashInfo = null;
		int i = 0;
		while(oIterador.hasNext()){
			oHashInfo = (DocumentoHashInfo)oIterador.next();
			lInfos[i] = getInfoDocumentoWS(oHashInfo);
			i++;
		}
		oDocs.setDocumentos(lInfos);
		return oDocs;
	}
	
	private InfoDocumento getInfoDocumentoWS(DocumentoHashInfo poDoc) throws IOException{
		if(poDoc == null){
			return null;
		}
		InfoDocumento oDoc = new InfoDocumento();
		String cContent = Base64.encodeFromFile(poDoc.getPath());
		if(cContent == null){
			throw new IOException("Error codificando archivo.");
		}
		oDoc.setContenidoB64(cContent);
		oDoc.setExtension(poDoc.getExtension());
		oDoc.setGuid(poDoc.getGuid());
		oDoc.setHash(poDoc.getHash());
		return oDoc;
	}

	private ieci.tecdoc.sgm.core.services.repositorio.ContenedorDocumento getContenedorServicio(ContenedorDocumento poDoc){
		if(poDoc == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.repositorio.ContenedorDocumento oDoc = new ieci.tecdoc.sgm.core.services.repositorio.ContenedorDocumento();
		if(poDoc.getContentB64() != null){
			oDoc.setContent(Base64.decode(poDoc.getContentB64()));	
		}
		if(poDoc.getContentSize() != null){
			try{
				oDoc.setContentSize(Integer.valueOf(poDoc.getContentSize()).intValue());
			}catch(NumberFormatException e){
				oDoc.setContentSize(0);
			}
		}else{
			oDoc.setContentSize(0);
		}
		oDoc.setExtension(poDoc.getExtension());
		oDoc.setGuid(poDoc.getGuid());
		oDoc.setHash(poDoc.getHash());
		if( (poDoc.getTimestamp() != null) && (!"".equals(poDoc))){
			Date oDate;
			try {
				oDate = DateTimeUtil.getDate(poDoc.getTimestamp(), ConstantesServicios.DATE_PATTERN);
				Timestamp otime = new Timestamp(oDate.getTime());
				oDoc.setTimestamp(otime);				
			} catch (Exception e) {}
		}
		return oDoc;
	}
	
	private ieci.tecdoc.sgm.rde.ws.client.Entidad getEntidadWS(Entidad poEntidad){
		if(poEntidad == null){
			return null;
		}
		ieci.tecdoc.sgm.rde.ws.client.Entidad oEntidad = new ieci.tecdoc.sgm.rde.ws.client.Entidad();
		oEntidad.setNombre(poEntidad.getNombre());
		oEntidad.setIdentificador(poEntidad.getIdentificador());
		return oEntidad;
	}
}
