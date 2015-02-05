/*
 * Created on 05-may-2005
 *
 */
package ieci.tecdoc.mvc.service;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.core.types.IeciTdType;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.archive.Archive;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveAccess;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveFld;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveFlds;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveIdx;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveIdxs;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveMisc;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveUpdInfo;
import ieci.tecdoc.idoc.admin.api.archive.Directory;
import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.exception.ArchiveErrorCodes;
import ieci.tecdoc.idoc.admin.api.exception.GroupErrorCodes;
import ieci.tecdoc.idoc.admin.api.exception.UserErrorCodes;
import ieci.tecdoc.idoc.admin.api.user.BasicUser;
import ieci.tecdoc.idoc.admin.api.user.BasicUsers;
import ieci.tecdoc.idoc.admin.api.user.Department;
import ieci.tecdoc.idoc.admin.api.user.Group;
import ieci.tecdoc.idoc.admin.api.user.LdapGroup;
import ieci.tecdoc.idoc.admin.api.user.LdapUser;
import ieci.tecdoc.idoc.admin.api.user.Permission;
import ieci.tecdoc.idoc.admin.api.user.Permissions;
import ieci.tecdoc.idoc.admin.api.user.User;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.idoc.admin.api.user.UserProfiles;
import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.mvc.dto.archive.Advanced;
import ieci.tecdoc.mvc.dto.archive.Field;
import ieci.tecdoc.mvc.dto.archive.Fields;
import ieci.tecdoc.mvc.dto.archive.Folder;
import ieci.tecdoc.mvc.dto.archive.General;
import ieci.tecdoc.mvc.dto.archive.Index;
import ieci.tecdoc.mvc.dto.archive.Indexs;
import ieci.tecdoc.mvc.dto.common.AccessDTO;
import ieci.tecdoc.mvc.dto.common.DeptAccessDTO;
import ieci.tecdoc.mvc.dto.common.GroupAccessDTO;
import ieci.tecdoc.mvc.dto.common.UserAccessDTO;
import ieci.tecdoc.mvc.error.MvcError;
import ieci.tecdoc.mvc.form.archive.DirForm;
import ieci.tecdoc.mvc.service.adminUser.ServiceCommon;
import ieci.tecdoc.mvc.service.adminUser.ldap.ServiceLdap;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.NaryTree;
import ieci.tecdoc.mvc.util.Node;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveVolListType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

/**
 * @author Antonio María
 *
 */
public class ServiceArchive {
    
    private static Logger logger = Logger.getLogger(ServiceArchive.class);
    Archive archive;
    Directory directory;
    ArchiveMisc archiveMisc;
    
    General general;
    Fields fields;
    Indexs indexs;
    Advanced advanced;    
    Folder folder;
    ArchiveUpdInfo updInfo;
    
    ArrayList dFldsId;
    ArrayList dIdxsId;
    
    List deletedFieldList;
    boolean isLdap;

    
    public ServiceArchive(Archive archive, boolean isLdap){
        this.archive = archive;
        updInfo = ObjFactory.createArchiveUpdInfo(archive.getName(),archive.getRemarks(),archive.getAdminUserId(),
                archive.isFtsInContents(),archive.getFldsDef(),archive.getIdxsDef(),archive.getMiscDef());       
        archiveMisc = archive.getMiscDef();
        dFldsId = new ArrayList();
        dIdxsId = new ArrayList();
        deletedFieldList = new ArrayList();
        this.isLdap = isLdap;
    }
    public ServiceArchive(){
    }
    
    public General getGeneral(int option, String entidad) throws Exception
    {
        general = new General();
        
        int volListType = archiveMisc.getVolListType(); // solo edicion
        int volListId = archiveMisc.getVolListId();
        
        if ( option != Constantes.NEW )
        {
	        int id = archive.getId();
	        String name = archive.getName();
	        String description = archive.getRemarks();
	        SimpleDateFormat formatter;
	        boolean ftsInContents = archive.isFtsInContents();
	        boolean existsFdrsInArch = archive.existsFdrsInArch(entidad);
	        
	        String patron = "d-M-yyyy H.m.s";
	        formatter = new SimpleDateFormat(patron);
	        
	        Date creationDate = archive.getCreationDate();
	        int creatorId = archive.getCreatorId();
	        String creatorName = null;
	        if (creatorId != 0 && creatorId != Defs.NULL_ID)
	            creatorName = ServiceCommon.getUserNameById(creatorId, isLdap, entidad);
	        else if (creatorId == 0)
	            creatorName = Defs.SYSSUPERUSER_NAME;
	        
	        Date updateDate = archive.getUpdateDate();
	        int updaterId = archive.getUpdaterId();
	        String updaterName = new String();
	        int updaterIdAbs = Math.abs(updaterId);
	
	        if (updaterId != 0 && updaterIdAbs != Defs.NULL_ID)
	            updaterName = ServiceCommon.getUserNameById(updaterId, isLdap, entidad);
	        else if (updaterId == 0 )
	            updaterName = Defs.SYSSUPERUSER_NAME;
	
	        List adminUsers = getAdminUsers(true, entidad); // solo edicion
	        int adminUserId = archive.getAdminUserId(); // solo edicion
	        
	        
	        
	        
	        
	        
	        general.setId(id);
	        general.setDescription(description);
	        general.setName(name);
	        
	        general.setFtsInContents(ftsInContents);
	        general.setExistsFdrsInArch(existsFdrsInArch);
	        
	        general.setCreationDate(creationDate, formatter);
	        general.setUpdateDate(updateDate, formatter);
	        
	        general.setCreatorName(creatorName);
	        general.setUpdaterName(updaterName);
	        
	        general.setAdminUsers(adminUsers);
	        general.setManagerId(adminUserId);
	        
	        
	        
        }
        // Lista de listas de volumenes
        List volumeListCollection = ServiceCommon.getVolumeListCollection(entidad);
        LabelValueBean tmp = new LabelValueBean("Ninguna", String.valueOf(Defs.NULL_ID));
        volumeListCollection.add(tmp);
        
        // Estatica o dinámica
        List volListTypeChoices = getVolListTypeChoices();
        
        general.setVolListTypeChoices(volListTypeChoices);
        general.setVolumeListCollection(volumeListCollection);
        
        // Lista de volumen asociada
        general.setVolListType(String.valueOf(volListType));
        general.setVolListId(volListId);
        
        return general;
    }
    
    public void loadDir(DirForm dirForm, boolean isLdap, int connectedUserId, boolean full, String entidad) throws Exception
    {
        int id = dirForm.getId();
        
        ArchiveAccess archiveAccess = ObjFactory.createArchiveAccess();
        
        boolean userCanEditDir = archiveAccess.userCanEditDir(connectedUserId, id, entidad);
        if (!userCanEditDir)
            throw new IeciTdException(String.valueOf(MvcError.EC_NOT_ENOUGH_PERMISSIONS), null);
        
        this.directory = ObjFactory.createDirectory(Defs.NULL_ID, Defs.NULL_ID, isLdap);
        directory.load(id, entidad);
        
        String name = directory.getName();
        String description = directory.getDescription();
        int adminUserId = directory.getAdminUserId();
        
        
        if (full) // Edicion
        {
            List adminUsers = getAdminUsers(false, entidad);
        	dirForm.setAdminUsers(adminUsers);
        }
        else // Propiedades
        {
            SimpleDateFormat formatter;
            String patron = "d-M-yyyy H.m.s";
            formatter = new SimpleDateFormat(patron);
            
            Date creationDate = directory.getCreationDate();
            int creatorId = directory.getCreatorId();
            String creatorName = null;
            if (creatorId != 0 && creatorId != Defs.NULL_ID)
                creatorName = ServiceCommon.getUserNameById(creatorId, isLdap, entidad);
            else if (creatorId == 0)
                creatorName = Defs.SYSSUPERUSER_NAME;
            
            Date updateDate = directory.getUpdateDate();
            int updaterId = directory.getUpdaterId();
            String updaterName = new String();
            if (updaterId != 0 && updaterId != Defs.NULL_ID)
                updaterName = ServiceCommon.getUserNameById(updaterId, isLdap, entidad);
            else if (updaterId == 0 )
                updaterName = Defs.SYSSUPERUSER_NAME;
            
            String managerName = null;
            if (adminUserId != 0 && adminUserId != Defs.NULL_ID)
                managerName = ServiceCommon.getUserNameById(updaterId, isLdap, entidad);
            else if (adminUserId == 0 )
                managerName = Defs.SYSSUPERUSER_NAME;
            
            dirForm.setManagerName(managerName);
            dirForm.setCreationDate(creationDate, formatter);
            dirForm.setUpdateDate(updateDate, formatter);
            
            dirForm.setCreatorName(creatorName);
            dirForm.setUpdaterName(updaterName);
            
        }
        
        dirForm.setName(name);
        dirForm.setDescription(description);
        dirForm.setAdminUserId(adminUserId);
        
    }
    public void saveDir(DirForm dirForm, boolean isNew, String entidad) throws Exception
    {
        String name = dirForm.getName();
        String description = dirForm.getDescription();
        int adminUserId = dirForm.getAdminUserId();
        
        directory.setName(name);
        directory.setAdminUserId(adminUserId);
        directory.setDescription(description);
        directory.store(entidad);
        if (isNew){
            directory.setAdminUserId(adminUserId);
            directory.store(entidad);
        }
            
    }
    public void newDir (DirForm dirForm, int userConnected, int parentId, boolean isLdap, boolean submitted, String entidad ) throws Exception
    {
        ArchiveAccess archiveAccess = ObjFactory.createArchiveAccess();
        boolean userCanCreateDir = archiveAccess.userCanCreateDir(userConnected, parentId, entidad);
        if (!userCanCreateDir)
            throw new IeciTdException(String.valueOf(MvcError.EC_NOT_ENOUGH_PERMISSIONS), null);
        
        this.directory = ObjFactory.createDirectory( userConnected, parentId, isLdap);
        List adminUsers = getAdminUsers(false, entidad);
    	dirForm.setAdminUsers(adminUsers);
    }
    public Fields getFields(int option )
    {
        fields = new Fields();
        List fldsList =  fields.getFldsList();
        // List fldsList = new ArrayList();
        
        if (option != Constantes.NEW ) {
	        ArchiveFlds archiveFlds = archive.getFldsDef();
	        int size = archiveFlds.count();
	        for (int i = 0; i < size; i ++ ){
	            ArchiveFld archiveFld = archiveFlds.get(i);
	            
	            int id = archiveFld.getId();
	            String name = archiveFld.getName();
	            int type = archiveFld.getType();
	            int len = archiveFld.getLen();
	            String colName = archiveFld.getColName();
	            
	            String description = archiveFld.getRemarks();
	            boolean mult = archiveFld.isMult();
	            boolean doc = archiveFld.isDoc();
	            boolean obligatory = ! archiveFld.isNullable();
	            
	            String length = new String ("-");
	            String typeToken = getTypeToken(type);
	            
	            if (type == IeciTdType.SHORT_TEXT || type == IeciTdType.LONG_TEXT)
	                length = String.valueOf(len);
	            
	            Field field = new Field();
	            field.setId(id);
	            field.setName(name);
	            field.setColName(colName);
	            field.setLength(length);
	            field.setTypeToken(typeToken);
	            field.setType(type);
	            // Detalle
	            field.setDescription(description);
	            field.setMult(mult);
	            field.setDoc(doc);
	            field.setObligatory(obligatory);
	            
	            fldsList.add(field);
	            
	        }
        }    
        List ieciTdTypeChoices = getIeciTdTypeChoices();

        // fields.setFldsList(fldsList);
        fields.setIeciTdTypeChoices(ieciTdTypeChoices);
        return fields;
    }
    
    public Advanced getAdvanced(String entidad) throws Exception
    {
        advanced = new Advanced();
        int adminUserId = archive.getAdminUserId();
        
        
        int volListId = archiveMisc.getVolListId();
        int volListType = archiveMisc.getVolListType();
        
        String adminUserName = ServiceCommon.getUserNameById(adminUserId, isLdap, entidad);
        String volListName = ServiceCommon.getNameListById(volListId, entidad);
        String volListTypeName = null;
        switch (volListType)
        {
        	case ArchiveVolListType.DYNAMIC:
        	    volListTypeName = Constantes.VOL_LIST_TYPE_DYNAMIC;
        	    break;
        	case ArchiveVolListType.STATIC:
        	    volListTypeName = Constantes.VOL_LIST_TYPE_STATIC;
        	    break;
        	case Defs.NULL_ID:
        	    volListTypeName = Constantes.VOL_LIST_TYPE_NONE;
        	    break;        	
        }
        advanced.setAdminUserName(adminUserName);
        advanced.setVolListName(volListName);
        advanced.setVolListType(volListTypeName);
        return advanced;
    }
    public Indexs getIndexs(int option) throws Exception
    {
        indexs = new Indexs();
        List indexsList = new ArrayList();
        if (option != Constantes.NEW)
        {
	        ArchiveIdxs archiveIdxs = archive.getIdxsDef();
	        int n = archiveIdxs.count();
	        
	        for (int i = 0; i < n; i ++ )
	        {
	            ArchiveIdx archiveIdx = archiveIdxs.get(i);
	            int id = archiveIdx.getId();
	            String name = archiveIdx.getName();
	            boolean isUnique = archiveIdx.isUnique();
	            List fldsId = archiveIdx.getFldsId();
	            
	            int size = fldsId.size();
	            String fldsIdArray[] = new String[size];
	            Iterator it = fldsId.iterator();
	            int j = 0;
	            while (it.hasNext())
	            {
	                Integer fdlId = (Integer) it.next();
	                fldsIdArray[j] = fdlId.toString();
	                j++;
	            }
	            Index index = new Index();
	            index.setId(id);
	            index.setName(name);
	            index.setIsUnique(isUnique);
	            index.setFldsId(fldsIdArray);
	            
	            indexsList.add(index);
	        }
	        indexs.setIndexsList(indexsList);
        }
        
        //  Los campos de texto largo y los multivalores no pueden formar parte de un índice.
        
        List fldsId = fields.getFldsList(); 
        List fldsIdChoices = new ArrayList(); 
        Iterator it = fldsId.iterator();
        int i = 0;
        while (it.hasNext() )
        {
            Field field = (Field) it.next();
            if (option == Constantes.NEW){
                field.setId(i); // TODO
                i++;
            }
            field.setTypeToken(getTypeToken(field.getType()));
            boolean isLongText =field.getIsLongText();
            boolean isMult = field.getMult();
            if (!isLongText && !isMult )
                fldsIdChoices.add(field);
        }
        indexs.setFldsIdChoices(fldsIdChoices);
        
        return indexs;
    }
    
    public Folder getFolder()
    {
        folder = new Folder();
        String fdrName = archiveMisc.getFdrName();
        
        // @FLD(-2) --> @FLD('#ID_FDR#')

        String regex= "@FLD\\(-2\\)";
        String replacement = "@FLD\\(\\'#ID_FDR#\\'\\)";
        fdrName = fdrName.replaceAll(regex,replacement);
        
        
        List fldsId = fields.getFldsList(); // Filtrar los campos de texto largo
        List fldsIdChoices = new ArrayList();
        Field idFolder = new Field();
        idFolder.setName("Id. de carpeta");
        idFolder.setTypeToken("-");
        idFolder.setLength("-");
        fldsIdChoices.add(idFolder);
        
        Iterator it = fldsId.iterator();
        while (it.hasNext() )
        {
            Field field = (Field) it.next();
            if (field.getType() != IeciTdType.LONG_TEXT )
                fldsIdChoices.add(field);
        }
        
        folder.setFldsIdChoices(fldsIdChoices);
        folder.setFdrName(fdrName);
        
        return folder;
        
    }
    public List getAdminUsers(boolean isArchive, String entidad)throws Exception
    {
        BasicUsers basicUsers;
        if (isArchive)
            basicUsers = archive.getAdminUsers();
        else
            basicUsers = directory.getAdminUsers(entidad);
        int n = basicUsers.count();
        List adminUsers = new ArrayList();
        
        for (int i = 0; i < n; i ++){
            BasicUser basicUser = basicUsers.get(i);
            int id = basicUser.getId();
            String name = basicUser.getName();
            LabelValueBean obj = new LabelValueBean (name, String.valueOf(id));
            adminUsers.add(obj);
        }
        return adminUsers;
    }
    
    public Field getNewField()
    {
        Field newField = new Field();
        newField.setIsNew(true);
        newField.setType(IeciTdType.SHORT_TEXT);
        newField.setTypeToken(getTypeToken(IeciTdType.SHORT_TEXT));
        
        return newField;
    }
    public Index getNewIndex()
    {
        Index newIndex = new Index();
        newIndex.setIsNew(true);
        return newIndex;
    }
    
    public List getVolListTypeChoices()
    {
        List volListTypeChoices = new ArrayList();
        volListTypeChoices.add(new LabelValueBean ( Constantes.VOL_LIST_TYPE_STATIC , String.valueOf(ArchiveVolListType.STATIC)) );
        volListTypeChoices.add(new LabelValueBean ( Constantes.VOL_LIST_TYPE_DYNAMIC , String.valueOf(ArchiveVolListType.DYNAMIC)) );        
        return volListTypeChoices;
    }
    public List getIeciTdTypeChoices()
    {
        List ieciTdTypeChoices = new ArrayList();
        ieciTdTypeChoices.add(new LabelValueBean (Constantes.SHORT_TEXT_TOKEN,String.valueOf(IeciTdType.SHORT_TEXT)) );
        ieciTdTypeChoices.add(new LabelValueBean (Constantes.LONG_TEXT_TOKEN,String.valueOf(IeciTdType.LONG_TEXT)) );
        ieciTdTypeChoices.add(new LabelValueBean (Constantes.SHORT_INTEGER_TOKEN, String.valueOf(IeciTdType.SHORT_INTEGER)) );
        ieciTdTypeChoices.add(new LabelValueBean (Constantes.LONG_INTEGER_TOKEN,String.valueOf(IeciTdType.LONG_INTEGER)) );
        ieciTdTypeChoices.add(new LabelValueBean (Constantes.SHORT_DECIMAL_TOKEN,String.valueOf(IeciTdType.SHORT_DECIMAL)) );
        ieciTdTypeChoices.add(new LabelValueBean (Constantes.LONG_DECIMAL_TOKEN,String.valueOf(IeciTdType.LONG_DECIMAL)) );
        ieciTdTypeChoices.add(new LabelValueBean (Constantes.TIME_TOKEN,String.valueOf(Constantes.TIME)) );
        ieciTdTypeChoices.add(new LabelValueBean (Constantes.DATE_TOKEN,String.valueOf(Constantes.DATE)) );
        ieciTdTypeChoices.add(new LabelValueBean (Constantes.DATE_TIME_TOKEN,String.valueOf(Constantes.DATE_TIME)) );
        return ieciTdTypeChoices;
    }
    public String getTypeToken(int type)
    {
        String typeToken = null;
        switch (type){
        	case IeciTdType.SHORT_TEXT:
        	    typeToken = Constantes.SHORT_TEXT_TOKEN;
        	    break;
        	case IeciTdType.LONG_TEXT:
        	    typeToken = Constantes.LONG_TEXT_TOKEN;
        	    break;
        	case IeciTdType.SHORT_INTEGER:
        	    typeToken = Constantes.SHORT_INTEGER_TOKEN;
        	    break;
        	case IeciTdType.LONG_INTEGER:
        	    typeToken = Constantes.LONG_INTEGER_TOKEN;
        	    break;
        	case IeciTdType.SHORT_DECIMAL:
        	    typeToken = Constantes.SHORT_DECIMAL_TOKEN;
        	    break;
        	case IeciTdType.LONG_DECIMAL:
        	    typeToken = Constantes.SHORT_DECIMAL_TOKEN;
        	    break;
        	case Constantes.TIME:
        	    typeToken = Constantes.TIME_TOKEN;
        		break;
        	case Constantes.DATE:
        	    typeToken = Constantes.DATE_TOKEN;
        		break;
        	case Constantes.DATE_TIME:
        	    typeToken = Constantes.DATE_TIME_TOKEN;
        	    break;
        }        
        return typeToken;
    }

    public AccessDTO getAccessDTO(HttpServletRequest request, boolean isLdap, String entidad)throws Exception {
        AccessDTO archPerm = null;
        if (isLdap){
            archPerm = getAccessDTOLdap(request, entidad);
        }
        else{
            archPerm = getAccessDTOBd(request, entidad);
        }

        return archPerm;
    }
    
    private AccessDTO getAccessDTOBd (HttpServletRequest request, String entidad) throws Exception {
        AccessDTO accessDTO = null;
        int type = Integer.parseInt(request.getParameter("type"));
        int id = Integer.parseInt(request.getParameter("id"));
        
        String destName = null;
        int destId = Defs.NULL_ID;
        Permissions permissions = null;
        Permission permission = null;
        switch (type){
        	case Constantes.PERSON:
        	    User user = ObjFactory.createUser();
        		user.load(id, entidad);
        		accessDTO = new UserAccessDTO();
        		destName = user.getName();
                destId = user.getId();
        		
                UserAccessDTO userAccessDTO = (UserAccessDTO) accessDTO ; 
                UserProfiles profiles = user.getProfiles();
                
                permissions = user.getPermissions();
                permission = permissions.getProductPermission(UserDefs.PRODUCT_IDOC); // Permisos sobre InvesDoc
                
                userAccessDTO.setProfiles(profiles);
                accessDTO.setPermission(permission);
        	    break;
        	case Constantes.GROUP:
        	    Group group = ObjFactory.createGroup();
        		group.load(id, entidad);
        		destName = group.getName();
        		destId = group.getId();
        		
        	    permissions = group.getPermissions();
	            permission = permissions.getProductPermission(UserDefs.PRODUCT_IDOC); // Permisos sobre InvesDoc                
	            
	            accessDTO = new GroupAccessDTO();
	            accessDTO.setPermission(permission);
        	    break;
        	case Constantes.DEPARTAMENT:
        	    Department dept = ObjFactory.createDepartment();
        		dept.load(id, entidad);
        		destName = dept.getName();
        		destId = dept.getId();
        		
        		permissions = dept.getPermissions();
	            permission = permissions.getProductPermission(UserDefs.PRODUCT_IDOC); // Permisos sobre InvesDoc                
	            
	            accessDTO = new DeptAccessDTO();
	            accessDTO.setPermission(permission);
        	    break;
        }
        
        accessDTO.setId(destId);
        accessDTO.setName(destName);
        
        return accessDTO;
    }
    
    private AccessDTO getAccessDTOLdap (HttpServletRequest request, String entidad) throws Exception {
        
        int destType = Defs.NULL_ID;
        int destId = Defs.NULL_ID;
        String destName = null;
        String message = null;
        AccessDTO accessDTO = null;
        
        HttpSession session = request.getSession(false);
        String id = request.getParameter("id");
        NaryTree tree = (NaryTree) session.getAttribute("tree");
        int maxChildrenLdap = ( (Integer)request.getSession().getServletContext().getAttribute(Constantes.MAX_CHILDREN_LDAP) ).intValue();
        
        ServiceTree serviceTree = new ServiceTreeLdap(tree, maxChildrenLdap, entidad);
        String dn = null;
        if (id != null) {
            boolean enc = serviceTree.searchNode(tree, Integer.parseInt(id));
            if (enc) {
                Node nodo = (Node) serviceTree.fin.getRoot();
                dn = nodo.getTitle(); // Sacar el dn a partir del id del árbol
            }
            else
                logger.error("No es un id valido");
        }
        else
            logger.error("No se ha recibido ningun id");

        
        
        ServiceLdap serviceLdap = new ServiceLdap(maxChildrenLdap, entidad);
        String userFilter = "person";
        String groupFilter = serviceLdap.getGroupFilter();
        
        int i = groupFilter.indexOf("="); // Posible chapuza para saber si un determinado dn, es un grupo
        groupFilter = groupFilter.substring(i+1,groupFilter.length()-1);
        
        Map attMap = serviceLdap.getAttributesMap(dn);

        String objectClassValue = attMap.get("objectclass").toString().toLowerCase();
        String guid = serviceLdap.getGuidAttributeName();
        
        
        if (objectClassValue.indexOf("person") != -1 ) // Es una persona
        {
            String guidValue = attMap.get(guid).toString();
            LdapUser user =ObjFactory.createLdapUser(Defs.NULL_ID);
            try {
                user.loadFromGuid(guidValue, entidad);
                accessDTO = new UserAccessDTO();
                destName = user.getFullName();
                destId = user.getId();
                UserAccessDTO userAccessDTO = (UserAccessDTO) accessDTO ; 
                UserProfiles profiles = user.getProfiles();
                
                Permissions permissions = user.getPermissions();
                Permission permission = permissions.getProductPermission(UserDefs.PRODUCT_IDOC); // Permisos sobre InvesDoc
                
                userAccessDTO.setProfiles(profiles);
                accessDTO.setPermission(permission);
                
            } catch (IeciTdException e1) { // eL usuario de Ldap, no esta dado de alta en invesDoc
                logger.debug(" No dado de alta");
                message = "message.ldap.user.notexist";
                if (e1.getErrorCode().equals(String.valueOf(UserErrorCodes.EC_USER_NOT_EXITS)))
                    throw new IeciTdException(String.valueOf(MvcError.EC_OBJ_LDAP_NOT_EXITS),null);
                else
                    throw e1;
            }
        }
        else if ( objectClassValue.indexOf(groupFilter) != -1 )
        {
            String guidValue = attMap.get(guid).toString();
            LdapGroup ldapGroup = ObjFactory.createLdapGroup(Defs.NULL_ID);
            try {
                ldapGroup.loadFromGuid(guidValue, entidad);
                Permissions permissions = ldapGroup.getPermissions();
                Permission permission = permissions.getProductPermission(UserDefs.PRODUCT_IDOC); // Permisos sobre InvesDoc                
                
                destId = ldapGroup.getId();
                destName = ldapGroup.getFullName();
                accessDTO = new GroupAccessDTO();
                accessDTO.setPermission(permission);
                
            }
            catch (IeciTdException e1) { // eL grupo de Ldap, no esta dado de alta en invesDoc
                message = "message.ldap.group.notexist";
                if (e1.getErrorCode().equals(String.valueOf(GroupErrorCodes.EC_GROUP_NOT_EXITS)))
                    throw new IeciTdException(String.valueOf(MvcError.EC_OBJ_LDAP_NOT_EXITS),null);
                else
                    throw e1;
            }            
        }
        else{
            throw new IeciTdException(String.valueOf(MvcError.EC_OBJ_LDAP_NOT_EXITS),null);
        }
        
        accessDTO.setId(destId);
        accessDTO.setName(destName);
        
        return accessDTO;
    }
    public List getPermissionChoices()
    {
        List permissionChoices = new ArrayList();
        permissionChoices.add(new LabelValueBean (Constantes.TOKEN_PERM_QUERY, String.valueOf(Defs.OBJ_PERM_QUERY)) );        
        permissionChoices.add(new LabelValueBean (Constantes.TOKEN_PERM_UPDATE, String.valueOf(Defs.OBJ_PERM_UPDATE)) );
        permissionChoices.add(new LabelValueBean (Constantes.TOKEN_PERM_CREATION, String.valueOf(Defs.OBJ_PERM_CREATION)) );
        permissionChoices.add(new LabelValueBean (Constantes.TOKEN_PERM_DELETION, String.valueOf(Defs.OBJ_PERM_DELETION)) );
        
        return permissionChoices;
    }
    /**
     * @param idx
     */
    public void updateField(int idx) {

        Field field = (Field) fields.getFldsList().get(idx);
        field.setIsUpdate(true);
    }
    /**
     * 
     */
    public void updateArchive(String entidad) throws Exception {
        Iterator it; 
        List fldsList = fields.getFldsList();
        
        // Añadir los posibles campos para eliminar por si casca que se sigan mostrando
        it = deletedFieldList.iterator();
        while (it.hasNext())
            fldsList.add(it.next());
        
        deletedFieldList = new ArrayList();
        // Copia de la lista con los id d los campos para eliminar
        ArrayList dFldsIdClon = (ArrayList) dFldsId.clone();
        dFldsId = new ArrayList();
        // Copia de la lista con los id d los indices para eliminar
        ArrayList dIdxsIdClon = (ArrayList) dIdxsId.clone();
        dIdxsId  = new ArrayList();
        
        ArchiveFlds fldsDef = updInfo.getFldsDef();
        
        it = fldsList.iterator();
        int i = 0; 
        ArchiveFld archiveFld;
        
//      Actualización de General
        String newArchName = general.getName();
        if (! updInfo.getName().equals(newArchName))
            updInfo.setName(newArchName);
        
        String remarks = general.getDescription();
        if ( updInfo.getRemarks() == null || !updInfo.getRemarks().equals(remarks) )
            updInfo.setRemarks(remarks);
        
        int newAdminUserId = general.getManagerId();
        if (newAdminUserId != updInfo.getAdminUserId() )
            updInfo.setAdminUserId(newAdminUserId);
        
        updInfo.setFtsInContents(general.getFtsInContents());

//      Actualización de Campos
        ArrayList nFldsId = new ArrayList(); // Lista de campos nuevos
        boolean isThereAnyFieldNew = false;
        while (it.hasNext())
        {
            Field field = (Field) it.next();
            String newName = field.getName();
            
            if (field.getIsUpdate() || field.getIsNew()){
                int newType = field.getType();
                String newLen = field.getLength();
                boolean newIsObligatory = field.getObligatory();
                boolean newMult = field.getMult();
                boolean newDoc = field.getDoc();
                int intNewLen = 0;
                
                try {
                    if (field.getIsText())
                        intNewLen = Integer.parseInt(newLen);
                    else
                        field.setLength("-");
                }catch (Exception e)
                {
                    AdminException.throwException(ArchiveErrorCodes.EC_ARCH_FLD_TXT_BAD_LEN);
                }
                
                String newDescription = field.getDescription();
                field.setTypeToken(getTypeToken(newType));
                
                if (field.getIsUpdate()){
                    archiveFld = fldsDef.get(i);
                    archiveFld.setName(newName);
                    archiveFld.setLen( intNewLen );
                    
                    archiveFld.setType(newType);
                    archiveFld.setNullable(!newIsObligatory);
                    archiveFld.setMult(newMult);
                    archiveFld.setDoc(newDoc);
                    archiveFld.setRemarks(newDescription);
                    
                }
                else if (field.getIsNew()) {
                    
                    fldsDef.add(newName,newType, intNewLen, !newIsObligatory, newDoc,newMult,newDescription);
                    nFldsId.add(new Integer(fldsDef.getFldIdByName(newName)) ); // OK
                    isThereAnyFieldNew = true;
                }
            }
            i++;
        }

        // Borrar campos
        boolean modifyDefFlds = false;
        
        if (! general.getExistsFdrsInArch())
            modifyDefFlds = true;
        updInfo.setUpdateFlds(nFldsId,dFldsIdClon,modifyDefFlds);
        
        // Indices
        ArrayList nIdxsId = new ArrayList();
        ArchiveIdxs archiveIdxs = updInfo.getIdxsDef();
        List indexsList = indexs.getIndexsList();
        it = indexsList.iterator();
        i = 0;
        ArchiveIdx archiveIdx;
        while (it.hasNext())
        {
            Index index = (Index) it.next();
            if (index.getIsUpdate())
            {
                /*
                archiveIdx = archiveIdxs.get(i);
                ArrayList fldsId = new ArrayList();
                int j = 0;
                // Creo que esto no hace falta, ya que no se pueden modificar los campos asociados a los indices
                while (j < index.getFldsId().length)
                {
                    
                    int idField =  Integer.parseInt( index.getFldsId()[j] );
                    fldsId.add(new Integer ( idField ));
                    
                    j++;
                }
                archiveIdx.setFldsId(fldsId);
                */
            }
            else if (index.getIsNew())
            {
                ArrayList fldsId = new ArrayList();
                int j = 0; 
                while (j < index.getFldsId().length)
                {
                    int idField =  Integer.parseInt( index.getFldsId()[j] );
                    //int idField = fldsDef.get(fieldPos).getId();
                    //fldsId.add(new Integer ( idField ));
                    fldsId.add(new Integer ( idField ));
                    
                    
                    j++;
                }
                archiveIdxs.add(index.getName(), index.getIsUnique(), fldsId);
                nIdxsId.add(  new Integer ( archiveIdxs.getIdxIdByName(index.getName())) );
            }
        }
        updInfo.setUpdateIdxs(nIdxsId,dIdxsIdClon);

        //  Actualizacion de Lista de volumenes asociada y nombre de carpeta
        
        ArchiveMisc miscDef = updInfo.getMiscDef();
        
        int volListTypeNew = Integer.parseInt(this.general.getVolListType());
        boolean modifyListVols = false;
        
        int volListIdNew = general.getVolListId();
        
        if (volListIdNew == Defs.NULL_ID ){
            this.general.setVolListType(String.valueOf(ArchiveVolListType.NONE));
            volListTypeNew = ArchiveVolListType.NONE;
        }
        
        if (volListTypeNew != miscDef.getVolListType()){
            miscDef.setVolListType(volListTypeNew);
            modifyListVols = true;
        }
        if (volListIdNew != miscDef.getVolListId()){
            miscDef.setVolListId(volListIdNew);
            modifyListVols = true;
        }

        
        // Cambiar nombre de carpeta 
        //  @FLD('#ID_FDR#') --> @FLD(-2)
        
        String regex = "@FLD\\(\\'#ID_FDR#\\'\\)";
        String replacement = "@FLD\\(-2\\)";
        
        String fdrNameNew = folder.getFdrName();
        fdrNameNew = fdrNameNew.replaceAll(regex,replacement);

        miscDef.setFdrName(fdrNameNew);
        
        
        updInfo.ModifyListVols(modifyListVols);
        
        
        archive.update(updInfo, entidad);
        
//      Se supone que no ha habido fallos luego actualizamos cambios
        
        this.dIdxsId = nIdxsId = this.dFldsId = nFldsId = new ArrayList();

        it = fldsList.iterator();
        while (it.hasNext())
        {
            Field field = (Field) it.next();
            if (field.getIsUpdate())
                field.setIsUpdate(false);
            else if (field.getIsNew()) 
                field.setIsNew(false);
        }
        it = indexsList.iterator();
        while (it.hasNext())
        {
            Index index = (Index) it.next();
            if (index.getIsUpdate())
                index.setIsUpdate(false);
            if (index.getIsNew())
                index.setIsNew(false);
        }
        
        // it = deletedFieldList.iterator();
        int deletedFieldCount = dFldsIdClon.size(); 
        int fieldsCount = fldsList.size();
        i = fieldsCount - 1 ;
        int end = i - deletedFieldCount ;
        while ( i > end ){
            fldsList.remove(i);
            i --;
        }
        
        // Actualizacion de indices
        if (isThereAnyFieldNew) // Si hay algun campo nuevo actualizar la lista de campos posibles en los indices
        {
	        List fldsId = fields.getFldsList(); 
	        List fldsIdChoices = new ArrayList(); 
	        it = fldsId.iterator();
	        while (it.hasNext() )
	        {
	            Field field = (Field) it.next();
	            boolean isLongText =field.getIsLongText();
	            boolean isMult = field.getMult();
	            if (!isLongText && !isMult )
	                fldsIdChoices.add(field);
	        }
	        indexs.setFldsIdChoices(fldsIdChoices);
        }
        logger.debug("Actualización de archivador correcta");
    }
    /**
     * @param idx
     */
    public void deleteField(int idx) throws Exception{
        Field field = (Field) fields.getFldsList().get(idx);
        int id = field.getId();
                
        dFldsId.add(new Integer(id));
        Field deletedField = (Field) fields.getFldsList().remove(idx);
        deletedFieldList.add(deletedField);
        
    }
    /**
     * @param idx
     */
    public void updateIndex(int idx) {
        Index index = (Index) indexs.getIndexsList().get(idx);
        index.setIsUpdate(true);
        
    }
    /**
     * @param idx
     */
    public void deleteIndex(int idx) {
        Index index = (Index)indexs.getIndexsList().get(idx);
        int id = index.getId();
        dIdxsId.add(new Integer(id));
        indexs.getIndexsList().remove(idx);
        // TODO Auto-generated method stub
        
    }
    /**
     * 
     */
    public void createArchive(String entidad) throws Exception{
        if (logger.isDebugEnabled())
            logger.debug("Creando Archivador...");
        // Paso 1 - General
        String name = general.getName();
        String remarks = general.getDescription();
        
        // Paso 2 - Campos
        ArchiveFlds fldsDef = ObjFactory.createArchiveFlds();
        
        Iterator it;
        List fldsList = fields.getFldsList();
        it = fldsList.iterator();
        while (it.hasNext()){
            Field field = (Field ) it.next();
            String nameField = field.getName();
            int type = field.getType();
            String len = field.getLength();
            boolean isObligatory = field.getObligatory();
            boolean mult = field.getMult();
            boolean doc = field.getDoc();
            int intLen = 0;
            try {
                if (field.getIsText())
                    intLen = Integer.parseInt(len);
            }catch (Exception e)
            {
                AdminException.throwException(ArchiveErrorCodes.EC_ARCH_FLD_TXT_BAD_LEN);
            }
            
            String description = field.getDescription();
            field.setTypeToken(getTypeToken(type));
            fldsDef.add(nameField,type, intLen, !isObligatory, doc,mult,description);
        }
        // Paso 3 - Indices
        List indexsList = indexs.getIndexsList();
        ArchiveIdxs idxsDef = ObjFactory.createArchiveIdxs();
        
        it = indexsList.iterator();
        ArchiveIdx archiveIdx;
        while (it.hasNext())
        {
            Index index = (Index) it.next();
            ArrayList fldsId = new ArrayList();
            int j = 0; 
            while (j < index.getFldsId().length)
            {
                int fieldPos =  Integer.parseInt( index.getFldsId()[j] );
                int idField = fldsDef.get(fieldPos).getId();
                fldsId.add(new Integer ( idField ));
                j++;
            }
            idxsDef.add(index.getName(), index.getIsUnique(), fldsId);
        }
        // Paso 4 - Almacenamiento
        int volListId = general.getVolListId();
        int volListType = Integer.parseInt(general.getVolListType());
        
        archiveMisc.setVolListId(volListId);
        archiveMisc.setVolListType(volListType );
        // Guardar Cambios
        archive.setName(name);
        archive.setRemarks(remarks);
        archive.setFldsDef(fldsDef);
        archive.setIdxsDef(idxsDef);
        
        archive.create(entidad);
        
    }
    /**
     * @param userConnected
     * @param id
     */
    public void dirDelete(int userConnected, int id, boolean isLdap, String entidad) throws Exception {
        ArchiveAccess archiveAccess = ObjFactory.createArchiveAccess();
        boolean userCanDeleteDir = archiveAccess.userCanDeleteDir(userConnected, id, entidad);
        
        if (!userCanDeleteDir)
            throw new IeciTdException(String.valueOf(MvcError.EC_NOT_ENOUGH_PERMISSIONS), null);
        
        Directory dir = ObjFactory.createDirectory(Defs.NULL_ID, Defs.NULL_ID,isLdap);
        dir.load(id, entidad);
        dir.delete(entidad);
            
    }

}
