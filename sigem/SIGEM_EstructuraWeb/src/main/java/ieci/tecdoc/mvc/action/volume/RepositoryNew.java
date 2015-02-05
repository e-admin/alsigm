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
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.SessionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Antonio María
 *  
 */
public class RepositoryNew extends BaseAction {

    /*
     * (non-Javadoc)
     * 
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(RepositoryNew.class);

protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    
	String entidad=SessionHelper.getEntidad(request);
	
	RepositoryForm repositoryForm= (RepositoryForm) form;
        String step = request.getParameter("step");
        int type = -1;
        if (step == null ){
            
            StateListDTO stateListDTO = new StateListDTO();
            stateListDTO.setStates(getRepositoryAtts());
            request.setAttribute("statesRepositoryList", stateListDTO );    
            request.setAttribute("typeCollection", getRepositoryTypeList());
            return mapping.findForward("ok_1"); // Mandar a Repositorio Nuevo -
                                                // Paso 1 de 2
        }
        else if ( step.equals("1"))
        {
            String typeObj = repositoryForm.getType();
            type = Integer.parseInt(typeObj);
            String states[] =repositoryForm.getStates();
            request.setAttribute("nombreRepositorio", repositoryForm.getName());
            request.getSession(false).setAttribute("states", states);
            
            if (type == VolumeDefs.REP_TYPE_FTP){
                request.setAttribute("typeServerCollection", getRepositoryTypeServerList());
                request.setAttribute("plataformCollection", getRepositoryPlataformList());
            }
            if (type != VolumeDefs.REP_TYPE_DB && type != VolumeDefs.REP_TYPE_CENTERA) // Si es centera o db, saltarse el paso 2
            	return mapping.findForward("ok_2"); // Mandar a Repositorio Nuevo -
                                                // Paso 1 de 2
        }
        
        if (step.equals("2") || ( type == VolumeDefs.REP_TYPE_DB || type == VolumeDefs.REP_TYPE_CENTERA ) )
        {
            String states[] = (String[]) request.getSession(false).getAttribute("states");
            repositoryForm.setStates(states);
            request.getSession().removeAttribute("states");
            createRepository(repositoryForm, entidad);
            logger.debug("Creando repositorio");
        }
      
        postGlobalMessage("message.repository.new", request);
        request.setAttribute("tipo", new Byte(Constantes.REPOSITORIES));
        
        return mapping.findForward("success");
        
    }    /**
 * @param repositoryForm
 */
private void createRepository(RepositoryForm repositoryForm, String entidad) throws Exception{
    
    Repository rep = ObjFactory.createRepository(0);
    rep.setName(repositoryForm.getName());
    
    int type =Integer.parseInt(repositoryForm.getType());
    rep.setType(type);
    if (type == VolumeDefs.REP_TYPE_FTP){
	    rep.setOs(Integer.parseInt(repositoryForm.getOs()));
	    rep.setPassword(repositoryForm.getPwd());
	    rep.setPort(repositoryForm.getPort());
	    rep.setRemarks(repositoryForm.getDescription());
	    rep.setServer(repositoryForm.getServer());
    }
    if (type == VolumeDefs.REP_TYPE_FTP || type == VolumeDefs.REP_TYPE_PFS)
    	rep.setPath(repositoryForm.getPath());
    String states[] = repositoryForm.getStates();
    if (states != null)
        repositoryForm.setStates_((String []) states.clone());
    else
        states = repositoryForm.getStates_();
    
    int status = 0;
    String s = new String();
    for (int i = 0; i < states.length; i++)
        s += states[i];

    if (s.indexOf(String.valueOf(VolumeDefs.REP_STAT_OFFLINE)) == -1)
        status += VolumeDefs.REP_STAT_OFFLINE;

    if (s.indexOf(String.valueOf(VolumeDefs.REP_STAT_READONLY)) != -1)
        status += VolumeDefs.REP_STAT_READONLY;

    rep.setState(status);
    
    
    rep.setUser(repositoryForm.getUser());
    
    rep.store(entidad);
    
}
    /**
          * @param repositoryForm
          * @param id
          */
    private void saveData(RepositoryForm repositoryForm, int id,
            HttpSession session, String entidad) throws Exception {

        Repository rep = (Repository) session.getAttribute("objRep");

        //if (rep == null ){
        rep = ObjFactory.createRepository(0);
        rep.load(id, entidad);
        //}

        rep.setName(repositoryForm.getName());
        rep.setRemarks(repositoryForm.getDescription());
        rep.setServer(repositoryForm.getServer());
        rep.setUser(repositoryForm.getUser());
        rep.setPassword(repositoryForm.getPwd());

        String states[] = repositoryForm.getStates();
        int status = 0;

        String s = new String();
        for (int i = 0; i < states.length; i++)
            s += states[i];

        if (s.indexOf(String.valueOf(VolumeDefs.REP_STAT_OFFLINE)) == -1)
            status += VolumeDefs.REP_STAT_OFFLINE;

        if (s.indexOf(String.valueOf(VolumeDefs.REP_STAT_READONLY)) != -1)
            status += VolumeDefs.REP_STAT_READONLY;

        logger.debug("Status: " + status);
        rep.setState(status);
        rep.store(entidad);
        session.removeAttribute("objRep");
    }

    public int loadData(RepositoryForm repositoryForm, int id,
            HttpSession session, String entidad) throws Exception {
        Repository rep = ObjFactory.createRepository(0);
        rep.load(id, entidad);

        session.setAttribute("objRep", rep);

        repositoryForm.setName(rep.getName());
        repositoryForm.setId(id);
        repositoryForm.setDescription(rep.getRemarks());

        int type = rep.getType();
        int vdefs = VolumeDefs.REP_TYPE_FTP;
        int vdefs2 = VolumeDefs.REP_TYPE_PFS;
        if (VolumeDefs.REP_TYPE_FTP == type) {
            repositoryForm.setType("FTP");
            int os = rep.getOs();
            switch (os) {
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
            if (server.indexOf('.') != -1) // tiene una direccion ip
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
        } else if (VolumeDefs.REP_TYPE_PFS == type) { // Detalle si es pfs
            repositoryForm.setType("PFS");

        }
        repositoryForm.setPath(rep.getPath());

        int status = rep.getState();
        String states[] = repositoryForm.getStates();
        int i = 0;
        if ((status & VolumeDefs.REP_STAT_OFFLINE) != VolumeDefs.REP_STAT_OFFLINE) {
            states[i++] = String.valueOf(VolumeDefs.REP_STAT_OFFLINE);
        }
        if ((status & VolumeDefs.REP_STAT_READONLY) == VolumeDefs.REP_STAT_READONLY) {
            states[i++] = String.valueOf(VolumeDefs.REP_STAT_READONLY);
        }

        return type;
    }

    public String getUserNameById(int id, String entidad) throws Exception {
        User u = ObjFactory.createUser();
        u.load(id, entidad);
        String name = u.getName();
        return name;
    }

}