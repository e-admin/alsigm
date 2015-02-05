package fondos.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.SigiaDbInputRecord;
import common.db.SingleValueDBOutputRecordSet;
import common.db.TableDef;
import common.util.ArrayUtils;

import descripcion.db.DescriptorDBEntityImpl;
import fondos.db.oracle.ProductorSerieDBEntityImpl;
import fondos.vos.ProductorSerieVO;

public abstract class ProductorSerieDBEntityImplBase extends DBEntity implements
		IProductorSerieDbEntity {

	/** Logger de la clase */
	static Logger logger = Logger.getLogger(ProductorSerieDBEntityImpl.class);

	public static final String TABLE_NAME_PRODUCTORSERIE = "ASGFPRODSERIE";

	private static final String IDSERIE_COLUMN_NAME = "IDSERIE";

	private static final String IDPROCEDIMIENTO_COLUMN_NAME = "IDPROCEDIMIENTO";

	private static final String IDPRODUCTOR_COLUMN_NAME = "IDPRODUCTOR";

	private static final String TIPOPRODUCTOR_COLUMN_NAME = "TIPOPRODUCTOR";

	private static final String FECHAINICIO_COLUMN_NAME = "FECHAINICIO";

	private static final String FECHAFINAL_COLUMN_NAME = "FECHAFINAL";

	private static final String LISTA_ACCESO_COLUMN_NAME = "IDLCAPREF";

	private static final String MARCAS_COLUMN_NAME = "MARCAS";

	public static final DbColumnDef CAMPO_IDSERIE = new DbColumnDef(null,
			TABLE_NAME_PRODUCTORSERIE, IDSERIE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_IDPROCEDIMIENTO = new DbColumnDef(
			null, TABLE_NAME_PRODUCTORSERIE, IDPROCEDIMIENTO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_IDPRODUCTOR = new DbColumnDef(null,
			TABLE_NAME_PRODUCTORSERIE, IDPRODUCTOR_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_TIPOPRODUCTOR = new DbColumnDef(null,
			TABLE_NAME_PRODUCTORSERIE, TIPOPRODUCTOR_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, false);

	public static final DbColumnDef CAMPO_FECHAINICIO = new DbColumnDef(
			"fechainicial", TABLE_NAME_PRODUCTORSERIE, FECHAINICIO_COLUMN_NAME,
			DbDataType.DATE_TIME, false);

	public static final DbColumnDef CAMPO_FECHAFINAL = new DbColumnDef(
			"fechafinal", TABLE_NAME_PRODUCTORSERIE, FECHAFINAL_COLUMN_NAME,
			DbDataType.DATE_TIME, false);

	public static final DbColumnDef CAMPO_LISTAACCESO = new DbColumnDef(
			"idlcapref", TABLE_NAME_PRODUCTORSERIE, LISTA_ACCESO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_MARCAS = new DbColumnDef(null,
			TABLE_NAME_PRODUCTORSERIE, MARCAS_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, false);

	public static final DbColumnDef[] COLS_DEFS_PRODUCTOR_SERIE = {
			CAMPO_IDSERIE, CAMPO_IDPROCEDIMIENTO, CAMPO_IDPRODUCTOR,
			CAMPO_TIPOPRODUCTOR, CAMPO_FECHAINICIO, CAMPO_FECHAFINAL,
			CAMPO_LISTAACCESO, CAMPO_MARCAS };

	public static final String COLUMN_NAMES_PRODUCTORSERIE = DbUtil
			.getColumnNames(COLS_DEFS_PRODUCTOR_SERIE);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME_PRODUCTORSERIE;
	}

	/**
	 * @param dataSource
	 */
	public ProductorSerieDBEntityImplBase(DbDataSource dataSource) {
		super(dataSource);
	}

	public ProductorSerieDBEntityImplBase(DbConnection conn) {
		super(conn);
	}

	public List getProductoresXSerie(String idSerie) {

		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie))
				.toString();

		return getProductoresSerieVO(qual);
	}

	private static final String[] queryTables = { TABLE_NAME_PRODUCTORSERIE,
			DescriptorDBEntityImpl.TABLE_NAME };
	private static final DbColumnDef[] queryColumns = (DbColumnDef[]) ArrayUtils
			.concat(COLS_DEFS_PRODUCTOR_SERIE, DescriptorDBEntityImpl.COLS_DEFS);

	private static final String[] queryTablesProductor = { TABLE_NAME_PRODUCTORSERIE };

	private static final DbColumnDef[] queryColumnsProductor = (DbColumnDef[]) COLS_DEFS_PRODUCTOR_SERIE;

	// (DbColumnDef[])ArrayUtils.concat(COLS_DEFS_PRODUCTOR_SERIE, new
	// DbColumnDef[]{DescriptorDBEntityImpl.CAMPO_NOMBRE});

	private String composeWhereClause(String qual, boolean orderByDate) {
		StringBuffer whereClause = new StringBuffer(qual);
		if (qual.length() > 0)
			whereClause.append("AND ");
		else
			whereClause.append("WHERE ");
		whereClause.append(
				DBUtils.generateJoinCondition(CAMPO_IDPRODUCTOR,
						DescriptorDBEntityImpl.CAMPO_ID)).append(" ORDER BY ");

		if (orderByDate) {
			whereClause.append(CAMPO_FECHAFINAL.getQualifiedName()).append(
					" DESC ");
		} else {
			whereClause.append(DescriptorDBEntityImpl.CAMPO_NOMBRE
					.getQualifiedName());
		}

		return whereClause.toString();
	}

	private String composeWhereClause(String qual) {
		return composeWhereClause(qual, false);
	}

	protected List getProductoresSerieHistoricoVO(final String qual) {
		String order = ProductorSerieDBEntityImpl.CAMPO_FECHAFINAL
				.getQualifiedName() + " DESC";
		return getVOS(composeWhereClause(qual, order),
				DBUtils.generateTableSet(queryTables), queryColumns,
				ProductorSerieVO.class);
	}

	private String composeWhereClause(String qual, String order) {
		StringBuffer whereClause = new StringBuffer(qual);
		if (qual.length() > 0)
			whereClause.append("AND ");
		else
			whereClause.append("WHERE ");
		whereClause
				.append(DBUtils.generateJoinCondition(CAMPO_IDPRODUCTOR,
						DescriptorDBEntityImpl.CAMPO_ID)).append(" ORDER BY ")
				.append(order);

		return whereClause.toString();
	}

	protected List getProductoresSerieVO(final String qual) {
		return getVOS(composeWhereClause(qual),
				DBUtils.generateTableSet(queryTables), queryColumns,
				ProductorSerieVO.class);
	}

	protected List getProductoresSerieVO(final String qual, boolean orderByDate) {
		return getVOS(composeWhereClause(qual, orderByDate),
				DBUtils.generateTableSet(queryTables), queryColumns,
				ProductorSerieVO.class);
	}

	public ProductorSerieVO getProductorSerieVO(String idSerie,
			String idProcedimiento, String idProductor) {
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPRODUCTOR,
						idProductor))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPROCEDIMIENTO,
						idProcedimiento));

		return (ProductorSerieVO) getVO(qual.toString(),
				TABLE_NAME_PRODUCTORSERIE, COLS_DEFS_PRODUCTOR_SERIE,
				ProductorSerieVO.class);

	}

	private ProductorSerieVO getProductorSerieVO(final String qual) {
		return (ProductorSerieVO) getVO(composeWhereClause(qual),
				DBUtils.generateTableSet(queryTables), queryColumns,
				ProductorSerieVO.class);
	}

	public ProductorSerieVO getProductorXId(String idSerie, String idProductor) {
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPRODUCTOR,
						idProductor)).append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie));

		return getProductorSerieVO(qual.toString());

	}

	public ProductorSerieVO getProductorXIdSerieEIdDescriptorOrgano(
			String idSerie, String idProductor) {
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPRODUCTOR,
						idProductor)).append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie));

		return (ProductorSerieVO) getVO(qual.toString(),
				DBUtils.generateTableSet(queryTablesProductor),
				queryColumnsProductor, ProductorSerieVO.class);
	}

	public ProductorSerieVO getProductorXIdSerieYNombreProductor(
			String idSerie, String nombreProductor) {

		/*
		 * SELECT * FROM ASGFPRODSERIE INNER JOIN ADDESCRIPTOR ON
		 * ASGFPRODSERIE.IDPRODUCTOR = ADDESCRIPTOR.ID WHERE ADDESCRIPTOR.NOMBRE
		 * = '' AND IDSERIE=''
		 */

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(
						DescriptorDBEntityImpl.CAMPO_NOMBRE, nombreProductor))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie));

		return getProductorSerieVO(qual.toString());

	}

	public ProductorSerieVO getProductorXIdSerieYIdProcYIdProductorYTipo(
			final String idSerie, final String idProc,
			final String idProductor, final Integer tipo) {
		StringBuffer qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPRODUCTOR,
						idProductor));
		if (idProc != null)
			qual.append(" AND ")
					.append(DBUtils.generateEQTokenField(CAMPO_IDPROCEDIMIENTO,
							idProc));
		if (tipo != null)
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_TIPOPRODUCTOR,
							tipo.intValue()));

		return getProductorSerieVO(qual.toString());
	}

	public List getProductoresXIdSerieYIdProc(final String idSerie,
			final String idProc) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPROCEDIMIENTO,
						idProc)).toString();

		return getProductoresSerieVO(qual);

	}

	public List getProductoresXIdSerie(String idSerie, String procedimiento,
			boolean orderByDate) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_IDSERIE, idSerie));
		if (procedimiento != null)
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_IDPROCEDIMIENTO,
							procedimiento));

		return getProductoresSerieVO(qual.toString(), orderByDate);
	}

	/**
	 * Recupera los productores de una serie
	 * 
	 * @param idSerie
	 *            Identificador de serie documental
	 * @param procedimiento
	 *            Procedimiento al que debe corresponder la documentación
	 *            producida para la serie
	 * @return Lista de productores
	 */
	public List getProductoresXIdSerie(String idSerie, String procedimiento) {
		return getProductoresXIdSerie(idSerie, procedimiento, false);
	}

	public List getProductoresVigentesBySerie(String idSerie) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie)).append(
				" AND " + TABLE_NAME_PRODUCTORSERIE + "."
						+ CAMPO_FECHAINICIO.getName() + " IS NOT NULL AND "
						+ TABLE_NAME_PRODUCTORSERIE + "."
						+ CAMPO_FECHAFINAL.getName() + " IS NULL ");

		return getProductoresSerieVO(qual.toString());
	}

	public List getProductoresVigentesOriginalesBySerie(String idSerie) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie)).append(
				" AND " + TABLE_NAME_PRODUCTORSERIE + "."
						+ CAMPO_FECHAINICIO.getName() + " IS NOT NULL "
						+ " AND " + TABLE_NAME_PRODUCTORSERIE + "."
						+ CAMPO_FECHAFINAL.getName() + " IS NULL ");

		return getProductoresSerieVO(qual.toString());
	}

	public List getProductoresHistoricosBySerieFechaFinalMayorQueFecha(
			String idSerie, Date date) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie)).append(
				" AND "
						+ TABLE_NAME_PRODUCTORSERIE
						+ "."
						+ CAMPO_FECHAINICIO.getName()
						+ " IS NOT NULL AND "
						+ CAMPO_FECHAFINAL.getQualifiedName()
						+ " > "
						+ DBUtils.getNativeDateTimeSyntax(getConnection(),
								date, false) + TABLE_NAME_PRODUCTORSERIE + "."
						+ CAMPO_FECHAFINAL.getName() + " IS NOT NULL ");
		return getProductoresSerieHistoricoVO(qual.toString());
	}

	public ProductorSerieVO getProductorVigenteBySerie(String idSerie,
			String idProductor) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPRODUCTOR,
						idProductor))
				.append(" AND " + TABLE_NAME_PRODUCTORSERIE + "."
						+ CAMPO_FECHAINICIO.getName() + " IS NOT NULL AND "
						+ TABLE_NAME_PRODUCTORSERIE + "."
						+ CAMPO_FECHAFINAL.getName() + " IS NULL ");

		return getProductorSerieVO(qual.toString());
	}

	public List getProductoresHistoricosBySerie(String idSerie) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie)).append(
				" AND " + TABLE_NAME_PRODUCTORSERIE + "."
						+ CAMPO_FECHAINICIO.getName() + " IS NOT NULL AND "
						+ TABLE_NAME_PRODUCTORSERIE + "."
						+ CAMPO_FECHAFINAL.getName() + " IS NOT NULL ");
		return getProductoresSerieHistoricoVO(qual.toString());
	}

	public ProductorSerieVO getProductorHistoricoBySerie(String idSerie,
			String idProductor) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPRODUCTOR,
						idProductor))
				.append(" AND " + TABLE_NAME_PRODUCTORSERIE + "."
						+ CAMPO_FECHAINICIO.getName() + " IS NOT NULL AND "
						+ TABLE_NAME_PRODUCTORSERIE + "."
						+ CAMPO_FECHAFINAL.getName() + " IS NOT NULL ");
		return getProductorSerieVO(qual.toString());
	}

	/**
	 * Recupera los productores de un clasificador de series
	 * 
	 * @param idClfSeries
	 *            Identificador del clasificador de series
	 * @return Lista de productores
	 */
	public abstract List getProductoresXIdClasificadorSeries(String idClfSeries);

	public ProductorSerieVO insertProductorVGSerieVO(
			final ProductorSerieVO productorVO) {

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEFS_PRODUCTOR_SERIE, productorVO);
				DbInsertFns.insert(conn, TABLE_NAME_PRODUCTORSERIE,
						COLUMN_NAMES_PRODUCTORSERIE, inputRecord);
			}
		};
		command.execute();

		return productorVO;
	}

	public void updateIdProcOrgProductor(final ProductorSerieVO productorVO,
			final String newIdProcedimiento) {
		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDSERIE,
						productorVO.getIdserie()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPROCEDIMIENTO,
						productorVO.getIdprocedimiento()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPRODUCTOR,
						productorVO.getId()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPOPRODUCTOR,
						productorVO.getTipo())).toString();

		productorVO.setIdprocedimiento(newIdProcedimiento);

		updateVO(qual, TABLE_NAME_PRODUCTORSERIE, COLS_DEFS_PRODUCTOR_SERIE,
				productorVO);
	}

	public int countProductoresByIdListaAcceso(String idListaAcceso) {

		final String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_LISTAACCESO,
						idListaAcceso)).toString();

		return getVOCount(qual.toString(), TABLE_NAME_PRODUCTORSERIE);

	}

	public void updateByIdSerieIdProductor(ProductorSerieVO productorVO) {
		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDSERIE,
						productorVO.getIdserie()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPRODUCTOR,
						productorVO.getId())).toString();

		updateVO(qual, TABLE_NAME_PRODUCTORSERIE, COLS_DEFS_PRODUCTOR_SERIE,
				productorVO);

	}

	public void updateOrgProductor(final ProductorSerieVO productorVO) {
		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDSERIE,
						productorVO.getIdserie()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPROCEDIMIENTO,
						productorVO.getIdprocedimiento()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPRODUCTOR,
						productorVO.getId()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPOPRODUCTOR,
						productorVO.getTipoProductor())).toString();
		updateVO(qual, TABLE_NAME_PRODUCTORSERIE, COLS_DEFS_PRODUCTOR_SERIE,
				productorVO);
	}

	public void delete(ProductorSerieVO productorVGVO) {
		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDSERIE,
						productorVGVO.getIdserie()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPROCEDIMIENTO,
						productorVGVO.getIdprocedimiento()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPRODUCTOR,
						productorVGVO.getId()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPOPRODUCTOR,
						productorVGVO.getTipo())).toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME_PRODUCTORSERIE,
						qual.toString());

			}

		};
		command.execute();

	}

	/**
	 * Recupera los identificadores de los procedimientos tramitados por un
	 * determinado organo productor o por un determinado tipo de productor
	 * 
	 * @param idorgremitente
	 *            Identificador de organo. Caso de ser nulo se recuperan todos
	 *            los procedimientos que son producidos por un productor del
	 *            tipo indicado
	 * @tipoProductor Tipo de productor
	 * @return Lista de identificadores de procedimiento
	 */
	public List getProcedimientosXProductor(String idProductor,
			int tipoProductor) {
		final StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_TIPOPRODUCTOR,
						tipoProductor))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_FECHAFINAL, null))
				.append(DBUtils.AND)
				.append(DBUtils.generateIsNotNullCondition(getConnection(),
						CAMPO_IDPROCEDIMIENTO));

		if (idProductor != null) {
			qual.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(CAMPO_IDPRODUCTOR,
							idProductor));
		}

		final ArrayList procedimientos = new ArrayList();
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				SingleValueDBOutputRecordSet outputRecord = new SingleValueDBOutputRecordSet(
						CAMPO_IDPROCEDIMIENTO);
				DbSelectFns.select(conn, TABLE_NAME_PRODUCTORSERIE,
						IDPROCEDIMIENTO_COLUMN_NAME, qual.toString(), true,
						outputRecord);
				procedimientos.addAll(outputRecord.getValues());
			}
		};
		command.execute();
		return procedimientos;
	}

	/**
	 * Recupera los identificadores de los procedimientos tramitados por un
	 * determinado organo productor o por un determinado tipo de productor
	 * 
	 * @param idorgremitente
	 *            Identificador de organo. Caso de ser nulo se recuperan todos
	 *            los procedimientos que son producidos por un productor del
	 *            tipo indicado
	 * @tipoProductor Tipo de productor
	 * @return Lista de identificadores de procedimiento
	 */
	public List getIdsSeriesXProductor(String idProductor, int tipoProductor) {

		/*
		 * SELECT DISTINCT asgfserie.IDELEMENTOCF FROM asgfprodserie left outer
		 * join asgfserie ON asgfserie.IDELEMENTOCF = asgfprodserie.IDSERIE
		 * WHERE asgfprodserie.tipoproductor = <tipoProductor> AND
		 * asgfprodserie.fechafinal IS NULL --AND asgfprodserie.idprocedimiento
		 * IS NOT NULL AND asgfprodserie.idproductor = <idProductor>
		 */

		JoinDefinition[] join = new JoinDefinition[] { new JoinDefinition(
				CAMPO_IDSERIE, SerieDBEntityImpl.CAMPO_ID) };

		final StringBuffer select = new StringBuffer(DBUtils.SELECT)
				.append(SerieDBEntityImpl.CAMPO_ID.getQualifiedName())
				.append(DBUtils.FROM)
				.append(DBUtils.generateLeftOuterJoinCondition(new TableDef(
						TABLE_NAME_PRODUCTORSERIE), join));

		final StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_TIPOPRODUCTOR,
						tipoProductor)).append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_FECHAFINAL, null));

		if (idProductor != null) {
			qual.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(CAMPO_IDPRODUCTOR,
							idProductor));
		}

		final String sqlCompleta = select + " " + qual;

		final ArrayList series = new ArrayList();
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				SingleValueDBOutputRecordSet outputRecord = new SingleValueDBOutputRecordSet(
						CAMPO_IDSERIE);

				DbSelectFns.select(conn, sqlCompleta, outputRecord);

				// DbSelectFns.select(
				// conn,
				// TABLE_NAME_PRODUCTORSERIE,
				// SerieDBEntityImpl.IDELEMENTOCF_COLUMN_NAME,
				// qual.toString(),
				// true,
				// outputRecord);
				series.addAll(outputRecord.getValues());
			}
		};
		command.execute();
		return series;
	}

	public List getProductoresSerieXIdProductor(String idProductor) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_IDPRODUCTOR, idProductor));

		return getProductoresSerieVO(qual.toString());
	}

	public List getProductorSerie(String idSerie, String idLista, String nombre) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						DescriptorDBEntityImpl.CAMPO_NOMBRE, nombre))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						DescriptorDBEntityImpl.CAMPO_IDLISTA, idLista));
		;

		return getProductoresSerieVO(qual.toString());
	}

	/**
	 * Elimina los productores que han sido definidos para una serie
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 */
	public void deleteProductoresSerie(String idSerie) {
		final String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie))
				.toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME_PRODUCTORSERIE, qual);
			}
		};

		command.execute();
	}

	/**
	 * Elimina los productores vigentes que han sido definidos para una serie
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 */
	public void deleteProductoresVigentesSerie(String idSerie) {
		final String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie))
				.append(DBUtils.AND)
				.append(DBUtils.generateIsNullCondition(getConnection(),
						CAMPO_FECHAFINAL)).toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME_PRODUCTORSERIE, qual);
			}
		};

		command.execute();
	}

	public void delete(String idSerie, String idProductor) {
		final String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPRODUCTOR,
						idProductor)).toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME_PRODUCTORSERIE, qual);
			}
		};

		command.execute();
	}

	/**
	 * Establece / actualiza la lista de control de acceso del productor de una
	 * serie
	 * 
	 * @param idSerie
	 * @param idListaControlAcceso
	 */
	public void setListaControlAcceso(String idProductor, String idSerie,
			String idListaControlAcceso) {
		final StringBuffer qual = new StringBuffer();

		qual.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPRODUCTOR,
						idProductor)).append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie));

		Map columnsToUpdate = new HashMap();
		columnsToUpdate.put(CAMPO_LISTAACCESO, idListaControlAcceso);

		updateFields(qual.toString(), columnsToUpdate,
				TABLE_NAME_PRODUCTORSERIE);

		// updateVO(qual, TABLE_NAME_PRODUCTORSERIE, COLS_DEFS_PRODUCTOR_SERIE,
		// productorVO);
	}

	public void setMarcaProductores(String idSerie, int marca) {
		final StringBuffer qual = new StringBuffer();
		qual.append(" WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie));

		Map columnsToUpdate = new HashMap();
		columnsToUpdate.put(CAMPO_MARCAS, String.valueOf(marca));
		updateFields(qual.toString(), columnsToUpdate,
				TABLE_NAME_PRODUCTORSERIE);
	}

}
