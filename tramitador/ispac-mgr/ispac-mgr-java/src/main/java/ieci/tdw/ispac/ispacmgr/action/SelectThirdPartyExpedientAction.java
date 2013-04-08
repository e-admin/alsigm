package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectThirdPartyExpedientAction extends BaseAction {
    
    public ActionForward executeAction(ActionMapping mapping,
    								   ActionForm form,
    								   HttpServletRequest request,
    								   HttpServletResponse response,
    								   SessionAPI session) throws Exception {
    	
        ClientContext cct = session.getClientContext();
		
		///////////////////////////////////////////////
		// Se obtiene el estado de tramitación
        IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
   	    IState currentState = managerAPI.currentState(getStateticket(request));
   	    
   	    IInvesflowAPI invesFlowAPI = session.getAPI();
   	    IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
   	    
   	    List ltThirdParty = new ArrayList();
   	    
   	    String numexp = currentState.getNumexp();
   	    Properties properties = getProperties();
   	    
   	    // Obtener el interesado principal
   	    IItem principal = getPrincipal(entitiesAPI, numexp, properties);
   	    
   	    if (principal != null) {
   	    	ltThirdParty.add(principal);
   	    }
   	    
   	    // Obtener los participantes
   	    ltThirdParty.addAll(getParticipants(entitiesAPI, numexp, properties));
   	    
    	if (!ltThirdParty.isEmpty()) {
    		
        	// Generar los beans con los terceros en la request para la vista
        	IItemCollection collection = new ListCollection(ltThirdParty);
        	request.setAttribute("ValueList", CollectionBean.getBeanList(collection));
    	}
   	    
		/*
		 * Nombre de la variable de sesión que mantiene los parámetros
		 * del tag de búsqueda.
		 */
		String parameters = request.getParameter("parameters");
    	
//        // Obtiene los parametros del tag y los salva en la request
//        String sParameters = session.getClientContext().getSsVariable(parameters);
//        if (sParameters != null) {
//            request.setAttribute("parameters", ActionFrameTag.toMap(sParameters));
//        }

        // Obtiene los parametros del tag y los salva en la request
        Map sParameters = (Map)request.getSession().getAttribute(parameters);
        if (sParameters != null) {
            request.setAttribute("parameters", sParameters);
        }

        // Establecer el formateador de la tabla para la vista
		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/digester/thirdpartyexpedientformatter.xml"));
		request.setAttribute("Formatter", formatter);

    	return mapping.findForward("success");
    }
    
    private List getParticipants(IEntitiesAPI entitiesAPI, 
    							 String numexp,
    							 Properties properties) throws ISPACException {
    	
    	List ltParticipants = new ArrayList();
    	
    	// Obtener los participantes del expediente
    	IItemCollection participants = entitiesAPI.getParticipants(numexp, null, "NDOC");
    	
        Iterator it = participants.iterator();
        while (it.hasNext()) {
        	
        	IItem participant = (IItem)it.next();
        	
        	// Datos del participante
        	String id = participant.getString("ID_EXT");
        	String nombre = participant.getString("NOMBRE");
        	String tipo = participant.getString("TIPO_PERSONA");
        	String id_participante = participant.getString("ID");
        	
        	IItem thirdParty = generateThirdParty(id, nombre, tipo, id_participante, properties );
        	
        	if (thirdParty != null) {
        		ltParticipants.add(thirdParty);
        	}
        }
        
        return ltParticipants;
    }
    
    private IItem getPrincipal(IEntitiesAPI entitiesAPI,
    						   String numExp,
    						   Properties properties) throws ISPACException {
        
        // Obtener el expediente
        IItem expediente = entitiesAPI.getExpedient(numExp);
        
        // Datos del interesado principal
        String id = expediente.getString("IDTITULAR");
        String nombre = expediente.getString("IDENTIDADTITULAR");
        String tipo = expediente.getString("TIPOPERSONA");
        String id_participante="";
        
        return generateThirdParty(id, nombre, tipo, id_participante, properties);
    }
 
    private Properties getProperties() {
    	
        int ordinal = 0;
        Properties propertiesRet = new Properties();
        propertiesRet.add( new Property(ordinal++, "ID", Types.VARCHAR));
        propertiesRet.add( new Property(ordinal++, "NOMBRE", Types.VARCHAR));
        propertiesRet.add( new Property(ordinal++, "TIPO", Types.VARCHAR));
        propertiesRet.add( new Property(ordinal++, "VALUES", Types.VARCHAR));
        propertiesRet.add( new Property(ordinal++, "ID_PARTICIPANTE", Types.VARCHAR));
        
        return propertiesRet;
    }
    
    private IItem generateThirdParty(String id,
    								 String nombre,
    								 String tipo,
    								 String id_participante,
    								 Properties properties) throws ISPACException {
    	
        if (nombre != null) {
        	
        	// TODO Valores que se asignan a id y tipo cuando son nulos
            if (id == null) {
            	id = "0";
            }
            
            if ((tipo == null) || (id.equals("0"))) {
            	tipo = "N"; //No validado
            }
            
            // Generar el tercero del expediente
            IItem thirdParty = new GenericItem(properties, "ID");
            thirdParty.set("ID", id);
            thirdParty.set("NOMBRE", nombre);
            thirdParty.set("TIPO", tipo);
            thirdParty.set("VALUES", id + "$" + nombre + "$" + tipo);
            thirdParty.set("ID_PARTICIPANTE", id_participante);
            
            return thirdParty;
        }
        
        return null;
    }
    
}