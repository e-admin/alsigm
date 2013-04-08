package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRegisterAPI;
import ieci.tdw.ispac.api.IThirdPartyAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispaclib.sicres.ISicresConnector;
import ieci.tdw.ispac.ispaclib.sicres.vo.Register;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterInfo;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterType;
import ieci.tdw.ispac.ispaclib.sicres.vo.ThirdPerson;
import ieci.tdw.ispac.ispaclib.thirdparty.IElectronicAddressAdapter;
import ieci.tdw.ispac.ispaclib.thirdparty.IPostalAddressAdapter;
import ieci.tdw.ispac.ispaclib.thirdparty.IThirdPartyAdapter;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;

import java.sql.Types;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para realizar búsquedas en el registro de entrada SICRES.
 *
 */
public class SearchInputRegistryAction extends BaseAction {
	
	public ActionForward executeAction(ActionMapping mapping, 
									   ActionForm form,
									   HttpServletRequest request, 
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {
   	    IInvesflowAPI invesFlowAPI = session.getAPI();
		IThirdPartyAPI thirdPartyAPI = invesFlowAPI.getThirdPartyAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		IRegisterAPI registerAPI = invesFlowAPI.getRegisterAPI();
		
		
		
		if (registerAPI.existConnector()) {
			
			EntityForm defaultForm = (EntityForm) form;
	
			// Nombre del campo que contiene el valor de la búsqueda
			String field = request.getParameter("field");
	
			// Número de registro a buscar
			String registerNumber = defaultForm.getProperty(field);
			
			if (StringUtils.isEmpty(registerNumber)) {
				throw new ISPACInfo("exception.info.emptyInputRegister",false);
			}
			
			// Leer el número de registro
			RegisterInfo registerInfo = new RegisterInfo(null, registerNumber, null, RegisterType.ENTRADA);
			Register register = registerAPI.readRegister(registerInfo);
			
	        if (register != null) {
	        	
	            request.setAttribute("REG_FOUND", "true");
	            request.setAttribute("NUMERO_REGISTRO", registerNumber);
	            
	            // Establecer los datos para la vista
	            viewRegister(request, register);
	            
	            // Generar el Item con los datos para el expediente e insertarlo en la request
	            IItem item = generateInputRegisterItem(register, thirdPartyAPI, entitiesAPI);
	            request.setAttribute("Value", new ItemBean(item));
	        }
	        else {
	        	request.setAttribute("REG_FOUND", "false");
		        request.setAttribute("NUMERO_REGISTRO", registerNumber);
	        	throw new ISPACInfo(getResources(request).getMessage("registro.entrada.notfound")+" "+registerNumber,false);
	          
	        }

		} else {
			
			// El conector con SICRES no está configurado
			throw new ISPACInfo("exception.sicres.notConfigured",false);
		}

		/*
		 * Nombre de la variable de sesión que mantiene los parámetros
		 * del tag de búsqueda
		 */
		String parameters = request.getParameter("parameters");
    	
//        // Obtiene los parametros del tag y los salva en la request
//        String sParameters = session.getClientContext().getSsVariable(parameters);
//        if (sParameters != null) {
//            request.setAttribute(parameters, ActionFrameTag.toMap(sParameters));
//        }

		// Obtiene los parametros del tag y los salva en la request
        Map sParameters = (Map)request.getSession().getAttribute(parameters);
        if (sParameters != null) {
            request.setAttribute("parameters", sParameters);
        }
                
        return mapping.findForward("success");
	}
	
    private IItem generateInputRegisterItem(Register register,
    										IThirdPartyAPI thirdPartyAPI,
    										IEntitiesAPI entitiesAPI) throws ISPACException {
    	
        Properties properties = getProperties();
        IItem item = new GenericItem(properties, "ID");
        
        try {
        	// REGISTRO
	    	item.set("NREG", register.getRegisterOrigin().getRegisterNumber());
	    	item.set("FREG", DateUtil.formatDate(register.getRegisterOrigin().getRegisterDate().getTime()));
	    	
            if (register.getRegisterData() != null) {
            	
            	// INTERESADO PRINCIPAL
            	if (!ArrayUtils.isEmpty(register.getRegisterData().getParticipants())) {
                		
                	ThirdPerson thirdPerson = register.getRegisterData().getParticipants() [0];
                	if (thirdPerson != null) {
                		
                		// Obtener el interesado principal
	                	IThirdPartyAdapter thirdParty = null;
	                	
	                	if ((thirdPartyAPI != null) && StringUtils.isNotBlank(thirdPerson.getId())) {
	                		
	                		// Obtener los datos del tercero consultando la Base de Datos de Terceros
	                		thirdParty = thirdPartyAPI.lookupById(thirdPerson.getId(),thirdPerson.getAddressId(),null);
                		}

	                	if (thirdParty != null) {
	                		
		                	item.set("TIPOPERSONA", thirdParty.getTipoPersona());
		                	item.set("IDTITULAR", thirdParty.getIdExt());
		                	item.set("NIFCIFTITULAR", thirdParty.getIdentificacion());
		                	item.set("IDENTIDADTITULAR", thirdParty.getNombreCompleto());
		                	
		                	IPostalAddressAdapter dirPostal = thirdParty.getDefaultDireccionPostal();
		                	if (dirPostal != null) {
		                		item.set("IDDIRECCIONPOSTAL", dirPostal.getId());
			                	item.set("DOMICILIO", dirPostal.getDireccionPostal());
			                	item.set("CPOSTAL", dirPostal.getCodigoPostal());
			                	item.set("CIUDAD", dirPostal.getMunicipio());
			                	item.set("TFNOFIJO", dirPostal.getTelefono());

			                	String regionPais = dirPostal.getProvincia();
			                	if (StringUtils.isNotEmpty(dirPostal.getPais()))
			                		regionPais += "/"+dirPostal.getPais();
			                	
			                	item.set("REGIONPAIS", regionPais);
		                	}
		                	
		                	IElectronicAddressAdapter dirElectronica = thirdParty.getDefaultDireccionElectronica();
		                	if (dirElectronica != null) {
		                		if (dirElectronica.getTipo() == IElectronicAddressAdapter.PHONE_TYPE) {
		                			item.set("TFNOMOVIL", dirElectronica.getDireccion());
		                		} else {
		                			item.set("DIRECCIONTELEMATICA", dirElectronica.getDireccion());
		                		}
		                	}
		                	
		                	String addressType = IThirdPartyAdapter.ADDRESS_TYPE_POSTAL;
		                	if (thirdParty.isNotificacionTelematica()) {
		                		addressType = IThirdPartyAdapter.ADDRESS_TYPE_TELEMATIC;
		                	}
		                	item.set("TIPODIRECCIONINTERESADO", addressType);
		                	item.set("SUSTITUTOTIPODIRECCIONINTERESADO", 
		                			entitiesAPI.getSubstitute(SpacEntities.SPAC_TBL_005, 
		                					addressType));
	                	}
	                	else {
	                		// Si no existe el tercero en la Base de Datos de Terceros
	                		// la identidad del tercero se obtiene de los datos de registro
	                		item.set("IDENTIDADTITULAR", thirdPerson.getName());
	                		item.set("DOMICILIO", thirdPerson.getAddress());
	                	}
                	}
            	}
            	
            	// ASUNTO (se corresponde con el resumen de registro)
    	    	if (register.getRegisterData().getSummary() != null) {
    	    		item.set("ASUNTO", StringUtils.nullToEmpty(register.getRegisterData().getSummary()));
    	    	} else if (register.getRegisterData().getSubject() != null) {
    	    		item.set("ASUNTO", StringUtils.nullToEmpty(register.getRegisterData().getSubject().getName()));
    	    	}
            }
            
            // DESTINO
            if (register.getDestinationOrganization() != null) {
            	
            	item.set("DESTINO_ID", (register.getDestinationOrganization().getId() == null) ? "" : register.getDestinationOrganization().getId());
            	item.set("DESTINO", (register.getDestinationOrganization().getName() == null) ? "" : register.getDestinationOrganization().getName());
            	item.set("DESTINO_TIPO", ISicresConnector.ORGANIZATION_TYPE);
            }
        }
	    catch (Exception e) {
	    	throw new ISPACException(e);
	    }

    	return item;
    }
    
    private Properties getProperties() {
    	
        int ordinal = 0;
        Properties properties = new Properties();
        properties.add( new Property(ordinal++, "ID", Types.VARCHAR));
        properties.add( new Property(ordinal++, "NREG", Types.VARCHAR));
        properties.add( new Property(ordinal++, "FREG", Types.VARCHAR));
        
        properties.add( new Property(ordinal++, "TIPOPERSONA", Types.VARCHAR));
        properties.add( new Property(ordinal++, "IDTITULAR", Types.VARCHAR));
        properties.add( new Property(ordinal++, "NIFCIFTITULAR", Types.VARCHAR));
        properties.add( new Property(ordinal++, "IDENTIDADTITULAR", Types.VARCHAR));
        properties.add( new Property(ordinal++, "IDDIRECCIONPOSTAL", Types.VARCHAR));
        properties.add( new Property(ordinal++, "DOMICILIO", Types.VARCHAR));
        properties.add( new Property(ordinal++, "CPOSTAL", Types.VARCHAR));
        properties.add( new Property(ordinal++, "CIUDAD", Types.VARCHAR));
        properties.add( new Property(ordinal++, "REGIONPAIS", Types.VARCHAR)); 
        properties.add( new Property(ordinal++, "TFNOFIJO", Types.VARCHAR));
        properties.add( new Property(ordinal++, "TFNOMOVIL", Types.VARCHAR));
        properties.add( new Property(ordinal++, "DIRECCIONTELEMATICA", Types.VARCHAR));
        properties.add( new Property(ordinal++, "TIPODIRECCIONINTERESADO", Types.VARCHAR));
        properties.add( new Property(ordinal++, "SUSTITUTOTIPODIRECCIONINTERESADO", Types.VARCHAR));
        
        properties.add( new Property(ordinal++, "DESTINO_ID", Types.VARCHAR));
        properties.add( new Property(ordinal++, "DESTINO", Types.VARCHAR));
        properties.add( new Property(ordinal++, "DESTINO_TIPO", Types.VARCHAR));
        
        properties.add( new Property(ordinal++, "ASUNTO", Types.VARCHAR));

        return properties;
    }
    
    private void viewRegister(HttpServletRequest request,
    						  Register register) throws ISPACException {
    	
    	try {    	
	        // PROCEDENCIA
	        if (register.getRegisterOrigin() != null) {
	        	
	        	request.setAttribute("FECHA_REGISTRO", (register.getRegisterOrigin().getRegisterDate() == null) ? "" : DateUtil.formatCalendar(register.getRegisterOrigin().getRegisterDate()));
	        	request.setAttribute("FECHA_CREACION", (register.getRegisterOrigin().getCreationDate() == null) ? "" : DateUtil.formatDate(register.getRegisterOrigin().getCreationDate().getTime()));
	        	request.setAttribute("USUARIO", (register.getRegisterOrigin().getUser() == null) ? "" : register.getRegisterOrigin().getUser());
	        	
	        	if (register.getRegisterOrigin().getRegisterOffice() != null) {
	        		
	        		request.setAttribute("OFICINA_REGISTRO", (register.getRegisterOrigin().getRegisterOffice().getName() == null) ? "" : register.getRegisterOrigin().getRegisterOffice().getName());
	        	}
	        }
	        
	        // ORGANISMOS ORIGEN Y DESTINO
	        if (register.getOriginOrganization() != null) {
	        	
	        	request.setAttribute("ORGANISMO_ORIGEN", (register.getOriginOrganization().getName() == null) ? "" : register.getOriginOrganization().getName());
	        }
	        if (register.getDestinationOrganization() != null) {
	        	
	        	request.setAttribute("ORGANISMO_DESTINO", (register.getDestinationOrganization().getName() == null) ? "" : register.getDestinationOrganization().getName());
	        }
	        
	        // CONTENIDO / INTERESADOS
	        if (register.getRegisterData() != null) {
	        	
	        	if ((register.getRegisterData().getParticipants() != null) && 
	        		(register.getRegisterData().getParticipants().length >0)) {
	        		
	        		ThirdPerson thirdPerson = register.getRegisterData().getParticipants() [0];
	        		request.setAttribute("REMITENTE", (thirdPerson.getName() == null) ? "" : thirdPerson.getName());
	        	}
	        	else {
	        		request.setAttribute("REMITENTE", "");
	        	}
	        	request.setAttribute("RESUMEN", (register.getRegisterData().getSummary() == null) ? "" : register.getRegisterData().getSummary());
	        	
				if ((register.getRegisterData().getInfoDocuments() != null)
						&& (register.getRegisterData().getInfoDocuments().length > 0)) {
					request.setAttribute("DOCUMENTOS", register.getRegisterData().getInfoDocuments().length);
				}
	        	
	        	if (register.getRegisterData().getSubject() != null) {
	        		
	        		request.setAttribute("ASUNTO", (register.getRegisterData().getSubject().getName() == null) ? "" : register.getRegisterData().getSubject().getName());
	        	}
	        }
	        
	        // TRANSPORTE
	        if (register.getTransport() != null) {
	        	
	        	request.setAttribute("TIPO_TRANSPORTE", (register.getTransport().getType() == null) ? "" : register.getTransport().getType());
	        	request.setAttribute("NUMERO_TRANSPORTE", (register.getTransport().getNumber() == null) ? "" : register.getTransport().getNumber());
	        }
    	}
    	catch (Exception e) {
    		throw new ISPACException(e);
    	}
    }
	
}