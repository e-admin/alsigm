package ieci.tecdoc.sbo.idoc.api;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.idoc.acs.ReportMdoSearch;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveToken;
import ieci.tecdoc.sbo.idoc.report.search.ReportSearchResults;

/**
 * @author egonzalez
 */
public class ReportSearch
{

  /**
   * Configuración de la base de datos donde se encuentra el modelo de datos de
   * invesdoc
   */
  DbConnectionConfig m_dbConnConfig;

  /**
   * Directorio donde se encuentra el fichero con la configuración de la base de
   * datos invesdoc
   */
  String             m_configDir;

  /**
   * Constructor
   * 
   * @throws Exception
   */
  public ReportSearch() throws Exception
  {
    m_dbConnConfig = null;
    m_configDir = null;
  }

  /**
   * Construtor
   * 
   * @param configDir configuración de la base de datos invesdoc
   * @throws Exception
   */
  public ReportSearch(String configDir) throws Exception
  {
    m_dbConnConfig = null;
    m_configDir = configDir;
  }

  /**
   * Devuelve la configuración de la base de datos donde se encuentran usuarios
   * invesdoc.
   * 
   * @return
   * @throws Exception
   */
  private DbConnectionConfig getDbConfig() throws Exception
  {
    if (m_dbConnConfig == null)
    {
      if (m_configDir == null)
      {
        m_dbConnConfig = CfgMdoConfig.getDbConfig();
      }
      else
      {
        m_dbConnConfig = CfgMdoConfig.getDbConfig(m_configDir);
      }
    }

    return m_dbConnConfig;
  }

  /**
   * Establece una configuración de base de datos. Esta configuración
   * corresponderá con la base de datos invesdoc.
   * 
   * @param dbConnConfig Configuración de base de datos
   * @throws Exception
   */
  public void setConnectionConfig(DbConnectionConfig dbConnConfig)
        throws Exception
  {
    m_dbConnConfig = dbConnConfig;
  }

  /**
   * Devuelve todos los informes visibles por un determinado usuario de un
   * determinado archivador
   * 
   * @param acs identificación y permsisos de usuario
   * @param userId identificador de usuario
   * @param arch archivador
   * @return informes
   * @throws Exception si se produce algun error
   */
  public ReportSearchResults executeQuery(AcsAccessObject acs, int userId,
        ArchiveToken arch) throws Exception
  {
    ReportSearchResults rs = null;

    try
    {

      DbConnection.open(getDbConfig());

      rs = ReportMdoSearch.executeQuery(acs.getAccessToken(), userId, arch);

      DbConnection.close();

    }
    catch (Exception e)
    {
      DbConnection.ensureClose(e);
      return null;
    }

    return rs;
  }

  public static void main(String args[])
  {
    try
    {
      String drv = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
      String url = "jdbc:microsoft:sqlserver://egonzaleze:1066;databasename=idoc9";
      String usr = "sa";
      String pwd = "manager";

      String iDocUsr = "consulta";
      String iDocPwd = "consulta";

      DbConnectionConfig dbConConfig = new DbConnectionConfig(drv, url, usr,
            pwd);

      Login loginApi = new Login();
      loginApi.setConnectionConfig(dbConConfig);
      AcsAccessObject acs = loginApi.doLoginStd(iDocUsr, iDocPwd, 1);
      
      Archive archiveApi = new Archive ();
      archiveApi.setConnectionConfig(dbConConfig);
      ArchiveObject arch = archiveApi.loadArchive(acs, 3);
      
      
      ReportSearch searchApi = new ReportSearch ();
      searchApi.setConnectionConfig(dbConConfig);
      ReportSearchResults results = searchApi.executeQuery(acs, acs.getUserId(), arch.getArchiveToken());
      System.out.println (results);

    }
    catch (Throwable e)
    {
      e.printStackTrace();
    }
  }
}
