package descripcion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import java.util.List;

import transferencias.vos.InteresadoUdocVO;
import xml.config.ConfiguracionDescripcion;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.TableDef;

import descripcion.vos.RangoVO;
import descripcion.vos.ValorCampoGenericoVOBase;

/**
 * DBEntity para acceder a la tabla ADVCTEXTUDOCRE.
 */
public class TextoCortoUDocREDBEntityImpl extends TextoDBEntityImplBase
		implements ITextoDBEntity, ITextoCortoUdocREDBEntity {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ADVCTEXTUDOCRE";

	/** Nombre de columnas */
	public static final String IDUNIDADDOCRE_COLUMN_NAME = "idudocre";
	public static final String TIPOUDOC_COLUMN_NAME = "tipoudoc";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID_ELEMENTO = new DbColumnDef(
			IDOBJETO_COLUMN_ALIAS, TABLE_NAME, IDUNIDADDOCRE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_ID_CAMPO = new DbColumnDef(null,
			TABLE_NAME, IDCAMPO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_VALOR = new DbColumnDef(null,
			TABLE_NAME, VALOR_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);
	public static final DbColumnDef CAMPO_ORDEN = new DbColumnDef(null,
			TABLE_NAME, ORDEN_COLUMN_NAME, DbDataType.LONG_INTEGER, 0, false);
	public static final DbColumnDef CAMPO_TIPOUDOC = new DbColumnDef(null,
			TABLE_NAME, TIPOUDOC_COLUMN_NAME, DbDataType.SHORT_INTEGER, 0,
			false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] {
			CAMPO_ID_ELEMENTO, CAMPO_ID_CAMPO, CAMPO_VALOR, CAMPO_ORDEN,
			CAMPO_TIPOUDOC };

	/** Lista de nombres de columnas. */
	public static final String COLUMN_NAMES_LIST = DbUtil
			.getColumnNames(COL_DEFS);

	/** Hint para agilizar las búsquedas. */
	protected static final String HINT = "/*+INDEX(ADVCTEXTUDOCRE ADVCTEXTUDOCRE1)*/";

	/**
	 * Constructor.
	 * 
	 * @param dataSource
	 *            Pool de conexiones de base de datos.
	 */
	public TextoCortoUDocREDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public TextoCortoUDocREDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public DbColumnDef getCampoIdElemento() {
		return CAMPO_ID_ELEMENTO;
	}

	public DbColumnDef[] getColDefs() {
		return COL_DEFS;
	}

	public String getHint() {
		return HINT;
	}

	public String getColumnNamesList() {
		return COLUMN_NAMES_LIST;
	}

	public DbColumnDef getCampoIdCampo() {
		return CAMPO_ID_CAMPO;
	}

	public DbColumnDef getCampoOrden() {
		return CAMPO_ORDEN;
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	public DbColumnDef getCampoTipoElemento() {
		return CAMPO_TIPOUDOC;
	}

	public DbColumnDef getCampoValor() {
		return CAMPO_VALOR;
	}

	public DbColumnDef getCustomizedField(DbColumnDef column, String alias) {
		return new DbColumnDef(column.getBindPropertyVO(), alias,
				column.getName(), column.getDataType(), column.getMaxLen(),
				column.isNullable());
	}

	/**
	 * Obtiene la lista de rangos de una unidad documental en relación de
	 * entrega
	 * 
	 * @param idUDoc
	 *            Identificador de la unidad documental en la relación
	 * @param idCampoInicial
	 *            Identificador del campo rango inicial
	 * @param idCampoFinal
	 *            Identificador del campo rango final
	 * @return Lista de rangos.
	 */
	public List getRangosUDoc(String idUDoc, String idCampoInicial,
			String idCampoFinal) {

		StringBuffer sqlFrom = new StringBuffer();
		String aliasTextoCorto1 = "TC1", aliasTextoCorto2 = "TC2";
		TableDef tablaTextoCorto1 = new TableDef(TABLE_NAME, aliasTextoCorto1);
		TableDef tablaTextoCorto2 = new TableDef(TABLE_NAME, aliasTextoCorto2);

		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(tablaTextoCorto1, getCustomizedField(
						CAMPO_ID_ELEMENTO, aliasTextoCorto1)), new DbColumnDef(
						tablaTextoCorto2, getCustomizedField(CAMPO_ID_ELEMENTO,
								aliasTextoCorto2))) };

		sqlFrom.append(DBUtils.generateInnerJoinCondition(tablaTextoCorto1,
				joins));

		sqlFrom.append(" AND ")
				.append(DBUtils
						.generateEQTokenField(
								getCustomizedField(CAMPO_ID_ELEMENTO,
										aliasTextoCorto1), idUDoc))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						getCustomizedField(CAMPO_ORDEN, aliasTextoCorto1),
						getCustomizedField(CAMPO_ORDEN, aliasTextoCorto2)))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						getCustomizedField(CAMPO_ID_CAMPO, aliasTextoCorto1),
						idCampoInicial))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						getCustomizedField(CAMPO_ID_CAMPO, aliasTextoCorto2),
						idCampoFinal));

		DbColumnDef[] columns = new DbColumnDef[] {
				new DbColumnDef("rangoInicial", getCustomizedField(CAMPO_VALOR,
						aliasTextoCorto1)),
				new DbColumnDef("rangoFinal", getCustomizedField(CAMPO_VALOR,
						aliasTextoCorto2)), };

		return getVOS(Constants.STRING_EMPTY, sqlFrom.toString(), columns,
				RangoVO.class);
	}

	/*
	 * public void updateValor(String tipo_old, String tipo_new) { String
	 * idSoporteDoc =
	 * ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo()
	 * .getConfiguracionDescripcion().getSoporteDocumental(); StringBuffer qual
	 * = new StringBuffer(DBUtils.WHERE)
	 * .append(DBUtils.generateEQTokenField(CAMPO_VALOR, tipo_old))
	 * .append(" AND ") .append(DBUtils.generateEQTokenField(CAMPO_ID_CAMPO,
	 * idSoporteDoc));
	 * 
	 * Map colsToUpdate = new HashMap(); colsToUpdate.put(CAMPO_VALOR,
	 * tipo_new);
	 * 
	 * updateFields(qual.toString(), colsToUpdate, TABLE_NAME); }
	 */

	private String generateInteresadoPrincipalJoin(String tableAlias,
			int tipoUdoc, String idCampo, String valorPrincipal) {

		StringBuffer sqlJoin = new StringBuffer();
		sqlJoin.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						ReferenciaUDocREDBEntityImpl.CAMPO_ORDEN,
						getCustomizedField(CAMPO_ORDEN, tableAlias)))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						getCustomizedField(CAMPO_TIPOUDOC, tableAlias),
						tipoUdoc))
				.append(" AND ")
				.append(DBUtils
						.generateEQTokenField(
								getCustomizedField(CAMPO_ID_CAMPO, tableAlias),
								idCampo));

		if ((valorPrincipal != null) && (!valorPrincipal.equals(""))) {
			sqlJoin.append(" AND ").append(
					DBUtils.generateEQTokenField(
							getCustomizedField(CAMPO_VALOR, tableAlias),
							valorPrincipal));
		}

		return sqlJoin.toString();

	}

	/**
	 * Obtiene la lista de interesados de varias unidades documentales en una
	 * relación de entrega
	 * 
	 * @param idsUDoc
	 *            Identificador de las unidades documentales en la relación
	 * @param tipoUdoc
	 *            Tipo de unidad documental
	 * @return Lista de interesados principales.
	 */
	public List getInteresadosPrincipales(String[] idsUDoc, int tipoUdoc) {

		ConfiguracionDescripcion configDesc = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion();

		String idCampoInteresadoIdentidad = configDesc.getInteresadoIdentidad();
		String idCampoInteresadoPrincipal = configDesc.getInteresadoPrincipal();
		String idCampoIdTerceroInteresado = configDesc.getIdTerceroInteresado();
		String idCampoInteresadoNumeroIdentidad = configDesc
				.getInteresadoNumeroIdentidad();
		String idCampoInteresadoRol = configDesc.getInteresadoRol();
		String idCampoInteresadoValidado = configDesc.getInteresadoValidado();

		StringBuffer sqlFrom = new StringBuffer();
		String aliasTextoCorto1 = "TC1", aliasTextoCorto2 = "TC2", aliasTextoCorto3 = "TC3", aliasTextoCorto4 = "TC4", aliasTextoCorto5 = "TC5";

		TableDef tablaReferencia = new TableDef(
				ReferenciaUDocREDBEntityImpl.TABLE_NAME);
		TableDef tablaDescriptor = new TableDef(
				DescriptorDBEntityImpl.TABLE_NAME);
		TableDef tablaTextoCorto1 = new TableDef(TABLE_NAME, aliasTextoCorto1);
		TableDef tablaTextoCorto2 = new TableDef(TABLE_NAME, aliasTextoCorto2);
		TableDef tablaTextoCorto3 = new TableDef(TABLE_NAME, aliasTextoCorto3);
		TableDef tablaTextoCorto4 = new TableDef(TABLE_NAME, aliasTextoCorto4);
		TableDef tablaTextoCorto5 = new TableDef(TABLE_NAME, aliasTextoCorto5);

		JoinDefinition joinDescriptor = new JoinDefinition(new DbColumnDef(
				tablaReferencia, ReferenciaUDocREDBEntityImpl.CAMPO_IDOBJREF),
				new DbColumnDef(tablaDescriptor,
						DescriptorDBEntityImpl.CAMPO_ID));

		JoinDefinition joinTextoCorto1 = new JoinDefinition(
				new DbColumnDef(tablaReferencia,
						ReferenciaUDocREDBEntityImpl.CAMPO_ID_ELEMENTO),
				new DbColumnDef(tablaTextoCorto1, getCustomizedField(
						CAMPO_ID_ELEMENTO, aliasTextoCorto1)),
				generateInteresadoPrincipalJoin(aliasTextoCorto1,
						ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_RE,
						idCampoInteresadoPrincipal, Constants.TRUE_FULL_STRING));

		JoinDefinition joinTextoCorto2 = new JoinDefinition(
				new DbColumnDef(tablaReferencia,
						ReferenciaUDocREDBEntityImpl.CAMPO_ID_ELEMENTO),
				new DbColumnDef(tablaTextoCorto2, getCustomizedField(
						CAMPO_ID_ELEMENTO, aliasTextoCorto2)),
				generateInteresadoPrincipalJoin(aliasTextoCorto2,
						ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_RE,
						idCampoIdTerceroInteresado, null));

		JoinDefinition joinTextoCorto3 = new JoinDefinition(
				new DbColumnDef(tablaReferencia,
						ReferenciaUDocREDBEntityImpl.CAMPO_ID_ELEMENTO),
				new DbColumnDef(tablaTextoCorto3, getCustomizedField(
						CAMPO_ID_ELEMENTO, aliasTextoCorto3)),
				generateInteresadoPrincipalJoin(aliasTextoCorto3,
						ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_RE,
						idCampoInteresadoNumeroIdentidad, null));

		JoinDefinition joinTextoCorto4 = new JoinDefinition(
				new DbColumnDef(tablaReferencia,
						ReferenciaUDocREDBEntityImpl.CAMPO_ID_ELEMENTO),
				new DbColumnDef(tablaTextoCorto4, getCustomizedField(
						CAMPO_ID_ELEMENTO, aliasTextoCorto4)),
				generateInteresadoPrincipalJoin(aliasTextoCorto4,
						ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_RE,
						idCampoInteresadoRol, null));

		JoinDefinition joinTextoCorto5 = new JoinDefinition(
				new DbColumnDef(tablaReferencia,
						ReferenciaUDocREDBEntityImpl.CAMPO_ID_ELEMENTO),
				new DbColumnDef(tablaTextoCorto5, getCustomizedField(
						CAMPO_ID_ELEMENTO, aliasTextoCorto5)),
				generateInteresadoPrincipalJoin(aliasTextoCorto5,
						ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_RE,
						idCampoInteresadoValidado, null));

		JoinDefinition[] joins = new JoinDefinition[] { joinDescriptor,
				joinTextoCorto1, joinTextoCorto2, joinTextoCorto3,
				joinTextoCorto4, joinTextoCorto5 };

		sqlFrom.append(DBUtils.generateLeftOuterJoinCondition(tablaReferencia,
				joins));

		sqlFrom.append(DBUtils.WHERE)
				.append(DBUtils
						.generateInTokenField(
								ReferenciaUDocREDBEntityImpl.CAMPO_ID_ELEMENTO,
								idsUDoc))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						ReferenciaUDocREDBEntityImpl.CAMPO_ID_CAMPO,
						idCampoInteresadoIdentidad))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						ReferenciaUDocREDBEntityImpl.CAMPO_TIPOUDOC, tipoUdoc))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						getCustomizedField(CAMPO_VALOR, aliasTextoCorto1),
						Constants.TRUE_FULL_STRING));

		DbColumnDef[] columns = new DbColumnDef[] {
				new DbColumnDef("id",
						ReferenciaUDocREDBEntityImpl.CAMPO_ID_ELEMENTO),
				new DbColumnDef("orden",
						ReferenciaUDocREDBEntityImpl.CAMPO_ORDEN),
				new DbColumnDef("nombre", DescriptorDBEntityImpl.CAMPO_NOMBRE),
				new DbColumnDef("principal", getCustomizedField(CAMPO_VALOR,
						aliasTextoCorto1)),
				new DbColumnDef("idEnTerceros", getCustomizedField(CAMPO_VALOR,
						aliasTextoCorto2)),
				new DbColumnDef("numeroIdentificacion", getCustomizedField(
						CAMPO_VALOR, aliasTextoCorto3)),
				new DbColumnDef("rol", getCustomizedField(CAMPO_VALOR,
						aliasTextoCorto4)),
				new DbColumnDef("validado", getCustomizedField(CAMPO_VALOR,
						aliasTextoCorto5)), };

		return getVOS(Constants.STRING_EMPTY, sqlFrom.toString(), columns,
				InteresadoUdocVO.class);

		/*
		 * SELECT advcrefudocre.idudocre, addescriptor.nombre NOMBRE,
		 * advctextudocre1.valor PRINCIPAL, advctextudocre2.valor IDTERCERO,
		 * advctextudocre3.valor NUMIDENTIFICACION, advctextudocre4.valor ROL,
		 * advctextudocre5.valor VALIDADO FROM advcrefudocre advcrefudocre left
		 * outer join addescriptor addescriptor on advcrefudocre.IDOBJREF =
		 * addescriptor.id left outer join advctextudocre advctextudocre1 on
		 * advcrefudocre.IDUDOCRE = advctextudocre1.IDUDOCRE and
		 * advcrefudocre.orden = advctextudocre1.orden and
		 * advctextudocre1.tipoudoc = 1 and advctextudocre1.idcampo = '213' and
		 * advctextudocre1.valor = 'Si' left outer join advctextudocre
		 * advctextudocre2 on advcrefudocre.IDUDOCRE = advctextudocre2.IDUDOCRE
		 * and advcrefudocre.orden = advctextudocre2.orden and
		 * advctextudocre2.tipoudoc = 1 and advctextudocre2.idcampo = '51' left
		 * outer join advctextudocre advctextudocre3 on advcrefudocre.IDUDOCRE =
		 * advctextudocre3.IDUDOCRE and advcrefudocre.orden =
		 * advctextudocre3.orden and advctextudocre3.tipoudoc = 1 and
		 * advctextudocre3.idcampo = '10' left outer join advctextudocre
		 * advctextudocre4 on advcrefudocre.IDUDOCRE = advctextudocre4.IDUDOCRE
		 * and advcrefudocre.orden = advctextudocre4.orden and
		 * advctextudocre4.tipoudoc = 1 and advctextudocre4.idcampo = '11' left
		 * outer join advctextudocre advctextudocre5 on advcrefudocre.IDUDOCRE =
		 * advctextudocre5.IDUDOCRE and advcrefudocre.orden =
		 * advctextudocre5.orden and advctextudocre5.tipoudoc = 1 and
		 * advctextudocre5.idcampo = '12' WHERE advcrefudocre.idudocre in
		 * ('0ba281fc5a89900000000000000004d5','0ba281fcb79db00000000000000004d5',
		 * '0ba281fcc113900000000000000004d5') AND advcrefudocre.idcampo = '9'
		 * AND advcrefudocre.tipoudoc = 1 AND advctextudocre1.valor='Si'
		 */

	}

}