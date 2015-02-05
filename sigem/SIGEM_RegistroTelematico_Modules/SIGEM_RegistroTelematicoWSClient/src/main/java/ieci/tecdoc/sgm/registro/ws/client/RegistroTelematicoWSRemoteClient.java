package ieci.tecdoc.sgm.registro.ws.client;

import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;
import ieci.tecdoc.sgm.core.services.telematico.RegistroTelematicoException;
import ieci.tecdoc.sgm.core.services.telematico.ServicioRegistroTelematico;
import ieci.tecdoc.sgm.registro.ws.client.axis.ByteArrayB64;
import ieci.tecdoc.sgm.registro.ws.client.axis.ContenedorDocumento;
import ieci.tecdoc.sgm.registro.ws.client.axis.ContenedorDocumentos;
import ieci.tecdoc.sgm.registro.ws.client.axis.DocumentoExtendido;
import ieci.tecdoc.sgm.registro.ws.client.axis.Documentos;
import ieci.tecdoc.sgm.registro.ws.client.axis.PeticionDocumento;
import ieci.tecdoc.sgm.registro.ws.client.axis.PeticionDocumentos;
import ieci.tecdoc.sgm.registro.ws.client.axis.Registro;
import ieci.tecdoc.sgm.registro.ws.client.axis.RegistroConsulta;
import ieci.tecdoc.sgm.registro.ws.client.axis.RegistroDocumento;
import ieci.tecdoc.sgm.registro.ws.client.axis.RegistroDocumentos;
import ieci.tecdoc.sgm.registro.ws.client.axis.RegistroPeticion;
import ieci.tecdoc.sgm.registro.ws.client.axis.RegistroTelematicoWebService_PortType;
import ieci.tecdoc.sgm.registro.ws.client.axis.Registros;
import ieci.tecdoc.sgm.registro.ws.client.axis.RetornoCadena;
import ieci.tecdoc.sgm.registro.ws.client.axis.RetornoLogico;
import ieci.tecdoc.sgm.registro.ws.client.axis.RetornoServicio;
import ieci.tecdoc.sgm.registro.ws.client.axis.StringB64;

import java.io.ByteArrayOutputStream;
import java.rmi.RemoteException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RegistroTelematicoWSRemoteClient implements ServicioRegistroTelematico {

	private RegistroTelematicoWebService_PortType service;


	public RegistroTelematicoWebService_PortType getService() {
		return service;
	}

	public void setService(RegistroTelematicoWebService_PortType service) {
		this.service = service;
	}

	public void actualizarDocumentoRegistro(ieci.tecdoc.sgm.core.services.telematico.RegistroDocumento registryDocument, Entidad entidad) throws RegistroTelematicoException {
		try{
			RetornoServicio oRetorno =  getService().actualizarDocumentoRegistro(getRegistroDocumentoWS(registryDocument), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public byte[] crearPeticionRegistro(String sessionId, ieci.tecdoc.sgm.core.services.telematico.RegistroPeticion requestInfo,
			String idiom, String organismo, String numeroExpediente, Entidad entidad) throws RegistroTelematicoException {
		try{
			StringB64 oRetorno =  getService().crearPeticionRegistro(sessionId, getRegistroPeticionWS(requestInfo), idiom, organismo, numeroExpediente, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getStringB64Servicio(oRetorno);
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public void eliminarDocumentoRegistro(String registryNumber, Entidad entidad) throws RegistroTelematicoException {
		try{
			RetornoServicio oRetorno =  getService().eliminarDocumentoRegistro(registryNumber, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public void eliminarDocumentosTemporales(String sessionId, String registryNumber, Entidad entidad) throws RegistroTelematicoException {
		try{
			RetornoServicio oRetorno =  getService().eliminarDocumentosTemporales(sessionId, registryNumber, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public String establecerDocumentosSubsanacion(String sessionId, ieci.tecdoc.sgm.core.services.catalogo.Documentos procedureDocuments,
			ieci.tecdoc.sgm.core.services.telematico.PeticionDocumentos requestDocuments, Entidad entidad) throws RegistroTelematicoException {
		try{
			RetornoCadena oRetorno =  getService().establecerDocumentosSubsanacion(sessionId, getDocumentosWS(procedureDocuments),
					getPeticionDocumentosWS(requestDocuments), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return oRetorno.getCadena();
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public void establecerEstadoRegistro(String registryNumber, int status, Entidad entidad) throws RegistroTelematicoException {
		try{
			RetornoServicio oRetorno =  getService().establecerEstadoRegistro(registryNumber, ""+status, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.telematico.RegistroDocumento insertarDocumentoRegistro(ieci.tecdoc.sgm.core.services.telematico.RegistroDocumento registryDocument,
			Entidad entidad) throws RegistroTelematicoException {
		try{
			RegistroDocumento oRetorno =  getService().insertarDocumentoRegistro(getRegistroDocumentoWS(registryDocument), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getRegistroDocumentoServicio(oRetorno);
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ByteArrayOutputStream obtenerContenidoDocumento(String sessionId, String registryNumber, String code, Entidad entidad) throws RegistroTelematicoException {
		try{
			ByteArrayB64 oRetorno =  getService().obtenerContenidoDocumento(sessionId, registryNumber, code, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getByteArrayB64Servicio(oRetorno);
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.repositorio.ContenedorDocumentos obtenerDatosDocumentosRegistro(String registryNumber, Entidad entidad) throws RegistroTelematicoException {
		try{
			ContenedorDocumentos oRetorno =  getService().obtenerDatosDocumentosRegistro(registryNumber, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getContenedorDocumentosServicio(oRetorno);
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public byte[] obtenerDocumento(String registryNumber, String code, Entidad entidad) throws RegistroTelematicoException {
		try{
			StringB64 oRetorno =  getService().obtenerDocumento(registryNumber, code, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getStringB64Servicio(oRetorno);
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.telematico.RegistroDocumento obtenerDocumentoRegistro(String sessionId,
			String registryNumber, String code, Entidad entidad) throws RegistroTelematicoException {
		try{
			RegistroDocumento oRetorno =  getService().obtenerDocumentoRegistro(sessionId, registryNumber, code, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getRegistroDocumentoServicio(oRetorno);
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.telematico.RegistroDocumentos obtenerDocumentosRegistro(String registryNumber,
			Entidad entidad) throws RegistroTelematicoException {
		try{
			RegistroDocumentos oRetorno =  getService().obtenerDocumentosRegistro(registryNumber, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getRegistroDocumentosServicio(oRetorno);
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public byte[] obtenerJustificanteRegistro(String sessionId, String registryNumber, Entidad entidad) throws RegistroTelematicoException {
		try{
			StringB64 oRetorno =  getService().obtenerJustificanteRegistro(sessionId, registryNumber, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getStringB64Servicio(oRetorno);
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public String obtenerNumeroRegistro(Entidad entidad) throws RegistroTelematicoException {
		try{
			RetornoCadena oRetorno =  getService().obtenerNumeroRegistro(getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return oRetorno.getCadena();
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public byte[] obtenerPeticionRegistro(String registryNumber, Entidad entidad) throws RegistroTelematicoException {
		try{
			StringB64 oRetorno =  getService().obtenerPeticionRegistro(registryNumber, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getStringB64Servicio(oRetorno);
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.telematico.Registro obtenerRegistro(String sessionId, String registryNumber,
			Entidad entidad) throws RegistroTelematicoException {
		try{
			Registro oRetorno =  getService().obtenerRegistro(sessionId, registryNumber, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getRegistroServicio(oRetorno);
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.telematico.Registros obtenerRegistrosConsolidados(Entidad entidad) throws RegistroTelematicoException {
		try{
			Registros oRetorno =  getService().obtenerRegistrosConsolidados(getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getRegistrosServicio(oRetorno);
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.telematico.Registros obtenerRegistrosParaMostrar(ieci.tecdoc.sgm.core.services.telematico.RegistroConsulta query, Entidad entidad) throws RegistroTelematicoException {
		try{
			Registros oRetorno =  getService().obtenerRegistrosParaMostrar(getRegistroConsultaWS(query), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getRegistrosServicio(oRetorno);
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.telematico.Registros query(String sessionId, ieci.tecdoc.sgm.core.services.telematico.RegistroConsulta query,
			Entidad entidad) throws RegistroTelematicoException {
		try{
			Registros oRetorno =  getService().query(sessionId, getRegistroConsultaWS(query), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getRegistrosServicio(oRetorno);
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public byte[] registrar(String sessionId, byte[] registryRequest, String additionalInfo, String idiom, String oficina,
			String plantilla, String certificado, Entidad entidad) throws RegistroTelematicoException {
		try{
			StringB64 oRetorno =  getService().registrar(sessionId, getStringB64WS(registryRequest),  additionalInfo,  idiom,
					oficina,  plantilla,  certificado, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getStringB64Servicio(oRetorno);
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public byte[] registrar(String sessionId, byte[] registryRequest,
			String additionalInfo, String idiom, String oficina,
			byte[] plantilla, String certificado, Entidad entidad)
			throws RegistroTelematicoException {
		try{
			StringB64 oRetorno =  getService().registrarConJustificante(sessionId, getStringB64WS(registryRequest),  additionalInfo,  idiom,
					oficina,  getStringB64WS(plantilla),  certificado, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getStringB64Servicio(oRetorno);
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public boolean tieneDocumentos(String registryNumber, Entidad entidad) throws RegistroTelematicoException {
		try{
			RetornoLogico oRetorno =  getService().tieneDocumentos(registryNumber, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getRetornoLogicoServicio(oRetorno);
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public void deshacerRegistro(String sessionId, String registryNumber, Entidad entidad) throws RegistroTelematicoException {
		try{
			RetornoServicio oRetorno =  getService().deshacerRegistro(sessionId, registryNumber, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public byte[] registrarTelematicoAndIniciarExpediente(String sessionId, byte[] registryRequest, String additionalInfo, String idiom,
			String oficina, String plantilla, String certificado, String tramiteId, Entidad entidad) throws RegistroTelematicoException {
		try{
			StringB64 oRetorno =  getService().registrarTelematicoAndIniciarExpediente(sessionId, getStringB64WS(registryRequest),  additionalInfo,  idiom,
					oficina,  plantilla,  certificado, tramiteId, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getStringB64Servicio(oRetorno);
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public byte[] registrarTelematicoAndIniciarExpediente(String sessionId,
			byte[] registryRequest, String additionalInfo, String idiom,
			String oficina, byte[] plantilla, String certificado,
			String tramiteId, Entidad entidad)
			throws RegistroTelematicoException {
		try{
			StringB64 oRetorno =  getService().registrarTelematicoConJustificanteAndIniciarExpediente(sessionId, getStringB64WS(registryRequest),  additionalInfo,  idiom,
					oficina,  getStringB64WS(plantilla),  certificado, tramiteId, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getStringB64Servicio(oRetorno);
			}else{
				throw getRegistroTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	private RegistroTelematicoException getRegistroTelematicoException(IRetornoServicio oReturn){
		return new RegistroTelematicoException(Long.valueOf(oReturn.getErrorCode()).longValue());
	}

	private RegistroDocumento getRegistroDocumentoWS(ieci.tecdoc.sgm.core.services.telematico.RegistroDocumento poRegistroDocumento){
		RegistroDocumento oRegistroDocumento = new RegistroDocumento();
		if(poRegistroDocumento == null){
			return oRegistroDocumento;
		}

		oRegistroDocumento.setCode(poRegistroDocumento.getCode());
		oRegistroDocumento.setGuid(poRegistroDocumento.getGuid());
		oRegistroDocumento.setRegistryNumber(poRegistroDocumento.getRegistryNumber());

		return oRegistroDocumento;
	}


	private RegistroPeticion getRegistroPeticionWS(ieci.tecdoc.sgm.core.services.telematico.RegistroPeticion poRegistroPeticion){
		RegistroPeticion oRegistroPeticion = new RegistroPeticion();
		if(poRegistroPeticion == null){
			return oRegistroPeticion;
		}

		oRegistroPeticion.setAddressee(poRegistroPeticion.getAddressee());
		oRegistroPeticion.setDocuments(getPeticionDocumentosWS(poRegistroPeticion.getDocuments()));
		oRegistroPeticion.setEmail(poRegistroPeticion.getEmail());
		oRegistroPeticion.setFolderId(poRegistroPeticion.getFolderId());
		oRegistroPeticion.setProcedureId(poRegistroPeticion.getProcedureId());
		oRegistroPeticion.setSenderIdType(poRegistroPeticion.getSenderIdType());
		oRegistroPeticion.setSpecificData(poRegistroPeticion.getSpecificData());

		return oRegistroPeticion;
	}

	private PeticionDocumentos getPeticionDocumentosWS(ieci.tecdoc.sgm.core.services.telematico.PeticionDocumentos poPeticionDocumentos){
		if (poPeticionDocumentos == null)
			return null;

		PeticionDocumentos oPeticionDocumentos = new PeticionDocumentos();
		PeticionDocumento[] poPeticionDocumentosArray = new PeticionDocumento[poPeticionDocumentos.count()];
		for(int i=0; i<poPeticionDocumentos.count(); i++)
			poPeticionDocumentosArray[i] = getPeticionDocumentoWS((ieci.tecdoc.sgm.core.services.telematico.PeticionDocumento)poPeticionDocumentos.get(i));
		oPeticionDocumentos.setPeticionDocumentos(poPeticionDocumentosArray);
		return oPeticionDocumentos;
	}


	private PeticionDocumento getPeticionDocumentoWS(ieci.tecdoc.sgm.core.services.telematico.PeticionDocumento poPeticionDocumento){
		PeticionDocumento oPeticionDocumento = new PeticionDocumento();
		if(poPeticionDocumento == null){
			return oPeticionDocumento;
		}

		oPeticionDocumento.setCode(poPeticionDocumento.getCode());
		oPeticionDocumento.setExtension(poPeticionDocumento.getExtension());
		oPeticionDocumento.setFileName(poPeticionDocumento.getFileName());
		oPeticionDocumento.setLocation(poPeticionDocumento.getLocation());
		oPeticionDocumento.setFileContent(getStringB64WS(poPeticionDocumento.getFileContent()));
		return oPeticionDocumento;
	}

	private byte[] getStringB64Servicio(StringB64 poStringB64){
		BASE64Decoder decoder = new BASE64Decoder();
		try{
			return decoder.decodeBuffer(poStringB64.getStringB64());
		}catch(Exception e){
			return null;
		}
	}

	private StringB64 getStringB64WS(byte[] poStringB64){
		StringB64 stringB64 = null;
		if(poStringB64!=null)
		{
			BASE64Encoder encoder = new BASE64Encoder();
			stringB64 = new StringB64();
			stringB64.setStringB64(encoder.encode(poStringB64));
		}
		return stringB64;
	}

	private boolean getRetornoLogicoServicio(RetornoLogico poRetorno){
		if (poRetorno == null || !ConstantesServicios.LABEL_TRUE.equals(poRetorno.getValor()))
			return false;
		return true;
	}

	private Documentos getDocumentosWS(ieci.tecdoc.sgm.core.services.catalogo.Documentos poDocumentos){
		if (poDocumentos == null)
			return null;

		Documentos oDocumentos = new Documentos();
		DocumentoExtendido[] poDocumentosArray = new DocumentoExtendido[poDocumentos.count()];
		for(int i=0; i<poDocumentos.count(); i++)
			poDocumentosArray[i] = getDocumentoExtendidoWS((ieci.tecdoc.sgm.core.services.catalogo.DocumentoExtendido)poDocumentos.get(i));

		oDocumentos.setDocumentos(poDocumentosArray);

		return oDocumentos;
	}

	private DocumentoExtendido getDocumentoExtendidoWS(ieci.tecdoc.sgm.core.services.catalogo.DocumentoExtendido poDocumentoExtendido){
		DocumentoExtendido oDocumentoExtendido = new DocumentoExtendido();
		if(poDocumentoExtendido == null){
			return oDocumentoExtendido;
		}

		oDocumentoExtendido.setCode(poDocumentoExtendido.getCode());
		oDocumentoExtendido.setDescription(poDocumentoExtendido.getDescription());
		oDocumentoExtendido.setExtension(poDocumentoExtendido.getExtension());
		oDocumentoExtendido.setId(poDocumentoExtendido.getId());
		oDocumentoExtendido.setMandatory(""+poDocumentoExtendido.isMandatory());
		oDocumentoExtendido.setSignatureHook(poDocumentoExtendido.getSignatureHook());
		oDocumentoExtendido.setValidationHook(poDocumentoExtendido.getValidationHook());

		return oDocumentoExtendido;
	}

	private ieci.tecdoc.sgm.core.services.telematico.RegistroDocumento getRegistroDocumentoServicio(RegistroDocumento poRegistroDocumento){
		ieci.tecdoc.sgm.core.services.telematico.RegistroDocumento oRegistroDocumento = new ieci.tecdoc.sgm.core.services.telematico.RegistroDocumento();
		if(poRegistroDocumento == null){
			return oRegistroDocumento;
		}

		oRegistroDocumento.setCode(poRegistroDocumento.getCode());
		oRegistroDocumento.setGuid(poRegistroDocumento.getGuid());
		oRegistroDocumento.setRegistryNumber(poRegistroDocumento.getRegistryNumber());

		return oRegistroDocumento;
	}

	private ieci.tecdoc.sgm.core.services.telematico.RegistroDocumentos getRegistroDocumentosServicio(RegistroDocumentos poRegistroDocumentos) {
		if (poRegistroDocumentos == null)
			return null;

		ieci.tecdoc.sgm.core.services.telematico.RegistroDocumentos oRegistroDocumentos = new ieci.tecdoc.sgm.core.services.telematico.RegistroDocumentos();
		RegistroDocumento[] oRegistroDocumentosArray = poRegistroDocumentos.getRegistroDocumentos();
		for(int i=0; i<oRegistroDocumentosArray.length; i++)
			oRegistroDocumentos.add(getRegistroDocumentoServicio((RegistroDocumento)oRegistroDocumentosArray[i]));

		return oRegistroDocumentos;
	}

	private ByteArrayOutputStream getByteArrayB64Servicio(ByteArrayB64 poByteArrayB64){
		BASE64Decoder decoder = new BASE64Decoder();
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			baos.write(decoder.decodeBuffer(poByteArrayB64.getByteArrayB64()));
			return baos;
		}catch(Exception e){
			return null;
		}
	}

	private ieci.tecdoc.sgm.core.services.repositorio.ContenedorDocumento getContenedorDocumentoServicio(ContenedorDocumento poContenedorDocumento){
		if(poContenedorDocumento == null){
			return null;
		}

		ieci.tecdoc.sgm.core.services.repositorio.ContenedorDocumento oContenedorDocumento = new ieci.tecdoc.sgm.core.services.repositorio.ContenedorDocumento();
		try{
			BASE64Decoder decoder = new BASE64Decoder();
			oContenedorDocumento.setContent(decoder.decodeBuffer(poContenedorDocumento.getContent()));
			oContenedorDocumento.setContentSize(new Integer(poContenedorDocumento.getContentSize()).intValue());
			oContenedorDocumento.setExtension(poContenedorDocumento.getExtension());
			oContenedorDocumento.setGuid(poContenedorDocumento.getGuid());
			oContenedorDocumento.setHash(poContenedorDocumento.getHash());
			oContenedorDocumento.setTimestamp(DateTimeUtil.getDate(poContenedorDocumento.getTimestamp(), ConstantesServicios.DATE_PATTERN));
		}catch(Exception e) {
			return null;
		}
		return oContenedorDocumento;
	}


	private ieci.tecdoc.sgm.core.services.repositorio.ContenedorDocumentos getContenedorDocumentosServicio(ContenedorDocumentos poContenedorDocumentos) {
		if (poContenedorDocumentos == null)
			return null;

		ieci.tecdoc.sgm.core.services.repositorio.ContenedorDocumentos oContenedorDocumentos = new ieci.tecdoc.sgm.core.services.repositorio.ContenedorDocumentos();
		ContenedorDocumento[] oContenedorDocumentosArray = poContenedorDocumentos.getContenedorDocumentos();
		for(int i=0; i<oContenedorDocumentosArray.length; i++)
			oContenedorDocumentos.add(getContenedorDocumentoServicio((ContenedorDocumento)oContenedorDocumentosArray[i]));

		return oContenedorDocumentos;
	}

	private ieci.tecdoc.sgm.core.services.telematico.Registro getRegistroServicio(Registro poRegistro){
		if(poRegistro == null){
			return null;
		}

		ieci.tecdoc.sgm.core.services.telematico.Registro oRegistro = new ieci.tecdoc.sgm.core.services.telematico.Registro();
		try{
			BASE64Decoder decoder = new BASE64Decoder();
			oRegistro.setAddressee(poRegistro.getAddressee());
			oRegistro.setAdittionalInfo(decoder.decodeBuffer(poRegistro.getAdditionalInfo()));
			oRegistro.setEMail(poRegistro.getEMail());
			oRegistro.setName(poRegistro.getName());
			oRegistro.setNumeroExpediente(poRegistro.getNumeroExpediente());
			oRegistro.setOficina(poRegistro.getOficina());
			oRegistro.setRegistryDate(DateTimeUtil.getDate(poRegistro.getRegistryDate(), ConstantesServicios.DATE_PATTERN));
			oRegistro.setEffectiveDate(DateTimeUtil.getDate(poRegistro.getEffectiveDate(), ConstantesServicios.DATE_PATTERN));
			oRegistro.setRegistryNumber(poRegistro.getRegistryNumber());
			oRegistro.setRepresentedId(poRegistro.getRepresentedId());
			oRegistro.setRepresentedName(poRegistro.getRepresentedName());
			oRegistro.setSenderId(poRegistro.getSenderId());
			oRegistro.setSenderIdType(new Integer(poRegistro.getSenderIdType()).intValue());
			oRegistro.setStatus(new Integer(poRegistro.getStatus()).intValue());
			//oRegistro.setSurName(poRegistro.getSurName());
			oRegistro.setTopic(poRegistro.getTopic());
		}catch(Exception e){
			return null;
		}
		return oRegistro;
	}

	private ieci.tecdoc.sgm.core.services.telematico.Registros getRegistrosServicio(Registros poRegistros) {
		if (poRegistros == null)
			return null;

		ieci.tecdoc.sgm.core.services.telematico.Registros oRegistros = new ieci.tecdoc.sgm.core.services.telematico.Registros();
		Registro[] oRegistrosArray = poRegistros.getRegistros();
		for(int i=0; i<oRegistrosArray.length; i++)
			oRegistros.add(getRegistroServicio((Registro)oRegistrosArray[i]));

		return oRegistros;
	}

	private RegistroConsulta getRegistroConsultaWS(ieci.tecdoc.sgm.core.services.telematico.RegistroConsulta poRegistroConsulta){
		RegistroConsulta oRegistroConsulta = new RegistroConsulta();
		if(poRegistroConsulta == null){
			return oRegistroConsulta;
		}

		oRegistroConsulta.setAddressee(poRegistroConsulta.getAddressee());
		oRegistroConsulta.setFirstRegistryDate(poRegistroConsulta.getFirstRegistryDate());
		oRegistroConsulta.setFolderId(poRegistroConsulta.getFolderId());
		oRegistroConsulta.setLastRegistryDate(poRegistroConsulta.getLastRegistryDate());
		oRegistroConsulta.setOprRegistryNumber(""+poRegistroConsulta.getOprRegistryNumber());
		oRegistroConsulta.setRegistryNumber(poRegistroConsulta.getRegistryNumber());
		oRegistroConsulta.setSenderId(poRegistroConsulta.getSenderId());
		oRegistroConsulta.setStatus(""+poRegistroConsulta.getStatus());
		oRegistroConsulta.setSubject(poRegistroConsulta.getSubject());
		oRegistroConsulta.setSubtype(poRegistroConsulta.getSubtype());
		oRegistroConsulta.setTopic(poRegistroConsulta.getTopic());
		oRegistroConsulta.setType(poRegistroConsulta.getType());
		oRegistroConsulta.setFirstEffectiveDate(poRegistroConsulta.getFirstEffectiveDate());
		oRegistroConsulta.setLastEffectiveDate(poRegistroConsulta.getLastEffectiveDate());

		return oRegistroConsulta;
	}

	private ieci.tecdoc.sgm.registro.ws.client.axis.Entidad getEntidadWS(Entidad poEntidad){
		if(poEntidad == null){
			return null;
		}
		ieci.tecdoc.sgm.registro.ws.client.axis.Entidad oEntidad = new ieci.tecdoc.sgm.registro.ws.client.axis.Entidad();
		oEntidad.setIdentificador(poEntidad.getIdentificador());
		return oEntidad;
	}



}
