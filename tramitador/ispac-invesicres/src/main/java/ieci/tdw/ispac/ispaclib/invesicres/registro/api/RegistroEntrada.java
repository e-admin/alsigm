package ieci.tdw.ispac.ispaclib.invesicres.registro.api;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.invesicres.InveSicresConfiguration;
import ieci.tdw.ispac.ispaclib.invesicres.registro.vo.DocumentoVO;
import ieci.tdw.ispac.ispaclib.invesicres.registro.vo.InteresadoVO;
import ieci.tdw.ispac.ispaclib.invesicres.registro.vo.RegistroEntradaVO;
import ieci.tecdoc.sbo.idoc.api.FolderObject;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author RAULHC
 *
 */
public class RegistroEntrada extends RegistroBase {
	
	
	public RegistroEntrada(InvesDocConnection invesDocConnection) throws ISPACException {
		super(invesDocConnection);
	}
	
	
	
	public RegistroEntradaVO getRegistro(String numRegistro) throws ISPACException {
		String qual = "WHERE FLD" + mConfig.get(InveSicresConfiguration.ENT_NUMERO_REGISTRO) + " = '" + numRegistro + "'";
		return getRegistroQual(qual);
	}

	public RegistroEntradaVO getRegistroById(String strId) throws ISPACException {
		String qual = "WHERE FDRID = " + strId;
		return getRegistroQual(qual);
	}
	
	
	public RegistroEntradaVO getRegistroQual(String qual) throws ISPACException {

		//*** COMPROBAR CONEXION ***
		if (invesDocConnection == null && !invesDocConnection.isConnected())
			throw new ISPACException("No esta conectado.");
		//**************************
		
		RegistroEntradaVO registroVO = null;
		String valor = null;
		Object data = null;

		FolderObject registroFdr = invesDocConnection.LoadFolderFromQual(
				mConfig.getInt(InveSicresConfiguration.ENT_ID_ARCH), qual);
		
		if (registroFdr != null) {
			registroVO = new RegistroEntradaVO();
			try {
				
				//------------------------------------------
				//Datos obtenidos de la carpeta del registro
				//------------------------------------------
				
				//Identificador del Archivador al que pertenece la carpeta del Registro
				registroVO.setArchiveId(mConfig.get(InveSicresConfiguration.ENT_ID_ARCH));
				//Identificador de la Carpeta
				registroVO.setFolderId(Integer.toString(registroFdr.getId()));
				//Numero de Registro
				registroVO.setNumeroRegistro((String)registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_NUMERO_REGISTRO)));
				//Fecha de Registro
				data = registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_FECHA_REGISTRO));
				if (data != null) {
					valor = getDateFormat((Date) data);
					registroVO.setFechaRegistro(valor);
					valor = getDateTimeFormat((Date) data);
					registroVO.setFechaHoraRegistro(valor);
				}
				//Usuario que creo el registro
				registroVO.setUsuario((String)registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_USUARIO)));
				//Fecha de Trabajo
				data = registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_FECHA_TRABAJO));
				valor = null;
				if (data != null) valor = getDateFormat((Date) data);
				registroVO.setFechaTrabajo(valor);
				//Identificador de la Oficina de Registro
				valor = null;
				data = registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_OFICINA_REGISTRO));
				if (data != null) valor = data.toString();
				if (valor.equals(NULL_INTEGER)) valor = null;
				registroVO.setOficinaRegistroId(valor);
				//Identificador del Estado
				valor = null;
				data = registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_ESTADO));
				if (data != null) valor = data.toString();
				registroVO.setIdEstado(valor);
				//Unidad Origen del Registro
				valor = null;
				data = registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_ORIGEN));
				if (data != null) valor = data.toString();
				if (valor.equals(NULL_INTEGER)) valor = null;
				registroVO.setOrigenId(valor);
				//Identificador de la unidad Destino
				valor = null;
				data = registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_DESTINO));
				if (data != null) valor = data.toString();
				if (valor.equals(NULL_INTEGER)) valor = null;
				registroVO.setDestinoId(valor);
				//Nombre del Remitente
				registroVO.setRemitente((String)registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_REMITENTE)));
				//Numero de Registro Original
				registroVO.setNumRegistroOriginal((String)registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_NUMERO_REGISTRO_ORIGINAL)));
				//Tipo del Registro Original
				valor = null;
				data = registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_TIPO_REGISTRO_ORIGINAL));
				if (data != null) valor = data.toString();
				if (valor.equals(NULL_INTEGER)) valor = null;
				registroVO.setTipoRegistroOriginalId(valor);
				//Fecha del Registro Original
				data = registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_FECHA_REGISTRO_ORIGINAL));
				valor = null;
				if (data != null) valor = getDateTimeFormat((Date) data);
				registroVO.setFechaRegistroOriginal(valor);
				//Identificador del la Unidad Administrativa del Registro Original
				valor = null;
				data = registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_REGISTRO_ORIGINAL));
				if (data != null) valor = data.toString();
				if (valor.equals(NULL_INTEGER)) valor = null;
				registroVO.setRegistroOriginalId(valor);
				//Tipo de Transporte
				registroVO.setTipoTransporte((String)registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_TIPO_TRANSPORTE)));
				//Numero de Transporte
				registroVO.setNumeroTransporte((String)registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_NUMERO_TRANSPORTE)));
				//Identificador del Tipo de Asunto
				valor = null;
				data = registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_TIPO_ASUNTO));
				if (data != null) valor = data.toString();				
				if (valor.equals(NULL_INTEGER)) valor = null;
				registroVO.setTipoAsuntoId(valor);
				//Resumen
				registroVO.setResumen((String)registroFdr.getFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_RESUMEN)));
				//------------------------------------------------------------------------------------------------------
				
				//Remitentes
				registroVO.setRemitentes(registroApi.getInteresados(mConfig.getInt(InveSicresConfiguration.ENT_ID_ARCH), registroFdr.getId()));
				
				//**Obtener valores de campos con identificador**
				
				//Oficina de registro
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
				//Registro Original
				String registroOriginal = registroApi.getScrNameFromId(mConfig.get(InveSicresConfiguration.TBL_OFIC),
						registroVO.getRegistroOriginalId());
				String registroOriginalCode = registroApi.getScrCodeFromId(mConfig.get(InveSicresConfiguration.TBL_OFIC), 
						registroVO.getRegistroOriginalId());
				String registroOriginalCif = getOrgCifFromId(registroVO.getRegistroOriginalId());
				registroVO.setRegistroOriginalName(registroOriginal);
				registroVO.setRegistroOriginalCode(registroOriginalCode);
				registroVO.setRegistroOriginalCif(registroOriginalCif);
				
				
				//Documentos
				registroVO.setDocumentos(registroApi.getDocumentos(mConfig.get(InveSicresConfiguration.ENT_ID_ARCH), registroVO.getFolderId()));
				
			} catch (Exception e) {
				throw new ISPACException("Error al leer datos del Registro.", e);
			}
		}
		
		return registroVO;
	}
	
	public RegistroEntradaVO addRegistro(RegistroEntradaVO registroVO) throws ISPACException {
		//*** COMPROBAR CONEXION ***
		if (invesDocConnection == null && !invesDocConnection.isConnected())
			throw new ISPACException("No esta conectado.");
		//**************************
		Date actualDateTime = registroApi.getDateTime();
		Date actualDateWithoutTime = registroApi.getDate();
		
		Integer id = null;
		int oficId = 0;
		boolean hashOrigen = false;
		boolean hashRemitente = false;
		boolean hashDestino = false;
		boolean hashTipoAsunto = false;
		boolean hashResumen = false;
		
		LinkedList newRemitentes = null;
		
		FolderObject registroFdr = invesDocConnection.newFolder(mConfig.getInt(InveSicresConfiguration.ENT_ID_ARCH));

		try {
			//Usuario
//			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_USUARIO),
//                    invesDocConnection.getIDocUserName().toUpperCase());
			boolean isSinUsuarioEnRegistro = (registroVO.getUsuario() == null || registroVO
                    .getUsuario().length() == 0);
		    String usuario = registroVO.getUsuario();
		    if (isSinUsuarioEnRegistro){
		        registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_USUARIO), invesDocConnection.getIDocUserName().toUpperCase());
		    }else{
		        registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_USUARIO), usuario);
		    }
			
			//Oficina Registro
			id = getValueId(mConfig.get(InveSicresConfiguration.TBL_OFIC), 
					registroVO.getOficinaRegistroId(), registroVO.getOficinaRegistroCode());
			if (id == null) throw new ISPACException("Falta indicar el Identificador o el Código de la Oficina de Registro.");
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_OFICINA_REGISTRO), id);
			oficId = id.intValue();
			//Origen
			id = getValueId(mConfig.get(InveSicresConfiguration.TBL_ORGS),
					registroVO.getOrigenId(), registroVO.getOrigenCode());
			if (id == null) id = getOrgIdFromCif(registroVO.getOrigenCif());
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_ORIGEN), id);
			if (id != null) hashOrigen = true;
			//Destino
			id = getValueId(mConfig.get(InveSicresConfiguration.TBL_ORGS),
					registroVO.getDestinoId(), registroVO.getDestinoCode());
			if (id == null) id = getOrgIdFromCif(registroVO.getDestinoCif());
			if (id != null) hashDestino = true;
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_DESTINO), id);
			//Remitente
			String remitente = registroVO.getRemitente();
			if (remitente != null && remitente.length() > 0) {
				registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_REMITENTE), remitente);
				hashRemitente = true;
			}
			//Numero de Registro Original
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_NUMERO_REGISTRO_ORIGINAL),
					registroVO.getNumRegistroOriginal());
			//Tipo de Registro Original
			String strId = registroVO.getTipoRegistroOriginalId();
			if (strId != null && strId.length() > 0)
				id = new Integer(strId);
			else
				id = null;
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_TIPO_REGISTRO_ORIGINAL), id);

			//Fecha de Registro Original
			Date fechaRegistroOriginal = parseDate(registroVO.getFechaRegistroOriginal());
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_FECHA_REGISTRO_ORIGINAL), 
					fechaRegistroOriginal);
			
			//Registro Original
			id = getValueId(mConfig.get(InveSicresConfiguration.TBL_ORGS),
					registroVO.getRegistroOriginalId(), registroVO.getRegistroOriginalCode());
			if (id == null) id = getOrgIdFromCif(registroVO.getRegistroOriginalCif());
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_REGISTRO_ORIGINAL), id);
			//Tipo Transporte
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_TIPO_TRANSPORTE), 
					registroVO.getTipoTransporte());
			//Numero de Transporte
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_NUMERO_TRANSPORTE), 
					registroVO.getNumeroTransporte());
			//Tipo de Asunto
			id = getValueId(mConfig.get(InveSicresConfiguration.TBL_CA), 
					registroVO.getTipoAsuntoId(), registroVO.getTipoAsuntoCode());
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_TIPO_ASUNTO), id);
			if (id != null) hashTipoAsunto = true;
			//Resumen
			String resumen = registroVO.getResumen();
			if (resumen != null && resumen.length() > 0) {
				registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_RESUMEN), resumen);
				hashResumen = true;
			}

			//Añadir Remitentes
			LinkedList remitentes = registroVO.getRemitentes();
			if (remitentes != null && remitentes.size() > 0) {
				hashRemitente = true;
				Iterator it = remitentes.iterator();
				newRemitentes = new LinkedList();
				while (it.hasNext()) {
					InteresadoVO interesadoVO = (InteresadoVO)it.next();
					InteresadoVO retInteresadoVO = registroApi.insertInteresado(
							mConfig.getInt(InveSicresConfiguration.ENT_ID_ARCH),	registroFdr.getId(), interesadoVO);
					newRemitentes.add(retInteresadoVO);
					if (remitente == null || remitente.length() > 0 ) {
						remitente = retInteresadoVO.getNombre();
						registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.SAL_DESTINATARIO), remitente);
					}
				}
			} else {
				if (remitente != null && remitente.length() > 0) {
					newRemitentes = new LinkedList();
					InteresadoVO interesadoVO = new InteresadoVO();
					interesadoVO.setIdTercero("0");
					interesadoVO.setIdDireccion("0");
					interesadoVO.setNombre(remitente);
					InteresadoVO retDestinatarioVO = registroApi.insertInteresado(
							mConfig.getInt(InveSicresConfiguration.SAL_ID_ARCH), registroFdr.getId(), interesadoVO);
					newRemitentes.add(retDestinatarioVO);
				}
			}
			
			//Estado
			if (hashDestino && (hashOrigen || hashRemitente) && (hashTipoAsunto || hashResumen)) {
				registroVO.setEstadoId("0"); //Completo
			} else {
				registroVO.setEstadoId("1"); //Incompleto
			}
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_ESTADO), new Integer(registroVO.getEstadoId()));
			
			//** Iniciar Transacion **
			invesDocConnection.StartTrans();
			//************************
			String numRegistro = null;

			//crear numero de registro si no le viene ninguno
			if (registroVO.getNumeroRegistro() != null
                    && registroVO.getNumeroRegistro().length() > 0) {

			    RegistroEntradaVO registroBuscado = getRegistro(registroVO.getNumeroRegistro());
			    if (registroBuscado==null)
			        numRegistro = registroVO.getNumeroRegistro();

			    else
			        throw new ISPACException("Error al crear el registro de entrada: registro duplicado");
			    
			}else{
				//Asignar numero de registro automatico
//				numRegistro = registroApi.getNumRegistroCentral(mConfig.getInt(InveSicresConfiguration.ENT_ID_ARCH));
			    //Crear numero de Registro
			     //numRegistro = registroApi.getNumRegistro(oficId, mConfig.getInt(InveSicresConfiguration.ENT_ID_ARCH));
				numRegistro = registroApi.getNumRegistro(oficId, mConfig.getInt(InveSicresConfiguration.ENT_ID_ARCH), 1);

			}
			
			
			//Establecer Numero de Registro
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_NUMERO_REGISTRO), numRegistro);

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
		    
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_FECHA_REGISTRO), fechaRegistro);
			
			//Establecer Fecha de Trabajo
			registroFdr.setFieldValue(mConfig.getInt(InveSicresConfiguration.ENT_FECHA_TRABAJO), actualDateWithoutTime);
			
			//Guardar Registro
			invesDocConnection.createFolder(mConfig.getInt(InveSicresConfiguration.ENT_ID_ARCH), registroFdr);
			
			//Retorno de valores del nuevo registro
			registroVO.setNumeroRegistro(numRegistro);
			
//			registroVO.setFechaHoraRegistro(getDateTimeFormat(actualDate));
//			registroVO.setFechaRegistro(getDateFormat(actualDate));
//			registroVO.setFechaTrabajo(getDateFormat(actualDate));
			registroVO.setFechaHoraRegistro(getDateTimeFormat(fechaRegistro));
			registroVO.setFechaRegistro(getDateFormat(fechaRegistro));
			registroVO.setFechaTrabajo(getDateFormat(actualDateTime));
			
		    if (isSinUsuarioEnRegistro){
		        registroVO.setUsuario(invesDocConnection.getIDocUserName().toUpperCase());
		    }
			
			registroVO.setRemitentes(newRemitentes);
			registroVO.setArchiveId(mConfig.get(InveSicresConfiguration.ENT_ID_ARCH));
			registroVO.setFolderId(Integer.toString(registroFdr.getId()));
			
			
			//** Finalizar Transacion **
			invesDocConnection.CommitTrans();
			//**************************
		} catch (ISPACException e) {
			invesDocConnection.RollbackTrans();
			throw e;
		} catch (Exception e) {
			invesDocConnection.RollbackTrans();
            throw new ISPACException("Error al crear el registro de entrada.", e);
		}
		
		return registroVO;
	}

	public DocumentoVO addDocument(String folderId, DocumentoVO documentoVO) throws ISPACException {
		return registroApi.addDocumento(mConfig.get(InveSicresConfiguration.ENT_ID_ARCH), folderId, documentoVO);
	}
	
}
