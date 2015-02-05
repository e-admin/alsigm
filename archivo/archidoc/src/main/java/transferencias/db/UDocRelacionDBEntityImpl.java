package transferencias.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInputStatement;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbUtil;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import transferencias.vos.UnidadDocumentalVO;

import common.Constants;
import common.db.AliasedColumnDef;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.SigiaDbInputRecord;
import common.db.TableDef;
import common.exceptions.TooManyResultsException;
import common.lang.MutableBoolean;
import common.pagination.PageInfo;
import common.util.ArrayUtils;

import descripcion.db.TextoCortoUDocREDBEntityImpl;

/**
 * Clase con los metodos encargados de recuperar y actualizar la tabla en la que
 * se almacenan los datos de unidades documentales
 */
public class UDocRelacionDBEntityImpl extends DBEntity implements
		IUDocRelacionDBEntity {

	static Logger logger = Logger.getLogger(UDocRelacionDBEntityImpl.class);

	public static final String TABLE_NAME = "ASGTUNIDADDOCRE";
	public static final String ID_COLUMN_NAME = "id";
	public static final String ID_RELACION_COLUMN_NAME = "idrelentrega";
	public static final String TIPO_TRANSFERENCIA_COLUMN_NAME = "tipotransferencia";
	public static final String NUMERO_EXPEDIENTE_COLUMN_NAME = "numexp";
	public static final String SISTEMA_PRODUCTOR_COLUMN_NAME = "codsistproductor";
	public static final String FECHA_INICIO_COLUMN_NAME = "fechaextini";
	public static final String FECHA_FIN_COLUMN_NAME = "fechaextfin";
	public static final String ASUNTO_COLUMN_NAME = "asunto";
	public static final String VALIDADA_COLUMN_NAME = "validada";
	public static final String SINDOCS_FISICOS_COLUMN_NAME = "sindocsfisicos";
	public static final String INFO_COLUMN_NAME = "info";
	public static final String NUM_PARTES_COLUMN_NAME = "numpartes";
	public static final String ORDEN_COLUMN_NAME = "orden";
	public static final String MARCAS_BLOQUEO_COLUMNNAME = "marcasbloqueo";

	public static final DbColumnDef ID_FIELD = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef ID_RELACION_FIELD = new DbColumnDef(null,
			TABLE_NAME, ID_RELACION_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);
	public static final DbColumnDef TIPO_TRANSFERENCIA_FIELD = new DbColumnDef(
			null, TABLE_NAME, TIPO_TRANSFERENCIA_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, false);
	public static final DbColumnDef NUMERO_EXPEDIENTE_FIELD = new DbColumnDef(
			"numeroExpediente", NUMERO_EXPEDIENTE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 256, false);
	public static final DbColumnDef SISTEMA_PRODUCTOR_FIELD = new DbColumnDef(
			SISTEMA_PRODUCTOR_COLUMN_NAME, DbDataType.SHORT_TEXT, false);
	public static final DbColumnDef FECHA_INICIO_FIELD = new DbColumnDef(
			"fechaInicio", FECHA_INICIO_COLUMN_NAME, DbDataType.DATE_TIME,
			false);
	public static final DbColumnDef FECHA_FIN_FIELD = new DbColumnDef(
			"fechaFin", FECHA_FIN_COLUMN_NAME, DbDataType.DATE_TIME, false);
	public static final DbColumnDef ASUNTO_FIELD = new DbColumnDef(
			ASUNTO_COLUMN_NAME, DbDataType.SHORT_TEXT, 128, false);
	public static final DbColumnDef VALIDADA_FIELD = new DbColumnDef(
			VALIDADA_COLUMN_NAME, DbDataType.SHORT_TEXT, 1, false);
	public static final DbColumnDef SINDOCS_FISICOS_FIELD = new DbColumnDef(
			SINDOCS_FISICOS_COLUMN_NAME, DbDataType.SHORT_TEXT, 1, false);
	public static final DbColumnDef INFO_FIELD = new DbColumnDef("xmlInfo",
			INFO_COLUMN_NAME, DbDataType.LONG_TEXT, false);
	public static final DbColumnDef NUM_PARTES_FIELD = new DbColumnDef(
			"numeroPartes", NUM_PARTES_COLUMN_NAME, DbDataType.LONG_INTEGER,
			false);
	public static final DbColumnDef ORDEN_FIELD = new DbColumnDef(null,
			TABLE_NAME, ORDEN_COLUMN_NAME, DbDataType.LONG_INTEGER, false);
	public static final DbColumnDef MARCAS_BLOQUEO_FIELD = new DbColumnDef(
			MARCAS_BLOQUEO_COLUMNNAME, DbDataType.SHORT_INTEGER, false);

	public static final DbColumnDef[] TABLE_COLUMNS = { ID_FIELD,
			ID_RELACION_FIELD, TIPO_TRANSFERENCIA_FIELD,
			NUMERO_EXPEDIENTE_FIELD, SISTEMA_PRODUCTOR_FIELD,
			FECHA_INICIO_FIELD, FECHA_FIN_FIELD, ASUNTO_FIELD,
			SINDOCS_FISICOS_FIELD, VALIDADA_FIELD, INFO_FIELD,
			NUM_PARTES_FIELD, ORDEN_FIELD, MARCAS_BLOQUEO_FIELD };

	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(TABLE_COLUMNS);
	private static final DbColumnDef[] COLUMNS_TO_UPDATE = {
			NUMERO_EXPEDIENTE_FIELD, FECHA_INICIO_FIELD, FECHA_FIN_FIELD,
			ASUNTO_FIELD, SINDOCS_FISICOS_FIELD, INFO_FIELD };
	public static final String COLUMNS_TO_UPDATE_LIST = DbUtil
			.getColumnNames(COLUMNS_TO_UPDATE);

	/**
	 * Obtiene el nombre de la tabla
	 *
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public UDocRelacionDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public UDocRelacionDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	private UnidadDocumentalVO getUnidadDocumental(String qual) {
		return (UnidadDocumentalVO) getVO(qual, TABLE_NAME, TABLE_COLUMNS,
				UnidadDocumentalVO.class);
	}

	private List getUnidadesDocumentales(String qual) {
		return getVOS(qual, TABLE_NAME, TABLE_COLUMNS, UnidadDocumentalVO.class);
	}

	private List getUnidadesDocumentales(String qual, PageInfo pageInfo)
			throws TooManyResultsException {

		return getVOS(qual, pageInfo.getOrderBy(getMappingOrderCriterion()),
				TABLE_NAME, TABLE_COLUMNS, UnidadDocumentalVO.class, pageInfo);

	}

	public void insertRow(final UnidadDocumentalVO udoc) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {

				udoc.setId(getGuid(udoc.getId()));

				// List
				// list=fetchRowsByCodigoRelacionOrderByOrden(udoc.getIdRelEntrega());
				// int orden=1;
				// if(list!=null && list.size()>0)
				// {
				// UnidadDocumentalVO
				// unidadDocumentalVO=(UnidadDocumentalVO)list.get(list.size()-1);
				// orden=unidadDocumentalVO.getOrden();
				// if(orden==IeciTdType.NULL_LONG_INTEGER)
				// orden=1;
				// else orden++;
				// }
				// udoc.setOrden(orden);
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						TABLE_COLUMNS, udoc);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);

			}
		};
		command.execute();
	}

	public UnidadDocumentalVO fetchRow(String udocID) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_FIELD, udocID));
		return getUnidadDocumental(qual.toString());
	}

	public void dropRow(final String udocID) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer(DBUtils.WHERE)
						.append(DBUtils.generateEQTokenField(ID_FIELD, udocID));
				DbDeleteFns.delete(conn, TABLE_NAME, qual.toString());
			}
		};
		command.execute();
	}

	/**
	 * Obtiene la lista de unidades documentales incluidas en una relacion de
	 * entrega
	 *
	 * @param conn
	 *            Conexion a la base de datos
	 * @param codigoRelacion
	 *            Codigo de la relacion de entrega cuyas unidades documentales
	 *            se desean recuperar
	 * @return Lista con la informacion referente a las unidades documentales
	 *         que estan incluidas en la relacion de entrega
	 * @throws TooManyResultsException
	 * @throws Exception
	 */
	// public List fetchRowsByCodigoRelacion(String codigoRelacion, PageInfo
	// pageInfo)
	// throws TooManyResultsException {
	// StringBuffer qual = new
	// StringBuffer("WHERE ").append(DBUtils.generateEQTokenField(
	// ID_RELACION_FIELD, codigoRelacion));
	// return getUnidadesDocumentales(qual.toString(), pageInfo);
	// }

	public int countRowsByCodigoRelacion(String codigoRelacion) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_RELACION_FIELD, codigoRelacion));
		return getVOCount(qual.toString(), TABLE_NAME);
	}

	private int countRowsConDocsFisicosByCodigoRelacion(String codigoRelacion) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
						codigoRelacion))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(SINDOCS_FISICOS_FIELD,
						Constants.FALSE_STRING));
		return getVOCount(qual.toString(), TABLE_NAME);
	}

	private int countRowsSinDocsFisicosByCodigoRelacion(String codigoRelacion) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
						codigoRelacion))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(SINDOCS_FISICOS_FIELD,
						Constants.TRUE_STRING));
		return getVOCount(qual.toString(), TABLE_NAME);
	}

	public boolean isOnlyDocumentosElectronicos(String codigoRelacion) {
		int docsFisicos = countRowsConDocsFisicosByCodigoRelacion(codigoRelacion);

		if (docsFisicos == 0) {
			int docsElectronicos = countRowsSinDocsFisicosByCodigoRelacion(codigoRelacion);
			if (docsElectronicos > 0)
				return true;
		}

		return false;
	}

	// public List fetchRowsByCodigoRelacion(String codigoRelacion) {
	// StringBuffer qual = new StringBuffer("WHERE ")
	// .append(DBUtils.generateEQTokenField(ID_RELACION_FIELD, codigoRelacion));
	// return getUnidadesDocumentales(qual.toString());
	// }

	public List fetchRowsByCodigoRelacionOrderByOrden(String codigoRelacion) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
						codigoRelacion)).append(
						DBUtils.generateOrderBy(ORDEN_FIELD));
		return getUnidadesDocumentales(qual.toString());
	}

	public List fetchRowsByCodigoRelacion(String codigoRelacion) {
		/*
		 * select asgtunidaddocre.* from
		 * asgtunidaddocre,asgtudocenui,asgtuinstalacionre where
		 * asgtuinstalacionre.id=asgtudocenui.iduinstalacionre AND
		 * asgtudocenui.idunidaddoc=asgtunidaddocre.id AND
		 * asgtuinstalacionre.iduiubicada IS NULL AND
		 * asgtunidaddocre.idrelentrega='09d7150538f5e000000000000000002e'
		 */
		StringBuffer qual = new StringBuffer(
				",asgtudocenui,asgtuinstalacionre ")
				.append("where asgtuinstalacionre.id=asgtudocenui.iduinstalacionre and asgtudocenui.idunidaddoc=asgtunidaddocre.id ")
				.append("and asgtuinstalacionre.iduiubicada IS NULL and ")
				.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
						codigoRelacion))
				.append(DBUtils.generateOrderBy(ORDEN_FIELD));
		return getVOS(qual.toString(), TABLE_NAME, TABLE_COLUMNS,
				UnidadDocumentalVO.class);
		// return getUnidadesDocumentales(qual.toString());
	}

	public List fetchRowsByCodigoRelacionOrderByOrden(String codigoRelacion,
			PageInfo pageInfo) throws TooManyResultsException {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
						codigoRelacion)).append(
						DBUtils.generateOrderBy(ORDEN_FIELD));
		return getUnidadesDocumentales(qual.toString(), pageInfo);
	}

	public List fetchRowsFisicasByCodigoRelacionOrderByOrden(
			String codigoRelacion) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
						codigoRelacion))
				.append(DBUtils.AND)
				.append(DBUtils.generateNEQTokenField(SINDOCS_FISICOS_FIELD,
						Constants.TRUE_STRING));
		return getUnidadesDocumentales(qual.toString());
	}



	public List fetchRowsElectronicasByCodigoRelacionOrderByOrden(
			String codigoRelacion) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
						codigoRelacion))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(SINDOCS_FISICOS_FIELD,
						Constants.TRUE_STRING));
		return getUnidadesDocumentales(qual.toString());
	}

	/**
	 * Obtiene la lista de unidades documentales incluidad en una determinada
	 * unidad de instalacion de la relacion de entrega
	 *
	 * @param conn
	 *            Conexion a la base de datos
	 * @param idRelacion
	 *            Identificador de la relacion de entrega
	 * @param idUnidadInstalacion
	 *            Identificador de unidad de instalacion
	 * @return Lista con las unidades documentales incluidas en la unidad de
	 *         instalacion especificada
	 * @throws Exception
	 */
	public List fetchRowsByUInstalacion(String idRelacion,
			String idUnidadInstalacion) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(ID_COLUMN_NAME);
		if (idUnidadInstalacion != null) {
			qual.append(" in (select ")
					.append(UdocEnUIDBEntityImpl.UDOC_COLUMN_NAME)
					.append(" from ")
					.append(UdocEnUIDBEntityImpl.TABLE_NAME)
					.append(DBUtils.WHERE)
					.append(DBUtils.generateEQTokenField(
							UdocEnUIDBEntityImpl.UINSTALACION_RELACION_FIELD,
							idUnidadInstalacion));
		} else {
			qual.append(" not in (select ")
					.append(UdocEnUIDBEntityImpl.UDOC_COLUMN_NAME)
					.append(" from ")
					.append(UdocEnUIDBEntityImpl.TABLE_NAME)
					.append(DBUtils.WHERE)
					.append(DBUtils.generateEQTokenField(
							UdocEnUIDBEntityImpl.ID_RELACION_FIELD, idRelacion));
		}
		return getUnidadesDocumentales(qual.toString());
	}

	public void updateRow(final UnidadDocumentalVO udoc) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_FIELD, udoc.getId()));
		updateVO(qual.toString(), TABLE_NAME, COLUMNS_TO_UPDATE, udoc);
	}

	public void updateOrden(String idUdoc, int orden) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_FIELD, idUdoc));
		Map colToUpdate = Collections.singletonMap(ORDEN_FIELD, new Integer(
				orden));
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	/**
	 * Actualiza en la base de datos los datos de una unidad documental
	 *
	 * @param udoc
	 *            Unidad documental a actualizar
	 * @param xmlInfo
	 *            Información a actualizar
	 */
	public void updateXmlInfo(final UnidadDocumentalVO udoc, String xmlInfo) {

		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_FIELD, udoc.getId()));
		Map colToUpdate = Collections.singletonMap(INFO_FIELD, xmlInfo);
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	public void setEstadoValidacion(String idRelacion, boolean validada) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_RELACION_FIELD, idRelacion));
		Map colToUpdate = Collections.singletonMap(VALIDADA_FIELD,
				validada ? Constants.TRUE_STRING : Constants.FALSE_STRING);
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	public void deleteXIdRelacion(final String idRelacion) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer(DBUtils.WHERE)
						.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
								idRelacion));
				DbDeleteFns.delete(conn, TABLE_NAME, qual.toString());
			}
		};
		command.execute();
	}

	/*
	 * comprobar q las carpetas (unidades doc con doc fisico) estan el cajas
	 */
	public boolean isAllUdocsConDocFisicosEnCaja(final String idRelacion) {
		final MutableBoolean allUdocsConDocsFisicos = new MutableBoolean(false);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer(DBUtils.WHERE)
						.append(DBUtils.generateEQTokenField(
								SINDOCS_FISICOS_FIELD, Constants.FALSE_STRING))
						.append(" AND ")
						.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
								idRelacion))
						.append(" AND ")
						.append(ID_COLUMN_NAME)
						.append(" IN (SELECT ")
						.append(UdocEnUIDBEntityImpl.UDOC_COLUMN_NAME)
						.append(" FROM ")
						.append(UdocEnUIDBEntityImpl.TABLE_NAME)
						.append(" where ")
						.append(DBUtils.generateJoinCondition(
								UdocEnUIDBEntityImpl.ID_RELACION_FIELD,
								ID_RELACION_FIELD)).append(")");
				int enCaja = DbSelectFns.selectCount(conn, TABLE_NAME,
						qual.toString());
				int enTotal = countRowsConDocsFisicosByCodigoRelacion(idRelacion);
				allUdocsConDocsFisicos.setValue(enCaja == enTotal);

				// allUdocsConDocsFisicos.setValue(
				// DbSelectFns.selectCount(conn, TABLE_NAME, qual.toString()) >
				// 0);
			}
		};
		command.execute();
		return allUdocsConDocsFisicos.getValue();
	}

	public boolean existUdocsConDocumentosFisicos(final String idRelacion) {
		final MutableBoolean udocsConDocsFisicos = new MutableBoolean(false);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer(DBUtils.WHERE)
						.append(DBUtils.generateEQTokenField(
								SINDOCS_FISICOS_FIELD, Constants.FALSE_STRING))
						.append(" AND ")
						.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
								idRelacion));
				udocsConDocsFisicos.setValue(!DbSelectFns.rowExists(conn,
						TABLE_NAME, qual.toString()));
			}
		};
		command.execute();
		return udocsConDocsFisicos.getValue();
	}

	public void updateNumPartesUdoc(String udocID, int numeroPartes) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_FIELD, udocID));
		Map colToUpdate = Collections.singletonMap(NUM_PARTES_FIELD,
				String.valueOf(numeroPartes));
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	/**
	 * Establece el asunto de una unidad documental
	 *
	 * @param udocID
	 *            Identificador de unidad documental
	 * @param titulo
	 *            asunto a establecer
	 */
	public void updateAsunto(String udocID, String titulo) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_FIELD, udocID));
		Map colToUpdate = Collections.singletonMap(ASUNTO_FIELD, titulo);
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	/**
	 * Permite establecer la marca de bloqueo de una unidad documental
	 *
	 * @param udocID
	 *            Identificador de unidad documental
	 * @param marca
	 *            valor de la marca a establecer
	 */
	public void updateMarcaBloqueo(String udocID, int marca) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_FIELD, udocID));
		Map colToUpdate = Collections.singletonMap(MARCAS_BLOQUEO_FIELD,
				String.valueOf(marca));
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	/**
	 * Establece el número de expediente de una unidad documental
	 *
	 * @param udocID
	 *            Identificador de unidad documental
	 * @param numExp
	 *            número de expediente a establecer
	 */
	public void updateNumeroExpediente(String udocID, String numExp) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_FIELD, udocID));
		Map colToUpdate = Collections.singletonMap(NUMERO_EXPEDIENTE_FIELD,
				numExp);
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	/**
	 * Incrementa el numero de partes en que se encuentra dividida una unidad
	 * documental en una determinada cantidad
	 *
	 * @param idUnidadDoc
	 *            Identificador de unidad documental
	 * @param inc
	 *            Cantidad en la que se quiere incrementar el numero de partes
	 */
	public void incrementNumPartesUdoc(String idUnidadDoc, int inc) {
		final StringBuffer query = new StringBuffer(" UPDATE ")
				.append(TABLE_NAME).append(" SET ")
				.append(NUM_PARTES_COLUMN_NAME).append(" = ")
				.append(NUM_PARTES_COLUMN_NAME).append(" + ").append(inc)
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_FIELD, idUnidadDoc));
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbInputStatement stmt = new DbInputStatement();
				stmt.create(conn, query.toString());
				stmt.execute();
			}
		};
		command.execute();

	}

	public List fetchRowsByNumExpediente(String numeroExpediente,
			String idRelacion) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(NUMERO_EXPEDIENTE_FIELD,
						numeroExpediente));
		if (idRelacion != null)
			qual.append(" and ")
					.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
							idRelacion));
		return getUnidadesDocumentales(qual.toString());
	}

	/**
	 * Cuenta el numero de unidades documentales de una relacion de entrega que
	 * cuyo estado de cotejo sea alguno de los solicitados. El conjunto de
	 * estados de cotejo puede ser nulo en cuyo caso se devuelve el numero total
	 * de unidades documentales de la relacion
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @param estadoCotejo
	 *            Conjunto de estados de cotejo
	 * @return numero de unidades documentales
	 */
	public int countUdocsConEstado(String idRelacion, int[] estadoCotejo) {
		String[] tables = { TABLE_NAME };
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_RELACION_FIELD, idRelacion));
		if (estadoCotejo != null) {
			qual.append(" AND ")
					.append(DBUtils.generateORTokens(
							UdocEnUIDBEntityImpl.ESTADO_COTEJO_FIELD,
							estadoCotejo))
					.append(" AND ")
					.append(DBUtils.generateJoinCondition(ID_FIELD,
							UdocEnUIDBEntityImpl.UDOC_FIELD));
			tables = new String[] { TABLE_NAME, UdocEnUIDBEntityImpl.TABLE_NAME };
		}
		String queryTables = ArrayUtils.join(tables, ",");
		return getVOCount(qual.toString(), queryTables);
	}

	/**
	 * Recupera las unidades documentales de una relación de entrega que todavía
	 * no han sido asignadas a ninguna caja
	 *
	 * @param idRelacion
	 *            Identificador de relación de entrega
	 * @return Lista de unidades documentales {@link UnidadDocumentalVO}
	 */
	public List getUdocsSinCaja(String idRelacion) {
		TableDef TABLA_UDOCS = new TableDef(TABLE_NAME, "udoc");
		AliasedColumnDef ALIASED_ID = new AliasedColumnDef(ID_FIELD,
				TABLA_UDOCS);
		AliasedColumnDef ALIASED_ID_RELACION = new AliasedColumnDef(
				ID_RELACION_FIELD, TABLA_UDOCS);
		AliasedColumnDef ALIASED_TIPO_TRANSFERENCIA = new AliasedColumnDef(
				TIPO_TRANSFERENCIA_FIELD, TABLA_UDOCS);
		AliasedColumnDef ALIASED_NUMERO_EXPEDIENTE = new AliasedColumnDef(
				NUMERO_EXPEDIENTE_FIELD, TABLA_UDOCS);
		AliasedColumnDef ALIASED_SISTEMA_PRODUCTOR = new AliasedColumnDef(
				SISTEMA_PRODUCTOR_FIELD, TABLA_UDOCS);
		AliasedColumnDef ALIASED_FECHA_INICIO = new AliasedColumnDef(
				FECHA_INICIO_FIELD, TABLA_UDOCS);
		AliasedColumnDef ALIASED_FECHA_FIN = new AliasedColumnDef(
				FECHA_FIN_FIELD, TABLA_UDOCS);
		AliasedColumnDef ALIASED_ASUNTO = new AliasedColumnDef(ASUNTO_FIELD,
				TABLA_UDOCS);
		AliasedColumnDef ALIASED_VALIDADA = new AliasedColumnDef(
				VALIDADA_FIELD, TABLA_UDOCS);
		AliasedColumnDef ALIASED_SINDOCS_FISICOS = new AliasedColumnDef(
				SINDOCS_FISICOS_FIELD, TABLA_UDOCS);
		AliasedColumnDef ALIASED_INFO = new AliasedColumnDef(INFO_FIELD,
				TABLA_UDOCS);
		AliasedColumnDef ALIASED_NUM_PARTES = new AliasedColumnDef(
				NUM_PARTES_FIELD, TABLA_UDOCS);
		AliasedColumnDef ALIASED_ORDEN = new AliasedColumnDef(ORDEN_FIELD,
				TABLA_UDOCS);
		AliasedColumnDef ALIASED_SIN_DOCS_FISICOS = new AliasedColumnDef(
				SINDOCS_FISICOS_FIELD, TABLA_UDOCS);
		DbColumnDef[] ALIASED_TABLE_COLUMNS = { ALIASED_ID,
				ALIASED_ID_RELACION, ALIASED_TIPO_TRANSFERENCIA,
				ALIASED_NUMERO_EXPEDIENTE, ALIASED_SISTEMA_PRODUCTOR,
				ALIASED_FECHA_INICIO, ALIASED_FECHA_FIN, ALIASED_ASUNTO,
				ALIASED_SINDOCS_FISICOS, ALIASED_VALIDADA, ALIASED_INFO,
				ALIASED_NUM_PARTES, ALIASED_ORDEN };

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ALIASED_ID_RELACION,
						idRelacion))
				.append(" AND ")
				.append(DBUtils.generateNEQTokenField(ALIASED_SIN_DOCS_FISICOS,
						Constants.TRUE_STRING))
				.append(" AND NOT EXISTS ")
				.append("(SELECT * FROM ")
				.append(UdocEnUIDBEntityImpl.TABLE_NAME)
				.append(" WHERE ")
				.append(DBUtils.generateJoinCondition(
						UdocEnUIDBEntityImpl.UDOC_FIELD, ALIASED_ID))
				.append(")").append(DBUtils.generateOrderBy(ALIASED_ORDEN));
		return getVOS(qual.toString(), TABLA_UDOCS.getDeclaration(),
				ALIASED_TABLE_COLUMNS, UnidadDocumentalVO.class);
	}

	private Map getMappingOrderCriterion() {
		Map criteriosOrdenacion = new HashMap();
		criteriosOrdenacion.put(EXPEDIENTE_ORDER_CRITERION,
				NUMERO_EXPEDIENTE_FIELD);
		criteriosOrdenacion.put(ASUNTO_ORDER_CRITERION, ASUNTO_FIELD);
		return criteriosOrdenacion;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * transferencias.db.IUDocRelacionDBEntity#fetchRowsByIds(java.lang.String
	 * [])
	 */
	public List fetchRowsByIds(String[] idsUDocs) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateORTokens(NUMERO_EXPEDIENTE_FIELD, idsUDocs));
		return getUnidadesDocumentales(qual.toString());
	}

	/**
	 * Recupera las unidades documentales de una relación de entrega
	 *
	 * @param idRelacion
	 *            Identificador de relación de entrega
	 * @return Lista de unidades documentales {@link UnidadDocumentalVO}
	 */
	public List getUdocsByIdRelacion(String idRelacion) {

		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_RELACION_FIELD, idRelacion));

		return getUnidadesDocumentales(qual.toString());
	}

	/**
	 * Actualiza el orden de las unidades documentales pertenecientes a la
	 * relacion de entrega
	 *
	 * @param idRelacionEntrega
	 * @param orden
	 */
	public void incrementOrdenUdoc(String idRelacionEntrega, int orden,
			int incremento) {
		final StringBuffer query = new StringBuffer(" UPDATE ")
				.append(TABLE_NAME)
				.append(" SET ")
				.append(ORDEN_COLUMN_NAME)
				.append(" = ")
				.append(ORDEN_COLUMN_NAME)
				.append(" + ")
				.append(incremento)
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
						idRelacionEntrega)).append(" AND ")
				.append(DBUtils.generateGTEQTokenField(ORDEN_FIELD, orden));
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbInputStatement stmt = new DbInputStatement();
				stmt.create(conn, query.toString());
				stmt.execute();
			}
		};
		command.execute();
	}

	public void updateFechaExtIni(String idUdocRE, Date fechaExtIni) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_FIELD, idUdocRE));

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(FECHA_INICIO_FIELD, fechaExtIni);

		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	public void updateFechaExtFin(String idUdocRE, Date fechaExtFin) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_FIELD, idUdocRE));

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(FECHA_FIN_FIELD, fechaExtFin);

		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * transferencias.db.IUDocRelacionDBEntity#getUdocsByInfoDescriptor(java
	 * .lang.String[])
	 */
	public List getUdocsByInfoDescriptor(String[] idsDescriptores) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE);

		for (int i = 0; i < idsDescriptores.length; i++) {
			if (i > 0) {
				qual.append(DBUtils.OR);
			}
			qual.append(DBUtils.generateLikeTokenField(INFO_FIELD,
					idsDescriptores[i], false));
		}

		return getUnidadesDocumentales(qual.toString());

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * transferencias.db.IUDocRelacionDBEntity#getNextOrdenUdocInRelacionEntrega
	 * (java.lang.String, int)
	 */
	public int getNextOrdenUdocInRelacionEntrega(String idRelacion, int orden) {

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
						idRelacion)).append(DBUtils.AND)
				.append(DBUtils.generateGTTokenField(ORDEN_FIELD, orden));

		return getMin(qual.toString(), TABLE_NAME, ORDEN_FIELD);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * transferencias.db.IUDocRelacionDBEntity#getPrevOrdenUdocInRelacionEntrega
	 * (java.lang.String, int)
	 */
	public int getPrevOrdenUdocInRelacionEntrega(String idRelacion, int orden) {

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
						idRelacion)).append(DBUtils.AND)
				.append(DBUtils.generateLTTokenField(ORDEN_FIELD, orden));

		return getMax(qual.toString(), TABLE_NAME, ORDEN_FIELD);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * transferencias.db.IUDocRelacionDBEntity#getUdocInRelacionEntregaByOrden
	 * (java.lang.String, int)
	 */
	public UnidadDocumentalVO getUdocInRelacionEntregaByOrden(
			String idRelacion, int orden) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
						idRelacion)).append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(ORDEN_FIELD, orden));
		return getUnidadDocumental(qual.toString());
	}

	public DbColumnDef getCustomizedField(DbColumnDef column, String alias) {
		return new DbColumnDef(column.getBindPropertyVO(), alias,
				column.getName(), column.getDataType(), column.getMaxLen(),
				column.isNullable());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * transferencias.db.IUDocRelacionDBEntity#getRangosUDocRE(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public List getRangosUDocRE(String idUDoc, String idCampoInicial,
			String idCampoFinal) {

		StringBuffer sqlFrom = new StringBuffer();
		String aliasTextoCorto1 = "TC1", aliasTextoCorto2 = "TC2";
		TableDef tablaTextoCorto1 = new TableDef(
				TextoCortoUDocREDBEntityImpl.TABLE_NAME, aliasTextoCorto1);
		TableDef tablaTextoCorto2 = new TableDef(
				TextoCortoUDocREDBEntityImpl.TABLE_NAME, aliasTextoCorto2);

		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(tablaTextoCorto1, getCustomizedField(
						TextoCortoUDocREDBEntityImpl.CAMPO_ID_ELEMENTO,
						aliasTextoCorto1)), new DbColumnDef(tablaTextoCorto2,
						getCustomizedField(
								TextoCortoUDocREDBEntityImpl.CAMPO_ID_ELEMENTO,
								aliasTextoCorto2))) };

		sqlFrom.append(DBUtils.generateInnerJoinCondition(tablaTextoCorto1,
				joins));

		sqlFrom.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						getCustomizedField(
								TextoCortoUDocREDBEntityImpl.CAMPO_ID_ELEMENTO,
								aliasTextoCorto1), idUDoc))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						getCustomizedField(
								TextoCortoUDocREDBEntityImpl.CAMPO_ORDEN,
								aliasTextoCorto1),
						getCustomizedField(
								TextoCortoUDocREDBEntityImpl.CAMPO_ORDEN,
								aliasTextoCorto2)))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						getCustomizedField(
								TextoCortoUDocREDBEntityImpl.CAMPO_ID_CAMPO,
								aliasTextoCorto1), idCampoInicial))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						getCustomizedField(
								TextoCortoUDocREDBEntityImpl.CAMPO_ID_CAMPO,
								aliasTextoCorto2), idCampoFinal));

		DbColumnDef[] columns = new DbColumnDef[] {
				new DbColumnDef("desde", getCustomizedField(
						TextoCortoUDocREDBEntityImpl.CAMPO_VALOR,
						aliasTextoCorto1)),
				new DbColumnDef("hasta", getCustomizedField(
						TextoCortoUDocREDBEntityImpl.CAMPO_VALOR,
						aliasTextoCorto2)), };

		sqlFrom.append(" ORDER BY ").append(
				getCustomizedField(TextoCortoUDocREDBEntityImpl.CAMPO_ORDEN,
						aliasTextoCorto1).getQualifiedName());

		return getVOS(Constants.STRING_EMPTY, sqlFrom.toString(), columns,
				transferencias.vos.RangoVO.class);
	}

	public UnidadDocumentalVO getUnidadDocumental(
			UnidadDocumentalVO filtroUdocVO) {

		StringBuffer qual = new StringBuffer();

		qual.insert(0, DBUtils.WHERE);

		if (StringUtils.isNotEmpty(filtroUdocVO.getCodSistProductor())) {
			qual.append(DBUtils.generateEQTokenField(SISTEMA_PRODUCTOR_FIELD,
					filtroUdocVO.getCodSistProductor()));
		} else {
			qual.append(DBUtils.generateIsNullCondition(getConnection(),
					SISTEMA_PRODUCTOR_FIELD));
		}

		// NUMERO DE EXPEDIENTE
		if (StringUtils.isNotEmpty(filtroUdocVO.getNumeroExpediente())) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(NUMERO_EXPEDIENTE_FIELD,
							filtroUdocVO.getNumeroExpediente()));
		}

		// IDRELENTREGA
		if (StringUtils.isNotEmpty(filtroUdocVO.getIdRelEntrega())) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(ID_RELACION_FIELD,
							filtroUdocVO.getIdRelEntrega()));
		}

		// ASUNTO
		if (StringUtils.isNotEmpty(filtroUdocVO.getAsunto())) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(ASUNTO_FIELD,
							filtroUdocVO.getAsunto()));
		}

		// FECHAEXTINI
		if (filtroUdocVO.getFechaInicio() != null) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(getConnection(),
							FECHA_INICIO_FIELD, filtroUdocVO.getFechaInicio()));
		}

		// FECHAEXTFIN
		if (filtroUdocVO.getFechaFin() != null) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(getConnection(),
							FECHA_FIN_FIELD, filtroUdocVO.getFechaFin()));
		}

		return getUnidadDocumental(qual.toString());

	}

	public UnidadDocumentalVO getUnidadDocumentalByRelacionAndId(
			String idRelacion, String id) {

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
			.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
							idRelacion))
			.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(ID_FIELD,
							id));

		return getUnidadDocumental(qual.toString());
	}

	public int getMaxOrden(String idRelacion) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_RELACION_FIELD, idRelacion));

		return getMax(qual.toString(), TABLE_NAME, ORDEN_FIELD);
	}

	public List fetchRowsByIdRelacionOrderByOrdenNoValidada(
			String idRelacion) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
						idRelacion))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(VALIDADA_FIELD, Constants.FALSE_STRING))
						;
		return getUnidadesDocumentales(qual.toString());
	}
}