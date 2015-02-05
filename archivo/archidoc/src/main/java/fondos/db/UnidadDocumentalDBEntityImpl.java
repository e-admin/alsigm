package fondos.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;
import ieci.core.exception.IeciTdException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import solicitudes.db.DetalleDBEntity;
import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.db.PrestamoDBEntityImpl;
import solicitudes.utils.PropertyHelper;
import transferencias.db.UnidadInstalacionDBEntityImpl;
import valoracion.db.ValoracionDBEntityImpl;
import valoracion.vos.ValoracionSerieVO;
import xml.config.ConfiguracionDescripcion;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.db.DBCommand;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.SigiaDbInputRecord;
import common.db.SigiaDbOutputRecord;
import common.db.SigiaDbOutputRecordset;
import common.db.TableDef;
import common.exceptions.DBException;
import common.exceptions.TooManyResultsException;
import common.lang.MutableInt;
import common.util.ArrayUtils;
import common.util.StringUtils;
import common.util.TypeConverter;
import common.vos.ConsultaConnectBy;

import deposito.db.DepositoDBEntityImpl;
import deposito.db.FormatoDBEntity;
import deposito.db.UDocEnUiDepositoDbEntityImpl;
import deposito.db.UInstalacionDepositoDBEntity;
import deposito.db.common.HuecoDbEntityImplBase;
import deposito.db.commonPostgreSQLServer.HuecoDBEntityImpl;
import deposito.vos.BusquedaUIAnioSerieVO;
import deposito.vos.UIAnioSerieVO;
import descripcion.db.DescriptorDBEntityImpl;
import descripcion.db.FechaDBEntityImpl;
import descripcion.db.ReferenciaDBEntityImpl;
import descripcion.db.TextoCortoDBEntityImpl;
import descripcion.db.TextoCortoUDocREDBEntityImpl;
import fondos.db.oracle.ElementoCuadroClasificacionDBEntityImpl;
import fondos.model.UnidadDocumental;
import fondos.vos.BusquedaUdocsVO;
import fondos.vos.DocumentoAntecedenteVO;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.TablaTemporalFondosVO;
import fondos.vos.UnidadDocumentalVO;
import fondos.vos.VolumenSerieVO;
import gcontrol.model.NivelAcceso;

/**
 * Funcionalidad necesaria para almacenar y recuperar de la base de datos la
 * informacion referente a una unidad documental una vez esta ha sido validada y
 * ubicada en el cuadro de clasificacion
 * 
 */
public class UnidadDocumentalDBEntityImpl extends
		ElementoCuadroClasificacionDBEntityImpl implements
		IUnidadDocumentalDbEntity {

	protected final static Logger logger = Logger
			.getLogger(UnidadDocumentalDBEntityImpl.class);

	public static final String TABLE_NAME_UNIDAD_DOCUMENTAL = "ASGFUNIDADDOC";

	public static final String ID_ELEMENTOCF_COLUMN_NAME = "idelementocf";
	public static final String SERIE_COLUMN_NAME = "idserie";
	public static final String FONDO_COLUMN_NAME = "idfondo";
	public static final String NUMERO_EXPEDIENTE_COLUMN_NAME = "numexp";
	public static final String SISTEMA_PRODUCTOR_COLUMN_NAME = "codsistproductor";
	public static final String TIPO_DOCUMENTAL_COLUMN_NAME = "tipodocumental";
	public static final String MARCAS_BLOQUEO_COLUMNNAME = "marcasbloqueo";

	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME_UNIDAD_DOCUMENTAL, ID_ELEMENTOCF_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_IDFONDO = new DbColumnDef(null,
			TABLE_NAME_UNIDAD_DOCUMENTAL, FONDO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_IDSERIE = new DbColumnDef(null,
			TABLE_NAME_UNIDAD_DOCUMENTAL, SERIE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_NUMERO_EXPEDIENTE = new DbColumnDef(
			null, TABLE_NAME_UNIDAD_DOCUMENTAL, NUMERO_EXPEDIENTE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 256, false);
	public static final DbColumnDef CAMPO_SISTEMA_PRODUCTOR = new DbColumnDef(
			null, TABLE_NAME_UNIDAD_DOCUMENTAL, SISTEMA_PRODUCTOR_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 16, false);
	public static final DbColumnDef CAMPO_TIPO_DOCUMENTAL = new DbColumnDef(
			null, TABLE_NAME_UNIDAD_DOCUMENTAL, TIPO_DOCUMENTAL_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 254, false);
	public static final DbColumnDef CAMPO_MARCAS_BLOQUEO = new DbColumnDef(
			null, TABLE_NAME_UNIDAD_DOCUMENTAL, MARCAS_BLOQUEO_COLUMNNAME,
			DbDataType.SHORT_INTEGER, false);

	private static final DbColumnDef[] COL_DEFS_UNIDAD_DOCUMENTAL = new DbColumnDef[] {
			CAMPO_ID, CAMPO_IDFONDO, CAMPO_IDSERIE, CAMPO_NUMERO_EXPEDIENTE,
			CAMPO_SISTEMA_PRODUCTOR, CAMPO_TIPO_DOCUMENTAL,
			CAMPO_MARCAS_BLOQUEO };

	public static final DbColumnDef CAMPO_CANTIDAD = new DbColumnDef(
			VolumenSerieDBEntityImpl.CANTIDAD_COLUMN_NAME,
			DBUtils.getCountDefault(),
			VolumenSerieDBEntityImpl.CAMPO_CANTIDAD.getDataType());

	private static final DbColumnDef[] COL_DEFS_VOLUMEN = new DbColumnDef[] {
			CAMPO_IDSERIE, CAMPO_TIPO_DOCUMENTAL, CAMPO_CANTIDAD };

	private static final String HINT_SELECT_DOCS_ANTECEDENTES = "/*+ USE_NL( "
			+ TextoCortoDBEntityImpl.TABLE_NAME + ")*/";

	public UnidadDocumentalDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public UnidadDocumentalDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public UnidadDocumentalVO insertUnidadDocumental(
			UnidadDocumentalVO unidadDocumental) {
		DbConnection conn = null;
		try {
			conn = getConnection();
			DbInsertFns.insert(conn, TABLE_NAME_UNIDAD_DOCUMENTAL, DbUtil
					.getColumnNames(COL_DEFS_UNIDAD_DOCUMENTAL),
					new SigiaDbInputRecord(COL_DEFS_UNIDAD_DOCUMENTAL,
							unidadDocumental));
			return unidadDocumental;
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	/**
	 * Recupera las unidades documentales que se corresponden con los números de
	 * expediente indicados
	 * 
	 * @param numeroExpediente
	 *            Conjunto de números de expediente
	 * @return Lista de unidades documentales {@link UnidadDocumentalVO}
	 */
	public List getUnidadDocumentalXNumeroExpediente(String[] numeroExpediente) {
		StringBuffer qual = new StringBuffer().append(" WHERE ").append(
				DBUtils.generateInTokenField(CAMPO_NUMERO_EXPEDIENTE,
						numeroExpediente));
		return selectUnidadesDocumentales(qual.toString());
	}

	/**
	 * Recupera los números de expediente de las unidades documentales que están
	 * en el cuadro de clasificación de la lista que se pasa como parámetro
	 * expediente indicados
	 * 
	 * @param numerosExpediente
	 *            Conjunto de números de expediente
	 * @return Lista de números de expediente
	 */
	public List findNumerosExpediente(String[] numerosExpediente) {
		List ret = new ArrayList();

		if (!ArrayUtils.isEmpty(numerosExpediente)) {
			StringBuffer qual = new StringBuffer().append(" WHERE ").append(
					DBUtils.generateInTokenField(CAMPO_NUMERO_EXPEDIENTE,
							numerosExpediente));

			List uDocs = selectNumerosExpediente(qual.toString());

			if (uDocs != null && uDocs.size() > 0) {
				Iterator it = uDocs.iterator();
				while (it.hasNext()) {
					UnidadDocumental udoc = (UnidadDocumental) it.next();
					ret.add(udoc.getNumExp());
				}
			}
		}

		return ret;
	}

	/**
	 * Permite establecer la marca de bloqueo de una unidad documental
	 * 
	 * @param idelementoCf
	 *            Identificador de unidad documental
	 * @param marca
	 *            valor de la marca a establecer
	 */
	public void updateMarcaBloqueo(String idelementoCf, int marca) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_ID, idelementoCf));
		Map colToUpdate = Collections.singletonMap(CAMPO_MARCAS_BLOQUEO,
				String.valueOf(marca));
		updateFields(qual.toString(), colToUpdate, TABLE_NAME_UNIDAD_DOCUMENTAL);
	}

	/**
	 * Obtiene la información de una unidad documental en función del
	 * identificador del elemento del cuadro de clasificación.
	 * 
	 * @param id
	 *            Identificador del elemento del cuadro de clasificación.
	 * @return Unidad documental.
	 */
	public UnidadDocumentalVO getUnidadDocumental(String id) {

		String qual = new StringBuffer("WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();
		return selectUnidadDocumental(qual);
	}

	/**
	 * Realiza el borrado de una unidad documental dada por su identificador.
	 * 
	 * @param id
	 *            Identificador de la unidad documental a eliminar.
	 */
	public void deleteUnidadDocumental(String id) {
		final String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME_UNIDAD_DOCUMENTAL, qual);
			}
		};

		command.execute();
	}

	UnidadDocumentalVO selectUnidadDocumental(String qual) {

		StringBuffer whereClause = new StringBuffer(qual);
		if (whereClause.length() > 0)
			whereClause.append(" and ");
		whereClause
				.append("(")
				.append(DBUtils.generateJoinCondition(TABLE_NAME_ELEMENTO,
						ID_ELEMENTO_FIELD, TABLE_NAME_UNIDAD_DOCUMENTAL,
						CAMPO_ID))
				.append(")")
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(NIVEL_FIELD,
						NivelCFDBEntityImpl.ID_NIVEL_FIELD));
		final UnidadDocumental udoc = new UnidadDocumental();
		try {
			DbColumnDef columnDefNivel = new DbColumnDef("nombreNivel",
					NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD);
			DbColumnDef[] columns = (DbColumnDef[]) ArrayUtils.concat(
					COL_DEFS_UNIDAD_DOCUMENTAL, COLS_DEFS_ELEMENTO);

			DbColumnDef[] allColumns = (DbColumnDef[]) ArrayUtils.concat(
					columns, new DbColumnDef[] { columnDefNivel });

			SigiaDbOutputRecord row = new SigiaDbOutputRecord(udoc, allColumns);
			StringBuffer columNames = new StringBuffer()
					.append(DBUtils.getQualifiedColumnNames(
							TABLE_NAME_UNIDAD_DOCUMENTAL,
							COL_DEFS_UNIDAD_DOCUMENTAL))
					.append(",")
					.append(DBUtils.getQualifiedColumnNames(
							TABLE_NAME_ELEMENTO, COLS_DEFS_ELEMENTO))
					.append(",")
					.append(DBUtils.getQualifiedColumnName(
							NivelCFDBEntityImpl.NIVELCF_TABLE_NAME,
							columnDefNivel));
			String[] tableSet = { TABLE_NAME_UNIDAD_DOCUMENTAL,
					TABLE_NAME_ELEMENTO, NivelCFDBEntityImpl.NIVELCF_TABLE_NAME };
			try {
				DbConnection conn = getConnection();
				DbSelectFns.select(conn, DBUtils.generateTableSet(tableSet),
						columNames.toString(), whereClause.toString(), row);
			} catch (IeciTdException iecie) {
				return null;
			}
		} catch (Exception e) {
			throw new DBException(e);
		}
		return udoc;
	}

	// función auxiliar para seleccionar los números de expedientes de unidades
	// documentales
	// que cumplan las condiciones que se pasan como parámetro
	List selectNumerosExpediente(String qual) {
		SigiaDbOutputRecordset rows = null;
		try {
			StringBuffer whereClause = new StringBuffer(qual);
			// if (whereClause.length() > 0) whereClause.append(" and ");

			DbColumnDef[] columns = new DbColumnDef[] { CAMPO_NUMERO_EXPEDIENTE };

			rows = new SigiaDbOutputRecordset(columns, UnidadDocumental.class);

			StringBuffer columNames = new StringBuffer().append(DBUtils
					.getQualifiedColumnNames(TABLE_NAME_UNIDAD_DOCUMENTAL,
							columns));

			String[] tableSet = { TABLE_NAME_UNIDAD_DOCUMENTAL };
			try {
				DbConnection conn = getConnection();
				DbSelectFns.select(conn, DBUtils.generateTableSet(tableSet),
						columNames.toString(), whereClause.toString(), false,
						rows);
			} catch (IeciTdException iecie) {
				return null;
			}
		} catch (Exception e) {
			throw new DBException(e);
		}
		return rows;
	}

	List selectUnidadesDocumentales(String qual) {
		SigiaDbOutputRecordset rows = null;
		try {
			StringBuffer whereClause = new StringBuffer(qual);
			if (whereClause.length() > 0)
				whereClause.append(" and ");
			whereClause
					.append("(")
					.append(DBUtils.generateJoinCondition(TABLE_NAME_ELEMENTO,
							ID_ELEMENTO_FIELD, TABLE_NAME_UNIDAD_DOCUMENTAL,
							CAMPO_ID))
					.append(")")
					.append(" AND ")
					.append(DBUtils.generateJoinCondition(NIVEL_FIELD,
							NivelCFDBEntityImpl.ID_NIVEL_FIELD));

			DbColumnDef columnDefNivel = new DbColumnDef("nombreNivel",
					NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD);
			DbColumnDef[] columns = (DbColumnDef[]) ArrayUtils.concat(
					COL_DEFS_UNIDAD_DOCUMENTAL, COLS_DEFS_ELEMENTO);

			DbColumnDef[] allColumns = (DbColumnDef[]) ArrayUtils.concat(
					columns, new DbColumnDef[] { columnDefNivel });
			rows = new SigiaDbOutputRecordset(allColumns,
					UnidadDocumental.class);

			StringBuffer columNames = new StringBuffer()
					.append(DBUtils.getQualifiedColumnNames(
							TABLE_NAME_UNIDAD_DOCUMENTAL,
							COL_DEFS_UNIDAD_DOCUMENTAL))
					.append(",")
					.append(DBUtils.getQualifiedColumnNames(
							TABLE_NAME_ELEMENTO, COLS_DEFS_ELEMENTO))
					.append(",")
					.append(DBUtils.getQualifiedColumnName(
							NivelCFDBEntityImpl.NIVELCF_TABLE_NAME,
							columnDefNivel));

			String[] tableSet = { TABLE_NAME_UNIDAD_DOCUMENTAL,
					TABLE_NAME_ELEMENTO, NivelCFDBEntityImpl.NIVELCF_TABLE_NAME };
			try {
				DbConnection conn = getConnection();
				DbSelectFns.select(conn, DBUtils.generateTableSet(tableSet),
						columNames.toString(), whereClause.toString(), false,
						rows);
			} catch (IeciTdException iecie) {
				return null;
			}
		} catch (Exception e) {
			throw new DBException(e);
		}
		return rows;
	}

	/**
	 * Cuenta el número de unidades documentales que contiene un fondo
	 * 
	 * @param idFondo
	 *            Identificador de fondo
	 * @return Número de unidades documentales que contiene el fondo
	 */
	public int countUdocsEnFondo(final String idFondo) {
		final MutableInt numUdocsEnFondo = new MutableInt(0);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer("where ").append(DBUtils
						.generateEQTokenField(CAMPO_IDFONDO, idFondo));
				numUdocsEnFondo.setValue(DbSelectFns.selectCount(conn,
						TABLE_NAME_UNIDAD_DOCUMENTAL, qual.toString()));
			}
		};
		command.execute();
		return numUdocsEnFondo.getValue();
	}

	/**
	 * Obtiene los documentos antecedentes de un tercero.
	 * 
	 * @param idTercero
	 *            Identificador de tercero.
	 * @param idSerie
	 *            Identificador de la serie.
	 * @return Documentos antecedentes ({@link DocumentoAntecedenteVO}).
	 */
	public List getDocumentosAntecedentes(String idTercero, String idSerie) {
		final DbColumnDef[] CUSTOM_COLS = new DbColumnDef[] {
				new DbColumnDef("id", CAMPO_ID),
				CAMPO_NUMERO_EXPEDIENTE,
				CAMPO_SISTEMA_PRODUCTOR,
				CAMPO_TIPO_DOCUMENTAL,
				new DbColumnDef("titulo", "U",
						ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD),
				new DbColumnDef(
						"codReferencia",
						"U",
						ElementoCuadroClasificacionDBEntityImpl.CODIGO_REFERENCIA_FIELD),
				new DbColumnDef(
						"estado",
						"U",
						ElementoCuadroClasificacionDBEntityImpl.ESTADO_ELEMENTO_FIELD),
				new DbColumnDef("serie", "S",
						ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD),
				new DbColumnDef("fondo", "F",
						ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD),
				new DbColumnDef("fechaInicial", "FI",
						TextoCortoDBEntityImpl.CAMPO_VALOR),
				new DbColumnDef("fechaFinal", "FF",
						TextoCortoDBEntityImpl.CAMPO_VALOR) };

		final String[] CUSTOM_TABLE_NAMES = new String[] {
				// TextoCortoDBEntityImpl.TABLE_NAME,
				TABLE_NAME_UNIDAD_DOCUMENTAL,
				new StringBuffer(TABLE_NAME_ELEMENTO).append(" U").toString(),
				new StringBuffer(TABLE_NAME_ELEMENTO).append(" S").toString(),
				new StringBuffer(TABLE_NAME_ELEMENTO).append(" F").toString()
		// new StringBuffer(FechaDBEntityImpl.TABLE_NAME)
		// .append(" FI").toString(),
		// new StringBuffer(FechaDBEntityImpl.TABLE_NAME)
		// .append(" FF").toString(),
		};

		final StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(
						TextoCortoDBEntityImpl.CAMPO_ID_CAMPO,
						ConfiguracionSistemaArchivoFactory
								.getConfiguracionSistemaArchivo()
								.getConfiguracionDescripcion()
								.getIdTerceroInteresado()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						TextoCortoDBEntityImpl.CAMPO_VALOR, idTercero))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO, CAMPO_ID))
				.append(" AND ")
				.append(DBUtils
						.generateJoinCondition(
								TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO,
								new DbColumnDef(
										null,
										"U",
										ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD)))
				.append(" AND ")
				.append(DBUtils
						.generateEQTokenField(
								new DbColumnDef(
										null,
										"U",
										ElementoCuadroClasificacionDBEntityImpl.NIVEL_ACCESO_FIELD),
								NivelAcceso.PUBLICO));

		if (StringUtils.isNotBlank(idSerie)) {
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie));
		}

		qual.append(" AND ")
				.append(DBUtils
						.generateJoinCondition(
								CAMPO_IDSERIE,
								new DbColumnDef(
										null,
										"S",
										ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD)))
				.append(" AND ")
				.append(DBUtils
						.generateJoinCondition(
								CAMPO_IDFONDO,
								new DbColumnDef(
										null,
										"F",
										ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD)))
				.append(" AND FI.")
				.append(FechaDBEntityImpl.IDCAMPO_COLUMN_NAME)
				.append("='")
				.append(ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionDescripcion().getFechaExtremaInicial())
				.append("'")
				.append(" AND FF.")
				.append(FechaDBEntityImpl.IDCAMPO_COLUMN_NAME)
				.append("='")
				.append(ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionDescripcion().getFechaExtremaFinal())
				.append("'").append(" ORDER BY ")
				.append(CAMPO_NUMERO_EXPEDIENTE.getQualifiedName());

		// .append(" AND ")
		// .append(DBUtils.generateJoinCondition(
		// TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO,
		// new DbColumnDef(null, "FI",
		// FechaDBEntityImpl.CAMPO_ID_ELEMENTO)))
		// .append("(+) AND FI.")//TODO ZMIGRACION BD - OUTER JOIN SIN PROBAR
		// (PROBAR DESDE SERVICIO WEB)
		// .append(FechaDBEntityImpl.IDCAMPO_COLUMN_NAME)
		// .append("(+)=")
		// .append(ConfiguracionSistemaArchivoFactory
		// .getConfiguracionSistemaArchivo()
		// .getConfiguracionDescripcion()
		// .getFechaExtremaInicial())
		// .append(" AND ")
		// .append(DBUtils.generateJoinCondition(
		// TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO,
		// new DbColumnDef(null, "FF",
		// FechaDBEntityImpl.CAMPO_ID_ELEMENTO)))
		// .append("(+) AND FF.")
		// .append(FechaDBEntityImpl.IDCAMPO_COLUMN_NAME)
		// .append("(+)=")
		// .append(ConfiguracionSistemaArchivoFactory
		// .getConfiguracionSistemaArchivo()
		// .getConfiguracionDescripcion()
		// .getFechaExtremaFinal())

		// Mapeo de campos y tablas
		/*
		 * TableDef tablaUdoc = new TableDef(TABLE_NAME_UNIDAD_DOCUMENTAL);
		 * DbColumnDef colDefFechaInicial = new DbColumnDef(null,
		 * ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo().
		 * getConfiguracionDescripcion
		 * ().getFechaExtremaInicial(),DbDataType.DATE_TIME,true); DbColumnDef
		 * colDefFechaFinal = new DbColumnDef(null,
		 * ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo().
		 * getConfiguracionDescripcion
		 * ().getFechaExtremaFinal(),DbDataType.DATE_TIME,true);
		 */
		TableDef textoCortoTableDef = new TableDef(
				TextoCortoDBEntityImpl.TABLE_NAME);

		JoinDefinition[] joinsTextoCorto = new JoinDefinition[] {
				new JoinDefinition(new DbColumnDef(textoCortoTableDef,
						TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO),
						new DbColumnDef(new TableDef(
								FechaDBEntityImpl.TABLE_NAME, "FI"),
								FechaDBEntityImpl.CAMPO_ID_ELEMENTO)),

				new JoinDefinition(new DbColumnDef(textoCortoTableDef,
						TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO),
						new DbColumnDef(new TableDef(
								FechaDBEntityImpl.TABLE_NAME, "FF"),
								FechaDBEntityImpl.CAMPO_ID_ELEMENTO)), };

		StringBuffer sqlFrom = new StringBuffer();
		sqlFrom.append(
				DBUtils.generateLeftOuterJoinCondition(textoCortoTableDef,
						joinsTextoCorto)).append(" , ")
				.append(ArrayUtils.join(CUSTOM_TABLE_NAMES, ","));

		/*
		 * JoinDefinition[] joinsTableUdoc = new JoinDefinition[]{ new
		 * JoinDefinition(new DbColumnDef(tablaUdoc,colDefFechaInicial), new
		 * DbColumnDef(new
		 * TableDef(FechaDBEntityImpl.TABLE_NAME,"FI"),FechaDBEntityImpl
		 * .CAMPO_ID_CAMPO)),
		 * 
		 * new JoinDefinition(new DbColumnDef(tablaUdoc,colDefFechaFinal), new
		 * DbColumnDef(new
		 * TableDef(FechaDBEntityImpl.TABLE_NAME,"FF"),FechaDBEntityImpl
		 * .CAMPO_ID_CAMPO)) }; sqlFrom.append(" , ");
		 * sqlFrom.append(DBUtils.generateLeftOuterJoinCondition(tablaUdoc,
		 * joinsTableUdoc));
		 * 
		 * sqlFrom.append(" ").append(ArrayUtils.join(CUSTOM_TABLE_NAMES,","));
		 */

		// return getVOS(qual.toString(),
		// ArrayUtils.join(CUSTOM_TABLE_NAMES, ","),
		// HINT_SELECT_DOCS_ANTECEDENTES,
		// CUSTOM_COLS, DocumentoAntecedenteVO.class);

		return getVOS(qual.toString(), sqlFrom.toString(),
				HINT_SELECT_DOCS_ANTECEDENTES, CUSTOM_COLS,
				DocumentoAntecedenteVO.class);
	}

	/**
	 * Obtiene la lista de unidades documentales con acceso restringido que han
	 * sobrepasado la fecha de restricción.
	 * 
	 * @return Lista de unidades documentales.
	 */
	public List getUnidadesDocumentalesPublicacion() {
		Map pairs = new HashMap();
		pairs.put(TABLE_NAME_ELEMENTO, COLS_DEFS_ELEMENTO);
		pairs.put(TABLE_NAME_UNIDAD_DOCUMENTAL, COL_DEFS_UNIDAD_DOCUMENTAL);
		pairs.put(FechaDBEntityImpl.TABLE_NAME, new DbColumnDef[0]);
		pairs.put(ValoracionDBEntityImpl.TABLE_NAME, new DbColumnDef[0]);

		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateNEQTokenField(NIVEL_ACCESO_FIELD,
						NivelAcceso.PUBLICO))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(ID_ELEMENTO_FIELD,
						CAMPO_ID))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(ID_ELEMENTO_FIELD,
						FechaDBEntityImpl.CAMPO_ID_ELEMENTO))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						FechaDBEntityImpl.CAMPO_ID_CAMPO, 4))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(CAMPO_IDSERIE,
						ValoracionDBEntityImpl.CAMPO_ID_SERIE))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						ValoracionDBEntityImpl.CAMPO_ESTADO,
						ValoracionSerieVO.ESTADO_DICTAMINADA))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						ValoracionDBEntityImpl.CAMPO_REGIMENACCESOTIPO,
						ValoracionSerieVO.REGIMEN_ACCESO_RESTRINGIDO_TEMPORAL))
				.append(" AND ")
				.append(DBUtils.getNativeSysDateSyntax(getConnection()))

				// .append(" > ADD_MONTHS(")
				// .append(FechaDBEntityImpl.CAMPO_FECHA_FINAL)
				// .append(",")
				// .append(ValoracionDBEntityImpl.CAMPO_REGIMENACCESOVIG)
				// .append("*12) ORDER BY ")
				// .append(CODIGO_REFERENCIA_FIELD);
				.append(" > ")
				.append(DBUtils.getNativeAddYearSyntax(getConnection(),
						FechaDBEntityImpl.CAMPO_FECHA_FINAL.toString(),
						ValoracionDBEntityImpl.CAMPO_REGIMENACCESOVIG
								.toString())).append(" ORDER BY ")
				.append(CODIGO_REFERENCIA_FIELD);

		return getVOS(qual.toString(), pairs, UnidadDocumental.class);
	}

	/**
	 * Obtiene la lista de unidades documentales prestadas que no han sido
	 * devueltas tras dos reclamaciones.
	 */
	public List getUnidadesDocumentalesPrestadasNoDevueltas() {
		Map pairs = new HashMap();
		pairs.put(TABLE_NAME_ELEMENTO, COLS_DEFS_ELEMENTO);
		pairs.put(TABLE_NAME_UNIDAD_DOCUMENTAL, COL_DEFS_UNIDAD_DOCUMENTAL);
		pairs.put(DetalleDBEntity.TABLE_NAME, new DbColumnDef[0]);
		pairs.put(PrestamoDBEntityImpl.TABLE_NAME, new DbColumnDef[0]);

		int plazoTrasReclamacion = TypeConverter.toInt(PropertyHelper
				.getProperty(PropertyHelper.PLAZO_TRAS_RECLAMACION), 0);

		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateEQTokenField(
						DetalleDBEntity.ESTADO_FIELD,
						PrestamosConstants.ESTADO_DETALLE_ENTREGADA))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						DetalleDBEntity.IDSOLICITUD_FIELD,
						PrestamoDBEntityImpl.CAMPO_ID))
				.append(" AND ")
				.append(DBUtils
						.generateInTokenField(
								PrestamoDBEntityImpl.CAMPO_ESTADO,
								new int[] {
										PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO,
										PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO }))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						PrestamoDBEntityImpl.CAMPO_NUMRECLAMACIONES, 2))
				.append(" AND ")
				.append(PrestamoDBEntityImpl.CAMPO_FRECLAMACION2)
				.append("+")
				.append(plazoTrasReclamacion)
				.append("<")
				.append(DBUtils.getNativeSysDateSyntax(getConnection()))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						DetalleDBEntity.IDUDOC_FIELD, CAMPO_ID))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(CAMPO_ID,
						ID_ELEMENTO_FIELD)).append(" ORDER BY ")
				.append(CODIGO_REFERENCIA_FIELD);

		return getVOS(qual.toString(), pairs, UnidadDocumental.class);
	}

	public List findUnidadesDocumentales(BusquedaUdocsVO vo)
			throws TooManyResultsException

	{
		StringBuffer qual = new StringBuffer();

		// Serie
		if (StringUtils.isNotBlank(vo.getSerie()))
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateEQTokenField(CAMPO_IDSERIE, vo.getSerie()));

		// Código
		if (StringUtils.isNotBlank(vo.getCodigo()))
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateLikeTokenField(CODIGO_REFERENCIA_FIELD,
							vo.getCodigo()));

		// Título
		if (StringUtils.isNotBlank(vo.getTitulo()))
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateContainsTokenField(getConnection(),
					// TITULO_FIELD, IDXTITULO_FIELD,
					// vo.getTitulo().toUpperCase()));
							TITULO_FIELD, IDXTITULO_FIELD, vo.getTitulo()));

		if (ArrayUtils.isNotEmpty(vo.getEstados())) {
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateInTokenField(ESTADO_ELEMENTO_FIELD,
							vo.getEstados()));
		}

		// Comprobación de permisos sobre el elemento
		DBUtils.addPermissionsCheckingClauses(vo.getServiceClient(), qual,
				NIVEL_ACCESO_FIELD, ARCHIVO_FIELD, LISTA_ACCESO_FIELD);

		StringBuffer whereClause = new StringBuffer(qual.toString());
		whereClause.append(DBUtils.getCondition(whereClause.toString()));
		whereClause
				.append("(")
				.append(DBUtils.generateJoinCondition(TABLE_NAME_ELEMENTO,
						ID_ELEMENTO_FIELD, TABLE_NAME_UNIDAD_DOCUMENTAL,
						CAMPO_ID)).append(")");

		DbColumnDef[] allColumns = (DbColumnDef[]) ArrayUtils.concat(
				COL_DEFS_UNIDAD_DOCUMENTAL, COLS_DEFS_ELEMENTO);

		StringBuffer columNames = new StringBuffer().append(DBUtils
				.getQualifiedColumnNames(TABLE_NAME_UNIDAD_DOCUMENTAL,
						COL_DEFS_UNIDAD_DOCUMENTAL));

		columNames.append(",").append(
				DBUtils.getQualifiedColumnNames(TABLE_NAME_ELEMENTO,
						COLS_DEFS_ELEMENTO));

		StringBuffer allTableNames = new StringBuffer();
		allTableNames.append(" ").append(TABLE_NAME_UNIDAD_DOCUMENTAL)
				.append(", ").append(TABLE_NAME_ELEMENTO).append(" ");

		if (vo.getPageInfo() != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("codigo", CODIGO_REFERENCIA_FIELD);
			criteriosOrdenacion.put("titulo", TITULO_FIELD);

			return getVOS(whereClause.toString(),
					vo.getPageInfo().getOrderBy(criteriosOrdenacion),
					allTableNames.toString(), allColumns,
					UnidadDocumental.class, vo.getPageInfo());
		} else {
			whereClause.append(" ORDER BY ").append(
					CODIGO_FIELD.getQualifiedName());

			return getVOS(whereClause.toString(), allTableNames.toString(),
					allColumns, UnidadDocumental.class);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fondos.db.IUnidadDocumentalDbEntity#updateSerieUDoc(java.lang.String,
	 * java.lang.String[])
	 */
	public void updateSerieUDoc(String nuevaSerie, String[] idsUdocs) {

		StringBuffer qual = new StringBuffer();
		qual.append(" WHERE ").append(
				DBUtils.generateORTokens(CAMPO_ID, idsUdocs));
		UnidadDocumental udoc = new UnidadDocumental();
		udoc.setIdSerie(nuevaSerie);
		updateVO(qual.toString(), TABLE_NAME_UNIDAD_DOCUMENTAL,
				new DbColumnDef[] { CAMPO_IDSERIE }, udoc);

	}

	public void updateSerieFondoUDocs(List idsUdocs) {
		// da igual los ids que lleguen en idsUdocs, la consulta solo se va a
		// quedar con aquellos que sean
		// unidades documentales.

		// UPDATE ASGFUNIDADDOC
		// SET IDSERIE=(SELECT IDPADRE FROM ASGFELEMENTOCF WHERE
		// ASGFUNIDADDOC.IDELEMENTOCF=ASGFELEMENTOCF.ID),
		// IDFONDO=(SELECT IDFONDO FROM ASGFELEMENTOCF WHERE
		// ASGFUNIDADDOC.IDELEMENTOCF=ASGFELEMENTOCF.ID)
		// WHERE IDELEMENTOCF IN ('0a3e123e714290000000000000000008')

		StringBuffer sqlUpdate = new StringBuffer("UPDATE ")
				.append(TABLE_NAME_UNIDAD_DOCUMENTAL + " ");

		sqlUpdate
				.append("SET ")
				.append(CAMPO_IDSERIE.getName())
				.append(" = ")
				.append("(SELECT " + IDPADRE_COLUMN_NAME)
				.append(" FROM " + TABLE_NAME_ELEMENTO)
				.append(" WHERE " + CAMPO_ID.getQualifiedName() + " = "
						+ ID_ELEMENTO_FIELD.getQualifiedName() + ")")
				.append(",")
				.append(CAMPO_IDFONDO.getName())
				.append(" = ")
				.append("(SELECT " + IDFONDO_COLUMN_NAME)
				.append(" FROM " + TABLE_NAME_ELEMENTO)
				.append(" WHERE " + CAMPO_ID.getQualifiedName() + " = "
						+ ID_ELEMENTO_FIELD.getQualifiedName() + ")")
				.append(" WHERE ")
				.append(DBUtils.generateInTokenField(CAMPO_ID, idsUdocs));

		try {
			DbUpdateFns.update(getConnection(), sqlUpdate.toString());
		} catch (Exception e) {
			logger.debug(e);
		}
	}

	public void updateSerieUDoc(
			ElementoCuadroClasificacionVO elementoCuadroClasificacionVO,
			String[] idsUdocs) {
		StringBuffer qual = new StringBuffer();
		qual.append(" WHERE ").append(
				DBUtils.generateORTokens(CAMPO_ID, idsUdocs));
		UnidadDocumental udoc = new UnidadDocumental();
		udoc.setIdSerie(elementoCuadroClasificacionVO.getId());
		udoc.setIdFondo(elementoCuadroClasificacionVO.getIdFondo());
		updateVO(qual.toString(), TABLE_NAME_UNIDAD_DOCUMENTAL,
				new DbColumnDef[] { CAMPO_IDSERIE, CAMPO_IDFONDO }, udoc);

	}

	public void updateSerieFondoUDocs(
			ElementoCuadroClasificacionVO elementoCuadroClasificacionVO,
			TablaTemporalFondosVO tablaTemporalFondosVO) {
		StringBuffer qual = new StringBuffer();
		qual.append(DBUtils.WHERE)
				.append(DBUtils
						.generateInTokenFieldSubQuery(
								CAMPO_ID,
								TablaTemporalDBEntityImpl
										.getSelectIdElementocfFromTemporal(tablaTemporalFondosVO)));
		UnidadDocumental udoc = new UnidadDocumental();
		udoc.setIdSerie(elementoCuadroClasificacionVO.getId());
		udoc.setIdFondo(elementoCuadroClasificacionVO.getIdFondo());
		updateVO(qual.toString(), TABLE_NAME_UNIDAD_DOCUMENTAL,
				new DbColumnDef[] { CAMPO_IDSERIE, CAMPO_IDFONDO }, udoc);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fondos.db.IUnidadDocumentalDbEntity#getUnidadesDocumentales(java.lang
	 * .String[])
	 */
	public List getUnidadesDocumentales(String[] ids) {

		StringBuffer qual = new StringBuffer();
		qual.append(" WHERE ").append(DBUtils.generateORTokens(CAMPO_ID, ids));
		return selectUnidadesDocumentales(qual.toString());

	}

	public HashMap getUdocNumCambiosVolumenSerie(List idsUDos) {
		// SELECT TIPODOCUMENTAL,COUNT(IDELEMENTOCF)
		// FROM ASGFUNIDADDOC
		// WHERE IDELEMENTOCF IN (...)
		// GROUP BY TIPODOCUMENTAL

		StringBuffer qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateInTokenField(CAMPO_ID, idsUDos))
				.append(" GROUP BY ").append(TIPO_DOCUMENTAL_COLUMN_NAME);

		return getPairsIdValue(qual.toString(), TABLE_NAME_UNIDAD_DOCUMENTAL,
				CAMPO_TIPO_DOCUMENTAL, new DbColumnDef(
						TABLE_NAME_UNIDAD_DOCUMENTAL, "COUNT("
								+ ID_ELEMENTOCF_COLUMN_NAME + ")",
						DbDataType.MUTABLE_INT));
	}

	public UnidadDocumentalVO getTuplaUnidadDocumental(String id) {
		StringBuffer qual = new StringBuffer();
		qual.append(" WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_ID, id));
		return (UnidadDocumentalVO) getVO(qual.toString(),
				TABLE_NAME_UNIDAD_DOCUMENTAL, COL_DEFS_UNIDAD_DOCUMENTAL,
				UnidadDocumental.class);
	}

	public int getNumUdocsUISerie(String idSerie, String idUnidadInstalacion) {

		final StringBuffer tblNames = new StringBuffer()
				.append(TABLE_NAME_UNIDAD_DOCUMENTAL).append(",")
				.append(UDocEnUiDepositoDbEntityImpl.TABLE_NAME);

		final StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(CAMPO_ID,
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD,
						idUnidadInstalacion));

		return getVOCount(qual.toString(), tblNames.toString());
	}

	/**
	 * Actualizar la lista de columnas indicadas de la unidad documental en el
	 * cuadro de clasificación
	 **/
	public void updateFieldsUDocCF(String idUnidadDocumental,
			final Map columnsToUpdate) {
		StringBuffer qual = new StringBuffer().append(" WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_ID, idUnidadDocumental));

		updateFields(qual.toString(), columnsToUpdate,
				TABLE_NAME_UNIDAD_DOCUMENTAL);

	}

	public void updateFieldsUDocCFVEA(UnidadDocumentalVO uDoc) {
		/*
		 * Actualizar: el id de fondo, el código de referencia de fondo , el
		 * código de referencia final del padre y el código de referencia de las
		 * unidades documentales en la tabla ASGFUNIDADDOC
		 */

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_IDFONDO, uDoc.getIdFondo());
		colsToUpdate.put(CAMPO_IDSERIE, uDoc.getIdPadre());

		updateFieldsUDocCF(uDoc.getId(), colsToUpdate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * transferencias.db.IUDocRelacionDBEntity#getRangosUDocRE(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public List getRangosUDoc(String idUDoc, String idCampoInicial,
			String idCampoFinal) {

		StringBuffer sqlFrom = new StringBuffer();
		String aliasTextoCorto1 = "TC1", aliasTextoCorto2 = "TC2";
		TableDef tablaTextoCorto1 = new TableDef(
				TextoCortoDBEntityImpl.TABLE_NAME, aliasTextoCorto1);
		TableDef tablaTextoCorto2 = new TableDef(
				TextoCortoDBEntityImpl.TABLE_NAME, aliasTextoCorto2);

		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(tablaTextoCorto1, getCustomizedField(
						TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO,
						aliasTextoCorto1)), new DbColumnDef(tablaTextoCorto2,
						getCustomizedField(
								TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO,
								aliasTextoCorto2))) };

		sqlFrom.append(DBUtils.generateInnerJoinCondition(tablaTextoCorto1,
				joins));

		sqlFrom.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						getCustomizedField(
								TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO,
								aliasTextoCorto1), idUDoc))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						getCustomizedField(TextoCortoDBEntityImpl.CAMPO_ORDEN,
								aliasTextoCorto1),
						getCustomizedField(
								TextoCortoUDocREDBEntityImpl.CAMPO_ORDEN,
								aliasTextoCorto2)))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						getCustomizedField(
								TextoCortoDBEntityImpl.CAMPO_ID_CAMPO,
								aliasTextoCorto1), idCampoInicial))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						getCustomizedField(
								TextoCortoDBEntityImpl.CAMPO_ID_CAMPO,
								aliasTextoCorto2), idCampoFinal));

		DbColumnDef[] columns = new DbColumnDef[] {
				new DbColumnDef("desde", getCustomizedField(
						TextoCortoDBEntityImpl.CAMPO_VALOR, aliasTextoCorto1)),
				new DbColumnDef("hasta", getCustomizedField(
						TextoCortoDBEntityImpl.CAMPO_VALOR, aliasTextoCorto2)), };

		sqlFrom.append(" ORDER BY ").append(
				getCustomizedField(TextoCortoDBEntityImpl.CAMPO_ORDEN,
						aliasTextoCorto1).getQualifiedName());

		return getVOS(Constants.STRING_EMPTY, sqlFrom.toString(), columns,
				transferencias.vos.RangoVO.class);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * fondos.db.IUnidadDocumentalDbEntity#updateNumeroExpediente(java.lang.
	 * String, java.lang.String)
	 */
	public void updateNumeroExpediente(String idUnidadDocumental,
			String numExpediente) {

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_NUMERO_EXPEDIENTE, numExpediente);

		updateFieldsUDocCF(idUnidadDocumental, colsToUpdate);
	}

	/*
	 * Obtiene en una lista de CampoValorVO el valor, formato y calificador de
	 * una fecha cuyo idCampo correspondiente se indica como parámetro
	 * 
	 * @param idElementocf Identificador del elemento del cuadro
	 * 
	 * @param idCampo Identificador del campo de fecha correspondiente
	 * 
	 * @return Lista de rangos.
	 */
	/*
	 * public Date getFechaMaximaUDocs(String idSerie, String idCampo) {
	 * 
	 * final StringBuffer tblNames = new StringBuffer()
	 * .append(TABLE_NAME_UNIDAD_DOCUMENTAL) .append(",")
	 * .append(FechaDBEntityImpl.TABLE_NAME);
	 * 
	 * Date ret = null;
	 * 
	 * DbColumnDef CAMPO_FECHA_MAXIMA = new DbColumnDef( null,
	 * TABLE_NAME_UNIDAD_DOCUMENTAL, "maximo", DbDataType.DATE_TIME, -1 , true);
	 * 
	 * DbColumnDef [] columns = new DbColumnDef [] {CAMPO_FECHA_MAXIMA}; final
	 * SigiaDbOutputRecordset rowSet = new SigiaDbOutputRecordset (columns,
	 * CampoFechaVO.class);
	 * 
	 * final StringBuffer sql = new StringBuffer("SELECT ") .append("MAX(")
	 * .append(FechaDBEntityImpl.CAMPO_FECHA_FINAL) .append(") AS maximo")
	 * .append(" FROM ") .append(TABLE_NAME_UNIDAD_DOCUMENTAL) .append(",")
	 * .append(FechaDBEntityImpl.TABLE_NAME) .append(" WHERE ")
	 * .append(DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie))
	 * .append(" AND ") .append(DBUtils.generateJoinCondition( CAMPO_ID,
	 * FechaDBEntityImpl.CAMPO_ID_ELEMENTO)) .append(" AND ")
	 * .append(DBUtils.generateEQTokenField( FechaDBEntityImpl.CAMPO_ID_CAMPO,
	 * idCampo)) .append(" GROUP BY ")
	 * .append(FechaDBEntityImpl.CAMPO_ID_CAMPO);
	 * 
	 * try { DbSelectFns.select(getConnection(), sql, rowSet);
	 * 
	 * } catch (IeciTdException iecie) { return null; }
	 * 
	 * 
	 * }
	 */

	/*
	 * public void updateTipoDocumental(String tipo_old, String tipo_new) {
	 * StringBuffer qual = new StringBuffer(DBUtils.WHERE)
	 * .append(DBUtils.generateEQTokenField(CAMPO_TIPO_DOCUMENTAL, tipo_old));
	 * 
	 * Map colsToUpdate = new HashMap(); colsToUpdate.put(CAMPO_TIPO_DOCUMENTAL,
	 * tipo_new);
	 * 
	 * updateFields(qual.toString(), colsToUpdate,
	 * TABLE_NAME_UNIDAD_DOCUMENTAL); }
	 */

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.db.IUnidadDocumentalDbEntity#deleteUnidades(java.lang.String[])
	 */
	public void deleteUnidades(String[] idsUdoc) {
		final String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(CAMPO_ID, idsUdoc))
				.toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME_UNIDAD_DOCUMENTAL, qual);
			}
		};

		command.execute();
	}

	static String aliasASGDUDOCENUI = "ASGDUDOCENUI_1";
	static String aliasASGDUINSTALACION = "ASGDUINSTALACION_1";
	static String aliasASGDHUECO = "ASGDHUECO_1";
	static String aliasASGDDEPOSITO = "ASGDDEPOSITO_1";
	static String aliasASGFUNIDADDOC = "ASGFUNIDADDOC_1";
	static String aliasADVCFECHACF = "ADVCFECHACF_1";
	static String aliasADVCFECHACF2 = "ADVCFECHACF_2";
	static String aliasADVCREFCF = "ADVCREFCF_1";
	static String aliasADDESCRIPTOR = "ADDESCRIPTOR_1";
	static String aliasAGFORMATO = "AGFORMATO_1";
	static String aliasASGFELEMENTOCF1 = "ASGFELEMENTOCF_1";
	static String aliasASGFELEMENTOCF2 = "ASGFELEMENTOCF_2";

	// TableDefs
	static TableDef tablaASGDUDOCENUI = new TableDef(
			UDocEnUiDepositoDbEntityImpl.TABLE_NAME, aliasASGDUDOCENUI);
	static TableDef tablaASGDUINSTALACION = new TableDef(
			UInstalacionDepositoDBEntity.TABLE_NAME, aliasASGDUINSTALACION);
	static TableDef tablaASGDHUECO = new TableDef(HuecoDBEntityImpl.TABLE_NAME,
			aliasASGDHUECO);
	static TableDef tablaASGDDEPOSITO = new TableDef(
			DepositoDBEntityImpl.TABLE_NAME, aliasASGDDEPOSITO);
	static TableDef tablaASGFUNIDADDOC = new TableDef(
			UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL,
			aliasASGFUNIDADDOC);
	static TableDef tablaADVCREFCF = new TableDef(
			ReferenciaDBEntityImpl.TABLE_NAME, aliasADVCREFCF);
	static TableDef tablaADVCFECHACF = new TableDef(
			FechaDBEntityImpl.TABLE_NAME, aliasADVCFECHACF);
	static TableDef tablaADVCFECHACF2 = new TableDef(
			FechaDBEntityImpl.TABLE_NAME, aliasADVCFECHACF2);

	static TableDef tablaADDESCRIPTOR = new TableDef(
			DescriptorDBEntityImpl.TABLE_NAME, aliasADDESCRIPTOR);
	static TableDef tablaAGFORMATO = new TableDef(FormatoDBEntity.TABLE_NAME,
			aliasAGFORMATO);
	static TableDef tablaUDOCCF = new TableDef(
			ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
			aliasASGFELEMENTOCF1);
	static TableDef tablaSERIECF = new TableDef(
			ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
			aliasASGFELEMENTOCF2);

	// Definición de Campos
	static String campoSignaturaUI = "SIGNATURAUI";
	static String campoTituloSerie = "TITULOSERIE";
	static String campoNombreProductor = "NOMBREPRODUCTOR";
	static String campoFechaInicialMenor = "FECHAINICIALMENOR";
	static String campoFechaInicialMayor = "FECHAINICIALMAYOR";
	static String campoNombreFormato = "NOMBREFORMATO";
	static String campoCountAnios = "COUNTANIOS";
	static String campoCountSeries = "COUNTSERIES";
	static String campoIdUI = "IDUI";
	static String campoIdSerie = "IDSERIE";
	static String campoIdProductor = "IDPRODUCTOR";
	static String campoIdFormato = "IDFORMATO";
	static String campoIdElementoAsignable = "IDELEMENTOASIGNABLE";
	static String campoNumOrdenHueco = "NUMORDENHUECO";

	static DbColumnDef columnaIdUI = new DbColumnDef(campoIdUI,
			tablaASGDUINSTALACION, UInstalacionDepositoDBEntity.ID_FIELD);
	static DbColumnDef columnaSignaturaUI = new DbColumnDef(campoSignaturaUI,
			tablaASGDUINSTALACION,
			UInstalacionDepositoDBEntity.SIGNATURAUI_FIELD);
	static DbColumnDef columnaIdSerie = new DbColumnDef(campoIdSerie,
			tablaSERIECF,
			ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD);
	static DbColumnDef columnaTituloSerie = new DbColumnDef(campoTituloSerie,
			tablaSERIECF,
			ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD);
	static DbColumnDef columnaIdProductor = new DbColumnDef(campoIdProductor,
			tablaADDESCRIPTOR, DescriptorDBEntityImpl.CAMPO_ID);
	static DbColumnDef columnaNombreProductor = new DbColumnDef(
			campoNombreProductor, tablaADDESCRIPTOR,
			DescriptorDBEntityImpl.CAMPO_NOMBRE);
	static DbColumnDef columnaIdFormato = new DbColumnDef(campoIdFormato,
			tablaAGFORMATO, FormatoDBEntity.CAMPO_ID);
	static DbColumnDef columnaNombreFormato = new DbColumnDef(
			campoNombreFormato, tablaAGFORMATO, FormatoDBEntity.CAMPO_NOMBRE);
	static DbColumnDef columnaFechaInicialMenor = new DbColumnDef(
			campoFechaInicialMenor, tablaADVCFECHACF,
			FechaDBEntityImpl.CAMPO_FECHA_INICIAL);
	static DbColumnDef columnaFechaInicialMayor = new DbColumnDef(
			campoFechaInicialMayor, tablaADVCFECHACF,
			FechaDBEntityImpl.CAMPO_FECHA_FINAL);
	static DbColumnDef columnaIdElementoAsignable = new DbColumnDef(
			campoIdElementoAsignable, tablaASGDHUECO,
			HuecoDBEntityImpl.CAMPO_IDELEMPADRE);
	static DbColumnDef columnaNumOrdenHueco = new DbColumnDef(
			campoNumOrdenHueco, tablaASGDHUECO,
			HuecoDBEntityImpl.CAMPO_NUMORDEN);

	public static final DbColumnDef columnaCountSeriesSQL = new DbColumnDef(
			null, TABLE_NAME_UNIDAD_DOCUMENTAL, campoCountSeries,
			DbDataType.LONG_INTEGER, 0, false);

	public static final DbColumnDef columnaCountAniosSQL = new DbColumnDef(
			null, TABLE_NAME_UNIDAD_DOCUMENTAL, campoCountAnios,
			DbDataType.LONG_INTEGER, 0, false);

	static String aliasTablaASGDUDOCENUITotalSeries = "ASGDUDOCENUI_3";
	static String aliasTablaASGFUNIDADDOCTotalSeries = "ASGFUNIDADDOC_3";
	static String aliasTablaASGFELEMENTOCFTotalSeries = "ASGFELEMENTOCF_3";

	static TableDef tablaASGDUDOCENUITotalSeries = new TableDef(
			UDocEnUiDepositoDbEntityImpl.TABLE_NAME,
			aliasTablaASGDUDOCENUITotalSeries);
	static TableDef tablaASGFUNIDADDOCTotalSeries = new TableDef(
			UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL,
			aliasTablaASGFUNIDADDOCTotalSeries);
	static TableDef tablaASGFELEMENTOCFTotalSerie = new TableDef(
			ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
			aliasTablaASGFELEMENTOCFTotalSeries);

	static DbColumnDef columnaIDPADRE = new DbColumnDef(
			tablaASGFELEMENTOCFTotalSerie,
			ElementoCuadroClasificacionDBEntityImplBase.IDPADRE_FIELD);

	// LEFT OUTER JOIN ASGFUNIDADDOC FD ON D.IDUNIDADDOC = FD.IDELEMENTOCF
	static DbColumnDef leftColJoinTablaASGFUNIDADDOCTotalSeries = new DbColumnDef(
			tablaASGDUDOCENUITotalSeries,
			UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD);
	static DbColumnDef rightColTablaASGFUNIDADDOCTotalSeries = new DbColumnDef(
			tablaASGFUNIDADDOCTotalSeries,
			UnidadDocumentalDBEntityImpl.CAMPO_ID);
	static JoinDefinition joinASGFUNIDADDOCTotalSeries = new JoinDefinition(
			leftColJoinTablaASGFUNIDADDOCTotalSeries,
			rightColTablaASGFUNIDADDOCTotalSeries);

	// LEFT OUTER JOIN ASGFELEMENTOCF CF1 ON FD.IDELEMENTOCF = CF1.ID
	static DbColumnDef leftColJoinASGFELEMENTOCFTotalSeries = new DbColumnDef(
			tablaASGFUNIDADDOCTotalSeries,
			UnidadDocumentalDBEntityImpl.CAMPO_ID);
	static DbColumnDef rightColASGFELEMENTOCFTotalSeries = new DbColumnDef(
			tablaASGFELEMENTOCFTotalSerie,
			ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD);
	static JoinDefinition joinASGFELEMENTOCFTotalSeries = new JoinDefinition(
			leftColJoinASGFELEMENTOCFTotalSeries,
			rightColASGFELEMENTOCFTotalSeries);

	static DbColumnDef columnaIDUINSTALACIONTotalSerie = new DbColumnDef(
			tablaASGDUDOCENUITotalSeries,
			UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD);

	static StringBuffer sqlColumnaCountSeries = new StringBuffer();
	static {
		sqlColumnaCountSeries
				.append(DBUtils.ABRIR_PARENTESIS)
				.append(DBUtils.SELECT)
				.append(DBUtils.getCountDistinct(columnaIDPADRE))
				.append(DBUtils.FROM)
				.append(DBUtils.generateLeftOuterJoinCondition(
						tablaASGDUDOCENUITotalSeries, new JoinDefinition[] {
								joinASGFUNIDADDOCTotalSeries,
								joinASGFELEMENTOCFTotalSeries }))
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField1(
						columnaIDUINSTALACIONTotalSerie, columnaIdUI))
				.append(DBUtils.CERRAR_PARENTESIS);

	}
	DbColumnDef columnaCountSeries = new DbColumnDef(campoCountSeries,
			sqlColumnaCountSeries.toString());

	// COUNT ANIOS
	/*
	 * SELECT COUNT(DISTINCT TO_CHAR(F.FECHAFIN,'yyyy')) FROM ASGDUDOCENUI D
	 * LEFT OUTER JOIN ASGFUNIDADDOC FD1 ON FD1.IDELEMENTOCF = D.IDUNIDADDOC
	 * LEFT OUTER JOIN ADVCFECHACF F ON F.IDELEMENTOCF = FD1.IDELEMENTOCF LEFT
	 * OUTER JOIN ASGFELEMENTOCF CF2 ON CF2.ID = FD1.IDELEMENTOCF LEFT OUTER
	 * JOIN ASGFELEMENTOCF S1 ON CF2.IDPADRE = S1.ID WHERE IDUINSTALACION = I.ID
	 * AND S1.ID = S.ID AND F.IDCAMPO = '3'
	 */

	static String aliasTablaASGDUDOCENUI4 = "ASGDUDOCENUI_4";
	static String aliasASGFUNIDADDOC4 = "ASGFUNIDADDOC_4";
	static String aliasADVCFECHACF4 = "ADVCFECHACF_4";
	static String aliasASGFELEMENTOCF4 = "ASGFELEMENTOCF_4";
	static String aliasASGFELEMENTOCF5 = "ASGFELEMENTOCF_5";

	static TableDef tablaASGDUDOCENUI4 = new TableDef(
			UDocEnUiDepositoDbEntityImpl.TABLE_NAME, aliasTablaASGDUDOCENUI4);
	static TableDef tablaASGFUNIDADDOC4 = new TableDef(
			UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL,
			aliasASGFUNIDADDOC4);
	static TableDef tablaADVCFECHACF4 = new TableDef(
			FechaDBEntityImpl.TABLE_NAME, aliasADVCFECHACF4);
	static TableDef tablaASGFELEMENTOCF4 = new TableDef(
			ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
			aliasASGFELEMENTOCF4);
	static TableDef tablaASGFELEMENTOCF5 = new TableDef(
			ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
			aliasASGFELEMENTOCF5);

	static DbColumnDef columnaFechaFinAnios = new DbColumnDef(
			tablaADVCFECHACF4, FechaDBEntityImpl.CAMPO_FECHA_FINAL);

	static DbColumnDef columnaIDUINSTALACIONCountAnios = new DbColumnDef(
			tablaASGDUDOCENUI4,
			UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD);
	static DbColumnDef columnaIDSERIECountAnios = new DbColumnDef(
			tablaASGFELEMENTOCF5,
			ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD);

	// LEFT OUTER JOIN ASGFUNIDADDOC FD1 ON D.IDUNIDADDOC = FD1.IDELEMENTOCF
	static DbColumnDef leftColASGFUNIDADADDOC4 = new DbColumnDef(
			tablaASGDUDOCENUI4, UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD);
	static DbColumnDef rightColASGFUNIDADDOC4 = new DbColumnDef(
			tablaASGFUNIDADDOC4, UnidadDocumentalDBEntityImpl.CAMPO_ID);
	static JoinDefinition joinASGFUNIDADADOC4 = new JoinDefinition(
			leftColASGFUNIDADADDOC4, rightColASGFUNIDADDOC4);

	// LEFT OUTER JOIN ADVCFECHACF F ON FD1.IDELEMENTOCF = F.IDELEMENTOCF
	static DbColumnDef leftColADVCFECHACF4 = new DbColumnDef(
			tablaASGFUNIDADDOC4, UnidadDocumentalDBEntityImpl.CAMPO_ID);
	static DbColumnDef rightColADVCFECHACF4 = new DbColumnDef(
			tablaADVCFECHACF4, FechaDBEntityImpl.CAMPO_ID_ELEMENTO);
	static DbColumnDef colIDCAMPOADVCFECHACF4 = new DbColumnDef(
			tablaADVCFECHACF4, FechaDBEntityImpl.CAMPO_ID_CAMPO);
	// static StringBuffer sqlAppendADVCFECHACF4 = new StringBuffer().append(
	// DBUtils.AND).append(
	// DBUtils.generateEQTokenField(colIDCAMPOADVCFECHACF4,
	// idCampoFechaExtremaInicial));

	// static JoinDefinition joinADVCFECHACF4 = new JoinDefinition(
	// leftColADVCFECHACF4, rightColADVCFECHACF4, sqlAppendADVCFECHACF4
	// .toString());

	// LEFT OUTER JOIN ASGFELEMENTOCF CF2 ON FD1.IDELEMENTOCF = CF2.ID
	static DbColumnDef leftColASGFELEMENTOCF4 = new DbColumnDef(
			tablaASGFUNIDADDOC4, UnidadDocumentalDBEntityImpl.CAMPO_ID);
	static DbColumnDef rightColASGFELEMENTOCF4 = new DbColumnDef(
			tablaASGFELEMENTOCF4,
			ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD);
	static JoinDefinition joinASGFELEMENTOCF4 = new JoinDefinition(
			leftColASGFELEMENTOCF4, rightColASGFELEMENTOCF4);

	// LEFT OUTER JOIN ASGFELEMENTOCF S1 ON CF2.IDPADRE = S1.ID
	static DbColumnDef leftColASGFELEMENTOCF5 = new DbColumnDef(
			tablaASGFELEMENTOCF4,
			ElementoCuadroClasificacionDBEntityImplBase.IDPADRE_FIELD);
	static DbColumnDef rightColASGFELEMENTOCF5 = new DbColumnDef(
			tablaASGFELEMENTOCF5,
			ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD);
	static JoinDefinition joinASGFELEMENTOCF5 = new JoinDefinition(
			leftColASGFELEMENTOCF5, rightColASGFELEMENTOCF5);

	public String getIdCampoFechaExtremaInicial(ConfiguracionDescripcion cd) {
		return cd.getFechaExtremaInicial();
	}

	public String getIdCampoFechaExtremaFinal(ConfiguracionDescripcion cd) {
		return cd.getFechaExtremaFinal();
	}

	public String getIdCampoProductor(ConfiguracionDescripcion cd) {
		return cd.getProductor();
	}

	public String getSqlColumnaAnios(BusquedaUIAnioSerieVO vo)
			throws IeciTdException {
		StringBuffer sqlColumnaCountAnios = new StringBuffer();

		StringBuffer sqlAppendADVCFECHACF4 = new StringBuffer().append(
				DBUtils.AND).append(
				DBUtils.generateEQTokenField(colIDCAMPOADVCFECHACF4,
						vo.getIdCampoFechaExtremaInicial()));

		JoinDefinition joinADVCFECHACF4 = new JoinDefinition(
				leftColADVCFECHACF4, rightColADVCFECHACF4,
				sqlAppendADVCFECHACF4.toString());

		sqlColumnaCountAnios
				.append(DBUtils.ABRIR_PARENTESIS)
				.append(DBUtils.SELECT)
				.append(DBUtils.getCountDistinctAnio(getConnection(),
						columnaFechaFinAnios))
				.append(DBUtils.FROM)
				.append(DBUtils.generateLeftOuterJoinCondition(
						tablaASGDUDOCENUI4, new JoinDefinition[] {
								joinASGFUNIDADADOC4, joinADVCFECHACF4,
								joinASGFELEMENTOCF4, joinASGFELEMENTOCF5 }))
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField1(
						columnaIDUINSTALACIONCountAnios, columnaIdUI))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField1(columnaIDSERIECountAnios,
						columnaIdSerie)).append(DBUtils.CERRAR_PARENTESIS);

		return sqlColumnaCountAnios.toString();
	}

	static {
	}

	public DbColumnDef[] getColsUIAnioSerie() {
		DbColumnDef[] COLS_UI_ANIOS_SERIE = new DbColumnDef[] {
				columnaSignaturaUI, columnaTituloSerie, columnaNombreProductor,
				columnaNombreFormato, columnaFechaInicialMenor,
				columnaFechaInicialMayor, columnaIdUI, columnaIdFormato,
				columnaIdSerie, columnaIdProductor, columnaIdElementoAsignable,
				columnaNumOrdenHueco, columnaCountSeriesSQL,
				columnaCountAniosSQL };

		return COLS_UI_ANIOS_SERIE;

	}

	public List getUnidadesInstalacionPorAniosYSerie(
			ConsultaConnectBy consultaConnectBy) throws IeciTdException {
		final SigiaDbOutputRecordset rowSet = new SigiaDbOutputRecordset(
				getColsUIAnioSerie(), UIAnioSerieVO.class);

		return getVOS(consultaConnectBy.getSqlCompuestaWith(),
				UIAnioSerieVO.class, rowSet);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IeciTdException
	 * 
	 * @throws IeciTdException
	 * @see transferencias.db.IUdocEnUIDBEntity#getUnidadesInstalacionPorAniosYSerie(deposito.vos.BusquedaUIAnioSerieVO)
	 */
	public ConsultaConnectBy getConsultaConnectByUnidadesInstalacionPorAniosYSerie(
			BusquedaUIAnioSerieVO busquedaUIAnioSerieVO) throws IeciTdException {

		ConsultaConnectBy consultaConnectBy = new ConsultaConnectBy();

		DbColumnDef columnaCountAnios = new DbColumnDef(campoCountAnios,
				getSqlColumnaAnios(busquedaUIAnioSerieVO));

		// DbColumnDef columnaCountAnios = new DbColumnDef(campoCountAnios,
		// tablaASGDUINSTALACION,
		// UInstalacionDepositoDBEntity.SIGNATURAUI_FIELD);
		// DbColumnDef columnaCountSeries = new DbColumnDef(campoCountSeries,
		// tablaASGDUINSTALACION,
		// UInstalacionDepositoDBEntity.SIGNATURAUI_FIELD);

		/*
		 * (SELECT COUNT(DISTINCT CF1.IDPADRE) FROM ASGDUDOCENUI D LEFT OUTER
		 * JOIN ASGFUNIDADDOC FD ON FD.IDELEMENTOCF = D.IDUNIDADDOC LEFT OUTER
		 * JOIN ASGFELEMENTOCF CF1 ON CF1.ID = FD.IDELEMENTOCF WHERE
		 * IDUINSTALACION = I.ID )
		 */

		/**
		 * (SELECT COUNT(DISTINCT TO_CHAR(F.FECHAFIN,'yyyy')) FROM ASGDUDOCENUI
		 * D LEFT OUTER JOIN ASGFUNIDADDOC FD1 ON FD1.IDELEMENTOCF =
		 * D.IDUNIDADDOC LEFT OUTER JOIN ADVCFECHACF F ON F.IDELEMENTOCF =
		 * FD1.IDELEMENTOCF LEFT OUTER JOIN ASGFELEMENTOCF CF2 ON CF2.ID =
		 * FD1.IDELEMENTOCF LEFT OUTER JOIN ASGFELEMENTOCF S1 ON CF2.IDPADRE =
		 * S1.ID WHERE IDUINSTALACION = I.ID AND S1.ID = S.ID AND F.IDCAMPO =
		 * '3' )
		 */

		StringBuffer groupByClause = new StringBuffer(DBUtils.GROUPBY)
				.append(columnaSignaturaUI.getQualifiedName())
				.append(Constants.COMMA)
				.append(columnaIdProductor.getQualifiedName())
				.append(Constants.COMMA)
				.append(columnaNombreProductor.getQualifiedName())
				.append(Constants.COMMA)
				.append(columnaIdFormato.getQualifiedName())
				.append(Constants.COMMA).append(columnaIdUI.getQualifiedName())
				.append(Constants.COMMA)
				.append(columnaTituloSerie.getQualifiedName())
				.append(Constants.COMMA)
				.append(columnaIdElementoAsignable.getQualifiedName())
				.append(Constants.COMMA)
				.append(columnaNumOrdenHueco.getQualifiedName())
				.append(Constants.COMMA)
				.append(columnaNombreFormato.getQualifiedName())
				.append(Constants.COMMA)
				.append(columnaIdSerie.getQualifiedName());

		// StringBuffer orderByClause = new StringBuffer(DBUtils
		// .generateOrderBy(new DbColumnDef[] { columnaSignaturaUI,
		// columnaTituloSerie }));

		StringBuffer orderByClause = new StringBuffer(DBUtils.ORDER_BY)
				.append(DBUtils.getNativeLPadSyntax(getConnection(),
						columnaSignaturaUI.getQualifiedName(),
						Constants.FORMATO_SIGNATURA_CAJA.length(), "0"))
				.append(Constants.COMMA).append(columnaTituloSerie);

		// Joins
		DbColumnDef colUdocEnUIIDUI = new DbColumnDef(tablaASGDUDOCENUI,
				UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD);

		// LEFT OUTER JOIN ASGDUINSTALACION I ON I.ID = D.IDUINSTALACION
		DbColumnDef colJoinASGDUINSTALACION = new DbColumnDef(
				tablaASGDUINSTALACION, UInstalacionDepositoDBEntity.ID_FIELD);
		JoinDefinition joinASGDUINSTALACION = new JoinDefinition(
				colUdocEnUIIDUI, colJoinASGDUINSTALACION);

		// LEFT OUTER JOIN ASGDHUECO H ON I.ID = H.IDUINSTALACION
		DbColumnDef colJoinASGDHUECO = new DbColumnDef(tablaASGDHUECO,
				HuecoDbEntityImplBase.CAMPO_IDUINSTALACION);
		JoinDefinition joinASGDHUECO = new JoinDefinition(colUdocEnUIIDUI,
				colJoinASGDHUECO);

		// LEFT OUTER JOIN ASGDDEPOSITO E ON H.IDDEPOSITO = E.ID
		DbColumnDef leftColJoinASGDDEPOSITO = new DbColumnDef(tablaASGDHUECO,
				HuecoDbEntityImplBase.CAMPO_IDDEPOSITO);
		DbColumnDef rightColJoinASGDEPOSITO = new DbColumnDef(
				tablaASGDDEPOSITO, DepositoDBEntityImpl.CAMPO_ID);
		JoinDefinition joinASGDDEPOSITO = new JoinDefinition(
				leftColJoinASGDDEPOSITO, rightColJoinASGDEPOSITO);

		// LEFT OUTER JOIN ASGFUNIDADDOC FD ON FD.IDELEMENTOCF = D.IDUNIDADDOC
		DbColumnDef leftColJoinASGFUNIDADDOC = new DbColumnDef(
				tablaASGDUDOCENUI,
				UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD);
		DbColumnDef rightColJoinASGFUNIDADDOC = new DbColumnDef(
				tablaASGFUNIDADDOC, UnidadDocumentalDBEntityImpl.CAMPO_ID);

		JoinDefinition joinASGFUNIDADDOC = new JoinDefinition(
				leftColJoinASGFUNIDADDOC, rightColJoinASGFUNIDADDOC);

		// LEFT OUTER JOIN ADVCFECHACF F1 ON F1.IDELEMENTOCF = FD.IDELEMENTOCF
		// AND F1.IDCAMPO='3'
		DbColumnDef leftColADVCFECHACF = new DbColumnDef(tablaASGFUNIDADDOC,
				UnidadDocumentalDBEntityImpl.CAMPO_ID);
		DbColumnDef rightColADVCFECHACF = new DbColumnDef(tablaADVCFECHACF,
				FechaDBEntityImpl.CAMPO_ID_ELEMENTO);
		DbColumnDef colIDCAMPOADVCFECHACF = new DbColumnDef(tablaADVCFECHACF,
				FechaDBEntityImpl.CAMPO_ID_CAMPO);
		StringBuffer sqlAppendADVCFECHACF = new StringBuffer().append(
				DBUtils.AND).append(
				DBUtils.generateEQTokenField(colIDCAMPOADVCFECHACF,
						busquedaUIAnioSerieVO.getIdCampoFechaExtremaInicial()));
		JoinDefinition joinADVCFECHACF = new JoinDefinition(leftColADVCFECHACF,
				rightColADVCFECHACF, sqlAppendADVCFECHACF.toString());

		// LEFT OUTER JOIN ADVCFECHACF F1 ON F1.IDELEMENTOCF = FD.IDELEMENTOCF
		// AND F1.IDCAMPO='4'
		DbColumnDef leftColADVCFECHACF2 = new DbColumnDef(tablaASGFUNIDADDOC,
				UnidadDocumentalDBEntityImpl.CAMPO_ID);
		DbColumnDef rightColADVCFECHACF2 = new DbColumnDef(tablaADVCFECHACF2,
				FechaDBEntityImpl.CAMPO_ID_ELEMENTO);
		DbColumnDef colIDCAMPOADVCFECHACF2 = new DbColumnDef(tablaADVCFECHACF2,
				FechaDBEntityImpl.CAMPO_ID_CAMPO);
		StringBuffer sqlAppendADVCFECHACF2 = new StringBuffer().append(
				DBUtils.AND).append(
				DBUtils.generateEQTokenField(colIDCAMPOADVCFECHACF2,
						busquedaUIAnioSerieVO.getIdCampoFechaExtremaFinal()));
		JoinDefinition joinADVCFECHACF2 = new JoinDefinition(
				leftColADVCFECHACF2, rightColADVCFECHACF2,
				sqlAppendADVCFECHACF2.toString());

		// LEFT OUTER JOIN ADVCREFCF R ON R.IDELEMENTOCF = FD.IDELEMENTOCF AND
		// R.IDCAMPO='16'
		DbColumnDef leftColADVCREFCF = new DbColumnDef(tablaASGFUNIDADDOC,
				UnidadDocumentalDBEntityImpl.CAMPO_ID);
		DbColumnDef rightColADVCREFCF = new DbColumnDef(tablaADVCREFCF,
				ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO);
		DbColumnDef colIDCAMPOADVCREFCF = new DbColumnDef(tablaADVCREFCF,
				ReferenciaDBEntityImpl.CAMPO_ID_CAMPO);
		StringBuffer sqlAppendADVCREFCF = new StringBuffer()
				.append(DBUtils.AND).append(
						DBUtils.generateEQTokenField(colIDCAMPOADVCREFCF,
								busquedaUIAnioSerieVO.getIdCampoProductor()));
		JoinDefinition joinADVCREFCF = new JoinDefinition(leftColADVCREFCF,
				rightColADVCREFCF, sqlAppendADVCREFCF.toString());

		// LEFT OUTER JOIN ADDESCRIPTOR DES ON DES.ID = R.IDOBJREF
		DbColumnDef leftColJoinADDESCRIPTOR = new DbColumnDef(tablaADVCREFCF,
				ReferenciaDBEntityImpl.CAMPO_IDOBJREF);
		DbColumnDef rightColJoinADDESCRIPTOR = new DbColumnDef(
				tablaADDESCRIPTOR, DescriptorDBEntityImpl.CAMPO_ID);
		JoinDefinition joinADDESCRIPTOR = new JoinDefinition(
				leftColJoinADDESCRIPTOR, rightColJoinADDESCRIPTOR);

		// LEFT OUTER JOIN AGFORMATO FMT ON FMT.ID = I.IDFORMATO
		DbColumnDef leftColJoinAGFORMATO = new DbColumnDef(
				tablaASGDUINSTALACION,
				UInstalacionDepositoDBEntity.IDFORMATO_FIELD);
		DbColumnDef rightColJoinAGFORMATO = new DbColumnDef(tablaAGFORMATO,
				FormatoDBEntity.CAMPO_ID);
		JoinDefinition joinAGFORMATO = new JoinDefinition(leftColJoinAGFORMATO,
				rightColJoinAGFORMATO);

		// LEFT OUTER JOIN ASGFELEMENTOCF CF1 ON CF1.ID = FD.IDELEMENTOCF
		DbColumnDef leftColJoinASGFELEMENTOCFUDOC = new DbColumnDef(
				tablaASGFUNIDADDOC, UnidadDocumentalDBEntityImpl.CAMPO_ID);
		DbColumnDef rightColJoinASGFELEMENTOCFUDOC = new DbColumnDef(
				tablaUDOCCF,
				ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD);
		JoinDefinition joinASGFELEMENTOCFUDOC = new JoinDefinition(
				leftColJoinASGFELEMENTOCFUDOC, rightColJoinASGFELEMENTOCFUDOC);

		// LEFT OUTER JOIN ASGFELEMENTOCF S ON CF1.IDPADRE = S.ID
		DbColumnDef leftColJoinASGFELEMENTOCFSERIE = new DbColumnDef(
				tablaUDOCCF,
				ElementoCuadroClasificacionDBEntityImplBase.IDPADRE_FIELD);
		DbColumnDef rightColJoinASGFELEMENTOCFSERIE = new DbColumnDef(
				tablaSERIECF,
				ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD);
		JoinDefinition joinASGFELEMENTOCFSERIE = new JoinDefinition(
				leftColJoinASGFELEMENTOCFSERIE, rightColJoinASGFELEMENTOCFSERIE);

		StringBuffer fromSql = new StringBuffer(DBUtils.FROM)
				.append(DBUtils
						.generateLeftOuterJoinCondition(tablaASGDUDOCENUI,
								new JoinDefinition[] { joinASGDUINSTALACION,
										joinASGDHUECO, joinASGDDEPOSITO,
										joinASGFUNIDADDOC, joinADVCFECHACF,
										joinADVCFECHACF2, joinADVCREFCF,
										joinADDESCRIPTOR, joinAGFORMATO,
										joinASGFELEMENTOCFUDOC,
										joinASGFELEMENTOCFSERIE }));

		StringBuffer qual = new StringBuffer();

		// IDSSERIE
		if (ArrayUtils.isNotEmpty(busquedaUIAnioSerieVO.getIdsSerie())) {
			DbColumnDef columnaIdSerieWhere = new DbColumnDef(tablaUDOCCF,
					ElementoCuadroClasificacionDBEntityImplBase.IDPADRE_FIELD);

			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateInTokenField(columnaIdSerieWhere,
							busquedaUIAnioSerieVO.getIdsSerie()));
		}

		// IDARCHIVO
		if (ArrayUtils.isNotEmpty(busquedaUIAnioSerieVO.getIdsArchivo())) {
			DbColumnDef columnaIdArchivo = new DbColumnDef(tablaASGDDEPOSITO,
					DepositoDBEntityImpl.CAMPO_IDARCHIVO);

			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateInTokenField(columnaIdArchivo,
							busquedaUIAnioSerieVO.getIdsArchivo()));
		}

		// IDFORMATO
		if (StringUtils.isNotBlank(busquedaUIAnioSerieVO.getIdFormato())) {
			DbColumnDef columnaIdFormatoWhere = new DbColumnDef(
					tablaASGDUINSTALACION,
					UnidadInstalacionDBEntityImpl.ID_FORMATO_FIELD);

			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateEQTokenField(columnaIdFormatoWhere,
							busquedaUIAnioSerieVO.getIdFormato()));
		}

		// PRODUCTOR
		if (ArrayUtils.isNotEmpty(busquedaUIAnioSerieVO.getProductores())) {
			DbColumnDef columnaIdDescriptorWhere = new DbColumnDef(
					tablaADDESCRIPTOR, DescriptorDBEntityImpl.CAMPO_ID);

			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateInTokenField(columnaIdDescriptorWhere,
							busquedaUIAnioSerieVO.getProductores()));
		}

		// IDUBICACION
		if (StringUtils.isNotEmpty(busquedaUIAnioSerieVO.getIdUbicacion())) {
			DbColumnDef columnaIdUbicacionWhere = new DbColumnDef(
					tablaASGDHUECO, HuecoDBEntityImpl.CAMPO_IDDEPOSITO);

			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateEQTokenField(columnaIdUbicacionWhere,
							busquedaUIAnioSerieVO.getIdUbicacion()));
		}

		if (ArrayUtils.isNotEmpty(busquedaUIAnioSerieVO
				.getIdsElementosAsignables())) {
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateInTokenField(columnaIdElementoAsignable,
							busquedaUIAnioSerieVO.getIdsElementosAsignables()));

		}

		// FECHA EXTREMA INICIAL (3)
		// Condiciones de Fechas extremas
		StringBuffer qualFechaExtremaInicial = new StringBuffer();

		Date fechaExtremaInicialIni = busquedaUIAnioSerieVO
				.getFechaExtremaInicialIni();

		Date fechaExtremaInicialFin = busquedaUIAnioSerieVO
				.getFechaExtremaInicialFin();

		DbColumnDef columnaFechaExtremaInicialFinWhere = new DbColumnDef(
				tablaADVCFECHACF, FechaDBEntityImpl.CAMPO_FECHA_FINAL);

		boolean generateFechaExtremaInicialIni = false;
		boolean generateFechaExtremaInicialFin = false;
		if (fechaExtremaInicialIni != null && fechaExtremaInicialFin != null) {
			generateFechaExtremaInicialIni = true;
			generateFechaExtremaInicialFin = true;
		} else if (fechaExtremaInicialIni != null) {
			generateFechaExtremaInicialIni = true;
		} else if (fechaExtremaInicialFin != null) {
			generateFechaExtremaInicialFin = true;
		}

		if (generateFechaExtremaInicialIni) {
			qualFechaExtremaInicial.append(DBUtils
					.generateTokenFieldDateAnioMesDia(getConnection(),
							columnaFechaExtremaInicialFinWhere, ">=",
							fechaExtremaInicialIni));
		}

		if (generateFechaExtremaInicialFin) {
			qualFechaExtremaInicial.append(
					DBUtils.getAnd(qualFechaExtremaInicial.toString())).append(
					DBUtils.generateTokenFieldDateAnioMesDia(getConnection(),
							columnaFechaExtremaInicialFinWhere, "<=",
							fechaExtremaInicialFin));
		}

		if (StringUtils.isNotEmpty(qualFechaExtremaInicial.toString())) {
			qual.append(DBUtils.getCondition(qual.toString()))
					.append(DBUtils.ABRIR_PARENTESIS)
					.append(qualFechaExtremaInicial.toString())
					.append(DBUtils.CERRAR_PARENTESIS);
		}

		// FECHA EXTREMA FINAL (4)
		// Condiciones de Fechas extremas
		StringBuffer qualFechaExtremaFinal = new StringBuffer();

		Date fechaExtremaFinalIni = busquedaUIAnioSerieVO
				.getFechaExtremaFinalIni();

		Date fechaExtremaFinalFin = busquedaUIAnioSerieVO
				.getFechaExtremaFinalFin();

		boolean generateFechaExtremaFinalIni = false;
		boolean generateFechaExtremaFinalFin = false;

		DbColumnDef columnaFechaExtremaFinalFinWhere = new DbColumnDef(
				tablaADVCFECHACF2, FechaDBEntityImpl.CAMPO_FECHA_FINAL);

		if (fechaExtremaFinalIni != null && fechaExtremaFinalFin != null) {
			generateFechaExtremaFinalIni = true;
			generateFechaExtremaFinalFin = true;
		}

		if (fechaExtremaFinalIni != null) {
			generateFechaExtremaFinalIni = true;
		}

		if (fechaExtremaFinalFin != null) {
			generateFechaExtremaFinalFin = true;
		}

		if (generateFechaExtremaFinalIni) {
			qualFechaExtremaFinal.append(DBUtils
					.generateTokenFieldDateAnioMesDia(getConnection(),
							columnaFechaExtremaFinalFinWhere,
							Constants.MAYOR_IGUAL, fechaExtremaFinalIni));
		}

		if (generateFechaExtremaFinalFin) {
			qualFechaExtremaFinal.append(
					DBUtils.getAnd(qualFechaExtremaFinal.toString())).append(
					DBUtils.generateTokenFieldDateAnioMesDia(getConnection(),
							columnaFechaExtremaFinalFinWhere,
							Constants.MENOR_IGUAL, fechaExtremaFinalFin));
		}

		if (StringUtils.isNotEmpty(qualFechaExtremaFinal.toString())) {
			qual.append(DBUtils.getCondition(qual.toString()))
					.append(DBUtils.ABRIR_PARENTESIS)
					.append(qualFechaExtremaFinal.toString())
					.append(DBUtils.CERRAR_PARENTESIS);
		}

		StringBuffer selectClause = new StringBuffer(DBUtils.SELECT)
				.append(columnaSignaturaUI.getQualifiedName())
				.append(Constants.COMMA)
				.append(columnaTituloSerie.getQualifiedName())
				.append(Constants.COMMA)
				.append(columnaNombreProductor.getQualifiedName())
				.append(Constants.COMMA)
				.append(columnaNombreFormato.getQualifiedName())
				.append(Constants.COMMA)
				.append(DBUtils.generateMIN(columnaFechaInicialMenor,
						campoFechaInicialMenor))
				.append(Constants.COMMA)
				.append(DBUtils.generateMAX(columnaFechaInicialMayor,
						campoFechaInicialMayor)).append(Constants.COMMA)
				.append(columnaIdUI.getQualifiedName()).append(Constants.COMMA)
				.append(columnaIdFormato.getQualifiedName())
				.append(Constants.COMMA)
				.append(columnaIdSerie.getQualifiedName())
				.append(Constants.COMMA)
				.append(columnaIdProductor.getQualifiedName())
				.append(Constants.COMMA)
				.append(columnaIdElementoAsignable.getQualifiedName())
				.append(Constants.COMMA)
				.append(columnaNumOrdenHueco.getQualifiedName())
				.append(Constants.COMMA)
				.append(columnaCountSeries.getQualifiedName())
				.append(Constants.COMMA)
				.append(columnaCountAnios.getQualifiedName());

		StringBuffer selectCountClause = new StringBuffer(DBUtils.SELECT)
				.append(columnaIdUI.getQualifiedName());

		consultaConnectBy.setSelectClause(selectClause.toString());
		consultaConnectBy.setSelectCountClause(selectCountClause.toString());
		consultaConnectBy.setFromClause(fromSql.toString());
		consultaConnectBy.setWhereClause(qual.toString());
		consultaConnectBy.setGroupByClause(groupByClause.toString());
		consultaConnectBy.setOrdeByClause(orderByClause.toString());

		StringBuffer sqlCompleta = new StringBuffer().append(selectClause)
				.append(fromSql).append(qual).append(groupByClause)
				.append(orderByClause);

		consultaConnectBy.setSqlClause(sqlCompleta.toString());

		return consultaConnectBy;

	}

	public List getCalculoVolumenYSoporteByIdSerie(String idSerie) {
		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie))
				.append(DBUtils.generateGroupBy(new DbColumnDef[] {
						CAMPO_IDSERIE, CAMPO_TIPO_DOCUMENTAL })).toString();

		return getVOS(qual, TABLE_NAME_UNIDAD_DOCUMENTAL, COL_DEFS_VOLUMEN,
				VolumenSerieVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.db.IUnidadDocumentalDbEntity#getCountUDocsByIdSerie(java.lang.String)
	 */
	public int getCountUDocsByIdSerie(String idSerie) {
		String qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie))
				.toString();

		return getVOCount(qual, TABLE_NAME_UNIDAD_DOCUMENTAL);
	}

	public String getTableName() {
		return TABLE_NAME_UNIDAD_DOCUMENTAL;
	}

}