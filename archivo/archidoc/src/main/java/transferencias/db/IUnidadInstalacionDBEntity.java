/*
 * Created on 25-nov-2005
 *
 */
package transferencias.db;

import java.util.List;

import transferencias.vos.UnidadInstalacionVO;

import common.db.IDBEntity;
import common.util.IntervalOptions;

/**
 * Entidad: <b>ASGTUINSTALACIONRE</b>
 * 
 * @author IECISA
 * 
 */
public interface IUnidadInstalacionDBEntity extends IDBEntity {
	/**
	 * Inserta una nueva fila en la tabla de unidades de instalacion
	 * 
	 * @param conn
	 *            Conexion a la base de datos
	 * @param unidadInstalacion
	 *            Datos de la unidad de instalacion a insertar
	 */
	public void insertRow(final UnidadInstalacionVO unidadInstalacion);

	/**
	 * Elimina una unidad de instalacion de la base de datos
	 * 
	 * @param unidadInstalacion
	 *            Unidad de instalacion a eliminar
	 */
	public void dropRow(final UnidadInstalacionVO unidadInstalacion);

	/**
	 * Obtiene las unidades de instalacion pertenecientes a una relacion de
	 * entrega
	 * 
	 * @param conn
	 *            Conexion a la base de datos
	 * @param idRelacion
	 *            Identificador de la relacion de entega
	 * @return lista con las unidades documentales pertenecientes a la relacion
	 *         de entrega
	 */
	public List fetchRowsByIdRelacion(String idRelacion);

	// public List fetchRowsByIdRelacion(String idRelacion, boolean asignado);

	public List fetchRowsByIdRelacion(String idRelacion, int tipoUInstalacion);

	/**
	 * Obtiene las unidades de instalacion de una relacion de entrega.
	 * 
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @param ordenes
	 *            Órdenes.
	 * @return Lista de unidades de instalacion {@link UnidadInstalacionVO}
	 */
	public List fetchRowsByIdRelacion(String idRelacion, IntervalOptions ordenes);

	/*
	 * LUIS public boolean checkIfTodasUnidadesInstalacionEnEstadosXIdRelacion(
	 * String idRelacion, int[] estados) { final MutableBoolean
	 * uisConMismoEstado = new MutableBoolean(false); DBCommand command = new
	 * DBCommand(this) { public void codeLogic(DbConnection conn) throws
	 * Exception { uisConMismoEstado.setValue( DbSelectFns.selectCount(conn,
	 * TABLE_NAME, qual.toString())); } }; command.execute(); return
	 * uisConMismoEstado.getValue(); String qual = new
	 * StringBuffer().append("WHERE NOT").append(ID_RELACION_COLUMN_NAME)
	 * .append("='").append(idRelacion).append("' ")
	 * .append(TransferenciasDBUtil.generateORTokens(ESTADO_COTEJO_FIELD,
	 * estados)) .append("order by ").append(ORDEN_COLUMN_NAME).toString();
	 * 
	 * return getRows(conn, qual).size()>0?true:false; }
	 */
	public boolean existUnidadesInstalacionEnEstadosXIdRelacion(
			final String idRelacion, final int[] estados);

	/*
	 * LUIS static List getRows(DbConnection conn, String qual) throws
	 * Exception{ final ArrayList rows = new ArrayList(); DbOutputRecordSet
	 * rowSet = new AbstractDbOutputRecordSet() { DbOutputRecord aRow = new
	 * DbOutputRecord() { public void getStatementValues(DbOutputStatement stmt)
	 * throws Exception { UnidadInstalacion unidadInstalacion =
	 * getUInstalacionFilled(stmt); rows.add(unidadInstalacion); } }; public
	 * DbOutputRecord newRecord() throws Exception { return aRow; } };
	 * DbSelectFns.select(conn,TABLE_NAME, COLUM_NAMES_LIST,qual,false,rowSet);
	 * return rows.size()>0?rows:null; }
	 */
	public void updateFieldSignatura(String idUInstalacion, String signatura);

	/**
	 * Actualiza el estado y flag de devolución de un conjunto de unidades de
	 * instalación.
	 * 
	 * @param idsUInstalacion
	 *            Lista de identificadores de unidad de instalacion
	 * @param estado
	 *            Nuevo estado al que se pasan la unidades de instalacion
	 * @param devuelta
	 *            Indica si las unidades de instalación están devueltas.
	 */
	public void updateFieldEstado(String[] idsUInstalacion, int estado,
			boolean devuelta);

	public void updateFieldEstado(String idUInstalacion, int estado);

	/*
	 * LUIS METODO DUPLICADO public static void updateFieldEstado (DbConnection
	 * conn, String[] idsUInstalacion, int estado) throws Exception { String
	 * qual = new StringBuffer(" WHERE ")
	 * .append(TransferenciasDBUtil.generateORTokens
	 * (ID_FIELD,idsUInstalacion)).toString();
	 * 
	 * DbUpdateFns.updateLongInteger(conn, TABLE_NAME,
	 * ESTADO_COTEJO_COLUMN_NAME, estado, qual); }
	 */public void updateFieldEstadoYDevolver(String[] idsUInstalacion,
			int estado, String devolver);

	public void updateFieldEstadoYDevolverYNotasCotejo(String idUInstalacion,
			int estado, String devolver, String notasCotejo);

	public void deleteXIdRelacion(final String idRelacion);

	/*
	 * public static void deleteNotNeeded(DbConnection conn, String
	 * codigoRelacion, int ultimaCajaUsada) throws Exception { String qual = new
	 * StringBuffer().append("WHERE ").append(CODIGO_RELACION_COLUMN_NAME)
	 * .append
	 * ("='").append(codigoRelacion).append("' AND ").append(NUMERO_CAJA_COLUMN_NAME
	 * ) .append(">").append(ultimaCajaUsada).toString();
	 * DbDeleteFns.delete(conn ,TABLE_NAME, qual); }
	 */public void setErroresCorregidos(String idRelacion);

	// public void deleteNoAsignadasAUdoc(final String idRelacion);
	public List getUnidadesInstalacionVacias(String idRelacion);

	/**
	 * Recupera de la base de datos la informacion de una unidad de instalacion
	 * a partir de su identificador
	 * 
	 * @param idUinstalacion
	 *            Identificador de unidad de instalacion
	 * @return Datos de unidad de instalacion {@link UnidadInstalacionVO}
	 */
	public UnidadInstalacionVO fetchRowById(String idUinstalacion);

	public UnidadInstalacionVO fetchRowBySignatura(String signatura);

	public UnidadInstalacionVO fetchRowBySignaturaEnRENoValidada(
			String signatura);

	public UnidadInstalacionVO fetchRowBySignaturaRE(String idRelacion,
			String signatura);

	/**
	 * Permite obtener una unidad de instalación a partir de su signatura y el
	 * id de archivo en la relación de entrega
	 * 
	 * @param signatura
	 *            Signatura a Buscar
	 * @param idArchivo
	 *            Archivo de la unidad de instalación a buscar
	 * @return UnidadInstalacionVO
	 */
	public UnidadInstalacionVO fetchRowBySignaturaYArchivo(String signatura,
			String idArchivo);

	/**
	 * Permite obtener una unidad de instalación a partir de su signatura y el
	 * id de archivo en la relación de entrega Sólo busca las unidades que
	 * pertenezcan a una relación de entrega no validada.
	 * 
	 * @param signatura
	 *            Signatura a Buscar
	 * @param idArchivo
	 *            Archivo de la unidad de instalación a buscar
	 * @return UnidadInstalacionVO
	 */
	public UnidadInstalacionVO fetchRowBySignaturaYArchivoEnRENoValidada(
			String signatura, String idArchivo);

	/**
	 * Permite obtener una unidad de instalación a partir de su signatura y el
	 * id de archivo en la relación de entrega Sólo busca las unidades que
	 * pertenezcan a una relación de entrega no validada.
	 * 
	 * @param signatura
	 *            Signatura a Buscar
	 * @param idArchivo
	 *            Archivo de la unidad de instalación a buscar
	 * @param idRelEntrega
	 *            Identificador de la relación de entrega a excluir
	 * @return UnidadInstalacionVO
	 */
	public UnidadInstalacionVO fetchRowBySignaturaYArchivoEnRENoValidadaOtraRelacion(
			String signatura, String idArchivo, String idRelEntregaExcluir);

	/**
	 * Obtiene las ubicaciones de las unidades de instalación de una relación de
	 * entrega.
	 * 
	 * @param idRelacion
	 *            Identificador de la relación de entrega.
	 * @return Lista de ubicaciones.
	 */
	public List getUbicacionesRelacion(String idRelacion);

	/**
	 * Obtiene las ubicaciones de las unidades de instalación de una relación de
	 * entrega con reencajado
	 * 
	 * @param idRelacion
	 *            Identificador de la relación de entrega.
	 * @return Lista de ubicaciones.
	 */
	public List getUbicacionesRelacionReencajado(String idRelacion);

	/**
	 * Obtiene las ubicaciones de las unidades de instalación de una relación de
	 * entrega. Realiza un outer join con hueco para que devuelva registros
	 * aunque no este ubicada.
	 * 
	 * @param idRelacion
	 *            Identificador de la relación de entrega.
	 * @return Lista de ubicaciones.
	 */
	List getUbicacionesRelacionOuterJoin(String idRelacion,
			IntervalOptions ordenes, String idDepositoDestino);

	/**
	 * Cuenta el número de unidades de instalacion incluidas en una relacion de
	 * entrega
	 * 
	 * @param idRelacion
	 *            Identificador de relación de entrega
	 * @return Numero de unidades de instalación de la relación de entrega
	 */
	public int countUIsRelacion(String idRelacion);

	public int countUIsRelacion(String idRelacion, boolean asignando);

	public int countUIsAsignadasRelacion(String idRelacion);

	/**
	 * Cuenta el número de unidades de instalacion incluidas en una relacion de
	 * entrega
	 * 
	 * @param idRelacion
	 *            Identificador de relación de entrega
	 * @return Numero de unidades de instalación de la relación de entrega
	 */
	public int maxOrdenUIsRelacion(String idRelacion);

	/**
	 * Cuenta el número de unidades de instalación en una relación de entrena
	 * que están asociadas a cajas existentes.
	 * 
	 * @param idRelacion
	 * @return
	 */
	public boolean allUIsAsociadas(String idRelacion);

	/**
	 * Actualiza el orden de las unidades de instalación pertenecientes a la
	 * relacion de entrega
	 * 
	 * @param idRelacionEntrega
	 * @param orden
	 */
	public void incrementOrdenUi(String idRelacionEntrega, int orden,
			int incremento);

	/**
	 * Obtiene una unidade de instalación
	 * 
	 * @param idRelacion
	 *            Cadena que contiene la identificación de la relación de
	 *            entrega
	 * @param orden
	 *            Entero que contiene la posición de la unidad de instalación en
	 *            la relación de entrega
	 * @return
	 */
	public UnidadInstalacionVO getUIByIdRelEntregaYOrden(String idRelacion,
			int orden);

	public UnidadInstalacionVO fetchRowBySignaturaEnRENoValidadaOtraRelacion(
			String signatura, String idRelacion);

}