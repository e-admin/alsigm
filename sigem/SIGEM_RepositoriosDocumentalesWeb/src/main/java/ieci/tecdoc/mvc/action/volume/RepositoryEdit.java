/*
 * Created on 11-abr-2005
 *
 */
package ieci.tecdoc.mvc.action.volume;

import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.user.User;
import ieci.tecdoc.idoc.admin.api.volume.Repository;
import ieci.tecdoc.idoc.admin.api.volume.VolumeDefs;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.dto.volume.StateListDTO;
import ieci.tecdoc.mvc.form.volume.RepositoryForm;
import ieci.tecdoc.mvc.util.SessionHelper;
import ieci.tecdoc.sgm.base.ftp.FtpUtils;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

/**
 * @author Antonio María
 *
 */
public class RepositoryEdit extends BaseAction {

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(RepositoryEdit.class);    
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
    	
        RepositoryForm repositoryForm= (RepositoryForm) form;
        String objId = request.getParameter("id");
        int id = Integer.parseInt(objId);
        
        HttpSession session = request.getSession();
        
        String submitted = request.getParameter("submitted");
        String test = request.getParameter("test");
        StateListDTO stateListDTO = new StateListDTO();
        stateListDTO.setStates(getRepositoryAtts());
        request.setAttribute("statesRepositoryList", stateListDTO );
        int type = -1;
        
        if (submitted == null )
            type = loadData (repositoryForm, id, session, entidad);

        else if (test !=null && test.equals("si")){
        	boolean testOK=testConexion(repositoryForm);
        	if(testOK){
        		request.setAttribute("testOk", "success");
        	}else{
        		request.setAttribute("testOk", "error");
        	}
        }
        else{
        	if(!testConexion(repositoryForm)){
        		throw new ieci.tecdoc.sgm.base.exception.IeciTdException("0","Error de conexión FTP");
        	}
            type = saveData (repositoryForm, id, session, entidad);
            request.setAttribute("id", objId);
            request.setAttribute("tipo", "1");
            return mapping.findForward("ok");
        }
        if (repositoryForm.getTypeServer() != -1 ) // es un repositorio ftp
        {
            Collection typeServer = new ArrayList();
            typeServer.add(new LabelValueBean("Dirección IP", "1"));
            typeServer.add(new LabelValueBean("Nombre Lógico", "2"));
            request.setAttribute("typeServerCollection", typeServer);
        }
        request.setAttribute("type", String.valueOf(type));
        return mapping.findForward("success");
    }
    
    private boolean testConexion(RepositoryForm repositoryForm){
    			
		String direccion=repositoryForm.getServer();
		String puerto=Integer.toString(repositoryForm.getPort());
		String usuario=repositoryForm.getUser();
		String password=repositoryForm.getPwd();	
		
		return FtpUtils.comprobarConexionFTP(direccion, puerto, usuario, password);

    }
    
    /**
     * @param repositoryForm
     * @param id
     */
    private int saveData(RepositoryForm repositoryForm, int id, HttpSession session, String entidad) throws Exception{

        Repository rep = (Repository) session.getAttribute("objRep");
        
        rep = ObjFactory.createRepository(0);
        rep.load(id, entidad);
        
        int type = rep.getType();
        
        rep.setName(repositoryForm.getName());
        rep.setRemarks(repositoryForm.getDescription());
        rep.setServer(repositoryForm.getServer());
        rep.setUser(repositoryForm.getUser());
        rep.setPassword(repositoryForm.getPwd());
        rep.setPort(repositoryForm.getPort());
        rep.cargaInfo();
        
        String states[] = repositoryForm.getStates();
        int status = 0;
        
        String s = new String();
        for (int i = 0; i < states.length; i ++ )
            s += states[i];
        
        if (s.indexOf(String.valueOf(VolumeDefs.REP_STAT_OFFLINE)) == -1)
            status += VolumeDefs.REP_STAT_OFFLINE;
        
        if (s.indexOf(String.valueOf(VolumeDefs.REP_STAT_READONLY)) != -1)
            status += VolumeDefs.REP_STAT_READONLY;

        logger.debug("Status: " + status);
        rep.setState(status);
        rep.store(entidad);
        session.removeAttribute("objRep");
        return type;
    }

    public int loadData(RepositoryForm repositoryForm, int id, HttpSession session, String entidad) throws Exception
    {
        Repository rep = ObjFactory.createRepository(0);
        rep.load(id, entidad);
        
        session.setAttribute("objRep", rep);
                
        repositoryForm.setName(rep.getName());
        repositoryForm.setId(id);
        repositoryForm.setDescription(rep.getRemarks());
        

        int type = rep.getType();
        int vdefs = VolumeDefs.REP_TYPE_FTP;
        int vdefs2 = VolumeDefs.REP_TYPE_PFS;
        if (VolumeDefs.REP_TYPE_FTP == type ){
            repositoryForm.setType("FTP");
            int os = rep.getOs();
            switch (os){
            	case VolumeDefs.OS_NT:
            	    repositoryForm.setOs("Windows NT");
            	    break;
            	case VolumeDefs.OS_UNIX:
            	    repositoryForm.setOs("Unix");
            	    break;
            	case VolumeDefs.OS_WINDOWS:
            	    repositoryForm.setOs("Windows");
            	    break;
            }
            String server = rep.getServer();
            if (server.indexOf('.') != -1 ) // tiene una direccion ip
                repositoryForm.setTypeServer(1);
            else
                repositoryForm.setTypeServer(2);
            
            repositoryForm.setServer(server);
            int port = rep.getPort();
            repositoryForm.setPort(port);
            String userName = rep.getUser();
            repositoryForm.setUser(userName);
            String userPwd = rep.getPassword();
            repositoryForm.setPwd(userPwd);
            repositoryForm.setPath(rep.getPath());
        }
        else if (VolumeDefs.REP_TYPE_PFS == type){ // Detalle si es pfs
            repositoryForm.setType("PFS");
            repositoryForm.setPath(rep.getPath());
        }
        else if (VolumeDefs.REP_TYPE_DB == type)
        	repositoryForm.setType("DB");
        else if (VolumeDefs.REP_TYPE_DB == type)
        	repositoryForm.setType("CENTERA");
        
        int status = rep.getState();
        String states[] = repositoryForm.getStates();
        int i = 0;
        if ( (status & VolumeDefs.REP_STAT_OFFLINE) != VolumeDefs.REP_STAT_OFFLINE ) {
            states[i++] = String.valueOf(VolumeDefs.REP_STAT_OFFLINE);
        }
        if ( (status & VolumeDefs.REP_STAT_READONLY) == VolumeDefs.REP_STAT_READONLY ) {
            states[i++] = String.valueOf(VolumeDefs.REP_STAT_READONLY);
        }        

        
        
        return type;
    }
    public String getUserNameById(int id, String entidad) throws Exception
    {
        User u = ObjFactory.createUser();
        u.load(id, entidad);
        String name = u.getName();
        return name;
    }    

}
