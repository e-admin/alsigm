package com.ieci.tecdoc.common.entity.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipInputStream;

import com.ieci.tecdoc.common.invesdoc.Idocvtblctlg;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfQuery;

/**
 * Interfaz de acceso a datos
 *
 * @author LMVICENTE
 * @creationDate 28-may-2004 10:08:11
 * @version
 * @since
 */
/**
 * @author 66575267
 *
 */
public interface DBEntityDAO {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	public static final String DB2_TYPE = "DB2";
	public static final String SQLSERVER_TYPE = "SQLSERVER";
	public static final String ORACLE_TYPE = "ORACLE";
	public static final String POSTGRESQL_TYPE = "POSTGRESQL";

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/***************************************************************************
	 * Public methods
	 **************************************************************************/

	/**
	 * Método que obtiene la fecha del servidor de bases de datos
	 *
	 * @param entidad
	 * @return fecha del servidor
	 */
	public Timestamp getDBServerDate(String entidad);

	/**
	 * Método que nos devuelve la fecha de la última conexión del usuario
	 *
	 * @param idUser
	 *            identificador del usuario
	 * @param entidad
	 * @return fecha de la última conexión del usuario
	 */
	public Timestamp getUserLastConnection(Integer idUser, String entidad);

	/**
	 * Método que nos devuelve el identificador del último registro creado por
	 * un usuario en un libro determinado.
	 *
	 * En caso de que el parametro <code>fdrid</code> tenga algun valor,
	 * actualizaremos la base de datos asignando este nuevo valor como ultimo
	 * registro creado
	 *
	 * @param fdrid
	 *            identificador del registro
	 * @param idUser
	 *            identificador del usuario
	 * @param bookId
	 *            identificador del libro
	 * @param entidad
	 * @return identidicador del registro
	 */
	public int lastRegister(Integer fdrid, Integer idUser, Integer bookId,
			String entidad);

	/**
	 * Método que nos devuelve el tipo de base de datos con el que estamos
	 * trabajando
	 *
	 * <ul>
	 * <li>DB2</li>
	 * <li>Postgres</li>
	 * <li>Oracle</li>
	 * <li>SqlServer</li>
	 * </ul>
	 *
	 * @return tipo de base de datos
	 */
	public String getDataBaseType();

	/**
	 * Método que nos devuelve la versión de la base de datos
	 *
	 * @param entidad
	 * @return versión de la base de datos
	 */
	public String getVersion(String entidad);

	/**
	 * Método que nos devuelve la sentencia para acceder al ultimo registro
	 * creado por un usuario
	 *
	 * @param tableName
	 *            archivador sobre el que se realizara la consulta
	 * @param filter
	 *            filtro que tiene el usuario sobre los registros
	 * @param orderby
	 *            criterios de ordenacion
	 * @return sencencia sql
	 */
	public String findAxSFLastForUserSENTENCE(String tableName, String filter,
			boolean orderby);

	/**
	 * Método que nos devuelve la sentencia para acceso a informes
	 *
	 * @param axsf
	 *            datos del archivador con el que trabajamos
	 * @param axsfQuery
	 *            criterios de busqueda sobre el archivador con el que
	 *            trabajamos
	 * @param filter
	 *            filtro que tiene el usuario sobre los registros
	 * @return sentencia sql
	 */
	public String createWhereClausuleReport(AxSf axsf, AxSfQuery axsfQuery,
			String filter);

	/**
	 * Método que nos devuelve la sentencia para acceso a los registros
	 *
	 * @param axsf
	 *            datos del archivador con el que trabajamos
	 * @param axsfQuery
	 *            criterios de busqueda sobre el archivador con el que
	 *            trabajamos
	 * @param filter
	 *            filtro que tiene el usuario sobre los registros
	 * @param orderby
	 *            criterios de ordenacion
	 * @return sentencia sql
	 */
	public String createWhereClausule(AxSf axsf, AxSfQuery axsfQuery,
			String filter, boolean orderby);

	/**
	 * Método que nos convierte una fecha a un timestamp de base de datos
	 *
	 * @param date
	 *            fecha a convertir
	 * @param index
	 * @return
	 */
	public String getTimeStampField(Date date, int index);

	/**
	 * Método que nos devuelve una consulta con la que obtendremos una lista de
	 * identificadores de registro en funcion de los criterios introducidos
	 *
	 * @param axsf
	 *            datos del archivador con el que trabajamos
	 * @param tableName
	 *            nombre del archivador
	 * @param axsfQuery
	 *            criterios de busqueda sobre el archivador con el que
	 *            trabajamos
	 * @param begin
	 *            posicion de inicio sobre los elementos de la lista que nos
	 *            devuelve
	 * @param end
	 *            posicion de fin sobre los elementos de la lista que nos
	 *            devuelve
	 * @param filter
	 *            filtro que tiene el usuario sobre los registros
	 * @param orderby
	 *            criterios de ordenacion
	 * @return sentencia sql
	 */
	public String findAxSFAllSENTENCE(AxSf axsf, String tableName,
			AxSfQuery axsfQuery, int begin, int end, String filter,
			boolean orderby);

	/**
	 * Método que nos devuelve una consulta con la que obtendremos el numero de
	 * registros que coinciden con los criterios introducidos
	 *
	 * @param axsf
	 *            datos del archivador con el que trabajamos
	 * @param tableName
	 *            nombre del archivador
	 * @param axsfQuery
	 *            criterios de busqueda sobre el archivador con el que
	 *            trabajamos
	 * @param filter
	 *            filtro que tiene el usuario sobre los registros
	 * @param orderby
	 *            criterios de ordenacion
	 * @return sentencia sql
	 */
	public String findAxSFAllSizeSENTENCE(AxSf axsf, String tableName,
			AxSfQuery axsfQuery, String filter, boolean orderby);

	/**
	 * Método que prepara la ejecucion de una consulta
	 *
	 * @param axsfQuery
	 *            criterios de busqueda sobre el archivador con el que
	 *            trabajamos
	 * @param axsfP
	 *            datos del archivador con el que trabajamos
	 * @param ps
	 *            PreparedStatement
	 * @throws SQLException
	 */
	public void assignAxSFPreparedStatement(AxSfQuery axsfQuery, AxSf axsfP,
			PreparedStatement ps) throws SQLException;

	/**
	 * Método que nos devuelve el caracter que la base de datos con la que
	 * trabajamos interpreta como like
	 *
	 * @return traduccion del like
	 */
	public String getLikeCharacter();

	/**
	 * Método que nos devuelve el siguiente identificador de una secuencia
	 *
	 * @param userId
	 *            identificador del usuario
	 * @param entidad
	 * @return siguiente identificador
	 * @throws SQLException
	 */
	public int getContador4SCRREGORIGDOC(Integer userId, String entidad)
			throws SQLException;

	/**
	 * Método que nos devuelve el siguiente identificador de una secuencia
	 *
	 * @param userId
	 *            identificador del usuario
	 * @param entidad
	 * @return siguiente identificador
	 * @throws SQLException
	 */
	public int getContador4PERSONS(Integer userId, String entidad)
			throws SQLException;

	/**
	 * Método que nos devuelve el siguiente identificador de una secuencia
	 *
	 * @param userId
	 *            identificador del usuario
	 * @param entidad
	 * @return siguiente identificador
	 * @throws SQLException
	 */
	public int getContador4SCRADDRESS(Integer userId, String entidad)
			throws SQLException;

	/**
	 * Método que obtiene el siguiente identificador para un documento
	 *
	 * @param bookId
	 *            identificador del libro al que se adjunta el documento
	 * @param entidad
	 * @return siguiente identificador
	 * @throws SQLException
	 */
	public int getNextDocID(Integer bookId, String entidad) throws SQLException;

	/**
	 * Método que nos devuelve el siguiente identificador de una secuencia
	 *
	 * @param userId
	 *            identificador del usuario
	 * @param entidad
	 * @return siguiente identificador
	 * @throws SQLException
	 */
	public int getNextIdForInter(Integer userId, String entidad)
			throws SQLException;

	/**
	 * Método que nos devuelve el siguiente identificador de una secuencia
	 *
	 * @param userId
	 *            identificador del usuario
	 * @param entidad
	 * @return siguiente identificador
	 * @throws SQLException
	 */
	public int getNextIdForScrModifreg(Integer userId, String entidad)
			throws SQLException;

	/**
	 * Método que nos devuelve el siguiente identificador de una secuencia
	 *
	 * @param userId
	 *            identificador del usuario
	 * @param entidad
	 * @return siguiente identificador
	 * @throws SQLException
	 */
	public int getNextIdForScrDistReg(Integer userId, String entidad)
			throws SQLException;

	/**
	 * Método que nos devuelve el siguiente identificador de una secuencia
	 *
	 * @param userId
	 *            identificador del usuario
	 * @param entidad
	 * @return siguiente identificador
	 * @throws SQLException
	 */
	public int getNextIdForScrRegAsoc(Integer userId, String entidad)
			throws SQLException;

	/**
	 * Método que nos devuelve el siguiente identificador de una secuencia
	 *
	 * @param userId
	 *            identificador del usuario
	 * @param entidad
	 * @return siguiente identificador
	 * @throws SQLException
	 */
	public int getNextIdForScrDistRegState(Integer userId, String entidad)
			throws SQLException;

	/**
	 * Método que obtiene el siguiente identificador para un registro
	 *
	 * @param bookId
	 *            identificador del libro al que se adjunta el documento
	 * @param entidad
	 * @return siguiente identificador
	 * @throws SQLException
	 */
	public int getNextIdForRegister(Integer bookID, String entidad)
			throws SQLException;

	/**
	 * Método que obtiene el siguiente identificador para un usuario
	 *
	 * @param guid
	 *            identificador del usuario en un sistema ldap
	 * @param fullName
	 *            nombre del usuario en un sistema ldap
	 * @param entidad
	 * @return identificador del usuario
	 * @throws SQLException
	 */
	public int getNextIdForUser(String guid, String fullName, String entidad)
			throws SQLException;

	/**
	 * Método que obtiene el siguiente identificador para un campo extendido
	 *
	 * @param bookId
	 *            identificador del libro al que se adjunta el documento
	 * @param entidad
	 * @return siguiente identificador
	 * @throws SQLException
	 */
	public int getNextIdForExtendedField(Integer bookID, String entidad)
			throws SQLException;

	/**
	 * Método que incrementa el contador de número de registro en la secuencia
	 * central de numeración
	 *
	 * @param year
	 *            Año correspondiente al contador
	 * @param bookType
	 *            Tipo de libro de registro (1.- Entrada y 2.- Salida)
	 * @param entidad
	 * @throws SQLException
	 */
	public void updateScrCntCentral(int year, int bookType, String entidad)
			throws SQLException;

	/**
	 * Método que incrementa el contador de número de registro en la secuencia
	 * local de numeración por oficina
	 *
	 * @param year
	 *            Año correspondiente al contador
	 * @param bookType
	 *            Tipo de libro de registro (1.- Entrada y 2.- Salida)
	 * @param ofic
	 *            Identificación de la oficina de registro de su contador
	 * @param entidad
	 * @throws SQLException
	 */
	public void updateScrCntOficina(int year, int bookType, int ofic,
			String entidad) throws SQLException;

	/**
	 * Método que obtiene el número de registro de la secuencia central de
	 * numeración
	 *
	 * @param year
	 *            Año correspondiente al contador
	 * @param bookType
	 *            Tipo de libro de registro (1.- Entrada y 2.- Salida)
	 * @param entidad
	 * @return siguiente numero de registro
	 * @throws SQLException
	 */
	public int getNumRegFromScrCntCentral(int year, int bookType, String entidad)
			throws SQLException;

	/**
	 * Método que obtiene el número de registro en la secuencia local de
	 * numeración por oficina
	 *
	 * @param year
	 *            Año correspondiente al contador
	 * @param bookType
	 *            Tipo de libro de registro (1.- Entrada y 2.- Salida)
	 * @param ofic
	 *            Identificación de la oficina de registro de su contador
	 * @param entidad
	 * @return siguiente numero de registro
	 * @throws SQLException
	 */
	public int getNumRegFromScrCntOficina(int year, int bookType, int ofic,
			String entidad) throws SQLException;

	/**
	 * Método para bloquear el contador de número de registro en la secuencia
	 * central de numeración
	 *
	 * @param year
	 *            Año correspondiente al contador
	 * @param entidad
	 * @throws SQLException
	 */
	public void lockScrCentral(int year, String entidad) throws SQLException;

	/**
	 * Método para bloquear el contador de número de registro en la secuencia
	 * local de numeración por oficina
	 *
	 * @param year
	 * @param entidad
	 * @throws SQLException
	 */
	public void lockScrOficina(int year, String entidad) throws SQLException;

	/**
	 * Método para bloquear los registros distribuidos
	 *
	 * @param id
	 *            identificador de la distribución del registro
	 * @param entidad
	 * @throws SQLException
	 */
	public void lockScrDistReg(int id, String entidad) throws SQLException;

	/**
	 * Método que elimina la configuración de un usuario
	 *
	 * @param id
	 *            identificador del usuario
	 * @param entidad
	 * @throws SQLException
	 */
	public void deleteUserConfig(int id, String entidad) throws SQLException;

	/**
	 * Método que actualiza la tabla de bloqueo de relaciones
	 *
	 * @param typeBook
	 *            Tipo de libro de registro (1.- Entrada y 2.- Salida)
	 * @param typeRel
	 *            Tipo de relacion (1.- Por destino y 2.- Por origen)
	 * @param idofic
	 *            identificador de la oficina de registro
	 * @param entidad
	 * @throws SQLException
	 */
	public void lockScrRelations(int typeBook, int typeRel, int idofic,
			String entidad) throws SQLException;

	/**
	 * Método que crea un nuevo contador de número de registro en la secuencia
	 * central de numeración
	 *
	 * @param year
	 *            Año correspondiente al contador
	 * @param bookType
	 *            Tipo de libro de registro (1.- Entrada y 2.- Salida)
	 * @param entidad
	 * @return identificador del nuevo contador
	 * @throws SQLException
	 */
	public int createScrCentralRow(int year, int bookType, String entidad)
			throws SQLException;

	/**
	 * Método que crea un nuevo contador de número de registro en la secuencia
	 * local de numeración por oficina
	 *
	 * @param year
	 *            Año correspondiente al contador
	 * @param bookType
	 *            Tipo de libro de registro (1.- Entrada y 2.- Salida)
	 * @param ofic
	 *            Identificación de la oficina de registro de su contador
	 * @param entidad
	 * @return identificador del nuevo contador
	 * @throws SQLException
	 */
	public int createScrOficinaRow(int year, int bookType, int ofic,
			String entidad) throws SQLException;

	/**
	 * Método que crea una nueva relacion
	 *
	 * @param typebook
	 *            Tipo de libro de registro (1.- Entrada y 2.- Salida)
	 * @param typerel
	 *            Tipo de relacion (1.- Por destino y 2.- Por origen)
	 * @param relyear
	 *            Año de la relación
	 * @param relmonth
	 *            Mes de la relación
	 * @param relday
	 *            Día de la relación
	 * @param idofic
	 *            Identificador de la oficina de registro
	 * @param reldate
	 *            Fecha de la relación
	 * @param idunit
	 *            Identificador de la unidad administrativa
	 * @param nrel
	 *            Número de la relación
	 * @param entidad
	 * @return identificador de la relacion
	 * @throws SQLException
	 */
	public int insertScrRelations(int typebook, int typerel, int relyear,
			int relmonth, int relday, int idofic, Date reldate, int idunit,
			int nrel, String entidad) throws SQLException;

	/**
	 * Método que inserta los cambios producidos sobre un campo en concreto.
	 * <br/> Las tablas en la que puede realizar la inserción son:
	 * <ul>
	 * <li>SCR_VALSTR: Tabla de valores modificados de tipo caracter</li>
	 * <li>SCR_VALDATE: Tabla de valores modificados de tipo fecha</li>
	 * <li>SCR_VALNUM: Tabla de valores modificados de tipo numérico</li>
	 * <ul>
	 *
	 * @param id
	 *            Identificador de registro de modificación
	 * @param oldValue
	 *            Valor antiguo del campo modificado
	 * @param newValue
	 *            Valor nuevo del campo modificado
	 * @param entidad
	 * @throws SQLException
	 */
	public void insertAudit(Integer id, Object oldValue, Object newValue,
			String entidad) throws SQLException;

	/**
	 * Método que elimina la relacion de los registros distribuidos con origen
	 * en un departamento que han cambiado de estado con las tablas:
	 * <ul>
	 * <li>SCR_PROCREG: Relación entre registros y procesos</li>
	 * <li>SCR_DISTREGSTATE: Tabla de cambios de estado de mensajes recibidos</li>
	 * <li>SCR_DISTREG: Tabla de distribución de registros</li>
	 * </ul>
	 *
	 * @param idArch
	 *            identificador del archivo de registro
	 * @param idFdr
	 *            identificador del registro
	 * @param idOrg
	 *            Identificador del organismo de origen
	 * @param entidad
	 * @param state
	 *            Estado de la distribucíon del registro
	 *            <ul>
	 *            <li>1.- Pendiente</li>
	 *            <li>2.- Aceptado</li>
	 *            <li>3.- Archivado</li>
	 *            <li>4.- Rechazado</li>
	 *            <li>5.- Redistribuido</li>
	 *            </ul>
	 * @throws SQLException
	 */
	public void deleteDistributeForUpdate(int idArch, int idFdr, int idOrg,
			String entidad, int state) throws SQLException;

	/**
	 * Método que elimina la relacion de los registros distribuidos con origen
	 * en un departamento y estado pendiente con las tablas:
	 * <ul>
	 * <li>SCR_PROCREG: Relación entre registros y procesos</li>
	 * <li>SCR_DISTREGSTATE: Tabla de cambios de estado de mensajes recibidos</li>
	 * <li>SCR_DISTREG: Tabla de distribución de registros</li>
	 * </ul>
	 *
	 * @param idArch
	 *            identificador del archivo de registro
	 * @param idFdr
	 *            identificador del registro
	 * @param idOrg
	 *            Identificador del organismo de origen
	 * @param entidad
	 * @throws SQLException
	 */
	public void deleteDistributeForUpdate(int idArch, int idFdr, int idOrg,
			String entidad) throws SQLException;

	/**
	 * Método que nos devuelve los campos validados de un tipo de libro de
	 * registro, para una sentencia sql
	 *
	 * @param bookType
	 *            Tipo de libro de registro (1.- Entrada y 2.- Salida)
	 * @return campos validados
	 */
	public String getAditionFields(int bookType);

	/**
	 * Método que nos devuelve parte de una sentencia sql con la que poder
	 * obtener el valor de los campos validados de un tipo de libro de registro
	 *
	 * @param bookType
	 *            Tipo de libro de registro (1.- Entrada y 2.- Salida)
	 * @return sentencia sql
	 */
	public String getSelectAditionFields(int bookType);

	/**
	 * Método que crea una sentencia para obtener lo danos necesarios para la
	 * generación de un informe
	 *
	 * @param tableName
	 *            nombre de la tabla
	 * @param bookId
	 *            identificador del libro de registro
	 * @param where
	 *            clausula where de la sentencia a crear
	 * @param fieldList
	 *            lista de columnas que necesitamos
	 * @param index
	 * @param aditionalFields
	 *            lista de columnas adicionales que necesitamos
	 * @param bookType
	 *            tipo del libro de registro
	 * @param maxReportRegister
	 *            número máximo de registros para el informe
	 * @return sentencia sql
	 */
	public String getReportCreateSelectMultiple1(String tableName,
			Integer bookId, String where, String fieldList, int index,
			String aditionalFields, int bookType, Integer maxReportRegister);

	/**
	 * @deprecated
	 * @param tableName
	 * @param bookId
	 * @param where
	 * @param fieldList
	 * @param index
	 * @return
	 */
	public String getReportCreateSelectMultiple(String tableName,
			Integer bookId, String where, String fieldList, int index);

	/**
	 * Método que crea una sentencia sql para la creaccion de una tabla que
	 * contiene la información que se quiere imprimir en un informe
	 *
	 * @param tableName
	 *            nombre de la tabla que se va a crear
	 * @param bookId
	 *            identificador del libro de registro
	 * @param where
	 *            clausuala where
	 * @param fieldList
	 *            lista de columnas
	 * @param aditionalFields
	 *            lista adicional de columnas
	 * @param bookType
	 *            tipo de libro de registro
	 * @param maxReportRegister
	 *            número maximo de resultados de la tabla a crear
	 * @return sentencia sql
	 */
	public String getReportCreateSelect1(String tableName, Integer bookId,
			String where, String fieldList, String aditionalFields,
			int bookType, Integer maxReportRegister);

	public String getReportCreateSelectLastRegister(String tableName,
			Integer bookId, String where, String fieldList,
			String aditionalFields, int bookType);

	public String getReportCreateSelectLastRegister1(String tableName,
			Integer bookId, int fdrid, String fieldList,
			String aditionalFields, int bookType);

	/**
	 * @deprecated
	 * @param tableName
	 * @param bookId
	 * @param where
	 * @param fieldList
	 * @return
	 */
	public String getReportCreateSelect(String tableName, Integer bookId,
			String where, String fieldList);

	/**
	 * @deprecated
	 * @param tableName
	 * @param fieldList
	 * @param bookId
	 * @return
	 */
	public String getReportCreate(String tableName, String fieldList,
			Integer bookId);

	/**
	 * @deprecated
	 * @param tableName
	 * @param fieldList
	 * @return
	 */
	public String getReportCreate(String tableName, String fieldList);

	/**
	 * @deprecated
	 * @param tableName
	 * @param bookId
	 * @param fieldList
	 * @param where
	 * @return
	 */
	public String getReportInsert(String tableName, Integer bookId,
			String fieldList, String where);

	/**
	 * @deprecated
	 * @param tableName
	 * @param bookId
	 * @param fieldList
	 * @return
	 */
	public String getReportInsert(String tableName, Integer bookId,
			String fieldList);

	/**
	 * Método que relaciona la fecha y el organismo de un registro.
	 *
	 * @param tableName
	 *            nombre de la tabla en la que se realizara esta relacion
	 * @param opcion
	 *            indica si se trata la relación se hará por origen o por
	 *            destino
	 * @param entidad
	 * @return Matriz de relaciones
	 * @throws SQLException
	 */
	public Object[][] getRelationsTupla(String tableName, int opcion,
			String entidad) throws SQLException;

	/**
	 * Método que nos devuelve el número de relaciones
	 *
	 * @param typebook
	 *            Tipo de libro de registro (1.- Entrada y 2.- Salida)
	 * @param typerel
	 *            Tipo de relacion (1.- Por destino y 2.- Por origen)
	 * @param idofic
	 *            Identificador de la oficina de registro
	 * @param idunit
	 *            Identificador de la unidad administrativa
	 * @param relyear
	 *            Año de la relación
	 * @param entidad
	 * @return número de relaciones
	 * @throws SQLException
	 */
	public int getNewNumRelations(int typebook, int typerel, int idofic,
			int idunit, int relyear, String entidad) throws SQLException;

	/**
	 * Método que nos devuelve el número de oficinas asociadas a un usuario. Sin
	 * contar con la oficina asociada (si la hay) al departamento del usuario
	 *
	 * @param userId
	 *            identificador del usuario
	 * @param useridofic
	 *            identificador de la oficina del usuario
	 * @param entidad
	 * @return número de oficinas
	 * @throws SQLException
	 */
	public int getOtherOffice(int userId, String entidad) throws SQLException;

	/**
	 * Elimina una tabla o vista
	 *
	 * @param tableName
	 *            nombre de la tabla o vista a eliminar
	 * @param entidad
	 */
	public void dropTableOrView(String tableName, String entidad);

	/**
	 * Método que crea una tabla o vista
	 *
	 * @param sentence
	 *            sentencia sql de creacion de tabla o vista
	 * @param entidad
	 * @throws SQLException
	 */
	public void createTableOrView(String sentence, String entidad)
			throws SQLException;

	/**
	 * @deprecated
	 * @param sentence
	 * @param entidad
	 * @throws SQLException
	 */
	public void insertTableOrView(String sentence, String entidad)
			throws SQLException;

	/**
	 * @deprecated
	 * @param create
	 * @param insert
	 * @param axsf
	 * @param axsfQuery
	 * @param entidad
	 * @throws SQLException
	 */
	public void createTableOrView(String create, String insert, AxSf axsf,
			AxSfQuery axsfQuery, String entidad) throws SQLException;

	/**
	 * @deprecated
	 * @param create
	 * @param axsf
	 * @param axsfQuery
	 * @param entidad
	 * @throws SQLException
	 */
	public void createTableOrView(String create, AxSf axsf,
			AxSfQuery axsfQuery, String entidad) throws SQLException;

	/**
	 * @deprecated
	 * @param insert
	 * @param axsf
	 * @param axsfQuery
	 * @param entidad
	 * @throws SQLException
	 */
	public void insertTableOrView(String insert, AxSf axsf,
			AxSfQuery axsfQuery, String entidad) throws SQLException;

	/**
	 * @deprecated
	 * @param insert
	 * @param beginDate
	 * @param endDate
	 * @param unit
	 * @param entidad
	 * @throws SQLException
	 */
	public void createTableOrView(String insert, Date beginDate, Date endDate,
			int unit, String entidad) throws SQLException;

	/**
	 * Método que devuelve el número de registros que tiene una tabla o una
	 * vista
	 *
	 * @param tableName
	 *            nombre de la tabla o vista
	 * @param entidad
	 * @return numero de registros de la tabla
	 * @throws SQLException
	 */
	public int getTableOrViewSize(String tableName, String entidad)
			throws SQLException;

	/**
	 * Método que devuelve el número de registros en estado incompleto
	 *
	 * @param bookId
	 *            identificador del libro de registro
	 * @param scroficId
	 *            identificador de la oficina con la que está conectado el
	 *            usuario
	 * @param entidad
	 * @return Número de registros
	 * @throws SQLException
	 */
	public int getSizeIncompletRegister(Integer bookId, Integer scroficId,
			String entidad) throws SQLException;

	/**
	 * @deprecated
	 * @param tableName
	 * @param newColumnName
	 * @param entidad
	 * @throws SQLException
	 */
	public void alterTableAddColumnString(String tableName,
			String newColumnName[], String entidad) throws SQLException;

	/**
	 * @deprecated
	 * @param tableName
	 * @param oldColumnName
	 * @param newColumnName
	 * @param linkedTableName
	 * @param linkedColumnName
	 * @param entidad
	 * @throws SQLException
	 */
	public void updateFromTable(String tableName, String oldColumnName[],
			String newColumnName[], String linkedTableName[],
			String linkedColumnName[], String entidad) throws SQLException;

	/**
	 * Método que nos devuelve una sentencia para obtener los registros
	 * distribuidos
	 *
	 * @param tableName
	 *            nombre de la tabla
	 * @param bookId
	 *            identificador del libro
	 * @param where
	 *            clausula where de la sentencia
	 * @param index
	 * @return
	 */
	public String getTemporalTableDistributionQuerySentence(String tableName,
			Integer bookId, String where, int index);

	/**
	 * Método que nos devuelve una sentencia para obtener los registros
	 * distribuidos y ordenados según el criterio indicado
	 *
	 * @param tableName - Nombre de la tabla
	 * @param bookId - Identificador del libro
	 * @param where - clausula where de la sentencia
	 * @param regWhere - clausula de búsqueda por campos del registro
	 * @param isCreateTable - indica si se debe concatenar la sentencia o generar nueva tabla/vista
	 * @param isInBook - indica si el libro es de entrada o salida
	 * @param language
	 *            <ul>
	 *            <li>Vacio o nulo: Castellano</li>
	 *            <li>es: Castellano</li>
	 *            <li>eu: Euskera</li>
	 *            <li>ct: Catalán</li>
	 *            <li>gl: Gallego</li>
	 *            </ul>
	 *
	 * @return String con la sentencia que genera la tabla temporal
	 */
	public String getTemporalTableDistributionQuerySentenceOrderBy(String tableName,
			Integer bookId, String where, String regWhere, boolean isCreateTable,  boolean isInBook, String language);

	/**
	 * Método que nos devuelve el número de registros distribuidos según la
	 * sentencia sql que le pasamos
	 *
	 * @param sentence
	 *            sentencia sql
	 * @param entidad
	 * @return numero de registros distribuidos
	 * @throws SQLException
	 */
	public int getDistributionSize(String sentence, String entidad)
			throws SQLException;

	/**
	 * Método que devuelve el número de personas que coinciden con la consulta
	 *
	 * @param sentence
	 *            consulta sql
	 * @param entidad
	 * @return número de personas
	 * @throws SQLException
	 */
	public int getPersonListSize(String sentence, String entidad)
			throws SQLException;

	/**
	 * Método que obtiene una lista de libros de registro que tienen registros
	 * distribuidos
	 *
	 * @param query
	 *            sentencia sql
	 * @param entidad
	 * @return lista de libros
	 * @throws SQLException
	 */
	public List getIdArchDistribution(String query, String entidad)
			throws SQLException;

	/**
	 * Método que comprueba si un registro ha sido distribuido y aceptado o no
	 *
	 * @param bookId
	 *            identificador del libro de registro
	 * @param fdrid
	 *            identificador del registro
	 * @param idOfic
	 *            identificador de la oficina con la que está conectado el
	 *            usuario
	 * @param entidad
	 * @return
	 *            <ul>
	 *            <li>true: el registro está aceptado</li>
	 *            <li>false: el registro no está aceptado</li>
	 *            </ul>
	 */
	public boolean getDistAccept(Integer bookId, int fdrid, Integer idOfic,
			String entidad);

	/**
	 * Método que comprueba si un registro tiene una distribucion aceptada
	 *
	 * @param bookId
	 *            identificador del libro de registro
	 * @param fdrid
	 *            identificador del registro
	 * @param entidad
	 * @return
	 *            <ul>
	 *            <li>true: el registro está aceptado</li>
	 *            <li>false: el registro no está aceptado</li>
	 *            </ul>
	 */
	public boolean getDistAcceptExist(Integer bookId, int fdrid, String entidad);

	/**
	 * Método que actualiza el estado del registro distribuido aceptado
	 *
	 * @param exist
	 *            parametro que nos indica si esta en estado aceptado o no
	 * @param bookId
	 *            identificador del libro de registro
	 * @param fdrId
	 *            identificador del registro
	 * @param officeId
	 *            identidicador de la oficina del usuario
	 * @param state
	 *            estado al que se actualiza
	 * @param accUser
	 *            nombre de usuario que realiza la modificacion
	 * @param accDate
	 *            fecha en la que se realiza la modificacion
	 * @param distDate
	 *            fecha de la distribución
	 * @param entidad
	 */
	public void updateInsertDistAccept(boolean exist, Integer bookId,
			int fdrId, int officeId, int state, String accUser, Date accDate,
			Date distDate, String entidad);

	/**
	 * Eliminamos el registro indicado de la tabla SCR_DISTACCEPT
	 *
	 * @param bookId
	 *            identificador del libro de registro
	 * @param fdrid
	 *            identificador del registro
	 * @param entidad
	 */
	public void deleteDistAccept(Integer bookId, int fdrid, String entidad);

	/**
	 * Método que nos devuelve el final de una sentencia sql que fija el limite
	 * de registros a devulver por la consulta
	 *
	 * @param maxReportRegister
	 *            limite de registros
	 * @return sentencia sql
	 */
	public String getFinalQuery(Integer maxReportRegister);

	/**
	 * Método que almacena un fichero compartido entre registros de distintos
	 * libros
	 *
	 * @param fileId
	 *            identificador de la imagen
	 * @param ownerBookId
	 *            identificador de libro de registro que contiene el registro al
	 *            que pertenece originariamente el fichero
	 * @param ownerRegId
	 *            identificador de registro al que pertenece originalmente el
	 *            fichero
	 * @param bookId
	 *            identificador del libro de registro que contiene el registro
	 *            que comparte el fichero
	 * @param regId
	 *            identificador del registro que comparte el fichero
	 * @param entidad
	 * @return 1
	 * @throws SQLException
	 */
	public int insertScrSharedFiles(int fileId, int ownerBookId,
			int ownerRegId, int bookId, int regId, String entidad)
			throws SQLException;

	/**
	 * Método que recupera un informe de la base de datos y lo almacena en un
	 * directorio temporal
	 *
	 * @param reportId
	 *            identificador del informe
	 * @param temporalDirectory
	 *            directorio temporal en el que se almacenara el informe
	 * @param entidad
	 * @throws SQLException
	 */
	public ZipInputStream getReportData(int reportId, String temporalDirectory,
			String entidad) throws SQLException;

	/**
	 * Método que nos devuelve la hash de un documento
	 *
	 * @param bookId
	 *            identificador del libro de registro
	 * @param fdrid
	 *            identificador del registro
	 * @param pageId
	 *            identificador de la pagina
	 * @param hash
	 * @param selDel
	 *            parametro que indica si estamos seleccionando o actualizando
	 *            la hash
	 *            <ul>
	 *            <li>true: seleccion de hash</li>
	 *            <li>false: actualizacion de hash</li>
	 *            </ul>
	 * @param entidad
	 * @return hash del documento
	 */
	public String getHashDocument(Integer bookId, int fdrid, int pageId,
			String hash, boolean selDel, String entidad);


	/**
	 * Metodo que borra la información del hash (tabla SCR_PAGEINFO) a partir del registro, libro y pagina
	 *
	 * @param bookId - Id del libro
	 * @param fdrid - Id del registro
	 * @param pageId - Id de la pagina
	 * @param entidad - Entidad

	 * @throws SQLException
	 */
	public void deleteHashDocument(Integer bookId, int fdrid, int pageId,
			String entidad) throws SQLException;

	/**
	 * @deprecated
	 * @param entidad
	 */
	public void recicleBin(String entidad);

	/**
	 * Método que actualiza el perfil del usuario
	 *
	 * @param userId
	 *            identificador del usuario
	 * @param prodId
	 *            identificador de la aplicacion
	 * @param type
	 *            tipo de perfil
	 * @param entidad
	 */
	public void updateRole(int userId, int prodId, int type, String entidad);

	/**
	 * Método que actualiza los datos de un usuario ldap en las tablas de
	 * invesdoc
	 *
	 * @param id
	 *            identificador del usuario en nuestras tablas
	 * @param ldapFullName
	 *            nombre completo del usuario
	 * @param entidad
	 */
	public void updateLdapFullName(int id, String ldapFullName, String entidad);

	/**
	 * Método que almacena las preferencias de configuracion de un usuario
	 *
	 * @param result
	 *            xml que contiene las preferencias de configuracion
	 * @param idUser
	 *            identifador del usuario
	 * @param type
	 *            <ul>
	 *            <li>0.- Inserta nueva configuración</li>
	 *            <li>1.- Actualiza la configuración</li>
	 *            </ul>
	 * @param entidad
	 */
	public void saveOrUpdateUserConfig(String result, Integer idUser, int type,
			String entidad);

	/**
	 * Método para eliminar los contadores
	 *
	 * @param userId
	 *            identificador del usuario
	 * @param entidad
	 */
	public void deleteIdsGenerationTable(Integer userId, String entidad);

	/**
	 * Método que obtiene todas las relaciones unidades-oficinas para una
	 * oficina determinada
	 *
	 * @param idofic
	 *            identificador de la oficina
	 * @param entidad
	 * @return lista de relaciones
	 * @throws SQLException
	 */
	public List getPrivOrgs(int idofic, String entidad) throws SQLException;

	/**
	 * @deprecated
	 * @param idUser
	 * @param entidad
	 * @return
	 */
	public String getUsrEmail(Integer idUser, String entidad);

	/**
	 * @deprecated
	 * @param idOfic
	 * @param entidad
	 * @return
	 */
	public String getOficEmail(Integer idOfic, String entidad);

	/**
	 * @deprecated
	 * @param entidad
	 * @param idAddress
	 * @return
	 */
	public List getListAddrTel(String entidad, int idAddress);

	/**
	 * Método que nos devuelve una sentencia para buscar los registros cerrados
	 * de un libro de registro
	 *
	 * @param tableName
	 *            nombre de la tabla del libro de registro
	 * @param filter
	 *            filtro de la busqueda
	 * @return
	 */
	public String findAxSFToCloseSentence(String tableName, String filter);

	/**
	 * Método que almacena en la tabla de configuración del usuario la oficina
	 * preferente del usuario
	 *
	 * @param idUser
	 *            identificador del usuario
	 * @param idOficPref
	 *            identificador de la oficina preferente
	 * @param entidad
	 */
	public void updateUserConfigIdOficPref(Integer idUser, Integer idOficPref,
			String entidad);

	/**
	 * Método que obtiene los interesados de un registro
	 *
	 * @param bookId
	 *            identificador del libro de registro
	 * @param fdrid
	 *            identificador del registro
	 * @param orderByOrd
	 * @param entidad
	 * @return lista de interesados
	 */
	public List getScrRegisterInter(Integer bookId, int fdrid,
			boolean orderByOrd, String entidad);

	/**
	 * Método que relaciona a un interesado con un registro
	 *
	 * @param id
	 *            identificador
	 * @param archId
	 *            identificador del libro de registro
	 * @param fdrId
	 *            identificador del registro
	 * @param name
	 *            nombre completo del interesado
	 * @param personId
	 *            identificador del interesado
	 * @param addressId
	 *            identificador de la direccion del interesado selecionada como
	 *            de referencia
	 * @param ord
	 *            prioridad entre los interesados relacionados con el registro
	 * @param entidad
	 * @return 1
	 * @throws SQLException
	 */
	public int insertScrRegInt(int id, int archId, int fdrId, String name,
			int personId, int addressId, int ord, String entidad)
			throws SQLException;

	/**
	 *Metodo que borra un interesado
	 *
	 * @param bookId
	 * @param fdrId
	 * @param entidad
	 * @throws SQLException
	 */
	public void deleteScrRegInt(int bookId, int fdrId, String entidad)
			throws SQLException;

	/**
	 * Método que devuelte la descripcion de un elemento de la tabla indicada en
	 * funcion del idioma
	 *
	 * @param id
	 *            identificador
	 * @param isScrTypeAdm
	 *            <ul>
	 *            <li>true: Es Tipo de Unidad Administrativa</li>
	 *            <li>false: No es Tipo de Unidad Administrativa</li>
	 *            </ul>
	 * @param isScrCa
	 *            <ul>
	 *            <li>true: Es Tipo de Asunto</li>
	 *            <li>false: No es Tipo de Asunto</li>
	 *            </ul>
	 * @param language
	 *            <ul>
	 *            <li>Vacio o nulo: Castellano</li>
	 *            <li>es: Castellano</li>
	 *            <li>eu: Euskera</li>
	 *            <li>ct: Catalán</li>
	 *            <li>gl: Gallego</li>
	 *            </ul>
	 * @param tableNameAux
	 *            nombre de la tabla sobre la que realizaremos la consulta
	 * @param entidad
	 * @return descripcion
	 * @throws SQLException
	 */
	public String getDescriptionByLocale(Integer id, boolean isScrTypeAdm,
			boolean isScrCa, String language, String tableNameAux,
			String entidad) throws SQLException;

	/**
	 * Método que obtiene la lista de informes en el idioma indicado
	 *
	 * @param reportType
	 *            tipo de informe
	 * @param bookType
	 *            tipo de libro de registro
	 * @param language
	 *            <ul>
	 *            <li>Vacio o nulo: Castellano</li>
	 *            <li>es: Castellano</li>
	 *            <li>eu: Euskera</li>
	 *            <li>ct: Catalán</li>
	 *            <li>gl: Gallego</li>
	 *            </ul>
	 * @param tableNameAux
	 *            nombre de la tabla sobre la que realizaremos la consulta
	 * @param entidad
	 * @return lista de informes
	 * @throws SQLException
	 */
	public List getReportsListByLocale(int reportType, int bookType,
			String language, String tableNameAux, String entidad)
			throws SQLException;

	/**
	 * @deprecated
	 * @param id
	 * @param language
	 * @param tableNameAux
	 * @param entidad
	 * @return
	 * @throws SQLException
	 */
	public Idocvtblctlg getIdocvtblctlg(int id, String language,
			String tableNameAux, String entidad) throws SQLException;

	/**
	 * Método que obtiene el identificador de un documento
	 *
	 * @param bookId
	 *            identificador del libro de registro
	 * @param fdrId
	 *            identificador del registro
	 * @param pageId
	 *            identificador de la página
	 * @param entidad
	 * @return identificador del documento
	 */
	public String getDocUID(Integer bookId, Integer fdrId, Integer pageId,
			String entidad);

	/**
	 * Método que almacena una nueva relación entre documentos anexos a
	 * registros y repositorios.
	 *
	 * @param bookID
	 *            identificador del libro de registro
	 * @param fdrID
	 *            identificador del registro
	 * @param pageID
	 *            identificador de la pagina
	 * @param docUID
	 *            identificador del documento en el repositosio
	 * @param entidad
	 * @return 1
	 * @throws SQLException
	 */
	public int insertScrPageRepository(int bookID, int fdrID, int pageID,
			String docUID, String entidad) throws SQLException;

	/**
	 * Método que borra la relación de documentos anexos a registros y repositorios
	 *
	 * @param bookId - Id del libro
	 * @param fdrId - Id del registro
	 * @param pageID - Id de la pagina
	 * @param entidad - Entidad
	 *
	 * @throws SQLException
	 */

	public void deleteScrPageRepository(int bookId, int fdrId, int pageID,
			String entidad) throws SQLException;
	/**
	 * Método que nos devuleve el identificador unico del documento en el
	 * repositorio documental
	 *
	 * @param isicresDocUID
	 *            identificador del documento en invesicres - invesdoc
	 * @param entidad
	 * @return
	 * @throws SQLException
	 */
	public String getDocumentRepositoryUID(String isicresDocUID, String entidad)
			throws SQLException;

	/**
	 * Método en el que almacenamos un nueva nueva relación entre el
	 * identificador del documento en invesicres - invesdoc y el identificador
	 * en el repositorio documental
	 *
	 * @param documentRepositoryUID
	 *            identificador del documento en el repositorio documental
	 * @param entidad
	 * @return
	 * @throws SQLException
	 */
	public String insertScrDocumentRepository(String documentRepositoryUID,
			String entidad) throws SQLException;

	/**
	 * Método que obtiene el identificador de la configuracion del repositorio
	 * en la base de datos
	 *
	 * @param bookType
	 *            tipo del libro de registro
	 * @param entidad
	 * @return
	 * @throws SQLException
	 */
	public Integer getRepositoryByBookType(Integer bookType, String entidad)
			throws SQLException;

	/**
	 * Método que obtiene la configuracion del repositorio en la base de datos
	 *
	 * @param id
	 *            identificador de la configuración del repositorio
	 * @param entidad
	 * @return
	 * @throws SQLException
	 */
	public String getRepositoryConfiguration(Integer id, String entidad)
			throws SQLException;

	/**
	 * Método que almacena una nueva relación entre documentos compulsados
	 * y un localizador
	 *
	 * @param bookID
	 *            identificador del libro de registro
	 * @param folderId
	 *            identificador del registro
	 * @param pageID
	 *            identificador de la pagina
	 * @param locator
	 *            localizador del documento compulsado
	 * @param entidad
	 * @return 1
	 * @throws SQLException
	 */
	public int insertScrDocLocator(int bookId, int folderId, int pageID, String locator, String entidad)
			throws SQLException;


	/**
	 * Método que devuelve la fecha maxima de cierre de cada libro por oficina de registro
	 *
	 * @param bookID
	 *            identificador del libro de registro
	 * @param entidad
	 * @param oficId
	 * @return fecha maxima
	 * @throws SQLException
	 */

	public Timestamp getMaxDateClose(Integer bookId, String entidad, Integer oficId)
			throws SQLException;

	/**
	 * Método que obtiene la información de la distribución de la tabla y según
	 * el ordenación indicado
	 *
	 * @param firstRow
	 *            - Posición inicial de elementos a mostrar
	 * @param maxResults
	 *            - Máximo de elementos a mostrar
	 * @param entidad
	 *            - Entidad
	 * @param orderBy
	 *            - Orden a realizar la búsqueda
	 * @param tableName
	 *            - Nombre de la tabla sobre la que se realiza la búsqueda
	 *
	 * @return Listado de objetos {@link ScrDistReg}
	 *
	 * @throws SQLException
	 */
	public List getListDistributionOrderBy(int firstRow, int maxResults,
			String entidad, String orderBy, String tableName)
			throws SQLException;

	/***************************************************************************
	 * Protected methods
	 **************************************************************************/

	/***************************************************************************
	 * Private methods
	 **************************************************************************/

	/***************************************************************************
	 * Inner classes
	 **************************************************************************/

	/***************************************************************************
	 * Test brench
	 **************************************************************************/

}
