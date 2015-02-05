
package ieci.tecdoc.sbo.idoc.archive.base;

import java.io.Serializable;

public final class ArchiveTokenFldVld implements Serializable
{
   
   private int     m_id;
   private String  m_fmtInfo;
   private int     m_vldType;
   private int     m_vldParam1;
   private int     m_vldParam2;  
   private boolean m_isRequired;
   private ArchiveTokenFldVld m_hierarchicalArchiveTokenFldVld;
      
//   public ArchiveTokenFldVld()
//   {
//      m_id         = 0;
//      m_fmtInfo    = null;     
//      m_vldType    = ArchiveValidationType.NONE;
//      m_vldParam1  = 0;
//      m_vldParam1  = 0;   
//      m_isRequired = false;
//      m_hierarchicalArchiveTokenFldVld = null;
//   }
   
   public ArchiveTokenFldVld(int id, String fmtInfo, int vldType, int vldParam1, 
                             int vldParam2, boolean isRequired)
   {
      m_id         = id;
      m_fmtInfo    = fmtInfo;
      m_vldType    = vldType;
      m_vldParam1  = vldParam1;
      m_vldParam2  = vldParam2;
      m_isRequired = isRequired;
      
      m_hierarchicalArchiveTokenFldVld = null;
      
	  // TODO. Parche Temporal.
      parche ();
   }
   
   public ArchiveTokenFldVld(int id, String fmtInfo, int vldType, int vldParam1, 
            int vldParam2, boolean isRequired,ArchiveTokenFldVld hierarchical)
   {
			m_id         = id;
			m_fmtInfo    = fmtInfo;
			m_vldType    = vldType;
			m_vldParam1  = vldParam1;
			m_vldParam2  = vldParam2;
			m_isRequired = isRequired;
			
			m_hierarchicalArchiveTokenFldVld = hierarchical;
			
		    // TODO. Parche Temporal.
		    parche ();
   }
   
   private void parche () {
      // TODO. Parche Temporal.
      // Las validaciones sobre tablas autonuméricas no están contempladas.
      // Hasta que se implemente, estos campos se tratarán como si no tuviesen validación.
      if (m_vldType == 7) {
    	  m_vldType = 0;
      }
   }
   
   public int getId()
   {
      return m_id;
   }
   
   public String getFmtInfo()
   {
      return m_fmtInfo;
   }
   
   public int getVldType()
   {
      return m_vldType;
   }
   
   public int getVldParam1()
   {
      return m_vldParam1;
   }
   
   public int getVldParam2()
   {
      return m_vldParam2;
   }
   
   public boolean isRequired()
   {
      return m_isRequired;
   }
   
   /**
    * toString methode: creates a String representation of the object
    * @return the String representation
    */
   public String toString() {
    
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("ArchiveTokenFldVld[");
      buffer.append("m_id = ").append(m_id);
      buffer.append(", m_fmtInfo = ").append(m_fmtInfo);
      buffer.append(", m_vldType = ").append(m_vldType);
      buffer.append(", m_vldParam1 = ").append(m_vldParam1);
      buffer.append(", m_vldParam2 = ").append(m_vldParam2);
      buffer.append(", m_isRequired = ").append(m_isRequired);
      buffer.append("]");
      
      return buffer.toString();
   }
   
   public boolean hasHierarchical () {
      return m_hierarchicalArchiveTokenFldVld != null;
   }
   
   public ArchiveTokenFldVld getHierarchicalArchiveTokenFldVld()
   {
      return m_hierarchicalArchiveTokenFldVld;
   }
   
   public void setHierarchicalArchiveTokenFldVld(
            ArchiveTokenFldVld archiveTokenFldVld)
   {
      m_hierarchicalArchiveTokenFldVld = archiveTokenFldVld;
   }
} // class
