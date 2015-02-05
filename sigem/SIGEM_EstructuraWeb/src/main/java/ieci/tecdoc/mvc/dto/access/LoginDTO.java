package ieci.tecdoc.mvc.dto.access;

import org.apache.log4j.Logger;

public class LoginDTO
{
   /**
    * Log4J Logger for this class
    */
   private static final Logger logger = Logger.getLogger(LoginDTO.class);

    //~ Instance fields --------------------------------------------------------

    private String m_user;
    private String m_pwd;
    private int    m_cntsTriesNum;
    private String m_language;

    //~ Methods ----------------------------------------------------------------

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
    
    public int getCntsTriesNum()
    {

        return m_cntsTriesNum;

    }

    public void setCntsTriesNum(int cntsTriesNum)
    {

        this.m_cntsTriesNum = cntsTriesNum;

    }

    public String getLanguage()
    {

        return m_language;

    }

    public void setLanguage(String language)
    {

        this.m_language = language;

    }

    /**
     * toString methode: creates a String representation of the object
     *
     * @return the String representation
     */
    public String toString()
    {

        StringBuffer buffer = new StringBuffer();
        
        buffer.append("LoginDTO[");
        buffer.append("m_user = ").append(m_user);
        buffer.append(", m_pwd = ****");
        buffer.append(", m_cntsTriesNum = ").append(m_cntsTriesNum);
        buffer.append(", m_language = ").append(m_language);
        buffer.append("]");

        return buffer.toString();

    }

}
