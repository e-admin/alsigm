
package ieci.tecdoc.idoc.admin.api;

import ieci.tecdoc.idoc.admin.api.archive.Archive;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveAccess;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveFld;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveFlds;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveIdx;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveIdxs;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveMisc;
import ieci.tecdoc.idoc.admin.api.archive.ArchivePerms;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveUpdInfo;
import ieci.tecdoc.idoc.admin.api.archive.Archives;
import ieci.tecdoc.idoc.admin.api.archive.Directories;
import ieci.tecdoc.idoc.admin.api.archive.Directory;
import ieci.tecdoc.idoc.admin.api.system.SystemCfg;
import ieci.tecdoc.idoc.admin.api.user.Department;
import ieci.tecdoc.idoc.admin.api.user.Group;
import ieci.tecdoc.idoc.admin.api.user.GroupUserRel;
import ieci.tecdoc.idoc.admin.api.user.LdapGroup;
import ieci.tecdoc.idoc.admin.api.user.LdapUser;
import ieci.tecdoc.idoc.admin.api.user.User;
import ieci.tecdoc.idoc.admin.api.user.UserAccess;
import ieci.tecdoc.idoc.admin.internal.ArchiveAccessImpl;
import ieci.tecdoc.idoc.admin.internal.ArchiveFldImpl;
import ieci.tecdoc.idoc.admin.internal.ArchiveFldsImpl;
import ieci.tecdoc.idoc.admin.internal.ArchiveIdxImpl;
import ieci.tecdoc.idoc.admin.internal.ArchiveIdxsImpl;
import ieci.tecdoc.idoc.admin.internal.ArchiveImpl;
import ieci.tecdoc.idoc.admin.internal.ArchiveMiscImpl;
import ieci.tecdoc.idoc.admin.internal.ArchivePermsImpl;
import ieci.tecdoc.idoc.admin.internal.ArchiveUpdInfoImpl;
import ieci.tecdoc.idoc.admin.internal.ArchivesImpl;
import ieci.tecdoc.idoc.admin.internal.DepartmentImpl;
import ieci.tecdoc.idoc.admin.internal.DirectoriesImpl;
import ieci.tecdoc.idoc.admin.internal.DirectoryImpl;
import ieci.tecdoc.idoc.admin.internal.GroupImpl;
import ieci.tecdoc.idoc.admin.internal.GrpUsrRelImpl;
import ieci.tecdoc.idoc.admin.internal.LdapGroupImpl;
import ieci.tecdoc.idoc.admin.internal.LdapUserImpl;
import ieci.tecdoc.idoc.admin.internal.SystemCfgImpl;
import ieci.tecdoc.idoc.admin.internal.UserAccessImpl;
import ieci.tecdoc.idoc.admin.internal.UserImpl;
import ieci.tecdoc.idoc.admin.api.volume.Repository;
import ieci.tecdoc.idoc.admin.api.volume.Volume;
import ieci.tecdoc.idoc.admin.api.volume.VolumeList;
import ieci.tecdoc.idoc.admin.internal.RepositoryImpl;
import ieci.tecdoc.idoc.admin.internal.VolumeImpl;
import ieci.tecdoc.idoc.admin.internal.VolumeListImpl;

/**
 * Factoría de creación de objetos de administración
 */

public class ObjFactory 
{
   private ObjFactory()
   {
   }

   /**
    * Crea una instancia (sin valores iniciales) de un usuario Ldap.
    *
    * @param userConnected Identificador del usuario conectado en la aplicación
    * de administración.
    * @return La instancia del usuario creado.
    */
    
   public static LdapUser createLdapUser(int userConnected)
   {
      return new LdapUserImpl(userConnected);
   }

   /**
    * Crea una instancia (sin valores iniciales) de un grupo Ldap.
    *
    * @param userConnected Identificador del usuario conectado en la aplicación
    * de administración.
    * @return La instancia del grupo creado.
    */
    
   public static LdapGroup createLdapGroup(int userConnected)
   {
      return new LdapGroupImpl(userConnected);
   }
   
   /**
    * Crea una instancia (sin valores iniciales) de un usuario.
    *
    * @param userConnected Identificador del usuario conectado en la aplicación
    * de administración.
    * @param deptId Identificador del departamento.
    * @return La instancia del usuario creado.
    */
   
   public static User createUser(int userConnected, int deptId)
	{
   	return new UserImpl(userConnected, deptId);
   }
   
   /**
    * Crea una instancia (sin valores iniciales) de un grupo.
    *
    * @param userConnected Identificador del usuario conectado en la aplicación
    * de administración.
    * @return La instancia del grupo creado.
    */
   
   public static Group createGroup(int userConnected)
	{
   	return new GroupImpl(userConnected);
   }
   
   /**
    * Crea una instancia (sin valores iniciales) de un departamento.
    *
    * @param userConnected Identificador del usuario conectado en la aplicación
    * de administración.
    * @param parentId Identificador del padre de departamento que pertenece.
    * @return La instancia del departamento creado.
    */
   
   public static Department createDepartment(int userConnected, int parentId)
	{
   	return new DepartmentImpl(userConnected, parentId);
   }

   /**
    * Crea una instancia (sin valores iniciales) de un directorio.
    *
    * @param userConnected Identificador del usuario conectado en la aplicación
    * de administración.
    * @param parentId Identificador del directorio padre.
    * @param isLdap Indica si se está trabajando con Ldap o no.
    * @return La instancia del directorio creado.
    */
    
   public static Directory createDirectory(int userConnected, int parentId, 
                                           boolean isLdap)
   {
      return new DirectoryImpl(userConnected, parentId, isLdap);
   }
   
   /**
    * Crea una instancia (sin valores inicialis) de lista de directorios
    * 
    * @return La instancia
    */
   public static Directories createDirectories()
   {
      return new DirectoriesImpl();
   }
   
   /**
    * Crea una instancia (sin valores iniciales) de un archivador.
    *
    * @param userConnected Identificador del usuario conectado en la aplicación
    * de administración.
    * @param parentId Identificador del directorio padre.
    * @param isLdap Indica si se está trabajando con Ldap o no.
    * @return La instancia del archivador creado.
    */
   public static Archive createArchive(int userConnected, int parentId,
         										boolean isLdap)
   {
      return new ArchiveImpl(userConnected, parentId, isLdap);
   }
   
   /**
    * Crea una instancia (sin valores iniciales) de permisos de archivador
    * @return La instaancia de los pemisos de archivador
    */
   public static ArchivePerms createArchivePerms()
   {
      return new ArchivePermsImpl();
   }
   
   /**
    * Crea una instancia de la estructura de un campo
    * @return la instancia de la estructura
    */
   public static ArchiveFld createArchiveFld()
   {
      return new ArchiveFldImpl();
   }
   
   /**
    * Crea la instancia de la estructura de campos
    * @return La instancia
    */
   public static ArchiveFlds createArchiveFlds()
   {
      return new ArchiveFldsImpl();
   }
   
   /**
    * Crea la instancia de la estructura de un índice
    * @return La instancia
    */
   public static ArchiveIdx createArchiveIdx()
   {
      return new ArchiveIdxImpl();
   }
   
   
   /**
    * Crea la instancia de la estructura de índices
    * @return La instancia
    */
   public static ArchiveIdxs createArchiveIdxs()
   {
      return new ArchiveIdxsImpl();
   }
   
   
   /**
    * Crea la instancia para información extra del archivador
    * @param fdrName título de las carpetas
    * @param volListType tipo de lista de volúmenes
    * @param volListId Identificador de lista de volúmenes
    * @return La instancia
    */
   public static ArchiveMisc createArchiveMisc(String fdrName, int volListType, int volListId)
   {
      return new ArchiveMiscImpl(fdrName, volListType, volListId);
   }
   
   /**
    * Crea la instancia de la estructura de actualización de un archivador
    * @return - la instancia
    */
   public static ArchiveUpdInfo createArchiveUpdInfo()
   {
      return new ArchiveUpdInfoImpl();
   }
   
   
   /**
    * Crea la instancia de la estructura de actualización de un archivador
    * @param name - nombre del archivador
    * @param remarks - descripción
    * @param adminUserId - identificador del administrador del archivador
    * @param ftsInContents - búsqueda en contenido de fichero (true/false)
    * @param flds - estructura con la definición de campos (antiguos y nuevos)
    * @param idxs - estructura con la definición de índices (antiguos y nuevos)
    * @param misc - estructura con la información extra del archivador
    * @return La instancia
    */
   public static ArchiveUpdInfo createArchiveUpdInfo(String name, String remarks, int adminUserId,
         															boolean ftsInContents,ArchiveFlds flds, 
         															ArchiveIdxs idxs, ArchiveMisc misc)
   {
      return new ArchiveUpdInfoImpl(name, remarks, adminUserId,
            								ftsInContents, (ArchiveFldsImpl)flds, 
            								(ArchiveIdxsImpl)idxs, (ArchiveMiscImpl)misc);
      
   }
   
   /**
    * Crea una instancia (sin valores inicialis) de lista de archivadores
    * 
    * @return La instancia
    */
   public static Archives createArchives()
   {
      return new ArchivesImpl();
   }
   
   /**
    * Crea la instancia para obtener el acceso al sistema de archivadores.
    * @return La instancia. 
    */
   public static ArchiveAccess createArchiveAccess()
   {
      return new ArchiveAccessImpl();
   }

   /**
    * Crea una instancia (sin valores iniciales) de un repositorio.
    * 
    * @param userConnected Identificador del usuario conectado en la aplicación
    * de administración.
    * @return La instancia del repositorio creado.
    */
   
   
   public static Repository createRepository(int userConnected)
   {
      return new RepositoryImpl(userConnected);
   }
   
   /**
    * Crea una instancia (sin valores iniciales) de una lista de volúmenes.
    * 
    * @param userConnected Identificador del usuario conectado en la aplicación
    * de administración.
    * @return La instancia de la lista de volúmenes creada.
    */
   public static VolumeList createVolumeList(int userConnected)
   {
      return new VolumeListImpl(userConnected);
   }
   
   /**
    * Crea una instancia (sin valores iniciales) de un volumen. 
    * 
    * @param userConnected Identificador del usuario conectado en la aplicación
    * de administración.
    * @param repId Identificador del repositorio al que pertenece
    * @return La instancia del volumen creado.
    */
   public static Volume createVolume(int userConnected, int repId)
   {
      return new VolumeImpl(userConnected, repId);
   }

   /**
    * Crea un instancia para obtener accesos al sistema de usuarios invesDoc.
    * @return La instancia
    */
   public static UserAccess createUserAccess()
   {
      return new UserAccessImpl();
   }
   /**
    * Crea una instancia para la relación grupos - usuarios. 
    * @return La instancia
    */
   public static GroupUserRel createGroupUserRel()
   {
      return new GrpUsrRelImpl();
   }
   
   /**
    * Crea una instancia para un grupo invesDoc
    * @return La instancia
    */
   public static Group createGroup()
   {
      return new GroupImpl();   
   }
   
   /**
    * Crea una instancia para un departamento invesDoc
    * @return La instancia
    */
   public static Department createDepartment()
   {
      return new DepartmentImpl();
   }
   
   /**
    * Crea una instancia para un usuario invesDoc
    * @return La instancia
    */
   public static User createUser()
   {
      return new UserImpl();
   }
   
   /**
    * Crea una instancia para el sistema invesDoc
    * @return La instancia
    */
   public static SystemCfg createSystem()
   {
      return new SystemCfgImpl();
   }
   
   /**
    * Crea una instancia para el sistema invesDoc con parámetros específicos de base de datos
    * 
    * @param drvClsName Driver
    * @param url Cadena de conexión
    * @param user Usuario
    * @param pwd Contraseña
    * @return La instancia
    */
   public static SystemCfg createSystem(String drvClsName,String url, String user, String pwd)
   {
       return new SystemCfgImpl(drvClsName, url, user, pwd);
   }
}