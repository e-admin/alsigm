
package ieci.tecdoc.sbo.idoc.folder.base;

import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;

import ieci.tecdoc.sbo.util.types.SboType;

/**
 * Clase que encapsula la información referente a un documento de una 
 * carpeta. Los documentos pertenecen a la jerarquía de nodos de una 
 * carpeta.
 * 
 * @author 
 *
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocTreeNode 
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDivider 
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenFdrLink 
 */
public final class FolderTokenDocument extends FolderTokenDocTreeNode implements Serializable
{
   /** Identificador del nodo padre */
   private int     m_parentDocId;
   
   /** Identificador del fichero en base de datos */
   private int     m_fileId;
   
   
   private InputStream m_inputStreamFile;
   private Reader      m_readerAnnFile;
   
   /** 
    * Path del fichero. Sólo esta relleno si el 
    * documento es nuevo o ha sido modificado
    */
   private String  m_pathFile;
   
   /** Extensión del fichero */
   private String  m_fileExt;
   
   /** Orden del fichero dentro de la jeraquía */
   private int     m_sortOrder;
   
   /** Flag que indica si el fichero ha sido modificado */
   private boolean m_isUpdateFile;

   /** 
    * Identificador del fichero de anotaciones ó NULL_ID si no tiene ninguno asociado
    */
   private int  m_annId;
   
   /** 
    * Path del fichero de anotaciones. Sólo esta relleno si es 
    * nuevo o ha sido modificado
    */
   private String  m_pathAnnFile;   
  
   /** Flag que indica si el fichero de anotaciones ha sido modificado */
   private boolean m_isUpdateAnnFile;
  
      
   /**
    * Constructor 
    * 
    * @param id identificador del clasificador
    * @param name nombre del clasificador
    * @param parentId identificador del clasificador padre
    * @param parentDocId
    * @param fileId identificador del fichero
    * @param fileExt extensión del fichero
    * @param sortOrder Orden del fichero dentro de la jeraquía
    */
   protected FolderTokenDocument(int id, String name, int parentId, int parentDocId,
                                 int fileId, String fileExt, int sortOrder, int annId)
   {
      super(id, name, parentId);
      
      m_parentDocId     = parentDocId;
      m_pathFile        = null;
      m_inputStreamFile = null;
      m_fileId          = fileId;
      m_fileExt         = fileExt;
      m_sortOrder       = sortOrder;
      m_isUpdateFile    = false; 
      m_annId           = annId; 
      m_pathAnnFile     = null; 
      m_readerAnnFile = null;
      m_isUpdateAnnFile = false; 
      
   } 
   
   /**
    * Constructor
    * 
    * @param id identificador del clasificador
    * @param name nombre del clasificador
    * @param parentId identificador del clasificador padre
    * @param parentDocId
    * @param fileExt extensión del fichero
    * @param sortOrder Orden del fichero dentro de la jeraquía
    * @param pathFile Path del fichero
    * @param editFlag Flag de estado del nodo
    */
   protected FolderTokenDocument(int id, String name, int parentId,
                                 int parentDocId, String fileExt, int sortOrder,
                                 String pathFile, String pathAnnFile, int editFlag)
   {
      
      super(id, name, parentId, editFlag);
      
      m_parentDocId     = parentDocId;
      m_pathFile        = pathFile;
      m_inputStreamFile = null;
      m_fileId          = SboType.NULL_ID;
      m_fileExt         = fileExt;
      m_sortOrder       = sortOrder;
      m_isUpdateFile    = true; 
      m_annId           = SboType.NULL_ID; 
      m_pathAnnFile     = pathAnnFile; 
      m_readerAnnFile = null;
      m_isUpdateAnnFile = false;      
   
   }  
   
   /**
    * Constructor
    * 
    * @param id identificador del clasificador
    * @param name nombre del clasificador
    * @param parentId identificador del clasificador padre
    * @param parentDocId
    * @param fileExt extensión del fichero
    * @param sortOrder Orden del fichero dentro de la jeraquía
    * @param inputStreamFile inputStream del fichero
    * @param editFlag Flag de estado del nodo
    */
   protected FolderTokenDocument(int id, String name, int parentId,
                                 int parentDocId, String fileExt, int sortOrder,
                                 InputStream inputStreamFile, Reader readerAnnFile, int editFlag)
   {
      
      super(id, name, parentId, editFlag);
      
      m_parentDocId     = parentDocId;
      m_pathFile        = null;
      m_pathAnnFile     = null;
      m_inputStreamFile = inputStreamFile;
      m_readerAnnFile = readerAnnFile;
      m_fileId          = SboType.NULL_ID;
      m_fileExt         = fileExt;
      m_sortOrder       = sortOrder;
      m_isUpdateFile    = true;    
      m_annId           = SboType.NULL_ID; 
   
   }     
   
   /**
    * 
    * @return
    */
   public int getParentDocId()
   {
      return m_parentDocId;
   }
   
   /** 
    * Devuelve el path del fichero
    * 
    * @return path del fichero
    */
   public String getPathFile()
   {
      return m_pathFile;
   }
   
   /**
    * Devuelve el id del fichero
    * 
    * @return id del fichero
    */
   public int getFileId()
   {
      return m_fileId;
   }
   
   /**
    * Devuelve la extension del fichero
    * 
    * @return extension del fichero
    */
   public String getFileExt()
   {
      return m_fileExt;
   }
   
   /**
    * Devuelve el orden del docuemento
    * 
    * @return orden del docuemento
    */
   public int getSortOrder()
   {
      return m_sortOrder;
   }
   
   /**
    * Devuelve <tt>true</tt> si el fichero ha sido modificado
    * 
    * @return <tt>true</tt> si el fichero ha sido modificado
    */
   public boolean isUpdateFile()
   {
      return m_isUpdateFile;
   }

    /**
    * Devuelve el id del fichero de anotaciones
    * 
    * @return id del fichero de anotaciones
    */
   public int getAnnId()
   {
      return m_annId;
   }

   /** 
    * Devuelve el path del fichero de anotaciones
    * 
    * @return path del fichero de anotaciones
    */
   public String getPathAnnFile()
   {
      return m_pathAnnFile;
   }
   
   /**
    * Devuelve <tt>true</tt> si el fichero de anotaciones ha sido modificado
    * 
    * @return <tt>true</tt> si el fichero de anotaciones ha sido modificado
    */
   public boolean isUpdateAnnFile()
   {
      return m_isUpdateAnnFile;
   } 
   
   /**
    * Cambia el padre del documento
    * 
    * @param parentDocId el identificador del nuevo padre 
    */
   protected void changeParentDocId(int parentDocId)
   {
      m_parentDocId = parentDocId;
   }
   
   /**
    * Cambia el fichero asociado al documento
    * 
    * @param fileId el nuevo identificador de fichero
    */
   protected void changeFileId(int fileId)
   {
      m_fileId = fileId;
   }
   
   /**
    * Elimina el fichero asociado al documento
    *
    */
   protected void clearFile()
   {
      m_pathFile = null;
      m_inputStreamFile = null;
   }
   
   /**
    * Cambia el identificador de las anotaciones asociadas al documento
    * 
    * @param annId el nuevo identificador de las anotaciones
    */
   protected void changeAnnId(int annId)
   {
      m_annId = annId;
   }
   
   /**
    * Limpia la infprmación del fichero de anotaciones asociado al documento
    *
    */
   protected void clearAnnFile()
   {
      m_pathAnnFile = null;
      m_readerAnnFile = null;
   }
   
   /**
    * Reemplaza el fichero asociado al documento
    * 
    * @param pathFile Path del fichero
    * @param fileExt Extension del fichero
    */
   public void replaceFile(String pathFile, String fileExt)
   {
       m_pathFile     = pathFile;
       m_inputStreamFile = null;
       m_fileExt      = fileExt;
       m_isUpdateFile = true;

      if (!isModified())
         setEditFlag(FolderEditFlag.UPDATE);
   }  
   
   /**
    * Reemplaza el fichero de anotaciones asociado al documento
    * 
    * @param pathAnnFile Path del fichero
    */
   public void replaceAnnFile(String pathAnnFile)
   {
       m_pathAnnFile     = pathAnnFile;
       m_readerAnnFile = null;
       m_isUpdateAnnFile = true;

      if (!isModified())
         setEditFlag(FolderEditFlag.UPDATE);
   } 
   
   /**
    * Reemplaza el fichero asociado al documento
    * 
    * @param pathFile Path del fichero
    * @param fileExt Extension del fichero
    */
   public void replaceFile(InputStream inputStreamFile, String fileExt)
   {
       m_pathFile     = null;
       m_inputStreamFile = inputStreamFile;
       m_fileExt      = fileExt;
       m_isUpdateFile = true;

      if (!isModified())
         setEditFlag(FolderEditFlag.UPDATE);
   }  
   
   /**
    * Reemplaza el fichero de anotaciones asociado al documento
    * 
    * @param pathAnnFile Path del fichero
    */
   public void replaceAnnFile(Reader readerAnnFile)
   {
       m_pathAnnFile     = null;
       m_readerAnnFile = readerAnnFile;
       m_isUpdateAnnFile = true;

      if (!isModified())
         setEditFlag(FolderEditFlag.UPDATE);
   } 
  
   /**
    * @see Object#toString()
    */
   public String toString() {
      
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("FolderTokenDocument[");
      buffer.append(super.toString());
      buffer.append(", m_parentDocId = ").append(m_parentDocId);
      buffer.append(", m_fileId = ").append(m_fileId);
      buffer.append(", m_pathFile = ").append(m_pathFile);      
      buffer.append(", m_fileExt = ").append(m_fileExt);
      buffer.append(", m_sortOrder = ").append(m_sortOrder);
      buffer.append(", m_isUpdateFile = ").append(m_isUpdateFile);
      buffer.append(", m_annId = ").append(m_annId);
      buffer.append(", m_pathAnnFile = ").append(m_pathAnnFile); 
      buffer.append(", m_readerAnnFile = ").append(m_pathAnnFile);
      buffer.append(", m_isUpdateAnnFile = ").append(m_isUpdateAnnFile);
      buffer.append("]");
      
      return buffer.toString();
   }
   
   /**
    * Devuelve el inputStream del fichero
    * @return
    */
   public InputStream getInputStreamFile()
   {
     return m_inputStreamFile;
   }
   
   public Reader getReaderAnnFile()
   {
     return m_readerAnnFile;
   }
} // class
