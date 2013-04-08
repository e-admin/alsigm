/**
 * @author Javier Septién Arceredillo
 *
 * Fecha de Creación: 11-may-2007
 */
package ieci.tecdoc.sgm.ct.database;

import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.core.services.consulta.CriterioBusquedaExpedientes;
import ieci.tecdoc.sgm.ct.database.datatypes.Expediente;
import ieci.tecdoc.sgm.ct.database.datatypes.ExpedienteImpl;
import ieci.tecdoc.sgm.ct.database.datatypes.Expedientes;
import ieci.tecdoc.sgm.ct.database.exception.DbErrorCodes;
import ieci.tecdoc.sgm.ct.database.exception.DbException;
import ieci.tecdoc.sgm.ct.exception.ConsultaCodigosError;
import ieci.tecdoc.sgm.ct.exception.ConsultaExcepcion;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Modela una fila de la tabla de expedientes del CT.
 *
 */
public class ExpedienteDatos extends ExpedienteImpl implements Serializable{
  private static final Logger logger = Logger.getLogger(ExpedienteDatos.class);
  protected boolean isDebugeable = true;

  /**
   * Constructor de clase
   */
  	public ExpedienteDatos() {
	}

  	/**
     * Constructor de clase
     * @param exp Expediente del que toma las propiedades
     */
	public ExpedienteDatos(ExpedienteImpl exp) {

		this.setNumero(exp.getNumero());
		this.setProcedimiento(exp.getProcedimiento());
		this.setFechaInicio(exp.getFechaInicio());
		this.setNumeroRegistro(exp.getNumeroRegistro());
		this.setFechaRegistro(exp.getFechaRegistro());
		this.setInformacionAuxiliar(exp.getInformacionAuxiliar());
		this.setAportacion(exp.getAportacion());
		this.setCodigoPresentacion(exp.getCodigoPresentacion());
		this.setEstado(exp.getEstado());
	}

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
  public Integer cargarColumnas(DbOutputStatement statement, Integer idx)
  throws DbException {
     if (isDebugeable)
       logger.debug("cargarColumnas >> statement: " + statement.toString() + " idx entrada: " + idx);

     int index = idx.intValue();

     try{

    	numero = statement.getShortText(index ++);
 		procedimiento = statement.getShortText(index ++);
 		fechaInicio = statement.getDateTime(index ++);
 		numeroRegistro = statement.getShortText(index ++);
 		fechaRegistro = statement.getDateTime(index ++);
 		informacionAuxiliar = statement.getShortText(index ++);
 		aportacion = statement.getShortText(index ++);
 		codigoPresentacion = statement.getShortText(index ++);
 		estado = statement.getShortText(index ++);

     }catch(Exception e){
       throw new DbException(DbErrorCodes.CT_OBTENER_TODOS_LOS_VALORES, e.getCause());
     }

     if (isDebugeable)
       logger.debug("cargarColumnas << Numero: " + numero);

     return new Integer(index);
  }


  /**
   * Genera la sentencia de inserción de un expediente.
   *
   * @param statement Sentencia sql precompilada.
   * @param idx Indice de posición del primer parámetro que se recoge
   * de la consulta.
   * @return Indice de posición del último parámtro recogido
   * @throws DbException Si se produce algún error.
   */

  public Integer insertar(DbInputStatement statement,Integer idx) throws DbException {

    if (isDebugeable)
     logger.debug("insert >> statement: " + statement.toString());

    int index = 1;

    try{
    	statement.setShortText(index ++, numero);
 		statement.setShortText(index ++, procedimiento);
 		statement.setDateTime(index ++, fechaInicio);
 		statement.setShortText(index ++, numeroRegistro);
 		statement.setDateTime(index ++, fechaRegistro);
 		statement.setShortText(index ++, informacionAuxiliar);
 		statement.setShortText(index ++, aportacion);
 		statement.setShortText(index ++, codigoPresentacion);
 		statement.setShortText(index ++, estado);
    }catch(Exception e){
      throw new DbException(DbErrorCodes.CT_INSERTAR_TODOS_LOS_VALORES, e.getCause());
    }
    return new Integer(index);
  }


	/**
   * Genera la sentencia de actualización de estado de un expediente.
   *
   * @param statement Sentencia sql precompilada.
   * @param idx Indice de posición del primer parámetro que se recoge
   * de la consulta.
   * @return Indice de posición del último parámtro recogido
   * @throws Exception Si se produce algún error.
   */
  public Integer updateStatus(DbInputStatement statement, Integer idx) throws Exception {
     int index = idx.intValue();

     statement.setShortText(index++, estado);

     return new Integer(index);
  }


  /**
   * Carga los valores del expediente
   *
   * @throws ConsultaExcepcion Si se produce algún error.
   */
  public void load(String entidad) throws ConsultaExcepcion {

	  if (numero == null || numero.equals(""))
		  throw new ConsultaExcepcion(ConsultaCodigosError.EC_NUMERO_EXPEDIENTE_INCORRECTO);
	  DynamicTable tableInfo = new DynamicTable();
	  DynamicRows rowsInfo = new DynamicRows();
	  DynamicRow rowInfo = new DynamicRow();
	  ExpedientesTabla tablaExpedientes = new ExpedientesTabla();
	  DbConnection dbConn = new DbConnection();

	  if (isDebugeable)
		  logger.debug("load >> numero: " + numero);

	  boolean codigoIncorrecto = false;

	  try {
		  dbConn.open(DBSessionManager.getSession(entidad));
		  tableInfo.setTableObject(tablaExpedientes);
		  tableInfo.setClassName(ExpedientesTabla.class.getName());
		  tableInfo.setTablesMethod("getNombreTabla");
		  tableInfo.setColumnsMethod("getNombresColumnas");
		  rowInfo.setClassName(ExpedienteDatos.class.getName());
		  rowInfo.setValuesMethod("cargarColumnas");
		  rowInfo.addRow(this);
		  rowsInfo.add(rowInfo);

		  if (!DynamicFns.select(dbConn, tablaExpedientes.getClausulaPorNumero(numero), tableInfo, rowsInfo, false)) {
			  codigoIncorrecto = true;
		  }
	  } catch (Exception e) {
		  throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_EXPEDIENTE);
	  } finally {
		  try{
			  if (dbConn.existConnection())
				  dbConn.close();
		  }catch(Exception ee){
			  DbException DbEx = new DbException(DbErrorCodes.CT_CERRAR_CONEXION);
			  logger.warn(this, DbEx.getCause());
		  }

		  if (codigoIncorrecto)
			  throw new ConsultaExcepcion(ConsultaCodigosError.EC_NUMERO_EXPEDIENTE_INCORRECTO);
	  }
  }


  /**
   * Añade un expediente.
   *
   * @throws ConsultaExcepcion Si se produce algún error.
   * @throws DbException Si se produce algún error.
   */
  public void add(String entidad) throws ConsultaExcepcion, DbException {

     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfo = new DynamicRow();
     ExpedientesTabla table = new ExpedientesTabla();
     DbConnection dbConn = new DbConnection();

     if (numero == null || numero.equals(""))
         throw new ConsultaExcepcion(ConsultaCodigosError.EC_NUMERO_EXPEDIENTE_INCORRECTO);

     logger.debug("add >> Numero de expediente: " + numero);

     try {
    	 dbConn.open(DBSessionManager.getSession(entidad));
        tableInfo.setTableObject(table);
        tableInfo.setClassName(ExpedientesTabla.class.getName());
        tableInfo.setTablesMethod("getNombreTabla");
        tableInfo.setColumnsMethod("getNombresColumnas");

        rowInfo.addRow(this);
        rowInfo.setClassName(ExpedienteDatos.class.getName());
        rowInfo.setValuesMethod("insert");
        rowsInfo.add(rowInfo);

        DynamicFns.insert(dbConn, tableInfo, rowsInfo);
     } catch (Exception e) {
        throw new DbException(DbErrorCodes.CT_ANIADIR_VALOR, e.getCause());
     } finally {
       try{
         if (dbConn.existConnection())
           dbConn.close();
       }catch(Exception ee){
    	   DbException DbEx = new DbException(DbErrorCodes.CT_CERRAR_CONEXION);
    	   logger.warn(this, DbEx.getCause());
       }
     }
  }


  /**
   * Borra los expedientes asociados a un guid.
   * @throws ConsultaExcepcion Si se produce algún error.
   * @throws DbException Si se produce algún error.
   */
  public void delete(String entidad)
    throws ConsultaExcepcion, DbException {

     ExpedientesTabla table = new ExpedientesTabla();

     logger.debug("delete >> numero de expediente: " + numero);

     if (numero == null || numero.equals(""))
       throw new ConsultaExcepcion(ConsultaCodigosError.EC_NUMERO_EXPEDIENTE_INCORRECTO);

     DbConnection dbConn = new DbConnection();

     try {
    	 dbConn.open(DBSessionManager.getSession(entidad));
        DbDeleteFns.delete(dbConn, table.getNombreTabla(), table.getClausulaPorNumero(numero));

     } catch (Exception e) {
    	 throw new DbException(DbErrorCodes.CT_BORRAR_TODOS_LOS_VALORES, e.getCause());
     } finally {
       try{
         if (dbConn.existConnection())
           dbConn.close();
       }catch(Exception ee){
    	   DbException DbEx = new DbException(DbErrorCodes.CT_CERRAR_CONEXION);
    	   logger.warn(this, DbEx.getCause());
       }
     }
  }

  /**
   * Recupera la información del conjunto de expedientes de un interesado
   * realizando una busqueda por fechas.
   * <br/>
   * Existen tres tipos de búsqueda
   * que vendrán definidas por el cuarto parámetro de entrada ('operadorClausula')
   * Este puede tomar los siguientes valores:
   * <br/>
   * Operador			Significado
   *<br/>
   *
   *  >: Mayor que una fecha<br/>
   *  =: Igual a una fecha<br/>
   *  entre: Rango entre dos fechas<br/>
   *
   *
   * @param NIF Se corresponde con el identificador del interesado.
   * @param fecha Fecha de búsqueda.
   * @param fechaHasta Fecha posterior de búsqueda cuando se hace
   * en un intervalo entre dos fechas (solo cuando el operadorClausula viene con valor 'entre').
   * @param operadorClausula Delimita el tipo  de búsqueda
   * @return Expedientes Lista de expedientes resultante de la consulta.
   * @throws Exception Si se produce algún error.
   */

  public Expedientes cargarExpedientesPorNIFYFecha(String NIF, String fecha, String fechaHasta, String operadorClausula, String entidad) throws Exception {


	 logger.debug("cargarExpedientesPorNIFYFecha>>> fechaDesde:" + fecha +
			 ",fechaHasta:" + fechaHasta +",operadorClausula:" + operadorClausula);

	 Expedientes expedientes = new Expedientes();
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfoExpediente = new DynamicRow();
     ExpedientesTabla table = new ExpedientesTabla();
     DbConnection dbConn = new DbConnection();

     try {
        dbConn.open(DBSessionManager.getSession(entidad));

        tableInfo.setTableObject(table);
        tableInfo.setClassName(ExpedientesTabla.class.getName());
        tableInfo.setTablesMethod("getTablaMixta");
        tableInfo.setColumnsMethod("getNombresColumnasConAlias");

        rowInfoExpediente.setClassName(ExpedienteDatos.class.getName());
        rowInfoExpediente.setValuesMethod("cargarColumnas");
        rowInfoExpediente.addRow(this);
        rowsInfo.add(rowInfoExpediente);

        String clausulaCompleta = null;
        if ("entre".equals(operadorClausula))
        	clausulaCompleta = table.getClausulaMixta(NIF) + table.getClausulaPorFechas(fecha, fechaHasta,dbConn);
        else if (">".equals(operadorClausula))
        	clausulaCompleta = table.getClausulaMixta(NIF) + table.getClausulaPorFechaMayorQue(fecha,dbConn);
        else if ("<".equals(operadorClausula))
        	clausulaCompleta = table.getClausulaMixta(NIF) + table.getClausulaPorFechaMenorQue(fecha,dbConn);
        else if ("=".equals(operadorClausula))
        	clausulaCompleta = table.getClausulaMixta(NIF) + table.getClausulaPorFecha(fecha,dbConn);
        else
        	clausulaCompleta = table.getClausulaMixta(NIF);

        if (!DynamicFns.selectMultiple(dbConn,clausulaCompleta, false, tableInfo, rowsInfo)) {
        	//throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_EXPEDIENTES);
  		}

        int size = rowInfoExpediente.getRowCount();

        for (int counter = 1; counter < size; counter++) {
        	ExpedienteImpl expediente = (ExpedienteImpl)rowInfoExpediente.getRow(counter);
            expedientes.add(expediente);
         }

     } catch (Exception e) {
    	 throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_EXPEDIENTES);
     } finally {
    	 if (dbConn.existConnection())
             dbConn.close();
     }

     return expedientes;
  }

  /**
   * Recupera la información del conjunto de expedientes de un interesado
   * realizando una busqueda por los siguientes criterios: expediente, procedimiento, numero registro inicial, fecha de expediente, fecha de registro y estado.
   * <br/>
   *
   *
   * @param NIF Se corresponde con el identificador del interesado.
   * @param criterio Objeto donde vienen los diferentes parámetros de búsqueda.
   * @param entidad Entidad de la aplicación
   * @return Expedientes Lista de expedientes resultante de la consulta.
   * @throws Exception Si se produce algún error.
   */
  public Expedientes cargarExpedientesPorNIFYCriterios(String NIF, CriterioBusquedaExpedientes criterios, String entidad) throws Exception {

		 Expedientes expedientes = new Expedientes();
	     DynamicTable tableInfo = new DynamicTable();
	     DynamicRows rowsInfo = new DynamicRows();
	     DynamicRow rowInfoExpediente = new DynamicRow();
	     ExpedientesTabla table = new ExpedientesTabla();
	     DbConnection dbConn = new DbConnection();

	     try {
	        dbConn.open(DBSessionManager.getSession(entidad));

	        tableInfo.setTableObject(table);
	        tableInfo.setClassName(ExpedientesTabla.class.getName());
	        tableInfo.setTablesMethod("getTablaMixta");
	        tableInfo.setColumnsMethod("getNombresColumnasConAlias");

	        rowInfoExpediente.setClassName(ExpedienteDatos.class.getName());
	        rowInfoExpediente.setValuesMethod("cargarColumnas");
	        rowInfoExpediente.addRow(this);
	        rowsInfo.add(rowInfoExpediente);

	        String clausulaCompleta = table.getClausulaMixta(NIF);
	        String operadorClausula = criterios.getOperadorConsulta();
	        if(StringUtils.isNotEmpty(criterios.getFechaDesde()))
	        {
		        if ("entre".equals(operadorClausula))
		        	clausulaCompleta += table.getClausulaPorFechas(criterios.getFechaDesde(), criterios.getFechaHasta(),dbConn);
		        else if (">".equals(operadorClausula))
		        	clausulaCompleta += table.getClausulaPorFechaMayorQue(criterios.getFechaDesde(),dbConn);
		        else if ("<".equals(operadorClausula))
		        	clausulaCompleta += table.getClausulaPorFechaMenorQue(criterios.getFechaDesde(),dbConn);
		        else {
		        	clausulaCompleta += table.getClausulaPorFecha(criterios.getFechaDesde(),dbConn);
		        }
	        }
	        clausulaCompleta += table.getClausulaPorEstado(criterios.getEstado());

	        clausulaCompleta += table.getClausulaMixtaPorExpediente(criterios.getExpediente());
	        clausulaCompleta += table.getClausulaPorNumRegistroInicial(criterios.getNumeroRegistroInicial());
	        clausulaCompleta += table.getClausulaPorProcedimiento(criterios.getProcedimiento());
	        clausulaCompleta += table.getClausulaPorFechaRegistroInicial(criterios.getFechaRegistroInicialDesde(), criterios.getFechaRegistroInicialHasta(), criterios.getOperadorConsultaFechaInicial(), dbConn);

	        if (!DynamicFns.selectMultiple(dbConn,clausulaCompleta, true, tableInfo, rowsInfo)) {
	        	//throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_EXPEDIENTES);
	  		}

	        int size = rowInfoExpediente.getRowCount();

	        for (int counter = 1; counter < size; counter++) {
	        	ExpedienteImpl expediente = (ExpedienteImpl)rowInfoExpediente.getRow(counter);
	            expedientes.add(expediente);
	         }

	     } catch (Exception e) {
	    	 throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_EXPEDIENTES);
	     } finally {
	    	 if (dbConn.existConnection())
	             dbConn.close();
	     }

	     return expedientes;
	  }



  /**
   * Recupera la información del conjunto de expedientes de un interesado
   * realizando una busqueda por estado.
   *
   * @param NIF Se corresponde con el identificador del interesado.
   * @param estado estado del expediente
   * @return Expedientes Lista de expedientes resultante de la consulta.
   * @throws Exception Si se produce algún error.
   */

  public Expedientes cargarExpedientesPorNIFYEstado(String NIF, String estado, String entidad) throws Exception {


	 logger.debug("cargarExpedientesPorNIFYFecha>>> estado:" + estado);

	 Expedientes expedientes = new Expedientes();
     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfoExpediente = new DynamicRow();
     ExpedientesTabla table = new ExpedientesTabla();
     DbConnection dbConn = new DbConnection();

     try {
        dbConn.open(DBSessionManager.getSession(entidad));

        tableInfo.setTableObject(table);
        tableInfo.setClassName(ExpedientesTabla.class.getName());
        tableInfo.setTablesMethod("getTablaMixta");
        tableInfo.setColumnsMethod("getNombresColumnasConAlias");

        rowInfoExpediente.setClassName(ExpedienteDatos.class.getName());
        rowInfoExpediente.setValuesMethod("cargarColumnas");
        rowInfoExpediente.addRow(this);
        rowsInfo.add(rowInfoExpediente);

        String clausulaCompleta = null;
       	clausulaCompleta = table.getClausulaMixta(NIF) + table.getClausulaPorEstado(estado);

        if (!DynamicFns.selectMultiple(dbConn,clausulaCompleta, false, tableInfo, rowsInfo)) {
        	//throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_EXPEDIENTES);
  		}

        int size = rowInfoExpediente.getRowCount();

        for (int counter = 1; counter < size; counter++) {
        	ExpedienteImpl expediente = (ExpedienteImpl)rowInfoExpediente.getRow(counter);
            expedientes.add(expediente);
         }

     } catch (Exception e) {
    	 throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_EXPEDIENTES);
     } finally {
    	 if (dbConn.existConnection())
             dbConn.close();
     }

     return expedientes;
  }


  /**
   * Recupera la información del conjunto de expedientes de un interesado
   * realizando una busqueda por fechas y por estado.
   * <br/>
   * Existen tres tipos de búsqueda
   * que vendrán definidas por el cuarto parámetro de entrada ('operadorClausula')
   * Este puede tomar los siguientes valores:
   * <br/>
   * Operador			Significado
   *<br/>
   *
   *  >: Mayor que una fecha<br/>
   *  =: Igual a una fecha<br/>
   *  entre: Rango entre dos fechas<br/>
   *
   *
   * @param NIF Se corresponde con el identificador del interesado.
   * @param fecha Fecha de búsqueda.
   * @param fechaHasta Fecha posterior de búsqueda cuando se hace
   * en un intervalo entre dos fechas (solo cuando el operadorClausula viene con valor 'entre').
   * @param operadorClausula Delimita el tipo  de búsqueda
   * @param estado Estado del expediente
   * @return Expedientes Lista de expedientes resultante de la consulta.
   * @throws Exception Si se produce algún error.
   */

  public Expedientes cargarExpedientesPorNIFEstadoYFechas(String NIF, String fecha, String fechaHasta, String operadorClausula, String estado, String entidad) throws Exception {


	  logger.debug("cargarExpedientesPorNIFYFecha>>> fechaDesde:" + fecha +
				 ",fechaHasta:" + fechaHasta +",operadorClausula:" + operadorClausula + ",estado:" + estado);

		 Expedientes expedientes = new Expedientes();
	     DynamicTable tableInfo = new DynamicTable();
	     DynamicRows rowsInfo = new DynamicRows();
	     DynamicRow rowInfoExpediente = new DynamicRow();
	     ExpedientesTabla table = new ExpedientesTabla();
	     DbConnection dbConn = new DbConnection();

	     try {
	        dbConn.open(DBSessionManager.getSession(entidad));

	        tableInfo.setTableObject(table);
	        tableInfo.setClassName(ExpedientesTabla.class.getName());
	        tableInfo.setTablesMethod("getTablaMixta");
	        tableInfo.setColumnsMethod("getNombresColumnasConAlias");

	        rowInfoExpediente.setClassName(ExpedienteDatos.class.getName());
	        rowInfoExpediente.setValuesMethod("cargarColumnas");
	        rowInfoExpediente.addRow(this);
	        rowsInfo.add(rowInfoExpediente);

	        String clausulaCompleta = null;
	        if (operadorClausula.equals("entre"))
	        	clausulaCompleta = table.getClausulaMixta(NIF) + table.getClausulaPorFechas(fecha, fechaHasta,dbConn);
	        else if (operadorClausula.equals(">"))
	        	clausulaCompleta = table.getClausulaMixta(NIF) + table.getClausulaPorFechaMayorQue(fecha,dbConn);
	        else if (operadorClausula.equals("<"))
	        	clausulaCompleta = table.getClausulaMixta(NIF) + table.getClausulaPorFechaMenorQue(fecha,dbConn);
	        else
	        	clausulaCompleta = table.getClausulaMixta(NIF) + table.getClausulaPorFecha(fecha,dbConn);

       	clausulaCompleta += table.getClausulaPorEstado(estado);

        if (!DynamicFns.selectMultiple(dbConn,clausulaCompleta, false, tableInfo, rowsInfo)) {
        	//throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_EXPEDIENTES);
  		}

        int size = rowInfoExpediente.getRowCount();

        for (int counter = 1; counter < size; counter++) {
        	ExpedienteImpl expediente = (ExpedienteImpl)rowInfoExpediente.getRow(counter);
            expedientes.add(expediente);
         }

     } catch (Exception e) {
    	 throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_EXPEDIENTES);
     } finally {
    	 if (dbConn.existConnection())
             dbConn.close();
     }

     return expedientes;
  }

  public Expediente cargarExpedientePorNIF(String NIF, String expediente, String entidad) throws Exception {

	  logger.debug("cargarExpedientePorNIF>>> nif:" + NIF +
				 ",expediente:" + expediente);

     DynamicTable tableInfo = new DynamicTable();
     DynamicRows rowsInfo = new DynamicRows();
     DynamicRow rowInfoExpediente = new DynamicRow();
     ExpedientesTabla table = new ExpedientesTabla();
     DbConnection dbConn = new DbConnection();

     try {
        dbConn.open(DBSessionManager.getSession(entidad));

        tableInfo.setTableObject(table);
        tableInfo.setClassName(ExpedientesTabla.class.getName());
        tableInfo.setTablesMethod("getTablaMixta");
        tableInfo.setColumnsMethod("getNombresColumnasConAlias");

        rowInfoExpediente.setClassName(ExpedienteDatos.class.getName());
        rowInfoExpediente.setValuesMethod("cargarColumnas");
        rowInfoExpediente.addRow(this);
        rowsInfo.add(rowInfoExpediente);

        String clausulaCompleta = table.getClausulaMixta(NIF) + table.getClausulaMixtaPorNumero(expediente);

        if (!DynamicFns.selectMultiple(dbConn,clausulaCompleta, false, tableInfo, rowsInfo)) {
        	//throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_EXPEDIENTES);
  		}

        int size = rowInfoExpediente.getRowCount();
        if (size > 1) {
        	return (ExpedienteImpl)rowInfoExpediente.getRow(1);
        }

        return null;
     } catch (Exception e) {
    	 throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_EXPEDIENTES);
     } finally {
    	 if (dbConn.existConnection())
             dbConn.close();
     }
  }


  /**
	 * Añade un registro.
	 *
	 * @param dbCon
	 *            Conexion a base de datos.
	 * @throws Exception
	 *             Si se produce algún error.
	 */
	public void nuevo(String entidad) throws Exception {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		ExpedientesTabla table = new ExpedientesTabla();
		DbConnection dbConn = new DbConnection();

		logger.debug("nuevo");

		try {
			dbConn.open(DBSessionManager.getSession(entidad));

			tableInfo.setTableObject(table);
			tableInfo.setClassName(ExpedientesTabla.class.getName());
			tableInfo.setTablesMethod("getNombreTabla");
			tableInfo.setColumnsMethod("getNombresColumnas");

			rowInfo.addRow(this);
			rowInfo.setClassName(ExpedienteDatos.class.getName());
			rowInfo.setValuesMethod("insertar");
			rowsInfo.add(rowInfo);

			DynamicFns.insert(dbConn, tableInfo, rowsInfo);

		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_INSERTAR_EXPEDIENTE, exc.getCause());
		} finally {
			if (dbConn.existConnection())
	             dbConn.close();
		}
	}

	/**
	 * Borra el registro cuyo número de registro que se pasa como parámetro.
	 *
	 * @param dbCon
	 *            Objeto conexión a base de datos.
	 * @throws Exception
	 *             Si se produce algún error.
	 */
	public void eliminar(DbConnection dbCon, String entidad) throws Exception {

		ExpedientesTabla table = new ExpedientesTabla();
		DbConnection dbConn = new DbConnection();

		logger.debug("eliminar");

		try {
			dbConn.open(DBSessionManager.getSession(entidad));

			DbDeleteFns.delete(dbConn, table.getNombreTabla(), table.getClausulaPorNumero(getNumero()));

		} catch (Exception exc) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_ELIMINAR_EXPEDIENTE, exc.getCause());
		} finally {
			if (dbConn.existConnection())
	             dbConn.close();
		}
	}

	  /**
	    * Actualiza el estado del registro cuyo número de registro
	    * se pasa como parámetro.
	    *
	    * @param registryNumber Número de registro.
	    * @throws Exception Si se produce algún error.
	    */
	   public void actulizarEstado(String entidad) throws Exception {
	      DynamicTable tableInfo = new DynamicTable();
	      DynamicRows rowsInfo = new DynamicRows();
	      DynamicRow rowInfo = new DynamicRow();
	      ExpedientesTabla table = new ExpedientesTabla();
	      DbConnection dbConn = new DbConnection();

	      logger.debug("actualizar estado <-- numero expediente: " + numero + ", estado: " + estado);

		  try {
		     dbConn.open(DBSessionManager.getSession(entidad));

	         tableInfo.setTableObject(table);
	         tableInfo.setClassName(ExpedientesTabla.class.getName());
	         tableInfo.setTablesMethod("getNombreTabla");
	         tableInfo.setColumnsMethod("getNombreColumnaEstado");

	         rowInfo.addRow(this);
	         rowInfo.setClassName(ExpedienteDatos.class.getName());
	         rowInfo.setValuesMethod("updateStatus");
	         rowsInfo.add(rowInfo);

	         DynamicFns.update(dbConn, table.getClausulaPorNumero(numero), tableInfo, rowsInfo);

		  } catch (Exception exc) {
				throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_EXPEDIENTES, exc.getCause());
			} finally {
				if (dbConn.existConnection())
		             dbConn.close();
			}
	   }

}