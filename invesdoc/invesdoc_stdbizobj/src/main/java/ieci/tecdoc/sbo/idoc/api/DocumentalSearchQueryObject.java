/*
 * Created on 13-may-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ieci.tecdoc.sbo.idoc.api;

import ieci.tecdoc.sbo.idoc.documental.search.DocumentalSearchQuery;


/**
 * Clase que encapusula la búsqueda documental (en todos los archivadores)
 * 
 */
public final class DocumentalSearchQueryObject
{
   private DocumentalSearchQuery m_documentalSearchQuery;

   /**
    * Constructor 
    * @param arch archivador sobre el que se va a realizar la 
    * búsqueda
    */
   public DocumentalSearchQueryObject()
   {
      m_documentalSearchQuery = new DocumentalSearchQuery ();
   }
   
   /**
    * Añade una condición de búsqueda en contenido de documentos
    * @param condition expresión de búsqueda documental   
    */   
   public void addFTSSearchCondition(String condition) 
   {
      m_documentalSearchQuery.addFTSSearchCondition (condition);
   }

   public DocumentalSearchQuery getDocumentalSearchQuery()
   {
      return m_documentalSearchQuery;
   }
}
