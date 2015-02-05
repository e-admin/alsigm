package ieci.tecdoc.sbo.acs.base;

import org.apache.log4j.Logger;

import ieci.tecdoc.sbo.util.types.SboType;

import java.io.Serializable;


public final class AcsAccessTokenUser implements Serializable
{
   /**
    * Log4J Logger for this class
    */
   private static final Logger logger = Logger.getLogger(AcsAccessTokenUser.class);

   //~ Instance fields ---------------------------------------------------------

   private int    m_id;
   private String m_profile;
   private int    m_genPerms;
   
   //~ Constructors ------------------------------------------------------------

   public AcsAccessTokenUser()
   {

      m_id        = SboType.NULL_ID;
      m_profile   = null;
      m_genPerms  = AcsPermission.NONE;
   
   }

   public AcsAccessTokenUser(int id, String profile, int genPerms)
   {

      m_id       = id;
      m_profile  = profile;
      m_genPerms = genPerms;

   }

   public AcsAccessTokenUser(int id, String profile, int genPerms,
                             String userLogin)
   {

      m_id        = id;
      m_profile   = profile;
      m_genPerms  = genPerms;
   
   }

   //~ Methods -----------------------------------------------------------------

   public int getId()
   {

      return m_id;

   }

   public String getProfile()
   {

      return m_profile;

   }

   public int getGenPerms()
   {

      return m_genPerms;

   }
   
   // XSysSuperuser sólo hay uno.
   public boolean isXSysSuperuser()
   {

      boolean is = false;

      if(m_profile.equals(AcsProfile.SYS_XSUPERUSER))

         is = true;

      return is;

   }

   /**
    * toString methode: creates a String representation of the object
    * @return the String representation
    */
   public String toString() {
      
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("AcsAccessTokenUser[");
      buffer.append(", m_id = ").append(m_id);
      buffer.append(", m_profile = ").append(m_profile);
      buffer.append(", m_genPerms = ").append(m_genPerms);
      buffer.append("]");
      
      return buffer.toString();
   }
}
 // class
