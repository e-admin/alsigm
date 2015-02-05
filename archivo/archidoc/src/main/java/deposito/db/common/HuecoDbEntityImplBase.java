/*
 * Created on 17-ene-2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package deposito.db.common;

import gcontrol.db.ArchivoDbEntityImpl;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInputStatement;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbTableDef;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;
import ieci.core.exception.IeciTdException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import transferencias.TransferenciasConstants;
import transferencias.db.oracle.RelacionEntregaDBEntityImpl;

import common.CodigoTransferenciaUtils;
import common.Constants;
import common.db.ConstraintType;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.SigiaDbInputRecord;
import common.db.TableDef;
import common.exceptions.ArchivoModelException;
import common.exceptions.DBException;
import common.exceptions.TooManyResultsException;
import common.lang.MutableBoolean;
import common.util.ArrayUtils;
import common.util.IntervalOption;
import common.util.IntervalOptions;
import common.util.IntervalRangeOption;
import common.util.IntervalSimpleOption;
import common.vos.ConsultaConnectBy;

import deposito.DepositoConstants;
import deposito.db.DepositoDBEntityImpl;
import deposito.db.ElementoAsignableDBEntity;
import deposito.db.ElementoNoAsignableDBEntity;
import deposito.db.IHuecoDBEntity;
import deposito.db.UInstalacionDepositoDBEntity;
import deposito.exceptions.NoPermitidaRenumeracionHuecoAlfanumericaException;
import deposito.exceptions.NoPermitidaRenumeracionHuecoCreadaException;
import deposito.exceptions.NoPermitidaRenumeracionHuecoMaximoSuperadoException;
import deposito.exceptions.NoPermitidaRenumeracionHuecoOcupadaException;
import deposito.model.DepositoException;
import deposito.vos.HuecoID;
import deposito.vos.HuecoVO;

public abstract class HuecoDbEntityImplBase extends DBEntity implements
		IHuecoDBEntity {
	/** Logger de la clase */
	static Logger logger = Logger.getLogger(HuecoDbEntityImplBase.class);

	public static final String TABLE_NAME = "ASGDHUECO";

	public static final String IDELEMPADRE_COLUMN_NAME = "idelemapadre";
	public static final String IDDEPOSITO_COLUMN_NAME = "iddeposito";
	public static final String NUMORDEN_COLUMN_NAME = "numorden";
	public static final String ESTADO_COLUMN_NAME = "estado";
	public static final String FECHAESTADO_COLUMN_NAME = "fechaestado";
	public static final String FORMATO_COLUMN_NAME = "idformato";
	public static final String IDUINSTALACION_COLUMN_NAME = "iduinstalacion";
	public static final String IDRELACION_COLUMN_NAME = "idrelentrega";
	public static final String PATH_COLUMN_NAME = "path";
	public static final String ORDEN_EN_RELACION_COLUMN_NAME = "ordenenrelacion";
	public static final String TIPO_ORD_COLUMN_NAME = "tipoord";
	public static final String CODORDEN_COLUMN_NAME = "codorden";
	public static final String NUMERACION_COLUMN_NAME = "numeracion";
	public static final String MARCAS_COLUMN_NAME = "marcas";
	public static final String IDUIREEACR_COLUMN_NAME = "iduireeacr";

	public static final DbColumnDef CAMPO_IDELEMPADRE = new DbColumnDef(null,
			TABLE_NAME, IDELEMPADRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef CAMPO_IDDEPOSITO = new DbColumnDef(null,
			TABLE_NAME, IDDEPOSITO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef CAMPO_NUMORDEN = new DbColumnDef(null,
			TABLE_NAME, NUMORDEN_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_ESTADO = new DbColumnDef(null,
			TABLE_NAME, ESTADO_COLUMN_NAME, DbDataType.SHORT_TEXT, 1, true);

	public static final DbColumnDef CAMPO_FECHAESTADO = new DbColumnDef(null,
			TABLE_NAME, FECHAESTADO_COLUMN_NAME, DbDataType.DATE_TIME, true);

	public static final DbColumnDef CAMPO_FORMATO = new DbColumnDef(null,
			TABLE_NAME, FORMATO_COLUMN_NAME, DbDataType.SHORT_TEXT, true);

	public static final DbColumnDef CAMPO_IDUINSTALACION = new DbColumnDef(
			null, TABLE_NAME, IDUINSTALACION_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, true);

	public static final DbColumnDef CAMPO_IDRELACION = new DbColumnDef(null,
			TABLE_NAME, IDRELACION_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, true);

	public static final DbColumnDef CAMPO_PATH = new DbColumnDef(null,
			TABLE_NAME, PATH_COLUMN_NAME, DbDataType.SHORT_TEXT, 512, true);

	public static final DbColumnDef CAMPO_ORDEN_EN_RELACION = new DbColumnDef(
			null, TABLE_NAME, ORDEN_EN_RELACION_COLUMN_NAME,
			DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_TIPO_ORD = new DbColumnDef(null,
			TABLE_NAME, TIPO_ORD_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_CODORDEN = new DbColumnDef(null,
			TABLE_NAME, CODORDEN_COLUMN_NAME, DbDataType.SHORT_TEXT, 50, true);

	public static final DbColumnDef CAMPO_NUMERACION = new DbColumnDef(null,
			TABLE_NAME, NUMERACION_COLUMN_NAME, DbDataType.SHORT_TEXT, 16, true);

	public static final DbColumnDef CAMPO_MARCAS = new DbColumnDef(null,
			TABLE_NAME, MARCAS_COLUMN_NAME, DbDataType.LONG_INTEGER, true);

	/**
	 * Columna Identificador de la unidad de instalación con reencajado asociada
	 * al hueco.
	 */
	public static final DbColumnDef CAMPO_IDUIREEACR = new DbColumnDef(null,
			TABLE_NAME, IDUIREEACR_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, true);

	public static final DbColumnDef[] TABLE_COLUMNS = { CAMPO_IDELEMPADRE,
			CAMPO_IDDEPOSITO, CAMPO_NUMORDEN, CAMPO_ESTADO, CAMPO_FECHAESTADO,
			CAMPO_FORMATO, CAMPO_IDUINSTALACION, CAMPO_IDRELACION, CAMPO_PATH,
			CAMPO_ORDEN_EN_RELACION, CAMPO_CODORDEN, CAMPO_TIPO_ORD,
			CAMPO_NUMERACION, CAMPO_MARCAS, CAMPO_IDUIREEACR };

	public static String ALL_COLUMN_NAMES = DbUtil
			.getNameColumnNames(new DbColumnDef[] { CAMPO_IDELEMPADRE,
					CAMPO_IDDEPOSITO, CAMPO_NUMORDEN, CAMPO_ESTADO,
					CAMPO_IDUINSTALACION, CAMPO_IDRELACION, CAMPO_FECHAESTADO,
					CAMPO_PATH, CAMPO_FORMATO, CAMPO_ORDEN_EN_RELACION,
					CAMPO_CODORDEN, CAMPO_TIPO_ORD, CAMPO_NUMERACION,
					CAMPO_MARCAS, CAMPO_IDUIREEACR });

	private static String ROW_QUAL(HuecoID idHueco) {
		String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDELEMPADRE,
						idHueco.getIdpadre()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_NUMORDEN,
						Integer.toString(idHueco.getNumorden()))).toString();

		return qual;
	};

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public HuecoDbEntityImplBase(DbDataSource dataSource) {
		super(dataSource);
	}

	public HuecoDbEntityImplBase(DbConnection conn) {
		super(conn);
	}

	private static final TableDef tablaHuecoDef = new TableDef(TABLE_NAME);
	private static final TableDef tablaArchivo = new TableDef(
			ArchivoDbEntityImpl.TABLE_NAME);

	private static final JoinDefinition[] joins = new JoinDefinition[] {
			new JoinDefinition(new DbColumnDef(tablaHuecoDef,
					CAMPO_IDUINSTALACION), new DbColumnDef(new TableDef(
					UInstalacionDepositoDBEntity.TABLE_NAME),
					UInstalacionDepositoDBEntity.ID_FIELD)),

			new JoinDefinition(
					new DbColumnDef(tablaHuecoDef, CAMPO_IDRELACION),
					new DbColumnDef(new TableDef(
							RelacionEntregaDBEntityImpl.TABLE_NAME),
							RelacionEntregaDBEntityImpl.CAMPO_ID)),

			new JoinDefinition(new DbColumnDef(new TableDef(
					RelacionEntregaDBEntityImpl.TABLE_NAME),
					RelacionEntregaDBEntityImpl.CAMPO_IDARCHIVORECEPTOR),
					new DbColumnDef(tablaArchivo, ArchivoDbEntityImpl.ID_FIELD)) };

	private static final String TABLE_SET = DBUtils
			.generateLeftOuterJoinCondition(tablaHuecoDef, joins);

	public boolean checkHuecosByDeposito(final String idDeposito,
			final String[] estados) {
		final MutableBoolean hayHuecosEnEstados = new MutableBoolean(false);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				final String qual = new StringBuffer()
						.append("WHERE ")
						.append(DBUtils.generateEQTokenField(CAMPO_IDDEPOSITO,
								idDeposito))
						.append(" AND ")
						.append(DBUtils.generateInTokenField(CAMPO_ESTADO,
								estados)).toString();
				hayHuecosEnEstados.setValue(DbSelectFns.rowExists(
						getConnection(), TABLE_NAME, qual));
			}
		};
		command.execute();
		return hayHuecosEnEstados.getValue();

	}

	public abstract boolean checkHuecosByElementoNoAsignable(
			final String idElementoNoAsignable, final String[] estados);

	public boolean checkHuecosByElementoAsignable(final String idAsignable,
			final String[] estados) {
		final MutableBoolean hayHuecosEnEstados = new MutableBoolean(false);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				final String qual = new StringBuffer("WHERE ")
						.append(DBUtils.generateEQTokenField(CAMPO_IDELEMPADRE,
								idAsignable))
						.append(" AND ")
						.append(DBUtils.generateInTokenField(CAMPO_ESTADO,
								estados)).toString();
				hayHuecosEnEstados.setValue(DbSelectFns.rowExists(conn,
						TABLE_NAME, qual));
			}
		};
		command.execute();
		return hayHuecosEnEstados.getValue();
	}

	public HuecoVO getHueco(HuecoID idHueco) {
		String qual = ROW_QUAL(idHueco);
		return (HuecoVO) getVO(qual, TABLE_NAME, TABLE_COLUMNS, HuecoVO.class);
	}

	public HuecoVO getHuecoUInstalacion(String unidadInstalacion) {
		StringBuffer qual = new StringBuffer("where ").append(DBUtils
				.generateEQTokenField(CAMPO_IDUINSTALACION, unidadInstalacion));
		return (HuecoVO) getVO(qual.toString(), TABLE_NAME, TABLE_COLUMNS,
				HuecoVO.class);
	}

	public List getHuecosEnElemento(String idElementoAsignable) {
		StringBuffer qual = new StringBuffer("where ").append(DBUtils
				.generateEQTokenField(CAMPO_IDELEMPADRE, idElementoAsignable));

		return getHuecos2(qual.toString(),
				DBUtils.generateOrderBy(CAMPO_NUMORDEN));
	}

	private List getHuecos2(String qual, String orderBy) {
		StringBuffer whereClause = new StringBuffer(qual.toString());
		whereClause.append(orderBy);

		DbColumnDef columna1 = new DbColumnDef("ano",
				RelacionEntregaDBEntityImpl.TABLE_NAME,
				RelacionEntregaDBEntityImpl.CAMPO_ANO);
		DbColumnDef columna2 = new DbColumnDef("orden",
				RelacionEntregaDBEntityImpl.TABLE_NAME,
				RelacionEntregaDBEntityImpl.CAMPO_ORDEN);
		DbColumnDef columna3 = new DbColumnDef("signaturaUI",
				UInstalacionDepositoDBEntity.TABLE_NAME,
				UInstalacionDepositoDBEntity.SIGNATURAUI_FIELD);
		DbColumnDef columna4 = new DbColumnDef("codArchivo",
				ArchivoDbEntityImpl.TABLE_NAME,
				ArchivoDbEntityImpl.CODIGO_FIELD);

		DbColumnDef[] areaColumn = new DbColumnDef[4];
		areaColumn[0] = columna1;
		areaColumn[1] = columna2;
		areaColumn[2] = columna3;
		areaColumn[3] = columna4;

		DbColumnDef[] cols = (DbColumnDef[]) ArrayUtils.concat(areaColumn,
				TABLE_COLUMNS);

		List list = getDistinctVOS(whereClause.toString(), TABLE_SET, cols,
				HuecoVO.class);

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				HuecoVO huecoVO = (HuecoVO) list.get(i);
				huecoVO.setRentrega(CodigoTransferenciaUtils
						.getCodigoTransferencia(huecoVO.getAno(),
								huecoVO.getCodArchivo(), huecoVO.getOrden(),
								TransferenciasConstants.FORMAT_ORDEN));
			}
		}
		return list;
	}

	public List getHuecosEnElemento2(String idElementoAsignable) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_IDELEMPADRE, idElementoAsignable));

		return getHuecos2(qual.toString(),
				DBUtils.generateOrderBy(CAMPO_NUMORDEN));
	}

	/**
	 * Devuelve la lista de huecos libres u ocupados pero no bloqueados del
	 * elementoAsignable
	 * 
	 * @param String
	 *            idElementoAsignable, id del elemento asignable al que
	 *            pertenecen los huecos devueltos
	 * @return Lista de huecos
	 */
	public List getHuecosNoBloqueados(String idElementoAsignable) {
		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(CAMPO_IDELEMPADRE,
						idElementoAsignable));

		return getHuecos2(qual.toString(),
				DBUtils.generateOrderBy(CAMPO_NUMORDEN));
	}

	public List getHuecosEnElementoXEstado(String idElementoAsignable,
			String[] estados) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDELEMPADRE,
						idElementoAsignable)).append(DBUtils.AND)
				.append(DBUtils.generateORTokens(CAMPO_ESTADO, estados));

		return getHuecos2(qual.toString(),
				DBUtils.generateOrderBy(CAMPO_NUMORDEN));
	}

	/**
	 * Cuenta el número de una ubicación del fondo físico de un determinado
	 * formato y que se encuentran en los estados indicados
	 * 
	 * @param idFormato
	 *            Formato de hueco. Puede ser nulo
	 * @param idDeposito
	 *            Identificador de ubicación
	 * @param estados
	 *            Conjunto de estados de hueco. Puede ser nulo
	 * @return Número de huecos que verifican los criterios
	 */
	public int countHuecosEnUbicacion(String idFormato, String idDeposito,
			String[] estados) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_IDDEPOSITO, idDeposito));
		if (idFormato != null)
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(CAMPO_FORMATO, idFormato));
		if (estados != null)
			qual.append(DBUtils.AND).append(
					DBUtils.generateORTokens(CAMPO_ESTADO, estados));
		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/**
	 * Recupera los huecos de una ubicación del fondo físico de un determinado
	 * formato y que se encuentran en los estados indicados
	 * 
	 * @param idFormato
	 *            Formato de hueco. Puede ser nulo
	 * @param idDeposito
	 *            Identificador de ubicación
	 * @param estados
	 *            Conjunto de estados de hueco. Puede ser nulo
	 * @return Lista de huecos que verifican los criterios {@link HuecoVO}
	 */
	public List getHuecosEnUbicacion(String idFormato, String idDeposito,
			String[] estados) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_IDDEPOSITO, idDeposito));
		if (idFormato != null)
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(CAMPO_FORMATO, idFormato));
		if (estados != null)
			qual.append(DBUtils.AND).append(
					DBUtils.generateORTokens(CAMPO_ESTADO, estados));
		return getVOS(qual.toString(), TABLE_NAME, TABLE_COLUMNS, HuecoVO.class);
	}

	public List getHuecosXRelacionEntregaYEstados(String idRelacionEntrega,
			String[] estados) {
		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDRELACION,
						idRelacionEntrega)).append(DBUtils.AND)
				.append(DBUtils.generateORTokens(CAMPO_ESTADO, estados))
				.append(DBUtils.generateOrderBy(CAMPO_ORDEN_EN_RELACION))
				.toString();

		return getVOS(qual, TABLE_NAME, TABLE_COLUMNS, HuecoVO.class);
	}

	public String getIdAsignablePadreXRelacionEntrega(String idRelacionEntrega) {
		String ret = null;
		String qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(CAMPO_IDRELACION,
						idRelacionEntrega)).toString();

		Object huecoObj = getVO(qual, TABLE_NAME,
				new DbColumnDef[] { CAMPO_IDELEMPADRE }, HuecoVO.class);
		if (huecoObj != null) {
			HuecoVO hueco = (HuecoVO) huecoObj;
			ret = hueco.getIdElemAPadre();
		}
		return ret;
	}

	private static String qualByIds(HuecoID[] ids) {
		String sentences[] = new String[ids.length];
		StringBuffer tmpStringBuffer = new StringBuffer();
		for (int i = 0; i < ids.length; i++) {
			tmpStringBuffer = tmpStringBuffer.delete(0,
					tmpStringBuffer.length());
			sentences[i] = tmpStringBuffer
					.append(DBUtils.generateEQTokenField(CAMPO_IDELEMPADRE,
							ids[i].getIdpadre()))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(CAMPO_NUMORDEN,
							Integer.toString(ids[i].getNumorden()))).toString();
		}

		return new StringBuffer().append(DBUtils.generateORTokens(sentences))
				.toString();
	}

	public List getHuecosXId(HuecoID[] ids) {
		String qual = new StringBuffer(DBUtils.WHERE).append(qualByIds(ids))
				.toString();
		return getVOS(qual, TABLE_NAME, TABLE_COLUMNS, HuecoVO.class);
	}

	public List getHuecosYSignaturaXId(HuecoID[] ids) {
		// cruzar con la tabla de unidades de instalacion (LEFT OUTER JOIN)
		// quedarse con todos los campos y ademas la signatura
		DbColumnDef[] COLS = new DbColumnDef[] { CAMPO_IDELEMPADRE,
				CAMPO_IDDEPOSITO, CAMPO_NUMORDEN, CAMPO_ESTADO,
				CAMPO_FECHAESTADO, CAMPO_FORMATO, CAMPO_IDUINSTALACION,
				CAMPO_IDRELACION, CAMPO_PATH, CAMPO_ORDEN_EN_RELACION,
				CAMPO_CODORDEN, CAMPO_TIPO_ORD,
				UInstalacionDepositoDBEntity.SIGNATURAUI_FIELD };
		String from = TABLE_NAME
				+ " LEFT OUTER JOIN "
				+ UInstalacionDepositoDBEntity.TABLE_NAME
				+ " ON "
				+ DBUtils.generateJoinCondition(CAMPO_IDUINSTALACION,
						UInstalacionDepositoDBEntity.ID_FIELD);

		String qual = new StringBuffer(DBUtils.WHERE).append(qualByIds(ids))
				.append(DBUtils.generateOrderBy(CAMPO_NUMORDEN)).toString();
		return getVOS(qual, from, COLS, HuecoVO.class);
	}

	public void insertHueco(final HuecoVO huecoVO) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						TABLE_COLUMNS, huecoVO);
				DbInsertFns.insert(conn, TABLE_NAME,
						DbUtil.getColumnNames(TABLE_COLUMNS), inputRecord);
			}
		};
		command.execute();
	}

	public void deleteHuecos(final String idAsignablePadre) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_IDELEMPADRE, idAsignablePadre));
		deleteVO(qual.toString(), TABLE_NAME);
	}

	public void deleteHuecos(String[] idsPadresAsignables) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateInTokenField(CAMPO_IDELEMPADRE, idsPadresAsignables));
		deleteVO(qual.toString(), TABLE_NAME);
	}

	public int updateEstadoHueco(final HuecoID idHueco,
			final String estadoHueco, final String estadoHuecoEsperado) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDELEMPADRE,
						idHueco.getIdpadre()))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_NUMORDEN,
						Integer.toString(idHueco.getNumorden())));

		if (estadoHuecoEsperado != null) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(CAMPO_ESTADO,
							estadoHuecoEsperado));
		}

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_ESTADO, estadoHueco);
		colsToUpdate.put(CAMPO_FECHAESTADO, new Date());
		return updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	/**
	 * Establece el estado del hueco a libre ademas de modificar la fecha de
	 * estado y borrar la relacion de entrega de la que viene y la uinstalacion
	 * 
	 * @param conn
	 * @param idsHuecos
	 * @throws Exception
	 */
	public void setEstadoLibre(final HuecoID[] idsHuecos) {
		String qual = new StringBuffer(DBUtils.WHERE).append(
				qualByIds(idsHuecos)).toString();

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_ESTADO, HuecoVO.LIBRE_STATE);
		colsToUpdate.put(CAMPO_FECHAESTADO, new Date());
		colsToUpdate.put(CAMPO_IDUINSTALACION, null);
		colsToUpdate.put(CAMPO_IDRELACION, null);
		updateFields(qual, colsToUpdate, TABLE_NAME);
	}

	public void liberarHuecosReservados(String idRelacionEntrega) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_IDRELACION, idRelacionEntrega));

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_ESTADO, HuecoVO.LIBRE_STATE);
		colsToUpdate.put(CAMPO_FECHAESTADO, new Date());
		colsToUpdate.put(CAMPO_IDRELACION, null);
		colsToUpdate.put(CAMPO_IDUIREEACR, null);
		colsToUpdate.put(CAMPO_IDUINSTALACION, null);
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	public int updateEstadoHuecoIdRelacionEntrega(HuecoID idHueco,
			String estadoHueco, String estadoHuecoEsperado,
			String idRelacionEntrega, Integer ordenenrelacion) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDELEMPADRE,
						idHueco.getIdpadre()))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_NUMORDEN,
						Integer.toString(idHueco.getNumorden())));

		if (estadoHuecoEsperado != null) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(CAMPO_ESTADO,
							estadoHuecoEsperado));
		}

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_ESTADO, estadoHueco);
		colsToUpdate.put(CAMPO_FECHAESTADO, new Date());
		colsToUpdate.put(CAMPO_IDRELACION, idRelacionEntrega);
		colsToUpdate.put(CAMPO_ORDEN_EN_RELACION, ordenenrelacion.toString());
		return updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	public int updateEstadoHuecoIdRelacionEntregaUnidadInstalacion(
			HuecoID idHueco, String estadoHueco, String estadoHuecoEsperado,
			String idRelacionEntrega, String idUnidadInstalacion,
			Integer ordenEnRelacion, boolean isConReencajado) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDELEMPADRE,
						idHueco.getIdpadre()))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_NUMORDEN,
						Integer.toString(idHueco.getNumorden())));

		if (estadoHuecoEsperado != null) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(CAMPO_ESTADO,
							estadoHuecoEsperado));
		}

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_ESTADO, estadoHueco);
		colsToUpdate.put(CAMPO_FECHAESTADO, new Date());
		if (isConReencajado) {
			colsToUpdate.put(CAMPO_IDUIREEACR, idUnidadInstalacion);
		} else {
			colsToUpdate.put(CAMPO_IDUINSTALACION, idUnidadInstalacion);
		}
		colsToUpdate.put(CAMPO_IDRELACION, idRelacionEntrega);

		if (ordenEnRelacion != null) {
			colsToUpdate.put(CAMPO_ORDEN_EN_RELACION,
					ordenEnRelacion.toString());
		}
		return updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	/**
	 * Realiza la liberacion de una unidad de instalacion poniendo su estado a
	 * libre 'L' y a nulo el campo IDUINSTALACION
	 * 
	 * @param conn
	 *            Conexion a la base de datos
	 * @param idUnidadInstalacion
	 *            Identificador de la unidad a liberar
	 * @throws Exception
	 *             Si se produce un error durante el proceso
	 */
	public void liberaUnidadInstalacion(String idUnidadInstalacion) {
		String qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(CAMPO_IDUINSTALACION,
						idUnidadInstalacion)).toString();

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_ESTADO, HuecoVO.LIBRE_STATE);
		colsToUpdate.put(CAMPO_FECHAESTADO, new Date());
		colsToUpdate.put(CAMPO_IDUINSTALACION, null);
		updateFields(qual, colsToUpdate, TABLE_NAME);
	}

	public void updatePathHuecos(final String idAsignablePadre,
			final String parentPath) {

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				String concatSymbol;
				try {
					concatSymbol = DbUtil
							.getNativeConcatSyntax(getConnection());
				} catch (IeciTdException e) {
					throw new ArchivoModelException(e.getMessage());
				}

				DbInputStatement stmt = null;
				stmt = new DbInputStatement();
				String sql = new StringBuffer()
						.append("update ")
						.append(TABLE_NAME)
						.append(" set ")
						.append(PATH_COLUMN_NAME)
						// ("=concat('")
						.append("='")
						.append(parentPath)
						.append("/Hueco '")
						// to_char(")
						.append(concatSymbol)
						.append(DBUtils.getNativeStrSyntax(getConnection(),
								NUMORDEN_COLUMN_NAME.toString()))
						// .append(NUMORDEN_COLUMN_NAME).append(")) where ")
						.append(" where ").append(IDELEMPADRE_COLUMN_NAME)
						.append("='").append(idAsignablePadre).append("'")
						.toString();
				stmt.create(conn, sql);
				stmt.execute();
				stmt.release();
			}
		};
		command.execute();
	}

	/**
	 * Retorna
	 * 
	 * @param primeraParteIdentificacion
	 * @param estados
	 * @param idElementoAsignable
	 * @return
	 */
	public List getHuecosEnElementoXEstadoYPrimeraParteIdentificacion(
			String primeraParteIdentificacion, String[] estados,
			String idElementoAsignable) {
		// SELECT ASGDUINSTALACION.IDENTIFICACION
		// FROM asgdhueco, ASGDUINSTALACION
		// WHERE ASGDUINSTALACION.ID = asgdhueco.IDUINSTALACION AND
		// SUBSTR(ASGDUINSTALACION.IDENTIFICACION,0,LENGTH(ASGDUINSTALACION.IDENTIFICACION)-10)='ES/03/8/001/1/1/04'
		// AND
		// asgdhueco.idelemapadre= '0750eb9c71b990000000000000000006' AND (
		// asgdhueco.estado= 'O' )

		String lengthParteIdentificacion = DBUtils.getNativeLengthSyntax(
				getConnection(), "'" + primeraParteIdentificacion + "'");
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateJoinCondition(
						UInstalacionDepositoDBEntity.TABLE_NAME,
						UInstalacionDepositoDBEntity.ID_FIELD, TABLE_NAME,
						CAMPO_IDUINSTALACION))
				.append(DBUtils.AND)
				.append(DBUtils
						.getNativeSubstrSyntax(
								getConnection(),
								UInstalacionDepositoDBEntity.IDENTIFICACION_COLUMN_NAME,
								"1", lengthParteIdentificacion))
				.append("='")
				.append(primeraParteIdentificacion)
				.append("'  ")
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDELEMPADRE,
						idElementoAsignable)).append(" AND ")
				.append(DBUtils.generateORTokens(CAMPO_ESTADO, estados));

		return getHuecos2(qual.toString(),
				DBUtils.generateOrderBy(CAMPO_NUMORDEN));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see deposito.db.IHuecoDBEntity#countHuecosByFormato(java.lang.String)
	 */
	public int countHuecosByFormato(String idFormato) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_FORMATO, idFormato));
		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/**
	 * Obtiene los Huecos que están dentro de un intervalo, ordenados por su
	 * código de Orden.
	 * 
	 * @param huecoOrigen
	 *            Hueco de Origen
	 * @param codOrdenUltimoHueco
	 *            Código de Orden del Último Hueco.
	 * @param estados
	 *            Estados de los huecos necesarios
	 * @return Lista de Huecos que cumplen las condiciones
	 */
	public List getHuecosBetweenXEstadoByCodOrden(HuecoVO huecoOrigenVO,
			HuecoVO huecoDestinoVO, int tipoOrdenacion, String[] estados) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDDEPOSITO,
						huecoOrigenVO.getIddeposito()))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_TIPO_ORD,
						tipoOrdenacion))
				.append(DBUtils.AND)
				.append(DBUtils.generateORTokens(CAMPO_ESTADO, estados))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_FORMATO,
						huecoOrigenVO.getIdformato()))
				.append(DBUtils.AND)
				.append(DBUtils.generateGTEQTokenField(CAMPO_CODORDEN,
						huecoOrigenVO.getCodorden()))
				.append(DBUtils.AND)
				.append(DBUtils.generateLTTokenField(CAMPO_CODORDEN,
						huecoDestinoVO.getCodorden())).append(DBUtils.ORDER_BY)
				.append(CODORDEN_COLUMN_NAME);

		return getVOS(qual.toString(), TABLE_NAME, TABLE_COLUMNS, HuecoVO.class);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * deposito.db.IHuecoDBEntity#countSignaturaInDeposito(java.lang.String,
	 * java.lang.String)
	 */
	public int countSignaturaInDeposito(String signatura,
			String idArchivoReceptor) {

		/*
		 * SELECT COUNT(ID) FROM ASGDHUECO INNER JOIN ASGDDEPOSITO ON
		 * ASGDHUECO.IDDEPOSITO = ASGDDEPOSITO.ID AND ASGDDEPOSITO.IDARCHIVO =
		 * '00000000000000000000000000000001' INNER JOIN ASGDUINSTALACION ON
		 * ASGDHUECO.IDUINSTALACION = ASGDUINSTALACION.ID AND
		 * ASGDUINSTALACION.SIGNATURAUI = '000000025'
		 */

		JoinDefinition[] joinHuecoDeposito = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(new TableDef(TABLE_NAME), CAMPO_IDDEPOSITO),
				new DbColumnDef(new TableDef(DepositoDBEntityImpl.TABLE_NAME),
						DepositoDBEntityImpl.CAMPO_ID)) };
		JoinDefinition[] joinHuecoUI = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(new TableDef(TABLE_NAME), CAMPO_IDUINSTALACION),
				new DbColumnDef(new TableDef(
						UInstalacionDepositoDBEntity.TABLE_NAME),
						UInstalacionDepositoDBEntity.ID_FIELD)) };
		StringBuffer qual = new StringBuffer(
				DBUtils.generateInnerJoinChainedCondition(joinHuecoDeposito))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								DepositoDBEntityImpl.CAMPO_IDARCHIVO,
								idArchivoReceptor))
				.append(DBUtils.generateInnerJoinChainedCondition(joinHuecoUI))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						UInstalacionDepositoDBEntity.SIGNATURAUI_FIELD,
						signatura));

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/**
	 * Actualizar la lista de columnas indicadas del hueco modificadas al
	 * validar una relación de entrega entre archivos
	 **/
	public void updateFieldsHuecoVREA(HuecoVO huecoVO) {
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_IDRELACION, huecoVO.getIdRelEntrega());
		colsToUpdate.put(CAMPO_ESTADO, huecoVO.getEstado());
		colsToUpdate.put(CAMPO_IDUINSTALACION, huecoVO.getIduinstalacion());
		colsToUpdate.put(CAMPO_ORDEN_EN_RELACION, huecoVO.getOrdenenrelacion());

		updateFieldsHueco(huecoVO.getIdElemAPadre(), huecoVO.getNumorden()
				.intValue(), colsToUpdate);
	}

	public void updateFieldsHueco(String idElemAPadre, int numOrden,
			final Map columnsToUpdate) {
		StringBuffer qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDELEMPADRE,
						idElemAPadre)).append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_NUMORDEN, numOrden));

		updateFields(qual.toString(), columnsToUpdate, TABLE_NAME);
	}

	public int getNumHuecosNoLibres(String idDeposito) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDDEPOSITO,
						idDeposito))
				.append(DBUtils.AND)
				.append(DBUtils.generateNEQTokenField(CAMPO_ESTADO,
						HuecoVO.LIBRE_STATE));
		return getVOCount(qual.toString(), TABLE_NAME);
	}

	public int getNumHuecosUbicacion(String idUbicacion) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_IDDEPOSITO, idUbicacion));
		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/**
	 * Comprueba si existe la signatura en algún deposito perteneciente al
	 * archivo de custodia especificado en el parametro, en cuyo caso se
	 * devuelve el hueco y en otro caso NULL.
	 * 
	 * @param signatura
	 * @param idArchivoReceptor
	 * @return HuecoVO
	 */
	public HuecoVO getHuecoBySignaturaInDeposito(String signatura,
			String idArchivoReceptor) {
		/*
		 * SELECT COUNT(ID) FROM ASGDHUECO INNER JOIN ASGDDEPOSITO ON
		 * ASGDHUECO.IDDEPOSITO = ASGDDEPOSITO.ID AND ASGDDEPOSITO.IDARCHIVO =
		 * '00000000000000000000000000000001' INNER JOIN ASGDUINSTALACION ON
		 * ASGDHUECO.IDUINSTALACION = ASGDUINSTALACION.ID AND
		 * ASGDUINSTALACION.SIGNATURAUI = '000000025'
		 */

		JoinDefinition[] joinHuecoDeposito = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(new TableDef(TABLE_NAME), CAMPO_IDDEPOSITO),
				new DbColumnDef(new TableDef(DepositoDBEntityImpl.TABLE_NAME),
						DepositoDBEntityImpl.CAMPO_ID)) };
		JoinDefinition[] joinHuecoUI = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(new TableDef(TABLE_NAME), CAMPO_IDUINSTALACION),
				new DbColumnDef(new TableDef(
						UInstalacionDepositoDBEntity.TABLE_NAME),
						UInstalacionDepositoDBEntity.ID_FIELD)) };
		StringBuffer qual = new StringBuffer(
				DBUtils.generateInnerJoinChainedCondition(joinHuecoDeposito))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								DepositoDBEntityImpl.CAMPO_IDARCHIVO,
								idArchivoReceptor))
				.append(DBUtils.generateInnerJoinChainedCondition(joinHuecoUI))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						UInstalacionDepositoDBEntity.SIGNATURAUI_FIELD,
						signatura));

		return (HuecoVO) getVO(qual.toString(), TABLE_NAME, TABLE_COLUMNS,
				HuecoVO.class);
	}

	public HuecoVO getHuecoUInstalacionXArchivo(String idUInstalacion,
			String idArchivo) {
		JoinDefinition[] joinHuecoDeposito = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(new TableDef(TABLE_NAME), CAMPO_IDDEPOSITO),
				new DbColumnDef(new TableDef(DepositoDBEntityImpl.TABLE_NAME),
						DepositoDBEntityImpl.CAMPO_ID)) };
		JoinDefinition[] joinHuecoUI = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(new TableDef(TABLE_NAME), CAMPO_IDUINSTALACION),
				new DbColumnDef(new TableDef(
						UInstalacionDepositoDBEntity.TABLE_NAME),
						UInstalacionDepositoDBEntity.ID_FIELD)) };
		StringBuffer qual = new StringBuffer(
				DBUtils.generateInnerJoinChainedCondition(joinHuecoDeposito))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						DepositoDBEntityImpl.CAMPO_IDARCHIVO, idArchivo))
				.append(DBUtils.generateInnerJoinChainedCondition(joinHuecoUI))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						UInstalacionDepositoDBEntity.ID_FIELD, idUInstalacion));

		return (HuecoVO) getVO(qual.toString(), TABLE_NAME, TABLE_COLUMNS,
				HuecoVO.class);
	}

	/**
	 * Obtiene el número de huecos pertenecientes a alguno de los depositos del
	 * archivo.
	 * 
	 * @param idArchivo
	 * @return
	 */
	public int getNumHuecosXArchivo(String idArchivo) {
		/*
		 * SELECT count(*) FROM ASGDHUECO INNER JOIN ASGDDEPOSITO ON
		 * ASGDHUECO.IDDEPOSITO = ASGDDEPOSITO.ID AND ASGDDEPOSITO.IDARCHIVO =
		 * 'idArchivo'
		 */

		JoinDefinition[] joinHuecoDeposito = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(new TableDef(TABLE_NAME), CAMPO_IDDEPOSITO),
				new DbColumnDef(new TableDef(DepositoDBEntityImpl.TABLE_NAME),
						DepositoDBEntityImpl.CAMPO_ID)) };

		StringBuffer qual = new StringBuffer(
				DBUtils.generateInnerJoinChainedCondition(joinHuecoDeposito))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						DepositoDBEntityImpl.CAMPO_IDARCHIVO, idArchivo));

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/**
	 * Obtiene el hueco perteneciente al archivo y cuya numeracion es la misma
	 * que la pasada como parametro.
	 * 
	 * @param idArchivo
	 * @param numeracion
	 * @return
	 */
	public HuecoVO getHuecoAsociadoNumeracion(String idArchivo,
			String numeracion) {
		/*
		 * SELECT ASGDHUECO.* FROM ASGDHUECO INNER JOIN ASGDDEPOSITO ON
		 * ASGDHUECO.IDDEPOSITO = ASGDDEPOSITO.ID AND ASGDDEPOSITO.IDARCHIVO =
		 * 'idArchivo' AND ASGDHUECO.NUMERACION = 'numeracion'
		 */

		JoinDefinition[] joinHuecoDeposito = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(new TableDef(TABLE_NAME), CAMPO_IDDEPOSITO),
				new DbColumnDef(new TableDef(DepositoDBEntityImpl.TABLE_NAME),
						DepositoDBEntityImpl.CAMPO_ID)) };

		StringBuffer qual = new StringBuffer(
				DBUtils.generateInnerJoinChainedCondition(joinHuecoDeposito))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						DepositoDBEntityImpl.CAMPO_IDARCHIVO, idArchivo))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_NUMERACION,
						numeracion));

		return (HuecoVO) getVO(qual.toString(), TABLE_NAME, TABLE_COLUMNS,
				HuecoVO.class);
	}

	/**
	 * Obtiene el hueco perteneciente al archivo y cuya numeracion es la misma
	 * que la pasada como parametro.
	 * 
	 * @param idArchivo
	 * @param numeracion
	 * @return
	 */
	public int countHuecosAsociadoNumeracion(String idArchivo, String numeracion) {
		/*
		 * SELECT ASGDHUECO.* FROM ASGDHUECO INNER JOIN ASGDDEPOSITO ON
		 * ASGDHUECO.IDDEPOSITO = ASGDDEPOSITO.ID AND ASGDDEPOSITO.IDARCHIVO =
		 * 'idArchivo' AND ASGDHUECO.NUMERACION = 'numeracion'
		 */

		JoinDefinition[] joinHuecoDeposito = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(new TableDef(TABLE_NAME), CAMPO_IDDEPOSITO),
				new DbColumnDef(new TableDef(DepositoDBEntityImpl.TABLE_NAME),
						DepositoDBEntityImpl.CAMPO_ID)) };

		StringBuffer qual = new StringBuffer(
				DBUtils.generateInnerJoinChainedCondition(joinHuecoDeposito))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						DepositoDBEntityImpl.CAMPO_IDARCHIVO, idArchivo))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_NUMERACION,
						numeracion));

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	public long getMaxNumeracionHueco(String idArchivo) throws Exception {
		JoinDefinition[] joinHuecoDeposito = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(new TableDef(TABLE_NAME), CAMPO_IDDEPOSITO),
				new DbColumnDef(new TableDef(DepositoDBEntityImpl.TABLE_NAME),
						DepositoDBEntityImpl.CAMPO_ID)) };

		StringBuffer qual = new StringBuffer();

		qual.append(
				DBUtils.generateInnerJoinChainedCondition(joinHuecoDeposito))
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(
						DepositoDBEntityImpl.CAMPO_IDARCHIVO, idArchivo))
				.append(DBUtils.AND)
				.append(DBUtils.getNativeIsNumericSyntax(getConnection(),
						CAMPO_NUMERACION.getQualifiedName()));

		return DbSelectFns.selectMaxLong(getConnection(), DbUtil
				.getTableNames(new DbTableDef[] { new DbTableDef(TABLE_NAME,
						TABLE_NAME) }), DbUtil.getNativeToNumberSyntax(
				getConnection(), NUMERACION_COLUMN_NAME, 16),
				NUMERACION_COLUMN_NAME, qual.toString());
	}

	/**
	 * Obtiene la numeracion del hueco segun el tipo de busqueda(min o max) para
	 * obtener el menor o el mayor de las numeraciones de los huecos.
	 * 
	 * @param idElemAPadre
	 * @param tipoBusqueda
	 * @return
	 */
	public long obtenerNumeracionHueco(String idElemAPadre, String tipoBusqueda) {
		/*
		 * select [MIN|MAX](CONVERT(NUMERIC,numeracion)) from asgdhueco where
		 * idelemapadre='idElemAPadre' and isnumeric(asgdhueco.numeracion)='1'
		 */
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDELEMPADRE,
						idElemAPadre))
				.append(DBUtils.AND)
				.append(DBUtils.getNativeIsNumericSyntax(getConnection(),
						CAMPO_NUMERACION.getQualifiedName()));

		long numeracion = 0;
		if (DBUtils.MIN.equals(tipoBusqueda))
			numeracion = getMinString(qual.toString(), TABLE_NAME,
					CAMPO_NUMERACION);
		else
			numeracion = getMaxString(qual.toString(), TABLE_NAME,
					CAMPO_NUMERACION);
		return numeracion;
	}

	/**
	 * Obtiene la numeracion del hueco segun el tipo de busqueda(min o max) para
	 * obtener el menor o el mayor de las numeraciones de los huecos según el
	 * orden.
	 * 
	 * @param idElemAPadre
	 * @param tipoBusqueda
	 * @return
	 */
	public String obtenerNumeracionOrdenHueco(String idElemAPadre,
			String tipoBusqueda) {
		/*
		 * select [MIN|MAX](numorden) from asgdhueco where
		 * idelemapadre='idElemAPadre';
		 */
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_IDELEMPADRE, idElemAPadre));

		int numorden;
		if (DBUtils.MIN.equals(tipoBusqueda)) {
			numorden = getMin(qual.toString(), TABLE_NAME, CAMPO_NUMORDEN);
		} else {
			numorden = getMax(qual.toString(), TABLE_NAME, CAMPO_NUMORDEN);
		}
		HuecoVO huecoVO = getHueco(new HuecoID(idElemAPadre, numorden));
		return huecoVO.getNumeracion();
	}

	/**
	 * Se encarga de buscar todos los huecos pertenecientes a los depositos del
	 * mismo archivo cuya numeración sea NUMERICA y MAYOR que la que tiene
	 * actualmente el hueco y el estado sea LIBRE.
	 * 
	 * @param huecoVO
	 * @return
	 * @throws IeciTdException
	 */
	private boolean comprobarNumeracionHuecosLibres(final HuecoVO huecoVO,
			final String idArchivo) {
		/*
		 * select asgdhueco.* from asgdhueco inner join asgddeposito on
		 * asgddeposito.id=asgdhueco.iddeposito and asgddeposito.id =
		 * 'huecoVO.getIddeposito()' inner join agarchivo on
		 * agarchivo.id=asgddeposito.idarchivo and asgdhueco.estado='L' and
		 * isnumeric(asgdhueco.numeracion)='1' and
		 * convert(numeric,asgdhueco.numeracion) > huecoVO.getNumeracion()
		 */

		JoinDefinition[] joinHuecoDeposito = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(new TableDef(TABLE_NAME), CAMPO_IDDEPOSITO),
				new DbColumnDef(new TableDef(DepositoDBEntityImpl.TABLE_NAME),
						DepositoDBEntityImpl.CAMPO_ID)) };

		StringBuffer qual = new StringBuffer(
				DBUtils.generateInnerJoinChainedCondition(joinHuecoDeposito))
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(
						DepositoDBEntityImpl.CAMPO_IDARCHIVO, idArchivo))
				.append(DBUtils.AND)
				.append(DBUtils.generateNEQTokenField(CAMPO_ESTADO,
						HuecoVO.LIBRE_STATE))
				.append(DBUtils.AND)
				.append(DBUtils.getNativeIsNumericSyntax(getConnection(),
						CAMPO_NUMERACION.getQualifiedName()))
				.append(DBUtils.AND)
				.append(DBUtils.getNativeGetNumericPositiveSyntax(
						getConnection(), CAMPO_NUMERACION.getQualifiedName()))
				.append(ConstraintType.getSymbol(ConstraintType.GREATER))
				.append(huecoVO.getNumeracion());

		if (getVOCount(qual.toString(), TABLE_NAME) > 0)
			return false;
		return true;
	}

	/**
	 * Se encarga de buscar todos los huecos pertenecientes a los depositos del
	 * mismo archivo cuya numeración sea NUMERICA y MAYOR que la que tiene
	 * actualmente el hueco y el estado sea LIBRE.
	 * 
	 * @param huecoVO
	 * @return
	 * @throws IeciTdException
	 */
	private boolean comprobarNumeracionHuecosMenorMaximo(final HuecoVO huecoVO,
			long incremento, final String idArchivo) {
		JoinDefinition[] joinHuecoDeposito = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(new TableDef(TABLE_NAME), CAMPO_IDDEPOSITO),
				new DbColumnDef(new TableDef(DepositoDBEntityImpl.TABLE_NAME),
						DepositoDBEntityImpl.CAMPO_ID)) };

		StringBuffer qual = new StringBuffer(
				DBUtils.generateInnerJoinChainedCondition(joinHuecoDeposito))
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(
						DepositoDBEntityImpl.CAMPO_IDARCHIVO, idArchivo))
				.append(DBUtils.AND)
				.append(DBUtils.getNativeIsNumericSyntax(getConnection(),
						CAMPO_NUMERACION.getQualifiedName()))
				.append(DBUtils.AND)
				.append(DBUtils.getNativeGetNumericPositiveSyntax(
						getConnection(), CAMPO_NUMERACION.getQualifiedName()))
				.append(ConstraintType.getSymbol(ConstraintType.GREATER))
				.append(huecoVO.getNumeracion())
				.append(DBUtils.AND)
				.append(DBUtils.getNativeGetNumericPositiveSyntax(
						getConnection(), CAMPO_NUMERACION.getQualifiedName())
						+ "+" + ("" + incremento))
				.append(ConstraintType.getSymbol(ConstraintType.GREATER))
				.append(DepositoConstants.MAX_NUMERACION_ENTERA_HUECOS);

		if (getVOCount(qual.toString(), TABLE_NAME) > 0)
			return false;
		return true;
	}

	/**
	 * Se encarga de buscar todos los huecos pertenecientes a los depositos del
	 * mismo archivo cuya numeración sea NUMERICA y ademas este comprendida
	 * entre la nueva numeraciòn y la anterior.
	 * 
	 * @param huecoVO
	 * @param nuevaNumeracion
	 * @return
	 */
	private boolean comprobarHuecosNoCreados(final HuecoVO huecoVO,
			final String nuevaNumeracion, String idArchivo) {
		/*
		 * select asgdhueco.* from asgdhueco inner join asgddeposito on
		 * asgddeposito.id=asgdhueco.iddeposito and asgddeposito.id =
		 * 'huecoVO.getIddeposito()' inner join agarchivo on agarchivo.id =
		 * asgddeposito.idarchivo and isnumeric(asgdhueco.numeracion)='1' and
		 * convert(numeric,asgdhueco.numeracion) >= nuevaNumeracion and
		 * convert(numeric,asgdhueco.numeracion) < huecoVO.getNumeracion()
		 */
		JoinDefinition[] joinHuecoDeposito = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(new TableDef(TABLE_NAME), CAMPO_IDDEPOSITO),
				new DbColumnDef(new TableDef(DepositoDBEntityImpl.TABLE_NAME),
						DepositoDBEntityImpl.CAMPO_ID)) };

		StringBuffer qual = new StringBuffer(
				DBUtils.generateInnerJoinChainedCondition(joinHuecoDeposito))
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(
						DepositoDBEntityImpl.CAMPO_IDARCHIVO, idArchivo))
				.append(DBUtils.AND)
				.append(DBUtils.getNativeIsNumericSyntax(getConnection(),
						CAMPO_NUMERACION.getQualifiedName()))
				.append(DBUtils.AND)
				.append(DBUtils.getNativeGetNumericPositiveSyntax(
						getConnection(), CAMPO_NUMERACION.getQualifiedName()))
				.append(ConstraintType
						.getSymbol(ConstraintType.GREATER_OR_EQUAL))
				.append(nuevaNumeracion)
				.append(DBUtils.AND)
				.append(DBUtils.getNativeGetNumericPositiveSyntax(
						getConnection(), CAMPO_NUMERACION.getQualifiedName()))
				.append(ConstraintType.getSymbol(ConstraintType.LESS))
				.append(huecoVO.getNumeracion());

		if (getVOCount(qual.toString(), TABLE_NAME) > 0)
			return false;
		return true;
	}

	/**
	 * Comprueba si la renumeracion del hueco se puede llevar a cabo o no.
	 * 
	 * @param huecoVO
	 * @param nuevaNumeracion
	 * @return
	 */
	public boolean comprobarRenumeracionHueco(final HuecoVO huecoVO,
			final String nuevaNumeracion, final String idArchivo)
			throws DepositoException {
		boolean res = false;
		if (StringUtils.isNotEmpty(nuevaNumeracion)
				&& StringUtils.isNumeric(nuevaNumeracion)
				&& StringUtils.isNotEmpty(huecoVO.getNumeracion())
				&& StringUtils.isNumeric(huecoVO.getNumeracion())) {

			long antigua = Long.parseLong(huecoVO.getNumeracion());
			long nueva = Long.parseLong(nuevaNumeracion);
			if (nueva > antigua) {
				res = comprobarNumeracionHuecosLibres(huecoVO, idArchivo);
				if (!res)
					throw new NoPermitidaRenumeracionHuecoOcupadaException();
				res = comprobarNumeracionHuecosMenorMaximo(huecoVO, nueva
						- antigua, idArchivo);
				if (!res)
					throw new NoPermitidaRenumeracionHuecoMaximoSuperadoException();

			} else {
				res = comprobarHuecosNoCreados(huecoVO, nuevaNumeracion,
						idArchivo);
				if (res) {
					res = comprobarNumeracionHuecosLibres(huecoVO, idArchivo);
					if (!res)
						throw new NoPermitidaRenumeracionHuecoOcupadaException();
				} else
					throw new NoPermitidaRenumeracionHuecoCreadaException();
			}
		} else
			throw new NoPermitidaRenumeracionHuecoAlfanumericaException();
		return res;
	}

	/**
	 * Comprueba si la numeracion ya esta utilizada por alguno de los huecos del
	 * archivo.
	 * 
	 * @param idArchivo
	 * @param numeracion
	 * @return
	 */
	public boolean isNumeracionEnUso(final String idArchivo,
			final String numeracion) {
		if (getHuecoAsociadoNumeracion(idArchivo, numeracion) != null)
			return true;
		else
			return false;
	}

	/**
	 * Se encarga de actualizar la numeración de un determinado hueco
	 * 
	 * @param idHueco
	 * @param nuevaNumeracion
	 */
	public void updateNumeracionHueco(final HuecoID idHueco,
			final String nuevaNumeracion) {
		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDELEMPADRE,
						idHueco.getIdpadre()))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_NUMORDEN,
						Integer.toString(idHueco.getNumorden()))).toString();
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_NUMERACION, nuevaNumeracion);
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	/**
	 * Se encarga de llevar a cabo la renumeración de los huecos.
	 * 
	 * @param idArchivo
	 * @param huecoVO
	 * @param nuevaNumeracion
	 * @param renumerar
	 */
	public void renumerarHuecos(final String idArchivo, final HuecoVO huecoVO,
			final String nuevaNumeracion, final boolean renumerar)
			throws DepositoException {
		/*
		 * update asgdhueco set numeracion = str(convert(numeric,numeracion) +
		 * (nueva - antigua)) where asgdhueco.iddeposito IN (select
		 * asgddeposito.id from asgddeposito where idarchivo='idArchivo') and
		 * isnumeric(numeracion)='1' and convert(numeric,numeracion) >= antigua
		 */

		long antigua = Long.parseLong(huecoVO.getNumeracion());
		long nueva = Long.parseLong(nuevaNumeracion);

		String query = new StringBuffer()
				.append(DBUtils.SELECT)
				.append(DepositoDBEntityImpl.CAMPO_ID)
				.append(DBUtils.FROM)
				.append(DepositoDBEntityImpl.TABLE_NAME)
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(
						DepositoDBEntityImpl.CAMPO_IDARCHIVO, idArchivo))
				.toString();

		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateInTokenFieldSubQuery(CAMPO_IDDEPOSITO,
						query))
				.append(DBUtils.AND)
				.append(DBUtils.getNativeIsNumericSyntax(getConnection(),
						CAMPO_NUMERACION.getQualifiedName()))
				.append(DBUtils.AND)
				.append(DBUtils.getNativeGetNumericPositiveSyntax(
						getConnection(), CAMPO_NUMERACION.getQualifiedName()))
				.append(ConstraintType
						.getSymbol(ConstraintType.GREATER_OR_EQUAL))
				.append(antigua).toString();

		// Modificado, porque daba error el str al convertir numeros de 16
		// digitos, metia en base de datos asteriscos.
		String value = DBUtils.getNativeTrimSyntax(getConnection(), DBUtils
				.getNativeStrSyntax(
						getConnection(),
						DBUtils.getNativeGetNumericPositiveSyntax(
								getConnection(),
								CAMPO_NUMERACION.getQualifiedName())
								+ " + (" + (nueva - antigua) + ")"));
		try {
			DbUpdateFns.updateCustom(getConnection(), TABLE_NAME,
					NUMERACION_COLUMN_NAME, value, qual);
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	/**
	 * Cuenta el numero de huecos numericos existentes en un determinado
	 * elemento
	 * 
	 * @param idElementoPadre
	 */
	public int countNumHuecosNumericos(final String idElementoPadre) {
		/*
		 * select count(*) from asgdhueco where idelemapadre='idElementopadre'
		 * and isnumeric(numeracion)='1'
		 */

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDELEMPADRE,
						idElementoPadre))
				.append(DBUtils.AND)
				.append(DBUtils.getNativeIsNumericSyntax(getConnection(),
						CAMPO_NUMERACION.getQualifiedName()));

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see deposito.db.IHuecoDBEntity#updateEstadoHuecoUInstalacion(java.lang.String,
	 *      java.lang.String)
	 */
	public void updateEstadoHuecoUInstalacion(String idRelEntrega,
			String estadoHueco) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE);
		qual.append(DBUtils
				.generateEQTokenField(CAMPO_IDRELACION, idRelEntrega));

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_ESTADO, estadoHueco);
		colsToUpdate.put(CAMPO_IDUINSTALACION, null);
		colsToUpdate.put(CAMPO_IDRELACION, null);
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IeciTdException
	 * @throws TooManyResultsException
	 * @see deposito.db.IHuecoDBEntity#getHuecosBySignaturas(java.lang.String,
	 *      java.lang.String, java.lang.String, common.util.IntervalOptions)
	 */
	public List getHuecosBySignaturas(String idElementoNoAsignable,
			String idUbicacion, String idFormato, IntervalOptions options)
			throws IeciTdException, TooManyResultsException {
		String tables = TABLE_NAME + Constants.COMMA
				+ ElementoAsignableDBEntity.TABLE_NAME + Constants.COMMA
				+ UInstalacionDepositoDBEntity.TABLE_NAME;
		DbColumnDef signaturaUI = new DbColumnDef("signaturaUI",
				UInstalacionDepositoDBEntity.TABLE_NAME,
				UInstalacionDepositoDBEntity.SIGNATURAUI_FIELD);
		DbColumnDef[] cols = (DbColumnDef[]) ArrayUtils.concat(TABLE_COLUMNS,
				new DbColumnDef[] { signaturaUI });
		DbColumnDef idElemAsignable = new DbColumnDef(new TableDef(
				ElementoAsignableDBEntity.TABLE_NAME),
				ElementoAsignableDBEntity.CAMPO_ID);
		DbColumnDef idElemnPadre = new DbColumnDef(new TableDef(
				ElementoAsignableDBEntity.TABLE_NAME),
				ElementoAsignableDBEntity.CAMPO_IDELEMNPADRE);
		DbColumnDef idDeposito = new DbColumnDef(new TableDef(
				ElementoAsignableDBEntity.TABLE_NAME),
				ElementoAsignableDBEntity.CAMPO_IDDEPOSITO);
		DbColumnDef idFmthueco = new DbColumnDef(new TableDef(
				ElementoAsignableDBEntity.TABLE_NAME),
				ElementoAsignableDBEntity.CAMPO_IDFMTHUECO);

		StringBuffer qual = new StringBuffer(DBUtils.WHERE);
		ConsultaConnectBy consulta = null;
		if (StringUtils.isNotEmpty(idUbicacion)) {
			qual.append(DBUtils.generateEQTokenField(idDeposito, idUbicacion));
		} else {
			consulta = DbUtil.generateNativeSQLWithConnectBy(getConnection(),
					new TableDef(ElementoNoAsignableDBEntity.TABLE_NAME),
					ElementoNoAsignableDBEntity.CAMPO_ID,
					ElementoNoAsignableDBEntity.CAMPO_IDPADRE,
					new String[] { idElementoNoAsignable }, new HashMap());
			qual.append(DBUtils.generateInTokenFieldSubQuery(idElemnPadre,
					consulta.getSqlClause()));
		}
		qual.append(DBUtils.AND)
				.append(idElemAsignable)
				.append(Constants.EQUAL)
				.append(CAMPO_IDELEMPADRE)
				.append(DBUtils.AND)
				.append(UInstalacionDepositoDBEntity.ID_FIELD)
				.append(Constants.EQUAL)
				.append(CAMPO_IDUINSTALACION)
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(idFmthueco, idFormato))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_ESTADO,
						HuecoVO.OCUPADO_STATE));

		if (options != null && !options.getOptions().isEmpty()) {
			List simples = getOptionsType(IntervalOption.SIMPLE_ITEM, options);
			List ranges = getOptionsType(IntervalOption.ITEM_RANGE, options);

			qual.append(DBUtils.AND);
			qual.append(Constants.ABRIR_PARENTESIS);
			if (!simples.isEmpty()) {
				String[] items = new String[simples.size()];
				int i = 0;
				for (Iterator iter = simples.iterator(); iter.hasNext();) {
					IntervalSimpleOption simple = (IntervalSimpleOption) iter
							.next();
					items[i++] = simple.getItem();
				}
				qual.append(DBUtils.generateInTokenField(
						UInstalacionDepositoDBEntity.SIGNATURAUI_FIELD, items));
			}
			if (!ranges.isEmpty()) {
				if (!simples.isEmpty()) {
					qual.append(DBUtils.OR);
				}
				for (Iterator iter = ranges.iterator(); iter.hasNext();) {
					IntervalRangeOption range = (IntervalRangeOption) iter
							.next();
					qual.append(Constants.ABRIR_PARENTESIS)
							.append(DBUtils
									.generateBetweenCadena(
											UInstalacionDepositoDBEntity.SIGNATURAUI_FIELD,
											range.getMinItem(),
											range.getMaxItem()))
							.append(Constants.CERRAR_PARENTESIS);
					if (iter.hasNext()) {
						qual.append(DBUtils.OR);
					}
				}
			}
			qual.append(Constants.CERRAR_PARENTESIS);
		}
		qual.append(DBUtils.generateOrderBy(signaturaUI));
		if (consulta != null
				&& StringUtils.isNotEmpty(consulta.getWithClause())) {
			return getDistinctVOS_WITH(consulta.getWithClause(),
					qual.toString(), tables, cols, HuecoVO.class);
		} else {
			return getVOS(qual.toString(), tables, cols, HuecoVO.class);
		}
	}

	private List getOptionsType(int type, IntervalOptions options) {
		List signaturas = new ArrayList();
		if (options != null && !options.getOptions().isEmpty()) {
			for (int i = 0; i < options.getOptions().size(); i++) {
				IntervalOption option = (IntervalOption) options.getOptions()
						.get(i);
				if (option.getType() == type) {
					signaturas.add(option);
				}
			}
		}
		return signaturas;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see deposito.db.IHuecoDBEntity#updateUIConReencajado(deposito.vos.HuecoID,
	 *      java.lang.String)
	 */
	public void updateUIConReencajado(HuecoID idHueco, String idUIREEACR) {

		StringBuffer qual = new StringBuffer(ROW_QUAL(idHueco));

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_IDUIREEACR, idUIREEACR);
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see deposito.db.IHuecoDBEntity#liberarUIsConReencajado(deposito.vos.HuecoID[])
	 */
	public void liberarUIsConReencajado(HuecoID[] huecosID) {
		StringBuffer qual = new StringBuffer();
		for (int i = 0; i < huecosID.length; i++) {
			HuecoID huecoID = huecosID[i];
			StringBuffer qualHuecoId = new StringBuffer();
			qualHuecoId
					.append(DBUtils.ABRIR_PARENTESIS)
					.append(DBUtils.generateEQTokenField(CAMPO_IDELEMPADRE,
							huecoID.getIdpadre()))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(CAMPO_NUMORDEN,
							huecoID.getNumorden()))
					.append(DBUtils.CERRAR_PARENTESIS);

			qual.append(DBUtils.getOrCondition(qual.toString())).append(
					qualHuecoId.toString());
		}

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_IDUIREEACR, null);
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see deposito.db.IHuecoDBEntity#updateHuecoIdUInstalacionByIdUIReeaCR(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void updateHuecoIdUInstalacionByIdUIReeaCR(String idRelEntrega,
			String idUIReeaCR, String idUInstalacion) {
		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDRELACION,
						idRelEntrega))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_IDUIREEACR,
						idUIReeaCR)).toString();

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_IDUINSTALACION, idUInstalacion);
		colsToUpdate.put(CAMPO_IDUIREEACR, null);
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see deposito.db.IHuecoDBEntity#updateHuecosElementoNoAsignable(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void updateHuecosElementoNoAsignable(final String idNoAsignable,
			final String pathAntiguo, final String pathNuevo) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbInputStatement stmt = new DbInputStatement();

				DbColumnDef columnaIdAsignable = new DbColumnDef("id",
						new TableDef(ElementoAsignableDBEntity.TABLE_NAME,
								ElementoAsignableDBEntity.TABLE_NAME),
						ElementoAsignableDBEntity.ID_COLUMN_NAME,
						ElementoAsignableDBEntity.CAMPO_ID.getDataType(), true);

				DbColumnDef columnaIdElemPadreAsignable = new DbColumnDef(
						"idPadre", new TableDef(
								ElementoAsignableDBEntity.TABLE_NAME,
								ElementoAsignableDBEntity.TABLE_NAME),
						ElementoAsignableDBEntity.IDELEMNPADRE_COLUMN_NAME,
						ElementoAsignableDBEntity.CAMPO_IDELEMNPADRE
								.getDataType(), true);

				ConsultaConnectBy consultaConnectBy = DbUtil
						.generateNativeSQLWithConnectBy(
								getConnection(),
								new TableDef(
										ElementoNoAsignableDBEntity.TABLE_NAME),
								ElementoNoAsignableDBEntity.CAMPO_ID,
								ElementoNoAsignableDBEntity.CAMPO_IDPADRE,
								new String[] { idNoAsignable }, null);

				StringBuffer selectClause = new StringBuffer(DBUtils.SELECT)
						.append(columnaIdAsignable)
						.append(DBUtils.FROM)
						.append(ElementoAsignableDBEntity.TABLE_NAME)
						.append(DBUtils.WHERE)
						.append(DBUtils.generateInTokenFieldSubQuery(
								columnaIdElemPadreAsignable,
								consultaConnectBy.getSqlClause()));

				StringBuffer whereUpdate = new StringBuffer(DBUtils.WHERE)
						.append(DBUtils.generateInTokenFieldSubQuery(
								CAMPO_IDELEMPADRE, selectClause.toString()));

				consultaConnectBy.setWhereClause(whereUpdate.toString());

				String campoUpdate = DBUtils.getNativeReplaceSyntax(conn,
						CAMPO_PATH, pathAntiguo, pathNuevo);

				ConsultaConnectBy consultaUpdate = DbUtil
						.generateNativeSQLUpdateRecursive(getConnection(),
								new TableDef(TABLE_NAME),
								new String[] { campoUpdate }, consultaConnectBy);

				stmt.create(conn, consultaUpdate.getSqlClause());
				stmt.execute();
				stmt.release();
			}
		};
		command.execute();
	}
}