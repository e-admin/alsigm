
package es.ieci.tecdoc.isicres.admin.estructura.factory;



import es.ieci.tecdoc.isicres.admin.estructura.dao.Archive;
import es.ieci.tecdoc.isicres.admin.estructura.dao.ArchiveAccess;
import es.ieci.tecdoc.isicres.admin.estructura.dao.ArchiveFlds;
import es.ieci.tecdoc.isicres.admin.estructura.dao.ArchiveIdx;
import es.ieci.tecdoc.isicres.admin.estructura.dao.ArchiveIdxs;
import es.ieci.tecdoc.isicres.admin.estructura.dao.ArchiveMisc;
import es.ieci.tecdoc.isicres.admin.estructura.dao.ArchiveUpdInfo;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Archives;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Department;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Directories;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Directory;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Group;
import es.ieci.tecdoc.isicres.admin.estructura.dao.GroupUserRel;
import es.ieci.tecdoc.isicres.admin.estructura.dao.LdapGroup;
import es.ieci.tecdoc.isicres.admin.estructura.dao.LdapUser;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Repository;
import es.ieci.tecdoc.isicres.admin.estructura.dao.SystemCfg;
import es.ieci.tecdoc.isicres.admin.estructura.dao.User;
import es.ieci.tecdoc.isicres.admin.estructura.dao.UserAccess;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Volume;
import es.ieci.tecdoc.isicres.admin.estructura.dao.VolumeList;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.ArchiveAccessImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.ArchiveFldImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.ArchiveFldsImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.ArchiveIdxImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.ArchiveIdxsImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.ArchiveImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.ArchiveMiscImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.ArchivePermsImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.ArchiveUpdInfoImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.ArchivesImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.DepartmentImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.DirectoriesImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.DirectoryImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.GroupImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.GrpUsrRelImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.LdapGroupImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.LdapUserImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.RepositoryImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.SystemCfgImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.UserAccessImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.UserImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.VolumeImpl;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.VolumeListImpl;

/**
 * Factoría de creación de objetos de administración
 */

public class ISicresAdminObjectFactory
{
   private ISicresAdminObjectFactory()
   {
   }

   /**
    * Crea una instancia (sin valores iniciales) de un usuario Ldap.
    *
    * @return La instancia del usuario creado.
    */

   public static LdapUser createLdapUser()
   {
      return new LdapUserImpl();
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
      return (Directories)(new DirectoriesImpl());
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
   public static Archive createArchivePerms()
   {
      return (Archive)(new ArchivePermsImpl());
   }

   /**
    * Crea una instancia de la estructura de un campo
    * @return la instancia de la estructura
    */
   public static Archive createArchiveFld()
   {
      return (Archive)new ArchiveFldImpl();
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
   public static Volume createVolume(int userConnected, int repId, String entidad)
   {
      return new VolumeImpl(userConnected, repId, entidad);
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