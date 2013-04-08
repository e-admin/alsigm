package es.ieci.tecdoc.isicres.deprecated.ws.service.impl.mapper.input;

import ieci.tecdoc.core.base64.Base64Util;
import ieci.tecdoc.sgm.registropresencial.ws.server.Document;
import ieci.tecdoc.sgm.registropresencial.ws.server.FieldInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.Folder;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSAddField;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSParamDocument;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSAddField;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamDocument;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamOutputRegisterEx;

public class FolderToWSParamOutputRegisterExMapper implements Mapper {
	
	private static final Logger logger = Logger.getLogger(FolderToWSParamInputRegisterExMapper.class);
	
	
	private static final String ORIGEN_FIELD_ID="7";
	private static final String DESTINO_FIELD_ID="8";

	private static final String TIPO_TRANSPORTE_FIELD_ID="10";
	private static final String NUMERO_TRANSPORTE_FIELD_ID="11";
	private static final String TIPO_ASUNTO_FIELD_ID="12";


	private static final int MAX_FIELD_ID = 14;
	
	
	public Object map(Object obj) {
	
		Folder folder = (Folder)obj;
		WSParamOutputRegisterEx register = new WSParamOutputRegisterEx();
		
		List<FieldInfo> listaFields = folder.getFields().getFields().getItem();
		for (FieldInfo fieldInfo : listaFields) {
			String fieldId = fieldInfo.getFieldId();
			String fieldValue = fieldInfo.getValue();
			if(ORIGEN_FIELD_ID.equals(fieldId))
			{
				register.setSender(fieldValue);
			}
			else if(DESTINO_FIELD_ID.equals(fieldId))
			{
				register.setDestination(fieldValue);
			}
			
			else if(TIPO_TRANSPORTE_FIELD_ID.equals(fieldId))
			{
				register.setTransportType(fieldValue);
			}
			else if(NUMERO_TRANSPORTE_FIELD_ID.equals(fieldId))
			{
				register.setTransportNumber(fieldValue);
			}
			else if(TIPO_ASUNTO_FIELD_ID.equals(fieldId))
			{
				register.setMatterType(fieldValue);
			}
			else if(Integer.valueOf(fieldId).intValue()>MAX_FIELD_ID)
			{
				if(register.getAddFields()==null)
				{
					register.setAddFields(new ArrayOfWSAddField());
				}
				WSAddField field = new WSAddField();
				field.setFieldId(Integer.valueOf(fieldId).intValue());
				field.setValue(fieldValue);
				register.getAddFields().getWSAddField().add(field);
			}
		}
		
		if(folder.getDocumentos()!=null && folder.getDocumentos().getDocuments()!=null 
				&& CollectionUtils.isNotEmpty(folder.getDocumentos().getDocuments().getItem()))
		{
			List<Document> documentos = folder.getDocumentos().getDocuments().getItem();
			
			ArrayOfWSParamDocument documentsWS = new ArrayOfWSParamDocument();
			
			for (Document document : documentos) {
				
				WSParamDocument documentWS = new WSParamDocument();
				if(StringUtils.isNotEmpty(document.getDocID()))
				{
					documentWS.setDocumentLocation(document.getDocID());
				}
				else
				{
					try{
						documentWS.setDocumentContent(Base64Util.decode(document.getDocumentContentB64()));
					}
					catch (Exception e) {
						logger.error("No se puede decodificar el contenido del fichero "+document.getDocumentName(), e);
						throw new RuntimeException("No se puede decodificar el contenido del fichero");
					}
				}
				
				if(StringUtils.isEmpty(document.getDocumentName()) || document.getFileName()==null || document.getExtension()==null)
				{
					throw new RuntimeException("Los documentos no tienen nombre o extension.");
				}
				documentWS.setDocumentName(document.getDocumentName());
				String completeFinalName=document.getFileName();
				if(!completeFinalName.endsWith(document.getExtension()))
				{
					if(!document.getExtension().startsWith("."))
					{
						completeFinalName+=".";
					}
					completeFinalName+=document.getExtension();
				}
				documentWS.setFileName(completeFinalName);
				documentsWS.getWSParamDocument().add(documentWS);
			}
			
			register.setDocuments(documentsWS);
		}
		return register;
	}

}
