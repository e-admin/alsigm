/*
 * DocumentosDatos.java
 *
 * Created on 23 de mayo de 2007, 16:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.database;

import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.core.config.impl.spring.DefaultConfiguration;
import ieci.tecdoc.sgm.core.db.DataSourceManager;
import ieci.tecdoc.sgm.nt.database.datatypes.DocumentoImpl;
import ieci.tecdoc.sgm.nt.database.datatypes.Documentos;
import ieci.tecdoc.sgm.nt.database.exception.DbErrorCodes;
import ieci.tecdoc.sgm.nt.database.exception.DbException;
import ieci.tecdoc.sgm.nt.exception.ClaveIncorrectaErrorCodigos;
import ieci.tecdoc.sgm.nt.exception.ClaveIncorrectaExcepcion;
import ieci.tecdoc.sgm.nt.exception.DocumentosRepositorioErrorCodigos;
import ieci.tecdoc.sgm.nt.exception.DocumentosRepositorioExcepcion;

import java.io.Serializable;




import org.apache.log4j.Logger;
/**
 *
 * @author Usuario
 */
public class DocumentosDatos extends DocumentoImpl implements Serializable{
    
  private static Logger logger = Logger.getLogger(DocumentosDatos.class);
  protected boolean isDebugeable = true;
  
  public static final char ALL = 'a';
  public static final char SEARCH = 's';
  public static final char SEARCH_DOC_FROM_ID_NOT = 'n';
  
  /**
   * Recupera todos los valores de los parámetros de la sentencia
   * de consulta que se pasa como parámetro.
   *
   * @param statement Sentencia sql precompilada.
   * @param idx Indice de posición del primer parámetro que se recoge
   * de la consulta.
   * @return Indice de posición del último parámetro recogido
   * @throws DbException Si se produce algún error.
   */
  public Integer loadAllValues(DbOutputStatement statement, Integer idx)
  throws DbException {
     
     if (isDebugeable)
       logger.debug("loadAllValues >> statement: " + statement.toString() + " idx entrada: " + idx);
    
     int index = idx.intValue();
     
     try{
         
        // vamos a suponer que los campos se crearon en el orden que indica la especificacion
        // de campos en la documentacion
        
        setExpediente(statement.getShortText(index ++));
        setNifDestinatario(statement.getShortText(index ++));
        setCodigo(statement.getShortText(index ++));   
        setGuid(statement.getShortText(index ++));  
        setNotiId(statement.getShortText(index ++));
        setTipoDoc(new Integer(statement.getShortInteger(index ++)));  
    
     }catch(Exception e){
       throw new DbException(DbErrorCodes.NT_GET_ALL_VALUES, e);
     }
     
     if (isDebugeable)
       logger.debug("loadAllValues << Nif destinatario:" + getNifDestinatario() + 
        " ,Numero de expediente:" + getExpediente () + 
        " ,Codigo:" + getCodigo () );
     
     return new Integer(index);
  }
  
  
  /**
   * Genera la sentencia de inserción de un documento.
   *
   * @param statement Sentencia sql precompilada.
   * @param idx Indice de posición del primer parámetro que se recoge
   * de la consulta.
   * @return Indice de posición del último parámtro recogido
   * @throws Exception Si se produce algún error.
   */
  public Integer insert(DbInputStatement statement, Integer idx)
  throws DbException {
    if (isDebugeable)
     logger.debug("insert >> statement: " + statement.toString() + " idx entrada: " + idx);
    
    int index = idx.intValue();
    
    try{
        
        statement.setShortText(index ++,getExpediente());
        statement.setShortText(index ++,getNifDestinatario());
        statement.setShortText(index ++,getCodigo()); 
        statement.setShortText(index ++, getGuid());
        statement.setShortText(index ++, getNotiId());
        statement.setShortInteger(index++, (getTipoDoc()!=null?getTipoDoc().shortValue():1));
 		
    }catch(Exception e){
      throw new DbException(DbErrorCodes.NT_INSERT_ALL_VALUES,e);
    }

    if (isDebugeable)
      logger.debug("insert << statement: " + statement.toString());
    
    return new Integer(index);
  }
  
  
  /**
   * Realiza la consulta por la clave primaria
   *
   * @param guid GUID del documento.
   * @throws DbException Si se produce algún error.
   */
  public void load(String entidad) throws ClaveIncorrectaExcepcion,DocumentosRepositorioExcepcion {
     
    if ((getNotiId() != null && !getNotiId().equals("") )|| 
    	(getExpediente() != null && !getExpediente().equals("") &&
         getNifDestinatario() != null && !getNifDestinatario().equals("") )){
      
        DynamicTable tableInfo = new DynamicTable();
        DynamicRows rowsInfo = new DynamicRows();
        DynamicRow rowInfo = new DynamicRow();
        DocumentosTabla tablaDoc = new DocumentosTabla();
        DbConnection dbConn = new DbConnection();
     
        if (isDebugeable)
            logger.debug("load >> expediente: " + getExpediente() + 
                                " ,codigo: " + getCodigo() +
                                " ,nif: " +  getNifDestinatario()) ;
    
        boolean incorrectGuid = false;
    
        try {
           dbConn.open(DBSessionManager.getSession(entidad));
           tableInfo.setTableObject(tablaDoc);
           tableInfo.setClassName(DocumentosTabla.class.getName());
           tableInfo.setTablesMethod("getTableName");
           tableInfo.setColumnsMethod("getAllColumnNames");
           rowInfo.setClassName(DocumentosDatos.class.getName());
           rowInfo.setValuesMethod("loadAllValues");
           rowInfo.addRow(this);
           rowsInfo.add(rowInfo);

           if (!DynamicFns.select(dbConn, tablaDoc.getClausulaPorId(getExpediente(),getNifDestinatario(),getNotiId()), tableInfo, rowsInfo)) {
              incorrectGuid = true;
           }
        } catch (Exception e) {
           throw new DocumentosRepositorioExcepcion(DocumentosRepositorioErrorCodigos.EC_RETRIEVE_DOCUMENT,e);
        } finally {
           try{
             if (dbConn.existConnection())
               dbConn.close();
        }catch(Exception ee){}
       
           if (incorrectGuid)
             throw new ClaveIncorrectaExcepcion(ClaveIncorrectaErrorCodigos.EC_INCORRECT_GUID);
    }   
    }else
        throw new ClaveIncorrectaExcepcion(ClaveIncorrectaErrorCodigos.EC_INCORRECT_GUID);
 }
  
  
  /**
   * Añade un documento
   *
   * @throws Exception Si se produce algún error.
   */
  public void add(String entidad) throws Exception {
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfo = new DynamicRow();
     DocumentosTabla table = new DocumentosTabla();
     DbConnection dbConn = new DbConnection();
     
     if (isDebugeable)
            logger.debug("load >> expediente: " + getExpediente() + 
                                " ,codigo: " + getCodigo() +
                                " ,nif: " +  getNifDestinatario()) ;
     
     try {
         dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(DocumentosTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getAllColumnNames");
        
        rowInfo.addRow(this);
        rowInfo.setClassName(DocumentosDatos.class.getName());
        rowInfo.setValuesMethod("insert");
        rowsInfo.add(rowInfo);
        
        DynamicFns.insert(dbConn, tableInfo, rowsInfo);
     } catch (Exception e) {
        throw new DbException(DbErrorCodes.NT_ADD_VALUE, e);
     } finally {
       try{
         if (dbConn.existConnection())
           dbConn.close();
       }catch(Exception ee){}
     }
  }
  
  
  /**
   * Borra un documento
   * @throws Exception Si se produce algún error.
   */
  public void delete(String entidad) 
    throws ClaveIncorrectaExcepcion,DocumentosRepositorioExcepcion{
     DocumentosTabla tablaDoc = new DocumentosTabla();
     
     if (isDebugeable)
            logger.debug("load >> expediente: " + getExpediente() + 
                                " ,codigo: " + getCodigo() +
                                " ,nif: " +  getNifDestinatario()) ;
     
     if ((getNotiId() != null && !getNotiId().equals("") )|| 
    	    	(getExpediente() != null && !getExpediente().equals("") &&
    	         getNifDestinatario() != null && !getNifDestinatario().equals("") )){
      
       
     
     DbConnection dbConn = new DbConnection();
     
     try {
         dbConn.open(DBSessionManager.getSession(entidad));
       
        DbDeleteFns.delete(dbConn, tablaDoc.getTableName(), tablaDoc.getClausulaPorId(getExpediente(),getNifDestinatario(),getNotiId()));
        
     } catch (Exception e) {
        throw new ClaveIncorrectaExcepcion(ClaveIncorrectaErrorCodigos.EC_INCORRECT_GUID,e);
     } finally {
       try{
         if (dbConn.existConnection())
           dbConn.close();
       }catch(Exception ee){
         throw new DocumentosRepositorioExcepcion(DocumentosRepositorioErrorCodigos.EC_DELETE_DOCUMENT);
       }
     }
     }else
         throw new ClaveIncorrectaExcepcion(ClaveIncorrectaErrorCodigos.EC_INCORRECT_GUID);
  }
  
  /**
   * Recupera la lista de documentos segun una determinada funcion y de los valores que se
   * le pasen como parametro a esa funcion
   *
   * @param caracter funcion a realizar en la busqueda. Tiene que corresponder con las constantes
   *                 estaticas y locales de esta clase echas para tal fin
   * @param CriteriosBusquedaBean son los parametros para la funcion de busqueda
   * @return Lista de docuemnto.
   * @throws Exception Si se produce algún error.
   */
  public Documentos getDocumentos(char funcion_,
                                  ieci.tecdoc.sgm.nt.bean.CriteriosBusquedaDocuBean params_, String entidad) throws Exception {
     Documentos docs = new Documentos();
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfoProcedure = new DynamicRow();
     DocumentosTabla table = new DocumentosTabla();
     DbConnection dbConn = new DbConnection();
     DocumentosDatos documentTmp;
     int counter, size;
         
     try {
         dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(DocumentosTabla.class.getName());
        rowInfoProcedure.setClassName(DocumentosDatos.class.getName());
        switch(funcion_){
            case SEARCH_DOC_FROM_ID_NOT:
                tableInfo.setTablesMethod("getTableNameChildDoc");
                tableInfo.setColumnsMethod("getAllColumnNamesWithAlias");                
             break;
             default:
                tableInfo.setTablesMethod("getTableName");
                tableInfo.setColumnsMethod("getAllColumnNames");
                
         break;
        
         }
         rowInfoProcedure.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfoProcedure);
         
         switch(funcion_){
                case SEARCH:DynamicFns.selectMultiple(dbConn, table.getClausulaPorBusqueda( params_ ), false, tableInfo, rowsInfo);
            break;         
             case SEARCH_DOC_FROM_ID_NOT:
               DynamicFns.selectMultiple(dbConn, table.getClausulaPorRegistroPadre(params_ ), false, tableInfo, rowsInfo);
              break;
                default:DynamicFns.selectMultiple(dbConn, null, false, tableInfo, rowsInfo);
            break;
        }
        size = rowInfoProcedure.getRowCount();
        
        for (counter = 0; counter < size; counter++) {
           documentTmp = (DocumentosDatos)rowInfoProcedure.getRow(counter);
           docs.add(documentTmp);
        }
        
     } catch (Exception e) {
       throw new DocumentosRepositorioExcepcion(DocumentosRepositorioErrorCodigos.EC_RETRIEVE_DOCUMENT,e);
     } finally {
    	 if (dbConn.existConnection())
             dbConn.close();
     }
     return docs;
  }
  
}
