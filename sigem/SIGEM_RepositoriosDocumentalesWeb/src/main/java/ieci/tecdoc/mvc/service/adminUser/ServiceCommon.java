/*
 * Created on 26-abr-2005
 *
 */
package ieci.tecdoc.mvc.service.adminUser;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.user.BasicUser;
import ieci.tecdoc.idoc.admin.api.user.BasicUsers;
import ieci.tecdoc.idoc.admin.api.user.Department;
import ieci.tecdoc.idoc.admin.api.user.Group;
import ieci.tecdoc.idoc.admin.api.user.LdapUser;
import ieci.tecdoc.idoc.admin.api.user.User;
import ieci.tecdoc.idoc.admin.api.volume.VolumeList;
import ieci.tecdoc.idoc.admin.api.volume.VolumeLists;
import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.sbo.acs.base.AcsPermission;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

/**
 * @author Antonio María
 *
 */
public class ServiceCommon {
    
    private static final Logger logger   = Logger.getLogger(ServiceCommon.class);
    
    public static List getAdminUsers(int id, int type, String entidad) throws Exception{
        List adminUsers = new ArrayList();
        BasicUsers managerUsers= null;
        BasicUser managerUser = null;
        switch (type)
        {
        	case Constantes.DEPARTAMENT:
        	    Department dept = ObjFactory.createDepartment(id, Defs.NULL_ID);
            	// dept.load(id); ya parece que no hace falta
            	managerUsers = dept.getAdminUsers(entidad);
        	    break;
        	case Constantes.GROUP:
        	    Group group = ObjFactory.createGroup();
        		//group.load(id);
        		managerUsers = group.getAdminUsers(entidad);
        		break;
        }
        for (int i = 0; i < managerUsers.count(); i ++){
            managerUser = managerUsers.get(i);
            adminUsers.add(new LabelValueBean( managerUser.getName(), String.valueOf(managerUser.getId())) );
        }
        return adminUsers;
    }
    
    public static String getUserNameById(int id, boolean isLdap, String entidad) throws Exception
    {
        String name = new String();
        if (id == Defs.SYSSUPERUSER_ID)
            name = Defs.SYSSUPERUSER_NAME;
        else if (id != Defs.NULL_ID)
        {
	        if (!isLdap){
		        User user = ObjFactory.createUser();
		        
		        try{
		            user.load(id, entidad);
		            name = user.getName();
		        }
		        catch (IeciTdException e){}
	        }
	        else {
	            LdapUser ldapUser = ObjFactory.createLdapUser(Defs.NULL_ID);
	            try{
	                ldapUser.load(id, entidad);
		            name = ldapUser.getFullName();
	            }catch (IeciTdException e){}
	        }
	    }
        return name;
        
    }
    public static String getNameDeptById(int id, String entidad) throws Exception
    {
        
        Department dep = ObjFactory.createDepartment();
        dep.load(id, entidad);
        String name = dep.getName();
        return name;
    }
    public static String getNameGroupById(int id, String entidad) throws Exception
    {
        
        Group grupo = ObjFactory.createGroup();
        grupo.load(id, entidad); 
        String name = grupo.getName();
        return name;
    }
    
    public static int getManagerId(int id, int type, String entidad) throws Exception
    {
        int managerId = Defs.NULL_ID;
        switch (type)
        {
        	case Constantes.DEPARTAMENT:
        	    Department dept = ObjFactory.createDepartment(Defs.NULL_ID, Defs.NULL_ID);
            	dept.load(id, entidad);
            	managerId = dept.getManagerId();
        	    break;
        	case Constantes.GROUP:
        	    Group group = ObjFactory.createGroup();
        		group.load(id, entidad);
        		managerId = group.getManagerId();
        		break;
        }
        return managerId;
        
    }
    
    public static Map getBasicProperties(int id, int type, String entidad) throws Exception
    {
        Map map = new HashMap();
        int managerId = Defs.NULL_ID;
        String name = "";
        switch (type)
        {
        	case Constantes.DEPARTAMENT:
        	    Department dept = ObjFactory.createDepartment(Defs.NULL_ID, Defs.NULL_ID);
            	dept.load(id, entidad);
            	managerId = dept.getManagerId();
            	name = dept.getName();
        	    break;
        	case Constantes.GROUP:
        	    Group group = ObjFactory.createGroup();
        		group.load(id, entidad);
        		managerId = group.getManagerId();
        		name = group.getName();
        		break;
        }
        map.put("managerId", new Integer(managerId));
        map.put("name", name);
        return map;
    }
    
    public static String getNameListById(int id, String entidad) throws Exception
    {
        String name = new String();
        VolumeList volumeList = ObjFactory.createVolumeList(Defs.NULL_ID);
        if (id != Defs.NULL_ID )
        {
        try {
            volumeList.load(id, entidad);
            name = volumeList.getName();
           }catch(Exception e){}
        }
       return name;    
    }
    
    public static List getVolumeListCollection(String entidad) throws Exception // obtener la lista de listas de volumenes
    {
        List lista = new ArrayList();
        VolumeLists volumeLists = new VolumeLists();

        volumeLists.load(entidad);
        int n = volumeLists.count();
        for (int i = 0; i < n; i ++ )
        {
            ieci.tecdoc.idoc.admin.api.volume.VolumeList obj = volumeLists.getVolumeList(i);
            LabelValueBean tmp = new LabelValueBean(obj.getName(), String.valueOf(obj.getId()));
            lista.add(tmp);
            
        }
        return lista;
    }
    public static int [] getPermmissionArray(int perm)
    {
        int permissionArray[] = new int[5];
        int i = 0;
        if ( (perm & AcsPermission.QUERY)  == AcsPermission.QUERY)
            permissionArray[i++] = AcsPermission.QUERY;
        if (( perm & AcsPermission.UPDATE)  == AcsPermission.UPDATE )
            permissionArray[i++] = AcsPermission.UPDATE;
        if (( perm & AcsPermission.CREATION)  == AcsPermission.CREATION )
            permissionArray[i++] = AcsPermission.CREATION;
        if (( perm & AcsPermission.DELETION)  == AcsPermission.DELETION )
            permissionArray[i++] = AcsPermission.DELETION;
        if (( perm & AcsPermission.PRINTING)  == AcsPermission.PRINTING )
            permissionArray[i++] = AcsPermission.PRINTING;        
            
        return permissionArray;
    }
    
    public static int getPermission(int permmissionArray [] )
    {
        int perm = 0;
        for (int i = 0; i < permmissionArray.length; i ++ )
            perm += permmissionArray[i];
        return perm;
    }
    
    public static int [] difference(int [] v1, int [] v2)
    {

        TreeSet resul = new TreeSet();
        int n = v1.length;
        int i;
        for (i = 0; i < n; i ++)
        {
            int e = v1[i];
            if (!search (e, v2))
                resul.add( new Integer(e) );
        }
        int nresul = resul.size();
        
        int r[] =  new int[nresul];
        Iterator it = resul.iterator();
        i = 0; 
        while (it.hasNext())
        {
            r[i] = ((Integer)it.next()).intValue();
            i++;
        }
        return r;
    }
    private static boolean search (int e, int [] v)
    {
        boolean enc = false;
        int n = v.length;
        int i = 0; 
        while (i < n  && !enc)
        {
            if (v[i] == e )
                enc = true;
            i++;
        }
        return enc;
    }
    public static String formatNumberToMb(String n) {
        n = n.replaceAll(",",".");
        double num = Double.parseDouble(n);
	    double tamKb = num * Math.pow(2,-20);
	    
	    String pattern = "###,###.##";
        DecimalFormat formatter = new DecimalFormat(pattern);
        String formatString = formatter.format(tamKb);
        return formatString;
    }    

}
