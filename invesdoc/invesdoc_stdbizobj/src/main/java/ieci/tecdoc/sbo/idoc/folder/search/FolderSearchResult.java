package ieci.tecdoc.sbo.idoc.folder.search;

import java.io.Serializable;

import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.core.exception.IeciTdException;


import org.apache.log4j.Logger;

/**
 * Este método encapsula los resultados de una búsqueda de carpetas. Los
 * resultados corresponden con los identificadores de las carpetas
 * @author
 */

public final class FolderSearchResult implements Serializable
{      
   /**
    * Coleccion de identificadores de carpetas
    */
   private IeciTdLongIntegerArrayList m_fdrIds;
   
   /**
    * Logger
    */
   private static Logger logger = Logger.getLogger( FolderSearchResult.class );
    
   /**
    * Constructor
    */
   public FolderSearchResult()
   {   
      m_fdrIds = new IeciTdLongIntegerArrayList();         
   }
      
   /**
    * Añade un nuevo identificador de carpeta a la colección
    * @param fdrId identificador de carpeta
    */
   protected void addFolder(int fdrId) 
   {
      m_fdrIds.add(fdrId);      
   }
      
   /**
    * Elimina un identificador de carpeta de la colección
    * @param folderId identificador de carpeta
    * @throws Exception si no encuentra el identificador de la carpeta 
    */
   public void removeFolderId(int folderId) throws Exception 
   {
       int idx = this.m_fdrIds.indexOf(folderId);
       if(idx == -1) 
       {
          throw new IeciTdException(FolderSearchError.EC_INVALID_PARAM, 
                   FolderSearchError.EM_INVALID_PARAM);
       }
       this.m_fdrIds.remove(idx);
       
   }
   
   /**
    * devuelte el número de carpetas que pertenecen al resultado de la búsqueda
    * @return número de carpetas que pertenecen al resultado de la búsqueda
    */
   public int count()
   {
      return m_fdrIds.count();
   } 
   
   /**
    * Devuelve el identificador de la carpeta i-ésimo de la colección
    * @param idx índice
    * @return identificador de la carpeta
    * @throws Exception si el índice no se encuentra en los límites de la colección
    */
   public int getFolderId(int idx) throws Exception
   {
      int fdrId ;
      
      if ( (idx + 1) > m_fdrIds.count() || (idx < 0) )
      {
         throw new IeciTdException(FolderSearchError.EC_INVALID_PARAM, 
                                   FolderSearchError.EM_INVALID_PARAM);
      
      }
      
      fdrId = m_fdrIds.get(idx);
      
      return fdrId;
   }

   /**
    * Devuelve la colección de identificadores de carpetas que son resultado de una búsqueda
    * @return referencia a un objeto de tipo IeciTdLongIntegerArrayList que es una colección de
    * identificadores de carpetas
    * @see IeciTdLongIntegerArrayList
    */
   public IeciTdLongIntegerArrayList getFolderIds() 
   {
      return m_fdrIds;
   }   
   
   /**
    * Devuelve un string con la representación del objeto, es decir, la colección de identificador
    * de carpetas
    * @return representación del objeto, es decir, la colección de identificadores de carpeta
    */
   public String toString() {
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("FolderSearchResult[");
      
      for(int i = 0; i < m_fdrIds.count(); i++)
      {

          buffer.append(" [folderId").append(i+1);
          buffer.append(" = ").append((m_fdrIds.get(i)));
          buffer.append("] ");

      }
      
      buffer.append("]");
      
      return buffer.toString();
   }
} // class
