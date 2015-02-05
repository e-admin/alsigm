package ieci.tecdoc.mvc.dto.access;

import org.apache.log4j.Logger;

public class ChangePwdDTO
{
   /**
    * Log4J Logger for this class
    */
   private static final Logger logger = Logger.getLogger(ChangePwdDTO.class);

   //~ Instance fields ---------------------------------------------------------

   private String m_user;
   private String m_pwd;
   private String m_newPwd;
   private String m_confNewPwd;
   private int    m_cntsTriesNum;

   //~ Methods -----------------------------------------------------------------

   public String getUser()
   {

      return m_user;

   }

   public void setUser(String user)
   {

      this.m_user = user;

   }

   public String getPwd()
   {

      return m_pwd;

   }

   public void setPwd(String pwd)
   {

      this.m_pwd = pwd;

   }

   public String getNewPwd()
   {

      return m_newPwd;

   }

   public void setNewPwd(String newPwd)
   {

      this.m_newPwd = newPwd;

   }

   public String getConfNewPwd()
   {

      return m_confNewPwd;

   }

   public void setConfNewPwd(String confNewPwd)
   {

      m_confNewPwd = confNewPwd;

   }

   public int getCntsTriesNum()
   {

      return m_cntsTriesNum;

   }

   public void setCntsTriesNum(int cntsTriesNum)
   {

      m_cntsTriesNum = cntsTriesNum;

   }

   /**
    * toString methode: creates a String representation of the object
    *
    * @return the String representation
    */
   public String toString()
   {

      StringBuffer buffer = new StringBuffer();
      
      buffer.append("ChangePwdDTO[");
      buffer.append("m_user = ").append(m_user);
      buffer.append(", m_pwd = ****");
      buffer.append(", m_newPwd = ****");
      buffer.append(", m_confNewPwd = ****");
      buffer.append(", m_cntsTriesNum = ").append(m_cntsTriesNum);
      buffer.append("]");

      return buffer.toString();

   }

}
