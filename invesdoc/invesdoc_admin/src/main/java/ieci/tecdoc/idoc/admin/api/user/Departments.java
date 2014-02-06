package ieci.tecdoc.idoc.admin.api.user;

import ieci.tecdoc.idoc.admin.internal.DepartmentsImpl;


/**
 * Maneja la lista de departamentos invesDoc.
 */
public class Departments
{
	/**
    * Construye un objeto de la clase.
    *  
    */

   public Departments()
   {
   	_departmentsImpl = new DepartmentsImpl();
   }

   /**
    * Devuelve el número de departamentos.
    * 
    * @return El número de departamento mencionado.
    */

   public int count() 
   {
      return _departmentsImpl.count();
   }

   /**
    * Carga la lista de departamentos con su información básica.
    * 
    * @throws Exception Si se produce algún error en la carga de los departamentos.
    */

   public void loadLite() throws Exception 
   {
   	_departmentsImpl.load();
   }

   /**
    * Carga la lista de departamentos de un mismo padre con su información
    * básica.
    *  
    * @param parentId Identificador de padre
    * @throws Exception  Si se produce algún error en la carga de los departamentos.
    */
   public void loadLite(int parentId) throws Exception
   {
      _departmentsImpl.load(parentId);
   }
   /**
    * Devuelve un departamento de la lista.
    * 
    * @param index Indice del departamento que se desea recuperar.
    * 
    * @return El departamento mencionado.
    */

   public Department getDepartment(int index) 
   {
      return _departmentsImpl.get(index);
   }
   
   /**
    * Obtiene la información de la lista de departamentos en formato XML.
    *  
    * @return La lista de departamentos mencionada.
    */

   public String toXML()
   {
      return _departmentsImpl.toXML(true);
   }

   /**
    * Muestra una representación de los valores de la clase en formato XML.
    *  
    * @return La representación mencionada.
    */

	public String toString()
   {
      return _departmentsImpl.toString();
   }


   private DepartmentsImpl _departmentsImpl;
}
