/*
 * Created on 10-ago-2005
 * @autor IECI S.A.
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ieci.tecdoc.sgm.catalogo_tramites.util.database;

//import ieci.tecdoc.sgm.catalogo_tramites.Configuracion;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesCodigosError;
import ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion;
import ieci.tecdoc.sgm.catalogo_tramites.util.Documento;
import ieci.tecdoc.sgm.catalogo_tramites.util.DocumentoExtendido;
import ieci.tecdoc.sgm.catalogo_tramites.util.DocumentoImpl;
import ieci.tecdoc.sgm.catalogo_tramites.util.Documentos;

import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * Modela una fila de la tabla de documentos.
 * @see DocumentoTable
 * @see Documento
 * @see DocumentoImpl
 * @see Documentos 
 */
public class DocumentoDatos extends DocumentoImpl implements Serializable {
   private static final Logger logger = Logger.getLogger(DocumentoDatos.class);
   boolean isDebugeable = true;
   
   /**
    * Constructor de la clase DocumentData
    */  
   public DocumentoDatos() {
   }
   
   
   /**
    * Constructor de la clase DocumentData
    * @param document Datos relacionados con un documento
    */
   public DocumentoDatos(Documento document) {
      this.setId(document.getId());
      this.setDescription(document.getDescription());
      this.setExtension(document.getExtension());
      this.setSignatureHook(document.getSignatureHook());
      this.setValidationHook(document.getValidationHook());
   }
   
   
   /**
    * Recupera todos los valores de los parámetros de la sentencia de consulta
    * que se pasa como parámetro.
    * @param statement Sentencia sql precompilada.
    * @param idx Indice de posición del primer parámetro que se recoge de la
    *  consulta.
    * @return Indice de posición del último parámetro recogido
    * @throws Exception Si se produce algún error.
    */
   public Integer loadAllValues(DbOutputStatement statement, Integer idx)
   throws Exception {

      int index = idx.intValue();
      
      id = statement.getShortText(index++);
      description = statement.getShortText(index++);
      extension = statement.getShortText(index++);
      signatureHook = statement.getShortText(index++);
      validationHook = statement.getShortText(index++);
      
      return new Integer(index);
   }

   
   /**
    * Genera la sentencia de inserción de un registro.
    * @param statement Sentencia SQL precompilada.
    * @param idx Indice de posición del primer parámetro en la sentencia.
    * @return Indice de posición del último parámetro en la sentencia.
    * @throws Exception Si se produce algún error.
    */
   public Integer insert(DbInputStatement statement, Integer idx)
   throws Exception {
      int index = idx.intValue();

      statement.setShortText(index++, id);
      statement.setShortText(index++, description);
      statement.setShortText(index++, extension);
      statement.setShortText(index++, signatureHook);
      statement.setShortText(index++, validationHook);
      
      return new Integer(index);
   }
   
   
   /**
    * Genera la sentencia de actualización de un registro.
    * @param statement Sentencia SQL precompilada.
    * @param idx Indice de posición del primer parámetro en la sentencia.
    * @return Indice de posición del último parámetro en la sentencia.
    * @throws Exception Si se produce algún error.
    */
   public Integer updateAll(DbInputStatement statement, Integer idx)
   throws Exception {
      int index = idx.intValue();

      statement.setShortText(index++, description);
      statement.setShortText(index++, extension);
      statement.setShortText(index++, signatureHook);
      statement.setShortText(index++, validationHook);
      
      return new Integer(index);
   }
   
   /**
    * Recoge el registro de tipo de documento cuyo identificador se pasa como
    * parámetro.
    * @param documentId Identificador de tipo de documento.
    * @throws Exception Si se produce algún error.
    */
   public void load(String documentId, String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      DocumentoTabla table = new DocumentoTabla();
      DbConnection dbConn = new DbConnection();
      boolean load = true;
   
      logger.debug("Load Document <-- Document ID: " + documentId);
      
      try {
       	 //dbConn.open(Configuracion.getDatabaseConnection());
         dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(DocumentoTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(DocumentoDatos.class.getName());
         rowInfo.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfo);
         
         if (!DynamicFns.select(dbConn,table.getByIdQual(documentId), tableInfo, rowsInfo)) {
           load = false;
         }
         logger.debug("Load Document --> Document: " + this.toString());
      } catch (CatalogoTramitesExcepcion e) {
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_DOCUMENT);
      } catch (Exception e) {
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
      } finally {
    	  if (dbConn.existConnection())
	           dbConn.close();
         if (!load)
           throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_BAD_DOCUMENTID);
      }
   }
   

   /**
    * Inserta un registro.
    * @throws Exception Si se produce algún error.
    */
   public void insert(String entidad) throws Exception {     
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      DocumentoTabla table = new DocumentoTabla();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Insert Document <-- Document: " + this.toString());
      
      try {
       	 //dbConn.open(Configuracion.getDatabaseConnection());
         dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(DocumentoTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(DocumentoDatos.class.getName());
         rowInfo.setValuesMethod("insert");
         rowsInfo.add(rowInfo);
         
         DynamicFns.insert(dbConn, tableInfo, rowsInfo);
         
      } catch (CatalogoTramitesExcepcion e) {
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ADD_DOCUMENT);
      } catch (Exception e) {
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
      } finally {
    	  if (dbConn.existConnection())
	           dbConn.close();
      }
   }
   
   
   /**
    * Actualiza el registro de tipo de documento cuyo identificador se pasa como
    * parámetro.
    * @throws Exception Si se produce algún error.
    */
   public void update(String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      DocumentoTabla table = new DocumentoTabla();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Update Document <-- Document: " + this.toString());
      
      try {
       	 //dbConn.open(Configuracion.getDatabaseConnection());
         dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(DocumentoTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getUpdateColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(DocumentoDatos.class.getName());
         rowInfo.setValuesMethod("updateAll");
         rowsInfo.add(rowInfo);
         
         DynamicFns.update(dbConn,table.getByIdQual(id), tableInfo, rowsInfo);
      } catch (CatalogoTramitesExcepcion e) {
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_UPDATE_DOCUMENT);
      } catch (Exception e) {
        throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
      } finally {
         if (dbConn.existConnection())
	           dbConn.close();
      }
   }
   
   
   /**
    * Elimina el registro de tipo de documento cuyo identificador se pasa como
    * parámetro.
    * @throws Exception Si se produce algún error.
    */
   public void delete(String entidad) throws Exception {
      DocumentoTabla table = new DocumentoTabla();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Delete Document <-- Document ID: " + id);
      
      try {
       	 //dbConn.open(Configuracion.getDatabaseConnection());
         dbConn.open(DBSessionManager.getSession(entidad));
         
         DbDeleteFns.delete(dbConn, table.getTableName(), table.getByIdQual(id));
         
      } catch (CatalogoTramitesExcepcion e) {
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_DELETE_DOCUMENT);
      } catch (Exception e) {
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
      } finally {
    	  if (dbConn.existConnection())
	           dbConn.close();
      }
   }
   
   
   /**
    * Recupera la información del conjunto de documentos asociados a un trámite.
    * @param procedureId Identificador del procedimiento.
    * @return Lista de documentos resultante de la consulta.
    * @throws Exception Si se produce algún error.
    */
   public static Documentos getDocuments(String procedureId, String entidad) throws Exception {
      Documentos documents = new Documentos();
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfoDocument = new DynamicRow();
      DynamicRow rowInfoProcedureDocument = new DynamicRow();
      DocumentoTabla table = new DocumentoTabla();
      DbConnection dbConn = new DbConnection();
      DocumentoDatos document;
      DocumentoTramiteDatos procedureDocument;
      DocumentoExtendido documentExt;
      int counter, size;
      
      logger.debug("Get Procedure Documents <-- Procedure ID: " + procedureId);
      
      try {
       	 //dbConn.open(Configuracion.getDatabaseConnection());
         dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(DocumentoTabla.class.getName());
         tableInfo.setTablesMethod("getTableNameExt");
         tableInfo.setColumnsMethod("getProcedureDocumentsColumnNames");
         
         rowInfoDocument.setClassName(DocumentoDatos.class.getName());
         rowInfoDocument.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfoDocument);
         
         rowInfoProcedureDocument.setClassName(DocumentoTramiteDatos.class.getName());
         rowInfoProcedureDocument.setValuesMethod("loadMandatoryAndCodeValues");
         rowsInfo.add(rowInfoProcedureDocument);
         
         DynamicFns.selectMultiple(dbConn,table.getDocumentsByProcedureIdQual(procedureId), false, tableInfo, rowsInfo);
         size = rowInfoDocument.getRowCount();
      
         for (counter = 0; counter < size; counter++) {
            document = (DocumentoDatos)rowInfoDocument.getRow(counter);
            documentExt = new DocumentoExtendido(document);
            
            procedureDocument = (DocumentoTramiteDatos)rowInfoProcedureDocument.getRow(counter);
            documentExt.setCode(procedureDocument.getCode());
            documentExt.setMandatory(procedureDocument.isMandatory());
            documents.add(documentExt);
         }
         
      } catch (CatalogoTramitesExcepcion e) {
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_DOCUMENTS);
      } catch (Exception e) {
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
      } finally {
    	  if (dbConn.existConnection())
	           dbConn.close();
      }
      
      return documents;
   }

   
   /**
    * Recupera la lista de documentos.
    * @return Lista de documentos.
    * @throws Exception Si se produce algún error.
    */
   public static Documentos getDocuments(String entidad) throws Exception {
      Documentos documents = new Documentos();
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfoDocument = new DynamicRow();
      DocumentoTabla table = new DocumentoTabla();
      DbConnection dbConn = new DbConnection();
      DocumentoDatos document;
      int counter, size;
      
      logger.debug("Get Documents");
      
      try {
       	 //dbConn.open(Configuracion.getDatabaseConnection());
         dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(DocumentoTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnNames");
         
         rowInfoDocument.setClassName(DocumentoDatos.class.getName());
         rowInfoDocument.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfoDocument);
         
         DynamicFns.selectMultiple(dbConn, table.getOrderByDesc(), false, tableInfo, rowsInfo);
         size = rowInfoDocument.getRowCount();
         
         for (counter = 0; counter < size; counter++) {
            document = (DocumentoDatos)rowInfoDocument.getRow(counter);
            documents.add(new DocumentoExtendido(document));
         }
         
      } catch (CatalogoTramitesExcepcion e) {
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_GET_DOCUMENTS);
      } catch (Exception e) {
         throw new CatalogoTramitesExcepcion(CatalogoTramitesCodigosError.EC_ERR_DB);
      } finally {
    	  if (dbConn.existConnection())
	           dbConn.close();
      }
      
      return documents;
   }
}
