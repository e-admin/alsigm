package fondos.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import util.CollectionUtils;
import valoracion.db.ValoracionDBEntityImpl;

import common.db.DBCommand;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.util.CustomDateRange;
import common.util.StringUtils;

import descripcion.db.DescriptorDBEntityImpl;
import fondos.db.oracle.ElementoCuadroClasificacionDBEntityImpl;
import fondos.model.ElementoCuadroClasificacion;
import fondos.model.Fondo;
import fondos.vos.FondoVO;
import fondos.vos.VolumenSerieVO;
import gcontrol.db.ArchivoDbEntityImpl;

/**
 * Clase que trabaja con trabaja contra la base de datos sobre la tabla de
 * fondos
 * 
 */
public class FondoDBEntityImpl extends ElementoCuadroClasificacionDBEntityImpl
		implements IFondoDbEntity {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(FondoDBEntityImpl.class);

	/** Logger de la clase */

	public static final String TABLE_NAME_FONDO = "ASGFFONDO";

	private static final String IDELEMENTOCF_FONDO_COLUMN_NAME = "IDELEMENTOCF";
	private static final String CODPAIS_COLUMN_NAME = "CODPAIS";
	private static final String CODCOMUNIDAD_COLUMN_NAME = "CODCOMUNIDAD";
	private static final String CODARCHIVO_COLUMN_NAME = "CODARCHIVO";
	private static final String TIPOFONDO_COLUMN_NAME = "TIPO";
	private static final String IDENTPRODUCTORA_COLUMN_NAME = "IDENTPRODUCTORA";

	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME_FONDO, IDELEMENTOCF_FONDO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_CODPAIS = new DbColumnDef(null,
			TABLE_NAME_FONDO, CODPAIS_COLUMN_NAME, DbDataType.SHORT_TEXT, 256,
			false);

	public static final DbColumnDef CAMPO_CODCOMUNIDAD = new DbColumnDef(null,
			TABLE_NAME_FONDO, CODCOMUNIDAD_COLUMN_NAME, DbDataType.SHORT_TEXT,
			256, false);

	public static final DbColumnDef CAMPO_IDENTPRODUCTORA = new DbColumnDef(
			null, TABLE_NAME_FONDO, IDENTPRODUCTORA_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_TIPOFONDO = new DbColumnDef(
			"tipofondo", TIPOFONDO_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_CODARCHIVO = new DbColumnDef(null,
			TABLE_NAME_FONDO, CODARCHIVO_COLUMN_NAME, DbDataType.SHORT_TEXT,
			false);

	public static final DbColumnDef[] COLS_DEFS_FONDO = { CAMPO_ID,
			CAMPO_CODPAIS, CAMPO_CODCOMUNIDAD, CAMPO_TIPOFONDO,
			CAMPO_IDENTPRODUCTORA, CAMPO_CODARCHIVO };

	public static final String COLUMN_NAMES_FONDO = DbUtil
			.getColumnNames(COLS_DEFS_FONDO);

	public static final String COLUMN_NAMES_FONDO_ENTITY = new StringBuffer()
			.append(DBUtils.getQualifiedColumnNames(TABLE_NAME_FONDO,
					COLS_DEFS_FONDO))
			.append(",")
			.append(DBUtils.getQualifiedColumnNames(TABLE_NAME_ELEMENTO,
					COLS_DEFS_ELEMENTO)).toString();

	protected final static String TABLE_NAME_ENTITY_FONDO = DBUtils
			.generateTableSet(new String[] { TABLE_NAME_FONDO,
					TABLE_NAME_ELEMENTO });

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME_FONDO;
	}

	public FondoDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public FondoDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Recupera el fondo que devuelve la ejecución de la consulta sobre fondos
	 * con la clausula <i>WHERE</i> que se proporciona
	 * 
	 * @param qual
	 *            Clausula <i>WHERE</i> de la consulta a ejecutar
	 * @return Fondo
	 */
	protected FondoVO getFondoVO(final String qual) {
		StringBuffer finalQual = new StringBuffer(qual)
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(ID_ELEMENTO_FIELD,
						CAMPO_ID))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(TIPO_ELEMENTO_FIELD,
						ElementoCuadroClasificacion.TIPO_FONDO));

		HashMap pairsTableNamesColsDefs = new HashMap();
		pairsTableNamesColsDefs.put(TABLE_NAME_FONDO, COLS_DEFS_FONDO);
		pairsTableNamesColsDefs.put(TABLE_NAME_ELEMENTO, COLS_DEFS_ELEMENTO);

		return (FondoVO) getVO(finalQual.toString(), pairsTableNamesColsDefs,
				Fondo.class);
	}

	/**
	 * Recupera la lista de fondos que devuelve la ejecución de la consulta
	 * sobre fondos con la clausula <i>WHERE</i> que se proporciona
	 * 
	 * @param qual
	 *            Clausula <i>WHERE</i> de la consulta a ejecutar
	 * @return Lista de fondos {@link FondoVO}
	 */
	private List getFondosVO(final String qual) {
		final StringBuffer finalQual = new StringBuffer(qual);
		if (StringUtils.isNotBlank(qual))
			finalQual.append(" AND ");
		else
			finalQual.append(" WHERE ");

		finalQual
				.append(DBUtils.generateJoinCondition(ID_ELEMENTO_FIELD,
						CAMPO_ID))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(TIPO_ELEMENTO_FIELD,
						ElementoCuadroClasificacion.TIPO_FONDO))
				.append(DBUtils.AND)
				.append(CAMPO_CODARCHIVO.getQualifiedName()).append(" = ")
				.append(ArchivoDbEntityImpl.CODIGO_FIELD).append(" ORDER BY ")
				.append(TITULO_FIELD.getQualifiedName());

		HashMap pairsTableNameColsDefs = new HashMap();
		pairsTableNameColsDefs.put(TABLE_NAME_FONDO, COLS_DEFS_FONDO);
		pairsTableNameColsDefs.put(TABLE_NAME_ELEMENTO, COLS_DEFS_ELEMENTO);
		pairsTableNameColsDefs.put(ArchivoDbEntityImpl.TABLE_NAME,
				new DbColumnDef[] { new DbColumnDef("nombreArchivo",
						ArchivoDbEntityImpl.NOMBRE_FIELD) });

		List fondos = getVOS(finalQual.toString(), pairsTableNameColsDefs,
				Fondo.class);
		List result = new ArrayList();
		if (!CollectionUtils.isEmpty(fondos)) {
			for (Iterator itFondos = fondos.iterator(); itFondos.hasNext();) {
				result.add((FondoVO) itFondos.next());
			}
		}

		return result;
	}

	/**
	 * Recupera el fondo asociado a la entidad productora que se indica
	 * 
	 * @param idEntidadProductora
	 *            Identificador de entidad productora
	 * @param codArchivo
	 * @return Fondos para los que produce la entidad productora {@link FondoVO}
	 */
	public List getFondosXEntidadProductora(String idEntidadProductora,
			String codArchivo) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_IDENTPRODUCTORA,
						idEntidadProductora));

		if (StringUtils.isNotEmpty(codArchivo)) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(CAMPO_CODARCHIVO, codArchivo));
		}

		return getFondosVO(qual.toString());
	}

	/**
	 * Recupera los fondos que se encuentran en alguno de los estados indicados
	 * 
	 * @param estados
	 *            Conjunto de estados de fondo
	 * @return Lista de fondos {@link FondoVO}
	 */
	public List getFondosXEstados(int[] estados) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateORTokensQualified(TABLE_NAME_ELEMENTO,
						ESTADO_ELEMENTO_FIELD, estados));

		return getFondosVO(qual.toString());
	}

	/**
	 * Recupera el fondo que tiene el identificador que se suministra
	 * 
	 * @param idFondo
	 *            Identificador de fondo
	 * @return Datos del fondo
	 */
	public FondoVO getFondoXId(final String idFondo) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idFondo));

		return getFondoVO(qual.toString());
	}

	/**
	 * Inserta un nuevo fondo en la base de datos
	 * 
	 * @param fondoVO
	 *            Datos del fondo a guardar
	 * @return Datos del fondo insertado
	 */
	public FondoVO insertFondo(final FondoVO fondo) {
		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEFS_FONDO, fondo);
				DbInsertFns.insert(conn, TABLE_NAME_FONDO, COLUMN_NAMES_FONDO,
						inputRecord);
			}
		};

		command.execute();

		return fondo;
	}

	/**
	 * Actualiza los datos del fondo que se suministra en la tabla de fondos
	 * 
	 * @param fondo
	 *            Datos del fondo a actualizar
	 */
	public void updateFondo(final FondoVO fondoVO) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, fondoVO.getId()));

		updateVO(qual.toString(), TABLE_NAME_FONDO, COLS_DEFS_FONDO, fondoVO);
	}

	/**
	 * Elimina el fondo indicado de la tabla de fondos
	 * 
	 * @param idFondo
	 */
	public void deleteFondo(String idFondo) {
		final String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, idFondo))
				.toString();

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {

				DbDeleteFns.delete(conn, TABLE_NAME_FONDO, qual);

			}

		};

		command.execute();
	}

	/**
	 * Recupera todos los fondos que contiene el cuadro de clasificación de
	 * fondos documentales
	 * 
	 * @return Lista de fondos {@link FondoVO}
	 */
	public List getFondos() {
		return getFondosVO("");
	}

	/**
	 * Obtiene el número de referencias a una entidad productora.
	 * 
	 * @param idEntProductora
	 *            Identificador de la entidad productora.
	 * @return Número de referencias.
	 */
	public int countReferencesEntProd(String idEntProductora) {
		StringBuffer qual = new StringBuffer().append("WHERE").append(
				DBUtils.generateEQTokenField(CAMPO_IDENTPRODUCTORA,
						idEntProductora));

		return getVOCount(qual.toString(), TABLE_NAME_FONDO);
	}

	/**
	 * Obtiene el número de referencias a entidades productoras de una lista
	 * descriptora.
	 * 
	 * @param idListaDescriptora
	 *            Identificador de la lista descriptora.
	 * @return Número de referencias.
	 */
	public int countReferencesEntProdInList(String idListaDescriptora) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateJoinCondition(CAMPO_IDENTPRODUCTORA,
						DescriptorDBEntityImpl.CAMPO_ID))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						DescriptorDBEntityImpl.CAMPO_IDLISTA,
						idListaDescriptora));

		return getVOCount(qual.toString(), TABLE_NAME_FONDO + ","
				+ DescriptorDBEntityImpl.TABLE_NAME);
	}

	/**
	 * Obtener el fondo de una valoración de serie.
	 * 
	 * @param idValoracion
	 *            Identificador de la valoración de serie.
	 * @return Fondo.
	 */
	public FondoVO getFondoXIdValoracion(String idValoracion) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(CAMPO_ID.getQualifiedName())
				.append("=(SELECT ")
				.append(ElementoCuadroClasificacionDBEntityImpl.IDFONDO_FIELD
						.getQualifiedName())
				.append(" FROM ")
				.append(ValoracionDBEntityImpl.TABLE_NAME)
				.append(",")
				.append(ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO)
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(
						ValoracionDBEntityImpl.CAMPO_ID, idValoracion))
				.append(" AND ")
				.append(DBUtils
						.generateJoinCondition(
								ValoracionDBEntityImpl.CAMPO_ID_SERIE,
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD))
				.append(")").toString();

		return getFondoVO(qual);
	}

	/**
	 * Obtiene las fechas extremas de un fondo.
	 * 
	 * @param idFondo
	 *            Identificador del fondo.
	 * @return Fechas extremas.
	 */
	public CustomDateRange getFechasExtremas(String idFondo) {
		String qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(
						SerieDBEntityImpl.CAMPO_IDFONDO, idFondo)).toString();

		DbColumnDef[] cols = new DbColumnDef[] {
				new DbColumnDef("initialDate", (String) null,
						"MIN("
								+ SerieDBEntityImpl.CAMPO_FECHAINICIO
										.getQualifiedName() + ")",
						DbDataType.DATE_TIME, false),
				new DbColumnDef("finalDate", (String) null, "MAX("
						+ SerieDBEntityImpl.CAMPO_FECHAFIN.getQualifiedName()
						+ ")", DbDataType.DATE_TIME, false) };

		return (CustomDateRange) getVO(qual,
				SerieDBEntityImpl.TABLE_NAME_SERIE, cols, CustomDateRange.class);
	}

	/**
	 * Obtiene la lista de volúmenes de las series de un fondo.
	 * 
	 * @param idFondo
	 *            Identificador de un fondo.
	 * @return Lista de volúmenes de las series de un fondo.
	 */
	public List getVolumenesSeriesFondo(String idFondo) {
		Map pairs = new HashMap();
		pairs.put(SerieDBEntityImpl.TABLE_NAME_SERIE, new DbColumnDef[0]);
		pairs.put(
				VolumenSerieDBEntityImpl.TABLE_NAME,
				new DbColumnDef[] { VolumenSerieDBEntityImpl.CAMPO_TIPODOCUMENTAL });
		pairs.put(
				null,
				new DbColumnDef[] { new DbColumnDef("cantidad", (String) null,
						"SUM("
								+ VolumenSerieDBEntityImpl.CAMPO_CANTIDAD
										.getQualifiedName() + ")",
						DbDataType.LONG_INTEGER, true) });

		String qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(
						SerieDBEntityImpl.CAMPO_IDFONDO, idFondo))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						SerieDBEntityImpl.CAMPO_ID,
						VolumenSerieDBEntityImpl.CAMPO_IDSERIE))
				.append(" GROUP BY ")
				.append(VolumenSerieDBEntityImpl.CAMPO_TIPODOCUMENTAL
						.getQualifiedName())
				.append(" ORDER BY ")
				.append(VolumenSerieDBEntityImpl.CAMPO_TIPODOCUMENTAL
						.getQualifiedName()).toString();

		return getVOS(qual, pairs, VolumenSerieVO.class);
	}

	public List getFondosXCodArchivo(String codArchivo) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_CODARCHIVO, codArchivo));

		return getFondosVO(qual.toString());

	}

	public FondoVO getFondosXCodArchivoYEntidadProductora(String codArchivo,
			String idEntidadProductora) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_CODARCHIVO,
						codArchivo))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_IDENTPRODUCTORA,
						idEntidadProductora));

		return getFondoVO(qual.toString());
	}

	public void unificarDescriptor(String idDescriptor, String[] idsAReemplazar) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateInTokenField(CAMPO_IDENTPRODUCTORA,
						idsAReemplazar)).toString();

		Map cols = Collections
				.singletonMap(CAMPO_IDENTPRODUCTORA, idDescriptor);

		updateFields(qual, cols, TABLE_NAME_FONDO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.db.IFondoDbEntity#getFondosXEntidadProductoraExcludeFondo(java.lang.String,
	 *      java.lang.String)
	 */
	public List getFondosXEntidadProductoraExcludeFondo(
			String idEntidadProductora, String idFondo) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_IDENTPRODUCTORA,
						idEntidadProductora));

		if (StringUtils.isNotEmpty(idFondo)) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateNEQTokenField(CAMPO_ID, idFondo));
		}

		return getFondosVO(qual.toString());
	}
}