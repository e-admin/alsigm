/**
 *
 */
package fondos.db;


import fondos.vos.TablaTemporalFondosVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbTableFns;
import ieci.core.db.DbUtil;

import java.util.HashMap;

import common.Constants;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.TableDef;

import deposito.db.UDocEnUiDepositoDbEntityImpl;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class TablaTemporalDBEntityImpl extends DBEntity implements
		ITablaTemporalDBEntity {

	public static final String IDUSUARIO_COLUMN_NAME = "IDUSUARIO";
	public static final String IDENTIFICADOR_COLUMN_NAME = "IDPADRE";
	public static final String IDELEMENTOCF_COLUMN_NAME = "IDELEMENTOCF";
	public static final String CODIGO_COLUMN_NAME = "CODIGO";
	public static final String IDUINSTALACION_COLUMN_NAME = "IDUINSTALACION";

	/**
	 * @param dataSource
	 */
	public TablaTemporalDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public TablaTemporalDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public static final String TABLE_PREFFIX = "ASGFTMP";

	/**
	 * Obtiene el nombre de la tabla
	 *
	 * @return
	 */
	public String getTableName() {
		return TABLE_PREFFIX;
	}

	private String getNombreTabla(int numeroTabla) {
		return TABLE_PREFFIX + numeroTabla;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see fondos.db.ITablaTemporalDBEntity#insertAsSelect(java.lang.String)
	 */
	public void insertAsSelect(final TablaTemporalFondosVO tablaTemporal,
			final String[] ids) {
		DbColumnDef IDUSUARIO_FIELD = new DbColumnDef(null,
				tablaTemporal.getNombreTabla(), IDUSUARIO_COLUMN_NAME,
				DbDataType.SHORT_TEXT, 32, false);

		DbColumnDef IDENTIFICADOR_FIELD = new DbColumnDef(null,
				tablaTemporal.getNombreTabla(), IDENTIFICADOR_COLUMN_NAME,
				DbDataType.SHORT_TEXT, 32, false);

		DbColumnDef IDUNIDAD_FIELD = new DbColumnDef(null,
				tablaTemporal.getNombreTabla(), IDELEMENTOCF_COLUMN_NAME,
				DbDataType.SHORT_TEXT, 32, false);

		DbColumnDef TEXTO_FIELD = new DbColumnDef(null,
				tablaTemporal.getNombreTabla(), CODIGO_COLUMN_NAME,
				DbDataType.SHORT_TEXT, 128, false);

		DbColumnDef IDUI_FIELD = new DbColumnDef(null,
				tablaTemporal.getNombreTabla(), IDUINSTALACION_COLUMN_NAME,
				DbDataType.SHORT_TEXT, 32, false);

		final String colNames = DbUtil.getColumnNames(new DbColumnDef[] {
				IDUSUARIO_FIELD, IDENTIFICADOR_FIELD, IDUNIDAD_FIELD,
				TEXTO_FIELD, IDUI_FIELD });

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {

				/*
				* SELECT ´IDUSUARIO´ AS IDUSUARIO, ´ID´ AS IDENTIFICADOR,
				 * DI.IDUNIDADDOC , F.CODIGO ,DI.IDUINSTALACION FROM
				 * ASGDUDOCENUI DI LEFT OUTER JOIN ASGFELEMENTOCF F ON F.ID =
				 * DI.IDUNIDADDOC WHERE DI.IDUNIDADDOC IN( SELECT IDELEMENTOCF
				 * FROM ASGFTMP3 ) ;
				 */

				/*
				* SELECT '1', '0eecf4091add000000000000000009e4', asgfelementocf.id,
       asgfelementocf.codigo, asgdudocenui.iduinstalacion
FROM asgfelementocf asgfelementocf
LEFT OUTER JOIN asgdudocenui asgdudocenui
    ON asgdudocenui.idunidaddoc = asgfelementocf.ID
				*
				*
				*/

				JoinDefinition[] joins = new JoinDefinition[] {

				new JoinDefinition(
						ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD
				) };

				StringBuffer selectClause =

				new StringBuffer(DBUtils.SELECT)
						.append(DBUtils.getValorCadenaSql(tablaTemporal.getIdUsuario()))
						.append(Constants.COMMA)
						.append(DBUtils.getValorCadenaSql(tablaTemporal.getIdentificador()))
						.append(Constants.COMMA)
						.append(ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD.getQualifiedName())
						.append(Constants.COMMA)
						.append(ElementoCuadroClasificacionDBEntityImplBase.CODIGO_FIELD.getQualifiedName())
						.append(Constants.COMMA)
						.append(DBUtils.getNativeIfNullSintax(getConnection() ,UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD
								.getQualifiedName() , DBUtils.getValorCadena(Constants.GUION))
								)
						.append(DBUtils.FROM)
						.append(DBUtils
								.generateLeftOuterJoinCondition(
										new TableDef(
												ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO),
										joins))
						.append(DBUtils.WHERE)
						.append(DBUtils.generateInTokenField(
								ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
								ids));

				DbInsertFns.insertAsSelect(conn,
						tablaTemporal.getNombreTabla(), colNames,
						selectClause.toString());
			}
		};
		command.execute();

	}

	public void createTable(int numeroTabla) throws Exception {

		DbColumnDef IDUSUARIO_FIELD = new DbColumnDef(null,
				getNombreTabla(numeroTabla), IDUSUARIO_COLUMN_NAME,
				DbDataType.SHORT_TEXT, 32, false);

		DbColumnDef IDENTIFICADOR_FIELD = new DbColumnDef(null,
				getNombreTabla(numeroTabla), IDENTIFICADOR_COLUMN_NAME,
				DbDataType.SHORT_TEXT, 32, false);

		DbColumnDef IDUNIDAD_FIELD = new DbColumnDef(null,
				getNombreTabla(numeroTabla), IDELEMENTOCF_COLUMN_NAME,
				DbDataType.SHORT_TEXT, 32, false);

		DbColumnDef TEXTO_FIELD = new DbColumnDef(null,
				getNombreTabla(numeroTabla), CODIGO_COLUMN_NAME,
				DbDataType.SHORT_TEXT, 32, false);

		DbColumnDef IDUI_FIELD = new DbColumnDef(null,
				getNombreTabla(numeroTabla), IDUINSTALACION_COLUMN_NAME,
				DbDataType.SHORT_TEXT, 32, false);

		DbColumnDef[] COLUMNS = new DbColumnDef[] { IDUSUARIO_FIELD,
				IDENTIFICADOR_FIELD, IDUNIDAD_FIELD, TEXTO_FIELD, IDUI_FIELD };

		DbTableFns.createTable(getConnection(), getNombreTabla(numeroTabla),
				COLUMNS);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws Exception
	 * @see fondos.db.ITablaTemporalDBEntity#truncateTable(java.lang.String)
	 */
	public void truncateTable(String nombreTabla) throws Exception {
		deleteVO("", nombreTabla);
	}

	public static String getSelectCodigoFromTemporal(
			TablaTemporalFondosVO tablaTemporalFondosVO) {
		return getSelect(tablaTemporalFondosVO, CODIGO_COLUMN_NAME);
	}

	public static String getSelectIdElementocfFromTemporal(
			TablaTemporalFondosVO tablaTemporalFondosVO) {
		return getSelect(tablaTemporalFondosVO, IDELEMENTOCF_COLUMN_NAME);
	}

	public static String getSelectIdUinstalacionElementocfFromTemporal(
			TablaTemporalFondosVO tablaTemporalFondosVO) {
		return getSelect(tablaTemporalFondosVO, IDUINSTALACION_COLUMN_NAME);
	}

	private static String getSelect(
			TablaTemporalFondosVO tablaTemporalFondosVO, String campo) {

		StringBuffer sql = new StringBuffer(DBUtils.SELECT).append(campo)
				.append(DBUtils.FROM)
				.append(tablaTemporalFondosVO.getNombreTabla())
				.append(getWhere(tablaTemporalFondosVO));
		return sql.toString();
	}

	private static String getWhere(TablaTemporalFondosVO tablaTemporalFondosVO) {
		StringBuffer where = new StringBuffer(DBUtils.WHERE)
				.append(IDUSUARIO_COLUMN_NAME).append("=")
				.append(DBUtils.getValorCadenaSql(tablaTemporalFondosVO.getIdUsuario()))
				.append(DBUtils.AND).append(IDENTIFICADOR_COLUMN_NAME)
				.append("=").append(DBUtils.getValorCadenaSql(tablaTemporalFondosVO.getIdentificador()))
				;

		return where.toString();

	}

	public HashMap getPairsIdCodigo(TablaTemporalFondosVO tablaTemporalFondosVO) {
		DbColumnDef IDUNIDAD_FIELD = new DbColumnDef(null,
				tablaTemporalFondosVO.getNombreTabla(),
				IDELEMENTOCF_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

		DbColumnDef TEXTO_FIELD = new DbColumnDef(null,
				tablaTemporalFondosVO.getNombreTabla(), CODIGO_COLUMN_NAME,
				DbDataType.SHORT_TEXT, 32, false);

		return getPairsIdValue(getWhere(tablaTemporalFondosVO),
				tablaTemporalFondosVO.getNombreTabla(), IDUNIDAD_FIELD,
				TEXTO_FIELD);
	}

	public void deleteByUsuarioAndIdPadre(TablaTemporalFondosVO tablaTemporal) {
		deleteVO(getWhere(tablaTemporal), tablaTemporal.getNombreTabla());
	}


}
