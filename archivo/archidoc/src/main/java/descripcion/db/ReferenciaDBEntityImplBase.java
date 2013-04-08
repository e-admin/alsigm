package descripcion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import xml.config.ConfiguracionDescripcion;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.SigiaDbInputRecord;
import common.db.TableDef;
import common.exceptions.DBException;
import common.util.StringUtils;

import descripcion.vos.CampoReferenciaVO;
import descripcion.vos.ProductorElementoCF;
import descripcion.vos.ValorCampoGenericoVOBase;
import fondos.db.UnidadDocumentalDBEntityImpl;
import fondos.vos.InteresadoUdocVO;

/**
 * DBEntity para acceder a las tablas de referencia.
 */
public abstract class ReferenciaDBEntityImplBase extends DBEntity implements
		IReferenciaDBEntity {
	/** Nombre de columnas */
	public static final String IDCAMPO_COLUMN_NAME = "idcampo";
	public static final String TIPOOBJREF_COLUMN_NAME = "tipoobjref";
	public static final String IDOBJREF_COLUMN_NAME = "idobjref";
	public static final String ORDEN_COLUMN_NAME = "orden";
	public static final String IDOBJETO_COLUMN_ALIAS = "idobjeto";

	/** Métodos abstractos que los hijos deben implementar */
	public abstract String getTableName();

	public abstract DbColumnDef getCampoIdElemento();

	public abstract DbColumnDef getCampoIdCampo();

	public abstract DbColumnDef getCampoOrden();

	public abstract DbColumnDef getCampoTipoObjRef();

	public abstract DbColumnDef getCampoIdObjRef();

	public abstract DbColumnDef getCampoTipoElemento();

	public abstract DbColumnDef[] getColDefs();

	public abstract DbColumnDef[] getCustomColDefs();

	public abstract String getHint();

	public abstract String getHintTipoObjIdObj();

	public abstract String getColumnNamesList();

	/**
	 * Constructor.
	 * 
	 * @param dataSource
	 *            Pool de conexiones de base de datos.
	 */
	public ReferenciaDBEntityImplBase(DbDataSource dataSource) {
		super(dataSource);
	}

	public ReferenciaDBEntityImplBase(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la lista de valores de tipo referencia de la ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @return Lista de valores.
	 */
	public List getValues(String idElementoCF, int tipoElemento) {

		return getValues(idElementoCF, tipoElemento, null);

	}

	/**
	 * Obtiene la lista de valores de tipo referencia de la ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @return Lista de valores.
	 */
	public List getValues(String idElementoCF, int tipoElemento,
			ArrayList listaCamposIgnorar) {
		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(getCampoIdElemento(),
						idElementoCF));

		if (getCampoTipoElemento() != null
				&& tipoElemento != ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(getCampoTipoElemento(),
							tipoElemento));

		if (listaCamposIgnorar != null && listaCamposIgnorar.size() > 0)
			qual.append(DBUtils.AND).append(
					DBUtils.generateNotInTokenField(getCampoIdCampo(),
							listaCamposIgnorar));

		qual.append(" ORDER BY ").append(getCampoIdCampo().getName())
				.append(",").append(getCampoOrden().getName());

		return getVOS(qual.toString(), getTableName(), getHint(),
				getCustomColDefs(), CampoReferenciaVO.class);
	}

	/**
	 * Obtiene la lista de valores de tipo referencia de la ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idCampo
	 *            Identificador del campo.
	 * @return Lista de valores.
	 */
	public List getValues(String idElementoCF, String idCampo, int tipoElemento) {
		StringBuffer qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(getCampoIdElemento(),
						idElementoCF))
				.append(" AND ")
				.append(DBUtils
						.generateEQTokenField(getCampoIdCampo(), idCampo));

		if (getCampoTipoElemento() != null
				&& tipoElemento != ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(getCampoTipoElemento(),
							tipoElemento));

		qual.append(" ORDER BY ").append(getCampoOrden().getName());

		return getVOS(qual.toString(), getTableName(), getHint(),
				getCustomColDefs(), CampoReferenciaVO.class);

	}

	/**
	 * Obtiene la lista de valores de tipo referencia de la ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idCampo
	 *            Identificador del campo.
	 * @return Lista de valores.
	 */
	public List getValues(String[] idsElementoCF, String idCampo,
			int tipoElemento) {
		StringBuffer qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(getCampoIdElemento(),
						idsElementoCF))
				.append(" AND ")
				.append(DBUtils
						.generateEQTokenField(getCampoIdCampo(), idCampo));

		if (getCampoTipoElemento() != null
				&& tipoElemento != ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(getCampoTipoElemento(),
							tipoElemento));

		qual.append(" ORDER BY ").append(getCampoIdElemento()).append(",")
				.append(getCampoOrden().getName());

		return getVOS(qual.toString(), getTableName(), getHint(),
				getCustomColDefs(), CampoReferenciaVO.class);

	}

	/**
	 * Obtiene el valor de la ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idCampo
	 *            Identificador del campo.
	 * @param orden
	 *            Orden del valor.
	 * @return Valor de la ficha.
	 */
	public CampoReferenciaVO getValue(String idElementoCF, String idCampo,
			int orden, int tipoElemento) {
		StringBuffer qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(getCampoIdElemento(),
						idElementoCF))
				.append(" AND ")
				.append(DBUtils
						.generateEQTokenField(getCampoIdCampo(), idCampo))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(getCampoOrden(), orden));

		if (getCampoTipoElemento() != null
				&& tipoElemento != ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(getCampoTipoElemento(),
							tipoElemento));

		return (CampoReferenciaVO) getVO(qual.toString(), getTableName(),
				getHint(), getCustomColDefs(), CampoReferenciaVO.class);
	}

	/**
	 * Inserta un valor de tipo referencia.
	 * 
	 * @param value
	 *            Valor de tipo referencia a insertar.
	 * @return Valor de tipo referencia insertado.
	 */
	public CampoReferenciaVO insertValue(final CampoReferenciaVO value) {
		try {
			DbConnection conn = getConnection();
			DbInsertFns.insert(conn, getTableName(),
					DbUtil.getColumnNames(getColDefs()),
					new SigiaDbInputRecord(getColDefs(), value));
			return value;
		} catch (Exception e) {
			logger.error(
					"Error insertando campo de tipo referencia para el elemento "
							+ value.getIdObjeto(), e);
			throw new DBException("insertando campo de tipo referencia", e);
		}
	}

	/**
	 * Modifica un valor de tipo referencia.
	 * 
	 * @param value
	 *            Información del campo de tipo referencia.
	 */
	public void updateValue(final CampoReferenciaVO value) {
		try {
			DbConnection conn = getConnection();
			final StringBuffer qual = new StringBuffer()
					.append(" WHERE ")
					.append(DBUtils.generateEQTokenField(getCampoIdElemento(),
							value.getIdObjeto()))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(getCampoIdCampo(),
							value.getIdCampo()))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(getCampoOrden(),
							value.getOrden()));

			if (value.getTipoElemento() != ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
				qual.append(DBUtils.AND).append(
						DBUtils.generateEQTokenField(getCampoTipoElemento(),
								value.getTipoElemento()));

			DbUpdateFns.update(conn, getTableName(), getColumnNamesList(),
					new SigiaDbInputRecord(getColDefs(), value),
					qual.toString());
		} catch (Exception e) {
			logger.error(
					"Error modificando campo de tipo referencia para el elemento "
							+ value.getIdObjeto(), e);
			throw new DBException("modificando campo de tipo referencia", e);
		}
	}

	/**
	 * Elimina un valor de tipo referencia.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idCampo
	 *            Identificador del campo.
	 * @param orden
	 *            Orden del valor.
	 */
	public void deleteValue(String idElementoCF, String idCampo, String orden,
			int tipoElemento) {
		try {
			DbConnection conn = getConnection();
			final StringBuffer qual = new StringBuffer()
					.append(DBUtils.WHERE)
					.append(DBUtils.generateEQTokenField(getCampoIdElemento(),
							idElementoCF))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(getCampoIdCampo(),
							idCampo));

			if (StringUtils.isNotBlank(orden))
				qual.append(DBUtils.AND).append(
						DBUtils.generateEQTokenField(getCampoOrden(), orden));

			if (tipoElemento != ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
				qual.append(DBUtils.AND).append(
						DBUtils.generateEQTokenField(getCampoTipoElemento(),
								tipoElemento));

			DbDeleteFns.delete(conn, getTableName(), qual.toString());
		} catch (Exception e) {
			logger.error(
					"Error eliminando campo de tipo referencia para el elemento "
							+ idElementoCF, e);
			throw new DBException("eliminando campo de tipo referencia", e);
		}
	}

	/**
	 * Elementos todos los valores de texto largo que pertenezcan al elementoCF
	 * pasado como parametro
	 * 
	 * @param idElementoCF
	 */
	public void deleteValueXIdElemento(String idElementoCF, int tipoElemento) {
		final StringBuffer qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(getCampoIdElemento(),
						idElementoCF));

		if (tipoElemento != ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(getCampoTipoElemento(),
							tipoElemento));

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, getTableName(), qual.toString());
			}
		};

		command.execute();

	}

	/**
	 * Obtiene el número de referencias a un objeto dado.
	 * 
	 * @param tipoObjRef
	 *            Tipo de objeto referenciado.
	 * @param idObjRef
	 *            Identificador del objeto referenciado.
	 * @return Número de referencias.
	 */
	public int countReferences(int tipoObjRef, String idObjRef) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE")
				.append(DBUtils.generateEQTokenField(getCampoTipoObjRef(),
						tipoObjRef))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(getCampoIdObjRef(),
						idObjRef));

		return getVOCount(qual.toString(), getTableName());
	}

	/**
	 * Obtiene el número de referencias a un objeto dado.
	 * 
	 * @param tipoObjRef
	 *            Tipo de objeto referenciado.
	 * @param idObjRef
	 *            Identificador del objeto referenciado.
	 * @return Número de referencias.
	 */
	public int countReferencesEnUdoc(int tipoObjRef, String idObjRef) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE")
				.append(DBUtils.generateEQTokenField(getCampoTipoObjRef(),
						tipoObjRef))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(getCampoIdObjRef(),
						idObjRef))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(getCampoIdElemento(),
						UnidadDocumentalDBEntityImpl.CAMPO_ID));

		return getVOCount(qual.toString(), getTableName(),
				getHintTipoObjIdObj());
	}

	/**
	 * Obtiene el número de referencias a los descriptores de una lista dada.
	 * 
	 * @param idListaDescriptora
	 *            Identificador de la lista descriptora.
	 * @return Número de referencias.
	 */
	public int countReferencesInList(String idListaDescriptora) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE")
				.append(DBUtils.generateEQTokenField(getCampoTipoObjRef(), 1))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(getCampoIdObjRef(),
						DescriptorDBEntityImpl.CAMPO_ID))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						DescriptorDBEntityImpl.CAMPO_IDLISTA,
						idListaDescriptora));

		return getVOCount(qual.toString(), getTableName() + ","
				+ DescriptorDBEntityImpl.TABLE_NAME, getHintTipoObjIdObj());
	}

	public void unificarDescriptor(String idDescriptor, String[] idsAReemplazar) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateInTokenField(getCampoIdObjRef(),
						idsAReemplazar)).toString();

		Map cols = Collections.singletonMap(getCampoIdObjRef(), idDescriptor);

		updateFields(qual, cols, getTableName());
	}

	public int getCountValues(String idElementoCF, String idCampo,
			int tipoElemento) {

		StringBuffer qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(getCampoIdElemento(),
						idElementoCF))
				.append(" AND ")
				.append(DBUtils
						.generateEQTokenField(getCampoIdCampo(), idCampo));

		if (tipoElemento != ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(getCampoTipoElemento(),
							tipoElemento));

		return getVOCount(qual.toString(), getTableName(), getHint());

	}

	public List getInteresadosByIdsElementosCF(String[] idsElementosCF) {

		// List interesados = new ArrayList();

		// Obtener los Identificadores de los campos.
		ConfiguracionDescripcion configDesc = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion();

		// String idCampoIdTerceroInteresado =
		// configDesc.getIdTerceroInteresado();
		String idCampoInteresadoIdentidad = configDesc.getInteresadoIdentidad();
		String idCampoInteresadoPrincipal = configDesc.getInteresadoPrincipal();
		String idCampoInteresadoNumeroIdentidad = configDesc
				.getInteresadoNumeroIdentidad();
		String idCampoInteresadoRol = configDesc.getInteresadoRol();
		String idCampoInteresadoValidado = configDesc.getInteresadoValidado();

		/*
		 * 
		 * SELECT R.IDELEMENTOCF, D.NOMBRE, P.VALOR AS PRINCIPAL, N.VALOR AS
		 * IDENTIDAD, RL.VALOR AS ROL, V.VALOR AS VALIDADO FROM ADVCREFCF R LEFT
		 * OUTER JOIN ADDESCRIPTOR D ON R.IDOBJREF = D.ID LEFT OUTER JOIN
		 * ADVCTEXTCF P ON R.IDELEMENTOCF = P.IDELEMENTOCF AND R.ORDEN = P.ORDEN
		 * AND P.IDCAMPO ='213' LEFT OUTER JOIN ADVCTEXTCF N ON R.IDELEMENTOCF =
		 * N.IDELEMENTOCF AND R.ORDEN = N.ORDEN AND N.IDCAMPO ='10' LEFT OUTER
		 * JOIN ADVCTEXTCF RL ON R.IDELEMENTOCF = RL.IDELEMENTOCF AND R.ORDEN =
		 * RL.ORDEN AND RL.IDCAMPO ='11' LEFT OUTER JOIN ADVCTEXTCF V ON
		 * R.IDELEMENTOCF = V.IDELEMENTOCF AND R.ORDEN = V.ORDEN AND V.IDCAMPO
		 * ='12' WHERE R.IDCAMPO = '9' AND R.IDELEMENTOCF IN(....)
		 */

		// Tabla
		// Tabla de Referencias
		String aliasTablaRefcf = "RF";
		TableDef tablaRefCf = new TableDef(getTableName(), aliasTablaRefcf);
		DbColumnDef colRidObjRef = new DbColumnDef(tablaRefCf,
				getCampoIdObjRef());
		DbColumnDef colRidElementocf = new DbColumnDef(tablaRefCf,
				getCampoIdElemento());
		DbColumnDef colRidCampo = new DbColumnDef(tablaRefCf, getCampoIdCampo());
		DbColumnDef colRorden = new DbColumnDef(tablaRefCf, getCampoOrden());

		// COLUMNA NOMBRE
		String aliasTablaDescriptor = "DES";
		TableDef tablaDescriptor = new TableDef(
				DescriptorDBEntityImpl.TABLE_NAME, aliasTablaDescriptor);

		DbColumnDef colDIdDescriptor = new DbColumnDef(tablaDescriptor,
				DescriptorDBEntityImpl.CAMPO_ID);
		DbColumnDef colDnombre = new DbColumnDef(tablaDescriptor,
				DescriptorDBEntityImpl.CAMPO_NOMBRE);

		JoinDefinition joinDescriptor = new JoinDefinition(colRidObjRef,
				colDIdDescriptor);

		// COLUMNA PRINCIPAL
		String aliasTablaPrincipal = "PPAL";
		TableDef tablaPrincipal = new TableDef(
				TextoCortoDBEntityImpl.TABLE_NAME, aliasTablaPrincipal);

		DbColumnDef colIdElementoCFPrincipal = new DbColumnDef(tablaPrincipal,
				TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO);
		DbColumnDef colOrdenPrincipal = new DbColumnDef(tablaPrincipal,
				TextoCortoDBEntityImpl.CAMPO_ORDEN);
		DbColumnDef colIdCampoPrincipal = new DbColumnDef(tablaPrincipal,
				TextoCortoDBEntityImpl.CAMPO_ID_CAMPO);
		DbColumnDef colValorCampoPrincipal = new DbColumnDef(tablaPrincipal,
				TextoCortoDBEntityImpl.CAMPO_VALOR);

		StringBuffer sqlAppendJoinPrincipal = new StringBuffer(DBUtils.AND)
				.append(DBUtils.generateEQTokenField1(colRorden,
						colOrdenPrincipal))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(colIdCampoPrincipal,
						idCampoInteresadoPrincipal));

		JoinDefinition joinPrincipal = new JoinDefinition(colRidElementocf,
				colIdElementoCFPrincipal, sqlAppendJoinPrincipal.toString());

		// COLUMNA Nº IDENTIDAD
		String aliasTablaNumIdentidad = "NI";
		TableDef tablaNumIdentidad = new TableDef(
				TextoCortoDBEntityImpl.TABLE_NAME, aliasTablaNumIdentidad);

		DbColumnDef colIdElementoCFNumIdentidad = new DbColumnDef(
				tablaNumIdentidad, TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO);
		DbColumnDef colOrdenNumIdentidad = new DbColumnDef(tablaNumIdentidad,
				TextoCortoDBEntityImpl.CAMPO_ORDEN);
		DbColumnDef colIdCampoNumIdentidad = new DbColumnDef(tablaNumIdentidad,
				TextoCortoDBEntityImpl.CAMPO_ID_CAMPO);
		DbColumnDef colValorCampoNumIdentidad = new DbColumnDef(
				tablaNumIdentidad, TextoCortoDBEntityImpl.CAMPO_VALOR);

		StringBuffer sqlAppendJoinNumIdentidad = new StringBuffer(DBUtils.AND)
				.append(DBUtils.generateEQTokenField1(colRorden,
						colOrdenNumIdentidad))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(colIdCampoNumIdentidad,
						idCampoInteresadoNumeroIdentidad));

		JoinDefinition joinNumIdentidad = new JoinDefinition(colRidElementocf,
				colIdElementoCFNumIdentidad,
				sqlAppendJoinNumIdentidad.toString());

		// Campo Rol
		String aliasTablaRol = "ROL";
		TableDef tablaRol = new TableDef(TextoCortoDBEntityImpl.TABLE_NAME,
				aliasTablaRol);

		DbColumnDef colIdElementoCFRol = new DbColumnDef(tablaRol,
				TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO);
		DbColumnDef colOrdenRol = new DbColumnDef(tablaRol,
				TextoCortoDBEntityImpl.CAMPO_ORDEN);
		DbColumnDef colIdCampoRol = new DbColumnDef(tablaRol,
				TextoCortoDBEntityImpl.CAMPO_ID_CAMPO);
		DbColumnDef colValorCampoRol = new DbColumnDef(tablaRol,
				TextoCortoDBEntityImpl.CAMPO_VALOR);

		StringBuffer sqlAppendJoinRol = new StringBuffer(DBUtils.AND)
				.append(DBUtils.generateEQTokenField1(colRorden, colOrdenRol))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(colIdCampoRol,
						idCampoInteresadoRol));

		JoinDefinition joinRol = new JoinDefinition(colRidElementocf,
				colIdElementoCFRol, sqlAppendJoinRol.toString());

		// Campo Validado
		String aliasTablaValidado = "VAL";
		TableDef tablaValidado = new TableDef(
				TextoCortoDBEntityImpl.TABLE_NAME, aliasTablaValidado);

		DbColumnDef colIdElementoCFValidado = new DbColumnDef(tablaValidado,
				TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO);
		DbColumnDef colOrdenValidado = new DbColumnDef(tablaValidado,
				TextoCortoDBEntityImpl.CAMPO_ORDEN);
		DbColumnDef colIdCampoValidado = new DbColumnDef(tablaValidado,
				TextoCortoDBEntityImpl.CAMPO_ID_CAMPO);
		DbColumnDef colValorCampoValidado = new DbColumnDef(tablaValidado,
				TextoCortoDBEntityImpl.CAMPO_VALOR);

		StringBuffer sqlAppendJoinValidado = new StringBuffer(DBUtils.AND)
				.append(DBUtils.generateEQTokenField1(colRorden,
						colOrdenValidado))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(colIdCampoValidado,
						idCampoInteresadoValidado));

		JoinDefinition joinValidado = new JoinDefinition(colRidElementocf,
				colIdElementoCFValidado, sqlAppendJoinValidado.toString());

		// DbColumnDef columnaNombreArchivo = new DbColumnDef("nombre", new
		// TableDef(ArchivoDbEntityImpl.TABLE_NAME,ArchivoDbEntityImpl.TABLE_NAME),
		// ArchivoDbEntityImpl.NOMBRE_COLUMN_NAME,
		// ArchivoDbEntityImpl.NOMBRE_FIELD.getDataType(),true);

		// DbColumnDef colNombre = DBUtils.getCustomizedField(colDnombre,
		// "nombre");
		// DbColumnDef colPrincipal =
		// DBUtils.getCustomizedField(colValorCampoPrincipal, "principal");
		// DbColumnDef colNumIdentidad =
		// DBUtils.getCustomizedField(colValorCampoNumIdentidad,
		// "numeroIdentificacion");
		// DbColumnDef colValidado =
		// DBUtils.getCustomizedField(colValorCampoValidado, "validado");
		// DbColumnDef colRol =
		// DBUtils.getCustomizedField(colValorCampoValidado, "rol");

		// DbColumnDef colNombre=new DbColumnDef("nombre",tablaDescriptor,
		// ,,true);

		DbColumnDef colIdElementocf = new DbColumnDef("idelementocf",
				tablaRefCf, colRidElementocf.getName(),
				colRidElementocf.getDataType(), colRidElementocf.isNullable());
		DbColumnDef colNombre = new DbColumnDef("nombre", tablaDescriptor,
				colDnombre.getName(), colDnombre.getDataType(),
				colDnombre.isNullable());
		DbColumnDef colPrincipal = new DbColumnDef("principal", tablaPrincipal,
				colValorCampoPrincipal.getName(),
				colValorCampoPrincipal.getDataType(),
				colValorCampoPrincipal.isNullable());
		DbColumnDef colNumIdentidad = new DbColumnDef("numeroIdentificacion",
				tablaNumIdentidad, colValorCampoNumIdentidad.getName(),
				colValorCampoNumIdentidad.getDataType(),
				colValorCampoNumIdentidad.isNullable());
		DbColumnDef colValidado = new DbColumnDef("validado", tablaValidado,
				colValorCampoValidado.getName(),
				colValorCampoValidado.getDataType(),
				colValorCampoValidado.isNullable());
		DbColumnDef colRol = new DbColumnDef("rol", tablaRol,
				colValorCampoRol.getName(), colValorCampoRol.getDataType(),
				colValorCampoRol.isNullable());

		// DbColumnDef colNombre = new
		// DbColumnDef(colDnombre.getBindPropertyVO(), "nombre" ,
		// colDnombre.getName(),colDnombre.getDataType(),
		// colDnombre.getMaxLen(),colDnombre.isNullable());
		// DbColumnDef colPrincipal = new DbColumnDef(
		// colValorCampoPrincipal.getBindPropertyVO(), "principal" ,
		// colValorCampoPrincipal.getName(),
		// colValorCampoPrincipal.getDataType(),
		// colValorCampoPrincipal.getMaxLen(),
		// colValorCampoPrincipal.isNullable());
		// DbColumnDef colNumIdentidad = new DbColumnDef(
		// colValorCampoNumIdentidad.getBindPropertyVO(), "numeroIdentificacion"
		// , colValorCampoNumIdentidad.getName(),
		// colValorCampoNumIdentidad.getDataType(),
		// colValorCampoNumIdentidad.getMaxLen(),
		// colValorCampoNumIdentidad.isNullable());
		// DbColumnDef colValidado = new DbColumnDef(
		// colValorCampoNumIdentidad.getBindPropertyVO(), "validado" ,
		// colValorCampoValidado.getName(), colValorCampoValidado.getDataType(),
		// colValorCampoValidado.getMaxLen(),
		// colValorCampoValidado.isNullable());
		// DbColumnDef colRol = new DbColumnDef(
		// colValorCampoValidado.getBindPropertyVO(), "rol" ,
		// colValorCampoRol.getName(), colValorCampoRol.getDataType(),
		// colValorCampoRol.getMaxLen(), colValorCampoRol.isNullable());

		// DbColumnDef(colDnombre.getBindPropertyVO(), "nombre" ,
		// colDnombre.getName(),colDnombre.getDataType(),
		// colDnombre.getMaxLen(),colDnombre.isNullable());

		DbColumnDef[] columnas = new DbColumnDef[] { colIdElementocf,
				colNombre, colPrincipal, colNumIdentidad, colValidado, colRol };

		JoinDefinition[] joins = new JoinDefinition[] { joinDescriptor,
				joinPrincipal, joinNumIdentidad, joinValidado, joinRol };

		StringBuffer sqlFrom = new StringBuffer().append(DBUtils
				.generateLeftOuterJoinCondition(tablaRefCf, joins));

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(colRidCampo,
						idCampoInteresadoIdentidad))
				.append(DBUtils.AND)
				.append("(")
				.append(DBUtils.generateORTokens(colRidElementocf,
						idsElementosCF)).append(")");

		return getVOS(qual.toString(), sqlFrom.toString(), columnas,
				InteresadoUdocVO.class);

	}

	public List getProductoresByIdsElementosCF(String[] idsElementosCF) {

		// Obtener los Identificadores de los campos.
		ConfiguracionDescripcion configDesc = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion();

		String idCampoProductor = configDesc.getProductor();

		/*
		 * 
		 * SELECT ADVCREFCF.idelementocf,ADDESCRIPTOR.nombre FROM ADVCREFCF
		 * ADVCREFCF, ADDESCRIPTOR ADDESCRIPTOR WHERE ADVCREFCF.idcampo= '16'
		 * AND((ADVCREFCF.idelementocf IN (...))) AND
		 * ADVCREFCF.idobjref=ADDESCRIPTOR.id
		 */

		TableDef tableReferencia = new TableDef(getTableName());
		TableDef tableDescriptor = new TableDef(
				DescriptorDBEntityImpl.TABLE_NAME);

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(getCampoIdCampo(),
						idCampoProductor))
				.append(DBUtils.AND)
				.append(DBUtils.generateInTokenField(getCampoIdElemento(),
						idsElementosCF))
				.append(DBUtils.AND)
				.append(DBUtils.generateJoinCondition(getCampoIdObjRef(),
						DescriptorDBEntityImpl.CAMPO_ID));

		StringBuffer sqlFrom = new StringBuffer().append(DBUtils
				.generateTableSet(new String[] {
						tableReferencia.getDeclaration(),
						tableDescriptor.getDeclaration() }));

		DbColumnDef colIdElemento = new DbColumnDef(
				ReferenciaDBEntityImpl.IDELEMENTOCF_COLUMN_NAME,
				getCampoIdElemento());
		DbColumnDef[] cols = new DbColumnDef[] { colIdElemento,
				DescriptorDBEntityImpl.CAMPO_NOMBRE };

		return getVOS(qual.toString(), sqlFrom.toString(), cols,
				ProductorElementoCF.class);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see descripcion.db.IReferenciaDBEntity#getProductor(java.lang.String,
	 * java.lang.String)
	 */
	public CampoReferenciaVO getProductor(String idElemento,
			String idCampoProductor) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(getCampoIdCampo(),
						idCampoProductor))
				.append(DBUtils.AND)
				.append(DBUtils.generateInTokenField(getCampoIdElemento(),
						idElemento));

		return (CampoReferenciaVO) getVO(qual.toString(), getTableName(),
				getHint(), getCustomColDefs(), CampoReferenciaVO.class);

	}

	public int getMaxOrden(String idElemento, String idCampo) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(getCampoIdElemento(),
						idElemento))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(getCampoIdCampo(), idCampo));

		return getMax(qual.toString(), getTableName(), getCampoOrden());
	}

}
