/*
 * Created on 04-may-2005
 *
 */
package ieci.tecdoc.mvc.action.archive;

import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.archive.Archives;
import ieci.tecdoc.idoc.admin.api.archive.BasicArchive;
import ieci.tecdoc.idoc.admin.api.archive.BasicArchives;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.dto.common.ListDTO;
import ieci.tecdoc.mvc.util.SessionHelper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

/**
 * @author Antonio María
 *
 */
public class ArchiveList extends BaseAction {

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(ArchiveList.class);
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	String entidad=SessionHelper.getEntidad(request);
        String dirId = request.getParameter("id");
        
        Archives archives = ObjFactory.createArchives();
        int id = Integer.parseInt(dirId);
        BasicArchives basicArchives =  archives.getArchivesByDirectory(id, entidad);
        int n = basicArchives.count();
        BasicArchive basicArchive = null;
        ListDTO listDTO = new ListDTO();
        List archiveList = new ArrayList();
        
        
        for (int i = 0; i < n; i ++){
            basicArchive = basicArchives.get(i);
            int _id = basicArchive.getId();
            String _name = basicArchive.getName();
            archiveList.add(new LabelValueBean(_name, String.valueOf(_id)));
        }
        listDTO.setList(archiveList);
        request.setAttribute("archivesList", listDTO);
        request.getSession().setAttribute("archivesList", listDTO);
        request.setAttribute("dirId", new Integer(id));
        
        return mapping.findForward("success");
    }

}
