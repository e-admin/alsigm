
package ieci.tecdoc.sbo.idoc.acs;

public final class AcsInfo
{
   
   private int  m_acsType;
   private int  m_acsId;
            
   public AcsInfo(int acsType, int acsId)
   {
      m_acsType  = acsType;
      m_acsId    = acsId;      
   }
   
   public int getAcsType()
   {
      return m_acsType;
   }
   
   public int getAcsId()
   {
      return m_acsId;
   }
   
   
   /**
    * toString methode: creates a String representation of the object
    * @return the String representation
    */
   public String toString()
   {
      StringBuffer buffer   = new StringBuffer();     
      
      buffer.append("AcsInfo[");
      buffer.append("m_acsType = ").append(m_acsType);
      buffer.append(", m_acsId = ").append(m_acsId);
      
      return buffer.toString();
   }
} // class
