package ieci.tdw.ispac.ispaclib.invesicres.registro.api;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.invesicres.InveSicresConfiguration;
import ieci.tdw.ispac.ispaclib.invesicres.registro.vo.DocumentoVO;
import ieci.tdw.ispac.ispaclib.invesicres.registro.vo.InteresadoVO;
import ieci.tdw.ispac.ispaclib.invesicres.registro.vo.RegistroSalidaVO;
import ieci.tdw.ispac.ispaclib.sicres.vo.DocumentInfo;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tecdoc.sbo.idoc.api.FolderObject;
import ieci.tecdoc.sbo.idoc.folder.base.FolderBaseDefs;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author RAULHC
 *
 */
public class RegistroSalida extends RegistroBase {
	
	
	public RegistroSalida(InvesDocConnection invesDocConnection) throws ISPACException {
		super(invesDocConnection);
	}
	
	public RegistroSalidaVO getRegistro(String numRegistro) throws ISPACException {
		String qual = "WHERE FLD" + mConfig.get(InveSicresConfiguration.ENT_NUMERO_REGISTRO) + " = '" + numRegistro + "'";
		return getRegistroQual(qual);
	}

	public RegistroSalidaVO getRegistroById(String strId) throws ISPACException {
		String qual = "WHERE FDRID = " + strId;
		return getRegistroQual(qual);
	}
	
	public RegistroSalidaVO getRegistroQual(String qual) throws ISPACException {
		
		//*** COMPROBAR CONEXION ***
		if (invesDocConnection == null && !invesDocConnection.isConnected())
			throw new ISPACException("No esta conectado.");
		//**************************
		
		RegistroSalidaVO registroVO = null;
		String valor = null;
		Object data = null;
		
		FolderObject registroFdr = invesDocConnection.LoadFolderFromQual(
				mConfig.getInt(InveSicresConfiguration.SAL_ID_ARCH), qual);
		
		if (registroFdr != null) {
			registroVO = new RegistroSalidaVO();
			try {
				//------------------------------------------
				//Datos obtenidos de la carpeta del registro
				//------------------------------------------
				
				//Identificador del Archivador al que pertenece la carpeta del Registro
				registroVO.setArchiveId(mConfig.get(InveSicresConfiguration.SAL_ID_ARCH));
				//Identificador de la Carpeta
				registroVO.setFolderId(Integer.toString(registroFdr.getId()));
				//Numero de Registro
				registroVO.setNumeroRegistro((String)registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_NUMERO_REGISTRO)));
				//Fecha de Registro
				data = registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_FECHA_REGISTRO));
				if (data != null) {
					valor = getDateFormat((Date) data);
					registroVO.setFechaRegistro(valor);
					valor = getDateTimeFormat((Date) data);
					registroVO.setFechaHoraRegistro(valor);
				}
				//Usuario que creo el registro
				registroVO.setUsuario((String)registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_USUARIO)));
				//Fecha de Trabajo
				data = registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_FECHA_TRABAJO));
				if (data != null) {
					valor = getDateFormat((Date) data);
					registroVO.setFechaTrabajo(valor);
				}
				//Identificador de la oficina de Registro
				valor = null;
				data = registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_OFICINA_REGISTRO));
				if (data != null) valor = data.toString();
				if (valor.equals(NULL_INTEGER)) valor = null;
				registroVO.setOficinaRegistroId(valor);
				//Identificador del Estado
				valor = null;
				data = registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_ESTADO));
				if (data != null) valor = data.toString();
				registroVO.setEstadoId(valor);
				//Unidad Origen del Registro
				valor = null;
				data = registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_ORIGEN));
				if (data != null) valor = data.toString();
				if (valor.equals(NULL_INTEGER)) valor = null;
				registroVO.setOrigenId(valor);
				//Identificador de la unidad Destino
				valor = null;
				data = registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_DESTINO));
				if (data != null) valor = data.toString();
				if (valor.equals(NULL_INTEGER)) valor = null;
				registroVO.setDestinoId(valor);
				//Nombre del destinatario
				registroVO.setDestinatario((String)registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_DESTINATARIO)));
				//Tipo de Transporte
				registroVO.setTipoTransporte((String)registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_TIPO_TRANSPORTE)));
				//Numero de Transporte
				registroVO.setNumeroTransporte((String)registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_NUMERO_TRANSPORTE)));
				//Identificador del Tipo de Asunto
				valor = null;
				data = registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_TIPO_ASUNTO));
				if (data != null) valor = data.toString();				
				if (valor.equals(NULL_INTEGER)) valor = null;
				registroVO.setTipoAsuntoId(valor);
				//Resumen
				registroVO.setResumen((String)registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_RESUMEN)));
				//Comentario
				registroVO.setComentario((String)registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_COMENTARIO)));
				//------------------------------------------------------------------------------------------------------
				
				//Destinatario
				registroVO.setDestinatarios(registroApi.getInteresados(
						mConfig.getInt(InveSicresConfiguration.SAL_ID_ARCH), registroFdr.getId()));	
				
				//** Obtener valores de campos con identificador **
				
				//Oficina de Registro
				String oficinaRegistro = registroApi.getScrNameFromId(mConfig.get(InveSicresConfiguration.TBL_OFIC), 
						registroVO.getOficinaRegistroId());
				String oficinaRegistroCode = registroApi.getScrCodeFromId(mConfig.get(InveSicresConfiguration.TBL_OFIC), 
						registroVO.getOficinaRegistroId());
				registroVO.setOficinaRegistroName(oficinaRegistro);
				registroVO.setOficinaRegistroCode(oficinaRegistroCode);
				//Destino
				String destino = registroApi.getScrNameFromId(mConfig.get(InveSicresConfiguration.TBL_ORGS), 
						registroVO.getDestinoId());
				String destinoCode = registroApi.getScrCodeFromId(mConfig.get(InveSicresConfiguration.TBL_ORGS), 
						registroVO.getDestinoId());
				String destinoCif = getOrgCifFromId(registroVO.getDestinoId());
				registroVO.setDestinoName(destino);
				registroVO.setDestinoCode(destinoCode);
				registroVO.setDestinoCif(destinoCif);
				//Origen
				String origen = registroApi.getScrNameFromId(mConfig.get(InveSicresConfiguration.TBL_ORGS), 
						registroVO.getOrigenId());
				String origenCode = registroApi.getScrCodeFromId(mConfig.get(InveSicresConfiguration.TBL_ORGS), 
						registroVO.getOrigenId());
				String origenCif = getOrgCifFromId(registroVO.getOrigenId());
				registroVO.setOrigenName(origen);
				registroVO.setOrigenCode(origenCode);
				registroVO.setOrigenCif(origenCif);
				//Tipo de Asunto
				String tipoAsunto = registroApi.getTipoAsuntoById(registroVO.getTipoAsuntoId());
				String tipoAsuntoCode = registroApi.getScrCodeFromId(mConfig.get(InveSicresConfiguration.TBL_CA), 
						registroVO.getTipoAsuntoId());
				registroVO.setTipoAsuntoName(tipoAsunto);
				registroVO.setTipoAsuntoCode(tipoAsuntoCode);
				
				
				//Documentos
				registroVO.setDocumentos(registroApi.getDocumentos(mConfig.get(InveSicresConfiguration.SAL_ID_ARCH), registroVO.getFolderId()));
				
			} catch (Exception e) {
				throw new ISPACException("Error al leer datos del Registro.", e);
			}
		}
		return registroVO;
	}
		
	public RegistroSalidaVO addRegistro(RegistroSalidaVO registroVO) throws ISPACException {
		
		//*** COMPROBAR CONEXION ***
		if (invesDocConnection == null && !invesDocConnection.isConnected())
			throw new ISPACException("No esta conectado.");
		//**************************
		Date actualDateTime = registroApi.getDateTime();
		Date actualDateWithoutTime = registroApi.getDate();
		
		Integer id = null;
		int oficId = 0;
		boolean hashDestino = false;
		boolean hashDestinatarios = false;
		boolean hashTipoAsunto = false;
		boolean hashResumen = false;
		boolean hashOrigen = false;
		
		LinkedList newDestinatarios = null;
		
		FolderObject registroFdr = invesDocConnection.newFolder(mConfig.getInt(InveSicresConfiguration.SAL_ID_ARCH));		
		
		try {
			boolean isSinUsuarioEnRegistro = (registroVO.getUsuario() == null || registroVO
                    .getUsuario().length() == 0);
			//Usuario
		    String usuario = registroVO.getUsuario();
		    if (isSinUsuarioEnRegistro){
		        registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_USUARIO), invesDocConnection.getIDocUserName().toUpperCase());
		    }else{
		        registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_USUARIO), usuario);
		    }
		    
		    //Oficina Registro
			id = getValueId(mConfig.get(InveSicresConfiguration.TBL_OFIC), 
					registroVO.getOficinaRegistroId(), registroVO.getOficinaRegistroCode());
			if (id == null) throw new ISPACException("Falta indicar el Identificador o el Código de la Oficina de Registro.");
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_OFICINA_REGISTRO), id);
			oficId = id.intValue();
			//Origen
			id = getValueId(mConfig.get(InveSicresConfiguration.TBL_ORGS),
					registroVO.getOrigenId(), registroVO.getOrigenCode());
			if (id == null) id = getOrgIdFromCif(registroVO.getOrigenCif());
			if (id != null) hashOrigen = true;
			//if (id == null) throw new ISPACException(ERROR_NEW, 
			//		"Falta indicar el Identificador o el Código de la Unidad Origen.");
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_ORIGEN), id);
			//Destino
			id = getValueId(mConfig.get(InveSicresConfiguration.TBL_ORGS),
					registroVO.getDestinoId(), registroVO.getDestinoCode());
			if (id == null) id = getOrgIdFromCif(registroVO.getDestinoCif());
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_DESTINO), id);
			if (id != null) hashDestino = true;
			//Destinatario
			String destinatario = registroVO.getDestinatario();
			if (destinatario != null && destinatario.length() > 0) {
				registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_DESTINATARIO), destinatario);
				hashDestinatarios = true;
			}
			//Tipo Transporte
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_TIPO_TRANSPORTE), registroVO.getTipoTransporte());
			//Numero de Transporte
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_NUMERO_TRANSPORTE), registroVO.getNumeroTransporte());
			//Tipo de Asunto
			id = getValueId(mConfig.get(InveSicresConfiguration.TBL_CA), 
					registroVO.getTipoAsuntoId(), registroVO.getTipoAsuntoCode());
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_TIPO_ASUNTO), id);
			if (id != null) hashTipoAsunto = true;
			//Resumen
			String resumen = registroVO.getResumen();
			if (resumen != null && resumen.length() > 0) {
				registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_RESUMEN), resumen);
				hashResumen = true;
			}
			
			
			//Comentario
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_COMENTARIO), registroVO.getComentario());
			
			//Añadir Destinatarios
			List destinatarios = registroVO.getDestinatarios();
			if (destinatarios != null && destinatarios.size() > 0) {
				hashDestinatarios = true;
				Iterator it = destinatarios.iterator();
				newDestinatarios = new LinkedList();
				while (it.hasNext()) {
					InteresadoVO destinatarioVO = (InteresadoVO)it.next();
					InteresadoVO retDestinatarioVO = registroApi.insertInteresado(
							mConfig.getInt(InveSicresConfiguration.SAL_ID_ARCH),	registroFdr.getId(), destinatarioVO);
					newDestinatarios.add(retDestinatarioVO);
					
					if ((destinatario == null || destinatario.length() == 0) && retDestinatarioVO.getOrden().equals("1")) {
						destinatario = retDestinatarioVO.getNombre();
						registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_DESTINATARIO), destinatario);
					}
				}
			}
			else {
				
				if (destinatario != null && destinatario.length() > 0) {
					newDestinatarios = new LinkedList();
					InteresadoVO destinatarioVO = new InteresadoVO();
					//destinatarioVO.setIdTercero("0");
					//destinatarioVO.setIdDireccion("0");
					destinatarioVO.setNombre(destinatario);
					InteresadoVO retDestinatarioVO = registroApi.insertInteresado(
							mConfig.getInt(InveSicresConfiguration.SAL_ID_ARCH), registroFdr.getId(), destinatarioVO);
					newDestinatarios.add(retDestinatarioVO);
				}
			}
			
			
			//Añadir Documentos
			List documentos = registroVO.getDocumentos();
			if (documentos != null && documentos.size()  > 0){
				for (Iterator iterator = documentos.iterator(); iterator.hasNext();) {
					DocumentInfo docInfo = (DocumentInfo) iterator.next();
					registroFdr.addDocument(docInfo.getName(), FolderBaseDefs.CLF_ROOT_ID, StringUtils.substring(docInfo.getName() , StringUtils.lastIndexOf(docInfo.getName(), '.')+1)  , new ByteArrayInputStream(docInfo.getData()));
				}
				
				
			}
			
			
			//Estado
			if (hashOrigen && (hashDestino || hashDestinatarios) && (hashTipoAsunto || hashResumen)) {
				registroVO.setEstadoId("0"); //Completo
			} else {
				registroVO.setEstadoId("1"); //Incompleto
			}
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_ESTADO), new Integer(registroVO.getEstadoId()));
			
			//** Iniciar Transacion **
			invesDocConnection.StartTrans();
			//************************
			
			String numRegistro = null;
			//crear numero de registro si no le viene ninguno
			if (registroVO.getNumeroRegistro() != null
                    && registroVO.getNumeroRegistro().length() > 0) {

			    RegistroSalidaVO registroBuscado = getRegistro(registroVO.getNumeroRegistro());
			    if (registroBuscado==null)
			        numRegistro = registroVO.getNumeroRegistro();
			    else
			        throw new ISPACException("Error al crear el registro de entrada: registro duplicado");
			    
			} else{
				//numRegistro = registroApi.getNumRegistroCentral(mConfig.getInt(InveSicresConfiguration.SAL_ID_ARCH));
				//numRegistro = registroApi.getNumRegistro(oficId, mConfig.getInt(InveSicresConfiguration.SAL_ID_ARCH));
				numRegistro = registroApi.getNumRegistro(oficId, mConfig.getInt(InveSicresConfiguration.SAL_ID_ARCH), 2);
			}
			
			//Establecer Numero de Registro
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_NUMERO_REGISTRO), numRegistro);

			//Establecer Fecha de Registro
			Date fechaRegistro = null;
			if (registroVO.getFechaRegistro() != null
                    && registroVO.getFechaRegistro().length() > 0) {

			    fechaRegistro = parseDate(registroVO.getFechaRegistro());

			}else if (registroVO.getFechaHoraRegistro() != null
                    && registroVO.getFechaHoraRegistro().length() > 0) {

			    fechaRegistro = parseDate(registroVO.getFechaHoraRegistro());
 
			}else{
			    fechaRegistro = actualDateTime;
			}
			
			//registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_FECHA_REGISTRO), actualDate);
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_FECHA_REGISTRO), fechaRegistro);
			
			//Establecer Fecha de Trabajo
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_FECHA_TRABAJO), actualDateWithoutTime);
			
			//Guardar Registro
			int archId = TypeConverter.parseInt(registroVO.getArchiveId(), mConfig.getInt(InveSicresConfiguration.SAL_ID_ARCH));
			invesDocConnection.createFolder(archId, registroFdr);
			
			//Retorno de valores del nuevo registro
			registroVO.setNumeroRegistro(numRegistro);
			
			//fecha registro
			//registroVO.setFechaHoraRegistro(getDateTimeFormat(actualDate));
			//registroVO.setFechaRegistro(getDateFormat(actualDate));
			registroVO.setFechaHoraRegistro(getDateTimeFormat(fechaRegistro));
			registroVO.setFechaRegistro(getDateFormat(fechaRegistro));
			
			registroVO.setFechaTrabajo(getDateFormat(actualDateTime));

			if (isSinUsuarioEnRegistro){
		        registroVO.setUsuario(invesDocConnection.getIDocUserName().toUpperCase());
		    }

		    
			registroVO.setDestinatarios(newDestinatarios);
			registroVO.setArchiveId(String.valueOf(archId));
			registroVO.setFolderId(Integer.toString(registroFdr.getId()));
					
			//** Finalizar Transacion **
			invesDocConnection.CommitTrans();
			//**************************
		}
		catch (Exception e) {
			
			invesDocConnection.RollbackTrans();
            throw new ISPACException("Error al crear el registro de salida.", e);
		}
		
		return registroVO;
	}
	
	public DocumentoVO addDocument(String folderId, DocumentoVO documentoVO) throws ISPACException {
		return registroApi.addDocumento(mConfig.get(InveSicresConfiguration.SAL_ID_ARCH), folderId, documentoVO);
	}
}
