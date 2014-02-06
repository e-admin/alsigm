package ieci.tecdoc.core.db;

/**
 * Clase que contiene la configuración de conexión con
 * la base de datos.  
 *  
 */

public class DbConnectionConfig
{

   /** <tt>true</tt> si la conexión es por driver */
   private boolean m_cntByDriver; 
   /** Nombre del dataSource */
   private String  m_cntParam;
   /** url de la base de datos */
   private String  m_url;
   /** usuario de base de datos */
   private String  m_user;
   /** password de base de datos */
   private String  m_pwd;

   /**
    * Constructor 
    * @param drvClsName nombre de la clase del driver de base de datos
    * @param url url de base de datos
    * @param user usuario de base de datos
    * @param pwd contraseña de base de datos
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
   
   /**
    * Constructor 
    * @param ctxName nombre del dataSource
    * @param user usuario de base de datos
    * @param pwd contraseña de base de datos
    */
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
    * Devuelve el nombre del datasource (isCntByDriver == false) o del driver (isCntByDriver == true)
    * @return nombre del datasource
    */
   public String getCntParam()
   {
      return m_cntParam;
   }
   
   /**
    * Establece el nombre del datasource
    * @param ctxName nombre del datsource
    */
   public void setCtxName(String ctxName)
   {
      this.m_cntParam    = ctxName;
      this.m_cntByDriver = false;
   }   
   
   /**
    * Establece el nombre de la clase del driver de base de datos
    * @param drvClsName nombre de la clase del driver de base de datos
    */
   public void setDrvClsName(String drvClsName)
   {
      this.m_cntParam    = drvClsName;
      this.m_cntByDriver = true;
   }

   /**
    * Devuelve la contraseña de base de datos
    * @return contraseña de base de datos
    */
   public String getPwd()
   {
      return m_pwd;
   }

   /**
    * Establece el valor de la contraseña de base de datos
    * @param pwd contraseña de base de datos
    */
   public void setPwd(String pwd)
   {
      this.m_pwd = pwd;
   }

   /**
    * Devuelve la url de conexión de base de datos
    * @return url de conexión de base de datos
    */
   public String getUrl()
   {
      return m_url;
   }

   /**
    * Establece el valor de la url de conexión a base de datos
    * @param url url de conexión a base de datos
    */
   public void setUrl(String url)
   {
      this.m_url = url;
   }

   /**
    * Devuelve el usuario de base de datos
    * @return usuario de base de datos
    */
   public String getUser()
   {
      return m_user;
   }

   /**
    * Establece el valor del usuario de base de datos
    * @param user usuario de base de datos
    */
   public void setUser(String user)
   {
      this.m_user = user;
   }
   
   /**
    * Devuelve <tt>true</tt> si la conexión es por driver
    * @return <tt>true</tt> si la conexión es por driver
    */
   public boolean isCntByDriver()
   {
      return m_cntByDriver;
   }
   
   /**
    * @see Object#toString()
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