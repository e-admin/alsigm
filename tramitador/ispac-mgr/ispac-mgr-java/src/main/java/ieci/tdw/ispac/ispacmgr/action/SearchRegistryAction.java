package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IThirdPartyAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispaclib.sicres.ISicresConnector;
import ieci.tdw.ispac.ispaclib.sicres.SicresConnectorFactory;
import ieci.tdw.ispac.ispaclib.sicres.vo.Register;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterInfo;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterType;
import ieci.tdw.ispac.ispaclib.sicres.vo.ThirdPerson;
import ieci.tdw.ispac.ispaclib.thirdparty.IThirdPartyAdapter;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.sql.Types;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para realizar búsquedas en el registro de entrada/salida SICRES.
 * 
 * Parámetros:
 * - fieldValue => número de registro
 * - tp_reg => tipo de registro: "ENTRADA" o "SALIDA".
 *
 */
public class SearchRegistryAction extends RegistryBaseAction {
	
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(SearchRegistryAction.class);
	
	public ActionForward defaultExecute(ActionMapping mapping, 
									   ActionForm form,
									   HttpServletRequest request, 
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {
		
        ClientContext cct = session.getClientContext();
		
   	    IInvesflowAPI invesFlowAPI = session.getAPI();
		IThirdPartyAPI thirdPartyAPI = invesFlowAPI.getThirdPartyAPI();
		
		// Obtenemos el conector con sicres
		ISicresConnector sicresConnector = SicresConnectorFactory.getInstance().getSicresConnector(cct);
		if (sicresConnector != null) {

			// Obtener el tipo de registro
			String registerType = request.getParameter("tp_reg");
			if (StringUtils.isBlank(registerType)) {
				registerType = RegisterType.SALIDA;
			}

			// Obtener el número de registro
			String registerNumber = request.getParameter("fieldValue");
			if (StringUtils.isEmpty(registerNumber)) {
				if (RegisterType.ENTRADA.equalsIgnoreCase(registerType)) {
					throw new ISPACInfo("exception.info.emptyInputRegister",false);
				} else {
					throw new ISPACInfo("exception.info.emptyOutputRegister",false);	
				}
			}
			
			// Leer el número de registro
			RegisterInfo registerInfo = new RegisterInfo(null, registerNumber, null, registerType);
			Register register = sicresConnector.readRegister(registerInfo);

            request.setAttribute("NUMERO_REGISTRO", registerNumber);
            request.setAttribute("TIPO_REGISTRO", registerType);

	        if (register != null) {
	        	
	            request.setAttribute("REG_FOUND", "true");
	            
	            // Establecer los datos para la vista
	            viewRegister(request, register);
	            
	            // Generar el Item con los datos para el expediente e insertarlo en la request
	            IItem item = generateRegisterItem(register, thirdPartyAPI);
	            request.setAttribute("Value", new ItemBean(item));
	        }
	        else {
	        	request.setAttribute("REG_FOUND", "false");
	        	if (RegisterType.ENTRADA.equalsIgnoreCase(registerType)) {
	        		throw new ISPACInfo(getResources(request).getMessage("registro.entrada.notfound")+" "+registerNumber,false);
	        	} else {
	        		throw new ISPACInfo(getResources(request).getMessage("registro.salida.notfound")+" "+registerNumber,false);
	        	}
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
	
    private IItem generateRegisterItem(Register register,
    										IThirdPartyAPI thirdPartyAPI) throws ISPACException {
    	
        Properties properties = getProperties();
        IItem item = new GenericItem(properties, "ID");
        
        try {
        	// REGISTRO
	    	item.set("NREG", register.getRegisterOrigin().getRegisterNumber());
	    	item.set("FREG", DateUtil.formatDate(register.getRegisterOrigin().getRegisterDate().getTime()));
	    	
            if (register.getRegisterData() != null) {
    	    
            	// ASUNTO
    	    	if (register.getRegisterData().getSummary() != null) {
    	    		item.set("ASUNTO", register.getRegisterData().getSummary());
    	    	}            	
            	
            	// DESTINATARIO PRINCIPAL
            	if (!ArrayUtils.isEmpty(register.getRegisterData().getParticipants())) {
                		
                	ThirdPerson thirdPerson = register.getRegisterData().getParticipants() [0];
                	if (thirdPerson != null) {
                		
                		// Obtener el interesado principal
	                	IThirdPartyAdapter thirdParty = null;

	                	if ((thirdPartyAPI != null) && StringUtils.isNotBlank(thirdPerson.getId())) {
	                		
	                		// Obtener los datos del tercero consultando la Base de Datos de Terceros
	                		thirdParty = thirdPartyAPI.lookupById(thirdPerson.getId());
                		}

	                	if (thirdParty != null) {
	                		
		                	item.set("IDTITULAR", thirdParty.getIdExt());
		                	item.set("IDENTIDADTITULAR", thirdParty.getNombreCompleto());
		                	item.set("TIPOPERSONA", thirdParty.getTipoPersona());
	                	}
	                	else {
	                		// Si no existe el tercero en la Base de Datos de Terceros
	                		// la identidad del tercero se obtiene de los datos de registro
	                		item.set("IDENTIDADTITULAR", thirdPerson.getName());
	                	}
                	}
            	}
            }
            
            // ORIGEN
            if (register.getOriginOrganization() != null) {
            	
            	item.set("ORIGEN_ID", (register.getOriginOrganization().getId() == null) ? "" : register.getOriginOrganization().getId());
            	item.set("ORIGEN", (register.getOriginOrganization().getName() == null) ? "" : register.getOriginOrganization().getName());
            	item.set("ORIGEN_TIPO", ISicresConnector.ORGANIZATION_TYPE);
            }

            // DESTINO
            if (register.getDestinationOrganization() != null) {
            	
            	item.set("DESTINO_ID", (register.getDestinationOrganization().getId() == null) ? "" : register.getDestinationOrganization().getId());
            	item.set("DESTINO", (register.getDestinationOrganization().getName() == null) ? "" : register.getDestinationOrganization().getName());
            	item.set("DESTINO_TIPO", ISicresConnector.ORGANIZATION_TYPE);
            }
            
        }
	    catch (Exception e) {
	    	logger.error("Error al componer la información del registro", e);
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
        properties.add( new Property(ordinal++, "IDENTIDADTITULAR", Types.VARCHAR));
        
        properties.add( new Property(ordinal++, "ORIGEN_ID", Types.VARCHAR));
        properties.add( new Property(ordinal++, "ORIGEN", Types.VARCHAR));
        properties.add( new Property(ordinal++, "ORIGEN_TIPO", Types.VARCHAR));

        properties.add( new Property(ordinal++, "DESTINO_ID", Types.VARCHAR));
        properties.add( new Property(ordinal++, "DESTINO", Types.VARCHAR));
        properties.add( new Property(ordinal++, "DESTINO_TIPO", Types.VARCHAR));

        properties.add( new Property(ordinal++, "ASUNTO", Types.VARCHAR));
        return properties;
    }
    
	
}
