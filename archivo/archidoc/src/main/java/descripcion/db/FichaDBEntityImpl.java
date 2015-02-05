package descripcion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbSelectStatement;
import ieci.core.db.DbTableDef;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;

import java.util.Iterator;
import java.util.List;

import transferencias.db.RelacionEntregaDBEntityBaseImpl;

import common.Constants;
import common.db.DBEntityKeyValue;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.definitions.ArchivoTables;
import common.exceptions.DBException;
import common.util.ListUtils;
import common.vos.IKeyValue;

import descripcion.model.TipoNorma;
import descripcion.vos.FichaVO;
import descripcion.vos.InfoBFichaVO;
import fondos.db.DivisionFSDBEntityImpl;
import fondos.db.ElementoCuadroClasificacionDBEntityImplBase;
import fondos.db.NivelCFDBEntityImpl;
import fondos.db.SerieDBEntityImpl;
import fondos.vos.INivelCFVO;

/**
 * DBEntity para acceder a la tabla ADFICHA.
 */
public class FichaDBEntityImpl extends DBEntityKeyValue implements
		IFichaDBEntity {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = ArchivoTables.ADFICHA_TABLE_NAME;

	/** Nombre de columnas */
	public static final String ID_COLUMN_NAME = "id";
	public static final String NOMBRE_COLUMN_NAME = "nombre";
	public static final String DEFINICION_COLUMN_NAME = "definicion";
	public static final String TIPONORMA_COLUMN_NAME = "tiponorma";
	public static final String TIPONIVEL_COLUMN_NAME = "tiponivel";
	public static final String SUBTIPONIVEL_COLUMN_NAME = "subtiponivel";
	public static final String DESCRIPCION_COLUMN_NAME = "descripcion";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);
	public static final DbColumnDef CAMPO_DEFINICION = new DbColumnDef(null,
			TABLE_NAME, DEFINICION_COLUMN_NAME, DbDataType.LONG_TEXT, 0, false);
	public static final DbColumnDef CAMPO_TIPONORMA = new DbColumnDef(null,
			TABLE_NAME, TIPONORMA_COLUMN_NAME, DbDataType.SHORT_INTEGER, 0,
			false);
	public static final DbColumnDef CAMPO_TIPONIVEL = new DbColumnDef(null,
			TABLE_NAME, TIPONIVEL_COLUMN_NAME, DbDataType.SHORT_INTEGER, 0,
			false);
	public static final DbColumnDef CAMPO_SUBTIPONIVEL = new DbColumnDef(null,
			TABLE_NAME, SUBTIPONIVEL_COLUMN_NAME, DbDataType.SHORT_INTEGER, 0,
			false);
	public static final DbColumnDef CAMPO_DESCRIPCION = new DbColumnDef(null,
			TABLE_NAME, DESCRIPCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			true);

	// Campo para join con listas descriptoras
	public static final DbColumnDef CAMPO_NOMBRE_FICHA_DESCR_PREF = new DbColumnDef(
			"nombreFichaDescrPref", TABLE_NAME, NOMBRE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 64, false);

	// Campo para join con descriptores
	public static final DbColumnDef CAMPO_NOMBRE_FICHA_DESCR = new DbColumnDef(
			"nombreFichaDescr", TABLE_NAME, NOMBRE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 64, false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] { CAMPO_ID,
			CAMPO_NOMBRE, CAMPO_DEFINICION, CAMPO_TIPONORMA, CAMPO_TIPONIVEL,
			CAMPO_SUBTIPONIVEL, CAMPO_DESCRIPCION };

	/** Lista de columnas reducida. */
	public static final DbColumnDef[] COL_DEFS_REDUCIDA = new DbColumnDef[] {
			CAMPO_ID, CAMPO_NOMBRE, CAMPO_TIPONORMA, CAMPO_TIPONIVEL,
			CAMPO_SUBTIPONIVEL, CAMPO_DESCRIPCION };

	/** Lista de nombres de columnas. */
	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(COL_DEFS);

	private static final String ALIAS_CONTADOR = "CONTADOR";
	private static final String ALIAS_UNIONES = "UNIONES";

	/**
	 * Obtiene el nombre de la tabla
	 *
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Constructor.
	 *
	 * @param dataSource
	 *            Pool de conexiones de base de datos.
	 */
	public FichaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public FichaDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la información de una ficha.
	 *
	 * @param id
	 *            Identificador de la ficha.
	 * @return Información de la ficha.
	 */
	public FichaVO getFicha(String id) {
		StringBuffer qual = new StringBuffer().append("WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_ID, id));

		return (FichaVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
				FichaVO.class);
	}

	public FichaVO getFichaByName(String nombreFicha) {
		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(CAMPO_NOMBRE, nombreFicha, true));

		return (FichaVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
				FichaVO.class);
	}

	/**
	 * Obtiene la lista de fichas
	 *
	 * @return una lista de fichas
	 */
	public List getFichas() {

		return getVOS("", TABLE_NAME, COL_DEFS, FichaVO.class);
	}

	/**
	 * Obtiene la información básica de una ficha.
	 *
	 * @param id
	 *            Identificador de la ficha.
	 * @return Información básica de la ficha.
	 */
	public InfoBFichaVO getInfoBFicha(String id) {
		StringBuffer qual = new StringBuffer().append("WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_ID, id));

		return (InfoBFichaVO) getVO(qual.toString(), TABLE_NAME,
				COL_DEFS_REDUCIDA, InfoBFichaVO.class);
	}

	/**
	 * Obtiene una lista de fichas de unos tipos de nivel determinados.
	 *
	 * @param tiposNivel
	 *            Tipos de nivel.
	 * @return Lista de fichas ({@link InfoBFichaVO}).
	 */
	public List getFichasByTiposNivel(int[] tiposNivel) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateInTokenField(CAMPO_TIPONIVEL,
						tiposNivel)).append(" ORDER BY ")
				.append(CAMPO_NOMBRE.getQualifiedName());

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS_REDUCIDA,
				InfoBFichaVO.class);
	}

	/**
	 * Obtiene una lista de fichas de unos tipos de nivel determinados.
	 *
	 * @param idsTipos
	 *            Tipos de nivel.
	 * @return Lista de fichas ({@link InfoBFichaVO}).
	 */
	public List getFichasByTiposNivelIdFichaPref(List niveles) {
		StringBuffer qual = new StringBuffer().append(Constants.STRING_EMPTY);

		if (ListUtils.isNotEmpty(niveles)) {
			for (Iterator iterator = niveles.iterator(); iterator.hasNext();) {
				INivelCFVO nivelCFVO = (INivelCFVO) iterator.next();

				if (nivelCFVO != null) {
					qual.append(DBUtils.getOrCondition(qual.toString()))
							.append(DBUtils.ABRIR_PARENTESIS)
							.append(DBUtils.generateEQTokenField(
									CAMPO_TIPONIVEL, nivelCFVO.getTipo()))
							.append(DBUtils.AND)
							.append(DBUtils.generateEQTokenField(
									CAMPO_SUBTIPONIVEL, nivelCFVO.getSubtipo()))
							.append(DBUtils.CERRAR_PARENTESIS);
				}
			}
		}

		qual.append(DBUtils.generateOrderBy(CAMPO_NOMBRE));

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS_REDUCIDA,
				InfoBFichaVO.class);
	}

	/**
	 * Obtiene la lista de fichas de un tipo de norma.
	 *
	 * @param tipoNorma
	 *            Tipo de norma ({@link TipoNorma}).
	 * @return Lista de fichas ({@link InfoBFichaVO}).
	 */
	public List getFichasByTipoNorma(int tipoNorma) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils
						.generateEQTokenField(CAMPO_TIPONORMA, tipoNorma))
				.append(" ORDER BY ").append(CAMPO_NOMBRE.getQualifiedName());

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS_REDUCIDA,
				InfoBFichaVO.class);
	}

	/**
	 * Obtiene la lista de fichas de un tipo de norma y unos tipo de nivel
	 * determinados.
	 *
	 * @param tipoNorma
	 *            Tipo de norma ({@link TipoNorma}).
	 * @param tiposNivel
	 *            Tipos de nivel.
	 * @return Lista de fichas ({@link InfoBFichaVO}).
	 */
	public List getFichasByTipoNormaYNiveles(int tipoNorma, int[] tiposNivel) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils
						.generateEQTokenField(CAMPO_TIPONORMA, tipoNorma))
				.append(" OR ")
				.append(DBUtils.generateInTokenField(CAMPO_TIPONIVEL,
						tiposNivel)).append(" ORDER BY ")
				.append(CAMPO_NOMBRE.getQualifiedName());

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS_REDUCIDA,
				InfoBFichaVO.class);
	}

	/**
	 * Inserta una nueva ficha.
	 *
	 * @param ficha
	 * @return FichaVO
	 */
	public FichaVO createFicha(FichaVO ficha) {
		DbConnection conn = getConnection();

		try {
			ficha.setId(getGuid(ficha.getId()));
			DbInsertFns.insert(conn, TABLE_NAME, DbUtil
					.getColumnNames(COL_DEFS), new SigiaDbInputRecord(COL_DEFS,
					ficha));
			return ficha;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBException(e.getMessage());
		}
	}

	/**
	 * Actualiza la ficha pasada por parametro.
	 *
	 * @param ficha
	 * @return FichaVO
	 */
	public FichaVO updateFicha(FichaVO fichaVO) {
		try {
			DbConnection conn = getConnection();
			final String qual = new StringBuffer()
					.append(" WHERE ")
					.append(DBUtils.generateEQTokenField(CAMPO_ID,
							fichaVO.getId())).toString();

			DbUpdateFns.update(conn, TABLE_NAME, COLUM_NAMES_LIST,
					new SigiaDbInputRecord(COL_DEFS, fichaVO), qual.toString());
			return fichaVO;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBException(e.getMessage());
		}
	}

	/**
	 * Elimina las fichas correspondientes a los ids pasados por parametro
	 *
	 * @param String
	 *            [] idsFichas
	 */
	public void deleteFichas(String[] idsFichas) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateInTokenField(CAMPO_ID, idsFichas))
				.toString();

		if (logger.isDebugEnabled())
			logger.debug("Se van a eliminar las fichas con ids: " + idsFichas);

		deleteVO(qual, TABLE_NAME);
	}

	/**
	 * Comprueba en todas las tablas de la base de datos que utilizan la ficha
	 * si hay elementos que usan la ficha con los ids pasados como parametro.
	 *
	 * @param idsFichas
	 * @return
	 */
	public int countElementosUsoFicha(String[] idsFichas) {
		/*
		 * SELECT COUNT(CONTADOR) FROM ( SELECT COUNT(ID) CONTADOR FROM
		 * ASGTRENTREGA WHERE IDFICHA IN ('5') UNION SELECT COUNT(IDFS) CONTADOR
		 * FROM ASGFDIVISIONFS WHERE IDFICHA IN ('5') UNION SELECT COUNT(ID)
		 * CONTADOR FROM ADCTLGLISTAD WHERE IDFICHADESCRPREF IN ('5') .... )
		 * UNIONES WHERE CONTADOR <> 0
		 */

		String selectRentrega = DbSelectStatement
				.getSelectStmtText(
						RelacionEntregaDBEntityBaseImpl.TABLE_NAME,
						DbUtil.getColumnNamesWithAlias(new DbColumnDef[] { new DbColumnDef(
								ALIAS_CONTADOR,
								DbUtil.getCountColumn(RelacionEntregaDBEntityBaseImpl.CAMPO_ID)) }),
						DBUtils.WHERE
								+ DBUtils
										.generateInTokenField(
												RelacionEntregaDBEntityBaseImpl.CAMPO_IDFICHA,
												idsFichas), false);

		String selectListaDescriptora = DbSelectStatement
				.getSelectStmtText(
						CatalogoListaDescriptoresDBEntityImpl.TABLE_NAME,
						DbUtil.getColumnNamesWithAlias(new DbColumnDef[] { new DbColumnDef(
								ALIAS_CONTADOR,
								DbUtil.getCountColumn(CatalogoListaDescriptoresDBEntityImpl.CAMPO_ID)) }),
						DBUtils.WHERE
								+ DBUtils
										.generateInTokenField(
												CatalogoListaDescriptoresDBEntityImpl.CAMPO_ID_FICHA_DESCR_PREF,
												idsFichas), false);

		String selectDescriptor = DbSelectStatement
				.getSelectStmtText(
						DescriptorDBEntityImpl.TABLE_NAME,
						DbUtil.getColumnNamesWithAlias(new DbColumnDef[] { new DbColumnDef(
								ALIAS_CONTADOR,
								DbUtil.getCountColumn(DescriptorDBEntityImpl.CAMPO_ID)) }),
						DBUtils.WHERE
								+ DBUtils
										.generateInTokenField(
												DescriptorDBEntityImpl.CAMPO_ID_FICHA_DESCR,
												idsFichas), false);

//		String selectMapDescriptorUdoc = DbSelectStatement
//				.getSelectStmtText(
//						MapDescUDocDBEntityImpl.TABLE_NAME,
//						DbUtil.getColumnNamesWithAlias(new DbColumnDef[] { new DbColumnDef(
//								ALIAS_CONTADOR,
//								DbUtil.getCountColumn(MapDescUDocDBEntityImpl.CAMPO_ID_FICHA)) }),
//						DBUtils.WHERE
//								+ DBUtils.generateInTokenField(
//										MapDescUDocDBEntityImpl.CAMPO_ID_FICHA,
//										idsFichas), false);

		String selectNivelCF = DbSelectStatement
				.getSelectStmtText(
						NivelCFDBEntityImpl.NIVELCF_TABLE_NAME,
						DbUtil.getColumnNamesWithAlias(new DbColumnDef[] { new DbColumnDef(
								ALIAS_CONTADOR,
								DbUtil.getCountColumn(NivelCFDBEntityImpl.ID_NIVEL_FIELD)) }),
						DBUtils.WHERE
								+ DBUtils.generateInTokenField(
										NivelCFDBEntityImpl.ID_FICHA_FIELD,
										idsFichas), false);

		String selectElementoCF = DbSelectStatement
				.getSelectStmtText(
						ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
						DbUtil.getColumnNamesWithAlias(new DbColumnDef[] { new DbColumnDef(
								ALIAS_CONTADOR,
								DbUtil.getCountColumn(ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD)) }),
						DBUtils.WHERE
								+ DBUtils
										.generateInTokenField(
												ElementoCuadroClasificacionDBEntityImplBase.IDFICHADESCR_FIELD,
												idsFichas), false);

		StringBuffer sentenciaWhere = new StringBuffer().append(DBUtils.WHERE);
		for (int i = 0; i < idsFichas.length; i++) {
			sentenciaWhere.append(DBUtils.generateLikeTokenField(
					SerieDBEntityImpl.CAMPO_INFO_FICHA_UDOC_REP_ECM,
					"<ID_FICHADESCRPREFUDOC>" + idsFichas[i]
							+ "</ID_FICHADESCRPREFUDOC>"));
			if (++i < idsFichas.length) {
				sentenciaWhere.append(DBUtils.OR);
				--i;
			}
		}
		String selectSerie = DbSelectStatement
				.getSelectStmtText(
						SerieDBEntityImpl.TABLE_NAME_SERIE,
						DbUtil.getColumnNamesWithAlias(new DbColumnDef[] { new DbColumnDef(
								ALIAS_CONTADOR,
								DbUtil.getCountColumn(SerieDBEntityImpl.CAMPO_ID)) }),
						sentenciaWhere.toString(), false);

		String selectDivisionFS = DbSelectStatement
				.getSelectStmtText(
						DivisionFSDBEntityImpl.TABLE_NAME_DIVISIONFS,
						DbUtil.getColumnNamesWithAlias(new DbColumnDef[] { new DbColumnDef(
								ALIAS_CONTADOR,
								DbUtil.getCountColumn(DivisionFSDBEntityImpl.CAMPO_IDFS)) }),
						DBUtils.WHERE
								+ DBUtils.generateInTokenField(
										DivisionFSDBEntityImpl.CAMPO_ID_FICHA,
										idsFichas), false);

		String uniones = DBUtils.generateUNIONTokens(new String[] {
				selectRentrega, selectListaDescriptora, selectDescriptor,
				 selectNivelCF, selectElementoCF,
				selectSerie, selectDivisionFS });
		DbTableDef table = new DbTableDef(uniones, ALIAS_UNIONES);

		StringBuffer qual = new StringBuffer();
		qual.append(DBUtils.WHERE);
		qual.append(DBUtils.generateNEQTokenField(new DbColumnDef(
				ALIAS_CONTADOR, DbDataType.SHORT_INTEGER, false), 0));

		try {
			return DbSelectFns.selectCount(getConnection(),
					table.getAliasedName(), ALIAS_CONTADOR, qual.toString(),
					false);
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	public DbColumnDef getKeyField() {
		return CAMPO_ID;
	}

	public IKeyValue getByKey(String key) {
		return getFicha(key);
	}

	public DbColumnDef getValueField() {
		return CAMPO_NOMBRE;
	}

	public IKeyValue getByValue(String value) {
		return getFichaByName(value);
	}
}