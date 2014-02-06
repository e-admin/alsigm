
package ieci.tecdoc.sbo.idoc.api;

import java.io.File;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.sbo.idoc.folder.std.FolderMdoFdr;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenFlds;
import ieci.tecdoc.sbo.idoc.folder.base.FolderToken;

/**
 * Esta clase actúa como una fachada para las carpetas. Proporciona un 
 * conjunto de métodos sencillos para realizar operaciones sobre carpetas.
 * 
 * @author 
 */

public final class Folder
{
   /** Configuración de la conexión con base de datos */
   DbConnectionConfig m_dbConnConfig;
   
   /** Ruta del fichero de configuración de conexión a base de datos */
   String             m_configDir;
   
   /**
    * Constructor  
    * 
    * @throws Exception
    */
   public Folder() throws Exception
   {
      m_dbConnConfig = null;
      m_configDir    = null; 
   }
   
   /**
    * Constructor 
    * 
    * @param configDir Ruta donde se encuentra el fichero de configuración 
    * de base de datos
    * @throws Exception
    */
   public Folder(String configDir) throws Exception
   {
      m_dbConnConfig = null;
      m_configDir    = configDir;     
   }

   /**
    * Devuelve la conexión a base de datos. Si la conexión es nula la crea 
    * a través del fichero de configuración de base de datos. 
    * 
    * @return Conexión con la base de datos
    * @throws Exception
    */
   private DbConnectionConfig getDbConfig() throws Exception
   {
      if (m_dbConnConfig == null)
      {
         if (m_configDir == null)
         {
            m_dbConnConfig = CfgMdoConfig.getDbConfig();
         }
         else
         {
            m_dbConnConfig = CfgMdoConfig.getDbConfig(m_configDir);
         }
      }
      
      return  m_dbConnConfig;
   }
   
   /**
    * Establece la configuración de conexión de base de datos
    * 
    * @param dbConnConfig configuración de conexión de base de datos
    * @throws Exception
    */
   public void setConnectionConfig(DbConnectionConfig dbConnConfig)
               throws Exception
   {
      m_dbConnConfig = dbConnConfig;
   }

   /**
    * Devuelve <tt>true</tt> si el usuario tiene permisos de acceso sobre 
    * la carpeta
    * 
    * @param acs Objeto AcsAccessObject con los permisos del usuario
    * Si se recibe <tt>null</tt> no se chequean permisos
    * @param arch archivador al que pertenece la carpeta
    * @param fdrId Identificador de la carpeta
    * @return <tt>true</tt> si el usuario tiene permisos de acceso sobre 
    * la carpeta. <tt>false</tt> en caso contrario
    * @throws Exception 
    */
   public boolean canLoadFolder(AcsAccessObject acs, ArchiveObject arch,
                                int fdrId) throws Exception
   {
      boolean can = false;
      
      try
      {
         
         DbConnection.open(getDbConfig());		        

         can = FolderMdoFdr.canLoadFolder(acs.getAccessToken(),
                                          arch.getArchiveToken(),
                                          fdrId);

         DbConnection.close();
         
         return can;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
         return can;
      }
            
   }
   
   /**
    * Obtiene la información sobre los campos que forman la carpeta
    * 
    * @param acs Objeto AcsAccessObject con los permisos del usuario.
    * Si se recibe <tt>null</tt> no se chequean permisos
    * @param arch archivador al que pertenece la carpeta
    * @param fdrId identificador de la carpeta
    * @return la información sobre los campos que formanb la carpeta
    * @throws Exception 
    */
   public FolderFieldObjects fetchFolderValues(AcsAccessObject acs,
                                               ArchiveObject arch, int fdrId)
							     throws Exception
   {
      
      FolderTokenFlds   token = null;
      FolderFieldObjects flds = null;
      
      try
      {
         
         DbConnection.open(getDbConfig());
         
         if (acs == null)
            token = FolderMdoFdr.fetchFolderValues(null,arch.getArchiveToken(), fdrId);
         else
            token = FolderMdoFdr.fetchFolderValues(acs.getAccessToken(),
                                                   arch.getArchiveToken(), fdrId);

         DbConnection.close();

         flds = new FolderFieldObjects(token);
         
         return flds;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
         return null;
      }
            
   }
   
   /**
    * Crea un objeto FolderObject con la información de una carpeta
    * concreta. Antes de crearlo verifica si el usuario que trata de 
    * obtener dicho FolderObject tiene permisos sobre el archivador.  
    * <p>
    * La información de los campos de la carpeta se obtienen de base 
    * de datos
    * </p>  
    * 
    * @param acs Objeto AcsAccessObject con los permisos del usuario.
    * Si se recibe <tt>null</tt> no se chequean permisos
    * @param userId identificador de usuario
    * @param arch archivador que contiene la carpeta
    * @param fdrId identificador de la carpeta
    * @return FoloderObject con la información de la carpeta.
    * @throws Exception arroja una IeciTdException en caso de que el
    * usuario no tenga permisos de acceso sobre la carpeta 
    * 
    */
   public FolderObject loadFolder(AcsAccessObject acs, int userId,
                                  ArchiveObject arch, int fdrId)
							 throws Exception
   {
      
      FolderToken token = null; 
      FolderObject fdr  = null;
      
      try
      {
         
         DbConnection.open(getDbConfig());
         
         if (acs == null)
            token = FolderMdoFdr.loadFolder(null, userId, arch.getArchiveToken(), fdrId);
         else
            token = FolderMdoFdr.loadFolder(acs.getAccessToken(), userId,
                                            arch.getArchiveToken(), fdrId);

         DbConnection.close();

         fdr = new FolderObject(token);
         
         return fdr;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
         return null;
      }
            
   }
   
   /**
    * Crea un objeto FolderObject con la información de una carpeta
    * concreta. Antes de crearlo verifica si el usuario que trata de 
    * obtener dicho FolderObject tiene permisos sobre el archivador.
    * <p>
    * La información de los campos de la carpeta se pasan como parámetro,
    * de modo que no se obtienen de base de datos
    * </p>  
    * 
    * @param acs Objeto AcsAccessObject con los permisos del usuario.
    * Si se recibe <tt>null</tt> no se chequean permisos
    * @param userId identificador del usuario
    * @param arch archivador que contiene la carpeta
    * @param fdrId identificador de la carpeta
    * @param fldsValues lista de campos que forman la carpeta
    * @return FoloderObject con la información de la carpeta.
    * @throws Exception arroja una IeciTdException en caso de que el
    * usuario no tenga permisos de acceso sobre la carpeta 
    * 
    */
   public FolderObject loadFolder(AcsAccessObject acs,int userId,
                                  ArchiveObject  arch, int fdrId, 
                                  FolderFieldObjects fldsValues)
							 throws Exception
   {
      
      FolderToken  token = null;
      FolderObject fdr  = null; 
      
      try
      {
         
         DbConnection.open(getDbConfig());		        

         if (acs == null)
            token = FolderMdoFdr.loadFolder(null, userId, 
                                            arch.getArchiveToken(), fdrId,
                                            fldsValues.getFieldsToken());
         else
            token = FolderMdoFdr.loadFolder(acs.getAccessToken(), userId, 
                                            arch.getArchiveToken(), fdrId,
                                            fldsValues.getFieldsToken());

         DbConnection.close();

         fdr = new FolderObject(token);
         
         return fdr;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
         return null;
      }
            
   }
   
   /**
    * Devuelve <tt>true</tt> si el usuario tiene permisos para eliminar
    * la carpeta especificada
    * 
    * @param acs Objeto AcsAccessObject con los permisos del usuario.
    * Si se recibe <tt>null</tt> no se chequean permisos
    * @param arch archivador al que pertenece la carpeta
    * @param fdrId Identificador de la carpeta
    * @return <tt>true</tt> si el usuario tiene permisos para eliminar
    * la carpeta. <tt>false</tt> en caso contrario 
    * @throws Exception 
    */
   public boolean canRemoveFolder(AcsAccessObject acs,
                                  ArchiveObject arch, int fdrId) throws Exception
   {
      boolean can = false;
      
      try
      {
         
         DbConnection.open(getDbConfig());		        

         can = FolderMdoFdr.canRemoveFolder(acs.getAccessToken(),
                                            arch.getArchiveToken(), fdrId);

         DbConnection.close();
         
         return can;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
         return can;
      }
            
   }

   /**
    * Elimina la carpeta indicada, si el usuario tiene permiso para ello
    * 
    * @param acs Objeto AcsAccessObject con los permisos del usuario.
    * Si se recibe <tt>null</tt> no se chequean permisos
    * @param userId identificador de usuario
    * @param arch archivador al que pertenece la carpeta
    * @param fdrId Identificador de la carpeta
    * @throws Exception arroja una {@link IeciTdException} en caso de que el
    * usuario no tenga permisos para eliminar la carpeta
    */
   public void removeFolder(AcsAccessObject acs, int userId,
                            ArchiveObject arch, int fdrId)
				  throws Exception
   {
            
      try
      {
         
         DbConnection.open(getDbConfig());
         
         if (acs == null)
            FolderMdoFdr.removeFolder(null, userId, 
                                      arch.getArchiveToken(), fdrId);
         else
            FolderMdoFdr.removeFolder(acs.getAccessToken(), userId, 
                                      arch.getArchiveToken(), fdrId);

         DbConnection.close();
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
      }
            
   }
   
   /**
    * Devuelve <tt>true</tt> si el usuario tiene permisos para crear
    * una carpeta en un determinado archivador
    * 
    * @param acs Objeto AcsAccessObject con los permisos del usuario.
    * Si se recibe <tt>null</tt> no se chequean permisos
    * @param arch archivador al que pertenece la carpeta
    * @return <tt>true</tt> si el usuario tiene permisos para crear
    * la carpeta. <tt>false</tt> en caso contrario 
    * @throws Exception 
    */
   public boolean canCreateFolder(AcsAccessObject acs, ArchiveObject arch)
                  throws Exception
   {
      boolean can = false;
      
      try
      {
         
         DbConnection.open(getDbConfig());		        

         can = FolderMdoFdr.canCreateFolder(acs.getAccessToken(), arch.getArchiveToken());

         DbConnection.close();
         
         return can;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
         return can;
      }
            
   }
   
   /**
    * Verifica si el usuario tiene permisos para crear una carpeta en
    * un determinado archivador, y si es así devuelve un {@link FolderObject}
    * con el esqueleto de dicha carpeta. 
    * 
    * @param acs Objeto AcsAccessObject con los permisos del usuario.
    * Si se recibe <tt>null</tt> no se chequean permisos
    * @param arch archivador al que pertenece la carpeta
    * @return Objeto {@link FolderObject} con el esqueleto de la carpeta
    * @throws Exception arroja una {@link IeciTdException} en caso de que el
    * usuario no tenga permisos para eliminar la carpeta
    */
   public FolderObject newFolder(AcsAccessObject acs, ArchiveObject arch)
							 throws Exception
   {
      
      FolderToken  token = null;
      FolderObject fdr  = null;  
      
      try
      {
         
         DbConnection.open(getDbConfig());
         
         if (acs == null)
            token = FolderMdoFdr.newFolder(null, arch.getArchiveToken());
         else
            token = FolderMdoFdr.newFolder(acs.getAccessToken(),
                                           arch.getArchiveToken());

         DbConnection.close();

         fdr = new FolderObject(token);
         
         return fdr;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
         return null;
      }
            
   }
   
   /**
    * Crea una carpeta en el archivador especificado si el usuario tiene
    * permisos para ello. 
    * 
    * @param userId identificador de usuario
    * @param acs objeto AcsAccessObject con los permisos del usuario.
    * Si se recibe <tt>null</tt> no se chequean permisos
    * @param fdr carpeta que se va a crear
    * @throws Exception arroja una {@link IeciTdException} en caso de que el
    * usuario no tenga permisos para crear la carpeta
    * @see Folder#newFolder(AcsAccessObject, ArchiveObject)
    */
   public void createFolder(int userId, ArchiveObject arch, FolderObject fdr)
				   throws Exception
   {
            
      try
      {
         
         DbConnection.open(getDbConfig());		        

         FolderMdoFdr.createFolder(userId, arch.getArchiveToken(), 
                                   fdr.getFolderToken());

         DbConnection.close();
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
      }
            
   }
   
   /**
    * Obtiene el contenido en bytes de un documento de la carpeta
    * especificada. 
    * 
    * @param acs Objeto AcsAccessObject con los permisos del usuario.
    * Si se recibe <tt>null</tt> no se chequean permisos
    * @param arch archivador al que pertenece la carpeta
    * @param fdr carpeta que se va a crear
    * @param docId identificador del documento
    * @return un array de bytes con el contenido de un documento de la carpeta
    * especificada. 
    * @throws Exception si se produce un error al leer el fichero
    */
   public byte[] retrieveFolderDocumentFile(AcsAccessObject acs, 
                                            ArchiveObject arch,
                                            FolderObject fdr,
                                            int docId)
				     throws Exception
   {
      byte[] fileData = null;
            
      try
      {
         
         DbConnection.open(getDbConfig());	
         
         if (acs == null)
            fileData = FolderMdoFdr.retrieveFolderDocumentFile(null,
                                                               arch.getArchiveToken(),
                                                               fdr.getFolderToken(),
                                                               docId);
         else
            fileData = FolderMdoFdr.retrieveFolderDocumentFile(acs.getAccessToken(),
                                                               arch.getArchiveToken(),
                                                               fdr.getFolderToken(),
                                                               docId);

         DbConnection.close();
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);
         return null;  
      }
      
      return fileData;
            
   }
   
   /**
    * Obtiene tamaño de un documento de la carpeta especificada. 
    * 
    * @param acs Objeto AcsAccessObject con los permisos del usuario.
    * Si se recibe <tt>null</tt> no se chequean permisos
    * @param arch archivador al que pertenece la carpeta
    * @param fdr carpeta que se va a crear
    * @param docId identificador del documento
    * @return el tamaño de un documento de la carpeta especificada. 
    * @throws Exception si se produce un error al leer el fichero
    */
   public int getFileSize (AcsAccessObject acs, 
	   ArchiveObject arch,
	   FolderObject fdr,
	   int docId)
	throws Exception
	{
		int size = -1;
		
		try
		{
		
			DbConnection.open(getDbConfig());	
			
			if (acs == null)
				size = FolderMdoFdr.getFileSize (null,
				                      arch.getArchiveToken(),
				                      fdr.getFolderToken(),
				                      docId);
			else
				size = FolderMdoFdr.getFileSize (acs.getAccessToken(),
				                      arch.getArchiveToken(),
				                      fdr.getFolderToken(),
				                      docId);
			
			DbConnection.close();
		 
		}
		catch (Throwable e)
		{
			if (e instanceof Exception)
				DbConnection.ensureClose ((Exception) e);
			else
				DbConnection.ensureClose (new Exception (e));
		}
		
		return size;
	
	}
   
   
   
   /**
    * Obtiene los bytes del fichero de anotaciones asociado al documento de la carpeta
    * especificada. 
    * 
    * @param acs Objeto AcsAccessObject con los permisos del usuario.
    * Si se recibe <tt>null</tt> no se chequean permisos
    * @param arch archivador al que pertenece la carpeta
    * @param fdr carpeta que se va a crear
    * @param docId identificador del documento
    * @return un array de bytes con el contenido de la anotaciones asociadas al docu
    * mento de la carpeta especificada. 
    * @throws Exception si se produce un error al leer el fichero
    */
   public String retrieveFolderDocumentAnnData(AcsAccessObject acs, 
                                               ArchiveObject arch,
                                               FolderObject fdr,
                                               int docId)
                 throws Exception
   {
      String data = null;
            
      try
      {
         
         DbConnection.open(getDbConfig());   
         
         if (acs == null)
            data = FolderMdoFdr.retrieveFolderDocumentAnnData(null,
                                                              arch.getArchiveToken(),
                                                              fdr.getFolderToken(),
                                                              docId);
         else
            data = FolderMdoFdr.retrieveFolderDocumentAnnData(acs.getAccessToken(),
                                                              arch.getArchiveToken(),
                                                              fdr.getFolderToken(),
                                                              docId);

         DbConnection.close();
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);
         return null;  
      }
      
      return data;
            
   }
   
   /**
    * Devuelve <tt>true</tt> si el usuario tiene permisos para modificar
    * una carpeta en un determinado archivador
    * 
    * @param acs Objeto AcsAccessObject con los permisos del usuario.
    * Si se recibe <tt>null</tt> no se chequean permisos
    * @param arch archivador al que pertenece la carpeta
    * @param fdrId identificador de la carpeta
    * @return <tt>true</tt> si el usuario tiene permisos para modificar
    * la carpeta. <tt>false</tt> en caso contrario 
    * @throws Exception 
    */
   public boolean canEditFolder(AcsAccessObject acs, ArchiveObject arch,
                                int fdrId)
                  throws Exception
   {
      boolean can = false;
      
      try
      {
         
         DbConnection.open(getDbConfig());		        

         can = FolderMdoFdr.canEditFolder(acs.getAccessToken(), 
                                          arch.getArchiveToken(), fdrId);

         DbConnection.close();
         
         return can;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
         return can;
      }
            
   }
   
   /**
    * Prepara una carpeta para su modificación en el archivador especificado si 
    * el usuario tiene permisos para ello.
    * <p>
    * Lo que hace realmente es bloquear dicha carpeta por el usuario para que esta 
    * pueda ser modificada
    * </p> 
    * 
    * @param acs objeto AcsAccessObject con los permisos del usuario.
    * Si se recibe <tt>null</tt> no se chequean permisos
    * @param userId identificador de usuario
    * @param arch archivador al que pertenece la carpeta
    * @param fdrId identificador de carpeta
    * @throws Exception arroja una {@link IeciTdException} en caso de que el
    * usuario no tenga permisos para modificar o que la carpeta se encuentre
    * bloqueada por otro usuario
    * @see Folder#terminateEditFolder(int, ArchiveObject, int)
    * @see Folder#storeFolder(int, ArchiveObject, FolderObject)
    */
   public void editFolder(AcsAccessObject acs, int userId,
                          ArchiveObject arch, int fdrId)
				   throws Exception
   {
            
      try
      {
         
         DbConnection.open(getDbConfig());		        

         if (acs == null)
            FolderMdoFdr.editFolder(null, userId, 
                                    arch.getId(), fdrId);
         else
            FolderMdoFdr.editFolder(acs.getAccessToken(), userId, 
                                    arch.getId(), fdrId);

         DbConnection.close();
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
      }
            
   }
   
   /**
    * Termina el modo edición de la carpeta para el usuario. Es decir, desbloquea
    * la carpeta (si es que realmente estaba bloqueada por dicho usuario). 
    * 
    * @param userId identificador de usuario
    * @param arch archivador al que pertenece la carpeta
    * @param fdrId identificador de carpeta
    * @throws Exception arroja una {@link IeciTdException} en caso de que el
    * usuario no tenga permisos para modificar
    * @see Folder#editFolder(AcsAccessObject, int, ArchiveObject, int)
    * @see Folder#storeFolder(int, ArchiveObject, FolderObject)
    */
   public void terminateEditFolder(int userId, ArchiveObject arch, int fdrId)
				  throws Exception
   {
            
      try
      {
         
         DbConnection.open(getDbConfig());		        

         FolderMdoFdr.terminateEditFolder(userId, arch.getId(), fdrId);

         DbConnection.close();
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
      }
            
   }
   
   public boolean isFolderLocked (int archId, int fdrId) throws Exception
   {
      boolean rc = false;
      
      try
      {
         DbConnection.open(getDbConfig());
         rc = FolderMdoFdr.isFolderLocked(archId, fdrId);
         DbConnection.close();
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e); 
      }
      
      return rc;
   }
   
   public boolean isFolderLockedByUser (int userId, int archId, int fdrId) throws Exception
   {
      boolean rc = false;
      
      try
      {
         DbConnection.open(getDbConfig());
         rc = FolderMdoFdr.isFolderLockedByUser(userId, archId, fdrId);
         DbConnection.close();
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e); 
      }
      
      return rc;
   }   
   
   /**
    * Almacena una carpeta que estaba siendo editada en base de datos
    * 
    * @param userId identificador de usuario
    * @param arch archivador al que pertenece la carpeta
    * @param fdrId identificador de carpeta
    * @throws Exception arroja IeciTdException si el usuario no tiene
    * previamente bloqueada la carpeta mediante el método editFolder
    * @see Folder#editFolder(AcsAccessObject, int, ArchiveObject, int)
    * @see Folder#terminateEditFolder(int, ArchiveObject, int)
    */
   public void storeFolder(int userId, ArchiveObject arch, FolderObject fdr)
				   throws Exception
   {
            
      try
      {
         
         DbConnection.open(getDbConfig());		        

         FolderMdoFdr.storeFolder(userId, arch.getArchiveToken(),
                                  fdr.getFolderToken());

         DbConnection.close();
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
      }
            
   }
   
   
   // PARCHE P9.0.2. Permite recuperar el contenido del documento en un File
   /**
    * Obtiene un fichero (File) con el contenido de un documento de la carpeta
    * especificada. 
    * 
    * @param acs Objeto AcsAccessObject con los permisos del usuario.
    * Si se recibe <tt>null</tt> no se chequean permisos
    * @param arch archivador al que pertenece la carpeta
    * @param fdr carpeta que se va a crear
    * @param docId identificador del documento
    * @param parentFolder Carpeta donde se creará el fichero que se devuelve.
    * Puede ser nulo,en cuyo caso se creará un fichero temporal.
    * @return una referencia al fichero creado. 
    * @throws Exception si se produce un error al leer el fichero
    */
   public File retrieveFolderDocumentFile(AcsAccessObject acs, 
                                            ArchiveObject arch,
                                            FolderObject fdr,
                                            File parentFolder,
                                            int docId)
                     throws Exception
   {
      File file = null;
            
      try
      {
         
         DbConnection.open(getDbConfig());  
         
         if (acs == null)
             file = FolderMdoFdr.retrieveFolderDocumentFile(null,
                                                               arch.getArchiveToken(),
                                                               fdr.getFolderToken(),
                                                               docId,
                                                               parentFolder);
         else
             file = FolderMdoFdr.retrieveFolderDocumentFile(acs.getAccessToken(),
                                                               arch.getArchiveToken(),
                                                               fdr.getFolderToken(),
                                                               docId,
                                                               parentFolder);

         DbConnection.close();
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);
         return null;  
      }
      
      return file;
            
   }

   
   
   
} // class
