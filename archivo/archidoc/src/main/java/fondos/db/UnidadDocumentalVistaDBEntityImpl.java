/**
 *
 */
package fondos.db;

import ieci.core.db.DbColumnDef;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import se.usuarios.ServiceClient;
import xml.config.ConfiguracionDescripcion;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.TableDef;

import descripcion.db.FechaDBEntityImpl;
import fondos.model.IElementoCuadroClasificacion;
import fondos.vos.BusquedaUdocsSerieVO;
import fondos.vos.UnidadDocumetalViewVO;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class UnidadDocumentalVistaDBEntityImpl extends DBEntity implements
		IUnidadDocumentalVistaDBEntity {

	public static final String VIEW_NAME = "VUNIDADDOCUMENTAL";

	public static final String FI_VALOR_COLUMN_NAME = "FIVALOR";
	public static final String FI_FECHAINI_COLUMN_NAME = "FIFECHAINI";
	public static final String FI_FECHAFIN_COLUMN_NAME = "FIFECHAFIN";
	public static final String FI_CALIFICADOR_COLUMN_NAME = "FICALIFICADOR";
	public static final String FI_FORMATO_COLUMN_NAME = "FIFORMATO";
	public static final String FI_SEP_COLUMN_NAME = "FISEP";

	public static final String FF_VALOR_COLUMN_NAME = "FFVALOR";
	public static final String FF_FECHAINI_COLUMN_NAME = "FFFECHAINI";
	public static final String FF_FECHAFIN_COLUMN_NAME = "FFFECHAFIN";
	public static final String FF_CALIFICADOR_COLUMN_NAME = "FFCALIFICADOR";
	public static final String FF_FORMATO_COLUMN_NAME = "FFFORMATO";
	public static final String FF_SEP_COLUMN_NAME = "FFSEP";

	public static final TableDef tableDef = new TableDef(VIEW_NAME);

	public static final DbColumnDef ID_ELEMENTO_FIELD = new DbColumnDef(
			tableDef,
			ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD);
	public static final DbColumnDef TIPO_ELEMENTO_FIELD = new DbColumnDef(
			tableDef,
			ElementoCuadroClasificacionDBEntityImplBase.TIPO_ELEMENTO_FIELD);
	public static final DbColumnDef NIVEL_COLUMN_FIELD = new DbColumnDef(
			tableDef, ElementoCuadroClasificacionDBEntityImplBase.NIVEL_FIELD);
	public static final DbColumnDef CODIGO_FIELD = new DbColumnDef(tableDef,
			ElementoCuadroClasificacionDBEntityImplBase.CODIGO_FIELD);
	public static final DbColumnDef TITULO_FIELD = new DbColumnDef(tableDef,
			ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD);
	public static final DbColumnDef IDPADRE_FIELD = new DbColumnDef(tableDef,
			ElementoCuadroClasificacionDBEntityImplBase.IDPADRE_FIELD);
	public static final DbColumnDef IDFONDO_FIELD = new DbColumnDef(tableDef,
			ElementoCuadroClasificacionDBEntityImplBase.IDFONDO_FIELD);
	public static final DbColumnDef CODREFFONDO_FIELD = new DbColumnDef(
			tableDef,
			ElementoCuadroClasificacionDBEntityImplBase.CODREFFONDO_FIELD);
	public static final DbColumnDef FINALCODREFPADRE_FIELD = new DbColumnDef(
			tableDef,
			ElementoCuadroClasificacionDBEntityImplBase.FINALCODREFPADRE_FIELD);
	public static final DbColumnDef CODREFERENCIA_FIELD = new DbColumnDef(
			tableDef,
			ElementoCuadroClasificacionDBEntityImplBase.CODIGO_REFERENCIA_FIELD);
	public static final DbColumnDef ESTADO_FIELD = new DbColumnDef(tableDef,
			ElementoCuadroClasificacionDBEntityImplBase.ESTADO_ELEMENTO_FIELD);
	public static final DbColumnDef IDELIMINACION_FIELD = new DbColumnDef(
			tableDef,
			ElementoCuadroClasificacionDBEntityImplBase.ID_ELIMINACION_FIELD);
	public static final DbColumnDef IDFICHADESCR_FIELD = new DbColumnDef(
			tableDef,
			ElementoCuadroClasificacionDBEntityImplBase.IDFICHADESCR_FIELD);
	public static final DbColumnDef TIENEDESCR_FIELD = new DbColumnDef(
			tableDef,
			ElementoCuadroClasificacionDBEntityImplBase.TIENEDESCR_FIELD);
	public static final DbColumnDef EDITCLFDOCS_FIELD = new DbColumnDef(
			tableDef,
			ElementoCuadroClasificacionDBEntityImplBase.EDITCLFDOCS_FIELD);
	public static final DbColumnDef ID_REP_ECM_FIELD = new DbColumnDef(
			tableDef,
			ElementoCuadroClasificacionDBEntityImplBase.REPOSITORIO_ECM_FIELD);
	public static final DbColumnDef IDARCHIVO_FIELD = new DbColumnDef(tableDef,
			ElementoCuadroClasificacionDBEntityImplBase.ARCHIVO_FIELD);
	public static final DbColumnDef NIVELACCESO_FIELD = new DbColumnDef(
			tableDef,
			ElementoCuadroClasificacionDBEntityImplBase.NIVEL_ACCESO_FIELD);
	public static final DbColumnDef IDLCA_FIELD = new DbColumnDef(tableDef,
			ElementoCuadroClasificacionDBEntityImplBase.LISTA_ACCESO_FIELD);
	public static final DbColumnDef SUBTIPO_FIELD = new DbColumnDef(tableDef,
			ElementoCuadroClasificacionDBEntityImplBase.SUBTIPO_ELEMENTO_FIELD);
	public static final DbColumnDef ORDPOS_FIELD = new DbColumnDef(tableDef,
			ElementoCuadroClasificacionDBEntityImplBase.ORDEN_POSICION_FIELD);

	public static final DbColumnDef FI_VALOR_FIELD = new DbColumnDef(
			FI_VALOR_COLUMN_NAME, FI_VALOR_COLUMN_NAME, tableDef,
			FechaDBEntityImpl.CAMPO_VALOR);
	public static final DbColumnDef FI_FECHAINI_FIELD = new DbColumnDef(
			FI_FECHAINI_COLUMN_NAME, FI_FECHAINI_COLUMN_NAME, tableDef,
			FechaDBEntityImpl.CAMPO_FECHA_INICIAL);
	public static final DbColumnDef FI_FECHAFIN_FIELD = new DbColumnDef(
			FI_FECHAFIN_COLUMN_NAME, FI_FECHAFIN_COLUMN_NAME, tableDef,
			FechaDBEntityImpl.CAMPO_FECHA_FINAL);
	public static final DbColumnDef FI_CALIFICADOR_FIELD = new DbColumnDef(
			FI_CALIFICADOR_COLUMN_NAME, FI_CALIFICADOR_COLUMN_NAME, tableDef,
			FechaDBEntityImpl.CAMPO_CALIFICADOR);
	public static final DbColumnDef FI_FORMATO_FIELD = new DbColumnDef(
			FI_FORMATO_COLUMN_NAME, FI_FORMATO_COLUMN_NAME, tableDef,
			FechaDBEntityImpl.CAMPO_FORMATO);
	public static final DbColumnDef FI_SEP_FIELD = new DbColumnDef(
			FI_SEP_COLUMN_NAME, FI_SEP_COLUMN_NAME, tableDef,
			FechaDBEntityImpl.CAMPO_SEP);

	public static final DbColumnDef FF_VALOR_FIELD = new DbColumnDef(
			FF_VALOR_COLUMN_NAME, FF_VALOR_COLUMN_NAME, tableDef,
			FechaDBEntityImpl.CAMPO_VALOR);
	public static final DbColumnDef FF_FECHAINI_FIELD = new DbColumnDef(
			FF_FECHAINI_COLUMN_NAME, FF_FECHAINI_COLUMN_NAME, tableDef,
			FechaDBEntityImpl.CAMPO_FECHA_INICIAL);
	public static final DbColumnDef FF_FECHAFIN_FIELD = new DbColumnDef(
			FF_FECHAFIN_COLUMN_NAME, FF_FECHAFIN_COLUMN_NAME, tableDef,
			FechaDBEntityImpl.CAMPO_FECHA_FINAL);
	public static final DbColumnDef FF_CALIFICADOR_FIELD = new DbColumnDef(
			FF_CALIFICADOR_COLUMN_NAME, FF_CALIFICADOR_COLUMN_NAME, tableDef,
			FechaDBEntityImpl.CAMPO_CALIFICADOR);
	public static final DbColumnDef FF_FORMATO_FIELD = new DbColumnDef(
			FF_FORMATO_COLUMN_NAME, FF_FORMATO_COLUMN_NAME, tableDef,
			FechaDBEntityImpl.CAMPO_FORMATO);
	public static final DbColumnDef FF_SEP_FIELD = new DbColumnDef(
			FF_SEP_COLUMN_NAME, FF_SEP_COLUMN_NAME, tableDef,
			FechaDBEntityImpl.CAMPO_SEP);

	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] {
			ID_ELEMENTO_FIELD, TIPO_ELEMENTO_FIELD, NIVEL_COLUMN_FIELD,
			CODIGO_FIELD, TITULO_FIELD, IDPADRE_FIELD, IDFONDO_FIELD,
			CODREFFONDO_FIELD, FINALCODREFPADRE_FIELD, CODREFERENCIA_FIELD,
			ESTADO_FIELD, IDELIMINACION_FIELD, IDFICHADESCR_FIELD,
			TIENEDESCR_FIELD, EDITCLFDOCS_FIELD, ID_REP_ECM_FIELD,
			IDARCHIVO_FIELD, NIVELACCESO_FIELD, IDLCA_FIELD, SUBTIPO_FIELD,
			ORDPOS_FIELD, FI_VALOR_FIELD, FI_FECHAINI_FIELD, FI_FECHAFIN_FIELD,
			FI_CALIFICADOR_FIELD, FI_FORMATO_FIELD, FI_SEP_FIELD,
			FF_VALOR_FIELD, FF_FECHAINI_FIELD, FF_FECHAFIN_FIELD,
			FF_CALIFICADOR_FIELD, FF_FORMATO_FIELD, FF_SEP_FIELD };

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return VIEW_NAME;
	}

	/**
	 * @param dataSource
	 */
	public UnidadDocumentalVistaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.db.IElementoCuadroClasificacionVistaDBEntity#getElementosByTipo(java.lang.String[])
	 */
	public List getUnidadesDocumentalesSerie(ServiceClient serviceClient,
			String idSerie, BusquedaUdocsSerieVO busquedaVO) {

		ConfiguracionDescripcion configDescripcion = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion();
		String idProductor = configDescripcion.getProductor();

		// String[] tables = new String[] {
		// new
		// TableDef(ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO).getDeclaration(),
		// new
		// TableDef(UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL).getDeclaration(),
		// new TableDef(NivelCFDBEntityImpl.NIVELCF_TABLE_NAME).getDeclaration()
		// };

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDPADRE_FIELD, idSerie))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(ESTADO_FIELD,
						IElementoCuadroClasificacion.VIGENTE));

		if (busquedaVO != null) {
			// if(StringUtils.isNotEmpty(busquedaVO.getProductor()) &&
			// !FondosConstants.TODOS_PRODUCTORES.equals(busquedaVO.getProductor())){
			// if(FondosConstants.SIN_PRODUCTOR.equals(busquedaVO.getProductor())){
			// StringBuffer subconsulta = new StringBuffer();
			// subconsulta.append(DBUtils.SELECT).append(ID_ELEMENTO_FIELD).append(DBUtils.FROM).append(TABLE_NAME_ELEMENTO).
			// append(DBUtils.WHERE).append(DBUtils.generateEQTokenField(IDPADRE_FIELD,
			// idSerie));
			//
			// StringBuffer subquery = new StringBuffer();
			// subquery.append(DBUtils.SELECT).append(ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO).
			// append(DBUtils.FROM).append(ReferenciaDBEntityImpl.TABLE_NAME).
			// append(DBUtils.WHERE).append(DBUtils.generateEQTokenField(ReferenciaDBEntityImpl.CAMPO_ID_CAMPO,
			// idProductor)).
			// append(DBUtils.AND).append(DBUtils.generateInTokenFieldSubQuery(ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO,
			// subconsulta.toString()));
			//
			// qual.append(DBUtils.AND);
			// qual.append(DBUtils.generateNotInTokenFieldSubQuery(ID_ELEMENTO_FIELD,
			// subquery.toString()));
			// }else{
			// DbColumnDef columnaNombreProductor=new
			// DbColumnDef("nombreProductor",new
			// TableDef(DescriptorDBEntityImpl.TABLE_NAME,DescriptorDBEntityImpl.TABLE_NAME),
			// DescriptorDBEntityImpl.NOMBRE_COLUMN_NAME,DescriptorDBEntityImpl.CAMPO_NOMBRE.getDataType(),true);
			// COLS_DEF = (DbColumnDef[]) ArrayUtils.concat(COLS_DEF,new
			// DbColumnDef[] {columnaNombreProductor});
			// tables = (String []) ArrayUtils.concat(tables, new String [] {new
			// TableDef(ReferenciaDBEntityImpl.TABLE_NAME).getDeclaration()});
			// tables = (String []) ArrayUtils.concat(tables, new String [] {new
			// TableDef(DescriptorDBEntityImpl.TABLE_NAME).getDeclaration()});
			// qual.append(DBUtils.AND).append(DBUtils.generateJoinCondition(ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
			// ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO));
			// qual.append(DBUtils.AND).append(DBUtils.generateJoinCondition(ReferenciaDBEntityImpl.CAMPO_IDOBJREF,
			// DescriptorDBEntityImpl.CAMPO_ID));
			// qual.append(DBUtils.AND).append(DBUtils.generateEQTokenField(ReferenciaDBEntityImpl.CAMPO_IDOBJREF,
			// busquedaVO.getProductor()));
			// }
			// }

			// if(StringUtils.isNotEmpty(str))

			// Titulo
			if (StringUtils.isNotEmpty(busquedaVO.getTitulo())) {
				qual.append(DBUtils.AND).append(
						DBUtils.generateLikeTokenField(TITULO_FIELD,
								busquedaVO.getTitulo(), true));
			}

			// Codigo
			if (StringUtils.isNotEmpty(busquedaVO.getCodigo())) {
				qual.append(DBUtils.AND).append(
						DBUtils.generateLikeTokenField(CODIGO_FIELD,
								busquedaVO.getCodigo(), true));
			}

			// Numero Expediente
			// if(StringUtils.isNotEmpty(busquedaVO.getNumeroExpediente())){
			// qual.append(DBUtils.AND)
			// .append(DBUtils.generateLikeTokenField(columnaNumExp,
			// busquedaVO.getNumeroExpediente(), true));
			// }

		}
		// DBUtils.addPermissionsCheckingClausesWithPermissions(serviceClient,
		// new StringBuffer(DBUtils.generateTableSet(tables)), qual,
		// NIVELACCESO_FIELD, IDARCHIVO_FIELD, LISTAACCESO_FIELD,
		// new
		// String[]{AppPermissions.CONSULTA_CUADRO_CLASIFICACION,AppPermissions.MODIFICACION_CUADRO_CLASIFICACION});

		// Orden
		// if (pageInfo != null){
		// Map criteriosOrdenacion = new HashMap();
		// criteriosOrdenacion.put("codigo", CODIGO_FIELD);
		// criteriosOrdenacion.put("titulo", TITULO_FIELD);
		// criteriosOrdenacion.put("nombreNivel", NIVEL_FIELD);
		// return
		// getDistinctVOS(qual.toString(),pageInfo.getOrderBy(criteriosOrdenacion),
		// DBUtils.generateTableSet(tables), COLS_DEF, UnidadDocumental.class,
		// pageInfo);
		// }else{
		qual.append(DBUtils.generateOrderBy(CODIGO_FIELD));
		return getDistinctVOS(qual.toString(), VIEW_NAME, COL_DEFS,
				UnidadDocumetalViewVO.class);
		// }

	}

	private String getQualById(String id) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_ELEMENTO_FIELD, id));
		return qual.toString();
	}

}
