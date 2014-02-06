package ieci.tecdoc.idoc.admin.api.volume;

import ieci.tecdoc.idoc.admin.internal.RepositoriesImpl;

/**
 * Maneja la lista de repositorios de invesDoc.
 */
public class Repositories
{
	/**
    * Construye un objeto de la clase.
    *  
    */

   public Repositories()
   {
   	_repositoriesImpl = new RepositoriesImpl();
   }

   /**
    * Devuelve el número de repositorios.
    * 
    * @return El número de repositorios mencionado.
    */

   public int count() 
   {
      return _repositoriesImpl.count();
   }

   /**
    * Carga la lista de repositorios con su información básica.
    * 
    * @throws Exception Si se produce algún error en la carga de los repositorios.
    */

   public void load() throws Exception 
   {
   	_repositoriesImpl.load();
   }

   /**
    * Devuelve un repositorio de la lista.
    * 
    * @param index Indice del repositorio que se desea recuperar.
    * 
    * @return El repositorio mencionado.
    */

   public Repository getRepository(int index) throws Exception 
   {
      return _repositoriesImpl.get(index);
   }
   
   /**
    * Obtiene la información de la lista de repositorios en formato XML.
    *  
    * @return La lista de repositorios mencionada.
    */

   public String toXML()
   {
      return _repositoriesImpl.toXML(true);
   }

   /**
    * Muestra una representación de los valores de la clase en formato XML.
    *  
    * @return La representación mencionada.
    */

	public String toString()
   {
      return _repositoriesImpl.toString();
   }


   private RepositoriesImpl _repositoriesImpl;
}
