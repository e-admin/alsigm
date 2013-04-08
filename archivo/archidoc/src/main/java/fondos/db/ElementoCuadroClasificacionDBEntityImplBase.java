package fondos.db;

import ieci.core.collections.IeciTdShortTextArrayList;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbOutputRecord;
import ieci.core.db.DbOutputStatement;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbSelectStatement;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;
import ieci.core.exception.IeciTdException;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import se.usuarios.AppPermissions;
import se.usuarios.GenericUserInfo;
import se.usuarios.ServiceClient;
import transferencias.db.RelacionEntregaDBEntityBaseImpl;
import transferencias.db.UDocRelacionDBEntityImpl;
import transferencias.db.UdocEnUIDBEntityImpl;
import transferencias.db.UnidadInstalacionReeaDBEntityImpl;
import transferencias.model.EstadoREntrega;
import util.StringOwnTokenizer;
import xml.config.Busqueda;
import xml.config.CampoBusqueda;
import xml.config.CampoDescriptivoConfigBusqueda;
import xml.config.ConfiguracionBusquedas;
import xml.config.ConfiguracionBusquedasFactory;
import xml.config.ConfiguracionFondos;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.ListaDescriptoraConfigBusqueda;
import xml.config.RestriccionCampoBusqueda;

import common.CodigoTransferenciaUtils;
import common.ConfigConstants;
import common.Constants;
import common.Operadores;
import common.db.AbstractDbOutputRecordSet;
import common.db.ConstraintType;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.SigiaDbInputRecord;
import common.db.SigiaDbOutputRecord;
import common.db.SigiaDbOutputRecordset;
import common.db.TableDef;
import common.db.VOList;
import common.exceptions.TooManyResultsException;
import common.lang.MutableStringId;
import common.util.ArrayUtils;
import common.util.CustomDate;
import common.util.CustomDateFormat;
import common.util.CustomDateRange;
import common.util.DateQualifierHelper;
import common.util.DateUtils;
import common.util.ListUtils;
import common.util.StringUtils;
import common.vos.ConsultaConnectBy;

import deposito.db.FormatoDBEntity;
import deposito.db.UDocEnUiDepositoDbEntityImpl;
import deposito.db.UInstalacionDepositoDBEntity;
import descripcion.DescripcionUtils;
import descripcion.db.DescriptorDBEntityImpl;
import descripcion.db.FechaDBEntityImpl;
import descripcion.db.NumeroDBEntityImpl;
import descripcion.db.ReferenciaDBEntityImpl;
import descripcion.db.TextoCortoDBEntityImpl;
import descripcion.db.TextoLargoDBEntityImpl;
import descripcion.model.eventos.rangos.EventoNormalizarRangos;
import descripcion.model.xml.definition.DefTipos;
import descripcion.vos.CampoFechaVO;
import descripcion.vos.CampoNumericoVO;
import descripcion.vos.CampoReferenciaVO;
import descripcion.vos.CampoTextoVO;
import descripcion.vos.DescriptorVO;
import descripcion.vos.ElementoCFVO;
import descripcion.vos.RangoVO;
import descripcion.vos.ValorCampoGenericoVO;
import docelectronicos.db.DocDocumentoCFDBEntityImpl;
import docelectronicos.db.UnidadDocumentalElectronicaDBEntityImpl;
import fondos.FondosConstants;
import fondos.db.oracle.ElementoCuadroClasificacionDBEntityImpl;
import fondos.exceptions.PadreSinAccesoPermitidoException;
import fondos.model.CamposBusquedas;
import fondos.model.ElementoCuadroClasificacion;
import fondos.model.IElementoCuadroClasificacion;
import fondos.model.RestriccionesCamposBusquedas;
import fondos.model.TipoNivelCF;
import fondos.utils.BusquedasGeneratorHelper;
import fondos.vos.BusquedaElementosVO;
import fondos.vos.BusquedaFondosQueryVO;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.FondoVO;
import fondos.vos.INivelCFVO;
import fondos.vos.TablaTemporalFondosVO;
import gcontrol.db.ArchivoDbEntityImpl;
import gcontrol.model.NivelAcceso;

public abstract class ElementoCuadroClasificacionDBEntityImplBase extends
		DBEntity implements IElementoCuadroClasificacionDbEntity {

	private static final String COLUMNAS_QUERY_IDS = "COLUMNAS_QUERY_IDS";

	private static final String TABLAS_QUERY_IDS = "TABLAS_QUERY_IDS";

	private static final String CONDICIONES_QUERY_IDS = "CONDICIONES_QUERY_IDS";

	private static final String TABLAS_QUERY_IDS_COUNT = "TABLAS_QUERY_IDS_COUNT";

	public static final String WITH_STATEMENT = "WITH_STATEMENT";

	public static final String UPDATE_COD_REF_PROCEDURE = "UPDATECODREF";
	public static final String GET_COD_REF_FUNCTION = "GETCODREF";

	/** Logger de la clase */
	static Logger logger = Logger
			.getLogger(ElementoCuadroClasificacionDBEntityImpl.class);

	public static final String TABLE_NAME_ELEMENTO = "ASGFELEMENTOCF";

	/** Estados del elemento */
	// public static final int ESTADO_TEMPORAL = 0;
	// public static final int ESTADO_NO_VIGENTE = 1;
	// public static final int ESTADO_VIGENTE = 2;
	// public static final int ESTADO_ELIMINADO = 3;

	public static final String ID_ELEMENTO_COLUMN_NAME_S = "ID";
	public static final String TIPO_ELEMENTO_COLUMN_NAME = "TIPO";
	public static final String NIVEL_COLUMN_NAME = "IDNIVEL";
	public static final String CODIGO_COLUMN_NAME = "CODIGO";
	public static final String TITULO_COLUMN_NAME = "TITULO";
	public static final String IDXTITULO_COLUMN_NAME = DBEntity.LONG_TEXT_POSTGRES_PREFFIX
			+ "TITULO";
	public static final String IDPADRE_COLUMN_NAME = "IDPADRE";
	public static final String IDFONDO_COLUMN_NAME = "IDFONDO";
	public static final String CODREFFONDO_COLUMN_NAME = "CODREFFONDO";
	public static final String CODREFERENCIA_COLUMN_NAME = "CODREFERENCIA";
	public static final String ESTADO_ELEMENTO_COLUMN_NAME = "ESTADO";
	public static final String IDFICHADESCR_COLUMN_NAME = "IDFICHADESCR";
	public static final String TIENEDESCR_COLUMN_NAME = "TIENEDESCR";
	public static final String ID_ARCHIVO_COLUMN_NAME = "IDARCHIVO";
	public static final String NIVEL_ACCESO_COLUMN_NAME = "NIVELACCESO";
	public static final String LISTA_ACCESO_COLUMN_NAME = "IDLCA";
	public static final String FINALCODREFPADRE_COLUMN_NAME = "FINALCODREFPADRE";
	public static final String EDITCLFDOCS_COLUMN_NAME = "EDITCLFDOCS";
	public static final String REPOSITORIO_ECM_COLUMN_NAME = "IDREPECM";
	public static final String ID_ELIMINACION_COLUMN_NAME = "IDELIMINACION";
	public static final String SUBTIPO_ELEMENTO_COLUMN_NAME = "SUBTIPO";
	public static final String ORDEN_POSICION_COLUMN_NAME = "ORDPOS";

	public static final String NUM_DOCS_ELECTRONICOS_NAME = "NUMDOCUMENTOSELECTRONICOS";

	public static final DbColumnDef ID_ELEMENTO_FIELD = new DbColumnDef(null,
			TABLE_NAME_ELEMENTO, ID_ELEMENTO_COLUMN_NAME_S,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef TIPO_ELEMENTO_FIELD = new DbColumnDef(null,
			TABLE_NAME_ELEMENTO, TIPO_ELEMENTO_COLUMN_NAME,
			DbDataType.LONG_INTEGER, false);
	public static final DbColumnDef NIVEL_FIELD = new DbColumnDef(null,
			TABLE_NAME_ELEMENTO, NIVEL_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);
	public static final DbColumnDef CODIGO_FIELD = new DbColumnDef(null,
			TABLE_NAME_ELEMENTO, CODIGO_COLUMN_NAME, DbDataType.SHORT_TEXT,
			128, false);
	public static final DbColumnDef TITULO_FIELD = new DbColumnDef(null,
			TABLE_NAME_ELEMENTO, TITULO_COLUMN_NAME, DbDataType.SHORT_TEXT,
			128, false);
	public static final DbColumnDef IDXTITULO_FIELD = new DbColumnDef(null,
			TABLE_NAME_ELEMENTO, IDXTITULO_COLUMN_NAME, DbDataType.SHORT_TEXT,
			128, false);
	public static final DbColumnDef IDPADRE_FIELD = new DbColumnDef(null,
			TABLE_NAME_ELEMENTO, IDPADRE_COLUMN_NAME, DbDataType.SHORT_TEXT,
			32, false);
	public static final DbColumnDef IDFONDO_FIELD = new DbColumnDef(null,
			TABLE_NAME_ELEMENTO, IDFONDO_COLUMN_NAME, DbDataType.SHORT_TEXT,
			32, false);
	public static final DbColumnDef CODREFFONDO_FIELD = new DbColumnDef(null,
			TABLE_NAME_ELEMENTO, CODREFFONDO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 254, false);
	public static final DbColumnDef CODIGO_REFERENCIA_FIELD = new DbColumnDef(
			null, TABLE_NAME_ELEMENTO, CODREFERENCIA_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 1024, false);
	public static final DbColumnDef ESTADO_ELEMENTO_FIELD = new DbColumnDef(
			null, TABLE_NAME_ELEMENTO, ESTADO_ELEMENTO_COLUMN_NAME,
			DbDataType.LONG_INTEGER, false);
	public static final DbColumnDef IDFICHADESCR_FIELD = new DbColumnDef(null,
			TABLE_NAME_ELEMENTO, IDFICHADESCR_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef TIENEDESCR_FIELD = new DbColumnDef(null,
			TABLE_NAME_ELEMENTO, TIENEDESCR_COLUMN_NAME, DbDataType.SHORT_TEXT,
			1, false);
	public static final DbColumnDef ARCHIVO_FIELD = new DbColumnDef(null,
			TABLE_NAME_ELEMENTO, ID_ARCHIVO_COLUMN_NAME, DbDataType.SHORT_TEXT,
			32, false);
	public static final DbColumnDef NIVEL_ACCESO_FIELD = new DbColumnDef(null,
			TABLE_NAME_ELEMENTO, NIVEL_ACCESO_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, false);
	public static final DbColumnDef LISTA_ACCESO_FIELD = new DbColumnDef(null,
			TABLE_NAME_ELEMENTO, LISTA_ACCESO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef FINALCODREFPADRE_FIELD = new DbColumnDef(
			null, TABLE_NAME_ELEMENTO, FINALCODREFPADRE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 1024, false);
	public static final DbColumnDef EDITCLFDOCS_FIELD = new DbColumnDef(null,
			TABLE_NAME_ELEMENTO, EDITCLFDOCS_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 1, false);
	public static final DbColumnDef REPOSITORIO_ECM_FIELD = new DbColumnDef(
			null, TABLE_NAME_ELEMENTO, REPOSITORIO_ECM_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef ID_ELIMINACION_FIELD = new DbColumnDef(
			null, TABLE_NAME_ELEMENTO, ID_ELIMINACION_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef SUBTIPO_ELEMENTO_FIELD = new DbColumnDef(
			null, TABLE_NAME_ELEMENTO, SUBTIPO_ELEMENTO_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, false);
	public static final DbColumnDef ORDEN_POSICION_FIELD = new DbColumnDef(
			"ordPos", TABLE_NAME_ELEMENTO, ORDEN_POSICION_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, true);

	public static final DbColumnDef[] COLS_DEFS_ELEMENTO = { ID_ELEMENTO_FIELD,
			CODIGO_FIELD, NIVEL_FIELD, TITULO_FIELD, IDPADRE_FIELD,
			TIPO_ELEMENTO_FIELD, CODREFFONDO_FIELD, CODIGO_REFERENCIA_FIELD,
			IDFONDO_FIELD, ESTADO_ELEMENTO_FIELD, IDFICHADESCR_FIELD,
			TIENEDESCR_FIELD, ARCHIVO_FIELD, NIVEL_ACCESO_FIELD,
			LISTA_ACCESO_FIELD, FINALCODREFPADRE_FIELD, EDITCLFDOCS_FIELD,
			REPOSITORIO_ECM_FIELD, ID_ELIMINACION_FIELD,
			SUBTIPO_ELEMENTO_FIELD, ORDEN_POSICION_FIELD };

	public static final String COLUM_NAMES_ELEMCF_LIST = DbUtil
			.getColumnNames(COLS_DEFS_ELEMENTO);

	/** Hint para agilizar las búsquedas. */
	protected static final String HINT_IDPADRE_TITULO = "/*+INDEX(ASGFELEMENTOCF ASGFELEMENTOCF03)*/";

	/**
	 * Obtiene el nombre de la tabla
	 *
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME_ELEMENTO;
	}

	public ElementoCuadroClasificacionDBEntityImplBase(DbDataSource dataSource) {
		super(dataSource);
	}

	public ElementoCuadroClasificacionDBEntityImplBase(DbConnection conn) {
		super(conn);
	}

	/**
	 * Comprueba si entre los descendientes de un elemento en el cuadro de
	 * clasificación existe uno que tenga el código que se indica
	 *
	 * @param idPadre
	 *            Identificador del elemento entre cuyos descendientes se debe
	 *            realizar la comprobación
	 * @param codigo
	 *            Código cuya existencia se quiere verificar
	 * @return <b>true</b> si existe algún elementos que verifique las
	 *         condiciones y <b>false</b> en caso contrario
	 */
	public boolean existeElementoConCodigo(String idPadre, String codigo) {
		final StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDPADRE_FIELD, idPadre))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CODIGO_FIELD, codigo));
		boolean existeElementoConCodigo = getVOCount(qual.toString(),
				TABLE_NAME_ELEMENTO, HINT_IDPADRE_TITULO) > 0;
		return existeElementoConCodigo;
	}

	public ElementoCuadroClasificacionVO getElementoCFXCodigoYTipo(
			String codigo, String tipo) {

		final StringBuffer qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CODIGO_FIELD, codigo))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(TIPO_ELEMENTO_FIELD, tipo));
		;

		return (IElementoCuadroClasificacion) getVO(qual.toString(),
				TABLE_NAME_ELEMENTO, COLS_DEFS_ELEMENTO,
				ElementoCuadroClasificacion.class);
	}

	/**
	 * Obtiene los elementos del cuadro de clasificación que son descendientes
	 * del elemento indicado y que se encuentran en alguno de los estados que se
	 * suministran
	 *
	 * @param idPadre
	 *            Identificador del elemento del cuadro cuyos descendientes se
	 *            solicitan
	 * @param estados
	 *            Conjunto de estados
	 * @return Elementos del cuadro de clasificacion
	 *         {@link ElementoCuadroClasificacionVO}
	 */
	public List getElementosCuadroClasificacion(String idPadre, int[] estados) {
		StringBuffer qual = new StringBuffer("where ");

		if (idPadre == null)
			qual.append(
					DBUtils.getNativeIfNullSintax(getConnection(),
							IDPADRE_FIELD.getQualifiedName(), DbUtil.NULL_VALUE))
					.append("=").append(DbUtil.NULL_VALUE);
		else
			qual.append(DBUtils.generateEQTokenField(IDPADRE_FIELD, idPadre));
		if (estados != null)
			qual.append(" and ").append(
					DBUtils.generateORTokens(ESTADO_ELEMENTO_FIELD, estados));
		qual.append(" order by ").append(ORDEN_POSICION_FIELD.getName())
				.append(",").append(CODIGO_FIELD.getName()).append(",")
				.append(TITULO_FIELD.getName());

		return getElementosCF(qual.toString(), HINT_IDPADRE_TITULO);
	}

	// teniendo en cuenta permisos de la lista de acceso
	public List getElementosCuadroClasificacionWithPermissions(
			ServiceClient serviceClient, String idPadre, int[] estados) {
		StringBuffer qual = new StringBuffer("where ");

		if (idPadre == null)
			qual.append(
					DBUtils.getNativeIfNullSintax(getConnection(),
							IDPADRE_FIELD.getQualifiedName(), DbUtil.NULL_VALUE))
					.append("=").append(DbUtil.NULL_VALUE);
		else
			qual.append(DBUtils.generateEQTokenField(IDPADRE_FIELD, idPadre));

		if (estados != null)
			qual.append(" and ").append(
					DBUtils.generateORTokens(ESTADO_ELEMENTO_FIELD, estados));

		StringBuffer tablas = new StringBuffer(TABLE_NAME_ELEMENTO);
		DBUtils.addPermissionsCheckingClausesWithPermissions(serviceClient,
				tablas, qual, NIVEL_ACCESO_FIELD, ARCHIVO_FIELD,
				LISTA_ACCESO_FIELD, new String[] {
						AppPermissions.CONSULTA_CUADRO_CLASIFICACION,
						AppPermissions.MODIFICACION_CUADRO_CLASIFICACION });

		qual.append(" order by ").append(ORDEN_POSICION_FIELD.getName())
				.append(",").append(CODIGO_FIELD.getName()).append(",")
				.append(TITULO_FIELD.getName());

		return getDistinctVOS(qual.toString(), tablas.toString(),
				COLS_DEFS_ELEMENTO, ElementoCuadroClasificacion.class);

		// return getElementosCF(qual.toString(), HINT_IDPADRE_TITULO);
	}

	// teniendo en cuenta permisos de la lista de acceso
	public List getFondosWithPermissions(ServiceClient serviceClient,
			int[] estados) {
		StringBuffer qual = new StringBuffer("where ");

		// Condición para obtener sólo los fondos
		qual.append(DBUtils.generateEQTokenField(TIPO_ELEMENTO_FIELD,
				ElementoCuadroClasificacion.TIPO_FONDO));

		if (estados != null)
			qual.append(" and ").append(
					DBUtils.generateORTokens(ESTADO_ELEMENTO_FIELD, estados));

		StringBuffer tablas = new StringBuffer(TABLE_NAME_ELEMENTO);
		DBUtils.addPermissionsCheckingClausesWithPermissions(serviceClient,
				tablas, qual, NIVEL_ACCESO_FIELD, ARCHIVO_FIELD,
				LISTA_ACCESO_FIELD, new String[] {
						AppPermissions.CONSULTA_CUADRO_CLASIFICACION,
						AppPermissions.MODIFICACION_CUADRO_CLASIFICACION });

		qual.append(" order by ").append(ORDEN_POSICION_FIELD.getName())
				.append(",").append(CODIGO_FIELD.getName()).append(",")
				.append(TITULO_FIELD.getName());

		return getDistinctVOS(qual.toString(), tablas.toString(),
				COLS_DEFS_ELEMENTO, ElementoCuadroClasificacion.class);
	}

	public List getElementosCuadroClasificacion(String[] ids) {
		StringBuffer qual = new StringBuffer(" where ").append(DBUtils
				.generateORTokens(ID_ELEMENTO_FIELD, ids));

		return getElementosCF(qual.toString(), null);
	}

	public String generarCondicionReferencia(ConfiguracionBusquedas cbf,
			CampoBusqueda cb, RestriccionCampoBusqueda rcb, String nombre,
			String aliasElemento, String idCampo) {
		StringBuffer sqlFrom = new StringBuffer();
		ArrayList conds = new ArrayList();
		TableDef tablaDescr = new TableDef(DescriptorDBEntityImpl.TABLE_NAME);
		TableDef tablaRef = new TableDef(ReferenciaDBEntityImpl.TABLE_NAME);

		if (cb != null) {
			sqlFrom.append(" EXISTS ( SELECT ")
					.append(ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO
							.getQualifiedName()).append(" FROM ");

			JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
					new DbColumnDef(tablaDescr, DescriptorDBEntityImpl.CAMPO_ID),
					new DbColumnDef(tablaRef,
							ReferenciaDBEntityImpl.CAMPO_IDOBJREF))
			// new DbColumnDef(new TableDef(DescriptorDBEntityImpl.TABLE_NAME,
			// aliasDescr),getCustomizedField(DescriptorDBEntityImpl.CAMPO_ID,aliasDescr)),
			// new DbColumnDef(new TableDef(ReferenciaDBEntityImpl.TABLE_NAME,
			// aliasRef),getCustomizedField(ReferenciaDBEntityImpl.CAMPO_IDOBJREF,aliasRef)))
			};

			sqlFrom.append(DBUtils
					.generateInnerJoinCondition(tablaDescr, joins));

			// .append("( SELECT ")
			// .append(ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO.getQualifiedName())
			// .append(getCustomizedField(ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO,aliasRef).getQualifiedName())
			// .append(" FROM ")
			// .append(tablaDescr)
			// .append(", ")
			// .append(ReferenciaDBEntityImpl.TABLE_NAME);

			/*
			 * JoinDefinition[] joins = new JoinDefinition[]{ new
			 * JoinDefinition( new DbColumnDef(new
			 * TableDef(DescriptorDBEntityImpl
			 * .TABLE_NAME),DescriptorDBEntityImpl.CAMPO_ID), new
			 * DbColumnDef(new
			 * TableDef(ReferenciaDBEntityImpl.TABLE_NAME),ReferenciaDBEntityImpl
			 * .CAMPO_IDOBJREF)) };
			 */

			/*
			 * sqlFrom .append(" WHERE ")
			 * .append(DBUtils.generateJoinCondition(DescriptorDBEntityImpl
			 * .TABLE_NAME, DescriptorDBEntityImpl.CAMPO_ID ,
			 * ReferenciaDBEntityImpl.TABLE_NAME,
			 * ReferenciaDBEntityImpl.CAMPO_IDOBJREF));
			 */

			/**
			 * OJO ! Esto no funcionaba en Oracle => convertirla a condición a
			 * poner en el where
			 **/
			/*
			 * conds.add(DBUtils.generateJoinCondition(ReferenciaDBEntityImpl.
			 * CAMPO_ID_ELEMENTO,
			 * getCustomizedField(ID_ELEMENTO_FIELD,aliasElemento)));
			 */

			String[] listasDescriptoras = getListasDescriptorasRestriccionCampo(
					cbf, rcb);
			if (listasDescriptoras != null && listasDescriptoras.length > 0)
				// sqlFrom.append(" AND ").append(DescriptorDBEntityImpl.CAMPO_IDLISTA).append(" IN (").append(listasDescriptoras).append(")");
				conds.add(DBUtils.generateInTokenField(
						DescriptorDBEntityImpl.CAMPO_IDLISTA,
						listasDescriptoras));

			if (StringUtils.isNotEmpty(nombre)) {
				ArrayList listaTexto = obtenerListaCondicionesTexto(nombre);
				if (listaTexto != null && listaTexto.size() > 0) {
					ArrayList condsTexto = new ArrayList();
					Iterator it = listaTexto.iterator();
					while (it.hasNext()) {
						String texto = (String) it.next();

						condsTexto.add(DBUtils.generateContainsTokenField(
								getConnection(),
								DescriptorDBEntityImpl.CAMPO_NOMBRE,
								DescriptorDBEntityImpl.IDXNOMBRE_FIELD, texto));

					}
					StringBuffer qualTexto = new StringBuffer();

					qualTexto.append("(")
							.append(condsToWhere(condsTexto, false).toString())
							.append(")");

					conds.add(qualTexto.toString());
				}
			}

			/*
			 * conds.add(DBUtils.generateContainsTokenField(getConnection(),
			 * DescriptorDBEntityImpl.CAMPO_NOMBRE,
			 * DescriptorDBEntityImpl.IDXNOMBRE_FIELD, nombre));
			 */

			if (StringUtils.isNotEmpty(idCampo)
					&& !(idCampo
							.equals(RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES)))
				conds.add(DBUtils.generateEQTokenField(
						ReferenciaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));

			StringBuffer condsToString = condsToWhere(conds, false);
			if (condsToString != null && condsToString.length() > 0)
				sqlFrom.append(" AND ").append(condsToString);
			// .append(")");

			/** Condición movida aquí por error en Oracle **/

			sqlFrom.append(" WHERE ")
					.append(DBUtils
							.generateJoinCondition(
									ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO,
									getCustomizedField(ID_ELEMENTO_FIELD,
											aliasElemento)));

			sqlFrom.append(" )");
		}

		return sqlFrom.toString();
	}

	public String generarCondicionRefDes(ConfiguracionBusquedas cbf,
			CampoBusqueda cb, RestriccionCampoBusqueda rcb, String nombre,
			String idCampo) {
		StringBuffer sqlFrom = new StringBuffer();
		ArrayList conds = new ArrayList();
		// TableDef tablaDescr = new
		// TableDef(DescriptorDBEntityImpl.TABLE_NAME);
		// TableDef tablaRef = new TableDef(ReferenciaDBEntityImpl.TABLE_NAME);

		if (cb != null) {
			sqlFrom.append(" SELECT DISTINCT ")
					.append(ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO
							.getQualifiedName()).append(" FROM ");

			/*
			 * Sustituido inner join por producto natural por razones de
			 * optimización
			 *
			 * JoinDefinition[] joins = new JoinDefinition[]{ new
			 * JoinDefinition( new
			 * DbColumnDef(tablaDescr,DescriptorDBEntityImpl.CAMPO_ID), new
			 * DbColumnDef(tablaRef,ReferenciaDBEntityImpl.CAMPO_IDOBJREF)) };
			 *
			 * sqlFrom.append(DBUtils.generateInnerJoinCondition(tablaDescr,
			 * joins));
			 */
			// Producto natural
			sqlFrom.append(DescriptorDBEntityImpl.TABLE_NAME).append(", ")
					.append(ReferenciaDBEntityImpl.TABLE_NAME);

			sqlFrom.append(DBUtils.WHERE)
					.append(DescriptorDBEntityImpl.CAMPO_ID.getQualifiedName())
					.append("=")
					.append(ReferenciaDBEntityImpl.CAMPO_IDOBJREF
							.getQualifiedName());

			/**
			 * OJO ! Esto no funcionaba en Oracle => convertirla a condición a
			 * poner en el where
			 **/
			/*
			 * conds.add(DBUtils.generateJoinCondition(ReferenciaDBEntityImpl.
			 * CAMPO_ID_ELEMENTO,
			 * getCustomizedField(ID_ELEMENTO_FIELD,aliasElemento)));
			 */

			String[] listasDescriptoras = getListasDescriptorasRestriccionCampo(
					cbf, rcb);
			if (listasDescriptoras != null && listasDescriptoras.length > 0)
				conds.add(DBUtils.generateInTokenField(
						DescriptorDBEntityImpl.CAMPO_IDLISTA,
						listasDescriptoras));

			if (StringUtils.isNotEmpty(nombre)) {
				ArrayList listaTexto = obtenerListaCondicionesTexto(nombre);
				if (listaTexto != null && listaTexto.size() > 0) {
					ArrayList condsTexto = new ArrayList();
					Iterator it = listaTexto.iterator();
					while (it.hasNext()) {
						String texto = (String) it.next();

						condsTexto.add(DBUtils.generateContainsTokenField(
								getConnection(),
								DescriptorDBEntityImpl.CAMPO_NOMBRE,
								DescriptorDBEntityImpl.IDXNOMBRE_FIELD, texto));

					}
					StringBuffer qualTexto = new StringBuffer();

					qualTexto.append("(")
							.append(condsToWhere(condsTexto, false).toString())
							.append(")");

					conds.add(qualTexto.toString());
				}
			}

			if (StringUtils.isNotEmpty(idCampo)
					&& !(idCampo
							.equals(RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES)))
				conds.add(DBUtils.generateEQTokenField(
						ReferenciaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));

			StringBuffer condsToString = condsToWhere(conds, false);
			if (condsToString != null && condsToString.length() > 0)
				sqlFrom.append(" AND ").append(condsToString);
			// .append(")");

			/** Condición movida aquí por error en Oracle **/

			/*
			 * sqlFrom.append(" WHERE ")
			 * .append(DBUtils.generateJoinCondition(ReferenciaDBEntityImpl
			 * .CAMPO_ID_ELEMENTO,
			 * getCustomizedField(ID_ELEMENTO_FIELD,aliasElemento)));
			 *
			 *
			 * sqlFrom.append(" )");
			 */
		}

		return sqlFrom.toString();
	}

	public String generarCondicionReferencia(String aliasElemento,
			String aliasDescriptor, String aliasReferencia, String idCampo) {
		StringBuffer sqlFrom = new StringBuffer();
		// ArrayList conds = new ArrayList();
		TableDef tablaDescr = new TableDef(DescriptorDBEntityImpl.TABLE_NAME,
				aliasDescriptor);
		TableDef tablaRef = new TableDef(ReferenciaDBEntityImpl.TABLE_NAME,
				aliasReferencia);

		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(tablaDescr, DescriptorDBEntityImpl.CAMPO_ID),
				new DbColumnDef(tablaRef, ReferenciaDBEntityImpl.CAMPO_IDOBJREF)) };

		sqlFrom.append(DBUtils.generateInnerJoinCondition(tablaDescr, joins));

		if (StringUtils.isNotEmpty(idCampo)
				&& !idCampo
						.equals(RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES))
			sqlFrom.append(" AND ").append(
					DBUtils.generateEQTokenField(
							getCustomizedField(
									ReferenciaDBEntityImpl.CAMPO_ID_CAMPO,
									aliasReferencia), idCampo));

		return sqlFrom.toString();
	}

	// public String generarCondicionReferencia(ConfiguracionBusquedas cbf,
	// CampoBusqueda cb, String nombre, String aliasElemento)
	// Para qué necesitaba el cb ????
	public String generarTablaReferencia(CampoBusqueda cb, String nombre,
			boolean addPar) {
		StringBuffer sqlFrom = new StringBuffer();
		ArrayList conds = new ArrayList();
		TableDef tablaDescr = new TableDef(DescriptorDBEntityImpl.TABLE_NAME);
		TableDef tablaRef = new TableDef(ReferenciaDBEntityImpl.TABLE_NAME);

		if (cb != null) {
			sqlFrom.append("SELECT ")
					.append(ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO
							.getQualifiedName()).append(" FROM ")
					.append(tablaDescr.getTableName()).append(", ")
					.append(tablaRef.getTableName());

			/*
			 * JoinDefinition[] joins = new JoinDefinition[]{ new
			 * JoinDefinition( new
			 * DbColumnDef(tablaDescr,DescriptorDBEntityImpl.CAMPO_ID), new
			 * DbColumnDef(tablaRef,ReferenciaDBEntityImpl.CAMPO_IDOBJREF)) };
			 */

			// sqlFrom.append(DBUtils.generateJoinCondition(tablaDescr, joins));

			conds.add(DBUtils.generateJoinCondition(
					ReferenciaDBEntityImpl.CAMPO_IDOBJREF,
					DescriptorDBEntityImpl.CAMPO_ID));

			if (StringUtils.isNotEmpty(nombre)) {
				ArrayList listaTexto = obtenerListaCondicionesTexto(nombre);
				if (listaTexto != null && listaTexto.size() > 0) {
					ArrayList condsTexto = new ArrayList();
					Iterator it = listaTexto.iterator();
					while (it.hasNext()) {
						String texto = (String) it.next();

						condsTexto.add(DBUtils.generateContainsTokenField(
								getConnection(),
								DescriptorDBEntityImpl.CAMPO_NOMBRE,
								DescriptorDBEntityImpl.IDXNOMBRE_FIELD, texto));

					}
					StringBuffer qualTexto = new StringBuffer();

					qualTexto.append("(")
							.append(condsToWhere(condsTexto, false).toString())
							.append(")");

					conds.add(qualTexto.toString());
				}
			}

			StringBuffer condsToString = condsToWhere(conds, true);
			// if (condsToString != null && condsToString.length()>0)
			sqlFrom// .append(" AND ")
			.append(condsToString);
			// .append(")");

			if (addPar) {
				sqlFrom = new StringBuffer().append("(").append(sqlFrom)
						.append(") REFDES");
			}
		}

		return sqlFrom.toString();
	}

	public String generarTablaTodosTextosTodosDescriptores(CampoBusqueda cb,
			String texto) {

		StringBuffer sqlFrom = new StringBuffer();

		if (StringUtils.isNotBlank(texto)) {
			sqlFrom.append(generarTablaReferencia(cb, texto, false))
					.append(" UNION ")
					.append(generarTablaTextoCorto(texto))
					.append(" UNION ")
					.append(generarTablaTextoLargo(texto))
					.append(" UNION ")
					.append(DBUtils
							.getUnionDummy(UnidadDocumentalDBEntityImpl.ID_ELEMENTOCF_COLUMN_NAME));

			sqlFrom = new StringBuffer().append("(").append(sqlFrom)
					.append(") TTEXTOSTDESC");
		}

		return sqlFrom.toString();
	}

	/*
	 * Anterior con exist
	 *
	 * public String generarCondicionReferencia(ConfiguracionBusquedas cbf,
	 * CampoBusqueda cb, String nombre, String aliasElemento) { StringBuffer
	 * sqlFrom = new StringBuffer(); ArrayList conds = new ArrayList(); TableDef
	 * tablaDescr = new TableDef(DescriptorDBEntityImpl.TABLE_NAME); TableDef
	 * tablaRef = new TableDef(ReferenciaDBEntityImpl.TABLE_NAME);
	 *
	 * if (cb != null) { sqlFrom .append(" EXISTS ( SELECT ")
	 * .append(ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO.getQualifiedName())
	 * .append(" FROM ");
	 *
	 * JoinDefinition[] joins = new JoinDefinition[]{ new JoinDefinition( new
	 * DbColumnDef(tablaDescr,DescriptorDBEntityImpl.CAMPO_ID), new
	 * DbColumnDef(tablaRef,ReferenciaDBEntityImpl.CAMPO_IDOBJREF)) };
	 *
	 * sqlFrom.append(DBUtils.generateInnerJoinCondition(tablaDescr, joins));
	 */

	/** Error en oracle => Movida condición al WHERE **/
	/*
	 * conds.add(DBUtils.generateJoinCondition(ReferenciaDBEntityImpl.
	 * CAMPO_ID_ELEMENTO, getCustomizedField(ID_ELEMENTO_FIELD,aliasElemento)));
	 */

	/*
	 * if (StringUtils.isNotEmpty(nombre)) { ArrayList listaTexto =
	 * obtenerListaCondicionesTexto(nombre); if (listaTexto != null &&
	 * listaTexto.size() > 0) { ArrayList condsTexto = new ArrayList(); Iterator
	 * it = listaTexto.iterator(); while (it.hasNext()) { String texto =
	 * (String)it.next();
	 *
	 * condsTexto.add(DBUtils.generateContainsTokenField(getConnection(),
	 * DescriptorDBEntityImpl.CAMPO_NOMBRE,
	 * DescriptorDBEntityImpl.IDXNOMBRE_FIELD, texto));
	 *
	 * } StringBuffer qualTexto = new StringBuffer();
	 *
	 * qualTexto.append("(") .append(condsToWhere(condsTexto, false).toString())
	 * .append(")");
	 *
	 * conds.add(qualTexto.toString()); } }
	 *
	 * StringBuffer condsToString = condsToWhere(conds, false); if
	 * (condsToString != null && condsToString.length()>0)
	 * sqlFrom.append(" AND ") .append(condsToString); // .append(")");
	 */
	/** Condición movida aquí por error en Oracle **/

	/*
	 * sqlFrom.append(" WHERE ")
	 * .append(DBUtils.generateJoinCondition(ReferenciaDBEntityImpl
	 * .CAMPO_ID_ELEMENTO,
	 * getCustomizedField(ID_ELEMENTO_FIELD,aliasElemento)));
	 *
	 * sqlFrom.append(" )"); }
	 *
	 * return sqlFrom.toString(); }
	 */

	public String generarTablaFecha(String aliasElemento, String aliasFecha,
			String idCampo) {
		StringBuffer sqlFrom = new StringBuffer();
		TableDef tablaElemento = new TableDef(TABLE_NAME_ELEMENTO,
				aliasElemento);
		TableDef tablaFecha = new TableDef(FechaDBEntityImpl.TABLE_NAME,
				aliasFecha);

		// Si es necesario sacar en el resultado alguna fecha, hay que hacer un
		// join con la tabla de fechas
		// if (fecha != null)
		// {
		// sqlFrom.append("( SELECT ").append(FechaDBEntityImpl.CAMPO_ID_ELEMENTO.getQualifiedName()).append(" FROM ");
		// String idCampo = rcb.getId(); //getIdsCampoRestriccionCampo(cbf,
		// rcb);

		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(tablaElemento, getCustomizedField(
						ID_ELEMENTO_FIELD, aliasElemento)), new DbColumnDef(
						tablaFecha,
						getCustomizedField(FechaDBEntityImpl.CAMPO_ID_ELEMENTO,
								aliasFecha))) };

		sqlFrom.append(DBUtils.generateLeftOuterJoinCondition(tablaElemento,
				joins));

		if (idCampo != null
				&& !idCampo
						.equals(RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES))
			// sqlFrom.append(" AND ").append(getCustomizedField(FechaDBEntityImpl.CAMPO_ID_CAMPO,aliasFecha).getQualifiedName()).append(" IN (").append(idCampo).append(")");
			sqlFrom.append(" AND ").append(
					DBUtils.generateEQTokenField(
							getCustomizedField(
									FechaDBEntityImpl.CAMPO_ID_CAMPO,
									aliasFecha), idCampo));

		// .append(DBUtils.generateInTokenField(getCustomizedField(FechaDBEntityImpl.CAMPO_ID_CAMPO,aliasFecha),idCampo));

		// sqlFrom.append( ")");
		// }

		return sqlFrom.toString();
	}

	public String generarTablaUDoc(String aliasElemento) {
		StringBuffer sqlFrom = new StringBuffer();
		TableDef tablaElemento = new TableDef(TABLE_NAME_ELEMENTO,
				aliasElemento);
		TableDef tablaUDoc = new TableDef(
				UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL);

		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(tablaElemento, getCustomizedField(
						ID_ELEMENTO_FIELD, aliasElemento)), new DbColumnDef(
						tablaUDoc, UnidadDocumentalDBEntityImpl.CAMPO_ID)) };

		sqlFrom.append(DBUtils.generateLeftOuterJoinCondition(tablaElemento,
				joins));
		// sqlFrom.append(DBUtils.generateInnerJoinCondition(tablaElemento,
		// joins));

		return sqlFrom.toString();
	}

	public String generarTablaUDocEnUI(String aliasElemento) {
		StringBuffer sqlFrom = new StringBuffer();
		TableDef tablaElemento = new TableDef(TABLE_NAME_ELEMENTO,
				aliasElemento);
		TableDef tablaUDocEnUI = new TableDef(
				UDocEnUiDepositoDbEntityImpl.TABLE_NAME);

		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(tablaElemento, getCustomizedField(
						ID_ELEMENTO_FIELD, aliasElemento)), new DbColumnDef(
						tablaUDocEnUI,
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD)) };

		sqlFrom.append(DBUtils.generateInnerJoinCondition(tablaElemento, joins));

		return sqlFrom.toString();
	}

	// public String generarTablaFecha(CampoBusqueda fecha,
	// RestriccionCampoBusqueda rcb, Date fechaIni, Date fechaFin, String
	// calificador)
	public String generarTablaFecha(CampoBusqueda fecha,
			RestriccionCampoBusqueda rcb, Date fechaIni, Date fechaFin,
			String calificador, String aliasElemento, String aliasFecha,
			String idCampo) {
		StringBuffer sqlFrom = new StringBuffer();
		TableDef tablaElemento = new TableDef(TABLE_NAME_ELEMENTO,
				aliasElemento);

		// Si es necesario sacar en el resultado alguna fecha, hay que hacer un
		// join con la tabla de fechas
		if (fecha != null) {
			// sqlFrom.append("( SELECT ").append(FechaDBEntityImpl.CAMPO_ID_ELEMENTO.getQualifiedName()).append(" FROM ");
			// String idCampo = getIdsCampoRestriccionCampo(rcb);

			JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
					new DbColumnDef(tablaElemento, getCustomizedField(
							ID_ELEMENTO_FIELD, aliasElemento)),
					new DbColumnDef(new TableDef(FechaDBEntityImpl.TABLE_NAME,
							aliasFecha), getCustomizedField(
							FechaDBEntityImpl.CAMPO_ID_ELEMENTO, aliasFecha))) };

			sqlFrom.append(DBUtils.generateLeftOuterJoinCondition(
					tablaElemento, joins));

			if (StringUtils.isNotEmpty(idCampo)
					&& !(idCampo
							.equals(RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES)))
				sqlFrom.append(" AND ")
						.append(getCustomizedField(
								FechaDBEntityImpl.CAMPO_ID_CAMPO, aliasFecha)
								.getQualifiedName()).append(" = '")
						.append(idCampo).append("'");

			/*
			 * String sqlWhere = componerCondicionesFecha(fechaIni, fechaFin,
			 * calificador, idCampo);
			 *
			 * sqlFrom .append(" WHERE ") .append(sqlWhere) .append( ")");
			 */
		}

		return sqlFrom.toString();
	}

	public DbColumnDef getCustomizedField(DbColumnDef column, String alias) {
		return new DbColumnDef(column.getBindPropertyVO(), alias,
				column.getName(), column.getDataType(), column.getMaxLen(),
				column.isNullable());
	}

	public Map fillQueryCampoTratamientoDirecto(String nombreCampo,
			ConfiguracionBusquedas configBus, CampoBusqueda cb,
			BusquedaElementosVO busquedaElementosVO, String aliasElemento) {
		Map query = new HashMap();

		ArrayList tablas = new ArrayList();
		ArrayList columnas = new ArrayList();
		ArrayList condiciones = new ArrayList();

		// el código de referencia no implica nuevas tablas ni columnas =>
		// añadimos sólo las condiciones
		if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CODIGO_REFERENCIA))
			condiciones.add(componerCondicionCodigoReferencia(
					busquedaElementosVO.getCodigoReferencia(), aliasElemento));
		/*
		 * Sacado un nivel fuera por optimización de búsqueda por este campo
		 *
		 * else if
		 * (nombreCampo.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TITULO))
		 * condiciones
		 * .add(componerCondicionTitulo(busquedaElementosVO.getTitulo(),
		 * aliasElemento));
		 */
		else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TIPO_ELEMENTO))
			condiciones.add(componerCondicionTipoElemento(
					busquedaElementosVO.getTipoElemento(), aliasElemento));
		else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_ELEMENTOS_EXCLUIR))
			condiciones.add(componerCondicionElementosExcluir(
					busquedaElementosVO.getElementosExcluir(), aliasElemento));
		else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_ELEMENTOS_RESTRINGIR))
			condiciones
					.add(componerCondicionElementosRestringir(
							busquedaElementosVO.getElementosRestringir(),
							aliasElemento));
		else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_SUBQUERY_ELEMENTOS_RESTRINGIR))
			condiciones.add(componerCondicionSubqueryElementosRestringir(
					busquedaElementosVO.getSubqueryElementosRestringir(),
					aliasElemento));
		else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CODIGO))
			condiciones.add(componerCondicionCodigo(
					busquedaElementosVO.getCodigo(), aliasElemento));
		else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NUMERO_EXPEDIENTE))
			// condiciones.add(componerCondicionCodigo(busquedaElementosVO.getNumeroExpediente(),
			// aliasElemento));
			condiciones.add(componerCondicionNumeroExpediente(
					busquedaElementosVO.getNumeroExpediente(),
					busquedaElementosVO.getCalificadorNumeroExpediente(),
					aliasElemento, cb.getRestricciones()));
		else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_DATO_NUMERICO))
			condiciones.add(componerCondicionesNumero(
					busquedaElementosVO.getNumero(),
					busquedaElementosVO.getNumeroComp(),
					busquedaElementosVO.getTipoMedida(),
					busquedaElementosVO.getUnidadMedida(), aliasElemento));
		else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_AMBITO)) {

			Map condicionesMap = new HashMap();
			//
			// if(ArrayUtils.isNotEmpty(busquedaElementosVO.getEstados())){
			// condicionesMap.put(ESTADO_ELEMENTO_FIELD,
			// busquedaElementosVO.getEstados());
			// }
			//
			// if(ArrayUtils.isNotEmpty(busquedaElementosVO.getNiveles())){
			// condicionesMap.put(NIVEL_FIELD,
			// busquedaElementosVO.getNiveles());
			// }

			ConsultaConnectBy consulta;
			try {
				consulta = componerCondicionIdAmbito(aliasElemento,
						busquedaElementosVO.getIdObjetoAmbito(), condicionesMap);
				if (consulta != null) {
					if (StringUtils.isNotEmpty(consulta.getWithClause())) {
						query.put(WITH_STATEMENT, consulta.getWithClause());
					}
					if (StringUtils.isNotEmpty(consulta.getSqlClause())) {
						condiciones.add(consulta.getSqlClause());
					}
				}
			} catch (IeciTdException e) {
				logger.error("Error al Generar la condición de ámbito", e);
			}

		}

		else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONDICIONES_AVANZADAS)) {
			String tablaCondsAvan = componerCondicionesAvanzadas(busquedaElementosVO);
			StringBuffer condsAvanS = new StringBuffer();
			boolean completarCondicion = false;
			if (StringUtils.isNotEmpty(tablaCondsAvan)) {
				tablas.add(tablaCondsAvan);
				condsAvanS.append(
						getCustomizedField(ID_ELEMENTO_FIELD, aliasElemento)
								.getQualifiedName())
						.append("=IDS.IDELEMENTOCF");
				completarCondicion = true;
			}

			if (completarCondicion
					|| cb.getRestriccion(RestriccionesCamposBusquedas.RESTRICCION_CAMPO_BUSQUEDA_FICHA) != null) {
				if (!StringUtils.isEmpty(busquedaElementosVO.getIdFicha())) {
					if (StringUtils.isNotEmpty(condsAvanS.toString()))
						condsAvanS.append(" AND ");
					condsAvanS.append(DBUtils.generateEQTokenField(
							getCustomizedField(IDFICHADESCR_FIELD,
									aliasElemento), busquedaElementosVO
									.getIdFicha()));
				}
			}
			condiciones.add(condsAvanS.toString());

		} else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_DESCRIPTORES)) {
			// Los descriptores pueden llevar un id de Campo, por lo que hay que
			// obtener su valor
			String idCampoKey = nombreCampo;
			CampoDescriptivoConfigBusqueda campoDCB = configBus
					.getCampoDescriptivo(idCampoKey);
			String idCampo = (campoDCB == null ? RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES
					: campoDCB.getValor());
			condiciones.add(componerCondicionesDescriptores(
					busquedaElementosVO.getDescriptores(), aliasElemento,
					idCampo));
		} else
		/*
		 * if
		 * (nombreCampo.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_ESTADO))
		 * condiciones
		 * .add(componerCondicionEstados(busquedaElementosVO.getEstados(),
		 * aliasElemento)); else if (nombreCampo.equals(CamposBusquedas.
		 * CAMPO_ENTRADA_BUSQUEDA_NIVELES_DESCRIPCION))
		 * condiciones.add(componerCondicionNiveles
		 * (busquedaElementosVO.getNiveles(), aliasElemento)); else
		 */
		if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_PRODUCTOR)) {
			String idCampoKey = nombreCampo;
			CampoDescriptivoConfigBusqueda campoDCB = configBus
					.getCampoDescriptivo(idCampoKey);
			String idCampo = (campoDCB == null ? RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES
					: campoDCB.getValor());
			condiciones.add(componerCondicionesDescriptores(
					busquedaElementosVO.getProductores(), aliasElemento,
					idCampo));
		} else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_PROCEDIMIENTO))
			condiciones.add(componerCondicionProcedimiento(
					busquedaElementosVO.getProcedimiento(), aliasElemento));
		else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FONDO))
			condiciones.add(componerCondicionIdFondo(
					busquedaElementosVO.getFondo(), aliasElemento));
		else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TEXTO)
				&& (cb != null)) {
			if (cb.getTipo().equals(CamposBusquedas.CAMPO_TIPO_TODOS_TEXTOS))
				condiciones.add(componerCondicionTexto(
						busquedaElementosVO.getTexto(), aliasElemento));
			else if (cb.getTipo().equals(
					CamposBusquedas.CAMPO_TIPO_TODOS_DESCRIPTORES)) {
				// condiciones.add(generarCondicionReferencia(configBus, cb,
				// busquedaElementosVO.getTexto(), aliasElemento));
				String tablaRefDesc = generarTablaReferencia(cb,
						busquedaElementosVO.getTexto(), true);
				if (StringUtils.isNotEmpty(tablaRefDesc)) {
					tablas.add(tablaRefDesc);
					StringBuffer condsRefDes = new StringBuffer();
					condsRefDes
							.append(getCustomizedField(ID_ELEMENTO_FIELD,
									aliasElemento).getQualifiedName()).append(
									"=REFDES.IDELEMENTOCF");
					condiciones.add(condsRefDes.toString());
				}

			} else if (cb.getTipo().equals(
					CamposBusquedas.CAMPO_TIPO_TODOS_TEXTOS_TODOS_DESCRIPTORES)) {
				String tablaTodosTextosTodosDescr = generarTablaTodosTextosTodosDescriptores(
						cb, busquedaElementosVO.getTexto());
				if (StringUtils.isNotEmpty(tablaTodosTextosTodosDescr)) {
					tablas.add(tablaTodosTextosTodosDescr);
					StringBuffer condsTodosTextosTodosDes = new StringBuffer();
					condsTodosTextosTodosDes
							.append(getCustomizedField(ID_ELEMENTO_FIELD,
									aliasElemento).getQualifiedName()).append(
									"=TTEXTOSTDESC.IDELEMENTOCF");
					condiciones.add(condsTodosTextosTodosDes.toString());
				}

				/*
				 * ArrayList condicionesAux = new ArrayList(); String
				 * condicionesAuxCompuestas = new String();
				 * condicionesAux.add(generarCondicionReferencia(configBus, cb,
				 * busquedaElementosVO.getTexto(), aliasElemento));
				 * condicionesAux
				 * .add(componerCondicionTexto(busquedaElementosVO.getTexto(),
				 * aliasElemento)); if (condicionesAux != null &&
				 * condicionesAux.size()>0) { String [] condicionesAuxS =
				 * listToStringArray(condicionesAux); condicionesAuxCompuestas =
				 * DBUtils.generateORTokens(condicionesAuxS); }
				 * condiciones.add(condicionesAuxCompuestas);
				 */
			}
		} else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_RANGOS)) {
			String idCampoInicialKey = nombreCampo + "InicialNorm";
			CampoDescriptivoConfigBusqueda campoDCBInicial = configBus
					.getCampoDescriptivo(idCampoInicialKey);
			String idCampoInicial = (campoDCBInicial == null ? RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES
					: campoDCBInicial.getValor());
			String idCampoFinalKey = nombreCampo + "FinalNorm";
			CampoDescriptivoConfigBusqueda campoDCBFinal = configBus
					.getCampoDescriptivo(idCampoFinalKey);
			String idCampoFinal = (campoDCBFinal == null ? RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES
					: campoDCBFinal.getValor());
			condiciones.add(componerCondicionTextoCorto(common.util.StringUtils
					.normalizarTexto(busquedaElementosVO.getRango()),
					aliasElemento, idCampoInicial, idCampoFinal));
		} else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_SIGNATURA))
			condiciones.add(componerCondicionSignatura(
					busquedaElementosVO.getSignatura(),
					busquedaElementosVO.getCalificadorSignatura(),
					busquedaElementosVO.getIdArchivo(), aliasElemento,
					cb.getRestricciones()));
		else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CODIGO_RELACION)
				&& (cb != null))
			condiciones.add(componerCondicionCodigoRelacion(
					busquedaElementosVO.getCodigoRelacion(), aliasElemento,
					cb.getRestricciones()));
		else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_IDARCHIVO))
			condiciones.add(componerCondicionIdArchivo(
					busquedaElementosVO.getIdArchivo(), aliasElemento));
		else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONTENIDO_FICHERO)) {
			tablas.add(DocDocumentoCFDBEntityImpl.TABLE_NAME);
			condiciones.add(DBUtils.generateJoinCondition(
					getCustomizedField(ID_ELEMENTO_FIELD, aliasElemento),
					DocDocumentoCFDBEntityImpl.CAMPO_ID_ELEMENTO_CF));
		} else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_GENERICO_TEXTO_CORTO)) {
			String aliasTablaTextoCorto = "TEXTOCORTO";
			String sqlTablaTextoCorto = componerCondicionGenericoTextoCorto(
					busquedaElementosVO.getGenericoTextoCorto(),
					busquedaElementosVO.getGenericoIdTextoCorto(),
					busquedaElementosVO.getGenericoOperadorTextoCorto());
			sqlTablaTextoCorto = componerTablaCamposGenericos(
					sqlTablaTextoCorto, aliasTablaTextoCorto);

			TableDef tablaTextoCorto = new TableDef(aliasTablaTextoCorto);
			DbColumnDef columnaIdTextoCorto = new DbColumnDef(tablaTextoCorto,
					TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO);
			tablas.add(sqlTablaTextoCorto);
			condiciones.add(DBUtils.generateJoinCondition(
					getCustomizedField(ID_ELEMENTO_FIELD, aliasElemento),
					columnaIdTextoCorto));
		} else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_GENERICO_TEXTO_LARGO)) {
			String aliasTablaTextoLargo = "TEXTOLARGO";
			String sqlTablaTextoLargo = componerCondicionGenericoTextoLargo(
					busquedaElementosVO.getGenericoTextoLargo(),
					busquedaElementosVO.getGenericoIdTextoLargo());
			sqlTablaTextoLargo = componerTablaCamposGenericos(
					sqlTablaTextoLargo, aliasTablaTextoLargo);

			TableDef tablaTextoLargo = new TableDef(aliasTablaTextoLargo);
			DbColumnDef columnaIdTextoLargo = new DbColumnDef(tablaTextoLargo,
					TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO);
			tablas.add(sqlTablaTextoLargo);
			condiciones.add(DBUtils.generateJoinCondition(
					getCustomizedField(ID_ELEMENTO_FIELD, aliasElemento),
					columnaIdTextoLargo));
		} else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_GENERICO_CAMPO_FECHA)) {
			String aliasTabla = "CAMPOFECHA";
			String sqlTabla = componerCondicionGenericoFecha(
					busquedaElementosVO.getGenericoIdFecha(),
					busquedaElementosVO.getGenericoFechaIni(),
					busquedaElementosVO.getGenericoFechaFin(),
					busquedaElementosVO.getGenericoFechaCalificador());
			sqlTabla = componerTablaCamposGenericos(sqlTabla, aliasTabla);

			TableDef tabla = new TableDef(aliasTabla);
			DbColumnDef columnaId = new DbColumnDef(tabla,
					FechaDBEntityImpl.CAMPO_ID_ELEMENTO);
			tablas.add(sqlTabla);
			condiciones.add(DBUtils.generateJoinCondition(
					getCustomizedField(ID_ELEMENTO_FIELD, aliasElemento),
					columnaId));
		} else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_GENERICO_CAMPO_NUMERICO)) {
			String aliasTabla = "CAMPONUMERICO";
			String sqlTabla = componerCondicionGenericoCampoNumerico(
					busquedaElementosVO.getGenericoCampoNumerico(),
					busquedaElementosVO.getGenericoCampoNumericoFin(),
					busquedaElementosVO.getGenericoIdCampoNumerico(),
					busquedaElementosVO.getGenericoOperadorCampoNumerico());
			sqlTabla = componerTablaCamposGenericos(sqlTabla, aliasTabla);

			TableDef tabla = new TableDef(aliasTabla);
			DbColumnDef columnaId = new DbColumnDef(tabla,
					NumeroDBEntityImpl.CAMPO_ID_ELEMENTO);
			tablas.add(sqlTabla);
			condiciones.add(DBUtils.generateJoinCondition(
					getCustomizedField(ID_ELEMENTO_FIELD, aliasElemento),
					columnaId));
		} else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_BLOQUEO)) {
			condiciones.add(componerCondicionBloqueos(
					busquedaElementosVO.getBloqueos(), aliasElemento));
		}

		query.put(TABLAS_QUERY_IDS, tablas);
		query.put(COLUMNAS_QUERY_IDS, columnas);
		query.put(CONDICIONES_QUERY_IDS, condiciones);

		return query;
	}

	public String componerCondicionUDocValidada() {
		StringBuffer qual = new StringBuffer();

		qual.append(DBUtils.generateEQTokenField(
				RelacionEntregaDBEntityBaseImpl.CAMPO_ESTADO,
				EstadoREntrega.VALIDADA.getIdentificador()));

		return qual.toString();
	}

	public String componerCondicionUDocEnDeposito(String aliasElemento,
			Map restricciones) {
		StringBuffer qual = new StringBuffer();

		qual.append(
				getCustomizedField(ID_ELEMENTO_FIELD, aliasElemento)
						.getQualifiedName()).append(" IN (SELECT DISTINCT ")
				.append(UnidadDocumentalDBEntityImpl.ID_ELEMENTOCF_COLUMN_NAME)
				.append(" FROM ");

		JoinDefinition[] joinsUDocEnUiUDoc = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(new TableDef(
						UDocEnUiDepositoDbEntityImpl.TABLE_NAME),
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD),
				new DbColumnDef(
						new TableDef(
								UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL),
						UnidadDocumentalDBEntityImpl.CAMPO_ID)) };

		qual.append(DBUtils.generateInnerJoinCondition(new TableDef(
				UDocEnUiDepositoDbEntityImpl.TABLE_NAME), joinsUDocEnUiUDoc));

		qual.append(")");

		return qual.toString();
	}

	public String componerCondicionUDocEnRelEntrega(String aliasElemento,
			Map restricciones) {
		StringBuffer qual = new StringBuffer();

		JoinDefinition[] joinsDocenuiDocre = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(new TableDef(
						UDocEnUiDepositoDbEntityImpl.TABLE_NAME),
						UDocEnUiDepositoDbEntityImpl.IDUDOCRE_FIELD),
				new DbColumnDef(new TableDef(
						UDocRelacionDBEntityImpl.TABLE_NAME),
						UDocRelacionDBEntityImpl.ID_FIELD)) };

		JoinDefinition[] joinsRelElem = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(new TableDef(TABLE_NAME_ELEMENTO),
						ID_ELEMENTO_FIELD), new DbColumnDef(new TableDef(
						RelacionEntregaDBEntityBaseImpl.TABLE_NAME),
						RelacionEntregaDBEntityBaseImpl.CAMPO_IDSERIEDESTINO)) };

		qual.append(
				getCustomizedField(ID_ELEMENTO_FIELD, aliasElemento)
						.getQualifiedName())
				.append(" IN (SELECT DISTINCT ")
				.append(UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_COLUMN_NAME)
				.append(" FROM ")
				.append(DBUtils.generateInnerJoinCondition(new TableDef(
						UDocEnUiDepositoDbEntityImpl.TABLE_NAME),
						joinsDocenuiDocre))
				.append(" , ")
				.append(DBUtils.generateInnerJoinCondition(new TableDef(
						TABLE_NAME_ELEMENTO), joinsRelElem))
				.append(" WHERE ")
				.append(DBUtils.generateJoinCondition(
						UDocRelacionDBEntityImpl.TABLE_NAME,
						UDocRelacionDBEntityImpl.ID_RELACION_FIELD,
						RelacionEntregaDBEntityBaseImpl.TABLE_NAME,
						RelacionEntregaDBEntityBaseImpl.CAMPO_ID));

		if (restricciones != null
				&& restricciones
						.containsKey(RestriccionesCamposBusquedas.RESTRICCION_CAMPO_BUSQUEDA_ESTADO_UDOC_VALIDADA)) {
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(
							RelacionEntregaDBEntityBaseImpl.CAMPO_ESTADO,
							EstadoREntrega.VALIDADA.getIdentificador()));
		}

		qual.append(")");

		return qual.toString();
	}

	public String componerTablaElemento(int orderColumn, Map mapCamposEntrada,
			Map mapCamposSalida, ArrayList camposRellenos,
			Map restriccionesBusqueda, String aliasElemento) {
		String tabla = new String();

		// Columna de ordenación
		DbColumnDef orderColumnDef = ElementoCFVO.getColsMappings()[orderColumn];

		TableDef tablaElemento = new TableDef(TABLE_NAME_ELEMENTO,
				aliasElemento);

		boolean joinNumExp = orderColumnDef.getName().equals(
				UnidadDocumentalDBEntityImpl.NUMERO_EXPEDIENTE_COLUMN_NAME);
		// La tabla principal de elemento va a estar presente siempre -
		// Dependiendo del caso, haremos left outer join de ella con otras
		// tablas
		if (joinNumExp) {

			JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
					new DbColumnDef(tablaElemento, ID_ELEMENTO_FIELD),
					new DbColumnDef(
							new TableDef(
									UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL),
							UnidadDocumentalDBEntityImpl.CAMPO_ID)) };

			// Por defecto se hace un left join, a no ser que sepamos que
			// obligatoriamente es una unidad documental => Inner Join
			tabla = DBUtils
					.generateLeftOuterJoinCondition(tablaElemento, joins);

			if (restriccionesBusqueda
					.containsKey(RestriccionesCamposBusquedas.RESTRICCION_CAMPO_BUSQUEDA_UDOC_ENRELENTREGA)) {
				tabla = DBUtils
						.generateInnerJoinCondition(tablaElemento, joins);
			} else {
				if (camposRellenos
						.contains(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NUMERO_EXPEDIENTE)) {
					CampoBusqueda cb = (CampoBusqueda) mapCamposEntrada
							.get(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NUMERO_EXPEDIENTE);

					if (cb != null) {
						if (cb.getRestriccion(RestriccionesCamposBusquedas.RESTRICCION_CAMPO_BUSQUEDA_UDOC_ENRELENTREGA) != null)
							tabla = DBUtils.generateInnerJoinCondition(
									tablaElemento, joins);
					}
				}
			}
		} else {
			if (orderColumnDef.getName().equals(
					UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_COLUMN_NAME)) {
				JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
						new DbColumnDef(tablaElemento, ID_ELEMENTO_FIELD),
						new DbColumnDef(new TableDef(
								UDocEnUiDepositoDbEntityImpl.TABLE_NAME),
								UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD)) };

				// Como en la lista de tablas sólo existe la tabla ECC1,
				// sustituimos ésta por el left join con ASGDUDOCENUI
				tabla = DBUtils.generateLeftOuterJoinCondition(tablaElemento,
						joins);

				if (restriccionesBusqueda
						.containsKey(RestriccionesCamposBusquedas.RESTRICCION_CAMPO_BUSQUEDA_UDOC_ENRELENTREGA)) {
					tabla = DBUtils.generateInnerJoinCondition(tablaElemento,
							joins);
				} else {
					if (camposRellenos
							.contains(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_SIGNATURA)) {
						CampoBusqueda cb = (CampoBusqueda) mapCamposEntrada
								.get(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_SIGNATURA);

						if (cb != null) {
							if (cb.getRestriccion(RestriccionesCamposBusquedas.RESTRICCION_CAMPO_BUSQUEDA_UDOC_ENRELENTREGA) != null)
								tabla = DBUtils.generateInnerJoinCondition(
										tablaElemento, joins);
						}
					}
				}
			} else if (orderColumnDef.getName().equals(
					NivelCFDBEntityImpl.NOMBRE_NIVEL_COLUMN_NAME)) {
				JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
						new DbColumnDef(tablaElemento, NIVEL_FIELD),
						new DbColumnDef(new TableDef(
								NivelCFDBEntityImpl.NIVELCF_TABLE_NAME),
								NivelCFDBEntityImpl.ID_NIVEL_FIELD)) };

				tabla = DBUtils
						.generateInnerJoinCondition(tablaElemento, joins);
				// listaCondiciones.add(DBUtils.generateJoinCondition(getCustomizedField(NIVEL_FIELD,
				// "ECC1"), NivelCFDBEntityImpl.ID_NIVEL_FIELD));
			} else if (orderColumnDef.getName().equals(
					FechaDBEntityImpl.FECHA_INICIAL_COLUMN_NAME)) {
				JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
						new DbColumnDef(tablaElemento, ID_ELEMENTO_FIELD),
						new DbColumnDef(new TableDef(
								FechaDBEntityImpl.TABLE_NAME),
								FechaDBEntityImpl.CAMPO_ID_ELEMENTO)) };

				tabla = DBUtils.generateLeftOuterJoinCondition(tablaElemento,
						joins);
				String idFechaInicial = ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionDescripcion().getFechaExtremaInicial();
				tabla += DBUtils.AND
						+ FechaDBEntityImpl.CAMPO_ID_CAMPO.getQualifiedName()
						+ Constants.EQUAL + "'" + idFechaInicial + "'";
			} else if (orderColumnDef.getName().equals(
					FechaDBEntityImpl.FECHA_FINAL_COLUMN_NAME)) {
				JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
						new DbColumnDef(tablaElemento, ID_ELEMENTO_FIELD),
						new DbColumnDef(new TableDef(
								FechaDBEntityImpl.TABLE_NAME),
								FechaDBEntityImpl.CAMPO_ID_ELEMENTO)) };

				tabla = DBUtils.generateLeftOuterJoinCondition(tablaElemento,
						joins);
				String idFechaFinal = ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionDescripcion().getFechaExtremaFinal();
				tabla += DBUtils.AND
						+ FechaDBEntityImpl.CAMPO_ID_CAMPO.getQualifiedName()
						+ Constants.EQUAL + "'" + idFechaFinal + "'";
			} else
				tabla = tablaElemento.getDeclaration();
		}

		if (!joinNumExp
				&& (camposRellenos
						.contains(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_BLOQUEO))) {
			// ||
			// mapCamposSalida.containsKey(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_BLOQUEO)))
			// {

			JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
					new DbColumnDef(tablaElemento, ID_ELEMENTO_FIELD),
					new DbColumnDef(
							new TableDef(
									UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL),
							UnidadDocumentalDBEntityImpl.CAMPO_ID)) };
			if (StringUtils.isEmpty(tabla)) {
				tabla = DBUtils.generateLeftOuterJoinCondition(tablaElemento,
						joins);
			} else {
				tabla += DBUtils.generateLeftOuterJoinChainedCondition(joins);
			}
		}
		return tabla;
	}

	public StringBuffer componerCondicionEspecialTitulo(BusquedaElementosVO vo) {

		StringBuffer tabla = new StringBuffer();

		tabla.append(DBUtils.SELECT).append(ID_ELEMENTO_COLUMN_NAME_S)
				.append(" AS ")
				.append(UnidadDocumentalDBEntityImpl.ID_ELEMENTOCF_COLUMN_NAME)
				.append(DBUtils.FROM).append(TABLE_NAME_ELEMENTO)
				.append(DBUtils.WHERE);

		if (!ArrayUtils.isEmpty(vo.getNiveles())) {
			tabla.append(
					componerCondicionNiveles(vo.getNiveles(),
							TABLE_NAME_ELEMENTO)).append(DBUtils.AND);
		}

		if (!ArrayUtils.isEmpty(vo.getEstados())) {
			tabla.append(
					componerCondicionEstados(vo.getEstados(),
							TABLE_NAME_ELEMENTO)).append(DBUtils.AND);
		}

		tabla.append(componerCondicionTitulo(vo.getTitulo(),
				TABLE_NAME_ELEMENTO));

		return tabla;
	}

	// Rellenar query para filtrar los campos a obtener en función de los
	// parámetros de búsqueda introducidos
	public Map fillQuery(Map mapCamposEntrada, Map mapCamposSalida,
			Map restriccionesBusqueda, BusquedaElementosVO vo) {
		Map query = new HashMap();
		ArrayList tablas = new ArrayList(); // ArrayList de Strings
		ArrayList columnas = new ArrayList(); // ArrayList de DbColumnDef
		ArrayList condiciones = new ArrayList(); // ArrayList de Strings
		ArrayList tablasCount = new ArrayList(); // Tablas necesarias para hacer
													// el COUNT antes de obtener
													// los resultados
		String withStatement = new String(); // Clausula With (Solo si hay
												// ambitos)
		int nofFechas = 1, nofElementos = 1;
		String aliasElementoPrincipal = "ECC" + nofElementos;

		DbColumnDef columnaIdElemento = getCustomizedField(ID_ELEMENTO_FIELD,
				aliasElementoPrincipal);

		// En el resultado vamos a obtener el id del elemento
		columnas.add(columnaIdElemento);

		String tablaElementoDef = componerTablaElemento(vo.getOrderColumn(),
				mapCamposEntrada, mapCamposSalida, vo.getCamposRellenos(),
				restriccionesBusqueda, aliasElementoPrincipal);
		tablas.add(tablaElementoDef);
		tablasCount.add(tablaElementoDef);

		// Condiciones de nivel - van a estar presentes siempre para evitar
		// sacar el nivel superior en la clasificación de fondos
		// Si la búsqueda tiene entre sus campos de entrada el título, se hará
		// un tratamiento especial por optimización de tiempo de búsqueda
		if (mapCamposEntrada.get(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TITULO) == null) {
			condiciones.add(componerCondicionNiveles(vo.getNiveles(),
					aliasElementoPrincipal));
			condiciones.add(componerCondicionEstados(vo.getEstados(),
					aliasElementoPrincipal));
		} else {
			List camposRellenos = vo.getCamposRellenos();
			boolean tituloRelleno = (camposRellenos != null)
					&& (camposRellenos
							.contains(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TITULO));

			if (!tituloRelleno) {
				condiciones.add(componerCondicionNiveles(vo.getNiveles(),
						aliasElementoPrincipal));
				condiciones.add(componerCondicionEstados(vo.getEstados(),
						aliasElementoPrincipal));
			} else if (mapCamposEntrada
					.get(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NIVELES_DESCRIPCION) != null) {
				condiciones.add(componerCondicionNiveles(vo.getNiveles(),
						aliasElementoPrincipal));
			}
		}

		nofElementos++;

		if (!ListUtils.isEmpty(vo.getCamposRellenos())) {
			Iterator it = vo.getCamposRellenos().iterator();
			ConfiguracionBusquedas configBus = ConfiguracionBusquedasFactory
					.getConfiguracionBusquedas();

			while (it.hasNext()) {
				String nombreCampo = (String) it.next();
				CampoBusqueda cb = (CampoBusqueda) mapCamposEntrada
						.get(nombreCampo);

				Map queryCampo = new HashMap();

				// Hay tratamientos que por el tipo de campo ya conocemos de
				// antemano, añadir las tablas y condiciones necesarias
				queryCampo = fillQueryCampoTratamientoDirecto(nombreCampo,
						configBus, cb, vo, aliasElementoPrincipal);

				ArrayList tablasCampo = queryCampo.get(TABLAS_QUERY_IDS) == null ? new ArrayList()
						: (ArrayList) queryCampo.get(TABLAS_QUERY_IDS);
				ArrayList condicionesCampo = queryCampo
						.get(CONDICIONES_QUERY_IDS) == null ? new ArrayList()
						: (ArrayList) queryCampo.get(CONDICIONES_QUERY_IDS);
				ArrayList condicionesCampoRefDes = new ArrayList();

				if (StringUtils.isBlank(withStatement)) {
					withStatement = queryCampo.get(WITH_STATEMENT) == null ? new String()
							: (String) queryCampo.get(WITH_STATEMENT);
				}

				// Tratar restricciones
				if (cb != null) {
					LinkedHashMap restricciones = cb.getRestricciones();
					Iterator itRes = restricciones.entrySet().iterator();
					while (itRes.hasNext()) {
						Entry rcbEntry = (Entry) itRes.next();
						RestriccionCampoBusqueda rcb = (RestriccionCampoBusqueda) rcbEntry
								.getValue();
						if (!StringUtils.isEmpty(rcb.getTipo())) {
							Short tipoRestriccion = new Short(rcb.getTipo());

							switch (tipoRestriccion.intValue()) {
							case RestriccionCampoBusqueda.RESTRICCION_TIPO_ESPECIAL_TITULO: {
								condicionesCampo.add(componerCondicionTitulo(
										vo.getTextoCampo(cb.getNombre()),
										aliasElementoPrincipal));
								break;
							}
							case RestriccionCampoBusqueda.RESTRICCION_TIPO_ESPECIAL_CODIGO: {
								condicionesCampo.add(componerCondicionCodigo(
										vo.getTextoCampo(cb.getNombre()),
										aliasElementoPrincipal));
								break;
							}
							case RestriccionCampoBusqueda.RESTRICCION_TIPO_ESPECIAL_CODIGO_REFERENCIA: {
								condicionesCampo
										.add(componerCondicionCodigoReferencia(
												vo.getTextoCampo(cb.getNombre()),
												aliasElementoPrincipal));
								break;
							}
							case RestriccionCampoBusqueda.RESTRICCION_TIPO_ESPECIAL_NUMERO_EXPEDIENTE: {
								condicionesCampo
										.add(componerCondicionNumeroExpediente(
												vo.getTextoCampo(cb.getNombre()),
												null, aliasElementoPrincipal,
												cb.getRestricciones()));
								break;
							}
							case RestriccionCampoBusqueda.RESTRICCION_TIPO_ESPECIAL_RANGOS: {
								/*
								 * String idCampoRangoKey = rcb.getId();
								 * CampoDescriptivoConfigBusqueda campoDCB =
								 * configBus
								 * .getCampoDescriptivo(idCampoRangoKey); String
								 * idCampoRango = (campoDCB == null ?
								 * RestriccionCampoBusqueda
								 * .RESTRICCION_IDCAMPO_TODOS_VALORES :
								 * campoDCB.getValor());
								 * condicionesCampo.add(componerCondicionTextoCorto
								 * (common.util.StringUtils.normalizarTexto(vo.
								 * getTextoCampo(cb.getNombre())),
								 * aliasElementoPrincipal, idCampoRango));
								 */

								String idCampoInicialKey = rcb.getId()
										+ "InicialNorm";
								CampoDescriptivoConfigBusqueda campoDCBInicial = configBus
										.getCampoDescriptivo(idCampoInicialKey);
								String idCampoInicial = (campoDCBInicial == null ? RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES
										: campoDCBInicial.getValor());
								String idCampoFinalKey = rcb.getId()
										+ "FinalNorm";
								CampoDescriptivoConfigBusqueda campoDCBFinal = configBus
										.getCampoDescriptivo(idCampoFinalKey);
								String idCampoFinal = (campoDCBFinal == null ? RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES
										: campoDCBFinal.getValor());
								condicionesCampo
										.add(componerCondicionTextoCorto(
												common.util.StringUtils.normalizarTexto(vo
														.getTextoCampo(cb
																.getNombre())),
												aliasElementoPrincipal,
												idCampoInicial, idCampoFinal));

								break;
							}
							case ValorCampoGenericoVO.TIPO_FECHA: {
								String idCampoKey = rcb.getId();
								CampoDescriptivoConfigBusqueda campoDCB = configBus
										.getCampoDescriptivo(idCampoKey);
								String idCampo = campoDCB == null ? RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES
										: campoDCB.getValor();
								String aliasFecha = "F" + nofFechas, aliasElemento = "ECC"
										+ nofElementos;
								String calificador = vo
										.getCalificadorFechaCampo(cb
												.getNombre());
								// String operador =
								// vo.getFechaOperadorCampo(cb.getNombre());
								Date fechaIni = vo.getFechaIniCampo(cb
										.getNombre()), fechaFin = vo
										.getFechaFinCampo(cb.getNombre());
								tablasCampo.add(// new TableDef(
										generarTablaFecha(cb, rcb, fechaIni,
												fechaFin, calificador,
												aliasElemento, aliasFecha,
												idCampo)// ,
										// aliasFecha
										// )
										);
								ArrayList condicionesCampoAux = new ArrayList();
								condicionesCampoAux
										.add(componerCondicionesFecha(fechaIni,
												fechaFin, calificador,
												aliasFecha, aliasElemento,
												idCampo));
								condicionesCampoAux
										.add(DBUtils
												.generateJoinCondition(
														getCustomizedField(
																FechaDBEntityImpl.CAMPO_ID_ELEMENTO,
																aliasFecha),
														getCustomizedField(
																ID_ELEMENTO_FIELD,
																aliasElementoPrincipal)));
								StringBuffer condicionesCampoAnd = condsToWhere(
										condicionesCampoAux, false);
								condicionesCampo.add(condicionesCampoAnd
										.toString());
								nofFechas++;
								nofElementos++;
								/*
								 * if (cb.getNombre().equals(CamposBusquedas.
								 * CAMPO_ENTRADA_BUSQUEDA_FECHA_INICIAL)) { //if
								 * (busquedaElementosVO.getFechaIniIni() != null
								 * || busquedaElementosVO.getFechaIniFin() !=
								 * null) tablasCampo.add(new
								 * TableDef(generarTablaFecha
								 * (cb,rcb,busquedaElementosVO
								 * .getFechaIniIni(),busquedaElementosVO
								 * .getFechaIniFin
								 * (),busquedaElementosVO.getCalificadorInicial
								 * ()),"FI")); } else if
								 * (cb.getNombre().equals(CamposBusquedas
								 * .CAMPO_ENTRADA_BUSQUEDA_FECHA_FINAL)) { //if
								 * (busquedaElementosVO.getFechaIniIni() != null
								 * || busquedaElementosVO.getFechaIniFin() !=
								 * null) tablasCampo.add(new
								 * TableDef(generarTablaFecha
								 * (cb,rcb,busquedaElementosVO
								 * .getFechaFinIni(),busquedaElementosVO
								 * .getFechaFinFin
								 * (),busquedaElementosVO.getCalificadorFinal
								 * ()),"FF")); } else if
								 * (cb.getNombre().equals(CamposBusquedas
								 * .CAMPO_ENTRADA_BUSQUEDA_FECHAS)) { //if
								 * (busquedaElementosVO.getFechaIniIni() != null
								 * || busquedaElementosVO.getFechaIniFin() !=
								 * null) tablasCampo.add(new
								 * TableDef(generarTablaFecha
								 * (cb,rcb,busquedaElementosVO
								 * .getFechaIni(),busquedaElementosVO
								 * .getFechaFin
								 * (),busquedaElementosVO.getCalificador
								 * ()),"F")); }
								 */
								break;
							}

							case ValorCampoGenericoVO.TIPO_REFERENCIA: {
								// Si el campo no es tipo texto sino ids, la
								// condición ya se ha añadido, se ignoran las
								// listas descriptoras
								if (cb.getTipo().equals(
										CamposBusquedas.CAMPO_TIPO_TEXTO)) {
									String idCampoKey = rcb.getId();
									CampoDescriptivoConfigBusqueda campoDCB = configBus
											.getCampoDescriptivo(idCampoKey);
									String idCampo = campoDCB == null ? RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES
											: campoDCB.getValor();
									condicionesCampoRefDes
											.add(generarCondicionRefDes(
													configBus, cb, rcb,
													vo.getTextoCampo(cb
															.getNombre()),
													idCampo));

									// ANTERIOR, FUNCIONA PERO TARDA MUCHO
									// condicionesCampo.add(generarCondicionReferencia(configBus,
									// cb, rcb,
									// vo.getTextoCampo(cb.getNombre()),
									// aliasElementoPrincipal, idCampo));
								}

								break;
							}

							case ValorCampoGenericoVO.TIPO_TEXTO_CORTO: {
								// Los rangos ya se han tratado en el
								// tratamiento directo porque tienen
								// características especiales
								if (!cb.getNombre()
										.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_RANGOS)) {
									String idCampoKey = rcb.getId();
									CampoDescriptivoConfigBusqueda campoDCB = configBus
											.getCampoDescriptivo(idCampoKey);
									String idCampo = campoDCB == null ? RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES
											: campoDCB.getValor();
									condicionesCampoRefDes
											.add(componerCondicionTextoCorto(vo
													.getTextoCampo(cb
															.getNombre()),
													idCampo));

									// ANTERIOR, FUNCIONA PERO TARDA MUCHO
									// condicionesCampo.add(componerCondicionTextoCorto(vo.getTextoCampo(cb.getNombre()),
									// aliasElementoPrincipal, idCampo));
								}
								break;
							}
							case ValorCampoGenericoVO.TIPO_TEXTO_LARGO: {
								String idCampoKey = rcb.getId();
								CampoDescriptivoConfigBusqueda campoDCB = configBus
										.getCampoDescriptivo(idCampoKey);
								String idCampo = campoDCB == null ? RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES
										: campoDCB.getValor();
								condicionesCampoRefDes
										.add(componerCondicionTextoLargo(vo
												.getTextoCampo(cb.getNombre()),
												idCampo));

								// ANTERIOR, FUNCIONA PERO TARDA MUCHO
								// condicionesCampo.add(componerCondicionTextoLargo(vo.getTextoCampo(cb.getNombre()),
								// aliasElementoPrincipal, idCampo));

								break;
							}
								// Esta restricción va a nivel global y unida
								// por AND al resto => va en el array
								// 'condiciones' directamente
								/*
								 * case RestriccionCampoBusqueda.
								 * RESTRICCION_TIPO_ESPECIAL_TABLA: { if
								 * (rcb.getId
								 * ().equals(RestriccionesCamposBusquedas
								 * .RESTRICCION_CAMPO_BUSQUEDA_UDOC_ENRELENTREGA
								 * )) {
								 * condiciones.add(componerCondicionUDocEnRelEntrega
								 * (aliasElementoPrincipal,
								 * cb.getRestricciones())); } break; }
								 */
							}// Switch
						} else {
							if (cb.getRestriccion(RestriccionesCamposBusquedas.RESTRICCION_CAMPO_BUSQUEDA_FICHA) != null) {
								// solo hay que añadir la restriccion del
								// identificador de la ficha
								// en caso de que no se haya seleccinado campo,
								// o este llegue vacio.
								if (!StringUtils.isEmpty(vo.getIdFicha())
										&& (vo.getCampo() == null
												|| vo.getCampo().length == 0 || (vo
												.getCampo().length == 1 && StringUtils
												.isEmpty(vo.getCampo()[0])))) {
									condicionesCampo
											.add(DBUtils
													.generateEQTokenField(
															getCustomizedField(
																	IDFICHADESCR_FIELD,
																	aliasElementoPrincipal),
															vo.getIdFicha()));
								}
							}
						}

					}// While restricciones
				}

				// Actualizar map que representa la query global
				// query = addCampoAQuery(query,queryCampo);

				// StringBuffer tablaRefDes = null;
				if (condicionesCampoRefDes != null
						&& condicionesCampoRefDes.size() > 0) {
					StringBuffer tablaRefDes = new StringBuffer("(");

					// Si el campo de búsqueda es el título, añadimos sus
					// condiciones especiales aquí
					if (nombreCampo
							.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TITULO)) {
						tablaRefDes.append(componerCondicionEspecialTitulo(vo))
								.append(DBUtils.UNION);
					}

					String[] condicionesCampoRefDesS = listToStringArray(condicionesCampoRefDes);

					tablaRefDes
							.append(DBUtils
									.generateUNIONTokens(condicionesCampoRefDesS))
							.append(DBUtils.UNION)
							.append(DBUtils
									.getUnionDummy(UnidadDocumentalDBEntityImpl.ID_ELEMENTOCF_COLUMN_NAME));

					tablaRefDes.append(") REFDESC ");

					tablasCampo.add(tablaRefDes.toString());

					StringBuffer condsRefDes = new StringBuffer(
							getCustomizedField(ID_ELEMENTO_FIELD,
									aliasElementoPrincipal).getQualifiedName())
							.append("=REFDESC.")
							.append(ReferenciaDBEntityImpl.IDELEMENTOCF_COLUMN_NAME);
					// condiciones.add(condsRefDes.toString());
					condicionesCampo.add(condsRefDes.toString());

				} else {

					// Si no hay condiciones de este tipo y el campo de búsqueda
					// es el título, hay que tratarlo como antes
					if (nombreCampo
							.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TITULO)) {
						condicionesCampo.add(componerCondicionTitulo(
								vo.getTitulo(), aliasElementoPrincipal));
						condiciones.add(componerCondicionNiveles(
								vo.getNiveles(), aliasElementoPrincipal));
						condiciones.add(componerCondicionEstados(
								vo.getEstados(), aliasElementoPrincipal));
					}
				}

				// si se trata de la busqueda de valores vacios del reemplazo
				// simple, hay que "arreglar"
				// las listas tablas, tablasCampo y condicionesCampo.
				fixQueryValoresVaciosCondicionesAvanzadas(
						aliasElementoPrincipal, vo, condicionesCampo, tablas,
						tablasCampo);

				String condicionesCampoCompuestas = new String();
				if (condicionesCampo != null && condicionesCampo.size() > 0) {
					String[] condicionesCampoS = listToStringArray(condicionesCampo);
					condicionesCampoCompuestas = DBUtils
							.generateORTokens(condicionesCampoS);
				}

				// tablasCampo = tableDefToDeclaration(tablasCampo);
				tablas.addAll(tablasCampo);
				tablasCount.addAll(tablasCampo);
				condiciones.add(condicionesCampoCompuestas);

			}// While campos
		}

		if (restriccionesBusqueda != null) // Mejorar esta condición, siempre
											// hay 2 campos al menos: estados y
											// niveles. Aplicar restricciones de
											// búsqueda si no existe ninguna
		{
			Iterator it = restriccionesBusqueda.entrySet().iterator();

			while (it.hasNext()) {
				Entry rcbEntry = (Entry) it.next();
				RestriccionCampoBusqueda rcb = (RestriccionCampoBusqueda) rcbEntry
						.getValue();
				if (!StringUtils.isEmpty(rcb.getTipo())) {
					Short tipoRestriccion = new Short(rcb.getTipo());

					switch (tipoRestriccion.intValue()) {
					case RestriccionCampoBusqueda.RESTRICCION_TIPO_ESPECIAL_TABLA: {
						if (rcb.getId()
								.equals(RestriccionesCamposBusquedas.RESTRICCION_CAMPO_BUSQUEDA_UDOC_ENRELENTREGA)
								&& !vo.getCamposRellenos()
										.contains(
												CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NUMERO_EXPEDIENTE)
								&& !vo.getCamposRellenos()
										.contains(
												CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CODIGO_RELACION)
								&& !vo.getCamposRellenos()
										.contains(
												CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_SIGNATURA)) {
							// condiciones.add(componerCondicionUDocEnRelEntrega(aliasElementoPrincipal,
							// restriccionesBusqueda));
							condiciones.add(componerCondicionUDocEnDeposito(
									aliasElementoPrincipal,
									restriccionesBusqueda));
						}
						break;
					}
					}
				}
			}
		}

		query.put(WITH_STATEMENT, withStatement);
		query.put(TABLAS_QUERY_IDS, tablas);
		query.put(COLUMNAS_QUERY_IDS, columnas);
		query.put(CONDICIONES_QUERY_IDS, condiciones);
		query.put(TABLAS_QUERY_IDS_COUNT, tablasCount);

		return query;
	}

	private void fixQueryValoresVaciosCondicionesAvanzadas(
			String aliasElementoPrincipal, BusquedaElementosVO vo,
			List condicionesCampo, List tablas, List tablasCampo) {
		// en caso de ser el campo actual condiciones avanzadas
		// -> reconocible por tener la cadena alias+"ID=IDS.IDELEMENTOCF"
		// Generar el NOT IN A partir del tablasCampo.get(0) quitandole el
		// " IDS" del final
		// Eliminar tablasCampo.get(0) tablas a añadir
		// String nuevaCondicion=(String)tablasCampo.get(0)

		// reconocer busqueda para obtener elementos con valores vacios.
		if (vo.getValor1().length > 0
				&& StringUtils.isEmpty(vo.getValor1()[0])
				&& (vo.getValor1A().length > 0
						&& StringUtils.isEmpty(vo.getValor1A()[0])
						&& vo.getValor1M().length > 0
						&& StringUtils.isEmpty(vo.getValor1D()[0])
						&& vo.getValor1D().length > 0
						&& StringUtils.isEmpty(vo.getValor1M()[0])
						&& vo.getValor1S().length > 0
						&& StringUtils.isEmpty(vo.getValor1S()[0])
						&& vo.getCampo().length > 0 && StringUtils
						.isNotEmpty(vo.getCampo()[0]))) {
			// se trata de una busqueda para obtener elementos con valores
			// vacios.
			if (condicionesCampo != null && condicionesCampo.size() > 0) {
				String whereCondicionesPosiblementeAvanzadas = (String) condicionesCampo
						.get(0);
				String cadComprobacionCondicionesAvanzadas = getCustomizedField(
						ID_ELEMENTO_FIELD, aliasElementoPrincipal)
						.getQualifiedName() + "=IDS.IDELEMENTOCF";

				int pos = whereCondicionesPosiblementeAvanzadas
						.indexOf(cadComprobacionCondicionesAvanzadas);
				if (pos != -1) {
					// Si entramos aqui se acaban de procesar las condiciones
					// avanzadas
					String whereCondicionesAvanzadas = whereCondicionesPosiblementeAvanzadas;

					// Obtener la subquery del listado de tablas
					String subQueryIdElementoConValorEnElCampo = (String) tablasCampo
							.get(0);

					// hay que eliminar esa subquery del from, ya que va a pasar
					// a estar en un NOT IN
					tablasCampo.remove(0);

					// Queda eliminar la condicion del INNER JOIN del where
					// Problema puede ir precedida por AND o seguida de AND o )
					// o ser la última del where, o combinaciones
					// no tiene sentido que la condicion de un INNER JOIN tenga
					// un OR a derecha o izquierda
					int posAnd = whereCondicionesAvanzadas.toUpperCase()
							.indexOf("AND", pos);
					// int
					// posOr=whereCondicionesAvanzadas.toUpperCase().indexOf("OR",pos);
					int posParentesis = whereCondicionesAvanzadas.indexOf(")",
							pos);

					String delimitador = "";
					int posDelimitadorFinal = 0;
					if (posParentesis != -1
							&& (posAnd == -1 || posParentesis < posAnd)) {
						delimitador = ")";
						posDelimitadorFinal = posParentesis;
					} else { // posParentesis > posAnd
						if (posAnd != -1) {
							delimitador = "AND";
							posDelimitadorFinal = posAnd;
						}
					}

					String nuevoWhere = "";
					int posWhere = whereCondicionesAvanzadas.toUpperCase()
							.substring(0, pos).lastIndexOf("WHERE ");
					if (posWhere == -1)
						posWhere = 0;
					posAnd = whereCondicionesAvanzadas.toUpperCase()
							.substring(posWhere, pos).lastIndexOf("AND");
					if (StringUtils.isEmpty(delimitador)
							|| delimitador.equals(")")) {
						// la condicion a eliminar esta al final de la cadena
						// comprobar si hay alguna condicion antes
						// posOr=whereCondicionesAvanzadas.toUpperCase().substring(posWhere,pos).lastIndexOf("OR");
						if (posAnd != -1) {
							nuevoWhere = whereCondicionesAvanzadas.substring(0,
									posAnd);
							if (delimitador.equals(")"))
								nuevoWhere += whereCondicionesAvanzadas
										.substring(posDelimitadorFinal);
						}
					} else {
						// hay mas condiciones posteriormente
						if (posAnd != -1)
							nuevoWhere = whereCondicionesAvanzadas.substring(0,
									posAnd);
						else
							posDelimitadorFinal += delimitador.length();

						nuevoWhere += whereCondicionesAvanzadas
								.substring(posDelimitadorFinal);
					}
					StringBuffer nuevaCondicion = new StringBuffer(nuevoWhere);
					if (nuevoWhere.length() > 6)
						nuevaCondicion.append(" AND ");

					// Eliminar de la subQuery el " IDS" utilizado como el alias
					// para el from de la antigua query
					String cadenaAEliminar = " IDS";
					int posIDS = subQueryIdElementoConValorEnElCampo
							.toUpperCase().lastIndexOf(cadenaAEliminar);
					String parteNotInDerecha = subQueryIdElementoConValorEnElCampo
							.substring(0, posIDS);

					// generar la condicion NOT IN y reemplazar la condicion
					// anterior por la nueva
					String parteNotInIzquierda = aliasElementoPrincipal + "."
							+ ID_ELEMENTO_COLUMN_NAME_S;
					nuevaCondicion.append(parteNotInIzquierda)
							.append(" NOT IN ").append(parteNotInDerecha);

					condicionesCampo.add(0, nuevaCondicion.toString());
					condicionesCampo.remove(1);
				}
			}
		}
	}

	// Rellenar query para generar los resultados de salida
	public Map fillQuery2(Map mapCampos, boolean udocsOnly) {
		Map query = new HashMap();
		ArrayList tablas = new ArrayList();
		ArrayList columnas = new ArrayList();
		ArrayList condiciones = new ArrayList();
		ConfiguracionBusquedas configBus = ConfiguracionBusquedasFactory
				.getConfiguracionBusquedas();

		int nofElemento = 1;
		String aliasElementoPrincipal = "ECC" + nofElemento;
		nofElemento++;

		String tablaElemento = (new TableDef(TABLE_NAME_ELEMENTO,
				aliasElementoPrincipal)).getDeclaration();
		DbColumnDef columnaId = getCustomizedField(ID_ELEMENTO_FIELD,
				aliasElementoPrincipal);
		DbColumnDef columnaTipo = getCustomizedField(TIPO_ELEMENTO_FIELD,
				aliasElementoPrincipal);
		DbColumnDef columnaSubtipo = getCustomizedField(SUBTIPO_ELEMENTO_FIELD,
				aliasElementoPrincipal);

		// La tabla principal de elemento va a estar presente siempre
		tablas.add(tablaElemento);

		// Las columnas de id y tipo de elemento se van a obtener siempre en los
		// resultados
		columnas.add(columnaId);
		columnas.add(columnaTipo);
		columnas.add(columnaSubtipo);

		if (mapCampos != null) {
			Iterator it = mapCampos.entrySet().iterator();
			boolean contieneTablaUDoc = false;
			while (it.hasNext()) {
				Entry cbEntry = (Entry) it.next();
				CampoBusqueda cb = (CampoBusqueda) cbEntry.getValue();
				ArrayList tablasCampo = new ArrayList(), condicionesCampo = new ArrayList(), columnasCampo = new ArrayList();
				int pos = -1;
				if ((pos = CamposBusquedas.esCampoSalidaTratamientoDirecto(cb
						.getNombre())) != -1) {
					DbColumnDef columna = CamposBusquedas
							.getCampoSalidaTratamientoDirecto(pos);
					columnasCampo.add(getCustomizedField(columna,
							aliasElementoPrincipal));
				} else {
					if (cb.getTipo().equals(CamposBusquedas.CAMPO_TIPO_FECHA)) {
						String sufijoAlias = "", aliasFecha = "";
						String idCampoKey = cb.getNombre();
						CampoDescriptivoConfigBusqueda campoDCB = configBus
								.getCampoDescriptivo(idCampoKey);
						String idCampo = campoDCB == null ? RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES
								: campoDCB.getValor();
						String aliasElemento = "ECC" + nofElemento;
						nofElemento++;

						if (cb.getNombre()
								.equals(CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_FECHA_INICIAL)) {
							aliasFecha = "FI";
							sufijoAlias = "FInicial";
						} else {
							if (cb.getNombre()
									.equals(CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_FECHA_FINAL)) {
								aliasFecha = "FF";
								sufijoAlias = "FFinal";
							}
						}

						tablasCampo.add(generarTablaFecha(aliasElemento,
								aliasFecha, idCampo));
						columnasCampo.add(new DbColumnDef(
								FechaDBEntityImpl.CAMPO_VALOR.getName()
										+ sufijoAlias, getCustomizedField(
										FechaDBEntityImpl.CAMPO_VALOR,
										aliasFecha)));
						columnasCampo.add(new DbColumnDef(
								FechaDBEntityImpl.CAMPO_CALIFICADOR.getName()
										+ sufijoAlias, getCustomizedField(
										FechaDBEntityImpl.CAMPO_CALIFICADOR,
										aliasFecha)));
						columnasCampo.add(new DbColumnDef(
								FechaDBEntityImpl.CAMPO_FORMATO.getName()
										+ sufijoAlias, getCustomizedField(
										FechaDBEntityImpl.CAMPO_FORMATO,
										aliasFecha)));
						condicionesCampo.add(DBUtils.generateJoinCondition(
								getCustomizedField(ID_ELEMENTO_FIELD,
										aliasElementoPrincipal),
								getCustomizedField(ID_ELEMENTO_FIELD,
										aliasElemento)));
					} else {
						if (cb.getNombre().equals(
								CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_FONDO)) {
							String aliasElemento = "ECC" + nofElemento;
							// TODO: ver cómo definir esto de otra forma
							String aliasCampoFondo = "nombreFondo";
							TableDef tablaFondo = new TableDef(
									TABLE_NAME_ELEMENTO, aliasElemento);
							tablasCampo.add(tablaFondo.getDeclaration());
							columnasCampo.add(new DbColumnDef(aliasCampoFondo,
									getCustomizedField(TITULO_FIELD,
											aliasElemento)));
							condicionesCampo.add(DBUtils.generateJoinCondition(
									getCustomizedField(IDFONDO_FIELD,
											aliasElementoPrincipal),
									getCustomizedField(ID_ELEMENTO_FIELD,
											aliasElemento)));
							nofElemento++;
						} else if (cb.getNombre().equals(
								CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_NIVEL)) {
							TableDef tablaNiveles = new TableDef(
									NivelCFDBEntityImpl.NIVELCF_TABLE_NAME);
							tablasCampo.add(tablaNiveles.getDeclaration());
							columnasCampo.add(new DbColumnDef(tablaNiveles,
									NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD));
							condicionesCampo.add(DBUtils.generateJoinCondition(
									getCustomizedField(NIVEL_FIELD,
											aliasElementoPrincipal),
									NivelCFDBEntityImpl.ID_NIVEL_FIELD));
						} else if (cb
								.getNombre()
								.equals(CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_NUMERO_EXPEDIENTE)
								|| cb.getNombre()
										.equals(CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_RANGOS_NUMERO_EXPEDIENTE)) {

							// TODO: Ojo a esta cambio! se cambia un join de 2
							// tablas por un producto natural
							// Se ha añadido este if para evitar error en el
							// caso concreto de que se hayan añadido los dos
							// campos a la vez a
							// la salida de una búsqueda que se añadiría la
							// misma tabla dos veces a la query
							if (!contieneTablaUDoc) {
								if (udocsOnly) {
									tablasCampo
											.add(UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL);
									columnasCampo
											.add(UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE);
									// Nuevo para préstamos
									columnasCampo
											.add(UnidadDocumentalDBEntityImpl.CAMPO_SISTEMA_PRODUCTOR);
									condicionesCampo
											.add(DBUtils
													.generateJoinCondition(
															getCustomizedField(
																	ID_ELEMENTO_FIELD,
																	aliasElementoPrincipal),
															UnidadDocumentalDBEntityImpl.CAMPO_ID));
								} else {
									String aliasElemento = "ECC" + nofElemento;
									tablasCampo
											.add(generarTablaUDoc(aliasElemento));
									columnasCampo
											.add(UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE);
									condicionesCampo
											.add(DBUtils
													.generateJoinCondition(
															getCustomizedField(
																	ID_ELEMENTO_FIELD,
																	aliasElementoPrincipal),
															getCustomizedField(
																	ID_ELEMENTO_FIELD,
																	aliasElemento)));
									nofElemento++;
								}
								contieneTablaUDoc = true;
							}
						} else if (cb
								.getNombre()
								.equals(CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_SIGNATURA)
								|| cb.getNombre()
										.equals(CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_SIGNATURA_OBLIGATORIA)) {
							// TODO: Ojo a esta cambio! se cambia un join de 2
							// tablas por un producto natural
							tablasCampo
									.add(UDocEnUiDepositoDbEntityImpl.TABLE_NAME);
							columnasCampo
									.add(UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD);
							// Lo necesitamos para los detalles del préstamo
							columnasCampo
									.add(UDocEnUiDepositoDbEntityImpl.IDENTIFICACION_FIELD);
							columnasCampo
									.add(getCustomizedField(
											ElementoCuadroClasificacionDBEntityImplBase.IDFONDO_FIELD,
											aliasElementoPrincipal));
							condicionesCampo
									.add(DBUtils
											.generateJoinCondition(
													getCustomizedField(
															ID_ELEMENTO_FIELD,
															aliasElementoPrincipal),
													UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD));

							// Comprobamos si se va a sacar también el campo
							// número expediente o el campo rangos + número de
							// expediente
							// , con lo que éste ya metería la tabla de Udocs, y
							// si no está, tendríamos que meterla aquí y
							// seleccionar
							// el campo que queremos sacar, el sistema productor
							if (!mapCampos
									.containsKey(CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_NUMERO_EXPEDIENTE)
									&& !mapCampos
											.containsKey(CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_RANGOS_NUMERO_EXPEDIENTE)) {

								// TODO: Ojo a esta cambio! se cambia un join de
								// 2 tablas por un producto natural
								tablasCampo
										.add(UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL);
								condicionesCampo
										.add(DBUtils
												.generateJoinCondition(
														getCustomizedField(
																ID_ELEMENTO_FIELD,
																aliasElementoPrincipal),
														UnidadDocumentalDBEntityImpl.CAMPO_ID));
							}
							// Lo necesitamos para los detalles del préstamo
							columnasCampo
									.add(UnidadDocumentalDBEntityImpl.CAMPO_SISTEMA_PRODUCTOR);
						}
					}
				}

				String condicionesCampoCompuestas = new String();
				if (condicionesCampo != null && condicionesCampo.size() > 0) {
					String[] condicionesCampoS = listToStringArray(condicionesCampo);
					condicionesCampoCompuestas = DBUtils
							.generateANDTokens(condicionesCampoS);
				}

				tablas.addAll(tablasCampo);
				columnas.addAll(columnasCampo);
				condiciones.add(condicionesCampoCompuestas);
			}
		}

		query.put(TABLAS_QUERY_IDS, tablas);
		query.put(COLUMNAS_QUERY_IDS, columnas);
		query.put(CONDICIONES_QUERY_IDS, condiciones);

		return query;
	}

	public StringBuffer getWhereFondo(Map mapCampos) {
		StringBuffer whereFondo = new StringBuffer();

		CampoBusqueda fondo = (CampoBusqueda) mapCampos
				.get(CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_FONDO);

		if (fondo != null)
			whereFondo// .append(" AND ")
					.append(DBUtils.generateJoinCondition(
							getCustomizedField(IDFONDO_FIELD, "A"),
							getCustomizedField(ID_ELEMENTO_FIELD, "C")));

		return whereFondo;

	}

	public StringBuffer getWhereNivel(Map mapCampos) {
		StringBuffer sqlWhere = new StringBuffer();

		CampoBusqueda nivel = (CampoBusqueda) mapCampos
				.get(CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_NIVEL);

		if (nivel != null)
			sqlWhere.append(DBUtils.generateJoinCondition(
					NivelCFDBEntityImpl.ID_NIVEL_FIELD,
					getCustomizedField(NIVEL_FIELD, "A")));

		return sqlWhere;
	}

	public StringBuffer condsToWhere(ArrayList conds, boolean addWhere) {
		StringBuffer sqlWhere = new StringBuffer();
		Iterator it = conds.iterator();

		int cont = 0;

		while (it.hasNext()) {
			String st = (String) it.next();
			if (StringUtils.isNotEmpty(st)) {
				if (cont == 0) {
					if (addWhere)
						sqlWhere.append(" WHERE ");

					sqlWhere.append(st);
				} else
					sqlWhere.append(" AND ").append(st);

				cont++;
			}
		}

		return sqlWhere;
	}

	public String[] listToStringArray(ArrayList list) {
		String[] ret;
		ArrayList aux = new ArrayList();
		Iterator it = list.iterator();

		while (it.hasNext()) {
			String st = (String) it.next();
			if (st != null && st.length() > 0)
				aux.add(st);
		}

		ret = new String[aux.size()];

		Iterator it2 = aux.iterator();
		int cont = 0;
		while (it2.hasNext()) {
			ret[cont] = (String) it2.next();
			cont++;
		}

		return ret;
	}

	public StringBuffer getWhereFecha(Map mapCampos) {
		StringBuffer sqlWhere = new StringBuffer();

		// Condiciones de fecha inicial
		CampoBusqueda fechaIni = (CampoBusqueda) mapCampos
				.get(CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_FECHA_INICIAL);
		CampoBusqueda fechaFin = (CampoBusqueda) mapCampos
				.get(CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_FECHA_FINAL);

		// Si hay que sacar fecha inicial y fecha final, hay que hacer el join
		// de ids de elementos de la lista de tablas del select
		// A.idelemento -> ids de elementos cuya fecha inicial cumple las conds
		// B.idelemento -> ids de elementos cuya fecha final cumple las conds
		if (fechaIni != null && fechaFin != null)
			sqlWhere.append(DBUtils.generateJoinCondition(
					getCustomizedField(ID_ELEMENTO_FIELD, "A"),
					getCustomizedField(ID_ELEMENTO_FIELD, "B")));

		return sqlWhere;

	}

	public StringBuffer getWhereBusqueda(Map mapCampos, boolean addWhere) {
		StringBuffer sqlWhere = new StringBuffer();
		ArrayList conds = new ArrayList();

		if (mapCampos != null) {
			// Condición de join con fondo
			conds.add(getWhereFondo(mapCampos).toString());

			// Condición de join con niveles
			conds.add(getWhereNivel(mapCampos).toString());

			// Condición de join con las fechas
			conds.add(getWhereFecha(mapCampos).toString());

			sqlWhere = condsToWhere(conds, addWhere);
		}

		return sqlWhere;
	}

	public List getElementosCuadroClasificacionYPartes(String[] idsYSignaturas,
			BusquedaElementosVO busquedaElementosVO, Busqueda busqueda)
			throws TooManyResultsException {
		ArrayList ids = new ArrayList();
		ArrayList signaturas = new ArrayList();
		if (idsYSignaturas != null && idsYSignaturas.length > 0) {
			for (int i = 0; i < idsYSignaturas.length; i++) {
				String idYSignatura = idsYSignaturas[i];

				StringOwnTokenizer st = new StringOwnTokenizer(idYSignatura,
						Constants.SEPARADOR_IDENTIFICADOR_BUSQUEDAS);
				if (st != null && st.countTokens() == 2) {
					while (st.hasNext()) {
						String id = st.nextToken();
						String signatura = new String();
						if (st.hasNext())
							signatura = st.nextToken();
						ids.add(id);
						signaturas.add(signatura);
					}
				}
			}
		}

		String aliasTablaPrincipal = "ELEMENTOCF";

		String[] arrayIds = (String[]) ids.toArray(new String[] {});
		String[] arraySignaturas = (String[]) signaturas
				.toArray(new String[] {});

		BusquedaFondosQueryVO busquedaFondosQueryVO = BusquedasGeneratorHelper
				.getQueryElementos(getConnection(), arrayIds, arraySignaturas,
						busquedaElementosVO, busqueda, aliasTablaPrincipal,
						busqueda.isResultadoSinElementosBloqueados(), true);

		return getVOS(
				busquedaFondosQueryVO.getWhere()
						+ busquedaFondosQueryVO.getOrderByFullQuery(),
				busquedaFondosQueryVO.getTablesFullQuery(),
				busquedaFondosQueryVO.getColsFullQueryFill(),
				ElementoCFVO.class);

	}

	public List getElementosCuadroClasificacion(String[] ids,
			BusquedaElementosVO busquedaElementosVO, Busqueda busqueda)
			throws TooManyResultsException {
		String aliasTablaPrincipal = "ELEMENTOCF";
		BusquedaFondosQueryVO busquedaFondosQueryVO = BusquedasGeneratorHelper
				.getQueryElementos(getConnection(), ids, null,
						busquedaElementosVO, busqueda, aliasTablaPrincipal,
						busqueda.isResultadoSinElementosBloqueados(), false);

		return getVOS(
				busquedaFondosQueryVO.getWhere()
						+ busquedaFondosQueryVO.getOrderByFullQuery(),
				busquedaFondosQueryVO.getTablesFullQuery(),
				busquedaFondosQueryVO.getColsFullQueryFill(),
				ElementoCFVO.class);
	}

	public List getElementosCFVOByIds(String[] ids) {
		String qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateInTokenField(ID_ELEMENTO_FIELD, ids))
				.toString();

		return getElementosCFVO(qual, HINT_IDPADRE_TITULO);
	}

	private List getElementosCFVO(final String qual, final String hint) {
		/*
		 * SELECT ASGFELEMENTOCF.id, ASGFELEMENTOCF.titulo,
		 * ASGFELEMENTOCF.idfondo, ASGDUDOCENUI.SIGNATURAUDOC,
		 * ASGDUDOCENUI.IDENTIFICACION, ASGFUNIDADDOC.numexp,
		 * ASGFUNIDADDOC.codsistproductor FROM
		 * ASGFELEMENTOCF,ASGFUNIDADDOC,ASGDUDOCENUI WHERE
		 * (ASGFELEMENTOCF.id=asgfunidaddoc.idelementocf ) AND
		 * (ASGFELEMENTOCF.id=ASGDUDOCENUI.IDUNIDADDOC ) AND ((ASGFELEMENTOCF.id
		 * IN ('0b7124c3c0c8d0000000000000000374'))) AND ((signaturaudoc IN
		 * ('1/1')))
		 */

		TableDef tablaElementoCf = new TableDef(
				ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO);

		DbColumnDef[] colDefs = new DbColumnDef[] {
				ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD,
				ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD,
				ElementoCuadroClasificacionDBEntityImpl.IDFONDO_FIELD,
				UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD,
				UDocEnUiDepositoDbEntityImpl.IDENTIFICACION_FIELD,
				UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE,
				UnidadDocumentalDBEntityImpl.CAMPO_SISTEMA_PRODUCTOR };

		JoinDefinition[] joins = new JoinDefinition[] {
				new JoinDefinition(
						ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD,
						UnidadDocumentalDBEntityImpl.CAMPO_ID),

				new JoinDefinition(
						ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD,
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD)

		};

		StringBuffer sqlCompleta = new StringBuffer(DBUtils.SELECT)
				.append(DbUtil.getColumnNames(colDefs))
				.append(DBUtils.FROM)
				.append(DBUtils.generateInnerJoinCondition(tablaElementoCf,
						joins));

		if (StringUtils.isNotEmpty(qual)) {
			sqlCompleta.append(qual);
		}

		SigiaDbOutputRecordset rowSet = new SigiaDbOutputRecordset(colDefs,
				ElementoCFVO.class);

		String sql = sqlCompleta.toString();

		return getVOS(sql, ElementoCFVO.class, rowSet);

	}

	protected List getElementosCF(final String qual, final String hint) {
		final ArrayList rows = new ArrayList();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbSelectFns.select(conn, TABLE_NAME_ELEMENTO,
						COLUM_NAMES_ELEMCF_LIST, qual, false, hint,
						new AbstractDbOutputRecordSet() {
							DbOutputRecord aRow = new DbOutputRecord() {
								public void getStatementValues(
										DbOutputStatement stmt)
										throws Exception {
									ElementoCuadroClasificacion unElmento = new ElementoCuadroClasificacion(
											null);
									DBUtils.fillVoWithDbOutputStament(
											COLS_DEFS_ELEMENTO, stmt, unElmento);
									rows.add(unElmento);
								}
							};

							public DbOutputRecord newRecord() throws Exception {
								return aRow;
							}
						});
			}

		};

		command.execute();

		return rows.size() > 0 ? rows : null;

	}

	protected ElementoCuadroClasificacionVO getElementoCF(final String qual) {
		return (ElementoCuadroClasificacionVO) getVO(qual, TABLE_NAME_ELEMENTO,
				COLS_DEFS_ELEMENTO, ElementoCuadroClasificacion.class);
	}

	public IElementoCuadroClasificacion getElementoCuadroClasificacion(
			String idElemento) {

		final StringBuffer qual = new StringBuffer().append("where ").append(
				DBUtils.generateEQTokenField(ID_ELEMENTO_FIELD, idElemento));

		return (IElementoCuadroClasificacion) getVO(qual.toString(),
				TABLE_NAME_ELEMENTO, COLS_DEFS_ELEMENTO,
				ElementoCuadroClasificacion.class);
	}

	public IElementoCuadroClasificacion getElementoCuadroClasificacionConFechas(
			String idElemento) {

		TableDef tableF1 = new TableDef(FechaDBEntityImpl.TABLE_NAME, "f1");
		TableDef tableF2 = new TableDef(FechaDBEntityImpl.TABLE_NAME, "f2");

		String tables = DBUtils.generateTableSet(new String[] {
				TABLE_NAME_ELEMENTO, tableF1.getDeclaration(),
				tableF2.getDeclaration() });

		DbColumnDef colFechaIni = new DbColumnDef("fechaInicial",
				tableF1.getAlias(), FechaDBEntityImpl.CAMPO_FECHA_INICIAL);
		DbColumnDef colFechaFin = new DbColumnDef("fechaFinal",
				tableF2.getAlias(), FechaDBEntityImpl.CAMPO_FECHA_FINAL);
		DbColumnDef colIdCampoFechaIni = new DbColumnDef(tableF1,
				FechaDBEntityImpl.CAMPO_ID_CAMPO);
		DbColumnDef colIdCampoFechaFin = new DbColumnDef(tableF2,
				FechaDBEntityImpl.CAMPO_ID_CAMPO);

		DbColumnDef[] cols = (DbColumnDef[]) ArrayUtils.addAll(
				COLS_DEFS_ELEMENTO, new DbColumnDef[] { colFechaIni,
						colFechaFin });

		final ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();
		final String ID_CAMPO_FECHA_INICIAL = csa.getConfiguracionDescripcion()
				.getFechaExtremaInicial();
		final String ID_CAMPO_FECHA_FINAL = csa.getConfiguracionDescripcion()
				.getFechaExtremaFinal();

		final StringBuffer qual = new StringBuffer()
				.append("where ")
				.append(DBUtils.generateEQTokenField(ID_ELEMENTO_FIELD,
						idElemento))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(tableF1.getAlias(),
						FechaDBEntityImpl.CAMPO_ID_ELEMENTO,
						TABLE_NAME_ELEMENTO, ID_ELEMENTO_FIELD))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(colIdCampoFechaIni,
						ID_CAMPO_FECHA_INICIAL))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(tableF2.getAlias(),
						FechaDBEntityImpl.CAMPO_ID_ELEMENTO,
						TABLE_NAME_ELEMENTO, ID_ELEMENTO_FIELD))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(colIdCampoFechaFin,
						ID_CAMPO_FECHA_FINAL));

		return (IElementoCuadroClasificacion) getVO(qual.toString(), tables,
				cols, ElementoCuadroClasificacion.class);
	}

	public ElementoCuadroClasificacionVO getElementoCFXCodigo(String codigo,
			String idPadre) {
		final StringBuffer qual = new StringBuffer("where ")
				.append(DBUtils.generateEQTokenField(CODIGO_FIELD, codigo))
				.append(" and ")
				.append(DBUtils.generateEQTokenField(IDPADRE_FIELD, idPadre));
		final ElementoCuadroClasificacion elementoCuadro = new ElementoCuadroClasificacion();
		final SigiaDbOutputRecord outputRecord = new SigiaDbOutputRecord(
				elementoCuadro, COLS_DEFS_ELEMENTO);
		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				DbSelectFns.select(conn, TABLE_NAME_ELEMENTO,
						COLUM_NAMES_ELEMCF_LIST, qual.toString(), outputRecord);

			}

		};

		command.execute();

		return command.isExecuteOk() ? elementoCuadro : null;

	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * fondos.db.IElementoCuadroClasificacionDbEntity#getElementoCFXCodigo(java
	 * .lang.String)
	 */
	public ElementoCuadroClasificacionVO getElementoCFXCodigo(String codigo) {
		final StringBuffer qual = new StringBuffer("where ").append(DBUtils
				.generateEQTokenField(CODIGO_FIELD, codigo));
		final ElementoCuadroClasificacion elementoCuadro = new ElementoCuadroClasificacion();
		final SigiaDbOutputRecord outputRecord = new SigiaDbOutputRecord(
				elementoCuadro, COLS_DEFS_ELEMENTO);
		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				DbSelectFns.select(conn, TABLE_NAME_ELEMENTO,
						COLUM_NAMES_ELEMCF_LIST, qual.toString(), outputRecord);

			}

		};

		command.execute();

		return command.isExecuteOk() ? elementoCuadro : null;
	}

	/**
	 * Obtiene la información del elementod el cuadro de clasificación.
	 *
	 * @param codReferencia
	 *            Código de referencia
	 * @return Un elemento del cuadro por el código de referencia.
	 */
	public ElementoCuadroClasificacionVO getElementoCuadroClasificacionXCodReferencia(
			String codReferencia) {
		final StringBuffer qual = new StringBuffer("where ").append(DBUtils
				.generateEQTokenField(CODIGO_REFERENCIA_FIELD, codReferencia));
		final ElementoCuadroClasificacion elementoCuadro = new ElementoCuadroClasificacion();
		final SigiaDbOutputRecord outputRecord = new SigiaDbOutputRecord(
				elementoCuadro, COLS_DEFS_ELEMENTO);
		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				DbSelectFns.select(conn, TABLE_NAME_ELEMENTO,
						COLUM_NAMES_ELEMCF_LIST, qual.toString(), outputRecord);

			}

		};

		command.execute();

		return command.isExecuteOk() ? elementoCuadro : null;
	}

	public IElementoCuadroClasificacion insertElementoCF(
			final ElementoCuadroClasificacionVO elementoVO) {

		// if (StringUtils.isNotBlank(elementoVO.getTitulo()))
		// elementoVO.setTitulo(elementoVO.getTitulo().toUpperCase());

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				elementoVO.setId(getGuid(elementoVO.getId()));
				SigiaDbInputRecord row = new SigiaDbInputRecord(
						COLS_DEFS_ELEMENTO, elementoVO);
				DbInsertFns.insert(conn, TABLE_NAME_ELEMENTO,
						COLUM_NAMES_ELEMCF_LIST, row);

			}
		};
		command.execute();
		return (ElementoCuadroClasificacion) elementoVO;
	}

	public void updateElementoCF(String idElementoCF, String nivel,
			String codigo, String denominacion, String codOrdenacion) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(ID_ELEMENTO_FIELD, idElementoCF));
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(NIVEL_FIELD, nivel);
		// if (StringUtils.isNotEmpty(codigo))
		colsToUpdate.put(CODIGO_FIELD, codigo);
		// if (StringUtils.isNotEmpty(codigoReferencia))
		// colsToUpdate.put(CODIGO_REFERENCIA_FIELD, codigoReferencia);
		// if (StringUtils.isNotEmpty(codigoReferenciaFondo))
		// colsToUpdate.put(CODREFFONDO_FIELD, codigoReferenciaFondo);

		if (StringUtils.isNotEmpty(denominacion))
			// colsToUpdate.put(TITULO_FIELD, denominacion.toUpperCase());
			colsToUpdate.put(TITULO_FIELD, denominacion);
		if (StringUtils.isNotEmpty(codOrdenacion))
			colsToUpdate.put(ORDEN_POSICION_FIELD, codOrdenacion);

		updateFields(qual.toString(), colsToUpdate, TABLE_NAME_ELEMENTO);
	}

	public void updateElementosCF(List idsElementoCF, String nombreCampo,
			String nuevoValor) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateInTokenField(ID_ELEMENTO_FIELD, idsElementoCF));
		Map colsToUpdate = new HashMap();

		if (StringUtils.isNotEmpty(nuevoValor))
			colsToUpdate.put(nombreCampo, nuevoValor);
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME_ELEMENTO);
	}

	public void updateEstadoElementoCF(String id, final int estado) {
		final String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(ID_ELEMENTO_FIELD, id))
				.toString();

		HashMap mapColumnsToUpdate = new HashMap();
		mapColumnsToUpdate.put(ESTADO_ELEMENTO_FIELD, new Integer(estado));

		updateFields(qual, mapColumnsToUpdate, TABLE_NAME_ELEMENTO);

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see fondos.db.IElementoCuadroClasificacionDbEntity#updateEstadoEliminacionElementoCF(java.lang.String,
	 *      int, java.lang.String)
	 */
	public void updateEstadoEliminacionElementoCF(String id, final int estado,
			String idEliminacion) {
		final String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_ELEMENTO_FIELD, id))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(ID_ELIMINACION_FIELD, null))
				.toString();

		HashMap mapColumnsToUpdate = new HashMap();
		mapColumnsToUpdate.put(ESTADO_ELEMENTO_FIELD, new Integer(estado));
		mapColumnsToUpdate.put(ID_ELIMINACION_FIELD, idEliminacion);

		updateFields(qual, mapColumnsToUpdate, TABLE_NAME_ELEMENTO);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see fondos.db.IElementoCuadroClasificacionDbEntity#updateEstadoEliminacionElementoCFSubquery(java.lang.String,
	 *      int, java.lang.String)
	 */
	public void updateEstadoEliminacionElementoCFSubquery(String subqueryIds,
			final int estado, String idEliminacion) {
		final String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateInTokenFieldSubQuery(ID_ELEMENTO_FIELD,
						subqueryIds))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(ID_ELIMINACION_FIELD, null))
				.toString();

		HashMap mapColumnsToUpdate = new HashMap();
		mapColumnsToUpdate.put(ESTADO_ELEMENTO_FIELD, new Integer(estado));
		mapColumnsToUpdate.put(ID_ELIMINACION_FIELD, idEliminacion);

		updateFields(qual, mapColumnsToUpdate, TABLE_NAME_ELEMENTO);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see fondos.db.IElementoCuadroClasificacionDbEntity#updateEstadoEliminacionElementoCF(java.lang.String[],
	 *      int, java.lang.String)
	 */
	public void updateEstadoEliminacionElementoCF(String[] ids,
			final int estado, String idEliminacion) {
		final String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(ID_ELEMENTO_FIELD, ids))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(ID_ELIMINACION_FIELD, null))
				.toString();

		HashMap mapColumnsToUpdate = new HashMap();
		mapColumnsToUpdate.put(ESTADO_ELEMENTO_FIELD, new Integer(estado));
		mapColumnsToUpdate.put(ID_ELIMINACION_FIELD, idEliminacion);

		updateFields(qual, mapColumnsToUpdate, TABLE_NAME_ELEMENTO);
	}

	private void deleteElementoCFXQual(final String qual) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME_ELEMENTO, qual);

			}
		};

		command.execute();
	}

	public void deleteElementoCF(String id) {
		final String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(ID_ELEMENTO_FIELD, id))
				.toString();

		deleteElementoCFXQual(qual);

	}

	public void deleteElementosCFXIdFondoExceptFondo(String idFondo) {

		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDFONDO_FIELD, idFondo))
				.append(" AND ")
				.append(DBUtils.generateNEQTokenField(ID_ELEMENTO_FIELD,
						idFondo)).toString();

		deleteElementoCFXQual(qual);

	}

	public void updateCodRefFondoElementoCF(String idFondo,
			String newCodRefFondo) {
		String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateEQTokenField(IDFONDO_FIELD, idFondo))
				.toString();
		HashMap columnsToUpdate = new HashMap();
		columnsToUpdate.put(IDFONDO_FIELD, idFondo);
		columnsToUpdate.put(CODREFFONDO_FIELD, newCodRefFondo);

		updateFields(qual, columnsToUpdate, TABLE_NAME_ELEMENTO);

	}

	public String getCodRefElementoCfFunction(final String id) {

		ConfiguracionFondos config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos();
		String separador = config.getDelimitadorCodigoReferencia();

		String qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(ID_ELEMENTO_FIELD, id)).toString();

		StringBuffer columnText = new StringBuffer()
				.append(GET_COD_REF_FUNCTION).append(DBUtils.ABRIR_PARENTESIS)
				.append(ID_ELEMENTO_FIELD).append(Constants.COMMA)
				.append(DBUtils.COMILLA_SIMPLE).append(separador)
				.append(DBUtils.COMILLA_SIMPLE)
				.append(DBUtils.CERRAR_PARENTESIS);

		DbColumnDef column = new DbColumnDef(CODREFERENCIA_COLUMN_NAME,
				columnText.toString(), DbDataType.SHORT_TEXT);
		DbColumnDef[] cols = new DbColumnDef[] { column };

		ElementoCuadroClasificacionVO elemento = (ElementoCuadroClasificacionVO) getVO(
				qual, TABLE_NAME_ELEMENTO, cols,
				ElementoCuadroClasificacion.class);
		if (elemento != null) {
			return elemento.getCodReferencia();
		} else {
			return null;
		}
	}

	public void updateTieneDescrElementoCF(String id, String tieneDescr) {
		String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateEQTokenField(ID_ELEMENTO_FIELD, id)).toString();

		HashMap columnsToUpdate = new HashMap();
		columnsToUpdate.put(TIENEDESCR_FIELD, tieneDescr);

		updateFields(qual, columnsToUpdate, TABLE_NAME_ELEMENTO);

	}

	public void updateEditClfDocsElementoCF(String id, String editClfDocs) {
		final String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateEQTokenField(ID_ELEMENTO_FIELD, id)).toString();

		HashMap columnsToUpdate = new HashMap();
		columnsToUpdate.put(EDITCLFDOCS_FIELD, editClfDocs);

		updateFields(qual, columnsToUpdate, TABLE_NAME_ELEMENTO);

	}

	public void getElementosCFXTipoYFondo(int tipo, String idFondo,
			VOList elementosCuadro) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(TIPO_ELEMENTO_FIELD, tipo));
		if (idFondo != null) {
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(IDFONDO_FIELD, idFondo));
		}
		getElementos(qual.toString(), elementosCuadro);
	}

	public List getElementosCFXIdFondo(final String idFondo) {
		StringBuffer qual = new StringBuffer().append(" WHERE ").append(
				DBUtils.generateEQTokenField(IDFONDO_FIELD, idFondo));

		return getElementos(qual.toString(), (String) null);
	}

	public void getElementos(final String qual, final VOList list) {

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				list.setNumItems(DbSelectFns.selectCount(conn,
						TABLE_NAME_ELEMENTO, qual));
				DbSelectFns.select(conn, TABLE_NAME_ELEMENTO,
						DbUtil.getColumnNames(list.getSelectedColumns()), qual,
						false, list);
			}
		};
		command.execute();

	}

	public List getElementos(final String qual, final String hint) {
		return getVOS(qual, TABLE_NAME_ELEMENTO, hint, COLS_DEFS_ELEMENTO,
				ElementoCuadroClasificacion.class);
	}

	public List getElementosCFXTipo(String idParent, int tipoElemento) {
		StringBuffer qual = new StringBuffer(" where ")
				.append(DBUtils.generateEQTokenField(IDPADRE_FIELD, idParent))
				.append(" and ")
				.append(DBUtils.generateEQTokenField(TIPO_ELEMENTO_FIELD,
						tipoElemento));
		return getElementos(qual.toString(), HINT_IDPADRE_TITULO);
	}

	public List getElementosCFXNivelYFondoYCodigoYTitulo(String[] niveles,
			String idFondo, String codigo, String titulo,
			boolean conCriterioEstado) {

		if(ArrayUtils.isEmptyOrBlank(niveles)){
			niveles = new String[]{""};
		}

		StringBuffer qual = new StringBuffer(" where (").append(DBUtils
				.generateEQTokenField(NIVEL_FIELD, niveles[0]));
		for (int i = 1; i < niveles.length; i++)
			qual.append(" OR ").append(
					DBUtils.generateEQTokenField(NIVEL_FIELD, niveles[i]));
		qual.append(")");
		if (conCriterioEstado)
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(ESTADO_ELEMENTO_FIELD,
							Constants.ELEMENTOCF_VIGENTE));
		if (!StringUtils.isEmpty(idFondo))
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(IDFONDO_FIELD, idFondo));
		if (!StringUtils.isEmpty(codigo))
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CODIGO_FIELD, codigo));
		if (!StringUtils.isEmpty(titulo))
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(TITULO_FIELD, titulo));
		return getElementos(qual.toString(), (String) null);
	}

	public List getElementosCFXNivel(String[] niveles, String idFondo) {
		StringBuffer qual = new StringBuffer(" where (").append(DBUtils
				.generateEQTokenField(NIVEL_FIELD, niveles[0]));
		for (int i = 1; i < niveles.length; i++)
			qual.append(" OR ").append(
					DBUtils.generateEQTokenField(NIVEL_FIELD, niveles[i]));
		qual.append(")");
		if (idFondo != null)
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(IDFONDO_FIELD, idFondo));
		return getElementos(qual.toString(), (String) null);
	}

	public int getCountElementosCFByNivel(String idNivel) {
		StringBuffer qual = new StringBuffer(" where ").append(DBUtils
				.generateEQTokenField(NIVEL_FIELD, idNivel));
		return getVOCount(qual.toString(), TABLE_NAME_ELEMENTO);
	}

	public void updateFieldsECF(String idElementoCF, final Map columnsToUpdate) {
		StringBuffer qual = new StringBuffer().append(" where ").append(
				DBUtils.generateEQTokenField(ID_ELEMENTO_FIELD, idElementoCF));

		updateFields(qual.toString(), columnsToUpdate, TABLE_NAME_ELEMENTO);

	}

	/**
	 * Actualizar un elemento en el cuadro de clasificación durante la
	 * validación de una relación de entrega entre archivos
	 **/
	public void updateElementoCFVEA(ElementoCuadroClasificacionVO elemento) {

		/*
		 * Actualizar: id de archivo, el id de padre, el id de fondo, el código
		 * de referencia de fondo , el código de referencia final del padre y el
		 * código de referencia de las unidades documentales en la tabla
		 * ASGFELEMENTOCF
		 */

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(ARCHIVO_FIELD, elemento.getIdArchivo());
		colsToUpdate.put(IDPADRE_FIELD, elemento.getIdPadre());
		colsToUpdate.put(IDFONDO_FIELD, elemento.getIdFondo());
		colsToUpdate.put(CODIGO_REFERENCIA_FIELD, elemento.getCodReferencia());
		colsToUpdate.put(CODREFFONDO_FIELD, elemento.getCodRefFondo());
		colsToUpdate
				.put(FINALCODREFPADRE_FIELD, elemento.getFinalCodRefPadre());

		updateFieldsECF(elemento.getId(), colsToUpdate);
	}

	public void updatePadreCodRefUnidadesDocumentales(final String idPadre,
			final String idFondo, final String codRefFondo,
			final String codRefPadre,
			final TablaTemporalFondosVO tablaTemporalFondosVO) throws Exception{
			ConfiguracionFondos config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos();
		String separador = config.getDelimitadorCodigoReferencia();

		StringBuffer qual = new StringBuffer();
		qual.append(DBUtils.WHERE)
				.append(DBUtils
						.generateInTokenFieldSubQuery(
								ID_ELEMENTO_FIELD,
								TablaTemporalDBEntityImpl
										.getSelectIdElementocfFromTemporal(tablaTemporalFondosVO)));

		String[] colNames = new String[] { IDPADRE_FIELD.getName(),
				IDFONDO_FIELD.getName(), CODREFFONDO_FIELD.getName(),
				CODIGO_REFERENCIA_FIELD.getName(),
				FINALCODREFPADRE_FIELD.getName() };

		String concatSymbolDb = DBUtils.getNativeConcatSyntax(getConnection());
		String[] colValues = new String[] {
				DBUtils.COMILLA_SIMPLE + idPadre + DBUtils.COMILLA_SIMPLE,
				DBUtils.COMILLA_SIMPLE + idFondo + DBUtils.COMILLA_SIMPLE,
				DBUtils.COMILLA_SIMPLE + codRefFondo + DBUtils.COMILLA_SIMPLE,
				DBUtils.COMILLA_SIMPLE + codRefPadre + separador + DBUtils.COMILLA_SIMPLE + concatSymbolDb + " " + CODIGO_COLUMN_NAME, DBUtils.NULL };

		DbUpdateFns.updateCustom(getConnection(), TABLE_NAME_ELEMENTO,
				colNames, colValues, qual.toString());

   }

	public void updateCodReferenciaCF(String id, String newCodRef) {

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CODIGO_REFERENCIA_FIELD, newCodRef);

		updateFieldsECF(id, colsToUpdate);
	}

	/**
	 * Obtiene la lista de elementos que cumplan los requisitos de la búsqueda.
	 *
	 * @param serviceClient
	 *            Cliente del servicio.
	 * @param busqueda
	 *            Información del formulario de búsqueda.
	 * @return Lista de elementos del cuadro de clasificación.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	/*
	 * public List getElementosCuadroClasificacion(ServiceClient serviceClient,
	 * BusquedaGeneralVO busqueda) throws TooManyResultsException
	 */

	public List getElementosCuadroClasificacion(ServiceClient serviceClient,
			BusquedaElementosVO busqueda) throws TooManyResultsException {

		final ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();
		final String ID_CAMPO_FECHA = csa.getConfiguracionDescripcion()
				.getFechaExtremaInicial();

		// Joins con las demás tablas
		TableDef tablaElemento = new TableDef(TABLE_NAME_ELEMENTO);
		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(tablaElemento, ID_ELEMENTO_FIELD),
				new DbColumnDef(new TableDef(FechaDBEntityImpl.TABLE_NAME),
						FechaDBEntityImpl.CAMPO_ID_ELEMENTO)), };

		StringBuffer sqlFrom = new StringBuffer();
		sqlFrom.append(DBUtils.generateLeftOuterJoinCondition(tablaElemento,
				joins));
		sqlFrom.append(" AND ").append(
				DBUtils.generateEQTokenField(FechaDBEntityImpl.CAMPO_ID_CAMPO,
						ID_CAMPO_FECHA));
		sqlFrom.append(",").append(NivelCFDBEntityImpl.NIVELCF_TABLE_NAME);
		DbColumnDef[] COLS_DEF_QUERY = new DbColumnDef[] {
				new DbColumnDef(new TableDef(
						NivelCFDBEntityImpl.NIVELCF_TABLE_NAME),
						NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD),
				new DbColumnDef(
						new TableDef(
								ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO),
						ID_ELEMENTO_FIELD),
				new DbColumnDef(
						new TableDef(
								ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO),
						CODIGO_REFERENCIA_FIELD),
				new DbColumnDef(
						new TableDef(
								ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO),
						TITULO_FIELD),
				new DbColumnDef(
						new TableDef(
								ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO),
						NIVEL_FIELD),
				new DbColumnDef(new TableDef(FechaDBEntityImpl.TABLE_NAME),
						new DbColumnDef("valorFInicial",
								FechaDBEntityImpl.CAMPO_VALOR)),
				new DbColumnDef(new TableDef(FechaDBEntityImpl.TABLE_NAME),
						new DbColumnDef("calificadorFInicial",
								FechaDBEntityImpl.CAMPO_CALIFICADOR)),
				new DbColumnDef(new TableDef(FechaDBEntityImpl.TABLE_NAME),
						new DbColumnDef("formatoFInicial",
								FechaDBEntityImpl.CAMPO_FORMATO))
		/*
		 * new DbColumnDef(new TableDef(FechaDBEntityImpl.TABLE_NAME),
		 * FechaDBEntityImpl.CAMPO_VALOR), new DbColumnDef(new
		 * TableDef(FechaDBEntityImpl.TABLE_NAME),
		 * FechaDBEntityImpl.CAMPO_CALIFICADOR), new DbColumnDef(new
		 * TableDef(FechaDBEntityImpl.TABLE_NAME),
		 * FechaDBEntityImpl.CAMPO_FORMATO )
		 */
		};

		if (busqueda.getPageInfo() != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("codReferencia", CODIGO_REFERENCIA_FIELD);
			criteriosOrdenacion.put("titulo", TITULO_FIELD);
			criteriosOrdenacion.put("nombre",
					NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD);
			// criteriosOrdenacion.put("nivel",
			// NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD);

			return getVOS(getBusquedaWhereClause(serviceClient, busqueda),
					sqlFrom.toString(), COLS_DEF_QUERY, busqueda.getPageInfo()
							.getOrderBy(criteriosOrdenacion),
					ElementoCFVO.class, busqueda.getPageInfo());

		} else {
			StringBuffer sbQual = new StringBuffer(getBusquedaWhereClause(
					serviceClient, busqueda)).append(" ORDER BY ").append(
					CODIGO_REFERENCIA_FIELD.getQualifiedName());

			return getVOS(sbQual.toString(), sqlFrom.toString(),
					COLS_DEF_QUERY, ElementoCFVO.class);
		}
	}

	/**
	 * Obtiene la clásula WHERE para la búsqueda de elementos del cuadro de
	 * clasificación.
	 *
	 * @param serviceClient
	 *            Cliente del servicio.
	 * @param busquedaGeneralVO
	 *            Información del formulario de búsqueda.
	 * @return Cláususla WHERE.
	 */
	/*
	 * private String getBusquedaWhereClause(GenericUserInfo serviceClient,
	 * BusquedaGeneralVO busquedaGeneralVO)
	 */
	private String getBusquedaWhereClause(GenericUserInfo serviceClient,
			BusquedaElementosVO busquedaElementosVO) {
		final StringBuffer clausulaWhere = new StringBuffer();
		// final ConfiguracionSistemaArchivo csa =
		// ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo();
		// final String ID_CAMPO_FECHA =
		// csa.getConfiguracionDescripcion().getFechaExtremaInicial();

		// Estado VIGENTE (ESTADO=2)
		clausulaWhere.append("WHERE ").append(
				DBUtils.generateEQTokenField(ESTADO_ELEMENTO_FIELD,
						IElementoCuadroClasificacion.VIGENTE));

		// Comprobación de permisos sobre el elemento
		DBUtils.addPermissionsCheckingClauses(serviceClient, clausulaWhere,
				NIVEL_ACCESO_FIELD, ARCHIVO_FIELD, LISTA_ACCESO_FIELD);
		clausulaWhere.append(" AND ").append(
				DBUtils.generateJoinCondition(NIVEL_FIELD,
						NivelCFDBEntityImpl.ID_NIVEL_FIELD));

		// Fondo
		if (StringUtils.isNotBlank(busquedaElementosVO.getFondo())) {
			clausulaWhere.append(" AND ").append(
					DBUtils.generateEQTokenField(IDFONDO_FIELD,
							busquedaElementosVO.getFondo()));
		}

		// Niveles de descripción
		if ((busquedaElementosVO.getNiveles() != null)
				&& (busquedaElementosVO.getNiveles().length > 0)) {
			clausulaWhere.append(" AND ").append(
					DBUtils.generateInTokenField(NIVEL_FIELD,
							busquedaElementosVO.getNiveles()));
		}

		// Código de referencia
		if (StringUtils.isNotBlank(busquedaElementosVO.getCodigoReferencia())) {
			clausulaWhere.append(" AND ").append(
					DBUtils.generateLikeTokenField(CODIGO_REFERENCIA_FIELD,
							busquedaElementosVO.getCodigoReferencia()));
		}

		// Título
		if (StringUtils.isNotBlank(busquedaElementosVO.getTitulo())) {
			clausulaWhere.append(" AND ").append(
					DBUtils.generateContainsTokenField(getConnection(),
							TITULO_FIELD, IDXTITULO_FIELD,
							busquedaElementosVO.getTitulo()));
			// busquedaElementosVO.getTitulo().toUpperCase()));
		}

		// Texto
		if (StringUtils.isNotBlank(busquedaElementosVO.getTexto())) {
			final String textoUpperCase = busquedaElementosVO.getTexto()
					.toUpperCase();

			clausulaWhere
					.append(" AND (")
					.append(ID_ELEMENTO_FIELD.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO
							.getQualifiedName())
					.append(" FROM " + TextoCortoDBEntityImpl.TABLE_NAME)
					.append(" WHERE ")
					.append(DBUtils.generateContainsTokenField(getConnection(),
							TextoCortoDBEntityImpl.CAMPO_VALOR,
							TextoCortoDBEntityImpl.CAMPO_IDXVALOR,
							busquedaElementosVO.getTexto()))
					.append(") OR ")
					.append(ID_ELEMENTO_FIELD.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(TextoLargoDBEntityImpl.CAMPO_ID_ELEMENTO
							.getQualifiedName())
					.append(" FROM " + TextoLargoDBEntityImpl.TABLE_NAME)
					.append(" WHERE ")
					.append(DBUtils.generateContainsTokenField(getConnection(),
							TextoLargoDBEntityImpl.CAMPO_VALOR,
							TextoLargoDBEntityImpl.CAMPO_IDXVALOR,
							busquedaElementosVO.getTexto()))
					.append(")")
					.append(" OR ")
					.append(DBUtils.generateContainsTokenField(getConnection(),
							TITULO_FIELD, IDXTITULO_FIELD, textoUpperCase))
					.append(" OR ")
					.append(DBUtils.generateLikeTokenField(
							CODIGO_REFERENCIA_FIELD, textoUpperCase))
					.append(")");
		}

		// Fechas
		if ((busquedaElementosVO.getFechaIni() != null)
				|| (busquedaElementosVO.getFechaFin() != null)) {
			clausulaWhere
					.append(" AND ")
					.append(ID_ELEMENTO_FIELD.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(FechaDBEntityImpl.CAMPO_ID_ELEMENTO
							.getQualifiedName())
					.append(" FROM " + FechaDBEntityImpl.TABLE_NAME + " WHERE ");

			if (busquedaElementosVO.getFechaIni() != null) {
				// clausulaWhere
				// .append("((")
				// .append(FechaDBEntityImpl.CAMPO_FECHA_FINAL.getQualifiedName())
				// .append(" IS NULL AND ")
				// .append(DBUtils.generateNEQTokenField(FechaDBEntityImpl.CAMPO_CALIFICADOR,
				// DateQualifierHelper.CALIFICADOR_SIN_FECHA))
				// .append(") OR ")
				// .append(FechaDBEntityImpl.CAMPO_FECHA_FINAL.getQualifiedName())
				// .append(">=TO_DATE('")
				// .append(CustomDateFormat.SDF_YYYYMMDD.format(busquedaGeneralVO.getFechaIni()))
				// .append("','")
				// .append(CustomDateFormat.SDF_YYYYMMDD.toPattern())
				// .append("'))");
				clausulaWhere
						.append("((")
						.append(FechaDBEntityImpl.CAMPO_FECHA_FINAL
								.getQualifiedName())
						.append(" IS NULL AND ")
						.append(DBUtils.generateNEQTokenField(
								FechaDBEntityImpl.CAMPO_CALIFICADOR,
								DateQualifierHelper.CALIFICADOR_SIN_FECHA))
						.append(") OR ")
						.append(FechaDBEntityImpl.CAMPO_FECHA_FINAL
								.getQualifiedName())
						.append(">=")
						.append(DBUtils.getNativeToDateSyntax(getConnection(),
								busquedaElementosVO.getFechaIni(),
								CustomDateFormat.SDF_YYYYMMDD)).append(")");

			}

			if (busquedaElementosVO.getFechaFin() != null) {
				if (busquedaElementosVO.getFechaIni() != null)
					clausulaWhere.append(" AND ");
				//
				// clausulaWhere
				// .append("((")
				// .append(FechaDBEntityImpl.CAMPO_FECHA_INICIAL.getQualifiedName())
				// .append(" IS NULL AND ")
				// .append(DBUtils.generateNEQTokenField(FechaDBEntityImpl.CAMPO_CALIFICADOR,
				// DateQualifierHelper.CALIFICADOR_SIN_FECHA))
				// .append(") OR ")
				// .append(FechaDBEntityImpl.CAMPO_FECHA_INICIAL.getQualifiedName())
				// .append("<=TO_DATE('")
				// .append(CustomDateFormat.SDF_YYYYMMDD.format(busquedaGeneralVO.getFechaFin()))
				// .append("','")
				// .append(CustomDateFormat.SDF_YYYYMMDD.toPattern())
				// .append("'))");
				clausulaWhere
						.append("((")
						.append(FechaDBEntityImpl.CAMPO_FECHA_INICIAL
								.getQualifiedName())
						.append(" IS NULL AND ")
						.append(DBUtils.generateNEQTokenField(
								FechaDBEntityImpl.CAMPO_CALIFICADOR,
								DateQualifierHelper.CALIFICADOR_SIN_FECHA))
						.append(") OR ")
						.append(FechaDBEntityImpl.CAMPO_FECHA_INICIAL
								.getQualifiedName())
						.append("<=")
						.append(DBUtils.getNativeToDateSyntax(getConnection(),
								busquedaElementosVO.getFechaFin(),
								CustomDateFormat.SDF_YYYYMMDD)).append(")");

			}

			clausulaWhere.append(")");
		}

		// Calificador de fechas
		if (StringUtils.isNotEmpty(busquedaElementosVO.getCalificador())) {
			clausulaWhere
					.append(" AND ")
					.append(ID_ELEMENTO_FIELD.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(FechaDBEntityImpl.CAMPO_ID_ELEMENTO
							.getQualifiedName())
					.append(" FROM " + FechaDBEntityImpl.TABLE_NAME + " WHERE ")
					.append(DBUtils.generateEQTokenField(
							FechaDBEntityImpl.CAMPO_CALIFICADOR,
							busquedaElementosVO.getCalificador())).append(")");
		}

		// Números
		if (StringUtils.isNotEmpty(busquedaElementosVO.getNumero())
				|| StringUtils.isNotEmpty(busquedaElementosVO.getTipoMedida())
				|| StringUtils
						.isNotEmpty(busquedaElementosVO.getUnidadMedida())) {
			boolean iniciado = false;

			clausulaWhere
					.append(" AND ")
					.append(ID_ELEMENTO_FIELD.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(NumeroDBEntityImpl.CAMPO_ID_ELEMENTO
							.getQualifiedName())
					.append(" FROM " + NumeroDBEntityImpl.TABLE_NAME
							+ " WHERE ");

			// Valor del número
			if (StringUtils.isNotBlank(busquedaElementosVO.getNumero())) {
				clausulaWhere
						.append(NumeroDBEntityImpl.CAMPO_VALOR
								.getQualifiedName())
						.append(busquedaElementosVO.getNumeroComp())
						.append(busquedaElementosVO.getNumero());

				iniciado = true;
			}

			// Tipo de medida
			if (StringUtils.isNotBlank(busquedaElementosVO.getTipoMedida())) {
				if (iniciado)
					clausulaWhere.append(" AND ");

				clausulaWhere.append(DBUtils.generateEQTokenField(
						NumeroDBEntityImpl.CAMPO_TIPOMEDIDA,
						busquedaElementosVO.getTipoMedida()));

				iniciado = true;
			}

			// Unidad de medida
			if (StringUtils.isNotBlank(busquedaElementosVO.getUnidadMedida())) {
				if (iniciado)
					clausulaWhere.append(" AND ");

				clausulaWhere
						.append(" UPPER(")
						.append(NumeroDBEntityImpl.CAMPO_UNIDADMEDIDA
								.getQualifiedName())
						.append(") LIKE '%")
						.append(busquedaElementosVO.getUnidadMedida()
								.toUpperCase()).append("%'");
			}

			clausulaWhere.append(")");
		}

		// Descriptores
		if ((busquedaElementosVO.getDescriptores() != null)
				&& (busquedaElementosVO.getDescriptores().length > 0)) {
			clausulaWhere
					.append(" AND ")
					.append(ID_ELEMENTO_FIELD.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO
							.getQualifiedName())
					.append(" FROM " + ReferenciaDBEntityImpl.TABLE_NAME
							+ " WHERE ")
					.append(DBUtils.generateEQTokenField(
							ReferenciaDBEntityImpl.CAMPO_TIPOOBJREF,
							CampoReferenciaVO.REFERENCIA_A_ELEMENTO_DESCRIPTOR))
					.append(" AND ")
					.append(DBUtils.generateInTokenField(
							ReferenciaDBEntityImpl.CAMPO_IDOBJREF,
							busquedaElementosVO.getDescriptores())).append(")");
		}

		return clausulaWhere.toString();
	}

	protected String generarCodigoReferenciaFondo(FondoVO fondoVO,
			ElementoCuadroClasificacionVO padre) {
		ConfiguracionFondos config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos();
		String delimiter = config.getDelimitadorCodigoReferencia();

		StringBuffer codigoFondo = new StringBuffer()
				.append(fondoVO.getCodPais()).append(delimiter)
				.append(fondoVO.getCodComunidad()).append(delimiter)
				.append(fondoVO.getCodArchivo()).append(delimiter);

		String codigoReferencia = concatDelimiter(padre.getCodReferencia(),
				delimiter, fondoVO.getCodigo());

		codigoFondo.append(codigoReferencia);

		return codigoFondo.toString();
	}

	public List getIdsTodosHijos(List ids) {
		List hijos = new ArrayList();
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateInTokenField(IDPADRE_FIELD, ids));

		List hijosAux = getFieldValues(qual.toString(), TABLE_NAME_ELEMENTO,
				ID_ELEMENTO_FIELD);
		if (hijosAux != null && hijosAux.size() > 0) {
			hijos.addAll(hijosAux);
			hijos.addAll(getIdsTodosHijos(hijosAux));
		}
		return hijos;
	}

	// public UpdateCodRefsReturns updateCodRefs2(List ids,
	// NivelCFVO nivelElemento, NivelCFVO nivelPadre,
	// ElementoCuadroClasificacionVO padre,List idsElementosAMover){
	//
	// }

	public UpdateCodRefsReturns updateCodRefElementoYHijos(String idElemento,
			String separador, boolean updateOtherTables) {
		IElementoCuadroClasificacion elementoActualizado = null;

		try {
			String sql = "{call " + UPDATE_COD_REF_PROCEDURE + "(?,?,?)}";
			CallableStatement cstmt = getConnection().getJdbcConnection()
					.prepareCall(sql);
			cstmt.setString(1, idElemento);
			cstmt.setString(2, separador);
			if (updateOtherTables) {
				cstmt.setString(3, Constants.TRUE_STRING);
			} else {
				cstmt.setString(3, Constants.FALSE_STRING);
			}
			cstmt.execute();

			elementoActualizado = getElementoCuadroClasificacion(idElemento);
		} catch (SQLException e) {
			logger.error(e);
		}

		if (elementoActualizado != null) {
			return new UpdateCodRefsReturns(
					elementoActualizado.getFinalCodRefPadre(),
					elementoActualizado.getCodReferencia());
		} else {
			return null;
		}
	}

	public String composeFinalCodRefPadre(INivelCFVO tipoNivelPadre,
			String finalCodRefDelPadre, String codigo) {
		ConfiguracionFondos config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos();

		String delimiter = config.getDelimitadorCodigoReferencia();

		if (tipoNivelPadre.getTipo() == TipoNivelCF.FONDO.getIdentificador())
			return "";

		String finalCodRefPadre = concatDelimiter(finalCodRefDelPadre,
				delimiter, codigo);

		return finalCodRefPadre;
	}

	public String composeCodigoReferencia(INivelCFVO nivelPadre,
			String finalCodRefPadreDelPadre, String codigoDelPadre,
			String codRefFondo, String codigoElemento) {

		ConfiguracionFondos config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos();

		String delimiter = config.getDelimitadorCodigoReferencia();

		String finalCodRefPadre = composeFinalCodRefPadre(nivelPadre,
				finalCodRefPadreDelPadre, codigoDelPadre);

		String codReferencia = concatDelimiter(codRefFondo, delimiter,
				finalCodRefPadre);

		codReferencia = concatDelimiter(codReferencia, delimiter,
				codigoElemento);

		/*
		 * String codReferencia = new StringBuffer(appendField(codRefFondo))
		 * .append(appendDelimiter(codRefFondo,delimiter))
		 * .append(appendField(finalCodRefPadre))
		 * .append(appendDelimiter(finalCodRefPadre, delimiter))
		 * .append(appendField(codigoElemento)).toString();
		 */

		return codReferencia;
	}

	public String composeCodigoReferencia(String codRefFondo,
			String finalCodRefPadre, String codigo) {

		ConfiguracionFondos config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos();

		String delimiter = config.getDelimitadorCodigoReferencia();

		String codReferencia = concatDelimiter(codRefFondo, delimiter,
				finalCodRefPadre);

		codReferencia = concatDelimiter(codReferencia, delimiter, codigo);

		/*
		 * String codReferencia = new StringBuffer(appendField(codRefFondo))
		 * .append(appendDelimiter(codRefFondo,delimiter))
		 * .append(appendField(finalCodRefPadre))
		 * .append(appendDelimiter(finalCodRefPadre, delimiter))
		 * .append(appendField(codigo)).toString();
		 */
		return codReferencia;
	}

	public List getElementosXNivelYPadre(String idNivelCFVO, String idPadre) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(NIVEL_FIELD, idNivelCFVO))
				.append(" AND")
				.append(DBUtils.generateEQTokenField(IDPADRE_FIELD, idPadre))
				.toString();

		return getElementosCF(qual, null);

	}

	/**
	 * Recupera la lista de antecesares jerarquicos de un elemento en el cuadro
	 * de clasificacion de fondos
	 *
	 * @param idElemento
	 *            Identificador de elemento del cuadro de clasificacion cuya
	 *            jerarquia se va a obtener
	 * @return Lista de elementos del cuadro de clasificacion
	 *         {@link ElementoCuadroClasificacionVO} que forman parte de la
	 *         jerarquia del elemento. El primer elemento de la lista sera uno
	 *         de los elementos raiz del arbol de clasificacion de fondos y el
	 *         ultimo sera el padre directo del que cuelga el elemento del
	 *         cuadro identificado por el parametro idElemento.
	 */
	public abstract List getAncestors(String idElemento);

	// public boolean hasHermanosConMismoCodigo(String idElemento, String
	// idNivelElemento, String nuevoCodigo, String idNuevoPadre) {
	// List hermanos = getElementosXNivelYPadre(idNivelElemento, idNuevoPadre);
	// if (!CollectionUtils.isEmptyCollection(hermanos)){
	// for (Iterator itHermanos = hermanos.iterator(); itHermanos.hasNext();) {
	// ElementoCuadroClasificacionVO hermano = (ElementoCuadroClasificacionVO)
	// itHermanos.next();
	// if (StringUtils.isNotEmpty(hermano.getCodigo()) &&
	// hermano.getCodigo().equalsIgnoreCase(nuevoCodigo)
	// &&!hermano.getId().equalsIgnoreCase(idElemento)){
	// return true;
	// }
	// }
	// }
	// return false;
	// }

	public boolean hasHermanosConMismoCodigo(
			TablaTemporalFondosVO tablaTemporalFondosVO, String idNuevoPadre) {

		TableDef tablaElemento = new TableDef(TABLE_NAME_ELEMENTO);
		StringBuffer from = new StringBuffer(tablaElemento.getDeclaration());

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDPADRE_FIELD,
						idNuevoPadre))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateInTokenFieldSubQuery(
								CODIGO_FIELD,
								TablaTemporalDBEntityImpl
										.getSelectCodigoFromTemporal(tablaTemporalFondosVO)));

		try {
			if (DbSelectFns.selectCount(getConnection(), from.toString(),
					qual.toString()) > 0)
				return true;
		} catch (Exception e) {
			logger.error(e);
		}

		return false;
	}

	public boolean hasHermanosConMismoCodigo(String idElemento,
			String idNivelElemento, String nuevoCodigo, String idNuevoPadre) {
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateEQTokenField(IDPADRE_FIELD,
						idNuevoPadre))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(NIVEL_FIELD,
						idNivelElemento))
				.append(" AND ")
				.append(DBUtils.generateIsNotNullCondition(getConnection(),
						CODIGO_FIELD))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CODIGO_FIELD, nuevoCodigo))
				.append(" AND ")
				.append(DBUtils.generateNEQTokenField(ID_ELEMENTO_FIELD,
						idElemento));

		try {
			if (DbSelectFns.selectCount(getConnection(), TABLE_NAME_ELEMENTO,
					qual.toString()) > 0)
				return true;
		} catch (Exception e) {
			logger.error(e);
		}
		return false;
	}

	public List getElementosCFXLCAcceso(String idListaControlAcceso) {
		String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateEQTokenField(LISTA_ACCESO_FIELD,
						idListaControlAcceso)).toString();

		return getElementosCF(qual, null);

	}

	/**
	 * Cuenta el numero de elementos que cuelgan de un determino elemento del
	 * cuadro de clasificación
	 *
	 * @param idElementoCF
	 *            Identficador de elemento del cuadro de clasificacion
	 * @return Numero de descendientes que dependen directamente del elemento
	 *         suministrado
	 */
	public int countNumChilds(String idElementoCF) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(IDPADRE_FIELD, idElementoCF));
		return getVOCount(qual.toString(), TABLE_NAME_ELEMENTO);
	}

	/**
	 * Busca los elementos del cuadro que cumplan unos criterios concretos.
	 *
	 * @param vo
	 *            Criterios de la búsqueda.
	 * @return Lista de elementos del cuadro.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getElementos(final BusquedaElementosVO vo)
			throws TooManyResultsException {
		final DbColumnDef CUSTOM_ID_ELEMENTO_FIELD = new DbColumnDef(null, "A",
				ID_ELEMENTO_COLUMN_NAME_S, DbDataType.SHORT_TEXT, 32, false);
		final DbColumnDef CUSTOM_B_ID_ELEMENTO_FIELD = new DbColumnDef(null,
				"B", ID_ELEMENTO_COLUMN_NAME_S, DbDataType.SHORT_TEXT, 32,
				false);
		final DbColumnDef CUSTOM_NIVEL_FIELD = new DbColumnDef(null, "A",
				NIVEL_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
		final DbColumnDef CUSTOM_NOMBRE_NIVEL_FIELD = new DbColumnDef(
				"nombreNivel", NivelCFDBEntityImpl.NIVELCF_TABLE_NAME,
				NivelCFDBEntityImpl.NOMBRE_NIVEL_COLUMN_NAME,
				DbDataType.SHORT_TEXT, 64, false);
		final DbColumnDef CUSTOM_CODIGO_FIELD = new DbColumnDef(null, "A",
				CODIGO_COLUMN_NAME, DbDataType.SHORT_TEXT, 128, false);
		final DbColumnDef CUSTOM_TITULO_FIELD = new DbColumnDef(null, "A",
				TITULO_COLUMN_NAME, DbDataType.SHORT_TEXT, 128, false);
		final DbColumnDef CUSTOM_IDXTITULO_FIELD = new DbColumnDef(null, "A",
				IDXTITULO_COLUMN_NAME, DbDataType.SHORT_TEXT, 128, false);
		final DbColumnDef CUSTOM_B_TITULO_FIELD = new DbColumnDef(
				"nombreFondo", "B", TITULO_COLUMN_NAME, DbDataType.SHORT_TEXT,
				128, false);
		final DbColumnDef CUSTOM_IDFONDO_FIELD = new DbColumnDef(null, "A",
				IDFONDO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
		// final DbColumnDef CUSTOM_B_IDFONDO_FIELD = new DbColumnDef(
		// null, "B", IDFONDO_COLUMN_NAME,DbDataType.SHORT_TEXT, 32, false);
		final DbColumnDef CUSTOM_ESTADO_ELEMENTO_FIELD = new DbColumnDef(null,
				"A", ESTADO_ELEMENTO_COLUMN_NAME, DbDataType.LONG_INTEGER,
				false);
		final DbColumnDef CUSTOM_ARCHIVO_FIELD = new DbColumnDef(null, "A",
				ID_ARCHIVO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
		final DbColumnDef CUSTOM_NIVEL_ACCESO_FIELD = new DbColumnDef(null,
				"A", NIVEL_ACCESO_COLUMN_NAME, DbDataType.SHORT_INTEGER, false);
		final DbColumnDef CUSTOM_LISTA_ACCESO_FIELD = new DbColumnDef(null,
				"A", LISTA_ACCESO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
		final DbColumnDef CUSTOM_TIPO_FIELD = new DbColumnDef(null, "A",
				TIPO_ELEMENTO_COLUMN_NAME, DbDataType.LONG_INTEGER, false);
		final DbColumnDef CUSTOM_ID_FICHA = new DbColumnDef(null, "A",
				IDFICHADESCR_FIELD);
		final DbColumnDef CUSTOM_ID_ELIMINACION = new DbColumnDef(null, "A",
				ID_ELIMINACION_FIELD);

		// final DbColumnDef CUSTOM_CODIGO_REFERENCIA = new DbColumnDef(
		// null, "A", CODIGO_REFERENCIA_FIELD);

		final DbColumnDef[] allColsDefs = new DbColumnDef[] {
				CUSTOM_ID_ELEMENTO_FIELD, CUSTOM_CODIGO_FIELD,
				CUSTOM_TITULO_FIELD, CUSTOM_IDFONDO_FIELD,
				CUSTOM_B_TITULO_FIELD, CUSTOM_NIVEL_FIELD,
				CUSTOM_NOMBRE_NIVEL_FIELD, CUSTOM_ESTADO_ELEMENTO_FIELD,
				CUSTOM_TIPO_FIELD, CUSTOM_ID_ELIMINACION };

		final StringBuffer allTableNames = new StringBuffer()
				.append(TABLE_NAME_ELEMENTO).append(" A,")
				.append(TABLE_NAME_ELEMENTO).append(" B,")
				.append(NivelCFDBEntityImpl.NIVELCF_TABLE_NAME);

		final StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateJoinCondition(CUSTOM_IDFONDO_FIELD,
						CUSTOM_B_ID_ELEMENTO_FIELD))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(CUSTOM_NIVEL_FIELD,
						NivelCFDBEntityImpl.ID_NIVEL_FIELD));

		// Comprobaciones de permisos
		DBUtils.addPermissionsCheckingClauses(vo.getServiceClient(), qual,
				CUSTOM_NIVEL_ACCESO_FIELD, CUSTOM_ARCHIVO_FIELD,
				CUSTOM_LISTA_ACCESO_FIELD);

		// Fondo
		if (StringUtils.isNotBlank(vo.getFondo()))
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CUSTOM_IDFONDO_FIELD,
							vo.getFondo()));

		// Condiciones ámbito
		if (!ArrayUtils.isEmpty(vo.getIdObjetoAmbito())) {
			StringBuffer condicionesAmbito = new StringBuffer();

			for (int i = 0; i < vo.getIdObjetoAmbito().length; i++) {
				if (StringUtils.isNotBlank(vo.getIdObjetoAmbito()[i])) {
					if (condicionesAmbito.length() > 0)
						condicionesAmbito.append(" OR ");

					// condicionesAmbito.append(DBUtils.getNativeStrPosSyntax(getConnection(),
					// CUSTOM_CODIGO_REFERENCIA, vo.getCodRefObjetoAmbito()[i]))
					// .append("=1");

				}
			}

			if (condicionesAmbito.length() > 0) {
				qual.append(" AND (").append(condicionesAmbito).append(")");
			}
		}

		// Ficha
		if (StringUtils.isNotBlank(vo.getIdFicha())) {
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CUSTOM_ID_FICHA,
							vo.getIdFicha()));
		}

		// Código
		if (StringUtils.isNotBlank(vo.getCodigo()))
			qual.append(" AND ").append(
					DBUtils.generateLikeTokenField(CUSTOM_CODIGO_FIELD,
							vo.getCodigo()));

		// Título
		if (StringUtils.isNotBlank(vo.getTitulo()))
			qual.append(" AND ").append(
					DBUtils.generateContainsTokenField(getConnection(),
							CUSTOM_TITULO_FIELD, CUSTOM_IDXTITULO_FIELD,
							vo.getTitulo()));

		// Niveles
		if (!ArrayUtils.isEmpty(vo.getNiveles()))
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CUSTOM_NIVEL_FIELD,
							vo.getNiveles()));

		// Estados
		if (!ArrayUtils.isEmpty(vo.getEstados()))
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CUSTOM_ESTADO_ELEMENTO_FIELD,
							vo.getEstados()));

		// Condiciones avanzadas
		if (!ArrayUtils.isEmpty(vo.getCampo())) {
			StringBuffer sqlAdvanced = new StringBuffer();

			String booleano, operador, valor1, valor2;
			String idCampo;
			int tipoCampo;
			Integer tipoReferencia = null;
			// String [] relacionesTesauro;

			for (int i = 0; i < vo.getCampo().length; i++) {
				tipoReferencia = null;
				booleano = vo.getBooleano()[i];
				idCampo = vo.getCampo()[i];
				tipoCampo = vo.getTipoCampo()[i];
				operador = vo.getOperador()[i];
				if (vo.getTiposReferencia() != null
						&& StringUtils.isNotEmpty(vo.getTiposReferencia()[i])
						&& Integer.valueOf(vo.getTiposReferencia()[i]) != null)
					tipoReferencia = new Integer(Integer.parseInt(vo
							.getTiposReferencia()[i]));

				if (StringUtils.isNotBlank(booleano) && i > 0
						&& StringUtils.isNotEmpty(idCampo)) {
					if (booleano.equals("Y"))
						sqlAdvanced.append(" INTERSECT");
					else if (booleano.equals("O"))
						sqlAdvanced.append(" UNION");
					else if (booleano.equals("Y NO")) {
						sqlAdvanced
								.append(DBUtils.getMinusKey(getConnection()));
					}
				}

				// Paréntesis de inicio
				if (StringUtils.isNotBlank(vo.getAbrirpar()[i]))
					sqlAdvanced.append("(");

				if (tipoCampo > 0 && StringUtils.isNotBlank(operador)) {
					valor1 = vo.getValor1()[i];
					valor2 = vo.getValor2()[i];
					// relacionesTesauro = (String [])
					// vo.getRelacionesTesAdv()[i];

					if (idCampo
							.equals(DefTipos.CAMPO_ESPECIAL_ID_CODIGO_REFERENCIA)) {
						if (operador.equals("=") && StringUtils.isEmpty(valor1)) {
							sqlAdvanced.append("(SELECT DISTINCT ")
									.append(ID_ELEMENTO_FIELD)
									.append(" AS IDELEMENTOCF")
									.append(" FROM ")
									.append(TABLE_NAME_ELEMENTO)
									.append(" WHERE ")
									.append(CODIGO_REFERENCIA_FIELD)
									.append(" IS NULL)");
						} else {
							sqlAdvanced
									.append(componerCampoEspecialIdCodigoReferencia(
											operador, valor1));
						}
					} else if (idCampo
							.equals(DefTipos.CAMPO_ESPECIAL_ID_NIVEL_DESCRIPCION)) {
						if (operador.equals("=") && StringUtils.isEmpty(valor1)) {
							sqlAdvanced
									.append("(SELECT DISTINCT ")
									.append(ID_ELEMENTO_FIELD)
									.append(" AS IDELEMENTOCF")
									.append(" FROM ")
									.append(TABLE_NAME_ELEMENTO)
									.append(" WHERE " + NIVEL_FIELD.toString()
											+ " NOT IN (")
									.append("SELECT "
											+ NivelCFDBEntityImpl.ID_NIVEL_FIELD)
									.append(" FROM "
											+ NivelCFDBEntityImpl.NIVELCF_TABLE_NAME)
									.append(" WHERE "
											+ NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD
											+ " IS NOT NULL))");
						} else {
							sqlAdvanced
									.append(componerCampoEspecialIdNivelDescripcion(
											operador, valor1));
						}
					} else if (idCampo
							.equals(DefTipos.CAMPO_ESPECIAL_ID_NUMERO_EXPEDIENTE)) {
						if (operador.equals("=") && StringUtils.isEmpty(valor1)) {
							sqlAdvanced
									.append("(SELECT DISTINCT ")
									.append(ID_ELEMENTO_FIELD)
									.append(" AS IDELEMENTOCF")
									.append(" FROM ")
									.append(TABLE_NAME_ELEMENTO)
									.append(" WHERE ")
									.append(ID_ELEMENTO_FIELD + " NOT IN (")
									.append("SELECT "
											+ UnidadDocumentalDBEntityImpl.CAMPO_ID)
									.append(" FROM "
											+ UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL)
									.append(" WHERE "
											+ UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE
											+ " IS NOT NULL))");
						} else {
							sqlAdvanced
									.append(componerCampoEspecialIdNumeroExpediente(
											operador, valor1));
						}
					} else if (idCampo
							.equals(DefTipos.CAMPO_ESPECIAL_ID_TITULO)) {
						/*
						 * sqlAdvanced.append("(SELECT DISTINCT ")
						 * .append(ID_ELEMENTO_FIELD)
						 * .append(" AS IDELEMENTOCF FROM ")
						 * .append(TABLE_NAME_ELEMENTO) .append(" WHERE ");
						 */

						if (operador.equals("=") && StringUtils.isEmpty(valor1)) {
							sqlAdvanced.append("(SELECT DISTINCT ")
									.append(ID_ELEMENTO_FIELD)
									.append(" AS IDELEMENTOCF")
									.append(" FROM ")
									.append(TABLE_NAME_ELEMENTO)
									.append(" WHERE ")
									.append(TITULO_FIELD + " IS NULL)");
						} else {
							sqlAdvanced.append(componerCampoEspecialIdTitulo(
									operador, valor1));
						}
					} else if (StringUtils.isNotEmpty(idCampo)) {
						switch (tipoCampo) {
						case DefTipos.TIPO_DATO_REFERENCIA: // Enlace
							if (operador.equals("=")
									&& StringUtils.isEmpty(valor1)) {
								sqlAdvanced
										.append("(SELECT DISTINCT ")
										.append(ID_ELEMENTO_FIELD)
										.append(" AS IDELEMENTOCF")
										.append(" FROM ")
										.append(TABLE_NAME_ELEMENTO)
										.append(" WHERE ")
										.append(ID_ELEMENTO_FIELD + " NOT IN (")
										.append("SELECT "
												+ ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO)
										.append(" FROM "
												+ ReferenciaDBEntityImpl.TABLE_NAME)
										.append(" WHERE "
												+ ReferenciaDBEntityImpl.CAMPO_IDOBJREF
												+ " IS NOT NULL")
										.append(" AND ");
								if (tipoReferencia != null) {
									sqlAdvanced
											.append(DBUtils
													.generateEQTokenField(
															ReferenciaDBEntityImpl.CAMPO_TIPOOBJREF,
															tipoReferencia
																	.intValue()));
								} else {
									sqlAdvanced
											.append(DBUtils
													.generateEQTokenField(
															ReferenciaDBEntityImpl.CAMPO_TIPOOBJREF,
															DefTipos.TIPO_REFERENCIA_DESCRIPTOR));
								}
								sqlAdvanced
										.append(" AND ")
										.append(DBUtils
												.generateEQTokenField(
														ReferenciaDBEntityImpl.CAMPO_ID_CAMPO,
														idCampo)).append("))");
							} else {
								sqlAdvanced
										.append(componerCampoEspecialTipoReferencia(
												idCampo, valor1, tipoReferencia));
							}
							break;
						case DefTipos.TIPO_DATO_FECHA: // Fecha
							if (operador.equals("=")
									&& StringUtils.isEmpty(valor1)) {
								sqlAdvanced
										.append("(SELECT DISTINCT ")
										.append(ID_ELEMENTO_FIELD)
										.append(" AS IDELEMENTOCF")
										.append(" FROM ")
										.append(TABLE_NAME_ELEMENTO)
										.append(" WHERE ")
										.append(ID_ELEMENTO_FIELD + " NOT IN (")
										.append("SELECT "
												+ FechaDBEntityImpl.CAMPO_ID_ELEMENTO)
										.append(" FROM "
												+ FechaDBEntityImpl.TABLE_NAME)
										.append(" WHERE "
												+ FechaDBEntityImpl.CAMPO_FECHA_INICIAL
												+ " IS NOT NULL")
										.append(" AND ")
										.append(FechaDBEntityImpl.CAMPO_FECHA_FINAL
												+ " IS NOT NULL")
										.append(" AND ")
										.append(DBUtils
												.generateEQTokenField(
														FechaDBEntityImpl.CAMPO_ID_CAMPO,
														idCampo)).append(")");
							} else {
								sqlAdvanced
										.append("(SELECT DISTINCT ")
										.append(FechaDBEntityImpl.CAMPO_ID_ELEMENTO)
										.append(" FROM ")
										.append(FechaDBEntityImpl.TABLE_NAME)
										.append(" WHERE ");
								if (CustomDateFormat.DATE_OPERATOR_EQ
										.equals(operador)) {
									sqlAdvanced
											.append(FechaDBEntityImpl.CAMPO_FECHA_INICIAL)
											.append("=")
											.append(DBUtils
													.getNativeToDateSyntax(
															getConnection(),
															valor1,
															CustomDateFormat.SDF_YYYYMMDD))
											.append("  AND ")
											.append(FechaDBEntityImpl.CAMPO_FECHA_FINAL)
											.append("=")
											.append(DBUtils
													.getNativeToDateSyntax(
															getConnection(),
															valor1,
															CustomDateFormat.SDF_YYYYMMDD))
											.append("  AND ")
											.append(DBUtils
													.generateEQTokenField(
															FechaDBEntityImpl.CAMPO_ID_CAMPO,
															idCampo));
								} else if (CustomDateFormat.DATE_OPERATOR_LT
										.equals(operador)) {
									sqlAdvanced
											.append(FechaDBEntityImpl.CAMPO_FECHA_INICIAL)
											.append("<")
											.append(DBUtils
													.getNativeToDateSyntax(
															getConnection(),
															valor1,
															CustomDateFormat.SDF_YYYYMMDD))
											.append("  AND ")
											.append(DBUtils
													.generateEQTokenField(
															FechaDBEntityImpl.CAMPO_ID_CAMPO,
															idCampo));
								} else if (CustomDateFormat.DATE_OPERATOR_LT_OR_EQ
										.equals(operador)) {
									sqlAdvanced
											.append(FechaDBEntityImpl.CAMPO_FECHA_FINAL)
											.append("<=")
											.append(DBUtils
													.getNativeToDateSyntax(
															getConnection(),
															valor1,
															CustomDateFormat.SDF_YYYYMMDD))
											.append(" AND ")
											.append(DBUtils
													.generateEQTokenField(
															FechaDBEntityImpl.CAMPO_ID_CAMPO,
															idCampo));
								} else if (CustomDateFormat.DATE_OPERATOR_GT
										.equals(operador)) {
									sqlAdvanced
											.append(FechaDBEntityImpl.CAMPO_FECHA_FINAL)
											.append(">")
											.append(DBUtils
													.getNativeToDateSyntax(
															getConnection(),
															valor1,
															CustomDateFormat.SDF_YYYYMMDD))
											.append(" AND ")
											.append(DBUtils
													.generateEQTokenField(
															FechaDBEntityImpl.CAMPO_ID_CAMPO,
															idCampo));
								} else if (CustomDateFormat.DATE_OPERATOR_GT_OR_EQ
										.equals(operador)) {
									sqlAdvanced
											.append(FechaDBEntityImpl.CAMPO_FECHA_INICIAL)
											.append(">")
											.append(DBUtils
													.getNativeToDateSyntax(
															getConnection(),
															valor1,
															CustomDateFormat.SDF_YYYYMMDD))
											.append(" AND ")
											.append(DBUtils
													.generateEQTokenField(
															FechaDBEntityImpl.CAMPO_ID_CAMPO,
															idCampo));
								} else if (CustomDateFormat.DATE_OPERATOR_RANGE
										.equals(operador)) {
									sqlAdvanced
											.append(FechaDBEntityImpl.CAMPO_FECHA_INICIAL)
											.append(" BETWEEN ")
											.append(DBUtils
													.getNativeToDateSyntax(
															getConnection(),
															valor1,
															CustomDateFormat.SDF_YYYYMMDD))
											.append(" AND ")
											.append(DBUtils
													.getNativeToDateSyntax(
															getConnection(),
															valor2,
															CustomDateFormat.SDF_YYYYMMDD))
											.append(" AND ")
											.append(FechaDBEntityImpl.CAMPO_FECHA_FINAL)
											.append(" BETWEEN ")
											.append(DBUtils
													.getNativeToDateSyntax(
															getConnection(),
															valor1,
															CustomDateFormat.SDF_YYYYMMDD))
											.append(" AND ")
											.append(DBUtils
													.getNativeToDateSyntax(
															getConnection(),
															valor2,
															CustomDateFormat.SDF_YYYYMMDD))
											.append(" AND ")
											.append(DBUtils
													.generateEQTokenField(
															FechaDBEntityImpl.CAMPO_ID_CAMPO,
															idCampo));
								} else if (CustomDateFormat.DATE_OPERATOR_CONTAINS
										.equals(operador)) {
									sqlAdvanced
											.append(FechaDBEntityImpl.CAMPO_FECHA_INICIAL)
											.append("<=")
											.append(DBUtils
													.getNativeToDateSyntax(
															getConnection(),
															valor1,
															CustomDateFormat.SDF_YYYYMMDD))
											.append(" AND ")
											.append(FechaDBEntityImpl.CAMPO_FECHA_FINAL)
											.append(">=")
											.append(DBUtils
													.getNativeToDateSyntax(
															getConnection(),
															valor1,
															CustomDateFormat.SDF_YYYYMMDD))
											.append(") OR (")
											.append(FechaDBEntityImpl.CAMPO_FECHA_INICIAL)
											.append(" BETWEEN ")
											.append(DBUtils
													.getNativeToDateSyntax(
															getConnection(),
															valor1,
															CustomDateFormat.SDF_YYYYMMDD))
											.append(" AND ")
											.append(DBUtils
													.getNativeToDateSyntax(
															getConnection(),
															valor2,
															CustomDateFormat.SDF_YYYYMMDD))
											.append(")) AND ")
											.append(DBUtils
													.generateEQTokenField(
															FechaDBEntityImpl.CAMPO_ID_CAMPO,
															idCampo));
								} else if (CustomDateFormat.DATE_OPERATOR_EXACT
										.equals(operador)) {
									sqlAdvanced
											.append(FechaDBEntityImpl.CAMPO_FECHA_INICIAL)
											.append("=")
											.append(DBUtils
													.getNativeToDateSyntax(
															getConnection(),
															valor1,
															CustomDateFormat.SDF_YYYYMMDD))
											.append(" AND ")
											.append(FechaDBEntityImpl.CAMPO_FECHA_FINAL)
											.append("=")
											.append(DBUtils
													.getNativeToDateSyntax(
															getConnection(),
															valor1,
															CustomDateFormat.SDF_YYYYMMDD))
											.append(" AND ")
											.append(DBUtils
													.generateEQTokenField(
															FechaDBEntityImpl.CAMPO_ID_CAMPO,
															idCampo));
								}
							}
							sqlAdvanced.append(")");
							break;
						case DefTipos.TIPO_DATO_NUMERICO: // Número
							if (operador.equals("=")
									&& StringUtils.isEmpty(valor1)) {
								sqlAdvanced
										.append("(SELECT DISTINCT ")
										.append(ID_ELEMENTO_FIELD)
										.append(" AS IDELEMENTOCF")
										.append(" FROM ")
										.append(TABLE_NAME_ELEMENTO)
										.append(" WHERE ")
										.append(ID_ELEMENTO_FIELD + " NOT IN (")
										.append("SELECT "
												+ NumeroDBEntityImpl.CAMPO_ID_ELEMENTO)
										.append(" FROM "
												+ NumeroDBEntityImpl.TABLE_NAME)
										.append(" WHERE "
												+ NumeroDBEntityImpl.CAMPO_VALOR
												+ " IS NOT NULL")
										.append(" AND ")
										.append(DBUtils
												.generateEQTokenField(
														NumeroDBEntityImpl.CAMPO_ID_CAMPO,
														idCampo)).append("))");
							} else {
								sqlAdvanced
										.append(componerCampoEspecialTipoNumerico(
												idCampo, operador, valor1,
												valor2));
							}
							break;
						case DefTipos.TIPO_DATO_TEXTO_LARGO: // Texto largo
							if (operador.equals("=")
									&& StringUtils.isEmpty(valor1)) {
								sqlAdvanced
										.append("(SELECT DISTINCT ")
										.append(ID_ELEMENTO_FIELD)
										.append(" AS IDELEMENTOCF")
										.append(" FROM ")
										.append(TABLE_NAME_ELEMENTO)
										.append(" WHERE ")
										.append(ID_ELEMENTO_FIELD + " NOT IN (")
										.append("SELECT "
												+ TextoLargoDBEntityImpl.CAMPO_ID_ELEMENTO)
										.append(" FROM "
												+ TextoLargoDBEntityImpl.TABLE_NAME)
										.append(" WHERE "
												+ TextoLargoDBEntityImpl.CAMPO_VALOR
												+ " IS NOT NULL")
										.append(" AND ")
										.append(DBUtils
												.generateEQTokenField(
														TextoLargoDBEntityImpl.CAMPO_ID_CAMPO,
														idCampo)).append("))");
							} else {
								sqlAdvanced
										.append(componerCampoEspecialTipoTextoLargo(
												idCampo, operador, valor1));
							}
							break;

						case DefTipos.TIPO_DATO_TEXTO_CORTO: // Texto corto
							if (operador.equals("=")
									&& StringUtils.isEmpty(valor1)) {
								sqlAdvanced
										.append("(SELECT DISTINCT ")
										.append(ID_ELEMENTO_FIELD)
										.append(" AS IDELEMENTOCF")
										.append(" FROM ")
										.append(TABLE_NAME_ELEMENTO)
										.append(" WHERE ")
										.append(ID_ELEMENTO_FIELD + " NOT IN (")
										.append("SELECT "
												+ TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO)
										.append(" FROM "
												+ TextoCortoDBEntityImpl.TABLE_NAME)
										.append(" WHERE "
												+ TextoCortoDBEntityImpl.CAMPO_VALOR
												+ " IS NOT NULL")
										.append(" AND ")
										.append(DBUtils
												.generateEQTokenField(
														TextoCortoDBEntityImpl.CAMPO_ID_CAMPO,
														idCampo)).append("))");
							} else {
								sqlAdvanced
										.append(componerCampoEspecialTipoTextoCorto(
												idCampo, operador, valor1));
							}
							break;
						}
					}
				}

				// Paréntesis de fin
				if (StringUtils.isNotBlank(vo.getCerrarpar()[i]))
					sqlAdvanced.append(")");
			}

			if (sqlAdvanced.length() > 0) {
				allTableNames.append(",(").append(sqlAdvanced).append(") IDS");
				qual.append(" AND ").append(CUSTOM_ID_ELEMENTO_FIELD)
						.append("=IDS.IDELEMENTOCF");
			}
		}

		// Orden
		if (vo.getPageInfo() != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("codigo", CUSTOM_CODIGO_FIELD);
			criteriosOrdenacion.put("titulo", CUSTOM_TITULO_FIELD);
			criteriosOrdenacion.put("nombreFondo", CUSTOM_B_TITULO_FIELD);
			criteriosOrdenacion.put("nombreNivel", CUSTOM_NOMBRE_NIVEL_FIELD);
			criteriosOrdenacion.put("estado", CUSTOM_ESTADO_ELEMENTO_FIELD);

			return getVOS(qual.toString(),
					vo.getPageInfo().getOrderBy(criteriosOrdenacion),
					allTableNames.toString(), allColsDefs,
					ElementoCuadroClasificacion.class, vo.getPageInfo());
		} else {
			qual.append(" ORDER BY ").append(
					CUSTOM_CODIGO_FIELD.getQualifiedName());

			return getVOS(qual.toString(), allTableNames.toString(),
					allColsDefs, ElementoCuadroClasificacion.class);
		}
	}

	/**
	 * Obtiene las fechas extremas de un clasificador de series.
	 *
	 * @param idClfSeries
	 *            Identificador del clasificador de series.
	 * @return Fechas extremas.
	 */
	public abstract CustomDateRange getFechasExtremasClasificadorSeries(
			String idClfSeries);

	public abstract ElementoCuadroClasificacionVO getElementoPadre(
			String idElemento);

	/**
	 * Modifica en la base de datos el titulo de un elemento del cuadro de
	 * clasificacion
	 *
	 * @param idElemento
	 *            Identificador del elemento al que se modifica el titulo
	 * @param denominacion
	 *            Nuevo titulo a establecer
	 */
	public void updateTitulo(String idElemento, String denominacion) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(ID_ELEMENTO_FIELD, idElemento));
		Map columToUpdate = Collections
				.singletonMap(TITULO_FIELD, denominacion);
		updateFields(qual.toString(), columToUpdate, TABLE_NAME_ELEMENTO);
	}

	public void setListaControlAcceso(String idElemento,
			String idListaControlAcceso) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(ID_ELEMENTO_FIELD, idElemento));
		Map toUpdate = Collections.singletonMap(LISTA_ACCESO_FIELD,
				idListaControlAcceso);
		updateFields(qual.toString(), toUpdate, TABLE_NAME_ELEMENTO);
	}

	public void updateElementoCuadroClasificacion(
			ElementoCuadroClasificacionVO elementoCuadroClasificacion) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(ID_ELEMENTO_FIELD,
						elementoCuadroClasificacion.getId()));

		updateVO(qual.toString(), TABLE_NAME_ELEMENTO, COLS_DEFS_ELEMENTO,
				elementoCuadroClasificacion);
	}

	/**
	 * Obtiene la lista de volúmenes de las series de un clasificador de series.
	 *
	 * @param idClfSeries
	 *            Identificador del clasificador de series.
	 * @return Lista de volúmenes de las series de un clasificador de series.
	 */
	public abstract List getVolumenesSeriesClasificadorSeries(String idClfSeries);

	/**
	 * Actualiza el valor de la columna en la que se mantiene el identificador
	 * del elemento padre de un elemento del cuadro de clasificación de fondos
	 * documentales
	 *
	 * @param idElementoCF
	 *            Identificador de elemento del cuadro de clasificación de
	 *            fondos
	 * @param idNuevoPadre
	 *            Nuevo valor para la columna en la que se almacena el valor del
	 *            padre del elemento del cuadro de clasificación
	 */
	public void setElementoPadre(String idElementoCF, String idNuevoPadre) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(ID_ELEMENTO_FIELD, idElementoCF));
		Map colToUpdate = Collections.singletonMap(IDPADRE_FIELD, idNuevoPadre);
		updateFields(qual.toString(), colToUpdate, TABLE_NAME_ELEMENTO);
	}

	/**
	 * Actualiza la información de control de acceso de un elemento.
	 *
	 * @param idElemento
	 *            Identificador del elemento.
	 * @param nivelAcceso
	 *            Nivel de acceso del elemento ({@link NivelAcceso}).
	 * @param idListaControlAcceso
	 *            Identificador de la lista de control de acceso.
	 */
	public void updateControlAcceso(String idElemento, int nivelAcceso,
			String idListaControlAcceso) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(ID_ELEMENTO_FIELD, idElemento));

		Map toUpdate = new HashMap();
		toUpdate.put(NIVEL_ACCESO_FIELD, new Integer(nivelAcceso));
		toUpdate.put(LISTA_ACCESO_FIELD, idListaControlAcceso);

		updateFields(qual.toString(), toUpdate, TABLE_NAME_ELEMENTO);
	}

	/**
	 * Actualiza la información de control de acceso de un elemento.
	 *
	 * @param idElemento
	 *            Identificador del elemento.
	 * @param nivelAcceso
	 *            Nivel de acceso del elemento ({@link NivelAcceso}).
	 * @param idListaControlAcceso
	 *            Identificador de la lista de control de acceso.
	 */
	public void updateControlAcceso(
			TablaTemporalFondosVO tablaTemporalFondosVO, int nivelAcceso,
			String idListaControlAcceso) {
		if (StringUtils.isNotEmpty(idListaControlAcceso)) {
			StringBuffer qual = new StringBuffer(DBUtils.WHERE)
					.append(DBUtils.generateInTokenFieldSubQuery(
							ID_ELEMENTO_FIELD,
							TablaTemporalDBEntityImpl
									.getSelectIdElementocfFromTemporal(tablaTemporalFondosVO)));

			Map toUpdate = new HashMap();

			if (nivelAcceso > 0) {
				toUpdate.put(NIVEL_ACCESO_FIELD, new Integer(nivelAcceso));
			}
			toUpdate.put(LISTA_ACCESO_FIELD, idListaControlAcceso);

			updateFields(qual.toString(), toUpdate, TABLE_NAME_ELEMENTO);
		}
	}

	public DbColumnDef[] getDbColumnDefs(ArrayList dbColumnDefArray) {
		DbColumnDef[] ret = new DbColumnDef[dbColumnDefArray.size()];

		Iterator it = dbColumnDefArray.iterator();
		int cont = 0;
		while (it.hasNext()) {
			DbColumnDef column = (DbColumnDef) it.next();
			ret[cont] = column;
			cont++;
		}
		return ret;
	}

	/**
	 * Obtiene una Lista de Strings con los Ids que cumplen los criterios. Se
	 * utiliza en la búsqueda rápida desde la bandeja de entrada
	 *
	 * @return
	 */
	public List getIdsElementos(BusquedaElementosVO busquedaElementosVO,
			int numMaxResultados, Busqueda busqueda)
			throws TooManyResultsException {

		String aliasTablaPrincipal = "ELEMENTOCF";
		BusquedaFondosQueryVO busquedaFondosQueryVO = BusquedasGeneratorHelper
				.getQueryElementos(getConnection(), null, null,
						busquedaElementosVO, busqueda, aliasTablaPrincipal,
						busqueda.isResultadoSinElementosBloqueados(), false);

		List ltElementos = getVOS(busquedaFondosQueryVO.getWith(),
				busquedaFondosQueryVO.getWhere(),
				busquedaFondosQueryVO.getTablesIds(),
				busquedaFondosQueryVO.getColsIds(),
				busquedaFondosQueryVO.getColsIdsFill(), MutableStringId.class,
				busquedaFondosQueryVO.getOrderByIds(), numMaxResultados,
				busquedaFondosQueryVO.getTablesIds(),
				getCustomizedField(ID_ELEMENTO_FIELD, aliasTablaPrincipal)
						.getQualifiedName());

		CollectionUtils.transform(ltElementos, new Transformer() {
			public Object transform(Object obj) {
				MutableStringId mutableStringId = (MutableStringId) obj;
				return mutableStringId.getId();
			}
		});

		return ltElementos;
	}

	/**
	 * Obtiene la SQL para obtener los id de los elementos que cumplen los
	 * criterios. Por problemas de memoria es mejor pasarle a una select la
	 * consulta que una lista con todos los ids de los elementos
	 *
	 * @return
	 */
	// public List getIdsElementos(BusquedaGeneralVO busquedaGeneralVO, int
	// numMaxResultados) throws TooManyResultsException{
	public ConsultaConnectBy getSQLIdsElementos(
			BusquedaElementosVO busquedaElementosVO, Busqueda busqueda,
			boolean onlyUdocs) {
		String[] fromAliasWhere = getFromWhereIdsElementos(busquedaElementosVO,
				busqueda);
		String sqlFrom = fromAliasWhere[0];
		String aliasTabla = fromAliasWhere[1];
		String qual = fromAliasWhere[2];
		String withQuery = fromAliasWhere[3];

		TableDef tablaElemento = new TableDef(TABLE_NAME_ELEMENTO, aliasTabla);
		DbColumnDef columna = new DbColumnDef(tablaElemento, ID_ELEMENTO_FIELD);

		if (onlyUdocs) {
			qual += " AND "
					+ DBUtils
							.generateEQTokenField(
									getCustomizedField(TIPO_ELEMENTO_FIELD,
											aliasTabla),
									TipoNivelCF.UNIDAD_DOCUMENTAL
											.getIdentificador());
		}

		String sqlClause = DbSelectStatement.getSelectStmtText(
				sqlFrom.toString(), columna.getQualifiedName(),
				qual.toString(), true);

		ConsultaConnectBy consultaConnectBy = new ConsultaConnectBy(sqlClause,
				withQuery);

		return consultaConnectBy;
	}

	public ConsultaConnectBy getSQLPadresEIdsElementos(
			BusquedaElementosVO busquedaElementosVO, Busqueda busqueda) {
		String[] fromAliasWhere = getFromWhereIdsElementos(busquedaElementosVO,
				busqueda);
		String sqlFrom = fromAliasWhere[0];
		String aliasTabla = fromAliasWhere[1];
		String qual = fromAliasWhere[2];
		String withQuery = fromAliasWhere[3];

		TableDef tablaElemento = new TableDef(TABLE_NAME_ELEMENTO, aliasTabla);
		StringBuffer columnas = new StringBuffer(new DbColumnDef(tablaElemento,
				ID_ELEMENTO_FIELD).getQualifiedName()).append(", ").append(
				new DbColumnDef(tablaElemento, IDPADRE_FIELD)
						.getQualifiedName());

		String sqlClause = DbSelectStatement.getSelectStmtText(withQuery,
				sqlFrom.toString(), columnas.toString(), qual.toString(), true);
		ConsultaConnectBy consultaConnectBy = new ConsultaConnectBy(sqlClause,
				withQuery);

		return consultaConnectBy;
	}

	private String[] getFromWhereIdsElementos(
			BusquedaElementosVO busquedaElementosVO, Busqueda busqueda) {
		Map queryMap = new HashMap();
		StringBuffer sqlFrom = new StringBuffer(), qual = new StringBuffer();
		String listaTablasCompuesta = new String();

		String aliasElemento = null;
		if (busquedaElementosVO.getSelectedIds() != null) { // reemplazo
															// avanzado -> no
															// tener en cuenta
															// udocs/ui
															// bloqueadas
			sqlFrom.append(TABLE_NAME_ELEMENTO);
			qual.append("WHERE ")
					.append(DBUtils
							.generateInTokenField(ID_ELEMENTO_FIELD, Arrays
									.asList(busquedaElementosVO
											.getSelectedIds())));
			aliasElemento = TABLE_NAME_ELEMENTO;
		} else {

			// Obtener los elementos de la búsqueda: columnas, tablas y
			// condiciones
			queryMap = fillQuery(busqueda.getMapEntrada(),
					busqueda.getMapSalida(), busqueda.getRestricciones(),
					busquedaElementosVO);

			// Obtener los elementos que van a componer las partes de la
			// consulta
			ArrayList listaTablas = (ArrayList) queryMap.get(TABLAS_QUERY_IDS);
			ArrayList listaCondiciones = (ArrayList) queryMap
					.get(CONDICIONES_QUERY_IDS);
			// DbColumnDef [] cols =
			// getDbColumnDefs((ArrayList)queryMap.get("COLUMNAS_QUERY_IDS"));

			aliasElemento = "ECC1";
			// TableDef tablaElemento = new
			// TableDef(TABLE_NAME_ELEMENTO,aliasElemento);

			// Componer from de la consulta
			if (listaTablas != null && listaTablas.size() > 0) {
				listaTablasCompuesta = DBUtils
						.generateTableSet(listToStringArray(listaTablas));
				sqlFrom.append(listaTablasCompuesta);
			}

			// Componer el where de la consulta
			if (listaCondiciones != null && listaCondiciones.size() > 0)
				qual.append(condsToWhere(listaCondiciones, true));

			if (busqueda.isResultadoSinElementosBloqueados()
					&& !busqueda.isTemporalyConElementosBloqueados()) {
				// modificar from para que incluya LEFT OUTER JOIN para detectar
				// elementos bloqueados
				String sqlFromAux = sqlFrom.toString();
				int pos = sqlFromAux.indexOf(TABLE_NAME_ELEMENTO + " "
						+ aliasElemento);
				String restoSqlFrom = sqlFromAux.substring(pos
						+ (TABLE_NAME_ELEMENTO + " " + aliasElemento).length());
				// TableDef tablaElem = new
				// TableDef(aliasElemento,TABLE_NAME_ELEMENTO);
				DbColumnDef idElem = getCustomizedField(ID_ELEMENTO_FIELD,
						aliasElemento);
				StringBuffer sqlFromBuffer = new StringBuffer(
						sqlFromAux.substring(
								0,
								pos
										+ (TABLE_NAME_ELEMENTO + " " + aliasElemento)
												.length()))
						.append(" LEFT OUTER JOIN ")
						.append(UnidadDocumentalElectronicaDBEntityImpl.TABLE_NAME)
						.append(" ON ")
						.append(DBUtils
								.generateJoinCondition(
										idElem,
										UnidadDocumentalElectronicaDBEntityImpl.IDELEMENTOCF_FIELD))
						.append(" LEFT OUTER JOIN ")
						.append(UDocEnUiDepositoDbEntityImpl.TABLE_NAME)
						.append(" ON ")
						.append(DBUtils.generateJoinCondition(idElem,
								UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD))
						.append(" LEFT OUTER JOIN ")
						.append(UInstalacionDepositoDBEntity.TABLE_NAME)
						.append(" ON ")
						.append(DBUtils
								.generateJoinCondition(
										UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD,
										UInstalacionDepositoDBEntity.ID_FIELD))
						.append(" " + restoSqlFrom);

				sqlFrom = sqlFromBuffer;
				qual.append(" AND ")
						.append("(")
						.append(DBUtils
								.generateEQTokenField(
										UInstalacionDepositoDBEntity.MARCAS_BLOQUEO_FIELD,
										0))
						.append(" OR ")
						.append(DBUtils
								.generateEQTokenField(
										UnidadDocumentalElectronicaDBEntityImpl.MARCAS_BLOQUEO_FIELD,
										0))
						.append(" OR (")
						.append(DBUtils
								.generateIsNullCondition(
										getConnection(),
										UInstalacionDepositoDBEntity.MARCAS_BLOQUEO_FIELD))
						.append(" AND ")
						.append(DBUtils
								.generateIsNullCondition(
										getConnection(),
										UnidadDocumentalElectronicaDBEntityImpl.MARCAS_BLOQUEO_FIELD))
						.append("))");
			}
		}
		DBUtils.addPermissionsCheckingClauses(
				busquedaElementosVO.getServiceClient(), qual,
				getCustomizedField(NIVEL_ACCESO_FIELD, aliasElemento),
				getCustomizedField(ARCHIVO_FIELD, aliasElemento),
				getCustomizedField(LISTA_ACCESO_FIELD, aliasElemento));

		return new String[] { sqlFrom.toString(), aliasElemento,
				qual.toString(), (String) queryMap.get(WITH_STATEMENT) };
	}

	/**
	 * Obtener los ids de elementos que cumplan las condiciones de búsqueda
	 * incluyendo uno por cada una de sus partes en caso de que las tuviesen
	 *
	 * @param vo
	 * @param numMaxResultados
	 * @param busqueda
	 * @return
	 * @throws TooManyResultsException
	 */
	public List getIdsElementosYPartes(BusquedaElementosVO busquedaElementosVO,
			int numMaxResultados, Busqueda busqueda)
			throws TooManyResultsException {

		String aliasTablaPrincipal = "ELEMENTOCF";
		BusquedaFondosQueryVO busquedaFondosQueryVO = BusquedasGeneratorHelper
				.getQueryElementos(getConnection(), null, null,
						busquedaElementosVO, busqueda, aliasTablaPrincipal,
						busqueda.isResultadoSinElementosBloqueados(), true);

		List ltElementos = getVOS(busquedaFondosQueryVO.getWith(),
				busquedaFondosQueryVO.getWhere(),
				busquedaFondosQueryVO.getTablesIds(),
				busquedaFondosQueryVO.getColsIds(),
				busquedaFondosQueryVO.getColsIdsFill(), ElementoCFVO.class,
				busquedaFondosQueryVO.getOrderByIds(), numMaxResultados,
				busquedaFondosQueryVO.getTablesIds(),
				UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD
						.getQualifiedName());

		CollectionUtils.transform(ltElementos, new Transformer() {
			public Object transform(Object obj) {
				ElementoCFVO elementoCFVO = (ElementoCFVO) obj;
				return elementoCFVO.getId()
						+ Constants.SEPARADOR_IDENTIFICADOR_BUSQUEDAS
						+ elementoCFVO.getSignaturaudoc();
			}
		});

		return ltElementos;
	}

	public boolean isAccesibleWithPermissions(ServiceClient serviceClient,
			String idElemento) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(ID_ELEMENTO_FIELD, idElemento));
		DBUtils.addPermissionsCheckingClauses(serviceClient, qual,
				NIVEL_ACCESO_FIELD, ARCHIVO_FIELD, LISTA_ACCESO_FIELD);

		List list = getVOS(qual.toString(), TABLE_NAME_ELEMENTO,
				COLS_DEFS_ELEMENTO, ElementoCuadroClasificacion.class);
		return !ListUtils.isEmpty(list);
	}

	public String componerCondicionIdFondo(String idFondo, String aliasElemento) {
		StringBuffer qual = new StringBuffer("");

		if (StringUtils.isNotBlank(idFondo))
			qual.append(DBUtils.generateEQTokenField(
					getCustomizedField(IDFONDO_FIELD, aliasElemento), idFondo));

		return qual.toString();
	}

	public String componerCondicionProcedimiento(String procedimiento,
			String aliasElemento) {
		StringBuffer qual = new StringBuffer();
		if (StringUtils.isNotBlank(procedimiento)) {
			qual.append(
					getCustomizedField(ID_ELEMENTO_FIELD, aliasElemento)
							.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(SerieDBEntityImpl.CAMPO_ID.getQualifiedName())
					.append(" FROM " + SerieDBEntityImpl.TABLE_NAME_SERIE
							+ " WHERE ")
					.append(DBUtils.generateEQTokenField(
							SerieDBEntityImpl.CAMPO_IDPROCEDIMIENTO,
							procedimiento)).append(")");
		}

		return qual.toString();
	}

	public ConsultaConnectBy componerCondicionIdAmbito(String aliasElemento,
			String[] idsAmbito, Map condicionesMap) throws IeciTdException {
		ConsultaConnectBy consultaConnectBy = null;

		if (ArrayUtils.isNotEmpty(idsAmbito)) {
			consultaConnectBy = DbUtil
					.generateNativeSQLWithConnectBy(getConnection(),
							new TableDef(TABLE_NAME_ELEMENTO),
							ID_ELEMENTO_FIELD, IDPADRE_FIELD, idsAmbito,
							condicionesMap);

			if (consultaConnectBy != null) {
				if (StringUtils.isNotEmpty(consultaConnectBy.getSqlClause())) {
					StringBuffer sql = new StringBuffer()
							.append(getCustomizedField(ID_ELEMENTO_FIELD,
									aliasElemento)).append(DBUtils.IN)
							.append("(")
							.append(consultaConnectBy.getSqlClause())
							.append(")");

					consultaConnectBy.setSqlClause(sql.toString());
				}
			}
		}
		return consultaConnectBy;
	}

	public String componerWithAmbito() {
		StringBuffer strWith = new StringBuffer();
		return strWith.toString();
	}

	public String componerCondicionFicha(String idFicha) {
		StringBuffer qual = new StringBuffer("");

		if (StringUtils.isNotBlank(idFicha)) {
			qual.append(DBUtils.generateEQTokenField(
					getCustomizedField(IDFICHADESCR_FIELD, "A"), idFicha));
		}

		return qual.toString();
	}

	public String componerCondicionCodigo(String codigo, String aliasElemento) {
		StringBuffer qual = new StringBuffer("");

		ArrayList listaTexto = obtenerListaCondicionesTexto(codigo);
		if (listaTexto != null && listaTexto.size() > 0) {
			ArrayList conds = new ArrayList();
			Iterator it = listaTexto.iterator();
			while (it.hasNext()) {
				String texto = (String) it.next();
				conds.add(DBUtils.generateLikeTokenField(
						getCustomizedField(CODIGO_FIELD, aliasElemento), texto));
			}

			qual.append("(").append(condsToWhere(conds, false).toString())
					.append(")");
		}
		return qual.toString();
	}

	public String componerCondicionNumeroExpediente(String numeroExpediente,
			String calificadorNumeroExpediente, String aliasElemento,
			Map restricciones) {
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotEmpty(numeroExpediente)) {
			qual.append(
					getCustomizedField(ID_ELEMENTO_FIELD, aliasElemento)
							.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(UnidadDocumentalDBEntityImpl.ID_ELEMENTOCF_COLUMN_NAME)
					.append(" FROM ");

			if (restricciones != null
					&& restricciones
							.containsKey(RestriccionesCamposBusquedas.RESTRICCION_CAMPO_BUSQUEDA_UDOC_ENRELENTREGA)) {
				JoinDefinition[] joinsUDocEnUiUDoc = new JoinDefinition[] { new JoinDefinition(
						new DbColumnDef(new TableDef(
								UDocEnUiDepositoDbEntityImpl.TABLE_NAME),
								UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD),
						new DbColumnDef(
								new TableDef(
										UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL),
								UnidadDocumentalDBEntityImpl.CAMPO_ID)) };

				qual.append(DBUtils.generateInnerJoinCondition(new TableDef(
						UDocEnUiDepositoDbEntityImpl.TABLE_NAME),
						joinsUDocEnUiUDoc));
			} else
				qual.append(UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL);

			qual.append(" WHERE ")
					.append(DBUtils
							.generateLikeFieldQualified(
									UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE,
									numeroExpediente,
									UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL,
									calificadorNumeroExpediente));

			qual.append(")");
		}
		return qual.toString();
	}

	public String componerCondicionTitulo(String titulo, String aliasElemento) {
		StringBuffer qual = new StringBuffer("");

		if (StringUtils.isNotBlank(titulo)) {
			ArrayList listaTexto = obtenerListaCondicionesTexto(titulo);
			if (listaTexto != null && listaTexto.size() > 0) {
				ArrayList conds = new ArrayList();
				Iterator it = listaTexto.iterator();
				while (it.hasNext()) {
					String texto = (String) it.next();

					conds.add(DBUtils.generateContainsTokenField(
							getConnection(),
							getCustomizedField(TITULO_FIELD, aliasElemento),
							getCustomizedField(IDXTITULO_FIELD, aliasElemento),
							texto));
				}

				qual.append("(").append(condsToWhere(conds, false).toString())
						.append(")");
			}
		}
		return qual.toString();
	}

	public String componerCondicionTipoElemento(String tipoElemento,
			String aliasElemento) {
		StringBuffer qual = new StringBuffer("");

		if (StringUtils.isNotBlank(tipoElemento)) {
			qual.append(DBUtils.generateEQTokenField(
					getCustomizedField(TIPO_ELEMENTO_FIELD, aliasElemento),
					tipoElemento));
		}

		return qual.toString();
	}

	/**
	 * Permite obtener la definición de una columna a partir de su nombre
	 *
	 * @param nameColumn
	 *            Nombre de la columna
	 * @return Definición de la columna
	 */
	private DbColumnDef getColumnDef(String nameColumn) {
		for (int i = 0; i < COLS_DEFS_ELEMENTO.length; i++) {
			DbColumnDef col = COLS_DEFS_ELEMENTO[i];
			if (col.getName().equals(nameColumn)) {
				return col;
			}
		}
		return null;
	}

	public String componerCondicionElementosExcluir(Map elementosExcluir,
			String aliasElemento) {
		StringBuffer qual = new StringBuffer("");
		int numCondiciones = 0;

		if ((elementosExcluir != null) && (!elementosExcluir.isEmpty())) {
			Iterator it = elementosExcluir.entrySet().iterator();
			while (it.hasNext()) {

				Entry entry = (Entry) it.next();
				String columna = (String) entry.getKey();
				String[] valores = (String[]) entry.getValue();
				DbColumnDef colDef = getColumnDef(columna);
				if ((colDef != null) && (valores != null)
						&& (valores.length > 0)) {
					numCondiciones++;
					qual.append(DBUtils.generateNotInTokenField(
							getCustomizedField(colDef, aliasElemento), valores));

					if (it.hasNext())
						qual.append(" AND ");
				}
			}
		}

		// Sólo retornar algo si alguna de las condiciones es correcta
		if (numCondiciones > 0)
			return qual.toString();
		else
			return Constants.STRING_EMPTY;
	}

	public String componerCondicionSubqueryElementosRestringir(
			Map subqueryElementosRestringir, String aliasElemento) {
		StringBuffer qual = new StringBuffer("");
		int numCondiciones = 0;

		if ((subqueryElementosRestringir != null)
				&& (!subqueryElementosRestringir.isEmpty())) {
			Iterator it = subqueryElementosRestringir.entrySet().iterator();
			while (it.hasNext()) {
				Entry entry = (Entry) it.next();
				String columna = (String) entry.getKey();
				String subquery = (String) entry.getValue();
				DbColumnDef colDef = getColumnDef(columna);
				if ((colDef != null) && (subquery != null)
						&& (!"".equals(subquery))) {
					numCondiciones++;
					qual.append(DBUtils
							.generateInTokenFieldSubQuery(
									getCustomizedField(colDef, aliasElemento),
									subquery));

					if (it.hasNext())
						qual.append(" AND ");
				}
			}
		}

		// Sólo retornar algo si alguna de las condiciones es correcta
		if (numCondiciones > 0)
			return qual.toString();
		else
			return Constants.STRING_EMPTY;
	}

	public String componerCondicionElementosRestringir(Map elementosRestringir,
			String aliasElemento) {
		StringBuffer qual = new StringBuffer("");
		int numCondiciones = 0;

		if ((elementosRestringir != null) && (!elementosRestringir.isEmpty())) {
			Iterator it = elementosRestringir.entrySet().iterator();
			while (it.hasNext()) {

				Entry entry = (Entry) it.next();
				String columna = (String) entry.getKey();
				String[] valores = (String[]) entry.getValue();
				DbColumnDef colDef = getColumnDef(columna);
				if ((colDef != null) && (valores != null)
						&& (valores.length > 0)) {
					numCondiciones++;
					qual.append(DBUtils.generateInTokenField(
							getCustomizedField(colDef, aliasElemento), valores));

					if (it.hasNext())
						qual.append(" AND ");
				}
			}
		}

		// Sólo retornar algo si alguna de las condiciones es correcta
		if (numCondiciones > 0)
			return qual.toString();
		else
			return Constants.STRING_EMPTY;
	}

	public String componerCondicionNiveles(String[] niveles,
			String aliasElemento) {
		StringBuffer qual = new StringBuffer("");
		// Niveles
		if (!ArrayUtils.isEmpty(niveles)) {
			qual.append(DBUtils.generateInTokenField(
					getCustomizedField(NIVEL_FIELD, aliasElemento), niveles));
		}

		return qual.toString();
	}

	public String componerCondicionEstados(String[] estados,
			String aliasElemento) {
		StringBuffer qual = new StringBuffer("");

		if (!ArrayUtils.isEmpty(estados))
			qual.append(DBUtils.generateInTokenField(
					getCustomizedField(ESTADO_ELEMENTO_FIELD, aliasElemento),
					estados));

		return qual.toString();
	}

	public String componerCondicionBloqueos(String[] bloqueos,
			String aliasElemento) {
		StringBuffer qual = new StringBuffer("");

		if (!ArrayUtils.isEmpty(bloqueos)) {
			// Solamente puede llegarle un valor (0 ó 1)
			if (FondosConstants.UDOC_DESBLOQUEADA.equals(bloqueos[0])) {
				qual.append(
						DBUtils.generateEQTokenField(
								UnidadDocumentalDBEntityImpl.CAMPO_MARCAS_BLOQUEO,
								FondosConstants.UDOC_DESBLOQUEADA))
						.append(DBUtils.OR)
						.append(DBUtils
								.generateIsNullCondition(
										getConnection(),
										UnidadDocumentalDBEntityImpl.CAMPO_MARCAS_BLOQUEO));
			} else
				qual.append(DBUtils.generateGTTokenField(
						UnidadDocumentalDBEntityImpl.CAMPO_MARCAS_BLOQUEO,
						FondosConstants.UDOC_DESBLOQUEADA));
		}

		return qual.toString();
	}

	public String componerCondicionConservada(String conservada,
			String aliasElemento) {
		StringBuffer qual = new StringBuffer("");

		if (!StringUtils.isEmpty(conservada)) {
			// Solamente puede llegarle un valor (0 ó 1)
			if (FondosConstants.UDOC_CONSERVADA.equals(conservada)) {
				qual.append(DBUtils
						.generateIsNotNullCondition(
								getConnection(),
								getCustomizedField(ID_ELIMINACION_FIELD,
										aliasElemento)));
			} else
				qual.append(DBUtils
						.generateIsNullCondition(
								getConnection(),
								getCustomizedField(ID_ELIMINACION_FIELD,
										aliasElemento)));
		}

		return qual.toString();
	}

	public String componerCampoEspecialIdCodigoReferencia(String operador,
			String valor1) {
		StringBuffer sqlAdvanced = new StringBuffer("");
		sqlAdvanced.append("(SELECT DISTINCT ").append(ID_ELEMENTO_FIELD)
				.append(" AS IDELEMENTOCF FROM ").append(TABLE_NAME_ELEMENTO)
				.append(" WHERE ");

		if (operador.equals("=") && StringUtils.isEmpty(valor1)) {
			sqlAdvanced.append(DBUtils.generateIsNotNullCondition(
					getConnection(), CODIGO_REFERENCIA_FIELD));
			sqlAdvanced.append(DBUtils.AND);
			sqlAdvanced.append(DBUtils.generateIsNotEmptyValue(getConnection(),
					CODIGO_REFERENCIA_FIELD));
		} else {
			if (operador.equals("=") || operador.equals("<")
					|| operador.equals("<=") || operador.equals(">")
					|| operador.equals(">=")) {
				sqlAdvanced.append(CODIGO_REFERENCIA_FIELD).append(operador)
						.append("'").append(valor1).append("'");
			} else if (operador.equals("QCC")) {
				sqlAdvanced.append(CODIGO_REFERENCIA_FIELD).append(" LIKE '")
						.append(valor1).append("%'");
			} else if (operador.equals("QC")) {
				sqlAdvanced.append(CODIGO_REFERENCIA_FIELD).append(" LIKE '%")
						.append(valor1).append("%'");
			}
		}

		sqlAdvanced.append(")");
		return sqlAdvanced.toString();
	}

	public String componerCampoEspecialIdNivelDescripcion(String operador,
			String valor1) {
		StringBuffer sqlAdvanced = new StringBuffer();
		sqlAdvanced
				.append("(SELECT DISTINCT ")
				.append(ID_ELEMENTO_FIELD)
				.append(" AS IDELEMENTOCF FROM ")
				.append(TABLE_NAME_ELEMENTO)
				.append(",")
				.append(NivelCFDBEntityImpl.NIVELCF_TABLE_NAME)
				.append(" WHERE ")
				.append(DBUtils.generateJoinCondition(NIVEL_FIELD,
						NivelCFDBEntityImpl.ID_NIVEL_FIELD)).append(" AND ");

		if (operador.equals("=") && StringUtils.isEmpty(valor1)) {
			sqlAdvanced.append(DBUtils.generateIsNotNullCondition(
					getConnection(), NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD));
		} else {
			if (operador.equals("=") || operador.equals("<")
					|| operador.equals("<=") || operador.equals(">")
					|| operador.equals(">=")) {
				sqlAdvanced.append(NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD)
						.append(operador).append("'").append(valor1)
						.append("'");
			} else if (operador.equals("QCC")) {
				sqlAdvanced.append(NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD)
						.append(" LIKE '").append(valor1).append("%'");
			} else if (operador.equals(Operadores.OPERADOR_QC)) {
				sqlAdvanced.append(NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD)
						.append(" LIKE '%").append(valor1).append("%')");
			}
		}
		sqlAdvanced.append(")");
		return sqlAdvanced.toString();
	}

	public String componerCampoEspecialIdNumeroExpediente(String operador,
			String valor1) {
		StringBuffer sqlAdvanced = new StringBuffer();

		sqlAdvanced
				.append("(SELECT DISTINCT ")
				.append(ID_ELEMENTO_FIELD)
				.append(" AS IDELEMENTOCF FROM ")
				.append(TABLE_NAME_ELEMENTO)
				.append(",")
				.append(UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL)
				.append(" WHERE ")
				.append(DBUtils.generateJoinCondition(ID_ELEMENTO_FIELD,
						UnidadDocumentalDBEntityImpl.CAMPO_ID)).append(" AND ");

		if (operador.equals("=") && StringUtils.isEmpty(valor1)) {
			sqlAdvanced.append(DBUtils.generateIsNotNullCondition(
					getConnection(),
					UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE));

			sqlAdvanced.append(DBUtils.AND);
			sqlAdvanced.append(DBUtils.generateIsNotEmptyValue(getConnection(),
					UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE));
		} else {
			if (operador.equals("=") || operador.equals("<")
					|| operador.equals("<=") || operador.equals(">")
					|| operador.equals(">=")) {
				sqlAdvanced
						.append(UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE)
						.append(operador).append("'").append(valor1)
						.append("'");
			} else if (operador.equals("QCC")) {
				sqlAdvanced
						.append(UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE)
						.append(" LIKE '").append(valor1).append("%'");
			} else if (operador.equals(Operadores.OPERADOR_QC)) {
				sqlAdvanced
						.append(UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE)
						.append(" LIKE '%").append(valor1).append("%'");
			}
		}
		sqlAdvanced.append(")");
		return sqlAdvanced.toString();
	}

	public String componerCampoEspecialIdTitulo(String operador, String valor1) {
		StringBuffer sqlAdvanced = new StringBuffer("");
		sqlAdvanced.append("(SELECT DISTINCT ").append(ID_ELEMENTO_FIELD)
				.append(" AS IDELEMENTOCF FROM ").append(TABLE_NAME_ELEMENTO)
				.append(" WHERE ");

		if (operador.equals("=") && StringUtils.isEmpty(valor1)) {
			sqlAdvanced.append(DBUtils.generateIsNotNullCondition(
					getConnection(), TITULO_FIELD));
			sqlAdvanced.append(DBUtils.AND);
			sqlAdvanced.append(DBUtils.generateIsNotEmptyValue(getConnection(),
					TITULO_FIELD));
		} else {
			if (operador.equals("=") || operador.equals("<")
					|| operador.equals("<=") || operador.equals(">")
					|| operador.equals(">=")) {
				sqlAdvanced.append(TITULO_FIELD).append(operador).append("'")
						.append(valor1).append("'");
			} else if (operador.equals("QCC")) {
				sqlAdvanced.append(TITULO_FIELD).append(" LIKE '")
						.append(valor1).append("%'");
			} else if (operador.equals(Operadores.OPERADOR_QC)) {
				sqlAdvanced
						.append(DBUtils.generateContainsTokenField(
								getConnection(), TITULO_FIELD, IDXTITULO_FIELD,
								valor1));
			}
		}
		sqlAdvanced.append(")");
		return sqlAdvanced.toString();
	}

	public String componerCampoEspecialTipoReferencia(String idCampo,
			String valor1, Integer tipoReferencia) {
		StringBuffer sqlAdvanced = new StringBuffer("");

		sqlAdvanced.append("((SELECT DISTINCT ")
				.append(ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO)
				.append(" FROM ").append(ReferenciaDBEntityImpl.TABLE_NAME)
				.append(" WHERE ");

		if (StringUtils.isEmpty(valor1)) {
			sqlAdvanced
					.append(DBUtils.generateIsNotNullCondition(getConnection(),
							ReferenciaDBEntityImpl.CAMPO_IDOBJREF))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							ReferenciaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		} else {
			if (tipoReferencia != null) {
				sqlAdvanced.append(DBUtils.generateEQTokenField(
						ReferenciaDBEntityImpl.CAMPO_TIPOOBJREF,
						tipoReferencia.intValue()));
			} else {
				sqlAdvanced.append(DBUtils.generateEQTokenField(
						ReferenciaDBEntityImpl.CAMPO_TIPOOBJREF,
						DefTipos.TIPO_REFERENCIA_DESCRIPTOR));
			}
			sqlAdvanced.append(" AND ").append(
					DBUtils.generateEQTokenField(
							ReferenciaDBEntityImpl.CAMPO_IDOBJREF, valor1));

			// No incluir esta condición si estamos buscando por todos los
			// descriptores
			if (!idCampo.equals(DefTipos.CAMPO_ESPECIAL_TODOS_DESCRIPTORES)) {
				sqlAdvanced
						.append(" AND ")
						.append(DBUtils.generateEQTokenField(
								ReferenciaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));

			}
		}

		sqlAdvanced.append(")");
		sqlAdvanced.append(")");

		return sqlAdvanced.toString();
	}

	public String componerCampoEspecialTipoFecha(String idCampo,
			String operador, String valor1, String valor2) {
		StringBuffer sqlAdvanced = new StringBuffer("");
		sqlAdvanced.append("(SELECT DISTINCT ")
				.append(FechaDBEntityImpl.CAMPO_ID_ELEMENTO).append(" FROM ")
				.append(FechaDBEntityImpl.TABLE_NAME).append(DBUtils.WHERE);

		if (operador.equals("=") && StringUtils.isEmpty(valor1)) {
			sqlAdvanced
					.append(DBUtils.generateIsNotNullCondition(getConnection(),
							FechaDBEntityImpl.CAMPO_VALOR))
					.append(DBUtils.AND)
					.append(DBUtils.generateIsNotEmptyValue(getConnection(),
							FechaDBEntityImpl.CAMPO_VALOR))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							FechaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		} else {
			if (CustomDateFormat.DATE_OPERATOR_EQ.equals(operador)) {
				// OPERADOR IGUAL
				sqlAdvanced
						.append(DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(),
								FechaDBEntityImpl.CAMPO_FECHA_FINAL, ">=",
								valor1))
						.append(DBUtils.AND)
						.append(DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(),
								FechaDBEntityImpl.CAMPO_FECHA_INICIAL, "<=",
								valor2))
						.append(DBUtils.AND)
						.append(DBUtils.generateEQTokenField(
								FechaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
			} else if (CustomDateFormat.DATE_OPERATOR_LT.equals(operador)) {
				// OPERADOR MENOR
				sqlAdvanced
						.append(DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(),
								FechaDBEntityImpl.CAMPO_FECHA_INICIAL, "<=",
								valor1))
						.append(DBUtils.AND)
						.append(DBUtils.generateEQTokenField(
								FechaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
			} else if (CustomDateFormat.DATE_OPERATOR_LT_OR_EQ.equals(operador)) {
				// OPERADOR MENOR O IGUAL
				sqlAdvanced
						.append(DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(),
								FechaDBEntityImpl.CAMPO_FECHA_FINAL, "<=",
								valor1))
						.append(DBUtils.AND)
						.append(DBUtils.generateEQTokenField(
								FechaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
			} else if (CustomDateFormat.DATE_OPERATOR_GT.equals(operador)) {
				// OPERADOR MAYOR
				sqlAdvanced
						.append(DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(),
								FechaDBEntityImpl.CAMPO_FECHA_FINAL, ">=",
								valor1))
						.append(DBUtils.AND)
						.append(DBUtils.generateEQTokenField(
								FechaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
			} else if (CustomDateFormat.DATE_OPERATOR_GT_OR_EQ.equals(operador)) {
				// OPERADOR MAYOR O IGUAL
				sqlAdvanced
						.append(DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(),
								FechaDBEntityImpl.CAMPO_FECHA_INICIAL, ">=",
								valor1))
						.append(DBUtils.AND)
						.append(DBUtils.generateEQTokenField(
								FechaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
			} else if (CustomDateFormat.DATE_OPERATOR_RANGE.equals(operador)) {
				// OPERADOR DE RANGO
				sqlAdvanced
						.append(DBUtils.generateBetweenDateAnioMesDia(
								getConnection(),
								FechaDBEntityImpl.CAMPO_FECHA_INICIAL, valor1,
								valor2, DbUtil.TO_CHAR_PATTERN_AAAAMMDD))

						.append(DBUtils.AND)
						.append(DBUtils.generateBetweenDateAnioMesDia(
								getConnection(),
								FechaDBEntityImpl.CAMPO_FECHA_FINAL, valor1,
								valor2, DbUtil.TO_CHAR_PATTERN_AAAAMMDD))

						.append(DBUtils.AND)
						.append(DBUtils.generateEQTokenField(
								FechaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
			} else if (CustomDateFormat.DATE_OPERATOR_CONTAINS.equals(operador)) {
				// OPERADOR CONTAINS
				sqlAdvanced
						.append(" ((")
						.append(DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(),
								FechaDBEntityImpl.CAMPO_FECHA_INICIAL, "<=",
								valor1))
						.append(DBUtils.AND)
						.append(DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(),
								FechaDBEntityImpl.CAMPO_FECHA_FINAL, ">=",
								valor1))
						.append(") OR (")
						.append(DBUtils.generateBetweenDateAnioMesDia(
								getConnection(),
								FechaDBEntityImpl.CAMPO_FECHA_INICIAL, valor1,
								valor2, DbUtil.TO_CHAR_PATTERN_AAAAMMDD))
						.append(")) AND ")
						.append(DBUtils.generateEQTokenField(
								FechaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
			} else if (CustomDateFormat.DATE_OPERATOR_EXACT.equals(operador)) {
				// OPERADOR EXACTA
				sqlAdvanced
						.append(DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(),
								FechaDBEntityImpl.CAMPO_FECHA_INICIAL, "=",
								valor1))
						.append(DBUtils.AND)
						.append(DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(),
								FechaDBEntityImpl.CAMPO_FECHA_FINAL, "=",
								valor2))
						.append(DBUtils.AND)

						.append(DBUtils.generateEQTokenField(
								FechaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
			}
		}
		sqlAdvanced.append(")");

		return sqlAdvanced.toString();
	}

	public String componerCampoEspecialTipoNumerico(String idCampo,
			String operador, String valor1, String valor2) {
		StringBuffer sqlAdvanced = new StringBuffer("");
		sqlAdvanced.append("(SELECT DISTINCT ")
				.append(NumeroDBEntityImpl.CAMPO_ID_ELEMENTO).append(" FROM ")
				.append(NumeroDBEntityImpl.TABLE_NAME).append(" WHERE ");

		if (operador.equals("=") && StringUtils.isEmpty(valor1)) {
			sqlAdvanced
					.append(DBUtils.generateIsNotNullCondition(getConnection(),
							NumeroDBEntityImpl.CAMPO_VALOR))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							NumeroDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		} else {
			if (operador.equals("=") || operador.equals("<")
					|| operador.equals("<=") || operador.equals(">")
					|| operador.equals(">=")) {
				sqlAdvanced
						.append(NumeroDBEntityImpl.CAMPO_VALOR)
						.append(operador)
						.append(valor1)
						.append(" AND ")
						.append(DBUtils.generateEQTokenField(
								NumeroDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
			} else if (operador.equals("[..]")) {
				sqlAdvanced
						.append(NumeroDBEntityImpl.CAMPO_VALOR)
						.append(" BETWEEN ")
						.append(valor1)
						.append(" AND ")
						.append(valor2)
						.append(" AND ")
						.append(DBUtils.generateEQTokenField(
								NumeroDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
			}
		}

		sqlAdvanced.append(")");
		return sqlAdvanced.toString();
	}

	public String componerCampoEspecialTipoTextoLargo(String idCampo,
			String operador, String valor1) {
		StringBuffer sqlAdvanced = new StringBuffer("");

		sqlAdvanced.append("(SELECT DISTINCT ")
				.append(TextoLargoDBEntityImpl.CAMPO_ID_ELEMENTO)
				.append(" FROM ").append(TextoLargoDBEntityImpl.TABLE_NAME)
				.append(" WHERE ");

		if (operador.equals("=") && StringUtils.isEmpty(valor1)) {
			sqlAdvanced
					.append(DBUtils.generateIsNotNullCondition(getConnection(),
							TextoLargoDBEntityImpl.CAMPO_VALOR))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							TextoLargoDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		} else {
			ArrayList listaTexto = obtenerListaCondicionesTexto(valor1);
			if (listaTexto != null && listaTexto.size() > 0) {
				ArrayList conds = new ArrayList();
				Iterator it = listaTexto.iterator();
				while (it.hasNext()) {
					String texto = (String) it.next();

					conds.add(DBUtils.generateContainsTokenField(
							getConnection(),
							TextoLargoDBEntityImpl.CAMPO_VALOR,
							TextoLargoDBEntityImpl.CAMPO_IDXVALOR, texto));

				}

				sqlAdvanced
						// .append("(")
						.append(condsToWhere(conds, false).toString())
						.append(" AND ")
						.append(DBUtils.generateEQTokenField(
								TextoLargoDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
			}
		}
		sqlAdvanced.append(")");
		return sqlAdvanced.toString();
	}

	public String componerCampoEspecialTipoTextoCorto(String idCampo,
			String operador, String valor1) {
		StringBuffer sqlAdvanced = new StringBuffer("");

		sqlAdvanced.append("(SELECT DISTINCT ")
				.append(TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO)
				.append(" FROM ").append(TextoCortoDBEntityImpl.TABLE_NAME)
				.append(" WHERE ");

		// si llega hasta aqui, la validacion es correcta => si valor1 es vacio
		// => buscando solo nulos
		if (operador.equals("=") && StringUtils.isEmpty(valor1)) {
			sqlAdvanced
					.append(DBUtils.generateIsNotNullCondition(getConnection(),
							TextoCortoDBEntityImpl.CAMPO_VALOR))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(
							TextoCortoDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		} else if (operador.equals("=") || operador.equals("<")
				|| operador.equals("<=") || operador.equals(">")
				|| operador.equals(">=")) {
			sqlAdvanced
					.append(TextoCortoDBEntityImpl.CAMPO_VALOR)
					.append(operador)
					.append("'")
					.append(valor1)
					.append("' AND ")
					.append(DBUtils.generateEQTokenField(
							TextoCortoDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		} else if (operador.equals("QCC")) {
			sqlAdvanced
					.append(TextoCortoDBEntityImpl.CAMPO_VALOR)
					.append(" LIKE '")
					.append(valor1)
					.append("%' AND ")
					.append(DBUtils.generateEQTokenField(
							TextoCortoDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		} else if (operador.equals(Operadores.OPERADOR_QC)) {
			sqlAdvanced
					.append(DBUtils.generateContainsTokenField(getConnection(),
							TextoCortoDBEntityImpl.CAMPO_VALOR,
							TextoCortoDBEntityImpl.CAMPO_IDXVALOR, valor1))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(
							TextoCortoDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		}
		sqlAdvanced.append(")");
		return sqlAdvanced.toString();
	}

	public String componerCampoEspecialSegunTipo(int tipoCampo, String idCampo,
			String operador, String valor1, String valor2,
			Integer tipoReferencia) {
		StringBuffer sqlAdvanced = new StringBuffer("");

		switch (tipoCampo) {
		case DefTipos.TIPO_DATO_REFERENCIA: // Enlace
			sqlAdvanced.append(componerCampoEspecialTipoReferencia(idCampo,
					valor1, tipoReferencia));
			break;
		case DefTipos.TIPO_DATO_FECHA: // Fecha
			sqlAdvanced.append(componerCampoEspecialTipoFecha(idCampo,
					operador, valor1, valor2));
			break;

		case DefTipos.TIPO_DATO_NUMERICO: // Número
			sqlAdvanced.append(componerCampoEspecialTipoNumerico(idCampo,
					operador, valor1, valor2));
			break;

		case DefTipos.TIPO_DATO_TEXTO_LARGO: // Texto largo
			sqlAdvanced.append(componerCampoEspecialTipoTextoLargo(idCampo,
					operador, valor1));
			break;

		case DefTipos.TIPO_DATO_TEXTO_CORTO: // Texto corto
			sqlAdvanced.append(componerCampoEspecialTipoTextoCorto(idCampo,
					operador, valor1));
			break;
		}

		return sqlAdvanced.toString();
	}

	public String componerCondicionesAvanzadas(BusquedaElementosVO vo /*
																	 * , String
																	 * aliasElemento
																	 */) {
		StringBuffer sqlFinal = new StringBuffer();

		if (!ArrayUtils.isEmpty(vo.getCampo())) {
			StringBuffer sqlAdvanced = new StringBuffer();

			String booleano, operador, valor1, valor2;
			String idCampo;
			int tipoCampo;
			Integer tipoReferencia = null;

			for (int i = 0; i < vo.getCampo().length; i++) {
				tipoReferencia = null;
				booleano = vo.getBooleano()[i];
				idCampo = vo.getCampo()[i];
				tipoCampo = vo.getTipoCampo()[i];
				operador = vo.getOperador()[i];
				if (vo.getTiposReferencia() != null
						&& StringUtils.isNotEmpty(vo.getTiposReferencia()[i])
						&& Integer.valueOf(vo.getTiposReferencia()[i]) != null) {
					tipoReferencia = new Integer(Integer.parseInt(vo
							.getTiposReferencia()[i]));
				} else if (vo.getTipoReferencia() != null
						&& StringUtils.isNotEmpty(vo.getTipoReferencia())
						&& Integer.valueOf(vo.getTipoReferencia()) != null) {
					tipoReferencia = new Integer(Integer.parseInt(vo
							.getTipoReferencia()));
				}

				if (StringUtils.isNotBlank(booleano) && i > 0
						&& StringUtils.isNotEmpty(idCampo)) {
					if (booleano.equals("Y"))
						sqlAdvanced.append(" INTERSECT");
					else if (booleano.equals("O"))
						sqlAdvanced.append(" UNION");
					else if (booleano.equals("Y NO")) {
						sqlAdvanced
								.append(DBUtils.getMinusKey(getConnection()));
					}
				}

				// Paréntesis de inicio
				if (StringUtils.isNotBlank(vo.getAbrirpar()[i]))
					sqlAdvanced.append("(");

				if (tipoCampo > 0 && StringUtils.isNotBlank(operador)) {
					valor1 = vo.getValor1()[i];
					valor2 = vo.getValor2()[i];

					if (idCampo
							.equals(DefTipos.CAMPO_ESPECIAL_ID_CODIGO_REFERENCIA)) {
						sqlAdvanced
								.append(componerCampoEspecialIdCodigoReferencia(
										operador, valor1));
					} else if (idCampo
							.equals(DefTipos.CAMPO_ESPECIAL_ID_NIVEL_DESCRIPCION)) {
						sqlAdvanced
								.append(componerCampoEspecialIdNivelDescripcion(
										operador, valor1));
					} else if (idCampo
							.equals(DefTipos.CAMPO_ESPECIAL_ID_NUMERO_EXPEDIENTE)) {
						sqlAdvanced
								.append(componerCampoEspecialIdNumeroExpediente(
										operador, valor1));
					} else if (idCampo
							.equals(DefTipos.CAMPO_ESPECIAL_ID_TITULO)) {
						sqlAdvanced.append(componerCampoEspecialIdTitulo(
								operador, valor1));
					} else {
						String idCampoRangoInicial = ConfiguracionSistemaArchivoFactory
								.getConfiguracionSistemaArchivo()
								.getConfiguracionDescripcion()
								.getRangoInicial();

						if (StringUtils.isNotEmpty(idCampoRangoInicial)
								&& idCampo.equals(idCampoRangoInicial)) {
							String idCampoRangoInicialNormalizado = ConfiguracionSistemaArchivoFactory
									.getConfiguracionSistemaArchivo()
									.getConfiguracionDescripcion()
									.getRangoInicialNormalizado();
							if (StringUtils
									.isNotEmpty(idCampoRangoInicialNormalizado)) {
								sqlAdvanced
										.append(componerCampoEspecialTipoTextoCorto(
												idCampoRangoInicialNormalizado,
												operador,
												common.util.StringUtils
														.normalizarTexto(valor1)));
								// condiciones.add(componerCondicionTextoCorto(common.util.StringUtils.normalizarTexto(busquedaElementosVO.getRango()),
								// aliasElemento, idCampoInicial,idCampoFinal));
							}
						} else {
							String idCampoRangoFinal = ConfiguracionSistemaArchivoFactory
									.getConfiguracionSistemaArchivo()
									.getConfiguracionDescripcion()
									.getRangoFinal();

							if (StringUtils.isNotEmpty(idCampoRangoFinal)
									&& idCampo.equals(idCampoRangoFinal)) {
								String idCampoRangoFinalNormalizado = ConfiguracionSistemaArchivoFactory
										.getConfiguracionSistemaArchivo()
										.getConfiguracionDescripcion()
										.getRangoFinalNormalizado();
								if (StringUtils
										.isNotEmpty(idCampoRangoFinalNormalizado)) {
									sqlAdvanced
											.append(componerCampoEspecialTipoTextoCorto(
													idCampoRangoFinalNormalizado,
													operador,
													common.util.StringUtils
															.normalizarTexto(valor1)));
								}
							} else if (StringUtils.isNotEmpty(idCampo)) // Es un
																		// campo
																		// especial
																		// descriptor
							{
								sqlAdvanced
										.append(componerCampoEspecialSegunTipo(
												tipoCampo, idCampo, operador,
												valor1, valor2, tipoReferencia));
							}
						}
					}
				}

				// Paréntesis de fin
				if (StringUtils.isNotBlank(vo.getCerrarpar()[i]))
					sqlAdvanced.append(")");
			}
			if (sqlAdvanced != null && sqlAdvanced.length() > 0) {
				sqlFinal.append("(").append(sqlAdvanced).append(") IDS");
			} else
				sqlFinal = sqlAdvanced;
		}

		return sqlFinal.toString();
	}

	public String componerTablaCamposGenericos(String sql, String alias) {
		StringBuffer sqlFinal = new StringBuffer();
		sqlFinal.append("(").append(sql).append(") " + alias);

		return sqlFinal.toString();
	}

	public ArrayList obtenerListaCondicionesTexto(String texto) {
		// String [] arrayTextos = new String [0];
		ArrayList listaTextos = new ArrayList();

		if (StringUtils.isNotEmpty(texto)) {
			texto = texto.trim();

			int cont = 0;
			while (cont < texto.length()) {
				char c = texto.charAt(cont);
				String condicion = new String();
				// Si encontramos una comilla, recorremos el string hasta la
				// comilla de fin y vamos concatenando
				// todos los caracteres a la condición de búsqueda
				if (c == Constants.CARACTER_COMILLA_DOBLE) {
					boolean comillaFin = false;
					cont++;
					while (cont < texto.length() && !comillaFin) {
						char cEntreComillas = texto.charAt(cont);
						if (cEntreComillas == Constants.CARACTER_COMILLA_DOBLE)
							comillaFin = true;
						else
							condicion = condicion + cEntreComillas;
						cont++;
					}
					if (StringUtils.isNotEmpty(condicion))
						listaTextos.add(condicion);
				} else if (c == Constants.CARACTER_ESPACIO_BLANCO
						|| c != Constants.CARACTER_COMILLA_DOBLE) {
					boolean finPalabra = false;
					if (c != Constants.CARACTER_ESPACIO_BLANCO)
						condicion = condicion + c;
					cont++;
					while (cont < texto.length() && !finPalabra) {
						char cPalabra = texto.charAt(cont);
						if (cPalabra == Constants.CARACTER_ESPACIO_BLANCO)
							finPalabra = true;
						else
							condicion = condicion + cPalabra;
						cont++;
					}
					if (StringUtils.isNotEmpty(condicion))
						listaTextos.add(condicion);
				}
			}
		}

		return listaTextos;
	}

	public String componerCondicionCodigoReferencia(String codigoReferencia,
			String aliasElemento) {
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotBlank(codigoReferencia)) {
			ArrayList listaTexto = obtenerListaCondicionesTexto(codigoReferencia);
			if (listaTexto != null && listaTexto.size() > 0) {
				ArrayList conds = new ArrayList();
				Iterator it = listaTexto.iterator();
				while (it.hasNext()) {
					String texto = (String) it.next();
					conds.add(DBUtils.generateLikeTokenField(
							getCustomizedField(CODIGO_REFERENCIA_FIELD,
									aliasElemento), texto));
				}

				qual.append("(").append(condsToWhere(conds, false).toString())
						.append(")");
			}
		}

		return qual.toString();
	}

	public String generarTablaTextoCorto(String texto, String idCampo) {
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotBlank(texto)) {
			qual.append("SELECT DISTINCT ")
					.append(TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO
							.getQualifiedName())
					.append(" FROM " + TextoCortoDBEntityImpl.TABLE_NAME)
					.append(" WHERE ");

			ArrayList listaTexto = obtenerListaCondicionesTexto(texto);
			if (listaTexto != null && listaTexto.size() > 0) {
				ArrayList conds = new ArrayList();
				Iterator it = listaTexto.iterator();
				while (it.hasNext()) {
					String textoS = (String) it.next();
					conds.add(DBUtils.generateContainsTokenField(
							getConnection(),
							TextoCortoDBEntityImpl.CAMPO_VALOR,
							TextoCortoDBEntityImpl.CAMPO_IDXVALOR, textoS));
				}

				qual.append("(").append(condsToWhere(conds, false).toString())
						.append(")");
			}

			if (StringUtils.isNotEmpty(idCampo)
					&& !(idCampo
							.equals(RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES)))
				qual.append(" AND ")
						.append(DBUtils.generateEQTokenField(
								TextoCortoDBEntityImpl.CAMPO_ID_CAMPO, idCampo));

			// qual.append("))");
		}

		return qual.toString();
	}

	// Condiciones especiales para todos los idCampos de texto corto
	public String generarTablaTextoCorto(String texto) {
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotBlank(texto)) {
			qual.append("SELECT DISTINCT ")
					.append(TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO
							.getQualifiedName())
					.append(" FROM " + TextoCortoDBEntityImpl.TABLE_NAME)
					.append(" WHERE ");

			ArrayList listaTexto = obtenerListaCondicionesTexto(texto);
			if (listaTexto != null && listaTexto.size() > 0) {
				ArrayList conds = new ArrayList();
				Iterator it = listaTexto.iterator();
				while (it.hasNext()) {
					String textoS = (String) it.next();
					conds.add(DBUtils.generateContainsTokenField(
							getConnection(),
							TextoCortoDBEntityImpl.CAMPO_VALOR,
							TextoCortoDBEntityImpl.CAMPO_IDXVALOR, textoS));
				}

				qual.append("(").append(condsToWhere(conds, false).toString())
						.append(")");
			}
		}
		return qual.toString();
	}

	public String componerCondicionTextoCorto(String texto,
			String aliasElemento, String idCampo) {
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotBlank(texto)) {
			qual.append(" (")
					.append(getCustomizedField(ID_ELEMENTO_FIELD, aliasElemento)
							.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO
							.getQualifiedName())
					.append(" FROM " + TextoCortoDBEntityImpl.TABLE_NAME)
					.append(" WHERE ");

			ArrayList listaTexto = obtenerListaCondicionesTexto(texto);
			if (listaTexto != null && listaTexto.size() > 0) {
				ArrayList conds = new ArrayList();
				Iterator it = listaTexto.iterator();
				while (it.hasNext()) {
					String textoS = (String) it.next();
					conds.add(DBUtils.generateContainsTokenField(
							getConnection(),
							TextoCortoDBEntityImpl.CAMPO_VALOR,
							TextoCortoDBEntityImpl.CAMPO_IDXVALOR, textoS));
				}

				qual.append("(").append(condsToWhere(conds, false).toString())
						.append(")");
			}

			if (StringUtils.isNotEmpty(idCampo)
					&& !(idCampo
							.equals(RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES)))
				qual.append(" AND ")
						.append(DBUtils.generateEQTokenField(
								TextoCortoDBEntityImpl.CAMPO_ID_CAMPO, idCampo));

			qual.append("))");
		}

		return qual.toString();
	}

	public String componerCondicionTextoCorto(String texto, String idCampo) {
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotBlank(texto)) {
			qual.append(" SELECT DISTINCT ")
					.append(TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO
							.getQualifiedName())
					.append(" FROM " + TextoCortoDBEntityImpl.TABLE_NAME)
					.append(" WHERE ");

			ArrayList listaTexto = obtenerListaCondicionesTexto(texto);
			if (listaTexto != null && listaTexto.size() > 0) {
				ArrayList conds = new ArrayList();
				Iterator it = listaTexto.iterator();
				while (it.hasNext()) {
					String textoS = (String) it.next();
					conds.add(DBUtils.generateContainsTokenField(
							getConnection(),
							TextoCortoDBEntityImpl.CAMPO_VALOR,
							TextoCortoDBEntityImpl.CAMPO_IDXVALOR, textoS));
				}

				qual.append("(").append(condsToWhere(conds, false).toString())
						.append(")");
			}

			if (StringUtils.isNotEmpty(idCampo)
					&& !(idCampo
							.equals(RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES)))
				qual.append(" AND ")
						.append(DBUtils.generateEQTokenField(
								TextoCortoDBEntityImpl.CAMPO_ID_CAMPO, idCampo));

			// qual.append("))");
		}

		return qual.toString();
	}

	public static void main(String[] args) {
		String[] textos = new String[] { "Campo500", "Campo501", "Campo501",
				"Campo501", "Campo501", "Campo501", "Campo501", "Campo501",
				"Campo501", "Campo501", "Campo501", "Campo501" };
		String[] idsCampo = new String[] { "500", "501", "501", "501", "501",
				"501", "501", "501", "501", "501", "501", "501" };
		String[] operadoresTexto = new String[] { "contiene", "igual", "igual",
				"igual", "igual", "igual", "igual", "igual", "igual", "igual",
				"igual", "igual" };
		String[] operadoresNumericos = new String[] { "[..]", "[..]", "[..]",
				"[..]", "[..]", "[..]", "[..]", "[..]", "[..]", "[..]", "[..]",
				"<" };
		String[] valores = new String[] { "500", "501", "501", "501", "501",
				"501", "501", "501", "501", "501", "501", "501" };
		String[] valoresFin = new String[] { "800", "1000", "1100", "1200",
				"800", "900", "1000", "301", "312", "10", "8", "-1" };

		logger.debug("--Campos Texto Corto");
		String sql = componerCondicionGenericoTextoCorto(
				new String[] { textos[0] }, new String[] { idsCampo[0] },
				new String[] { operadoresTexto[0] });
		logger.debug(sql);
		sql = componerCondicionGenericoTextoCorto(textos, idsCampo,
				operadoresTexto);
		logger.debug(sql);

		logger.debug("--Campos Tipo Numérico");
		sql = componerCondicionGenericoCampoNumerico(
				new String[] { valores[0] }, new String[] { valoresFin[0] },
				new String[] { idsCampo[0] },
				new String[] { operadoresNumericos[0] });
		logger.debug(sql);
		sql = componerCondicionGenericoCampoNumerico(new String[] { valores[0],
				valores[1] }, new String[] { valoresFin[0], valoresFin[1] },
				new String[] { idsCampo[0], idsCampo[1] }, new String[] {
						operadoresNumericos[0], operadoresNumericos[1] });
		logger.debug(sql);
		sql = componerCondicionGenericoCampoNumerico(valores, valoresFin,
				idsCampo, operadoresNumericos);
		logger.debug(sql);
	}

	public String componerCondicionGenericoTextoLargo(String[] textos,
			String[] idsCampo) {
		StringBuffer sql = new StringBuffer();
		StringBuffer tablas = new StringBuffer();
		StringBuffer joins = new StringBuffer();
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotEmpty(textos)) {

			String aliasTabla = "A";

			TableDef tablaPrincipal = new TableDef(
					TextoLargoDBEntityImpl.TABLE_NAME, aliasTabla);
			DbColumnDef columnaIdElementoCF = new DbColumnDef(tablaPrincipal,
					TextoLargoDBEntityImpl.CAMPO_ID_ELEMENTO);

			DbColumnDef columnaValor = new DbColumnDef(tablaPrincipal,
					TextoLargoDBEntityImpl.CAMPO_VALOR);
			DbColumnDef columnaIdCampo = new DbColumnDef(tablaPrincipal,
					TextoLargoDBEntityImpl.CAMPO_ID_CAMPO);
			DbColumnDef columnaIdx = new DbColumnDef(tablaPrincipal,
					TextoLargoDBEntityImpl.CAMPO_IDXVALOR);

			sql.append(DBUtils.SELECT).append(DBUtils.DISTINCT)
					.append(columnaIdElementoCF.getQualifiedName())
					.append(DBUtils.FROM);

			tablas.append(tablaPrincipal.getDeclaration());

			String texto = textos[0];
			String idCampo = idsCampo[0];

			qual.append(
					DBUtils.generateContainsTokenField(getConnection(),
							columnaValor, columnaIdx, texto))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(columnaIdCampo,
							idCampo));

			for (int i = 1; i < textos.length; i++) {
				texto = textos[i];
				idCampo = idsCampo[i];

				String alias = aliasTabla + String.valueOf(i);
				TableDef tabla2 = new TableDef(
						TextoLargoDBEntityImpl.TABLE_NAME, alias);
				DbColumnDef columnaIdElementoCF2 = new DbColumnDef(tabla2,
						TextoLargoDBEntityImpl.CAMPO_ID_ELEMENTO);

				columnaValor = new DbColumnDef(tabla2,
						TextoLargoDBEntityImpl.CAMPO_VALOR);
				columnaIdCampo = new DbColumnDef(tabla2,
						TextoLargoDBEntityImpl.CAMPO_ID_CAMPO);

				tablas.append(Constants.COMMA);
				tablas.append(tabla2.getDeclaration());

				if (!StringUtils.isEmpty(joins.toString())) {
					joins.append(DBUtils.AND);
				}

				joins.append(DBUtils.generateJoinCondition(columnaIdElementoCF,
						columnaIdElementoCF2));

				qual.append(DBUtils.AND)
						.append(DBUtils.generateContainsTokenField(
								getConnection(), columnaValor, columnaIdx,
								texto))
						.append(DBUtils.AND)
						.append(DBUtils.generateEQTokenField(columnaIdCampo,
								idCampo));

			}

			sql.append(tablas).append(DBUtils.WHERE).append(joins);

			if (StringUtils.isNotEmpty(joins.toString())) {
				sql.append(DBUtils.AND);
			}

			sql.append(qual);
		}

		return sql.toString();

	}

	public static String componerCondicionGenericoTextoCorto(String[] textos,
			String[] idsCampo, String[] operadores) {
		StringBuffer sql = new StringBuffer();
		StringBuffer tablas = new StringBuffer();
		StringBuffer joins = new StringBuffer();
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotEmpty(textos)) {
			String aliasTabla = "A";

			TableDef tablaPrincipal = new TableDef(
					TextoCortoDBEntityImpl.TABLE_NAME, aliasTabla);
			DbColumnDef columnaIdElementoCF = new DbColumnDef(tablaPrincipal,
					TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO);

			DbColumnDef columnaValor = new DbColumnDef(tablaPrincipal,
					TextoCortoDBEntityImpl.CAMPO_VALOR);
			DbColumnDef columnaIdCampo = new DbColumnDef(tablaPrincipal,
					TextoCortoDBEntityImpl.CAMPO_ID_CAMPO);

			sql.append(DBUtils.SELECT).append(DBUtils.DISTINCT)
					.append(columnaIdElementoCF.getQualifiedName())
					.append(DBUtils.FROM);

			tablas.append(tablaPrincipal.getDeclaration());

			String texto = textos[0];
			String idCampo = idsCampo[0];
			String operador = operadores[0];

			qual.append(
					DBUtils.generateLikeFieldQualified(columnaValor, texto,
							tablaPrincipal.getAlias(), operador))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(columnaIdCampo,
							idCampo));

			for (int i = 1; i < textos.length; i++) {
				texto = textos[i];
				idCampo = idsCampo[i];
				operador = operadores[i];

				String alias = aliasTabla + String.valueOf(i);
				TableDef tabla2 = new TableDef(
						TextoCortoDBEntityImpl.TABLE_NAME, alias);
				DbColumnDef columnaIdElementoCF2 = new DbColumnDef(tabla2,
						TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO);

				columnaValor = new DbColumnDef(tabla2,
						TextoCortoDBEntityImpl.CAMPO_VALOR);
				columnaIdCampo = new DbColumnDef(tabla2,
						TextoCortoDBEntityImpl.CAMPO_ID_CAMPO);

				tablas.append(Constants.COMMA);
				tablas.append(tabla2.getDeclaration());

				if (!StringUtils.isEmpty(joins.toString())) {
					joins.append(DBUtils.AND);
				}

				joins.append(DBUtils.generateJoinCondition(columnaIdElementoCF,
						columnaIdElementoCF2));

				qual.append(DBUtils.AND)
						.append(DBUtils.generateLikeFieldQualified(
								columnaValor, texto, tabla2.getAlias(),
								operador))
						.append(DBUtils.AND)
						.append(DBUtils.generateEQTokenField(columnaIdCampo,
								idCampo));

			}

			sql.append(tablas).append(DBUtils.WHERE).append(joins);

			if (StringUtils.isNotEmpty(joins.toString())) {
				sql.append(DBUtils.AND);
			}

			sql.append(qual);
		}

		return sql.toString();
	}

	public static String componerCondicionGenericoCampoNumerico(
			String[] valores, String[] valoresFin, String[] idsCampo,
			String[] operadores) {
		StringBuffer sql = new StringBuffer();
		StringBuffer tablas = new StringBuffer();
		StringBuffer joins = new StringBuffer();
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotEmpty(valores)) {
			String aliasTabla = "A";

			TableDef tablaPrincipal = new TableDef(
					NumeroDBEntityImpl.TABLE_NAME, aliasTabla);
			DbColumnDef columnaIdElementoCF = new DbColumnDef(tablaPrincipal,
					NumeroDBEntityImpl.CAMPO_ID_ELEMENTO);

			DbColumnDef columnaValor = new DbColumnDef(tablaPrincipal,
					NumeroDBEntityImpl.CAMPO_VALOR);
			DbColumnDef columnaIdCampo = new DbColumnDef(tablaPrincipal,
					NumeroDBEntityImpl.CAMPO_ID_CAMPO);

			sql.append(DBUtils.SELECT).append(DBUtils.DISTINCT)
					.append(columnaIdElementoCF.getQualifiedName())
					.append(DBUtils.FROM);

			tablas.append(tablaPrincipal.getDeclaration());

			int valor = NumberUtils.toInt(valores[0]);
			String idCampo = idsCampo[0];
			String operador = operadores[0];

			int valorFin = -1;
			if (valoresFin[0] != null) {
				valorFin = NumberUtils.toInt(valoresFin[0]);
			}
			int operator = ConstraintType.getValue(operador);
			if (operator == -1)
				operator = ConstraintType.EQUAL;

			String strValor = "";
			if (operador.equals(CustomDateFormat.DATE_OPERATOR_RANGE)) {
				strValor = "("
						+ DBUtils.generateBetweenNumerico(columnaValor, valor,
								valorFin) + ")";
			} else {
				strValor = DBUtils.generateTokenFieldQualified(columnaValor,
						valor, tablaPrincipal.getAlias(), operator);
			}

			qual.append(strValor)
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(columnaIdCampo,
							idCampo));

			for (int i = 1; i < valores.length; i++) {
				valor = NumberUtils.toInt(valores[i]);
				idCampo = idsCampo[i];
				operador = operadores[i];

				String alias = aliasTabla + String.valueOf(i);
				TableDef tabla2 = new TableDef(NumeroDBEntityImpl.TABLE_NAME,
						alias);
				DbColumnDef columnaIdElementoCF2 = new DbColumnDef(tabla2,
						NumeroDBEntityImpl.CAMPO_ID_ELEMENTO);

				columnaValor = new DbColumnDef(tabla2,
						NumeroDBEntityImpl.CAMPO_VALOR);
				columnaIdCampo = new DbColumnDef(tabla2,
						NumeroDBEntityImpl.CAMPO_ID_CAMPO);

				tablas.append(Constants.COMMA);
				tablas.append(tabla2.getDeclaration());

				if (!StringUtils.isEmpty(joins.toString())) {
					joins.append(DBUtils.AND);
				}

				joins.append(DBUtils.generateJoinCondition(columnaIdElementoCF,
						columnaIdElementoCF2));

				valorFin = -1;
				if (valoresFin[0] != null) {
					valorFin = NumberUtils.toInt(valoresFin[0]);
				}
				operator = ConstraintType.getValue(operador);
				if (operator == -1)
					operator = ConstraintType.EQUAL;

				strValor = "";
				if (operador.equals(CustomDateFormat.DATE_OPERATOR_RANGE)) {
					strValor = "("
							+ DBUtils.generateBetweenNumerico(columnaValor,
									valor, valorFin) + ")";
				} else {
					strValor = DBUtils.generateTokenFieldQualified(
							columnaValor, valor, tabla2.getAlias(), operator);
				}

				qual.append(DBUtils.AND)
						.append(strValor)
						.append(DBUtils.AND)
						.append(DBUtils.generateEQTokenField(columnaIdCampo,
								idCampo));

			}

			sql.append(tablas).append(DBUtils.WHERE).append(joins);

			if (StringUtils.isNotEmpty(joins.toString())) {
				sql.append(DBUtils.AND);
			}
			sql.append(qual);
		}

		return sql.toString();
	}

	public String componerCondicionGenericoFecha(String[] idsFecha,
			Date[] fechasIni, Date[] fechasFin, String[] calificadores) {

		StringBuffer sql = new StringBuffer();
		StringBuffer tablas = new StringBuffer();
		StringBuffer joins = new StringBuffer();
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotEmpty(idsFecha)) {
			String aliasTabla = "A";

			TableDef tablaPrincipal = new TableDef(
					FechaDBEntityImpl.TABLE_NAME, aliasTabla);
			DbColumnDef columnaIdElementoCF = new DbColumnDef(tablaPrincipal,
					FechaDBEntityImpl.CAMPO_ID_ELEMENTO);

			DbColumnDef columnaFechaIni = new DbColumnDef(tablaPrincipal,
					FechaDBEntityImpl.CAMPO_FECHA_INICIAL);
			DbColumnDef columnaFechaFinal = new DbColumnDef(tablaPrincipal,
					FechaDBEntityImpl.CAMPO_FECHA_FINAL);
			DbColumnDef columnaIdCampo = new DbColumnDef(tablaPrincipal,
					FechaDBEntityImpl.CAMPO_ID_CAMPO);
			DbColumnDef columnaCalificador = new DbColumnDef(tablaPrincipal,
					FechaDBEntityImpl.CAMPO_CALIFICADOR);

			sql.append(DBUtils.SELECT).append(DBUtils.DISTINCT)
					.append(columnaIdElementoCF.getQualifiedName())
					.append(DBUtils.FROM);

			tablas.append(tablaPrincipal.getDeclaration());

			Date fechaIni = fechasIni[0];
			Date fechaFin = fechasFin[0];
			String calificador = calificadores[0];
			String idFecha = idsFecha[0];

			qual.append(DBUtils.generateEQTokenField(columnaIdCampo, idFecha));

			// Añadir el Campo de Calificadores
			if (StringUtils.isNotEmpty(calificador)) {
				qual.append(DBUtils.AND).append(
						DBUtils.generateEQTokenField(columnaCalificador,
								calificador));
			}

			if (fechaIni != null) {
				qual.append(DBUtils.AND)
						.append("(")
						.append(DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(), columnaFechaFinal, ">=",
								fechaIni)).append(")");
			}

			if (fechaFin != null) {
				qual.append(DBUtils.AND)
						.append("(")
						.append(DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(), columnaFechaIni, "<=",
								fechaFin)).append(")");
			}

			for (int i = 1; i < idsFecha.length; i++) {
				fechaIni = fechasIni[i];
				fechaFin = fechasFin[i];
				calificador = calificadores[i];
				idFecha = idsFecha[i];

				String alias = aliasTabla + String.valueOf(i);
				TableDef tabla2 = new TableDef(FechaDBEntityImpl.TABLE_NAME,
						alias);
				DbColumnDef columnaIdElementoCF2 = new DbColumnDef(tabla2,
						FechaDBEntityImpl.CAMPO_ID_ELEMENTO);

				columnaIdCampo = new DbColumnDef(tabla2,
						FechaDBEntityImpl.CAMPO_ID_CAMPO);

				columnaFechaIni = new DbColumnDef(tabla2,
						FechaDBEntityImpl.CAMPO_FECHA_INICIAL);
				columnaFechaFinal = new DbColumnDef(tabla2,
						FechaDBEntityImpl.CAMPO_FECHA_FINAL);
				columnaIdCampo = new DbColumnDef(tabla2,
						FechaDBEntityImpl.CAMPO_ID_CAMPO);
				columnaCalificador = new DbColumnDef(tabla2,
						FechaDBEntityImpl.CAMPO_CALIFICADOR);

				tablas.append(Constants.COMMA);
				tablas.append(tabla2.getDeclaration());

				if (!StringUtils.isEmpty(joins.toString())) {
					joins.append(DBUtils.AND);
				}

				joins.append(DBUtils.generateJoinCondition(columnaIdElementoCF,
						columnaIdElementoCF2));

				qual.append(DBUtils.AND);

				qual.append(DBUtils.generateEQTokenField(columnaIdCampo,
						idFecha));

				// Añadir el Campo de Calificadores
				if (StringUtils.isNotEmpty(calificador)) {
					qual.append(DBUtils.AND).append(
							DBUtils.generateEQTokenField(columnaCalificador,
									calificador));
				}

				if (fechaIni != null) {
					qual.append(DBUtils.AND)
							.append("(")
							.append(DBUtils.generateTokenFieldDateAnioMesDia(
									getConnection(), columnaFechaFinal, ">=",
									fechaIni)).append(")");
				}

				if (fechaFin != null) {
					qual.append(DBUtils.AND)
							.append("(")
							.append(DBUtils.generateTokenFieldDateAnioMesDia(
									getConnection(), columnaFechaIni, "<=",
									fechaFin)).append(")");
				}

			}

			sql.append(tablas).append(DBUtils.WHERE).append(joins);

			if (StringUtils.isNotEmpty(joins.toString())) {
				sql.append(DBUtils.AND);
			}
			sql.append(qual);
		}

		return sql.toString();

	}

	public String componerCondicionTextoLargo(String texto, String idCampo) {
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotBlank(texto)) {
			qual.append(" SELECT DISTINCT ")
					.append(TextoLargoDBEntityImpl.CAMPO_ID_ELEMENTO
							.getQualifiedName())
					.append(" FROM " + TextoLargoDBEntityImpl.TABLE_NAME)
					.append(" WHERE ");

			ArrayList listaTexto = obtenerListaCondicionesTexto(texto);
			if (listaTexto != null && listaTexto.size() > 0) {
				ArrayList conds = new ArrayList();
				Iterator it = listaTexto.iterator();
				while (it.hasNext()) {
					String textoS = (String) it.next();
					conds.add(DBUtils.generateContainsTokenField(
							getConnection(),
							TextoLargoDBEntityImpl.CAMPO_VALOR,
							TextoLargoDBEntityImpl.CAMPO_IDXVALOR, textoS));
				}

				qual.append("(").append(condsToWhere(conds, false).toString())
						.append(")");
			}

			if (StringUtils.isNotEmpty(idCampo)
					&& !(idCampo
							.equals(RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES)))
				qual.append(" AND ")
						.append(DBUtils.generateEQTokenField(
								TextoLargoDBEntityImpl.CAMPO_ID_CAMPO, idCampo));

			// qual.append("))");
		}

		return qual.toString();
	}

	// Condiciones especiales para el campo Rango almacenado en la tabla de
	// textos cortos
	public String componerCondicionTextoCorto(String texto,
			String aliasElemento, String idCampoInicial, String idCampoFinal) {
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotBlank(texto)) {
			String aliasTextoCorto1 = "TC1", aliasTextoCorto2 = "TC2";
			TableDef tablaTextoCorto1 = new TableDef(
					TextoCortoDBEntityImpl.TABLE_NAME, aliasTextoCorto1);
			TableDef tablaTextoCorto2 = new TableDef(
					TextoCortoDBEntityImpl.TABLE_NAME, aliasTextoCorto2);

			JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
					new DbColumnDef(tablaTextoCorto1, getCustomizedField(
							TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO,
							aliasTextoCorto1)), new DbColumnDef(
							tablaTextoCorto2, getCustomizedField(
									TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO,
									aliasTextoCorto2))) };

			qual.append(" (")
					.append(getCustomizedField(ID_ELEMENTO_FIELD, aliasElemento)
							.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(getCustomizedField(
							TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO, "TC1")
							.getQualifiedName())
					.append(" FROM ")
					// + new
					// TableDef("TC1",TextoCortoDBEntityImpl.TABLE_NAME).getDeclaration())
					.append(DBUtils.generateInnerJoinCondition(
							tablaTextoCorto1, joins))
					.append(" AND ")
					.append(DBUtils.generateJoinCondition(
							getCustomizedField(
									TextoCortoDBEntityImpl.CAMPO_ORDEN,
									aliasTextoCorto1),
							getCustomizedField(
									TextoCortoDBEntityImpl.CAMPO_ORDEN,
									aliasTextoCorto2)))
					.append(" AND ")
					.append(DBUtils.generateLTEQTokenField(
							getCustomizedField(
									TextoCortoDBEntityImpl.CAMPO_VALOR,
									aliasTextoCorto1), texto))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(
							getCustomizedField(
									TextoCortoDBEntityImpl.CAMPO_ID_CAMPO,
									aliasTextoCorto1), idCampoInicial))
					.append(" AND ")
					.append(DBUtils.generateGTEQTokenField(
							getCustomizedField(
									TextoCortoDBEntityImpl.CAMPO_VALOR,
									aliasTextoCorto2), texto))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(
							getCustomizedField(
									TextoCortoDBEntityImpl.CAMPO_ID_CAMPO,
									aliasTextoCorto2), idCampoFinal));

			qual.append("))");
		}

		return qual.toString();
	}

	// Condición especial para las búsquedas sin filtro por idCampo
	public String generarTablaTextoLargo(String texto) {
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotBlank(texto)) {
			qual.append(" SELECT DISTINCT ")
					.append(TextoLargoDBEntityImpl.CAMPO_ID_ELEMENTO
							.getQualifiedName())
					.append(" FROM " + TextoLargoDBEntityImpl.TABLE_NAME)
					.append(" WHERE ");

			ArrayList listaTexto = obtenerListaCondicionesTexto(texto);
			if (listaTexto != null && listaTexto.size() > 0) {
				ArrayList conds = new ArrayList();
				Iterator it = listaTexto.iterator();
				while (it.hasNext()) {
					String textoS = (String) it.next();
					conds.add(DBUtils.generateContainsTokenField(
							getConnection(),
							TextoLargoDBEntityImpl.CAMPO_VALOR,
							TextoLargoDBEntityImpl.CAMPO_IDXVALOR, textoS));
				}

				qual.append("(").append(condsToWhere(conds, false).toString())
						.append(")");
			}
		}
		return qual.toString();
	}

	public String componerCondicionTextoLargo(String texto,
			String aliasElemento, String idCampo) {
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotBlank(texto)) {
			qual.append(" (")
					.append(getCustomizedField(ID_ELEMENTO_FIELD, aliasElemento)
							.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(TextoLargoDBEntityImpl.CAMPO_ID_ELEMENTO
							.getQualifiedName())
					.append(" FROM " + TextoLargoDBEntityImpl.TABLE_NAME)
					.append(" WHERE ");

			ArrayList listaTexto = obtenerListaCondicionesTexto(texto);
			if (listaTexto != null && listaTexto.size() > 0) {
				ArrayList conds = new ArrayList();
				Iterator it = listaTexto.iterator();
				while (it.hasNext()) {
					String textoS = (String) it.next();
					conds.add(DBUtils.generateContainsTokenField(
							getConnection(),
							TextoLargoDBEntityImpl.CAMPO_VALOR,
							TextoLargoDBEntityImpl.CAMPO_IDXVALOR, textoS));
				}

				qual.append("(").append(condsToWhere(conds, false).toString())
						.append(")");
			}

			if (StringUtils.isNotEmpty(idCampo)
					&& !(idCampo
							.equals(RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES)))
				qual.append(" AND ")
						.append(DBUtils.generateEQTokenField(
								TextoCortoDBEntityImpl.CAMPO_ID_CAMPO, idCampo));

			qual.append("))");

		}

		return qual.toString();
	}

	public String componerCondicionTexto(String texto, String aliasElemento) {
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotBlank(texto)) {
			qual.append(" (")
					.append(getCustomizedField(ID_ELEMENTO_FIELD, aliasElemento)
							.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO
							.getQualifiedName())
					.append(" FROM " + TextoCortoDBEntityImpl.TABLE_NAME)
					.append(" WHERE ");

			// Añadimos condiciones de texto corto
			ArrayList listaTextoCorto = obtenerListaCondicionesTexto(texto);
			if (listaTextoCorto != null && listaTextoCorto.size() > 0) {
				ArrayList conds = new ArrayList();
				Iterator it = listaTextoCorto.iterator();
				while (it.hasNext()) {
					String textoS = (String) it.next();
					conds.add(DBUtils.generateContainsTokenField(
							getConnection(),
							TextoCortoDBEntityImpl.CAMPO_VALOR,
							TextoCortoDBEntityImpl.CAMPO_IDXVALOR, textoS));
				}

				qual.append("(").append(condsToWhere(conds, false).toString())
						.append(")");
			}

			// Añadimos la condición de OR
			qual.append(") OR ")
					.append(getCustomizedField(ID_ELEMENTO_FIELD, aliasElemento)
							.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(TextoLargoDBEntityImpl.CAMPO_ID_ELEMENTO
							.getQualifiedName())
					.append(" FROM " + TextoLargoDBEntityImpl.TABLE_NAME)
					.append(" WHERE ");

			// Añadimos condiciones de texto largo
			ArrayList listaTextoLargo = obtenerListaCondicionesTexto(texto);
			if (listaTextoLargo != null && listaTextoLargo.size() > 0) {
				ArrayList conds = new ArrayList();
				Iterator it = listaTextoCorto.iterator();
				while (it.hasNext()) {
					String textoS = (String) it.next();
					conds.add(DBUtils.generateContainsTokenField(
							getConnection(),
							TextoLargoDBEntityImpl.CAMPO_VALOR,
							TextoLargoDBEntityImpl.CAMPO_IDXVALOR, textoS));
				}

				qual.append("(").append(condsToWhere(conds, false).toString())
						.append(")");
			}
			qual.append("))");
		}

		return qual.toString();
	}

	public String componerCondicionCodigoRelacion(String codigoRelacion,
			String aliasElemento, Map restricciones) {
		StringBuffer qual = new StringBuffer();
		if (StringUtils.isNotEmpty(codigoRelacion)) {
			JoinDefinition[] joinsDocenuiDocre = new JoinDefinition[] { new JoinDefinition(
					new DbColumnDef(new TableDef(
							UDocEnUiDepositoDbEntityImpl.TABLE_NAME),
							UDocEnUiDepositoDbEntityImpl.IDUDOCRE_FIELD),
					new DbColumnDef(new TableDef(
							UDocRelacionDBEntityImpl.TABLE_NAME),
							UDocRelacionDBEntityImpl.ID_FIELD)) };

			JoinDefinition[] joinsRelElem = new JoinDefinition[] { new JoinDefinition(
					new DbColumnDef(new TableDef(TABLE_NAME_ELEMENTO),
							ID_ELEMENTO_FIELD),
					new DbColumnDef(
							new TableDef(
									RelacionEntregaDBEntityBaseImpl.TABLE_NAME),
							RelacionEntregaDBEntityBaseImpl.CAMPO_IDSERIEDESTINO)) };

			JoinDefinition[] joinsDocenuiUInstalacion = new JoinDefinition[] { new JoinDefinition(
					new DbColumnDef(new TableDef(
							UDocEnUiDepositoDbEntityImpl.TABLE_NAME),
							UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD),
					new DbColumnDef(new TableDef(
							UInstalacionDepositoDBEntity.TABLE_NAME),
							UInstalacionDepositoDBEntity.ID_FIELD)) };

			JoinDefinition[] joinsUIREAUID = new JoinDefinition[] { new JoinDefinition(
					new DbColumnDef(new TableDef(
							UInstalacionDepositoDBEntity.TABLE_NAME),
							UInstalacionDepositoDBEntity.ID_FIELD),
					new DbColumnDef(
							new TableDef(
									UnidadInstalacionReeaDBEntityImpl.TABLE_NAME),
							UnidadInstalacionReeaDBEntityImpl.ID_UIDEPOSITO_FIELD)) };

			qual.append(
					getCustomizedField(ID_ELEMENTO_FIELD, aliasElemento)
							.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_COLUMN_NAME)
					.append(" FROM (")
					.append(" SELECT DISTINCT ")
					.append(UdocEnUIDBEntityImpl.UDOC_COLUMN_NAME)
					.append(", ")
					.append(UDocRelacionDBEntityImpl.ID_RELACION_COLUMN_NAME)
					.append(" FROM ")
					.append(DBUtils.generateInnerJoinCondition(new TableDef(
							UDocEnUiDepositoDbEntityImpl.TABLE_NAME),
							joinsDocenuiUInstalacion))
					.append(DBUtils
							.generateInnerJoinChainedCondition(joinsDocenuiDocre))
					/**
					 * Añadido para tener en cuenta las relaciones de entrega
					 * entre archivos
					 **/
					.append(" WHERE ")
					.append(DBUtils.generateEQTokenField(
							UInstalacionDepositoDBEntity.MARCAS_BLOQUEO_FIELD,
							0))
					.append(" UNION ")
					.append(" SELECT DISTINCT ")
					.append(UdocEnUIDBEntityImpl.UDOC_COLUMN_NAME)
					.append(", ")
					.append(UDocRelacionDBEntityImpl.ID_RELACION_COLUMN_NAME)
					.append(" FROM ")
					.append(DBUtils.generateInnerJoinCondition(new TableDef(
							UDocEnUiDepositoDbEntityImpl.TABLE_NAME),
							joinsDocenuiUInstalacion))
					.append(DBUtils
							.generateInnerJoinChainedCondition(joinsUIREAUID))
					.append(" WHERE ")
					.append(DBUtils.generateEQTokenField(
							UInstalacionDepositoDBEntity.MARCAS_BLOQUEO_FIELD,
							0))
					.append(") UDOCSREL")
					/** **/
					.append(" , ")
					.append(DBUtils.generateInnerJoinCondition(new TableDef(
							TABLE_NAME_ELEMENTO), joinsRelElem));

			if (ConfigConstants.getInstance()
					.getPermitirTransferenciasEntreArchivos()) {
				qual.append(", ").append(ArchivoDbEntityImpl.TABLE_NAME);
			}

			qual.append(" WHERE ")
					// .append(DBUtils.generateJoinCondition(UDocRelacionDBEntityImpl.TABLE_NAME,
					// UDocRelacionDBEntityImpl.ID_RELACION_FIELD,
					// RelacionEntregaDBEntityBaseImpl.TABLE_NAME,
					// RelacionEntregaDBEntityBaseImpl.CAMPO_ID))
					.append(DBUtils.generateJoinCondition("UDOCSREL",
							UDocRelacionDBEntityImpl.ID_RELACION_FIELD,
							RelacionEntregaDBEntityBaseImpl.TABLE_NAME,
							RelacionEntregaDBEntityBaseImpl.CAMPO_ID))
					.append(" AND ")
					.append(CodigoTransferenciaUtils
							.textSqlCodigoTransferenciaPrevision(
									RelacionEntregaDBEntityBaseImpl.CAMPO_ANO
											.getQualifiedName(),
									RelacionEntregaDBEntityBaseImpl.CAMPO_ORDEN
											.getQualifiedName(),
									getConnection())).append(" LIKE '%")
					.append(codigoRelacion).append("%'");

			if (ConfigConstants.getInstance()
					.getPermitirTransferenciasEntreArchivos()) {
				// Añadir la join con AGARCHIVO
				qual.append(" AND ")
						.append(DBUtils
								.generateJoinCondition(
										RelacionEntregaDBEntityBaseImpl.CAMPO_IDARCHIVORECEPTOR,
										ArchivoDbEntityImpl.ID_FIELD));
			}

			if (restricciones != null
					&& restricciones
							.containsKey(RestriccionesCamposBusquedas.RESTRICCION_CAMPO_BUSQUEDA_ESTADO_UDOC_VALIDADA)) {
				qual.append(" AND ").append(
						DBUtils.generateEQTokenField(
								RelacionEntregaDBEntityBaseImpl.CAMPO_ESTADO,
								EstadoREntrega.VALIDADA.getIdentificador()));
			}
			qual.append(")");
		}
		return qual.toString();
	}

	public String componerCondicionIdArchivo(String idArchivo,
			String aliasElemento) {
		StringBuffer qual = new StringBuffer();

		qual.append(DBUtils.generateEQTokenField(
				getCustomizedField(ARCHIVO_FIELD, aliasElemento), idArchivo));

		return qual.toString();
	}

	public String componerCondicionSignatura(String signatura,
			String calificadorSignatura, String idArchivo,
			String aliasElemento, Map restricciones) {
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotEmpty(signatura)) {
			qual.append(
					getCustomizedField(ID_ELEMENTO_FIELD, aliasElemento)
							.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_COLUMN_NAME)
					.append(" FROM ");

			qual.append(UDocEnUiDepositoDbEntityImpl.TABLE_NAME);

			qual.append(" WHERE ").append(
					DBUtils.generateLikeFieldQualified(
							UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD,
							signatura, UDocEnUiDepositoDbEntityImpl.TABLE_NAME,
							calificadorSignatura));

			qual.append(")");
		}
		return qual.toString();
	}

	public String[] getListasDescriptorasRestriccionCampo(
			ConfiguracionBusquedas cbf, RestriccionCampoBusqueda rcb) {
		String[] listasDescriptoras = new String[0];
		ArrayList ldArray = new ArrayList();

		List ldList = rcb.getListasDescriptoras();

		if (ldList != null) {
			Iterator it = ldList.iterator();
			while (it.hasNext()) {
				String ldKey = (String) it.next();
				ListaDescriptoraConfigBusqueda ld = cbf
						.getListaDescriptora(ldKey);
				if (ld != null)
					ldArray.add(ld.getValor());
			}
			listasDescriptoras = listToStringArray(ldArray);
		}
		return listasDescriptoras;
	}

	public String componerCondicionesFecha(Date fechaIni, Date fechaFin,
			String calificador, String tablaFecha, String tablaElemento,
			String idCampo) {
		StringBuffer qual = new StringBuffer();
		StringBuffer buffFechaConds = new StringBuffer();
		StringBuffer buffCalifConds = new StringBuffer();
		StringBuffer buffConds = new StringBuffer();
		ArrayList fechaConds = new ArrayList();
		ArrayList califConds = new ArrayList();
		ArrayList allConds = new ArrayList();

		// Condiciones de Fechas extremas
		if ((fechaIni != null) || (fechaFin != null)) {
			buffConds
					.append(getCustomizedField(ID_ELEMENTO_FIELD, tablaElemento)
							.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(FechaDBEntityImpl.CAMPO_ID_ELEMENTO
							.getQualifiedName())
					.append(" FROM " + FechaDBEntityImpl.TABLE_NAME + " WHERE ");

			if (fechaIni != null) {
				buffFechaConds.append("(")

						// Modificado para ignorar horas minutos y segundos
						.append(DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(),
								FechaDBEntityImpl.CAMPO_FECHA_FINAL, ">=",
								fechaIni)).append(")");

				fechaConds.add(buffFechaConds.toString());
			}

			if (fechaFin != null) {
				buffFechaConds = new StringBuffer();

				buffFechaConds.append("(")

						// Modificado para ignorar horas minutos y segundos
						.append(DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(),
								FechaDBEntityImpl.CAMPO_FECHA_FINAL, "<=",
								fechaFin)).append(")");

				fechaConds.add(buffFechaConds.toString());
			}

			buffConds.append(condsToWhere(fechaConds, false));

			// Añadimos a la lista de condiciones las condiciones de fecha
			allConds.add(buffConds.toString());
		}

		// Condiciones de calificador de fechas
		if (StringUtils.isNotEmpty(calificador)) {
			buffCalifConds
					.append(getCustomizedField(ID_ELEMENTO_FIELD, tablaElemento)
							.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(FechaDBEntityImpl.CAMPO_ID_ELEMENTO
							.getQualifiedName())
					.append(" FROM " + FechaDBEntityImpl.TABLE_NAME + " WHERE ")
					.append(DBUtils.generateEQTokenField(
							FechaDBEntityImpl.CAMPO_CALIFICADOR, calificador));
			if (fechaIni != null || fechaFin != null)
				buffCalifConds.append(")");

			califConds.add(buffCalifConds.toString());
		}

		// Filtro de tipo de fecha
		if (StringUtils.isNotEmpty(idCampo)
				&& !(idCampo
						.equals(RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES))) {
			buffCalifConds = new StringBuffer();

			// Mejorar este IN si se puede
			buffCalifConds
					.append(FechaDBEntityImpl.CAMPO_ID_CAMPO.getQualifiedName())
					.append(" = '").append(idCampo).append("'");

			califConds.add(buffCalifConds.toString());
		}

		// Añadimos a todas las condiciones, las condiciones de calificador y
		// tipo de fecha
		allConds.add(condsToWhere(califConds, false).toString());

		// Generamos el buffer final con los AND de todas las condiciones
		qual.append(condsToWhere(allConds, false)).append(")");

		return qual.toString();
	}

	public String componerCondicionesNumero(String numero, String numeroComp,
			String tipoMedida, String unidadMedida, String aliasElemento) {
		StringBuffer qual = new StringBuffer();
		ArrayList conds = new ArrayList();

		if (StringUtils.isNotEmpty(numero)
				|| StringUtils.isNotEmpty(tipoMedida)
				|| StringUtils.isNotEmpty(unidadMedida)) {

			qual.append(
					getCustomizedField(ID_ELEMENTO_FIELD, aliasElemento)
							.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(NumeroDBEntityImpl.CAMPO_ID_ELEMENTO
							.getQualifiedName())
					.append(" FROM " + NumeroDBEntityImpl.TABLE_NAME
							+ " WHERE ");

			// Valor del número
			if (StringUtils.isNotBlank(numero)) {
				conds.add(new StringBuffer()
						.append(NumeroDBEntityImpl.CAMPO_VALOR
								.getQualifiedName()).append(numeroComp)
						.append(numero).toString());
			}

			// Tipo de medida
			if (StringUtils.isNotBlank(tipoMedida)) {
				conds.add(DBUtils.generateEQTokenField(
						NumeroDBEntityImpl.CAMPO_TIPOMEDIDA, tipoMedida));
			}

			// Unidad de medida
			if (StringUtils.isNotBlank(unidadMedida)) {
				conds.add(new StringBuffer()
						.append(" UPPER(")
						.append(NumeroDBEntityImpl.CAMPO_UNIDADMEDIDA
								.getQualifiedName()).append(") LIKE '%")
						.append(unidadMedida.toUpperCase()).append("%'")
						.toString());
			}

			qual.append(condsToWhere(conds, false)).append(")");
		}

		return qual.toString();
	}

	public String componerCondicionesDescriptores(String[] descriptores,
			String aliasElemento, String idCampo) {
		StringBuffer qual = new StringBuffer();

		String[] idsCampo = idCampo.split(Constants.COMMA);

		if ((descriptores != null) && (descriptores.length > 0)) {
			qual.append(
					getCustomizedField(ID_ELEMENTO_FIELD, aliasElemento)
							.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO
							.getQualifiedName())
					.append(" FROM " + ReferenciaDBEntityImpl.TABLE_NAME
							+ " WHERE ")
					.append(DBUtils.generateEQTokenField(
							ReferenciaDBEntityImpl.CAMPO_TIPOOBJREF,
							CampoReferenciaVO.REFERENCIA_A_ELEMENTO_DESCRIPTOR))
					.append(" AND ")
					.append(DBUtils
							.generateInTokenField(
									ReferenciaDBEntityImpl.CAMPO_IDOBJREF,
									descriptores));

			if (StringUtils.isNotEmpty(idCampo)
					&& !(idCampo
							.equals(RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES))) {
				qual.append(" AND ")
						.append(DBUtils
								.generateInTokenField(
										ReferenciaDBEntityImpl.CAMPO_ID_CAMPO,
										idsCampo));
			}

			qual.append(")");
		}

		return qual.toString();
	}

	/**
	 * Obtiene la lista de productores de un elemento del cuadro de
	 * clasificación
	 *
	 * @param idElementocf
	 *            Identificador del elemento del cuadro
	 * @param idCampo
	 *            Identificador del campo de productor
	 * @return Lista de productores.
	 */
	public List getProductoresElementoCF(String idElementocf, String idCampo) {
		StringBuffer sqlFrom = new StringBuffer();
		TableDef tablaDescriptores = new TableDef(
				DescriptorDBEntityImpl.TABLE_NAME);
		TableDef tablaReferencias = new TableDef(
				ReferenciaDBEntityImpl.TABLE_NAME);

		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(tablaDescriptores,
						DescriptorDBEntityImpl.CAMPO_ID),
				new DbColumnDef(tablaReferencias,
						ReferenciaDBEntityImpl.CAMPO_IDOBJREF)) };

		sqlFrom.append(DBUtils.generateInnerJoinCondition(tablaDescriptores,
				joins));

		String[] idsCampo = new String[0];
		if (idCampo != null) {
			idsCampo = idCampo.split(Constants.COMMA);
		}

		sqlFrom.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO, idElementocf))
				.append(" AND ")
				.append(DBUtils.generateInTokenField(
						ReferenciaDBEntityImpl.CAMPO_ID_CAMPO, idsCampo));

		return getVOS(Constants.STRING_EMPTY, sqlFrom.toString(),
				DescriptorDBEntityImpl.COLS_DEFS, DescriptorVO.class);
	}

	/**
	 * Obtiene la lista de rangos de un elemento del cuadro de clasificación
	 *
	 * @param idElementocf
	 *            Identificador del elemento del cuadro
	 * @param idCampoInicial
	 *            Identificador del campo rango inicial
	 * @param idCampoFinal
	 *            Identificador del campo rango final
	 * @return Lista de rangos.
	 */
	public List getRangosElementoCF(String idElementocf, String idCampoInicial,
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
								aliasTextoCorto1), idElementocf))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						getCustomizedField(TextoCortoDBEntityImpl.CAMPO_ORDEN,
								aliasTextoCorto1),
						getCustomizedField(TextoCortoDBEntityImpl.CAMPO_ORDEN,
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
				new DbColumnDef("rangoInicial", getCustomizedField(
						TextoCortoDBEntityImpl.CAMPO_VALOR, aliasTextoCorto1)),
				new DbColumnDef("rangoFinal", getCustomizedField(
						TextoCortoDBEntityImpl.CAMPO_VALOR, aliasTextoCorto2)), };

		return getVOS(Constants.STRING_EMPTY, sqlFrom.toString(), columns,
				RangoVO.class);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * fondos.db.IElementoCuadroClasificacionDbEntity#getDistinctIdsArchivo(
	 * java.lang.String)
	 */
	public List getDistinctIdsArchivo(String idArchivo) {
		String qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(ARCHIVO_FIELD, idArchivo))
				.toString();
		DbColumnDef[] COL_IDARCHIVO = new DbColumnDef[] { ARCHIVO_FIELD };

		return getDistinctVOS(qual, TABLE_NAME_ELEMENTO, COL_IDARCHIVO,
				ElementoCuadroClasificacion.class);
	}

	private DbColumnDef getFieldNameFromTipoCampoCambio(int tipoCampoCambio,
			String idCampoCambio) {
		DbColumnDef field = null;
		if (idCampoCambio.equals(DefTipos.CAMPO_ESPECIAL_ID_CODIGO_REFERENCIA)
				|| idCampoCambio
						.equals(DefTipos.CAMPO_ESPECIAL_ID_NIVEL_DESCRIPCION)
				|| idCampoCambio.equals(DefTipos.CAMPO_ESPECIAL_ID_TITULO)) {
			field = ID_ELEMENTO_FIELD;
		} else if (idCampoCambio
				.equals(DefTipos.CAMPO_ESPECIAL_ID_NUMERO_EXPEDIENTE)) {
			field = UnidadDocumentalDBEntityImpl.CAMPO_ID;
		} else {
			switch (tipoCampoCambio) {
			case DefTipos.TIPO_DATO_REFERENCIA: // Enlace
				field = ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO;
				break;
			case DefTipos.TIPO_DATO_FECHA: // Fecha
				field = FechaDBEntityImpl.CAMPO_ID_ELEMENTO;
				break;
			case DefTipos.TIPO_DATO_NUMERICO: // Número
				field = NumeroDBEntityImpl.CAMPO_ID_ELEMENTO;
				break;
			case DefTipos.TIPO_DATO_TEXTO_LARGO: // Texto largo
				field = TextoLargoDBEntityImpl.CAMPO_ID_ELEMENTO;
				break;
			case DefTipos.TIPO_DATO_TEXTO_CORTO: // Texto corto
				field = TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO;
				break;
			}
		}
		return field;
	}

	private IeciTdShortTextArrayList getIdsElemsMultivalor(
			String subqueryForIdsElementos, int tipoCampo, String idCampo,
			final String withQuery) {
		final String tabla;
		final DbColumnDef campoIdCampo, campoIdElementoCF;

		if ((idCampo.equals(DefTipos.CAMPO_ESPECIAL_ID_CODIGO_REFERENCIA))
				|| (idCampo
						.equals(DefTipos.CAMPO_ESPECIAL_ID_NIVEL_DESCRIPCION))
				|| (idCampo
						.equals(DefTipos.CAMPO_ESPECIAL_ID_NUMERO_EXPEDIENTE))
				|| (idCampo.equals(DefTipos.CAMPO_ESPECIAL_ID_TITULO))) {
			return new IeciTdShortTextArrayList();
		}

		switch (tipoCampo) {
		case DefTipos.TIPO_DATO_REFERENCIA: // Enlace
			campoIdElementoCF = ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO;
			campoIdCampo = ReferenciaDBEntityImpl.CAMPO_ID_CAMPO;
			tabla = ReferenciaDBEntityImpl.TABLE_NAME;
			break;
		case DefTipos.TIPO_DATO_FECHA: // Fecha
			campoIdElementoCF = FechaDBEntityImpl.CAMPO_ID_ELEMENTO;
			campoIdCampo = FechaDBEntityImpl.CAMPO_ID_CAMPO;
			tabla = FechaDBEntityImpl.TABLE_NAME;
			break;
		case DefTipos.TIPO_DATO_NUMERICO: // Número
			campoIdElementoCF = NumeroDBEntityImpl.CAMPO_ID_ELEMENTO;
			campoIdCampo = NumeroDBEntityImpl.CAMPO_ID_CAMPO;
			tabla = NumeroDBEntityImpl.TABLE_NAME;
			break;
		case DefTipos.TIPO_DATO_TEXTO_LARGO: // Texto largo
			campoIdElementoCF = TextoLargoDBEntityImpl.CAMPO_ID_ELEMENTO;
			campoIdCampo = TextoLargoDBEntityImpl.CAMPO_ID_CAMPO;
			tabla = TextoLargoDBEntityImpl.TABLE_NAME;
			break;
		case DefTipos.TIPO_DATO_TEXTO_CORTO: // Texto corto
			campoIdElementoCF = TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO;
			campoIdCampo = TextoCortoDBEntityImpl.CAMPO_ID_CAMPO;
			tabla = TextoCortoDBEntityImpl.TABLE_NAME;
			break;
		default:
			campoIdCampo = campoIdElementoCF = null;
			tabla = null;
			return null;
		}

		final StringBuffer qual = new StringBuffer(subqueryForIdsElementos);
		qual.append(" AND ").append(
				DBUtils.generateEQTokenField(campoIdCampo, idCampo));

		qual.append(" GROUP BY ").append(campoIdElementoCF.getQualifiedName())
				.append(", ").append(campoIdCampo.getQualifiedName());

		qual.append(" HAVING ").append(
				DbUtil.getCountColumn(campoIdCampo) + "> 1");

		final IeciTdShortTextArrayList listaStr = new IeciTdShortTextArrayList();
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbSelectFns
						.select(conn,
								tabla,
								DbUtil.getColumnNames(new DbColumnDef[] { campoIdElementoCF }),
								qual.toString(), withQuery, true, listaStr);
			}
		};

		command.execute();
		return listaStr;
	}

	public int updateCampoGenerico(ConsultaConnectBy subqueryForIdsElementos,
			int tipoCampo, String idCampo, String valorAntiguo,
			String valorNuevo, String formatoRangoFechas,
			String condicionValor, boolean soloValoresNulos,
			Integer tipoReferencia) {
		StringBuffer where = new StringBuffer("WHERE ").append(DBUtils
				.generateInTokenFieldSubQuery(
						getFieldNameFromTipoCampoCambio(tipoCampo, idCampo),
						subqueryForIdsElementos.getSqlClause()));

		IeciTdShortTextArrayList idsElemsMultivalor = getIdsElemsMultivalor(
				where.toString(), tipoCampo, idCampo,
				subqueryForIdsElementos.getWithClause());

		int filasModificadas = 0;
		if (idsElemsMultivalor == null || idsElemsMultivalor.count() == 0) {
			// sin campos multivalor
			filasModificadas = updateCampoReemplazoGenerico(where.toString(),
					tipoCampo, idCampo, valorAntiguo, valorNuevo,
					formatoRangoFechas, null, soloValoresNulos,
					subqueryForIdsElementos, tipoReferencia);
		} else { // con algun elemento con campos multivalor
					// dos llamadas una de ellas solo el in con los campos
					// multivalor y la otra añade un not in

			// multivalor
			StringBuffer where2 = new StringBuffer("WHERE ")
					.append(DBUtils
							.generateInTokenField(
									getFieldNameFromTipoCampoCambio(tipoCampo,
											idCampo), idsElemsMultivalor
											.getList()));

			filasModificadas = updateCampoReemplazoGenerico(where2.toString(),
					tipoCampo, idCampo, valorAntiguo, valorNuevo,
					formatoRangoFechas, condicionValor, soloValoresNulos,
					subqueryForIdsElementos, tipoReferencia);

			// no multivalor
			where.append(" AND NOT ")
					.append(DBUtils
							.generateInTokenField(
									getFieldNameFromTipoCampoCambio(tipoCampo,
											idCampo), idsElemsMultivalor
											.getList()));

			filasModificadas += updateCampoReemplazoGenerico(where.toString(),
					tipoCampo, idCampo, valorAntiguo, valorNuevo,
					formatoRangoFechas, null, soloValoresNulos,
					subqueryForIdsElementos, tipoReferencia);
		}
		return filasModificadas;
	}

	public int updateCampoGenerico(List idsElementos, int tipoCampo,
			String idCampo, String valorAntiguo, String valorNuevo,
			String formatoRangoFechas, String condicionValor,
			boolean soloValoresNulos, Integer tipoReferencia) {
		StringBuffer where = new StringBuffer("WHERE ").append(DBUtils
				.generateInTokenField(
						getFieldNameFromTipoCampoCambio(tipoCampo, idCampo),
						idsElementos));

		ConsultaConnectBy queryOnlyIds = new ConsultaConnectBy("SELECT "
				+ ID_ELEMENTO_FIELD + " FROM " + TABLE_NAME_ELEMENTO
				+ " WHERE "
				+ DBUtils.generateInTokenField(ID_ELEMENTO_FIELD, idsElementos));

		IeciTdShortTextArrayList idsElemsMultivalor = getIdsElemsMultivalor(
				where.toString(), tipoCampo, idCampo, Constants.BLANK);

		int filasModificadas = 0;
		if (idsElemsMultivalor == null || idsElemsMultivalor.count() == 0) {
			// sin campos multivalor
			filasModificadas = updateCampoReemplazoGenerico(where.toString(),
					tipoCampo, idCampo, valorAntiguo, valorNuevo,
					formatoRangoFechas, null, soloValoresNulos, queryOnlyIds,
					tipoReferencia);
		} else { // con algun elemento con campos multivalor
					// dos llamadas una de ellas solo el in con los campos
					// multivalor y la otra añade un not in

			// multivalor
			StringBuffer where2 = new StringBuffer("WHERE ")
					.append(DBUtils
							.generateInTokenField(
									getFieldNameFromTipoCampoCambio(tipoCampo,
											idCampo), idsElemsMultivalor
											.getList()));

			filasModificadas = updateCampoReemplazoGenerico(where2.toString(),
					tipoCampo, idCampo, valorAntiguo, valorNuevo,
					formatoRangoFechas, condicionValor, soloValoresNulos,
					queryOnlyIds, tipoReferencia);

			// no multivalor
			// de la lista de elementos inicial quitar los multivalor
			HashSet tablaIdsElemsMultivalor = new HashSet();
			for (int i = 0; i < idsElemsMultivalor.count(); i++) {
				tablaIdsElemsMultivalor.add(idsElemsMultivalor.get(i));
			}

			List idsElemsNoMultivalor = new ArrayList();
			for (Iterator it = idsElementos.iterator(); it.hasNext();) {
				String id = (String) it.next();
				if (!tablaIdsElemsMultivalor.contains(id)) {
					idsElemsNoMultivalor.add(id);
				}
			}

			if (idsElemsNoMultivalor.size() > 0) {
				where = new StringBuffer("WHERE ").append(DBUtils
						.generateInTokenField(
								getFieldNameFromTipoCampoCambio(tipoCampo,
										idCampo), idsElemsNoMultivalor));

				filasModificadas += updateCampoReemplazoGenerico(
						where.toString(), tipoCampo, idCampo, valorAntiguo,
						valorNuevo, formatoRangoFechas, null, soloValoresNulos,
						queryOnlyIds, tipoReferencia);
			}
		}
		return filasModificadas;
	}

	private int updateCampoReemplazoGenerico(String where, int tipoCampo,
			String idCampo, String valorAntiguo, String valorNuevo,
			String formatoRangoFechas, String condicionValor,
			boolean soloValoresNulos, ConsultaConnectBy subqueryIdsElementos,
			Integer tipoReferencia) {
		QueryMapForUpdate query = null;
		int filasAfectadas = 0;
		if (tipoCampo > 0) {
			if (idCampo.equals(DefTipos.CAMPO_ESPECIAL_ID_CODIGO_REFERENCIA)) {
				query = prepararUpdateCampoEspecialCodigoReferencia(where,
						valorNuevo);
			} else if (idCampo
					.equals(DefTipos.CAMPO_ESPECIAL_ID_NIVEL_DESCRIPCION)) {
				query = prepararUpdateCampoEspecialIdNivelDescripcion(where,
						valorNuevo);
			} else if (idCampo
					.equals(DefTipos.CAMPO_ESPECIAL_ID_NUMERO_EXPEDIENTE)) {
				query = prepararUpdateCampoEspecialNumeroExpediente(where,
						valorNuevo);
			} else if (idCampo.equals(DefTipos.CAMPO_ESPECIAL_ID_TITULO)) {
				query = prepararUpdateCampoEspecialTitulo(where, valorNuevo);
			} else if (StringUtils.isNotEmpty(idCampo)) /*
														 * Es un campo especial
														 * descriptor
														 */{
				if (soloValoresNulos)
					return insertCamposEnFicha(subqueryIdsElementos, tipoCampo,
							idCampo, valorNuevo, formatoRangoFechas);
				query = prepararUpdateCampoEspecialSegunTipo(where, tipoCampo,
						idCampo, valorAntiguo, valorNuevo, formatoRangoFechas,
						tipoReferencia);
			}

			if (!StringUtils.isEmpty(condicionValor)) {
				String whereCondicion = query.getWhere() + " AND "
						+ condicionValor;
				query.setWhere(whereCondicion);
			}

			// caso especial: si el campo a reemplazar es alguno de los campos
			// rango
			// (Estos campos son configurados externamente a traves del fichero
			// en
			// descripcion/model/eventos/rangos/EventosNormalizarRangos.properties)
			// tambien hay que añadir en el campo de rango normalizado
			// correspondiente el campo normalizado
			String idCampoNormalizado = EventoNormalizarRangos.getInstance()
					.getIdCampoNormalizado(idCampo);
			if (StringUtils.isNotEmpty(idCampoNormalizado)) {
				QueryMapForUpdate queryValoresNormalizados = prepararUpdateCampoEspecialTipoTextoCorto(
						where, idCampoNormalizado,
						StringUtils.normalizarTexto(valorAntiguo),
						StringUtils.normalizarTexto(valorNuevo));
				filasAfectadas += updateFields(
						queryValoresNormalizados.getWhere(),
						queryValoresNormalizados.getCols(),
						queryValoresNormalizados.getTable(),
						subqueryIdsElementos.getWithClause());
			}

			if (query != null)
				filasAfectadas += updateFields(query.getWhere(),
						query.getCols(), query.getTable(),
						subqueryIdsElementos.getWithClause());
		}
		return filasAfectadas;
	}

	// Clases y metodos privados auxiliares para la actualizacion de las
	// pantallas para reemplazar campos de unidades documentales

	// clase auxiliar para eliminar cohesion con el metodo updateFields
	private class QueryMapForUpdate {
		private HashMap query = null;
		private final String WHERE = "where";
		private final String COLUMNAS = "columnas";
		private final String TABLA = "tabla";

		public QueryMapForUpdate(String where, HashMap colsToUpdate,
				String nombreTabla) {
			query = new HashMap();
			query.put(WHERE, where);
			query.put(COLUMNAS, colsToUpdate);
			query.put(TABLA, nombreTabla);
		}

		// public QueryMapForUpdate(){
		// query=new HashMap();
		// }

		public String getWhere() {
			return (String) query.get(WHERE);
		}

		public HashMap getCols() {
			return (HashMap) query.get(COLUMNAS);
		}

		public String getTable() {
			return (String) query.get(TABLA);
		}

		public void setWhere(String where) {
			query.put(WHERE, where);
		}
		// public void setCols(HashMap colsToUpdate){
		// query.put(COLUMNAS,colsToUpdate); }
		// public void setTable(String nombreTabla){
		// query.put(TABLA,nombreTabla); }
	}

	private QueryMapForUpdate prepararUpdateCampoEspecialCodigoReferencia(
			String where, String valor) {
		StringBuffer qual = new StringBuffer(where);
		HashMap colsToUpdate = new HashMap();

		colsToUpdate.put(CODIGO_REFERENCIA_FIELD, valor);

		return new QueryMapForUpdate(qual.toString(), colsToUpdate,
				TABLE_NAME_ELEMENTO);
	}

	private QueryMapForUpdate prepararUpdateCampoEspecialIdNivelDescripcion(
			String where, String valor) {

		// obtner la Id del nivel
		// no deberia fallar validacion ya realizada

		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD,
						valor));
		List idsNivel = getFieldValues(qual.toString(),
				NivelCFDBEntityImpl.NIVELCF_TABLE_NAME,
				NivelCFDBEntityImpl.ID_NIVEL_FIELD);
		// si no la encuentra o encuentra mas de una no hacer nada
		if (idsNivel == null || idsNivel.size() == 0 || idsNivel.size() > 1)
			return null;

		String idNivel = (String) idsNivel.get(0);
		if (StringUtils.isBlank(idNivel))
			return null;

		qual = new StringBuffer(where);
		HashMap colsToUpdate = new HashMap();

		colsToUpdate.put(NIVEL_FIELD, idNivel);
		return new QueryMapForUpdate(qual.toString(), colsToUpdate,
				TABLE_NAME_ELEMENTO);
	}

	private QueryMapForUpdate prepararUpdateCampoEspecialNumeroExpediente(
			String where, String valor) {
		StringBuffer qual = new StringBuffer(where);
		HashMap colsToUpdate = new HashMap();

		colsToUpdate.put(UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE,
				valor);
		return new QueryMapForUpdate(qual.toString(), colsToUpdate,
				UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL);
	}

	public QueryMapForUpdate prepararUpdateCampoEspecialTitulo(String where,
			String valor) {
		StringBuffer qual = new StringBuffer(where);
		HashMap colsToUpdate = new HashMap();

		colsToUpdate.put(TITULO_FIELD, valor);
		return new QueryMapForUpdate(qual.toString(), colsToUpdate,
				TABLE_NAME_ELEMENTO);
	}

	private QueryMapForUpdate prepararUpdateCampoEspecialSegunTipo(
			String where, int tipoCampo, String idCampo, String valorAntiguo,
			String valorNuevo, String formatoRangoFechas, Integer tipoReferencia) {
		QueryMapForUpdate query = null;
		switch (tipoCampo) {
		case DefTipos.TIPO_DATO_REFERENCIA: // Enlace
			query = prepararUpdateCampoEspecialTipoReferencia(where, idCampo,
					valorAntiguo, valorNuevo, tipoReferencia);
			break;
		case DefTipos.TIPO_DATO_FECHA: // Fecha
			query = prepararUpdateCampoEspecialTipoFecha(where, idCampo,
					valorAntiguo, valorNuevo, formatoRangoFechas);
			break;

		case DefTipos.TIPO_DATO_NUMERICO: // Número
			query = prepararUpdateCampoEspecialTipoNumerico(where, idCampo,
					valorAntiguo, valorNuevo);
			break;

		case DefTipos.TIPO_DATO_TEXTO_LARGO: // Texto largo
			query = prepararUpdateCampoEspecialTipoTextoLargo(where, idCampo,
					valorAntiguo, valorNuevo);
			break;

		case DefTipos.TIPO_DATO_TEXTO_CORTO: // Texto corto
			query = prepararUpdateCampoEspecialTipoTextoCorto(where, idCampo,
					valorAntiguo, valorNuevo);
			break;
		}
		return query;
	}

	private QueryMapForUpdate prepararUpdateCampoEspecialTipoReferencia(
			String where, String idCampo, String valorAntiguo,
			String valorNuevo, Integer tipoReferencia) {
		StringBuffer qual = new StringBuffer(where);
		HashMap colsToUpdate = new HashMap();

		colsToUpdate.put(ReferenciaDBEntityImpl.CAMPO_IDOBJREF, valorNuevo);

		qual.append(" AND ");
		if (tipoReferencia != null) {
			qual.append(DBUtils.generateEQTokenField(
					ReferenciaDBEntityImpl.CAMPO_TIPOOBJREF,
					tipoReferencia.intValue()));
		} else {
			qual.append(DBUtils.generateEQTokenField(
					ReferenciaDBEntityImpl.CAMPO_TIPOOBJREF,
					DefTipos.TIPO_REFERENCIA_DESCRIPTOR));
		}

		if (idCampo.equals(DefTipos.CAMPO_ESPECIAL_TODOS_DESCRIPTORES))
			return null;

		qual.append(" AND ").append(
				DBUtils.generateEQTokenField(
						ReferenciaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));

		if (valorAntiguo != null) {
			qual.append(" AND ")
					.append(DBUtils
							.generateEQTokenField(
									ReferenciaDBEntityImpl.CAMPO_IDOBJREF,
									valorAntiguo));
		}

		return new QueryMapForUpdate(qual.toString(), colsToUpdate,
				ReferenciaDBEntityImpl.TABLE_NAME);
	}

	private QueryMapForUpdate prepararUpdateCampoEspecialTipoFecha(
			String where, String idCampo, String valorAntiguo,
			String valorNuevo, String formatoRangoFechas) {
		StringBuffer qual = new StringBuffer(where);
		HashMap colsToUpdate = new HashMap();

		colsToUpdate.put(FechaDBEntityImpl.CAMPO_FORMATO, formatoRangoFechas);
		colsToUpdate.put(FechaDBEntityImpl.CAMPO_VALOR, valorNuevo);

		CustomDate periodoFecha = new CustomDate(valorNuevo,
				formatoRangoFechas, "/", null);

		colsToUpdate.put(FechaDBEntityImpl.CAMPO_FECHA_FINAL,
				periodoFecha.getMaxDate());
		colsToUpdate.put(FechaDBEntityImpl.CAMPO_FECHA_INICIAL,
				periodoFecha.getMinDate());

		qual.append(" AND ").append(
				DBUtils.generateEQTokenField(FechaDBEntityImpl.CAMPO_ID_CAMPO,
						idCampo));

		if (valorAntiguo != null) {
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(FechaDBEntityImpl.CAMPO_VALOR,
							valorAntiguo));
		}

		return new QueryMapForUpdate(qual.toString(), colsToUpdate,
				FechaDBEntityImpl.TABLE_NAME);
	}

	private QueryMapForUpdate prepararUpdateCampoEspecialTipoNumerico(
			String where, String idCampo, String valorAntiguo, String valorNuevo) {
		StringBuffer qual = new StringBuffer(where);
		HashMap colsToUpdate = new HashMap();

		colsToUpdate.put(NumeroDBEntityImpl.CAMPO_VALOR, valorNuevo);

		qual.append(" AND ").append(
				DBUtils.generateEQTokenField(NumeroDBEntityImpl.CAMPO_ID_CAMPO,
						idCampo));

		if (valorAntiguo != null) {
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(
							NumeroDBEntityImpl.CAMPO_VALOR, valorAntiguo));
		}

		return new QueryMapForUpdate(qual.toString(), colsToUpdate,
				NumeroDBEntityImpl.TABLE_NAME);
	}

	private QueryMapForUpdate prepararUpdateCampoEspecialTipoTextoLargo(
			String where, String idCampo, String valorAntiguo, String valorNuevo) {
		StringBuffer qual = new StringBuffer(where);
		HashMap colsToUpdate = new HashMap();

		colsToUpdate.put(TextoLargoDBEntityImpl.CAMPO_VALOR, valorNuevo);

		qual.append(" AND ").append(
				DBUtils.generateEQTokenField(
						TextoLargoDBEntityImpl.CAMPO_ID_CAMPO, idCampo));

		if (valorAntiguo != null) {
			qual.append(" AND ")
					.append(DBUtils
							.generateContainsTokenField(getConnection(),
									TextoLargoDBEntityImpl.CAMPO_VALOR,
									TextoLargoDBEntityImpl.CAMPO_IDXVALOR,
									valorAntiguo));
		}

		return new QueryMapForUpdate(qual.toString(), colsToUpdate,
				TextoLargoDBEntityImpl.TABLE_NAME);
	}

	private QueryMapForUpdate prepararUpdateCampoEspecialTipoTextoCorto(
			String where, String idCampo, String valorAntiguo, String valorNuevo) {
		StringBuffer qual = new StringBuffer(where);
		HashMap colsToUpdate = new HashMap();

		colsToUpdate.put(TextoCortoDBEntityImpl.CAMPO_VALOR, valorNuevo);

		qual.append(" AND ").append(
				DBUtils.generateEQTokenField(
						TextoCortoDBEntityImpl.CAMPO_ID_CAMPO, idCampo));

		if (valorAntiguo != null) {
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(
							TextoCortoDBEntityImpl.CAMPO_VALOR, valorAntiguo));
		}

		return new QueryMapForUpdate(qual.toString(), colsToUpdate,
				TextoCortoDBEntityImpl.TABLE_NAME);
	}

	public boolean checkUpdateFechaIniFin(List idsElementos, Date fecha,
			String idCampo, boolean isFechaIni) {
		StringBuffer sqlFrom = new StringBuffer("");
		StringBuffer sqlWhere = new StringBuffer("WHERE ");

		sqlWhere.append(DBUtils.generateInTokenField(
				FechaDBEntityImpl.CAMPO_ID_ELEMENTO, idsElementos));

		return checkUpdateFechaIniFin(sqlFrom, sqlWhere, fecha, idCampo,
				isFechaIni);
	}

	public boolean checkUpdateFechaIniFin(
			ConsultaConnectBy subqueryIdsElementos, Date fecha, String idCampo,
			boolean isFechaIni) {
		StringBuffer sqlFrom = new StringBuffer("");
		StringBuffer sqlWhere = new StringBuffer("WHERE ");

		String elementos = "ELEMCFS";
		TableDef tablaUdocs = new TableDef("("
				+ subqueryIdsElementos.getSqlClause() + ")", elementos);
		DbColumnDef idUdoc = new DbColumnDef(tablaUdocs, getCustomizedField(
				ID_ELEMENTO_FIELD, elementos));

		sqlFrom.append(tablaUdocs.getDeclaration());
		sqlWhere.append(DBUtils.generateJoinCondition(idUdoc,
				FechaDBEntityImpl.CAMPO_ID_ELEMENTO));

		return checkUpdateFechaIniFin(sqlFrom, sqlWhere, fecha, idCampo,
				isFechaIni, subqueryIdsElementos.getWithClause());
	}

	// TODO: corregir esta función, no va bien para sacar las series afectadas
	// por un reemplazo de fecha inicial de udoc

	public List checkUdocsInRangeNewFechasSeries(
			ConsultaConnectBy subqueryIdsElementos, Date fecha, String idCampo,
			boolean isFechaIni) {
		StringBuffer sqlFrom = new StringBuffer("");
		StringBuffer sqlWhere = new StringBuffer("WHERE ");

		String elementos = "ELEMSCF";
		TableDef tablaUdocs = new TableDef("("
				+ subqueryIdsElementos.getSqlClause() + ")", elementos);

		DbColumnDef idElem = new DbColumnDef(tablaUdocs, getCustomizedField(
				ID_ELEMENTO_FIELD, elementos));

		sqlFrom.append(tablaUdocs.getDeclaration()).append(", ")
				.append(FechaDBEntityImpl.TABLE_NAME).append(", ")
				.append(TABLE_NAME_ELEMENTO); // UDOCS

		sqlWhere.append(DBUtils.generateJoinCondition(idElem, IDPADRE_FIELD))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(ID_ELEMENTO_FIELD,
						FechaDBEntityImpl.CAMPO_ID_ELEMENTO))
				// udocs de series
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						FechaDBEntityImpl.CAMPO_ID_CAMPO, idCampo))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(TIPO_ELEMENTO_FIELD,
						TipoNivelCF.UNIDAD_DOCUMENTAL.getIdentificador()))
				.append(" AND ");

		if (isFechaIni) {
			sqlWhere.append(DBUtils.generateTokenFieldDateAnioMesDia(
					getConnection(), FechaDBEntityImpl.CAMPO_FECHA_INICIAL,
					"<", fecha));
		} else {
			sqlWhere.append(DBUtils.generateTokenFieldDateAnioMesDia(
					getConnection(), FechaDBEntityImpl.CAMPO_FECHA_FINAL, ">",
					fecha));
		}

		DbColumnDef[] cols = new DbColumnDef[] { IDPADRE_FIELD };
		List seriesFueraRango = null; // series con udocs fuera del rango de
										// fechas (de la serie) que se va a
										// modificar
		try {
			seriesFueraRango = getDistinctVOS_WITH(
					subqueryIdsElementos.getWithClause(), sqlWhere.toString(),
					sqlFrom.toString(), cols, ElementoCuadroClasificacion.class);
		} catch (Exception e) {
			logger.debug("Error en la consulta SQL generada");
			logger.debug("From = " + sqlFrom.toString());
			logger.debug("Where = " + sqlWhere.toString());
		}
		return seriesFueraRango;
	}

	private boolean checkUpdateFechaIniFin(StringBuffer sqlFrom,
			StringBuffer sqlWhere, Date fecha, String idCampo,
			boolean isFechaIni) {
		return checkUpdateFechaIniFin(sqlFrom, sqlWhere, fecha, idCampo,
				isFechaIni, Constants.BLANK);
	}

	private boolean checkUpdateFechaIniFin(StringBuffer sqlFrom,
			StringBuffer sqlWhere, Date fecha, String idCampo,
			boolean isFechaIni, String withQuery) {

		if (sqlFrom.length() > 0
				&& sqlFrom.lastIndexOf(", ") + 2 != sqlFrom.length())
			sqlFrom.append(", ");
		sqlFrom.append(FechaDBEntityImpl.TABLE_NAME);

		if (sqlWhere.length() > 0
				&& sqlFrom.lastIndexOf(" AND ") + 5 != sqlWhere.length())
			sqlWhere.append(" AND ");

		String campo = "";
		if (isFechaIni) {
			// la fecha final no puede ser menor que la nueva fecha inicial
			campo = DescripcionUtils.getIdCampoDescripcionFechaFin();
			sqlWhere.append(
					DBUtils.generateEQTokenField(
							FechaDBEntityImpl.CAMPO_ID_CAMPO, campo))
					.append(" AND ")
					.append(DBUtils.generateTokenFieldDateAnioMesDia(
							getConnection(),
							FechaDBEntityImpl.CAMPO_FECHA_FINAL, "<", fecha));
		} else {
			// la fecha inicial no puede ser mayor que la nueva fecha final
			campo = DescripcionUtils.getIdCampoDescripcionFechaIni();
			sqlWhere.append(
					DBUtils.generateEQTokenField(
							FechaDBEntityImpl.CAMPO_ID_CAMPO, campo))
					.append(" AND ")
					.append(DBUtils.generateTokenFieldDateAnioMesDia(
							getConnection(),
							FechaDBEntityImpl.CAMPO_FECHA_INICIAL, ">", fecha));
		}

		boolean resultado = false;
		try {
			resultado = DbSelectFns.selectCount_WITH(getConnection(),
					withQuery, sqlFrom.toString(), sqlWhere.toString()) == 0;
		} catch (Exception e) {
			logger.debug("Error en la consulta SQL generada");
			logger.debug("From = " + sqlFrom.toString());
			logger.debug("Where = " + sqlWhere.toString());
		}
		return resultado;
	}

	// true no hay que actualizar fechas de series, false hay que actualizarlas
	// y pedir confirmacion
	public boolean checkUpdateSerieFechaIniFin(
			ConsultaConnectBy subqueryIdsUdocs, Date fecha, String idCampo,
			boolean isFechaIni) {
		if (getSeriesAfectadasFechaIniFin(subqueryIdsUdocs, fecha, idCampo,
				isFechaIni, true) != null)
			return false;
		return true;
	}

	public List getSeriesAfectadasFechaIniFin(
			ConsultaConnectBy subqueryIdsUdocs, Date fecha, String idCampo,
			boolean isFechaIni, boolean onlyCount) {
		String[] fromWhere = getQuerySeriesAfectadas(subqueryIdsUdocs, fecha,
				idCampo, isFechaIni);
		String from = fromWhere[0];
		String where = fromWhere[1];

		boolean resultado = false;

		try {
			if (onlyCount) {
				resultado = DbSelectFns.selectCount_WITH(getConnection(),
						subqueryIdsUdocs.getWithClause(), from, where) == 0;
				if (!resultado)
					return new ArrayList(); // más de uno
				return null;
			} else {
				DbColumnDef[] cols = new DbColumnDef[] { ID_ELEMENTO_FIELD,
						CODIGO_FIELD, TITULO_FIELD, CODIGO_REFERENCIA_FIELD,
						CODREFFONDO_FIELD };
				return getDistinctVOS_WITH(subqueryIdsUdocs.getWithClause(),
						where, from, cols, ElementoCuadroClasificacion.class);
			}
		} catch (Exception e) {
			logger.debug("Error en la consulta SQL generada");
			logger.debug("From = " + from);
			logger.debug("Where = " + where);
		}
		return null;
	}

	private String[] getQuerySeriesAfectadas(
			ConsultaConnectBy subqueryIdsUdocs, Date fecha, String idCampo,
			boolean isFechaIni) {
		StringBuffer sqlFrom = new StringBuffer("");
		StringBuffer sqlWhere = new StringBuffer("WHERE ");

		String udocs = "ELEMSCF";
		TableDef tablaUdocs = new TableDef("("
				+ subqueryIdsUdocs.getSqlClause() + ")", udocs);
		DbColumnDef idPadreUdoc = new DbColumnDef(tablaUdocs,
				getCustomizedField(IDPADRE_FIELD, udocs));

		sqlFrom.append(tablaUdocs.getDeclaration()).append(", ")
				.append(FechaDBEntityImpl.TABLE_NAME).append(", ")
				.append(TABLE_NAME_ELEMENTO); // serie

		sqlWhere.append(
				DBUtils.generateJoinCondition(ID_ELEMENTO_FIELD, idPadreUdoc))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(ID_ELEMENTO_FIELD,
						FechaDBEntityImpl.CAMPO_ID_ELEMENTO))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						FechaDBEntityImpl.CAMPO_ID_CAMPO, idCampo))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(TIPO_ELEMENTO_FIELD,
						TipoNivelCF.SERIE.getIdentificador())).append(" AND ");

		if (isFechaIni) {
			sqlWhere.append(DBUtils.generateTokenFieldDateAnioMesDia(
					getConnection(), FechaDBEntityImpl.CAMPO_FECHA_INICIAL,
					">", fecha));
		} else {
			sqlWhere.append(DBUtils.generateTokenFieldDateAnioMesDia(
					getConnection(), FechaDBEntityImpl.CAMPO_FECHA_FINAL, "<",
					fecha));
		}

		return new String[] { sqlFrom.toString(), sqlWhere.toString() };

	}

	public int updateSerieAfectadasFechaIniFin(
			ConsultaConnectBy subqueryIdsUdocs, CustomDate periodoFecha,
			String idCampo, boolean isFechaIni) {

		Date fecha = null;
		if (isFechaIni) {
			fecha = periodoFecha.getMinDate();
		} else {
			fecha = periodoFecha.getMaxDate();
		}
		String[] fromWhere = getQuerySeriesAfectadas(subqueryIdsUdocs, fecha,
				idCampo, isFechaIni);

		String from = fromWhere[0];
		String where = fromWhere[1];

		String querySerieIds = "SELECT DISTINCT "
				+ DBUtils.getQualifiedColumnName(TABLE_NAME_ELEMENTO,
						ID_ELEMENTO_FIELD);
		querySerieIds = querySerieIds + " FROM " + from + " " + where;

		StringBuffer whereUpdate = new StringBuffer("WHERE ").append(
				DBUtils.generateInTokenFieldSubQuery(
						FechaDBEntityImpl.CAMPO_ID_ELEMENTO, querySerieIds))
				.append(" AND ");

		if (isFechaIni) {
			whereUpdate.append(DBUtils.generateEQTokenField(
					FechaDBEntityImpl.CAMPO_ID_CAMPO,
					ConfiguracionSistemaArchivoFactory
							.getConfiguracionSistemaArchivo()
							.getConfiguracionDescripcion()
							.getFechaExtremaInicial()));
		} else {
			whereUpdate.append(DBUtils.generateEQTokenField(
					FechaDBEntityImpl.CAMPO_ID_CAMPO,
					ConfiguracionSistemaArchivoFactory
							.getConfiguracionSistemaArchivo()
							.getConfiguracionDescripcion()
							.getFechaExtremaFinal()));
		}

		int filasCambiadas = 0;
		try {

			HashMap campoAActualizar = new HashMap();
			campoAActualizar.put(FechaDBEntityImpl.CAMPO_VALOR,
					periodoFecha.getValue());
			campoAActualizar.put(FechaDBEntityImpl.CAMPO_FORMATO,
					periodoFecha.getFormat());
			campoAActualizar.put(FechaDBEntityImpl.CAMPO_FECHA_INICIAL,
					periodoFecha.getMinDate());
			campoAActualizar.put(FechaDBEntityImpl.CAMPO_FECHA_FINAL,
					periodoFecha.getMaxDate());
			filasCambiadas += updateFields(whereUpdate.toString(),
					campoAActualizar, FechaDBEntityImpl.TABLE_NAME,
					subqueryIdsUdocs.getWithClause());
		} catch (Exception e) {
			logger.debug("Error en la consulta SQL generada (update)");
			logger.debug("Where = " + whereUpdate.toString());
			filasCambiadas = 0;
		}
		return filasCambiadas;
	}

	public int getCountElementosAfectados(
			BusquedaElementosVO busquedaElementosVO, Busqueda busqueda) {
		String[] fromAliasWhere = getFromWhereIdsElementos(busquedaElementosVO,
				busqueda);
		String sqlFrom = fromAliasWhere[0];
		String qual = fromAliasWhere[2];
		String withQuery = fromAliasWhere[3];

		int elementosAfectados = 0;
		try {
			elementosAfectados = DbSelectFns.selectCount_WITH(getConnection(),
					withQuery, sqlFrom.toString(), qual.toString());
		} catch (Exception e) {
			elementosAfectados = -1;
			logger.debug(e);
		}
		return elementosAfectados;
	}

	public List getElementosAfectados(BusquedaElementosVO busquedaElementosVO,
			Busqueda busqueda) {
		String[] fromAliasWhere = getFromWhereIdsElementos(busquedaElementosVO,
				busqueda);
		String sqlFrom = fromAliasWhere[0];
		String alias = fromAliasWhere[1];
		String qual = fromAliasWhere[2];
		String withStatement = fromAliasWhere[3];

		DbColumnDef[] COLS_ELEMENTO = { ID_ELEMENTO_FIELD, CODIGO_FIELD,
				NIVEL_FIELD, TITULO_FIELD, TIPO_ELEMENTO_FIELD,
				CODIGO_REFERENCIA_FIELD, IDFONDO_FIELD, ESTADO_ELEMENTO_FIELD,
				SUBTIPO_ELEMENTO_FIELD };

		DbColumnDef[] columnas = new DbColumnDef[COLS_ELEMENTO.length];
		for (int i = 0; i < columnas.length; i++) {
			columnas[i] = getCustomizedField(COLS_ELEMENTO[i], alias);
		}

		List elems = null;
		try {
			elems = getDistinctVOS_WITH(withStatement, qual.toString(),
					sqlFrom.toString(), columnas, ElementoCFVO.class);
		} catch (Exception e) {
			logger.debug(e);
			elems = new ArrayList();
		}
		return elems;
	}

	public String getCondicionValorCampo(BusquedaElementosVO vo) {
		// recorrer la lista de ids de campos de la busqueda
		if (vo.getCampo() == null)
			return null;
		String whereCondicionValor = "";

		for (int i = 0; i < vo.getCampo().length; i++) {
			if (vo.getCampo()[i] == vo.getCampoCambio()) {
				// si el campo puede ser multivalor
				// es decir no es un campo especial
				String idCampo = vo.getCampoCambio();
				if ((idCampo
						.equals(DefTipos.CAMPO_ESPECIAL_ID_CODIGO_REFERENCIA))
						|| (idCampo
								.equals(DefTipos.CAMPO_ESPECIAL_ID_NIVEL_DESCRIPCION))
						|| (idCampo
								.equals(DefTipos.CAMPO_ESPECIAL_ID_NUMERO_EXPEDIENTE))
						|| (idCampo.equals(DefTipos.CAMPO_ESPECIAL_ID_TITULO))) {
					return null;
				}

				if (StringUtils.isNotEmpty(idCampo)) { // Es un campo especial
														// que puede ser
														// multivalor
					// añadir condicion de valor para el descriptor

					int tipoCampo = vo.getTipoCampo()[i];
					switch (tipoCampo) {
					case DefTipos.TIPO_DATO_REFERENCIA: // Enlace
						whereCondicionValor = getReemplazarCondicionMultivalorReferencia(
								vo, i);
						break;
					case DefTipos.TIPO_DATO_FECHA: // Fecha
						// dos casos
						// en principio si es un rango y el campo es multivalor
						// se van a cambiar todas las entradas
						if (CustomDateFormat.DATE_OPERATOR_RANGE.equals(vo
								.getOperador()[i])) { // operador rango

						} else { // operador no rango
							CustomDate cd = new CustomDate(
									vo.getFormatoFecha1()[i],
									vo.getValor1A()[i], vo.getValor1M()[i],
									vo.getValor1D()[i], vo.getValor1S()[i]);

							// comparacion valor y formato fecha
							whereCondicionValor = DBUtils.generateEQTokenField(
									FechaDBEntityImpl.CAMPO_FORMATO,
									vo.getFormatoFecha1()[i]);
							whereCondicionValor += " AND ";
							whereCondicionValor += DBUtils
									.generateEQTokenField(
											FechaDBEntityImpl.CAMPO_VALOR,
											cd.getValue());
						}
						break;

					case DefTipos.TIPO_DATO_NUMERICO: // Número
						whereCondicionValor = getReemplazarCondicionMultivalorNumerico(
								vo, i);
						break;

					case DefTipos.TIPO_DATO_TEXTO_LARGO: // Texto largo
						whereCondicionValor = getReemplazarCondicionMultivalorTextoLargo(
								vo, i);
						break;

					case DefTipos.TIPO_DATO_TEXTO_CORTO: // Texto corto
						whereCondicionValor = getReemplazarCondicionMultivalorTextoCorto(
								vo, i);
						break;
					}
				}
				break;
			}
		}
		return whereCondicionValor;
	}

	private String getReemplazarCondicionMultivalorReferencia(
			BusquedaElementosVO vo, int pos) {
		return DBUtils.generateEQTokenField(
				ReferenciaDBEntityImpl.CAMPO_IDOBJREF, vo.getValor1()[pos]);
	}

	protected String getReemplazarCondicionMultivalorFecha(
			BusquedaElementosVO vo, int pos) {
		String operador = vo.getOperador()[pos];
		StringBuffer where = new StringBuffer();
		if (CustomDateFormat.DATE_OPERATOR_EQ.equals(operador)) {
			// OPERADOR IGUAL
			where.append(
					DBUtils.generateTokenFieldDateAnioMesDia(getConnection(),
							FechaDBEntityImpl.CAMPO_FECHA_FINAL, ">=",
							vo.getValor1()[pos]))
					.append(DBUtils.AND)
					.append(DBUtils.generateTokenFieldDateAnioMesDia(
							getConnection(),
							FechaDBEntityImpl.CAMPO_FECHA_INICIAL, "<=",
							vo.getValor2()[pos]));
		} else if (CustomDateFormat.DATE_OPERATOR_LT.equals(operador)) {
			where.append(DBUtils.generateTokenFieldDateAnioMesDia(
					getConnection(), FechaDBEntityImpl.CAMPO_FECHA_INICIAL,
					"<=", vo.getValor1()[pos]));
		} else if (CustomDateFormat.DATE_OPERATOR_LT_OR_EQ.equals(operador)) {
			// OPERADOR MENOR O IGUAL
			where.append(DBUtils.generateTokenFieldDateAnioMesDia(
					getConnection(), FechaDBEntityImpl.CAMPO_FECHA_FINAL, "<=",
					vo.getValor1()[pos]));
		} else if (CustomDateFormat.DATE_OPERATOR_GT.equals(operador)) {
			// OPERADOR MAYOR
			where.append(DBUtils.generateTokenFieldDateAnioMesDia(
					getConnection(), FechaDBEntityImpl.CAMPO_FECHA_FINAL, ">=",
					vo.getValor1()[pos]));
		} else if (CustomDateFormat.DATE_OPERATOR_GT_OR_EQ.equals(operador)) {
			// OPERADOR MAYOR O IGUAL
			where.append(DBUtils.generateTokenFieldDateAnioMesDia(
					getConnection(), FechaDBEntityImpl.CAMPO_FECHA_INICIAL,
					">=", vo.getValor1()[pos]));
		} else if (CustomDateFormat.DATE_OPERATOR_RANGE.equals(operador)) {
			where.append(
					DBUtils.generateBetweenDateAnioMesDia(getConnection(),
							FechaDBEntityImpl.CAMPO_FECHA_INICIAL,
							vo.getValor1()[pos], vo.getValor2()[pos],
							DbUtil.TO_CHAR_PATTERN_AAAAMMDD))
					.append(DBUtils.AND)
					.append(DBUtils.generateBetweenDateAnioMesDia(
							getConnection(),
							FechaDBEntityImpl.CAMPO_FECHA_FINAL,
							vo.getValor1()[pos], vo.getValor2()[pos],
							DbUtil.TO_CHAR_PATTERN_AAAAMMDD));
		} else if (CustomDateFormat.DATE_OPERATOR_CONTAINS.equals(operador)) {
			// OPERADOR CONTAINS
			where.append("(")
					.append(DBUtils.generateTokenFieldDateAnioMesDia(
							getConnection(),
							FechaDBEntityImpl.CAMPO_FECHA_INICIAL, "<=",
							vo.getValor1()[pos]))
					.append(DBUtils.AND)
					.append(DBUtils.generateTokenFieldDateAnioMesDia(
							getConnection(),
							FechaDBEntityImpl.CAMPO_FECHA_FINAL, ">=",
							vo.getValor1()[pos]))
					.append(") OR (")
					.append(DBUtils.generateBetweenDateAnioMesDia(
							getConnection(),
							FechaDBEntityImpl.CAMPO_FECHA_INICIAL,
							vo.getValor1()[pos], vo.getValor2()[pos],
							DbUtil.TO_CHAR_PATTERN_AAAAMMDD)).append(")");
		} else if (CustomDateFormat.DATE_OPERATOR_EXACT.equals(operador)) {
			// OPERADOR EXACTA
			where.append(
					DBUtils.generateTokenFieldDateAnioMesDia(getConnection(),
							FechaDBEntityImpl.CAMPO_FECHA_INICIAL, "=",
							vo.getValor1()[pos]))
					.append(DBUtils.AND)
					.append(DBUtils.generateTokenFieldDateAnioMesDia(
							getConnection(),
							FechaDBEntityImpl.CAMPO_FECHA_FINAL, "=",
							vo.getValor2()[pos]));
		}
		return where.toString();
	}

	private String getReemplazarCondicionMultivalorNumerico(
			BusquedaElementosVO vo, int pos) {
		String operador = vo.getOperador()[pos];
		String where = "";
		if (operador.equals("=") || operador.equals("<")
				|| operador.equals("<=") || operador.equals(">")
				|| operador.equals(">=")) {
			where = NumeroDBEntityImpl.CAMPO_VALOR + operador
					+ vo.getValor1()[pos];
		} else if (operador.equals("[..]")) {
			where = NumeroDBEntityImpl.CAMPO_VALOR + " BETWEEN "
					+ vo.getValor1()[pos] + " AND " + vo.getValor2()[pos];
		}
		return where;
	}

	private String getReemplazarCondicionMultivalorTextoCorto(
			BusquedaElementosVO vo, int pos) {
		String operador = vo.getOperador()[pos];
		String where = null;
		if (operador.equals("=") || operador.equals("<")
				|| operador.equals("<=") || operador.equals(">")
				|| operador.equals(">=")) {
			where = TextoCortoDBEntityImpl.CAMPO_VALOR + operador + "'"
					+ vo.getValor1()[pos] + "'";
		} else if (operador.equals("QCC")) {
			where = TextoCortoDBEntityImpl.CAMPO_VALOR + " LIKE '"
					+ vo.getValor1()[pos] + "%'";
		} else if (operador.equals(Operadores.OPERADOR_QC)) {
			where = DBUtils.generateContainsTokenField(getConnection(),
					TextoCortoDBEntityImpl.CAMPO_VALOR,
					TextoCortoDBEntityImpl.CAMPO_IDXVALOR, vo.getValor1()[pos]);
		}
		return where;
	}

	private String getReemplazarCondicionMultivalorTextoLargo(
			BusquedaElementosVO vo, int pos) {
		ArrayList listaTexto = obtenerListaCondicionesTexto(vo.getValor1()[pos]);
		if (listaTexto == null || listaTexto.size() == 0)
			return null;

		ArrayList conds = new ArrayList();
		Iterator it = listaTexto.iterator();
		while (it.hasNext()) {
			String textoS = (String) it.next();
			conds.add(DBUtils.generateContainsTokenField(getConnection(),
					TextoLargoDBEntityImpl.CAMPO_VALOR,
					TextoLargoDBEntityImpl.CAMPO_IDXVALOR, textoS));
		}
		return condsToWhere(conds, false).toString();
	}

	public void updateElementosXIdPadreYNivel(String idPadre, String idNivel,
			Map colsToUpdate) {

		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateEQTokenField(IDPADRE_FIELD, idPadre))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(NIVEL_FIELD, idNivel));

		updateFields(qual.toString(), colsToUpdate, TABLE_NAME_ELEMENTO);
	}

	public List getIdsElementosBloqueados(List ids) {
		StringBuffer sqlFrom = new StringBuffer(TABLE_NAME_ELEMENTO)
				.append(" LEFT OUTER JOIN ")
				.append(UnidadDocumentalElectronicaDBEntityImpl.TABLE_NAME)
				.append(" ON ")
				.append(DBUtils
						.generateJoinCondition(
								ID_ELEMENTO_FIELD,
								UnidadDocumentalElectronicaDBEntityImpl.IDELEMENTOCF_FIELD))
				.append(" LEFT OUTER JOIN ")
				.append(UDocEnUiDepositoDbEntityImpl.TABLE_NAME)
				.append(" ON ")
				.append(DBUtils.generateJoinCondition(ID_ELEMENTO_FIELD,
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD))
				.append(" LEFT OUTER JOIN ")
				.append(UInstalacionDepositoDBEntity.TABLE_NAME)
				.append(" ON ")
				.append(DBUtils.generateJoinCondition(
						UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD,
						UInstalacionDepositoDBEntity.ID_FIELD));

		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateInTokenField(ID_ELEMENTO_FIELD, ids))
				.append(" AND ")
				.append("(")
				.append(DBUtils.generateEQTokenField(
						UInstalacionDepositoDBEntity.MARCAS_BLOQUEO_FIELD, 1))
				.append(" OR ")
				.append(DBUtils
						.generateEQTokenField(
								UnidadDocumentalElectronicaDBEntityImpl.MARCAS_BLOQUEO_FIELD,
								1)).append(")");

		DbColumnDef[] cols = new DbColumnDef[] { ID_ELEMENTO_FIELD };
		return getDistinctVOS(qual.toString(), sqlFrom.toString(), cols,
				ElementoCuadroClasificacion.class);
	}

	// private List quitarElementosBloqueados(List ids){
	// if(ids.size()==0) return ids;
	//
	// //conseguir la lista de ids
	// List idsBloqueados=getIdsElementosBloqueados(ids);
	//
	// HashSet setIds=new HashSet();
	// for(Iterator it=idsBloqueados.iterator();it.hasNext();){
	// ElementoCuadroClasificacion ecc=(ElementoCuadroClasificacion)it.next();
	// setIds.add(ecc.getId());
	// }
	//
	// if(idsBloqueados.size()>0){
	// for(Iterator it=ids.iterator();it.hasNext();){
	// String id=(String)it.next();
	// if(setIds.contains(id)) it.remove();
	// }
	// }
	//
	// return ids;
	// }

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * fondos.db.IElementoCuadroClasificacionDbEntity#checkUdocsEnMismoFondo
	 * (java.util.List, java.lang.String)
	 */
	public boolean checkUdocsEnMismoFondo(List ids, String idFondo) {
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateInTokenField(ID_ELEMENTO_FIELD, ids))
				.append(" AND ")
				.append(DBUtils.generateNEQTokenField(IDFONDO_FIELD, idFondo));
		return getDistintVOCount(new DbColumnDef[] { IDFONDO_FIELD },
				qual.toString(), TABLE_NAME_ELEMENTO) == 0;
	}

	/**
	 * Comprueba que los elementos seleccionados sean del mismo tipo.
	 *
	 * @param ids
	 * @return
	 */
	public boolean checkElementosMismoTipo(String[] ids) {
		/*
		 * SELECT COUNT(DISTINCT TIPO) FROM ASGDFELEMENTOCF WHERE ID IN (ids);
		 */
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateInTokenField(ID_ELEMENTO_FIELD, ids));

		if (getDistintVOCount(new DbColumnDef[] { TIPO_ELEMENTO_FIELD },
				qual.toString(), TABLE_NAME_ELEMENTO) > 1)
			return false;
		return true;
	}

	/**
	 * Comprueba si los padres de los elementos seleccionados son distintos,
	 * siempre que no este permitido seleccionar distintos padres.
	 *
	 * @param ids
	 * @param permitidoDistintoPadre
	 * @return
	 */
	public boolean checkElementosDistintoPadre(String[] ids,
			boolean permitidoDistintoPadre) {
		/*
		 * SELECT COUNT(DISTINCT ID_PADRE) FROM ASGDFELEMENTOCF WHERE ID IN
		 * (ids);
		 */

		if (permitidoDistintoPadre)
			return true;
		else {
			StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
					.generateInTokenField(ID_ELEMENTO_FIELD, ids));

			if (getDistintVOCount(new DbColumnDef[] { IDPADRE_FIELD },
					qual.toString(), TABLE_NAME_ELEMENTO) > 1)
				return false;
			return true;
		}
	}

	public boolean checkPermisosAccesibilidadElementoYPadres(String idElemento,
			ServiceClient serviceClient, Object[] permisos) throws Exception {
		List padres = getAncestors(idElemento);
		for (Iterator it = padres.iterator(); it.hasNext();) {
			ElementoCuadroClasificacionVO elem = ((ElementoCuadroClasificacionVO) it
					.next());
			if (!checkAccesibilidadElementoWithPermissions(elem.getId(),
					serviceClient, permisos)) {
				throw new PadreSinAccesoPermitidoException(elem.getIdNivel(),
						elem.getTipo(), elem.getTitulo());
			}
		}
		return checkAccesibilidadElementoWithPermissions(idElemento,
				serviceClient, permisos);
	}

	public boolean checkAccesibilidadElementoWithPermissions(String idElemento,
			ServiceClient serviceClient, Object[] permisos) {
		if (StringUtils.isEmpty(idElemento))
			return false;

		StringBuffer qual = new StringBuffer("where ").append(DBUtils
				.generateEQTokenField(ID_ELEMENTO_FIELD, idElemento));

		StringBuffer tablas = new StringBuffer(TABLE_NAME_ELEMENTO);
		DBUtils.addPermissionsCheckingClausesWithPermissions(serviceClient,
				tablas, qual, NIVEL_ACCESO_FIELD, ARCHIVO_FIELD,
				LISTA_ACCESO_FIELD, permisos);
		if (getVOCount(qual.toString(), tablas.toString(), HINT_IDPADRE_TITULO) > 0) {
			return true;
		}
		return false;
	}

	// metodos insert para crear los registros de los reemplazos cuando se
	// buscan valores nulos
	// (se quiere dar valor a un campo de la ficha que antes no tenia valor =>
	// no había registro en la tabla correspondiente)
	public boolean insertFichaCampoEspecialTipoReferencia(String idElemento,
			String idCampo, String valor) {
		// (idElemento,idCampo,DefTipos.TIPO_REFERENCIA_DESCRIPTOR,valor,orden(1),TIMESTAMP)
		CampoReferenciaVO campo = new CampoReferenciaVO();

		campo.setIdObjeto(idElemento);
		campo.setIdCampo(idCampo);
		campo.setIdObjRef(valor);
		campo.setOrden(1);
		campo.setTimestamp(DateUtils.getFechaActual());
		campo.setTipoObjRef(DefTipos.TIPO_REFERENCIA_DESCRIPTOR);

		SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
				ReferenciaDBEntityImpl.COL_DEFS, campo);
		try {
			DbInsertFns.insert(getConnection(),
					ReferenciaDBEntityImpl.TABLE_NAME,
					ReferenciaDBEntityImpl.COLUMN_NAMES_LIST, inputRecord);
		} catch (Exception e) {
			logger.debug(e);
			return false;
		}
		return true;
	}

	public boolean insertFichaCampoEspecialTipoNumerico(String idElemento,
			String idCampo, String valor) {
		// (idElemto,idCampo,valor,orden(1),TIMESTAMP,tipoMedida(0),unidadMedida(NULL))
		CampoNumericoVO campo = new CampoNumericoVO();

		campo.setIdObjeto(idElemento);
		campo.setIdCampo(idCampo);
		campo.setValor(Double.parseDouble(valor));
		campo.setOrden(1);
		campo.setTipoMedida(0);
		// campo.setUnidadMedida(unidadMedida);
		campo.setTimestamp(DateUtils.getFechaActual());

		SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
				NumeroDBEntityImpl.COL_DEFS, campo);
		try {
			DbInsertFns.insert(getConnection(), NumeroDBEntityImpl.TABLE_NAME,
					NumeroDBEntityImpl.COLUMN_NAMES_LIST, inputRecord);
		} catch (Exception e) {
			logger.debug(e);
			return false;
		}
		return true;
	}

	public boolean insertFichaCampoEspecialTipoFecha(String idElemento,
			String idCampo, String valor, String formatoRangoFechas) {
		// (idElemento,idCampo,valor,fechaIni,fechaFin,calificador(null),orden(1),TIMESTAMP,formato,Sep(/))
		CampoFechaVO campo = new CampoFechaVO();

		CustomDate periodoFecha = new CustomDate(valor, formatoRangoFechas,
				"/", null);

		campo.setIdObjeto(idElemento);
		campo.setIdCampo(idCampo);
		campo.setValor(valor);
		campo.setFechaIni(periodoFecha.getMinDate());
		campo.setFechaFin(periodoFecha.getMaxDate());
		// campo.setCalificador(calificador);
		campo.setOrden(1);
		campo.setTimestamp(DateUtils.getFechaActual());
		campo.setFormato(formatoRangoFechas);
		campo.setSep(StringUtils.getPrimerCaracterNoNumerico(valor));

		SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
				FechaDBEntityImpl.COL_DEFS, campo);
		try {
			DbInsertFns.insert(getConnection(), FechaDBEntityImpl.TABLE_NAME,
					FechaDBEntityImpl.COLUMN_NAMES_LIST, inputRecord);
		} catch (Exception e) {
			logger.debug(e);
			return false;
		}
		return true;
	}

	public boolean insertFichaCampoEspecialTipoTextoCorto(String idElemento,
			String idCampo, String valor) {
		// (idElemento,idCampo,valor,orden(1),TIMESTAMP)
		CampoTextoVO campo = new CampoTextoVO();

		campo.setIdObjeto(idElemento);
		campo.setIdCampo(idCampo);
		campo.setValor(valor);
		campo.setOrden(1);
		// campo.setUnidadMedida(unidadMedida);
		campo.setTimestamp(DateUtils.getFechaActual());

		SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
				TextoCortoDBEntityImpl.COL_DEFS, campo);
		try {
			DbInsertFns.insert(getConnection(),
					TextoCortoDBEntityImpl.TABLE_NAME,
					TextoCortoDBEntityImpl.COLUMN_NAMES_LIST, inputRecord);
		} catch (Exception e) {
			logger.debug(e);
			return false;
		}
		return true;
	}

	public boolean insertFichaCampoEspecialTipoTextoLargo(String idElemento,
			String idCampo, String valor) {
		// (idElemento,idCampo,valor,orden(1),TIMESTAMP)
		CampoTextoVO campo = new CampoTextoVO();

		campo.setIdObjeto(idElemento);
		campo.setIdCampo(idCampo);
		campo.setValor(valor);
		campo.setOrden(1);
		// campo.setUnidadMedida(unidadMedida);
		campo.setTimestamp(DateUtils.getFechaActual());

		SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
				TextoLargoDBEntityImpl.COL_DEFS, campo);
		try {
			DbInsertFns.insert(getConnection(),
					TextoLargoDBEntityImpl.TABLE_NAME,
					TextoLargoDBEntityImpl.COLUMN_NAMES_LIST, inputRecord);
		} catch (Exception e) {
			logger.debug(e);
			return false;
		}
		return true;
	}

	private boolean insertCampoEspecialSegunTipo(String idElemento,
			int tipoCampo, String idCampo, String valor,
			String formatoRangoFechas) {
		boolean result = false;
		switch (tipoCampo) {
		case DefTipos.TIPO_DATO_REFERENCIA: // Enlace
			result = insertFichaCampoEspecialTipoReferencia(idElemento, ""
					+ idCampo, valor);
			break;
		case DefTipos.TIPO_DATO_FECHA: // Fecha
			result = insertFichaCampoEspecialTipoFecha(idElemento, idCampo,
					valor, formatoRangoFechas);
			break;

		case DefTipos.TIPO_DATO_NUMERICO: // Número
			result = insertFichaCampoEspecialTipoNumerico(idElemento, ""
					+ idCampo, valor);
			break;

		case DefTipos.TIPO_DATO_TEXTO_LARGO: // Texto largo
			result = insertFichaCampoEspecialTipoTextoLargo(idElemento, ""
					+ idCampo, valor);
			break;

		case DefTipos.TIPO_DATO_TEXTO_CORTO: // Texto corto
			result = insertFichaCampoEspecialTipoTextoCorto(idElemento, ""
					+ idCampo, valor);
			break;
		}
		return result;
	}

	private int insertCamposEnFicha(ConsultaConnectBy consultaIds,
			int tipoCampo, String idCampo, String valor,
			String formatoRangoFechas) {
		// obtener los Ids de los elementos en cuya ficha hay que insertar el
		// campo
		List idsElementos = getVOS(new DbColumnDef[] { ID_ELEMENTO_FIELD },
				consultaIds, ElementoCFVO.class);

		int numCamposInsertados = 0;
		for (Iterator it = idsElementos.iterator(); it.hasNext();) {
			// utilizar cada id de esa lista para generar
			String idElemento = ((ElementoCFVO) it.next()).getId();
			if (insertCampoEspecialSegunTipo(idElemento, tipoCampo, idCampo,
					valor, formatoRangoFechas))
				numCamposInsertados++;
		}

		return numCamposInsertados;
	}

	public List getIdsElementosCampoVacioFicha(List idsElementos,
			int tipoCampo, String idCampo) {
		// id not in (select idelementocf from ADVFECHACF WHERE VALOR IS NOT
		// NULL)

		String query = null, operador = "=", valor1 = "", valor2 = "";
		if (idCampo.equals(DefTipos.CAMPO_ESPECIAL_ID_CODIGO_REFERENCIA)) {
			query = componerCampoEspecialIdCodigoReferencia(operador, valor1);
		} else if (idCampo.equals(DefTipos.CAMPO_ESPECIAL_ID_NIVEL_DESCRIPCION)) {
			query = componerCampoEspecialIdNivelDescripcion(operador, valor1);
		} else if (idCampo.equals(DefTipos.CAMPO_ESPECIAL_ID_NUMERO_EXPEDIENTE)) {
			query = componerCampoEspecialIdNumeroExpediente(operador, valor1);
		} else if (idCampo.equals(DefTipos.CAMPO_ESPECIAL_ID_TITULO)) {
			query = componerCampoEspecialIdTitulo(operador, valor1);
		} else if (StringUtils.isNotEmpty(idCampo)) /*
													 * Es un campo especial
													 * descriptor
													 */{
			query = componerCampoEspecialSegunTipo(tipoCampo, idCampo,
					operador, valor1, valor2, new Integer(
							DefTipos.TIPO_REFERENCIA_DESCRIPTOR));
		}

		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateInTokenField(ID_ELEMENTO_FIELD,
						idsElementos)).append(" AND ")
				.append(ID_ELEMENTO_COLUMN_NAME_S).append(" NOT IN ")
				.append(query);

		DbColumnDef[] cols = new DbColumnDef[] { ID_ELEMENTO_FIELD };
		List listaElementos = getDistinctVOS(qual.toString(),
				TABLE_NAME_ELEMENTO, cols, ElementoCuadroClasificacion.class);
		List nuevaListaIds = new ArrayList();

		for (Iterator it = listaElementos.iterator(); it.hasNext();) {
			String id = ((ElementoCuadroClasificacion) it.next()).getId();
			nuevaListaIds.add(id);
		}

		return nuevaListaIds;
	}

	public List getNombresNiveles(List idsElementos) {
		String tables = TABLE_NAME_ELEMENTO + ","
				+ NivelCFDBEntityImpl.NIVELCF_TABLE_NAME;
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateJoinCondition(NIVEL_FIELD,
						NivelCFDBEntityImpl.ID_NIVEL_FIELD))
				.append(" AND ")
				.append(DBUtils.generateInTokenField(ID_ELEMENTO_FIELD,
						idsElementos));

		return getMultipleFieldValues(qual.toString(), tables,
				new DbColumnDef[] { ID_ELEMENTO_FIELD,
						NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD });
	}

	public HashMap getPairsIdCodigo(List idsElementos) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateInTokenField(ID_ELEMENTO_FIELD, idsElementos));

		return getPairsIdValue(qual.toString(), TABLE_NAME_ELEMENTO,
				ID_ELEMENTO_FIELD, CODIGO_FIELD);
	}

	public HashMap getPairsIdCodRefPadre(List idsElementos) {
		String ELEMENTO = "elem";
		String PADRE = "padre";

		TableDef tablaElemento = new TableDef(TABLE_NAME_ELEMENTO, ELEMENTO);
		TableDef tablaPadre = new TableDef(TABLE_NAME_ELEMENTO, PADRE);

		StringBuffer from = new StringBuffer(tablaElemento.getDeclaration())
				.append(",").append(tablaPadre.getDeclaration());

		DbColumnDef idElemento = new DbColumnDef(tablaElemento,
				ID_ELEMENTO_FIELD);
		DbColumnDef idElementoPadre = new DbColumnDef(tablaPadre,
				ID_ELEMENTO_FIELD);
		DbColumnDef idPadre = new DbColumnDef(tablaElemento, IDPADRE_FIELD);
		DbColumnDef codRefPadre = new DbColumnDef(tablaPadre,
				CODIGO_REFERENCIA_FIELD);

		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateJoinCondition(idPadre, idElementoPadre))
				.append(" AND ")
				.append(DBUtils.generateInTokenField(idElemento, idsElementos));

		return getPairsIdValue(qual.toString(), from.toString(), idElemento,
				codRefPadre);
	}

	public String getQueryNumUdocsEnUIsYSerie(String idSerie, List idUis,
			String aliasInterno) {
		// implementa la siguiente consulta

		// SELECT MAX(ORIGEN.numUdocs) AS NUMDOCS,ORIGEN.UI AS IDUI
		// FROM (
		// (SELECT COUNT(asgdudocenui.idunidaddoc) AS numUdocs, iduinstalacion
		// AS UI
		// FROM asgdudocenui asgdudocenui,asgfunidaddoc asgfunidaddoc
		// WHERE asgfunidaddoc.idelementocf = asgdudocenui.idunidaddoc AND
		// asgfunidaddoc.idserie = 'sJU000000000000000000000000S014532' AND
		// asgdudocenui.iduinstalacion IN
		// ('u0JU0000000000000000000000046293','u0JU0000000000000000000000049426')
		// GROUP BY iduinstalacion )
		// UNION
		// (SELECT 0 AS numUdocs, iduinstalacion AS UI
		// FROM asgdudocenui asgdudocenui
		// WHERE asgdudocenui.iduinstalacion IN
		// ('u0JU0000000000000000000000046293','u0JU0000000000000000000000049426')
		// ) ) ORIGEN
		// GROUP BY ORIGEN.UI

		String aliasNumDocs = "NUMUDOCS";
		String aliasIdsUIs = "IDUI";
		String aliasNumDocsSubquery = "numUdocs";
		String aliasUISubquery = "UI";
		StringBuffer sql = new StringBuffer("SELECT ")
				.append(DBUtils.generateMAX(new DbColumnDef(aliasNumDocs,
						aliasInterno + "." + aliasNumDocsSubquery),
						aliasNumDocs))
				.append("," + aliasInterno + "." + aliasUISubquery + " AS "
						+ aliasIdsUIs).append(" FROM (");

		StringBuffer sqlSubquery = new StringBuffer("(SELECT ")
				.append(DBUtils.generateUnaryFunction("COUNT",
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD,
						aliasNumDocsSubquery))
				.append(","
						+ UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD
								.getQualifiedName() + " AS " + aliasUISubquery)
				.append(" FROM ")
				.append(UDocEnUiDepositoDbEntityImpl.TABLE_NAME
						+ ","
						+ UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL)
				.append(" WHERE ")
				.append(DBUtils.generateJoinCondition(
						UnidadDocumentalDBEntityImpl.CAMPO_ID,
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						UnidadDocumentalDBEntityImpl.CAMPO_IDSERIE, idSerie))
				.append(" AND ")
				.append(DBUtils.generateInTokenField(
						UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD,
						idUis))
				.append(" GROUP BY ")
				.append(UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD
						.getQualifiedName())
				.append(" ) UNION (")
				.append("SELECT ")
				.append("0 AS " + aliasNumDocsSubquery)
				.append(",")
				.append(UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD
						.getQualifiedName() + " AS " + aliasUISubquery)
				.append(" FROM ")
				.append(UDocEnUiDepositoDbEntityImpl.TABLE_NAME)
				.append(" WHERE ")
				.append(DBUtils.generateInTokenField(
						UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD,
						idUis)).append(" ) ");

		sql.append(sqlSubquery.toString()).append(" ) " + aliasInterno)
				.append(" GROUP BY " + aliasInterno + "." + aliasUISubquery);
		return sql.toString();
	}

	public List getNumUdocsLongFormatoMovimiento(String idSerieOrigen,
			String idSerieDestino, List idsUIs,
			String[] aliasCamposDevueltosConsulta) {
		if (aliasCamposDevueltosConsulta.length == 3)
			return new ArrayList();

		// basandose en el metodo anterior implementa la siguiente consulta
		// SELECT
		// UDOCSXUI_ORIGEN.IDUI,UDOCSXUI_ORIGEN.NUMUDOCS,UDOCSXUI_DESTINO.NUMDOCS,AGFORMATO.LONGITUD,AGFORMATO.NOMBRE
		// FROM ASGDUINSTALACION,AGFORMATO,
		// (...) UDOCSXUI_ORIGEN,
		// (...) UDOCSXUI_DESTINO
		// WHERE UDOCSXUI_ORIGEN.IDUI=UDOCSXUI_DESTINO.IDUI AND
		// ASGDUINSTALACION.ID=UDOCSXUI_ORIGEN.IDUI AND
		// ASGDUINSTALACION.IDFORMATO=AGFORMATO.ID

		String aliasSubQueryOrigen = "UDOCSXUI_ORIGEN";
		String aliasSubQueryDestino = "UDOCSXUI_DESTINO";
		String colNameIDUISubQuery = "IDUI";
		String colNameNUMUDOCSSubQuery = "NUMUDOCS";
		String aliasSubSubQueryOrigen = "ORIGEN";
		String aliasSubSubQueryDestino = "DESTINO";

		// Definir columnas para recoger los resultados.
		DbColumnDef idUIQuery = new DbColumnDef(
				aliasCamposDevueltosConsulta[0], aliasSubQueryOrigen + "."
						+ colNameIDUISubQuery, DbDataType.SHORT_TEXT);
		DbColumnDef numUDocSerieOrigenQuery = new DbColumnDef(
				aliasCamposDevueltosConsulta[1], aliasSubQueryOrigen + "."
						+ colNameNUMUDOCSSubQuery, DbDataType.SHORT_INTEGER);
		DbColumnDef numUDocSerieDestinoQuery = new DbColumnDef(
				aliasCamposDevueltosConsulta[2], aliasSubQueryDestino + "."
						+ colNameNUMUDOCSSubQuery, DbDataType.SHORT_INTEGER);

		DbColumnDef[] cols = new DbColumnDef[] { idUIQuery,
				numUDocSerieOrigenQuery, numUDocSerieDestinoQuery,
				FormatoDBEntity.CAMPO_LONGITUD };

		// StringBuffer select=new StringBuffer("SELECT ")
		// .append(aliasSubQueryOrigen+"."+colNameIDUISubQuery+",")
		// .append(aliasSubQueryOrigen+"."+colNameNUMUDOCSSubQuery+",")
		// .append(aliasSubQueryDestino+"."+colNameNUMUDOCSSubQuery+",")
		// .append(FormatoDBEntity.CAMPO_LONGITUD.getQualifiedName());
		StringBuffer tables = new StringBuffer(" ")
				.append(UInstalacionDepositoDBEntity.TABLE_NAME + ","
						+ FormatoDBEntity.TABLE_NAME)
				.append(",")
				.append("("
						+ getQueryNumUdocsEnUIsYSerie(idSerieOrigen, idsUIs,
								aliasSubSubQueryOrigen) + ") "
						+ aliasSubQueryOrigen)
				.append(",")
				.append("("
						+ getQueryNumUdocsEnUIsYSerie(idSerieDestino, idsUIs,
								aliasSubSubQueryDestino) + ") "
						+ aliasSubQueryDestino);
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(aliasSubQueryOrigen + "." + colNameIDUISubQuery + "="
						+ aliasSubQueryDestino + "." + colNameIDUISubQuery)
				.append(" AND ")
				.append(UInstalacionDepositoDBEntity.ID_FIELD
						.getQualifiedName()
						+ "="
						+ aliasSubQueryOrigen
						+ "."
						+ colNameIDUISubQuery)
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						UInstalacionDepositoDBEntity.IDFORMATO_FIELD,
						FormatoDBEntity.CAMPO_ID));

		return getListMapsFieldValues(qual.toString(), tables.toString(), cols);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see fondos.db.IElementoCuadroClasificacionDbEntity#checkUdocsEnMismoArchivo(java.lang.String[])
	 */
	public boolean checkUdocsEnMismoArchivo(String[] ids) {
		/*
		 * SELECT COUNT(DISTINCT IDARCHIVO) FROM ASGDFELEMENTOCF WHERE ID IN
		 * (ids);
		 */
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateInTokenField(ID_ELEMENTO_FIELD, ids));

		if (getDistintVOCount(new DbColumnDef[] { ARCHIVO_FIELD },
				qual.toString(), TABLE_NAME_ELEMENTO) > 1)
			return false;
		return true;

	}

	public int getMarcasBloqueoElemento(String idElementoCF) {
		int marcasBloqueo = 0;

		DbColumnDef[] colDefs = new DbColumnDef[] { UnidadDocumentalDBEntityImpl.CAMPO_MARCAS_BLOQUEO };

		StringBuffer sqlFrom = new StringBuffer()
				.append(ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO)
				.append(", ")
				.append(UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL);

		StringBuffer qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils
						.generateJoinCondition(
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD,
								UnidadDocumentalDBEntityImpl.CAMPO_ID))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD,
								idElementoCF));

		ElementoCFVO elemento = (ElementoCFVO) getVO(qual.toString(),
				sqlFrom.toString(), colDefs, ElementoCFVO.class);
		if (elemento != null)
			marcasBloqueo = elemento.getMarcasBloqueo();

		return marcasBloqueo;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see fondos.db.IElementoCuadroClasificacionDbEntity#deleteElementosCFRelacion(java.lang.String[])
	 */
	public void deleteElementosCFRelacion(String[] idsUdoc) {
		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils
						.generateInTokenField(ID_ELEMENTO_FIELD, idsUdoc))
				.toString();

		deleteElementoCFXQual(qual);
	}

	public List getElementosCuadroClasificacion(
			TablaTemporalFondosVO tablaTemporalFondosVO) {
		String subquery = TablaTemporalDBEntityImpl
				.getSelectIdElementocfFromTemporal(tablaTemporalFondosVO);

		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateInTokenFieldSubQuery(ID_ELEMENTO_FIELD,
						subquery.toString()));

		return getElementosCF(qual.toString(), HINT_IDPADRE_TITULO);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see fondos.db.IElementoCuadroClasificacionDbEntity#updateEstadoEliminacionElementoCF(java.lang.String[],
	 *      int, java.lang.String)
	 */
	public void removeIdEliminacionElementoCF(String[] ids) {
		final String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(ID_ELEMENTO_FIELD, ids))
				.toString();

		HashMap mapColumnsToUpdate = new HashMap();
		mapColumnsToUpdate.put(ID_ELIMINACION_FIELD, null);

		updateFields(qual, mapColumnsToUpdate, TABLE_NAME_ELEMENTO);
	}

	public List getNombresNiveles(TablaTemporalFondosVO tablaTemporalFondosVO) {
		String tables = TABLE_NAME_ELEMENTO + ","
				+ NivelCFDBEntityImpl.NIVELCF_TABLE_NAME;
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateJoinCondition(NIVEL_FIELD,
						NivelCFDBEntityImpl.ID_NIVEL_FIELD))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateInTokenFieldSubQuery(
								ID_ELEMENTO_FIELD,
								TablaTemporalDBEntityImpl
										.getSelectIdElementocfFromTemporal(tablaTemporalFondosVO)));

		return getMultipleFieldValues(qual.toString(), tables,
				new DbColumnDef[] { ID_ELEMENTO_FIELD,
						NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD });
	}

	public HashMap getPairsIdCodRefPadre(
			TablaTemporalFondosVO tablaTemporalFondosVO) {
		String ELEMENTO = "elem";
		String PADRE = "padre";

		TableDef tablaElemento = new TableDef(TABLE_NAME_ELEMENTO, ELEMENTO);
		TableDef tablaPadre = new TableDef(TABLE_NAME_ELEMENTO, PADRE);

		StringBuffer from = new StringBuffer(tablaElemento.getDeclaration())
				.append(",").append(tablaPadre.getDeclaration());

		DbColumnDef idElemento = new DbColumnDef(tablaElemento,
				ID_ELEMENTO_FIELD);
		DbColumnDef idElementoPadre = new DbColumnDef(tablaPadre,
				ID_ELEMENTO_FIELD);
		DbColumnDef idPadre = new DbColumnDef(tablaElemento, IDPADRE_FIELD);
		DbColumnDef codRefPadre = new DbColumnDef(tablaPadre,
				CODIGO_REFERENCIA_FIELD);

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateJoinCondition(idPadre, idElementoPadre))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateInTokenFieldSubQuery(
								idElemento,
								TablaTemporalDBEntityImpl
										.getSelectIdElementocfFromTemporal(tablaTemporalFondosVO)));

		return getPairsIdValue(qual.toString(), from.toString(), idElemento,
				codRefPadre);
	}

}