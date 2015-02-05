package es.ieci.sigem;

import java.sql.Timestamp;

import ieci.tecdoc.sgm.base.guid.Guid;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.calendario.RetornoCalendario;
import ieci.tecdoc.sgm.core.services.calendario.ServicioCalendario;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.telematico.RegistroEstado;
import ieci.tecdoc.sgm.rde.database.ContenedorDocumentoDatos;
import ieci.tecdoc.sgm.rde.database.util.Utilities;
import ieci.tecdoc.sgm.registro.RegistroDocumentoManager;
import ieci.tecdoc.sgm.registro.RegistroManager;
import ieci.tecdoc.sgm.registro.util.RegistroDocumentoImpl;
import ieci.tecdoc.sgm.registro.util.RegistroDocumentos;
import ieci.tecdoc.sgm.registro.util.database.RegistroDatos;
import ieci.tecdoc.sgm.registro.util.database.RegistroDocumentoDatos;

/**
 * Clase que proporciona la interfaz de funcionalidad de registro.
 * 
 * @author Tomas Cabrero
 * 
 */
public class RegistroTelematico
{
	public static final String CODIGO_JUSTIFICANTE = "Justificante de Registro";
	public static final String CODIGO_SOLICITUD_FIRMADA = "Solicitud Firmada";
	public static final String CODIGO_SOLICITUD = "Solicitud";
	
	public static final String TRAMITADOR_JUSTIFICANTE = "Justificante";
	public static final String TRAMITADOR_ANEXO_SOLICITUD = "Anexo a Solicitud";
	public static final String TRAMITADOR_SOLICITUD = "Solicitud Registro";
	
	public static final String EXTENSION_XML = "XML";
	public static final String EXTENSION_PDF = "PDF";
	public static final String EXTENSION_TXT = "TXT";
	
	private String entidad;

	public RegistroTelematico(String entidad)
	{
		this.entidad = entidad;
	}
	
	public String getEntidad()
	{
		return entidad;
	}

	public void setEntidad(String entidad)
	{
		this.entidad = entidad;
	}
	
	/**
	 * Inserta un nuevo registro
	 * 
	 * @param nif
	 * @param nombre
	 * @param email
	 * @param asunto
	 * @param oficina
	 * @param organo
	 * @param xmlInfoAdicional
	 * @return Número de registro
	 * @throws Exception
	 */
	public RegistroDatos registra(String nif, String nombre, String email, String asunto, String oficina,
			String organo, String xmlInfoAdicional)	throws Exception
	{		
		RegistroDatos rd = new RegistroDatos();		
		
		rd.setSenderId(nif);
		rd.setSenderIdType(1);
		rd.setName(nombre);
		rd.setEMail(email);
		rd.setTopic(asunto);
		rd.setOficina(oficina);
		rd.setAddressee(organo);
		rd.setAdittionalInfo(xmlInfoAdicional.getBytes());
		
		RegistroManager.createRegistryData(oficina, entidad, rd);
		
		return rd;
	}
	
	/**
	 * Devuelve los documentos anexos al registro
	 * 
	 * @param numRegistro
	 * @return
	 * @throws Exception
	 */
	public RegistroDocumentos getDocumentosRegistro(String numRegistro) throws Exception
	{
		RegistroDocumentoDatos rdd = new RegistroDocumentoDatos();
		
		rdd.setRegistryNumber(numRegistro);
		
		return rdd.getRegistryDocuments(entidad);
	}
	

	/**
	* Actualiza la fecha efectiva de registro y el estado
	*
	* @param numRegistro
	* @throws Exception
	*/
	public void actualizaRegistro(RegistroDatos registro) throws Exception
	{
		// Obtener la fecha efectiva del registro telemático
		Entidad oEntidad = new Entidad();
		oEntidad.setIdentificador(getEntidad());
	
		ServicioCalendario oServicioCalendario = LocalizadorServicios.getServicioCalendario();
		RetornoCalendario fechaEfectiva = oServicioCalendario.proximoLaborable(registro.getRegistryDate(), oEntidad);
		if (fechaEfectiva.isLaborable()) {
			registro.setEffectiveDate(registro.getRegistryDate());
		}
		else {
			registro.setEffectiveDate(fechaEfectiva.getProximo());
		}
	
		registro.setStatus(RegistroEstado.STATUS_NOT_CONSOLIDATED);
	
		registro.updateEffectiveDateAndStatus(entidad);
	}
	
	
	
	/**
	 * Elimina el registro y sus documentos asociados
	 * 
	 * @param numRegistro
	 * @throws Exception
	 */
	public void eliminaRegistro(String numRegistro) throws Exception
	{
		RegistroDatos rd = new RegistroDatos();
		
		rd.setRegistryNumber(numRegistro);
		
		rd.delete(entidad);
		
		deleteRegisterDocuments(numRegistro);
	}
	
	/**
	 * guarda el binario y lo añade como documento anexo al registro
	 * 
	 * @param numeroRegistro
	 * @param code
	 * @param extension
	 * @param documentData
	 * @throws Exception
	 */
	public void storeAndRegisterDocument(String numeroRegistro, String code, String extension, byte[] documentData)
		throws Exception
	{		
		addRegisterDocument(numeroRegistro, code, addDocument(extension, documentData));
	}
	
	/**
	 * añade el binario ya guardado como documento anexo al registro
	 * 
	 * @param entidad
	 * @param numeroRegistro
	 * @param codigo (Justificante de Registro, Solicitud, Solicitud Firmada)
	 * @throws Exception
	 */
	public void addRegisterDocument(String numeroRegistro, String codigo, String documentGuid) throws Exception
	{		
		RegistroDocumentoImpl rd = new RegistroDocumentoImpl();
		
		rd.setGuid(documentGuid);
		rd.setRegistryNumber(numeroRegistro);
		rd.setCode(codigo);
		
		RegistroDocumentoManager.addRegistryDocument(rd, entidad);
	}
	
	/**
	 * Borra los documentos asociados a un registro eliminando tambien los binarios
	 * 
	 * @param numeroRegistro
	 * @throws Exception
	 */
	public void deleteRegisterDocuments(String numeroRegistro) throws Exception
	{
		RegistroDocumentoDatos rdd = new RegistroDocumentoDatos();
		
		rdd.setRegistryNumber(numeroRegistro);
		
		RegistroDocumentos rDocs = rdd.getRegistryDocuments(entidad);
		
		// Borra los binarios
		for (int i = 0; i < rDocs.count(); i++)
		{
			deleteDocument(rDocs.get(i).getGuid());
		}
		
		rdd.delete(entidad);
	}
	
	/**
	 * Almacena el documento
	 * 
	 * @param entidad
	 * @param extension
	 * @param documentData
	 * @return
	 * @throws Exception
	 */
	public String addDocument(String extension, byte[] documentData) throws Exception
	{
		ContenedorDocumentoDatos cdd = new ContenedorDocumentoDatos();
		
		cdd.setGuid(new Guid().toString());
		cdd.setContent(documentData);
		cdd.setExtension(extension);
		cdd.setHash(Utilities.getHash(documentData));
		cdd.setTimestamp(new Timestamp(System.currentTimeMillis()));
		cdd.add(entidad);
		
		return cdd.getGuid();
	}
	
	public ContenedorDocumentoDatos getDocument(String guid) throws Exception
	{
		ContenedorDocumentoDatos cdd = new ContenedorDocumentoDatos();		
		
		cdd.load(guid, entidad);
		
		return cdd;
	}
	
	/**
	 * Elimina el documento
	 * 
	 * @param guid
	 * @throws Exception
	 */
	public void deleteDocument(String guid) throws Exception
	{
		ContenedorDocumentoDatos cdd = new ContenedorDocumentoDatos();
		
		cdd.setGuid(guid);
		
		cdd.delete(entidad);
	}
}