/*
 * Created on 06-abr-2005
 *
 */
package ieci.tecdoc.mvc.action.adminUser.ldap;

import ieci.tecdoc.core.collections.IeciTdShortTextArrayList;
import ieci.tecdoc.core.ldap.LdapBasicFns;
import ieci.tecdoc.core.ldap.LdapConnCfg;
import ieci.tecdoc.core.ldap.LdapConnection;
import ieci.tecdoc.core.ldap.LdapFilter;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.error.MvcError;
import ieci.tecdoc.mvc.form.adminUser.common.SearchForm;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.SessionHelper;
import ieci.tecdoc.sbo.config.CfgDefs;
import ieci.tecdoc.sbo.uas.ldap.UasConfigUtilLdap;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * @author Antonio María
 *
 */
public class Search extends BaseAction{

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(Search.class);
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
    	
    	SearchForm searchForm = (SearchForm) form;
    	
        //String userAtt = UasConfigUtilLdap.createUasAuthConfig().getUserAttrName();
        String userAtt = "cn";
    	String groupAtt = "cn";
    	
        String submitted = request.getParameter("submitted");
        
        if (submitted == null )
        {
            searchForm.setTipoBusqueda((byte)1);
            //searchForm.setValor(userAtt + "=");
        }
        else if( !submitted.equals("") ) 
        {
            byte tipoBusqueda = searchForm.getTipoBusqueda();
            String campoBusqueda = searchForm.getValor();
            StringTokenizer tok = new StringTokenizer(campoBusqueda, "=");
            String condicion = "";
            String valor = "";
            int tokCount = tok.countTokens() ;
            boolean buscar= false;
            if ( tokCount == 2){	// La consulta tiene la forma
                					// uid=xxxx ó cn=xxxx
                condicion = tok.nextToken();
                valor = tok.nextToken();
                buscar = true;
            }
            else // Cambia la condicion de busqueda o, la caja se manda vacia
            {
                if (tipoBusqueda == Constantes.GROUP) {
                    //searchForm.setValor(groupAtt +"=");
                	condicion = groupAtt;
                } else {
                    //searchForm.setValor(userAtt+"=");
                	condicion = userAtt;
                }
                valor = campoBusqueda;
                buscar = true;
            }
            if (buscar)
            {
                LdapConnection ldapConn = null;
                LdapConnCfg connCfg = null;
                boolean enc = false;
                ActionMessages mensajes = new ActionMessages();

    	        ldapConn = new LdapConnection();
    	        connCfg = UasConfigUtilLdap.createLdapConnConfig(entidad);
    	        ldapConn.open(connCfg);
    	        
    	        if (logger.isDebugEnabled())
    	        {
    	            logger.debug("Buscando: " +searchForm.getValor() + " por " + searchForm.getTipoBusqueda());
    	        }
    	            String dn= null;
    	            String cadenaBusqueda = null; 
    	            
    	            LdapFilter flt=new LdapFilter();
    	            
    	            if( ldapConn.getEngine() == CfgDefs.LDAP_ENGINE_IPLANET ) {
    	            	if (tipoBusqueda == Constantes.GROUP) {
    	            	  condicion = "cn";	
    	            	} else {
    	              	  condicion = "uid";
    	            	}
    	            }
    	            
    	            if (searchForm.getTipoBusqueda() == Constantes.PERSON){ // Usuarios
    	                
    	            	flt.addFilter(LdapFilter.getUserFilter(ldapConn));
    	                flt.addFilter(condicion, valor);
    	                cadenaBusqueda = flt.getText();

    	                // usuarios ej:(&(objectclass=person)(uid=usuario))
    	                IeciTdShortTextArrayList dns=null;
    	                
    	                String userStart = UasConfigUtilLdap.createUasAuthConfig(entidad).getUserStart();
    	                String start = UserTree.ldapRootDn;
    	                if( !userStart.equals("")) {
    	                	if( userStart.indexOf(UserTree.ldapRootDn) != -1) {
    	                		int i = userStart.indexOf(UserTree.ldapRootDn);
    	                		if (i > 0) {
    	                			start = userStart.substring(0,i-1);
    	                		}
    	                		else {
    	                			start = "";
    	                		}
    	                	}
    	                } else {
    	                	start = "";
    	                }
    	                
                        dns = LdapBasicFns.findEntryDns(ldapConn, start, UasConfigUtilLdap.createUasAuthConfig(entidad).getUserScope(), cadenaBusqueda);
                        
        	            int numOcurrencias = dns.count();
        	            if (numOcurrencias ==1){
        	                dn=dns.get(0);
        	                enc = true;
        	            }
        	            else if (numOcurrencias == 0)
        	            {
        	                
        	                ActionMessage msg = new ActionMessage( String.valueOf(MvcError.EC_OBJ_LDAP_SEARCH_USER_NOT_FOUND));
        	                mensajes.add(ActionMessages.GLOBAL_MESSAGE, msg);
        	                saveMessages(request,mensajes);
        	            }
        	            else{
        	                ActionMessage msg = new ActionMessage( String.valueOf(MvcError.EC_OBJ_LDAP_SEARCH_EXCESS_USER));
        	                mensajes.add(ActionMessages.GLOBAL_MESSAGE, msg);
        	                saveMessages(request,mensajes);
        	            }
        	                
        	            request.setAttribute("typeTree",String.valueOf(Constantes.PERSON));
    	            }
    	            
    	            else{ // Grupos
    	                flt.addFilter(LdapFilter.getGroupFilter(ldapConn));
    	                flt.addFilter(condicion, valor);
    	                cadenaBusqueda = flt.getText();
    	                
    	                // Grupos ej:(&(objectclass=groupofuniquenames)(cn=usuarios.consultas))
    	                IeciTdShortTextArrayList dns=null;
    	                
    	                String groupStart = UasConfigUtilLdap.createUasAuthConfig(entidad).getGroupStart();
    	                String start = UserTree.ldapRootDn;
    	                if( !groupStart.equals("")) {
    	                	if( groupStart.indexOf(UserTree.ldapRootDn) != -1) {
    	                		int i = groupStart.indexOf(UserTree.ldapRootDn);
    	                		if (i > 0) {
    	                			start = groupStart.substring(0,i-1);
    	                		}
    	                		else {
    	                			start = "";
    	                		}
    	                	}
    	                } else {
    	                	start = "";
    	                }
    	                
        	            dns = LdapBasicFns.findEntryDns(ldapConn, start, UasConfigUtilLdap.createUasAuthConfig(entidad).getGroupScope(), cadenaBusqueda);
        	            int numOcurrencias = dns.count() ;
        	            
        	            if (numOcurrencias ==1){
        	                dn=dns.get(0);
        	                enc = true;
        	            }
        	            else if (numOcurrencias == 0)
        	            {
        	                ActionMessage msg = new ActionMessage( String.valueOf(MvcError.EC_OBJ_LDAP_SEARCH_GROUP_NOT_FOUND) );
        	                mensajes.add(ActionMessages.GLOBAL_MESSAGE, msg);
        	                saveMessages(request,mensajes);
        	            }
        	            else{
        	                ActionMessage msg = new ActionMessage( String.valueOf(MvcError.EC_OBJ_LDAP_SEARCH_EXCESS_GROUP));
        	                mensajes.add(ActionMessages.GLOBAL_MESSAGE, msg);
        	                saveMessages(request,mensajes);
        	            }  
        	            request.setAttribute("typeTree",String.valueOf(Constantes.GROUP));
    	            }   
    	            
           if (enc) {
               if (logger.isDebugEnabled())
                   logger.debug("Encontrado: " + dn);
               		request.setAttribute("enc", new Boolean(true));
    	            request.setAttribute("dn",dn);
    	       }
            } // fin if (buscar)            
        }
        
        return mapping.findForward("success");
    }

}
