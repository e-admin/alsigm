package ieci.tecdoc.sgm.registro.util.database;

import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.rde.database.exception.DbCodigosError;
import ieci.tecdoc.sgm.rde.database.exception.DbExcepcion;
import ieci.tecdoc.sgm.rde.exception.GuidIncorrectoExcepcion;
import ieci.tecdoc.sgm.rde.exception.RepositorioDocumentosExcepcion;
import ieci.tecdoc.sgm.registro.exception.RegistroCodigosError;
import ieci.tecdoc.sgm.registro.exception.RegistroExcepcion;
import ieci.tecdoc.sgm.registro.util.RegistroDocumento;
import ieci.tecdoc.sgm.registro.util.RegistroDocumentoImpl;
import ieci.tecdoc.sgm.registro.util.RegistroDocumentos;

import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * Modela una fila de la tabla de documentos de registro.
 *  
 */
public class RegistroDocumentoDatos extends RegistroDocumentoImpl implements Serializable{
  private static final Logger logger = Logger.getLogger(RegistroDocumentoDatos.class);
  protected boolean isDebugeable = true;
  
  /**
   * Constructor de la clase RegistryDocumentData
   */
  public RegistroDocumentoDatos() {
    super();
  }
  
  /**
   * Constructor de la clase RegistryDocumentData a partir de un objeto
   * @param registryDocument Datos del documento del registro
   */
  public RegistroDocumentoDatos(RegistroDocumento registryDocument) {
     this.setRegistryNumber(registryDocument.getRegistryNumber());
     this.setCode(registryDocument.getCode());
     this.setGuid(registryDocument.getGuid());
  }
  
  /**
   * Recupera todos los valores de los parámetros de la sentencia
   * de consulta que se pasa como parámetro.
   *
   * @param statement Sentencia sql precompilada.
   * @param idx Indice de posición del primer parámetro que se recoge
   * de la consulta.
   * @return Indice de posición del último parámetro recogido
   * @throws DbExcepcion Si se produce algún error.
   */
  public Integer loadAllValues(DbOutputStatement statement, Integer idx)
  throws DbExcepcion {
     
     int index = idx.intValue();
     
     try{
       registryNumber = statement.getShortText(index ++);
       code = statement.getShortText(index ++);
       guid = statement.getShortText(index ++);
     }catch(Exception e){
       throw new DbExcepcion(DbCodigosError.EC_GET_ALL_VALUES, e.getCause());
     }

     return new Integer(index);
  }
  
  
  /**
   * Genera la sentencia de inserción de un documento de registro.
   *
   * @param statement Sentencia sql precompilada.
   * @param idx Indice de posición del primer parámetro que se recoge
   * de la consulta.
   * @return Indice de posición del último parámtro recogido
   * @throws Exception Si se produce algún error.
   */
  public Integer insert(DbInputStatement statement, Integer idx)
  throws DbExcepcion {
    
    int index = idx.intValue();
    
    try{
      statement.setShortText(index++, registryNumber);
      statement.setShortText(index++, code);
      statement.setShortText(index++, guid);
    }catch(Exception e){
      throw new DbExcepcion(DbCodigosError.EC_INSERT_ALL_VALUES);
    }

    return new Integer(index);
  }
  
  
  /**
   * Realiza la consulta por número de registro y código de documento.
   *
   * @param registryNumber Número de registro
   * @param code Código de documento
   * @throws DbExcepcion Si se produce algún error.
   */
  public void load(String registryNumber, String code, String entidad)
     throws RegistroExcepcion, GuidIncorrectoExcepcion, RepositorioDocumentosExcepcion {
     
    if (registryNumber == null || registryNumber.equals("") || code == null || code.equals(""))
      //CAMBIAR ESTE ERROR DE CODIGO
      throw new RegistroExcepcion(RegistroCodigosError.EC_INCORRECT_RN_OR_DC);
    DynamicTable tableInfo = new DynamicTable();
    DynamicRows rowsInfo = new DynamicRows();
    DynamicRow rowInfo = new DynamicRow();
    RegistroDocumentoTabla table = new RegistroDocumentoTabla();
    DbConnection dbConn = new DbConnection();

    logger.debug("Load Registry Document <-- Registry Number: " + registryNumber + " Code: " + code);
    
    boolean incorrectValues = false;
    
    try {
   	   //dbConn.open(Configuracion.getDatabaseConnection());
       dbConn.open(DBSessionManager.getSession(entidad));
    	 
       tableInfo.setTableObject(table);
       tableInfo.setClassName(RegistroDocumentoTabla.class.getName());
       tableInfo.setTablesMethod("getTableName");
       tableInfo.setColumnsMethod("getAllColumnNames");
        
       rowInfo.addRow(this);
       rowInfo.setClassName(RegistroDocumentoDatos.class.getName());
       rowInfo.setValuesMethod("loadAllValues");
       rowsInfo.add(rowInfo);
        
       if (!DynamicFns.select(dbConn, table.getByRegistryNumberAndCodeQual(registryNumber, code), tableInfo, rowsInfo)) {
          incorrectValues = true;
       }
       
       logger.debug("Load Registry Document --> Registry Document: " + this.toString());
    } catch (Exception e) {
       throw new RegistroExcepcion(RegistroCodigosError.EC_RETRIEVE_REGISTRY_DOCUMENT);
    } finally {
       try{
         if (dbConn.existConnection())
           dbConn.close();
       }catch(Exception ee){}
       
       if (incorrectValues)
         //CAMBIAR CODIGO DE ERROR
         throw new RegistroExcepcion(RegistroCodigosError.EC_INCORRECT_RN_OR_DC);
    }
  }
  
  
  /**
   * Añade un documento de un registro.
   *
   * @throws Exception Si se produce algún error.
   */
  public void add(String entidad) throws Exception {
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfo = new DynamicRow();
     RegistroDocumentoTabla table = new RegistroDocumentoTabla();
     DbConnection dbConn = new DbConnection();
     
     logger.debug("Add Registry Documento <-- Registry Document: " + this.toString());
     
     try {
     	//dbConn.open(Configuracion.getDatabaseConnection());
        dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(RegistroDocumentoTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getAllColumnNames");
        
        rowInfo.addRow(this);
        rowInfo.setClassName(RegistroDocumentoDatos.class.getName());
        rowInfo.setValuesMethod("insert");
        rowsInfo.add(rowInfo);
        
        DynamicFns.insert(dbConn, tableInfo, rowsInfo);
     } catch (Exception e) {
        throw new RegistroExcepcion(RegistroCodigosError.EC_ADD_REGISTRY_DOCUMENT);
     } finally {
       try{
         if (dbConn.existConnection())
           dbConn.close();
       }catch(Exception ee){}
     }
  }
  
  
  /**
   * Actualiza un documento de un registro.
   *
   * @throws Exception Si se produce algún error.
   */
  public void updateOne(String entidad) throws Exception {
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfo = new DynamicRow();
     RegistroDocumentoTabla table = new RegistroDocumentoTabla();
     DbConnection dbConn = new DbConnection();
     
     logger.debug("Update Registry Document <-- Registry Document: " + this.toString());
     
     try {
     	//dbConn.open(Configuracion.getDatabaseConnection());
        dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(RegistroDocumentoTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getAllColumnNames");
        
        rowInfo.addRow(this);
        rowInfo.setClassName(RegistroDocumentoDatos.class.getName());
        rowInfo.setValuesMethod("loadAllValues");
        rowsInfo.add(rowInfo);
        
        DynamicFns.update(dbConn, table.getByRegistryNumberAndCodeQual(registryNumber, code), tableInfo, rowsInfo);
     } catch (Exception e) {
        throw new RegistroExcepcion(RegistroCodigosError.EC_UPDATE_REGISTRY_DOCUMENT);
     } finally {
       try{
         if (dbConn.existConnection())
           dbConn.close();
       }catch(Exception ee){}
     }
  }
  
  
  /**
   * Actualiza los números de registro temporal de los documentos
   * de un registro.
   * @param tmpRegistryNumber Número de registro temporal
   * @throws Exception Si se produce algún error.
   */
  public void updateAll(String tmpRegistryNumber, String entidad) throws Exception {
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfo = new DynamicRow();
     RegistroDocumentoTabla table = new RegistroDocumentoTabla();
     DbConnection dbConn = new DbConnection();
     
     logger.debug("Update Temporal Registry Number To Real Registry Number <-- Tmp Registry Number: " + tmpRegistryNumber + " Real Registry Number: " + registryNumber);
     
     try {
     	//dbConn.open(Configuracion.getDatabaseConnection());
        dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(RegistroDocumentoTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getAllColumnNames");
        
        rowInfo.addRow(this);
        rowInfo.setClassName(RegistroDocumentoDatos.class.getName());
        rowInfo.setValuesMethod("loadAllValues");
        rowsInfo.add(rowInfo);
        
        DynamicFns.update(dbConn, table.getByRegistryNumberQual(tmpRegistryNumber), tableInfo, rowsInfo);
     } catch (Exception e) {
        throw new RegistroExcepcion(RegistroCodigosError.EC_UPDATE_REGISTRY_DOCUMENTS);
     } finally {
       try{
         if (dbConn.existConnection())
           dbConn.close();
       }catch(Exception ee){}
     }
  }
  
  /**
   * Borra los documentos de registro asociadas a un numero de registro.
   * @throws Exception Si se produce algún error.
   */
  public void delete(String entidad) 
    throws RegistroExcepcion{
     RegistroDocumentoTabla table = new RegistroDocumentoTabla();
     
     logger.debug("Delete Registry Documents <-- Registry Number: " + registryNumber);
     
     if (registryNumber == null || registryNumber.equals(""))
       throw new RegistroExcepcion(RegistroCodigosError.EC_INCORRECT_REGISTRY_NUMBER);
     
     DbConnection dbConn = new DbConnection();
     
     try {
     	//dbConn.open(Configuracion.getDatabaseConnection());
        dbConn.open(DBSessionManager.getSession(entidad));
        
        //FALTARIA ELIMINAR LAS ENTRADAS DE LOS DOCUMENTOS
        DbDeleteFns.delete(dbConn, table.getTableName(), table.getByRegistryNumberQual(registryNumber));
        
     } catch (Exception e) {
        throw new RegistroExcepcion(RegistroCodigosError.EC_INCORRECT_REGISTRY_NUMBER);
     } finally {
       try{
         if (dbConn.existConnection())
           dbConn.close();
       }catch(Exception ee){
         throw new RegistroExcepcion(RegistroCodigosError.EC_DELETE_REGISTRY_DOCUMENTS);
       }
     }
  }
  
  
  /**
   * Recupera la lista de documentos de un registro.
   *
   * @return Lista de registros de docuemnto.
   * @throws Exception Si se produce algún error.
   */
  public RegistroDocumentos getRegistryDocuments(String entidad) throws Exception {
    RegistroDocumentos registryDocuments = new RegistroDocumentos();
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfoProcedure = new DynamicRow();
     RegistroDocumentoTabla table = new RegistroDocumentoTabla();
     DbConnection dbConn = new DbConnection();
     RegistroDocumentoDatos registryDocument;
     int counter, size;
  
     if (registryNumber!=null && !registryNumber.equals(""))
    	 logger.debug("Get Registry Documents <-- Registry Number: " + registryNumber);
     else logger.debug("Get Registry Documents");
     
     try {
     	//dbConn.open(Configuracion.getDatabaseConnection());
        dbConn.open(DBSessionManager.getSession(entidad));
        
        tableInfo.setTableObject(table);
        tableInfo.setClassName(RegistroDocumentoTabla.class.getName());
        tableInfo.setTablesMethod("getTableName");
        tableInfo.setColumnsMethod("getAllColumnNames");
        
        rowInfoProcedure.setClassName(RegistroDocumentoDatos.class.getName());
        rowInfoProcedure.setValuesMethod("loadAllValues");
        rowsInfo.add(rowInfoProcedure);
        
        if (registryNumber!=null && !registryNumber.equals(""))
          DynamicFns.selectMultiple(dbConn, table.getByRegistryNumberQual(registryNumber) + " " + table.getOrderByCode(), false, tableInfo, rowsInfo);
        else
          DynamicFns.selectMultiple(dbConn, table.getOrderByCode(), false, tableInfo, rowsInfo);
        size = rowInfoProcedure.getRowCount();
        
        for (counter = 0; counter < size; counter++) {
           registryDocument = (RegistroDocumentoDatos)rowInfoProcedure.getRow(counter);
           registryDocuments.add(registryDocument);
        }
        
        logger.debug("Get Registry Documents --> Registries: " + registryDocuments.toString());
     } catch (RegistroExcepcion e) {
       throw new RegistroExcepcion(RegistroCodigosError.EC_GET_REGISTRY_DOCUMENTS);
     } catch (Exception e) {
       throw new RegistroExcepcion(RegistroCodigosError.EC_ERR_DB);
     } finally {
    	 if (dbConn.existConnection())
             dbConn.close();
     }
     
     return registryDocuments;
  }

}