package com.ieci.tecdoc.isicres.audit.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxXf;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrDocument;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrInter;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrPage;
import com.ieci.tecdoc.isicres.session.folder.FolderDataSession;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

import es.ieci.tecdoc.fwktd.audit.integration.business.vo.AuditContext;
import es.ieci.tecdoc.fwktd.audit.integration.context.AuditContextHolder;
import es.ieci.tecdoc.isicres.api.audit.ISicresAuditConstants;
import es.ieci.tecdoc.isicres.api.audit.business.manager.IsicresAuditoriaManager;
import es.ieci.tecdoc.isicres.api.audit.business.manager.impl.IsicresAuditoriaManagerImpl;
import es.ieci.tecdoc.isicres.api.audit.business.vo.IsicresAuditoriaValorModificadoVO;
import es.ieci.tecdoc.isicres.api.audit.business.vo.events.IsicresAuditEventAccesoAplicacionVO;
import es.ieci.tecdoc.isicres.api.audit.business.vo.events.IsicresAuditEventAccesoRegistroVO;
import es.ieci.tecdoc.isicres.api.audit.business.vo.events.IsicresAuditEventCreacionRegistroVO;
import es.ieci.tecdoc.isicres.api.audit.business.vo.events.IsicresAuditEventModificacionRegistroVO;

public class ISicresAuditHelper {

	private static final String MENSAJE_NOT_FOUND_DATA_HOST_OR_IP = "Invocación desde servidor";
	private static final String CAMPOS_MODIFICADOS = "CAMPOS_MODIFICADOS";
	private static final String ID_REGISTRO = "idRegistro";
	private static final String NUMERO_REGISTRO = "numeroRegistro";
	private static final String XML_VALOR_NUEVO = "VALOR_NUEVO";
	private static final String XML_VALOR_ANTIGUO = "VALOR_ANTIGUO";
	private static final String XML_DATOS_DEL_REGISTRO = "DATOS_DEL_REGISTRO";
	private static final String XML_CAMPOS = "CAMPOS";
	private static final String XML_CAMPOS_EXTENDIDOS = "CAMPOS_EXTENDIDOS";
	private static final String FLD = "fld";
	private static final String XML_INTERESADOS = "INTERESADOS";
	private static final String XML_DIRECCION_REPRESENTANTE = "DIRECCION_REPRESENTANTE";
	private static final String XML_ID_DIRECCION_REPRESENTANTE = "ID_DIRECCION_REPRESENTANTE";
	private static final String XML_NOMBRE_REPRESENTANTE = "NOMBRE_REPRESENTANTE";
	private static final String XML_ID_REPRESENTANTE = "ID_REPRESENTANTE";
	private static final String XML_DIRECCION = "DIRECCION";
	private static final String XML_ID_DIRECCION = "ID_DIRECCION";
	private static final String XML_NOMBRE_INTERESADO = "NOMBRE_INTERESADO";
	private static final String XML_ID_INTERESADO = "ID_INTERESADO";
	private static final String XML_INTERESADO = "INTERESADO";
	private static final String XML_DOCUMENTOS = "DOCUMENTOS";
	private static final String XML_DOCUMENTO = "DOCUMENTO";
	private static final String XML_ID_DOCUMENTO = "ID_DOCUMENTO";
	private static final String XML_NOMBRE_DOCUMENTO = "NOMBRE_DOCUMENTO";
	private static final String XML_PAGINAS = "PAGINAS";
	private static final String XML_NOMBRE_PAGINA = "NOMBRE_PAGINA";
	private static final String XML_ID_PAGINA = "ID_PAGINA";
	private static final String XML_PAGINA = "PAGINA";


	private static final Logger log = Logger.getLogger(ISicresAuditHelper.class);

	/**
	 * Método que audita el acceso a la aplicación
	 *
	 * @param user - Datos del usuario logeado
	 * @param scrofic - Oficina del usuario logeado
	 *
	 */
	public static void auditarLogin(AuthenticationUser user, ScrOfic scrofic) {
		try{
			// Auditamos acceso a la aplicación
			IsicresAuditoriaManager auditoria = new IsicresAuditoriaManagerImpl();

			// obtenemos el evento
			IsicresAuditEventAccesoAplicacionVO evento = adapterEventoAuditAccesoAplicacion(
					user, scrofic);

			// auditamos
			auditoria.audit(evento);
		}catch (Exception e){
			log.warn("No se ha podido realizar la auditoría de la autenticación de la aplicación", e);
		}
	}

	/**
	 * Método que audita el acceso de un registro
	 *
	 * @param sessionID - Identificador de session
	 * @param bookId - Identificador de libro
	 * @param folderId - Identificador de registro
	 * @param axsf - Información del registro
	 * @param archiveName - Nombre del libro
	 * @param bookType - Tipo de libro (1-Libro de Entrada/2-Libro de salida)
	 *
	 */
	public static void auditarAccesoRegistro(String sessionID, Integer bookId,
			int folderId, AxSf axsf, String archiveName, int bookType) {
		try{
			// Auditoria de acceso a un registro
			IsicresAuditoriaManager auditoria = new IsicresAuditoriaManagerImpl();

			// Obtenemos el evento
			IsicresAuditEventAccesoRegistroVO evento = getEventoAuditAccesoRegistro(
					sessionID, bookId, folderId, axsf, archiveName, bookType);

			// Auditamos
			auditoria.audit(evento);
		}catch(Exception e){
			StringBuffer sb = new StringBuffer();
			sb.append(
					"No ha sido posible realizar la auditoría de acceso al registro con ID [")
					.append(folderId).append("] del libro [")
					.append(archiveName).append("con ID [").append(bookId)
					.append("]");
			log.warn(sb.toString(), e);
		}
	}

	/**
	 * Método que audita el proceso de creación de un registro
	 *
	 * @param inter - listado de objetos {@link FlushFdrInter} Listado de interesados del registro
	 * @param documentos - Listado de documentos adjuntados al registro
	 * @param folderData - {@link FolderDataSession} Datos del registro
	 */
	public static void auditoriaCrearRegistro(List inter, Map documentos,
			FolderDataSession folderData) {
		try{
			// Generamos el evento auditoria
			IsicresAuditoriaManager auditoria = new IsicresAuditoriaManagerImpl();

			// Obtenemos el evento con la información necesaria
			IsicresAuditEventCreacionRegistroVO evento = getEventoAuditCrearRegistro(
					inter, documentos, folderData);

			// Auditamos
			auditoria.audit(evento);
		}catch (Exception e){
			log.warn("No se ha completado la auditoría de creación del registro", e);
		}
	}

	/**
	 * Método que audita el proceso de modificación de un registro
	 *
	 * @param axsfNew - {@link AxSf} Datos del nuevo registro
	 * @param inter - Listado {@link FlushFdrInter} de interesados del registro
	 * @param axsfOld - {@link AxSf} Datos del registro antes de ser modificado
	 * @param data - {@link FolderDataSession} Datos del registro modificado y del proceso de actualización
	 *
	 * @throws BookException
	 */
	public static void auditarModificacionRegistro(List inter, AxSf axsfOld,
			FolderDataSession data) throws BookException {

		try{
			// Auditar cambios registro
			IsicresAuditoriaManager auditoria = new IsicresAuditoriaManagerImpl();

			// Obtenemos el objeto evento
			IsicresAuditEventModificacionRegistroVO evento = getEventoAuditarModificacionRegistro(
					inter, axsfOld, data);

			// Auditamos
			auditoria.audit(evento);
		}catch (Exception e){
			log.warn("No se ha completado la auditoría de modificación del registro", e);
		}
	}


	/**
	 * Método que adapta los datos del usuario logado a los datos necesarios para la auditoria
	 *
	 * @param user - Datos del usuario logeado
	 * @param scrofic - Oficina del usuario logeado
	 * @return {@link IsicresAuditEventAccesoAplicacionVO} - Datos del evento de acceso
	 */
	private static IsicresAuditEventAccesoAplicacionVO adapterEventoAuditAccesoAplicacion(
			AuthenticationUser user, ScrOfic scrofic) {

		IsicresAuditEventAccesoAplicacionVO eventoAcceso = new IsicresAuditEventAccesoAplicacionVO();

		AuditContext auditContext = AuditContextHolder.getAuditContext();

		eventoAcceso.setAppId(ISicresAuditConstants.getAppId());
		eventoAcceso.setAppDescription(ISicresAuditConstants.getAppDescription());

		// Fecha actual
		eventoAcceso.setFecha(new Date());

		if(scrofic != null){
			// Id de la oficina del usuario logado
			eventoAcceso.setIdOficina(scrofic.getId().toString());
			// Nombre de la oficina
			eventoAcceso.setOficina(scrofic.getName());
		}
		// Id del usuario logado
		eventoAcceso.setIdUser(user.getId().toString());
		// Nombre del usuario logado
		eventoAcceso.setUser(user.getName());

		if (auditContext != null) {
			eventoAcceso.setUserHostName(getStringInfoIPHost(auditContext.getUserHost()));
			eventoAcceso.setUserIp(getStringInfoIPHost(auditContext.getUserIP()));
		} else {
			eventoAcceso.setUserHostName(getStringInfoIPHost(null));
			eventoAcceso.setUserIp(getStringInfoIPHost(null));
			log.warn("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría. Faltan los siguientes valores por auditar: userHost y userIp");
		}

		return eventoAcceso;
	}


	private static IsicresAuditEventAccesoRegistroVO getEventoAuditAccesoRegistro(
			String sessionID, Integer bookId, int folderId, AxSf axsf,
			String archiveName, int bookType) {

		// Creamos el evento
		IsicresAuditEventAccesoRegistroVO evento = new IsicresAuditEventAccesoRegistroVO();

		AuditContext auditContext = AuditContextHolder.getAuditContext();

		// Datos de la aplicación
		evento.setAppId(ISicresAuditConstants.getAppId());
		evento.setAppDescription(ISicresAuditConstants.getAppDescription());

		// Fecha
		evento.setFecha(new Date());

		// Datos del libro
		evento.setIdLibro(bookId.toString());
		evento.setIdTipoLibro(Integer.toString(bookType));
		evento.setLibro(archiveName);

		if (auditContext != null) {
			evento.setUserHostName(getStringInfoIPHost(auditContext.getUserHost()));
			evento.setUserIp(getStringInfoIPHost(auditContext.getUserIP()));
		} else {
			evento.setUserHostName(getStringInfoIPHost(null));
			evento.setUserIp(getStringInfoIPHost(null));
			log.warn("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría. Faltan los siguientes valores por auditar: userHost y userIp");
		}

		AuthenticationUser user = null;
		ScrOfic oficina = null;
		try {
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			user = (AuthenticationUser) cacheBag
					.get(HibernateKeys.HIBERNATE_Iuseruserhdr);

			// Datos del usuario
			evento.setIdUser(user.getId().toString());
			evento.setUser(user.getName());

			oficina = (ScrOfic) cacheBag.get(HibernateKeys.HIBERNATE_ScrOfic);

			if(oficina != null){
				evento.setIdOficina(oficina.getId().toString());
				evento.setOficina(oficina.getName());
			}

		} catch (TecDocException e) {
			log.warn("ERROR EN LA AUDITORÍA. No están disponibles todos los datos. Faltan los siguientes valores por auditar: usuario y oficina");
		}

		evento.setIdRegistro(Integer.toString(folderId));
		evento.setNumRegistro(axsf.getAttributeValueAsString(AxSf.FLD1_FIELD));

		return evento;
	}

	/**
	 * Método que obtiene el objeto {@link IsicresAuditEventModificacionRegistroVO}
	 * con los datos del registro (valores anteriores a la modificación y valores modificados), se genera un XML que contiene la información
	 * del registro:
	 *
	 * <?xml version="1.0" encoding="UTF-8"?>
	 *	<DATOS_MODIFICADOS_DEL_REGISTRO numeroRegistro="201200100000556" idRegistro="581">
	 *		<CAMPOS_MODIFICADOS>
	 *			<fld8>
	 *				<VALOR_ANTIGUO>4888</VALOR_ANTIGUO>
	 *				<VALOR_NUEVO>4887</VALOR_NUEVO>
	 *			</fld8>
	 *			<fld17>
	 *				<VALOR_ANTIGUO>RESUMEN DEL REGISTRO</VALOR_ANTIGUO>
	 *				<VALOR_NUEVO>RESUMEN DEL REGISTRO MODIFICADO</VALOR_NUEVO>
	 *			</fld17>
	 *			<fld7>
	 * 				<VALOR_ANTIGUO>4887</VALOR_ANTIGUO>
	 *				<VALOR_NUEVO>4889</VALOR_NUEVO>
	 *			</fld7>
	 *		</CAMPOS_MODIFICADOS>
	 *		<CAMPOS_EXTENDIDOS_MODIFICADOS>
	 *			<fld18>
	 *				<VALOR_ANTIGUO>ESTO ES UN COMENTARIO DE REGISTRO</VALOR_ANTIGUO>
	 *				<VALOR_NUEVO>ESTO ES UN COMENTARIO DE REGISTRO MODIFICADO</VALOR_NUEVO>
	 *			</fld18>
	 *		</CAMPOS_EXTENDIDOS_MODIFICADOS>
	 *		<INTERESADOS>
	 *			<INTERESADO>
	 *				<ID_INTERESADO>1</ID_INTERESADO>
	 *				<NOMBRE_INTERESADO>FERNÁNDEZ FDEZ. FRANCISCO</NOMBRE_INTERESADO>
	 *				<ID_DIRECCION>0</ID_DIRECCION>
	 *				<DIRECCION></DIRECCION>
	 *				<ID_REPRESENTANTE>3</ID_REPRESENTANTE>
	 *				<NOMBRE_REPRESENTANTE>MARTÍNEZ MARTÍN, JUAN</NOMBRE_REPRESENTANTE>
	 *				<ID_DIRECCION_REPRESENTANTE>0</ID_DIRECCION_REPRESENTANTE>
	 *				<DIRECCION_REPRESENTANTE></DIRECCION_REPRESENTANTE>
	 *			</INTERESADO>
	 *			<INTERESADO>
	 *				<ID_INTERESADO>18</ID_INTERESADO>
	 *				<NOMBRE_INTERESADO>CHAVEZ RAMIRO JOSE </NOMBRE_INTERESADO>
	 *				<ID_DIRECCION>0</ID_DIRECCION>
	 *				<DIRECCION></DIRECCION>
	 *			</INTERESADO>
	 *		</INTERESADOS>
	 *		<DOCUMENTOS>
	 *			<DOCUMENTO>
	 *				<ID_DOCUMENTO>1</ID_DOCUMENTO>
	 *				<NOMBRE_DOCUMENTO>Documentos adjuntados</NOMBRE_DOCUMENTO>
	 *				<PAGINAS>
	 *					<PAGINA>
	 *						<ID_PAGINA></ID_PAGINA>
	 *						<NOMBRE_PAGINA>doc_adjuntado_registro.pdf</NOMBRE_PAGINA>
	 *					</PAGINA>
	 *				</PAGINAS>
	 *			</DOCUMENTO>
	 *		</DOCUMENTOS>
	 *	</DATOS_MODIFICADOS_DEL_REGISTRO>
	 * @param inter
	 * @param axsfOld
	 * @param data
	 * @return
	 */
	private static IsicresAuditEventModificacionRegistroVO getEventoAuditarModificacionRegistro(List inter, AxSf axsfOld, FolderDataSession data) {
		//Obtenemos el evento
		IsicresAuditEventModificacionRegistroVO evento = new IsicresAuditEventModificacionRegistroVO();

		AuditContext auditContext = AuditContextHolder.getAuditContext();

		//Datos de la aplicación
		evento.setAppId(ISicresAuditConstants.getAppId());
		evento.setAppDescription(ISicresAuditConstants.getAppDescription());

		//Fecha
		evento.setFecha(new Date());

		//Datos del libro
		evento.setIdLibro(data.getScrregstate().getIdocarchhdr().getId().toString());
		evento.setIdTipoLibro(Integer.toString(data.getScrregstate().getIdocarchhdr().getType()));
		evento.setLibro(data.getScrregstate().getIdocarchhdr().getName());

		//Datos de la oficina
		evento.setIdOficina(data.getScrofic().getId().toString());
		evento.setOficina(data.getScrofic().getName());

		//Datos del usuario
		evento.setIdUser(data.getUserId().toString());
		evento.setUser(data.getUserName());

		if (auditContext != null) {
			evento.setUserHostName(getStringInfoIPHost(auditContext.getUserHost()));
			evento.setUserIp(getStringInfoIPHost(auditContext.getUserIP()));
		} else {
			evento.setUserHostName(getStringInfoIPHost(null));
			evento.setUserIp(getStringInfoIPHost(null));
			log.warn("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría. Faltan los siguientes valores por auditar: userHost y userIp");
		}

		//Datos del registro
		evento.setNumRegistro(data.getOldAttributeValueAsString(AxSf.FLD1_FIELD));
		evento.setIdRegistro(data.getOldAttributeValueAsString(AxSf.FDRID_FIELD));

		List<IsicresAuditoriaValorModificadoVO> listAuditCambiosRegistro = new ArrayList();

		//Generamos XML con la información del registro
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("DATOS_MODIFICADOS_DEL_REGISTRO");
		root.addAttribute(NUMERO_REGISTRO, evento.getNumRegistro());
		root.addAttribute(ID_REGISTRO, evento.getIdRegistro());

		Element elementXMLCamposModifReg = root.addElement(CAMPOS_MODIFICADOS);

		IsicresAuditoriaValorModificadoVO campo = null;
		//Auditamos los campos del registro
		auditCamposModificadosRegistro(axsfOld, data, listAuditCambiosRegistro,
				elementXMLCamposModifReg);

		//Auditamos los campos extendidos del registro
		//Element elementXMLCamposExtModifReg = root.addElement("CAMPOS_EXTENDIDOS_MODIFICADOS");

		auditCamposExtendidosModificadosRegistro(axsfOld, data, listAuditCambiosRegistro);

		//Auditamos los interesados
		if(inter != null && !inter.isEmpty()){
			//Obtenemos elemento XML con la información de interesados
			Element interesados = getXMLInteresados(inter);
			//Generamos la auditoria de los interesados
			campo = new IsicresAuditoriaValorModificadoVO();
			campo.setFieldName(XML_INTERESADOS);
			campo.setNewValue(interesados.asXML());
			listAuditCambiosRegistro.add(campo);
		}

		//Auditamos los documentos modificados
		if(data.getDocumentsUpdate() != null && !data.getDocumentsUpdate().isEmpty()){
			//Obtenemos elemento XML con la información de los documentos
			Element documentosXML = getXMLDocumentos(data.getDocumentsUpdate(), root);
			//Generamos la auditoria de los documentos
			campo = new IsicresAuditoriaValorModificadoVO();
			campo.setFieldName(XML_DOCUMENTOS);
			campo.setNewValue(documentosXML.asXML());
			listAuditCambiosRegistro.add(campo);
		}

		//Añadimos la información de cada uno de los campos del registro al evento
		evento.setValores(listAuditCambiosRegistro);

		//Añadimos información XML del registro al evento auditoria
		evento.setInfoRegistroXML(document.asXML());

		return evento;
	}

	/**
	 * Método que obtiene el objeto {@link IsicresAuditEventCreacionRegistroVO}
	 * con los datos del registro, se genera un XML que contiene la información
	 * del registro:
	 *
	 * Ejemplo XML:
	 *
	 *	<?xml version="1.0" encoding="UTF-8"?>
	 *	<DATOS_DEL_REGISTRO>
	 *		<CAMPOS>
	 *			<fdrid>581</fdrid>
	 *			<fld9>FERNÁNDEZ FDEZ. FRANCISCO</fld9>
	 *			<fld3>REGISTRADOR</fld3>
	 *			<fld5>2</fld5>
	 *			<fld1>201200100000556</fld1>
	 *			<fld8>4888</fld8>
	 *			<fld6>0</fld6>
	 *			<fld17>RESUMEN DEL REGISTRO</fld17>
	 *			<timestamp>Fri Sep 14 10:58:54 CEST 2012</timestamp>
	 *			<fld4>Fri Sep 14 00:00:00 CEST 2012</fld4>
	 *			<fld2>Fri Sep 14 10:58:54 CEST 2012</fld2>
	 *			<fld7>4887</fld7>
	 *		</CAMPOS>
	 *		<DOCUMENTOS>
	 *			<DOCUMENTO>
	 *				<ID_DOCUMENTO>1</ID_DOCUMENTO>
	 *				<NOMBRE_DOCUMENTO>Documentos adjuntados</NOMBRE_DOCUMENTO>
	 *				<PAGINAS>
	 *					<PAGINA>
	 *						<ID_PAGINA></ID_PAGINA>
	 *						<NOMBRE_PAGINA>doc_adjuntado_registro.pdf</NOMBRE_PAGINA>
	 *					</PAGINA>
	 *				</PAGINAS>
	 *			</DOCUMENTO>
	 *		</DOCUMENTOS>
	 *	</DATOS_DEL_REGISTRO>
	 *
	 * @param listaInteresados
	 *            - Listado de interesados del registro
	 * @param documentos
	 *            - Listado de documentos adjuntados al registro
	 * @param folderData
	 *            - {@link FolderDataSession} Datos del registro
	 *
	 * @return {@link IsicresAuditEventCreacionRegistroVO}
	 */

	private static IsicresAuditEventCreacionRegistroVO getEventoAuditCrearRegistro(
			List listaInteresados, Map documentos, FolderDataSession folderData) {

		IsicresAuditEventCreacionRegistroVO evento = new IsicresAuditEventCreacionRegistroVO();

		AuditContext auditContext = AuditContextHolder.getAuditContext();

		//Datos de la aplicación
		evento.setAppId(ISicresAuditConstants.getAppId());
		evento.setAppDescription(ISicresAuditConstants.getAppDescription());

		//Fecha
		evento.setFecha(new Date());

		//Datos del libro
		evento.setIdLibro(folderData.getScrregstate().getIdocarchhdr().getId().toString());
		evento.setIdTipoLibro(Integer.toString(folderData.getScrregstate().getIdocarchhdr().getType()));
		evento.setLibro(folderData.getScrregstate().getIdocarchhdr().getName());

		//Datos de la oficina
		evento.setIdOficina(folderData.getScrofic().getId().toString());
		evento.setOficina(folderData.getScrofic().getName());

		//Datos del usuario
		evento.setIdUser(folderData.getUserId().toString());
		evento.setUser(folderData.getUserName());

		if (auditContext != null) {
			evento.setUserHostName(getStringInfoIPHost(auditContext.getUserHost()));
			evento.setUserIp(getStringInfoIPHost(auditContext.getUserIP()));
		} else {
			evento.setUserHostName(getStringInfoIPHost(null));
			evento.setUserIp(getStringInfoIPHost(null));
			log.warn("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría. Faltan los siguientes valores por auditar: userHost y userIp");
		}

		//Datos del registro
		evento.setNumRegistro(folderData.getNewAttributeValueAsString(AxSf.FLD1_FIELD));
		evento.setIdRegistro(folderData.getNewAttributeValueAsString(AxSf.FDRID_FIELD));

		List<IsicresAuditoriaValorModificadoVO> auditCamposNuevoRegistro = new ArrayList();
		IsicresAuditoriaValorModificadoVO campo = null;

		//Generamos XML con la información del registro
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(XML_DATOS_DEL_REGISTRO);
		Element elementXMLCamposRegistro = root.addElement(XML_CAMPOS);

		//Auditamos los campos del registro
		auditCamposNuevoRegistro(folderData.getAxsfNew(), auditCamposNuevoRegistro, elementXMLCamposRegistro);


		//Auditamos los campos extendidos del registro
		auditCamposExtendidosNuevoRegistro(folderData.getAxsfNew().getExtendedFields(), auditCamposNuevoRegistro);

		//Auditamos los interesados
		if(listaInteresados != null && !listaInteresados.isEmpty()){
			//Obtenemos elemento XML con la información de interesados
			Element interesados = getXMLInteresados(listaInteresados);
			//Generamos la auditoria de los interesados
			campo = new IsicresAuditoriaValorModificadoVO();
			campo.setFieldName(XML_INTERESADOS);
			campo.setNewValue(interesados.asXML());
			auditCamposNuevoRegistro.add(campo);
		}

		//Auditamos los documentos adjuntados
		if(documentos != null && !documentos.isEmpty()){
			//Obtenemos elemento XML con la información de los documentos
			Element documentosXML = getXMLDocumentos(documentos, root);
			//Generamos la auditoria de los documentos
			campo = new IsicresAuditoriaValorModificadoVO();
			campo.setFieldName(XML_DOCUMENTOS);
			campo.setNewValue(documentosXML.asXML());
			auditCamposNuevoRegistro.add(campo);

		}
		//Añadimos la información de cada uno de los campos del registro al evento
		evento.setValores(auditCamposNuevoRegistro);
		//Añadimos información XML del registro al evento auditoria
		evento.setInfoRegistroXML(document.asXML());

		return evento;
	}



	/**
	 * Método que añade al array pasado como parámetro la información de los
	 * campos del registro además también se añade la información del campo al
	 * XML pasado como parámetro
	 *
	 * @param axsfOld
	 *            - Información del registro sin modificar
	 * @param data
	 *            - Datos del registro modificado
	 * @param listAuditCambiosRegistro
	 *            - Listado en el que se añade la información con los campos
	 *            modificados
	 * @param elementXMLCamposExtModifReg
	 *            - Elemento XML en el que se añade la información de la
	 *            auditoría de cambios
	 */
	private static void auditCamposExtendidosModificadosRegistro(AxSf axsfOld,
			FolderDataSession data,
			List<IsicresAuditoriaValorModificadoVO> listAuditCambiosRegistro) {
		IsicresAuditoriaValorModificadoVO campo;
		//Recorremos los campos extendidos del registro
		for(Iterator it = data.getAxsfNew().getExtendedFields().keySet().iterator(); it.hasNext();){
			Integer key = (Integer) it.next();
			AxXf oldValueAxXf = (AxXf) axsfOld.getExtendedFields().get(key);
			AxXf newValueAxXf = (AxXf) data.getAxsfNew().getExtendedFields().get(key);

			String oldValue = null;
			String newValue = null;

			//validamos que el objeto oldValueAxXf venga distinto de nulo
			if(oldValueAxXf != null){
				oldValue = oldValueAxXf.getText();
			}
			//validamos que el objeto newValueAxXf venga distinto de nulo
			if(newValueAxXf != null){
				newValue = newValueAxXf.getText();
			}

			if(!StringUtils.equals(oldValue, newValue)){
				//Nombre del campo extendido
				String nameFld = FLD + key;

				//Añadimos al array de campos el campo modificado
				campo = addCampoArrayAudit(nameFld, oldValue, newValue);
				listAuditCambiosRegistro.add(campo);
			}
		}
	}

	/**
	 * Método que añade al array pasado como parámetro la información de los campos modificados del registro
	 * además también se añade la información del campo al XML pasado como parámetro
	 *
	 * @param axsfOld - Datos del registro antes de su modificación
	 * @param data - {@link FolderDataSession} Información del registro
	 * @param listAuditCambiosRegistro - Listado de campos modificados
	 * @param elementXMLCamposModifReg - XML con la información del registro y los campos modificados
	 *
	 */
	private static void auditCamposModificadosRegistro(AxSf axsfOld,
			FolderDataSession data,
			List<IsicresAuditoriaValorModificadoVO> listAuditCambiosRegistro,
			Element elementXMLCamposModifReg) {

		IsicresAuditoriaValorModificadoVO campo = null;
		//Recorremos los campos modificados del registro
		for(Iterator it = data.getAxsfNew().getAttributesValues().keySet().iterator(); it.hasNext();){
			String key = (String) it.next();
			String oldValue = axsfOld.getAttributeValueAsString(key);
			String newValue = data.getAxsfNew().getAttributeValueAsString(key);

			if(!StringUtils.equals(oldValue, newValue)){
				//Añadimos al nodo de campos modificados la información del campo
				addElementXMLCampoModificado(elementXMLCamposModifReg, key,
						oldValue, newValue);

				//Añadimos al listado de campos modificados el campo aqui tratado
				campo = addCampoArrayAudit(key, oldValue, newValue);
				listAuditCambiosRegistro.add(campo);
			}
		}
	}

	/**
	 * Añade nodo XML con la información de un campo, al elemento pasado como parámetro
	 *
	 * @param elementXMLCamposModifReg
	 * @param nameFld - Nombre del campo
	 * @param oldValue - Valor del campo anterior a la modificación
	 * @param newValue - Valor del campo después de la modificación
	 *
	 */
	private static void addElementXMLCampoModificado(Element elementXMLCamposModifReg,
			String nameFld, String oldValue, String newValue) {

		Element xmlCampoModifReg = elementXMLCamposModifReg.addElement(nameFld);
		if(!StringUtils.isBlank(oldValue)){
			xmlCampoModifReg.addElement(XML_VALOR_ANTIGUO).addText(oldValue);
		}
		if(!StringUtils.isBlank(newValue)){
			xmlCampoModifReg.addElement(XML_VALOR_NUEVO).addText(newValue);
		}
	}

	/**
	 * Método que añade al array pasado como parametro la información del campo
	 * extendido, además también se añade la información del campo al XML
	 *
	 * @param camposExtendidos
	 *            - Campos extendidos del registro
	 * @param auditCamposRegistro
	 *            - Array de cambios
	 * @param elementXMLcamposExtendidosRegistro
	 *            - Nodo XML de campos extendidos, donde añadimos la información del array de campos extendidos
	 *
	 */
	private static void auditCamposExtendidosNuevoRegistro(
			Map camposExtendidos,
			List<IsicresAuditoriaValorModificadoVO> auditCamposRegistro) {

		IsicresAuditoriaValorModificadoVO campo = null;
		//Recorremos los campos extendidos
		for (Iterator it = camposExtendidos.keySet().iterator(); it.hasNext();) {
			Integer key = (Integer) it.next();
			String nameFld = FLD + key;
			AxXf newValue = (AxXf) camposExtendidos.get(key);

			if((newValue != null) && (!StringUtils.isEmpty(newValue.getText()))){
				//Añadimos al array de campos el campo modificado
				campo = addCampoArrayAudit(nameFld, null, newValue.getText());
				auditCamposRegistro.add(campo);
			}
		}
	}

	private static String getStringInfoIPHost(String cadena){
		if(!StringUtils.isBlank(cadena)){
			return cadena;
		}else{
			log.warn("No se ha podido obtener la información de IP/HOST");
			return MENSAJE_NOT_FOUND_DATA_HOST_OR_IP;
		}
	}

	/**
	 * Método que añade al array pasado como parámetro la información de los campos del registro
	 * además también se añade la información del campo al XML pasado como parámetro
	 *
	 * @param infoRegistro - {@link AxSF} - Información del registro
	 * @param auditCamposRegistro - Array en el que añadimos la auditoría de los campos del registro
	 * @param elementXMLCamposRegistro - XML en el que añadimos la información de los campos del registro
	 *
	 */
	private static void auditCamposNuevoRegistro(AxSf infoRegistro,
			List<IsicresAuditoriaValorModificadoVO> auditCamposRegistro,
			Element elementXMLCamposRegistro) {

		IsicresAuditoriaValorModificadoVO campo = null;
		//Recorremos los campos del registro
		for(Iterator it = infoRegistro.getAttributesValues().keySet().iterator(); it.hasNext();){
			String key = (String) it.next();
			String newValue = infoRegistro.getAttributeValueAsString(key);

			if(!StringUtils.isEmpty(newValue)){
				//Añadimos al XML la información del campo
				elementXMLCamposRegistro.addElement(key).addText(newValue);

				//Añadimos al array de campos el campo modificado
				campo = addCampoArrayAudit(key, null, newValue);
				auditCamposRegistro.add(campo);
			}
		}
	}

	/**
	 * Método que devuelve un nuevo campo modificado a partir de los parámetros
	 *
	 * @param fldName - Nombre del campo (FLD[X])
	 * @param newValueFld - Valor del campo
	 *
	 * @return {@link IsicresAuditoriaValorModificadoVO} - Datos del campo modificado
	 */
	private static IsicresAuditoriaValorModificadoVO addCampoArrayAudit(String fldName,
			 String valueOldFld, String newValueFld) {
		IsicresAuditoriaValorModificadoVO result = new IsicresAuditoriaValorModificadoVO();

		result.setFieldName(fldName);
		if(!StringUtils.isBlank(valueOldFld)){
			result.setOldValue(valueOldFld);
		}
		if(!StringUtils.isBlank(newValueFld)){
			result.setNewValue(newValueFld);
		}

		return result;
	}

	/**
	 * Obtenemos un XML con los datos del array de interesados pasados como parámetro
	 *
	 * @param inter - Listado interesados
	 * @param root - Nodo del documento XML al que se añade el XML de interesados
	 *
	 * @return Element - Elemento con los interesados
	 */
	private static Element getXMLInteresados(List<FlushFdrInter> inter) {
		Document document = DocumentHelper.createDocument();
		Element elementXMLinteresados = document.addElement(XML_INTERESADOS);

		for(Iterator<FlushFdrInter> it = inter.iterator(); it.hasNext();){
			FlushFdrInter interesado = (FlushFdrInter) it.next();

			getXMLInteresado(elementXMLinteresados, interesado);
		}
		return elementXMLinteresados;
	}

	/**
	 * Método que añade un nodo XML de tipo Interesado con los datos del interesado
	 *
	 * @param elementXMLinteresados - Elemento XML de los interesados
	 * @param interesado - Información del interesado
	 *
	 */
	private static void getXMLInteresado(Element elementXMLinteresados,
			FlushFdrInter interesado) {
		Element elementInteresadoXML = elementXMLinteresados.addElement(XML_INTERESADO);

		elementInteresadoXML.addElement(XML_ID_INTERESADO).addText(Integer.toString(interesado.getInterId()));
		elementInteresadoXML.addElement(XML_NOMBRE_INTERESADO).addText(getStringElement(interesado.getInterName()));
		elementInteresadoXML.addElement(XML_ID_DIRECCION).addText(Integer.toString(interesado.getDomId()));
		elementInteresadoXML.addElement(XML_DIRECCION).addText(getStringElement(interesado.getDirection()));

		if(interesado.getRepresentante() != null){
			elementInteresadoXML.addElement(XML_ID_REPRESENTANTE).addText(Integer.toString(interesado.getRepresentante().getInterId()));
			elementInteresadoXML.addElement(XML_NOMBRE_REPRESENTANTE).addText(interesado.getRepresentante().getInterName());
			elementInteresadoXML.addElement(XML_ID_DIRECCION_REPRESENTANTE).addText(Integer.toString(interesado.getRepresentante().getDomId()));
			elementInteresadoXML.addElement(XML_DIRECCION_REPRESENTANTE).addText(getStringElement(interesado.getRepresentante().getDirection()));
		}
	}

	/**
	 * Método que obtiene un XML con los documentos del registro
	 * @param documentos - Colección de documentos
	 * @param root - Elemento raíz al cual se añade el XML de documentos
	 *
	 * @return Element - XML con los datos de los documentos
	 */
	public static Element getXMLDocumentos(Map documentos, Element root) {
		Element documentosXML = root.addElement(XML_DOCUMENTOS);

		for(Iterator it = documentos.keySet().iterator(); it.hasNext();){
			String key = (String) it.next();
			FlushFdrDocument documento = (FlushFdrDocument) documentos.get(key);

			getXMLDocumento(documentosXML, documento);
		}
		return documentosXML;
	}

	/**
	 * Método que genera el nodo XML Documento, con la información del documento y sus paginas
	 * @param documentosXML - Nodo del que se añadira el documento
	 * @param documento - {@link FlushFdrDocument} información del documento
	 *
	 */
	private static void getXMLDocumento(Element documentosXML, FlushFdrDocument documento) {
		Element documentoXML = documentosXML.addElement(XML_DOCUMENTO);

		documentoXML.addElement(XML_ID_DOCUMENTO).addText(Integer.toString(documento.getDocID()));
		documentoXML.addElement(XML_NOMBRE_DOCUMENTO).addText(getStringElement(documento.getDocumentName()));
		getXMLPaginasDocumento(documento, documentoXML);
	}

	/**
	 * Método que genera el nodo paginas, y asigna la información de estas
	 * @param documento - {@link FlushFdrDocument} Información del documento
	 * @param documentoXML - Nodo del que se añade la información de las paginas
	 *
	 */
	private static void getXMLPaginasDocumento(FlushFdrDocument documento, Element documentoXML) {
		Element paginasXML = documentoXML.addElement(XML_PAGINAS);
		for(Iterator itPaginas = documento.getPages().iterator(); itPaginas.hasNext();){
			FlushFdrPage pagina = (FlushFdrPage) itPaginas.next();
			getXMLPagina(pagina, paginasXML);
		}
	}

	/**
	 * Método que genera la información referente a cada pagina
	 * @param pagina - {@link FlushFdrPage} Información de la pagina
	 * @param paginasXML - nodo xml a partir del que se añadira la información de la pagina
	 */
	private static void getXMLPagina(FlushFdrPage pagina, Element paginasXML) {
		Element paginaXML = paginasXML.addElement(XML_PAGINA);
		paginaXML.addElement(XML_ID_PAGINA).addText(getStringElement(pagina.getFile().getPageID()));
		paginaXML.addElement(XML_NOMBRE_PAGINA).addText(getStringElement(pagina.getPageName()));
	}

	/**
	 * Método que devuelve una cadena inicializada si el valor pasado como parámetro en nulo o vacio
	 *
	 * @param cadena
	 *
	 * @return String
	 */
	private static String getStringElement(String cadena){
		String result = "";
		if(!StringUtils.isEmpty(cadena)){
			result = cadena;
		}

		return result;
	}

}
