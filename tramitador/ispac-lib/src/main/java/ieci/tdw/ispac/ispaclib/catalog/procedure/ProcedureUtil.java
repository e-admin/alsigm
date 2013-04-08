/*
 * invesflow Java - ISPAC
 * $Source: /TEST/SIGEM/ispaclib/src/ieci/tdw/ispac/ispaclib/catalog/procedure/ProcedureUtil.java,v $
 * $Revision: 1.12 $
 * $Date: 2008/09/30 07:03:05 $
 * $Author: davidfa $
 *
 */
package ieci.tdw.ispac.ispaclib.catalog.procedure;

import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.graph.GInfo;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.StageElement;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.TaskElement;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PEntidadDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PEventoDAO;
import ieci.tdw.ispac.ispaclib.dao.security.PermissionDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.messages.MessagesFormatter;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * ProcedureUtil
 *
 *
 * @version $Revision: 1.12 $ $Date: 2008/09/30 07:03:05 $
 */
public final class ProcedureUtil
{
	/** Nombre del fichero de recursos. */
	private static final String BUNDLE_NAME = 
		"ieci.tdw.ispac.ispaclib.catalog.procedure.ExportImportProcedureResources";
	
	/** Recursos. */
	//private static final Map RESOURCE_BUNDLES = new HashMap();
	
	/*
	private static ResourceBundle getResourceBundle(String language) {
		
		ResourceBundle resourceBundle = (ResourceBundle) RESOURCE_BUNDLES.get(language);
		if (resourceBundle == null) {
			
			resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale(language));
			RESOURCE_BUNDLES.put(language, resourceBundle);
		}
		
		return resourceBundle;
	}
	*/
	
    static public List duplicate(DbCnt cnt,List objdaolist)
    throws ISPACException
    {
        if (objdaolist==null)
            return null;

        List duplist=new ArrayList();
        Iterator it=objdaolist.iterator();
        while (it.hasNext())
        {
            ObjectDAO objdao = (ObjectDAO) it.next();
            duplist.add(objdao.duplicate(cnt));
        }
        return duplist;
    }
    
    static public void duplicatePcdEntityEvents(DbCnt cnt, List entitylist, List newentitylist)
    throws ISPACException
    {
        if ((entitylist != null) &&
            (!entitylist.isEmpty())) {
        	
        	int indexNewentitylist = 0;
        	Iterator it = entitylist.iterator();
        	while (it.hasNext()) {
        		
        		PEntidadDAO pEntidadDAO = (PEntidadDAO) it.next();
        		
        		// Id de la nueva entidad en el procedimiento
        		int idObj = ( (PEntidadDAO) newentitylist.get(indexNewentitylist)).getKeyInt();
        		
        		// Eventos de la entidad en el procedimiento
        		CollectionDAO entityEvents = PEventoDAO.getEvents(cnt, EventsDefines.EVENT_OBJ_ENTITY, pEntidadDAO.getKeyInt());
        		while (entityEvents.next()) {
        			
        			PEventoDAO pEventoDAO = (PEventoDAO) entityEvents.value();
        			
        			// Duplicar el evento
        			PEventoDAO newPEventoDAO = new PEventoDAO(cnt);
        			newPEventoDAO.createNew(cnt);
        			newPEventoDAO.set("ID_OBJ", idObj);
        			newPEventoDAO.set("TP_OBJ", pEventoDAO.getInt("TP_OBJ"));
        			newPEventoDAO.set("EVENTO", pEventoDAO.getInt("EVENTO"));
        			newPEventoDAO.setOrderSequence(cnt);
        			newPEventoDAO.set("ID_REGLA", pEventoDAO.getInt("ID_REGLA"));
        			newPEventoDAO.set("CONDICION", pEventoDAO.getString("CONDICION"));
        			newPEventoDAO.store(cnt);
        		}

        		indexNewentitylist++;
        	}        	
        }
    }

    static public List duplicatePcdElement(DbCnt cnt,List objdeflist)
    throws ISPACException
    {
        if (objdeflist==null)
            return null;

        List duplist=new ArrayList();
        Iterator it=objdeflist.iterator();
        while (it.hasNext())
        {
            IPcdElement objdef = (IPcdElement) it.next();
            duplist.add(objdef.duplicate(cnt));
        }
        return duplist;
    }

    static public Map duplicatePcdElement(DbCnt cnt,Map objdefmap)
    throws ISPACException
    {
        if (objdefmap==null)
            return null;

        LinkedHashMap dupmap=new LinkedHashMap();
        Iterator it=objdefmap.values().iterator();
        while (it.hasNext())
        {
            IPcdElement objdef = (IPcdElement) it.next();
            IPcdElement dupdef = objdef.duplicate(cnt);
            dupmap.put(dupdef.getIntegerId(),dupdef);
        }
        return dupmap;
    }
    
    static public Map duplicateStageElement(DbCnt cnt,Map objdefmap,Map origidxdupmap,List newpcdstageslist, Map ctstagestask)
    throws ISPACException
    {
        if (objdefmap==null || origidxdupmap==null)
            return null;
        
        PcdElementFactory pcdfactory = new PcdElementFactory();
        LinkedHashMap dupmap=new LinkedHashMap();

        // Fases del nuevo procedimiento / actividades del nuevo subproceso      
        if (newpcdstageslist != null) {
        	
        	Iterator it = newpcdstageslist.iterator();
        	while (it.hasNext()) {
        		
        		Integer ctStageId = null;
        		String stageName = null;
        		
        		Object objstage = it.next();
        		
        		if (objstage instanceof String) {
        			// Actividad
        			stageName = (String) objstage;
        		}
        		else {
        			// Fase
        			IItem item = (IItem) objstage;
        			ctStageId = item.getKeyInteger();
        			stageName = item.getString("NOMBRE");
        		}
        		
        		StageElement stageElement = findStageByName(objdefmap, stageName);
        		// Fase del procedimiento original / actividad del subproceso original
        		if (stageElement != null) {
        			
        			List cttaskidlist = null;
                    
        			// Trámites de la fase
        			if ((ctstagestask != null) && 
        				(ctStageId.intValue() != ISPACEntities.ENTITY_NULLREGKEYID)) {
        				
	                    cttaskidlist = (List) ctstagestask.get(ctStageId);
        			}
        			
        			IPcdElement dupdef = stageElement.duplicate(cnt, cttaskidlist);
        			
                    dupmap.put(dupdef.getIntegerId(),dupdef);
                    origidxdupmap.put(stageElement.getIntegerId(),dupdef);
        		}
        		// Nueva fase añadida / nueva actividad creada
        		else {
        			if (ctStageId != null) {
        				stageElement = pcdfactory.createStage(cnt, ctStageId.intValue(), new GInfo());
        				
            			// Trámites de la fase
            			if (ctstagestask != null) {
            				
    	                    List cttaskidlist = (List) ctstagestask.get(ctStageId);
    	                    if (cttaskidlist != null) {
    	                    	
    	                    	stageElement.modify(cnt, cttaskidlist);
    	                    }
            			}
        			}
        			else {
        				stageElement = pcdfactory.createStage(cnt, 0, -1, stageName, new GInfo());
        			}

        			dupmap.put(stageElement.getIntegerId(),stageElement);
        		}
        	}
        }
        
        // Duplicar el resto de fases / actividades para que la modificación
        // reajuste los flujos para los elementos eliminados
        Iterator it=objdefmap.values().iterator();
        while (it.hasNext())
        {
            IPcdElement objdef = (IPcdElement) it.next();
            
            if (!origidxdupmap.containsKey(objdef.getIntegerId())) {
            	
	            IPcdElement dupdef = objdef.duplicate(cnt);
	            dupmap.put(dupdef.getIntegerId(),dupdef);
	
	            origidxdupmap.put(objdef.getIntegerId(),dupdef);
            }
        }
        return dupmap;
    }
    
	public static Map duplicateTaskElements(DbCnt cnt, Map taskdefmap)
			throws ISPACException {

		if (taskdefmap == null) {
			return null;
		}

		Map<Integer, TaskElement> newTasksMap = new LinkedHashMap<Integer, TaskElement>();
		Map<Integer, Integer> idsMap = new HashMap<Integer, Integer>();

		Iterator<TaskElement> taskIterator = taskdefmap.values().iterator();
		while (taskIterator.hasNext()) {
			
			TaskElement oldTask = taskIterator.next();
			TaskElement newTask = (TaskElement) oldTask.duplicate(cnt);
			
			newTasksMap.put(newTask.getIntegerId(), newTask);
			idsMap.put(oldTask.getIntegerId(), newTask.getIntegerId());
		}
		
		// Actualizar el identificador del trámite padre en las dependencias entre trámites
		taskIterator = newTasksMap.values().iterator();
		while (taskIterator.hasNext()) {
			TaskElement task = taskIterator.next();
			task.updateDependencies(cnt, idsMap);
		}
		
		return newTasksMap;
	}
    
    static StageElement findStageByName(Map stagedefmap, String stageName) 
	throws ISPACException
	{
    	StageElement stage = null;

    	if (StringUtils.isNotBlank(stageName)) {
    		Iterator stages = stagedefmap.values().iterator();
    		while ((stage == null) && stages.hasNext()) {
    			StageElement aux = (StageElement) stages.next();
    			if (stageName.equals(aux.getName())) {
    				stage = aux;
    			}
    		}
    	}

    	return stage;
	}

    static public Map duplicatePcdElement(DbCnt cnt,Map objdefmap,Map origidxdupmap)
    throws ISPACException
    {
        if (objdefmap==null || origidxdupmap==null)
            return null;
        
        LinkedHashMap dupmap=new LinkedHashMap();
        Iterator it=objdefmap.values().iterator();
        while (it.hasNext())
        {
            IPcdElement objdef = (IPcdElement) it.next();
            IPcdElement dupdef = objdef.duplicate(cnt);
            dupmap.put(dupdef.getIntegerId(),dupdef);

            origidxdupmap.put(objdef.getIntegerId(),dupdef);
        }
        return dupmap;
    }
    
    
    public static List duplicateOtherPermission(List otherpermissionlist) {
		List newotherpermissionlist = new ArrayList();
		Iterator itr = otherpermissionlist.iterator();

		while (itr.hasNext()) {
			PermissionDAO permissionDao = (PermissionDAO) itr.next();
			permissionDao.setNewObject();
			newotherpermissionlist.add(permissionDao);

		}
		return newotherpermissionlist;
	}

    static public void setPcdElementProperty(Map pcdobjmap,Number pcdId,Number stagePcdId,Number taskPcdId)
    throws ISPACException
    {
        if (pcdobjmap==null)
            return;
        setPcdElementProperty(pcdobjmap.values(),pcdId,stagePcdId,taskPcdId);
    }

    static public void setPcdElementProperty(Collection objdefcol,Number pcdId,Number stagePcdId,Number taskPcdId)
    throws ISPACException
    {
        if (objdefcol==null)
            return;

        Iterator it=objdefcol.iterator();
        while (it.hasNext())
        {
            IPcdElement objdef = (IPcdElement) it.next();
            if (pcdId!=null)
                objdef.setProcedureId(pcdId.intValue());
            if (stagePcdId!=null)
                objdef.setStagePcdId(stagePcdId.intValue());
            if (taskPcdId!=null)
                objdef.setTaskPcdId(taskPcdId.intValue());
        }
    }

    static public void setProperty(List objdaolist,String property,Object value)
    throws ISPACException
    {
        if (objdaolist==null)
            return;

        Iterator it=objdaolist.iterator();
        while (it.hasNext())
        {
            ObjectDAO objdao = (ObjectDAO) it.next();
            objdao.set(property,value);
        }
    }

    static public void store(DbCnt cnt,Collection objdaolist)
    throws ISPACException
    {
        if (objdaolist==null)
            return;

        Iterator it=objdaolist.iterator();
        while (it.hasNext())
        {
            ObjectDAO objdao = (ObjectDAO) it.next();
            objdao.store(cnt);
        }
    }
    static public void storePcdElement(DbCnt cnt,Map objdefmap)
    throws ISPACException
    {
        if(objdefmap==null)
            return;
        storePcdElement(cnt,objdefmap.values());
    }
    static public void storePcdElement(DbCnt cnt,Collection objdeflist)
    throws ISPACException
    {
        if (objdeflist==null)
            return;

        Iterator it=objdeflist.iterator();
        while (it.hasNext())
        {
            IPcdElement objdef = (IPcdElement) it.next();
            objdef.store(cnt);
        }
    }


    static public void delete(DbCnt cnt,Collection objdaolist)
    throws ISPACException
    {
        if (objdaolist==null)
            return;

        Iterator it=objdaolist.iterator();
        while (it.hasNext())
        {
            ObjectDAO objdao = (ObjectDAO) it.next();
            objdao.delete(cnt);
        }
    }


    static public void deletePcdElement(DbCnt cnt,Map objdefmap)
    throws ISPACException
    {
        if (objdefmap==null)
            return;
        deletePcdElement(cnt,objdefmap.values());
    }

    static public void deletePcdElement(DbCnt cnt,Collection objdeflist)
    throws ISPACException
    {
        if (objdeflist==null)
            return;

        Iterator it=objdeflist.iterator();
        while (it.hasNext())
        {
            IPcdElement objdef = (IPcdElement) it.next();
            objdef.delete(cnt);
        }
    }


    static public Map indexPcdElementByCatalogId(Map objdefmap)
    throws ISPACException
    {
        if (objdefmap==null)
            return null;

        LinkedHashMap dupmap=new LinkedHashMap();
        Iterator it=objdefmap.values().iterator();
        while (it.hasNext())
        {
            IPcdElement objdef = (IPcdElement) it.next();
            dupmap.put(objdef.getCatalogIntegerId(),objdef);
        }
        return dupmap;
    }

    static public void removePcdElement(Map origmap,Collection delitems)
    throws ISPACException
    {
        if (origmap==null)
            return;

        if (delitems==null)
            return;

        Iterator it=delitems.iterator();
        while (it.hasNext())
        {
            IPcdElement item = (IPcdElement) it.next();
            origmap.remove(item.getIntegerId());
        }
    }
    
	/**
	 * Obtiene el texto del recurso especificado.
	 * @param language Idioma.
	 * @param key Clave del texto.
	 * @return Texto.
	 */
	public static String getString(String language, String key) {
		try {
			//return getResourceBundle(language).getString(key);
			return ResourceBundle.getBundle(BUNDLE_NAME, new Locale(language)).getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Obtiene el texto del recurso especificado.
	 * @param language Idioma.
	 * @param key Clave del texto.
	 * @param params Parámetros para sustituir en el texto.
	 * @return Texto.
	 */
	public static String getString(String language, String key, Object [] params) {
		
		try {
			//String text = getResourceBundle(language).getString(key);
			String text = getString(language, key);
			if ((text != null) && (text.length() > 0)) {
				text = MessagesFormatter.format(text, params);
			}
			return text;
		}
		catch (MissingResourceException e) {
			return key;
		}
	}
		
	public static void generateLog(StringBuffer buffer, String language, String key, Object [] params, boolean underline) {
		
		String log = "";
		if (params != null) {
			log = getString(language, key, params);
		}
		else {
			log = getString(language, key);
		}
		buffer.append(log).append(ExportProcedureMgr.RETORNO);
		
		if (underline) {
			for (int i = 0; i < log.length(); i++) buffer.append("_");
			buffer.append(ExportProcedureMgr.RETORNO)
				  .append(ExportProcedureMgr.RETORNO);
		}
	}
	
	public static void generateLog(StringBuffer buffer, String language, String key, Object [] params) {
		generateLog(buffer, language, key, params, false);
	}

	public static void generateLog(StringBuffer buffer, String language, String key, boolean underline) {
		generateLog(buffer, language, key, null, underline);
	}
	
	public static void generateLog(StringBuffer buffer, String language, String key) {
		generateLog(buffer, language, key, null, false);
	}

}