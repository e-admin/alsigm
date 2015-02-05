/*
 * Created on 20-dic-2005
 *
 */
package ieci.tecdoc.sbo.fss.core;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Ernesto
 */
public class CfgMdoFile
{
   
   private ArrayList m_repositories;
   
   public CfgMdoFile () {
      m_repositories = new ArrayList ();
   }
   
   public void addRepositoryConfig (RepositoryConfig repositoryConfig) {
      m_repositories.add (repositoryConfig);
   }
   
   public List getRepositoriesConfig () {
      return m_repositories;
   }
   
   public static class RepositoryConfig {
   
	   private int m_id;
	   private String m_path;
	   private int m_operatingSystem;
	   
	   /**
	    * @param id
	    * @param path
	    * @param operatingSystem
	    */
	   public RepositoryConfig(int id, String path, int operatingSystem)
	   {
	      super();
	      this.m_id = id;
	      this.m_path = path;
	      this.m_operatingSystem = operatingSystem;
	   }
	   
	   /**
	    * @return Returns the id.
	    */
	   public int getId()
	   {
	      return m_id;
	   }
	   
	   /**
	    * @return Returns the operatingSystem.
	    */
	   public int getOperatingSystem()
	   {
	      return m_operatingSystem;
	   }
	   
	   /**
	    * @return Returns the path.
	    */
	   public String getPath()
	   {
	      return m_path;
	   }
   }
}
