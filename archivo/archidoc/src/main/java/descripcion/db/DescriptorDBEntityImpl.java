package descripcion.db;

import fondos.db.OrganoProductorDBEntity;
import fondos.db.oracle.ProductorSerieDBEntityImpl;
import gcontrol.db.CAOrganoDbEntityImpl;
import gcontrol.db.ListaControlAccesoDbEntityImpl;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import se.usuarios.GenericUserInfo;
import transferencias.db.RelacionEntregaDBEntityBaseImpl;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.SigiaDbInputRecord;
import common.db.TableDef;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;
import common.util.ArrayUtils;
import common.util.CustomDateFormat;
import common.util.DateQualifierHelper;
import common.vos.ConsultaConnectBy;

import descripcion.model.TipoDescriptor;
import descripcion.vos.AutoridadVO;
import descripcion.vos.BusquedaDescriptoresVO;
import descripcion.vos.BusquedaGeneralAutVO;
import descripcion.vos.DescriptorVO;

/**
 * DBEntity para acceder a la tabla ADDESCRIPTOR.
 */
public class DescriptorDBEntityImpl extends DBEntity implements
		IDescriptorDBEntity {

	/** Nombre de la tabla. */
	public static final String TABLE_NAME = "ADDESCRIPTOR";

	/** Nombre de columnas */
	public static final String ID_COLUMN_NAME = "ID";
	public static final String NOMBRE_COLUMN_NAME = "NOMBRE";
	public static final String ID_LISTA_COLUMN_NAME = "IDLISTA";
	public static final String ESTADO_COLUMN_NAME = "ESTADO";
	public static final String ID_DESCR_SIST_EXT_COLUMN_NAME = "IDDESCRSISTEXT";
	public static final String TIPO_COLUMN_NAME = "TIPO";
	public static final String TIMESTAMP_COLUMN_NAME = "TIMESTAMP";
	public static final String ID_FICHA_DESCR_COLUMN_NAME = "IDFICHADESCR";
	public static final String TIENE_DESCR_COLUMN_NAME = "TIENEDESCR";
	public static final String EDITCLFDOCS_COLUMN_NAME = "EDITCLFDOCS";
	public static final String ID_SIST_EXT_COLUMN_NAME = "IDSISTEXT";
	public static final String NIVEL_ACCESO_COLUMN_NAME = "NIVELACCESO";
	public static final String ID_LCA_COLUMN_NAME = "IDLCA";
	public static final String ID_REP_ECM_COLUMN_NAME = "IDREPECM";
	public static final String IDXNOMBRE_COLUMN_NAME = DBEntity.LONG_TEXT_POSTGRES_PREFFIX
			+ "NOMBRE";

	/** Definiciones de columnas */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);
	public static final DbColumnDef CAMPO_IDLISTA = new DbColumnDef(null,
			TABLE_NAME, ID_LISTA_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_ESTADO = new DbColumnDef(null,
			TABLE_NAME, ESTADO_COLUMN_NAME, DbDataType.LONG_INTEGER, false);
	public static final DbColumnDef CAMPO_ID_DESCR_SIST_EXT = new DbColumnDef(
			null, TABLE_NAME, ID_DESCR_SIST_EXT_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_TIPO = new DbColumnDef(null,
			TABLE_NAME, TIPO_COLUMN_NAME, DbDataType.LONG_INTEGER, false);
	public static final DbColumnDef CAMPO_TIMESTAMP = new DbColumnDef(null,
			TABLE_NAME, TIMESTAMP_COLUMN_NAME, DbDataType.DATE_TIME, false);
	public static final DbColumnDef CAMPO_ID_FICHA_DESCR = new DbColumnDef(
			null, TABLE_NAME, ID_FICHA_DESCR_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_TIENE_DESCR = new DbColumnDef(null,
			TABLE_NAME, TIENE_DESCR_COLUMN_NAME, DbDataType.SHORT_TEXT, 1,
			false);
	public static final DbColumnDef CAMPO_EDITCLFDOCS = new DbColumnDef(null,
			TABLE_NAME, EDITCLFDOCS_COLUMN_NAME, DbDataType.SHORT_TEXT, 1,
			false);
	public static final DbColumnDef CAMPO_ID_SIST_EXT = new DbColumnDef(null,
			TABLE_NAME, ID_SIST_EXT_COLUMN_NAME, DbDataType.SHORT_TEXT, 64,
			false);
	public static final DbColumnDef CAMPO_NIVEL_ACCESO = new DbColumnDef(null,
			TABLE_NAME, NIVEL_ACCESO_COLUMN_NAME, DbDataType.LONG_INTEGER,
			false);
	public static final DbColumnDef CAMPO_ID_LCA = new DbColumnDef(null,
			TABLE_NAME, ID_LCA_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_ID_REP_ECM = new DbColumnDef(null,
			TABLE_NAME, ID_REP_ECM_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);
	public static final DbColumnDef IDXNOMBRE_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDXNOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 128,
			false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COLS_DEFS = { CAMPO_ID, CAMPO_NOMBRE,
			CAMPO_IDLISTA, CAMPO_ESTADO, CAMPO_ID_DESCR_SIST_EXT, CAMPO_TIPO,
			CAMPO_TIMESTAMP, CAMPO_ID_FICHA_DESCR, CAMPO_TIENE_DESCR,
			CAMPO_EDITCLFDOCS, CAMPO_ID_SIST_EXT, CAMPO_NIVEL_ACCESO,
			CAMPO_ID_LCA, CAMPO_ID_REP_ECM };

	/** Lista de nombres de columnas. */
	public static final String COL_NAMES = DbUtil.getColumnNames(COLS_DEFS);

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
	public DescriptorDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public DescriptorDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la información de un descriptor.
    *
	 * @param idDescriptor
	 *            Identificador del descriptor.
	 * @return Descriptor.
	 */
	public DescriptorVO getDescriptor(String idDescriptor) {
		String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_ID, idDescriptor))
				.toString();

		return (DescriptorVO) getVO(qual, TABLE_NAME, COLS_DEFS,
				DescriptorVO.class);
	}

	/**
	 * Obtiene la información de un descriptor con información extendida.
    *
	 * @param serviceClient
	 *            Cliente del servicio.
	 * @param idDescriptor
	 *            Identificador del descriptor.
	 * @return Descriptor.
	 */
	public DescriptorVO getDescriptorExt(GenericUserInfo serviceClient,
			String idDescriptor) {
		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(CAMPO_ID, idDescriptor));

		TableDef tablaDescriptor = new TableDef(TABLE_NAME);
		JoinDefinition[] joins = new JoinDefinition[] {

				new JoinDefinition(new DbColumnDef(tablaDescriptor,
						CAMPO_ID_FICHA_DESCR), new DbColumnDef(new TableDef(
						FichaDBEntityImpl.TABLE_NAME),
						FichaDBEntityImpl.CAMPO_ID)),
				new JoinDefinition(new DbColumnDef(tablaDescriptor,
						CAMPO_IDLISTA), new DbColumnDef(new TableDef(
						CatalogoListaDescriptoresDBEntityImpl.TABLE_NAME),
						CatalogoListaDescriptoresDBEntityImpl.CAMPO_ID)),
				new JoinDefinition(
						new DbColumnDef(tablaDescriptor, CAMPO_ID_LCA),
						new DbColumnDef(
								new TableDef(
										ListaControlAccesoDbEntityImpl.TABLE_NAME_LISTA_CONTROL),
								ListaControlAccesoDbEntityImpl.CAMPO_ID))

		};
		StringBuffer sqlFrom = new StringBuffer();
		sqlFrom.append(DBUtils.generateLeftOuterJoinCondition(tablaDescriptor,
				joins));

		// Comprobación de permisos sobre el elemento
		DBUtils.addPermissionsCheckingClauses(serviceClient, qual,
				CAMPO_NIVEL_ACCESO, null, CAMPO_ID_LCA);

		DbColumnDef[] COLS_DEF_QUERY = (DbColumnDef[]) ArrayUtils
				.concat(new DbColumnDef[] {
						new DbColumnDef(new TableDef(
								FichaDBEntityImpl.TABLE_NAME),
								FichaDBEntityImpl.CAMPO_NOMBRE_FICHA_DESCR),
						new DbColumnDef(
								new TableDef(
										CatalogoListaDescriptoresDBEntityImpl.TABLE_NAME),
								CatalogoListaDescriptoresDBEntityImpl.CAMPO_NOMBRE_LISTA),
						new DbColumnDef(
								new TableDef(
										ListaControlAccesoDbEntityImpl.TABLE_NAME_LISTA_CONTROL),
								ListaControlAccesoDbEntityImpl.CAMPO_NOMBRE_LCA) },
						COLS_DEFS);

		return (DescriptorVO) getVO(qual.toString(), sqlFrom.toString(),
				COLS_DEF_QUERY, DescriptorVO.class);
	}

	/**
	 * Obtiene la información de un descriptor.
    *
	 * @param idListaDescriptora
	 *            Identificador de la lista descriptora.
	 * @param nombre
	 *            Nombre del descriptor.
	 * @return Descriptor.
	 */
	public DescriptorVO getDescriptor(String idListaDescriptora, String nombre) {
		String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDLISTA,
						idListaDescriptora))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_NOMBRE,
						getValorCampo(nombre))).toString();

		return (DescriptorVO) getVO(qual, TABLE_NAME, COLS_DEFS,
				DescriptorVO.class);
	}

	/**
    * Obtiene la información de un descriptor.
    *
    * @param idListaDescriptora
    *            Identificador de la lista descriptora.
    * @param nombre
    *            Nombre del descriptor.
    * @return Descriptor.
    */
	public DescriptorVO getDescriptor(String idListaDescriptora, String nombre, String idSistExt, String idDescrSistExt) {
		String qual = new StringBuilder()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDLISTA,
						idListaDescriptora))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_NOMBRE,
						getValorCampo(nombre)))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_ID_SIST_EXT,
						getValorCampo(idSistExt)))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_ID_DESCR_SIST_EXT,
						getValorCampo(idDescrSistExt))).toString();


		return (DescriptorVO) getVO(qual, TABLE_NAME, COLS_DEFS,
				DescriptorVO.class);
	}

	/**
	 * Reemplaza el valor de la comilla Simple por doble comilla simple
    *
	 * @param cadena
	 *            Cadena a Reemplazar.
	 * @return
	 */
	public String getValorCampo(String cadena) {
		String COMILLA = "'";
		String[] partes = cadena.split(COMILLA);

		cadena = partes[0];

		for (int i = 1; i < partes.length; i++) {
			cadena += COMILLA + COMILLA + partes[i];
		}
		return cadena;
	}

	/**
	 * Obtiene la lista de descriptores de una lista descriptora.
    *
	 * @param serviceClient
	 *            Cliente del servicio.
	 * @param idListaDescriptora
	 *            Identificador de la lista descriptora.
	 * @return Lista de descriptores.
	 */
	public List getDescriptores(GenericUserInfo serviceClient,
			String idListaDescriptora) {
		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDLISTA,
						idListaDescriptora));

		// Comprobación de permisos sobre el elemento
		DBUtils.addPermissionsCheckingClauses(serviceClient, qual,
				CAMPO_NIVEL_ACCESO, null, CAMPO_ID_LCA);

		qual.append(DBUtils.generateOrderBy(CAMPO_NOMBRE));

		return getVOS(qual.toString(), TABLE_NAME, COLS_DEFS,
				DescriptorVO.class);
	}

	/**
	 * Recupera los descriptores cuyo valor contiene el patrón de búsqueda
	 * indicado
    *
	 * @param pattern
	 *            Patrón que debe contener el valor de los descriptores
	 * @param idListaDescriptora
	 *            Identificador de la lista descriptora a la que deben
	 *            pertenecer los descriptores. Pude ser nulo.
	 * @return Conjunto de descriptores que verifican las condiciones
	 *         {@link DescriptorVO}
	 */
	public List findDescriptores(String pattern, String idListaDescriptora) {
		StringBuffer qual = new StringBuffer("WHERE 1=1 ");
		if (StringUtils.isNotBlank(pattern))
			qual.append(DBUtils.AND)
					.append(DBUtils.generateLikeTokenField(CAMPO_NOMBRE,
							pattern, true));
		if (idListaDescriptora != null)
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(CAMPO_IDLISTA,
							idListaDescriptora));

		return getVOS(qual.toString(), TABLE_NAME, COLS_DEFS,
				DescriptorVO.class);

	}

	/**
	 * Obtiene la lista de descriptores de una lista descriptora con información
	 * extendida.
    *
	 * @param serviceClient
	 *            Cliente del servicio.
	 * @param idListaDescriptora
	 *            Identificador de la lista descriptora.
	 * @param pageInfo
	 *            Información de la paginación.
	 * @return Lista de descriptores.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el m
	 */
	public List getDescriptoresExt(GenericUserInfo serviceClient,
			String idListaDescriptora, PageInfo pageInfo)
			throws TooManyResultsException {
		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDLISTA,
						idListaDescriptora));

		// Comprobación de permisos sobre el elemento
		DBUtils.addPermissionsCheckingClauses(serviceClient, qual,
				CAMPO_NIVEL_ACCESO, null, CAMPO_ID_LCA);

		TableDef tablaDescriptor = new TableDef(TABLE_NAME);
		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(tablaDescriptor, CAMPO_ID_FICHA_DESCR),
				new DbColumnDef(new TableDef(FichaDBEntityImpl.TABLE_NAME),
						FichaDBEntityImpl.CAMPO_ID)) };
		StringBuffer sqlFrom = new StringBuffer();
		sqlFrom.append(DBUtils.generateLeftOuterJoinCondition(tablaDescriptor,
				joins));

		DbColumnDef[] COLS_DEF_QUERY = (DbColumnDef[]) ArrayUtils.concat(
				new DbColumnDef[] { new DbColumnDef(new TableDef(
						FichaDBEntityImpl.TABLE_NAME),
						FichaDBEntityImpl.CAMPO_NOMBRE_FICHA_DESCR) },
				COLS_DEFS);

		if (pageInfo != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("nombre", CAMPO_NOMBRE);
			criteriosOrdenacion.put("estado", CAMPO_ESTADO);
			criteriosOrdenacion.put("fecha", CAMPO_TIMESTAMP);
			criteriosOrdenacion.put("nombreFichaDescr",
					FichaDBEntityImpl.CAMPO_NOMBRE_FICHA_DESCR);
			criteriosOrdenacion.put("tieneDescr", CAMPO_TIENE_DESCR);
			criteriosOrdenacion.put("volumen", CAMPO_ID_REP_ECM);

			return getVOS(qual.toString(), sqlFrom.toString(), COLS_DEF_QUERY,
					pageInfo.getOrderBy(criteriosOrdenacion),
					DescriptorVO.class, pageInfo);
		} else {
			qual.append(" ORDER BY ").append(CAMPO_NOMBRE.getQualifiedName());

			return getVOS(qual.toString(), sqlFrom.toString(), COLS_DEF_QUERY,
					DescriptorVO.class);
		}
	}

	/**
	 * Obtiene una lista de descriptores a partir de su nombre.
    *
	 * @param nombre
	 *            Nombre del descriptor.
	 * @return Lista de descriptores.
	 */
	public List getDescriptoresXNombre(String nombre) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_NOMBRE, nombre))
				.toString();

		return getVOS(qual, TABLE_NAME, COLS_DEFS, DescriptorVO.class);
	}

	public DescriptorVO getDescriptorXNombreYIdLista(String nombre,
			String idLista) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_NOMBRE, nombre))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDLISTA, idLista))
				.toString();

		return (DescriptorVO) getVO(qual, TABLE_NAME, COLS_DEFS,
				DescriptorVO.class);
	}

	/**
	 * Busca los descriptores en función de unos criterios.
    *
	 * @param serviceClient
	 *            Cliente del servicio.
	 * @param criterios
	 *            Criterios de búsqueda.
	 * @return Lista de descriptores.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getDescriptores(GenericUserInfo serviceClient,
			BusquedaDescriptoresVO criterios) throws TooManyResultsException {
		StringBuffer qual = new StringBuffer();

		// Identificador de la lista de descriptores
		if (StringUtils.isNotBlank(criterios.getIdLista()))
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateEQTokenField(CAMPO_IDLISTA,
							criterios.getIdLista()));

		// Nombre del descriptor

		if (StringUtils.isNotBlank(criterios.getNombre()))
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateLikeFieldQualified(CAMPO_NOMBRE,
							criterios.getNombre(), null,
							criterios.getCualificadorNombre(), true));

		// Estado
		if (criterios.getEstado() > 0)
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateEQTokenField(CAMPO_ESTADO,
							criterios.getEstado()));

		// Comprobación de permisos sobre el elemento: se ha hecho una función
		// específica aquí porque para el caso de los descriptores deben salir
		// todos siempre y cuando el usuario pertenezca a algún archivo y estos
		// descriptores tengan nivel de archivo
		DBUtils.addPermissionsCheckingClausesArchiveUser(serviceClient, qual,
				CAMPO_NIVEL_ACCESO, CAMPO_ID_LCA);

		TableDef tablaDescriptor = new TableDef(TABLE_NAME);
		JoinDefinition[] joins = new JoinDefinition[] {

				new JoinDefinition(new DbColumnDef(tablaDescriptor,
						CAMPO_ID_FICHA_DESCR), new DbColumnDef(new TableDef(
						FichaDBEntityImpl.TABLE_NAME),
						FichaDBEntityImpl.CAMPO_ID)),
				new JoinDefinition(new DbColumnDef(tablaDescriptor,
						CAMPO_IDLISTA), new DbColumnDef(new TableDef(
						CatalogoListaDescriptoresDBEntityImpl.TABLE_NAME),
						CatalogoListaDescriptoresDBEntityImpl.CAMPO_ID)),
				new JoinDefinition(
						new DbColumnDef(tablaDescriptor, CAMPO_ID_LCA),
						new DbColumnDef(
								new TableDef(
										ListaControlAccesoDbEntityImpl.TABLE_NAME_LISTA_CONTROL),
								ListaControlAccesoDbEntityImpl.CAMPO_ID))

		};
		StringBuffer sqlFrom = new StringBuffer();
		sqlFrom.append(DBUtils.generateLeftOuterJoinCondition(tablaDescriptor,
				joins));

		DbColumnDef[] COLS_DEF_QUERY = (DbColumnDef[]) ArrayUtils
				.concat(new DbColumnDef[] {
						new DbColumnDef(new TableDef(
								FichaDBEntityImpl.TABLE_NAME),
								FichaDBEntityImpl.CAMPO_NOMBRE_FICHA_DESCR),
						new DbColumnDef(
								new TableDef(
										CatalogoListaDescriptoresDBEntityImpl.TABLE_NAME),
								CatalogoListaDescriptoresDBEntityImpl.CAMPO_NOMBRE_LISTA),
						new DbColumnDef(
								new TableDef(
										ListaControlAccesoDbEntityImpl.TABLE_NAME_LISTA_CONTROL),
								ListaControlAccesoDbEntityImpl.CAMPO_NOMBRE_LCA) },
						COLS_DEFS);

		if (criterios.getPageInfo() != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("nombre", CAMPO_NOMBRE);
			criteriosOrdenacion.put("nombreLista",
					CatalogoListaDescriptoresDBEntityImpl.CAMPO_NOMBRE_LISTA);
			criteriosOrdenacion.put("estado", CAMPO_ESTADO);
			criteriosOrdenacion.put("fecha", CAMPO_TIMESTAMP);
			criteriosOrdenacion.put("nombreFichaDescr",
					FichaDBEntityImpl.CAMPO_NOMBRE_FICHA_DESCR);
			criteriosOrdenacion.put("tieneDescr", CAMPO_TIENE_DESCR);
			criteriosOrdenacion.put("volumen", CAMPO_ID_REP_ECM);

			return getVOS(qual.toString(), sqlFrom.toString(), COLS_DEF_QUERY,
					criterios.getPageInfo().getOrderBy(criteriosOrdenacion),
					DescriptorVO.class, criterios.getPageInfo());
		} else {
			qual.append(" ORDER BY ").append(CAMPO_NOMBRE.getQualifiedName());

			return getVOS(qual.toString(), sqlFrom.toString(), COLS_DEF_QUERY,
					DescriptorVO.class);
		}
	}

	/**
	 * Inserta un descriptor.
    *
	 * @param descriptor
	 *            Descriptor.
	 * @return Descriptor insertado.
	 */
	public DescriptorVO insertDescriptorVO(final DescriptorVO descriptorVO) {
		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				descriptorVO.setId(getGuid(descriptorVO.getId()));
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEFS, descriptorVO);
				DbInsertFns.insert(conn, TABLE_NAME, COL_NAMES, inputRecord);
			}

		};

		command.execute();

		return descriptorVO;
	}

	/**
	 * Modifica un descriptor.
    *
	 * @param id
	 *            Identificador del descriptor.
	 * @param columnsToUpdate
	 *            Columnas a modificar.
	 */
	public void updateDescriptorVOXId(String id, Map columnsToUpdate) {
		String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		updateFields(qual, columnsToUpdate, TABLE_NAME);
	}

	/**
	 * Modifica un descriptor.
    *
	 * @param descriptor
	 *            Descriptor.
	 */
	public void updateDescriptorVO(DescriptorVO descriptorVO) {
		String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_ID,
						descriptorVO.getId())).toString();

		updateVO(qual, TABLE_NAME, COLS_DEFS, descriptorVO);
	}

	/**
	 * Establece si un descriptor tiene ficha de descripción.
    *
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @param tieneDescr
	 *            Si el descriptor tiene ficha de descripción.
	 */
	public void setTieneDescr(String idDescr, String tieneDescr) {
		String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_ID, idDescr))
				.toString();

		HashMap columnsToUpdate = new HashMap();
		columnsToUpdate.put(CAMPO_TIENE_DESCR, tieneDescr);
		columnsToUpdate.put(CAMPO_TIMESTAMP, new Date());

		updateFields(qual, columnsToUpdate, TABLE_NAME);
	}

	/**
	 * Establece si se han creado los clasificadores por defecto de un
	 * descriptor.
    *
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @param editClfDocs
	 *            Si se han creado los clasificadores por defecto.
	 */
	public void setEditClfDocs(String idDescr, String editClfDocs) {
		String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_ID, idDescr))
				.toString();

		HashMap columnsToUpdate = new HashMap();
		columnsToUpdate.put(CAMPO_EDITCLFDOCS, editClfDocs);
		columnsToUpdate.put(CAMPO_TIMESTAMP, new Date());

		updateFields(qual, columnsToUpdate, TABLE_NAME);
	}

	/**
	 * Elimina un descriptor.
    *
	 * @param id
	 *            Identificador del descriptor.
	 */
	public void deleteDescriptor(String id) {
		String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		if (logger.isDebugEnabled())
			logger.debug("Se va a eliminar el descriptor con id: " + id);

		deleteVO(qual, TABLE_NAME);
	}

	/**
	 * Elimina los descriptores.
    *
	 * @param listaIds
	 *            Lista de identificadores de descriptores.
	 */
	public void deleteDescriptores(String[] listaIds) {
		String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(CAMPO_ID, listaIds))
				.toString();

		if (logger.isDebugEnabled())
			logger.debug("Se van a eliminar los descriptores con ids: "
					+ listaIds);

		deleteVO(qual, TABLE_NAME);
	}

	/**
	 * Realiza la búsqueda de autoridades en función de los parámetros del
	 * formulario de búsquedas.
    *
	 * @param serviceClient
	 *            Cliente del servicio.
	 * @param busqueda
	 *            Parámetros del formulario de búsquedas.
	 * @return Lista de autoridades.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getAutoridades(GenericUserInfo serviceClient,
			BusquedaGeneralAutVO busqueda) throws TooManyResultsException {
		HashMap pairsTableNameColsDefs = new HashMap();
		pairsTableNameColsDefs.put(TABLE_NAME, new DbColumnDef[] { CAMPO_ID,
				CAMPO_NOMBRE });
		pairsTableNameColsDefs
				.put(CatalogoListaDescriptoresDBEntityImpl.TABLE_NAME,
						new DbColumnDef[] { CatalogoListaDescriptoresDBEntityImpl.CAMPO_NOMBRE_LISTA });

		String qual = getBusquedaWhereClause(serviceClient, busqueda);

		if (busqueda.getPageInfo() != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("nombre", CAMPO_NOMBRE);
			criteriosOrdenacion.put("nombreLista",
					CatalogoListaDescriptoresDBEntityImpl.CAMPO_NOMBRE_LISTA);

			return getVOS(qual,
					busqueda.getPageInfo().getOrderBy(criteriosOrdenacion),
					pairsTableNameColsDefs, AutoridadVO.class,
					busqueda.getPageInfo());
		} else {
			StringBuffer sbQual = new StringBuffer(qual).append(" ORDER BY ")
					.append(CAMPO_NOMBRE.getQualifiedName());

			return getVOS(sbQual.toString(), pairsTableNameColsDefs,
					AutoridadVO.class);
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
	private String getBusquedaWhereClause(GenericUserInfo serviceClient,
			BusquedaGeneralAutVO busquedaGeneralAutVO) {
		final StringBuffer clausulaWhere = new StringBuffer();

		// Join con la tabla de listas descriptoras
		clausulaWhere.append("WHERE ").append(
				DBUtils.generateJoinCondition(CAMPO_IDLISTA,
						CatalogoListaDescriptoresDBEntityImpl.CAMPO_ID));

		// Modificación para que devuelva los que sólo tienen descripción
		clausulaWhere.append(" AND ").append(
				DBUtils.generateIsNotNullCondition(getConnection(),
						CAMPO_ID_FICHA_DESCR));

		clausulaWhere.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPO,
						TipoDescriptor.ENTIDAD));

		// Comprobación de permisos sobre el elemento
		DBUtils.addPermissionsCheckingClauses(serviceClient, clausulaWhere,
				CAMPO_NIVEL_ACCESO, null, CAMPO_ID_LCA);

		// Nombre
		if (StringUtils.isNotBlank(busquedaGeneralAutVO.getNombre())) {
			// TODO: Revisar si es necesario cambiar esto a contains

			clausulaWhere.append(" AND UPPER(")
					.append(CAMPO_NOMBRE.getQualifiedName())
					.append(") LIKE '%")
					.append(busquedaGeneralAutVO.getNombre().toUpperCase())
					.append("%'");
		}

		// Listas descriptoras
		if ((busquedaGeneralAutVO.getListasDescriptoras() != null)
				&& (busquedaGeneralAutVO.getListasDescriptoras().length > 0)) {
			clausulaWhere.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_IDLISTA,
							busquedaGeneralAutVO.getListasDescriptoras()));
		}

		// Texto
		if (StringUtils.isNotBlank(busquedaGeneralAutVO.getTexto())) {
			final String textoUpperCase = busquedaGeneralAutVO.getTexto()
					.toUpperCase();

			clausulaWhere
					.append(" AND (")
					.append(CAMPO_ID.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(TextoCortoDescrDBEntityImpl.CAMPO_ID_DESCR
							.getQualifiedName())
					.append(" FROM " + TextoCortoDescrDBEntityImpl.TABLE_NAME)
					.append(" WHERE ")
					.append(DBUtils.generateContainsTokenField(getConnection(),
							TextoCortoDescrDBEntityImpl.CAMPO_VALOR,
							TextoCortoDescrDBEntityImpl.CAMPO_IDXVALOR,
							busquedaGeneralAutVO.getTexto()))
					.append(")")
					.append(" OR ")
					.append(CAMPO_ID.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(TextoLargoDescrDBEntityImpl.CAMPO_ID_DESCR
							.getQualifiedName())
					.append(" FROM " + TextoLargoDescrDBEntityImpl.TABLE_NAME)
					.append(" WHERE ")
					.append(DBUtils.generateContainsTokenField(getConnection(),
							TextoLargoDescrDBEntityImpl.CAMPO_VALOR,
							TextoLargoDescrDBEntityImpl.CAMPO_IDXVALOR,
							busquedaGeneralAutVO.getTexto())).append(")")
					.append(" OR UPPER(")

					// TODO: revisar si es necesario cambiar esto a contains
					.append(CAMPO_NOMBRE.getQualifiedName())
					.append(") LIKE '%" + textoUpperCase + "%'").append(")");
		}

		// Todas/Con descripción/Sin descripción
		if (StringUtils.isNotBlank(busquedaGeneralAutVO.getDescripcion())) {
			clausulaWhere.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_TIENE_DESCR,
							busquedaGeneralAutVO.getDescripcion()));
		}

		// Fechas
		if ((busquedaGeneralAutVO.getFechaIni() != null)
				|| (busquedaGeneralAutVO.getFechaFin() != null)) {
			clausulaWhere
					.append(" AND ")
					.append(CAMPO_ID.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(FechaDescrDBEntityImpl.CAMPO_ID_DESCR
							.getQualifiedName())
					.append(" FROM " + FechaDescrDBEntityImpl.TABLE_NAME
							+ " WHERE ");

			if (busquedaGeneralAutVO.getFechaIni() != null) {
				clausulaWhere
						.append("((")
						.append(FechaDescrDBEntityImpl.CAMPO_FECHA_FINAL
								.getQualifiedName())
						.append(" IS NULL AND ")
						.append(DBUtils.generateNEQTokenField(
								FechaDescrDBEntityImpl.CAMPO_CALIFICADOR,
								DateQualifierHelper.CALIFICADOR_SIN_FECHA))
						.append(") OR ")
						.append(FechaDescrDBEntityImpl.CAMPO_FECHA_FINAL
								.getQualifiedName())
						.append(">=")
						.append(DBUtils.getNativeToDateSyntax(getConnection(),
								busquedaGeneralAutVO.getFechaIni(),
								CustomDateFormat.SDF_YYYYMMDD)).append(")");

			}

			if (busquedaGeneralAutVO.getFechaFin() != null) {
				if (busquedaGeneralAutVO.getFechaIni() != null)
					clausulaWhere.append("AND ");

				clausulaWhere
						.append("((")
						.append(FechaDescrDBEntityImpl.CAMPO_FECHA_INICIAL
								.getQualifiedName())
						.append(" IS NULL AND ")
						.append(DBUtils.generateNEQTokenField(
								FechaDescrDBEntityImpl.CAMPO_CALIFICADOR,
								DateQualifierHelper.CALIFICADOR_SIN_FECHA))
						.append(") OR ")
						.append(FechaDescrDBEntityImpl.CAMPO_FECHA_INICIAL
								.getQualifiedName())
						.append("<=")
						.append(DBUtils.getNativeToDateSyntax(getConnection(),
								busquedaGeneralAutVO.getFechaFin(),
								CustomDateFormat.SDF_YYYYMMDD)).append(")");

			}

			clausulaWhere.append(")");
		}

		// Calificador de fechas
		if (StringUtils.isNotEmpty(busquedaGeneralAutVO.getCalificador())) {
			clausulaWhere
					.append(" AND ")
					.append(CAMPO_ID.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(FechaDescrDBEntityImpl.CAMPO_ID_DESCR
							.getQualifiedName())
					.append(" FROM " + FechaDescrDBEntityImpl.TABLE_NAME
							+ " WHERE ")
					.append(DBUtils.generateEQTokenField(
							FechaDescrDBEntityImpl.CAMPO_CALIFICADOR,
							busquedaGeneralAutVO.getCalificador())).append(")");
		}

		// Números
		if (StringUtils.isNotEmpty(busquedaGeneralAutVO.getNumero())
				|| StringUtils.isNotEmpty(busquedaGeneralAutVO.getTipoMedida())
				|| StringUtils.isNotEmpty(busquedaGeneralAutVO
						.getUnidadMedida())) {
			boolean iniciado = false;

			clausulaWhere
					.append(" AND ")
					.append(CAMPO_ID.getQualifiedName())
					.append(" IN (SELECT DISTINCT ")
					.append(NumeroDescrDBEntityImpl.CAMPO_ID_DESCR
							.getQualifiedName())
					.append(" FROM " + NumeroDescrDBEntityImpl.TABLE_NAME
							+ " WHERE ");

			// Valor del número
			if (StringUtils.isNotBlank(busquedaGeneralAutVO.getNumero())) {
				clausulaWhere
						.append(NumeroDescrDBEntityImpl.CAMPO_VALOR
								.getQualifiedName())
						.append(busquedaGeneralAutVO.getNumeroComp())
						.append(busquedaGeneralAutVO.getNumero());

				iniciado = true;
			}

			// Tipo de medida
			if (StringUtils.isNotBlank(busquedaGeneralAutVO.getTipoMedida())) {
				if (iniciado)
					clausulaWhere.append(" AND ");

				clausulaWhere.append(DBUtils.generateEQTokenField(
						NumeroDescrDBEntityImpl.CAMPO_TIPOMEDIDA,
						busquedaGeneralAutVO.getTipoMedida()));

				iniciado = true;
			}

			// Unidad de medida
			if (StringUtils.isNotBlank(busquedaGeneralAutVO.getUnidadMedida())) {
				if (iniciado)
					clausulaWhere.append(" AND ");

				clausulaWhere
						.append(" UPPER(")
						.append(NumeroDescrDBEntityImpl.CAMPO_UNIDADMEDIDA
								.getQualifiedName())
						.append(") LIKE '%")
						.append(busquedaGeneralAutVO.getUnidadMedida()
								.toUpperCase()).append("%'");
			}

			clausulaWhere.append(")");
		}

		return clausulaWhere.toString();
	}

	public List getDescriptoresXListaAcceso(String idListaAcceso) {
		String qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_LCA,
						idListaAcceso)).toString();

		return (List) getVOS(qual, TABLE_NAME, COLS_DEFS, DescriptorVO.class);
	}

	/**
	 * Recupera el descriptor de una determinada lista de descriptores a partir
	 * del identificador de ese descriptor en el sistema externo desde el que
	 * fue importado
    *
	 * @param idEnSistemaExterno
	 *            Identificador del descriptor en el sistema externo del que fue
	 *            importado
	 * @param listaDescriptores
	 *            Identificador de la lista de descriptores a la que debe
	 *            pertenecer el descriptor
	 * @return Datos del descriptor
	 */
	public DescriptorVO getDescriptorXIdEnSistemaExterno(
			String idEnSistemaExterno, String listaDescriptores) {
		StringBuffer qual = new StringBuffer("WHERE 1=1 ");
		if (listaDescriptores != null)
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_IDLISTA,
							listaDescriptores));
		if (idEnSistemaExterno != null)
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_ID_DESCR_SIST_EXT,
							idEnSistemaExterno));
		return (DescriptorVO) getVO(qual.toString(), TABLE_NAME, COLS_DEFS,
				DescriptorVO.class);
	}

	public List getDescriptoresProductoresRelacion(
			BusquedaDescriptoresVO busquedaVO, Integer[] tiposRelacion) {
		/*
		 * SELECT * FROM ADDESCRIPTOR WHERE ID IN (SELECT IDDESCRORGPRODUCTOR
		 * FROM ASGTRENTREGA WHERE TIPOTRANSFERENCIA IN(<tiposRelacion>)) ORDER
		 * BY NOMBRE;
		 */

		StringBuffer subquery = new StringBuffer(DBUtils.SELECT)
				.append(RelacionEntregaDBEntityBaseImpl.CAMPO_IDDESCRORGPRODUCTOR)
				.append(DBUtils.FROM)
				.append(RelacionEntregaDBEntityBaseImpl.TABLE_NAME);

		if (ArrayUtils.isNotEmpty(tiposRelacion)) {
			subquery.append(DBUtils.WHERE)
					.append(DBUtils
							.generateInTokenField(
									RelacionEntregaDBEntityBaseImpl.CAMPO_TIPOTRANSFERENCIA,
									tiposRelacion));
		}

		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateInTokenFieldSubQuery(CAMPO_ID, subquery.toString()));

		if (busquedaVO != null
				&& StringUtils.isNotEmpty(busquedaVO.getNombre())) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateLikeTokenField(CAMPO_NOMBRE,
							busquedaVO.getNombre(), true));
		}

		// Añadir la orednacion por nombre.
		qual.append(DBUtils.generateOrderBy(CAMPO_NOMBRE));

		return getVOS(qual.toString(), TABLE_NAME, COLS_DEFS,
				DescriptorVO.class);
	}

	/**
	 * {@inheritDoc}
    *
	 * @see descripcion.db.IDescriptorDBEntity#updateEstado(int,
	 *      java.lang.String[])
	 */
	public void updateEstado(int estado, String[] ids) {
		String qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateInTokenField(CAMPO_ID, ids)).toString();

		HashMap columnsToUpdate = new HashMap();
		columnsToUpdate.put(CAMPO_ESTADO, new Integer(estado));
		columnsToUpdate.put(CAMPO_TIMESTAMP, new Date());

		updateFields(qual, columnsToUpdate, TABLE_NAME);
	}

	public DescriptorVO getProductorSerieByCodigoOrgano(String idSerie,
			String idLista, String codigoOrgano) {

		return getProductorSerie(idSerie, idLista, null, codigoOrgano);

	}

	public DescriptorVO getProductorSerieByNombreOrgano(String idSerie,
			String idLista, String nombreOrgano) {

		return getProductorSerie(idSerie, idLista, nombreOrgano, null);
	}

	private DescriptorVO getProductorSerie(String idSerie, String idLista,
			String nombreOrgano, String codigoOrgano) {

		StringBuffer selectClause = new StringBuffer(DBUtils.SELECT)
				.append(DbUtil.getColumnNames(COLS_DEFS));

		// INNER JOIN ASGFORGPROD ON ADDESCRIPTOR.ID = ASGFORGPROD.IDDESCR
		JoinDefinition joinOrgProd = new JoinDefinition(CAMPO_ID,
				OrganoProductorDBEntity.CAMPO_IDDESCR);

		// INNER JOIN ASGFPRODSERIE ON ADDESCRIPTOR.ID =
		// ASGFPRODSERIE.IDPRODUCTOR
		JoinDefinition joinProdSerie = new JoinDefinition(CAMPO_ID,
				ProductorSerieDBEntityImpl.CAMPO_IDPRODUCTOR);

		// INNER JOIN ASCAORGANO ON ASGFORGPROD.IDORG = ASCAORGANO.ID
		JoinDefinition joinOrgano = new JoinDefinition(
				OrganoProductorDBEntity.CAMPO_ID_ORG,
				CAOrganoDbEntityImpl.CAMPO_ID);

		StringBuffer fromClause = new StringBuffer(DBUtils.FROM);

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(
						ProductorSerieDBEntityImpl.CAMPO_IDSERIE, idSerie))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_IDLISTA, idLista));

		if (StringUtils.isNotEmpty(codigoOrgano)) {

			/*
			 * SELECT ADDESCRIPTOR.* FROM ADDESCRIPTOR INNER JOIN ASGFORGPROD ON
			 * ADDESCRIPTOR.ID = ASGFORGPROD.IDDESCR INNER JOIN ASGFPRODSERIE ON
			 * ASGFPRODSERIE.IDPRODUCTOR = ASGFORGPROD.IDDESCR INNER JOIN
			 * ASCAORGANO ON ASGFORGPROD.IDORG = ASCAORGANO.ID WHERE
			 * ASGFPRODSERIE.IDSERIE = '0cb0ec0ce98990000000000000000007' AND
			 * ADDESCRIPTOR.IDLISTA='ID_LIST_BDORGANIZACION' AND
			 * ASCAORGANO.CODIGO = 'OILUCAS'
			 */

			fromClause.append(DBUtils.generateInnerJoinCondition(new TableDef(
					TABLE_NAME), new JoinDefinition[] { joinOrgProd,
					joinProdSerie, joinOrgano }));

			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(
							CAOrganoDbEntityImpl.CAMPO_CODIGO, codigoOrgano));
		} else if (StringUtils.isNotEmpty(nombreOrgano)) {
			/*
			 * SELECT ADDESCRIPTOR.* FROM ADDESCRIPTOR INNER JOIN ASGFPRODSERIE
			 * ON ADDESCRIPTOR.ID = ASGFPRODSERIE.IDPRODUCTOR WHERE
			 * ASGFPRODSERIE.IDSERIE = '0cb0ec0ce98990000000000000000007' AND
			 * ADDESCRIPTOR.IDLISTA='ID_LIST_BDORGANIZACION' AND
			 * ADDESCRIPTOR.NOMBRE = 'Órgano Lucas/Institución Lucas'
			 */
			fromClause.append(DBUtils.generateInnerJoinCondition(new TableDef(
					TABLE_NAME), new JoinDefinition[] { joinOrgProd,
					joinProdSerie, joinOrgano }));

			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(CAMPO_NOMBRE, nombreOrgano));
		}

		final ConsultaConnectBy consultaConnectBy = new ConsultaConnectBy(
				selectClause.toString(), fromClause.toString(),
				qual.toString(), null, null);

		return (DescriptorVO) getVO(COLS_DEFS, consultaConnectBy,
				DescriptorVO.class);
	}

	public DescriptorVO getDescriptorByIdDescr(String idListaDescriptora,
			String idDescrSistExt) {
		String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDLISTA,
						idListaDescriptora))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_ID_DESCR_SIST_EXT,
						getValorCampo(idDescrSistExt))).toString();

		return (DescriptorVO) getVO(qual, TABLE_NAME, COLS_DEFS,
				DescriptorVO.class);
	}

}
