package ieci.tecdoc.sgm.registro;

import java.io.ByteArrayOutputStream;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.base.miscelanea.Goodies;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.catalogo.Documentos;
import ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites;
import ieci.tecdoc.sgm.core.services.catalogo.Tramite;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.repositorio.ContenedorDocumentos;
import ieci.tecdoc.sgm.core.services.telematico.PeticionDocumento;
import ieci.tecdoc.sgm.core.services.telematico.PeticionDocumentos;
import ieci.tecdoc.sgm.core.services.telematico.Registro;
import ieci.tecdoc.sgm.core.services.telematico.RegistroConsulta;
import ieci.tecdoc.sgm.core.services.telematico.RegistroDocumento;
import ieci.tecdoc.sgm.core.services.telematico.RegistroDocumentos;
import ieci.tecdoc.sgm.core.services.telematico.RegistroTelematicoException;
import ieci.tecdoc.sgm.core.services.telematico.RegistroPeticion;
import ieci.tecdoc.sgm.core.services.telematico.Registros;
import ieci.tecdoc.sgm.core.services.telematico.ServicioRegistroTelematico;
import ieci.tecdoc.sgm.registro.exception.RegistroExcepcion;

public class ServicioRegistroTelematicoAdapter implements ServicioRegistroTelematico{

	private static final Logger logger = Logger.getLogger(ServicioRegistroTelematicoAdapter.class);

	public byte[] crearPeticionRegistro (String sessionId, RegistroPeticion requestInfo, String idiom,
			String organismo, String numeroExpediente, Entidad entidad)
		throws RegistroTelematicoException {
		try {
			return RegistroManager.createRegistryRequest(sessionId, getRegistroPeticionImpl(requestInfo), idiom,
						organismo, numeroExpediente, entidad.getIdentificador());
		} catch (RegistroExcepcion e) {
			logger.error("Error al crear una peticion de registro.", e);
			throw getRegistroTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al crear una peticion de registro.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}



	public String establecerDocumentosSubsanacion (String sessionId, Documentos procedureDocuments,
			PeticionDocumentos requestDocuments, Entidad entidad)
		throws RegistroTelematicoException {
		try {
			return RegistroManager.setDocumentosSubsanacion(sessionId, procedureDocuments,
					getPeticionDocumentosImpl(requestDocuments), entidad.getIdentificador());
		} catch (RegistroExcepcion e) {
			logger.error("Error al establecer documentos de subsanacion.", e);
			throw getRegistroTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al establecer documentos de subsanacion.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public byte[] registrar (String sessionId, byte[] registryRequest, String additionalInfo, String idiom,
			String oficina, String plantilla, String certificado, Entidad entidad)
		throws RegistroTelematicoException {
		try {
			return RegistroManager.register(sessionId, registryRequest, additionalInfo, idiom, oficina,
					plantilla, certificado, entidad.getIdentificador());
		} catch (RegistroExcepcion e) {
			logger.error("Error al registrar.", e);
			throw getRegistroTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al registrar.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public byte[] registrar (String sessionId, byte[] registryRequest, String additionalInfo, String idiom,
			String oficina, byte[] plantilla, String certificado, Entidad entidad)
		throws RegistroTelematicoException {
		try {
			return RegistroManager.register(sessionId, registryRequest, additionalInfo, idiom, oficina,
					plantilla, certificado, entidad.getIdentificador());
		} catch (RegistroExcepcion e) {
			logger.error("Error al registrar.", e);
			throw getRegistroTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al registrar.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public String obtenerNumeroRegistro(Entidad entidad) throws RegistroTelematicoException {
		try {
			return RegistroManager.getRegistryNumber(entidad.getIdentificador());
		} catch (RegistroExcepcion e) {
			logger.error("Error al obtener numero de registro.", e);
			throw getRegistroTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al obtener numero de registro.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void eliminarDocumentosTemporales (String sessionId, String registryNumber, Entidad entidad) throws RegistroTelematicoException {
		try {
			//RegistroManager.cleanTempDocuments(sessionId, registryNumber, entidad.getIdentificador());
			RegistroManager.cleanTempDocuments(sessionId, entidad.getIdentificador());
		} catch (RegistroExcepcion e) {
			logger.error("Error al eliminar documentos temporales.", e);
			throw getRegistroTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al eliminar documentos temporales.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public Registros query (String sessionId, RegistroConsulta query, Entidad entidad) throws RegistroTelematicoException {
		try {
			return getRegistrosServicio(RegistroManager.query(sessionId, getRegistroConsultaImpl(query), entidad.getIdentificador()));
		} catch (RegistroExcepcion e) {
			logger.error("Error al realizar consulta.", e);
			throw getRegistroTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al realizar consulta.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public Registro obtenerRegistro(String sessionId, String registryNumber, Entidad entidad) throws RegistroTelematicoException {
		try {
			return getRegistroServicio((ieci.tecdoc.sgm.registro.util.RegistroImpl)
						RegistroManager.getRegistry(sessionId, registryNumber, entidad.getIdentificador()));
		} catch (RegistroExcepcion e) {
			logger.error("Error al obtener registro.", e);
			throw getRegistroTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al obtener registro.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public ContenedorDocumentos obtenerDatosDocumentosRegistro (String registryNumber, Entidad entidad) throws RegistroTelematicoException {
		try {
			return RegistroManager.getRegistryDocumentsData(registryNumber, entidad.getIdentificador());
		} catch (RegistroExcepcion e) {
			logger.error("Error al obtener contenido de documentos de registro.", e);
			throw getRegistroTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al obtener contenido de documentos de registro.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public byte[] obtenerJustificanteRegistro(String sessionId, String registryNumber, Entidad entidad) throws RegistroTelematicoException {
		try {
			return RegistroManager.getRegistryReceipt(sessionId, registryNumber, entidad.getIdentificador());
		} catch (RegistroExcepcion e) {
			logger.error("Error al obtener justificante de registro.", e);
			throw getRegistroTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al obtener justificante de registro.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public byte[] obtenerPeticionRegistro (String registryNumber, Entidad entidad) throws RegistroTelematicoException {
		try {
			return RegistroManager.getRegistryRequest(registryNumber, entidad.getIdentificador());
		} catch (RegistroExcepcion e) {
			logger.error("Error al obtener peticion de registro.", e);
			throw getRegistroTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al obtener peticion de registro.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public Registros obtenerRegistrosConsolidados(Entidad entidad) throws RegistroTelematicoException {
		try {
			return getRegistrosServicio(RegistroManager.getConsolidationRegistries(entidad.getIdentificador()));
		} catch (RegistroExcepcion e) {
			logger.error("Error al obtener registros consolidados.", e);
			throw getRegistroTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al obtener registros consolidados.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public Registros obtenerRegistrosParaMostrar(RegistroConsulta query, Entidad entidad) throws RegistroTelematicoException {
		try {
			return getRegistrosServicio(RegistroManager.getRegistriesToShow(getRegistroConsultaImpl(query), entidad.getIdentificador()));
		} catch (RegistroExcepcion e) {
			logger.error("Error al obtener registros consolidados.", e);
			throw getRegistroTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al obtener registros consolidados.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public boolean tieneDocumentos (String registryNumber, Entidad entidad) throws RegistroTelematicoException {
		try {
			return RegistroManager.hasDocuments(registryNumber, entidad.getIdentificador());
		} catch (RegistroExcepcion e) {
			logger.error("Error al obtener si tiene documentos el registro.", e);
			throw getRegistroTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al obtener si tiene documentos el registro.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public ByteArrayOutputStream obtenerContenidoDocumento (String sessionId, String registryNumber,
			String code, Entidad entidad) throws RegistroTelematicoException {
		try {
			return RegistroManager.getDocumentContent(sessionId, registryNumber, code, entidad.getIdentificador());
		} catch (RegistroExcepcion e) {
			logger.error("Error al obtener contenido de documento.", e);
			throw getRegistroTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al obtener contenido de documento.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public byte[] obtenerDocumento (String registryNumber, String code, Entidad entidad) throws RegistroTelematicoException {
		try {
			return RegistroManager.getDocument(registryNumber, code, entidad.getIdentificador());
		} catch (RegistroExcepcion e) {
			logger.error("Error al obtener contenido de documento.", e);
			throw getRegistroTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al obtener contenido de documento.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void establecerEstadoRegistro (String registryNumber, int status, Entidad entidad) throws RegistroTelematicoException {
		try {
			RegistroManager.setRegistryStatus(registryNumber, status, entidad.getIdentificador());
		} catch (RegistroExcepcion e) {
			logger.error("Error al establecer estado de registro.", e);
			throw getRegistroTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al establecer estado de registro.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public RegistroDocumentos obtenerDocumentosRegistro(String registryNumber, Entidad entidad) throws RegistroTelematicoException {
		try {
			return getRegistroDocumentosServicio(RegistroDocumentoManager.getRegistryDocuments(registryNumber, entidad.getIdentificador()));
		} catch (RegistroExcepcion e) {
			logger.error("Error al obtener documentos de registro.", e);
			throw getRegistroTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al obtener documentos de registro.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public RegistroDocumento obtenerDocumentoRegistro (String sessionId, String registryNumber, String code, Entidad entidad) throws RegistroTelematicoException {
		try {
			return getRegistroDocumentoServicio((ieci.tecdoc.sgm.registro.util.RegistroDocumentoImpl)
					RegistroDocumentoManager.getRegistryDocument(sessionId, registryNumber, code, entidad.getIdentificador()));
		} catch (RegistroExcepcion e) {
			logger.error("Error al obtener documento de registro.", e);
			throw getRegistroTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al obtener documento de registro.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public RegistroDocumento insertarDocumentoRegistro (RegistroDocumento registryDocument, Entidad entidad) throws RegistroTelematicoException{
		try {
			return getRegistroDocumentoServicio((ieci.tecdoc.sgm.registro.util.RegistroDocumentoImpl)
					RegistroDocumentoManager.addRegistryDocument(getRegistroDocumentoImpl(registryDocument), entidad.getIdentificador()));
		} catch (RegistroExcepcion e) {
			logger.error("Error al insertar documentos de registro.", e);
			throw getRegistroTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al insertar documentos de registro.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void actualizarDocumentoRegistro (RegistroDocumento registryDocument, Entidad entidad) throws RegistroTelematicoException {
		try {
			RegistroDocumentoManager.updateRegistryDocument(getRegistroDocumentoImpl(registryDocument), entidad.getIdentificador());
		} catch (RegistroExcepcion e) {
			logger.error("Error al atualizar documentos de registro.", e);
			throw getRegistroTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al actualizar documentos de registro.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void eliminarDocumentoRegistro (String registryNumber, Entidad entidad) throws RegistroTelematicoException {
		try {
			RegistroDocumentoManager.deleteRegistryDocument(registryNumber, entidad.getIdentificador());
		} catch (RegistroExcepcion e) {
			logger.error("Error al eliminar documentos de registro.", e);
			throw getRegistroTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al obtener documentos de registro.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public byte[] registrarTelematicoAndIniciarExpediente (String sessionId, byte[] registryRequest, String additionalInfo,
			String idiom, String oficina, String plantilla, String certificado, String tramiteId, Entidad entidad)
		throws RegistroTelematicoException {
		byte[] justificante = null;
		try {
			justificante =  registrar(sessionId, registryRequest, additionalInfo, idiom, oficina, plantilla, certificado, entidad);
			String strJustificante = Goodies.fromUTF8ToStr(justificante);
			ServicioCatalogoTramites oServicioCatalogo = LocalizadorServicios.getServicioCatalogoTramites();
			Tramite tramite = oServicioCatalogo.getProcedure(tramiteId, false, entidad);
			if (tramite.getIdProcedimiento() != null && !"".equals(tramite.getIdProcedimiento())) {
				if (!RegistroManager.iniciarExpediente(sessionId, strJustificante, tramiteId, additionalInfo,
						tramite.getIdProcedimiento(), entidad.getIdentificador())) {
					String numReg = RegistroManager.getNumeroRegistro(strJustificante);
					if (numReg != null)
						deshacerRegistro(sessionId, numReg, entidad);
					return null;
				}
			}
			return justificante;
		} catch (RegistroTelematicoException e) {
			if (justificante != null){
				try {
					String strJustificante = Goodies.fromUTF8ToStr(justificante);
					String numReg = RegistroManager.getNumeroRegistro(strJustificante);
					if (numReg != null)
						deshacerRegistro(sessionId, numReg, entidad);
				}catch(Exception ex) { }
			}
			logger.error("Error al registrar e iniciar expediente.", e);
			throw e;
		} catch (Throwable e){
			if (justificante != null){
				try {
					String strJustificante = Goodies.fromUTF8ToStr(justificante);
					String numReg = RegistroManager.getNumeroRegistro(strJustificante);
					if (numReg != null)
						deshacerRegistro(sessionId, numReg, entidad);
				}catch(Exception ex) { }
			}
			logger.error("Error inesperado al registrar e iniciar expediente.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public byte[] registrarTelematicoAndIniciarExpediente (String sessionId, byte[] registryRequest, String additionalInfo,
			String idiom, String oficina, byte[] plantilla, String certificado, String tramiteId, Entidad entidad)
		throws RegistroTelematicoException {
		byte[] justificante = null;
		try {
			justificante =  registrar(sessionId, registryRequest, additionalInfo, idiom, oficina, plantilla, certificado, entidad);
			String strJustificante = Goodies.fromUTF8ToStr(justificante);
			ServicioCatalogoTramites oServicioCatalogo = LocalizadorServicios.getServicioCatalogoTramites();
			Tramite tramite = oServicioCatalogo.getProcedure(tramiteId, false, entidad);
			if (tramite.getIdProcedimiento() != null && !"".equals(tramite.getIdProcedimiento())) {
				if (!RegistroManager.iniciarExpediente(sessionId, strJustificante, tramiteId, additionalInfo,
						tramite.getIdProcedimiento(), entidad.getIdentificador())) {
					String numReg = RegistroManager.getNumeroRegistro(strJustificante);
					if (numReg != null)
						deshacerRegistro(sessionId, numReg, entidad);
					return null;
				}
			}
			return justificante;
		} catch (RegistroTelematicoException e) {
			if (justificante != null){
				try {
					String strJustificante = Goodies.fromUTF8ToStr(justificante);
					String numReg = RegistroManager.getNumeroRegistro(strJustificante);
					if (numReg != null)
						deshacerRegistro(sessionId, numReg, entidad);
				}catch(Exception ex) { }
			}
			logger.error("Error al registrar e iniciar expediente.", e);
			throw e;
		} catch (Throwable e){
			if (justificante != null){
				try {
					String strJustificante = Goodies.fromUTF8ToStr(justificante);
					String numReg = RegistroManager.getNumeroRegistro(strJustificante);
					if (numReg != null)
						deshacerRegistro(sessionId, numReg, entidad);
				}catch(Exception ex) { }
			}
			logger.error("Error inesperado al registrar e iniciar expediente.", e);
			throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}


	public void deshacerRegistro (String sessionId, String registryNumber, Entidad entidad)
	throws RegistroTelematicoException {
	try {
		RegistroManager.deshacerRegistro(sessionId, registryNumber, entidad.getIdentificador());
	} catch (RegistroExcepcion e) {
		logger.error("Error al deshacer un registro.", e);
		throw getRegistroTelematicoException(e);
	} catch (Throwable e){
		logger.error("Error inesperado al deshacer un registro.", e);
		throw new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION, e);
	}
}

	private RegistroTelematicoException getRegistroTelematicoException(RegistroExcepcion poException){
		if(poException == null){
			return new RegistroTelematicoException(RegistroTelematicoException.EXC_GENERIC_EXCEPCION);
		}
		StringBuffer cCodigo = new StringBuffer(ConstantesServicios.SERVICE_EREGISTRY_ERROR_PREFIX);
		cCodigo.append(String.valueOf(poException.getErrorCode()));
		return new RegistroTelematicoException(Long.valueOf(cCodigo.toString()).longValue(), poException);
	}


	private ieci.tecdoc.sgm.registro.util.RegistroPeticion getRegistroPeticionImpl(RegistroPeticion poRegistroPeticion){
		ieci.tecdoc.sgm.registro.util.RegistroPeticion oRegistroPeticion = new ieci.tecdoc.sgm.registro.util.RegistroPeticion();
		if (poRegistroPeticion == null)
			return oRegistroPeticion;

		oRegistroPeticion.setAddressee(poRegistroPeticion.getAddressee());
		oRegistroPeticion.setDocuments(getPeticionDocumentosImpl(poRegistroPeticion.getDocuments()));
		oRegistroPeticion.setEmail(poRegistroPeticion.getEmail());
		oRegistroPeticion.setFolderId(poRegistroPeticion.getFolderId());
		oRegistroPeticion.setProcedureId(poRegistroPeticion.getProcedureId());
		oRegistroPeticion.setSenderIdType(poRegistroPeticion.getSenderIdType());
		oRegistroPeticion.setSpecificData(poRegistroPeticion.getSpecificData());

		return oRegistroPeticion;
	}


	private ieci.tecdoc.sgm.registro.util.RegistroDocumentoImpl getRegistroDocumentoImpl(RegistroDocumento poRegistroDocumento){
		ieci.tecdoc.sgm.registro.util.RegistroDocumentoImpl oRegistroDocumento = new ieci.tecdoc.sgm.registro.util.RegistroDocumentoImpl();
		if (poRegistroDocumento == null)
			return oRegistroDocumento;

		oRegistroDocumento.setCode(poRegistroDocumento.getCode());
		oRegistroDocumento.setGuid(poRegistroDocumento.getGuid());
		oRegistroDocumento.setRegistryNumber(poRegistroDocumento.getRegistryNumber());

		return oRegistroDocumento;
	}


	private ieci.tecdoc.sgm.registro.util.PeticionDocumentos getPeticionDocumentosImpl(PeticionDocumentos poPeticionDocumentos){
		ieci.tecdoc.sgm.registro.util.PeticionDocumentos oPeticionDocumentos = new ieci.tecdoc.sgm.registro.util.PeticionDocumentos();
		if (poPeticionDocumentos == null)
			return oPeticionDocumentos;

		for(int i=0; i<poPeticionDocumentos.count(); i++)
			oPeticionDocumentos.add(getPeticionDocumentoImpl((PeticionDocumento)poPeticionDocumentos.get(i)));

		return oPeticionDocumentos;
	}


	private ieci.tecdoc.sgm.registro.util.PeticionDocumento getPeticionDocumentoImpl(PeticionDocumento poPeticionDocumento){
		ieci.tecdoc.sgm.registro.util.PeticionDocumento oPeticionDocumento = new ieci.tecdoc.sgm.registro.util.PeticionDocumento();
		if (poPeticionDocumento == null)
			return oPeticionDocumento;

		oPeticionDocumento.setCode(poPeticionDocumento.getCode());
		oPeticionDocumento.setExtension(poPeticionDocumento.getExtension());
		oPeticionDocumento.setFileName(poPeticionDocumento.getFileName());
		oPeticionDocumento.setLocation(poPeticionDocumento.getLocation());
		oPeticionDocumento.setIdioma(poPeticionDocumento.getIdioma());

		return oPeticionDocumento;
	}


	private ieci.tecdoc.sgm.registro.util.RegistroConsulta getRegistroConsultaImpl(RegistroConsulta poRegistroConsulta){
		ieci.tecdoc.sgm.registro.util.RegistroConsulta oRegistroConsulta = new ieci.tecdoc.sgm.registro.util.RegistroConsulta();
		if (poRegistroConsulta == null)
			return oRegistroConsulta;

		oRegistroConsulta.setAddressee(poRegistroConsulta.getAddressee());
		oRegistroConsulta.setFirstRegistryDate(poRegistroConsulta.getFirstRegistryDate());
		oRegistroConsulta.setFolderId(poRegistroConsulta.getFolderId());
		oRegistroConsulta.setLastRegistryDate(poRegistroConsulta.getLastRegistryDate());
		oRegistroConsulta.setOprRegistryNumber(poRegistroConsulta.getOprRegistryNumber());
		oRegistroConsulta.setRegistryNumber(poRegistroConsulta.getRegistryNumber());
		oRegistroConsulta.setSenderId(poRegistroConsulta.getSenderId());
		oRegistroConsulta.setStatus(poRegistroConsulta.getStatus());
		oRegistroConsulta.setSubject(poRegistroConsulta.getSubject());
		oRegistroConsulta.setSubtype(poRegistroConsulta.getSubtype());
		oRegistroConsulta.setTopic(poRegistroConsulta.getTopic());
		oRegistroConsulta.setType(poRegistroConsulta.getType());
		oRegistroConsulta.setFirstEffectiveDate(poRegistroConsulta.getFirstEffectiveDate());
		oRegistroConsulta.setLastEffectiveDate(poRegistroConsulta.getLastEffectiveDate());
		return oRegistroConsulta;
	}


	private Registro getRegistroServicio(ieci.tecdoc.sgm.registro.util.RegistroImpl poRegistro){
		Registro oRegistro = new Registro();
		if (poRegistro == null)
			return oRegistro;

		oRegistro.setAddressee(poRegistro.getAddressee());
		oRegistro.setAdittionalInfo(poRegistro.getAdditionalInfo());
		oRegistro.setEMail(poRegistro.getEMail());
		oRegistro.setName(poRegistro.getName());
		oRegistro.setNumeroExpediente(poRegistro.getNumeroExpediente());
		oRegistro.setOficina(poRegistro.getOficina());
		oRegistro.setRegistryDate(poRegistro.getRegistryDate());
		oRegistro.setRegistryNumber(poRegistro.getRegistryNumber());
		//oRegistro.setRepresentedId(poRegistro.getRepresentedId());
		//oRegistro.setRepresentedName(poRegistro.getRepresentedName());
		oRegistro.setSenderId(poRegistro.getSenderId());
		oRegistro.setSenderIdType(poRegistro.getSenderIdType());
		oRegistro.setStatus(poRegistro.getStatus());
		//oRegistro.setSurName(poRegistro.getSurName());
		oRegistro.setTopic(poRegistro.getTopic());
		oRegistro.setEffectiveDate(poRegistro.getEffectiveDate());

		return oRegistro;
	}


	private Registros getRegistrosServicio(ieci.tecdoc.sgm.registro.util.Registros poRegistros){
		Registros oRegistros = new Registros();
		if (poRegistros == null)
			return oRegistros;

		for(int i=0; i<poRegistros.count(); i++)
			oRegistros.add(getRegistroServicio((ieci.tecdoc.sgm.registro.util.RegistroImpl)poRegistros.get(i)));

		return oRegistros;
	}


	private RegistroDocumento  getRegistroDocumentoServicio(ieci.tecdoc.sgm.registro.util.RegistroDocumentoImpl poRegistroDocumento){
		RegistroDocumento oRegistroDocumento = new RegistroDocumento();
		if (poRegistroDocumento == null)
			return oRegistroDocumento;

		oRegistroDocumento.setCode(poRegistroDocumento.getCode());
		oRegistroDocumento.setGuid(poRegistroDocumento.getGuid());
		oRegistroDocumento.setRegistryNumber(poRegistroDocumento.getRegistryNumber());

		return oRegistroDocumento;
	}


	private RegistroDocumentos getRegistroDocumentosServicio(ieci.tecdoc.sgm.registro.util.RegistroDocumentos poRegistroDocumentos){
		RegistroDocumentos oRegistroDocumentos = new RegistroDocumentos();
		if (poRegistroDocumentos == null)
			return oRegistroDocumentos;

		for(int i=0; i<poRegistroDocumentos.count(); i++)
			oRegistroDocumentos.add(getRegistroDocumentoServicio((ieci.tecdoc.sgm.registro.util.RegistroDocumentoImpl)poRegistroDocumentos.get(i)));

		return oRegistroDocumentos;
	}

}
