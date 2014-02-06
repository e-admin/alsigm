/*
 * Created on 13-may-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ieci.tecdoc.sbo.idoc.api;

import java.util.ArrayList;

import ieci.tecdoc.sbo.idoc.folder.search.FolderSearchQuery;


/**
 * Clase que encapusula los filtros de búsqueda de carpetas dentro de un
 * archivador y el conjunto de campos por los cuales se ordena los resultados
 * de una búsqueda.
 * <p>
 * Existen los siguientes operadores:
 * <li> IN_AND: todos los valores se encuentran en la colección de valores
 * de un campo multivalor
 * <li> IN_OR: algunos valores se encuentran en la colección de valores
 * de un campo multivalor
 * <li> LIKE_AND: todos los valores son como alguno de la colección de valores
 * de un campo multivalor
 * <li> LIKE_OR: algunos valores son como alguno de la colección de valores
 * de un campo multivalor
 * <li> EQUAL: igual
 * <li> DISTINCT: distinto
 * <li> GREATER: mayor que
 * <li> GREATER_EQUAL: mayor o igual que
 * <li> LOWER: menor
 * <li> LOWER_EQUAL: menor que 
 * <li> BETWEEN: entre dos valores
 * <li> LIKE: como
 * <li> OR: o
 * <li> FULL_TEXT: búsqueda documental dentro de un campo relacional. La expresión
 * documental de búsqueda se cumple.
 * <li> FULL_TEXT_NOT: búsqueda documental dentro de un campo relacional. La expresión
 * documental de búsqueda no se cumple.   
 */
public final class FolderSearchQueryObject
{
   private FolderSearchQuery m_query;

   /**
    * Constructor 
    * @param arch archivador sobre el que se va a realizar la 
    * búsqueda
    */
   public FolderSearchQueryObject(ArchiveObject arch)
   {
      m_query = new FolderSearchQuery(arch.getArchiveToken());
   }
   /**
    * Añade el campo identificador de la carpeta al conjunto de campos por los cuales
    * de va a ordenar la búsqueda
    * @param desc la búsqueda es descendente
    * @throws Exception si se produce algún error al añadir el campo identificador
    */
   public void addFdrIdSearchOrder(boolean desc) throws Exception
   {
      m_query.addFdrIdSearchOrder(desc);
   }

   /**
    * Añade una condición de búsqueda en contenido de documentos
    * @param condition expresión de búsqueda documental   
    * @throws Exception si se produce algún error al añadir la condicion de búsqueda
    */   
   public void addFTSSearchCondition(String condition) throws Exception
   {
      m_query.addFTSSearchCondition(condition);
   }
   
   /**
    * Añade una condición de búsqueda para un determinado campo del archivador 
    * @param fldId identificador del campo del archivador
    * @param opr operador de búsqueda
    * @param vals valores del campo  
    * @throws Exception si se produce algún error al añadir la condicion de búsqueda
    */   
   public void addSearchCondition(int fldId, String opr, ArrayList vals)
            throws Exception
   {
      m_query.addSearchCondition(fldId, opr, vals);
   }

   /**
    * Añade una condición de búsqueda para un determinado campo del archivador 
    * @param fldId identificador del campo del archivador
    * @param opr operador de búsqueda
    * @param val valor del campo  
    * @throws Exception si se produce algún error al añadir la condicion de búsqueda
    */         
   public void addSearchCondition(int fldId, String opr, Object val)
            throws Exception
   {
      m_query.addSearchCondition(fldId, opr, val);
   }

   /**
    * Añade una condición de búsqueda para un determinado campo del archivador 
    * @param fldId identificador del campo del archivador
    * @param opr operador de búsqueda
    * @param val valor del campo  
    * @throws Exception si se produce algún error al añadir la condicion de búsqueda
    */            
   public void addSearchCondition(int fldId, String opr, String val)
            throws Exception
   {
      m_query.addSearchCondition(fldId, opr, val);
   }

   /**
    * Añade una condición de búsqueda para un determinado campo del archivador 
    * @param fldId identificador del campo del archivador
    * @param opr operador de búsqueda
    * @param vals valores del campo  
    * @throws Exception si se produce algún error al añadir la condicion de búsqueda
    */            
   public void addSearchCondition(int fldId, String opr, String[] vals)
            throws Exception
   {
      m_query.addSearchCondition(fldId, opr, vals);
   }

   /**
    * Añade una condición de búsqueda para un determinado campo del archivador 
    * @param fldName nombre del campo del archivador
    * @param opr operador de búsqueda
    * @param vals valores del campo  
    * @throws Exception si se produce algún error al añadir la condicion de búsqueda
    */
   public void addSearchCondition(String fldName, String opr, ArrayList vals)
            throws Exception
   {
      m_query.addSearchCondition(fldName, opr, vals);
   }

   /**
    * Añade una condición de búsqueda para un determinado campo del archivador 
    * @param fldName nombre del campo del archivador
    * @param opr operador de búsqueda
    * @param val valor del campo  
    * @throws Exception si se produce algún error al añadir la condicion de búsqueda
    */      
   public void addSearchCondition(String fldName, String opr, Object val)
            throws Exception
   {
      m_query.addSearchCondition(fldName, opr, val);
   }

   /**
    * Añade una condición de búsqueda para un determinado campo del archivador 
    * @param fldName nombre del campo del archivador
    * @param opr operador de búsqueda
    * @param val valor del campo  
    * @throws Exception si se produce algún error al añadir la condicion de búsqueda
    */      
   public void addSearchCondition(String fldName, String opr, String val)
            throws Exception
   {
      m_query.addSearchCondition(fldName, opr, val);
   }

   /**
    * Añade una condición de búsqueda para un determinado campo del archivador 
    * @param fldName nombre del campo del archivador
    * @param opr operador de búsqueda
    * @param vals valores del campo  
    * @throws Exception si se produce algún error al añadir la condicion de búsqueda
    */         
   public void addSearchCondition(String fldName, String opr, String[] vals)
            throws Exception
   {
      m_query.addSearchCondition(fldName, opr, vals);
   }

   /**
    * Añade un campo dentro del conjunto de campos por los cuales se va a ordenar la
    * búsqueda 
    * @param fldId identificador del campo
    * @param desc la búsqueda es descendente
    * @throws Exception si se produce algún error al añadir el campo
    */   
   public void addSearchOrder(int fldId, boolean desc) throws Exception
   {
      m_query.addSearchOrder(fldId, desc);
   }

   /**
    * Añade un campo dentro del conjunto de campos por los cuales se va a ordenar la
    * búsqueda 
    * @param fldName nombre del campo
    * @param desc la búsqueda es descendente
    * @throws Exception si se produce algún error al añadir el campo
    */
   public void addSearchOrder(String fldName, boolean desc) throws Exception
   {
      m_query.addSearchOrder(fldName, desc);
   }

   /**
    * Devuelve la cláusula ORDER BY que define la ordenación de las carpetas resultado
    * de una búsqueda
    * @return cláusula ORDER BY
    * @throws Exception si se produce algún error en la obtención de la cláusula
    */
   public String getSqlOrderBy() throws Exception
   {
      return m_query.getSqlOrderBy();
   }

   /**
    * Devuelve una cadena que representa las condiciones de búsqueda de carpetas dentro
    * de un archivador y la cláusula ORDER BY para la ordenación de los resultados de la
    * búsqueda  
    * @param dbEngine tipo de base de datos: 
    * <li> DbEngine.SQLSERVER_STR
    * <li> DbEngine.ORACLE_STR
    * @return condición sql y order by 
    * @throws Exception si se produce algún error en la obtención de la condición y cláusula 
    * order by
    */   
   public String getSqlQual(int dbEngine) throws Exception
   {
      return m_query.getSqlQual(dbEngine);
   }

   /**
    * Devuelve la condición sql que se utilizará como filtro en la búsqueda de carpetas
    * dentro de un archivador
    * @param dbEngine tipo de base de datos: 
    * <li> DbEngine.SQLSERVER_STR
    * <li> DbEngine.ORACLE_STR
    * @return condición sql 
    * @throws Exception si se produce algún error en la obtención de la condición sql
    */
   public String getSqlWhere(int dbEngine) throws Exception
   {
      return m_query.getSqlWhere(dbEngine);
   }
   
   /**
    * Devuelve el atributo folderSearchQuery que contiene la información 
    * de la búsqueda 
    * 
    * @return el atributo folderSearchQuery 
    */
   public FolderSearchQuery getFolderSearchQuery() 
   {
      return m_query;
   }
   
   /**
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return m_query.toString();
   }
}
