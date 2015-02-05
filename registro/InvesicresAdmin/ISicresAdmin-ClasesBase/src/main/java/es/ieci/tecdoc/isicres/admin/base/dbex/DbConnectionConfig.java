package es.ieci.tecdoc.isicres.admin.base.dbex;

public class DbConnectionConfig
{

   private boolean m_cntByDriver; //true si la conexión es por driver,
                                  //false si es por DataSource
   private String  m_cntParam; // Nombre del dataSource o del driver.
   private String  m_url;
   private String  m_user;
   private String  m_pwd;

   /**
    * @param drvClsName
    * @param url
    * @param user
    * @param pwd
    */
   public DbConnectionConfig(String drvClsName, String url, String user,
                             String pwd)
   {
      this.m_cntByDriver = true;
      this.m_cntParam    = drvClsName;
      this.m_url         = url;
      this.m_user        = user;
      this.m_pwd         = pwd;
   }
   
   public DbConnectionConfig(String ctxName, String user, String pwd)
   {
      this.m_cntByDriver = false;
      this.m_cntParam    = ctxName;
      this.m_url         = null;
      this.m_user        = user;
      this.m_pwd         = pwd;
   }

   private DbConnectionConfig()
   {
      //Intencionadamente Privado
   }

   /**
    * @return Returns the cntParam.
    */
   public String getCntParam()
   {
      return m_cntParam;
   }
   
   public void setCtxName(String ctxName)
   {
      this.m_cntParam    = ctxName;
      this.m_cntByDriver = false;
   }   
   
   public void setDrvClsName(String drvClsName)
   {
      this.m_cntParam    = drvClsName;
      this.m_cntByDriver = true;
   }

   /**
    * @return Returns the pwd.
    */
   public String getPwd()
   {
      return m_pwd;
   }

   /**
    * @param pwd
    *           The pwd to set.
    */
   public void setPwd(String pwd)
   {
      this.m_pwd = pwd;
   }

   /**
    * @return Returns the url.
    */
   public String getUrl()
   {
      return m_url;
   }

   /**
    * @param url
    *           The url to set.
    */
   public void setUrl(String url)
   {
      this.m_url = url;
   }

   /**
    * @return Returns the user.
    */
   public String getUser()
   {
      return m_user;
   }

   /**
    * @param user
    *           The user to set.
    */
   public void setUser(String user)
   {
      this.m_user = user;
   }
   
   public boolean isCntByDriver()
   {
      return m_cntByDriver;
   }
   
   /**
    * toString methode: creates a String representation of the object
    * @return the String representation
    */
   public String toString() {
      StringBuffer buffer = new StringBuffer();
      buffer.append("DbConnectionConfig[");
      buffer.append("cntByDriver = ").append(m_cntByDriver);
      buffer.append(", cntParam = ").append(m_cntParam);
      buffer.append(", url = ").append(m_url);
      buffer.append(", user = ").append(m_user);
      buffer.append(", pwd = ").append(m_pwd);
      buffer.append("]");
      return buffer.toString();
   }
}