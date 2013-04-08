
package ieci.tecdoc.sbo.idoc.api;

import java.io.Serializable;

import ieci.tecdoc.sbo.idoc.archive.base.ArchiveToken;

/**
 * Contiene toda la información relevante de un archivador. 
 * 
 */
public final class ArchiveObject implements Serializable
{   
   /** El ArchiveToken con la información del archivador */
   protected ArchiveToken m_arch;   
     
   /**
    * Constructor
    * 
    * @param arch El ArchiveToken con la información del archivador
    */
   protected ArchiveObject(ArchiveToken arch)
   {
      m_arch = arch;
   }

   /**
    * Devuelve el ArchiveToken con la información del archivador
    * 
    * @return El ArchiveToken con la información del archivador
    */
   public ArchiveToken getArchiveToken()
   {
      return m_arch;
   }   
   
   /**
    * Devuelve el identificador del archivador
    * 
    * @return el identificador del archivador
    */
   public int getId()
   {
      return m_arch.getId();
   }

   /**
    * Devuelve el nombre del archivador
    * 
    * @return El nombre del archivador
    */
   public String getName()
   {
      return m_arch.getArchHdr().getName();
   }

   
  
} // class
