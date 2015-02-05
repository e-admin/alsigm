package ieci.tdw.ispac.ispacpublicador.business.action.mensajesCortos;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.expedients.adapter.XMLFacade;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;
import ieci.tdw.ispac.ispacpublicador.business.action.SigemBaseAction;
import ieci.tdw.ispac.ispacpublicador.business.action.mensajesCortos.vo.DestinatarioVO;
import ieci.tdw.ispac.ispacpublicador.business.attribute.AttributeContext;
import ieci.tecdoc.sgm.core.exception.SigemException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

public abstract class MensajesCortosAction extends SigemBaseAction {
	public static final String VAR_TYPE_XPATH               		 = "xpath";
    public static final String VAR_TYPE_VAR             			 = "var";
	public static final String VAR_TYPE_SEPARATOR           		 = ":";
	
	public static final String ATR_DESTINATARIOS					="DESTINATARIOS";
	public static final String ATR_MENSAJE							="MENSAJE";
	public static final String ATR_EMISOR							="EMISOR";
	
	public static final String PROP_NOMBRE 							 ="NOMBRE";
	public static final String PROP_NIF								 ="NIF";
	public static final String PROP_NUM_EXP							 ="NUM_EXP";
	public static final String PROP_COD_PROCEDIMIENTO				 ="COD_PROCEDIMIENTO";
	public static final String PROP_NOMBRE_PROCEDIMIENTO			 ="NOMBRE_PROCEDIMIENTO";
	
	public static final String PROP_NOMBRE_REPLACE					 ="${var:NOMBRE}";
	public static final String PROP_NIF_REPLACE						 ="${var:NIF}";
	public static final String PROP_NUM_EXP_REPLACE					 ="${var:NUM_EXP}";
	public static final String PROP_COD_PROCEDIMIENTO_REPLACE 		 ="${var:COD_PROCEDIMIENTO}";
	public static final String PROP_NOMBRE_PROCEDIMIENTO_REPLACE	 ="${var:NOMBRE_PROCEDIMIENTO}";
	
	
	public static final int ENVIO_SMS								 =1;
	public static final int ENVIO_EMAIL								 =2;
	
	public static final int TEXTO_ASUNTO							 =1;
	public static final int TEXTO_CONTENIDO							 =2;
	
	
	//Patron ${lo que sea }
	 public static final String FIELD_VARIABLE_PATTERN       = "\\x24\\x7b[^\\x7d]*\\x7d";
    
    /** Contexto del cliente. */
    public ClientContext clientContext = null;
	protected XMLFacade xmlExp;
	
    protected String VALUE_NUMEXP="";
    protected String VALUE_COD_PROCEDIMIENTO="";
    protected String VALUE_NOMBRRE_PROCEDIMIENTO="";
    
public String getXmlExpediente(String numExp) throws ISPACException {
		
		VALUE_NUMEXP=numExp;
		
		// API de entidades
		IEntitiesAPI entitiesAPI = clientContext.getAPI().getEntitiesAPI();
		IItem procedimiento=clientContext.getAPI().getProcess(numExp);
							
		
		int idProcedimiento=procedimiento.getInt("ID_PCD");
		
		VALUE_COD_PROCEDIMIENTO=idProcedimiento+"";
		
		//Obtiene la colecciones de entidades que participan en el procedimiento
		IItemCollection entities= entitiesAPI.getProcedureEntities(idProcedimiento);

		List registrosExpediente=new ArrayList();
		Iterator itr = entities.iterator();
		String xml="<EXPEDIENTE>";
		while(itr.hasNext()){
			//Obtiene una colección con los registros de una entidad de un expediente concreto,
		    // a partir del identificador de la entidad y el número de expediente.
			IItem entidad= (IItem) itr.next();
			IItem infoEntidad=entitiesAPI.getCatalogEntity(entidad.getInt("ID_ENT"));
			
			xml+="<"+infoEntidad.getString("NOMBRE")+">";
			IItemCollection registros=entitiesAPI.getEntities(entidad.getInt("ID_ENT"), numExp);
			Iterator itrRegistros=registros.iterator();
			while(itrRegistros.hasNext()){
				EntityDAO regEntity= (EntityDAO) itrRegistros.next();
				xml+="<REGISTRO ID='"+regEntity.getKeyInt()+"'>";
				xml+=toXml(regEntity);
				xml+="</REGISTRO>";
				
			}
			
			xml+="</"+infoEntidad.getString("NOMBRE")+">";
		}

		xml+="</EXPEDIENTE>";
		
		
		
		return xml;
    }
	
	/**
	 * 
	 * @param entity registro de la entidad con todas sus propiedades y valores
	 * @return Un string con tantas propiedades como tenga el registro, atentiendo cada una de esas propiedades a esta patrón
	 *  <nombre_propiedad>valor</nombre_propiedad>
	 * @throws ISPACException
	 */
	String toXml(EntityDAO entity) throws ISPACException{

		ieci.tdw.ispac.api.item.Properties properties=entity.getProperties();
		String sXml="";
		Iterator iterator = properties.iterator();

		while (iterator.hasNext())
		{
			Property property = (Property) iterator.next();
			sXml+="<"+property.getName()+">";
			sXml+=entity.get(property.getName());
			sXml+="</"+property.getName()+">";
		}

		return  sXml.toString();
	
		
	}
	
	/**
	 * 
	 * @param texto Texto a enviar con los xpath y var aún por reemplazar
	 * @param destinatarios Lista de destinatarios
	 */
	public void getTexto( String texto, List destinatarios, int typeText){
		List propiedades= new ArrayList();
		 if (texto != null)
	        {
			 	StringBuffer value= new StringBuffer();
	            Pattern patt = Pattern.compile(FIELD_VARIABLE_PATTERN);
	            Matcher m = patt.matcher(texto);
	            
	            String tipo = null;
	            String paramName = null;
	          
	            while (m.find()) 
	            {
		            String variable= m.group(0);
		            String valor="";
		            // Definir el tipo y nombre del parámetro
		            int colonIndex = variable.indexOf(VAR_TYPE_SEPARATOR);
		            if (colonIndex > 0)
		            {
		                tipo = variable.substring(2, colonIndex);
		                paramName = variable.substring(colonIndex+1, variable.length()-1);
		              
		                colonIndex = paramName.indexOf(VAR_TYPE_SEPARATOR);
		                if (colonIndex > 0)
		                {
		                	paramName = paramName.substring(0, colonIndex);
		                }
		                
		                if(VAR_TYPE_XPATH.equalsIgnoreCase(tipo)){
		                	valor = xmlExp.get(paramName);
		                	 m.appendReplacement(value, valor);
		                	
		                }
		                else if(VAR_TYPE_VAR.equalsIgnoreCase(tipo)){
		                	//Solo se tratan dos propiedades de los destintarios el nombre y el cif
		                	if(PROP_NOMBRE.equalsIgnoreCase(paramName)){
		                		propiedades.add(PROP_NOMBRE);
		                	}
		                	else if(PROP_NIF.equalsIgnoreCase(paramName)){
		                		propiedades.add(PROP_NIF);
		                	}
		                	else if(PROP_COD_PROCEDIMIENTO.equalsIgnoreCase(paramName)){
		                		
		                		m.appendReplacement(value, VALUE_COD_PROCEDIMIENTO);
		                	}
		                	else if(PROP_NOMBRE_PROCEDIMIENTO.equalsIgnoreCase(paramName)){
		                		
		                		m.appendReplacement(value, VALUE_NOMBRRE_PROCEDIMIENTO);
		                	}
		                	else if(PROP_NUM_EXP.equalsIgnoreCase(paramName)){
		                		
		                		m.appendReplacement(value, VALUE_NUMEXP);
		                	}
		                	
		                }
		               
		            }  
	            }
	  
	            //Guardo el mensaje en cada destinatario traduciendo las propiedades por su valor en el caso de que existieran propiedades.
	            
	            for(int i=0; i<destinatarios.size(); i++){
	            	
	            	DestinatarioVO dest= (DestinatarioVO) destinatarios.get(i);
	            	
	            	StringBuffer str= new StringBuffer();
	            	str.append(value.toString());
	            	if(propiedades.contains(PROP_NOMBRE)){
	            		int index=value.indexOf(PROP_NOMBRE_REPLACE);
	            		str.replace(index, index+PROP_NOMBRE_REPLACE.length(), dest.getNombre());
	            	}
	            	
	            	if(propiedades.contains(PROP_NIF)){
	            		int index=value.indexOf(PROP_NIF_REPLACE);
	            		str.replace(index, index+PROP_NIF_REPLACE.length(), dest.getNif());
	            	}
	            	if(TEXTO_ASUNTO==typeText){
	            		dest.setAsunto(str.toString());
	            	}
	            	else if(TEXTO_CONTENIDO==typeText){
	            		dest.setTexto(str.toString());
	            	}
	            	destinatarios.set(i, dest);
	            	
	            }
	  
	            
	        }
	
	}
	
	/**
	 * 
	 * @param type tipo de interviniente 
	 * @param destinatarios Lista con la información de los destinatarios
	 * @param typeEnvio Indica si es un envio de sms o de emails
	 */
	public void getInfoIntervinientes( String type, List destinatarios, int typeEnvio){
		
		NodeIterator intervinientesNodeIt = xmlExp
		.getNodeIterator("/EXPEDIENTE/SPAC_DT_INTERVINIENTES/REGISTRO");
		
		boolean hayDestinatariosYTypeTodos=false;
		if("*".equalsIgnoreCase(type) && destinatarios.size()>0){
			hayDestinatariosYTypeTodos=true;
		}
		for (Node intervinienteNode = intervinientesNodeIt.nextNode(); intervinienteNode != null; intervinienteNode = intervinientesNodeIt
		.nextNode()) {
			
			
			if("*".equalsIgnoreCase(type)  || type.equalsIgnoreCase(XmlFacade.get(intervinienteNode, "ROL"))){
				String movil = XmlFacade.get(intervinienteNode, "TFNO_MOVIL");
				String email= XmlFacade.get(intervinienteNode, "DIRECCIONTELEMATICA");
				
				if( (!"null".equalsIgnoreCase(movil) && typeEnvio==ENVIO_SMS )||(!"null".equalsIgnoreCase(email) && typeEnvio==ENVIO_EMAIL)){
					
					DestinatarioVO dest= new DestinatarioVO(movil);
					dest.setMail(email);
					dest.setNif(XmlFacade.get(intervinienteNode, "NDOC"));
					dest.setNombre(XmlFacade.get(intervinienteNode, "NOMBRE"));
					if(hayDestinatariosYTypeTodos && !destinatarios.contains(dest)){
						destinatarios.add(dest);
					}
					else{
						destinatarios.add(dest);
					}
				}
			}
		
		
		}
		
	}
	
	public abstract void realizarEnvio(AttributeContext actx, List destinatarios) throws SigemException;
	
	/**
	 * Busca a los destinatarios , compone el mensaje y realiza el envío
	 * 
	 * @param xmlExpediente String con la información del expediente
	 * @param actx Contexto de atributos
	 * @param typeEnvio Indica si es un envio de sms o de email
	 * @throws SigemException
	 */
	public void componerEnviarSms(String xmlExpediente, AttributeContext actx, int typeEnvio) throws SigemException{
	

		String destinatarios=actx.getAttribute(ATR_DESTINATARIOS);
		String mensaje=actx.getAttribute(ATR_MENSAJE);
		
		destinatarios=StringUtils.unescapeXml(destinatarios);
		XmlFacade xml = new XmlFacade(destinatarios);
		xmlExpediente=StringUtils.unescapeXml(xmlExpediente);
		xmlExp= new XMLFacade(xmlExpediente);
		mensaje=StringUtils.unescapeXml(mensaje);
		XMLFacade xmlMensaje= new XMLFacade(mensaje);;
		
		VALUE_NOMBRRE_PROCEDIMIENTO=xmlExp.get("EXPEDIENTE/SPAC_EXPEDIENTES/REGISTRO/NOMBREPROCEDIMIENTO");
		
		List destinatariosVo=new ArrayList();
		//Direcciones insertardas en el xml
		NodeIterator addrNodeIt = xml
		.getNodeIterator("/addressees/addressee");
		
		for (Node addrNode = addrNodeIt.nextNode(); addrNode != null; addrNode = addrNodeIt
		.nextNode()) {
		
			String obj=(XmlFacade.getNodeValue(addrNode));
			DestinatarioVO dest= new DestinatarioVO();
			if(typeEnvio==ENVIO_EMAIL){
				dest.setMail(obj);
			}
			else if(typeEnvio==ENVIO_SMS){
				dest.setMovil(obj);
			}
			dest.setDestinatarioExterno(true);
			destinatariosVo.add(dest);
		}
		
		NodeIterator rolesNodeIt = xml
		.getNodeIterator("/addressees/roles/role");
		boolean primero=true;
		for (Node roleNode = rolesNodeIt.nextNode(); roleNode != null; roleNode = rolesNodeIt
		.nextNode()) {
		
			String role=(XmlFacade.getNodeValue(roleNode));
			
			if((StringUtils.equalsIgnoreCase(role, "INT")||StringUtils.equalsIgnoreCase(role, "*"))&& primero)
			{
				String movil=xmlExp.get("EXPEDIENTE/SPAC_EXPEDIENTES/REGISTRO/TFNOMOVIL");
				String email=xmlExp.get("EXPEDIENTE/SPAC_EXPEDIENTES/REGISTRO/DIRECCIONTELEMATICA");
				if( (!"null".equalsIgnoreCase(movil) && typeEnvio==ENVIO_SMS )||(!"null".equalsIgnoreCase(email) && typeEnvio==ENVIO_EMAIL)){
					DestinatarioVO dest= new DestinatarioVO(movil);
					dest.setNif(xmlExp.get("/EXPEDIENTE/SPAC_EXPEDIENTES/REGISTRO/NIFCIFTITULAR"));
					dest.setNombre(xmlExp.get("/EXPEDIENTE/SPAC_EXPEDIENTES/REGISTRO/IDENTIDADTITULAR"));
					destinatariosVo.add(dest);
				}
			
			}
			primero=false;
			
			getInfoIntervinientes(role, destinatariosVo, typeEnvio);	
			
		}
		
		//Ahora hay que componer el mensaje
		
		fillTexto(xmlMensaje, destinatariosVo, TEXTO_CONTENIDO);
		
		//Realizamos el envío
		
		if(typeEnvio==ENVIO_SMS){
			realizarEnvio(actx,destinatariosVo);
		}
		if(typeEnvio==ENVIO_EMAIL){
			
			
			realizarEnvio(actx,destinatariosVo);
		}
		
		
		
	}
	
	/**
	 * 
	 * @param xml xml con la información de las labels
	 * @param destinatariosVo Lista de destinatarios destino
	 * @param tipoTexto Indica si es un envio sms o email
	 */
	public void fillTexto(XMLFacade xml , List destinatariosVo, int tipoTexto){
		
		Node labels=xml.getSingleNode("/labels");
		String defaultLanguage=XmlFacade.getAttributeValue(labels, "default");
		NodeIterator labelNodeIt = xml.getNodeIterator("/labels/label");
		for (Node labelNode = labelNodeIt.nextNode(); labelNode != null; labelNode = labelNodeIt.nextNode()) {
		
			String language=XmlFacade.getAttributeValue(labelNode, "locale");
			if(defaultLanguage.equalsIgnoreCase(language)){
				getTexto( XmlFacade.getNodeValue(labelNode),destinatariosVo, tipoTexto);
				
			}
		
		}
	}

}
