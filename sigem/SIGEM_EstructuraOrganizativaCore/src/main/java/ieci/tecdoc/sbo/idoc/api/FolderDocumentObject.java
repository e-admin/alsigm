
package ieci.tecdoc.sbo.idoc.api;

import java.io.InputStream;
import java.io.Reader;

import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocument;

/**
 * Contiene la información referente a un documento de una carpeta. 
 * 
 */
public final class FolderDocumentObject 
{
   /** Atributo que encapsula la información referente a un documento */
   FolderTokenDocument m_doc;
   
   /**
    * Constructor
    * 
    * @param doc objeto FolderTokenDocument
    */
   protected FolderDocumentObject(FolderTokenDocument doc)
   {
      m_doc = doc;   
   }
  
   /**
    * Devuelve el identificador del documento
    * 
    * @return identificador del documento
    */
   public int getId()
   {
      return m_doc.getId();
   }
   
   /**
    * Devuelve el nombre del documento
    * 
    * @return el nombre del documento
    */
   public String getName()
   {
      return m_doc.getName();
   }
   
   /**
    * Devuelve el identificador del clasificador padre
    * 
    * @return el identificador del clasificador padre
    */
   public int getParentId()
   {
      return m_doc.getParentId();
   }   
      
   /**
    * Devuelve la extension del fichero del documento
    * 
    * @return la extension del fichero del documento
    */
   public String getFileExt()
   {
      return m_doc.getFileExt();
   }
   
   /**
    * Devuelve el número de orden dentro del clasificador
    * 
    * @return el número de orden del documento
    */
   public int getSortOrder()
   {
      return m_doc.getSortOrder();
   }   
  
   /**
    * Reemplaza el fichero del documento
    * 
    * @param pathFile path del nuevo documento
    * @param fileExt extension del nuevo documento
    */
   public void replaceFile(String pathFile, String fileExt)
   {
       m_doc.replaceFile(pathFile, fileExt);
   } 
   
   
   /**
    * Reemplaza el fichero de anotaciones del documento
    * 
    * @param pathFile path del nuevo fichero de anotaciones    
    */
   public void replaceAnnFile(String pathAnnFile)
   {
       m_doc.replaceAnnFile(pathAnnFile);
   } 
   
   /**
    * Reemplaza el fichero del documento
    * 
    * @param pathFile path del nuevo documento
    * @param fileExt extension del nuevo documento
    */
   public void replaceFile(InputStream inputStreamFile, String fileExt)
   {
       m_doc.replaceFile(inputStreamFile, fileExt);
   } 
   
   
   /**
    * Reemplaza el fichero de anotaciones del documento
    * 
    * @param pathFile path del nuevo fichero de anotaciones    
    */
   public void replaceAnnFile(Reader readerAnnFile)
   {
       m_doc.replaceAnnFile(readerAnnFile);
   } 
   
} // class
